package com.android.systemui.navigationbar.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavBarTipPopup;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
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
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.MarqueeLogic;
import com.android.systemui.navigationbar.util.NavBarTipPopupUtil;
import com.android.systemui.navigationbar.views.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.views.buttons.ContextualButton;
import com.android.systemui.navigationbar.views.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawable;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.searcle.SearcleTipPopup;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.widget.SemTipPopup;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.IconType;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
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
        int displayId = context.getDisplayId();
        this.displayId = displayId;
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
        NavBarIconResourceMapper navBarIconResourceMapper = new NavBarIconResourceMapper(navBarButtonDrawableProvider, navBarStore, context);
        this.keyButtonMapper = navBarIconResourceMapper;
        this.pluginNavigationBar = new SamsungPluginNavigationBar(this, navBarStore, new ButtonDispatcherProxy(((FrameLayout) this).mContext, this.mButtonDispatchers), ((FrameLayout) this).mContext);
        this.AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        this.marqueeLogic = new MarqueeLogic();
        this.navBarTip = new NavBarTipPopup(context, (WindowManager) context.getSystemService("window"), logWrapper);
        this.mContextualButtonGroup.mKeyButtonMapper = navBarIconResourceMapper;
        ContextualButton contextualButton = new ContextualButton(R.id.ime_switcher, this.mLightContext, IconType.TYPE_IME);
        ContextualButton contextualButton2 = new ContextualButton(R.id.accessibility_button, this.mLightContext, IconType.TYPE_A11Y);
        boolean zIsGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
        ((ArrayList) this.mContextualButtonGroup.mButtonData).clear();
        if (zIsGestureMode) {
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
            if (!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || displayId == 0) {
                SearcleManager searcleManager = (SearcleManager) Dependency.sDependency.getDependencyInner(SearcleManager.class);
                this.searcleManager = searcleManager;
                if (searcleManager != null) {
                    NavBarStateManager navBarStateManager = this.navBarStateManager;
                    searcleManager.navigationBarView = this;
                    searcleManager.tipPopup.navBarStateManager = navBarStateManager;
                }
            }
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final KeyButtonDrawable getBackIconWithAlt(boolean z) {
        if (!z) {
            return this.mBackIcon;
        }
        KeyButtonDrawable keyButtonDrawable = this.backAltIcon;
        keyButtonDrawable.getClass();
        return keyButtonDrawable;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final IconThemeBase getDefaultIconTheme() {
        return this.keyButtonMapper.getDefaultIconTheme();
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final GestureHintGroup getHintGroup() {
        GestureHintGroup gestureHintGroup = this.gestureHintGroup;
        if (gestureHintGroup == null) {
            return null;
        }
        return gestureHintGroup;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final ButtonDispatcher getHintView() {
        GestureHintGroup gestureHintGroup = this.gestureHintGroup;
        if (gestureHintGroup == null) {
            gestureHintGroup = null;
        }
        int i = GestureHintGroup.$r8$clinit;
        return (ButtonDispatcher) gestureHintGroup.hintGroup.get(1);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final ExtendableBar getPluginBar() {
        return this.pluginNavigationBar;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final GestureHintDrawable getSecondaryHomeHandleDrawable(int i) {
        NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
        IconType iconType = IconType.TYPE_SECONDARY_HOME_HANDLE;
        return ((NavBarButtonDrawableProvider) navBarIconResourceMapper.buttonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(iconType), i);
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00ef A[LOOP:3: B:51:0x00ed->B:52:0x00ef, LOOP_END] */
    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void marqueeNavigationBarIcon(int i, int i2) {
        int size;
        int i3;
        if (this.mCurrentView == null) {
            reorient();
            return;
        }
        MarqueeLogic marqueeLogic = this.marqueeLogic;
        float f = getContext().getResources().getDisplayMetrics().density;
        int i4 = marqueeLogic.horizontalShift;
        if (-16 > i4 || i4 >= 17) {
            marqueeLogic.horizontalMoved = -marqueeLogic.horizontalMoved;
        }
        int i5 = marqueeLogic.verticalShift;
        if (-10 > i5 || i5 >= 11) {
            marqueeLogic.verticalMoved = -marqueeLogic.verticalMoved;
        }
        marqueeLogic.horizontalShift = i4 + marqueeLogic.horizontalMoved;
        marqueeLogic.verticalShift = i5 + marqueeLogic.verticalMoved;
        marqueeLogic.scaleFactor = f / 4.0f;
        MarqueeLogic marqueeLogic2 = this.marqueeLogic;
        int iCeil = (int) Math.ceil((this.mIsVertical ? marqueeLogic2.verticalShift : marqueeLogic2.horizontalShift) * marqueeLogic2.scaleFactor);
        MarqueeLogic marqueeLogic3 = this.marqueeLogic;
        int iCeil2 = (int) Math.ceil((this.mIsVertical ? marqueeLogic3.horizontalShift : marqueeLogic3.verticalShift) * marqueeLogic3.scaleFactor);
        boolean z = ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove;
        this.marqueeLogic.getClass();
        int iMin = (int) (Math.min(i, i2) * 0.0222d);
        int size2 = this.mButtonDispatchers.size();
        for (int i6 = 0; i6 < size2; i6++) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.valueAt(i6);
            if (z) {
                ArrayList arrayList = (ArrayList) this.mContextualButtonGroup.mButtonData;
                int size3 = arrayList.size();
                int i7 = 0;
                while (i7 < size3) {
                    Object obj = arrayList.get(i7);
                    i7++;
                    if (((ContextualButtonGroup.ButtonData) obj).button.equals(buttonDispatcher)) {
                        int i8 = this.mCurrentRotation;
                        int i9 = i8 == 1 ? iMin + iCeil2 : iCeil2;
                        int i10 = i8 == 0 ? iMin : 0;
                        int i11 = i8 == 3 ? iMin : 0;
                        ArrayList arrayList2 = buttonDispatcher.mViews;
                        int size4 = arrayList2.size();
                        int i12 = 0;
                        while (i12 < size4) {
                            Object obj2 = arrayList2.get(i12);
                            i12++;
                            ((View) obj2).setPadding(iCeil, i9, i10, i11);
                        }
                    }
                }
                ArrayList arrayList3 = buttonDispatcher.mViews;
                size = arrayList3.size();
                i3 = 0;
                while (i3 < size) {
                    Object obj3 = arrayList3.get(i3);
                    i3++;
                    ((View) obj3).setPadding(iCeil, iCeil2, 0, 0);
                }
            } else {
                ArrayList arrayList32 = buttonDispatcher.mViews;
                size = arrayList32.size();
                i3 = 0;
                while (i3 < size) {
                }
            }
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final boolean needTouchableInsetsFrame() {
        if (!((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            return true;
        }
        boolean z = this.imeVisible;
        if (z && this.canShowHideKeyboard) {
            return true;
        }
        boolean z2 = BasicRune.NAVBAR_MULTI_MODAL_ICON;
        if (z2 && z) {
            NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
            if (navBarStateManagerImpl.canShowKeyboardButtonForRotation(navBarStateManagerImpl.states.rotation)) {
                return true;
            }
            NavBarStateManagerImpl navBarStateManagerImpl2 = (NavBarStateManagerImpl) this.navBarStateManager;
            int i = navBarStateManagerImpl2.states.rotation;
            navBarStateManagerImpl2.getClass();
            if (z2) {
                if (!navBarStateManagerImpl2.isGestureMode()) {
                    return true;
                }
                if (navBarStateManagerImpl2.navBarRemoteViewManager.isSetMultimodalButton() && navBarStateManagerImpl2.canPlaceKeyboardButton(i)) {
                    return true;
                }
            }
        }
        return ((NavBarStateManagerImpl) this.navBarStateManager).shouldShowSUWStyle();
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.view.View
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarAttachedToWindow(this, this.mBarTransitions), this.displayId);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void onBackDismissImeChanged(boolean z) {
        super.onBackDismissImeChanged(z);
        this.imeVisible = z;
        if (z) {
            NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
            this.canShowHideKeyboard = navBarStateManagerImpl.canShowHideKeyboardButtonForRotation(navBarStateManagerImpl.states.rotation);
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        SearcleManager searcleManager;
        super.onConfigurationChanged(configuration);
        updateCurrentView();
        if (!BasicRune.SEARCLE || (searcleManager = this.searcleManager) == null) {
            return;
        }
        SearcleTipPopup searcleTipPopup = searcleManager.tipPopup;
        if (searcleTipPopup.isTipPopupShowing) {
            searcleTipPopup.hideImmediate();
            searcleTipPopup.showSearcleTip(true);
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarDetachedFromWindow(false, 1, null), this.displayId);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.view.View
    public final void onFinishInflate() {
        NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) findViewById(R.id.navigation_inflater);
        this.mNavigationInflaterView = navigationBarInflaterView;
        navigationBarInflaterView.setButtonDispatchers(this.mButtonDispatchers);
        updateOrientationViews();
        updateIcons(Configuration.EMPTY);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() || (motionEvent.getFlags() & this.AMOTION_EVENT_FLAG_BYPASSABLE_WINDOW_TYPE) == 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        NavBarTipPopup navBarTipPopup = this.navBarTip;
        int measuredWidth = getMeasuredWidth();
        if (navBarTipPopup.navBarWidth != measuredWidth && navBarTipPopup.tipLayout.getTag() != null) {
            navBarTipPopup.hide();
        }
        navBarTipPopup.navBarWidth = measuredWidth;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) throws Resources.NotFoundException {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        boolean z = ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove;
        int stableInsetLeft = getRootWindowInsets().getStableInsetLeft();
        int stableInsetRight = getRootWindowInsets().getStableInsetRight();
        int dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.select_dialog_drawable_padding_start_material);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_right_edge_size);
        boolean zIsGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
        if (!z || (i3 = this.mCurrentRotation) == 0 || i3 == 2) {
            if (zIsGestureMode) {
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void reorient() {
        super.reorient();
        if (BasicRune.NAVBAR_ICON_MOVEMENT) {
            this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconMarquee(false, 1, null), getContext().getDisplay().getDisplayId());
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void setDefaultIconTheme(IconThemeBase iconThemeBase) {
        NavBarIconResourceMapper navBarIconResourceMapper = this.keyButtonMapper;
        navBarIconResourceMapper.getClass();
        Log.d(navBarIconResourceMapper.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setPreloadedIconSet() null: ", iconThemeBase == null));
        navBarIconResourceMapper.preloadedIconSet = iconThemeBase;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void setIconThemeAlpha(float f) {
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        if (navigationBarTransitions != null) {
            navigationBarTransitions.mLightsOutDisabled = !(f == 1.0f);
        }
        this.mVertical.requireViewById(R.id.nav_buttons).setAlpha(f);
        this.mHorizontal.requireViewById(R.id.nav_buttons).setAlpha(f);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void showA11ySwipeUpTipPopup() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        final NavBarTipPopup navBarTipPopup = this.navBarTip;
        final boolean zSemIsScreenReaderEnabled = accessibilityManager.semIsScreenReaderEnabled();
        navBarTipPopup.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarTipPopup$showA11ySwipeUpTip$1
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                NavBarTipPopup navBarTipPopup2 = navBarTipPopup;
                int i = zSemIsScreenReaderEnabled ? R.string.gesture_accessibility_guide_gesture_onboarding_voice_assistant_on : R.string.gesture_accessibility_guide_gesture_onboarding;
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
                Context context = navBarTipPopup.context;
                navBarTipPopupUtil.getClass();
                Prefs.putInt(context, "NavigationBarAccessibilityShortcutTipCount", Prefs.getInt(context, "NavigationBarAccessibilityShortcutTipCount", 0) + 1);
            }
        });
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void showPinningEscapeToast() {
        this.mScreenPinningNotify.showEscapeToast(((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode(), getRecentsButton().getVisibility() == 0);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        this.mEdgeBackGestureHandler.mEdgeBackPlugin.updateActiveIndicatorSpringParams(f, f2);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackGestureHandler.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.updateBackGestureIcon(drawable, drawable2);
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        this.mEdgeBackGestureHandler.mEdgeBackPlugin.updateBackPanelColor(i, i2, i3, i4);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateDisabledSystemUiStateFlags(SysUiState sysUiState) {
        super.updateDisabledSystemUiStateFlags(sysUiState);
        SysUiState flag = sysUiState.setFlag(4194304L, (this.mDisabledFlags & 4194304) != 0);
        ((FrameLayout) this).mContext.getDisplayId();
        ((SysUiStateImpl) flag).commitUpdate();
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateGestureHintGroupRotation() {
        if (((NavBarStateManagerImpl) this.navBarStateManager).isBottomGestureMode(false)) {
            GestureHintGroup gestureHintGroup = this.gestureHintGroup;
            if (gestureHintGroup == null) {
                gestureHintGroup = null;
            }
            gestureHintGroup.setCurrentRotation(this.mCurrentRotation, ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove);
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateHintVisibility(boolean z, boolean z2, boolean z3) {
        int i = 0;
        int i2 = this.settingsHelper.isNavBarButtonOrderDefault() ? 0 : 2;
        int i3 = 2 - i2;
        GestureHintGroup gestureHintGroup = this.gestureHintGroup;
        if (gestureHintGroup == null) {
            gestureHintGroup = null;
        }
        ((ButtonDispatcher) gestureHintGroup.hintGroup.get(1)).setVisibility(z2 ? 0 : 4);
        ((ButtonDispatcher) gestureHintGroup.hintGroup.get(i2)).setVisibility(z ? 0 : 4);
        ((ButtonDispatcher) gestureHintGroup.hintGroup.get(i3)).setVisibility(z3 ? 0 : 4);
        ButtonDispatcher homeHandle = getHomeHandle();
        if (homeHandle != null) {
            if (!z && !z2) {
                i = 4;
            }
            homeHandle.setVisibility(i);
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateIconsAndHints() {
        updateIcons(Configuration.EMPTY);
        updateNavButtonIcons();
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateLayoutProviderView() {
        this.mNavigationInflaterView.updateLayoutProviderView();
        updateCurrentView();
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateNavButtonIcons() {
        super.updateNavButtonIcons();
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        if (BasicRune.NAVBAR_GESTURE) {
            boolean z2 = (this.mNavbarFlags & 1) != 0;
            boolean z3 = BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && ((FrameLayout) this).mContext.getDisplayId() == 1 && ((NavBarStateManagerImpl) this.navBarStateManager).canShowButtonInLargeCoverIme();
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
                ArrayList arrayList = gestureHintGroup.hintGroup;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    NavigationHintHandle navigationHintHandle = (NavigationHintHandle) ((ButtonDispatcher) obj).mCurrentView;
                    if (navigationHintHandle != null) {
                        navigationHintHandle.iconResourceMapper = navBarIconResourceMapper2;
                    }
                }
                gestureHintGroup.setCurrentRotation(this.mCurrentRotation, ((NavBarStateManagerImpl) this.navBarStateManager).states.canMove);
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && ((FrameLayout) this).mContext.getDisplayId() == 1 && z3) {
                    this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnSetGestureHintVisibility(false, !z2 && canShowNavButtonsOnLargeCover(), !z2 && canShowNavButtonsOnLargeCover()), this.displayId);
                } else {
                    NavBarStore navBarStore = this.navBarStore;
                    ButtonDispatcher recentsButton = getRecentsButton();
                    boolean z4 = recentsButton != null && recentsButton.getVisibility() == 0;
                    int i2 = this.mDisabledFlags;
                    navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnSetGestureHintVisibility(z4, (2097152 & i2) == 0, (i2 & 4194304) == 0), this.displayId);
                }
                NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
                navigationBarTransitions.applyDarkIntensity(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
            }
            if (z2) {
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
                boolean zCanShowHideKeyboardButtonForRotation = navBarStateManagerImpl.canShowHideKeyboardButtonForRotation(navBarStateManagerImpl.states.rotation);
                this.canShowHideKeyboard = zCanShowHideKeyboardButtonForRotation;
                if (!zCanShowHideKeyboardButtonForRotation) {
                    getBackButton().setVisibility(4);
                }
                NavBarStateManagerImpl navBarStateManagerImpl2 = (NavBarStateManagerImpl) this.navBarStateManager;
                if (!navBarStateManagerImpl2.canShowKeyboardButtonForRotation(navBarStateManagerImpl2.states.rotation)) {
                    this.mContextualButtonGroup.setButtonVisibility(R.id.ime_switcher, false);
                }
            }
            boolean z5 = z2 && this.canShowHideKeyboard;
            if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() && this.notifyHideKeyboard != z5) {
                this.notifyHideKeyboard = z5;
            }
        }
        if (BasicRune.NAVBAR_REMOTEVIEW && this.currentRemoteView != null) {
            boolean z6 = (this.mNavbarFlags & 1) != 0;
            NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) ((NavBarStoreImpl) this.navBarStore).getModule(NavBarRemoteViewManager.class, this.displayId);
            if (navBarRemoteViewManager != null) {
                if (navBarRemoteViewManager.useAltBack && !z6) {
                    LinearLayout linearLayout = navBarRemoteViewManager.leftContainer;
                    if (linearLayout != null) {
                        linearLayout.removeAllViews();
                    }
                    LinearLayout linearLayout2 = navBarRemoteViewManager.leftContainer;
                    if (linearLayout2 != null) {
                        linearLayout2.setVisibility(4);
                    }
                    LinearLayout linearLayout3 = navBarRemoteViewManager.rightContainer;
                    if (linearLayout3 != null) {
                        linearLayout3.removeAllViews();
                    }
                    LinearLayout linearLayout4 = navBarRemoteViewManager.rightContainer;
                    if (linearLayout4 != null) {
                        linearLayout4.setVisibility(4);
                    }
                }
                navBarRemoteViewManager.useAltBack = z6;
            }
            updateRemoteViewContainerVisibility();
            ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSysUiStateFlag(z, i, defaultConstructorMarker));
        }
        if (((NavBarStoreImpl) this.navBarStore).pluginBarInteractionManager.pluginNavigationBar == null || ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            return;
        }
        i = (getBackButton().getVisibility() == 0 && getHomeButton().getVisibility() == 0) ? 1 : 0;
        Iterator it = Arrays.asList(Integer.valueOf(R.id.nav_custom_key_1), Integer.valueOf(R.id.nav_custom_key_2), Integer.valueOf(R.id.nav_custom_key_3), Integer.valueOf(R.id.nav_custom_key_4), Integer.valueOf(R.id.nav_custom_key_5)).iterator();
        while (it.hasNext()) {
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(((Number) it.next()).intValue());
            if (buttonDispatcher != null) {
                buttonDispatcher.setVisibility(i != 0 ? 0 : 4);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
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

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateOpaqueColor(int i) {
        this.mBarTransitions.mBarBackground.updateOpaqueColor(i);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void updateRemoteViewContainer() {
        LightBarTransitionsController lightBarTransitionsController;
        View view = this.currentRemoteView;
        if (view == null) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.left_remoteview);
        View view2 = this.currentRemoteView;
        LinearLayout linearLayout2 = view2 != null ? (LinearLayout) view2.findViewById(R.id.right_remoteview) : null;
        NavigationBarTransitions navigationBarTransitions = this.mBarTransitions;
        float f = (navigationBarTransitions == null || (lightBarTransitionsController = navigationBarTransitions.mLightTransitionsController) == null) ? 0.0f : lightBarTransitionsController.mDarkIntensity;
        updateRemoteViewContainerVisibility();
        if (linearLayout == null || linearLayout2 == null) {
            return;
        }
        boolean z = false;
        if (!((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() && this.mContextualButtonGroup.getVisibleContextButton() != null) {
            z = true;
        }
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateRemoteViewContainer(this.mCurrentRotation, linearLayout, linearLayout2, z, f, this.displayId));
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRemoteViewContainerVisibility() {
        int i;
        NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) ((NavBarStoreImpl) this.navBarStore).getModule(NavBarRemoteViewManager.class, this.displayId);
        if (navBarRemoteViewManager != null) {
            View view = this.currentRemoteView;
            if (view != null) {
                ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(R.id.ime_switcher);
                view.setVisibility((((buttonDispatcher == null || buttonDispatcher.getVisibility() != 0) && (!((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode() || navBarRemoteViewManager.showInGestureMode)) || (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && ((FrameLayout) this).mContext.getDisplayId() == 1)) ? 0 : 4);
            }
            int i2 = this.displayId;
            if (navBarRemoteViewManager.showInGestureMode) {
                NavBarStoreImpl navBarStoreImpl = navBarRemoteViewManager.navBarStore;
                if (navBarStoreImpl == null) {
                    navBarStoreImpl = null;
                }
                if (((NavBarStateManagerImpl) navBarStoreImpl.getNavStateManager(i2)).states.canMove) {
                    NavBarStoreImpl navBarStoreImpl2 = navBarRemoteViewManager.navBarStore;
                    if (((NavBarStateManagerImpl) (navBarStoreImpl2 != null ? navBarStoreImpl2 : null).getNavStateManager(i2)).states.rotation == 1) {
                        i = 1 - navBarRemoteViewManager.adaptivePosition;
                    }
                } else {
                    i = navBarRemoteViewManager.adaptivePosition;
                }
                LinearLayout linearLayout = navBarRemoteViewManager.leftContainer;
                if (linearLayout != null) {
                    linearLayout.setVisibility(i == 0 ? 0 : 4);
                }
                LinearLayout linearLayout2 = navBarRemoteViewManager.rightContainer;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(i == 1 ? 0 : 4);
                }
            }
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void orientBackButton(KeyButtonDrawable keyButtonDrawable) {
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarView
    public final void orientHomeButton(KeyButtonDrawable keyButtonDrawable) {
    }
}
