<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>GBACP</title>
        <link href="resources/css/gbacpForm.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <a href="${pageContext.request.contextPath}/logout">Deconecteaza-te</a>
        <div>
            <h1 id="id${user.id}">GBACP</h1>
            <h2>Datele GBACP:</h2>
            <span id="sForm" type="button">Formular</span> | <span id="sFile" type="button">Fisier</span>
            <form id="inputForm" action="">
                <label for="years">Numarul de ani: </label>
                <input type="number" name="years" required>
                <label for="year_periods">Numarul de perioade ale unui an: </label>
                <input type="number" name="year_periods" required>
                <label for="min_credits">Numarul total de credite minim: </label>
                <input type="number" name="min_credits" required>
                <label for="max_credits">Numarul total de credite maxim: </label>
                <input type="number" name="max_credits" required>
                <label for="min_courses">Numarul minim de cursuri: </label>
                <input type="number" name="min_courses" required>
                <label for="max_courses">Numarul maxim de cursuri: </label>
                <input type="number" name="max_courses" required>

                <p>Specializari <button id="emptyCurricula" class="close" type="button">&times;</button></p>
                <ol id="curricula">
                </ol>

                <label for="curriculum_title">Titlul specializarii: </label>
                <input id="curriculum_title" type="text">
                <button id="add_curriculum" class="button" type="button">Adauga specializare</button>

                <label for="course_title">Titlul cursului: </label>
                <input id="course_title" type="text">
                <label for="credits">Numarul de credite: </label>
                <input id="credits" type="number">
                <label for="id_curriculum">Numarul specializarii: </label>
                <input id="id_curriculum" type="number">
                <button id="add_course" class="button" type="button">Adauga curs</button>

                <label for="id_course_old">Numarul cursului: </label>
                <input id="id_course_old" type="number">
                <label for="id_curriculum_old">Numarul specializarii: </label>
                <input id="id_curriculum_old" type="number">
                <button id="add_course_old" class="button" type="button">Adauga curs existent</button>

                <p>Preconditii</p>
                <ul id="preconditions">
                </ul>
                <label for="id_course1">Numarul primului curs: </label>
                <input id="id_course1" type="number">
                <label for="id_course2">Numarul celui de-al doilea curs: </label>
                <input id="id_course2" type="number">
                <button id="add_precondition" class="button" type="button">Adauga preconditie</button>


                <p>Preferinte</p>
                <ul id="preferences">
                </ul>
                <label for="id_course">Numarul cursului: </label>
                <input id="id_course" type="number">
                <label for="year_period">Perioada anului: </label>
                <input id="year_period" type="number">
                <button id="add_preference" class="button" type="button">Adauga preferinta</button>

                <button id="send"><i class="fa fa-spinner fa-spin"></i> Calculeaza</button>
            </form>
            <form id="fileForm" action="">
                <label for="gbacpFile">Incarca un fisier </label>
                <input type="file" id="gbacpFile" name="gbacpFile" required>
                <button id="send2"><i class="fa fa-spinner fa-spin"></i> Calculeaza</button>
            </form>
            <div id="solDiv">
                <h2>Solutia GBACP:</h2>
                <ul id="sol">
                </ul>
                <p id="costS">Costul dezechilibrului: </p>
                <p id="costP">Costul preferintelor: </p>
                <p id="costT">Costul total al solutiei: </p>
            </div>
            <p id="noSol">Nicio solutie gasita in timpul setat</p>
            <button id="reset" class="button" type="button">Reseteaza</button>
        </div>
        <script src= "resources/js/gbacpForm.js"></script>
    </body>
</html>