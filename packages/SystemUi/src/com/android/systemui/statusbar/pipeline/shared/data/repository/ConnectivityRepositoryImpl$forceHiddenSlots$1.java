package com.android.systemui.statusbar.pipeline.shared.data.repository;

import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.tuner.TunerService;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1", m277f = "ConnectivityRepository.kt", m278l = {130}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ConnectivityRepositoryImpl$forceHiddenSlots$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityInputLogger $logger;
    final /* synthetic */ TunerService $tunerService;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConnectivityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectivityRepositoryImpl$forceHiddenSlots$1(TunerService tunerService, ConnectivityInputLogger connectivityInputLogger, ConnectivityRepositoryImpl connectivityRepositoryImpl, Continuation<? super ConnectivityRepositoryImpl$forceHiddenSlots$1> continuation) {
        super(2, continuation);
        this.$tunerService = tunerService;
        this.$logger = connectivityInputLogger;
        this.this$0 = connectivityRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConnectivityRepositoryImpl$forceHiddenSlots$1 connectivityRepositoryImpl$forceHiddenSlots$1 = new ConnectivityRepositoryImpl$forceHiddenSlots$1(this.$tunerService, this.$logger, this.this$0, continuation);
        connectivityRepositoryImpl$forceHiddenSlots$1.L$0 = obj;
        return connectivityRepositoryImpl$forceHiddenSlots$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConnectivityRepositoryImpl$forceHiddenSlots$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1, com.android.systemui.tuner.TunerService$Tunable] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ConnectivityInputLogger connectivityInputLogger = this.$logger;
            final ConnectivityRepositoryImpl connectivityRepositoryImpl = this.this$0;
            final ?? r1 = new TunerService.Tunable() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1
                /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
                
                    if (r5 == null) goto L9;
                 */
                @Override // com.android.systemui.tuner.TunerService.Tunable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onTuningChanged(String str, String str2) {
                    Set set;
                    if (Intrinsics.areEqual(str, "icon_blacklist")) {
                        ConnectivityInputLogger.this.logTuningChanged(str2);
                        ConnectivityRepositoryImpl connectivityRepositoryImpl2 = connectivityRepositoryImpl;
                        if (str2 != null) {
                            set = ConnectivityRepositoryImpl.Companion.access$toSlotSet(ConnectivityRepositoryImpl.Companion, StringsKt__StringsKt.split$default(str2, new String[]{","}, 0, 6), connectivityRepositoryImpl2.connectivitySlots);
                        }
                        set = connectivityRepositoryImpl2.defaultHiddenIcons;
                        ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(set);
                    }
                }
            };
            this.$tunerService.addTunable(r1, "icon_blacklist");
            final TunerService tunerService = this.$tunerService;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TunerService.this.removeTunable(r1);
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
