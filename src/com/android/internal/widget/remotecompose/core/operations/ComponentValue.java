package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class ComponentValue extends Operation implements SerializableToString, Serializable {
    private static final String CLASS_NAME = "ComponentValue";
    public static final int HEIGHT = 1;
    private static final int OP_CODE = 150;
    public static final int WIDTH = 0;
    private int mComponentID;
    private int mType;
    private int mValueId;

    public static int id() {
        return 150;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return null;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public String toString() {
        return "ComponentValue(" + this.mType + ", " + this.mComponentID + ", " + this.mValueId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public int getType() {
        return this.mType;
    }

    public int getComponentId() {
        return this.mComponentID;
    }

    public int getValueId() {
        return this.mValueId;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mType, this.mComponentID, this.mValueId);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ComponentValue(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 150, CLASS_NAME).description("Encode a component-related value (eg its width, height etc.)").field(0, AccessibilityButtonChooserActivity.EXTRA_TYPE_TO_CHOOSE, "The type of value, either WIDTH(0) or HEIGHT(1)").field(0, "COMPONENT_ID", "The component id to reference").field(0, "VALUE_ID", "The id of the RemoteFloat representing the described component value, which can be used in expressions");
    }

    public ComponentValue(int i, int i2, int i3) {
        this.mType = i;
        this.mComponentID = i2;
        this.mValueId = i3;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3) {
        wireBuffer.start(150);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        String str;
        if (this.mType != 1) {
            str = "WIDTH";
        } else {
            str = "HEIGHT";
        }
        stringSerializer.append(i, "ComponentValue value " + this.mValueId + " set to " + str + " of Component " + this.mComponentID);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("valueId", Integer.valueOf(this.mValueId)).add("componentValueType", typeToString(this.mType)).add("componentId", Integer.valueOf(this.mComponentID));
    }

    private String typeToString(int i) {
        if (i == 0) {
            return "WIDTH";
        }
        if (i == 1) {
            return "HEIGHT";
        }
        return "INVALID_TYPE";
    }
}
