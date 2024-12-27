package com.android.server.input;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.server.UiThread;
import com.android.server.input.NativeInputManagerService;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PointerIconCache {
    public final Context mContext;
    public final NativeInputManagerService mNative;
    public final Handler mUiThreadHandler = UiThread.getHandler();
    public final SparseArray mLoadedPointerIconsByDisplayAndType = new SparseArray();
    public boolean mUseLargePointerIcons = false;
    public float mPointerIconSizeScale = 1.0f;
    public int mPointerIconColor = 16777215;
    public final SparseArray mDisplayContexts = new SparseArray();
    public final SparseIntArray mDisplayDensities = new SparseIntArray();
    public int mPointerIconFillStyle = 0;
    public float mPointerIconScale = 1.0f;
    public final AnonymousClass1 mDisplayListener = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.input.PointerIconCache$1, reason: invalid class name */
    public final class AnonymousClass1 implements DisplayManager.DisplayListener {
        public AnonymousClass1() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
            synchronized (PointerIconCache.this.mLoadedPointerIconsByDisplayAndType) {
                PointerIconCache.this.updateDisplayDensityLocked(i);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            PointerIconCache pointerIconCache = PointerIconCache.this;
            synchronized (pointerIconCache.mLoadedPointerIconsByDisplayAndType) {
                try {
                    if (pointerIconCache.updateDisplayDensityLocked(i)) {
                        Slog.i("PointerIconCache", "Reloading pointer icons due to density change on display: " + i);
                        SparseArray sparseArray = (SparseArray) pointerIconCache.mLoadedPointerIconsByDisplayAndType.get(i);
                        if (sparseArray == null) {
                            return;
                        }
                        sparseArray.clear();
                        pointerIconCache.mDisplayContexts.remove(i);
                        pointerIconCache.mNative.reloadPointerIcons();
                    }
                } finally {
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
            synchronized (PointerIconCache.this.mLoadedPointerIconsByDisplayAndType) {
                PointerIconCache.this.mLoadedPointerIconsByDisplayAndType.remove(i);
                PointerIconCache.this.mDisplayContexts.remove(i);
                PointerIconCache.this.mDisplayDensities.delete(i);
            }
        }
    }

    public PointerIconCache(Context context, NativeInputManagerService.NativeImpl nativeImpl) {
        this.mContext = context;
        this.mNative = nativeImpl;
    }

    public final Context getContextForDisplayLocked(int i) {
        if (i != -1 && i != this.mContext.getDisplay().getDisplayId()) {
            Context context = (Context) this.mDisplayContexts.get(i);
            if (context != null) {
                return context;
            }
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
            Objects.requireNonNull(displayManager);
            Display display = displayManager.getDisplay(i);
            if (display == null) {
                return this.mContext;
            }
            Context createDisplayContext = this.mContext.createDisplayContext(display);
            this.mDisplayContexts.put(i, createDisplayContext);
            return createDisplayContext;
        }
        return this.mContext;
    }

    public final void monitor() {
        synchronized (this.mLoadedPointerIconsByDisplayAndType) {
        }
    }

    public final boolean updateDisplayDensityLocked(int i) {
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        Display display = displayManager.getDisplay(i);
        if (display == null) {
            return false;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        int i2 = this.mDisplayDensities.get(i, 0);
        int i3 = displayInfo.logicalDensityDpi;
        if (i2 == i3) {
            return false;
        }
        this.mDisplayDensities.put(i, i3);
        return true;
    }
}
