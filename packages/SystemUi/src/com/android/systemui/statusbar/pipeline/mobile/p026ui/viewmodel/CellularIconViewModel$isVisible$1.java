package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$isVisible$1", m277f = "MobileIconViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CellularIconViewModel$isVisible$1 extends SuspendLambda implements Function5 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$isVisible$1(CellularIconViewModel cellularIconViewModel, Continuation<? super CellularIconViewModel$isVisible$1> continuation) {
        super(5, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        CellularIconViewModel$isVisible$1 cellularIconViewModel$isVisible$1 = new CellularIconViewModel$isVisible$1(this.this$0, (Continuation) obj5);
        cellularIconViewModel$isVisible$1.Z$0 = booleanValue;
        cellularIconViewModel$isVisible$1.Z$1 = booleanValue2;
        cellularIconViewModel$isVisible$1.Z$2 = booleanValue3;
        cellularIconViewModel$isVisible$1.Z$3 = booleanValue4;
        return cellularIconViewModel$isVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0020, code lost:
    
        r4 = false;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = this.Z$3;
        boolean z5 = false;
        if (!z && !z2) {
            boolean z6 = this.this$0.slotId != 0 ? true : true;
            if (z6) {
                z5 = true;
            }
        }
        return Boolean.valueOf(z5);
    }
}
