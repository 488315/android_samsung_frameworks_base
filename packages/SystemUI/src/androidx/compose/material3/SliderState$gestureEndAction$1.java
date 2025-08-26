package androidx.compose.material3;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class SliderState$gestureEndAction$1 extends Lambda implements Function0 {
    final /* synthetic */ SliderState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliderState$gestureEndAction$1(SliderState sliderState) {
        super(0);
        this.this$0 = sliderState;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Function0 function0;
        if (!((Boolean) ((SnapshotMutableStateImpl) this.this$0.isDragging$delegate).getValue()).booleanValue() && (function0 = this.this$0.onValueChangeFinished) != null) {
            function0.invoke();
        }
        return Unit.INSTANCE;
    }
}
