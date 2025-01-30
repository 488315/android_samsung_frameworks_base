package com.samsung.android.nexus.video;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.text.TextUtils;
import android.view.Surface;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.egl.core.Shader;
import com.samsung.android.nexus.egl.utils.EglUtils;
import com.samsung.android.nexus.egl.world.BaseWorld;
import com.samsung.android.nexus.egl.world.WorldOrthographic;
import com.sec.ims.configuration.DATA;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class VideoGL {
    public static final float DEFAULT_HSV_HUE = 0.0f;
    public static final float DEFAULT_HSV_SATURATION = 0.5f;
    public static final float DEFAULT_HSV_VALUE = 0.5f;
    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int GL_TEXTURE_EXTERNAL_OES = 36197;
    private static final int POSITION_OFFSET = 0;
    private static final int STRIDE_SIZE = 20;
    private static final String TAG = "VideoGL";
    private static final int TEXTURE_COORD_OFFSET = 3;
    private static final int TEXTURE_UV_OFFSET = 2;
    private static final int TOTAL_COMPONENT_COUNT = 5;
    protected float mAngle;
    protected RectF mBoundRect;
    private Context mContext;
    private float mContrast;
    private VideoFrameController mFrameController;
    private float mGlobalAlpha;
    private float mHdrSaturationConverted;
    private float[] mHsvFilterColor;
    private float[] mHsvFilterColorConverted;
    private boolean mIsHdrModeEnabled;
    private boolean mIsTransparencyEnabled;
    private PointF mObjectCenter;
    private float mObjectHalfHeight;
    private float mObjectHalfWidth;
    private float[] mObjectLeftBottom;
    private float[] mObjectLeftTop;
    protected RectF mObjectRect;
    private float[] mObjectRightBottom;
    private float[] mObjectRightTop;
    protected float mOffsetX;
    protected float mOffsetY;
    protected float mOffsetZ;
    private VideoPlayer mPlayer;
    private float[] mRgbFilterColor;
    private float[] mRotatedLeftBottom;
    private float[] mRotatedLeftTop;
    private float[] mRotatedRightBottom;
    private float[] mRotatedRightTop;
    protected float[] mRotationMatrix;
    protected float mRotationX;
    protected float mRotationY;
    protected float mRotationZ;
    protected float[] mSTMatrix;
    private Shader mShader;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private int mTextureID;
    private final float[] mTriangleVerticesData;
    private FloatBuffer mVertices;
    private BaseWorld mWorld;
    private float mWorldHeight;
    private float mWorldWidth;

    /* renamed from: mZ */
    protected float f629mZ;
    private int maPositionHandle;
    private int maTextureHandle;
    private int muContrastHandle;
    private int muGlobalAlphaHandle;
    private int muHsvFilterColorHandle;
    private int muNightFilterHandle;
    private int muRgbFilterColorHandle;
    private int muSTMatrixHandle;
    private int muTransparencyHandle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class VideoFrameController {
        static final boolean DEBUG = false;
        static final int UPDATE_COUNT_LIMIT = 1000000;
        int needUpdateCount = 0;
        int updatedCount = 0;

        public VideoFrameController() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSurface() {
            while (hasFrame()) {
                updateTexImage();
            }
        }

        private void updateTexImage() {
            try {
                try {
                    VideoGL.this.mSurfaceTexture.updateTexImage();
                    VideoGL.this.mSurfaceTexture.getTransformMatrix(VideoGL.this.mSTMatrix);
                    EglUtils.checkGlError("updateTexImage");
                } catch (RuntimeException e) {
                    Log.m261e(VideoGL.TAG, "Cannot update surface. need to update count = " + this.needUpdateCount + ", error = " + e.getMessage());
                }
            } finally {
                frameConsumed();
            }
        }

        public void frameConsumed() {
            int i = this.updatedCount + 1;
            this.updatedCount = i;
            if (i >= UPDATE_COUNT_LIMIT) {
                this.updatedCount = 1;
            }
        }

        public void frameUpdated() {
            int i = this.needUpdateCount + 1;
            this.needUpdateCount = i;
            if (i >= UPDATE_COUNT_LIMIT) {
                this.needUpdateCount = 1;
            }
        }

        public boolean hasFrame() {
            int i = this.needUpdateCount;
            return (i == 0 || this.updatedCount == i) ? false : true;
        }
    }

    public VideoGL(Context context, VideoPlayer videoPlayer) {
        this(context, videoPlayer, null);
    }

    private void applySize(float f, float f2, int i) {
        RectF rectF = this.mObjectRect;
        if (rectF == null) {
            Log.m262i(TAG, "Object size is null. So cannot apply the size.");
            return;
        }
        float abs = Math.abs(rectF.width());
        float abs2 = Math.abs(this.mObjectRect.height());
        if (i == 0) {
            float f3 = (f - abs) / 2.0f;
            RectF rectF2 = this.mObjectRect;
            rectF2.left -= f3;
            rectF2.top += f3;
            rectF2.right += f3;
            rectF2.bottom -= (f2 - abs2) / 2.0f;
        } else {
            RectF rectF3 = this.mObjectRect;
            rectF3.right = rectF3.left + f;
            rectF3.bottom = rectF3.top - f2;
        }
        calculateObjectFactors();
        updateVertices();
    }

    private void calculateBoundsOffset() {
        RectF rectF;
        if (this.mBoundRect == null || (rectF = this.mObjectRect) == null) {
            return;
        }
        setOffset(((Math.abs(rectF.width()) - this.mWorldWidth) / 2.0f) + this.mBoundRect.left, (((Math.abs(this.mObjectRect.height()) - this.mWorldHeight) / 2.0f) + this.mBoundRect.top) * (-1.0f), 0.0f);
    }

    private void calculateObjectFactors() {
        RectF rectF = this.mObjectRect;
        if (rectF == null) {
            return;
        }
        this.mObjectHalfWidth = Math.abs(rectF.width()) / 2.0f;
        float abs = Math.abs(this.mObjectRect.height()) / 2.0f;
        this.mObjectHalfHeight = abs;
        float f = this.mObjectHalfWidth;
        this.mObjectLeftTop = new float[]{-f, abs, 0.0f, 0.0f};
        this.mObjectRightTop = new float[]{f, abs, 0.0f, 0.0f};
        this.mObjectLeftBottom = new float[]{-f, -abs, 0.0f, 0.0f};
        this.mObjectRightBottom = new float[]{f, -abs, 0.0f, 0.0f};
        PointF pointF = this.mObjectCenter;
        RectF rectF2 = this.mObjectRect;
        pointF.x = (rectF2.left + rectF2.right) / 2.0f;
        pointF.y = (rectF2.top + rectF2.bottom) / 2.0f;
    }

    private void calculateRotatedFactors() {
        if (this.mObjectRect == null) {
            return;
        }
        if (this.mAngle != 0.0f) {
            Arrays.fill(this.mRotatedLeftTop, 0.0f);
            Arrays.fill(this.mRotatedRightTop, 0.0f);
            Arrays.fill(this.mRotatedLeftBottom, 0.0f);
            Arrays.fill(this.mRotatedRightBottom, 0.0f);
            Matrix.multiplyMV(this.mRotatedLeftTop, 0, this.mRotationMatrix, 0, this.mObjectLeftTop, 0);
            Matrix.multiplyMV(this.mRotatedRightTop, 0, this.mRotationMatrix, 0, this.mObjectRightTop, 0);
            Matrix.multiplyMV(this.mRotatedLeftBottom, 0, this.mRotationMatrix, 0, this.mObjectLeftBottom, 0);
            Matrix.multiplyMV(this.mRotatedRightBottom, 0, this.mRotationMatrix, 0, this.mObjectRightBottom, 0);
            return;
        }
        float[] fArr = this.mObjectLeftTop;
        System.arraycopy(fArr, 0, this.mRotatedLeftTop, 0, fArr.length);
        float[] fArr2 = this.mObjectRightTop;
        System.arraycopy(fArr2, 0, this.mRotatedRightTop, 0, fArr2.length);
        float[] fArr3 = this.mObjectLeftBottom;
        System.arraycopy(fArr3, 0, this.mRotatedLeftBottom, 0, fArr3.length);
        float[] fArr4 = this.mObjectRightBottom;
        System.arraycopy(fArr4, 0, this.mRotatedRightBottom, 0, fArr4.length);
    }

    private void createVideoSurface() {
        String str = TAG;
        Log.m262i(str, "Create video surface.");
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTextureID);
        this.mSurfaceTexture = surfaceTexture;
        surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.samsung.android.nexus.video.VideoGL$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                VideoGL.this.lambda$createVideoSurface$0(surfaceTexture2);
            }
        });
        this.mSurface = new Surface(this.mSurfaceTexture);
        Log.m262i(str, "Create surface media player = " + this.mPlayer);
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer != null) {
            videoPlayer.setSurface(this.mSurface);
        }
    }

    private void generateTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        this.mTextureID = i;
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, i);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, 10241, 9729.0f);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, 10240, 9729.0f);
    }

    private void initElements() {
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(this.mTriangleVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mVertices = asFloatBuffer;
        asFloatBuffer.put(this.mTriangleVerticesData).position(0);
        this.mShader = new Shader(this.mContext, C4749R.raw.video_vert_shader, C4749R.raw.video_frag_shader);
        Matrix.setIdentityM(this.mSTMatrix, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVideoSurface$0(SurfaceTexture surfaceTexture) {
        this.mFrameController.frameUpdated();
    }

    private void loadHandles() {
        this.maPositionHandle = this.mShader.getHandle("aPosition");
        this.maTextureHandle = this.mShader.getHandle("aTextureCoord");
        this.muGlobalAlphaHandle = this.mShader.getHandle("uGlobalAlpha");
        this.muNightFilterHandle = this.mShader.getHandle("uNightFilter");
        this.muRgbFilterColorHandle = this.mShader.getHandle("uRgbFilterColor");
        this.muHsvFilterColorHandle = this.mShader.getHandle("uHsvFilterColor");
        this.muContrastHandle = this.mShader.getHandle("uContrast");
        this.muSTMatrixHandle = this.mShader.getHandle("uSTMatrix");
        this.muTransparencyHandle = this.mShader.getHandle("uTransparency");
    }

    private void updateRotationMatrix() {
        Matrix.setIdentityM(this.mRotationMatrix, 0);
        Matrix.rotateM(this.mRotationMatrix, 0, this.mAngle, this.mRotationX, this.mRotationY, this.mRotationZ);
    }

    private void updateVertices() {
        if (this.mObjectRect == null) {
            Log.m261e(TAG, "Cannot update vertices because of the object rect is null.");
            return;
        }
        PointF pointF = this.mObjectCenter;
        float f = pointF.x + this.mOffsetX;
        float f2 = pointF.y + this.mOffsetY;
        float f3 = this.f629mZ + this.mOffsetZ;
        this.mVertices.position(0);
        this.mVertices.put(0, this.mRotatedLeftBottom[0] + f);
        this.mVertices.put(1, this.mRotatedLeftBottom[1] + f2);
        this.mVertices.put(2, f3);
        this.mVertices.put(5, this.mRotatedRightBottom[0] + f);
        this.mVertices.put(6, this.mRotatedRightBottom[1] + f2);
        this.mVertices.put(7, f3);
        this.mVertices.put(10, this.mRotatedLeftTop[0] + f);
        this.mVertices.put(11, this.mRotatedLeftTop[1] + f2);
        this.mVertices.put(12, f3);
        this.mVertices.put(15, this.mRotatedRightTop[0] + f);
        this.mVertices.put(16, this.mRotatedRightTop[1] + f2);
        this.mVertices.put(17, f3);
    }

    public void clear() {
        Log.m262i(TAG, "Clear");
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
        }
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
        GLES20.glDeleteTextures(1, new int[]{this.mTextureID}, 0);
        Shader shader = this.mShader;
        shader.getClass();
        GLES20.glUseProgram(0);
        EglUtils.checkGlError("glUseProgram : release");
        GLES20.glDeleteProgram(shader.mProgramId);
        EglUtils.checkGlError("glDeleteProgram id = " + shader.mProgramId);
        GLES20.glDeleteShader(shader.mVertexShaderId);
        EglUtils.checkGlError("glDeleteShader : vertex id = " + shader.mVertexShaderId);
        GLES20.glDeleteShader(shader.mFragmentShaderId);
        EglUtils.checkGlError("glDeleteShader : fragment id = " + shader.mFragmentShaderId);
        this.mShader = null;
        this.mVertices.clear();
        this.mVertices = null;
    }

    public float getContrast() {
        return this.mContrast;
    }

    public float getGlobalAlpha() {
        return this.mGlobalAlpha;
    }

    public boolean getHdrModeEnabled() {
        return this.mIsHdrModeEnabled;
    }

    public float getHdrSaturation() {
        return this.mHdrSaturationConverted;
    }

    public float[] getHsvFilterColor() {
        return this.mHsvFilterColor;
    }

    public float getHsvHue() {
        return this.mHsvFilterColor[0];
    }

    public float getHsvSaturation() {
        return this.mHsvFilterColor[1];
    }

    public float getHsvValue() {
        return this.mHsvFilterColor[2];
    }

    public float getOffsetX() {
        return this.mOffsetX;
    }

    public float getOffsetY() {
        return this.mOffsetY;
    }

    public boolean getTransparencyEnabled() {
        return this.mIsTransparencyEnabled;
    }

    public BaseWorld getWorld() {
        return this.mWorld;
    }

    public void onDrawFrame() {
        this.mFrameController.updateSurface();
        if (this.mWorld == null || this.mObjectRect == null) {
            return;
        }
        GLES20.glUseProgram(this.mShader.mProgramId);
        EglUtils.checkGlError("use program");
        this.mWorld.sendParams(this.mShader);
        EglUtils.checkGlError("send params");
        GLES20.glUniform1f(this.muGlobalAlphaHandle, this.mGlobalAlpha);
        float[] fArr = this.mRgbFilterColor;
        if (fArr != null) {
            GLES20.glUniform1f(this.muNightFilterHandle, fArr[3]);
            int i = this.muRgbFilterColorHandle;
            float[] fArr2 = this.mRgbFilterColor;
            GLES20.glUniform3f(i, fArr2[0], fArr2[1], fArr2[2]);
        } else {
            GLES20.glUniform1f(this.muNightFilterHandle, 0.0f);
        }
        if (this.mIsHdrModeEnabled) {
            int i2 = this.muHsvFilterColorHandle;
            float[] fArr3 = this.mHsvFilterColorConverted;
            GLES20.glUniform3f(i2, fArr3[0], this.mHdrSaturationConverted, fArr3[2]);
            GLES20.glUniform1f(this.muContrastHandle, this.mContrast);
        } else {
            int i3 = this.muHsvFilterColorHandle;
            float[] fArr4 = this.mHsvFilterColorConverted;
            GLES20.glUniform3f(i3, fArr4[0], fArr4[1], fArr4[2]);
            GLES20.glUniform1f(this.muContrastHandle, -1.0f);
        }
        if (this.mIsTransparencyEnabled) {
            GLES20.glUniform1f(this.muTransparencyHandle, 1.0f);
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(770, 771);
        } else {
            GLES20.glUniform1f(this.muTransparencyHandle, 0.0f);
        }
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, this.mTextureID);
        EglUtils.checkGlError("bind texture");
        this.mVertices.position(0);
        GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 20, (Buffer) this.mVertices);
        GLES20.glEnableVertexAttribArray(this.maPositionHandle);
        this.mVertices.position(0);
        EglUtils.checkGlError("send vertex data");
        this.mVertices.position(3);
        GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, 20, (Buffer) this.mVertices);
        GLES20.glEnableVertexAttribArray(this.maTextureHandle);
        this.mVertices.position(0);
        EglUtils.checkGlError("send texture coord data");
        GLES20.glUniformMatrix4fv(this.muSTMatrixHandle, 1, false, this.mSTMatrix, 0);
        GLES20.glDrawArrays(5, 0, 4);
    }

    public void setBoundRect(RectF rectF) {
        String str = TAG;
        Log.m262i(str, "Set bounds : " + rectF);
        this.mBoundRect = new RectF(rectF);
        float abs = Math.abs(rectF.width());
        float abs2 = Math.abs(rectF.height());
        if (abs == 0.0f || abs2 == 0.0f) {
            Log.m261e(str, "Cannot set bound. Size is wrong. bounds : " + rectF);
        } else {
            float f = abs2 / 2.0f;
            float f2 = -(abs / 2.0f);
            setObjectRect(new RectF(f2, f, abs + f2, f - abs2));
            calculateBoundsOffset();
        }
    }

    public void setContrast(float f) {
        Log.m262i(TAG, "Set contrast = " + f);
        this.mContrast = f;
    }

    public void setCropRect(RectF rectF) {
        if (this.mVertices == null) {
            return;
        }
        Log.m262i(TAG, "Apply crop rect = " + rectF);
        this.mVertices.position(0);
        this.mVertices.put(3, rectF.left);
        this.mVertices.put(4, rectF.bottom);
        this.mVertices.put(8, rectF.right);
        this.mVertices.put(9, rectF.bottom);
        this.mVertices.put(13, rectF.left);
        this.mVertices.put(14, rectF.top);
        this.mVertices.put(18, rectF.right);
        this.mVertices.put(19, rectF.top);
    }

    public void setGlobalAlpha(float f) {
        this.mGlobalAlpha = f;
    }

    public void setHdrModeEnabled(boolean z) {
        Log.m262i(TAG, "Set HDR mode = " + z);
        this.mIsHdrModeEnabled = z;
    }

    public void setHdrSaturation(float f) {
        Log.m262i(TAG, "Set hdr saturation = " + f);
        this.mHdrSaturationConverted = f * 2.0f;
    }

    public void setHsvFilterColor(float[] fArr) {
        if (fArr == null || fArr.length != 3) {
            return;
        }
        setHsvHue(fArr[0]);
        setHsvSaturation(fArr[1]);
        setHsvValue(fArr[2]);
    }

    public void setHsvHue(float f) {
        this.mHsvFilterColor[0] = f;
        this.mHsvFilterColorConverted[0] = f;
    }

    public void setHsvSaturation(float f) {
        this.mHsvFilterColor[1] = f;
        this.mHsvFilterColorConverted[1] = f * 2.0f;
    }

    public void setHsvValue(float f) {
        this.mHsvFilterColor[2] = f;
        this.mHsvFilterColorConverted[2] = f * 2.0f;
    }

    public void setObjectRect(RectF rectF) {
        Log.m262i(TAG, "Set object rect : " + rectF);
        this.mObjectRect = new RectF(rectF);
        calculateObjectFactors();
        calculateRotatedFactors();
        updateVertices();
    }

    public void setOffset(float f, float f2, float f3) {
        this.mOffsetX = f;
        this.mOffsetY = f2;
        this.mOffsetZ = f3;
        updateVertices();
    }

    public void setOffsetXY(float f, float f2) {
        this.mOffsetX = f;
        this.mOffsetY = f2;
        updateVertices();
    }

    public void setRgbFilterColor(float[] fArr) {
        if (fArr == null || fArr.length < 4) {
            Log.m262i(TAG, "Set color filter to null");
            this.mRgbFilterColor = null;
            return;
        }
        Log.m262i(TAG, "Set color filter R = " + fArr[0] + ", G = " + fArr[1] + ", B = " + fArr[2] + ", A = " + fArr[3]);
        this.mRgbFilterColor = (float[]) fArr.clone();
    }

    public void setRotation(float f, float f2, float f3, float f4) {
        this.mAngle = f;
        this.mRotationX = f2;
        this.mRotationY = f3;
        this.mRotationZ = f4;
        updateRotationMatrix();
        calculateRotatedFactors();
        updateVertices();
    }

    public void setScale(float f) {
        RectF rectF = this.mObjectRect;
        if (rectF == null) {
            return;
        }
        applySize(Math.abs(rectF.width()) * f, Math.abs(this.mObjectRect.height()) * f, 0);
    }

    public void setSize(float f, float f2) {
        applySize(f, f2, 1);
    }

    public void setTransparencyEnabled(boolean z) {
        Log.m262i(TAG, "Set transparency mode = " + z);
        this.mIsTransparencyEnabled = z;
    }

    public void setVideoOrientation(String str) {
        char c;
        PointF pointF;
        PointF pointF2;
        PointF pointF3;
        PointF pointF4;
        String str2 = TAG;
        Log.m261e(str2, "Apply video orientation : " + str);
        if (TextUtils.isEmpty(str)) {
            str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        }
        Log.m261e(str2, "Apply video orientation adj : " + str);
        str.getClass();
        int hashCode = str.hashCode();
        if (hashCode == 1815) {
            if (str.equals(DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 48873) {
            if (hashCode == 49803 && str.equals("270")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("180")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            pointF = new PointF(0.0f, 0.0f);
            pointF2 = new PointF(0.0f, 1.0f);
            pointF3 = new PointF(1.0f, 0.0f);
            pointF4 = new PointF(1.0f, 1.0f);
        } else if (c == 1) {
            pointF = new PointF(1.0f, 0.0f);
            pointF2 = new PointF(0.0f, 0.0f);
            pointF3 = new PointF(1.0f, 1.0f);
            pointF4 = new PointF(0.0f, 1.0f);
        } else if (c != 2) {
            pointF = new PointF(0.0f, 1.0f);
            pointF2 = new PointF(1.0f, 1.0f);
            pointF3 = new PointF(0.0f, 0.0f);
            pointF4 = new PointF(1.0f, 0.0f);
        } else {
            pointF = new PointF(1.0f, 1.0f);
            pointF2 = new PointF(1.0f, 0.0f);
            pointF3 = new PointF(0.0f, 1.0f);
            pointF4 = new PointF(0.0f, 0.0f);
        }
        this.mVertices.put(3, pointF3.x);
        this.mVertices.put(4, pointF3.y);
        this.mVertices.put(8, pointF4.x);
        this.mVertices.put(9, pointF4.y);
        this.mVertices.put(13, pointF.x);
        this.mVertices.put(14, pointF.y);
        this.mVertices.put(18, pointF2.x);
        this.mVertices.put(19, pointF2.y);
    }

    public void setWorldSize(int i, int i2) {
        this.mWorldWidth = i;
        this.mWorldHeight = i2;
        this.mWorld = new WorldOrthographic(i, i2);
        calculateBoundsOffset();
    }

    public VideoGL(Context context, VideoPlayer videoPlayer, Rect rect) {
        this.mTriangleVerticesData = new float[]{-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f, 1.0f};
        this.mFrameController = new VideoFrameController();
        this.mGlobalAlpha = 1.0f;
        this.mHsvFilterColor = new float[]{0.0f, 0.5f, 0.5f};
        this.mHsvFilterColorConverted = new float[]{0.0f, 1.0f, 1.0f};
        this.mWorldWidth = 0.0f;
        this.mWorldHeight = 0.0f;
        this.mIsTransparencyEnabled = false;
        this.mIsHdrModeEnabled = false;
        this.mContrast = 1.0f;
        this.mHdrSaturationConverted = 1.0f;
        this.mObjectRect = null;
        this.mBoundRect = null;
        this.f629mZ = 0.0f;
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        this.mOffsetZ = 0.0f;
        this.mAngle = 0.0f;
        this.mRotationX = 0.0f;
        this.mRotationY = 0.0f;
        this.mRotationZ = 0.0f;
        this.mRotationMatrix = new float[16];
        this.mSTMatrix = new float[16];
        this.mObjectHalfWidth = 0.0f;
        this.mObjectHalfHeight = 0.0f;
        this.mObjectCenter = new PointF(0.0f, 0.0f);
        this.mObjectLeftTop = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mObjectRightTop = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mObjectLeftBottom = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mObjectRightBottom = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mRotatedLeftTop = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mRotatedRightTop = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mRotatedLeftBottom = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mRotatedRightBottom = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mContext = context;
        this.mPlayer = videoPlayer;
        initElements();
        loadHandles();
        generateTexture();
        createVideoSurface();
        setVideoOrientation(this.mPlayer.getVideoOrientation());
        if (rect != null) {
            setObjectRect(rect);
        }
    }

    public void setHdrModeEnabled(boolean z, float f, float f2) {
        setHdrSaturation(f);
        setContrast(f2);
        setHdrModeEnabled(z);
    }

    public void setObjectRect(Rect rect) {
        setObjectRect(new RectF(rect));
    }
}
