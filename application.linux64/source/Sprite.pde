class Sprite extends Object{
  
  private int currentX,
              h, w,
              imgH, imgW,
              frames, currentFrame;
  private PImage img;
  private boolean visible, paused;
  private ArrayList<PImage> framesList;

  Sprite(int x, int y, int w, int h, int frames, String path){
    super(x, y);
    framesList = new ArrayList<PImage>(); 
    this.loadImg(w, h, frames, path);
  }
  
  public void loadImg(int w, int h, int frames, String path){
    this.w = w;
    this.h = h;   
    this.frames = frames;
    this.img = loadImage(path);
    this.imgH = img.height;
    this.imgW = img.width/frames;
    this.currentFrame = 0;
    this.visible = true;
    this.setLayer(0);
    fillFrames(w, h, this.img, frames);
  }
  
  public void fillFrames(int w, int h, PImage img, int frames){
    for(int i = 0; i < frames; ++i){
      this.framesList.add(i, img.get(i*this.imgW, 0, this.imgW, this.imgH));     
      this.framesList.get(i).resize(w, h); 
    }
  }

  public int getW(){
    return this.w;
  }
  
  public int getH(){
    return this.h;
  }
  
  public boolean isVisible(){
    return this.visible;
  }

  public boolean isPaused(){
    return this.paused;
  }
  
  public void setVisible(boolean visible){
    this.visible = visible;
  }
  
  public void setPaused(boolean paused){
    this.paused = paused;
  }

  public void setFrame(int frame){
     this.currentFrame = frame;
     this.currentX = this.currentFrame * this.imgW;
  }

  public void update(){
    if(paused) return;
    this.currentFrame = this.currentFrame+1 == this.frames ? 0 : ++this.currentFrame;
    this.currentX = this.currentFrame * this.imgW;
  }
 
  public void paint(){  
    if(this.visible){
      image(this.framesList.get(this.currentFrame), this.getX(), this.getY());
      
      this.overObject();
      this.controls();
      
      this.update();
    }
  }

  public void controls(){}
  public void overObject(){}
  
}