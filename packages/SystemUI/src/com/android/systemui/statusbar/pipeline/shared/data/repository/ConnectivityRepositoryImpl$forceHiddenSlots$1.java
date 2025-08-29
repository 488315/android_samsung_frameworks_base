package com.android.systemui.statusbar.pipeline.shared.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.tuner.TunerService;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class ConnectivityRepositoryImpl$forceHiddenSlots$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityInputLogger $logger;
    final /* synthetic */ TunerService $tunerService;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConnectivityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectivityRepositoryImpl$forceHiddenSlots$1(TunerService tunerService, ConnectivityInputLogger connectivityInputLogger, ConnectivityRepositoryImpl connectivityRepositoryImpl, Continuation continuation) {
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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ConnectivityInputLogger connectivityInputLogger = this.$logger;
            final ConnectivityRepositoryImpl connectivityRepositoryImpl = this.this$0;
            TunerService.Tunable tunable = new TunerService.Tunable() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1
                @Override // com.android.systemui.tuner.TunerService.Tunable
                public final void onTuningChanged(String str, String str2) {
                    Set setAccess$toSlotSet;
                    List listSplit$default;
                    if (Intrinsics.areEqual(str, "icon_blacklist")) {
                        ConnectivityInputLogger connectivityInputLogger2 = connectivityInputLogger;
                        connectivityInputLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        ConnectivityInputLogger$$ExternalSyntheticLambda0 connectivityInputLogger$$ExternalSyntheticLambda0 = new ConnectivityInputLogger$$ExternalSyntheticLambda0(1);
                        LogBuffer logBuffer = connectivityInputLogger2.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, connectivityInputLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) logMessageObtain).str1 = str2;
                        logBuffer.commit(logMessageObtain);
                        ConnectivityRepositoryImpl connectivityRepositoryImpl2 = connectivityRepositoryImpl;
                        if (str2 == null || (listSplit$default = StringsKt__StringsKt.split$default(str2, new String[]{","}, 0, 6)) == null || (setAccess$toSlotSet = ConnectivityRepositoryImpl.Companion.access$toSlotSet(ConnectivityRepositoryImpl.Companion, listSplit$default, connectivityRepositoryImpl2.connectivitySlots)) == null) {
                            setAccess$toSlotSet = connectivityRepositoryImpl2.defaultHiddenIcons;
                        }
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(setAccess$toSlotSet);
                    }
                }
            };
            this.$tunerService.addTunable(tunable, "icon_blacklist");
            ConnectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0 connectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0 = new ConnectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0(0, this.$tunerService, tunable);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, connectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
