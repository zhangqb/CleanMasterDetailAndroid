package es.ulpgc.eite.clean.mvp.masterdetail.detail;


import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.mvp.masterdetail.app.Mediator;
import es.ulpgc.eite.clean.mvp.masterdetail.app.ModelItem;
import es.ulpgc.eite.clean.mvp.masterdetail.app.Navigator;

public class DetailPresenter extends GenericPresenter
    <Detail.PresenterToView, Detail.PresenterToModel, Detail.ModelToPresenter, DetailModel>
    implements Detail.ViewToPresenter, Detail.ModelToPresenter,
    Detail.MasterToDetail, Detail.DetailToMaster {


  private boolean hideToolbar;

  /**
   * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
   * Responsible to initialize MODEL.
   * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
   * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onCreate(Detail.PresenterToView view) {
    super.onCreate(DetailModel.class, this);
    setView(view);

    // Debe llamarse al arrancar el detalle para fijar su estado inicial.
    // En este caso, este estado es fijado por el mediador en función de
    // los valores pasados desde el maestro
    Mediator app = (Mediator) getView().getApplication();
    app.startingDetailScreen(this);
  }

  /**
   * Operation called by VIEW after its reconstruction.
   * Always call {@link GenericPresenter#setView(ContextView)}
   * to save the new instance of PresenterToView
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onResume(Detail.PresenterToView view) {
    setView(view);

    // Verificamos si mostramos o no la barra de tareas cuando se produce un giro de pantalla
    // en función de la orientación actual de la pantalla
    if(configurationChangeOccurred()) {
      checkVisibility();
    }
  }

  /**
   * Helper method to inform Presenter that a onBackPressed event occurred
   * Called by {@link GenericActivity}
   */
  @Override
  public void onBackPressed() {

  }

  /**
   * Hook method called when the VIEW is being destroyed or
   * having its configuration changed.
   * Responsible to maintain MVP synchronized with Activity lifecycle.
   * Called by onDestroy methods of the VIEW layer, like: {@link GenericActivity#onDestroy()}
   *
   * @param isChangingConfiguration true: configuration changing & false: being destroyed
   */
  @Override
  public void onDestroy(boolean isChangingConfiguration) {
    super.onDestroy(isChangingConfiguration);

    // Si giramos la pantalla debemos fijar si la barra de tareas será visible o no
    if(isChangingConfiguration) {
      hideToolbar = !hideToolbar;
    }
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // View To Presenter ///////////////////////////////////////////////////////////////

  /**
   * Llamado por el mediador para actualizar el estado del maestro en caso de borrado
   *
   * @return item a ser eliminado de la lista del maestro
   */
  @Override
  public ModelItem getItem() {
    return getModel().getItem();
  }


  /**
   * Llamado ante una acción de borrado.
   * En este caso, además de notificar al maestro del nuevo estado es necesario finalizar el detalle
   */
  @Override
  public void onDeleteActionClicked() {
    Navigator app = (Navigator) getView().getApplication();
    app.backToMasterScreen(this);
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // Master To Detail ////////////////////////////////////////////////////////////////


  /**
   * Llamado por el mediador despues de fijar el estado inicial de la pantalla del detalle
   * pasado desde la pantalla del maestro
   */
  @Override
  public void onScreenStarted() {
    checkVisibility();
  }

  /**
   * Llamado por el mediador para fijar el estado del detalle pasado desde el maestro
   *
   * @param item seleccionado al hacer click en la lista del maestro
   */
  @Override
  public void setItem(ModelItem item) {
    getModel().setItem(item);
  }

  @Override
  public void setToolbarVisibility(boolean visible) {
    hideToolbar = !visible;
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // Detail To Master ////////////////////////////////////////////////////////////////


  /**
   * Llamado por el mediador ante una acción de borrado para finalizar el detalle
   */
  @Override
  public void destroyView() {
    getView().finishScreen();
  }

  /**
   * Llamado desde el mediador para recuperar el elemento de la lista que debe ser borrado
   * en el maestro cuando este se reinicie al finalizar el detalle
   *
   * @return item a borrar de la lista en el maestro
   */
  @Override
  public ModelItem getItemToDelete() {
    return getModel().getItem();
  }


  ///////////////////////////////////////////////////////////////////////////////////


  private void checkVisibility(){
    if(isViewRunning()) {
      if (hideToolbar) {
        getView().hideToolbar();
      }
    }
  }

}
