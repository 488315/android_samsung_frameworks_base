package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class DrawTextOnPath extends PaintOperation {
    public static final Companion COMPANION = new Companion();
    float mHOffset;
    int mPathId;
    public int mTextId;
    float mVOffset;

    public DrawTextOnPath(int textId, int pathId, float hOffset, float vOffset) {
        this.mPathId = pathId;
        this.mTextId = textId;
        this.mHOffset = vOffset;
        this.mVOffset = hOffset;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mTextId, this.mPathId, this.mHOffset, this.mVOffset);
    }

    public String toString() {
        return "DrawTextOnPath ["
                + this.mTextId
                + "] ["
                + this.mPathId
                + "] "
                + this.mHOffset
                + ", "
                + this.mVOffset;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int textId = buffer.readInt();
            int pathId = buffer.readInt();
            float hOffset = buffer.readFloat();
            float vOffset = buffer.readFloat();
            DrawTextOnPath op = new DrawTextOnPath(textId, pathId, hOffset, vOffset);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "DrawTextOnPath";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 53;
        }

        public void apply(WireBuffer buffer, int textId, int pathId, float hOffset, float vOffset) {
            buffer.start(53);
            buffer.writeInt(textId);
            buffer.writeInt(pathId);
            buffer.writeFloat(hOffset);
            buffer.writeFloat(vOffset);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawTextOnPath(this.mTextId, this.mPathId, this.mHOffset, this.mVOffset);
    }
}
