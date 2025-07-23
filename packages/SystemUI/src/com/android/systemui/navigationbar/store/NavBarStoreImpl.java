package com.android.systemui.navigationbar.store;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.UserHandle;
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
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactoryBase;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.plugin.PluginBarInteractionManager;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
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
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1;
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
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final NavBarStoreImpl apply(Band.Kit kit, NavBarStoreAction navBarStoreAction) {
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
            return this;
        }
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
                    r4 = 0;
                }
                navigationBarSetupWizardButton2.setVisibility(r4);
                return this;
            }
            if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDarkIntensity) {
                SamsungNavigationBarSetupWizardView sUWNavigationBarView2 = getSUWNavigationBarView(kit.displayId);
                float f2 = ((NavBarStoreAction.UpdateSUWDarkIntensity) navBarStoreAction).action.darkIntensity;
                int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f2, Integer.valueOf(sUWNavigationBarView2.getContext().getColor(R.color.navbar_icon_color_light)), Integer.valueOf(sUWNavigationBarView2.getContext().getColor(R.color.navbar_icon_color_dark)))).intValue();
                ImageView imageView = sUWNavigationBarView2.prevBtn;
                if (imageView == null) {
                    imageView = null;
                }
                PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
                imageView.setColorFilter(new PorterDuffColorFilter(intValue, mode));
                ImageView imageView2 = sUWNavigationBarView2.imeBtn;
                if (imageView2 == null) {
                    imageView2 = null;
                }
                imageView2.setColorFilter(new PorterDuffColorFilter(intValue, mode));
                ImageView imageView3 = sUWNavigationBarView2.a11yBtn;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                imageView3.setColorFilter(new PorterDuffColorFilter(intValue, mode));
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
                this.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$apply$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavigationBarView navigationBarView2 = NavigationBarView.this;
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
                    boolean canShowGestureHint = ((NavBarStateManagerImpl) kit.manager).canShowGestureHint();
                    NavBarStates navBarStates = ((NavBarStateManagerImpl) kit.manager).states;
                    ((NavigationBarView) getModule(NavigationBarView.class, kit.displayId)).updateHintVisibility(navBarStates.recentVisible & canShowGestureHint, navBarStates.homeVisible & canShowGestureHint, canShowGestureHint & navBarStates.backVisible);
                    return this;
                }
                if (navBarStoreAction instanceof NavBarStoreAction.ResetHintVI) {
                    final GestureHintAnimator gestureHintAnimator2 = (GestureHintAnimator) getModule(GestureHintAnimator.class, kit.displayId);
                    gestureHintAnimator2.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.GestureHintAnimator$reset$1
                        /* JADX WARN: Removed duplicated region for block: B:23:0x0073  */
                        /* JADX WARN: Removed duplicated region for block: B:27:0x00a2  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                Method dump skipped, instructions count: 250
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.GestureHintAnimator$reset$1.run():void");
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
                            GestureHintAnimator gestureHintAnimator5 = GestureHintAnimator.this;
                            ViewGroup viewGroup = (ViewGroup) gestureHintAnimator5.getHintView(gestureHintAnimator5.currentHintId);
                            if (viewGroup != null) {
                                GestureHintAnimator gestureHintAnimator6 = GestureHintAnimator.this;
                                float dipToPixel = gestureHintAnimator6.dipToPixel(NavigationModeUtil.isBottomGesture(gestureHintAnimator6.navigationMode) ? 210.0f : 105.0f);
                                GestureHintAnimator gestureHintAnimator7 = GestureHintAnimator.this;
                                float dipToPixel2 = gestureHintAnimator7.dipToPixel(NavigationModeUtil.isBottomGesture(gestureHintAnimator7.navigationMode) ? 17.0f : 8.5f);
                                GestureHintAnimator gestureHintAnimator8 = GestureHintAnimator.this;
                                int i13 = i12;
                                int i14 = i11;
                                int i15 = ((NavBarStateManagerImpl) gestureHintAnimator8.navBarStateManager).states.rotation;
                                int abs = (!gestureHintAnimator8.isCanMove || i15 == 0 || i15 == 2) ? Math.abs(i14) : Math.abs(i13);
                                float f3 = abs;
                                float f4 = (dipToPixel2 * f3) / dipToPixel;
                                float min = abs > 0 ? Math.min(f4, dipToPixel2) : Math.max(f4, -dipToPixel2);
                                float f5 = NavigationModeUtil.isBottomGesture(GestureHintAnimator.this.navigationMode) ? 1.16f : 1.1f;
                                float min2 = Math.min((((f5 - 1.0f) * f3) / dipToPixel) + 1.0f, f5);
                                GestureHintAnimator gestureHintAnimator9 = GestureHintAnimator.this;
                                int i16 = ((NavBarStateManagerImpl) gestureHintAnimator9.navBarStateManager).states.rotation;
                                if (!gestureHintAnimator9.isCanMove || i16 == 0 || i16 == 2) {
                                    viewGroup.setTranslationY(-min);
                                    viewGroup.setScaleX(min2);
                                    str = "scaleX";
                                } else {
                                    if (i16 == 3) {
                                        viewGroup.setTranslationX(min);
                                        viewGroup.setScaleY(min2);
                                    } else if (i16 == 1) {
                                        viewGroup.setTranslationX(-min);
                                        viewGroup.setScaleY(min2);
                                    }
                                    str = "scaleY";
                                }
                                if (GestureHintAnimator.this.currentHintId != 1 || viewGroup.getChildCount() <= 0) {
                                    return;
                                }
                                View childAt = viewGroup.getChildAt(0);
                                if (j == 0) {
                                    AnimatorSet animatorSet = GestureHintAnimator.this.holdingViAnimator;
                                    if (animatorSet != null) {
                                        animatorSet.getClass();
                                        animatorSet.cancel();
                                        GestureHintAnimator.this.holdingViAnimator = null;
                                        return;
                                    }
                                    return;
                                }
                                if (GestureHintAnimator.this.holdingViAnimator == null) {
                                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(childAt, str, 1.0f);
                                    GestureHintAnimator.this.holdingViAnimator = new AnimatorSet();
                                    AnimatorSet animatorSet2 = GestureHintAnimator.this.holdingViAnimator;
                                    animatorSet2.getClass();
                                    animatorSet2.playTogether(ofFloat);
                                    AnimatorSet animatorSet3 = GestureHintAnimator.this.holdingViAnimator;
                                    animatorSet3.getClass();
                                    animatorSet3.setDuration(500L);
                                    AnimatorSet animatorSet4 = GestureHintAnimator.this.holdingViAnimator;
                                    animatorSet4.getClass();
                                    animatorSet4.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f));
                                    AnimatorSet animatorSet5 = GestureHintAnimator.this.holdingViAnimator;
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
                            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("set ", sysUiFlagInfo.flag, " : ");
                            m.append(sysUiFlagInfo.value);
                            storeLogUtil.printLog(i13, m.toString());
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
                            TypedArray obtainTypedArray = context.getResources().obtainTypedArray(android.R.array.config_primaryCredentialProviderService);
                            int length = obtainTypedArray.length();
                            float[] fArr = new float[length];
                            for (int i15 = 0; i15 < length; i15++) {
                                fArr[i15] = obtainTypedArray.getFloat(i15, 1.0f);
                            }
                            obtainTypedArray.recycle();
                            NavigationModeUtil.sideInsetScaleArray = fArr;
                        }
                        if (NavigationModeUtil.bottomInsetScaleArray.length == 0) {
                            TypedArray obtainTypedArray2 = context.getResources().obtainTypedArray(android.R.array.config_roundedCornerRadiusAdjustmentArray);
                            int length2 = obtainTypedArray2.length();
                            float[] fArr2 = new float[length2];
                            for (int i16 = 0; i16 < length2; i16++) {
                                fArr2[i16] = obtainTypedArray2.getFloat(i16, 1.0f);
                            }
                            obtainTypedArray2.recycle();
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
                            return this;
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
                                    boolean isTaskBarEnabled = ((NavBarStateManagerImpl) kit.manager).isTaskBarEnabled(false);
                                    int i17 = kit.displayId;
                                    if (isTaskBarEnabled) {
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
        Set keySet = this.navDependencies.keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : keySet) {
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
        String m = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Failed to add display dependencies because display ", " returns null.");
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (storeLogUtil.allowLogging) {
            storeLogUtil.printLog(storeLogUtil.lastDepth, m);
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

    /* JADX WARN: Type inference failed for: r15v0, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2] */
    public final Object handleEvent(final Object obj, final EventTypeFactory.EventType eventType, final int i, Object obj2) {
        final DesktopModeInteractor desktopModeInteractor;
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
                InteractorFactory interactorFactory = this.interactorFactory;
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
                    if (BasicRune.NAVBAR_DESKTOP && (desktopModeInteractor = (DesktopModeInteractor) interactorFactory.get(DesktopModeInteractor.class)) != null) {
                        final Consumer consumer = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$prepareHandleEvent$1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj3) {
                                NavBarStoreImpl navBarStoreImpl = NavBarStoreImpl.this;
                                navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnDesktopModeChanged(((Boolean) obj3).booleanValue()));
                                NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                                navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
                            }
                        };
                        DesktopModeInteractor$addCallback$2 desktopModeInteractor$addCallback$2 = desktopModeInteractor.broadcastReceiver;
                        if (desktopModeInteractor$addCallback$2 != null) {
                            desktopModeInteractor.broadcastDispatcher.unregisterReceiver(desktopModeInteractor$addCallback$2);
                        }
                        ?? r15 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context2, Intent intent) {
                                String action = intent != null ? intent.getAction() : null;
                                if (action != null && action.hashCode() == 833559602 && action.equals("android.intent.action.USER_UNLOCKED")) {
                                    DesktopModeInteractor desktopModeInteractor2 = DesktopModeInteractor.this;
                                    desktopModeInteractor2.userUnlocked = Boolean.TRUE;
                                    Consumer consumer2 = consumer;
                                    if (consumer2 != null) {
                                        consumer2.accept(Boolean.valueOf(desktopModeInteractor2.isEnabled()));
                                    }
                                }
                            }
                        };
                        BroadcastDispatcher.registerReceiverWithHandler$default(desktopModeInteractor.broadcastDispatcher, r15, desktopModeInteractor.intentFilter, desktopModeInteractor.bgHandler, UserHandle.ALL, null, 48);
                        desktopModeInteractor.broadcastReceiver = r15;
                        DesktopMode desktopMode = desktopModeInteractor.desktopMode;
                        if (desktopMode != null) {
                            DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = new DesktopTasksController.DefaultDisplayDesktopModeChangeListener() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$4
                                @Override // com.android.wm.shell.desktopmode.DesktopTasksController.DefaultDisplayDesktopModeChangeListener
                                public final void onDefaultDisplayDesktopModeChanged(boolean z6) {
                                    DesktopModeInteractor desktopModeInteractor2 = DesktopModeInteractor.this;
                                    desktopModeInteractor2.isDefaultDisplayDesktopMode = z6;
                                    Consumer consumer2 = consumer;
                                    if (consumer2 != null) {
                                        consumer2.accept(Boolean.valueOf(desktopModeInteractor2.isEnabled()));
                                    }
                                }
                            };
                            Executor executor = desktopModeInteractor.mainExecutor;
                            DesktopTasksController desktopTasksController = DesktopTasksController.this;
                            desktopTasksController.mainExecutor.execute(new DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1(desktopTasksController, defaultDisplayDesktopModeChangeListener, executor));
                        }
                        consumer.accept(Boolean.valueOf(desktopModeInteractor.isEnabled()));
                    }
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
                    Object obj3 = interactorFactory.get(ColorSetting.class);
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
        Stream flatMap = stream.flatMap(new Function() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj5) {
                return Function1.this.mo779invoke(obj5);
            }
        });
        final Function1 function1 = new Function1() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$$ExternalSyntheticLambda1
            /* JADX WARN: Code restructure failed: missing block: B:12:0x0042, code lost:
            
                if (r7 == r6) goto L60;
             */
            /* JADX WARN: Code restructure failed: missing block: B:13:0x0124, code lost:
            
                r3 = true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:53:0x0122, code lost:
            
                if (((java.lang.reflect.Type) r2) == null) goto L60;
             */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object mo779invoke(java.lang.Object r11) {
                /*
                    Method dump skipped, instructions count: 298
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStoreImpl$$ExternalSyntheticLambda1.mo779invoke(java.lang.Object):java.lang.Object");
            }
        };
        Stream filter = flatMap.filter(new Predicate() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj5) {
                return ((Boolean) Function1.this.mo779invoke(obj5)).booleanValue();
            }
        });
        final NavBarStoreImpl$$ExternalSyntheticLambda2 navBarStoreImpl$$ExternalSyntheticLambda2 = new NavBarStoreImpl$$ExternalSyntheticLambda2();
        List<Band> list = (List) filter.sorted(new Comparator() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$sam$java_util_Comparator$0
            @Override // java.util.Comparator
            public final /* synthetic */ int compare(Object obj5, Object obj6) {
                return ((Number) Function2.this.invoke(obj5, obj6)).intValue();
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
            Object apply = function != null ? function.apply(kit) : null;
            if (!(apply instanceof Unit)) {
                obj5 = apply == null ? null : apply;
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
