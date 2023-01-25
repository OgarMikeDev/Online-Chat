$(function() {

  $('.send-message').on('click', function() {
    alert('You pressed the button');

    $.get('/init', {}, function(response) {
      $('.messages-list').html('');
      $('.users-list').html('');
      $('.new-message').html('');
    });

  });

});