package androidx.picker.features.observable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BooleanState implements MutableState {
    public boolean value;

    public BooleanState(boolean z) {
        this.value = z;
    }

    @Override // androidx.picker.features.observable.MutableState
    public final Object getValue() {
        return Boolean.valueOf(this.value);
    }

    @Override // androidx.picker.features.observable.MutableState
    public final void setValue(Object obj) {
        this.value = ((Boolean) obj).booleanValue();
    }
}
