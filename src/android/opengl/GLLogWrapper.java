package android.opengl;

import android.app.blob.XmlTags;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.CallLog;
import android.provider.Telephony;
import com.samsung.android.emergencymode.SemEmergencyConstants;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.zt.internal.KnoxZtInternalConst;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.IOException;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import javax.microedition.khronos.opengles.GL;

/* loaded from: classes3.dex */
class GLLogWrapper extends GLWrapperBase {
    private static final int FORMAT_FIXED = 2;
    private static final int FORMAT_FLOAT = 1;
    private static final int FORMAT_INT = 0;
    private int mArgCount;
    boolean mColorArrayEnabled;
    private PointerInfo mColorPointer;
    private Writer mLog;
    private boolean mLogArgumentNames;
    boolean mNormalArrayEnabled;
    private PointerInfo mNormalPointer;
    StringBuilder mStringBuilder;
    private PointerInfo mTexCoordPointer;
    boolean mTextureCoordArrayEnabled;
    boolean mVertexArrayEnabled;
    private PointerInfo mVertexPointer;

    private int getFogParamCount(int i) {
        switch (i) {
            case 2914:
            case 2915:
            case 2916:
            case 2917:
                return 1;
            case 2918:
                return 4;
            default:
                return 0;
        }
    }

    private int getIntegerStateFormat(int i) {
        switch (i) {
            case 35213:
            case 35214:
            case 35215:
                return 1;
            default:
                return 0;
        }
    }

    private int getLightModelParamCount(int i) {
        if (i != 2898) {
            return i != 2899 ? 0 : 4;
        }
        return 1;
    }

    private int getLightParamCount(int i) {
        switch (i) {
            case 4608:
            case 4609:
            case 4610:
            case 4611:
                return 4;
            case 4612:
                return 3;
            case 4613:
            case 4614:
            case 4615:
            case 4616:
            case 4617:
                return 1;
            default:
                return 0;
        }
    }

    private int getMaterialParamCount(int i) {
        switch (i) {
            case 4608:
            case 4609:
            case 4610:
                break;
            default:
                switch (i) {
                }
        }
        return 4;
    }

    private int getTextureEnvParamCount(int i) {
        if (i != 8704) {
            return i != 8705 ? 0 : 4;
        }
        return 1;
    }

    public GLLogWrapper(GL gl, Writer writer, boolean z) {
        super(gl);
        this.mColorPointer = new PointerInfo();
        this.mNormalPointer = new PointerInfo();
        this.mTexCoordPointer = new PointerInfo();
        this.mVertexPointer = new PointerInfo();
        this.mLog = writer;
        this.mLogArgumentNames = z;
    }

    private void checkError() throws IOException {
        int iGlGetError = this.mgl.glGetError();
        if (iGlGetError != 0) {
            logLine("glError: " + Integer.toString(iGlGetError));
        }
    }

    private void logLine(String str) throws IOException {
        log(str + '\n');
    }

    private void log(String str) throws IOException {
        try {
            this.mLog.write(str);
        } catch (IOException unused) {
        }
    }

    private void begin(String str) throws IOException {
        log(str + '(');
        this.mArgCount = 0;
    }

    private void arg(String str, String str2) throws IOException {
        int i = this.mArgCount;
        this.mArgCount = i + 1;
        if (i > 0) {
            log(", ");
        }
        if (this.mLogArgumentNames) {
            log(str + "=");
        }
        log(str2);
    }

    private void end() throws IOException {
        log(");\n");
        flush();
    }

    private void flush() throws IOException {
        try {
            this.mLog.flush();
        } catch (IOException unused) {
            this.mLog = null;
        }
    }

    private void arg(String str, boolean z) throws IOException {
        arg(str, Boolean.toString(z));
    }

    private void arg(String str, int i) throws IOException {
        arg(str, Integer.toString(i));
    }

    private void arg(String str, float f) throws IOException {
        arg(str, Float.toString(f));
    }

    private void returns(String str) throws IOException {
        log(") returns " + str + ";\n");
        flush();
    }

    private void returns(int i) throws IOException {
        returns(Integer.toString(i));
    }

    private void arg(String str, int i, int[] iArr, int i2) throws IOException {
        arg(str, toString(i, 0, iArr, i2));
    }

    private void arg(String str, int i, short[] sArr, int i2) throws IOException {
        arg(str, toString(i, sArr, i2));
    }

    private void arg(String str, int i, float[] fArr, int i2) throws IOException {
        arg(str, toString(i, fArr, i2));
    }

    private void formattedAppend(StringBuilder sb, int i, int i2) {
        if (i2 == 0) {
            sb.append(i);
        } else if (i2 == 1) {
            sb.append(Float.intBitsToFloat(i));
        } else {
            if (i2 != 2) {
                return;
            }
            sb.append(i / 65536.0f);
        }
    }

    private String toString(int i, int i2, int[] iArr, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        int length = iArr.length;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = i3 + i4;
            sb.append(" [" + i5 + "] = ");
            if (i5 < 0 || i5 >= length) {
                sb.append("out of bounds");
            } else {
                formattedAppend(sb, iArr[i5], i2);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(int i, short[] sArr, int i2) {
        StringBuilder sb = new StringBuilder("{\n");
        int length = sArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(" [" + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append((int) sArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(int i, float[] fArr, int i2) {
        StringBuilder sb = new StringBuilder("{\n");
        int length = fArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(NavigationBarInflaterView.SIZE_MOD_START + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append(fArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(int i, FloatBuffer floatBuffer) {
        StringBuilder sb = new StringBuilder("{\n");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" [" + i2 + "] = " + floatBuffer.get(i2) + '\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(int i, int i2, IntBuffer intBuffer) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(" [" + i3 + "] = ");
            formattedAppend(sb, intBuffer.get(i3), i2);
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(int i, ShortBuffer shortBuffer) {
        StringBuilder sb = new StringBuilder("{\n");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" [" + i2 + "] = " + ((int) shortBuffer.get(i2)) + '\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private void arg(String str, int i, FloatBuffer floatBuffer) throws IOException {
        arg(str, toString(i, floatBuffer));
    }

    private void arg(String str, int i, IntBuffer intBuffer) throws IOException {
        arg(str, toString(i, 0, intBuffer));
    }

    private void arg(String str, int i, ShortBuffer shortBuffer) throws IOException {
        arg(str, toString(i, shortBuffer));
    }

    private void argPointer(int i, int i2, int i3, Buffer buffer) throws IOException {
        arg(Contract.DatabaseSize.PATH, i);
        arg("type", getPointerTypeName(i2));
        arg("stride", i3);
        arg("pointer", buffer.toString());
    }

    private static String getHex(int i) {
        return "0x" + Integer.toHexString(i);
    }

    public static String getErrorString(int i) {
        if (i == 0) {
            return "GL_NO_ERROR";
        }
        switch (i) {
            case 1280:
                return "GL_INVALID_ENUM";
            case 1281:
                return "GL_INVALID_VALUE";
            case 1282:
                return "GL_INVALID_OPERATION";
            case 1283:
                return "GL_STACK_OVERFLOW";
            case 1284:
                return "GL_STACK_UNDERFLOW";
            case 1285:
                return "GL_OUT_OF_MEMORY";
            default:
                return getHex(i);
        }
    }

    private String getClearBufferMask(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 256) != 0) {
            sb.append("GL_DEPTH_BUFFER_BIT");
            i &= -257;
        }
        if ((i & 1024) != 0) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append("GL_STENCIL_BUFFER_BIT");
            i &= -1025;
        }
        if ((i & 16384) != 0) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append("GL_COLOR_BUFFER_BIT");
            i &= -16385;
        }
        if (i != 0) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(getHex(i));
        }
        return sb.toString();
    }

    private String getFactor(int i) {
        if (i == 0) {
            return "GL_ZERO";
        }
        if (i == 1) {
            return "GL_ONE";
        }
        switch (i) {
            case 768:
                return "GL_SRC_COLOR";
            case 769:
                return "GL_ONE_MINUS_SRC_COLOR";
            case 770:
                return "GL_SRC_ALPHA";
            case 771:
                return "GL_ONE_MINUS_SRC_ALPHA";
            case 772:
                return "GL_DST_ALPHA";
            case 773:
                return "GL_ONE_MINUS_DST_ALPHA";
            case 774:
                return "GL_DST_COLOR";
            case 775:
                return "GL_ONE_MINUS_DST_COLOR";
            case 776:
                return "GL_SRC_ALPHA_SATURATE";
            default:
                return getHex(i);
        }
    }

    private String getShadeModel(int i) {
        if (i == 7424) {
            return "GL_FLAT";
        }
        if (i == 7425) {
            return "GL_SMOOTH";
        }
        return getHex(i);
    }

    private String getTextureTarget(int i) {
        if (i == 3553) {
            return "GL_TEXTURE_2D";
        }
        return getHex(i);
    }

    private String getTextureEnvTarget(int i) {
        if (i == 8960) {
            return "GL_TEXTURE_ENV";
        }
        return getHex(i);
    }

    private String getTextureEnvPName(int i) {
        if (i == 8704) {
            return "GL_TEXTURE_ENV_MODE";
        }
        if (i == 8705) {
            return "GL_TEXTURE_ENV_COLOR";
        }
        return getHex(i);
    }

    private String getTextureEnvParamName(float f) {
        int i = (int) f;
        if (f != i) {
            return Float.toString(f);
        }
        if (i == 260) {
            return "GL_ADD";
        }
        if (i == 3042) {
            return "GL_BLEND";
        }
        if (i == 7681) {
            return "GL_REPLACE";
        }
        if (i == 34160) {
            return "GL_COMBINE";
        }
        if (i == 8448) {
            return "GL_MODULATE";
        }
        if (i == 8449) {
            return "GL_DECAL";
        }
        return getHex(i);
    }

    private String getMatrixMode(int i) {
        switch (i) {
            case 5888:
                return "GL_MODELVIEW";
            case 5889:
                return "GL_PROJECTION";
            case 5890:
                return "GL_TEXTURE";
            default:
                return getHex(i);
        }
    }

    private String getClientState(int i) {
        switch (i) {
            case 32884:
                return "GL_VERTEX_ARRAY";
            case 32885:
                return "GL_NORMAL_ARRAY";
            case 32886:
                return "GL_COLOR_ARRAY";
            case 32887:
            default:
                return getHex(i);
            case 32888:
                return "GL_TEXTURE_COORD_ARRAY";
        }
    }

    private String getCap(int i) {
        switch (i) {
            case 2832:
                return "GL_POINT_SMOOTH";
            case 2848:
                return "GL_LINE_SMOOTH";
            case 2884:
                return "GL_CULL_FACE";
            case 2896:
                return "GL_LIGHTING";
            case 2903:
                return "GL_COLOR_MATERIAL";
            case 2912:
                return "GL_FOG";
            case 2929:
                return "GL_DEPTH_TEST";
            case 2960:
                return "GL_STENCIL_TEST";
            case 2977:
                return "GL_NORMALIZE";
            case 3008:
                return "GL_ALPHA_TEST";
            case 3024:
                return "GL_DITHER";
            case 3042:
                return "GL_BLEND";
            case 3058:
                return "GL_COLOR_LOGIC_OP";
            case 3089:
                return "GL_SCISSOR_TEST";
            case 3553:
                return "GL_TEXTURE_2D";
            case 32826:
                return "GL_RESCALE_NORMAL";
            case 32888:
                return "GL_TEXTURE_COORD_ARRAY";
            default:
                switch (i) {
                    case 16384:
                        return "GL_LIGHT0";
                    case 16385:
                        return "GL_LIGHT1";
                    case 16386:
                        return "GL_LIGHT2";
                    case 16387:
                        return "GL_LIGHT3";
                    case 16388:
                        return "GL_LIGHT4";
                    case 16389:
                        return "GL_LIGHT5";
                    case 16390:
                        return "GL_LIGHT6";
                    case 16391:
                        return "GL_LIGHT7";
                    default:
                        switch (i) {
                            case 32884:
                                return "GL_VERTEX_ARRAY";
                            case 32885:
                                return "GL_NORMAL_ARRAY";
                            case 32886:
                                return "GL_COLOR_ARRAY";
                            default:
                                switch (i) {
                                    case 32925:
                                        return "GL_MULTISAMPLE";
                                    case 32926:
                                        return "GL_SAMPLE_ALPHA_TO_COVERAGE";
                                    case 32927:
                                        return "GL_SAMPLE_ALPHA_TO_ONE";
                                    case 32928:
                                        return "GL_SAMPLE_COVERAGE";
                                    default:
                                        return getHex(i);
                                }
                        }
                }
        }
    }

    private String getTexturePName(int i) {
        if (i == 33169) {
            return "GL_GENERATE_MIPMAP";
        }
        if (i != 35741) {
            switch (i) {
                case 10240:
                    return "GL_TEXTURE_MAG_FILTER";
                case 10241:
                    return "GL_TEXTURE_MIN_FILTER";
                case 10242:
                    return "GL_TEXTURE_WRAP_S";
                case 10243:
                    return "GL_TEXTURE_WRAP_T";
                default:
                    return getHex(i);
            }
        }
        return "GL_TEXTURE_CROP_RECT_OES";
    }

    private String getTextureParamName(float f) {
        int i = (int) f;
        if (f != i) {
            return Float.toString(f);
        }
        if (i == 9728) {
            return "GL_NEAREST";
        }
        if (i == 9729) {
            return "GL_LINEAR";
        }
        if (i == 10497) {
            return "GL_REPEAT";
        }
        if (i == 33071) {
            return "GL_CLAMP_TO_EDGE";
        }
        switch (i) {
            case 9984:
                return "GL_NEAREST_MIPMAP_NEAREST";
            case 9985:
                return "GL_LINEAR_MIPMAP_NEAREST";
            case 9986:
                return "GL_NEAREST_MIPMAP_LINEAR";
            case 9987:
                return "GL_LINEAR_MIPMAP_LINEAR";
            default:
                return getHex(i);
        }
    }

    private String getFogPName(int i) {
        switch (i) {
            case 2914:
                return "GL_FOG_DENSITY";
            case 2915:
                return "GL_FOG_START";
            case 2916:
                return "GL_FOG_END";
            case 2917:
                return "GL_FOG_MODE";
            case 2918:
                return "GL_FOG_COLOR";
            default:
                return getHex(i);
        }
    }

    private String getBeginMode(int i) {
        switch (i) {
            case 0:
                return "GL_POINTS";
            case 1:
                return "GL_LINES";
            case 2:
                return "GL_LINE_LOOP";
            case 3:
                return "GL_LINE_STRIP";
            case 4:
                return "GL_TRIANGLES";
            case 5:
                return "GL_TRIANGLE_STRIP";
            case 6:
                return "GL_TRIANGLE_FAN";
            default:
                return getHex(i);
        }
    }

    private String getIndexType(int i) {
        if (i == 5121) {
            return "GL_UNSIGNED_BYTE";
        }
        if (i == 5123) {
            return "GL_UNSIGNED_SHORT";
        }
        return getHex(i);
    }

    private String getIntegerStateName(int i) {
        switch (i) {
            case 2834:
                return "GL_SMOOTH_POINT_SIZE_RANGE";
            case 2850:
                return "GL_SMOOTH_LINE_WIDTH_RANGE";
            case 3377:
                return "GL_MAX_LIGHTS";
            case 3379:
                return "GL_MAX_TEXTURE_SIZE";
            case 3382:
                return "GL_MAX_MODELVIEW_STACK_DEPTH";
            case 3384:
                return "GL_MAX_PROJECTION_STACK_DEPTH";
            case 3385:
                return "GL_MAX_TEXTURE_STACK_DEPTH";
            case 3386:
                return "GL_MAX_VIEWPORT_DIMS";
            case 3408:
                return "GL_SUBPIXEL_BITS";
            case 3410:
                return "GL_RED_BITS";
            case 3411:
                return "GL_GREEN_BITS";
            case 3412:
                return "GL_BLUE_BITS";
            case 3413:
                return "GL_ALPHA_BITS";
            case 3414:
                return "GL_DEPTH_BITS";
            case 3415:
                return "GL_STENCIL_BITS";
            case 33000:
                return "GL_MAX_ELEMENTS_VERTICES";
            case 33001:
                return "GL_MAX_ELEMENTS_INDICES";
            case 33901:
                return "GL_ALIASED_POINT_SIZE_RANGE";
            case 33902:
                return "GL_ALIASED_LINE_WIDTH_RANGE";
            case 34018:
                return "GL_MAX_TEXTURE_UNITS";
            case 34466:
                return "GL_NUM_COMPRESSED_TEXTURE_FORMATS";
            case 34467:
                return "GL_COMPRESSED_TEXTURE_FORMATS";
            case 35213:
                return "GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES";
            case 35214:
                return "GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES";
            case 35215:
                return "GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES";
            default:
                return getHex(i);
        }
    }

    private int getIntegerStateSize(int i) {
        switch (i) {
            case 2834:
            case 2850:
                return 2;
            case 3377:
            case 3379:
            case 3382:
            case 3384:
            case 3385:
                return 1;
            case 3386:
                return 2;
            case 3408:
            case 3410:
            case 3411:
            case 3412:
            case 3413:
            case 3414:
            case 3415:
            case 33000:
            case 33001:
                return 1;
            case 33901:
            case 33902:
                return 2;
            case 34018:
            case 34466:
                return 1;
            case 34467:
                int[] iArr = new int[1];
                this.mgl.glGetIntegerv(34466, iArr, 0);
                return iArr[0];
            case 35213:
            case 35214:
            case 35215:
                return 16;
            default:
                return 0;
        }
    }

    private String getHintTarget(int i) {
        if (i != 33170) {
            switch (i) {
                case 3152:
                    return "GL_PERSPECTIVE_CORRECTION_HINT";
                case 3153:
                    return "GL_POINT_SMOOTH_HINT";
                case 3154:
                    return "GL_LINE_SMOOTH_HINT";
                case 3155:
                    return "GL_POLYGON_SMOOTH_HINT";
                case 3156:
                    return "GL_FOG_HINT";
                default:
                    return getHex(i);
            }
        }
        return "GL_GENERATE_MIPMAP_HINT";
    }

    private String getHintMode(int i) {
        switch (i) {
            case 4352:
                return "GL_DONT_CARE";
            case 4353:
                return "GL_FASTEST";
            case 4354:
                return "GL_NICEST";
            default:
                return getHex(i);
        }
    }

    private String getFaceName(int i) {
        if (i == 1032) {
            return "GL_FRONT_AND_BACK";
        }
        return getHex(i);
    }

    private String getMaterialPName(int i) {
        switch (i) {
            case 4608:
                return "GL_AMBIENT";
            case 4609:
                return "GL_DIFFUSE";
            case 4610:
                return "GL_SPECULAR";
            default:
                switch (i) {
                    case 5632:
                        return "GL_EMISSION";
                    case 5633:
                        return "GL_SHININESS";
                    case 5634:
                        return "GL_AMBIENT_AND_DIFFUSE";
                    default:
                        return getHex(i);
                }
        }
    }

    private String getLightName(int i) {
        if (i >= 16384 && i <= 16391) {
            return "GL_LIGHT" + Integer.toString(i);
        }
        return getHex(i);
    }

    private String getLightPName(int i) {
        switch (i) {
            case 4608:
                return "GL_AMBIENT";
            case 4609:
                return "GL_DIFFUSE";
            case 4610:
                return "GL_SPECULAR";
            case 4611:
                return "GL_POSITION";
            case 4612:
                return "GL_SPOT_DIRECTION";
            case 4613:
                return "GL_SPOT_EXPONENT";
            case 4614:
                return "GL_SPOT_CUTOFF";
            case 4615:
                return "GL_CONSTANT_ATTENUATION";
            case 4616:
                return "GL_LINEAR_ATTENUATION";
            case 4617:
                return "GL_QUADRATIC_ATTENUATION";
            default:
                return getHex(i);
        }
    }

    private String getLightModelPName(int i) {
        if (i == 2898) {
            return "GL_LIGHT_MODEL_TWO_SIDE";
        }
        if (i == 2899) {
            return "GL_LIGHT_MODEL_AMBIENT";
        }
        return getHex(i);
    }

    private String getPointerTypeName(int i) {
        if (i == 5126) {
            return "GL_FLOAT";
        }
        if (i != 5132) {
            switch (i) {
                case 5120:
                    return "GL_BYTE";
                case 5121:
                    return "GL_UNSIGNED_BYTE";
                case 5122:
                    return "GL_SHORT";
                default:
                    return getHex(i);
            }
        }
        return "GL_FIXED";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ByteBuffer toByteBuffer(int i, Buffer buffer) {
        ByteBuffer byteBufferOrder;
        int i2 = 0;
        boolean z = i < 0;
        if (buffer instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) buffer;
            int iPosition = byteBuffer.position();
            if (z) {
                i = byteBuffer.limit() - iPosition;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(byteBuffer.order());
            while (i2 < i) {
                byteBufferOrder.put(byteBuffer.get());
                i2++;
            }
            byteBuffer.position(iPosition);
        } else if (buffer instanceof CharBuffer) {
            CharBuffer charBuffer = (CharBuffer) buffer;
            int iPosition2 = charBuffer.position();
            if (z) {
                i = (charBuffer.limit() - iPosition2) * 2;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(charBuffer.order());
            CharBuffer charBufferAsCharBuffer = byteBufferOrder.asCharBuffer();
            while (i2 < i / 2) {
                charBufferAsCharBuffer.put(charBuffer.get());
                i2++;
            }
            charBuffer.position(iPosition2);
        } else if (buffer instanceof ShortBuffer) {
            ShortBuffer shortBuffer = (ShortBuffer) buffer;
            int iPosition3 = shortBuffer.position();
            if (z) {
                i = (shortBuffer.limit() - iPosition3) * 2;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(shortBuffer.order());
            ShortBuffer shortBufferAsShortBuffer = byteBufferOrder.asShortBuffer();
            while (i2 < i / 2) {
                shortBufferAsShortBuffer.put(shortBuffer.get());
                i2++;
            }
            shortBuffer.position(iPosition3);
        } else if (buffer instanceof IntBuffer) {
            IntBuffer intBuffer = (IntBuffer) buffer;
            int iPosition4 = intBuffer.position();
            if (z) {
                i = (intBuffer.limit() - iPosition4) * 4;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(intBuffer.order());
            IntBuffer intBufferAsIntBuffer = byteBufferOrder.asIntBuffer();
            while (i2 < i / 4) {
                intBufferAsIntBuffer.put(intBuffer.get());
                i2++;
            }
            intBuffer.position(iPosition4);
        } else if (buffer instanceof FloatBuffer) {
            FloatBuffer floatBuffer = (FloatBuffer) buffer;
            int iPosition5 = floatBuffer.position();
            if (z) {
                i = (floatBuffer.limit() - iPosition5) * 4;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(floatBuffer.order());
            FloatBuffer floatBufferAsFloatBuffer = byteBufferOrder.asFloatBuffer();
            while (i2 < i / 4) {
                floatBufferAsFloatBuffer.put(floatBuffer.get());
                i2++;
            }
            floatBuffer.position(iPosition5);
        } else if (buffer instanceof DoubleBuffer) {
            DoubleBuffer doubleBuffer = (DoubleBuffer) buffer;
            int iPosition6 = doubleBuffer.position();
            if (z) {
                i = (doubleBuffer.limit() - iPosition6) * 8;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(doubleBuffer.order());
            DoubleBuffer doubleBufferAsDoubleBuffer = byteBufferOrder.asDoubleBuffer();
            while (i2 < i / 8) {
                doubleBufferAsDoubleBuffer.put(doubleBuffer.get());
                i2++;
            }
            doubleBuffer.position(iPosition6);
        } else if (buffer instanceof LongBuffer) {
            LongBuffer longBuffer = (LongBuffer) buffer;
            int iPosition7 = longBuffer.position();
            if (z) {
                i = (longBuffer.limit() - iPosition7) * 8;
            }
            byteBufferOrder = ByteBuffer.allocate(i).order(longBuffer.order());
            LongBuffer longBufferAsLongBuffer = byteBufferOrder.asLongBuffer();
            while (i2 < i / 8) {
                longBufferAsLongBuffer.put(longBuffer.get());
                i2++;
            }
            longBuffer.position(iPosition7);
        } else {
            throw new RuntimeException("Unimplemented Buffer subclass.");
        }
        byteBufferOrder.rewind();
        byteBufferOrder.order(ByteOrder.nativeOrder());
        return byteBufferOrder;
    }

    private char[] toCharIndices(int i, int i2, Buffer buffer) {
        CharBuffer charBufferAsCharBuffer;
        char[] cArr = new char[i];
        if (i2 == 5121) {
            ByteBuffer byteBuffer = toByteBuffer(i, buffer);
            byte[] bArrArray = byteBuffer.array();
            int iArrayOffset = byteBuffer.arrayOffset();
            for (int i3 = 0; i3 < i; i3++) {
                cArr[i3] = (char) (bArrArray[iArrayOffset + i3] & 255);
            }
        } else if (i2 == 5123) {
            if (buffer instanceof CharBuffer) {
                charBufferAsCharBuffer = (CharBuffer) buffer;
            } else {
                charBufferAsCharBuffer = toByteBuffer(i * 2, buffer).asCharBuffer();
            }
            int iPosition = charBufferAsCharBuffer.position();
            charBufferAsCharBuffer.position(0);
            charBufferAsCharBuffer.get(cArr);
            charBufferAsCharBuffer.position(iPosition);
            return cArr;
        }
        return cArr;
    }

    private void doArrayElement(StringBuilder sb, boolean z, String str, PointerInfo pointerInfo, int i) {
        if (z) {
            sb.append(" ");
            sb.append(str + ":{");
            if (pointerInfo == null || pointerInfo.mTempByteBuffer == null) {
                sb.append("undefined }");
                return;
            }
            if (pointerInfo.mStride < 0) {
                sb.append("invalid stride");
                return;
            }
            int stride = pointerInfo.getStride();
            ByteBuffer byteBuffer = pointerInfo.mTempByteBuffer;
            int i2 = pointerInfo.mSize;
            int i3 = pointerInfo.mType;
            int iSizeof = pointerInfo.sizeof(i3);
            int i4 = stride * i;
            for (int i5 = 0; i5 < i2; i5++) {
                if (i5 > 0) {
                    sb.append(", ");
                }
                if (i3 == 5126) {
                    sb.append(Float.toString(byteBuffer.asFloatBuffer().get(i4 / 4)));
                } else if (i3 != 5132) {
                    switch (i3) {
                        case 5120:
                            sb.append(Integer.toString(byteBuffer.get(i4)));
                            break;
                        case 5121:
                            sb.append(Integer.toString(byteBuffer.get(i4) & 255));
                            break;
                        case 5122:
                            sb.append(Integer.toString(byteBuffer.asShortBuffer().get(i4 / 2)));
                            break;
                        default:
                            sb.append("?");
                            break;
                    }
                } else {
                    sb.append(Integer.toString(byteBuffer.asIntBuffer().get(i4 / 4)));
                }
                i4 += iSizeof;
            }
            sb.append("}");
        }
    }

    private void doElement(StringBuilder sb, int i, int i2) {
        sb.append(" [" + i + " : " + i2 + "] =");
        doArrayElement(sb, this.mVertexArrayEnabled, "v", this.mVertexPointer, i2);
        doArrayElement(sb, this.mNormalArrayEnabled, "n", this.mNormalPointer, i2);
        doArrayElement(sb, this.mColorArrayEnabled, "c", this.mColorPointer, i2);
        doArrayElement(sb, this.mTextureCoordArrayEnabled, "t", this.mTexCoordPointer, i2);
        sb.append(ShaderAssembler.NEWLINE);
    }

    private void bindArrays() {
        if (this.mColorArrayEnabled) {
            this.mColorPointer.bindByteBuffer();
        }
        if (this.mNormalArrayEnabled) {
            this.mNormalPointer.bindByteBuffer();
        }
        if (this.mTextureCoordArrayEnabled) {
            this.mTexCoordPointer.bindByteBuffer();
        }
        if (this.mVertexArrayEnabled) {
            this.mVertexPointer.bindByteBuffer();
        }
    }

    private void unbindArrays() {
        if (this.mColorArrayEnabled) {
            this.mColorPointer.unbindByteBuffer();
        }
        if (this.mNormalArrayEnabled) {
            this.mNormalPointer.unbindByteBuffer();
        }
        if (this.mTextureCoordArrayEnabled) {
            this.mTexCoordPointer.unbindByteBuffer();
        }
        if (this.mVertexArrayEnabled) {
            this.mVertexPointer.unbindByteBuffer();
        }
    }

    private void startLogIndices() {
        StringBuilder sb = new StringBuilder();
        this.mStringBuilder = sb;
        sb.append(ShaderAssembler.NEWLINE);
        bindArrays();
    }

    private void endLogIndices() throws IOException {
        log(this.mStringBuilder.toString());
        unbindArrays();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glActiveTexture(int i) throws IOException {
        begin("glActiveTexture");
        arg("texture", i);
        end();
        this.mgl.glActiveTexture(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glAlphaFunc(int i, float f) throws IOException {
        begin("glAlphaFunc");
        arg("func", i);
        arg("ref", f);
        end();
        this.mgl.glAlphaFunc(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glAlphaFuncx(int i, int i2) throws IOException {
        begin("glAlphaFuncx");
        arg("func", i);
        arg("ref", i2);
        end();
        this.mgl.glAlphaFuncx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindTexture(int i, int i2) throws IOException {
        begin("glBindTexture");
        arg("target", getTextureTarget(i));
        arg("texture", i2);
        end();
        this.mgl.glBindTexture(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glBlendFunc(int i, int i2) throws IOException {
        begin("glBlendFunc");
        arg("sfactor", getFactor(i));
        arg("dfactor", getFactor(i2));
        end();
        this.mgl.glBlendFunc(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClear(int i) throws IOException {
        begin("glClear");
        arg("mask", getClearBufferMask(i));
        end();
        this.mgl.glClear(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearColor(float f, float f2, float f3, float f4) throws IOException {
        begin("glClearColor");
        arg("red", f);
        arg("green", f2);
        arg("blue", f3);
        arg("alpha", f4);
        end();
        this.mgl.glClearColor(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearColorx(int i, int i2, int i3, int i4) throws IOException {
        begin("glClearColor");
        arg("red", i);
        arg("green", i2);
        arg("blue", i3);
        arg("alpha", i4);
        end();
        this.mgl.glClearColorx(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearDepthf(float f) throws IOException {
        begin("glClearDepthf");
        arg("depth", f);
        end();
        this.mgl.glClearDepthf(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearDepthx(int i) throws IOException {
        begin("glClearDepthx");
        arg("depth", i);
        end();
        this.mgl.glClearDepthx(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearStencil(int i) throws IOException {
        begin("glClearStencil");
        arg(XmlTags.TAG_SESSION, i);
        end();
        this.mgl.glClearStencil(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClientActiveTexture(int i) throws IOException {
        begin("glClientActiveTexture");
        arg("texture", i);
        end();
        this.mgl.glClientActiveTexture(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColor4f(float f, float f2, float f3, float f4) throws IOException {
        begin("glColor4f");
        arg("red", f);
        arg("green", f2);
        arg("blue", f3);
        arg("alpha", f4);
        end();
        this.mgl.glColor4f(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColor4x(int i, int i2, int i3, int i4) throws IOException {
        begin("glColor4x");
        arg("red", i);
        arg("green", i2);
        arg("blue", i3);
        arg("alpha", i4);
        end();
        this.mgl.glColor4x(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorMask(boolean z, boolean z2, boolean z3, boolean z4) throws IOException {
        begin("glColorMask");
        arg("red", z);
        arg("green", z2);
        arg("blue", z3);
        arg("alpha", z4);
        end();
        this.mgl.glColorMask(z, z2, z3, z4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorPointer(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glColorPointer");
        argPointer(i, i2, i3, buffer);
        end();
        this.mColorPointer = new PointerInfo(i, i2, i3, buffer);
        this.mgl.glColorPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glCompressedTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, Buffer buffer) throws IOException {
        begin("glCompressedTexImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("internalformat", i3);
        arg("width", i4);
        arg("height", i5);
        arg("border", i6);
        arg("imageSize", i7);
        arg("data", buffer.toString());
        end();
        this.mgl.glCompressedTexImage2D(i, i2, i3, i4, i5, i6, i7, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCompressedTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Buffer buffer) throws IOException {
        begin("glCompressedTexSubImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("xoffset", i3);
        arg("yoffset", i4);
        arg("width", i5);
        arg("height", i6);
        arg(Telephony.CellBroadcasts.MESSAGE_FORMAT, i7);
        arg("imageSize", i8);
        arg("data", buffer.toString());
        end();
        this.mgl.glCompressedTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glCopyTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IOException {
        begin("glCopyTexImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("internalformat", i3);
        arg("x", i4);
        arg("y", i5);
        arg("width", i6);
        arg("height", i7);
        arg("border", i8);
        end();
        this.mgl.glCopyTexImage2D(i, i2, i3, i4, i5, i6, i7, i8);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCopyTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IOException {
        begin("glCopyTexSubImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("xoffset", i3);
        arg("yoffset", i4);
        arg("x", i5);
        arg("y", i6);
        arg("width", i7);
        arg("height", i8);
        end();
        this.mgl.glCopyTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCullFace(int i) throws IOException {
        begin("glCullFace");
        arg("mode", i);
        end();
        this.mgl.glCullFace(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDeleteTextures(int i, int[] iArr, int i2) throws IOException {
        begin("glDeleteTextures");
        arg("n", i);
        arg("textures", i, iArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glDeleteTextures(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDeleteTextures(int i, IntBuffer intBuffer) throws IOException {
        begin("glDeleteTextures");
        arg("n", i);
        arg("textures", i, intBuffer);
        end();
        this.mgl.glDeleteTextures(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthFunc(int i) throws IOException {
        begin("glDepthFunc");
        arg("func", i);
        end();
        this.mgl.glDepthFunc(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthMask(boolean z) throws IOException {
        begin("glDepthMask");
        arg(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG, z);
        end();
        this.mgl.glDepthMask(z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthRangef(float f, float f2) throws IOException {
        begin("glDepthRangef");
        arg("near", f);
        arg("far", f2);
        end();
        this.mgl.glDepthRangef(f, f2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthRangex(int i, int i2) throws IOException {
        begin("glDepthRangex");
        arg("near", i);
        arg("far", i2);
        end();
        this.mgl.glDepthRangex(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDisable(int i) throws IOException {
        begin("glDisable");
        arg("cap", getCap(i));
        end();
        this.mgl.glDisable(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDisableClientState(int i) throws IOException {
        begin("glDisableClientState");
        arg("array", getClientState(i));
        end();
        switch (i) {
            case 32884:
                this.mVertexArrayEnabled = false;
                break;
            case 32885:
                this.mNormalArrayEnabled = false;
                break;
            case 32886:
                this.mColorArrayEnabled = false;
                break;
            case 32888:
                this.mTextureCoordArrayEnabled = false;
                break;
        }
        this.mgl.glDisableClientState(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDrawArrays(int i, int i2, int i3) throws IOException {
        begin("glDrawArrays");
        arg("mode", i);
        arg("first", i2);
        arg(Contract.Events.Projection.COUNT_ONLY, i3);
        startLogIndices();
        for (int i4 = 0; i4 < i3; i4++) {
            doElement(this.mStringBuilder, i4, i2 + i4);
        }
        endLogIndices();
        end();
        this.mgl.glDrawArrays(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDrawElements(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glDrawElements");
        arg("mode", getBeginMode(i));
        arg(Contract.Events.Projection.COUNT_ONLY, i2);
        arg("type", getIndexType(i3));
        char[] charIndices = toCharIndices(i2, i3, buffer);
        int length = charIndices.length;
        startLogIndices();
        for (int i4 = 0; i4 < length; i4++) {
            doElement(this.mStringBuilder, i4, charIndices[i4]);
        }
        endLogIndices();
        end();
        this.mgl.glDrawElements(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glEnable(int i) throws IOException {
        begin("glEnable");
        arg("cap", getCap(i));
        end();
        this.mgl.glEnable(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext
    public void glEnableClientState(int i) throws IOException {
        begin("glEnableClientState");
        arg("array", getClientState(i));
        end();
        switch (i) {
            case 32884:
                this.mVertexArrayEnabled = true;
                break;
            case 32885:
                this.mNormalArrayEnabled = true;
                break;
            case 32886:
                this.mColorArrayEnabled = true;
                break;
            case 32888:
                this.mTextureCoordArrayEnabled = true;
                break;
        }
        this.mgl.glEnableClientState(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFinish() throws IOException {
        begin("glFinish");
        end();
        this.mgl.glFinish();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFlush() throws IOException {
        begin("glFlush");
        end();
        this.mgl.glFlush();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogf(int i, float f) throws IOException {
        begin("glFogf");
        arg("pname", i);
        arg("param", f);
        end();
        this.mgl.glFogf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogfv(int i, float[] fArr, int i2) throws IOException {
        begin("glFogfv");
        arg("pname", getFogPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getFogParamCount(i), fArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glFogfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogfv(int i, FloatBuffer floatBuffer) throws IOException {
        begin("glFogfv");
        arg("pname", getFogPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getFogParamCount(i), floatBuffer);
        end();
        this.mgl.glFogfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogx(int i, int i2) throws IOException {
        begin("glFogx");
        arg("pname", getFogPName(i));
        arg("param", i2);
        end();
        this.mgl.glFogx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogxv(int i, int[] iArr, int i2) throws IOException {
        begin("glFogxv");
        arg("pname", getFogPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getFogParamCount(i), iArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glFogxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogxv(int i, IntBuffer intBuffer) throws IOException {
        begin("glFogxv");
        arg("pname", getFogPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getFogParamCount(i), intBuffer);
        end();
        this.mgl.glFogxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrontFace(int i) throws IOException {
        begin("glFrontFace");
        arg("mode", i);
        end();
        this.mgl.glFrontFace(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrustumf(float f, float f2, float f3, float f4, float f5, float f6) throws IOException {
        begin("glFrustumf");
        arg("left", f);
        arg("right", f2);
        arg(GenerateXML.BOTTOM, f3);
        arg(GenerateXML.TOP, f4);
        arg("near", f5);
        arg("far", f6);
        end();
        this.mgl.glFrustumf(f, f2, f3, f4, f5, f6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrustumx(int i, int i2, int i3, int i4, int i5, int i6) throws IOException {
        begin("glFrustumx");
        arg("left", i);
        arg("right", i2);
        arg(GenerateXML.BOTTOM, i3);
        arg(GenerateXML.TOP, i4);
        arg("near", i5);
        arg("far", i6);
        end();
        this.mgl.glFrustumx(i, i2, i3, i4, i5, i6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glGenTextures(int i, int[] iArr, int i2) throws IOException {
        begin("glGenTextures");
        arg("n", i);
        arg("textures", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        this.mgl.glGenTextures(i, iArr, i2);
        returns(toString(i, 0, iArr, i2));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glGenTextures(int i, IntBuffer intBuffer) throws IOException {
        begin("glGenTextures");
        arg("n", i);
        arg("textures", intBuffer.toString());
        this.mgl.glGenTextures(i, intBuffer);
        returns(toString(i, 0, intBuffer));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public int glGetError() throws IOException {
        begin("glGetError");
        int iGlGetError = this.mgl.glGetError();
        returns(iGlGetError);
        return iGlGetError;
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetIntegerv(int i, int[] iArr, int i2) throws IOException {
        begin("glGetIntegerv");
        arg("pname", getIntegerStateName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        this.mgl.glGetIntegerv(i, iArr, i2);
        returns(toString(getIntegerStateSize(i), getIntegerStateFormat(i), iArr, i2));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetIntegerv(int i, IntBuffer intBuffer) throws IOException {
        begin("glGetIntegerv");
        arg("pname", getIntegerStateName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        this.mgl.glGetIntegerv(i, intBuffer);
        returns(toString(getIntegerStateSize(i), getIntegerStateFormat(i), intBuffer));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public String glGetString(int i) throws IOException {
        begin("glGetString");
        arg("name", i);
        String strGlGetString = this.mgl.glGetString(i);
        returns(strGlGetString);
        checkError();
        return strGlGetString;
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glHint(int i, int i2) throws IOException {
        begin("glHint");
        arg("target", getHintTarget(i));
        arg("mode", getHintMode(i2));
        end();
        this.mgl.glHint(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelf(int i, float f) throws IOException {
        begin("glLightModelf");
        arg("pname", getLightModelPName(i));
        arg("param", f);
        end();
        this.mgl.glLightModelf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelfv(int i, float[] fArr, int i2) throws IOException {
        begin("glLightModelfv");
        arg("pname", getLightModelPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightModelParamCount(i), fArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glLightModelfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelfv(int i, FloatBuffer floatBuffer) throws IOException {
        begin("glLightModelfv");
        arg("pname", getLightModelPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightModelParamCount(i), floatBuffer);
        end();
        this.mgl.glLightModelfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelx(int i, int i2) throws IOException {
        begin("glLightModelx");
        arg("pname", getLightModelPName(i));
        arg("param", i2);
        end();
        this.mgl.glLightModelx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelxv(int i, int[] iArr, int i2) throws IOException {
        begin("glLightModelxv");
        arg("pname", getLightModelPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightModelParamCount(i), iArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glLightModelxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelxv(int i, IntBuffer intBuffer) throws IOException {
        begin("glLightModelfv");
        arg("pname", getLightModelPName(i));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightModelParamCount(i), intBuffer);
        end();
        this.mgl.glLightModelxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightf(int i, int i2, float f) throws IOException {
        begin("glLightf");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("param", f);
        end();
        this.mgl.glLightf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glLightfv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightParamCount(i2), fArr, i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glLightfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glLightfv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightParamCount(i2), floatBuffer);
        end();
        this.mgl.glLightfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightx(int i, int i2, int i3) throws IOException {
        begin("glLightx");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("param", i3);
        end();
        this.mgl.glLightx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glLightxv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightParamCount(i2), iArr, i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glLightxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glLightxv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getLightParamCount(i2), intBuffer);
        end();
        this.mgl.glLightxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLineWidth(float f) throws IOException {
        begin("glLineWidth");
        arg("width", f);
        end();
        this.mgl.glLineWidth(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLineWidthx(int i) throws IOException {
        begin("glLineWidthx");
        arg("width", i);
        end();
        this.mgl.glLineWidthx(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadIdentity() throws IOException {
        begin("glLoadIdentity");
        end();
        this.mgl.glLoadIdentity();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixf(float[] fArr, int i) throws IOException {
        begin("glLoadMatrixf");
        arg("m", 16, fArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glLoadMatrixf(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixf(FloatBuffer floatBuffer) throws IOException {
        begin("glLoadMatrixf");
        arg("m", 16, floatBuffer);
        end();
        this.mgl.glLoadMatrixf(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixx(int[] iArr, int i) throws IOException {
        begin("glLoadMatrixx");
        arg("m", 16, iArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glLoadMatrixx(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixx(IntBuffer intBuffer) throws IOException {
        begin("glLoadMatrixx");
        arg("m", 16, intBuffer);
        end();
        this.mgl.glLoadMatrixx(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLogicOp(int i) throws IOException {
        begin("glLogicOp");
        arg("opcode", i);
        end();
        this.mgl.glLogicOp(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialf(int i, int i2, float f) throws IOException {
        begin("glMaterialf");
        arg(Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("param", f);
        end();
        this.mgl.glMaterialf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glMaterialfv");
        arg(Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getMaterialParamCount(i2), fArr, i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glMaterialfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glMaterialfv");
        arg(Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getMaterialParamCount(i2), floatBuffer);
        end();
        this.mgl.glMaterialfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialx(int i, int i2, int i3) throws IOException {
        begin("glMaterialx");
        arg(Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("param", i3);
        end();
        this.mgl.glMaterialx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glMaterialxv");
        arg(Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getMaterialParamCount(i2), iArr, i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glMaterialxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glMaterialxv");
        arg(Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getMaterialParamCount(i2), intBuffer);
        end();
        this.mgl.glMaterialxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMatrixMode(int i) throws IOException {
        begin("glMatrixMode");
        arg("mode", getMatrixMode(i));
        end();
        this.mgl.glMatrixMode(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixf(float[] fArr, int i) throws IOException {
        begin("glMultMatrixf");
        arg("m", 16, fArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glMultMatrixf(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixf(FloatBuffer floatBuffer) throws IOException {
        begin("glMultMatrixf");
        arg("m", 16, floatBuffer);
        end();
        this.mgl.glMultMatrixf(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixx(int[] iArr, int i) throws IOException {
        begin("glMultMatrixx");
        arg("m", 16, iArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glMultMatrixx(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixx(IntBuffer intBuffer) throws IOException {
        begin("glMultMatrixx");
        arg("m", 16, intBuffer);
        end();
        this.mgl.glMultMatrixx(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultiTexCoord4f(int i, float f, float f2, float f3, float f4) throws IOException {
        begin("glMultiTexCoord4f");
        arg("target", i);
        arg(XmlTags.TAG_SESSION, f);
        arg("t", f2);
        arg("r", f3);
        arg("q", f4);
        end();
        this.mgl.glMultiTexCoord4f(i, f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultiTexCoord4x(int i, int i2, int i3, int i4, int i5) throws IOException {
        begin("glMultiTexCoord4x");
        arg("target", i);
        arg(XmlTags.TAG_SESSION, i2);
        arg("t", i3);
        arg("r", i4);
        arg("q", i5);
        end();
        this.mgl.glMultiTexCoord4x(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormal3f(float f, float f2, float f3) throws IOException {
        begin("glNormal3f");
        arg("nx", f);
        arg("ny", f2);
        arg("nz", f3);
        end();
        this.mgl.glNormal3f(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormal3x(int i, int i2, int i3) throws IOException {
        begin("glNormal3x");
        arg("nx", i);
        arg("ny", i2);
        arg("nz", i3);
        end();
        this.mgl.glNormal3x(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormalPointer(int i, int i2, Buffer buffer) throws IOException {
        begin("glNormalPointer");
        arg("type", i);
        arg("stride", i2);
        arg("pointer", buffer.toString());
        end();
        this.mNormalPointer = new PointerInfo(3, i, i2, buffer);
        this.mgl.glNormalPointer(i, i2, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glOrthof(float f, float f2, float f3, float f4, float f5, float f6) throws IOException {
        begin("glOrthof");
        arg("left", f);
        arg("right", f2);
        arg(GenerateXML.BOTTOM, f3);
        arg(GenerateXML.TOP, f4);
        arg("near", f5);
        arg("far", f6);
        end();
        this.mgl.glOrthof(f, f2, f3, f4, f5, f6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glOrthox(int i, int i2, int i3, int i4, int i5, int i6) throws IOException {
        begin("glOrthox");
        arg("left", i);
        arg("right", i2);
        arg(GenerateXML.BOTTOM, i3);
        arg(GenerateXML.TOP, i4);
        arg("near", i5);
        arg("far", i6);
        end();
        this.mgl.glOrthox(i, i2, i3, i4, i5, i6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPixelStorei(int i, int i2) throws IOException {
        begin("glPixelStorei");
        arg("pname", i);
        arg("param", i2);
        end();
        this.mgl.glPixelStorei(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPointSize(float f) throws IOException {
        begin("glPointSize");
        arg(Contract.DatabaseSize.PATH, f);
        end();
        this.mgl.glPointSize(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPointSizex(int i) throws IOException {
        begin("glPointSizex");
        arg(Contract.DatabaseSize.PATH, i);
        end();
        this.mgl.glPointSizex(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPolygonOffset(float f, float f2) throws IOException {
        begin("glPolygonOffset");
        arg("factor", f);
        arg("units", f2);
        end();
        this.mgl.glPolygonOffset(f, f2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPolygonOffsetx(int i, int i2) throws IOException {
        begin("glPolygonOffsetx");
        arg("factor", i);
        arg("units", i2);
        end();
        this.mgl.glPolygonOffsetx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPopMatrix() throws IOException {
        begin("glPopMatrix");
        end();
        this.mgl.glPopMatrix();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPushMatrix() throws IOException {
        begin("glPushMatrix");
        end();
        this.mgl.glPushMatrix();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glReadPixels(int i, int i2, int i3, int i4, int i5, int i6, Buffer buffer) throws IOException {
        begin("glReadPixels");
        arg("x", i);
        arg("y", i2);
        arg("width", i3);
        arg("height", i4);
        arg(Telephony.CellBroadcasts.MESSAGE_FORMAT, i5);
        arg("type", i6);
        arg("pixels", buffer.toString());
        end();
        this.mgl.glReadPixels(i, i2, i3, i4, i5, i6, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glRotatef(float f, float f2, float f3, float f4) throws IOException {
        begin("glRotatef");
        arg("angle", f);
        arg("x", f2);
        arg("y", f3);
        arg("z", f4);
        end();
        this.mgl.glRotatef(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glRotatex(int i, int i2, int i3, int i4) throws IOException {
        begin("glRotatex");
        arg("angle", i);
        arg("x", i2);
        arg("y", i3);
        arg("z", i4);
        end();
        this.mgl.glRotatex(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glSampleCoverage(float f, boolean z) throws IOException {
        begin("glSampleCoveragex");
        arg("value", f);
        arg("invert", z);
        end();
        this.mgl.glSampleCoverage(f, z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glSampleCoveragex(int i, boolean z) throws IOException {
        begin("glSampleCoveragex");
        arg("value", i);
        arg("invert", z);
        end();
        this.mgl.glSampleCoveragex(i, z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScalef(float f, float f2, float f3) throws IOException {
        begin("glScalef");
        arg("x", f);
        arg("y", f2);
        arg("z", f3);
        end();
        this.mgl.glScalef(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScalex(int i, int i2, int i3) throws IOException {
        begin("glScalex");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        end();
        this.mgl.glScalex(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScissor(int i, int i2, int i3, int i4) throws IOException {
        begin("glScissor");
        arg("x", i);
        arg("y", i2);
        arg("width", i3);
        arg("height", i4);
        end();
        this.mgl.glScissor(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glShadeModel(int i) throws IOException {
        begin("glShadeModel");
        arg("mode", getShadeModel(i));
        end();
        this.mgl.glShadeModel(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glStencilFunc(int i, int i2, int i3) throws IOException {
        begin("glStencilFunc");
        arg("func", i);
        arg("ref", i2);
        arg("mask", i3);
        end();
        this.mgl.glStencilFunc(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glStencilMask(int i) throws IOException {
        begin("glStencilMask");
        arg("mask", i);
        end();
        this.mgl.glStencilMask(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glStencilOp(int i, int i2, int i3) throws IOException {
        begin("glStencilOp");
        arg("fail", i);
        arg("zfail", i2);
        arg("zpass", i3);
        end();
        this.mgl.glStencilOp(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexCoordPointer(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glTexCoordPointer");
        argPointer(i, i2, i3, buffer);
        end();
        this.mTexCoordPointer = new PointerInfo(i, i2, i3, buffer);
        this.mgl.glTexCoordPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvf(int i, int i2, float f) throws IOException {
        begin("glTexEnvf");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("param", getTextureEnvParamName(f));
        end();
        this.mgl.glTexEnvf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glTexEnvfv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getTextureEnvParamCount(i2), fArr, i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glTexEnvfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glTexEnvfv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getTextureEnvParamCount(i2), floatBuffer);
        end();
        this.mgl.glTexEnvfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvx(int i, int i2, int i3) throws IOException {
        begin("glTexEnvx");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("param", i3);
        end();
        this.mgl.glTexEnvx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glTexEnvxv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getTextureEnvParamCount(i2), iArr, i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glTexEnvxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glTexEnvxv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, getTextureEnvParamCount(i2), intBuffer);
        end();
        this.mgl.glTexEnvxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Buffer buffer) throws IOException {
        begin("glTexImage2D");
        arg("target", i);
        arg("level", i2);
        arg("internalformat", i3);
        arg("width", i4);
        arg("height", i5);
        arg("border", i6);
        arg(Telephony.CellBroadcasts.MESSAGE_FORMAT, i7);
        arg("type", i8);
        arg("pixels", buffer.toString());
        end();
        this.mgl.glTexImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexParameterf(int i, int i2, float f) throws IOException {
        begin("glTexParameterf");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg("param", getTextureParamName(f));
        end();
        this.mgl.glTexParameterf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexParameterx(int i, int i2, int i3) throws IOException {
        begin("glTexParameterx");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg("param", i3);
        end();
        this.mgl.glTexParameterx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteriv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glTexParameteriv");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, 4, iArr, i3);
        end();
        this.mgl11.glTexParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteriv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glTexParameteriv");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, 4, intBuffer);
        end();
        this.mgl11.glTexParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Buffer buffer) throws IOException {
        begin("glTexSubImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("xoffset", i3);
        arg("yoffset", i4);
        arg("width", i5);
        arg("height", i6);
        arg(Telephony.CellBroadcasts.MESSAGE_FORMAT, i7);
        arg("type", i8);
        arg("pixels", buffer.toString());
        end();
        this.mgl.glTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTranslatef(float f, float f2, float f3) throws IOException {
        begin("glTranslatef");
        arg("x", f);
        arg("y", f2);
        arg("z", f3);
        end();
        this.mgl.glTranslatef(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTranslatex(int i, int i2, int i3) throws IOException {
        begin("glTranslatex");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        end();
        this.mgl.glTranslatex(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glVertexPointer(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glVertexPointer");
        argPointer(i, i2, i3, buffer);
        end();
        this.mVertexPointer = new PointerInfo(i, i2, i3, buffer);
        this.mgl.glVertexPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glViewport(int i, int i2, int i3, int i4) throws IOException {
        begin("glViewport");
        arg("x", i);
        arg("y", i2);
        arg("width", i3);
        arg("height", i4);
        end();
        this.mgl.glViewport(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanef(int i, float[] fArr, int i2) throws IOException {
        begin("glClipPlanef");
        arg("plane", i);
        arg("equation", 4, fArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glClipPlanef(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanef(int i, FloatBuffer floatBuffer) throws IOException {
        begin("glClipPlanef");
        arg("plane", i);
        arg("equation", 4, floatBuffer);
        end();
        this.mgl11.glClipPlanef(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanex(int i, int[] iArr, int i2) throws IOException {
        begin("glClipPlanex");
        arg("plane", i);
        arg("equation", 4, iArr, i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glClipPlanex(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanex(int i, IntBuffer intBuffer) throws IOException {
        begin("glClipPlanef");
        arg("plane", i);
        arg("equation", 4, intBuffer);
        end();
        this.mgl11.glClipPlanex(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfOES(float f, float f2, float f3, float f4, float f5) throws IOException {
        begin("glDrawTexfOES");
        arg("x", f);
        arg("y", f2);
        arg("z", f3);
        arg("width", f4);
        arg("height", f5);
        end();
        this.mgl11Ext.glDrawTexfOES(f, f2, f3, f4, f5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfvOES(float[] fArr, int i) throws IOException {
        begin("glDrawTexfvOES");
        arg("coords", 5, fArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexfvOES(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfvOES(FloatBuffer floatBuffer) throws IOException {
        begin("glDrawTexfvOES");
        arg("coords", 5, floatBuffer);
        end();
        this.mgl11Ext.glDrawTexfvOES(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexiOES(int i, int i2, int i3, int i4, int i5) throws IOException {
        begin("glDrawTexiOES");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        arg("width", i4);
        arg("height", i5);
        end();
        this.mgl11Ext.glDrawTexiOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexivOES(int[] iArr, int i) throws IOException {
        begin("glDrawTexivOES");
        arg("coords", 5, iArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexivOES(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexivOES(IntBuffer intBuffer) throws IOException {
        begin("glDrawTexivOES");
        arg("coords", 5, intBuffer);
        end();
        this.mgl11Ext.glDrawTexivOES(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsOES(short s, short s2, short s3, short s4, short s5) throws IOException {
        begin("glDrawTexsOES");
        arg("x", (int) s);
        arg("y", (int) s2);
        arg("z", (int) s3);
        arg("width", (int) s4);
        arg("height", (int) s5);
        end();
        this.mgl11Ext.glDrawTexsOES(s, s2, s3, s4, s5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsvOES(short[] sArr, int i) throws IOException {
        begin("glDrawTexsvOES");
        arg("coords", 5, sArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexsvOES(sArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsvOES(ShortBuffer shortBuffer) throws IOException {
        begin("glDrawTexsvOES");
        arg("coords", 5, shortBuffer);
        end();
        this.mgl11Ext.glDrawTexsvOES(shortBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxOES(int i, int i2, int i3, int i4, int i5) throws IOException {
        begin("glDrawTexxOES");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        arg("width", i4);
        arg("height", i5);
        end();
        this.mgl11Ext.glDrawTexxOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxvOES(int[] iArr, int i) throws IOException {
        begin("glDrawTexxvOES");
        arg("coords", 5, iArr, i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexxvOES(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxvOES(IntBuffer intBuffer) throws IOException {
        begin("glDrawTexxvOES");
        arg("coords", 5, intBuffer);
        end();
        this.mgl11Ext.glDrawTexxvOES(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public int glQueryMatrixxOES(int[] iArr, int i, int[] iArr2, int i2) throws IOException {
        begin("glQueryMatrixxOES");
        arg("mantissa", Arrays.toString(iArr));
        arg("exponent", Arrays.toString(iArr2));
        end();
        int iGlQueryMatrixxOES = this.mgl10Ext.glQueryMatrixxOES(iArr, i, iArr2, i2);
        returns(toString(16, 2, iArr, i));
        returns(toString(16, 0, iArr2, i2));
        checkError();
        return iGlQueryMatrixxOES;
    }

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public int glQueryMatrixxOES(IntBuffer intBuffer, IntBuffer intBuffer2) throws IOException {
        begin("glQueryMatrixxOES");
        arg("mantissa", intBuffer.toString());
        arg("exponent", intBuffer2.toString());
        end();
        int iGlQueryMatrixxOES = this.mgl10Ext.glQueryMatrixxOES(intBuffer, intBuffer2);
        returns(toString(16, 2, intBuffer));
        returns(toString(16, 0, intBuffer2));
        checkError();
        return iGlQueryMatrixxOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBindBuffer(int i, int i2) throws IOException {
        begin("glBindBuffer");
        arg("target", i);
        arg("buffer", i2);
        end();
        this.mgl11.glBindBuffer(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBufferData(int i, int i2, Buffer buffer, int i3) throws IOException {
        begin("glBufferData");
        arg("target", i);
        arg(Contract.DatabaseSize.PATH, i2);
        arg("data", buffer.toString());
        arg("usage", i3);
        end();
        this.mgl11.glBufferData(i, i2, buffer, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBufferSubData(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glBufferSubData");
        arg("target", i);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        arg(Contract.DatabaseSize.PATH, i3);
        arg("data", buffer.toString());
        end();
        this.mgl11.glBufferSubData(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glColor4ub(byte b, byte b2, byte b3, byte b4) throws IOException {
        begin("glColor4ub");
        arg("red", (int) b);
        arg("green", (int) b2);
        arg("blue", (int) b3);
        arg("alpha", (int) b4);
        end();
        this.mgl11.glColor4ub(b, b2, b3, b4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDeleteBuffers(int i, int[] iArr, int i2) throws IOException {
        begin("glDeleteBuffers");
        arg("n", i);
        arg("buffers", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glDeleteBuffers(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDeleteBuffers(int i, IntBuffer intBuffer) throws IOException {
        begin("glDeleteBuffers");
        arg("n", i);
        arg("buffers", intBuffer.toString());
        end();
        this.mgl11.glDeleteBuffers(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGenBuffers(int i, int[] iArr, int i2) throws IOException {
        begin("glGenBuffers");
        arg("n", i);
        arg("buffers", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGenBuffers(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGenBuffers(int i, IntBuffer intBuffer) throws IOException {
        begin("glGenBuffers");
        arg("n", i);
        arg("buffers", intBuffer.toString());
        end();
        this.mgl11.glGenBuffers(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBooleanv(int i, boolean[] zArr, int i2) throws IOException {
        begin("glGetBooleanv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(zArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetBooleanv(i, zArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBooleanv(int i, IntBuffer intBuffer) throws IOException {
        begin("glGetBooleanv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetBooleanv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBufferParameteriv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetBufferParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetBufferParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBufferParameteriv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetBufferParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetBufferParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanef(int i, float[] fArr, int i2) throws IOException {
        begin("glGetClipPlanef");
        arg("pname", i);
        arg("eqn", Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetClipPlanef(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanef(int i, FloatBuffer floatBuffer) throws IOException {
        begin("glGetClipPlanef");
        arg("pname", i);
        arg("eqn", floatBuffer.toString());
        end();
        this.mgl11.glGetClipPlanef(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanex(int i, int[] iArr, int i2) throws IOException {
        begin("glGetClipPlanex");
        arg("pname", i);
        arg("eqn", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetClipPlanex(i, iArr, i2);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanex(int i, IntBuffer intBuffer) throws IOException {
        begin("glGetClipPlanex");
        arg("pname", i);
        arg("eqn", intBuffer.toString());
        end();
        this.mgl11.glGetClipPlanex(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFixedv(int i, int[] iArr, int i2) throws IOException {
        begin("glGetFixedv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetFixedv(i, iArr, i2);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFixedv(int i, IntBuffer intBuffer) throws IOException {
        begin("glGetFixedv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetFixedv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFloatv(int i, float[] fArr, int i2) throws IOException {
        begin("glGetFloatv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetFloatv(i, fArr, i2);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFloatv(int i, FloatBuffer floatBuffer) throws IOException {
        begin("glGetFloatv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11.glGetFloatv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glGetLightfv");
        arg("light", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetLightfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glGetLightfv");
        arg("light", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11.glGetLightfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetLightxv");
        arg("light", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetLightxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetLightxv");
        arg("light", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetLightxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glGetMaterialfv");
        arg(Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetMaterialfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glGetMaterialfv");
        arg(Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11.glGetMaterialfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetMaterialxv");
        arg(Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetMaterialxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetMaterialxv");
        arg(Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetMaterialxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnviv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnviv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetTexEnviv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnvxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnvxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetTexEnvxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glGetTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexParameterfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glGetTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11.glGetTexParameterfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameteriv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetTexParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameteriv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetTexParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetTexParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexParameterxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glGetTexParameterxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsBuffer(int i) throws IOException {
        begin("glIsBuffer");
        arg("buffer", i);
        end();
        boolean zGlIsBuffer = this.mgl11.glIsBuffer(i);
        checkError();
        return zGlIsBuffer;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsEnabled(int i) throws IOException {
        begin("glIsEnabled");
        arg("cap", i);
        end();
        boolean zGlIsEnabled = this.mgl11.glIsEnabled(i);
        checkError();
        return zGlIsEnabled;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsTexture(int i) throws IOException {
        begin("glIsTexture");
        arg("texture", i);
        end();
        boolean zGlIsTexture = this.mgl11.glIsTexture(i);
        checkError();
        return zGlIsTexture;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterf(int i, float f) throws IOException {
        begin("glPointParameterf");
        arg("pname", i);
        arg("param", f);
        end();
        this.mgl11.glPointParameterf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterfv(int i, float[] fArr, int i2) throws IOException {
        begin("glPointParameterfv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glPointParameterfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterfv(int i, FloatBuffer floatBuffer) throws IOException {
        begin("glPointParameterfv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11.glPointParameterfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterx(int i, int i2) throws IOException {
        begin("glPointParameterfv");
        arg("pname", i);
        arg("param", i2);
        end();
        this.mgl11.glPointParameterx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterxv(int i, int[] iArr, int i2) throws IOException {
        begin("glPointParameterxv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glPointParameterxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterxv(int i, IntBuffer intBuffer) throws IOException {
        begin("glPointParameterxv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glPointParameterxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointSizePointerOES(int i, int i2, Buffer buffer) throws IOException {
        begin("glPointSizePointerOES");
        arg("type", i);
        arg("stride", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, buffer.toString());
        end();
        this.mgl11.glPointSizePointerOES(i, i2, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnvi(int i, int i2, int i3) throws IOException {
        begin("glTexEnvi");
        arg("target", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11.glTexEnvi(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnviv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glTexEnviv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnviv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glTexEnviv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glTexEnviv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11, javax.microedition.khronos.opengles.GL11Ext
    public void glTexParameterfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glTexParameterfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11.glTexParameterfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteri(int i, int i2, int i3) throws IOException {
        begin("glTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11.glTexParameteri(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glTexParameterxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11.glTexParameterxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glColorPointer(int i, int i2, int i3, int i4) throws IOException {
        begin("glColorPointer");
        arg(Contract.DatabaseSize.PATH, i);
        arg("type", i2);
        arg("stride", i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glColorPointer(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDrawElements(int i, int i2, int i3, int i4) throws IOException {
        begin("glDrawElements");
        arg("mode", i);
        arg(Contract.Events.Projection.COUNT_ONLY, i2);
        arg("type", i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glDrawElements(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetPointerv(int i, Buffer[] bufferArr) throws IOException {
        begin("glGetPointerv");
        arg("pname", i);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(bufferArr));
        end();
        this.mgl11.glGetPointerv(i, bufferArr);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glNormalPointer(int i, int i2, int i3) throws IOException {
        begin("glNormalPointer");
        arg("type", i);
        arg("stride", i2);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glNormalPointer(i, i2, i3);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexCoordPointer(int i, int i2, int i3, int i4) throws IOException {
        begin("glTexCoordPointer");
        arg(Contract.DatabaseSize.PATH, i);
        arg("type", i2);
        arg("stride", i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glTexCoordPointer(i, i2, i3, i4);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glVertexPointer(int i, int i2, int i3, int i4) throws IOException {
        begin("glVertexPointer");
        arg(Contract.DatabaseSize.PATH, i);
        arg("type", i2);
        arg("stride", i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glVertexPointer(i, i2, i3, i4);
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glCurrentPaletteMatrixOES(int i) throws IOException {
        begin("glCurrentPaletteMatrixOES");
        arg("matrixpaletteindex", i);
        end();
        this.mgl11Ext.glCurrentPaletteMatrixOES(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glLoadPaletteFromModelViewMatrixOES() throws IOException {
        begin("glLoadPaletteFromModelViewMatrixOES");
        end();
        this.mgl11Ext.glLoadPaletteFromModelViewMatrixOES();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glMatrixIndexPointerOES");
        argPointer(i, i2, i3, buffer);
        end();
        this.mgl11Ext.glMatrixIndexPointerOES(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, int i4) throws IOException {
        begin("glMatrixIndexPointerOES");
        arg(Contract.DatabaseSize.PATH, i);
        arg("type", i2);
        arg("stride", i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11Ext.glMatrixIndexPointerOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, Buffer buffer) throws IOException {
        begin("glWeightPointerOES");
        argPointer(i, i2, i3, buffer);
        end();
        this.mgl11Ext.glWeightPointerOES(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, int i4) throws IOException {
        begin("glWeightPointerOES");
        arg(Contract.DatabaseSize.PATH, i);
        arg("type", i2);
        arg("stride", i3);
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11Ext.glWeightPointerOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindFramebufferOES(int i, int i2) throws IOException {
        begin("glBindFramebufferOES");
        arg("target", i);
        arg("framebuffer", i2);
        end();
        this.mgl11ExtensionPack.glBindFramebufferOES(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindRenderbufferOES(int i, int i2) throws IOException {
        begin("glBindRenderbufferOES");
        arg("target", i);
        arg("renderbuffer", i2);
        end();
        this.mgl11ExtensionPack.glBindRenderbufferOES(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendEquation(int i) throws IOException {
        begin("glBlendEquation");
        arg("mode", i);
        end();
        this.mgl11ExtensionPack.glBlendEquation(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendEquationSeparate(int i, int i2) throws IOException {
        begin("glBlendEquationSeparate");
        arg("modeRGB", i);
        arg("modeAlpha", i2);
        end();
        this.mgl11ExtensionPack.glBlendEquationSeparate(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendFuncSeparate(int i, int i2, int i3, int i4) throws IOException {
        begin("glBlendFuncSeparate");
        arg("srcRGB", i);
        arg("dstRGB", i2);
        arg("srcAlpha", i3);
        arg("dstAlpha", i4);
        end();
        this.mgl11ExtensionPack.glBlendFuncSeparate(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public int glCheckFramebufferStatusOES(int i) throws IOException {
        begin("glCheckFramebufferStatusOES");
        arg("target", i);
        end();
        int iGlCheckFramebufferStatusOES = this.mgl11ExtensionPack.glCheckFramebufferStatusOES(i);
        checkError();
        return iGlCheckFramebufferStatusOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteFramebuffersOES(int i, int[] iArr, int i2) throws IOException {
        begin("glDeleteFramebuffersOES");
        arg("n", i);
        arg("framebuffers", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteFramebuffersOES(int i, IntBuffer intBuffer) throws IOException {
        begin("glDeleteFramebuffersOES");
        arg("n", i);
        arg("framebuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteRenderbuffersOES(int i, int[] iArr, int i2) throws IOException {
        begin("glDeleteRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteRenderbuffersOES(int i, IntBuffer intBuffer) throws IOException {
        begin("glDeleteRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glFramebufferRenderbufferOES(int i, int i2, int i3, int i4) throws IOException {
        begin("glFramebufferRenderbufferOES");
        arg("target", i);
        arg("attachment", i2);
        arg("renderbuffertarget", i3);
        arg("renderbuffer", i4);
        end();
        this.mgl11ExtensionPack.glFramebufferRenderbufferOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glFramebufferTexture2DOES(int i, int i2, int i3, int i4, int i5) throws IOException {
        begin("glFramebufferTexture2DOES");
        arg("target", i);
        arg("attachment", i2);
        arg("textarget", i3);
        arg("texture", i4);
        arg("level", i5);
        end();
        this.mgl11ExtensionPack.glFramebufferTexture2DOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenerateMipmapOES(int i) throws IOException {
        begin("glGenerateMipmapOES");
        arg("target", i);
        end();
        this.mgl11ExtensionPack.glGenerateMipmapOES(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenFramebuffersOES(int i, int[] iArr, int i2) throws IOException {
        begin("glGenFramebuffersOES");
        arg("n", i);
        arg("framebuffers", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glGenFramebuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenFramebuffersOES(int i, IntBuffer intBuffer) throws IOException {
        begin("glGenFramebuffersOES");
        arg("n", i);
        arg("framebuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGenFramebuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenRenderbuffersOES(int i, int[] iArr, int i2) throws IOException {
        begin("glGenRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenRenderbuffersOES(int i, IntBuffer intBuffer) throws IOException {
        begin("glGenRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, int[] iArr, int i4) throws IOException {
        begin("glGetFramebufferAttachmentParameterivOES");
        arg("target", i);
        arg("attachment", i2);
        arg("pname", i3);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(i, i2, i3, iArr, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, IntBuffer intBuffer) throws IOException {
        begin("glGetFramebufferAttachmentParameterivOES");
        arg("target", i);
        arg("attachment", i2);
        arg("pname", i3);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(i, i2, i3, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetRenderbufferParameterivOES(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetRenderbufferParameterivOES");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetRenderbufferParameterivOES(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetRenderbufferParameterivOES");
        arg("target", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glGetTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetTexGenfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glGetTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetTexGenfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGeniv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetTexGeniv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGeniv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetTexGeniv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glGetTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetTexGenxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glGetTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetTexGenxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public boolean glIsFramebufferOES(int i) throws IOException {
        begin("glIsFramebufferOES");
        arg("framebuffer", i);
        end();
        boolean zGlIsFramebufferOES = this.mgl11ExtensionPack.glIsFramebufferOES(i);
        checkError();
        return zGlIsFramebufferOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public boolean glIsRenderbufferOES(int i) throws IOException {
        begin("glIsRenderbufferOES");
        arg("renderbuffer", i);
        end();
        this.mgl11ExtensionPack.glIsRenderbufferOES(i);
        checkError();
        return false;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glRenderbufferStorageOES(int i, int i2, int i3, int i4) throws IOException {
        begin("glRenderbufferStorageOES");
        arg("target", i);
        arg("internalformat", i2);
        arg("width", i3);
        arg("height", i4);
        end();
        this.mgl11ExtensionPack.glRenderbufferStorageOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenf(int i, int i2, float f) throws IOException {
        begin("glTexGenf");
        arg("coord", i);
        arg("pname", i2);
        arg("param", f);
        end();
        this.mgl11ExtensionPack.glTexGenf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenfv(int i, int i2, float[] fArr, int i3) throws IOException {
        begin("glTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(fArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glTexGenfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenfv(int i, int i2, FloatBuffer floatBuffer) throws IOException {
        begin("glTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, floatBuffer.toString());
        end();
        this.mgl11ExtensionPack.glTexGenfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeni(int i, int i2, int i3) throws IOException {
        begin("glTexGeni");
        arg("coord", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11ExtensionPack.glTexGeni(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeniv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glTexGeniv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeniv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glTexGeniv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenx(int i, int i2, int i3) throws IOException {
        begin("glTexGenx");
        arg("coord", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11ExtensionPack.glTexGenx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenxv(int i, int i2, int[] iArr, int i3) throws IOException {
        begin("glTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, Arrays.toString(iArr));
        arg(CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glTexGenxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenxv(int i, int i2, IntBuffer intBuffer) throws IOException {
        begin("glTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg(KnoxZtInternalConst.Event.LogKeys.PARAMS, intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glTexGenxv(i, i2, intBuffer);
        checkError();
    }

    private class PointerInfo {
        public Buffer mPointer;
        public int mSize;
        public int mStride;
        public ByteBuffer mTempByteBuffer;
        public int mType;

        public int sizeof(int i) {
            if (i == 5126 || i == 5132) {
                return 4;
            }
            switch (i) {
                case 5120:
                case 5121:
                    return 1;
                case 5122:
                    return 2;
                default:
                    return 0;
            }
        }

        public PointerInfo() {
        }

        public PointerInfo(int i, int i2, int i3, Buffer buffer) {
            this.mSize = i;
            this.mType = i2;
            this.mStride = i3;
            this.mPointer = buffer;
        }

        public int getStride() {
            int i = this.mStride;
            return i > 0 ? i : sizeof(this.mType) * this.mSize;
        }

        public void bindByteBuffer() {
            Buffer buffer = this.mPointer;
            this.mTempByteBuffer = buffer == null ? null : GLLogWrapper.this.toByteBuffer(-1, buffer);
        }

        public void unbindByteBuffer() {
            this.mTempByteBuffer = null;
        }
    }
}
