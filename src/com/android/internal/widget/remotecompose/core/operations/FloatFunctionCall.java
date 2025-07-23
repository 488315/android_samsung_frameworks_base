package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import com.android.internal.widget.remotecompose.core.operations.utilities.NanMap;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class FloatFunctionCall extends PaintOperation implements VariableSupport {
    private static final String CLASS_NAME = "FunctionCall";
    private static final int OP_CODE = 166;
    private final float[] mArgs;
    FloatFunctionDefine mFunction;
    private final int mId;
    private final float[] mOutArgs;
    private ArrayList<Operation> mList = new ArrayList<>();
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();

    public FloatFunctionCall(int i, float[] fArr) {
        this.mId = i;
        this.mArgs = fArr;
        if (fArr != null) {
            float[] fArr2 = new float[fArr.length];
            this.mOutArgs = fArr2;
            System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
            return;
        }
        this.mOutArgs = null;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        if (this.mOutArgs == null) {
            return;
        }
        int i = 0;
        while (true) {
            float[] fArr = this.mArgs;
            if (i >= fArr.length) {
                return;
            }
            float f = fArr[i];
            float[] fArr2 = this.mOutArgs;
            if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                f = remoteContext.getFloat(Utils.idFromNan(f));
            }
            fArr2[i] = f;
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        this.mFunction = (FloatFunctionDefine) remoteContext.getObject(this.mId);
        if (this.mArgs == null) {
            return;
        }
        int i = 0;
        while (true) {
            float[] fArr = this.mArgs;
            if (i >= fArr.length) {
                return;
            }
            float f = fArr[i];
            if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                remoteContext.listensTo(Utils.idFromNan(f), this);
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mArgs);
    }

    public String toString() {
        String str = "callFunction[" + Utils.idString(this.mId) + "] ";
        int i = 0;
        while (i < this.mArgs.length) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(i == 0 ? "" : " ,");
            sb.append(Utils.floatToString(this.mArgs[i], this.mOutArgs[i]));
            str = sb.toString();
            i++;
        }
        return str;
    }

    public static void apply(WireBuffer wireBuffer, int i, float[] fArr) {
        wireBuffer.start(166);
        wireBuffer.writeInt(i);
        if (fArr != null) {
            wireBuffer.writeInt(fArr.length);
            for (float f : fArr) {
                wireBuffer.writeFloat(f);
            }
            return;
        }
        wireBuffer.writeInt(0);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        float[] fArr;
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        if (readInt2 > 0) {
            fArr = new float[readInt2];
            for (int i = 0; i < readInt2; i++) {
                fArr[i] = wireBuffer.readFloat();
            }
        } else {
            fArr = null;
        }
        list.add(new FloatFunctionCall(readInt, fArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 166, CLASS_NAME).description("Command to call the function").field(0, "id", "id of function to call").field(0, "argLen", "the number of Arguments").field(10, "values", "argLen", "array of float arguments");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        RemoteContext context = paintContext.getContext();
        int[] args = this.mFunction.getArgs();
        int i = 0;
        while (true) {
            float[] fArr = this.mOutArgs;
            if (i < fArr.length) {
                context.loadFloat(args[i], fArr[i]);
                updateVariables(context);
                i++;
            } else {
                this.mFunction.execute(context);
                return;
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("args", Collections.singletonList(this.mArgs)).add("outArgs", Collections.singletonList(this.mOutArgs));
    }
}
