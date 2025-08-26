package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.app.AlarmManager;
import android.content.Context;
import android.net.wifi.nl80211.IApInterfaceEventCallback;
import android.net.wifi.nl80211.IClientInterface;
import android.net.wifi.nl80211.IPnoScanEvent;
import android.net.wifi.nl80211.IScanEvent;
import android.net.wifi.nl80211.ISendMgmtFrameEvent;
import android.net.wifi.nl80211.IWificond;
import android.net.wifi.nl80211.IWificondEventCallback;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

@SystemApi
/* loaded from: classes3.dex */
public class WifiNl80211Manager {
    public static final String EXTRA_SCANNING_PARAM_VENDOR_IES = "android.net.wifi.nl80211.extra.SCANNING_PARAM_VENDOR_IES";
    private static final int PRESCAN_LISTENER_TYPE_ASYNC = 1;
    private static final int PRESCAN_LISTENER_TYPE_NONE = 0;
    private static final int PRESCAN_LISTENER_TYPE_SYNC = 2;
    public static final String SCANNING_PARAM_ENABLE_6GHZ_RNR = "android.net.wifi.nl80211.SCANNING_PARAM_ENABLE_6GHZ_RNR";
    public static final int SCAN_TYPE_PNO_SCAN = 1;
    public static final int SCAN_TYPE_SINGLE_SCAN = 0;
    public static final int SEND_MGMT_FRAME_ERROR_ALREADY_STARTED = 5;
    public static final int SEND_MGMT_FRAME_ERROR_MCS_UNSUPPORTED = 2;
    public static final int SEND_MGMT_FRAME_ERROR_NO_ACK = 3;
    public static final int SEND_MGMT_FRAME_ERROR_TIMEOUT = 4;
    public static final int SEND_MGMT_FRAME_ERROR_UNKNOWN = 1;
    private static final int SEND_MGMT_FRAME_TIMEOUT_MS = 1000;
    private static final String TAG = "WifiNl80211Manager";
    private static final String TIMEOUT_ALARM_TAG = "WifiNl80211Manager Send Management Frame Timeout";
    private AlarmManager mAlarmManager;
    private HashMap<String, IApInterfaceEventCallback> mApInterfaceListeners;
    private HashMap<String, IApInterface> mApInterfaces;
    private HashMap<String, IClientInterface> mClientInterfaces;
    private Runnable mDeathEventHandler;
    private Handler mEventHandler;
    private String mInterfaceNameForPreScan;
    private Object mLock;
    private HashMap<String, IPnoScanEvent> mPnoScanEventHandlers;
    private int mPreScanListenerType;
    private Executor mPreScanResultExecutor;
    private PreScanResultListener mPreScanResultListener;
    private HashMap<String, IScanEvent> mScanEventHandlers;
    private AtomicBoolean mSendMgmtFrameInProgress;
    private boolean mVerboseLoggingEnabled;
    private IWificond mWificond;
    private WificondEventHandler mWificondEventHandler;
    private HashMap<String, IWifiScannerImpl> mWificondScanners;

    public interface CountryCodeChangedListener {
        void onCountryCodeChanged(String str);
    }

    public interface PnoScanRequestCallback {
        void onPnoRequestFailed();

        void onPnoRequestSucceeded();
    }

    public interface PreScanResultListener {
        void onPreScanResult(List<NativeScanResult> list, int i);
    }

    public interface ScanEventCallback {
        void onScanFailed();

        default void onScanFailed(int i) {
        }

        void onScanResultReady();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanResultType {
    }

    public interface SendMgmtFrameCallback {
        void onAck(int i);

        void onFailure(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SendMgmtFrameError {
    }

    @Deprecated
    public interface SoftApCallback {
        void onConnectedClientsChanged(NativeWifiClient nativeWifiClient, boolean z);

        void onFailure();

        void onSoftApChannelSwitched(int i, int i2);
    }

    public static OemSecurityType parseOemSecurityTypeElement(int i, int i2, byte[] bArr) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toFrameworkScanStatusCode(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 2) {
            return -6;
        }
        if (i == 3) {
            return -7;
        }
        if (i != 4) {
            return i != 5 ? -1 : -9;
        }
        return -8;
    }

    public class WificondEventHandler extends IWificondEventCallback.Stub {
        private Map<CountryCodeChangedListener, Executor> mCountryCodeChangedListenerHolder = new HashMap();

        public WificondEventHandler(WifiNl80211Manager wifiNl80211Manager) {
        }

        public void registerCountryCodeChangedListener(Executor executor, CountryCodeChangedListener countryCodeChangedListener) {
            this.mCountryCodeChangedListenerHolder.put(countryCodeChangedListener, executor);
        }

        public void unregisterCountryCodeChangedListener(CountryCodeChangedListener countryCodeChangedListener) {
            this.mCountryCodeChangedListenerHolder.remove(countryCodeChangedListener);
        }

        @Override // android.net.wifi.nl80211.IWificondEventCallback
        public void OnRegDomainChanged(final String str) {
            Log.d(WifiNl80211Manager.TAG, "OnRegDomainChanged " + str);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mCountryCodeChangedListenerHolder.forEach(new BiConsumer() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$WificondEventHandler$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Executor executor = (Executor) obj2;
                        executor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$WificondEventHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                countryCodeChangedListener.onCountryCodeChanged(str);
                            }
                        });
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ScanEventHandler extends IScanEvent.Stub {
        private ScanEventCallback mCallback;
        private Executor mExecutor;

        ScanEventHandler(Executor executor, ScanEventCallback scanEventCallback) {
            this.mExecutor = executor;
            this.mCallback = scanEventCallback;
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanResultReady() {
            Log.d(WifiNl80211Manager.TAG, "Scan result ready event");
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (WifiNl80211Manager.this.mInterfaceNameForPreScan != null && WifiNl80211Manager.this.mPreScanResultListener != null) {
                    WifiNl80211Manager wifiNl80211Manager = WifiNl80211Manager.this;
                    final List<NativeScanResult> scanResults = wifiNl80211Manager.getScanResults(wifiNl80211Manager.mInterfaceNameForPreScan, 0);
                    if (WifiNl80211Manager.this.mPreScanListenerType == 1) {
                        if (WifiNl80211Manager.this.mPreScanResultExecutor != null) {
                            WifiNl80211Manager.this.mPreScanResultExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$OnScanResultReady$0(scanResults);
                                }
                            });
                        }
                    } else if (WifiNl80211Manager.this.mPreScanListenerType == 2) {
                        WifiNl80211Manager.this.mPreScanResultListener.onPreScanResult(scanResults, 0);
                    }
                }
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnScanResultReady$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanResultReady$0(List list) {
            WifiNl80211Manager.this.mPreScanResultListener.onPreScanResult(list, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanResultReady$1() {
            this.mCallback.onScanResultReady();
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanFailed() {
            Log.d(WifiNl80211Manager.TAG, "Scan failed event");
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnScanFailed$2();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanFailed$2() {
            this.mCallback.onScanFailed();
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanRequestFailed(final int i) {
            Log.d(WifiNl80211Manager.TAG, "Scan failed event with error code: " + i);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnScanRequestFailed$3(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanRequestFailed$3(int i) {
            this.mCallback.onScanFailed(WifiNl80211Manager.this.toFrameworkScanStatusCode(i));
        }
    }

    @Deprecated
    public static class SignalPollResult {
        public final int associationFrequencyMHz;
        public final int currentRssiDbm;
        public final int rxBitrateMbps;
        public final int txBitrateMbps;

        public SignalPollResult(int i, int i2, int i3, int i4) {
            this.currentRssiDbm = i;
            this.txBitrateMbps = i2;
            this.rxBitrateMbps = i3;
            this.associationFrequencyMHz = i4;
        }
    }

    public static class TxPacketCounters {
        public final int txPacketFailed;
        public final int txPacketSucceeded;

        public TxPacketCounters(int i, int i2) {
            this.txPacketSucceeded = i;
            this.txPacketFailed = i2;
        }
    }

    public WifiNl80211Manager(Context context) {
        this.mVerboseLoggingEnabled = false;
        this.mWificondEventHandler = new WificondEventHandler(this);
        this.mClientInterfaces = new HashMap<>();
        this.mApInterfaces = new HashMap<>();
        this.mWificondScanners = new HashMap<>();
        this.mScanEventHandlers = new HashMap<>();
        this.mPnoScanEventHandlers = new HashMap<>();
        this.mApInterfaceListeners = new HashMap<>();
        this.mLock = new Object();
        this.mPreScanListenerType = 0;
        this.mSendMgmtFrameInProgress = new AtomicBoolean(false);
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        this.mEventHandler = new Handler(context.getMainLooper());
    }

    public WifiNl80211Manager(Context context, IBinder iBinder) {
        this(context);
        IWificond iWificondAsInterface = IWificond.Stub.asInterface(iBinder);
        this.mWificond = iWificondAsInterface;
        if (iWificondAsInterface == null) {
            Log.e(TAG, "Failed to get reference to wificond");
        }
    }

    public WifiNl80211Manager(Context context, IWificond iWificond) {
        this(context);
        this.mWificond = iWificond;
    }

    public WificondEventHandler getWificondEventHandler() {
        return this.mWificondEventHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PnoScanEventHandler extends IPnoScanEvent.Stub {
        private ScanEventCallback mCallback;
        private Executor mExecutor;

        PnoScanEventHandler(Executor executor, ScanEventCallback scanEventCallback) {
            this.mExecutor = executor;
            this.mCallback = scanEventCallback;
        }

        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoNetworkFound() {
            Log.d(WifiNl80211Manager.TAG, "Pno scan result event");
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (WifiNl80211Manager.this.mInterfaceNameForPreScan != null && WifiNl80211Manager.this.mPreScanResultListener != null) {
                    WifiNl80211Manager wifiNl80211Manager = WifiNl80211Manager.this;
                    final List<NativeScanResult> scanResults = wifiNl80211Manager.getScanResults(wifiNl80211Manager.mInterfaceNameForPreScan, 1);
                    if (WifiNl80211Manager.this.mPreScanListenerType == 1) {
                        if (WifiNl80211Manager.this.mPreScanResultExecutor != null) {
                            WifiNl80211Manager.this.mPreScanResultExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$PnoScanEventHandler$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$OnPnoNetworkFound$0(scanResults);
                                }
                            });
                        }
                    } else if (WifiNl80211Manager.this.mPreScanListenerType == 2) {
                        WifiNl80211Manager.this.mPreScanResultListener.onPreScanResult(scanResults, 1);
                    }
                }
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$PnoScanEventHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnPnoNetworkFound$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnPnoNetworkFound$0(List list) {
            WifiNl80211Manager.this.mPreScanResultListener.onPreScanResult(list, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnPnoNetworkFound$1() {
            this.mCallback.onScanResultReady();
        }

        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoScanFailed() {
            Log.d(WifiNl80211Manager.TAG, "Pno Scan failed event");
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$PnoScanEventHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnPnoScanFailed$2();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnPnoScanFailed$2() {
            this.mCallback.onScanFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ApInterfaceEventCallback extends IApInterfaceEventCallback.Stub {
        private Executor mExecutor;
        private SoftApCallback mSoftApListener;

        private int toFrameworkBandwidth(int i) {
            switch (i) {
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                    return 6;
                case 7:
                    return 11;
                default:
                    return 0;
            }
        }

        ApInterfaceEventCallback(Executor executor, SoftApCallback softApCallback) {
            this.mExecutor = executor;
            this.mSoftApListener = softApCallback;
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onConnectedClientsChanged(final NativeWifiClient nativeWifiClient, final boolean z) {
            if (WifiNl80211Manager.this.mVerboseLoggingEnabled) {
                Log.d(WifiNl80211Manager.TAG, "onConnectedClientsChanged called with " + nativeWifiClient.getMacAddress() + " isConnected: " + z);
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ApInterfaceEventCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onConnectedClientsChanged$0(nativeWifiClient, z);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnectedClientsChanged$0(NativeWifiClient nativeWifiClient, boolean z) {
            this.mSoftApListener.onConnectedClientsChanged(nativeWifiClient, z);
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onSoftApChannelSwitched(final int i, final int i2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ApInterfaceEventCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSoftApChannelSwitched$1(i, i2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoftApChannelSwitched$1(int i, int i2) {
            this.mSoftApListener.onSoftApChannelSwitched(i, toFrameworkBandwidth(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SendMgmtFrameEvent extends ISendMgmtFrameEvent.Stub {
        private SendMgmtFrameCallback mCallback;
        private Executor mExecutor;
        private AlarmManager.OnAlarmListener mTimeoutCallback = new AlarmManager.OnAlarmListener() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda4
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.lambda$new$2();
            }
        };
        private boolean mWasCalled = false;

        private void runIfFirstCall(Runnable runnable) {
            if (this.mWasCalled) {
                return;
            }
            this.mWasCalled = true;
            WifiNl80211Manager.this.mSendMgmtFrameInProgress.set(false);
            runnable.run();
        }

        SendMgmtFrameEvent(Executor executor, SendMgmtFrameCallback sendMgmtFrameCallback) {
            this.mExecutor = executor;
            this.mCallback = sendMgmtFrameCallback;
            WifiNl80211Manager.this.mAlarmManager.set(2, SystemClock.elapsedRealtime() + 1000, WifiNl80211Manager.TIMEOUT_ALARM_TAG, this.mTimeoutCallback, WifiNl80211Manager.this.mEventHandler);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2() {
            runIfFirstCall(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            if (WifiNl80211Manager.this.mVerboseLoggingEnabled) {
                Log.e(WifiNl80211Manager.TAG, "Timed out waiting for ACK");
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$0();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            this.mCallback.onFailure(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnAck$5(final int i) {
            runIfFirstCall(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$OnAck$4(i);
                }
            });
        }

        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnAck(final int i) {
            WifiNl80211Manager.this.mEventHandler.post(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$OnAck$5(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnAck$4(final int i) {
            WifiNl80211Manager.this.mAlarmManager.cancel(this.mTimeoutCallback);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnAck$3(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnAck$3(int i) {
            this.mCallback.onAck(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnFailure$8(final int i) {
            runIfFirstCall(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$OnFailure$7(i);
                }
            });
        }

        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnFailure(final int i) {
            WifiNl80211Manager.this.mEventHandler.post(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$OnFailure$8(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnFailure$7(final int i) {
            WifiNl80211Manager.this.mAlarmManager.cancel(this.mTimeoutCallback);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$OnFailure$6(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnFailure$6(int i) {
            this.mCallback.onFailure(i);
        }
    }

    /* renamed from: binderDied, reason: merged with bridge method [inline-methods] */
    public void lambda$retrieveWificondAndRegisterForDeath$1() {
        this.mEventHandler.post(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$binderDied$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        synchronized (this.mLock) {
            Log.e(TAG, "Wificond died!");
            clearState();
            this.mWificond = null;
            Runnable runnable = this.mDeathEventHandler;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public void enableVerboseLogging(boolean z) {
        this.mVerboseLoggingEnabled = z;
    }

    public void setOnServiceDeadCallback(Runnable runnable) {
        if (this.mDeathEventHandler != null) {
            Log.e(TAG, "Death handler already present");
        }
        this.mDeathEventHandler = runnable;
    }

    private boolean retrieveWificondAndRegisterForDeath() {
        if (this.mWificond != null) {
            if (this.mVerboseLoggingEnabled) {
                Log.d(TAG, "Wificond handle already retrieved");
            }
            return true;
        }
        IWificond iWificondAsInterface = IWificond.Stub.asInterface(ServiceManager.getService(Context.WIFI_NL80211_SERVICE));
        this.mWificond = iWificondAsInterface;
        if (iWificondAsInterface == null) {
            Log.e(TAG, "Failed to get reference to wificond");
            return false;
        }
        try {
            iWificondAsInterface.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    this.f$0.lambda$retrieveWificondAndRegisterForDeath$1();
                }
            }, 0);
            this.mWificond.registerWificondEventCallback(this.mWificondEventHandler);
            return true;
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to register death notification for wificond");
            return false;
        }
    }

    public boolean semSetupInterface(String str) {
        Log.d(TAG, "Setting up interface for client mode. iface:" + str);
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        try {
            Iterator<IBinder> it = this.mWificond.GetClientInterfaces().iterator();
            while (it.hasNext()) {
                IClientInterface iClientInterfaceAsInterface = IClientInterface.Stub.asInterface(it.next());
                if (TextUtils.equals(str, iClientInterfaceAsInterface.getInterfaceName())) {
                    this.mClientInterfaces.put(str, iClientInterfaceAsInterface);
                    IWifiScannerImpl wifiScannerImpl = iClientInterfaceAsInterface.getWifiScannerImpl();
                    if (wifiScannerImpl == null) {
                        Log.e(TAG, "Failed to get IWifiScannerImpl");
                    } else {
                        this.mWificondScanners.put(str, wifiScannerImpl);
                    }
                }
            }
            return this.mClientInterfaces.size() > 0;
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to get IClientInterface due to remote exception");
            return false;
        }
    }

    public boolean setupInterfaceForClientMode(String str, Executor executor, ScanEventCallback scanEventCallback, ScanEventCallback scanEventCallback2) {
        Log.d(TAG, "Setting up interface for client mode: " + str);
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        if (scanEventCallback == null || scanEventCallback2 == null || executor == null) {
            Log.e(TAG, "setupInterfaceForClientMode invoked with null callbacks");
            return false;
        }
        try {
            IClientInterface iClientInterfaceCreateClientInterface = this.mWificond.createClientInterface(str);
            if (iClientInterfaceCreateClientInterface == null) {
                Log.e(TAG, "Could not get IClientInterface instance from wificond");
                return false;
            }
            Binder.allowBlocking(iClientInterfaceCreateClientInterface.asBinder());
            this.mClientInterfaces.put(str, iClientInterfaceCreateClientInterface);
            try {
                IWifiScannerImpl wifiScannerImpl = iClientInterfaceCreateClientInterface.getWifiScannerImpl();
                if (wifiScannerImpl == null) {
                    Log.e(TAG, "Failed to get WificondScannerImpl");
                    return false;
                }
                this.mWificondScanners.put(str, wifiScannerImpl);
                Binder.allowBlocking(wifiScannerImpl.asBinder());
                ScanEventHandler scanEventHandler = new ScanEventHandler(executor, scanEventCallback);
                this.mScanEventHandlers.put(str, scanEventHandler);
                wifiScannerImpl.subscribeScanEvents(scanEventHandler);
                PnoScanEventHandler pnoScanEventHandler = new PnoScanEventHandler(executor, scanEventCallback2);
                this.mPnoScanEventHandlers.put(str, pnoScanEventHandler);
                wifiScannerImpl.subscribePnoScanEvents(pnoScanEventHandler);
                return true;
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to refresh wificond scanner due to remote exception");
                return true;
            }
        } catch (RemoteException unused2) {
            Log.e(TAG, "Failed to get IClientInterface due to remote exception");
            return false;
        } catch (NullPointerException unused3) {
            Log.e(TAG, "setupInterfaceForClientMode NullPointerException");
            return false;
        }
    }

    public boolean tearDownClientInterface(String str) {
        if (getClientInterface(str) == null) {
            Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            return false;
        }
        try {
            IWifiScannerImpl iWifiScannerImpl = this.mWificondScanners.get(str);
            if (iWifiScannerImpl != null) {
                iWifiScannerImpl.unsubscribeScanEvents();
                iWifiScannerImpl.unsubscribePnoScanEvents();
            }
            IWificond iWificond = this.mWificond;
            if (iWificond == null) {
                Log.e(TAG, "tearDownClientInterface: mWificond binder is null! Did wificond die?");
                return false;
            }
            try {
                if (!iWificond.tearDownClientInterface(str)) {
                    Log.e(TAG, "Failed to teardown client interface");
                    return false;
                }
                this.mClientInterfaces.remove(str);
                this.mWificondScanners.remove(str);
                this.mScanEventHandlers.remove(str);
                this.mPnoScanEventHandlers.remove(str);
                return true;
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to teardown client interface due to remote exception");
                return false;
            } catch (NullPointerException unused2) {
                Log.e(TAG, "tearDownClientInterface NullPointerException");
                return false;
            }
        } catch (RemoteException unused3) {
            Log.e(TAG, "Failed to unsubscribe wificond scanner due to remote exception");
            return false;
        }
    }

    public boolean setupInterfaceForSoftApMode(String str) {
        Log.d(TAG, "Setting up interface for soft ap mode for iface=" + str);
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        try {
            IApInterface iApInterfaceCreateApInterface = this.mWificond.createApInterface(str);
            if (iApInterfaceCreateApInterface == null) {
                Log.e(TAG, "Could not get IApInterface instance from wificond");
                return false;
            }
            Binder.allowBlocking(iApInterfaceCreateApInterface.asBinder());
            this.mApInterfaces.put(str, iApInterfaceCreateApInterface);
            return true;
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to get IApInterface due to remote exception");
            return false;
        } catch (NullPointerException unused2) {
            Log.e(TAG, "setupInterfaceForSoftApMode NullPointerException");
            return false;
        }
    }

    public boolean tearDownSoftApInterface(String str) {
        if (getApInterface(str) == null) {
            Log.e(TAG, "No valid wificond ap interface handler for iface=" + str);
            return false;
        }
        IWificond iWificond = this.mWificond;
        if (iWificond == null) {
            Log.e(TAG, "tearDownSoftApInterface: mWificond binder is null! Did wificond die?");
            return false;
        }
        try {
            if (!iWificond.tearDownApInterface(str)) {
                Log.e(TAG, "Failed to teardown AP interface");
                return false;
            }
            this.mApInterfaces.remove(str);
            this.mApInterfaceListeners.remove(str);
            return true;
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to teardown AP interface due to remote exception");
            return false;
        } catch (NullPointerException unused2) {
            Log.e(TAG, "tearDownSoftApInterface NullPointerException");
            return false;
        }
    }

    public boolean tearDownInterfaces() {
        synchronized (this.mLock) {
            Log.d(TAG, "tearing down interfaces in wificond");
            if (!retrieveWificondAndRegisterForDeath()) {
                return false;
            }
            try {
                for (Map.Entry<String, IWifiScannerImpl> entry : this.mWificondScanners.entrySet()) {
                    entry.getValue().unsubscribeScanEvents();
                    entry.getValue().unsubscribePnoScanEvents();
                }
                this.mWificond.tearDownInterfaces();
                clearState();
                return true;
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to tear down interfaces due to remote exception");
                return false;
            }
        }
    }

    private IClientInterface getClientInterface(String str) {
        return this.mClientInterfaces.get(str);
    }

    @Deprecated
    public SignalPollResult signalPoll(String str) {
        IClientInterface clientInterface = getClientInterface(str);
        if (clientInterface == null) {
            Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            return null;
        }
        try {
            int[] iArrSignalPoll = clientInterface.signalPoll();
            if (iArrSignalPoll == null || iArrSignalPoll.length != 4) {
                Log.e(TAG, "Invalid signal poll result from wificond");
                return null;
            }
            return new SignalPollResult(iArrSignalPoll[0], iArrSignalPoll[1], iArrSignalPoll[3], iArrSignalPoll[2]);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to do signal polling due to remote exception");
            return null;
        }
    }

    public TxPacketCounters getTxPacketCounters(String str) {
        IClientInterface clientInterface = getClientInterface(str);
        if (clientInterface == null) {
            Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            return null;
        }
        try {
            int[] packetCounters = clientInterface.getPacketCounters();
            if (packetCounters == null || packetCounters.length != 2) {
                Log.e(TAG, "Invalid signal poll result from wificond");
                return null;
            }
            return new TxPacketCounters(packetCounters[0], packetCounters[1]);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to do signal polling due to remote exception");
            return null;
        }
    }

    private IWifiScannerImpl getScannerImpl(String str) {
        return this.mWificondScanners.get(str);
    }

    public List<NativeScanResult> getScanResults(String str, int i) {
        List<NativeScanResult> arrayList;
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl != null) {
            try {
                if (i == 0) {
                    arrayList = Arrays.asList(scannerImpl.getScanResults());
                } else {
                    arrayList = Arrays.asList(scannerImpl.getPnoScanResults());
                }
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to create ScanDetail ArrayList");
                arrayList = null;
            }
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (this.mVerboseLoggingEnabled) {
                Log.d(TAG, "get " + arrayList.size() + " scan results from wificond");
            }
            return arrayList;
        }
        Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
        return new ArrayList();
    }

    public int getMaxSsidsPerScan(String str) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return 0;
        }
        try {
            return scannerImpl.getMaxSsidsPerScan();
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to getMaxSsidsPerScan");
            return 0;
        }
    }

    private static int getScanType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        throw new IllegalArgumentException("Invalid scan type " + i);
    }

    @Deprecated
    public boolean startScan(String str, int i, Set<Integer> set, List<byte[]> list) {
        return startScan(str, i, set, list, null);
    }

    @Deprecated
    public boolean startScan(String str, int i, Set<Integer> set, List<byte[]> list, Bundle bundle) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return false;
        }
        SingleScanSettings singleScanSettingsCreateSingleScanSettings = createSingleScanSettings(i, set, list, bundle);
        if (singleScanSettingsCreateSingleScanSettings == null) {
            return false;
        }
        try {
            return scannerImpl.scan(singleScanSettingsCreateSingleScanSettings);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to request scan due to remote exception");
            return false;
        }
    }

    public int startScan2(String str, int i, Set<Integer> set, List<byte[]> list, Bundle bundle) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return -9;
        }
        SingleScanSettings singleScanSettingsCreateSingleScanSettings = createSingleScanSettings(i, set, list, bundle);
        if (singleScanSettingsCreateSingleScanSettings == null) {
            return -9;
        }
        try {
            return toFrameworkScanStatusCode(scannerImpl.scanRequest(singleScanSettingsCreateSingleScanSettings));
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to request scan due to remote exception");
            return -1;
        }
    }

    private SingleScanSettings createSingleScanSettings(int i, Set<Integer> set, List<byte[]> list, Bundle bundle) {
        SingleScanSettings singleScanSettings = new SingleScanSettings();
        try {
            singleScanSettings.scanType = getScanType(i);
            singleScanSettings.channelSettings = new ArrayList<>();
            singleScanSettings.hiddenNetworks = new ArrayList<>();
            if (bundle != null) {
                singleScanSettings.enable6GhzRnr = bundle.getBoolean(SCANNING_PARAM_ENABLE_6GHZ_RNR);
                singleScanSettings.vendorIes = bundle.getByteArray(EXTRA_SCANNING_PARAM_VENDOR_IES);
            }
            if (set != null) {
                for (Integer num : set) {
                    ChannelSettings channelSettings = new ChannelSettings();
                    channelSettings.frequency = num.intValue();
                    singleScanSettings.channelSettings.add(channelSettings);
                }
            }
            if (list != null) {
                for (byte[] bArr : list) {
                    HiddenNetwork hiddenNetwork = new HiddenNetwork();
                    hiddenNetwork.ssid = bArr;
                    if (!singleScanSettings.hiddenNetworks.contains(hiddenNetwork)) {
                        singleScanSettings.hiddenNetworks.add(hiddenNetwork);
                    }
                }
            }
            return singleScanSettings;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Invalid scan type ", e);
            return null;
        }
    }

    public boolean startPnoScan(String str, PnoSettings pnoSettings, Executor executor, final PnoScanRequestCallback pnoScanRequestCallback) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return false;
        }
        if (pnoScanRequestCallback == null || executor == null) {
            Log.e(TAG, "startPnoScan called with a null callback");
            return false;
        }
        try {
            boolean zStartPnoScan = scannerImpl.startPnoScan(pnoSettings);
            if (zStartPnoScan) {
                Objects.requireNonNull(pnoScanRequestCallback);
                executor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        pnoScanRequestCallback.onPnoRequestSucceeded();
                    }
                });
                return zStartPnoScan;
            }
            Objects.requireNonNull(pnoScanRequestCallback);
            executor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    pnoScanRequestCallback.onPnoRequestFailed();
                }
            });
            return zStartPnoScan;
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to start pno scan due to remote exception");
            return false;
        }
    }

    public boolean stopPnoScan(String str) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return false;
        }
        try {
            return scannerImpl.stopPnoScan();
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to stop pno scan due to remote exception");
            return false;
        }
    }

    public void abortScan(String str) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
        } else {
            try {
                scannerImpl.abortScan();
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to request abortScan due to remote exception");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int[] getChannelsMhzForBand(int i) {
        int[] available2gChannels;
        IWificond iWificond = this.mWificond;
        if (iWificond == null) {
            Log.e(TAG, "getChannelsMhzForBand: mWificond binder is null! Did wificond die?");
            return new int[0];
        }
        try {
            if (i == 1) {
                available2gChannels = iWificond.getAvailable2gChannels();
            } else if (i == 2) {
                available2gChannels = iWificond.getAvailable5gNonDFSChannels();
            } else if (i == 4) {
                available2gChannels = iWificond.getAvailableDFSChannels();
            } else if (i == 8) {
                available2gChannels = iWificond.getAvailable6gChannels();
            } else if (i == 16) {
                available2gChannels = iWificond.getAvailable60gChannels();
            } else {
                throw new IllegalArgumentException("unsupported band " + i);
            }
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to request getChannelsForBand due to remote exception");
            available2gChannels = null;
            if (available2gChannels == null) {
            }
        } catch (NullPointerException unused2) {
            Log.e(TAG, "getChannelsMhzForBand NullPointerException");
            available2gChannels = null;
            if (available2gChannels == null) {
            }
        }
        return available2gChannels == null ? new int[0] : available2gChannels;
    }

    private IApInterface getApInterface(String str) {
        return this.mApInterfaces.get(str);
    }

    public DeviceWiphyCapabilities getDeviceWiphyCapabilities(String str) {
        IWificond iWificond = this.mWificond;
        if (iWificond == null) {
            Log.e(TAG, "getDeviceWiphyCapabilities: mWificond binder is null! Did wificond die?");
            return null;
        }
        try {
            return iWificond.getDeviceWiphyCapabilities(str);
        } catch (RemoteException unused) {
            return null;
        } catch (NullPointerException unused2) {
            Log.e(TAG, "getDeviceWiphyCapabilities NullPointerException");
            return null;
        }
    }

    public boolean registerCountryCodeChangedListener(Executor executor, CountryCodeChangedListener countryCodeChangedListener) {
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        Log.d(TAG, "registerCountryCodeEventListener called");
        this.mWificondEventHandler.registerCountryCodeChangedListener(executor, countryCodeChangedListener);
        return true;
    }

    public void unregisterCountryCodeChangedListener(CountryCodeChangedListener countryCodeChangedListener) {
        Log.d(TAG, "unregisterCountryCodeEventListener called");
        this.mWificondEventHandler.unregisterCountryCodeChangedListener(countryCodeChangedListener);
    }

    public void notifyCountryCodeChanged(String str) {
        if (this.mWificond == null) {
            new RemoteException("Wificond service doesn't exist!").rethrowFromSystemServer();
        }
        try {
            this.mWificond.notifyCountryCodeChanged();
            Log.i(TAG, "Receive country code change to " + str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        } catch (NullPointerException unused) {
            new RemoteException("Wificond service doesn't exist!").rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean registerApCallback(String str, Executor executor, SoftApCallback softApCallback) {
        IApInterface apInterface = getApInterface(str);
        if (apInterface == null) {
            Log.e(TAG, "No valid ap interface handler for iface=" + str);
            return false;
        }
        if (softApCallback == null || executor == null) {
            Log.e(TAG, "registerApCallback called with a null callback");
            return false;
        }
        try {
            ApInterfaceEventCallback apInterfaceEventCallback = new ApInterfaceEventCallback(executor, softApCallback);
            this.mApInterfaceListeners.put(str, apInterfaceEventCallback);
            if (apInterface.registerCallback(apInterfaceEventCallback)) {
                return true;
            }
            Log.e(TAG, "Failed to register ap callback.");
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in registering AP callback: " + e);
            return false;
        }
    }

    public void sendMgmtFrame(String str, byte[] bArr, int i, Executor executor, final SendMgmtFrameCallback sendMgmtFrameCallback) {
        if (sendMgmtFrameCallback == null || executor == null) {
            Log.e(TAG, "callback cannot be null!");
            return;
        }
        if (bArr == null) {
            Log.e(TAG, "frame cannot be null!");
            executor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    sendMgmtFrameCallback.onFailure(1);
                }
            });
            return;
        }
        IClientInterface clientInterface = getClientInterface(str);
        if (clientInterface == null) {
            Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            executor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    sendMgmtFrameCallback.onFailure(1);
                }
            });
            return;
        }
        if (!this.mSendMgmtFrameInProgress.compareAndSet(false, true)) {
            Log.e(TAG, "An existing management frame transmission is in progress!");
            executor.execute(new Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    sendMgmtFrameCallback.onFailure(5);
                }
            });
            return;
        }
        SendMgmtFrameEvent sendMgmtFrameEvent = new SendMgmtFrameEvent(executor, sendMgmtFrameCallback);
        try {
            clientInterface.SendMgmtFrame(bArr, sendMgmtFrameEvent, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception while starting link probe: " + e);
            sendMgmtFrameEvent.OnFailure(1);
        }
    }

    private void clearState() {
        this.mClientInterfaces.clear();
        this.mWificondScanners.clear();
        this.mPnoScanEventHandlers.clear();
        this.mScanEventHandlers.clear();
        this.mApInterfaces.clear();
        this.mApInterfaceListeners.clear();
        this.mSendMgmtFrameInProgress.set(false);
    }

    public static class OemSecurityType {
        public final int groupCipher;
        public final List<Integer> keyManagement;
        public final List<Integer> pairwiseCipher;
        public final int protocol;

        public OemSecurityType(int i, List<Integer> list, List<Integer> list2, int i2) {
            this.protocol = i;
            this.keyManagement = list == null ? new ArrayList<>() : list;
            this.pairwiseCipher = list2 == null ? new ArrayList<>() : list2;
            this.groupCipher = i2;
        }
    }

    public void disableRandomMac(String str) {
        IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            Log.e(TAG, "No valid wificond scanner interface handler, disableRandomMac");
            return;
        }
        try {
            scannerImpl.disableRandomMac();
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to request disable random mac due to remote exception");
        }
    }

    public boolean registerPreScanResultListener(Executor executor, String str, PreScanResultListener preScanResultListener) {
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        if (this.mPreScanListenerType != 0) {
            Log.e(TAG, "registerPreScanResultListener - already registered");
            return false;
        }
        Log.i(TAG, "registerPreScanResultListener");
        this.mInterfaceNameForPreScan = str;
        this.mPreScanResultExecutor = executor;
        this.mPreScanResultListener = preScanResultListener;
        this.mPreScanListenerType = 1;
        return true;
    }

    public void unregisterPreScanResultListener(PreScanResultListener preScanResultListener) {
        if (this.mPreScanListenerType == 1) {
            Log.i(TAG, "unregisterPreScanResultListener");
            this.mInterfaceNameForPreScan = null;
            this.mPreScanResultExecutor = null;
            this.mPreScanResultListener = null;
            this.mPreScanListenerType = 0;
        }
    }

    public boolean registerSyncPreScanResultListener(String str, PreScanResultListener preScanResultListener) {
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        if (this.mPreScanListenerType != 0) {
            Log.e(TAG, "registerPreScanResultListener - already registered");
            return false;
        }
        Log.i(TAG, "registerSyncPreScanResultListener");
        this.mInterfaceNameForPreScan = str;
        this.mPreScanResultListener = preScanResultListener;
        this.mPreScanListenerType = 2;
        return true;
    }

    public void unregisterSyncPreScanResultListener(PreScanResultListener preScanResultListener) {
        if (this.mPreScanListenerType == 2) {
            Log.i(TAG, "unregisterSyncPreScanResultListener");
            this.mInterfaceNameForPreScan = null;
            this.mPreScanResultListener = null;
            this.mPreScanListenerType = 0;
        }
    }
}
