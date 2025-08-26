package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.List;

/* loaded from: classes6.dex */
public class BitmapData extends Operation implements SerializableToString, Serializable {
    private static final String CLASS_NAME = "BitmapData";
    public static final short ENCODING_FILE = 2;
    public static final short ENCODING_INLINE = 0;
    public static final short ENCODING_URL = 1;
    public static final int MAX_IMAGE_DIMENSION = 8000;
    private static final int OP_CODE = 101;
    public static final short TYPE_PNG = 1;
    public static final short TYPE_PNG_8888 = 0;
    public static final short TYPE_PNG_ALPHA_8 = 4;
    public static final short TYPE_RAW8 = 2;
    public static final short TYPE_RAW8888 = 3;
    byte[] mBitmap;
    short mEncoding;
    int mImageHeight;
    public final int mImageId;
    int mImageWidth;
    short mType;

    public static int id() {
        return 101;
    }

    public BitmapData(int i, int i2, int i3, byte[] bArr) {
        this.mImageId = i;
        this.mImageWidth = i2;
        this.mImageHeight = i3;
        this.mBitmap = bArr;
    }

    public void update(BitmapData bitmapData) {
        this.mImageWidth = bitmapData.mImageWidth;
        this.mImageHeight = bitmapData.mImageHeight;
        this.mBitmap = bitmapData.mBitmap;
        this.mType = bitmapData.mType;
        this.mEncoding = bitmapData.mEncoding;
    }

    public int getWidth() {
        return this.mImageWidth;
    }

    public int getHeight() {
        return this.mImageHeight;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mImageId, this.mImageWidth, this.mImageHeight, this.mBitmap);
    }

    public String toString() {
        return "BITMAP DATA " + this.mImageId;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public int getType() {
        return this.mType;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, byte[] bArr) {
        wireBuffer.start(101);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeBuffer(bArr);
    }

    public static void apply(WireBuffer wireBuffer, int i, short s, short s2, short s3, short s4, byte[] bArr) {
        wireBuffer.start(101);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt((s << 16) | s2);
        wireBuffer.writeInt((s3 << 16) | s4);
        wireBuffer.writeBuffer(bArr);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int i;
        int i2 = wireBuffer.readInt();
        int i3 = wireBuffer.readInt();
        int i4 = wireBuffer.readInt();
        int i5 = 0;
        if (i3 > 65535) {
            i = i3 >> 16;
            i3 &= 65535;
        } else {
            i = 0;
        }
        if (i4 > 65535) {
            i5 = i4 >> 16;
            i4 &= 65535;
        }
        if (i3 < 1 || i4 < 1 || i4 > 8000 || i3 > 8000) {
            throw new RuntimeException("Dimension of image is invalid " + i3 + "x" + i4);
        }
        BitmapData bitmapData = new BitmapData(i2, i3, i4, wireBuffer.readBuffer());
        bitmapData.mType = (short) i;
        bitmapData.mEncoding = (short) i5;
        list.add(bitmapData);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 101, CLASS_NAME).description("Bitmap data").field(0, "id", "id of bitmap data").field(9, "type", "width of the image").field(9, "width", "width of the image").field(9, "encoding", "height of the image").field(0, "width", "width of the image").field(9, "height", "height of the image").field(11, "values", Contract.CompressedEvents.Field.LENGTH, "Array of ints");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.putObject(this.mImageId, this);
        remoteContext.loadBitmap(this.mImageId, this.mEncoding, this.mType, this.mImageWidth, this.mImageHeight, this.mBitmap);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "BitmapData id " + this.mImageId + " (" + this.mImageWidth + "x" + this.mImageHeight + NavigationBarInflaterView.KEY_CODE_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("imageId", Integer.valueOf(this.mImageId)).add("imageWidth", Integer.valueOf(this.mImageWidth)).add("imageHeight", Integer.valueOf(this.mImageHeight)).add("imageType", getImageTypeString(this.mType)).add("encoding", getEncodingString(this.mEncoding));
    }

    private String getEncodingString(short s) {
        if (s == 0) {
            return "ENCODING_INLINE";
        }
        if (s == 1) {
            return "ENCODING_URL";
        }
        if (s == 2) {
            return "ENCODING_FILE";
        }
        return "ENCODING_INVALID";
    }

    private String getImageTypeString(short s) {
        if (s == 0) {
            return "TYPE_PNG_8888";
        }
        if (s == 1) {
            return "TYPE_PNG";
        }
        if (s == 2) {
            return "TYPE_RAW8";
        }
        if (s == 3) {
            return "TYPE_RAW8888";
        }
        if (s == 4) {
            return "TYPE_PNG_ALPHA_8";
        }
        return "TYPE_INVALID";
    }
}
