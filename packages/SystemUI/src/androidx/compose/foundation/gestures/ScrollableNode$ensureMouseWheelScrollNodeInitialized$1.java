package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Velocity;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class ScrollableNode$ensureMouseWheelScrollNodeInitialized$1 extends AdaptedFunctionReference implements Function2 {
    public ScrollableNode$ensureMouseWheelScrollNodeInitialized$1(Object obj) {
        super(2, obj, ScrollableNode.class, "onWheelScrollStopped", "onWheelScrollStopped-TH1AsA0(J)V", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final /* synthetic */ Object invoke(Object obj, Object obj2) {
        return m79invokesFctU(((Velocity) obj).packedValue);
    }

    /* renamed from: invoke-sF-c-tU, reason: not valid java name */
    public final Object m79invokesFctU(long j) {
        ScrollableNode scrollableNode = (ScrollableNode) this.receiver;
        BuildersKt.launch$default(scrollableNode.nestedScrollDispatcher.getCoroutineScope(), null, null, new ScrollableNode$onWheelScrollStopped$1(scrollableNode, j, null), 3);
        return Unit.INSTANCE;
    }
}
