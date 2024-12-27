package com.android.internal.policy;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.SearchManager;
import android.app.WindowConfiguration;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.Intent;
import android.content.om.WallpaperThemeUtils;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AndroidRuntimeException;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.AttachedSurfaceControl;
import android.view.ContextThemeWrapper;
import android.view.CrossWindowBlurListeners;
import android.view.IRotationWatcher;
import android.view.IScrollCaptureResponseListener;
import android.view.IWindowManager;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputQueue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScrollCaptureCallback;
import android.view.SearchEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;
import android.window.ProxyOnBackInvokedDispatcher;
import android.window.TaskConstants;

import com.android.internal.R;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.IconMenuPresenter;
import com.android.internal.view.menu.ListMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.widget.DecorContentParent;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PhoneWindow extends Window implements MenuBuilder.Callback {
    private static final String ACTION_BAR_TAG = "android:ActionBar";
    private static final int CUSTOM_TITLE_COMPATIBLE_FEATURES = 13505;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKGROUND_FADE_DURATION_MS = 300;
    private static final long ENFORCE_EDGE_TO_EDGE = 309578419;
    static final int FLAG_RESOURCE_SET_ICON = 1;
    static final int FLAG_RESOURCE_SET_ICON_FALLBACK = 4;
    static final int FLAG_RESOURCE_SET_LOGO = 2;
    private static final String FOCUSED_ID_TAG = "android:focusedViewId";
    private static final long OVERRIDE_LAYOUT_IN_DISPLAY_CUTOUT_MODE = 332679525;
    private static final String PANELS_TAG = "android:Panels";
    private static final String TAG = "PhoneWindow";
    private static final String VIEWS_TAG = "android:views";
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    private ViewRootImpl.ActivityConfigCallback mActivityConfigCallback;
    Configuration mActivityCurrentConfig;
    private Boolean mAllowEnterTransitionOverlap;
    private Boolean mAllowFloatingWindowsFillScreen;
    private Boolean mAllowReturnTransitionOverlap;
    private boolean mAlwaysReadCloseOnTouchAttr;
    private AudioManager mAudioManager;
    private int mAudioMode;
    private int mBackgroundBlurRadius;
    Drawable mBackgroundDrawable;
    private long mBackgroundFadeDurationMillis;
    Drawable mBackgroundFallbackDrawable;
    private ProgressBar mCircularProgressBar;
    private boolean mClipToOutline;
    private boolean mClosingActionMenu;
    ViewGroup mContentParent;
    private boolean mContentParentExplicitlySet;
    private Scene mContentScene;
    ContextMenuBuilder mContextMenu;
    final PhoneWindowMenuCallback mContextMenuCallback;
    MenuHelper mContextMenuHelper;
    private DecorView mDecor;
    DecorContentParent mDecorContentParent;
    boolean mDecorFitsSystemWindows;
    private int mDefaultNavigationBarColor;
    private int mDeviceDefaultNavigationBarColor;
    private DrawableFeatureState[] mDrawables;
    boolean mEdgeToEdgeEnforced;
    private float mElevation;
    boolean mEnsureNavigationBarContrastWhenTransparent;
    boolean mEnsureStatusBarContrastWhenTransparent;
    private Transition mEnterTransition;
    private Transition mExitTransition;
    TypedValue mFixedHeightMajor;
    TypedValue mFixedHeightMinor;
    TypedValue mFixedWidthMajor;
    TypedValue mFixedWidthMinor;
    private boolean mForceDecorInstall;
    private boolean mForcedNavigationBarColor;
    private boolean mForcedStatusBarColor;
    private int mFrameResource;
    private ProgressBar mHorizontalProgressBar;
    int mIconRes;
    private int mInvalidatePanelMenuFeatures;
    private boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable;
    boolean mIsFloating;
    boolean mIsStartingWindow;
    private boolean mIsTranslucent;
    private KeyguardManager mKeyguardManager;
    private LayoutInflater mLayoutInflater;
    private ImageView mLeftIconView;
    private boolean mLoadElevation;
    int mLogoRes;
    private MediaController mMediaController;
    private MediaSessionManager mMediaSessionManager;
    final TypedValue mMinWidthMajor;
    final TypedValue mMinWidthMinor;
    int mNavigationBarColor;
    boolean mNavigationBarColorSpecified;
    int mNavigationBarDividerColor;
    private AudioManager.OnModeChangedListener mOnModeChangedListener;
    int mPanelChordingKey;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    PanelFeatureState mPreparedPanel;
    private final ProxyOnBackInvokedDispatcher mProxyOnBackInvokedDispatcher;
    private Transition mReenterTransition;
    public final boolean mRenderShadowsInCompositor;
    int mResourcesSetFlags;
    private Transition mReturnTransition;
    private ImageView mRightIconView;
    private int mSettingsNavigationBarColor;
    private Transition mSharedElementEnterTransition;
    private Transition mSharedElementExitTransition;
    private Transition mSharedElementReenterTransition;
    private Transition mSharedElementReturnTransition;
    private Boolean mSharedElementsUseOverlay;
    int mStatusBarColor;
    private boolean mSupportsPictureInPicture;
    InputQueue.Callback mTakeInputQueueCallback;
    SurfaceHolder.Callback2 mTakeSurfaceCallback;
    private int mTextColor;
    private int mTheme;
    private boolean mThemeApplied;
    private CharSequence mTitle;
    private int mTitleColor;
    private TextView mTitleView;
    private TransitionManager mTransitionManager;
    private int mUiOptions;
    private boolean mUseDecorContext;
    private int mVolumeControlStreamType;
    private static final Window.OnContentApplyWindowInsetsListener sDefaultContentInsetsApplier =
            new Window
                    .OnContentApplyWindowInsetsListener() { // from class:
                                                            // com.android.internal.policy.PhoneWindow$$ExternalSyntheticLambda1
                @Override // android.view.Window.OnContentApplyWindowInsetsListener
                public final Pair onContentApplyWindowInsets(View view, WindowInsets windowInsets) {
                    return PhoneWindow.lambda$static$0(view, windowInsets);
                }
            };
    private static final Transition USE_DEFAULT_TRANSITION = new TransitionSet();
    private static final int ENFORCE_EDGE_TO_EDGE_SDK_VERSION =
            SystemProperties.getInt("persist.wm.debug.default_e2e_since_sdk", Integer.MAX_VALUE);
    static final RotationWatcher sRotationWatcher = new RotationWatcher();

    static /* synthetic */ Pair lambda$static$0(View view, WindowInsets insets) {
        if ((view.getWindowSystemUiVisibility() & 1536) != 0) {
            return new Pair(Insets.NONE, insets);
        }
        Insets insetsToApply = insets.getSystemWindowInsets();
        return new Pair(insetsToApply, insets.inset(insetsToApply).consumeSystemWindowInsets());
    }

    static class WindowManagerHolder {
        static final IWindowManager sWindowManager =
                IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));

        WindowManagerHolder() {}
    }

    public PhoneWindow(Context context) {
        super(context);
        this.mContextMenuCallback = new PhoneWindowMenuCallback(this);
        this.mMinWidthMajor = new TypedValue();
        this.mMinWidthMinor = new TypedValue();
        this.mForceDecorInstall = false;
        this.mContentParentExplicitlySet = false;
        this.mBackgroundDrawable = null;
        this.mBackgroundFallbackDrawable = null;
        this.mBackgroundBlurRadius = 0;
        this.mLoadElevation = true;
        this.mFrameResource = 0;
        this.mTextColor = 0;
        this.mStatusBarColor = 0;
        this.mNavigationBarColor = 0;
        this.mNavigationBarDividerColor = 0;
        this.mNavigationBarColorSpecified = false;
        this.mForcedStatusBarColor = false;
        this.mForcedNavigationBarColor = false;
        this.mTitle = null;
        this.mTitleColor = 0;
        this.mAlwaysReadCloseOnTouchAttr = false;
        this.mVolumeControlStreamType = Integer.MIN_VALUE;
        this.mAudioMode = 0;
        this.mUiOptions = 0;
        this.mInvalidatePanelMenuRunnable =
                new Runnable() { // from class: com.android.internal.policy.PhoneWindow.1
                    @Override // java.lang.Runnable
                    public void run() {
                        for (int i = 0; i <= 13; i++) {
                            if ((PhoneWindow.this.mInvalidatePanelMenuFeatures & (1 << i)) != 0) {
                                PhoneWindow.this.doInvalidatePanelMenu(i);
                            }
                        }
                        PhoneWindow.this.mInvalidatePanelMenuPosted = false;
                        PhoneWindow.this.mInvalidatePanelMenuFeatures = 0;
                    }
                };
        this.mEnterTransition = null;
        this.mReturnTransition = USE_DEFAULT_TRANSITION;
        this.mExitTransition = null;
        this.mReenterTransition = USE_DEFAULT_TRANSITION;
        this.mSharedElementEnterTransition = null;
        this.mSharedElementReturnTransition = USE_DEFAULT_TRANSITION;
        this.mSharedElementExitTransition = null;
        this.mSharedElementReenterTransition = USE_DEFAULT_TRANSITION;
        this.mBackgroundFadeDurationMillis = -1L;
        this.mTheme = -1;
        this.mUseDecorContext = false;
        this.mDecorFitsSystemWindows = true;
        this.mActivityCurrentConfig = null;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRenderShadowsInCompositor =
                Settings.Global.getInt(
                                context.getContentResolver(),
                                Settings.Global.DEVELOPMENT_RENDER_SHADOWS_IN_COMPOSITOR,
                                1)
                        != 0;
        this.mProxyOnBackInvokedDispatcher = new ProxyOnBackInvokedDispatcher(context);
        this.mAllowFloatingWindowsFillScreen =
                Boolean.valueOf(
                        context.getResources()
                                .getBoolean(R.bool.config_allowFloatingWindowsFillScreen));
        updateDeviceDefaultNavigationBarColor();
        this.mDefaultNavigationBarColor = this.mDeviceDefaultNavigationBarColor;
        this.mSettingsNavigationBarColor = this.mDeviceDefaultNavigationBarColor;
    }

    public PhoneWindow(
            Context context,
            Window preservedWindow,
            ViewRootImpl.ActivityConfigCallback activityConfigCallback) {
        this(context);
        boolean forceResizable;
        boolean z = true;
        this.mUseDecorContext = true;
        if (preservedWindow != null) {
            this.mDecor = (DecorView) preservedWindow.getDecorView();
            this.mElevation = preservedWindow.getElevation();
            this.mLoadElevation = false;
            this.mForceDecorInstall = true;
            setSystemBarAppearance(preservedWindow.getSystemBarAppearance());
            getAttributes().token = preservedWindow.getAttributes().token;
            ViewRootImpl viewRoot = this.mDecor.getViewRootImpl();
            if (viewRoot != null) {
                viewRoot.getOnBackInvokedDispatcher().clear();
                onViewRootImplSet(viewRoot);
            }
            setPreserved(true);
        }
        if (Settings.Global.getInt(
                        context.getContentResolver(),
                        Settings.Global.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES,
                        0)
                != 0) {
            forceResizable = true;
        } else {
            forceResizable = false;
        }
        if (!forceResizable
                && !context.getPackageManager()
                        .hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            z = false;
        }
        this.mSupportsPictureInPicture = z;
        this.mActivityConfigCallback = activityConfigCallback;
    }

    public static boolean isEdgeToEdgeEnforced(
            ApplicationInfo info, boolean local, TypedArray windowStyle) {
        if (windowStyle.getBoolean(63, false)) {
            return false;
        }
        if (info.targetSdkVersion < ENFORCE_EDGE_TO_EDGE_SDK_VERSION) {
            if (!Flags.enforceEdgeToEdge()) {
                return false;
            }
            if (local) {
                if (!CompatChanges.isChangeEnabled(ENFORCE_EDGE_TO_EDGE)) {
                    return false;
                }
            } else if (!info.isChangeEnabled(ENFORCE_EDGE_TO_EDGE)) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.Window
    public final void setContainer(Window container) {
        super.setContainer(container);
    }

    @Override // android.view.Window
    public boolean requestFeature(int featureId) {
        if (this.mContentParentExplicitlySet) {
            throw new AndroidRuntimeException(
                    "requestFeature() must be called before adding content");
        }
        int features = getFeatures();
        int newFeatures = (1 << featureId) | features;
        if ((newFeatures & 128) != 0 && (newFeatures & (-13506)) != 0) {
            throw new AndroidRuntimeException(
                    "You cannot combine custom titles with other title features");
        }
        if ((features & 2) != 0 && featureId == 8) {
            return false;
        }
        if ((features & 256) != 0 && featureId == 1) {
            removeFeature(8);
        }
        if (featureId == 5
                && getContext()
                        .getPackageManager()
                        .hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            throw new AndroidRuntimeException("You cannot use indeterminate progress on a watch.");
        }
        return super.requestFeature(featureId);
    }

    @Override // android.view.Window
    public void setUiOptions(int uiOptions) {
        this.mUiOptions = uiOptions;
    }

    @Override // android.view.Window
    public void setUiOptions(int uiOptions, int mask) {
        this.mUiOptions = (this.mUiOptions & (~mask)) | (uiOptions & mask);
    }

    @Override // android.view.Window
    public TransitionManager getTransitionManager() {
        return this.mTransitionManager;
    }

    @Override // android.view.Window
    public void setTransitionManager(TransitionManager tm) {
        this.mTransitionManager = tm;
    }

    @Override // android.view.Window
    public Scene getContentScene() {
        return this.mContentScene;
    }

    @Override // android.view.Window
    public void setContentView(int layoutResID) {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            Scene newScene =
                    Scene.getSceneForLayout(this.mContentParent, layoutResID, getContext());
            transitionTo(newScene);
        } else {
            this.mLayoutInflater.inflate(layoutResID, this.mContentParent);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback cb = getCallback();
        if (!isDestroyed()) {
            if (cb != null) {
                cb.onContentChanged();
            }
            if (this.mDecorContentParent != null) {
                this.mDecorContentParent.notifyContentChanged();
            }
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override // android.view.Window
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // android.view.Window
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            view.setLayoutParams(params);
            Scene newScene = new Scene(this.mContentParent, view);
            transitionTo(newScene);
        } else {
            this.mContentParent.addView(view, params);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback cb = getCallback();
        if (!isDestroyed()) {
            if (cb != null) {
                cb.onContentChanged();
            }
            if (this.mDecorContentParent != null) {
                this.mDecorContentParent.notifyContentChanged();
            }
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override // android.view.Window
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (this.mContentParent == null) {
            installDecor();
        }
        if (hasFeature(12)) {
            Log.v(TAG, "addContentView does not support content transitions");
        }
        this.mContentParent.addView(view, params);
        this.mContentParent.requestApplyInsets();
        Window.Callback cb = getCallback();
        if (!isDestroyed()) {
            if (cb != null) {
                cb.onContentChanged();
            }
            if (this.mDecorContentParent != null) {
                this.mDecorContentParent.notifyContentChanged();
            }
        }
    }

    @Override // android.view.Window
    public void clearContentView() {
        if (this.mDecor != null) {
            this.mDecor.clearContentView();
        }
    }

    private void transitionTo(Scene scene) {
        if (this.mContentScene == null) {
            scene.enter();
        } else {
            this.mTransitionManager.transitionTo(scene);
        }
        this.mContentScene = scene;
    }

    @Override // android.view.Window
    public View getCurrentFocus() {
        if (this.mDecor != null) {
            return this.mDecor.findFocus();
        }
        return null;
    }

    @Override // android.view.Window
    public void takeSurface(SurfaceHolder.Callback2 callback) {
        this.mTakeSurfaceCallback = callback;
    }

    @Override // android.view.Window
    public void takeInputQueue(InputQueue.Callback callback) {
        this.mTakeInputQueueCallback = callback;
    }

    @Override // android.view.Window
    public boolean isFloating() {
        return this.mIsFloating;
    }

    public boolean isTranslucent() {
        return this.mIsTranslucent;
    }

    boolean isShowingWallpaper() {
        return (getAttributes().flags & 1048576) != 0;
    }

    @Override // android.view.Window
    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    @Override // android.view.Window
    public void setTitle(CharSequence title) {
        setTitle(title, true);
    }

    public void setTitle(CharSequence title, boolean updateAccessibilityTitle) {
        ViewRootImpl vr;
        if (this.mTitleView != null) {
            this.mTitleView.lambda$setTextAsync$0(title);
        } else if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(title);
        }
        this.mTitle = title;
        if (updateAccessibilityTitle) {
            WindowManager.LayoutParams params = getAttributes();
            if (!TextUtils.equals(title, params.accessibilityTitle)) {
                params.accessibilityTitle = TextUtils.stringOrSpannedString(title);
                if (this.mDecor != null && (vr = this.mDecor.getViewRootImpl()) != null) {
                    vr.onWindowTitleChanged();
                }
                dispatchWindowAttributesChanged(getAttributes());
            }
        }
    }

    @Override // android.view.Window
    @Deprecated
    public void setTitleColor(int textColor) {
        if (this.mTitleView != null) {
            this.mTitleView.setTextColor(textColor);
        }
        this.mTitleColor = textColor;
    }

    public final boolean preparePanel(PanelFeatureState st, KeyEvent event) {
        if (isDestroyed()) {
            return false;
        }
        if (st.isPrepared) {
            return true;
        }
        if (this.mPreparedPanel != null && this.mPreparedPanel != st) {
            closePanel(this.mPreparedPanel, false);
        }
        Window.Callback cb = getCallback();
        if (cb != null) {
            st.createdPanelView = cb.onCreatePanelView(st.featureId);
        }
        boolean isActionBarMenu = st.featureId == 0 || st.featureId == 8;
        if (isActionBarMenu && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if (st.createdPanelView == null) {
            if (st.menu == null || st.refreshMenuContent) {
                if (st.menu == null && (!initializePanelMenu(st) || st.menu == null)) {
                    return false;
                }
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(st.menu, this.mActionMenuPresenterCallback);
                }
                st.menu.stopDispatchingItemsChanged();
                if (cb == null || !cb.onCreatePanelMenu(st.featureId, st.menu)) {
                    st.setMenu(null);
                    if (isActionBarMenu && this.mDecorContentParent != null) {
                        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                st.refreshMenuContent = false;
            }
            st.menu.stopDispatchingItemsChanged();
            if (st.frozenActionViewState != null) {
                st.menu.restoreActionViewStates(st.frozenActionViewState);
                st.frozenActionViewState = null;
            }
            if (!cb.onPreparePanel(st.featureId, st.createdPanelView, st.menu)) {
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                st.menu.startDispatchingItemsChanged();
                return false;
            }
            KeyCharacterMap kmap = KeyCharacterMap.load(event != null ? event.getDeviceId() : -1);
            st.qwertyMode = kmap.getKeyboardType() != 1;
            st.menu.setQwertyMode(st.qwertyMode);
            st.menu.startDispatchingItemsChanged();
        }
        st.isPrepared = true;
        st.isHandled = false;
        this.mPreparedPanel = st;
        return true;
    }

    @Override // android.view.Window
    public void onConfigurationChanged(Configuration newConfig) {
        PanelFeatureState st;
        if (this.mDecorContentParent == null
                && (st = getPanelState(0, false)) != null
                && st.menu != null) {
            if (st.isOpen) {
                Bundle state = new Bundle();
                if (st.iconMenuPresenter != null) {
                    st.iconMenuPresenter.saveHierarchyState(state);
                }
                if (st.listMenuPresenter != null) {
                    st.listMenuPresenter.saveHierarchyState(state);
                }
                clearMenuViews(st);
                reopenMenu(false);
                if (st.iconMenuPresenter != null) {
                    st.iconMenuPresenter.restoreHierarchyState(state);
                }
                if (st.listMenuPresenter != null) {
                    st.listMenuPresenter.restoreHierarchyState(state);
                    return;
                }
                return;
            }
            clearMenuViews(st);
        }
    }

    @Override // android.view.Window
    public void onMultiWindowModeChanged() {
        if (this.mDecor != null) {
            this.mDecor.onConfigurationChanged(getContext().getResources().getConfiguration());
        }
    }

    @Override // android.view.Window
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        if (this.mDecor != null) {
            this.mDecor.updatePictureInPictureOutlineProvider(isInPictureInPictureMode);
        }
    }

    private static void clearMenuViews(PanelFeatureState st) {
        st.createdPanelView = null;
        st.refreshDecorView = true;
        st.clearMenuPresenters();
    }

    @Override // android.view.Window
    public final void openPanel(int featureId, KeyEvent event) {
        if (featureId == 0
                && this.mDecorContentParent != null
                && this.mDecorContentParent.canShowOverflowMenu()
                && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.showOverflowMenu();
        } else {
            openPanel(getPanelState(featureId, true), event);
        }
    }

    private void openPanel(PanelFeatureState st, KeyEvent event) {
        int backgroundResId;
        ViewGroup.LayoutParams lp;
        if (st.isOpen || isDestroyed()) {
            return;
        }
        if (st.featureId == 0) {
            Context context = getContext();
            Configuration config = context.getResources().getConfiguration();
            boolean isXLarge = (config.screenLayout & 15) == 4;
            boolean isHoneycombApp = context.getApplicationInfo().targetSdkVersion >= 11;
            if (isXLarge && isHoneycombApp) {
                return;
            }
        }
        Window.Callback cb = getCallback();
        if (cb != null && !cb.onMenuOpened(st.featureId, st.menu)) {
            closePanel(st, true);
            return;
        }
        WindowManager wm = getWindowManager();
        if (wm == null || !preparePanel(st, event)) {
            return;
        }
        int width = -2;
        if (st.decorView == null || st.refreshDecorView) {
            if (st.decorView == null) {
                if (!initializePanelDecor(st) || st.decorView == null) {
                    return;
                }
            } else if (st.refreshDecorView && st.decorView.getChildCount() > 0) {
                st.decorView.removeAllViews();
            }
            if (!initializePanelContent(st) || !st.hasPanelItems()) {
                return;
            }
            ViewGroup.LayoutParams lp2 = st.shownPanelView.getLayoutParams();
            if (lp2 == null) {
                lp2 = new ViewGroup.LayoutParams(-2, -2);
            }
            if (lp2.width == -1) {
                backgroundResId = st.fullBackground;
                width = -1;
            } else {
                backgroundResId = st.background;
            }
            st.decorView.setWindowBackground(getContext().getDrawable(backgroundResId));
            ViewParent shownPanelParent = st.shownPanelView.getParent();
            if (shownPanelParent != null && (shownPanelParent instanceof ViewGroup)) {
                ((ViewGroup) shownPanelParent).removeView(st.shownPanelView);
            }
            st.decorView.addView(st.shownPanelView, lp2);
            if (!st.shownPanelView.hasFocus()) {
                st.shownPanelView.requestFocus();
            }
        } else if (!st.isInListMode()) {
            width = -1;
        } else if (st.createdPanelView != null
                && (lp = st.createdPanelView.getLayoutParams()) != null
                && lp.width == -1) {
            width = -1;
        }
        if (!st.hasPanelItems()) {
            return;
        }
        st.isHandled = false;
        WindowManager.LayoutParams lp3 =
                new WindowManager.LayoutParams(
                        width, -2, st.x, st.y, 1003, 8519680, st.decorView.mDefaultOpacity);
        if (st.isCompact) {
            lp3.gravity = getOptionsPanelGravity();
            sRotationWatcher.addWindow(this);
        } else {
            lp3.gravity = st.gravity;
        }
        lp3.windowAnimations = st.windowAnimations;
        wm.addView(st.decorView, lp3);
        st.isOpen = true;
    }

    @Override // android.view.Window
    public final void closePanel(int featureId) {
        if (featureId == 0
                && this.mDecorContentParent != null
                && this.mDecorContentParent.canShowOverflowMenu()
                && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.hideOverflowMenu();
        } else if (featureId == 6) {
            closeContextMenu();
        } else {
            closePanel(getPanelState(featureId, true), true);
        }
    }

    public final void closePanel(PanelFeatureState st, boolean doCallback) {
        if (doCallback
                && st.featureId == 0
                && this.mDecorContentParent != null
                && this.mDecorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(st.menu);
            return;
        }
        ViewManager wm = getWindowManager();
        if (wm != null && st.isOpen) {
            if (st.decorView != null) {
                wm.removeView(st.decorView);
                if (st.isCompact) {
                    sRotationWatcher.removeWindow(this);
                }
            }
            if (doCallback) {
                callOnPanelClosed(st.featureId, st, null);
            }
        }
        st.isPrepared = false;
        st.isHandled = false;
        st.isOpen = false;
        st.shownPanelView = null;
        if (st.isInExpandedMode) {
            st.refreshDecorView = true;
            st.isInExpandedMode = false;
        }
        if (this.mPreparedPanel == st) {
            this.mPreparedPanel = null;
            this.mPanelChordingKey = 0;
        }
    }

    void checkCloseActionMenu(Menu menu) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onPanelClosed(8, menu);
        }
        this.mClosingActionMenu = false;
    }

    @Override // android.view.Window
    public final void togglePanel(int featureId, KeyEvent event) {
        PanelFeatureState st = getPanelState(featureId, true);
        if (st.isOpen) {
            closePanel(st, true);
        } else {
            openPanel(st, event);
        }
    }

    @Override // android.view.Window
    public void invalidatePanelMenu(int featureId) {
        this.mInvalidatePanelMenuFeatures |= 1 << featureId;
        if (!this.mInvalidatePanelMenuPosted && this.mDecor != null) {
            this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    void doPendingInvalidatePanelMenu() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
    }

    void doInvalidatePanelMenu(int featureId) {
        PanelFeatureState st;
        PanelFeatureState st2 = getPanelState(featureId, false);
        if (st2 == null) {
            return;
        }
        if (st2.menu != null) {
            Bundle savedActionViewStates = new Bundle();
            st2.menu.saveActionViewStates(savedActionViewStates);
            if (savedActionViewStates.size() > 0) {
                st2.frozenActionViewState = savedActionViewStates;
            }
            st2.menu.stopDispatchingItemsChanged();
            st2.menu.clear();
        }
        st2.refreshMenuContent = true;
        st2.refreshDecorView = true;
        if ((featureId == 8 || featureId == 0)
                && this.mDecorContentParent != null
                && (st = getPanelState(0, false)) != null) {
            st.isPrepared = false;
            preparePanel(st, null);
        }
    }

    public final boolean onKeyDownPanel(int featureId, KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (event.getRepeatCount() == 0) {
            this.mPanelChordingKey = keyCode;
            PanelFeatureState st = getPanelState(featureId, false);
            if (st != null && !st.isOpen) {
                return preparePanel(st, event);
            }
        }
        return false;
    }

    public final void onKeyUpPanel(int featureId, KeyEvent event) {
        if (this.mPanelChordingKey != 0) {
            this.mPanelChordingKey = 0;
            PanelFeatureState st = getPanelState(featureId, false);
            if (event.isCanceled()) {
                return;
            }
            if ((this.mDecor != null && this.mDecor.mPrimaryActionMode != null) || st == null) {
                return;
            }
            boolean playSoundEffect = false;
            if (featureId == 0
                    && this.mDecorContentParent != null
                    && this.mDecorContentParent.canShowOverflowMenu()
                    && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
                if (!this.mDecorContentParent.isOverflowMenuShowing()) {
                    if (!isDestroyed() && preparePanel(st, event)) {
                        playSoundEffect = this.mDecorContentParent.showOverflowMenu();
                    }
                } else {
                    playSoundEffect = this.mDecorContentParent.hideOverflowMenu();
                }
            } else if (st.isOpen || st.isHandled) {
                playSoundEffect = st.isOpen;
                closePanel(st, true);
            } else if (st.isPrepared) {
                boolean show = true;
                if (st.refreshMenuContent) {
                    st.isPrepared = false;
                    show = preparePanel(st, event);
                }
                if (show) {
                    EventLog.writeEvent(50001, 0);
                    openPanel(st, event);
                    playSoundEffect = true;
                }
            }
            if (playSoundEffect) {
                AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
                if (audioManager != null) {
                    audioManager.playSoundEffect(0);
                } else {
                    Log.w(TAG, "Couldn't get audio manager");
                }
            }
        }
    }

    @Override // android.view.Window
    public final void closeAllPanels() {
        ViewManager wm = getWindowManager();
        if (wm == null) {
            return;
        }
        PanelFeatureState[] panels = this.mPanels;
        int N = panels != null ? panels.length : 0;
        for (int i = 0; i < N; i++) {
            PanelFeatureState panel = panels[i];
            if (panel != null) {
                closePanel(panel, true);
            }
        }
        closeContextMenu();
    }

    private synchronized void closeContextMenu() {
        if (this.mContextMenu != null) {
            this.mContextMenu.close();
            dismissContextMenu();
        }
    }

    public synchronized void dismissContextMenu() {
        this.mContextMenu = null;
        if (this.mContextMenuHelper != null) {
            this.mContextMenuHelper.dismiss();
            this.mContextMenuHelper = null;
        }
    }

    @Override // android.view.Window
    public boolean performPanelShortcut(int featureId, int keyCode, KeyEvent event, int flags) {
        return performPanelShortcut(getPanelState(featureId, false), keyCode, event, flags);
    }

    boolean performPanelShortcut(PanelFeatureState st, int keyCode, KeyEvent event, int flags) {
        if (event.isSystem() || st == null) {
            return false;
        }
        boolean handled = false;
        if ((st.isPrepared || preparePanel(st, event)) && st.menu != null) {
            handled = st.menu.performShortcut(keyCode, event, flags);
        }
        if (handled) {
            st.isHandled = true;
            if ((flags & 1) == 0 && this.mDecorContentParent == null) {
                closePanel(st, true);
            }
        }
        return handled;
    }

    @Override // android.view.Window
    public boolean performPanelIdentifierAction(int featureId, int id, int flags) {
        PanelFeatureState st = getPanelState(featureId, true);
        if (!preparePanel(st, new KeyEvent(0, 82)) || st.menu == null) {
            return false;
        }
        boolean res = st.menu.performIdentifierAction(id, flags);
        if (this.mDecorContentParent == null) {
            closePanel(st, true);
        }
        return res;
    }

    public PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panels = this.mPanels;
        int N = panels != null ? panels.length : 0;
        for (int i = 0; i < N; i++) {
            PanelFeatureState panel = panels[i];
            if (panel != null && panel.menu == menu) {
                return panel;
            }
        }
        return null;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
        PanelFeatureState panel;
        Window.Callback cb = getCallback();
        if (cb != null && !isDestroyed() && (panel = findMenuPanel(menu.getRootMenu())) != null) {
            return cb.onMenuItemSelected(panel.featureId, item);
        }
        return false;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menu) {
        reopenMenu(true);
    }

    private void reopenMenu(boolean toggleMenuMode) {
        if (this.mDecorContentParent != null
                && this.mDecorContentParent.canShowOverflowMenu()
                && (!ViewConfiguration.get(getContext()).hasPermanentMenuKey()
                        || this.mDecorContentParent.isOverflowMenuShowPending())) {
            Window.Callback cb = getCallback();
            if (!this.mDecorContentParent.isOverflowMenuShowing() || !toggleMenuMode) {
                if (cb != null && !isDestroyed()) {
                    if (this.mInvalidatePanelMenuPosted
                            && (1 & this.mInvalidatePanelMenuFeatures) != 0) {
                        this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
                        this.mInvalidatePanelMenuRunnable.run();
                    }
                    PanelFeatureState st = getPanelState(0, false);
                    if (st != null
                            && st.menu != null
                            && !st.refreshMenuContent
                            && cb.onPreparePanel(0, st.createdPanelView, st.menu)) {
                        cb.onMenuOpened(8, st.menu);
                        this.mDecorContentParent.showOverflowMenu();
                        return;
                    }
                    return;
                }
                return;
            }
            this.mDecorContentParent.hideOverflowMenu();
            PanelFeatureState st2 = getPanelState(0, false);
            if (st2 != null && cb != null && !isDestroyed()) {
                cb.onPanelClosed(8, st2.menu);
                return;
            }
            return;
        }
        PanelFeatureState st3 = getPanelState(0, false);
        if (st3 == null) {
            return;
        }
        boolean newExpandedMode = st3.isInExpandedMode;
        if (toggleMenuMode) {
            newExpandedMode = !newExpandedMode;
        }
        st3.refreshDecorView = true;
        closePanel(st3, false);
        st3.isInExpandedMode = newExpandedMode;
        openPanel(st3, (KeyEvent) null);
    }

    protected boolean initializePanelMenu(PanelFeatureState st) {
        Context context = getContext();
        if ((st.featureId == 0 || st.featureId == 8) && this.mDecorContentParent != null) {
            TypedValue outValue = new TypedValue();
            Resources.Theme baseTheme = context.getTheme();
            baseTheme.resolveAttribute(16843825, outValue, true);
            Resources.Theme widgetTheme = null;
            if (outValue.resourceId != 0) {
                widgetTheme = context.getResources().newTheme();
                widgetTheme.setTo(baseTheme);
                widgetTheme.applyStyle(outValue.resourceId, true);
                widgetTheme.resolveAttribute(16843671, outValue, true);
            } else {
                baseTheme.resolveAttribute(16843671, outValue, true);
            }
            if (outValue.resourceId != 0) {
                if (widgetTheme == null) {
                    widgetTheme = context.getResources().newTheme();
                    widgetTheme.setTo(baseTheme);
                }
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            if (widgetTheme != null) {
                context = new ContextThemeWrapper(context, 0);
                context.getTheme().setTo(widgetTheme);
            }
        }
        MenuBuilder menu = new MenuBuilder(context);
        menu.setCallback(this);
        st.setMenu(menu);
        return true;
    }

    protected boolean initializePanelDecor(PanelFeatureState st) {
        st.decorView = generateDecor(st.featureId);
        st.gravity = 81;
        st.setStyle(getContext());
        TypedArray a =
                getContext()
                        .obtainStyledAttributes(null, R.styleable.Window, 0, st.listPresenterTheme);
        float elevation = a.getDimension(37, 0.0f);
        if (elevation != 0.0f) {
            st.decorView.setElevation(elevation);
        }
        a.recycle();
        return true;
    }

    private int getOptionsPanelGravity() {
        try {
            return WindowManagerHolder.sWindowManager.getPreferredOptionsPanelGravity(
                    getContext().getDisplayId());
        } catch (RemoteException ex) {
            Log.e(TAG, "Couldn't getOptionsPanelGravity; using default", ex);
            return 81;
        }
    }

    void onOptionsPanelRotationChanged() {
        PanelFeatureState st = getPanelState(0, false);
        if (st == null) {
            return;
        }
        WindowManager.LayoutParams lp =
                st.decorView != null
                        ? (WindowManager.LayoutParams) st.decorView.getLayoutParams()
                        : null;
        if (lp != null) {
            lp.gravity = getOptionsPanelGravity();
            ViewManager wm = getWindowManager();
            if (wm != null) {
                wm.updateViewLayout(st.decorView, lp);
            }
        }
    }

    protected boolean initializePanelContent(PanelFeatureState st) {
        MenuView menuView;
        if (st.createdPanelView != null) {
            st.shownPanelView = st.createdPanelView;
            return true;
        }
        if (st.menu == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        if (st.isInListMode()) {
            menuView = st.getListMenuView(getContext(), this.mPanelMenuPresenterCallback);
        } else {
            menuView = st.getIconMenuView(getContext(), this.mPanelMenuPresenterCallback);
        }
        st.shownPanelView = (View) menuView;
        if (st.shownPanelView == null) {
            return false;
        }
        int defaultAnimations = menuView.getWindowAnimations();
        if (defaultAnimations != 0) {
            st.windowAnimations = defaultAnimations;
        }
        return true;
    }

    @Override // android.view.Window
    public boolean performContextMenuIdentifierAction(int id, int flags) {
        if (this.mContextMenu != null) {
            return this.mContextMenu.performIdentifierAction(id, flags);
        }
        return false;
    }

    @Override // android.view.Window
    public final void setElevation(float elevation) {
        this.mElevation = elevation;
        WindowManager.LayoutParams attrs = getAttributes();
        if (this.mDecor != null) {
            this.mDecor.setElevation(elevation);
            attrs.setSurfaceInsets(this.mDecor, true, false);
        }
        dispatchWindowAttributesChanged(attrs);
    }

    @Override // android.view.Window
    public float getElevation() {
        return this.mElevation;
    }

    @Override // android.view.Window
    public final void setClipToOutline(boolean clipToOutline) {
        this.mClipToOutline = clipToOutline;
        if (this.mDecor != null) {
            this.mDecor.setClipToOutline(clipToOutline);
        }
    }

    @Override // android.view.Window
    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable != this.mBackgroundDrawable) {
            this.mBackgroundDrawable = drawable;
            if (this.mDecor != null) {
                this.mDecor.startChanging();
                this.mDecor.setWindowBackground(drawable);
                if (this.mBackgroundFallbackDrawable != null) {
                    this.mDecor.setBackgroundFallback(
                            drawable != null ? null : this.mBackgroundFallbackDrawable);
                }
                this.mDecor.finishChanging();
            }
        }
    }

    @Override // android.view.Window
    public final void setBackgroundBlurRadius(int blurRadius) {
        super.setBackgroundBlurRadius(blurRadius);
        if (CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED
                && this.mBackgroundBlurRadius != Math.max(blurRadius, 0)) {
            this.mBackgroundBlurRadius = Math.max(blurRadius, 0);
            this.mDecor.setBackgroundBlurRadius(this.mBackgroundBlurRadius);
        }
    }

    @Override // android.view.Window
    public final void setFeatureDrawableResource(int featureId, int resId) {
        if (resId != 0) {
            DrawableFeatureState st = getDrawableState(featureId, true);
            if (st.resid != resId) {
                st.resid = resId;
                st.uri = null;
                st.local = getContext().getDrawable(resId);
                updateDrawable(featureId, st, false);
                return;
            }
            return;
        }
        setFeatureDrawable(featureId, null);
    }

    @Override // android.view.Window
    public final void setFeatureDrawableUri(int featureId, Uri uri) {
        if (uri != null) {
            DrawableFeatureState st = getDrawableState(featureId, true);
            if (st.uri == null || !st.uri.equals(uri)) {
                st.resid = 0;
                st.uri = uri;
                st.local = loadImageURI(uri);
                updateDrawable(featureId, st, false);
                return;
            }
            return;
        }
        setFeatureDrawable(featureId, null);
    }

    @Override // android.view.Window
    public final void setFeatureDrawable(int featureId, Drawable drawable) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        st.resid = 0;
        st.uri = null;
        if (st.local != drawable) {
            st.local = drawable;
            updateDrawable(featureId, st, false);
        }
    }

    @Override // android.view.Window
    public void setFeatureDrawableAlpha(int featureId, int alpha) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        if (st.alpha != alpha) {
            st.alpha = alpha;
            updateDrawable(featureId, st, false);
        }
    }

    protected final void setFeatureDefaultDrawable(int featureId, Drawable drawable) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        if (st.def != drawable) {
            st.def = drawable;
            updateDrawable(featureId, st, false);
        }
    }

    @Override // android.view.Window
    public final void setFeatureInt(int featureId, int value) {
        updateInt(featureId, value, false);
    }

    protected final void updateDrawable(int featureId, boolean fromActive) {
        DrawableFeatureState st = getDrawableState(featureId, false);
        if (st != null) {
            updateDrawable(featureId, st, fromActive);
        }
    }

    protected void onDrawableChanged(int featureId, Drawable drawable, int alpha) {
        ImageView view;
        if (featureId == 3) {
            view = getLeftIconView();
        } else if (featureId == 4) {
            view = getRightIconView();
        } else {
            return;
        }
        if (drawable != null) {
            drawable.setAlpha(alpha);
            view.setImageDrawable(drawable);
            view.setVisibility(0);
            return;
        }
        view.setVisibility(8);
    }

    protected void onIntChanged(int featureId, int value) {
        FrameLayout titleContainer;
        if (featureId == 2 || featureId == 5) {
            updateProgressBars(value);
        } else if (featureId == 7
                && (titleContainer = (FrameLayout) findViewById(R.id.title_container)) != null) {
            this.mLayoutInflater.inflate(value, titleContainer);
        }
    }

    private void updateProgressBars(int value) {
        ProgressBar circularProgressBar = getCircularProgressBar(true);
        ProgressBar horizontalProgressBar = getHorizontalProgressBar(true);
        int features = getLocalFeatures();
        if (value == -1) {
            if ((features & 4) != 0) {
                if (horizontalProgressBar != null) {
                    int level = horizontalProgressBar.getProgress();
                    int visibility =
                            (horizontalProgressBar.isIndeterminate() || level < 10000) ? 0 : 4;
                    horizontalProgressBar.setVisibility(visibility);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((features & 32) != 0) {
                if (circularProgressBar != null) {
                    circularProgressBar.setVisibility(0);
                    return;
                } else {
                    Log.e(TAG, "Circular progress bar not located in current window decor");
                    return;
                }
            }
            return;
        }
        if (value == -2) {
            if ((features & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility(8);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((features & 32) != 0) {
                if (circularProgressBar != null) {
                    circularProgressBar.setVisibility(8);
                    return;
                } else {
                    Log.e(TAG, "Circular progress bar not located in current window decor");
                    return;
                }
            }
            return;
        }
        if (value == -3) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(true);
                return;
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
                return;
            }
        }
        if (value == -4) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(false);
                return;
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
                return;
            }
        }
        if (value >= 0 && value <= 10000) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setProgress(value + 0);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            if (value < 10000) {
                showProgressBars(horizontalProgressBar, circularProgressBar);
                return;
            } else {
                hideProgressBars(horizontalProgressBar, circularProgressBar);
                return;
            }
        }
        if (20000 <= value && value <= 30000) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setSecondaryProgress(
                        value + TaskConstants.TASK_CHILD_LAYER_LETTERBOX_BACKGROUND);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            showProgressBars(horizontalProgressBar, circularProgressBar);
        }
    }

    private void showProgressBars(
            ProgressBar horizontalProgressBar, ProgressBar spinnyProgressBar) {
        int features = getLocalFeatures();
        if ((features & 32) != 0
                && spinnyProgressBar != null
                && spinnyProgressBar.getVisibility() == 4) {
            spinnyProgressBar.setVisibility(0);
        }
        if ((features & 4) != 0
                && horizontalProgressBar != null
                && horizontalProgressBar.getProgress() < 10000) {
            horizontalProgressBar.setVisibility(0);
        }
    }

    private void hideProgressBars(
            ProgressBar horizontalProgressBar, ProgressBar spinnyProgressBar) {
        int features = getLocalFeatures();
        Animation anim = AnimationUtils.loadAnimation(getContext(), 17432577);
        anim.setDuration(1000L);
        if ((features & 32) != 0
                && spinnyProgressBar != null
                && spinnyProgressBar.getVisibility() == 0) {
            spinnyProgressBar.startAnimation(anim);
            spinnyProgressBar.setVisibility(4);
        }
        if ((features & 4) != 0
                && horizontalProgressBar != null
                && horizontalProgressBar.getVisibility() == 0) {
            horizontalProgressBar.startAnimation(anim);
            horizontalProgressBar.setVisibility(4);
        }
    }

    @Override // android.view.Window
    public void setIcon(int resId) {
        this.mIconRes = resId;
        this.mResourcesSetFlags |= 1;
        this.mResourcesSetFlags &= -5;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setIcon(resId);
        }
    }

    @Override // android.view.Window
    public void setDefaultIcon(int resId) {
        if ((this.mResourcesSetFlags & 1) != 0) {
            return;
        }
        this.mIconRes = resId;
        if (this.mDecorContentParent != null) {
            if (!this.mDecorContentParent.hasIcon() || (this.mResourcesSetFlags & 4) != 0) {
                if (resId != 0) {
                    this.mDecorContentParent.setIcon(resId);
                    this.mResourcesSetFlags &= -5;
                } else {
                    this.mDecorContentParent.setIcon(
                            getContext().getPackageManager().getDefaultActivityIcon());
                    this.mResourcesSetFlags |= 4;
                }
            }
        }
    }

    @Override // android.view.Window
    public void setLogo(int resId) {
        this.mLogoRes = resId;
        this.mResourcesSetFlags |= 2;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setLogo(resId);
        }
    }

    @Override // android.view.Window
    public void setDefaultLogo(int resId) {
        if ((this.mResourcesSetFlags & 2) != 0) {
            return;
        }
        this.mLogoRes = resId;
        if (this.mDecorContentParent != null && !this.mDecorContentParent.hasLogo()) {
            this.mDecorContentParent.setLogo(resId);
        }
    }

    @Override // android.view.Window
    public void setLocalFocus(boolean hasFocus, boolean inTouchMode) {
        ViewRootImpl viewRoot = getViewRootImpl();
        viewRoot.windowFocusChanged(hasFocus);
        viewRoot.touchModeChanged(inTouchMode);
    }

    @Override // android.view.Window
    public void injectInputEvent(InputEvent event) {
        getViewRootImpl().dispatchInputEvent(event);
    }

    private ViewRootImpl getViewRootImpl() {
        ViewRootImpl viewRootImpl = getViewRootImplOrNull();
        if (viewRootImpl != null) {
            return viewRootImpl;
        }
        throw new IllegalStateException("view not added");
    }

    private ViewRootImpl getViewRootImplOrNull() {
        if (this.mDecor == null) {
            return null;
        }
        return this.mDecor.getViewRootImpl();
    }

    @Override // android.view.Window
    public void takeKeyEvents(boolean get) {
        this.mDecor.setFocusable(get);
    }

    @Override // android.view.Window
    public boolean superDispatchKeyEvent(KeyEvent event) {
        return this.mDecor.superDispatchKeyEvent(event);
    }

    @Override // android.view.Window
    public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return this.mDecor.superDispatchKeyShortcutEvent(event);
    }

    @Override // android.view.Window
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return this.mDecor.superDispatchTouchEvent(event);
    }

    @Override // android.view.Window
    public boolean superDispatchTrackballEvent(MotionEvent event) {
        return this.mDecor.superDispatchTrackballEvent(event);
    }

    @Override // android.view.Window
    public boolean superDispatchGenericMotionEvent(MotionEvent event) {
        return this.mDecor.superDispatchGenericMotionEvent(event);
    }

    protected boolean onKeyDown(int featureId, int keyCode, KeyEvent event) {
        KeyEvent.DispatcherState dispatcher =
                this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null;
        switch (keyCode) {
            case 4:
                if (event.getRepeatCount() <= 0 && featureId >= 0) {
                    if (dispatcher != null) {
                        dispatcher.startTracking(event, this);
                    }
                    return true;
                }
                return false;
            case 24:
            case 25:
            case 164:
                if (this.mMediaController != null && !isActivePhoneCallOngoing()) {
                    getMediaSessionManager()
                            .dispatchVolumeKeyEventToSessionAsSystemService(
                                    event, this.mMediaController.getSessionToken());
                } else {
                    getMediaSessionManager()
                            .dispatchVolumeKeyEventAsSystemService(
                                    event, this.mVolumeControlStreamType);
                }
                return true;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 126:
            case 127:
            case 130:
                return this.mMediaController != null
                        && getMediaSessionManager()
                                .dispatchMediaKeyEventToSessionAsSystemService(
                                        event, this.mMediaController.getSessionToken());
            case 82:
                onKeyDownPanel(featureId >= 0 ? featureId : 0, event);
                return true;
            default:
                return false;
        }
    }

    private boolean isActivePhoneCallOngoing() {
        return this.mAudioMode == 2 || this.mAudioMode == 3;
    }

    private KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager =
                    (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
        }
        return this.mKeyguardManager;
    }

    AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        }
        return this.mAudioManager;
    }

    private MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager =
                    (MediaSessionManager)
                            getContext().getSystemService(Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    protected boolean onKeyUp(int featureId, int keyCode, KeyEvent event) {
        PanelFeatureState st;
        KeyEvent.DispatcherState dispatcher =
                this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null;
        if (dispatcher != null) {
            dispatcher.handleUpEvent(event);
        }
        switch (keyCode) {
            case 4:
                if (featureId >= 0 && event.isTracking() && !event.isCanceled()) {
                    if (featureId == 0
                            && (st = getPanelState(featureId, false)) != null
                            && st.isInExpandedMode) {
                        reopenMenu(true);
                        return true;
                    }
                    closePanel(featureId);
                    return true;
                }
                return false;
            case 24:
            case 25:
                if (this.mMediaController != null) {
                    getMediaSessionManager()
                            .dispatchVolumeKeyEventToSessionAsSystemService(
                                    event, this.mMediaController.getSessionToken());
                } else {
                    getMediaSessionManager()
                            .dispatchVolumeKeyEventAsSystemService(
                                    event, this.mVolumeControlStreamType);
                }
                return true;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 126:
            case 127:
            case 130:
                return this.mMediaController != null
                        && getMediaSessionManager()
                                .dispatchMediaKeyEventToSessionAsSystemService(
                                        event, this.mMediaController.getSessionToken());
            case 82:
                onKeyUpPanel(featureId >= 0 ? featureId : 0, event);
                return true;
            case 84:
                if (!isNotInstantAppAndKeyguardRestricted()
                        && (getContext().getResources().getConfiguration().uiMode & 15) != 6) {
                    if (event.isTracking() && !event.isCanceled()) {
                        launchDefaultSearch(event);
                    }
                    return true;
                }
                return false;
            case 164:
                getMediaSessionManager()
                        .dispatchVolumeKeyEventAsSystemService(event, Integer.MIN_VALUE);
                return true;
            case 171:
                if (this.mSupportsPictureInPicture && !event.isCanceled()) {
                    getWindowControllerCallback().enterPictureInPictureModeIfPossible();
                }
                return true;
            default:
                return false;
        }
    }

    private boolean isNotInstantAppAndKeyguardRestricted() {
        return !getContext().getPackageManager().isInstantApp()
                && getKeyguardManager().inKeyguardRestrictedInputMode();
    }

    @Override // android.view.Window
    protected void onActive() {}

    @Override // android.view.Window
    public final View getDecorView() {
        if (this.mDecor == null || this.mForceDecorInstall) {
            installDecor();
        }
        return this.mDecor;
    }

    @Override // android.view.Window
    public final View peekDecorView() {
        return this.mDecor;
    }

    void onViewRootImplSet(ViewRootImpl viewRoot) {
        viewRoot.setActivityConfigCallback(this.mActivityConfigCallback);
        viewRoot.getOnBackInvokedDispatcher().updateContext(getContext());
        this.mProxyOnBackInvokedDispatcher.setActualDispatcher(
                viewRoot.getOnBackInvokedDispatcher());
        applyDecorFitsSystemWindows();
    }

    @Override // android.view.Window
    public Bundle saveHierarchyState() {
        Bundle outState = new Bundle();
        if (this.mContentParent == null) {
            return outState;
        }
        SparseArray<Parcelable> states = new SparseArray<>();
        this.mContentParent.saveHierarchyState(states);
        outState.putSparseParcelableArray(VIEWS_TAG, states);
        View focusedView = this.mContentParent.findFocus();
        if (focusedView != null && focusedView.getId() != -1) {
            outState.putInt(FOCUSED_ID_TAG, focusedView.getId());
        }
        SparseArray<Parcelable> panelStates = new SparseArray<>();
        savePanelState(panelStates);
        if (panelStates.size() > 0) {
            outState.putSparseParcelableArray(PANELS_TAG, panelStates);
        }
        if (this.mDecorContentParent != null) {
            SparseArray<Parcelable> actionBarStates = new SparseArray<>();
            this.mDecorContentParent.saveToolbarHierarchyState(actionBarStates);
            outState.putSparseParcelableArray(ACTION_BAR_TAG, actionBarStates);
        }
        return outState;
    }

    @Override // android.view.Window
    public void restoreHierarchyState(Bundle savedInstanceState) {
        if (this.mContentParent == null) {
            return;
        }
        SparseArray<Parcelable> savedStates =
                savedInstanceState.getSparseParcelableArray(VIEWS_TAG);
        if (savedStates != null) {
            this.mContentParent.restoreHierarchyState(savedStates);
        }
        int focusedViewId = savedInstanceState.getInt(FOCUSED_ID_TAG, -1);
        if (focusedViewId != -1) {
            View needsFocus = this.mContentParent.findViewById(focusedViewId);
            if (needsFocus == null) {
                Log.w(
                        TAG,
                        "Previously focused view reported id "
                                + focusedViewId
                                + " during save, but can't be found during restore.");
            } else {
                needsFocus.requestFocus();
            }
        }
        SparseArray<Parcelable> panelStates =
                savedInstanceState.getSparseParcelableArray(PANELS_TAG);
        if (panelStates != null) {
            restorePanelState(panelStates);
        }
        if (this.mDecorContentParent != null) {
            SparseArray<Parcelable> actionBarStates =
                    savedInstanceState.getSparseParcelableArray(ACTION_BAR_TAG);
            if (actionBarStates == null) {
                Log.w(
                        TAG,
                        "Missing saved instance states for action bar views! State will not be"
                            + " restored.");
            } else {
                doPendingInvalidatePanelMenu();
                this.mDecorContentParent.restoreToolbarHierarchyState(actionBarStates);
            }
        }
    }

    private void savePanelState(SparseArray<Parcelable> icicles) {
        PanelFeatureState[] panels = this.mPanels;
        if (panels == null) {
            return;
        }
        for (int curFeatureId = panels.length - 1; curFeatureId >= 0; curFeatureId--) {
            if (panels[curFeatureId] != null) {
                icicles.put(curFeatureId, panels[curFeatureId].onSaveInstanceState());
            }
        }
    }

    private void restorePanelState(SparseArray<Parcelable> icicles) {
        for (int i = icicles.size() - 1; i >= 0; i--) {
            int curFeatureId = icicles.keyAt(i);
            PanelFeatureState st = getPanelState(curFeatureId, false);
            if (st != null) {
                st.onRestoreInstanceState(icicles.get(curFeatureId));
                invalidatePanelMenu(curFeatureId);
            }
        }
    }

    void openPanelsAfterRestore() {
        PanelFeatureState[] panels = this.mPanels;
        if (panels == null) {
            return;
        }
        for (int i = panels.length - 1; i >= 0; i--) {
            PanelFeatureState st = panels[i];
            if (st != null) {
                st.applyFrozenState();
                if (!st.isOpen && st.wasLastOpen) {
                    st.isInExpandedMode = st.wasLastExpanded;
                    openPanel(st, (KeyEvent) null);
                }
            }
        }
    }

    @Override // android.view.Window
    protected void onDestroy() {
        if (this.mOnModeChangedListener != null) {
            getAudioManager().removeOnModeChangedListener(this.mOnModeChangedListener);
            this.mOnModeChangedListener = null;
        }
    }

    private class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        private PanelMenuPresenterCallback() {}

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            Menu parentMenu = menu.getRootMenu();
            boolean isSubMenu = parentMenu != menu;
            PanelFeatureState panel = PhoneWindow.this.findMenuPanel(isSubMenu ? parentMenu : menu);
            if (panel != null) {
                if (isSubMenu) {
                    PhoneWindow.this.callOnPanelClosed(panel.featureId, panel, parentMenu);
                    PhoneWindow.this.closePanel(panel, true);
                } else {
                    PhoneWindow.this.closePanel(panel, allMenusAreClosing);
                }
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            Window.Callback cb;
            if (subMenu == null
                    && PhoneWindow.this.hasFeature(8)
                    && (cb = PhoneWindow.this.getCallback()) != null
                    && !PhoneWindow.this.isDestroyed()) {
                cb.onMenuOpened(8, subMenu);
                return true;
            }
            return true;
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {}

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            Window.Callback cb = PhoneWindow.this.getCallback();
            if (cb != null) {
                cb.onMenuOpened(8, subMenu);
                return true;
            }
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            PhoneWindow.this.checkCloseActionMenu(menu);
        }
    }

    protected DecorView generateDecor(int featureId) {
        Context context;
        if (this.mUseDecorContext) {
            Context applicationContext = getContext().getApplicationContext();
            if (applicationContext == null) {
                context = getContext();
            } else {
                context = new DecorContext(applicationContext, this);
                if (this.mTheme != -1) {
                    context.setTheme(this.mTheme);
                }
            }
        } else {
            context = getContext();
        }
        return new DecorView(context, featureId, this, getAttributes());
    }

    protected ViewGroup generateLayout(DecorView decor) {
        int layoutResource;
        Drawable frame;
        ProgressBar progress;
        int i;
        TypedArray a = getWindowStyle();
        WindowManager.LayoutParams params = getAttributes();
        this.mEdgeToEdgeEnforced = isEdgeToEdgeEnforced(getContext().getApplicationInfo(), true, a);
        if (this.mEdgeToEdgeEnforced) {
            getAttributes().privateFlags |= 2048;
            this.mDecorFitsSystemWindows = false;
            applyDecorFitsSystemWindows();
            this.mStatusBarColor = 0;
            this.mNavigationBarDividerColor = 0;
        }
        if (CompatChanges.isChangeEnabled(OVERRIDE_LAYOUT_IN_DISPLAY_CUTOUT_MODE)
                && !a.getBoolean(63, false)) {
            getAttributes().privateFlags |= 262144;
        }
        this.mIsFloating = a.getBoolean(4, false);
        int flagsToUpdate = (~getForcedWindowFlags()) & 65792;
        if (this.mIsFloating && !this.mAllowFloatingWindowsFillScreen.booleanValue()) {
            setLayout(-2, -2);
            setFlags(0, flagsToUpdate);
        } else {
            setFlags(65792, flagsToUpdate);
            params.setFitInsetsSides(0);
            params.setFitInsetsTypes(0);
        }
        if (a.getBoolean(3, false)) {
            requestFeature(1);
        } else if (a.getBoolean(15, false)) {
            requestFeature(8);
        }
        if (a.getBoolean(17, false)) {
            requestFeature(9);
        }
        if (a.getBoolean(16, false)) {
            requestFeature(10);
        }
        if (a.getBoolean(9, false)) {
            setFlags(1024, (~getForcedWindowFlags()) & 1024);
        }
        if (a.getBoolean(23, false)) {
            setFlags(67108864, (~getForcedWindowFlags()) & 67108864);
        }
        if (a.getBoolean(24, false)) {
            setFlags(134217728, (~getForcedWindowFlags()) & 134217728);
        }
        if (a.getBoolean(14, false)) {
            setFlags(1048576, (~getForcedWindowFlags()) & 1048576);
        }
        if (a.getBoolean(18, getContext().getApplicationInfo().targetSdkVersion >= 11)) {
            setFlags(8388608, (~getForcedWindowFlags()) & 8388608);
        }
        a.getValue(19, this.mMinWidthMajor);
        a.getValue(20, this.mMinWidthMinor);
        if (a.hasValue(68)) {
            if (this.mFixedWidthMajor == null) {
                this.mFixedWidthMajor = new TypedValue();
            }
            a.getValue(68, this.mFixedWidthMajor);
        }
        if (a.hasValue(69)) {
            if (this.mFixedWidthMinor == null) {
                this.mFixedWidthMinor = new TypedValue();
            }
            a.getValue(69, this.mFixedWidthMinor);
        }
        if (a.hasValue(66)) {
            if (this.mFixedHeightMajor == null) {
                this.mFixedHeightMajor = new TypedValue();
            }
            a.getValue(66, this.mFixedHeightMajor);
        }
        if (a.hasValue(67)) {
            if (this.mFixedHeightMinor == null) {
                this.mFixedHeightMinor = new TypedValue();
            }
            a.getValue(67, this.mFixedHeightMinor);
        }
        if (a.getBoolean(25, false)) {
            requestFeature(12);
        }
        if (a.getBoolean(44, false)) {
            requestFeature(13);
        }
        if (a.hasValue(64) && sToolkitSetFrameRateReadOnlyFlagValue) {
            setFrameRatePowerSavingsBalanced(a.getBoolean(64, true));
        }
        this.mIsTranslucent = a.getBoolean(5, false);
        Context context = getContext();
        int targetSdk = context.getApplicationInfo().targetSdkVersion;
        boolean targetPreL = targetSdk < 21;
        boolean targetPreQ = targetSdk < 29;
        if (!this.mForcedStatusBarColor && !this.mEdgeToEdgeEnforced) {
            this.mStatusBarColor = a.getColor(34, -16777216);
        }
        if (!this.mForcedNavigationBarColor) {
            int navBarCompatibleColor = context.getColor(R.color.navigation_bar_compatible);
            int navBarDefaultColor = context.getColor(R.color.navigation_bar_default);
            int navBarColor = a.getColor(35, navBarDefaultColor);
            boolean navigationBarColorSpecified = navBarColor != navBarDefaultColor;
            if (!navigationBarColorSpecified
                    && !this.mEdgeToEdgeEnforced
                    && !context.getResources().getBoolean(R.bool.config_navBarDefaultTransparent)) {
                i = navBarCompatibleColor;
            } else {
                i = navBarColor;
            }
            this.mNavigationBarColor = i;
            this.mNavigationBarColorSpecified |= navigationBarColorSpecified;
            if (!this.mEdgeToEdgeEnforced) {
                this.mNavigationBarDividerColor = a.getColor(49, 0);
            }
        }
        if (!targetPreQ) {
            this.mEnsureStatusBarContrastWhenTransparent = a.getBoolean(51, false);
            this.mEnsureNavigationBarContrastWhenTransparent = a.getBoolean(52, true);
        }
        int appColor = a.getColor(35, this.mDeviceDefaultNavigationBarColor);
        boolean appUseDeviceDefault =
                this.mDeviceDefaultNavigationBarColor == appColor
                        || context.getColor(R.color.navigation_bar_default) == appColor;
        this.mDefaultNavigationBarColor = getDefaultNavigationBarColor();
        if (!this.mForcedNavigationBarColor) {
            this.mNavigationBarColor =
                    appUseDeviceDefault ? this.mDefaultNavigationBarColor : appColor;
        }
        if (this.mNavigationBarColor == this.mDefaultNavigationBarColor
                && needLightNavigationBar(this.mNavigationBarColor)) {
            params.samsungFlags |= 1048576;
        }
        if (!this.mIsFloating) {
            if (!targetPreL && a.getBoolean(33, false)) {
                setFlags(Integer.MIN_VALUE, (~getForcedWindowFlags()) & Integer.MIN_VALUE);
            }
            if (this.mDecor.mForceWindowDrawsBarBackgrounds) {
                params.privateFlags |= 32768;
            }
            if (this.mActivityCurrentConfig == null
                    || !this.mActivityCurrentConfig.semIsPopOver()) {
                params.privateFlags |= 64;
            }
        }
        if (a.getBoolean(62, false)) {
            params.privateFlags |= 64;
        }
        boolean lightStatus = a.getBoolean(45, false);
        boolean lightNav = a.getBoolean(48, false);
        decor.setSystemUiVisibility(
                (decor.getSystemUiVisibility() & (-8209))
                        | (lightStatus ? 8192 : 0)
                        | (lightNav ? 16 : 0));
        decor.getWindowInsetsController()
                .setSystemBarsAppearanceFromResource(
                        (lightStatus ? 8 : 0) | (lightNav ? 16 : 0), 24);
        try {
            if (context.getResources().getAssets().getSamsungThemeOverlays().size() > 0) {
                TypedValue isDeviceDefault = new TypedValue();
                context.getTheme()
                        .resolveAttribute(R.attr.parentIsDeviceDefault, isDeviceDefault, false);
                if (isDeviceDefault.data != 0
                        && a.getBoolean(45, true)
                        && this.mStatusBarColor != 0) {
                    if (context.getResources().getBoolean(R.bool.sem_window_light_status_bar)) {
                        decor.setSystemUiVisibility(decor.getSystemUiVisibility() | 8192);
                    } else {
                        decor.setSystemUiVisibility(decor.getSystemUiVisibility() & (-8193));
                    }
                    this.mStatusBarColor =
                            context.getResources().getColor(R.color.tw_status_bar_color);
                }
            }
            if (context.getResources().getAssets().getSamsungThemeOverlays().size() > 0
                    || WallpaperThemeUtils.hasWallpaperThemeOverlays(context)) {
                this.mThemeApplied = true;
                int themeColor = getDefaultNavigationBarColor();
                if (this.mNavigationBarColor == this.mDefaultNavigationBarColor
                        && !this.mForcedNavigationBarColor) {
                    this.mNavigationBarColor = themeColor;
                }
                if (this.mNavigationBarColor == themeColor && !needLightNavigationBar(themeColor)) {
                    params.samsungFlags &= -1048577;
                }
                this.mDefaultNavigationBarColor = themeColor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a.hasValue(50)) {
            int mode = a.getInt(50, -1);
            if (mode < 0 || mode > 3) {
                throw new UnsupportedOperationException(
                        "Unknown windowLayoutInDisplayCutoutMode: " + a.getString(50));
            }
            params.layoutInDisplayCutoutMode = mode;
        }
        if ((this.mAlwaysReadCloseOnTouchAttr
                        || getContext().getApplicationInfo().targetSdkVersion >= 11)
                && a.getBoolean(21, false)) {
            setCloseOnTouchOutsideIfNotSet(true);
        }
        if (!hasSoftInputMode()) {
            params.softInputMode = a.getInt(13, params.softInputMode);
        }
        if (a.getBoolean(11, this.mIsFloating)) {
            if ((getForcedWindowFlags() & 2) == 0) {
                params.flags |= 2;
            }
            if (!haveDimAmount()) {
                params.dimAmount = a.getFloat(0, 0.5f);
            }
        }
        if (a.getBoolean(54, false)) {
            if ((getForcedWindowFlags() & 4) == 0) {
                params.flags |= 4;
            }
            params.setBlurBehindRadius(a.getDimensionPixelSize(53, 0));
        }
        setBackgroundBlurRadius(a.getDimensionPixelSize(55, 0));
        if (params.windowAnimations == 0) {
            params.windowAnimations = a.getResourceId(8, 0);
        }
        if (getContainer() == null) {
            if (this.mBackgroundDrawable == null) {
                if (this.mFrameResource == 0) {
                    this.mFrameResource = a.getResourceId(2, 0);
                }
                if (a.hasValue(1)) {
                    this.mBackgroundDrawable = a.getDrawable(1);
                    this.mDecor.setLastBackgroundResource(a.getResourceId(1, 0));
                }
            }
            if (a.hasValue(46)) {
                this.mBackgroundFallbackDrawable = a.getDrawable(46);
            }
            if (this.mLoadElevation) {
                if (this.mDecor.isDialogInPopOver()) {
                    this.mElevation = 0.0f;
                } else if (!CoreRune.MW_SHELL_FREEFORM_SHADOW_WITH_VIEW_ELEVATION
                        || !this.mDecor.isNonFullscreenWindowInFreeform()) {
                    this.mElevation = a.getDimension(37, 0.0f);
                } else {
                    this.mElevation = 0.0f;
                }
            }
            this.mClipToOutline = a.getBoolean(38, false);
            this.mTextColor = a.getColor(7, 0);
        }
        int features = getLocalFeatures();
        if ((features & 24) != 0) {
            if (this.mIsFloating) {
                TypedValue res = new TypedValue();
                getContext()
                        .getTheme()
                        .resolveAttribute(R.attr.dialogTitleIconsDecorLayout, res, true);
                layoutResource = res.resourceId;
            } else {
                layoutResource = R.layout.screen_title_icons;
            }
            removeFeature(8);
        } else {
            int layoutResource2 = features & 36;
            if (layoutResource2 != 0 && (features & 256) == 0) {
                layoutResource = R.layout.screen_progress;
            } else {
                int layoutResource3 = features & 128;
                if (layoutResource3 != 0) {
                    if (this.mIsFloating) {
                        TypedValue res2 = new TypedValue();
                        getContext()
                                .getTheme()
                                .resolveAttribute(R.attr.dialogCustomTitleDecorLayout, res2, true);
                        layoutResource = res2.resourceId;
                    } else {
                        layoutResource = R.layout.screen_custom_title;
                    }
                    removeFeature(8);
                } else {
                    int layoutResource4 = features & 2;
                    if (layoutResource4 == 0) {
                        if (this.mIsFloating) {
                            TypedValue res3 = new TypedValue();
                            getContext()
                                    .getTheme()
                                    .resolveAttribute(R.attr.dialogTitleDecorLayout, res3, true);
                            layoutResource = res3.resourceId;
                        } else {
                            int layoutResource5 = features & 256;
                            if (layoutResource5 != 0) {
                                layoutResource = a.getResourceId(65, R.layout.screen_action_bar);
                            } else {
                                layoutResource = R.layout.screen_title;
                            }
                        }
                    } else {
                        int layoutResource6 = features & 1024;
                        if (layoutResource6 != 0) {
                            layoutResource = R.layout.screen_simple_overlay_action_mode;
                        } else {
                            layoutResource = R.layout.screen_simple;
                        }
                    }
                }
            }
        }
        this.mDecor.startChanging();
        this.mDecor.onResourcesLoaded(this.mLayoutInflater, layoutResource);
        ViewGroup contentParent = (ViewGroup) findViewById(16908290);
        if (contentParent == null) {
            throw new RuntimeException("Window couldn't find content container view");
        }
        if ((features & 32) != 0 && (progress = getCircularProgressBar(false)) != null) {
            progress.setIndeterminate(true);
        }
        if (getContainer() == null) {
            this.mDecor.setWindowBackground(this.mBackgroundDrawable);
            if (this.mFrameResource != 0) {
                frame = getContext().getDrawable(this.mFrameResource);
            } else {
                frame = null;
            }
            this.mDecor.setWindowFrame(frame);
            this.mDecor.setElevation(this.mElevation);
            this.mDecor.setClipToOutline(this.mClipToOutline);
            if (this.mTitle != null) {
                setTitle(this.mTitle);
            }
            if (this.mTitleColor == 0) {
                this.mTitleColor = this.mTextColor;
            }
            setTitleColor(this.mTitleColor);
        }
        this.mDecor.finishChanging();
        return contentParent;
    }

    @Override // android.view.Window
    public void alwaysReadCloseOnTouchAttr() {
        this.mAlwaysReadCloseOnTouchAttr = true;
    }

    private void installDecor() {
        this.mForceDecorInstall = false;
        if (this.mDecor == null) {
            this.mDecor = generateDecor(-1);
            this.mDecor.setDescendantFocusability(262144);
            this.mDecor.setIsRootNamespace(true);
            if (!this.mInvalidatePanelMenuPosted && this.mInvalidatePanelMenuFeatures != 0) {
                this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            }
        } else {
            this.mDecor.setWindow(this);
        }
        if (this.mContentParent == null) {
            this.mContentParent = generateLayout(this.mDecor);
            this.mDecor.makeFrameworkOptionalFitsSystemWindows();
            DecorContentParent decorContentParent =
                    (DecorContentParent) this.mDecor.findViewById(R.id.decor_content_parent);
            if (decorContentParent != null) {
                this.mDecorContentParent = decorContentParent;
                this.mDecorContentParent.setWindowCallback(getCallback());
                if (this.mDecorContentParent.getTitle() == null) {
                    this.mDecorContentParent.setWindowTitle(this.mTitle);
                }
                int localFeatures = getLocalFeatures();
                for (int i = 0; i < 13; i++) {
                    if (((1 << i) & localFeatures) != 0) {
                        this.mDecorContentParent.initFeature(i);
                    }
                }
                this.mDecorContentParent.setUiOptions(this.mUiOptions);
                if ((this.mResourcesSetFlags & 1) != 0
                        || (this.mIconRes != 0 && !this.mDecorContentParent.hasIcon())) {
                    this.mDecorContentParent.setIcon(this.mIconRes);
                } else if ((this.mResourcesSetFlags & 1) == 0
                        && this.mIconRes == 0
                        && !this.mDecorContentParent.hasIcon()) {
                    this.mDecorContentParent.setIcon(
                            getContext().getPackageManager().getDefaultActivityIcon());
                    this.mResourcesSetFlags |= 4;
                }
                if ((this.mResourcesSetFlags & 2) != 0
                        || (this.mLogoRes != 0 && !this.mDecorContentParent.hasLogo())) {
                    this.mDecorContentParent.setLogo(this.mLogoRes);
                }
                PanelFeatureState st = getPanelState(0, false);
                if (!isDestroyed()
                        && ((st == null || st.menu == null) && !this.mIsStartingWindow)) {
                    invalidatePanelMenu(8);
                }
            } else {
                this.mTitleView = (TextView) findViewById(16908310);
                if (this.mTitleView != null) {
                    if ((getLocalFeatures() & 2) != 0) {
                        View titleContainer = findViewById(R.id.title_container);
                        if (titleContainer != null) {
                            titleContainer.setVisibility(8);
                        } else {
                            this.mTitleView.setVisibility(8);
                        }
                        this.mContentParent.setForeground(null);
                    } else {
                        this.mTitleView.lambda$setTextAsync$0(this.mTitle);
                    }
                }
            }
            if (this.mDecor.getBackground() == null && this.mBackgroundFallbackDrawable != null) {
                this.mDecor.setBackgroundFallback(this.mBackgroundFallbackDrawable);
            }
            if (hasFeature(13)) {
                if (this.mTransitionManager == null) {
                    int transitionRes = getWindowStyle().getResourceId(26, 0);
                    if (transitionRes != 0) {
                        TransitionInflater inflater = TransitionInflater.from(getContext());
                        this.mTransitionManager =
                                inflater.inflateTransitionManager(
                                        transitionRes, this.mContentParent);
                    } else {
                        this.mTransitionManager = new TransitionManager();
                    }
                }
                this.mEnterTransition = getTransition(this.mEnterTransition, null, 27);
                this.mReturnTransition =
                        getTransition(this.mReturnTransition, USE_DEFAULT_TRANSITION, 39);
                this.mExitTransition = getTransition(this.mExitTransition, null, 28);
                this.mReenterTransition =
                        getTransition(this.mReenterTransition, USE_DEFAULT_TRANSITION, 40);
                this.mSharedElementEnterTransition =
                        getTransition(this.mSharedElementEnterTransition, null, 29);
                this.mSharedElementReturnTransition =
                        getTransition(
                                this.mSharedElementReturnTransition, USE_DEFAULT_TRANSITION, 41);
                this.mSharedElementExitTransition =
                        getTransition(this.mSharedElementExitTransition, null, 30);
                this.mSharedElementReenterTransition =
                        getTransition(
                                this.mSharedElementReenterTransition, USE_DEFAULT_TRANSITION, 42);
                if (this.mAllowEnterTransitionOverlap == null) {
                    this.mAllowEnterTransitionOverlap =
                            Boolean.valueOf(getWindowStyle().getBoolean(32, true));
                }
                if (this.mAllowReturnTransitionOverlap == null) {
                    this.mAllowReturnTransitionOverlap =
                            Boolean.valueOf(getWindowStyle().getBoolean(31, true));
                }
                if (this.mBackgroundFadeDurationMillis < 0) {
                    this.mBackgroundFadeDurationMillis = getWindowStyle().getInteger(36, 300);
                }
                if (this.mSharedElementsUseOverlay == null) {
                    this.mSharedElementsUseOverlay =
                            Boolean.valueOf(getWindowStyle().getBoolean(43, true));
                }
            }
        }
    }

    private Transition getTransition(Transition currentValue, Transition defaultValue, int id) {
        if (currentValue != defaultValue) {
            return currentValue;
        }
        int transitionId = getWindowStyle().getResourceId(id, -1);
        if (transitionId == -1 || transitionId == 17760256) {
            return defaultValue;
        }
        TransitionInflater inflater = TransitionInflater.from(getContext());
        Transition transition = inflater.inflateTransition(transitionId);
        if ((transition instanceof TransitionSet)
                && ((TransitionSet) transition).getTransitionCount() == 0) {
            return null;
        }
        return transition;
    }

    private Drawable loadImageURI(Uri uri) {
        try {
            return Drawable.createFromStream(
                    getContext().getContentResolver().openInputStream(uri), null);
        } catch (Exception e) {
            Log.w(TAG, "Unable to open content: " + uri);
            return null;
        }
    }

    private DrawableFeatureState getDrawableState(int featureId, boolean required) {
        if ((getFeatures() & (1 << featureId)) == 0) {
            if (!required) {
                return null;
            }
            throw new RuntimeException("The feature has not been requested");
        }
        DrawableFeatureState[] drawableFeatureStateArr = this.mDrawables;
        DrawableFeatureState[] ar = drawableFeatureStateArr;
        if (drawableFeatureStateArr == null || ar.length <= featureId) {
            DrawableFeatureState[] nar = new DrawableFeatureState[featureId + 1];
            if (ar != null) {
                System.arraycopy(ar, 0, nar, 0, ar.length);
            }
            ar = nar;
            this.mDrawables = nar;
        }
        DrawableFeatureState st = ar[featureId];
        if (st == null) {
            DrawableFeatureState st2 = new DrawableFeatureState(featureId);
            ar[featureId] = st2;
            return st2;
        }
        return st;
    }

    PanelFeatureState getPanelState(int featureId, boolean required) {
        return getPanelState(featureId, required, null);
    }

    private PanelFeatureState getPanelState(
            int featureId, boolean required, PanelFeatureState convertPanelState) {
        PanelFeatureState panelFeatureState;
        if ((getFeatures() & (1 << featureId)) == 0) {
            if (!required) {
                return null;
            }
            throw new RuntimeException("The feature has not been requested");
        }
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        PanelFeatureState[] ar = panelFeatureStateArr;
        if (panelFeatureStateArr == null || ar.length <= featureId) {
            PanelFeatureState[] nar = new PanelFeatureState[featureId + 1];
            if (ar != null) {
                System.arraycopy(ar, 0, nar, 0, ar.length);
            }
            ar = nar;
            this.mPanels = nar;
        }
        PanelFeatureState st = ar[featureId];
        if (st == null) {
            if (convertPanelState != null) {
                panelFeatureState = convertPanelState;
            } else {
                panelFeatureState = new PanelFeatureState(featureId);
            }
            PanelFeatureState st2 = panelFeatureState;
            ar[featureId] = panelFeatureState;
            return st2;
        }
        return st;
    }

    @Override // android.view.Window
    public final void setChildDrawable(int featureId, Drawable drawable) {
        DrawableFeatureState st = getDrawableState(featureId, true);
        st.child = drawable;
        updateDrawable(featureId, st, false);
    }

    @Override // android.view.Window
    public final void setChildInt(int featureId, int value) {
        updateInt(featureId, value, false);
    }

    @Override // android.view.Window
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        PanelFeatureState st = getPanelState(0, false);
        return (st == null || st.menu == null || !st.menu.isShortcutKey(keyCode, event))
                ? false
                : true;
    }

    private void updateDrawable(int featureId, DrawableFeatureState st, boolean fromResume) {
        if (this.mContentParent == null) {
            return;
        }
        int featureMask = 1 << featureId;
        if ((getFeatures() & featureMask) == 0 && !fromResume) {
            return;
        }
        Drawable drawable = null;
        if (st != null) {
            drawable = st.child;
            if (drawable == null) {
                drawable = st.local;
            }
            if (drawable == null) {
                drawable = st.def;
            }
        }
        if ((getLocalFeatures() & featureMask) == 0) {
            if (getContainer() != null) {
                if (isActive() || fromResume) {
                    getContainer().setChildDrawable(featureId, drawable);
                    return;
                }
                return;
            }
            return;
        }
        if (st != null) {
            if (st.cur != drawable || st.curAlpha != st.alpha) {
                st.cur = drawable;
                st.curAlpha = st.alpha;
                onDrawableChanged(featureId, drawable, st.alpha);
            }
        }
    }

    private void updateInt(int featureId, int value, boolean fromResume) {
        if (this.mContentParent == null) {
            return;
        }
        int featureMask = 1 << featureId;
        if ((getFeatures() & featureMask) == 0 && !fromResume) {
            return;
        }
        if ((getLocalFeatures() & featureMask) == 0) {
            if (getContainer() != null) {
                getContainer().setChildInt(featureId, value);
                return;
            }
            return;
        }
        onIntChanged(featureId, value);
    }

    private ImageView getLeftIconView() {
        if (this.mLeftIconView != null) {
            return this.mLeftIconView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        ImageView imageView = (ImageView) findViewById(R.id.left_icon);
        this.mLeftIconView = imageView;
        return imageView;
    }

    @Override // android.view.Window
    protected void dispatchWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        super.dispatchWindowAttributesChanged(attrs);
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, true);
        }
    }

    private ProgressBar getCircularProgressBar(boolean shouldInstallDecor) {
        if (this.mCircularProgressBar != null) {
            return this.mCircularProgressBar;
        }
        if (this.mContentParent == null && shouldInstallDecor) {
            installDecor();
        }
        this.mCircularProgressBar = (ProgressBar) findViewById(R.id.progress_circular);
        if (this.mCircularProgressBar != null) {
            this.mCircularProgressBar.setVisibility(4);
        }
        return this.mCircularProgressBar;
    }

    private ProgressBar getHorizontalProgressBar(boolean shouldInstallDecor) {
        if (this.mHorizontalProgressBar != null) {
            return this.mHorizontalProgressBar;
        }
        if (this.mContentParent == null && shouldInstallDecor) {
            installDecor();
        }
        this.mHorizontalProgressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        if (this.mHorizontalProgressBar != null) {
            this.mHorizontalProgressBar.setVisibility(4);
        }
        return this.mHorizontalProgressBar;
    }

    private ImageView getRightIconView() {
        if (this.mRightIconView != null) {
            return this.mRightIconView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        ImageView imageView = (ImageView) findViewById(R.id.right_icon);
        this.mRightIconView = imageView;
        return imageView;
    }

    public void callOnPanelClosed(int featureId, PanelFeatureState panel, Menu menu) {
        Window.Callback cb = getCallback();
        if (cb == null) {
            return;
        }
        if (menu == null) {
            if (panel == null && featureId >= 0 && featureId < this.mPanels.length) {
                panel = this.mPanels[featureId];
            }
            if (panel != null) {
                menu = panel.menu;
            }
        }
        if ((panel == null || panel.isOpen) && !isDestroyed()) {
            cb.onPanelClosed(featureId, menu);
        }
    }

    private boolean isTvUserSetupComplete() {
        boolean isTvSetupComplete =
                Settings.Secure.getInt(
                                getContext().getContentResolver(),
                                Settings.Secure.USER_SETUP_COMPLETE,
                                0)
                        != 0;
        return isTvSetupComplete
                & (Settings.Secure.getInt(
                                getContext().getContentResolver(),
                                Settings.Secure.TV_USER_SETUP_COMPLETE,
                                0)
                        != 0);
    }

    private boolean launchDefaultSearch(KeyEvent event) {
        boolean result;
        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK)
                && !isTvUserSetupComplete()) {
            return false;
        }
        Window.Callback cb = getCallback();
        if (cb == null || isDestroyed()) {
            result = false;
        } else {
            int deviceId = event.getDeviceId();
            SearchEvent searchEvent = null;
            if (deviceId != 0) {
                searchEvent = new SearchEvent(InputDevice.getDevice(deviceId));
            }
            try {
                result = cb.onSearchRequested(searchEvent);
            } catch (AbstractMethodError e) {
                Log.e(
                        TAG,
                        "WindowCallback "
                                + cb.getClass().getName()
                                + " does not implement method onSearchRequested(SearchEvent); fa",
                        e);
                result = cb.onSearchRequested();
            }
        }
        if (!result && (getContext().getResources().getConfiguration().uiMode & 15) == 4) {
            Bundle args = new Bundle();
            args.putInt(Intent.EXTRA_ASSIST_INPUT_DEVICE_ID, event.getDeviceId());
            args.putLong(Intent.EXTRA_TIME, event.getEventTime());
            ((SearchManager) getContext().getSystemService("search")).launchAssist(args);
            return true;
        }
        return result;
    }

    @Override // android.view.Window
    public void setVolumeControlStream(int streamType) {
        this.mVolumeControlStreamType = streamType;
    }

    @Override // android.view.Window
    public int getVolumeControlStream() {
        return this.mVolumeControlStreamType;
    }

    @Override // android.view.Window
    public void setMediaController(MediaController controller) {
        this.mMediaController = controller;
        if (controller != null && this.mOnModeChangedListener == null) {
            this.mAudioMode = getAudioManager().getMode();
            this.mOnModeChangedListener =
                    new AudioManager
                            .OnModeChangedListener() { // from class:
                                                       // com.android.internal.policy.PhoneWindow$$ExternalSyntheticLambda0
                        @Override // android.media.AudioManager.OnModeChangedListener
                        public final void onModeChanged(int i) {
                            PhoneWindow.this.lambda$setMediaController$1(i);
                        }
                    };
            getAudioManager()
                    .addOnModeChangedListener(
                            getContext().getMainExecutor(), this.mOnModeChangedListener);
        } else if (this.mOnModeChangedListener != null && controller == null) {
            getAudioManager().removeOnModeChangedListener(this.mOnModeChangedListener);
            this.mOnModeChangedListener = null;
        }
    }

    public /* synthetic */ void lambda$setMediaController$1(int mode) {
        this.mAudioMode = mode;
    }

    @Override // android.view.Window
    public MediaController getMediaController() {
        return this.mMediaController;
    }

    @Override // android.view.Window
    public void setEnterTransition(Transition enterTransition) {
        this.mEnterTransition = enterTransition;
    }

    @Override // android.view.Window
    public void setReturnTransition(Transition transition) {
        this.mReturnTransition = transition;
    }

    @Override // android.view.Window
    public void setExitTransition(Transition exitTransition) {
        this.mExitTransition = exitTransition;
    }

    @Override // android.view.Window
    public void setReenterTransition(Transition transition) {
        this.mReenterTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementEnterTransition(Transition sharedElementEnterTransition) {
        this.mSharedElementEnterTransition = sharedElementEnterTransition;
    }

    @Override // android.view.Window
    public void setSharedElementReturnTransition(Transition transition) {
        this.mSharedElementReturnTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementExitTransition(Transition sharedElementExitTransition) {
        this.mSharedElementExitTransition = sharedElementExitTransition;
    }

    @Override // android.view.Window
    public void setSharedElementReenterTransition(Transition transition) {
        this.mSharedElementReenterTransition = transition;
    }

    @Override // android.view.Window
    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    @Override // android.view.Window
    public Transition getReturnTransition() {
        return this.mReturnTransition == USE_DEFAULT_TRANSITION
                ? getEnterTransition()
                : this.mReturnTransition;
    }

    @Override // android.view.Window
    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    @Override // android.view.Window
    public Transition getReenterTransition() {
        return this.mReenterTransition == USE_DEFAULT_TRANSITION
                ? getExitTransition()
                : this.mReenterTransition;
    }

    @Override // android.view.Window
    public Transition getSharedElementEnterTransition() {
        return this.mSharedElementEnterTransition;
    }

    @Override // android.view.Window
    public Transition getSharedElementReturnTransition() {
        return this.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION
                ? getSharedElementEnterTransition()
                : this.mSharedElementReturnTransition;
    }

    @Override // android.view.Window
    public Transition getSharedElementExitTransition() {
        return this.mSharedElementExitTransition;
    }

    @Override // android.view.Window
    public Transition getSharedElementReenterTransition() {
        return this.mSharedElementReenterTransition == USE_DEFAULT_TRANSITION
                ? getSharedElementExitTransition()
                : this.mSharedElementReenterTransition;
    }

    @Override // android.view.Window
    public void setAllowEnterTransitionOverlap(boolean allow) {
        this.mAllowEnterTransitionOverlap = Boolean.valueOf(allow);
    }

    @Override // android.view.Window
    public boolean getAllowEnterTransitionOverlap() {
        if (this.mAllowEnterTransitionOverlap == null) {
            return true;
        }
        return this.mAllowEnterTransitionOverlap.booleanValue();
    }

    @Override // android.view.Window
    public void setAllowReturnTransitionOverlap(boolean allowExitTransitionOverlap) {
        this.mAllowReturnTransitionOverlap = Boolean.valueOf(allowExitTransitionOverlap);
    }

    @Override // android.view.Window
    public boolean getAllowReturnTransitionOverlap() {
        if (this.mAllowReturnTransitionOverlap == null) {
            return true;
        }
        return this.mAllowReturnTransitionOverlap.booleanValue();
    }

    @Override // android.view.Window
    public long getTransitionBackgroundFadeDuration() {
        if (this.mBackgroundFadeDurationMillis < 0) {
            return 300L;
        }
        return this.mBackgroundFadeDurationMillis;
    }

    @Override // android.view.Window
    public void setTransitionBackgroundFadeDuration(long fadeDurationMillis) {
        if (fadeDurationMillis < 0) {
            throw new IllegalArgumentException("negative durations are not allowed");
        }
        this.mBackgroundFadeDurationMillis = fadeDurationMillis;
    }

    @Override // android.view.Window
    public void setSharedElementsUseOverlay(boolean sharedElementsUseOverlay) {
        this.mSharedElementsUseOverlay = Boolean.valueOf(sharedElementsUseOverlay);
    }

    @Override // android.view.Window
    public boolean getSharedElementsUseOverlay() {
        if (this.mSharedElementsUseOverlay == null) {
            return true;
        }
        return this.mSharedElementsUseOverlay.booleanValue();
    }

    private static final class DrawableFeatureState {
        Drawable child;
        Drawable cur;
        Drawable def;
        final int featureId;
        Drawable local;
        int resid;
        Uri uri;
        int alpha = 255;
        int curAlpha = 255;

        DrawableFeatureState(int _featureId) {
            this.featureId = _featureId;
        }
    }

    static final class PanelFeatureState {
        int background;
        View createdPanelView;
        DecorView decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int fullBackground;
        int gravity;
        IconMenuPresenter iconMenuPresenter;
        boolean isCompact;
        boolean isHandled;
        boolean isInExpandedMode;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        int listPresenterTheme;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastExpanded;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int featureId) {
            this.featureId = featureId;
        }

        public boolean isInListMode() {
            return this.isInExpandedMode || this.isCompact;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            return (this.isCompact || this.isInExpandedMode)
                    ? this.listMenuPresenter.getAdapter().getCount() > 0
                    : ((ViewGroup) this.shownPanelView).getChildCount() > 0;
        }

        public void clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.iconMenuPresenter = null;
            this.listMenuPresenter = null;
        }

        void setStyle(Context context) {
            TypedArray a = context.obtainStyledAttributes(R.styleable.Theme);
            this.background = a.getResourceId(46, 0);
            this.fullBackground = a.getResourceId(47, 0);
            this.windowAnimations = a.getResourceId(93, 0);
            this.isCompact = a.getBoolean(397, false);
            this.listPresenterTheme = a.getResourceId(398, R.style.Theme_ExpandedMenu);
            a.recycle();
        }

        void setMenu(MenuBuilder menu) {
            if (menu == this.menu) {
                return;
            }
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menu;
            if (menu != null) {
                if (this.iconMenuPresenter != null) {
                    menu.addMenuPresenter(this.iconMenuPresenter);
                }
                if (this.listMenuPresenter != null) {
                    menu.addMenuPresenter(this.listMenuPresenter);
                }
            }
        }

        MenuView getListMenuView(Context context, MenuPresenter.Callback cb) {
            if (this.menu == null) {
                return null;
            }
            if (!this.isCompact) {
                getIconMenuView(context, cb);
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter =
                        new ListMenuPresenter(
                                R.layout.list_menu_item_layout, this.listPresenterTheme);
                this.listMenuPresenter.setCallback(cb);
                this.listMenuPresenter.setId(R.id.list_menu_presenter);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            if (this.iconMenuPresenter != null) {
                this.listMenuPresenter.setItemIndexOffset(
                        this.iconMenuPresenter.getNumActualItemsShown());
            }
            MenuView result = this.listMenuPresenter.getMenuView(this.decorView);
            return result;
        }

        MenuView getIconMenuView(Context context, MenuPresenter.Callback cb) {
            if (this.menu == null) {
                return null;
            }
            if (this.iconMenuPresenter == null) {
                this.iconMenuPresenter = new IconMenuPresenter(context);
                this.iconMenuPresenter.setCallback(cb);
                this.iconMenuPresenter.setId(R.id.icon_menu_presenter);
                this.menu.addMenuPresenter(this.iconMenuPresenter);
            }
            MenuView result = this.iconMenuPresenter.getMenuView(this.decorView);
            return result;
        }

        Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            savedState.isInExpandedMode = this.isInExpandedMode;
            if (this.menu != null) {
                savedState.menuState = new Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        void onRestoreInstanceState(Parcelable state) {
            SavedState savedState = (SavedState) state;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.wasLastExpanded = savedState.isInExpandedMode;
            this.frozenMenuState = savedState.menuState;
            this.createdPanelView = null;
            this.shownPanelView = null;
            this.decorView = null;
        }

        void applyFrozenState() {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }

        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR =
                    new Parcelable.Creator<
                            SavedState>() { // from class:
                                            // com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState.1
                        @Override // android.os.Parcelable.Creator
                        public SavedState createFromParcel(Parcel in) {
                            return SavedState.readFromParcel(in);
                        }

                        @Override // android.os.Parcelable.Creator
                        public SavedState[] newArray(int size) {
                            return new SavedState[size];
                        }
                    };
            int featureId;
            boolean isInExpandedMode;
            boolean isOpen;
            Bundle menuState;

            private SavedState() {}

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.featureId);
                parcel.writeInt(this.isOpen ? 1 : 0);
                parcel.writeInt(this.isInExpandedMode ? 1 : 0);
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState);
                }
            }

            public static SavedState readFromParcel(Parcel source) {
                SavedState savedState = new SavedState();
                savedState.featureId = source.readInt();
                savedState.isOpen = source.readInt() == 1;
                savedState.isInExpandedMode = source.readInt() == 1;
                if (savedState.isOpen) {
                    savedState.menuState = source.readBundle();
                }
                return savedState;
            }
        }
    }

    static class RotationWatcher extends IRotationWatcher.Stub {
        private Handler mHandler;
        private boolean mIsWatching;
        private final Runnable mRotationChanged =
                new Runnable() { // from class:
                                 // com.android.internal.policy.PhoneWindow.RotationWatcher.1
                    @Override // java.lang.Runnable
                    public void run() {
                        RotationWatcher.this.dispatchRotationChanged();
                    }
                };
        private final ArrayList<WeakReference<PhoneWindow>> mWindows = new ArrayList<>();

        RotationWatcher() {}

        @Override // android.view.IRotationWatcher
        public void onRotationChanged(int rotation) throws RemoteException {
            this.mHandler.post(this.mRotationChanged);
        }

        public void addWindow(PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                if (!this.mIsWatching) {
                    try {
                        WindowManagerHolder.sWindowManager.watchRotation(
                                this, phoneWindow.getContext().getDisplayId());
                        this.mHandler = new Handler();
                        this.mIsWatching = true;
                    } catch (RemoteException ex) {
                        Log.e(PhoneWindow.TAG, "Couldn't start watching for device rotation", ex);
                    }
                }
                this.mWindows.add(new WeakReference<>(phoneWindow));
            }
        }

        public void removeWindow(PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    WeakReference<PhoneWindow> ref = this.mWindows.get(i);
                    PhoneWindow win = ref.get();
                    if (win != null && win != phoneWindow) {
                        i++;
                    }
                    this.mWindows.remove(i);
                }
            }
        }

        void dispatchRotationChanged() {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    WeakReference<PhoneWindow> ref = this.mWindows.get(i);
                    PhoneWindow win = ref.get();
                    if (win != null) {
                        win.onOptionsPanelRotationChanged();
                        i++;
                    } else {
                        this.mWindows.remove(i);
                    }
                }
            }
        }
    }

    public static final class PhoneWindowMenuCallback
            implements MenuBuilder.Callback, MenuPresenter.Callback {
        private static final int FEATURE_ID = 6;
        private boolean mShowDialogForSubmenu;
        private MenuDialogHelper mSubMenuHelper;
        private final PhoneWindow mWindow;

        public PhoneWindowMenuCallback(PhoneWindow window) {
            this.mWindow = window;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            if (menu.getRootMenu() != menu) {
                onCloseSubMenu(menu);
            }
            if (allMenusAreClosing) {
                Window.Callback callback = this.mWindow.getCallback();
                if (callback != null && !this.mWindow.isDestroyed()) {
                    callback.onPanelClosed(6, menu);
                }
                if (menu == this.mWindow.mContextMenu) {
                    this.mWindow.dismissContextMenu();
                }
                if (this.mSubMenuHelper != null) {
                    this.mSubMenuHelper.dismiss();
                    this.mSubMenuHelper = null;
                }
            }
        }

        private void onCloseSubMenu(MenuBuilder menu) {
            Window.Callback callback = this.mWindow.getCallback();
            if (callback != null && !this.mWindow.isDestroyed()) {
                callback.onPanelClosed(6, menu.getRootMenu());
            }
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            Window.Callback callback = this.mWindow.getCallback();
            return (callback == null
                            || this.mWindow.isDestroyed()
                            || !callback.onMenuItemSelected(6, item))
                    ? false
                    : true;
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menu) {}

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            if (subMenu == null) {
                return false;
            }
            subMenu.setCallback(this);
            if (!this.mShowDialogForSubmenu) {
                return false;
            }
            this.mSubMenuHelper = new MenuDialogHelper(subMenu);
            this.mSubMenuHelper.show(null);
            return true;
        }

        public void setShowDialogForSubmenu(boolean enabled) {
            this.mShowDialogForSubmenu = enabled;
        }
    }

    int getLocalFeaturesPrivate() {
        return super.getLocalFeatures();
    }

    @Override // android.view.Window
    protected void setDefaultWindowFormat(int format) {
        super.setDefaultWindowFormat(format);
    }

    void sendCloseSystemWindows() {
        sendCloseSystemWindows(getContext(), null);
    }

    void sendCloseSystemWindows(String reason) {
        sendCloseSystemWindows(getContext(), reason);
    }

    public static void sendCloseSystemWindows(Context context, String reason) {
        if (ActivityManager.isSystemReady()) {
            try {
                ActivityManager.getService().closeSystemDialogs(reason);
            } catch (RemoteException e) {
            }
        }
    }

    public static void sendCloseSystemWindowsInDisplay(String reason, int displayId) {
        if (ActivityManager.isSystemReady()) {
            try {
                if (displayId != -1) {
                    ActivityManager.getService().closeSystemDialogsInDisplay(reason, displayId);
                } else {
                    ActivityManager.getService().closeSystemDialogs(reason);
                }
            } catch (RemoteException e) {
            }
        }
    }

    @Override // android.view.Window
    public int getStatusBarColor() {
        return this.mStatusBarColor;
    }

    @Override // android.view.Window
    public void setStatusBarColor(int color) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        if (this.mStatusBarColor == color && this.mForcedStatusBarColor) {
            return;
        }
        this.mStatusBarColor = color;
        this.mForcedStatusBarColor = true;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
        Window.WindowControllerCallback callback = getWindowControllerCallback();
        if (callback != null) {
            getWindowControllerCallback().updateStatusBarColor(color);
        }
    }

    @Override // android.view.Window
    public int getNavigationBarColor() {
        if (this.mEdgeToEdgeEnforced) {
            return 0;
        }
        return this.mNavigationBarColor;
    }

    @Override // android.view.Window
    public void setNavigationBarColor(int color) {
        if (this.mNavigationBarColor == color && this.mForcedNavigationBarColor) {
            return;
        }
        this.mNavigationBarColor = color;
        this.mForcedNavigationBarColor = true;
        this.mNavigationBarColorSpecified = true;
        if (this.mDecor != null) {
            this.mDecor.getWindowInsetsController().setSystemBarsAppearance(0, 512);
            this.mDecor.updateColorViews(null, false);
            updateForceLightNavigationBar();
        }
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        Window.WindowControllerCallback callback = getWindowControllerCallback();
        if (callback != null) {
            getWindowControllerCallback().updateNavigationBarColor(color);
        }
    }

    @Override // android.view.Window
    public void setNavigationBarDividerColor(int navigationBarDividerColor) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        this.mNavigationBarDividerColor = navigationBarDividerColor;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public int getNavigationBarDividerColor() {
        return this.mNavigationBarDividerColor;
    }

    @Override // android.view.Window
    public void setStatusBarContrastEnforced(boolean ensureContrast) {
        this.mEnsureStatusBarContrastWhenTransparent = ensureContrast;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public boolean isStatusBarContrastEnforced() {
        return this.mEnsureStatusBarContrastWhenTransparent;
    }

    @Override // android.view.Window
    public void setNavigationBarContrastEnforced(boolean enforceContrast) {
        this.mEnsureNavigationBarContrastWhenTransparent = enforceContrast;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public boolean isNavigationBarContrastEnforced() {
        return this.mEnsureNavigationBarContrastWhenTransparent;
    }

    public void setIsStartingWindow(boolean isStartingWindow) {
        this.mIsStartingWindow = isStartingWindow;
    }

    @Override // android.view.Window
    public void setTheme(int resid) {
        this.mTheme = resid;
        if (this.mDecor != null) {
            Context context = this.mDecor.getContext();
            if (context instanceof DecorContext) {
                context.setTheme(resid);
            }
        }
    }

    @Override // android.view.Window
    public void setResizingCaptionDrawable(Drawable drawable) {}

    @Override // android.view.Window
    public void setDecorCaptionShade(int decorCaptionShade) {}

    @Override // android.view.Window
    public void setAttributes(WindowManager.LayoutParams params) {
        super.setAttributes(params);
        if (this.mDecor != null) {
            this.mDecor.updateLogTag(params);
        }
    }

    @Override // android.view.Window
    public WindowInsetsController getInsetsController() {
        return this.mDecor.getWindowInsetsController();
    }

    @Override // android.view.Window
    public void setSystemGestureExclusionRects(List<Rect> rects) {
        getViewRootImpl().setRootSystemGestureExclusionRects(rects);
    }

    @Override // android.view.Window
    public List<Rect> getSystemGestureExclusionRects() {
        return getViewRootImpl().getRootSystemGestureExclusionRects();
    }

    @Override // android.view.Window
    public void setDecorFitsSystemWindows(boolean decorFitsSystemWindows) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        this.mDecorFitsSystemWindows = decorFitsSystemWindows;
        applyDecorFitsSystemWindows();
    }

    @Override // android.view.Window
    public boolean decorFitsSystemWindows() {
        return this.mDecorFitsSystemWindows;
    }

    private void applyDecorFitsSystemWindows() {
        Window.OnContentApplyWindowInsetsListener onContentApplyWindowInsetsListener;
        ViewRootImpl impl = getViewRootImplOrNull();
        if (impl != null) {
            if (this.mDecorFitsSystemWindows) {
                onContentApplyWindowInsetsListener = sDefaultContentInsetsApplier;
            } else {
                onContentApplyWindowInsetsListener = null;
            }
            impl.setOnContentApplyWindowInsetsListener(onContentApplyWindowInsetsListener);
        }
    }

    @Override // android.view.Window
    public void requestScrollCapture(IScrollCaptureResponseListener listener) {
        getViewRootImpl().dispatchScrollCaptureRequest(listener);
    }

    @Override // android.view.Window
    public void registerScrollCaptureCallback(ScrollCaptureCallback callback) {
        getViewRootImpl().addScrollCaptureCallback(callback);
    }

    @Override // android.view.Window
    public void unregisterScrollCaptureCallback(ScrollCaptureCallback callback) {
        getViewRootImpl().removeScrollCaptureCallback(callback);
    }

    @Override // android.view.Window
    public View getStatusBarBackgroundView() {
        if (this.mDecor != null) {
            return this.mDecor.getStatusBarBackgroundView();
        }
        return null;
    }

    @Override // android.view.Window
    public View getNavigationBarBackgroundView() {
        if (this.mDecor != null) {
            return this.mDecor.getNavigationBarBackgroundView();
        }
        return null;
    }

    @Override // android.view.Window
    public AttachedSurfaceControl getRootSurfaceControl() {
        return getViewRootImplOrNull();
    }

    @Override // android.view.Window
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mProxyOnBackInvokedDispatcher;
    }

    public void setSettingsNavigationBarColor(int color) {
        this.mSettingsNavigationBarColor = color;
    }

    public void updateDefaultNavigationBarColor() {
        int defaultColor = getDefaultNavigationBarColor();
        boolean needUpdate =
                (this.mNavigationBarColor != this.mDefaultNavigationBarColor
                                || this.mNavigationBarColor == defaultColor
                                || this.mForcedNavigationBarColor)
                        ? false
                        : true;
        this.mDefaultNavigationBarColor = defaultColor;
        if (needUpdate) {
            this.mNavigationBarColor = defaultColor;
            if (this.mDecor != null) {
                this.mDecor.updateColorViews(null, false);
            }
        }
        updateForceLightNavigationBar();
    }

    private int getDefaultNavigationBarColor() {
        if (this.mThemeApplied) {
            boolean useThemeDefault =
                    Settings.Global.getInt(
                                    getContext().getContentResolver(),
                                    "navigationbar_use_theme_default",
                                    0)
                            != 0;
            if (useThemeDefault) {
                return getContext().getResources().getColor(R.color.tw_navigation_bar_color);
            }
        }
        boolean useThemeDefault2 = isNightMode();
        if (useThemeDefault2) {
            return this.mDeviceDefaultNavigationBarColor;
        }
        return this.mSettingsNavigationBarColor;
    }

    private boolean isNightMode() {
        return (getContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public void dumpColors(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String innerPrefix = prefix + "  ";
        writer.print(prefix);
        writer.println("PhoneWindow:");
        writer.print(innerPrefix);
        writer.print("mStatusBarColor=0x");
        writer.print(Integer.toHexString(this.mStatusBarColor).toUpperCase());
        writer.print(" mForcedStatusBarColor=");
        writer.println(this.mForcedStatusBarColor);
        writer.print(innerPrefix);
        writer.print("mNavigationBarColor=0x");
        writer.print(Integer.toHexString(this.mNavigationBarColor).toUpperCase());
        writer.print(" mForcedNavigationBarColor=");
        writer.println(this.mForcedNavigationBarColor);
        writer.print(innerPrefix);
        writer.print("mDeviceDefaultNavigationBarColor=0x");
        writer.println(Integer.toHexString(this.mDeviceDefaultNavigationBarColor).toUpperCase());
        writer.print(innerPrefix);
        writer.print("mDefaultNavigationBarColor=0x");
        writer.println(Integer.toHexString(this.mDefaultNavigationBarColor).toUpperCase());
        writer.print(innerPrefix);
        writer.print("mSettingsNavigationBarColor=0x");
        writer.println(Integer.toHexString(this.mSettingsNavigationBarColor).toUpperCase());
        writer.print(innerPrefix);
        writer.print("mThemeApplied=");
        writer.println(this.mThemeApplied);
    }

    public int getDeviceDefaultNavigationBarColor() {
        return this.mDeviceDefaultNavigationBarColor;
    }

    void updateDeviceDefaultNavigationBarColor() {
        this.mDeviceDefaultNavigationBarColor =
                getContext().getResources().getColor(R.color.navbar_light_theme_color, null);
    }

    private boolean needLightNavigationBar(int color) {
        if (this.mThemeApplied) {
            boolean useThemeDefault =
                    Settings.Global.getInt(
                                    getContext().getContentResolver(),
                                    "navigationbar_use_theme_default",
                                    0)
                            != 0;
            if (useThemeDefault) {
                boolean needLight =
                        getContext()
                                .getResources()
                                .getBoolean(R.bool.sem_window_light_navigation_bar);
                if (!needLight) {
                    getDecorView()
                            .setSystemUiVisibility(getDecorView().getSystemUiVisibility() & (-17));
                }
                return needLight;
            }
        }
        float[] pixelHSV = new float[3];
        Color.colorToHSV(color, pixelHSV);
        return pixelHSV[1] < 0.3f && pixelHSV[2] >= 0.88f;
    }

    private void setForceLightNavigationBar(boolean enable) {
        WindowManager.LayoutParams attr = getAttributes();
        int oldValue = attr.samsungFlags;
        if (enable) {
            attr.samsungFlags |= 1048576;
        } else {
            attr.samsungFlags &= -1048577;
        }
        if (oldValue != attr.samsungFlags) {
            Log.d(
                    TAG,
                    "forceLight changed to "
                            + enable
                            + " ["
                            + ((Object) attr.getTitle())
                            + "] from "
                            + Debug.getCallers(5));
            setAttributes(attr);
            if (this.mDecor != null && this.mDecor.getViewRootImpl() != null) {
                this.mDecor.getViewRootImpl().requestRecomputeViewAttributes();
            }
        }
    }

    void updateForceLightNavigationBar() {
        boolean enable;
        if (this.mDecor != null && this.mDecor.isDrawLegacyNavigationBarBackground()) {
            enable = !isNightMode();
        } else if (this.mNavigationBarColor == this.mDeviceDefaultNavigationBarColor) {
            enable = !isNightMode();
        } else if (this.mNavigationBarColor == this.mDefaultNavigationBarColor) {
            enable = needLightNavigationBar(this.mNavigationBarColor);
        } else {
            enable = false;
        }
        setForceLightNavigationBar(enable);
    }

    public void setActivityCurrentConfig(Configuration config) {
        int oldWindowingMode;
        boolean split = false;
        if (this.mActivityCurrentConfig != null) {
            oldWindowingMode = this.mActivityCurrentConfig.windowConfiguration.getWindowingMode();
        } else {
            oldWindowingMode = 0;
        }
        this.mActivityCurrentConfig = config;
        if (this.mDecor != null
                && oldWindowingMode != config.windowConfiguration.getWindowingMode()) {
            if (config.windowConfiguration.getWindowingMode() == 6
                    && WindowConfiguration.isSplitScreenWindowingMode(config.windowConfiguration)) {
                split = true;
            }
            this.mDecor.onWindowingModeChanged(
                    config.windowConfiguration.getWindowingMode(), split);
        }
    }
}
