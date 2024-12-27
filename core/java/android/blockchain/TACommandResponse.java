package android.blockchain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes.dex */
public class TACommandResponse implements Parcelable {
    public static final Parcelable.Creator<TACommandResponse> CREATOR = new Parcelable.Creator<TACommandResponse>() { // from class: android.blockchain.TACommandResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse createFromParcel(Parcel in) {
            return new TACommandResponse(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse[] newArray(int size) {
            return new TACommandResponse[size];
        }
    };
    private static final String TAG = "TACommandResponse";
    public String mErrorMsg;
    public byte[] mResponse;
    public int mResponseCode;

    public TACommandResponse() {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
    }

    public TACommandResponse(int responseId, String errorMsg, byte[] response) {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
        this.mResponseCode = responseId;
        this.mErrorMsg = errorMsg;
        this.mResponse = response;
    }

    private TACommandResponse(Parcel in) {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.mResponseCode);
        out.writeString(this.mErrorMsg);
        out.writeInt(this.mResponse.length);
        out.writeByteArray(this.mResponse);
    }

    public void readFromParcel(Parcel in) {
        this.mResponseCode = in.readInt();
        this.mErrorMsg = in.readString();
        int len = in.readInt();
        this.mResponse = new byte[len];
        in.readByteArray(this.mResponse);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump() {
        Log.d(TAG, "Length = " + this.mResponse.length);
        StringBuilder hex = new StringBuilder((this.mResponse.length * 3) + 100);
        for (int i = 0; i < this.mResponse.length; i++) {
            if (i > 0 && this.mResponse[i] != 0 && this.mResponse[i - 1] == 0) {
                hex.append("\n");
            }
            hex.append(String.format("%02X ", Byte.valueOf(this.mResponse[i])));
        }
        Log.d(TAG, hex.toString());
        FileWriter outFile = null;
        BufferedWriter bw = null;
        try {
            try {
                try {
                    outFile = new FileWriter("/mnt/sdcard/respbuf.txt", false);
                    bw = new BufferedWriter(outFile);
                    bw.append((CharSequence) hex.toString());
                    bw.close();
                    outFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (bw != null) {
                        bw.close();
                    }
                    if (outFile != null) {
                        outFile.close();
                    }
                }
            } catch (Throwable th) {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        throw th;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        throw th;
                    }
                }
                if (outFile != null) {
                    outFile.close();
                }
                throw th;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        } catch (Exception e5) {
            e5.printStackTrace();
        }
    }
}
