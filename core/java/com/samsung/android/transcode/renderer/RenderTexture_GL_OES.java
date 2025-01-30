package com.samsung.android.transcode.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.p009os.SemSystemProperties;
import com.samsung.android.transcode.util.LogS;
import com.samsung.android.transcode.util.OpenGlHelper;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes5.dex */
public class RenderTexture_GL_OES {
    private static final String A_POSITION = "a_Position";
    private static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    private static final String BLUR_FRAGMENT_SHADER_CODE = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 v_TextureCoord;\nuniform samplerExternalOES u_TextureUnit;\nuniform float fWidth;\nuniform float fHeight;\nvoid main()\t\t\t\t\t\t\t\t\t\t \n        {\n  vec4 lightColor; \t\t\t\t\t\t\t\t \n\thighp vec4 color = vec4(0,0,0,1);\t\t\n\thighp vec2 gaussFilter[7];\t\t\t\t\n\t gaussFilter[0] = vec2(-3.0, 0.1063);\t \n\t gaussFilter[1] = vec2(-2.0, 0.1403);\t \n\t gaussFilter[2] = vec2(-1.0, 0.1658);\t \n\t gaussFilter[3] = vec2(0.0, 0.1752); \n\t gaussFilter[4] = vec2(1.0, 0.1658);\t\n\t gaussFilter[5] = vec2(2.0, 0.1403);\t\n\t gaussFilter[6] = vec2(3.0, 0.1063);\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\n\tfor( int i = 0; i < 7; i++ )\t\t\n\t\tcolor += texture2D(u_TextureUnit, vec2( v_TextureCoord.x+gaussFilter[i].x*fWidth, v_TextureCoord.y+gaussFilter[i].x*fHeight))*gaussFilter[i].y;\t\t\n  gl_FragColor = color ;\t\n}\n";
    private static final int FLOAT_SIZE_BYTES = 4;
    public static final int PREPARE_FAILURE = 0;
    public static final int PREPARE_SUCCESS = 1;
    private static final String TEXTURE_FRAGMENT_SHADER_CODE = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 v_TextureCoord;\nuniform samplerExternalOES u_TextureUnit;\nvoid main() {\n  gl_FragColor = texture2D(u_TextureUnit, v_TextureCoord);\n}\n";
    private static final String TEXTURE_VERTEX_SHADER_CODE = "uniform mat4 u_MVPMatrix;\nuniform mat4 u_STMatrix;\nattribute vec4 a_Position;\nattribute vec4 a_TextureCoordinates;\nvarying vec2 v_TextureCoord;\nvoid main() {\n  gl_Position = u_MVPMatrix * a_Position;\n  v_TextureCoord = (u_STMatrix * a_TextureCoordinates).xy;\n}\n";
    private static final String U_FRMAE_HEIGHT = "fHegiht";
    private static final String U_FRMAE_WIDTH = "fWidth";
    private static final String U_MVPMATRIX = "u_MVPMatrix";
    private static final String U_STMATRIX = "u_STMatrix";
    private static final String U_TEXTURE_UNIT = "u_TextureUnit";
    private static final int VERTICES_DATA_POS_COUNT = 2;
    private static final int VERTICES_DATA_POS_OFFSET = 0;
    private static final int VERTICES_DATA_STRIDE_BYTES = 16;
    private static final int VERTICES_DATA_TEX_COORD_COUNT = 2;
    private static final int VERTICES_DATA_TEX_COORD_OFFSET = 2;
    private float mHeight;
    private int mProgram;
    private int mTextureId;
    private final float[] mVerticesData;
    private FloatBuffer mVerticesFloatBuffer;
    private float mWidth;
    private int ma_PositionHandle;
    private int ma_TextureCoordinatesHandle;
    private int mu_FheightHandle;
    private int mu_FwidthHandle;
    private int mu_MVPMatrixHandle;
    private int mu_STMatrixHandle;
    private int mu_TextureUnitHandle;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mSTMatrix = new float[16];
    private boolean mMMSMode = false;
    private boolean mCallFinish = true;

    public RenderTexture_GL_OES() {
        float[] fArr = {-1.0f, -1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
        this.mVerticesData = fArr;
        FloatBuffer put = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr);
        this.mVerticesFloatBuffer = put;
        put.position(0);
    }

    public int createProgram(boolean isBlurMode) {
        if (isBlurMode) {
            this.mProgram = OpenGlHelper.createProgram(TEXTURE_VERTEX_SHADER_CODE, BLUR_FRAGMENT_SHADER_CODE);
        } else {
            this.mProgram = OpenGlHelper.createProgram(TEXTURE_VERTEX_SHADER_CODE, TEXTURE_FRAGMENT_SHADER_CODE);
        }
        int i = this.mProgram;
        if (i == 0) {
            return 0;
        }
        this.mu_MVPMatrixHandle = GLES20.glGetUniformLocation(i, U_MVPMATRIX);
        this.mu_STMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, U_STMATRIX);
        this.ma_PositionHandle = GLES20.glGetAttribLocation(this.mProgram, A_POSITION);
        this.ma_TextureCoordinatesHandle = GLES20.glGetAttribLocation(this.mProgram, A_TEXTURE_COORDINATES);
        this.mu_TextureUnitHandle = GLES20.glGetUniformLocation(this.mProgram, U_TEXTURE_UNIT);
        return 1;
    }

    public int prepare(int rotationAngle) {
        if (createProgram(false) == 0) {
            return 0;
        }
        loadTexture(rotationAngle);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        return 1;
    }

    public int prepare(int rotationAngle, int x, int y, int width, int height, int original_width, int original_height, boolean mmsMode, int pbuffer_width, int pbuffer_height) {
        this.mMMSMode = mmsMode;
        this.mCallFinish = checkcallFinish();
        if (createProgram(this.mMMSMode) == 0) {
            return 0;
        }
        if (this.mMMSMode) {
            this.mu_FwidthHandle = GLES20.glGetUniformLocation(this.mProgram, U_FRMAE_WIDTH);
            this.mu_FheightHandle = GLES20.glGetUniformLocation(this.mProgram, U_FRMAE_HEIGHT);
            this.mWidth = original_width;
            this.mHeight = original_height;
            if (pbuffer_width != 0 && pbuffer_height != 0) {
                this.mWidth = pbuffer_width;
                this.mHeight = pbuffer_height;
            }
        }
        loadTexture(rotationAngle);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glViewport(x, y, width, height);
        return 1;
    }

    public void draw(SurfaceTexture st) {
        st.getTransformMatrix(this.mSTMatrix);
        GLES20.glUseProgram(this.mProgram);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, this.mTextureId);
        GLES20.glUniformMatrix4fv(this.mu_MVPMatrixHandle, 1, false, this.mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(this.mu_STMatrixHandle, 1, false, this.mSTMatrix, 0);
        GLES20.glUniform1i(this.mu_TextureUnitHandle, 0);
        if (this.mMMSMode) {
            GLES20.glUniform1f(this.mu_FwidthHandle, 1.0f / this.mWidth);
            GLES20.glUniform1f(this.mu_FheightHandle, 1.0f / this.mHeight);
        }
        this.mVerticesFloatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.ma_PositionHandle, 2, 5126, false, 16, (Buffer) this.mVerticesFloatBuffer);
        GLES20.glEnableVertexAttribArray(this.ma_PositionHandle);
        OpenGlHelper.checkGLError("glEnableVertexAttribArray ma_PositionHandle");
        this.mVerticesFloatBuffer.position(2);
        GLES20.glVertexAttribPointer(this.ma_TextureCoordinatesHandle, 2, 5126, false, 16, (Buffer) this.mVerticesFloatBuffer);
        GLES20.glEnableVertexAttribArray(this.ma_TextureCoordinatesHandle);
        OpenGlHelper.checkGLError("glEnableVertexAttribArray ma_TextureCoordinatesHandle");
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        GLES20.glDrawArrays(5, 0, 4);
        if (this.mCallFinish) {
            GLES20.glFinish();
        }
    }

    public int loadTexture(int rotationAngle) {
        if (this.mTextureId != 0) {
            deleteTexture();
        }
        int loadTextureOES = OpenGlHelper.loadTextureOES();
        this.mTextureId = loadTextureOES;
        if (loadTextureOES == 0) {
            LogS.m473d("TranscodeLib", "not able to load new texture");
        }
        Matrix.setIdentityM(this.mMVPMatrix, 0);
        Matrix.setRotateM(this.mMVPMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
        return this.mTextureId;
    }

    private void deleteTexture() {
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        OpenGlHelper.deleteTexture(this.mTextureId);
        this.mTextureId = 0;
    }

    public int getTextureId() {
        return this.mTextureId;
    }

    public void release() {
        deleteTexture();
        GLES20.glDeleteProgram(this.mProgram);
        this.mProgram = 0;
        this.mu_MVPMatrixHandle = 0;
        this.mu_STMatrixHandle = 0;
        this.ma_PositionHandle = 0;
        this.ma_TextureCoordinatesHandle = 0;
        this.mu_TextureUnitHandle = 0;
        this.mVerticesFloatBuffer = null;
        this.mu_FwidthHandle = 0;
        this.mu_FheightHandle = 0;
        this.mMMSMode = false;
    }

    private boolean checkcallFinish() {
        String chipName = SemSystemProperties.get("ro.product.board").toLowerCase();
        if (chipName.contains("exynos") || chipName.contains("s5e")) {
            LogS.m473d("TranscodeLib", "checkcallFinish  chipName :" + chipName + ", return false");
            return false;
        }
        LogS.m473d("TranscodeLib", "checkcallFinish  chipName :" + chipName + ", return true");
        return true;
    }
}
