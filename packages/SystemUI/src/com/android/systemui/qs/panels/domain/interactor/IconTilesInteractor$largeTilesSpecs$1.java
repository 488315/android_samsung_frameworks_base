package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class IconTilesInteractor$largeTilesSpecs$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ IconTilesInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconTilesInteractor$largeTilesSpecs$1(IconTilesInteractor iconTilesInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = iconTilesInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        IconTilesInteractor$largeTilesSpecs$1 iconTilesInteractor$largeTilesSpecs$1 = new IconTilesInteractor$largeTilesSpecs$1(this.this$0, continuation);
        iconTilesInteractor$largeTilesSpecs$1.L$0 = obj;
        return iconTilesInteractor$largeTilesSpecs$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IconTilesInteractor$largeTilesSpecs$1) create((Set) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.L$0;
        IconTilesInteractor iconTilesInteractor = this.this$0;
        int i = IconTilesInteractor.$r8$clinit;
        iconTilesInteractor.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        IconTilesInteractor$$ExternalSyntheticLambda0 iconTilesInteractor$$ExternalSyntheticLambda0 = new IconTilesInteractor$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = iconTilesInteractor.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("LargeTilesSpecsChange", logLevel, iconTilesInteractor$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = set.toString();
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
