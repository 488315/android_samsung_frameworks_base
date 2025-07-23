package com.android.internal.widget.remotecompose.core.operations;

import android.provider.Downloads;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class DataListIds extends Operation implements VariableSupport, ArrayAccess, Serializable {
    private static final String CLASS_NAME = "IdListData";
    private static final int MAX_LIST = 2000;
    private static final int OP_CODE = 146;
    private final int mId;
    private final int[] mIds;

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public float getFloatValue(int i) {
        return Float.NaN;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public float[] getFloats() {
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public int getIntValue(int i) {
        return 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
    }

    public DataListIds(int i, int[] iArr) {
        this.mId = i;
        this.mIds = iArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mIds);
    }

    public String toString() {
        return "map[" + Utils.idString(this.mId) + "]  \"" + Arrays.toString(this.mIds) + "\"";
    }

    public static void apply(WireBuffer wireBuffer, int i, int[] iArr) {
        wireBuffer.start(146);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(iArr.length);
        for (int i2 : iArr) {
            wireBuffer.writeInt(i2);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        if (readInt2 > 2000) {
            throw new RuntimeException(readInt2 + " list entries more than max = 2000");
        }
        int[] iArr = new int[readInt2];
        for (int i = 0; i < readInt2; i++) {
            iArr[i] = wireBuffer.readInt();
        }
        list.add(new DataListIds(readInt, iArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 146, CLASS_NAME).description("a list of id's").field(0, "id", "id the array").field(0, Contract.CompressedEvents.Field.LENGTH, "number of ids").field(11, "ids[n]", Contract.CompressedEvents.Field.LENGTH, "ids of other variables");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.addCollection(this.mId, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public int getId(int i) {
        return this.mIds[i];
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public int getLength() {
        return this.mIds.length;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add(Downloads.EXTRA_IDS, List.of(this.mIds));
    }
}
