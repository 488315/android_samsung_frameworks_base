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
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowInsets;
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
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.NavUtils;
import androidx.core.content.PermissionChecker;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.sec.ims.presence.ServiceTuple;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
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
    public final AnonymousClass2 mInvalidatePanelMenuRunnable;
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
    public AnonymousClass6 mShowActionModePopup;
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

    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$2, reason: invalid class name */
    public class AnonymousClass2 implements Runnable {
        public AnonymousClass2() {
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

    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$5, reason: invalid class name */
    public class AnonymousClass5 {
        public AnonymousClass5() {
        }
    }

    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        public ActionMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menuBuilder);
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = AppCompatDelegateImpl.this.mWindow.getCallback();
            if (callback == null) {
                return true;
            }
            callback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    public class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
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
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(appCompatDelegateImpl.mActionModeView);
                viewPropertyAnimatorCompatAnimate.alpha(0.0f);
                appCompatDelegateImpl.mFadeAnim = viewPropertyAnimatorCompatAnimate;
                viewPropertyAnimatorCompatAnimate.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.ActionModeCallbackWrapperV9.1
                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                    public final void onAnimationEnd() {
                        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = ActionModeCallbackWrapperV9.this;
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl2.mActionModePopup;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl2.mActionModeView.getParent() instanceof View) {
                            View view = (View) appCompatDelegateImpl2.mActionModeView.getParent();
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api20Impl.requestApplyInsets(view);
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

    public class AppCompatWindowCallback extends WindowCallbackWrapper {
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
            return this.mDispatchKeyEventBypassEnabled ? this.mWrapped.dispatchKeyEvent(keyEvent) : AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || this.mWrapped.dispatchKeyEvent(keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            if (!this.mWrapped.dispatchKeyShortcutEvent(keyEvent)) {
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
                            boolean zPerformPanelShortcut = appCompatDelegateImpl.performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent);
                            panelState.isPrepared = false;
                            if (zPerformPanelShortcut) {
                            }
                        }
                        return false;
                    }
                    PanelFeatureState panelFeatureState2 = appCompatDelegateImpl.mPreparedPanel;
                    if (panelFeatureState2 != null) {
                        panelFeatureState2.isHandled = true;
                        return true;
                    }
                }
            }
            return true;
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
                return this.mWrapped.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final View onCreatePanelView(int i) {
            ToolbarActionBar.ToolbarMenuCallback toolbarMenuCallback = this.mActionBarCallback;
            if (toolbarMenuCallback != null) {
                toolbarMenuCallback.getClass();
                View view = i == 0 ? new View(ToolbarActionBar.this.mDecorToolbar.mToolbar.getContext()) : null;
                if (view != null) {
                    return view;
                }
            }
            return this.mWrapped.onCreatePanelView(i);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i != 108) {
                appCompatDelegateImpl.getClass();
                return true;
            }
            appCompatDelegateImpl.initWindowDecorActionBar();
            ActionBar actionBar = appCompatDelegateImpl.mActionBar;
            if (actionBar != null) {
                actionBar.dispatchMenuVisibilityChanged(true);
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
            boolean zOnPreparePanel = this.mWrapped.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.mOverrideVisibleItems = false;
            }
            return zOnPreparePanel;
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

        /* JADX WARN: Type inference failed for: r0v37, types: [androidx.appcompat.app.AppCompatDelegateImpl$6] */
        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            ViewGroup viewGroup;
            Context contextThemeWrapper;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.mHandleNativeActionModes || i != 0) {
                return this.mWrapped.onWindowStartingActionMode(callback, i);
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
                appCompatDelegateImpl2.mActionMode = actionBar.startActionMode(actionModeCallbackWrapperV9);
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
                if (appCompatDelegateImpl2.mAppCompatCallback != null) {
                    boolean z = appCompatDelegateImpl2.mDestroyed;
                }
                if (appCompatDelegateImpl2.mActionModeView == null) {
                    if (appCompatDelegateImpl2.mIsFloating) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = appCompatDelegateImpl2.mContext.getTheme();
                        theme.resolveAttribute(com.android.systemui.R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            Resources.Theme themeNewTheme = appCompatDelegateImpl2.mContext.getResources().newTheme();
                            themeNewTheme.setTo(theme);
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                            contextThemeWrapper = new ContextThemeWrapper(appCompatDelegateImpl2.mContext, 0);
                            contextThemeWrapper.getTheme().setTo(themeNewTheme);
                        } else {
                            contextThemeWrapper = appCompatDelegateImpl2.mContext;
                        }
                        appCompatDelegateImpl2.mActionModeView = new ActionBarContextView(contextThemeWrapper);
                        PopupWindow popupWindow = new PopupWindow(contextThemeWrapper, (AttributeSet) null, com.android.systemui.R.attr.actionModePopupWindowStyle);
                        appCompatDelegateImpl2.mActionModePopup = popupWindow;
                        popupWindow.setWindowLayoutType(2);
                        appCompatDelegateImpl2.mActionModePopup.setContentView(appCompatDelegateImpl2.mActionModeView);
                        appCompatDelegateImpl2.mActionModePopup.setWidth(-1);
                        contextThemeWrapper.getTheme().resolveAttribute(com.android.systemui.R.attr.actionBarSize, typedValue, true);
                        appCompatDelegateImpl2.mActionModeView.mContentHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, contextThemeWrapper.getResources().getDisplayMetrics());
                        appCompatDelegateImpl2.mActionModePopup.setHeight(-2);
                        appCompatDelegateImpl2.mShowActionModePopup = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewGroup viewGroup2;
                                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl3.mActionModePopup.showAtLocation(appCompatDelegateImpl3.mActionModeView, 55, 0, 0);
                                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = AppCompatDelegateImpl.this.mFadeAnim;
                                if (viewPropertyAnimatorCompat2 != null) {
                                    viewPropertyAnimatorCompat2.cancel();
                                }
                                AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
                                if (!(appCompatDelegateImpl4.mSubDecorInstalled && (viewGroup2 = appCompatDelegateImpl4.mSubDecor) != null && viewGroup2.isLaidOut())) {
                                    AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                    AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                    return;
                                }
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                                AppCompatDelegateImpl appCompatDelegateImpl5 = AppCompatDelegateImpl.this;
                                ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(appCompatDelegateImpl5.mActionModeView);
                                viewPropertyAnimatorCompatAnimate.alpha(1.0f);
                                appCompatDelegateImpl5.mFadeAnim = viewPropertyAnimatorCompatAnimate;
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6.1
                                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                    public final void onAnimationEnd() {
                                        AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
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
                        Context context = appCompatDelegateImpl2.mSubDecor.getContext();
                        View viewFindViewById = appCompatDelegateImpl2.mSubDecor.findViewById(context.getResources().getIdentifier("collapsing_toolbar", "id", context.getPackageName()));
                        if (viewFindViewById == null) {
                            viewFindViewById = appCompatDelegateImpl2.mSubDecor.findViewById(context.getResources().getIdentifier("sesl_toolbar_container", "id", context.getPackageName()));
                        }
                        ViewStubCompat viewStubCompat = (viewFindViewById == null || appCompatDelegateImpl2.mOverlayActionMode) ? (ViewStubCompat) appCompatDelegateImpl2.mSubDecor.findViewById(com.android.systemui.R.id.action_mode_bar_stub) : (ViewStubCompat) viewFindViewById.findViewById(com.android.systemui.R.id.action_mode_bar_stub);
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
                    if (actionModeCallbackWrapperV9.mWrapped.onCreateActionMode(standaloneActionMode, standaloneActionMode.mMenu)) {
                        standaloneActionMode.invalidate();
                        appCompatDelegateImpl2.mActionModeView.initForMode(standaloneActionMode);
                        appCompatDelegateImpl2.mActionMode = standaloneActionMode;
                        if (appCompatDelegateImpl2.mSubDecorInstalled && (viewGroup = appCompatDelegateImpl2.mSubDecor) != null && viewGroup.isLaidOut()) {
                            appCompatDelegateImpl2.mActionModeView.setAlpha(0.0f);
                            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(appCompatDelegateImpl2.mActionModeView);
                            viewPropertyAnimatorCompatAnimate.alpha(1.0f);
                            appCompatDelegateImpl2.mFadeAnim = viewPropertyAnimatorCompatAnimate;
                            viewPropertyAnimatorCompatAnimate.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.7
                                @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                public final void onAnimationEnd() {
                                    AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                                    appCompatDelegateImpl3.mActionModeView.setAlpha(1.0f);
                                    appCompatDelegateImpl3.mFadeAnim.setListener(null);
                                    appCompatDelegateImpl3.mFadeAnim = null;
                                }

                                @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                public final void onAnimationStart() {
                                    AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                                    appCompatDelegateImpl3.mActionModeView.setVisibility(0);
                                    if (appCompatDelegateImpl3.mActionModeView.getParent() instanceof View) {
                                        View view = (View) appCompatDelegateImpl3.mActionModeView.getParent();
                                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                        ViewCompat.Api20Impl.requestApplyInsets(view);
                                    }
                                }
                            });
                        } else {
                            appCompatDelegateImpl2.mActionModeView.setAlpha(1.0f);
                            appCompatDelegateImpl2.mActionModeView.setVisibility(0);
                            if (appCompatDelegateImpl2.mActionModeView.getParent() instanceof View) {
                                View view = (View) appCompatDelegateImpl2.mActionModeView.getParent();
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
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

    public class AutoBatteryNightModeManager extends AutoNightModeManager {
        public final PowerManager mPowerManager;

        public AutoBatteryNightModeManager(Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final IntentFilter createIntentFilterForBroadcastReceiver() {
            return AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("android.os.action.POWER_SAVE_MODE_CHANGED");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final int getApplyableNightMode() {
            return this.mPowerManager.isPowerSaveMode() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final void onChange() {
            AppCompatDelegateImpl.this.applyApplicationSpecificConfig(true);
        }
    }

    public abstract class AutoNightModeManager {
        public AnonymousClass1 mReceiver;

        public AutoNightModeManager() {
        }

        public final void cleanup() {
            AnonymousClass1 anonymousClass1 = this.mReceiver;
            if (anonymousClass1 != null) {
                try {
                    AppCompatDelegateImpl.this.mContext.unregisterReceiver(anonymousClass1);
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
            IntentFilter intentFilterCreateIntentFilterForBroadcastReceiver = createIntentFilterForBroadcastReceiver();
            if (intentFilterCreateIntentFilterForBroadcastReceiver.countActions() == 0) {
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
            AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, intentFilterCreateIntentFilterForBroadcastReceiver);
        }
    }

    public class AutoTimeNightModeManager extends AutoNightModeManager {
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
            boolean z;
            long j;
            TwilightManager twilightManager = this.mTwilightManager;
            TwilightManager.TwilightState twilightState = twilightManager.mTwilightState;
            if (twilightState == null || twilightState.nextUpdate <= System.currentTimeMillis()) {
                Location lastKnownLocationForProvider = PermissionChecker.checkSelfPermission(twilightManager.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? twilightManager.getLastKnownLocationForProvider("network") : null;
                Location lastKnownLocationForProvider2 = PermissionChecker.checkSelfPermission(twilightManager.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 ? twilightManager.getLastKnownLocationForProvider("gps") : null;
                if (lastKnownLocationForProvider2 == null || lastKnownLocationForProvider == null ? lastKnownLocationForProvider2 != null : lastKnownLocationForProvider2.getTime() > lastKnownLocationForProvider.getTime()) {
                    lastKnownLocationForProvider = lastKnownLocationForProvider2;
                }
                if (lastKnownLocationForProvider != null) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (TwilightCalculator.sInstance == null) {
                        TwilightCalculator.sInstance = new TwilightCalculator();
                    }
                    TwilightCalculator twilightCalculator = TwilightCalculator.sInstance;
                    twilightCalculator.calculateTwilight(lastKnownLocationForProvider.getLatitude(), lastKnownLocationForProvider.getLongitude(), jCurrentTimeMillis - 86400000);
                    twilightCalculator.calculateTwilight(lastKnownLocationForProvider.getLatitude(), lastKnownLocationForProvider.getLongitude(), jCurrentTimeMillis);
                    z = twilightCalculator.state == 1;
                    long j2 = twilightCalculator.sunrise;
                    long j3 = twilightCalculator.sunset;
                    twilightCalculator.calculateTwilight(lastKnownLocationForProvider.getLatitude(), lastKnownLocationForProvider.getLongitude(), jCurrentTimeMillis + 86400000);
                    long j4 = twilightCalculator.sunrise;
                    if (j2 == -1 || j3 == -1) {
                        j = jCurrentTimeMillis + 43200000;
                    } else {
                        if (jCurrentTimeMillis > j3) {
                            j2 = j4;
                        } else if (jCurrentTimeMillis > j2) {
                            j2 = j3;
                        }
                        j = j2 + 60000;
                    }
                    twilightState.isNight = z;
                    twilightState.nextUpdate = j;
                } else {
                    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
                    int i = Calendar.getInstance().get(11);
                    if (i < 6 || i >= 22) {
                        z = true;
                    }
                }
            } else {
                z = twilightState.isNight;
            }
            return z ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public final void onChange() {
            AppCompatDelegateImpl.this.applyApplicationSpecificConfig(true);
        }
    }

    public class ListMenuDecorView extends ContentFrameLayout {
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
            Window.Callback callback;
            if (menuBuilder != menuBuilder.getRootMenu()) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.mHasActionBar || (callback = appCompatDelegateImpl.mWindow.getCallback()) == null || appCompatDelegateImpl.mDestroyed) {
                return true;
            }
            callback.onMenuOpened(108, menuBuilder);
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
    /* JADX WARN: Removed duplicated region for block: B:40:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean applyApplicationSpecificConfig(boolean z) {
        int i;
        int i2;
        DisplayMetrics displayMetrics;
        boolean z2;
        boolean z3;
        if (this.mDestroyed) {
            return false;
        }
        int i3 = this.mLocalNightMode;
        if (i3 == -100) {
            i3 = AppCompatDelegate.sDefaultNightMode;
        }
        int i4 = i3;
        Configuration configurationCreateOverrideAppConfiguration = createOverrideAppConfiguration(this.mContext, mapNightMode(i4, this.mContext), null, false);
        Context context = this.mContext;
        boolean z4 = true;
        if (this.mActivityHandlesConfigFlagsChecked || !(this.mHost instanceof Activity)) {
            this.mActivityHandlesConfigFlagsChecked = true;
            i = this.mActivityHandlesConfigFlags;
        } else {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                i = 0;
            } else {
                try {
                    ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(context, this.mHost.getClass()), 269221888);
                    if (activityInfo != null) {
                        this.mActivityHandlesConfigFlags = activityInfo.configChanges;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                    this.mActivityHandlesConfigFlags = 0;
                }
                this.mActivityHandlesConfigFlagsChecked = true;
                i = this.mActivityHandlesConfigFlags;
            }
        }
        Configuration configuration = this.mEffectiveConfiguration;
        if (configuration == null) {
            configuration = this.mContext.getResources().getConfiguration();
        }
        int i5 = configuration.uiMode & 48;
        int i6 = configurationCreateOverrideAppConfiguration.uiMode & 48;
        LocaleListCompat localeListCompatForLanguageTags = LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
        int i7 = i5 != i6 ? 512 : 0;
        if (((~i) & i7) != 0 && z && this.mBaseContextAttached && ((z3 = sCanReturnDifferentContext) || this.mCreated)) {
            Object obj = this.mHost;
            if ((obj instanceof Activity) && !((Activity) obj).isChild()) {
                displayMetrics = null;
                i2 = i7;
                Log.d("AppCompatDelegate", String.format("updateAppConfiguration attempting to recreate Activity [currentNightMode:%s, newNightMode:%s, currentLocales:%s, newLocales:%s, activityHandlingNightModeChanges:%s, activityHandlingLocalesChanges:%s, activityHandlingLayoutDirectionChanges:%s, baseContextAttached:%s, created:%s, canReturnDifferentContext:%s, host:%s], application configuration [%s]", Integer.valueOf(i5), Integer.valueOf(i6), localeListCompatForLanguageTags, null, Boolean.valueOf((i & 512) != 0), Boolean.valueOf((i & 4) != 0), Boolean.valueOf((i & 8192) != 0), Boolean.valueOf(this.mBaseContextAttached), Boolean.valueOf(this.mCreated), Boolean.valueOf(z3), this.mHost, this.mContext.getApplicationContext().getResources().getConfiguration()));
                ((Activity) this.mHost).recreate();
                z2 = true;
            }
        } else {
            i2 = i7;
            displayMetrics = null;
            z2 = false;
        }
        if (z2 || i2 == 0) {
            z4 = z2;
        } else {
            boolean z5 = (i & i2) == i2;
            Resources resources = this.mContext.getResources();
            Configuration configuration2 = new Configuration(resources.getConfiguration());
            configuration2.uiMode = i6 | (resources.getConfiguration().uiMode & (-49));
            resources.updateConfiguration(configuration2, displayMetrics);
            int i8 = this.mThemeResId;
            if (i8 != 0) {
                this.mContext.setTheme(i8);
                this.mContext.getTheme().applyStyle(this.mThemeResId, true);
            }
            if (z5) {
                Object obj2 = this.mHost;
                if (obj2 instanceof Activity) {
                    Activity activity = (Activity) obj2;
                    if (activity instanceof LifecycleOwner) {
                        if (((LifecycleOwner) activity).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                            activity.onConfigurationChanged(configuration2);
                        }
                    } else if (this.mCreated && !this.mDestroyed) {
                        activity.onConfigurationChanged(configuration2);
                    }
                }
            }
        }
        if (z4) {
            Object obj3 = this.mHost;
            if ((obj3 instanceof AppCompatActivity) && (i2 & 512) != 0) {
                ((AppCompatActivity) obj3).getClass();
            }
        }
        if (i4 == 0) {
            getAutoTimeNightModeManager(this.mContext).setup();
        } else {
            AutoTimeNightModeManager autoTimeNightModeManager = this.mAutoTimeNightModeManager;
            if (autoTimeNightModeManager != null) {
                autoTimeNightModeManager.cleanup();
            }
        }
        if (i4 == 3) {
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
        return z4;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final boolean applyDayNight() {
        return applyApplicationSpecificConfig(true);
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
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mContext, (AttributeSet) null, sWindowBackgroundStyleable);
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            window.setBackgroundDrawable(drawableIfKnown);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
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
        ActionMenuView actionMenuView = actionBarOverlayLayout.mDecorToolbar.mToolbar.mMenuView;
        if (actionMenuView != null && (actionMenuPresenter = actionMenuView.mPresenter) != null) {
            actionMenuPresenter.hideOverflowMenu();
            ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
            if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
                actionButtonSubmenu.mPopup.dismiss();
            }
        }
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mDestroyed) {
            callback.onPanelClosed(108, menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    public final void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        ListMenuDecorView listMenuDecorView;
        DecorContentParent decorContentParent;
        if (z && panelFeatureState.featureId == 0 && (decorContentParent = this.mDecorContentParent) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
            actionBarOverlayLayout.pullChildren();
            if (actionBarOverlayLayout.mDecorToolbar.mToolbar.isOverflowMenuShowing()) {
                checkCloseActionMenu(panelFeatureState.menu);
                return;
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean zShowOverflowMenu;
        boolean zPreparePanel;
        ActionMenuView actionMenuView;
        ActionMenuPresenter actionMenuPresenter;
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
                if (!callback.dispatchKeyEvent(keyEvent)) {
                    int keyCode = keyEvent.getKeyCode();
                    if (keyEvent.getAction() == 0) {
                        if (keyCode == 4) {
                            this.mLongPressBackDown = (keyEvent.getFlags() & 128) != 0;
                            return false;
                        }
                        if (keyCode == 82) {
                            if (keyEvent.getRepeatCount() == 0) {
                                PanelFeatureState panelState = getPanelState(0);
                                if (!panelState.isOpen) {
                                    preparePanel(panelState, keyEvent);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                    if (keyCode != 4) {
                        if (keyCode == 82) {
                            if (this.mActionMode == null) {
                                PanelFeatureState panelState2 = getPanelState(0);
                                DecorContentParent decorContentParent = this.mDecorContentParent;
                                if (decorContentParent != null) {
                                    ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
                                    actionBarOverlayLayout.pullChildren();
                                    Toolbar toolbar = actionBarOverlayLayout.mDecorToolbar.mToolbar;
                                    if (toolbar.getVisibility() != 0 || (actionMenuView = toolbar.mMenuView) == null || !actionMenuView.mReserveOverflow || ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
                                        boolean z = panelState2.isOpen;
                                        if (z || panelState2.isHandled) {
                                            closePanel(panelState2, true);
                                            zShowOverflowMenu = z;
                                            if (zShowOverflowMenu) {
                                                AudioManager audioManager = (AudioManager) this.mContext.getApplicationContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                                                if (audioManager != null) {
                                                    audioManager.playSoundEffect(0);
                                                    return true;
                                                }
                                                Log.w("AppCompatDelegate", "Couldn't get audio manager");
                                                return true;
                                            }
                                        } else {
                                            if (panelState2.isPrepared) {
                                                if (panelState2.refreshMenuContent) {
                                                    panelState2.isPrepared = false;
                                                    zPreparePanel = preparePanel(panelState2, keyEvent);
                                                } else {
                                                    zPreparePanel = true;
                                                }
                                                if (zPreparePanel) {
                                                    openPanel(panelState2, keyEvent);
                                                    zShowOverflowMenu = true;
                                                    if (zShowOverflowMenu) {
                                                    }
                                                }
                                            }
                                            zShowOverflowMenu = false;
                                            if (zShowOverflowMenu) {
                                            }
                                        }
                                    } else {
                                        ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.mDecorContentParent;
                                        actionBarOverlayLayout2.pullChildren();
                                        if (actionBarOverlayLayout2.mDecorToolbar.mToolbar.isOverflowMenuShowing()) {
                                            ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.mDecorContentParent;
                                            actionBarOverlayLayout3.pullChildren();
                                            ActionMenuView actionMenuView2 = actionBarOverlayLayout3.mDecorToolbar.mToolbar.mMenuView;
                                            if (actionMenuView2 != null && (actionMenuPresenter = actionMenuView2.mPresenter) != null && actionMenuPresenter.hideOverflowMenu()) {
                                                zShowOverflowMenu = true;
                                            }
                                            if (zShowOverflowMenu) {
                                            }
                                        } else {
                                            if (!this.mDestroyed && preparePanel(panelState2, keyEvent)) {
                                                ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.mDecorContentParent;
                                                actionBarOverlayLayout4.pullChildren();
                                                zShowOverflowMenu = actionBarOverlayLayout4.mDecorToolbar.mToolbar.showOverflowMenu();
                                            }
                                            if (zShowOverflowMenu) {
                                            }
                                        }
                                        zShowOverflowMenu = false;
                                        if (zShowOverflowMenu) {
                                        }
                                    }
                                }
                            }
                        }
                        return false;
                    }
                    if (!onBackPressed()) {
                        return false;
                    }
                }
            } finally {
                appCompatWindowCallback.mDispatchKeyEventBypassEnabled = false;
            }
        }
        return true;
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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!typedArrayObtainStyledAttributes.hasValue(145)) {
            typedArrayObtainStyledAttributes.recycle();
            Log.e("AppCompatDelegate", "createSubDecor: mContext = " + this.mContext);
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(154, false)) {
            requestWindowFeature(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(145, false)) {
            requestWindowFeature(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(146, false)) {
            requestWindowFeature(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(147, false)) {
            requestWindowFeature(10);
        }
        this.mIsFloating = typedArrayObtainStyledAttributes.getBoolean(1, false);
        if (typedArrayObtainStyledAttributes.hasValue(86)) {
            this.mIsIgnoreRemoveSystemTopInset = typedArrayObtainStyledAttributes.getBoolean(86, false);
        }
        typedArrayObtainStyledAttributes.recycle();
        ensureWindow();
        this.mWindow.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        if (this.mWindowNoTitle) {
            viewGroup = this.mOverlayActionMode ? (ViewGroup) layoutInflaterFrom.inflate(com.android.systemui.R.layout.sesl_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(com.android.systemui.R.layout.sesl_screen_simple, (ViewGroup) null);
        } else if (this.mIsFloating) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(com.android.systemui.R.layout.sesl_dialog_title, (ViewGroup) null);
            this.mOverlayActionBar = false;
            this.mHasActionBar = false;
        } else if (this.mHasActionBar) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(com.android.systemui.R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new ContextThemeWrapper(this.mContext, typedValue.resourceId) : this.mContext).inflate(com.android.systemui.R.layout.sesl_screen_toolbar, (ViewGroup) null);
            DecorContentParent decorContentParent = (DecorContentParent) viewGroup.findViewById(com.android.systemui.R.id.decor_content_parent);
            this.mDecorContentParent = decorContentParent;
            Window.Callback callback = this.mWindow.getCallback();
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
            actionBarOverlayLayout.pullChildren();
            actionBarOverlayLayout.mDecorToolbar.mWindowCallback = callback;
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
            throw new IllegalArgumentException(MoveResult$$ExternalSyntheticOutline0.m(sb, this.mWindowNoTitle, " }"));
        }
        OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                boolean z;
                boolean z2;
                int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                appCompatDelegateImpl.getClass();
                int systemWindowInsetTop2 = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
                ActionBarContextView actionBarContextView = appCompatDelegateImpl.mActionModeView;
                if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                    z = false;
                } else {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) appCompatDelegateImpl.mActionModeView.getLayoutParams();
                    if (appCompatDelegateImpl.mActionModeView.isShown()) {
                        if (appCompatDelegateImpl.mTempRect1 == null) {
                            appCompatDelegateImpl.mTempRect1 = new Rect();
                            appCompatDelegateImpl.mTempRect2 = new Rect();
                        }
                        Rect rect = appCompatDelegateImpl.mTempRect1;
                        Rect rect2 = appCompatDelegateImpl.mTempRect2;
                        if (windowInsetsCompat == null) {
                            rect.set(null);
                        } else {
                            rect.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                        }
                        Insets systemWindowInsets = appCompatDelegateImpl.mSubDecor.computeSystemWindowInsets(new WindowInsets.Builder().setSystemWindowInsets(Insets.of(rect)).build(), rect2).getSystemWindowInsets();
                        rect.set(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
                        int i = rect.top;
                        int i2 = rect.left;
                        int i3 = rect.right;
                        ViewGroup viewGroup2 = appCompatDelegateImpl.mSubDecor;
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
                        if (i <= 0 || appCompatDelegateImpl.mStatusGuard != null) {
                            View view2 = appCompatDelegateImpl.mStatusGuard;
                            if (view2 != null) {
                                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                                int i4 = marginLayoutParams2.height;
                                int i5 = marginLayoutParams.topMargin;
                                if (i4 != i5 || marginLayoutParams2.leftMargin != systemWindowInsetLeft || marginLayoutParams2.rightMargin != systemWindowInsetRight) {
                                    marginLayoutParams2.height = i5;
                                    marginLayoutParams2.leftMargin = systemWindowInsetLeft;
                                    marginLayoutParams2.rightMargin = systemWindowInsetRight;
                                    appCompatDelegateImpl.mStatusGuard.setLayoutParams(marginLayoutParams2);
                                }
                            }
                        } else {
                            View view3 = new View(appCompatDelegateImpl.mContext);
                            appCompatDelegateImpl.mStatusGuard = view3;
                            view3.setVisibility(8);
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                            layoutParams.leftMargin = systemWindowInsetLeft;
                            layoutParams.rightMargin = systemWindowInsetRight;
                            appCompatDelegateImpl.mSubDecor.addView(appCompatDelegateImpl.mStatusGuard, -1, layoutParams);
                        }
                        View view4 = appCompatDelegateImpl.mStatusGuard;
                        z = view4 != null;
                        if (z && view4.getVisibility() != 0) {
                            View view5 = appCompatDelegateImpl.mStatusGuard;
                            view5.setBackgroundColor((view5.getWindowSystemUiVisibility() & 8192) != 0 ? appCompatDelegateImpl.mContext.getColor(com.android.systemui.R.color.abc_decor_view_status_guard_light) : appCompatDelegateImpl.mContext.getColor(com.android.systemui.R.color.abc_decor_view_status_guard));
                        }
                        if (!appCompatDelegateImpl.mOverlayActionMode && z && !appCompatDelegateImpl.mIsIgnoreRemoveSystemTopInset) {
                            systemWindowInsetTop2 = 0;
                        }
                        View viewFindViewById = appCompatDelegateImpl.findViewById(R.id.content);
                        if (viewFindViewById instanceof ContentFrameLayout) {
                            if (viewFindViewById.getPaddingTop() != 0) {
                                marginLayoutParams.topMargin = 0;
                            }
                            if (viewFindViewById.getPaddingRight() != 0) {
                                marginLayoutParams.rightMargin = 0;
                            }
                            if (viewFindViewById.getPaddingLeft() != 0) {
                                marginLayoutParams.leftMargin = 0;
                            }
                        }
                        z = z;
                        z = z2;
                    } else if (marginLayoutParams.topMargin != 0) {
                        marginLayoutParams.topMargin = 0;
                        z = false;
                    } else {
                        z = false;
                        z = false;
                    }
                    if (z) {
                        appCompatDelegateImpl.mActionModeView.setLayoutParams(marginLayoutParams);
                        View view6 = appCompatDelegateImpl.mStatusGuard;
                        if (view6 != null) {
                            ViewGroup.LayoutParams layoutParams2 = view6.getLayoutParams();
                            if (layoutParams2.height != systemWindowInsetTop2) {
                                layoutParams2.height = systemWindowInsetTop2;
                                appCompatDelegateImpl.mStatusGuard.setLayoutParams(layoutParams2);
                            }
                        }
                    }
                }
                View view7 = appCompatDelegateImpl.mStatusGuard;
                if (view7 != null) {
                    view7.setVisibility(z ? 0 : 8);
                }
                if (systemWindowInsetTop != systemWindowInsetTop2) {
                    int systemWindowInsetLeft2 = windowInsetsCompat.getSystemWindowInsetLeft();
                    int systemWindowInsetRight2 = windowInsetsCompat.getSystemWindowInsetRight();
                    int systemWindowInsetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                    WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(windowInsetsCompat);
                    androidx.core.graphics.Insets insetsOf = androidx.core.graphics.Insets.of(systemWindowInsetLeft2, systemWindowInsetTop2, systemWindowInsetRight2, systemWindowInsetBottom);
                    WindowInsetsCompat.BuilderImpl30 builderImpl30 = builder.mImpl;
                    builderImpl30.setSystemWindowInsets(insetsOf);
                    windowInsetsCompat = builderImpl30.build();
                }
                return ViewCompat.onApplyWindowInsets(windowInsetsCompat, view);
            }
        };
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(viewGroup, onApplyWindowInsetsListener);
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(com.android.systemui.R.id.title);
        }
        try {
            Class[] clsArr = new Class[0];
            Method method = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", null);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(viewGroup, null);
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
        contentFrameLayout.mAttachListener = new AnonymousClass5();
        this.mSubDecor = viewGroup;
        Object obj = this.mHost;
        CharSequence title = obj instanceof Activity ? ((Activity) obj).getTitle() : this.mTitle;
        if (!TextUtils.isEmpty(title)) {
            DecorContentParent decorContentParent2 = this.mDecorContentParent;
            if (decorContentParent2 != null) {
                ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) decorContentParent2;
                actionBarOverlayLayout2.pullChildren();
                ToolbarWidgetWrapper toolbarWidgetWrapper = actionBarOverlayLayout2.mDecorToolbar;
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
        if (contentFrameLayout2.isLaidOut()) {
            contentFrameLayout2.requestLayout();
        }
        TypedArray typedArrayObtainStyledAttributes2 = this.mContext.obtainStyledAttributes(iArr);
        if (contentFrameLayout2.mMinWidthMajor == null) {
            contentFrameLayout2.mMinWidthMajor = new TypedValue();
        }
        typedArrayObtainStyledAttributes2.getValue(152, contentFrameLayout2.mMinWidthMajor);
        if (contentFrameLayout2.mMinWidthMinor == null) {
            contentFrameLayout2.mMinWidthMinor = new TypedValue();
        }
        typedArrayObtainStyledAttributes2.getValue(153, contentFrameLayout2.mMinWidthMinor);
        if (typedArrayObtainStyledAttributes2.hasValue(150)) {
            if (contentFrameLayout2.mFixedWidthMajor == null) {
                contentFrameLayout2.mFixedWidthMajor = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(150, contentFrameLayout2.mFixedWidthMajor);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(151)) {
            if (contentFrameLayout2.mFixedWidthMinor == null) {
                contentFrameLayout2.mFixedWidthMinor = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(151, contentFrameLayout2.mFixedWidthMinor);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(148)) {
            if (contentFrameLayout2.mFixedHeightMajor == null) {
                contentFrameLayout2.mFixedHeightMajor = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(148, contentFrameLayout2.mFixedHeightMajor);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(149)) {
            if (contentFrameLayout2.mFixedHeightMinor == null) {
                contentFrameLayout2.mFixedHeightMinor = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(149, contentFrameLayout2.mFixedHeightMinor);
        }
        typedArrayObtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.mSubDecorInstalled = true;
        PanelFeatureState panelState = getPanelState(0);
        if (this.mDestroyed || panelState.menu != null) {
            return;
        }
        invalidatePanelMenu(108);
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
                TwilightManager.sInstance = new TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
            }
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.sInstance);
        }
        return this.mAutoTimeNightModeManager;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final Context getContextForDelegate() {
        return this.mContext;
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
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        if (layoutInflaterFrom.getFactory() == null) {
            layoutInflaterFrom.setFactory2(this);
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof AppCompatDelegateImpl) {
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
            invalidatePanelMenu(0);
        }
    }

    public final void invalidatePanelMenu(int i) {
        this.mInvalidatePanelMenuFeatures = (1 << i) | this.mInvalidatePanelMenuFeatures;
        if (this.mInvalidatePanelMenuPosted) {
            return;
        }
        View decorView = this.mWindow.getDecorView();
        AnonymousClass2 anonymousClass2 = this.mInvalidatePanelMenuRunnable;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        decorView.postOnAnimation(anonymousClass2);
        this.mInvalidatePanelMenuPosted = true;
    }

    public final int mapNightMode(int i, Context context) {
        if (i != -100) {
            if (i != -1) {
                if (i != 0) {
                    if (i != 1 && i != 2) {
                        if (i != 3) {
                            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                        }
                        if (this.mAutoBatteryNightModeManager == null) {
                            this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
                        }
                        return this.mAutoBatteryNightModeManager.getApplyableNightMode();
                    }
                } else if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() != 0) {
                    return getAutoTimeNightModeManager(context).getApplyableNightMode();
                }
            }
            return i;
        }
        return -1;
    }

    public final boolean onBackPressed() {
        boolean z = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        PanelFeatureState panelState = getPanelState(0);
        if (!panelState.isOpen) {
            androidx.appcompat.view.ActionMode actionMode = this.mActionMode;
            if (actionMode != null) {
                actionMode.finish();
                return true;
            }
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            if (actionBar == null || !actionBar.collapseActionView()) {
                return false;
            }
        } else if (!z) {
            closePanel(panelState, true);
            return true;
        }
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void onCreate() {
        String parentActivityName;
        this.mBaseContextAttached = true;
        applyApplicationSpecificConfig(false);
        ensureWindow();
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            try {
                Activity activity = (Activity) obj;
                try {
                    parentActivityName = NavUtils.getParentActivityName(activity, activity.getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            } catch (IllegalArgumentException unused) {
                parentActivityName = null;
            }
            if (parentActivityName != null) {
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
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:24:0x009a  */
    @Override // android.view.LayoutInflater.Factory2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View appCompatRatingBar;
        char c = '\b';
        View view2 = null;
        if (this.mAppCompatViewInflater == null) {
            TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(R$styleable.AppCompatTheme);
            String string = typedArrayObtainStyledAttributes.getString(144);
            typedArrayObtainStyledAttributes.recycle();
            if (string == null) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    Class[] clsArr = new Class[0];
                    this.mAppCompatViewInflater = (AppCompatViewInflater) this.mContext.getClassLoader().loadClass(string).getDeclaredConstructor(null).newInstance(null);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        AppCompatViewInflater appCompatViewInflater = this.mAppCompatViewInflater;
        int i = VectorEnabledTintResources.$r8$clinit;
        appCompatViewInflater.getClass();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.View, 0, 0);
        int resourceId = typedArrayObtainStyledAttributes2.getResourceId(8, 0);
        if (resourceId != 0) {
            Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        typedArrayObtainStyledAttributes2.recycle();
        Context contextThemeWrapper = (resourceId == 0 || ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).mThemeResource == resourceId)) ? context : new ContextThemeWrapper(context, resourceId);
        str.getClass();
        switch (str.hashCode()) {
            case -1946472170:
                if (!str.equals("RatingBar")) {
                    c = 65535;
                    break;
                } else {
                    c = 0;
                    break;
                }
            case -1455429095:
                if (str.equals("CheckedTextView")) {
                    c = 1;
                    break;
                }
                break;
            case -1346021293:
                if (str.equals("MultiAutoCompleteTextView")) {
                    c = 2;
                    break;
                }
                break;
            case -938935918:
                if (str.equals("TextView")) {
                    c = 3;
                    break;
                }
                break;
            case -937446323:
                if (str.equals("ImageButton")) {
                    c = 4;
                    break;
                }
                break;
            case -339785223:
                if (str.equals("Spinner")) {
                    c = 5;
                    break;
                }
                break;
            case 776382189:
                if (str.equals("RadioButton")) {
                    c = 6;
                    break;
                }
                break;
            case 799298502:
                if (str.equals("ToggleButton")) {
                    c = 7;
                    break;
                }
                break;
            case 1125864064:
                if (!str.equals("ImageView")) {
                }
                break;
            case 1413872058:
                if (str.equals("AutoCompleteTextView")) {
                    c = '\t';
                    break;
                }
                break;
            case 1601505219:
                if (str.equals("CheckBox")) {
                    c = '\n';
                    break;
                }
                break;
            case 1666676343:
                if (str.equals("EditText")) {
                    c = 11;
                    break;
                }
                break;
            case 2001146706:
                if (str.equals("Button")) {
                    c = '\f';
                    break;
                }
                break;
        }
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
                break;
            case 4:
                appCompatRatingBar = new AppCompatImageButton(contextThemeWrapper, attributeSet);
                break;
            case 5:
                appCompatRatingBar = new AppCompatSpinner(contextThemeWrapper, attributeSet);
                break;
            case 6:
                appCompatRatingBar = appCompatViewInflater.createRadioButton(contextThemeWrapper, attributeSet);
                break;
            case 7:
                appCompatRatingBar = new AppCompatToggleButton(contextThemeWrapper, attributeSet);
                break;
            case '\b':
                appCompatRatingBar = new AppCompatImageView(contextThemeWrapper, attributeSet);
                break;
            case '\t':
                appCompatRatingBar = appCompatViewInflater.createAutoCompleteTextView(contextThemeWrapper, attributeSet);
                break;
            case '\n':
                appCompatRatingBar = appCompatViewInflater.createCheckBox(contextThemeWrapper, attributeSet);
                break;
            case 11:
                appCompatRatingBar = new AppCompatEditText(contextThemeWrapper, attributeSet);
                break;
            case '\f':
                appCompatRatingBar = appCompatViewInflater.createButton(contextThemeWrapper, attributeSet);
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
                            View viewCreateViewByPrefix = appCompatViewInflater.createViewByPrefix(contextThemeWrapper, str, strArr[i2]);
                            if (viewCreateViewByPrefix != null) {
                                objArr[0] = null;
                                objArr[1] = null;
                                view2 = viewCreateViewByPrefix;
                            } else {
                                i2++;
                            }
                        }
                    }
                } else {
                    View viewCreateViewByPrefix2 = appCompatViewInflater.createViewByPrefix(contextThemeWrapper, str, null);
                    objArr[0] = null;
                    objArr[1] = null;
                    view2 = viewCreateViewByPrefix2;
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
            if ((context2 instanceof ContextWrapper) && appCompatRatingBar.hasOnClickListeners()) {
                TypedArray typedArrayObtainStyledAttributes3 = context2.obtainStyledAttributes(attributeSet, AppCompatViewInflater.sOnClickAttrs);
                String string2 = typedArrayObtainStyledAttributes3.getString(0);
                if (string2 != null) {
                    appCompatRatingBar.setOnClickListener(new AppCompatViewInflater.DeclaredOnClickListener(appCompatRatingBar, string2));
                }
                typedArrayObtainStyledAttributes3.recycle();
            }
        }
        return appCompatRatingBar;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    @Override // androidx.appcompat.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDestroy() {
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
            } else {
                sLocalNightModes.remove(this.mHost.getClass().getName());
            }
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        AutoTimeNightModeManager autoTimeNightModeManager = this.mAutoTimeNightModeManager;
        if (autoTimeNightModeManager != null) {
            autoTimeNightModeManager.cleanup();
        }
        AutoBatteryNightModeManager autoBatteryNightModeManager = this.mAutoBatteryNightModeManager;
        if (autoBatteryNightModeManager != null) {
            autoBatteryNightModeManager.cleanup();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        PanelFeatureState panelFeatureState;
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mDestroyed) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            PanelFeatureState[] panelFeatureStateArr = this.mPanels;
            int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
            int i = 0;
            while (true) {
                if (i < length) {
                    panelFeatureState = panelFeatureStateArr[i];
                    if (panelFeatureState != null && panelFeatureState.menu == rootMenu) {
                        break;
                    }
                    i++;
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState != null) {
                return callback.onMenuItemSelected(panelFeatureState.featureId, menuItem);
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0044, code lost:
    
        if (r6.isOverflowMenuShowing() != false) goto L20;
     */
    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMenuModeChange(MenuBuilder menuBuilder) {
        ActionMenuView actionMenuView;
        ActionMenuPresenter actionMenuPresenter;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
            actionBarOverlayLayout.pullChildren();
            Toolbar toolbar = actionBarOverlayLayout.mDecorToolbar.mToolbar;
            if (toolbar.getVisibility() == 0 && (actionMenuView = toolbar.mMenuView) != null && actionMenuView.mReserveOverflow) {
                if (ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
                    ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.mDecorContentParent;
                    actionBarOverlayLayout2.pullChildren();
                    ActionMenuView actionMenuView2 = actionBarOverlayLayout2.mDecorToolbar.mToolbar.mMenuView;
                    if (actionMenuView2 != null) {
                        ActionMenuPresenter actionMenuPresenter2 = actionMenuView2.mPresenter;
                        if (actionMenuPresenter2 != null) {
                            if (actionMenuPresenter2.mPostedOpenRunnable == null) {
                            }
                        }
                    }
                }
                Window.Callback callback = this.mWindow.getCallback();
                ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.mDecorContentParent;
                actionBarOverlayLayout3.pullChildren();
                if (actionBarOverlayLayout3.mDecorToolbar.mToolbar.isOverflowMenuShowing()) {
                    ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.mDecorContentParent;
                    actionBarOverlayLayout4.pullChildren();
                    ActionMenuView actionMenuView3 = actionBarOverlayLayout4.mDecorToolbar.mToolbar.mMenuView;
                    if (actionMenuView3 != null && (actionMenuPresenter = actionMenuView3.mPresenter) != null) {
                        actionMenuPresenter.hideOverflowMenu();
                    }
                    if (this.mDestroyed) {
                        return;
                    }
                    callback.onPanelClosed(108, getPanelState(0).menu);
                    return;
                }
                if (callback == null || this.mDestroyed) {
                    return;
                }
                if (this.mInvalidatePanelMenuPosted && (1 & this.mInvalidatePanelMenuFeatures) != 0) {
                    this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                PanelFeatureState panelState = getPanelState(0);
                MenuBuilder menuBuilder2 = panelState.menu;
                if (menuBuilder2 == null || panelState.refreshMenuContent || !callback.onPreparePanel(0, panelState.createdPanelView, menuBuilder2)) {
                    return;
                }
                callback.onMenuOpened(108, panelState.menu);
                ActionBarOverlayLayout actionBarOverlayLayout5 = (ActionBarOverlayLayout) this.mDecorContentParent;
                actionBarOverlayLayout5.pullChildren();
                actionBarOverlayLayout5.mDecorToolbar.mToolbar.showOverflowMenu();
                return;
            }
        }
        PanelFeatureState panelState2 = getPanelState(0);
        panelState2.refreshDecorView = true;
        closePanel(panelState2, false);
        openPanel(panelState2, null);
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

    /* JADX WARN: Code restructure failed: missing block: B:91:0x017f, code lost:
    
        if (r15.mAdapter.getCount() > 0) goto L92;
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i;
        ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.isOpen || this.mDestroyed) {
            return;
        }
        int i2 = panelFeatureState.featureId;
        if (i2 == 0 && (this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
            return;
        }
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !callback.onMenuOpened(i2, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return;
        }
        ListMenuDecorView listMenuDecorView = panelFeatureState.decorView;
        if (listMenuDecorView != null && !panelFeatureState.refreshDecorView) {
            View view = panelFeatureState.createdPanelView;
            if (view != null && (layoutParams = view.getLayoutParams()) != null && layoutParams.width == -1) {
                i = -1;
            }
            panelFeatureState.isHandled = false;
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
            layoutParams2.gravity = panelFeatureState.gravity;
            layoutParams2.windowAnimations = panelFeatureState.windowAnimations;
            windowManager.addView(panelFeatureState.decorView, layoutParams2);
            panelFeatureState.isOpen = true;
            if (i2 != 0) {
                updateBackInvokedCallbackState();
                return;
            }
            return;
        }
        ExpandedMenuView expandedMenuView = null;
        if (listMenuDecorView == null) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            Context themedContext = actionBar != null ? actionBar.getThemedContext() : null;
            if (themedContext == null) {
                themedContext = this.mContext;
            }
            TypedValue typedValue = new TypedValue();
            Resources.Theme themeNewTheme = themedContext.getResources().newTheme();
            themeNewTheme.setTo(themedContext.getTheme());
            themeNewTheme.resolveAttribute(com.android.systemui.R.attr.actionBarPopupTheme, typedValue, true);
            int i3 = typedValue.resourceId;
            if (i3 != 0) {
                themeNewTheme.applyStyle(i3, true);
            }
            themeNewTheme.resolveAttribute(com.android.systemui.R.attr.panelMenuListTheme, typedValue, true);
            int i4 = typedValue.resourceId;
            if (i4 != 0) {
                themeNewTheme.applyStyle(i4, true);
            } else {
                themeNewTheme.applyStyle(2132018762, true);
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(themedContext, 0);
            contextThemeWrapper.getTheme().setTo(themeNewTheme);
            panelFeatureState.listPresenterContext = contextThemeWrapper;
            TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R$styleable.AppCompatTheme);
            panelFeatureState.background = typedArrayObtainStyledAttributes.getResourceId(102, 0);
            panelFeatureState.windowAnimations = typedArrayObtainStyledAttributes.getResourceId(8, 0);
            typedArrayObtainStyledAttributes.recycle();
            panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
            panelFeatureState.gravity = 81;
        } else if (panelFeatureState.refreshDecorView && listMenuDecorView.getChildCount() > 0) {
            panelFeatureState.decorView.removeAllViews();
        }
        View view2 = panelFeatureState.createdPanelView;
        if (view2 == null) {
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
                if (expandedMenuView != null) {
                }
            }
            panelFeatureState.refreshDecorView = true;
            return;
        }
        panelFeatureState.shownPanelView = view2;
        if (panelFeatureState.shownPanelView != null) {
            if (panelFeatureState.createdPanelView == null) {
                ListMenuPresenter listMenuPresenter3 = panelFeatureState.listMenuPresenter;
                if (listMenuPresenter3.mAdapter == null) {
                    listMenuPresenter3.mAdapter = listMenuPresenter3.new MenuAdapter();
                }
            }
            ViewGroup.LayoutParams layoutParams3 = panelFeatureState.shownPanelView.getLayoutParams();
            if (layoutParams3 == null) {
                layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
            }
            panelFeatureState.decorView.setBackgroundResource(panelFeatureState.background);
            ViewParent parent = panelFeatureState.shownPanelView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(panelFeatureState.shownPanelView);
            }
            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, layoutParams3);
            if (!panelFeatureState.shownPanelView.hasFocus()) {
                panelFeatureState.shownPanelView.requestFocus();
            }
        }
        panelFeatureState.refreshDecorView = true;
        return;
        i = -2;
        panelFeatureState.isHandled = false;
        WindowManager.LayoutParams layoutParams22 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
        layoutParams22.gravity = panelFeatureState.gravity;
        layoutParams22.windowAnimations = panelFeatureState.windowAnimations;
        windowManager.addView(panelFeatureState.decorView, layoutParams22);
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

    /* JADX WARN: Removed duplicated region for block: B:62:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        Resources.Theme themeNewTheme;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (!this.mDestroyed) {
            if (panelFeatureState.isPrepared) {
                return true;
            }
            PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
            if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
                closePanel(panelFeatureState2, false);
            }
            Window.Callback callback = this.mWindow.getCallback();
            int i = panelFeatureState.featureId;
            if (callback != null) {
                panelFeatureState.createdPanelView = callback.onCreatePanelView(i);
            }
            boolean z = i == 0 || i == 108;
            if (z && (decorContentParent3 = this.mDecorContentParent) != null) {
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent3;
                actionBarOverlayLayout.pullChildren();
                actionBarOverlayLayout.mDecorToolbar.mMenuPrepared = true;
            }
            if (panelFeatureState.createdPanelView == null && (!z || !(this.mActionBar instanceof ToolbarActionBar))) {
                MenuBuilder menuBuilder = panelFeatureState.menu;
                if (menuBuilder == null || panelFeatureState.refreshMenuContent) {
                    if (menuBuilder == null) {
                        Context context = this.mContext;
                        if ((i == 0 || i == 108) && this.mDecorContentParent != null) {
                            TypedValue typedValue = new TypedValue();
                            Resources.Theme theme = context.getTheme();
                            theme.resolveAttribute(com.android.systemui.R.attr.actionBarTheme, typedValue, true);
                            if (typedValue.resourceId != 0) {
                                themeNewTheme = context.getResources().newTheme();
                                themeNewTheme.setTo(theme);
                                themeNewTheme.applyStyle(typedValue.resourceId, true);
                                themeNewTheme.resolveAttribute(com.android.systemui.R.attr.actionBarWidgetTheme, typedValue, true);
                            } else {
                                theme.resolveAttribute(com.android.systemui.R.attr.actionBarWidgetTheme, typedValue, true);
                                themeNewTheme = null;
                            }
                            if (typedValue.resourceId != 0) {
                                if (themeNewTheme == null) {
                                    themeNewTheme = context.getResources().newTheme();
                                    themeNewTheme.setTo(theme);
                                }
                                themeNewTheme.applyStyle(typedValue.resourceId, true);
                            }
                            if (themeNewTheme != null) {
                                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                                contextThemeWrapper.getTheme().setTo(themeNewTheme);
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
                        if (panelFeatureState.menu != null) {
                            if (z && this.mDecorContentParent != null) {
                                if (this.mActionMenuPresenterCallback == null) {
                                    this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                                }
                                ((ActionBarOverlayLayout) this.mDecorContentParent).setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                            }
                            panelFeatureState.menu.stopDispatchingItemsChanged();
                            if (callback.onCreatePanelMenu(i, panelFeatureState.menu)) {
                                panelFeatureState.refreshMenuContent = false;
                            } else {
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
                            }
                        }
                    }
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                Bundle bundle = panelFeatureState.frozenActionViewState;
                if (bundle != null) {
                    panelFeatureState.menu.restoreActionViewStates(bundle);
                    panelFeatureState.frozenActionViewState = null;
                }
                if (!callback.onPreparePanel(0, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
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
        return false;
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
                applyApplicationSpecificConfig(true);
            }
        }
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
        ToolbarWidgetWrapper toolbarWidgetWrapper = actionBarOverlayLayout.mDecorToolbar;
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
    /* JADX WARN: Type inference failed for: r1v3, types: [android.window.OnBackInvokedCallback, androidx.appcompat.app.AppCompatDelegateImpl$Api33Impl$$ExternalSyntheticLambda0] */
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
                    this.f$0.onBackPressed();
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
        this.mInvalidatePanelMenuRunnable = new AnonymousClass2();
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
