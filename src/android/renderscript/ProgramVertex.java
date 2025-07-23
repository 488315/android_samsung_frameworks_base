package android.renderscript;

import android.renderscript.Program;

@Deprecated
/* loaded from: classes3.dex */
public class ProgramVertex extends Program {
    ProgramVertex(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public int getInputCount() {
        if (this.mInputs != null) {
            return this.mInputs.length;
        }
        return 0;
    }

    public Element getInput(int i) {
        if (i < 0 || i >= this.mInputs.length) {
            throw new IllegalArgumentException("Slot ID out of range.");
        }
        return this.mInputs[i];
    }

    public static class Builder extends Program.BaseProgramBuilder {
        public Builder(RenderScript renderScript) {
            super(renderScript);
        }

        public Builder addInput(Element element) throws IllegalStateException {
            if (this.mInputCount >= 8) {
                throw new RSIllegalArgumentException("Max input count exceeded.");
            }
            if (element.isComplex()) {
                throw new RSIllegalArgumentException("Complex elements not allowed.");
            }
            Element[] elementArr = this.mInputs;
            int i = this.mInputCount;
            this.mInputCount = i + 1;
            elementArr[i] = element;
            return this;
        }

        public ProgramVertex create() {
            this.mRS.validate();
            long[] jArr = new long[(this.mInputCount + this.mOutputCount + this.mConstantCount + this.mTextureCount) * 2];
            String[] strArr = new String[this.mTextureCount];
            int i = 0;
            for (int i2 = 0; i2 < this.mInputCount; i2++) {
                int i3 = i + 1;
                jArr[i] = Program.ProgramParam.INPUT.mID;
                i += 2;
                jArr[i3] = this.mInputs[i2].getID(this.mRS);
            }
            for (int i4 = 0; i4 < this.mOutputCount; i4++) {
                int i5 = i + 1;
                jArr[i] = Program.ProgramParam.OUTPUT.mID;
                i += 2;
                jArr[i5] = this.mOutputs[i4].getID(this.mRS);
            }
            for (int i6 = 0; i6 < this.mConstantCount; i6++) {
                int i7 = i + 1;
                jArr[i] = Program.ProgramParam.CONSTANT.mID;
                i += 2;
                jArr[i7] = this.mConstants[i6].getID(this.mRS);
            }
            for (int i8 = 0; i8 < this.mTextureCount; i8++) {
                int i9 = i + 1;
                jArr[i] = Program.ProgramParam.TEXTURE_TYPE.mID;
                i += 2;
                jArr[i9] = this.mTextureTypes[i8].mID;
                strArr[i8] = this.mTextureNames[i8];
            }
            ProgramVertex programVertex = new ProgramVertex(this.mRS.nProgramVertexCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programVertex);
            return programVertex;
        }
    }
}
