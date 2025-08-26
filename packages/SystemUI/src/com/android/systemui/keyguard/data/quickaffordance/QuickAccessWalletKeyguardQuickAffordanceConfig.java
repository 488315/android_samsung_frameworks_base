package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda3;
import java.util.List;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ChannelFlowTransformLatest lockScreenState = FlowKt.transformLatest(FlowConflatedKt.conflatedCallbackFlow(new QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(this, null)), new QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(null, this));
    public final QuickAccessWalletController walletController;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return QuickAccessWalletKeyguardQuickAffordanceConfig.this.getPickerScreenState(this);
        }
    }

    static {
        new Companion(null);
    }

    public QuickAccessWalletKeyguardQuickAffordanceConfig(Context context, CoroutineDispatcher coroutineDispatcher, QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter) {
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.walletController = quickAccessWalletController;
        this.activityStarter = activityStarter;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return "wallet";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return R.drawable.ic_wallet_lockscreen;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x008e, code lost:
    
        if (r13 == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerScreenState(Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            if (!this.walletController.mQuickAccessWalletClient.isWalletServiceAvailable()) {
                return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
            }
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2(this, null), anonymousClass1);
            if (objWithContext != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (QuickAccessWalletKeyguardQuickAffordanceConfig) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWithContext);
            return ((List) objWithContext).isEmpty() ? new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(this.context.getString(R.string.wallet_quick_affordance_unavailable_configure_the_app), null, null, 6, null) : new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
        }
        this = (QuickAccessWalletKeyguardQuickAffordanceConfig) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objWithContext);
        if (!((Boolean) objWithContext).booleanValue()) {
            return new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(this.context.getString(R.string.wallet_quick_affordance_unavailable_install_the_app), null, null, 6, null);
        }
        anonymousClass1.L$0 = this;
        anonymousClass1.label = 2;
        this.getClass();
        objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new QuickAccessWalletKeyguardQuickAffordanceConfig$queryCards$2(this, null), anonymousClass1);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        ActivityTransitionAnimator.Controller controllerActivityTransitionController = null;
        if (expandable != null) {
            Expandable.Companion companion = Expandable.Companion;
            controllerActivityTransitionController = expandable.activityTransitionController(null);
        }
        QuickAccessWalletController quickAccessWalletController = this.walletController;
        quickAccessWalletController.mQuickAccessWalletClient.getWalletPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda3(quickAccessWalletController, this.activityStarter, controllerActivityTransitionController, true));
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(true);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.wallet_title);
    }
}
