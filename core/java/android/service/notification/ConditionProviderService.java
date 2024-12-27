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
import android.util.Log;

@Deprecated
/* loaded from: classes3.dex */
public abstract class ConditionProviderService extends Service {

    @Deprecated
    public static final String EXTRA_RULE_ID = "android.service.notification.extra.RULE_ID";

    @Deprecated
    public static final String META_DATA_CONFIGURATION_ACTIVITY =
            "android.service.zen.automatic.configurationActivity";

    @Deprecated
    public static final String META_DATA_RULE_INSTANCE_LIMIT =
            "android.service.zen.automatic.ruleInstanceLimit";

    @Deprecated
    public static final String META_DATA_RULE_TYPE = "android.service.zen.automatic.ruleType";

    public static final String SERVICE_INTERFACE =
            "android.service.notification.ConditionProviderService";
    private final String TAG =
            ConditionProviderService.class.getSimpleName()
                    + NavigationBarInflaterView.SIZE_MOD_START
                    + getClass().getSimpleName()
                    + NavigationBarInflaterView.SIZE_MOD_END;
    private final H mHandler = new H();
    boolean mIsConnected;
    private INotificationManager mNoMan;
    private Provider mProvider;

    public abstract void onConnected();

    public abstract void onSubscribe(Uri uri);

    public abstract void onUnsubscribe(Uri uri);

    public void onRequestConditions(int relevance) {}

    private final INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan =
                    INotificationManager.Stub.asInterface(
                            ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    public static final void requestRebind(ComponentName componentName) {
        INotificationManager noMan =
                INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        try {
            noMan.requestBindProvider(componentName);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public final void requestUnbind() {
        INotificationManager noMan = getNotificationInterface();
        try {
            noMan.requestUnbindProvider(this.mProvider);
            this.mIsConnected = false;
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
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
    public final void notifyConditions(Condition... conditions) {
        if (!isBound() || conditions == null) {
            return;
        }
        try {
            getNotificationInterface()
                    .notifyConditions(getPackageName(), this.mProvider, conditions);
        } catch (RemoteException ex) {
            Log.v(this.TAG, "Unable to contact notification manager", ex);
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
        private Provider() {}

        @Override // android.service.notification.IConditionProvider
        public void onConnected() {
            ConditionProviderService.this.mIsConnected = true;
            ConditionProviderService.this.mHandler.obtainMessage(1).sendToTarget();
        }

        @Override // android.service.notification.IConditionProvider
        public void onSubscribe(Uri conditionId) {
            ConditionProviderService.this.mHandler.obtainMessage(3, conditionId).sendToTarget();
        }

        @Override // android.service.notification.IConditionProvider
        public void onUnsubscribe(Uri conditionId) {
            ConditionProviderService.this.mHandler.obtainMessage(4, conditionId).sendToTarget();
        }
    }

    private final class H extends Handler {
        private static final int ON_CONNECTED = 1;
        private static final int ON_SUBSCRIBE = 3;
        private static final int ON_UNSUBSCRIBE = 4;

        private H() {}

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String name = null;
            if (!ConditionProviderService.this.mIsConnected) {
                return;
            }
            try {
                switch (msg.what) {
                    case 1:
                        name = "onConnected";
                        ConditionProviderService.this.onConnected();
                        break;
                    case 3:
                        name = "onSubscribe";
                        ConditionProviderService.this.onSubscribe((Uri) msg.obj);
                        break;
                    case 4:
                        name = "onUnsubscribe";
                        ConditionProviderService.this.onUnsubscribe((Uri) msg.obj);
                        break;
                }
            } catch (Throwable t) {
                Log.w(ConditionProviderService.this.TAG, "Error running " + name, t);
            }
        }
    }
}
