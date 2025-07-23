package android.app.usage;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.usage.ICacheQuotaService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallback;
import android.util.Log;
import android.util.Pair;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public abstract class CacheQuotaService extends Service {
    public static final String REQUEST_LIST_KEY = "requests";
    public static final String SERVICE_INTERFACE = "android.app.usage.CacheQuotaService";
    private static final String TAG = "CacheQuotaService";
    private Handler mHandler;
    private CacheQuotaServiceWrapper mWrapper;

    public abstract List<CacheQuotaHint> onComputeCacheQuotaHints(List<CacheQuotaHint> list);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new CacheQuotaServiceWrapper();
        this.mHandler = new ServiceHandler(getMainLooper());
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    private final class CacheQuotaServiceWrapper extends ICacheQuotaService.Stub {
        private CacheQuotaServiceWrapper() {
        }

        @Override // android.app.usage.ICacheQuotaService
        public void computeCacheQuotaHints(RemoteCallback remoteCallback, List<CacheQuotaHint> list) {
            CacheQuotaService.this.mHandler.sendMessage(CacheQuotaService.this.mHandler.obtainMessage(1, Pair.create(remoteCallback, list)));
        }
    }

    private final class ServiceHandler extends Handler {
        public static final int MSG_SEND_LIST = 1;

        public ServiceHandler(Looper looper) {
            super(looper, null, true);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Pair pair = (Pair) message.obj;
                List<CacheQuotaHint> onComputeCacheQuotaHints = CacheQuotaService.this.onComputeCacheQuotaHints((List) pair.second);
                Bundle bundle = new Bundle();
                bundle.putParcelableList(CacheQuotaService.REQUEST_LIST_KEY, onComputeCacheQuotaHints);
                ((RemoteCallback) pair.first).sendResult(bundle);
                return;
            }
            Log.w(CacheQuotaService.TAG, "Handling unknown message: " + i);
        }
    }
}
