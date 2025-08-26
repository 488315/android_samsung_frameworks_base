package android.renderscript;

import android.content.res.Resources;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Deprecated
/* loaded from: classes3.dex */
public class Program extends BaseObj {
    static final int MAX_CONSTANT = 8;
    static final int MAX_INPUT = 8;
    static final int MAX_OUTPUT = 8;
    static final int MAX_TEXTURE = 8;
    Type[] mConstants;
    Element[] mInputs;
    Element[] mOutputs;
    String mShader;
    int mTextureCount;
    String[] mTextureNames;
    TextureType[] mTextures;

    public enum TextureType {
        TEXTURE_2D(0),
        TEXTURE_CUBE(1);

        int mID;

        TextureType(int i) {
            this.mID = i;
        }
    }

    enum ProgramParam {
        INPUT(0),
        OUTPUT(1),
        CONSTANT(2),
        TEXTURE_TYPE(3);

        int mID;

        ProgramParam(int i) {
            this.mID = i;
        }
    }

    Program(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public int getConstantCount() {
        Type[] typeArr = this.mConstants;
        if (typeArr != null) {
            return typeArr.length;
        }
        return 0;
    }

    public Type getConstant(int i) {
        if (i >= 0) {
            Type[] typeArr = this.mConstants;
            if (i < typeArr.length) {
                return typeArr[i];
            }
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public int getTextureCount() {
        return this.mTextureCount;
    }

    public TextureType getTextureType(int i) {
        if (i < 0 || i >= this.mTextureCount) {
            throw new IllegalArgumentException("Slot ID out of range.");
        }
        return this.mTextures[i];
    }

    public String getTextureName(int i) {
        if (i < 0 || i >= this.mTextureCount) {
            throw new IllegalArgumentException("Slot ID out of range.");
        }
        return this.mTextureNames[i];
    }

    public void bindConstants(Allocation allocation, int i) throws Throwable {
        if (i < 0 || i >= this.mConstants.length) {
            throw new IllegalArgumentException("Slot ID out of range.");
        }
        if (allocation != null && allocation.getType().getID(this.mRS) != this.mConstants[i].getID(this.mRS)) {
            throw new IllegalArgumentException("Allocation type does not match slot type.");
        }
        this.mRS.nProgramBindConstants(getID(this.mRS), i, allocation != null ? allocation.getID(this.mRS) : 0L);
    }

    public void bindTexture(Allocation allocation, int i) throws Throwable {
        this.mRS.validate();
        if (i < 0 || i >= this.mTextureCount) {
            throw new IllegalArgumentException("Slot ID out of range.");
        }
        if (allocation != null && allocation.getType().hasFaces() && this.mTextures[i] != TextureType.TEXTURE_CUBE) {
            throw new IllegalArgumentException("Cannot bind cubemap to 2d texture slot");
        }
        this.mRS.nProgramBindTexture(getID(this.mRS), i, allocation != null ? allocation.getID(this.mRS) : 0L);
    }

    public void bindSampler(Sampler sampler, int i) throws Throwable {
        this.mRS.validate();
        if (i < 0 || i >= this.mTextureCount) {
            throw new IllegalArgumentException("Slot ID out of range.");
        }
        this.mRS.nProgramBindSampler(getID(this.mRS), i, sampler != null ? sampler.getID(this.mRS) : 0L);
    }

    public static class BaseProgramBuilder {
        RenderScript mRS;
        String mShader;
        Type[] mTextures;
        Element[] mInputs = new Element[8];
        Element[] mOutputs = new Element[8];
        Type[] mConstants = new Type[8];
        int mInputCount = 0;
        int mOutputCount = 0;
        int mConstantCount = 0;
        int mTextureCount = 0;
        TextureType[] mTextureTypes = new TextureType[8];
        String[] mTextureNames = new String[8];

        protected BaseProgramBuilder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public BaseProgramBuilder setShader(String str) {
            this.mShader = str;
            return this;
        }

        public BaseProgramBuilder setShader(Resources resources, int i) throws Resources.NotFoundException, IOException {
            InputStream inputStreamOpenRawResource = resources.openRawResource(i);
            try {
                try {
                    byte[] bArr = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int length = bArr.length - i2;
                        if (length == 0) {
                            int length2 = bArr.length * 2;
                            byte[] bArr2 = new byte[length2];
                            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                            length = length2 - i2;
                            bArr = bArr2;
                        }
                        int i3 = inputStreamOpenRawResource.read(bArr, i2, length);
                        if (i3 <= 0) {
                            try {
                                this.mShader = new String(bArr, 0, i2, "UTF-8");
                                return this;
                            } catch (UnsupportedEncodingException unused) {
                                Log.e("RenderScript shader creation", "Could not decode shader string");
                                return this;
                            }
                        }
                        i2 += i3;
                    }
                } finally {
                    inputStreamOpenRawResource.close();
                }
            } catch (IOException unused2) {
                throw new Resources.NotFoundException();
            }
        }

        public int getCurrentConstantIndex() {
            return this.mConstantCount - 1;
        }

        public int getCurrentTextureIndex() {
            return this.mTextureCount - 1;
        }

        public BaseProgramBuilder addConstant(Type type) throws IllegalStateException {
            if (this.mConstantCount >= 8) {
                throw new RSIllegalArgumentException("Max input count exceeded.");
            }
            if (type.getElement().isComplex()) {
                throw new RSIllegalArgumentException("Complex elements not allowed.");
            }
            Type[] typeArr = this.mConstants;
            int i = this.mConstantCount;
            typeArr[i] = type;
            this.mConstantCount = i + 1;
            return this;
        }

        public BaseProgramBuilder addTexture(TextureType textureType) throws IllegalArgumentException {
            addTexture(textureType, "Tex" + this.mTextureCount);
            return this;
        }

        public BaseProgramBuilder addTexture(TextureType textureType, String str) throws IllegalArgumentException {
            int i = this.mTextureCount;
            if (i >= 8) {
                throw new IllegalArgumentException("Max texture count exceeded.");
            }
            this.mTextureTypes[i] = textureType;
            this.mTextureNames[i] = str;
            this.mTextureCount = i + 1;
            return this;
        }

        protected void initProgram(Program program) {
            program.mInputs = new Element[this.mInputCount];
            System.arraycopy(this.mInputs, 0, program.mInputs, 0, this.mInputCount);
            program.mOutputs = new Element[this.mOutputCount];
            System.arraycopy(this.mOutputs, 0, program.mOutputs, 0, this.mOutputCount);
            program.mConstants = new Type[this.mConstantCount];
            System.arraycopy(this.mConstants, 0, program.mConstants, 0, this.mConstantCount);
            program.mTextureCount = this.mTextureCount;
            program.mTextures = new TextureType[this.mTextureCount];
            System.arraycopy(this.mTextureTypes, 0, program.mTextures, 0, this.mTextureCount);
            program.mTextureNames = new String[this.mTextureCount];
            System.arraycopy(this.mTextureNames, 0, program.mTextureNames, 0, this.mTextureCount);
        }
    }
}
