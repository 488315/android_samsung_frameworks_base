package com.samsung.android.graphics.spr.document.debug;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.shape.SprObjectBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprDebug {
    public static final int DEBUG_HIGH = 3;
    public static final int DEBUG_LOW = 1;
    public static final int DEBUG_MID = 2;
    public static boolean IsDebug = false;
    private static Integer mDebugLevel;
    private static Paint mTextOutlinePaint;
    private static Paint mTextPaint;

    static {
        if ("eng".equals(Build.TYPE)) {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                mDebugLevel = (Integer) cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, "persist.sys.spr.debug", 0);
            } catch (Exception e) {
                e.printStackTrace();
                mDebugLevel = 0;
            }
        } else {
            mDebugLevel = 0;
        }
        IsDebug = mDebugLevel.intValue() >= 1;
    }

    public void dumpPNG(SprDocument sprDocument, int i, int i2, int i3) throws IOException {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        sprDocument.draw(new Canvas(bitmapCreateBitmap), i, i2, 0, i3);
        try {
            File file = new File("/sdcard/spr_debug");
            if (file.mkdir() || file.isDirectory()) {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(file, String.valueOf(sprDocument.hashCode() % 10000) + ".png"));
                bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void drawRect(Canvas canvas, SprDocument sprDocument, int i, int i2) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5.0f);
        paint.setColor(-65536);
        canvas.drawRect(sprDocument.mLeft, sprDocument.mTop, sprDocument.mLeft + i, sprDocument.mTop + i2, paint);
    }

    public static void preDraw(SprObjectBase sprObjectBase) {
        if (mDebugLevel.intValue() < 3 || sprObjectBase.strokePaint == null) {
            return;
        }
        sprObjectBase.isVisibleStroke = true;
        sprObjectBase.strokePaint.setColor(Color.rgb(255, 0, 255));
        if (sprObjectBase.strokePaint.getStrokeWidth() < 2.0f) {
            sprObjectBase.strokePaint.setStrokeWidth(2.0f);
        }
    }

    public static void drawDebugInfo(Canvas canvas, SprDocument sprDocument, int i, int i2, int i3) {
        if (mDebugLevel.intValue() >= 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(sprDocument.isNinePatch() ? GnssSignalType.CODE_TYPE_N : sprDocument.isIntrinsic() ? "" : GnssSignalType.CODE_TYPE_C);
            sb.append(String.valueOf(sprDocument.hashCode() % 10000));
            drawText(canvas, sb.toString(), 20);
            drawText(canvas, sprDocument.mName, 40);
            drawText(canvas, i3 + NavigationBarInflaterView.KEY_CODE_END + i + "x" + i2, 60);
            if (mDebugLevel.intValue() >= 3) {
                drawText(canvas, sprDocument.getLoadingTime() + "ms E:" + sprDocument.getTotalElementCount() + " S:" + sprDocument.getTotalSegmentCount() + " A:" + sprDocument.getTotalAttributeCount(), 80);
            }
        }
    }

    private static void drawText(Canvas canvas, String str, int i) {
        if (mTextOutlinePaint == null) {
            Paint paint = new Paint();
            mTextOutlinePaint = paint;
            paint.setAntiAlias(true);
            mTextOutlinePaint.setTextSize(20.0f);
            mTextOutlinePaint.setStyle(Paint.Style.STROKE);
            mTextOutlinePaint.setColor(-16777216);
            mTextOutlinePaint.setStrokeWidth(4.0f);
        }
        if (mTextPaint == null) {
            Paint paint2 = new Paint();
            mTextPaint = paint2;
            paint2.setAntiAlias(true);
            mTextPaint.setTextSize(20.0f);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setColor(-1);
        }
        float f = i;
        canvas.drawText(str, 5.0f, f, mTextOutlinePaint);
        canvas.drawText(str, 5.0f, f, mTextPaint);
    }
}
