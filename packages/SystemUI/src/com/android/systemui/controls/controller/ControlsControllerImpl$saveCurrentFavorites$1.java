package com.android.systemui.controls.controller;

public final class ControlsControllerImpl$saveCurrentFavorites$1 implements Runnable {
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$saveCurrentFavorites$1(ControlsControllerImpl controlsControllerImpl) {
        this.this$0 = controlsControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = this.this$0.persistenceWrapper;
        Favorites.INSTANCE.getClass();
        controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
    }
}
