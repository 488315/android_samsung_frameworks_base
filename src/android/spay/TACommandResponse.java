package android.spay;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes3.dex */
public class TACommandResponse implements Parcelable {
    public static final Parcelable.Creator<TACommandResponse> CREATOR = new Parcelable.Creator<TACommandResponse>() { // from class: android.spay.TACommandResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse createFromParcel(Parcel parcel) {
            return new TACommandResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse[] newArray(int i) {
            return new TACommandResponse[i];
        }
    };
    private static final String TAG = "TACommandResponse";
    public String mErrorMsg;
    public byte[] mResponse;
    public int mResponseCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TACommandResponse() {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
    }

    public TACommandResponse(int i, String str, byte[] bArr) {
        this.mResponseCode = i;
        this.mErrorMsg = str;
        this.mResponse = bArr;
    }

    private TACommandResponse(Parcel parcel) {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResponseCode);
        parcel.writeString(this.mErrorMsg);
        parcel.writeInt(this.mResponse.length);
        parcel.writeByteArray(this.mResponse);
    }

    public void readFromParcel(Parcel parcel) {
        this.mResponseCode = parcel.readInt();
        this.mErrorMsg = parcel.readString();
        byte[] bArr = new byte[parcel.readInt()];
        this.mResponse = bArr;
        parcel.readByteArray(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x008a A[Catch: Exception -> 0x0093, IOException -> 0x0098, TRY_ENTER, TryCatch #10 {IOException -> 0x0098, Exception -> 0x0093, blocks: (B:16:0x006d, B:32:0x008a, B:34:0x008f), top: B:57:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008f A[Catch: Exception -> 0x0093, IOException -> 0x0098, TRY_LEAVE, TryCatch #10 {IOException -> 0x0098, Exception -> 0x0093, blocks: (B:16:0x006d, B:32:0x008a, B:34:0x008f), top: B:57:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00aa A[Catch: Exception -> 0x00a4, IOException -> 0x00a6, TRY_LEAVE, TryCatch #9 {IOException -> 0x00a6, Exception -> 0x00a4, blocks: (B:43:0x00a0, B:49:0x00aa), top: B:58:0x00a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump() throws Throwable {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        Throwable th;
        Exception e;
        Log.d(TAG, "Length = " + this.mResponse.length);
        StringBuilder sb = new StringBuilder((this.mResponse.length * 3) + 100);
        int i = 0;
        while (true) {
            byte[] bArr = this.mResponse;
            if (i < bArr.length) {
                if (i > 0 && bArr[i] != 0 && bArr[i - 1] == 0) {
                    sb.append(ShaderAssembler.NEWLINE);
                }
                sb.append(String.format("%02X ", Byte.valueOf(this.mResponse[i])));
                i++;
            } else {
                Log.d(TAG, sb.toString());
                try {
                    try {
                        fileWriter = new FileWriter("/mnt/sdcard/respbuf.txt", false);
                        try {
                            bufferedWriter = new BufferedWriter(fileWriter);
                            try {
                                try {
                                    bufferedWriter.append((CharSequence) sb.toString());
                                    bufferedWriter.close();
                                    fileWriter.close();
                                    return;
                                } catch (Exception e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    if (bufferedWriter != null) {
                                        bufferedWriter.close();
                                    }
                                    if (fileWriter == null) {
                                        fileWriter.close();
                                        return;
                                    }
                                    return;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (bufferedWriter != null) {
                                    try {
                                        bufferedWriter.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        throw th;
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (fileWriter != null) {
                                    fileWriter.close();
                                }
                                throw th;
                            }
                        } catch (Exception e5) {
                            e = e5;
                            bufferedWriter = null;
                            e = e;
                            e.printStackTrace();
                            if (bufferedWriter != null) {
                            }
                            if (fileWriter == null) {
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedWriter = null;
                            th = th;
                            if (bufferedWriter != null) {
                            }
                            if (fileWriter != null) {
                            }
                            throw th;
                        }
                    } catch (IOException e6) {
                        e6.printStackTrace();
                        return;
                    } catch (Exception e7) {
                        e7.printStackTrace();
                        return;
                    }
                } catch (Exception e8) {
                    e = e8;
                    fileWriter = null;
                    bufferedWriter = null;
                } catch (Throwable th4) {
                    th = th4;
                    fileWriter = null;
                    bufferedWriter = null;
                }
            }
        }
    }
}
