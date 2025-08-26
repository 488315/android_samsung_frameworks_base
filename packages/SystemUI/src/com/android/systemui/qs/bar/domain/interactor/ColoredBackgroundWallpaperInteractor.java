package com.android.systemui.qs.bar.domain.interactor;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class ColoredBackgroundWallpaperInteractor {
    public static final String TAG;
    public final Context context;
    public int homeMatchingColor;
    public int lockMatchingColor;
    public final int uiMode;
    public final WallpaperManager wallpaperManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class WallPaperStatus {
        public static final /* synthetic */ WallPaperStatus[] $VALUES;
        public static final WallPaperStatus HOME;
        public static final WallPaperStatus LOCK;

        static {
            WallPaperStatus wallPaperStatus = new WallPaperStatus("HOME", 0);
            HOME = wallPaperStatus;
            WallPaperStatus wallPaperStatus2 = new WallPaperStatus("LOCK", 1);
            LOCK = wallPaperStatus2;
            WallPaperStatus[] wallPaperStatusArr = {wallPaperStatus, wallPaperStatus2};
            $VALUES = wallPaperStatusArr;
            EnumEntriesKt.enumEntries(wallPaperStatusArr);
        }

        private WallPaperStatus(String str, int i) {
        }

        public static WallPaperStatus valueOf(String str) {
            return (WallPaperStatus) Enum.valueOf(WallPaperStatus.class, str);
        }

        public static WallPaperStatus[] values() {
            return (WallPaperStatus[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(ColoredBackgroundWallpaperInteractor.class).getSimpleName();
    }

    public ColoredBackgroundWallpaperInteractor(Context context) {
        this.context = context;
        this.wallpaperManager = WallpaperManager.getInstance(context);
        this.homeMatchingColor = context.getResources().getColor(R.color.qs_tile_container_bg);
        this.lockMatchingColor = context.getResources().getColor(R.color.qs_tile_container_bg);
        this.uiMode = context.getResources().getConfiguration().uiMode;
    }

    public final int adjustLegibility(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = fArr[2] * (this.uiMode == 32 ? 0.33333334f : 0.5f);
        return Color.HSVToColor(fArr);
    }

    public final int extractColor(int i, String str) {
        int[] seedColors = this.wallpaperManager.getSeedColors(i);
        if (seedColors != null) {
            Integer numValueOf = seedColors.length == 0 ? null : Integer.valueOf(seedColors[0]);
            if (numValueOf != null) {
                int iAdjustLegibility = adjustLegibility(numValueOf.intValue());
                Log.d(TAG, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " MatchingColor : 0x", Integer.toHexString(iAdjustLegibility)));
                return iAdjustLegibility;
            }
        }
        return this.context.getResources().getColor(R.color.qs_tile_container_bg);
    }

    public final int getWallpaperColor(WallPaperStatus wallPaperStatus) {
        float[] fArr = new float[3];
        WallPaperStatus wallPaperStatus2 = WallPaperStatus.LOCK;
        Color.colorToHSV(wallPaperStatus == wallPaperStatus2 ? this.lockMatchingColor : this.homeMatchingColor, fArr);
        boolean z = false;
        String str = wallPaperStatus.name() + " h : " + fArr[0] + " s : " + fArr[1] + " v : " + fArr[2];
        String str2 = TAG;
        Log.d(str2, str);
        float f = fArr[1];
        int color = (f <= 0.04f && fArr[2] <= 0.04f) ? this.context.getResources().getColor(R.color.qs_tile_container_bg) : (fArr[0] == 0.0f && f == 0.0f && fArr[2] == 0.0f) ? this.context.getResources().getColor(R.color.qs_tile_coloring_container_bg_ultra_mode) : wallPaperStatus == wallPaperStatus2 ? this.lockMatchingColor : this.homeMatchingColor;
        float f2 = fArr[1];
        boolean z2 = f2 <= 0.04f && fArr[2] <= 0.04f;
        if (fArr[0] == 0.0f && f2 == 0.0f && fArr[2] == 0.0f) {
            z = true;
        }
        String strName = wallPaperStatus.name();
        String hexString = Integer.toHexString(color);
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("getBGColor isWhiteWallpaper = ", " isBlackWallpaper = ", " wallPaperStatus = ", z2, z);
        sbM.append(strName);
        sbM.append(" 0x");
        sbM.append(hexString);
        Log.d(str2, sbM.toString());
        return color;
    }
}
