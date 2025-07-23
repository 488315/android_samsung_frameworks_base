package android.security.net.config;

import com.android.internal.logging.nano.MetricsProto;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class Domain {
    public final String hostname;
    public final boolean subdomainsIncluded;

    public Domain(String str, boolean z) {
        if (str == null) {
            throw new NullPointerException("Hostname must not be null");
        }
        this.hostname = str.toLowerCase(Locale.US);
        this.subdomainsIncluded = z;
    }

    public int hashCode() {
        return (this.subdomainsIncluded ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT) ^ this.hostname.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Domain)) {
            return false;
        }
        Domain domain = (Domain) obj;
        return domain.subdomainsIncluded == this.subdomainsIncluded && domain.hostname.equals(this.hostname);
    }
}
