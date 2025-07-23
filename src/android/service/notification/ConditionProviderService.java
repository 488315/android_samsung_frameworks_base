package android.service.notification;

import android.app.INotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.notification.IConditionProvider;
import android.util.Log;

@Deprecated
/* loaded from: classes3.dex */
public abstract class ConditionProviderService extends Service {

    @Deprecated
    public static final String EXTRA_RULE_ID = "android.service.notification.extra.RULE_ID";

    @Deprecated
    public static final String META_DATA_CONFIGURATION_ACTIVITY = "android.service.zen.automatic.configurationActivity";

    @Deprecated
    public static final String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";

    @Deprecated
    public static final String META_DATA_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final String SERVICE_INTERFACE = "android.service.notification.ConditionProviderService";
    private final String TAG = "ConditionProviderService[" + getClass().getSimpleName() + NavigationBarInflaterView.SIZE_MOD_END;
    private final H mHandler = new H();
    boolean mIsConnected;
    private INotificationManager mNoMan;
    private Provider mProvider;

    public abstract void onConnected();

    public void onRequestConditions(int i) {
    }

    public abstract void onSubscribe(Uri uri);

    public abstract void onUnsubscribe(Uri uri);

    private final INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    public static final void requestRebind(ComponentName componentName) {
        try {
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification")).requestBindProvider(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void requestUnbind() {
        try {
            getNotificationInterface().requestUnbindProvider(this.mProvider);
            this.mIsConnected = false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public final void notifyCondition(Condition condition) {
        if (condition == null) {
            return;
        }
        notifyConditions(condition);
    }

    @Deprecated
    public final void notifyConditions(Condition... conditionArr) {
        if (!isBound() || conditionArr == null) {
            return;
        }
        try {
            getNotificationInterface().notifyConditions(getPackageName(), this.mProvider, conditionArr);
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (this.mProvider == null) {
            this.mProvider = new Provider();
        }
        return this.mProvider;
    }

    public boolean isBound() {
        if (!this.mIsConnected) {
            Log.w(this.TAG, "Condition provider service not yet bound.");
        }
        return this.mIsConnected;
    }

    private final class Provider extends IConditionProvider.Stub {
        private Provider() {
        }

        @Override // android.service.notification.IConditionProvider
        public void onConnected() {
            ConditionProviderService.this.mIsConnected = true;
            ConditionProviderService.this.mHandler.obtainMessage(1).sendToTarget();
        }

        @Override // android.service.notification.IConditionProvider
        public void onSubscribe(Uri uri) {
            ConditionProviderService.this.mHandler.obtainMessage(3, uri).sendToTarget();
        }

        @Override // android.service.notification.IConditionProvider
        public void onUnsubscribe(Uri uri) {
            ConditionProviderService.this.mHandler.obtainMessage(4, uri).sendToTarget();
        }
    }

    private final class H extends Handler {
        private static final int ON_CONNECTED = 1;
        private static final int ON_SUBSCRIBE = 3;
        private static final int ON_UNSUBSCRIBE = 4;

        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (ConditionProviderService.this.mIsConnected) {
                try {
                    int i = message.what;
                    if (i == 1) {
                        ConditionProviderService.this.onConnected();
                    } else if (i == 3) {
                        ConditionProviderService.this.onSubscribe((Uri) message.obj);
                    } else {
                        if (i != 4) {
                            return;
                        }
                        ConditionProviderService.this.onUnsubscribe((Uri) message.obj);
                    }
                } catch (Throwable th) {
                    Log.w(ConditionProviderService.this.TAG, "Error running " + ((String) null), th);
                }
            }
        }
    }
}
