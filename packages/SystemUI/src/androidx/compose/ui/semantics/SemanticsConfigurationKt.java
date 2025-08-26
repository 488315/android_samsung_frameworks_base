package androidx.compose.ui.semantics;

import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class SemanticsConfigurationKt {
    public static final Object getOrNull(SemanticsConfiguration semanticsConfiguration, SemanticsPropertyKey semanticsPropertyKey) {
        AnonymousClass1 anonymousClass1 = new Function0() { // from class: androidx.compose.ui.semantics.SemanticsConfigurationKt.getOrNull.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return null;
            }
        };
        Object obj = semanticsConfiguration.props.get(semanticsPropertyKey);
        return obj == null ? anonymousClass1.invoke() : obj;
    }
}
