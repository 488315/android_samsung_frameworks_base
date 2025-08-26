package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.DataMap;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.List;

/* loaded from: classes6.dex */
public class DataMapIds extends Operation {
    private static final String CLASS_NAME = "DataMapIds";
    private static final int MAX_MAP = 2000;
    private static final int OP_CODE = 145;
    public static final byte TYPE_BOOLEAN = 4;
    public static final byte TYPE_FLOAT = 2;
    public static final byte TYPE_INT = 1;
    public static final byte TYPE_LONG = 3;
    public static final byte TYPE_STRING = 0;
    final DataMap mDataMap;
    int mId;

    private String typeString(byte b) {
        if (b == 0) {
            return "String";
        }
        if (b == 1) {
            return "Int";
        }
        if (b == 2) {
            return "Float";
        }
        if (b == 3) {
            return "Long";
        }
        if (b == 4) {
            return "Boolean";
        }
        return "?";
    }

    public DataMapIds(int i, String[] strArr, byte[] bArr, int[] iArr) {
        this.mId = i;
        this.mDataMap = new DataMap(strArr, bArr, iArr);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mDataMap.mNames, this.mDataMap.mTypes, this.mDataMap.mIds);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataMapIds[" + Utils.idString(this.mId) + "] ");
        for (int i = 0; i < this.mDataMap.mNames.length; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(typeString(this.mDataMap.mTypes[i]));
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            sb.append(this.mDataMap.mNames[i]);
            sb.append("]=");
            sb.append(this.mDataMap.mIds[i]);
        }
        return sb.toString();
    }

    public static void apply(WireBuffer wireBuffer, int i, String[] strArr, byte[] bArr, int[] iArr) {
        wireBuffer.start(145);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(strArr.length);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            wireBuffer.writeUTF8(strArr[i2]);
            wireBuffer.writeByte(bArr == null ? (byte) 2 : bArr[i2]);
            wireBuffer.writeInt(iArr[i2]);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int i = wireBuffer.readInt();
        int i2 = wireBuffer.readInt();
        if (i2 > 2000) {
            throw new RuntimeException(i2 + " map entries more than max = 2000");
        }
        String[] strArr = new String[i2];
        int[] iArr = new int[i2];
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            strArr[i3] = wireBuffer.readUTF8();
            bArr[i3] = (byte) wireBuffer.readByte();
            iArr[i3] = wireBuffer.readInt();
        }
        list.add(new DataMapIds(i, strArr, bArr, iArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 145, CLASS_NAME).description("Encode a collection of name id pairs").field(0, "id", "id the array").field(0, Contract.CompressedEvents.Field.LENGTH, "number of entries").field(0, "names[0]", Contract.CompressedEvents.Field.LENGTH, "path encoded as floats").field(5, "id[0]", Contract.CompressedEvents.Field.LENGTH, "path encoded as floats");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.putDataMap(this.mId, this.mDataMap);
    }
}
