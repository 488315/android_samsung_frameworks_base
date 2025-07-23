package com.android.systemui.shade;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecPanelSplitHelper implements ShadeExpansionListener, SettingsHelper.OnChangedCallback, StatusBarStateController.StateListener, LockscreenShadeTransitionController.Callback, ConfigurationController.ConfigurationListener {
    public static boolean isEnabled;
    public final Context context;
    public int currentOrientation;
    public int currentState;
    public float draggedFraction;
    public boolean enabled;
    public final Executor executor;
    public final CopyOnWriteArrayList expansionListeners;
    public final HeadsUpManager headsUpManager;
    public SecNotificationPanelViewController$panelSplitHelper$1$1 interceptCallback;
    public boolean isOnceOverExpanded;
    public boolean onceOverSlide;
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
    public final Lazy settingsHelper$delegate;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public View shadeRootView;
    public PanelSlideEventHandler.Direction shouldQsDownInLockscreen;
    public final SplitStateRepository splitStateRepository;
    public int stateOnDown;
    public int stateToChange;
    public final Lazy statusBarStateController$delegate;
    public MotionEvent synthesizedActionDown;
    public final UserTracker.Callback userChanged;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final Uri SPLIT_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL);
    public static final Uri REVERSED_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED);
    public static final Uri USER_CHANGED = Uri.parse("USER_CHANGED");
    public static final Uri RATIO_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_RATIO);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new SecPanelSplitHelper$$ExternalSyntheticLambda0(0));
        this.settingsHelper$delegate = lazy;
        this.statusBarStateController$delegate = LazyKt__LazyJVMKt.lazy(new SecPanelSplitHelper$$ExternalSyntheticLambda0(1));
        this.expansionListeners = new CopyOnWriteArrayList();
        PanelSlideEventHandler panelSlideEventHandler = new PanelSlideEventHandler(context, shadeExpansionStateManager, quickSettingsControllerImpl, this);
        panelSlideEventHandler.panelSlideEventCallback = new SecPanelSplitHelper$panelSlideEventHandler$1$1(this);
        this.panelSlideEventHandler = panelSlideEventHandler;
        this.quickSALog = new QuickSALog();
        this.currentState = 2;
        this.stateToChange = 1;
        this.shouldQsDownInLockscreen = PanelSlideEventHandler.Direction.UNDECIDED;
        ((SettingsHelper) lazy.getValue()).registerCallback(this, SPLIT_URI, REVERSED_URI, RATIO_URI);
        this.reversed = ((SettingsHelper) lazy.getValue()).isPanelSplitReversed();
        if (secQsUiDisplayModeInteractor.isTablet()) {
            splitStateRepository._reverseState.updateState(null, Boolean.valueOf(isReversed()));
        }
        boolean isPanelSplit = ((SettingsHelper) lazy.getValue()).isPanelSplit();
        panelSlideEventHandler.panelSplitEnabled = isPanelSplit;
        Lazy lazy2 = panelSlideEventHandler.statusBarStateController$delegate;
        Lazy lazy3 = panelSlideEventHandler.configurationController$delegate;
        ShadeExpansionStateManager shadeExpansionStateManager2 = panelSlideEventHandler.shadeExpansionStateManager;
        if (isPanelSplit) {
            panelSlideEventHandler.updateResource();
            shadeExpansionStateManager2.addExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy3.getValue())).addCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy2.getValue()).addCallback(panelSlideEventHandler);
        } else {
            shadeExpansionStateManager2.removeExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy3.getValue())).removeCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy2.getValue()).removeCallback(panelSlideEventHandler);
        }
        setEnabled$1(isPanelSplit);
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
                SecPanelSplitHelper secPanelSplitHelper = SecPanelSplitHelper.this;
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

    public final StatusBarStateController getStatusBarStateController$2() {
        return (StatusBarStateController) this.statusBarStateController$delegate.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ca, code lost:
    
        if (r3.panelSlideEventHandler.tracking != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0022, code lost:
    
        if (r3 != 3) goto L56;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleTouch(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SecPanelSplitHelper.handleTouch(android.view.MotionEvent):boolean");
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
                Lazy lazy = this.settingsHelper$delegate;
                setEnabled$1(((SettingsHelper) lazy.getValue()).isPanelSplit());
                this.reversed = ((SettingsHelper) lazy.getValue()).isPanelSplitReversed();
                if (this.secQsUiDisplayModeInteractor.isTablet()) {
                    this.splitStateRepository._reverseState.updateState(null, Boolean.valueOf(isReversed()));
                }
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
        if (this.secQsUiDisplayModeInteractor.isTablet()) {
            this.splitStateRepository._reverseState.updateState(null, Boolean.valueOf(isReversed()));
        }
        int i = this.currentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.currentOrientation = i2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
    
        if (r8 != 3) goto L213;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onIntercept(android.view.MotionEvent r15) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SecPanelSplitHelper.onIntercept(android.view.MotionEvent):boolean");
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
        if (this.secQsUiDisplayModeInteractor.isTablet()) {
            if (getStatusBarStateController$2().getState() == 1) {
                return;
            }
            Integer valueOf = Integer.valueOf((this.draggedFraction <= 0.5f ? this.stateOnDown != 1 : this.stateOnDown == 1) ? 0 : 1);
            SplitStateRepository splitStateRepository = this.splitStateRepository;
            splitStateRepository._isQsState.updateState(null, valueOf);
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
        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1;
        int i = 0;
        if (this.enabled) {
            MotionEvent motionEvent2 = this.synthesizedActionDown;
            if (motionEvent2 != null) {
                motionEvent = motionEvent2;
            }
            PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
            panelSlideEventHandler.getClass();
            if (motionEvent.getActionMasked() == 0) {
                int i2 = QsAnimatorState.state;
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12 = panelSlideEventHandler.panelSlideEventCallback;
                Integer valueOf = secPanelSplitHelper$panelSlideEventHandler$1$12 != null ? Integer.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.currentState) : null;
                Log.d("SecPanelSplitHelper", "shouldQSDown statusBarState = " + i2 + ", state = " + valueOf + " displayWidthOfDivider = " + panelSlideEventHandler.displayWidthOfDivider + " x = " + motionEvent.getX() + " y = " + motionEvent.getY() + " statusBarHeight = " + SystemBarUtils.getStatusBarHeight(panelSlideEventHandler.context));
                boolean isReversed = panelSlideEventHandler.secPanelSplitHelper.isReversed();
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
                            secPanelSplitHelper$panelSlideEventHandler$1$15.this$0.slide$1(isReversed ? 1 : 0);
                        }
                        i = isReversed ? 1 : 0;
                    }
                    Log.d("SecPanelSplitHelper", "shouldQSDown x, isReversed? " + isReversed + ", " + PanelTransitionState.toString(i) + " return true");
                    return true;
                }
                int i3 = !isReversed ? 1 : 0;
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
                Log.d("SecPanelSplitHelper", "shouldQSDown else, isReversed? " + isReversed + ", " + PanelTransitionState.toString(i3) + " return false");
                return false;
            }
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
            float abs = Math.abs(f);
            if (0.0f >= abs) {
                abs = 0.0f;
            }
            if (maxSlideDistance > abs) {
                maxSlideDistance = abs;
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.overSlideAmount, 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.SecPanelSplitHelper$springBack$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SecPanelSplitHelper secPanelSplitHelper = SecPanelSplitHelper.this;
                SecQSImplAnimatorManager secQSImplAnimatorManager = secPanelSplitHelper.qsAnimatorManager;
                if (secQSImplAnimatorManager != null) {
                    secQSImplAnimatorManager.slide(0.0f, floatValue, null, secPanelSplitHelper.stateToChange);
                }
            }
        });
        ofFloat.setDuration((long) ((Math.abs(this.overSlideAmount) / getMaxSlideDistance()) * 200));
        ofFloat.setInterpolator(new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f));
        ofFloat.start();
    }

    public final void updatePanelVisibility() {
        Log.d("SecPanelSplitHelper", "updatePanelVisibility ".concat(PanelTransitionState.toString(this.currentState)));
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
        if (this.secQsUiDisplayModeInteractor.isTablet() && Arrays.asList(0, 1).contains(Integer.valueOf(this.currentState))) {
            this.splitStateRepository._isQsState.updateState(null, Integer.valueOf(this.currentState));
        }
        updatePanelVisibility();
        this.stateToChange = this.stateOnDown != 0 ? 0 : 1;
        this.overSlideAmount = 0.0f;
    }
}
