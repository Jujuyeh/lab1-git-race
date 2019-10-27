<%--@elvariable id="msg" type="String"--%>
<%--@elvariable id="primeFactors" type="String"--%>
<!DOCTYPE html>
<html lang="en">
<title>Rock, Paper or Scissors</title>
<body>
<div>
    <div>
    <h1>Resultado:</h1>
    <h2>${msg}<br></h2>
    <h3>${msg2}<br></h3>
        <br>
          <img src="${img}">
          <p></p>
        <form>
            <input type="submit" value="Go back!" onclick="history.back()">
        </form>
    </div>
</div>
</body>
</html>