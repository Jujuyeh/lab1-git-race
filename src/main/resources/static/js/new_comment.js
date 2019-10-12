$('form').submit(function () {
  console.log("Hello");
  // Create a POST request to /comments
    $.ajax({
      type: "Post",
      url: $('form').attr('action'),
      dataType: 'json',
      data: $('form').serialize(),
      success: function (result) {

        // Reload the comment section
        window.location = 'comment_section';
      }
    });

    return false;
});