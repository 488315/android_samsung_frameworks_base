package com.android.internal.widget.remotecompose.core.operations;

import android.app.backup.FullBackup;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class ImageAttribute extends PaintOperation {
    private static final String CLASS_NAME = "ImageAttribute";
    public static final short IMAGE_HEIGHT = 1;
    public static final short IMAGE_WIDTH = 0;
    private static final int OP_CODE = 171;
    private final int[] mArgs;
    float[] mBounds = new float[4];
    public int mId;
    int mImageId;
    short mType;

    public static int id() {
        return 171;
    }

    public ImageAttribute(int i, int i2, short s, int[] iArr) {
        this.mId = i;
        this.mImageId = i2;
        this.mType = s;
        this.mArgs = iArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mImageId, this.mType, this.mArgs);
    }

    public String toString() {
        return "ImageAttribute[" + this.mId + "] = " + this.mImageId + " " + ((int) this.mType);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, short s, int[] iArr) {
        wireBuffer.start(171);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeShort(s);
        if (iArr == null) {
            wireBuffer.writeShort(0);
            return;
        }
        wireBuffer.writeShort((short) iArr.length);
        for (int i3 : iArr) {
            wireBuffer.writeInt(i3);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        short readShort = (short) wireBuffer.readShort();
        int readShort2 = (short) wireBuffer.readShort();
        int[] iArr = new int[readShort2];
        for (int i = 0; i < readShort2; i++) {
            iArr[i] = wireBuffer.readInt();
        }
        list.add(new ImageAttribute(readInt, readInt2, readShort, iArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Image Attributes", 171, CLASS_NAME).description("Measure text").field(0, "id", "id of float result of the measure").field(0, "ImageId", "id of the image").field(9, "type", "type: measure 0=width,1=height").field(9, "len", "number of additional arguments (currently 0)").field(0, FullBackup.APK_TREE_TOKEN, "len", "number of arguments");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        BitmapData bitmapData = (BitmapData) paintContext.getContext().getObject(this.mImageId);
        short s = this.mType;
        if (s == 0) {
            paintContext.getContext().loadFloat(this.mId, bitmapData.getWidth());
        } else {
            if (s != 1) {
                return;
            }
            paintContext.getContext().loadFloat(this.mId, bitmapData.getHeight());
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("imageId", Integer.valueOf(this.mImageId)).add("args", Collections.singletonList(this.mArgs)).addType(typeToString());
    }

    private String typeToString() {
        short s = this.mType;
        if (s == 0) {
            return "IMAGE_WIDTH";
        }
        if (s == 1) {
            return "IMAGE_HEIGHT";
        }
        return "INVALID_TYPE";
    }
}
