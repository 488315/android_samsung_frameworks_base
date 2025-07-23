package androidx.compose.ui.node;

import androidx.compose.ui.modifier.ModifierLocalReadScope;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BackwardsCompatNodeKt {
    public static final BackwardsCompatNodeKt$DetachedModifierLocalReadScope$1 DetachedModifierLocalReadScope = new ModifierLocalReadScope() { // from class: androidx.compose.ui.node.BackwardsCompatNodeKt$DetachedModifierLocalReadScope$1
        @Override // androidx.compose.ui.modifier.ModifierLocalReadScope
        public final Object getCurrent(ProvidableModifierLocal providableModifierLocal) {
            return providableModifierLocal.defaultFactory.invoke();
        }
    };
    public static final Function1 updateModifierLocalConsumer = new Function1() { // from class: androidx.compose.ui.node.BackwardsCompatNodeKt$updateModifierLocalConsumer$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            ((BackwardsCompatNode) obj).updateModifierLocalConsumer();
            return Unit.INSTANCE;
        }
    };
}
