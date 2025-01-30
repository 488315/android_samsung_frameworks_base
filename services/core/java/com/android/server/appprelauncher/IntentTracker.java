package com.android.server.appprelauncher;

import android.content.Intent;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Slog;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class IntentTracker {
  public static final boolean DEBUG = Build.IS_DEBUGGABLE;
  public static final long INTENT_TRACK_TIMEOUT_NS = TimeUnit.MILLISECONDS.toNanos(5000);
  public final Object mLock;
  public final Map mIntentTrackerItems = new ArrayMap();
  public int mNextTrackId = 1;

  public IntentTracker(Object obj) {
    this.mLock = obj;
  }

  public final class IntentTrackerItem {
    public final long mBeginTrackTimeNs = System.nanoTime();
    public final Intent mIntent;
    public final String mPackageName;
    public final int mTrackId;
    public final int mUserId;

    public IntentTrackerItem(String str, int i, Intent intent, int i2) {
      this.mIntent = intent;
      this.mPackageName = str;
      this.mUserId = i;
      this.mTrackId = i2;
    }

    public long getElapsedNs() {
      return System.nanoTime() - this.mBeginTrackTimeNs;
    }

    public boolean isValid() {
      return getElapsedNs() < IntentTracker.INTENT_TRACK_TIMEOUT_NS;
    }

    public String toString() {
      return "IntentTrackerItem { mTrackId: "
          + this.mTrackId
          + ", mPackageName: "
          + this.mPackageName
          + ", mUserId: "
          + this.mUserId
          + ", mIntent: "
          + this.mIntent
          + ", elapsed: "
          + TimeUnit.NANOSECONDS.toMillis(getElapsedNs())
          + " ms }";
    }
  }

  public void validateLocked() {
    for (IntentTrackerItem intentTrackerItem : this.mIntentTrackerItems.values()) {
      if (!intentTrackerItem.isValid()) {
        Slog.w("IntentTracker", "Found too old track " + intentTrackerItem);
        stopTrackIntent(intentTrackerItem.mTrackId);
      }
    }
  }

  public int startTrackIntent(Intent intent, int i) {
    IntentTrackerItem intentTrackerItem;
    if (intent == null || intent.getComponent() == null) {
      return 0;
    }
    String packageName = intent.getComponent().getPackageName();
    synchronized (this.mLock) {
      validateLocked();
      int i2 = this.mNextTrackId;
      this.mNextTrackId = i2 + 1;
      intentTrackerItem = new IntentTrackerItem(packageName, i, intent, i2);
      this.mIntentTrackerItems.put(Integer.valueOf(intentTrackerItem.mTrackId), intentTrackerItem);
    }
    if (DEBUG) {
      Slog.d("IntentTracker", "Start tracking " + intentTrackerItem);
    }
    return intentTrackerItem.mTrackId;
  }

  public void stopTrackIntent(int i) {
    IntentTrackerItem intentTrackerItem;
    synchronized (this.mLock) {
      intentTrackerItem = (IntentTrackerItem) this.mIntentTrackerItems.remove(Integer.valueOf(i));
    }
    if (DEBUG) {
      Slog.d("IntentTracker", "End tracking " + intentTrackerItem);
    }
  }

  public IntentTrackerItem findTrack(String str, int i) {
    if (str == null) {
      Slog.e("IntentTracker", "Provided invalid package name");
      return null;
    }
    synchronized (this.mLock) {
      validateLocked();
      for (IntentTrackerItem intentTrackerItem : this.mIntentTrackerItems.values()) {
        if (str.equals(intentTrackerItem.mPackageName) && i == intentTrackerItem.mUserId) {
          return intentTrackerItem;
        }
      }
      if (DEBUG) {
        Slog.d("IntentTracker", "Track not found for " + str + " userId " + i);
      }
      return null;
    }
  }

  public boolean hasTrack(String str, int i) {
    return findTrack(str, i) != null;
  }
}
