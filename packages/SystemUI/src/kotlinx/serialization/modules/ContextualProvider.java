package kotlinx.serialization.modules;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ContextualProvider {
    public /* synthetic */ ContextualProvider(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract KSerializer invoke();

    private ContextualProvider() {
    }
}
