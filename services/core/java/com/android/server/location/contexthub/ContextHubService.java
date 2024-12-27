package com.android.server.location.contexthub;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.chre.flags.Flags;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.SensorPrivacyManagerInternal;
import android.hardware.contexthub.MessageDeliveryStatus;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.ContextHubMessage;
import android.hardware.location.ContextHubTransaction;
import android.hardware.location.IContextHubCallback;
import android.hardware.location.IContextHubClient;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.IContextHubService;
import android.hardware.location.IContextHubTransactionCallback;
import android.hardware.location.NanoApp;
import android.hardware.location.NanoAppBinary;
import android.hardware.location.NanoAppFilter;
import android.hardware.location.NanoAppInstanceInfo;
import android.hardware.location.NanoAppMessage;
import android.hardware.location.NanoAppState;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.location.contexthub.ContextHubClientManager;
import com.android.server.location.contexthub.ContextHubEventLogger;
import com.android.server.location.contexthub.ContextHubTransactionManager;
import com.android.server.location.contexthub.ContextHubTransactionManager.AnonymousClass1;
import com.android.server.location.contexthub.ContextHubTransactionManager.AnonymousClass6;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContextHubService extends IContextHubService.Stub {
    public final ContextHubClientManager mClientManager;
    public final Context mContext;
    public final Map mContextHubIdToInfoMap;
    public final List mContextHubInfoList;
    public final IContextHubWrapper mContextHubWrapper;
    public final Map mDefaultClientMap;
    public final SensorPrivacyManagerInternal mSensorPrivacyManagerInternal;
    public final List mSupportedContextHubPerms;
    public final ContextHubTransactionManager mTransactionManager;
    public final UserManager mUserManager;
    public final RemoteCallbackList mCallbacksList = new RemoteCallbackList();
    public final NanoAppStateManager mNanoAppStateManager = new NanoAppStateManager();
    public final ScheduledThreadPoolExecutor mDailyMetricTimer = new ScheduledThreadPoolExecutor(1);
    public final PriorityQueue mReliableMessageRecordQueue = new PriorityQueue(new ContextHubService$$ExternalSyntheticLambda1());
    public final TestModeManager mTestModeManager = new TestModeManager();
    public boolean mIsWifiAvailable = false;
    public boolean mIsWifiScanningEnabled = false;
    public boolean mIsWifiMainEnabled = false;
    public boolean mIsBtScanningEnabled = false;
    public boolean mIsBtMainEnabled = false;
    public final AtomicBoolean mIsTestModeEnabled = new AtomicBoolean(false);
    public final Set mMetricQueryPendingContextHubIds = Collections.newSetFromMap(new ConcurrentHashMap());
    public final Object mSendWifiSettingUpdateLock = new Object();
    public final Map mLastRestartTimestampMap = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.location.contexthub.ContextHubService$9, reason: invalid class name */
    public final class AnonymousClass9 extends IContextHubTransactionCallback.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ContextHubService this$0;
        public final /* synthetic */ int val$contextHubId;

        public /* synthetic */ AnonymousClass9(ContextHubService contextHubService, int i, int i2) {
            this.$r8$classId = i2;
            this.this$0 = contextHubService;
            this.val$contextHubId = i;
        }

        private final void onQueryResponse$com$android$server$location$contexthub$ContextHubService$9(int i, List list) {
        }

        private final void onTransactionComplete$com$android$server$location$contexthub$ContextHubService$10(int i) {
        }

        public final void onQueryResponse(int i, List list) {
            switch (this.$r8$classId) {
                case 0:
                    break;
                default:
                    this.this$0.onMessageReceiptOldApi(5, this.val$contextHubId, -1, new byte[]{(byte) i});
                    break;
            }
        }

        public final void onTransactionComplete(int i) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.onMessageReceiptOldApi(4, this.val$contextHubId, -1, new byte[]{(byte) i});
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContextHubServiceCallback {
        public final int mContextHubId;

        public ContextHubServiceCallback(int i) {
            this.mContextHubId = i;
        }

        public final void handleContextHubEvent(int i) {
            ContextHubService contextHubService = ContextHubService.this;
            int i2 = this.mContextHubId;
            contextHubService.getClass();
            if (i != 1) {
                Log.i("ContextHubService", DualAppManagerService$$ExternalSyntheticOutline0.m(i2, i, "Received unknown hub event (hub ID = ", ", type = ", ")"));
                return;
            }
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            long millis = TimeUnit.NANOSECONDS.toMillis(elapsedRealtimeNanos - ((AtomicLong) ((HashMap) contextHubService.mLastRestartTimestampMap).get(Integer.valueOf(i2))).getAndSet(elapsedRealtimeNanos));
            StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
            newBuilder.setAtomId(399);
            newBuilder.writeLong(millis);
            newBuilder.writeInt(i2);
            newBuilder.usePooledBuffer();
            StatsLog.write(newBuilder.build());
            ContextHubEventLogger contextHubEventLogger = ContextHubEventLogger.getInstance();
            synchronized (contextHubEventLogger) {
                ContextHubEventLogger.ContextHubRestartEvent contextHubRestartEvent = new ContextHubEventLogger.ContextHubRestartEvent(System.currentTimeMillis(), i2);
                if (!contextHubEventLogger.mContextHubRestartEventQueue.add(contextHubRestartEvent)) {
                    Log.e("ContextHubEventLogger", "Unable to add Context Hub restart event to queue: " + contextHubRestartEvent);
                }
            }
            contextHubService.mIsTestModeEnabled.set(false);
            contextHubService.sendLocationSettingUpdate();
            contextHubService.sendWifiSettingUpdate(true);
            contextHubService.sendAirplaneModeSettingUpdate();
            contextHubService.sendMicrophoneDisableSettingUpdateForCurrentUser();
            contextHubService.sendBtSettingUpdate(true);
            ContextHubTransactionManager contextHubTransactionManager = contextHubService.mTransactionManager;
            synchronized (contextHubTransactionManager) {
                if (((ContextHubServiceTransaction) contextHubTransactionManager.mTransactionQueue.peek()) != null) {
                    contextHubTransactionManager.removeTransactionAndStartNext();
                }
            }
            contextHubService.queryNanoAppsInternal(i2);
            ContextHubClientManager contextHubClientManager = contextHubService.mClientManager;
            contextHubClientManager.getClass();
            contextHubClientManager.forEachClientOfHub(i2, new ContextHubClientManager$$ExternalSyntheticLambda0());
        }

        public final void handleNanoappInfo(List list) {
            ContextHubService contextHubService = ContextHubService.this;
            int i = this.mContextHubId;
            if (contextHubService.mMetricQueryPendingContextHubIds.contains(Integer.valueOf(i))) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    NanoAppState nanoAppState = (NanoAppState) it.next();
                    long nanoAppId = nanoAppState.getNanoAppId();
                    int nanoAppVersion = (int) nanoAppState.getNanoAppVersion();
                    StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                    newBuilder.setAtomId(400);
                    newBuilder.writeInt(i);
                    newBuilder.writeLong(nanoAppId);
                    newBuilder.writeInt(nanoAppVersion);
                    newBuilder.usePooledBuffer();
                    StatsLog.write(newBuilder.build());
                }
                contextHubService.mMetricQueryPendingContextHubIds.remove(Integer.valueOf(i));
                if (contextHubService.mMetricQueryPendingContextHubIds.isEmpty()) {
                    contextHubService.scheduleDailyMetricSnapshot();
                }
            }
            NanoAppStateManager nanoAppStateManager = contextHubService.mNanoAppStateManager;
            synchronized (nanoAppStateManager) {
                try {
                    HashSet hashSet = new HashSet();
                    Iterator it2 = ((ArrayList) list).iterator();
                    while (it2.hasNext()) {
                        NanoAppState nanoAppState2 = (NanoAppState) it2.next();
                        nanoAppStateManager.handleQueryAppEntry(i, (int) nanoAppState2.getNanoAppVersion(), nanoAppState2.getNanoAppId());
                        hashSet.add(Long.valueOf(nanoAppState2.getNanoAppId()));
                    }
                    Iterator it3 = nanoAppStateManager.mNanoAppHash.values().iterator();
                    while (it3.hasNext()) {
                        NanoAppInstanceInfo nanoAppInstanceInfo = (NanoAppInstanceInfo) it3.next();
                        if (nanoAppInstanceInfo.getContexthubId() == i && !hashSet.contains(Long.valueOf(nanoAppInstanceInfo.getAppId()))) {
                            it3.remove();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            ContextHubTransactionManager contextHubTransactionManager = contextHubService.mTransactionManager;
            synchronized (contextHubTransactionManager) {
                ContextHubServiceTransaction contextHubServiceTransaction = (ContextHubServiceTransaction) contextHubTransactionManager.mTransactionQueue.peek();
                if (contextHubServiceTransaction == null) {
                    Log.w("ContextHubTransactionManager", "Received unexpected query response (no transaction pending)");
                    return;
                }
                if (contextHubServiceTransaction.mTransactionType == 4) {
                    contextHubServiceTransaction.onQueryResponse(0, list);
                    contextHubTransactionManager.removeTransactionAndStartNext();
                } else {
                    Log.w("ContextHubTransactionManager", "Received unexpected query response (expected " + contextHubServiceTransaction + ")");
                }
            }
        }

        public final void handleNanoappMessage(short s, NanoAppMessage nanoAppMessage, List list, List list2) {
            if (Flags.reliableMessageImplementation() && Flags.reliableMessageTestModeBehavior()) {
                ContextHubService contextHubService = ContextHubService.this;
                if (contextHubService.mIsTestModeEnabled.get()) {
                    TestModeManager testModeManager = contextHubService.mTestModeManager;
                    testModeManager.getClass();
                    if (nanoAppMessage.isReliable() && Flags.reliableMessageDuplicateDetectionService() && testModeManager.mRandom.nextInt(100) < 50) {
                        Log.i("ContextHubService", "[TEST MODE] Duplicating message (3 sends) with message sequence number: " + nanoAppMessage.getMessageSequenceNumber());
                        for (int i = 0; i < 3; i++) {
                            ContextHubService.m639$$Nest$mhandleClientMessageCallback(ContextHubService.this, this.mContextHubId, s, nanoAppMessage, list, list2);
                        }
                        return;
                    }
                }
            }
            ContextHubService.m639$$Nest$mhandleClientMessageCallback(ContextHubService.this, this.mContextHubId, s, nanoAppMessage, list, list2);
        }

        public final void handleTransactionResult(int i, boolean z) {
            ContextHubTransactionManager contextHubTransactionManager = ContextHubService.this.mTransactionManager;
            synchronized (contextHubTransactionManager) {
                ContextHubServiceTransaction contextHubServiceTransaction = (ContextHubServiceTransaction) contextHubTransactionManager.mTransactionQueue.peek();
                if (contextHubServiceTransaction == null) {
                    Log.w("ContextHubTransactionManager", "Received unexpected transaction response (no transaction pending)");
                    return;
                }
                if (contextHubServiceTransaction.mTransactionId == i) {
                    contextHubServiceTransaction.onTransactionComplete(z ? 0 : 5);
                    contextHubTransactionManager.removeTransactionAndStartNext();
                    return;
                }
                Log.w("ContextHubTransactionManager", "Received unexpected transaction response (expected ID = " + contextHubServiceTransaction.mTransactionId + ", received ID = " + i + ")");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReliableMessageRecord {
        public int mContextHubId;
        public byte mErrorCode;
        public int mMessageSequenceNumber;
        public long mTimestamp;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TestModeManager {
        public final Random mRandom = new Random();

        public TestModeManager() {
        }
    }

    /* renamed from: -$$Nest$mhandleClientMessageCallback, reason: not valid java name */
    public static void m639$$Nest$mhandleClientMessageCallback(ContextHubService contextHubService, int i, short s, NanoAppMessage nanoAppMessage, List list, List list2) {
        Optional empty;
        byte onMessageFromNanoApp;
        contextHubService.getClass();
        if (!Flags.reliableMessageImplementation() || !Flags.reliableMessageDuplicateDetectionService()) {
            byte onMessageFromNanoApp2 = contextHubService.mClientManager.onMessageFromNanoApp(i, s, nanoAppMessage, list, list2);
            if (!nanoAppMessage.isReliable() || onMessageFromNanoApp2 == 0) {
                return;
            }
            contextHubService.sendMessageDeliveryStatusToContextHub(i, nanoAppMessage.getMessageSequenceNumber(), onMessageFromNanoApp2);
            return;
        }
        if (!nanoAppMessage.isReliable()) {
            contextHubService.mClientManager.onMessageFromNanoApp(i, s, nanoAppMessage, list, list2);
            contextHubService.cleanupReliableMessageRecordQueue();
            return;
        }
        synchronized (contextHubService.mReliableMessageRecordQueue) {
            try {
                int messageSequenceNumber = nanoAppMessage.getMessageSequenceNumber();
                Iterator it = contextHubService.mReliableMessageRecordQueue.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        empty = Optional.empty();
                        break;
                    }
                    ReliableMessageRecord reliableMessageRecord = (ReliableMessageRecord) it.next();
                    if (reliableMessageRecord.mContextHubId == i && reliableMessageRecord.mMessageSequenceNumber == messageSequenceNumber) {
                        empty = Optional.of(reliableMessageRecord);
                        break;
                    }
                }
                if (empty.isPresent()) {
                    onMessageFromNanoApp = ((ReliableMessageRecord) empty.get()).mErrorCode;
                    if (onMessageFromNanoApp == 1) {
                        Log.w("ContextHubService", "Found duplicate reliable message with message sequence number: " + ((ReliableMessageRecord) empty.get()).mMessageSequenceNumber + ": retrying");
                        onMessageFromNanoApp = contextHubService.mClientManager.onMessageFromNanoApp(i, s, nanoAppMessage, list, list2);
                        ((ReliableMessageRecord) empty.get()).mErrorCode = onMessageFromNanoApp;
                    } else {
                        Log.w("ContextHubService", "Found duplicate reliable message with message sequence number: " + ((ReliableMessageRecord) empty.get()).mMessageSequenceNumber);
                    }
                } else {
                    onMessageFromNanoApp = contextHubService.mClientManager.onMessageFromNanoApp(i, s, nanoAppMessage, list, list2);
                    PriorityQueue priorityQueue = contextHubService.mReliableMessageRecordQueue;
                    long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
                    int messageSequenceNumber2 = nanoAppMessage.getMessageSequenceNumber();
                    ReliableMessageRecord reliableMessageRecord2 = new ReliableMessageRecord();
                    reliableMessageRecord2.mContextHubId = i;
                    reliableMessageRecord2.mTimestamp = elapsedRealtimeNanos;
                    reliableMessageRecord2.mMessageSequenceNumber = messageSequenceNumber2;
                    reliableMessageRecord2.mErrorCode = onMessageFromNanoApp;
                    priorityQueue.add(reliableMessageRecord2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        contextHubService.sendMessageDeliveryStatusToContextHub(i, nanoAppMessage.getMessageSequenceNumber(), onMessageFromNanoApp);
        contextHubService.cleanupReliableMessageRecordQueue();
    }

    public ContextHubService(Context context, IContextHubWrapper iContextHubWrapper) {
        Pair pair;
        final int i = 0;
        final int i2 = 1;
        this.mUserManager = null;
        Log.i("ContextHubService", "Starting Context Hub Service init");
        this.mContext = context;
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        this.mContextHubWrapper = iContextHubWrapper;
        if (iContextHubWrapper == null) {
            this.mTransactionManager = null;
            this.mClientManager = null;
            this.mSensorPrivacyManagerInternal = null;
            this.mDefaultClientMap = Collections.emptyMap();
            this.mContextHubIdToInfoMap = Collections.emptyMap();
            this.mSupportedContextHubPerms = Collections.emptyList();
            this.mContextHubInfoList = Collections.emptyList();
            Log.e("ContextHubService", "Failed to initialize the Context Hub Service");
            return;
        }
        try {
            pair = iContextHubWrapper.getHubs();
        } catch (RemoteException e) {
            Log.e("ContextHubService", "RemoteException while getting Context Hub info", e);
            pair = new Pair(Collections.emptyList(), Collections.emptyList());
        }
        long elapsedRealtimeNanos2 = SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos;
        int size = ((List) pair.first).size();
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(398);
        newBuilder.writeLong(elapsedRealtimeNanos2);
        newBuilder.writeInt(size);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
        List<ContextHubInfo> list = (List) pair.first;
        DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
        HashMap hashMap = new HashMap();
        for (ContextHubInfo contextHubInfo : list) {
            hashMap.put(Integer.valueOf(contextHubInfo.getId()), contextHubInfo);
        }
        this.mContextHubIdToInfoMap = Collections.unmodifiableMap(hashMap);
        this.mSupportedContextHubPerms = (List) pair.second;
        this.mContextHubInfoList = new ArrayList(this.mContextHubIdToInfoMap.values());
        ContextHubClientManager contextHubClientManager = new ContextHubClientManager(this.mContext, this.mContextHubWrapper);
        this.mClientManager = contextHubClientManager;
        this.mTransactionManager = new ContextHubTransactionManager(this.mContextHubWrapper, contextHubClientManager, this.mNanoAppStateManager);
        this.mSensorPrivacyManagerInternal = (SensorPrivacyManagerInternal) LocalServices.getService(SensorPrivacyManagerInternal.class);
        HashMap hashMap2 = new HashMap();
        for (Map.Entry entry : this.mContextHubIdToInfoMap.entrySet()) {
            Integer num = (Integer) entry.getKey();
            final int intValue = num.intValue();
            ContextHubInfo contextHubInfo2 = (ContextHubInfo) entry.getValue();
            ((HashMap) this.mLastRestartTimestampMap).put(num, new AtomicLong(SystemClock.elapsedRealtimeNanos()));
            try {
                this.mContextHubWrapper.registerCallback(intValue, new ContextHubServiceCallback(intValue));
            } catch (RemoteException e2) {
                Log.e("ContextHubService", "RemoteException while registering service callback for hub (ID = " + intValue + ")", e2);
            }
            hashMap2.put(num, this.mClientManager.registerClient(contextHubInfo2, new IContextHubClientCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubService.1
                public final void finishCallback() {
                    try {
                        ((IContextHubClient) ContextHubService.this.mDefaultClientMap.get(Integer.valueOf(intValue))).callbackFinished();
                    } catch (RemoteException e3) {
                        Log.e("ContextHubService", "RemoteException while finishing callback for hub (ID = " + intValue + ")", e3);
                    }
                }

                public final void onClientAuthorizationChanged(long j, int i3) {
                    finishCallback();
                }

                public final void onHubReset() {
                    ContextHubService.this.onMessageReceiptOldApi(7, intValue, -1, new byte[]{0});
                    finishCallback();
                }

                public final void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) {
                    ContextHubService.this.onMessageReceiptOldApi(nanoAppMessage.getMessageType(), intValue, ContextHubService.this.mNanoAppStateManager.getNanoAppHandle(intValue, nanoAppMessage.getNanoAppId()), nanoAppMessage.getMessageBody());
                    finishCallback();
                }

                public final void onNanoAppAborted(long j, int i3) {
                    finishCallback();
                }

                public final void onNanoAppDisabled(long j) {
                    finishCallback();
                }

                public final void onNanoAppEnabled(long j) {
                    finishCallback();
                }

                public final void onNanoAppLoaded(long j) {
                    finishCallback();
                }

                public final void onNanoAppUnloaded(long j) {
                    finishCallback();
                }
            }, null, this.mTransactionManager, this.mContext.getPackageName()));
            queryNanoAppsInternal(intValue);
        }
        this.mDefaultClientMap = Collections.unmodifiableMap(hashMap2);
        IContextHubWrapper iContextHubWrapper2 = this.mContextHubWrapper;
        if (iContextHubWrapper2 != null && iContextHubWrapper2.supportsLocationSettingNotifications()) {
            sendLocationSettingUpdate();
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("location_mode"), true, new ContentObserver(this) { // from class: com.android.server.location.contexthub.ContextHubService.2
                public final /* synthetic */ ContextHubService this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(null);
                    this.this$0 = this;
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    switch (i) {
                        case 0:
                            this.this$0.sendLocationSettingUpdate();
                            break;
                        case 1:
                            this.this$0.sendWifiSettingUpdate(false);
                            break;
                        case 2:
                            this.this$0.sendAirplaneModeSettingUpdate();
                            break;
                        default:
                            this.this$0.sendBtSettingUpdate(false);
                            break;
                    }
                }
            }, -1);
        }
        IContextHubWrapper iContextHubWrapper3 = this.mContextHubWrapper;
        if (iContextHubWrapper3 != null && iContextHubWrapper3.supportsWifiSettingNotifications()) {
            sendWifiSettingUpdate(true);
            this.mContext.registerReceiver(new BroadcastReceiver(this) { // from class: com.android.server.location.contexthub.ContextHubService.3
                public final /* synthetic */ ContextHubService this$0;

                {
                    this.this$0 = this;
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    switch (i) {
                        case 0:
                            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction()) || "android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED".equals(intent.getAction())) {
                                this.this$0.sendWifiSettingUpdate(false);
                                break;
                            }
                            break;
                        default:
                            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                                this.this$0.sendBtSettingUpdate(false);
                                break;
                            }
                            break;
                    }
                }
            }, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.net.wifi.WIFI_STATE_CHANGED", "android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED"));
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("wifi_scan_always_enabled"), true, new ContentObserver(this) { // from class: com.android.server.location.contexthub.ContextHubService.2
                public final /* synthetic */ ContextHubService this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(null);
                    this.this$0 = this;
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    switch (i2) {
                        case 0:
                            this.this$0.sendLocationSettingUpdate();
                            break;
                        case 1:
                            this.this$0.sendWifiSettingUpdate(false);
                            break;
                        case 2:
                            this.this$0.sendAirplaneModeSettingUpdate();
                            break;
                        default:
                            this.this$0.sendBtSettingUpdate(false);
                            break;
                    }
                }
            }, -1);
        }
        IContextHubWrapper iContextHubWrapper4 = this.mContextHubWrapper;
        if (iContextHubWrapper4 != null && iContextHubWrapper4.supportsAirplaneModeSettingNotifications()) {
            sendAirplaneModeSettingUpdate();
            final int i3 = 2;
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("airplane_mode_on"), true, new ContentObserver(this) { // from class: com.android.server.location.contexthub.ContextHubService.2
                public final /* synthetic */ ContextHubService this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(null);
                    this.this$0 = this;
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    switch (i3) {
                        case 0:
                            this.this$0.sendLocationSettingUpdate();
                            break;
                        case 1:
                            this.this$0.sendWifiSettingUpdate(false);
                            break;
                        case 2:
                            this.this$0.sendAirplaneModeSettingUpdate();
                            break;
                        default:
                            this.this$0.sendBtSettingUpdate(false);
                            break;
                    }
                }
            }, -1);
        }
        IContextHubWrapper iContextHubWrapper5 = this.mContextHubWrapper;
        if (iContextHubWrapper5 != null && iContextHubWrapper5.supportsMicrophoneSettingNotifications()) {
            if (this.mUserManager == null) {
                UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
                this.mUserManager = userManager;
                if (userManager == null) {
                    Log.e("ContextHubService", "Unable to get the UserManager service");
                }
            }
            sendMicrophoneDisableSettingUpdateForCurrentUser();
            SensorPrivacyManagerInternal sensorPrivacyManagerInternal = this.mSensorPrivacyManagerInternal;
            if (sensorPrivacyManagerInternal == null) {
                Log.e("ContextHubService", "Unable to add a sensor privacy listener for all users");
            } else {
                sensorPrivacyManagerInternal.addSensorPrivacyListenerForAllUsers(1, new SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda6
                    public final void onSensorPrivacyChanged(int i4, boolean z) {
                        ContextHubService contextHubService = ContextHubService.this;
                        contextHubService.getClass();
                        if (UserManager.isHeadlessSystemUserMode() || i4 == ContextHubService.getCurrentUserId()) {
                            Log.d("ContextHubService", "User: " + i4 + " mic privacy: " + z);
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m("Mic Disabled Setting: ", "ContextHubService", z);
                            contextHubService.mContextHubWrapper.onMicrophoneSettingChanged(z ^ true);
                        }
                    }
                });
            }
        }
        IContextHubWrapper iContextHubWrapper6 = this.mContextHubWrapper;
        if (iContextHubWrapper6 != null && iContextHubWrapper6.supportsBtSettingNotifications()) {
            sendBtSettingUpdate(true);
            this.mContext.registerReceiver(new BroadcastReceiver(this) { // from class: com.android.server.location.contexthub.ContextHubService.3
                public final /* synthetic */ ContextHubService this$0;

                {
                    this.this$0 = this;
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    switch (i2) {
                        case 0:
                            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction()) || "android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED".equals(intent.getAction())) {
                                this.this$0.sendWifiSettingUpdate(false);
                                break;
                            }
                            break;
                        default:
                            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                                this.this$0.sendBtSettingUpdate(false);
                                break;
                            }
                            break;
                    }
                }
            }, BatteryService$$ExternalSyntheticOutline0.m("android.bluetooth.adapter.action.STATE_CHANGED"));
            final int i4 = 3;
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("ble_scan_always_enabled"), false, new ContentObserver(this) { // from class: com.android.server.location.contexthub.ContextHubService.2
                public final /* synthetic */ ContextHubService this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(null);
                    this.this$0 = this;
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    switch (i4) {
                        case 0:
                            this.this$0.sendLocationSettingUpdate();
                            break;
                        case 1:
                            this.this$0.sendWifiSettingUpdate(false);
                            break;
                        case 2:
                            this.this$0.sendAirplaneModeSettingUpdate();
                            break;
                        default:
                            this.this$0.sendBtSettingUpdate(false);
                            break;
                    }
                }
            }, -1);
        }
        scheduleDailyMetricSnapshot();
        Log.i("ContextHubService", "Finished Context Hub Service init");
    }

    public static int getCurrentUserId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = ActivityManager.getService().getCurrentUser().id;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean checkHalProxyAndContextHubId(int i, IContextHubTransactionCallback iContextHubTransactionCallback, int i2) {
        if (this.mContextHubWrapper == null) {
            try {
                iContextHubTransactionCallback.onTransactionComplete(8);
            } catch (RemoteException e) {
                Log.e("ContextHubService", "RemoteException while calling onTransactionComplete", e);
            }
            return false;
        }
        if (this.mContextHubIdToInfoMap.containsKey(Integer.valueOf(i))) {
            return true;
        }
        Log.e("ContextHubService", "Cannot start " + ContextHubTransaction.typeToString(i2, false) + " transaction for invalid hub ID " + i);
        try {
            iContextHubTransactionCallback.onTransactionComplete(2);
        } catch (RemoteException e2) {
            Log.e("ContextHubService", "RemoteException while calling onTransactionComplete", e2);
        }
        return false;
    }

    public final void cleanupReliableMessageRecordQueue() {
        synchronized (this.mReliableMessageRecordQueue) {
            while (this.mReliableMessageRecordQueue.peek() != null && ((ReliableMessageRecord) this.mReliableMessageRecordQueue.peek()).mTimestamp + 1000000000 < SystemClock.elapsedRealtimeNanos()) {
                try {
                    this.mReliableMessageRecordQueue.poll();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final IContextHubClient createClient(int i, IContextHubClientCallback iContextHubClientCallback, String str, String str2) {
        createClient_enforcePermission();
        if (!this.mContextHubIdToInfoMap.containsKey(Integer.valueOf(i))) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid context hub ID "));
        }
        if (iContextHubClientCallback == null) {
            throw new NullPointerException("Cannot register client with null callback");
        }
        return this.mClientManager.registerClient((ContextHubInfo) this.mContextHubIdToInfoMap.get(Integer.valueOf(i)), iContextHubClientCallback, str, this.mTransactionManager, str2);
    }

    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.os.IBinder, com.android.server.location.contexthub.ContextHubClientBroker] */
    /* JADX WARN: Type inference failed for: r2v9 */
    public final IContextHubClient createPendingIntentClient(int i, PendingIntent pendingIntent, long j, String str) {
        ?? r2;
        createPendingIntentClient_enforcePermission();
        if (!this.mContextHubIdToInfoMap.containsKey(Integer.valueOf(i))) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid context hub ID "));
        }
        ContextHubInfo contextHubInfo = (ContextHubInfo) this.mContextHubIdToInfoMap.get(Integer.valueOf(i));
        ContextHubClientManager contextHubClientManager = this.mClientManager;
        ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
        String str2 = "Regenerated";
        synchronized (contextHubClientManager) {
            try {
                ContextHubClientBroker clientBroker = contextHubClientManager.getClientBroker(contextHubInfo.getId(), pendingIntent, j);
                if (clientBroker == null) {
                    short hostEndPointId = contextHubClientManager.getHostEndPointId();
                    ContextHubClientBroker contextHubClientBroker = new ContextHubClientBroker(contextHubClientManager.mContext, contextHubClientManager.mContextHubProxy, contextHubClientManager, contextHubInfo, hostEndPointId, null, str, contextHubTransactionManager, pendingIntent, j, pendingIntent.getCreatorPackage());
                    contextHubClientManager.mHostEndPointIdToClientMap.put(Short.valueOf(hostEndPointId), contextHubClientBroker);
                    str2 = "Registered";
                    contextHubClientManager.mRegistrationRecordDeque.add(new ContextHubClientManager.RegistrationRecord(contextHubClientBroker.toString(), 0));
                    r2 = contextHubClientBroker;
                } else {
                    clientBroker.mAttributionTag = str;
                    r2 = clientBroker;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, " client with host endpoint ID ");
        m.append((int) r2.mHostEndPointId);
        Log.d("ContextHubClientManager", m.toString());
        return IContextHubClient.Stub.asInterface((IBinder) r2);
    }

    public final void disableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) {
        disableNanoApp_enforcePermission();
        if (checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 3)) {
            ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
            this.mTransactionManager.addTransaction(new ContextHubTransactionManager.AnonymousClass2(contextHubTransactionManager, contextHubTransactionManager.mNextAvailableId.getAndIncrement(), getCallingPackageName(), i, j, iContextHubTransactionCallback, 2));
        }
    }

    public final void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        int intValue;
        long[] preloadedNanoappIds;
        if (DumpUtils.checkDumpPermission(this.mContext, "ContextHubService", printWriter)) {
            for (String str : strArr) {
                if ("--proto".equals(str)) {
                    final ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                    final int i = 1;
                    this.mContextHubIdToInfoMap.values().forEach(new Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i2 = i;
                            Object obj2 = protoOutputStream;
                            switch (i2) {
                                case 0:
                                    ((PrintWriter) obj2).println((NanoAppInstanceInfo) obj);
                                    break;
                                default:
                                    ProtoOutputStream protoOutputStream2 = (ProtoOutputStream) obj2;
                                    long start = protoOutputStream2.start(2246267895809L);
                                    ((ContextHubInfo) obj).dump(protoOutputStream2);
                                    protoOutputStream2.end(start);
                                    break;
                            }
                        }
                    });
                    long start = protoOutputStream.start(1146756268034L);
                    ContextHubClientManager contextHubClientManager = this.mClientManager;
                    for (ContextHubClientBroker contextHubClientBroker : contextHubClientManager.mHostEndPointIdToClientMap.values()) {
                        long start2 = protoOutputStream.start(2246267895809L);
                        protoOutputStream.write(1120986464257L, (int) contextHubClientBroker.mHostEndPointId);
                        protoOutputStream.write(1120986464258L, contextHubClientBroker.mAttachedContextHubInfo.getId());
                        protoOutputStream.write(1138166333443L, contextHubClientBroker.mPackage);
                        boolean z = true;
                        if (contextHubClientBroker.mPendingIntentRequest.mValid) {
                            protoOutputStream.write(1133871366149L, true);
                            protoOutputStream.write(1112396529668L, contextHubClientBroker.mPendingIntentRequest.mNanoAppId);
                        }
                        if (contextHubClientBroker.mPendingIntentRequest.mPendingIntent == null) {
                            z = false;
                        }
                        protoOutputStream.write(1133871366150L, z);
                        protoOutputStream.write(1133871366151L, contextHubClientBroker.mIsPendingIntentCancelled.get());
                        protoOutputStream.write(1133871366152L, contextHubClientBroker.mRegistered);
                        protoOutputStream.end(start2);
                    }
                    Iterator descendingIterator = contextHubClientManager.mRegistrationRecordDeque.descendingIterator();
                    while (descendingIterator.hasNext()) {
                        long start3 = protoOutputStream.start(2246267895810L);
                        ContextHubClientManager.RegistrationRecord registrationRecord = (ContextHubClientManager.RegistrationRecord) descendingIterator.next();
                        protoOutputStream.write(1112396529665L, registrationRecord.mTimestamp);
                        protoOutputStream.write(1120986464258L, registrationRecord.mAction);
                        protoOutputStream.write(1138166333443L, registrationRecord.mBroker);
                        protoOutputStream.end(start3);
                    }
                    protoOutputStream.end(start);
                    protoOutputStream.flush();
                    return;
                }
            }
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "Dumping ContextHub Service", "", "=================== CONTEXT HUBS ====================");
            Iterator it = this.mContextHubIdToInfoMap.values().iterator();
            while (it.hasNext()) {
                printWriter.println((ContextHubInfo) it.next());
            }
            printWriter.println("Supported permissions: " + Arrays.toString(this.mSupportedContextHubPerms.toArray()));
            printWriter.println("");
            printWriter.println("=================== NANOAPPS ====================");
            final int i2 = 0;
            this.mNanoAppStateManager.foreachNanoAppInstanceInfo(new Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i22 = i2;
                    Object obj2 = printWriter;
                    switch (i22) {
                        case 0:
                            ((PrintWriter) obj2).println((NanoAppInstanceInfo) obj);
                            break;
                        default:
                            ProtoOutputStream protoOutputStream2 = (ProtoOutputStream) obj2;
                            long start4 = protoOutputStream2.start(2246267895809L);
                            ((ContextHubInfo) obj).dump(protoOutputStream2);
                            protoOutputStream2.end(start4);
                            break;
                    }
                }
            });
            printWriter.println("");
            printWriter.println("=================== PRELOADED NANOAPPS ====================");
            if (this.mContextHubWrapper != null) {
                Iterator it2 = this.mContextHubIdToInfoMap.keySet().iterator();
                while (it2.hasNext() && (preloadedNanoappIds = this.mContextHubWrapper.getPreloadedNanoappIds((intValue = ((Integer) it2.next()).intValue()))) != null) {
                    printWriter.print("Context Hub (id=");
                    printWriter.print(intValue);
                    printWriter.println("):");
                    for (long j : preloadedNanoappIds) {
                        printWriter.print("  ID: 0x");
                        printWriter.println(Long.toHexString(j));
                    }
                }
            }
            printWriter.println("");
            printWriter.println("=================== CLIENTS ====================");
            printWriter.println(this.mClientManager);
            printWriter.println("");
            printWriter.println("=================== TRANSACTIONS ====================");
            printWriter.println(this.mTransactionManager);
            printWriter.println("");
            printWriter.println("=================== EVENTS ====================");
            printWriter.println(ContextHubEventLogger.getInstance());
        }
    }

    public final void enableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) {
        enableNanoApp_enforcePermission();
        if (checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 2)) {
            ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
            this.mTransactionManager.addTransaction(new ContextHubTransactionManager.AnonymousClass2(contextHubTransactionManager, contextHubTransactionManager.mNextAvailableId.getAndIncrement(), getCallingPackageName(), i, j, iContextHubTransactionCallback, 1));
        }
    }

    public final int[] findNanoAppOnHub(int i, final NanoAppFilter nanoAppFilter) {
        findNanoAppOnHub_enforcePermission();
        final ArrayList arrayList = new ArrayList();
        if (nanoAppFilter != null) {
            this.mNanoAppStateManager.foreachNanoAppInstanceInfo(new Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NanoAppFilter nanoAppFilter2 = nanoAppFilter;
                    ArrayList arrayList2 = arrayList;
                    NanoAppInstanceInfo nanoAppInstanceInfo = (NanoAppInstanceInfo) obj;
                    if (nanoAppFilter2.testMatch(nanoAppInstanceInfo)) {
                        arrayList2.add(Integer.valueOf(nanoAppInstanceInfo.getHandle()));
                    }
                }
            });
        }
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
        }
        return iArr;
    }

    public final String getCallingPackageName() {
        return this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
    }

    public final int[] getContextHubHandles() {
        getContextHubHandles_enforcePermission();
        Set keySet = this.mContextHubIdToInfoMap.keySet();
        DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
        int[] iArr = new int[keySet.size()];
        Iterator it = keySet.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((Integer) it.next()).intValue();
            i++;
        }
        return iArr;
    }

    public final ContextHubInfo getContextHubInfo(int i) {
        getContextHubInfo_enforcePermission();
        if (this.mContextHubIdToInfoMap.containsKey(Integer.valueOf(i))) {
            return (ContextHubInfo) this.mContextHubIdToInfoMap.get(Integer.valueOf(i));
        }
        AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "Invalid Context Hub handle ", " in getContextHubInfo", "ContextHubService");
        return null;
    }

    public final List getContextHubs() {
        getContextHubs_enforcePermission();
        return this.mContextHubInfoList;
    }

    public final NanoAppInstanceInfo getNanoAppInstanceInfo(int i) {
        NanoAppInstanceInfo nanoAppInstanceInfo;
        getNanoAppInstanceInfo_enforcePermission();
        NanoAppStateManager nanoAppStateManager = this.mNanoAppStateManager;
        synchronized (nanoAppStateManager) {
            nanoAppInstanceInfo = (NanoAppInstanceInfo) nanoAppStateManager.mNanoAppHash.get(Integer.valueOf(i));
        }
        return nanoAppInstanceInfo;
    }

    public final long[] getPreloadedNanoAppIds(ContextHubInfo contextHubInfo) {
        getPreloadedNanoAppIds_enforcePermission();
        Objects.requireNonNull(contextHubInfo, "hubInfo cannot be null");
        long[] preloadedNanoappIds = this.mContextHubWrapper.getPreloadedNanoappIds(contextHubInfo.getId());
        return preloadedNanoappIds == null ? new long[0] : preloadedNanoappIds;
    }

    public final int loadNanoApp(final int i, NanoApp nanoApp) {
        loadNanoApp_enforcePermission();
        if (this.mContextHubWrapper == null) {
            return -1;
        }
        if (!this.mContextHubIdToInfoMap.containsKey(Integer.valueOf(i))) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "Invalid Context Hub handle ", " in loadNanoApp", "ContextHubService");
            return -1;
        }
        if (nanoApp == null) {
            Log.e("ContextHubService", "NanoApp cannot be null in loadNanoApp");
            return -1;
        }
        final NanoAppBinary nanoAppBinary = new NanoAppBinary(nanoApp.getAppBinary());
        IContextHubTransactionCallback.Stub stub = new IContextHubTransactionCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubService.8
            public final void onQueryResponse(int i2, List list) {
            }

            public final void onTransactionComplete(int i2) {
                ContextHubService contextHubService = ContextHubService.this;
                int i3 = i;
                NanoAppBinary nanoAppBinary2 = nanoAppBinary;
                contextHubService.getClass();
                if (nanoAppBinary2 == null) {
                    Log.e("ContextHubService", "Nanoapp binary field was null for a load transaction");
                    return;
                }
                byte[] bArr = new byte[5];
                bArr[0] = (byte) i2;
                ByteBuffer.wrap(bArr, 1, 4).order(ByteOrder.nativeOrder()).putInt(contextHubService.mNanoAppStateManager.getNanoAppHandle(i3, nanoAppBinary2.getNanoAppId()));
                contextHubService.onMessageReceiptOldApi(3, i3, -1, bArr);
            }
        };
        ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
        this.mTransactionManager.addTransaction(contextHubTransactionManager.new AnonymousClass1(contextHubTransactionManager.mNextAvailableId.getAndIncrement(), nanoAppBinary.getNanoAppId(), getCallingPackageName(), i, nanoAppBinary, stub));
        return 0;
    }

    public final void loadNanoAppOnHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) {
        loadNanoAppOnHub_enforcePermission();
        if (checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 0)) {
            if (nanoAppBinary == null) {
                Log.e("ContextHubService", "NanoAppBinary cannot be null in loadNanoAppOnHub");
                iContextHubTransactionCallback.onTransactionComplete(2);
            } else {
                ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
                this.mTransactionManager.addTransaction(contextHubTransactionManager.new AnonymousClass1(contextHubTransactionManager.mNextAvailableId.getAndIncrement(), nanoAppBinary.getNanoAppId(), getCallingPackageName(), i, nanoAppBinary, iContextHubTransactionCallback));
            }
        }
    }

    public final int onMessageReceiptOldApi(int i, int i2, int i3, byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        synchronized (this.mCallbacksList) {
            try {
                int beginBroadcast = this.mCallbacksList.beginBroadcast();
                if (beginBroadcast < 1) {
                    return 0;
                }
                ContextHubMessage contextHubMessage = new ContextHubMessage(i, 0, bArr);
                for (int i4 = 0; i4 < beginBroadcast; i4++) {
                    IContextHubCallback broadcastItem = this.mCallbacksList.getBroadcastItem(i4);
                    try {
                        broadcastItem.onMessageReceipt(i2, i3, contextHubMessage);
                    } catch (RemoteException e) {
                        Log.i("ContextHubService", "Exception (" + e + ") calling remote callback (" + broadcastItem + ").");
                    }
                }
                this.mCallbacksList.finishBroadcast();
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new ContextHubShellCommand(this.mContext, this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void queryNanoApps(int i, IContextHubTransactionCallback iContextHubTransactionCallback) {
        queryNanoApps_enforcePermission();
        if (checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 4)) {
            ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
            this.mTransactionManager.addTransaction(contextHubTransactionManager.new AnonymousClass6(contextHubTransactionManager.mNextAvailableId.getAndIncrement(), getCallingPackageName(), i, iContextHubTransactionCallback));
        }
    }

    public final boolean queryNanoAppsInternal(int i) {
        if (this.mContextHubWrapper == null) {
            return false;
        }
        AnonymousClass9 anonymousClass9 = new AnonymousClass9(this, i, 1);
        ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
        this.mTransactionManager.addTransaction(contextHubTransactionManager.new AnonymousClass6(contextHubTransactionManager.mNextAvailableId.getAndIncrement(), getCallingPackageName(), i, anonymousClass9));
        return true;
    }

    public final int registerCallback(IContextHubCallback iContextHubCallback) {
        registerCallback_enforcePermission();
        this.mCallbacksList.register(iContextHubCallback);
        Log.d("ContextHubService", "Added callback, total callbacks " + this.mCallbacksList.getRegisteredCallbackCount());
        return 0;
    }

    public final void scheduleDailyMetricSnapshot() {
        try {
            this.mDailyMetricTimer.schedule(new Runnable() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubService contextHubService = ContextHubService.this;
                    for (Integer num : contextHubService.mContextHubIdToInfoMap.keySet()) {
                        int intValue = num.intValue();
                        contextHubService.mMetricQueryPendingContextHubIds.add(num);
                        contextHubService.queryNanoAppsInternal(intValue);
                    }
                }
            }, 1L, TimeUnit.DAYS);
        } catch (Exception e) {
            Log.e("ContextHubService", "Error when schedule a timer", e);
        }
    }

    public final void sendAirplaneModeSettingUpdate() {
        this.mContextHubWrapper.onAirplaneModeSettingChanged(Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1);
    }

    public final void sendBtSettingUpdate(boolean z) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            boolean isEnabled = defaultAdapter.isEnabled();
            boolean isBleScanAlwaysAvailable = defaultAdapter.isBleScanAlwaysAvailable();
            if (z || this.mIsBtScanningEnabled != isBleScanAlwaysAvailable) {
                this.mIsBtScanningEnabled = isBleScanAlwaysAvailable;
                this.mContextHubWrapper.onBtScanningSettingChanged(isBleScanAlwaysAvailable);
            }
            if (z || this.mIsBtMainEnabled != isEnabled) {
                this.mIsBtMainEnabled = isEnabled;
                this.mContextHubWrapper.onBtMainSettingChanged(isEnabled);
                return;
            }
            return;
        }
        Log.d("ContextHubService", "BT adapter not available. Getting permissions from user settings");
        boolean z2 = Settings.Global.getInt(this.mContext.getContentResolver(), "bluetooth_on", 0) == 1;
        boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "ble_scan_always_enabled", 0) == 1;
        if (z || this.mIsBtMainEnabled != z2) {
            this.mIsBtMainEnabled = z2;
            this.mContextHubWrapper.onBtMainSettingChanged(z2);
        }
        if (z || this.mIsBtScanningEnabled != z3) {
            this.mIsBtScanningEnabled = z3;
            this.mContextHubWrapper.onBtScanningSettingChanged(z3);
        }
    }

    public final void sendLocationSettingUpdate() {
        this.mContextHubWrapper.onLocationSettingChanged(((LocationManager) this.mContext.getSystemService(LocationManager.class)).isLocationEnabledForUser(UserHandle.CURRENT));
    }

    public final int sendMessage(int i, int i2, ContextHubMessage contextHubMessage) {
        boolean z;
        sendMessage_enforcePermission();
        if (this.mContextHubWrapper == null) {
            return -1;
        }
        if (contextHubMessage == null) {
            Log.e("ContextHubService", "ContextHubMessage cannot be null in sendMessage");
            return -1;
        }
        if (contextHubMessage.getData() == null) {
            Log.e("ContextHubService", "ContextHubMessage message body cannot be null in sendMessage");
            return -1;
        }
        if (!this.mContextHubIdToInfoMap.containsKey(Integer.valueOf(i))) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "Invalid Context Hub handle ", " in sendMessage", "ContextHubService");
            return -1;
        }
        if (i2 != -1) {
            NanoAppInstanceInfo nanoAppInstanceInfo = getNanoAppInstanceInfo(i2);
            if (nanoAppInstanceInfo != null) {
                if (((IContextHubClient) this.mDefaultClientMap.get(Integer.valueOf(i))).sendMessageToNanoApp(NanoAppMessage.createMessageToNanoApp(nanoAppInstanceInfo.getAppId(), contextHubMessage.getMsgType(), contextHubMessage.getData())) == 0) {
                    z = true;
                }
            } else {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(i2, "Failed to send nanoapp message - nanoapp with handle ", " does not exist.", "ContextHubService");
            }
            z = false;
        } else if (contextHubMessage.getMsgType() == 5) {
            z = queryNanoAppsInternal(i);
        } else {
            Log.e("ContextHubService", "Invalid OS message params of type " + contextHubMessage.getMsgType());
            z = false;
        }
        return z ? 0 : -1;
    }

    public final void sendMessageDeliveryStatusToContextHub(int i, int i2, byte b) {
        if (Flags.reliableMessageImplementation()) {
            MessageDeliveryStatus messageDeliveryStatus = new MessageDeliveryStatus();
            messageDeliveryStatus.messageSequenceNumber = i2;
            messageDeliveryStatus.errorCode = b;
            if (this.mContextHubWrapper.sendMessageDeliveryStatusToContextHub(i, messageDeliveryStatus) != 0) {
                Log.e("ContextHubService", "Failed to send the reliable message status for message sequence number: " + i2 + " with error code: " + ((int) b));
            }
        }
    }

    public final void sendMicrophoneDisableSettingUpdateForCurrentUser() {
        SensorPrivacyManagerInternal sensorPrivacyManagerInternal = this.mSensorPrivacyManagerInternal;
        boolean isSensorPrivacyEnabled = sensorPrivacyManagerInternal == null ? false : sensorPrivacyManagerInternal.isSensorPrivacyEnabled(getCurrentUserId(), 1);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("Mic Disabled Setting: ", "ContextHubService", isSensorPrivacyEnabled);
        this.mContextHubWrapper.onMicrophoneSettingChanged(!isSensorPrivacyEnabled);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001f A[Catch: all -> 0x0024, TryCatch #0 {all -> 0x0024, blocks: (B:4:0x0003, B:10:0x001f, B:14:0x002f, B:17:0x003c, B:19:0x0047, B:23:0x0040, B:24:0x0033, B:25:0x0026), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f A[Catch: all -> 0x0024, TryCatch #0 {all -> 0x0024, blocks: (B:4:0x0003, B:10:0x001f, B:14:0x002f, B:17:0x003c, B:19:0x0047, B:23:0x0040, B:24:0x0033, B:25:0x0026), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003c A[Catch: all -> 0x0024, TryCatch #0 {all -> 0x0024, blocks: (B:4:0x0003, B:10:0x001f, B:14:0x002f, B:17:0x003c, B:19:0x0047, B:23:0x0040, B:24:0x0033, B:25:0x0026), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendWifiSettingUpdate(boolean r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mSendWifiSettingUpdateLock
            monitor-enter(r0)
            android.content.Context r1 = r5.mContext     // Catch: java.lang.Throwable -> L24
            java.lang.Class<android.net.wifi.WifiManager> r2 = android.net.wifi.WifiManager.class
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch: java.lang.Throwable -> L24
            android.net.wifi.WifiManager r1 = (android.net.wifi.WifiManager) r1     // Catch: java.lang.Throwable -> L24
            boolean r2 = r1.isWifiEnabled()     // Catch: java.lang.Throwable -> L24
            boolean r1 = r1.isScanAlwaysAvailable()     // Catch: java.lang.Throwable -> L24
            if (r2 != 0) goto L1c
            if (r1 == 0) goto L1a
            goto L1c
        L1a:
            r3 = 0
            goto L1d
        L1c:
            r3 = 1
        L1d:
            if (r6 != 0) goto L26
            boolean r4 = r5.mIsWifiAvailable     // Catch: java.lang.Throwable -> L24
            if (r4 == r3) goto L2d
            goto L26
        L24:
            r5 = move-exception
            goto L49
        L26:
            r5.mIsWifiAvailable = r3     // Catch: java.lang.Throwable -> L24
            com.android.server.location.contexthub.IContextHubWrapper r4 = r5.mContextHubWrapper     // Catch: java.lang.Throwable -> L24
            r4.onWifiSettingChanged(r3)     // Catch: java.lang.Throwable -> L24
        L2d:
            if (r6 != 0) goto L33
            boolean r3 = r5.mIsWifiScanningEnabled     // Catch: java.lang.Throwable -> L24
            if (r3 == r1) goto L3a
        L33:
            r5.mIsWifiScanningEnabled = r1     // Catch: java.lang.Throwable -> L24
            com.android.server.location.contexthub.IContextHubWrapper r3 = r5.mContextHubWrapper     // Catch: java.lang.Throwable -> L24
            r3.onWifiScanningSettingChanged(r1)     // Catch: java.lang.Throwable -> L24
        L3a:
            if (r6 != 0) goto L40
            boolean r6 = r5.mIsWifiMainEnabled     // Catch: java.lang.Throwable -> L24
            if (r6 == r2) goto L47
        L40:
            r5.mIsWifiMainEnabled = r2     // Catch: java.lang.Throwable -> L24
            com.android.server.location.contexthub.IContextHubWrapper r5 = r5.mContextHubWrapper     // Catch: java.lang.Throwable -> L24
            r5.onWifiMainSettingChanged(r2)     // Catch: java.lang.Throwable -> L24
        L47:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L24
            return
        L49:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L24
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.contexthub.ContextHubService.sendWifiSettingUpdate(boolean):void");
    }

    public final boolean setTestMode(boolean z) {
        setTestMode_enforcePermission();
        boolean testMode = this.mContextHubWrapper.setTestMode(z);
        if (testMode) {
            this.mIsTestModeEnabled.set(z);
        }
        Iterator it = this.mDefaultClientMap.keySet().iterator();
        while (it.hasNext()) {
            queryNanoAppsInternal(((Integer) it.next()).intValue());
        }
        return testMode;
    }

    public final int unloadNanoApp(int i) {
        NanoAppInstanceInfo nanoAppInstanceInfo;
        unloadNanoApp_enforcePermission();
        if (this.mContextHubWrapper == null) {
            return -1;
        }
        NanoAppStateManager nanoAppStateManager = this.mNanoAppStateManager;
        synchronized (nanoAppStateManager) {
            nanoAppInstanceInfo = (NanoAppInstanceInfo) nanoAppStateManager.mNanoAppHash.get(Integer.valueOf(i));
        }
        if (nanoAppInstanceInfo == null) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "Invalid nanoapp handle ", " in unloadNanoApp", "ContextHubService");
            return -1;
        }
        int contexthubId = nanoAppInstanceInfo.getContexthubId();
        long appId = nanoAppInstanceInfo.getAppId();
        AnonymousClass9 anonymousClass9 = new AnonymousClass9(this, contexthubId, 0);
        ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
        this.mTransactionManager.addTransaction(new ContextHubTransactionManager.AnonymousClass2(contextHubTransactionManager, contextHubTransactionManager.mNextAvailableId.getAndIncrement(), appId, getCallingPackageName(), contexthubId, appId, (IContextHubTransactionCallback) anonymousClass9));
        return 0;
    }

    public final void unloadNanoAppFromHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) {
        unloadNanoAppFromHub_enforcePermission();
        if (checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 1)) {
            ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
            this.mTransactionManager.addTransaction(new ContextHubTransactionManager.AnonymousClass2(contextHubTransactionManager, contextHubTransactionManager.mNextAvailableId.getAndIncrement(), j, getCallingPackageName(), i, j, iContextHubTransactionCallback));
        }
    }
}
