package com.android.server.textclassifier;

import com.android.internal.util.Preconditions;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/* loaded from: classes3.dex */
public final class FixedSizeQueue {
  public final Queue mDelegate;
  public final int mMaxSize;
  public final OnEntryEvictedListener mOnEntryEvictedListener;

  public interface OnEntryEvictedListener {
    void onEntryEvicted(Object obj);
  }

  public FixedSizeQueue(int i, OnEntryEvictedListener onEntryEvictedListener) {
    Preconditions.checkArgument(i > 0, "maxSize (%s) must > 0", new Object[] {Integer.valueOf(i)});
    this.mDelegate = new ArrayDeque(i);
    this.mMaxSize = i;
    this.mOnEntryEvictedListener = onEntryEvictedListener;
  }

  public int size() {
    return this.mDelegate.size();
  }

  public boolean add(Object obj) {
    Objects.requireNonNull(obj);
    if (size() == this.mMaxSize) {
      Object remove = this.mDelegate.remove();
      OnEntryEvictedListener onEntryEvictedListener = this.mOnEntryEvictedListener;
      if (onEntryEvictedListener != null) {
        onEntryEvictedListener.onEntryEvicted(remove);
      }
    }
    this.mDelegate.add(obj);
    return true;
  }

  public Object poll() {
    return this.mDelegate.poll();
  }

  public boolean remove(Object obj) {
    Objects.requireNonNull(obj);
    return this.mDelegate.remove(obj);
  }

  public boolean isEmpty() {
    return this.mDelegate.isEmpty();
  }
}
