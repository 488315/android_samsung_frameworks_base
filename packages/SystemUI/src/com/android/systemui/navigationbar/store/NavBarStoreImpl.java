package com.android.systemui.navigationbar.store;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactoryBase;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.plugin.PluginBarInteractionManager;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.util.NavBarReflectUtil;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.util.OneHandModeUtil;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarInflaterView;
import com.android.systemui.navigationbar.views.NavigationBarTransitions;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarSetupWizardView;
import com.android.systemui.navigationbar.views.buttons.ButtonDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.navigationbar.SamsungKeyButtonRipple;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import com.samsung.systemui.splugins.navigationbar.PluginNavigationBar;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class NavBarStoreImpl implements NavBarStore {
    public final BandAidPackFactoryBase bandAidPackFactory;
    public final DisplayManager displayManager;
    public boolean handleEventLoggingEnabled;
    public final Handler handler;
    public final GestureHintAnimator.Factory hintAnimatorFactory;
    public final InteractorFactory interactorFactory;
    public final LayoutProviderContainer layoutProviderContainer;
    public final StoreLogUtil logWrapper;
    public int loggingDepth;
    public final SamsungNavigationBarProxy navBarProxy;
    public final NavBarRemoteViewManager navBarRemoteViewManager;
    public final HashMap navDependencies = new HashMap();
    public final HashMap navStateManager = new HashMap();
    public List packs = new ArrayList();
    public final PluginBarInteractionManager pluginBarInteractionManager;
    public final SPluginManager pluginManager;
    private final SettingsHelper settingsHelper;
    public final SysUiState sysUiFlagContainer;
    public TaskbarDelegate taskbarDelegate;
    public final UserTracker userTracker;

    public NavBarStoreImpl(Context context, DisplayManager displayManager, SettingsHelper settingsHelper, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, BandAidPackFactoryBase bandAidPackFactoryBase, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, GestureHintAnimator.Factory factory, SysUiState sysUiState, SPluginManager sPluginManager, UserTracker userTracker, Handler handler) {
        this.displayManager = displayManager;
        this.settingsHelper = settingsHelper;
        this.layoutProviderContainer = layoutProviderContainer;
        this.navBarRemoteViewManager = navBarRemoteViewManager;
        this.bandAidPackFactory = bandAidPackFactoryBase;
        this.interactorFactory = interactorFactory;
        this.logWrapper = storeLogUtil;
        this.hintAnimatorFactory = factory;
        this.sysUiFlagContainer = sysUiState;
        this.pluginManager = sPluginManager;
        this.userTracker = userTracker;
        this.handler = handler;
        PluginBarInteractionManager pluginBarInteractionManager = new PluginBarInteractionManager(context, this, sPluginManager);
        this.pluginBarInteractionManager = pluginBarInteractionManager;
        SamsungNavigationBarProxy.Companion.getClass();
        SamsungNavigationBarProxy samsungNavigationBarProxy = SamsungNavigationBarProxy.INSTANCE;
        if (samsungNavigationBarProxy == null) {
            samsungNavigationBarProxy = new SamsungNavigationBarProxy();
            SamsungNavigationBarProxy.INSTANCE = samsungNavigationBarProxy;
        }
        this.navBarProxy = samsungNavigationBarProxy;
        initDisplayDependenciesIfNeeded(context.getDisplayId(), context);
        BasicRuneWrapper.NAVBAR_ENABLED = BasicRune.NAVBAR_ENABLED;
        BasicRuneWrapper.NAVBAR_GESTURE = BasicRune.NAVBAR_GESTURE;
        pluginBarInteractionManager.pluginManager.addPluginListener((SPluginListener) pluginBarInteractionManager.pluginListener, PluginNavigationBar.class, false);
    }

    public final NavBarStoreImpl apply(Band.Kit kit, NavBarStoreAction navBarStoreAction) throws Resources.NotFoundException {
        RotationButtonController rotationButtonController;
        TaskbarDelegate taskbarDelegate;
        EdgeBackGestureHandler edgeBackGestureHandler;
        NavigationBar navigationBar;
        EdgeBackGestureHandler edgeBackGestureHandler2;
        boolean z;
        boolean z2 = this.handleEventLoggingEnabled;
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (z2) {
            storeLogUtil.printLog(this.loggingDepth, BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(kit.displayId, "apply(", ") ", navBarStoreAction.getClass().getSimpleName()));
        }
        if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarIconAndHints) {
            ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).updateIconsAndHints();
            if (BasicRune.NAVBAR_DESKTOP) {
                ((NavigationBarControllerImpl) ((NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class))).onInitializedTaskbarNavigationBar();
                return this;
            }
        } else {
            if (navBarStoreAction instanceof NavBarStoreAction.ReevaluateNavBar) {
                ((LightBarControllerImpl) ((LightBarController) getModule(LightBarController.class, kit.displayId))).reevaluate();
                return this;
            }
            if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarOpaqueColor) {
                NavigationBarTransitions navigationBarTransitions = (NavigationBarTransitions) getModule(NavigationBarTransitions.class, kit.displayId);
                Object obj = this.interactorFactory.get(ColorSetting.class);
                obj.getClass();
                navigationBarTransitions.mBarBackground.updateOpaqueColor(((ColorSetting) obj).getNavigationBarColor());
                return this;
            }
            if (navBarStoreAction instanceof NavBarStoreAction.ReinflateNavBar) {
                ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).reInflateNavBarLayout();
                return this;
            }
            if (navBarStoreAction instanceof NavBarStoreAction.NavBarIconMarquee) {
                Point point = kit.states.displaySize;
                ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).marqueeNavigationBarIcon(point.x, point.y);
                return this;
            }
            if (navBarStoreAction instanceof NavBarStoreAction.InvalidateRemoteView) {
                ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).updateRemoteViewContainer();
                return this;
            }
            if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewContainer) {
                NavBarStoreAction.Action action = ((NavBarStoreAction.UpdateRemoteViewContainer) navBarStoreAction).action;
                NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, kit.displayId);
                int i = action.rotation;
                LinearLayout linearLayout = action.leftRemoteViewContainer;
                LinearLayout linearLayout2 = action.rightRemoteViewContainer;
                int i2 = kit.displayId;
                navBarRemoteViewManager.updateRemoteViewContainer(i, linearLayout, linearLayout2, i2);
                if (action.contextualButtonVisible) {
                    NavBarStoreImpl navBarStoreImpl = navBarRemoteViewManager.navBarStore;
                    if (!((NavBarStateManagerImpl) (navBarStoreImpl != null ? navBarStoreImpl : null).getNavStateManager(i2)).states.canMove) {
                        LinearLayout linearLayout3 = navBarRemoteViewManager.rightContainer;
                        if (linearLayout3 != null) {
                            linearLayout3.setVisibility(4);
                            return this;
                        }
                    } else if (action.rotation == 1) {
                        LinearLayout linearLayout4 = navBarRemoteViewManager.leftContainer;
                        if (linearLayout4 != null) {
                            linearLayout4.setVisibility(4);
                            return this;
                        }
                    } else {
                        LinearLayout linearLayout5 = navBarRemoteViewManager.rightContainer;
                        if (linearLayout5 != null) {
                            linearLayout5.setVisibility(4);
                            return this;
                        }
                    }
                }
            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewDarkIntensity) {
                NavBarRemoteViewManager navBarRemoteViewManager2 = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, kit.displayId);
                float f = ((NavBarStoreAction.UpdateRemoteViewDarkIntensity) navBarStoreAction).action.remoteViewDarkIntensity;
                if (navBarRemoteViewManager2.darkIntensity != f) {
                    navBarRemoteViewManager2.darkIntensity = f;
                    Iterator it = navBarRemoteViewManager2.leftViewList.iterator();
                    while (it.hasNext()) {
                        navBarRemoteViewManager2.applyTint(((NavBarRemoteView) it.next()).view);
                    }
                    Iterator it2 = navBarRemoteViewManager2.rightViewList.iterator();
                    while (it2.hasNext()) {
                        navBarRemoteViewManager2.applyTint(((NavBarRemoteView) it2.next()).view);
                    }
                }
            } else {
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewShortcut) {
                    ((NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, kit.displayId)).setRemoteView(((NavBarStoreAction.UpdateRemoteViewShortcut) navBarStoreAction).action.remoteViewShortcut, kit.displayId);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarNormalStyle) {
                    getSUWNavigationBarView(kit.displayId).setVisibility(8);
                    int i3 = kit.displayId;
                    ((NavigationBarInflaterView) ((NavigationBarView) getModule(NavigationBarView.class, i3)).requireViewById(R.id.navigation_inflater)).setVisibility(0);
                    NavigationBar navigationBar2 = (NavigationBar) getModule(NavigationBar.class, i3);
                    navigationBar2.updateNavBarLayoutParams();
                    navigationBar2.updateNavBarStyle(false);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarSUWStyle) {
                    ((NavigationBarInflaterView) ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).requireViewById(R.id.navigation_inflater)).setVisibility(8);
                    int i4 = kit.displayId;
                    getSUWNavigationBarView(i4).setVisibility(0);
                    NavigationBar navigationBar3 = (NavigationBar) getModule(NavigationBar.class, i4);
                    navigationBar3.updateNavBarLayoutParams();
                    navigationBar3.updateNavBarStyle(true);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDisabled) {
                    SamsungNavigationBarSetupWizardView sUWNavigationBarView = getSUWNavigationBarView(kit.displayId);
                    boolean z3 = ((NavBarStoreAction.UpdateSUWDisabled) navBarStoreAction).action.disableSUWBack;
                    z = (sUWNavigationBarView.hints & 1) != 0;
                    SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton = sUWNavigationBarView.prevBtnLayout;
                    SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton2 = navigationBarSetupWizardButton != null ? navigationBarSetupWizardButton : null;
                    if (!z3 && !z) {
                        i = 0;
                    }
                    navigationBarSetupWizardButton2.setVisibility(i);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDarkIntensity) {
                    SamsungNavigationBarSetupWizardView sUWNavigationBarView2 = getSUWNavigationBarView(kit.displayId);
                    float f2 = ((NavBarStoreAction.UpdateSUWDarkIntensity) navBarStoreAction).action.darkIntensity;
                    int iIntValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f2, Integer.valueOf(sUWNavigationBarView2.getContext().getColor(R.color.navbar_icon_color_light)), Integer.valueOf(sUWNavigationBarView2.getContext().getColor(R.color.navbar_icon_color_dark)))).intValue();
                    ImageView imageView = sUWNavigationBarView2.prevBtn;
                    if (imageView == null) {
                        imageView = null;
                    }
                    PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
                    imageView.setColorFilter(new PorterDuffColorFilter(iIntValue, mode));
                    ImageView imageView2 = sUWNavigationBarView2.imeBtn;
                    if (imageView2 == null) {
                        imageView2 = null;
                    }
                    imageView2.setColorFilter(new PorterDuffColorFilter(iIntValue, mode));
                    ImageView imageView3 = sUWNavigationBarView2.a11yBtn;
                    if (imageView3 == null) {
                        imageView3 = null;
                    }
                    imageView3.setColorFilter(new PorterDuffColorFilter(iIntValue, mode));
                    SamsungKeyButtonRipple samsungKeyButtonRipple = sUWNavigationBarView2.backRipple;
                    if (samsungKeyButtonRipple == null) {
                        samsungKeyButtonRipple = null;
                    }
                    samsungKeyButtonRipple.setDarkIntensity(f2);
                    SamsungKeyButtonRipple samsungKeyButtonRipple2 = sUWNavigationBarView2.backAltRipple;
                    if (samsungKeyButtonRipple2 == null) {
                        samsungKeyButtonRipple2 = null;
                    }
                    samsungKeyButtonRipple2.setDarkIntensity(f2);
                    SamsungKeyButtonRipple samsungKeyButtonRipple3 = sUWNavigationBarView2.a11yRipple;
                    (samsungKeyButtonRipple3 != null ? samsungKeyButtonRipple3 : null).setDarkIntensity(f2);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWIconHints) {
                    SamsungNavigationBarSetupWizardView sUWNavigationBarView3 = getSUWNavigationBarView(kit.displayId);
                    int i5 = ((NavBarStoreAction.UpdateSUWIconHints) navBarStoreAction).action.navBarIconHints;
                    sUWNavigationBarView3.hints = i5;
                    z = (i5 & 1) != 0;
                    SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton3 = sUWNavigationBarView3.imeBtnLayout;
                    if (navigationBarSetupWizardButton3 == null) {
                        navigationBarSetupWizardButton3 = null;
                    }
                    navigationBarSetupWizardButton3.setVisibility(z ? 0 : 4);
                    SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton4 = sUWNavigationBarView3.prevBtnLayout;
                    (navigationBarSetupWizardButton4 != null ? navigationBarSetupWizardButton4 : null).setVisibility(z ? 4 : 0);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWA11yIcon) {
                    SamsungNavigationBarSetupWizardView sUWNavigationBarView4 = getSUWNavigationBarView(kit.displayId);
                    NavBarStoreAction.Action action2 = ((NavBarStoreAction.UpdateSUWA11yIcon) navBarStoreAction).action;
                    boolean z4 = action2.a11yClickable;
                    SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton5 = sUWNavigationBarView4.a11yLayout;
                    if (navigationBarSetupWizardButton5 == null) {
                        navigationBarSetupWizardButton5 = null;
                    }
                    navigationBarSetupWizardButton5.setVisibility(z4 ? 0 : 4);
                    SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton6 = sUWNavigationBarView4.a11yLayout;
                    (navigationBarSetupWizardButton6 != null ? navigationBarSetupWizardButton6 : null).setLongClickable(action2.a11yLongClickable);
                    return this;
                }
                boolean z5 = navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarGoneStateFlag;
                SysUiState sysUiState = this.sysUiFlagContainer;
                if (z5) {
                    SysUiState flag = sysUiState.setFlag(1099511627776L, ((NavBarStoreAction.UpdateNavBarGoneStateFlag) navBarStoreAction).action.navBarVisibility == 8);
                    int i6 = kit.displayId;
                    ((SysUiStateImpl) flag).commitUpdate();
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.SetNavBarVisibility) {
                    final int i7 = ((NavBarStoreAction.SetNavBarVisibility) navBarStoreAction).action.navBarVisibility;
                    if (this.handleEventLoggingEnabled) {
                        storeLogUtil.printLog(this.loggingDepth, "Visibility : " + i7);
                    }
                    final NavigationBarView navigationBarView = (NavigationBarView) getModule(NavigationBarView.class, kit.displayId);
                    this.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl.apply.4
                        @Override // java.lang.Runnable
                        public final void run() {
                            NavigationBarView navigationBarView2 = navigationBarView;
                            if (navigationBarView2 != null) {
                                int i8 = i7;
                                navigationBarView2.setVisibility(i8);
                                View rootView = navigationBarView2.getRootView();
                                if (rootView != null) {
                                    rootView.setVisibility(i8);
                                }
                            }
                        }
                    });
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy) {
                    int i8 = ((NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy) navBarStoreAction).action.edgeBackGestureDisablePolicy;
                    if (this.handleEventLoggingEnabled) {
                        storeLogUtil.printLog(this.loggingDepth, "policy : " + i8);
                    }
                    int i9 = kit.displayId;
                    if (i9 == 0 && (navigationBar = (NavigationBar) getModule(NavigationBar.class, i9)) != null && (edgeBackGestureHandler2 = navigationBar.mEdgeBackGestureHandler) != null) {
                        edgeBackGestureHandler2.updateDisablePolicy(i8);
                    }
                    if (BasicRune.NAVBAR_TASKBAR && (taskbarDelegate = (TaskbarDelegate) getModule(TaskbarDelegate.class, kit.displayId)) != null && (edgeBackGestureHandler = taskbarDelegate.mEdgeBackGestureHandler) != null) {
                        edgeBackGestureHandler.updateDisablePolicy(i8);
                        return this;
                    }
                } else {
                    if (navBarStoreAction instanceof NavBarStoreAction.SetGestureHintViewGroup) {
                        NavigationBarView navigationBarView2 = (NavigationBarView) getModule(NavigationBarView.class, kit.displayId);
                        GestureHintAnimator gestureHintAnimator = (GestureHintAnimator) getModule(GestureHintAnimator.class, kit.displayId);
                        ButtonDispatcher homeHandle = navigationBarView2.getHomeHandle();
                        GestureHintGroup hintGroup = navigationBarView2.getHintGroup();
                        gestureHintAnimator.homeHandle = homeHandle;
                        gestureHintAnimator.gestureHintGroup = hintGroup;
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateGestureHintVisibility) {
                        boolean zCanShowGestureHint = ((NavBarStateManagerImpl) kit.manager).canShowGestureHint();
                        NavBarStates navBarStates = ((NavBarStateManagerImpl) kit.manager).states;
                        ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).updateHintVisibility(navBarStates.recentVisible & zCanShowGestureHint, navBarStates.homeVisible & zCanShowGestureHint, zCanShowGestureHint & navBarStates.backVisible);
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.ResetHintVI) {
                        final GestureHintAnimator gestureHintAnimator2 = (GestureHintAnimator) getModule(GestureHintAnimator.class, kit.displayId);
                        gestureHintAnimator2.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.GestureHintAnimator$reset$1
                            /* JADX WARN: Removed duplicated region for block: B:31:0x0073  */
                            /* JADX WARN: Removed duplicated region for block: B:32:0x00a2  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() {
                                int i10;
                                String str;
                                float f3;
                                int i11 = 0;
                                GestureHintAnimator gestureHintAnimator3 = gestureHintAnimator2;
                                Iterator it3 = gestureHintAnimator3.hintList.iterator();
                                while (it3.hasNext()) {
                                    int iIntValue2 = ((Number) it3.next()).intValue();
                                    View hintView = gestureHintAnimator3.getHintView(iIntValue2);
                                    if ((hintView != null ? hintView.animate() : null) != null) {
                                        hintView.setAlpha(1.0f);
                                    }
                                    ViewGroup viewGroup = (ViewGroup) gestureHintAnimator3.getHintView(iIntValue2);
                                    if ((viewGroup != null ? viewGroup.animate() : null) != null) {
                                        AnimatorSet animatorSet = gestureHintAnimator3.holdingViAnimator;
                                        if (animatorSet != null) {
                                            animatorSet.cancel();
                                            gestureHintAnimator3.holdingViAnimator = null;
                                        }
                                        int i12 = ((NavBarStateManagerImpl) gestureHintAnimator3.navBarStateManager).states.rotation;
                                        if (!gestureHintAnimator3.isCanMove) {
                                            str = "scaleX";
                                            f3 = 0.0f;
                                            if (viewGroup.getChildCount() <= 0) {
                                            }
                                            float[] fArr = new float[1];
                                            fArr[i10] = 1.0f;
                                            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewGroup, "scaleX", fArr);
                                            float[] fArr2 = new float[1];
                                            fArr2[i10] = 1.0f;
                                            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewGroup, "scaleY", fArr2);
                                            float[] fArr3 = new float[1];
                                            fArr3[i10] = f3;
                                            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(viewGroup, "translationX", fArr3);
                                            float[] fArr4 = new float[1];
                                            fArr4[i10] = 0.0f;
                                            ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(viewGroup, "translationY", fArr4);
                                            AnimatorSet animatorSet2 = new AnimatorSet();
                                            Animator[] animatorArr = new Animator[4];
                                            animatorArr[i10] = objectAnimatorOfFloat;
                                            animatorArr[1] = objectAnimatorOfFloat2;
                                            animatorArr[2] = objectAnimatorOfFloat3;
                                            animatorArr[3] = objectAnimatorOfFloat4;
                                            animatorSet2.playTogether(animatorArr);
                                            animatorSet2.setDuration(200L);
                                            animatorSet2.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                            animatorSet2.start();
                                        } else if (i12 != 1) {
                                            if (i12 == 3) {
                                                f3 = -0.0f;
                                                str = "scaleY";
                                                if (viewGroup.getChildCount() <= 0) {
                                                    View childAt = viewGroup.getChildAt(i11);
                                                    float[] fArr5 = new float[1];
                                                    fArr5[i11] = 0.0f;
                                                    ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(childAt, str, fArr5);
                                                    AnimatorSet animatorSet3 = new AnimatorSet();
                                                    Animator[] animatorArr2 = new Animator[1];
                                                    animatorArr2[i11] = objectAnimatorOfFloat5;
                                                    animatorSet3.playTogether(animatorArr2);
                                                    animatorSet3.setDuration(200L);
                                                    i10 = i11;
                                                    animatorSet3.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f));
                                                    animatorSet3.start();
                                                } else {
                                                    i10 = i11;
                                                }
                                                float[] fArr6 = new float[1];
                                                fArr6[i10] = 1.0f;
                                                ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(viewGroup, "scaleX", fArr6);
                                                float[] fArr22 = new float[1];
                                                fArr22[i10] = 1.0f;
                                                ObjectAnimator objectAnimatorOfFloat22 = ObjectAnimator.ofFloat(viewGroup, "scaleY", fArr22);
                                                float[] fArr32 = new float[1];
                                                fArr32[i10] = f3;
                                                ObjectAnimator objectAnimatorOfFloat32 = ObjectAnimator.ofFloat(viewGroup, "translationX", fArr32);
                                                float[] fArr42 = new float[1];
                                                fArr42[i10] = 0.0f;
                                                ObjectAnimator objectAnimatorOfFloat42 = ObjectAnimator.ofFloat(viewGroup, "translationY", fArr42);
                                                AnimatorSet animatorSet22 = new AnimatorSet();
                                                Animator[] animatorArr3 = new Animator[4];
                                                animatorArr3[i10] = objectAnimatorOfFloat6;
                                                animatorArr3[1] = objectAnimatorOfFloat22;
                                                animatorArr3[2] = objectAnimatorOfFloat32;
                                                animatorArr3[3] = objectAnimatorOfFloat42;
                                                animatorSet22.playTogether(animatorArr3);
                                                animatorSet22.setDuration(200L);
                                                animatorSet22.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                                animatorSet22.start();
                                            }
                                            str = "scaleX";
                                            f3 = 0.0f;
                                            if (viewGroup.getChildCount() <= 0) {
                                            }
                                            float[] fArr62 = new float[1];
                                            fArr62[i10] = 1.0f;
                                            ObjectAnimator objectAnimatorOfFloat62 = ObjectAnimator.ofFloat(viewGroup, "scaleX", fArr62);
                                            float[] fArr222 = new float[1];
                                            fArr222[i10] = 1.0f;
                                            ObjectAnimator objectAnimatorOfFloat222 = ObjectAnimator.ofFloat(viewGroup, "scaleY", fArr222);
                                            float[] fArr322 = new float[1];
                                            fArr322[i10] = f3;
                                            ObjectAnimator objectAnimatorOfFloat322 = ObjectAnimator.ofFloat(viewGroup, "translationX", fArr322);
                                            float[] fArr422 = new float[1];
                                            fArr422[i10] = 0.0f;
                                            ObjectAnimator objectAnimatorOfFloat422 = ObjectAnimator.ofFloat(viewGroup, "translationY", fArr422);
                                            AnimatorSet animatorSet222 = new AnimatorSet();
                                            Animator[] animatorArr32 = new Animator[4];
                                            animatorArr32[i10] = objectAnimatorOfFloat62;
                                            animatorArr32[1] = objectAnimatorOfFloat222;
                                            animatorArr32[2] = objectAnimatorOfFloat322;
                                            animatorArr32[3] = objectAnimatorOfFloat422;
                                            animatorSet222.playTogether(animatorArr32);
                                            animatorSet222.setDuration(200L);
                                            animatorSet222.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                            animatorSet222.start();
                                        } else {
                                            str = "scaleY";
                                            f3 = 0.0f;
                                            if (viewGroup.getChildCount() <= 0) {
                                            }
                                            float[] fArr622 = new float[1];
                                            fArr622[i10] = 1.0f;
                                            ObjectAnimator objectAnimatorOfFloat622 = ObjectAnimator.ofFloat(viewGroup, "scaleX", fArr622);
                                            float[] fArr2222 = new float[1];
                                            fArr2222[i10] = 1.0f;
                                            ObjectAnimator objectAnimatorOfFloat2222 = ObjectAnimator.ofFloat(viewGroup, "scaleY", fArr2222);
                                            float[] fArr3222 = new float[1];
                                            fArr3222[i10] = f3;
                                            ObjectAnimator objectAnimatorOfFloat3222 = ObjectAnimator.ofFloat(viewGroup, "translationX", fArr3222);
                                            float[] fArr4222 = new float[1];
                                            fArr4222[i10] = 0.0f;
                                            ObjectAnimator objectAnimatorOfFloat4222 = ObjectAnimator.ofFloat(viewGroup, "translationY", fArr4222);
                                            AnimatorSet animatorSet2222 = new AnimatorSet();
                                            Animator[] animatorArr322 = new Animator[4];
                                            animatorArr322[i10] = objectAnimatorOfFloat622;
                                            animatorArr322[1] = objectAnimatorOfFloat2222;
                                            animatorArr322[2] = objectAnimatorOfFloat3222;
                                            animatorArr322[3] = objectAnimatorOfFloat4222;
                                            animatorSet2222.playTogether(animatorArr322);
                                            animatorSet2222.setDuration(200L);
                                            animatorSet2222.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                            animatorSet2222.start();
                                        }
                                    } else {
                                        i10 = i11;
                                    }
                                    i11 = i10;
                                }
                            }
                        });
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.StartHintVI) {
                        NavBarStoreAction.GestureHintVIInfo gestureHintVIInfo = ((NavBarStoreAction.StartHintVI) navBarStoreAction).action.gestureHintVIInfo;
                        GestureHintAnimator gestureHintAnimator3 = (GestureHintAnimator) getModule(GestureHintAnimator.class, kit.displayId);
                        int i10 = gestureHintVIInfo.hintID;
                        boolean z6 = kit.states.canMove;
                        gestureHintAnimator3.currentHintId = i10;
                        gestureHintAnimator3.isCanMove = z6;
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.MoveHintVI) {
                        NavBarStoreAction.GestureHintVIInfo gestureHintVIInfo2 = ((NavBarStoreAction.MoveHintVI) navBarStoreAction).action.gestureHintVIInfo;
                        final GestureHintAnimator gestureHintAnimator4 = (GestureHintAnimator) getModule(GestureHintAnimator.class, kit.displayId);
                        gestureHintAnimator4.currentHintId = gestureHintVIInfo2.hintID;
                        Handler handler = gestureHintAnimator4.handler;
                        final int i11 = gestureHintVIInfo2.distanceY;
                        final long j = gestureHintVIInfo2.duration;
                        final int i12 = gestureHintVIInfo2.distanceX;
                        handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.GestureHintAnimator$onActionMove$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                String str;
                                GestureHintAnimator gestureHintAnimator5 = gestureHintAnimator4;
                                ViewGroup viewGroup = (ViewGroup) gestureHintAnimator5.getHintView(gestureHintAnimator5.currentHintId);
                                if (viewGroup != null) {
                                    GestureHintAnimator gestureHintAnimator6 = gestureHintAnimator4;
                                    float fDipToPixel = gestureHintAnimator6.dipToPixel(NavigationModeUtil.isBottomGesture(gestureHintAnimator6.navigationMode) ? 210.0f : 105.0f);
                                    GestureHintAnimator gestureHintAnimator7 = gestureHintAnimator4;
                                    float fDipToPixel2 = gestureHintAnimator7.dipToPixel(NavigationModeUtil.isBottomGesture(gestureHintAnimator7.navigationMode) ? 17.0f : 8.5f);
                                    GestureHintAnimator gestureHintAnimator8 = gestureHintAnimator4;
                                    int i13 = i12;
                                    int i14 = i11;
                                    int i15 = ((NavBarStateManagerImpl) gestureHintAnimator8.navBarStateManager).states.rotation;
                                    int iAbs = (!gestureHintAnimator8.isCanMove || i15 == 0 || i15 == 2) ? Math.abs(i14) : Math.abs(i13);
                                    float f3 = iAbs;
                                    float f4 = (fDipToPixel2 * f3) / fDipToPixel;
                                    float fMin = iAbs > 0 ? Math.min(f4, fDipToPixel2) : Math.max(f4, -fDipToPixel2);
                                    float f5 = NavigationModeUtil.isBottomGesture(gestureHintAnimator4.navigationMode) ? 1.16f : 1.1f;
                                    float fMin2 = Math.min((((f5 - 1.0f) * f3) / fDipToPixel) + 1.0f, f5);
                                    GestureHintAnimator gestureHintAnimator9 = gestureHintAnimator4;
                                    int i16 = ((NavBarStateManagerImpl) gestureHintAnimator9.navBarStateManager).states.rotation;
                                    if (!gestureHintAnimator9.isCanMove || i16 == 0 || i16 == 2) {
                                        viewGroup.setTranslationY(-fMin);
                                        viewGroup.setScaleX(fMin2);
                                        str = "scaleX";
                                    } else {
                                        if (i16 == 3) {
                                            viewGroup.setTranslationX(fMin);
                                            viewGroup.setScaleY(fMin2);
                                        } else if (i16 == 1) {
                                            viewGroup.setTranslationX(-fMin);
                                            viewGroup.setScaleY(fMin2);
                                        }
                                        str = "scaleY";
                                    }
                                    if (gestureHintAnimator4.currentHintId != 1 || viewGroup.getChildCount() <= 0) {
                                        return;
                                    }
                                    View childAt = viewGroup.getChildAt(0);
                                    if (j == 0) {
                                        AnimatorSet animatorSet = gestureHintAnimator4.holdingViAnimator;
                                        if (animatorSet != null) {
                                            animatorSet.getClass();
                                            animatorSet.cancel();
                                            gestureHintAnimator4.holdingViAnimator = null;
                                            return;
                                        }
                                        return;
                                    }
                                    if (gestureHintAnimator4.holdingViAnimator == null) {
                                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(childAt, str, 1.0f);
                                        gestureHintAnimator4.holdingViAnimator = new AnimatorSet();
                                        AnimatorSet animatorSet2 = gestureHintAnimator4.holdingViAnimator;
                                        animatorSet2.getClass();
                                        animatorSet2.playTogether(objectAnimatorOfFloat);
                                        AnimatorSet animatorSet3 = gestureHintAnimator4.holdingViAnimator;
                                        animatorSet3.getClass();
                                        animatorSet3.setDuration(500L);
                                        AnimatorSet animatorSet4 = gestureHintAnimator4.holdingViAnimator;
                                        animatorSet4.getClass();
                                        animatorSet4.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f));
                                        AnimatorSet animatorSet5 = gestureHintAnimator4.holdingViAnimator;
                                        animatorSet5.getClass();
                                        animatorSet5.start();
                                        if (str.equals("scaleY")) {
                                            childAt.setScaleX(1.0f);
                                        } else {
                                            childAt.setScaleY(1.0f);
                                        }
                                    }
                                }
                            }
                        });
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateSysUiFlags) {
                        for (NavBarStoreAction.SysUiFlagInfo sysUiFlagInfo : ((NavBarStoreAction.UpdateSysUiFlags) navBarStoreAction).action.sysUiFlagInfoList) {
                            if (this.handleEventLoggingEnabled) {
                                int i13 = this.loggingDepth;
                                StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("set ", sysUiFlagInfo.flag, " : ");
                                sbM.append(sysUiFlagInfo.value);
                                storeLogUtil.printLog(i13, sbM.toString());
                            }
                            sysUiState.setFlag(sysUiFlagInfo.flag, sysUiFlagInfo.value);
                        }
                        int i14 = kit.displayId;
                        ((SysUiStateImpl) sysUiState).commitUpdate();
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateOneHandModeInfo) {
                        OneHandModeUtil.Companion companion = OneHandModeUtil.Companion;
                        NavBarStoreAction.OneHandModeInfo oneHandModeInfo = ((NavBarStoreAction.UpdateOneHandModeInfo) navBarStoreAction).action.oneHandModeInfo;
                        companion.getClass();
                        OneHandModeUtil.oneHandModeInfo = oneHandModeInfo;
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateRegionSamplingRect) {
                        ((NavigationBar) getModule(NavigationBar.class, kit.displayId)).mRegionSamplingHelper.updateSamplingRect();
                        return this;
                    }
                    if (navBarStoreAction instanceof NavBarStoreAction.RecalculateGestureInsetScale) {
                        NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                        SettingsHelper settingsHelper = this.settingsHelper;
                        Context context = (Context) getModule(Context.class, kit.displayId);
                        boolean z7 = ((NavBarStoreAction.RecalculateGestureInsetScale) navBarStoreAction).action.folded;
                        navigationModeUtil.getClass();
                        try {
                            if (NavigationModeUtil.sideInsetScaleArray.length == 0) {
                                TypedArray typedArrayObtainTypedArray = context.getResources().obtainTypedArray(android.R.array.config_priorityOnlyDndExemptPackages);
                                int length = typedArrayObtainTypedArray.length();
                                float[] fArr = new float[length];
                                for (int i15 = 0; i15 < length; i15++) {
                                    fArr[i15] = typedArrayObtainTypedArray.getFloat(i15, 1.0f);
                                }
                                typedArrayObtainTypedArray.recycle();
                                NavigationModeUtil.sideInsetScaleArray = fArr;
                            }
                            if (NavigationModeUtil.bottomInsetScaleArray.length == 0) {
                                TypedArray typedArrayObtainTypedArray2 = context.getResources().obtainTypedArray(android.R.array.config_roundedCornerRadiusArray);
                                int length2 = typedArrayObtainTypedArray2.length();
                                float[] fArr2 = new float[length2];
                                for (int i16 = 0; i16 < length2; i16++) {
                                    fArr2[i16] = typedArrayObtainTypedArray2.getFloat(i16, 1.0f);
                                }
                                typedArrayObtainTypedArray2.recycle();
                                NavigationModeUtil.bottomInsetScaleArray = fArr2;
                            }
                            int navigationBarBackGestureSentivitySub = z7 ? settingsHelper.getNavigationBarBackGestureSentivitySub() : settingsHelper.getNavigationBarBackGestureSentivity();
                            Settings.Secure.putFloat(context.getContentResolver(), "back_gesture_inset_scale_left", NavigationModeUtil.sideInsetScaleArray[navigationBarBackGestureSentivitySub]);
                            Settings.Secure.putFloat(context.getContentResolver(), "back_gesture_inset_scale_right", NavigationModeUtil.sideInsetScaleArray[navigationBarBackGestureSentivitySub]);
                            Settings.Secure.putFloat(context.getContentResolver(), "bottom_gesture_inset_scale", NavigationModeUtil.bottomInsetScaleArray[navigationBarBackGestureSentivitySub]);
                            return this;
                        } catch (Exception unused) {
                        }
                    } else {
                        if (navBarStoreAction instanceof NavBarStoreAction.ShowA11ySwipeUpTipPopup) {
                            ((NavigationBarView) getModule(NavigationBarView.class, 0)).showA11ySwipeUpTipPopup();
                            return this;
                        }
                        if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavigationIcon) {
                            ((LightBarControllerImpl) ((LightBarController) getModule(LightBarController.class, kit.displayId))).updateNavigation();
                            return this;
                        }
                        if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskBarIconsAndHints) {
                            TaskbarDelegate taskbarDelegate2 = (TaskbarDelegate) getModule(TaskbarDelegate.class, 0);
                            if (taskbarDelegate2 != null) {
                                taskbarDelegate2.updateTaskbarButtonIconsAndHints();
                                if (BasicRune.NAVBAR_DESKTOP && !taskbarDelegate2.mInitialized) {
                                    ((NavigationBarControllerImpl) ((NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class))).onInitializedTaskbarNavigationBar();
                                    return this;
                                }
                            }
                        } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskBarNavBarEvents) {
                            TaskbarDelegate taskbarDelegate3 = (TaskbarDelegate) getModule(TaskbarDelegate.class, 0);
                            if (taskbarDelegate3 != null) {
                                taskbarDelegate3.sendNavbarEvent(((NavBarStoreAction.UpdateTaskBarNavBarEvents) navBarStoreAction).action.taskbarNavBarEvents);
                                return this;
                            }
                        } else {
                            if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarLayoutParams) {
                                ((NavigationBar) getModule(NavigationBar.class, kit.displayId)).updateNavBarLayoutParams();
                                return this;
                            }
                            if (navBarStoreAction instanceof NavBarStoreAction.UpdateA11YStatus) {
                                NavigationBar navigationBar4 = (NavigationBar) getModule(NavigationBar.class, kit.displayId);
                                if (navigationBar4 != null) {
                                    navigationBar4.mNavBarHelper.updateA11yState();
                                }
                                TaskbarDelegate taskbarDelegate4 = (TaskbarDelegate) getModule(TaskbarDelegate.class, kit.displayId);
                                if (taskbarDelegate4 != null) {
                                    taskbarDelegate4.mNavBarHelper.updateA11yState();
                                    return this;
                                }
                            } else {
                                if (navBarStoreAction instanceof NavBarStoreAction.ForceHideGestureHint) {
                                    kit.manager.getClass();
                                    ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).updateHintVisibility(false, false, false);
                                    return this;
                                }
                                if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskbarStatus) {
                                    NavBarStoreAction.UpdateTaskbarStatus updateTaskbarStatus = (NavBarStoreAction.UpdateTaskbarStatus) navBarStoreAction;
                                    boolean z8 = updateTaskbarStatus.action.taskbarEnabled;
                                    PluginBarInteractionManager pluginBarInteractionManager = this.pluginBarInteractionManager;
                                    if (z8) {
                                        TaskbarDelegate taskbarDelegate5 = this.taskbarDelegate;
                                        SamsungPluginTaskBar samsungPluginTaskBar = taskbarDelegate5 != null ? taskbarDelegate5.mPluginTaskbar : null;
                                        samsungPluginTaskBar.getClass();
                                        PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
                                        if (pluginNavigationBar != null) {
                                            pluginNavigationBar.onAttachedToWindow(samsungPluginTaskBar);
                                        }
                                    } else {
                                        TaskbarDelegate taskbarDelegate6 = this.taskbarDelegate;
                                        SamsungPluginTaskBar samsungPluginTaskBar2 = taskbarDelegate6 != null ? taskbarDelegate6.mPluginTaskbar : null;
                                        samsungPluginTaskBar2.getClass();
                                        PluginNavigationBar pluginNavigationBar2 = pluginBarInteractionManager.pluginNavigationBar;
                                        if (pluginNavigationBar2 != null) {
                                            pluginNavigationBar2.onDetachedFromWindow(samsungPluginTaskBar2);
                                        }
                                    }
                                    NavigationBar navigationBar5 = (NavigationBar) getModule(NavigationBar.class, kit.displayId);
                                    if (navigationBar5 != null && BasicRune.NAVBAR_POLICY_VISIBILITY && ((NavBarStateManagerImpl) getNavStateManager()).isGestureMode()) {
                                        navigationBar5.getView().reorient();
                                        boolean z9 = updateTaskbarStatus.action.taskbarEnabled;
                                        RegionSamplingHelper regionSamplingHelper = navigationBar5.mRegionSamplingHelper;
                                        if (z9) {
                                            regionSamplingHelper.stop();
                                            return this;
                                        }
                                        regionSamplingHelper.start(navigationBar5.mSamplingBounds);
                                        return this;
                                    }
                                } else {
                                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateIndicatorSpringParams) {
                                        NavBarStoreAction.Action action3 = ((NavBarStoreAction.UpdateIndicatorSpringParams) navBarStoreAction).action;
                                        boolean zIsTaskBarEnabled = ((NavBarStateManagerImpl) kit.manager).isTaskBarEnabled(false);
                                        int i17 = kit.displayId;
                                        if (zIsTaskBarEnabled) {
                                            ((TaskbarDelegate) getModule(TaskbarDelegate.class, i17)).mEdgeBackGestureHandler.mEdgeBackPlugin.updateActiveIndicatorSpringParams(action3.stiffness, action3.dampingRatio);
                                            return this;
                                        }
                                        ((NavigationBarView) getModule(NavigationBarView.class, i17)).updateActiveIndicatorSpringParams(action3.stiffness, action3.dampingRatio);
                                        return this;
                                    }
                                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateDefaultNavigationBarStatus) {
                                        NavigationBar navigationBar6 = (NavigationBar) getModule(NavigationBar.class, 0);
                                        if (navigationBar6 != null) {
                                            navigationBar6.updateSystemUiStateFlags();
                                        }
                                        NavigationBarView navigationBarView3 = (NavigationBarView) getModule(NavigationBarView.class, 0);
                                        if (navigationBarView3 != null && (rotationButtonController = navigationBarView3.mRotationButtonController) != null) {
                                            rotationButtonController.mLastUnknownRotationProposedTick = 0L;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    public final void endLogging(int i, boolean z, Object obj) {
        boolean z2 = this.handleEventLoggingEnabled;
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (z2) {
            storeLogUtil.printLog(this.loggingDepth, "handleEvent(" + i + ") value= " + obj);
        }
        storeLogUtil.allowLogging = false;
        storeLogUtil.lastDepth = 0;
        this.handleEventLoggingEnabled = z;
    }

    public final Object getModule(Class cls, int i) {
        NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) this.navDependencies.get(Integer.valueOf(i));
        if (navBarModuleDependency != null) {
            return navBarModuleDependency.modules.get(cls.getTypeName());
        }
        return null;
    }

    public final NavBarStateManager getNavStateManager(int i) {
        if (this.navStateManager.get(Integer.valueOf(i)) != null) {
            Object obj = this.navStateManager.get(Integer.valueOf(i));
            obj.getClass();
            return (NavBarStateManager) obj;
        }
        Object obj2 = this.navStateManager.get(0);
        obj2.getClass();
        return (NavBarStateManager) obj2;
    }

    public final Object getProvider(int i, int i2) {
        if (i == 0) {
            return this.interactorFactory.get(ColorSetting.class);
        }
        if (i != 1) {
            return null;
        }
        return ((NavBarStateManagerImpl) getNavStateManager(i2)).layoutProviderContainer;
    }

    public final SamsungNavigationBarSetupWizardView getSUWNavigationBarView(int i) {
        return (SamsungNavigationBarSetupWizardView) ((NavigationBarView) getModule(NavigationBarView.class, i)).requireViewById(R.id.navigation_setupwizard);
    }

    public final void handleEvent(Object obj, EventTypeFactory.EventType eventType) {
        int i = 0;
        if (!(eventType instanceof EventTypeFactory.EventType.ResetBottomGestureHintVI) && !(eventType instanceof EventTypeFactory.EventType.MoveBottomGestureHintDistance) && !(eventType instanceof EventTypeFactory.EventType.StartBottomGestureHintVI) && !(eventType instanceof EventTypeFactory.EventType.OnSetRemoteView) && !(eventType instanceof EventTypeFactory.EventType.OnUpdateRemoteViewContainer) && !(eventType instanceof EventTypeFactory.EventType.OnInvalidateRemoteViews)) {
            handleEvent(obj, eventType, 0);
            return;
        }
        Set setKeySet = this.navDependencies.keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : setKeySet) {
            Integer num = (Integer) obj2;
            if ((num != null && num.intValue() == 0) || (num != null && num.intValue() == 1)) {
                arrayList.add(obj2);
            }
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj3 = arrayList.get(i);
            i++;
            Integer num2 = (Integer) obj3;
            if (((NavBarModuleDependency) this.navDependencies.get(num2)) != null) {
                num2.getClass();
                handleEvent(obj, eventType, num2.intValue());
            }
        }
    }

    public final void initDisplayDependenciesIfNeeded(int i, Context context) {
        Context context2;
        NavBarStateManager navBarStateManager;
        Unit unit;
        Display display = this.displayManager.getDisplay(i);
        if (display != null) {
            if (this.navDependencies.get(Integer.valueOf(i)) == null) {
                this.navDependencies.put(Integer.valueOf(i), new NavBarModuleDependency());
                context2 = context;
                putModule(Context.class, context2, i);
            } else {
                context2 = context;
            }
            if (this.navStateManager.get(Integer.valueOf(i)) == null) {
                this.navStateManager.put(Integer.valueOf(i), new NavBarStateManagerImpl(context2, this, this.settingsHelper, this.interactorFactory, this.logWrapper, this.layoutProviderContainer, this.navBarRemoteViewManager, this.userTracker, null, 256, null));
            } else if (i == 0 && (navBarStateManager = (NavBarStateManager) this.navStateManager.get(Integer.valueOf(i))) != null) {
                ((NavBarStateManagerImpl) navBarStateManager).onNavigationBarCreated();
            }
            if (BasicRune.NAVBAR_REMOTEVIEW) {
                NavBarRemoteViewManager navBarRemoteViewManager = this.navBarRemoteViewManager;
                navBarRemoteViewManager.navBarStore = this;
                putModule(NavBarRemoteViewManager.class, navBarRemoteViewManager, i);
            }
            Point point = new Point();
            display.getRealSize(point);
            NavBarStateManager navBarStateManager2 = (NavBarStateManager) this.navStateManager.get(Integer.valueOf(i));
            if (navBarStateManager2 != null) {
                ((NavBarStateManagerImpl) navBarStateManager2).states.displaySize = point;
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit != null) {
                return;
            }
        }
        String strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Failed to add display dependencies because display ", " returns null.");
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (storeLogUtil.allowLogging) {
            storeLogUtil.printLog(storeLogUtil.lastDepth, strM);
        }
    }

    public final void putModule(Type type, Object obj, int i) {
        if (obj == null) {
            NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) this.navDependencies.get(Integer.valueOf(i));
            if (navBarModuleDependency != null) {
                navBarModuleDependency.modules.remove(((Class) type).getTypeName());
                return;
            }
            return;
        }
        NavBarModuleDependency navBarModuleDependency2 = (NavBarModuleDependency) this.navDependencies.get(Integer.valueOf(i));
        if (navBarModuleDependency2 != null) {
            navBarModuleDependency2.modules.put(((Class) type).getTypeName(), obj);
        }
    }

    public final void setProvider(int i, int i2, Object obj) {
        if (i != 0) {
            if (i == 1) {
                handleEvent(this, new EventTypeFactory.EventType.OnLayoutContainerChanged((LayoutProviderContainer) obj), i2);
                return;
            } else {
                if (i != 2) {
                    return;
                }
                handleEvent(this, new EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged((BarLayoutParams) obj), i2);
                return;
            }
        }
        if (obj != null) {
            InteractorFactory interactorFactory = this.interactorFactory;
            interactorFactory.provider.put(ColorSetting.class, obj);
            ColorSetting colorSetting = (ColorSetting) interactorFactory.get(ColorSetting.class);
            if (colorSetting != null) {
                colorSetting.addColorCallback(null);
            }
        }
    }

    public final Object handleEvent(final Object obj, final EventTypeFactory.EventType eventType, final int i, Object obj2) {
        boolean z;
        boolean z2 = true;
        boolean z3 = false;
        String str = null;
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarCreated) {
            EventTypeFactory.EventType.OnNavBarCreated onNavBarCreated = (EventTypeFactory.EventType.OnNavBarCreated) eventType;
            putModule(KeyguardStateController.class, onNavBarCreated.keyguardStateController, i);
            putModule(NavigationBar.class, onNavBarCreated.navigationBar, i);
        } else {
            boolean z4 = eventType instanceof EventTypeFactory.EventType.OnNavBarConfigChanged;
            SamsungNavigationBarProxy samsungNavigationBarProxy = this.navBarProxy;
            if (z4) {
                EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) eventType;
                onNavBarConfigChanged.canMove &= !DeviceType.isTablet();
                onNavBarConfigChanged.supportPhoneLayoutProvider &= !DeviceType.isTablet();
                samsungNavigationBarProxy.getClass();
            } else {
                boolean z5 = eventType instanceof EventTypeFactory.EventType.OnNavBarAttachedToWindow;
                PluginBarInteractionManager pluginBarInteractionManager = this.pluginBarInteractionManager;
                if (z5) {
                    EventTypeFactory.EventType.OnNavBarAttachedToWindow onNavBarAttachedToWindow = (EventTypeFactory.EventType.OnNavBarAttachedToWindow) eventType;
                    NavigationBarView navigationBarView = onNavBarAttachedToWindow.navigationBarView;
                    putModule(NavigationBarView.class, navigationBarView, i);
                    putModule(NavigationBarTransitions.class, onNavBarAttachedToWindow.navbarTransitions, i);
                    Context context = (Context) getModule(Context.class, 0);
                    GestureHintAnimator.Factory factory = this.hintAnimatorFactory;
                    factory.getClass();
                    putModule(GestureHintAnimator.class, new GestureHintAnimator(context, factory.mLogWrapper), i);
                    GestureHintAnimator gestureHintAnimator = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                    gestureHintAnimator.navigationMode = gestureHintAnimator.navigationModeController.addListener(gestureHintAnimator);
                    ExtendableBar pluginBar = navigationBarView.getPluginBar();
                    PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
                    if (pluginNavigationBar != null) {
                        pluginNavigationBar.onAttachedToWindow(pluginBar);
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnNavBarDetachedFromWindow) {
                    ExtendableBar pluginBar2 = ((NavigationBarView) getModule(NavigationBarView.class, i)).getPluginBar();
                    PluginNavigationBar pluginNavigationBar2 = pluginBarInteractionManager.pluginNavigationBar;
                    if (pluginNavigationBar2 != null) {
                        pluginNavigationBar2.onDetachedFromWindow(pluginBar2);
                    }
                    putModule(NavigationBarView.class, null, i);
                    putModule(NavigationBarTransitions.class, null, i);
                    GestureHintAnimator gestureHintAnimator2 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                    gestureHintAnimator2.navigationModeController.removeListener(gestureHintAnimator2);
                    putModule(GestureHintAnimator.class, null, i);
                    if (DeviceType.isTablet() && !((NavBarStateManagerImpl) getNavStateManager()).isNavBarHiddenByKnox()) {
                        ((SysUiStateImpl) this.sysUiFlagContainer.setFlag(1099511627776L, false)).commitUpdate();
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnLightBarControllerCreated) {
                    putModule(LightBarController.class, ((EventTypeFactory.EventType.OnLightBarControllerCreated) eventType).lightBarController, i);
                    NavigationBarTransitions navigationBarTransitions = (NavigationBarTransitions) getModule(NavigationBarTransitions.class, i);
                    Object obj3 = this.interactorFactory.get(ColorSetting.class);
                    obj3.getClass();
                    navigationBarTransitions.mBarBackground.updateOpaqueColor(((ColorSetting) obj3).getNavigationBarColor());
                } else if (eventType instanceof EventTypeFactory.EventType.OnRotationLockedChanged) {
                    boolean z6 = ((EventTypeFactory.EventType.OnRotationLockedChanged) eventType).rotationLocked;
                    samsungNavigationBarProxy.rotationLocked = z6;
                    ArrayList arrayList = (ArrayList) samsungNavigationBarProxy.rotationLockCallback;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj4 = arrayList.get(i2);
                        i2++;
                        ((Consumer) obj4).accept(Boolean.valueOf(z6));
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnNavBarTransitionModeChanged) {
                    samsungNavigationBarProxy.getClass();
                }
            }
        }
        Stream stream = this.packs.stream();
        final NavBarStoreImpl$$ExternalSyntheticLambda0 navBarStoreImpl$$ExternalSyntheticLambda0 = new NavBarStoreImpl$$ExternalSyntheticLambda0();
        Stream streamFlatMap = stream.flatMap(new Function() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj5) {
                return navBarStoreImpl$$ExternalSyntheticLambda0.mo781invoke(obj5);
            }
        });
        final Function1 function1 = new Function1() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$$ExternalSyntheticLambda1
            /* JADX WARN: Removed duplicated region for block: B:60:0x0124  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj5) {
                Object next;
                Object next2;
                Band band = (Band) obj5;
                NavBarStoreImpl navBarStoreImpl = this.f$0;
                navBarStoreImpl.getClass();
                BandAid bandAid = band.bandAidDependency;
                Object obj6 = null;
                Boolean boolValueOf = bandAid != null ? Boolean.valueOf(bandAid.getEnabled()) : null;
                boolValueOf.getClass();
                boolean z7 = false;
                if (boolValueOf.booleanValue() && band.runeDependency) {
                    String str2 = band.sPluginTag;
                    int length = str2.length();
                    EventTypeFactory.EventType eventType2 = eventType;
                    int i3 = i;
                    int i4 = band.targetDisplayId;
                    if (length > 0) {
                        if (StringsKt__StringsKt.contains(eventType2.getClass().getTypeName(), str2, false) && i4 == i3) {
                            z7 = true;
                        }
                    } else if (i4 == -1 || i4 == i3) {
                        Iterator it = band.targetEvents.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            if (Intrinsics.areEqual(((Type) next).getTypeName(), eventType2.getClass().getTypeName())) {
                                break;
                            }
                        }
                        if (((Type) next) != null) {
                            Iterator it2 = band.targetModules.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    next2 = null;
                                    break;
                                }
                                next2 = it2.next();
                                Type type = (Type) next2;
                                if (StringsKt__StringsKt.contains(type.toString(), NavBarReflectUtil.class.getTypeName(), false)) {
                                    break;
                                }
                                Object obj7 = obj;
                                if (Intrinsics.areEqual(obj7.getClass().getTypeName(), type.getTypeName()) || StringsKt__StringsKt.contains(obj7.getClass().getTypeName(), type.getTypeName(), false) || (obj7.getClass().getEnclosingClass() != null && Intrinsics.areEqual(obj7.getClass().getEnclosingClass().getTypeName(), type.getTypeName()))) {
                                    break;
                                }
                            }
                            if (((Type) next2) != null) {
                                Iterator it3 = band.moduleDependencies.iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        break;
                                    }
                                    Object next3 = it3.next();
                                    Type type2 = (Type) next3;
                                    NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) navBarStoreImpl.navDependencies.get(Integer.valueOf(i3));
                                    if ((navBarModuleDependency != null ? navBarModuleDependency.modules.get(type2.getTypeName()) : null) == null) {
                                        obj6 = next3;
                                        break;
                                    }
                                }
                                if (((Type) obj6) == null) {
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z7);
            }
        };
        Stream streamFilter = streamFlatMap.filter(new Predicate() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj5) {
                return ((Boolean) function1.mo781invoke(obj5)).booleanValue();
            }
        });
        final NavBarStoreImpl$$ExternalSyntheticLambda2 navBarStoreImpl$$ExternalSyntheticLambda2 = new NavBarStoreImpl$$ExternalSyntheticLambda2();
        List<Band> list = (List) streamFilter.sorted(new Comparator() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$sam$java_util_Comparator$0
            @Override // java.util.Comparator
            public final /* synthetic */ int compare(Object obj5, Object obj6) {
                return ((Number) navBarStoreImpl$$ExternalSyntheticLambda2.invoke(obj5, obj6)).intValue();
            }
        }).collect(Collectors.toList());
        boolean z7 = this.handleEventLoggingEnabled;
        int i3 = this.loggingDepth;
        StoreLogUtil storeLogUtil = this.logWrapper;
        storeLogUtil.lastDepth = i3;
        boolean z8 = ((eventType instanceof EventTypeFactory.EventType.OnInvalidateRemoteViews) || (eventType instanceof EventTypeFactory.EventType.OnSetRemoteView) || (eventType instanceof EventTypeFactory.EventType.OnUpdateRemoteViewContainer) || (eventType instanceof EventTypeFactory.EventType.OnSetDisableFlags) || (eventType instanceof EventTypeFactory.EventType.GetBarLayoutParams) || (eventType instanceof EventTypeFactory.EventType.OnNavBarIconMarquee) || (eventType instanceof EventTypeFactory.EventType.OnUpdateDarkIntensity) || (eventType instanceof EventTypeFactory.EventType.GetDeadZoneSize) || (eventType instanceof EventTypeFactory.EventType.OnUpdateRegionSamplingListener) || (eventType instanceof EventTypeFactory.EventType.GetNavBarInsets) || (eventType instanceof EventTypeFactory.EventType.GetImeInsets) || (eventType instanceof EventTypeFactory.EventType.GetMandatoryInsets) || (eventType instanceof EventTypeFactory.EventType.MoveBottomGestureHintDistance)) ? false : true;
        storeLogUtil.allowLogging = z8;
        if (z8) {
            StringBuilder sb = new StringBuilder();
            for (int i4 = 0; i4 < i3; i4++) {
                sb.append("--");
            }
            sb.append("handleEvent(" + i + ") ");
            sb.append(eventType.toString());
            sb.append(" [Module] ");
            sb.append(obj.getClass().getSimpleName());
            storeLogUtil.logWrapper.d("Store", sb.toString());
            z3 = true;
        }
        this.handleEventLoggingEnabled = z3;
        this.loggingDepth++;
        NavBarStateManager navStateManager = getNavStateManager(i);
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navStateManager;
        Band.Kit kit = new Band.Kit(eventType, navStateManager, NavBarStates.copy$default(navBarStateManagerImpl.states), i);
        Object obj5 = null;
        for (Band band : list) {
            if (this.handleEventLoggingEnabled) {
                int i5 = this.loggingDepth;
                BandAid bandAid = band.bandAidDependency;
                z = z2;
                storeLogUtil.printLog(i5, "[Band]" + (bandAid != null ? bandAid.name() : str));
            } else {
                z = z2;
            }
            this.loggingDepth++;
            Function function = band.patchAction;
            Object objApply = function != null ? function.apply(kit) : null;
            if (!(objApply instanceof Unit)) {
                obj5 = objApply == null ? null : objApply;
            }
            this.loggingDepth--;
            z2 = z;
            str = null;
        }
        navBarStateManagerImpl.updateStateFromEvent(eventType);
        for (Band band2 : list) {
            if (band2.afterAction != null) {
                if (this.handleEventLoggingEnabled) {
                    int i6 = this.loggingDepth;
                    BandAid bandAid2 = band2.bandAidDependency;
                    storeLogUtil.printLog(i6, "[Band] (afterAction) " + (bandAid2 != null ? bandAid2.name() : null));
                }
                this.loggingDepth++;
                band2.afterAction.accept(kit);
                this.loggingDepth--;
            }
        }
        this.loggingDepth--;
        if (obj5 != null && !(obj5 instanceof NavBarStore)) {
            endLogging(i, z7, obj5);
            return obj5;
        }
        if (!(obj2 instanceof Unit)) {
            endLogging(i, z7, obj2);
        }
        return obj2;
    }
}
