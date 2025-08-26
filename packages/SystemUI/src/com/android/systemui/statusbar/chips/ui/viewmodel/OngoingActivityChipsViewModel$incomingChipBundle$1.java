package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
final class OngoingActivityChipsViewModel$incomingChipBundle$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    /* synthetic */ Object L$4;
    int label;
    final /* synthetic */ OngoingActivityChipsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingActivityChipsViewModel$incomingChipBundle$1(OngoingActivityChipsViewModel ongoingActivityChipsViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = ongoingActivityChipsViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        OngoingActivityChipsViewModel$incomingChipBundle$1 ongoingActivityChipsViewModel$incomingChipBundle$1 = new OngoingActivityChipsViewModel$incomingChipBundle$1(this.this$0, (Continuation) obj6);
        ongoingActivityChipsViewModel$incomingChipBundle$1.L$0 = (OngoingActivityChipModel) obj;
        ongoingActivityChipsViewModel$incomingChipBundle$1.L$1 = (OngoingActivityChipModel) obj2;
        ongoingActivityChipsViewModel$incomingChipBundle$1.L$2 = (OngoingActivityChipModel) obj3;
        ongoingActivityChipsViewModel$incomingChipBundle$1.L$3 = (OngoingActivityChipModel) obj4;
        ongoingActivityChipsViewModel$incomingChipBundle$1.L$4 = (List) obj5;
        return ongoingActivityChipsViewModel$incomingChipBundle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        OngoingActivityChipModel ongoingActivityChipModel = (OngoingActivityChipModel) this.L$0;
        OngoingActivityChipModel ongoingActivityChipModel2 = (OngoingActivityChipModel) this.L$1;
        OngoingActivityChipModel ongoingActivityChipModel3 = (OngoingActivityChipModel) this.L$2;
        OngoingActivityChipModel ongoingActivityChipModel4 = (OngoingActivityChipModel) this.L$3;
        List list = (List) this.L$4;
        LogBuffer logBuffer = this.this$0.logger;
        String str = OngoingActivityChipsViewModel.TAG;
        LogLevel logLevel = LogLevel.INFO;
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, new OngoingActivityChipsViewModel$incomingChipBundle$1$$ExternalSyntheticLambda0(0), null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = ongoingActivityChipModel.getLogName();
        logMessageImpl.str2 = ongoingActivityChipModel2.getLogName();
        logMessageImpl.str3 = ongoingActivityChipModel3.getLogName();
        logBuffer.commit(logMessageObtain);
        LogBuffer logBuffer2 = this.this$0.logger;
        LogMessage logMessageObtain2 = logBuffer2.obtain(str, logLevel, new OngoingActivityChipsViewModel$incomingChipBundle$1$$ExternalSyntheticLambda0(1), null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
        logMessageImpl2.str1 = ongoingActivityChipModel4.getLogName();
        List list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((OngoingActivityChipModel.Active) it.next()).getLogName());
        }
        logMessageImpl2.str2 = arrayList.toString();
        logBuffer2.commit(logMessageObtain2);
        return new OngoingActivityChipsViewModel.ChipBundle(ongoingActivityChipModel, ongoingActivityChipModel2, ongoingActivityChipModel3, ongoingActivityChipModel4, list);
    }
}
