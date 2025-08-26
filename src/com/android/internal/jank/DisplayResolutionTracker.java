package com.android.internal.jank;

import android.app.ActivityThread;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.util.SparseArray;
import android.view.DisplayInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class DisplayResolutionTracker {
    public static final int RESOLUTION_FHD = 3;
    public static final int RESOLUTION_HD = 2;
    public static final int RESOLUTION_QHD = 4;
    public static final int RESOLUTION_SD = 1;
    public static final int RESOLUTION_UNKNOWN = 0;
    private static final String TAG = "DisplayResolutionTracker";
    private final Object mLock;
    private final DisplayInterface mManager;
    private final SparseArray<Integer> mResolutions;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Resolution {
    }

    public DisplayResolutionTracker(Handler handler) {
        this(DisplayInterface.getDefault(handler));
    }

    public DisplayResolutionTracker(DisplayInterface displayInterface) {
        this.mResolutions = new SparseArray<>();
        this.mLock = new Object();
        this.mManager = displayInterface;
        displayInterface.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.internal.jank.DisplayResolutionTracker.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
                DisplayResolutionTracker.this.updateDisplay(i);
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                DisplayResolutionTracker.this.updateDisplay(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplay(int i) {
        DisplayInfo displayInfo = this.mManager.getDisplayInfo(i);
        if (displayInfo == null) {
            return;
        }
        int resolution = getResolution(displayInfo);
        synchronized (this.mLock) {
            this.mResolutions.put(i, Integer.valueOf(resolution));
        }
    }

    public int getResolution(int i) {
        return this.mResolutions.get(i, 0).intValue();
    }

    public static int getResolution(DisplayInfo displayInfo) {
        int iMin = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
        int iMax = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
        if (iMin < 720 || iMax < 1280) {
            return 1;
        }
        if (iMin < 1080 || iMax < 1920) {
            return 2;
        }
        return (iMin < 1440 || iMax < 2560) ? 3 : 4;
    }

    public interface DisplayInterface {
        DisplayInfo getDisplayInfo(int i);

        void registerDisplayListener(DisplayManager.DisplayListener displayListener);

        static DisplayInterface getDefault(final Handler handler) {
            final long j = (com.android.server.display.feature.flags.Flags.displayListenerPerformanceImprovements() && com.android.server.display.feature.flags.Flags.delayImplicitRrRegistrationUntilRrAccessed()) ? 3L : 67L;
            final DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
            return new DisplayInterface() { // from class: com.android.internal.jank.DisplayResolutionTracker.DisplayInterface.1
                @Override // com.android.internal.jank.DisplayResolutionTracker.DisplayInterface
                public void registerDisplayListener(DisplayManager.DisplayListener displayListener) {
                    displayManagerGlobal.registerDisplayListener(displayListener, handler, j, ActivityThread.currentPackageName());
                }

                @Override // com.android.internal.jank.DisplayResolutionTracker.DisplayInterface
                public DisplayInfo getDisplayInfo(int i) {
                    return displayManagerGlobal.getDisplayInfo(i);
                }
            };
        }
    }
}
