package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class ClipPath extends PaintOperation {
    public static final Companion COMPANION = new Companion();
    public static final int DIFFERENCE = 1;
    public static final int INTERSECT = 2;
    public static final int REPLACE = 0;
    public static final int REVERSE_DIFFERENCE = 5;
    public static final int UNDEFINED = 6;
    public static final int UNION = 3;
    public static final int XOR = 4;
    int mId;
    int mRegionOp;

    public ClipPath(int pathId, int regionOp) {
        this.mId = pathId;
        this.mRegionOp = regionOp;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mId);
    }

    public String toString() {
        return "ClipPath " + this.mId + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static class Companion implements CompanionOperation {
        public static final int PATH_CLIP_DIFFERENCE = 1;
        public static final int PATH_CLIP_INTERSECT = 2;
        public static final int PATH_CLIP_REPLACE = 0;
        public static final int PATH_CLIP_REVERSE_DIFFERENCE = 5;
        public static final int PATH_CLIP_UNDEFINED = 6;
        public static final int PATH_CLIP_UNION = 3;
        public static final int PATH_CLIP_XOR = 4;

        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int pack = buffer.readInt();
            int id = 1048575 & pack;
            int regionOp = pack >> 24;
            ClipPath op = new ClipPath(id, regionOp);
            operations.add(op);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "ClipPath";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 38;
        }

        public void apply(WireBuffer buffer, int id) {
            buffer.start(38);
            buffer.writeInt(id);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.clipPath(this.mId, this.mRegionOp);
    }
}
