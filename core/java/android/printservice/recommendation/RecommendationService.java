package android.printservice.recommendation;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

@SystemApi
public abstract class RecommendationService extends Service {
    private static final String LOG_TAG = "PrintServiceRecS";
    public static final String SERVICE_INTERFACE =
            "android.printservice.recommendation.RecommendationService";
    private IRecommendationServiceCallbacks mCallbacks;
    private Handler mHandler;

    public abstract void onConnected();

    public abstract void onDisconnected();

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mHandler = new MyHandler();
    }

    public final void updateRecommendations(List<RecommendationInfo> recommendations) {
        this.mHandler.obtainMessage(3, recommendations).sendToTarget();
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IRecommendationService
                .Stub() { // from class: android.printservice.recommendation.RecommendationService.1
            @Override // android.printservice.recommendation.IRecommendationService
            public void registerCallbacks(IRecommendationServiceCallbacks callbacks) {
                if (callbacks != null) {
                    RecommendationService.this.mHandler.obtainMessage(1, callbacks).sendToTarget();
                } else {
                    RecommendationService.this.mHandler.obtainMessage(2).sendToTarget();
                }
            }
        };
    }

    private class MyHandler extends Handler {
        static final int MSG_CONNECT = 1;
        static final int MSG_DISCONNECT = 2;
        static final int MSG_UPDATE = 3;

        MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RecommendationService.this.mCallbacks =
                            (IRecommendationServiceCallbacks) msg.obj;
                    RecommendationService.this.onConnected();
                    break;
                case 2:
                    RecommendationService.this.onDisconnected();
                    RecommendationService.this.mCallbacks = null;
                    break;
                case 3:
                    if (RecommendationService.this.mCallbacks != null) {
                        try {
                            RecommendationService.this.mCallbacks.onRecommendationsUpdated(
                                    (List) msg.obj);
                            break;
                        } catch (RemoteException | NullPointerException e) {
                            Log.e(
                                    RecommendationService.LOG_TAG,
                                    "Could not update recommended services",
                                    e);
                            return;
                        }
                    }
                    break;
            }
        }
    }
}
