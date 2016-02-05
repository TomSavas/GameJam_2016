class MessageListener{
  
  MessageListener(Handler handler){
    PostBoxes.add(handler);
  }
  
  MessageListener(Button button){
  }
  
  void sendMessage(String message){
    for (Handler handler : PostBoxes){
      handler.newMessage(message);
    }
  }
  
}