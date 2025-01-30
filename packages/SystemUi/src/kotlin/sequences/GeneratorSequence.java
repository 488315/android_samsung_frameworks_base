package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class GeneratorSequence implements Sequence {
    public final Function0 getInitialValue;
    public final Function1 getNextValue;

    public GeneratorSequence(Function0 function0, Function1 function1) {
        this.getInitialValue = function0;
        this.getNextValue = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new GeneratorSequence$iterator$1(this);
    }
}
