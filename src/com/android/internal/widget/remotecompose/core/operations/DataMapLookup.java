package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.DataMap;
import com.android.internal.widget.remotecompose.core.types.BooleanConstant;
import com.android.internal.widget.remotecompose.core.types.LongConstant;
import java.util.List;

/* loaded from: classes6.dex */
public class DataMapLookup extends Operation {
    private static final String CLASS_NAME = "DataMapLookup";
    private static final int OP_CODE = 154;
    public int mDataMapId;
    public int mId;
    public int mStringId;

    public static int id() {
        return 154;
    }

    public DataMapLookup(int i, int i2, int i3) {
        this.mId = i;
        this.mDataMapId = i2;
        this.mStringId = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mDataMapId, this.mStringId);
    }

    public String toString() {
        return "DataMapLookup[" + this.mId + "] = " + Utils.idString(this.mDataMapId) + " " + this.mStringId;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3) {
        wireBuffer.start(154);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DataMapLookup(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 154, CLASS_NAME).description("Look up a value in a data map").field(0, "id", "id of float").field(0, "dataMapId", "32-bit float value").field(0, "stringId", "32-bit float value");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        String text = remoteContext.getText(this.mStringId);
        DataMap dataMap = remoteContext.getDataMap(this.mDataMapId);
        int pos = dataMap.getPos(text);
        byte type = dataMap.getType(pos);
        int id = dataMap.getId(pos);
        if (type == 0) {
            remoteContext.loadText(this.mId, remoteContext.getText(id));
            return;
        }
        if (type == 1) {
            remoteContext.loadInteger(this.mId, remoteContext.getInteger(id));
            return;
        }
        if (type == 2) {
            remoteContext.loadFloat(this.mId, remoteContext.getFloat(id));
            return;
        }
        if (type == 3) {
            remoteContext.loadInteger(this.mId, (int) ((LongConstant) remoteContext.getObject(id)).getValue());
        } else {
            if (type != 4) {
                return;
            }
            remoteContext.loadInteger(this.mId, ((BooleanConstant) remoteContext.getObject(id)).getValue() ? 1 : 0);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }
}
