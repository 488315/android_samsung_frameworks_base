package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManagerGlobal;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.window.ActivityWindowInfo;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class ClientTransactionListenerController {
    private static final String TAG = "ClientTransactionListenerController";
    private static ClientTransactionListenerController sController;
    private final DisplayManagerGlobal mDisplayManager;
    private boolean mIsClientTransactionExecuting;
    private final Object mLock = new Object();
    private final ArraySet<BiConsumer<IBinder, ActivityWindowInfo>> mActivityWindowInfoChangedListeners = new ArraySet<>();
    private final ArrayMap<Context, Configuration> mContextToPreChangedConfigMap = new ArrayMap<>();

    public static ClientTransactionListenerController getInstance() {
        ClientTransactionListenerController clientTransactionListenerController;
        synchronized (ClientTransactionListenerController.class) {
            if (sController == null) {
                sController = new ClientTransactionListenerController(DisplayManagerGlobal.getInstance());
            }
            clientTransactionListenerController = sController;
        }
        return clientTransactionListenerController;
    }

    public static ClientTransactionListenerController createInstanceForTesting(DisplayManagerGlobal displayManagerGlobal) {
        return new ClientTransactionListenerController(displayManagerGlobal);
    }

    private ClientTransactionListenerController(DisplayManagerGlobal displayManagerGlobal) {
        this.mDisplayManager = (DisplayManagerGlobal) Objects.requireNonNull(displayManagerGlobal);
    }

    public void registerActivityWindowInfoChangedListener(BiConsumer<IBinder, ActivityWindowInfo> biConsumer) {
        synchronized (this.mLock) {
            this.mActivityWindowInfoChangedListeners.add(biConsumer);
        }
    }

    public void unregisterActivityWindowInfoChangedListener(BiConsumer<IBinder, ActivityWindowInfo> biConsumer) {
        synchronized (this.mLock) {
            this.mActivityWindowInfoChangedListeners.remove(biConsumer);
        }
    }

    public void onActivityWindowInfoChanged(IBinder iBinder, ActivityWindowInfo activityWindowInfo) {
        synchronized (this.mLock) {
            if (this.mActivityWindowInfoChangedListeners.isEmpty()) {
                return;
            }
            for (Object obj : this.mActivityWindowInfoChangedListeners.toArray()) {
                ((BiConsumer) obj).accept(iBinder, new ActivityWindowInfo(activityWindowInfo));
            }
        }
    }

    public void onClientTransactionStarted() {
        synchronized (this.mLock) {
            this.mIsClientTransactionExecuting = true;
        }
    }

    public void onClientTransactionFinished() {
        synchronized (this.mLock) {
            this.mIsClientTransactionExecuting = false;
            if (this.mContextToPreChangedConfigMap.isEmpty()) {
                return;
            }
            ArraySet arraySet = new ArraySet();
            int size = this.mContextToPreChangedConfigMap.size();
            for (int i = 0; i < size; i++) {
                try {
                    Context contextKeyAt = this.mContextToPreChangedConfigMap.keyAt(i);
                    if (shouldReportDisplayChange(contextKeyAt, this.mContextToPreChangedConfigMap.valueAt(i))) {
                        arraySet.add(Integer.valueOf(contextKeyAt.getDisplayId()));
                    }
                } finally {
                    this.mContextToPreChangedConfigMap.clear();
                }
            }
            try {
                int size2 = arraySet.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    onDisplayChanged(((Integer) arraySet.valueAt(i2)).intValue());
                }
            } catch (RejectedExecutionException unused) {
                Log.w(TAG, "Failed to notify DisplayListener because the Handler is shutting down");
            }
        }
    }

    public void onContextConfigurationPreChanged(Context context) {
        if (ActivityThread.isSystem()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mContextToPreChangedConfigMap.containsKey(context)) {
                return;
            }
            this.mContextToPreChangedConfigMap.put(context, new Configuration(context.getResources().getConfiguration()));
        }
    }

    public void onContextConfigurationPostChanged(Context context) {
        if (ActivityThread.isSystem()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mIsClientTransactionExecuting) {
                return;
            }
            Configuration configurationRemove = this.mContextToPreChangedConfigMap.remove(context);
            int displayId = (configurationRemove == null || !shouldReportDisplayChange(context, configurationRemove)) ? -1 : context.getDisplayId();
            if (displayId != -1) {
                try {
                    onDisplayChanged(displayId);
                } catch (RejectedExecutionException unused) {
                    Log.w(TAG, "Failed to notify DisplayListener because the Handler is shutting down");
                }
            }
        }
    }

    private boolean shouldReportDisplayChange(Context context, Configuration configuration) {
        return !WindowConfiguration.areConfigurationsEqualForDisplay(context.getResources().getConfiguration(), configuration);
    }

    public void onDisplayChanged(int i) throws RejectedExecutionException {
        this.mDisplayManager.handleDisplayChangeFromWindowManager(i);
    }
}
