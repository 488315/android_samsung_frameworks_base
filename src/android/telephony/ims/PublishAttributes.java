package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class PublishAttributes implements Parcelable {
    public static final Parcelable.Creator<PublishAttributes> CREATOR = new Parcelable.Creator<PublishAttributes>() { // from class: android.telephony.ims.PublishAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PublishAttributes createFromParcel(Parcel parcel) {
            return new PublishAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PublishAttributes[] newArray(int i) {
            return new PublishAttributes[i];
        }
    };
    private List<RcsContactPresenceTuple> mPresenceTuples;
    private final int mPublishState;
    private SipDetails mSipDetails;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private PublishAttributes mAttributes;

        public Builder(int i) {
            this.mAttributes = new PublishAttributes(i);
        }

        public Builder setSipDetails(SipDetails sipDetails) {
            this.mAttributes.mSipDetails = sipDetails;
            return this;
        }

        public Builder setPresenceTuples(List<RcsContactPresenceTuple> list) {
            this.mAttributes.mPresenceTuples = list;
            return this;
        }

        public PublishAttributes build() {
            return this.mAttributes;
        }
    }

    private PublishAttributes(int i) {
        this.mPublishState = i;
    }

    public int getPublishState() {
        return this.mPublishState;
    }

    public List<RcsContactPresenceTuple> getPresenceTuples() {
        List<RcsContactPresenceTuple> list = this.mPresenceTuples;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public SipDetails getSipDetails() {
        return this.mSipDetails;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPublishState);
        parcel.writeList(this.mPresenceTuples);
        parcel.writeParcelable(this.mSipDetails, 0);
    }

    private PublishAttributes(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mPublishState = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.mPresenceTuples = arrayList;
        parcel.readList(arrayList, null, RcsContactPresenceTuple.class);
        this.mSipDetails = (SipDetails) parcel.readParcelable(SipDetails.class.getClassLoader(), SipDetails.class);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PublishAttributes publishAttributes = (PublishAttributes) obj;
            if (this.mPublishState == publishAttributes.mPublishState && Objects.equals(this.mPresenceTuples, publishAttributes.mPresenceTuples) && Objects.equals(this.mSipDetails, publishAttributes.mSipDetails)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPublishState), this.mPresenceTuples, this.mSipDetails);
    }

    public String toString() {
        return "PublishAttributes { publishState= " + this.mPublishState + ", presenceTuples=[" + this.mPresenceTuples + "]SipDetails=" + this.mSipDetails + "}";
    }
}
