package kotlin.text;

import java.util.Iterator;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StringsKt__StringsKt$lineSequence$$inlined$Sequence$1 implements Sequence {
    public final /* synthetic */ CharSequence $this_lineSequence$inlined;

    public StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(CharSequence charSequence) {
        this.$this_lineSequence$inlined = charSequence;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new LinesIterator(this.$this_lineSequence$inlined);
    }
}
