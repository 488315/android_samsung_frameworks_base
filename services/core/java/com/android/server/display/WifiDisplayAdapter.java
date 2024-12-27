package com.android.server.display;

import android.R;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.IDisplayManager;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplaySessionInfo;
import android.hardware.display.WifiDisplayStatus;
import android.media.RemoteDisplay;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayAdapter;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.DlnaController;
import com.android.server.display.DlnaController.DlnaClientDeathMonitor;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.input.KeyboardMetricsCollector;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.wifi.SemWifiManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WifiDisplayAdapter extends DisplayAdapter {
    public WifiDisplay mActiveDisplay;
    public int mActiveDisplayState;
    public WifiDisplay[] mAvailableDisplays;
    public final AnonymousClass13 mBroadcastReceiver;
    public WifiDisplayStatus mCurrentStatus;
    public DisplayDeviceInfo mDefaultDisplayDeviceInfo;
    public WifiDisplayController mDisplayController;
    public WifiDisplayDevice mDisplayDevice;
    public WifiDisplay[] mDisplays;
    public DlnaController mDlnaController;
    public int mFeatureState;
    public final WifiDisplayHandler mHandler;
    public IRefreshRateToken mIRefreshRateToken;
    public boolean mPendingStatusChangeBroadcast;
    public final PersistentDataStore mPersistentDataStore;
    public WifiDisplay[] mRememberedDisplays;
    public int mScanState;
    public WifiDisplaySessionInfo mSessionInfo;
    public final boolean mSupportsProtectedBuffers;
    public VolumeController mVolumeController;
    public final AnonymousClass14 mWifiDisplayListener;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayAdapter$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayAdapter this$0;

        public /* synthetic */ AnonymousClass1(WifiDisplayAdapter wifiDisplayAdapter, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDisplayAdapter;
        }

        @Override // java.lang.Runnable
        public final void run() {
            RemoteDisplay remoteDisplay;
            RemoteDisplay remoteDisplay2;
            switch (this.$r8$classId) {
                case 0:
                    WifiDisplayAdapter wifiDisplayAdapter = this.this$0;
                    wifiDisplayAdapter.mDisplayController = new WifiDisplayController(wifiDisplayAdapter.mContext, ((DisplayAdapter) wifiDisplayAdapter).mHandler, wifiDisplayAdapter.mWifiDisplayListener, wifiDisplayAdapter.mPersistentDataStore);
                    WifiDisplayAdapter wifiDisplayAdapter2 = this.this$0;
                    wifiDisplayAdapter2.mDlnaController = new DlnaController(wifiDisplayAdapter2.mContext, ((DisplayAdapter) wifiDisplayAdapter2).mHandler);
                    WifiDisplayAdapter wifiDisplayAdapter3 = this.this$0;
                    wifiDisplayAdapter3.mVolumeController = new VolumeController(((DisplayAdapter) wifiDisplayAdapter3).mHandler, wifiDisplayAdapter3.mDisplayController, wifiDisplayAdapter3.mDlnaController);
                    IntentFilter intentFilter = new IntentFilter("android.server.display.wfd.DISCONNECT");
                    intentFilter.addAction("com.samsung.intent.action.SEC_PRESENTATION_START");
                    intentFilter.addAction("com.samsung.intent.action.SEC_PRESENTATION_STOP");
                    intentFilter.addAction("android.intent.action.USER_SWITCHED");
                    WifiDisplayAdapter wifiDisplayAdapter4 = this.this$0;
                    wifiDisplayAdapter4.mContext.registerReceiverAsUser(wifiDisplayAdapter4.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, wifiDisplayAdapter4.mHandler, 2);
                    break;
                case 1:
                    WifiDisplayController wifiDisplayController = this.this$0.mDisplayController;
                    if (wifiDisplayController != null && !wifiDisplayController.mScanRequested) {
                        wifiDisplayController.mScanRequested = true;
                        wifiDisplayController.enableP2p();
                        wifiDisplayController.updateScanState();
                        break;
                    }
                    break;
                case 2:
                    WifiDisplayController wifiDisplayController2 = this.this$0.mDisplayController;
                    if (wifiDisplayController2 != null && wifiDisplayController2.mScanRequested) {
                        wifiDisplayController2.mScanRequested = false;
                        wifiDisplayController2.updateScanState();
                        break;
                    }
                    break;
                case 3:
                    WifiDisplayController wifiDisplayController3 = this.this$0.mDisplayController;
                    if (wifiDisplayController3 != null && (remoteDisplay = wifiDisplayController3.mRemoteDisplay) != null) {
                        remoteDisplay.pause();
                        break;
                    }
                    break;
                case 4:
                    WifiDisplayController wifiDisplayController4 = this.this$0.mDisplayController;
                    if (wifiDisplayController4 != null && (remoteDisplay2 = wifiDisplayController4.mRemoteDisplay) != null) {
                        remoteDisplay2.resume();
                        break;
                    }
                    break;
                default:
                    WifiDisplayController wifiDisplayController5 = this.this$0.mDisplayController;
                    if (wifiDisplayController5 != null) {
                        wifiDisplayController5.mDisconnectByUser = true;
                        wifiDisplayController5.disconnect();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayAdapter$11, reason: invalid class name */
    public final class AnonymousClass11 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayAdapter this$0;
        public final /* synthetic */ boolean val$mode;

        public /* synthetic */ AnonymousClass11(WifiDisplayAdapter wifiDisplayAdapter, boolean z, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDisplayAdapter;
            this.val$mode = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    Intent intent = new Intent("com.samsung.intent.action.SET_SCREEN_RATIO_VALUE");
                    intent.putExtra("screenratiovalue", this.val$mode);
                    this.this$0.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    SystemProperties.set("wlan.wfd.screenratiovalue", String.valueOf(this.val$mode));
                    break;
                default:
                    SemWifiManager semWifiManager = (SemWifiManager) this.this$0.mContext.getSystemService("sem_wifi");
                    if (semWifiManager != null) {
                        semWifiManager.setAllowWifiScan(this.val$mode);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayAdapter$14, reason: invalid class name */
    public final class AnonymousClass14 {
        public AnonymousClass14() {
        }

        public final void onDisplayConnected(WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3) {
            WifiDisplay wifiDisplay2;
            synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                try {
                    WifiDisplay applyWifiDisplayAlias = WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
                    if (surface != null) {
                        WifiDisplayAdapter.m465$$Nest$maddDisplayDeviceLocked(WifiDisplayAdapter.this, applyWifiDisplayAlias, surface, i, i2, i3);
                    } else {
                        if (WifiDisplayAdapter.this.mPersistentDataStore.rememberWifiDisplay(applyWifiDisplayAlias)) {
                            WifiDisplayAdapter.this.mPersistentDataStore.saveIfNeeded();
                            WifiDisplayAdapter.this.updateRememberedDisplaysLocked();
                        }
                        applyWifiDisplayAlias.setEmptySurface(true);
                    }
                    WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                    if (wifiDisplayAdapter.mActiveDisplayState != 2 || (wifiDisplay2 = wifiDisplayAdapter.mActiveDisplay) == null || !wifiDisplay2.equals(applyWifiDisplayAlias) || WifiDisplayAdapter.this.mActiveDisplay.isEmptySurface()) {
                        if (applyWifiDisplayAlias.getMode() == 3) {
                            WifiDisplayAdapter.this.mFeatureState = 3;
                        }
                        WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                        wifiDisplayAdapter2.mActiveDisplayState = 2;
                        wifiDisplayAdapter2.mActiveDisplay = applyWifiDisplayAlias;
                        wifiDisplayAdapter2.scheduleStatusChangedBroadcastLocked();
                    }
                    WifiDisplayAdapter wifiDisplayAdapter3 = WifiDisplayAdapter.this;
                    wifiDisplayAdapter3.getClass();
                    wifiDisplayAdapter3.mHandler.post(new AnonymousClass11(wifiDisplayAdapter3, false, 1));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WifiDisplayDevice extends DisplayDevice {
        public final DisplayAddress mAddress;
        public int mCurrentDisplayRotation;
        public final int mFlags;
        public int mHeight;
        public DisplayDeviceInfo mInfo;
        public Display.Mode mMode;
        public String mName;
        public int mPendingChanges;
        public final float mRefreshRate;
        public final int mRotation;
        public int mRotationForHiddenDisplay;
        public Surface mSurface;
        public int mWidth;

        public WifiDisplayDevice(IBinder iBinder, String str, int i, int i2, int i3, String str2, Surface surface) {
            super(WifiDisplayAdapter.this, iBinder, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("wifi:", str2), WifiDisplayAdapter.this.mContext, false);
            this.mRotation = 0;
            this.mRotationForHiddenDisplay = 0;
            this.mCurrentDisplayRotation = 0;
            this.mName = str;
            this.mWidth = i;
            this.mHeight = i2;
            this.mRefreshRate = 60.0f;
            this.mFlags = i3;
            this.mAddress = DisplayAddress.fromMacAddress(str2);
            this.mSurface = surface;
            this.mMode = new Display.Mode(DisplayAdapter.NEXT_DISPLAY_MODE_ID.getAndIncrement(), i, i2, 60.0f, 60.0f, false, new float[0], new int[0]);
            this.mRotation = 0;
            this.mPendingChanges |= 1;
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
                this.mInfo.defaultModeId = this.mMode.getModeId();
                DisplayDeviceInfo displayDeviceInfo2 = this.mInfo;
                displayDeviceInfo2.supportedModes = new Display.Mode[]{this.mMode};
                displayDeviceInfo2.presentationDeadlineNanos = 1000000000 / ((int) this.mRefreshRate);
                displayDeviceInfo2.flags = this.mFlags;
                displayDeviceInfo2.rotation = this.mRotation;
                displayDeviceInfo2.type = 3;
                displayDeviceInfo2.address = this.mAddress;
                displayDeviceInfo2.touch = 2;
                int i = this.mWidth;
                int i2 = this.mHeight;
                displayDeviceInfo2.getClass();
                int min = (Math.min(i, i2) * 320) / 1080;
                displayDeviceInfo2.densityDpi = min;
                float f = min;
                displayDeviceInfo2.xDpi = f;
                displayDeviceInfo2.yDpi = f;
                DisplayDeviceInfo displayDeviceInfo3 = this.mInfo;
                displayDeviceInfo3.flags |= 8192;
                DisplayDeviceInfo displayDeviceInfo4 = WifiDisplayAdapter.this.mDefaultDisplayDeviceInfo;
                if (displayDeviceInfo4 != null) {
                    displayDeviceInfo3.supportedColorModes = displayDeviceInfo4.supportedColorModes;
                }
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean hasStableUniqueId() {
            return true;
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean isRotatedLocked() {
            int i;
            return super.isRotatedLocked() || (i = this.mCurrentDisplayRotation) == 1 || i == 3;
        }

        @Override // com.android.server.display.DisplayDevice
        public final void performTraversalLocked(SurfaceControl.Transaction transaction) {
            Surface surface;
            if (this.mSurface != null) {
                if ((this.mPendingChanges & 2) != 0) {
                    transaction.setDisplaySize(this.mDisplayToken, this.mWidth, this.mHeight);
                }
                if ((this.mPendingChanges & 1) != 0 && this.mCurrentSurface != (surface = this.mSurface)) {
                    this.mCurrentSurface = surface;
                    transaction.setDisplaySurface(this.mDisplayToken, surface);
                }
            }
            this.mPendingChanges = 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WifiDisplayHandler extends Handler {
        public WifiDisplayHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
            synchronized (wifiDisplayAdapter.mSyncRoot) {
                try {
                    if (wifiDisplayAdapter.mPendingStatusChangeBroadcast) {
                        wifiDisplayAdapter.mPendingStatusChangeBroadcast = false;
                        Intent intent = new Intent("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED");
                        intent.addFlags(1073741824);
                        intent.putExtra("android.hardware.display.extra.WIFI_DISPLAY_STATUS", (Parcelable) wifiDisplayAdapter.getWifiDisplayStatusLocked());
                        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                        makeBasic.setDeliveryGroupPolicy(1);
                        wifiDisplayAdapter.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, null, makeBasic.toBundle());
                    }
                } finally {
                }
            }
        }
    }

    /* renamed from: -$$Nest$maddDisplayDeviceLocked, reason: not valid java name */
    public static void m465$$Nest$maddDisplayDeviceLocked(final WifiDisplayAdapter wifiDisplayAdapter, WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3) {
        wifiDisplayAdapter.removeDisplayDeviceLocked();
        PersistentDataStore persistentDataStore = wifiDisplayAdapter.mPersistentDataStore;
        if (persistentDataStore.rememberWifiDisplay(wifiDisplay)) {
            persistentDataStore.saveIfNeeded();
            wifiDisplayAdapter.updateRememberedDisplaysLocked();
            wifiDisplayAdapter.scheduleStatusChangedBroadcastLocked();
        }
        boolean z = (i3 & 1) != 0;
        int i4 = z ? wifiDisplayAdapter.mSupportsProtectedBuffers ? 268435564 : 268435556 : 268435552;
        final int i5 = (i3 & 4) != 0 ? 1 : (i3 & 8) != 0 ? 3 : 0;
        if (wifiDisplay.getMode() == 3) {
            i4 = 134217728 | ((i4 | 536871040) & (-65));
        }
        if (wifiDisplay.getMode() == 2) {
            i4 = 67108864 | ((i4 | 536871040) & (-65));
        }
        int i6 = ((wifiDisplay.getFlags() & 32) != 0 || wifiDisplay.getMode() == 1) ? 536870912 | i4 : i4;
        if (wifiDisplay.getMode() == 0) {
            wifiDisplayAdapter.setIRefreshRate(true);
        }
        String friendlyDisplayName = wifiDisplay.getFriendlyDisplayName();
        WifiDisplayDevice wifiDisplayDevice = wifiDisplayAdapter.new WifiDisplayDevice(DisplayControl.createVirtualDisplay(friendlyDisplayName, z), friendlyDisplayName, i, i2, i6, wifiDisplay.getDeviceAddress(), surface);
        wifiDisplayAdapter.mDisplayDevice = wifiDisplayDevice;
        wifiDisplayAdapter.sendDisplayDeviceEventLocked(wifiDisplayDevice, 1);
        wifiDisplayAdapter.mDisplayDevice.mRotationForHiddenDisplay = i5;
        if (i5 != 0) {
            ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                    int i7 = i5;
                    wifiDisplayAdapter2.getClass();
                    Intent intent = new Intent("com.samsung.intent.action.ROTATION_CHANGED");
                    intent.putExtra("rotation", i7);
                    intent.putExtra("waitForDeviceAdded", true);
                    wifiDisplayAdapter2.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                }
            });
        }
    }

    /* renamed from: -$$Nest$mfixRememberedDisplayNamesFromAvailableDisplaysLocked, reason: not valid java name */
    public static void m466$$Nest$mfixRememberedDisplayNamesFromAvailableDisplaysLocked(WifiDisplayAdapter wifiDisplayAdapter) {
        PersistentDataStore persistentDataStore;
        WifiDisplay wifiDisplay;
        int i = 0;
        boolean z = false;
        while (true) {
            WifiDisplay[] wifiDisplayArr = wifiDisplayAdapter.mRememberedDisplays;
            int length = wifiDisplayArr.length;
            persistentDataStore = wifiDisplayAdapter.mPersistentDataStore;
            if (i >= length) {
                break;
            }
            WifiDisplay wifiDisplay2 = wifiDisplayArr[i];
            String deviceAddress = wifiDisplay2.getDeviceAddress();
            WifiDisplay[] wifiDisplayArr2 = wifiDisplayAdapter.mAvailableDisplays;
            int length2 = wifiDisplayArr2.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length2) {
                    wifiDisplay = null;
                    break;
                }
                wifiDisplay = wifiDisplayArr2[i2];
                if (wifiDisplay.getDeviceAddress().equals(deviceAddress)) {
                    break;
                } else {
                    i2++;
                }
            }
            if (wifiDisplay != null && !wifiDisplay2.equals(wifiDisplay)) {
                Slog.d("WifiDisplayAdapter", "fixRememberedDisplayNamesFromAvailableDisplaysLocked: updating remembered display to " + wifiDisplay);
                wifiDisplayAdapter.mRememberedDisplays[i] = wifiDisplay;
                z |= persistentDataStore.rememberWifiDisplay(new WifiDisplay(wifiDisplay.getDeviceAddress(), wifiDisplay.getDeviceName(), (String) null, false, false, false, wifiDisplay.getPrimaryDeviceType()));
            }
            i++;
        }
        if (z) {
            persistentDataStore.saveIfNeeded();
        }
    }

    /* renamed from: -$$Nest$mupdateDisplaySurfaceLocked, reason: not valid java name */
    public static void m467$$Nest$mupdateDisplaySurfaceLocked(WifiDisplayAdapter wifiDisplayAdapter, Surface surface, int i, int i2) {
        wifiDisplayAdapter.getClass();
        Slog.i("WifiDisplayAdapter", "updateDisplaySurfaceLocked");
        WifiDisplayDevice wifiDisplayDevice = wifiDisplayAdapter.mDisplayDevice;
        if (wifiDisplayDevice != null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("updateSurfaceLocked = "), wifiDisplayDevice.mName, "WifiDisplayAdapter");
            WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
            if (wifiDisplayAdapter2.mDisplayDevice.mSurface.equals(surface) && wifiDisplayDevice.mWidth == i && wifiDisplayDevice.mHeight == i2) {
                return;
            }
            wifiDisplayAdapter2.sendDisplayDeviceEventLocked(wifiDisplayAdapter2.mDisplayDevice, 2);
            Surface surface2 = wifiDisplayDevice.mSurface;
            if (surface2 != null) {
                surface2.release();
            }
            wifiDisplayAdapter2.sendTraversalRequestLocked();
            wifiDisplayDevice.mSurface = surface;
            wifiDisplayDevice.mWidth = i;
            wifiDisplayDevice.mHeight = i2;
            wifiDisplayDevice.mMode = new Display.Mode(DisplayAdapter.NEXT_DISPLAY_MODE_ID.getAndIncrement(), i, i2, 60.0f, 60.0f, false, new float[0], new int[0]);
            wifiDisplayDevice.mInfo = null;
            wifiDisplayDevice.mPendingChanges |= 3;
        }
    }

    /* JADX WARN: Type inference failed for: r8v2, types: [com.android.server.display.WifiDisplayAdapter$13] */
    public WifiDisplayAdapter(DisplayManagerService.SyncRoot syncRoot, Context context, Handler handler, DisplayAdapter.Listener listener, PersistentDataStore persistentDataStore, DisplayManagerFlags displayManagerFlags) {
        super(syncRoot, context, handler, listener, "WifiDisplayAdapter", displayManagerFlags);
        WifiDisplay[] wifiDisplayArr = WifiDisplay.EMPTY_ARRAY;
        this.mDisplays = wifiDisplayArr;
        this.mAvailableDisplays = wifiDisplayArr;
        this.mRememberedDisplays = wifiDisplayArr;
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayAdapter.13
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.server.display.wfd.DISCONNECT".equals(action)) {
                    synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                        WifiDisplayAdapter.this.requestDisconnectLocked();
                    }
                    return;
                }
                final boolean z = true;
                if ("com.samsung.intent.action.SEC_PRESENTATION_START".equals(action)) {
                    try {
                        final String stringExtra = intent.getStringExtra("ownerPackageName");
                        final int intExtra = intent.getIntExtra("displayID", -1);
                        Slog.d("WifiDisplayAdapter", "SEM_PRESENTATION_START displayID : " + intExtra + ", PackageName : " + stringExtra);
                        if (intExtra <= -1 || intExtra == 1) {
                            return;
                        }
                        final WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                        wifiDisplayAdapter.getClass();
                        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.10
                            @Override // java.lang.Runnable
                            public final void run() {
                                Handler handler2 = ((DisplayAdapter) WifiDisplayAdapter.this).mHandler;
                                int i = intExtra;
                                boolean z2 = z;
                                ((DisplayAdapter) WifiDisplayAdapter.this).mHandler.sendMessage(handler2.obtainMessage(21, i, z2 ? 1 : 0, stringExtra));
                            }
                        });
                        return;
                    } catch (Exception e) {
                        Slog.e("WifiDisplayAdapter", "SEM_PRESENTATION_START Error : " + e.toString());
                        return;
                    }
                }
                if (!"com.samsung.intent.action.SEC_PRESENTATION_STOP".equals(action)) {
                    "android.intent.action.USER_SWITCHED".equals(action);
                    return;
                }
                try {
                    final String stringExtra2 = intent.getStringExtra("ownerPackageName");
                    final int intExtra2 = intent.getIntExtra("displayID", -1);
                    Slog.d("WifiDisplayAdapter", "SEM_PRESENTATION_STOP displayID : " + intExtra2 + ", PackageName : " + stringExtra2);
                    if (intExtra2 <= -1 || intExtra2 == 1) {
                        return;
                    }
                    final WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                    wifiDisplayAdapter2.getClass();
                    final boolean z2 = false;
                    ((DisplayAdapter) wifiDisplayAdapter2).mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.10
                        @Override // java.lang.Runnable
                        public final void run() {
                            Handler handler2 = ((DisplayAdapter) WifiDisplayAdapter.this).mHandler;
                            int i = intExtra2;
                            boolean z22 = z2;
                            ((DisplayAdapter) WifiDisplayAdapter.this).mHandler.sendMessage(handler2.obtainMessage(21, i, z22 ? 1 : 0, stringExtra2));
                        }
                    });
                } catch (Exception e2) {
                    Slog.e("WifiDisplayAdapter", "SEM_PRESENTATION_STOP Error : " + e2.toString());
                }
            }
        };
        this.mWifiDisplayListener = new AnonymousClass14();
        if (!context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
            throw new RuntimeException("WiFi display was requested, but there is no WiFi Direct feature");
        }
        this.mHandler = new WifiDisplayHandler(handler.getLooper());
        this.mPersistentDataStore = persistentDataStore;
        this.mSupportsProtectedBuffers = context.getResources().getBoolean(R.bool.ignore_modem_config_emergency_numbers);
    }

    @Override // com.android.server.display.DisplayAdapter
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("mCurrentStatus=" + getWifiDisplayStatusLocked());
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mFeatureState="), this.mFeatureState, printWriter, "mScanState="), this.mScanState, printWriter, "mActiveDisplayState="), this.mActiveDisplayState, printWriter, "mActiveDisplay=");
        m.append(this.mActiveDisplay);
        printWriter.println(m.toString());
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, Arrays.toString(this.mRememberedDisplays), "mPendingStatusChangeBroadcast=", BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, Arrays.toString(this.mAvailableDisplays), "mRememberedDisplays=", BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, Arrays.toString(this.mDisplays), "mAvailableDisplays=", new StringBuilder("mDisplays=")))), this.mPendingStatusChangeBroadcast, printWriter, "mSupportsProtectedBuffers="), this.mSupportsProtectedBuffers, printWriter);
        if (this.mDisplayController == null) {
            printWriter.println("mDisplayController=null");
            return;
        }
        printWriter.println("mDisplayController:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        DumpUtils.dumpAsync(super.mHandler, this.mDisplayController, indentingPrintWriter, "", 200L);
    }

    public final WifiDisplayStatus getWifiDisplayStatusLocked() {
        if (this.mCurrentStatus == null) {
            this.mCurrentStatus = new WifiDisplayStatus(this.mFeatureState, this.mScanState, this.mActiveDisplayState, this.mActiveDisplay, this.mDisplays, this.mSessionInfo);
        }
        Slog.d("WifiDisplayAdapter", "getWifiDisplayStatusLocked: result=" + this.mCurrentStatus);
        return this.mCurrentStatus;
    }

    public final void removeDisplayDeviceLocked() {
        WifiDisplayDevice wifiDisplayDevice = this.mDisplayDevice;
        if (wifiDisplayDevice != null) {
            Surface surface = wifiDisplayDevice.mSurface;
            if (surface != null) {
                surface.release();
                wifiDisplayDevice.mSurface = null;
            }
            DisplayControl.destroyVirtualDisplay(wifiDisplayDevice.mDisplayToken);
            sendDisplayDeviceEventLocked(this.mDisplayDevice, 3);
            this.mDisplayDevice = null;
            setIRefreshRate(false);
        }
    }

    public final void requestDisconnectLocked() {
        Slog.d("WifiDisplayAdapter", "requestDisconnectedLocked");
        super.mHandler.post(new AnonymousClass1(this, 5));
    }

    public final void requestForgetLocked(String str) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m("requestForgetLocked: address=", str, "WifiDisplayAdapter");
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.loadIfNeeded();
        int findRememberedWifiDisplay = persistentDataStore.findRememberedWifiDisplay(str);
        if (findRememberedWifiDisplay >= 0) {
            persistentDataStore.mRememberedWifiDisplays.remove(findRememberedWifiDisplay);
            persistentDataStore.mDirty = true;
            persistentDataStore.saveIfNeeded();
            updateRememberedDisplaysLocked();
            scheduleStatusChangedBroadcastLocked();
        }
        WifiDisplay wifiDisplay = this.mActiveDisplay;
        if (wifiDisplay == null || !wifiDisplay.getDeviceAddress().equals(str)) {
            return;
        }
        requestDisconnectLocked();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void requestRenameLocked(java.lang.String r12, java.lang.String r13) {
        /*
            r11 = this;
            java.lang.String r0 = "requestRenameLocked: address="
            java.lang.String r1 = ", alias="
            java.lang.String r2 = "WifiDisplayAdapter"
            com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0.m(r0, r12, r1, r13, r2)
            r0 = 0
            if (r13 == 0) goto L1e
            java.lang.String r13 = r13.trim()
            boolean r1 = r13.isEmpty()
            if (r1 != 0) goto L20
            boolean r1 = r13.equals(r12)
            if (r1 == 0) goto L1e
            goto L20
        L1e:
            r5 = r13
            goto L21
        L20:
            r5 = r0
        L21:
            com.android.server.display.PersistentDataStore r13 = r11.mPersistentDataStore
            r13.loadIfNeeded()
            int r1 = r13.findRememberedWifiDisplay(r12)
            if (r1 < 0) goto L35
            java.util.ArrayList r2 = r13.mRememberedWifiDisplays
            java.lang.Object r1 = r2.get(r1)
            android.hardware.display.WifiDisplay r1 = (android.hardware.display.WifiDisplay) r1
            goto L36
        L35:
            r1 = r0
        L36:
            if (r1 == 0) goto L63
            java.lang.String r2 = r1.getDeviceAlias()
            boolean r2 = java.util.Objects.equals(r2, r5)
            if (r2 != 0) goto L63
            android.hardware.display.WifiDisplay r10 = new android.hardware.display.WifiDisplay
            java.lang.String r4 = r1.getDeviceName()
            java.lang.String r9 = r1.getPrimaryDeviceType()
            r6 = 0
            r7 = 0
            r8 = 0
            r2 = r10
            r3 = r12
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            boolean r1 = r13.rememberWifiDisplay(r10)
            if (r1 == 0) goto L63
            r13.saveIfNeeded()
            r11.updateRememberedDisplaysLocked()
            r11.scheduleStatusChangedBroadcastLocked()
        L63:
            android.hardware.display.WifiDisplay r13 = r11.mActiveDisplay
            if (r13 == 0) goto L91
            java.lang.String r13 = r13.getDeviceAddress()
            boolean r12 = r13.equals(r12)
            if (r12 == 0) goto L91
            android.hardware.display.WifiDisplay r12 = r11.mActiveDisplay
            java.lang.String r12 = r12.getFriendlyDisplayName()
            com.android.server.display.WifiDisplayAdapter$WifiDisplayDevice r13 = r11.mDisplayDevice
            if (r13 == 0) goto L91
            com.android.server.display.DisplayDeviceInfo r13 = r13.getDisplayDeviceInfoLocked()
            java.lang.String r13 = r13.name
            boolean r13 = r13.equals(r12)
            if (r13 != 0) goto L91
            com.android.server.display.WifiDisplayAdapter$WifiDisplayDevice r13 = r11.mDisplayDevice
            r13.mName = r12
            r13.mInfo = r0
            r12 = 2
            r11.sendDisplayDeviceEventLocked(r13, r12)
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.WifiDisplayAdapter.requestRenameLocked(java.lang.String, java.lang.String):void");
    }

    public final boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
        WifiDisplayController wifiDisplayController = this.mDisplayController;
        if (wifiDisplayController == null) {
            return false;
        }
        if (wifiDisplayController.mAdvertisedDisplay != null && semWifiDisplayParameter != null) {
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("requestWifiDisplayParameter, parametersGroup : ", str, " parameter : ");
            m.append(semWifiDisplayParameter.toString());
            Slog.d("WifiDisplayController", m.toString());
            str.getClass();
            switch (str) {
                case "initparams":
                    wifiDisplayController.setParam("setc", WifiDisplayController.wifiDisplayParameterToJOSNArray("initparams", semWifiDisplayParameter));
                    return true;
                case "setparams":
                    wifiDisplayController.setParam("setp", WifiDisplayController.wifiDisplayParameterToJOSNArray("setparams", semWifiDisplayParameter));
                    return true;
                case "getparams":
                    return true;
            }
        }
        return false;
    }

    public final void scheduleStatusChangedBroadcastLocked() {
        this.mCurrentStatus = null;
        if (this.mPendingStatusChangeBroadcast) {
            return;
        }
        this.mPendingStatusChangeBroadcast = true;
        this.mHandler.sendEmptyMessage(1);
    }

    public final void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) {
        boolean z;
        WifiDisplayController wifiDisplayController;
        DlnaController dlnaController = this.mDlnaController;
        dlnaController.getClass();
        StringBuilder sb = new StringBuilder("setDlnaDevice ::type = ");
        int dlnaType = semDlnaDevice.getDlnaType();
        String str = KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
        sb.append(dlnaType != 0 ? dlnaType != 1 ? dlnaType != 2 ? dlnaType != 3 ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : "Music_chn" : "Music" : "Image" : "Video");
        sb.append(", state = ");
        int connectionState = semDlnaDevice.getConnectionState();
        if (connectionState == 0) {
            str = "Not_connected";
        } else if (connectionState == 1) {
            str = "Connected";
        } else if (connectionState == 2) {
            str = "Error";
        } else if (connectionState == 3) {
            str = "Connecting";
        }
        sb.append(str);
        sb.append(", name = ");
        sb.append(semDlnaDevice.getDeviceName());
        Log.d("DlnaController", sb.toString());
        if (semDlnaDevice.getDlnaType() == -1 || semDlnaDevice.getDlnaType() == 3) {
            z = false;
        } else {
            z = dlnaController.mDevice.getConnectionState() != semDlnaDevice.getConnectionState();
            if (iBinder != null && dlnaController.mDlnaMonitor == null) {
                dlnaController.mDlnaMonitor = dlnaController.new DlnaClientDeathMonitor(iBinder, semDlnaDevice.getDlnaType());
            }
            dlnaController.mDevice = semDlnaDevice;
            dlnaController.mHandler.post(new DlnaController.AnonymousClass1(1, dlnaController));
        }
        if (!z || (wifiDisplayController = this.mDisplayController) == null) {
            return;
        }
        if (wifiDisplayController.mAdvertisedDisplay != null) {
            int connectionState2 = semDlnaDevice.getConnectionState();
            Handler handler = super.mHandler;
            if (connectionState2 == 1) {
                Slog.d("WifiDisplayAdapter", "requestPauseLocked");
                handler.post(new AnonymousClass1(this, 3));
            } else if (connectionState2 == 0 || connectionState2 == 2) {
                Slog.d("WifiDisplayAdapter", "requestResumeLocked");
                handler.post(new AnonymousClass1(this, 4));
            }
        }
    }

    public final void setIRefreshRate(boolean z) {
        IDisplayManager asInterface;
        if (!z) {
            if (this.mIRefreshRateToken != null) {
                try {
                    Slog.i("WifiDisplayAdapter", "setIRefreshRate release");
                    this.mIRefreshRateToken.release();
                } catch (RemoteException e) {
                    Slog.e("WifiDisplayAdapter", "setIRefreshRate exception : " + e);
                }
                this.mIRefreshRateToken = null;
                return;
            }
            return;
        }
        if (this.mIRefreshRateToken != null || (asInterface = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"))) == null) {
            return;
        }
        try {
            Slog.i("WifiDisplayAdapter", "setIRefreshRate acquire");
            this.mIRefreshRateToken = asInterface.acquireLowRefreshRateToken((IBinder) null, "WifiDisplayAdapter");
        } catch (RemoteException e2) {
            Slog.e("WifiDisplayAdapter", "setIRefreshRate acquire exception : " + e2);
        }
    }

    public final void updateDisplaysLocked() {
        ArrayList arrayList = new ArrayList(this.mAvailableDisplays.length + this.mRememberedDisplays.length);
        boolean[] zArr = new boolean[this.mAvailableDisplays.length];
        for (WifiDisplay wifiDisplay : this.mRememberedDisplays) {
            int i = 0;
            while (true) {
                WifiDisplay[] wifiDisplayArr = this.mAvailableDisplays;
                if (i < wifiDisplayArr.length) {
                    if (wifiDisplay.equals(wifiDisplayArr[i])) {
                        zArr[i] = true;
                        break;
                    }
                    i++;
                } else if (wifiDisplay.equals(this.mActiveDisplay)) {
                    arrayList.add(this.mActiveDisplay);
                } else {
                    arrayList.add(new WifiDisplay(wifiDisplay.getDeviceAddress(), wifiDisplay.getDeviceName(), wifiDisplay.getDeviceAlias(), false, false, true, wifiDisplay.getPrimaryDeviceType()));
                }
            }
        }
        int i2 = 0;
        while (true) {
            WifiDisplay[] wifiDisplayArr2 = this.mAvailableDisplays;
            if (i2 >= wifiDisplayArr2.length) {
                this.mDisplays = (WifiDisplay[]) arrayList.toArray(WifiDisplay.EMPTY_ARRAY);
                return;
            }
            WifiDisplay wifiDisplay2 = wifiDisplayArr2[i2];
            WifiDisplay wifiDisplay3 = new WifiDisplay(wifiDisplay2.getDeviceAddress(), wifiDisplay2.getDeviceName(), wifiDisplay2.getDeviceAlias(), true, wifiDisplay2.canConnect(), zArr[i2], wifiDisplay2.getPrimaryDeviceType());
            wifiDisplay3.setSamsungDeviceType(wifiDisplay2.getSamsungDeviceType());
            wifiDisplay3.setSamsungDeviceIcon(wifiDisplay2.getSamsungDeviceIcon());
            wifiDisplay3.setBluetoothMacAddress(wifiDisplay2.getBluetoothMacAddress());
            wifiDisplay3.setScreenSharingHashedDi(wifiDisplay2.getScreenSharingHashedDi());
            wifiDisplay3.setDeviceInfo(wifiDisplay2.getDeviceInfo());
            arrayList.add(wifiDisplay3);
            i2++;
        }
    }

    public final void updateRememberedDisplaysLocked() {
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.loadIfNeeded();
        ArrayList arrayList = persistentDataStore.mRememberedWifiDisplays;
        this.mRememberedDisplays = (WifiDisplay[]) arrayList.toArray(new WifiDisplay[arrayList.size()]);
        this.mActiveDisplay = persistentDataStore.applyWifiDisplayAlias(this.mActiveDisplay);
        this.mAvailableDisplays = persistentDataStore.applyWifiDisplayAliases(this.mAvailableDisplays);
        updateDisplaysLocked();
    }
}
