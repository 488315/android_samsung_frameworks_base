package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.ImageScaling;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawBitmapScaled extends PaintOperation implements VariableSupport, AccessibleComponent {
    private static final String CLASS_NAME = "DrawBitmapScaled";
    private static final int OP_CODE = 149;
    public static final int SCALE_CROP = 5;
    public static final int SCALE_FILL_BOUNDS = 6;
    public static final int SCALE_FILL_HEIGHT = 3;
    public static final int SCALE_FILL_WIDTH = 2;
    public static final int SCALE_FIT = 4;
    public static final int SCALE_FIXED_SCALE = 7;
    public static final int SCALE_INSIDE = 1;
    public static final int SCALE_NONE = 0;
    int mContentDescId;
    float mDstBottom;
    float mDstLeft;
    float mDstRight;
    float mDstTop;
    int mImageId;
    int mMode;
    float mOutDstBottom;
    float mOutDstLeft;
    float mOutDstRight;
    float mOutDstTop;
    float mOutScaleFactor;
    float mOutSrcBottom;
    float mOutSrcLeft;
    float mOutSrcRight;
    float mOutSrcTop;
    float mScaleFactor;
    int mScaleType;
    ImageScaling mScaling = new ImageScaling();
    float mSrcBottom;
    float mSrcLeft;
    float mSrcRight;
    float mSrcTop;

    public static int id() {
        return 149;
    }

    public DrawBitmapScaled(int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i2, float f9, int i3) {
        this.mImageId = i;
        this.mSrcLeft = f;
        this.mOutSrcLeft = f;
        this.mSrcTop = f2;
        this.mOutSrcTop = f2;
        this.mSrcRight = f3;
        this.mOutSrcRight = f3;
        this.mSrcBottom = f4;
        this.mOutSrcBottom = f4;
        this.mDstLeft = f5;
        this.mOutDstLeft = f5;
        this.mDstTop = f6;
        this.mOutDstTop = f6;
        this.mDstRight = f7;
        this.mOutDstRight = f7;
        this.mDstBottom = f8;
        this.mOutDstBottom = f8;
        this.mScaleType = i2 & 255;
        this.mMode = i2 >> 8;
        this.mScaleFactor = f9;
        this.mOutScaleFactor = f9;
        this.mContentDescId = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        float f;
        float f2;
        float f3;
        this.mOutSrcLeft = Float.isNaN(this.mSrcLeft) ? remoteContext.getFloat(Utils.idFromNan(this.mSrcLeft)) : this.mSrcLeft;
        this.mOutSrcTop = Float.isNaN(this.mSrcTop) ? remoteContext.getFloat(Utils.idFromNan(this.mSrcTop)) : this.mSrcTop;
        this.mOutSrcRight = Float.isNaN(this.mSrcRight) ? remoteContext.getFloat(Utils.idFromNan(this.mSrcRight)) : this.mSrcRight;
        if (Float.isNaN(this.mSrcBottom)) {
            f = remoteContext.getFloat(Utils.idFromNan(this.mSrcBottom));
        } else {
            f = this.mSrcBottom;
        }
        this.mOutSrcBottom = f;
        this.mOutDstLeft = Float.isNaN(this.mDstLeft) ? remoteContext.getFloat(Utils.idFromNan(this.mDstLeft)) : this.mDstLeft;
        this.mOutDstTop = Float.isNaN(this.mDstTop) ? remoteContext.getFloat(Utils.idFromNan(this.mDstTop)) : this.mDstTop;
        this.mOutDstRight = Float.isNaN(this.mDstRight) ? remoteContext.getFloat(Utils.idFromNan(this.mDstRight)) : this.mDstRight;
        if (Float.isNaN(this.mDstBottom)) {
            f2 = remoteContext.getFloat(Utils.idFromNan(this.mDstBottom));
        } else {
            f2 = this.mDstBottom;
        }
        this.mOutDstBottom = f2;
        if (Float.isNaN(this.mScaleFactor)) {
            f3 = remoteContext.getFloat(Utils.idFromNan(this.mScaleFactor));
        } else {
            f3 = this.mScaleFactor;
        }
        this.mOutScaleFactor = f3;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        register(remoteContext, this.mSrcLeft);
        register(remoteContext, this.mSrcTop);
        register(remoteContext, this.mSrcRight);
        register(remoteContext, this.mSrcBottom);
        register(remoteContext, this.mDstLeft);
        register(remoteContext, this.mDstTop);
        register(remoteContext, this.mDstRight);
        register(remoteContext, this.mDstBottom);
        register(remoteContext, this.mScaleFactor);
    }

    private void register(RemoteContext remoteContext, float f) {
        if (Float.isNaN(f)) {
            remoteContext.listensTo(Utils.idFromNan(f), this);
        }
    }

    static String str(float f) {
        return ("  " + ((int) f)).substring(r2.length() - 3);
    }

    void print(String str, float f, float f2, float f3, float f4) {
        System.out.println((str + str(f) + ", " + str(f2) + ", " + str(f3) + ", " + str(f4) + ", ") + " [" + str(f3 - f) + " x " + str(f4 - f2) + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mScaleType, this.mScaleFactor, this.mContentDescId);
    }

    public String toString() {
        return "DrawBitmapScaled " + this.mImageId + " [" + Utils.floatToString(this.mSrcLeft, this.mOutSrcLeft) + " " + Utils.floatToString(this.mSrcTop, this.mOutSrcTop) + " " + Utils.floatToString(this.mSrcRight, this.mOutSrcRight) + " " + Utils.floatToString(this.mSrcBottom, this.mOutSrcBottom) + "] - [" + Utils.floatToString(this.mDstLeft, this.mOutDstLeft) + " " + Utils.floatToString(this.mDstTop, this.mOutDstTop) + " " + Utils.floatToString(this.mDstRight, this.mOutDstRight) + " " + Utils.floatToString(this.mDstBottom, this.mOutDstBottom) + "]  " + this.mScaleType + " " + Utils.floatToString(this.mScaleFactor, this.mOutScaleFactor);
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getContentDescriptionId() {
        return Integer.valueOf(this.mContentDescId);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i2, float f9, int i3) {
        wireBuffer.start(149);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeFloat(f5);
        wireBuffer.writeFloat(f6);
        wireBuffer.writeFloat(f7);
        wireBuffer.writeFloat(f8);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f9);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawBitmapScaled(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 149, CLASS_NAME).description("Draw a bitmap using integer coordinates").field(0, "id", "id of bitmap").field(1, "srcLeft", "The left side of the image").field(1, "srcTop", "The top of the image").field(1, "srcRight", "The right side of the image").field(1, "srcBottom", "The bottom of the output").field(1, "dstLeft", "The left side of the output").field(1, "dstTop", "The top of the output").field(1, "dstRight", "The right side of the output").field(0, "type", "type of auto scaling").field(0, "scaleFactor", "for allowed").field(0, "cdId", "id of string");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        this.mScaling.setup(this.mOutSrcLeft, this.mOutSrcTop, this.mOutSrcRight, this.mOutSrcBottom, this.mOutDstLeft, this.mOutDstTop, this.mOutDstRight, this.mOutDstBottom, this.mScaleType, this.mOutScaleFactor);
        paintContext.save();
        paintContext.clipRect(this.mOutDstLeft, this.mOutDstTop, this.mOutDstRight, this.mOutDstBottom);
        int integer = this.mImageId;
        if ((this.mMode & 1) != 0) {
            integer = paintContext.getContext().getInteger(integer);
        }
        paintContext.drawBitmap(integer, (int) this.mOutSrcLeft, (int) this.mOutSrcTop, (int) this.mOutSrcRight, (int) this.mOutSrcBottom, (int) this.mScaling.mFinalDstLeft, (int) this.mScaling.mFinalDstTop, (int) this.mScaling.mFinalDstRight, (int) this.mScaling.mFinalDstBottom, this.mContentDescId);
        paintContext.restore();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("imageId", Integer.valueOf(this.mImageId)).add("contentDescriptionId", Integer.valueOf(this.mContentDescId)).add("scaleType", getScaleTypeString()).add("mode", Integer.valueOf(this.mMode)).add("scaleFactor", this.mScaleFactor, this.mOutScaleFactor).add("srcLeft", this.mSrcLeft, this.mOutSrcLeft).add("srcTop", this.mSrcTop, this.mOutSrcTop).add("srcRight", this.mSrcRight, this.mOutSrcRight).add("srcBottom", this.mSrcBottom, this.mOutSrcBottom).add("dstLeft", this.mDstLeft, this.mOutDstLeft).add("dstTop", this.mDstTop, this.mOutDstTop).add("dstRight", this.mDstRight, this.mOutDstRight).add("dstBottom", this.mDstBottom, this.mOutDstBottom);
    }

    private String getScaleTypeString() {
        switch (this.mScaleType) {
            case 0:
                return "SCALE_NONE";
            case 1:
                return "SCALE_INSIDE";
            case 2:
                return "SCALE_FILL_WIDTH";
            case 3:
                return "SCALE_FILL_HEIGHT";
            case 4:
                return "SCALE_FIT";
            case 5:
                return "SCALE_CROP";
            case 6:
                return "SCALE_FILL_BOUNDS";
            case 7:
                return "SCALE_FIXED_SCALE";
            default:
                return "INVALID_SCALE_TYPE";
        }
    }
}
