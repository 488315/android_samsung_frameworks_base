package androidx.compose.ui.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.sequences.Sequence;

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
