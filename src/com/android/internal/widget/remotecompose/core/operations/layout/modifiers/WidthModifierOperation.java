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
public class WidthModifierOperation extends DimensionModifierOperation {
    public static final String CLASS_NAME = "WidthModifierOperation";
    private static final int OP_CODE = 16;
    private WidthInModifierOperation mWidthIn;

    public static int id() {
        return 16;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f) {
        wireBuffer.start(16);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new WidthModifierOperation(DimensionModifierOperation.Type.fromInt(wireBuffer.readInt()), wireBuffer.readFloat()));
    }

    public WidthModifierOperation(DimensionModifierOperation.Type type, float f) {
        super(type, f);
        this.mWidthIn = null;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mType.ordinal(), this.mValue);
    }

    public WidthModifierOperation(DimensionModifierOperation.Type type) {
        super(type);
        this.mWidthIn = null;
    }

    public WidthModifierOperation(float f) {
        super(f);
        this.mWidthIn = null;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionModifierOperation
    public String toString() {
        return "Width(" + this.mType + ", " + this.mValue + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionModifierOperation
    public String serializedName() {
        return "WIDTH";
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 16, CLASS_NAME).description("define the animation").field(0, "type", "").field(1, "value", "");
    }

    public void setWidthIn(WidthInModifierOperation widthInModifierOperation) {
        this.mWidthIn = widthInModifierOperation;
    }

    public WidthInModifierOperation getWidthIn() {
        return this.mWidthIn;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("width", this.mValue, this.mOutValue).add("dimensionModifierType", this.mType);
    }
}
