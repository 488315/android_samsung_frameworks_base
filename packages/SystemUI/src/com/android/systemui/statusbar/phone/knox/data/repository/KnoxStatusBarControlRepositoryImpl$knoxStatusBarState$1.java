package com.android.systemui.statusbar.phone.knox.data.repository;

import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.knox.data.model.KnoxStatusBarControlModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KnoxStatusBarControlRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1(KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl, Continuation continuation) {
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
                        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onUpdateStatusBarHidden()", " >> ", "KnoxStatusBarControlRepository", knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel.statusBarHidden, ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden());
                    }
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel, ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden(), false, null, 0, 0, 0, 62));
                }

                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateStatusBarIcons() {
                    KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = KnoxStatusBarControlRepositoryImpl.this;
                    boolean z = knoxStatusBarControlRepositoryImpl2.enableLog;
                    KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
                    if (z) {
                        boolean z2 = knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel.statusBarIconsEnabled;
                        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onUpdateStatusBarIcons()", " >> ", "KnoxStatusBarControlRepository", z2, customSdkMonitor != null && customSdkMonitor.mStatusBarIconsState);
                    }
                    CustomSdkMonitor customSdkMonitor2 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel, false, customSdkMonitor2 != null && customSdkMonitor2.mStatusBarIconsState, null, 0, 0, 0, 61));
                }

                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateStatusBarText() {
                    KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = KnoxStatusBarControlRepositoryImpl.this;
                    boolean z = knoxStatusBarControlRepositoryImpl2.enableLog;
                    KnoxStatusBarControlModel knoxStatusBarControlModel = knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel;
                    KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
                    if (z) {
                        String str = knoxStatusBarControlModel.knoxStatusBarCustomText;
                        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                        MediaSessions$H$$ExternalSyntheticOutline0.m("onUpdateStatusBarText()", str, " >> ", customSdkMonitor == null ? null : customSdkMonitor.mStatusBarText, "KnoxStatusBarControlRepository");
                    }
                    CustomSdkMonitor customSdkMonitor2 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    String str2 = customSdkMonitor2 == null ? null : customSdkMonitor2.mStatusBarText;
                    CustomSdkMonitor customSdkMonitor3 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    int i2 = customSdkMonitor3 == null ? 0 : customSdkMonitor3.mStatusBarTextStyle;
                    CustomSdkMonitor customSdkMonitor4 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    int i3 = customSdkMonitor4 == null ? 0 : customSdkMonitor4.mStatusBarTextSize;
                    CustomSdkMonitor customSdkMonitor5 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel, false, false, str2, i2, i3, customSdkMonitor5 == null ? 0 : customSdkMonitor5.mStatusBarTextWidth, 3));
                    CustomSdkMonitor customSdkMonitor6 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    knoxStatusBarControlModel.knoxStatusBarCustomText = customSdkMonitor6 != null ? customSdkMonitor6.mStatusBarText : null;
                    CustomSdkMonitor customSdkMonitor7 = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
                    knoxStatusBarControlModel.customTextStyle = customSdkMonitor7 == null ? 0 : customSdkMonitor7.mStatusBarTextStyle;
                    knoxStatusBarControlModel.customTextSize = customSdkMonitor7 == null ? 0 : customSdkMonitor7.mStatusBarTextSize;
                    knoxStatusBarControlModel.customTextWidth = customSdkMonitor7 != null ? customSdkMonitor7.mStatusBarTextWidth : 0;
                }
            };
            ((KnoxStateMonitorImpl) this.this$0.knoxStateMonitor).registerCallback(r1);
            KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl2 = this.this$0;
            KnoxStatusBarControlModel knoxStatusBarControlModel = knoxStatusBarControlRepositoryImpl2.knoxStatusBarControlModel;
            KnoxStateMonitor knoxStateMonitor = knoxStatusBarControlRepositoryImpl2.knoxStateMonitor;
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
            ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(KnoxStatusBarControlModel.copy$default(knoxStatusBarControlModel, ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden(), customSdkMonitor != null && customSdkMonitor.mStatusBarIconsState, null, 0, 0, 0, 60));
            final KnoxStatusBarControlRepositoryImpl knoxStatusBarControlRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.knox.data.repository.KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1$$ExternalSyntheticLambda0
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
