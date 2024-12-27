package com.android.systemui.navigationbar.store;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarInflaterView;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.SamsungNavigationBarSetupWizardView;
import com.android.systemui.navigationbar.SecTaskBarManagerImpl;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactoryBase;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor$addCallback$2;
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
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.navigationbar.SamsungKeyButtonRipple;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

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

    public NavBarStoreImpl(Context context, DisplayManager displayManager, SettingsHelper settingsHelper, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, BandAidPackFactoryBase bandAidPackFactoryBase, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, GestureHintAnimator.Factory factory, SysUiState sysUiState, SPluginManager sPluginManager, Handler handler) {
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
        this.handler = handler;
        PluginBarInteractionManager pluginBarInteractionManager = new PluginBarInteractionManager(context, this, sPluginManager);
        this.pluginBarInteractionManager = pluginBarInteractionManager;
        SamsungNavigationBarProxy.Companion.getClass();
        this.navBarProxy = SamsungNavigationBarProxy.Companion.getInstance();
        initDisplayDependenciesIfNeeded(context.getDisplayId(), context);
        BasicRuneWrapper.NAVBAR_ENABLED = BasicRune.NAVBAR_ENABLED;
        BasicRuneWrapper.NAVBAR_GESTURE = BasicRune.NAVBAR_GESTURE;
        BasicRuneWrapper.NAVBAR_SUPPORT_LARGE_COVER_SCREEN = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
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
        int i = kit.displayId;
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (z2) {
            storeLogUtil.printLog(this.loggingDepth, BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(i, "apply(", ") ", navBarStoreAction.getClass().getSimpleName()));
        }
        if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarIconAndHints) {
            ((NavigationBarView) getModule(NavigationBarView.class, i)).updateIconsAndHints();
        } else if (navBarStoreAction instanceof NavBarStoreAction.ReevaluateNavBar) {
            ((LightBarController) getModule(LightBarController.class, i)).reevaluate();
        } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarOpaqueColor) {
            NavigationBarTransitions navigationBarTransitions = (NavigationBarTransitions) getModule(NavigationBarTransitions.class, i);
            Object obj = this.interactorFactory.get(ColorSetting.class);
            Intrinsics.checkNotNull(obj);
            navigationBarTransitions.mBarBackground.updateOpaqueColor(((ColorSetting) obj).getNavigationBarColor());
        } else if (navBarStoreAction instanceof NavBarStoreAction.ReinflateNavBar) {
            ((NavigationBarView) getModule(NavigationBarView.class, i)).reInflateNavBarLayout();
        } else {
            boolean z3 = navBarStoreAction instanceof NavBarStoreAction.NavBarIconMarquee;
            NavBarStates navBarStates = kit.states;
            if (z3) {
                Point point = navBarStates.displaySize;
                ((NavigationBarView) getModule(NavigationBarView.class, i)).marqueeNavigationBarIcon(point.x, point.y);
            } else if (navBarStoreAction instanceof NavBarStoreAction.InvalidateRemoteView) {
                ((NavigationBarView) getModule(NavigationBarView.class, i)).updateRemoteViewContainer();
            } else {
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewContainer) {
                    NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, i);
                    NavBarStoreAction.Action action = ((NavBarStoreAction.UpdateRemoteViewContainer) navBarStoreAction).action;
                    navBarRemoteViewManager.updateRemoteViewContainer(action.rotation, action.leftRemoteViewContainer, action.rightRemoteViewContainer, i);
                    if (action.contextualButtonVisible) {
                        NavBarStore navBarStore = navBarRemoteViewManager.navBarStore;
                        if (!((NavBarStateManagerImpl) ((NavBarStoreImpl) (navBarStore != null ? navBarStore : null)).getNavStateManager(i)).states.canMove) {
                            LinearLayout linearLayout = navBarRemoteViewManager.rightContainer;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(4);
                            }
                        } else if (action.rotation == 1) {
                            LinearLayout linearLayout2 = navBarRemoteViewManager.leftContainer;
                            if (linearLayout2 != null) {
                                linearLayout2.setVisibility(4);
                            }
                        } else {
                            LinearLayout linearLayout3 = navBarRemoteViewManager.rightContainer;
                            if (linearLayout3 != null) {
                                linearLayout3.setVisibility(4);
                            }
                        }
                    }
                } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewDarkIntensity) {
                    NavBarRemoteViewManager navBarRemoteViewManager2 = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, i);
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
                } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewShortcut) {
                    ((NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, i)).setRemoteView(((NavBarStoreAction.UpdateRemoteViewShortcut) navBarStoreAction).action.remoteViewShortcut, i);
                } else {
                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarNormalStyle) {
                        getSUWNavigationBarView(i).setVisibility(8);
                        ((NavigationBarInflaterView) ((NavigationBarView) getModule(NavigationBarView.class, i)).requireViewById(R.id.navigation_inflater)).setVisibility(0);
                        NavigationBar navigationBar2 = (NavigationBar) getModule(NavigationBar.class, i);
                        navigationBar2.updateNavBarLayoutParams();
                        if (((NavBarStateManagerImpl) navigationBar2.mNavBarStateManager).isGestureMode()) {
                            RegionSamplingHelper regionSamplingHelper = navigationBar2.mRegionSamplingHelper;
                            regionSamplingHelper.updateSamplingRect();
                            regionSamplingHelper.start(navigationBar2.mSamplingBounds);
                        }
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarSUWStyle) {
                        ((NavigationBarInflaterView) ((NavigationBarView) getModule(NavigationBarView.class, i)).requireViewById(R.id.navigation_inflater)).setVisibility(8);
                        getSUWNavigationBarView(i).setVisibility(0);
                        NavigationBar navigationBar3 = (NavigationBar) getModule(NavigationBar.class, i);
                        navigationBar3.updateNavBarLayoutParams();
                        navigationBar3.mRegionSamplingHelper.stop();
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDisabled) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView = getSUWNavigationBarView(i);
                        boolean z4 = ((NavBarStoreAction.UpdateSUWDisabled) navBarStoreAction).action.disableSUWBack;
                        z = (sUWNavigationBarView.hints & 1) != 0;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton = sUWNavigationBarView.prevBtnLayout;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton2 = navigationBarSetupWizardButton != null ? navigationBarSetupWizardButton : null;
                        if (!z4 && !z) {
                            r6 = 0;
                        }
                        navigationBarSetupWizardButton2.setVisibility(r6);
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDarkIntensity) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView2 = getSUWNavigationBarView(i);
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
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWIconHints) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView3 = getSUWNavigationBarView(i);
                        int i2 = ((NavBarStoreAction.UpdateSUWIconHints) navBarStoreAction).action.navBarIconHints;
                        sUWNavigationBarView3.hints = i2;
                        z = (i2 & 1) != 0;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton3 = sUWNavigationBarView3.imeBtnLayout;
                        if (navigationBarSetupWizardButton3 == null) {
                            navigationBarSetupWizardButton3 = null;
                        }
                        navigationBarSetupWizardButton3.setVisibility(z ? 0 : 4);
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton4 = sUWNavigationBarView3.prevBtnLayout;
                        (navigationBarSetupWizardButton4 != null ? navigationBarSetupWizardButton4 : null).setVisibility(z ? 4 : 0);
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWA11yIcon) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView4 = getSUWNavigationBarView(i);
                        NavBarStoreAction.Action action2 = ((NavBarStoreAction.UpdateSUWA11yIcon) navBarStoreAction).action;
                        boolean z5 = action2.a11yClickable;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton5 = sUWNavigationBarView4.a11yLayout;
                        if (navigationBarSetupWizardButton5 == null) {
                            navigationBarSetupWizardButton5 = null;
                        }
                        navigationBarSetupWizardButton5.setVisibility(z5 ? 0 : 4);
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton6 = sUWNavigationBarView4.a11yLayout;
                        (navigationBarSetupWizardButton6 != null ? navigationBarSetupWizardButton6 : null).setLongClickable(action2.a11yLongClickable);
                    } else {
                        boolean z6 = navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarGoneStateFlag;
                        SysUiState sysUiState = this.sysUiFlagContainer;
                        if (z6) {
                            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_MUSIC, ((NavBarStoreAction.UpdateNavBarGoneStateFlag) navBarStoreAction).action.navBarVisibility == 8);
                            sysUiState.commitUpdate(i);
                        } else if (navBarStoreAction instanceof NavBarStoreAction.SetNavBarVisibility) {
                            final int i3 = ((NavBarStoreAction.SetNavBarVisibility) navBarStoreAction).action.navBarVisibility;
                            if (this.handleEventLoggingEnabled) {
                                storeLogUtil.printLog(this.loggingDepth, "Visibility : " + i3);
                            }
                            final NavigationBarView navigationBarView = (NavigationBarView) getModule(NavigationBarView.class, i);
                            this.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$apply$4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NavigationBarView navigationBarView2 = NavigationBarView.this;
                                    if (navigationBarView2 != null) {
                                        int i4 = i3;
                                        navigationBarView2.setVisibility(i4);
                                        View rootView = navigationBarView2.getRootView();
                                        if (rootView == null) {
                                            return;
                                        }
                                        rootView.setVisibility(i4);
                                    }
                                }
                            });
                        } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy) {
                            boolean z7 = ((NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy) navBarStoreAction).action.edgeBackGestureDisabled;
                            if (this.handleEventLoggingEnabled) {
                                storeLogUtil.printLog(this.loggingDepth, "disabled : " + z7);
                            }
                            if (i == 0 && (navigationBar = (NavigationBar) getModule(NavigationBar.class, i)) != null && (edgeBackGestureHandler2 = navigationBar.mEdgeBackGestureHandler) != null) {
                                edgeBackGestureHandler2.mDisabledByPolicy = z7;
                            }
                            if (BasicRune.NAVBAR_TASKBAR && (taskbarDelegate = (TaskbarDelegate) getModule(TaskbarDelegate.class, i)) != null && (edgeBackGestureHandler = taskbarDelegate.getEdgeBackGestureHandler()) != null) {
                                edgeBackGestureHandler.mDisabledByPolicy = z7;
                            }
                        } else if (navBarStoreAction instanceof NavBarStoreAction.SetGestureHintViewGroup) {
                            NavigationBarView navigationBarView2 = (NavigationBarView) getModule(NavigationBarView.class, i);
                            GestureHintAnimator gestureHintAnimator = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                            ButtonDispatcher homeHandle = navigationBarView2.getHomeHandle();
                            GestureHintGroup hintGroup = navigationBarView2.getHintGroup();
                            gestureHintAnimator.homeHandle = homeHandle;
                            gestureHintAnimator.gestureHintGroup = hintGroup;
                        } else {
                            boolean z8 = navBarStoreAction instanceof NavBarStoreAction.UpdateGestureHintVisibility;
                            NavBarStateManager navBarStateManager = kit.manager;
                            if (z8) {
                                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStateManager;
                                boolean canShowGestureHint = navBarStateManagerImpl.canShowGestureHint();
                                NavBarStates navBarStates2 = navBarStateManagerImpl.states;
                                ((NavigationBarView) getModule(NavigationBarView.class, i)).updateHintVisibility(navBarStates2.recentVisible & canShowGestureHint, navBarStates2.homeVisible & canShowGestureHint, navBarStates2.backVisible & canShowGestureHint);
                            } else if (navBarStoreAction instanceof NavBarStoreAction.ResetHintVI) {
                                final GestureHintAnimator gestureHintAnimator2 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                                gestureHintAnimator2.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.GestureHintAnimator$reset$1
                                    /* JADX WARN: Removed duplicated region for block: B:24:0x0075  */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final void run() {
                                        /*
                                            Method dump skipped, instructions count: 244
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.GestureHintAnimator$reset$1.run():void");
                                    }
                                });
                            } else if (navBarStoreAction instanceof NavBarStoreAction.StartHintVI) {
                                NavBarStoreAction.GestureHintVIInfo gestureHintVIInfo = ((NavBarStoreAction.StartHintVI) navBarStoreAction).action.gestureHintVIInfo;
                                GestureHintAnimator gestureHintAnimator3 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                                int i4 = gestureHintVIInfo.hintID;
                                boolean z9 = navBarStates.canMove;
                                gestureHintAnimator3.currentHintId = i4;
                                gestureHintAnimator3.isCanMove = z9;
                            } else if (navBarStoreAction instanceof NavBarStoreAction.MoveHintVI) {
                                NavBarStoreAction.GestureHintVIInfo gestureHintVIInfo2 = ((NavBarStoreAction.MoveHintVI) navBarStoreAction).action.gestureHintVIInfo;
                                final GestureHintAnimator gestureHintAnimator4 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                                gestureHintAnimator4.currentHintId = gestureHintVIInfo2.hintID;
                                Handler handler = gestureHintAnimator4.handler;
                                final int i5 = gestureHintVIInfo2.distanceY;
                                final long j = gestureHintVIInfo2.duration;
                                final int i6 = gestureHintVIInfo2.distanceX;
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
                                            int i7 = i6;
                                            int i8 = i5;
                                            int i9 = ((NavBarStateManagerImpl) gestureHintAnimator8.navBarStateManager).states.rotation;
                                            int abs = (!gestureHintAnimator8.isCanMove || i9 == 0 || i9 == 2) ? Math.abs(i8) : Math.abs(i7);
                                            float f3 = abs;
                                            float f4 = (dipToPixel2 * f3) / dipToPixel;
                                            float min = abs > 0 ? Math.min(f4, dipToPixel2) : Math.max(f4, -dipToPixel2);
                                            float f5 = NavigationModeUtil.isBottomGesture(GestureHintAnimator.this.navigationMode) ? 1.16f : 1.1f;
                                            float min2 = Math.min((((f5 - 1.0f) * f3) / dipToPixel) + 1.0f, f5);
                                            GestureHintAnimator gestureHintAnimator9 = GestureHintAnimator.this;
                                            int i10 = ((NavBarStateManagerImpl) gestureHintAnimator9.navBarStateManager).states.rotation;
                                            if (!gestureHintAnimator9.isCanMove || i10 == 0 || i10 == 2) {
                                                viewGroup.setTranslationY(-min);
                                                viewGroup.setScaleX(min2);
                                                str = "scaleX";
                                            } else {
                                                if (i10 == 3) {
                                                    viewGroup.setTranslationX(min);
                                                    viewGroup.setScaleY(min2);
                                                } else if (i10 == 1) {
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
                                                    Intrinsics.checkNotNull(animatorSet);
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
                                                Intrinsics.checkNotNull(animatorSet2);
                                                animatorSet2.playTogether(ofFloat);
                                                AnimatorSet animatorSet3 = GestureHintAnimator.this.holdingViAnimator;
                                                Intrinsics.checkNotNull(animatorSet3);
                                                animatorSet3.setDuration(500L);
                                                AnimatorSet animatorSet4 = GestureHintAnimator.this.holdingViAnimator;
                                                Intrinsics.checkNotNull(animatorSet4);
                                                animatorSet4.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f));
                                                AnimatorSet animatorSet5 = GestureHintAnimator.this.holdingViAnimator;
                                                Intrinsics.checkNotNull(animatorSet5);
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
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSysUiFlags) {
                                for (NavBarStoreAction.SysUiFlagInfo sysUiFlagInfo : ((NavBarStoreAction.UpdateSysUiFlags) navBarStoreAction).action.sysUiFlagInfoList) {
                                    if (this.handleEventLoggingEnabled) {
                                        int i7 = this.loggingDepth;
                                        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("set ", sysUiFlagInfo.flag, " : ");
                                        m.append(sysUiFlagInfo.value);
                                        storeLogUtil.printLog(i7, m.toString());
                                    }
                                    sysUiState.setFlag(sysUiFlagInfo.flag, sysUiFlagInfo.value);
                                }
                                sysUiState.commitUpdate(i);
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateOneHandModeInfo) {
                                OneHandModeUtil.Companion companion = OneHandModeUtil.Companion;
                                NavBarStoreAction.OneHandModeInfo oneHandModeInfo = ((NavBarStoreAction.UpdateOneHandModeInfo) navBarStoreAction).action.oneHandModeInfo;
                                companion.getClass();
                                OneHandModeUtil.oneHandModeInfo = oneHandModeInfo;
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateRegionSamplingRect) {
                                ((NavigationBar) getModule(NavigationBar.class, i)).mRegionSamplingHelper.updateSamplingRect();
                            } else if (navBarStoreAction instanceof NavBarStoreAction.RecalculateGestureInsetScale) {
                                NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                                SettingsHelper settingsHelper = this.settingsHelper;
                                Context context = (Context) getModule(Context.class, i);
                                boolean z10 = ((NavBarStoreAction.RecalculateGestureInsetScale) navBarStoreAction).action.folded;
                                navigationModeUtil.getClass();
                                try {
                                    if (NavigationModeUtil.sideInsetScaleArray.length == 0) {
                                        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(android.R.array.config_ntpServers);
                                        int length = obtainTypedArray.length();
                                        float[] fArr = new float[length];
                                        for (int i8 = 0; i8 < length; i8++) {
                                            fArr[i8] = obtainTypedArray.getFloat(i8, 1.0f);
                                        }
                                        obtainTypedArray.recycle();
                                        NavigationModeUtil.sideInsetScaleArray = fArr;
                                    }
                                    if (NavigationModeUtil.bottomInsetScaleArray.length == 0) {
                                        TypedArray obtainTypedArray2 = context.getResources().obtainTypedArray(android.R.array.config_rearDisplayDeviceStates);
                                        int length2 = obtainTypedArray2.length();
                                        float[] fArr2 = new float[length2];
                                        for (int i9 = 0; i9 < length2; i9++) {
                                            fArr2[i9] = obtainTypedArray2.getFloat(i9, 1.0f);
                                        }
                                        obtainTypedArray2.recycle();
                                        NavigationModeUtil.bottomInsetScaleArray = fArr2;
                                    }
                                    int navigationBarBackGestureSentivitySub = z10 ? settingsHelper.getNavigationBarBackGestureSentivitySub() : settingsHelper.getNavigationBarBackGestureSentivity();
                                    Settings.Secure.putFloat(context.getContentResolver(), "back_gesture_inset_scale_left", NavigationModeUtil.sideInsetScaleArray[navigationBarBackGestureSentivitySub]);
                                    Settings.Secure.putFloat(context.getContentResolver(), "back_gesture_inset_scale_right", NavigationModeUtil.sideInsetScaleArray[navigationBarBackGestureSentivitySub]);
                                    Settings.Secure.putFloat(context.getContentResolver(), "bottom_gesture_inset_scale", NavigationModeUtil.bottomInsetScaleArray[navigationBarBackGestureSentivitySub]);
                                } catch (Exception unused) {
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.ShowA11ySwipeUpTipPopup) {
                                ((NavigationBarView) getModule(NavigationBarView.class, 0)).showA11ySwipeUpTipPopup();
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavigationIcon) {
                                ((LightBarController) getModule(LightBarController.class, i)).updateNavigation();
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskBarIconsAndHints) {
                                TaskbarDelegate taskbarDelegate2 = (TaskbarDelegate) getModule(TaskbarDelegate.class, 0);
                                if (taskbarDelegate2 != null) {
                                    taskbarDelegate2.updateTaskbarButtonIconsAndHints();
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskBarNavBarEvents) {
                                TaskbarDelegate taskbarDelegate3 = (TaskbarDelegate) getModule(TaskbarDelegate.class, 0);
                                if (taskbarDelegate3 != null) {
                                    taskbarDelegate3.handleNavigationBarEvent(((NavBarStoreAction.UpdateTaskBarNavBarEvents) navBarStoreAction).action.taskbarNavBarEvents);
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarLayoutParams) {
                                ((NavigationBar) getModule(NavigationBar.class, i)).updateNavBarLayoutParams();
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateA11YStatus) {
                                NavigationBar navigationBar4 = (NavigationBar) getModule(NavigationBar.class, i);
                                if (navigationBar4 != null) {
                                    navigationBar4.mNavBarHelper.updateA11yState();
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.ForceHideGestureHint) {
                                navBarStateManager.getClass();
                                ((NavigationBarView) getModule(NavigationBarView.class, i)).updateHintVisibility(false, false, false);
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskbarStatus) {
                                NavBarStoreAction.Action action3 = ((NavBarStoreAction.UpdateTaskbarStatus) navBarStoreAction).action;
                                boolean z11 = action3.taskbarEnabled;
                                PluginBarInteractionManager pluginBarInteractionManager = this.pluginBarInteractionManager;
                                if (z11) {
                                    TaskbarDelegate taskbarDelegate4 = this.taskbarDelegate;
                                    SamsungPluginTaskBar samsungPluginTaskBar = taskbarDelegate4 != null ? ((SecTaskBarManagerImpl) taskbarDelegate4).pluginTaskBar : null;
                                    Intrinsics.checkNotNull(samsungPluginTaskBar);
                                    PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
                                    if (pluginNavigationBar != null) {
                                        pluginNavigationBar.onAttachedToWindow(samsungPluginTaskBar);
                                    }
                                } else {
                                    TaskbarDelegate taskbarDelegate5 = this.taskbarDelegate;
                                    SamsungPluginTaskBar samsungPluginTaskBar2 = taskbarDelegate5 != null ? ((SecTaskBarManagerImpl) taskbarDelegate5).pluginTaskBar : null;
                                    Intrinsics.checkNotNull(samsungPluginTaskBar2);
                                    PluginNavigationBar pluginNavigationBar2 = pluginBarInteractionManager.pluginNavigationBar;
                                    if (pluginNavigationBar2 != null) {
                                        pluginNavigationBar2.onDetachedFromWindow(samsungPluginTaskBar2);
                                    }
                                }
                                NavigationBar navigationBar5 = (NavigationBar) getModule(NavigationBar.class, i);
                                if (navigationBar5 != null && BasicRune.NAVBAR_POLICY_VISIBILITY && ((NavBarStateManagerImpl) getNavStateManager()).isGestureMode()) {
                                    navigationBar5.getView().reorient();
                                    boolean z12 = !action3.taskbarEnabled;
                                    RegionSamplingHelper regionSamplingHelper2 = navigationBar5.mRegionSamplingHelper;
                                    if (z12) {
                                        regionSamplingHelper2.start(navigationBar5.mSamplingBounds);
                                    } else {
                                        regionSamplingHelper2.stop();
                                    }
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateIndicatorSpringParams) {
                                boolean isTaskBarEnabled = ((NavBarStateManagerImpl) navBarStateManager).isTaskBarEnabled(false);
                                NavBarStoreAction.Action action4 = ((NavBarStoreAction.UpdateIndicatorSpringParams) navBarStoreAction).action;
                                if (isTaskBarEnabled) {
                                    ((TaskbarDelegate) getModule(TaskbarDelegate.class, i)).updateActiveIndicatorSpringParams(action4.stiffness, action4.dampingRatio);
                                } else {
                                    ((NavigationBarView) getModule(NavigationBarView.class, i)).updateActiveIndicatorSpringParams(action4.stiffness, action4.dampingRatio);
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateDefaultNavigationBarStatus) {
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
            Intrinsics.checkNotNull(obj);
            return (NavBarStateManager) obj;
        }
        Object obj2 = this.navStateManager.get(0);
        Intrinsics.checkNotNull(obj2);
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
        if (!(eventType instanceof EventTypeFactory.EventType.ResetBottomGestureHintVI ? true : eventType instanceof EventTypeFactory.EventType.MoveBottomGestureHintDistance ? true : eventType instanceof EventTypeFactory.EventType.StartBottomGestureHintVI ? true : eventType instanceof EventTypeFactory.EventType.OnSetRemoteView ? true : eventType instanceof EventTypeFactory.EventType.OnUpdateRemoteViewContainer ? true : eventType instanceof EventTypeFactory.EventType.OnInvalidateRemoteViews)) {
            handleEvent(obj, eventType, 0);
            return;
        }
        for (Integer num : this.navDependencies.keySet()) {
            Intrinsics.checkNotNull(num);
            handleEvent(obj, eventType, num.intValue());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initDisplayDependenciesIfNeeded(int r19, android.content.Context r20) {
        /*
            r18 = this;
            r11 = r18
            r12 = r19
            android.hardware.display.DisplayManager r0 = r11.displayManager
            android.view.Display r13 = r0.getDisplay(r12)
            if (r13 == 0) goto Lac
            java.util.HashMap r0 = r11.navDependencies
            java.lang.Integer r1 = java.lang.Integer.valueOf(r19)
            java.lang.Object r0 = r0.get(r1)
            if (r0 != 0) goto L2e
            java.lang.Integer r0 = java.lang.Integer.valueOf(r19)
            java.util.HashMap r1 = r11.navDependencies
            com.android.systemui.navigationbar.store.NavBarModuleDependency r2 = new com.android.systemui.navigationbar.store.NavBarModuleDependency
            r2.<init>()
            r1.put(r0, r2)
            java.lang.Class<android.content.Context> r0 = android.content.Context.class
            r1 = r20
            r11.putModule(r0, r1, r12)
            goto L30
        L2e:
            r1 = r20
        L30:
            java.util.HashMap r0 = r11.navStateManager
            java.lang.Integer r2 = java.lang.Integer.valueOf(r19)
            java.lang.Object r0 = r0.get(r2)
            if (r0 != 0) goto L67
            java.lang.Integer r15 = java.lang.Integer.valueOf(r19)
            java.util.HashMap r10 = r11.navStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r9 = new com.android.systemui.navigationbar.store.NavBarStateManagerImpl
            com.android.systemui.util.SettingsHelper r3 = r11.settingsHelper
            com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager r7 = r11.navBarRemoteViewManager
            r8 = 0
            com.android.systemui.navigationbar.interactor.InteractorFactory r4 = r11.interactorFactory
            com.android.systemui.navigationbar.util.StoreLogUtil r5 = r11.logWrapper
            com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer r6 = r11.layoutProviderContainer
            r16 = 128(0x80, float:1.794E-43)
            r17 = 0
            r0 = r9
            r1 = r20
            r2 = r18
            r14 = r9
            r9 = r16
            r16 = r13
            r13 = r10
            r10 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r13.put(r15, r14)
            goto L7e
        L67:
            r16 = r13
            if (r12 != 0) goto L7e
            java.util.HashMap r0 = r11.navStateManager
            java.lang.Integer r1 = java.lang.Integer.valueOf(r19)
            java.lang.Object r0 = r0.get(r1)
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = (com.android.systemui.navigationbar.store.NavBarStateManager) r0
            if (r0 == 0) goto L7e
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            r0.onNavigationBarCreated()
        L7e:
            boolean r0 = com.android.systemui.BasicRune.NAVBAR_REMOTEVIEW
            if (r0 == 0) goto L8b
            com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager r0 = r11.navBarRemoteViewManager
            r0.navBarStore = r11
            java.lang.Class<com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager> r1 = com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager.class
            r11.putModule(r1, r0, r12)
        L8b:
            android.graphics.Point r0 = new android.graphics.Point
            r0.<init>()
            r1 = r16
            r1.getRealSize(r0)
            java.util.HashMap r1 = r11.navStateManager
            java.lang.Integer r2 = java.lang.Integer.valueOf(r19)
            java.lang.Object r1 = r1.get(r2)
            com.android.systemui.navigationbar.store.NavBarStateManager r1 = (com.android.systemui.navigationbar.store.NavBarStateManager) r1
            if (r1 == 0) goto Lac
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r1 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r1
            com.android.systemui.navigationbar.model.NavBarStates r1 = r1.states
            r1.displaySize = r0
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            goto Lad
        Lac:
            r14 = 0
        Lad:
            if (r14 != 0) goto Lc2
            java.lang.String r0 = "Failed to add display dependencies because display "
            java.lang.String r1 = " returns null."
            java.lang.String r0 = androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0.m(r12, r0, r1)
            com.android.systemui.navigationbar.util.StoreLogUtil r1 = r11.logWrapper
            boolean r2 = r1.allowLogging
            if (r2 == 0) goto Lc2
            int r2 = r1.lastDepth
            r1.printLog(r2, r0)
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStoreImpl.initDisplayDependenciesIfNeeded(int, android.content.Context):void");
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

    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor$addCallback$2] */
    public final Object handleEvent(final Object obj, final EventTypeFactory.EventType eventType, final int i, Object obj2) {
        DesktopModeInteractor desktopModeInteractor;
        LegacyDesktopModeInteractor legacyDesktopModeInteractor;
        boolean z;
        boolean z2 = false;
        String str = null;
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarCreated) {
            EventTypeFactory.EventType.OnNavBarCreated onNavBarCreated = (EventTypeFactory.EventType.OnNavBarCreated) eventType;
            putModule(KeyguardStateController.class, onNavBarCreated.keyguardStateController, i);
            putModule(NavigationBar.class, onNavBarCreated.navigationBar, i);
        } else {
            boolean z3 = eventType instanceof EventTypeFactory.EventType.OnNavBarConfigChanged;
            SamsungNavigationBarProxy samsungNavigationBarProxy = this.navBarProxy;
            if (z3) {
                EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) eventType;
                onNavBarConfigChanged.canMove &= !DeviceType.isTablet();
                onNavBarConfigChanged.supportPhoneLayoutProvider &= !DeviceType.isTablet();
                samsungNavigationBarProxy.getClass();
            } else {
                boolean z4 = eventType instanceof EventTypeFactory.EventType.OnNavBarAttachedToWindow;
                PluginBarInteractionManager pluginBarInteractionManager = this.pluginBarInteractionManager;
                InteractorFactory interactorFactory = this.interactorFactory;
                if (z4) {
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
                    if (BasicRune.NAVBAR_DESKTOP && (legacyDesktopModeInteractor = (LegacyDesktopModeInteractor) interactorFactory.get(LegacyDesktopModeInteractor.class)) != null) {
                        final Consumer consumer = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$prepareHandleEvent$1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj3) {
                                NavBarStoreImpl navBarStoreImpl = NavBarStoreImpl.this;
                                navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnDesktopModeChanged(obj3 instanceof SemDesktopModeState ? (SemDesktopModeState) obj3 : null));
                            }
                        };
                        LegacyDesktopModeInteractor$addCallback$2 legacyDesktopModeInteractor$addCallback$2 = legacyDesktopModeInteractor.callback;
                        if (legacyDesktopModeInteractor$addCallback$2 != null) {
                            ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).unregisterCallback(legacyDesktopModeInteractor$addCallback$2);
                        }
                        legacyDesktopModeInteractor.callback = new DesktopManager.Callback() { // from class: com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor$addCallback$2
                            @Override // com.android.systemui.util.DesktopManager.Callback
                            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                                Consumer consumer2 = consumer;
                                if (consumer2 != null) {
                                    consumer2.accept(semDesktopModeState);
                                }
                            }
                        };
                        DesktopManager desktopManager = (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
                        if (desktopManager != null) {
                            desktopManager.registerCallback(legacyDesktopModeInteractor.callback);
                            consumer.accept(desktopManager.getSemDesktopModeState());
                        }
                    }
                    if (BasicRune.NAVBAR_NEW_DEX && (desktopModeInteractor = (DesktopModeInteractor) interactorFactory.get(DesktopModeInteractor.class)) != null) {
                        desktopModeInteractor.addCallback(new NavBarStoreImpl$prepareHandleEvent$2(this));
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
                        SysUiState sysUiState = this.sysUiFlagContainer;
                        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_MUSIC, false);
                        sysUiState.commitUpdate(i);
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnLightBarControllerCreated) {
                    putModule(LightBarController.class, ((EventTypeFactory.EventType.OnLightBarControllerCreated) eventType).lightBarController, i);
                    NavigationBarTransitions navigationBarTransitions = (NavigationBarTransitions) getModule(NavigationBarTransitions.class, i);
                    Object obj3 = interactorFactory.get(ColorSetting.class);
                    Intrinsics.checkNotNull(obj3);
                    navigationBarTransitions.mBarBackground.updateOpaqueColor(((ColorSetting) obj3).getNavigationBarColor());
                } else if (eventType instanceof EventTypeFactory.EventType.OnRotationLockedChanged) {
                    boolean z5 = ((EventTypeFactory.EventType.OnRotationLockedChanged) eventType).rotationLocked;
                    samsungNavigationBarProxy.rotationLocked = z5;
                    Iterator it = ((ArrayList) samsungNavigationBarProxy.rotationLockCallback).iterator();
                    while (it.hasNext()) {
                        ((Consumer) it.next()).accept(Boolean.valueOf(z5));
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnNavBarTransitionModeChanged) {
                    samsungNavigationBarProxy.navbarTransitionMode = ((EventTypeFactory.EventType.OnNavBarTransitionModeChanged) eventType).transitionMode;
                }
            }
        }
        List<Band> list = (List) this.packs.stream().flatMap(new Function() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$filterBands$allBands$1
            @Override // java.util.function.Function
            public final Object apply(Object obj4) {
                return ((BandAidPack) obj4).getBands().stream();
            }
        }).filter(new Predicate() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$filterBands$bands$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj4) {
                Object obj5;
                Object obj6;
                Band band = (Band) obj4;
                NavBarStoreImpl navBarStoreImpl = NavBarStoreImpl.this;
                Object obj7 = obj;
                EventTypeFactory.EventType eventType2 = eventType;
                int i2 = i;
                navBarStoreImpl.getClass();
                BandAid bandAid = band.bandAidDependency;
                Object obj8 = null;
                Boolean valueOf = bandAid != null ? Boolean.valueOf(bandAid.getEnabled()) : null;
                Intrinsics.checkNotNull(valueOf);
                if (!valueOf.booleanValue() || !band.runeDependency) {
                    return false;
                }
                String str2 = band.sPluginTag;
                if (str2.length() > 0) {
                    return StringsKt__StringsKt.contains(eventType2.getClass().getTypeName(), str2, false);
                }
                int i3 = band.targetDisplayId;
                if (i3 != -1 && i3 != i2) {
                    return false;
                }
                Iterator it2 = band.targetEvents.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj5 = null;
                        break;
                    }
                    obj5 = it2.next();
                    if (Intrinsics.areEqual(((Type) obj5).getTypeName(), eventType2.getClass().getTypeName())) {
                        break;
                    }
                }
                if (((Type) obj5) == null) {
                    return false;
                }
                Iterator it3 = band.targetModules.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        obj6 = null;
                        break;
                    }
                    obj6 = it3.next();
                    Type type = (Type) obj6;
                    if (StringsKt__StringsKt.contains(type.toString(), NavBarReflectUtil.class.getTypeName(), false) || Intrinsics.areEqual(obj7.getClass().getTypeName(), type.getTypeName()) || StringsKt__StringsKt.contains(obj7.getClass().getTypeName(), type.getTypeName(), false) || (obj7.getClass().getEnclosingClass() != null && Intrinsics.areEqual(obj7.getClass().getEnclosingClass().getTypeName(), type.getTypeName()))) {
                        break;
                    }
                }
                if (((Type) obj6) == null) {
                    return false;
                }
                Iterator it4 = band.moduleDependencies.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        break;
                    }
                    Object next = it4.next();
                    Type type2 = (Type) next;
                    NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) navBarStoreImpl.navDependencies.get(Integer.valueOf(i2));
                    if ((navBarModuleDependency != null ? navBarModuleDependency.modules.get(type2.getTypeName()) : null) == null) {
                        obj8 = next;
                        break;
                    }
                }
                return ((Type) obj8) == null;
            }
        }).sorted(new Comparator() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$filterBands$bands$2
            @Override // java.util.Comparator
            public final int compare(Object obj4, Object obj5) {
                return ((Band) obj4).priority >= ((Band) obj5).priority ? 1 : -1;
            }
        }).collect(Collectors.toList());
        boolean z6 = this.handleEventLoggingEnabled;
        int i2 = this.loggingDepth;
        StoreLogUtil storeLogUtil = this.logWrapper;
        storeLogUtil.lastDepth = i2;
        if (storeLogUtil.loggingStarted) {
            z = !(eventType instanceof EventTypeFactory.EventType.OnInvalidateRemoteViews ? true : eventType instanceof EventTypeFactory.EventType.OnSetRemoteView ? true : eventType instanceof EventTypeFactory.EventType.OnSetDisableFlags ? true : eventType instanceof EventTypeFactory.EventType.GetBarLayoutParams ? true : eventType instanceof EventTypeFactory.EventType.OnNavBarIconMarquee ? true : eventType instanceof EventTypeFactory.EventType.OnUpdateDarkIntensity ? true : eventType instanceof EventTypeFactory.EventType.GetDeadZoneSize ? true : eventType instanceof EventTypeFactory.EventType.OnUpdateRegionSamplingListener ? true : eventType instanceof EventTypeFactory.EventType.GetNavBarInsets ? true : eventType instanceof EventTypeFactory.EventType.GetImeInsets ? true : eventType instanceof EventTypeFactory.EventType.GetMandatoryInsets ? true : eventType instanceof EventTypeFactory.EventType.MoveBottomGestureHintDistance);
            storeLogUtil.allowLogging = z;
        } else {
            z = false;
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < i2; i3++) {
                sb.append("--");
            }
            sb.append("handleEvent(" + i + ") ");
            sb.append(eventType.toString());
            sb.append(" [Module] ");
            sb.append(obj.getClass().getSimpleName());
            storeLogUtil.logWrapper.d("Store", sb.toString());
            z2 = true;
        }
        this.handleEventLoggingEnabled = z2;
        this.loggingDepth++;
        NavBarStateManager navStateManager = getNavStateManager(i);
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navStateManager;
        Band.Kit kit = new Band.Kit(eventType, navStateManager, NavBarStates.copy$default(navBarStateManagerImpl.states), i);
        Object obj4 = null;
        for (Band band : list) {
            if (this.handleEventLoggingEnabled) {
                int i4 = this.loggingDepth;
                BandAid bandAid = band.bandAidDependency;
                storeLogUtil.printLog(i4, "[Band]" + (bandAid != null ? bandAid.name() : str));
            }
            this.loggingDepth++;
            Function function = band.patchAction;
            Object apply = function != null ? function.apply(kit) : null;
            if (!(apply instanceof Unit)) {
                obj4 = apply == null ? null : apply;
            }
            this.loggingDepth--;
            str = null;
        }
        navBarStateManagerImpl.updateStateFromEvent(eventType);
        for (Band band2 : list) {
            if (band2.afterAction != null) {
                if (this.handleEventLoggingEnabled) {
                    int i5 = this.loggingDepth;
                    BandAid bandAid2 = band2.bandAidDependency;
                    storeLogUtil.printLog(i5, "[Band] (afterAction) " + (bandAid2 != null ? bandAid2.name() : null));
                }
                this.loggingDepth++;
                band2.afterAction.accept(kit);
                this.loggingDepth--;
            }
        }
        this.loggingDepth--;
        if (obj4 != null && !(obj4 instanceof NavBarStore)) {
            endLogging(i, z6, obj4);
            return obj4;
        }
        if (obj2 instanceof Unit) {
            return obj2;
        }
        endLogging(i, z6, obj2);
        return obj2;
    }
}
