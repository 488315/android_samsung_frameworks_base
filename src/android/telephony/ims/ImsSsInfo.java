package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsSsInfo implements Parcelable {
    public static final int CLIR_OUTGOING_DEFAULT = 0;
    public static final int CLIR_OUTGOING_INVOCATION = 1;
    public static final int CLIR_OUTGOING_SUPPRESSION = 2;
    public static final int CLIR_STATUS_NOT_PROVISIONED = 0;
    public static final int CLIR_STATUS_PROVISIONED_PERMANENT = 1;
    public static final int CLIR_STATUS_TEMPORARILY_ALLOWED = 4;
    public static final int CLIR_STATUS_TEMPORARILY_RESTRICTED = 3;
    public static final int CLIR_STATUS_UNKNOWN = 2;
    public static final Parcelable.Creator<ImsSsInfo> CREATOR = new Parcelable.Creator<ImsSsInfo>() { // from class: android.telephony.ims.ImsSsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsSsInfo createFromParcel(Parcel parcel) {
            return new ImsSsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsSsInfo[] newArray(int i) {
            return new ImsSsInfo[i];
        }
    };
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;
    public static final int NOT_REGISTERED = -1;
    public static final int SERVICE_NOT_PROVISIONED = 0;
    public static final int SERVICE_PROVISIONED = 1;
    public static final int SERVICE_PROVISIONING_UNKNOWN = -1;
    private int mClirInterrogationStatus;
    private int mClirOutgoingState;
    public String mIcbNum;
    public int mProvisionStatus;
    private int mServiceClass;
    public int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClirInterrogationStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClirOutgoingState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceProvisionStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsSsInfo() {
        this.mProvisionStatus = -1;
        this.mClirInterrogationStatus = 2;
        this.mClirOutgoingState = 0;
    }

    public static final class Builder {
        private final ImsSsInfo mImsSsInfo;

        public Builder(int i) {
            ImsSsInfo imsSsInfo = new ImsSsInfo();
            this.mImsSsInfo = imsSsInfo;
            imsSsInfo.mStatus = i;
        }

        public Builder setIncomingCommunicationBarringNumber(String str) {
            this.mImsSsInfo.mIcbNum = str;
            return this;
        }

        public Builder setProvisionStatus(int i) {
            this.mImsSsInfo.mProvisionStatus = i;
            return this;
        }

        public Builder setClirInterrogationStatus(int i) {
            this.mImsSsInfo.mClirInterrogationStatus = i;
            return this;
        }

        public Builder setClirOutgoingState(int i) {
            this.mImsSsInfo.mClirOutgoingState = i;
            return this;
        }

        public Builder setServiceClass(int i) {
            this.mImsSsInfo.mServiceClass = i;
            return this;
        }

        public ImsSsInfo build() {
            return this.mImsSsInfo;
        }
    }

    @Deprecated
    public ImsSsInfo(int i, String str) {
        this.mProvisionStatus = -1;
        this.mClirInterrogationStatus = 2;
        this.mClirOutgoingState = 0;
        this.mStatus = i;
        this.mIcbNum = str;
    }

    private ImsSsInfo(Parcel parcel) {
        this.mProvisionStatus = -1;
        this.mClirInterrogationStatus = 2;
        this.mClirOutgoingState = 0;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mServiceClass);
        parcel.writeString(this.mIcbNum);
        parcel.writeInt(this.mProvisionStatus);
        parcel.writeInt(this.mClirInterrogationStatus);
        parcel.writeInt(this.mClirOutgoingState);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", Status: ");
        sb.append(this.mStatus == 0 ? "disabled" : "enabled");
        sb.append(", ProvisionStatus: ");
        sb.append(provisionStatusToString(this.mProvisionStatus));
        return sb.toString();
    }

    private static String provisionStatusToString(int i) {
        if (i == 0) {
            return "Service not provisioned";
        }
        if (i == 1) {
            return "Service provisioned";
        }
        return "Service provisioning unknown";
    }

    private void readFromParcel(Parcel parcel) {
        this.mStatus = parcel.readInt();
        this.mServiceClass = parcel.readInt();
        this.mIcbNum = parcel.readString();
        this.mProvisionStatus = parcel.readInt();
        this.mClirInterrogationStatus = parcel.readInt();
        this.mClirOutgoingState = parcel.readInt();
    }

    public int getStatus() {
        return this.mStatus;
    }

    @Deprecated
    public String getIcbNum() {
        return this.mIcbNum;
    }

    public String getIncomingCommunicationBarringNumber() {
        return this.mIcbNum;
    }

    public int getProvisionStatus() {
        return this.mProvisionStatus;
    }

    public int getClirOutgoingState() {
        return this.mClirOutgoingState;
    }

    public int getClirInterrogationStatus() {
        return this.mClirInterrogationStatus;
    }

    public int getServiceClass() {
        return this.mServiceClass;
    }

    public int[] getCompatArray(int i) {
        int[] iArr = new int[2];
        if (i == 8) {
            iArr[0] = getClirOutgoingState();
            iArr[1] = getClirInterrogationStatus();
            return iArr;
        }
        if (i == 10) {
            iArr[0] = getProvisionStatus();
        }
        iArr[0] = getStatus();
        iArr[1] = getProvisionStatus();
        return iArr;
    }
}
