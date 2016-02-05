class GUI{

  private HashMap <Integer, ArrayList<Object> > objectsInLayers;

  GUI(){
    objectsInLayers = new HashMap<Integer, ArrayList<Object>>();
  }

  public void attach(Object object, int layer){
    if(!this.objectsInLayers.containsKey(layer)) this.objectsInLayers.put(layer, new ArrayList<Object>());
    (this.objectsInLayers.get(layer)).add(object);
  }
  
  public ArrayList<Object> getLayerObjects(int layer){
    return (this.objectsInLayers).get(layer);
  }
  
  public void paint(){
    for(int key : (this.objectsInLayers).keySet()){
      for(Object object : (this.objectsInLayers).get(key)){
        object.paint(); 
      }
    }
  }
  
}