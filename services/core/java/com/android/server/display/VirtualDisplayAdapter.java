package com.android.server.display;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplayConfig;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionCallback;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayShape;
import android.view.Surface;
import android.view.SurfaceControl;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.feature.DisplayManagerFlags;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class VirtualDisplayAdapter extends DisplayAdapter {
    static final String UNIQUE_ID_PREFIX = "virtual:";
    public static final AtomicInteger sNextUniqueIndex = new AtomicInteger(0);
    public DisplayDeviceInfo mDefaultDeviceInfo;
    public final Handler mHandler;
    public LogicalDisplayMapper mLogicalDisplayMapper;
    public final SurfaceControlDisplayFactory mSurfaceControlDisplayFactory;
    public final ArrayMap mVirtualDisplayDevices;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.VirtualDisplayAdapter$1, reason: invalid class name */
    public final class AnonymousClass1 implements SurfaceControlDisplayFactory {
        @Override // com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory
        public final IBinder createDisplay(String str, boolean z, String str2, float f) {
            return DisplayControl.createVirtualDisplay(str, z, str2, f);
        }

        @Override // com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory
        public final void destroyDisplay(IBinder iBinder) {
            DisplayControl.destroyVirtualDisplay(iBinder);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Callback extends Handler {
        public final IVirtualDisplayCallback mCallback;

        public Callback(IVirtualDisplayCallback iVirtualDisplayCallback, Handler handler) {
            super(handler.getLooper());
            this.mCallback = iVirtualDisplayCallback;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 0) {
                    this.mCallback.onPaused();
                } else if (i == 1) {
                    this.mCallback.onResumed();
                } else if (i == 2) {
                    this.mCallback.onStopped();
                }
            } catch (RemoteException e) {
                Slog.w(
                        "VirtualDisplayAdapter",
                        "Failed to notify listener of virtual display event.",
                        e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MediaProjectionCallback extends IMediaProjectionCallback.Stub {
        public final IBinder mAppToken;

        public MediaProjectionCallback(IBinder iBinder) {
            this.mAppToken = iBinder;
        }

        public final void onCapturedContentResize(int i, int i2) {}

        public final void onCapturedContentVisibilityChanged(boolean z) {}

        public final void onStop() {
            synchronized (VirtualDisplayAdapter.this.mSyncRoot) {
                VirtualDisplayAdapter.m464$$Nest$mhandleMediaProjectionStoppedLocked(
                        VirtualDisplayAdapter.this, this.mAppToken);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SurfaceControlDisplayFactory {
        IBinder createDisplay(String str, boolean z, String str2, float f);

        void destroyDisplay(IBinder iBinder);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VirtualDisplayDevice extends DisplayDevice
            implements IBinder.DeathRecipient {
        public final IBinder mAppToken;
        public final Callback mCallback;
        public int mDensityDpi;
        public boolean mDexEnabled;
        public int mDisplayIdToMirror;
        public int mDisplayState;
        public final int mFlags;
        public int mHeight;
        public DisplayDeviceInfo mInfo;
        public boolean mIsDisplayOn;
        public boolean mIsWindowManagerMirroring;
        public final IMediaProjectionCallback mMediaProjectionCallback;
        public Display.Mode mMode;
        public final String mName;
        public final String mOwnerPackageName;
        public final int mOwnerUid;
        public int mPendingChanges;
        public final IMediaProjection mProjection;
        public final float mRequestedRefreshRate;
        public boolean mStopped;
        public Surface mSurface;
        public int mWidth;

        public VirtualDisplayDevice(
                IBinder iBinder,
                IBinder iBinder2,
                int i,
                String str,
                Surface surface,
                int i2,
                Callback callback,
                IMediaProjection iMediaProjection,
                MediaProjectionCallback mediaProjectionCallback,
                String str2,
                VirtualDisplayConfig virtualDisplayConfig) {
            super(
                    VirtualDisplayAdapter.this,
                    iBinder,
                    str2,
                    VirtualDisplayAdapter.this.mContext,
                    false);
            this.mAppToken = iBinder2;
            this.mOwnerUid = i;
            this.mOwnerPackageName = str;
            this.mName = virtualDisplayConfig.getName();
            this.mWidth = virtualDisplayConfig.getWidth();
            this.mHeight = virtualDisplayConfig.getHeight();
            this.mDensityDpi = virtualDisplayConfig.getDensityDpi();
            float requestedRefreshRate = virtualDisplayConfig.getRequestedRefreshRate();
            this.mRequestedRefreshRate = requestedRefreshRate;
            int i3 = this.mWidth;
            int i4 = this.mHeight;
            float f =
                    requestedRefreshRate == FullScreenMagnificationGestureHandler.MAX_SCALE
                            ? 60.0f
                            : requestedRefreshRate;
            this.mMode =
                    new Display.Mode(
                            DisplayAdapter.NEXT_DISPLAY_MODE_ID.getAndIncrement(),
                            i3,
                            i4,
                            f,
                            f,
                            false,
                            new float[0],
                            new int[0]);
            this.mSurface = surface;
            this.mFlags = i2;
            this.mCallback = callback;
            this.mProjection = iMediaProjection;
            this.mMediaProjectionCallback = mediaProjectionCallback;
            this.mDisplayState = 0;
            this.mPendingChanges |= 1;
            this.mIsDisplayOn = surface != null;
            this.mDisplayIdToMirror = virtualDisplayConfig.getDisplayIdToMirror();
            this.mIsWindowManagerMirroring = virtualDisplayConfig.isWindowManagerMirroringEnabled();
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            IMediaProjectionCallback iMediaProjectionCallback;
            synchronized (VirtualDisplayAdapter.this.mSyncRoot) {
                VirtualDisplayAdapter.this.mVirtualDisplayDevices.remove(this.mAppToken);
                Slog.i(
                        "VirtualDisplayAdapter",
                        "Virtual display device released because application token died: "
                                + this.mOwnerPackageName);
                destroyLocked(false);
                IMediaProjection iMediaProjection = this.mProjection;
                if (iMediaProjection != null
                        && (iMediaProjectionCallback = this.mMediaProjectionCallback) != null) {
                    try {
                        iMediaProjection.unregisterCallback(iMediaProjectionCallback);
                    } catch (RemoteException e) {
                        Slog.w(
                                "VirtualDisplayAdapter",
                                "Failed to unregister callback in binderDied",
                                e);
                    }
                }
                VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 3);
            }
        }

        public final void destroyLocked(boolean z) {
            IMediaProjectionCallback iMediaProjectionCallback;
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
            VirtualDisplayAdapter.this.mSurfaceControlDisplayFactory.destroyDisplay(
                    this.mDisplayToken);
            IMediaProjection iMediaProjection = this.mProjection;
            if (iMediaProjection != null
                    && (iMediaProjectionCallback = this.mMediaProjectionCallback) != null) {
                try {
                    iMediaProjection.unregisterCallback(iMediaProjectionCallback);
                } catch (RemoteException e) {
                    Slog.w("VirtualDisplayAdapter", "Failed to unregister callback in destroy", e);
                }
            }
            if (z) {
                this.mCallback.sendEmptyMessage(2);
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final void dumpLocked(PrintWriter printWriter) {
            super.dumpLocked(printWriter);
            StringBuilder m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mFlags="),
                            this.mFlags,
                            printWriter,
                            "mDisplayState=");
            m.append(Display.stateToString(this.mDisplayState));
            printWriter.println(m.toString());
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("mStopped="),
                                            this.mStopped,
                                            printWriter,
                                            "mDisplayIdToMirror="),
                                    this.mDisplayIdToMirror,
                                    printWriter,
                                    "mWindowManagerMirroring="),
                            this.mIsWindowManagerMirroring,
                            printWriter,
                            "mRequestedRefreshRate="),
                    this.mRequestedRefreshRate,
                    printWriter);
            if (this.mDexEnabled) {
                printWriter.println("mDexEnabled=true");
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean getDexEnabledStateLocked() {
            return this.mDexEnabled;
        }

        @Override // com.android.server.display.DisplayDevice
        public final DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            if (this.mInfo == null) {
                DisplayDeviceInfo displayDeviceInfo = new DisplayDeviceInfo();
                this.mInfo = displayDeviceInfo;
                displayDeviceInfo.name = this.mName;
                displayDeviceInfo.uniqueId = this.mUniqueId;
                displayDeviceInfo.width = this.mWidth;
                displayDeviceInfo.height = this.mHeight;
                displayDeviceInfo.modeId = this.mMode.getModeId();
                this.mInfo.renderFrameRate = this.mMode.getRefreshRate();
                this.mInfo.defaultModeId = this.mMode.getModeId();
                DisplayDeviceInfo displayDeviceInfo2 = this.mInfo;
                displayDeviceInfo2.supportedModes = new Display.Mode[] {this.mMode};
                int i = this.mDensityDpi;
                displayDeviceInfo2.densityDpi = i;
                float f = i;
                displayDeviceInfo2.xDpi = f;
                displayDeviceInfo2.yDpi = f;
                float f2 = this.mRequestedRefreshRate;
                if (f2 == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    f2 = 60.0f;
                }
                displayDeviceInfo2.presentationDeadlineNanos = 1000000000 / ((int) f2);
                displayDeviceInfo2.flags = 0;
                int i2 = this.mFlags;
                int i3 = i2 & 1;
                if (i3 == 0) {
                    displayDeviceInfo2.flags = 48;
                }
                if ((i2 & 16) != 0) {
                    displayDeviceInfo2.flags &= -33;
                } else {
                    int i4 = displayDeviceInfo2.flags;
                    displayDeviceInfo2.flags = i4 | 128;
                    if ((i2 & 2048) != 0) {
                        displayDeviceInfo2.flags = i4 | 16512;
                    }
                }
                if ((i2 & 32768) != 0) {
                    displayDeviceInfo2.flags |= 262144;
                }
                if ((i2 & 4) != 0) {
                    displayDeviceInfo2.flags |= 4;
                }
                if ((i2 & 2) != 0) {
                    displayDeviceInfo2.flags |= 64;
                    if (i3 != 0
                            && "portrait"
                                    .equals(SystemProperties.get("persist.demo.remoterotation"))) {
                        this.mInfo.rotation = 3;
                    }
                }
                int i5 = this.mFlags;
                if ((i5 & 32) != 0) {
                    this.mInfo.flags |= 512;
                }
                if ((i5 & 128) != 0) {
                    this.mInfo.flags |= 2;
                }
                if ((i5 & 256) != 0) {
                    this.mInfo.flags |= 1024;
                }
                if ((i5 & 512) != 0) {
                    this.mInfo.flags |= 4096;
                }
                if ((i5 & 1024) != 0) {
                    this.mInfo.flags |= 8192;
                }
                if ((i5 & 4096) != 0) {
                    DisplayDeviceInfo displayDeviceInfo3 = this.mInfo;
                    int i6 = displayDeviceInfo3.flags;
                    if ((i6 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                                    == 0
                            && (i5 & 32768) == 0) {
                        Slog.w(
                                "VirtualDisplayAdapter",
                                "Ignoring VIRTUAL_DISPLAY_FLAG_ALWAYS_UNLOCKED as it requires"
                                    + " VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP or"
                                    + " VIRTUAL_DISPLAY_FLAG_OWN_DISPLAY_GROUP.");
                    } else {
                        displayDeviceInfo3.flags = i6 | 32768;
                    }
                }
                int i7 = this.mFlags;
                if ((i7 & 8192) != 0) {
                    this.mInfo.flags |=
                            EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
                }
                if ((i7 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                        != 0) {
                    if ((i7 & 1024) != 0) {
                        this.mInfo.flags |= 131072;
                    } else {
                        Slog.w(
                                "VirtualDisplayAdapter",
                                "Ignoring VIRTUAL_DISPLAY_FLAG_OWN_FOCUS as it requires"
                                    + " VIRTUAL_DISPLAY_FLAG_TRUSTED.");
                    }
                }
                int i8 = this.mFlags;
                if ((i8 & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) != 0) {
                    if ((i8 & 1024) == 0
                            || (i8
                                            & EndpointMonitorConst
                                                    .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                                    == 0) {
                        Slog.w(
                                "VirtualDisplayAdapter",
                                "Ignoring VIRTUAL_DISPLAY_FLAG_STEAL_TOP_FOCUS_DISABLED as it"
                                    + " requires VIRTUAL_DISPLAY_FLAG_OWN_FOCUS which requires"
                                    + " VIRTUAL_DISPLAY_FLAG_TRUSTED.");
                    } else {
                        this.mInfo.flags |= 524288;
                    }
                }
                DisplayDeviceInfo displayDeviceInfo4 = this.mInfo;
                displayDeviceInfo4.type = 5;
                int i9 = this.mFlags;
                displayDeviceInfo4.touch = (i9 & 64) == 0 ? 0 : 3;
                boolean z = this.mIsDisplayOn;
                displayDeviceInfo4.state = z ? 2 : 1;
                displayDeviceInfo4.ownerUid = this.mOwnerUid;
                displayDeviceInfo4.ownerPackageName = this.mOwnerPackageName;
                if (CoreRune.SYSFW_APP_SPEG && 16777672 == i9) {
                    displayDeviceInfo4.flags |= 1073741824;
                }
                if (CoreRune.BAIDU_CARLIFE && (1048576 & i9) != 0) {
                    displayDeviceInfo4.flags |= -1610612732;
                    int i10 = this.mDisplayState;
                    if (i10 == 0) {
                        i10 = 2;
                    }
                    displayDeviceInfo4.state = i10;
                }
                DisplayDeviceInfo displayDeviceInfo5 =
                        VirtualDisplayAdapter.this.mDefaultDeviceInfo;
                if (displayDeviceInfo5 != null
                        && ((i9 & 262144) != 0 || (i9 & 524288) != 0 || (i9 & 131072) != 0)) {
                    displayDeviceInfo4.hdrCapabilities = displayDeviceInfo5.hdrCapabilities;
                    displayDeviceInfo4.supportedColorModes = displayDeviceInfo5.supportedColorModes;
                }
                if ((1073741824 & i9) != 0) {
                    displayDeviceInfo4.flags |= 541065216;
                    displayDeviceInfo4.state = z ? this.mDisplayState : 1;
                }
                if ((262144 & i9) != 0) {
                    displayDeviceInfo4.flags |= 537919524;
                    int i11 = this.mDisplayState;
                    if (i11 == 0) {
                        i11 = 2;
                    }
                    displayDeviceInfo4.state = i11;
                }
                if ((524288 & i9) != 0) {
                    if (this.mDisplayState == 1 || !z) {
                        displayDeviceInfo4.state = 1;
                    } else {
                        displayDeviceInfo4.state = 2;
                    }
                    displayDeviceInfo4.flags |= 2097282;
                }
                if ((i9 & 131072) != 0) {
                    displayDeviceInfo4.state = 2;
                    displayDeviceInfo4.flags |= 33554570;
                    if (displayDeviceInfo5 != null) {
                        displayDeviceInfo4.hdrCapabilities = displayDeviceInfo5.hdrCapabilities;
                        displayDeviceInfo4.supportedColorModes =
                                displayDeviceInfo5.supportedColorModes;
                    }
                }
                if ((2097152 & i9) != 0) {
                    displayDeviceInfo4.flags |= 536870912;
                }
                displayDeviceInfo4.displayShape =
                        DisplayShape.createDefaultDisplayShape(
                                displayDeviceInfo4.width, displayDeviceInfo4.height, false);
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public final int getDisplayIdToMirrorLocked() {
            return this.mDisplayIdToMirror;
        }

        @Override // com.android.server.display.DisplayDevice
        public final Point getDisplaySurfaceDefaultSizeLocked() {
            Surface surface = this.mSurface;
            if (surface == null) {
                return null;
            }
            Point defaultSize = surface.getDefaultSize();
            return isRotatedLocked() ? new Point(defaultSize.y, defaultSize.x) : defaultSize;
        }

        public Surface getSurfaceLocked() {
            return this.mSurface;
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean hasStableUniqueId() {
            return false;
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean isWindowManagerMirroringLocked() {
            return this.mIsWindowManagerMirroring;
        }

        @Override // com.android.server.display.DisplayDevice
        public final void performTraversalLocked(SurfaceControl.Transaction transaction) {
            Surface surface;
            if ((this.mPendingChanges & 2) != 0) {
                transaction.setDisplaySize(this.mDisplayToken, this.mWidth, this.mHeight);
            }
            if ((this.mPendingChanges & 1) != 0
                    && this.mCurrentSurface != (surface = this.mSurface)) {
                this.mCurrentSurface = surface;
                transaction.setDisplaySurface(this.mDisplayToken, surface);
            }
            this.mPendingChanges = 0;
        }

        @Override // com.android.server.display.DisplayDevice
        public final Runnable requestDisplayStateLocked(
                int i, float f, float f2, DisplayOffloadSessionImpl displayOffloadSessionImpl) {
            if ((!((this.mFlags & 262144) != 0) || this.mDexEnabled) && i != this.mDisplayState) {
                this.mDisplayState = i;
                if (i == 1) {
                    this.mCallback.sendEmptyMessage(0);
                } else {
                    this.mCallback.sendEmptyMessage(1);
                }
                int i2 = this.mFlags;
                if ((i2 & 262144) != 0
                        || (524288 & i2) != 0
                        || (1073741824 & i2) != 0
                        || (CoreRune.BAIDU_CARLIFE && (i2 & 1048576) != 0)) {
                    this.mInfo = null;
                    VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
                }
            }
            return null;
        }

        public final void setSurfaceLocked(Surface surface) {
            Surface surface2;
            if (this.mStopped || (surface2 = this.mSurface) == surface) {
                return;
            }
            if ((surface2 != null) != (surface != null)) {
                VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
            }
            VirtualDisplayAdapter.this.sendTraversalRequestLocked();
            this.mSurface = surface;
            this.mInfo = null;
            this.mPendingChanges |= 1;
            LogicalDisplay displayLocked =
                    VirtualDisplayAdapter.this.mLogicalDisplayMapper.getDisplayLocked(this);
            if (displayLocked != null) {
                VirtualDisplayAdapter.this.mHandler.sendMessage(
                        VirtualDisplayAdapter.this.mHandler.obtainMessage(
                                30, displayLocked.mDisplayId, 0));
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final void setWindowManagerMirroringLocked(boolean z) {
            if (this.mIsWindowManagerMirroring != z) {
                this.mIsWindowManagerMirroring = z;
                VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
                VirtualDisplayAdapter.this.sendTraversalRequestLocked();
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final void updateDexEnabledStateLocked(boolean z) {
            if (this.mDexEnabled != z) {
                this.mDexEnabled = z;
                Slog.d(
                        "VirtualDisplayAdapter",
                        "updateDexEnabledStateLocked: enabled=" + z + ", " + this);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleMediaProjectionStoppedLocked, reason: not valid java name */
    public static void m464$$Nest$mhandleMediaProjectionStoppedLocked(
            VirtualDisplayAdapter virtualDisplayAdapter, IBinder iBinder) {
        VirtualDisplayDevice virtualDisplayDevice =
                (VirtualDisplayDevice) virtualDisplayAdapter.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            Slog.i(
                    "VirtualDisplayAdapter",
                    "Virtual display device released because media projection stopped: "
                            + virtualDisplayDevice.mName);
            Slog.d(
                    "VirtualDisplayAdapter",
                    "Virtual Display: stopping device " + virtualDisplayDevice.mName);
            virtualDisplayDevice.setSurfaceLocked(null);
            virtualDisplayDevice.mStopped = true;
        }
    }

    public VirtualDisplayAdapter(
            DisplayManagerService.SyncRoot syncRoot,
            Context context,
            Handler handler,
            DisplayAdapter.Listener listener,
            SurfaceControlDisplayFactory surfaceControlDisplayFactory,
            DisplayManagerFlags displayManagerFlags) {
        super(syncRoot, context, handler, listener, "VirtualDisplayAdapter", displayManagerFlags);
        this.mVirtualDisplayDevices = new ArrayMap();
        this.mHandler = handler;
        this.mSurfaceControlDisplayFactory = surfaceControlDisplayFactory;
    }

    public static String generateDisplayUniqueId(
            String str, int i, VirtualDisplayConfig virtualDisplayConfig) {
        String sb;
        StringBuilder sb2 = new StringBuilder(UNIQUE_ID_PREFIX);
        sb2.append(str);
        if (virtualDisplayConfig.getUniqueId() != null) {
            sb = ":" + virtualDisplayConfig.getUniqueId();
        } else {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, ",", ",");
            m.append(virtualDisplayConfig.getName());
            m.append(",");
            m.append(sNextUniqueIndex.getAndIncrement());
            sb = m.toString();
        }
        sb2.append(sb);
        return sb2.toString();
    }

    public Surface getVirtualDisplaySurfaceLocked(IBinder iBinder) {
        VirtualDisplayDevice virtualDisplayDevice =
                (VirtualDisplayDevice) this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            return virtualDisplayDevice.getSurfaceLocked();
        }
        return null;
    }

    public final VirtualDisplayDevice releaseVirtualDisplayLocked(IBinder iBinder) {
        VirtualDisplayDevice virtualDisplayDevice =
                (VirtualDisplayDevice) this.mVirtualDisplayDevices.remove(iBinder);
        if (virtualDisplayDevice != null) {
            Slog.v("VirtualDisplayAdapter", "Release VirtualDisplay " + virtualDisplayDevice.mName);
            virtualDisplayDevice.destroyLocked(true);
            iBinder.unlinkToDeath(virtualDisplayDevice, 0);
        }
        return virtualDisplayDevice;
    }

    public final void resizeVirtualDisplayLocked(int i, int i2, int i3, IBinder iBinder) {
        VirtualDisplayDevice virtualDisplayDevice =
                (VirtualDisplayDevice) this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            StringBuilder sb = new StringBuilder("Resize VirtualDisplay ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i, virtualDisplayDevice.mName, " to ", " ", sb);
            GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, i2, "VirtualDisplayAdapter");
            if (virtualDisplayDevice.mWidth == i
                    && virtualDisplayDevice.mHeight == i2
                    && virtualDisplayDevice.mDensityDpi == i3) {
                return;
            }
            VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(virtualDisplayDevice, 2);
            VirtualDisplayAdapter.this.sendTraversalRequestLocked();
            virtualDisplayDevice.mWidth = i;
            virtualDisplayDevice.mHeight = i2;
            float f = virtualDisplayDevice.mRequestedRefreshRate;
            if (f == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f = 60.0f;
            }
            float f2 = f;
            virtualDisplayDevice.mMode =
                    new Display.Mode(
                            DisplayAdapter.NEXT_DISPLAY_MODE_ID.getAndIncrement(),
                            i,
                            i2,
                            f2,
                            f2,
                            false,
                            new float[0],
                            new int[0]);
            virtualDisplayDevice.mDensityDpi = i3;
            virtualDisplayDevice.mInfo = null;
            virtualDisplayDevice.mPendingChanges |= 2;
        }
    }
}
