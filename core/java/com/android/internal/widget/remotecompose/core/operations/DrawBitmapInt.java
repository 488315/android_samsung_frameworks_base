package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class DrawBitmapInt extends PaintOperation {
    public static final Companion COMPANION = new Companion();
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

    public DrawBitmapInt(int imageId, int srcLeft, int srcTop, int srcRight, int srcBottom, int dstLeft, int dstTop, int dstRight, int dstBottom, int cdId) {
        this.mContentDescId = 0;
        this.mImageId = imageId;
        this.mSrcLeft = srcLeft;
        this.mSrcTop = srcTop;
        this.mSrcRight = srcRight;
        this.mSrcBottom = srcBottom;
        this.mDstLeft = dstLeft;
        this.mDstTop = dstTop;
        this.mDstRight = dstRight;
        this.mDstBottom = dstBottom;
        this.mContentDescId = cdId;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mContentDescId);
    }

    public String toString() {
        return "DRAW_BITMAP_INT " + this.mImageId + " on " + this.mSrcLeft + " " + this.mSrcTop + " " + this.mSrcRight + " " + this.mSrcBottom + " - " + this.mDstLeft + " " + this.mDstTop + " " + this.mDstRight + " " + this.mDstBottom + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "DrawBitmapInt";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 44;
        }

        public void apply(WireBuffer buffer, int imageId, int srcLeft, int srcTop, int srcRight, int srcBottom, int dstLeft, int dstTop, int dstRight, int dstBottom, int cdId) {
            buffer.start(66);
            buffer.writeInt(imageId);
            buffer.writeInt(srcLeft);
            buffer.writeInt(srcTop);
            buffer.writeInt(srcRight);
            buffer.writeInt(srcBottom);
            buffer.writeInt(dstLeft);
            buffer.writeInt(dstTop);
            buffer.writeInt(dstRight);
            buffer.writeInt(dstBottom);
            buffer.writeInt(cdId);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int imageId = buffer.readInt();
            int sLeft = buffer.readInt();
            int srcTop = buffer.readInt();
            int srcRight = buffer.readInt();
            int srcBottom = buffer.readInt();
            int dstLeft = buffer.readInt();
            int dstTop = buffer.readInt();
            int dstRight = buffer.readInt();
            int dstBottom = buffer.readInt();
            int cdId = buffer.readInt();
            DrawBitmapInt op = new DrawBitmapInt(imageId, sLeft, srcTop, srcRight, srcBottom, dstLeft, dstTop, dstRight, dstBottom, cdId);
            operations.add(op);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawBitmap(this.mImageId, this.mSrcLeft, this.mSrcTop, this.mSrcRight, this.mSrcBottom, this.mDstLeft, this.mDstTop, this.mDstRight, this.mDstBottom, this.mContentDescId);
    }
}
