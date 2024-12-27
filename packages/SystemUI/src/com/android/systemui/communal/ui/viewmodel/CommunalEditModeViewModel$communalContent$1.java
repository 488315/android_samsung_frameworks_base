package com.android.systemui.communal.ui.viewmodel;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

final class CommunalEditModeViewModel$communalContent$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalEditModeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalEditModeViewModel$communalContent$1(CommunalEditModeViewModel communalEditModeViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalEditModeViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalEditModeViewModel$communalContent$1 communalEditModeViewModel$communalContent$1 = new CommunalEditModeViewModel$communalContent$1(this.this$0, continuation);
        communalEditModeViewModel$communalContent$1.L$0 = obj;
        return communalEditModeViewModel$communalContent$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalEditModeViewModel$communalContent$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        Logger logger = this.this$0.logger;
        AnonymousClass1 anonymousClass1 = new Function1() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$communalContent$1.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Content updated: ", ((LogMessage) obj2).getStr1());
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, anonymousClass1, null);
        obtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(list, null, null, null, new Function1() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$communalContent$1$2$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ((CommunalContentModel.WidgetContent) obj2).getKey();
            }
        }, 31));
        logger.getBuffer().commit(obtain);
        return Unit.INSTANCE;
    }
}
