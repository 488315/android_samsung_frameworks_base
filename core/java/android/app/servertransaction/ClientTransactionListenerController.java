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

import com.android.window.flags.Flags;

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
    private final ArraySet<BiConsumer<IBinder, ActivityWindowInfo>>
            mActivityWindowInfoChangedListeners = new ArraySet<>();
    private final ArrayMap<Context, Configuration> mContextToPreChangedConfigMap = new ArrayMap<>();

    public static ClientTransactionListenerController getInstance() {
        ClientTransactionListenerController clientTransactionListenerController;
        synchronized (ClientTransactionListenerController.class) {
            if (sController == null) {
                sController =
                        new ClientTransactionListenerController(DisplayManagerGlobal.getInstance());
            }
            clientTransactionListenerController = sController;
        }
        return clientTransactionListenerController;
    }

    public static ClientTransactionListenerController createInstanceForTesting(
            DisplayManagerGlobal displayManager) {
        return new ClientTransactionListenerController(displayManager);
    }

    private ClientTransactionListenerController(DisplayManagerGlobal displayManager) {
        this.mDisplayManager = (DisplayManagerGlobal) Objects.requireNonNull(displayManager);
    }

    public void registerActivityWindowInfoChangedListener(
            BiConsumer<IBinder, ActivityWindowInfo> listener) {
        if (!Flags.activityWindowInfoFlag()) {
            return;
        }
        synchronized (this.mLock) {
            this.mActivityWindowInfoChangedListeners.add(listener);
        }
    }

    public void unregisterActivityWindowInfoChangedListener(
            BiConsumer<IBinder, ActivityWindowInfo> listener) {
        if (!Flags.activityWindowInfoFlag()) {
            return;
        }
        synchronized (this.mLock) {
            this.mActivityWindowInfoChangedListeners.remove(listener);
        }
    }

    public void onActivityWindowInfoChanged(
            IBinder activityToken, ActivityWindowInfo activityWindowInfo) {
        if (!Flags.activityWindowInfoFlag()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mActivityWindowInfoChangedListeners.isEmpty()) {
                return;
            }
            Object[] activityWindowInfoChangedListeners =
                    this.mActivityWindowInfoChangedListeners.toArray();
            for (Object activityWindowInfoChangedListener : activityWindowInfoChangedListeners) {
                ((BiConsumer) activityWindowInfoChangedListener)
                        .accept(activityToken, new ActivityWindowInfo(activityWindowInfo));
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
            ArraySet<Integer> configUpdatedDisplayIds = new ArraySet<>();
            int contextCount = this.mContextToPreChangedConfigMap.size();
            for (int i = 0; i < contextCount; i++) {
                try {
                    Context context = this.mContextToPreChangedConfigMap.keyAt(i);
                    Configuration preChangedConfig = this.mContextToPreChangedConfigMap.valueAt(i);
                    if (shouldReportDisplayChange(context, preChangedConfig)) {
                        configUpdatedDisplayIds.add(Integer.valueOf(context.getDisplayId()));
                    }
                } finally {
                    this.mContextToPreChangedConfigMap.clear();
                }
            }
            try {
                int displayCount = configUpdatedDisplayIds.size();
                for (int i2 = 0; i2 < displayCount; i2++) {
                    int displayId = configUpdatedDisplayIds.valueAt(i2).intValue();
                    onDisplayChanged(displayId);
                }
            } catch (RejectedExecutionException e) {
                Log.w(TAG, "Failed to notify DisplayListener because the Handler is shutting down");
            }
        }
    }

    public void onContextConfigurationPreChanged(Context context) {
        if (!Flags.bundleClientTransactionFlag() || ActivityThread.isSystem()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mContextToPreChangedConfigMap.containsKey(context)) {
                return;
            }
            this.mContextToPreChangedConfigMap.put(
                    context, new Configuration(context.getResources().getConfiguration()));
        }
    }

    public void onContextConfigurationPostChanged(Context context) {
        if (!Flags.bundleClientTransactionFlag() || ActivityThread.isSystem()) {
            return;
        }
        int changedDisplayId = -1;
        synchronized (this.mLock) {
            if (this.mIsClientTransactionExecuting) {
                return;
            }
            Configuration preChangedConfig = this.mContextToPreChangedConfigMap.remove(context);
            if (preChangedConfig != null && shouldReportDisplayChange(context, preChangedConfig)) {
                changedDisplayId = context.getDisplayId();
            }
            if (changedDisplayId != -1) {
                try {
                    onDisplayChanged(changedDisplayId);
                } catch (RejectedExecutionException e) {
                    Log.w(
                            TAG,
                            "Failed to notify DisplayListener because the Handler is shutting"
                                    + " down");
                }
            }
        }
    }

    private boolean shouldReportDisplayChange(Context context, Configuration preChangedConfig) {
        Configuration postChangedConfig = context.getResources().getConfiguration();
        return !WindowConfiguration.areConfigurationsEqualForDisplay(
                postChangedConfig, preChangedConfig);
    }

    public void onDisplayChanged(int displayId) throws RejectedExecutionException {
        this.mDisplayManager.handleDisplayChangeFromWindowManager(displayId);
    }
}
