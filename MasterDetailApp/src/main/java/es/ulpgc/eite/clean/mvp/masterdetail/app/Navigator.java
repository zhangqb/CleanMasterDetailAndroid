package es.ulpgc.eite.clean.mvp.masterdetail.app;


import es.ulpgc.eite.clean.mvp.masterdetail.detail.Detail;
import es.ulpgc.eite.clean.mvp.masterdetail.master.Master;

public interface Navigator {

  void backToMasterScreen(Detail.DetailToMaster presenter);
  void goToDetailScreen(Master.MasterToDetail presenter);
}
