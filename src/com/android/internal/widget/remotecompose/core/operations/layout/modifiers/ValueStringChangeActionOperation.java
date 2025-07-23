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
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class ValueStringChangeActionOperation extends Operation implements ActionOperation {
    private static final int OP_CODE = 213;
    int mTargetValueId;
    int mValueId;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public ValueStringChangeActionOperation(int i, int i2) {
        this.mTargetValueId = i;
        this.mValueId = i2;
    }

    public String toString() {
        return "ValueChangeActionOperation(" + this.mTargetValueId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public int getActionId() {
        return this.mTargetValueId;
    }

    public String serializedName() {
        return "VALUE_CHANGE";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, serializedName() + " = " + this.mTargetValueId + " -> " + this.mValueId);
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

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation
    public void runAction(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        remoteContext.overrideText(this.mTargetValueId, this.mValueId);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(213);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ValueStringChangeActionOperation(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 213, "ValueStringChangeActionOperation").description("ValueStrin gChange action.  This operation represents a String change (referenced by id) for the given string id").field(0, "TARGET_ID", "Target Value ID").field(0, "VALUE_ID", "Value ID to be assigned to the target value as a string");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER, SerializeTags.ACTION).addType("ValueIntegerExpressionChangeActionOperation").add("targetValueId", Integer.valueOf(this.mTargetValueId)).add("valueId", Integer.valueOf(this.mValueId));
    }
}
