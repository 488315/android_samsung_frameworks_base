package com.android.server;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.net.INetworkRecommendationProvider;
import android.net.INetworkScoreCache;
import android.net.INetworkScoreService;
import android.net.NetworkKey;
import android.net.NetworkScorerAppData;
import android.net.ScoredNetwork;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import com.android.internal.content.PackageMonitor;
import com.android.internal.util.DumpUtils;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider;
import com.android.server.pm.permission.LegacyPermissionManagerService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NetworkScoreService extends INetworkScoreService.Stub {
    public static final boolean DBG;
    public static final boolean VERBOSE;
    public final Context mContext;
    public final ServiceHandler mHandler;
    public final AnonymousClass1 mLocationModeReceiver;
    public final NetworkScorerAppManager mNetworkScorerAppManager;
    public NetworkScorerPackageMonitor mPackageMonitor;
    public final DispatchingContentObserver mRecommendationSettingsObserver;
    public final Map mScoreCaches;
    public final Function mServiceConnProducer;
    public ScoringServiceConnection mServiceConnection;
    public final AnonymousClass3 mUseOpenWifiPackageObserver;
    public final AnonymousClass1 mUserIntentReceiver;
    public final Object mPackageMonitorLock = new Object();
    public final Object mServiceConnectionLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.NetworkScoreService$4, reason: invalid class name */
    public final class AnonymousClass4 implements BiConsumer {
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            try {
                ((INetworkScoreCache) obj).clearScores();
            } catch (RemoteException e) {
                if (Log.isLoggable("NetworkScoreService", 2)) {
                    Log.v("NetworkScoreService", "Unable to clear scores", e);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class CurrentNetworkScoreCacheFilter implements UnaryOperator {
        public final NetworkKey mCurrentNetwork;

        public CurrentNetworkScoreCacheFilter(WifiInfoSupplier wifiInfoSupplier) {
            this.mCurrentNetwork = NetworkKey.createFromWifiInfo((WifiInfo) wifiInfoSupplier.get());
        }

        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            List list = (List) obj;
            if (this.mCurrentNetwork == null || list.isEmpty()) {
                return Collections.emptyList();
            }
            for (int i = 0; i < list.size(); i++) {
                ScoredNetwork scoredNetwork = (ScoredNetwork) list.get(i);
                if (scoredNetwork.networkKey.equals(this.mCurrentNetwork)) {
                    return Collections.singletonList(scoredNetwork);
                }
            }
            return Collections.emptyList();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class DispatchingContentObserver extends ContentObserver {
        public final Context mContext;
        public final Handler mHandler;
        public final Map mUriEventMap;

        public DispatchingContentObserver(Context context, ServiceHandler serviceHandler) {
            super(serviceHandler);
            this.mContext = context;
            this.mHandler = serviceHandler;
            this.mUriEventMap = new ArrayMap();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            onChange(z, null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (NetworkScoreService.DBG) {
                Log.d(
                        "NetworkScoreService",
                        String.format("onChange(%s, %s)", Boolean.valueOf(z), uri));
            }
            Integer num = (Integer) ((ArrayMap) this.mUriEventMap).get(uri);
            if (num != null) {
                this.mHandler.obtainMessage(num.intValue()).sendToTarget();
                return;
            }
            Log.w("NetworkScoreService", "No matching event to send for URI = " + uri);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class FilteringCacheUpdatingConsumer implements BiConsumer {
        public final Context mContext;
        public UnaryOperator mCurrentNetworkFilter;
        public final int mNetworkType;
        public UnaryOperator mScanResultsFilter;
        public final List mScoredNetworkList;

        public FilteringCacheUpdatingConsumer(
                Context context,
                List list,
                int i,
                UnaryOperator unaryOperator,
                UnaryOperator unaryOperator2) {
            this.mContext = context;
            this.mScoredNetworkList = list;
            this.mNetworkType = i;
            this.mCurrentNetworkFilter = unaryOperator;
            this.mScanResultsFilter = unaryOperator2;
        }

        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            INetworkScoreCache iNetworkScoreCache = (INetworkScoreCache) obj;
            try {
                List filterScores =
                        filterScores(
                                obj2 instanceof Integer ? ((Integer) obj2).intValue() : 0,
                                this.mScoredNetworkList);
                if (filterScores.isEmpty()) {
                    return;
                }
                iNetworkScoreCache.updateScores(filterScores);
            } catch (RemoteException e) {
                if (NetworkScoreService.VERBOSE) {
                    Log.v(
                            "NetworkScoreService",
                            "Unable to update scores of type " + this.mNetworkType,
                            e);
                }
            }
        }

        public final List filterScores(int i, List list) {
            if (i == 0) {
                return list;
            }
            if (i == 1) {
                if (this.mCurrentNetworkFilter == null) {
                    this.mCurrentNetworkFilter =
                            new CurrentNetworkScoreCacheFilter(
                                    new WifiInfoSupplier(this.mContext, 0));
                }
                return (List) this.mCurrentNetworkFilter.apply(list);
            }
            if (i != 2) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i, "Unknown filter type: ", "NetworkScoreService");
                return list;
            }
            if (this.mScanResultsFilter == null) {
                this.mScanResultsFilter =
                        new ScanResultsScoreCacheFilter(new WifiInfoSupplier(this.mContext, 1));
            }
            return (List) this.mScanResultsFilter.apply(list);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final NetworkScoreService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService =
                    new NetworkScoreService(
                            context,
                            new NetworkScorerAppManager(
                                    context, new NetworkScorerAppManager.SettingsFacade()),
                            new NetworkScoreService$$ExternalSyntheticLambda2(),
                            Looper.myLooper());
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            NetworkScoreService networkScoreService = this.mService;
            if (i != 500) {
                if (i == 1000) {
                    networkScoreService.getClass();
                    if (NetworkScoreService.DBG) {
                        Log.d("NetworkScoreService", "systemRunning");
                        return;
                    }
                    return;
                }
                return;
            }
            networkScoreService.getClass();
            if (NetworkScoreService.DBG) {
                Log.d("NetworkScoreService", "systemReady");
            }
            Uri uriFor = Settings.Global.getUriFor("network_recommendations_package");
            DispatchingContentObserver dispatchingContentObserver =
                    networkScoreService.mRecommendationSettingsObserver;
            ((ArrayMap) dispatchingContentObserver.mUriEventMap).put(uriFor, 1);
            dispatchingContentObserver
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(uriFor, false, dispatchingContentObserver);
            Uri uriFor2 = Settings.Global.getUriFor("network_recommendations_enabled");
            DispatchingContentObserver dispatchingContentObserver2 =
                    networkScoreService.mRecommendationSettingsObserver;
            ((ArrayMap) dispatchingContentObserver2.mUriEventMap).put(uriFor2, 2);
            dispatchingContentObserver2
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(uriFor2, false, dispatchingContentObserver2);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            Log.i("NetworkScoreService", "Registering network_score");
            publishBinderService("network_score", this.mService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkScorerPackageMonitor extends PackageMonitor {
        public final String mPackageToWatch;

        public NetworkScorerPackageMonitor(String str) {
            this.mPackageToWatch = str;
        }

        public final void evaluateBinding(String str, boolean z) {
            if (this.mPackageToWatch.equals(str)) {
                boolean z2 = NetworkScoreService.DBG;
                if (z2) {
                    Log.d(
                            "NetworkScoreService",
                            "Evaluating binding for: " + str + ", forceUnbind=" + z);
                }
                NetworkScorerAppData activeScorer =
                        NetworkScoreService.this.mNetworkScorerAppManager.getActiveScorer();
                if (activeScorer == null) {
                    if (z2) {
                        Log.d("NetworkScoreService", "No active scorers available.");
                    }
                    NetworkScoreService.this.refreshBinding();
                    return;
                }
                if (z) {
                    NetworkScoreService.this.unbindFromScoringServiceIfNeeded();
                }
                if (z2) {
                    Log.d(
                            "NetworkScoreService",
                            "Binding to "
                                    + activeScorer.getRecommendationServiceComponent()
                                    + " if needed.");
                }
                NetworkScoreService.this.bindToScoringServiceIfNeeded(activeScorer);
            }
        }

        public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
            if (z) {
                for (String str : strArr) {
                    evaluateBinding(str, true);
                }
            }
            return super.onHandleForceStop(intent, strArr, i, z);
        }

        public final void onPackageAdded(String str, int i) {
            evaluateBinding(str, true);
        }

        public final void onPackageModified(String str) {
            evaluateBinding(str, false);
        }

        public final void onPackageRemoved(String str, int i) {
            evaluateBinding(str, true);
        }

        public final void onPackageUpdateFinished(String str, int i) {
            evaluateBinding(str, true);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class ScanResultsScoreCacheFilter implements UnaryOperator {
        public final Set mScanResultKeys;

        public ScanResultsScoreCacheFilter(WifiInfoSupplier wifiInfoSupplier) {
            List list = (List) wifiInfoSupplier.get();
            int size = list.size();
            this.mScanResultKeys = new ArraySet(size);
            for (int i = 0; i < size; i++) {
                NetworkKey createFromScanResult =
                        NetworkKey.createFromScanResult((ScanResult) list.get(i));
                if (createFromScanResult != null) {
                    ((ArraySet) this.mScanResultKeys).add(createFromScanResult);
                }
            }
        }

        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            List list = (List) obj;
            if (((ArraySet) this.mScanResultKeys).isEmpty() || list.isEmpty()) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                ScoredNetwork scoredNetwork = (ScoredNetwork) list.get(i);
                if (((ArraySet) this.mScanResultKeys).contains(scoredNetwork.networkKey)) {
                    arrayList.add(scoredNetwork);
                }
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ScoringServiceConnection implements ServiceConnection {
        public final NetworkScorerAppData mAppData;
        public volatile boolean mBound = false;
        public volatile boolean mConnected = false;
        public volatile INetworkRecommendationProvider mRecommendationProvider;

        public ScoringServiceConnection(NetworkScorerAppData networkScorerAppData) {
            this.mAppData = networkScorerAppData;
        }

        public void bind(Context context) {
            if (this.mBound) {
                return;
            }
            Intent intent = new Intent("android.net.action.RECOMMEND_NETWORKS");
            intent.setComponent(this.mAppData.getRecommendationServiceComponent());
            this.mBound = context.bindServiceAsUser(intent, this, 67108865, UserHandle.SYSTEM);
            if (this.mBound) {
                if (NetworkScoreService.DBG) {
                    Log.d("NetworkScoreService", "ScoringServiceConnection bound.");
                }
            } else {
                Log.w("NetworkScoreService", "Bind call failed for " + intent);
                context.unbindService(this);
            }
        }

        public NetworkScorerAppData getAppData() {
            return this.mAppData;
        }

        public String getPackageName() {
            return this.mAppData.getRecommendationServiceComponent().getPackageName();
        }

        public INetworkRecommendationProvider getRecommendationProvider() {
            return this.mRecommendationProvider;
        }

        public boolean isAlive() {
            return this.mBound && this.mConnected;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (NetworkScoreService.DBG) {
                Log.d(
                        "NetworkScoreService",
                        "ScoringServiceConnection: " + componentName.flattenToString());
            }
            this.mConnected = true;
            this.mRecommendationProvider = INetworkRecommendationProvider.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            if (NetworkScoreService.DBG) {
                Log.d(
                        "NetworkScoreService",
                        "ScoringServiceConnection, disconnected: "
                                + componentName.flattenToString());
            }
            this.mConnected = false;
            this.mRecommendationProvider = null;
        }

        public void unbind(Context context) {
            try {
                if (this.mBound) {
                    this.mBound = false;
                    context.unbindService(this);
                    if (NetworkScoreService.DBG) {
                        Log.d("NetworkScoreService", "ScoringServiceConnection unbound.");
                    }
                }
            } catch (RuntimeException e) {
                Log.e("NetworkScoreService", "Unbind failed.", e);
            }
            this.mConnected = false;
            this.mRecommendationProvider = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1 && i != 2) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i, "Unknown message: ", "NetworkScoreService");
            } else {
                boolean z = NetworkScoreService.DBG;
                NetworkScoreService.this.refreshBinding();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WifiInfoSupplier implements Supplier {
        public final /* synthetic */ int $r8$classId;
        public final Context mContext;

        public /* synthetic */ WifiInfoSupplier(Context context, int i) {
            this.$r8$classId = i;
            this.mContext = context;
        }

        @Override // java.util.function.Supplier
        public final Object get() {
            switch (this.$r8$classId) {
                case 0:
                    WifiManager wifiManager =
                            (WifiManager) this.mContext.getSystemService(WifiManager.class);
                    if (wifiManager != null) {
                        return wifiManager.getConnectionInfo();
                    }
                    Log.w(
                            "NetworkScoreService",
                            "WifiManager is null, failed to return the WifiInfo.");
                    return null;
                default:
                    WifiScanner wifiScanner =
                            (WifiScanner) this.mContext.getSystemService(WifiScanner.class);
                    if (wifiScanner != null) {
                        return wifiScanner.getSingleScanResults();
                    }
                    Log.w(
                            "NetworkScoreService",
                            "WifiScanner is null, failed to return scan results.");
                    return Collections.emptyList();
            }
        }
    }

    static {
        boolean z = Build.IS_DEBUGGABLE;
        boolean z2 = false;
        DBG = z && Log.isLoggable("NetworkScoreService", 3);
        if (z && Log.isLoggable("NetworkScoreService", 2)) {
            z2 = true;
        }
        VERBOSE = z2;
    }

    public NetworkScoreService(
            Context context,
            NetworkScorerAppManager networkScorerAppManager,
            Function function,
            Looper looper) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.NetworkScoreService.1
                    public final /* synthetic */ NetworkScoreService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                if (NetworkScoreService.DBG) {
                                    NetworkScoreService$$ExternalSyntheticOutline0.m(
                                            intExtra,
                                            "Received ",
                                            action,
                                            " for userId ",
                                            "NetworkScoreService");
                                }
                                if (intExtra != -10000
                                        && "android.intent.action.USER_UNLOCKED".equals(action)) {
                                    this.this$0.onUserUnlocked(intExtra);
                                    break;
                                }
                                break;
                            default:
                                if ("android.location.MODE_CHANGED".equals(intent.getAction())) {
                                    this.this$0.refreshBinding();
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.NetworkScoreService.1
                    public final /* synthetic */ NetworkScoreService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                if (NetworkScoreService.DBG) {
                                    NetworkScoreService$$ExternalSyntheticOutline0.m(
                                            intExtra,
                                            "Received ",
                                            action,
                                            " for userId ",
                                            "NetworkScoreService");
                                }
                                if (intExtra != -10000
                                        && "android.intent.action.USER_UNLOCKED".equals(action)) {
                                    this.this$0.onUserUnlocked(intExtra);
                                    break;
                                }
                                break;
                            default:
                                if ("android.location.MODE_CHANGED".equals(intent.getAction())) {
                                    this.this$0.refreshBinding();
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mContext = context;
        this.mNetworkScorerAppManager = networkScorerAppManager;
        this.mScoreCaches = new ArrayMap();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_UNLOCKED");
        UserHandle userHandle = UserHandle.SYSTEM;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, null);
        ServiceHandler serviceHandler = new ServiceHandler(looper);
        context.registerReceiverAsUser(
                broadcastReceiver2,
                userHandle,
                new IntentFilter("android.location.MODE_CHANGED"),
                null,
                serviceHandler);
        this.mRecommendationSettingsObserver =
                new DispatchingContentObserver(context, serviceHandler);
        this.mServiceConnProducer = function;
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("use_open_wifi_package"),
                        false,
                        new ContentObserver(serviceHandler) { // from class:
                            // com.android.server.NetworkScoreService.3
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri, int i3) {
                                if (Settings.Global.getUriFor("use_open_wifi_package")
                                        .equals(uri)) {
                                    String string =
                                            Settings.Global.getString(
                                                    NetworkScoreService.this.mContext
                                                            .getContentResolver(),
                                                    "use_open_wifi_package");
                                    if (TextUtils.isEmpty(string)) {
                                        return;
                                    }
                                    DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                                            LegacyPermissionManagerService.this
                                                    .mDefaultPermissionGrantPolicy;
                                    defaultPermissionGrantPolicy.getClass();
                                    Log.i(
                                            "DefaultPermGrantPolicy",
                                            "Granting permissions to default Use Open WiFi app for"
                                                + " user:"
                                                    + i3);
                                    Set[] setArr = {
                                        DefaultPermissionGrantPolicy.ALWAYS_LOCATION_PERMISSIONS
                                    };
                                    DefaultPermissionGrantPolicy.AnonymousClass1 anonymousClass1 =
                                            defaultPermissionGrantPolicy.NO_PM_CACHE;
                                    defaultPermissionGrantPolicy.grantPermissionsToPackage(
                                            anonymousClass1,
                                            anonymousClass1.getPackageInfo(string),
                                            i3,
                                            false,
                                            true,
                                            setArr);
                                }
                            }
                        });
        LegacyPermissionManagerService.Internal internal =
                (LegacyPermissionManagerService.Internal)
                        LocalServices.getService(LegacyPermissionManagerService.Internal.class);
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider =
                        new LegacyPermissionManagerInternal$PackagesProvider() { // from class:
                                                                                 // com.android.server.NetworkScoreService$$ExternalSyntheticLambda1
                            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                            public final String[] getPackages(int i3) {
                                String string =
                                        Settings.Global.getString(
                                                NetworkScoreService.this.mContext
                                                        .getContentResolver(),
                                                "use_open_wifi_package");
                                if (TextUtils.isEmpty(string)) {
                                    return null;
                                }
                                return new String[] {string};
                            }
                        };
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy.mLock) {
            defaultPermissionGrantPolicy.mUseOpenWifiAppPackagesProvider =
                    legacyPermissionManagerInternal$PackagesProvider;
        }
    }

    public static void sendCacheUpdateCallback(BiConsumer biConsumer, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            RemoteCallbackList remoteCallbackList = (RemoteCallbackList) it.next();
            synchronized (remoteCallbackList) {
                try {
                    int beginBroadcast = remoteCallbackList.beginBroadcast();
                    for (int i = 0; i < beginBroadcast; i++) {
                        try {
                            biConsumer.accept(
                                    remoteCallbackList.getBroadcastItem(i),
                                    remoteCallbackList.getBroadcastCookie(i));
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void bindToScoringServiceIfNeeded(NetworkScorerAppData networkScorerAppData) {
        if (DBG) {
            Log.d(
                    "NetworkScoreService",
                    "bindToScoringServiceIfNeeded(" + networkScorerAppData + ")");
        }
        if (networkScorerAppData == null) {
            unbindFromScoringServiceIfNeeded();
            return;
        }
        synchronized (this.mServiceConnectionLock) {
            try {
                ScoringServiceConnection scoringServiceConnection = this.mServiceConnection;
                if (scoringServiceConnection != null
                        && !scoringServiceConnection.getAppData().equals(networkScorerAppData)) {
                    unbindFromScoringServiceIfNeeded();
                }
                if (this.mServiceConnection == null) {
                    this.mServiceConnection =
                            (ScoringServiceConnection)
                                    this.mServiceConnProducer.apply(networkScorerAppData);
                }
                this.mServiceConnection.bind(this.mContext);
            } finally {
            }
        }
    }

    public final void clearInternal() {
        ArrayList arrayList;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        synchronized (this.mScoreCaches) {
            arrayList = new ArrayList(((ArrayMap) this.mScoreCaches).values());
        }
        sendCacheUpdateCallback(anonymousClass4, arrayList);
    }

    public final boolean clearScores() {
        int callingUid = INetworkScoreService.Stub.getCallingUid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES")
                        != 0
                && !isCallerActiveScorer(callingUid)) {
            throw new SecurityException(
                    "Caller is neither the system process or the active network scorer.");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            clearInternal();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void disableScoring() {
        int callingUid = INetworkScoreService.Stub.getCallingUid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES")
                        != 0
                && !isCallerActiveScorer(callingUid)) {
            throw new SecurityException(
                    "Caller is neither the system process or the active network scorer.");
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "NetworkScoreService", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NetworkScorerAppData activeScorer = this.mNetworkScorerAppManager.getActiveScorer();
                if (activeScorer == null) {
                    printWriter.println("Scoring is disabled.");
                    return;
                }
                printWriter.println("Current scorer: " + activeScorer);
                synchronized (this.mServiceConnectionLock) {
                    ScoringServiceConnection scoringServiceConnection = this.mServiceConnection;
                    if (scoringServiceConnection != null) {
                        StringBuilder sb = new StringBuilder("ScoringServiceConnection: ");
                        sb.append(
                                scoringServiceConnection.mAppData
                                        .getRecommendationServiceComponent());
                        sb.append(", bound: ");
                        sb.append(scoringServiceConnection.mBound);
                        sb.append(", connected: ");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                sb, scoringServiceConnection.mConnected, printWriter);
                    } else {
                        printWriter.println("ScoringServiceConnection: null");
                    }
                }
                printWriter.flush();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final NetworkScorerAppData getActiveScorer() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.REQUEST_NETWORK_SCORES",
                "Caller must be granted REQUEST_NETWORK_SCORES.");
        return this.mNetworkScorerAppManager.getActiveScorer();
    }

    public final String getActiveScorerPackage() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES")
                        != 0
                && this.mContext.checkCallingOrSelfPermission("android.permission.SCORE_NETWORKS")
                        != 0) {
            throw new SecurityException(
                    "Caller is neither the system process or a network scorer.");
        }
        NetworkScorerAppData activeScorer = this.mNetworkScorerAppManager.getActiveScorer();
        if (activeScorer == null) {
            return null;
        }
        return activeScorer.getRecommendationServicePackageName();
    }

    public final List getAllValidScorers() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.REQUEST_NETWORK_SCORES",
                "Caller must be granted REQUEST_NETWORK_SCORES.");
        return this.mNetworkScorerAppManager.getAllValidScorers();
    }

    public final boolean isCallerActiveScorer(int i) {
        boolean z;
        synchronized (this.mServiceConnectionLock) {
            try {
                ScoringServiceConnection scoringServiceConnection = this.mServiceConnection;
                z =
                        scoringServiceConnection != null
                                && scoringServiceConnection.getAppData().packageUid == i;
            } finally {
            }
        }
        return z;
    }

    public void onUserUnlocked(int i) {
        if (DBG) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "onUserUnlocked(", ")", "NetworkScoreService");
        }
        refreshBinding();
    }

    public final void refreshBinding() {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkScoreService", "refreshBinding()");
        }
        this.mNetworkScorerAppManager.updateState();
        this.mNetworkScorerAppManager.migrateNetworkScorerAppSettingIfNeeded();
        if (z) {
            Log.d("NetworkScoreService", "registerPackageMonitorIfNeeded()");
        }
        NetworkScorerAppData activeScorer = this.mNetworkScorerAppManager.getActiveScorer();
        synchronized (this.mPackageMonitorLock) {
            try {
                if (this.mPackageMonitor != null) {
                    if (activeScorer != null) {
                        if (!activeScorer
                                .getRecommendationServicePackageName()
                                .equals(this.mPackageMonitor.mPackageToWatch)) {}
                    }
                    if (z) {
                        Log.d(
                                "NetworkScoreService",
                                "Unregistering package monitor for "
                                        + this.mPackageMonitor.mPackageToWatch);
                    }
                    this.mPackageMonitor.unregister();
                    this.mPackageMonitor = null;
                }
                if (activeScorer != null && this.mPackageMonitor == null) {
                    NetworkScorerPackageMonitor networkScorerPackageMonitor =
                            new NetworkScorerPackageMonitor(
                                    activeScorer.getRecommendationServicePackageName());
                    this.mPackageMonitor = networkScorerPackageMonitor;
                    networkScorerPackageMonitor.register(
                            this.mContext, (Looper) null, UserHandle.SYSTEM, false);
                    if (z) {
                        Log.d(
                                "NetworkScoreService",
                                "Registered package monitor for "
                                        + this.mPackageMonitor.mPackageToWatch);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            Log.d("NetworkScoreService", "bindToScoringServiceIfNeeded");
        }
        bindToScoringServiceIfNeeded(this.mNetworkScorerAppManager.getActiveScorer());
    }

    public final void registerNetworkScoreCache(
            int i, INetworkScoreCache iNetworkScoreCache, int i2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.REQUEST_NETWORK_SCORES",
                "Caller must be granted REQUEST_NETWORK_SCORES.");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mScoreCaches) {
                try {
                    RemoteCallbackList remoteCallbackList =
                            (RemoteCallbackList)
                                    ((ArrayMap) this.mScoreCaches).get(Integer.valueOf(i));
                    if (remoteCallbackList == null) {
                        remoteCallbackList = new RemoteCallbackList();
                        ((ArrayMap) this.mScoreCaches).put(Integer.valueOf(i), remoteCallbackList);
                    }
                    if (!remoteCallbackList.register(iNetworkScoreCache, Integer.valueOf(i2))) {
                        if (remoteCallbackList.getRegisteredCallbackCount() == 0) {
                            ((ArrayMap) this.mScoreCaches).remove(Integer.valueOf(i));
                        }
                        if (Log.isLoggable("NetworkScoreService", 2)) {
                            Log.v(
                                    "NetworkScoreService",
                                    "Unable to register NetworkScoreCache for type " + i);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean requestScores(NetworkKey[] networkKeyArr) {
        INetworkRecommendationProvider recommendationProvider;
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.REQUEST_NETWORK_SCORES",
                "Caller must be granted REQUEST_NETWORK_SCORES.");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mServiceConnectionLock) {
                try {
                    ScoringServiceConnection scoringServiceConnection = this.mServiceConnection;
                    recommendationProvider =
                            scoringServiceConnection != null
                                    ? scoringServiceConnection.getRecommendationProvider()
                                    : null;
                } finally {
                }
            }
            if (recommendationProvider != null) {
                try {
                    recommendationProvider.requestScores(networkKeyArr);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                } catch (RemoteException e) {
                    Log.w("NetworkScoreService", "Failed to request scores.", e);
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setActiveScorer(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES")
                        == 0
                || this.mContext.checkCallingOrSelfPermission("android.permission.SCORE_NETWORKS")
                        == 0) {
            return this.mNetworkScorerAppManager.setActiveScorer(str);
        }
        throw new SecurityException("Caller is neither the system process or a network scorer.");
    }

    public final void unbindFromScoringServiceIfNeeded() {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkScoreService", "unbindFromScoringServiceIfNeeded");
        }
        synchronized (this.mServiceConnectionLock) {
            try {
                ScoringServiceConnection scoringServiceConnection = this.mServiceConnection;
                if (scoringServiceConnection != null) {
                    scoringServiceConnection.unbind(this.mContext);
                    if (z) {
                        Log.d(
                                "NetworkScoreService",
                                "Disconnected from: "
                                        + this.mServiceConnection
                                                .getAppData()
                                                .getRecommendationServiceComponent());
                    }
                }
                this.mServiceConnection = null;
            } catch (Throwable th) {
                throw th;
            }
        }
        clearInternal();
    }

    public final void unregisterNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.REQUEST_NETWORK_SCORES",
                "Caller must be granted REQUEST_NETWORK_SCORES.");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mScoreCaches) {
                try {
                    RemoteCallbackList remoteCallbackList =
                            (RemoteCallbackList)
                                    ((ArrayMap) this.mScoreCaches).get(Integer.valueOf(i));
                    if (remoteCallbackList != null
                            && remoteCallbackList.unregister(iNetworkScoreCache)) {
                        if (remoteCallbackList.getRegisteredCallbackCount() == 0) {
                            ((ArrayMap) this.mScoreCaches).remove(Integer.valueOf(i));
                        }
                    }
                    if (Log.isLoggable("NetworkScoreService", 2)) {
                        Log.v(
                                "NetworkScoreService",
                                "Unable to unregister NetworkScoreCache for type " + i);
                    }
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateScores(ScoredNetwork[] scoredNetworkArr) {
        RemoteCallbackList remoteCallbackList;
        if (!isCallerActiveScorer(INetworkScoreService.Stub.getCallingUid())) {
            throw new SecurityException(
                    "Caller with UID "
                            + INetworkScoreService.Stub.getCallingUid()
                            + " is not the active scorer.");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayMap arrayMap = new ArrayMap();
            for (ScoredNetwork scoredNetwork : scoredNetworkArr) {
                List list = (List) arrayMap.get(Integer.valueOf(scoredNetwork.networkKey.type));
                if (list == null) {
                    list = new ArrayList();
                    arrayMap.put(Integer.valueOf(scoredNetwork.networkKey.type), list);
                }
                list.add(scoredNetwork);
            }
            Iterator it = arrayMap.entrySet().iterator();
            while (true) {
                boolean z = true;
                if (!it.hasNext()) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                Map.Entry entry = (Map.Entry) it.next();
                synchronized (this.mScoreCaches) {
                    try {
                        remoteCallbackList =
                                (RemoteCallbackList)
                                        ((ArrayMap) this.mScoreCaches).get(entry.getKey());
                        if (remoteCallbackList != null
                                && remoteCallbackList.getRegisteredCallbackCount() != 0) {
                            z = false;
                        }
                    } finally {
                    }
                }
                if (!z) {
                    sendCacheUpdateCallback(
                            new FilteringCacheUpdatingConsumer(
                                    this.mContext,
                                    (List) entry.getValue(),
                                    ((Integer) entry.getKey()).intValue(),
                                    null,
                                    null),
                            Collections.singleton(remoteCallbackList));
                } else if (Log.isLoggable("NetworkScoreService", 2)) {
                    Log.v(
                            "NetworkScoreService",
                            "No scorer registered for type " + entry.getKey() + ", discarding");
                }
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
