package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class BitmapData implements Operation {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_IMAGE_DIMENSION = 8000;
    byte[] mBitmap;
    int mImageHeight;
    int mImageId;
    int mImageWidth;

    public BitmapData(int imageId, int width, int height, byte[] bitmap) {
        this.mImageId = imageId;
        this.mImageWidth = width;
        this.mImageHeight = height;
        this.mBitmap = bitmap;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mImageId, this.mImageWidth, this.mImageHeight, this.mBitmap);
    }

    public String toString() {
        return "BITMAP DATA " + this.mImageId;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "BitmapData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 101;
        }

        public void apply(WireBuffer buffer, int imageId, int width, int height, byte[] bitmap) {
            buffer.start(101);
            buffer.writeInt(imageId);
            buffer.writeInt(width);
            buffer.writeInt(height);
            buffer.writeBuffer(bitmap);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int imageId = buffer.readInt();
            int width = buffer.readInt();
            int height = buffer.readInt();
            if (width < 1 || height < 1 || height > 8000 || width > 8000) {
                throw new RuntimeException("Dimension of image is invalid " + width + "x" + height);
            }
            byte[] bitmap = buffer.readBuffer();
            operations.add(new BitmapData(imageId, width, height, bitmap));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.loadBitmap(this.mImageId, this.mImageWidth, this.mImageHeight, this.mBitmap);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
