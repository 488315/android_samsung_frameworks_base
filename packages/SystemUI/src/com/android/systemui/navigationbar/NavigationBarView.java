package com.android.systemui.navigationbar;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.view.RotationPolicy;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda1;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.store.SystemBarProxy;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0;
import com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda3;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NavigationBarView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AutoHideController mAutoHideController;
    public KeyButtonDrawable mBackIcon;
    public NavigationBarTransitions mBarTransitions;
    public Executor mBgExecutor;
    public final SparseArray mButtonDispatchers;
    public final Configuration mConfiguration;
    public final ContextualButtonGroup mContextualButtonGroup;
    public int mCurrentRotation;
    public View mCurrentView;
    public final int mDarkIconColor;
    public final DeadZone mDeadZone;
    public int mDisabledFlags;
    public DisplayTracker mDisplayTracker;
    public KeyButtonDrawable mDockedIcon;
    public EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final FloatingRotationButton mFloatingRotationButton;
    public KeyButtonDrawable mHomeDefaultIcon;
    public View mHorizontal;
    public final boolean mImeCanRenderGesturalNavButtons;
    public boolean mImeDrawsImeNavBar;
    public boolean mInCarMode;
    public boolean mIsVertical;
    public boolean mLayoutTransitionsEnabled;
    public final Context mLightContext;
    public final int mLightIconColor;
    public int mNavBarMode;
    public int mNavigationIconHints;
    public NavigationBarInflaterView mNavigationInflaterView;
    public NavigationBar$$ExternalSyntheticLambda2 mOnVerticalChangedListener;
    public boolean mOverviewProxyEnabled;
    public PanelExpansionInteractor mPanelExpansionInteractor;
    public final NavigationBarView$$ExternalSyntheticLambda0 mPipListener;
    public final AnonymousClass1 mQuickStepAccessibilityDelegate;
    public KeyButtonDrawable mRecentIcon;
    public Optional mRecentsOptional;
    public final RotationButtonController mRotationButtonController;
    public final AnonymousClass2 mRotationButtonListener;
    public boolean mScreenOn;
    public boolean mScreenPinningActive;
    public final ScreenPinningNotify mScreenPinningNotify;
    public boolean mShowSwipeUpUi;
    public final Configuration mTmpLastConfiguration;
    public Gefingerpoken mTouchHandler;
    public final NavTransitionListener mTransitionListener;
    public NavigationBar$$ExternalSyntheticLambda2 mUpdateActiveTouchRegionsCallback;
    public View mVertical;
    public boolean mWakeAndUnlocking;
    public final NavBarStateManager navBarStateManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.navigationbar.NavigationBarView$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NavTransitionListener implements LayoutTransition.TransitionListener {
        public boolean mBackTransitioning;
        public long mDuration;
        public boolean mHomeAppearing;
        public TimeInterpolator mInterpolator;
        public long mStartDelay;

        public /* synthetic */ NavTransitionListener(NavigationBarView navigationBarView, int i) {
            this();
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public final void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
            if (view.getId() == R.id.back) {
                this.mBackTransitioning = false;
            } else if (view.getId() == R.id.home && i == 2) {
                this.mHomeAppearing = false;
            }
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public final void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
            if (view.getId() == R.id.back) {
                this.mBackTransitioning = true;
                return;
            }
            if (view.getId() == R.id.home && i == 2) {
                this.mHomeAppearing = true;
                this.mStartDelay = layoutTransition.getStartDelay(i);
                this.mDuration = layoutTransition.getDuration(i);
                this.mInterpolator = layoutTransition.getInterpolator(i);
            }
        }

        private NavTransitionListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.navigationbar.NavigationBarView$1] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda0] */
    public NavigationBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentView = null;
        this.mCurrentRotation = -1;
        this.mDisabledFlags = 0;
        this.mNavigationIconHints = 0;
        this.mTransitionListener = new NavTransitionListener(this, 0);
        this.mLayoutTransitionsEnabled = true;
        this.mInCarMode = false;
        this.mScreenOn = true;
        SparseArray sparseArray = new SparseArray();
        this.mButtonDispatchers = sparseArray;
        this.mRecentsOptional = Optional.empty();
        this.mScreenPinningActive = false;
        this.mImeCanRenderGesturalNavButtons = InputMethodService.canImeRenderGesturalNavButtons();
        this.mQuickStepAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.navigationbar.NavigationBarView.1
            public AccessibilityNodeInfo.AccessibilityAction mToggleOverviewAction;

            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (this.mToggleOverviewAction == null) {
                    this.mToggleOverviewAction = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_toggle_overview, NavigationBarView.this.getContext().getString(R.string.quick_step_accessibility_toggle_overview));
                }
                accessibilityNodeInfo.addAction(this.mToggleOverviewAction);
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != R.id.action_toggle_overview) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                NavigationBarView.this.mRecentsOptional.ifPresent(new SystemActions$$ExternalSyntheticLambda1());
                return true;
            }
        };
        this.mRotationButtonListener = new AnonymousClass2();
        this.mPipListener = new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final NavigationBarView navigationBarView = NavigationBarView.this;
                final Rect rect = (Rect) obj;
                int i = NavigationBarView.$r8$clinit;
                navigationBarView.getClass();
                navigationBarView.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavigationBarView navigationBarView2 = NavigationBarView.this;
                        navigationBarView2.mEdgeBackGestureHandler.mPipExcludedBounds.set(rect);
                    }
                });
            }
        };
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.darkIconTheme, context));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.lightIconTheme, context));
        this.mLightContext = contextThemeWrapper2;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(contextThemeWrapper2, R.attr.singleToneColor, 0);
        this.mLightIconColor = colorAttrDefaultColor;
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.singleToneColor, 0);
        this.mDarkIconColor = colorAttrDefaultColor2;
        this.mIsVertical = false;
        ContextualButtonGroup contextualButtonGroup = new ContextualButtonGroup(R.id.menu_container);
        this.mContextualButtonGroup = contextualButtonGroup;
        ContextualButton contextualButton = new ContextualButton(R.id.ime_switcher, contextThemeWrapper2, R.drawable.ic_ime_switcher_default);
        ContextualButton contextualButton2 = new ContextualButton(R.id.accessibility_button, contextThemeWrapper2, R.drawable.ic_sysbar_accessibility_button);
        contextualButtonGroup.addButton(contextualButton);
        contextualButtonGroup.addButton(contextualButton2);
        this.mFloatingRotationButton = new FloatingRotationButton(((FrameLayout) this).mContext, R.string.accessibility_rotate_button, R.layout.rotate_suggestion, R.id.rotate_suggestion, R.dimen.floating_rotation_button_min_margin, R.dimen.rounded_corner_content_padding, R.dimen.floating_rotation_button_taskbar_left_margin, R.dimen.floating_rotation_button_taskbar_bottom_margin, R.dimen.floating_rotation_button_diameter, R.dimen.key_button_ripple_max_width, R.bool.floating_rotation_button_position_left);
        this.mRotationButtonController = new RotationButtonController(contextThemeWrapper2, colorAttrDefaultColor, colorAttrDefaultColor2, R.drawable.ic_sysbar_rotate_button_ccw_start_0, R.drawable.ic_sysbar_rotate_button_ccw_start_90, R.drawable.ic_sysbar_rotate_button_cw_start_0, R.drawable.ic_sysbar_rotate_button_cw_start_90, new Supplier() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Integer.valueOf(NavigationBarView.this.mCurrentRotation);
            }
        });
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        this.mTmpLastConfiguration = new Configuration();
        configuration.updateFrom(context.getResources().getConfiguration());
        this.mScreenPinningNotify = new ScreenPinningNotify(((FrameLayout) this).mContext);
        sparseArray.put(R.id.back, new ButtonDispatcher(R.id.back));
        sparseArray.put(R.id.home, new ButtonDispatcher(R.id.home));
        sparseArray.put(R.id.home_handle, new ButtonDispatcher(R.id.home_handle));
        sparseArray.put(R.id.recent_apps, new ButtonDispatcher(R.id.recent_apps));
        sparseArray.put(R.id.ime_switcher, contextualButton);
        sparseArray.put(R.id.accessibility_button, contextualButton2);
        sparseArray.put(R.id.menu_container, contextualButtonGroup);
        this.mDeadZone = new DeadZone(this);
        if (BasicRune.NAVBAR_ENABLED) {
            this.navBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(((FrameLayout) this).mContext.getDisplayId());
        }
    }

    public static void dumpButton(PrintWriter printWriter, String str, ButtonDispatcher buttonDispatcher) {
        printWriter.print("      " + str + ": ");
        if (buttonDispatcher == null) {
            printWriter.print("null");
        } else {
            printWriter.print(visibilityToString(buttonDispatcher.getVisibility()) + " alpha=" + buttonDispatcher.getAlpha());
        }
        printWriter.println();
    }

    public static String visibilityToString(int i) {
        return i != 4 ? i != 8 ? "VISIBLE" : "GONE" : "INVISIBLE";
    }

    public final void abortCurrentGesture() {
        ButtonDispatcher homeButton = getHomeButton();
        int size = homeButton.mViews.size();
        for (int i = 0; i < size; i++) {
            if (homeButton.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) homeButton.mViews.get(i)).abortCurrentGesture();
            }
        }
    }

    public final ButtonDispatcher getAccessibilityButton() {
        return (ButtonDispatcher) this.mButtonDispatchers.get(R.id.accessibility_button);
    }

    public final ButtonDispatcher getBackButton() {
        return (ButtonDispatcher) this.mButtonDispatchers.get(R.id.back);
    }

    public KeyButtonDrawable getBackIconWithAlt(boolean z) {
        return this.mBackIcon;
    }

    public IconThemeBase getDefaultIconTheme() {
        return null;
    }

    public final KeyButtonDrawable getDrawable(int i) {
        return KeyButtonDrawable.create(this.mLightContext, this.mLightIconColor, this.mDarkIconColor, i, true);
    }

    public GestureHintGroup getHintGroup() {
        return null;
    }

    public ButtonDispatcher getHintView() {
        return null;
    }

    public final ButtonDispatcher getHomeButton() {
        return (ButtonDispatcher) this.mButtonDispatchers.get(R.id.home);
    }

    public final ButtonDispatcher getHomeHandle() {
        return (ButtonDispatcher) this.mButtonDispatchers.get(R.id.home_handle);
    }

    public ExtendableBar getPluginBar() {
        return null;
    }

    public final ButtonDispatcher getRecentsButton() {
        return (ButtonDispatcher) this.mButtonDispatchers.get(R.id.recent_apps);
    }

    public GestureHintDrawable getSecondaryHomeHandleDrawable(int i) {
        return null;
    }

    public final boolean isImeRenderingNavButtons() {
        return this.mImeDrawsImeNavBar && this.mImeCanRenderGesturalNavButtons && (this.mNavigationIconHints & 2) != 0;
    }

    public final boolean isOverviewEnabled() {
        return (this.mDisabledFlags & 16777216) == 0;
    }

    public boolean isRecentsButtonDisabled() {
        if (isOverviewEnabled()) {
            int displayId = getContext().getDisplayId();
            this.mDisplayTracker.getClass();
            if (displayId == 0) {
                return false;
            }
        }
        return true;
    }

    public final boolean isRecentsButtonVisible() {
        return getRecentsButton().getVisibility() == 0;
    }

    public boolean needTouchableInsetsFrame() {
        return false;
    }

    public final void notifyActiveTouchRegions() {
        NavigationBar$$ExternalSyntheticLambda2 navigationBar$$ExternalSyntheticLambda2 = this.mUpdateActiveTouchRegionsCallback;
        if (navigationBar$$ExternalSyntheticLambda2 != null) {
            NavigationBar navigationBar = navigationBar$$ExternalSyntheticLambda2.f$0;
            Region buttonLocations = navigationBar.getButtonLocations(true, true);
            OverviewProxyService overviewProxyService = navigationBar.mOverviewProxyService;
            overviewProxyService.mActiveNavBarRegion = buttonLocations;
            IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
            if (iOverviewProxy != null) {
                try {
                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onActiveNavBarRegionChanges(buttonLocations);
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onActiveNavBarRegionChanges()", e);
                }
            }
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int systemWindowInsetLeft = windowInsets.getSystemWindowInsetLeft();
        int systemWindowInsetRight = windowInsets.getSystemWindowInsetRight();
        boolean z = true;
        if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || ((FrameLayout) this).mContext.getDisplayId() != 1) {
            setPadding(systemWindowInsetLeft, windowInsets.getSystemWindowInsetTop(), systemWindowInsetRight, windowInsets.getSystemWindowInsetBottom());
        }
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        edgeBackGestureHandler.mLeftInset = systemWindowInsetLeft;
        edgeBackGestureHandler.mRightInset = systemWindowInsetRight;
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = edgeBackGestureHandler.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.setInsets(systemWindowInsetLeft, systemWindowInsetRight);
        }
        if (QuickStepContract.isGesturalMode(this.mNavBarMode) && windowInsets.getSystemWindowInsetBottom() != 0) {
            z = false;
        }
        setClipChildren(z);
        setClipToPadding(z);
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestApplyInsets();
        reorient();
        final RotationButtonController rotationButtonController = this.mRotationButtonController;
        if (rotationButtonController != null && !rotationButtonController.mListenersRegistered && !rotationButtonController.getContext().getPackageManager().hasSystemFeature("android.hardware.type.pc")) {
            rotationButtonController.mListenersRegistered = true;
            rotationButtonController.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda5
                public final /* synthetic */ boolean f$1 = false;

                @Override // java.lang.Runnable
                public final void run() {
                    final RotationButtonController rotationButtonController2 = RotationButtonController.this;
                    boolean z = this.f$1;
                    rotationButtonController2.getClass();
                    if (z) {
                        try {
                            WindowManagerGlobal.getWindowManagerService().watchRotation(rotationButtonController2.mRotationWatcher, 0);
                            rotationButtonController2.mRotationWatcherRegistered = true;
                        } catch (RemoteException e) {
                            Log.e("RotationButtonController", "RegisterListeners caught a RemoteException", e);
                        } catch (IllegalArgumentException e2) {
                            Log.w("RotationButtonController", "RegisterListeners for the display failed", e2);
                        }
                    }
                    final Intent registerReceiver = rotationButtonController2.mContext.registerReceiver(rotationButtonController2.mDockedReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"));
                    rotationButtonController2.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            RotationButtonController rotationButtonController3 = RotationButtonController.this;
                            Intent intent = registerReceiver;
                            rotationButtonController3.getClass();
                            if (intent == null) {
                                return;
                            }
                            rotationButtonController3.mDocked = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0;
                        }
                    });
                }
            });
            TaskStackChangeListeners.INSTANCE.registerTaskStackListener(rotationButtonController.mTaskStackListener);
            if (BasicRuneWrapper.NAVBAR_ENABLED) {
                SystemBarProxy systemBarProxy = rotationButtonController.mBarProxy;
                RotationButtonController$$ExternalSyntheticLambda3 rotationButtonController$$ExternalSyntheticLambda3 = rotationButtonController.mRotationLockCallback;
                SamsungNavigationBarProxy samsungNavigationBarProxy = (SamsungNavigationBarProxy) systemBarProxy;
                samsungNavigationBarProxy.getClass();
                try {
                    ((ArrayList) samsungNavigationBarProxy.rotationLockCallback).add(rotationButtonController$$ExternalSyntheticLambda3);
                } catch (Exception unused) {
                }
            }
        }
        updateNavButtonIcons();
        Flags.FEATURE_FLAGS.getClass();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTmpLastConfiguration.updateFrom(this.mConfiguration);
        int updateFrom = this.mConfiguration.updateFrom(configuration);
        FloatingRotationButton floatingRotationButton = this.mFloatingRotationButton;
        floatingRotationButton.getClass();
        if ((updateFrom & 4096) != 0 || (updateFrom & 1024) != 0) {
            floatingRotationButton.updateDimensionResources();
            if (floatingRotationButton.mIsShowing) {
                floatingRotationButton.mWindowManager.updateViewLayout(floatingRotationButton.mKeyButtonContainer, floatingRotationButton.adjustViewPositionAndCreateLayoutParams());
            }
        }
        if ((updateFrom & 4) != 0) {
            floatingRotationButton.mKeyButtonView.setContentDescription(floatingRotationButton.mContext.getString(floatingRotationButton.mContentDescriptionResource));
        }
        Configuration configuration2 = this.mConfiguration;
        if (configuration2 != null) {
            boolean z = (configuration2.uiMode & 15) == 3;
            if (z != this.mInCarMode) {
                this.mInCarMode = z;
            }
        }
        updateIcons(this.mTmpLastConfiguration);
        updateRecentsIcon();
        updateCurrentRotation();
        Configuration configuration3 = this.mTmpLastConfiguration;
        if (configuration3.densityDpi == this.mConfiguration.densityDpi && configuration3.getLayoutDirection() == this.mConfiguration.getLayoutDirection()) {
            return;
        }
        updateNavButtonIcons();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            ((ButtonDispatcher) this.mButtonDispatchers.valueAt(i)).getClass();
        }
        if (this.mRotationButtonController != null) {
            this.mFloatingRotationButton.hide();
            RotationButtonController rotationButtonController = this.mRotationButtonController;
            if (rotationButtonController.mListenersRegistered) {
                rotationButtonController.mListenersRegistered = false;
                rotationButtonController.mBgExecutor.execute(new RotationButtonController$$ExternalSyntheticLambda0(rotationButtonController, 3));
                TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(rotationButtonController.mTaskStackListener);
                if (BasicRuneWrapper.NAVBAR_ENABLED) {
                    SystemBarProxy systemBarProxy = rotationButtonController.mBarProxy;
                    RotationButtonController$$ExternalSyntheticLambda3 rotationButtonController$$ExternalSyntheticLambda3 = rotationButtonController.mRotationLockCallback;
                    SamsungNavigationBarProxy samsungNavigationBarProxy = (SamsungNavigationBarProxy) systemBarProxy;
                    samsungNavigationBarProxy.getClass();
                    try {
                        ((ArrayList) samsungNavigationBarProxy.rotationLockCallback).remove(rotationButtonController$$ExternalSyntheticLambda3);
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        DeadZone deadZone = this.mDeadZone;
        if (deadZone.mUseDeadZone && deadZone.mShouldFlash && deadZone.mFlashFrac > 0.0f) {
            int size = (int) deadZone.getSize(SystemClock.uptimeMillis());
            if (!deadZone.mVertical) {
                canvas.clipRect(0, 0, canvas.getWidth(), size);
            } else if (deadZone.mDisplayRotation == 3) {
                canvas.clipRect(canvas.getWidth() - size, 0, canvas.getWidth(), canvas.getHeight());
            } else {
                canvas.clipRect(0, 0, size, canvas.getHeight());
            }
            canvas.drawARGB((int) (deadZone.mFlashFrac * 255.0f), 221, IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp, 170);
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) findViewById(R.id.navigation_inflater);
        this.mNavigationInflaterView = navigationBarInflaterView;
        navigationBarInflaterView.setButtonDispatchers(this.mButtonDispatchers);
        updateOrientationViews();
        updateIcons(Configuration.EMPTY);
    }

    public void onImeVisibilityChanged(boolean z) {
        if (z) {
            return;
        }
        NavTransitionListener navTransitionListener = this.mTransitionListener;
        ButtonDispatcher backButton = NavigationBarView.this.getBackButton();
        if (!navTransitionListener.mBackTransitioning && backButton.getVisibility() == 0 && navTransitionListener.mHomeAppearing && NavigationBarView.this.getHomeButton().getAlpha() == 0.0f) {
            NavigationBarView.this.getBackButton().setAlpha(0.0f, true);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(backButton, "alpha", 0.0f, 1.0f);
            ofFloat.setStartDelay(navTransitionListener.mStartDelay);
            ofFloat.setDuration(navTransitionListener.mDuration);
            ofFloat.setInterpolator(navTransitionListener.mInterpolator);
            ofFloat.start();
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mTouchHandler.onInterceptTouchEvent(motionEvent) || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        notifyActiveTouchRegions();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0020, code lost:
    
        if (com.android.systemui.shared.system.QuickStepContract.isGesturalMode(r7.mNavBarMode) == false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0017, code lost:
    
        if (r4 == 2) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0022, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004c  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r8, int r9) {
        /*
            r7 = this;
            int r0 = android.view.View.MeasureSpec.getSize(r8)
            int r1 = android.view.View.MeasureSpec.getSize(r9)
            r2 = 1
            r3 = 0
            if (r0 <= 0) goto L24
            if (r1 <= r0) goto L24
            boolean r4 = com.android.systemui.BasicRune.NAVBAR_GESTURE
            if (r4 == 0) goto L1a
            int r4 = r7.mNavBarMode
            com.android.systemui.navigationbar.util.NavigationModeUtil r5 = com.android.systemui.navigationbar.util.NavigationModeUtil.INSTANCE
            r5 = 2
            if (r4 != r5) goto L22
            goto L24
        L1a:
            int r4 = r7.mNavBarMode
            boolean r4 = com.android.systemui.shared.system.QuickStepContract.isGesturalMode(r4)
            if (r4 != 0) goto L24
        L22:
            r4 = r2
            goto L25
        L24:
            r4 = r3
        L25:
            boolean r5 = r7.mIsVertical
            if (r4 == r5) goto L48
            r7.mIsVertical = r4
            r7.reorient()
            com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda2 r5 = r7.mOnVerticalChangedListener
            if (r5 == 0) goto L48
            com.android.systemui.navigationbar.NavigationBar r5 = r5.f$0
            dagger.Lazy r6 = r5.mCentralSurfacesOptionalLazy
            java.lang.Object r6 = r6.get()
            java.util.Optional r6 = (java.util.Optional) r6
            boolean r6 = r6.isPresent()
            if (r6 == 0) goto L48
            r2 = r2 ^ r4
            com.android.systemui.shade.ShadeViewController r4 = r5.mShadeViewController
            r4.setQsScrimEnabled(r2)
        L48:
            boolean r2 = com.android.systemui.BasicRune.NAVBAR_LIGHTBAR
            if (r2 != 0) goto L8e
            int r2 = r7.mNavBarMode
            boolean r2 = com.android.systemui.shared.system.QuickStepContract.isGesturalMode(r2)
            if (r2 == 0) goto L87
            boolean r2 = r7.mIsVertical
            if (r2 == 0) goto L64
            android.content.res.Resources r2 = r7.getResources()
            r4 = 17105611(0x10502cb, float:2.4430246E-38)
            int r2 = r2.getDimensionPixelSize(r4)
            goto L6f
        L64:
            android.content.res.Resources r2 = r7.getResources()
            r4 = 17105609(0x10502c9, float:2.443024E-38)
            int r2 = r2.getDimensionPixelSize(r4)
        L6f:
            android.content.res.Resources r4 = r7.getResources()
            r5 = 17105604(0x10502c4, float:2.4430226E-38)
            int r4 = r4.getDimensionPixelSize(r5)
            com.android.systemui.navigationbar.NavigationBarTransitions r5 = r7.mBarTransitions
            android.graphics.Rect r6 = new android.graphics.Rect
            int r4 = r4 - r2
            r6.<init>(r3, r4, r0, r1)
            com.android.systemui.statusbar.phone.BarTransitions$BarBackgroundDrawable r0 = r5.mBarBackground
            r0.mFrame = r6
            goto L8e
        L87:
            com.android.systemui.navigationbar.NavigationBarTransitions r0 = r7.mBarTransitions
            com.android.systemui.statusbar.phone.BarTransitions$BarBackgroundDrawable r0 = r0.mBarBackground
            r1 = 0
            r0.mFrame = r1
        L8e:
            super.onMeasure(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationBarView.onMeasure(int, int):void");
    }

    public void onScreenStateChanged(boolean z) {
        this.mScreenOn = z;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mTouchHandler.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    public void orientBackButton(KeyButtonDrawable keyButtonDrawable) {
        float f;
        boolean z = (this.mNavigationIconHints & 1) != 0;
        boolean z2 = this.mConfiguration.getLayoutDirection() == 1;
        float f2 = 0.0f;
        if (z) {
            f = z2 ? 90 : -90;
        } else {
            f = 0.0f;
        }
        if (keyButtonDrawable.mState.mRotateDegrees == f) {
            return;
        }
        if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            keyButtonDrawable.setRotation(f);
            return;
        }
        if (!this.mShowSwipeUpUi && !this.mIsVertical && z) {
            f2 = -getResources().getDimension(R.dimen.navbar_back_button_ime_offset);
        }
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(keyButtonDrawable, PropertyValuesHolder.ofFloat(KeyButtonDrawable.KEY_DRAWABLE_ROTATE, f), PropertyValuesHolder.ofFloat(KeyButtonDrawable.KEY_DRAWABLE_TRANSLATE_Y, f2));
        ofPropertyValuesHolder.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofPropertyValuesHolder.setDuration(200L);
        ofPropertyValuesHolder.start();
    }

    public void orientHomeButton(KeyButtonDrawable keyButtonDrawable) {
        keyButtonDrawable.setRotation(this.mIsVertical ? 90.0f : 0.0f);
    }

    public void reorient() {
        updateCurrentView();
        ((NavigationBarFrame) getRootView()).mDeadZone = this.mDeadZone;
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        navigationBarTransitions.applyModeBackground(navigationBarTransitions.mMode, false);
        navigationBarTransitions.applyLightsOut(false, true);
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
        }
        updateNavButtonIcons();
        ButtonDispatcher homeButton = getHomeButton();
        homeButton.mVertical = this.mIsVertical;
        int size = homeButton.mViews.size();
        for (int i = 0; i < size; i++) {
            KeyEvent.Callback callback = (View) homeButton.mViews.get(i);
            if (callback instanceof ButtonInterface) {
                ((ButtonInterface) callback).getClass();
            }
        }
    }

    public final void setAccessibilityButtonState(boolean z, boolean z2) {
        getAccessibilityButton().setLongClickable(z2);
        if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && ((FrameLayout) this).mContext.getDisplayId() == 1) {
            this.mContextualButtonGroup.setButtonVisibility(R.id.accessibility_button, false);
        } else {
            this.mContextualButtonGroup.setButtonVisibility(R.id.accessibility_button, z);
        }
    }

    public final void setDisabledFlags(int i, SysUiState sysUiState) {
        if (this.mDisabledFlags == i) {
            return;
        }
        boolean isOverviewEnabled = isOverviewEnabled();
        this.mDisabledFlags = i;
        if (!BasicRune.NAVBAR_ENABLED && !isOverviewEnabled && isOverviewEnabled()) {
            updateIcons(Configuration.EMPTY);
        }
        updateNavButtonIcons();
        updateSlippery();
        updateDisabledSystemUiStateFlags(sysUiState);
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        updateIcons(Configuration.EMPTY);
        super.setLayoutDirection(i);
    }

    public final void setSlippery(boolean z) {
        WindowManager.LayoutParams layoutParams;
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null || (layoutParams = (WindowManager.LayoutParams) viewGroup.getLayoutParams()) == null) {
            return;
        }
        int i = layoutParams.flags;
        if (z == ((i & 536870912) != 0)) {
            return;
        }
        if (z) {
            layoutParams.flags = i | 536870912;
        } else {
            layoutParams.flags = (-536870913) & i;
        }
        ((WindowManager) getContext().getSystemService(WindowManager.class)).updateViewLayout(viewGroup, layoutParams);
    }

    public void showPinningEscapeToast() {
        this.mScreenPinningNotify.showEscapeToast(this.mNavBarMode == 2, isRecentsButtonVisible());
    }

    public final void updateCurrentRotation() {
        int displayRotation = this.mConfiguration.windowConfiguration.getDisplayRotation();
        if (this.mCurrentRotation == displayRotation) {
            return;
        }
        this.mCurrentRotation = displayRotation;
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        boolean z = displayRotation == 1;
        if (z != navigationBarInflaterView.mAlternativeOrder) {
            navigationBarInflaterView.mAlternativeOrder = z;
            navigationBarInflaterView.updateAlternativeOrder();
        }
        this.mDeadZone.onConfigurationChanged(this.mCurrentRotation);
        if (BasicRune.NAVBAR_GESTURE) {
            updateGestureHintGroupRotation();
        }
    }

    public void updateCurrentView() {
        this.mHorizontal.setVisibility(8);
        this.mVertical.setVisibility(8);
        View view = this.mIsVertical ? this.mVertical : this.mHorizontal;
        this.mCurrentView = view;
        view.setVisibility(0);
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        boolean z = this.mIsVertical;
        if (z != navigationBarInflaterView.mIsVertical) {
            navigationBarInflaterView.mIsVertical = z;
        }
        navigationBarInflaterView.updateButtonDispatchersCurrentView();
        updateLayoutTransitionsEnabled();
        updateCurrentRotation();
    }

    public void updateDisabledSystemUiStateFlags(SysUiState sysUiState) {
        int displayId = ((FrameLayout) this).mContext.getDisplayId();
        sysUiState.setFlag(128L, (this.mDisabledFlags & 16777216) != 0);
        sysUiState.setFlag(256L, (this.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0);
        sysUiState.setFlag(1024L, (this.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0);
        sysUiState.commitUpdate(displayId);
    }

    public void updateIcons(Configuration configuration) {
        int i = configuration.orientation;
        Configuration configuration2 = this.mConfiguration;
        boolean z = (i == configuration2.orientation || BasicRune.NAVBAR_ENABLED) ? false : true;
        boolean z2 = configuration.densityDpi != configuration2.densityDpi;
        boolean z3 = configuration.getLayoutDirection() != this.mConfiguration.getLayoutDirection();
        if (z || z2) {
            this.mDockedIcon = getDrawable(R.drawable.ic_sysbar_docked);
            KeyButtonDrawable drawable = this.mShowSwipeUpUi ? getDrawable(R.drawable.ic_sysbar_home_quick_step) : getDrawable(R.drawable.ic_sysbar_home);
            orientHomeButton(drawable);
            this.mHomeDefaultIcon = drawable;
        }
        if (z2 || z3) {
            this.mRecentIcon = getDrawable(R.drawable.ic_sysbar_recent);
            ContextualButtonGroup contextualButtonGroup = this.mContextualButtonGroup;
            int i2 = this.mLightIconColor;
            int i3 = this.mDarkIconColor;
            Iterator it = ((ArrayList) contextualButtonGroup.mButtonData).iterator();
            while (it.hasNext()) {
                ContextualButton contextualButton = ((ContextualButtonGroup.ButtonData) it.next()).button;
                contextualButton.getClass();
                boolean z4 = BasicRune.NAVBAR_ENABLED;
                IconType iconType = contextualButton.mIconType;
                int i4 = contextualButton.mIconResId;
                if (!z4 || iconType != null || i4 != 0) {
                    KeyButtonDrawable keyButtonDrawable = contextualButton.mImageDrawable;
                    KeyButtonDrawable create = (!z4 || iconType == null) ? KeyButtonDrawable.create(contextualButton.mLightContext, i2, i3, i4, false) : contextualButton.mGroup.mKeyButtonMapper.getButtonDrawable(iconType);
                    if (keyButtonDrawable != null) {
                        create.setDarkIntensity(keyButtonDrawable.mState.mDarkIntensity);
                    }
                    contextualButton.setImageDrawable(create);
                }
            }
        }
        if (z || z2 || z3) {
            KeyButtonDrawable drawable2 = getDrawable(R.drawable.ic_sysbar_back);
            orientBackButton(drawable2);
            this.mBackIcon = drawable2;
        }
    }

    public final void updateLayoutTransitionsEnabled() {
        boolean z = !this.mWakeAndUnlocking && this.mLayoutTransitionsEnabled;
        LayoutTransition layoutTransition = ((ViewGroup) this.mCurrentView.findViewById(R.id.nav_buttons)).getLayoutTransition();
        if (layoutTransition != null) {
            if (z) {
                layoutTransition.enableTransitionType(2);
                layoutTransition.enableTransitionType(3);
                layoutTransition.enableTransitionType(0);
                layoutTransition.enableTransitionType(1);
                return;
            }
            layoutTransition.disableTransitionType(2);
            layoutTransition.disableTransitionType(3);
            layoutTransition.disableTransitionType(0);
            layoutTransition.disableTransitionType(1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x0088, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x007e, code lost:
    
        if (com.android.systemui.shared.system.QuickStepContract.isGesturalMode(r14.mNavBarMode) == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0075, code lost:
    
        if (r6 == 2) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0083, code lost:
    
        if ((r14.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) == 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0086, code lost:
    
        r6 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateNavButtonIcons() {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationBarView.updateNavButtonIcons():void");
    }

    public final void updateOrientationViews() {
        this.mHorizontal = findViewById(R.id.horizontal);
        this.mVertical = findViewById(R.id.vertical);
        updateCurrentView();
    }

    public final void updateRecentsIcon() {
        this.mDockedIcon.setRotation(0.0f);
        getRecentsButton().setImageDrawable(this.mRecentIcon);
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        navigationBarTransitions.applyDarkIntensity(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
    }

    public final void updateRotationButton() {
        final RotationButtonController rotationButtonController = this.mRotationButtonController;
        FloatingRotationButton floatingRotationButton = this.mFloatingRotationButton;
        AnonymousClass2 anonymousClass2 = this.mRotationButtonListener;
        rotationButtonController.mRotationButton = floatingRotationButton;
        floatingRotationButton.mRotationButtonController = rotationButtonController;
        floatingRotationButton.updateIcon(rotationButtonController.mLightIconColor, rotationButtonController.mDarkIconColor);
        FloatingRotationButton floatingRotationButton2 = rotationButtonController.mRotationButton;
        floatingRotationButton2.mKeyButtonView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RotationButtonController rotationButtonController2 = RotationButtonController.this;
                if (BasicRuneWrapper.NAVBAR_ENABLED) {
                    rotationButtonController2.mLastUnknownRotationProposedTick = 0L;
                    ((SamsungNavigationBarProxy) rotationButtonController2.mBarProxy).getClass();
                    MetricsLogger metricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
                    if (metricsLogger != null) {
                        metricsLogger.action(1287);
                    }
                }
                rotationButtonController2.mUiEventLogger.log(RotationButtonController.RotationButtonEvent.ROTATION_SUGGESTION_ACCEPTED);
                ContentResolver contentResolver = rotationButtonController2.mContext.getContentResolver();
                int i = Settings.Secure.getInt(contentResolver, "num_rotation_suggestions_accepted", 0);
                if (i < 1) {
                    Settings.Secure.putInt(contentResolver, "num_rotation_suggestions_accepted", i + 1);
                }
                int i2 = rotationButtonController2.mLastRotationSuggestion;
                Boolean isRotationLocked = rotationButtonController2.isRotationLocked();
                if (isRotationLocked != null) {
                    RotationPolicy.setRotationLockAtAngle(rotationButtonController2.mContext, isRotationLocked.booleanValue(), i2, "RotationButtonController#onRotateSuggestionClick");
                }
                Log.i("RotationButtonController", "onRotateSuggestionClick() mLastRotationSuggestion=" + rotationButtonController2.mLastRotationSuggestion);
                view.performHapticFeedback(1);
            }
        });
        FloatingRotationButton floatingRotationButton3 = rotationButtonController.mRotationButton;
        floatingRotationButton3.mKeyButtonView.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda8
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                RotationButtonController rotationButtonController2 = RotationButtonController.this;
                rotationButtonController2.getClass();
                int actionMasked = motionEvent.getActionMasked();
                rotationButtonController2.mHoveringRotationSuggestion = actionMasked == 9 || actionMasked == 7;
                rotationButtonController2.rescheduleRotationTimeout(true);
                return false;
            }
        });
        rotationButtonController.mRotationButton.mUpdatesCallback = anonymousClass2;
    }

    public final void updateSlippery() {
        PanelExpansionInteractor panelExpansionInteractor;
        setSlippery((this.mShowSwipeUpUi && isOverviewEnabled() && ((panelExpansionInteractor = this.mPanelExpansionInteractor) == null || !panelExpansionInteractor.isFullyExpanded() || this.mPanelExpansionInteractor.isCollapsing())) ? false : true);
    }

    public final void updateStates() {
        NavigationBarInflaterView navigationBarInflaterView;
        if (!BasicRune.NAVBAR_ENABLED && (navigationBarInflaterView = this.mNavigationInflaterView) != null) {
            String defaultLayout = navigationBarInflaterView.getDefaultLayout();
            if (!Objects.equals(navigationBarInflaterView.mCurrentLayout, defaultLayout)) {
                navigationBarInflaterView.clearViews();
                navigationBarInflaterView.inflateLayout(defaultLayout);
            }
        }
        updateSlippery();
        updateIcons(Configuration.EMPTY);
        updateNavButtonIcons();
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    WindowManagerGlobal.getWindowManagerService().setNavBarVirtualKeyHapticFeedbackEnabled(!NavigationBarView.this.mShowSwipeUpUi);
                } catch (RemoteException e) {
                    Log.w("NavBarView", "Failed to enable or disable navigation bar button haptics: ", e);
                }
            }
        });
        ButtonDispatcher homeButton = getHomeButton();
        AnonymousClass1 anonymousClass1 = this.mShowSwipeUpUi ? this.mQuickStepAccessibilityDelegate : null;
        homeButton.mAccessibilityDelegate = anonymousClass1;
        int size = homeButton.mViews.size();
        for (int i = 0; i < size; i++) {
            ((View) homeButton.mViews.get(i)).setAccessibilityDelegate(anonymousClass1);
        }
    }

    public void reInflateNavBarLayout() {
    }

    public void showA11ySwipeUpTipPopup() {
    }

    public void updateGestureHintGroupRotation() {
    }

    public void updateIconsAndHints() {
    }

    public void updateLayoutProviderView() {
    }

    public void updateNavigationBarColor() {
    }

    public void updateRemoteViewContainer() {
    }

    public void setDefaultIconTheme(IconThemeBase iconThemeBase) {
    }

    public void setIconThemeAlpha(float f) {
    }

    public void updateOpaqueColor(int i) {
    }

    public void marqueeNavigationBarIcon(int i, int i2) {
    }

    public void updateActiveIndicatorSpringParams(float f, float f2) {
    }

    public void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
    }

    public void updateHintVisibility(boolean z, boolean z2, boolean z3) {
    }

    public void updateBackPanelColor(int i, int i2, int i3, int i4) {
    }
}
