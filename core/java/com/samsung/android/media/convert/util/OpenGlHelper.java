package com.samsung.android.media.convert.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes5.dex */
public class OpenGlHelper {
  public static int createProgram(String vertexShaderSource, String fragmentShaderSource) {
    int vertexShaderId = compileVertexShader(vertexShaderSource);
    if (vertexShaderId == 0) {
      return 0;
    }
    int fragmentShaderId = compileFragmentShader(fragmentShaderSource);
    if (fragmentShaderId == 0) {
      GLES20.glDeleteShader(vertexShaderId);
      return 0;
    }
    int programId = linkProgram(vertexShaderId, fragmentShaderId);
    GLES20.glDeleteShader(vertexShaderId);
    GLES20.glDeleteShader(vertexShaderId);
    return programId;
  }

  private static int compileVertexShader(String vertexShaderCode) {
    return compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
  }

  private static int compileFragmentShader(String fragmentShaderCode) {
    return compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
  }

  private static int compileShader(int shaderType, String shaderSource) {
    int shaderId = GLES20.glCreateShader(shaderType);
    if (shaderId == 0) {
      checkGLError("shader type " + shaderType + " creation failded");
      return 0;
    }
    GLES20.glShaderSource(shaderId, shaderSource);
    GLES20.glCompileShader(shaderId);
    int[] compileStatus = new int[1];
    GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
    if (compileStatus[0] == 0) {
      Log.m96e(
          Constants.TAG,
          "Could not compile shader " + shaderType + " " + GLES20.glGetShaderInfoLog(shaderId));
      GLES20.glDeleteShader(shaderId);
      return 0;
    }
    return shaderId;
  }

  private static int linkProgram(int vertexShaderId, int fragmentShaderId) {
    int programId = GLES20.glCreateProgram();
    if (programId == 0) {
      checkGLError("CreateProgram failed");
      return 0;
    }
    GLES20.glAttachShader(programId, vertexShaderId);
    GLES20.glAttachShader(programId, fragmentShaderId);
    GLES20.glLinkProgram(programId);
    int[] linkStatus = new int[1];
    GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
    if (linkStatus[0] == 0) {
      Log.m96e(Constants.TAG, "Couldn't link program :" + GLES20.glGetProgramInfoLog(programId));
      GLES20.glDeleteProgram(programId);
      return 0;
    }
    return programId;
  }

  public static int loadTexture(String path, int outW, int outH, Rect decodedImageDimen)
      throws IOException {
    ExifInterface exifData = new ExifInterface(path);
    int orientation = exifData.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
    BitmapFactory.Options option = new BitmapFactory.Options();
    option.inSampleSize = getOptimalSamplingSize(path, outW, outH, orientation);
    Bitmap bitmap = BitmapFactory.decodeFile(path, option);
    if (bitmap == null) {
      Log.m96e(Constants.TAG, "Could not decode bitmap. error.");
      return 0;
    }
    decodedImageDimen.left = 0;
    decodedImageDimen.top = 0;
    decodedImageDimen.right = bitmap.getWidth();
    decodedImageDimen.bottom = bitmap.getHeight();
    int textureId = loadTexture(bitmap);
    bitmap.recycle();
    return textureId;
  }

  private static int getOptimalSamplingSize(String path, int outW, int outH, int orientation)
      throws IOException {
    BitmapFactory.Options option = new BitmapFactory.Options();
    option.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, option);
    float width = option.outWidth;
    float height = option.outHeight;
    float correspondingWRatio = width / outW;
    float correspondingHRatio = height / outH;
    int optimalSampleVal =
        Math.round(
            correspondingWRatio >= correspondingHRatio ? correspondingWRatio : correspondingHRatio);
    return optimalSampleVal;
  }

  public static int loadTexture(Bitmap bitmap) {
    int[] textureObjectIds = new int[1];
    GLES20.glGenTextures(1, textureObjectIds, 0);
    if (textureObjectIds[0] == 0) {
      Log.m96e(Constants.TAG, "Could not create new opengl texture object");
      return 0;
    }
    GLES20.glBindTexture(3553, textureObjectIds[0]);
    if (checkGLError("glBindTexture error") != 0) {
      GLES20.glDeleteTextures(1, textureObjectIds, 0);
      return 0;
    }
    GLES20.glTexParameteri(3553, 10241, 9729);
    GLES20.glTexParameteri(3553, 10240, 9729);
    GLES20.glTexParameterf(3553, 10242, 33071.0f);
    GLES20.glTexParameterf(3553, 10243, 33071.0f);
    if (checkGLError("glTexParameter error") != 0) {
      GLES20.glDeleteTextures(1, textureObjectIds, 0);
      return 0;
    }
    GLUtils.texImage2D(3553, 0, bitmap, 0);
    if (checkGLError("texImage2D error") != 0) {
      GLES20.glDeleteTextures(1, textureObjectIds, 0);
      return 0;
    }
    GLES20.glGenerateMipmap(3553);
    GLES20.glBindTexture(3553, 0);
    return textureObjectIds[0];
  }

  public static int loadTextureOES() {
    int[] textureObjectIds = new int[1];
    GLES20.glGenTextures(1, textureObjectIds, 0);
    if (textureObjectIds[0] == 0) {
      Log.m96e(Constants.TAG, "Could not create new opengl oes texture object");
      return 0;
    }
    GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureObjectIds[0]);
    if (checkGLError("glBindTexture error") != 0) {
      GLES20.glDeleteTextures(1, textureObjectIds, 0);
      return 0;
    }
    GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10241, 9729.0f);
    GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10240, 9729.0f);
    GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10242, 33071);
    GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10243, 33071);
    if (checkGLError("External OES parameter set error.") != 0) {
      GLES20.glDeleteTextures(1, textureObjectIds, 0);
      return 0;
    }
    return textureObjectIds[0];
  }

  public static void deleteTexture(int textureId) {
    GLES20.glDeleteTextures(1, new int[] {textureId}, 0);
  }

  public static void deleteTexture(int[] texturesId) {
    GLES20.glDeleteTextures(1, texturesId, 0);
  }

  public static int checkGLError(String operation) {
    while (true) {
      int error = GLES20.glGetError();
      if (error == 0) {
        return error;
      }
      Log.m96e(Constants.TAG, operation + ": glError " + error);
    }
  }
}
