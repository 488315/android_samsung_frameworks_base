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
public class ValueIntegerChangeActionOperation extends Operation implements ActionOperation {
    private static final int OP_CODE = 212;
    int mTargetValueId;
    int mValue;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public ValueIntegerChangeActionOperation(int i, int i2) {
        this.mTargetValueId = i;
        this.mValue = i2;
    }

    public String toString() {
        return "ValueChangeActionOperation(" + this.mTargetValueId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String serializedName() {
        return "VALUE_INTEGER_CHANGE";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, serializedName() + " = " + this.mTargetValueId + " -> " + this.mValue);
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
        remoteContext.overrideInteger(this.mTargetValueId, this.mValue);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(212);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ValueIntegerChangeActionOperation(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 212, "ValueIntegerChangeActionOperation").description("ValueIntegerChange action.  This operation represents a value change for the given id").field(0, "TARGET_VALUE_ID", "Value ID").field(0, "VALUE", "integer value to be assigned to the target");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER, SerializeTags.ACTION).addType("ValueIntegerChangeActionOperation").add("targetValueId", Integer.valueOf(this.mTargetValueId)).add("value", Integer.valueOf(this.mValue));
    }
}
