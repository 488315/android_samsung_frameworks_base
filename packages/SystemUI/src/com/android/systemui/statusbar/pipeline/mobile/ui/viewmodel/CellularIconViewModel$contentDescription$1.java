package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class CellularIconViewModel$contentDescription$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$contentDescription$1(CellularIconViewModel cellularIconViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CellularIconViewModel$contentDescription$1 cellularIconViewModel$contentDescription$1 = new CellularIconViewModel$contentDescription$1(this.this$0, (Continuation) obj3);
        cellularIconViewModel$contentDescription$1.L$0 = (SignalIconModel) obj;
        cellularIconViewModel$contentDescription$1.L$1 = (NetworkNameModel) obj2;
        return cellularIconViewModel$contentDescription$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SignalIconModel signalIconModel = (SignalIconModel) this.L$0;
        NetworkNameModel networkNameModel = (NetworkNameModel) this.L$1;
        if (!(signalIconModel instanceof SignalIconModel.Cellular)) {
            return null;
        }
        String name = networkNameModel.getName();
        SignalIconModel.Cellular cellular = (SignalIconModel.Cellular) signalIconModel;
        this.this$0.getClass();
        int i2 = cellular.level;
        if (i2 == 0) {
            i = R.string.accessibility_no_signal;
        } else if (i2 == 1) {
            i = R.string.accessibility_one_bar;
        } else if (i2 == 2) {
            i = R.string.accessibility_two_bars;
        } else if (i2 != 3) {
            int i3 = cellular.numberOfLevels;
            if (i2 != 4) {
                if (i2 == 5 && i3 == 6) {
                }
            } else if (i3 == 6) {
                i = R.string.accessibility_four_bars;
            }
            i = R.string.accessibility_signal_full;
        } else {
            i = R.string.accessibility_three_bars;
        }
        return new MobileContentDescription$Cellular(name, i);
    }
}
