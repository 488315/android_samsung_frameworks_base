package com.android.systemui.statusbar.phone.knox.data.repository;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.knox.data.model.KnoxStatusBarControlModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.phone.knox.data.repository.KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1", m277f = "KnoxStatusBarControlRepository.kt", m278l = {85}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KnoxStatusBarControlRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1(KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl, Continuation<? super KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1> continuation) {
        super(2, continuation);
        this.this$0 = knoxStatusBarControlRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1 knoxStatusBarControlRepositoryImpl$knoxStatusBarState$1 = new KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1(this.this$0, continuation);
        knoxStatusBarControlRepositoryImpl$knoxStatusBarState$1.L$0 = obj;
        return knoxStatusBarControlRepositoryImpl$knoxStatusBarState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.knox.KnoxStateMonitorCallback, com.android.systemui.statusbar.phone.knox.data.repository.KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl = this.this$0;
            final ?? r1 = new KnoxStateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.knox.data.repository.KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1$callback$1
                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateStatusBarHidden() {
                    KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = KnoxStatusBarControlRepositoryImpl.this;
                    boolean z = knoxStatusBarControlRepositoryImpl2.enableLog;
                    KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
                    if (z) {
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Status bar hidden is called=", ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden(), "KnoxStatusBarControlRepository");
                    }
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel, ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden(), false, null, 0, 0, 0, 62));
                }

                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateStatusBarIcons() {
                    KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = KnoxStatusBarControlRepositoryImpl.this;
                    boolean z = knoxStatusBarControlRepositoryImpl2.enableLog;
                    KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
                    if (z) {
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Status bar icon is called=", ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden(), "KnoxStatusBarControlRepository");
                    }
                    KnoxStatusBarControlModel knoxStatusBarControlModel = knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel;
                    CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlModel, false, customSdkMonitor != null && customSdkMonitor.mStatusBarIconsState, null, 0, 0, 0, 61));
                }

                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateStatusBarText() {
                    KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = KnoxStatusBarControlRepositoryImpl.this;
                    boolean z = knoxStatusBarControlRepositoryImpl2.enableLog;
                    KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
                    if (z) {
                        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                        AbstractC0000x2c234b15.m3m("Status bar text is updated=", customSdkMonitor == null ? null : customSdkMonitor.mStatusBarText, "KnoxStatusBarControlRepository");
                    }
                    KnoxStatusBarControlModel knoxStatusBarControlModel = knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel;
                    CustomSdkMonitor customSdkMonitor2 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    String str = customSdkMonitor2 != null ? customSdkMonitor2.mStatusBarText : null;
                    CustomSdkMonitor customSdkMonitor3 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    int i2 = customSdkMonitor3 == null ? 0 : customSdkMonitor3.mStatusBarTextStyle;
                    CustomSdkMonitor customSdkMonitor4 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    int i3 = customSdkMonitor4 == null ? 0 : customSdkMonitor4.mStatusBarTextSize;
                    CustomSdkMonitor customSdkMonitor5 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlModel, false, false, str, i2, i3, customSdkMonitor5 == null ? 0 : customSdkMonitor5.mStatusBarTextWidth, 3));
                }
            };
            ((KnoxStateMonitorImpl) this.this$0.knoxStateMonitor).registerCallback(r1);
            KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = this.this$0;
            KnoxStatusBarControlModel knoxStatusBarControlModel = knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel;
            KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
            ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlModel, ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden(), customSdkMonitor != null && customSdkMonitor.mStatusBarIconsState, null, 0, 0, 0, 60));
            final KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.knox.data.repository.KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((KnoxStateMonitorImpl) KnoxStatusBarControlRepositoryImpl.this.knoxStateMonitor).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
