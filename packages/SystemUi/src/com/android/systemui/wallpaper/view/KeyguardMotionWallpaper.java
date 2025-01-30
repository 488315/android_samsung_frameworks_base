package com.android.systemui.wallpaper.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.FixedOrientationController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.view.animation.SineOut33;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardMotionWallpaper extends SystemUIWallpaper implements SensorEventListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ValueAnimator mAlphaAnimator;
    public float mAngularSum;
    public float mAnimatedAngularSum;
    public final Paint mBlendingPaint;
    public final Context mContext;
    public float mCroppedScale;
    public final int mCurrentWhich;
    public float mDeltaOfAngularSum;
    public final FixedOrientationController mFixedOrientationController;
    public final HandlerC37032 mHandler;
    public final SineOut33 mInterpolator;
    public final Sensor mInterruptedGyro;
    public boolean mIsSensorRegistered;
    public int mLastHeight;
    public int mLastWidth;
    public AsyncTaskC37021 mLoader;
    public ArrayList mMotionBitmapList;
    public final ArrayList mOldBitmapList;
    public String mPkgName;
    public Resources mPkgResources;
    public float mPrevAngularSum;
    public float mPrevAnimatedAngularSum;
    public float mPrevStartAngularSum;
    public int mRangeOfRotation;
    public int mRotation;
    public final SensorManager mSensorManager;
    public long mTimestamp;
    public int mViewHeight;
    public int mViewWidth;
    public final WallpaperManager mWallpaperManager;
    public final WallpaperResultCallback mWallpaperResultCallback;
    public String mXmlName;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MotionBitmap implements Cloneable {
        public int alpha;
        public float calculatedSum;
        public Bitmap image;
        public String path;
        public int prevAlpha;
        public int stayPoint1;
        public int stayPoint2;
        public int type;
        public Matrix matrix = new Matrix();
        public boolean isBackground = false;
        public boolean bitmapLoaded = false;

        public MotionBitmap() {
        }

        public final Object clone() {
            return (MotionBitmap) super.clone();
        }

        public final void setAlpha(float f, float f2) {
            boolean z = f < f2;
            int i = KeyguardMotionWallpaper.this.mRangeOfRotation;
            float f3 = f2 % i;
            this.calculatedSum = f3;
            if (f3 > i - 3) {
                this.calculatedSum = f3 - i;
            } else if (f3 < -3.0f) {
                this.calculatedSum = f3 + i;
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("calculatedSum = "), this.calculatedSum, "KeyguardMotionWallpaper");
            float f4 = this.calculatedSum;
            int i2 = this.stayPoint1;
            if (f4 < i2 || f4 > this.stayPoint2) {
                if (this.isBackground) {
                    this.isBackground = false;
                }
                float f5 = i2;
                if (i2 == -3) {
                    f5 += KeyguardMotionWallpaper.this.mRangeOfRotation;
                }
                if (f4 <= this.stayPoint2 || f4 >= r7 + 24) {
                    if (f4 < f5 && f4 > f5 - 24.0f && !this.isBackground && !z) {
                        this.isBackground = true;
                    }
                } else if (!this.isBackground && z) {
                    this.isBackground = true;
                }
            } else {
                this.isBackground = true;
            }
            if (this.isBackground) {
                this.alpha = 0;
            } else {
                float f6 = i2;
                if (i2 == -3) {
                    f6 += KeyguardMotionWallpaper.this.mRangeOfRotation;
                }
                int i3 = this.stayPoint2;
                if (f4 > i3 && f4 < i3 + 24) {
                    this.alpha = (int) ((Math.abs(f4 - i3) / 24.0f) * 255.0f);
                    RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Foreground alpha = "), this.alpha, "KeyguardMotionWallpaper");
                } else if (f4 >= f6 || f4 <= f6 - 24.0f) {
                    this.alpha = 255;
                    Log.d("KeyguardMotionWallpaper", "disappear!!");
                } else {
                    this.alpha = (int) ((Math.abs(f4 - f6) / 24.0f) * 255.0f);
                    RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Foreground alpha = "), this.alpha, "KeyguardMotionWallpaper");
                }
            }
            if (this.alpha > 255) {
                this.alpha = 255;
            }
            this.alpha = 255 - this.alpha;
        }
    }

    public KeyguardMotionWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, Consumer<Boolean> consumer, int i) {
        this(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, null, null, false, i);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        Log.d("KeyguardMotionWallpaper", "cleanUp");
        clearData(this.mOldBitmapList, false);
        clearData(this.mMotionBitmapList, false);
        this.mDrawingState = 3;
    }

    public final void clearData(ArrayList arrayList, boolean z) {
        Bitmap bitmap;
        boolean z2;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("clearData: ");
        sb.append(arrayList.size());
        Log.i("KeyguardMotionWallpaper", sb.toString());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            MotionBitmap motionBitmap = (MotionBitmap) it.next();
            motionBitmap.bitmapLoaded = false;
            boolean z3 = true;
            if (z && motionBitmap.image != null) {
                Iterator it2 = this.mMotionBitmapList.iterator();
                while (true) {
                    z2 = true;
                    while (it2.hasNext()) {
                        if (motionBitmap.image != ((MotionBitmap) it2.next()).image) {
                            break;
                        } else {
                            z2 = false;
                        }
                    }
                }
                z3 = z2;
            }
            if (z3 && (bitmap = motionBitmap.image) != null && !bitmap.isRecycled()) {
                motionBitmap.image.recycle();
                motionBitmap.image = null;
            }
        }
        arrayList.clear();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final Bitmap getWallpaperBitmap() {
        Bitmap bitmap = null;
        try {
            Log.d("KeyguardMotionWallpaper", "getWallpaperBitmap() hw accelerated: " + isHardwareAccelerated());
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            draw(new Canvas(bitmap));
            return bitmap;
        } catch (Throwable th) {
            th.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return super.getWallpaperBitmap();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init() {
        WallpaperResultCallback wallpaperResultCallback;
        float f;
        float f2;
        Log.d("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("init()"));
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList == null) {
            Log.e("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("mMotionBitmapList == null || mMotionBitmapList.size() == 0"));
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (!((MotionBitmap) it.next()).bitmapLoaded) {
                Log.e("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("bitmapLoaded == false"));
                return;
            }
        }
        if (WallpaperUtils.isStatusBarHeight(this.mContext, this, getHeight())) {
            this.mLastWidth = -1;
            this.mLastHeight = -1;
            return;
        }
        this.mViewWidth = (getWidth() - ((FrameLayout) this).mPaddingLeft) - ((FrameLayout) this).mPaddingRight;
        int height = (getHeight() - ((FrameLayout) this).mPaddingTop) - ((FrameLayout) this).mPaddingBottom;
        this.mViewHeight = height;
        if (this.mViewWidth == 0 || height == 0) {
            Log.e("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("mViewWidth == 0 || mViewHeight == 0"));
            return;
        }
        this.mRangeOfRotation = this.mMotionBitmapList.size() * 30;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("mRangeOfRotation = ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mRangeOfRotation, "KeyguardMotionWallpaper");
        this.mPrevAngularSum = 0.0f;
        this.mAngularSum = 0.0f;
        this.mDeltaOfAngularSum = 0.0f;
        this.mTimestamp = 0L;
        Iterator it2 = this.mMotionBitmapList.iterator();
        while (it2.hasNext()) {
            MotionBitmap motionBitmap = (MotionBitmap) it2.next();
            Bitmap bitmap = motionBitmap.image;
            if (bitmap == null || bitmap.isRecycled()) {
                Log.e("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("bitmap is wrong."));
                invalidate();
                wallpaperResultCallback = this.mWallpaperResultCallback;
                if (wallpaperResultCallback == null) {
                    if (LsRune.WALLPAPER_CAPTURED_BLUR) {
                        wallpaperResultCallback.onDrawFinished();
                    }
                    this.mWallpaperResultCallback.onPreviewReady();
                    return;
                }
                return;
            }
            int width = motionBitmap.image.getWidth();
            int height2 = motionBitmap.image.getHeight();
            int i = this.mViewHeight;
            int i2 = width * i;
            int i3 = this.mViewWidth;
            if (i2 > i3 * height2) {
                f = i;
                f2 = height2;
            } else {
                f = i3;
                f2 = width;
            }
            float f3 = (f / f2) * 1.0f;
            int round = Math.round((i3 - (width * f3)) * 0.5f);
            int round2 = Math.round((i - (height2 * f3)) * 0.5f);
            motionBitmap.matrix.setScale(f3, f3);
            motionBitmap.matrix.postTranslate(round, round2);
            motionBitmap.isBackground = false;
            motionBitmap.setAlpha(0.0f, 0.0f);
        }
        invalidate();
        wallpaperResultCallback = this.mWallpaperResultCallback;
        if (wallpaperResultCallback == null) {
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onBackDropLayoutChange() {
        updateDisplayInfo();
        awaitCall();
        int i = this.mCurDisplayInfo.rotation;
        Log.i("KeyguardMotionWallpaper", "onConfigurationChanged: prev = " + this.mRotation + ", new = " + i);
        this.mRotation = i;
        if (LsRune.WALLPAPER_ROTATABLE_WALLPAPER || this.mIsPreview) {
            return;
        }
        this.mFixedOrientationController.applyPortraitRotation();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.i("KeyguardMotionWallpaper", "onConfigurationChanged: prev = " + this.mOrientation + ", new = " + configuration.orientation + ", isSub:" + SystemUIWallpaper.isSubDisplay());
        if ((!LsRune.WALLPAPER_ROTATABLE_WALLPAPER || SystemUIWallpaper.isSubDisplay()) && !this.mIsPreview) {
            this.mFixedOrientationController.applyPortraitRotation();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsSensorRegistered) {
            unregisterSensor();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Log.d("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("onDraw()"));
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList == null || arrayList.size() == 0) {
            Log.e("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("mBitmapImageList == null || mBitmapImageList.size() == 0"));
            return;
        }
        int size = this.mMotionBitmapList.size();
        for (int i = 0; i < size; i++) {
            if (!((MotionBitmap) this.mMotionBitmapList.get(i)).bitmapLoaded) {
                Log.e("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("bitmapLoaded == false"));
                return;
            }
        }
        canvas.save();
        boolean z = true;
        for (int i2 = 0; i2 < this.mMotionBitmapList.size(); i2++) {
            if (((MotionBitmap) this.mMotionBitmapList.get(i2)).isBackground) {
                this.mBlendingPaint.setAlpha(((MotionBitmap) this.mMotionBitmapList.get(i2)).alpha);
                Bitmap bitmap = ((MotionBitmap) this.mMotionBitmapList.get(i2)).image;
                if (bitmap == null || bitmap.isRecycled()) {
                    Log.e("KeyguardMotionWallpaper", "onDraw: recycled bitmap1");
                    z = false;
                } else {
                    canvas.drawBitmap(bitmap, ((MotionBitmap) this.mMotionBitmapList.get(i2)).matrix, this.mBlendingPaint);
                }
                RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("drawBackground!!!! ===> ", i2, ", alpha = "), ((MotionBitmap) this.mMotionBitmapList.get(i2)).alpha, "KeyguardMotionWallpaper");
            }
        }
        for (int i3 = 0; i3 < this.mMotionBitmapList.size(); i3++) {
            int i4 = ((MotionBitmap) this.mMotionBitmapList.get(i3)).alpha;
            if (!((MotionBitmap) this.mMotionBitmapList.get(i3)).isBackground && i4 != 0) {
                this.mBlendingPaint.setAlpha(i4);
                Bitmap bitmap2 = ((MotionBitmap) this.mMotionBitmapList.get(i3)).image;
                if (bitmap2 == null || bitmap2.isRecycled()) {
                    Log.e("KeyguardMotionWallpaper", "onDraw: recycled bitmap2");
                    z = false;
                } else {
                    canvas.drawBitmap(bitmap2, ((MotionBitmap) this.mMotionBitmapList.get(i3)).matrix, this.mBlendingPaint);
                }
                RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("drawForeground!!!! ===> ", i3, ", alpha = "), ((MotionBitmap) this.mMotionBitmapList.get(i3)).alpha, "KeyguardMotionWallpaper");
            }
        }
        this.mDrawingState = z ? 1 : 2;
        canvas.restore();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
        this.mBouncer = z;
        if (z && this.mIsSensorRegistered) {
            unregisterSensor();
        } else if (this.mIsKeyguardShowing) {
            registerSensor();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i3 - i;
        int i6 = i4 - i2;
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onLayout called : ", i, " , ", i2, " , "), i3, " , ", i4, "KeyguardMotionWallpaper");
        if (!z || i5 <= 0 || i6 <= 0 || WallpaperUtils.isStatusBarHeight(getContext(), this, i4)) {
            return;
        }
        if (this.mLastWidth == i5 && this.mLastHeight == i6) {
            return;
        }
        init();
        this.mLastWidth = i5;
        this.mLastHeight = i6;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onPause() {
        this.mResumed = false;
        Log.d("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("onPause()"));
        if (this.mIsSensorRegistered) {
            unregisterSensor();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onResume() {
        int i;
        ArrayList arrayList;
        this.mResumed = true;
        int i2 = WallpaperUtils.sCurrentWhich;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("onResume, mDrawingState:");
        sb.append(this.mDrawingState);
        sb.append(", this = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb, this.mCurrentWhich, " , current = ", i2, "KeyguardMotionWallpaper");
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !this.mIsPreview && this.mCurrentWhich == i2 && ((i = this.mDrawingState) == 3 || i == 2 || (arrayList = this.mMotionBitmapList) == null || arrayList.size() == 0)) {
            Log.d("KeyguardMotionWallpaper", "onResume, reload");
            this.mDrawingState = 0;
            updateWallpaper();
        }
        if ((!LsRune.WALLPAPER_ROTATABLE_WALLPAPER || SystemUIWallpaper.isSubDisplay()) && !this.mIsPreview) {
            this.mFixedOrientationController.applyPortraitRotation();
        }
        if (!this.mIsSensorRegistered) {
            registerSensor();
        }
        init();
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("KeyguardMotionWallpaper", "onSensorChanged: " + sensorEvent.sensor.getType() + " , mTimestamp = " + this.mTimestamp + " , timestamp = " + sensorEvent.timestamp);
        if (sensorEvent.sensor.getType() != 65579) {
            return;
        }
        if (this.mTimestamp != 0) {
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            int i = this.mCurDisplayInfo.rotation;
            boolean z = i == 1 || i == 3 || this.mOrientation == 2;
            if (!z && (Math.abs(f) > Math.abs(f2) || Math.abs(f3) > Math.abs(f2))) {
                Log.e("KeyguardMotionWallpaper", "axisY is not biggest, stop animation");
                return;
            }
            if (z && (Math.abs(f2) > Math.abs(f) || Math.abs(f3) > Math.abs(f))) {
                Log.e("KeyguardMotionWallpaper", "axisX is not biggest, stop animation");
                return;
            }
            float f4 = this.mAngularSum;
            this.mPrevAngularSum = f4;
            if (z) {
                this.mAngularSum = f4 + f;
            } else {
                this.mAngularSum = f4 + f2;
            }
            this.mDeltaOfAngularSum = Math.abs(this.mAngularSum - f4);
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("axisX: ", f, ", axisY: ", f2, ", axisZ: ");
            m88m.append(f3);
            Log.d("KeyguardMotionWallpaper", m88m.toString());
            StringBuilder sb = new StringBuilder("mAngularSum: ");
            sb.append(this.mAngularSum);
            sb.append(", mDeltaOfAngularSum: ");
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(sb, this.mDeltaOfAngularSum, "KeyguardMotionWallpaper");
            float[] fArr2 = {this.mPrevAngularSum, this.mAngularSum};
            for (int i2 = 0; i2 < this.mMotionBitmapList.size(); i2++) {
                MotionBitmap motionBitmap = (MotionBitmap) this.mMotionBitmapList.get(i2);
                motionBitmap.prevAlpha = motionBitmap.alpha;
                motionBitmap.setAlpha(this.mPrevAngularSum, this.mAngularSum);
                if (motionBitmap.prevAlpha != motionBitmap.alpha) {
                    if (this.mDeltaOfAngularSum < 3.0f) {
                        ValueAnimator valueAnimator = this.mAlphaAnimator;
                        if (valueAnimator == null) {
                            invalidate();
                        } else if (valueAnimator.isRunning()) {
                            Log.e("KeyguardMotionWallpaper", "mAlphaAnimator isRunning");
                            HandlerC37032 handlerC37032 = this.mHandler;
                            handlerC37032.sendMessage(handlerC37032.obtainMessage(1, fArr2));
                        }
                    } else {
                        HandlerC37032 handlerC370322 = this.mHandler;
                        handlerC370322.sendMessage(handlerC370322.obtainMessage(1, fArr2));
                    }
                }
            }
        }
        this.mTimestamp = sensorEvent.timestamp;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onUnlock() {
        Log.d("KeyguardMotionWallpaper", "onUnlock()");
        if (this.mResumed && this.mIsSensorRegistered) {
            unregisterSensor();
        }
    }

    public final ArrayList parseXML(XmlPullParser xmlPullParser) {
        String name;
        int eventType = xmlPullParser.getEventType();
        ArrayList arrayList = null;
        MotionBitmap motionBitmap = null;
        while (eventType != 1) {
            if (eventType == 0) {
                xmlPullParser.getName();
                arrayList = new ArrayList();
            } else if (eventType == 2) {
                String name2 = xmlPullParser.getName();
                if (name2.equals("layer")) {
                    motionBitmap = new MotionBitmap();
                } else if (motionBitmap != null) {
                    if (name2.equals("type")) {
                        motionBitmap.type = Integer.parseInt(xmlPullParser.nextText());
                    } else if (name2.equals("id_path_image")) {
                        motionBitmap.path = xmlPullParser.nextText();
                    }
                }
            } else if (eventType == 3 && (name = xmlPullParser.getName()) != null && name.equalsIgnoreCase("layer") && motionBitmap != null && arrayList != null) {
                arrayList.add(motionBitmap);
            }
            eventType = xmlPullParser.next();
        }
        return arrayList;
    }

    public final void registerSensor() {
        Log.d("KeyguardMotionWallpaper", "registerSensor");
        this.mSensorManager.registerListener(this, this.mInterruptedGyro, 1);
        this.mIsSensorRegistered = true;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void reset() {
        Log.d("KeyguardMotionWallpaper", "reset()");
        if (this.mResumed && !this.mIsSensorRegistered) {
            registerSensor();
        }
        init();
    }

    public final void startAlphaAnimator(float f, final float f2, boolean z) {
        Log.d("KeyguardMotionWallpaper", "mAlphaAnimator starts");
        Log.d("KeyguardMotionWallpaper", "prevAngularSum = " + f + " curAngularSum = " + f2);
        if (z) {
            float f3 = this.mPrevAnimatedAngularSum;
            this.mAnimatedAngularSum = f3;
            this.mPrevStartAngularSum = f3;
        } else {
            this.mAnimatedAngularSum = f;
            this.mPrevStartAngularSum = f;
        }
        this.mAlphaAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAlphaAnimator.setDuration((int) (Math.abs(f2 - this.mPrevAnimatedAngularSum) * 16.0f));
        this.mAlphaAnimator.setInterpolator(this.mInterpolator);
        this.mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.view.KeyguardMotionWallpaper.3
            /* JADX WARN: Code restructure failed: missing block: B:20:0x0026, code lost:
            
                if (r1 <= r7) goto L11;
             */
            /* JADX WARN: Code restructure failed: missing block: B:4:0x0019, code lost:
            
                if (r1 >= r7) goto L11;
             */
            /* JADX WARN: Code restructure failed: missing block: B:5:0x0029, code lost:
            
                r3 = false;
             */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f4 = f2;
                KeyguardMotionWallpaper keyguardMotionWallpaper = KeyguardMotionWallpaper.this;
                boolean z2 = true;
                if (f4 < keyguardMotionWallpaper.mPrevStartAngularSum) {
                    float f5 = keyguardMotionWallpaper.mAnimatedAngularSum;
                    float f6 = f5 - ((f5 - f4) * 0.05f);
                    keyguardMotionWallpaper.mAnimatedAngularSum = f6;
                } else {
                    float f7 = keyguardMotionWallpaper.mAnimatedAngularSum;
                    float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f4, f7, 0.05f, f7);
                    keyguardMotionWallpaper.mAnimatedAngularSum = m20m;
                }
                if (Math.abs(keyguardMotionWallpaper.mPrevAnimatedAngularSum - keyguardMotionWallpaper.mAnimatedAngularSum) <= 1.0E-4f || !z2) {
                    return;
                }
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("animatedAngle = "), KeyguardMotionWallpaper.this.mAnimatedAngularSum, "KeyguardMotionWallpaper");
                for (int i = 0; i < KeyguardMotionWallpaper.this.mMotionBitmapList.size(); i++) {
                    MotionBitmap motionBitmap = (MotionBitmap) KeyguardMotionWallpaper.this.mMotionBitmapList.get(i);
                    KeyguardMotionWallpaper keyguardMotionWallpaper2 = KeyguardMotionWallpaper.this;
                    motionBitmap.setAlpha(keyguardMotionWallpaper2.mPrevAnimatedAngularSum, keyguardMotionWallpaper2.mAnimatedAngularSum);
                }
                KeyguardMotionWallpaper.this.invalidate();
                KeyguardMotionWallpaper keyguardMotionWallpaper3 = KeyguardMotionWallpaper.this;
                keyguardMotionWallpaper3.mPrevAnimatedAngularSum = keyguardMotionWallpaper3.mAnimatedAngularSum;
            }
        });
        this.mAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.wallpaper.view.KeyguardMotionWallpaper.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardMotionWallpaper.this.mAlphaAnimator = null;
            }
        });
        this.mAlphaAnimator.start();
    }

    public final void unregisterSensor() {
        Log.d("KeyguardMotionWallpaper", "unregisterSensor");
        this.mSensorManager.unregisterListener(this);
        this.mIsSensorRegistered = false;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        String motionWallpaperPkgName = this.mWallpaperManager.getMotionWallpaperPkgName(WallpaperUtils.sCurrentWhich);
        if (TextUtils.isEmpty(motionWallpaperPkgName) || (motionWallpaperPkgName != null && motionWallpaperPkgName.equals(this.mPkgName))) {
            Log.d("KeyguardMotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("same pkg, do not update() "));
            WallpaperResultCallback wallpaperResultCallback = this.mWallpaperResultCallback;
            if (wallpaperResultCallback != null) {
                wallpaperResultCallback.onPreviewReady();
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("update() prev = ");
        sb.append(this.mPkgName);
        sb.append(", new = ");
        sb.append(motionWallpaperPkgName);
        Log.d("KeyguardMotionWallpaper", sb.toString());
        updateWallpaper();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wallpaper.view.KeyguardMotionWallpaper$1] */
    public final void updateWallpaper() {
        AsyncTaskC37021 asyncTaskC37021 = this.mLoader;
        if (asyncTaskC37021 != null && !asyncTaskC37021.isCancelled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mIsPreview ? "(Preview)" : "");
            sb.append(" cancel loader = ");
            sb.append(this.mLoader);
            Log.i("KeyguardMotionWallpaper", sb.toString());
            cancel(true);
            this.mLoader = null;
        }
        this.mLoader = new AsyncTask() { // from class: com.android.systemui.wallpaper.view.KeyguardMotionWallpaper.1
            /* JADX WARN: Removed duplicated region for block: B:102:0x0223  */
            /* JADX WARN: Removed duplicated region for block: B:134:0x0150  */
            /* JADX WARN: Removed duplicated region for block: B:20:0x00f9  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x0105  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x014e  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x016f  */
            /* JADX WARN: Removed duplicated region for block: B:52:0x0221  */
            /* JADX WARN: Removed duplicated region for block: B:77:0x03a9  */
            /* JADX WARN: Removed duplicated region for block: B:84:0x03cb A[SYNTHETIC] */
            @Override // android.os.AsyncTask
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object doInBackground(Object[] objArr) {
                ArrayList arrayList;
                ArrayList arrayList2;
                Iterator it;
                Bitmap bitmap;
                String str;
                String str2;
                Iterator it2;
                Matrix matrix;
                float f;
                String str3;
                Resources resources;
                String str4 = "";
                String str5 = "(Preview)";
                KeyguardMotionWallpaper keyguardMotionWallpaper = KeyguardMotionWallpaper.this;
                int i = KeyguardMotionWallpaper.$r8$clinit;
                keyguardMotionWallpaper.getClass();
                Bitmap bitmap2 = null;
                try {
                    XmlPullParserFactory.newInstance().newPullParser();
                    if (keyguardMotionWallpaper.mIsPreview) {
                        keyguardMotionWallpaper.mXmlName = "motion";
                    } else {
                        keyguardMotionWallpaper.mPkgName = keyguardMotionWallpaper.mWallpaperManager.getMotionWallpaperPkgName(keyguardMotionWallpaper.mCurrentWhich);
                        keyguardMotionWallpaper.mXmlName = "motion";
                    }
                    str3 = "/data/overlays/main_packages/" + keyguardMotionWallpaper.mPkgName + ".apk";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (new File(str3).exists()) {
                    try {
                        PackageInfo packageArchiveInfo = keyguardMotionWallpaper.mContext.getPackageManager().getPackageArchiveInfo(str3, PackageManager.PackageInfoFlags.of(0L));
                        packageArchiveInfo.applicationInfo.publicSourceDir = str3;
                        resources = keyguardMotionWallpaper.mContext.getPackageManager().getResourcesForApplication(packageArchiveInfo.applicationInfo);
                    } catch (PackageManager.NameNotFoundException e2) {
                        e2.printStackTrace();
                        resources = null;
                    }
                    keyguardMotionWallpaper.mPkgResources = resources;
                    if (resources == null) {
                        Log.e("KeyguardMotionWallpaper", "mPkgResources == null");
                        arrayList = null;
                        if (arrayList != null && arrayList.size() > 0) {
                            KeyguardMotionWallpaper keyguardMotionWallpaper2 = KeyguardMotionWallpaper.this;
                            arrayList2 = keyguardMotionWallpaper2.mMotionBitmapList;
                            if (arrayList2 != null) {
                                int size = arrayList2.size();
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(keyguardMotionWallpaper2.mIsPreview ? "(Preview)" : "");
                                sb2.append("collectOldBitmap: size = ");
                                sb2.append(size);
                                Log.i("KeyguardMotionWallpaper", sb2.toString());
                                if (size > 0) {
                                    Iterator it3 = keyguardMotionWallpaper2.mMotionBitmapList.iterator();
                                    while (it3.hasNext()) {
                                        keyguardMotionWallpaper2.mOldBitmapList.add((MotionBitmap) it3.next());
                                    }
                                }
                            }
                            KeyguardMotionWallpaper keyguardMotionWallpaper3 = KeyguardMotionWallpaper.this;
                            keyguardMotionWallpaper3.mMotionBitmapList = arrayList;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(!keyguardMotionWallpaper3.mIsPreview ? "(Preview)" : "");
                            sb3.append("BITMAP LOAD START ");
                            sb3.append(keyguardMotionWallpaper3);
                            Log.d("KeyguardMotionWallpaper", sb3.toString());
                            it = keyguardMotionWallpaper3.mMotionBitmapList.iterator();
                            while (it.hasNext()) {
                                MotionBitmap motionBitmap = (MotionBitmap) it.next();
                                int i2 = motionBitmap.type;
                                if (i2 == 0) {
                                    try {
                                        int identifier = keyguardMotionWallpaper3.mPkgResources.getIdentifier(motionBitmap.path, "drawable", keyguardMotionWallpaper3.mPkgName);
                                        if (identifier > 0) {
                                            bitmap = ((BitmapDrawable) keyguardMotionWallpaper3.mPkgResources.getDrawable(identifier)).getBitmap();
                                            try {
                                                motionBitmap.image = bitmap.copy(bitmap.getConfig(), true);
                                            } catch (Exception e3) {
                                                e = e3;
                                                StringBuilder sb4 = new StringBuilder();
                                                sb4.append(keyguardMotionWallpaper3.mIsPreview ? str5 : str4);
                                                sb4.append("loadDrawable exception");
                                                sb4.append(e.toString());
                                                Log.e("KeyguardMotionWallpaper", sb4.toString());
                                                Log.d("KeyguardMotionWallpaper", (!keyguardMotionWallpaper3.mIsPreview ? str5 : str4).concat("getMatrix"));
                                                if (bitmap != null) {
                                                }
                                                str = str4;
                                                str2 = str5;
                                                it2 = it;
                                                matrix = null;
                                                if (matrix != null) {
                                                }
                                                int indexOf = keyguardMotionWallpaper3.mMotionBitmapList.indexOf(motionBitmap) * 30;
                                                motionBitmap.stayPoint1 = indexOf - 3;
                                                motionBitmap.stayPoint2 = indexOf + 3;
                                                motionBitmap.isBackground = false;
                                                motionBitmap.setAlpha(0.0f, 0.0f);
                                                str5 = str2;
                                                str4 = str;
                                                it = it2;
                                                bitmap2 = null;
                                            }
                                        } else {
                                            StringBuilder sb5 = new StringBuilder();
                                            sb5.append(keyguardMotionWallpaper3.mIsPreview ? str5 : str4);
                                            sb5.append("Fail to get drawable");
                                            Log.w("KeyguardMotionWallpaper", sb5.toString());
                                            bitmap = bitmap2;
                                        }
                                    } catch (Exception e4) {
                                        e = e4;
                                        bitmap = bitmap2;
                                    }
                                } else if (i2 == 1) {
                                    File file = new File(motionBitmap.path);
                                    if (!file.exists() || !file.canRead()) {
                                        break;
                                    }
                                    try {
                                        bitmap = new BitmapDrawable(keyguardMotionWallpaper3.mPkgResources, motionBitmap.path).getBitmap();
                                        motionBitmap.image = bitmap.copy(bitmap.getConfig(), true);
                                    } catch (Exception e5) {
                                        e5.printStackTrace();
                                    }
                                } else {
                                    if (i2 == 2) {
                                        motionBitmap.image = bitmap2;
                                    }
                                    bitmap = bitmap2;
                                }
                                Log.d("KeyguardMotionWallpaper", (!keyguardMotionWallpaper3.mIsPreview ? str5 : str4).concat("getMatrix"));
                                if (bitmap != null || bitmap.isRecycled()) {
                                    str = str4;
                                    str2 = str5;
                                    it2 = it;
                                } else {
                                    int width = bitmap.getWidth();
                                    int height = bitmap.getHeight();
                                    float f2 = width;
                                    float f3 = f2 / 2.0f;
                                    float f4 = height;
                                    float f5 = f4 / 2.0f;
                                    keyguardMotionWallpaper3.awaitCall();
                                    int i3 = keyguardMotionWallpaper3.mMetricsWidth;
                                    keyguardMotionWallpaper3.awaitCall();
                                    int i4 = keyguardMotionWallpaper3.mMetricsHeight;
                                    float f6 = (width * i4 > i3 * height ? i4 / f4 : i3 / f2) * 1.0f;
                                    keyguardMotionWallpaper3.mCroppedScale = f6;
                                    float f7 = (i3 * 1.0f) / f6;
                                    float f8 = (i4 * 1.0f) / f6;
                                    float f9 = f3 - (f7 / 2.0f);
                                    if (f9 < 0.0f) {
                                        str = str4;
                                        f = 0.0f;
                                    } else {
                                        str = str4;
                                        f = f9;
                                    }
                                    float f10 = f5 - (f8 / 2.0f);
                                    if (f10 < 0.0f) {
                                        str2 = str5;
                                        f10 = 0.0f;
                                    } else {
                                        str2 = str5;
                                    }
                                    Matrix matrix2 = new Matrix();
                                    matrix2.setScale(f6, f6);
                                    matrix2.postTranslate(f, f10);
                                    it2 = it;
                                    Log.d("KeyguardMotionWallpaper", "widthOrigin = " + width);
                                    Log.d("KeyguardMotionWallpaper", "heightOrigin = " + height);
                                    Log.d("KeyguardMotionWallpaper", "scale = " + f6);
                                    Log.d("KeyguardMotionWallpaper", "mCroppedScale:" + keyguardMotionWallpaper3.mCroppedScale);
                                    Log.d("KeyguardMotionWallpaper", "centerX = " + f3);
                                    Log.d("KeyguardMotionWallpaper", "centerY = " + f5);
                                    Log.d("KeyguardMotionWallpaper", "startX = " + f);
                                    Log.d("KeyguardMotionWallpaper", "startY = " + f10);
                                    Log.d("KeyguardMotionWallpaper", "width = " + f7);
                                    Log.d("KeyguardMotionWallpaper", "height = " + f8);
                                    if (Math.round(f) == 0 && Math.round(f10) == 0 && width == Math.round(f7) && height == Math.round(f8)) {
                                        Log.d("KeyguardMotionWallpaper", "It doesn't need to crop bitmap");
                                    } else if (Math.round(f7) < 1 || Math.round(f8) < 1 || i3 < 1 || i4 < 1) {
                                        Log.d("KeyguardMotionWallpaper", "Math.round(width) < 1 || Math.round(height) < 1 || mMetricsWidth < 1 || mMetricsHeight < 1");
                                    } else {
                                        if (Math.round(f) + Math.round(f7) <= width) {
                                            if (Math.round(f10) + Math.round(f8) <= height) {
                                                matrix = matrix2;
                                                if (matrix != null) {
                                                    StringBuilder sb6 = new StringBuilder();
                                                    sb6.append(keyguardMotionWallpaper3.mIsPreview ? str2 : str);
                                                    sb6.append("loadWallpapers: matrix ");
                                                    sb6.append(matrix);
                                                    Log.i("KeyguardMotionWallpaper", sb6.toString());
                                                    motionBitmap.matrix = matrix;
                                                }
                                                int indexOf2 = keyguardMotionWallpaper3.mMotionBitmapList.indexOf(motionBitmap) * 30;
                                                motionBitmap.stayPoint1 = indexOf2 - 3;
                                                motionBitmap.stayPoint2 = indexOf2 + 3;
                                                motionBitmap.isBackground = false;
                                                motionBitmap.setAlpha(0.0f, 0.0f);
                                                str5 = str2;
                                                str4 = str;
                                                it = it2;
                                                bitmap2 = null;
                                            }
                                        }
                                        Log.d("KeyguardMotionWallpaper", "Calculated crop size error");
                                    }
                                }
                                matrix = null;
                                if (matrix != null) {
                                }
                                int indexOf22 = keyguardMotionWallpaper3.mMotionBitmapList.indexOf(motionBitmap) * 30;
                                motionBitmap.stayPoint1 = indexOf22 - 3;
                                motionBitmap.stayPoint2 = indexOf22 + 3;
                                motionBitmap.isBackground = false;
                                motionBitmap.setAlpha(0.0f, 0.0f);
                                str5 = str2;
                                str4 = str;
                                it = it2;
                                bitmap2 = null;
                            }
                        }
                        return bitmap2;
                    }
                }
                int identifier2 = keyguardMotionWallpaper.mPkgResources.getIdentifier(keyguardMotionWallpaper.mXmlName, "layout", keyguardMotionWallpaper.mPkgName);
                StringBuilder sb7 = new StringBuilder();
                sb7.append(keyguardMotionWallpaper.mIsPreview ? "(Preview)" : "");
                sb7.append("pkg name (");
                sb7.append(keyguardMotionWallpaper.mPkgName);
                sb7.append(") xml name(");
                sb7.append(keyguardMotionWallpaper.mXmlName);
                sb7.append(")");
                Log.d("KeyguardMotionWallpaper", sb7.toString());
                if (identifier2 == 0) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(keyguardMotionWallpaper.mIsPreview ? "(Preview)" : "");
                    sb8.append("ERROR - chosen xml name(");
                    sb8.append(keyguardMotionWallpaper.mXmlName);
                    sb8.append(") resource is not exist !!!");
                    Log.e("KeyguardMotionWallpaper", sb8.toString());
                } else {
                    XmlResourceParser xml = keyguardMotionWallpaper.mPkgResources.getXml(identifier2);
                    if (xml != null) {
                        arrayList = keyguardMotionWallpaper.parseXML(xml);
                        if (arrayList != null) {
                            KeyguardMotionWallpaper keyguardMotionWallpaper22 = KeyguardMotionWallpaper.this;
                            arrayList2 = keyguardMotionWallpaper22.mMotionBitmapList;
                            if (arrayList2 != null) {
                            }
                            KeyguardMotionWallpaper keyguardMotionWallpaper32 = KeyguardMotionWallpaper.this;
                            keyguardMotionWallpaper32.mMotionBitmapList = arrayList;
                            StringBuilder sb32 = new StringBuilder();
                            sb32.append(!keyguardMotionWallpaper32.mIsPreview ? "(Preview)" : "");
                            sb32.append("BITMAP LOAD START ");
                            sb32.append(keyguardMotionWallpaper32);
                            Log.d("KeyguardMotionWallpaper", sb32.toString());
                            it = keyguardMotionWallpaper32.mMotionBitmapList.iterator();
                            while (it.hasNext()) {
                            }
                        }
                        return bitmap2;
                    }
                }
                arrayList = null;
                if (arrayList != null) {
                }
                return bitmap2;
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                ArrayList arrayList = KeyguardMotionWallpaper.this.mMotionBitmapList;
                if (arrayList == null || arrayList.size() == 0) {
                    Log.d("KeyguardMotionWallpaper", (KeyguardMotionWallpaper.this.mIsPreview ? "(Preview)" : "").concat("PARSE FAILED"));
                    KeyguardMotionWallpaper.this.mDrawingState = 2;
                    return;
                }
                Iterator it = KeyguardMotionWallpaper.this.mMotionBitmapList.iterator();
                while (it.hasNext()) {
                    ((MotionBitmap) it.next()).bitmapLoaded = true;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(KeyguardMotionWallpaper.this.mIsPreview ? "(Preview)" : "");
                sb2.append("BITMAP LOAD FINISH ");
                sb2.append(this);
                Log.d("KeyguardMotionWallpaper", sb2.toString());
                try {
                    ArrayList arrayList2 = KeyguardMotionWallpaper.this.mMotionBitmapList;
                    arrayList2.add((MotionBitmap) ((MotionBitmap) arrayList2.get(0)).clone());
                    ArrayList arrayList3 = KeyguardMotionWallpaper.this.mMotionBitmapList;
                    arrayList3.add((MotionBitmap) ((MotionBitmap) arrayList3.get(1)).clone());
                    KeyguardMotionWallpaper.this.mMotionBitmapList.remove(1);
                } catch (CloneNotSupportedException unused) {
                    Log.e("KeyguardMotionWallpaper", (KeyguardMotionWallpaper.this.mIsPreview ? "(Preview)" : "").concat("CloneNotSupportedException"));
                } catch (IndexOutOfBoundsException unused2) {
                    Log.e("KeyguardMotionWallpaper", (KeyguardMotionWallpaper.this.mIsPreview ? "(Preview)" : "").concat("IndexOutOfBoundsException"));
                }
                for (int i = 0; i < KeyguardMotionWallpaper.this.mMotionBitmapList.size(); i++) {
                    MotionBitmap motionBitmap = (MotionBitmap) KeyguardMotionWallpaper.this.mMotionBitmapList.get(i);
                    int i2 = i * 30;
                    motionBitmap.stayPoint1 = i2 - 3;
                    motionBitmap.stayPoint2 = i2 + 3;
                }
                ArrayList arrayList4 = KeyguardMotionWallpaper.this.mMotionBitmapList;
                if (arrayList4 != null) {
                    Iterator it2 = arrayList4.iterator();
                    Log.d("KeyguardMotionWallpaper", "it = " + it2.hasNext());
                    int i3 = 0;
                    while (it2.hasNext()) {
                        MotionBitmap motionBitmap2 = (MotionBitmap) it2.next();
                        StringBuilder sb3 = new StringBuilder("layer ");
                        int i4 = i3 + 1;
                        sb3.append(i3);
                        sb3.append(" ");
                        StringBuilder m2m = AbstractC0000x2c234b15.m2m(sb3.toString(), "URL :");
                        m2m.append(motionBitmap2.image);
                        m2m.append(" / ");
                        AbstractC0000x2c234b15.m3m("content = ", ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0000x2c234b15.m2m(ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0000x2c234b15.m2m(ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0000x2c234b15.m2m(m2m.toString(), "type :"), motionBitmap2.type, " / "), "stayPoint1 :"), motionBitmap2.stayPoint1, " / "), "stayPoint2 :"), motionBitmap2.stayPoint2, " / "), "KeyguardMotionWallpaper");
                        i3 = i4;
                    }
                } else {
                    Log.e("KeyguardMotionWallpaper", "layers = null");
                }
                KeyguardMotionWallpaper keyguardMotionWallpaper = KeyguardMotionWallpaper.this;
                if (keyguardMotionWallpaper.mWallpaperResultCallback != null && keyguardMotionWallpaper.mMotionBitmapList.size() > 0) {
                    KeyguardMotionWallpaper keyguardMotionWallpaper2 = KeyguardMotionWallpaper.this;
                    keyguardMotionWallpaper2.mWallpaperResultCallback.onDelegateBitmapReady(((MotionBitmap) keyguardMotionWallpaper2.mMotionBitmapList.get(0)).image);
                }
                KeyguardMotionWallpaper.this.init();
                KeyguardMotionWallpaper keyguardMotionWallpaper3 = KeyguardMotionWallpaper.this;
                keyguardMotionWallpaper3.clearData(keyguardMotionWallpaper3.mOldBitmapList, true);
            }
        };
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mIsPreview ? "(Preview)" : "");
        sb2.append("updateWallpaper: start load = ");
        sb2.append(this.mLoader);
        Log.i("KeyguardMotionWallpaper", sb2.toString());
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.wallpaper.view.KeyguardMotionWallpaper$2] */
    public KeyguardMotionWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, Consumer<Boolean> consumer, String str, String str2, boolean z, int i) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, z);
        this.mRotation = 0;
        this.mMotionBitmapList = new ArrayList();
        this.mOldBitmapList = new ArrayList();
        this.mIsSensorRegistered = false;
        this.mCurrentWhich = 2;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.view.KeyguardMotionWallpaper.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                KeyguardMotionWallpaper keyguardMotionWallpaper = KeyguardMotionWallpaper.this;
                float[] fArr = (float[]) message.obj;
                int i2 = KeyguardMotionWallpaper.$r8$clinit;
                keyguardMotionWallpaper.getClass();
                float f = fArr[0];
                float f2 = fArr[1];
                ValueAnimator valueAnimator = keyguardMotionWallpaper.mAlphaAnimator;
                if (valueAnimator == null) {
                    keyguardMotionWallpaper.startAlphaAnimator(f, f2, false);
                } else if (valueAnimator.isRunning()) {
                    keyguardMotionWallpaper.mAlphaAnimator.end();
                    keyguardMotionWallpaper.startAlphaAnimator(f, f2, true);
                }
            }
        };
        this.mInterpolator = new SineOut33();
        this.mPrevAngularSum = 0.0f;
        this.mAngularSum = 0.0f;
        this.mDeltaOfAngularSum = 0.0f;
        setWillNotDraw(false);
        this.mContext = context;
        this.mCurrentWhich = i;
        this.mWallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensorManager = sensorManager;
        this.mInterruptedGyro = sensorManager.getDefaultSensor(65579);
        this.mFixedOrientationController = new FixedOrientationController(this);
        Paint paint = new Paint();
        this.mBlendingPaint = paint;
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        this.mPkgName = str;
        this.mXmlName = str2;
        this.mWallpaperResultCallback = wallpaperResultCallback;
        updateWallpaper();
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
