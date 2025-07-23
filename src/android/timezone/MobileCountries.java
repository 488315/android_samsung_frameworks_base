package android.timezone;

import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class MobileCountries {
    private final com.android.i18n.timezone.MobileCountries mDelegate;

    MobileCountries(com.android.i18n.timezone.MobileCountries mobileCountries) {
        this.mDelegate = (com.android.i18n.timezone.MobileCountries) Objects.requireNonNull(mobileCountries);
    }

    public String getMcc() {
        return this.mDelegate.getMcc();
    }

    public Set<String> getCountryIsoCodes() {
        return this.mDelegate.getCountryIsoCodes();
    }

    public String getDefaultCountryIsoCode() {
        return this.mDelegate.getDefaultCountryIsoCode();
    }

    public String toString() {
        return "MobileCountries{mDelegate=" + this.mDelegate + '}';
    }
}
