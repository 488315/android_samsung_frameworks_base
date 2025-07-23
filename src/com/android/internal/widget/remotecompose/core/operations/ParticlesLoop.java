package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Container;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import com.android.internal.widget.remotecompose.core.operations.utilities.NanMap;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ParticlesLoop extends PaintOperation implements VariableSupport, Container {
    private static final String CLASS_NAME = "ParticlesLoop";
    private static final int MAX_EQU_LENGTH = 32;
    private static final int MAX_FLOAT_ARRAY = 2000;
    private static final int OP_CODE = 163;
    private final float[][] mEquations;
    private final int mId;
    private final float[][] mOutEquations;
    private final float[] mOutRestart;
    private float[][] mParticles;
    ParticlesCreate mParticlesSource;
    private final float[] mRestart;
    private int[] mVarId;
    private ArrayList<Operation> mList = new ArrayList<>();
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    public ParticlesLoop(int i, float[] fArr, float[][] fArr2) {
        this.mId = i;
        this.mRestart = fArr;
        if (fArr != null) {
            float[] fArr3 = new float[fArr.length];
            this.mOutRestart = fArr3;
            System.arraycopy(fArr, 0, fArr3, 0, fArr.length);
        } else {
            this.mOutRestart = null;
        }
        this.mEquations = fArr2;
        this.mOutEquations = new float[fArr2.length][];
        for (int i2 = 0; i2 < fArr2.length; i2++) {
            float[][] fArr4 = this.mOutEquations;
            float[] fArr5 = new float[fArr2[i2].length];
            fArr4[i2] = fArr5;
            float[] fArr6 = fArr2[i2];
            System.arraycopy(fArr6, 0, fArr5, 0, fArr6.length);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        if (this.mOutRestart != null) {
            int i = 0;
            while (true) {
                float[] fArr = this.mRestart;
                if (i >= fArr.length) {
                    break;
                }
                float f = fArr[i];
                float[] fArr2 = this.mOutRestart;
                if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                    f = remoteContext.getFloat(Utils.idFromNan(f));
                }
                fArr2[i] = f;
                i++;
            }
        }
        int i2 = 0;
        while (true) {
            float[][] fArr3 = this.mEquations;
            if (i2 >= fArr3.length) {
                return;
            }
            float[] fArr4 = fArr3[i2];
            for (int i3 = 0; i3 < fArr4.length; i3++) {
                float f2 = fArr4[i3];
                float[] fArr5 = this.mOutEquations[i2];
                if (Float.isNaN(f2) && !AnimatedFloatExpression.isMathOperator(f2) && !NanMap.isDataVariable(f2)) {
                    f2 = remoteContext.getFloat(Utils.idFromNan(f2));
                }
                fArr5[i3] = f2;
            }
            i2++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        ParticlesCreate particlesCreate = (ParticlesCreate) remoteContext.getObject(this.mId);
        this.mParticlesSource = particlesCreate;
        this.mParticles = particlesCreate.getParticles();
        this.mVarId = this.mParticlesSource.getVariableIds();
        if (this.mRestart != null) {
            int i = 0;
            while (true) {
                float[] fArr = this.mRestart;
                if (i >= fArr.length) {
                    break;
                }
                float f = fArr[i];
                if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                    remoteContext.listensTo(Utils.idFromNan(f), this);
                }
                i++;
            }
        }
        int i2 = 0;
        while (true) {
            float[][] fArr2 = this.mEquations;
            if (i2 >= fArr2.length) {
                return;
            }
            for (float f2 : fArr2[i2]) {
                if (Float.isNaN(f2) && !AnimatedFloatExpression.isMathOperator(f2) && !NanMap.isDataVariable(f2)) {
                    remoteContext.listensTo(Utils.idFromNan(f2), this);
                }
            }
            i2++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mRestart, this.mEquations);
    }

    public String toString() {
        return "ParticlesLoop[" + Utils.idString(this.mId) + "] ";
    }

    public static void apply(WireBuffer wireBuffer, int i, float[] fArr, float[][] fArr2) {
        wireBuffer.start(163);
        wireBuffer.writeInt(i);
        if (fArr != null) {
            wireBuffer.writeInt(fArr.length);
            for (float f : fArr) {
                wireBuffer.writeFloat(f);
            }
        } else {
            wireBuffer.writeInt(0);
        }
        wireBuffer.writeInt(fArr2.length);
        for (int i2 = 0; i2 < fArr2.length; i2++) {
            wireBuffer.writeInt(fArr2[i2].length);
            int i3 = 0;
            while (true) {
                float[] fArr3 = fArr2[i2];
                if (i3 < fArr3.length) {
                    wireBuffer.writeFloat(fArr3[i3]);
                    i3++;
                }
            }
        }
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
        int readInt3 = wireBuffer.readInt();
        if (readInt3 > 2000) {
            throw new RuntimeException(readInt3 + " map entries more than max = 2000");
        }
        float[][] fArr2 = new float[readInt3][];
        for (int i2 = 0; i2 < readInt3; i2++) {
            int readInt4 = wireBuffer.readInt();
            if (readInt4 > 32) {
                throw new RuntimeException(readInt4 + " map entries more than max = 2000");
            }
            fArr2[i2] = new float[readInt4];
            int i3 = 0;
            while (true) {
                float[] fArr3 = fArr2[i2];
                if (i3 < fArr3.length) {
                    fArr3[i3] = wireBuffer.readFloat();
                    i3++;
                }
            }
        }
        list.add(new ParticlesLoop(readInt, fArr, fArr2));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 163, CLASS_NAME).description("This evolves the particles & recycles them").field(0, "id", "id of particle system").field(0, "recycleLen", "the number of floats in restart equeation if 0 no restart").field(10, "values", "recycleLen", "array of floats").field(0, "varLen", "the number of equations to follow").field(0, "equLen", "the number of equations to follow").field(10, "values", "equLen", "floats for the equation");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        RemoteContext context = paintContext.getContext();
        for (int i = 0; i < this.mParticles.length; i++) {
            int i2 = 0;
            while (true) {
                float[] fArr = this.mParticles[i];
                if (i2 >= fArr.length) {
                    break;
                }
                context.loadFloat(this.mVarId[i2], fArr[i2]);
                updateVariables(context);
                i2++;
            }
            int i3 = 0;
            while (true) {
                float[] fArr2 = this.mParticles[i];
                if (i3 >= fArr2.length) {
                    break;
                }
                AnimatedFloatExpression animatedFloatExpression = this.mExp;
                float[] fArr3 = this.mOutEquations[i3];
                fArr2[i3] = animatedFloatExpression.eval(fArr3, fArr3.length, new float[0]);
                context.loadFloat(this.mVarId[i3], this.mParticles[i][i3]);
                i3++;
            }
            if (this.mOutRestart != null) {
                int i4 = 0;
                while (true) {
                    float[] fArr4 = this.mRestart;
                    if (i4 >= fArr4.length) {
                        break;
                    }
                    float f = fArr4[i4];
                    float[] fArr5 = this.mOutRestart;
                    if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                        f = context.getFloat(Utils.idFromNan(f));
                    }
                    fArr5[i4] = f;
                    i4++;
                }
                AnimatedFloatExpression animatedFloatExpression2 = this.mExp;
                float[] fArr6 = this.mOutRestart;
                if (animatedFloatExpression2.eval(fArr6, fArr6.length, new float[0]) > 0.0f) {
                    this.mParticlesSource.initializeParticle(i);
                }
            }
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Operation next = it.next();
                if (next instanceof VariableSupport) {
                    ((VariableSupport) next).updateVariables(paintContext.getContext());
                }
                context.incrementOpCount();
                next.apply(paintContext.getContext());
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId));
    }
}
