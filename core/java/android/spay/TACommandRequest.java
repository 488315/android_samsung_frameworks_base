package android.spay;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class TACommandRequest implements Parcelable {
    public static final int HEADER_SIZE = 100;
    public static final int MAX_BUFFER_SIZE = 5242880;
    public static final int MAX_DATA_TRANSACTION_SIZE = 3072;
    public static final int PAYLOAD_SIZE = 2972;
    private static final String TAG = "TACommandRequest";
    public int mCommandId;
    public int mLength;
    public byte[] mMagicNum;
    public int mOffset;
    public byte[] mRequest;
    public int mVersion;
    private static boolean DEBUG = true;
    public static final Parcelable.Creator<TACommandRequest> CREATOR = new Parcelable.Creator<TACommandRequest>() { // from class: android.spay.TACommandRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest createFromParcel(Parcel in) {
            return new TACommandRequest(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest[] newArray(int size) {
            return new TACommandRequest[size];
        }
    };

    public TACommandRequest() {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
    }

    public void init(int version, byte[] magic, int cmdId, byte[] request) {
        this.mVersion = version;
        this.mMagicNum = magic;
        this.mCommandId = cmdId;
        this.mRequest = request;
        if (request != null) {
            this.mLength = request.length;
        } else {
            this.mLength = 0;
        }
        this.mOffset = 0;
    }

    private TACommandRequest(Parcel in) {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.mVersion);
        byte[] bArr = this.mMagicNum;
        if (bArr == null || bArr.length != 4) {
            out.writeInt(0);
        } else {
            out.writeInt(bArr.length);
            out.writeByteArray(this.mMagicNum);
        }
        out.writeInt(this.mCommandId);
        out.writeInt(this.mLength);
        out.writeInt(this.mOffset);
        byte[] bArr2 = this.mRequest;
        if (bArr2 == null || bArr2.length == 0) {
            out.writeInt(0);
        } else {
            out.writeInt(bArr2.length);
            out.writeByteArray(this.mRequest);
        }
    }

    public void readFromParcel(Parcel in) {
        this.mVersion = in.readInt();
        int len = in.readInt();
        if (len > 0) {
            byte[] bArr = new byte[len];
            this.mMagicNum = bArr;
            in.readByteArray(bArr);
        }
        this.mCommandId = in.readInt();
        this.mLength = in.readInt();
        this.mOffset = in.readInt();
        int len2 = in.readInt();
        if (len2 > 0) {
            byte[] bArr2 = new byte[len2];
            this.mRequest = bArr2;
            in.readByteArray(bArr2);
        }
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TACommandRequest(int _version, byte[] _magicNum, int _commandId, int _length, int _offset, byte[] _requestPayload) {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
        this.mVersion = _version;
        this.mMagicNum = _magicNum;
        this.mCommandId = _commandId;
        this.mLength = _length;
        this.mOffset = _offset;
        this.mRequest = _requestPayload;
    }

    public int getTotalLength() {
        return this.mLength;
    }

    public int getChunkOffset() {
        return this.mOffset;
    }

    public byte[] getPayload() {
        return this.mRequest;
    }

    public List<TACommandRequest> disassemble() {
        int i;
        List<TACommandRequest> arr = new ArrayList<>();
        byte[] bArr = this.mRequest;
        if (bArr == null) {
            return null;
        }
        if (bArr.length <= 2972) {
            arr.add(this);
            if (DEBUG) {
                Log.m98i(TAG, "no need to divide the mRequest, len=" + this.mRequest.length);
            }
            return arr;
        }
        if (DEBUG) {
            Log.m98i(TAG, "dividing the mRequest, len=" + this.mRequest.length);
        }
        int offset = 0;
        while (true) {
            int i2 = offset + 2972;
            i = this.mLength;
            if (i2 >= i) {
                break;
            }
            if (DEBUG) {
                Log.m98i(TAG, "generating the chunk from " + offset + " to " + ((offset + 2972) - 1));
            }
            arr.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, this.mLength, offset, Arrays.copyOfRange(this.mRequest, offset, offset + 2972)));
            offset += 2972;
        }
        arr.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, i, offset, Arrays.copyOfRange(this.mRequest, offset, i)));
        if (DEBUG) {
            Log.m98i(TAG, "generating the chunk from " + offset + " to " + (this.mLength - 1));
        }
        return arr;
    }

    public void dump() {
        Log.m94d(TAG, "Command ID = " + this.mCommandId);
        Log.m94d(TAG, "Length = " + this.mRequest.length);
        StringBuilder hex = new StringBuilder((this.mRequest.length * 2) + 100);
        hex.append("{");
        int i = 0;
        while (true) {
            byte[] bArr = this.mRequest;
            if (i >= bArr.length) {
                break;
            }
            hex.append(String.format("0x%02X", Byte.valueOf(bArr[i])));
            if (i != this.mRequest.length) {
                hex.append(", ");
            }
            i++;
        }
        hex.append("}");
        Log.m94d(TAG, hex.toString());
        FileWriter outFile = null;
        BufferedWriter bw = null;
        try {
            try {
                try {
                    outFile = new FileWriter("/mnt/sdcard/sendbuf.txt", false);
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
