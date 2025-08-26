package com.android.systemui.keyguard.data.quickaffordance;

import android.graphics.drawable.Drawable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(Continuation continuation, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig) {
        super(3, continuation);
        this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a1, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r5, r1, r14) != r0) goto L26;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        FlowCollector flowCollector;
        QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig;
        Boolean bool;
        Object visible;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            Boolean bool2 = (Boolean) this.L$1;
            if (bool2 != null) {
                quickAccessWalletKeyguardQuickAffordanceConfig = this.this$0;
                this.L$0 = flowCollector;
                this.L$1 = bool2;
                this.L$2 = quickAccessWalletKeyguardQuickAffordanceConfig;
                this.label = 1;
                int i2 = QuickAccessWalletKeyguardQuickAffordanceConfig.$r8$clinit;
                quickAccessWalletKeyguardQuickAffordanceConfig.getClass();
                Object objWithContext = BuildersKt.withContext(quickAccessWalletKeyguardQuickAffordanceConfig.backgroundDispatcher, new QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2(quickAccessWalletKeyguardQuickAffordanceConfig, null), this);
                if (objWithContext != coroutineSingletons) {
                    bool = bool2;
                    obj = objWithContext;
                }
                return coroutineSingletons;
            }
            visible = KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(visible);
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            quickAccessWalletKeyguardQuickAffordanceConfig = (QuickAccessWalletKeyguardQuickAffordanceConfig) this.L$2;
            bool = (Boolean) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = bool.booleanValue();
        Drawable tileIcon = this.this$0.walletController.mQuickAccessWalletClient.getTileIcon();
        quickAccessWalletKeyguardQuickAffordanceConfig.getClass();
        visible = (zBooleanValue && zBooleanValue2 && tileIcon != null) ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(tileIcon, new ContentDescription.Resource(R.string.accessibility_wallet_button), null, 4, null), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(visible);
        this.L$0 = null;
        this.L$1 = null;
        this.L$2 = null;
        this.label = 2;
    }
}
