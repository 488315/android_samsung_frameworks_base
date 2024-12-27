package com.android.systemui.controls.controller;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
