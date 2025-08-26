package android.blockchain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class TACommandRequest implements Parcelable {
    public static final Parcelable.Creator<TACommandRequest> CREATOR = new Parcelable.Creator<TACommandRequest>() { // from class: android.blockchain.TACommandRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest createFromParcel(Parcel parcel) {
            return new TACommandRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest[] newArray(int i) {
            return new TACommandRequest[i];
        }
    };
    private static boolean DEBUG = true;
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TACommandRequest() {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
    }

    public void init(int i, byte[] bArr, int i2, byte[] bArr2) {
        this.mVersion = i;
        this.mMagicNum = bArr;
        this.mCommandId = i2;
        this.mRequest = bArr2;
        if (bArr2 != null) {
            this.mLength = bArr2.length;
        } else {
            this.mLength = 0;
        }
        this.mOffset = 0;
    }

    private TACommandRequest(Parcel parcel) {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVersion);
        byte[] bArr = this.mMagicNum;
        if (bArr == null || bArr.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.mMagicNum);
        }
        parcel.writeInt(this.mCommandId);
        parcel.writeInt(this.mLength);
        parcel.writeInt(this.mOffset);
        byte[] bArr2 = this.mRequest;
        if (bArr2 == null || bArr2.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr2.length);
            parcel.writeByteArray(this.mRequest);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mVersion = parcel.readInt();
        int i = parcel.readInt();
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.mMagicNum = bArr;
            parcel.readByteArray(bArr);
        }
        this.mCommandId = parcel.readInt();
        this.mLength = parcel.readInt();
        this.mOffset = parcel.readInt();
        int i2 = parcel.readInt();
        if (i2 > 0) {
            byte[] bArr2 = new byte[i2];
            this.mRequest = bArr2;
            parcel.readByteArray(bArr2);
        }
    }

    private TACommandRequest(int i, byte[] bArr, int i2, int i3, int i4, byte[] bArr2) {
        this.mVersion = i;
        this.mMagicNum = bArr;
        this.mCommandId = i2;
        this.mLength = i3;
        this.mOffset = i4;
        this.mRequest = bArr2;
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
        int i2;
        ArrayList arrayList = new ArrayList();
        byte[] bArr = this.mRequest;
        if (bArr == null) {
            return null;
        }
        if (bArr.length <= 2972) {
            arrayList.add(this);
            if (DEBUG) {
                Log.i(TAG, "no need to divide the mRequest, len=" + this.mRequest.length);
                return arrayList;
            }
        } else {
            if (DEBUG) {
                Log.i(TAG, "dividing the mRequest, len=" + this.mRequest.length);
            }
            int i3 = 0;
            while (true) {
                i = i3;
                i3 = i + 2972;
                i2 = this.mLength;
                if (i3 >= i2) {
                    break;
                }
                if (DEBUG) {
                    Log.i(TAG, "generating the chunk from " + i + " to " + (i + 2971));
                }
                arrayList.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, this.mLength, i, Arrays.copyOfRange(this.mRequest, i, i3)));
            }
            arrayList.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, i2, i, Arrays.copyOfRange(this.mRequest, i, i2)));
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("generating the chunk from ");
                sb.append(i);
                sb.append(" to ");
                sb.append(this.mLength - 1);
                Log.i(TAG, sb.toString());
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a0 A[Catch: Exception -> 0x00a9, IOException -> 0x00ae, TRY_ENTER, TryCatch #7 {IOException -> 0x00ae, Exception -> 0x00a9, blocks: (B:13:0x0083, B:29:0x00a0, B:31:0x00a5), top: B:61:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a5 A[Catch: Exception -> 0x00a9, IOException -> 0x00ae, TRY_LEAVE, TryCatch #7 {IOException -> 0x00ae, Exception -> 0x00a9, blocks: (B:13:0x0083, B:29:0x00a0, B:31:0x00a5), top: B:61:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c0 A[Catch: Exception -> 0x00ba, IOException -> 0x00bc, TRY_LEAVE, TryCatch #8 {IOException -> 0x00bc, Exception -> 0x00ba, blocks: (B:40:0x00b6, B:46:0x00c0), top: B:59:0x00b6 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.FileWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump() throws Throwable {
        Exception e;
        String str = "Command ID = " + this.mCommandId;
        ?? fileWriter = TAG;
        Log.d(TAG, str);
        Log.d(TAG, "Length = " + this.mRequest.length);
        StringBuilder sb = new StringBuilder((this.mRequest.length * 2) + 100);
        sb.append("{");
        BufferedWriter bufferedWriter = null;
        int i = 0;
        while (true) {
            byte[] bArr = this.mRequest;
            if (i < bArr.length) {
                sb.append(String.format("0x%02X", Byte.valueOf(bArr[i])));
                if (i != this.mRequest.length) {
                    sb.append(", ");
                }
                i++;
            } else {
                sb.append("}");
                Log.d(TAG, sb.toString());
                try {
                    try {
                        try {
                            fileWriter = new FileWriter("/mnt/sdcard/sendbuf.txt", false);
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    throw th;
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                    throw th;
                                }
                            }
                            if (fileWriter != 0) {
                                fileWriter.close();
                            }
                            throw th;
                        }
                        try {
                            bufferedWriter = new BufferedWriter(fileWriter);
                            try {
                                bufferedWriter.append((CharSequence) sb.toString());
                                bufferedWriter.close();
                                fileWriter.close();
                                return;
                            } catch (Exception e4) {
                                e = e4;
                                e.printStackTrace();
                                if (bufferedWriter != null) {
                                    bufferedWriter.close();
                                }
                                if (fileWriter != 0) {
                                    fileWriter.close();
                                }
                                return;
                            }
                        } catch (Exception e5) {
                            e = e5;
                            bufferedWriter = null;
                            fileWriter = fileWriter;
                            e = e;
                            e.printStackTrace();
                            if (bufferedWriter != null) {
                            }
                            if (fileWriter != 0) {
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedWriter = null;
                            fileWriter = fileWriter;
                            th = th;
                            if (bufferedWriter != null) {
                            }
                            if (fileWriter != 0) {
                            }
                            throw th;
                        }
                    } catch (Exception e6) {
                        e = e6;
                        fileWriter = 0;
                        bufferedWriter = null;
                    } catch (Throwable th3) {
                        th = th3;
                        fileWriter = 0;
                        bufferedWriter = null;
                    }
                } catch (IOException e7) {
                    e7.printStackTrace();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            }
        }
    }
}
