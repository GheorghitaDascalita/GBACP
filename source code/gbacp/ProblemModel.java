package fii.student.gbacp;

public class ProblemModel {
	
	// nr ani
	private int y;
	// nr perioade ale anului
	private int py;
	// nr perioade
	private int m;
	// nr cursuri
	private int n;
	// nr specializari
	private int k;
	// min credite per perioada
	private int a;
	// max credite per perioada
	private int b;
	// min cursuri per perioada
	private int c;
	// max cursuri per perioada
	private int d;
	// nr preconditii
	private int nPrec;
	// nr preferinte
	private int nPref;
	
	//nr credite asociat fiecarui curs
	private int[] w;
	//sp[s][i] = 1 <=> la specializarea s se preda cursul i
	private int[][] sp;
	//pref[i][j] = 1 <=> se prefera sa nu se predea cursul i in perioada j
	private int[][] pref;
	
	
	public ProblemModel() {
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPy() {
		return py;
	}
	public void setPy(int py) {
		this.py = py;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public int getnPrec() {
		return nPrec;
	}
	public void setnPrec(int nPrec) {
		this.nPrec = nPrec;
	}
	public int getnPref() {
		return nPref;
	}
	public void setnPref(int nPref) {
		this.nPref = nPref;
	}

	public int[] getW() {
		return w;
	}

	public void setW(int[] w) {
		this.w = w;
	}

	public int[][] getSp() {
		return sp;
	}

	public void setSp(int[][] sp) {
		this.sp = sp;
	}

	public int[][] getPref() {
		return pref;
	}

	public void setPref(int[][] pref) {
		this.pref = pref;
	}

}
