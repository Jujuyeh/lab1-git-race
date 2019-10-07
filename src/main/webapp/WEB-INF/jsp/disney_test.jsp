<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<!-- https://freefrontend.com/css-radio-buttons/ -->
<link href="customRadio.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" media="screen" href="https://fontlibrary.org/face/waltograph" type="text/css"/>
<head>
    <c:choose>
        <c:when test = "${question == 0}">
            <form action = "./disney" class="form">
                <h2 style="font-family:  WaltographRegular">Which is your favourite season?</h2><br>
                <div class="inputGroup">
                    <input id="radio1" name="answer" type="radio" value="1"/>
                    <label for="radio1">Winter</label>
                </div>
                <div class="inputGroup">
                    <input id="radio2" name="answer" type="radio" value="2"/>
                    <label for="radio2">Spring</label>
                </div>
                <div class="inputGroup">
                    <input id="radio3" name="answer" type="radio" value="3"/>
                    <label for="radio3">Summer</label>
                </div>
                <div class="inputGroup">
                    <input id="radio4" name="answer" type="radio" value="4"/>
                    <label for="radio4">Autumn</label>
                </div>
                
                <input style="border-radius: 25px; background: rgb(93, 209, 112)" type = "submit" value = "Next" />
            </form>
        </c:when>
        <c:when test = "${question == 1}">
            <form action = "./disney" class="form">
                <h2 style="font-family:  WaltographRegular">How do you define yourself?</h2><br>
                <div class="inputGroup">
                    <input id="radio1" name="answer" type="radio" value="1"/>
                    <label for="radio1">Introvert</label>
                </div>
                <div class="inputGroup">
                    <input id="radio2" name="answer" type="radio" value="2"/>
                    <label for="radio2">Funny</label>
                </div>
                <div class="inputGroup">
                    <input id="radio3" name="answer" type="radio" value="3"/>
                    <label for="radio3">Fierce</label>
                </div>
                <div class="inputGroup">
                    <input id="radio4" name="answer" type="radio" value="4"/>
                    <label for="radio4">Wise</label>
                </div>
                
                <input style="border-radius: 25px; background: rgb(93, 209, 112)" type = "submit" value = "Next" />
            </form>
        </c:when>
        <c:when test = "${question == 2}">
            <form action = "./disney" class="form">
                <h2 style="font-family:  WaltographRegular">Where do you prefer to travel?</h2><br>
                <div class="inputGroup">
                    <input id="radio1" name="answer" type="radio" value="1"/>
                    <label for="radio1">Russia</label>
                </div>
                <div class="inputGroup">
                    <input id="radio2" name="answer" type="radio" value="2"/>
                    <label for="radio2">China</label>
                </div>
                <div class="inputGroup">
                    <input id="radio3" name="answer" type="radio" value="3"/>
                    <label for="radio3">Kenya</label>
                </div>
                <div class="inputGroup">
                    <input id="radio4" name="answer" type="radio" value="4"/>
                    <label for="radio4">Mexico</label>
                </div>
                
                <input style="border-radius: 25px; background: rgb(93, 209, 112)" type = "submit" value = "Next" />
            </form>
        </c:when>
        <c:when test = "${question == 3}">
            <form action = "./disney" class="form">
                <h2 style="font-family:  WaltographRegular">Which is your motto?</h2><br>
                <div class="inputGroup">
                    <input id="radio1" name="answer" type="radio" value="1"/>
                    <label for="radio1">Let it go!</label>
                </div>
                <div class="inputGroup">
                    <input id="radio2" name="answer" type="radio" value="2"/>
                    <label for="radio2">Oooh! All right, that's it!</label>
                </div>
                <div class="inputGroup">
                    <input id="radio3" name="answer" type="radio" value="3"/>
                    <label for="radio3">Hakuna Matata!</label>
                </div>
                <div class="inputGroup">
                    <input id="radio4" name="answer" type="radio" value="4"/>
                    <label for="radio4">You know your path, child, now follow it.</label>
                </div>
                
                <input style="border-radius: 25px; background: rgb(93, 209, 112)" type = "submit" value = "Next" />
            </form>
        </c:when>
    </c:choose>
</head>