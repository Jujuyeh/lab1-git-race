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
    <script>
      $.ajax({
        url: '/comments',
        type: 'GET',
        dataType: 'json',
        success: function (comments) {

          // Iterate though all the comments received
          comments.forEach(comment => {

            // Reformat the date string
            comment.date = comment.date.replace("T", " ");
            comment.date = comment.date.split('.')[0];

            // Show in the HTML parragraph identified by "comments" the comment
            document.getElementById("comments").innerHTML += "<font size=+1>" 
              + comment.comment + "</font>" + "<br>Written by " + comment.name 
              + " on " + comment.date + "<br><br>";
          });
        }, error(error) {
          console.log("Error: " + error);
        }
      });
    </script>

  </kbd>

  <form id="form" action="/comments" method="POST">
    <font color=white>Write a comment!</font> <br>
    <input type="text" name="comment" placeholder="Comment" required><br><br>
    <input type="text" name="name" placeholder="Your name" required><br><br>
    <input type="submit" value="Submit">
  </form>

  <script type="text/javascript">
    $('form').submit(function () {

      // Create a POST request to /comments
      $.ajax({
        type: "Post",
        url: $('form').attr('action'),
        data: $('form').serialize(),
        success: function (result) {

          // Reload the comment section
          window.location = 'comment_section';
        }
      });

      return false;
    });
  </script>

  <%-- Links to JQuery JavaScript library as a webjar dependency --%>
  <script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
</body>

</html>