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
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.app.ToolbarActionBar;
import androidx.appcompat.app.TwilightManager;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.ListMenuPresenter.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.sec.ims.gls.GlsIntent;
import com.sec.ims.presence.ServiceTuple;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    public ActionBar mActionBar;
    public ActionMenuPresenterCallback mActionMenuPresenterCallback;
    public ActionMode mActionMode;
    public PopupWindow mActionModePopup;
    public ActionBarContextView mActionModeView;
    public int mActivityHandlesConfigFlags;
    public boolean mActivityHandlesConfigFlagsChecked;
    public final AppCompatCallback mAppCompatCallback;
    public AppCompatViewInflater mAppCompatViewInflater;
    public AppCompatWindowCallback mAppCompatWindowCallback;
    public AutoBatteryNightModeManager mAutoBatteryNightModeManager;
    public AutoTimeNightModeManager mAutoTimeNightModeManager;
    public AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0 mBackCallback;
    public boolean mBaseContextAttached;
    public boolean mClosingActionMenu;
    public final Context mContext;
    public boolean mCreated;
    public DecorContentParent mDecorContentParent;
    public boolean mDestroyed;
    public OnBackInvokedDispatcher mDispatcher;
    public Configuration mEffectiveConfiguration;
    public boolean mEnableDefaultActionBarUp;
    public ViewPropertyAnimatorCompat mFadeAnim;
    public boolean mFeatureIndeterminateProgress;
    public boolean mFeatureProgress;
    public final boolean mHandleNativeActionModes;
    public boolean mHasActionBar;
    public final Object mHost;
    public int mInvalidatePanelMenuFeatures;
    public boolean mInvalidatePanelMenuPosted;
    public final RunnableC00282 mInvalidatePanelMenuRunnable;
    public boolean mIsFloating;
    public boolean mIsIgnoreRemoveSystemTopInset;
    public int mLocalNightMode;
    public boolean mLongPressBackDown;
    public SupportMenuInflater mMenuInflater;
    public boolean mOverlayActionBar;
    public boolean mOverlayActionMode;
    public PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    public PanelFeatureState[] mPanels;
    public PanelFeatureState mPreparedPanel;
    public RunnableC00316 mShowActionModePopup;
    public View mStatusGuard;
    public ViewGroup mSubDecor;
    public boolean mSubDecorInstalled;
    public Rect mTempRect1;
    public Rect mTempRect2;
    public int mThemeResId;
    public CharSequence mTitle;
    public TextView mTitleView;
    public Window mWindow;
    public boolean mWindowNoTitle;
    public static final SimpleArrayMap sLocalNightModes = new SimpleArrayMap();
    public static final int[] sWindowBackgroundStyleable = {R.attr.windowBackground};
    public static final boolean sCanReturnDifferentContext = !"robolectric".equals(Build.FINGERPRINT);
    public static final boolean sCanApplyOverrideConfiguration = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$2 */
    public final class RunnableC00282 implements Runnable {
        public RunnableC00282() {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$5 */
    public final class C00305 {
        public C00305() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        public ActionMenuPresenterCallback() {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        public final ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.mWrapped = callback;
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
                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                    public final void onAnimationEnd(View view) {
                        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = ActionModeCallbackWrapperV9.this;
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl2.mActionModePopup;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl2.mActionModeView.getParent() instanceof View) {
                            View view2 = (View) appCompatDelegateImpl2.mActionModeView.getParent();
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api20Impl.requestApplyInsets(view2);
                        }
                        appCompatDelegateImpl2.mActionModeView.killMode();
                        appCompatDelegateImpl2.mFadeAnim.setListener(null);
                        appCompatDelegateImpl2.mFadeAnim = null;
                        ViewGroup viewGroup = appCompatDelegateImpl2.mSubDecor;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api20Impl.requestApplyInsets(viewGroup);
                    }
                });
            }
            AppCompatCallback appCompatCallback = appCompatDelegateImpl.mAppCompatCallback;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished();
            }
            appCompatDelegateImpl.mActionMode = null;
            ViewGroup viewGroup = appCompatDelegateImpl.mSubDecor;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api20Impl.requestApplyInsets(viewGroup);
            appCompatDelegateImpl.updateBackInvokedCallbackState();
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public final boolean onPrepareActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            ViewGroup viewGroup = AppCompatDelegateImpl.this.mSubDecor;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api20Impl.requestApplyInsets(viewGroup);
            return this.mWrapped.onPrepareActionMode(actionMode, menuBuilder);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppCompatWindowCallback extends WindowCallbackWrapper {
        public ToolbarActionBar.ToolbarMenuCallback mActionBarCallback;
        public boolean mDispatchKeyEventBypassEnabled;
        public boolean mOnContentChangedBypassEnabled;
        public boolean mOnPanelClosedBypassEnabled;

        public AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        public final void bypassOnContentChanged(Window.Callback callback) {
            try {
                this.mOnContentChangedBypassEnabled = true;
                callback.onContentChanged();
            } finally {
                this.mOnContentChangedBypassEnabled = false;
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.mDispatchKeyEventBypassEnabled ? this.mWrapped.dispatchKeyEvent(keyEvent) : AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x0046, code lost:
        
            if (r4 != false) goto L20;
         */
        /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x004e  */
        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            boolean z;
            if (super.dispatchKeyShortcutEvent(keyEvent)) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            int keyCode = keyEvent.getKeyCode();
            appCompatDelegateImpl.initWindowDecorActionBar();
            ActionBar actionBar = appCompatDelegateImpl.mActionBar;
            if (actionBar == null || !actionBar.onKeyShortcut(keyCode, keyEvent)) {
                PanelFeatureState panelFeatureState = appCompatDelegateImpl.mPreparedPanel;
                if (panelFeatureState == null || !appCompatDelegateImpl.performPanelShortcut(panelFeatureState, keyEvent.getKeyCode(), keyEvent)) {
                    if (appCompatDelegateImpl.mPreparedPanel == null) {
                        PanelFeatureState panelState = appCompatDelegateImpl.getPanelState(0);
                        appCompatDelegateImpl.preparePanel(panelState, keyEvent);
                        boolean performPanelShortcut = appCompatDelegateImpl.performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent);
                        panelState.isPrepared = false;
                    }
                    z = false;
                    return !z;
                }
                PanelFeatureState panelFeatureState2 = appCompatDelegateImpl.mPreparedPanel;
                if (panelFeatureState2 != null) {
                    panelFeatureState2.isHandled = true;
                }
            }
            z = true;
            if (!z) {
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onContentChanged() {
            if (this.mOnContentChangedBypassEnabled) {
                this.mWrapped.onContentChanged();
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
            ToolbarActionBar.ToolbarMenuCallback toolbarMenuCallback = this.mActionBarCallback;
            if (toolbarMenuCallback != null) {
                View view = i == 0 ? new View(ToolbarActionBar.this.mDecorToolbar.mToolbar.getContext()) : null;
                if (view != null) {
                    return view;
                }
            }
            return super.onCreatePanelView(i);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i == 108) {
                appCompatDelegateImpl.initWindowDecorActionBar();
                ActionBar actionBar = appCompatDelegateImpl.mActionBar;
                if (actionBar != null) {
                    actionBar.dispatchMenuVisibilityChanged(true);
                }
            } else {
                appCompatDelegateImpl.getClass();
            }
            return true;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onPanelClosed(int i, Menu menu) {
            if (this.mOnPanelClosedBypassEnabled) {
                this.mWrapped.onPanelClosed(i, menu);
                return;
            }
            super.onPanelClosed(i, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i == 108) {
                appCompatDelegateImpl.initWindowDecorActionBar();
                ActionBar actionBar = appCompatDelegateImpl.mActionBar;
                if (actionBar != null) {
                    actionBar.dispatchMenuVisibilityChanged(false);
                    return;
                }
                return;
            }
            if (i != 0) {
                appCompatDelegateImpl.getClass();
                return;
            }
            PanelFeatureState panelState = appCompatDelegateImpl.getPanelState(i);
            if (panelState.isOpen) {
                appCompatDelegateImpl.closePanel(panelState, false);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.mOverrideVisibleItems = true;
            }
            ToolbarActionBar.ToolbarMenuCallback toolbarMenuCallback = this.mActionBarCallback;
            if (toolbarMenuCallback != null && i == 0) {
                ToolbarActionBar toolbarActionBar = ToolbarActionBar.this;
                if (!toolbarActionBar.mToolbarMenuPrepared) {
                    toolbarActionBar.mDecorToolbar.mMenuPrepared = true;
                    toolbarActionBar.mToolbarMenuPrepared = true;
                }
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.mOverrideVisibleItems = false;
            }
            return onPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onProvideKeyboardShortcuts(List list, Menu menu, int i) {
            MenuBuilder menuBuilder = AppCompatDelegateImpl.this.getPanelState(0).menu;
            if (menuBuilder != null) {
                super.onProvideKeyboardShortcuts(list, menuBuilder, i);
            } else {
                super.onProvideKeyboardShortcuts(list, menu, i);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:72:0x019b, code lost:
        
            if (androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r9) != false) goto L75;
         */
        /* JADX WARN: Type inference failed for: r0v38, types: [androidx.appcompat.app.AppCompatDelegateImpl$6] */
        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            AppCompatCallback appCompatCallback;
            Context context;
            AppCompatCallback appCompatCallback2;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.mHandleNativeActionModes || i != 0) {
                return super.onWindowStartingActionMode(callback, i);
            }
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(appCompatDelegateImpl.mContext, callback);
            final AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            androidx.appcompat.view.ActionMode actionMode = appCompatDelegateImpl2.mActionMode;
            if (actionMode != null) {
                actionMode.finish();
            }
            ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = appCompatDelegateImpl2.new ActionModeCallbackWrapperV9(callbackWrapper);
            appCompatDelegateImpl2.initWindowDecorActionBar();
            ActionBar actionBar = appCompatDelegateImpl2.mActionBar;
            if (actionBar != null) {
                androidx.appcompat.view.ActionMode startActionMode = actionBar.startActionMode(actionModeCallbackWrapperV9);
                appCompatDelegateImpl2.mActionMode = startActionMode;
                if (startActionMode != null && (appCompatCallback2 = appCompatDelegateImpl2.mAppCompatCallback) != null) {
                    appCompatCallback2.onSupportActionModeStarted();
                }
            }
            if (appCompatDelegateImpl2.mActionMode == null) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = appCompatDelegateImpl2.mFadeAnim;
                if (viewPropertyAnimatorCompat != null) {
                    viewPropertyAnimatorCompat.cancel();
                }
                androidx.appcompat.view.ActionMode actionMode2 = appCompatDelegateImpl2.mActionMode;
                if (actionMode2 != null) {
                    actionMode2.finish();
                }
                AppCompatCallback appCompatCallback3 = appCompatDelegateImpl2.mAppCompatCallback;
                if (appCompatCallback3 != null && !appCompatDelegateImpl2.mDestroyed) {
                    try {
                        appCompatCallback3.onWindowStartingSupportActionMode();
                    } catch (AbstractMethodError unused) {
                    }
                }
                boolean z = true;
                if (appCompatDelegateImpl2.mActionModeView == null) {
                    if (appCompatDelegateImpl2.mIsFloating) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = appCompatDelegateImpl2.mContext.getTheme();
                        theme.resolveAttribute(com.android.systemui.R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            Resources.Theme newTheme = appCompatDelegateImpl2.mContext.getResources().newTheme();
                            newTheme.setTo(theme);
                            newTheme.applyStyle(typedValue.resourceId, true);
                            context = new ContextThemeWrapper(appCompatDelegateImpl2.mContext, 0);
                            context.getTheme().setTo(newTheme);
                        } else {
                            context = appCompatDelegateImpl2.mContext;
                        }
                        appCompatDelegateImpl2.mActionModeView = new ActionBarContextView(context);
                        PopupWindow popupWindow = new PopupWindow(context, (AttributeSet) null, com.android.systemui.R.attr.actionModePopupWindowStyle);
                        appCompatDelegateImpl2.mActionModePopup = popupWindow;
                        popupWindow.setWindowLayoutType(2);
                        appCompatDelegateImpl2.mActionModePopup.setContentView(appCompatDelegateImpl2.mActionModeView);
                        appCompatDelegateImpl2.mActionModePopup.setWidth(-1);
                        context.getTheme().resolveAttribute(com.android.systemui.R.attr.actionBarSize, typedValue, true);
                        appCompatDelegateImpl2.mActionModeView.mContentHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
                        appCompatDelegateImpl2.mActionModePopup.setHeight(-2);
                        appCompatDelegateImpl2.mShowActionModePopup = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6
                            /* JADX WARN: Removed duplicated region for block: B:13:0x002e  */
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() {
                                boolean z2;
                                ViewGroup viewGroup;
                                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl3.mActionModePopup.showAtLocation(appCompatDelegateImpl3.mActionModeView, 55, 0, 0);
                                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = AppCompatDelegateImpl.this.mFadeAnim;
                                if (viewPropertyAnimatorCompat2 != null) {
                                    viewPropertyAnimatorCompat2.cancel();
                                }
                                AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
                                if (appCompatDelegateImpl4.mSubDecorInstalled && (viewGroup = appCompatDelegateImpl4.mSubDecor) != null) {
                                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                    if (ViewCompat.Api19Impl.isLaidOut(viewGroup)) {
                                        z2 = true;
                                        if (z2) {
                                            AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                            AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                            return;
                                        }
                                        AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                                        AppCompatDelegateImpl appCompatDelegateImpl5 = AppCompatDelegateImpl.this;
                                        ViewPropertyAnimatorCompat animate = ViewCompat.animate(appCompatDelegateImpl5.mActionModeView);
                                        animate.alpha(1.0f);
                                        appCompatDelegateImpl5.mFadeAnim = animate;
                                        AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6.1
                                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                            public final void onAnimationEnd(View view) {
                                                RunnableC00316 runnableC00316 = RunnableC00316.this;
                                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                                AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                                AppCompatDelegateImpl.this.mFadeAnim = null;
                                            }

                                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                            public final void onAnimationStart(View view) {
                                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                            }
                                        });
                                        return;
                                    }
                                }
                                z2 = false;
                                if (z2) {
                                }
                            }
                        };
                    } else {
                        Context context2 = appCompatDelegateImpl2.mSubDecor.getContext();
                        View findViewById = appCompatDelegateImpl2.mSubDecor.findViewById(context2.getResources().getIdentifier("collapsing_toolbar", "id", context2.getPackageName()));
                        if (findViewById == null) {
                            findViewById = appCompatDelegateImpl2.mSubDecor.findViewById(context2.getResources().getIdentifier("sesl_toolbar_container", "id", context2.getPackageName()));
                        }
                        ViewStubCompat viewStubCompat = findViewById == null ? (ViewStubCompat) appCompatDelegateImpl2.mSubDecor.findViewById(com.android.systemui.R.id.action_mode_bar_stub) : appCompatDelegateImpl2.mOverlayActionMode ? (ViewStubCompat) appCompatDelegateImpl2.mSubDecor.findViewById(com.android.systemui.R.id.action_mode_bar_stub) : (ViewStubCompat) findViewById.findViewById(com.android.systemui.R.id.action_mode_bar_stub);
                        if (viewStubCompat != null) {
                            appCompatDelegateImpl2.initWindowDecorActionBar();
                            ActionBar actionBar2 = appCompatDelegateImpl2.mActionBar;
                            Context themedContext = actionBar2 != null ? actionBar2.getThemedContext() : null;
                            if (themedContext == null) {
                                themedContext = appCompatDelegateImpl2.mContext;
                            }
                            viewStubCompat.mInflater = LayoutInflater.from(themedContext);
                            appCompatDelegateImpl2.mActionModeView = (ActionBarContextView) viewStubCompat.inflate();
                        }
                    }
                }
                if (appCompatDelegateImpl2.mActionModeView != null) {
                    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = appCompatDelegateImpl2.mFadeAnim;
                    if (viewPropertyAnimatorCompat2 != null) {
                        viewPropertyAnimatorCompat2.cancel();
                    }
                    appCompatDelegateImpl2.mActionModeView.killMode();
                    StandaloneActionMode standaloneActionMode = new StandaloneActionMode(appCompatDelegateImpl2.mActionModeView.getContext(), appCompatDelegateImpl2.mActionModeView, actionModeCallbackWrapperV9, appCompatDelegateImpl2.mActionModePopup == null);
                    if (actionModeCallbackWrapperV9.onCreateActionMode(standaloneActionMode, standaloneActionMode.mMenu)) {
                        standaloneActionMode.invalidate();
                        appCompatDelegateImpl2.mActionModeView.initForMode(standaloneActionMode);
                        appCompatDelegateImpl2.mActionMode = standaloneActionMode;
                        if (appCompatDelegateImpl2.mSubDecorInstalled && (r9 = appCompatDelegateImpl2.mSubDecor) != null) {
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        }
                        z = false;
                        if (z) {
                            appCompatDelegateImpl2.mActionModeView.setAlpha(0.0f);
                            ViewPropertyAnimatorCompat animate = ViewCompat.animate(appCompatDelegateImpl2.mActionModeView);
                            animate.alpha(1.0f);
                            appCompatDelegateImpl2.mFadeAnim = animate;
                            animate.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.7
                                @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                public final void onAnimationEnd(View view) {
                                    AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                                    appCompatDelegateImpl3.mActionModeView.setAlpha(1.0f);
                                    appCompatDelegateImpl3.mFadeAnim.setListener(null);
                                    appCompatDelegateImpl3.mFadeAnim = null;
                                }

                                @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                public final void onAnimationStart(View view) {
                                    AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                                    appCompatDelegateImpl3.mActionModeView.setVisibility(0);
                                    if (appCompatDelegateImpl3.mActionModeView.getParent() instanceof View) {
                                        View view2 = (View) appCompatDelegateImpl3.mActionModeView.getParent();
                                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                        ViewCompat.Api20Impl.requestApplyInsets(view2);
                                    }
                                }
                            });
                        } else {
                            appCompatDelegateImpl2.mActionModeView.setAlpha(1.0f);
                            appCompatDelegateImpl2.mActionModeView.setVisibility(0);
                            if (appCompatDelegateImpl2.mActionModeView.getParent() instanceof View) {
                                View view = (View) appCompatDelegateImpl2.mActionModeView.getParent();
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                ViewCompat.Api20Impl.requestApplyInsets(view);
                            }
                        }
                        if (appCompatDelegateImpl2.mActionModePopup != null) {
                            appCompatDelegateImpl2.mWindow.getDecorView().post(appCompatDelegateImpl2.mShowActionModePopup);
                        }
                    } else {
                        appCompatDelegateImpl2.mActionMode = null;
                    }
                }
                if (appCompatDelegateImpl2.mActionMode != null && (appCompatCallback = appCompatDelegateImpl2.mAppCompatCallback) != null) {
                    appCompatCallback.onSupportActionModeStarted();
                }
                appCompatDelegateImpl2.updateBackInvokedCallbackState();
                appCompatDelegateImpl2.mActionMode = appCompatDelegateImpl2.mActionMode;
            }
            appCompatDelegateImpl2.updateBackInvokedCallbackState();
            androidx.appcompat.view.ActionMode actionMode3 = appCompatDelegateImpl2.mActionMode;
            if (actionMode3 != null) {
                return callbackWrapper.getActionModeWrapper(actionMode3);
            }
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AutoBatteryNightModeManager extends AutoNightModeManager {
        public final PowerManager mPowerManager;

        public AutoBatteryNightModeManager(Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final IntentFilter createIntentFilterForBroadcastReceiver() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.os.action.POWER_SAVE_MODE_CHANGED");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final int getApplyableNightMode() {
            return this.mPowerManager.isPowerSaveMode() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final void onChange() {
            AppCompatDelegateImpl.this.applyApplicationSpecificConfig(true, true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class AutoNightModeManager {
        public C00341 mReceiver;

        public AutoNightModeManager() {
        }

        public final void cleanup() {
            C00341 c00341 = this.mReceiver;
            if (c00341 != null) {
                try {
                    AppCompatDelegateImpl.this.mContext.unregisterReceiver(c00341);
                } catch (IllegalArgumentException unused) {
                }
                this.mReceiver = null;
            }
        }

        public abstract IntentFilter createIntentFilterForBroadcastReceiver();

        public abstract int getApplyableNightMode();

        public abstract void onChange();

        /* JADX WARN: Type inference failed for: r1v4, types: [androidx.appcompat.app.AppCompatDelegateImpl$AutoNightModeManager$1] */
        public final void setup() {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AutoTimeNightModeManager extends AutoNightModeManager {
        public final TwilightManager mTwilightManager;

        public AutoTimeNightModeManager(TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final int getApplyableNightMode() {
            Location location;
            boolean z;
            long j;
            Location lastKnownLocation;
            TwilightManager twilightManager = this.mTwilightManager;
            TwilightManager.TwilightState twilightState = twilightManager.mTwilightState;
            if (twilightState != null && twilightState.nextUpdate > System.currentTimeMillis()) {
                z = twilightState.isNight;
            } else {
                Context context = twilightManager.mContext;
                int checkSelfPermission = PermissionChecker.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
                Location location2 = null;
                LocationManager locationManager = twilightManager.mLocationManager;
                if (checkSelfPermission == 0) {
                    if (locationManager != null) {
                        try {
                        } catch (Exception e) {
                            Log.d("TwilightManager", "Failed to get last known location", e);
                        }
                        if (locationManager.isProviderEnabled("network")) {
                            lastKnownLocation = locationManager.getLastKnownLocation("network");
                            location = lastKnownLocation;
                        }
                    }
                    lastKnownLocation = null;
                    location = lastKnownLocation;
                } else {
                    location = null;
                }
                if (PermissionChecker.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 && locationManager != null) {
                    try {
                        if (locationManager.isProviderEnabled("gps")) {
                            location2 = locationManager.getLastKnownLocation("gps");
                        }
                    } catch (Exception e2) {
                        Log.d("TwilightManager", "Failed to get last known location", e2);
                    }
                }
                if (location2 == null || location == null ? location2 != null : location2.getTime() > location.getTime()) {
                    location = location2;
                }
                if (location != null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (TwilightCalculator.sInstance == null) {
                        TwilightCalculator.sInstance = new TwilightCalculator();
                    }
                    TwilightCalculator twilightCalculator = TwilightCalculator.sInstance;
                    twilightCalculator.calculateTwilight(location.getLatitude(), location.getLongitude(), currentTimeMillis - 86400000);
                    twilightCalculator.calculateTwilight(location.getLatitude(), location.getLongitude(), currentTimeMillis);
                    r3 = twilightCalculator.state == 1;
                    long j2 = twilightCalculator.sunrise;
                    long j3 = twilightCalculator.sunset;
                    twilightCalculator.calculateTwilight(location.getLatitude(), location.getLongitude(), 86400000 + currentTimeMillis);
                    long j4 = twilightCalculator.sunrise;
                    if (j2 == -1 || j3 == -1) {
                        j = currentTimeMillis + 43200000;
                    } else {
                        j = (currentTimeMillis > j3 ? j4 + 0 : currentTimeMillis > j2 ? j3 + 0 : j2 + 0) + 60000;
                    }
                    twilightState.isNight = r3;
                    twilightState.nextUpdate = j;
                } else {
                    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
                    int i = Calendar.getInstance().get(11);
                    if (i < 6 || i >= 22) {
                        r3 = true;
                    }
                }
                z = r3;
            }
            return z ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final void onChange() {
            AppCompatDelegateImpl.this.applyApplicationSpecificConfig(true, true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
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
            setBackgroundDrawable(AppCompatResources.getDrawable(i, getContext()));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PanelFeatureState {
        public int background;
        public View createdPanelView;
        public ListMenuDecorView decorView;
        public final int featureId;
        public Bundle frozenActionViewState;
        public int gravity;
        public boolean isHandled;
        public boolean isOpen;
        public boolean isPrepared;
        public ListMenuPresenter listMenuPresenter;
        public ContextThemeWrapper listPresenterContext;
        public MenuBuilder menu;
        public boolean refreshDecorView = false;
        public boolean refreshMenuContent;
        public View shownPanelView;
        public int windowAnimations;

        public PanelFeatureState(int i) {
            this.featureId = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        public PanelMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            PanelFeatureState panelFeatureState;
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            int i = 0;
            boolean z2 = rootMenu != menuBuilder;
            if (z2) {
                menuBuilder = rootMenu;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            PanelFeatureState[] panelFeatureStateArr = appCompatDelegateImpl.mPanels;
            int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
            while (true) {
                if (i < length) {
                    panelFeatureState = panelFeatureStateArr[i];
                    if (panelFeatureState != null && panelFeatureState.menu == menuBuilder) {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState != null) {
                if (!z2) {
                    appCompatDelegateImpl.closePanel(panelFeatureState, z);
                } else {
                    appCompatDelegateImpl.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, rootMenu);
                    appCompatDelegateImpl.closePanel(panelFeatureState, true);
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

    public AppCompatDelegateImpl(Activity activity, AppCompatCallback appCompatCallback) {
        this(activity, null, appCompatCallback, activity);
    }

    public static Configuration createOverrideAppConfiguration(Context context, int i, Configuration configuration, boolean z) {
        int i2 = i != 1 ? i != 2 ? z ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 48 : 32 : 16;
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i2 | (configuration2.uiMode & (-49));
        return configuration2;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(R.id.content)).addView(view, layoutParams);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x012a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean applyApplicationSpecificConfig(boolean z, boolean z2) {
        int i;
        Configuration configuration;
        int i2;
        int i3;
        LocaleListCompat forLanguageTags;
        int i4;
        boolean z3;
        boolean z4;
        Object obj;
        if (this.mDestroyed) {
            return false;
        }
        int i5 = this.mLocalNightMode;
        if (i5 == -100) {
            i5 = AppCompatDelegate.sDefaultNightMode;
        }
        Configuration createOverrideAppConfiguration = createOverrideAppConfiguration(this.mContext, mapNightMode(i5, this.mContext), null, false);
        Context context = this.mContext;
        boolean z5 = true;
        if (!this.mActivityHandlesConfigFlagsChecked && (this.mHost instanceof Activity)) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                i = 0;
                configuration = this.mEffectiveConfiguration;
                if (configuration == null) {
                    configuration = this.mContext.getResources().getConfiguration();
                }
                i2 = configuration.uiMode & 48;
                i3 = createOverrideAppConfiguration.uiMode & 48;
                forLanguageTags = LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
                i4 = i2 == i3 ? 512 : 0;
                if (((~i) & i4) != 0 && z && this.mBaseContextAttached && ((z4 = sCanReturnDifferentContext) || this.mCreated)) {
                    obj = this.mHost;
                    if ((obj instanceof Activity) && !((Activity) obj).isChild()) {
                        Object[] objArr = new Object[12];
                        objArr[0] = Integer.valueOf(i2);
                        objArr[1] = Integer.valueOf(i3);
                        objArr[2] = forLanguageTags;
                        objArr[3] = null;
                        objArr[4] = Boolean.valueOf((i & 512) == 0);
                        objArr[5] = Boolean.valueOf((i & 4) == 0);
                        objArr[6] = Boolean.valueOf((i & 8192) == 0);
                        objArr[7] = Boolean.valueOf(this.mBaseContextAttached);
                        objArr[8] = Boolean.valueOf(this.mCreated);
                        objArr[9] = Boolean.valueOf(z4);
                        objArr[10] = this.mHost;
                        objArr[11] = this.mContext.getApplicationContext().getResources().getConfiguration();
                        Log.d("AppCompatDelegate", String.format("updateAppConfiguration attempting to recreate Activity [currentNightMode:%s, newNightMode:%s, currentLocales:%s, newLocales:%s, activityHandlingNightModeChanges:%s, activityHandlingLocalesChanges:%s, activityHandlingLayoutDirectionChanges:%s, baseContextAttached:%s, created:%s, canReturnDifferentContext:%s, host:%s], application configuration [%s]", objArr));
                        Activity activity = (Activity) this.mHost;
                        int i6 = ActivityCompat.$r8$clinit;
                        activity.recreate();
                        z3 = true;
                        if (!z3 || i4 == 0) {
                            z5 = z3;
                        } else {
                            boolean z6 = (i4 & i) == i4;
                            Resources resources = this.mContext.getResources();
                            Configuration configuration2 = new Configuration(resources.getConfiguration());
                            configuration2.uiMode = i3 | (resources.getConfiguration().uiMode & (-49));
                            resources.updateConfiguration(configuration2, null);
                            int i7 = this.mThemeResId;
                            if (i7 != 0) {
                                this.mContext.setTheme(i7);
                                this.mContext.getTheme().applyStyle(this.mThemeResId, true);
                            }
                            if (z6) {
                                Object obj2 = this.mHost;
                                if (obj2 instanceof Activity) {
                                    Activity activity2 = (Activity) obj2;
                                    if (activity2 instanceof LifecycleOwner) {
                                        if (((LifecycleOwner) activity2).getLifecycle().mState.isAtLeast(Lifecycle.State.CREATED)) {
                                            activity2.onConfigurationChanged(configuration2);
                                        }
                                    } else if (this.mCreated && !this.mDestroyed) {
                                        activity2.onConfigurationChanged(configuration2);
                                    }
                                }
                            }
                        }
                        if (z5) {
                            Object obj3 = this.mHost;
                            if (obj3 instanceof AppCompatActivity) {
                                if ((i4 & 512) != 0) {
                                    ((AppCompatActivity) obj3).getClass();
                                }
                                if ((i4 & 4) != 0) {
                                    ((AppCompatActivity) this.mHost).getClass();
                                }
                            }
                        }
                        if (i5 != 0) {
                            getAutoTimeNightModeManager(this.mContext).setup();
                        } else {
                            AutoTimeNightModeManager autoTimeNightModeManager = this.mAutoTimeNightModeManager;
                            if (autoTimeNightModeManager != null) {
                                autoTimeNightModeManager.cleanup();
                            }
                        }
                        if (i5 != 3) {
                            Context context2 = this.mContext;
                            if (this.mAutoBatteryNightModeManager == null) {
                                this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context2);
                            }
                            this.mAutoBatteryNightModeManager.setup();
                        } else {
                            AutoBatteryNightModeManager autoBatteryNightModeManager = this.mAutoBatteryNightModeManager;
                            if (autoBatteryNightModeManager != null) {
                                autoBatteryNightModeManager.cleanup();
                            }
                        }
                        return z5;
                    }
                }
                z3 = false;
                if (z3) {
                }
                z5 = z3;
                if (z5) {
                }
                if (i5 != 0) {
                }
                if (i5 != 3) {
                }
                return z5;
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
        i2 = configuration.uiMode & 48;
        i3 = createOverrideAppConfiguration.uiMode & 48;
        forLanguageTags = LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
        if (i2 == i3) {
        }
        if (((~i) & i4) != 0) {
            obj = this.mHost;
            if (obj instanceof Activity) {
                Object[] objArr2 = new Object[12];
                objArr2[0] = Integer.valueOf(i2);
                objArr2[1] = Integer.valueOf(i3);
                objArr2[2] = forLanguageTags;
                objArr2[3] = null;
                objArr2[4] = Boolean.valueOf((i & 512) == 0);
                objArr2[5] = Boolean.valueOf((i & 4) == 0);
                objArr2[6] = Boolean.valueOf((i & 8192) == 0);
                objArr2[7] = Boolean.valueOf(this.mBaseContextAttached);
                objArr2[8] = Boolean.valueOf(this.mCreated);
                objArr2[9] = Boolean.valueOf(z4);
                objArr2[10] = this.mHost;
                objArr2[11] = this.mContext.getApplicationContext().getResources().getConfiguration();
                Log.d("AppCompatDelegate", String.format("updateAppConfiguration attempting to recreate Activity [currentNightMode:%s, newNightMode:%s, currentLocales:%s, newLocales:%s, activityHandlingNightModeChanges:%s, activityHandlingLocalesChanges:%s, activityHandlingLayoutDirectionChanges:%s, baseContextAttached:%s, created:%s, canReturnDifferentContext:%s, host:%s], application configuration [%s]", objArr2));
                Activity activity3 = (Activity) this.mHost;
                int i62 = ActivityCompat.$r8$clinit;
                activity3.recreate();
                z3 = true;
                if (z3) {
                }
                z5 = z3;
                if (z5) {
                }
                if (i5 != 0) {
                }
                if (i5 != 3) {
                }
                return z5;
            }
        }
        z3 = false;
        if (z3) {
        }
        z5 = z3;
        if (z5) {
        }
        if (i5 != 0) {
        }
        if (i5 != 3) {
        }
        return z5;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final Context attachBaseContext2(Context context) {
        this.mBaseContextAttached = true;
        int i = this.mLocalNightMode;
        if (i == -100) {
            i = AppCompatDelegate.sDefaultNightMode;
        }
        int mapNightMode = mapNightMode(i, context);
        boolean z = false;
        if (AppCompatDelegate.isAutoStorageOptedIn(context) && AppCompatDelegate.isAutoStorageOptedIn(context) && !AppCompatDelegate.sIsFrameworkSyncChecked) {
            AppCompatDelegate.sSerialExecutorForLocalesStorage.execute(new AppCompatDelegate$$ExternalSyntheticLambda0(context, 0));
        }
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
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 2132018381);
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

    public final void attachToWindow(Window window) {
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

    public final void callOnPanelClosed(int i, PanelFeatureState panelFeatureState, MenuBuilder menuBuilder) {
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
            AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
            Window.Callback callback = this.mWindow.getCallback();
            appCompatWindowCallback.getClass();
            try {
                appCompatWindowCallback.mOnPanelClosedBypassEnabled = true;
                callback.onPanelClosed(i, menuBuilder);
            } finally {
                appCompatWindowCallback.mOnPanelClosedBypassEnabled = false;
            }
        }
    }

    public final void checkCloseActionMenu(MenuBuilder menuBuilder) {
        ActionMenuPresenter actionMenuPresenter;
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.mDecorContentParent;
        actionBarOverlayLayout.pullChildren();
        ActionMenuView actionMenuView = ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mToolbar.mMenuView;
        if (actionMenuView != null && (actionMenuPresenter = actionMenuView.mPresenter) != null) {
            actionMenuPresenter.hideOverflowMenu();
            ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
            if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
                actionButtonSubmenu.mPopup.dismiss();
            }
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !this.mDestroyed) {
            windowCallback.onPanelClosed(108, menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        ListMenuDecorView listMenuDecorView;
        DecorContentParent decorContentParent;
        boolean z2;
        if (z && panelFeatureState.featureId == 0 && (decorContentParent = this.mDecorContentParent) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
            actionBarOverlayLayout.pullChildren();
            ActionMenuView actionMenuView = ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mToolbar.mMenuView;
            if (actionMenuView != null) {
                ActionMenuPresenter actionMenuPresenter = actionMenuView.mPresenter;
                if (actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing()) {
                    z2 = true;
                    if (z2) {
                        checkCloseActionMenu(panelFeatureState.menu);
                        return;
                    }
                }
            }
            z2 = false;
            if (z2) {
            }
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager != null && panelFeatureState.isOpen && (listMenuDecorView = panelFeatureState.decorView) != null) {
            if (listMenuDecorView.isAttachedToWindow()) {
                windowManager.removeView(panelFeatureState.decorView);
            }
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

    /* JADX WARN: Code restructure failed: missing block: B:76:0x00fa, code lost:
    
        if ((r7 != null && r7.showOverflowMenu()) != false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x011b, code lost:
    
        if ((r7 != null && r7.hideOverflowMenu()) != false) goto L106;
     */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        ActionMenuView actionMenuView;
        Object obj = this.mHost;
        if (((obj instanceof KeyEventDispatcher$Component) || (obj instanceof AppCompatDialog)) && this.mWindow.getDecorView() != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        }
        if (keyEvent.getKeyCode() == 82) {
            AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
            Window.Callback callback = this.mWindow.getCallback();
            appCompatWindowCallback.getClass();
            try {
                appCompatWindowCallback.mDispatchKeyEventBypassEnabled = true;
                if (callback.dispatchKeyEvent(keyEvent)) {
                    return true;
                }
            } finally {
                appCompatWindowCallback.mDispatchKeyEventBypassEnabled = false;
            }
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
                if (decorContentParent != null) {
                    ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
                    actionBarOverlayLayout.pullChildren();
                    Toolbar toolbar = ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mToolbar;
                    if ((toolbar.getVisibility() == 0 && (actionMenuView = toolbar.mMenuView) != null && actionMenuView.mReserveOverflow) && !ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
                        ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.mDecorContentParent;
                        actionBarOverlayLayout2.pullChildren();
                        ActionMenuView actionMenuView2 = ((ToolbarWidgetWrapper) actionBarOverlayLayout2.mDecorToolbar).mToolbar.mMenuView;
                        if (actionMenuView2 != null) {
                            ActionMenuPresenter actionMenuPresenter = actionMenuView2.mPresenter;
                            if (actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing()) {
                                z3 = true;
                                if (z3) {
                                    if (!this.mDestroyed && preparePanel(panelState2, keyEvent)) {
                                        ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.mDecorContentParent;
                                        actionBarOverlayLayout3.pullChildren();
                                        ActionMenuView actionMenuView3 = ((ToolbarWidgetWrapper) actionBarOverlayLayout3.mDecorToolbar).mToolbar.mMenuView;
                                        if (actionMenuView3 != null) {
                                            ActionMenuPresenter actionMenuPresenter2 = actionMenuView3.mPresenter;
                                        }
                                    }
                                    z = false;
                                } else {
                                    ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.mDecorContentParent;
                                    actionBarOverlayLayout4.pullChildren();
                                    ActionMenuView actionMenuView4 = ((ToolbarWidgetWrapper) actionBarOverlayLayout4.mDecorToolbar).mToolbar.mMenuView;
                                    if (actionMenuView4 != null) {
                                        ActionMenuPresenter actionMenuPresenter3 = actionMenuView4.mPresenter;
                                    }
                                    z = false;
                                }
                                if (!z) {
                                    return true;
                                }
                                AudioManager audioManager = (AudioManager) this.mContext.getApplicationContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                                if (audioManager != null) {
                                    audioManager.playSoundEffect(0);
                                    return true;
                                }
                                Log.w("AppCompatDelegate", "Couldn't get audio manager");
                                return true;
                            }
                        }
                        z3 = false;
                        if (z3) {
                        }
                        if (!z) {
                        }
                    }
                }
                boolean z4 = panelState2.isOpen;
                if (z4 || panelState2.isHandled) {
                    closePanel(panelState2, true);
                    z = z4;
                    if (!z) {
                    }
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
                            if (!z) {
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
            }
        } else if (onBackPressed()) {
            return true;
        }
        return false;
    }

    public final void doInvalidatePanelMenu(int i) {
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

    public final void ensureSubDecor() {
        ViewGroup viewGroup;
        if (this.mSubDecorInstalled) {
            return;
        }
        Context context = this.mContext;
        int[] iArr = R$styleable.AppCompatTheme;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!obtainStyledAttributes.hasValue(145)) {
            obtainStyledAttributes.recycle();
            Log.e("AppCompatDelegate", "createSubDecor: mContext = " + this.mContext);
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (obtainStyledAttributes.getBoolean(154, false)) {
            requestWindowFeature(1);
        } else if (obtainStyledAttributes.getBoolean(145, false)) {
            requestWindowFeature(108);
        }
        if (obtainStyledAttributes.getBoolean(146, false)) {
            requestWindowFeature(109);
        }
        if (obtainStyledAttributes.getBoolean(147, false)) {
            requestWindowFeature(10);
        }
        this.mIsFloating = obtainStyledAttributes.getBoolean(1, false);
        if (obtainStyledAttributes.hasValue(86)) {
            this.mIsIgnoreRemoveSystemTopInset = obtainStyledAttributes.getBoolean(86, false);
        }
        obtainStyledAttributes.recycle();
        ensureWindow();
        this.mWindow.getDecorView();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (this.mWindowNoTitle) {
            viewGroup = this.mOverlayActionMode ? (ViewGroup) from.inflate(com.android.systemui.R.layout.sesl_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) from.inflate(com.android.systemui.R.layout.sesl_screen_simple, (ViewGroup) null);
        } else if (this.mIsFloating) {
            viewGroup = (ViewGroup) from.inflate(com.android.systemui.R.layout.sesl_dialog_title, (ViewGroup) null);
            this.mOverlayActionBar = false;
            this.mHasActionBar = false;
        } else if (this.mHasActionBar) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(com.android.systemui.R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new ContextThemeWrapper(this.mContext, typedValue.resourceId) : this.mContext).inflate(com.android.systemui.R.layout.sesl_screen_toolbar, (ViewGroup) null);
            DecorContentParent decorContentParent = (DecorContentParent) viewGroup.findViewById(com.android.systemui.R.id.decor_content_parent);
            this.mDecorContentParent = decorContentParent;
            Window.Callback windowCallback = getWindowCallback();
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
            actionBarOverlayLayout.pullChildren();
            ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mWindowCallback = windowCallback;
            if (this.mOverlayActionBar) {
                ((ActionBarOverlayLayout) this.mDecorContentParent).initFeature(109);
            }
            if (this.mFeatureProgress) {
                ((ActionBarOverlayLayout) this.mDecorContentParent).initFeature(2);
            }
            if (this.mFeatureIndeterminateProgress) {
                ((ActionBarOverlayLayout) this.mDecorContentParent).initFeature(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            StringBuilder sb = new StringBuilder("AppCompat does not support the current theme features: { windowActionBar: ");
            sb.append(this.mHasActionBar);
            sb.append(", windowActionBarOverlay: ");
            sb.append(this.mOverlayActionBar);
            sb.append(", android:windowIsFloating: ");
            sb.append(this.mIsFloating);
            sb.append(", windowActionModeOverlay: ");
            sb.append(this.mOverlayActionMode);
            sb.append(", windowNoTitle: ");
            throw new IllegalArgumentException(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.mWindowNoTitle, " }"));
        }
        OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                int updateStatusGuard = AppCompatDelegateImpl.this.updateStatusGuard(windowInsetsCompat, null);
                if (systemWindowInsetTop != updateStatusGuard) {
                    int systemWindowInsetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                    int systemWindowInsetRight = windowInsetsCompat.getSystemWindowInsetRight();
                    int systemWindowInsetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                    WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(windowInsetsCompat);
                    builder.mImpl.setSystemWindowInsets(Insets.m28of(systemWindowInsetLeft, updateStatusGuard, systemWindowInsetRight, systemWindowInsetBottom));
                    windowInsetsCompat = builder.build();
                }
                return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
            }
        };
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(viewGroup, onApplyWindowInsetsListener);
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(com.android.systemui.R.id.title);
        }
        Method method = ViewUtils.sComputeFitSystemWindowsMethod;
        try {
            Method method2 = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
            if (!method2.isAccessible()) {
                method2.setAccessible(true);
            }
            method2.invoke(viewGroup, new Object[0]);
        } catch (IllegalAccessException e) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e);
        } catch (NoSuchMethodException unused) {
            Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
        } catch (InvocationTargetException e2) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e2);
        }
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(com.android.systemui.R.id.action_bar_activity_content);
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
        contentFrameLayout.mAttachListener = new C00305();
        this.mSubDecor = viewGroup;
        Object obj = this.mHost;
        CharSequence title = obj instanceof Activity ? ((Activity) obj).getTitle() : this.mTitle;
        if (!TextUtils.isEmpty(title)) {
            DecorContentParent decorContentParent2 = this.mDecorContentParent;
            if (decorContentParent2 != null) {
                ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) decorContentParent2;
                actionBarOverlayLayout2.pullChildren();
                ToolbarWidgetWrapper toolbarWidgetWrapper = (ToolbarWidgetWrapper) actionBarOverlayLayout2.mDecorToolbar;
                if (!toolbarWidgetWrapper.mTitleSet) {
                    toolbarWidgetWrapper.mTitle = title;
                    if ((toolbarWidgetWrapper.mDisplayOpts & 8) != 0) {
                        Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
                        toolbar.setTitle(title);
                        if (toolbarWidgetWrapper.mTitleSet) {
                            ViewCompat.setAccessibilityPaneTitle(toolbar.getRootView(), title);
                        }
                    }
                }
            } else {
                ActionBar actionBar = this.mActionBar;
                if (actionBar != null) {
                    actionBar.setWindowTitle(title);
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
        contentFrameLayout2.mDecorPadding.set(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isLaidOut(contentFrameLayout2)) {
            contentFrameLayout2.requestLayout();
        }
        TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(iArr);
        if (contentFrameLayout2.mMinWidthMajor == null) {
            contentFrameLayout2.mMinWidthMajor = new TypedValue();
        }
        obtainStyledAttributes2.getValue(152, contentFrameLayout2.mMinWidthMajor);
        if (contentFrameLayout2.mMinWidthMinor == null) {
            contentFrameLayout2.mMinWidthMinor = new TypedValue();
        }
        obtainStyledAttributes2.getValue(153, contentFrameLayout2.mMinWidthMinor);
        if (obtainStyledAttributes2.hasValue(150)) {
            if (contentFrameLayout2.mFixedWidthMajor == null) {
                contentFrameLayout2.mFixedWidthMajor = new TypedValue();
            }
            obtainStyledAttributes2.getValue(150, contentFrameLayout2.mFixedWidthMajor);
        }
        if (obtainStyledAttributes2.hasValue(151)) {
            if (contentFrameLayout2.mFixedWidthMinor == null) {
                contentFrameLayout2.mFixedWidthMinor = new TypedValue();
            }
            obtainStyledAttributes2.getValue(151, contentFrameLayout2.mFixedWidthMinor);
        }
        if (obtainStyledAttributes2.hasValue(148)) {
            if (contentFrameLayout2.mFixedHeightMajor == null) {
                contentFrameLayout2.mFixedHeightMajor = new TypedValue();
            }
            obtainStyledAttributes2.getValue(148, contentFrameLayout2.mFixedHeightMajor);
        }
        if (obtainStyledAttributes2.hasValue(149)) {
            if (contentFrameLayout2.mFixedHeightMinor == null) {
                contentFrameLayout2.mFixedHeightMinor = new TypedValue();
            }
            obtainStyledAttributes2.getValue(149, contentFrameLayout2.mFixedHeightMinor);
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
        ViewCompat.Api16Impl.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
        this.mInvalidatePanelMenuPosted = true;
    }

    public final void ensureWindow() {
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

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final View findViewById(int i) {
        ensureSubDecor();
        return this.mWindow.findViewById(i);
    }

    public final AutoNightModeManager getAutoTimeNightModeManager(Context context) {
        if (this.mAutoTimeNightModeManager == null) {
            if (TwilightManager.sInstance == null) {
                Context applicationContext = context.getApplicationContext();
                TwilightManager.sInstance = new TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService(GlsIntent.Extras.EXTRA_LOCATION));
            }
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.sInstance);
        }
        return this.mAutoTimeNightModeManager;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final Context getContextForDelegate() {
        return this.mContext;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            this.mMenuInflater = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.mContext);
        }
        return this.mMenuInflater;
    }

    public final PanelFeatureState getPanelState(int i) {
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
    public final ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    public final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    public final void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) this.mHost, this.mOverlayActionBar);
            } else if (obj instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) this.mHost);
            }
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
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
            if (this.mActionBar.invalidateOptionsMenu()) {
                return;
            }
            this.mInvalidatePanelMenuFeatures |= 1;
            if (this.mInvalidatePanelMenuPosted) {
                return;
            }
            View decorView = this.mWindow.getDecorView();
            RunnableC00282 runnableC00282 = this.mInvalidatePanelMenuRunnable;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(decorView, runnableC00282);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    public final int mapNightMode(int i, Context context) {
        if (i == -100) {
            return -1;
        }
        if (i != -1) {
            if (i == 0) {
                if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                    return -1;
                }
                return getAutoTimeNightModeManager(context).getApplyableNightMode();
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

    public final boolean onBackPressed() {
        boolean z = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        PanelFeatureState panelState = getPanelState(0);
        if (panelState.isOpen) {
            if (!z) {
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
        ActionBar actionBar = this.mActionBar;
        return actionBar != null && actionBar.collapseActionView();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mHasActionBar && this.mSubDecorInstalled) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.onConfigurationChanged();
            }
        }
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        Context context = this.mContext;
        synchronized (appCompatDrawableManager) {
            ResourceManagerInternal resourceManagerInternal = appCompatDrawableManager.mResourceManager;
            synchronized (resourceManagerInternal) {
                LongSparseArray longSparseArray = (LongSparseArray) resourceManagerInternal.mDrawableCaches.get(context);
                if (longSparseArray != null) {
                    longSparseArray.clear();
                }
            }
        }
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
                ActionBar actionBar = this.mActionBar;
                if (actionBar == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    actionBar.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            synchronized (AppCompatDelegate.sActivityDelegatesLock) {
                AppCompatDelegate.removeDelegateFromActives(this);
                AppCompatDelegate.sActivityDelegates.add(new WeakReference(this));
            }
        }
        this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
        this.mCreated = true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00d8, code lost:
    
        if (r8.equals("ImageView") == false) goto L76;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.view.LayoutInflater.Factory2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View appCompatRatingBar;
        if (this.mAppCompatViewInflater == null) {
            String string = this.mContext.obtainStyledAttributes(R$styleable.AppCompatTheme).getString(144);
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
        appCompatViewInflater.getClass();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.View, 0, 0);
        char c = '\b';
        int resourceId = obtainStyledAttributes.getResourceId(8, 0);
        if (resourceId != 0) {
            Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        obtainStyledAttributes.recycle();
        Context contextThemeWrapper = (resourceId == 0 || ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).mThemeResource == resourceId)) ? context : new ContextThemeWrapper(context, resourceId);
        str.getClass();
        switch (str.hashCode()) {
            case -1946472170:
                if (str.equals("RatingBar")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1455429095:
                if (str.equals("CheckedTextView")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1346021293:
                if (str.equals("MultiAutoCompleteTextView")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -938935918:
                if (str.equals("TextView")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -937446323:
                if (str.equals("ImageButton")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -339785223:
                if (str.equals("Spinner")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 776382189:
                if (str.equals("RadioButton")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 799298502:
                if (str.equals("ToggleButton")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1125864064:
                break;
            case 1413872058:
                if (str.equals("AutoCompleteTextView")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1601505219:
                if (str.equals("CheckBox")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1666676343:
                if (str.equals("EditText")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 2001146706:
                if (str.equals("Button")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        View view2 = null;
        switch (c) {
            case 0:
                appCompatRatingBar = new AppCompatRatingBar(contextThemeWrapper, attributeSet);
                break;
            case 1:
                appCompatRatingBar = new AppCompatCheckedTextView(contextThemeWrapper, attributeSet);
                break;
            case 2:
                appCompatRatingBar = new AppCompatMultiAutoCompleteTextView(contextThemeWrapper, attributeSet);
                break;
            case 3:
                appCompatRatingBar = appCompatViewInflater.createTextView(contextThemeWrapper, attributeSet);
                appCompatViewInflater.verifyNotNull(appCompatRatingBar, str);
                break;
            case 4:
                appCompatRatingBar = new AppCompatImageButton(contextThemeWrapper, attributeSet);
                break;
            case 5:
                appCompatRatingBar = new AppCompatSpinner(contextThemeWrapper, attributeSet);
                break;
            case 6:
                appCompatRatingBar = appCompatViewInflater.createRadioButton(contextThemeWrapper, attributeSet);
                appCompatViewInflater.verifyNotNull(appCompatRatingBar, str);
                break;
            case 7:
                appCompatRatingBar = new AppCompatToggleButton(contextThemeWrapper, attributeSet);
                break;
            case '\b':
                appCompatRatingBar = new AppCompatImageView(contextThemeWrapper, attributeSet);
                break;
            case '\t':
                appCompatRatingBar = appCompatViewInflater.createAutoCompleteTextView(contextThemeWrapper, attributeSet);
                appCompatViewInflater.verifyNotNull(appCompatRatingBar, str);
                break;
            case '\n':
                appCompatRatingBar = appCompatViewInflater.createCheckBox(contextThemeWrapper, attributeSet);
                appCompatViewInflater.verifyNotNull(appCompatRatingBar, str);
                break;
            case 11:
                appCompatRatingBar = new AppCompatEditText(contextThemeWrapper, attributeSet);
                break;
            case '\f':
                appCompatRatingBar = appCompatViewInflater.createButton(contextThemeWrapper, attributeSet);
                appCompatViewInflater.verifyNotNull(appCompatRatingBar, str);
                break;
            default:
                appCompatRatingBar = null;
                break;
        }
        if (appCompatRatingBar == null && context != contextThemeWrapper) {
            Object[] objArr = appCompatViewInflater.mConstructorArgs;
            if (str.equals("view")) {
                str = attributeSet.getAttributeValue(null, "class");
            }
            try {
                objArr[0] = contextThemeWrapper;
                objArr[1] = attributeSet;
                if (-1 == str.indexOf(46)) {
                    int i2 = 0;
                    while (true) {
                        String[] strArr = AppCompatViewInflater.sClassPrefixList;
                        if (i2 < 3) {
                            View createViewByPrefix = appCompatViewInflater.createViewByPrefix(contextThemeWrapper, str, strArr[i2]);
                            if (createViewByPrefix != null) {
                                objArr[0] = null;
                                objArr[1] = null;
                                view2 = createViewByPrefix;
                            } else {
                                i2++;
                            }
                        }
                    }
                } else {
                    View createViewByPrefix2 = appCompatViewInflater.createViewByPrefix(contextThemeWrapper, str, null);
                    objArr[0] = null;
                    objArr[1] = null;
                    view2 = createViewByPrefix2;
                }
            } catch (Exception unused) {
            } finally {
                objArr[0] = null;
                objArr[1] = null;
            }
            appCompatRatingBar = view2;
        }
        if (appCompatRatingBar != null) {
            Context context2 = appCompatRatingBar.getContext();
            if (context2 instanceof ContextWrapper) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api15Impl.hasOnClickListeners(appCompatRatingBar)) {
                    TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet, AppCompatViewInflater.sOnClickAttrs);
                    String string2 = obtainStyledAttributes2.getString(0);
                    if (string2 != null) {
                        appCompatRatingBar.setOnClickListener(new AppCompatViewInflater.DeclaredOnClickListener(appCompatRatingBar, string2));
                    }
                    obtainStyledAttributes2.recycle();
                }
            }
        }
        return appCompatRatingBar;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    @Override // androidx.appcompat.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDestroy() {
        ActionBar actionBar;
        AutoTimeNightModeManager autoTimeNightModeManager;
        AutoBatteryNightModeManager autoBatteryNightModeManager;
        if (this.mHost instanceof Activity) {
            synchronized (AppCompatDelegate.sActivityDelegatesLock) {
                AppCompatDelegate.removeDelegateFromActives(this);
            }
        }
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        this.mDestroyed = true;
        if (this.mLocalNightMode != -100) {
            Object obj = this.mHost;
            if ((obj instanceof Activity) && ((Activity) obj).isChangingConfigurations()) {
                sLocalNightModes.put(this.mHost.getClass().getName(), Integer.valueOf(this.mLocalNightMode));
                actionBar = this.mActionBar;
                if (actionBar != null) {
                    actionBar.onDestroy();
                }
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
        actionBar = this.mActionBar;
        if (actionBar != null) {
        }
        autoTimeNightModeManager = this.mAutoTimeNightModeManager;
        if (autoTimeNightModeManager != null) {
        }
        autoBatteryNightModeManager = this.mAutoBatteryNightModeManager;
        if (autoBatteryNightModeManager == null) {
        }
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        int i;
        int i2;
        PanelFeatureState panelFeatureState;
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !this.mDestroyed) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            PanelFeatureState[] panelFeatureStateArr = this.mPanels;
            if (panelFeatureStateArr != null) {
                i = panelFeatureStateArr.length;
                i2 = 0;
            } else {
                i = 0;
                i2 = 0;
            }
            while (true) {
                if (i2 < i) {
                    panelFeatureState = panelFeatureStateArr[i2];
                    if (panelFeatureState != null && panelFeatureState.menu == rootMenu) {
                        break;
                    }
                    i2++;
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState != null) {
                return windowCallback.onMenuItemSelected(panelFeatureState.featureId, menuItem);
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003f, code lost:
    
        if (((androidx.appcompat.widget.ToolbarWidgetWrapper) r6.mDecorToolbar).isOverflowMenuShowPending() != false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0097  */
    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMenuModeChange(MenuBuilder menuBuilder) {
        boolean z;
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2;
        ActionMenuView actionMenuView;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
            actionBarOverlayLayout.pullChildren();
            Toolbar toolbar = ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mToolbar;
            if (toolbar.getVisibility() == 0 && (actionMenuView = toolbar.mMenuView) != null && actionMenuView.mReserveOverflow) {
                if (ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
                    ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.mDecorContentParent;
                    actionBarOverlayLayout2.pullChildren();
                }
                Window.Callback windowCallback = getWindowCallback();
                ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.mDecorContentParent;
                actionBarOverlayLayout3.pullChildren();
                ActionMenuView actionMenuView2 = ((ToolbarWidgetWrapper) actionBarOverlayLayout3.mDecorToolbar).mToolbar.mMenuView;
                if (actionMenuView2 != null) {
                    ActionMenuPresenter actionMenuPresenter3 = actionMenuView2.mPresenter;
                    if (actionMenuPresenter3 != null && actionMenuPresenter3.isOverflowMenuShowing()) {
                        z = true;
                        if (!z) {
                            ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.mDecorContentParent;
                            actionBarOverlayLayout4.pullChildren();
                            ActionMenuView actionMenuView3 = ((ToolbarWidgetWrapper) actionBarOverlayLayout4.mDecorToolbar).mToolbar.mMenuView;
                            if (actionMenuView3 != null && ((actionMenuPresenter2 = actionMenuView3.mPresenter) == null || !actionMenuPresenter2.hideOverflowMenu())) {
                            }
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
                            this.mInvalidatePanelMenuRunnable.run();
                        }
                        PanelFeatureState panelState = getPanelState(0);
                        MenuBuilder menuBuilder2 = panelState.menu;
                        if (menuBuilder2 == null || panelState.refreshMenuContent || !windowCallback.onPreparePanel(0, panelState.createdPanelView, menuBuilder2)) {
                            return;
                        }
                        windowCallback.onMenuOpened(108, panelState.menu);
                        ActionBarOverlayLayout actionBarOverlayLayout5 = (ActionBarOverlayLayout) this.mDecorContentParent;
                        actionBarOverlayLayout5.pullChildren();
                        ActionMenuView actionMenuView4 = ((ToolbarWidgetWrapper) actionBarOverlayLayout5.mDecorToolbar).mToolbar.mMenuView;
                        if (actionMenuView4 == null || (actionMenuPresenter = actionMenuView4.mPresenter) == null) {
                            return;
                        }
                        actionMenuPresenter.showOverflowMenu();
                        return;
                    }
                }
                z = false;
                if (!z) {
                }
            }
        }
        PanelFeatureState panelState2 = getPanelState(0);
        panelState2.refreshDecorView = true;
        closePanel(panelState2, false);
        openPanel(panelState2, null);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onPostResume() {
        initWindowDecorActionBar();
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onStart() {
        applyApplicationSpecificConfig(true, false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onStop() {
        initWindowDecorActionBar();
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null) {
                closePanel(panelFeatureState, true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x015e, code lost:
    
        if (r5 != null) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0182, code lost:
    
        if (r15.mAdapter.getCount() > 0) goto L98;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x018a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        int i;
        ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.isOpen || this.mDestroyed) {
            return;
        }
        int i2 = panelFeatureState.featureId;
        if (i2 == 0) {
            if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                return;
            }
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !windowCallback.onMenuOpened(i2, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return;
        }
        ListMenuDecorView listMenuDecorView = panelFeatureState.decorView;
        if (listMenuDecorView == null || panelFeatureState.refreshDecorView) {
            ExpandedMenuView expandedMenuView = null;
            if (listMenuDecorView == null) {
                initWindowDecorActionBar();
                ActionBar actionBar = this.mActionBar;
                Context themedContext = actionBar != null ? actionBar.getThemedContext() : null;
                if (themedContext == null) {
                    themedContext = this.mContext;
                }
                TypedValue typedValue = new TypedValue();
                Resources.Theme newTheme = themedContext.getResources().newTheme();
                newTheme.setTo(themedContext.getTheme());
                newTheme.resolveAttribute(com.android.systemui.R.attr.actionBarPopupTheme, typedValue, true);
                int i3 = typedValue.resourceId;
                if (i3 != 0) {
                    newTheme.applyStyle(i3, true);
                }
                newTheme.resolveAttribute(com.android.systemui.R.attr.panelMenuListTheme, typedValue, true);
                int i4 = typedValue.resourceId;
                if (i4 != 0) {
                    newTheme.applyStyle(i4, true);
                } else {
                    newTheme.applyStyle(2132018369, true);
                }
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(themedContext, 0);
                contextThemeWrapper.getTheme().setTo(newTheme);
                panelFeatureState.listPresenterContext = contextThemeWrapper;
                TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R$styleable.AppCompatTheme);
                panelFeatureState.background = obtainStyledAttributes.getResourceId(102, 0);
                panelFeatureState.windowAnimations = obtainStyledAttributes.getResourceId(8, 0);
                obtainStyledAttributes.recycle();
                panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
                panelFeatureState.gravity = 81;
            } else if (panelFeatureState.refreshDecorView && listMenuDecorView.getChildCount() > 0) {
                panelFeatureState.decorView.removeAllViews();
            }
            View view = panelFeatureState.createdPanelView;
            if (view == null) {
                if (panelFeatureState.menu != null) {
                    if (this.mPanelMenuPresenterCallback == null) {
                        this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
                    }
                    PanelMenuPresenterCallback panelMenuPresenterCallback = this.mPanelMenuPresenterCallback;
                    if (panelFeatureState.menu != null) {
                        if (panelFeatureState.listMenuPresenter == null) {
                            ListMenuPresenter listMenuPresenter = new ListMenuPresenter(panelFeatureState.listPresenterContext, com.android.systemui.R.layout.sesl_list_menu_item_layout);
                            panelFeatureState.listMenuPresenter = listMenuPresenter;
                            listMenuPresenter.mCallback = panelMenuPresenterCallback;
                            MenuBuilder menuBuilder = panelFeatureState.menu;
                            menuBuilder.addMenuPresenter(listMenuPresenter, menuBuilder.mContext);
                        }
                        ListMenuPresenter listMenuPresenter2 = panelFeatureState.listMenuPresenter;
                        ListMenuDecorView listMenuDecorView2 = panelFeatureState.decorView;
                        if (listMenuPresenter2.mMenuView == null) {
                            listMenuPresenter2.mMenuView = (ExpandedMenuView) listMenuPresenter2.mInflater.inflate(com.android.systemui.R.layout.abc_expanded_menu_layout, (ViewGroup) listMenuDecorView2, false);
                            if (listMenuPresenter2.mAdapter == null) {
                                listMenuPresenter2.mAdapter = listMenuPresenter2.new MenuAdapter();
                            }
                            listMenuPresenter2.mMenuView.setAdapter((ListAdapter) listMenuPresenter2.mAdapter);
                            listMenuPresenter2.mMenuView.setOnItemClickListener(listMenuPresenter2);
                        }
                        expandedMenuView = listMenuPresenter2.mMenuView;
                    }
                    panelFeatureState.shownPanelView = expandedMenuView;
                }
                z = false;
                if (z) {
                    if (panelFeatureState.shownPanelView != null) {
                        if (panelFeatureState.createdPanelView == null) {
                            ListMenuPresenter listMenuPresenter3 = panelFeatureState.listMenuPresenter;
                            if (listMenuPresenter3.mAdapter == null) {
                                listMenuPresenter3.mAdapter = listMenuPresenter3.new MenuAdapter();
                            }
                        }
                        z2 = true;
                        if (z2) {
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
                    z2 = false;
                    if (z2) {
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
        View view2 = panelFeatureState.createdPanelView;
        if (view2 != null && (layoutParams = view2.getLayoutParams()) != null && layoutParams.width == -1) {
            i = -1;
            panelFeatureState.isHandled = false;
            WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
            layoutParams3.gravity = panelFeatureState.gravity;
            layoutParams3.windowAnimations = panelFeatureState.windowAnimations;
            windowManager.addView(panelFeatureState.decorView, layoutParams3);
            panelFeatureState.isOpen = true;
            if (i2 != 0) {
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
        if (i2 != 0) {
        }
    }

    public final boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent) {
        MenuBuilder menuBuilder;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && (menuBuilder = panelFeatureState.menu) != null) {
            return menuBuilder.performShortcut(i, keyEvent, 1);
        }
        return false;
    }

    public final boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        Resources.Theme theme;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
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
        int i = panelFeatureState.featureId;
        if (windowCallback != null) {
            panelFeatureState.createdPanelView = windowCallback.onCreatePanelView(i);
        }
        boolean z = i == 0 || i == 108;
        if (z && (decorContentParent3 = this.mDecorContentParent) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent3;
            actionBarOverlayLayout.pullChildren();
            ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mMenuPrepared = true;
        }
        if (panelFeatureState.createdPanelView == null && (!z || !(this.mActionBar instanceof ToolbarActionBar))) {
            MenuBuilder menuBuilder = panelFeatureState.menu;
            if (menuBuilder == null || panelFeatureState.refreshMenuContent) {
                if (menuBuilder == null) {
                    Context context = this.mContext;
                    if ((i == 0 || i == 108) && this.mDecorContentParent != null) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme2 = context.getTheme();
                        theme2.resolveAttribute(com.android.systemui.R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            theme = context.getResources().newTheme();
                            theme.setTo(theme2);
                            theme.applyStyle(typedValue.resourceId, true);
                            theme.resolveAttribute(com.android.systemui.R.attr.actionBarWidgetTheme, typedValue, true);
                        } else {
                            theme2.resolveAttribute(com.android.systemui.R.attr.actionBarWidgetTheme, typedValue, true);
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
                    menuBuilder2.mCallback = this;
                    MenuBuilder menuBuilder3 = panelFeatureState.menu;
                    if (menuBuilder2 != menuBuilder3) {
                        if (menuBuilder3 != null) {
                            menuBuilder3.removeMenuPresenter(panelFeatureState.listMenuPresenter);
                        }
                        panelFeatureState.menu = menuBuilder2;
                        ListMenuPresenter listMenuPresenter = panelFeatureState.listMenuPresenter;
                        if (listMenuPresenter != null) {
                            menuBuilder2.addMenuPresenter(listMenuPresenter, menuBuilder2.mContext);
                        }
                    }
                    if (panelFeatureState.menu == null) {
                        return false;
                    }
                }
                if (z && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    ((ActionBarOverlayLayout) this.mDecorContentParent).setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (!windowCallback.onCreatePanelMenu(i, panelFeatureState.menu)) {
                    MenuBuilder menuBuilder4 = panelFeatureState.menu;
                    if (menuBuilder4 != null) {
                        if (menuBuilder4 != null) {
                            menuBuilder4.removeMenuPresenter(panelFeatureState.listMenuPresenter);
                        }
                        panelFeatureState.menu = null;
                    }
                    if (z && (decorContentParent = this.mDecorContentParent) != null) {
                        ((ActionBarOverlayLayout) decorContentParent).setMenu(null, this.mActionMenuPresenterCallback);
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
                if (z && (decorContentParent2 = this.mDecorContentParent) != null) {
                    ((ActionBarOverlayLayout) decorContentParent2).setMenu(null, this.mActionMenuPresenterCallback);
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
    public final void setLocalNightMode(int i) {
        if (this.mLocalNightMode != i) {
            this.mLocalNightMode = i;
            if (this.mBaseContextAttached) {
                applyApplicationSpecificConfig(true, true);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setSupportActionBar(Toolbar toolbar) {
        if (this.mHost instanceof Activity) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            if (actionBar instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.mMenuInflater = null;
            if (actionBar != null) {
                actionBar.onDestroy();
            }
            this.mActionBar = null;
            if (toolbar != null) {
                Object obj = this.mHost;
                ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, obj instanceof Activity ? ((Activity) obj).getTitle() : this.mTitle, this.mAppCompatWindowCallback);
                this.mActionBar = toolbarActionBar;
                this.mAppCompatWindowCallback.mActionBarCallback = toolbarActionBar.mMenuCallback;
                toolbar.setBackInvokedCallbackEnabled();
                Window window = this.mWindow;
                if (window != null) {
                    window.setCallback(this.mAppCompatWindowCallback);
                }
            } else {
                this.mAppCompatWindowCallback.mActionBarCallback = null;
            }
            invalidateOptionsMenu();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTheme(int i) {
        this.mThemeResId = i;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null) {
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.setWindowTitle(charSequence);
                return;
            }
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.setText(charSequence);
                return;
            }
            return;
        }
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
        actionBarOverlayLayout.pullChildren();
        ToolbarWidgetWrapper toolbarWidgetWrapper = (ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar;
        if (toolbarWidgetWrapper.mTitleSet) {
            return;
        }
        toolbarWidgetWrapper.mTitle = charSequence;
        if ((toolbarWidgetWrapper.mDisplayOpts & 8) != 0) {
            Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
            toolbar.setTitle(charSequence);
            if (toolbarWidgetWrapper.mTitleSet) {
                ViewCompat.setAccessibilityPaneTitle(toolbar.getRootView(), charSequence);
            }
        }
    }

    public final void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.window.OnBackInvokedCallback, androidx.appcompat.app.AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0] */
    public final void updateBackInvokedCallbackState() {
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
    }

    public final int updateStatusGuard(WindowInsetsCompat windowInsetsCompat, Rect rect) {
        boolean z;
        boolean z2;
        int color;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : rect != null ? rect.top : 0;
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
                Rect rect2 = this.mTempRect1;
                Rect rect3 = this.mTempRect2;
                if (windowInsetsCompat == null) {
                    rect2.set(rect);
                } else {
                    rect2.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                ViewGroup viewGroup = this.mSubDecor;
                Method method = ViewUtils.sComputeFitSystemWindowsMethod;
                if (method != null) {
                    try {
                        method.invoke(viewGroup, rect2, rect3);
                    } catch (Exception e) {
                        Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", e);
                    }
                }
                int i = rect2.top;
                int i2 = rect2.left;
                int i3 = rect2.right;
                ViewGroup viewGroup2 = this.mSubDecor;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                WindowInsetsCompat rootWindowInsets = ViewCompat.Api23Impl.getRootWindowInsets(viewGroup2);
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
                    if ((ViewCompat.Api16Impl.getWindowSystemUiVisibility(view4) & 8192) != 0) {
                        Context context = this.mContext;
                        Object obj = ContextCompat.sLock;
                        color = context.getColor(com.android.systemui.R.color.abc_decor_view_status_guard_light);
                    } else {
                        Context context2 = this.mContext;
                        Object obj2 = ContextCompat.sLock;
                        color = context2.getColor(com.android.systemui.R.color.abc_decor_view_status_guard);
                    }
                    view4.setBackgroundColor(color);
                }
                if (!this.mOverlayActionMode && z && !this.mIsIgnoreRemoveSystemTopInset) {
                    systemWindowInsetTop = 0;
                }
                View findViewById = findViewById(R.id.content);
                if (findViewById instanceof ContentFrameLayout) {
                    if (findViewById.getPaddingTop() != 0) {
                        marginLayoutParams.topMargin = 0;
                    }
                    if (findViewById.getPaddingRight() != 0) {
                        marginLayoutParams.rightMargin = 0;
                    }
                    if (findViewById.getPaddingLeft() != 0) {
                        marginLayoutParams.leftMargin = 0;
                    }
                }
                r5 = z2;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z = false;
            } else {
                z = false;
                r5 = false;
            }
            if (r5) {
                this.mActionModeView.setLayoutParams(marginLayoutParams);
                View view5 = this.mStatusGuard;
                if (view5 != null) {
                    ViewGroup.LayoutParams layoutParams2 = view5.getLayoutParams();
                    if (layoutParams2.height != systemWindowInsetTop) {
                        layoutParams2.height = systemWindowInsetTop;
                        this.mStatusGuard.setLayoutParams(layoutParams2);
                    }
                }
            }
        }
        View view6 = this.mStatusGuard;
        if (view6 != null) {
            view6.setVisibility(z ? 0 : 8);
        }
        return systemWindowInsetTop;
    }

    public AppCompatDelegateImpl(Dialog dialog, AppCompatCallback appCompatCallback) {
        this(dialog.getContext(), dialog.getWindow(), appCompatCallback, dialog);
    }

    public AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this(context, window, appCompatCallback, context);
    }

    public AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback appCompatCallback) {
        this(context, null, appCompatCallback, activity);
    }

    private AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback, Object obj) {
        AppCompatActivity appCompatActivity = null;
        this.mFadeAnim = null;
        this.mHandleNativeActionModes = true;
        this.mLocalNightMode = -100;
        this.mInvalidatePanelMenuRunnable = new RunnableC00282();
        this.mIsIgnoreRemoveSystemTopInset = false;
        this.mContext = context;
        this.mAppCompatCallback = appCompatCallback;
        this.mHost = obj;
        if (this.mLocalNightMode == -100 && (obj instanceof Dialog)) {
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
                this.mLocalNightMode = ((AppCompatDelegateImpl) appCompatActivity.getDelegate()).mLocalNightMode;
            }
        }
        if (this.mLocalNightMode == -100) {
            SimpleArrayMap simpleArrayMap = sLocalNightModes;
            Integer num = (Integer) simpleArrayMap.get(this.mHost.getClass().getName());
            if (num != null) {
                this.mLocalNightMode = num.intValue();
                simpleArrayMap.remove(this.mHost.getClass().getName());
            }
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
}
