console.log("Hello");
$.ajax({
    url: '/comments',
    type: 'GET',
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