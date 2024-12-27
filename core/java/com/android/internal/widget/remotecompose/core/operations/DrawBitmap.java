package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

/* loaded from: classes5.dex */
public class DrawBitmap extends PaintOperation implements VariableSupport {
    public static final Companion COMPANION = new Companion();
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

    public DrawBitmap(
            int imageId, float left, float top, float right, float bottom, int descriptionId) {
        this.mDescriptionId = 0;
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
        this.mId = imageId;
        this.mDescriptionId = descriptionId;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        this.mOutputLeft =
                Float.isNaN(this.mLeft)
                        ? context.getFloat(Utils.idFromNan(this.mLeft))
                        : this.mLeft;
        this.mOutputTop =
                Float.isNaN(this.mTop) ? context.getFloat(Utils.idFromNan(this.mTop)) : this.mTop;
        this.mOutputRight =
                Float.isNaN(this.mRight)
                        ? context.getFloat(Utils.idFromNan(this.mRight))
                        : this.mRight;
        this.mOutputBottom =
                Float.isNaN(this.mBottom)
                        ? context.getFloat(Utils.idFromNan(this.mBottom))
                        : this.mBottom;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (Float.isNaN(this.mLeft)) {
            context.listensTo(Utils.idFromNan(this.mLeft), this);
        }
        if (Float.isNaN(this.mTop)) {
            context.listensTo(Utils.idFromNan(this.mTop), this);
        }
        if (Float.isNaN(this.mRight)) {
            context.listensTo(Utils.idFromNan(this.mRight), this);
        }
        if (Float.isNaN(this.mBottom)) {
            context.listensTo(Utils.idFromNan(this.mBottom), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(
                buffer,
                this.mId,
                this.mLeft,
                this.mTop,
                this.mRight,
                this.mBottom,
                this.mDescriptionId);
    }

    public String toString() {
        return "DrawBitmap (desc="
                + this.mDescriptionId
                + NavigationBarInflaterView.KEY_CODE_END
                + this.mLeft
                + " "
                + this.mTop
                + " "
                + this.mRight
                + " "
                + this.mBottom
                + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int id = buffer.readInt();
            float sLeft = buffer.readFloat();
            float srcTop = buffer.readFloat();
            float srcRight = buffer.readFloat();
            float srcBottom = buffer.readFloat();
            int discriptionId = buffer.readInt();
            DrawBitmap op = new DrawBitmap(id, sLeft, srcTop, srcRight, srcBottom, discriptionId);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "DrawOval";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 44;
        }

        public void apply(
                WireBuffer buffer,
                int id,
                float left,
                float top,
                float right,
                float bottom,
                int descriptionId) {
            buffer.start(44);
            buffer.writeInt(id);
            buffer.writeFloat(left);
            buffer.writeFloat(top);
            buffer.writeFloat(right);
            buffer.writeFloat(bottom);
            buffer.writeInt(descriptionId);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawBitmap(
                this.mId, this.mOutputLeft, this.mOutputTop, this.mOutputRight, this.mOutputBottom);
    }
}
