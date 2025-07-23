package com.android.bouncer.ui;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.util.Log;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UpdateRepositoryImpl implements UpdateRepository {
    public final StateFlowImpl _isWhiteWp;
    public final ReadonlyStateFlow isWhiteWp;
    public final UpdateRepositoryImpl$systemUIWidgetCallback$1 systemUIWidgetCallback;
    public final WallpaperManager wallpaperManager;

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.bouncer.ui.UpdateRepositoryImpl$systemUIWidgetCallback$1, com.android.systemui.widget.SystemUIWidgetCallback] */
    public UpdateRepositoryImpl(WallpaperManager wallpaperManager) {
        this.wallpaperManager = wallpaperManager;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(isWhiteWallpaper()));
        this._isWhiteWp = MutableStateFlow;
        this.isWhiteWp = FlowKt.asStateFlow(MutableStateFlow);
        ?? r3 = new SystemUIWidgetCallback() { // from class: com.android.bouncer.ui.UpdateRepositoryImpl$systemUIWidgetCallback$1
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                Log.i("UpdateRepository", "updateStyle()");
                UpdateRepositoryImpl updateRepositoryImpl = UpdateRepositoryImpl.this;
                updateRepositoryImpl._isWhiteWp.updateState(null, Boolean.valueOf(updateRepositoryImpl.isWhiteWallpaper()));
            }
        };
        this.systemUIWidgetCallback = r3;
        WallpaperUtils.registerSystemUIWidgetCallback(r3, 1536L);
    }

    public final boolean isWhiteWallpaper() {
        SemWallpaperColors semGetWallpaperColors = this.wallpaperManager.semGetWallpaperColors(10);
        if (semGetWallpaperColors != null) {
            return semGetWallpaperColors.get(512L).getFontColor() == 1;
        }
        Log.d("UpdateRepository", "semGetWallpaperColors is null");
        return true;
    }
}
