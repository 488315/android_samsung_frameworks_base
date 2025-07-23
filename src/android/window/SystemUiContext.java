package android.window;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacksController;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;

/* loaded from: classes5.dex */
public class SystemUiContext extends ContextWrapper implements ConfigurationDispatcher {
    private final ComponentCallbacksController mCallbacksController;

    @Override // android.window.ConfigurationDispatcher
    public boolean shouldReportPrivateChanges() {
        return true;
    }

    public SystemUiContext(Context context) {
        super(context);
        this.mCallbacksController = new ComponentCallbacksController();
        if (!Flags.trackSystemUiContextBeforeWms()) {
            throw new UnsupportedOperationException("SystemUiContext can only be used after flag is enabled.");
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.registerCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.unregisterCallbacks(componentCallbacks);
    }

    @Override // android.window.ConfigurationDispatcher
    public void dispatchConfigurationChanged(Configuration configuration) {
        this.mCallbacksController.dispatchConfigurationChanged(configuration);
    }
}
