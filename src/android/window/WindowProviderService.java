package android.window;

import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.Service;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacksController;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowManagerImpl;

/* loaded from: classes5.dex */
public abstract class WindowProviderService extends Service implements WindowProvider, ConfigurationDispatcher {
    private static final String TAG = "WindowProviderService";
    private final ComponentCallbacksController mCallbacksController;
    private final WindowContextController mController;
    private boolean mInitialized;
    private final Bundle mOptions;
    private WindowManager mWindowManager;
    private final WindowTokenClient mWindowToken;

    public int getInitialDisplayId() {
        return 0;
    }

    public abstract int getWindowType();

    public static boolean isWindowProviderService(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(WindowProvider.KEY_IS_WINDOW_PROVIDER_SERVICE, false);
    }

    public WindowProviderService() {
        WindowTokenClient windowTokenClient = new WindowTokenClient();
        this.mWindowToken = windowTokenClient;
        this.mController = new WindowContextController(windowTokenClient);
        this.mCallbacksController = new ComponentCallbacksController();
        Bundle bundle = new Bundle();
        this.mOptions = bundle;
        bundle.putBoolean(WindowProvider.KEY_IS_WINDOW_PROVIDER_SERVICE, true);
    }

    public Bundle getWindowContextOptions() {
        return this.mOptions;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.registerCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.unregisterCallbacks(componentCallbacks);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCallbacksController.dispatchConfigurationChanged(configuration);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCallbacksController.dispatchLowMemory();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCallbacksController.dispatchTrimMemory(i);
    }

    public final void attachToWindowToken(IBinder iBinder) {
        this.mController.attachToWindowToken(iBinder);
    }

    @Override // android.app.Service
    public final Context createServiceBaseContext(ActivityThread activityThread, LoadedApk loadedApk) {
        Context contextCreateServiceBaseContext = super.createServiceBaseContext(activityThread, loadedApk);
        DisplayManager displayManager = (DisplayManager) contextCreateServiceBaseContext.getSystemService(DisplayManager.class);
        int initialDisplayId = getInitialDisplayId();
        Display display = displayManager.getDisplay(initialDisplayId);
        if (display == null) {
            Log.e(TAG, "Display with id " + initialDisplayId + " not found, falling back to DEFAULT_DISPLAY");
            display = displayManager.getDisplay(0);
        }
        return contextCreateServiceBaseContext.createTokenContext(this.mWindowToken, display);
    }

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        if (this.mInitialized) {
            return;
        }
        this.mWindowToken.attachContext(this);
        this.mController.attachToDisplayArea(getWindowType(), getDisplayId(), getWindowContextOptions());
        this.mWindowManager = WindowManagerImpl.createWindowContextWindowManager(this);
        this.mInitialized = true;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (Context.WINDOW_SERVICE.equals(str)) {
            return this.mWindowManager;
        }
        return super.getSystemService(str);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.mController.detachIfNeeded();
        this.mCallbacksController.clearCallbacks();
    }

    @Override // android.window.ConfigurationDispatcher
    public void dispatchConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(configuration);
    }
}
