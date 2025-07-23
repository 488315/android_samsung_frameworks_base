package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return IntOffset.m847boximpl(alignment.mo352alignKFBX0sM(0L, ((IntSize) obj).packedValue, (LayoutDirection) obj2));
    }
}
