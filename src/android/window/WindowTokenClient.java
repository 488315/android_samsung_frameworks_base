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
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class WindowTokenClient extends Binder {
    private static final String TAG = "WindowTokenClient";
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
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public void onConfigurationChanged(Configuration configuration, int i) {
        onConfigurationChanged(configuration, i, true);
    }

    public void postOnConfigurationChanged(Configuration configuration, int i) {
        this.mHandler.post(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.window.WindowTokenClient$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) {
                this.f$0.onConfigurationChanged((Configuration) obj, ((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
            }
        }, configuration, Integer.valueOf(i), true).recycleOnUse());
    }

    public void onConfigurationChanged(Configuration configuration, int i, boolean z) {
        Context context = this.mContextRef.get();
        if (context == null) {
            return;
        }
        if (z) {
            ClientTransactionListenerController clientTransactionListenerController = getClientTransactionListenerController();
            clientTransactionListenerController.onContextConfigurationPreChanged(context);
            try {
                onConfigurationChangedInner(context, configuration, i, z);
                return;
            } finally {
                clientTransactionListenerController.onContextConfigurationPostChanged(context);
            }
        }
        onConfigurationChangedInner(context, configuration, i, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onConfigurationChangedInner(Context context, Configuration configuration, int i, boolean z) {
        boolean zIsDifferentDisplay;
        boolean zShouldUpdateResources;
        int iDiffPublicOnly;
        Configuration configuration2;
        CompatibilityInfo.applyOverrideIfNeeded(configuration);
        synchronized (this.mConfiguration) {
            zIsDifferentDisplay = ConfigurationHelper.isDifferentDisplay(context.getDisplayId(), i);
            zShouldUpdateResources = ConfigurationHelper.shouldUpdateResources(this, this.mConfiguration, configuration, configuration, zIsDifferentDisplay, null);
            iDiffPublicOnly = this.mConfiguration.diffPublicOnly(configuration);
            configuration2 = this.mShouldDumpConfigForIme ? new Configuration(this.mConfiguration) : null;
            if (zShouldUpdateResources) {
                this.mConfiguration.setTo(configuration);
            }
        }
        if (!zShouldUpdateResources && this.mShouldDumpConfigForIme) {
            Log.d(TAG, "Configuration not dispatch to IME because configuration is up to date. Current config=" + context.getResources().getConfiguration() + ", reported config=" + configuration2 + ", updated config=" + configuration + ", updated display ID=" + i);
        }
        if (zIsDifferentDisplay) {
            context.updateDisplay(i);
        }
        if (zShouldUpdateResources) {
            this.mResourcesManager.updateResourcesForActivity(this, configuration, i);
            if (z && (context instanceof ConfigurationDispatcher)) {
                ConfigurationDispatcher configurationDispatcher = (ConfigurationDispatcher) context;
                if (configurationDispatcher.shouldReportPrivateChanges() || iDiffPublicOnly != 0) {
                    configurationDispatcher.dispatchConfigurationChanged(configuration);
                }
            }
            ConfigurationHelper.freeTextLayoutCachesIfNeeded(iDiffPublicOnly);
            if (this.mShouldDumpConfigForIme) {
                if (!z) {
                    Log.d(TAG, "Only apply configuration update to Resources because shouldReportConfigChange is false. context=" + context + ", config=" + context.getResources().getConfiguration() + ", display ID=" + context.getDisplayId() + ShaderAssembler.NEWLINE + Debug.getCallers(5));
                    return;
                }
                if (iDiffPublicOnly == 0) {
                    Log.d(TAG, "Configuration not dispatch to IME because configuration has no  public difference with updated config.  Current config=" + context.getResources().getConfiguration() + ", reported config=" + configuration2 + ", updated config=" + configuration + ", display ID=" + context.getDisplayId());
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
