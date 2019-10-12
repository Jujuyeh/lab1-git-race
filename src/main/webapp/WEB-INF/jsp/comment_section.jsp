<!DOCTYPE html>

<html lang="en">
<title>Comment section</title>
<%-- Links to Bootstrap CSS framework as a webjar dependency --%>
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.5/css/bootstrap.min.css" />

<body style="background-color: #333">
  <pre style="display: table;color: white;background-color: transparent;border: none;">
.___                  __      __      ___.      ____ ________ 
|   | ____    ____   /  \    /  \ ____\_ |__   /_   /   __   \
|   |/    \  / ___\  \   \/\/   // __ \| __ \   |   \____    /
|   |   |  \/ /_/  >  \        /\  ___/| \_\ \  |   |  /    / 
|___|___|  /\___  /    \__/\  /  \___  >___  /  |___| /____/  
         \//_____/          \/       \/    \/                 

</pre>
  <kbd>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <h2>
      <font color=white>Comments</font>
    </h2>
    <p id="comments"></p>
    <script src="js/get_comments.js"></script>

  </kbd>

  <form id="form" action="/comments" method="POST">
    <font color=white>Write a comment!</font> <br>
    <input type="text" name="comment" placeholder="Comment" required><br><br>
    <input type="text" name="name" placeholder="Your name" required><br><br>
    <input type="submit" value="Submit">
  </form>

  <script src="js/new_comment.js"></script>

  <%-- Links to JQuery JavaScript library as a webjar dependency --%>
  <script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
</body>

</html>