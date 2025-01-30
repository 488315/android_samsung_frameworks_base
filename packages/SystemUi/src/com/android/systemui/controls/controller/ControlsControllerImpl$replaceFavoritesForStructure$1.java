package com.android.systemui.controls.controller;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsControllerImpl$replaceFavoritesForStructure$1 implements Runnable {
    public final /* synthetic */ StructureInfo $structureInfo;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$replaceFavoritesForStructure$1(StructureInfo structureInfo, ControlsControllerImpl controlsControllerImpl) {
        this.$structureInfo = structureInfo;
        this.this$0 = controlsControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Favorites favorites = Favorites.INSTANCE;
        StructureInfo structureInfo = this.$structureInfo;
        favorites.getClass();
        Favorites.replaceControls(structureInfo);
        this.this$0.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
    }
}
