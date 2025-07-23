package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.service.quicksettings.Tile;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTileRepositoryImpl$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ Tile f$0;

    public /* synthetic */ CustomTileRepositoryImpl$$ExternalSyntheticLambda1(Tile tile) {
        this.f$0 = tile;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
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
