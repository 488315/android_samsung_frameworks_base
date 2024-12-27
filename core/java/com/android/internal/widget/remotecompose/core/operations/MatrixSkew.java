package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class MatrixSkew extends PaintOperation {
    public static final Companion COMPANION = new Companion();
    float mSkewX;
    float mSkewY;

    public MatrixSkew(float skewX, float skewY) {
        this.mSkewX = skewX;
        this.mSkewY = skewY;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mSkewX, this.mSkewY);
    }

    public String toString() {
        return "DrawArc "
                + this.mSkewY
                + ", "
                + this.mSkewY
                + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            float skewX = buffer.readFloat();
            float skewY = buffer.readFloat();
            MatrixSkew op = new MatrixSkew(skewX, skewY);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "Matrix";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 128;
        }

        public void apply(WireBuffer buffer, float skewX, float skewY) {
            buffer.start(128);
            buffer.writeFloat(skewX);
            buffer.writeFloat(skewY);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.matrixSkew(this.mSkewX, this.mSkewY);
    }
}
