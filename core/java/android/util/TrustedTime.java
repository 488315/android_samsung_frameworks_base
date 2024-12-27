package android.util;

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
