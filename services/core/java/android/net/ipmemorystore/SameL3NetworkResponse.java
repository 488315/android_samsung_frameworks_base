package android.net.ipmemorystore;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class SameL3NetworkResponse {
    public static final int NETWORK_DIFFERENT = 2;
    public static final int NETWORK_NEVER_CONNECTED = 3;
    public static final int NETWORK_SAME = 1;
    public final float confidence;
    public final String l2Key1;
    public final String l2Key2;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkSameness {}

    public SameL3NetworkResponse(SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable) {
        this(
                sameL3NetworkResponseParcelable.l2Key1,
                sameL3NetworkResponseParcelable.l2Key2,
                sameL3NetworkResponseParcelable.confidence);
    }

    public SameL3NetworkResponse(String str, String str2, float f) {
        this.l2Key1 = str;
        this.l2Key2 = str2;
        this.confidence = f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SameL3NetworkResponse)) {
            return false;
        }
        SameL3NetworkResponse sameL3NetworkResponse = (SameL3NetworkResponse) obj;
        return this.l2Key1.equals(sameL3NetworkResponse.l2Key1)
                && this.l2Key2.equals(sameL3NetworkResponse.l2Key2)
                && this.confidence == sameL3NetworkResponse.confidence;
    }

    public final int getNetworkSameness() {
        float f = this.confidence;
        if (f > 1.0d || f < 0.0d) {
            return 3;
        }
        return ((double) f) > 0.5d ? 1 : 2;
    }

    public int hashCode() {
        return Objects.hash(this.l2Key1, this.l2Key2, Float.valueOf(this.confidence));
    }

    public SameL3NetworkResponseParcelable toParcelable() {
        SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable =
                new SameL3NetworkResponseParcelable();
        sameL3NetworkResponseParcelable.l2Key1 = this.l2Key1;
        sameL3NetworkResponseParcelable.l2Key2 = this.l2Key2;
        sameL3NetworkResponseParcelable.confidence = this.confidence;
        return sameL3NetworkResponseParcelable;
    }

    public String toString() {
        int networkSameness = getNetworkSameness();
        if (networkSameness == 1) {
            StringBuilder sb = new StringBuilder("\"");
            sb.append(this.l2Key1);
            sb.append("\" same L3 network as \"");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.l2Key2, "\"");
        }
        if (networkSameness == 2) {
            StringBuilder sb2 = new StringBuilder("\"");
            sb2.append(this.l2Key1);
            sb2.append("\" different L3 network from \"");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb2, this.l2Key2, "\"");
        }
        if (networkSameness != 3) {
            StringBuilder sb3 = new StringBuilder("Buggy sameness value ? \"");
            sb3.append(this.l2Key1);
            sb3.append("\", \"");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb3, this.l2Key2, "\"");
        }
        StringBuilder sb4 = new StringBuilder("\"");
        sb4.append(this.l2Key1);
        sb4.append("\" can't be tested against \"");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb4, this.l2Key2, "\"");
    }
}
