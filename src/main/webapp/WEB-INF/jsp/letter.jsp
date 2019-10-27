<!-- File: letter.jsp
     Author: Daniel Revillo Rey
     Last review: 16/10/2019
     Comment: This asks you for your ID number and then it calls 'letter.jsp'
     that calculates your ID's letter. The letter is shown after.
-->


<%--@elvariable id="msgID" type="String"--%>
<!DOCTYPE html>
<html lang="en">
<title>ID Letter Finder</title>
<body>
<div>
    <div>
    <h1>ID Letter Finder</h1>
    <h2>Insert your ID number so I can find the letter, don't be shy I won't use it for bad things ;)</h2>
    <br>
    <form action="./letter">
    <span> ID: <input type="text" name="ID" id="ID"><input type="submit" value="Send"></input>
    </span>
    </form>
    <br>
    <br>
    <h5>${msgID}</h5>
    <form>
        <input type="submit" value="Return" onclick="history.back()">
    </form>
    </div>
</div>
</body>
</html>