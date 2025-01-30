package com.android.systemui.keyguard;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.LogUtil;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DisplayLifecycle extends SecLifecycle implements Dumpable {
    public final C14481 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final Handler mHandler;
    public final Map mDisplayHash = new HashMap();
    public final SparseArray mDisplaySizeHash = new SparseArray();
    public final SparseArray mDisplayRealSizeHash = new SparseArray();
    public final SparseArray mDisplayMetricsHash = new SparseArray();
    public final SparseIntArray mDisplayRotationHash = new SparseIntArray();
    public boolean mIsFolderOpened = true;
    public boolean mIsFitToActiveDisplay = false;
    public int mCurrentState = 0;
    public int mPreviousState = 0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.hardware.display.DisplayManager$DisplayListener, com.android.systemui.keyguard.DisplayLifecycle$1] */
    public DisplayLifecycle(Context context, Handler handler, final Lazy lazy) {
        ?? r0 = new DisplayManager.DisplayListener() { // from class: com.android.systemui.keyguard.DisplayLifecycle.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
                DisplayLifecycle displayLifecycle = DisplayLifecycle.this;
                displayLifecycle.addDisplay(i);
                displayLifecycle.mHandler.post(new DisplayLifecycle$$ExternalSyntheticLambda5(displayLifecycle, i, 2));
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Boolean valueOf;
                DisplayLifecycle displayLifecycle = DisplayLifecycle.this;
                displayLifecycle.getClass();
                android.util.Log.d("DisplayLifecycle", "updateDisplay id = " + i);
                if (displayLifecycle.getDisplay(i) == null) {
                    displayLifecycle.addDisplay(i);
                    if (displayLifecycle.getDisplay(i) == null) {
                        android.util.Log.e("DisplayLifecycle", "updateDisplay return - display is null");
                        valueOf = Boolean.FALSE;
                        if (!valueOf.booleanValue() || ((DesktopSystemUiBinder) ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
                            displayLifecycle.mHandler.post(new DisplayLifecycle$$ExternalSyntheticLambda5(displayLifecycle, i, 0));
                        }
                        return;
                    }
                }
                valueOf = Boolean.valueOf(displayLifecycle.updateCacheVariables(i));
                if (valueOf.booleanValue()) {
                }
                displayLifecycle.mHandler.post(new DisplayLifecycle$$ExternalSyntheticLambda5(displayLifecycle, i, 0));
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
                DisplayLifecycle displayLifecycle = DisplayLifecycle.this;
                displayLifecycle.getClass();
                displayLifecycle.mHandler.post(new DisplayLifecycle$$ExternalSyntheticLambda5(displayLifecycle, i, 1));
            }
        };
        this.mDisplayListener = r0;
        this.mHandler = handler;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(r0, null);
        handler.post(new Runnable() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final DisplayLifecycle displayLifecycle = DisplayLifecycle.this;
                Lazy lazy2 = lazy;
                displayLifecycle.getClass();
                ((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy2.get())).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda1
                    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                    public final void onFoldStateChanged(final boolean z) {
                        final DisplayLifecycle displayLifecycle2 = DisplayLifecycle.this;
                        if (displayLifecycle2.mIsFolderOpened == z) {
                            return;
                        }
                        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("dispatchFolderStateChanged "), displayLifecycle2.mIsFolderOpened, " -> ", z, "DisplayLifecycle");
                        displayLifecycle2.mIsFolderOpened = z;
                        displayLifecycle2.updateCacheVariables(0);
                        displayLifecycle2.mHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                DisplayLifecycle displayLifecycle3 = DisplayLifecycle.this;
                                final boolean z2 = z;
                                displayLifecycle3.getClass();
                                displayLifecycle3.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda6
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ((DisplayLifecycle.Observer) obj).onFolderStateChanged(z2);
                                    }
                                });
                            }
                        });
                    }
                }, 3);
            }
        });
    }

    public final void addDisplay(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("addDisplay id = ", i, "DisplayLifecycle");
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null || display.getDisplayId() != i) {
            android.util.Log.e("DisplayLifecycle", "addDisplay return - display is null or id is invalid");
            return;
        }
        ((HashMap) this.mDisplayHash).put(Integer.valueOf(i), display);
        updateCacheVariables(i);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("DisplayLifecycle:");
        ((HashMap) this.mDisplayHash).values().stream().filter(new DisplayLifecycle$$ExternalSyntheticLambda2()).forEach(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                printWriter.println("  " + ((Display) obj).toString());
            }
        });
    }

    public final Display getDisplay(int i) {
        Map map = this.mDisplayHash;
        Display display = (Display) ((HashMap) map).get(Integer.valueOf(i));
        if (display == null) {
            addDisplay(i);
            display = (Display) ((HashMap) map).get(Integer.valueOf(i));
            if (display == null) {
                android.util.Log.e("DisplayLifecycle", "getDisplay() is null, get directly from DisplayManager");
                return this.mDisplayManager.getDisplay(i);
            }
        }
        return display;
    }

    public final Point getRealSize() {
        if (getDisplay(0) == null) {
            addDisplay(0);
        }
        Point point = (Point) this.mDisplayRealSizeHash.get(0);
        if (point == null) {
            LogUtil.m226w("DisplayLifecycle", "getRealSize(%d) is null, return empty Point", 0);
            point = new Point();
        }
        return new Point(point);
    }

    public final int getRotation() {
        if (getDisplay(0) == null) {
            addDisplay(0);
        }
        Integer valueOf = Integer.valueOf(this.mDisplayRotationHash.get(0));
        if (valueOf == null) {
            LogUtil.m226w("DisplayLifecycle", "getRotation(%d) is null, return empty Point", 0);
            valueOf = 0;
        }
        return valueOf.intValue();
    }

    public final boolean updateCacheVariables(int i) {
        int i2;
        int i3;
        Display display = (Display) ((HashMap) this.mDisplayHash).get(Integer.valueOf(i));
        boolean z = false;
        if (display == null) {
            return false;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (i == 0 && (i2 = this.mCurrentState) != (i3 = displayInfo.state)) {
            if (i2 == 0) {
                this.mCurrentState = i3;
                this.mPreviousState = i3;
            } else {
                this.mPreviousState = i2;
                this.mCurrentState = i3;
            }
        }
        SparseArray sparseArray = this.mDisplaySizeHash;
        Point point = (Point) sparseArray.get(i);
        Point point2 = new Point();
        display.getSize(point2);
        boolean z2 = true;
        if (point == null || !point.equals(point2)) {
            sparseArray.put(i, point2);
            z = true;
        }
        SparseArray sparseArray2 = this.mDisplayRealSizeHash;
        Point point3 = (Point) sparseArray2.get(i);
        Point point4 = new Point();
        display.getRealSize(point4);
        if (point3 == null || !point3.equals(point4)) {
            sparseArray2.put(i, point4);
            z = true;
        }
        SparseArray sparseArray3 = this.mDisplayMetricsHash;
        DisplayMetrics displayMetrics = (DisplayMetrics) sparseArray3.get(i);
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        display.getMetrics(displayMetrics2);
        if (displayMetrics == null || !displayMetrics.equals(displayMetrics2)) {
            sparseArray3.put(i, displayMetrics2);
            z = true;
        }
        SparseIntArray sparseIntArray = this.mDisplayRotationHash;
        Integer valueOf = Integer.valueOf(sparseIntArray.get(i));
        int i4 = displayInfo.rotation;
        if (valueOf == null || valueOf.intValue() != i4) {
            sparseIntArray.put(i, i4);
        } else {
            z2 = z;
        }
        if (z2) {
            updateSmartViewFitToActiveDisplay();
            android.util.Log.d("DisplayLifecycle", "updateCacheVariables id = " + i + ", display = " + display + ", size = " + point2 + ", realSize = " + point4 + ", rotation = " + i4);
        }
        return z2;
    }

    public final void updateSmartViewFitToActiveDisplay() {
        this.mIsFitToActiveDisplay = this.mDisplayManager.semIsFitToActiveDisplay();
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updateSmartViewFitToActiveDisplay : "), this.mIsFitToActiveDisplay, "DisplayLifecycle");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Observer {
        default void onDisplayAdded(int i) {
        }

        default void onDisplayChanged(int i) {
        }

        default void onDisplayRemoved(int i) {
        }

        default void onFolderStateChanged(boolean z) {
        }
    }
}
