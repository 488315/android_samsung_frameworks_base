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
public class ValueFloatExpressionChangeActionOperation extends Operation implements ActionOperation {
    private static final int OP_CODE = 227;
    int mTargetValueId;
    int mValueExpressionId;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public ValueFloatExpressionChangeActionOperation(int i, int i2) {
        this.mTargetValueId = i;
        this.mValueExpressionId = i2;
    }

    public String toString() {
        return "ValueFloatExpressionChangeActionOperation(" + this.mTargetValueId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String serializedName() {
        return "VALUE_FLOAT_EXPRESSION_CHANGE";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, serializedName() + " = " + this.mTargetValueId + " -> " + this.mValueExpressionId);
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
        coreDocument.evaluateFloatExpression(this.mValueExpressionId, this.mTargetValueId, remoteContext);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(227);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ValueFloatExpressionChangeActionOperation(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 227, "ValueIntegerExpressionChangeActionOperation").description("ValueIntegerExpressionChange action.  This operation represents a value change for the given id").field(0, "TARGET_VALUE_ID", "Value ID").field(0, "VALUE_ID", "id of the value to be assigned to the target");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER, SerializeTags.ACTION).addType("ValueFloatExpressionChangeActionOperation").add("targetValueId", Integer.valueOf(this.mTargetValueId)).add("valueExpressionId", Integer.valueOf(this.mValueExpressionId));
    }
}
