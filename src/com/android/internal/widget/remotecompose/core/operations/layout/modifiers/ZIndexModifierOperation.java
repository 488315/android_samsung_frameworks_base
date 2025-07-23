package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class ZIndexModifierOperation extends DecoratorModifierOperation {
    public static final String CLASS_NAME = "ZIndexModifierOperation";
    private static final int OP_CODE = 223;
    float mCurrentValue;
    float mValue;

    public static int id() {
        return 223;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
    }

    public ZIndexModifierOperation(float f) {
        this.mValue = f;
    }

    public float getValue() {
        return this.mCurrentValue;
    }

    public void setValue(float f) {
        this.mValue = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "ZINDEX = [" + this.mValue + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        float f = this.mValue;
        this.mCurrentValue = f;
        if (Utils.isVariable(f)) {
            this.mCurrentValue = paintContext.getContext().mRemoteComposeState.getFloat(Utils.idFromNan(this.mValue));
        }
    }

    public String toString() {
        return "ZIndexModifierOperation(" + this.mValue + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, float f) {
        wireBuffer.start(223);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ZIndexModifierOperation(wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 223, CLASS_NAME).description("define the Z-Index Modifier").field(1, "value", "");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("value", Float.valueOf(this.mValue));
    }
}
