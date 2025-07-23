package com.android.systemui.wallpapers.data.repository;

import android.graphics.RectF;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperFocalAreaRepositoryImpl implements WallpaperFocalAreaRepository {
    public final StateFlowImpl _notificationDefaultTop;
    public final StateFlowImpl _notificationStackAbsoluteBottom;
    public final StateFlowImpl _shortcutAbsoluteTop;
    public final StateFlowImpl _wallpaperFocalAreaBounds;
    public final ReadonlyStateFlow hasFocalArea;
    public final ReadonlyStateFlow notificationDefaultTop;
    public final ReadonlyStateFlow notificationStackAbsoluteBottom;
    public final ReadonlyStateFlow shortcutAbsoluteTop;
    public final ReadonlyStateFlow wallpaperFocalAreaBounds;
    public final WallpaperRepository wallpaperRepository;

    public WallpaperFocalAreaRepositoryImpl(WallpaperRepository wallpaperRepository) {
        this.wallpaperRepository = wallpaperRepository;
        Float valueOf = Float.valueOf(0.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._shortcutAbsoluteTop = MutableStateFlow;
        this.shortcutAbsoluteTop = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._notificationStackAbsoluteBottom = MutableStateFlow2;
        this.notificationStackAbsoluteBottom = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(new RectF(0.0f, 0.0f, 0.0f, 0.0f));
        this._wallpaperFocalAreaBounds = MutableStateFlow3;
        this.wallpaperFocalAreaBounds = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(valueOf);
        this._notificationDefaultTop = MutableStateFlow4;
        this.notificationDefaultTop = FlowKt.asStateFlow(MutableStateFlow4);
        this.hasFocalArea = ((WallpaperRepositoryImpl) wallpaperRepository).shouldSendFocalArea;
    }
}
