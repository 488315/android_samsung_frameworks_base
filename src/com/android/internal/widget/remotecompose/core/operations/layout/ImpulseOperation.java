package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ImpulseOperation extends PaintOperation implements VariableSupport, Container {
    private static final String CLASS_NAME = "ImpulseOperation";
    private static final int OP_CODE = 164;
    private float mDuration;
    int mIndexVariableId;
    private boolean mInitialPass = true;
    public ArrayList<Operation> mList = new ArrayList<>();
    private float mOutDuration;
    private float mOutStartAt;
    private ImpulseProcess mProcess;
    private float mStartAt;

    public ImpulseOperation(float f, float f2) {
        this.mDuration = f;
        this.mStartAt = f2;
        this.mOutStartAt = f2;
        this.mOutDuration = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (this.mProcess == null) {
            System.out.println(".....");
            Operation operation = this.mList.get(r0.size() - 1);
            if (operation instanceof ImpulseProcess) {
                this.mProcess = (ImpulseProcess) operation;
                this.mList.remove(operation);
            }
        }
        if (Float.isNaN(this.mStartAt)) {
            remoteContext.listensTo(Utils.idFromNan(this.mStartAt), this);
        }
        if (Float.isNaN(this.mDuration)) {
            remoteContext.listensTo(Utils.idFromNan(this.mDuration), this);
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof VariableSupport) {
                ((VariableSupport) obj).registerListening(remoteContext);
            }
        }
        ImpulseProcess impulseProcess = this.mProcess;
        if (impulseProcess != null) {
            impulseProcess.registerListening(remoteContext);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutDuration = Float.isNaN(this.mDuration) ? remoteContext.getFloat(Utils.idFromNan(this.mDuration)) : this.mDuration;
        this.mOutStartAt = Float.isNaN(this.mStartAt) ? remoteContext.getFloat(Utils.idFromNan(this.mStartAt)) : this.mStartAt;
        ImpulseProcess impulseProcess = this.mProcess;
        if (impulseProcess != null) {
            impulseProcess.updateVariables(remoteContext);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mDuration, this.mStartAt);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ImpulseOperation\n");
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next();
            sb.append("  startAt: ");
            sb.append(this.mStartAt);
            sb.append(" duration: ");
            sb.append(this.mDuration);
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
        if (context.getAnimationTime() < this.mOutStartAt + this.mOutDuration) {
            if (this.mInitialPass) {
                Iterator<Operation> it = this.mList.iterator();
                while (it.hasNext()) {
                    Operation next = it.next();
                    if ((next instanceof VariableSupport) && next.isDirty()) {
                        ((VariableSupport) next).updateVariables(paintContext.getContext());
                    }
                    context.incrementOpCount();
                    next.apply(paintContext.getContext());
                }
                this.mInitialPass = false;
                return;
            }
            context.incrementOpCount();
            ImpulseProcess impulseProcess = this.mProcess;
            if (impulseProcess != null) {
                impulseProcess.paint(paintContext);
                return;
            }
            return;
        }
        this.mInitialPass = true;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2) {
        wireBuffer.start(164);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ImpulseOperation(wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Operations", 164, name()).description("Impulse Operation. This operation execute a list of action for a fixed duration").field(1, "duration", "How long to last").field(1, "startAt", "value step");
    }

    public int estimateIterations() {
        if (Float.isNaN(this.mDuration)) {
            return 10;
        }
        return (int) (this.mDuration * 60.0f);
    }

    public void setProcess(ImpulseProcess impulseProcess) {
        this.mProcess = impulseProcess;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("duration", this.mDuration, this.mOutDuration).add("startAt", this.mStartAt, this.mOutStartAt);
    }
}
