package com.samsung.android.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import com.android.internal.R;
import com.android.internal.policy.PhoneWindow;

public class SemUiSupportService extends Service implements Window.Callback, KeyEvent.Callback {
    private static final String TAG = "SemUiSupportService";
    protected Context mContext;
    private View mDecor;
    private Window mWindow;
    private WindowManager.LayoutParams mWindowAttributes;
    protected WindowManager mWindowManager;

    @Override // android.app.Service
    public void onCreate() {
        ApplicationInfo appInfo;
        super.onCreate();
        Log.i(TAG, "onCreate() : " + this);
        this.mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.mContext = this;
        try {
            PackageManager packageManager = getPackageManager();
            if (packageManager != null
                    && (appInfo = packageManager.getApplicationInfo(getPackageName(), 0)) != null) {
                this.mContext = new ContextThemeWrapper(this, appInfo.theme);
                Log.i(TAG, "loaded theme = " + appInfo.theme);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to get running tasks.", e);
        }
        this.mWindowAttributes = createLayoutParams();
        this.mWindow = new PhoneWindow(this.mContext);
        this.mWindow.setWindowManager(this.mWindowManager, null, null);
        this.mWindowManager = this.mWindow.getWindowManager();
        this.mWindow.requestFeature(1);
        this.mWindow.setCallback(this);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "onStartCommand()");
        if (this.mWindow != null) {
            this.mWindow.setAttributes(this.mWindowAttributes);
            this.mDecor = this.mWindow.getDecorView();
            this.mDecor.setVisibility(0);
            WindowManager.LayoutParams l = this.mWindow.getAttributes();
            if ((l.softInputMode & 256) == 0) {
                l.softInputMode |= 256;
            }
            try {
                this.mWindowManager.addView(this.mDecor, l);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                stopSelf();
                return 1;
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        removeDecorView();
        Log.i(TAG, "onDestroy() : " + this);
        this.mContext = null;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void stopService() {
        removeDecorView();
        stopForeground(true);
        stopSelf();
    }

    public void stopUiSupportService() {
        removeDecorView();
        stopForeground(true);
        stopSelf();
    }

    private void removeDecorView() {
        try {
            if (this.mDecor != null) {
                this.mWindowManager.removeView(this.mDecor);
                this.mDecor = null;
            }
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Already remove this view : " + this.mDecor);
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public void setContentView(int layoutResID) {
        this.mWindow.setContentView(layoutResID);
    }

    public void setContentView(View view) {
        this.mWindow.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        this.mWindow.setContentView(view, params);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        this.mWindow.addContentView(view, params);
    }

    public View findViewById(int id) {
        return this.mWindow.findViewById(id);
    }

    public WindowManager.LayoutParams getWindowAttributes() {
        Log.i(TAG, "getWindowAttributes()");
        return this.mWindowAttributes;
    }

    public void setWindowAttributes(WindowManager.LayoutParams a) {
        Log.i(TAG, "setAttributes()");
        this.mWindow.setAttributes(a);
    }

    public WindowManager.LayoutParams createLayoutParams() {
        Log.i(TAG, "createLayoutParams");
        WindowManager.LayoutParams lp =
                new WindowManager.LayoutParams(
                        2002, R.string.config_deviceSpecificAudioService, -3);
        lp.privateFlags |= 16;
        lp.softInputMode = 32;
        lp.setTitle(getClass().getName());
        return lp;
    }

    public Window getWindow() {
        return this.mWindow;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            stopService();
            return true;
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (this.mWindow.superDispatchKeyEvent(event)) {
            return true;
        }
        return event.dispatch(
                this, this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null, this);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return this.mWindow.superDispatchKeyShortcutEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent event) {
        return this.mWindow.superDispatchTouchEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return this.mWindow.superDispatchTrackballEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return this.mWindow.superDispatchGenericMotionEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int featureId) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int featureId, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {}

    @Override // android.view.Window.Callback
    public void onContentChanged() {}

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {}

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {}

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {}

    @Override // android.view.Window.Callback
    public void onPanelClosed(int featureId, Menu menu) {}

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode mode) {}

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode mode) {}
}
