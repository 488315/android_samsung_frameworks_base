package androidx.compose.ui.node;

import androidx.compose.ui.modifier.ModifierLocalReadScope;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
        public final Object mo781invoke(Object obj) {
            ((BackwardsCompatNode) obj).updateModifierLocalConsumer();
            return Unit.INSTANCE;
        }
    };
}
