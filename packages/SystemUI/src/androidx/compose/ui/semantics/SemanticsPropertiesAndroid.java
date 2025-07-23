package androidx.compose.ui.semantics;

import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsPropertiesAndroid {
    public static final SemanticsPropertiesAndroid INSTANCE = new SemanticsPropertiesAndroid();
    public static final SemanticsPropertyKey TestTagsAsResourceId = new SemanticsPropertyKey("TestTagsAsResourceId", false, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsPropertiesAndroid$TestTagsAsResourceId$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            ((Boolean) obj2).booleanValue();
            return bool;
        }
    });

    private SemanticsPropertiesAndroid() {
    }
}
