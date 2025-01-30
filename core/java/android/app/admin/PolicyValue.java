package android.app.admin;

import android.p009os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class PolicyValue<V> implements Parcelable {
    private V mValue;

    public PolicyValue(V v) {
        this.mValue = (V) Objects.requireNonNull(v);
    }

    PolicyValue() {
    }

    public V getValue() {
        return this.mValue;
    }

    void setValue(V value) {
        this.mValue = value;
    }
}
