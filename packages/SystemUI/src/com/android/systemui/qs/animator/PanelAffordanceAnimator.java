package com.android.systemui.qs.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AnimHelper;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class PanelAffordanceAnimator extends SecQSImplAnimatorBase {
    public static final AnimHelper.AnimProperty HIDE_GLOW_ANIM_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_GLOW_ANIM_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimationType[] INIT_PROPERTY_FIELDS;
    public static final AnimHelper.AnimProperty SHOW_ANIM_GLOW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ConfigurationController configurationController;
    public final Context context;
    public QSAnimView glowView;
    public AnimatorSet hideAnimSet;
    public final PanelAffordanceAnimator$hideGlowRunnable$1 hideGlowRunnable;
    public boolean isGlowShowing;
    public boolean isIfNeedReloadView;
    public int lastDensityDpi;
    public int lastLayoutDirection;
    public int lastOrientation;
    public boolean panelExpanded;
    public boolean panelOnceFullyExpanded;
    public boolean panelOnceFullySlidden;
    public final SecQSExpansionStateInteractor qsExpansionStateInteractor;
    public boolean reversed;
    public final Lazy secPanelSplitHelper$delegate;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public AnimatorSet showAnimSet;
    public boolean showAnimTriggered;
    public final Lazy statusBarStateController$delegate;
    public final QSAnimViewProvider viewProvider;
    public final ArrayList showAnimList = new ArrayList();
    public final ArrayList hideAnimList = new ArrayList();
    public final Handler hideHandler = new Handler(Looper.getMainLooper());
    public int targetState = 2;
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    public final PanelAffordanceAnimator$secQSStateListener$1 secQSStateListener = new PanelAffordanceAnimator$secQSStateListener$1(this);
    public final PanelAffordanceAnimator$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (this.this$0.isThereNoView() || this.this$0.isNotVisible()) {
                return;
            }
            this.this$0.hiding(200);
        }
    };
    private SettingsHelper.OnChangedCallback settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (Intrinsics.areEqual(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED), uri)) {
                PanelAffordanceAnimator panelAffordanceAnimator = this.this$0;
                boolean zIsPanelSplitReversed = panelAffordanceAnimator.settingsHelper.isPanelSplitReversed();
                panelAffordanceAnimator.reversed = zIsPanelSplitReversed;
                Log.d("PanelAffordanceAnimator", "OnChangedCallback reversed = " + zIsPanelSplitReversed);
                panelAffordanceAnimator.loadView();
                panelAffordanceAnimator.clearAnimationState();
            }
        }
    };
    public final PanelAffordanceAnimator$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            PanelAffordanceAnimator panelAffordanceAnimator = this.this$0;
            if (configuration == null) {
                AnimHelper.AnimationType[] animationTypeArr = PanelAffordanceAnimator.INIT_PROPERTY_FIELDS;
                panelAffordanceAnimator.getClass();
                return;
            }
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(panelAffordanceAnimator.lastOrientation, configuration.orientation, "onConfigChange lastOrientation = ", ", orientation = ", ", isIfNeedReloadView, newConfig = ");
            sbM.append(configuration);
            Log.d("PanelAffordanceAnimator", sbM.toString());
            if (panelAffordanceAnimator.lastDensityDpi == configuration.densityDpi && panelAffordanceAnimator.lastOrientation == configuration.orientation && panelAffordanceAnimator.lastLayoutDirection == configuration.getLayoutDirection() && !panelAffordanceAnimator.isIfNeedReloadView) {
                return;
            }
            panelAffordanceAnimator.lastDensityDpi = configuration.densityDpi;
            panelAffordanceAnimator.lastOrientation = configuration.orientation;
            panelAffordanceAnimator.lastLayoutDirection = configuration.getLayoutDirection();
            panelAffordanceAnimator.isIfNeedReloadView = false;
            if (!panelAffordanceAnimator.isNotVisible()) {
                panelAffordanceAnimator.hiding(0);
            }
            panelAffordanceAnimator.loadView();
            panelAffordanceAnimator.clearAnimationState();
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        AnimHelper.AnimationType animationType = AnimHelper.AnimationType.ALPHA;
        AnimHelper.AnimationType animationType2 = AnimHelper.AnimationType.TRANSLATION_Y;
        INIT_PROPERTY_FIELDS = new AnimHelper.AnimationType[]{animationType, animationType2};
        PathInterpolator pathInterpolator = new PathInterpolator(0.61f, 1.0f, 0.88f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        SHOW_ANIM_GLOW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 500, 0, 0.0f, 1.0f, pathInterpolator);
        SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, 0.0f, -1.0f, pathInterpolator2);
        HIDE_GLOW_ANIM_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 200, 0, 1.0f, 0.0f, pathInterpolator);
        HIDE_GLOW_ANIM_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 200, 0, -1.0f, -1.0f, pathInterpolator);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.qs.animator.PanelAffordanceAnimator$hideGlowRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qs.animator.PanelAffordanceAnimator$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.animator.PanelAffordanceAnimator$configurationListener$1] */
    public PanelAffordanceAnimator(Context context, QSAnimViewProvider qSAnimViewProvider, SecQSExpansionStateInteractor secQSExpansionStateInteractor, BroadcastDispatcher broadcastDispatcher, ConfigurationController configurationController, DisplayLifecycle displayLifecycle, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.context = context;
        this.viewProvider = qSAnimViewProvider;
        this.qsExpansionStateInteractor = secQSExpansionStateInteractor;
        this.broadcastDispatcher = broadcastDispatcher;
        this.configurationController = configurationController;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        final int i = 0;
        this.secPanelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        AnimHelper.AnimationType[] animationTypeArr = PanelAffordanceAnimator.INIT_PROPERTY_FIELDS;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        AnimHelper.AnimationType[] animationTypeArr2 = PanelAffordanceAnimator.INIT_PROPERTY_FIELDS;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                }
            }
        });
        final int i2 = 1;
        this.statusBarStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        AnimHelper.AnimationType[] animationTypeArr = PanelAffordanceAnimator.INIT_PROPERTY_FIELDS;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        AnimHelper.AnimationType[] animationTypeArr2 = PanelAffordanceAnimator.INIT_PROPERTY_FIELDS;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                }
            }
        });
        this.lastDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.lastOrientation = context.getResources().getConfiguration().orientation;
        this.lastLayoutDirection = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context);
        new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$displayLifecycleObserver$1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onFolderStateChanged isOpened = ", "PanelAffordanceAnimator", z);
                this.this$0.isIfNeedReloadView = true;
            }
        };
        this.hideGlowRunnable = new Runnable() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator$hideGlowRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("PanelAffordanceAnimator", "hiding waiting 3s");
                this.this$0.hiding(200);
            }
        };
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        if (isThereNoView()) {
            return;
        }
        Log.d("PanelAffordanceAnimator", "clearAnimationState");
        AnimHelper animHelper = AnimHelper.INSTANCE;
        View[] viewArr = new View[1];
        QSAnimView qSAnimView = this.glowView;
        viewArr[0] = qSAnimView != null ? qSAnimView.getView() : null;
        animHelper.initProperty(INIT_PROPERTY_FIELDS, viewArr);
        resetAnim();
        this.panelExpanded = false;
        this.panelOnceFullyExpanded = false;
        this.panelOnceFullySlidden = false;
        this.showAnimTriggered = false;
        this.isGlowShowing = false;
        this.targetState = 2;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        this.settingsHelper.unregisterCallback(this.settingsListener);
        this.qsExpansionStateInteractor.qsStateListener = null;
        this.broadcastDispatcher.unregisterReceiver(this.broadcastReceiver);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        this.glowView = null;
    }

    public final void hiding(int i) {
        Log.d("PanelAffordanceAnimator", "hiding");
        resetAnim();
        AnimHelper animHelper = AnimHelper.INSTANCE;
        QSAnimView qSAnimView = this.glowView;
        View view = qSAnimView != null ? qSAnimView.getView() : null;
        QSAnimView qSAnimView2 = this.glowView;
        float alpha = qSAnimView2 != null ? qSAnimView2.getAlpha() : 1.0f;
        AnimHelper.AnimProperty animProperty = HIDE_GLOW_ANIM_ALPHA_PROPERTY;
        animProperty.setFromValue(alpha);
        animProperty.setDuration(i);
        Unit unit = Unit.INSTANCE;
        AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(view, animProperty);
        QSAnimView qSAnimView3 = this.glowView;
        View view2 = qSAnimView3 != null ? qSAnimView3.getView() : null;
        QSAnimView qSAnimView4 = this.glowView;
        float translationY = qSAnimView4 != null ? qSAnimView4.getTranslationY() : this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_glow_height);
        AnimHelper.AnimProperty animProperty2 = HIDE_GLOW_ANIM_TRANSLATION_Y_PROPERTY;
        animProperty2.setFromValue(translationY);
        animProperty2.setToValue(this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_glow_height) * 0.8f);
        animProperty2.setDuration(i);
        AnimatorSet animatorSetMakeAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, new AnimHelper.AnimPairSet(view2, animProperty2)}, this.hideAnimList, new AnimHelper.BaseAnimatorListener() { // from class: com.android.systemui.qs.animator.PanelAffordanceAnimator.hiding.3
            @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PanelAffordanceAnimator panelAffordanceAnimator = PanelAffordanceAnimator.this;
                panelAffordanceAnimator.panelOnceFullyExpanded = false;
                panelAffordanceAnimator.panelOnceFullySlidden = false;
            }
        });
        this.hideAnimSet = animatorSetMakeAnimSet;
        this.isGlowShowing = false;
        if (animatorSetMakeAnimSet != null) {
            animatorSetMakeAnimSet.start();
        }
    }

    public final boolean isNotVisible() {
        QSAnimView qSAnimView = this.glowView;
        return qSAnimView != null && qSAnimView.getAlpha() == 0.0f;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        if (super.isThereNoView()) {
            return true;
        }
        SecPanelSplitHelper.Companion.getClass();
        if (!SecPanelSplitHelper.isEnabled || QsAnimatorState.isDetailShowing) {
            return true;
        }
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
        return !(secQsUiDisplayModeInteractor.isTablet() || secQsUiDisplayModeInteractor.isFoldWide() || this.lastOrientation != 2) || this.settingsHelper.isRemoveAnimation();
    }

    public final void loadView() {
        QSAnimView qSAnimView = this.viewProvider.get(QSAnimViewProvider.ViewType.AFFORDANCE_GLOW);
        this.glowView = qSAnimView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (qSAnimView != null ? qSAnimView.getLayoutParams() : null);
        float f = this.context.getResources().getDisplayMetrics().widthPixels;
        float f2 = 0.37f;
        if (QpRune.QUICK_TABLET || this.secQsUiDisplayModeInteractor.isTablet()) {
            if (this.lastOrientation == 2) {
                f2 = 0.2312f;
            }
        } else if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() && this.lastOrientation == 2) {
            f2 = 0.328f;
        }
        layoutParams.width = (int) (f * f2);
        layoutParams.height = dimensionPixelSize;
        layoutParams.topMargin = -dimensionPixelSize;
        layoutParams.gravity = this.reversed ? 8388659 : 8388661;
        AnimHelper animHelper = AnimHelper.INSTANCE;
        View[] viewArr = new View[1];
        QSAnimView qSAnimView2 = this.glowView;
        viewArr[0] = qSAnimView2 != null ? qSAnimView2.getView() : null;
        animHelper.initProperty(INIT_PROPERTY_FIELDS, viewArr);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        if (QsAnimatorState.state == 0 && this.isGlowShowing) {
            hiding(200);
        }
        clearAnimationState();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        if (isThereNoView() || QsAnimatorState.state == 1) {
            return;
        }
        boolean z = this.panelExpanded;
        boolean z2 = shadeExpansionChangeEvent.expanded;
        if (z != z2) {
            this.panelExpanded = z2;
            this.showAnimTriggered = z2;
        }
        float f = shadeExpansionChangeEvent.fraction;
        if (f == 0.0f) {
            this.panelOnceFullyExpanded = false;
            if (this.isGlowShowing) {
                hiding(200);
            }
        } else if (f == 1.0f) {
            this.panelOnceFullyExpanded = true;
        }
        if (f > 0.1f && this.targetState == 0 && QsAnimatorState.state == 0 && this.showAnimTriggered && !this.isGlowShowing && this.panelExpanded && this.mPanelState == 0 && !this.panelOnceFullyExpanded) {
            this.showAnimTriggered = false;
            showing();
        } else if (f < 0.8f && this.targetState == 2 && this.isGlowShowing && this.panelOnceFullyExpanded) {
            this.panelOnceFullyExpanded = false;
            hiding(200);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        if (i != 2) {
            clearAnimationState();
        }
    }

    public final void resetAnim() {
        this.showAnimList.clear();
        AnimatorSet animatorSet = this.showAnimSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.hideAnimList.clear();
        AnimatorSet animatorSet2 = this.hideAnimSet;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        this.hideHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = (QSImpl) qs;
        loadView();
        clearAnimationState();
        this.settingsHelper.registerCallback(this.settingsListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED));
        this.reversed = this.settingsHelper.isPanelSplitReversed();
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        this.qsExpansionStateInteractor.qsStateListener = this.secQSStateListener;
        IntentFilter intentFilterM = AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.broadcastReceiver, intentFilterM, null, UserHandle.ALL, 0, null, 48);
    }

    public final void showing() {
        Log.d("PanelAffordanceAnimator", "showing");
        resetAnim();
        AnimHelper animHelper = AnimHelper.INSTANCE;
        QSAnimView qSAnimView = this.glowView;
        View view = qSAnimView != null ? qSAnimView.getView() : null;
        QSAnimView qSAnimView2 = this.glowView;
        float alpha = qSAnimView2 != null ? qSAnimView2.getAlpha() : 0.0f;
        AnimHelper.AnimProperty animProperty = SHOW_ANIM_GLOW_ALPHA_PROPERTY;
        animProperty.setFromValue(alpha);
        Unit unit = Unit.INSTANCE;
        AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(view, animProperty);
        QSAnimView qSAnimView3 = this.glowView;
        View view2 = qSAnimView3 != null ? qSAnimView3.getView() : null;
        QSAnimView qSAnimView4 = this.glowView;
        float translationY = qSAnimView4 != null ? qSAnimView4.getTranslationY() : 0.0f;
        AnimHelper.AnimProperty animProperty2 = SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
        animProperty2.setFromValue(translationY);
        animProperty2.setToValue(this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_glow_height));
        AnimatorSet animatorSetMakeAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, new AnimHelper.AnimPairSet(view2, animProperty2)}, this.showAnimList, new AnimHelper.BaseAnimatorListener("PanelAffordanceAnimator", "Show", false));
        this.showAnimSet = animatorSetMakeAnimSet;
        this.isGlowShowing = true;
        if (animatorSetMakeAnimSet != null) {
            animatorSetMakeAnimSet.start();
        }
        this.hideHandler.postDelayed(this.hideGlowRunnable, 2500L);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void slide(float f, float f2, PanelSlideEventHandler.Direction direction, int i) {
        if (isThereNoView()) {
            return;
        }
        boolean zIsReversed = ((SecPanelSplitHelper) this.secPanelSplitHelper$delegate.getValue()).isReversed();
        boolean z = zIsReversed && ((StatusBarStateController) this.statusBarStateController$delegate.getValue()).getState() == 1;
        boolean z2 = !z ? i != 0 : i != 1;
        int i2 = this.targetState;
        if (i2 != i) {
            this.targetState = i;
            this.showAnimTriggered = i == 0;
        } else if (z) {
            this.showAnimTriggered = i2 == 1;
        }
        if (f == 0.0f) {
            clearAnimationState();
        } else if (f == 1.0f) {
            if (zIsReversed) {
                int i3 = this.mPanelState;
                if ((i3 == 0 && i == 1 && this.targetState == 1) || (i3 == 0 && i == 0 && this.targetState == 0)) {
                    this.panelOnceFullySlidden = true;
                }
            } else if (i == 0) {
                this.panelOnceFullySlidden = true;
            }
        }
        if (f > 0.1f && this.showAnimTriggered && !this.isGlowShowing) {
            if ((QsAnimatorState.state == 0 ? this.panelExpanded : true) && z2 && direction == PanelSlideEventHandler.Direction.DOWN) {
                this.showAnimTriggered = false;
                showing();
                return;
            }
        }
        if (f < 0.8f && this.isGlowShowing && this.panelOnceFullySlidden) {
            this.panelOnceFullySlidden = false;
            hiding(200);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
    }
}
