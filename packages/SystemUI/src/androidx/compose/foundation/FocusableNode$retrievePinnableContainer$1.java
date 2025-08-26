package androidx.compose.foundation;

import androidx.compose.ui.layout.PinnableContainer;
import androidx.compose.ui.layout.PinnableContainerKt;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
final class FocusableNode$retrievePinnableContainer$1 extends Lambda implements Function0 {
    final /* synthetic */ Ref$ObjectRef<PinnableContainer> $container;
    final /* synthetic */ FocusableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FocusableNode$retrievePinnableContainer$1(Ref$ObjectRef<PinnableContainer> ref$ObjectRef, FocusableNode focusableNode) {
        super(0);
        this.$container = ref$ObjectRef;
        this.this$0 = focusableNode;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [T, java.lang.Object] */
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        this.$container.element = CompositionLocalConsumerModifierNodeKt.currentValueOf(this.this$0, PinnableContainerKt.LocalPinnableContainer);
        return Unit.INSTANCE;
    }
}
