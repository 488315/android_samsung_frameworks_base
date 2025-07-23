package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class ColorAttribute extends PaintOperation {
    private static final String CLASS_NAME = "ColorAttribute";
    public static final short COLOR_ALPHA = 6;
    public static final short COLOR_BLUE = 5;
    public static final short COLOR_BRIGHTNESS = 2;
    public static final short COLOR_GREEN = 4;
    public static final short COLOR_HUE = 0;
    public static final short COLOR_RED = 3;
    public static final short COLOR_SATURATION = 1;
    private static final int OP_CODE = 180;
    float[] mBounds = new float[4];
    public int mColorId;
    public int mId;
    public short mType;

    public static int id() {
        return 180;
    }

    public ColorAttribute(int i, int i2, short s) {
        this.mId = i;
        this.mColorId = i2;
        this.mType = s;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mColorId, this.mType);
    }

    public String toString() {
        return "ColorAttribute[" + this.mId + "] = " + this.mColorId + " " + ((int) this.mType);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, short s) {
        wireBuffer.start(180);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeShort(s);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ColorAttribute(wireBuffer.readInt(), wireBuffer.readInt(), (short) wireBuffer.readShort()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Color Operations", 180, CLASS_NAME).description("Calculate Information about a Color").field(0, "id", "id to output").field(0, "longId", "id of color").field(9, "type", "the type information to extract");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        int i = this.mType & 255;
        RemoteContext context = paintContext.getContext();
        int color = context.getColor(this.mColorId);
        switch (i) {
            case 0:
                context.loadFloat(this.mId, Utils.getHue(color));
                break;
            case 1:
                context.loadFloat(this.mId, Utils.getSaturation(color));
                break;
            case 2:
                context.loadFloat(this.mId, Utils.getBrightness(color));
                break;
            case 3:
                context.loadFloat(this.mId, ((color >> 16) & 255) / 255.0f);
                break;
            case 4:
                context.loadFloat(this.mId, ((color >> 8) & 255) / 255.0f);
                break;
            case 5:
                context.loadFloat(this.mId, (color & 255) / 255.0f);
                break;
            case 6:
                context.loadFloat(this.mId, ((color >> 24) & 255) / 255.0f);
                break;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("timeId", Integer.valueOf(this.mColorId)).addType(getTypeString());
    }

    private String getTypeString() {
        switch (this.mType & 255) {
            case 0:
                return "COLOR_HUE";
            case 1:
                return "COLOR_SATURATION";
            case 2:
                return "COLOR_BRIGHTNESS";
            case 3:
                return "COLOR_RED";
            case 4:
                return "COLOR_GREEN";
            case 5:
                return "COLOR_BLUE";
            case 6:
                return "COLOR_ALPHA";
            default:
                return "INVALID_TIME_TYPE";
        }
    }
}
