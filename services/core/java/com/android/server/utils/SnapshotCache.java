package com.android.server.utils;

import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public abstract class SnapshotCache extends Watcher {
  public static final WeakHashMap sCaches = new WeakHashMap();
  public volatile boolean mSealed;
  public volatile Object mSnapshot;
  public final Object mSource;
  public final Statistics mStatistics;

  public abstract Object createSnapshot();

  public class Statistics {
    public final String mName;
    public final AtomicInteger mReused = new AtomicInteger(0);
    public final AtomicInteger mRebuilt = new AtomicInteger(0);

    public Statistics(String str) {
      this.mName = str;
    }
  }

  public SnapshotCache(Object obj, Watchable watchable, String str) {
    this.mSnapshot = null;
    this.mSealed = false;
    this.mSource = obj;
    watchable.registerObserver(this);
    if (str != null) {
      this.mStatistics = new Statistics(str);
      sCaches.put(this, null);
    } else {
      this.mStatistics = null;
    }
  }

  public SnapshotCache(Object obj, Watchable watchable) {
    this(obj, watchable, null);
  }

  public SnapshotCache() {
    this.mSnapshot = null;
    this.mSealed = false;
    this.mSource = null;
    this.mSealed = true;
    this.mStatistics = null;
  }

  @Override // com.android.server.utils.Watcher
  public final void onChange(Watchable watchable) {
    if (this.mSealed) {
      throw new IllegalStateException("attempt to change a sealed object");
    }
    this.mSnapshot = null;
  }

  public final Object snapshot() {
    Object obj = this.mSnapshot;
    if (obj == null) {
      obj = createSnapshot();
      this.mSnapshot = obj;
      Statistics statistics = this.mStatistics;
      if (statistics != null) {
        statistics.mRebuilt.incrementAndGet();
      }
    } else {
      Statistics statistics2 = this.mStatistics;
      if (statistics2 != null) {
        statistics2.mReused.incrementAndGet();
      }
    }
    return obj;
  }

  public class Sealed extends SnapshotCache {
    @Override // com.android.server.utils.SnapshotCache
    public Object createSnapshot() {
      throw new UnsupportedOperationException("cannot snapshot a sealed snaphot");
    }
  }

  public class Auto extends SnapshotCache {
    public Auto(Snappable snappable, Watchable watchable, String str) {
      super(snappable, watchable, str);
    }

    @Override // com.android.server.utils.SnapshotCache
    public Snappable createSnapshot() {
      return (Snappable) ((Snappable) this.mSource).snapshot();
    }
  }
}
