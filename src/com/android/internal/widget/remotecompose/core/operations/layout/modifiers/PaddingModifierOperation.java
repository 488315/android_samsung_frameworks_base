package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.List;

/* loaded from: classes6.dex */
public class PaddingModifierOperation extends Operation implements ModifierOperation {
    public static final String CLASS_NAME = "PaddingModifierOperation";
    private static final int OP_CODE = 58;
    float mBottom;
    float mLeft;
    float mRight;
    float mTop;

    public static int id() {
        return 58;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    public PaddingModifierOperation(float f, float f2, float f3, float f4) {
        this.mLeft = f;
        this.mTop = f2;
        this.mRight = f3;
        this.mBottom = f4;
    }

    public float getLeft() {
        return this.mLeft;
    }

    public float getTop() {
        return this.mTop;
    }

    public float getRight() {
        return this.mRight;
    }

    public float getBottom() {
        return this.mBottom;
    }

    public void setLeft(float f) {
        this.mLeft = f;
    }

    public void setTop(float f) {
        this.mTop = f;
    }

    public void setRight(float f) {
        this.mRight = f;
    }

    public void setBottom(float f) {
        this.mBottom = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mLeft, this.mTop, this.mRight, this.mBottom);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "PADDING = [" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + NavigationBarInflaterView.SIZE_MOD_END);
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

    public String toString() {
        return "PaddingModifierOperation(" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        wireBuffer.start(58);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new PaddingModifierOperation(wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 58, CLASS_NAME).description("define the Padding Modifier").field(1, "left", "").field(1, GenerateXML.TOP, "").field(1, "right", "").field(1, GenerateXML.BOTTOM, "");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("left", Float.valueOf(this.mLeft)).add(GenerateXML.TOP, Float.valueOf(this.mTop)).add("right", Float.valueOf(this.mRight)).add(GenerateXML.BOTTOM, Float.valueOf(this.mBottom));
    }
}
