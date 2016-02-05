class LevelObjects{
  private HashMap<Integer, ArrayList<Zone>> levelsZone;
  private HashMap<Integer, ArrayList<Portal>> levelsPortal;
  
  LevelObjects(){
    levelsZone = new HashMap<Integer, ArrayList<Zone>>();
    levelsPortal = new HashMap<Integer, ArrayList<Portal>>();
    
    levelsZone.put(0, new ArrayList<Zone>());
    levelsZone.put(1, new ArrayList<Zone>());  
    (levelsZone.get(1)).add(new Zone(0, height-34, width, height, zoneType.black)); //Floor, it's neccessary for every level
    (levelsZone.get(1)).add(new Zone(745, 128, 865, height-34, zoneType.black));
    (levelsZone.get(1)).add(new Zone(0, 270, 10, 305, zoneType.black));
    (levelsZone.get(1)).add(new Zone(476, 270, 745, 305, zoneType.black));
    (levelsZone.get(1)).add(new Zone(87, 270, 419, 305, zoneType.black));
    (levelsZone.get(1)).add(new Zone(478, 128, 687, 147, zoneType.black));
    (levelsZone.get(1)).add(new Zone(419, 265, 478, height-34, zoneType.blue));
    (levelsZone.get(1)).add(new Zone(0, 0, 478, 270, zoneType.yellow));
    (levelsZone.get(1)).add(new Zone(478, 0, width, 128, zoneType.yellow));
    (levelsZone.get(1)).add(new Zone(478, 147, 687, 270, zoneType.green));
    (levelsZone.get(1)).add(new Zone(687, 124, 747, 274, zoneType.orange));
    (levelsZone.get(1)).add(new Zone(10, 270, 87, 305, zoneType.red));
    
    
    levelsPortal.put(0, new ArrayList<Portal>());
    levelsPortal.put(1, new ArrayList<Portal>());  
    (levelsPortal.get(1)).add(new Portal(250, height-130, 64, 100, portalType.blue_orange));
    (levelsPortal.get(1)).add(new Portal(600, height-130, 64, 100, portalType.red_green));
    (levelsPortal.get(1)).add(new Portal(130, 170, 64, 100, portalType.blue_orange));
    (levelsPortal.get(1)).add(new Portal(280, 170, 64, 100, portalType.yellow_purple));
    (levelsPortal.get(1)).add(new Portal(130+370, 170, 64, 100, portalType.blue_orange));
    (levelsPortal.get(1)).add(new Portal(280+370, 170, 64, 100, portalType.yellow_purple));
  }
  
  public ArrayList<Zone> getLevelZones(int level){
    return this.levelsZone.get(level);
  }
  
  public ArrayList<Portal> getLevelPortals(int level){
    return this.levelsPortal.get(level); 
  }
  
}  