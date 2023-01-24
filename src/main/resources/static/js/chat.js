$(function(){

  let getMessageElement = function(message) {
        let item = $('<div class="message-item"></div>');
        let header = $('<div class="message-header"></div>');
        header.append($('<div class="datatime"> ' + message.datatime + ' </div>'));
        header.append($('<div class="username"> ' + message.username + '</div>'));
        let text = $('<div class="messagetext">' + message.text + '</div>');
        item.append(header, text);
        return item;
  };

  let updateMessages = function(){
    $('/message-list').html('Messages not');
     $.get('/message', {}, function(response) {
     if (response.message == 0) {
        return;
     };
     $('/message-list').html('');

                for(i in response) {
                     let element = getMessageElement(response[i]);
                     $('/message-list').append(element);
                }
     });
  };

  let initApplication = function()
  {
   $('.messages-and-users').css({display: 'flex'});
   $('.controls').css({display: 'flex'});

   $('.send-message').on('click', function()
   {
    let message = $('.new-message').val();
       $.post('/message', {message: message}, function(response){
           if(response.result) {
            $('.new-message').val('');
           } else {
             alert('Error! Repeat the mistake later');
           }
       })
   })
   //TODO: init to events

   setInterval(updateMessages, 1000);
  };

  let registerUser = function(name)
  {
   $.post('/auth', {name: name}, function(response) {
       if(response.result) {
         initApplication();
       }
    });
  };


  $.get('/init', {}, function(response)
  {
        if(!response.result)
        {
        let name = prompt('Enter Your Name');
        registerUser(name);
                return;
        }
        initApplication();
   });
});