<%-- IntelliJ specific linter annotations --%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="time" type="java.util.Date"--%>
<!DOCTYPE html>

<html lang="en">
<title>Hello world</title>
<%-- Links to Bootstrap CSS framework as a webjar dependency --%>
<link rel="stylesheet" type="text/css"
      href="webjars/bootstrap/3.3.5/css/bootstrap.min.css"/>
<body style="background-color: #333">
<pre style="display: table;color: white;background-color: transparent;border: none;">
.___                  __      __      ___.      ____ ________ 
|   | ____    ____   /  \    /  \ ____\_ |__   /_   /   __   \
|   |/    \  / ___\  \   \/\/   // __ \| __ \   |   \____    /
|   |   |  \/ /_/  >  \        /\  ___/| \_\ \  |   |  /    / 
|___|___|  /\___  /    \__/\  /  \___  >___  /  |___| /____/  
         \//_____/          \/       \/    \/                 
</pre>
<kbd>${os} ${hostname} ${version} ${time} <br>
Last login: ${last_time} from ${last_ip}<br>
<br>Deadline is ${deadline}<br>
${daysLeft} days, ${hoursLeft} hours, ${minutesLeft} minutes and ${secondsLeft} seconds left<br>
<br>
user@${hostname}:~$ ${message}</kbd>
<%-- Links to JQuery JavaScript library as a webjar dependency --%>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
</body>

</html>