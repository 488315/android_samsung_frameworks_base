package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
