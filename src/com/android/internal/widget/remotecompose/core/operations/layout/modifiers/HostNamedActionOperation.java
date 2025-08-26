package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class HostNamedActionOperation extends Operation implements ActionOperation, Serializable {
    public static final int FLOAT_ARRAY_TYPE = 3;
    public static final int FLOAT_TYPE = 0;
    public static final int INT_TYPE = 1;
    public static final int NONE_TYPE = -1;
    private static final int OP_CODE = 210;
    public static final int STRING_TYPE = 2;
    int mTextId;
    int mType;
    int mValueId;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public HostNamedActionOperation(int i, int i2, int i3) {
        this.mTextId = i;
        this.mType = i2;
        this.mValueId = i3;
    }

    public String toString() {
        return "HostNamedActionOperation(" + this.mTextId + " : " + this.mValueId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String serializedName() {
        return "HOST_NAMED_ACTION";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        if (this.mValueId != -1) {
            stringSerializer.append(i, serializedName() + " = " + this.mTextId + " : " + this.mValueId);
            return;
        }
        stringSerializer.append(i, serializedName() + " = " + this.mTextId);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003e  */
    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void runAction(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        Object floats;
        if (this.mValueId == -1) {
            floats = null;
        } else {
            int i = this.mType;
            if (i == 1) {
                floats = Integer.valueOf(remoteContext.mRemoteComposeState.getInteger(this.mValueId));
            } else if (i == 2) {
                floats = remoteContext.mRemoteComposeState.getFromId(this.mValueId);
            } else if (i == 0) {
                floats = Float.valueOf(remoteContext.mRemoteComposeState.getFloat(this.mValueId));
            } else if (i == 3) {
                floats = remoteContext.mRemoteComposeState.getFloats(this.mValueId);
            }
        }
        remoteContext.runNamedAction(this.mTextId, floats);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3) {
        wireBuffer.start(210);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new HostNamedActionOperation(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 210, "HostNamedAction").description("Host Named action. This operation represents a host action").field(0, "TEXT_ID", "Named Host Action Text ID").field(0, "VALUE_ID", "Named Host Action Value ID");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType("HostNamedActionOperation").add("textId", Integer.valueOf(this.mTextId)).add("actionType", getActionType(this.mType)).add("valueId", Integer.valueOf(this.mValueId));
    }

    private static String getActionType(int i) {
        if (i == -1) {
            return "NONE_TYPE";
        }
        if (i == 0) {
            return "FLOAT_TYPE";
        }
        if (i == 1) {
            return "INT_TYPE";
        }
        if (i == 2) {
            return "STRING_TYPE";
        }
        if (i == 3) {
            return "FLOAT_ARRAY_TYPE";
        }
        return "INVALID_TYPE";
    }
}
