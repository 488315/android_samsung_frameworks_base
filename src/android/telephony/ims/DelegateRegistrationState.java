package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class DelegateRegistrationState implements Parcelable {
    public static final Parcelable.Creator<DelegateRegistrationState> CREATOR = new Parcelable.Creator<DelegateRegistrationState>() { // from class: android.telephony.ims.DelegateRegistrationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DelegateRegistrationState createFromParcel(Parcel parcel) {
            return new DelegateRegistrationState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DelegateRegistrationState[] newArray(int i) {
            return new DelegateRegistrationState[i];
        }
    };
    public static final int DEREGISTERED_REASON_NOT_PROVISIONED = 1;
    public static final int DEREGISTERED_REASON_NOT_REGISTERED = 2;
    public static final int DEREGISTERED_REASON_UNKNOWN = 0;
    public static final int DEREGISTERING_REASON_DESTROY_PENDING = 6;
    public static final int DEREGISTERING_REASON_FEATURE_TAGS_CHANGING = 5;
    public static final int DEREGISTERING_REASON_LOSING_PDN = 7;
    public static final int DEREGISTERING_REASON_PDN_CHANGE = 3;
    public static final int DEREGISTERING_REASON_PROVISIONING_CHANGE = 4;
    public static final int DEREGISTERING_REASON_UNSPECIFIED = 8;
    private final ArraySet<FeatureTagState> mDeregisteredTags;
    private final ArraySet<FeatureTagState> mDeregisteringTags;
    private ArraySet<String> mRegisteredTags;
    private ArraySet<String> mRegisteringTags;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeregisteredReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeregisteringReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final DelegateRegistrationState mState = new DelegateRegistrationState();

        public Builder addRegisteringFeatureTags(Set<String> set) {
            this.mState.mRegisteringTags.addAll(set);
            return this;
        }

        public Builder addRegisteredFeatureTag(String str) {
            this.mState.mRegisteredTags.add(str);
            return this;
        }

        public Builder addRegisteredFeatureTags(Set<String> set) {
            this.mState.mRegisteredTags.addAll(set);
            return this;
        }

        public Builder addDeregisteringFeatureTag(String str, int i) {
            this.mState.mDeregisteringTags.add(new FeatureTagState(str, i));
            return this;
        }

        public Builder addDeregisteredFeatureTag(String str, int i) {
            this.mState.mDeregisteredTags.add(new FeatureTagState(str, i));
            return this;
        }

        public DelegateRegistrationState build() {
            return this.mState;
        }
    }

    private DelegateRegistrationState() {
        this.mRegisteringTags = new ArraySet<>();
        this.mRegisteredTags = new ArraySet<>();
        this.mDeregisteringTags = new ArraySet<>();
        this.mDeregisteredTags = new ArraySet<>();
    }

    private DelegateRegistrationState(Parcel parcel) {
        this.mRegisteringTags = new ArraySet<>();
        this.mRegisteredTags = new ArraySet<>();
        ArraySet<FeatureTagState> arraySet = new ArraySet<>();
        this.mDeregisteringTags = arraySet;
        ArraySet<FeatureTagState> arraySet2 = new ArraySet<>();
        this.mDeregisteredTags = arraySet2;
        this.mRegisteredTags = parcel.readArraySet(null);
        readStateFromParcel(parcel, arraySet);
        readStateFromParcel(parcel, arraySet2);
        this.mRegisteringTags = parcel.readArraySet(null);
    }

    public Set<String> getRegisteringFeatureTags() {
        return new ArraySet((ArraySet) this.mRegisteringTags);
    }

    public Set<String> getRegisteredFeatureTags() {
        return new ArraySet((ArraySet) this.mRegisteredTags);
    }

    public Set<FeatureTagState> getDeregisteringFeatureTags() {
        return new ArraySet((ArraySet) this.mDeregisteringTags);
    }

    public Set<FeatureTagState> getDeregisteredFeatureTags() {
        return new ArraySet((ArraySet) this.mDeregisteredTags);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeArraySet(this.mRegisteredTags);
        writeStateToParcel(parcel, this.mDeregisteringTags);
        writeStateToParcel(parcel, this.mDeregisteredTags);
        parcel.writeArraySet(this.mRegisteringTags);
    }

    private void writeStateToParcel(Parcel parcel, Set<FeatureTagState> set) {
        parcel.writeInt(set.size());
        for (FeatureTagState featureTagState : set) {
            parcel.writeString(featureTagState.getFeatureTag());
            parcel.writeInt(featureTagState.getState());
        }
    }

    private void readStateFromParcel(Parcel parcel, Set<FeatureTagState> set) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            set.add(new FeatureTagState(parcel.readString(), parcel.readInt()));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DelegateRegistrationState delegateRegistrationState = (DelegateRegistrationState) obj;
            if (this.mRegisteringTags.equals(delegateRegistrationState.mRegisteringTags) && this.mRegisteredTags.equals(delegateRegistrationState.mRegisteredTags) && this.mDeregisteringTags.equals(delegateRegistrationState.mDeregisteringTags) && this.mDeregisteredTags.equals(delegateRegistrationState.mDeregisteredTags)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mRegisteringTags, this.mRegisteredTags, this.mDeregisteringTags, this.mDeregisteredTags);
    }

    public String toString() {
        return "DelegateRegistrationState{ registered={" + this.mRegisteredTags + "}, registering={" + this.mRegisteringTags + "}, deregistering={" + this.mDeregisteringTags + "}, deregistered={" + this.mDeregisteredTags + "}}";
    }
}
