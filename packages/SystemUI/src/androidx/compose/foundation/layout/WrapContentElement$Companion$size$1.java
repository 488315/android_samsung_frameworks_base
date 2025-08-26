package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class WrapContentElement$Companion$size$1 extends Lambda implements Function2 {
    final /* synthetic */ Alignment $align;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrapContentElement$Companion$size$1(Alignment alignment) {
        super(2);
        this.$align = alignment;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Alignment alignment = this.$align;
        IntSize.Companion.getClass();
        return IntOffset.m849boximpl(alignment.mo353alignKFBX0sM(0L, ((IntSize) obj).packedValue, (LayoutDirection) obj2));
    }
}
