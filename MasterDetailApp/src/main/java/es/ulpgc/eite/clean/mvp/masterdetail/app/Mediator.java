package es.ulpgc.eite.clean.mvp.masterdetail.app;


import es.ulpgc.eite.clean.mvp.masterdetail.detail.Detail;
import es.ulpgc.eite.clean.mvp.masterdetail.master.Master;

public interface Mediator {

  void startingMasterScreen(Master.ToMaster presenter);
  void resumingMasterScreen(Master.DetailToMaster presenter);
  void startingDetailScreen(Detail.MasterToDetail presenter);
}
