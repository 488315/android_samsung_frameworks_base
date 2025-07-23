package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawTextOnPath extends PaintOperation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "DrawTextOnPath";
    private static final int OP_CODE = 53;
    float mHOffset;
    float mOutHOffset;
    float mOutVOffset;
    int mPathId;
    public int mTextId;
    float mVOffset;

    public static int id() {
        return 53;
    }

    public DrawTextOnPath(int i, int i2, float f, float f2) {
        this.mPathId = i2;
        this.mTextId = i;
        this.mHOffset = f;
        this.mOutHOffset = f;
        this.mVOffset = f2;
        this.mOutVOffset = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutHOffset = Float.isNaN(this.mHOffset) ? remoteContext.getFloat(Utils.idFromNan(this.mHOffset)) : this.mHOffset;
        this.mOutVOffset = Float.isNaN(this.mVOffset) ? remoteContext.getFloat(Utils.idFromNan(this.mVOffset)) : this.mVOffset;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mHOffset)) {
            remoteContext.listensTo(Utils.idFromNan(this.mHOffset), this);
        }
        if (Float.isNaN(this.mVOffset)) {
            remoteContext.listensTo(Utils.idFromNan(this.mVOffset), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextId, this.mPathId, this.mHOffset, this.mVOffset);
    }

    public String toString() {
        return "DrawTextOnPath [" + this.mTextId + "] [" + this.mPathId + "] " + Utils.floatToString(this.mHOffset, this.mOutHOffset) + ", " + Utils.floatToString(this.mVOffset, this.mOutVOffset);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawTextOnPath(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f, float f2) {
        wireBuffer.start(53);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 53, CLASS_NAME).description("Draw text along path object").field(0, "textId", "id of the text").field(0, "pathId", "id of the path").field(1, "xOffset", "x Shift of the text").field(1, "yOffset", "y Shift of the text");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawTextOnPath(this.mTextId, this.mPathId, this.mOutHOffset, this.mOutVOffset);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("pathId", Integer.valueOf(this.mPathId)).add("textId", Integer.valueOf(this.mTextId)).add("vOffset", this.mVOffset, this.mOutVOffset).add("hOffset", this.mHOffset, this.mOutHOffset);
    }
}
