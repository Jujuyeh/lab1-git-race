<%-- IntelliJ specific linter annotations --%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="time" type="java.util.Date"--%>
<!DOCTYPE html>

<%@include  file="layout.jsp" %>

<kbd>${os} ${hostname} ${version} ${time} <br>
Last login: ${last_time} from ${last_ip}<br>
<br>Deadline is ${deadline}<br>
${daysLeft} days, ${hoursLeft} hours, ${minutesLeft} minutes and ${secondsLeft} seconds left<br>
<br>
Do you wanna know if you would survive thanos snap?? ->> <a href="./thanos">CLICK HERE</a><br>
<br>
user@${hostname}:~$ ${message}</kbd>
<%-- Links to JQuery JavaScript library as a webjar dependency --%>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
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