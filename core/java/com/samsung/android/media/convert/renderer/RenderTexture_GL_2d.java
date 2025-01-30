package com.samsung.android.media.convert.renderer;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import com.samsung.android.media.convert.util.Constants;
import com.samsung.android.media.convert.util.OpenGlHelper;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes5.dex */
public class RenderTexture_GL_2d {
  private static final String A_POSITION = "a_Position";
  private static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
  private static final int FLOAT_SIZE_BYTES = 4;
  public static final int PREPARE_FAILURE = 0;
  public static final int PREPARE_SUCCESS = 1;
  private static final String TEXTURE_FRAGMENT_SHADER_CODE =
      "precision mediump float;\n"
          + "uniform sampler2D u_TextureUnit;\n"
          + "varying vec2 v_TextureCoordinates;\n"
          + "void main(){ \n"
          + "gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);\n"
          + "}\n";
  private static final String TEXTURE_VERTEX_SHADER_CODE =
      "uniform mat4 u_Matrix;\n"
          + "attribute vec4 a_Position;\n"
          + "attribute vec2 a_TextureCoordinates;\n"
          + "varying vec2 v_TextureCoordinates;\n"
          + "void main(){\n"
          + "v_TextureCoordinates = a_TextureCoordinates;\n"
          + "gl_Position =u_Matrix*a_Position;\n"
          + "}\n";
  private static final String U_MATRIX = "u_Matrix";
  private static final String U_TEXTURE_UNIT = "u_TextureUnit";
  private static final int VERTICES_DATA_POS_COUNT = 2;
  private static final int VERTICES_DATA_POS_OFFSET = 0;
  private static final int VERTICES_DATA_STRIDE_BYTES = 16;
  private static final int VERTICES_DATA_TEX_COORD_COUNT = 2;
  private static final int VERTICES_DATA_TEX_COORD_OFFSET = 2;
  private int mProgram;
  private int mTextureId;
  private final float[] mVerticesData;
  private FloatBuffer mVerticesFloatBuffer;
  private int ma_PositionHandle;
  private int ma_TextureCoordinatesHandle;
  private int mu_MatrixHandle;
  private int mu_TextureUnitHandle;
  private final float[] projectionMatrix = new float[16];

  public RenderTexture_GL_2d() {
    float[] fArr = {
      -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
      0.0f
    };
    this.mVerticesData = fArr;
    FloatBuffer put =
        ByteBuffer.allocateDirect(fArr.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(fArr);
    this.mVerticesFloatBuffer = put;
    put.position(0);
  }

  public int prepare() {
    int createProgram =
        OpenGlHelper.createProgram(TEXTURE_VERTEX_SHADER_CODE, TEXTURE_FRAGMENT_SHADER_CODE);
    this.mProgram = createProgram;
    if (createProgram == 0) {
      return 0;
    }
    this.mu_MatrixHandle = GLES20.glGetUniformLocation(createProgram, U_MATRIX);
    this.ma_PositionHandle = GLES20.glGetAttribLocation(this.mProgram, A_POSITION);
    this.ma_TextureCoordinatesHandle =
        GLES20.glGetAttribLocation(this.mProgram, A_TEXTURE_COORDINATES);
    this.mu_TextureUnitHandle = GLES20.glGetUniformLocation(this.mProgram, U_TEXTURE_UNIT);
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    return 1;
  }

  public void draw() {
    GLES20.glUseProgram(this.mProgram);
    GLES20.glUniformMatrix4fv(this.mu_MatrixHandle, 1, false, this.projectionMatrix, 0);
    GLES20.glActiveTexture(33984);
    GLES20.glBindTexture(3553, this.mTextureId);
    GLES20.glUniform1i(this.mu_TextureUnitHandle, 0);
    this.mVerticesFloatBuffer.position(0);
    GLES20.glVertexAttribPointer(
        this.ma_PositionHandle, 2, 5126, false, 16, (Buffer) this.mVerticesFloatBuffer);
    GLES20.glEnableVertexAttribArray(this.ma_PositionHandle);
    OpenGlHelper.checkGLError("glEnableVertexAttribArray ma_PositionHandle");
    this.mVerticesFloatBuffer.position(2);
    GLES20.glVertexAttribPointer(
        this.ma_TextureCoordinatesHandle, 2, 5126, false, 16, (Buffer) this.mVerticesFloatBuffer);
    GLES20.glEnableVertexAttribArray(this.ma_TextureCoordinatesHandle);
    OpenGlHelper.checkGLError("glEnableVertexAttribArray ma_TextureCoordinatesHandle");
    GLES20.glEnable(3042);
    GLES20.glBlendFunc(770, 771);
    GLES20.glDrawArrays(5, 0, 4);
    Log.m94d(Constants.TAG, "Calling glFinish blocking call");
    GLES20.glFinish();
    Log.m94d(Constants.TAG, "Finished glFinish");
  }

  public int loadTexture(Bitmap textureBitmap, int outW, int outH) {
    if (this.mTextureId != 0) {
      deleteTexture();
    }
    Rect decodedImageDim = new Rect();
    decodedImageDim.left = 0;
    decodedImageDim.top = 0;
    decodedImageDim.right = textureBitmap.getWidth();
    decodedImageDim.bottom = textureBitmap.getHeight();
    int loadTexture = OpenGlHelper.loadTexture(textureBitmap);
    this.mTextureId = loadTexture;
    if (loadTexture == 0) {
      Log.m94d(Constants.TAG, "not able to load new texture");
    }
    float correspondingWRatio = decodedImageDim.width() / outW;
    float correspondingHRatio = decodedImageDim.height() / outH;
    float scaleX = 1.0f;
    float scaleY = 1.0f;
    if (correspondingWRatio >= correspondingHRatio) {
      scaleY = correspondingHRatio / correspondingWRatio;
    } else {
      scaleX = correspondingWRatio / correspondingHRatio;
    }
    Matrix.setIdentityM(this.projectionMatrix, 0);
    Matrix.scaleM(this.projectionMatrix, 0, scaleX, scaleY, 1.0f);
    return this.mTextureId;
  }

  public int loadTexture(String inputImagePath, int outW, int outH) throws IOException {
    if (this.mTextureId != 0) {
      deleteTexture();
    }
    Rect decodedImageDim = new Rect();
    int loadTexture = OpenGlHelper.loadTexture(inputImagePath, outW, outH, decodedImageDim);
    this.mTextureId = loadTexture;
    if (loadTexture == 0) {
      Log.m94d(Constants.TAG, "not able to load new texture");
    }
    float correspondingWRatio = decodedImageDim.width() / outW;
    float correspondingHRatio = decodedImageDim.height() / outH;
    float scaleX = 1.0f;
    float scaleY = 1.0f;
    if (correspondingWRatio >= correspondingHRatio) {
      scaleY = correspondingHRatio / correspondingWRatio;
    } else {
      scaleX = correspondingWRatio / correspondingHRatio;
    }
    Matrix.setIdentityM(this.projectionMatrix, 0);
    Matrix.scaleM(this.projectionMatrix, 0, scaleX, scaleY, 1.0f);
    return this.mTextureId;
  }

  public void setProjectionMatrixScale(float scaleX, float scaleY) {
    Matrix.scaleM(this.projectionMatrix, 0, scaleX, scaleY, 1.0f);
  }

  public void setProjectionMatrixTranslate(float transX, float transY) {
    Matrix.translateM(this.projectionMatrix, 0, transX, transY, 0.0f);
  }

  public void setProjectionMatrixRotate(float angle, float rotateX, float rotateY, float rotateZ) {
    Matrix.rotateM(this.projectionMatrix, 0, angle, rotateX, rotateY, rotateZ);
  }

  private void deleteTexture() {
    GLES20.glBindTexture(3553, 0);
    OpenGlHelper.deleteTexture(this.mTextureId);
    this.mTextureId = 0;
  }

  public void release() {
    deleteTexture();
    GLES20.glDeleteProgram(this.mProgram);
    this.mProgram = 0;
    this.mu_MatrixHandle = 0;
    this.ma_PositionHandle = 0;
    this.ma_TextureCoordinatesHandle = 0;
    this.mu_TextureUnitHandle = 0;
    this.mVerticesFloatBuffer = null;
  }
}
