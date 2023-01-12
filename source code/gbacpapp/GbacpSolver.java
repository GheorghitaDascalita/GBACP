package fii.student.gbacpapp;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.tools.ArrayUtils;

public class GbacpSolver {
    // prefP[i] = 1 <=> cursul i este atribuit unei perioade nepreferate
    private BoolVar[] prefP;
    
    // perioada asociata fiecarui curs
    private IntVar[] p;
    // x[i][j] = 1 <=> cursul j se preda in perioada i
    private BoolVar[][] x;
    // l[s][j] = nr total de credite al perioadei j, specializare s
    private IntVar[][] l;
    // distanta intre l[perioada] si l mediu [specializare]
    private IntVar[][] lDist;
    
    // cost dezechilibru
    private IntVar cs;
    // cost preferinte
    private IntVar cp;
    // cost total, al functiei obiectiv
    private IntVar ct;
    
    private Solver solver;
    private Model model;


    public void createSolver() {
    	model = new Model("GBACP");
        solver = model.getSolver();
    }

    public void buildModel(ProblemModel prob) {
    	int[] w = prob.getW();
    	int[][] sp = prob.getSp();
    	int[][] pref = prob.getPref();
    	
    	
        p = model.intVarArray("P", prob.getN(), 0, prob.getM() - 1);
        x = model.boolVarMatrix("B", prob.getM(), prob.getN());
        l = model.intVarMatrix("L", prob.getK(), prob.getM(), prob.getA(), prob.getB());
        lDist = model.intVarMatrix("Ldist", prob.getK(), prob.getM(), 0, prob.getB()-prob.getA());
        prefP = model.boolVarArray("PrefP", prob.getN());
        
        cs = model.intVar("Cs", 0, 10000);
        cp = model.intVar("Cp", 0, 10000);
        ct = model.intVar("C", 0, 10000, true);
        
        // x[i][j] = 1 <=> p[j] = i
        for (int i = 0; i < prob.getM(); i++) {
            for (int j = 0; j < prob.getN(); j++) {
                model.ifThenElse(x[i][j],
                    model.arithm(p[j], "=", i),
                    model.arithm(p[j], "!=", i)
                );
            }
        }
        
        int[] sW = new int[prob.getN()];
        for (int s = 0; s < prob.getK(); s++) {
        	for(int j=0; j<prob.getN(); j++)
        		sW[j] = sp[s][j] * w[j];
	        for (int i = 0; i < prob.getM(); i++) {
	        	// pt specializarea s, perioada i, nr cursuri >= c
	            model.scalar(x[i], sp[s], ">=", prob.getC()).post();
	            // pt specializarea s, perioada i, nr cursuri <= d
	            model.scalar(x[i], sp[s], "<=", prob.getD()).post();
	            // l[specializare][perioada] = nr total de credite
	            model.scalar(x[i], sW, "=", l[s][i]).post();
	        }
        }

        // p[curs1] < p[curs2]
        for(int i = 0; i < prob.getnPrec(); i++)
            precondition(prob.getPrec()[i][0], prob.getPrec()[i][1]);
        
        // Lm[s] = nr total mediu de credite, specializare s
        int[] Lm = new int[prob.getK()];
        for(int s = 0; s < prob.getK(); s++) {
        	for(int i = 0; i < prob.getN(); i++)
        		Lm[s] = Lm[s] + sp[s][i] * w[i];
        	Lm[s] = Lm[s] / prob.getM();
        	
        	// se calculeaza distanta dintre Lm[s] si l[s][perioada]
        	for(int j = 0; j < prob.getM(); j++)
        		lDist[s][j] = l[s][j].dist(Lm[s]).intVar();
        }
        // cs = suma deviatiilor lDist
        model.sum(ArrayUtils.flatten(lDist), "=", cs).post();
        
        for(int i = 0; i < prob.getN(); i++) {
        		// prefP[i] = pref[i][p[i]]
        		model.element(prefP[i], pref[i], p[i]).post();
        }
        int[] prefW = new int[prob.getN()];
        for(int i=0; i<prob.getN(); i++)
        	// penalizare = 2 * nr credite
        	prefW[i] = 2 * w[i];
        // cp = suma penalizarilor
        // prefP[curs] = 1 => penalizare
        model.scalar(prefP, prefW, "=", cp).post();
        
        model.arithm(cs, "+", cp, "=", ct).post();
    }

    private void precondition(int course1, int course2) {
        model.arithm(p[course1], "<", p[course2]).post();
    }

    public int[] solve(ProblemModel prob) {
    	// minimizarea costului total (variabilei obiectiv)
    	model.setObjective(Model.MINIMIZE, ct);
        solver.limitTime("10s");
        
    	Solution solution = new Solution(model);
    	while (solver.solve()) {
    	    solution.record();
    	}   	
        if(solution.exists()){
            // se trimite p, pt solutia optima
            int[] solP = new int[prob.getN()+3];
            for(int j = 0; j < prob.getN(); j++)
                    solP[j] = solution.getIntVal(p[j]);
            // se trimit costurile: dezechilibrului, preferintelor si total
            solP[prob.getN()] = solution.getIntVal(cs);
            solP[prob.getN()+1] = solution.getIntVal(cp);
            solP[prob.getN()+2] = solution.getIntVal(ct);
            return solP;
        }
        return new int[0];
    }
}
