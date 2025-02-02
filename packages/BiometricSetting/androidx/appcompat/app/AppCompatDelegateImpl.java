package androidx.appcompat.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.app.WindowDecorActionBar.ActionModeImpl;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes.dex */
final class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    WindowDecorActionBar mActionBar;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private int mActivityHandlesConfigFlags;
    private boolean mActivityHandlesConfigFlagsChecked;
    final AppCompatCallback mAppCompatCallback;
    private AppCompatViewInflater mAppCompatViewInflater;
    private AppCompatWindowCallback mAppCompatWindowCallback;
    private AutoBatteryNightModeManager mAutoBatteryNightModeManager;
    private AutoTimeNightModeManager mAutoTimeNightModeManager;
    private AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0 mBackCallback;
    private boolean mBaseContextAttached;
    private boolean mClosingActionMenu;
    final Context mContext;
    private boolean mCreated;
    private DecorContentParent mDecorContentParent;
    boolean mDestroyed;
    private OnBackInvokedDispatcher mDispatcher;
    private Configuration mEffectiveConfiguration;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private boolean mHandleNativeActionModes;
    boolean mHasActionBar;
    final Object mHost;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable;
    boolean mIsFloating;
    private int mLocalNightMode;
    private boolean mLongPressBackDown;
    SupportMenuInflater mMenuInflater;
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private int mThemeResId;
    private CharSequence mTitle;
    private TextView mTitleView;
    Window mWindow;
    boolean mWindowNoTitle;
    private static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap<>();
    private static final int[] sWindowBackgroundStyleable = {R.attr.windowBackground};
    private static final boolean sCanReturnDifferentContext = !"robolectric".equals(Build.FINGERPRINT);
    private static final boolean sCanApplyOverrideConfiguration = true;

    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$2 */
    final class RunnableC00142 implements Runnable {
        RunnableC00142() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl.mInvalidatePanelMenuFeatures & 1) != 0) {
                appCompatDelegateImpl.doInvalidatePanelMenu(0);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl2.mInvalidatePanelMenuFeatures & 4096) != 0) {
                appCompatDelegateImpl2.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl3.mInvalidatePanelMenuPosted = false;
            appCompatDelegateImpl3.mInvalidatePanelMenuFeatures = 0;
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menuBuilder);
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback = AppCompatDelegateImpl.this.getWindowCallback();
            if (windowCallback == null) {
                return true;
            }
            windowCallback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(SupportActionModeWrapper.CallbackWrapper callbackWrapper) {
            this.mWrapped = callbackWrapper;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public final boolean onCreateActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            return this.mWrapped.onCreateActionMode(actionMode, menuBuilder);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.mActionModePopup != null) {
                appCompatDelegateImpl.mWindow.getDecorView().removeCallbacks(appCompatDelegateImpl.mShowActionModePopup);
            }
            if (appCompatDelegateImpl.mActionModeView != null) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = appCompatDelegateImpl.mFadeAnim;
                if (viewPropertyAnimatorCompat != null) {
                    viewPropertyAnimatorCompat.cancel();
                }
                ViewPropertyAnimatorCompat animate = ViewCompat.animate(appCompatDelegateImpl.mActionModeView);
                animate.alpha(0.0f);
                appCompatDelegateImpl.mFadeAnim = animate;
                animate.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.ActionModeCallbackWrapperV9.1
                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public final void onAnimationEnd() {
                        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = ActionModeCallbackWrapperV9.this;
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl2.mActionModePopup;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl2.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) appCompatDelegateImpl2.mActionModeView.getParent());
                        }
                        appCompatDelegateImpl2.mActionModeView.killMode();
                        appCompatDelegateImpl2.mFadeAnim.setListener(null);
                        appCompatDelegateImpl2.mFadeAnim = null;
                        ViewCompat.requestApplyInsets(appCompatDelegateImpl2.mSubDecor);
                    }
                });
            }
            AppCompatCallback appCompatCallback = appCompatDelegateImpl.mAppCompatCallback;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished();
            }
            appCompatDelegateImpl.mActionMode = null;
            ViewCompat.requestApplyInsets(appCompatDelegateImpl.mSubDecor);
            appCompatDelegateImpl.updateBackInvokedCallbackState();
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public final boolean onPrepareActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.mSubDecor);
            return this.mWrapped.onPrepareActionMode(actionMode, menuBuilder);
        }
    }

    class AppCompatWindowCallback extends WindowCallbackWrapper {
        private boolean mDispatchKeyEventBypassEnabled;
        private boolean mOnContentChangedBypassEnabled;
        private boolean mOnPanelClosedBypassEnabled;

        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        public final boolean bypassDispatchKeyEvent(Window.Callback callback, KeyEvent keyEvent) {
            try {
                this.mDispatchKeyEventBypassEnabled = true;
                return callback.dispatchKeyEvent(keyEvent);
            } finally {
                this.mDispatchKeyEventBypassEnabled = false;
            }
        }

        public final void bypassOnContentChanged(Window.Callback callback) {
            try {
                this.mOnContentChangedBypassEnabled = true;
                callback.onContentChanged();
            } finally {
                this.mOnContentChangedBypassEnabled = false;
            }
        }

        public final void bypassOnPanelClosed(Window.Callback callback, int i, Menu menu) {
            try {
                this.mOnPanelClosedBypassEnabled = true;
                callback.onPanelClosed(i, menu);
            } finally {
                this.mOnPanelClosedBypassEnabled = false;
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.mDispatchKeyEventBypassEnabled ? getWrapped().dispatchKeyEvent(keyEvent) : AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
        }

        @Override // android.view.Window.Callback
        public final void onContentChanged() {
            if (this.mOnContentChangedBypassEnabled) {
                getWrapped().onContentChanged();
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final View onCreatePanelView(int i) {
            return super.onCreatePanelView(i);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl.this.onMenuOpened(i);
            return true;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onPanelClosed(int i, Menu menu) {
            if (this.mOnPanelClosedBypassEnabled) {
                getWrapped().onPanelClosed(i, menu);
            } else {
                super.onPanelClosed(i, menu);
                AppCompatDelegateImpl.this.onPanelClosed(i);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(false);
            }
            return onPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            MenuBuilder menuBuilder = AppCompatDelegateImpl.this.getPanelState(0).menu;
            if (menuBuilder != null) {
                super.onProvideKeyboardShortcuts(list, menuBuilder, i);
            } else {
                super.onProvideKeyboardShortcuts(list, menu, i);
            }
        }

        @Override // android.view.Window.Callback
        public final android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            if (!AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() || i != 0) {
                return super.onWindowStartingActionMode(callback, i);
            }
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, callback);
            androidx.appcompat.view.ActionMode startSupportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (startSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(startSupportActionMode);
            }
            return null;
        }
    }

    private class AutoBatteryNightModeManager extends AutoNightModeManager {
        private final PowerManager mPowerManager;

        AutoBatteryNightModeManager(Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        final IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        public final int getApplyableNightMode() {
            return this.mPowerManager.isPowerSaveMode() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    abstract class AutoNightModeManager {
        private BroadcastReceiver mReceiver;

        AutoNightModeManager() {
        }

        final void cleanup() {
            BroadcastReceiver broadcastReceiver = this.mReceiver;
            if (broadcastReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.mContext.unregisterReceiver(broadcastReceiver);
                } catch (IllegalArgumentException unused) {
                }
                this.mReceiver = null;
            }
        }

        abstract IntentFilter createIntentFilterForBroadcastReceiver();

        abstract void onChange();

        final void setup() {
            cleanup();
            IntentFilter createIntentFilterForBroadcastReceiver = createIntentFilterForBroadcastReceiver();
            if (createIntentFilterForBroadcastReceiver == null || createIntentFilterForBroadcastReceiver.countActions() == 0) {
                return;
            }
            if (this.mReceiver == null) {
                this.mReceiver = new BroadcastReceiver() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.onChange();
                    }
                };
            }
            AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, createIntentFilterForBroadcastReceiver);
        }
    }

    private class AutoTimeNightModeManager extends AutoNightModeManager {
        private final TwilightManager mTwilightManager;

        AutoTimeNightModeManager(TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        final IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        public final int getApplyableNightMode() {
            return this.mTwilightManager.isNight() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    private class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(ContextThemeWrapper contextThemeWrapper) {
            super(contextThemeWrapper);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5) {
                    AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                    appCompatDelegateImpl.closePanel(appCompatDelegateImpl.getPanelState(0), true);
                    return true;
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public final void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }
    }

    protected static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        ContextThemeWrapper listPresenterContext;
        MenuBuilder menu;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        int windowAnimations;

        PanelFeatureState(int i) {
            this.featureId = i;
        }
    }

    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            if (z2) {
                menuBuilder = rootMenu;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            PanelFeatureState findMenuPanel = appCompatDelegateImpl.findMenuPanel(menuBuilder);
            if (findMenuPanel != null) {
                if (!z2) {
                    appCompatDelegateImpl.closePanel(findMenuPanel, z);
                } else {
                    appCompatDelegateImpl.callOnPanelClosed(findMenuPanel.featureId, findMenuPanel, rootMenu);
                    appCompatDelegateImpl.closePanel(findMenuPanel, true);
                }
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback;
            if (menuBuilder != menuBuilder.getRootMenu()) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.mHasActionBar || (windowCallback = appCompatDelegateImpl.getWindowCallback()) == null || appCompatDelegateImpl.mDestroyed) {
                return true;
            }
            windowCallback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    AppCompatDelegateImpl(Activity activity, AppCompatCallback appCompatCallback) {
        this(activity, null, appCompatCallback, activity);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean applyApplicationSpecificConfig(boolean z, boolean z2) {
        int i;
        Configuration configuration;
        int i2;
        boolean z3;
        Object obj;
        if (this.mDestroyed) {
            return false;
        }
        int i3 = this.mLocalNightMode;
        if (i3 == -100) {
            i3 = AppCompatDelegate.getDefaultNightMode();
        }
        Configuration createOverrideAppConfiguration = createOverrideAppConfiguration(this.mContext, mapNightMode(this.mContext, i3), null, false);
        Context context = this.mContext;
        boolean z4 = true;
        if (!this.mActivityHandlesConfigFlagsChecked && (this.mHost instanceof Activity)) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                i = 0;
                configuration = this.mEffectiveConfiguration;
                if (configuration == null) {
                    configuration = this.mContext.getResources().getConfiguration();
                }
                int i4 = configuration.uiMode & 48;
                int i5 = createOverrideAppConfiguration.uiMode & 48;
                LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
                i2 = i4 == i5 ? 512 : 0;
                if (((~i) & i2) != 0 && z && this.mBaseContextAttached && (sCanReturnDifferentContext || this.mCreated)) {
                    obj = this.mHost;
                    if ((obj instanceof Activity) && !((Activity) obj).isChild()) {
                        if ((i2 & 8192) != 0) {
                            ((Activity) this.mHost).getWindow().getDecorView().setLayoutDirection(createOverrideAppConfiguration.getLayoutDirection());
                        }
                        Activity activity = (Activity) this.mHost;
                        int i6 = ActivityCompat.$r8$clinit;
                        activity.recreate();
                        z3 = true;
                        if (!z3 || i2 == 0) {
                            z4 = z3;
                        } else {
                            boolean z5 = (i2 & i) == i2;
                            Resources resources = this.mContext.getResources();
                            Configuration configuration2 = new Configuration(resources.getConfiguration());
                            configuration2.uiMode = (resources.getConfiguration().uiMode & (-49)) | i5;
                            resources.updateConfiguration(configuration2, null);
                            int i7 = this.mThemeResId;
                            if (i7 != 0) {
                                this.mContext.setTheme(i7);
                                this.mContext.getTheme().applyStyle(this.mThemeResId, true);
                            }
                            if (z5) {
                                Object obj2 = this.mHost;
                                if (obj2 instanceof Activity) {
                                    Activity activity2 = (Activity) obj2;
                                    if (activity2 instanceof LifecycleOwner) {
                                        if (((LifecycleOwner) activity2).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                                            activity2.onConfigurationChanged(configuration2);
                                        }
                                    } else if (this.mCreated && !this.mDestroyed) {
                                        activity2.onConfigurationChanged(configuration2);
                                    }
                                }
                            }
                        }
                        if (z4) {
                            Object obj3 = this.mHost;
                            if (obj3 instanceof AppCompatActivity) {
                                if ((i2 & 512) != 0) {
                                    ((AppCompatActivity) obj3).getClass();
                                }
                                if ((i2 & 4) != 0) {
                                    ((AppCompatActivity) this.mHost).getClass();
                                }
                            }
                        }
                        if (i3 != 0) {
                            Context context2 = this.mContext;
                            if (this.mAutoTimeNightModeManager == null) {
                                this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(context2));
                            }
                            this.mAutoTimeNightModeManager.setup();
                        } else {
                            AutoTimeNightModeManager autoTimeNightModeManager = this.mAutoTimeNightModeManager;
                            if (autoTimeNightModeManager != null) {
                                autoTimeNightModeManager.cleanup();
                            }
                        }
                        if (i3 != 3) {
                            Context context3 = this.mContext;
                            if (this.mAutoBatteryNightModeManager == null) {
                                this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context3);
                            }
                            this.mAutoBatteryNightModeManager.setup();
                        } else {
                            AutoBatteryNightModeManager autoBatteryNightModeManager = this.mAutoBatteryNightModeManager;
                            if (autoBatteryNightModeManager != null) {
                                autoBatteryNightModeManager.cleanup();
                            }
                        }
                        return z4;
                    }
                }
                z3 = false;
                if (z3) {
                }
                z4 = z3;
                if (z4) {
                }
                if (i3 != 0) {
                }
                if (i3 != 3) {
                }
                return z4;
            }
            try {
                ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(context, this.mHost.getClass()), 269221888);
                if (activityInfo != null) {
                    this.mActivityHandlesConfigFlags = activityInfo.configChanges;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                this.mActivityHandlesConfigFlags = 0;
            }
        }
        this.mActivityHandlesConfigFlagsChecked = true;
        i = this.mActivityHandlesConfigFlags;
        configuration = this.mEffectiveConfiguration;
        if (configuration == null) {
        }
        int i42 = configuration.uiMode & 48;
        int i52 = createOverrideAppConfiguration.uiMode & 48;
        LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
        if (i42 == i52) {
        }
        if (((~i) & i2) != 0) {
            obj = this.mHost;
            if (obj instanceof Activity) {
                if ((i2 & 8192) != 0) {
                }
                Activity activity3 = (Activity) this.mHost;
                int i62 = ActivityCompat.$r8$clinit;
                activity3.recreate();
                z3 = true;
                if (z3) {
                }
                z4 = z3;
                if (z4) {
                }
                if (i3 != 0) {
                }
                if (i3 != 3) {
                }
                return z4;
            }
        }
        z3 = false;
        if (z3) {
        }
        z4 = z3;
        if (z4) {
        }
        if (i3 != 0) {
        }
        if (i3 != 3) {
        }
        return z4;
    }

    private void attachToWindow(Window window) {
        AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0 appCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0;
        if (this.mWindow != null) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        Window.Callback callback = window.getCallback();
        if (callback instanceof AppCompatWindowCallback) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        AppCompatWindowCallback appCompatWindowCallback = new AppCompatWindowCallback(callback);
        this.mAppCompatWindowCallback = appCompatWindowCallback;
        window.setCallback(appCompatWindowCallback);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mContext, (AttributeSet) null, sWindowBackgroundStyleable);
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            window.setBackgroundDrawable(drawableIfKnown);
        }
        obtainStyledAttributes.recycle();
        this.mWindow = window;
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mDispatcher;
        if (onBackInvokedDispatcher == null) {
            if (onBackInvokedDispatcher != null && (appCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0 = this.mBackCallback) != null) {
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(appCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0);
                this.mBackCallback = null;
            }
            Object obj = this.mHost;
            if (!(obj instanceof Activity) || ((Activity) obj).getWindow() == null) {
                this.mDispatcher = null;
            } else {
                this.mDispatcher = ((Activity) this.mHost).getOnBackInvokedDispatcher();
            }
            updateBackInvokedCallbackState();
        }
    }

    private static Configuration createOverrideAppConfiguration(Context context, int i, Configuration configuration, boolean z) {
        int i2 = i != 1 ? i != 2 ? z ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 48 : 32 : 16;
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i2 | (configuration2.uiMode & (-49));
        return configuration2;
    }

    private void ensureSubDecor() {
        ViewGroup viewGroup;
        if (this.mSubDecorInstalled) {
            return;
        }
        Context context = this.mContext;
        int[] iArr = R$styleable.AppCompatTheme;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!obtainStyledAttributes.hasValue(117)) {
            obtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (obtainStyledAttributes.getBoolean(126, false)) {
            requestWindowFeature(1);
        } else if (obtainStyledAttributes.getBoolean(117, false)) {
            requestWindowFeature(108);
        }
        if (obtainStyledAttributes.getBoolean(118, false)) {
            requestWindowFeature(109);
        }
        if (obtainStyledAttributes.getBoolean(119, false)) {
            requestWindowFeature(10);
        }
        this.mIsFloating = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        ensureWindow();
        this.mWindow.getDecorView();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (this.mWindowNoTitle) {
            viewGroup = this.mOverlayActionMode ? (ViewGroup) from.inflate(com.samsung.android.biometrics.app.setting.R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) from.inflate(com.samsung.android.biometrics.app.setting.R.layout.abc_screen_simple, (ViewGroup) null);
        } else if (this.mIsFloating) {
            viewGroup = (ViewGroup) from.inflate(com.samsung.android.biometrics.app.setting.R.layout.abc_dialog_title_material, (ViewGroup) null);
            this.mOverlayActionBar = false;
            this.mHasActionBar = false;
        } else if (this.mHasActionBar) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new ContextThemeWrapper(this.mContext, typedValue.resourceId) : this.mContext).inflate(com.samsung.android.biometrics.app.setting.R.layout.abc_screen_toolbar, (ViewGroup) null);
            DecorContentParent decorContentParent = (DecorContentParent) viewGroup.findViewById(com.samsung.android.biometrics.app.setting.R.id.decor_content_parent);
            this.mDecorContentParent = decorContentParent;
            decorContentParent.setWindowCallback(getWindowCallback());
            if (this.mOverlayActionBar) {
                this.mDecorContentParent.initFeature(109);
            }
            if (this.mFeatureProgress) {
                this.mDecorContentParent.initFeature(2);
            }
            if (this.mFeatureIndeterminateProgress) {
                this.mDecorContentParent.initFeature(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }");
        }
        ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                int updateStatusGuard = AppCompatDelegateImpl.this.updateStatusGuard(windowInsetsCompat);
                if (systemWindowInsetTop != updateStatusGuard) {
                    windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), updateStatusGuard, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
            }
        });
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(com.samsung.android.biometrics.app.setting.R.id.title);
        }
        int i = ViewUtils.$r8$clinit;
        try {
            Method method = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(viewGroup, new Object[0]);
        } catch (IllegalAccessException e) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e);
        } catch (NoSuchMethodException unused) {
            Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
        } catch (InvocationTargetException e2) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e2);
        }
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(com.samsung.android.biometrics.app.setting.R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.mWindow.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.mWindow.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.5
            @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
            public final void onDetachedFromWindow() {
                AppCompatDelegateImpl.this.dismissPopups();
            }
        });
        this.mSubDecor = viewGroup;
        Object obj = this.mHost;
        CharSequence title = obj instanceof Activity ? ((Activity) obj).getTitle() : this.mTitle;
        if (!TextUtils.isEmpty(title)) {
            DecorContentParent decorContentParent2 = this.mDecorContentParent;
            if (decorContentParent2 != null) {
                decorContentParent2.setWindowTitle(title);
            } else {
                WindowDecorActionBar windowDecorActionBar = this.mActionBar;
                if (windowDecorActionBar != null) {
                    windowDecorActionBar.mDecorToolbar.setWindowTitle(title);
                } else {
                    TextView textView = this.mTitleView;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
        }
        ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.mSubDecor.findViewById(R.id.content);
        View decorView = this.mWindow.getDecorView();
        contentFrameLayout2.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(iArr);
        obtainStyledAttributes2.getValue(124, contentFrameLayout2.getMinWidthMajor());
        obtainStyledAttributes2.getValue(125, contentFrameLayout2.getMinWidthMinor());
        if (obtainStyledAttributes2.hasValue(122)) {
            obtainStyledAttributes2.getValue(122, contentFrameLayout2.getFixedWidthMajor());
        }
        if (obtainStyledAttributes2.hasValue(123)) {
            obtainStyledAttributes2.getValue(123, contentFrameLayout2.getFixedWidthMinor());
        }
        if (obtainStyledAttributes2.hasValue(120)) {
            obtainStyledAttributes2.getValue(120, contentFrameLayout2.getFixedHeightMajor());
        }
        if (obtainStyledAttributes2.hasValue(121)) {
            obtainStyledAttributes2.getValue(121, contentFrameLayout2.getFixedHeightMinor());
        }
        obtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.mSubDecorInstalled = true;
        PanelFeatureState panelState = getPanelState(0);
        if (this.mDestroyed || panelState.menu != null) {
            return;
        }
        this.mInvalidatePanelMenuFeatures |= 4096;
        if (this.mInvalidatePanelMenuPosted) {
            return;
        }
        ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
        this.mInvalidatePanelMenuPosted = true;
    }

    private void ensureWindow() {
        if (this.mWindow == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                attachToWindow(((Activity) obj).getWindow());
            }
        }
        if (this.mWindow == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    private void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) this.mHost, this.mOverlayActionBar);
            } else if (obj instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) this.mHost);
            }
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                windowDecorActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:90:0x0131, code lost:
    
        if (r14 != null) goto L73;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        boolean z;
        int i;
        ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.isOpen || this.mDestroyed) {
            return;
        }
        if (panelFeatureState.featureId == 0) {
            if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                return;
            }
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !windowCallback.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return;
        }
        ViewGroup viewGroup = panelFeatureState.decorView;
        if (viewGroup == null || panelFeatureState.refreshDecorView) {
            if (viewGroup == null) {
                initWindowDecorActionBar();
                WindowDecorActionBar windowDecorActionBar = this.mActionBar;
                Context themedContext = windowDecorActionBar != null ? windowDecorActionBar.getThemedContext() : null;
                if (themedContext == null) {
                    themedContext = this.mContext;
                }
                TypedValue typedValue = new TypedValue();
                Resources.Theme newTheme = themedContext.getResources().newTheme();
                newTheme.setTo(themedContext.getTheme());
                newTheme.resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarPopupTheme, typedValue, true);
                int i2 = typedValue.resourceId;
                if (i2 != 0) {
                    newTheme.applyStyle(i2, true);
                }
                newTheme.resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.panelMenuListTheme, typedValue, true);
                int i3 = typedValue.resourceId;
                if (i3 != 0) {
                    newTheme.applyStyle(i3, true);
                } else {
                    newTheme.applyStyle(com.samsung.android.biometrics.app.setting.R.style.Theme_AppCompat_CompactMenu, true);
                }
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(themedContext, 0);
                contextThemeWrapper.getTheme().setTo(newTheme);
                panelFeatureState.listPresenterContext = contextThemeWrapper;
                TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R$styleable.AppCompatTheme);
                panelFeatureState.background = obtainStyledAttributes.getResourceId(86, 0);
                panelFeatureState.windowAnimations = obtainStyledAttributes.getResourceId(1, 0);
                obtainStyledAttributes.recycle();
                panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
                panelFeatureState.gravity = 81;
            } else if (panelFeatureState.refreshDecorView && viewGroup.getChildCount() > 0) {
                panelFeatureState.decorView.removeAllViews();
            }
            View view = panelFeatureState.createdPanelView;
            if (view == null) {
                if (panelFeatureState.menu != null) {
                    if (this.mPanelMenuPresenterCallback == null) {
                        this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
                    }
                    PanelMenuPresenterCallback panelMenuPresenterCallback = this.mPanelMenuPresenterCallback;
                    if (panelFeatureState.listMenuPresenter == null) {
                        ListMenuPresenter listMenuPresenter = new ListMenuPresenter(panelFeatureState.listPresenterContext);
                        panelFeatureState.listMenuPresenter = listMenuPresenter;
                        listMenuPresenter.setCallback(panelMenuPresenterCallback);
                        panelFeatureState.menu.addMenuPresenter(panelFeatureState.listMenuPresenter);
                    }
                    View view2 = (View) panelFeatureState.listMenuPresenter.getMenuView(panelFeatureState.decorView);
                    panelFeatureState.shownPanelView = view2;
                }
                z = false;
                if (z) {
                    if (panelFeatureState.shownPanelView != null && (panelFeatureState.createdPanelView != null || panelFeatureState.listMenuPresenter.getAdapter().getCount() > 0)) {
                        ViewGroup.LayoutParams layoutParams2 = panelFeatureState.shownPanelView.getLayoutParams();
                        if (layoutParams2 == null) {
                            layoutParams2 = new ViewGroup.LayoutParams(-2, -2);
                        }
                        panelFeatureState.decorView.setBackgroundResource(panelFeatureState.background);
                        ViewParent parent = panelFeatureState.shownPanelView.getParent();
                        if (parent instanceof ViewGroup) {
                            ((ViewGroup) parent).removeView(panelFeatureState.shownPanelView);
                        }
                        panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, layoutParams2);
                        if (!panelFeatureState.shownPanelView.hasFocus()) {
                            panelFeatureState.shownPanelView.requestFocus();
                        }
                    }
                }
                panelFeatureState.refreshDecorView = true;
                return;
            }
            panelFeatureState.shownPanelView = view;
            z = true;
            if (z) {
            }
            panelFeatureState.refreshDecorView = true;
            return;
        }
        View view3 = panelFeatureState.createdPanelView;
        if (view3 != null && (layoutParams = view3.getLayoutParams()) != null && layoutParams.width == -1) {
            i = -1;
            panelFeatureState.isHandled = false;
            WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
            layoutParams3.gravity = panelFeatureState.gravity;
            layoutParams3.windowAnimations = panelFeatureState.windowAnimations;
            windowManager.addView(panelFeatureState.decorView, layoutParams3);
            panelFeatureState.isOpen = true;
            if (panelFeatureState.featureId != 0) {
                updateBackInvokedCallbackState();
                return;
            }
            return;
        }
        i = -2;
        panelFeatureState.isHandled = false;
        WindowManager.LayoutParams layoutParams32 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
        layoutParams32.gravity = panelFeatureState.gravity;
        layoutParams32.windowAnimations = panelFeatureState.windowAnimations;
        windowManager.addView(panelFeatureState.decorView, layoutParams32);
        panelFeatureState.isOpen = true;
        if (panelFeatureState.featureId != 0) {
        }
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent) {
        MenuBuilder menuBuilder;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && (menuBuilder = panelFeatureState.menu) != null) {
            return menuBuilder.performShortcut(i, keyEvent, 1);
        }
        return false;
    }

    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        Resources.Theme theme;
        DecorContentParent decorContentParent3;
        DecorContentParent decorContentParent4;
        if (this.mDestroyed) {
            return false;
        }
        if (panelFeatureState.isPrepared) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
            closePanel(panelFeatureState2, false);
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null) {
            panelFeatureState.createdPanelView = windowCallback.onCreatePanelView(panelFeatureState.featureId);
        }
        int i = panelFeatureState.featureId;
        boolean z = i == 0 || i == 108;
        if (z && (decorContentParent4 = this.mDecorContentParent) != null) {
            decorContentParent4.setMenuPrepared();
        }
        if (panelFeatureState.createdPanelView == null) {
            MenuBuilder menuBuilder = panelFeatureState.menu;
            if (menuBuilder == null || panelFeatureState.refreshMenuContent) {
                if (menuBuilder == null) {
                    Context context = this.mContext;
                    int i2 = panelFeatureState.featureId;
                    if ((i2 == 0 || i2 == 108) && this.mDecorContentParent != null) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme2 = context.getTheme();
                        theme2.resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            theme = context.getResources().newTheme();
                            theme.setTo(theme2);
                            theme.applyStyle(typedValue.resourceId, true);
                            theme.resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarWidgetTheme, typedValue, true);
                        } else {
                            theme2.resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarWidgetTheme, typedValue, true);
                            theme = null;
                        }
                        if (typedValue.resourceId != 0) {
                            if (theme == null) {
                                theme = context.getResources().newTheme();
                                theme.setTo(theme2);
                            }
                            theme.applyStyle(typedValue.resourceId, true);
                        }
                        if (theme != null) {
                            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                            contextThemeWrapper.getTheme().setTo(theme);
                            context = contextThemeWrapper;
                        }
                    }
                    MenuBuilder menuBuilder2 = new MenuBuilder(context);
                    menuBuilder2.setCallback(this);
                    MenuBuilder menuBuilder3 = panelFeatureState.menu;
                    if (menuBuilder2 != menuBuilder3) {
                        if (menuBuilder3 != null) {
                            menuBuilder3.removeMenuPresenter(panelFeatureState.listMenuPresenter);
                        }
                        panelFeatureState.menu = menuBuilder2;
                        ListMenuPresenter listMenuPresenter = panelFeatureState.listMenuPresenter;
                        if (listMenuPresenter != null) {
                            menuBuilder2.addMenuPresenter(listMenuPresenter);
                        }
                    }
                    if (panelFeatureState.menu == null) {
                        return false;
                    }
                }
                if (z && (decorContentParent2 = this.mDecorContentParent) != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    decorContentParent2.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (!windowCallback.onCreatePanelMenu(panelFeatureState.featureId, panelFeatureState.menu)) {
                    MenuBuilder menuBuilder4 = panelFeatureState.menu;
                    if (menuBuilder4 != null) {
                        if (menuBuilder4 != null) {
                            menuBuilder4.removeMenuPresenter(panelFeatureState.listMenuPresenter);
                        }
                        panelFeatureState.menu = null;
                    }
                    if (z && (decorContentParent = this.mDecorContentParent) != null) {
                        decorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            Bundle bundle = panelFeatureState.frozenActionViewState;
            if (bundle != null) {
                panelFeatureState.menu.restoreActionViewStates(bundle);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!windowCallback.onPreparePanel(0, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                if (z && (decorContentParent3 = this.mDecorContentParent) != null) {
                    decorContentParent3.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(R.id.content)).addView(view, layoutParams);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
    }

    public final void applyDayNight() {
        applyApplicationSpecificConfig(true, true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final Context attachBaseContext2(Context context) {
        this.mBaseContextAttached = true;
        int i = this.mLocalNightMode;
        if (i == -100) {
            i = AppCompatDelegate.getDefaultNightMode();
        }
        int mapNightMode = mapNightMode(context, i);
        if (AppCompatDelegate.isAutoStorageOptedIn(context)) {
            AppCompatDelegate.syncRequestedAndStoredLocales(context);
        }
        boolean z = false;
        Configuration configuration = null;
        if (sCanApplyOverrideConfiguration && (context instanceof android.view.ContextThemeWrapper)) {
            try {
                ((android.view.ContextThemeWrapper) context).applyOverrideConfiguration(createOverrideAppConfiguration(context, mapNightMode, null, false));
                return context;
            } catch (IllegalStateException unused) {
            }
        }
        if (context instanceof ContextThemeWrapper) {
            try {
                ((ContextThemeWrapper) context).applyOverrideConfiguration(createOverrideAppConfiguration(context, mapNightMode, null, false));
                return context;
            } catch (IllegalStateException unused2) {
            }
        }
        if (!sCanReturnDifferentContext) {
            return context;
        }
        Configuration configuration2 = new Configuration();
        configuration2.uiMode = -1;
        configuration2.fontScale = 0.0f;
        Configuration configuration3 = context.createConfigurationContext(configuration2).getResources().getConfiguration();
        Configuration configuration4 = context.getResources().getConfiguration();
        configuration3.uiMode = configuration4.uiMode;
        if (!configuration3.equals(configuration4)) {
            configuration = new Configuration();
            configuration.fontScale = 0.0f;
            if (configuration3.diff(configuration4) != 0) {
                float f = configuration3.fontScale;
                float f2 = configuration4.fontScale;
                if (f != f2) {
                    configuration.fontScale = f2;
                }
                int i2 = configuration3.mcc;
                int i3 = configuration4.mcc;
                if (i2 != i3) {
                    configuration.mcc = i3;
                }
                int i4 = configuration3.mnc;
                int i5 = configuration4.mnc;
                if (i4 != i5) {
                    configuration.mnc = i5;
                }
                LocaleList locales = configuration3.getLocales();
                LocaleList locales2 = configuration4.getLocales();
                if (!locales.equals(locales2)) {
                    configuration.setLocales(locales2);
                    configuration.locale = configuration4.locale;
                }
                int i6 = configuration3.touchscreen;
                int i7 = configuration4.touchscreen;
                if (i6 != i7) {
                    configuration.touchscreen = i7;
                }
                int i8 = configuration3.keyboard;
                int i9 = configuration4.keyboard;
                if (i8 != i9) {
                    configuration.keyboard = i9;
                }
                int i10 = configuration3.keyboardHidden;
                int i11 = configuration4.keyboardHidden;
                if (i10 != i11) {
                    configuration.keyboardHidden = i11;
                }
                int i12 = configuration3.navigation;
                int i13 = configuration4.navigation;
                if (i12 != i13) {
                    configuration.navigation = i13;
                }
                int i14 = configuration3.navigationHidden;
                int i15 = configuration4.navigationHidden;
                if (i14 != i15) {
                    configuration.navigationHidden = i15;
                }
                int i16 = configuration3.orientation;
                int i17 = configuration4.orientation;
                if (i16 != i17) {
                    configuration.orientation = i17;
                }
                int i18 = configuration3.screenLayout & 15;
                int i19 = configuration4.screenLayout & 15;
                if (i18 != i19) {
                    configuration.screenLayout |= i19;
                }
                int i20 = configuration3.screenLayout & 192;
                int i21 = configuration4.screenLayout & 192;
                if (i20 != i21) {
                    configuration.screenLayout |= i21;
                }
                int i22 = configuration3.screenLayout & 48;
                int i23 = configuration4.screenLayout & 48;
                if (i22 != i23) {
                    configuration.screenLayout |= i23;
                }
                int i24 = configuration3.screenLayout & 768;
                int i25 = configuration4.screenLayout & 768;
                if (i24 != i25) {
                    configuration.screenLayout |= i25;
                }
                int i26 = configuration3.colorMode & 3;
                int i27 = configuration4.colorMode & 3;
                if (i26 != i27) {
                    configuration.colorMode |= i27;
                }
                int i28 = configuration3.colorMode & 12;
                int i29 = configuration4.colorMode & 12;
                if (i28 != i29) {
                    configuration.colorMode |= i29;
                }
                int i30 = configuration3.uiMode & 15;
                int i31 = configuration4.uiMode & 15;
                if (i30 != i31) {
                    configuration.uiMode |= i31;
                }
                int i32 = configuration3.uiMode & 48;
                int i33 = configuration4.uiMode & 48;
                if (i32 != i33) {
                    configuration.uiMode |= i33;
                }
                int i34 = configuration3.screenWidthDp;
                int i35 = configuration4.screenWidthDp;
                if (i34 != i35) {
                    configuration.screenWidthDp = i35;
                }
                int i36 = configuration3.screenHeightDp;
                int i37 = configuration4.screenHeightDp;
                if (i36 != i37) {
                    configuration.screenHeightDp = i37;
                }
                int i38 = configuration3.smallestScreenWidthDp;
                int i39 = configuration4.smallestScreenWidthDp;
                if (i38 != i39) {
                    configuration.smallestScreenWidthDp = i39;
                }
                int i40 = configuration3.densityDpi;
                int i41 = configuration4.densityDpi;
                if (i40 != i41) {
                    configuration.densityDpi = i41;
                }
            }
        }
        Configuration createOverrideAppConfiguration = createOverrideAppConfiguration(context, mapNightMode, configuration, true);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, com.samsung.android.biometrics.app.setting.R.style.Theme_AppCompat_Empty);
        contextThemeWrapper.applyOverrideConfiguration(createOverrideAppConfiguration);
        try {
            z = context.getTheme() != null;
        } catch (NullPointerException unused3) {
        }
        if (z) {
            contextThemeWrapper.getTheme().rebase();
        }
        return contextThemeWrapper;
    }

    final void callOnPanelClosed(int i, PanelFeatureState panelFeatureState, MenuBuilder menuBuilder) {
        if (menuBuilder == null) {
            if (panelFeatureState == null && i >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (i < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i];
                }
            }
            if (panelFeatureState != null) {
                menuBuilder = panelFeatureState.menu;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.isOpen) && !this.mDestroyed) {
            this.mAppCompatWindowCallback.bypassOnPanelClosed(this.mWindow.getCallback(), i, menuBuilder);
        }
    }

    final void checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !this.mDestroyed) {
            windowCallback.onPanelClosed(108, menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    final void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        ViewGroup viewGroup;
        DecorContentParent decorContentParent;
        if (z && panelFeatureState.featureId == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager != null && panelFeatureState.isOpen && (viewGroup = panelFeatureState.decorView) != null) {
            windowManager.removeView(viewGroup);
            if (z) {
                callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        panelFeatureState.refreshDecorView = true;
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null;
        }
        if (panelFeatureState.featureId == 0) {
            updateBackInvokedCallbackState();
        }
    }

    final void dismissPopups() {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.mActionModePopup = null;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
        MenuBuilder menuBuilder = getPanelState(0).menu;
        if (menuBuilder != null) {
            menuBuilder.close(true);
        }
    }

    final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        Object obj = this.mHost;
        if (((obj instanceof KeyEventDispatcher$Component) || (obj instanceof AppCompatDialog)) && this.mWindow.getDecorView() != null) {
            int i = ViewCompat.$r8$clinit;
        }
        if (keyEvent.getKeyCode() == 82 && this.mAppCompatWindowCallback.bypassDispatchKeyEvent(this.mWindow.getCallback(), keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() == 0) {
            if (keyCode == 4) {
                this.mLongPressBackDown = (keyEvent.getFlags() & 128) != 0;
            } else if (keyCode == 82) {
                if (keyEvent.getRepeatCount() != 0) {
                    return true;
                }
                PanelFeatureState panelState = getPanelState(0);
                if (panelState.isOpen) {
                    return true;
                }
                preparePanel(panelState, keyEvent);
                return true;
            }
        } else if (keyCode != 4) {
            if (keyCode == 82) {
                if (this.mActionMode != null) {
                    return true;
                }
                PanelFeatureState panelState2 = getPanelState(0);
                DecorContentParent decorContentParent = this.mDecorContentParent;
                if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
                    boolean z3 = panelState2.isOpen;
                    if (z3 || panelState2.isHandled) {
                        closePanel(panelState2, true);
                        z = z3;
                    } else {
                        if (panelState2.isPrepared) {
                            if (panelState2.refreshMenuContent) {
                                panelState2.isPrepared = false;
                                z2 = preparePanel(panelState2, keyEvent);
                            } else {
                                z2 = true;
                            }
                            if (z2) {
                                openPanel(panelState2, keyEvent);
                                z = true;
                            }
                        }
                        z = false;
                    }
                } else if (this.mDecorContentParent.isOverflowMenuShowing()) {
                    z = this.mDecorContentParent.hideOverflowMenu();
                } else {
                    if (!this.mDestroyed && preparePanel(panelState2, keyEvent)) {
                        z = this.mDecorContentParent.showOverflowMenu();
                    }
                    z = false;
                }
                if (!z) {
                    return true;
                }
                AudioManager audioManager = (AudioManager) this.mContext.getApplicationContext().getSystemService("audio");
                if (audioManager != null) {
                    audioManager.playSoundEffect(0);
                    return true;
                }
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
                return true;
            }
        } else if (onBackPressed()) {
            return true;
        }
        return false;
    }

    final void doInvalidatePanelMenu(int i) {
        PanelFeatureState panelState = getPanelState(i);
        if (panelState.menu != null) {
            Bundle bundle = new Bundle();
            panelState.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelState.frozenActionViewState = bundle;
            }
            panelState.menu.stopDispatchingItemsChanged();
            panelState.menu.clear();
        }
        panelState.refreshMenuContent = true;
        panelState.refreshDecorView = true;
        if ((i == 108 || i == 0) && this.mDecorContentParent != null) {
            PanelFeatureState panelState2 = getPanelState(0);
            panelState2.isPrepared = false;
            preparePanel(panelState2, null);
        }
    }

    final PanelFeatureState findMenuPanel(MenuBuilder menuBuilder) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.menu == menuBuilder) {
                return panelFeatureState;
            }
        }
        return null;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final <T extends View> T findViewById(int i) {
        ensureSubDecor();
        return (T) this.mWindow.findViewById(i);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final Context getContextForDelegate() {
        return this.mContext;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final int getLocalNightMode() {
        return this.mLocalNightMode;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            this.mMenuInflater = new SupportMenuInflater(windowDecorActionBar != null ? windowDecorActionBar.getThemedContext() : this.mContext);
        }
        return this.mMenuInflater;
    }

    protected final PanelFeatureState getPanelState(int i) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i);
        panelFeatureStateArr[i] = panelFeatureState2;
        return panelFeatureState2;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final WindowDecorActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void installViewFactory() {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (from.getFactory() == null) {
            from.setFactory2(this);
        } else {
            if (from.getFactory2() instanceof AppCompatDelegateImpl) {
                return;
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void invalidateOptionsMenu() {
        if (this.mActionBar != null) {
            initWindowDecorActionBar();
            this.mActionBar.getClass();
            this.mInvalidatePanelMenuFeatures |= 1;
            if (this.mInvalidatePanelMenuPosted) {
                return;
            }
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    public final boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    final int mapNightMode(Context context, int i) {
        if (i == -100) {
            return -1;
        }
        if (i != -1) {
            if (i == 0) {
                if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                    return -1;
                }
                if (this.mAutoTimeNightModeManager == null) {
                    this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(context));
                }
                return this.mAutoTimeNightModeManager.getApplyableNightMode();
            }
            if (i != 1 && i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                }
                if (this.mAutoBatteryNightModeManager == null) {
                    this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
                }
                return this.mAutoBatteryNightModeManager.getApplyableNightMode();
            }
        }
        return i;
    }

    final boolean onBackPressed() {
        boolean z;
        boolean z2 = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        PanelFeatureState panelState = getPanelState(0);
        if (panelState.isOpen) {
            if (!z2) {
                closePanel(panelState, true);
            }
            return true;
        }
        androidx.appcompat.view.ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
            return true;
        }
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            DecorToolbar decorToolbar = windowDecorActionBar.mDecorToolbar;
            if (decorToolbar == null || !decorToolbar.hasExpandedActionView()) {
                z = false;
            } else {
                windowDecorActionBar.mDecorToolbar.collapseActionView();
                z = true;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mHasActionBar && this.mSubDecorInstalled) {
            initWindowDecorActionBar();
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                windowDecorActionBar.onConfigurationChanged();
            }
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
        applyApplicationSpecificConfig(false, false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onCreate() {
        String str;
        this.mBaseContextAttached = true;
        applyApplicationSpecificConfig(false, true);
        ensureWindow();
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            try {
                Activity activity = (Activity) obj;
                try {
                    str = NavUtils.getParentActivityName(activity, activity.getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            } catch (IllegalArgumentException unused) {
                str = null;
            }
            if (str != null) {
                WindowDecorActionBar windowDecorActionBar = this.mActionBar;
                if (windowDecorActionBar == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    windowDecorActionBar.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            AppCompatDelegate.addActiveDelegate(this);
        }
        this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
        this.mCreated = true;
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        if (this.mAppCompatViewInflater == null) {
            String string = this.mContext.obtainStyledAttributes(R$styleable.AppCompatTheme).getString(116);
            if (string == null) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) this.mContext.getClassLoader().loadClass(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        AppCompatViewInflater appCompatViewInflater = this.mAppCompatViewInflater;
        int i = VectorEnabledTintResources.$r8$clinit;
        return appCompatViewInflater.createView(view, str, context, attributeSet);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    @Override // androidx.appcompat.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDestroy() {
        AutoTimeNightModeManager autoTimeNightModeManager;
        AutoBatteryNightModeManager autoBatteryNightModeManager;
        if (this.mHost instanceof Activity) {
            AppCompatDelegate.removeActivityDelegate(this);
        }
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        this.mDestroyed = true;
        if (this.mLocalNightMode != -100) {
            Object obj = this.mHost;
            if ((obj instanceof Activity) && ((Activity) obj).isChangingConfigurations()) {
                sLocalNightModes.put(this.mHost.getClass().getName(), Integer.valueOf(this.mLocalNightMode));
                autoTimeNightModeManager = this.mAutoTimeNightModeManager;
                if (autoTimeNightModeManager != null) {
                    autoTimeNightModeManager.cleanup();
                }
                autoBatteryNightModeManager = this.mAutoBatteryNightModeManager;
                if (autoBatteryNightModeManager == null) {
                    autoBatteryNightModeManager.cleanup();
                    return;
                }
                return;
            }
        }
        sLocalNightModes.remove(this.mHost.getClass().getName());
        autoTimeNightModeManager = this.mAutoTimeNightModeManager;
        if (autoTimeNightModeManager != null) {
        }
        autoBatteryNightModeManager = this.mAutoBatteryNightModeManager;
        if (autoBatteryNightModeManager == null) {
        }
    }

    final boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        boolean z;
        MenuBuilder menu;
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            WindowDecorActionBar.ActionModeImpl actionModeImpl = windowDecorActionBar.mActionMode;
            if (actionModeImpl == null || (menu = actionModeImpl.getMenu()) == null) {
                z = false;
            } else {
                menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
                z = menu.performShortcut(i, keyEvent, 0);
            }
            if (z) {
                return true;
            }
        }
        PanelFeatureState panelFeatureState = this.mPreparedPanel;
        if (panelFeatureState != null && performPanelShortcut(panelFeatureState, keyEvent.getKeyCode(), keyEvent)) {
            PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
            if (panelFeatureState2 != null) {
                panelFeatureState2.isHandled = true;
            }
            return true;
        }
        if (this.mPreparedPanel == null) {
            PanelFeatureState panelState = getPanelState(0);
            preparePanel(panelState, keyEvent);
            boolean performPanelShortcut = performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent);
            panelState.isPrepared = false;
            if (performPanelShortcut) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        PanelFeatureState findMenuPanel;
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback == null || this.mDestroyed || (findMenuPanel = findMenuPanel(menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return windowCallback.onMenuItemSelected(findMenuPanel.featureId, menuItem);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public final void onMenuModeChange(MenuBuilder menuBuilder) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.mContext).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState panelState = getPanelState(0);
            panelState.refreshDecorView = true;
            closePanel(panelState, false);
            openPanel(panelState, null);
            return;
        }
        Window.Callback windowCallback = getWindowCallback();
        if (this.mDecorContentParent.isOverflowMenuShowing()) {
            this.mDecorContentParent.hideOverflowMenu();
            if (this.mDestroyed) {
                return;
            }
            windowCallback.onPanelClosed(108, getPanelState(0).menu);
            return;
        }
        if (windowCallback == null || this.mDestroyed) {
            return;
        }
        if (this.mInvalidatePanelMenuPosted && (1 & this.mInvalidatePanelMenuFeatures) != 0) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
            ((RunnableC00142) this.mInvalidatePanelMenuRunnable).run();
        }
        PanelFeatureState panelState2 = getPanelState(0);
        MenuBuilder menuBuilder2 = panelState2.menu;
        if (menuBuilder2 == null || panelState2.refreshMenuContent || !windowCallback.onPreparePanel(0, panelState2.createdPanelView, menuBuilder2)) {
            return;
        }
        windowCallback.onMenuOpened(108, panelState2.menu);
        this.mDecorContentParent.showOverflowMenu();
    }

    final void onMenuOpened(int i) {
        if (i == 108) {
            initWindowDecorActionBar();
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                windowDecorActionBar.dispatchMenuVisibilityChanged(true);
            }
        }
    }

    final void onPanelClosed(int i) {
        if (i == 108) {
            initWindowDecorActionBar();
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                windowDecorActionBar.dispatchMenuVisibilityChanged(false);
                return;
            }
            return;
        }
        if (i == 0) {
            PanelFeatureState panelState = getPanelState(i);
            if (panelState.isOpen) {
                closePanel(panelState, false);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onPostCreate() {
        ensureSubDecor();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onPostResume() {
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onStart() {
        applyApplicationSpecificConfig(true, false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onStop() {
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.setShowHideAnimationEnabled(false);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final boolean requestWindowFeature(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            i = 108;
        } else if (i == 9) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            i = 109;
        }
        if (this.mWindowNoTitle && i == 108) {
            return false;
        }
        if (this.mHasActionBar && i == 1) {
            this.mHasActionBar = false;
        }
        if (i == 1) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mWindowNoTitle = true;
            return true;
        }
        if (i == 2) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureProgress = true;
            return true;
        }
        if (i == 5) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureIndeterminateProgress = true;
            return true;
        }
        if (i == 10) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mOverlayActionMode = true;
            return true;
        }
        if (i == 108) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
        }
        if (i != 109) {
            return this.mWindow.requestFeature(i);
        }
        throwFeatureRequestIfSubDecorInstalled();
        this.mOverlayActionBar = true;
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setContentView(View view) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTheme(int i) {
        this.mThemeResId = i;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
            return;
        }
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mDecorToolbar.setWindowTitle(charSequence);
            return;
        }
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    final boolean shouldAnimateActionModeView() {
        ViewGroup viewGroup;
        return this.mSubDecorInstalled && (viewGroup = this.mSubDecor) != null && ViewCompat.isLaidOut(viewGroup);
    }

    public final androidx.appcompat.view.ActionMode startSupportActionMode(SupportActionModeWrapper.CallbackWrapper callbackWrapper) {
        AppCompatCallback appCompatCallback;
        Context context;
        AppCompatCallback appCompatCallback2;
        androidx.appcompat.view.ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = new ActionModeCallbackWrapperV9(callbackWrapper);
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            WindowDecorActionBar.ActionModeImpl actionModeImpl = windowDecorActionBar.mActionMode;
            if (actionModeImpl != null) {
                actionModeImpl.finish();
            }
            windowDecorActionBar.mOverlayLayout.setHideOnContentScrollEnabled(false);
            windowDecorActionBar.mContextView.killMode();
            WindowDecorActionBar.ActionModeImpl actionModeImpl2 = windowDecorActionBar.new ActionModeImpl(windowDecorActionBar.mContextView.getContext(), actionModeCallbackWrapperV9);
            if (actionModeImpl2.dispatchOnCreate()) {
                windowDecorActionBar.mActionMode = actionModeImpl2;
                actionModeImpl2.invalidate();
                windowDecorActionBar.mContextView.initForMode(actionModeImpl2);
                windowDecorActionBar.animateToMode(true);
            } else {
                actionModeImpl2 = null;
            }
            this.mActionMode = actionModeImpl2;
            if (actionModeImpl2 != null && (appCompatCallback2 = this.mAppCompatCallback) != null) {
                appCompatCallback2.onSupportActionModeStarted();
            }
        }
        if (this.mActionMode == null) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
            if (viewPropertyAnimatorCompat != null) {
                viewPropertyAnimatorCompat.cancel();
            }
            androidx.appcompat.view.ActionMode actionMode2 = this.mActionMode;
            if (actionMode2 != null) {
                actionMode2.finish();
            }
            AppCompatCallback appCompatCallback3 = this.mAppCompatCallback;
            if (appCompatCallback3 != null && !this.mDestroyed) {
                try {
                    appCompatCallback3.onWindowStartingSupportActionMode();
                } catch (AbstractMethodError unused) {
                }
            }
            if (this.mActionModeView == null) {
                if (this.mIsFloating) {
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = this.mContext.getTheme();
                    theme.resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme newTheme = this.mContext.getResources().newTheme();
                        newTheme.setTo(theme);
                        newTheme.applyStyle(typedValue.resourceId, true);
                        context = new ContextThemeWrapper(this.mContext, 0);
                        context.getTheme().setTo(newTheme);
                    } else {
                        context = this.mContext;
                    }
                    this.mActionModeView = new ActionBarContextView(context);
                    PopupWindow popupWindow = new PopupWindow(context, (AttributeSet) null, com.samsung.android.biometrics.app.setting.R.attr.actionModePopupWindowStyle);
                    this.mActionModePopup = popupWindow;
                    popupWindow.setWindowLayoutType(2);
                    this.mActionModePopup.setContentView(this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    context.getTheme().resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarSize, typedValue, true);
                    this.mActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics()));
                    this.mActionModePopup.setHeight(-2);
                    this.mShowActionModePopup = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                            appCompatDelegateImpl.mActionModePopup.showAtLocation(appCompatDelegateImpl.mActionModeView, 55, 0, 0);
                            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = AppCompatDelegateImpl.this.mFadeAnim;
                            if (viewPropertyAnimatorCompat2 != null) {
                                viewPropertyAnimatorCompat2.cancel();
                            }
                            if (!AppCompatDelegateImpl.this.shouldAnimateActionModeView()) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                return;
                            }
                            AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                            ViewPropertyAnimatorCompat animate = ViewCompat.animate(appCompatDelegateImpl2.mActionModeView);
                            animate.alpha(1.0f);
                            appCompatDelegateImpl2.mFadeAnim = animate;
                            AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6.1
                                @Override // androidx.core.view.ViewPropertyAnimatorListener
                                public final void onAnimationEnd() {
                                    RunnableC00176 runnableC00176 = RunnableC00176.this;
                                    AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                    AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                    AppCompatDelegateImpl.this.mFadeAnim = null;
                                }

                                @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                public final void onAnimationStart() {
                                    AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                }
                            });
                        }
                    };
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) this.mSubDecor.findViewById(com.samsung.android.biometrics.app.setting.R.id.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        initWindowDecorActionBar();
                        WindowDecorActionBar windowDecorActionBar2 = this.mActionBar;
                        Context themedContext = windowDecorActionBar2 != null ? windowDecorActionBar2.getThemedContext() : null;
                        if (themedContext == null) {
                            themedContext = this.mContext;
                        }
                        viewStubCompat.setLayoutInflater(LayoutInflater.from(themedContext));
                        this.mActionModeView = (ActionBarContextView) viewStubCompat.inflate();
                    }
                }
            }
            if (this.mActionModeView != null) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = this.mFadeAnim;
                if (viewPropertyAnimatorCompat2 != null) {
                    viewPropertyAnimatorCompat2.cancel();
                }
                this.mActionModeView.killMode();
                StandaloneActionMode standaloneActionMode = new StandaloneActionMode(this.mActionModeView.getContext(), this.mActionModeView, actionModeCallbackWrapperV9);
                if (actionModeCallbackWrapperV9.onCreateActionMode(standaloneActionMode, standaloneActionMode.getMenu())) {
                    standaloneActionMode.invalidate();
                    this.mActionModeView.initForMode(standaloneActionMode);
                    this.mActionMode = standaloneActionMode;
                    if (shouldAnimateActionModeView()) {
                        this.mActionModeView.setAlpha(0.0f);
                        ViewPropertyAnimatorCompat animate = ViewCompat.animate(this.mActionModeView);
                        animate.alpha(1.0f);
                        this.mFadeAnim = animate;
                        animate.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.7
                            @Override // androidx.core.view.ViewPropertyAnimatorListener
                            public final void onAnimationEnd() {
                                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl.mActionModeView.setAlpha(1.0f);
                                appCompatDelegateImpl.mFadeAnim.setListener(null);
                                appCompatDelegateImpl.mFadeAnim = null;
                            }

                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                            public final void onAnimationStart() {
                                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl.mActionModeView.setVisibility(0);
                                if (appCompatDelegateImpl.mActionModeView.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) appCompatDelegateImpl.mActionModeView.getParent());
                                }
                            }
                        });
                    } else {
                        this.mActionModeView.setAlpha(1.0f);
                        this.mActionModeView.setVisibility(0);
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.mActionModeView.getParent());
                        }
                    }
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                } else {
                    this.mActionMode = null;
                }
            }
            if (this.mActionMode != null && (appCompatCallback = this.mAppCompatCallback) != null) {
                appCompatCallback.onSupportActionModeStarted();
            }
            updateBackInvokedCallbackState();
            this.mActionMode = this.mActionMode;
        }
        updateBackInvokedCallbackState();
        return this.mActionMode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.window.OnBackInvokedCallback, androidx.appcompat.app.AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0] */
    final void updateBackInvokedCallbackState() {
        AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0 appCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0;
        boolean z = false;
        if (this.mDispatcher != null && (getPanelState(0).isOpen || this.mActionMode != null)) {
            z = true;
        }
        if (z && this.mBackCallback == null) {
            OnBackInvokedDispatcher onBackInvokedDispatcher = this.mDispatcher;
            ?? r1 = new OnBackInvokedCallback() { // from class: androidx.appcompat.app.AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    AppCompatDelegateImpl.this.onBackPressed();
                }
            };
            onBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, r1);
            this.mBackCallback = r1;
            return;
        }
        if (z || (appCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0 = this.mBackCallback) == null) {
            return;
        }
        this.mDispatcher.unregisterOnBackInvokedCallback(appCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0);
        this.mBackCallback = null;
    }

    final int updateStatusGuard(WindowInsetsCompat windowInsetsCompat) {
        boolean z;
        boolean z2;
        int color;
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        ActionBarContextView actionBarContextView = this.mActionModeView;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mActionModeView.getLayoutParams();
            if (this.mActionModeView.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect rect = this.mTempRect1;
                Rect rect2 = this.mTempRect2;
                rect.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                ViewUtils.computeFitSystemWindows(rect, rect2, this.mSubDecor);
                int i = rect.top;
                int i2 = rect.left;
                int i3 = rect.right;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this.mSubDecor);
                int systemWindowInsetLeft = rootWindowInsets == null ? 0 : rootWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = rootWindowInsets == null ? 0 : rootWindowInsets.getSystemWindowInsetRight();
                if (marginLayoutParams.topMargin == i && marginLayoutParams.leftMargin == i2 && marginLayoutParams.rightMargin == i3) {
                    z2 = false;
                } else {
                    marginLayoutParams.topMargin = i;
                    marginLayoutParams.leftMargin = i2;
                    marginLayoutParams.rightMargin = i3;
                    z2 = true;
                }
                if (i <= 0 || this.mStatusGuard != null) {
                    View view = this.mStatusGuard;
                    if (view != null) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        int i4 = marginLayoutParams2.height;
                        int i5 = marginLayoutParams.topMargin;
                        if (i4 != i5 || marginLayoutParams2.leftMargin != systemWindowInsetLeft || marginLayoutParams2.rightMargin != systemWindowInsetRight) {
                            marginLayoutParams2.height = i5;
                            marginLayoutParams2.leftMargin = systemWindowInsetLeft;
                            marginLayoutParams2.rightMargin = systemWindowInsetRight;
                            this.mStatusGuard.setLayoutParams(marginLayoutParams2);
                        }
                    }
                } else {
                    View view2 = new View(this.mContext);
                    this.mStatusGuard = view2;
                    view2.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = systemWindowInsetLeft;
                    layoutParams.rightMargin = systemWindowInsetRight;
                    this.mSubDecor.addView(this.mStatusGuard, -1, layoutParams);
                }
                View view3 = this.mStatusGuard;
                z = view3 != null;
                if (z && view3.getVisibility() != 0) {
                    View view4 = this.mStatusGuard;
                    if ((ViewCompat.getWindowSystemUiVisibility(view4) & 8192) != 0) {
                        Context context = this.mContext;
                        int i6 = ContextCompat.$r8$clinit;
                        color = context.getColor(com.samsung.android.biometrics.app.setting.R.color.abc_decor_view_status_guard_light);
                    } else {
                        Context context2 = this.mContext;
                        int i7 = ContextCompat.$r8$clinit;
                        color = context2.getColor(com.samsung.android.biometrics.app.setting.R.color.abc_decor_view_status_guard);
                    }
                    view4.setBackgroundColor(color);
                }
                if (!this.mOverlayActionMode && z) {
                    systemWindowInsetTop = 0;
                }
                r5 = z2;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z = false;
            } else {
                r5 = false;
                z = false;
            }
            if (r5) {
                this.mActionModeView.setLayoutParams(marginLayoutParams);
            }
        }
        View view5 = this.mStatusGuard;
        if (view5 != null) {
            view5.setVisibility(z ? 0 : 8);
        }
        return systemWindowInsetTop;
    }

    AppCompatDelegateImpl(Dialog dialog, AppCompatCallback appCompatCallback) {
        this(dialog.getContext(), dialog.getWindow(), appCompatCallback, dialog);
    }

    private AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback, Object obj) {
        SimpleArrayMap<String, Integer> simpleArrayMap;
        Integer num;
        AppCompatActivity appCompatActivity = null;
        this.mFadeAnim = null;
        this.mHandleNativeActionModes = true;
        this.mLocalNightMode = -100;
        this.mInvalidatePanelMenuRunnable = new RunnableC00142();
        this.mContext = context;
        this.mAppCompatCallback = appCompatCallback;
        this.mHost = obj;
        if (obj instanceof Dialog) {
            while (true) {
                if (context != null) {
                    if (context instanceof AppCompatActivity) {
                        appCompatActivity = (AppCompatActivity) context;
                        break;
                    } else if (!(context instanceof ContextWrapper)) {
                        break;
                    } else {
                        context = ((ContextWrapper) context).getBaseContext();
                    }
                } else {
                    break;
                }
            }
            if (appCompatActivity != null) {
                this.mLocalNightMode = appCompatActivity.getDelegate().getLocalNightMode();
            }
        }
        if (this.mLocalNightMode == -100 && (num = (simpleArrayMap = sLocalNightModes).get(this.mHost.getClass().getName())) != null) {
            this.mLocalNightMode = num.intValue();
            simpleArrayMap.remove(this.mHost.getClass().getName());
        }
        if (window != null) {
            attachToWindow(window);
        }
        AppCompatDrawableManager.preload();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setContentView(int i) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(i, viewGroup);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onSaveInstanceState() {
    }
}
