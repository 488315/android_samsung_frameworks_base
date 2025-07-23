package com.android.internal.widget.remotecompose.core.operations.layout;

import android.app.slice.Slice;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ImpulseProcess extends PaintOperation implements VariableSupport, Container, Serializable {
    private static final String CLASS_NAME = "ImpulseProcess";
    private static final int OP_CODE = 165;
    public ArrayList<Operation> mList = new ArrayList<>();

    public int estimateIterations() {
        return 1;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof VariableSupport) {
                ((VariableSupport) obj).registerListening(remoteContext);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof VariableSupport) {
                ((VariableSupport) obj).updateVariables(remoteContext);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ImpulseProcess\n");
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
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof VariableSupport) && next.isDirty()) {
                ((VariableSupport) next).updateVariables(paintContext.getContext());
            }
            context.incrementOpCount();
            next.apply(paintContext.getContext());
        }
    }

    public static String name() {
        return "Loop";
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(165);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ImpulseProcess());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Operations", 165, name()).description("Impulse Process that runs a list of operations");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add(Slice.HINT_LIST, this.mList);
    }
}
