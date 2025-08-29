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
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.IconMenuPresenter;
import com.android.internal.view.menu.ListMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.widget.DecorContentParent;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class PhoneWindow extends Window implements MenuBuilder.Callback {
    private static final String ACTION_BAR_TAG = "android:ActionBar";
    private static final int CUSTOM_TITLE_COMPATIBLE_FEATURES = 13505;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKGROUND_FADE_DURATION_MS = 300;
    private static final long DISABLE_OPT_OUT_EDGE_TO_EDGE = 377864165;
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
    private boolean mIsStartingWindow;
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
    private static final Window.OnContentApplyWindowInsetsListener sDefaultContentInsetsApplier = new Window.OnContentApplyWindowInsetsListener() { // from class: com.android.internal.policy.PhoneWindow$$ExternalSyntheticLambda1
        @Override // android.view.Window.OnContentApplyWindowInsetsListener
        public final Pair onContentApplyWindowInsets(View view, WindowInsets windowInsets) {
            return PhoneWindow.lambda$static$0(view, windowInsets);
        }
    };
    private static final Transition USE_DEFAULT_TRANSITION = new TransitionSet();
    private static final int ENFORCE_EDGE_TO_EDGE_SDK_VERSION = SystemProperties.getInt("persist.wm.debug.default_e2e_since_sdk", Integer.MAX_VALUE);
    static final RotationWatcher sRotationWatcher = new RotationWatcher();
    private static boolean sIsEdgeToEdgeQueried = false;
    private static boolean sIsEdgeToEdgeDisabled = false;

    @Override // android.view.Window
    protected void onActive() {
    }

    @Override // android.view.Window
    public void setDecorCaptionShade(int i) {
    }

    @Override // android.view.Window
    public void setResizingCaptionDrawable(Drawable drawable) {
    }

    static /* synthetic */ Pair lambda$static$0(View view, WindowInsets windowInsets) {
        if ((view.getWindowSystemUiVisibility() & 1536) != 0) {
            return new Pair(Insets.NONE, windowInsets);
        }
        Insets systemWindowInsets = windowInsets.getSystemWindowInsets();
        return new Pair(systemWindowInsets, windowInsets.inset(systemWindowInsets).consumeSystemWindowInsets());
    }

    static class WindowManagerHolder {
        static final IWindowManager sWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));

        WindowManagerHolder() {
        }
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
        this.mInvalidatePanelMenuRunnable = new Runnable() { // from class: com.android.internal.policy.PhoneWindow.1
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
        Transition transition = USE_DEFAULT_TRANSITION;
        this.mReturnTransition = transition;
        this.mExitTransition = null;
        this.mReenterTransition = transition;
        this.mSharedElementEnterTransition = null;
        this.mSharedElementReturnTransition = transition;
        this.mSharedElementExitTransition = null;
        this.mSharedElementReenterTransition = transition;
        this.mBackgroundFadeDurationMillis = -1L;
        this.mTheme = -1;
        this.mUseDecorContext = false;
        this.mDecorFitsSystemWindows = true;
        this.mActivityCurrentConfig = null;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRenderShadowsInCompositor = Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_RENDER_SHADOWS_IN_COMPOSITOR, 1) != 0;
        this.mProxyOnBackInvokedDispatcher = new ProxyOnBackInvokedDispatcher(context);
        this.mAllowFloatingWindowsFillScreen = Boolean.valueOf(context.getResources().getBoolean(R.bool.config_allowFloatingWindowsFillScreen));
        updateDeviceDefaultNavigationBarColor();
        int i = this.mDeviceDefaultNavigationBarColor;
        this.mDefaultNavigationBarColor = i;
        this.mSettingsNavigationBarColor = i;
    }

    public PhoneWindow(Context context, Window window, ViewRootImpl.ActivityConfigCallback activityConfigCallback) {
        this(context);
        boolean z = true;
        this.mUseDecorContext = true;
        if (window != null) {
            this.mDecor = (DecorView) window.getDecorView();
            this.mElevation = window.getElevation();
            this.mLoadElevation = false;
            this.mForceDecorInstall = true;
            this.mDecorFitsSystemWindows = window.decorFitsSystemWindows();
            setSystemBarAppearance(window.getSystemBarAppearance());
            getAttributes().token = window.getAttributes().token;
            ViewRootImpl viewRootImpl = this.mDecor.getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.getOnBackInvokedDispatcher().clear();
                if (Flags.clearSystemVibrator()) {
                    viewRootImpl.clearSystemVibrator();
                }
                onViewRootImplSet(viewRootImpl);
            }
            setPreserved(true);
        }
        if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES, 0) == 0 && !context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            z = false;
        }
        this.mSupportsPictureInPicture = z;
        this.mActivityConfigCallback = activityConfigCallback;
    }

    public static boolean isEdgeToEdgeEnforced(ApplicationInfo applicationInfo, boolean z, TypedArray typedArray) {
        if (isOptingOutEdgeToEdgeEnforcement(applicationInfo, z, typedArray)) {
            return false;
        }
        if (applicationInfo.targetSdkVersion < ENFORCE_EDGE_TO_EDGE_SDK_VERSION) {
            if (!Flags.enforceEdgeToEdge()) {
                return false;
            }
            if (z) {
                if (!CompatChanges.isChangeEnabled(ENFORCE_EDGE_TO_EDGE)) {
                    return false;
                }
            } else if (!applicationInfo.isChangeEnabled(ENFORCE_EDGE_TO_EDGE)) {
                return false;
            }
        }
        return (CoreRune.FW_FORCE_OPT_OUT_EDGE_TO_EDGE && isEdgeToEdgeDisabled(applicationInfo.packageName)) ? false : true;
    }

    private static boolean isEdgeToEdgeDisabled(String str) {
        if (!sIsEdgeToEdgeQueried) {
            try {
                sIsEdgeToEdgeDisabled = WindowManagerHolder.sWindowManager.isEdgeToEdgeDisabled(str);
                sIsEdgeToEdgeQueried = true;
            } catch (RemoteException e) {
                Log.e(TAG, "Couldn't get isEdgeToEdgeDisabled", e);
            }
        }
        return sIsEdgeToEdgeDisabled;
    }

    public static boolean isOptOutEdgeToEdgeEnabled(ApplicationInfo applicationInfo, boolean z) {
        return !(Flags.disableOptOutEdgeToEdge() && (!z ? !applicationInfo.isChangeEnabled(DISABLE_OPT_OUT_EDGE_TO_EDGE) : !CompatChanges.isChangeEnabled(DISABLE_OPT_OUT_EDGE_TO_EDGE)));
    }

    public static boolean isOptingOutEdgeToEdgeEnforcement(ApplicationInfo applicationInfo, boolean z, TypedArray typedArray) {
        return isOptOutEdgeToEdgeEnabled(applicationInfo, z) && typedArray.getBoolean(63, false);
    }

    @Override // android.view.Window
    public final void setContainer(Window window) {
        super.setContainer(window);
    }

    @Override // android.view.Window
    public boolean requestFeature(int i) {
        if (this.mContentParentExplicitlySet) {
            throw new AndroidRuntimeException("requestFeature() must be called before adding content");
        }
        int features = getFeatures();
        int i2 = (1 << i) | features;
        if ((i2 & 128) != 0 && (i2 & (-13506)) != 0) {
            throw new AndroidRuntimeException("You cannot combine custom titles with other title features");
        }
        if ((features & 2) != 0 && i == 8) {
            return false;
        }
        if ((features & 256) != 0 && i == 1) {
            removeFeature(8);
        }
        if (i == 5 && getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            throw new AndroidRuntimeException("You cannot use indeterminate progress on a watch.");
        }
        return super.requestFeature(i);
    }

    @Override // android.view.Window
    public void setUiOptions(int i) {
        this.mUiOptions = i;
    }

    @Override // android.view.Window
    public void setUiOptions(int i, int i2) {
        this.mUiOptions = (i & i2) | (this.mUiOptions & (~i2));
    }

    @Override // android.view.Window
    public TransitionManager getTransitionManager() {
        return this.mTransitionManager;
    }

    @Override // android.view.Window
    public void setTransitionManager(TransitionManager transitionManager) {
        this.mTransitionManager = transitionManager;
    }

    @Override // android.view.Window
    public Scene getContentScene() {
        return this.mContentScene;
    }

    @Override // android.view.Window
    public void setContentView(int i) throws Resources.NotFoundException {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            transitionTo(Scene.getSceneForLayout(this.mContentParent, i, getContext()));
        } else {
            this.mLayoutInflater.inflate(i, this.mContentParent);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback callback = getCallback();
        if (!isDestroyed()) {
            if (callback != null) {
                callback.onContentChanged();
            }
            DecorContentParent decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null) {
                decorContentParent.notifyContentChanged();
            }
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override // android.view.Window
    public void setContentView(View view) throws Resources.NotFoundException {
        setContentView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // android.view.Window
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) throws Resources.NotFoundException {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            view.setLayoutParams(layoutParams);
            transitionTo(new Scene(this.mContentParent, view));
        } else {
            this.mContentParent.addView(view, layoutParams);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback callback = getCallback();
        if (!isDestroyed()) {
            if (callback != null) {
                callback.onContentChanged();
            }
            DecorContentParent decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null) {
                decorContentParent.notifyContentChanged();
            }
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override // android.view.Window
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.mContentParent == null) {
            installDecor();
        }
        if (hasFeature(12)) {
            Log.v(TAG, "addContentView does not support content transitions");
        }
        this.mContentParent.addView(view, layoutParams);
        this.mContentParent.requestApplyInsets();
        Window.Callback callback = getCallback();
        if (isDestroyed()) {
            return;
        }
        if (callback != null) {
            callback.onContentChanged();
        }
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.notifyContentChanged();
        }
    }

    @Override // android.view.Window
    public void clearContentView() {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.clearContentView();
        }
    }

    private void transitionTo(Scene scene) throws Resources.NotFoundException {
        if (this.mContentScene == null) {
            scene.enter();
        } else {
            this.mTransitionManager.transitionTo(scene);
        }
        this.mContentScene = scene;
    }

    @Override // android.view.Window
    public View getCurrentFocus() {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            return decorView.findFocus();
        }
        return null;
    }

    @Override // android.view.Window
    public void takeSurface(SurfaceHolder.Callback2 callback2) {
        this.mTakeSurfaceCallback = callback2;
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
    public void setTitle(CharSequence charSequence) {
        setTitle(charSequence, true);
    }

    public void setTitle(CharSequence charSequence, boolean z) {
        ViewRootImpl viewRootImpl;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.lambda$setTextAsync$0(charSequence);
        } else {
            DecorContentParent decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null) {
                decorContentParent.setWindowTitle(charSequence);
            }
        }
        this.mTitle = charSequence;
        if (z) {
            WindowManager.LayoutParams attributes = getAttributes();
            if (TextUtils.equals(charSequence, attributes.accessibilityTitle)) {
                return;
            }
            attributes.accessibilityTitle = TextUtils.stringOrSpannedString(charSequence);
            DecorView decorView = this.mDecor;
            if (decorView != null && (viewRootImpl = decorView.getViewRootImpl()) != null) {
                viewRootImpl.onWindowTitleChanged();
            }
            dispatchWindowAttributesChanged(getAttributes());
        }
    }

    @Override // android.view.Window
    @Deprecated
    public void setTitleColor(int i) {
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setTextColor(i);
        }
        this.mTitleColor = i;
    }

    public final boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (isDestroyed()) {
            return false;
        }
        if (panelFeatureState.isPrepared) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
            closePanel(panelFeatureState2, false);
        }
        Window.Callback callback = getCallback();
        if (callback != null) {
            panelFeatureState.createdPanelView = callback.onCreatePanelView(panelFeatureState.featureId);
        }
        boolean z = panelFeatureState.featureId == 0 || panelFeatureState.featureId == 8;
        if (z && (decorContentParent3 = this.mDecorContentParent) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (panelFeatureState.createdPanelView == null) {
            if (panelFeatureState.menu == null || panelFeatureState.refreshMenuContent) {
                if (panelFeatureState.menu == null && (!initializePanelMenu(panelFeatureState) || panelFeatureState.menu == null)) {
                    return false;
                }
                if (z && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (callback == null || !callback.onCreatePanelMenu(panelFeatureState.featureId, panelFeatureState.menu)) {
                    panelFeatureState.setMenu(null);
                    if (z && (decorContentParent = this.mDecorContentParent) != null) {
                        decorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            if (panelFeatureState.frozenActionViewState != null) {
                panelFeatureState.menu.restoreActionViewStates(panelFeatureState.frozenActionViewState);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!callback.onPreparePanel(panelFeatureState.featureId, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                if (z && (decorContentParent2 = this.mDecorContentParent) != null) {
                    decorContentParent2.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.qwertyMode = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.menu.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    @Override // android.view.Window
    public void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        PanelFeatureState panelState;
        if (this.mDecorContentParent != null || (panelState = getPanelState(0, false)) == null || panelState.menu == null) {
            return;
        }
        if (panelState.isOpen) {
            Bundle bundle = new Bundle();
            if (panelState.iconMenuPresenter != null) {
                panelState.iconMenuPresenter.saveHierarchyState(bundle);
            }
            if (panelState.listMenuPresenter != null) {
                panelState.listMenuPresenter.saveHierarchyState(bundle);
            }
            clearMenuViews(panelState);
            reopenMenu(false);
            if (panelState.iconMenuPresenter != null) {
                panelState.iconMenuPresenter.restoreHierarchyState(bundle);
            }
            if (panelState.listMenuPresenter != null) {
                panelState.listMenuPresenter.restoreHierarchyState(bundle);
                return;
            }
            return;
        }
        clearMenuViews(panelState);
    }

    @Override // android.view.Window
    public void onMultiWindowModeChanged() throws Resources.NotFoundException {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.onConfigurationChanged(getContext().getResources().getConfiguration());
        }
    }

    @Override // android.view.Window
    public void onPictureInPictureModeChanged(boolean z) {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updatePictureInPictureOutlineProvider(z);
        }
    }

    private static void clearMenuViews(PanelFeatureState panelFeatureState) {
        panelFeatureState.createdPanelView = null;
        panelFeatureState.refreshDecorView = true;
        panelFeatureState.clearMenuPresenters();
    }

    @Override // android.view.Window
    public final void openPanel(int i, KeyEvent keyEvent) throws Resources.NotFoundException {
        DecorContentParent decorContentParent;
        if (i == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.showOverflowMenu();
        } else {
            openPanel(getPanelState(i, true), keyEvent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) throws Resources.NotFoundException {
        int i;
        int i2;
        ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.isOpen || isDestroyed()) {
            return;
        }
        if (panelFeatureState.featureId == 0) {
            Context context = getContext();
            boolean z = (context.getResources().getConfiguration().screenLayout & 15) == 4;
            boolean z2 = context.getApplicationInfo().targetSdkVersion >= 11;
            if (z && z2) {
                return;
            }
        }
        Window.Callback callback = getCallback();
        if (callback != null && !callback.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true);
            return;
        }
        WindowManager windowManager = getWindowManager();
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return;
        }
        int i3 = -1;
        if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) {
            if (panelFeatureState.decorView == null) {
                if (!initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) {
                    return;
                }
            } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                panelFeatureState.decorView.removeAllViews();
            }
            if (!initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) {
                return;
            }
            ViewGroup.LayoutParams layoutParams2 = panelFeatureState.shownPanelView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams2 = new ViewGroup.LayoutParams(-2, -2);
            }
            if (layoutParams2.width == -1) {
                i = panelFeatureState.fullBackground;
            } else {
                i3 = -2;
                i = panelFeatureState.background;
            }
            panelFeatureState.decorView.setWindowBackground(getContext().getDrawable(i));
            ViewParent parent = panelFeatureState.shownPanelView.getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(panelFeatureState.shownPanelView);
            }
            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, layoutParams2);
            if (!panelFeatureState.shownPanelView.hasFocus()) {
                panelFeatureState.shownPanelView.requestFocus();
            }
        } else {
            if (panelFeatureState.isInListMode() && (panelFeatureState.createdPanelView == null || (layoutParams = panelFeatureState.createdPanelView.getLayoutParams()) == null || layoutParams.width != -1)) {
                i2 = -2;
            }
            if (panelFeatureState.hasPanelItems()) {
                return;
            }
            panelFeatureState.isHandled = false;
            WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.x, panelFeatureState.y, 1003, 8519680, panelFeatureState.decorView.mDefaultOpacity);
            if (panelFeatureState.isCompact) {
                layoutParams3.gravity = getOptionsPanelGravity();
                sRotationWatcher.addWindow(this);
            } else {
                layoutParams3.gravity = panelFeatureState.gravity;
            }
            layoutParams3.windowAnimations = panelFeatureState.windowAnimations;
            windowManager.addView(panelFeatureState.decorView, layoutParams3);
            panelFeatureState.isOpen = true;
            return;
        }
        i2 = i3;
        if (panelFeatureState.hasPanelItems()) {
        }
    }

    @Override // android.view.Window
    public final void closePanel(int i) {
        DecorContentParent decorContentParent;
        if (i == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.hideOverflowMenu();
        } else if (i == 6) {
            closeContextMenu();
        } else {
            closePanel(getPanelState(i, true), true);
        }
    }

    public final void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        DecorContentParent decorContentParent;
        if (z && panelFeatureState.featureId == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        WindowManager windowManager = getWindowManager();
        if (windowManager != null && panelFeatureState.isOpen) {
            if (panelFeatureState.decorView != null) {
                windowManager.removeView(panelFeatureState.decorView);
                if (panelFeatureState.isCompact) {
                    sRotationWatcher.removeWindow(this);
                }
            }
            if (z) {
                callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        if (panelFeatureState.isInExpandedMode) {
            panelFeatureState.refreshDecorView = true;
            panelFeatureState.isInExpandedMode = false;
        }
        if (this.mPreparedPanel == panelFeatureState) {
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
        Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            callback.onPanelClosed(8, menu);
        }
        this.mClosingActionMenu = false;
    }

    @Override // android.view.Window
    public final void togglePanel(int i, KeyEvent keyEvent) throws Resources.NotFoundException {
        PanelFeatureState panelState = getPanelState(i, true);
        if (panelState.isOpen) {
            closePanel(panelState, true);
        } else {
            openPanel(panelState, keyEvent);
        }
    }

    @Override // android.view.Window
    public void invalidatePanelMenu(int i) {
        DecorView decorView;
        this.mInvalidatePanelMenuFeatures = (1 << i) | this.mInvalidatePanelMenuFeatures;
        if (this.mInvalidatePanelMenuPosted || (decorView = this.mDecor) == null) {
            return;
        }
        decorView.postOnAnimation(this.mInvalidatePanelMenuRunnable);
        this.mInvalidatePanelMenuPosted = true;
    }

    void doPendingInvalidatePanelMenu() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
    }

    void doInvalidatePanelMenu(int i) {
        PanelFeatureState panelState;
        PanelFeatureState panelState2 = getPanelState(i, false);
        if (panelState2 == null) {
            return;
        }
        if (panelState2.menu != null) {
            Bundle bundle = new Bundle();
            panelState2.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelState2.frozenActionViewState = bundle;
            }
            panelState2.menu.stopDispatchingItemsChanged();
            panelState2.menu.clear();
        }
        panelState2.refreshMenuContent = true;
        panelState2.refreshDecorView = true;
        if ((i != 8 && i != 0) || this.mDecorContentParent == null || (panelState = getPanelState(0, false)) == null) {
            return;
        }
        panelState.isPrepared = false;
        preparePanel(panelState, null);
    }

    public final boolean onKeyDownPanel(int i, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getRepeatCount() == 0) {
            this.mPanelChordingKey = keyCode;
            PanelFeatureState panelState = getPanelState(i, false);
            if (panelState != null && !panelState.isOpen) {
                return preparePanel(panelState, keyEvent);
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onKeyUpPanel(int i, KeyEvent keyEvent) throws Resources.NotFoundException {
        boolean zHideOverflowMenu;
        boolean zPreparePanel;
        DecorContentParent decorContentParent;
        if (this.mPanelChordingKey != 0) {
            this.mPanelChordingKey = 0;
            PanelFeatureState panelState = getPanelState(i, false);
            if (keyEvent.isCanceled()) {
                return;
            }
            DecorView decorView = this.mDecor;
            if ((decorView == null || decorView.mPrimaryActionMode == null) && panelState != null) {
                if (i == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
                    if (!this.mDecorContentParent.isOverflowMenuShowing()) {
                        if (!isDestroyed() && preparePanel(panelState, keyEvent)) {
                            zHideOverflowMenu = this.mDecorContentParent.showOverflowMenu();
                        }
                    } else {
                        zHideOverflowMenu = this.mDecorContentParent.hideOverflowMenu();
                    }
                } else if (panelState.isOpen || panelState.isHandled) {
                    zHideOverflowMenu = panelState.isOpen;
                    closePanel(panelState, true);
                } else if (panelState.isPrepared) {
                    if (panelState.refreshMenuContent) {
                        panelState.isPrepared = false;
                        zPreparePanel = preparePanel(panelState, keyEvent);
                    } else {
                        zPreparePanel = true;
                    }
                    if (zPreparePanel) {
                        EventLog.writeEvent(50001, 0);
                        openPanel(panelState, keyEvent);
                        zHideOverflowMenu = true;
                    }
                } else {
                    zHideOverflowMenu = false;
                }
                if (zHideOverflowMenu) {
                    AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
                    if (audioManager != null) {
                        audioManager.playSoundEffect(0);
                    } else {
                        Log.w(TAG, "Couldn't get audio manager");
                    }
                }
            }
        }
    }

    @Override // android.view.Window
    public final void closeAllPanels() {
        if (getWindowManager() == null) {
            return;
        }
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null) {
                closePanel(panelFeatureState, true);
            }
        }
        closeContextMenu();
    }

    private synchronized void closeContextMenu() {
        ContextMenuBuilder contextMenuBuilder = this.mContextMenu;
        if (contextMenuBuilder != null) {
            contextMenuBuilder.close();
            dismissContextMenu();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void dismissContextMenu() {
        this.mContextMenu = null;
        MenuHelper menuHelper = this.mContextMenuHelper;
        if (menuHelper != null) {
            menuHelper.dismiss();
            this.mContextMenuHelper = null;
        }
    }

    @Override // android.view.Window
    public boolean performPanelShortcut(int i, int i2, KeyEvent keyEvent, int i3) {
        return performPanelShortcut(getPanelState(i, false), i2, keyEvent, i3);
    }

    boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent, int i2) {
        boolean zPerformShortcut = false;
        if (!keyEvent.isSystem() && panelFeatureState != null) {
            if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && panelFeatureState.menu != null) {
                zPerformShortcut = panelFeatureState.menu.performShortcut(i, keyEvent, i2);
            }
            if (zPerformShortcut) {
                panelFeatureState.isHandled = true;
                if ((i2 & 1) == 0 && this.mDecorContentParent == null) {
                    closePanel(panelFeatureState, true);
                }
            }
        }
        return zPerformShortcut;
    }

    @Override // android.view.Window
    public boolean performPanelIdentifierAction(int i, int i2, int i3) {
        PanelFeatureState panelState = getPanelState(i, true);
        if (!preparePanel(panelState, new KeyEvent(0, 82)) || panelState.menu == null) {
            return false;
        }
        boolean zPerformIdentifierAction = panelState.menu.performIdentifierAction(i2, i3);
        if (this.mDecorContentParent == null) {
            closePanel(panelState, true);
        }
        return zPerformIdentifierAction;
    }

    public PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.menu == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        PanelFeatureState panelFeatureStateFindMenuPanel;
        Window.Callback callback = getCallback();
        if (callback == null || isDestroyed() || (panelFeatureStateFindMenuPanel = findMenuPanel(menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return callback.onMenuItemSelected(panelFeatureStateFindMenuPanel.featureId, menuItem);
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) throws Resources.NotFoundException {
        reopenMenu(true);
    }

    private void reopenMenu(boolean z) throws Resources.NotFoundException {
        boolean z2;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null && decorContentParent.canShowOverflowMenu() && (!ViewConfiguration.get(getContext()).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
            Window.Callback callback = getCallback();
            if (!this.mDecorContentParent.isOverflowMenuShowing() || !z) {
                if (callback == null || isDestroyed()) {
                    return;
                }
                if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                    this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                PanelFeatureState panelState = getPanelState(0, false);
                if (panelState == null || panelState.menu == null || panelState.refreshMenuContent || !callback.onPreparePanel(0, panelState.createdPanelView, panelState.menu)) {
                    return;
                }
                callback.onMenuOpened(8, panelState.menu);
                this.mDecorContentParent.showOverflowMenu();
                return;
            }
            this.mDecorContentParent.hideOverflowMenu();
            PanelFeatureState panelState2 = getPanelState(0, false);
            if (panelState2 == null || callback == null || isDestroyed()) {
                return;
            }
            callback.onPanelClosed(8, panelState2.menu);
            return;
        }
        PanelFeatureState panelState3 = getPanelState(0, false);
        if (panelState3 == null) {
            return;
        }
        if (z) {
            z2 = !panelState3.isInExpandedMode;
        } else {
            z2 = panelState3.isInExpandedMode;
        }
        panelState3.refreshDecorView = true;
        closePanel(panelState3, false);
        panelState3.isInExpandedMode = z2;
        openPanel(panelState3, (KeyEvent) null);
    }

    protected boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Resources.Theme themeNewTheme;
        Context context = getContext();
        if ((panelFeatureState.featureId == 0 || panelFeatureState.featureId == 8) && this.mDecorContentParent != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(16843825, typedValue, true);
            if (typedValue.resourceId != 0) {
                themeNewTheme = context.getResources().newTheme();
                themeNewTheme.setTo(theme);
                themeNewTheme.applyStyle(typedValue.resourceId, true);
                themeNewTheme.resolveAttribute(16843671, typedValue, true);
            } else {
                theme.resolveAttribute(16843671, typedValue, true);
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
        MenuBuilder menuBuilder = new MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.setMenu(menuBuilder);
        return true;
    }

    protected boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.decorView = generateDecor(panelFeatureState.featureId);
        panelFeatureState.gravity = 81;
        panelFeatureState.setStyle(getContext());
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.Window, 0, panelFeatureState.listPresenterTheme);
        float dimension = typedArrayObtainStyledAttributes.getDimension(37, 0.0f);
        if (dimension != 0.0f) {
            panelFeatureState.decorView.setElevation(dimension);
        }
        typedArrayObtainStyledAttributes.recycle();
        return true;
    }

    private int getOptionsPanelGravity() {
        try {
            return WindowManagerHolder.sWindowManager.getPreferredOptionsPanelGravity(getContext().getDisplayId());
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't getOptionsPanelGravity; using default", e);
            return 81;
        }
    }

    void onOptionsPanelRotationChanged() {
        PanelFeatureState panelState = getPanelState(0, false);
        if (panelState == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = panelState.decorView != null ? (WindowManager.LayoutParams) panelState.decorView.getLayoutParams() : null;
        if (layoutParams != null) {
            layoutParams.gravity = getOptionsPanelGravity();
            WindowManager windowManager = getWindowManager();
            if (windowManager != null) {
                windowManager.updateViewLayout(panelState.decorView, layoutParams);
            }
        }
    }

    protected boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        MenuView iconMenuView;
        if (panelFeatureState.createdPanelView != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView;
            return true;
        }
        if (panelFeatureState.menu == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        if (panelFeatureState.isInListMode()) {
            iconMenuView = panelFeatureState.getListMenuView(getContext(), this.mPanelMenuPresenterCallback);
        } else {
            iconMenuView = panelFeatureState.getIconMenuView(getContext(), this.mPanelMenuPresenterCallback);
        }
        panelFeatureState.shownPanelView = (View) iconMenuView;
        if (panelFeatureState.shownPanelView == null) {
            return false;
        }
        int windowAnimations = iconMenuView.getWindowAnimations();
        if (windowAnimations != 0) {
            panelFeatureState.windowAnimations = windowAnimations;
        }
        return true;
    }

    @Override // android.view.Window
    public boolean performContextMenuIdentifierAction(int i, int i2) {
        ContextMenuBuilder contextMenuBuilder = this.mContextMenu;
        if (contextMenuBuilder != null) {
            return contextMenuBuilder.performIdentifierAction(i, i2);
        }
        return false;
    }

    @Override // android.view.Window
    public final void setElevation(float f) {
        DecorView decorView = this.mDecor;
        if (decorView == null || !decorView.isDialogInPopOver()) {
            this.mElevation = f;
            WindowManager.LayoutParams attributes = getAttributes();
            DecorView decorView2 = this.mDecor;
            if (decorView2 != null) {
                decorView2.setElevation(f);
                attributes.setSurfaceInsets(this.mDecor, true, false);
            }
            dispatchWindowAttributesChanged(attributes);
        }
    }

    @Override // android.view.Window
    public float getElevation() {
        return this.mElevation;
    }

    @Override // android.view.Window
    public final void setClipToOutline(boolean z) {
        this.mClipToOutline = z;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.setClipToOutline(z);
        }
    }

    @Override // android.view.Window
    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable != this.mBackgroundDrawable) {
            this.mBackgroundDrawable = drawable;
            DecorView decorView = this.mDecor;
            if (decorView != null) {
                decorView.startChanging();
                this.mDecor.setWindowBackground(drawable);
                Drawable drawable2 = this.mBackgroundFallbackDrawable;
                if (drawable2 != null) {
                    DecorView decorView2 = this.mDecor;
                    if (drawable != null) {
                        drawable2 = null;
                    }
                    decorView2.setBackgroundFallback(drawable2);
                }
                this.mDecor.finishChanging();
            }
        }
    }

    @Override // android.view.Window
    public final void setBackgroundBlurRadius(int i) {
        super.setBackgroundBlurRadius(i);
        if (!CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED || this.mBackgroundBlurRadius == Math.max(i, 0)) {
            return;
        }
        int iMax = Math.max(i, 0);
        this.mBackgroundBlurRadius = iMax;
        this.mDecor.setBackgroundBlurRadius(iMax);
    }

    @Override // android.view.Window
    public final void setFeatureDrawableResource(int i, int i2) {
        if (i2 != 0) {
            DrawableFeatureState drawableState = getDrawableState(i, true);
            if (drawableState.resid != i2) {
                drawableState.resid = i2;
                drawableState.uri = null;
                drawableState.local = getContext().getDrawable(i2);
                updateDrawable(i, drawableState, false);
                return;
            }
            return;
        }
        setFeatureDrawable(i, null);
    }

    @Override // android.view.Window
    public final void setFeatureDrawableUri(int i, Uri uri) {
        if (uri != null) {
            DrawableFeatureState drawableState = getDrawableState(i, true);
            if (drawableState.uri == null || !drawableState.uri.equals(uri)) {
                drawableState.resid = 0;
                drawableState.uri = uri;
                drawableState.local = loadImageURI(uri);
                updateDrawable(i, drawableState, false);
                return;
            }
            return;
        }
        setFeatureDrawable(i, null);
    }

    @Override // android.view.Window
    public final void setFeatureDrawable(int i, Drawable drawable) {
        DrawableFeatureState drawableState = getDrawableState(i, true);
        drawableState.resid = 0;
        drawableState.uri = null;
        if (drawableState.local != drawable) {
            drawableState.local = drawable;
            updateDrawable(i, drawableState, false);
        }
    }

    @Override // android.view.Window
    public void setFeatureDrawableAlpha(int i, int i2) {
        DrawableFeatureState drawableState = getDrawableState(i, true);
        if (drawableState.alpha != i2) {
            drawableState.alpha = i2;
            updateDrawable(i, drawableState, false);
        }
    }

    protected final void setFeatureDefaultDrawable(int i, Drawable drawable) {
        DrawableFeatureState drawableState = getDrawableState(i, true);
        if (drawableState.def != drawable) {
            drawableState.def = drawable;
            updateDrawable(i, drawableState, false);
        }
    }

    @Override // android.view.Window
    public final void setFeatureInt(int i, int i2) throws Resources.NotFoundException {
        updateInt(i, i2, false);
    }

    protected final void updateDrawable(int i, boolean z) {
        DrawableFeatureState drawableState = getDrawableState(i, false);
        if (drawableState != null) {
            updateDrawable(i, drawableState, z);
        }
    }

    protected void onDrawableChanged(int i, Drawable drawable, int i2) {
        ImageView rightIconView;
        if (i == 3) {
            rightIconView = getLeftIconView();
        } else if (i != 4) {
            return;
        } else {
            rightIconView = getRightIconView();
        }
        if (drawable != null) {
            drawable.setAlpha(i2);
            rightIconView.lambda$setImageURIAsync$2(drawable);
            rightIconView.setVisibility(0);
            return;
        }
        rightIconView.setVisibility(8);
    }

    protected void onIntChanged(int i, int i2) throws Resources.NotFoundException {
        FrameLayout frameLayout;
        if (i == 2 || i == 5) {
            updateProgressBars(i2);
        } else {
            if (i != 7 || (frameLayout = (FrameLayout) findViewById(R.id.title_container)) == null) {
                return;
            }
            this.mLayoutInflater.inflate(i2, frameLayout);
        }
    }

    private void updateProgressBars(int i) throws Resources.NotFoundException {
        ProgressBar circularProgressBar = getCircularProgressBar(true);
        ProgressBar horizontalProgressBar = getHorizontalProgressBar(true);
        int localFeatures = getLocalFeatures();
        if (i == -1) {
            if ((localFeatures & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility((horizontalProgressBar.isIndeterminate() || horizontalProgressBar.getProgress() < 10000) ? 0 : 4);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((localFeatures & 32) != 0) {
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
        if (i == -2) {
            if ((localFeatures & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility(8);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((localFeatures & 32) != 0) {
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
        if (i == -3) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(true);
                return;
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
                return;
            }
        }
        if (i == -4) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(false);
                return;
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
                return;
            }
        }
        if (i < 0 || i > 10000) {
            if (20000 > i || i > 30000) {
                return;
            }
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setSecondaryProgress(i - 20000);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            showProgressBars(horizontalProgressBar, circularProgressBar);
            return;
        }
        if (horizontalProgressBar != null) {
            horizontalProgressBar.setProgress(i);
        } else {
            Log.e(TAG, "Horizontal progress bar not located in current window decor");
        }
        if (i < 10000) {
            showProgressBars(horizontalProgressBar, circularProgressBar);
        } else {
            hideProgressBars(horizontalProgressBar, circularProgressBar);
        }
    }

    private void showProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        int localFeatures = getLocalFeatures();
        if ((localFeatures & 32) != 0 && progressBar2 != null && progressBar2.getVisibility() == 4) {
            progressBar2.setVisibility(0);
        }
        if ((localFeatures & 4) == 0 || progressBar == null || progressBar.getProgress() >= 10000) {
            return;
        }
        progressBar.setVisibility(0);
    }

    private void hideProgressBars(ProgressBar progressBar, ProgressBar progressBar2) throws Resources.NotFoundException {
        int localFeatures = getLocalFeatures();
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), 17432577);
        animationLoadAnimation.setDuration(1000L);
        if ((localFeatures & 32) != 0 && progressBar2 != null && progressBar2.getVisibility() == 0) {
            progressBar2.startAnimation(animationLoadAnimation);
            progressBar2.setVisibility(4);
        }
        if ((localFeatures & 4) == 0 || progressBar == null || progressBar.getVisibility() != 0) {
            return;
        }
        progressBar.startAnimation(animationLoadAnimation);
        progressBar.setVisibility(4);
    }

    @Override // android.view.Window
    public void setIcon(int i) {
        this.mIconRes = i;
        this.mResourcesSetFlags = (this.mResourcesSetFlags | 1) & (-5);
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setIcon(i);
        }
    }

    @Override // android.view.Window
    public void setDefaultIcon(int i) {
        if ((this.mResourcesSetFlags & 1) != 0) {
            return;
        }
        this.mIconRes = i;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            if (decorContentParent.hasIcon() && (this.mResourcesSetFlags & 4) == 0) {
                return;
            }
            if (i != 0) {
                this.mDecorContentParent.setIcon(i);
                this.mResourcesSetFlags &= -5;
            } else {
                this.mDecorContentParent.setIcon(getContext().getPackageManager().getDefaultActivityIcon());
                this.mResourcesSetFlags |= 4;
            }
        }
    }

    @Override // android.view.Window
    public void setLogo(int i) {
        this.mLogoRes = i;
        this.mResourcesSetFlags |= 2;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setLogo(i);
        }
    }

    @Override // android.view.Window
    public void setDefaultLogo(int i) {
        if ((this.mResourcesSetFlags & 2) != 0) {
            return;
        }
        this.mLogoRes = i;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null || decorContentParent.hasLogo()) {
            return;
        }
        this.mDecorContentParent.setLogo(i);
    }

    @Override // android.view.Window
    public void setLocalFocus(boolean z, boolean z2) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        viewRootImpl.windowFocusChanged(z);
        viewRootImpl.touchModeChanged(z2);
    }

    @Override // android.view.Window
    public void injectInputEvent(InputEvent inputEvent) {
        getViewRootImpl().dispatchInputEvent(inputEvent);
    }

    private ViewRootImpl getViewRootImpl() {
        ViewRootImpl viewRootImplOrNull = getViewRootImplOrNull();
        if (viewRootImplOrNull != null) {
            return viewRootImplOrNull;
        }
        throw new IllegalStateException("view not added");
    }

    private ViewRootImpl getViewRootImplOrNull() {
        DecorView decorView = this.mDecor;
        if (decorView == null) {
            return null;
        }
        return decorView.getViewRootImpl();
    }

    @Override // android.view.Window
    public void takeKeyEvents(boolean z) {
        this.mDecor.setFocusable(z);
    }

    @Override // android.view.Window
    public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return this.mDecor.superDispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.mDecor.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
        return this.mDecor.superDispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
        return this.mDecor.superDispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.mDecor.superDispatchGenericMotionEvent(motionEvent);
    }

    protected boolean onKeyDown(int i, int i2, KeyEvent keyEvent) {
        DecorView decorView = this.mDecor;
        KeyEvent.DispatcherState keyDispatcherState = decorView != null ? decorView.getKeyDispatcherState() : null;
        if (i2 != 4) {
            if (i2 != 79) {
                if (i2 == 82) {
                    if (i < 0) {
                        i = 0;
                    }
                    onKeyDownPanel(i, keyEvent);
                    return true;
                }
                if (i2 != 130) {
                    if (i2 == 164 || i2 == 24 || i2 == 25) {
                        if (this.mMediaController != null && !isActivePhoneCallOngoing()) {
                            getMediaSessionManager().dispatchVolumeKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
                        } else {
                            getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, this.mVolumeControlStreamType);
                        }
                        return true;
                    }
                    if (i2 != 126 && i2 != 127) {
                        switch (i2) {
                        }
                        return true;
                    }
                }
            }
            return this.mMediaController != null && getMediaSessionManager().dispatchMediaKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
        }
        if (keyEvent.getRepeatCount() <= 0 && i >= 0) {
            if (keyDispatcherState != null) {
                keyDispatcherState.startTracking(keyEvent, this);
            }
            return true;
        }
        return false;
    }

    private boolean isActivePhoneCallOngoing() {
        int i = this.mAudioMode;
        return i == 2 || i == 3;
    }

    private KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
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
            this.mMediaSessionManager = (MediaSessionManager) getContext().getSystemService(Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected boolean onKeyUp(int i, int i2, KeyEvent keyEvent) throws Resources.NotFoundException {
        PanelFeatureState panelState;
        DecorView decorView = this.mDecor;
        KeyEvent.DispatcherState keyDispatcherState = decorView != null ? decorView.getKeyDispatcherState() : null;
        if (keyDispatcherState != null) {
            keyDispatcherState.handleUpEvent(keyEvent);
        }
        if (i2 != 4) {
            if (i2 != 79) {
                if (i2 == 82) {
                    if (i < 0) {
                        i = 0;
                    }
                    onKeyUpPanel(i, keyEvent);
                    return true;
                }
                if (i2 != 130) {
                    if (i2 == 164) {
                        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, Integer.MIN_VALUE);
                        return true;
                    }
                    if (i2 == 171) {
                        if (this.mSupportsPictureInPicture && !keyEvent.isCanceled()) {
                            getWindowControllerCallback().enterPictureInPictureModeIfPossible();
                        }
                        return true;
                    }
                    if (i2 != 24 && i2 != 25) {
                        if (i2 != 126 && i2 != 127) {
                            switch (i2) {
                                case 84:
                                    if (!isNotInstantAppAndKeyguardRestricted() && (getContext().getResources().getConfiguration().uiMode & 15) != 6) {
                                        if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                                            launchDefaultSearch(keyEvent);
                                        }
                                        return true;
                                    }
                                    break;
                            }
                        }
                    } else {
                        if (this.mMediaController != null) {
                            getMediaSessionManager().dispatchVolumeKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
                        } else {
                            getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, this.mVolumeControlStreamType);
                        }
                        return true;
                    }
                }
            }
            return this.mMediaController != null && getMediaSessionManager().dispatchMediaKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
        }
        if (i >= 0 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
            if (i == 0 && (panelState = getPanelState(i, false)) != null && panelState.isInExpandedMode) {
                reopenMenu(true);
                return true;
            }
            closePanel(i);
            return true;
        }
        return false;
    }

    private boolean isNotInstantAppAndKeyguardRestricted() {
        return !getContext().getPackageManager().isInstantApp() && getKeyguardManager().inKeyguardRestrictedInputMode();
    }

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

    void onViewRootImplSet(ViewRootImpl viewRootImpl) {
        viewRootImpl.setActivityConfigCallback(this.mActivityConfigCallback);
        viewRootImpl.getOnBackInvokedDispatcher().updateContext(getContext());
        this.mProxyOnBackInvokedDispatcher.setActualDispatcher(viewRootImpl.getOnBackInvokedDispatcher());
        applyDecorFitsSystemWindows();
    }

    @Override // android.view.Window
    public Bundle saveHierarchyState() {
        Bundle bundle = new Bundle();
        if (this.mContentParent != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.mContentParent.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
            View viewFindFocus = this.mContentParent.findFocus();
            if (viewFindFocus != null && viewFindFocus.getId() != -1) {
                bundle.putInt(FOCUSED_ID_TAG, viewFindFocus.getId());
            }
            SparseArray<Parcelable> sparseArray2 = new SparseArray<>();
            savePanelState(sparseArray2);
            if (sparseArray2.size() > 0) {
                bundle.putSparseParcelableArray(PANELS_TAG, sparseArray2);
            }
            if (this.mDecorContentParent != null) {
                SparseArray<Parcelable> sparseArray3 = new SparseArray<>();
                this.mDecorContentParent.saveToolbarHierarchyState(sparseArray3);
                bundle.putSparseParcelableArray(ACTION_BAR_TAG, sparseArray3);
            }
        }
        return bundle;
    }

    @Override // android.view.Window
    public void restoreHierarchyState(Bundle bundle) throws Resources.NotFoundException {
        if (this.mContentParent == null) {
            return;
        }
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            this.mContentParent.restoreHierarchyState(sparseParcelableArray);
        }
        int i = bundle.getInt(FOCUSED_ID_TAG, -1);
        if (i != -1) {
            View viewFindViewById = this.mContentParent.findViewById(i);
            if (viewFindViewById != null) {
                viewFindViewById.requestFocus();
            } else {
                Log.w(TAG, "Previously focused view reported id " + i + " during save, but can't be found during restore.");
            }
        }
        SparseArray<Parcelable> sparseParcelableArray2 = bundle.getSparseParcelableArray(PANELS_TAG);
        if (sparseParcelableArray2 != null) {
            restorePanelState(sparseParcelableArray2);
        }
        if (this.mDecorContentParent != null) {
            SparseArray<Parcelable> sparseParcelableArray3 = bundle.getSparseParcelableArray(ACTION_BAR_TAG);
            if (sparseParcelableArray3 == null) {
                Log.w(TAG, "Missing saved instance states for action bar views! State will not be restored.");
            } else {
                doPendingInvalidatePanelMenu();
                this.mDecorContentParent.restoreToolbarHierarchyState(sparseParcelableArray3);
            }
        }
    }

    private void savePanelState(SparseArray<Parcelable> sparseArray) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null) {
            return;
        }
        for (int length = panelFeatureStateArr.length - 1; length >= 0; length--) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[length];
            if (panelFeatureState != null) {
                sparseArray.put(length, panelFeatureState.onSaveInstanceState());
            }
        }
    }

    private void restorePanelState(SparseArray<Parcelable> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            int iKeyAt = sparseArray.keyAt(size);
            PanelFeatureState panelState = getPanelState(iKeyAt, false);
            if (panelState != null) {
                panelState.onRestoreInstanceState(sparseArray.get(iKeyAt));
                invalidatePanelMenu(iKeyAt);
            }
        }
    }

    void openPanelsAfterRestore() {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null) {
            return;
        }
        for (int length = panelFeatureStateArr.length - 1; length >= 0; length--) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[length];
            if (panelFeatureState != null) {
                panelFeatureState.applyFrozenState();
                if (!panelFeatureState.isOpen && panelFeatureState.wasLastOpen) {
                    panelFeatureState.isInExpandedMode = panelFeatureState.wasLastExpanded;
                    openPanel(panelFeatureState, (KeyEvent) null);
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
        private PanelMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            PhoneWindow phoneWindow = PhoneWindow.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState panelFeatureStateFindMenuPanel = phoneWindow.findMenuPanel(menuBuilder);
            if (panelFeatureStateFindMenuPanel != null) {
                if (z2) {
                    PhoneWindow.this.callOnPanelClosed(panelFeatureStateFindMenuPanel.featureId, panelFeatureStateFindMenuPanel, rootMenu);
                    PhoneWindow.this.closePanel(panelFeatureStateFindMenuPanel, true);
                } else {
                    PhoneWindow.this.closePanel(panelFeatureStateFindMenuPanel, z);
                }
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback;
            if (menuBuilder != null || !PhoneWindow.this.hasFeature(8) || (callback = PhoneWindow.this.getCallback()) == null || PhoneWindow.this.isDestroyed()) {
                return true;
            }
            callback.onMenuOpened(8, menuBuilder);
            return true;
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = PhoneWindow.this.getCallback();
            if (callback == null) {
                return false;
            }
            callback.onMenuOpened(8, menuBuilder);
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            PhoneWindow.this.checkCloseActionMenu(menuBuilder);
        }
    }

    protected DecorView generateDecor(int i) {
        Context context;
        Context applicationContext;
        if (!this.mUseDecorContext || (applicationContext = getContext().getApplicationContext()) == null) {
            context = getContext();
        } else {
            DecorContext decorContext = new DecorContext(applicationContext, this);
            int i2 = this.mTheme;
            if (i2 != -1) {
                decorContext.setTheme(i2);
            }
            context = decorContext;
        }
        return new DecorView(context, i, this, getAttributes());
    }

    protected ViewGroup generateLayout(DecorView decorView) {
        int i;
        int resourceId;
        ProgressBar circularProgressBar;
        TypedArray windowStyle = getWindowStyle();
        WindowManager.LayoutParams attributes = getAttributes();
        ApplicationInfo applicationInfo = getContext().getApplicationInfo();
        boolean zIsEdgeToEdgeEnforced = isEdgeToEdgeEnforced(applicationInfo, true, windowStyle);
        this.mEdgeToEdgeEnforced = zIsEdgeToEdgeEnforced;
        if (zIsEdgeToEdgeEnforced) {
            getAttributes().privateFlags |= 2048;
            this.mDecorFitsSystemWindows = false;
            applyDecorFitsSystemWindows();
            this.mStatusBarColor = 0;
            this.mNavigationBarDividerColor = 0;
        }
        if (CompatChanges.isChangeEnabled(OVERRIDE_LAYOUT_IN_DISPLAY_CUTOUT_MODE) && !isOptingOutEdgeToEdgeEnforcement(applicationInfo, true, windowStyle)) {
            getAttributes().privateFlags |= 262144;
        }
        boolean z = windowStyle.getBoolean(4, false);
        this.mIsFloating = z;
        boolean z2 = z && this.mAllowFloatingWindowsFillScreen.booleanValue() && windowStyle.getBoolean(9, false);
        int i2 = (~getForcedWindowFlags()) & 65792;
        if (this.mIsFloating && !z2) {
            setLayout(-2, -2);
            setFlags(0, i2);
        } else {
            setFlags(65792, i2);
            attributes.setFitInsetsSides(0);
            attributes.setFitInsetsTypes(0);
        }
        if (windowStyle.getBoolean(3, false)) {
            requestFeature(1);
        } else if (windowStyle.getBoolean(15, false)) {
            requestFeature(8);
        }
        if (windowStyle.getBoolean(17, false)) {
            requestFeature(9);
        }
        if (windowStyle.getBoolean(16, false)) {
            requestFeature(10);
        }
        if (windowStyle.getBoolean(9, false)) {
            setFlags(1024, (~getForcedWindowFlags()) & 1024);
        }
        if (windowStyle.getBoolean(23, false)) {
            setFlags(67108864, (~getForcedWindowFlags()) & 67108864);
        }
        if (windowStyle.getBoolean(24, false)) {
            setFlags(134217728, (~getForcedWindowFlags()) & 134217728);
        }
        if (windowStyle.getBoolean(14, false)) {
            setFlags(1048576, (~getForcedWindowFlags()) & 1048576);
        }
        if (windowStyle.getBoolean(18, getContext().getApplicationInfo().targetSdkVersion >= 11)) {
            setFlags(8388608, (~getForcedWindowFlags()) & 8388608);
        }
        windowStyle.getValue(19, this.mMinWidthMajor);
        windowStyle.getValue(20, this.mMinWidthMinor);
        if (windowStyle.hasValue(68)) {
            if (this.mFixedWidthMajor == null) {
                this.mFixedWidthMajor = new TypedValue();
            }
            windowStyle.getValue(68, this.mFixedWidthMajor);
        }
        if (windowStyle.hasValue(69)) {
            if (this.mFixedWidthMinor == null) {
                this.mFixedWidthMinor = new TypedValue();
            }
            windowStyle.getValue(69, this.mFixedWidthMinor);
        }
        if (windowStyle.hasValue(66)) {
            if (this.mFixedHeightMajor == null) {
                this.mFixedHeightMajor = new TypedValue();
            }
            windowStyle.getValue(66, this.mFixedHeightMajor);
        }
        if (windowStyle.hasValue(67)) {
            if (this.mFixedHeightMinor == null) {
                this.mFixedHeightMinor = new TypedValue();
            }
            windowStyle.getValue(67, this.mFixedHeightMinor);
        }
        if (windowStyle.getBoolean(25, false)) {
            requestFeature(12);
        }
        if (windowStyle.getBoolean(44, false)) {
            requestFeature(13);
        }
        if (windowStyle.hasValue(64) && sToolkitSetFrameRateReadOnlyFlagValue) {
            setFrameRatePowerSavingsBalanced(windowStyle.getBoolean(64, true));
        }
        this.mIsTranslucent = windowStyle.getBoolean(5, false);
        Context context = getContext();
        int i3 = context.getApplicationInfo().targetSdkVersion;
        boolean z3 = i3 < 21;
        boolean z4 = i3 < 29;
        if (!this.mForcedStatusBarColor && !this.mEdgeToEdgeEnforced) {
            this.mStatusBarColor = windowStyle.getColor(34, -16777216);
        }
        if (this.mForcedNavigationBarColor) {
            i = 1048576;
        } else {
            int color = context.getColor(R.color.navigation_bar_compatible);
            int color2 = context.getColor(R.color.navigation_bar_default);
            i = 1048576;
            int color3 = windowStyle.getColor(35, color2);
            boolean z5 = color3 != color2;
            if (z5 || this.mEdgeToEdgeEnforced || context.getResources().getBoolean(R.bool.config_navBarDefaultTransparent)) {
                color = color3;
            }
            this.mNavigationBarColor = color;
            this.mNavigationBarColorSpecified |= z5;
            if (!this.mEdgeToEdgeEnforced) {
                this.mNavigationBarDividerColor = windowStyle.getColor(49, 0);
            }
        }
        if (!z4) {
            this.mEnsureStatusBarContrastWhenTransparent = windowStyle.getBoolean(51, false);
            this.mEnsureNavigationBarContrastWhenTransparent = windowStyle.getBoolean(52, true);
        }
        int color4 = windowStyle.getColor(35, this.mDeviceDefaultNavigationBarColor);
        boolean z6 = this.mDeviceDefaultNavigationBarColor == color4 || context.getColor(R.color.navigation_bar_default) == color4;
        int defaultNavigationBarColor = getDefaultNavigationBarColor();
        this.mDefaultNavigationBarColor = defaultNavigationBarColor;
        if (!this.mForcedNavigationBarColor) {
            if (z6) {
                color4 = defaultNavigationBarColor;
            }
            this.mNavigationBarColor = color4;
        }
        int i4 = this.mNavigationBarColor;
        if (i4 == defaultNavigationBarColor && needLightNavigationBar(i4)) {
            attributes.samsungFlags |= i;
        }
        if (!this.mIsFloating) {
            if (!z3 && windowStyle.getBoolean(33, false)) {
                setFlags(Integer.MIN_VALUE, (~getForcedWindowFlags()) & Integer.MIN_VALUE);
            }
            if (this.mDecor.mForceWindowDrawsBarBackgrounds) {
                attributes.privateFlags |= 32768;
            }
            Configuration configuration = this.mActivityCurrentConfig;
            if (configuration == null || !configuration.semIsPopOver()) {
                attributes.privateFlags |= 64;
            }
        }
        if (windowStyle.getBoolean(62, false)) {
            attributes.privateFlags |= 64;
        }
        boolean z7 = windowStyle.getBoolean(45, false);
        boolean z8 = windowStyle.getBoolean(48, false);
        decorView.setSystemUiVisibility((decorView.getSystemUiVisibility() & (-8209)) | (z7 ? 8192 : 0) | (z8 ? 16 : 0));
        decorView.getWindowInsetsController().setSystemBarsAppearanceFromResource((z7 ? 8 : 0) | (z8 ? 16 : 0), 24);
        try {
            if (context.getResources().getAssets().getSamsungThemeOverlays().size() > 0) {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
                if (typedValue.data != 0 && windowStyle.getBoolean(45, true)) {
                    if (context.getResources().getBoolean(R.bool.sem_window_light_status_bar)) {
                        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 8192);
                    } else {
                        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-8193));
                    }
                    if (this.mStatusBarColor != 0) {
                        this.mStatusBarColor = context.getResources().getColor(R.color.tw_status_bar_color);
                    }
                }
            }
            if (context.getResources().getAssets().getSamsungThemeOverlays().size() > 0 || WallpaperThemeUtils.hasWallpaperThemeOverlays(context)) {
                this.mThemeApplied = true;
                int defaultNavigationBarColor2 = getDefaultNavigationBarColor();
                if (this.mNavigationBarColor == this.mDefaultNavigationBarColor && !this.mForcedNavigationBarColor) {
                    this.mNavigationBarColor = defaultNavigationBarColor2;
                }
                if (this.mNavigationBarColor == defaultNavigationBarColor2 && !needLightNavigationBar(defaultNavigationBarColor2)) {
                    attributes.samsungFlags &= -1048577;
                }
                this.mDefaultNavigationBarColor = defaultNavigationBarColor2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (windowStyle.hasValue(50)) {
            int i5 = windowStyle.getInt(50, -1);
            if (i5 < 0 || i5 > 3) {
                throw new UnsupportedOperationException("Unknown windowLayoutInDisplayCutoutMode: " + windowStyle.getString(50));
            }
            attributes.layoutInDisplayCutoutMode = i5;
        }
        if ((this.mAlwaysReadCloseOnTouchAttr || getContext().getApplicationInfo().targetSdkVersion >= 11) && windowStyle.getBoolean(21, false)) {
            setCloseOnTouchOutsideIfNotSet(true);
        }
        if (!hasSoftInputMode()) {
            attributes.softInputMode = windowStyle.getInt(13, attributes.softInputMode);
        }
        if (windowStyle.getBoolean(11, this.mIsFloating)) {
            if ((getForcedWindowFlags() & 2) == 0) {
                attributes.flags |= 2;
            }
            if (!haveDimAmount()) {
                attributes.dimAmount = windowStyle.getFloat(0, 0.5f);
            }
        }
        if (windowStyle.getBoolean(54, false)) {
            if ((getForcedWindowFlags() & 4) == 0) {
                attributes.flags |= 4;
            }
            attributes.setBlurBehindRadius(windowStyle.getDimensionPixelSize(53, 0));
        }
        setBackgroundBlurRadius(windowStyle.getDimensionPixelSize(55, 0));
        if (attributes.windowAnimations == 0) {
            attributes.windowAnimations = windowStyle.getResourceId(8, 0);
        }
        if (getContainer() == null) {
            if (this.mBackgroundDrawable == null) {
                if (this.mFrameResource == 0) {
                    this.mFrameResource = windowStyle.getResourceId(2, 0);
                }
                if (windowStyle.hasValue(1)) {
                    this.mBackgroundDrawable = windowStyle.getDrawable(1);
                    this.mDecor.setLastBackgroundResource(windowStyle.getResourceId(1, 0));
                }
            }
            if (windowStyle.hasValue(46)) {
                this.mBackgroundFallbackDrawable = windowStyle.getDrawable(46);
            }
            if (this.mLoadElevation) {
                if (this.mDecor.isDialogInPopOver()) {
                    this.mElevation = 0.0f;
                } else {
                    this.mElevation = windowStyle.getDimension(37, 0.0f);
                }
            }
            this.mClipToOutline = windowStyle.getBoolean(38, false);
            this.mTextColor = windowStyle.getColor(7, 0);
        }
        int localFeatures = getLocalFeatures();
        if ((localFeatures & 24) != 0) {
            if (this.mIsFloating) {
                TypedValue typedValue2 = new TypedValue();
                getContext().getTheme().resolveAttribute(R.attr.dialogTitleIconsDecorLayout, typedValue2, true);
                resourceId = typedValue2.resourceId;
            } else {
                resourceId = R.layout.screen_title_icons;
            }
            removeFeature(8);
        } else if ((localFeatures & 36) != 0 && (localFeatures & 256) == 0) {
            resourceId = R.layout.screen_progress;
        } else if ((localFeatures & 128) != 0) {
            if (this.mIsFloating) {
                TypedValue typedValue3 = new TypedValue();
                getContext().getTheme().resolveAttribute(R.attr.dialogCustomTitleDecorLayout, typedValue3, true);
                resourceId = typedValue3.resourceId;
            } else {
                resourceId = R.layout.screen_custom_title;
            }
            removeFeature(8);
        } else if ((localFeatures & 2) != 0) {
            resourceId = (localFeatures & 1024) != 0 ? R.layout.screen_simple_overlay_action_mode : R.layout.screen_simple;
        } else if (this.mIsFloating) {
            TypedValue typedValue4 = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.dialogTitleDecorLayout, typedValue4, true);
            resourceId = typedValue4.resourceId;
        } else {
            resourceId = (localFeatures & 256) != 0 ? windowStyle.getResourceId(65, R.layout.screen_action_bar) : R.layout.screen_title;
        }
        this.mDecor.startChanging();
        this.mDecor.onResourcesLoaded(this.mLayoutInflater, resourceId);
        ViewGroup viewGroup = (ViewGroup) findViewById(16908290);
        if (viewGroup == null) {
            throw new RuntimeException("Window couldn't find content container view");
        }
        if ((localFeatures & 32) != 0 && (circularProgressBar = getCircularProgressBar(false)) != null) {
            circularProgressBar.setIndeterminate(true);
        }
        if (getContainer() == null) {
            this.mDecor.setWindowBackground(this.mBackgroundDrawable);
            this.mDecor.setWindowFrame(this.mFrameResource != 0 ? getContext().getDrawable(this.mFrameResource) : null);
            this.mDecor.setElevation(this.mElevation);
            this.mDecor.setClipToOutline(this.mClipToOutline);
            CharSequence charSequence = this.mTitle;
            if (charSequence != null) {
                setTitle(charSequence);
            }
            if (this.mTitleColor == 0) {
                this.mTitleColor = this.mTextColor;
            }
            setTitleColor(this.mTitleColor);
        }
        this.mDecor.finishChanging();
        return viewGroup;
    }

    @Override // android.view.Window
    public void alwaysReadCloseOnTouchAttr() {
        this.mAlwaysReadCloseOnTouchAttr = true;
    }

    private void installDecor() {
        Drawable drawable;
        this.mForceDecorInstall = false;
        DecorView decorView = this.mDecor;
        if (decorView == null) {
            DecorView decorViewGenerateDecor = generateDecor(-1);
            this.mDecor = decorViewGenerateDecor;
            decorViewGenerateDecor.setDescendantFocusability(262144);
            this.mDecor.setIsRootNamespace(true);
            if (!this.mInvalidatePanelMenuPosted && this.mInvalidatePanelMenuFeatures != 0) {
                this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            }
        } else {
            decorView.setWindow(this);
        }
        if (this.mContentParent == null) {
            this.mContentParent = generateLayout(this.mDecor);
            this.mDecor.makeFrameworkOptionalFitsSystemWindows();
            DecorContentParent decorContentParent = (DecorContentParent) this.mDecor.findViewById(R.id.decor_content_parent);
            if (decorContentParent != null) {
                this.mDecorContentParent = decorContentParent;
                decorContentParent.setWindowCallback(getCallback());
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
                if ((this.mResourcesSetFlags & 1) != 0 || (this.mIconRes != 0 && !this.mDecorContentParent.hasIcon())) {
                    this.mDecorContentParent.setIcon(this.mIconRes);
                } else if ((this.mResourcesSetFlags & 1) == 0 && this.mIconRes == 0 && !this.mDecorContentParent.hasIcon()) {
                    this.mDecorContentParent.setIcon(getContext().getPackageManager().getDefaultActivityIcon());
                    this.mResourcesSetFlags |= 4;
                }
                if ((this.mResourcesSetFlags & 2) != 0 || (this.mLogoRes != 0 && !this.mDecorContentParent.hasLogo())) {
                    this.mDecorContentParent.setLogo(this.mLogoRes);
                }
                PanelFeatureState panelState = getPanelState(0, false);
                if (!isDestroyed() && ((panelState == null || panelState.menu == null) && !this.mIsStartingWindow)) {
                    invalidatePanelMenu(8);
                }
            } else {
                TextView textView = (TextView) findViewById(16908310);
                this.mTitleView = textView;
                if (textView != null) {
                    if ((getLocalFeatures() & 2) != 0) {
                        View viewFindViewById = findViewById(R.id.title_container);
                        if (viewFindViewById != null) {
                            viewFindViewById.setVisibility(8);
                        } else {
                            this.mTitleView.setVisibility(8);
                        }
                        this.mContentParent.setForeground(null);
                    } else {
                        this.mTitleView.lambda$setTextAsync$0(this.mTitle);
                    }
                }
            }
            if (this.mDecor.getBackground() == null && (drawable = this.mBackgroundFallbackDrawable) != null) {
                this.mDecor.setBackgroundFallback(drawable);
            }
            if (hasFeature(13)) {
                if (this.mTransitionManager == null) {
                    int resourceId = getWindowStyle().getResourceId(26, 0);
                    if (resourceId != 0) {
                        this.mTransitionManager = TransitionInflater.from(getContext()).inflateTransitionManager(resourceId, this.mContentParent);
                    } else {
                        this.mTransitionManager = new TransitionManager();
                    }
                }
                this.mEnterTransition = getTransition(this.mEnterTransition, null, 27);
                Transition transition = this.mReturnTransition;
                Transition transition2 = USE_DEFAULT_TRANSITION;
                this.mReturnTransition = getTransition(transition, transition2, 39);
                this.mExitTransition = getTransition(this.mExitTransition, null, 28);
                this.mReenterTransition = getTransition(this.mReenterTransition, transition2, 40);
                this.mSharedElementEnterTransition = getTransition(this.mSharedElementEnterTransition, null, 29);
                this.mSharedElementReturnTransition = getTransition(this.mSharedElementReturnTransition, transition2, 41);
                this.mSharedElementExitTransition = getTransition(this.mSharedElementExitTransition, null, 30);
                this.mSharedElementReenterTransition = getTransition(this.mSharedElementReenterTransition, transition2, 42);
                if (this.mAllowEnterTransitionOverlap == null) {
                    this.mAllowEnterTransitionOverlap = Boolean.valueOf(getWindowStyle().getBoolean(32, true));
                }
                if (this.mAllowReturnTransitionOverlap == null) {
                    this.mAllowReturnTransitionOverlap = Boolean.valueOf(getWindowStyle().getBoolean(31, true));
                }
                if (this.mBackgroundFadeDurationMillis < 0) {
                    this.mBackgroundFadeDurationMillis = getWindowStyle().getInteger(36, 300);
                }
                if (this.mSharedElementsUseOverlay == null) {
                    this.mSharedElementsUseOverlay = Boolean.valueOf(getWindowStyle().getBoolean(43, true));
                }
            }
        }
    }

    private Transition getTransition(Transition transition, Transition transition2, int i) {
        if (transition != transition2) {
            return transition;
        }
        int resourceId = getWindowStyle().getResourceId(i, -1);
        if (resourceId == -1 || resourceId == 17760256) {
            return transition2;
        }
        Transition transitionInflateTransition = TransitionInflater.from(getContext()).inflateTransition(resourceId);
        if ((transitionInflateTransition instanceof TransitionSet) && ((TransitionSet) transitionInflateTransition).getTransitionCount() == 0) {
            return null;
        }
        return transitionInflateTransition;
    }

    private Drawable loadImageURI(Uri uri) {
        try {
            return Drawable.createFromStream(getContext().getContentResolver().openInputStream(uri), null);
        } catch (Exception unused) {
            Log.w(TAG, "Unable to open content: " + uri);
            return null;
        }
    }

    private DrawableFeatureState getDrawableState(int i, boolean z) {
        if ((getFeatures() & (1 << i)) == 0) {
            if (z) {
                throw new RuntimeException("The feature has not been requested");
            }
            return null;
        }
        DrawableFeatureState[] drawableFeatureStateArr = this.mDrawables;
        if (drawableFeatureStateArr == null || drawableFeatureStateArr.length <= i) {
            DrawableFeatureState[] drawableFeatureStateArr2 = new DrawableFeatureState[i + 1];
            if (drawableFeatureStateArr != null) {
                System.arraycopy(drawableFeatureStateArr, 0, drawableFeatureStateArr2, 0, drawableFeatureStateArr.length);
            }
            this.mDrawables = drawableFeatureStateArr2;
            drawableFeatureStateArr = drawableFeatureStateArr2;
        }
        DrawableFeatureState drawableFeatureState = drawableFeatureStateArr[i];
        if (drawableFeatureState != null) {
            return drawableFeatureState;
        }
        DrawableFeatureState drawableFeatureState2 = new DrawableFeatureState(i);
        drawableFeatureStateArr[i] = drawableFeatureState2;
        return drawableFeatureState2;
    }

    PanelFeatureState getPanelState(int i, boolean z) {
        return getPanelState(i, z, null);
    }

    private PanelFeatureState getPanelState(int i, boolean z, PanelFeatureState panelFeatureState) {
        if ((getFeatures() & (1 << i)) == 0) {
            if (z) {
                throw new RuntimeException("The feature has not been requested");
            }
            return null;
        }
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState2 = panelFeatureStateArr[i];
        if (panelFeatureState2 != null) {
            return panelFeatureState2;
        }
        if (panelFeatureState == null) {
            panelFeatureState = new PanelFeatureState(i);
        }
        panelFeatureStateArr[i] = panelFeatureState;
        return panelFeatureState;
    }

    @Override // android.view.Window
    public final void setChildDrawable(int i, Drawable drawable) {
        DrawableFeatureState drawableState = getDrawableState(i, true);
        drawableState.child = drawable;
        updateDrawable(i, drawableState, false);
    }

    @Override // android.view.Window
    public final void setChildInt(int i, int i2) throws Resources.NotFoundException {
        updateInt(i, i2, false);
    }

    @Override // android.view.Window
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        PanelFeatureState panelState = getPanelState(0, false);
        return (panelState == null || panelState.menu == null || !panelState.menu.isShortcutKey(i, keyEvent)) ? false : true;
    }

    private void updateDrawable(int i, DrawableFeatureState drawableFeatureState, boolean z) {
        Drawable drawable;
        if (this.mContentParent == null) {
            return;
        }
        int i2 = 1 << i;
        if ((getFeatures() & i2) != 0 || z) {
            if (drawableFeatureState != null) {
                drawable = drawableFeatureState.child;
                if (drawable == null) {
                    drawable = drawableFeatureState.local;
                }
                if (drawable == null) {
                    drawable = drawableFeatureState.def;
                }
            } else {
                drawable = null;
            }
            if ((i2 & getLocalFeatures()) == 0) {
                if (getContainer() != null) {
                    if (isActive() || z) {
                        getContainer().setChildDrawable(i, drawable);
                        return;
                    }
                    return;
                }
                return;
            }
            if (drawableFeatureState != null) {
                if (drawableFeatureState.cur == drawable && drawableFeatureState.curAlpha == drawableFeatureState.alpha) {
                    return;
                }
                drawableFeatureState.cur = drawable;
                drawableFeatureState.curAlpha = drawableFeatureState.alpha;
                onDrawableChanged(i, drawable, drawableFeatureState.alpha);
            }
        }
    }

    private void updateInt(int i, int i2, boolean z) throws Resources.NotFoundException {
        if (this.mContentParent == null) {
            return;
        }
        int i3 = 1 << i;
        if ((getFeatures() & i3) != 0 || z) {
            if ((getLocalFeatures() & i3) == 0) {
                if (getContainer() != null) {
                    getContainer().setChildInt(i, i2);
                    return;
                }
                return;
            }
            onIntChanged(i, i2);
        }
    }

    private ImageView getLeftIconView() {
        ImageView imageView = this.mLeftIconView;
        if (imageView != null) {
            return imageView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.left_icon);
        this.mLeftIconView = imageView2;
        return imageView2;
    }

    @Override // android.view.Window
    protected void dispatchWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        super.dispatchWindowAttributesChanged(layoutParams);
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, true);
        }
    }

    private ProgressBar getCircularProgressBar(boolean z) {
        ProgressBar progressBar = this.mCircularProgressBar;
        if (progressBar != null) {
            return progressBar;
        }
        if (this.mContentParent == null && z) {
            installDecor();
        }
        ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progress_circular);
        this.mCircularProgressBar = progressBar2;
        if (progressBar2 != null) {
            progressBar2.setVisibility(4);
        }
        return this.mCircularProgressBar;
    }

    private ProgressBar getHorizontalProgressBar(boolean z) {
        ProgressBar progressBar = this.mHorizontalProgressBar;
        if (progressBar != null) {
            return progressBar;
        }
        if (this.mContentParent == null && z) {
            installDecor();
        }
        ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progress_horizontal);
        this.mHorizontalProgressBar = progressBar2;
        if (progressBar2 != null) {
            progressBar2.setVisibility(4);
        }
        return this.mHorizontalProgressBar;
    }

    private ImageView getRightIconView() {
        ImageView imageView = this.mRightIconView;
        if (imageView != null) {
            return imageView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.right_icon);
        this.mRightIconView = imageView2;
        return imageView2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnPanelClosed(int i, PanelFeatureState panelFeatureState, Menu menu) {
        Window.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        if (menu == null) {
            if (panelFeatureState == null && i >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (i < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.menu;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.isOpen) && !isDestroyed()) {
            callback.onPanelClosed(i, menu);
        }
    }

    private boolean isTvUserSetupComplete() {
        return (Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0) != 0) & (Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.TV_USER_SETUP_COMPLETE, 0) != 0);
    }

    private boolean launchDefaultSearch(KeyEvent keyEvent) {
        boolean zOnSearchRequested = false;
        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) && !isTvUserSetupComplete()) {
            return false;
        }
        Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            int deviceId = keyEvent.getDeviceId();
            try {
                zOnSearchRequested = callback.onSearchRequested(deviceId != 0 ? new SearchEvent(InputDevice.getDevice(deviceId)) : null);
            } catch (AbstractMethodError e) {
                Log.e(TAG, "WindowCallback " + callback.getClass().getName() + " does not implement method onSearchRequested(SearchEvent); fa", e);
                zOnSearchRequested = callback.onSearchRequested();
            }
        }
        if (zOnSearchRequested || (getContext().getResources().getConfiguration().uiMode & 15) != 4) {
            return zOnSearchRequested;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Intent.EXTRA_ASSIST_INPUT_DEVICE_ID, keyEvent.getDeviceId());
        bundle.putLong(Intent.EXTRA_TIME, keyEvent.getEventTime());
        bundle.putBoolean(Intent.EXTRA_ASSIST_INPUT_HINT_KEYBOARD, true);
        ((SearchManager) getContext().getSystemService("search")).launchAssist(bundle);
        return true;
    }

    @Override // android.view.Window
    public void setVolumeControlStream(int i) {
        this.mVolumeControlStreamType = i;
    }

    @Override // android.view.Window
    public int getVolumeControlStream() {
        return this.mVolumeControlStreamType;
    }

    @Override // android.view.Window
    public void setMediaController(MediaController mediaController) {
        this.mMediaController = mediaController;
        if (mediaController != null && this.mOnModeChangedListener == null) {
            this.mAudioMode = getAudioManager().getMode();
            this.mOnModeChangedListener = new AudioManager.OnModeChangedListener() { // from class: com.android.internal.policy.PhoneWindow$$ExternalSyntheticLambda0
                @Override // android.media.AudioManager.OnModeChangedListener
                public final void onModeChanged(int i) {
                    this.f$0.lambda$setMediaController$1(i);
                }
            };
            getAudioManager().addOnModeChangedListener(getContext().getMainExecutor(), this.mOnModeChangedListener);
        } else {
            if (this.mOnModeChangedListener == null || mediaController != null) {
                return;
            }
            getAudioManager().removeOnModeChangedListener(this.mOnModeChangedListener);
            this.mOnModeChangedListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMediaController$1(int i) {
        this.mAudioMode = i;
    }

    @Override // android.view.Window
    public MediaController getMediaController() {
        return this.mMediaController;
    }

    @Override // android.view.Window
    public void setEnterTransition(Transition transition) {
        this.mEnterTransition = transition;
    }

    @Override // android.view.Window
    public void setReturnTransition(Transition transition) {
        this.mReturnTransition = transition;
    }

    @Override // android.view.Window
    public void setExitTransition(Transition transition) {
        this.mExitTransition = transition;
    }

    @Override // android.view.Window
    public void setReenterTransition(Transition transition) {
        this.mReenterTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementEnterTransition(Transition transition) {
        this.mSharedElementEnterTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementReturnTransition(Transition transition) {
        this.mSharedElementReturnTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementExitTransition(Transition transition) {
        this.mSharedElementExitTransition = transition;
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
        Transition transition = this.mReturnTransition;
        return transition == USE_DEFAULT_TRANSITION ? getEnterTransition() : transition;
    }

    @Override // android.view.Window
    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    @Override // android.view.Window
    public Transition getReenterTransition() {
        Transition transition = this.mReenterTransition;
        return transition == USE_DEFAULT_TRANSITION ? getExitTransition() : transition;
    }

    @Override // android.view.Window
    public Transition getSharedElementEnterTransition() {
        return this.mSharedElementEnterTransition;
    }

    @Override // android.view.Window
    public Transition getSharedElementReturnTransition() {
        Transition transition = this.mSharedElementReturnTransition;
        return transition == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : transition;
    }

    @Override // android.view.Window
    public Transition getSharedElementExitTransition() {
        return this.mSharedElementExitTransition;
    }

    @Override // android.view.Window
    public Transition getSharedElementReenterTransition() {
        Transition transition = this.mSharedElementReenterTransition;
        return transition == USE_DEFAULT_TRANSITION ? getSharedElementExitTransition() : transition;
    }

    @Override // android.view.Window
    public void setAllowEnterTransitionOverlap(boolean z) {
        this.mAllowEnterTransitionOverlap = Boolean.valueOf(z);
    }

    @Override // android.view.Window
    public boolean getAllowEnterTransitionOverlap() {
        Boolean bool = this.mAllowEnterTransitionOverlap;
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    @Override // android.view.Window
    public void setAllowReturnTransitionOverlap(boolean z) {
        this.mAllowReturnTransitionOverlap = Boolean.valueOf(z);
    }

    @Override // android.view.Window
    public boolean getAllowReturnTransitionOverlap() {
        Boolean bool = this.mAllowReturnTransitionOverlap;
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    @Override // android.view.Window
    public long getTransitionBackgroundFadeDuration() {
        long j = this.mBackgroundFadeDurationMillis;
        if (j < 0) {
            return 300L;
        }
        return j;
    }

    @Override // android.view.Window
    public void setTransitionBackgroundFadeDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("negative durations are not allowed");
        }
        this.mBackgroundFadeDurationMillis = j;
    }

    @Override // android.view.Window
    public void setSharedElementsUseOverlay(boolean z) {
        this.mSharedElementsUseOverlay = Boolean.valueOf(z);
    }

    @Override // android.view.Window
    public boolean getSharedElementsUseOverlay() {
        Boolean bool = this.mSharedElementsUseOverlay;
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
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

        DrawableFeatureState(int i) {
            this.featureId = i;
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

        PanelFeatureState(int i) {
            this.featureId = i;
        }

        public boolean isInListMode() {
            return this.isInExpandedMode || this.isCompact;
        }

        public boolean hasPanelItems() {
            View view = this.shownPanelView;
            if (view == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            return (this.isCompact || this.isInExpandedMode) ? this.listMenuPresenter.getAdapter().getCount() > 0 : ((ViewGroup) view).getChildCount() > 0;
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.iconMenuPresenter = null;
            this.listMenuPresenter = null;
        }

        void setStyle(Context context) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Theme);
            this.background = typedArrayObtainStyledAttributes.getResourceId(46, 0);
            this.fullBackground = typedArrayObtainStyledAttributes.getResourceId(47, 0);
            this.windowAnimations = typedArrayObtainStyledAttributes.getResourceId(93, 0);
            this.isCompact = typedArrayObtainStyledAttributes.getBoolean(331, false);
            this.listPresenterTheme = typedArrayObtainStyledAttributes.getResourceId(332, R.style.Theme_ExpandedMenu);
            typedArrayObtainStyledAttributes.recycle();
        }

        void setMenu(MenuBuilder menuBuilder) {
            MenuBuilder menuBuilder2 = this.menu;
            if (menuBuilder == menuBuilder2) {
                return;
            }
            if (menuBuilder2 != null) {
                menuBuilder2.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menuBuilder;
            if (menuBuilder != null) {
                IconMenuPresenter iconMenuPresenter = this.iconMenuPresenter;
                if (iconMenuPresenter != null) {
                    menuBuilder.addMenuPresenter(iconMenuPresenter);
                }
                ListMenuPresenter listMenuPresenter = this.listMenuPresenter;
                if (listMenuPresenter != null) {
                    menuBuilder.addMenuPresenter(listMenuPresenter);
                }
            }
        }

        MenuView getListMenuView(Context context, MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (!this.isCompact) {
                getIconMenuView(context, callback);
            }
            if (this.listMenuPresenter == null) {
                ListMenuPresenter listMenuPresenter = new ListMenuPresenter(R.layout.list_menu_item_layout, this.listPresenterTheme);
                this.listMenuPresenter = listMenuPresenter;
                listMenuPresenter.setCallback(callback);
                this.listMenuPresenter.setId(R.id.list_menu_presenter);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            IconMenuPresenter iconMenuPresenter = this.iconMenuPresenter;
            if (iconMenuPresenter != null) {
                this.listMenuPresenter.setItemIndexOffset(iconMenuPresenter.getNumActualItemsShown());
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        MenuView getIconMenuView(Context context, MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (this.iconMenuPresenter == null) {
                IconMenuPresenter iconMenuPresenter = new IconMenuPresenter(context);
                this.iconMenuPresenter = iconMenuPresenter;
                iconMenuPresenter.setCallback(callback);
                this.iconMenuPresenter.setId(R.id.icon_menu_presenter);
                this.menu.addMenuPresenter(this.iconMenuPresenter);
            }
            return this.iconMenuPresenter.getMenuView(this.decorView);
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

        void onRestoreInstanceState(Parcelable parcelable) {
            SavedState savedState = (SavedState) parcelable;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.wasLastExpanded = savedState.isInExpandedMode;
            this.frozenMenuState = savedState.menuState;
            this.createdPanelView = null;
            this.shownPanelView = null;
            this.decorView = null;
        }

        void applyFrozenState() {
            Bundle bundle;
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder == null || (bundle = this.frozenMenuState) == null) {
                return;
            }
            menuBuilder.restorePresenterStates(bundle);
            this.frozenMenuState = null;
        }

        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.readFromParcel(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            int featureId;
            boolean isInExpandedMode;
            boolean isOpen;
            Bundle menuState;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            private SavedState() {
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

            /* JADX INFO: Access modifiers changed from: private */
            public static SavedState readFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState();
                savedState.featureId = parcel.readInt();
                savedState.isOpen = parcel.readInt() == 1;
                savedState.isInExpandedMode = parcel.readInt() == 1;
                if (savedState.isOpen) {
                    savedState.menuState = parcel.readBundle();
                }
                return savedState;
            }
        }
    }

    static class RotationWatcher extends IRotationWatcher.Stub {
        private Handler mHandler;
        private boolean mIsWatching;
        private final Runnable mRotationChanged = new Runnable() { // from class: com.android.internal.policy.PhoneWindow.RotationWatcher.1
            @Override // java.lang.Runnable
            public void run() {
                RotationWatcher.this.dispatchRotationChanged();
            }
        };
        private final ArrayList<WeakReference<PhoneWindow>> mWindows = new ArrayList<>();

        RotationWatcher() {
        }

        @Override // android.view.IRotationWatcher
        public void onRotationChanged(int i) throws RemoteException {
            this.mHandler.post(this.mRotationChanged);
        }

        public void addWindow(PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                if (!this.mIsWatching) {
                    try {
                        WindowManagerHolder.sWindowManager.watchRotation(this, phoneWindow.getContext().getDisplayId());
                        this.mHandler = new Handler();
                        this.mIsWatching = true;
                    } catch (RemoteException e) {
                        Log.e(PhoneWindow.TAG, "Couldn't start watching for device rotation", e);
                    }
                    this.mWindows.add(new WeakReference<>(phoneWindow));
                } else {
                    this.mWindows.add(new WeakReference<>(phoneWindow));
                }
            }
        }

        public void removeWindow(PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    PhoneWindow phoneWindow2 = this.mWindows.get(i).get();
                    if (phoneWindow2 == null || phoneWindow2 == phoneWindow) {
                        this.mWindows.remove(i);
                    } else {
                        i++;
                    }
                }
            }
        }

        void dispatchRotationChanged() {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    PhoneWindow phoneWindow = this.mWindows.get(i).get();
                    if (phoneWindow != null) {
                        phoneWindow.onOptionsPanelRotationChanged();
                        i++;
                    } else {
                        this.mWindows.remove(i);
                    }
                }
            }
        }
    }

    public static final class PhoneWindowMenuCallback implements MenuBuilder.Callback, MenuPresenter.Callback {
        private static final int FEATURE_ID = 6;
        private boolean mShowDialogForSubmenu;
        private MenuDialogHelper mSubMenuHelper;
        private final PhoneWindow mWindow;

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }

        public PhoneWindowMenuCallback(PhoneWindow phoneWindow) {
            this.mWindow = phoneWindow;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder.getRootMenu() != menuBuilder) {
                onCloseSubMenu(menuBuilder);
            }
            if (z) {
                Window.Callback callback = this.mWindow.getCallback();
                if (callback != null && !this.mWindow.isDestroyed()) {
                    callback.onPanelClosed(6, menuBuilder);
                }
                if (menuBuilder == this.mWindow.mContextMenu) {
                    this.mWindow.dismissContextMenu();
                }
                MenuDialogHelper menuDialogHelper = this.mSubMenuHelper;
                if (menuDialogHelper != null) {
                    menuDialogHelper.dismiss();
                    this.mSubMenuHelper = null;
                }
            }
        }

        private void onCloseSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = this.mWindow.getCallback();
            if (callback == null || this.mWindow.isDestroyed()) {
                return;
            }
            callback.onPanelClosed(6, menuBuilder.getRootMenu());
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            Window.Callback callback = this.mWindow.getCallback();
            return (callback == null || this.mWindow.isDestroyed() || !callback.onMenuItemSelected(6, menuItem)) ? false : true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            menuBuilder.setCallback(this);
            if (!this.mShowDialogForSubmenu) {
                return false;
            }
            MenuDialogHelper menuDialogHelper = new MenuDialogHelper(menuBuilder);
            this.mSubMenuHelper = menuDialogHelper;
            menuDialogHelper.show(null);
            return true;
        }

        public void setShowDialogForSubmenu(boolean z) {
            this.mShowDialogForSubmenu = z;
        }
    }

    int getLocalFeaturesPrivate() {
        return super.getLocalFeatures();
    }

    @Override // android.view.Window
    protected void setDefaultWindowFormat(int i) {
        super.setDefaultWindowFormat(i);
    }

    void sendCloseSystemWindows() {
        sendCloseSystemWindows(getContext(), null);
    }

    void sendCloseSystemWindows(String str) {
        sendCloseSystemWindows(getContext(), str);
    }

    public static void sendCloseSystemWindows(Context context, String str) {
        if (ActivityManager.isSystemReady()) {
            try {
                ActivityManager.getService().closeSystemDialogs(str);
            } catch (RemoteException unused) {
            }
        }
    }

    public static void sendCloseSystemWindowsInDisplay(String str, int i) {
        if (ActivityManager.isSystemReady()) {
            try {
                if (i != -1) {
                    ActivityManager.getService().closeSystemDialogsInDisplay(str, i);
                } else {
                    ActivityManager.getService().closeSystemDialogs(str);
                }
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.Window
    public int getStatusBarColor() {
        return this.mStatusBarColor;
    }

    @Override // android.view.Window
    public void setStatusBarColor(int i) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        if (this.mStatusBarColor == i && this.mForcedStatusBarColor) {
            return;
        }
        this.mStatusBarColor = i;
        this.mForcedStatusBarColor = true;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
        if (getWindowControllerCallback() != null) {
            getWindowControllerCallback().updateStatusBarColor(i);
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
    public void setNavigationBarColor(int i) {
        if (this.mNavigationBarColor == i && this.mForcedNavigationBarColor) {
            return;
        }
        this.mNavigationBarColor = i;
        this.mForcedNavigationBarColor = true;
        this.mNavigationBarColorSpecified = true;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.getWindowInsetsController().setSystemBarsAppearance(0, 512);
            this.mDecor.updateColorViews(null, false);
            updateForceLightNavigationBar();
        }
        if (this.mEdgeToEdgeEnforced || getWindowControllerCallback() == null) {
            return;
        }
        getWindowControllerCallback().updateNavigationBarColor(i);
    }

    @Override // android.view.Window
    public void setNavigationBarDividerColor(int i) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        this.mNavigationBarDividerColor = i;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public int getNavigationBarDividerColor() {
        return this.mNavigationBarDividerColor;
    }

    @Override // android.view.Window
    public void setStatusBarContrastEnforced(boolean z) {
        this.mEnsureStatusBarContrastWhenTransparent = z;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public boolean isStatusBarContrastEnforced() {
        return this.mEnsureStatusBarContrastWhenTransparent;
    }

    @Override // android.view.Window
    public void setNavigationBarContrastEnforced(boolean z) {
        this.mEnsureNavigationBarContrastWhenTransparent = z;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public boolean isNavigationBarContrastEnforced() {
        return this.mEnsureNavigationBarContrastWhenTransparent;
    }

    public void setIsStartingWindow(boolean z) {
        this.mIsStartingWindow = z;
    }

    @Override // android.view.Window
    public void setTheme(int i) {
        this.mTheme = i;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            Context context = decorView.getContext();
            if (context instanceof DecorContext) {
                context.setTheme(i);
            }
        }
    }

    @Override // android.view.Window
    public void setAttributes(WindowManager.LayoutParams layoutParams) {
        super.setAttributes(layoutParams);
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateLogTag(layoutParams);
        }
    }

    @Override // android.view.Window
    public WindowInsetsController getInsetsController() {
        return this.mDecor.getWindowInsetsController();
    }

    @Override // android.view.Window
    public void setSystemGestureExclusionRects(List<Rect> list) {
        getViewRootImpl().setRootSystemGestureExclusionRects(list);
    }

    @Override // android.view.Window
    public List<Rect> getSystemGestureExclusionRects() {
        return getViewRootImpl().getRootSystemGestureExclusionRects();
    }

    @Override // android.view.Window
    public void setDecorFitsSystemWindows(boolean z) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        this.mDecorFitsSystemWindows = z;
        applyDecorFitsSystemWindows();
    }

    @Override // android.view.Window
    public boolean decorFitsSystemWindows() {
        return this.mDecorFitsSystemWindows;
    }

    private void applyDecorFitsSystemWindows() {
        ViewRootImpl viewRootImplOrNull = getViewRootImplOrNull();
        if (viewRootImplOrNull != null) {
            viewRootImplOrNull.setOnContentApplyWindowInsetsListener(this.mDecorFitsSystemWindows ? sDefaultContentInsetsApplier : null);
        }
    }

    @Override // android.view.Window
    public void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        getViewRootImpl().dispatchScrollCaptureRequest(iScrollCaptureResponseListener);
    }

    @Override // android.view.Window
    public void registerScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
        getViewRootImpl().addScrollCaptureCallback(scrollCaptureCallback);
    }

    @Override // android.view.Window
    public void unregisterScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
        getViewRootImpl().removeScrollCaptureCallback(scrollCaptureCallback);
    }

    @Override // android.view.Window
    public View getStatusBarBackgroundView() {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            return decorView.getStatusBarBackgroundView();
        }
        return null;
    }

    @Override // android.view.Window
    public View getNavigationBarBackgroundView() {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            return decorView.getNavigationBarBackgroundView();
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

    public void setSettingsNavigationBarColor(int i) {
        this.mSettingsNavigationBarColor = i;
    }

    public void updateDefaultNavigationBarColor() {
        int defaultNavigationBarColor = getDefaultNavigationBarColor();
        int i = this.mNavigationBarColor;
        boolean z = (i != this.mDefaultNavigationBarColor || i == defaultNavigationBarColor || this.mForcedNavigationBarColor) ? false : true;
        this.mDefaultNavigationBarColor = defaultNavigationBarColor;
        if (z) {
            this.mNavigationBarColor = defaultNavigationBarColor;
            DecorView decorView = this.mDecor;
            if (decorView != null) {
                decorView.updateColorViews(null, false);
            }
        }
        updateForceLightNavigationBar();
    }

    private int getDefaultNavigationBarColor() {
        if (this.mThemeApplied && Settings.Global.getInt(getContext().getContentResolver(), "navigationbar_use_theme_default", 0) != 0) {
            return getContext().getResources().getColor(R.color.tw_navigation_bar_color);
        }
        if (isNightMode()) {
            return this.mDeviceDefaultNavigationBarColor;
        }
        return this.mSettingsNavigationBarColor;
    }

    private boolean isNightMode() {
        return (getContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public void dumpColors(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2 = str + "  ";
        printWriter.print(str);
        printWriter.println("PhoneWindow:");
        printWriter.print(str2);
        printWriter.print("mStatusBarColor=0x");
        printWriter.print(Integer.toHexString(this.mStatusBarColor).toUpperCase());
        printWriter.print(" mForcedStatusBarColor=");
        printWriter.println(this.mForcedStatusBarColor);
        printWriter.print(str2);
        printWriter.print("mNavigationBarColor=0x");
        printWriter.print(Integer.toHexString(this.mNavigationBarColor).toUpperCase());
        printWriter.print(" mForcedNavigationBarColor=");
        printWriter.println(this.mForcedNavigationBarColor);
        printWriter.print(str2);
        printWriter.print("mDeviceDefaultNavigationBarColor=0x");
        printWriter.println(Integer.toHexString(this.mDeviceDefaultNavigationBarColor).toUpperCase());
        printWriter.print(str2);
        printWriter.print("mDefaultNavigationBarColor=0x");
        printWriter.println(Integer.toHexString(this.mDefaultNavigationBarColor).toUpperCase());
        printWriter.print(str2);
        printWriter.print("mSettingsNavigationBarColor=0x");
        printWriter.println(Integer.toHexString(this.mSettingsNavigationBarColor).toUpperCase());
        printWriter.print(str2);
        printWriter.print("mThemeApplied=");
        printWriter.println(this.mThemeApplied);
    }

    public int getDeviceDefaultNavigationBarColor() {
        return this.mDeviceDefaultNavigationBarColor;
    }

    void updateDeviceDefaultNavigationBarColor() {
        this.mDeviceDefaultNavigationBarColor = getContext().getResources().getColor(R.color.navbar_light_theme_color, null);
    }

    private boolean needLightNavigationBar(int i) throws Resources.NotFoundException {
        if (this.mThemeApplied && Settings.Global.getInt(getContext().getContentResolver(), "navigationbar_use_theme_default", 0) != 0) {
            boolean z = getContext().getResources().getBoolean(R.bool.sem_window_light_navigation_bar);
            if (!z) {
                getDecorView().setSystemUiVisibility(getDecorView().getSystemUiVisibility() & (-17));
            }
            return z;
        }
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        return fArr[1] < 0.3f && fArr[2] >= 0.88f;
    }

    private void setForceLightNavigationBar(boolean z) {
        WindowManager.LayoutParams attributes = getAttributes();
        int i = attributes.samsungFlags;
        if (z) {
            attributes.samsungFlags |= 1048576;
        } else {
            attributes.samsungFlags &= -1048577;
        }
        if (i != attributes.samsungFlags) {
            Log.d(TAG, "forceLight changed to " + z + " [" + ((Object) attributes.getTitle()) + "] from " + Debug.getCallers(5));
            setAttributes(attributes);
            DecorView decorView = this.mDecor;
            if (decorView == null || decorView.getViewRootImpl() == null) {
                return;
            }
            this.mDecor.getViewRootImpl().requestRecomputeViewAttributes();
        }
    }

    void updateForceLightNavigationBar() {
        int i;
        boolean zNeedLightNavigationBar;
        DecorView decorView = this.mDecor;
        if ((decorView != null && decorView.isDrawLegacyNavigationBarBackground()) || (i = this.mNavigationBarColor) == this.mDeviceDefaultNavigationBarColor) {
            boolean zIsNightMode = isNightMode();
            zNeedLightNavigationBar = !zIsNightMode;
            setForceLightNavigationBar(zNeedLightNavigationBar);
        } else {
            zNeedLightNavigationBar = i == this.mDefaultNavigationBarColor ? needLightNavigationBar(i) : false;
            setForceLightNavigationBar(zNeedLightNavigationBar);
        }
    }

    public void setActivityCurrentConfig(Configuration configuration) {
        Configuration configuration2 = this.mActivityCurrentConfig;
        boolean z = false;
        int windowingMode = configuration2 != null ? configuration2.windowConfiguration.getWindowingMode() : 0;
        this.mActivityCurrentConfig = configuration;
        if (this.mDecor == null || windowingMode == configuration.windowConfiguration.getWindowingMode()) {
            return;
        }
        if (configuration.windowConfiguration.getWindowingMode() == 6 && WindowConfiguration.isSplitScreenWindowingMode(configuration.windowConfiguration)) {
            z = true;
        }
        this.mDecor.onWindowingModeChanged(configuration.windowConfiguration.getWindowingMode(), z);
    }
}
