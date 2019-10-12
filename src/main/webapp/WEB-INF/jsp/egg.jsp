<!DOCTYPE html>

<html lang="en">
<title>Egg Game</title>

<body style="background-color: #333" text="white">
    <pre style="display: table;color: white;background-color: transparent;border: none;">
    ______                                         
    |  ____|                                        
    | |__   __ _  __ _    __ _  __ _ _ __ ___   ___ 
    |  __| / _` |/ _` |  / _` |/ _` | '_ ` _ \ / _ \
    | |___| (_| | (_| | | (_| | (_| | | | | | |  __/
    |______\__, |\__, |  \__, |\__,_|_| |_| |_|\___|
            __/ | __/ |   __/ |                     
            |___/ |___/   |___/               
</pre>
    <h3>Click the egg and get the best score.</h3>
    <h4>${bestEgg}</h4>
    <button id="egg" style="display:inline-block; background-color: Transparent; border: none;">
        <img src="http://www.pngall.com/wp-content/uploads/2016/03/Egg-Free-Download-PNG.png" alt="Egg img"
            style="float:left;margin-right:0.5em;" height="200">
    </button>
    <br><br>

    <form action="./egg" method="post">
        Your score:
        <strong id="eggClick"> 0</strong><br>
        User name: <input type="text" name="username">
        <input id="eggValue" name="eggValue" type="hidden">
        <input type="submit" value="Go!">
        </input>
    </form>
    <br>${eggRes}<br>
    <script>
        var egg = document.getElementById("egg");
        var eggValue = document.getElementById("eggClick");
        var count = 0;
        egg.onclick = function () {
            count += 1;
            eggValue.innerHTML = count;
            document.getElementById("eggValue").value = count;
        };
    </script>


</body>

</html>