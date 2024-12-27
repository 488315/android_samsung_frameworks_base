package com.att.iqi.libs;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;

import com.att.iqi.IIQIBroker;
import com.att.iqi.IIQIService;
import com.att.iqi.IMetricQueryCallback;
import com.att.iqi.IMetricSourcingCallback;
import com.att.iqi.IProfileChangedCallback;
import com.att.iqi.IServiceStateChangeCallback;
import com.att.iqi.lib.Build;
import com.att.iqi.lib.Metric;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;

public class IQIServiceBroker extends SystemService implements IIQIServiceBrokerPub {
    private static final String CAN_CHANGE_UNLOCK_KEYCODE_PERMISSION =
            "com.att.iqi.permission.CAN_CHANGE_UNLOCK_KEYCODE";
    private static final String CHANGE_IQI_STATE_ACTION = "com.att.iqi.action.CHANGE_IQI_STATE";
    private static final String CHANGE_IQI_STATE_PERMISSION =
            "com.att.iqi.permission.CHANGE_IQI_STATE";
    private static final String IQI_DEBUG_ACTION = "com.att.iqi.action.ENABLE_IQI_DEBUG_LOG";
    private static final String IQI_DEBUG_EXTRA = "com.att.iqi.extra.IQI_DEBUG";
    private static final String IQI_STATE_EXTRA = "com.att.iqi.extra.IQI_STATE";
    private static final String IQI_UPLOAD_COMPLETE_ACTION = "com.att.iqi.action.UPLOAD_COMPLETE";
    private static final int MAX_RETRY_ATTEMPTS = 10;
    private static final int MSG_CHECK_VALID = 2;
    private static final int MSG_SIM_INVALID = 3;
    private static final int MSG_TIMED_OUT_WAITING_PACKAGE_UPLOAD = 4;
    private static final int MSG_TRY_CONNECTING = 1;
    private static final long PACKAGE_UPLOAD_WAIT_MS = 15000;
    private static final String RECEIVE_UPLOAD_NOTIFICATIONS_PERMISSION =
            "com.att.iqi.permission.RECEIVE_UPLOAD_NOTIFICATIONS";
    private static final long SERVICE_CONNECTION_WAIT_TIME_MS = 3000;
    private static final String TAG = "IQIServiceBroker";
    private static final String TOGGLE_DEBUG_STATE_PERMISSION =
            "com.att.iqi.permission.TOGGLE_DEBUG_STATE";
    private static final String VALIDSIG =
            "3082036b30820253a003020102020456485fc9300d06092a864886f70d01010b05003066310b3009060355040613025553310b3009060355040813024341311230100603550407130953756e6e7976616c65310d300b060355040a0c04415426543111300f060355040b13084269672044617461311430120603550403130b695120496e736967687473301e170d3137303831373232333133365a170d3432303831313232333133365a3066310b3009060355040613025553310b3009060355040813024341311230100603550407130953756e6e7976616c65310d300b060355040a0c04415426543111300f060355040b13084269672044617461311430120603550403130b695120496e73696768747330820122300d06092a864886f70d01010105000382010f003082010a0282010100a5a975410b93a85fda21c82c39f8e383254f81f13d56e496b084613243ff1f2e3f8707787ec47c24bc48c78ead30bbdeb75527d6c6525fe11bd5b214502e0f8a9dcc3722d18f51f03b54f53f3694fc08956df6c118cc1b5d627643027a317b86ce51ac6963266e60edd02d80d2f88dadbd785e019835f8850212325f90303d3f92df34bdc534220a84e1e5fb972b9cdd2031e98f7625cb1d4d48f7f388e1e757d6f1a1658a3ea5d14be131ac66780778878bc94b75493a70f9df1d0f3fddce55bed8af5b17d8a08e00a5575b75080a4b706bffe003bed28c31399f652abfb95f2cfb66fbd01648e2ab917c9442d0e1d02ad043c677ddaf8851d29e2ef755edd30203010001a321301f301d0603551d0e04160414eb81e16cd29453db8a35a27cea042b18e8261d9c300d06092a864886f70d01010b050003820101008443999475099358e466a683f4ee7adc0f2616496d127d263ca05fa327cbdda891502241165e7f43d8cfbe38ff9aa9a05af368712f6eef7959f6d7d83d93f2f7c0af45449edef2468fdade1e0968f2d5508f0e6fd09912436d5b9575b45534412c6b5b6633dbafdbf91c8ab0cfe71875e84d5cd2ccf1fef35e2eef2002b9ced555b806319fcc158579a8a5979e78f6fb3d83854141f7bdfcef8c9ce156d4d5d6aeec475b6c33ae197f6d6697b9f2543baca1af9e7ec1a13ab093b9cf9e1aa8f436ff04ede4a1f4e3ca6b3640f72c91f26d66368e86f9419dc0dcc630498dc387f98585d4bd04b8241249560860f5e0698590625945e37a55c4645d012dc50773";
    private static IQIServiceBroker sInstance;
    private boolean mApplicationEnabled;
    private final CellBroadcastObserver mCBObserver;
    private ServiceConnection mConnection;
    private final Context mContext;
    private BroadcastReceiver mDebugReceiver;
    private IIQIService mImplementingBinderInterface;
    private boolean mInitialServiceConnect;
    private boolean mLastServiceEnabled;
    private final Handler mMessageDispatcher;
    private final SparseArray mMetricQueryCallbackList;
    private final SparseArray mMetricSourcingCallbackList;
    private final RemoteCallbackList mProfileChangeCallbackList;
    private int mRetryCount;
    private boolean mServicePublished;
    public int mServiceState;
    private final ArraySet mServiceStateChangeList;
    public final IIQIService mServiceStubForFailure;
    public BroadcastReceiver mStateChangeReceiver;
    private final SubscriptionManager.OnSubscriptionsChangedListener mSubListener;
    private BroadcastReceiver mUserUnlockedReceiver;
    private static final String PACKAGE_NAME = "com.att.iqi";
    private static final ComponentName TARGET_IMPLEMENTATION_COMPONENT =
            new ComponentName(PACKAGE_NAME, "com.att.iqi.service.IQService");
    private static final IIQIServiceBrokerPub sDummyPublicBroker =
            new IIQIServiceBrokerPub() { // from class: com.att.iqi.libs.IQIServiceBroker.1
                @Override // com.att.iqi.libs.IIQIServiceBrokerPub
                public void postMessage(int i, Bundle bundle) {}
            };

    public class BinderService extends IIQIBroker.Stub {
        public BinderService() {}

        @Override // com.att.iqi.IIQIBroker
        public void disableService() throws RemoteException {
            try {
                final Message obtainMessage =
                        IQIServiceBroker.this.mMessageDispatcher.obtainMessage(4);
                IQIServiceBroker.this.mContext.registerReceiver(
                        new BroadcastReceiver() { // from class:
                                                  // com.att.iqi.libs.IQIServiceBroker.BinderService.1
                            @Override // android.content.BroadcastReceiver
                            public void onReceive(Context context, Intent intent) {
                                IQIServiceBroker.this.mMessageDispatcher.removeMessages(
                                        obtainMessage.what);
                                IQIServiceBroker.this.disablePackage();
                                context.unregisterReceiver(this);
                            }
                        },
                        new IntentFilter(IQIServiceBroker.IQI_UPLOAD_COMPLETE_ACTION),
                        IQIServiceBroker.RECEIVE_UPLOAD_NOTIFICATIONS_PERMISSION,
                        null,
                        2);
                IQIServiceBroker.this.mMessageDispatcher.sendMessageDelayed(
                        obtainMessage, IQIServiceBroker.PACKAGE_UPLOAD_WAIT_MS);
                IQIServiceBroker.this.getBrokeredService().disableService();
            } catch (Exception e) {
                LogUtil.logd("disableService failed", e);
            }
        }

        @Override // android.os.Binder
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(
                            IQIServiceBroker.this.getContext(), IQIServiceBroker.TAG, printWriter)
                    && LogUtil.canLog()
                    && !ArrayUtils.isEmpty(strArr)) {
                for (String str : strArr) {
                    if ("-h".equals(str)) {
                        IQIServiceBroker.dumpHelp(printWriter);
                    } else if ("--concierge".equals(str)) {
                        IQIServiceBroker.printConciergeVersion(printWriter);
                    } else if (TextUtils.equals("--aar", str)) {
                        IQIServiceBroker.printAARVersion(printWriter);
                    } else {
                        if (!"-v".equals(str)) {
                            if (str.length() <= 0 || str.charAt(0) != '-') {
                                return;
                            }
                            printWriter.println("Unknown option: ".concat(str));
                            return;
                        }
                        IQIServiceBroker.printArtifactsVersion(printWriter);
                    }
                }
            }
        }

        public void finalize() {
            LogUtil.logd("Finalize BinderService");
        }

        @Override // com.att.iqi.IIQIBroker
        public void forceStopService() throws RemoteException {
            try {
                IQIServiceBroker.this.getBrokeredService().forceStopService();
                IQIServiceBroker.this.mMessageDispatcher.sendMessageDelayed(
                        IQIServiceBroker.this.mMessageDispatcher.obtainMessage(1), 4000L);
            } catch (Exception e) {
                LogUtil.logd("forceStopService failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public long getTimestamp() throws RemoteException {
            try {
                return IQIServiceBroker.this.getBrokeredService().getTimestamp();
            } catch (Exception e) {
                LogUtil.logd("getTimestamp failed", e);
                return -1L;
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void registerMetricQueryCallback(
                Metric.ID id, IMetricQueryCallback iMetricQueryCallback) throws RemoteException {
            boolean z;
            try {
                synchronized (IQIServiceBroker.this.mMetricQueryCallbackList) {
                    try {
                        RemoteCallbackList remoteCallbackList =
                                (RemoteCallbackList)
                                        IQIServiceBroker.this.mMetricQueryCallbackList.get(
                                                id.asInt());
                        if (remoteCallbackList == null) {
                            remoteCallbackList = new RemoteCallbackList();
                            IQIServiceBroker.this.mMetricQueryCallbackList.append(
                                    id.asInt(), remoteCallbackList);
                        }
                        int registeredCallbackCount =
                                remoteCallbackList.getRegisteredCallbackCount();
                        remoteCallbackList.register(iMetricQueryCallback);
                        z = true;
                        if (remoteCallbackList.getRegisteredCallbackCount()
                                != registeredCallbackCount + 1) {
                            z = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    IQIServiceBroker.this
                            .getBrokeredService()
                            .registerMetricQueryCallback(id, iMetricQueryCallback);
                }
            } catch (Exception e) {
                LogUtil.logd("registerMetricQueryCallback failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void registerMetricSourcingCallback(
                Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback)
                throws RemoteException {
            boolean z;
            try {
                synchronized (IQIServiceBroker.this.mMetricSourcingCallbackList) {
                    try {
                        RemoteCallbackList remoteCallbackList =
                                (RemoteCallbackList)
                                        IQIServiceBroker.this.mMetricSourcingCallbackList.get(
                                                id.asInt());
                        if (remoteCallbackList == null) {
                            remoteCallbackList = new RemoteCallbackList();
                            IQIServiceBroker.this.mMetricSourcingCallbackList.append(
                                    id.asInt(), remoteCallbackList);
                        }
                        int registeredCallbackCount =
                                remoteCallbackList.getRegisteredCallbackCount();
                        remoteCallbackList.register(iMetricSourcingCallback);
                        z = true;
                        if (remoteCallbackList.getRegisteredCallbackCount()
                                != registeredCallbackCount + 1) {
                            z = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    IQIServiceBroker.this
                            .getBrokeredService()
                            .registerMetricSourcingCallback(id, iMetricSourcingCallback);
                }
            } catch (Exception e) {
                LogUtil.logd("registerMetricSourcingCallback failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void registerProfileChangedCallback(IProfileChangedCallback iProfileChangedCallback)
                throws RemoteException {
            boolean z;
            try {
                synchronized (IQIServiceBroker.this.mProfileChangeCallbackList) {
                    int registeredCallbackCount =
                            IQIServiceBroker.this.mProfileChangeCallbackList
                                    .getRegisteredCallbackCount();
                    IQIServiceBroker.this.mProfileChangeCallbackList.register(
                            iProfileChangedCallback);
                    z = true;
                    if (IQIServiceBroker.this.mProfileChangeCallbackList
                                    .getRegisteredCallbackCount()
                            != registeredCallbackCount + 1) {
                        z = false;
                    }
                }
                if (z) {
                    IQIServiceBroker.this
                            .getBrokeredService()
                            .registerProfileChangedCallback(iProfileChangedCallback);
                }
            } catch (Exception e) {
                LogUtil.logd("registerProfileChangedCallback failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void registerServiceChangedCallback(
                IServiceStateChangeCallback iServiceStateChangeCallback) throws RemoteException {
            boolean z;
            synchronized (IQIServiceBroker.this.mServiceStateChangeList) {
                try {
                    z =
                            IQIServiceBroker.this.mServiceStateChangeList.add(
                                    iServiceStateChangeCallback);
                } catch (Exception e) {
                    LogUtil.logd("registerServiceChangedCallback failed", e);
                    z = false;
                }
            }
            if (z) {
                LogUtil.logd("Call service changed callback now");
                try {
                    iServiceStateChangeCallback.onServiceChange(
                            IQIServiceBroker.this.mImplementingBinderInterface != null);
                } catch (Exception e2) {
                    LogUtil.logd("notifyServiceState failed", e2);
                }
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public boolean setUnlockCode(long j) throws RemoteException {
            if (IQIServiceBroker.this.mContext.checkCallingOrSelfPermission(
                            IQIServiceBroker.CAN_CHANGE_UNLOCK_KEYCODE_PERMISSION)
                    != 0) {
                throw new RemoteException("Calling process not allowed to change unlock code");
            }
            try {
                return IQIServiceBroker.this.getBrokeredService().setUnlockCode(j);
            } catch (RemoteException e) {
                LogUtil.logd("setUnlockCode failed", e);
                return false;
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public boolean shouldSubmitMetric(Metric.ID id) throws RemoteException {
            try {
                return IQIServiceBroker.this.getBrokeredService().shouldSubmitMetric(id);
            } catch (Exception e) {
                LogUtil.logd("shouldSubmitMetric failed", e);
                return false;
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void submitMetric(Metric metric) throws RemoteException {
            try {
                IQIServiceBroker.this.getBrokeredService().submitMetric(metric);
            } catch (Exception e) {
                LogUtil.logd("submitMetric failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void unregisterMetricQueryCallback(
                Metric.ID id, IMetricQueryCallback iMetricQueryCallback) throws RemoteException {
            try {
                IQIServiceBroker.this
                        .getBrokeredService()
                        .unregisterMetricQueryCallback(id, iMetricQueryCallback);
                synchronized (IQIServiceBroker.this.mMetricQueryCallbackList) {
                    try {
                        RemoteCallbackList remoteCallbackList =
                                (RemoteCallbackList)
                                        IQIServiceBroker.this.mMetricQueryCallbackList.get(
                                                id.asInt());
                        if (remoteCallbackList != null) {
                            remoteCallbackList.unregister(iMetricQueryCallback);
                        }
                    } finally {
                    }
                }
            } catch (Exception e) {
                LogUtil.logd("unregisterMetricQueryCallback failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void unregisterMetricSourcingCallback(
                Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback)
                throws RemoteException {
            try {
                IQIServiceBroker.this
                        .getBrokeredService()
                        .unregisterMetricSourcingCallback(id, iMetricSourcingCallback);
                synchronized (IQIServiceBroker.this.mMetricSourcingCallbackList) {
                    try {
                        RemoteCallbackList remoteCallbackList =
                                (RemoteCallbackList)
                                        IQIServiceBroker.this.mMetricSourcingCallbackList.get(
                                                id.asInt());
                        if (remoteCallbackList != null) {
                            remoteCallbackList.unregister(iMetricSourcingCallback);
                        }
                    } finally {
                    }
                }
            } catch (Exception e) {
                LogUtil.logd("unregisterMetricSourcingCallback failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void unregisterProfileChangedCallback(
                IProfileChangedCallback iProfileChangedCallback) throws RemoteException {
            try {
                IQIServiceBroker.this
                        .getBrokeredService()
                        .unregisterProfileChangedCallback(iProfileChangedCallback);
                synchronized (IQIServiceBroker.this.mProfileChangeCallbackList) {
                    IQIServiceBroker.this.mProfileChangeCallbackList.unregister(
                            iProfileChangedCallback);
                }
            } catch (Exception e) {
                LogUtil.logd("unregisterProfileChangedCallback failed", e);
            }
        }

        @Override // com.att.iqi.IIQIBroker
        public void unregisterServiceChangedCallback(
                IServiceStateChangeCallback iServiceStateChangeCallback) throws RemoteException {
            synchronized (IQIServiceBroker.this.mServiceStateChangeList) {
                IQIServiceBroker.this.mServiceStateChangeList.remove(iServiceStateChangeCallback);
            }
        }
    }

    public IQIServiceBroker(Context context) {
        super(context);
        this.mRetryCount = 0;
        this.mServiceState = 1;
        this.mMetricQueryCallbackList = new SparseArray();
        this.mProfileChangeCallbackList = new RemoteCallbackList();
        this.mMetricSourcingCallbackList = new SparseArray();
        this.mServiceStateChangeList = new ArraySet();
        this.mStateChangeReceiver =
                new BroadcastReceiver() { // from class: com.att.iqi.libs.IQIServiceBroker.2
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        boolean booleanExtra =
                                intent.getBooleanExtra(IQIServiceBroker.IQI_STATE_EXTRA, false);
                        if (booleanExtra) {
                            IQIServiceBroker iQIServiceBroker = IQIServiceBroker.this;
                            if (iQIServiceBroker.mServiceState == 0) {
                                iQIServiceBroker.mServiceState = 1;
                                PreferenceStore.getInstance()
                                        .setInteger(
                                                PreferenceStore.PREF_SERVICE_STATE,
                                                IQIServiceBroker.this.mServiceState);
                                IQIServiceBroker.this.getBrokeredService();
                                IQIServiceBroker.this.registerReceivers(false);
                                return;
                            }
                        }
                        if (booleanExtra) {
                            return;
                        }
                        IQIServiceBroker iQIServiceBroker2 = IQIServiceBroker.this;
                        if (iQIServiceBroker2.mServiceState == 1) {
                            iQIServiceBroker2.mServiceState = 0;
                            PreferenceStore.getInstance()
                                    .setInteger(
                                            PreferenceStore.PREF_SERVICE_STATE,
                                            IQIServiceBroker.this.mServiceState);
                            IQIServiceBroker.this.tryDisconnecting();
                            IQIServiceBroker.this.unregisterReceivers();
                        }
                    }
                };
        this.mUserUnlockedReceiver =
                new BroadcastReceiver() { // from class: com.att.iqi.libs.IQIServiceBroker.3
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        LogUtil.logd("User unlocked.");
                        IQIServiceBroker.this.mMessageDispatcher.sendMessage(
                                IQIServiceBroker.this.mMessageDispatcher.obtainMessage(2));
                    }
                };
        this.mDebugReceiver =
                new BroadcastReceiver() { // from class: com.att.iqi.libs.IQIServiceBroker.4
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        boolean booleanExtra =
                                intent.getBooleanExtra(IQIServiceBroker.IQI_DEBUG_EXTRA, false);
                        if (booleanExtra != LogUtil.canLog()) {
                            PreferenceStore.getInstance()
                                    .setBoolean(PreferenceStore.PREF_LOGS_ENABLED, booleanExtra);
                            LogUtil.enableLogging(booleanExtra);
                        }
                    }
                };
        this.mMessageDispatcher =
                new Handler(
                        WorkerThread.getHandler()
                                .getLooper()) { // from class: com.att.iqi.libs.IQIServiceBroker.5
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        LogUtil.logd("Message received by dispatcher: " + message.what);
                        int i = message.what;
                        if (i == 1) {
                            if (IQIServiceBroker.this.shouldConnect()) {
                                if (!(message.obj instanceof Bundle)) {
                                    message.obj = new Bundle();
                                    LogUtil.logw("Trying to connect with empty bundle");
                                }
                                IQIServiceBroker.this.tryConnecting((Bundle) message.obj);
                                return;
                            }
                            return;
                        }
                        if (i == 2) {
                            if (IQIServiceBroker.this.checkPkgValidity()
                                    && IQIServiceBroker.this.appEnabled()) {
                                IQIServiceBroker.this.mMessageDispatcher.sendMessage(
                                        IQIServiceBroker.this.mMessageDispatcher.obtainMessage(1));
                                return;
                            } else {
                                LogUtil.loge("Bad or disabled package");
                                return;
                            }
                        }
                        if (i == 3) {
                            IQIServiceBroker.this.tryDisconnecting();
                        } else if (i != 4) {
                            LogUtil.loge("Unknown message");
                        } else {
                            LogUtil.logw("Timed out waiting for packages to be uploaded");
                            IQIServiceBroker.this.disablePackage();
                        }
                    }
                };
        this.mServiceStubForFailure = new IIQIService.Default();
        this.mConnection =
                new ServiceConnection() { // from class: com.att.iqi.libs.IQIServiceBroker.6
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        LogUtil.logw(
                                "Implementation service connected "
                                        + componentName.flattenToString()
                                        + " binder service "
                                        + iBinder.toString());
                        synchronized (IQIServiceBroker.this) {
                            IQIServiceBroker iQIServiceBroker = IQIServiceBroker.this;
                            iQIServiceBroker.mImplementingBinderInterface =
                                    iQIServiceBroker.getIBinderAsIInterface(iBinder);
                            IQIServiceBroker.this.notifyServiceState(true);
                            IQIServiceBroker.this.notifyAll();
                            try {
                                iBinder.linkToDeath(
                                        new IBinder
                                                .DeathRecipient() { // from class:
                                                                    // com.att.iqi.libs.IQIServiceBroker.6.1
                                            @Override // android.os.IBinder.DeathRecipient
                                            public void binderDied() {
                                                synchronized (IQIServiceBroker.this) {
                                                    LogUtil.logw("binderDied");
                                                    IQIServiceBroker.this
                                                                    .mImplementingBinderInterface =
                                                            null;
                                                }
                                            }
                                        },
                                        0);
                            } catch (RemoteException e) {
                                LogUtil.loge("Exception while linking IBinder to death", e);
                            }
                            IQIServiceBroker.this.mRetryCount = 0;
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName componentName) {
                        LogUtil.logw("Implementation service unexpectedly disconnected");
                        synchronized (IQIServiceBroker.this) {
                            IQIServiceBroker.this.notifyServiceState(false);
                            IQIServiceBroker.this.mImplementingBinderInterface = null;
                            IQIServiceBroker.this.notifyAll();
                            IQIServiceBroker.this.mRetryCount = 0;
                        }
                    }
                };
        this.mSubListener =
                new SubscriptionManager
                        .OnSubscriptionsChangedListener() { // from class:
                                                            // com.att.iqi.libs.IQIServiceBroker.8
                    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                    public void onSubscriptionsChanged() {
                        SubscriptionManager subscriptionManager =
                                (SubscriptionManager)
                                        IQIServiceBroker.this.mContext.getSystemService(
                                                SubscriptionManager.class);
                        IQIServiceBroker.this.mMessageDispatcher.sendMessage(
                                IQIServiceBroker.this.mMessageDispatcher.obtainMessage(
                                        (subscriptionManager == null
                                                        || !IQIConcierge.updateSubscriptions(
                                                                subscriptionManager))
                                                ? 3
                                                : 2));
                    }
                };
        sInstance = this;
        this.mContext = context;
        PreferenceStore preferenceStore = PreferenceStore.getInstance();
        LogUtil.enableLogging(preferenceStore.getBoolean(PreferenceStore.PREF_LOGS_ENABLED, false));
        this.mServiceState = preferenceStore.getInteger(PreferenceStore.PREF_SERVICE_STATE, 1);
        this.mCBObserver = new CellBroadcastObserver(context, WorkerThread.getHandler());
        registerPackageMonitor();
        registerReceivers(true);
    }

    public boolean appEnabled() {
        ApplicationInfo applicationInfo;
        String packageName = getServiceComponent().getPackageName();
        synchronized (this) {
            try {
                try {
                    PackageManager packageManager = this.mContext.getPackageManager();
                    if (packageManager != null
                            && (applicationInfo = packageManager.getApplicationInfo(packageName, 0))
                                    != null) {
                        boolean z = applicationInfo.enabled;
                        this.mApplicationEnabled = z;
                        LogUtil.logd("Application ".concat(z ? "enabled" : "disabled"));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    this.mApplicationEnabled = false;
                    LogUtil.loge(
                            "cannot find application information for "
                                    + packageName
                                    + ": "
                                    + e.toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mApplicationEnabled;
    }

    private void checkRetryAttemptsLocked(int i, String str) {
        int i2 = this.mRetryCount;
        if (i2 >= 10) {
            LogUtil.logd("Retried several times already, give up");
            return;
        }
        this.mRetryCount = i2 + 1;
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ", attempt # ");
        m.append(this.mRetryCount);
        LogUtil.logd(m.toString());
        Handler handler = this.mMessageDispatcher;
        handler.sendMessageDelayed(handler.obtainMessage(i), 3000L);
    }

    public void disablePackage() {
        this.mContext.getPackageManager().setApplicationEnabledSetting(PACKAGE_NAME, 3, 0);
        LogUtil.logd("package has been disabled");
    }

    public static void dumpHelp(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "IQIServiceBroker dump options:",
                "    -h: print this help",
                "    --concierge: print IQIConcierge version",
                "    --aar: print AAR version");
        printWriter.println("    -v: print the version of all artifacts");
    }

    public IIQIService getIBinderAsIInterface(IBinder iBinder) {
        return IIQIService.Stub.asInterface(iBinder);
    }

    public static IIQIServiceBrokerPub getPublicInterface() {
        IQIServiceBroker iQIServiceBroker = sInstance;
        return iQIServiceBroker == null ? sDummyPublicBroker : iQIServiceBroker;
    }

    private ComponentName getServiceComponent() {
        return TARGET_IMPLEMENTATION_COMPONENT;
    }

    public void notifyServiceState(boolean z) {
        LogUtil.logd("notifyServiceState = " + z);
        if (z != this.mLastServiceEnabled) {
            if (z && this.mInitialServiceConnect) {
                restoreCallbacks();
            }
            if (z) {
                this.mInitialServiceConnect = true;
            }
            this.mLastServiceEnabled = z;
        }
        synchronized (this.mServiceStateChangeList) {
            Iterator it = this.mServiceStateChangeList.iterator();
            while (it.hasNext()) {
                try {
                    ((IServiceStateChangeCallback) it.next()).onServiceChange(z);
                } catch (Exception e) {
                    LogUtil.logd("notifyServiceState failed", e);
                }
            }
        }
    }

    public static void printAARVersion(PrintWriter printWriter) {
        printWriter.println("AAR version: " + Build.getSdkVersion());
    }

    public static void printArtifactsVersion(PrintWriter printWriter) {
        printConciergeVersion(printWriter);
        printAARVersion(printWriter);
    }

    public static void printConciergeVersion(PrintWriter printWriter) {
        printWriter.println("IQIConcierge version: " + IQIConcierge.getVersion());
    }

    private void registerPackageMonitor() {
        new PackageMonitor() { // from class: com.att.iqi.libs.IQIServiceBroker.7
            public void onPackageModified(String str) {
                if (TextUtils.equals(
                                str,
                                IQIServiceBroker.TARGET_IMPLEMENTATION_COMPONENT.getPackageName())
                        && IQIServiceBroker.this.appEnabled()) {
                    LogUtil.logd("Package modified, try to reconnect...");
                    IQIServiceBroker.this.mMessageDispatcher.sendMessageDelayed(
                            IQIServiceBroker.this.mMessageDispatcher.obtainMessage(1), 3000L);
                }
            }
        }.register(this.mContext, BackgroundThread.getHandler().getLooper(), true);
    }

    public void registerReceivers(boolean z) {
        if (z) {
            this.mContext.registerReceiver(
                    this.mStateChangeReceiver,
                    new IntentFilter(CHANGE_IQI_STATE_ACTION),
                    CHANGE_IQI_STATE_PERMISSION,
                    null,
                    2);
        }
        this.mContext.registerReceiver(
                this.mDebugReceiver,
                new IntentFilter(IQI_DEBUG_ACTION),
                TOGGLE_DEBUG_STATE_PERMISSION,
                null,
                2);
        this.mContext.registerReceiver(
                this.mUserUnlockedReceiver,
                new IntentFilter("android.intent.action.USER_UNLOCKED"));
        this.mCBObserver.register();
    }

    private void restoreCallbacks() {
        try {
            synchronized (this.mMetricQueryCallbackList) {
                try {
                    int size = this.mMetricQueryCallbackList.size();
                    for (int i = 0; i < size; i++) {
                        int beginBroadcast =
                                ((RemoteCallbackList) this.mMetricQueryCallbackList.valueAt(i))
                                        .beginBroadcast();
                        for (int i2 = 0; i2 < beginBroadcast; i2++) {
                            getBrokeredService()
                                    .registerMetricQueryCallback(
                                            new Metric.ID(this.mMetricQueryCallbackList.keyAt(i)),
                                            (IMetricQueryCallback)
                                                    ((RemoteCallbackList)
                                                                    this.mMetricQueryCallbackList
                                                                            .valueAt(i))
                                                            .getBroadcastItem(i2));
                        }
                        ((RemoteCallbackList) this.mMetricQueryCallbackList.valueAt(i))
                                .finishBroadcast();
                    }
                } finally {
                }
            }
            synchronized (this.mProfileChangeCallbackList) {
                try {
                    int beginBroadcast2 = this.mProfileChangeCallbackList.beginBroadcast();
                    for (int i3 = 0; i3 < beginBroadcast2; i3++) {
                        getBrokeredService()
                                .registerProfileChangedCallback(
                                        (IProfileChangedCallback)
                                                this.mProfileChangeCallbackList.getBroadcastItem(
                                                        i3));
                    }
                    this.mProfileChangeCallbackList.finishBroadcast();
                } finally {
                }
            }
            synchronized (this.mMetricSourcingCallbackList) {
                try {
                    int size2 = this.mMetricSourcingCallbackList.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        int beginBroadcast3 =
                                ((RemoteCallbackList) this.mMetricSourcingCallbackList.valueAt(i4))
                                        .beginBroadcast();
                        for (int i5 = 0; i5 < beginBroadcast3; i5++) {
                            getBrokeredService()
                                    .registerMetricSourcingCallback(
                                            new Metric.ID(
                                                    this.mMetricSourcingCallbackList.keyAt(i4)),
                                            (IMetricSourcingCallback)
                                                    ((RemoteCallbackList)
                                                                    this.mMetricSourcingCallbackList
                                                                            .valueAt(i4))
                                                            .getBroadcastItem(i5));
                        }
                        ((RemoteCallbackList) this.mMetricSourcingCallbackList.valueAt(i4))
                                .finishBroadcast();
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            LogUtil.logd("Remote exception in restoreCallbacks", e);
        }
    }

    public void tryConnecting(Bundle bundle) {
        LogUtil.logw("Connecting to implementation");
        synchronized (this) {
            try {
                if (this.mImplementingBinderInterface != null) {
                    LogUtil.logd("Already connected");
                    return;
                }
                Intent intent = new Intent();
                ComponentName serviceComponent = getServiceComponent();
                if (serviceComponent == null) {
                    LogUtil.loge("No implementation service found");
                    return;
                }
                intent.setComponent(serviceComponent);
                intent.putExtras(bundle);
                IQIConcierge.loadBridgeLibrary(this.mContext, serviceComponent.getPackageName());
                try {
                    if (!this.mContext.bindServiceAsUser(
                            intent, this.mConnection, 1, UserHandle.CURRENT)) {
                        LogUtil.loge("Failed to bind to implementation " + serviceComponent);
                        checkRetryAttemptsLocked(1, "Retry reconnecting");
                    }
                } catch (SecurityException e) {
                    LogUtil.loge("Forbidden to bind to implementation " + serviceComponent, e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterReceivers() {
        this.mContext.unregisterReceiver(this.mDebugReceiver);
        this.mContext.unregisterReceiver(this.mUserUnlockedReceiver);
        this.mCBObserver.unregister();
    }

    public boolean checkPkgValidity() {
        SigningInfo signingInfo;
        String packageName = getServiceComponent().getPackageName();
        synchronized (this) {
            try {
                try {
                    PackageManager packageManager = this.mContext.getPackageManager();
                    if (packageManager != null
                            && (signingInfo =
                                            packageManager.getPackageInfo(packageName, 134217728)
                                                    .signingInfo)
                                    != null) {
                        Signature[] signingCertificateHistory =
                                signingInfo.getSigningCertificateHistory();
                        int length = signingCertificateHistory.length;
                        int i = 0;
                        boolean z = false;
                        while (i < length) {
                            if (!signingCertificateHistory[i].toCharsString().equals(VALIDSIG)) {
                                LogUtil.loge("Bad signature");
                                return false;
                            }
                            LogUtil.logd("Valid signature");
                            i++;
                            z = true;
                        }
                        return z;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    LogUtil.loge(
                            "cannot find package information for "
                                    + packageName
                                    + ": "
                                    + e.toString());
                    checkRetryAttemptsLocked(2, "Retry check package");
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public IIQIService getBrokeredService() {
        synchronized (this) {
            try {
                IIQIService iIQIService = this.mImplementingBinderInterface;
                if (iIQIService != null) {
                    return iIQIService;
                }
                if (!shouldConnect()) {
                    return this.mServiceStubForFailure;
                }
                Handler handler = this.mMessageDispatcher;
                handler.sendMessage(handler.obtainMessage(2));
                long elapsedRealtime = SystemClock.elapsedRealtime() + 3000;
                for (long j = 3000; j > 0; j = elapsedRealtime - SystemClock.elapsedRealtime()) {
                    try {
                        wait(j);
                    } catch (InterruptedException e) {
                        LogUtil.logw("Connection wait interrupted", e);
                    }
                    IIQIService iIQIService2 = this.mImplementingBinderInterface;
                    if (iIQIService2 != null) {
                        return iIQIService2;
                    }
                }
                LogUtil.logw("Timed out waiting for service connection, returning stub");
                return this.mServiceStubForFailure;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 600) {
            LogUtil.logd("Third party apps ready");
            Handler handler = this.mMessageDispatcher;
            handler.sendMessage(handler.obtainMessage(2));
            IQIConcierge.init(this.mContext);
            SubscriptionManager subscriptionManager =
                    (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
            if (subscriptionManager != null) {
                subscriptionManager.addOnSubscriptionsChangedListener(this.mSubListener);
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("iqi", new BinderService());
        this.mServicePublished = true;
    }

    @Override // com.att.iqi.libs.IIQIServiceBrokerPub
    public void postMessage(int i, Bundle bundle) {
        if (bundle == null) {
            this.mMessageDispatcher.sendEmptyMessage(i);
        } else {
            Handler handler = this.mMessageDispatcher;
            handler.sendMessage(handler.obtainMessage(i, bundle));
        }
    }

    public boolean shouldConnect() {
        boolean z;
        synchronized (this) {
            try {
                z = true;
                if (this.mServiceState != 1
                        || !this.mServicePublished
                        || !this.mApplicationEnabled
                        || !IQIConcierge.isServiceBindingAllowed()) {
                    z = false;
                }
            } finally {
            }
        }
        return z;
    }

    public final void tryDisconnecting() {
        LogUtil.logw("Disconnecting from implementation");
        synchronized (this) {
            try {
                if (this.mImplementingBinderInterface == null) {
                    LogUtil.logd("Already disconnected");
                    return;
                }
                this.mContext.unbindService(this.mConnection);
                notifyServiceState(false);
                this.mImplementingBinderInterface = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
