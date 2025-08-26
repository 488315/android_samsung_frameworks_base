package android.renderscript;

import android.hardware.gnss.GnssSignalType;
import android.renderscript.Element;
import android.renderscript.Program;
import android.renderscript.Type;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

@Deprecated
/* loaded from: classes3.dex */
public class ProgramVertexFixedFunction extends ProgramVertex {
    ProgramVertexFixedFunction(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public void bindConstants(Constants constants) {
        this.mRS.validate();
        bindConstants(constants.getAllocation(), 0);
    }

    static class InternalBuilder extends Program.BaseProgramBuilder {
        public InternalBuilder(RenderScript renderScript) {
            super(renderScript);
        }

        public InternalBuilder addInput(Element element) throws IllegalStateException {
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

        public ProgramVertexFixedFunction create() {
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
            ProgramVertexFixedFunction programVertexFixedFunction = new ProgramVertexFixedFunction(this.mRS.nProgramVertexCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programVertexFixedFunction);
            return programVertexFixedFunction;
        }
    }

    public static class Builder {
        RenderScript mRS;
        String mShader;
        boolean mTextureMatrixEnable;

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public Builder setTextureMatrixEnable(boolean z) {
            this.mTextureMatrixEnable = z;
            return this;
        }

        static Type getConstantInputType(RenderScript renderScript) {
            Element.Builder builder = new Element.Builder(renderScript);
            builder.add(Element.MATRIX4X4(renderScript), "MV");
            builder.add(Element.MATRIX4X4(renderScript), GnssSignalType.CODE_TYPE_P);
            builder.add(Element.MATRIX4X4(renderScript), "TexMatrix");
            builder.add(Element.MATRIX4X4(renderScript), "MVP");
            Type.Builder builder2 = new Type.Builder(renderScript, builder.create());
            builder2.setX(1);
            return builder2.create();
        }

        private void buildShaderString() {
            this.mShader = "//rs_shader_internal\n";
            this.mShader += "varying vec4 varColor;\n";
            this.mShader += "varying vec2 varTex0;\n";
            this.mShader += "void main() {\n";
            this.mShader += "  gl_Position = UNI_MVP * ATTRIB_position;\n";
            this.mShader += "  gl_PointSize = 1.0;\n";
            this.mShader += "  varColor = ATTRIB_color;\n";
            if (this.mTextureMatrixEnable) {
                this.mShader += "  varTex0 = (UNI_TexMatrix * vec4(ATTRIB_texture0, 0.0, 1.0)).xy;\n";
            } else {
                this.mShader += "  varTex0 = ATTRIB_texture0;\n";
            }
            this.mShader += ShaderAssembler.SHADER_MAIN_CODE_END;
        }

        public ProgramVertexFixedFunction create() throws IllegalStateException {
            buildShaderString();
            InternalBuilder internalBuilder = new InternalBuilder(this.mRS);
            internalBuilder.setShader(this.mShader);
            internalBuilder.addConstant(getConstantInputType(this.mRS));
            Element.Builder builder = new Element.Builder(this.mRS);
            builder.add(Element.F32_4(this.mRS), "position");
            builder.add(Element.F32_4(this.mRS), "color");
            builder.add(Element.F32_3(this.mRS), "normal");
            builder.add(Element.F32_2(this.mRS), "texture0");
            internalBuilder.addInput(builder.create());
            return internalBuilder.create();
        }
    }

    public static class Constants {
        static final int MODELVIEW_OFFSET = 0;
        static final int PROJECTION_OFFSET = 16;
        static final int TEXTURE_OFFSET = 32;
        Allocation mAlloc;
        private FieldPacker mIOBuffer;
        Matrix4f mModel;
        Matrix4f mProjection;
        Matrix4f mTexture;

        Allocation getAllocation() {
            return this.mAlloc;
        }

        public Constants(RenderScript renderScript) {
            Type constantInputType = Builder.getConstantInputType(renderScript);
            this.mAlloc = Allocation.createTyped(renderScript, constantInputType);
            this.mIOBuffer = new FieldPacker(constantInputType.getElement().getBytesSize() * constantInputType.getCount());
            this.mModel = new Matrix4f();
            this.mProjection = new Matrix4f();
            this.mTexture = new Matrix4f();
            setModelview(new Matrix4f());
            setProjection(new Matrix4f());
            setTexture(new Matrix4f());
        }

        public void destroy() {
            this.mAlloc.destroy();
            this.mAlloc = null;
        }

        private void addToBuffer(int i, Matrix4f matrix4f) {
            this.mIOBuffer.reset(i);
            for (int i2 = 0; i2 < 16; i2++) {
                this.mIOBuffer.addF32(matrix4f.mMat[i2]);
            }
            FieldPacker fieldPacker = this.mIOBuffer;
            fieldPacker.reset(fieldPacker.getData().length);
            this.mAlloc.setFromFieldPacker(0, this.mIOBuffer);
        }

        public void setModelview(Matrix4f matrix4f) {
            this.mModel.load(matrix4f);
            addToBuffer(0, matrix4f);
        }

        public void setProjection(Matrix4f matrix4f) {
            this.mProjection.load(matrix4f);
            addToBuffer(64, matrix4f);
        }

        public void setTexture(Matrix4f matrix4f) {
            this.mTexture.load(matrix4f);
            addToBuffer(128, matrix4f);
        }
    }
}
