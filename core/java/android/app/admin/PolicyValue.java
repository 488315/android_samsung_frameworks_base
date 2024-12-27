package android.app.admin;

import android.os.Parcelable;

import java.util.Objects;

public abstract class PolicyValue<V> implements Parcelable {
    private V mValue;

    public PolicyValue(V v) {
        this.mValue = (V) Objects.requireNonNull(v);
    }

    PolicyValue() {}

    public V getValue() {
        return this.mValue;
    }

    void setValue(V value) {
        this.mValue = value;
    }
}
