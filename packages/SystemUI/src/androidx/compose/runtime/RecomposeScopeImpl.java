package androidx.compose.runtime;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.Composer;
import java.util.List;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RecomposeScopeImpl implements RecomposeScope {
    public static final Companion Companion = new Companion(null);
    public Anchor anchor;
    public Function2 block;
    public int currentToken;
    public int flags;
    public RecomposeScopeOwner owner;
    public MutableScatterMap trackedDependencies;
    public MutableObjectIntMap trackedInstances;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void adoptAnchoredScopes$runtime_release(SlotWriter slotWriter, List list, RecomposeScopeOwner recomposeScopeOwner) {
            Object obj;
            List list2 = list;
            if (list2.isEmpty()) {
                return;
            }
            int size = list2.size();
            for (int i = 0; i < size; i++) {
                int anchorIndex = slotWriter.anchorIndex((Anchor) list.get(i));
                int slotIndex = slotWriter.slotIndex(slotWriter.groupIndexToAddress(anchorIndex), slotWriter.groups);
                if (slotIndex < slotWriter.dataIndex(slotWriter.groupIndexToAddress(anchorIndex + 1), slotWriter.groups)) {
                    obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(slotIndex)];
                } else {
                    Composer.Companion.getClass();
                    obj = Composer.Companion.Empty;
                }
                RecomposeScopeImpl recomposeScopeImpl = obj instanceof RecomposeScopeImpl ? (RecomposeScopeImpl) obj : null;
                if (recomposeScopeImpl != null) {
                    recomposeScopeImpl.owner = recomposeScopeOwner;
                }
            }
        }

        private Companion() {
        }
    }

    public RecomposeScopeImpl(RecomposeScopeOwner recomposeScopeOwner) {
        this.owner = recomposeScopeOwner;
    }

    public final boolean getValid() {
        if (this.owner != null) {
            Anchor anchor = this.anchor;
            if (anchor != null ? anchor.getValid() : false) {
                return true;
            }
        }
        return false;
    }

    public final InvalidationResult invalidateForResult(Object obj) {
        InvalidationResult invalidate;
        RecomposeScopeOwner recomposeScopeOwner = this.owner;
        return (recomposeScopeOwner == null || (invalidate = recomposeScopeOwner.invalidate(this, obj)) == null) ? InvalidationResult.IGNORED : invalidate;
    }

    public final void release() {
        RecomposeScopeOwner recomposeScopeOwner = this.owner;
        if (recomposeScopeOwner != null) {
            recomposeScopeOwner.recomposeScopeReleased();
        }
        this.owner = null;
        this.trackedInstances = null;
        this.trackedDependencies = null;
    }

    public final void setRereading(boolean z) {
        if (z) {
            this.flags |= 32;
        } else {
            this.flags &= -33;
        }
    }
}
