package android.net;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public class ScoredNetwork implements Parcelable {
    public static final String ATTRIBUTES_KEY_BADGING_CURVE = "android.net.attributes.key.BADGING_CURVE";
    public static final String ATTRIBUTES_KEY_HAS_CAPTIVE_PORTAL = "android.net.attributes.key.HAS_CAPTIVE_PORTAL";
    public static final String ATTRIBUTES_KEY_RANKING_SCORE_OFFSET = "android.net.attributes.key.RANKING_SCORE_OFFSET";
    public static final Parcelable.Creator<ScoredNetwork> CREATOR = new Parcelable.Creator<ScoredNetwork>() { // from class: android.net.ScoredNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScoredNetwork createFromParcel(Parcel parcel) {
            return new ScoredNetwork(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScoredNetwork[] newArray(int i) {
            return new ScoredNetwork[i];
        }
    };
    public final Bundle attributes;
    public final boolean meteredHint;
    public final NetworkKey networkKey;
    public final RssiCurve rssiCurve;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ScoredNetwork(NetworkKey networkKey, RssiCurve rssiCurve) {
        this(networkKey, rssiCurve, false);
    }

    public ScoredNetwork(NetworkKey networkKey, RssiCurve rssiCurve, boolean z) {
        this(networkKey, rssiCurve, z, null);
    }

    public ScoredNetwork(NetworkKey networkKey, RssiCurve rssiCurve, boolean z, Bundle bundle) {
        this.networkKey = networkKey;
        this.rssiCurve = rssiCurve;
        this.meteredHint = z;
        this.attributes = bundle;
    }

    private ScoredNetwork(Parcel parcel) {
        this.networkKey = NetworkKey.CREATOR.createFromParcel(parcel);
        if (parcel.readByte() == 1) {
            this.rssiCurve = RssiCurve.CREATOR.createFromParcel(parcel);
        } else {
            this.rssiCurve = null;
        }
        this.meteredHint = parcel.readByte() == 1;
        this.attributes = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.networkKey.writeToParcel(parcel, i);
        if (this.rssiCurve != null) {
            parcel.writeByte((byte) 1);
            this.rssiCurve.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeByte(this.meteredHint ? (byte) 1 : (byte) 0);
        parcel.writeBundle(this.attributes);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ScoredNetwork scoredNetwork = (ScoredNetwork) obj;
            if (Objects.equals(this.networkKey, scoredNetwork.networkKey) && Objects.equals(this.rssiCurve, scoredNetwork.rssiCurve) && Objects.equals(Boolean.valueOf(this.meteredHint), Boolean.valueOf(scoredNetwork.meteredHint)) && bundleEquals(this.attributes, scoredNetwork.attributes)) {
                return true;
            }
        }
        return false;
    }

    private boolean bundleEquals(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null || bundle2 == null || bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (!Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.networkKey, this.rssiCurve, Boolean.valueOf(this.meteredHint), this.attributes);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ScoredNetwork{networkKey=" + this.networkKey + ", rssiCurve=" + this.rssiCurve + ", meteredHint=" + this.meteredHint);
        Bundle bundle = this.attributes;
        if (bundle != null && !bundle.isEmpty()) {
            sb.append(", attributes=" + this.attributes);
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean hasRankingScore() {
        if (this.rssiCurve != null) {
            return true;
        }
        Bundle bundle = this.attributes;
        return bundle != null && bundle.containsKey(ATTRIBUTES_KEY_RANKING_SCORE_OFFSET);
    }

    public int calculateRankingScore(int i) throws UnsupportedOperationException {
        if (!hasRankingScore()) {
            throw new UnsupportedOperationException("Either rssiCurve or rankingScoreOffset is required to calculate the ranking score");
        }
        Bundle bundle = this.attributes;
        int i2 = bundle != null ? bundle.getInt(ATTRIBUTES_KEY_RANKING_SCORE_OFFSET, 0) : 0;
        RssiCurve rssiCurve = this.rssiCurve;
        int lookupScore = rssiCurve != null ? rssiCurve.lookupScore(i) << 8 : 0;
        try {
            return Math.addExact(lookupScore, i2);
        } catch (ArithmeticException unused) {
            return lookupScore < 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    public int calculateBadge(int i) {
        Bundle bundle = this.attributes;
        if (bundle == null || !bundle.containsKey(ATTRIBUTES_KEY_BADGING_CURVE)) {
            return 0;
        }
        return ((RssiCurve) this.attributes.getParcelable(ATTRIBUTES_KEY_BADGING_CURVE, RssiCurve.class)).lookupScore(i);
    }
}
