package com.android.server.vr;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.media.ImageReader;
import android.os.Handler;
import android.service.vr.IPersistentVrStateCallbacks;
import android.service.vr.IVrManager;
import android.util.Log;
import android.view.Surface;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;

public final class Vr2dDisplay {
    public final DisplayManager mDisplayManager;
    public ImageReader mImageReader;
    public boolean mIsPersistentVrModeEnabled;
    public AnonymousClass3 mStopVDRunnable;
    public Surface mSurface;
    public VirtualDisplay mVirtualDisplay;
    public final IVrManager mVrManager;
    public final WindowManagerInternal mWindowManagerInternal;
    public final Object mVdLock = new Object();
    public final Handler mHandler = new Handler();
    public final AnonymousClass1 mVrStateCallbacks =
            new IPersistentVrStateCallbacks
                    .Stub() { // from class: com.android.server.vr.Vr2dDisplay.1
                public final void onPersistentVrStateChanged(boolean z) {
                    Vr2dDisplay vr2dDisplay = Vr2dDisplay.this;
                    if (z != vr2dDisplay.mIsPersistentVrModeEnabled) {
                        vr2dDisplay.mIsPersistentVrModeEnabled = z;
                        vr2dDisplay.updateVirtualDisplay();
                    }
                }
            };
    public boolean mIsVirtualDisplayAllowed = true;
    public boolean mBootsToVr = false;
    public int mVirtualDisplayWidth = 1400;
    public int mVirtualDisplayHeight = 1800;
    public int mVirtualDisplayDpi = 320;

    public Vr2dDisplay(
            DisplayManager displayManager,
            WindowManagerInternal windowManagerInternal,
            VrManagerService.AnonymousClass4 anonymousClass4) {
        this.mDisplayManager = displayManager;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mVrManager = anonymousClass4;
    }

    public final void setSurfaceLocked(Surface surface) {
        if (this.mSurface != surface) {
            if (surface == null || surface.isValid()) {
                Log.i(
                        "Vr2dDisplay",
                        "Setting the new surface from " + this.mSurface + " to " + surface);
                VirtualDisplay virtualDisplay = this.mVirtualDisplay;
                if (virtualDisplay != null) {
                    virtualDisplay.setSurface(surface);
                }
                Surface surface2 = this.mSurface;
                if (surface2 != null) {
                    surface2.release();
                }
                this.mSurface = surface;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setVirtualDisplayProperties(android.app.Vr2dDisplayProperties r10) {
        /*
            r9 = this;
            java.lang.String r0 = "Ignoring Width/Height/Dpi values of "
            java.lang.String r1 = "Setting width/height/dpi to "
            java.lang.Object r2 = r9.mVdLock
            monitor-enter(r2)
            int r3 = r10.getWidth()     // Catch: java.lang.Throwable -> L45
            int r4 = r10.getHeight()     // Catch: java.lang.Throwable -> L45
            int r5 = r10.getDpi()     // Catch: java.lang.Throwable -> L45
            r6 = 0
            r7 = 1
            if (r3 < r7) goto L47
            if (r4 < r7) goto L47
            if (r5 >= r7) goto L1c
            goto L47
        L1c:
            java.lang.String r0 = "Vr2dDisplay"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L45
            r8.<init>(r1)     // Catch: java.lang.Throwable -> L45
            r8.append(r3)     // Catch: java.lang.Throwable -> L45
            java.lang.String r1 = ","
            r8.append(r1)     // Catch: java.lang.Throwable -> L45
            r8.append(r4)     // Catch: java.lang.Throwable -> L45
            java.lang.String r1 = ","
            r8.append(r1)     // Catch: java.lang.Throwable -> L45
            r8.append(r5)     // Catch: java.lang.Throwable -> L45
            java.lang.String r1 = r8.toString()     // Catch: java.lang.Throwable -> L45
            android.util.Log.i(r0, r1)     // Catch: java.lang.Throwable -> L45
            r9.mVirtualDisplayWidth = r3     // Catch: java.lang.Throwable -> L45
            r9.mVirtualDisplayHeight = r4     // Catch: java.lang.Throwable -> L45
            r9.mVirtualDisplayDpi = r5     // Catch: java.lang.Throwable -> L45
            r0 = r7
            goto L69
        L45:
            r9 = move-exception
            goto L9f
        L47:
            java.lang.String r1 = "Vr2dDisplay"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L45
            r8.<init>(r0)     // Catch: java.lang.Throwable -> L45
            r8.append(r3)     // Catch: java.lang.Throwable -> L45
            java.lang.String r0 = ","
            r8.append(r0)     // Catch: java.lang.Throwable -> L45
            r8.append(r4)     // Catch: java.lang.Throwable -> L45
            java.lang.String r0 = ","
            r8.append(r0)     // Catch: java.lang.Throwable -> L45
            r8.append(r5)     // Catch: java.lang.Throwable -> L45
            java.lang.String r0 = r8.toString()     // Catch: java.lang.Throwable -> L45
            android.util.Log.i(r1, r0)     // Catch: java.lang.Throwable -> L45
            r0 = r6
        L69:
            int r1 = r10.getAddedFlags()     // Catch: java.lang.Throwable -> L45
            r1 = r1 & r7
            if (r1 != r7) goto L73
            r9.mIsVirtualDisplayAllowed = r7     // Catch: java.lang.Throwable -> L45
            goto L7c
        L73:
            int r10 = r10.getRemovedFlags()     // Catch: java.lang.Throwable -> L45
            r10 = r10 & r7
            if (r10 != r7) goto L7c
            r9.mIsVirtualDisplayAllowed = r6     // Catch: java.lang.Throwable -> L45
        L7c:
            android.hardware.display.VirtualDisplay r10 = r9.mVirtualDisplay     // Catch: java.lang.Throwable -> L45
            if (r10 == 0) goto L9a
            if (r0 == 0) goto L9a
            boolean r0 = r9.mIsVirtualDisplayAllowed     // Catch: java.lang.Throwable -> L45
            if (r0 == 0) goto L9a
            int r0 = r9.mVirtualDisplayWidth     // Catch: java.lang.Throwable -> L45
            int r1 = r9.mVirtualDisplayHeight     // Catch: java.lang.Throwable -> L45
            int r3 = r9.mVirtualDisplayDpi     // Catch: java.lang.Throwable -> L45
            r10.resize(r0, r1, r3)     // Catch: java.lang.Throwable -> L45
            android.media.ImageReader r10 = r9.mImageReader     // Catch: java.lang.Throwable -> L45
            r0 = 0
            r9.mImageReader = r0     // Catch: java.lang.Throwable -> L45
            r9.startImageReader()     // Catch: java.lang.Throwable -> L45
            r10.close()     // Catch: java.lang.Throwable -> L45
        L9a:
            r9.updateVirtualDisplay()     // Catch: java.lang.Throwable -> L45
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L45
            return
        L9f:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L45
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.vr.Vr2dDisplay.setVirtualDisplayProperties(android.app.Vr2dDisplayProperties):void");
    }

    public final void startImageReader() {
        if (this.mImageReader == null) {
            this.mImageReader =
                    ImageReader.newInstance(
                            this.mVirtualDisplayWidth, this.mVirtualDisplayHeight, 1, 2);
            StringBuilder sb = new StringBuilder("VD startImageReader: res = ");
            sb.append(this.mVirtualDisplayWidth);
            sb.append("X");
            sb.append(this.mVirtualDisplayHeight);
            sb.append(", dpi = ");
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    sb, this.mVirtualDisplayDpi, "Vr2dDisplay");
        }
        synchronized (this.mVdLock) {
            setSurfaceLocked(this.mImageReader.getSurface());
        }
    }

    public final void updateDisplayId(int i) {
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService)
                        ((ActivityTaskManagerInternal)
                                LocalServices.getService(ActivityTaskManagerInternal.class));
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[0]) {
            localService.getClass();
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_TASKS, -1123414663662718691L, 1, null, Long.valueOf(i));
        }
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityTaskManagerService.this.mVr2dDisplayId = i;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        this.mWindowManagerInternal.setVr2dDisplayId(i);
    }

    public final void updateVirtualDisplay() {
        if (!this.mIsVirtualDisplayAllowed
                || (!this.mBootsToVr && !this.mIsPersistentVrModeEnabled)) {
            if (this.mStopVDRunnable == null) {
                this.mStopVDRunnable =
                        new Runnable() { // from class: com.android.server.vr.Vr2dDisplay.3
                            @Override // java.lang.Runnable
                            public final void run() {
                                Vr2dDisplay vr2dDisplay = Vr2dDisplay.this;
                                if (vr2dDisplay.mIsVirtualDisplayAllowed
                                        && (vr2dDisplay.mBootsToVr
                                                || vr2dDisplay.mIsPersistentVrModeEnabled)) {
                                    Log.i(
                                            "Vr2dDisplay",
                                            "Virtual Display destruction stopped: VrMode is back"
                                                + " on.");
                                    return;
                                }
                                Log.i("Vr2dDisplay", "Stopping Virtual Display");
                                synchronized (Vr2dDisplay.this.mVdLock) {
                                    try {
                                        Vr2dDisplay.this.updateDisplayId(-1);
                                        Vr2dDisplay.this.setSurfaceLocked(null);
                                        VirtualDisplay virtualDisplay =
                                                Vr2dDisplay.this.mVirtualDisplay;
                                        if (virtualDisplay != null) {
                                            virtualDisplay.release();
                                            Vr2dDisplay.this.mVirtualDisplay = null;
                                        }
                                        Vr2dDisplay vr2dDisplay2 = Vr2dDisplay.this;
                                        ImageReader imageReader = vr2dDisplay2.mImageReader;
                                        if (imageReader != null) {
                                            imageReader.close();
                                            vr2dDisplay2.mImageReader = null;
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        };
            }
            Handler handler = this.mHandler;
            handler.removeCallbacks(this.mStopVDRunnable);
            handler.postDelayed(this.mStopVDRunnable, 2000L);
            return;
        }
        Log.i("Vr2dDisplay", "Attempting to start virtual display");
        if (this.mDisplayManager == null) {
            Log.w("Vr2dDisplay", "Cannot create virtual display because mDisplayManager == null");
            return;
        }
        synchronized (this.mVdLock) {
            try {
                if (this.mVirtualDisplay != null) {
                    Log.i("Vr2dDisplay", "VD already exists, ignoring request");
                } else {
                    VirtualDisplayConfig.Builder builder =
                            new VirtualDisplayConfig.Builder(
                                    "VR 2D Display",
                                    this.mVirtualDisplayWidth,
                                    this.mVirtualDisplayHeight,
                                    this.mVirtualDisplayDpi);
                    builder.setUniqueId("277f1a09-b88d-4d1e-8716-796f114d080b");
                    builder.setFlags(1485);
                    VirtualDisplay createVirtualDisplay =
                            this.mDisplayManager.createVirtualDisplay(
                                    null, builder.build(), null, null);
                    this.mVirtualDisplay = createVirtualDisplay;
                    if (createVirtualDisplay != null) {
                        updateDisplayId(createVirtualDisplay.getDisplay().getDisplayId());
                        startImageReader();
                        Log.i("Vr2dDisplay", "VD created: " + this.mVirtualDisplay);
                    } else {
                        Log.w(
                                "Vr2dDisplay",
                                "Virtual display id is null after createVirtualDisplay");
                        updateDisplayId(-1);
                    }
                }
            } finally {
            }
        }
    }
}
