package com.android.systemui.wallpaper.engines.theme;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.theme.MotionWallpaper;

/* loaded from: classes3.dex */
public class MotionSource {
    public final String TAG;
    public final MotionWallpaper mRootView;
    public final int mWhich;

    public MotionSource(int i, MotionWallpaper motionWallpaper) {
        this.TAG = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[MotionSource]");
        this.mRootView = motionWallpaper;
        this.mWhich = i;
    }
}
