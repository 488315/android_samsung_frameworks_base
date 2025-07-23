package android.renderscript;

import android.renderscript.Element;
import android.renderscript.Program;
import android.renderscript.Type;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

@Deprecated
/* loaded from: classes3.dex */
public class ProgramFragmentFixedFunction extends ProgramFragment {
    ProgramFragmentFixedFunction(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    static class InternalBuilder extends Program.BaseProgramBuilder {
        public InternalBuilder(RenderScript renderScript) {
            super(renderScript);
        }

        public ProgramFragmentFixedFunction create() {
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
            ProgramFragmentFixedFunction programFragmentFixedFunction = new ProgramFragmentFixedFunction(this.mRS.nProgramFragmentCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programFragmentFixedFunction);
            return programFragmentFixedFunction;
        }
    }

    public static class Builder {
        public static final int MAX_TEXTURE = 2;
        int mNumTextures;
        RenderScript mRS;
        String mShader;
        boolean mVaryingColorEnable;
        Slot[] mSlots = new Slot[2];
        boolean mPointSpriteEnable = false;

        public enum EnvMode {
            REPLACE(1),
            MODULATE(2),
            DECAL(3);

            int mID;

            EnvMode(int i) {
                this.mID = i;
            }
        }

        public enum Format {
            ALPHA(1),
            LUMINANCE_ALPHA(2),
            RGB(3),
            RGBA(4);

            int mID;

            Format(int i) {
                this.mID = i;
            }
        }

        private class Slot {
            EnvMode env;
            Format format;

            Slot(Builder builder, EnvMode envMode, Format format) {
                this.env = envMode;
                this.format = format;
            }
        }

        private void buildShaderString() {
            this.mShader = "//rs_shader_internal\n";
            this.mShader += "varying lowp vec4 varColor;\n";
            this.mShader += "varying vec2 varTex0;\n";
            this.mShader += "void main() {\n";
            if (this.mVaryingColorEnable) {
                this.mShader += "  lowp vec4 col = varColor;\n";
            } else {
                this.mShader += "  lowp vec4 col = UNI_Color;\n";
            }
            if (this.mNumTextures != 0) {
                if (this.mPointSpriteEnable) {
                    this.mShader += "  vec2 t0 = gl_PointCoord;\n";
                } else {
                    this.mShader += "  vec2 t0 = varTex0.xy;\n";
                }
            }
            for (int i = 0; i < this.mNumTextures; i++) {
                int ordinal = this.mSlots[i].env.ordinal();
                if (ordinal == 0) {
                    int ordinal2 = this.mSlots[i].format.ordinal();
                    if (ordinal2 == 0) {
                        this.mShader += "  col.a = texture2D(UNI_Tex0, t0).a;\n";
                    } else if (ordinal2 == 1) {
                        this.mShader += "  col.rgba = texture2D(UNI_Tex0, t0).rgba;\n";
                    } else if (ordinal2 == 2) {
                        this.mShader += "  col.rgb = texture2D(UNI_Tex0, t0).rgb;\n";
                    } else if (ordinal2 == 3) {
                        this.mShader += "  col.rgba = texture2D(UNI_Tex0, t0).rgba;\n";
                    }
                } else if (ordinal == 1) {
                    int ordinal3 = this.mSlots[i].format.ordinal();
                    if (ordinal3 == 0) {
                        this.mShader += "  col.a *= texture2D(UNI_Tex0, t0).a;\n";
                    } else if (ordinal3 == 1) {
                        this.mShader += "  col.rgba *= texture2D(UNI_Tex0, t0).rgba;\n";
                    } else if (ordinal3 == 2) {
                        this.mShader += "  col.rgb *= texture2D(UNI_Tex0, t0).rgb;\n";
                    } else if (ordinal3 == 3) {
                        this.mShader += "  col.rgba *= texture2D(UNI_Tex0, t0).rgba;\n";
                    }
                } else if (ordinal == 2) {
                    this.mShader += "  col = texture2D(UNI_Tex0, t0);\n";
                }
            }
            this.mShader += "  gl_FragColor = col;\n";
            this.mShader += ShaderAssembler.SHADER_MAIN_CODE_END;
        }

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public Builder setTexture(EnvMode envMode, Format format, int i) throws IllegalArgumentException {
            if (i < 0 || i >= 2) {
                throw new IllegalArgumentException("MAX_TEXTURE exceeded.");
            }
            this.mSlots[i] = new Slot(this, envMode, format);
            return this;
        }

        public Builder setPointSpriteTexCoordinateReplacement(boolean z) {
            this.mPointSpriteEnable = z;
            return this;
        }

        public Builder setVaryingColor(boolean z) {
            this.mVaryingColorEnable = z;
            return this;
        }

        public ProgramFragmentFixedFunction create() {
            Type type;
            InternalBuilder internalBuilder = new InternalBuilder(this.mRS);
            this.mNumTextures = 0;
            for (int i = 0; i < 2; i++) {
                if (this.mSlots[i] != null) {
                    this.mNumTextures++;
                }
            }
            buildShaderString();
            internalBuilder.setShader(this.mShader);
            if (this.mVaryingColorEnable) {
                type = null;
            } else {
                Element.Builder builder = new Element.Builder(this.mRS);
                builder.add(Element.F32_4(this.mRS), "Color");
                Type.Builder builder2 = new Type.Builder(this.mRS, builder.create());
                builder2.setX(1);
                type = builder2.create();
                internalBuilder.addConstant(type);
            }
            for (int i2 = 0; i2 < this.mNumTextures; i2++) {
                internalBuilder.addTexture(Program.TextureType.TEXTURE_2D);
            }
            ProgramFragmentFixedFunction create = internalBuilder.create();
            create.mTextureCount = 2;
            if (!this.mVaryingColorEnable) {
                Allocation createTyped = Allocation.createTyped(this.mRS, type);
                FieldPacker fieldPacker = new FieldPacker(16);
                fieldPacker.addF32(new Float4(1.0f, 1.0f, 1.0f, 1.0f));
                createTyped.setFromFieldPacker(0, fieldPacker);
                create.bindConstants(createTyped, 0);
            }
            return create;
        }
    }
}
