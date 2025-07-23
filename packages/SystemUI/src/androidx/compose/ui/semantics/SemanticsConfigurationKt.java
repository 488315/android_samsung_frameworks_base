package androidx.compose.ui.semantics;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SemanticsConfigurationKt {
    public static final Object getOrNull(SemanticsConfiguration semanticsConfiguration, SemanticsPropertyKey semanticsPropertyKey) {
        SemanticsConfigurationKt$getOrNull$1 semanticsConfigurationKt$getOrNull$1 = new Function0() { // from class: androidx.compose.ui.semantics.SemanticsConfigurationKt$getOrNull$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return null;
            }
        };
        Object obj = semanticsConfiguration.props.get(semanticsPropertyKey);
        return obj == null ? semanticsConfigurationKt$getOrNull$1.invoke() : obj;
    }
}
