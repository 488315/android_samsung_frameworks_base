package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class DrawTweenPath extends PaintOperation {
    public static final Companion COMPANION = new Companion();
    int mPath1Id;
    int mPath2Id;
    float mStart;
    float mStop;
    float mTween;

    public DrawTweenPath(int path1Id, int path2Id, float tween, float start, float stop) {
        this.mTween = tween;
        this.mStart = start;
        this.mStop = stop;
        this.mPath1Id = path1Id;
        this.mPath2Id = path2Id;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mPath1Id, this.mPath2Id, this.mTween, this.mStart, this.mStop);
    }

    public String toString() {
        return "DrawTweenPath " + this.mPath1Id + " " + this.mPath2Id + " " + this.mTween + " " + this.mStart + " - " + this.mStop;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int path1Id = buffer.readInt();
            int path2Id = buffer.readInt();
            float tween = buffer.readFloat();
            float start = buffer.readFloat();
            float stop = buffer.readFloat();
            DrawTweenPath op = new DrawTweenPath(path1Id, path2Id, tween, start, stop);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "DrawTweenPath";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 125;
        }

        public void apply(WireBuffer buffer, int path1Id, int path2Id, float tween, float start, float stop) {
            buffer.start(125);
            buffer.writeInt(path1Id);
            buffer.writeInt(path2Id);
            buffer.writeFloat(tween);
            buffer.writeFloat(start);
            buffer.writeFloat(stop);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawTweenPath(this.mPath1Id, this.mPath2Id, this.mTween, this.mStart, this.mStop);
    }
}
