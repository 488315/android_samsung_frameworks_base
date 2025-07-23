package android.window;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacksController;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.lang.ref.Reference;

/* loaded from: classes5.dex */
public class WindowContext extends ContextWrapper implements WindowProvider, ConfigurationDispatcher {
    private final ComponentCallbacksController mCallbacksController;
    private final WindowContextController mController;
    private final Bundle mOptions;
    private final int mType;
    private final WindowManager mWindowManager;

    @Override // android.window.ConfigurationDispatcher
    public boolean shouldReportPrivateChanges() {
        return true;
    }

    public WindowContext(Context context, int i, Bundle bundle) {
        super(context);
        this.mCallbacksController = new ComponentCallbacksController();
        this.mType = i;
        this.mOptions = bundle;
        this.mWindowManager = WindowManagerImpl.createWindowContextWindowManager(this);
        this.mController = new WindowContextController((WindowTokenClient) getWindowContextToken());
        Reference.reachabilityFence(this);
    }

    public void attachToDisplayArea() {
        this.mController.attachToDisplayArea(this.mType, getDisplayId(), this.mOptions);
    }

    public void reparentToDisplay(int i) {
        if (!Flags.reparentWindowTokenApi() || i == getDisplayId()) {
            return;
        }
        super.updateDisplay(i);
        this.mController.reparentToDisplayArea(this.mType, i, this.mOptions);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (Context.WINDOW_SERVICE.equals(str)) {
            return this.mWindowManager;
        }
        return super.getSystemService(str);
    }

    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }

    public void release() {
        this.mController.detachIfNeeded();
        destroy();
    }

    @Override // android.content.Context
    public void destroy() {
        try {
            this.mCallbacksController.clearCallbacks();
            getBaseContext().destroy();
        } finally {
            Reference.reachabilityFence(this);
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

    @Override // android.window.WindowProvider
    public int getWindowType() {
        return this.mType;
    }

    @Override // android.window.WindowProvider
    public Bundle getWindowContextOptions() {
        return this.mOptions;
    }
}
