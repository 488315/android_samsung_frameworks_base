package com.android.internal.widget.remotecompose.core.operations.layout;

import android.app.slice.Slice;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class LoopOperation extends PaintOperation implements Container, VariableSupport, Serializable {
    private static final String CLASS_NAME = "LoopOperation";
    private static final int OP_CODE = 215;
    float mFrom;
    float mFromOut;
    int mIndexVariableId;
    public ArrayList<Operation> mList = new ArrayList<>();
    float mStep;
    float mStepOut;
    float mUntil;
    float mUntilOut;

    public LoopOperation(int i, int i2) {
        this.mUntil = i;
        this.mIndexVariableId = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mUntil)) {
            remoteContext.listensTo(Utils.idFromNan(this.mUntil), this);
        }
        if (Float.isNaN(this.mFrom)) {
            remoteContext.listensTo(Utils.idFromNan(this.mFrom), this);
        }
        if (Float.isNaN(this.mStep)) {
            remoteContext.listensTo(Utils.idFromNan(this.mStep), this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mUntilOut = Float.isNaN(this.mUntil) ? remoteContext.getFloat(Utils.idFromNan(this.mUntil)) : this.mUntil;
        this.mFromOut = Float.isNaN(this.mFrom) ? remoteContext.getFloat(Utils.idFromNan(this.mFrom)) : this.mFrom;
        this.mStepOut = Float.isNaN(this.mStep) ? remoteContext.getFloat(Utils.idFromNan(this.mStep)) : this.mStep;
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof VariableSupport) && next.isDirty()) {
                ((VariableSupport) next).updateVariables(remoteContext);
            }
        }
    }

    public LoopOperation(int i, float f, float f2, float f3) {
        this.mUntil = f3;
        this.mFrom = f;
        this.mStep = f2;
        this.mIndexVariableId = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mIndexVariableId, this.mFrom, this.mStep, this.mUntil);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LoopOperation\n");
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            sb.append("  ");
            sb.append(next);
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        RemoteContext context = paintContext.getContext();
        if (this.mIndexVariableId == 0) {
            float f = this.mFromOut;
            while (f < this.mUntilOut) {
                Iterator<Operation> it = this.mList.iterator();
                while (it.hasNext()) {
                    Operation next = it.next();
                    context.incrementOpCount();
                    next.apply(paintContext.getContext());
                }
                f += this.mStepOut;
            }
            return;
        }
        float f2 = this.mFromOut;
        while (f2 < this.mUntilOut) {
            paintContext.getContext().loadFloat(this.mIndexVariableId, f2);
            Iterator<Operation> it2 = this.mList.iterator();
            while (it2.hasNext()) {
                Operation next2 = it2.next();
                if ((next2 instanceof VariableSupport) && next2.isDirty()) {
                    ((VariableSupport) next2).updateVariables(paintContext.getContext());
                }
                context.incrementOpCount();
                next2.apply(paintContext.getContext());
            }
            f2 += this.mStepOut;
        }
    }

    public static String name() {
        return "Loop";
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3) {
        wireBuffer.start(215);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new LoopOperation(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Operations", 215, name()).description("Loop. This operation execute a list of action in a loop").field(0, "id", "if not 0 write value").field(1, "from", "values starts at").field(1, "step", "value step").field(1, "until", "stops less than or equal");
    }

    public int estimateIterations() {
        if (Float.isNaN(this.mUntil) || Float.isNaN(this.mFrom) || Float.isNaN(this.mStep)) {
            return 10;
        }
        return (int) (((this.mUntil - this.mFrom) / this.mStep) + 0.5f);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("indexVariableId", Integer.valueOf(this.mIndexVariableId)).add("until", this.mUntil, this.mUntilOut).add("from", this.mFrom, this.mFromOut).add("step", this.mStep, this.mStepOut).add(Slice.HINT_LIST, this.mList);
    }
}
