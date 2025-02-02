package com.android.server.pm;

import android.os.Binder;

/* loaded from: classes3.dex */
public class KeySetHandle extends Binder {
  public final long mId;
  public int mRefCount;

  public KeySetHandle(long j) {
    this.mId = j;
    this.mRefCount = 1;
  }

  public KeySetHandle(long j, int i) {
    this.mId = j;
    this.mRefCount = i;
  }

  public long getId() {
    return this.mId;
  }

  public int getRefCountLPr() {
    return this.mRefCount;
  }

  public void setRefCountLPw(int i) {
    this.mRefCount = i;
  }

  public void incrRefCountLPw() {
    this.mRefCount++;
  }

  public int decrRefCountLPw() {
    int i = this.mRefCount - 1;
    this.mRefCount = i;
    return i;
  }
}
