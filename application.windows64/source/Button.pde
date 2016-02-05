class Button extends Sprite{
  
  private String message = "";
   
  private MessageListener messageListener;
 
  Button(int x, int y, int w, int h, int frames, String path){
    super(x, y, w, h, frames, path);
    ButtonsListener.add(this);
    messageListener = new MessageListener(this);
  }

  public boolean mouseOver(){
    if (this.getX() < mouseX && mouseX < this.getX() + this.getW() && this.getY() < mouseY && mouseY < this.getY() + this.getH()){
      return true;
    }
    else return false;
  }
  
  public String getMessage(){
   return this.message; 
  }
  
  public void setMessage(String message){
    this.message = message;
  }
  
  void sendMessage(String message){
    messageListener.sendMessage(message);
  }
   
  void newMessage(String message){}
  
}