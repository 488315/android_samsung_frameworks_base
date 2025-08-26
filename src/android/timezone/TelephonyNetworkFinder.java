package android.timezone;

import com.android.internal.hidden_from_bootclasspath.com.android.icu.Flags;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TelephonyNetworkFinder {
    private final com.android.i18n.timezone.TelephonyNetworkFinder mDelegate;

    TelephonyNetworkFinder(com.android.i18n.timezone.TelephonyNetworkFinder telephonyNetworkFinder) {
        this.mDelegate = (com.android.i18n.timezone.TelephonyNetworkFinder) Objects.requireNonNull(telephonyNetworkFinder);
    }

    public TelephonyNetwork findNetworkByMccMnc(String str, String str2) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        com.android.i18n.timezone.TelephonyNetwork telephonyNetworkFindNetworkByMccMnc = this.mDelegate.findNetworkByMccMnc(str, str2);
        if (telephonyNetworkFindNetworkByMccMnc != null) {
            return new TelephonyNetwork(telephonyNetworkFindNetworkByMccMnc);
        }
        return null;
    }

    public MobileCountries findCountriesByMcc(String str) {
        if (!Flags.telephonyLookupMccExtension()) {
            return null;
        }
        Objects.requireNonNull(str);
        com.android.i18n.timezone.MobileCountries mobileCountriesFindCountriesByMcc = this.mDelegate.findCountriesByMcc(str);
        if (mobileCountriesFindCountriesByMcc != null) {
            return new MobileCountries(mobileCountriesFindCountriesByMcc);
        }
        return null;
    }
}
