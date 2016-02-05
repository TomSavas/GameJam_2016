public GUI gameGUI;

public Sprite shrine;
public Player player;
public LevelObjects levelObjects;
public ArrayList<Level> levels;
public int currentLevel;

void loadLevel(int level){
  levels = new ArrayList<Level>();
  levelObjects = new LevelObjects();
  gameGUI = new GUI();
  currentLevel = level;
  levels.add(0, new Level());
  //Background
  levels.add(level, new Level(0, 0, width, height, 1, "level_1_background.png", levelObjects.getLevelZones(level), levelObjects.getLevelPortals(level)));
  gameGUI.attach(levels.get(level), 1);
  //Background
 
  //Player
  player = new Player(20, height-100-50, 53, 100, 18, "player.png");
  //player.setPaused(true);
  //player.setFrame(6);
  gameGUI.attach(player, 10); //Player must be on top
  //Player
  
  shrine = new Sprite(width - 120, height-100-35, 75, 100, 1, "shrine.png");
  
  gameGUI.attach(shrine, 1);
  
  musicPlayer = minim.loadFile("Main_test4.mp3");
  musicPlayer.play();
  musicPlayer.loop();
}