package androidx.compose.ui.semantics;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsPropertyKey<T> {
    public final boolean isImportantForAccessibility;
    public final Function2 mergePolicy;
    public final String name;

    public SemanticsPropertyKey(String str, Function2 function2) {
        this.name = str;
        this.mergePolicy = function2;
    }

    public final void setValue(SemanticsPropertyReceiver semanticsPropertyReceiver, Object obj) {
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(this, obj);
    }

    public final String toString() {
        return "AccessibilityKey: " + this.name;
    }

    public /* synthetic */ SemanticsPropertyKey(String str, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? new Function2() { // from class: androidx.compose.ui.semantics.SemanticsPropertyKey.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return obj == null ? obj2 : obj;
            }
        } : function2);
    }

    public SemanticsPropertyKey(String str, boolean z) {
        this(str, null, 2, 0 == true ? 1 : 0);
        this.isImportantForAccessibility = z;
    }

    public SemanticsPropertyKey(String str, boolean z, Function2 function2) {
        this(str, function2);
        this.isImportantForAccessibility = z;
    }
}
