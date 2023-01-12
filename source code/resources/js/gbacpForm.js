var prob = new Object();

prob.curricula = [];
prob.courses = [];
prob.preconditions = [];
prob.preferences = [];

const curriculaButton = document.getElementById('add_curriculum');

curriculaButton.addEventListener('click', _  => {
	
	let li = document.createElement("li");
	li.id = "curriculum" + prob.curricula.length;
	li.appendChild(document.createTextNode(document.getElementById("curriculum_title").value));
	let ul = document.createElement("ul");
	ul.id = "list" + prob.curricula.length;
	li.appendChild(ul);
	let list = document.getElementById("curricula");
	list.append(li);
	
	prob.curricula.push(document.getElementById("curriculum_title").value);
	
	document.getElementById("curriculum_title").value = "";
});

const courseButton = document.getElementById('add_course');

courseButton.addEventListener('click', _ => {
	let li = document.createElement("li");
	li.appendChild(document.createTextNode("(" + (prob.courses.length + 1) + ") " + document.getElementById("course_title").value));
	let list = document.getElementById("list" + (document.getElementById("id_curriculum").value-1));
	list.append(li);
	
	
	let course = {
		"course_title": document.getElementById("course_title").value,
		"credits": document.getElementById("credits").value,
		"id_curriculum": [(document.getElementById("id_curriculum").value - 1)]
	};
	prob.courses.push(course);
	
	document.getElementById("course_title").value = "";
	document.getElementById("credits").value = "";
	document.getElementById("id_curriculum").value = "";
});

const courseButton2 = document.getElementById('add_course_old');

courseButton2.addEventListener('click', _ => {
	let li = document.createElement("li");
	li.appendChild(document.createTextNode("(" + document.getElementById("id_course_old").value + ") "
            + prob.courses[document.getElementById("id_course_old").value - 1].course_title));
	let list = document.getElementById("list" + (document.getElementById("id_curriculum_old").value-1));
	list.append(li);
	
	prob.courses[document.getElementById("id_course_old").value - 1]
            .id_curriculum.push(document.getElementById("id_curriculum_old").value-1);
	
	document.getElementById("id_course_old").value = "";
	document.getElementById("id_curriculum_old").value = "";
});

const preconditionsButton = document.getElementById('add_precondition');

preconditionsButton.addEventListener('click', _ => {
        let precId = (prob.preconditions.length === 0) ? 0 :
        prob.preconditions[prob.preconditions.length - 1].id + 1;
	let precondition = {
                "id": precId,
		"id_course1": (document.getElementById("id_course1").value - 1),
		"id_course2": (document.getElementById("id_course2").value - 1)
	};
	prob.preconditions.push(precondition);
        
	let li = document.createElement("li");
	li.appendChild(document.createTextNode("Cursul " + document.getElementById("id_course1").value 
	+ " inaintea cursului " + document.getElementById("id_course2").value));

	let span = document.createElement("span");
	span.innerHTML="&times;";
	span.classList.add("close");
        span.addEventListener('click', event => {
            prob.preconditions = prob.preconditions.filter(x => {
                return x.id !== precId;
            });
            event.target.parentNode.remove();
        });
        span.classList.add("close");
	li.appendChild(span);

	let list = document.getElementById("preconditions") ;
	list.append(li);
	
	document.getElementById("id_course1").value = "";
	document.getElementById("id_course2").value = "";
});

const preferencesButton = document.getElementById('add_preference');

preferencesButton.addEventListener('click', _ => {
	let prefId = (prob.preferences.length === 0) ? 0 :
        prob.preferences[prob.preferences.length - 1].id + 1;
	let preference = {
                "id": prefId,
		"id_course": (document.getElementById("id_course").value - 1),
		"year_period": (document.getElementById("year_period").value - 1)
	};
	prob.preferences.push(preference);
        
	let li = document.createElement("li");
	li.appendChild(document.createTextNode("Cursul " + document.getElementById("id_course").value 
	+ " sa nu fie predat in perioada " + document.getElementById("year_period").value + " a anului"));

	let span = document.createElement("span");
	span.innerHTML="&times;";
	span.classList.add("close");
        span.addEventListener('click', event => {
            prob.preferences = prob.preferences.filter(x => {
                return x.id !== prefId;
            });
            event.target.parentNode.remove();
        });
        span.classList.add("close");
	li.appendChild(span);
        
	let list = document.getElementById("preferences");
	list.append(li);
	
	document.getElementById("id_course").value = "";
	document.getElementById("year_period").value = "";
});


const sendForm = document.getElementById('inputForm');

sendForm.addEventListener('submit', event => {
	event.preventDefault();
        document.querySelector("#send2 > i").style.visibility = "visible";
	
	prob.years = document.querySelector("input[name=years]").value;
	prob.yearPeriods = document.querySelector("input[name=year_periods]").value;
	prob.minCredits = document.querySelector("input[name=min_credits]").value;
	prob.maxCredits = document.querySelector("input[name=max_credits]").value;
	prob.minCourses = document.querySelector("input[name=min_courses]").value;
	prob.maxCourses = document.querySelector("input[name=max_courses]").value;
	
	document.querySelector("input[name=years]").value = "";
	document.querySelector("input[name=year_periods]").value = "";
	document.querySelector("input[name=min_credits]").value = "";
	document.querySelector("input[name=max_credits]").value = "";
	document.querySelector("input[name=min_courses]").value = "";
	document.querySelector("input[name=max_courses]").value = "";
	
	
	fetch("http://localhost:8080/gbacpApp/gbacpForm", {
	    method: 'post',
	    body: JSON.stringify(prob),
	    headers: {
	        'Accept': 'application/json',
	        'Content-Type': 'application/json'
	    }
	}).then((response) => {
	    return response.json();
	})
        .then((data) => {
            if(data.solution.length !== 0){
                for(var j1 = 0; j1 < prob.curricula.length; j1++){
                    let sp = document.createElement("p");
                    sp.appendChild(document.createTextNode("Specializarea: " + prob.curricula[j1]));
                    let ulY = document.createElement("ul");
                    for(let j12 = 0; j12 < prob.years; j12++){
                        let y = document.createElement("p");
                        y.appendChild(document.createTextNode("Anul " + (j12+1)));
                        let ulP = document.createElement("ul");
                        for(let j2 = 0; j2 < prob.yearPeriods; j2++){
                            let l1 = document.createElement("li");
                            l1.appendChild(document.createTextNode("Perioada " + (j2+1) + ": "));
                            let c = "";
                            for(var j3 = 0; j3 < prob.courses.length; j3++){
                                if(prob.courses[j3].id_curriculum.includes(j1)
                                && data.solution[j3] == (j12 * prob.yearPeriods + j2)){
                                    c = c + prob.courses[j3].course_title + ", ";
                                }
                            }
                            c = c.slice(0, -2);
                            let courses = document.createElement("span");
                            courses.appendChild(document.createTextNode(c));
                            l1.appendChild(courses);
                            ulP.appendChild(l1);
                        }
                        let lineY = document.createElement("li");
                        lineY.appendChild(y);
                        lineY.appendChild(ulP);
                        ulY.appendChild(lineY);
                    }                    
                    let line = document.createElement("li");
                    line.appendChild(sp);
                    line.appendChild(ulY);
                    document.getElementById("sol").appendChild(line);
                }
                document.getElementById("costS").appendChild(document.createTextNode(data.solution[data.solution.length-3]));
                document.getElementById("costP").appendChild(document.createTextNode(data.solution[data.solution.length-2]));
                document.getElementById("costT").appendChild(document.createTextNode(data.solution[data.solution.length-1]));
                document.getElementById("solDiv").style.display = "block";
            }
            else{
                document.getElementById("noSol").style.display = "block";
            }
            document.querySelector("#send2 > i").style.visibility = "hidden";
	}).catch((error) => {
	    console.log(error);
	});
});

document.querySelector("form").onkeypress = function(e) {
  var key = e.charCode || e.keyCode || 0;     
  if (key === 13) {
    e.preventDefault();
  }
};

const ECButton = document.getElementById("emptyCurricula");

ECButton.addEventListener('click', event => {
    prob.curricula = [];
    prob.courses = [];
    
    document.getElementById("curricula").innerHTML = "";
});

const fileInput = document.getElementById('gbacpFile');
fileInput.addEventListener('change', function(e) {
    if(e.target.files.length){
        let file = e.target.files[0];
        let reader = new FileReader();
        reader.addEventListener('load', function (e2) {
            let resultData = e2.target.result; 
            parse(resultData);
        });

        reader.readAsBinaryString(file);
    }
});

function parse(data){
    let parsed = [];

    let newLinebrk = data.split("\n");
    for(let i = 0; i < newLinebrk.length; i++) {
        parsed.push(newLinebrk[i].split(" "));
    }
    prob.years = parseInt(parsed[1][1]);
    prob.yearPeriods = parseInt(parsed[2][1]);
    prob.minCourses = parseInt(parsed[5][1]);
    prob.maxCourses = parseInt(parsed[5][2]);
    prob.minCredits = 0;
    prob.maxCredits = 10000;
    prob.courses = [];
    prob.curricula = [];
    prob.preconditions = [];
    prob.preferences = [];
    
    let j1 = 10;
    let j2 = parseInt(parsed[3][1]);
    while(j2 > 0){
        let course = {
        "course_title": parsed[j1][0],
        "credits": parseInt(parsed[j1][1]),
        "id_curriculum": []
	};
	prob.courses.push(course);
        j1++;
        j2--;
    }
    j1 = j1 + 2;
    j2 = 0;
    let j3 = 0;
    while(j2 < parseInt(parsed[4][1])){
        prob.curricula.push(parsed[j1][0]);
        
        j3 = 2;
        while(j3 <= 1 + parseInt(parsed[j1][1])){
            prob.courses.every(function(obj){
                if(obj.course_title === parsed[j1][j3]){
                    obj.id_curriculum.push(j2);
                    return false;
                }
                return true;
            });
            j3++;
        }
        j1++;
        j2++;
    }
    
    j1 = j1 + 2;
    j2 = 0;
    while(j2 < parseInt(parsed[6][1])){
        let course1 = prob.courses.map(e => e.course_title).indexOf(parsed[j1][0]);
        let course2 = prob.courses.map(e => e.course_title).indexOf(parsed[j1][1]);
        let precId = (prob.preconditions.length === 0) ? 0 : prob.preconditions[prob.preconditions.length - 1].id + 1;
	let precondition = {
            "id": precId,
            "id_course1": course1,
            "id_course2": course2
	};
	prob.preconditions.push(precondition);
        j1++;
        j2++;
    }
    
    j1 = j1 + 2;
    j2 = 0;
    while(j2 < parseInt(parsed[7][1])){
	let prefId = (prob.preferences.length === 0) ? 0 : prob.preferences[prob.preferences.length - 1].id + 1;
        let course = prob.courses.map(e => e.course_title).indexOf(parsed[j1][0]);
	let preference = {
                "id": prefId,
		"id_course": course,
		"year_period": parseInt(parsed[j1][1])
	};
	prob.preferences.push(preference);
        j1++;
        j2++;
    }
}

const resetButton = document.getElementById("reset");

resetButton.addEventListener("click", _ => {
    prob = new Object();
    prob.curricula = [];
    prob.courses = [];
    prob.preconditions = [];
    prob.preferences = [];
    
    document.getElementById("curricula").innerHTML = "";
    document.getElementById("preconditions").innerHTML = "";
    document.getElementById("preferences").innerHTML = "";
    document.getElementById("sol").innerHTML = "";
    document.getElementById("costS").innerHTML = "Costul dezechilibrului: ";
    document.getElementById("costP").innerHTML = "Costul preferintelor: ";
    document.getElementById("costT").innerHTML = "Costul total al solutiei: ";
    document.getElementById("solDiv").style.display = "none";
    document.getElementById("noSol").style.display = "none";
    
    document.getElementById("fileForm").reset();
});

const sForm = document.getElementById("sForm");
sForm.addEventListener("click", _ => {
    document.getElementById("inputForm").style.display = "block";
    document.getElementById("sForm").style.color = "black";
    document.getElementById("fileForm").style.display = "none";
    document.getElementById("sFile").style.color = "grey";
});

const sFile = document.getElementById("sFile");
sFile.addEventListener("click", e => {
    document.getElementById("fileForm").style.display = "block";
    document.getElementById("sFile").style.color = "black";
    document.getElementById("inputForm").style.display = "none";
    document.getElementById("sForm").style.color = "grey";
});

const sendForm2 = document.getElementById('fileForm');

sendForm2.addEventListener('submit', event => {
	event.preventDefault();
        document.querySelector("#send2 > i").style.visibility = "visible";
	fetch("http://localhost:8080/gbacpApp/gbacpForm", {
	    method: 'post',
	    body: JSON.stringify(prob),
	    headers: {
	        'Accept': 'application/json',
	        'Content-Type': 'application/json'
	    }
	}).then((response) => {
	    return response.json();
	})
        .then((data) => {
            if(data.solution.length !== 0){
                for(var j1 = 0; j1 < prob.curricula.length; j1++){
                    let sp = document.createElement("p");
                    sp.appendChild(document.createTextNode("Specializarea: " + prob.curricula[j1]));
                    let ulY = document.createElement("ul");
                    for(let j12 = 0; j12 < prob.years; j12++){
                        let y = document.createElement("p");
                        y.appendChild(document.createTextNode("Anul " + (j12+1)));
                        let ulP = document.createElement("ul");
                        for(let j2 = 0; j2 < prob.yearPeriods; j2++){
                            let l1 = document.createElement("li");
                            l1.appendChild(document.createTextNode("Perioada " + (j2+1) + ": "));
                            let c = "";
                            for(var j3 = 0; j3 < prob.courses.length; j3++){
                                if(prob.courses[j3].id_curriculum.includes(j1)
                                && data.solution[j3] == (j12 * prob.yearPeriods + j2)){
                                    c = c + prob.courses[j3].course_title + ", ";
                                }
                            }
                            c = c.slice(0, -2);
                            let courses = document.createElement("span");
                            courses.appendChild(document.createTextNode(c));
                            l1.appendChild(courses);
                            ulP.appendChild(l1);
                        }
                        let lineY = document.createElement("li");
                        lineY.appendChild(y);
                        lineY.appendChild(ulP);
                        ulY.appendChild(lineY);
                    }                    
                    let line = document.createElement("li");
                    line.appendChild(sp);
                    line.appendChild(ulY);
                    document.getElementById("sol").appendChild(line);
                }
                document.getElementById("costS").appendChild(document.createTextNode(data.solution[data.solution.length-3]));
                document.getElementById("costP").appendChild(document.createTextNode(data.solution[data.solution.length-2]));
                document.getElementById("costT").appendChild(document.createTextNode(data.solution[data.solution.length-1]));
                document.getElementById("solDiv").style.display = "block";
            }
            else{
                document.getElementById("noSol").style.display = "block";
            }
            document.querySelector("#send2 > i").style.visibility = "hidden";
        }).catch((error) => {
	    console.log(error);
	});
});