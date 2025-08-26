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

/* loaded from: classes.dex */
public final class UpdateRepositoryImpl implements UpdateRepository {
    public final StateFlowImpl _isWhiteWp;
    public final ReadonlyStateFlow isWhiteWp;
    public final UpdateRepositoryImpl$systemUIWidgetCallback$1 systemUIWidgetCallback;
    public final WallpaperManager wallpaperManager;

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.bouncer.ui.UpdateRepositoryImpl$systemUIWidgetCallback$1, com.android.systemui.widget.SystemUIWidgetCallback] */
    public UpdateRepositoryImpl(WallpaperManager wallpaperManager) {
        this.wallpaperManager = wallpaperManager;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(isWhiteWallpaper()));
        this._isWhiteWp = stateFlowImplMutableStateFlow;
        this.isWhiteWp = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        ?? r3 = new SystemUIWidgetCallback() { // from class: com.android.bouncer.ui.UpdateRepositoryImpl$systemUIWidgetCallback$1
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                Log.i("UpdateRepository", "updateStyle()");
                UpdateRepositoryImpl updateRepositoryImpl = this.this$0;
                updateRepositoryImpl._isWhiteWp.updateState(null, Boolean.valueOf(updateRepositoryImpl.isWhiteWallpaper()));
            }
        };
        this.systemUIWidgetCallback = r3;
        WallpaperUtils.registerSystemUIWidgetCallback(r3, 1536L);
    }

    public final boolean isWhiteWallpaper() {
        SemWallpaperColors semWallpaperColorsSemGetWallpaperColors = this.wallpaperManager.semGetWallpaperColors(10);
        if (semWallpaperColorsSemGetWallpaperColors != null) {
            return semWallpaperColorsSemGetWallpaperColors.get(512L).getFontColor() == 1;
        }
        Log.d("UpdateRepository", "semGetWallpaperColors is null");
        return true;
    }
}
