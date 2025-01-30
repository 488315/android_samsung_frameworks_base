package android.nfc;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class TransceiveResult implements Parcelable {
    public static final Parcelable.Creator<TransceiveResult> CREATOR = new Parcelable.Creator<TransceiveResult>() { // from class: android.nfc.TransceiveResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransceiveResult createFromParcel(Parcel in) {
            byte[] responseData;
            int result = in.readInt();
            if (result == 0) {
                int responseLength = in.readInt();
                responseData = new byte[responseLength];
                in.readByteArray(responseData);
            } else {
                responseData = null;
            }
            return new TransceiveResult(result, responseData);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransceiveResult[] newArray(int size) {
            return new TransceiveResult[size];
        }
    };
    public static final int RESULT_EXCEEDED_LENGTH = 3;
    public static final int RESULT_FAILURE = 1;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_TAGLOST = 2;
    final byte[] mResponseData;
    final int mResult;

    public TransceiveResult(int result, byte[] data) {
        this.mResult = result;
        this.mResponseData = data;
    }

    public byte[] getResponseOrThrow() throws IOException {
        switch (this.mResult) {
            case 0:
                return this.mResponseData;
            case 1:
            default:
                throw new IOException("Transceive failed");
            case 2:
                throw new TagLostException("Tag was lost.");
            case 3:
                throw new IOException("Transceive length exceeds supported maximum");
        }
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mResult);
        if (this.mResult == 0) {
            dest.writeInt(this.mResponseData.length);
            dest.writeByteArray(this.mResponseData);
        }
    }
}
