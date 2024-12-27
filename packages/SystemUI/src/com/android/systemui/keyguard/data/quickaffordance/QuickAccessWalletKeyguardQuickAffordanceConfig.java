package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class QuickAccessWalletKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ChannelFlowTransformLatest lockScreenState;
    public final QuickAccessWalletController walletController;
    public final String key = "wallet";
    public final int pickerIconResourceId = R.drawable.ic_wallet_lockscreen;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(this, null);
        conflatedCallbackFlow.getClass();
        this.lockScreenState = FlowKt.transformLatest(FlowConflatedKt.conflatedCallbackFlow(quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1), new QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPickerScreenState(kotlin.coroutines.Continuation r13) {
        /*
            r12 = this;
            boolean r0 = r13 instanceof com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1
            if (r0 == 0) goto L13
            r0 = r13
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1 r0 = (com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1 r0 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1
            r0.<init>(r12, r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3f
            if (r2 == r5) goto L37
            if (r2 != r4) goto L2f
            java.lang.Object r12 = r0.L$0
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r12 = (com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig) r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto L93
        L2f:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L37:
            java.lang.Object r12 = r0.L$0
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r12 = (com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig) r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto L61
        L3f:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.wallet.controller.QuickAccessWalletController r13 = r12.walletController
            android.service.quickaccesswallet.QuickAccessWalletClient r13 = r13.mQuickAccessWalletClient
            boolean r13 = r13.isWalletServiceAvailable()
            if (r13 != 0) goto L4f
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$UnavailableOnDevice r12 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE
            goto Lb4
        L4f:
            r0.L$0 = r12
            r0.label = r5
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2 r13 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2
            r13.<init>(r12, r3)
            kotlinx.coroutines.CoroutineDispatcher r2 = r12.backgroundDispatcher
            java.lang.Object r13 = kotlinx.coroutines.BuildersKt.withContext(r2, r13, r0)
            if (r13 != r1) goto L61
            return r1
        L61:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 != 0) goto L7e
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$Disabled r13 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$Disabled
            android.content.Context r12 = r12.context
            r0 = 2131956848(0x7f131470, float:1.9550263E38)
            java.lang.String r7 = r12.getString(r0)
            r8 = 0
            r9 = 0
            r10 = 6
            r11 = 0
            r6 = r13
            r6.<init>(r7, r8, r9, r10, r11)
        L7c:
            r12 = r13
            goto Lb4
        L7e:
            r0.L$0 = r12
            r0.label = r4
            r12.getClass()
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$queryCards$2 r13 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$queryCards$2
            r13.<init>(r12, r3)
            kotlinx.coroutines.CoroutineDispatcher r2 = r12.backgroundDispatcher
            java.lang.Object r13 = kotlinx.coroutines.BuildersKt.withContext(r2, r13, r0)
            if (r13 != r1) goto L93
            return r1
        L93:
            java.util.List r13 = (java.util.List) r13
            boolean r13 = r13.isEmpty()
            if (r13 == 0) goto Laf
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$Disabled r13 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$Disabled
            android.content.Context r12 = r12.context
            r0 = 2131956847(0x7f13146f, float:1.9550261E38)
            java.lang.String r7 = r12.getString(r0)
            r8 = 0
            r9 = 0
            r10 = 6
            r11 = 0
            r6 = r13
            r6.<init>(r7, r8, r9, r10, r11)
            goto L7c
        Laf:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$Default r12 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$Default
            r12.<init>(r3, r5, r3)
        Lb4:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig.getPickerScreenState(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        ActivityTransitionAnimator.Controller controller = null;
        if (expandable != null) {
            Expandable.Companion companion = Expandable.Companion;
            controller = expandable.activityTransitionController(null);
        }
        QuickAccessWalletController quickAccessWalletController = this.walletController;
        quickAccessWalletController.mQuickAccessWalletClient.getWalletPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda2(quickAccessWalletController, this.activityStarter, controller, true));
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.accessibility_wallet_button);
    }
}
