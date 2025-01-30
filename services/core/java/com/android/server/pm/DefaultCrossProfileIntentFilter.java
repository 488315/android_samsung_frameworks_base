package com.android.server.pm;

import android.content.IntentFilter;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class DefaultCrossProfileIntentFilter {
  public final int direction;
  public final WatchedIntentFilter filter;
  public final int flags;
  public final boolean letsPersonalDataIntoProfile;

  public DefaultCrossProfileIntentFilter(
      WatchedIntentFilter watchedIntentFilter, int i, int i2, boolean z) {
    Objects.requireNonNull(watchedIntentFilter);
    this.filter = watchedIntentFilter;
    this.flags = i;
    this.direction = i2;
    this.letsPersonalDataIntoProfile = z;
  }

  public final class Builder {
    public int mDirection;
    public WatchedIntentFilter mFilter = new WatchedIntentFilter();
    public int mFlags;
    public boolean mLetsPersonalDataIntoProfile;

    public Builder(int i, int i2, boolean z) {
      this.mDirection = i;
      this.mFlags = i2;
      this.mLetsPersonalDataIntoProfile = z;
    }

    public Builder addAction(String str) {
      this.mFilter.addAction(str);
      return this;
    }

    public Builder addCategory(String str) {
      this.mFilter.addCategory(str);
      return this;
    }

    public Builder addDataType(String str) {
      try {
        this.mFilter.addDataType(str);
      } catch (IntentFilter.MalformedMimeTypeException unused) {
      }
      return this;
    }

    public Builder addDataScheme(String str) {
      this.mFilter.addDataScheme(str);
      return this;
    }

    public DefaultCrossProfileIntentFilter build() {
      return new DefaultCrossProfileIntentFilter(
          this.mFilter, this.mFlags, this.mDirection, this.mLetsPersonalDataIntoProfile);
    }
  }
}
