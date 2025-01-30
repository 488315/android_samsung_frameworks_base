package androidx.picker.features.observable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StringState implements MutableState {
    public String value;

    public StringState(String str) {
        this.value = str;
    }

    @Override // androidx.picker.features.observable.MutableState
    public final Object getValue() {
        return this.value;
    }

    @Override // androidx.picker.features.observable.MutableState
    public final void setValue(Object obj) {
        this.value = (String) obj;
    }
}
