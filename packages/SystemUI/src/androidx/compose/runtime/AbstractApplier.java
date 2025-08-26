package androidx.compose.runtime;

import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class AbstractApplier<T> implements Applier<T> {
    public Object current;
    public final Object root;
    public final ArrayList stack = new ArrayList();

    public AbstractApplier(T t) {
        this.root = t;
        this.current = t;
    }

    @Override // androidx.compose.runtime.Applier
    public final void clear() {
        this.stack.clear();
        this.current = this.root;
        onClear();
    }

    @Override // androidx.compose.runtime.Applier
    public final void down(Object obj) {
        this.stack.add(this.current);
        this.current = obj;
    }

    @Override // androidx.compose.runtime.Applier
    public final Object getCurrent() {
        return this.current;
    }

    public abstract void onClear();

    @Override // androidx.compose.runtime.Applier
    public final void up() {
        this.current = this.stack.remove(r0.size() - 1);
    }
}
