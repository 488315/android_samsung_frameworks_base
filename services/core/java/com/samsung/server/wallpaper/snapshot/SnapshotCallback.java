package com.samsung.server.wallpaper.snapshot;

import android.content.ComponentName;
import android.os.Bundle;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.wallpaper.WallpaperData;
import java.util.List;

/* loaded from: classes2.dex */
public interface SnapshotCallback {
    void requestBindWallpaper(int i, int i2, ComponentName componentName);

    void requestClearWallpaper(int i, int i2);

    void requestInitializeThumnailFile(WallpaperData wallpaperData, int i, int i2);

    List requestKeyguardListeners();

    void requestNotifyCoverWallpaperChanged(int i, int i2, Bundle bundle);

    void requestNotifyLockWallpaperChanged(int i, int i2, Bundle bundle);

    void requestNotifyMultipackApplied(int i, int i2, Bundle bundle);

    void requestNotifySemWallpaperColors(int i);

    void requestParseWallpaperAttributes(TypedXmlPullParser typedXmlPullParser, WallpaperData wallpaperData, boolean z);

    void requestSaveRestoredWallpaperLocked(int i, int i2, WallpaperData wallpaperData);

    void requestSaveSettingsLocked(int i, int i2);

    WallpaperData requestWallpaperData(int i, int i2);

    void requestWallpaperId(WallpaperData wallpaperData);

    void requestWriteWallpaperAttributes(TypedXmlSerializer typedXmlSerializer, String str, WallpaperData wallpaperData);
}
