package android.appwidget;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.appwidget.IAppWidgetService;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes.dex */
public class AppWidgetHost {
    static final int HANDLE_APP_WIDGET_REMOVED = 5;
    static final int HANDLE_PROVIDERS_CHANGED = 3;
    static final int HANDLE_PROVIDER_CHANGED = 2;
    static final int HANDLE_UPDATE = 1;
    static final int HANDLE_VIEW_DATA_CHANGED = 4;
    static final int HANDLE_VIEW_UPDATE_DEFERRED = 6;
    private static final String TAG = "AppWidgetHost";
    static IAppWidgetService sService = null;
    static boolean sServiceInitialized = false;
    static final Object sServiceLock = new Object();
    private final Callbacks mCallbacks;
    private String mContextOpPackageName;
    private DisplayMetrics mDisplayMetrics;
    private final Handler mHandler;
    private final int mHostId;
    private RemoteViews.InteractionHandler mInteractionHandler;
    private final SparseArray<AppWidgetHostListener> mListeners;

    public void onAppWidgetRemoved(int i) {
    }

    protected void onProvidersChanged() {
    }

    static class Callbacks extends IAppWidgetHost.Stub {
        private final WeakReference<Handler> mWeakHandler;

        public Callbacks(Handler handler) {
            this.mWeakHandler = new WeakReference<>(handler);
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidget(int i, RemoteViews remoteViews) {
            if (isLocalBinder() && remoteViews != null) {
                remoteViews = remoteViews.mo465clone();
            }
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                Log.d(AppWidgetHost.TAG, "Handler is not available.");
            } else {
                handler.obtainMessage(1, i, 0, remoteViews).sendToTarget();
            }
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providerChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) {
            if (isLocalBinder() && appWidgetProviderInfo != null) {
                appWidgetProviderInfo = appWidgetProviderInfo.m865clone();
            }
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(2, i, 0, appWidgetProviderInfo).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void appWidgetRemoved(int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(5, i, 0).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providersChanged() {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(3).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void viewDataChanged(int i, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(4, i, i2).sendToTarget();
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidgetDeferred(int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(6, i, 0, null).sendToTarget();
        }

        private static boolean isLocalBinder() {
            return Process.myPid() == Binder.getCallingPid();
        }
    }

    class UpdateHandler extends Handler {
        public UpdateHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    AppWidgetHost.this.updateAppWidgetView(message.arg1, (RemoteViews) message.obj);
                    break;
                case 2:
                    AppWidgetHost.this.onProviderChanged(message.arg1, (AppWidgetProviderInfo) message.obj);
                    break;
                case 3:
                    AppWidgetHost.this.onProvidersChanged();
                    break;
                case 4:
                    AppWidgetHost.this.viewDataChanged(message.arg1, message.arg2);
                    break;
                case 5:
                    AppWidgetHost.this.dispatchOnAppWidgetRemoved(message.arg1);
                    break;
                case 6:
                    AppWidgetHost.this.updateAppWidgetDeferred(message.arg1);
                    break;
            }
        }
    }

    public AppWidgetHost(Context context, int i) {
        this(context, i, null, context.getMainLooper());
    }

    private AppWidgetHostListener getListener(int i) {
        AppWidgetHostListener appWidgetHostListener;
        synchronized (this.mListeners) {
            appWidgetHostListener = this.mListeners.get(i);
        }
        return appWidgetHostListener;
    }

    public AppWidgetHost(Context context, int i, RemoteViews.InteractionHandler interactionHandler, Looper looper) {
        this.mListeners = new SparseArray<>();
        this.mContextOpPackageName = context.getOpPackageName();
        this.mHostId = i;
        this.mInteractionHandler = interactionHandler;
        UpdateHandler updateHandler = new UpdateHandler(looper);
        this.mHandler = updateHandler;
        this.mCallbacks = new Callbacks(updateHandler);
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        bindService(context);
    }

    private static void bindService(Context context) {
        synchronized (sServiceLock) {
            if (sServiceInitialized) {
                return;
            }
            sServiceInitialized = true;
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_APP_WIDGETS) || context.getResources().getBoolean(R.bool.config_enableAppWidgetService)) {
                sService = IAppWidgetService.Stub.asInterface(ServiceManager.getService(Context.APPWIDGET_SERVICE));
            }
        }
    }

    public void startListening() {
        int[] iArr;
        int i;
        if (sService == null) {
            return;
        }
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.mListeners.keyAt(i2);
            }
        }
        try {
            List list = sService.startListening(this.mCallbacks, this.mContextOpPackageName, this.mHostId, iArr).getList();
            int size2 = list.size();
            for (i = 0; i < size2; i++) {
                PendingHostUpdate pendingHostUpdate = (PendingHostUpdate) list.get(i);
                int i3 = pendingHostUpdate.type;
                if (i3 == 0) {
                    updateAppWidgetView(pendingHostUpdate.appWidgetId, pendingHostUpdate.views);
                } else if (i3 == 1) {
                    onProviderChanged(pendingHostUpdate.appWidgetId, pendingHostUpdate.widgetInfo);
                } else if (i3 == 2) {
                    viewDataChanged(pendingHostUpdate.appWidgetId, pendingHostUpdate.viewId);
                } else if (i3 == 3) {
                    dispatchOnAppWidgetRemoved(pendingHostUpdate.appWidgetId);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void stopListening() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.stopListening(this.mContextOpPackageName, this.mHostId);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public int allocateAppWidgetId() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return -1;
        }
        try {
            return iAppWidgetService.allocateAppWidgetId(this.mContextOpPackageName, this.mHostId);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public final IntentSender getIntentSenderForConfigureActivity(int i, int i2) {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return null;
        }
        try {
            IntentSender createAppWidgetConfigIntentSender = iAppWidgetService.createAppWidgetConfigIntentSender(this.mContextOpPackageName, i, i2);
            if (createAppWidgetConfigIntentSender != null) {
                return createAppWidgetConfigIntentSender;
            }
            throw new ActivityNotFoundException();
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public final void startAppWidgetConfigureActivityForResult(Activity activity, int i, int i2, int i3, Bundle bundle) {
        if (sService == null) {
            return;
        }
        try {
            activity.startIntentSenderForResult(getIntentSenderForConfigureActivity(i, i2), i3, (Intent) null, 0, 0, 0, bundle);
        } catch (IntentSender.SendIntentException unused) {
            throw new ActivityNotFoundException();
        }
    }

    public void setAppWidgetHidden() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.setAppWidgetHidden(this.mContextOpPackageName, this.mHostId);
        } catch (RemoteException e) {
            throw new RuntimeException("System server dead?", e);
        }
    }

    public final void semStartAppWidgetConfigureActivityForResult(Activity activity, int i, int i2, int i3, Bundle bundle) {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            IntentSender semCreateAppWidgetConfigIntentSender = iAppWidgetService.semCreateAppWidgetConfigIntentSender(this.mContextOpPackageName, i, i2);
            if (semCreateAppWidgetConfigIntentSender != null) {
                activity.startIntentSenderForResult(semCreateAppWidgetConfigIntentSender, i3, (Intent) null, 0, 0, 0, bundle);
                return;
            }
            throw new ActivityNotFoundException();
        } catch (IntentSender.SendIntentException unused) {
            throw new ActivityNotFoundException();
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public boolean hidden_semStartListening() {
        int[] iArr;
        Log.i(TAG, "semStartListening()");
        if (sService == null) {
            return false;
        }
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = this.mListeners.keyAt(i);
            }
        }
        try {
            List list = sService.startListening(this.mCallbacks, this.mContextOpPackageName, this.mHostId, iArr).getList();
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                PendingHostUpdate pendingHostUpdate = (PendingHostUpdate) list.get(i2);
                int i3 = pendingHostUpdate.type;
                if (i3 == 0) {
                    this.mHandler.obtainMessage(1, pendingHostUpdate.appWidgetId, 0, pendingHostUpdate.views).sendToTarget();
                } else if (i3 == 1) {
                    this.mHandler.obtainMessage(2, pendingHostUpdate.appWidgetId, 0, pendingHostUpdate.widgetInfo).sendToTarget();
                } else if (i3 == 2) {
                    this.mHandler.obtainMessage(4, pendingHostUpdate.appWidgetId, pendingHostUpdate.viewId).sendToTarget();
                } else if (i3 == 3) {
                    this.mHandler.obtainMessage(5, pendingHostUpdate.appWidgetId, 0).sendToTarget();
                }
            }
            return true;
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void setInteractionHandler(RemoteViews.InteractionHandler interactionHandler) {
        this.mInteractionHandler = interactionHandler;
    }

    public int[] getAppWidgetIds() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return new int[0];
        }
        try {
            return iAppWidgetService.getAppWidgetIdsForHost(this.mContextOpPackageName, this.mHostId);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void deleteAppWidgetId(int i) {
        if (sService == null) {
            return;
        }
        removeListener(i);
        try {
            Log.d(TAG, "deleteAppWidgetId() appWidgetId = " + i);
            Log.d(TAG, "Stack:", new Throwable("stack dump"));
            sService.deleteAppWidgetId(this.mContextOpPackageName, i);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void deleteHost() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.deleteHost(this.mContextOpPackageName, this.mHostId);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public static void deleteAllHosts() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.deleteAllHosts();
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public final AppWidgetHostView createView(Context context, int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        if (sService == null) {
            return null;
        }
        AppWidgetHostView onCreateView = onCreateView(context, i, appWidgetProviderInfo);
        onCreateView.setInteractionHandler(this.mInteractionHandler);
        onCreateView.setAppWidget(i, appWidgetProviderInfo);
        setListener(i, onCreateView);
        return onCreateView;
    }

    protected AppWidgetHostView onCreateView(Context context, int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        return new AppWidgetHostView(context, this.mInteractionHandler);
    }

    protected void onProviderChanged(int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        AppWidgetHostListener listener = getListener(i);
        appWidgetProviderInfo.updateDimensions(this.mDisplayMetrics);
        if (listener != null) {
            listener.onUpdateProviderInfo(appWidgetProviderInfo);
        }
    }

    public interface AppWidgetHostListener {
        void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo);

        void onViewDataChanged(int i);

        void updateAppWidget(RemoteViews remoteViews);

        default void updateAppWidgetDeferred(String str, int i) {
            RemoteViews remoteViews;
            try {
                remoteViews = AppWidgetHost.sService.getAppWidgetViews(str, i);
            } catch (Exception e) {
                Log.e(AppWidgetHost.TAG, "updateAppWidgetDeferred: ", e);
                remoteViews = null;
            }
            updateAppWidget(remoteViews);
        }
    }

    void dispatchOnAppWidgetRemoved(int i) {
        removeListener(i);
        onAppWidgetRemoved(i);
    }

    public void setListener(int i, AppWidgetHostListener appWidgetHostListener) {
        synchronized (this.mListeners) {
            this.mListeners.put(i, appWidgetHostListener);
        }
        try {
            appWidgetHostListener.updateAppWidget(sService.getAppWidgetViews(this.mContextOpPackageName, i));
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void removeListener(int i) {
        synchronized (this.mListeners) {
            this.mListeners.remove(i);
        }
    }

    void updateAppWidgetView(int i, RemoteViews remoteViews) {
        AppWidgetHostListener listener = getListener(i);
        Log.i(TAG, "updateAppWidgetView, appWidgetId = " + i + ", v = " + listener);
        if (listener != null) {
            listener.updateAppWidget(remoteViews);
        }
    }

    void viewDataChanged(int i, int i2) {
        AppWidgetHostListener listener = getListener(i);
        Log.i(TAG, "viewDataChanged, appWidgetId = " + i + ", v = " + listener);
        if (listener != null) {
            listener.onViewDataChanged(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppWidgetDeferred(int i) {
        AppWidgetHostListener listener = getListener(i);
        if (listener == null) {
            Log.e(TAG, "updateAppWidgetDeferred: null listener for id: " + i);
            return;
        }
        listener.updateAppWidgetDeferred(this.mContextOpPackageName, i);
    }

    protected void clearViews() {
        synchronized (this.mListeners) {
            this.mListeners.clear();
        }
    }
}
