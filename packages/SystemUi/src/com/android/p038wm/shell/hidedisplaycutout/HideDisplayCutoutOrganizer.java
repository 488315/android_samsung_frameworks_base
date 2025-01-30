package com.android.p038wm.shell.hidedisplaycutout;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.Log;
import android.util.RotationUtils;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.SurfaceControl;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.policy.SystemBarUtils;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.ShellExecutor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HideDisplayCutoutOrganizer extends DisplayAreaOrganizer {
    public final Context mContext;
    public Insets mCurrentCutoutInsets;
    final Rect mCurrentDisplayBounds;
    public Insets mDefaultCutoutInsets;
    public final Rect mDefaultDisplayBounds;
    ArrayMap<WindowContainerToken, SurfaceControl> mDisplayAreaMap;
    public final DisplayController mDisplayController;
    public final C40001 mListener;
    int mOffsetX;
    int mOffsetY;
    int mRotation;
    public int mStatusBarHeight;

    /* renamed from: -$$Nest$monDisplayChanged, reason: not valid java name */
    public static void m2750$$Nest$monDisplayChanged(HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer, int i) {
        if (i != 0) {
            hideDisplayCutoutOrganizer.getClass();
            return;
        }
        DisplayLayout displayLayout = hideDisplayCutoutOrganizer.mDisplayController.getDisplayLayout(0);
        if (displayLayout == null) {
            return;
        }
        int i2 = hideDisplayCutoutOrganizer.mRotation;
        int i3 = displayLayout.mRotation;
        boolean z = i2 != i3;
        hideDisplayCutoutOrganizer.mRotation = i3;
        if (z || hideDisplayCutoutOrganizer.isDisplayBoundsChanged()) {
            hideDisplayCutoutOrganizer.updateBoundsAndOffsets(true);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            synchronized (hideDisplayCutoutOrganizer) {
                hideDisplayCutoutOrganizer.mDisplayAreaMap.forEach(new HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0(hideDisplayCutoutOrganizer, windowContainerTransaction, transaction));
            }
            hideDisplayCutoutOrganizer.applyTransaction(windowContainerTransaction, transaction);
        }
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutOrganizer$1] */
    public HideDisplayCutoutOrganizer(Context context, DisplayController displayController, ShellExecutor shellExecutor) {
        super(shellExecutor);
        this.mDisplayAreaMap = new ArrayMap<>();
        this.mDefaultDisplayBounds = new Rect();
        this.mCurrentDisplayBounds = new Rect();
        Insets insets = Insets.NONE;
        this.mDefaultCutoutInsets = insets;
        this.mCurrentCutoutInsets = insets;
        this.mListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutOrganizer.1
            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayAdded(int i) {
                HideDisplayCutoutOrganizer.m2750$$Nest$monDisplayChanged(HideDisplayCutoutOrganizer.this, i);
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
                HideDisplayCutoutOrganizer.m2750$$Nest$monDisplayChanged(HideDisplayCutoutOrganizer.this, i);
            }
        };
        this.mContext = context;
        this.mDisplayController = displayController;
    }

    public boolean addDisplayAreaInfoAndLeashToMap(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        synchronized (this) {
            if (displayAreaInfo.displayId != 0) {
                return false;
            }
            if (!this.mDisplayAreaMap.containsKey(displayAreaInfo.token)) {
                this.mDisplayAreaMap.put(displayAreaInfo.token, surfaceControl);
                return true;
            }
            Log.w("HideDisplayCutoutOrganizer", "Already appeared token: " + displayAreaInfo.token);
            return false;
        }
    }

    public void applyBoundsAndOffsets(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl, WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        windowContainerTransaction.setBounds(windowContainerToken, this.mCurrentDisplayBounds);
        transaction.setPosition(surfaceControl, this.mOffsetX, this.mOffsetY);
        transaction.setWindowCrop(surfaceControl, this.mCurrentDisplayBounds.width(), this.mCurrentDisplayBounds.height());
    }

    public void applyTransaction(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        applyTransaction(windowContainerTransaction);
        transaction.apply();
    }

    public Rect getDisplayBoundsOfNaturalOrientation() {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(0);
        if (displayLayout == null) {
            return new Rect();
        }
        int i = this.mRotation;
        boolean z = true;
        if (i != 1 && i != 3) {
            z = false;
        }
        return new Rect(0, 0, z ? displayLayout.mHeight : displayLayout.mWidth, z ? displayLayout.mWidth : displayLayout.mHeight);
    }

    public Insets getDisplayCutoutInsetsOfNaturalOrientation() {
        Display display = this.mDisplayController.getDisplay(0);
        if (display == null) {
            return Insets.NONE;
        }
        DisplayCutout cutout = display.getCutout();
        Insets of = cutout != null ? Insets.of(cutout.getSafeInsets()) : Insets.NONE;
        int i = this.mRotation;
        return i != 0 ? RotationUtils.rotateInsets(of, 4 - i) : of;
    }

    public int getStatusBarHeight() {
        return SystemBarUtils.getStatusBarHeight(this.mContext);
    }

    public final boolean isDisplayBoundsChanged() {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(0);
        if (displayLayout == null) {
            return false;
        }
        int i = this.mRotation;
        boolean z = i == 1 || i == 3;
        return (!this.mDefaultDisplayBounds.isEmpty() && this.mDefaultDisplayBounds.width() == (z ? displayLayout.mHeight : displayLayout.mWidth) && this.mDefaultDisplayBounds.height() == (z ? displayLayout.mWidth : displayLayout.mHeight)) ? false : true;
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        surfaceControl.setUnreleasedWarningCallSite("HideDisplayCutoutOrganizer.onDisplayAreaAppeared");
        if (addDisplayAreaInfoAndLeashToMap(displayAreaInfo, surfaceControl)) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            applyBoundsAndOffsets(displayAreaInfo.token, surfaceControl, windowContainerTransaction, transaction);
            applyTransaction(windowContainerTransaction, transaction);
        }
    }

    public final void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) {
        synchronized (this) {
            if (!this.mDisplayAreaMap.containsKey(displayAreaInfo.token)) {
                Log.w("HideDisplayCutoutOrganizer", "Unrecognized token: " + displayAreaInfo.token);
                return;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            SurfaceControl surfaceControl = this.mDisplayAreaMap.get(displayAreaInfo.token);
            applyBoundsAndOffsets(displayAreaInfo.token, surfaceControl, windowContainerTransaction, transaction);
            applyTransaction(windowContainerTransaction, transaction);
            surfaceControl.release();
            this.mDisplayAreaMap.remove(displayAreaInfo.token);
        }
    }

    public void updateBoundsAndOffsets(boolean z) {
        if (!z) {
            this.mCurrentDisplayBounds.setEmpty();
            this.mOffsetX = 0;
            this.mOffsetY = 0;
            return;
        }
        if (isDisplayBoundsChanged()) {
            this.mDefaultDisplayBounds.set(getDisplayBoundsOfNaturalOrientation());
            this.mDefaultCutoutInsets = getDisplayCutoutInsetsOfNaturalOrientation();
            this.mDefaultDisplayBounds.width();
            this.mDefaultDisplayBounds.height();
        }
        this.mCurrentDisplayBounds.set(this.mDefaultDisplayBounds);
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mCurrentCutoutInsets = RotationUtils.rotateInsets(this.mDefaultCutoutInsets, this.mRotation);
        int i = this.mRotation;
        if (i == 1 || i == 3) {
            Rect rect = this.mCurrentDisplayBounds;
            rect.set(rect.top, rect.left, rect.bottom, rect.right);
        }
        this.mCurrentDisplayBounds.inset(this.mCurrentCutoutInsets);
        int statusBarHeight = getStatusBarHeight();
        this.mStatusBarHeight = statusBarHeight;
        int i2 = this.mCurrentCutoutInsets.top;
        if (i2 != 0) {
            this.mCurrentDisplayBounds.top = Math.max(statusBarHeight, i2);
        }
        Rect rect2 = this.mCurrentDisplayBounds;
        this.mOffsetX = rect2.left;
        this.mOffsetY = rect2.top;
    }
}
