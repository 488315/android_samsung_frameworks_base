package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntMap;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* loaded from: classes6.dex */
public class Header extends Operation implements RemoteComposeOperation {
    private static final String CLASS_NAME = "Header";
    private static final short DATA_TYPE_FLOAT = 1;
    private static final short DATA_TYPE_INT = 0;
    private static final short DATA_TYPE_LONG = 2;
    private static final short DATA_TYPE_STRING = 3;
    public static final short DOC_CONTENT_DESCRIPTION = 9;
    public static final short DOC_DATA_UPDATE = 12;
    public static final short DOC_DENSITY_AT_GENERATION = 7;
    public static final short DOC_DESIRED_FPS = 8;
    public static final short DOC_HEIGHT = 6;
    public static final short DOC_SOURCE = 11;
    public static final short DOC_WIDTH = 5;
    private static final short[] KEYS = {5, 6, 7, 8, 9, 11, 12};
    private static final String[] KEY_NAMES = {"DOC_WIDTH", "DOC_HEIGHT", "DOC_DENSITY_AT_GENERATION", "DOC_DESIRED_FPS", "DOC_CONTENT_DESCRIPTION", "DOC_SOURCE"};
    private static final int MAGIC_NUMBER = 76283904;
    private static final int OP_CODE = 0;
    long mCapabilities;
    float mDensity;
    int mHeight;
    int mMajorVersion;
    int mMinorVersion;
    int mPatchVersion;
    private IntMap<Object> mProperties;
    int mWidth;

    public static int id() {
        return 0;
    }

    public Object get(short s) {
        return this.mProperties.get(s);
    }

    public Header(int i, int i2, int i3, int i4, int i5, float f, long j) {
        this.mMajorVersion = i;
        this.mMinorVersion = i2;
        this.mPatchVersion = i3;
        this.mWidth = i4;
        this.mHeight = i5;
        this.mDensity = f;
        this.mCapabilities = j;
    }

    public Header(int i, int i2, int i3, IntMap<Object> intMap) {
        this.mWidth = 256;
        this.mHeight = 256;
        this.mDensity = 3.0f;
        this.mCapabilities = 0L;
        this.mMajorVersion = i;
        this.mMinorVersion = i2;
        this.mPatchVersion = i3;
        if (intMap != null) {
            this.mProperties = intMap;
            this.mWidth = getInt(5, 256);
            this.mHeight = getInt(6, 256);
            this.mDensity = getFloat(7, 0.0f);
        }
    }

    private int getInt(int i, int i2) {
        Integer num;
        IntMap<Object> intMap = this.mProperties;
        return (intMap == null || (num = (Integer) intMap.get(i)) == null) ? i2 : num.intValue();
    }

    private long getLong(int i, long j) {
        Long l;
        IntMap<Object> intMap = this.mProperties;
        return (intMap == null || (l = (Long) intMap.get(i)) == null) ? j : l.longValue();
    }

    private float getFloat(int i, float f) {
        Float f2;
        IntMap<Object> intMap = this.mProperties;
        return (intMap == null || (f2 = (Float) intMap.get(i)) == null) ? f : f2.floatValue();
    }

    private String getString(int i, String str) {
        String str2;
        IntMap<Object> intMap = this.mProperties;
        return (intMap == null || (str2 = (String) intMap.get(i)) == null) ? str : str2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mWidth, this.mHeight, this.mDensity, this.mCapabilities);
    }

    public String toString() {
        if (this.mProperties != null) {
            String str = "";
            int i = 0;
            while (true) {
                short[] sArr = KEYS;
                if (i < sArr.length) {
                    Object obj = this.mProperties.get(sArr[i]);
                    if (obj != null) {
                        str = str + "\n  " + KEY_NAMES[i] + " " + obj.toString();
                    }
                    i++;
                } else {
                    return "HEADER v" + this.mMajorVersion + MediaMetrics.SEPARATOR + this.mMinorVersion + MediaMetrics.SEPARATOR + this.mPatchVersion + str;
                }
            }
        } else {
            return "HEADER v" + this.mMajorVersion + MediaMetrics.SEPARATOR + this.mMinorVersion + MediaMetrics.SEPARATOR + this.mPatchVersion + ", " + this.mWidth + " x " + this.mHeight + " [" + this.mCapabilities + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.header(this.mMajorVersion, this.mMinorVersion, this.mPatchVersion, this.mWidth, this.mHeight, this.mCapabilities, this.mProperties);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return toString();
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f, long j) {
        wireBuffer.start(0);
        wireBuffer.writeInt(1);
        wireBuffer.writeInt(0);
        wireBuffer.writeInt(0);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeLong(j);
    }

    public static void apply(WireBuffer wireBuffer, short[] sArr, Object[] objArr) {
        wireBuffer.start(0);
        wireBuffer.writeInt(76283905);
        wireBuffer.writeInt(0);
        wireBuffer.writeInt(0);
        wireBuffer.writeInt(sArr.length);
        writeMap(wireBuffer, sArr, objArr);
    }

    public static Header readDirect(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            byte readByte = dataInputStream.readByte();
            if (readByte != 0) {
                throw new IOException("Invalid header " + ((int) readByte) + " != 0");
            }
            int readInt = dataInputStream.readInt();
            int readInt2 = dataInputStream.readInt();
            int readInt3 = dataInputStream.readInt();
            if (readInt < 65536) {
                return new Header(readInt, readInt2, readInt3, dataInputStream.readInt(), dataInputStream.readInt(), 1.0f, dataInputStream.readLong());
            }
            int i = (-65536) & readInt;
            if (i != MAGIC_NUMBER) {
                throw new IOException("Invalid header MAGIC_NUMBER " + i + " != 76283904");
            }
            int i2 = 65535 & readInt;
            int readInt4 = dataInputStream.readInt();
            short[] sArr = new short[readInt4];
            Object[] objArr = new Object[readInt4];
            readMap(dataInputStream, sArr, objArr);
            IntMap intMap = new IntMap();
            for (int i3 = 0; i3 < readInt4; i3++) {
                intMap.put(sArr[i3], objArr[i3]);
            }
            return new Header(i2, readInt2, readInt3, intMap);
        } finally {
            dataInputStream.close();
        }
    }

    private static void readMap(DataInputStream dataInputStream, short[] sArr, Object[] objArr) throws IOException {
        for (int i = 0; i < sArr.length; i++) {
            short readShort = dataInputStream.readShort();
            dataInputStream.readShort();
            int i2 = readShort >> 10;
            sArr[i] = (short) (readShort & 63);
            if (i2 == 0) {
                objArr[i] = Integer.valueOf(dataInputStream.readInt());
            } else if (i2 == 1) {
                objArr[i] = Float.valueOf(dataInputStream.readFloat());
            } else if (i2 == 2) {
                objArr[i] = Long.valueOf(dataInputStream.readLong());
            } else if (i2 == 3) {
                byte[] bArr = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr);
                objArr[i] = new String(bArr);
            }
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        int readInt3 = wireBuffer.readInt();
        if (readInt < 65536) {
            list.add(new Header(readInt, readInt2, readInt3, wireBuffer.readInt(), wireBuffer.readInt(), 1.0f, wireBuffer.readLong()));
            return;
        }
        int i = 65535 & readInt;
        int readInt4 = wireBuffer.readInt();
        short[] sArr = new short[readInt4];
        Object[] objArr = new Object[readInt4];
        readMap(wireBuffer, sArr, objArr);
        IntMap intMap = new IntMap();
        for (int i2 = 0; i2 < readInt4; i2++) {
            intMap.put(sArr[i2], objArr[i2]);
        }
        list.add(new Header(i, readInt2, readInt3, intMap));
    }

    private static void readMap(WireBuffer wireBuffer, short[] sArr, Object[] objArr) {
        for (int i = 0; i < sArr.length; i++) {
            short readShort = (short) wireBuffer.readShort();
            wireBuffer.readShort();
            int i2 = readShort >> 10;
            sArr[i] = (short) (readShort & 63);
            if (i2 == 0) {
                objArr[i] = Integer.valueOf(wireBuffer.readInt());
            } else if (i2 == 1) {
                objArr[i] = Float.valueOf(wireBuffer.readFloat());
            } else if (i2 == 2) {
                objArr[i] = Long.valueOf(wireBuffer.readLong());
            } else if (i2 == 3) {
                objArr[i] = wireBuffer.readUTF8();
            }
        }
    }

    private static void writeMap(WireBuffer wireBuffer, short[] sArr, Object[] objArr) {
        for (int i = 0; i < sArr.length; i++) {
            short s = sArr[i];
            Object obj = objArr[i];
            if (obj instanceof String) {
                wireBuffer.writeShort((short) (s | 3072));
                byte[] bytes = ((String) objArr[i]).getBytes();
                wireBuffer.writeShort(bytes.length + 4);
                wireBuffer.writeBuffer(bytes);
            } else if (obj instanceof Integer) {
                wireBuffer.writeShort(s);
                wireBuffer.writeShort(4);
                wireBuffer.writeInt(((Integer) objArr[i]).intValue());
            } else if (obj instanceof Float) {
                wireBuffer.writeShort((short) (s | 1024));
                wireBuffer.writeShort(4);
                wireBuffer.writeFloat(((Float) objArr[i]).floatValue());
            } else if (obj instanceof Long) {
                wireBuffer.writeShort((short) (s | 2048));
                wireBuffer.writeShort(8);
                wireBuffer.writeLong(((Long) objArr[i]).longValue());
            }
        }
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Protocol Operations", 0, CLASS_NAME).description("Document metadata, containing the version, original size & density, capabilities mask").field(0, "MAJOR_VERSION", "Major version").field(0, "MINOR_VERSION", "Minor version").field(0, "PATCH_VERSION", "Patch version").field(0, "WIDTH", "Major version").field(0, "HEIGHT", "Major version").field(8, "CAPABILITIES", "Major version");
    }

    public void setVersion(CoreDocument coreDocument) {
        coreDocument.setUpdateDoc(getInt(12, 0) != 0);
        coreDocument.setVersion(this.mMajorVersion, this.mMinorVersion, this.mPatchVersion);
    }
}
