package android.net;

import android.Manifest;
import android.annotation.SystemApi;
import android.content.Context;
import android.net.INetworkRecommendationProvider;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public abstract class NetworkRecommendationProvider {
    private static final String TAG = "NetworkRecProvider";
    private static final boolean VERBOSE;
    private final IBinder mService;

    public abstract void onRequestScores(NetworkKey[] networkKeyArr);

    static {
        VERBOSE = Build.IS_DEBUGGABLE && Log.isLoggable(TAG, 2);
    }

    public NetworkRecommendationProvider(Context context, Executor executor) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(executor);
        this.mService = new ServiceWrapper(context, executor);
    }

    public final IBinder getBinder() {
        return this.mService;
    }

    private final class ServiceWrapper extends INetworkRecommendationProvider.Stub {
        private final Context mContext;
        private final Executor mExecutor;
        private final Handler mHandler = null;

        ServiceWrapper(Context context, Executor executor) {
            this.mContext = context;
            this.mExecutor = executor;
        }

        @Override // android.net.INetworkRecommendationProvider
        public void requestScores(final NetworkKey[] networkKeyArr) throws RemoteException {
            enforceCallingPermission();
            if (networkKeyArr == null || networkKeyArr.length <= 0) {
                return;
            }
            execute(new Runnable() { // from class: android.net.NetworkRecommendationProvider.ServiceWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    NetworkRecommendationProvider.this.onRequestScores(networkKeyArr);
                }
            });
        }

        private void execute(Runnable runnable) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(runnable);
            } else {
                this.mHandler.post(runnable);
            }
        }

        private void enforceCallingPermission() {
            Context context = this.mContext;
            if (context != null) {
                context.enforceCallingOrSelfPermission(Manifest.permission.REQUEST_NETWORK_SCORES, "Permission denied.");
            }
        }
    }
}
