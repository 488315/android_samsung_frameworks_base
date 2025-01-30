package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.Arrays;
import libcore.util.EmptyArray;

/* loaded from: classes4.dex */
public class LongArray implements Cloneable {
  private static final int MIN_CAPACITY_INCREMENT = 12;
  private int mSize;
  private long[] mValues;

  private LongArray(long[] array, int size) {
    this.mValues = array;
    this.mSize =
        Preconditions.checkArgumentInRange(size, 0, array.length, Contract.DatabaseSize.PATH);
  }

  public LongArray() {
    this(0);
  }

  public LongArray(int initialCapacity) {
    if (initialCapacity == 0) {
      this.mValues = EmptyArray.LONG;
    } else {
      this.mValues = ArrayUtils.newUnpaddedLongArray(initialCapacity);
    }
    this.mSize = 0;
  }

  public static LongArray wrap(long[] array) {
    return new LongArray(array, array.length);
  }

  public static LongArray fromArray(long[] array, int size) {
    return wrap(Arrays.copyOf(array, size));
  }

  public void resize(int newSize) {
    Preconditions.checkArgumentNonnegative(newSize);
    long[] jArr = this.mValues;
    if (newSize <= jArr.length) {
      Arrays.fill(jArr, newSize, jArr.length, 0L);
    } else {
      ensureCapacity(newSize - this.mSize);
    }
    this.mSize = newSize;
  }

  public void add(long value) {
    add(this.mSize, value);
  }

  public void add(int index, long value) {
    ensureCapacity(1);
    int i = this.mSize;
    int rightSegment = i - index;
    int i2 = i + 1;
    this.mSize = i2;
    ArrayUtils.checkBounds(i2, index);
    if (rightSegment != 0) {
      long[] jArr = this.mValues;
      System.arraycopy(jArr, index, jArr, index + 1, rightSegment);
    }
    this.mValues[index] = value;
  }

  public void addAll(LongArray values) {
    int count = values.mSize;
    ensureCapacity(count);
    System.arraycopy(values.mValues, 0, this.mValues, this.mSize, count);
    this.mSize += count;
  }

  private void ensureCapacity(int count) {
    int currentSize = this.mSize;
    int minCapacity = currentSize + count;
    if (minCapacity >= this.mValues.length) {
      int targetCap = (currentSize < 6 ? 12 : currentSize >> 1) + currentSize;
      int newCapacity = targetCap > minCapacity ? targetCap : minCapacity;
      long[] newValues = ArrayUtils.newUnpaddedLongArray(newCapacity);
      System.arraycopy(this.mValues, 0, newValues, 0, currentSize);
      this.mValues = newValues;
    }
  }

  public void clear() {
    this.mSize = 0;
  }

  /* renamed from: clone, reason: merged with bridge method [inline-methods] */
  public LongArray m5418clone() {
    LongArray clone = null;
    try {
      clone = (LongArray) super.clone();
      clone.mValues = (long[]) this.mValues.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      return clone;
    }
  }

  public long get(int index) {
    ArrayUtils.checkBounds(this.mSize, index);
    return this.mValues[index];
  }

  public void set(int index, long value) {
    ArrayUtils.checkBounds(this.mSize, index);
    this.mValues[index] = value;
  }

  public int indexOf(long value) {
    int n = this.mSize;
    for (int i = 0; i < n; i++) {
      if (this.mValues[i] == value) {
        return i;
      }
    }
    return -1;
  }

  public void remove(int index) {
    ArrayUtils.checkBounds(this.mSize, index);
    long[] jArr = this.mValues;
    System.arraycopy(jArr, index + 1, jArr, index, (this.mSize - index) - 1);
    this.mSize--;
  }

  public int size() {
    return this.mSize;
  }

  public long[] toArray() {
    return Arrays.copyOf(this.mValues, this.mSize);
  }

  public static boolean elementsEqual(LongArray a, LongArray b) {
    if (a == null || b == null) {
      return a == b;
    }
    if (a.mSize != b.mSize) {
      return false;
    }
    for (int i = 0; i < a.mSize; i++) {
      if (a.get(i) != b.get(i)) {
        return false;
      }
    }
    return true;
  }
}
