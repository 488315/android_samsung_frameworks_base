package android.util;

/* loaded from: classes4.dex */
public interface TrustedTime {
  @Deprecated
  long currentTimeMillis();

  @Deprecated
  boolean forceRefresh();

  @Deprecated
  long getCacheAge();

  @Deprecated
  boolean hasCache();
}
