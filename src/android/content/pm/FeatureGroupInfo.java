package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class FeatureGroupInfo implements Parcelable {
    public static final Parcelable.Creator<FeatureGroupInfo> CREATOR = new Parcelable.Creator<FeatureGroupInfo>() { // from class: android.content.pm.FeatureGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureGroupInfo createFromParcel(Parcel parcel) {
            FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
            featureGroupInfo.features = (FeatureInfo[]) parcel.createTypedArray(FeatureInfo.CREATOR);
            return featureGroupInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureGroupInfo[] newArray(int i) {
            return new FeatureGroupInfo[i];
        }
    };
    public FeatureInfo[] features;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FeatureGroupInfo() {
    }

    public FeatureGroupInfo(FeatureGroupInfo featureGroupInfo) {
        this.features = featureGroupInfo.features;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.features, i);
    }
}
