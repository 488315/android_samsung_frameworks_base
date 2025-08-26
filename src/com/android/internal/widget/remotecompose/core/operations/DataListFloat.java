package com.android.internal.widget.remotecompose.core.operations;

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
public class DataListFloat extends Operation implements VariableSupport, ArrayAccess, Serializable {
    private static final String CLASS_NAME = "IdListData";
    private static final int MAX_FLOAT_ARRAY = 2000;
    private static final int OP_CODE = 147;
    public final int mId;
    private float[] mValues;

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
    }

    public DataListFloat(int i, float[] fArr) {
        this.mId = i;
        this.mValues = fArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        remoteContext.addCollection(this.mId, this);
        for (float f : this.mValues) {
            if (Utils.isVariable(f)) {
                remoteContext.listensTo(Utils.idFromNan(f), this);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mValues);
    }

    public String toString() {
        return "DataListFloat[" + Utils.idString(this.mId) + "] " + Arrays.toString(this.mValues);
    }

    public static void apply(WireBuffer wireBuffer, int i, float[] fArr) {
        wireBuffer.start(147);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(fArr.length);
        for (float f : fArr) {
            wireBuffer.writeFloat(f);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int i = wireBuffer.readInt();
        int i2 = wireBuffer.readInt();
        if (i2 > 2000) {
            throw new RuntimeException(i2 + " map entries more than max = 2000");
        }
        float[] fArr = new float[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            fArr[i3] = wireBuffer.readFloat();
        }
        list.add(new DataListFloat(i, fArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 147, CLASS_NAME).description("a list of Floats").field(0, "id", "id the array (2xxxxx)").field(0, Contract.CompressedEvents.Field.LENGTH, "number of floats").field(10, "values", Contract.CompressedEvents.Field.LENGTH, "array of floats");
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
    public float getFloatValue(int i) {
        return this.mValues[i];
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public float[] getFloats() {
        return this.mValues;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess
    public int getLength() {
        return this.mValues.length;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("values", List.of(this.mValues));
    }

    public void update(DataListFloat dataListFloat) {
        this.mValues = dataListFloat.mValues;
    }
}
