package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class ParticlesCreate extends PaintOperation implements VariableSupport {
    private static final String CLASS_NAME = "ParticlesCreate";
    private static final int MAX_EQU_LENGTH = 32;
    private static final int MAX_FLOAT_ARRAY = 2000;
    private static final int OP_CODE = 161;
    private final float[][] mEquations;
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();
    private final int mId;
    private final int[] mIndexeVars;
    private final float[][] mOutEquations;
    private final int mParticleCount;
    private final float[][] mParticles;
    private final int[] mVarId;

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
    }

    public ParticlesCreate(int i, int[] iArr, float[][] fArr, int i2) {
        this.mId = i;
        this.mVarId = iArr;
        this.mEquations = fArr;
        this.mParticleCount = i2;
        this.mOutEquations = new float[fArr.length][];
        for (int i3 = 0; i3 < fArr.length; i3++) {
            float[][] fArr2 = this.mOutEquations;
            float[] fArr3 = new float[fArr[i3].length];
            fArr2[i3] = fArr3;
            float[] fArr4 = fArr[i3];
            System.arraycopy(fArr4, 0, fArr3, 0, fArr4.length);
        }
        this.mParticles = (float[][]) Array.newInstance((Class<?>) Float.TYPE, i2, iArr.length);
        int[] iArr2 = new int[20];
        int floatToRawIntBits = Float.floatToRawIntBits(AnimatedFloatExpression.VAR1);
        int i4 = 0;
        for (int i5 = 0; i5 < this.mEquations.length; i5++) {
            int i6 = 0;
            while (true) {
                float[] fArr5 = this.mEquations[i5];
                if (i6 < fArr5.length) {
                    if (Float.isNaN(fArr5[i6]) && Float.floatToRawIntBits(this.mEquations[i5][i6]) == floatToRawIntBits) {
                        iArr2[i4] = (this.mEquations.length * i5) + i6;
                        i4++;
                    }
                    i6++;
                }
            }
        }
        this.mIndexeVars = Arrays.copyOf(iArr2, i4);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        for (int i = 0; i < this.mEquations.length; i++) {
            int i2 = 0;
            while (true) {
                float[] fArr = this.mEquations[i];
                if (i2 < fArr.length) {
                    float f = fArr[i2];
                    float[] fArr2 = this.mOutEquations[i];
                    if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                        f = remoteContext.getFloat(Utils.idFromNan(f));
                    }
                    fArr2[i2] = f;
                    i2++;
                }
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        remoteContext.putObject(this.mId, this);
        int i = 0;
        while (true) {
            float[][] fArr = this.mEquations;
            if (i >= fArr.length) {
                return;
            }
            for (float f : fArr[i]) {
                if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                    remoteContext.listensTo(Utils.idFromNan(f), this);
                }
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mVarId, this.mEquations, this.mParticleCount);
    }

    public String toString() {
        String str = "ParticlesCreate[" + Utils.idString(this.mId) + "] ";
        for (int i = 0; i < this.mVarId.length; i++) {
            String str2 = str + NavigationBarInflaterView.SIZE_MOD_START + this.mVarId[i] + "] ";
            float[] fArr = this.mEquations[i];
            String[] strArr = new String[fArr.length];
            for (int i2 = 0; i2 < fArr.length; i2++) {
                if (Float.isNaN(fArr[i2])) {
                    strArr[i2] = NavigationBarInflaterView.SIZE_MOD_START + Utils.idStringFromNan(fArr[i2]) + NavigationBarInflaterView.SIZE_MOD_END;
                }
            }
            str = str2 + AnimatedFloatExpression.toString(fArr, strArr) + ShaderAssembler.NEWLINE;
        }
        return str;
    }

    public static void apply(WireBuffer wireBuffer, int i, int[] iArr, float[][] fArr, int i2) {
        wireBuffer.start(161);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(iArr.length);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            wireBuffer.writeInt(iArr[i3]);
            wireBuffer.writeInt(fArr[i3].length);
            int i4 = 0;
            while (true) {
                float[] fArr2 = fArr[i3];
                if (i4 < fArr2.length) {
                    wireBuffer.writeFloat(fArr2[i4]);
                    i4++;
                }
            }
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        int readInt3 = wireBuffer.readInt();
        if (readInt3 > 2000) {
            throw new RuntimeException(readInt3 + " map entries more than max = 2000");
        }
        int[] iArr = new int[readInt3];
        float[][] fArr = new float[readInt3][];
        for (int i = 0; i < readInt3; i++) {
            iArr[i] = wireBuffer.readInt();
            int readInt4 = wireBuffer.readInt();
            if (readInt4 > 32) {
                throw new RuntimeException(readInt4 + " map entries more than max = 2000");
            }
            fArr[i] = new float[readInt4];
            int i2 = 0;
            while (true) {
                float[] fArr2 = fArr[i];
                if (i2 < fArr2.length) {
                    fArr2[i2] = wireBuffer.readFloat();
                    i2++;
                }
            }
        }
        list.add(new ParticlesCreate(readInt, iArr, fArr, readInt2));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 161, CLASS_NAME).description("Creates a particle system").field(0, "id", "The reference of the particle system").field(0, "particleCount", "number of particles to create").field(0, "varLen", "number of variables asociate with the particles").field(10, "id", "varLen", "id followed by equations").field(0, "equLen", "length of the equation").field(10, "equation", "varLen * equLen", "float array equations");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        for (int i = 0; i < this.mParticles.length; i++) {
            initializeParticle(i);
        }
    }

    void initializeParticle(int i) {
        for (int i2 = 0; i2 < this.mParticles[i].length; i2++) {
            int i3 = 0;
            while (true) {
                int[] iArr = this.mIndexeVars;
                if (i3 < iArr.length) {
                    int i4 = iArr[i3];
                    float[][] fArr = this.mOutEquations;
                    fArr[i4 / fArr.length][i4 % fArr.length] = i;
                    i3++;
                }
            }
            float[] fArr2 = this.mParticles[i];
            AnimatedFloatExpression animatedFloatExpression = this.mExp;
            float[] fArr3 = this.mOutEquations[i2];
            fArr2[i2] = animatedFloatExpression.eval(fArr3, fArr3.length, new float[0]);
        }
    }

    public float[][] getParticles() {
        return this.mParticles;
    }

    public int[] getVariableIds() {
        return this.mVarId;
    }

    public float[][] getEquations() {
        return this.mOutEquations;
    }
}
