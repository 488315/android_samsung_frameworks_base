package com.android.systemui.navigationbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.RotationContextButton;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.gestural.NavigationHandle;
import com.android.systemui.navigationbar.gestural.NavigationHintHandle;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.ButtonDispatcherProxy;
import com.android.systemui.navigationbar.plugin.SamsungPluginNavigationBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.MarqueeLogic;
import com.android.systemui.navigationbar.util.NavBarTipPopupUtil;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.searcle.SearcleTipPopup;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.widget.SemTipPopup;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungNavigationBarView extends NavigationBarView {
    public final int AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE;
    public KeyButtonDrawable backAltIcon;
    public boolean canShowHideKeyboard;
    public View currentRemoteView;
    public final int displayId;
    public GestureHintGroup gestureHintGroup;
    public boolean imeVisible;
    public final NavBarIconResourceMapper keyButtonMapper;
    public final MarqueeLogic marqueeLogic;
    public final NavBarStore navBarStore;
    public final NavBarTipPopup navBarTip;
    public boolean notifyHideKeyboard;
    public final SamsungPluginNavigationBar pluginNavigationBar;
    public final SearcleManager searcleManager;
    public final SettingsHelper settingsHelper;

    public SamsungNavigationBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.displayId = context.getDisplayId();
        NavBarStore navBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
        this.navBarStore = navBarStore;
        LogWrapper logWrapper = new LogWrapper(ModuleType.NAVBAR, null);
        this.settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        NavBarButtonDrawableProvider.Companion.getClass();
        NavBarButtonDrawableProvider navBarButtonDrawableProvider = NavBarButtonDrawableProvider.INSTANCE;
        if (navBarButtonDrawableProvider == null) {
            navBarButtonDrawableProvider = new NavBarButtonDrawableProvider();
            NavBarButtonDrawableProvider.INSTANCE = navBarButtonDrawableProvider;
        }
        NavBarIconResourceMapper navBarIconResourceMapper = new NavBarIconResourceMapper(navBarButtonDrawableProvider, navBarStore, context, logWrapper);
        this.keyButtonMapper = navBarIconResourceMapper;
        this.pluginNavigationBar = new SamsungPluginNavigationBar(this, navBarStore, new ButtonDispatcherProxy(((FrameLayout) this).mContext, this.mButtonDispatchers), ((FrameLayout) this).mContext);
        this.AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE = QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT;
        this.marqueeLogic = new MarqueeLogic();
        this.navBarTip = new NavBarTipPopup(context, (WindowManager) context.getSystemService("window"), logWrapper);
        this.mContextualButtonGroup.mKeyButtonMapper = navBarIconResourceMapper;
        ContextualButton contextualButton = new ContextualButton(R.id.ime_switcher, this.mLightContext, IconType.TYPE_IME);
        ContextualButton contextualButton2 = new ContextualButton(R.id.accessibility_button, this.mLightContext, IconType.TYPE_A11Y);
        this.mRotationContextButton = new RotationContextButton(R.id.rotate_suggestion, this.mLightContext, R.drawable.ic_samsung_sysbar_rotate_button);
        boolean isGestureMode = this.navBarStateManager.isGestureMode();
        ((ArrayList) this.mContextualButtonGroup.mButtonData).clear();
        if (isGestureMode) {
            this.mContextualButtonGroup.addButton(contextualButton2);
            this.mContextualButtonGroup.addButton(contextualButton);
        } else {
            this.mContextualButtonGroup.addButton(contextualButton);
            this.mContextualButtonGroup.addButton(contextualButton2);
        }
        RotationButtonController rotationButtonController = new RotationButtonController(this.mLightContext, this.mLightIconColor, this.mDarkIconColor, R.drawable.ic_sysbar_rotate_button_ccw_start_0, R.drawable.ic_sysbar_rotate_button_ccw_start_90, R.drawable.ic_sysbar_rotate_button_cw_start_0, R.drawable.ic_sysbar_rotate_button_cw_start_90, new Supplier() { // from class: com.android.systemui.navigationbar.SamsungNavigationBarView$initButtonDispatcherGroup$1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Integer.valueOf(SamsungNavigationBarView.this.getDisplay().getRotation());
            }
        });
        this.mRotationButtonController = rotationButtonController;
        SamsungNavigationBarProxy.Companion.getClass();
        SamsungNavigationBarProxy samsungNavigationBarProxy = SamsungNavigationBarProxy.INSTANCE;
        if (samsungNavigationBarProxy == null) {
            samsungNavigationBarProxy = new SamsungNavigationBarProxy();
            SamsungNavigationBarProxy.INSTANCE = samsungNavigationBarProxy;
        }
        rotationButtonController.mBarProxy = samsungNavigationBarProxy;
        rotationButtonController.mSamsungRotateButtonResId = R.drawable.ic_samsung_sysbar_rotate_button;
        rotationButtonController.mSamsungIconCWStart0ResId = R.style.SamsungRotateButtonCWStart0;
        rotationButtonController.mSamsungIconCCWStart0ResId = R.style.SamsungRotateButtonCCWStart0;
        rotationButtonController.mSamsungIconCWStart90ResId = R.style.SamsungRotateButtonCWStart90;
        rotationButtonController.mSamsungIconCCWStart90ResId = R.style.SamsungRotateButtonCCWStart90;
        rotationButtonController.mSamsungIconCWStart180ResId = R.style.SamsungRotateButtonCWDegree180;
        rotationButtonController.mSamsungIconCCWStart180ResId = R.style.SamsungRotateButtonCCWDegree180;
        this.mButtonDispatchers.put(R.id.ime_switcher, contextualButton);
        this.mButtonDispatchers.put(R.id.accessibility_button, contextualButton2);
        this.mButtonDispatchers.put(R.id.hint_left, new ButtonDispatcher(R.id.hint_left));
        this.mButtonDispatchers.put(R.id.hint_center, new ButtonDispatcher(R.id.hint_center));
        this.mButtonDispatchers.put(R.id.hint_right, new ButtonDispatcher(R.id.hint_right));
        this.gestureHintGroup = new GestureHintGroup(this.mButtonDispatchers);
        if (BasicRune.SEARCLE) {
            SearcleManager searcleManager = (SearcleManager) Dependency.get(SearcleManager.class);
            this.searcleManager = searcleManager;
            if (searcleManager != null) {
                NavBarStateManager navBarStateManager = this.navBarStateManager;
                searcleManager.navigationBarView = this;
                searcleManager.tipPopup.navBarStateManager = navBarStateManager;
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final KeyButtonDrawable getBackIconWithAlt(boolean z) {
        if (!z) {
            return this.mBackIcon;
        }
        KeyButtonDrawable keyButtonDrawable = this.backAltIcon;
        Intrinsics.checkNotNull(keyButtonDrawable);
        return keyButtonDrawable;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final IconThemeBase getDefaultIconTheme() {
        return this.keyButtonMapper.getDefaultIconTheme();
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final GestureHintGroup getHintGroup() {
        GestureHintGroup gestureHintGroup = this.gestureHintGroup;
        if (gestureHintGroup == null) {
            return null;
        }
        return gestureHintGroup;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final ButtonDispatcher getHintView() {
        GestureHintGroup gestureHintGroup = this.gestureHintGroup;
        if (gestureHintGroup == null) {
            gestureHintGroup = null;
        }
        int i = GestureHintGroup.$r8$clinit;
        return (ButtonDispatcher) gestureHintGroup.hintGroup.get(1);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final ExtendableBar getPluginBar() {
        return this.pluginNavigationBar;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final GestureHintDrawable getSecondaryHomeHandleDrawable(int i) {
        return this.keyButtonMapper.getGestureHandleDrawable(IconType.TYPE_SECONDARY_HOME_HANDLE, i);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void marqueeNavigationBarIcon(int i, int i2) {
        boolean z;
        if (this.mCurrentView == null) {
            reorient();
            return;
        }
        MarqueeLogic marqueeLogic = this.marqueeLogic;
        float f = getContext().getResources().getDisplayMetrics().density;
        int i3 = marqueeLogic.horizontalShift;
        if (!(-16 <= i3 && i3 < 17)) {
            marqueeLogic.horizontalMoved = -marqueeLogic.horizontalMoved;
        }
        int i4 = marqueeLogic.verticalShift;
        if (!(-10 <= i4 && i4 < 11)) {
            marqueeLogic.verticalMoved = -marqueeLogic.verticalMoved;
        }
        marqueeLogic.horizontalShift = i3 + marqueeLogic.horizontalMoved;
        marqueeLogic.verticalShift = i4 + marqueeLogic.verticalMoved;
        marqueeLogic.scaleFactor = f / 4.0f;
        MarqueeLogic marqueeLogic2 = this.marqueeLogic;
        int ceil = (int) Math.ceil((this.mIsVertical ? marqueeLogic2.verticalShift : marqueeLogic2.horizontalShift) * marqueeLogic2.scaleFactor);
        MarqueeLogic marqueeLogic3 = this.marqueeLogic;
        int ceil2 = (int) Math.ceil((this.mIsVertical ? marqueeLogic3.horizontalShift : marqueeLogic3.verticalShift) * marqueeLogic3.scaleFactor);
        boolean z2 = this.navBarStateManager.states.canMove;
        this.marqueeLogic.getClass();
        int min = (int) (Math.min(i, i2) * 0.0222d);
        int size = this.mButtonDispatchers.size();
        for (int i5 = 0; i5 < size; i5++) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.valueAt(i5);
            if (z2) {
                Iterator it = ((ArrayList) this.mContextualButtonGroup.mButtonData).iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((ContextualButtonGroup.ButtonData) it.next()).button.equals(buttonDispatcher)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    int i6 = this.mCurrentRotation;
                    int i7 = i6 == 1 ? min + ceil2 : ceil2;
                    int i8 = i6 == 0 ? min + 0 : 0;
                    int i9 = i6 == 3 ? min + 0 : 0;
                    Iterator it2 = buttonDispatcher.mViews.iterator();
                    while (it2.hasNext()) {
                        ((View) it2.next()).setPadding(ceil, i7, i8, i9);
                    }
                }
            }
            Iterator it3 = buttonDispatcher.mViews.iterator();
            while (it3.hasNext()) {
                ((View) it3.next()).setPadding(ceil, ceil2, 0, 0);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final boolean needTouchableInsetsFrame() {
        if (!this.navBarStateManager.isGestureMode()) {
            return true;
        }
        boolean z = this.imeVisible;
        if (z && this.canShowHideKeyboard) {
            return true;
        }
        boolean z2 = BasicRune.NAVBAR_MULTI_MODAL_ICON;
        if (z2 && z) {
            NavBarStateManager navBarStateManager = this.navBarStateManager;
            if (navBarStateManager.canShowKeyboardButtonForRotation(navBarStateManager.states.rotation)) {
                return true;
            }
            NavBarStateManager navBarStateManager2 = this.navBarStateManager;
            if (z2 && (!navBarStateManager2.isGestureMode() || (navBarStateManager2.navBarRemoteViewManager.isSetMultimodalButton() && navBarStateManager2.canPlaceKeyboardButton(navBarStateManager2.states.rotation)))) {
                return true;
            }
        }
        return this.navBarStateManager.isNavBarButtonForcedVisible() || this.navBarStateManager.shouldShowSUWStyle();
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.navBarStateManager.supportLargeCoverScreenNavBar()) {
            DisplayInfo displayInfo = new DisplayInfo();
            ((FrameLayout) this).mContext.getDisplay().getDisplayInfo(displayInfo);
            int i = displayInfo.rotation;
            Rect rect = (Rect) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding(i), ((FrameLayout) this).mContext.getDisplayId(), new Rect(0, 0, 0, 0));
            if (rect != null) {
                setPadding(rect.left, rect.top, rect.right, rect.bottom);
            }
            GestureHintGroup gestureHintGroup = this.gestureHintGroup;
            if (gestureHintGroup == null) {
                gestureHintGroup = null;
            }
            gestureHintGroup.setCurrentRotation(i, false);
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarAttachedToWindow(this, this.mBarTransitions), this.displayId);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        SearcleManager searcleManager;
        super.onConfigurationChanged(configuration);
        updateCurrentView();
        if (!BasicRune.SEARCLE || (searcleManager = this.searcleManager) == null || searcleManager == null) {
            return;
        }
        SearcleTipPopup searcleTipPopup = searcleManager.tipPopup;
        if (searcleTipPopup.isTipPopupShowing) {
            searcleTipPopup.hideImmediate();
            searcleTipPopup.showSearcleTip(true);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarDetachedFromWindow(false, 1, null), this.displayId);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.View
    public final void onFinishInflate() {
        NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) findViewById(R.id.navigation_inflater);
        this.mNavigationInflaterView = navigationBarInflaterView;
        navigationBarInflaterView.setButtonDispatchers(this.mButtonDispatchers);
        updateOrientationViews();
        updateIcons(Configuration.EMPTY);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void onImeVisibilityChanged(boolean z) {
        super.onImeVisibilityChanged(z);
        this.imeVisible = z;
        if (z) {
            NavBarStateManager navBarStateManager = this.navBarStateManager;
            this.canShowHideKeyboard = !navBarStateManager.isGestureMode() || (navBarStateManager.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() && navBarStateManager.canPlaceKeyboardButton(navBarStateManager.states.rotation));
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.navBarStateManager.isGestureMode() || (motionEvent.getFlags() & this.AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE) == 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        NavBarTipPopup navBarTipPopup = this.navBarTip;
        int measuredWidth = getMeasuredWidth();
        if (navBarTipPopup.navBarWidth != measuredWidth && navBarTipPopup.tipLayout.getTag() != null) {
            navBarTipPopup.hide();
        }
        navBarTipPopup.navBarWidth = measuredWidth;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        boolean z = this.navBarStateManager.states.canMove;
        int stableInsetLeft = getRootWindowInsets().getStableInsetLeft();
        int stableInsetRight = getRootWindowInsets().getStableInsetRight();
        int dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
        boolean isGestureMode = this.navBarStateManager.isGestureMode();
        if (!z || (i3 = this.mCurrentRotation) == 0 || i3 == 2) {
            if (isGestureMode) {
                stableInsetLeft = 0;
                stableInsetRight = 0;
            }
            this.mBarTransitions.mBarBackground.mFrame = new Rect(stableInsetLeft, dimensionPixelSize2 - dimensionPixelSize, size - stableInsetRight, size2);
        } else if (i3 == 1) {
            this.mBarTransitions.mBarBackground.mFrame = new Rect(size - dimensionPixelSize, 0, size, size2);
        } else if (i3 == 3) {
            this.mBarTransitions.mBarBackground.mFrame = new Rect(0, 0, dimensionPixelSize, size2);
        }
        super.onMeasure(i, i2);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void onScreenStateChanged(boolean z) {
        this.mScreenOn = z;
        if (z) {
            return;
        }
        if (BasicRune.NAVBAR_ICON_MOVEMENT) {
            ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconMarquee(false, 1, null), getContext().getDisplay().getDisplayId());
        }
        NavBarTipPopup navBarTipPopup = this.navBarTip;
        if (navBarTipPopup.isTipPopupShowing) {
            navBarTipPopup.hide();
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void reInflateNavBarLayout() {
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        if (navigationBarInflaterView != null) {
            navigationBarInflaterView.createInflaters();
        }
        NavigationBarInflaterView navigationBarInflaterView2 = this.mNavigationInflaterView;
        if (navigationBarInflaterView2 != null) {
            navigationBarInflaterView2.updateLayoutProviderView();
        }
        updateCurrentView();
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void reorient() {
        super.reorient();
        if (BasicRune.NAVBAR_ICON_MOVEMENT) {
            ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconMarquee(false, 1, null), getContext().getDisplay().getDisplayId());
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void setDefaultIconTheme(IconThemeBase iconThemeBase) {
        this.keyButtonMapper.setPreloadedIconSet(iconThemeBase);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void setIconThemeAlpha(float f) {
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        if (navigationBarTransitions != null) {
            navigationBarTransitions.mLightsOutDisabled = !(f == 1.0f);
        }
        this.mVertical.findViewById(R.id.nav_buttons).setAlpha(f);
        this.mHorizontal.findViewById(R.id.nav_buttons).setAlpha(f);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void showA11ySwipeUpTipPopup() {
        final NavBarTipPopup navBarTipPopup = this.navBarTip;
        final boolean semIsScreenReaderEnabled = ((AccessibilityManager) getContext().getSystemService("accessibility")).semIsScreenReaderEnabled();
        navBarTipPopup.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarTipPopup$showA11ySwipeUpTip$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                NavBarTipPopup navBarTipPopup2 = NavBarTipPopup.this;
                int i = semIsScreenReaderEnabled ? R.string.f778x52b17911 : R.string.gesture_accessibility_guide_gesture_onboarding;
                SemTipPopup semTipPopup = navBarTipPopup2.tipPopup;
                if (semTipPopup != null && semTipPopup.isShowing()) {
                    navBarTipPopup2.hide();
                }
                Integer valueOf = Integer.valueOf(i);
                View view = navBarTipPopup2.tipLayout;
                view.setTag(valueOf);
                Context context = navBarTipPopup2.context;
                int i2 = context.getResources().getConfiguration().orientation;
                if (navBarTipPopup2.isTipPopupShowing || i2 != 1) {
                    z = false;
                } else {
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.navbar_tip_margin_start);
                    try {
                        WindowManager windowManager = navBarTipPopup2.windowManager;
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, dimensionPixelSize, 0, 2008, 520, -3);
                        layoutParams.semAddPrivateFlags(16);
                        layoutParams.setTitle("NavBarTip");
                        layoutParams.gravity = 83;
                        windowManager.addView(view, layoutParams);
                    } catch (Exception unused) {
                    }
                    navBarTipPopup2.currentMessage = i;
                    z = true;
                }
                if (z) {
                    NavBarTipPopupUtil navBarTipPopupUtil = NavBarTipPopupUtil.INSTANCE;
                    Context context2 = NavBarTipPopup.this.context;
                    navBarTipPopupUtil.getClass();
                    Prefs.putInt(context2, "NavigationBarAccessibilityShortcutTipCount", Prefs.getInt(context2, "NavigationBarAccessibilityShortcutTipCount", 0) + 1);
                }
            }
        });
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void showPinningEscapeToast() {
        this.mScreenPinningNotify.showEscapeToast(this.navBarStateManager.isGestureMode(), isRecentsButtonVisible());
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        if (edgeBackGestureHandler.mIsNewBackAffordanceEnabled) {
            edgeBackGestureHandler.mEdgeBackPlugin.updateActiveIndicatorSpringParams(f, f2);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        if (edgeBackGestureHandler.mIsNewBackAffordanceEnabled) {
            edgeBackGestureHandler.mEdgeBackPlugin.updateBackPanelColor(i, i2, i3, i4);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateCurrentView() {
        boolean z;
        this.mHorizontal.setVisibility(8);
        this.mVertical.setVisibility(8);
        updateCurrentRotation();
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (navBarStateManager.states.supportPhoneLayoutProvider || navBarStateManager.isGestureMode()) {
            z = this.mIsVertical;
        } else {
            int i = this.mCurrentRotation;
            z = true;
            if (i != 1 && i != 3) {
                z = false;
            }
        }
        View view = z ? this.mVertical : this.mHorizontal;
        this.mCurrentView = view;
        view.setVisibility(0);
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        if (z != navigationBarInflaterView.mIsVertical) {
            navigationBarInflaterView.mIsVertical = z;
        }
        navigationBarInflaterView.updateButtonDispatchersCurrentView();
        updateLayoutTransitionsEnabled();
        boolean z2 = BasicRune.NAVBAR_REMOTEVIEW;
        if (z2 && z2) {
            this.currentRemoteView = this.mCurrentView.findViewById(R.id.nav_bar_widget);
            updateRemoteViewContainer();
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateDisabledSystemUiStateFlags(SysUiState sysUiState) {
        super.updateDisabledSystemUiStateFlags(sysUiState);
        sysUiState.setFlag(4194304L, (this.mDisabledFlags & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0);
        sysUiState.commitUpdate(((FrameLayout) this).mContext.getDisplayId());
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateGestureHintGroupRotation() {
        if (this.navBarStateManager.isBottomGestureMode(false)) {
            GestureHintGroup gestureHintGroup = this.gestureHintGroup;
            if (gestureHintGroup == null) {
                gestureHintGroup = null;
            }
            gestureHintGroup.setCurrentRotation(this.mCurrentRotation, this.navBarStateManager.states.canMove);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateHintVisibility(boolean z, boolean z2, boolean z3) {
        int i = 0;
        int i2 = this.settingsHelper.isNavBarButtonOrderDefault() ? 0 : 2;
        int i3 = 2 - i2;
        GestureHintGroup gestureHintGroup = this.gestureHintGroup;
        if (gestureHintGroup == null) {
            gestureHintGroup = null;
        }
        ((ButtonDispatcher) gestureHintGroup.hintGroup.get(1)).setVisibility(z2 ? 0 : 4);
        ArrayList arrayList = gestureHintGroup.hintGroup;
        ((ButtonDispatcher) arrayList.get(i2)).setVisibility(z ? 0 : 4);
        ((ButtonDispatcher) arrayList.get(i3)).setVisibility(z3 ? 0 : 4);
        ButtonDispatcher homeHandle = getHomeHandle();
        if (!z && !z2) {
            i = 4;
        }
        homeHandle.setVisibility(i);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateIcons(Configuration configuration) {
        super.updateIcons(configuration);
        boolean z = configuration.densityDpi != this.mConfiguration.densityDpi;
        boolean z2 = configuration.getLayoutDirection() != this.mConfiguration.getLayoutDirection();
        if (z || z2) {
            NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
            navBarIconResourceMapper.isRTL = this.mConfiguration.getLayoutDirection() == 1;
            this.mRecentIcon = navBarIconResourceMapper.getButtonDrawable(IconType.TYPE_RECENT);
            this.mHomeDefaultIcon = navBarIconResourceMapper.getButtonDrawable(IconType.TYPE_HOME);
            this.mBackIcon = navBarIconResourceMapper.getButtonDrawable(IconType.TYPE_BACK);
            this.backAltIcon = navBarIconResourceMapper.getButtonDrawable(IconType.TYPE_BACK_ALT);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateIconsAndHints() {
        updateIcons(Configuration.EMPTY);
        updateNavButtonIcons();
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateLayoutProviderView() {
        this.mNavigationInflaterView.updateLayoutProviderView();
        updateCurrentView();
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateNavButtonIcons() {
        super.updateNavButtonIcons();
        if (BasicRune.NAVBAR_GESTURE) {
            boolean z = (this.mNavigationIconHints & 1) != 0;
            boolean z2 = BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && ((FrameLayout) this).mContext.getDisplayId() == 1 && this.navBarStateManager.canShowButtonInLargeCoverIme();
            if (this.navBarStateManager.isGestureMode()) {
                NavigationHandle navigationHandle = (NavigationHandle) getHomeHandle().mCurrentView;
                if (navigationHandle != null) {
                    navigationHandle.setImageDrawable(this.keyButtonMapper.getGestureHandleDrawable(IconType.TYPE_GESTURE_HANDLE_HINT, 0));
                }
                GestureHintGroup gestureHintGroup = this.gestureHintGroup;
                if (gestureHintGroup == null) {
                    gestureHintGroup = null;
                }
                NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
                Iterator it = gestureHintGroup.hintGroup.iterator();
                while (it.hasNext()) {
                    NavigationHintHandle navigationHintHandle = (NavigationHintHandle) ((ButtonDispatcher) it.next()).mCurrentView;
                    if (navigationHintHandle != null) {
                        navigationHintHandle.iconResourceMapper = navBarIconResourceMapper;
                    }
                }
                gestureHintGroup.setCurrentRotation(this.mCurrentRotation, this.navBarStateManager.states.canMove);
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && ((FrameLayout) this).mContext.getDisplayId() == 1 && z && z2) {
                    ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnSetGestureHintVisibility(false, false, false), this.displayId);
                } else {
                    NavBarStore navBarStore = this.navBarStore;
                    boolean z3 = getRecentsButton().getVisibility() == 0;
                    int i = this.mDisabledFlags;
                    ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnSetGestureHintVisibility(z3, (2097152 & i) == 0, (i & QuickStepContract.SYSUI_STATE_BACK_DISABLED) == 0), this.displayId);
                }
                NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
                navigationBarTransitions.applyDarkIntensity(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
            }
            if (z) {
                NavBarStateManager navBarStateManager = this.navBarStateManager;
                boolean z4 = !navBarStateManager.isGestureMode() || (navBarStateManager.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() && navBarStateManager.canPlaceKeyboardButton(navBarStateManager.states.rotation));
                this.canShowHideKeyboard = z4;
                if (!z4) {
                    getBackButton().setVisibility(4);
                }
                NavBarStateManager navBarStateManager2 = this.navBarStateManager;
                if (!navBarStateManager2.canShowKeyboardButtonForRotation(navBarStateManager2.states.rotation)) {
                    this.mContextualButtonGroup.setButtonVisibility(R.id.ime_switcher, false);
                }
            }
            boolean z5 = z && this.canShowHideKeyboard;
            if (this.navBarStateManager.isGestureMode() && this.notifyHideKeyboard != z5) {
                this.notifyHideKeyboard = z5;
                ShadeViewController shadeViewController = this.mPanelView;
                if (shadeViewController != null) {
                    ((NotificationPanelViewController) shadeViewController).mNavBarKeyboardButtonShowing = z5;
                }
            }
        }
        if (BasicRune.NAVBAR_REMOTEVIEW && this.currentRemoteView != null) {
            updateRemoteViewContainerVisibility();
            ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSysUiStateFlag(false, 1, null));
        }
        if (!(((NavBarStoreImpl) this.navBarStore).pluginBarInteractionManager.pluginNavigationBar != null) || this.navBarStateManager.isGestureMode()) {
            return;
        }
        boolean z6 = getBackButton().getVisibility() == 0 && getHomeButton().getVisibility() == 0;
        Iterator it2 = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(R.id.navbar_pin), Integer.valueOf(R.id.nav_custom_key_1), Integer.valueOf(R.id.nav_custom_key_2), Integer.valueOf(R.id.nav_custom_key_3), Integer.valueOf(R.id.nav_custom_key_4), Integer.valueOf(R.id.nav_custom_key_5)).iterator();
        while (it2.hasNext()) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(((Number) it2.next()).intValue());
            if (buttonDispatcher != null) {
                buttonDispatcher.setVisibility(z6 ? 0 : 4);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateNavigationBarColor() {
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        ColorSetting colorSetting = (ColorSetting) navBarStateManager.interactorFactory.get(ColorSetting.class);
        Integer valueOf = Integer.valueOf(colorSetting != null ? colorSetting.getNavigationBarColor() : navBarStateManager.context.getColor(R.color.light_navbar_background_opaque));
        navBarStateManager.logNavBarStates(valueOf, "getNavigationBarColor");
        Intrinsics.checkNotNull(valueOf);
        int intValue = valueOf.intValue();
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        if (navigationBarTransitions != null) {
            navigationBarTransitions.mBarBackground.updateOpaqueColor(intValue);
        }
        Settings.Global.putInt(this.settingsHelper.mResolver, "navigationbar_current_color", intValue);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateOpaqueColor(int i) {
        this.mBarTransitions.mBarBackground.updateOpaqueColor(i);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateRemoteViewContainer() {
        LightBarTransitionsController lightBarTransitionsController;
        View view = this.currentRemoteView;
        if (view == null) {
            return;
        }
        LinearLayout linearLayout = view != null ? (LinearLayout) view.findViewById(R.id.left_remoteview) : null;
        View view2 = this.currentRemoteView;
        LinearLayout linearLayout2 = view2 != null ? (LinearLayout) view2.findViewById(R.id.right_remoteview) : null;
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        float f = (navigationBarTransitions == null || (lightBarTransitionsController = navigationBarTransitions.mLightTransitionsController) == null) ? 0.0f : lightBarTransitionsController.mDarkIntensity;
        updateRemoteViewContainerVisibility();
        if (linearLayout == null || linearLayout2 == null) {
            return;
        }
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateRemoteViewContainer(linearLayout, linearLayout2, this.mContextualButtonGroup.getVisibleContextButton() != null, f, this.displayId));
    }

    public final void updateRemoteViewContainerVisibility() {
        NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) ((NavBarStoreImpl) this.navBarStore).getModule(NavBarRemoteViewManager.class, this.displayId);
        if (navBarRemoteViewManager != null) {
            View view = this.currentRemoteView;
            if (view != null) {
                ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(R.id.ime_switcher);
                view.setVisibility(((buttonDispatcher != null && buttonDispatcher.getVisibility() == 0) || (this.navBarStateManager.isGestureMode() && !navBarRemoteViewManager.showInGestureMode)) ? 4 : 0);
            }
            int i = this.displayId;
            if (navBarRemoteViewManager.showInGestureMode) {
                int i2 = (((NavBarStoreImpl) navBarRemoteViewManager.getNavBarStore()).getNavStateManager(i).states.canMove && ((NavBarStoreImpl) navBarRemoteViewManager.getNavBarStore()).getNavStateManager(i).states.rotation == 1) ? 1 - navBarRemoteViewManager.adaptivePosition : navBarRemoteViewManager.adaptivePosition;
                LinearLayout linearLayout = navBarRemoteViewManager.leftContainer;
                if (linearLayout != null) {
                    linearLayout.setVisibility(i2 == 0 ? 0 : 4);
                }
                LinearLayout linearLayout2 = navBarRemoteViewManager.rightContainer;
                if (linearLayout2 == null) {
                    return;
                }
                linearLayout2.setVisibility(i2 == 1 ? 0 : 4);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void orientBackButton(KeyButtonDrawable keyButtonDrawable) {
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void orientHomeButton(KeyButtonDrawable keyButtonDrawable) {
    }
}
