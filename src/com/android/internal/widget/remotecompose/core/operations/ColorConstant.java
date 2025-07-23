package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class ColorConstant extends Operation implements Serializable {
    private static final String CLASS_NAME = "ColorConstant";
    private static final int OP_CODE = 138;
    public int mColor;
    public int mColorId;

    public static int id() {
        return 138;
    }

    public ColorConstant(int i, int i2) {
        this.mColorId = i;
        this.mColor = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mColorId, this.mColor);
    }

    public String toString() {
        return "ColorConstant[" + this.mColorId + "] = " + Utils.colorInt(this.mColor) + "";
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(138);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ColorConstant(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 138, CLASS_NAME).description("Define a Color").field(0, "id", "Id of the color").field(0, "color", "32 bit ARGB color");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadColor(this.mColorId, this.mColor);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("color", Utils.colorInt(this.mColor)).add("colorId", Integer.valueOf(this.mColorId));
    }
}
