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
import java.io.OutputStream;
import java.lang.reflect.Method;

public class SprDebug {
    public static final int DEBUG_HIGH = 3;
    public static final int DEBUG_LOW = 1;
    public static final int DEBUG_MID = 2;
    public static boolean IsDebug;
    private static Integer mDebugLevel;
    private static Paint mTextPaint = null;
    private static Paint mTextOutlinePaint = null;

    static {
        IsDebug = false;
        mDebugLevel = null;
        if ("eng".equals(Build.TYPE)) {
            try {
                Class<?> clazz = Class.forName("android.os.SystemProperties");
                Method md = clazz.getMethod("getInt", String.class, Integer.TYPE);
                mDebugLevel = (Integer) md.invoke(clazz, "persist.sys.spr.debug", 0);
            } catch (Exception e) {
                e.printStackTrace();
                mDebugLevel = 0;
            }
        } else {
            mDebugLevel = 0;
        }
        IsDebug = mDebugLevel.intValue() >= 1;
    }

    public void dumpPNG(SprDocument document, int displayWidth, int displayHeight, int dpi) {
        Bitmap bm = Bitmap.createBitmap(displayWidth, displayHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        document.draw(c, displayWidth, displayHeight, 0, dpi);
        try {
            File dir = new File("/sdcard/spr_debug");
            if (dir.mkdir() || dir.isDirectory()) {
                OutputStream out =
                        new FileOutputStream(
                                new File(
                                        dir, String.valueOf(document.hashCode() % 10000) + ".png"));
                bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void drawRect(
            Canvas canvas, SprDocument document, int displayWidth, int displayHeight) {
        Paint debugPaint = new Paint();
        debugPaint.setAntiAlias(true);
        debugPaint.setStyle(Paint.Style.STROKE);
        debugPaint.setStrokeWidth(5.0f);
        debugPaint.setColor(-65536);
        canvas.drawRect(
                document.mLeft,
                document.mTop,
                displayWidth + document.mLeft,
                displayHeight + document.mTop,
                debugPaint);
    }

    public static void preDraw(SprObjectBase object) {
        if (mDebugLevel.intValue() >= 3 && object.strokePaint != null) {
            object.isVisibleStroke = true;
            object.strokePaint.setColor(Color.rgb(255, 0, 255));
            if (object.strokePaint.getStrokeWidth() < 2.0f) {
                object.strokePaint.setStrokeWidth(2.0f);
            }
        }
    }

    public static void drawDebugInfo(
            Canvas canvas, SprDocument document, int displayWidth, int displayHeight, int dpi) {
        if (mDebugLevel.intValue() >= 2) {
            drawText(
                    canvas,
                    (document.isNinePatch()
                                    ? GnssSignalType.CODE_TYPE_N
                                    : document.isIntrinsic() ? "" : GnssSignalType.CODE_TYPE_C)
                            + String.valueOf(document.hashCode() % 10000),
                    20);
            drawText(canvas, document.mName, 40);
            drawText(
                    canvas,
                    dpi
                            + NavigationBarInflaterView.KEY_CODE_END
                            + displayWidth
                            + "x"
                            + displayHeight,
                    60);
            if (mDebugLevel.intValue() >= 3) {
                drawText(
                        canvas,
                        document.getLoadingTime()
                                + "ms E:"
                                + document.getTotalElementCount()
                                + " S:"
                                + document.getTotalSegmentCount()
                                + " A:"
                                + document.getTotalAttributeCount(),
                        80);
            }
        }
    }

    private static void drawText(Canvas canvas, String text, int y) {
        if (mTextOutlinePaint == null) {
            mTextOutlinePaint = new Paint();
            mTextOutlinePaint.setAntiAlias(true);
            mTextOutlinePaint.setTextSize(20.0f);
            mTextOutlinePaint.setStyle(Paint.Style.STROKE);
            mTextOutlinePaint.setColor(-16777216);
            mTextOutlinePaint.setStrokeWidth(4.0f);
        }
        if (mTextPaint == null) {
            mTextPaint = new Paint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextSize(20.0f);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setColor(-1);
        }
        canvas.drawText(text, 5.0f, y, mTextOutlinePaint);
        canvas.drawText(text, 5.0f, y, mTextPaint);
    }
}
