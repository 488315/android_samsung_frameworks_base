package com.android.systemui.shade;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class SecPanelSplitHelper implements ShadeExpansionListener, SettingsHelper.OnChangedCallback, StatusBarStateController.StateListener, LockscreenShadeTransitionController.Callback, ConfigurationController.ConfigurationListener {
    public static boolean isEnabled;
    public final Context context;
    public int currentOrientation;
    public int currentState;
    public float draggedFraction;
    public boolean enabled;
    public final Executor executor;
    public final HeadsUpManager headsUpManager;
    public SecNotificationPanelViewController$panelSplitHelper$1$1 interceptCallback;
    public boolean isOnceOverExpanded;
    public boolean onceOverSlide;
    public boolean overHalfdraggedFraction;
    public float overSlideAmount;
    public boolean panelExpanded;
    public View panelRootView;
    public final PanelSlideEventHandler panelSlideEventHandler;
    public QS qs;
    public SecQSImplAnimatorManager qsAnimatorManager;
    public View qsFrame;
    public View qsScrollView;
    public final QuickSALog quickSALog;
    public final QuickSettingsControllerImpl quickSettingsControllerImpl;
    public final ShadeRepository repository;
    public boolean reversed;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public View shadeRootView;
    public PanelSlideEventHandler.Direction shouldQsDownInLockscreen;
    public final SplitStateRepository splitStateRepository;
    public int stateOnDown;
    public int stateToChange;
    public MotionEvent synthesizedActionDown;
    public final UserTracker.Callback userChanged;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final Uri SPLIT_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL);
    public static final Uri REVERSED_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED);
    public static final Uri USER_CHANGED = Uri.parse("USER_CHANGED");
    public static final Uri RATIO_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_RATIO);
    public final Lazy settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new SecPanelSplitHelper$$ExternalSyntheticLambda0(0));
    public final Lazy statusBarStateController$delegate = LazyKt__LazyJVMKt.lazy(new SecPanelSplitHelper$$ExternalSyntheticLambda0(1));
    public final CopyOnWriteArrayList expansionListeners = new CopyOnWriteArrayList();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class QuickSALog {
        public final Lazy panelSAStatusLogInteractor$delegate = LazyKt__LazyJVMKt.lazy(new SecPanelSplitHelper$$ExternalSyntheticLambda0(2));
        public boolean isDone = true;
    }

    public SecPanelSplitHelper(Context context, ShadeExpansionStateManager shadeExpansionStateManager, QuickSettingsControllerImpl quickSettingsControllerImpl, CoroutineScope coroutineScope, ShadeRepository shadeRepository, LockscreenShadeTransitionController lockscreenShadeTransitionController, UserTracker userTracker, Executor executor, ConfigurationController configurationController, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecQSPanelResourcePicker secQSPanelResourcePicker, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, SplitStateRepository splitStateRepository, HeadsUpManager headsUpManager) {
        this.context = context;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.quickSettingsControllerImpl = quickSettingsControllerImpl;
        this.repository = shadeRepository;
        this.userTracker = userTracker;
        this.executor = executor;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.splitStateRepository = splitStateRepository;
        this.headsUpManager = headsUpManager;
        PanelSlideEventHandler panelSlideEventHandler = new PanelSlideEventHandler(context, shadeExpansionStateManager, quickSettingsControllerImpl, this);
        panelSlideEventHandler.panelSlideEventCallback = new SecPanelSplitHelper$panelSlideEventHandler$1$1(this);
        this.panelSlideEventHandler = panelSlideEventHandler;
        this.quickSALog = new QuickSALog();
        this.currentState = 2;
        this.stateToChange = 1;
        this.shouldQsDownInLockscreen = PanelSlideEventHandler.Direction.UNDECIDED;
        getSettingsHelper$2().registerCallback(this, SPLIT_URI, REVERSED_URI, RATIO_URI);
        this.reversed = getSettingsHelper$2().isPanelSplitReversed();
        boolean zIsPanelSplit = getSettingsHelper$2().isPanelSplit();
        panelSlideEventHandler.panelSplitEnabled = zIsPanelSplit;
        Lazy lazy = panelSlideEventHandler.statusBarStateController$delegate;
        Lazy lazy2 = panelSlideEventHandler.configurationController$delegate;
        ShadeExpansionStateManager shadeExpansionStateManager2 = panelSlideEventHandler.shadeExpansionStateManager;
        if (zIsPanelSplit) {
            panelSlideEventHandler.updateResource();
            shadeExpansionStateManager2.addExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).addCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy.getValue()).addCallback(panelSlideEventHandler);
        } else {
            shadeExpansionStateManager2.removeExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).removeCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy.getValue()).removeCallback(panelSlideEventHandler);
        }
        setEnabled$1(zIsPanelSplit);
        lockscreenShadeTransitionController.addCallback(this);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        FlowKt.launchIn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeRepositoryImpl.lockscreenShadeExpansion, shadeRepositoryImpl.legacyShadeExpansion, new SecPanelSplitHelper$2$1(this, null))), coroutineScope);
        shadeExpansionStateManager.addExpansionListener(this);
        getStatusBarStateController$2().addCallback(this);
        quickSettingsControllerImpl.mNotificationStackScrollLayoutController.mPanelSplitHelper = this;
        this.userChanged = new UserTracker.Callback() { // from class: com.android.systemui.shade.SecPanelSplitHelper$userChanged$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                SecPanelSplitHelper secPanelSplitHelper = this.this$0;
                SecQSImplAnimatorManager secQSImplAnimatorManager = secPanelSplitHelper.qsAnimatorManager;
                if (secQSImplAnimatorManager != null) {
                    secQSImplAnimatorManager.onUserSwitched(-2);
                }
                secPanelSplitHelper.onChanged(SecPanelSplitHelper.USER_CHANGED);
            }
        };
    }

    public static final boolean isEnabled() {
        Companion.getClass();
        return isEnabled;
    }

    public final void addListener(PanelTransitionStateListener panelTransitionStateListener) {
        this.expansionListeners.add(panelTransitionStateListener);
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(this.enabled, this.draggedFraction, this.currentState);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
    }

    public final float getMaxSlideDistance() {
        return DeviceState.getScreenWidth(this.context);
    }

    public final SettingsHelper getSettingsHelper$2() {
        return (SettingsHelper) this.settingsHelper$delegate.getValue();
    }

    public final StatusBarStateController getStatusBarStateController$2() {
        return (StatusBarStateController) this.statusBarStateController$delegate.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleTouch(MotionEvent motionEvent) {
        PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
        if (!panelSlideEventHandler.panelSplitEnabled) {
            return false;
        }
        float x = motionEvent.getX() - panelSlideEventHandler.initialX;
        float y = motionEvent.getY() - panelSlideEventHandler.initialY;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            if (panelSlideEventHandler.panelSliderIntercepted) {
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback;
                if (!Intrinsics.areEqual(secPanelSplitHelper$panelSlideEventHandler$1$1 != null ? Float.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.draggedFraction) : null, 1.0f)) {
                    SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12 = panelSlideEventHandler.panelSlideEventCallback;
                    if (!Intrinsics.areEqual(secPanelSplitHelper$panelSlideEventHandler$1$12 != null ? Float.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.draggedFraction) : null, 0.0f)) {
                        PanelSlideEventHandler.Direction direction = panelSlideEventHandler.direction;
                        PanelSlideEventHandler.Direction direction2 = PanelSlideEventHandler.Direction.DOWN;
                        if (direction == direction2) {
                            x = y;
                        }
                        VelocityTracker velocityTracker = panelSlideEventHandler.velocityTracker;
                        velocityTracker.computeCurrentVelocity(1000);
                        Pair pair = new Pair(Float.valueOf(velocityTracker.getXVelocity()), Float.valueOf(velocityTracker.getYVelocity()));
                        velocityTracker.clear();
                        float fFloatValue = ((Number) pair.component1()).floatValue();
                        float fFloatValue2 = ((Number) pair.component2()).floatValue();
                        if (panelSlideEventHandler.direction == direction2) {
                            fFloatValue = fFloatValue2;
                        }
                        if (motionEvent.getAction() == 1) {
                            panelSlideEventHandler.createSlideAnimatorAndRun(fFloatValue, x);
                        } else if (motionEvent.getAction() == 3) {
                            SecPanelSplitHelper secPanelSplitHelper = panelSlideEventHandler.secPanelSplitHelper;
                            if (secPanelSplitHelper.currentState == 2 && secPanelSplitHelper.panelSlideEventHandler.tracking) {
                            }
                        }
                    }
                }
            }
            panelSlideEventHandler.tracking = false;
            panelSlideEventHandler.fullyExpandedOnDown = false;
            panelSlideEventHandler.panelSliderIntercepted = false;
            panelSlideEventHandler.slidingInitialized = false;
            SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$13 = panelSlideEventHandler.panelSlideEventCallback;
            if (secPanelSplitHelper$panelSlideEventHandler$1$13 != null) {
                SecPanelSplitHelper secPanelSplitHelper2 = secPanelSplitHelper$panelSlideEventHandler$1$13.this$0;
                if (secPanelSplitHelper2.overSlideAmount != 0.0f && secPanelSplitHelper2.draggedFraction == 0.0f) {
                    secPanelSplitHelper2.springBack();
                }
                secPanelSplitHelper2.onceOverSlide = false;
            }
        } else if (actionMasked == 2) {
            panelSlideEventHandler.velocityTracker.addMovement(motionEvent);
            panelSlideEventHandler.updateDirection(x, y);
            if (panelSlideEventHandler.panelSliderIntercepted) {
                PanelSlideEventHandler.Direction direction3 = panelSlideEventHandler.direction;
                if (direction3 == PanelSlideEventHandler.Direction.DOWN) {
                    x = y;
                }
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$14 = panelSlideEventHandler.panelSlideEventCallback;
                if (secPanelSplitHelper$panelSlideEventHandler$1$14 != null) {
                    secPanelSplitHelper$panelSlideEventHandler$1$14.this$0.slide(x, direction3, panelSlideEventHandler.tracking);
                }
            }
        } else if (actionMasked == 3) {
        }
        int action = motionEvent.getAction();
        if ((2 != action ? Integer.valueOf(action) : null) != null) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("handleTouch: ", MotionEvent.actionToString(motionEvent.getAction()), ": FINAL: return true", "SecPanelSplitHelper");
        }
        return true;
    }

    public final boolean isQSState() {
        return this.currentState == 0;
    }

    public final boolean isReversed() {
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1;
        boolean z2 = this.reversed;
        return this.enabled && ((z && !z2) || (!z && z2));
    }

    public final boolean isShadeState() {
        return this.currentState == 1;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        if (uri != null) {
            if (uri.equals(SPLIT_URI) || uri.equals(REVERSED_URI) || uri.equals(USER_CHANGED) || uri.equals(RATIO_URI)) {
                if (!this.enabled) {
                    this.panelExpanded = QsAnimatorState.panelExpanded;
                }
                if (!uri.equals(USER_CHANGED) && ((!this.panelExpanded || QsAnimatorState.state == 1) && (secQSImplAnimatorManager = this.qsAnimatorManager) != null)) {
                    secQSImplAnimatorManager.onUserSwitched(-2);
                }
                setEnabled$1(getSettingsHelper$2().isPanelSplit());
                this.reversed = getSettingsHelper$2().isPanelSplitReversed();
                boolean z = this.enabled;
                ShadeExpansionStateManager shadeExpansionStateManager = this.shadeExpansionStateManager;
                if (z) {
                    shadeExpansionStateManager.addExpansionListener(this);
                    getStatusBarStateController$2().addCallback(this);
                    this.quickSettingsControllerImpl.mNotificationStackScrollLayoutController.mPanelSplitHelper = this;
                } else {
                    shadeExpansionStateManager.removeExpansionListener(this);
                    getStatusBarStateController$2().removeCallback(this);
                }
                PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
                panelSlideEventHandler.getClass();
                Log.d("SecPanelSplitHelper", "onChanged");
                panelSlideEventHandler.updateResource();
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        int i = this.currentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.currentOrientation = i2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onIntercept(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        SecNotificationPanelViewController$panelSplitHelper$1$1 secNotificationPanelViewController$panelSplitHelper$1$1;
        PanelSlideEventHandler.Direction direction;
        PanelSlideEventHandler.Direction direction2;
        PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
        if (panelSlideEventHandler.panelSplitEnabled) {
            if (panelSlideEventHandler.panelSliderIntercepted) {
                if (motionEvent.getAction() == 2) {
                    Log.d("SecPanelSplitHelper", "onIntercept: ACTION_MOVE return true -> panelSliderIntercepted is true");
                }
                return true;
            }
            float x = motionEvent.getX() - panelSlideEventHandler.initialX;
            float y = motionEvent.getY() - panelSlideEventHandler.initialY;
            float fAbs = Math.abs(x);
            float fAbs2 = Math.abs(y);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                panelSlideEventHandler.initiateSlide(motionEvent);
            } else if (actionMasked == 1) {
                if (panelSlideEventHandler.panelSliderIntercepted) {
                    SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback;
                    if (!Intrinsics.areEqual(secPanelSplitHelper$panelSlideEventHandler$1$1 != null ? Float.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.draggedFraction) : null, 1.0f)) {
                        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12 = panelSlideEventHandler.panelSlideEventCallback;
                        if (!Intrinsics.areEqual(secPanelSplitHelper$panelSlideEventHandler$1$12 != null ? Float.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.draggedFraction) : null, 0.0f)) {
                            PanelSlideEventHandler.Direction direction3 = panelSlideEventHandler.direction;
                            PanelSlideEventHandler.Direction direction4 = PanelSlideEventHandler.Direction.DOWN;
                            if (direction3 == direction4) {
                                x = y;
                            }
                            VelocityTracker velocityTracker = panelSlideEventHandler.velocityTracker;
                            velocityTracker.computeCurrentVelocity(1000);
                            Pair pair = new Pair(Float.valueOf(velocityTracker.getXVelocity()), Float.valueOf(velocityTracker.getYVelocity()));
                            velocityTracker.clear();
                            float fFloatValue = ((Number) pair.component1()).floatValue();
                            float fFloatValue2 = ((Number) pair.component2()).floatValue();
                            if (panelSlideEventHandler.direction == direction4) {
                                fFloatValue = fFloatValue2;
                            }
                            if (motionEvent.getAction() == 1) {
                                panelSlideEventHandler.createSlideAnimatorAndRun(fFloatValue, x);
                            }
                            panelSlideEventHandler.velocityTracker.clear();
                        }
                    }
                }
                panelSlideEventHandler.tracking = false;
                panelSlideEventHandler.fullyExpandedOnDown = false;
                panelSlideEventHandler.isInChangeSpotOnDown = false;
                panelSlideEventHandler.isInSlidableAreaOnDown = false;
                panelSlideEventHandler.panelSliderIntercepted = false;
                panelSlideEventHandler.isInGestureArea = false;
                panelSlideEventHandler.isInQsScrollerTopMarginArea = false;
            } else if (actionMasked == 2) {
                panelSlideEventHandler.velocityTracker.addMovement(motionEvent);
                float f = panelSlideEventHandler.touchSlop;
                if (fAbs > f || fAbs2 > f) {
                    panelSlideEventHandler.updateDirection(x, y);
                    if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && ((direction2 = panelSlideEventHandler.direction) == PanelSlideEventHandler.Direction.LEFT || direction2 == PanelSlideEventHandler.Direction.RIGHT)) {
                        Companion.getClass();
                        boolean z3 = isEnabled;
                        if (z3) {
                            Log.d("SecPanelSplitHelper", "onIntercept: TabletModel direction = " + panelSlideEventHandler.direction + ", isEnabled = " + z3);
                            return false;
                        }
                    }
                    SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$13 = panelSlideEventHandler.panelSlideEventCallback;
                    boolean z4 = secPanelSplitHelper$panelSlideEventHandler$1$13 != null && secPanelSplitHelper$panelSlideEventHandler$1$13.this$0.currentState == 0;
                    if (z4 && panelSlideEventHandler.direction == PanelSlideEventHandler.Direction.DOWN && panelSlideEventHandler.canScrollDownOnDown && !panelSlideEventHandler.isInQsScrollerTopMarginArea) {
                        Log.d("SecPanelSplitHelper", "onIntercept: ACTION_MOVE: return false: direction == DOWN && canScrollDownOnDown && !isInQsScrollerTopMarginArea");
                        return false;
                    }
                    if (fAbs2 <= f || panelSlideEventHandler.direction != PanelSlideEventHandler.Direction.DOWN) {
                        z = false;
                        if (fAbs > f || !((direction = panelSlideEventHandler.direction) == PanelSlideEventHandler.Direction.RIGHT || direction == PanelSlideEventHandler.Direction.LEFT)) {
                            z2 = false;
                            if (z4) {
                                z2 = z2 && !panelSlideEventHandler.isInSlidableAreaOnDown;
                            }
                            if (!panelSlideEventHandler.panelSliderIntercepted && panelSlideEventHandler.fullyExpandedOnDown && panelSlideEventHandler.panelExpanded && (z || z2)) {
                                if (!(QsAnimatorState.state != 1) && panelSlideEventHandler.panelExpandFraction > 0.9f && !panelSlideEventHandler.isInGestureArea) {
                                    panelSlideEventHandler.panelSliderIntercepted = true;
                                    secNotificationPanelViewController$panelSplitHelper$1$1 = panelSlideEventHandler.interceptCallback;
                                    if (secNotificationPanelViewController$panelSplitHelper$1$1 != null) {
                                        secNotificationPanelViewController$panelSplitHelper$1$1.run();
                                    }
                                    Log.d("SecPanelSplitHelper", "onIntercept: ACTION_MOVE: return true: direction: " + panelSlideEventHandler.direction);
                                    panelSlideEventHandler.tracking = true;
                                    return true;
                                }
                            }
                            if (panelSlideEventHandler.panelFullyExpanded) {
                                StringBuilder sb = new StringBuilder();
                                StringBuilder sb2 = panelSlideEventHandler.panelSliderIntercepted ? sb : null;
                                if (sb2 != null) {
                                    sb2.append("panelSliderIntercepted true, ");
                                }
                                StringBuilder sb3 = !panelSlideEventHandler.fullyExpandedOnDown ? sb : null;
                                if (sb3 != null) {
                                    sb3.append("fullyExpandedOnDown false, ");
                                }
                                StringBuilder sb4 = !panelSlideEventHandler.panelExpanded ? sb : null;
                                if (sb4 != null) {
                                    sb4.append("panelExpanded false, ");
                                }
                                if (!z && !z2) {
                                    if (panelSlideEventHandler.direction == PanelSlideEventHandler.Direction.DOWN) {
                                        StringBuilder sb5 = fAbs2 <= f ? sb : null;
                                        if (sb5 != null) {
                                            sb5.append("isVerticalAllowed false by { height <= touchSlop }, ");
                                        }
                                        StringBuilder sb6 = !panelSlideEventHandler.isInChangeSpotOnDown ? sb : null;
                                        if (sb6 != null) {
                                            sb6.append("isVerticalAllowed false by { !isInChangeSpotOnDown }, ");
                                        }
                                    }
                                    PanelSlideEventHandler.Direction direction5 = panelSlideEventHandler.direction;
                                    if (direction5 == PanelSlideEventHandler.Direction.LEFT || direction5 == PanelSlideEventHandler.Direction.RIGHT) {
                                        StringBuilder sb7 = fAbs <= f ? sb : null;
                                        if (sb7 != null) {
                                            sb7.append("isHorizontalAllowed false by { width <= touchSlop }, ");
                                        }
                                        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$14 = panelSlideEventHandler.panelSlideEventCallback;
                                        StringBuilder sb8 = (secPanelSplitHelper$panelSlideEventHandler$1$14 == null || !secPanelSplitHelper$panelSlideEventHandler$1$14.this$0.isOnceOverExpanded) ? null : sb;
                                        if (sb8 != null) {
                                            sb8.append("isHorizontalAllowed false by { panelSlideEventCallback?.isOnCeOverExpanded }, ");
                                        }
                                    }
                                }
                                StringBuilder sb9 = QsAnimatorState.state == 1 ? sb : null;
                                if (sb9 != null) {
                                    sb9.append("isKeyguardShowing() true, ");
                                }
                                StringBuilder sb10 = panelSlideEventHandler.panelExpandFraction <= 0.9f ? sb : null;
                                if (sb10 != null) {
                                    sb10.append("panelExpandFraction <= PANEL_EXPANDED_FRACTION_THRESHOLD, ");
                                }
                                StringBuilder sb11 = panelSlideEventHandler.isInGestureArea ? sb : null;
                                if (sb11 != null) {
                                    sb11.append("isInGestureArea true, ");
                                }
                                StringBuilder sb12 = panelSlideEventHandler.isInQsScrollerTopMarginArea ? sb : null;
                                if (sb12 != null) {
                                    sb12.append("isInQsScrollerTopMarginArea true, ");
                                }
                                String string = sb.toString();
                                StringBuilder sb13 = panelSlideEventHandler.logBuilder;
                                if (Intrinsics.areEqual(sb13.toString(), string)) {
                                    string = null;
                                }
                                if (string != null) {
                                    sb13.setLength(0);
                                    sb13.append(string);
                                    Log.d("SecPanelSplitHelper", "##############  not intercepted " + ((Object) sb13));
                                }
                            }
                        } else {
                            SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$15 = panelSlideEventHandler.panelSlideEventCallback;
                            if ((secPanelSplitHelper$panelSlideEventHandler$1$15 == null || secPanelSplitHelper$panelSlideEventHandler$1$15.this$0.isOnceOverExpanded) ? false : true) {
                                z2 = true;
                            }
                            if (z4) {
                            }
                            if (!panelSlideEventHandler.panelSliderIntercepted) {
                                if (!(QsAnimatorState.state != 1)) {
                                    panelSlideEventHandler.panelSliderIntercepted = true;
                                    secNotificationPanelViewController$panelSplitHelper$1$1 = panelSlideEventHandler.interceptCallback;
                                    if (secNotificationPanelViewController$panelSplitHelper$1$1 != null) {
                                    }
                                    Log.d("SecPanelSplitHelper", "onIntercept: ACTION_MOVE: return true: direction: " + panelSlideEventHandler.direction);
                                    panelSlideEventHandler.tracking = true;
                                    return true;
                                }
                            }
                            if (panelSlideEventHandler.panelFullyExpanded) {
                            }
                        }
                    } else {
                        if (panelSlideEventHandler.initialY < ((float) ((ShadeHeaderController) panelSlideEventHandler.shadeHeaderController$delegate.getValue()).header.getMeasuredHeight()) ? panelSlideEventHandler.isInChangeSpotOnDown : false) {
                            z = true;
                        }
                        if (fAbs > f) {
                            z2 = false;
                            if (z4) {
                            }
                            if (!panelSlideEventHandler.panelSliderIntercepted) {
                            }
                            if (panelSlideEventHandler.panelFullyExpanded) {
                            }
                        }
                    }
                }
            } else if (actionMasked == 3) {
            }
            int action = motionEvent.getAction();
            if ((2 == action ? null : Integer.valueOf(action)) != null) {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onIntercept: ", MotionEvent.actionToString(motionEvent.getAction()), ": FINAL: return false", "SecPanelSplitHelper");
            }
        }
        return false;
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = this.panelExpanded;
        boolean z2 = shadeExpansionChangeEvent.expanded;
        if (z2 == z) {
            return;
        }
        this.panelExpanded = z2;
        if (isEnabled && z2 && ((HeadsUpManagerImpl) this.headsUpManager).mHasPinnedNotification) {
            slide$1(1);
        }
        if (z2) {
            return;
        }
        int i = this.currentState;
        int i2 = PanelTransitionState.$r8$clinit;
        if (i != 3) {
            slide$1(1);
        }
        QsAnimatorState.isSliding = false;
        this.onceOverSlide = false;
        this.isOnceOverExpanded = false;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (i == 1) {
            this.quickSettingsControllerImpl.closeQs();
            return;
        }
        if (i == 2 && this.currentState == 2) {
            if (!isReversed() || this.secQsUiDisplayModeInteractor.isTablet()) {
                slide$1(this.stateToChange);
            } else {
                slide$1(this.stateOnDown);
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStatePostChange() {
        View view;
        if (this.currentState != 0 || (view = this.shadeRootView) == null) {
            return;
        }
        view.setVisibility(4);
    }

    public final void removeListener(PanelTransitionStateListener panelTransitionStateListener) {
        this.expansionListeners.remove(panelTransitionStateListener);
    }

    public final void setDraggedFraction(float f) {
        this.draggedFraction = f;
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(this.enabled, f, this.currentState);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.secQsUiDisplayModeInteractor;
        if (secQsUiDisplayModeInteractor.isTablet()) {
            if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && secQsUiDisplayModeInteractor.isTablet() && getSettingsHelper$2().isRemoveAnimation() && this.draggedFraction >= 0.5f) {
                this.overHalfdraggedFraction = true;
            }
            Integer numValueOf = Integer.valueOf((this.draggedFraction <= 0.5f ? this.stateOnDown != 1 : this.stateOnDown == 1) ? 0 : 1);
            SplitStateRepository splitStateRepository = this.splitStateRepository;
            splitStateRepository._isQsState.updateState(null, numValueOf);
            float f2 = this.draggedFraction;
            splitStateRepository._transitioning.updateState(null, Boolean.valueOf(f2 > 0.5f && f2 < 1.0f));
        }
    }

    public final void setEnabled$1(boolean z) {
        isEnabled = z;
        this.enabled = z;
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(z, this.draggedFraction, this.currentState);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("enabled = ", ", panelExpanded = ", "SecPanelSplitHelper", this.enabled, this.panelExpanded);
        boolean z2 = this.enabled;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.quickSettingsControllerImpl;
        if (z2) {
            slide$1((!this.panelExpanded || QsAnimatorState.state == 1) ? 1 : 0);
        } else {
            if (this.secQsUiDisplayModeInteractor.isTablet()) {
                this.splitStateRepository._isQsState.updateState(null, -1);
            }
            slide$1(3);
            ((ShadeRepositoryImpl) this.repository)._legacyExpandImmediate.updateState(null, Boolean.FALSE);
            QS qs = quickSettingsControllerImpl.mQs;
            if (qs != null) {
                qs.setOverScrollAmount(0);
            }
        }
        quickSettingsControllerImpl.mPanelSplitEnabled = z;
        PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
        panelSlideEventHandler.panelSplitEnabled = z;
        Lazy lazy = panelSlideEventHandler.statusBarStateController$delegate;
        Lazy lazy2 = panelSlideEventHandler.configurationController$delegate;
        ShadeExpansionStateManager shadeExpansionStateManager = panelSlideEventHandler.shadeExpansionStateManager;
        if (!z) {
            shadeExpansionStateManager.removeExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).removeCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy.getValue()).removeCallback(panelSlideEventHandler);
        } else {
            panelSlideEventHandler.updateResource();
            shadeExpansionStateManager.addExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).addCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy.getValue()).addCallback(panelSlideEventHandler);
        }
    }

    @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
    public final void setTransitionToFullShadeAmount(float f) {
        if (this.enabled && getStatusBarStateController$2().getState() == 1 && this.shouldQsDownInLockscreen != PanelSlideEventHandler.Direction.UNDECIDED) {
            if (!isReversed()) {
                slide(getMaxSlideDistance() * f, PanelSlideEventHandler.Direction.DOWN, false);
            } else if (this.shouldQsDownInLockscreen == PanelSlideEventHandler.Direction.LEFT) {
                slide(getMaxSlideDistance() * f, PanelSlideEventHandler.Direction.DOWN, false);
            }
        }
    }

    public final boolean shouldQSDown(MotionEvent motionEvent) {
        View view;
        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1;
        boolean z = this.enabled;
        int i = 0;
        if (z && this.synthesizedActionDown == null) {
            PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
            panelSlideEventHandler.getClass();
            if (motionEvent.getActionMasked() == 0) {
                int i2 = QsAnimatorState.state;
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12 = panelSlideEventHandler.panelSlideEventCallback;
                Integer numValueOf = secPanelSplitHelper$panelSlideEventHandler$1$12 != null ? Integer.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.currentState) : null;
                Log.d("SecPanelSplitHelper", "shouldQSDown statusBarState = " + i2 + ", state = " + numValueOf + " displayWidthOfDivider = " + panelSlideEventHandler.displayWidthOfDivider + " x = " + motionEvent.getX() + " y = " + motionEvent.getY() + " statusBarHeight = " + SystemBarUtils.getStatusBarHeight(panelSlideEventHandler.context));
                boolean zIsReversed = panelSlideEventHandler.secPanelSplitHelper.isReversed();
                if (((HeadsUpManagerImpl) ((HeadsUpManager) panelSlideEventHandler.headsUpManager$delegate.getValue())).mHasPinnedNotification) {
                    SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$13 = panelSlideEventHandler.panelSlideEventCallback;
                    if (secPanelSplitHelper$panelSlideEventHandler$1$13 != null) {
                        SecPanelSplitHelper secPanelSplitHelper = secPanelSplitHelper$panelSlideEventHandler$1$13.this$0;
                        if (secPanelSplitHelper.currentState == 0 && secPanelSplitHelper$panelSlideEventHandler$1$13 != null) {
                            secPanelSplitHelper.slide$1(1);
                        }
                    }
                    return true;
                }
                if (motionEvent.getY() > SystemBarUtils.getStatusBarHeight(panelSlideEventHandler.context)) {
                    SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$14 = panelSlideEventHandler.panelSlideEventCallback;
                    if (secPanelSplitHelper$panelSlideEventHandler$1$14 != null) {
                        secPanelSplitHelper$panelSlideEventHandler$1$14.this$0.slide$1(1);
                    }
                    Log.d("SecPanelSplitHelper", "shouldQSDown y, SHADE_STATE return false");
                    return false;
                }
                if (motionEvent.getX() >= panelSlideEventHandler.displayWidthOfDivider) {
                    if (QsAnimatorState.state != 2 || (secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback) == null || secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.currentState != 1) {
                        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$15 = panelSlideEventHandler.panelSlideEventCallback;
                        if (secPanelSplitHelper$panelSlideEventHandler$1$15 != null) {
                            secPanelSplitHelper$panelSlideEventHandler$1$15.this$0.slide$1(zIsReversed ? 1 : 0);
                        }
                        i = zIsReversed ? 1 : 0;
                    }
                    Log.d("SecPanelSplitHelper", "shouldQSDown x, isReversed? " + zIsReversed + ", " + PanelTransitionState.toString(i) + " return true");
                    return true;
                }
                int i3 = !zIsReversed ? 1 : 0;
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$16 = panelSlideEventHandler.panelSlideEventCallback;
                if (secPanelSplitHelper$panelSlideEventHandler$1$16 != null) {
                    secPanelSplitHelper$panelSlideEventHandler$1$16.this$0.slide$1(i3);
                }
                SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) panelSlideEventHandler.secPanelSAStatusLogInteractor$delegate.getValue();
                secPanelSAStatusLogInteractor.getClass();
                Companion.getClass();
                if (isEnabled) {
                    StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openNotificationPanelFromStatusbarInShade;
                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                }
                Log.d("SecPanelSplitHelper", "shouldQSDown else, isReversed? " + zIsReversed + ", " + PanelTransitionState.toString(i3) + " return false");
                return false;
            }
        } else if (z && this.currentState == 1 && this.synthesizedActionDown != null && (view = this.qsScrollView) != null && view.getVisibility() == 0) {
            Log.d("NonInterceptingScrollView", "Launcher Swipe DOWN, but QS ScrollView is VISIBLE. Set it INVISIBLE.");
            view.setVisibility(4);
        }
        return false;
    }

    public final void slide(float f, PanelSlideEventHandler.Direction direction, boolean z) {
        if (QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing || QsAnimatorState.isCustomizerShowing) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("can't slide : ", ", ", ", ", QsAnimatorState.isDetailOpening, QsAnimatorState.isDetailShowing), QsAnimatorState.isDetailClosing, ", ", QsAnimatorState.isCustomizerShowing, "SecPanelSplitHelper");
            return;
        }
        int i = (!isReversed() || this.secQsUiDisplayModeInteractor.isTablet()) ? 0 : 1;
        int i2 = this.stateOnDown;
        if (i2 != i ? i2 != (i ^ 1) || (direction != PanelSlideEventHandler.Direction.DOWN ? f < 0.0f : f > 0.0f) : f > 0.0f) {
            float maxSlideDistance = getMaxSlideDistance();
            float fAbs = Math.abs(f);
            if (0.0f >= fAbs) {
                fAbs = 0.0f;
            }
            if (maxSlideDistance > fAbs) {
                maxSlideDistance = fAbs;
            }
            setDraggedFraction(this.onceOverSlide ? 0.0f : maxSlideDistance / getMaxSlideDistance());
            this.overSlideAmount = 0.0f;
        } else {
            setDraggedFraction(0.0f);
            this.overSlideAmount = f;
        }
        float f2 = this.overSlideAmount;
        if (f2 != 0.0f) {
            this.onceOverSlide = true;
        }
        Log.d("SecPanelSplitHelper", "slide ( " + f + ", " + direction + ", " + z + " ) draggedFraction : " + this.draggedFraction + " overSlideAmount : " + f2 + " onceOverSlide : " + this.onceOverSlide);
        float f3 = this.draggedFraction;
        QuickSALog quickSALog = this.quickSALog;
        if (f3 == 1.0f) {
            updateState((i == 0 || getStatusBarStateController$2().getState() != 1) ? this.stateToChange : this.stateOnDown);
            QsAnimatorState.isSliding = false;
            if (quickSALog != null) {
                int i3 = this.currentState;
                int state = getStatusBarStateController$2().getState();
                SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) quickSALog.panelSAStatusLogInteractor$delegate.getValue();
                if (quickSALog.isDone) {
                    secPanelSAStatusLogInteractor = null;
                }
                if (secPanelSAStatusLogInteractor != null) {
                    PanelSlideEventHandler.Direction direction2 = PanelSlideEventHandler.Direction.DOWN;
                    SecPanelSAStatusLogRepository secPanelSAStatusLogRepository = secPanelSAStatusLogInteractor.repository;
                    if (direction != direction2 || i3 != 0) {
                        Companion companion = Companion;
                        if (direction == direction2 && i3 == 1) {
                            if (state == 0) {
                                companion.getClass();
                                if (isEnabled) {
                                    StateFlowImpl stateFlowImpl = secPanelSAStatusLogRepository._openNotificationPanelFromSwipeDownInShade;
                                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                                }
                            } else if (state == 2) {
                                companion.getClass();
                                if (isEnabled) {
                                    StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogRepository._openNotificationPanelFromSwipeDownInKeyguard;
                                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl2.getValue(), 1L, stateFlowImpl2, null);
                                }
                            }
                        } else if (direction == PanelSlideEventHandler.Direction.LEFT && i3 == 0) {
                            if (state == 0) {
                                StateFlowImpl stateFlowImpl3 = secPanelSAStatusLogRepository._openQuickPanelFromHorizontalSwipingInShade;
                                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl3.getValue(), 1L, stateFlowImpl3, null);
                            } else if (state == 2) {
                                StateFlowImpl stateFlowImpl4 = secPanelSAStatusLogRepository._openQuickPanelFromHorizontalSwipingInKeyguard;
                                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl4.getValue(), 1L, stateFlowImpl4, null);
                            }
                        } else if (direction == PanelSlideEventHandler.Direction.RIGHT && i3 == 1) {
                            if (state == 0) {
                                companion.getClass();
                                if (isEnabled) {
                                    StateFlowImpl stateFlowImpl5 = secPanelSAStatusLogRepository._openNotificationPanelFromSwipeRightInShade;
                                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl5.getValue(), 1L, stateFlowImpl5, null);
                                }
                            } else if (state == 2) {
                                companion.getClass();
                                if (isEnabled) {
                                    StateFlowImpl stateFlowImpl6 = secPanelSAStatusLogRepository._openNotificationPanelFromSwipeRightInKeyguard;
                                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl6.getValue(), 1L, stateFlowImpl6, null);
                                }
                            }
                        }
                    } else if (state == 0) {
                        StateFlowImpl stateFlowImpl7 = secPanelSAStatusLogRepository._openQuickPanelFromWipeDownInShade;
                        LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl7.getValue(), 1L, stateFlowImpl7, null);
                    } else if (state == 2) {
                        StateFlowImpl stateFlowImpl8 = secPanelSAStatusLogRepository._openQuickPanelFromWipeDownInKeyguard;
                        LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl8.getValue(), 1L, stateFlowImpl8, null);
                    }
                    quickSALog.isDone = true;
                }
            }
        } else if (f3 == 0.0f) {
            updateState((i == 0 || getStatusBarStateController$2().getState() != 1) ? this.stateOnDown : this.stateToChange);
            QsAnimatorState.isSliding = false;
        } else if (this.currentState != 2) {
            updateState(2);
            QsAnimatorState.isSliding = true;
            if (quickSALog != null) {
                quickSALog.isDone = false;
            }
        }
        if (Math.abs(this.overSlideAmount) == getMaxSlideDistance() && !z) {
            springBack();
        }
        SecQSImplAnimatorManager secQSImplAnimatorManager = this.qsAnimatorManager;
        if (secQSImplAnimatorManager != null) {
            secQSImplAnimatorManager.slide(this.draggedFraction, this.overSlideAmount, direction, this.stateToChange);
        }
    }

    public final void slide$1(int i) {
        MediaSessions$H$$ExternalSyntheticOutline0.m("slide ( ", PanelTransitionState.toString(i), " )\n", Debug.getCallers(10, " - "), "SecPanelSplitHelper");
        updateState(i);
        QuickSALog quickSALog = this.quickSALog;
        if (quickSALog != null) {
            if (getStatusBarStateController$2().getState() != 0) {
                quickSALog = null;
            }
            if (quickSALog != null) {
                SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) quickSALog.panelSAStatusLogInteractor$delegate.getValue();
                if (i != 0) {
                    secPanelSAStatusLogInteractor = null;
                }
                if (secPanelSAStatusLogInteractor != null) {
                    StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFromStatusBarInShade;
                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                }
            }
        }
        SecQSImplAnimatorManager secQSImplAnimatorManager = this.qsAnimatorManager;
        if (secQSImplAnimatorManager != null) {
            secQSImplAnimatorManager.slide(1.0f, 0.0f, PanelSlideEventHandler.Direction.DOWN, i);
        }
    }

    public final void springBack() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.overSlideAmount, 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.SecPanelSplitHelper$springBack$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SecPanelSplitHelper secPanelSplitHelper = this.this$0;
                SecQSImplAnimatorManager secQSImplAnimatorManager = secPanelSplitHelper.qsAnimatorManager;
                if (secQSImplAnimatorManager != null) {
                    secQSImplAnimatorManager.slide(0.0f, fFloatValue, null, secPanelSplitHelper.stateToChange);
                }
            }
        });
        valueAnimatorOfFloat.setDuration((long) ((Math.abs(this.overSlideAmount) / getMaxSlideDistance()) * 200));
        valueAnimatorOfFloat.setInterpolator(new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f));
        valueAnimatorOfFloat.start();
    }

    public final void updatePanelVisibility() {
        Log.d("SecPanelSplitHelper", "updatePanelVisibility ".concat(PanelTransitionState.toString(this.currentState)));
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && this.secQsUiDisplayModeInteractor.isTablet() && getSettingsHelper$2().isRemoveAnimation()) {
            return;
        }
        View view = this.qsScrollView;
        View view2 = this.shadeRootView;
        int i = this.currentState;
        if (i == 0) {
            if (view != null) {
                view.setVisibility(0);
            }
            if (view2 != null) {
                view2.setVisibility(4);
                return;
            }
            return;
        }
        if (i != 1) {
            if (view != null) {
                view.setVisibility(0);
            }
            if (view2 != null) {
                view2.setVisibility(0);
                return;
            }
            return;
        }
        if (view != null) {
            view.setVisibility(4);
        }
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    public final void updateState(int i) {
        if (i == this.currentState) {
            return;
        }
        Log.d("SecPanelSplitHelper", "updateState ".concat(PanelTransitionState.toString(i)));
        this.currentState = i;
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(this.enabled, this.draggedFraction, i);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
        if (this.currentState == 0) {
            ((NotificationStackScrollLayout) this.shadeRootView).resetScrollPosition();
        }
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.secQsUiDisplayModeInteractor;
        boolean zIsTablet = secQsUiDisplayModeInteractor.isTablet();
        SplitStateRepository splitStateRepository = this.splitStateRepository;
        if (zIsTablet && Arrays.asList(0, 1).contains(Integer.valueOf(this.currentState))) {
            splitStateRepository._isQsState.updateState(null, Integer.valueOf(this.currentState));
        } else if (this.currentState == 3) {
            splitStateRepository._isQsState.updateState(null, -1);
        }
        updatePanelVisibility();
        if (isEnabled && QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && secQsUiDisplayModeInteractor.isTablet() && getSettingsHelper$2().isRemoveAnimation() && !this.overHalfdraggedFraction) {
            updateTransitionVisibility(this.currentState);
        }
        this.stateToChange = this.stateOnDown != 0 ? 0 : 1;
        this.overSlideAmount = 0.0f;
    }

    public final void updateTransitionVisibility(int i) {
        this.overHalfdraggedFraction = false;
        Log.d("SecPanelSplitHelper", "updateTransitionVisibility ".concat(PanelTransitionState.toString(i)));
        if (i == 0) {
            View view = this.qsFrame;
            if (view != null) {
                view.setVisibility(0);
            }
            View view2 = this.qsScrollView;
            if (view2 != null) {
                view2.setVisibility(0);
            }
            View view3 = this.shadeRootView;
            if (view3 != null) {
                view3.setVisibility(4);
                return;
            }
            return;
        }
        if (i != 1) {
            View view4 = this.qsFrame;
            if (view4 != null) {
                view4.setVisibility(4);
            }
            View view5 = this.shadeRootView;
            if (view5 != null) {
                view5.setVisibility(4);
                return;
            }
            return;
        }
        View view6 = this.qsFrame;
        if (view6 != null) {
            view6.setVisibility(0);
        }
        View view7 = this.qsScrollView;
        if (view7 != null) {
            view7.setVisibility(4);
        }
        View view8 = this.shadeRootView;
        if (view8 != null) {
            view8.setVisibility(0);
        }
    }
}
