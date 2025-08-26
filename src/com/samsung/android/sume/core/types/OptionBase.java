package com.samsung.android.sume.core.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class OptionBase implements Parcelable, Serializable {
    private static final String TAG = Def.tagOf((Class<?>) OptionBase.class);
    private final Map<Integer, Object> data;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected OptionBase() {
        this.data = new HashMap();
    }

    protected OptionBase(Parcel parcel) throws ClassNotFoundException, IOException {
        HashMap map = new HashMap();
        this.data = map;
        parcel.readMap(map, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(this.data);
    }

    public OptionBase set(int i) {
        this.data.put(Integer.valueOf(i), null);
        return this;
    }

    public OptionBase set(int i, Object obj) {
        if (this.data.containsKey(Integer.valueOf(i))) {
            Log.w(TAG, "exist option(" + i + ") value will be overwritten: " + this.data.get(Integer.valueOf(i)) + " -> " + obj);
        }
        this.data.put(Integer.valueOf(i), obj);
        return this;
    }

    public <V> V get(int i) {
        return (V) this.data.get(Integer.valueOf(i));
    }

    public <V> V get(int i, V v) {
        return (V) this.data.getOrDefault(Integer.valueOf(i), v);
    }

    public boolean contains(int i) {
        return this.data.containsKey(Integer.valueOf(i));
    }

    public <V> V remove(int i) {
        return (V) this.data.remove(Integer.valueOf(i));
    }

    public void copyTo(OptionBase optionBase) {
        optionBase.data.putAll(this.data);
    }

    protected Map<Integer, Object> getAll() {
        return this.data;
    }

    public Stream<Map.Entry<Integer, Object>> stream() {
        return this.data.entrySet().stream();
    }

    public void clear() {
        this.data.clear();
    }

    public String toString() {
        return Def.tagOf(this) + "opt=" + this.data;
    }
}
