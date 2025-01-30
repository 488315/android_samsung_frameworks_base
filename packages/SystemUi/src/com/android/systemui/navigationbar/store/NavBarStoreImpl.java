package com.android.systemui.navigationbar.store;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarInflaterView;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.SamsungNavigationBarSetupWizardView;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactoryBase;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.gestural.GestureHintGroup;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$5;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor$addCallback$2;
import com.android.systemui.navigationbar.plugin.PluginBarInteractionManager;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.util.NavBarReflectUtil;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.util.OneHandModeUtil;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
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
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarStoreImpl implements NavBarStore {
    public final BandAidPackFactoryBase bandAidPackFactory;
    public final DisplayManager displayManager;
    public boolean handleEventLoggingEnabled;
    public final GestureHintAnimator.Factory hintAnimatorFactory;
    public final InteractorFactory interactorFactory;
    public final LayoutProviderContainer layoutProviderContainer;
    public final StoreLogUtil logWrapper;
    public int loggingDepth;
    public final Context mainContext;
    public final SamsungNavigationBarProxy navBarProxy;
    public final NavBarRemoteViewManager navBarRemoteViewManager;
    public final HashMap navDependencies = new HashMap();
    public final HashMap navStateManager = new HashMap();
    public List packs = new ArrayList();
    public final PluginBarInteractionManager pluginBarInteractionManager;
    public final SettingsHelper settingsHelper;
    public final SysUiState sysUiFlagContainer;
    public TaskbarDelegate taskbarDelegate;

    public NavBarStoreImpl(Context context, DisplayManager displayManager, SettingsHelper settingsHelper, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, BandAidPackFactoryBase bandAidPackFactoryBase, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, GestureHintAnimator.Factory factory, SysUiState sysUiState) {
        this.mainContext = context;
        this.displayManager = displayManager;
        this.settingsHelper = settingsHelper;
        this.layoutProviderContainer = layoutProviderContainer;
        this.navBarRemoteViewManager = navBarRemoteViewManager;
        this.bandAidPackFactory = bandAidPackFactoryBase;
        this.interactorFactory = interactorFactory;
        this.logWrapper = storeLogUtil;
        this.hintAnimatorFactory = factory;
        this.sysUiFlagContainer = sysUiState;
        PluginBarInteractionManager pluginBarInteractionManager = new PluginBarInteractionManager(context, this);
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
        BasicRuneWrapper.NAVBAR_SUPPORT_LARGE_COVER_SCREEN = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        ((SPluginManager) Dependency.get(SPluginManager.class)).addPluginListener((SPluginListener) pluginBarInteractionManager.pluginListener, PluginNavigationBar.class, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v100 */
    /* JADX WARN: Type inference failed for: r0v89 */
    /* JADX WARN: Type inference failed for: r0v90 */
    /* JADX WARN: Type inference failed for: r13v1, types: [com.android.systemui.navigationbar.store.NavBarStateManager, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v41 */
    /* JADX WARN: Type inference failed for: r13v42 */
    /* JADX WARN: Type inference failed for: r13v45 */
    /* JADX WARN: Type inference failed for: r13v46 */
    /* JADX WARN: Type inference failed for: r13v56 */
    /* JADX WARN: Type inference failed for: r13v59 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public final NavBarStoreImpl apply(Band.Kit kit, NavBarStoreAction navBarStoreAction) {
        RotationButtonController rotationButtonController;
        TaskbarDelegate taskbarDelegate;
        EdgeBackGestureHandler edgeBackGestureHandler;
        NavigationBar navigationBar;
        EdgeBackGestureHandler edgeBackGestureHandler2;
        boolean z = this.handleEventLoggingEnabled;
        int i = kit.displayId;
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (z) {
            storeLogUtil.printLog(this.loggingDepth, "apply(" + i + ") " + navBarStoreAction.getClass().getSimpleName());
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
            boolean z2 = navBarStoreAction instanceof NavBarStoreAction.NavBarIconMarquee;
            NavBarStateManager.States states = kit.states;
            if (z2) {
                Point point = states.displaySize;
                ((NavigationBarView) getModule(NavigationBarView.class, i)).marqueeNavigationBarIcon(point.x, point.y);
            } else if (navBarStoreAction instanceof NavBarStoreAction.InvalidateRemoteView) {
                ((NavigationBarView) getModule(NavigationBarView.class, i)).updateRemoteViewContainer();
            } else {
                int i2 = 1;
                i2 = 1;
                if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewContainer) {
                    int i3 = states.rotation;
                    NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, i);
                    NavBarStoreAction.Action action = ((NavBarStoreAction.UpdateRemoteViewContainer) navBarStoreAction).action;
                    navBarRemoteViewManager.updateRemoteViewContainer(i3, action.leftRemoteViewContainer, action.rightRemoteViewContainer, i);
                    if (action.contextualButtonVisible) {
                        if (!((NavBarStoreImpl) navBarRemoteViewManager.getNavBarStore()).getNavStateManager(i).states.canMove) {
                            LinearLayout linearLayout = navBarRemoteViewManager.rightContainer;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(4);
                            }
                        } else if (i3 == 1) {
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
                } else {
                    int i4 = 0;
                    if (navBarStoreAction instanceof NavBarStoreAction.UpdateRemoteViewDarkIntensity) {
                        NavBarRemoteViewManager navBarRemoteViewManager2 = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, i);
                        float f = ((NavBarStoreAction.UpdateRemoteViewDarkIntensity) navBarStoreAction).action.remoteViewDarkIntensity;
                        if ((navBarRemoteViewManager2.darkIntensity != f ? 0 : 1) == 0) {
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
                        NavBarRemoteViewManager navBarRemoteViewManager3 = (NavBarRemoteViewManager) getModule(NavBarRemoteViewManager.class, i);
                        NavBarStoreAction.RemoteViewShortcut remoteViewShortcut = ((NavBarStoreAction.UpdateRemoteViewShortcut) navBarStoreAction).action.remoteViewShortcut;
                        navBarRemoteViewManager3.getClass();
                        String str = remoteViewShortcut.requestClass;
                        int i5 = remoteViewShortcut.position;
                        RemoteViews remoteViews = remoteViewShortcut.remoteViews;
                        if (remoteViews != null) {
                            NavBarRemoteView navBarRemoteView = new NavBarRemoteView(navBarRemoteViewManager3.context, str, remoteViews, remoteViewShortcut.priority);
                            NavBarStateManager navStateManager = ((NavBarStoreImpl) navBarRemoteViewManager3.getNavBarStore()).getNavStateManager(i);
                            String str2 = navBarRemoteView.requestClass;
                            navBarRemoteViewManager3.removeRemoteView(i5, str2);
                            if ((str2 != null && StringsKt__StringsKt.contains(str2, "honeyboard", false)) != false) {
                                if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && i == 1 && !navBarRemoteViewManager3.settingsHelper.isNavBarButtonOrderDefault() && ((navStateManager.states.rotation == 0 && !navStateManager.isSideAndBottomGestureMode(false)) || (navStateManager.states.rotation != 0 && !navStateManager.isGestureMode()))) {
                                    i4 = 1;
                                }
                                navBarRemoteViewManager3.showInGestureMode = true;
                                navBarRemoteViewManager3.adaptivePosition = i4;
                                i5 = i4;
                            }
                            if (i5 == 0) {
                                navBarRemoteViewManager3.leftViewList.add(navBarRemoteView);
                            } else if (i5 == 1) {
                                navBarRemoteViewManager3.rightViewList.add(navBarRemoteView);
                            }
                            navBarRemoteViewManager3.applyTint(navBarRemoteView.view);
                        } else {
                            navBarRemoteViewManager3.removeRemoteView(i5, str);
                        }
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarNormalStyle) {
                        NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) ((NavigationBarView) getModule(NavigationBarView.class, i)).findViewById(R.id.navigation_inflater);
                        if (navigationBarInflaterView.getVisibility() != 0) {
                            getSUWNavigationBarView(i).setVisibility(8);
                            navigationBarInflaterView.setVisibility(0);
                            ((NavigationBar) getModule(NavigationBar.class, 0)).updateNavBarLayoutParams();
                        }
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarSUWStyle) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView = getSUWNavigationBarView(i);
                        if (sUWNavigationBarView.getVisibility() != 0) {
                            ((NavigationBarInflaterView) ((NavigationBarView) getModule(NavigationBarView.class, i)).findViewById(R.id.navigation_inflater)).setVisibility(8);
                            sUWNavigationBarView.setVisibility(0);
                            ((NavigationBar) getModule(NavigationBar.class, 0)).updateNavBarLayoutParams();
                        }
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDisabled) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView2 = getSUWNavigationBarView(i);
                        boolean z3 = ((NavBarStoreAction.UpdateSUWDisabled) navBarStoreAction).action.disableSUWBack;
                        ?? r5 = (sUWNavigationBarView2.hints & 1) == 0 ? 0 : 1;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton = sUWNavigationBarView2.prevBtnLayout;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton2 = navigationBarSetupWizardButton != null ? navigationBarSetupWizardButton : null;
                        if (!z3 && r5 == 0) {
                            r6 = 0;
                        }
                        navigationBarSetupWizardButton2.setVisibility(r6);
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWDarkIntensity) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView3 = getSUWNavigationBarView(i);
                        float f2 = ((NavBarStoreAction.UpdateSUWDarkIntensity) navBarStoreAction).action.darkIntensity;
                        int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f2, Integer.valueOf(sUWNavigationBarView3.getContext().getColor(R.color.navbar_icon_color_light)), Integer.valueOf(sUWNavigationBarView3.getContext().getColor(R.color.navbar_icon_color_dark)))).intValue();
                        ImageView imageView = sUWNavigationBarView3.prevBtn;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setColorFilter(new PorterDuffColorFilter(intValue, PorterDuff.Mode.SRC_ATOP));
                        ImageView imageView2 = sUWNavigationBarView3.imeBtn;
                        if (imageView2 == null) {
                            imageView2 = null;
                        }
                        imageView2.setColorFilter(new PorterDuffColorFilter(intValue, PorterDuff.Mode.SRC_ATOP));
                        ImageView imageView3 = sUWNavigationBarView3.a11yBtn;
                        if (imageView3 == null) {
                            imageView3 = null;
                        }
                        imageView3.setColorFilter(new PorterDuffColorFilter(intValue, PorterDuff.Mode.SRC_ATOP));
                        KeyButtonRipple keyButtonRipple = sUWNavigationBarView3.backRipple;
                        if (keyButtonRipple == null) {
                            keyButtonRipple = null;
                        }
                        keyButtonRipple.setDarkIntensity(f2);
                        KeyButtonRipple keyButtonRipple2 = sUWNavigationBarView3.backAltRipple;
                        if (keyButtonRipple2 == null) {
                            keyButtonRipple2 = null;
                        }
                        keyButtonRipple2.setDarkIntensity(f2);
                        KeyButtonRipple keyButtonRipple3 = sUWNavigationBarView3.a11yRipple;
                        (keyButtonRipple3 != null ? keyButtonRipple3 : null).setDarkIntensity(f2);
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWIconHints) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView4 = getSUWNavigationBarView(i);
                        int i6 = ((NavBarStoreAction.UpdateSUWIconHints) navBarStoreAction).action.navBarIconHints;
                        sUWNavigationBarView4.hints = i6;
                        ?? r52 = (i6 & 1) == 0 ? 0 : 1;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton3 = sUWNavigationBarView4.imeBtnLayout;
                        if (navigationBarSetupWizardButton3 == null) {
                            navigationBarSetupWizardButton3 = null;
                        }
                        navigationBarSetupWizardButton3.setVisibility(r52 != 0 ? 0 : 4);
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton4 = sUWNavigationBarView4.prevBtnLayout;
                        (navigationBarSetupWizardButton4 != null ? navigationBarSetupWizardButton4 : null).setVisibility(r52 == 0 ? 0 : 4);
                    } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateSUWA11yIcon) {
                        SamsungNavigationBarSetupWizardView sUWNavigationBarView5 = getSUWNavigationBarView(i);
                        NavBarStoreAction.Action action2 = ((NavBarStoreAction.UpdateSUWA11yIcon) navBarStoreAction).action;
                        boolean z4 = action2.a11yClickable;
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton5 = sUWNavigationBarView5.a11yLayout;
                        if (navigationBarSetupWizardButton5 == null) {
                            navigationBarSetupWizardButton5 = null;
                        }
                        navigationBarSetupWizardButton5.setVisibility(z4 ? 0 : 4);
                        SamsungNavigationBarSetupWizardView.NavigationBarSetupWizardButton navigationBarSetupWizardButton6 = sUWNavigationBarView5.a11yLayout;
                        (navigationBarSetupWizardButton6 != null ? navigationBarSetupWizardButton6 : null).setLongClickable(action2.a11yLongClickable);
                    } else {
                        boolean z5 = navBarStoreAction instanceof NavBarStoreAction.UpdateNavBarGoneStateFlag;
                        SysUiState sysUiState = this.sysUiFlagContainer;
                        if (z5) {
                            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO, ((NavBarStoreAction.UpdateNavBarGoneStateFlag) navBarStoreAction).action.navBarVisibility == 8);
                            sysUiState.commitUpdate(i);
                        } else if (navBarStoreAction instanceof NavBarStoreAction.SetNavBarVisibility) {
                            final int i7 = ((NavBarStoreAction.SetNavBarVisibility) navBarStoreAction).action.navBarVisibility;
                            if (this.handleEventLoggingEnabled) {
                                storeLogUtil.printLog(this.loggingDepth, "Visibility : " + i7);
                            }
                            final NavigationBarView navigationBarView = (NavigationBarView) getModule(NavigationBarView.class, i);
                            ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$apply$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NavigationBarView navigationBarView2 = NavigationBarView.this;
                                    if (navigationBarView2 != null) {
                                        int i8 = i7;
                                        navigationBarView2.setVisibility(i8);
                                        View rootView = navigationBarView2.getRootView();
                                        if (rootView == null) {
                                            return;
                                        }
                                        rootView.setVisibility(i8);
                                    }
                                }
                            });
                        } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy) {
                            boolean z6 = ((NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy) navBarStoreAction).action.edgeBackGestureDisabled;
                            if (this.handleEventLoggingEnabled) {
                                storeLogUtil.printLog(this.loggingDepth, "disabled : " + z6);
                            }
                            if (i == 0 && (navigationBar = (NavigationBar) getModule(NavigationBar.class, i)) != null && (edgeBackGestureHandler2 = navigationBar.mEdgeBackGestureHandler) != null) {
                                edgeBackGestureHandler2.mDisabledByPolicy = z6;
                            }
                            if (BasicRune.NAVBAR_SUPPORT_TASKBAR && (taskbarDelegate = (TaskbarDelegate) getModule(TaskbarDelegate.class, i)) != null && (edgeBackGestureHandler = taskbarDelegate.mEdgeBackGestureHandler) != null) {
                                edgeBackGestureHandler.mDisabledByPolicy = z6;
                            }
                        } else if (navBarStoreAction instanceof NavBarStoreAction.SetGestureHintViewGroup) {
                            NavigationBarView navigationBarView2 = (NavigationBarView) getModule(NavigationBarView.class, i);
                            GestureHintAnimator gestureHintAnimator = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                            ButtonDispatcher homeHandle = navigationBarView2.getHomeHandle();
                            GestureHintGroup hintGroup = navigationBarView2.getHintGroup();
                            gestureHintAnimator.homeHandle = homeHandle;
                            gestureHintAnimator.gestureHintGroup = hintGroup;
                        } else {
                            boolean z7 = navBarStoreAction instanceof NavBarStoreAction.UpdateGestureHintVisibility;
                            ?? r13 = kit.manager;
                            if (z7) {
                                Boolean valueOf = Boolean.valueOf(r13.isGestureHintEnabled() && !r13.states.sPayShowing);
                                r13.logNavBarStates(valueOf, "canShowGestureHint");
                                Intrinsics.checkNotNull(valueOf);
                                boolean booleanValue = valueOf.booleanValue();
                                NavBarStateManager.States states2 = r13.states;
                                ((NavigationBarView) getModule(NavigationBarView.class, i)).updateHintVisibility(states2.recentVisible & booleanValue, states2.homeVisible & booleanValue, states2.backVisible & booleanValue);
                            } else if (navBarStoreAction instanceof NavBarStoreAction.ResetHintVI) {
                                final GestureHintAnimator gestureHintAnimator2 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                                gestureHintAnimator2.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.GestureHintAnimator$reset$1
                                    /* JADX WARN: Removed duplicated region for block: B:26:0x006f  */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final void run() {
                                        String str3;
                                        float f3;
                                        GestureHintAnimator gestureHintAnimator3 = GestureHintAnimator.this;
                                        Iterator it3 = gestureHintAnimator3.hintList.iterator();
                                        while (it3.hasNext()) {
                                            int intValue2 = ((Number) it3.next()).intValue();
                                            View hintView = gestureHintAnimator3.getHintView(intValue2);
                                            if ((hintView != null ? hintView.animate() : null) != null) {
                                                hintView.setAlpha(1.0f);
                                            }
                                            ViewGroup viewGroup = (ViewGroup) gestureHintAnimator3.getHintView(intValue2);
                                            if ((viewGroup != null ? viewGroup.animate() : null) != null) {
                                                AnimatorSet animatorSet = gestureHintAnimator3.holdingViAnimator;
                                                if (animatorSet != null) {
                                                    animatorSet.cancel();
                                                    gestureHintAnimator3.holdingViAnimator = null;
                                                }
                                                int i8 = gestureHintAnimator3.navBarStateManager.states.rotation;
                                                if (gestureHintAnimator3.isCanMove) {
                                                    if (i8 == 1) {
                                                        str3 = "scaleY";
                                                        f3 = 0.0f;
                                                        if (viewGroup.getChildCount() > 0) {
                                                        }
                                                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "scaleX", 1.0f);
                                                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(viewGroup, "scaleY", 1.0f);
                                                        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(viewGroup, "translationX", f3);
                                                        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(viewGroup, "translationY", 0.0f);
                                                        AnimatorSet animatorSet2 = new AnimatorSet();
                                                        animatorSet2.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4);
                                                        animatorSet2.setDuration(200L);
                                                        animatorSet2.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                                        animatorSet2.start();
                                                    } else if (i8 == 3) {
                                                        f3 = -0.0f;
                                                        str3 = "scaleY";
                                                        if (viewGroup.getChildCount() > 0) {
                                                            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(viewGroup.getChildAt(0), str3, 0.0f);
                                                            AnimatorSet animatorSet3 = new AnimatorSet();
                                                            animatorSet3.playTogether(ofFloat5);
                                                            animatorSet3.setDuration(200L);
                                                            animatorSet3.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f));
                                                            animatorSet3.start();
                                                        }
                                                        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(viewGroup, "scaleX", 1.0f);
                                                        ObjectAnimator ofFloat22 = ObjectAnimator.ofFloat(viewGroup, "scaleY", 1.0f);
                                                        ObjectAnimator ofFloat32 = ObjectAnimator.ofFloat(viewGroup, "translationX", f3);
                                                        ObjectAnimator ofFloat42 = ObjectAnimator.ofFloat(viewGroup, "translationY", 0.0f);
                                                        AnimatorSet animatorSet22 = new AnimatorSet();
                                                        animatorSet22.playTogether(ofFloat6, ofFloat22, ofFloat32, ofFloat42);
                                                        animatorSet22.setDuration(200L);
                                                        animatorSet22.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                                        animatorSet22.start();
                                                    }
                                                }
                                                str3 = "scaleX";
                                                f3 = 0.0f;
                                                if (viewGroup.getChildCount() > 0) {
                                                }
                                                ObjectAnimator ofFloat62 = ObjectAnimator.ofFloat(viewGroup, "scaleX", 1.0f);
                                                ObjectAnimator ofFloat222 = ObjectAnimator.ofFloat(viewGroup, "scaleY", 1.0f);
                                                ObjectAnimator ofFloat322 = ObjectAnimator.ofFloat(viewGroup, "translationX", f3);
                                                ObjectAnimator ofFloat422 = ObjectAnimator.ofFloat(viewGroup, "translationY", 0.0f);
                                                AnimatorSet animatorSet222 = new AnimatorSet();
                                                animatorSet222.playTogether(ofFloat62, ofFloat222, ofFloat322, ofFloat422);
                                                animatorSet222.setDuration(200L);
                                                animatorSet222.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f));
                                                animatorSet222.start();
                                            }
                                        }
                                    }
                                });
                            } else if (navBarStoreAction instanceof NavBarStoreAction.StartHintVI) {
                                NavBarStoreAction.GestureHintVIInfo gestureHintVIInfo = ((NavBarStoreAction.StartHintVI) navBarStoreAction).action.gestureHintVIInfo;
                                GestureHintAnimator gestureHintAnimator3 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                                int i8 = gestureHintVIInfo.hintID;
                                boolean z8 = states.canMove;
                                gestureHintAnimator3.currentHintId = i8;
                                gestureHintAnimator3.isCanMove = z8;
                            } else if (navBarStoreAction instanceof NavBarStoreAction.MoveHintVI) {
                                NavBarStoreAction.GestureHintVIInfo gestureHintVIInfo2 = ((NavBarStoreAction.MoveHintVI) navBarStoreAction).action.gestureHintVIInfo;
                                final GestureHintAnimator gestureHintAnimator4 = (GestureHintAnimator) getModule(GestureHintAnimator.class, i);
                                int i9 = gestureHintVIInfo2.hintID;
                                final int i10 = gestureHintVIInfo2.distanceX;
                                final int i11 = gestureHintVIInfo2.distanceY;
                                final long j = gestureHintVIInfo2.duration;
                                gestureHintAnimator4.currentHintId = i9;
                                gestureHintAnimator4.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.GestureHintAnimator$onActionMove$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        String str3;
                                        GestureHintAnimator gestureHintAnimator5 = GestureHintAnimator.this;
                                        ViewGroup viewGroup = (ViewGroup) gestureHintAnimator5.getHintView(gestureHintAnimator5.currentHintId);
                                        if (viewGroup != null) {
                                            GestureHintAnimator gestureHintAnimator6 = GestureHintAnimator.this;
                                            float applyDimension = TypedValue.applyDimension(1, NavigationModeUtil.isBottomGesture(gestureHintAnimator6.navigationMode) ? 210.0f : 105.0f, gestureHintAnimator6.context.getResources().getDisplayMetrics());
                                            GestureHintAnimator gestureHintAnimator7 = GestureHintAnimator.this;
                                            float applyDimension2 = TypedValue.applyDimension(1, NavigationModeUtil.isBottomGesture(gestureHintAnimator7.navigationMode) ? 17.0f : 8.5f, gestureHintAnimator7.context.getResources().getDisplayMetrics());
                                            GestureHintAnimator gestureHintAnimator8 = GestureHintAnimator.this;
                                            int i12 = i10;
                                            int i13 = i11;
                                            int i14 = gestureHintAnimator8.navBarStateManager.states.rotation;
                                            int abs = (!gestureHintAnimator8.isCanMove || i14 == 0 || i14 == 2) ? Math.abs(i13) : Math.abs(i12);
                                            float f3 = abs;
                                            float f4 = (applyDimension2 * f3) / applyDimension;
                                            float min = abs > 0 ? Math.min(f4, applyDimension2) : Math.max(f4, -applyDimension2);
                                            float f5 = NavigationModeUtil.isBottomGesture(GestureHintAnimator.this.navigationMode) ? 1.16f : 1.1f;
                                            float min2 = Math.min((((f5 - 1.0f) * f3) / applyDimension) + 1.0f, f5);
                                            GestureHintAnimator gestureHintAnimator9 = GestureHintAnimator.this;
                                            int i15 = gestureHintAnimator9.navBarStateManager.states.rotation;
                                            if (!gestureHintAnimator9.isCanMove || i15 == 0 || i15 == 2) {
                                                viewGroup.setTranslationY(-min);
                                                viewGroup.setScaleX(min2);
                                                str3 = "scaleX";
                                            } else {
                                                if (i15 == 3) {
                                                    viewGroup.setTranslationX(min);
                                                    viewGroup.setScaleY(min2);
                                                } else if (i15 == 1) {
                                                    viewGroup.setTranslationX(-min);
                                                    viewGroup.setScaleY(min2);
                                                }
                                                str3 = "scaleY";
                                            }
                                            if (GestureHintAnimator.this.currentHintId != 1 || viewGroup.getChildCount() <= 0) {
                                                return;
                                            }
                                            View childAt = viewGroup.getChildAt(0);
                                            if (j == 0) {
                                                AnimatorSet animatorSet = GestureHintAnimator.this.holdingViAnimator;
                                                if (animatorSet != null) {
                                                    animatorSet.cancel();
                                                    GestureHintAnimator.this.holdingViAnimator = null;
                                                    return;
                                                }
                                                return;
                                            }
                                            if (GestureHintAnimator.this.holdingViAnimator == null) {
                                                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(childAt, str3, 1.0f);
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
                                                if (Intrinsics.areEqual(str3, "scaleY")) {
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
                                        int i12 = this.loggingDepth;
                                        StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("set ", sysUiFlagInfo.flag, " : ");
                                        m17m.append(sysUiFlagInfo.value);
                                        storeLogUtil.printLog(i12, m17m.toString());
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
                                Context context = (Context) getModule(Context.class, i);
                                boolean z9 = ((NavBarStoreAction.RecalculateGestureInsetScale) navBarStoreAction).action.folded;
                                navigationModeUtil.getClass();
                                try {
                                    if ((NavigationModeUtil.sideInsetScaleArray.length == 0) != false) {
                                        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(android.R.array.config_oem_enabled_satellite_country_codes);
                                        int length = obtainTypedArray.length();
                                        float[] fArr = new float[length];
                                        for (int i13 = 0; i13 < length; i13++) {
                                            fArr[i13] = obtainTypedArray.getFloat(i13, 1.0f);
                                        }
                                        obtainTypedArray.recycle();
                                        NavigationModeUtil.sideInsetScaleArray = fArr;
                                    }
                                    if ((NavigationModeUtil.bottomInsetScaleArray.length == 0) != false) {
                                        TypedArray obtainTypedArray2 = context.getResources().obtainTypedArray(android.R.array.config_reduceBrightColorsCoefficientsNonlinear);
                                        int length2 = obtainTypedArray2.length();
                                        float[] fArr2 = new float[length2];
                                        while (i4 < length2) {
                                            fArr2[i4] = obtainTypedArray2.getFloat(i4, 1.0f);
                                            i4++;
                                        }
                                        obtainTypedArray2.recycle();
                                        NavigationModeUtil.bottomInsetScaleArray = fArr2;
                                    }
                                    SettingsHelper.ItemMap itemMap = this.settingsHelper.mItemLists;
                                    if (z9) {
                                        if (BasicRune.NAVBAR_GESTURE) {
                                            i2 = itemMap.get("navigation_bar_back_gesture_sensitivity_sub").getIntValue();
                                        }
                                    } else if (BasicRune.NAVBAR_GESTURE) {
                                        i2 = itemMap.get("navigation_bar_back_gesture_sensitivity").getIntValue();
                                    }
                                    ContentResolver contentResolver = context.getContentResolver();
                                    float[] fArr3 = NavigationModeUtil.sideInsetScaleArray;
                                    Intrinsics.checkNotNull(fArr3);
                                    Settings.Secure.putFloat(contentResolver, "back_gesture_inset_scale_left", fArr3[i2]);
                                    ContentResolver contentResolver2 = context.getContentResolver();
                                    float[] fArr4 = NavigationModeUtil.sideInsetScaleArray;
                                    Intrinsics.checkNotNull(fArr4);
                                    Settings.Secure.putFloat(contentResolver2, "back_gesture_inset_scale_right", fArr4[i2]);
                                    ContentResolver contentResolver3 = context.getContentResolver();
                                    float[] fArr5 = NavigationModeUtil.bottomInsetScaleArray;
                                    Intrinsics.checkNotNull(fArr5);
                                    Settings.Secure.putFloat(contentResolver3, "bottom_gesture_inset_scale", fArr5[i2]);
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
                                NavigationBar navigationBar2 = (NavigationBar) getModule(NavigationBar.class, i);
                                if (navigationBar2 != null) {
                                    navigationBar2.updateNavBarLayoutParams();
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateA11YStatus) {
                                NavigationBar navigationBar3 = (NavigationBar) getModule(NavigationBar.class, i);
                                if (navigationBar3 != null) {
                                    navigationBar3.mNavBarHelper.updateA11yState();
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.ForceHideGestureHint) {
                                r13.getClass();
                                ((NavigationBarView) getModule(NavigationBarView.class, i)).updateHintVisibility(false, false, false);
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateTaskbarStatus) {
                                NavBarStoreAction.Action action3 = ((NavBarStoreAction.UpdateTaskbarStatus) navBarStoreAction).action;
                                boolean z10 = action3.taskbarEnabled;
                                PluginBarInteractionManager pluginBarInteractionManager = this.pluginBarInteractionManager;
                                if (z10) {
                                    TaskbarDelegate taskbarDelegate4 = this.taskbarDelegate;
                                    SamsungPluginTaskBar samsungPluginTaskBar = taskbarDelegate4 != null ? taskbarDelegate4.mPluginTaskBar : null;
                                    Intrinsics.checkNotNull(samsungPluginTaskBar);
                                    PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
                                    if (pluginNavigationBar != null) {
                                        pluginNavigationBar.onAttachedToWindow(samsungPluginTaskBar);
                                    }
                                } else {
                                    TaskbarDelegate taskbarDelegate5 = this.taskbarDelegate;
                                    SamsungPluginTaskBar samsungPluginTaskBar2 = taskbarDelegate5 != null ? taskbarDelegate5.mPluginTaskBar : null;
                                    Intrinsics.checkNotNull(samsungPluginTaskBar2);
                                    PluginNavigationBar pluginNavigationBar2 = pluginBarInteractionManager.pluginNavigationBar;
                                    if (pluginNavigationBar2 != null) {
                                        pluginNavigationBar2.onDetachedFromWindow(samsungPluginTaskBar2);
                                    }
                                }
                                NavigationBar navigationBar4 = (NavigationBar) getModule(NavigationBar.class, i);
                                if (navigationBar4 != null && BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY && getNavStateManager(0).isGestureMode()) {
                                    ((NavigationBarView) navigationBar4.mView).reorient();
                                    boolean z11 = !action3.taskbarEnabled;
                                    RegionSamplingHelper regionSamplingHelper = navigationBar4.mRegionSamplingHelper;
                                    if (z11) {
                                        regionSamplingHelper.start(navigationBar4.mSamplingBounds);
                                    } else {
                                        regionSamplingHelper.stop();
                                    }
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateIndicatorSpringParams) {
                                boolean isTaskBarEnabled = r13.isTaskBarEnabled(false);
                                NavBarStoreAction.Action action4 = ((NavBarStoreAction.UpdateIndicatorSpringParams) navBarStoreAction).action;
                                if (isTaskBarEnabled) {
                                    TaskbarDelegate taskbarDelegate6 = (TaskbarDelegate) getModule(TaskbarDelegate.class, i);
                                    float f3 = action4.stiffness;
                                    EdgeBackGestureHandler edgeBackGestureHandler3 = taskbarDelegate6.mEdgeBackGestureHandler;
                                    if (edgeBackGestureHandler3.mIsNewBackAffordanceEnabled) {
                                        edgeBackGestureHandler3.mEdgeBackPlugin.updateActiveIndicatorSpringParams(f3, action4.dampingRatio);
                                    }
                                } else {
                                    ((NavigationBarView) getModule(NavigationBarView.class, i)).updateActiveIndicatorSpringParams(action4.stiffness, action4.dampingRatio);
                                }
                            } else if (navBarStoreAction instanceof NavBarStoreAction.UpdateDefaultNavigationBarStatus) {
                                NavigationBar navigationBar5 = (NavigationBar) getModule(NavigationBar.class, 0);
                                if (navigationBar5 != null) {
                                    navigationBar5.updateSystemUiStateFlags();
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

    public final Object getModule(Class cls, int i) {
        NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) this.navDependencies.get(Integer.valueOf(i));
        if (navBarModuleDependency != null) {
            return navBarModuleDependency.modules.get(cls.getTypeName());
        }
        return null;
    }

    public final NavBarStateManager getNavStateManager(int i) {
        HashMap hashMap = this.navStateManager;
        if (hashMap.get(Integer.valueOf(i)) != null) {
            Object obj = hashMap.get(Integer.valueOf(i));
            Intrinsics.checkNotNull(obj);
            return (NavBarStateManager) obj;
        }
        Object obj2 = hashMap.get(0);
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
        return getNavStateManager(i2).layoutProviderContainer;
    }

    public final SamsungNavigationBarSetupWizardView getSUWNavigationBarView(int i) {
        return (SamsungNavigationBarSetupWizardView) ((NavigationBarView) getModule(NavigationBarView.class, i)).findViewById(R.id.navigation_setupwizard);
    }

    public final void handleEvent(Object obj, EventTypeFactory.EventType eventType, int i) {
        handleEvent(obj, eventType, i, Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initDisplayDependenciesIfNeeded(int i, Context context) {
        Unit unit;
        Display display;
        NavBarStateManager navBarStateManager;
        Display display2 = this.displayManager.getDisplay(i);
        if (display2 != null) {
            Integer valueOf = Integer.valueOf(i);
            HashMap hashMap = this.navDependencies;
            if (hashMap.get(valueOf) == null) {
                hashMap.put(Integer.valueOf(i), new NavBarModuleDependency());
                putModule(Context.class, context, i);
            }
            Integer valueOf2 = Integer.valueOf(i);
            HashMap hashMap2 = this.navStateManager;
            if (hashMap2.get(valueOf2) == null) {
                display = display2;
                hashMap2.put(Integer.valueOf(i), new NavBarStateManager(context, this, this.settingsHelper, this.interactorFactory, this.logWrapper, this.layoutProviderContainer, this.navBarRemoteViewManager, null, 128, null));
            } else {
                display = display2;
                if (i == 0 && (navBarStateManager = (NavBarStateManager) hashMap2.get(Integer.valueOf(i))) != null) {
                    navBarStateManager.onNavigationBarCreated();
                }
            }
            if (BasicRune.NAVBAR_REMOTEVIEW) {
                NavBarRemoteViewManager navBarRemoteViewManager = this.navBarRemoteViewManager;
                navBarRemoteViewManager.navBarStore = this;
                putModule(NavBarRemoteViewManager.class, navBarRemoteViewManager, i);
            }
            Point point = new Point();
            display.getRealSize(point);
            NavBarStateManager navBarStateManager2 = (NavBarStateManager) hashMap2.get(Integer.valueOf(i));
            if (navBarStateManager2 != null) {
                navBarStateManager2.states.displaySize = point;
                unit = Unit.INSTANCE;
                if (unit != null) {
                    String m31m = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Failed to add display dependencies because display ", i, " returns null.");
                    StoreLogUtil storeLogUtil = this.logWrapper;
                    if (storeLogUtil.allowLogging) {
                        storeLogUtil.printLog(storeLogUtil.lastDepth, m31m);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        unit = null;
        if (unit != null) {
        }
    }

    public final void putModule(Type type, Object obj, int i) {
        HashMap hashMap = this.navDependencies;
        if (obj == null) {
            NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) hashMap.get(Integer.valueOf(i));
            if (navBarModuleDependency != null) {
                navBarModuleDependency.modules.remove(((Class) type).getTypeName());
                return;
            }
            return;
        }
        NavBarModuleDependency navBarModuleDependency2 = (NavBarModuleDependency) hashMap.get(Integer.valueOf(i));
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

    public final void handleEvent(Object obj, EventTypeFactory.EventType eventType) {
        if (!(eventType instanceof EventTypeFactory.EventType.ResetBottomGestureHintVI ? true : eventType instanceof EventTypeFactory.EventType.MoveBottomGestureHintDistance ? true : eventType instanceof EventTypeFactory.EventType.StartBottomGestureHintVI ? true : eventType instanceof EventTypeFactory.EventType.OnSetRemoteView ? true : eventType instanceof EventTypeFactory.EventType.OnUpdateRemoteViewContainer ? true : eventType instanceof EventTypeFactory.EventType.OnInvalidateRemoteViews)) {
            handleEvent(obj, eventType, 0);
            return;
        }
        Iterator it = this.navDependencies.keySet().iterator();
        while (it.hasNext()) {
            handleEvent(obj, eventType, ((Integer) it.next()).intValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v10, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r9v12, types: [com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$5, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r9v14, types: [com.android.systemui.navigationbar.interactor.LegacyDesktopModeInteractor$addCallback$2] */
    public final Object handleEvent(final Object obj, final EventTypeFactory.EventType eventType, int i, Object obj2) {
        final DesktopModeInteractor desktopModeInteractor;
        LegacyDesktopModeInteractor legacyDesktopModeInteractor;
        boolean z;
        boolean z2;
        Iterator it;
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = i;
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarCreated) {
            EventTypeFactory.EventType.OnNavBarCreated onNavBarCreated = (EventTypeFactory.EventType.OnNavBarCreated) eventType;
            putModule(CentralSurfaces.class, onNavBarCreated.centralSurfaces, i);
            putModule(NavigationBar.class, onNavBarCreated.navigationBar, ref$IntRef.element);
        } else {
            boolean z3 = eventType instanceof EventTypeFactory.EventType.OnNavBarConfigChanged;
            SamsungNavigationBarProxy samsungNavigationBarProxy = this.navBarProxy;
            if (z3) {
                EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) eventType;
                onNavBarConfigChanged.canMove = (onNavBarConfigChanged.canMove & (!DeviceType.isTablet())) | (DeviceState.isSubDisplay(this.mainContext) & (onNavBarConfigChanged.navigationMode != 2));
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
                    putModule(NavigationBarTransitions.class, onNavBarAttachedToWindow.navbarTransitions, ref$IntRef.element);
                    Context context = (Context) getModule(Context.class, 0);
                    GestureHintAnimator.Factory factory = this.hintAnimatorFactory;
                    factory.getClass();
                    putModule(GestureHintAnimator.class, new GestureHintAnimator(context, factory.mLogWrapper), ref$IntRef.element);
                    GestureHintAnimator gestureHintAnimator = (GestureHintAnimator) getModule(GestureHintAnimator.class, ref$IntRef.element);
                    gestureHintAnimator.navigationMode = gestureHintAnimator.navigationModeController.addListener(gestureHintAnimator);
                    if (BasicRune.NAVBAR_DESKTOP && (legacyDesktopModeInteractor = (LegacyDesktopModeInteractor) interactorFactory.get(LegacyDesktopModeInteractor.class)) != null) {
                        final Consumer consumer = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$handleEvent$2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj3) {
                                NavBarStoreImpl navBarStoreImpl = NavBarStoreImpl.this;
                                navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnDesktopModeChanged(obj3 instanceof SemDesktopModeState ? (SemDesktopModeState) obj3 : null));
                            }
                        };
                        LegacyDesktopModeInteractor$addCallback$2 legacyDesktopModeInteractor$addCallback$2 = legacyDesktopModeInteractor.callback;
                        if (legacyDesktopModeInteractor$addCallback$2 != null) {
                            ((ArrayList) ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).mCallbacks).remove(legacyDesktopModeInteractor$addCallback$2);
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
                        DesktopManager desktopManager = (DesktopManager) Dependency.get(DesktopManager.class);
                        if (desktopManager != null) {
                            DesktopManagerImpl desktopManagerImpl = (DesktopManagerImpl) desktopManager;
                            desktopManagerImpl.registerCallback(legacyDesktopModeInteractor.callback);
                            consumer.accept(desktopManagerImpl.getSemDesktopModeState());
                        }
                    }
                    if (BasicRune.NAVBAR_NEW_DEX && (desktopModeInteractor = (DesktopModeInteractor) interactorFactory.get(DesktopModeInteractor.class)) != null) {
                        final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$handleEvent$3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj3) {
                                NavBarStoreImpl navBarStoreImpl = NavBarStoreImpl.this;
                                navBarStoreImpl.handleEvent(navBarStoreImpl, new EventTypeFactory.EventType.OnNewDexModeChanged(((Boolean) obj3).booleanValue()));
                                NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                                navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
                            }
                        };
                        DesktopModeInteractor$addCallback$2 desktopModeInteractor$addCallback$2 = desktopModeInteractor.broadcastReceiver;
                        if (desktopModeInteractor$addCallback$2 != null) {
                            desktopModeInteractor.broadcastDispatcher.unregisterReceiver(desktopModeInteractor$addCallback$2);
                        }
                        ?? r9 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context2, Intent intent) {
                                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.USER_UNLOCKED")) {
                                    DesktopModeInteractor desktopModeInteractor2 = DesktopModeInteractor.this;
                                    desktopModeInteractor2.userUnlocked = true;
                                    Consumer consumer3 = consumer2;
                                    if (consumer3 != null) {
                                        consumer3.accept(Boolean.valueOf(desktopModeInteractor2.isEnabled()));
                                    }
                                }
                            }
                        };
                        BroadcastDispatcher.registerReceiverWithHandler$default(desktopModeInteractor.broadcastDispatcher, r9, desktopModeInteractor.intentFilter, desktopModeInteractor.bgHandler, UserHandle.ALL, 48);
                        desktopModeInteractor.broadcastReceiver = r9;
                        DesktopModeInteractor$addCallback$5 desktopModeInteractor$addCallback$5 = desktopModeInteractor.callback;
                        SettingsHelper settingsHelper = desktopModeInteractor.settingsHelper;
                        if (desktopModeInteractor$addCallback$5 != null) {
                            settingsHelper.unregisterCallback(desktopModeInteractor$addCallback$5);
                        }
                        ?? r92 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$5
                            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                            public final void onChanged(Uri uri) {
                                Consumer consumer3 = consumer2;
                                if (consumer3 != null) {
                                    consumer3.accept(Boolean.valueOf(desktopModeInteractor.isEnabled()));
                                }
                            }
                        };
                        desktopModeInteractor.callback = r92;
                        settingsHelper.registerCallback(r92, Settings.System.getUriFor("new_dex"));
                        consumer2.accept(Boolean.valueOf(desktopModeInteractor.isEnabled()));
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
                    putModule(NavigationBarView.class, null, ref$IntRef.element);
                    putModule(NavigationBarTransitions.class, null, ref$IntRef.element);
                    GestureHintAnimator gestureHintAnimator2 = (GestureHintAnimator) getModule(GestureHintAnimator.class, ref$IntRef.element);
                    gestureHintAnimator2.navigationModeController.removeListener(gestureHintAnimator2);
                    putModule(GestureHintAnimator.class, null, ref$IntRef.element);
                    if (DeviceType.isTablet() && !getNavStateManager(0).isNavBarHiddenByKnox()) {
                        SysUiState sysUiState = this.sysUiFlagContainer;
                        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO, false);
                        sysUiState.commitUpdate(ref$IntRef.element);
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnLightBarControllerCreated) {
                    putModule(LightBarController.class, ((EventTypeFactory.EventType.OnLightBarControllerCreated) eventType).lightBarController, i);
                    NavigationBarTransitions navigationBarTransitions = (NavigationBarTransitions) getModule(NavigationBarTransitions.class, ref$IntRef.element);
                    Object obj3 = interactorFactory.get(ColorSetting.class);
                    Intrinsics.checkNotNull(obj3);
                    navigationBarTransitions.mBarBackground.updateOpaqueColor(((ColorSetting) obj3).getNavigationBarColor());
                } else if (eventType instanceof EventTypeFactory.EventType.OnRotationLockedChanged) {
                    boolean z5 = ((EventTypeFactory.EventType.OnRotationLockedChanged) eventType).rotationLocked;
                    samsungNavigationBarProxy.rotationLocked = z5;
                    Iterator it2 = ((ArrayList) samsungNavigationBarProxy.rotationLockCallback).iterator();
                    while (it2.hasNext()) {
                        ((Consumer) it2.next()).accept(Boolean.valueOf(z5));
                    }
                } else if (eventType instanceof EventTypeFactory.EventType.OnNavBarTransitionModeChanged) {
                    samsungNavigationBarProxy.navbarTransitionMode = ((EventTypeFactory.EventType.OnNavBarTransitionModeChanged) eventType).transitionMode;
                }
            }
        }
        List<Band> list = (List) this.packs.stream().flatMap(new Function() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$handleEvent$allBands$1
            @Override // java.util.function.Function
            public final Object apply(Object obj4) {
                return ((BandAidPack) obj4).getBands().stream();
            }
        }).filter(new Predicate() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$handleEvent$filteredBands$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj4) {
                Object obj5;
                Object obj6;
                Band band = (Band) obj4;
                NavBarStoreImpl navBarStoreImpl = NavBarStoreImpl.this;
                Object obj7 = obj;
                EventTypeFactory.EventType eventType2 = eventType;
                int i2 = ref$IntRef.element;
                navBarStoreImpl.getClass();
                BandAid bandAid = band.bandAidDependency;
                Object obj8 = null;
                Boolean valueOf = bandAid != null ? Boolean.valueOf(bandAid.getEnabled()) : null;
                Intrinsics.checkNotNull(valueOf);
                if (!valueOf.booleanValue() || !band.runeDependency) {
                    return false;
                }
                String str = band.sPluginTag;
                if (str.length() > 0) {
                    return StringsKt__StringsKt.contains(eventType2.getClass().getTypeName(), str, false);
                }
                int i3 = band.targetDisplayId;
                if (i3 != -1 && i3 != i2) {
                    return false;
                }
                Iterator it3 = band.targetEvents.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        obj5 = null;
                        break;
                    }
                    obj5 = it3.next();
                    if (Intrinsics.areEqual(((Type) obj5).getTypeName(), eventType2.getClass().getTypeName())) {
                        break;
                    }
                }
                if (((Type) obj5) == null) {
                    return false;
                }
                Iterator it4 = band.targetModules.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        obj6 = null;
                        break;
                    }
                    obj6 = it4.next();
                    Type type = (Type) obj6;
                    if (StringsKt__StringsKt.contains(type.toString(), NavBarReflectUtil.class.getTypeName(), false) || Intrinsics.areEqual(obj7.getClass().getTypeName(), type.getTypeName()) || (obj7.getClass().getEnclosingClass() != null && Intrinsics.areEqual(obj7.getClass().getEnclosingClass().getTypeName(), type.getTypeName()))) {
                        break;
                    }
                }
                if (((Type) obj6) == null) {
                    return false;
                }
                Iterator it5 = band.moduleDependencies.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        break;
                    }
                    Object next = it5.next();
                    Type type2 = (Type) next;
                    NavBarModuleDependency navBarModuleDependency = (NavBarModuleDependency) navBarStoreImpl.navDependencies.get(Integer.valueOf(i2));
                    if ((navBarModuleDependency != null ? navBarModuleDependency.modules.get(type2.getTypeName()) : null) == null) {
                        obj8 = next;
                        break;
                    }
                }
                return ((Type) obj8) == null;
            }
        }).sorted(new Comparator() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$handleEvent$filteredBands$2
            @Override // java.util.Comparator
            public final int compare(Object obj4, Object obj5) {
                return ((Band) obj4).priority >= ((Band) obj5).priority ? 1 : -1;
            }
        }).collect(Collectors.toList());
        boolean z6 = this.handleEventLoggingEnabled;
        int i2 = this.loggingDepth;
        int i3 = ref$IntRef.element;
        StoreLogUtil storeLogUtil = this.logWrapper;
        storeLogUtil.lastDepth = i2;
        if (storeLogUtil.loggingStarted) {
            z = !(eventType instanceof EventTypeFactory.EventType.OnInvalidateRemoteViews ? true : eventType instanceof EventTypeFactory.EventType.OnNavBarStyleChanged ? true : eventType instanceof EventTypeFactory.EventType.OnSetDisableFlags ? true : eventType instanceof EventTypeFactory.EventType.GetBarLayoutParams ? true : eventType instanceof EventTypeFactory.EventType.OnNavBarIconMarquee ? true : eventType instanceof EventTypeFactory.EventType.OnUpdateDarkIntensity ? true : eventType instanceof EventTypeFactory.EventType.GetDeadZoneSize ? true : eventType instanceof EventTypeFactory.EventType.OnUpdateRegionSamplingListener ? true : eventType instanceof EventTypeFactory.EventType.GetNavBarInsets ? true : eventType instanceof EventTypeFactory.EventType.GetImeInsets ? true : eventType instanceof EventTypeFactory.EventType.GetMandatoryInsets ? true : eventType instanceof EventTypeFactory.EventType.MoveBottomGestureHintDistance);
            storeLogUtil.allowLogging = z;
        } else {
            z = false;
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            for (int i4 = 0; i4 < i2; i4++) {
                sb.append("--");
            }
            sb.append("handleEvent(" + i3 + ") ");
            sb.append(eventType.toString());
            sb.append(" [Module] ");
            sb.append(obj.getClass().getSimpleName());
            storeLogUtil.logWrapper.m98d("Store", sb.toString());
            z2 = true;
        } else {
            z2 = false;
        }
        this.handleEventLoggingEnabled = z2;
        this.loggingDepth++;
        NavBarStateManager navStateManager = getNavStateManager(ref$IntRef.element);
        Band.Kit kit = new Band.Kit(eventType, navStateManager, NavBarStateManager.States.copy$default(navStateManager.states), ref$IntRef.element);
        Iterator it3 = list.iterator();
        Object obj4 = null;
        while (it3.hasNext()) {
            Band band = (Band) it3.next();
            if (this.handleEventLoggingEnabled) {
                int i5 = this.loggingDepth;
                BandAid bandAid = band.bandAidDependency;
                it = it3;
                storeLogUtil.printLog(i5, "[Band]" + (bandAid != null ? bandAid.name() : null));
            } else {
                it = it3;
            }
            this.loggingDepth++;
            Function function = band.patchAction;
            Object apply = function != null ? function.apply(kit) : null;
            if (!(apply instanceof Unit)) {
                obj4 = apply == null ? null : apply;
            }
            this.loggingDepth--;
            it3 = it;
        }
        navStateManager.updateStateFromEvent(eventType);
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
        int i7 = this.loggingDepth - 1;
        this.loggingDepth = i7;
        if (obj4 != null && !(obj4 instanceof NavBarStore)) {
            if (this.handleEventLoggingEnabled) {
                storeLogUtil.printLog(i7, "handleEvent(" + ref$IntRef.element + ") retValue= " + obj4);
            }
            this.handleEventLoggingEnabled = z6;
            storeLogUtil.allowLogging = false;
            storeLogUtil.lastDepth = 0;
            return obj4;
        }
        if (!(obj2 instanceof Unit)) {
            if (this.handleEventLoggingEnabled) {
                storeLogUtil.printLog(i7, "handleEvent(" + ref$IntRef.element + ") ret defaultValue= " + obj2);
            }
            this.handleEventLoggingEnabled = z6;
            storeLogUtil.allowLogging = false;
            storeLogUtil.lastDepth = 0;
        }
        return obj2;
    }
}
