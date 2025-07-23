package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionModifierOperation;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class HeightModifierOperation extends DimensionModifierOperation {
    public static final String CLASS_NAME = "HeightModifierOperation";
    private static final int OP_CODE = 67;
    private HeightInModifierOperation mHeightIn;

    public static int id() {
        return 67;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f) {
        wireBuffer.start(67);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new HeightModifierOperation(DimensionModifierOperation.Type.fromInt(wireBuffer.readInt()), wireBuffer.readFloat()));
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mType.ordinal(), this.mValue);
    }

    public HeightModifierOperation(DimensionModifierOperation.Type type, float f) {
        super(type, f);
        this.mHeightIn = null;
    }

    public HeightModifierOperation(DimensionModifierOperation.Type type) {
        super(type);
        this.mHeightIn = null;
    }

    public HeightModifierOperation(float f) {
        super(f);
        this.mHeightIn = null;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionModifierOperation
    public String toString() {
        return "Height(" + this.mType + ", " + this.mValue + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionModifierOperation
    public String serializedName() {
        return "HEIGHT";
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 67, CLASS_NAME).description("define the animation").field(0, "type", "").field(1, "value", "");
    }

    public void setHeightIn(HeightInModifierOperation heightInModifierOperation) {
        this.mHeightIn = heightInModifierOperation;
    }

    public HeightInModifierOperation getHeightIn() {
        return this.mHeightIn;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("height", this.mValue, this.mOutValue).add("dimensionModifierType", this.mType);
    }
}
