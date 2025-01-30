package com.samsung.android.knox.p045ex.knoxAI;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import android.util.Half;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DataBuffer implements Parcelable {
    public static final Parcelable.Creator<DataBuffer> CREATOR = new Parcelable.Creator<DataBuffer>() { // from class: com.samsung.android.knox.ex.knoxAI.DataBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DataBuffer createFromParcel(Parcel parcel) {
            return new DataBuffer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DataBuffer[] newArray(int i) {
            return new DataBuffer[i];
        }

        @Override // android.os.Parcelable.Creator
        public final DataBuffer createFromParcel(Parcel parcel) {
            return new DataBuffer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final DataBuffer[] newArray(int i) {
            return new DataBuffer[i];
        }
    };
    public byte dataFormat;
    public float[] dataOriginal;
    public SharedMemory dataShared;
    public byte dataSource;
    public byte dataType;
    public FileDescriptor filedesc;
    public int[] shape;

    public DataBuffer() {
    }

    public static Half readFloat16FromBytes(byte[] bArr, int i) {
        int i2;
        return new Half((short) ((i < 0 || (i2 = i + 1) >= bArr.length) ? 0 : (bArr[i] & 255) | ((bArr[i2] & 255) << 8)));
    }

    public static float readFloatFromBytes(byte[] bArr, int i) {
        int i2;
        if (i < 4 || i - 1 >= bArr.length) {
            return 0.0f;
        }
        return Float.intBitsToFloat(Integer.valueOf((bArr[i - 4] & 255) | Integer.valueOf(Integer.valueOf(Integer.valueOf(bArr[i2] << 24).intValue() | ((bArr[i - 2] & 255) << 16)).intValue() | ((bArr[i - 3] & 255) << 8)).intValue()).intValue());
    }

    public static byte[] readFloatToBytes(float[] fArr) {
        if (fArr == null) {
            return null;
        }
        byte[] bArr = new byte[fArr.length * 4];
        for (int i = 0; i < fArr.length; i++) {
            int floatToIntBits = Float.floatToIntBits(fArr[i]);
            int i2 = i * 4;
            bArr[i2 + 3] = (byte) (floatToIntBits >> 24);
            bArr[i2 + 2] = (byte) (floatToIntBits >> 16);
            bArr[i2 + 1] = (byte) (floatToIntBits >> 8);
            bArr[i2] = (byte) floatToIntBits;
        }
        return bArr;
    }

    public static int readIntFromBytes(byte[] bArr, int i) {
        int i2;
        if (i < 0 || (i2 = i + 3) >= bArr.length) {
            return 0;
        }
        return (bArr[i] & 255) | (bArr[i2] << 24) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 1] & 255) << 8);
    }

    public static byte[] readIntToBytes(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        byte[] bArr = new byte[iArr.length * 4];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 4;
            int i3 = iArr[i];
            bArr[i2 + 3] = (byte) (i3 >> 24);
            bArr[i2 + 2] = (byte) (i3 >> 16);
            bArr[i2 + 1] = (byte) (i3 >> 8);
            bArr[i2] = (byte) i3;
        }
        return bArr;
    }

    public static JSONObject readJSONObjectFromBytes(byte[] bArr) {
        try {
            return new JSONObject(new String(bArr));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long readLongFromBytes(byte[] bArr, int i) {
        long j = 0;
        if (i >= 0 && i + 7 < bArr.length) {
            for (int i2 = 0; i2 < 8; i2++) {
                j += (bArr[i + i2] & 255) << (i2 * 8);
            }
        }
        return j;
    }

    public static byte[] readStringToBytes(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        for (String str : strArr) {
            length += str.length();
        }
        ByteBuffer allocate = ByteBuffer.allocate(length);
        for (String str2 : strArr) {
            allocate.put(str2.getBytes(StandardCharsets.UTF_8));
            allocate.put((byte) 0);
        }
        return allocate.array();
    }

    public final void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final byte getDataFormat() {
        return this.dataFormat;
    }

    public final float[] getDataOriginal() {
        return this.dataOriginal;
    }

    public final SharedMemory getDataShared() {
        return this.dataShared;
    }

    public final byte getDataSource() {
        return this.dataSource;
    }

    public final byte getDataType() {
        return this.dataType;
    }

    public final FileDescriptor getFileDesc() {
        return this.filedesc;
    }

    public final int[] getShape() {
        return this.shape;
    }

    public final void setDataFormat(byte b) {
        this.dataFormat = b;
    }

    public final void setDataOriginal(float[] fArr) {
        this.dataOriginal = fArr;
    }

    public final void setDataShared(SharedMemory sharedMemory) {
        this.dataShared = sharedMemory;
    }

    public final void setDataSource(byte b) {
        this.dataSource = b;
    }

    public final void setDataType(byte b) {
        this.dataType = b;
    }

    public final void setFileDesc(FileDescriptor fileDescriptor) {
        this.filedesc = fileDescriptor;
    }

    public final void setShape(int[] iArr) {
        this.shape = iArr;
    }

    public final String toString() {
        return "DBufr [" + ((int) this.dataType) + "," + ((int) this.dataFormat) + "],shp=[" + Arrays.toString(this.shape) + "],[" + Arrays.toString(this.dataOriginal) + "]";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.dataType);
        parcel.writeByte(this.dataFormat);
        int[] iArr = this.shape;
        if (iArr == null || iArr.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(iArr.length);
            parcel.writeIntArray(this.shape);
        }
        parcel.writeByte(this.dataSource);
        byte b = this.dataSource;
        if (b != 0) {
            if (b == 1) {
                parcel.writeFileDescriptor(this.filedesc);
                return;
            } else {
                if (b == 2) {
                    parcel.writeParcelable(this.dataShared, 0);
                    return;
                }
                return;
            }
        }
        float[] fArr = this.dataOriginal;
        if (fArr == null || fArr.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(fArr.length);
            parcel.writeFloatArray(this.dataOriginal);
        }
    }

    public DataBuffer(Parcel parcel) {
        this.dataType = parcel.readByte();
        this.dataFormat = parcel.readByte();
        int readInt = parcel.readInt();
        if (readInt != 0) {
            int[] iArr = new int[readInt];
            this.shape = iArr;
            parcel.readIntArray(iArr);
        }
        byte readByte = parcel.readByte();
        this.dataSource = readByte;
        if (readByte == 0) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 0) {
                float[] fArr = new float[readInt2];
                this.dataOriginal = fArr;
                parcel.readFloatArray(fArr);
                return;
            }
            return;
        }
        if (readByte == 1) {
            this.filedesc = parcel.readFileDescriptor().getFileDescriptor();
        } else if (readByte == 2) {
            this.dataShared = (SharedMemory) parcel.readParcelable(SharedMemory.class.getClassLoader());
        }
    }
}
