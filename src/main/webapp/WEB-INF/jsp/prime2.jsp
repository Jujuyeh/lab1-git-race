<%--@elvariable id="msg" type="String"--%>
<%--@elvariable id="primeFactors" type="String"--%>
<!DOCTYPE html>
<html lang="en">
<title>Prime Number Checker</title>
<body>
<div>
    <div>
    <h1>Prime Number Checker</h1>
    <h2>${msg}<br></h2>
    <h3>Prime Factors: ${primeFactors}<br></h3>
        <br>
        <p></p>
        <form>
            <input type="submit" value="Go back!" onclick="history.back()">
        </form>
    </div>
</div>
</body>
</html>