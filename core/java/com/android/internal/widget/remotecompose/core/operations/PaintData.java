package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;

import java.util.List;

public class PaintData extends PaintOperation implements VariableSupport {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public PaintBundle mPaintData = new PaintBundle();

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        this.mPaintData.updateVariables(context);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        this.mPaintData.registerVars(context, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mPaintData);
    }

    public String toString() {
        return "PaintData \"" + this.mPaintData + "\"";
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "TextData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 40;
        }

        public void apply(WireBuffer buffer, PaintBundle paintBundle) {
            buffer.start(40);
            paintBundle.writeBundle(buffer);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            PaintData data = new PaintData();
            data.mPaintData.readBundle(buffer);
            operations.add(data);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation,
              // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.applyPaint(this.mPaintData);
    }
}
