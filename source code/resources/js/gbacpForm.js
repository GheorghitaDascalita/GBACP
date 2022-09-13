var prob = new Object();

prob.curricula = []
prob.courses = []
prob.preconditions = []
prob.preferences = []

const curriculaButton = document.getElementById('add_curriculum');

curriculaButton.addEventListener('click', _  => {
	
	let li = document.createElement("li")
	li.id = "curriculum" + prob.curricula.length
	li.appendChild(document.createTextNode(document.getElementById("curriculum_title").value))
	let span = document.createElement("span")
	span.innerHTML="&times;"
	span.classList.add("close");
	li.appendChild(span)
	let ul = document.createElement("ul");
	ul.id = "list" + prob.curricula.length
	li.appendChild(ul)
	let list = document.getElementById("curricula") 
	list.append(li)
	
	prob.curricula.push(document.getElementById("curriculum_title").value)
	
	document.getElementById("curriculum_title").value = ""
})

const courseButton = document.getElementById('add_course');

courseButton.addEventListener('click', _ => {
	let li = document.createElement("li")
	li.appendChild(document.createTextNode("(" + (prob.courses.length + 1) + ") " + document.getElementById("course_title").value))
	let span = document.createElement("span")
	span.innerHTML="&times;"
	span.classList.add("close");
	li.appendChild(span)
	let list = document.getElementById("list" + (document.getElementById("id_curriculum").value-1)) 
	list.append(li)
	
	
	let course = {
		"courseTitle": document.getElementById("course_title").value,
		"credits": document.getElementById("credits").value,
		"id_curriculum": (document.getElementById("id_curriculum").value - 1)
	}
	prob.courses.push(course)
	
	document.getElementById("course_title").value = ""
	document.getElementById("credits").value = ""
	document.getElementById("id_curriculum").value = ""
})

const preconditionsButton = document.getElementById('add_precondition');

preconditionsButton.addEventListener('click', _ => {
	let li = document.createElement("li")
	li.appendChild(document.createTextNode("Cursul " + document.getElementById("id_course1").value 
	+ " inaintea cursului " + document.getElementById("id_course2").value))
	let span = document.createElement("span")
	span.innerHTML="&times;"
	span.classList.add("close");
	li.appendChild(span)
	let list = document.getElementById("preconditions") 
	list.append(li)
	
	
	let precondition = {
		"id_course1": (document.getElementById("id_course1").value - 1),
		"id_course2": (document.getElementById("id_course2").value - 1)
	}
	prob.preconditions.push(precondition)
	
	document.getElementById("id_course1").value = ""
	document.getElementById("id_course2").value = ""
})

const preferencesButton = document.getElementById('add_preference');

preferencesButton.addEventListener('click', _ => {
	let li = document.createElement("li")
	li.appendChild(document.createTextNode("Cursul " + document.getElementById("id_course").value 
	+ " sa nu fie predat in perioada " + document.getElementById("year_period").value + " a anului"))
	let span = document.createElement("span")
	span.innerHTML="&times;"
	span.classList.add("close");
	li.appendChild(span)
	let list = document.getElementById("preferences") 
	list.append(li)
	
	
	let preference = {
		"id_course": (document.getElementById("id_course").value - 1),
		"year_period": (document.getElementById("year_period").value - 1)
	}
	prob.preferences.push(preference)
	
	document.getElementById("id_course").value = ""
	document.getElementById("year_period").value = ""
})


const sendButton = document.querySelector('form');

sendButton.addEventListener('submit', event => {
	event.preventDefault();
	
	prob.years = document.querySelector("input[name=years]").value;
	prob.yearPeriods = document.querySelector("input[name=year_periods]").value;
	prob.minCredits = document.querySelector("input[name=min_credits]").value;
	prob.maxCredits = document.querySelector("input[name=max_credits]").value;
	prob.minCourses = document.querySelector("input[name=min_courses]").value;
	prob.maxCourses = document.querySelector("input[name=max_courses]").value;
	
	document.querySelector("input[name=years]").value = ""
	document.querySelector("input[name=year_periods]").value = ""
	document.querySelector("input[name=min_credits]").value = ""
	document.querySelector("input[name=max_credits]").value = ""
	document.querySelector("input[name=min_courses]").value = ""
	document.querySelector("input[name=max_courses]").value = ""
	
	
	fetch("http://localhost:8080/GBACP/gbacpForm", {
	    method: 'post',
	    body: JSON.stringify(prob),
	    headers: {
	        'Accept': 'application/json',
	        'Content-Type': 'application/json'
	    }
	}).then((res) => {
	    if (res.status === 201 || res.status === 200) {
	        console.log("Post successfully created!")
	    }
	}).catch((error) => {
	    console.log(error)
	})
});