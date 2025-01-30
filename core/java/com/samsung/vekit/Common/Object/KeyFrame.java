package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class KeyFrame<T> {
  private long duration;
  private T from;

  /* renamed from: to */
  private T f3099to;

  public KeyFrame(T from, T to, long duration) {
    this.from = from;
    this.f3099to = to;
    this.duration = duration;
  }

  public T getFrom() {
    return this.from;
  }

  public void setFrom(T from) {
    this.from = from;
  }

  public T getTo() {
    return this.f3099to;
  }

  public void setTo(T to) {
    this.f3099to = to;
  }

  public long getDuration() {
    return this.duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }
}
