package kotlin.text;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DelimitedRangesSequence implements Sequence {
    public final Function2 getNextMatch;
    public final CharSequence input;
    public final int limit;
    public final int startIndex;

    public DelimitedRangesSequence(CharSequence charSequence, int i, int i2, Function2 function2) {
        this.input = charSequence;
        this.startIndex = i;
        this.limit = i2;
        this.getNextMatch = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new DelimitedRangesSequence$iterator$1(this);
    }
}
