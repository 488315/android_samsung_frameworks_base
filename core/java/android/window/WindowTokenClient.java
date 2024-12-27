package android.window;

import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.app.servertransaction.ClientTransactionListenerController;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.inputmethodservice.AbstractInputMethodService;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.window.flags.Flags;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class WindowTokenClient extends Binder {
    private static final String TAG = WindowTokenClient.class.getSimpleName();
    private boolean mShouldDumpConfigForIme;
    private WeakReference<Context> mContextRef = null;
    private final ResourcesManager mResourcesManager = ResourcesManager.getInstance();
    private final Configuration mConfiguration = new Configuration();
    private final Handler mHandler = ActivityThread.currentActivityThread().getHandler();

    public void attachContext(Context context) {
        if (this.mContextRef != null) {
            throw new IllegalStateException("Context is already attached.");
        }
        this.mContextRef = new WeakReference<>(context);
        this.mShouldDumpConfigForIme = Build.IS_DEBUGGABLE && (context instanceof AbstractInputMethodService);
    }

    public Context getContext() {
        if (this.mContextRef != null) {
            return this.mContextRef.get();
        }
        return null;
    }

    public void onConfigurationChanged(Configuration newConfig, int newDisplayId) {
        onConfigurationChanged(newConfig, newDisplayId, true);
    }

    public void postOnConfigurationChanged(Configuration newConfig, int newDisplayId) {
        this.mHandler.post(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.window.WindowTokenClient$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) {
                WindowTokenClient.this.onConfigurationChanged((Configuration) obj, ((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
            }
        }, newConfig, Integer.valueOf(newDisplayId), true).recycleOnUse());
    }

    public void onConfigurationChanged(Configuration newConfig, int newDisplayId, boolean shouldReportConfigChange) {
        Context context = this.mContextRef.get();
        if (context == null) {
            return;
        }
        if (shouldReportConfigChange && Flags.windowTokenConfigThreadSafe()) {
            ClientTransactionListenerController controller = getClientTransactionListenerController();
            controller.onContextConfigurationPreChanged(context);
            try {
                onConfigurationChangedInner(context, newConfig, newDisplayId, shouldReportConfigChange);
                return;
            } finally {
                controller.onContextConfigurationPostChanged(context);
            }
        }
        onConfigurationChangedInner(context, newConfig, newDisplayId, shouldReportConfigChange);
    }

    public void onConfigurationChangedInner(Context context, Configuration newConfig, int newDisplayId, boolean shouldReportConfigChange) {
        boolean displayChanged;
        boolean shouldUpdateResources;
        int diff;
        Configuration currentConfig;
        CompatibilityInfo.applyOverrideScaleIfNeeded(newConfig);
        synchronized (this.mConfiguration) {
            displayChanged = ConfigurationHelper.isDifferentDisplay(context.getDisplayId(), newDisplayId);
            shouldUpdateResources = ConfigurationHelper.shouldUpdateResources(this, this.mConfiguration, newConfig, newConfig, displayChanged, null);
            diff = this.mConfiguration.diffPublicOnly(newConfig);
            currentConfig = this.mShouldDumpConfigForIme ? new Configuration(this.mConfiguration) : null;
            if (shouldUpdateResources) {
                this.mConfiguration.setTo(newConfig);
            }
        }
        if (!shouldUpdateResources && this.mShouldDumpConfigForIme) {
            Log.d(TAG, "Configuration not dispatch to IME because configuration is up to date. Current config=" + context.getResources().getConfiguration() + ", reported config=" + currentConfig + ", updated config=" + newConfig + ", updated display ID=" + newDisplayId);
        }
        if (displayChanged) {
            context.updateDisplay(newDisplayId);
        }
        if (shouldUpdateResources) {
            this.mResourcesManager.updateResourcesForActivity(this, newConfig, newDisplayId);
            if (shouldReportConfigChange && (context instanceof WindowContext)) {
                WindowContext windowContext = (WindowContext) context;
                windowContext.dispatchConfigurationChanged(newConfig);
            }
            if (shouldReportConfigChange && diff != 0 && (context instanceof WindowProviderService)) {
                WindowProviderService windowProviderService = (WindowProviderService) context;
                windowProviderService.onConfigurationChanged(newConfig);
            }
            ConfigurationHelper.freeTextLayoutCachesIfNeeded(diff);
            if (this.mShouldDumpConfigForIme) {
                if (!shouldReportConfigChange) {
                    Log.d(TAG, "Only apply configuration update to Resources because shouldReportConfigChange is false. context=" + context + ", config=" + context.getResources().getConfiguration() + ", display ID=" + context.getDisplayId() + "\n" + Debug.getCallers(5));
                } else if (diff == 0) {
                    Log.d(TAG, "Configuration not dispatch to IME because configuration has no  public difference with updated config.  Current config=" + context.getResources().getConfiguration() + ", reported config=" + currentConfig + ", updated config=" + newConfig + ", display ID=" + context.getDisplayId());
                }
            }
        }
    }

    public void onWindowTokenRemoved() {
        Context context = this.mContextRef.get();
        if (context != null) {
            context.destroy();
            this.mContextRef.clear();
        }
    }

    public ClientTransactionListenerController getClientTransactionListenerController() {
        return ClientTransactionListenerController.getInstance();
    }
}
