package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawBitmap extends PaintOperation implements VariableSupport {
    private static final String CLASS_NAME = "DrawBitmap";
    private static final int OP_CODE = 44;
    float mBottom;
    int mDescriptionId;
    int mId;
    float mLeft;
    float mOutputBottom;
    float mOutputLeft;
    float mOutputRight;
    float mOutputTop;
    float mRight;
    float mTop;

    public static int id() {
        return 44;
    }

    public DrawBitmap(int i, float f, float f2, float f3, float f4, int i2) {
        this.mLeft = f;
        this.mTop = f2;
        this.mRight = f3;
        this.mBottom = f4;
        this.mId = i;
        this.mDescriptionId = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutputLeft = Float.isNaN(this.mLeft) ? remoteContext.getFloat(Utils.idFromNan(this.mLeft)) : this.mLeft;
        this.mOutputTop = Float.isNaN(this.mTop) ? remoteContext.getFloat(Utils.idFromNan(this.mTop)) : this.mTop;
        this.mOutputRight = Float.isNaN(this.mRight) ? remoteContext.getFloat(Utils.idFromNan(this.mRight)) : this.mRight;
        this.mOutputBottom = Float.isNaN(this.mBottom) ? remoteContext.getFloat(Utils.idFromNan(this.mBottom)) : this.mBottom;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mLeft)) {
            remoteContext.listensTo(Utils.idFromNan(this.mLeft), this);
        }
        if (Float.isNaN(this.mTop)) {
            remoteContext.listensTo(Utils.idFromNan(this.mTop), this);
        }
        if (Float.isNaN(this.mRight)) {
            remoteContext.listensTo(Utils.idFromNan(this.mRight), this);
        }
        if (Float.isNaN(this.mBottom)) {
            remoteContext.listensTo(Utils.idFromNan(this.mBottom), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mLeft, this.mTop, this.mRight, this.mBottom, this.mDescriptionId);
    }

    public String toString() {
        return "DrawBitmap (desc=" + this.mDescriptionId + NavigationBarInflaterView.KEY_CODE_END + this.mLeft + " " + this.mTop + " " + this.mRight + " " + this.mBottom + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawBitmap(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3, float f4, int i2) {
        wireBuffer.start(44);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeInt(i2);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 44, CLASS_NAME).description("Draw a bitmap").field(0, "id", "id of float").field(1, "left", "The left side of the image").field(1, GenerateXML.TOP, "The top of the image").field(1, "right", "The right side of the image").field(1, GenerateXML.BOTTOM, "The bottom of the image").field(0, "descriptionId", "id of string");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawBitmap(this.mId, this.mOutputLeft, this.mOutputTop, this.mOutputRight, this.mOutputBottom);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("imageId", Integer.valueOf(this.mId)).add("contentDescriptionId", Integer.valueOf(this.mDescriptionId)).add("left", this.mLeft, this.mOutputLeft).add(GenerateXML.TOP, this.mTop, this.mOutputTop).add("right", this.mRight, this.mOutputRight).add(GenerateXML.BOTTOM, this.mBottom, this.mOutputBottom);
    }
}
