<%--@elvariable id="msg" type="String"--%>
<%--@elvariable id="msg2" type="String"--%>
<%--@elvariable id="img" type="String"--%>
<%@ page isELIgnored ="false" %> 
<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>Thanos Snap</title>
    <link href="thanos_files/styles.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A demo site to use with Netlify Drop">
    <meta name="keywords" content="Thanos, JavaScript, Paradoy, Netlify Drop">
    <meta name="author" content="Rafael Conde">

    <link rel="icon" type="image/png" sizes="32x32" href="thanos_files/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="thanos_files/favicon-16x16.png">
  </head>
  <body>
    <div class="titan-background">
      <span class="stars stars-L"></span>
      <span class="stars stars-M"></span>
      <span class="stars stars-S"></span>
    </div>
    <div class="marvellous-container">
      <div class="header">
        <h1><span href="."class="title-marvel">Thanos</span> <span class="title-studios gems">Snap</span></h1>
        <h2>
            ${msg} <br>
            ${msg2}
          </h2>
          <br>
          <img src="${img}">
          <p></p>
          <form>
              <input type="submit" value="Go back!" onclick="history.back()">
            </form>
      </div>
      <div class="footer">
        <img src="thanos_files/glove.png" alt="thanos gauntlet but its like the classic emoji hands with an awful photoshop of stupid gems on it, because funny">
        <p>
          The styles (majority) are from <a target="_blank" href="https://thanosjs.org/">https://thanosjs.org/</a> <br>
        </p>
      </div>
    </div>
  

</body></html>