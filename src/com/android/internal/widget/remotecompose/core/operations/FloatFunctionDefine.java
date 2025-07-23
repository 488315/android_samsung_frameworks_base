package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Container;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class FloatFunctionDefine extends Operation implements VariableSupport, Container {
    private static final String CLASS_NAME = "FunctionDefine";
    private static final int OP_CODE = 168;
    private final int[] mFloatVarId;
    private final int mId;
    private ArrayList<Operation> mList = new ArrayList<>();
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
    }

    public FloatFunctionDefine(int i, int[] iArr) {
        this.mId = i;
        this.mFloatVarId = iArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        remoteContext.putObject(this.mId, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mFloatVarId);
    }

    public String toString() {
        String str = "FloatFunctionDefine[" + Utils.idString(this.mId) + "] (";
        for (int i = 0; i < this.mFloatVarId.length; i++) {
            str = str + NavigationBarInflaterView.SIZE_MOD_START + this.mFloatVarId[i] + "] ";
        }
        String str2 = str + NavigationBarInflaterView.KEY_CODE_END;
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            str2 = str2 + " \n  " + it.next().toString();
        }
        return str2;
    }

    public static void apply(WireBuffer wireBuffer, int i, int[] iArr) {
        wireBuffer.start(168);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(iArr.length);
        for (int i2 : iArr) {
            wireBuffer.writeInt(i2);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        int[] iArr = new int[readInt2];
        for (int i = 0; i < readInt2; i++) {
            iArr[i] = wireBuffer.readInt();
        }
        list.add(new FloatFunctionDefine(readInt, iArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 168, CLASS_NAME).description("Define a function").field(0, "id", "The reference of the function").field(0, "varLen", "number of arguments to the function").field(10, "id", "varLen", "id equations");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    public int[] getArgs() {
        return this.mFloatVarId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void execute(RemoteContext remoteContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof VariableSupport) {
                ((VariableSupport) next).updateVariables(remoteContext);
            }
            remoteContext.incrementOpCount();
            next.apply(remoteContext);
        }
    }
}
