package es.ulpgc.eite.clean.mvp.masterdetail.app;

/**
 * Created by Luis on 19/11/16.
 */

public class ModelItem {

  private String id;
  private String title;
  private String details;

  public ModelItem(String id, String title, String details) {
    this.id = id;
    this.title = title;
    this.details = details;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return title;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ModelItem){
      ModelItem item = (ModelItem) obj;
      if(item.getId() == getId()){
        return true;
      }
    }
    return false;
  }
}
