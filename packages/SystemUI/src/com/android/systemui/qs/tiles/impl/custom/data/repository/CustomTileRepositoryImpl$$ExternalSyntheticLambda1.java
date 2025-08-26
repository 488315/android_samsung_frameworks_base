package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.service.quicksettings.Tile;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTileRepositoryImpl$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ Tile f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Tile tile = this.f$0;
        Tile tile2 = (Tile) obj;
        int i = CustomTileRepositoryImpl.$r8$clinit;
        if (tile.getIcon() != null) {
            tile2.setIcon(tile.getIcon());
        }
        if (tile.getCustomLabel() != null) {
            tile2.setLabel(tile.getCustomLabel());
        }
        if (tile.getSubtitle() != null) {
            tile2.setSubtitle(tile.getSubtitle());
        }
        if (tile.getContentDescription() != null) {
            tile2.setContentDescription(tile.getContentDescription());
        }
        if (tile.getStateDescription() != null) {
            tile2.setStateDescription(tile.getStateDescription());
        }
        tile2.setActivityLaunchForClick(tile.getActivityLaunchForClick());
        tile2.setState(tile.getState());
        return Unit.INSTANCE;
    }
}
