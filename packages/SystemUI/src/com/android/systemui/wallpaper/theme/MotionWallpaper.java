package com.android.systemui.wallpaper.theme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.DisplayInfo;
import android.widget.FrameLayout;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.samsung.android.view.animation.SineOut33;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MotionWallpaper extends FrameLayout implements SensorEventListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ValueAnimator mAlphaAnimator;
    public float mAngularSum;
    public float mAnimatedAngularSum;
    public final Paint mBlendingPaint;
    public final Context mContext;
    public final int mCurrentWhich;
    public float mDeltaOfAngularSum;
    public final SineOut33 mInterpolator;
    public final Sensor mInterruptedGyro;
    public final boolean mIsPreview;
    public boolean mIsSensorRegistered;
    public final AnonymousClass1 mLoader;
    public ArrayList mMotionBitmapList;
    public final ArrayList mOldBitmapList;
    public String mPkgName;
    public Resources mPkgResources;
    public float mPrevAngularSum;
    public float mPrevAnimatedAngularSum;
    public float mPrevStartAngularSum;
    public int mRangeOfRotation;
    public final SensorManager mSensorManager;
    public long mTimestamp;
    public final Consumer mUpdateCallback;
    public int mViewHeight;
    public int mViewWidth;
    public final WallpaperManager mWallpaperManager;
    public String mXmlName;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            int i = MotionWallpaper.this.mRangeOfRotation;
            float f3 = f2 % i;
            this.calculatedSum = f3;
            if (f3 > i - 3) {
                this.calculatedSum = f3 - i;
            } else if (f3 < -3.0f) {
                this.calculatedSum = f3 + i;
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("calculatedSum = "), this.calculatedSum, "MotionWallpaper");
            float f4 = this.calculatedSum;
            int i2 = this.stayPoint1;
            if (f4 < i2 || f4 > this.stayPoint2) {
                if (this.isBackground) {
                    this.isBackground = false;
                }
                float f5 = i2;
                if (i2 == -3) {
                    f5 += MotionWallpaper.this.mRangeOfRotation;
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
                    f6 += MotionWallpaper.this.mRangeOfRotation;
                }
                int i3 = this.stayPoint2;
                if (f4 > i3 && f4 < i3 + 24) {
                    this.alpha = (int) ((Math.abs(f4 - i3) / 24.0f) * 255.0f);
                    RecyclerView$$ExternalSyntheticOutline0.m(this.alpha, "MotionWallpaper", new StringBuilder("Foreground alpha = "));
                } else if (f4 >= f6 || f4 <= f6 - 24.0f) {
                    this.alpha = 255;
                    Log.d("MotionWallpaper", "disappear!!");
                } else {
                    this.alpha = (int) ((Math.abs(f4 - f6) / 24.0f) * 255.0f);
                    RecyclerView$$ExternalSyntheticOutline0.m(this.alpha, "MotionWallpaper", new StringBuilder("Foreground alpha = "));
                }
            }
            if (this.alpha > 255) {
                this.alpha = 255;
            }
            this.alpha = 255 - this.alpha;
        }
    }

    public MotionWallpaper(Context context, int i, boolean z) {
        this(context, null, null, false, i, null, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void init() {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.theme.MotionWallpaper.init():void");
    }

    public final void initializeMotionBitmaps() {
        boolean z;
        Bitmap bitmap;
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList == null || arrayList.size() == 0) {
            Log.d("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("PARSE FAILED"));
            return;
        }
        Iterator it = this.mMotionBitmapList.iterator();
        while (it.hasNext()) {
            ((MotionBitmap) it.next()).bitmapLoaded = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("BITMAP LOAD FINISH ");
        sb.append(this);
        Log.d("MotionWallpaper", sb.toString());
        try {
            ArrayList arrayList2 = this.mMotionBitmapList;
            arrayList2.add((MotionBitmap) ((MotionBitmap) arrayList2.get(0)).clone());
            ArrayList arrayList3 = this.mMotionBitmapList;
            arrayList3.add((MotionBitmap) ((MotionBitmap) arrayList3.get(1)).clone());
            this.mMotionBitmapList.remove(1);
        } catch (CloneNotSupportedException unused) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("CloneNotSupportedException"));
        } catch (IndexOutOfBoundsException unused2) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("IndexOutOfBoundsException"));
        }
        for (int i = 0; i < this.mMotionBitmapList.size(); i++) {
            MotionBitmap motionBitmap = (MotionBitmap) this.mMotionBitmapList.get(i);
            int i2 = i * 30;
            motionBitmap.stayPoint1 = i2 - 3;
            motionBitmap.stayPoint2 = i2 + 3;
        }
        ArrayList arrayList4 = this.mMotionBitmapList;
        if (arrayList4 != null) {
            Iterator it2 = arrayList4.iterator();
            Log.d("MotionWallpaper", "it = " + it2.hasNext());
            int i3 = 0;
            while (it2.hasNext()) {
                MotionBitmap motionBitmap2 = (MotionBitmap) it2.next();
                StringBuilder sb2 = new StringBuilder("layer ");
                int i4 = i3 + 1;
                sb2.append(i3);
                sb2.append(" ");
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb2.toString(), "URL :");
                m.append(motionBitmap2.image);
                m.append(" / ");
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("content = ", Anchor$$ExternalSyntheticOutline0.m(motionBitmap2.stayPoint2, " / ", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(Anchor$$ExternalSyntheticOutline0.m(motionBitmap2.stayPoint1, " / ", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(Anchor$$ExternalSyntheticOutline0.m(motionBitmap2.type, " / ", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m.toString(), "type :")), "stayPoint1 :")), "stayPoint2 :")), "MotionWallpaper");
                i3 = i4;
            }
        } else {
            Log.e("MotionWallpaper", "layers = null");
        }
        init();
        ArrayList arrayList5 = this.mOldBitmapList;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mIsPreview ? "(Preview)" : "");
        sb3.append("clearData: ");
        sb3.append(arrayList5.size());
        Log.i("MotionWallpaper", sb3.toString());
        Iterator it3 = arrayList5.iterator();
        while (it3.hasNext()) {
            MotionBitmap motionBitmap3 = (MotionBitmap) it3.next();
            motionBitmap3.bitmapLoaded = false;
            if (motionBitmap3.image != null) {
                Iterator it4 = this.mMotionBitmapList.iterator();
                while (true) {
                    z = true;
                    while (it4.hasNext()) {
                        if (motionBitmap3.image != ((MotionBitmap) it4.next()).image) {
                            break;
                        } else {
                            z = false;
                        }
                    }
                }
            } else {
                z = true;
            }
            if (z && (bitmap = motionBitmap3.image) != null && !bitmap.isRecycled()) {
                motionBitmap3.image.recycle();
                motionBitmap3.image = null;
            }
        }
        arrayList5.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0248 A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadMotionWallpaperBitmaps() {
        /*
            Method dump skipped, instructions count: 585
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.theme.MotionWallpaper.loadMotionWallpaperBitmaps():void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsSensorRegistered) {
            Log.d("MotionWallpaper", "unregisterSensor");
            this.mSensorManager.unregisterListener(this);
            this.mIsSensorRegistered = false;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("onDraw()");
        sb.append(this);
        Log.d("MotionWallpaper", sb.toString());
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList == null || arrayList.size() == 0) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("mBitmapImageList == null || mBitmapImageList.size() == 0"));
            return;
        }
        int size = this.mMotionBitmapList.size();
        for (int i = 0; i < size; i++) {
            if (!((MotionBitmap) this.mMotionBitmapList.get(i)).bitmapLoaded) {
                Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("bitmapLoaded == false"));
                return;
            }
        }
        int i2 = 0;
        while (true) {
            if (i2 >= this.mMotionBitmapList.size()) {
                break;
            }
            if (((MotionBitmap) this.mMotionBitmapList.get(i2)).isBackground) {
                this.mBlendingPaint.setAlpha(((MotionBitmap) this.mMotionBitmapList.get(i2)).alpha);
                Bitmap bitmap = ((MotionBitmap) this.mMotionBitmapList.get(i2)).image;
                if (bitmap != null && !bitmap.isRecycled()) {
                    canvas.drawBitmap(bitmap, ((MotionBitmap) this.mMotionBitmapList.get(i2)).matrix, this.mBlendingPaint);
                    break;
                }
                Log.e("MotionWallpaper", "onDraw: recycled bitmap1");
                StringBuilder sb2 = new StringBuilder("drawBackground!!!! ===> ");
                sb2.append(i2);
                sb2.append(", alpha = ");
                RecyclerView$$ExternalSyntheticOutline0.m(((MotionBitmap) this.mMotionBitmapList.get(i2)).alpha, "MotionWallpaper", sb2);
            }
            i2++;
        }
        for (int i3 = 0; i3 < this.mMotionBitmapList.size(); i3++) {
            int i4 = ((MotionBitmap) this.mMotionBitmapList.get(i3)).alpha;
            if (!((MotionBitmap) this.mMotionBitmapList.get(i3)).isBackground && i4 != 0) {
                this.mBlendingPaint.setAlpha(i4);
                Bitmap bitmap2 = ((MotionBitmap) this.mMotionBitmapList.get(i3)).image;
                if (bitmap2 == null || bitmap2.isRecycled()) {
                    Log.e("MotionWallpaper", "onDraw: recycled bitmap2");
                } else {
                    canvas.drawBitmap(bitmap2, ((MotionBitmap) this.mMotionBitmapList.get(i3)).matrix, this.mBlendingPaint);
                }
                RecyclerView$$ExternalSyntheticOutline0.m(((MotionBitmap) this.mMotionBitmapList.get(i3)).alpha, "MotionWallpaper", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "drawForeground!!!! ===> ", ", alpha = "));
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        ValueAnimator valueAnimator;
        Log.d("MotionWallpaper", "onSensorChanged: " + sensorEvent.sensor.getType() + " , mTimestamp = " + this.mTimestamp + " , timestamp = " + sensorEvent.timestamp);
        if (sensorEvent.sensor.getType() != 65579) {
            return;
        }
        if (this.mTimestamp != 0) {
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            DisplayInfo displayInfo = new DisplayInfo();
            ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getDisplay(0).getDisplayInfo(displayInfo);
            int i = displayInfo.rotation;
            boolean z = i == 1 || i == 3;
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(CubicBezierEasing$$ExternalSyntheticOutline0.m("axisX: ", f, ", axisY: ", f2, ", axisZ: "), f3, "MotionWallpaper");
            if (!z && (Math.abs(f) > Math.abs(f2) || Math.abs(f3) > Math.abs(f2))) {
                Log.e("MotionWallpaper", "axisY is not biggest, stop animation");
                return;
            }
            if (z && (Math.abs(f2) > Math.abs(f) || Math.abs(f3) > Math.abs(f))) {
                Log.e("MotionWallpaper", "axisX is not biggest, stop animation");
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
            StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("axisX: ", f, ", axisY: ", f2, ", axisZ: ");
            m.append(f3);
            Log.d("MotionWallpaper", m.toString());
            StringBuilder sb = new StringBuilder("mAngularSum: ");
            sb.append(this.mAngularSum);
            sb.append(", mDeltaOfAngularSum: ");
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, this.mDeltaOfAngularSum, "MotionWallpaper");
            boolean z2 = false;
            for (int i2 = 0; i2 < this.mMotionBitmapList.size(); i2++) {
                MotionBitmap motionBitmap = (MotionBitmap) this.mMotionBitmapList.get(i2);
                motionBitmap.prevAlpha = motionBitmap.alpha;
                motionBitmap.setAlpha(this.mPrevAngularSum, this.mAngularSum);
                if (motionBitmap.prevAlpha != motionBitmap.alpha) {
                    if (this.mDeltaOfAngularSum >= 3.0f || (valueAnimator = this.mAlphaAnimator) == null) {
                        z2 = true;
                    } else if (valueAnimator.isRunning()) {
                        Log.e("MotionWallpaper", "mAlphaAnimator isRunning");
                    }
                }
            }
            Consumer consumer = this.mUpdateCallback;
            if (consumer != null && z2) {
                consumer.accept(0);
            }
        }
        this.mTimestamp = sensorEvent.timestamp;
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

    public final void startAlphaAnimator(float f, final float f2, boolean z) {
        Log.d("MotionWallpaper", "mAlphaAnimator starts");
        StringBuilder sb = new StringBuilder("prevAngularSum = ");
        sb.append(f);
        sb.append(" curAngularSum = ");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, f2, "MotionWallpaper");
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
        this.mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.MotionWallpaper.3
            /* JADX WARN: Code restructure failed: missing block: B:23:0x0028, code lost:
            
                if (r1 <= r7) goto L10;
             */
            /* JADX WARN: Code restructure failed: missing block: B:4:0x0019, code lost:
            
                if (r1 >= r7) goto L10;
             */
            /* JADX WARN: Code restructure failed: missing block: B:5:0x001c, code lost:
            
                r3 = false;
             */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onAnimationUpdate(android.animation.ValueAnimator r7) {
                /*
                    r6 = this;
                    float r7 = r2
                    com.android.systemui.wallpaper.theme.MotionWallpaper r0 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    float r1 = r0.mPrevStartAngularSum
                    int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
                    r2 = 1028443341(0x3d4ccccd, float:0.05)
                    r3 = 1
                    r4 = 0
                    if (r1 >= 0) goto L1e
                    float r1 = r0.mAnimatedAngularSum
                    float r5 = r1 - r7
                    float r5 = r5 * r2
                    float r1 = r1 - r5
                    r0.mAnimatedAngularSum = r1
                    int r7 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
                    if (r7 < 0) goto L1c
                    goto L2a
                L1c:
                    r3 = r4
                    goto L2a
                L1e:
                    float r1 = r0.mAnimatedAngularSum
                    float r1 = androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(r7, r1, r2, r1)
                    r0.mAnimatedAngularSum = r1
                    int r7 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
                    if (r7 > 0) goto L1c
                L2a:
                    float r7 = r0.mPrevAnimatedAngularSum
                    float r0 = r0.mAnimatedAngularSum
                    float r7 = r7 - r0
                    float r7 = java.lang.Math.abs(r7)
                    r0 = 953267991(0x38d1b717, float:1.0E-4)
                    int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                    if (r7 <= 0) goto L83
                    if (r3 == 0) goto L83
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    java.lang.String r0 = "animatedAngle = "
                    r7.<init>(r0)
                    com.android.systemui.wallpaper.theme.MotionWallpaper r0 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    float r0 = r0.mAnimatedAngularSum
                    java.lang.String r1 = "MotionWallpaper"
                    androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0.m(r7, r0, r1)
                    r7 = r4
                L4d:
                    com.android.systemui.wallpaper.theme.MotionWallpaper r0 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    java.util.ArrayList r0 = r0.mMotionBitmapList
                    int r0 = r0.size()
                    if (r7 >= r0) goto L6d
                    com.android.systemui.wallpaper.theme.MotionWallpaper r0 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    java.util.ArrayList r0 = r0.mMotionBitmapList
                    java.lang.Object r0 = r0.get(r7)
                    com.android.systemui.wallpaper.theme.MotionWallpaper$MotionBitmap r0 = (com.android.systemui.wallpaper.theme.MotionWallpaper.MotionBitmap) r0
                    com.android.systemui.wallpaper.theme.MotionWallpaper r1 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    float r2 = r1.mPrevAnimatedAngularSum
                    float r1 = r1.mAnimatedAngularSum
                    r0.setAlpha(r2, r1)
                    int r7 = r7 + 1
                    goto L4d
                L6d:
                    com.android.systemui.wallpaper.theme.MotionWallpaper r7 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    java.util.function.Consumer r0 = r7.mUpdateCallback
                    if (r0 == 0) goto L7d
                    r7.getClass()
                    java.lang.Integer r7 = java.lang.Integer.valueOf(r4)
                    r0.accept(r7)
                L7d:
                    com.android.systemui.wallpaper.theme.MotionWallpaper r6 = com.android.systemui.wallpaper.theme.MotionWallpaper.this
                    float r7 = r6.mAnimatedAngularSum
                    r6.mPrevAnimatedAngularSum = r7
                L83:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.theme.MotionWallpaper.AnonymousClass3.onAnimationUpdate(android.animation.ValueAnimator):void");
            }
        });
        this.mAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.wallpaper.theme.MotionWallpaper.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                MotionWallpaper.this.mAlphaAnimator = null;
            }
        });
        this.mAlphaAnimator.start();
    }

    public MotionWallpaper(Context context, int i, Consumer<Integer> consumer) {
        this(context, null, null, false, i, consumer, false);
    }

    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.wallpaper.theme.MotionWallpaper$1] */
    public MotionWallpaper(Context context, String str, String str2, boolean z, int i, Consumer<Integer> consumer, boolean z2) {
        super(context);
        this.mMotionBitmapList = new ArrayList();
        this.mOldBitmapList = new ArrayList();
        this.mIsSensorRegistered = false;
        this.mCurrentWhich = 2;
        new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.theme.MotionWallpaper.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                float[] fArr = (float[]) message.obj;
                int i2 = MotionWallpaper.$r8$clinit;
                MotionWallpaper motionWallpaper = MotionWallpaper.this;
                motionWallpaper.getClass();
                float f = fArr[0];
                float f2 = fArr[1];
                ValueAnimator valueAnimator = motionWallpaper.mAlphaAnimator;
                if (valueAnimator == null) {
                    motionWallpaper.startAlphaAnimator(f, f2, false);
                } else if (valueAnimator.isRunning()) {
                    motionWallpaper.mAlphaAnimator.end();
                    motionWallpaper.startAlphaAnimator(f, f2, true);
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
        this.mUpdateCallback = consumer;
        Paint paint = new Paint();
        this.mBlendingPaint = paint;
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        this.mPkgName = str;
        this.mXmlName = str2;
        this.mIsPreview = z;
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append(z ? "(Preview)" : "");
            sb.append("updateWallpaperOnMainThread : ");
            sb.append(this);
            Log.i("MotionWallpaper", sb.toString());
            loadMotionWallpaperBitmaps();
            initializeMotionBitmaps();
            return;
        }
        AnonymousClass1 anonymousClass1 = this.mLoader;
        if (anonymousClass1 != null && !anonymousClass1.isCancelled()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(z ? "(Preview)" : "");
            sb2.append(" cancel loader = ");
            sb2.append(this.mLoader);
            Log.i("MotionWallpaper", sb2.toString());
            cancel(true);
            this.mLoader = null;
        }
        this.mLoader = new AsyncTask() { // from class: com.android.systemui.wallpaper.theme.MotionWallpaper.1
            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                MotionWallpaper motionWallpaper = MotionWallpaper.this;
                int i2 = MotionWallpaper.$r8$clinit;
                motionWallpaper.loadMotionWallpaperBitmaps();
                return null;
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                MotionWallpaper motionWallpaper = MotionWallpaper.this;
                int i2 = MotionWallpaper.$r8$clinit;
                motionWallpaper.initializeMotionBitmaps();
            }
        };
        StringBuilder sb3 = new StringBuilder();
        sb3.append(z ? "(Preview)" : "");
        sb3.append("updateWallpaper: start load = ");
        sb3.append(this.mLoader);
        Log.i("MotionWallpaper", sb3.toString());
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
