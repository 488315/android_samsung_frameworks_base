package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WakefulnessLifecycle extends SecLifecycle implements Dumpable {
    public final Context mContext;
    public final DisplayMetrics mDisplayMetrics;
    public final SystemClock mSystemClock;
    public final IWallpaperManager mWallpaperManagerService;
    public int mWakefulness = 2;
    public int mLastWakeReason = 0;
    public Point mLastWakeOriginLocation = null;
    public int mLastSleepReason = 0;
    public Point mLastSleepOriginLocation = null;

    public WakefulnessLifecycle(Context context, IWallpaperManager iWallpaperManager, SystemClock systemClock, DumpManager dumpManager) {
        this.mContext = context;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mWallpaperManagerService = iWallpaperManager;
        this.mSystemClock = systemClock;
        dumpManager.registerDumpable("WakefulnessLifecycle", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "WakefulnessLifecycle:", "  mWakefulness=");
        m75m.append(this.mWakefulness);
        printWriter.println(m75m.toString());
    }

    public final Point getPowerButtonOrigin() {
        Context context = this.mContext;
        boolean z = context.getResources().getConfiguration().orientation == 1;
        DisplayMetrics displayMetrics = this.mDisplayMetrics;
        return z ? new Point(displayMetrics.widthPixels, context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y)) : new Point(context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y), displayMetrics.heightPixels);
    }

    @Override // com.android.systemui.keyguard.SecLifecycle
    public final int getWakefulness() {
        return this.mWakefulness;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Observer {
        default void onFinishedGoingToSleep() {
        }

        default void onFinishedWakingUp() {
        }

        default void onPostFinishedWakingUp() {
        }

        default void onStartedGoingToSleep() {
        }

        default void onStartedWakingUp() {
        }
    }
}
