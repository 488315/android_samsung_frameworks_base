package android.service.gatekeeper;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class GateKeeperResponse implements Parcelable {
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    public static final int RESPONSE_SYSTEM_ERROR = -100;
    private byte[] mPayload;
    private final int mResponseCode;
    private boolean mShouldReEnroll;
    private int mTimeout;
    public static final GateKeeperResponse ERROR = createGenericResponse(-1);
    public static final Parcelable.Creator<GateKeeperResponse> CREATOR = new Parcelable.Creator<GateKeeperResponse>() { // from class: android.service.gatekeeper.GateKeeperResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GateKeeperResponse createFromParcel(Parcel parcel) {
            byte[] bArr;
            int i = parcel.readInt();
            if (i == 1) {
                return GateKeeperResponse.createRetryResponse(parcel.readInt());
            }
            if (i == 0) {
                boolean z = parcel.readInt() == 1;
                int i2 = parcel.readInt();
                if (i2 > 0) {
                    bArr = new byte[i2];
                    parcel.readByteArray(bArr);
                } else {
                    bArr = null;
                }
                return GateKeeperResponse.createOkResponse(bArr, z);
            }
            return GateKeeperResponse.createGenericResponse(i);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GateKeeperResponse[] newArray(int i) {
            return new GateKeeperResponse[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private GateKeeperResponse(int i) {
        this.mResponseCode = i;
    }

    public static GateKeeperResponse createGenericResponse(int i) {
        return new GateKeeperResponse(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static GateKeeperResponse createRetryResponse(int i) {
        GateKeeperResponse gateKeeperResponse = new GateKeeperResponse(1);
        gateKeeperResponse.mTimeout = i;
        return gateKeeperResponse;
    }

    public static GateKeeperResponse createOkResponse(byte[] bArr, boolean z) {
        GateKeeperResponse gateKeeperResponse = new GateKeeperResponse(0);
        gateKeeperResponse.mPayload = bArr;
        gateKeeperResponse.mShouldReEnroll = z;
        return gateKeeperResponse;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResponseCode);
        int i2 = this.mResponseCode;
        if (i2 == 1) {
            parcel.writeInt(this.mTimeout);
            return;
        }
        if (i2 == 0) {
            parcel.writeInt(this.mShouldReEnroll ? 1 : 0);
            byte[] bArr = this.mPayload;
            if (bArr != null && bArr.length > 0) {
                parcel.writeInt(bArr.length);
                parcel.writeByteArray(this.mPayload);
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public byte[] getPayload() {
        return this.mPayload;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public boolean getShouldReEnroll() {
        return this.mShouldReEnroll;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }
}
