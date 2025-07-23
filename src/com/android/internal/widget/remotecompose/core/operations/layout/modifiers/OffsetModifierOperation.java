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
public class OffsetModifierOperation extends DecoratorModifierOperation {
    public static final String CLASS_NAME = "OffsetModifierOperation";
    private static final int OP_CODE = 221;
    float mX;
    float mY;

    public static int id() {
        return 221;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
    }

    public OffsetModifierOperation(float f, float f2) {
        this.mX = f;
        this.mY = f2;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public void setX(float f) {
        this.mX = f;
    }

    public void setY(float f) {
        this.mY = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mX, this.mY);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "OFFSET = [" + this.mX + ", " + this.mY + NavigationBarInflaterView.SIZE_MOD_END);
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
        float f = paintContext.getContext().mRemoteComposeState.getFloat(Utils.idFromNan(this.mX));
        float f2 = paintContext.getContext().mRemoteComposeState.getFloat(Utils.idFromNan(this.mY));
        float density = paintContext.getContext().getDensity();
        paintContext.translate(f * density, f2 * density);
    }

    public String toString() {
        return "OffsetModifierOperation(" + this.mX + ", " + this.mY + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2) {
        wireBuffer.start(221);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new OffsetModifierOperation(wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 221, CLASS_NAME).description("define the Offset Modifier").field(1, "x", "").field(1, "y", "");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("x", Float.valueOf(this.mX)).add("y", Float.valueOf(this.mY));
    }
}
