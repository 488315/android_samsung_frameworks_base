package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DrawWithCacheElement extends ModifierNodeElement<CacheDrawModifierNodeImpl> {
    public final Function1 onBuildDrawCache;

    public DrawWithCacheElement(Function1 function1) {
        this.onBuildDrawCache = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new CacheDrawModifierNodeImpl(new CacheDrawScope(), this.onBuildDrawCache);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DrawWithCacheElement) {
            return this.onBuildDrawCache == ((DrawWithCacheElement) obj).onBuildDrawCache;
        }
        return false;
    }

    public final int hashCode() {
        return this.onBuildDrawCache.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        CacheDrawModifierNodeImpl cacheDrawModifierNodeImpl = (CacheDrawModifierNodeImpl) node;
        cacheDrawModifierNodeImpl.block = this.onBuildDrawCache;
        cacheDrawModifierNodeImpl.invalidateDrawCache();
    }
}
