package androidx.compose.ui.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ValueElementSequence implements Sequence {
    public final List elements = new ArrayList();

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return ((ArrayList) this.elements).iterator();
    }

    public final void set(Object obj, String str) {
        ((ArrayList) this.elements).add(new ValueElement(str, obj));
    }
}
