package kotlin.text;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
