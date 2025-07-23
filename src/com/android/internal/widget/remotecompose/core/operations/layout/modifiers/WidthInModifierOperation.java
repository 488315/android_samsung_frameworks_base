package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.quality.ParameterCapability;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class WidthInModifierOperation extends DimensionInModifierOperation {
    public static final String CLASS_NAME = "WidthInModifierOperation";
    private static final int OP_CODE = 231;

    public static int id() {
        return 231;
    }

    public WidthInModifierOperation(float f, float f2) {
        super(231, f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionInModifierOperation, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, getMin(), getMax());
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new WidthInModifierOperation(wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 231, CLASS_NAME).description("Add additional constraints to the width").field(1, ParameterCapability.CAPABILITY_MIN, "The minimum width, -1 if not applied").field(1, "max", "The maximum width, -1 if not applied");
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2) {
        wireBuffer.start(231);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionInModifierOperation, com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "WIDTH_IN = [" + getMin() + ", " + getMax() + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add(ParameterCapability.CAPABILITY_MIN, this.mV1, this.mValue1).add("max", this.mV2, this.mValue2);
    }
}
