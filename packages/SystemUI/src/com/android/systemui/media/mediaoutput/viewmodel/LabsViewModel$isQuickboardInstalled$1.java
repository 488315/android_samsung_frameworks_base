package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class LabsViewModel$isQuickboardInstalled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LabsViewModel this$0;

    public LabsViewModel$isQuickboardInstalled$1(LabsViewModel labsViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = labsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LabsViewModel$isQuickboardInstalled$1 labsViewModel$isQuickboardInstalled$1 = new LabsViewModel$isQuickboardInstalled$1(this.this$0, continuation);
        labsViewModel$isQuickboardInstalled$1.L$0 = obj;
        return labsViewModel$isQuickboardInstalled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LabsViewModel$isQuickboardInstalled$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            PackageManager packageManager = this.this$0.context.getPackageManager();
            try {
                int i2 = Result.$r8$clinit;
                failure = packageManager.getPackageInfo("com.samsung.android.mdx.quickboard", 1);
            } catch (Throwable th) {
                int i3 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            Boolean valueOf = Boolean.valueOf(((PackageInfo) failure) != null);
            this.L$0 = failure;
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
