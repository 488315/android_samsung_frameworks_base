package android.timezone;

import java.util.Objects;

/* loaded from: classes4.dex */
public final class TelephonyNetwork {
    private final com.android.i18n.timezone.TelephonyNetwork mDelegate;

    TelephonyNetwork(com.android.i18n.timezone.TelephonyNetwork telephonyNetwork) {
        this.mDelegate = (com.android.i18n.timezone.TelephonyNetwork) Objects.requireNonNull(telephonyNetwork);
    }

    public String getMcc() {
        return this.mDelegate.getMcc();
    }

    public String getMnc() {
        return this.mDelegate.getMnc();
    }

    public String getCountryIsoCode() {
        return this.mDelegate.getCountryIsoCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mDelegate.equals(((TelephonyNetwork) obj).mDelegate);
    }

    public int hashCode() {
        return Objects.hash(this.mDelegate);
    }

    public String toString() {
        return "TelephonyNetwork{mDelegate=" + this.mDelegate + '}';
    }
}
