package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class DrawPath extends PaintOperation {
    public static final Companion COMPANION = new Companion();
    int mId;
    float mStart = 0.0f;
    float mEnd = 1.0f;

    public DrawPath(int pathId) {
        this.mId = pathId;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mId);
    }

    public String toString() {
        return "DrawPath [" + this.mId + "], " + this.mStart + ", " + this.mEnd;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int id = buffer.readInt();
            DrawPath op = new DrawPath(id);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "DrawPath";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 124;
        }

        public void apply(WireBuffer buffer, int id) {
            buffer.start(124);
            buffer.writeInt(id);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawPath(this.mId, this.mStart, this.mEnd);
    }
}
