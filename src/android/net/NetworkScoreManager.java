package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.INetworkScoreCache;
import android.net.INetworkScoreService;
import android.net.NetworkScoreManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public class NetworkScoreManager {

    @Deprecated
    public static final String ACTION_CHANGE_ACTIVE = "android.net.scoring.CHANGE_ACTIVE";
    public static final String ACTION_CUSTOM_ENABLE = "android.net.scoring.CUSTOM_ENABLE";
    public static final String ACTION_RECOMMEND_NETWORKS = "android.net.action.RECOMMEND_NETWORKS";
    public static final String ACTION_SCORER_CHANGED = "android.net.scoring.SCORER_CHANGED";

    @Deprecated
    public static final String ACTION_SCORE_NETWORKS = "android.net.scoring.SCORE_NETWORKS";

    @Deprecated
    public static final String EXTRA_NETWORKS_TO_SCORE = "networksToScore";
    public static final String EXTRA_NEW_SCORER = "newScorer";

    @Deprecated
    public static final String EXTRA_PACKAGE_NAME = "packageName";
    public static final String NETWORK_AVAILABLE_NOTIFICATION_CHANNEL_ID_META_DATA = "android.net.wifi.notification_channel_id_network_available";
    public static final int RECOMMENDATIONS_ENABLED_FORCED_OFF = -1;
    public static final int RECOMMENDATIONS_ENABLED_OFF = 0;
    public static final int RECOMMENDATIONS_ENABLED_ON = 1;
    public static final String RECOMMENDATION_SERVICE_LABEL_META_DATA = "android.net.scoring.recommendation_service_label";
    public static final int SCORE_FILTER_CURRENT_NETWORK = 1;
    public static final int SCORE_FILTER_NONE = 0;
    public static final int SCORE_FILTER_SCAN_RESULTS = 2;
    private static final String TAG = "NetworkScoreManager";
    public static final String USE_OPEN_WIFI_PACKAGE_META_DATA = "android.net.wifi.use_open_wifi_package";
    private final Context mContext;
    private final INetworkScoreService mService = INetworkScoreService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.NETWORK_SCORE_SERVICE));

    @SystemApi
    public static abstract class NetworkScoreCallback {
        public abstract void onScoresInvalidated();

        public abstract void onScoresUpdated(Collection<ScoredNetwork> collection);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecommendationsEnabledSetting {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScoreUpdateFilter {
    }

    public NetworkScoreManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public String getActiveScorerPackage() {
        try {
            return this.mService.getActiveScorerPackage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NetworkScorerAppData getActiveScorer() {
        try {
            return this.mService.getActiveScorer();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<NetworkScorerAppData> getAllValidScorers() {
        try {
            return this.mService.getAllValidScorers();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateScores(ScoredNetwork[] scoredNetworkArr) throws SecurityException {
        try {
            return this.mService.updateScores(scoredNetworkArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearScores() throws SecurityException {
        try {
            return this.mService.clearScores();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setActiveScorer(String str) throws SecurityException {
        try {
            return this.mService.setActiveScorer(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableScoring() throws SecurityException {
        try {
            this.mService.disableScoring();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestScores(NetworkKey[] networkKeyArr) throws SecurityException {
        try {
            return this.mService.requestScores(networkKeyArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean requestScores(Collection<NetworkKey> collection) throws SecurityException {
        return requestScores((NetworkKey[]) collection.toArray(new NetworkKey[0]));
    }

    @Deprecated
    public void registerNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) {
        registerNetworkScoreCache(i, iNetworkScoreCache, 0);
    }

    public void registerNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache, int i2) {
        try {
            this.mService.registerNetworkScoreCache(i, iNetworkScoreCache, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) {
        try {
            this.mService.unregisterNetworkScoreCache(i, iNetworkScoreCache);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class NetworkScoreCallbackProxy extends INetworkScoreCache.Stub {
        private final NetworkScoreCallback mCallback;
        private final Executor mExecutor;

        NetworkScoreCallbackProxy(NetworkScoreManager networkScoreManager, Executor executor, NetworkScoreCallback networkScoreCallback) {
            this.mExecutor = executor;
            this.mCallback = networkScoreCallback;
        }

        @Override // android.net.INetworkScoreCache
        public void updateScores(final List<ScoredNetwork> list) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.NetworkScoreManager$NetworkScoreCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkScoreManager.NetworkScoreCallbackProxy.this.lambda$updateScores$0(list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateScores$0(List list) {
            this.mCallback.onScoresUpdated(list);
        }

        @Override // android.net.INetworkScoreCache
        public void clearScores() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.NetworkScoreManager$NetworkScoreCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkScoreManager.NetworkScoreCallbackProxy.this.lambda$clearScores$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearScores$1() {
            this.mCallback.onScoresInvalidated();
        }
    }

    @SystemApi
    public void registerNetworkScoreCallback(int i, int i2, Executor executor, NetworkScoreCallback networkScoreCallback) throws SecurityException {
        if (networkScoreCallback == null || executor == null) {
            throw new IllegalArgumentException("callback / executor cannot be null");
        }
        Log.v(TAG, "registerNetworkScoreCallback: callback=" + networkScoreCallback + ", executor=" + executor);
        registerNetworkScoreCache(i, new NetworkScoreCallbackProxy(this, executor, networkScoreCallback), i2);
    }

    public boolean isCallerActiveScorer(int i) {
        try {
            return this.mService.isCallerActiveScorer(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
