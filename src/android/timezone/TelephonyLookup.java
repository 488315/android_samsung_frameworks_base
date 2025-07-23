package android.timezone;

import java.util.Objects;

/* loaded from: classes4.dex */
public final class TelephonyLookup {
    private static TelephonyLookup sInstance;
    private static final Object sLock = new Object();
    private final com.android.i18n.timezone.TelephonyLookup mDelegate;

    public static TelephonyLookup getInstance() {
        TelephonyLookup telephonyLookup;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new TelephonyLookup(com.android.i18n.timezone.TelephonyLookup.getInstance());
            }
            telephonyLookup = sInstance;
        }
        return telephonyLookup;
    }

    private TelephonyLookup(com.android.i18n.timezone.TelephonyLookup telephonyLookup) {
        this.mDelegate = (com.android.i18n.timezone.TelephonyLookup) Objects.requireNonNull(telephonyLookup);
    }

    public TelephonyNetworkFinder getTelephonyNetworkFinder() {
        com.android.i18n.timezone.TelephonyNetworkFinder telephonyNetworkFinder = this.mDelegate.getTelephonyNetworkFinder();
        if (telephonyNetworkFinder != null) {
            return new TelephonyNetworkFinder(telephonyNetworkFinder);
        }
        return null;
    }
}
