package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class FeatureTagState implements Parcelable {
    public static final Parcelable.Creator<FeatureTagState> CREATOR = new Parcelable.Creator<FeatureTagState>() { // from class: android.telephony.ims.FeatureTagState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureTagState createFromParcel(Parcel parcel) {
            return new FeatureTagState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureTagState[] newArray(int i) {
            return new FeatureTagState[i];
        }
    };
    private final String mFeatureTag;
    private final int mState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FeatureTagState(String str, int i) {
        this.mFeatureTag = str;
        this.mState = i;
    }

    private FeatureTagState(Parcel parcel) {
        this.mFeatureTag = parcel.readString();
        this.mState = parcel.readInt();
    }

    public String getFeatureTag() {
        return this.mFeatureTag;
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mFeatureTag);
        parcel.writeInt(this.mState);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            FeatureTagState featureTagState = (FeatureTagState) obj;
            if (this.mState == featureTagState.mState && this.mFeatureTag.equals(featureTagState.mFeatureTag)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mFeatureTag, Integer.valueOf(this.mState));
    }

    public String toString() {
        return "FeatureTagState{mFeatureTag='" + this.mFeatureTag + ", mState=" + this.mState + '}';
    }
}
