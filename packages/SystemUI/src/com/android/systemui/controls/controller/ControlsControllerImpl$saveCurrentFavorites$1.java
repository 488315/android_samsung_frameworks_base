package com.android.systemui.controls.controller;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
