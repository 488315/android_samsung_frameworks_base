package com.android.server.display;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.display.SemWifiDisplayConfig;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplaySessionInfo;
import android.media.AudioManager;
import android.media.RemoteDisplay;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pWfdInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.Surface;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.display.DlnaController;
import com.android.server.display.WifiDisplayAdapter;
import com.samsung.android.game.IGameManagerCallback;
import com.samsung.android.game.IGameManagerService;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.wfd.WFDUibcManager;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifi.p2p.SemWifiP2pDevice;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WifiDisplayController implements DumpUtils.Dump {
    public WifiDisplay mAdvertisedDisplay;
    public int mAdvertisedDisplayFlags;
    public int mAdvertisedDisplayHeight;
    public Surface mAdvertisedDisplaySurface;
    public int mAdvertisedDisplayWidth;
    public final AudioManager mAudioManager;
    public final AnonymousClass20 mAudioModeChangedReceiver;
    public IWifiDisplayConnectionCallback mCallback;
    public WifiP2pDevice mCancelingDevice;
    public WifiP2pDevice mConnectedDevice;
    public WifiP2pGroup mConnectedDeviceGroupInfo;
    public WifiP2pDevice mConnectingDevice;
    public int mConnectionRetriesLeft;
    public final Context mContext;
    public WifiP2pDevice mDesiredDevice;
    public boolean mDisconnectByUser;
    public WifiP2pDevice mDisconnectingDevice;
    public boolean mDiscoverPeersInProgress;
    public final AnonymousClass32 mGameCallback;
    public IGameManagerService mGameManagerService;
    public final Handler mHandler;
    public final AnonymousClass20 mInitiateReceiver;
    public boolean mIsDisplayVolumeControlSupported;
    public boolean mIsPortraitDisplay;
    public boolean mIsSupportInitiateMirroring;
    public boolean mIsUibcAvailable;
    public final WifiDisplayAdapter.AnonymousClass14 mListener;
    public String mLocalDeviceP2pMacAddress;
    public final WifiDisplayMcfManager mMcfManager;
    public final AnonymousClass35 mNativeListener;
    public NetworkInfo mNetworkInfo;
    public int mP2pFrequency;
    public final AnonymousClass20 mP2pReceiver;
    public final PersistentDataStore mPersistentDataStore;
    public int mPrevMusicStreamVolume;
    public RemoteDisplay mRemoteDisplay;
    public boolean mRemoteDisplayConnected;
    public String mRemoteDisplayInterface;
    public boolean mScanRequested;
    public boolean mScreenWakeUpByUser;
    public final SecureRandom mSecureRandom;
    public final SemWifiP2pManager mSemWifiP2pManager;
    public WifiP2pDevice mThisDevice;
    public String mViewMode;
    public boolean mWfdEnabled;
    public boolean mWfdEnabling;
    public final WFDUibcManager mWfdUibcManager;
    public final AnonymousClass6 mWifiDisable;
    public boolean mWifiDisplayCertMode;
    public SemWifiDisplayConfig mWifiDisplayConfig;
    public boolean mWifiDisplayOnSetting;
    public final WifiManager mWifiManager;
    public WifiP2pManager.Channel mWifiP2pChannel;
    public boolean mWifiP2pEnabled;
    public WifiP2pManager mWifiP2pManager;
    public boolean mWifiTurnedOnByController;
    public final AnonymousClass36 remoteDisplayListener;
    public int mRequestedScanChannel = 1611;
    public int mRequestedScanInterval = 1000;
    public final ArrayList mAvailableWifiDisplayPeers = new ArrayList();
    public int mTransportMode = 0;
    public int mPrevAudioMode = 0;
    public List mParameterList = new ArrayList();
    public final AnonymousClass6 mDiscoverPeers = new AnonymousClass6(this, 1);
    public final AnonymousClass6 mConnectionTimeout = new AnonymousClass6(this, 2);
    public final AnonymousClass6 mRtspTimeout = new AnonymousClass6(this, 3);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$10, reason: invalid class name */
    public final class AnonymousClass10 implements WifiP2pManager.ActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayController this$0;
        public final /* synthetic */ WifiP2pDevice val$oldDevice;

        public /* synthetic */ AnonymousClass10(WifiDisplayController wifiDisplayController, WifiP2pDevice wifiP2pDevice, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDisplayController;
            this.val$oldDevice = wifiP2pDevice;
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onFailure(int i) {
            switch (this.$r8$classId) {
                case 0:
                    Log.i("WifiDisplayController", "Failed to disconnect from Wifi display: " + this.val$oldDevice.deviceName + ", reason=" + i);
                    WifiDisplayController wifiDisplayController = this.this$0;
                    if (wifiDisplayController.mDisconnectingDevice == this.val$oldDevice) {
                        wifiDisplayController.unadvertiseDisplay();
                        WifiDisplayController wifiDisplayController2 = this.this$0;
                        wifiDisplayController2.mDisconnectingDevice = null;
                        wifiDisplayController2.updateConnection();
                        break;
                    }
                    break;
                case 1:
                    Slog.i("WifiDisplayController", "Failed to disconnect from Wifi display: " + this.val$oldDevice.deviceName + ", reason=" + i);
                    WifiDisplayController wifiDisplayController3 = this.this$0;
                    if (wifiDisplayController3.mDisconnectingDevice == this.val$oldDevice) {
                        wifiDisplayController3.mDisconnectingDevice = null;
                        wifiDisplayController3.updateConnection();
                        break;
                    }
                    break;
                default:
                    if (this.this$0.mConnectingDevice == this.val$oldDevice) {
                        Slog.i("WifiDisplayController", "Failed to initiate connection to Wifi display: " + this.val$oldDevice.deviceName + ", reason=" + i);
                        this.this$0.handleConnectionFailure(6);
                        this.this$0.mConnectingDevice = null;
                        break;
                    }
                    break;
            }
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onSuccess() {
            switch (this.$r8$classId) {
                case 0:
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("Disconnected from Wifi display: "), this.val$oldDevice.deviceName, "WifiDisplayController");
                    WifiDisplayController wifiDisplayController = this.this$0;
                    if (wifiDisplayController.mDisconnectingDevice == this.val$oldDevice) {
                        wifiDisplayController.unadvertiseDisplay();
                        WifiDisplayController wifiDisplayController2 = this.this$0;
                        wifiDisplayController2.mDisconnectingDevice = null;
                        wifiDisplayController2.updateConnection();
                        break;
                    }
                    break;
                case 1:
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Disconnected from Wifi display: "), this.val$oldDevice.deviceName, "WifiDisplayController");
                    WifiDisplayController wifiDisplayController3 = this.this$0;
                    if (wifiDisplayController3.mDisconnectingDevice == this.val$oldDevice) {
                        wifiDisplayController3.mDisconnectingDevice = null;
                        wifiDisplayController3.updateConnection();
                        break;
                    }
                    break;
                default:
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Initiated connection to Wifi display: "), this.val$oldDevice.deviceName, "WifiDisplayController");
                    if (!this.this$0.mWifiDisplayConfig.isPinRequest()) {
                        WifiDisplayController wifiDisplayController4 = this.this$0;
                        wifiDisplayController4.mHandler.postDelayed(wifiDisplayController4.mConnectionTimeout, 30000L);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$21, reason: invalid class name */
    public final class AnonymousClass21 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayController this$0;
        public final /* synthetic */ int val$mode;
        public final /* synthetic */ int val$state;

        public AnonymousClass21(WifiDisplayController wifiDisplayController, int i) {
            this.$r8$classId = 1;
            this.this$0 = wifiDisplayController;
            this.val$state = 8;
            this.val$mode = i;
        }

        public /* synthetic */ AnonymousClass21(WifiDisplayController wifiDisplayController, int i, int i2, int i3) {
            this.$r8$classId = i3;
            this.this$0 = wifiDisplayController;
            this.val$state = i;
            this.val$mode = i2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    StringBuilder sb = new StringBuilder("handleWifiDisplayStateBroadcast:: state = ");
                    sb.append(this.val$state == 1 ? "ON" : "OFF");
                    sb.append(", [VolumeControl] isVolumeControlSupported: ");
                    sb.append(this.this$0.mIsDisplayVolumeControlSupported);
                    sb.append(", disconnectByUser: ");
                    sb.append(this.this$0.mDisconnectByUser);
                    Slog.d("WifiDisplayController", sb.toString());
                    Intent intent = new Intent("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
                    intent.addFlags(67108864);
                    intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, this.val$state);
                    intent.putExtra("isSupportDisplayVolumeControl", this.this$0.mIsDisplayVolumeControlSupported);
                    intent.putExtra("by_user", this.this$0.mDisconnectByUser);
                    intent.putExtra("isPortraitDisplay", this.this$0.mIsPortraitDisplay);
                    intent.putExtra("mode", this.val$mode);
                    this.this$0.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    WifiDisplayController wifiDisplayController = this.this$0;
                    if (wifiDisplayController.mIsDisplayVolumeControlSupported) {
                        if (this.val$state == 1) {
                            wifiDisplayController.mPrevMusicStreamVolume = wifiDisplayController.mAudioManager.semGetStreamVolume(3, 25);
                            this.this$0.mAudioManager.semSetStreamVolume(3, 15, 0, 25);
                        } else {
                            int semGetStreamVolume = wifiDisplayController.mAudioManager.semGetStreamVolume(3, 25);
                            WifiDisplayController wifiDisplayController2 = this.this$0;
                            AudioManager audioManager = wifiDisplayController2.mAudioManager;
                            if (semGetStreamVolume == 15) {
                                semGetStreamVolume = wifiDisplayController2.mPrevMusicStreamVolume;
                            }
                            audioManager.semSetStreamVolume(3, semGetStreamVolume, 0, 25);
                        }
                    }
                    this.this$0.mDisconnectByUser = false;
                    break;
                case 1:
                    Slog.d("WifiDisplayController", "sendEventToSemDeviceStatusListener");
                    Bundle bundle = new Bundle();
                    if (this.val$state == 8) {
                        bundle.putInt(Constants.JSON_CLIENT_DATA_STATUS, this.val$mode);
                    }
                    Message obtainMessage = this.this$0.mHandler.obtainMessage(20, this.val$state, 0);
                    obtainMessage.setData(bundle);
                    this.this$0.mHandler.sendMessage(obtainMessage);
                    break;
                default:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(Constants.JSON_CLIENT_DATA_STATUS, this.val$state);
                    Message obtainMessage2 = this.this$0.mHandler.obtainMessage(20, this.val$mode, 0);
                    obtainMessage2.setData(bundle2);
                    StringBuilder sb2 = new StringBuilder("sendRemoteDisplayStateChangeEvent state : ");
                    sb2.append(this.val$state);
                    sb2.append(", event : ");
                    DeviceIdleController$$ExternalSyntheticOutline0.m(sb2, this.val$mode, "WifiDisplayController");
                    this.this$0.mHandler.sendMessage(obtainMessage2);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$29, reason: invalid class name */
    public final class AnonymousClass29 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayController this$0;
        public final /* synthetic */ boolean val$status;

        public /* synthetic */ AnonymousClass29(WifiDisplayController wifiDisplayController, boolean z, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDisplayController;
            this.val$status = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    Log.d("WifiDisplayController", "sendSSRMRequestIntent() : " + this.val$status);
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.intent.action.SSRM_REQUEST");
                    intent.putExtra("SSRM_STATUS_NAME", "ScreenMirroring_enable");
                    intent.putExtra("SSRM_STATUS_VALUE", this.val$status);
                    intent.putExtra("PID", Process.myPid());
                    intent.putExtra("PackageName", this.this$0.mContext.getPackageName());
                    this.this$0.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    break;
                default:
                    SemWifiManager semWifiManager = (SemWifiManager) this.this$0.mContext.getSystemService("sem_wifi");
                    if (semWifiManager != null) {
                        semWifiManager.setAllowWifiScan(this.val$status);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$3, reason: invalid class name */
    public final class AnonymousClass3 implements WifiP2pManager.ActionListener {
        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onFailure(int i) {
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onSuccess() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public final /* synthetic */ int val$featureState;

        public AnonymousClass4(int i) {
            this.val$featureState = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            WifiDisplayAdapter.AnonymousClass14 anonymousClass14 = WifiDisplayController.this.mListener;
            int i = this.val$featureState;
            synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                    if (wifiDisplayAdapter.mFeatureState != i) {
                        wifiDisplayAdapter.mFeatureState = i;
                        wifiDisplayAdapter.scheduleStatusChangedBroadcastLocked();
                        WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                        if (wifiDisplayAdapter2.mFeatureState == 2) {
                            wifiDisplayAdapter2.mDisplays = WifiDisplay.EMPTY_ARRAY;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$6, reason: invalid class name */
    public final class AnonymousClass6 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayController this$0;

        public /* synthetic */ AnonymousClass6(WifiDisplayController wifiDisplayController, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDisplayController;
        }

        @Override // java.lang.Runnable
        public final void run() {
            WifiDisplayController wifiDisplayController;
            WifiP2pDevice wifiP2pDevice;
            WifiDisplay wifiDisplay;
            switch (this.$r8$classId) {
                case 0:
                    WifiDisplayAdapter.AnonymousClass14 anonymousClass14 = this.this$0.mListener;
                    synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                        try {
                            WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                            if (wifiDisplayAdapter.mScanState != 1) {
                                if (wifiDisplayAdapter.mActiveDisplay != null) {
                                    Slog.i("WifiDisplayAdapter", "onScanStarted in counnected status");
                                    WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                                    wifiDisplayAdapter2.mDisplays = new WifiDisplay[]{wifiDisplayAdapter2.mActiveDisplay};
                                } else {
                                    Slog.i("WifiDisplayAdapter", "onScanStarted");
                                    WifiDisplayAdapter.this.mDisplays = WifiDisplay.EMPTY_ARRAY;
                                }
                                WifiDisplayAdapter wifiDisplayAdapter3 = WifiDisplayAdapter.this;
                                wifiDisplayAdapter3.mScanState = 1;
                                wifiDisplayAdapter3.scheduleStatusChangedBroadcastLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                case 1:
                    this.this$0.tryDiscoverPeers(0, 10000);
                    return;
                case 2:
                    if (WifiDisplayController.m469$$Nest$misVpnConnected(this.this$0) || (wifiP2pDevice = (wifiDisplayController = this.this$0).mConnectingDevice) == null || wifiP2pDevice != wifiDisplayController.mDesiredDevice) {
                        return;
                    }
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Timed out waiting for Wifi display connection after 30 seconds: "), this.this$0.mConnectingDevice.deviceName, "WifiDisplayController");
                    this.this$0.handleConnectionFailure(2);
                    return;
                case 3:
                    if (WifiDisplayController.m469$$Nest$misVpnConnected(this.this$0)) {
                        return;
                    }
                    WifiDisplayController wifiDisplayController2 = this.this$0;
                    if (wifiDisplayController2.mConnectedDevice == null || wifiDisplayController2.mRemoteDisplay == null || wifiDisplayController2.mRemoteDisplayConnected) {
                        return;
                    }
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Timed out waiting for Wifi display RTSP connection after 30 seconds: "), this.this$0.mConnectedDevice.deviceName, "WifiDisplayController");
                    this.this$0.handleConnectionFailure(2);
                    return;
                case 4:
                    WifiDisplayController wifiDisplayController3 = this.this$0;
                    boolean z = wifiDisplayController3.mIsDisplayVolumeControlSupported && (wifiDisplay = wifiDisplayController3.mAdvertisedDisplay) != null && wifiDisplay.getState() == 6;
                    Slog.d("WifiDisplayController", "[VolumeControl] Send SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED " + z);
                    Intent intent = new Intent("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED");
                    intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 1);
                    intent.putExtra("isSupportDisplayVolumeControl", z);
                    this.this$0.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    return;
                case 5:
                    Slog.i("WifiDisplayController", "wakeUpScreen");
                    PowerManager powerManager = (PowerManager) this.this$0.mContext.getSystemService("power");
                    if (powerManager.isInteractive()) {
                        this.this$0.mScreenWakeUpByUser = true;
                    } else {
                        this.this$0.mScreenWakeUpByUser = false;
                        powerManager.wakeUp(SystemClock.uptimeMillis(), 2, "WifiDisplay WakeUpScreen");
                    }
                    ((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semDismissKeyguard();
                    return;
                case 6:
                    AnyMotionDetector$$ExternalSyntheticOutline0.m("WifiDisplayController", new StringBuilder("try to turn off wifi, mWifiDisplayOnSetting : "), this.this$0.mWifiDisplayOnSetting);
                    WifiDisplayController wifiDisplayController4 = this.this$0;
                    if (wifiDisplayController4.mWifiDisplayOnSetting) {
                        return;
                    }
                    if (wifiDisplayController4.mWifiManager.isWifiEnabled()) {
                        Slog.i("WifiDisplayController", "turn off wifi");
                        this.this$0.mWifiManager.setWifiEnabled(false);
                    }
                    this.this$0.mWifiTurnedOnByController = false;
                    return;
                default:
                    WifiDisplayAdapter.AnonymousClass14 anonymousClass142 = this.this$0.mListener;
                    synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                        WifiDisplayAdapter wifiDisplayAdapter4 = WifiDisplayAdapter.this;
                        if (wifiDisplayAdapter4.mScanState != 0) {
                            wifiDisplayAdapter4.mScanState = 0;
                            wifiDisplayAdapter4.scheduleStatusChangedBroadcastLocked();
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$7, reason: invalid class name */
    public final class AnonymousClass7 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDisplayController this$0;
        public final /* synthetic */ Object val$displays;

        public /* synthetic */ AnonymousClass7(WifiDisplayController wifiDisplayController, Object obj, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDisplayController;
            this.val$displays = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    WifiDisplayAdapter.AnonymousClass14 anonymousClass14 = this.this$0.mListener;
                    WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) this.val$displays;
                    synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                        try {
                            WifiDisplay[] applyWifiDisplayAliases = WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAliases(wifiDisplayArr);
                            boolean z = !Arrays.equals(WifiDisplayAdapter.this.mAvailableDisplays, applyWifiDisplayAliases);
                            for (int i = 0; !z && i < applyWifiDisplayAliases.length; i++) {
                                z = applyWifiDisplayAliases[i].canConnect() != WifiDisplayAdapter.this.mAvailableDisplays[i].canConnect();
                            }
                            if (z) {
                                WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                                wifiDisplayAdapter.mAvailableDisplays = applyWifiDisplayAliases;
                                WifiDisplayAdapter.m466$$Nest$mfixRememberedDisplayNamesFromAvailableDisplaysLocked(wifiDisplayAdapter);
                                WifiDisplayAdapter.this.updateDisplaysLocked();
                                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    this.this$0.mHandler.sendMessage(this.this$0.mHandler.obtainMessage(25, 16, 0, (List) this.val$displays));
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.WifiDisplayController$9, reason: invalid class name */
    public final class AnonymousClass9 implements SemWifiP2pManager.ActionListener {
        public final void onFailure(int i) {
        }

        public final void onSuccess() {
        }
    }

    /* renamed from: -$$Nest$mfinish, reason: not valid java name */
    public static void m468$$Nest$mfinish(WifiDisplayController wifiDisplayController) {
        wifiDisplayController.getClass();
        Log.d("WifiDisplayController", "finish");
        wifiDisplayController.mConnectedDevice = null;
        wifiDisplayController.mDesiredDevice = null;
        wifiDisplayController.mDisconnectingDevice = null;
        wifiDisplayController.mTransportMode = 0;
        wifiDisplayController.mIsSupportInitiateMirroring = false;
        wifiDisplayController.mScreenWakeUpByUser = false;
        wifiDisplayController.mIsUibcAvailable = false;
        wifiDisplayController.mPrevAudioMode = 0;
        wifiDisplayController.mHandler.post(new AnonymousClass21(wifiDisplayController, 0, wifiDisplayController.mWifiDisplayConfig.getMode(), 0));
        wifiDisplayController.mWifiDisplayConfig = new SemWifiDisplayConfig();
        wifiDisplayController.mHandler.removeCallbacks(wifiDisplayController.mRtspTimeout);
    }

    /* renamed from: -$$Nest$misVpnConnected, reason: not valid java name */
    public static boolean m469$$Nest$misVpnConnected(WifiDisplayController wifiDisplayController) {
        ConnectivityManager connectivityManager = (ConnectivityManager) wifiDisplayController.mContext.getSystemService("connectivity");
        if (connectivityManager == null || connectivityManager.getAllNetworks() == null || connectivityManager.getAllNetworks().length <= 0) {
            return false;
        }
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            if (networkCapabilities != null && networkCapabilities.hasTransport(4)) {
                Slog.i("WifiDisplayController", "Connection ended due to VPN.");
                wifiDisplayController.handleConnectionFailure(5);
                return true;
            }
        }
        return false;
    }

    /* renamed from: -$$Nest$mparseParametersFromEngine, reason: not valid java name */
    public static List m470$$Nest$mparseParametersFromEngine(WifiDisplayController wifiDisplayController, WifiDisplay wifiDisplay, String str) {
        boolean z;
        wifiDisplayController.getClass();
        Slog.i("WifiDisplayController", "parseParametersFromEngine : " + str);
        if (wifiDisplay != null && str != null) {
            try {
                ArrayList arrayList = new ArrayList();
                JSONObject jSONObject = new JSONObject(str);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    JSONArray jSONArray = jSONObject.getJSONArray(keys.next());
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        Iterator<String> keys2 = jSONObject2.keys();
                        while (keys2.hasNext()) {
                            String next = keys2.next();
                            String string = jSONObject2.getString(next);
                            if (wifiDisplayController.mParameterList.contains(next)) {
                                List list = wifiDisplayController.mParameterList;
                                if (!((SemWifiDisplayParameter) list.get(list.indexOf(next))).getValue().equals(string)) {
                                    arrayList.add(new SemWifiDisplayParameter(next, string));
                                    wifiDisplay.addParameter(next, string);
                                    List list2 = wifiDisplayController.mParameterList;
                                    list2.remove(list2.indexOf(next));
                                    wifiDisplayController.mParameterList.add(new SemWifiDisplayParameter(next, string));
                                }
                            } else {
                                arrayList.add(new SemWifiDisplayParameter(next, string));
                                wifiDisplay.addParameter(next, string);
                                wifiDisplayController.mParameterList.add(new SemWifiDisplayParameter(next, string));
                            }
                            if (wifiDisplay.getMode() == 0 && next.equals("wfd_sec_tv_ble_mac") && BluetoothAdapter.checkBluetoothAddress(string)) {
                                PersistentDataStore persistentDataStore = wifiDisplayController.mPersistentDataStore;
                                persistentDataStore.loadIfNeeded();
                                if (persistentDataStore.mRememberedInitiatedDevices.contains(string)) {
                                    persistentDataStore.mRememberedInitiatedDevices.remove(string);
                                    z = false;
                                } else {
                                    z = true;
                                }
                                persistentDataStore.mRememberedInitiatedDevices.add(string);
                                if (persistentDataStore.mRememberedInitiatedDevices.size() > 3) {
                                    persistentDataStore.mRememberedInitiatedDevices.remove(0);
                                }
                                persistentDataStore.mDirty = true;
                                if (z) {
                                    Slog.d("WifiDisplayController", "add new initiated device list");
                                    wifiDisplayController.mPersistentDataStore.saveIfNeeded();
                                    if (Build.VERSION.SEM_PLATFORM_INT >= 120100) {
                                        PersistentDataStore persistentDataStore2 = wifiDisplayController.mPersistentDataStore;
                                        persistentDataStore2.loadIfNeeded();
                                        if (!persistentDataStore2.mRememberedInitiatedDevices.isEmpty()) {
                                            wifiDisplayController.mMcfManager.initialize();
                                        }
                                    }
                                }
                            }
                            if ("wfd_sec_view_mode".equals(next) && !string.equals(wifiDisplayController.mViewMode)) {
                                wifiDisplayController.mViewMode = string;
                                SystemProperties.set("debug.wfd.viewmode", string);
                            }
                        }
                    }
                }
                return arrayList;
            } catch (JSONException e) {
                Slog.w("WifiDisplayController", e.toString());
            }
        }
        return null;
    }

    /* renamed from: -$$Nest$msendVoipModeMessageIfNecessary, reason: not valid java name */
    public static void m471$$Nest$msendVoipModeMessageIfNecessary(WifiDisplayController wifiDisplayController, WifiDisplay wifiDisplay, int i) {
        int i2 = wifiDisplayController.mPrevAudioMode;
        if (i2 != 3 && i == 3) {
            ArrayList arrayList = new ArrayList();
            if (wifiDisplay.isVoipModeSupported()) {
                arrayList.add(new SemWifiDisplayParameter("wfd_sec_voip_mode", "on"));
            } else {
                arrayList.add(new SemWifiDisplayParameter("wfd_sec_low_latency_mode", "on"));
            }
            wifiDisplayController.requestSetWifiDisplayParameters(arrayList);
        } else if (i2 == 3 && i != 3) {
            ArrayList arrayList2 = new ArrayList();
            if (wifiDisplay.isVoipModeSupported()) {
                arrayList2.add(new SemWifiDisplayParameter("wfd_sec_voip_mode", "off"));
            } else {
                arrayList2.add(new SemWifiDisplayParameter("wfd_sec_low_latency_mode", "off"));
            }
            wifiDisplayController.requestSetWifiDisplayParameters(arrayList2);
        }
        wifiDisplayController.mPrevAudioMode = i;
    }

    /* renamed from: -$$Nest$msendWifiDisplayParameterEvent, reason: not valid java name */
    public static void m472$$Nest$msendWifiDisplayParameterEvent(WifiDisplayController wifiDisplayController, List list) {
        wifiDisplayController.getClass();
        if (list != null) {
            Log.i("WifiDisplayController", "sendWifiDisplayParameterEvent, parameters : " + list.toString());
            wifiDisplayController.mHandler.post(new AnonymousClass7(wifiDisplayController, list, 1));
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.server.display.WifiDisplayController$36] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.display.WifiDisplayController$32] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.server.display.WifiDisplayController$20] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.server.display.WifiDisplayController$20] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.server.display.WifiDisplayController$35] */
    public WifiDisplayController(Context context, Handler handler, WifiDisplayAdapter.AnonymousClass14 anonymousClass14, PersistentDataStore persistentDataStore) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.display.WifiDisplayController.20
            public final /* synthetic */ WifiDisplayController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i) {
                    case 0:
                        String action = intent.getAction();
                        if (!"android.net.wifi.p2p.STATE_CHANGED".equals(action)) {
                            if (!"android.net.wifi.p2p.PEERS_CHANGED".equals(action)) {
                                if (!"android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(action)) {
                                    if (!"android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(action)) {
                                        if ("android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED".equals(action)) {
                                            boolean booleanExtra = intent.getBooleanExtra("android.net.wifi.p2p.extra.REQUEST_RESPONSE", false);
                                            DeviceIdleController$$ExternalSyntheticOutline0.m("Received WifiP2pManager.ACTION_WIFI_P2P_REQUEST_RESPONSE_CHANGED : requestAccepted = ", "WifiDisplayController", booleanExtra);
                                            WifiDisplayController wifiDisplayController = this.this$0;
                                            if (!wifiDisplayController.mWifiDisplayCertMode && wifiDisplayController.mConnectingDevice != null && wifiDisplayController.mWifiDisplayConfig.isPinRequest()) {
                                                if (!booleanExtra) {
                                                    Slog.d("WifiDisplayController", "User cancelled PIN connect or timeout");
                                                    this.this$0.disconnect();
                                                    break;
                                                } else {
                                                    Slog.d("WifiDisplayController", "User accepted PIN connect");
                                                    WifiDisplayController wifiDisplayController2 = this.this$0;
                                                    wifiDisplayController2.mHandler.postDelayed(wifiDisplayController2.mConnectionTimeout, 30000L);
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        this.this$0.mThisDevice = (WifiP2pDevice) intent.getParcelableExtra("wifiP2pDevice");
                                        WifiDisplayController wifiDisplayController3 = this.this$0;
                                        if (wifiDisplayController3.mWfdEnabled && wifiDisplayController3.mDiscoverPeersInProgress && wifiDisplayController3.mWifiP2pChannel != null && wifiDisplayController3.mWifiP2pManager != null && TextUtils.isEmpty(wifiDisplayController3.mLocalDeviceP2pMacAddress)) {
                                            WifiDisplayController wifiDisplayController4 = this.this$0;
                                            wifiDisplayController4.mWifiP2pManager.requestDeviceInfo(wifiDisplayController4.mWifiP2pChannel, new WifiP2pManager.DeviceInfoListener() { // from class: com.android.server.display.WifiDisplayController.20.1
                                                @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
                                                public final void onDeviceInfoAvailable(WifiP2pDevice wifiP2pDevice) {
                                                    if (wifiP2pDevice == null || TextUtils.isEmpty(wifiP2pDevice.deviceAddress)) {
                                                        return;
                                                    }
                                                    Log.d("WifiDisplayController", "onDeviceInfoAvailable");
                                                    AnonymousClass20.this.this$0.mLocalDeviceP2pMacAddress = wifiP2pDevice.deviceAddress;
                                                }
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                    if (this.this$0.mWifiDisplayConfig.getConnectionType() == 1) {
                                        if (networkInfo.getDetailedState() != NetworkInfo.DetailedState.CONNECTING) {
                                            final WifiDisplayController wifiDisplayController5 = this.this$0;
                                            wifiDisplayController5.mNetworkInfo = networkInfo;
                                            if (!wifiDisplayController5.mWfdEnabled || !networkInfo.isConnected()) {
                                                wifiDisplayController5.mConnectedDeviceGroupInfo = null;
                                                if (wifiDisplayController5.mConnectingDevice != null || wifiDisplayController5.mConnectedDevice != null) {
                                                    wifiDisplayController5.disconnect();
                                                }
                                                if (wifiDisplayController5.mWfdEnabled) {
                                                    wifiDisplayController5.mWifiP2pManager.requestPeers(wifiDisplayController5.mWifiP2pChannel, new WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
                                                        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
                                                        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                                                            SemWifiP2pDevice semWifiP2pDevice;
                                                            int deviceType;
                                                            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                                                            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                                                                WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
                                                                if ((wfdInfo != null && wfdInfo.isEnabled() && wfdInfo.isSessionAvailable() && ((deviceType = wfdInfo.getDeviceType()) == 1 || deviceType == 3)) || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                                                                    WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                                                                }
                                                            }
                                                            WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
                                                            if (wifiDisplayController6.mDiscoverPeersInProgress) {
                                                                int size = wifiDisplayController6.mAvailableWifiDisplayPeers.size();
                                                                WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
                                                                for (int i2 = 0; i2 < size; i2++) {
                                                                    WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) wifiDisplayController6.mAvailableWifiDisplayPeers.get(i2);
                                                                    wifiDisplayArr[i2] = wifiDisplayController6.createWifiDisplay(wifiP2pDevice2);
                                                                    if (!wifiP2pDevice2.getVendorElements().isEmpty() && (semWifiP2pDevice = wifiDisplayController6.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice2)) != null) {
                                                                        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
                                                                        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
                                                                            BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("setScreenSharingHashedDi, deviceName : "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                                                                            wifiDisplayArr[i2].setScreenSharingHashedDi(screenSharingHashedDi);
                                                                        }
                                                                    }
                                                                    String str = wifiP2pDevice2.deviceAddress;
                                                                    WifiP2pDevice wifiP2pDevice3 = wifiDisplayController6.mDesiredDevice;
                                                                    if (wifiP2pDevice3 != null && wifiP2pDevice3.deviceAddress.equals(str)) {
                                                                        wifiDisplayController6.mDesiredDevice.update(wifiP2pDevice2);
                                                                        WifiDisplay wifiDisplay = wifiDisplayController6.mAdvertisedDisplay;
                                                                        if (wifiDisplay != null && wifiDisplay.getDeviceAddress().equals(str)) {
                                                                            wifiDisplayController6.advertiseDisplay(wifiDisplayController6.createWifiDisplay(wifiDisplayController6.mDesiredDevice), wifiDisplayController6.mAdvertisedDisplaySurface, wifiDisplayController6.mAdvertisedDisplayWidth, wifiDisplayController6.mAdvertisedDisplayHeight, wifiDisplayController6.mAdvertisedDisplayFlags);
                                                                        }
                                                                    }
                                                                }
                                                                wifiDisplayController6.mHandler.post(new AnonymousClass7(wifiDisplayController6, wifiDisplayArr, 0));
                                                            }
                                                        }
                                                    });
                                                }
                                            } else if (wifiDisplayController5.mDesiredDevice != null || wifiDisplayController5.mWifiDisplayCertMode) {
                                                wifiDisplayController5.mWifiP2pManager.requestGroupInfo(wifiDisplayController5.mWifiP2pChannel, new WifiP2pManager.GroupInfoListener() { // from class: com.android.server.display.WifiDisplayController.15
                                                    @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
                                                    public final void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                                                        WifiP2pDevice wifiP2pDevice = WifiDisplayController.this.mConnectingDevice;
                                                        if (wifiP2pDevice != null && (wifiP2pGroup == null || (!wifiP2pGroup.getOwner().equals(wifiP2pDevice) && !wifiP2pGroup.getClientList().contains(wifiP2pDevice)))) {
                                                            StringBuilder sb = new StringBuilder("Aborting connection to Wifi display because the current P2P group does not contain the device we expected to find: ");
                                                            sb.append(WifiDisplayController.this.mConnectingDevice.deviceName);
                                                            sb.append(", group info was: ");
                                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, wifiP2pGroup != null ? wifiP2pGroup.toString().replace('\n', ',') : "null", "WifiDisplayController");
                                                            WifiDisplayController.this.handleConnectionFailure(6);
                                                            return;
                                                        }
                                                        WifiP2pDevice wifiP2pDevice2 = WifiDisplayController.this.mDesiredDevice;
                                                        if (wifiP2pDevice2 != null && (wifiP2pGroup == null || (!wifiP2pGroup.getOwner().equals(wifiP2pDevice2) && !wifiP2pGroup.getClientList().contains(wifiP2pDevice2)))) {
                                                            WifiDisplayController.this.disconnect();
                                                            return;
                                                        }
                                                        if (WifiDisplayController.this.mWifiDisplayCertMode) {
                                                            boolean equals = wifiP2pGroup.getOwner().deviceAddress.equals(WifiDisplayController.this.mThisDevice.deviceAddress);
                                                            if (equals && wifiP2pGroup.getClientList().isEmpty()) {
                                                                WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
                                                                wifiDisplayController6.mDesiredDevice = null;
                                                                wifiDisplayController6.mConnectingDevice = null;
                                                                wifiDisplayController6.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                                                wifiDisplayController6.updateConnection();
                                                            } else {
                                                                WifiDisplayController wifiDisplayController7 = WifiDisplayController.this;
                                                                if (wifiDisplayController7.mConnectingDevice == null && wifiDisplayController7.mDesiredDevice == null) {
                                                                    WifiP2pDevice next = equals ? wifiP2pGroup.getClientList().iterator().next() : wifiP2pGroup.getOwner();
                                                                    wifiDisplayController7.mDesiredDevice = next;
                                                                    wifiDisplayController7.mConnectingDevice = next;
                                                                    Slog.i("WifiDisplayController", "CertMode incoming from sink" + WifiDisplayController.describeWifiP2pDevice(WifiDisplayController.this.mConnectingDevice));
                                                                    WifiDisplayController wifiDisplayController8 = WifiDisplayController.this;
                                                                    wifiDisplayController8.advertiseDisplay(wifiDisplayController8.createWifiDisplay(wifiDisplayController8.mConnectingDevice), null, 0, 0, 0);
                                                                }
                                                            }
                                                        }
                                                        WifiDisplayController wifiDisplayController9 = WifiDisplayController.this;
                                                        WifiP2pDevice wifiP2pDevice3 = wifiDisplayController9.mConnectingDevice;
                                                        if (wifiP2pDevice3 == null || wifiP2pDevice3 != wifiDisplayController9.mDesiredDevice) {
                                                            return;
                                                        }
                                                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Connected to Wifi display: "), WifiDisplayController.this.mConnectingDevice.deviceName, "WifiDisplayController");
                                                        WifiDisplayController wifiDisplayController10 = WifiDisplayController.this;
                                                        wifiDisplayController10.mHandler.removeCallbacks(wifiDisplayController10.mConnectionTimeout);
                                                        WifiDisplayController wifiDisplayController11 = WifiDisplayController.this;
                                                        wifiDisplayController11.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                                        wifiDisplayController11.mConnectedDevice = wifiDisplayController11.mConnectingDevice;
                                                        wifiDisplayController11.mConnectingDevice = null;
                                                        wifiDisplayController11.mP2pFrequency = wifiP2pGroup.getFrequency();
                                                        WifiDisplayController.this.updateConnection();
                                                    }
                                                });
                                            }
                                            WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
                                            Intent intent2 = new Intent("com.samsung.intent.action.WIFI_P2P_CONNECTION_CHANGED");
                                            intent2.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
                                            intent2.putExtra("networkInfo", networkInfo);
                                            intent2.putExtra("wifiP2pInfo", wifiP2pInfo);
                                            this.this$0.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                                            break;
                                        } else {
                                            Slog.w("WifiDisplayController", "do not handle p2p CONNECTING state");
                                            break;
                                        }
                                    } else {
                                        HeimdAllFsService$$ExternalSyntheticOutline0.m("do not handle p2p intent, action = ", action, "WifiDisplayController");
                                        break;
                                    }
                                }
                            } else {
                                final WifiDisplayController wifiDisplayController6 = this.this$0;
                                if (wifiDisplayController6.mDiscoverPeersInProgress) {
                                    wifiDisplayController6.mWifiP2pManager.requestPeers(wifiDisplayController6.mWifiP2pChannel, new WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
                                        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
                                        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                                            SemWifiP2pDevice semWifiP2pDevice;
                                            int deviceType;
                                            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                                            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                                                WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
                                                if ((wfdInfo != null && wfdInfo.isEnabled() && wfdInfo.isSessionAvailable() && ((deviceType = wfdInfo.getDeviceType()) == 1 || deviceType == 3)) || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                                                    WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                                                }
                                            }
                                            WifiDisplayController wifiDisplayController62 = WifiDisplayController.this;
                                            if (wifiDisplayController62.mDiscoverPeersInProgress) {
                                                int size = wifiDisplayController62.mAvailableWifiDisplayPeers.size();
                                                WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
                                                for (int i2 = 0; i2 < size; i2++) {
                                                    WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) wifiDisplayController62.mAvailableWifiDisplayPeers.get(i2);
                                                    wifiDisplayArr[i2] = wifiDisplayController62.createWifiDisplay(wifiP2pDevice2);
                                                    if (!wifiP2pDevice2.getVendorElements().isEmpty() && (semWifiP2pDevice = wifiDisplayController62.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice2)) != null) {
                                                        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
                                                        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
                                                            BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("setScreenSharingHashedDi, deviceName : "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                                                            wifiDisplayArr[i2].setScreenSharingHashedDi(screenSharingHashedDi);
                                                        }
                                                    }
                                                    String str = wifiP2pDevice2.deviceAddress;
                                                    WifiP2pDevice wifiP2pDevice3 = wifiDisplayController62.mDesiredDevice;
                                                    if (wifiP2pDevice3 != null && wifiP2pDevice3.deviceAddress.equals(str)) {
                                                        wifiDisplayController62.mDesiredDevice.update(wifiP2pDevice2);
                                                        WifiDisplay wifiDisplay = wifiDisplayController62.mAdvertisedDisplay;
                                                        if (wifiDisplay != null && wifiDisplay.getDeviceAddress().equals(str)) {
                                                            wifiDisplayController62.advertiseDisplay(wifiDisplayController62.createWifiDisplay(wifiDisplayController62.mDesiredDevice), wifiDisplayController62.mAdvertisedDisplaySurface, wifiDisplayController62.mAdvertisedDisplayWidth, wifiDisplayController62.mAdvertisedDisplayHeight, wifiDisplayController62.mAdvertisedDisplayFlags);
                                                        }
                                                    }
                                                }
                                                wifiDisplayController62.mHandler.post(new AnonymousClass7(wifiDisplayController62, wifiDisplayArr, 0));
                                            }
                                        }
                                    });
                                }
                                String stringExtra = intent.getStringExtra("connectedDevAddress");
                                WifiP2pDeviceList wifiP2pDeviceList = (WifiP2pDeviceList) intent.getParcelableExtra("wifiP2pDeviceList");
                                Intent intent3 = new Intent("com.samsung.intent.action.WIFI_P2P_PEERS_CHANGED");
                                intent3.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
                                intent3.putExtra("wifiP2pDeviceList", wifiP2pDeviceList);
                                intent3.putExtra("connectedDevAddress", stringExtra);
                                this.this$0.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL);
                                break;
                            }
                        } else {
                            boolean z = intent.getIntExtra("wifi_p2p_state", 1) == 2;
                            WifiDisplayController wifiDisplayController7 = this.this$0;
                            wifiDisplayController7.mWifiP2pEnabled = z;
                            if (wifiDisplayController7.mWifiDisplayConfig.getConnectionType() == 1) {
                                if (wifiDisplayController7.mWifiDisplayOnSetting && !z) {
                                    Slog.w("WifiDisplayController", "handleStateChanged, wifi p2p state turn off, but wfd state on");
                                    wifiDisplayController7.mScanRequested = false;
                                    Settings.Global.putInt(wifiDisplayController7.mContext.getContentResolver(), "wifi_display_on", 0);
                                    break;
                                } else {
                                    wifiDisplayController7.updateWfdEnableState();
                                    break;
                                }
                            } else {
                                Slog.w("WifiDisplayController", "handleStateChanged, do not handle p2p intent, enabled = " + z);
                                break;
                            }
                        }
                        break;
                    case 1:
                        if ("android.samsung.media.action.AUDIO_MODE".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0);
                            WifiDisplayController wifiDisplayController8 = this.this$0;
                            if (wifiDisplayController8.mRemoteDisplayConnected) {
                                WifiDisplayController.m471$$Nest$msendVoipModeMessageIfNecessary(wifiDisplayController8, wifiDisplayController8.mAdvertisedDisplay, intExtra);
                                break;
                            }
                        }
                        break;
                    default:
                        String action2 = intent.getAction();
                        String str = "swipe";
                        if (!"com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action2)) {
                            if (!"android.intent.action.SCREEN_ON".equals(action2)) {
                                if ("android.intent.action.SCREEN_OFF".equals(action2)) {
                                    Slog.i("WifiDisplayController", "Received ACTION_SCREEN_OFF");
                                    this.this$0.setParam("usls", "screen_off");
                                    break;
                                }
                            } else if (!this.this$0.mScreenWakeUpByUser) {
                                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by controller");
                                this.this$0.mScreenWakeUpByUser = true;
                                break;
                            } else {
                                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by user");
                                if (((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded()) {
                                    this.this$0.setParam("usls", "swipe");
                                    break;
                                }
                            }
                        } else if (((PowerManager) this.this$0.mContext.getSystemService("power")).isInteractive()) {
                            boolean booleanExtra2 = intent.getBooleanExtra("bouncerShowing", false);
                            DeviceIdleController$$ExternalSyntheticOutline0.m("Received KEYGUARD_STATE_UPDATE = ", "WifiDisplayController", booleanExtra2);
                            if (!booleanExtra2) {
                                if (!((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded()) {
                                    this.this$0.setParam("usls", "none");
                                    break;
                                } else {
                                    this.this$0.setParam("usls", "swipe");
                                    break;
                                }
                            } else {
                                WifiDisplayController wifiDisplayController9 = this.this$0;
                                wifiDisplayController9.getClass();
                                int keyguardStoredPasswordQuality = new LockPatternUtils(wifiDisplayController9.mContext).getKeyguardStoredPasswordQuality(UserHandle.myUserId());
                                if (keyguardStoredPasswordQuality == 0) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Swipe");
                                } else if (keyguardStoredPasswordQuality == 32768) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Bio");
                                    str = "bio";
                                } else if (keyguardStoredPasswordQuality == 65536) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Pattern");
                                    str = "pattern";
                                } else if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Pin");
                                    str = "pin";
                                } else if (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Password");
                                    str = "password";
                                } else {
                                    str = "none";
                                }
                                wifiDisplayController9.setParam("usls", str);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        this.mWifiDisable = new AnonymousClass6(this, 6);
        this.mGameCallback = new IGameManagerCallback.Stub() { // from class: com.android.server.display.WifiDisplayController.32
            public final void onGameFocusIn(String str) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m("onGameFocusIn. ", str, "WifiDisplayController");
                WifiDisplayController.this.setParam("lowl", Boolean.TRUE);
            }

            public final void onGameFocusOut(String str) {
                Slog.d("WifiDisplayController", "onGameFocusOut. " + str);
                if (str.equals("KILL_YOURSELF")) {
                    return;
                }
                WifiDisplayController.this.setParam("lowl", Boolean.FALSE);
            }
        };
        final int i2 = 1;
        this.mAudioModeChangedReceiver = new BroadcastReceiver(this) { // from class: com.android.server.display.WifiDisplayController.20
            public final /* synthetic */ WifiDisplayController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i2) {
                    case 0:
                        String action = intent.getAction();
                        if (!"android.net.wifi.p2p.STATE_CHANGED".equals(action)) {
                            if (!"android.net.wifi.p2p.PEERS_CHANGED".equals(action)) {
                                if (!"android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(action)) {
                                    if (!"android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(action)) {
                                        if ("android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED".equals(action)) {
                                            boolean booleanExtra = intent.getBooleanExtra("android.net.wifi.p2p.extra.REQUEST_RESPONSE", false);
                                            DeviceIdleController$$ExternalSyntheticOutline0.m("Received WifiP2pManager.ACTION_WIFI_P2P_REQUEST_RESPONSE_CHANGED : requestAccepted = ", "WifiDisplayController", booleanExtra);
                                            WifiDisplayController wifiDisplayController = this.this$0;
                                            if (!wifiDisplayController.mWifiDisplayCertMode && wifiDisplayController.mConnectingDevice != null && wifiDisplayController.mWifiDisplayConfig.isPinRequest()) {
                                                if (!booleanExtra) {
                                                    Slog.d("WifiDisplayController", "User cancelled PIN connect or timeout");
                                                    this.this$0.disconnect();
                                                    break;
                                                } else {
                                                    Slog.d("WifiDisplayController", "User accepted PIN connect");
                                                    WifiDisplayController wifiDisplayController2 = this.this$0;
                                                    wifiDisplayController2.mHandler.postDelayed(wifiDisplayController2.mConnectionTimeout, 30000L);
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        this.this$0.mThisDevice = (WifiP2pDevice) intent.getParcelableExtra("wifiP2pDevice");
                                        WifiDisplayController wifiDisplayController3 = this.this$0;
                                        if (wifiDisplayController3.mWfdEnabled && wifiDisplayController3.mDiscoverPeersInProgress && wifiDisplayController3.mWifiP2pChannel != null && wifiDisplayController3.mWifiP2pManager != null && TextUtils.isEmpty(wifiDisplayController3.mLocalDeviceP2pMacAddress)) {
                                            WifiDisplayController wifiDisplayController4 = this.this$0;
                                            wifiDisplayController4.mWifiP2pManager.requestDeviceInfo(wifiDisplayController4.mWifiP2pChannel, new WifiP2pManager.DeviceInfoListener() { // from class: com.android.server.display.WifiDisplayController.20.1
                                                @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
                                                public final void onDeviceInfoAvailable(WifiP2pDevice wifiP2pDevice) {
                                                    if (wifiP2pDevice == null || TextUtils.isEmpty(wifiP2pDevice.deviceAddress)) {
                                                        return;
                                                    }
                                                    Log.d("WifiDisplayController", "onDeviceInfoAvailable");
                                                    AnonymousClass20.this.this$0.mLocalDeviceP2pMacAddress = wifiP2pDevice.deviceAddress;
                                                }
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                    if (this.this$0.mWifiDisplayConfig.getConnectionType() == 1) {
                                        if (networkInfo.getDetailedState() != NetworkInfo.DetailedState.CONNECTING) {
                                            final WifiDisplayController wifiDisplayController5 = this.this$0;
                                            wifiDisplayController5.mNetworkInfo = networkInfo;
                                            if (!wifiDisplayController5.mWfdEnabled || !networkInfo.isConnected()) {
                                                wifiDisplayController5.mConnectedDeviceGroupInfo = null;
                                                if (wifiDisplayController5.mConnectingDevice != null || wifiDisplayController5.mConnectedDevice != null) {
                                                    wifiDisplayController5.disconnect();
                                                }
                                                if (wifiDisplayController5.mWfdEnabled) {
                                                    wifiDisplayController5.mWifiP2pManager.requestPeers(wifiDisplayController5.mWifiP2pChannel, new WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
                                                        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
                                                        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                                                            SemWifiP2pDevice semWifiP2pDevice;
                                                            int deviceType;
                                                            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                                                            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                                                                WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
                                                                if ((wfdInfo != null && wfdInfo.isEnabled() && wfdInfo.isSessionAvailable() && ((deviceType = wfdInfo.getDeviceType()) == 1 || deviceType == 3)) || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                                                                    WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                                                                }
                                                            }
                                                            WifiDisplayController wifiDisplayController62 = WifiDisplayController.this;
                                                            if (wifiDisplayController62.mDiscoverPeersInProgress) {
                                                                int size = wifiDisplayController62.mAvailableWifiDisplayPeers.size();
                                                                WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
                                                                for (int i22 = 0; i22 < size; i22++) {
                                                                    WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) wifiDisplayController62.mAvailableWifiDisplayPeers.get(i22);
                                                                    wifiDisplayArr[i22] = wifiDisplayController62.createWifiDisplay(wifiP2pDevice2);
                                                                    if (!wifiP2pDevice2.getVendorElements().isEmpty() && (semWifiP2pDevice = wifiDisplayController62.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice2)) != null) {
                                                                        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
                                                                        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
                                                                            BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("setScreenSharingHashedDi, deviceName : "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                                                                            wifiDisplayArr[i22].setScreenSharingHashedDi(screenSharingHashedDi);
                                                                        }
                                                                    }
                                                                    String str = wifiP2pDevice2.deviceAddress;
                                                                    WifiP2pDevice wifiP2pDevice3 = wifiDisplayController62.mDesiredDevice;
                                                                    if (wifiP2pDevice3 != null && wifiP2pDevice3.deviceAddress.equals(str)) {
                                                                        wifiDisplayController62.mDesiredDevice.update(wifiP2pDevice2);
                                                                        WifiDisplay wifiDisplay = wifiDisplayController62.mAdvertisedDisplay;
                                                                        if (wifiDisplay != null && wifiDisplay.getDeviceAddress().equals(str)) {
                                                                            wifiDisplayController62.advertiseDisplay(wifiDisplayController62.createWifiDisplay(wifiDisplayController62.mDesiredDevice), wifiDisplayController62.mAdvertisedDisplaySurface, wifiDisplayController62.mAdvertisedDisplayWidth, wifiDisplayController62.mAdvertisedDisplayHeight, wifiDisplayController62.mAdvertisedDisplayFlags);
                                                                        }
                                                                    }
                                                                }
                                                                wifiDisplayController62.mHandler.post(new AnonymousClass7(wifiDisplayController62, wifiDisplayArr, 0));
                                                            }
                                                        }
                                                    });
                                                }
                                            } else if (wifiDisplayController5.mDesiredDevice != null || wifiDisplayController5.mWifiDisplayCertMode) {
                                                wifiDisplayController5.mWifiP2pManager.requestGroupInfo(wifiDisplayController5.mWifiP2pChannel, new WifiP2pManager.GroupInfoListener() { // from class: com.android.server.display.WifiDisplayController.15
                                                    @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
                                                    public final void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                                                        WifiP2pDevice wifiP2pDevice = WifiDisplayController.this.mConnectingDevice;
                                                        if (wifiP2pDevice != null && (wifiP2pGroup == null || (!wifiP2pGroup.getOwner().equals(wifiP2pDevice) && !wifiP2pGroup.getClientList().contains(wifiP2pDevice)))) {
                                                            StringBuilder sb = new StringBuilder("Aborting connection to Wifi display because the current P2P group does not contain the device we expected to find: ");
                                                            sb.append(WifiDisplayController.this.mConnectingDevice.deviceName);
                                                            sb.append(", group info was: ");
                                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, wifiP2pGroup != null ? wifiP2pGroup.toString().replace('\n', ',') : "null", "WifiDisplayController");
                                                            WifiDisplayController.this.handleConnectionFailure(6);
                                                            return;
                                                        }
                                                        WifiP2pDevice wifiP2pDevice2 = WifiDisplayController.this.mDesiredDevice;
                                                        if (wifiP2pDevice2 != null && (wifiP2pGroup == null || (!wifiP2pGroup.getOwner().equals(wifiP2pDevice2) && !wifiP2pGroup.getClientList().contains(wifiP2pDevice2)))) {
                                                            WifiDisplayController.this.disconnect();
                                                            return;
                                                        }
                                                        if (WifiDisplayController.this.mWifiDisplayCertMode) {
                                                            boolean equals = wifiP2pGroup.getOwner().deviceAddress.equals(WifiDisplayController.this.mThisDevice.deviceAddress);
                                                            if (equals && wifiP2pGroup.getClientList().isEmpty()) {
                                                                WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
                                                                wifiDisplayController6.mDesiredDevice = null;
                                                                wifiDisplayController6.mConnectingDevice = null;
                                                                wifiDisplayController6.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                                                wifiDisplayController6.updateConnection();
                                                            } else {
                                                                WifiDisplayController wifiDisplayController7 = WifiDisplayController.this;
                                                                if (wifiDisplayController7.mConnectingDevice == null && wifiDisplayController7.mDesiredDevice == null) {
                                                                    WifiP2pDevice next = equals ? wifiP2pGroup.getClientList().iterator().next() : wifiP2pGroup.getOwner();
                                                                    wifiDisplayController7.mDesiredDevice = next;
                                                                    wifiDisplayController7.mConnectingDevice = next;
                                                                    Slog.i("WifiDisplayController", "CertMode incoming from sink" + WifiDisplayController.describeWifiP2pDevice(WifiDisplayController.this.mConnectingDevice));
                                                                    WifiDisplayController wifiDisplayController8 = WifiDisplayController.this;
                                                                    wifiDisplayController8.advertiseDisplay(wifiDisplayController8.createWifiDisplay(wifiDisplayController8.mConnectingDevice), null, 0, 0, 0);
                                                                }
                                                            }
                                                        }
                                                        WifiDisplayController wifiDisplayController9 = WifiDisplayController.this;
                                                        WifiP2pDevice wifiP2pDevice3 = wifiDisplayController9.mConnectingDevice;
                                                        if (wifiP2pDevice3 == null || wifiP2pDevice3 != wifiDisplayController9.mDesiredDevice) {
                                                            return;
                                                        }
                                                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Connected to Wifi display: "), WifiDisplayController.this.mConnectingDevice.deviceName, "WifiDisplayController");
                                                        WifiDisplayController wifiDisplayController10 = WifiDisplayController.this;
                                                        wifiDisplayController10.mHandler.removeCallbacks(wifiDisplayController10.mConnectionTimeout);
                                                        WifiDisplayController wifiDisplayController11 = WifiDisplayController.this;
                                                        wifiDisplayController11.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                                        wifiDisplayController11.mConnectedDevice = wifiDisplayController11.mConnectingDevice;
                                                        wifiDisplayController11.mConnectingDevice = null;
                                                        wifiDisplayController11.mP2pFrequency = wifiP2pGroup.getFrequency();
                                                        WifiDisplayController.this.updateConnection();
                                                    }
                                                });
                                            }
                                            WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
                                            Intent intent2 = new Intent("com.samsung.intent.action.WIFI_P2P_CONNECTION_CHANGED");
                                            intent2.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
                                            intent2.putExtra("networkInfo", networkInfo);
                                            intent2.putExtra("wifiP2pInfo", wifiP2pInfo);
                                            this.this$0.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                                            break;
                                        } else {
                                            Slog.w("WifiDisplayController", "do not handle p2p CONNECTING state");
                                            break;
                                        }
                                    } else {
                                        HeimdAllFsService$$ExternalSyntheticOutline0.m("do not handle p2p intent, action = ", action, "WifiDisplayController");
                                        break;
                                    }
                                }
                            } else {
                                final WifiDisplayController wifiDisplayController6 = this.this$0;
                                if (wifiDisplayController6.mDiscoverPeersInProgress) {
                                    wifiDisplayController6.mWifiP2pManager.requestPeers(wifiDisplayController6.mWifiP2pChannel, new WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
                                        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
                                        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                                            SemWifiP2pDevice semWifiP2pDevice;
                                            int deviceType;
                                            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                                            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                                                WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
                                                if ((wfdInfo != null && wfdInfo.isEnabled() && wfdInfo.isSessionAvailable() && ((deviceType = wfdInfo.getDeviceType()) == 1 || deviceType == 3)) || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                                                    WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                                                }
                                            }
                                            WifiDisplayController wifiDisplayController62 = WifiDisplayController.this;
                                            if (wifiDisplayController62.mDiscoverPeersInProgress) {
                                                int size = wifiDisplayController62.mAvailableWifiDisplayPeers.size();
                                                WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
                                                for (int i22 = 0; i22 < size; i22++) {
                                                    WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) wifiDisplayController62.mAvailableWifiDisplayPeers.get(i22);
                                                    wifiDisplayArr[i22] = wifiDisplayController62.createWifiDisplay(wifiP2pDevice2);
                                                    if (!wifiP2pDevice2.getVendorElements().isEmpty() && (semWifiP2pDevice = wifiDisplayController62.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice2)) != null) {
                                                        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
                                                        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
                                                            BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("setScreenSharingHashedDi, deviceName : "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                                                            wifiDisplayArr[i22].setScreenSharingHashedDi(screenSharingHashedDi);
                                                        }
                                                    }
                                                    String str = wifiP2pDevice2.deviceAddress;
                                                    WifiP2pDevice wifiP2pDevice3 = wifiDisplayController62.mDesiredDevice;
                                                    if (wifiP2pDevice3 != null && wifiP2pDevice3.deviceAddress.equals(str)) {
                                                        wifiDisplayController62.mDesiredDevice.update(wifiP2pDevice2);
                                                        WifiDisplay wifiDisplay = wifiDisplayController62.mAdvertisedDisplay;
                                                        if (wifiDisplay != null && wifiDisplay.getDeviceAddress().equals(str)) {
                                                            wifiDisplayController62.advertiseDisplay(wifiDisplayController62.createWifiDisplay(wifiDisplayController62.mDesiredDevice), wifiDisplayController62.mAdvertisedDisplaySurface, wifiDisplayController62.mAdvertisedDisplayWidth, wifiDisplayController62.mAdvertisedDisplayHeight, wifiDisplayController62.mAdvertisedDisplayFlags);
                                                        }
                                                    }
                                                }
                                                wifiDisplayController62.mHandler.post(new AnonymousClass7(wifiDisplayController62, wifiDisplayArr, 0));
                                            }
                                        }
                                    });
                                }
                                String stringExtra = intent.getStringExtra("connectedDevAddress");
                                WifiP2pDeviceList wifiP2pDeviceList = (WifiP2pDeviceList) intent.getParcelableExtra("wifiP2pDeviceList");
                                Intent intent3 = new Intent("com.samsung.intent.action.WIFI_P2P_PEERS_CHANGED");
                                intent3.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
                                intent3.putExtra("wifiP2pDeviceList", wifiP2pDeviceList);
                                intent3.putExtra("connectedDevAddress", stringExtra);
                                this.this$0.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL);
                                break;
                            }
                        } else {
                            boolean z = intent.getIntExtra("wifi_p2p_state", 1) == 2;
                            WifiDisplayController wifiDisplayController7 = this.this$0;
                            wifiDisplayController7.mWifiP2pEnabled = z;
                            if (wifiDisplayController7.mWifiDisplayConfig.getConnectionType() == 1) {
                                if (wifiDisplayController7.mWifiDisplayOnSetting && !z) {
                                    Slog.w("WifiDisplayController", "handleStateChanged, wifi p2p state turn off, but wfd state on");
                                    wifiDisplayController7.mScanRequested = false;
                                    Settings.Global.putInt(wifiDisplayController7.mContext.getContentResolver(), "wifi_display_on", 0);
                                    break;
                                } else {
                                    wifiDisplayController7.updateWfdEnableState();
                                    break;
                                }
                            } else {
                                Slog.w("WifiDisplayController", "handleStateChanged, do not handle p2p intent, enabled = " + z);
                                break;
                            }
                        }
                        break;
                    case 1:
                        if ("android.samsung.media.action.AUDIO_MODE".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0);
                            WifiDisplayController wifiDisplayController8 = this.this$0;
                            if (wifiDisplayController8.mRemoteDisplayConnected) {
                                WifiDisplayController.m471$$Nest$msendVoipModeMessageIfNecessary(wifiDisplayController8, wifiDisplayController8.mAdvertisedDisplay, intExtra);
                                break;
                            }
                        }
                        break;
                    default:
                        String action2 = intent.getAction();
                        String str = "swipe";
                        if (!"com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action2)) {
                            if (!"android.intent.action.SCREEN_ON".equals(action2)) {
                                if ("android.intent.action.SCREEN_OFF".equals(action2)) {
                                    Slog.i("WifiDisplayController", "Received ACTION_SCREEN_OFF");
                                    this.this$0.setParam("usls", "screen_off");
                                    break;
                                }
                            } else if (!this.this$0.mScreenWakeUpByUser) {
                                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by controller");
                                this.this$0.mScreenWakeUpByUser = true;
                                break;
                            } else {
                                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by user");
                                if (((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded()) {
                                    this.this$0.setParam("usls", "swipe");
                                    break;
                                }
                            }
                        } else if (((PowerManager) this.this$0.mContext.getSystemService("power")).isInteractive()) {
                            boolean booleanExtra2 = intent.getBooleanExtra("bouncerShowing", false);
                            DeviceIdleController$$ExternalSyntheticOutline0.m("Received KEYGUARD_STATE_UPDATE = ", "WifiDisplayController", booleanExtra2);
                            if (!booleanExtra2) {
                                if (!((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded()) {
                                    this.this$0.setParam("usls", "none");
                                    break;
                                } else {
                                    this.this$0.setParam("usls", "swipe");
                                    break;
                                }
                            } else {
                                WifiDisplayController wifiDisplayController9 = this.this$0;
                                wifiDisplayController9.getClass();
                                int keyguardStoredPasswordQuality = new LockPatternUtils(wifiDisplayController9.mContext).getKeyguardStoredPasswordQuality(UserHandle.myUserId());
                                if (keyguardStoredPasswordQuality == 0) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Swipe");
                                } else if (keyguardStoredPasswordQuality == 32768) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Bio");
                                    str = "bio";
                                } else if (keyguardStoredPasswordQuality == 65536) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Pattern");
                                    str = "pattern";
                                } else if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Pin");
                                    str = "pin";
                                } else if (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Password");
                                    str = "password";
                                } else {
                                    str = "none";
                                }
                                wifiDisplayController9.setParam("usls", str);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i3 = 2;
        this.mInitiateReceiver = new BroadcastReceiver(this) { // from class: com.android.server.display.WifiDisplayController.20
            public final /* synthetic */ WifiDisplayController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i3) {
                    case 0:
                        String action = intent.getAction();
                        if (!"android.net.wifi.p2p.STATE_CHANGED".equals(action)) {
                            if (!"android.net.wifi.p2p.PEERS_CHANGED".equals(action)) {
                                if (!"android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(action)) {
                                    if (!"android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(action)) {
                                        if ("android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED".equals(action)) {
                                            boolean booleanExtra = intent.getBooleanExtra("android.net.wifi.p2p.extra.REQUEST_RESPONSE", false);
                                            DeviceIdleController$$ExternalSyntheticOutline0.m("Received WifiP2pManager.ACTION_WIFI_P2P_REQUEST_RESPONSE_CHANGED : requestAccepted = ", "WifiDisplayController", booleanExtra);
                                            WifiDisplayController wifiDisplayController = this.this$0;
                                            if (!wifiDisplayController.mWifiDisplayCertMode && wifiDisplayController.mConnectingDevice != null && wifiDisplayController.mWifiDisplayConfig.isPinRequest()) {
                                                if (!booleanExtra) {
                                                    Slog.d("WifiDisplayController", "User cancelled PIN connect or timeout");
                                                    this.this$0.disconnect();
                                                    break;
                                                } else {
                                                    Slog.d("WifiDisplayController", "User accepted PIN connect");
                                                    WifiDisplayController wifiDisplayController2 = this.this$0;
                                                    wifiDisplayController2.mHandler.postDelayed(wifiDisplayController2.mConnectionTimeout, 30000L);
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        this.this$0.mThisDevice = (WifiP2pDevice) intent.getParcelableExtra("wifiP2pDevice");
                                        WifiDisplayController wifiDisplayController3 = this.this$0;
                                        if (wifiDisplayController3.mWfdEnabled && wifiDisplayController3.mDiscoverPeersInProgress && wifiDisplayController3.mWifiP2pChannel != null && wifiDisplayController3.mWifiP2pManager != null && TextUtils.isEmpty(wifiDisplayController3.mLocalDeviceP2pMacAddress)) {
                                            WifiDisplayController wifiDisplayController4 = this.this$0;
                                            wifiDisplayController4.mWifiP2pManager.requestDeviceInfo(wifiDisplayController4.mWifiP2pChannel, new WifiP2pManager.DeviceInfoListener() { // from class: com.android.server.display.WifiDisplayController.20.1
                                                @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
                                                public final void onDeviceInfoAvailable(WifiP2pDevice wifiP2pDevice) {
                                                    if (wifiP2pDevice == null || TextUtils.isEmpty(wifiP2pDevice.deviceAddress)) {
                                                        return;
                                                    }
                                                    Log.d("WifiDisplayController", "onDeviceInfoAvailable");
                                                    AnonymousClass20.this.this$0.mLocalDeviceP2pMacAddress = wifiP2pDevice.deviceAddress;
                                                }
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                    if (this.this$0.mWifiDisplayConfig.getConnectionType() == 1) {
                                        if (networkInfo.getDetailedState() != NetworkInfo.DetailedState.CONNECTING) {
                                            final WifiDisplayController wifiDisplayController5 = this.this$0;
                                            wifiDisplayController5.mNetworkInfo = networkInfo;
                                            if (!wifiDisplayController5.mWfdEnabled || !networkInfo.isConnected()) {
                                                wifiDisplayController5.mConnectedDeviceGroupInfo = null;
                                                if (wifiDisplayController5.mConnectingDevice != null || wifiDisplayController5.mConnectedDevice != null) {
                                                    wifiDisplayController5.disconnect();
                                                }
                                                if (wifiDisplayController5.mWfdEnabled) {
                                                    wifiDisplayController5.mWifiP2pManager.requestPeers(wifiDisplayController5.mWifiP2pChannel, new WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
                                                        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
                                                        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                                                            SemWifiP2pDevice semWifiP2pDevice;
                                                            int deviceType;
                                                            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                                                            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                                                                WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
                                                                if ((wfdInfo != null && wfdInfo.isEnabled() && wfdInfo.isSessionAvailable() && ((deviceType = wfdInfo.getDeviceType()) == 1 || deviceType == 3)) || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                                                                    WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                                                                }
                                                            }
                                                            WifiDisplayController wifiDisplayController62 = WifiDisplayController.this;
                                                            if (wifiDisplayController62.mDiscoverPeersInProgress) {
                                                                int size = wifiDisplayController62.mAvailableWifiDisplayPeers.size();
                                                                WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
                                                                for (int i22 = 0; i22 < size; i22++) {
                                                                    WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) wifiDisplayController62.mAvailableWifiDisplayPeers.get(i22);
                                                                    wifiDisplayArr[i22] = wifiDisplayController62.createWifiDisplay(wifiP2pDevice2);
                                                                    if (!wifiP2pDevice2.getVendorElements().isEmpty() && (semWifiP2pDevice = wifiDisplayController62.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice2)) != null) {
                                                                        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
                                                                        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
                                                                            BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("setScreenSharingHashedDi, deviceName : "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                                                                            wifiDisplayArr[i22].setScreenSharingHashedDi(screenSharingHashedDi);
                                                                        }
                                                                    }
                                                                    String str = wifiP2pDevice2.deviceAddress;
                                                                    WifiP2pDevice wifiP2pDevice3 = wifiDisplayController62.mDesiredDevice;
                                                                    if (wifiP2pDevice3 != null && wifiP2pDevice3.deviceAddress.equals(str)) {
                                                                        wifiDisplayController62.mDesiredDevice.update(wifiP2pDevice2);
                                                                        WifiDisplay wifiDisplay = wifiDisplayController62.mAdvertisedDisplay;
                                                                        if (wifiDisplay != null && wifiDisplay.getDeviceAddress().equals(str)) {
                                                                            wifiDisplayController62.advertiseDisplay(wifiDisplayController62.createWifiDisplay(wifiDisplayController62.mDesiredDevice), wifiDisplayController62.mAdvertisedDisplaySurface, wifiDisplayController62.mAdvertisedDisplayWidth, wifiDisplayController62.mAdvertisedDisplayHeight, wifiDisplayController62.mAdvertisedDisplayFlags);
                                                                        }
                                                                    }
                                                                }
                                                                wifiDisplayController62.mHandler.post(new AnonymousClass7(wifiDisplayController62, wifiDisplayArr, 0));
                                                            }
                                                        }
                                                    });
                                                }
                                            } else if (wifiDisplayController5.mDesiredDevice != null || wifiDisplayController5.mWifiDisplayCertMode) {
                                                wifiDisplayController5.mWifiP2pManager.requestGroupInfo(wifiDisplayController5.mWifiP2pChannel, new WifiP2pManager.GroupInfoListener() { // from class: com.android.server.display.WifiDisplayController.15
                                                    @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
                                                    public final void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                                                        WifiP2pDevice wifiP2pDevice = WifiDisplayController.this.mConnectingDevice;
                                                        if (wifiP2pDevice != null && (wifiP2pGroup == null || (!wifiP2pGroup.getOwner().equals(wifiP2pDevice) && !wifiP2pGroup.getClientList().contains(wifiP2pDevice)))) {
                                                            StringBuilder sb = new StringBuilder("Aborting connection to Wifi display because the current P2P group does not contain the device we expected to find: ");
                                                            sb.append(WifiDisplayController.this.mConnectingDevice.deviceName);
                                                            sb.append(", group info was: ");
                                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, wifiP2pGroup != null ? wifiP2pGroup.toString().replace('\n', ',') : "null", "WifiDisplayController");
                                                            WifiDisplayController.this.handleConnectionFailure(6);
                                                            return;
                                                        }
                                                        WifiP2pDevice wifiP2pDevice2 = WifiDisplayController.this.mDesiredDevice;
                                                        if (wifiP2pDevice2 != null && (wifiP2pGroup == null || (!wifiP2pGroup.getOwner().equals(wifiP2pDevice2) && !wifiP2pGroup.getClientList().contains(wifiP2pDevice2)))) {
                                                            WifiDisplayController.this.disconnect();
                                                            return;
                                                        }
                                                        if (WifiDisplayController.this.mWifiDisplayCertMode) {
                                                            boolean equals = wifiP2pGroup.getOwner().deviceAddress.equals(WifiDisplayController.this.mThisDevice.deviceAddress);
                                                            if (equals && wifiP2pGroup.getClientList().isEmpty()) {
                                                                WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
                                                                wifiDisplayController6.mDesiredDevice = null;
                                                                wifiDisplayController6.mConnectingDevice = null;
                                                                wifiDisplayController6.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                                                wifiDisplayController6.updateConnection();
                                                            } else {
                                                                WifiDisplayController wifiDisplayController7 = WifiDisplayController.this;
                                                                if (wifiDisplayController7.mConnectingDevice == null && wifiDisplayController7.mDesiredDevice == null) {
                                                                    WifiP2pDevice next = equals ? wifiP2pGroup.getClientList().iterator().next() : wifiP2pGroup.getOwner();
                                                                    wifiDisplayController7.mDesiredDevice = next;
                                                                    wifiDisplayController7.mConnectingDevice = next;
                                                                    Slog.i("WifiDisplayController", "CertMode incoming from sink" + WifiDisplayController.describeWifiP2pDevice(WifiDisplayController.this.mConnectingDevice));
                                                                    WifiDisplayController wifiDisplayController8 = WifiDisplayController.this;
                                                                    wifiDisplayController8.advertiseDisplay(wifiDisplayController8.createWifiDisplay(wifiDisplayController8.mConnectingDevice), null, 0, 0, 0);
                                                                }
                                                            }
                                                        }
                                                        WifiDisplayController wifiDisplayController9 = WifiDisplayController.this;
                                                        WifiP2pDevice wifiP2pDevice3 = wifiDisplayController9.mConnectingDevice;
                                                        if (wifiP2pDevice3 == null || wifiP2pDevice3 != wifiDisplayController9.mDesiredDevice) {
                                                            return;
                                                        }
                                                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Connected to Wifi display: "), WifiDisplayController.this.mConnectingDevice.deviceName, "WifiDisplayController");
                                                        WifiDisplayController wifiDisplayController10 = WifiDisplayController.this;
                                                        wifiDisplayController10.mHandler.removeCallbacks(wifiDisplayController10.mConnectionTimeout);
                                                        WifiDisplayController wifiDisplayController11 = WifiDisplayController.this;
                                                        wifiDisplayController11.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                                        wifiDisplayController11.mConnectedDevice = wifiDisplayController11.mConnectingDevice;
                                                        wifiDisplayController11.mConnectingDevice = null;
                                                        wifiDisplayController11.mP2pFrequency = wifiP2pGroup.getFrequency();
                                                        WifiDisplayController.this.updateConnection();
                                                    }
                                                });
                                            }
                                            WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
                                            Intent intent2 = new Intent("com.samsung.intent.action.WIFI_P2P_CONNECTION_CHANGED");
                                            intent2.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
                                            intent2.putExtra("networkInfo", networkInfo);
                                            intent2.putExtra("wifiP2pInfo", wifiP2pInfo);
                                            this.this$0.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                                            break;
                                        } else {
                                            Slog.w("WifiDisplayController", "do not handle p2p CONNECTING state");
                                            break;
                                        }
                                    } else {
                                        HeimdAllFsService$$ExternalSyntheticOutline0.m("do not handle p2p intent, action = ", action, "WifiDisplayController");
                                        break;
                                    }
                                }
                            } else {
                                final WifiDisplayController wifiDisplayController6 = this.this$0;
                                if (wifiDisplayController6.mDiscoverPeersInProgress) {
                                    wifiDisplayController6.mWifiP2pManager.requestPeers(wifiDisplayController6.mWifiP2pChannel, new WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
                                        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
                                        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                                            SemWifiP2pDevice semWifiP2pDevice;
                                            int deviceType;
                                            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                                            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                                                WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
                                                if ((wfdInfo != null && wfdInfo.isEnabled() && wfdInfo.isSessionAvailable() && ((deviceType = wfdInfo.getDeviceType()) == 1 || deviceType == 3)) || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                                                    WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                                                }
                                            }
                                            WifiDisplayController wifiDisplayController62 = WifiDisplayController.this;
                                            if (wifiDisplayController62.mDiscoverPeersInProgress) {
                                                int size = wifiDisplayController62.mAvailableWifiDisplayPeers.size();
                                                WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
                                                for (int i22 = 0; i22 < size; i22++) {
                                                    WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) wifiDisplayController62.mAvailableWifiDisplayPeers.get(i22);
                                                    wifiDisplayArr[i22] = wifiDisplayController62.createWifiDisplay(wifiP2pDevice2);
                                                    if (!wifiP2pDevice2.getVendorElements().isEmpty() && (semWifiP2pDevice = wifiDisplayController62.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice2)) != null) {
                                                        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
                                                        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
                                                            BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("setScreenSharingHashedDi, deviceName : "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                                                            wifiDisplayArr[i22].setScreenSharingHashedDi(screenSharingHashedDi);
                                                        }
                                                    }
                                                    String str = wifiP2pDevice2.deviceAddress;
                                                    WifiP2pDevice wifiP2pDevice3 = wifiDisplayController62.mDesiredDevice;
                                                    if (wifiP2pDevice3 != null && wifiP2pDevice3.deviceAddress.equals(str)) {
                                                        wifiDisplayController62.mDesiredDevice.update(wifiP2pDevice2);
                                                        WifiDisplay wifiDisplay = wifiDisplayController62.mAdvertisedDisplay;
                                                        if (wifiDisplay != null && wifiDisplay.getDeviceAddress().equals(str)) {
                                                            wifiDisplayController62.advertiseDisplay(wifiDisplayController62.createWifiDisplay(wifiDisplayController62.mDesiredDevice), wifiDisplayController62.mAdvertisedDisplaySurface, wifiDisplayController62.mAdvertisedDisplayWidth, wifiDisplayController62.mAdvertisedDisplayHeight, wifiDisplayController62.mAdvertisedDisplayFlags);
                                                        }
                                                    }
                                                }
                                                wifiDisplayController62.mHandler.post(new AnonymousClass7(wifiDisplayController62, wifiDisplayArr, 0));
                                            }
                                        }
                                    });
                                }
                                String stringExtra = intent.getStringExtra("connectedDevAddress");
                                WifiP2pDeviceList wifiP2pDeviceList = (WifiP2pDeviceList) intent.getParcelableExtra("wifiP2pDeviceList");
                                Intent intent3 = new Intent("com.samsung.intent.action.WIFI_P2P_PEERS_CHANGED");
                                intent3.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
                                intent3.putExtra("wifiP2pDeviceList", wifiP2pDeviceList);
                                intent3.putExtra("connectedDevAddress", stringExtra);
                                this.this$0.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL);
                                break;
                            }
                        } else {
                            boolean z = intent.getIntExtra("wifi_p2p_state", 1) == 2;
                            WifiDisplayController wifiDisplayController7 = this.this$0;
                            wifiDisplayController7.mWifiP2pEnabled = z;
                            if (wifiDisplayController7.mWifiDisplayConfig.getConnectionType() == 1) {
                                if (wifiDisplayController7.mWifiDisplayOnSetting && !z) {
                                    Slog.w("WifiDisplayController", "handleStateChanged, wifi p2p state turn off, but wfd state on");
                                    wifiDisplayController7.mScanRequested = false;
                                    Settings.Global.putInt(wifiDisplayController7.mContext.getContentResolver(), "wifi_display_on", 0);
                                    break;
                                } else {
                                    wifiDisplayController7.updateWfdEnableState();
                                    break;
                                }
                            } else {
                                Slog.w("WifiDisplayController", "handleStateChanged, do not handle p2p intent, enabled = " + z);
                                break;
                            }
                        }
                        break;
                    case 1:
                        if ("android.samsung.media.action.AUDIO_MODE".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0);
                            WifiDisplayController wifiDisplayController8 = this.this$0;
                            if (wifiDisplayController8.mRemoteDisplayConnected) {
                                WifiDisplayController.m471$$Nest$msendVoipModeMessageIfNecessary(wifiDisplayController8, wifiDisplayController8.mAdvertisedDisplay, intExtra);
                                break;
                            }
                        }
                        break;
                    default:
                        String action2 = intent.getAction();
                        String str = "swipe";
                        if (!"com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action2)) {
                            if (!"android.intent.action.SCREEN_ON".equals(action2)) {
                                if ("android.intent.action.SCREEN_OFF".equals(action2)) {
                                    Slog.i("WifiDisplayController", "Received ACTION_SCREEN_OFF");
                                    this.this$0.setParam("usls", "screen_off");
                                    break;
                                }
                            } else if (!this.this$0.mScreenWakeUpByUser) {
                                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by controller");
                                this.this$0.mScreenWakeUpByUser = true;
                                break;
                            } else {
                                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by user");
                                if (((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded()) {
                                    this.this$0.setParam("usls", "swipe");
                                    break;
                                }
                            }
                        } else if (((PowerManager) this.this$0.mContext.getSystemService("power")).isInteractive()) {
                            boolean booleanExtra2 = intent.getBooleanExtra("bouncerShowing", false);
                            DeviceIdleController$$ExternalSyntheticOutline0.m("Received KEYGUARD_STATE_UPDATE = ", "WifiDisplayController", booleanExtra2);
                            if (!booleanExtra2) {
                                if (!((KeyguardManager) this.this$0.mContext.getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded()) {
                                    this.this$0.setParam("usls", "none");
                                    break;
                                } else {
                                    this.this$0.setParam("usls", "swipe");
                                    break;
                                }
                            } else {
                                WifiDisplayController wifiDisplayController9 = this.this$0;
                                wifiDisplayController9.getClass();
                                int keyguardStoredPasswordQuality = new LockPatternUtils(wifiDisplayController9.mContext).getKeyguardStoredPasswordQuality(UserHandle.myUserId());
                                if (keyguardStoredPasswordQuality == 0) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Swipe");
                                } else if (keyguardStoredPasswordQuality == 32768) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Bio");
                                    str = "bio";
                                } else if (keyguardStoredPasswordQuality == 65536) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Pattern");
                                    str = "pattern";
                                } else if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Pin");
                                    str = "pin";
                                } else if (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) {
                                    Slog.d("WifiDisplayController", "LockScreenType : Password");
                                    str = "password";
                                } else {
                                    str = "none";
                                }
                                wifiDisplayController9.setParam("usls", str);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        this.mNativeListener = new RemoteDisplay.NativeListener() { // from class: com.android.server.display.WifiDisplayController.35
            public final void onNotify(int i4, String str) {
                WifiDisplay wifiDisplay;
                if (i4 == 1) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                        jSONObject.getBoolean("renameAvailable");
                        wifiDisplayController.getClass();
                        return;
                    } catch (JSONException e) {
                        Slog.w("WifiDisplayController", e.toString());
                        return;
                    }
                }
                if (i4 == 2) {
                    Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_WFD_ENGINE_RESUME");
                    WifiDisplayController.this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.smartview.WFD_ENGINE_RESUME"), UserHandle.ALL);
                    return;
                }
                if (i4 == 3) {
                    Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_WFD_ENGINE_PAUSE");
                    WifiDisplayController.this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.smartview.WFD_ENGINE_PAUSE"), UserHandle.ALL);
                    return;
                }
                if (i4 == 7) {
                    int parseInt = Integer.parseInt(str);
                    int i5 = parseInt % 2;
                    WifiDisplayController.this.mTransportMode = i5;
                    Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_TRANSPORT_MODE,  mTransportMode = ".concat(i5 == 1 ? "TCP" : "UDP"));
                    if (parseInt <= 1) {
                        WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
                        Slog.d("WifiDisplayController", "sendBroadcastTransportMode mode : ".concat(wifiDisplayController2.mTransportMode == 1 ? "TCP" : "UDP"));
                        Intent intent = new Intent("com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE");
                        intent.addFlags(67108864);
                        intent.putExtra("CONNECTION_MODE", wifiDisplayController2.mTransportMode);
                        wifiDisplayController2.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                        return;
                    }
                    return;
                }
                if (i4 == 40) {
                    try {
                        WifiDisplayController.this.mIsSupportInitiateMirroring = new JSONObject(str).getBoolean("isSupportInitiatedMirroring");
                        Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_SUPPORT_INITIATE_MIRRORING = " + WifiDisplayController.this.mIsSupportInitiateMirroring);
                        return;
                    } catch (JSONException e2) {
                        Slog.w("WifiDisplayController", e2.toString());
                        return;
                    }
                }
                if (i4 == 50) {
                    Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_SCREEN_WAKE_UP");
                    WifiDisplayController wifiDisplayController3 = WifiDisplayController.this;
                    wifiDisplayController3.mHandler.post(new AnonymousClass6(wifiDisplayController3, 5));
                    return;
                }
                if (i4 == 60) {
                    try {
                        WifiDisplayController.this.mIsDisplayVolumeControlSupported = new JSONObject(str).getBoolean("isSupportDisplayVolumeControl");
                        Log.i("WifiDisplayController", "    onNotify received: [VolumeControl] NOTIFY_DISPLAY_VOLUME_SUPPORT = " + WifiDisplayController.this.mIsDisplayVolumeControlSupported);
                        WifiDisplayController.this.sendWifiDisplayVolumeSupportChangedBroadcast();
                        WifiDisplayController wifiDisplayController4 = WifiDisplayController.this;
                        if (wifiDisplayController4.mIsDisplayVolumeControlSupported && (wifiDisplay = wifiDisplayController4.mAdvertisedDisplay) != null && wifiDisplay.getState() == 1) {
                            WifiDisplayController wifiDisplayController5 = WifiDisplayController.this;
                            wifiDisplayController5.mPrevMusicStreamVolume = wifiDisplayController5.mAudioManager.semGetStreamVolume(3, 25);
                            WifiDisplayController.this.mAudioManager.semSetStreamVolume(3, 15, 0, 25);
                            return;
                        }
                        return;
                    } catch (JSONException e3) {
                        Log.w("WifiDisplayController", e3.toString());
                        return;
                    }
                }
                if (i4 == 70) {
                    final Bundle bundle = new Bundle();
                    try {
                        JSONObject jSONObject2 = new JSONObject(str);
                        bundle.putInt("minVol", jSONObject2.getInt("minVol"));
                        bundle.putInt("maxVol", jSONObject2.getInt("maxVol"));
                        bundle.putInt("curVol", jSONObject2.getInt("curVol"));
                        bundle.putBoolean("isMute", jSONObject2.getBoolean("isMute"));
                    } catch (JSONException e4) {
                        Log.w("WifiDisplayController", e4.toString());
                    }
                    final WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
                    wifiDisplayController6.mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayController.26
                        public final /* synthetic */ int val$event = 11;

                        @Override // java.lang.Runnable
                        public final void run() {
                            Message obtainMessage = WifiDisplayController.this.mHandler.obtainMessage(22, this.val$event, 0);
                            obtainMessage.setData(bundle);
                            WifiDisplayController.this.mHandler.sendMessage(obtainMessage);
                        }
                    });
                    Log.i("WifiDisplayController", "    onNotify received :  NOTIFY_DISPLAY_VOLUME_STATUS");
                    return;
                }
                if (i4 == 90) {
                    Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_SWTICH_TCP_FOR_DEMO");
                    WifiDisplayController wifiDisplayController7 = WifiDisplayController.this;
                    if (wifiDisplayController7.mTransportMode != 1) {
                        wifiDisplayController7.mTransportMode = 1;
                        wifiDisplayController7.setParam("tcp", Boolean.TRUE);
                        return;
                    }
                    return;
                }
                if (i4 == 100) {
                    Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_NOT_DEFINED : " + str);
                    WifiDisplayController wifiDisplayController8 = WifiDisplayController.this;
                    if (wifiDisplayController8.mRemoteDisplayConnected) {
                        WifiDisplayController.m472$$Nest$msendWifiDisplayParameterEvent(wifiDisplayController8, WifiDisplayController.m470$$Nest$mparseParametersFromEngine(wifiDisplayController8, wifiDisplayController8.mAdvertisedDisplay, str));
                        return;
                    }
                    return;
                }
                if (i4 == 9) {
                    try {
                        JSONObject jSONObject3 = new JSONObject(str);
                        WifiDisplayController.this.mIsUibcAvailable = jSONObject3.getBoolean("UibcAvailable");
                        boolean z = jSONObject3.getBoolean("UibcSamsungMobile");
                        Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_SUPPORT_UIBC = " + WifiDisplayController.this.mIsUibcAvailable + ", isSamsungMobile = " + z);
                        WifiDisplayController wifiDisplayController9 = WifiDisplayController.this;
                        if (wifiDisplayController9.mIsUibcAvailable) {
                            wifiDisplayController9.mWfdUibcManager.start(false);
                            WifiDisplayController.this.mWfdUibcManager.setSinkDevice(z);
                        } else {
                            wifiDisplayController9.mWfdUibcManager.stop(false);
                        }
                        return;
                    } catch (JSONException e5) {
                        Slog.w("WifiDisplayController", e5.toString());
                        return;
                    }
                }
                int i6 = 10;
                if (i4 != 10) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(i4, "onNotify Error,  msg : ", "WifiDisplayController");
                    return;
                }
                int parseInt2 = Integer.parseInt(str);
                HermesService$3$$ExternalSyntheticOutline0.m(parseInt2, "    onNotify received : NOTIFY_ROTATION_CHANGED = ", "WifiDisplayController");
                WifiDisplayController wifiDisplayController10 = WifiDisplayController.this;
                wifiDisplayController10.mHandler.post(new AnonymousClass21(wifiDisplayController10, parseInt2 == 0 ? 8 : 9, i6, 2));
                Intent intent2 = new Intent("com.samsung.intent.action.ROTATION_CHANGED");
                intent2.putExtra("rotation", parseInt2);
                intent2.putExtra("updateDisplayDevice", true);
                WifiDisplayController.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                if (parseInt2 == 1 || parseInt2 == 3) {
                    WifiDisplayController.this.mWfdUibcManager.setPortraitMode(true);
                } else {
                    WifiDisplayController.this.mWfdUibcManager.setPortraitMode(false);
                }
            }
        };
        this.remoteDisplayListener = new RemoteDisplay.Listener() { // from class: com.android.server.display.WifiDisplayController.36
            public final void onDisplayChanged(Surface surface, int i4, int i5, int i6) {
                WifiDisplay wifiDisplay;
                ASKSManagerService$$ExternalSyntheticOutline0.m(i4, i5, "onDisplayChanged, width : ", ", height : ", "WifiDisplayController");
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                if (wifiDisplayController.mConnectedDevice == null || wifiDisplayController.mRemoteDisplay == null || (wifiDisplay = wifiDisplayController.mAdvertisedDisplay) == null) {
                    return;
                }
                wifiDisplayController.advertiseDisplay(wifiDisplay, surface, i4, i5, i6);
            }

            public final void onDisplayConnected(Surface surface, int i4, int i5, int i6, int i7, String str) {
                IBinder gMSBinder;
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                if (wifiDisplayController.mConnectedDevice == null || wifiDisplayController.mRemoteDisplay == null) {
                    return;
                }
                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i4, i5, "onDisplayConnected, width : ", " , height : ", " , flags : ");
                WifiDisplayController.this.getClass();
                ArrayList arrayList = new ArrayList();
                if ((i6 & 1) != 0) {
                    arrayList.add("SECURE");
                }
                if ((i6 & 2) != 0) {
                    arrayList.add("LANDSCAPE");
                }
                int i8 = i6 & 4;
                if (i8 != 0) {
                    arrayList.add("PORTRAIT_90");
                }
                int i9 = i6 & 8;
                if (i9 != 0) {
                    arrayList.add("PORTRAIT_270");
                }
                if ((i6 & 16) != 0) {
                    arrayList.add("AUDIO_ONLY");
                }
                int i10 = i6 & 32;
                if (i10 != 0) {
                    arrayList.add("HIGH_RESOLUTION_SUPPORT");
                }
                int i11 = i6 & 64;
                if (i11 != 0) {
                    arrayList.add("DMR_SUPPORT");
                }
                m.append(arrayList.toString());
                m.append("\nOpened RTSP connection with Wifi display: ");
                DeviceIdleController$$ExternalSyntheticOutline0.m(m, WifiDisplayController.this.mConnectedDevice.deviceName, "WifiDisplayController");
                WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
                WifiDisplay createWifiDisplay = wifiDisplayController2.createWifiDisplay(wifiDisplayController2.mConnectedDevice);
                createWifiDisplay.setState(6);
                createWifiDisplay.setFlags(wifiDisplayController2.mWifiDisplayConfig.getFlags());
                createWifiDisplay.setMode(wifiDisplayController2.mWifiDisplayConfig.getMode());
                if (wifiDisplayController2.mWifiDisplayConfig.getMode() == 0) {
                    if (i11 != 0) {
                        createWifiDisplay.addParameter("wfd_sec_dmr_support", "enable");
                    }
                    if (i10 != 0) {
                        createWifiDisplay.addParameter("high_resolution_mode", "support");
                    }
                }
                if (wifiDisplayController2.mWifiDisplayConfig.getConnectionType() == 2 && createWifiDisplay.getSamsungDeviceType() == 0) {
                    createWifiDisplay.setSamsungDeviceType(6);
                }
                WifiDisplayController.this.advertiseDisplay(createWifiDisplay, surface, i4, i5, i6);
                WifiDisplayController wifiDisplayController3 = WifiDisplayController.this;
                boolean z = true;
                boolean z2 = true;
                wifiDisplayController3.mIsPortraitDisplay = (i8 == 0 && i9 == 0) ? false : true;
                wifiDisplayController3.mWfdUibcManager.setUIBCNegotiagedResolution(i4, i5);
                WifiDisplayController wifiDisplayController4 = WifiDisplayController.this;
                wifiDisplayController4.mRemoteDisplayConnected = true;
                wifiDisplayController4.mHandler.removeCallbacks(wifiDisplayController4.mRtspTimeout);
                WifiDisplayController wifiDisplayController5 = WifiDisplayController.this;
                wifiDisplayController5.mHandler.post(new AnonymousClass29(wifiDisplayController5, z, 0));
                WifiDisplayController.this.sendEventToSemDeviceStatusListener(2);
                WifiDisplayController.this.mParameterList = new ArrayList();
                WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
                WifiDisplayController.m470$$Nest$mparseParametersFromEngine(wifiDisplayController6, wifiDisplayController6.mAdvertisedDisplay, str);
                for (Map.Entry entry : WifiDisplayController.this.mAdvertisedDisplay.getParameters().entrySet()) {
                    WifiDisplayController.this.mParameterList.add(new SemWifiDisplayParameter((String) entry.getKey(), (String) entry.getValue()));
                }
                WifiDisplayController wifiDisplayController7 = WifiDisplayController.this;
                List list = wifiDisplayController7.mParameterList;
                try {
                    IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback = wifiDisplayController7.mCallback;
                    if (iWifiDisplayConnectionCallback != null) {
                        iWifiDisplayConnectionCallback.onSuccess(list);
                        wifiDisplayController7.mCallback = null;
                    }
                } catch (RemoteException unused) {
                    Slog.w("WifiDisplayController", "Failed to onSuccess");
                }
                WifiDisplayController wifiDisplayController8 = WifiDisplayController.this;
                WifiDisplayController.m472$$Nest$msendWifiDisplayParameterEvent(wifiDisplayController8, wifiDisplayController8.mParameterList);
                WifiDisplayController.this.sendWifiDisplayVolumeSupportChangedBroadcast();
                WifiDisplayController wifiDisplayController9 = WifiDisplayController.this;
                if (wifiDisplayController9.mWifiDisplayCertMode) {
                    WifiDisplayAdapter.AnonymousClass14 anonymousClass142 = wifiDisplayController9.mListener;
                    WifiDisplaySessionInfo sessionInfo = wifiDisplayController9.getSessionInfo(wifiDisplayController9.mConnectedDeviceGroupInfo, i7);
                    synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                        WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                        wifiDisplayAdapter.mSessionInfo = sessionInfo;
                        wifiDisplayAdapter.scheduleStatusChangedBroadcastLocked();
                    }
                }
                if (WifiDisplayController.this.mWifiDisplayConfig.isInitiateMirroringMode()) {
                    WifiDisplayController wifiDisplayController10 = WifiDisplayController.this;
                    wifiDisplayController10.mHandler.post(new AnonymousClass6(wifiDisplayController10, 5));
                }
                if (WifiDisplayController.this.mWifiDisplayConfig.getConnectionType() == 2 || createWifiDisplay.getSamsungDeviceType() == 6) {
                    WifiDisplayController wifiDisplayController11 = WifiDisplayController.this;
                    wifiDisplayController11.getClass();
                    try {
                        if (wifiDisplayController11.mGameManagerService == null && (gMSBinder = SemGameManager.getGMSBinder()) != null) {
                            wifiDisplayController11.mGameManagerService = IGameManagerService.Stub.asInterface(gMSBinder);
                        }
                        IGameManagerService iGameManagerService = wifiDisplayController11.mGameManagerService;
                        if (iGameManagerService != null) {
                            if (iGameManagerService.identifyForegroundApp() == 1) {
                                Slog.i("WifiDisplayController", "Set low latency mode");
                                wifiDisplayController11.setParam("lowl", Boolean.TRUE);
                            }
                            Slog.d("WifiDisplayController", "registerCallback. ret: " + wifiDisplayController11.mGameManagerService.registerCallback(wifiDisplayController11.mGameCallback));
                        } else {
                            Slog.d("WifiDisplayController", "failed to get game manager");
                        }
                    } catch (Exception e) {
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "registerCallback failed.", "WifiDisplayController");
                    }
                    IntentFilter m2 = BatteryService$$ExternalSyntheticOutline0.m("android.samsung.media.action.AUDIO_MODE");
                    WifiDisplayController wifiDisplayController12 = WifiDisplayController.this;
                    wifiDisplayController12.mContext.registerReceiver(wifiDisplayController12.mAudioModeChangedReceiver, m2, null, wifiDisplayController12.mHandler, 2);
                    WifiDisplayController wifiDisplayController13 = WifiDisplayController.this;
                    WifiDisplayController.m471$$Nest$msendVoipModeMessageIfNecessary(wifiDisplayController13, wifiDisplayController13.mAdvertisedDisplay, wifiDisplayController13.mAudioManager.getMode());
                }
                WifiDisplayController wifiDisplayController14 = WifiDisplayController.this;
                wifiDisplayController14.mHandler.post(new AnonymousClass21(wifiDisplayController14, z2 ? 1 : 0, wifiDisplayController14.mAdvertisedDisplay.getMode(), 0));
                Slog.i("WifiDisplayController", "onDisplayConnected wifidisplay " + WifiDisplayController.this.mAdvertisedDisplay.toString());
            }

            public final void onDisplayDisconnected() {
                Slog.d("WifiDisplayController", "onDisplayDisconnected");
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                wifiDisplayController.mHandler.post(new AnonymousClass29(wifiDisplayController, false, 0));
                WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
                WifiP2pDevice wifiP2pDevice = wifiDisplayController2.mConnectedDevice;
                if (wifiP2pDevice != null && wifiP2pDevice == wifiDisplayController2.mDesiredDevice) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Closed RTSP connection with Wifi display: "), WifiDisplayController.this.mConnectedDevice.deviceName, "WifiDisplayController");
                    WifiDisplayController.this.disconnect();
                }
                if (WifiDisplayController.this.mWifiDisplayConfig.isInitiateMirroringMode()) {
                    try {
                        WifiDisplayController wifiDisplayController3 = WifiDisplayController.this;
                        wifiDisplayController3.mContext.unregisterReceiver(wifiDisplayController3.mInitiateReceiver);
                    } catch (IllegalArgumentException unused) {
                        Log.w("WifiDisplayController", "unregisterReceiver:: mInitiateReceiver not registered");
                    }
                }
                WifiDisplayController wifiDisplayController4 = WifiDisplayController.this;
                wifiDisplayController4.getClass();
                try {
                    IGameManagerService iGameManagerService = wifiDisplayController4.mGameManagerService;
                    if (iGameManagerService != null) {
                        iGameManagerService.unregisterCallback(wifiDisplayController4.mGameCallback);
                        wifiDisplayController4.mGameManagerService = null;
                    }
                } catch (RemoteException e) {
                    Slog.e("WifiDisplayController", "mGameManagerService.unregisterCallback error");
                    e.printStackTrace();
                }
                try {
                    WifiDisplayController wifiDisplayController5 = WifiDisplayController.this;
                    wifiDisplayController5.mContext.unregisterReceiver(wifiDisplayController5.mAudioModeChangedReceiver);
                } catch (IllegalArgumentException unused2) {
                    Log.w("WifiDisplayController", "unregisterReceiver:: mAudioModeChangedReceiver not registered");
                }
                WifiDisplayController.this.sendEventToSemDeviceStatusListener(0);
            }

            public final void onDisplayError(int i4) {
                WifiDisplayController wifiDisplayController;
                WifiP2pDevice wifiP2pDevice;
                Slog.d("WifiDisplayController", "onDisplayError");
                WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
                wifiDisplayController2.mHandler.post(new AnonymousClass29(wifiDisplayController2, false, 0));
                if (i4 == 3) {
                    Slog.e("WifiDisplayController", "HDCP Key is no written");
                    WifiDisplayController.this.handleConnectionFailure(3);
                } else {
                    if (WifiDisplayController.m469$$Nest$misVpnConnected(WifiDisplayController.this) || (wifiP2pDevice = (wifiDisplayController = WifiDisplayController.this).mConnectedDevice) == null || wifiP2pDevice != wifiDisplayController.mDesiredDevice) {
                        return;
                    }
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(BatteryService$$ExternalSyntheticOutline0.m(i4, "Lost RTSP connection with Wifi display due to error ", ": "), WifiDisplayController.this.mConnectedDevice.deviceName, "WifiDisplayController");
                    WifiDisplayController.this.handleConnectionFailure(4);
                }
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = anonymousClass14;
        this.mPersistentDataStore = persistentDataStore;
        this.mWifiDisplayConfig = new SemWifiDisplayConfig();
        this.mSecureRandom = new SecureRandom();
        this.mWifiP2pManager = (WifiP2pManager) context.getSystemService("wifip2p");
        this.mSemWifiP2pManager = (SemWifiP2pManager) context.getSystemService("sem_wifi_p2p");
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        context.registerReceiver(broadcastReceiver, VcnManagementService$$ExternalSyntheticOutline0.m("android.net.wifi.p2p.STATE_CHANGED", "android.net.wifi.p2p.PEERS_CHANGED", "android.net.wifi.p2p.CONNECTION_STATE_CHANGE", "android.net.wifi.p2p.THIS_DEVICE_CHANGED", "android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED"), null, handler, 2);
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.server.display.WifiDisplayController.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                ContentResolver contentResolver = wifiDisplayController.mContext.getContentResolver();
                boolean z2 = true;
                wifiDisplayController.mWifiDisplayOnSetting = Settings.Global.getInt(contentResolver, "wifi_display_on", 0) != 0;
                boolean z3 = Settings.Global.getInt(contentResolver, "wifi_display_certification_on", 0) != 0;
                wifiDisplayController.mWifiDisplayCertMode = z3;
                if (z3) {
                    Settings.Global.getInt(contentResolver, "wifi_display_wps_config", 4);
                }
                if (wifiDisplayController.mWifiDisplayOnSetting) {
                    if ("0".equals(SystemProperties.get("secmm.wfd.running", "0"))) {
                        SystemProperties.set("secmm.wfd.running", "1");
                        Slog.d("WifiDisplayController", "[SECMM] Starting remotedisplay from Controller..");
                    }
                    wifiDisplayController.retrieveWifiP2pManagerAndChannel();
                } else {
                    StringBuilder sb = new StringBuilder("disableP2p:: mConnectedDevice = ");
                    sb.append(wifiDisplayController.mConnectedDevice);
                    sb.append(", mWifiTurnedOnByController = ");
                    sb.append(wifiDisplayController.mWifiTurnedOnByController);
                    sb.append(", mWifiP2pEnabled = ");
                    RCPManagerService$$ExternalSyntheticOutline0.m("WifiDisplayController", sb, wifiDisplayController.mWifiP2pEnabled);
                    if (wifiDisplayController.mWifiTurnedOnByController) {
                        wifiDisplayController.mHandler.postDelayed(wifiDisplayController.mWifiDisable, 1000L);
                        wifiDisplayController.mHandler.post(new AnonymousClass29(wifiDisplayController, z2, 1));
                    }
                }
                wifiDisplayController.updateWfdEnableState();
            }
        };
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("wifi_display_on"), false, contentObserver);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("wifi_display_certification_on"), false, contentObserver);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("wifi_display_wps_config"), false, contentObserver);
        this.mWfdUibcManager = new WFDUibcManager(context);
        this.mMcfManager = new WifiDisplayMcfManager(context, handler, persistentDataStore);
        Settings.Global.putInt(context.getContentResolver(), "wifi_display_on", 0);
    }

    public static String byteArrayToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            i = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02x", new Object[]{Integer.valueOf(bArr[i] & 255)}, sb, i, 1);
        }
        return sb.toString();
    }

    public static String describeWifiP2pDevice(WifiP2pDevice wifiP2pDevice) {
        return wifiP2pDevice != null ? wifiP2pDevice.toString().replace('\n', ',') : "null";
    }

    public static byte[] encryptByAES256CBC(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr.length > 32) {
            bArr = trimKeyToLength(32, bArr);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(bArr3));
        return cipher.doFinal(bArr2);
    }

    public static byte[] getEncryptData(int[] iArr, byte[] bArr) {
        byte[] bArr2 = new byte[16];
        int i = 0;
        int i2 = 0;
        while (i < 8) {
            bArr2[i2] = Byte.parseByte(Integer.toHexString(iArr[i]));
            i++;
            i2++;
        }
        System.arraycopy(bArr, 0, bArr2, i2, 6);
        byte[] bArr3 = new byte[2];
        new SecureRandom().nextBytes(bArr3);
        System.arraycopy(bArr3, 0, bArr2, i2 + 6, 2);
        return bArr2;
    }

    public static Inet4Address getInterfaceAddress(WifiP2pGroup wifiP2pGroup) {
        try {
            Enumeration<InetAddress> inetAddresses = NetworkInterface.getByName(wifiP2pGroup.getInterface()).getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress nextElement = inetAddresses.nextElement();
                if (nextElement instanceof Inet4Address) {
                    return (Inet4Address) nextElement;
                }
            }
            Slog.w("WifiDisplayController", "Could not obtain address of network interface " + wifiP2pGroup.getInterface() + " because it had no IPv4 addresses.");
            return null;
        } catch (SocketException e) {
            Slog.w("WifiDisplayController", "Could not obtain address of network interface " + wifiP2pGroup.getInterface(), e);
            return null;
        }
    }

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) (Character.digit(str.charAt(i + 1), 16) + (Character.digit(str.charAt(i), 16) << 4));
        }
        return bArr;
    }

    public static byte[] macAddressStringToByteArray(String str) {
        String[] split = str.split(":");
        byte[] bArr = new byte[6];
        for (int i = 0; i < 6; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return bArr;
    }

    public static String parseMacAddressToUpperString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(18);
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            byte b = bArr[i];
            if (sb.length() > 0) {
                sb.append(':');
            }
            i = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02X", new Object[]{Byte.valueOf(b)}, sb, i, 1);
        }
        return sb.toString();
    }

    public static byte[] trimKeyToLength(int i, byte[] bArr) {
        if (bArr.length <= i) {
            return bArr;
        }
        StringBuilder sb = new StringBuilder("trimKeyToLength, key : ");
        sb.append(Arrays.toString(bArr));
        sb.append(", key.length : ");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, bArr.length, "WifiDisplayController");
        return Arrays.copyOfRange(bArr, 0, i);
    }

    public static JSONArray wifiDisplayParameterToJOSNArray(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
        char c;
        JSONArray jSONArray = new JSONArray();
        int hashCode = str.hashCode();
        if (hashCode == 1071665814) {
            if (str.equals("initparams")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 1402102888) {
            if (hashCode == 1616719964 && str.equals("getparams")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("setparams")) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1 && !TextUtils.isEmpty(semWifiDisplayParameter.getKey()) && !TextUtils.isEmpty(semWifiDisplayParameter.getValue())) {
                try {
                    jSONArray.put(new JSONObject().put(semWifiDisplayParameter.getKey(), semWifiDisplayParameter.getValue()));
                } catch (JSONException unused) {
                    Log.e("WifiDisplayController", "wifiDisplaySetParameterToJOSNArray JSONException");
                }
            }
        } else if (!TextUtils.isEmpty(semWifiDisplayParameter.getKey()) && !TextUtils.isEmpty(semWifiDisplayParameter.getValue())) {
            jSONArray.put(semWifiDisplayParameter.toString());
        }
        return jSONArray;
    }

    public final void advertiseDisplay(final WifiDisplay wifiDisplay, final Surface surface, final int i, final int i2, final int i3) {
        if (Objects.equals(this.mAdvertisedDisplay, wifiDisplay) && this.mAdvertisedDisplaySurface == surface && this.mAdvertisedDisplayWidth == i && this.mAdvertisedDisplayHeight == i2 && this.mAdvertisedDisplayFlags == i3) {
            return;
        }
        final WifiDisplay wifiDisplay2 = this.mAdvertisedDisplay;
        final Surface surface2 = this.mAdvertisedDisplaySurface;
        this.mAdvertisedDisplay = wifiDisplay;
        this.mAdvertisedDisplaySurface = surface;
        this.mAdvertisedDisplayWidth = i;
        this.mAdvertisedDisplayHeight = i2;
        this.mAdvertisedDisplayFlags = i3;
        this.mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayController.19
            @Override // java.lang.Runnable
            public final void run() {
                WifiDisplay wifiDisplay3;
                Surface surface3;
                Surface surface4;
                Surface surface5 = surface2;
                boolean z = true;
                if ((surface5 == null || surface != null) && ((wifiDisplay3 = wifiDisplay2) == null || surface5 != null || wifiDisplay3.hasSameAddress(wifiDisplay))) {
                    WifiDisplay wifiDisplay4 = wifiDisplay2;
                    if (wifiDisplay4 != null && !wifiDisplay4.hasSameAddress(wifiDisplay)) {
                        WifiDisplayAdapter.AnonymousClass14 anonymousClass14 = WifiDisplayController.this.mListener;
                        synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                            WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                            if (wifiDisplayAdapter.mActiveDisplayState != 0 || wifiDisplayAdapter.mActiveDisplay != null) {
                                wifiDisplayAdapter.mActiveDisplayState = 0;
                                wifiDisplayAdapter.mActiveDisplay = null;
                                wifiDisplayAdapter.scheduleStatusChangedBroadcastLocked();
                            }
                        }
                        WifiDisplayController.m468$$Nest$mfinish(WifiDisplayController.this);
                    }
                } else {
                    WifiDisplayAdapter.AnonymousClass14 anonymousClass142 = WifiDisplayController.this.mListener;
                    synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                        WifiDisplayAdapter.this.removeDisplayDeviceLocked();
                        WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                        if (wifiDisplayAdapter2.mActiveDisplayState != 0 || wifiDisplayAdapter2.mActiveDisplay != null) {
                            DlnaController dlnaController = wifiDisplayAdapter2.mDlnaController;
                            if (dlnaController.mDevice.isConnected()) {
                                dlnaController.mHandler.post(new DlnaController.AnonymousClass1(0, dlnaController));
                            }
                            WifiDisplayAdapter wifiDisplayAdapter3 = WifiDisplayAdapter.this;
                            wifiDisplayAdapter3.mActiveDisplayState = 0;
                            wifiDisplayAdapter3.mActiveDisplay = null;
                            WifiDisplay[] wifiDisplayArr = WifiDisplay.EMPTY_ARRAY;
                            wifiDisplayAdapter3.mAvailableDisplays = wifiDisplayArr;
                            wifiDisplayAdapter3.mDisplays = wifiDisplayArr;
                            wifiDisplayAdapter3.scheduleStatusChangedBroadcastLocked();
                        }
                        WifiDisplayAdapter wifiDisplayAdapter4 = WifiDisplayAdapter.this;
                        wifiDisplayAdapter4.getClass();
                        wifiDisplayAdapter4.mHandler.post(new WifiDisplayAdapter.AnonymousClass11(wifiDisplayAdapter4, z, 1));
                    }
                    Settings.Global.putInt(WifiDisplayAdapter.this.mContext.getContentResolver(), "wifi_display_on", 0);
                    WifiDisplayController.m468$$Nest$mfinish(WifiDisplayController.this);
                }
                WifiDisplay wifiDisplay5 = wifiDisplay;
                if (wifiDisplay5 != null) {
                    if (!wifiDisplay5.hasSameAddress(wifiDisplay2)) {
                        WifiDisplayAdapter.AnonymousClass14 anonymousClass143 = WifiDisplayController.this.mListener;
                        WifiDisplay wifiDisplay6 = wifiDisplay;
                        synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                            try {
                                WifiDisplay applyWifiDisplayAlias = WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay6);
                                WifiDisplayAdapter wifiDisplayAdapter5 = WifiDisplayAdapter.this;
                                if (wifiDisplayAdapter5.mActiveDisplayState == 1) {
                                    WifiDisplay wifiDisplay7 = wifiDisplayAdapter5.mActiveDisplay;
                                    if (wifiDisplay7 != null) {
                                        if (!wifiDisplay7.equals(applyWifiDisplayAlias)) {
                                        }
                                    }
                                }
                                WifiDisplayAdapter wifiDisplayAdapter6 = WifiDisplayAdapter.this;
                                wifiDisplayAdapter6.mActiveDisplayState = 1;
                                wifiDisplayAdapter6.mActiveDisplay = applyWifiDisplayAlias;
                                wifiDisplayAdapter6.mDisplays = new WifiDisplay[]{applyWifiDisplayAlias};
                                wifiDisplayAdapter6.scheduleStatusChangedBroadcastLocked();
                            } finally {
                            }
                        }
                    } else if (!wifiDisplay.equals(wifiDisplay2) || ((surface3 = surface2) != null && surface3 != surface)) {
                        WifiDisplayAdapter.AnonymousClass14 anonymousClass144 = WifiDisplayController.this.mListener;
                        WifiDisplay wifiDisplay8 = wifiDisplay;
                        Surface surface6 = surface;
                        int i4 = i;
                        int i5 = i2;
                        synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                            try {
                                Slog.i("WifiDisplayAdapter", "onDisplayChanged");
                                WifiDisplay applyWifiDisplayAlias2 = WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay8);
                                WifiDisplay wifiDisplay9 = WifiDisplayAdapter.this.mActiveDisplay;
                                if (wifiDisplay9 != null && wifiDisplay9.hasSameAddress(applyWifiDisplayAlias2)) {
                                    if (WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias2)) {
                                        WifiDisplayAdapter.m467$$Nest$mupdateDisplaySurfaceLocked(WifiDisplayAdapter.this, surface6, i4, i5);
                                    } else {
                                        WifiDisplayAdapter wifiDisplayAdapter7 = WifiDisplayAdapter.this;
                                        wifiDisplayAdapter7.mActiveDisplay = applyWifiDisplayAlias2;
                                        String friendlyDisplayName = applyWifiDisplayAlias2.getFriendlyDisplayName();
                                        WifiDisplayAdapter.WifiDisplayDevice wifiDisplayDevice = wifiDisplayAdapter7.mDisplayDevice;
                                        if (wifiDisplayDevice != null && !wifiDisplayDevice.getDisplayDeviceInfoLocked().name.equals(friendlyDisplayName)) {
                                            WifiDisplayAdapter.WifiDisplayDevice wifiDisplayDevice2 = wifiDisplayAdapter7.mDisplayDevice;
                                            wifiDisplayDevice2.mName = friendlyDisplayName;
                                            wifiDisplayDevice2.mInfo = null;
                                            wifiDisplayAdapter7.sendDisplayDeviceEventLocked(wifiDisplayDevice2, 2);
                                        }
                                    }
                                    WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                                }
                            } finally {
                            }
                        }
                    }
                    if (surface2 != null || (surface4 = surface) == null) {
                        return;
                    }
                    WifiDisplayController.this.mListener.onDisplayConnected(wifiDisplay, surface4, i, i2, i3);
                }
            }
        });
    }

    public final void connectP2p(WifiP2pDevice wifiP2pDevice, WifiP2pConfig wifiP2pConfig) {
        Slog.i("WifiDisplayController", "connectP2p");
        this.mWifiP2pManager.connect(this.mWifiP2pChannel, wifiP2pConfig, new AnonymousClass10(this, wifiP2pDevice, 2));
    }

    public final WifiP2pConfig createConnectConfig(WifiP2pDevice wifiP2pDevice) {
        WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
        if (wifiP2pDevice != null) {
            wifiP2pConfig = new WifiP2pConfig.Builder().setDeviceAddress(MacAddress.fromString(wifiP2pDevice.deviceAddress)).enablePersistentMode(false).build();
            SemWifiP2pDevice semWifiP2pDevice = this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice);
            if (semWifiP2pDevice != null) {
                Slog.d("WifiDisplayController", "semDeviceType :" + semWifiP2pDevice.getDeviceType());
                if (this.mWifiDisplayConfig.getMode() == 1 || this.mRequestedScanChannel != 1611 || semWifiP2pDevice.getDeviceType() == 6) {
                    Slog.d("WifiDisplayController", "set NETWORK_ID_PERSISTENT");
                    wifiP2pConfig = new WifiP2pConfig.Builder().setDeviceAddress(MacAddress.fromString(wifiP2pDevice.deviceAddress)).enablePersistentMode(true).build();
                }
                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("createConnectConfig:: name = "), wifiP2pDevice.deviceName, "WifiDisplayController");
                if (this.mWifiDisplayConfig.isPinRequest() && wifiP2pDevice.wpsDisplaySupported()) {
                    wifiP2pConfig.wps.setup = 2;
                } else if (wifiP2pDevice.wpsPbcSupported()) {
                    wifiP2pConfig.wps.setup = 0;
                } else if (wifiP2pDevice.wpsDisplaySupported()) {
                    wifiP2pConfig.wps.setup = 2;
                } else if (wifiP2pDevice.wpsKeypadSupported()) {
                    wifiP2pConfig.wps.setup = 1;
                } else {
                    wifiP2pConfig.wps.setup = 0;
                }
            }
        }
        wifiP2pConfig.groupOwnerIntent = 13;
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("set groupOwnerIntent : "), wifiP2pConfig.groupOwnerIntent, "WifiDisplayController");
        return wifiP2pConfig;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0089 A[Catch: Exception -> 0x00a0, TryCatch #0 {Exception -> 0x00a0, blocks: (B:11:0x004f, B:13:0x0055, B:16:0x005c, B:21:0x006b, B:25:0x007c, B:27:0x0086, B:29:0x0089, B:33:0x008f, B:35:0x0094, B:36:0x00a2, B:39:0x0083), top: B:10:0x004f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c7 A[EDGE_INSN: B:37:0x00c7->B:43:0x00c7 BREAK  A[LOOP:1: B:27:0x0086->B:31:0x00ae], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.hardware.display.WifiDisplay createWifiDisplay(android.net.wifi.p2p.WifiP2pDevice r11) {
        /*
            r10 = this;
            android.hardware.display.WifiDisplay r8 = new android.hardware.display.WifiDisplay
            java.lang.String r1 = r11.deviceAddress
            java.lang.String r2 = r11.deviceName
            android.net.wifi.p2p.WifiP2pWfdInfo r0 = r11.getWfdInfo()
            r9 = 0
            if (r0 == 0) goto L17
            android.net.wifi.p2p.WifiP2pWfdInfo r0 = r11.getWfdInfo()
            boolean r0 = r0.isSessionAvailable()
            r5 = r0
            goto L18
        L17:
            r5 = r9
        L18:
            r6 = 0
            java.lang.String r7 = r11.primaryDeviceType
            r3 = 0
            r4 = 1
            r0 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            com.samsung.android.wifi.p2p.SemWifiP2pManager r0 = r10.mSemWifiP2pManager
            com.samsung.android.wifi.p2p.SemWifiP2pDevice r0 = r0.getSemWifiP2pDevice(r11)
            if (r0 == 0) goto Ld1
            int r1 = r0.getDeviceType()
            r8.setSamsungDeviceType(r1)
            int r1 = r0.getDeviceIcon()
            r8.setSamsungDeviceIcon(r1)
            java.lang.String r1 = "WifiDisplayController"
            com.samsung.android.wifi.p2p.SemWifiP2pManager r10 = r10.mSemWifiP2pManager
            com.samsung.android.wifi.p2p.SemWifiP2pDevice r10 = r10.getSemWifiP2pDevice(r11)
            java.lang.String r11 = ""
            if (r10 == 0) goto Lc7
            java.lang.String r2 = r10.getServiceData()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L4f
            goto Lc7
        L4f:
            java.lang.String r10 = r10.getServiceData()     // Catch: java.lang.Exception -> La0
            if (r10 == 0) goto L83
            int r2 = r10.length()     // Catch: java.lang.Exception -> La0
            if (r2 != 0) goto L5c
            goto L83
        L5c:
            int r2 = r10.length()     // Catch: java.lang.Exception -> La0
            int r2 = r2 / 2
            byte[] r3 = new byte[r2]     // Catch: java.lang.Exception -> La0
            r4 = r9
        L65:
            if (r4 >= r2) goto L85
            int r5 = r4 * 2
            int r6 = r5 + 2
            java.lang.String r5 = r10.substring(r5, r6)     // Catch: java.lang.NumberFormatException -> L7b java.lang.Exception -> La0
            r6 = 16
            int r5 = java.lang.Integer.parseInt(r5, r6)     // Catch: java.lang.NumberFormatException -> L7b java.lang.Exception -> La0
            byte r5 = (byte) r5     // Catch: java.lang.NumberFormatException -> L7b java.lang.Exception -> La0
            r3[r4] = r5     // Catch: java.lang.NumberFormatException -> L7b java.lang.Exception -> La0
            int r4 = r4 + 1
            goto L65
        L7b:
            r10 = move-exception
            java.lang.String r2 = "stringToByte, NumberFormatException : "
            android.util.Slog.w(r1, r2, r10)     // Catch: java.lang.Exception -> La0
            goto L85
        L83:
            byte[] r3 = new byte[r9]     // Catch: java.lang.Exception -> La0
        L85:
            r10 = 3
        L86:
            int r2 = r3.length     // Catch: java.lang.Exception -> La0
            if (r10 >= r2) goto Lc7
            r2 = r3[r10]     // Catch: java.lang.Exception -> La0
            r4 = r2 & 2
            if (r4 <= 0) goto Lae
            r2 = r2 & 1
            r4 = 6
            if (r2 <= 0) goto La2
            byte[] r2 = new byte[r4]     // Catch: java.lang.Exception -> La0
            int r10 = r10 + 7
            java.lang.System.arraycopy(r3, r10, r2, r9, r4)     // Catch: java.lang.Exception -> La0
            java.lang.String r11 = parseMacAddressToUpperString(r2)     // Catch: java.lang.Exception -> La0
            goto Lc7
        La0:
            r10 = move-exception
            goto Lb1
        La2:
            byte[] r2 = new byte[r4]     // Catch: java.lang.Exception -> La0
            int r10 = r10 + 1
            java.lang.System.arraycopy(r3, r10, r2, r9, r4)     // Catch: java.lang.Exception -> La0
            java.lang.String r11 = parseMacAddressToUpperString(r2)     // Catch: java.lang.Exception -> La0
            goto Lc7
        Lae:
            int r10 = r10 + 1
            goto L86
        Lb1:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "getBluetoothMacAddress, error : "
            r2.<init>(r3)
            java.lang.String r10 = r10.toString()
            r2.append(r10)
            java.lang.String r10 = r2.toString()
            android.util.Slog.w(r1, r10)
        Lc7:
            r8.setBluetoothMacAddress(r11)
            int r10 = r0.getScreenSharingInfo()
            r8.setDeviceInfo(r10)
        Ld1:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.WifiDisplayController.createWifiDisplay(android.net.wifi.p2p.WifiP2pDevice):android.hardware.display.WifiDisplay");
    }

    public final void disconnect() {
        Log.d("WifiDisplayController", "disconnect() : " + this.mConnectedDevice);
        if (!this.mRemoteDisplayConnected) {
            onConnectionFailure(1);
        }
        if (this.mWifiDisplayConfig.isSkipPinNumberConfirm()) {
            Slog.d("WifiDisplayController", "clear setPreparedAccountPin");
            this.mSemWifiP2pManager.setPreparedAccountPin((String) null, (String) null, (String) null, new AnonymousClass9());
        }
        if (this.mWifiDisplayConfig.getConnectionType() != 2 && this.mWifiDisplayConfig.getConnectionType() != 3) {
            this.mDesiredDevice = null;
            updateConnection();
        } else if (this.mRemoteDisplay != null) {
            if (this.mWifiDisplayConfig.getConnectionType() == 3) {
                setParam("fcdc", Boolean.TRUE);
            }
            sendEventToSemDeviceStatusListener(3);
            this.mRemoteDisplay.dispose();
            this.mRemoteDisplay = null;
            this.mRemoteDisplayInterface = null;
            this.mRemoteDisplayConnected = false;
            unadvertiseDisplay();
        }
    }

    public final void dump(PrintWriter printWriter, String str) {
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mWifiDisplayOnSetting="), this.mWifiDisplayOnSetting, printWriter, "mWifiP2pEnabled="), this.mWifiP2pEnabled, printWriter, "mWfdEnabled="), this.mWfdEnabled, printWriter, "mWfdEnabling="), this.mWfdEnabling, printWriter, "mNetworkInfo=");
        m.append(this.mNetworkInfo);
        printWriter.println(m.toString());
        StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mScanRequested="), this.mScanRequested, printWriter, "mDiscoverPeersInProgress="), this.mDiscoverPeersInProgress, printWriter, "mDesiredDevice=");
        m2.append(describeWifiP2pDevice(this.mDesiredDevice));
        printWriter.println(m2.toString());
        printWriter.println("mConnectingDisplay=" + describeWifiP2pDevice(this.mConnectingDevice));
        printWriter.println("mDisconnectingDisplay=" + describeWifiP2pDevice(this.mDisconnectingDevice));
        printWriter.println("mCancelingDisplay=" + describeWifiP2pDevice(this.mCancelingDevice));
        printWriter.println("mConnectedDevice=" + describeWifiP2pDevice(this.mConnectedDevice));
        StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mConnectionRetriesLeft="), this.mConnectionRetriesLeft, printWriter, "mRemoteDisplay=");
        m3.append(this.mRemoteDisplay);
        printWriter.println(m3.toString());
        StringBuilder m4 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, this.mRemoteDisplayInterface, "mRemoteDisplayConnected=", new StringBuilder("mRemoteDisplayInterface=")), this.mRemoteDisplayConnected, printWriter, "mAdvertisedDisplay=");
        m4.append(this.mAdvertisedDisplay);
        printWriter.println(m4.toString());
        printWriter.println("mAdvertisedDisplaySurface=" + this.mAdvertisedDisplaySurface);
        StringBuilder m5 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mAdvertisedDisplayWidth="), this.mAdvertisedDisplayWidth, printWriter, "mAdvertisedDisplayHeight="), this.mAdvertisedDisplayHeight, printWriter, "mAdvertisedDisplayFlags="), this.mAdvertisedDisplayFlags, printWriter, "mAvailableWifiDisplayPeers: size=");
        m5.append(this.mAvailableWifiDisplayPeers.size());
        printWriter.println(m5.toString());
        Iterator it = this.mAvailableWifiDisplayPeers.iterator();
        while (it.hasNext()) {
            printWriter.println("  " + describeWifiP2pDevice((WifiP2pDevice) it.next()));
        }
    }

    public final void enableP2p() {
        Cursor cursor;
        try {
            cursor = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"), null, "isWifiEnabled", new String[]{"true"}, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            cursor.moveToFirst();
            try {
                try {
                    if (cursor.getString(cursor.getColumnIndex("isWifiEnabled")).equals("false")) {
                        Log.e("WifiDisplayController", "Restriction policy block wifi change");
                        return;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                cursor.close();
            }
        }
        if (this.mWifiManager.getWifiState() != 3) {
            Log.d("WifiDisplayController", "turn on wifi by controller : wifiState = " + this.mWifiManager.getWifiState());
            this.mWifiTurnedOnByController = true;
            this.mHandler.post(new AnonymousClass29(this, false, 1));
        }
        retrieveWifiP2pManagerAndChannel();
        this.mWifiManager.setWifiEnabled(true);
    }

    public final String getEngineParameters() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.mWifiDisplayConfig.getConnectionType() == 1) {
                this.mWifiDisplayConfig.addParameter("initparams", new SemWifiDisplayParameter("frequency", this.mP2pFrequency));
            } else if (this.mWifiDisplayConfig.getConnectionType() == 2 && this.mWifiManager.getConnectionInfo() != null) {
                this.mWifiDisplayConfig.addParameter("initparams", new SemWifiDisplayParameter("frequency", this.mWifiManager.getConnectionInfo().getFrequency()));
            }
            if (this.mWifiDisplayConfig.getMode() == 0) {
                this.mWifiDisplayConfig.addParameter("getparams", new SemWifiDisplayParameter("wfd_sec_view_mode"));
                this.mWifiDisplayConfig.addParameter("getparams", new SemWifiDisplayParameter("wfd_sec_dmr_support"));
                this.mWifiDisplayConfig.addParameter("getparams", new SemWifiDisplayParameter("wfd_sec_dmr_support_type"));
                this.mWifiDisplayConfig.addParameter("getparams", new SemWifiDisplayParameter("wfd_sec_voip_mode"));
                this.mWifiDisplayConfig.addParameter("getparams", new SemWifiDisplayParameter("wfd_sec_tv_ble_mac"));
                this.mWifiDisplayConfig.addParameter("setparams", new SemWifiDisplayParameter("wfd_sec_mirroring_uuid", this.mMcfManager.getInitiatedMirroringUuid()));
                if (Build.VERSION.SEM_PLATFORM_INT >= 150100) {
                    this.mWifiDisplayConfig.addParameter("getparams", new SemWifiDisplayParameter("wfd_sec_dmr_meta_check"));
                }
            }
            jSONObject.put("initparams", this.mWifiDisplayConfig.getJasonArrayParameters("initparams"));
            jSONObject.put("getparams", this.mWifiDisplayConfig.getJasonArrayParameters("getparams"));
            jSONObject.put("setparams", this.mWifiDisplayConfig.getJasonArrayParameters("setparams"));
        } catch (JSONException e) {
            Slog.w("WifiDisplayController", e.toString());
        }
        return jSONObject.toString();
    }

    public final WifiDisplaySessionInfo getSessionInfo(WifiP2pGroup wifiP2pGroup, int i) {
        if (wifiP2pGroup == null) {
            return null;
        }
        Inet4Address interfaceAddress = getInterfaceAddress(wifiP2pGroup);
        return new WifiDisplaySessionInfo(!wifiP2pGroup.getOwner().deviceAddress.equals(this.mThisDevice.deviceAddress), i, wifiP2pGroup.getOwner().deviceAddress + " " + wifiP2pGroup.getNetworkName(), wifiP2pGroup.getPassphrase(), interfaceAddress != null ? interfaceAddress.getHostAddress() : "");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001b, code lost:
    
        if (r6 != 6) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleConnectionFailure(int r6) {
        /*
            r5 = this;
            java.lang.String r0 = "WifiDisplayController"
            java.lang.String r1 = "Wifi display connection failed!"
            android.util.Slog.i(r0, r1)
            r0 = 1
            r1 = 17043083(0x1040e8b, float:2.4255005E-38)
            r2 = 0
            r3 = 16974123(0x103012b, float:2.4061738E-38)
            if (r6 == r0) goto L78
            r0 = 2
            if (r6 == r0) goto L66
            r0 = 3
            if (r6 == r0) goto L46
            r0 = 5
            if (r6 == r0) goto L1f
            r0 = 6
            if (r6 == r0) goto L78
            goto L9e
        L1f:
            android.view.ContextThemeWrapper r0 = new android.view.ContextThemeWrapper
            android.content.Context r4 = r5.mContext
            r0.<init>(r4, r3)
            android.content.Context r3 = r5.mContext
            android.net.wifi.p2p.WifiP2pDevice r4 = r5.mDesiredDevice
            if (r4 == 0) goto L2f
            java.lang.String r1 = r4.deviceName
            goto L33
        L2f:
            java.lang.String r1 = r3.getString(r1)
        L33:
            java.lang.Object[] r1 = new java.lang.Object[]{r1}
            r4 = 17043514(0x104103a, float:2.4256213E-38)
            java.lang.String r1 = r3.getString(r4, r1)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
            r0.show()
            goto L9e
        L46:
            android.view.ContextThemeWrapper r0 = new android.view.ContextThemeWrapper
            android.content.Context r4 = r5.mContext
            r0.<init>(r4, r3)
            android.content.Context r3 = r5.mContext
            java.lang.String r1 = r3.getString(r1)
            java.lang.Object[] r1 = new java.lang.Object[]{r1}
            r4 = 17043511(0x1041037, float:2.4256205E-38)
            java.lang.String r1 = r3.getString(r4, r1)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
            r0.show()
            goto L9e
        L66:
            android.view.ContextThemeWrapper r0 = new android.view.ContextThemeWrapper
            android.content.Context r1 = r5.mContext
            r0.<init>(r1, r3)
            r1 = 17043513(0x1041039, float:2.425621E-38)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
            r0.show()
            goto L9e
        L78:
            android.view.ContextThemeWrapper r0 = new android.view.ContextThemeWrapper
            android.content.Context r4 = r5.mContext
            r0.<init>(r4, r3)
            android.content.Context r3 = r5.mContext
            android.net.wifi.p2p.WifiP2pDevice r4 = r5.mDesiredDevice
            if (r4 == 0) goto L88
            java.lang.String r1 = r4.deviceName
            goto L8c
        L88:
            java.lang.String r1 = r3.getString(r1)
        L8c:
            java.lang.Object[] r1 = new java.lang.Object[]{r1}
            r4 = 17043512(0x1041038, float:2.4256207E-38)
            java.lang.String r1 = r3.getString(r4, r1)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
            r0.show()
        L9e:
            r5.onConnectionFailure(r6)
            r5.disconnect()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.WifiDisplayController.handleConnectionFailure(int):void");
    }

    public final void onConnectionFailure(int i) {
        try {
            IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback = this.mCallback;
            if (iWifiDisplayConnectionCallback != null) {
                iWifiDisplayConnectionCallback.onFailure(i);
                this.mCallback = null;
            }
        } catch (RemoteException unused) {
            Slog.w("WifiDisplayController", "Failed to onFailure");
        }
    }

    public final void requestConnect(String str) {
        WifiP2pDevice wifiP2pDevice;
        Iterator it = this.mAvailableWifiDisplayPeers.iterator();
        while (it.hasNext()) {
            WifiP2pDevice wifiP2pDevice2 = (WifiP2pDevice) it.next();
            if (wifiP2pDevice2.deviceAddress.equals(str) && ((wifiP2pDevice = this.mDesiredDevice) == null || wifiP2pDevice.deviceAddress.equals(wifiP2pDevice2.deviceAddress))) {
                WifiP2pDevice wifiP2pDevice3 = this.mConnectedDevice;
                if (wifiP2pDevice3 == null || wifiP2pDevice3.deviceAddress.equals(wifiP2pDevice2.deviceAddress) || this.mDesiredDevice != null) {
                    if (this.mWfdEnabled) {
                        this.mDesiredDevice = wifiP2pDevice2;
                        this.mConnectionRetriesLeft = 3;
                        updateConnection();
                    } else {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Ignoring request to connect to Wifi display because the  feature is currently disabled: "), wifiP2pDevice2.deviceName, "WifiDisplayController");
                    }
                }
            }
        }
        if (this.mDesiredDevice == null) {
            Log.e("WifiDisplayController", "Can not found desired device");
            onConnectionFailure(1);
        }
    }

    public final boolean requestSetWifiDisplayParameters(List list) {
        if (this.mAdvertisedDisplay == null || list == null || list.isEmpty()) {
            return false;
        }
        Slog.d("WifiDisplayController", "requestSetWifiDisplayParameters, parameters : " + list.toString());
        JSONArray jSONArray = new JSONArray();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SemWifiDisplayParameter semWifiDisplayParameter = (SemWifiDisplayParameter) it.next();
            if (!TextUtils.isEmpty(semWifiDisplayParameter.getKey()) && !TextUtils.isEmpty(semWifiDisplayParameter.getValue())) {
                jSONArray.put(semWifiDisplayParameter.toString());
            }
        }
        setParam("setp", jSONArray);
        return true;
    }

    public final void retrieveWifiP2pManagerAndChannel() {
        WifiP2pManager wifiP2pManager;
        if (this.mWifiP2pManager == null) {
            this.mWifiP2pManager = (WifiP2pManager) this.mContext.getSystemService("wifip2p");
        }
        if (this.mWifiP2pChannel != null || (wifiP2pManager = this.mWifiP2pManager) == null) {
            return;
        }
        this.mWifiP2pChannel = wifiP2pManager.initialize(this.mContext, this.mHandler.getLooper(), null);
    }

    public final void sendEventToSemDeviceStatusListener(int i) {
        this.mHandler.post(new AnonymousClass21(this, i));
    }

    public final void sendWifiDisplayVolumeSupportChangedBroadcast() {
        if (this.mRemoteDisplayConnected) {
            this.mHandler.post(new AnonymousClass6(this, 4));
        }
    }

    public final void setParam(String str, Object obj) {
        RemoteDisplay remoteDisplay = this.mRemoteDisplay;
        if (remoteDisplay != null) {
            remoteDisplay.setParam(str, obj);
        }
    }

    public final void tryDiscoverPeers(int i, int i2) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "tryDiscoverPeers requestChannel=", ", isChannelConstrainedDiscoverySupported=");
        m.append(this.mWifiP2pManager.isChannelConstrainedDiscoverySupported());
        Slog.e("WifiDisplayController", m.toString());
        if (i == 0) {
            this.mWifiP2pManager.discoverPeers(this.mWifiP2pChannel, null);
        } else if (!this.mWifiP2pManager.isChannelConstrainedDiscoverySupported()) {
            this.mSemWifiP2pManager.discoverPeersOnSpecificChannel(i, (SemWifiP2pManager.ActionListener) null);
        } else if (i == 1611) {
            this.mWifiP2pManager.discoverPeersOnSocialChannels(this.mWifiP2pChannel, null);
        } else {
            WifiP2pManager wifiP2pManager = this.mWifiP2pManager;
            WifiP2pManager.Channel channel = this.mWifiP2pChannel;
            if (i >= 1 && i <= 165) {
                i = (i * 5) + (i <= 14 ? 2407 : 5000);
            } else if (i == 0) {
                i = -1;
            }
            wifiP2pManager.discoverPeersOnSpecificFrequency(channel, i, null);
        }
        this.mHandler.postDelayed(this.mDiscoverPeers, i2);
    }

    public final void unadvertiseDisplay() {
        advertiseDisplay(null, null, 0, 0, 0);
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.server.display.WifiDisplayController$13] */
    /* JADX WARN: Type inference failed for: r9v4, types: [com.android.server.display.WifiDisplayController$13] */
    public final void updateConnection() {
        WifiP2pDevice wifiP2pDevice;
        if (this.mWifiDisplayConfig.getConnectionType() != 1) {
            return;
        }
        StringBuilder sb = new StringBuilder("updateConnection, mConnectedDevice = ");
        sb.append(this.mConnectedDevice != null);
        sb.append(", mRemoteDisplay = ");
        sb.append(this.mRemoteDisplay != null);
        Slog.d("WifiDisplayController", sb.toString());
        retrieveWifiP2pManagerAndChannel();
        updateScanState();
        byte[] bArr = null;
        if (this.mRemoteDisplay != null && (wifiP2pDevice = this.mConnectedDevice) != null && wifiP2pDevice != this.mDesiredDevice) {
            Slog.i("WifiDisplayController", "Stopped listening for RTSP connection on " + this.mRemoteDisplayInterface + " from Wifi display: " + this.mConnectedDevice.deviceName);
            Intent intent = new Intent("com.samsung.intent.action.ROTATION_CHANGED");
            intent.putExtra("rotation", 0);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            this.mRemoteDisplay.dispose();
            this.mRemoteDisplay = null;
            this.mRemoteDisplayInterface = null;
            this.mRemoteDisplayConnected = false;
            this.mHandler.removeCallbacks(this.mRtspTimeout);
            this.mWifiP2pManager.setMiracastMode(0);
            unadvertiseDisplay();
        }
        if (this.mDisconnectingDevice != null) {
            return;
        }
        WifiP2pDevice wifiP2pDevice2 = this.mConnectedDevice;
        if (wifiP2pDevice2 != null && wifiP2pDevice2 != this.mDesiredDevice) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Disconnecting from Wifi display: "), this.mConnectedDevice.deviceName, "WifiDisplayController");
            this.mDisconnectingDevice = this.mConnectedDevice;
            this.mConnectedDevice = null;
            this.mConnectedDeviceGroupInfo = null;
            unadvertiseDisplay();
            WifiP2pDevice wifiP2pDevice3 = this.mDisconnectingDevice;
            WifiP2pGroup wifiP2pGroup = this.mConnectedDeviceGroupInfo;
            if (wifiP2pGroup == null || !wifiP2pGroup.isGroupOwner()) {
                this.mWifiP2pManager.removeGroup(this.mWifiP2pChannel, new AnonymousClass10(this, wifiP2pDevice3, 1));
                return;
            } else {
                createConnectConfig(wifiP2pDevice3);
                this.mWifiP2pManager.removeClient(this.mWifiP2pChannel, MacAddress.fromString(wifiP2pDevice3.deviceAddress), new AnonymousClass10(this, wifiP2pDevice3, 0));
            }
        }
        if (this.mCancelingDevice != null) {
            return;
        }
        WifiP2pDevice wifiP2pDevice4 = this.mConnectingDevice;
        if (wifiP2pDevice4 != null && wifiP2pDevice4 != this.mDesiredDevice) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Canceling connection to Wifi display: "), this.mConnectingDevice.deviceName, "WifiDisplayController");
            this.mCancelingDevice = this.mConnectingDevice;
            this.mConnectingDevice = null;
            unadvertiseDisplay();
            this.mHandler.removeCallbacks(this.mConnectionTimeout);
            WifiP2pDevice wifiP2pDevice5 = this.mCancelingDevice;
            this.mWifiP2pManager.cancelConnect(this.mWifiP2pChannel, null);
            if (this.mCancelingDevice == wifiP2pDevice5) {
                this.mCancelingDevice = null;
                updateConnection();
                this.mHandler.removeCallbacks(this.mConnectionTimeout);
                if (this.mWifiDisplayConfig.isInitiateMirroringMode()) {
                    try {
                        this.mContext.unregisterReceiver(this.mInitiateReceiver);
                        return;
                    } catch (IllegalArgumentException unused) {
                        Log.w("WifiDisplayController", "unregisterReceiver:: mInitiateReceiver not registered");
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (this.mDesiredDevice == null) {
            if (this.mWifiDisplayCertMode) {
                WifiDisplayAdapter.AnonymousClass14 anonymousClass14 = this.mListener;
                WifiDisplaySessionInfo sessionInfo = getSessionInfo(this.mConnectedDeviceGroupInfo, 0);
                synchronized (WifiDisplayAdapter.this.mSyncRoot) {
                    WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                    wifiDisplayAdapter.mSessionInfo = sessionInfo;
                    wifiDisplayAdapter.scheduleStatusChangedBroadcastLocked();
                }
            }
            unadvertiseDisplay();
            return;
        }
        WifiP2pDevice wifiP2pDevice6 = this.mConnectedDevice;
        if (wifiP2pDevice6 != null || wifiP2pDevice4 != null) {
            if (wifiP2pDevice6 == null || this.mRemoteDisplay != null) {
                return;
            }
            Inet4Address interfaceAddress = getInterfaceAddress(this.mConnectedDeviceGroupInfo);
            if (interfaceAddress == null) {
                Slog.i("WifiDisplayController", "Failed to get local interface address for communicating with Wifi display: " + this.mConnectedDevice.deviceName);
                handleConnectionFailure(1);
                return;
            }
            this.mWifiP2pManager.setMiracastMode(1);
            WifiP2pDevice wifiP2pDevice7 = this.mConnectedDevice;
            String str = interfaceAddress.getHostAddress() + ":" + ((wifiP2pDevice7.deviceName.startsWith("DIRECT-") && wifiP2pDevice7.deviceName.endsWith("Broadcom")) ? 8554 : 7236);
            this.mRemoteDisplayInterface = str;
            DeviceIdleController$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("Listening for RTSP connection on ", str, " from Wifi display: "), this.mConnectedDevice.deviceName, "WifiDisplayController");
            this.mRemoteDisplay = RemoteDisplay.listen(str, this.remoteDisplayListener, this.mHandler, this.mContext.getOpPackageName(), getEngineParameters(), this.mNativeListener);
            this.mHandler.postDelayed(this.mRtspTimeout, (this.mWifiDisplayCertMode ? 120 : 30) * 1000);
            return;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Connecting to Wifi display: "), this.mDesiredDevice.deviceName, "WifiDisplayController");
        WifiP2pDevice wifiP2pDevice8 = this.mDesiredDevice;
        this.mConnectingDevice = wifiP2pDevice8;
        final WifiP2pConfig createConnectConfig = createConnectConfig(wifiP2pDevice8);
        WifiDisplay createWifiDisplay = createWifiDisplay(this.mConnectingDevice);
        createWifiDisplay.setMode(this.mWifiDisplayConfig.getMode());
        advertiseDisplay(createWifiDisplay, null, 0, 0, 0);
        if (!this.mWifiDisplayConfig.isSkipPinNumberConfirm()) {
            connectP2p(this.mDesiredDevice, createConnectConfig);
            return;
        }
        final WifiP2pDevice wifiP2pDevice9 = this.mDesiredDevice;
        SemWifiDisplayConfig semWifiDisplayConfig = this.mWifiDisplayConfig;
        Slog.i("WifiDisplayController", "connectP2pWithSkipPinNumberConfirm");
        SemWifiDisplayParameter parameter = semWifiDisplayConfig.getParameter("initparams", "samsung_account");
        SemWifiDisplayParameter parameter2 = semWifiDisplayConfig.getParameter("initparams", "tv_ble_irk");
        SemWifiDisplayParameter parameter3 = semWifiDisplayConfig.getParameter("initparams", "tv_device_id");
        if (parameter != null && !TextUtils.isEmpty(parameter.getValue()) && parameter2 != null && !TextUtils.isEmpty(parameter2.getValue()) && !TextUtils.isEmpty(this.mLocalDeviceP2pMacAddress)) {
            byte[] hexStringToByteArray = hexStringToByteArray(parameter2.getValue());
            final byte[] bytes = parameter.getValue().getBytes(StandardCharsets.UTF_8);
            final int i = 0;
            final ?? r9 = new SemWifiP2pManager.ActionListener(this) { // from class: com.android.server.display.WifiDisplayController.13
                public final /* synthetic */ WifiDisplayController this$0;

                {
                    this.this$0 = this;
                }

                public final void onFailure(int i2) {
                    switch (i) {
                        case 0:
                            Slog.i("WifiDisplayController", "setPreparedAccountPin with IRK, onFailure");
                            this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                            break;
                        default:
                            Slog.i("WifiDisplayController", "setPreparedAccountPin with SmartThings Device ID, onFailure");
                            this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                            break;
                    }
                }

                public final void onSuccess() {
                    switch (i) {
                        case 0:
                            Slog.i("WifiDisplayController", "setPreparedAccountPin with IRK, onSuccess");
                            this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                            break;
                        default:
                            Slog.i("WifiDisplayController", "setPreparedAccountPin with SmartThings Device ID, onSuccess");
                            this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                            break;
                    }
                }
            };
            Log.d("WifiDisplayController", "setPreparedAccountPin, irk : " + Arrays.toString(hexStringToByteArray) + ", samsungAccount : " + Arrays.toString(bytes));
            try {
                Mac mac = Mac.getInstance("HmacSHA512");
                mac.init(new SecretKeySpec(hexStringToByteArray, "HmacSHA512"));
                bArr = mac.doFinal(bytes);
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                Log.e("WifiDisplayController", "getEncryptionKeyByHmacSha512 Exception : " + e);
            }
            Optional.ofNullable(bArr).ifPresent(new Consumer() { // from class: com.android.server.display.WifiDisplayController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    byte[] bArr2;
                    WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                    byte[] bArr3 = bytes;
                    SemWifiP2pManager.ActionListener actionListener = r9;
                    byte[] bArr4 = (byte[]) obj;
                    wifiDisplayController.getClass();
                    SecureRandom secureRandom = new SecureRandom();
                    int[] iArr = new int[8];
                    for (int i2 = 0; i2 < 8; i2++) {
                        iArr[i2] = secureRandom.nextInt(10);
                    }
                    byte[] bArr5 = new byte[16];
                    wifiDisplayController.mSecureRandom.nextBytes(bArr5);
                    Log.i("WifiDisplayController", "pinNumber = " + Arrays.toString(iArr).replaceAll("[^0-9]", "") + " , local mac address : " + wifiDisplayController.mLocalDeviceP2pMacAddress + ", " + Arrays.toString(WifiDisplayController.macAddressStringToByteArray(wifiDisplayController.mLocalDeviceP2pMacAddress)) + " , iv hex = " + WifiDisplayController.byteArrayToHexString(bArr5) + " , iv byte array = " + Arrays.toString(bArr5) + " , encryptionKey byte array: " + Arrays.toString(bArr4) + " , encryptionKey hex string: " + WifiDisplayController.byteArrayToHexString(bArr4));
                    try {
                        byte[] encryptByAES256CBC = WifiDisplayController.encryptByAES256CBC(bArr4, WifiDisplayController.getEncryptData(iArr, WifiDisplayController.macAddressStringToByteArray(wifiDisplayController.mLocalDeviceP2pMacAddress)), bArr5);
                        try {
                            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                            messageDigest.update(bArr3);
                            bArr2 = messageDigest.digest();
                        } catch (NoSuchAlgorithmException e2) {
                            Log.e("WifiDisplayController", "encryptBySHA256 Exception : " + e2);
                            bArr2 = null;
                        }
                        byte[] trimKeyToLength = WifiDisplayController.trimKeyToLength(16, bArr2);
                        Log.i("WifiDisplayController", "encryptedValue : " + Arrays.toString(encryptByAES256CBC) + ", hex : " + WifiDisplayController.byteArrayToHexString(encryptByAES256CBC) + ", hashedAccount : " + Arrays.toString(trimKeyToLength) + ", hex : " + WifiDisplayController.byteArrayToHexString(trimKeyToLength));
                        wifiDisplayController.mSemWifiP2pManager.setPreparedAccountPin(2, Arrays.toString(iArr).replaceAll("[^0-9]", ""), WifiDisplayController.byteArrayToHexString(encryptByAES256CBC), WifiDisplayController.byteArrayToHexString(bArr5), WifiDisplayController.byteArrayToHexString(trimKeyToLength), actionListener);
                    } catch (Exception e3) {
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e3, "setPreparedAccountPin failed : ", "WifiDisplayController");
                    }
                }
            });
            this.mWifiDisplayConfig.removeParameter("initparams", "samsung_account");
            this.mWifiDisplayConfig.removeParameter("initparams", "tv_ble_irk");
            return;
        }
        if (parameter3 == null || TextUtils.isEmpty(parameter3.getValue()) || TextUtils.isEmpty(this.mLocalDeviceP2pMacAddress)) {
            connectP2p(wifiP2pDevice9, createConnectConfig);
            return;
        }
        String value = parameter3.getValue();
        String str2 = this.mConnectingDevice.deviceAddress;
        final int i2 = 1;
        final ?? r7 = new SemWifiP2pManager.ActionListener(this) { // from class: com.android.server.display.WifiDisplayController.13
            public final /* synthetic */ WifiDisplayController this$0;

            {
                this.this$0 = this;
            }

            public final void onFailure(int i22) {
                switch (i2) {
                    case 0:
                        Slog.i("WifiDisplayController", "setPreparedAccountPin with IRK, onFailure");
                        this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                        break;
                    default:
                        Slog.i("WifiDisplayController", "setPreparedAccountPin with SmartThings Device ID, onFailure");
                        this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                        break;
                }
            }

            public final void onSuccess() {
                switch (i2) {
                    case 0:
                        Slog.i("WifiDisplayController", "setPreparedAccountPin with IRK, onSuccess");
                        this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                        break;
                    default:
                        Slog.i("WifiDisplayController", "setPreparedAccountPin with SmartThings Device ID, onSuccess");
                        this.this$0.connectP2p(wifiP2pDevice9, createConnectConfig);
                        break;
                }
            }
        };
        StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("setPreparedAccountPin, deviceId : ", value, ", TV MacAddress : ", str2, " TV MacAddress byte array: ");
        m.append(Arrays.toString(macAddressStringToByteArray(str2)));
        Log.d("WifiDisplayController", m.toString());
        byte[] hexStringToByteArray2 = hexStringToByteArray(value);
        byte[] macAddressStringToByteArray = macAddressStringToByteArray(str2);
        try {
            Mac mac2 = Mac.getInstance("HmacSHA256");
            mac2.init(new SecretKeySpec(hexStringToByteArray2, "HmacSHA256"));
            bArr = mac2.doFinal(macAddressStringToByteArray);
        } catch (InvalidKeyException | NoSuchAlgorithmException e2) {
            Log.e("WifiDisplayController", "getEncryptionKeyByHmacSha256 Exception : " + e2);
        }
        Optional.ofNullable(bArr).ifPresent(new Consumer() { // from class: com.android.server.display.WifiDisplayController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                SemWifiP2pManager.ActionListener actionListener = r7;
                byte[] bArr2 = (byte[]) obj;
                wifiDisplayController.getClass();
                SecureRandom secureRandom = new SecureRandom();
                int[] iArr = new int[8];
                for (int i3 = 0; i3 < 8; i3++) {
                    iArr[i3] = secureRandom.nextInt(10);
                }
                byte[] bArr3 = new byte[16];
                wifiDisplayController.mSecureRandom.nextBytes(bArr3);
                Log.i("WifiDisplayController", "pinNumber = " + Arrays.toString(iArr).replaceAll("[^0-9]", "") + " , local mac address : " + wifiDisplayController.mLocalDeviceP2pMacAddress + ", " + Arrays.toString(WifiDisplayController.macAddressStringToByteArray(wifiDisplayController.mLocalDeviceP2pMacAddress)) + " , iv hex = " + WifiDisplayController.byteArrayToHexString(bArr3) + " , iv byte array = " + Arrays.toString(bArr3) + " , encryptionKey byte array: " + Arrays.toString(bArr2) + " , encryptionKey hex string: " + WifiDisplayController.byteArrayToHexString(bArr2));
                try {
                    byte[] encryptByAES256CBC = WifiDisplayController.encryptByAES256CBC(bArr2, WifiDisplayController.getEncryptData(iArr, WifiDisplayController.macAddressStringToByteArray(wifiDisplayController.mLocalDeviceP2pMacAddress)), bArr3);
                    Log.i("WifiDisplayController", "encryptedValue : " + Arrays.toString(encryptByAES256CBC) + " hex : " + WifiDisplayController.byteArrayToHexString(encryptByAES256CBC));
                    wifiDisplayController.mSemWifiP2pManager.setPreparedAccountPin(Arrays.toString(iArr).replaceAll("[^0-9]", ""), WifiDisplayController.byteArrayToHexString(encryptByAES256CBC), WifiDisplayController.byteArrayToHexString(bArr3), actionListener);
                } catch (Exception e3) {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e3, "setPreparedAccountPin failed : ", "WifiDisplayController");
                }
            }
        });
        this.mWifiDisplayConfig.removeParameter("initparams", "tv_device_id");
    }

    public final void updateScanState() {
        StringBuilder sb = new StringBuilder("updateScanState() mScanRequested = ");
        sb.append(this.mScanRequested);
        sb.append(", mWfdEnabled = ");
        sb.append(this.mWfdEnabled);
        sb.append(", mDiscoverPeersInProgress = ");
        RCPManagerService$$ExternalSyntheticOutline0.m("WifiDisplayController", sb, this.mDiscoverPeersInProgress);
        if (this.mWfdEnabled && this.mScanRequested && (this.mDesiredDevice == null || this.mRemoteDisplayConnected)) {
            if (this.mDiscoverPeersInProgress) {
                return;
            }
            FlashNotificationsController$$ExternalSyntheticOutline0.m("WifiDisplayController", new StringBuilder("Starting Wifi display scan, mRemoteDisplayConnected = "), this.mRemoteDisplayConnected);
            this.mDiscoverPeersInProgress = true;
            this.mHandler.post(new AnonymousClass6(this, 0));
            tryDiscoverPeers(this.mRequestedScanChannel, this.mRequestedScanInterval);
            return;
        }
        if (this.mDiscoverPeersInProgress) {
            this.mHandler.removeCallbacks(this.mDiscoverPeers);
            WifiP2pDevice wifiP2pDevice = this.mDesiredDevice;
            if (wifiP2pDevice == null || wifiP2pDevice == this.mConnectedDevice) {
                Slog.i("WifiDisplayController", "Stopping Wifi display scan.");
                this.mDiscoverPeersInProgress = false;
                this.mRequestedScanChannel = 1611;
                Log.d("WifiDisplayController", "stopPeerDiscovery");
                this.mWifiP2pManager.stopPeerDiscovery(this.mWifiP2pChannel, null);
                this.mHandler.post(new AnonymousClass6(this, 7));
            }
        }
    }

    public final void updateWfdEnableState() {
        StringBuilder sb = new StringBuilder("updateWfdEnableState:: mWifiDisplayOnSetting = ");
        sb.append(this.mWifiDisplayOnSetting);
        sb.append(" mWifiP2pEnabled = ");
        sb.append(this.mWifiP2pEnabled);
        sb.append(" mWfdEnabled = ");
        sb.append(this.mWfdEnabled);
        sb.append(" mWfdEnabling = ");
        RCPManagerService$$ExternalSyntheticOutline0.m("WifiDisplayController", sb, this.mWfdEnabling);
        int i = 1;
        if (!this.mWifiDisplayOnSetting) {
            if (this.mWfdEnabled || this.mWfdEnabling) {
                this.mSemWifiP2pManager.setScreenSharing(false);
                WifiP2pWfdInfo wifiP2pWfdInfo = new WifiP2pWfdInfo();
                wifiP2pWfdInfo.setEnabled(false);
                this.mWifiP2pManager.setWfdInfo(this.mWifiP2pChannel, wifiP2pWfdInfo, new AnonymousClass3());
            }
            this.mWfdEnabling = false;
            this.mWfdEnabled = false;
            if (this.mWifiP2pEnabled) {
                i = this.mWifiDisplayOnSetting ? 3 : 2;
            }
            this.mHandler.post(new AnonymousClass4(i));
            updateScanState();
            disconnect();
            return;
        }
        if (!this.mWifiP2pEnabled || this.mWfdEnabled || this.mWfdEnabling) {
            return;
        }
        this.mWfdEnabling = true;
        this.mSemWifiP2pManager.setScreenSharing(true);
        WifiP2pWfdInfo wifiP2pWfdInfo2 = new WifiP2pWfdInfo();
        wifiP2pWfdInfo2.setEnabled(true);
        wifiP2pWfdInfo2.setDeviceType(0);
        wifiP2pWfdInfo2.setSessionAvailable(true);
        wifiP2pWfdInfo2.setContentProtectionSupported(true);
        wifiP2pWfdInfo2.setControlPort(7236);
        wifiP2pWfdInfo2.setMaxThroughput(50);
        this.mWifiP2pManager.setWfdInfo(this.mWifiP2pChannel, wifiP2pWfdInfo2, new WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.2
            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public final void onFailure(int i2) {
                WifiDisplayController.this.mWfdEnabling = false;
            }

            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public final void onSuccess() {
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                if (wifiDisplayController.mWfdEnabling) {
                    wifiDisplayController.mWfdEnabling = false;
                    wifiDisplayController.mWfdEnabled = true;
                    wifiDisplayController.mHandler.post(wifiDisplayController.new AnonymousClass4(wifiDisplayController.mWifiP2pEnabled ? wifiDisplayController.mWifiDisplayOnSetting ? 3 : 2 : 1));
                    WifiDisplayController.this.updateScanState();
                }
            }
        });
    }
}
