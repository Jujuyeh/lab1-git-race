<%-- IntelliJ specific linter annotations --%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="time" type="java.util.Date"--%>
<!DOCTYPE html>

<%@include  file="layout.jsp" %>

<kbd>${os} ${hostname} ${version} ${time} <br>
Last login: ${last_time} from ${last_ip}<br>
Your personal visits to the website: ${yourVisits}<br>
<br>Deadline is ${deadline}<br>
${daysLeft} days, ${hoursLeft} hours, ${minutesLeft} minutes and ${secondsLeft} seconds left<br>
<br>
Do you wanna know if you would survive thanos snap?? ->> <a href="./thanos">CLICK HERE</a><br>
<br>If you want to write a comment or view the comments, check the <a href="./comments">comment section</a>.<br>
<br>
Egg Game?? ->> <a href="./egg">CLICK HERE</a><br>
<br>
Next tramway arrivals at Campus Rio Ebro tramway stop <a href="./tramway">SHOW TIME</a><br>
${time_to_mago_oz}<br>
<p style="color: red; background-color: #333">${alert_mago_oz}</p>
${time_to_avenida_academia}<br>
<p style="color: red; background-color: #333">${alert_avenida_academia}</p>
<br>
user@${hostname}:~$ ${message}<br>
<br>

<p style="font-family:  WaltographRegular; font-size: large">Please do this test to find out which Disney character you are. <a href="./disney">START TEST</a></p><br>
<br>
Know if your NIP is a prime number <a href="./prime">CLICK</a><br>
<br>
user@${hostname}:~$ ${message}</kbd>

Last commit(${commitDate}): ${commitMessage}</kbd>

<%-- Links to JQuery JavaScript library as a webjar dependency --%>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
</center>
</div>
<div class="social-bar">
    <a href="https://www.facebook.com/DevCode.la" class="icon icon-facebook" target="_blank"></a>
    <a href="https://twitter.com/DevCodela" class="icon icon-twitter" target="_blank"></a>
    <a href="https://www.youtube.com/c/devcodela" class="icon icon-youtube" target="_blank"></a>
    <a href="https://www.instagram.com/devcodela/" class="icon icon-instagram" target="_blank"></a>
</div>
</body>


<div style="position: absolute; bottom: 5px; background-color: ${color}">
	${uagent}
	<button onclick="myFunction()">Who am i?</button>

	<script>
		function myFunction() {
			alert("${status}");
		}
	</script>
</div>


</html>
