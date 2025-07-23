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

/* loaded from: classes6.dex */
public class SemUiSupportService extends Service implements Window.Callback, KeyEvent.Callback {
    private static final String TAG = "SemUiSupportService";
    protected Context mContext;
    private View mDecor;
    private Window mWindow;
    private WindowManager.LayoutParams mWindowAttributes;
    protected WindowManager mWindowManager;

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        ApplicationInfo applicationInfo;
        super.onCreate();
        Log.i(TAG, "onCreate() : " + this);
        this.mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.mContext = this;
        try {
            PackageManager packageManager = getPackageManager();
            if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0)) != null) {
                this.mContext = new ContextThemeWrapper(this, applicationInfo.theme);
                Log.i(TAG, "loaded theme = " + applicationInfo.theme);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to get running tasks.", e);
        }
        this.mWindowAttributes = createLayoutParams();
        PhoneWindow phoneWindow = new PhoneWindow(this.mContext);
        this.mWindow = phoneWindow;
        phoneWindow.setWindowManager(this.mWindowManager, null, null);
        this.mWindowManager = this.mWindow.getWindowManager();
        this.mWindow.requestFeature(1);
        this.mWindow.setCallback(this);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        Log.i(TAG, "onStartCommand()");
        Window window = this.mWindow;
        if (window == null) {
            return 1;
        }
        window.setAttributes(this.mWindowAttributes);
        View decorView = this.mWindow.getDecorView();
        this.mDecor = decorView;
        decorView.setVisibility(0);
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if ((attributes.softInputMode & 256) == 0) {
            attributes.softInputMode |= 256;
        }
        try {
            this.mWindowManager.addView(this.mDecor, attributes);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
            return 1;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        removeDecorView();
        Log.i(TAG, "onDestroy() : " + this);
        this.mContext = null;
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
            View view = this.mDecor;
            if (view != null) {
                this.mWindowManager.removeView(view);
                this.mDecor = null;
            }
        } catch (IllegalArgumentException unused) {
            Log.i(TAG, "Already remove this view : " + this.mDecor);
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public void setContentView(int i) {
        this.mWindow.setContentView(i);
    }

    public void setContentView(View view) {
        this.mWindow.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindow.setContentView(view, layoutParams);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindow.addContentView(view, layoutParams);
    }

    public View findViewById(int i) {
        return this.mWindow.findViewById(i);
    }

    public WindowManager.LayoutParams getWindowAttributes() {
        Log.i(TAG, "getWindowAttributes()");
        return this.mWindowAttributes;
    }

    public void setWindowAttributes(WindowManager.LayoutParams layoutParams) {
        Log.i(TAG, "setAttributes()");
        this.mWindow.setAttributes(layoutParams);
    }

    public WindowManager.LayoutParams createLayoutParams() {
        Log.i(TAG, "createLayoutParams");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2002, R.string.config_defaultSearchSelectorPackageName, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.softInputMode = 32;
        layoutParams.setTitle(getClass().getName());
        return layoutParams;
    }

    public Window getWindow() {
        return this.mWindow;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        stopService();
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mWindow.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View view = this.mDecor;
        return keyEvent.dispatch(this, view != null ? view.getKeyDispatcherState() : null, this);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.mWindow.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mWindow.superDispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.mWindow.superDispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.mWindow.superDispatchGenericMotionEvent(motionEvent);
    }
}
