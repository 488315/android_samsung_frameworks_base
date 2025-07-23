package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WrapContentElement$Companion$height$1 extends Lambda implements Function2 {
    final /* synthetic */ Alignment.Vertical $align;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrapContentElement$Companion$height$1(Alignment.Vertical vertical) {
        super(2);
        this.$align = vertical;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        long j = ((IntSize) obj).packedValue;
        return IntOffset.m847boximpl((((BiasAlignment.Vertical) this.$align).align(0, (int) (j & 4294967295L)) & 4294967295L) | (0 << 32));
    }
}
