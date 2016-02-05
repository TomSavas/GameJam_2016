class Player extends Sprite {

  private boolean moveUp = false, 
    moveDown = false, 
    moveRight = true, 
    moveLeft = true, 
    gravitationEnabled = true;
  int speed = 5;

  Player(int x, int y, int w, int h, int frames, String path) {
    super(x, y, w, h, frames, path);
  }

  public boolean canMoveUp() {
    return this.moveUp;
  }

  public boolean canMoveDown() {
    return this.moveDown;
  }

  public boolean canMoveRight() {
    return this.moveRight;
  }

  public boolean canMoveLeft() {
    return this.moveLeft;
  }

  public boolean isGravitationEnabled() {
    return this.gravitationEnabled;
  }

  public void setMove(boolean moveUp, boolean moveDown, boolean moveRight, boolean moveLeft) {
    this.moveUp = moveUp;
    this.moveDown = moveDown;
    this.moveRight = moveRight;
    this.moveLeft = moveLeft;
  }

  public void setGravitationEnabled(boolean gravitationEnabled) {
    this.gravitationEnabled = gravitationEnabled;
  }

  public void setMoveUp(boolean moveUp) {
    this.moveUp = moveUp;
  }

  public void setMoveDown(boolean moveDown) {
    this.moveDown = moveDown;
  }

  public void setMoveRight(boolean moveRight) {
    this.moveRight = moveRight;
  }

  public void setMoveLeft(boolean moveLeft) {
    this.moveLeft = moveLeft;
  }

  public void moveX(int step) {
    if (!this.canMoveRight() && step > 0) return;
    if (!this.canMoveLeft() && step < 0) return;
    this.setX(this.getX() + step); 
    if (this.getX() < 0 || this.getX()+this.getW() > width) {
      this.setX(this.getX() - step); 
      return;
    }
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      if (this.getX()+this.getW() > zone.getX1() && this.getX() < zone.getX1() && this.getY() >= zone.getY1() && this.getY() < zone.getY2() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step > 0) {
        this.setX(this.getX() - step); 
        break;
      }
      if (this.getX() < zone.getX2() && this.getX()+this.getW() > zone.getX2() && this.getY() < zone.getY2() && this.getY() >= zone.getY1() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step < 0) {
        this.setX(this.getX() - step); 
        break;
      }
    }
  }

  public void moveY(int step) {
    if (!this.canMoveUp() && step < 0) return;
    if (!this.canMoveDown() && step > 0) return;
    this.setY(this.getY() + step); 
    if (this.getY() < 0 || this.getY()+this.getH() > height) {
      this.setY(this.getY() - step); 
      return;
    }
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      if (this.getY() < zone.getY2() && this.getY() + this.getH() > zone.getY2() && this.getX() >= zone.getX1() &&  this.getX() < zone.getX2() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step < 0) {
        this.setY(this.getY() - step);
        break;
      }
      if (this.getY() + this.getH() > zone.getY1() && this.getY() < zone.getY1() && this.getX() >= zone.getX1() &&  this.getX() < zone.getX2() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step > 0) {
        this.setY(this.getY() - step);
        break;
      }
    }
  }

  void newMessage(String message) {
    if (message.equals("left")) {
      moveX(-10);
    } else if (message.equals("right")) {
      moveX(10);
    };
  }

  public void gravitation() {
    this.setY(this.getY() + 10);
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      //if(this.getY()+this.getH() >= zone.getY1() && (this.getX() >= zone.getX1() || this.getX()+this.getW() >= zone.getX2()) ){
      if (this.getY() > zone.getY1()) continue;
      if (( (this.getX() > zone.getX1() && this.getX() < zone.getX2()) || (this.getX()+this.getW() < zone.getX2() && this.getX()+this.getW() > zone.getX1())) && this.getY()+this.getH() >= zone.getY1() && (zone.getZoneType() == zoneType.black || zone.getZoneType() == zoneType.blue || zone.getZoneType() == zoneType.green) ) {
        this.setY(this.getY() - 10); 
        break;
      }
    }
  }

  @Override
    public void controls() {
    if(this.getX() >= shrine.getX() && this.getY()+this.getH() > shrine.getY()){
      noLoop();
      fill(255, 0, 0);
      textSize(144);
      text("GG m8", 100, 150);
    }
    if (this.isGravitationEnabled()) gravitation();
    if (keyPressed) {
      if (key == 'a' || key == 'A') this.moveX(-5);
      else if (key == 'd' || key == 'D') this.moveX(5);
      else if (key == CODED) {
        if (keyCode == LEFT) this.moveX(-5);
        else if (keyCode == RIGHT) this.moveX(5);
      }
    }
  }

  @Override 
    public void overObject() {
    for (Portal portal : levelObjects.getLevelPortals(currentLevel)) {
      if (portal.isUsed()) continue;
      if (keyPressed && key == 32 && this.getX()+this.getW()/2 >= portal.getX() && this.getX()+this.getW()/2 < portal.getX()+portal.getW() && this.getY()+this.getH()/2 > portal.getY() && this.getY()+this.getH()/2 < portal.getY()+portal.getW()) {  
        for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
          switch(portal.getPortalType()) {
          case red_green:
            if (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.red)
              zone.invert();
            break;
          case yellow_purple:
            if (zone.getZoneType() == zoneType.yellow || zone.getZoneType() == zoneType.purple)
              zone.invert();
            break;
          case blue_orange:
            if (zone.getZoneType() == zoneType.blue || zone.getZoneType() == zoneType.orange)
              zone.invert();
            break;
          }
        }
        portal.setUsed();
      }
    }

    //println(this.getX() + "  " + (this.getY()+this.getH()));
    this.setMoveUp(true);
    this.setMoveDown(true);
    this.setMoveRight(true);
    this.setMoveLeft(true);
    this.setGravitationEnabled(true);
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      if (this.getX() > zone.getX1() && this.getX()+this.getW() < zone.getX2() && this.getY()+this.getH()-2 < zone.getY2() && this.getY()+this.getH()-2 > zone.getY1()) {
        this.speed = 5;
        println(zone.getZoneType());
        switch(zone.getZoneType()) {
        case blue:
          this.setGravitationEnabled(false);
          this.setMoveUp(true);
          this.setMoveDown(false);
          this.setMoveRight(false);
          this.setMoveLeft(false);
          this.moveY(-5);
          return;
        case orange:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(true);
          this.setMoveLeft(true);
          return;
        case yellow:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(false);
          this.setMoveLeft(true);
          return;
        case purple:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(true);
          this.setMoveLeft(false);
          return;
        case red:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(true);
          this.setMoveLeft(true);
          return;
        case green:
          this.setMoveUp(false);
          this.setMoveDown(false);
          this.setMoveRight(false);
          this.setMoveLeft(false);
          return;
        case black:
          this.setMoveUp(false);
          this.setMoveDown(false);
          this.setMoveRight(false);
          this.setMoveLeft(false);
          return;
        }
      }
    }
  }
}