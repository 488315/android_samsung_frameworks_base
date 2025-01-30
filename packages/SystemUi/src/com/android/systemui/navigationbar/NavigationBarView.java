package com.android.systemui.navigationbar;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
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
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.RotationContextButton;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.store.SystemBarProxy;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda1;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public boolean mDockedStackExists;
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
    public NavigationBar$$ExternalSyntheticLambda11 mOnVerticalChangedListener;
    public boolean mOverviewProxyEnabled;
    public ShadeViewController mPanelView;
    public final NavigationBarView$$ExternalSyntheticLambda0 mPipListener;
    public final C18581 mQuickStepAccessibilityDelegate;
    public KeyButtonDrawable mRecentIcon;
    public Optional mRecentsOptional;
    public RotationButtonController mRotationButtonController;
    public final C18592 mRotationButtonListener;
    public RotationContextButton mRotationContextButton;
    public boolean mScreenOn;
    public boolean mScreenPinningActive;
    public final ScreenPinningNotify mScreenPinningNotify;
    public boolean mShowSwipeUpUi;
    public final Configuration mTmpLastConfiguration;
    public Gefingerpoken mTouchHandler;
    public final NavTransitionListener mTransitionListener;
    public NavigationBar$$ExternalSyntheticLambda11 mUpdateActiveTouchRegionsCallback;
    public View mVertical;
    public boolean mWakeAndUnlocking;
    public final NavBarStateManager navBarStateManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.navigationbar.NavigationBarView$2 */
    public final class C18592 {
        public C18592() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                NavigationBarView.this.mRecentsOptional.ifPresent(new NavigationBarView$1$$ExternalSyntheticLambda0());
                return true;
            }
        };
        this.mRotationButtonListener = new C18592();
        this.mPipListener = new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final NavigationBarView navigationBarView = NavigationBarView.this;
                final Rect rect = (Rect) obj;
                int i = NavigationBarView.$r8$clinit;
                navigationBarView.getClass();
                final int i2 = 1;
                navigationBarView.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                NavigationBarView navigationBarView2 = navigationBarView;
                                Boolean bool = (Boolean) rect;
                                int i3 = NavigationBarView.$r8$clinit;
                                navigationBarView2.getClass();
                                navigationBarView2.mDockedStackExists = bool.booleanValue();
                                navigationBarView2.updateRecentsIcon();
                                break;
                            default:
                                NavigationBarView navigationBarView3 = navigationBarView;
                                navigationBarView3.mEdgeBackGestureHandler.mPipExcludedBounds.set((Rect) rect);
                                break;
                        }
                    }
                });
            }
        };
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.darkIconTheme, context));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.lightIconTheme, context));
        this.mLightContext = contextThemeWrapper2;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(R.attr.singleToneColor, contextThemeWrapper2, 0);
        this.mLightIconColor = colorAttrDefaultColor;
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(R.attr.singleToneColor, contextThemeWrapper, 0);
        this.mDarkIconColor = colorAttrDefaultColor2;
        this.mIsVertical = false;
        ContextualButtonGroup contextualButtonGroup = new ContextualButtonGroup(R.id.menu_container);
        this.mContextualButtonGroup = contextualButtonGroup;
        ContextualButton contextualButton = new ContextualButton(R.id.ime_switcher, contextThemeWrapper2, R.drawable.ic_ime_switcher_default);
        ContextualButton contextualButton2 = new ContextualButton(R.id.accessibility_button, contextThemeWrapper2, R.drawable.ic_sysbar_accessibility_button);
        contextualButtonGroup.addButton(contextualButton);
        contextualButtonGroup.addButton(contextualButton2);
        this.mRotationContextButton = new RotationContextButton(R.id.rotate_suggestion, contextThemeWrapper2, R.drawable.ic_sysbar_rotate_button_ccw_start_0);
        FloatingRotationButton floatingRotationButton = new FloatingRotationButton(((FrameLayout) this).mContext, R.string.accessibility_rotate_button, R.layout.rotate_suggestion, R.id.rotate_suggestion, R.dimen.floating_rotation_button_min_margin, R.dimen.rounded_corner_content_padding, R.dimen.floating_rotation_button_taskbar_left_margin, R.dimen.floating_rotation_button_taskbar_bottom_margin, R.dimen.floating_rotation_button_diameter, R.dimen.key_button_ripple_max_width, R.bool.floating_rotation_button_position_left);
        this.mFloatingRotationButton = floatingRotationButton;
        boolean z = BasicRune.NAVBAR_ENABLED;
        if (z) {
            floatingRotationButton.mSamsungBottomMarginResource = R.dimen.samsung_floating_rotation_button_bottom_margin;
            floatingRotationButton.mSamsungDiameterResource = R.dimen.samsung_floating_rotation_button_diameter;
            floatingRotationButton.mSamsungHiddenVisualCueRotateBtnResource = R.drawable.samsung_hidden_visual_cue_rotate_btn;
            floatingRotationButton.updateDimensionResources();
        }
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
        if (z) {
            this.navBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.get(NavBarStore.class))).getNavStateManager(((FrameLayout) this).mContext.getDisplayId());
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
        NavigationBar$$ExternalSyntheticLambda11 navigationBar$$ExternalSyntheticLambda11 = this.mUpdateActiveTouchRegionsCallback;
        if (navigationBar$$ExternalSyntheticLambda11 != null) {
            NavigationBar navigationBar = navigationBar$$ExternalSyntheticLambda11.f$0;
            Region buttonLocations = navigationBar.getButtonLocations(true, true, true);
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

    public final void notifyVerticalChangedListener(boolean z) {
        NavigationBar$$ExternalSyntheticLambda11 navigationBar$$ExternalSyntheticLambda11 = this.mOnVerticalChangedListener;
        if (navigationBar$$ExternalSyntheticLambda11 != null) {
            Optional optional = (Optional) navigationBar$$ExternalSyntheticLambda11.f$0.mCentralSurfacesOptionalLazy.get();
            if (!optional.isPresent() || ((CentralSurfacesImpl) ((CentralSurfaces) optional.get())).getShadeViewController() == null) {
                return;
            }
            boolean z2 = !z;
            QuickSettingsController quickSettingsController = ((NotificationPanelViewController) ((CentralSurfacesImpl) ((CentralSurfaces) optional.get())).getShadeViewController()).mQsController;
            boolean z3 = quickSettingsController.mScrimEnabled != z2;
            quickSettingsController.mScrimEnabled = z2;
            if (z3) {
                quickSettingsController.updateQsState();
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
        RotationButtonController rotationButtonController = this.mRotationButtonController;
        if (rotationButtonController != null && !rotationButtonController.mListenersRegistered && !rotationButtonController.getContext().getPackageManager().hasSystemFeature("android.hardware.type.pc")) {
            rotationButtonController.mListenersRegistered = true;
            Intent registerReceiver = rotationButtonController.mContext.registerReceiver(rotationButtonController.mDockedReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"));
            if (registerReceiver != null) {
                rotationButtonController.mDocked = registerReceiver.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0;
            }
            TaskStackChangeListeners.INSTANCE.registerTaskStackListener(rotationButtonController.mTaskStackListener);
            if (BasicRuneWrapper.NAVBAR_ENABLED) {
                SystemBarProxy systemBarProxy = rotationButtonController.mBarProxy;
                RotationButtonController$$ExternalSyntheticLambda1 rotationButtonController$$ExternalSyntheticLambda1 = rotationButtonController.mRotationLockCallback;
                SamsungNavigationBarProxy samsungNavigationBarProxy = (SamsungNavigationBarProxy) systemBarProxy;
                samsungNavigationBarProxy.getClass();
                try {
                    ((ArrayList) samsungNavigationBarProxy.rotationLockCallback).add(rotationButtonController$$ExternalSyntheticLambda1);
                } catch (Exception unused) {
                }
            }
        }
        updateNavButtonIcons();
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
        this.mEdgeBackGestureHandler.onConfigurationChanged(this.mConfiguration);
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
                try {
                    rotationButtonController.mContext.unregisterReceiver(rotationButtonController.mDockedReceiver);
                } catch (IllegalArgumentException e) {
                    Log.e("RotationButtonController", "Docked receiver already unregistered", e);
                }
                if (rotationButtonController.mRotationWatcherRegistered) {
                    try {
                        WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(rotationButtonController.mRotationWatcher);
                    } catch (RemoteException e2) {
                        Log.e("RotationButtonController", "UnregisterListeners caught a RemoteException", e2);
                        return;
                    }
                }
                TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(rotationButtonController.mTaskStackListener);
                if (BasicRuneWrapper.NAVBAR_ENABLED) {
                    SystemBarProxy systemBarProxy = rotationButtonController.mBarProxy;
                    RotationButtonController$$ExternalSyntheticLambda1 rotationButtonController$$ExternalSyntheticLambda1 = rotationButtonController.mRotationLockCallback;
                    SamsungNavigationBarProxy samsungNavigationBarProxy = (SamsungNavigationBarProxy) systemBarProxy;
                    samsungNavigationBarProxy.getClass();
                    try {
                        ((ArrayList) samsungNavigationBarProxy.rotationLockCallback).remove(rotationButtonController$$ExternalSyntheticLambda1);
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        DeadZone deadZone = this.mDeadZone;
        if (deadZone.mShouldFlash && deadZone.mFlashFrac > 0.0f) {
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
        if (!z) {
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
        this.mRotationButtonController.mRotationButton.setCanShowRotationButton(!z);
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

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0025, code lost:
    
        if (com.android.systemui.shared.system.QuickStepContract.isGesturalMode(r7.mNavBarMode) == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x001c, code lost:
    
        if ((r3 == 2) != false) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMeasure(int i, int i2) {
        boolean z;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (size > 0 && size2 > size) {
            z = true;
            if (BasicRune.NAVBAR_GESTURE) {
                int i3 = this.mNavBarMode;
                NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
            }
            if (z != this.mIsVertical) {
                this.mIsVertical = z;
                reorient();
                notifyVerticalChangedListener(z);
            }
            if (!BasicRune.NAVBAR_LIGHTBAR) {
                if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
                    this.mBarTransitions.mBarBackground.mFrame = new Rect(0, getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end) - (this.mIsVertical ? getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_width_low_ram) : getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram)), size, size2);
                } else {
                    this.mBarTransitions.mBarBackground.mFrame = null;
                }
            }
            super.onMeasure(i, i2);
        }
        z = false;
        if (z != this.mIsVertical) {
        }
        if (!BasicRune.NAVBAR_LIGHTBAR) {
        }
        super.onMeasure(i, i2);
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
        ArrayList arrayList = homeButton.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            KeyEvent.Callback callback = (View) arrayList.get(i);
            if (callback instanceof ButtonInterface) {
                ((ButtonInterface) callback).setVertical();
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
        if (z == ((i & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT) != 0)) {
            return;
        }
        if (z) {
            layoutParams.flags = 536870912 | i;
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
                ((ContextualButtonGroup.ButtonData) it.next()).button.updateIcon(i2, i3);
            }
        }
        if (z || z2 || z3) {
            KeyButtonDrawable drawable2 = getDrawable(this.mShowSwipeUpUi ? R.drawable.ic_sysbar_back_quick_step : R.drawable.ic_sysbar_back);
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0060, code lost:
    
        if ((r6 == 2) != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006e, code lost:
    
        if ((r10.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) == 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0071, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0073, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0069, code lost:
    
        if (com.android.systemui.shared.system.QuickStepContract.isGesturalMode(r10.mNavBarMode) == false) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateNavButtonIcons() {
        boolean z;
        LayoutTransition layoutTransition;
        boolean z2 = (this.mNavigationIconHints & 1) != 0;
        KeyButtonDrawable backIconWithAlt = BasicRune.NAVBAR_ENABLED ? getBackIconWithAlt(z2) : this.mBackIcon;
        orientBackButton(backIconWithAlt);
        KeyButtonDrawable keyButtonDrawable = this.mHomeDefaultIcon;
        orientHomeButton(keyButtonDrawable);
        getHomeButton().setImageDrawable(keyButtonDrawable);
        getBackButton().setImageDrawable(backIconWithAlt);
        updateRecentsIcon();
        this.mContextualButtonGroup.setButtonVisibility(R.id.ime_switcher, !((this.mNavigationIconHints & 4) == 0 || isImeRenderingNavButtons()));
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        navigationBarTransitions.applyDarkIntensity(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
        boolean z3 = BasicRune.NAVBAR_GESTURE;
        if (z3) {
            int i = this.mNavBarMode;
            NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
        }
        boolean isRecentsButtonDisabled = isRecentsButtonDisabled();
        boolean z4 = isRecentsButtonDisabled && (2097152 & this.mDisabledFlags) != 0;
        if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && ((FrameLayout) this).mContext.getDisplayId() == 1 && this.navBarStateManager.canShowButtonInLargeCoverIme()) {
            z |= z2;
            z4 |= z2;
        }
        boolean isHandlingGestures = this.mEdgeBackGestureHandler.isHandlingGestures();
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            isHandlingGestures |= ((TaskbarDelegate) Dependency.get(TaskbarDelegate.class)).mEdgeBackGestureHandler.isHandlingGestures();
        }
        boolean z5 = (!z2 && (isHandlingGestures || (this.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0 || (z3 && NavigationModeUtil.isBottomGesture(this.mNavBarMode)))) || isImeRenderingNavButtons();
        if (!z3) {
            if (this.mOverviewProxyEnabled) {
                int i2 = this.mNavBarMode;
                boolean z6 = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
                isRecentsButtonDisabled |= true ^ (i2 == 0);
                if (this.mScreenPinningActive && !QuickStepContract.isGesturalMode(i2)) {
                    z5 = false;
                    z = false;
                }
            } else if (this.mScreenPinningActive) {
                z5 = false;
                isRecentsButtonDisabled = false;
            }
        }
        ViewGroup viewGroup = (ViewGroup) this.mCurrentView.findViewById(R.id.nav_buttons);
        if (viewGroup != null && (layoutTransition = viewGroup.getLayoutTransition()) != null && !layoutTransition.getTransitionListeners().contains(this.mTransitionListener)) {
            layoutTransition.addTransitionListener(this.mTransitionListener);
        }
        getBackButton().setVisibility(z5 ? 4 : 0);
        getHomeButton().setVisibility(z ? 4 : 0);
        getRecentsButton().setVisibility(isRecentsButtonDisabled ? 4 : 0);
        getHomeHandle().setVisibility(z4 ? 4 : 0);
        notifyActiveTouchRegions();
    }

    public final void updateOrientationViews() {
        this.mHorizontal = findViewById(R.id.horizontal);
        this.mVertical = findViewById(R.id.vertical);
        updateCurrentView();
    }

    public final void updateRecentsIcon() {
        this.mDockedIcon.setRotation((this.mDockedStackExists && this.mIsVertical) ? 90.0f : 0.0f);
        getRecentsButton().setImageDrawable(this.mDockedStackExists ? this.mDockedIcon : this.mRecentIcon);
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        navigationBarTransitions.applyDarkIntensity(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
    }

    public final void updateRotationButton() {
        if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            ContextualButtonGroup contextualButtonGroup = this.mContextualButtonGroup;
            int contextButtonIndex = contextualButtonGroup.getContextButtonIndex(R.id.rotate_suggestion);
            if (contextButtonIndex != -1) {
                ((ArrayList) contextualButtonGroup.mButtonData).remove(contextButtonIndex);
            }
            this.mButtonDispatchers.remove(R.id.rotate_suggestion);
            this.mRotationButtonController.setRotationButton(this.mFloatingRotationButton, this.mRotationButtonListener);
        } else {
            ContextualButtonGroup contextualButtonGroup2 = this.mContextualButtonGroup;
            int contextButtonIndex2 = contextualButtonGroup2.getContextButtonIndex(R.id.rotate_suggestion);
            if ((contextButtonIndex2 != -1 ? ((ContextualButtonGroup.ButtonData) ((ArrayList) contextualButtonGroup2.mButtonData).get(contextButtonIndex2)).button : null) == null) {
                this.mContextualButtonGroup.addButton(this.mRotationContextButton);
                this.mButtonDispatchers.put(R.id.rotate_suggestion, this.mRotationContextButton);
                this.mRotationButtonController.setRotationButton(this.mRotationContextButton, this.mRotationButtonListener);
            }
        }
        this.mNavigationInflaterView.setButtonDispatchers(this.mButtonDispatchers);
    }

    public final void updateSlippery() {
        ShadeViewController shadeViewController;
        boolean z = true;
        if ((this.mShowSwipeUpUi && isOverviewEnabled()) && ((shadeViewController = this.mPanelView) == null || !((NotificationPanelViewController) shadeViewController).isFullyExpanded() || ((NotificationPanelViewController) this.mPanelView).isCollapsing())) {
            z = false;
        }
        setSlippery(z);
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
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda3
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
        C18581 c18581 = this.mShowSwipeUpUi ? this.mQuickStepAccessibilityDelegate : null;
        homeButton.mAccessibilityDelegate = c18581;
        ArrayList arrayList = homeButton.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((View) arrayList.get(i)).setAccessibilityDelegate(c18581);
        }
    }

    public void setDefaultIconTheme(IconThemeBase iconThemeBase) {
    }

    public void setIconThemeAlpha(float f) {
    }

    public void updateOpaqueColor(int i) {
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

    public void marqueeNavigationBarIcon(int i, int i2) {
    }

    public void updateActiveIndicatorSpringParams(float f, float f2) {
    }

    public void updateHintVisibility(boolean z, boolean z2, boolean z3) {
    }

    public void updateBackPanelColor(int i, int i2, int i3, int i4) {
    }
}
