package androidx.compose.ui.focus;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class FocusOwnerImpl$focusSearch$1 extends Lambda implements Function1 {
    final /* synthetic */ Function1 $onFound;
    final /* synthetic */ FocusTargetNode $source;
    final /* synthetic */ FocusOwnerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FocusOwnerImpl$focusSearch$1(FocusTargetNode focusTargetNode, FocusOwnerImpl focusOwnerImpl, Function1 function1) {
        super(1);
        this.$source = focusTargetNode;
        this.this$0 = focusOwnerImpl;
        this.$onFound = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        boolean zBooleanValue;
        FocusTargetNode focusTargetNode = (FocusTargetNode) obj;
        if (Intrinsics.areEqual(focusTargetNode, this.$source)) {
            zBooleanValue = false;
        } else {
            if (Intrinsics.areEqual(focusTargetNode, this.this$0.rootFocusNode)) {
                throw new IllegalStateException("Focus search landed at the root.");
            }
            zBooleanValue = ((Boolean) this.$onFound.mo781invoke(focusTargetNode)).booleanValue();
        }
        return Boolean.valueOf(zBooleanValue);
    }
}
