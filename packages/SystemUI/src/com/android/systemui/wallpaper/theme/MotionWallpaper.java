package com.android.systemui.wallpaper.theme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.view.animation.SineOut33;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes3.dex */
public class MotionWallpaper extends FrameLayout implements SensorEventListener {
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

    public class MotionBitmap implements Cloneable {
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
            float f3 = MotionWallpaper.this.mRangeOfRotation;
            float f4 = f2 % f3;
            this.calculatedSum = f4;
            if (f4 > r2 - 3) {
                this.calculatedSum = f4 - f3;
            } else if (f4 < -3.0f) {
                this.calculatedSum = f4 + f3;
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(this.calculatedSum, "MotionWallpaper", new StringBuilder("calculatedSum = "));
            float f5 = this.calculatedSum;
            int i = this.stayPoint1;
            float f6 = i;
            if (f5 < f6 || f5 > this.stayPoint2) {
                if (this.isBackground) {
                    this.isBackground = false;
                }
                float f7 = i == -3 ? MotionWallpaper.this.mRangeOfRotation + f6 : f6;
                if (f5 <= this.stayPoint2 || f5 >= r8 + 24) {
                    if (f5 < f7 && f5 > f7 - 24.0f && !this.isBackground && !z) {
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
                if (i == -3) {
                    f6 += MotionWallpaper.this.mRangeOfRotation;
                }
                float f8 = this.stayPoint2;
                if (f5 > f8 && f5 < r11 + 24) {
                    this.alpha = (int) ((Math.abs(f5 - f8) / 24.0f) * 255.0f);
                    RecyclerView$$ExternalSyntheticOutline0.m(this.alpha, "MotionWallpaper", new StringBuilder("Foreground alpha = "));
                } else if (f5 >= f6 || f5 <= f6 - 24.0f) {
                    this.alpha = 255;
                    Log.d("MotionWallpaper", "disappear!!");
                } else {
                    this.alpha = (int) ((Math.abs(f5 - f6) / 24.0f) * 255.0f);
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

    /* JADX WARN: Removed duplicated region for block: B:66:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init() throws Resources.NotFoundException {
        Consumer consumer;
        float f;
        float f2;
        int dimensionPixelOffset;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("init() : ");
        sb.append(this);
        Log.d("MotionWallpaper", sb.toString());
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList == null) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("mMotionBitmapList == null || mMotionBitmapList.size() == 0"));
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (!((MotionBitmap) obj).bitmapLoaded) {
                Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("bitmapLoaded == false"));
                return;
            }
        }
        Context context = this.mContext;
        int height = getHeight();
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        if (context != null) {
            WindowInsets rootWindowInsets = getRootWindowInsets();
            DisplayCutout displayCutout = rootWindowInsets != null ? rootWindowInsets.getDisplayCutout() : null;
            if (displayCutout != null) {
                dimensionPixelOffset = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom();
                Log.d("WallpaperUtils", "updateStatusBarHeight - dc = " + displayCutout);
            } else {
                dimensionPixelOffset = -1;
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m(dimensionPixelOffset, "Height from dc = ", "WallpaperUtils");
            if (dimensionPixelOffset <= 0) {
                dimensionPixelOffset = context.getResources().getDimensionPixelOffset(17106379);
                ListPopupWindow$$ExternalSyntheticOutline0.m(dimensionPixelOffset, "Height from resource = ", "WallpaperUtils");
            }
            Log.i("WallpaperUtils", "statusbar statusBarSize = " + dimensionPixelOffset + ", view height = " + height);
            if (height == dimensionPixelOffset) {
                return;
            }
        }
        this.mViewWidth = (getWidth() - ((FrameLayout) this).mPaddingLeft) - ((FrameLayout) this).mPaddingRight;
        int height2 = (getHeight() - ((FrameLayout) this).mPaddingTop) - ((FrameLayout) this).mPaddingBottom;
        this.mViewHeight = height2;
        if (this.mViewWidth == 0 || height2 == 0) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("mViewWidth == 0 || mViewHeight == 0"));
            return;
        }
        this.mRangeOfRotation = this.mMotionBitmapList.size() * 30;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mIsPreview ? "(Preview)" : "");
        sb2.append("mRangeOfRotation = ");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mRangeOfRotation, "MotionWallpaper", sb2);
        this.mPrevAngularSum = 0.0f;
        this.mAngularSum = 0.0f;
        this.mDeltaOfAngularSum = 0.0f;
        this.mTimestamp = 0L;
        ArrayList arrayList2 = this.mMotionBitmapList;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            MotionBitmap motionBitmap = (MotionBitmap) obj2;
            Bitmap bitmap = motionBitmap.image;
            if (bitmap == null || bitmap.isRecycled()) {
                Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("bitmap is wrong."));
                consumer = this.mUpdateCallback;
                if (consumer == null) {
                    consumer.accept(0);
                    return;
                }
                return;
            }
            int width = motionBitmap.image.getWidth();
            int height3 = motionBitmap.image.getHeight();
            int i3 = this.mViewHeight;
            int i4 = width * i3;
            int i5 = this.mViewWidth;
            if (i4 > i5 * height3) {
                f = i3;
                f2 = height3;
            } else {
                f = i5;
                f2 = width;
            }
            float f3 = (f / f2) * 1.0f;
            float fM = Frame$$ExternalSyntheticOutline0.m(width, f3, i5, 0.5f);
            float fM2 = Frame$$ExternalSyntheticOutline0.m(height3, f3, i3, 0.5f);
            int iRound = Math.round(fM);
            int iRound2 = Math.round(fM2);
            motionBitmap.matrix.setScale(f3, f3);
            motionBitmap.matrix.postTranslate(iRound, iRound2);
            motionBitmap.isBackground = false;
            motionBitmap.setAlpha(0.0f, 0.0f);
        }
        consumer = this.mUpdateCallback;
        if (consumer == null) {
        }
    }

    public final void initializeMotionBitmaps() throws Resources.NotFoundException {
        boolean z;
        Bitmap bitmap;
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList == null || arrayList.size() == 0) {
            Log.d("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("PARSE FAILED"));
            return;
        }
        ArrayList arrayList2 = this.mMotionBitmapList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((MotionBitmap) obj).bitmapLoaded = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsPreview ? "(Preview)" : "");
        sb.append("BITMAP LOAD FINISH ");
        sb.append(this);
        Log.d("MotionWallpaper", sb.toString());
        try {
            ArrayList arrayList3 = this.mMotionBitmapList;
            arrayList3.add((MotionBitmap) ((MotionBitmap) arrayList3.get(0)).clone());
            ArrayList arrayList4 = this.mMotionBitmapList;
            arrayList4.add((MotionBitmap) ((MotionBitmap) arrayList4.get(1)).clone());
            this.mMotionBitmapList.remove(1);
        } catch (CloneNotSupportedException unused) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("CloneNotSupportedException"));
        } catch (IndexOutOfBoundsException unused2) {
            Log.e("MotionWallpaper", (this.mIsPreview ? "(Preview)" : "").concat("IndexOutOfBoundsException"));
        }
        for (int i2 = 0; i2 < this.mMotionBitmapList.size(); i2++) {
            MotionBitmap motionBitmap = (MotionBitmap) this.mMotionBitmapList.get(i2);
            int i3 = i2 * 30;
            motionBitmap.stayPoint1 = i3 - 3;
            motionBitmap.stayPoint2 = i3 + 3;
        }
        ArrayList arrayList5 = this.mMotionBitmapList;
        if (arrayList5 != null) {
            Iterator it = arrayList5.iterator();
            Log.d("MotionWallpaper", "it = " + it.hasNext());
            int i4 = 0;
            while (it.hasNext()) {
                MotionBitmap motionBitmap2 = (MotionBitmap) it.next();
                StringBuilder sb2 = new StringBuilder("layer ");
                int i5 = i4 + 1;
                sb2.append(i4);
                sb2.append(" ");
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb2.toString(), "URL :");
                sbM.append(motionBitmap2.image);
                sbM.append(" / ");
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("content = ", ReorderTile$$ExternalSyntheticOutline0.m(motionBitmap2.stayPoint2, " / ", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(motionBitmap2.stayPoint1, " / ", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(motionBitmap2.type, " / ", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sbM.toString(), "type :")), "stayPoint1 :")), "stayPoint2 :")), "MotionWallpaper");
                i4 = i5;
            }
        } else {
            Log.e("MotionWallpaper", "layers = null");
        }
        init();
        ArrayList arrayList6 = this.mOldBitmapList;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mIsPreview ? "(Preview)" : "");
        sb3.append("clearData: ");
        sb3.append(arrayList6.size());
        Log.i("MotionWallpaper", sb3.toString());
        int size2 = arrayList6.size();
        int i6 = 0;
        while (i6 < size2) {
            Object obj2 = arrayList6.get(i6);
            i6++;
            MotionBitmap motionBitmap3 = (MotionBitmap) obj2;
            motionBitmap3.bitmapLoaded = false;
            if (motionBitmap3.image != null) {
                ArrayList arrayList7 = this.mMotionBitmapList;
                int size3 = arrayList7.size();
                int i7 = 0;
                while (true) {
                    z = true;
                    while (i7 < size3) {
                        Object obj3 = arrayList7.get(i7);
                        i7++;
                        if (motionBitmap3.image != ((MotionBitmap) obj3).image) {
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
        arrayList6.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0078 A[Catch: Exception -> 0x0019, TryCatch #2 {Exception -> 0x0019, blocks: (B:3:0x0009, B:6:0x0016, B:10:0x0028, B:12:0x0046, B:16:0x006c, B:18:0x0070, B:20:0x0078, B:24:0x0090, B:26:0x00b6, B:30:0x00c2, B:31:0x00dc, B:33:0x00e4, B:15:0x0068, B:9:0x001c), top: B:96:0x0009, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadMotionWallpaperBitmaps() throws XmlPullParserException, Resources.NotFoundException, PackageManager.NameNotFoundException {
        ArrayList xml;
        String str;
        Resources resourcesForApplication;
        try {
            XmlPullParserFactory.newInstance().newPullParser();
            if (this.mIsPreview) {
                this.mXmlName = "motion";
            } else {
                this.mPkgName = this.mWallpaperManager.getMotionWallpaperPkgName(this.mCurrentWhich);
                this.mXmlName = "motion";
            }
            str = "/data/overlays/main_packages/" + this.mPkgName + ".apk";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (new File(str).exists()) {
            try {
                PackageInfo packageArchiveInfo = this.mContext.getPackageManager().getPackageArchiveInfo(str, PackageManager.PackageInfoFlags.of(0L));
                packageArchiveInfo.applicationInfo.publicSourceDir = str;
                resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(packageArchiveInfo.applicationInfo);
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
                resourcesForApplication = null;
            }
            this.mPkgResources = resourcesForApplication;
            if (resourcesForApplication == null) {
                Log.e("MotionWallpaper", "mPkgResources == null");
            } else {
                int identifier = this.mPkgResources.getIdentifier(this.mXmlName, "layout", this.mPkgName);
                StringBuilder sb = new StringBuilder();
                sb.append(this.mIsPreview ? "(Preview)" : "");
                sb.append("pkg name (");
                sb.append(this.mPkgName);
                sb.append(") xml name(");
                sb.append(this.mXmlName);
                sb.append(")");
                Log.d("MotionWallpaper", sb.toString());
                if (identifier == 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.mIsPreview ? "(Preview)" : "");
                    sb2.append("ERROR - chosen xml name(");
                    sb2.append(this.mXmlName);
                    sb2.append(") resource is not exist !!!");
                    Log.e("MotionWallpaper", sb2.toString());
                } else {
                    XmlResourceParser xml2 = this.mPkgResources.getXml(identifier);
                    if (xml2 != null) {
                        xml = parseXML(xml2);
                    }
                }
            }
            xml = null;
        }
        if (xml == null || xml.size() <= 0) {
            return;
        }
        ArrayList arrayList = this.mMotionBitmapList;
        if (arrayList != null) {
            int size = arrayList.size();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.mIsPreview ? "(Preview)" : "");
            sb3.append("collectOldBitmap: size = ");
            sb3.append(size);
            Log.i("MotionWallpaper", sb3.toString());
            if (size > 0) {
                ArrayList arrayList2 = this.mMotionBitmapList;
                int size2 = arrayList2.size();
                int i = 0;
                while (i < size2) {
                    Object obj = arrayList2.get(i);
                    i++;
                    this.mOldBitmapList.add((MotionBitmap) obj);
                }
            }
        }
        this.mMotionBitmapList = xml;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.mIsPreview ? "(Preview)" : "");
        sb4.append("BITMAP LOAD START ");
        sb4.append(this);
        Log.d("MotionWallpaper", sb4.toString());
        ArrayList arrayList3 = this.mMotionBitmapList;
        int size3 = arrayList3.size();
        int i2 = 0;
        while (i2 < size3) {
            Object obj2 = arrayList3.get(i2);
            i2++;
            MotionBitmap motionBitmap = (MotionBitmap) obj2;
            int i3 = motionBitmap.type;
            if (i3 == 0) {
                try {
                    int identifier2 = this.mPkgResources.getIdentifier(motionBitmap.path, "drawable", this.mPkgName);
                    if (identifier2 > 0) {
                        Bitmap bitmap = ((BitmapDrawable) this.mPkgResources.getDrawable(identifier2)).getBitmap();
                        motionBitmap.image = bitmap.copy(bitmap.getConfig(), true);
                    } else {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(this.mIsPreview ? "(Preview)" : "");
                        sb5.append("Fail to get drawable");
                        Log.w("MotionWallpaper", sb5.toString());
                    }
                } catch (Exception e3) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(this.mIsPreview ? "(Preview)" : "");
                    sb6.append("loadDrawable exception");
                    sb6.append(e3.toString());
                    Log.e("MotionWallpaper", sb6.toString());
                }
            } else if (i3 == 1) {
                File file = new File(motionBitmap.path);
                if (!file.exists() || !file.canRead()) {
                    return;
                }
                try {
                    Bitmap bitmap2 = new BitmapDrawable(this.mPkgResources, motionBitmap.path).getBitmap();
                    motionBitmap.image = bitmap2.copy(bitmap2.getConfig(), true);
                } catch (Exception e4) {
                    e4.printStackTrace();
                    return;
                }
            } else if (i3 == 2) {
                motionBitmap.image = null;
            }
            Matrix matrix = new Matrix();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(this.mIsPreview ? "(Preview)" : "");
            sb7.append("loadWallpapers: matrix ");
            sb7.append(matrix);
            Log.i("MotionWallpaper", sb7.toString());
            motionBitmap.matrix = matrix;
            int iIndexOf = this.mMotionBitmapList.indexOf(motionBitmap) * 30;
            motionBitmap.stayPoint1 = iIndexOf - 3;
            motionBitmap.stayPoint2 = iIndexOf + 3;
            motionBitmap.isBackground = false;
            motionBitmap.setAlpha(0.0f, 0.0f);
        }
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
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(f3, "MotionWallpaper", CubicBezierEasing$$ExternalSyntheticOutline0.m("axisX: ", f, ", axisY: ", f2, ", axisZ: "));
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
            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("axisX: ", f, ", axisY: ", f2, ", axisZ: ");
            sbM.append(f3);
            Log.d("MotionWallpaper", sbM.toString());
            StringBuilder sb = new StringBuilder("mAngularSum: ");
            sb.append(this.mAngularSum);
            sb.append(", mDeltaOfAngularSum: ");
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(this.mDeltaOfAngularSum, "MotionWallpaper", sb);
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

    public final ArrayList parseXML(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
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
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(f2, "MotionWallpaper", sb);
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
            /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f4 = f2;
                MotionWallpaper motionWallpaper = MotionWallpaper.this;
                boolean z2 = true;
                if (f4 < motionWallpaper.mPrevStartAngularSum) {
                    float f5 = motionWallpaper.mAnimatedAngularSum;
                    float f6 = f5 - ((f5 - f4) * 0.05f);
                    motionWallpaper.mAnimatedAngularSum = f6;
                    if (f6 < f4) {
                        z2 = false;
                    }
                } else {
                    float f7 = motionWallpaper.mAnimatedAngularSum;
                    float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f4, f7, 0.05f, f7);
                    motionWallpaper.mAnimatedAngularSum = fM$1;
                    if (fM$1 > f4) {
                    }
                }
                if (Math.abs(motionWallpaper.mPrevAnimatedAngularSum - motionWallpaper.mAnimatedAngularSum) <= 1.0E-4f || !z2) {
                    return;
                }
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(MotionWallpaper.this.mAnimatedAngularSum, "MotionWallpaper", new StringBuilder("animatedAngle = "));
                for (int i = 0; i < MotionWallpaper.this.mMotionBitmapList.size(); i++) {
                    MotionBitmap motionBitmap = (MotionBitmap) MotionWallpaper.this.mMotionBitmapList.get(i);
                    MotionWallpaper motionWallpaper2 = MotionWallpaper.this;
                    motionBitmap.setAlpha(motionWallpaper2.mPrevAnimatedAngularSum, motionWallpaper2.mAnimatedAngularSum);
                }
                MotionWallpaper motionWallpaper3 = MotionWallpaper.this;
                Consumer consumer = motionWallpaper3.mUpdateCallback;
                if (consumer != null) {
                    motionWallpaper3.getClass();
                    consumer.accept(0);
                }
                MotionWallpaper motionWallpaper4 = MotionWallpaper.this;
                motionWallpaper4.mPrevAnimatedAngularSum = motionWallpaper4.mAnimatedAngularSum;
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
    public MotionWallpaper(Context context, String str, String str2, boolean z, int i, Consumer<Integer> consumer, boolean z2) throws XmlPullParserException, Resources.NotFoundException, PackageManager.NameNotFoundException {
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
            public final Object doInBackground(Object[] objArr) throws XmlPullParserException, Resources.NotFoundException, PackageManager.NameNotFoundException {
                MotionWallpaper motionWallpaper = MotionWallpaper.this;
                int i2 = MotionWallpaper.$r8$clinit;
                motionWallpaper.loadMotionWallpaperBitmaps();
                return null;
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) throws Resources.NotFoundException {
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
