package com.android.systemui.navigationbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.gestural.NavigationHandle;
import com.android.systemui.navigationbar.gestural.NavigationHintHandle;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.ButtonDispatcherProxy;
import com.android.systemui.navigationbar.plugin.SamsungPluginNavigationBar;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.MarqueeLogic;
import com.android.systemui.navigationbar.util.NavBarTipPopupUtil;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.searcle.SearcleTipPopup;
import com.android.systemui.shared.rotation.FloatingRotationButton;
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
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SamsungNavigationBarView extends NavigationBarView {
    public final int AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE;
    public KeyButtonDrawable backAltIcon;
    public boolean canShowHideKeyboard;
    public View currentRemoteView;
    public final int displayId;
    public final GestureHintGroup gestureHintGroup;
    public boolean imeVisible;
    public final NavBarIconResourceMapper keyButtonMapper;
    public final MarqueeLogic marqueeLogic;
    public final NavBarStore navBarStore;
    public final NavBarTipPopup navBarTip;
    public boolean notifyHideKeyboard;
    public final SamsungPluginNavigationBar pluginNavigationBar;
    public final SearcleManager searcleManager;
    private final SettingsHelper settingsHelper;

    public SamsungNavigationBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.displayId = context.getDisplayId();
        NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
        this.navBarStore = navBarStore;
        LogWrapper logWrapper = new LogWrapper(ModuleType.NAVBAR, null);
        this.settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        NavBarButtonDrawableProvider.Companion.getClass();
        NavBarButtonDrawableProvider navBarButtonDrawableProvider = NavBarButtonDrawableProvider.INSTANCE;
        if (navBarButtonDrawableProvider == null) {
            navBarButtonDrawableProvider = new NavBarButtonDrawableProvider();
            NavBarButtonDrawableProvider.INSTANCE = navBarButtonDrawableProvider;
        }
        NavBarIconResourceMapper navBarIconResourceMapper = new NavBarIconResourceMapper(navBarButtonDrawableProvider, navBarStore, context, logWrapper);
        this.keyButtonMapper = navBarIconResourceMapper;
        this.pluginNavigationBar = new SamsungPluginNavigationBar(this, navBarStore, new ButtonDispatcherProxy(((FrameLayout) this).mContext, this.mButtonDispatchers), ((FrameLayout) this).mContext);
        this.AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE = 536870912;
        this.marqueeLogic = new MarqueeLogic();
        this.navBarTip = new NavBarTipPopup(context, (WindowManager) context.getSystemService("window"), logWrapper);
        this.mContextualButtonGroup.mKeyButtonMapper = navBarIconResourceMapper;
        ContextualButton contextualButton = new ContextualButton(R.id.ime_switcher, this.mLightContext, IconType.TYPE_IME);
        ContextualButton contextualButton2 = new ContextualButton(R.id.accessibility_button, this.mLightContext, IconType.TYPE_A11Y);
        boolean isGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
        ((ArrayList) this.mContextualButtonGroup.mButtonData).clear();
        if (isGestureMode) {
            this.mContextualButtonGroup.addButton(contextualButton2);
            this.mContextualButtonGroup.addButton(contextualButton);
        } else {
            this.mContextualButtonGroup.addButton(contextualButton);
            this.mContextualButtonGroup.addButton(contextualButton2);
        }
        FloatingRotationButton floatingRotationButton = this.mFloatingRotationButton;
        floatingRotationButton.mSamsungBottomMarginResource = R.dimen.samsung_floating_rotation_button_bottom_margin;
        floatingRotationButton.mSamsungDiameterResource = R.dimen.samsung_floating_rotation_button_diameter;
        floatingRotationButton.mSamsungHiddenVisualCueRotateBtnResource = R.drawable.samsung_hidden_visual_cue_rotate_btn;
        floatingRotationButton.updateDimensionResources();
        RotationButtonController rotationButtonController = this.mRotationButtonController;
        SamsungNavigationBarProxy.Companion.getClass();
        rotationButtonController.mBarProxy = SamsungNavigationBarProxy.Companion.getInstance();
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
            SearcleManager searcleManager = (SearcleManager) Dependency.sDependency.getDependencyInner(SearcleManager.class);
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
        NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
        IconType iconType = IconType.TYPE_SECONDARY_HOME_HANDLE;
        return ((NavBarButtonDrawableProvider) navBarIconResourceMapper.buttonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(iconType), i);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void marqueeNavigationBarIcon(int i, int i2) {
        if (this.mCurrentView == null) {
            reorient();
            return;
        }
        MarqueeLogic marqueeLogic = this.marqueeLogic;
        float f = getContext().getResources().getDisplayMetrics().density;
        int i3 = marqueeLogic.horizontalShift;
        if (-16 > i3 || i3 >= 17) {
            marqueeLogic.horizontalMoved = -marqueeLogic.horizontalMoved;
        }
        int i4 = marqueeLogic.verticalShift;
        if (-10 > i4 || i4 >= 11) {
            marqueeLogic.verticalMoved = -marqueeLogic.verticalMoved;
        }
        marqueeLogic.horizontalShift = i3 + marqueeLogic.horizontalMoved;
        marqueeLogic.verticalShift = i4 + marqueeLogic.verticalMoved;
        marqueeLogic.scaleFactor = f / 4.0f;
        MarqueeLogic marqueeLogic2 = this.marqueeLogic;
        int ceil = (int) Math.ceil((this.mIsVertical ? marqueeLogic2.verticalShift : marqueeLogic2.horizontalShift) * marqueeLogic2.scaleFactor);
        MarqueeLogic marqueeLogic3 = this.marqueeLogic;
        int ceil2 = (int) Math.ceil((this.mIsVertical ? marqueeLogic3.horizontalShift : marqueeLogic3.verticalShift) * marqueeLogic3.scaleFactor);
        boolean z = ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove;
        this.marqueeLogic.getClass();
        int min = (int) (Math.min(i, i2) * 0.0222d);
        int size = this.mButtonDispatchers.size();
        for (int i5 = 0; i5 < size; i5++) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.valueAt(i5);
            if (z) {
                Iterator it = ((ArrayList) this.mContextualButtonGroup.mButtonData).iterator();
                while (it.hasNext()) {
                    if (((ContextualButtonGroup.ButtonData) it.next()).button.equals(buttonDispatcher)) {
                        int i6 = this.mCurrentRotation;
                        int i7 = i6 == 1 ? min + ceil2 : ceil2;
                        int i8 = i6 == 0 ? min : 0;
                        int i9 = i6 == 3 ? min : 0;
                        Iterator it2 = buttonDispatcher.mViews.iterator();
                        while (it2.hasNext()) {
                            ((View) it2.next()).setPadding(ceil, i7, i8, i9);
                        }
                    }
                }
            }
            Iterator it3 = buttonDispatcher.mViews.iterator();
            while (it3.hasNext()) {
                ((View) it3.next()).setPadding(ceil, ceil2, 0, 0);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0032, code lost:
    
        if (r0.canShowMultiModalButtonForRotation(r0.states.rotation) == false) goto L15;
     */
    @Override // com.android.systemui.navigationbar.NavigationBarView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean needTouchableInsetsFrame() {
        /*
            r2 = this;
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = r2.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            boolean r0 = r0.isGestureMode()
            if (r0 == 0) goto L4b
            boolean r0 = r2.imeVisible
            if (r0 == 0) goto L12
            boolean r1 = r2.canShowHideKeyboard
            if (r1 != 0) goto L4b
        L12:
            boolean r1 = com.android.systemui.BasicRune.NAVBAR_MULTI_MODAL_ICON
            if (r1 == 0) goto L34
            if (r0 == 0) goto L34
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = r2.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            com.android.systemui.navigationbar.model.NavBarStates r1 = r0.states
            int r1 = r1.rotation
            boolean r0 = r0.canShowKeyboardButtonForRotation(r1)
            if (r0 != 0) goto L4b
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = r2.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            com.android.systemui.navigationbar.model.NavBarStates r1 = r0.states
            int r1 = r1.rotation
            boolean r0 = r0.canShowMultiModalButtonForRotation(r1)
            if (r0 != 0) goto L4b
        L34:
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = r2.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            boolean r0 = r0.isNavBarButtonForcedVisible()
            if (r0 != 0) goto L4b
            com.android.systemui.navigationbar.store.NavBarStateManager r2 = r2.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r2 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r2
            boolean r2 = r2.shouldShowSUWStyle()
            if (r2 == 0) goto L49
            goto L4b
        L49:
            r2 = 0
            goto L4c
        L4b:
            r2 = 1
        L4c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.SamsungNavigationBarView.needTouchableInsetsFrame():boolean");
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && ((NavBarStateManagerImpl) this.navBarStateManager).supportLargeCoverScreenNavBar()) {
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
        this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarAttachedToWindow(this, this.mBarTransitions), this.displayId);
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
        this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarDetachedFromWindow(false, 1, null), this.displayId);
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
        boolean canShowHideKeyboardButtonForRotation;
        super.onImeVisibilityChanged(z);
        this.imeVisible = z;
        if (z) {
            canShowHideKeyboardButtonForRotation = ((NavBarStateManagerImpl) r1).canShowHideKeyboardButtonForRotation(((NavBarStateManagerImpl) this.navBarStateManager).states.rotation);
            this.canShowHideKeyboard = canShowHideKeyboardButtonForRotation;
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() || (motionEvent.getFlags() & this.AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE) == 0) {
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
        boolean z = ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove;
        int stableInsetLeft = getRootWindowInsets().getStableInsetLeft();
        int stableInsetRight = getRootWindowInsets().getStableInsetRight();
        int dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.resolver_max_collapsed_height);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(android.R.dimen.resolver_empty_state_container_padding_top);
        boolean isGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
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
            this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconMarquee(false, 1, null), getContext().getDisplay().getDisplayId());
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
            this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconMarquee(false, 1, null), getContext().getDisplay().getDisplayId());
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void setDefaultIconTheme(IconThemeBase iconThemeBase) {
        NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
        navBarIconResourceMapper.getClass();
        navBarIconResourceMapper.logWrapper.d(navBarIconResourceMapper.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setPreloadedIconSet() null: ", iconThemeBase == null));
        navBarIconResourceMapper.preloadedIconSet = iconThemeBase;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void setIconThemeAlpha(float f) {
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        if (navigationBarTransitions != null) {
            navigationBarTransitions.mLightsOutDisabled = !(f == 1.0f);
        }
        this.mVertical.requireViewById(R.id.nav_buttons).setAlpha(f);
        this.mHorizontal.requireViewById(R.id.nav_buttons).setAlpha(f);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void showA11ySwipeUpTipPopup() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        final NavBarTipPopup navBarTipPopup = this.navBarTip;
        final boolean semIsScreenReaderEnabled = accessibilityManager.semIsScreenReaderEnabled();
        navBarTipPopup.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarTipPopup$showA11ySwipeUpTip$1
            @Override // java.lang.Runnable
            public final void run() {
                NavBarTipPopup navBarTipPopup2 = NavBarTipPopup.this;
                int i = semIsScreenReaderEnabled ? R.string.gesture_accessibility_guide_gesture_onboarding_voice_assistant_on : R.string.gesture_accessibility_guide_gesture_onboarding;
                SemTipPopup semTipPopup = navBarTipPopup2.tipPopup;
                if (semTipPopup != null && semTipPopup.isShowing()) {
                    navBarTipPopup2.hide();
                }
                navBarTipPopup2.tipLayout.setTag(Integer.valueOf(i));
                int i2 = navBarTipPopup2.context.getResources().getConfiguration().orientation;
                if (navBarTipPopup2.isTipPopupShowing || i2 != 1) {
                    return;
                }
                int dimensionPixelSize = navBarTipPopup2.context.getResources().getDimensionPixelSize(R.dimen.navbar_tip_margin_start);
                try {
                    WindowManager windowManager = navBarTipPopup2.windowManager;
                    View view = navBarTipPopup2.tipLayout;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, dimensionPixelSize, 0, 2008, 520, -3);
                    layoutParams.semAddPrivateFlags(16);
                    layoutParams.setTitle("NavBarTip");
                    layoutParams.gravity = 83;
                    windowManager.addView(view, layoutParams);
                } catch (Exception unused) {
                }
                navBarTipPopup2.currentMessage = i;
                NavBarTipPopupUtil navBarTipPopupUtil = NavBarTipPopupUtil.INSTANCE;
                Context context = NavBarTipPopup.this.context;
                navBarTipPopupUtil.getClass();
                Prefs.putInt(context, "NavigationBarAccessibilityShortcutTipCount", Prefs.get(context).getInt("NavigationBarAccessibilityShortcutTipCount", 0) + 1);
            }
        });
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void showPinningEscapeToast() {
        this.mScreenPinningNotify.showEscapeToast(((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode(), isRecentsButtonVisible());
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        this.mEdgeBackGestureHandler.mEdgeBackPlugin.updateActiveIndicatorSpringParams(f, f2);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackGestureHandler.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.updateBackGestureIcon(drawable, drawable2);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        this.mEdgeBackGestureHandler.mEdgeBackPlugin.updateBackPanelColor(i, i2, i3, i4);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateCurrentView() {
        boolean z;
        this.mHorizontal.setVisibility(8);
        this.mVertical.setVisibility(8);
        updateCurrentRotation();
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
        if (navBarStateManagerImpl.states.supportPhoneLayoutProvider || navBarStateManagerImpl.isGestureMode()) {
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
        if (((NavBarStateManagerImpl) this.navBarStateManager).isBottomGestureMode(false)) {
            GestureHintGroup gestureHintGroup = this.gestureHintGroup;
            if (gestureHintGroup == null) {
                gestureHintGroup = null;
            }
            gestureHintGroup.setCurrentRotation(this.mCurrentRotation, ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateHintVisibility(boolean z, boolean z2, boolean z3) {
        int i = 0;
        int i2 = this.settingsHelper.isNavBarButtonOrderDefault() ? 0 : 2;
        int i3 = 2 - i2;
        GestureHintGroup hintGroup = getHintGroup();
        ((ButtonDispatcher) hintGroup.hintGroup.get(1)).setVisibility(z2 ? 0 : 4);
        ((ButtonDispatcher) hintGroup.hintGroup.get(i2)).setVisibility(z ? 0 : 4);
        ((ButtonDispatcher) hintGroup.hintGroup.get(i3)).setVisibility(z3 ? 0 : 4);
        ButtonDispatcher homeHandle = getHomeHandle();
        if (homeHandle == null) {
            return;
        }
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
        boolean canShowHideKeyboardButtonForRotation;
        super.updateNavButtonIcons();
        if (BasicRune.NAVBAR_GESTURE) {
            boolean z = (this.mNavigationIconHints & 1) != 0;
            boolean z2 = BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && ((FrameLayout) this).mContext.getDisplayId() == 1 && ((NavBarStateManagerImpl) this.navBarStateManager).canShowButtonInLargeCoverIme();
            if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
                ButtonDispatcher homeHandle = getHomeHandle();
                NavigationHandle navigationHandle = (NavigationHandle) (homeHandle != null ? homeHandle.mCurrentView : null);
                if (navigationHandle != null) {
                    NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
                    navigationHandle.setImageDrawable(((NavBarButtonDrawableProvider) navBarIconResourceMapper.buttonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(IconType.TYPE_GESTURE_HANDLE_HINT), 0));
                }
                GestureHintGroup gestureHintGroup = this.gestureHintGroup;
                if (gestureHintGroup == null) {
                    gestureHintGroup = null;
                }
                NavBarIconResourceMapper navBarIconResourceMapper2 = this.keyButtonMapper;
                Iterator it = gestureHintGroup.hintGroup.iterator();
                while (it.hasNext()) {
                    NavigationHintHandle navigationHintHandle = (NavigationHintHandle) ((ButtonDispatcher) it.next()).mCurrentView;
                    if (navigationHintHandle != null) {
                        navigationHintHandle.iconResourceMapper = navBarIconResourceMapper2;
                    }
                }
                gestureHintGroup.setCurrentRotation(this.mCurrentRotation, ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove);
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && ((FrameLayout) this).mContext.getDisplayId() == 1 && z && z2) {
                    this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnSetGestureHintVisibility(false, false, false), this.displayId);
                } else {
                    NavBarStore navBarStore = this.navBarStore;
                    ButtonDispatcher recentsButton = getRecentsButton();
                    boolean z3 = recentsButton != null && recentsButton.getVisibility() == 0;
                    int i = this.mDisabledFlags;
                    navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnSetGestureHintVisibility(z3, (2097152 & i) == 0, (i & QuickStepContract.SYSUI_STATE_BACK_DISABLED) == 0), this.displayId);
                }
                NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
                navigationBarTransitions.applyDarkIntensity(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
            }
            if (z) {
                canShowHideKeyboardButtonForRotation = ((NavBarStateManagerImpl) r5).canShowHideKeyboardButtonForRotation(((NavBarStateManagerImpl) this.navBarStateManager).states.rotation);
                this.canShowHideKeyboard = canShowHideKeyboardButtonForRotation;
                if (!canShowHideKeyboardButtonForRotation) {
                    getBackButton().setVisibility(4);
                }
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
                if (!navBarStateManagerImpl.canShowKeyboardButtonForRotation(navBarStateManagerImpl.states.rotation)) {
                    this.mContextualButtonGroup.setButtonVisibility(R.id.ime_switcher, false);
                }
            }
            boolean z4 = z && this.canShowHideKeyboard;
            if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() && this.notifyHideKeyboard != z4) {
                this.notifyHideKeyboard = z4;
            }
        }
        if (BasicRune.NAVBAR_REMOTEVIEW && this.currentRemoteView != null) {
            updateRemoteViewContainerVisibility();
            ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSysUiStateFlag(false, 1, null));
        }
        if (((NavBarStoreImpl) this.navBarStore).pluginBarInteractionManager.pluginNavigationBar == null || ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            return;
        }
        boolean z5 = getBackButton().getVisibility() == 0 && getHomeButton().getVisibility() == 0;
        Iterator it2 = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(R.id.nav_custom_key_1), Integer.valueOf(R.id.nav_custom_key_2), Integer.valueOf(R.id.nav_custom_key_3), Integer.valueOf(R.id.nav_custom_key_4), Integer.valueOf(R.id.nav_custom_key_5)).iterator();
        while (it2.hasNext()) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(((Number) it2.next()).intValue());
            if (buttonDispatcher != null) {
                buttonDispatcher.setVisibility(z5 ? 0 : 4);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void updateNavigationBarColor() {
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
        ColorSetting colorSetting = (ColorSetting) navBarStateManagerImpl.interactorFactory.get(ColorSetting.class);
        int navigationBarColor = colorSetting != null ? colorSetting.getNavigationBarColor() : navBarStateManagerImpl.context.getColor(R.color.light_navbar_background_opaque);
        navBarStateManagerImpl.logNavBarStates(Integer.valueOf(navigationBarColor), "getNavigationBarColor");
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        if (navigationBarTransitions != null) {
            navigationBarTransitions.mBarBackground.updateOpaqueColor(navigationBarColor);
        }
        this.settingsHelper.setNavigationBarCurrentColor(navigationBarColor);
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
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateRemoteViewContainer(this.mCurrentRotation, linearLayout, linearLayout2, (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() || this.mContextualButtonGroup.getVisibleContextButton() == null) ? false : true, f, this.displayId));
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRemoteViewContainerVisibility() {
        /*
            r6 = this;
            com.android.systemui.navigationbar.store.NavBarStore r0 = r6.navBarStore
            int r1 = r6.displayId
            com.android.systemui.navigationbar.store.NavBarStoreImpl r0 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r0
            java.lang.Class<com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager> r2 = com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager.class
            java.lang.Object r0 = r0.getModule(r2, r1)
            com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager r0 = (com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager) r0
            if (r0 == 0) goto L8e
            android.view.View r1 = r6.currentRemoteView
            r2 = 4
            r3 = 0
            if (r1 != 0) goto L17
            goto L3f
        L17:
            android.util.SparseArray r4 = r6.mButtonDispatchers
            r5 = 2131363100(0x7f0a051c, float:1.8346E38)
            java.lang.Object r4 = r4.get(r5)
            com.android.systemui.navigationbar.buttons.ButtonDispatcher r4 = (com.android.systemui.navigationbar.buttons.ButtonDispatcher) r4
            if (r4 == 0) goto L2b
            int r4 = r4.getVisibility()
            if (r4 != 0) goto L2b
            goto L39
        L2b:
            com.android.systemui.navigationbar.store.NavBarStateManager r4 = r6.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r4 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r4
            boolean r4 = r4.isGestureMode()
            if (r4 == 0) goto L3b
            boolean r4 = r0.showInGestureMode
            if (r4 != 0) goto L3b
        L39:
            r4 = r2
            goto L3c
        L3b:
            r4 = r3
        L3c:
            r1.setVisibility(r4)
        L3f:
            int r6 = r6.displayId
            boolean r1 = r0.showInGestureMode
            if (r1 != 0) goto L46
            goto L8e
        L46:
            com.android.systemui.navigationbar.store.NavBarStore r1 = r0.navBarStore
            r4 = 0
            if (r1 == 0) goto L4c
            goto L4d
        L4c:
            r1 = r4
        L4d:
            com.android.systemui.navigationbar.store.NavBarStoreImpl r1 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r1
            com.android.systemui.navigationbar.store.NavBarStateManager r1 = r1.getNavStateManager(r6)
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r1 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r1
            com.android.systemui.navigationbar.model.NavBarStates r1 = r1.states
            boolean r1 = r1.canMove
            r5 = 1
            if (r1 == 0) goto L74
            com.android.systemui.navigationbar.store.NavBarStore r1 = r0.navBarStore
            if (r1 == 0) goto L61
            r4 = r1
        L61:
            com.android.systemui.navigationbar.store.NavBarStoreImpl r4 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r4
            com.android.systemui.navigationbar.store.NavBarStateManager r6 = r4.getNavStateManager(r6)
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r6 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r6
            com.android.systemui.navigationbar.model.NavBarStates r6 = r6.states
            int r6 = r6.rotation
            if (r6 != r5) goto L74
            int r6 = r0.adaptivePosition
            int r6 = 1 - r6
            goto L76
        L74:
            int r6 = r0.adaptivePosition
        L76:
            android.widget.LinearLayout r1 = r0.leftContainer
            if (r1 != 0) goto L7b
            goto L83
        L7b:
            if (r6 != 0) goto L7f
            r4 = r3
            goto L80
        L7f:
            r4 = r2
        L80:
            r1.setVisibility(r4)
        L83:
            android.widget.LinearLayout r0 = r0.rightContainer
            if (r0 != 0) goto L88
            goto L8e
        L88:
            if (r6 != r5) goto L8b
            r2 = r3
        L8b:
            r0.setVisibility(r2)
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.SamsungNavigationBarView.updateRemoteViewContainerVisibility():void");
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void orientBackButton(KeyButtonDrawable keyButtonDrawable) {
    }

    @Override // com.android.systemui.navigationbar.NavigationBarView
    public final void orientHomeButton(KeyButtonDrawable keyButtonDrawable) {
    }
}
