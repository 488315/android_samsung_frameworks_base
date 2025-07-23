package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawBitmapInt extends PaintOperation implements AccessibleComponent {
    private static final String CLASS_NAME = "DrawBitmapInt";
    private static final int OP_CODE = 66;
    int mContentDescId;
    int mDstBottom;
    int mDstLeft;
    int mDstRight;
    int mDstTop;
    int mImageId;
    int mSrcBottom;
    int mSrcLeft;
    int mSrcRight;
    int mSrcTop;

    public static int id() {
        return 66;
    }

    public DrawBitmapInt(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mImageId = i;
        this.mSrcLeft = i2;
        this.mSrcTop = i3;
        this.mSrcRight = i4;
        this.mSrcBottom = i5;
        this.mDstLeft = i6;
        this.mDstTop = i7;
        this.mDstRight = i8;
        this.mDstBottom = i9;
        this.mContentDescId = i10;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mContentDescId);
    }

    public String toString() {
        return "DRAW_BITMAP_INT " + this.mImageId + " on " + this.mSrcLeft + " " + this.mSrcTop + " " + this.mSrcRight + " " + this.mSrcBottom + " - " + this.mDstLeft + " " + this.mDstTop + " " + this.mDstRight + " " + this.mDstBottom + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getContentDescriptionId() {
        return Integer.valueOf(this.mContentDescId);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        wireBuffer.start(66);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeInt(i5);
        wireBuffer.writeInt(i6);
        wireBuffer.writeInt(i7);
        wireBuffer.writeInt(i8);
        wireBuffer.writeInt(i9);
        wireBuffer.writeInt(i10);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawBitmapInt(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 66, CLASS_NAME).description("Draw a bitmap using integer coordinates").field(0, "id", "id of bitmap").field(0, "srcLeft", "The left side of the image").field(0, "srcTop", "The top of the image").field(0, "srcRight", "The right side of the image").field(0, "srcBottom", "The bottom of the image").field(0, "dstLeft", "The left side of the image").field(0, "dstTop", "The top of the image").field(0, "dstRight", "The right side of the image").field(0, "dstBottom", "The bottom of the image").field(0, "cdId", "id of string");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawBitmap(this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mContentDescId);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("imageId", Integer.valueOf(this.mImageId)).add("contentDescriptionId", Integer.valueOf(this.mContentDescId)).add("srcLeft", Integer.valueOf(this.mSrcLeft)).add("srcTop", Integer.valueOf(this.mSrcTop)).add("srcRight", Integer.valueOf(this.mSrcRight)).add("srcBottom", Integer.valueOf(this.mSrcBottom)).add("dstLeft", Integer.valueOf(this.mDstLeft)).add("dstTop", Integer.valueOf(this.mDstTop)).add("dstRight", Integer.valueOf(this.mDstRight)).add("dstBottom", Integer.valueOf(this.mDstBottom));
    }
}
