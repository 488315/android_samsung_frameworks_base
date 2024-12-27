package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class DrawTextRun extends PaintOperation {
    public static final Companion COMPANION = new Companion();
    int mContextEnd;
    int mContextStart;
    int mEnd;
    boolean mRtl;
    int mStart;
    int mTextID;
    float mX;
    float mY;

    public DrawTextRun(int textID, int start, int end, int contextStart, int contextEnd, float x, float y, boolean rtl) {
        this.mStart = 0;
        this.mEnd = 0;
        this.mContextStart = 0;
        this.mContextEnd = 0;
        this.mX = 0.0f;
        this.mY = 0.0f;
        this.mRtl = false;
        this.mTextID = textID;
        this.mStart = start;
        this.mEnd = end;
        this.mContextStart = contextStart;
        this.mContextEnd = contextEnd;
        this.mX = x;
        this.mY = y;
        this.mRtl = rtl;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mTextID, this.mStart, this.mEnd, this.mContextStart, this.mContextEnd, this.mX, this.mY, this.mRtl);
    }

    public String toString() {
        return "";
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int text = buffer.readInt();
            int start = buffer.readInt();
            int end = buffer.readInt();
            int contextStart = buffer.readInt();
            int contextEnd = buffer.readInt();
            float x = buffer.readFloat();
            float y = buffer.readFloat();
            boolean rtl = buffer.readBoolean();
            DrawTextRun op = new DrawTextRun(text, start, end, contextStart, contextEnd, x, y, rtl);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 0;
        }

        public void apply(WireBuffer buffer, int textID, int start, int end, int contextStart, int contextEnd, float x, float y, boolean rtl) {
            buffer.start(43);
            buffer.writeInt(textID);
            buffer.writeInt(start);
            buffer.writeInt(end);
            buffer.writeInt(contextStart);
            buffer.writeInt(contextEnd);
            buffer.writeFloat(x);
            buffer.writeFloat(y);
            buffer.writeBoolean(rtl);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawTextRun(this.mTextID, this.mStart, this.mEnd, this.mContextStart, this.mContextEnd, this.mX, this.mY, this.mRtl);
    }
}
