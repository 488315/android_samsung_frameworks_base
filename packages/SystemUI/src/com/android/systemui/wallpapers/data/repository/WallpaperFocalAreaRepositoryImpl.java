package com.android.systemui.wallpapers.data.repository;

import android.graphics.RectF;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
        Float fValueOf = Float.valueOf(0.0f);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(fValueOf);
        this._shortcutAbsoluteTop = stateFlowImplMutableStateFlow;
        this.shortcutAbsoluteTop = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(fValueOf);
        this._notificationStackAbsoluteBottom = stateFlowImplMutableStateFlow2;
        this.notificationStackAbsoluteBottom = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(new RectF(0.0f, 0.0f, 0.0f, 0.0f));
        this._wallpaperFocalAreaBounds = stateFlowImplMutableStateFlow3;
        this.wallpaperFocalAreaBounds = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(fValueOf);
        this._notificationDefaultTop = stateFlowImplMutableStateFlow4;
        this.notificationDefaultTop = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.hasFocalArea = ((WallpaperRepositoryImpl) wallpaperRepository).shouldSendFocalArea;
    }
}
