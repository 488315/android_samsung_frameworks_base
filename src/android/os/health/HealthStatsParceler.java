package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class HealthStatsParceler implements Parcelable {
    public static final Parcelable.Creator<HealthStatsParceler> CREATOR = new Parcelable.Creator<HealthStatsParceler>() { // from class: android.os.health.HealthStatsParceler.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HealthStatsParceler createFromParcel(Parcel parcel) {
            return new HealthStatsParceler(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HealthStatsParceler[] newArray(int i) {
            return new HealthStatsParceler[i];
        }
    };
    private HealthStats mHealthStats;
    private HealthStatsWriter mWriter;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HealthStatsParceler(HealthStatsWriter healthStatsWriter) {
        this.mWriter = healthStatsWriter;
    }

    public HealthStatsParceler(Parcel parcel) {
        this.mHealthStats = new HealthStats(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        HealthStatsWriter healthStatsWriter = this.mWriter;
        if (healthStatsWriter != null) {
            healthStatsWriter.flattenToParcel(parcel);
            return;
        }
        throw new RuntimeException("Can not re-parcel HealthStatsParceler that was constructed from a Parcel");
    }

    public HealthStats getHealthStats() {
        if (this.mWriter != null) {
            Parcel parcelObtain = Parcel.obtain();
            this.mWriter.flattenToParcel(parcelObtain);
            parcelObtain.setDataPosition(0);
            this.mHealthStats = new HealthStats(parcelObtain);
            parcelObtain.recycle();
        }
        return this.mHealthStats;
    }
}
