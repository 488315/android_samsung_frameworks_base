package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ObserverNodeOwnerScope implements OwnerScope {
    public static final Companion Companion = new Companion(null);
    public static final Function1 OnObserveReadsChanged = new Function1() { // from class: androidx.compose.ui.node.ObserverNodeOwnerScope$Companion$OnObserveReadsChanged$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            ObserverNodeOwnerScope observerNodeOwnerScope = (ObserverNodeOwnerScope) obj;
            if (observerNodeOwnerScope.isValidOwnerScope()) {
                observerNodeOwnerScope.observerNode.onObservedReadsChanged();
            }
            return Unit.INSTANCE;
        }
    };
    public final ObserverModifierNode observerNode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ObserverNodeOwnerScope(ObserverModifierNode observerModifierNode) {
        this.observerNode = observerModifierNode;
    }

    @Override // androidx.compose.ui.node.OwnerScope
    public final boolean isValidOwnerScope() {
        return ((Modifier.Node) this.observerNode).node.isAttached;
    }
}
