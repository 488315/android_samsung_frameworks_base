package com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$activity$1$1", m277f = "WifiViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiViewModel$activity$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DataActivityModel $default;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiViewModel$activity$1$1(DataActivityModel dataActivityModel, Continuation<? super WifiViewModel$activity$1$1> continuation) {
        super(3, continuation);
        this.$default = dataActivityModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiViewModel$activity$1$1 wifiViewModel$activity$1$1 = new WifiViewModel$activity$1$1(this.$default, (Continuation) obj3);
        wifiViewModel$activity$1$1.L$0 = (DataActivityModel) obj;
        wifiViewModel$activity$1$1.L$1 = (String) obj2;
        return wifiViewModel$activity$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((String) this.L$1) == null ? this.$default : (DataActivityModel) this.L$0;
    }
}
