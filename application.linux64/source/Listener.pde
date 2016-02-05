ArrayList <Button> ButtonsListener;
ArrayList <Handler> PostBoxes;

void listen(){
  ButtonsListener = new ArrayList<Button>();
  PostBoxes = new ArrayList<Handler>();
}

void mouseListener(){
    if(mousePressed){
      for (Button button : ButtonsListener){
        if (button.mouseOver()){
          button.sendMessage(button.getMessage());
        }
      } 
    }
}