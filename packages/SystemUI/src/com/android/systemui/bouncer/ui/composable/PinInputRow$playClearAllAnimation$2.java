package com.android.systemui.bouncer.ui.composable;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PinInputRow$playClearAllAnimation$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PinInputRow this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputRow$playClearAllAnimation$2(PinInputRow pinInputRow, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinInputRow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PinInputRow$playClearAllAnimation$2 pinInputRow$playClearAllAnimation$2 = new PinInputRow$playClearAllAnimation$2(this.this$0, continuation);
        pinInputRow$playClearAllAnimation$2.L$0 = obj;
        return pinInputRow$playClearAllAnimation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputRow$playClearAllAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            PersistentList persistentList = this.this$0.entries.getReadable$runtime_release().list;
            PinInputRow pinInputRow = this.this$0;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(persistentList, 10));
            int i2 = 0;
            for (Object obj2 : persistentList) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                arrayList.add(BuildersKt.launch$default(coroutineScope, null, null, new PinInputRow$playClearAllAnimation$2$1$1(pinInputRow, i2, (PinInputEntry) obj2, null), 3));
                i2 = i3;
            }
            this.L$0 = persistentList;
            this.label = 1;
            if (AwaitKt.joinAll(arrayList, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            list = persistentList;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return Boolean.valueOf(this.this$0.entries.removeAll(list));
    }
}
