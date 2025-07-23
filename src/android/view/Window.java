package android.view;

import android.Manifest;
import android.annotation.SystemApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.ActionMode;
import android.view.InputQueue;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.flags.Flags;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.R;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class Window {
    public static final int DECOR_CAPTION_SHADE_AUTO = 0;
    public static final int DECOR_CAPTION_SHADE_DARK = 2;
    public static final int DECOR_CAPTION_SHADE_LIGHT = 1;

    @Deprecated
    protected static final int DEFAULT_FEATURES = 65;
    public static final int FEATURE_ACTION_BAR = 8;
    public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
    public static final int FEATURE_ACTIVITY_TRANSITIONS = 13;
    public static final int FEATURE_CONTENT_TRANSITIONS = 12;
    public static final int FEATURE_CONTEXT_MENU = 6;
    public static final int FEATURE_CUSTOM_TITLE = 7;

    @Deprecated
    public static final int FEATURE_INDETERMINATE_PROGRESS = 5;
    public static final int FEATURE_LEFT_ICON = 3;
    public static final int FEATURE_MAX = 13;
    public static final int FEATURE_NO_TITLE = 1;
    public static final int FEATURE_OPTIONS_PANEL = 0;

    @Deprecated
    public static final int FEATURE_PROGRESS = 2;
    public static final int FEATURE_RIGHT_ICON = 4;

    @Deprecated
    public static final int FEATURE_SWIPE_TO_DISMISS = 11;
    public static final int ID_ANDROID_CONTENT = 16908290;
    public static final String NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME = "android:navigation:background";

    @Deprecated
    public static final int PROGRESS_END = 10000;

    @Deprecated
    public static final int PROGRESS_INDETERMINATE_OFF = -4;

    @Deprecated
    public static final int PROGRESS_INDETERMINATE_ON = -3;

    @Deprecated
    public static final int PROGRESS_SECONDARY_END = 30000;

    @Deprecated
    public static final int PROGRESS_SECONDARY_START = 20000;

    @Deprecated
    public static final int PROGRESS_START = 0;

    @Deprecated
    public static final int PROGRESS_VISIBILITY_OFF = -2;

    @Deprecated
    public static final int PROGRESS_VISIBILITY_ON = -1;
    public static final String STATUS_BAR_BACKGROUND_TRANSITION_NAME = "android:status:background";
    protected static boolean sToolkitSetFrameRateReadOnlyFlagValue = Flags.toolkitSetFrameRateReadOnly();
    private Window mActiveChild;
    private String mAppName;
    private IBinder mAppToken;
    private Callback mCallback;
    private Window mContainer;
    private final Context mContext;
    private DecorCallback mDecorCallback;
    private boolean mDestroyed;
    private int mFeatures;
    private boolean mHardwareAccelerated;
    private boolean mIsPreserved;
    private int mLocalFeatures;
    private OnRestrictedCaptionAreaChangedListener mOnRestrictedCaptionAreaChangedListener;
    private OnWindowDismissedCallback mOnWindowDismissedCallback;
    private OnWindowSwipeDismissedCallback mOnWindowSwipeDismissedCallback;
    private Rect mRestrictedCaptionAreaRect;
    private int mSystemBarAppearance;
    private WindowControllerCallback mWindowControllerCallback;
    private WindowManager mWindowManager;
    private TypedArray mWindowStyle;
    private boolean mIsActive = false;
    private boolean mHasChildren = false;
    private boolean mCloseOnTouchOutside = false;
    private boolean mSetCloseOnTouchOutside = false;
    private int mForcedWindowFlags = 0;
    private boolean mHaveWindowFormat = false;
    private boolean mHaveDimAmount = false;
    private int mDefaultWindowFormat = -1;
    private boolean mHasSoftInputMode = false;
    private boolean mOverlayWithDecorCaptionEnabled = true;
    private boolean mCloseOnSwipeEnabled = false;
    private final WindowManager.LayoutParams mWindowAttributes = new WindowManager.LayoutParams();

    public interface Callback {
        boolean dispatchGenericMotionEvent(MotionEvent motionEvent);

        boolean dispatchKeyEvent(KeyEvent keyEvent);

        boolean dispatchKeyShortcutEvent(KeyEvent keyEvent);

        boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        boolean dispatchTouchEvent(MotionEvent motionEvent);

        boolean dispatchTrackballEvent(MotionEvent motionEvent);

        void onActionModeFinished(ActionMode actionMode);

        void onActionModeStarted(ActionMode actionMode);

        void onAttachedToWindow();

        void onContentChanged();

        boolean onCreatePanelMenu(int i, Menu menu);

        View onCreatePanelView(int i);

        void onDetachedFromWindow();

        boolean onMenuItemSelected(int i, MenuItem menuItem);

        boolean onMenuOpened(int i, Menu menu);

        void onPanelClosed(int i, Menu menu);

        default void onPointerCaptureChanged(boolean z) {
        }

        boolean onPreparePanel(int i, View view, Menu menu);

        default void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
        }

        boolean onSearchRequested();

        boolean onSearchRequested(SearchEvent searchEvent);

        void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams);

        void onWindowFocusChanged(boolean z);

        ActionMode onWindowStartingActionMode(ActionMode.Callback callback);

        ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i);
    }

    public interface DecorCallback {
        boolean onDrawLegacyNavigationBarBackgroundChanged(boolean z);

        void onSystemBarAppearanceChanged(int i);
    }

    public interface OnContentApplyWindowInsetsListener {
        Pair<Insets, WindowInsets> onContentApplyWindowInsets(View view, WindowInsets windowInsets);
    }

    public interface OnFrameMetricsAvailableListener {
        void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int i);
    }

    public interface OnRestrictedCaptionAreaChangedListener {
        void onRestrictedCaptionAreaChanged(Rect rect);
    }

    public interface OnWindowDismissedCallback {
        void onWindowDismissed(boolean z, boolean z2);
    }

    public interface OnWindowSwipeDismissedCallback {
        void onWindowSwipeDismissed();
    }

    public interface WindowControllerCallback {
        void enterPictureInPictureModeIfPossible();

        boolean isTaskRoot();

        void toggleFreeformWindowingMode();

        void updateNavigationBarColor(int i);

        void updateStatusBarColor(int i);

        void updateSystemBarsAppearance(int i);
    }

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void alwaysReadCloseOnTouchAttr();

    public abstract void clearContentView();

    public abstract void closeAllPanels();

    public abstract void closePanel(int i);

    public boolean decorFitsSystemWindows() {
        return false;
    }

    public boolean getAllowEnterTransitionOverlap() {
        return true;
    }

    public boolean getAllowReturnTransitionOverlap() {
        return true;
    }

    public Scene getContentScene() {
        return null;
    }

    public abstract View getCurrentFocus();

    public abstract View getDecorView();

    public float getElevation() {
        return 0.0f;
    }

    public Transition getEnterTransition() {
        return null;
    }

    public Transition getExitTransition() {
        return null;
    }

    public WindowInsetsController getInsetsController() {
        return null;
    }

    public abstract LayoutInflater getLayoutInflater();

    public MediaController getMediaController() {
        return null;
    }

    public View getNavigationBarBackgroundView() {
        return null;
    }

    @Deprecated
    public abstract int getNavigationBarColor();

    @Deprecated
    public int getNavigationBarDividerColor() {
        return 0;
    }

    public Transition getReenterTransition() {
        return null;
    }

    public Transition getReturnTransition() {
        return null;
    }

    public AttachedSurfaceControl getRootSurfaceControl() {
        return null;
    }

    public Transition getSharedElementEnterTransition() {
        return null;
    }

    public Transition getSharedElementExitTransition() {
        return null;
    }

    public Transition getSharedElementReenterTransition() {
        return null;
    }

    public Transition getSharedElementReturnTransition() {
        return null;
    }

    public boolean getSharedElementsUseOverlay() {
        return true;
    }

    public View getStatusBarBackgroundView() {
        return null;
    }

    @Deprecated
    public abstract int getStatusBarColor();

    public long getTransitionBackgroundFadeDuration() {
        return 0L;
    }

    public TransitionManager getTransitionManager() {
        return null;
    }

    public abstract int getVolumeControlStream();

    public void injectInputEvent(InputEvent inputEvent) {
    }

    public abstract void invalidatePanelMenu(int i);

    public abstract boolean isFloating();

    public boolean isNavigationBarContrastEnforced() {
        return false;
    }

    public abstract boolean isShortcutKey(int i, KeyEvent keyEvent);

    @Deprecated
    public boolean isStatusBarContrastEnforced() {
        return false;
    }

    protected abstract void onActive();

    public abstract void onConfigurationChanged(Configuration configuration);

    protected void onDestroy() {
    }

    public abstract void onMultiWindowModeChanged();

    public abstract void onPictureInPictureModeChanged(boolean z);

    public abstract void openPanel(int i, KeyEvent keyEvent);

    public abstract View peekDecorView();

    public abstract boolean performContextMenuIdentifierAction(int i, int i2);

    public abstract boolean performPanelIdentifierAction(int i, int i2, int i3);

    public abstract boolean performPanelShortcut(int i, int i2, KeyEvent keyEvent, int i3);

    public void registerScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
    }

    public void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
    }

    public abstract void restoreHierarchyState(Bundle bundle);

    public abstract Bundle saveHierarchyState();

    public void setAllowEnterTransitionOverlap(boolean z) {
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
    }

    public void setBackgroundBlurRadius(int i) {
    }

    public abstract void setBackgroundDrawable(Drawable drawable);

    public abstract void setChildDrawable(int i, Drawable drawable);

    public abstract void setChildInt(int i, int i2);

    public void setClipToOutline(boolean z) {
    }

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void setDecorCaptionShade(int i);

    public void setDecorFitsSystemWindows(boolean z) {
    }

    public void setDefaultIcon(int i) {
    }

    public void setDefaultLogo(int i) {
    }

    public void setElevation(float f) {
    }

    public void setEnterTransition(Transition transition) {
    }

    public void setExitTransition(Transition transition) {
    }

    public abstract void setFeatureDrawable(int i, Drawable drawable);

    public abstract void setFeatureDrawableAlpha(int i, int i2);

    public abstract void setFeatureDrawableResource(int i, int i2);

    public abstract void setFeatureDrawableUri(int i, Uri uri);

    public abstract void setFeatureInt(int i, int i2);

    public void setIcon(int i) {
    }

    public void setLocalFocus(boolean z, boolean z2) {
    }

    public void setLogo(int i) {
    }

    public void setMediaController(MediaController mediaController) {
    }

    @Deprecated
    public abstract void setNavigationBarColor(int i);

    public void setNavigationBarContrastEnforced(boolean z) {
    }

    @Deprecated
    public void setNavigationBarDividerColor(int i) {
    }

    public void setReenterTransition(Transition transition) {
    }

    public abstract void setResizingCaptionDrawable(Drawable drawable);

    public void setReturnTransition(Transition transition) {
    }

    public void setSharedElementEnterTransition(Transition transition) {
    }

    public void setSharedElementExitTransition(Transition transition) {
    }

    public void setSharedElementReenterTransition(Transition transition) {
    }

    public void setSharedElementReturnTransition(Transition transition) {
    }

    public void setSharedElementsUseOverlay(boolean z) {
    }

    @Deprecated
    public abstract void setStatusBarColor(int i);

    @Deprecated
    public void setStatusBarContrastEnforced(boolean z) {
    }

    public void setTheme(int i) {
    }

    public abstract void setTitle(CharSequence charSequence);

    @Deprecated
    public abstract void setTitleColor(int i);

    public void setTransitionBackgroundFadeDuration(long j) {
    }

    public void setUiOptions(int i) {
    }

    public void setUiOptions(int i, int i2) {
    }

    public abstract void setVolumeControlStream(int i);

    public abstract boolean superDispatchGenericMotionEvent(MotionEvent motionEvent);

    public abstract boolean superDispatchKeyEvent(KeyEvent keyEvent);

    public abstract boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent);

    public abstract boolean superDispatchTouchEvent(MotionEvent motionEvent);

    public abstract boolean superDispatchTrackballEvent(MotionEvent motionEvent);

    public abstract void takeInputQueue(InputQueue.Callback callback);

    public abstract void takeKeyEvents(boolean z);

    public abstract void takeSurface(SurfaceHolder.Callback2 callback2);

    public abstract void togglePanel(int i, KeyEvent keyEvent);

    public void unregisterScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
    }

    public boolean isPreserved() {
        return this.mIsPreserved;
    }

    public void setPreserved(boolean z) {
        this.mIsPreserved = z;
    }

    public Window(Context context) {
        this.mContext = context;
        int defaultFeatures = getDefaultFeatures(context);
        this.mLocalFeatures = defaultFeatures;
        this.mFeatures = defaultFeatures;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final TypedArray getWindowStyle() {
        TypedArray typedArray;
        synchronized (this) {
            if (this.mWindowStyle == null) {
                this.mWindowStyle = this.mContext.obtainStyledAttributes(R.styleable.Window);
            }
            typedArray = this.mWindowStyle;
        }
        return typedArray;
    }

    public void setContainer(Window window) {
        this.mContainer = window;
        if (window != null) {
            this.mFeatures |= 2;
            this.mLocalFeatures |= 2;
            window.mHasChildren = true;
        }
    }

    public final Window getContainer() {
        return this.mContainer;
    }

    public final boolean hasChildren() {
        return this.mHasChildren;
    }

    public final void destroy() {
        this.mDestroyed = true;
        onDestroy();
    }

    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    public void setWindowManager(WindowManager windowManager, IBinder iBinder, String str) {
        setWindowManager(windowManager, iBinder, str, false);
    }

    public void setWindowManager(WindowManager windowManager, IBinder iBinder, String str, boolean z) {
        this.mAppToken = iBinder;
        this.mAppName = str;
        this.mHardwareAccelerated = z;
        if (windowManager == null) {
            windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        this.mWindowManager = ((WindowManagerImpl) windowManager).createLocalWindowManager(this);
    }

    void adjustLayoutParamsForSubWindow(WindowManager.LayoutParams layoutParams) {
        CharSequence charSequence;
        View peekDecorView;
        CharSequence title = layoutParams.getTitle();
        if (layoutParams.type >= 1000 && layoutParams.type <= 1999) {
            if (layoutParams.token == null && (peekDecorView = peekDecorView()) != null) {
                layoutParams.token = peekDecorView.getWindowToken();
            }
            if (title == null || title.length() == 0) {
                StringBuilder sb = new StringBuilder(32);
                if (layoutParams.type == 1001) {
                    sb.append("Media");
                } else if (layoutParams.type == 1004) {
                    sb.append("MediaOvr");
                } else if (layoutParams.type == 1000) {
                    sb.append("Panel");
                } else if (layoutParams.type == 1002) {
                    sb.append("SubPanel");
                } else if (layoutParams.type == 1005) {
                    sb.append("AboveSubPanel");
                } else if (layoutParams.type == 1003) {
                    sb.append("AtchDlg");
                } else {
                    sb.append(layoutParams.type);
                }
                if (this.mAppName != null) {
                    sb.append(":");
                    sb.append(this.mAppName);
                }
                layoutParams.setTitle(sb);
            }
        } else if (layoutParams.type >= 2000 && layoutParams.type <= 2999) {
            if (title == null || title.length() == 0) {
                StringBuilder sb2 = new StringBuilder(32);
                sb2.append("Sys");
                sb2.append(layoutParams.type);
                if (this.mAppName != null) {
                    sb2.append(":");
                    sb2.append(this.mAppName);
                }
                layoutParams.setTitle(sb2);
            }
        } else {
            if (layoutParams.token == null) {
                Window window = this.mContainer;
                layoutParams.token = window == null ? this.mAppToken : window.mAppToken;
            }
            if ((title == null || title.length() == 0) && (charSequence = this.mAppName) != null) {
                layoutParams.setTitle(charSequence);
            }
        }
        if (layoutParams.packageName == null) {
            layoutParams.packageName = this.mContext.getPackageName();
        }
        if (this.mHardwareAccelerated || (this.mWindowAttributes.flags & 16777216) != 0) {
            layoutParams.flags |= 16777216;
        }
    }

    public WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public final Callback getCallback() {
        return this.mCallback;
    }

    public final void addOnFrameMetricsAvailableListener(OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, Handler handler) {
        View decorView = getDecorView();
        if (decorView == null) {
            throw new IllegalStateException("can't observe a Window without an attached view");
        }
        if (onFrameMetricsAvailableListener == null) {
            throw new NullPointerException("listener cannot be null");
        }
        decorView.addFrameMetricsListener(this, onFrameMetricsAvailableListener, handler);
    }

    public final void removeOnFrameMetricsAvailableListener(OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (getDecorView() != null) {
            getDecorView().removeFrameMetricsListener(onFrameMetricsAvailableListener);
        }
    }

    public final void setOnWindowDismissedCallback(OnWindowDismissedCallback onWindowDismissedCallback) {
        this.mOnWindowDismissedCallback = onWindowDismissedCallback;
    }

    public final void dispatchOnWindowDismissed(boolean z, boolean z2) {
        OnWindowDismissedCallback onWindowDismissedCallback = this.mOnWindowDismissedCallback;
        if (onWindowDismissedCallback != null) {
            onWindowDismissedCallback.onWindowDismissed(z, z2);
        }
    }

    public final void setOnWindowSwipeDismissedCallback(OnWindowSwipeDismissedCallback onWindowSwipeDismissedCallback) {
        this.mOnWindowSwipeDismissedCallback = onWindowSwipeDismissedCallback;
    }

    public final void dispatchOnWindowSwipeDismissed() {
        OnWindowSwipeDismissedCallback onWindowSwipeDismissedCallback = this.mOnWindowSwipeDismissedCallback;
        if (onWindowSwipeDismissedCallback != null) {
            onWindowSwipeDismissedCallback.onWindowSwipeDismissed();
        }
    }

    public final void setWindowControllerCallback(WindowControllerCallback windowControllerCallback) {
        this.mWindowControllerCallback = windowControllerCallback;
    }

    public final WindowControllerCallback getWindowControllerCallback() {
        return this.mWindowControllerCallback;
    }

    public final void setDecorCallback(DecorCallback decorCallback) {
        this.mDecorCallback = decorCallback;
    }

    public final void setSystemBarAppearance(int i) {
        this.mSystemBarAppearance = i;
    }

    public final int getSystemBarAppearance() {
        return this.mSystemBarAppearance;
    }

    public final void dispatchOnSystemBarAppearanceChanged(int i) {
        setSystemBarAppearance(i);
        DecorCallback decorCallback = this.mDecorCallback;
        if (decorCallback != null) {
            decorCallback.onSystemBarAppearanceChanged(i);
        }
        WindowControllerCallback windowControllerCallback = this.mWindowControllerCallback;
        if (windowControllerCallback != null) {
            windowControllerCallback.updateSystemBarsAppearance(i);
        }
    }

    public final boolean onDrawLegacyNavigationBarBackgroundChanged(boolean z) {
        DecorCallback decorCallback = this.mDecorCallback;
        if (decorCallback == null) {
            return false;
        }
        return decorCallback.onDrawLegacyNavigationBarBackgroundChanged(z);
    }

    public final void setRestrictedCaptionAreaListener(OnRestrictedCaptionAreaChangedListener onRestrictedCaptionAreaChangedListener) {
        this.mOnRestrictedCaptionAreaChangedListener = onRestrictedCaptionAreaChangedListener;
        this.mRestrictedCaptionAreaRect = onRestrictedCaptionAreaChangedListener != null ? new Rect() : null;
    }

    public final void setHideOverlayWindows(boolean z) {
        if (this.mContext.checkSelfPermission(Manifest.permission.HIDE_NON_SYSTEM_OVERLAY_WINDOWS) != 0 && this.mContext.checkSelfPermission(Manifest.permission.HIDE_OVERLAY_WINDOWS) != 0) {
            throw new SecurityException("Permission denial: setHideOverlayWindows: HIDE_OVERLAY_WINDOWS");
        }
        setPrivateFlags(z ? 524288 : 0, 524288);
    }

    public void setLayout(int i, int i2) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.width = i;
        attributes.height = i2;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setGravity(int i) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.gravity = i;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setType(int i) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.type = i;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setFormat(int i) {
        WindowManager.LayoutParams attributes = getAttributes();
        if (i != 0) {
            attributes.format = i;
            this.mHaveWindowFormat = true;
        } else {
            attributes.format = this.mDefaultWindowFormat;
            this.mHaveWindowFormat = false;
        }
        dispatchWindowAttributesChanged(attributes);
    }

    public void setWindowAnimations(int i) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.windowAnimations = i;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setSoftInputMode(int i) {
        WindowManager.LayoutParams attributes = getAttributes();
        if (i != 0) {
            attributes.softInputMode = i;
            this.mHasSoftInputMode = true;
        } else {
            this.mHasSoftInputMode = false;
        }
        dispatchWindowAttributesChanged(attributes);
    }

    public void addFlags(int i) {
        setFlags(i, i);
    }

    public void addPrivateFlags(int i) {
        setPrivateFlags(i, i);
    }

    @SystemApi
    public void addSystemFlags(int i) {
        addPrivateFlags(i);
    }

    public void clearFlags(int i) {
        setFlags(0, i);
    }

    public void setFlags(int i, int i2) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.flags = (i & i2) | (attributes.flags & (~i2));
        this.mForcedWindowFlags |= i2;
        dispatchWindowAttributesChanged(attributes);
    }

    private void setPrivateFlags(int i, int i2) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.privateFlags = (i & i2) | (attributes.privateFlags & (~i2));
        dispatchWindowAttributesChanged(attributes);
    }

    protected void dispatchWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onWindowAttributesChanged(layoutParams);
        }
    }

    public void setColorMode(int i) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.setColorMode(i);
        dispatchWindowAttributesChanged(attributes);
    }

    public void setDesiredHdrHeadroom(float f) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.setDesiredHdrHeadroom(f);
        dispatchWindowAttributesChanged(attributes);
    }

    public float getDesiredHdrHeadroom() {
        return getAttributes().getDesiredHdrHeadroom();
    }

    public void setFrameRateBoostOnTouchEnabled(boolean z) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            WindowManager.LayoutParams attributes = getAttributes();
            attributes.setFrameRateBoostOnTouchEnabled(z);
            dispatchWindowAttributesChanged(attributes);
        }
    }

    public boolean getFrameRateBoostOnTouchEnabled() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return getAttributes().getFrameRateBoostOnTouchEnabled();
        }
        return true;
    }

    public void setFrameRatePowerSavingsBalanced(boolean z) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            WindowManager.LayoutParams attributes = getAttributes();
            attributes.setFrameRatePowerSavingsBalanced(z);
            dispatchWindowAttributesChanged(attributes);
        }
    }

    public boolean isFrameRatePowerSavingsBalanced() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return getAttributes().isFrameRatePowerSavingsBalanced();
        }
        return false;
    }

    public void setPreferMinimalPostProcessing(boolean z) {
        this.mWindowAttributes.preferMinimalPostProcessing = z;
        dispatchWindowAttributesChanged(this.mWindowAttributes);
    }

    public int getColorMode() {
        return getAttributes().getColorMode();
    }

    public boolean isWideColorGamut() {
        return getColorMode() == 1 && getContext().getResources().getConfiguration().isScreenWideColorGamut();
    }

    public void setDimAmount(float f) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.dimAmount = f;
        this.mHaveDimAmount = true;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setAttributes(WindowManager.LayoutParams layoutParams) {
        this.mWindowAttributes.copyFrom(layoutParams);
        dispatchWindowAttributesChanged(this.mWindowAttributes);
    }

    public final WindowManager.LayoutParams getAttributes() {
        return this.mWindowAttributes;
    }

    protected final int getForcedWindowFlags() {
        return this.mForcedWindowFlags;
    }

    protected final boolean hasSoftInputMode() {
        return this.mHasSoftInputMode;
    }

    public void setCloseOnTouchOutside(boolean z) {
        this.mCloseOnTouchOutside = z;
        this.mSetCloseOnTouchOutside = true;
    }

    public void setCloseOnTouchOutsideIfNotSet(boolean z) {
        if (this.mSetCloseOnTouchOutside) {
            return;
        }
        this.mCloseOnTouchOutside = z;
        this.mSetCloseOnTouchOutside = true;
    }

    public boolean shouldCloseOnTouchOutside() {
        return this.mCloseOnTouchOutside;
    }

    public boolean shouldCloseOnTouch(Context context, MotionEvent motionEvent) {
        return this.mCloseOnTouchOutside && peekDecorView() != null && ((motionEvent.getAction() == 1 && isOutOfBounds(context, motionEvent)) || motionEvent.getAction() == 4);
    }

    public void setSustainedPerformanceMode(boolean z) {
        setPrivateFlags(z ? 65536 : 0, 65536);
    }

    public boolean isOutOfBounds(Context context, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        View decorView = getDecorView();
        int i = -scaledWindowTouchSlop;
        return x < i || y < i || x > decorView.getWidth() + scaledWindowTouchSlop || y > decorView.getHeight() + scaledWindowTouchSlop;
    }

    public boolean requestFeature(int i) {
        int i2 = 1 << i;
        int i3 = this.mFeatures | i2;
        this.mFeatures = i3;
        int i4 = this.mLocalFeatures;
        Window window = this.mContainer;
        this.mLocalFeatures = i4 | (window != null ? (~window.mFeatures) & i2 : i2);
        return (i3 & i2) != 0;
    }

    protected void removeFeature(int i) {
        int i2 = 1 << i;
        this.mFeatures &= ~i2;
        int i3 = this.mLocalFeatures;
        Window window = this.mContainer;
        if (window != null) {
            i2 &= ~window.mFeatures;
        }
        this.mLocalFeatures = (~i2) & i3;
    }

    public final void makeActive() {
        Window window = this.mContainer;
        if (window != null) {
            Window window2 = window.mActiveChild;
            if (window2 != null) {
                window2.mIsActive = false;
            }
            window.mActiveChild = this;
        }
        this.mIsActive = true;
        onActive();
    }

    public final boolean isActive() {
        return this.mIsActive;
    }

    public <T extends View> T findViewById(int i) {
        return (T) getDecorView().findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Window");
    }

    public void setBackgroundDrawableResource(int i) {
        setBackgroundDrawable(this.mContext.getDrawable(i));
    }

    protected final int getFeatures() {
        return this.mFeatures;
    }

    public static int getDefaultFeatures(Context context) {
        Resources resources = context.getResources();
        boolean z = resources.getBoolean(R.bool.config_defaultWindowFeatureOptionsPanel);
        return resources.getBoolean(R.bool.config_defaultWindowFeatureContextMenu) ? (z ? 1 : 0) | 64 : z ? 1 : 0;
    }

    public boolean hasFeature(int i) {
        return (getFeatures() & (1 << i)) != 0;
    }

    protected final int getLocalFeatures() {
        return this.mLocalFeatures;
    }

    protected void setDefaultWindowFormat(int i) {
        this.mDefaultWindowFormat = i;
        if (this.mHaveWindowFormat) {
            return;
        }
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.format = i;
        dispatchWindowAttributesChanged(attributes);
    }

    protected boolean haveDimAmount() {
        return this.mHaveDimAmount;
    }

    public void setTransitionManager(TransitionManager transitionManager) {
        throw new UnsupportedOperationException();
    }

    public void setSystemGestureExclusionRects(List<Rect> list) {
        throw new UnsupportedOperationException("window does not support gesture exclusion rects");
    }

    public List<Rect> getSystemGestureExclusionRects() {
        return Collections.EMPTY_LIST;
    }

    public void setOverlayWithDecorCaptionEnabled(boolean z) {
        this.mOverlayWithDecorCaptionEnabled = z;
    }

    public boolean isOverlayWithDecorCaptionEnabled() {
        return this.mOverlayWithDecorCaptionEnabled;
    }

    public void notifyRestrictedCaptionAreaCallback(int i, int i2, int i3, int i4) {
        if (this.mOnRestrictedCaptionAreaChangedListener != null) {
            this.mRestrictedCaptionAreaRect.set(i, i2, i3, i4);
            this.mOnRestrictedCaptionAreaChangedListener.onRestrictedCaptionAreaChanged(this.mRestrictedCaptionAreaRect);
        }
    }

    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }
}
