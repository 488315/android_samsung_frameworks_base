package com.samsung.android.sume.core.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public abstract class OptionBase implements Parcelable, Serializable {
  private static final String TAG = Def.tagOf((Class<?>) OptionBase.class);
  private final Map<Integer, Object> data;

  protected OptionBase() {
    this.data = new HashMap();
  }

  protected OptionBase(Parcel in) {
    HashMap hashMap = new HashMap();
    this.data = hashMap;
    in.readMap(hashMap, null);
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeMap(this.data);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public OptionBase set(int option) {
    this.data.put(Integer.valueOf(option), null);
    return this;
  }

  public OptionBase set(int option, Object data) {
    if (this.data.containsKey(Integer.valueOf(option))) {
      Log.m102w(
          TAG,
          "exist option("
              + option
              + ") value will be overwritten: "
              + this.data.get(Integer.valueOf(option))
              + " -> "
              + data);
    }
    this.data.put(Integer.valueOf(option), data);
    return this;
  }

  public <V> V get(int i) {
    return (V) this.data.get(Integer.valueOf(i));
  }

  public <V> V get(int i, V v) {
    return (V) this.data.getOrDefault(Integer.valueOf(i), v);
  }

  public boolean contains(int option) {
    return this.data.containsKey(Integer.valueOf(option));
  }

  public <V> V remove(int i) {
    return (V) this.data.remove(Integer.valueOf(i));
  }

  public void copyTo(OptionBase other) {
    other.data.putAll(this.data);
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
