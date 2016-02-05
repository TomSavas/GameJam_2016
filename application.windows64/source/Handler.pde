class Handler extends Object{
  
  private MessageListener messageListener;
  
  Handler(){
    messageListener = new MessageListener(this);
  }
    
  void newMessage(String message){
    if (message.equals("lvl1")) {
      loadLevel(1);
      stage = Stages.lvl1;
    }
  }
  
}