package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class WakefulnessLifecycle extends SecLifecycle implements Dumpable {
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
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        MagnificationImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "WakefulnessLifecycle:", "  mWakefulness="), this.mWakefulness, printWriter);
    }

    public final Point getPowerButtonOrigin() {
        return this.mContext.getResources().getConfiguration().orientation == 1 ? new Point(this.mDisplayMetrics.widthPixels, this.mContext.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y)) : new Point(this.mContext.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y), this.mDisplayMetrics.heightPixels);
    }

    @Override // com.android.systemui.keyguard.SecLifecycle
    public final int getWakefulness() {
        return this.mWakefulness;
    }

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
