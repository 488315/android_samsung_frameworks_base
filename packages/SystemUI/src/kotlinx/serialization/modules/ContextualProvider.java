package kotlinx.serialization.modules;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public abstract class ContextualProvider {
    public /* synthetic */ ContextualProvider(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract KSerializer invoke();

    private ContextualProvider() {
    }
}
