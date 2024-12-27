package com.android.internal.widget.remotecompose.core;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.ClickArea;
import com.android.internal.widget.remotecompose.core.operations.ClipPath;
import com.android.internal.widget.remotecompose.core.operations.ClipRect;
import com.android.internal.widget.remotecompose.core.operations.ColorExpression;
import com.android.internal.widget.remotecompose.core.operations.DrawArc;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmap;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt;
import com.android.internal.widget.remotecompose.core.operations.DrawCircle;
import com.android.internal.widget.remotecompose.core.operations.DrawLine;
import com.android.internal.widget.remotecompose.core.operations.DrawOval;
import com.android.internal.widget.remotecompose.core.operations.DrawPath;
import com.android.internal.widget.remotecompose.core.operations.DrawRect;
import com.android.internal.widget.remotecompose.core.operations.DrawRoundRect;
import com.android.internal.widget.remotecompose.core.operations.DrawText;
import com.android.internal.widget.remotecompose.core.operations.DrawTextAnchored;
import com.android.internal.widget.remotecompose.core.operations.DrawTextOnPath;
import com.android.internal.widget.remotecompose.core.operations.DrawTweenPath;
import com.android.internal.widget.remotecompose.core.operations.FloatConstant;
import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.Header;
import com.android.internal.widget.remotecompose.core.operations.MatrixRestore;
import com.android.internal.widget.remotecompose.core.operations.MatrixRotate;
import com.android.internal.widget.remotecompose.core.operations.MatrixSave;
import com.android.internal.widget.remotecompose.core.operations.MatrixScale;
import com.android.internal.widget.remotecompose.core.operations.MatrixSkew;
import com.android.internal.widget.remotecompose.core.operations.MatrixTranslate;
import com.android.internal.widget.remotecompose.core.operations.PaintData;
import com.android.internal.widget.remotecompose.core.operations.PathData;
import com.android.internal.widget.remotecompose.core.operations.RootContentBehavior;
import com.android.internal.widget.remotecompose.core.operations.RootContentDescription;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.TextFromFloat;
import com.android.internal.widget.remotecompose.core.operations.TextMerge;
import com.android.internal.widget.remotecompose.core.operations.Theme;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class RemoteComposeBuffer {
    private static final boolean DEBUG = false;
    public static final int EASING_CUBIC_ACCELERATE = 2;
    public static final int EASING_CUBIC_ANTICIPATE = 5;
    public static final int EASING_CUBIC_CUSTOM = 11;
    public static final int EASING_CUBIC_DECELERATE = 3;
    public static final int EASING_CUBIC_LINEAR = 4;
    public static final int EASING_CUBIC_OVERSHOOT = 6;
    public static final int EASING_CUBIC_STANDARD = 1;
    public static final int EASING_EASE_OUT_BOUNCE = 13;
    public static final int EASING_EASE_OUT_ELASTIC = 14;
    public static final int EASING_SPLINE_CUSTOM = 12;
    public static final int PAD_AFTER_NONE = 1;
    public static final int PAD_AFTER_SPACE = 0;
    public static final int PAD_AFTER_ZERO = 3;
    public static final int PAD_PRE_NONE = 4;
    public static final int PAD_PRE_SPACE = 0;
    public static final int PAD_PRE_ZERO = 12;
    WireBuffer mBuffer = new WireBuffer();
    Platform mPlatform = null;
    RemoteComposeState mRemoteComposeState;

    public RemoteComposeBuffer(RemoteComposeState remoteComposeState) {
        this.mRemoteComposeState = remoteComposeState;
    }

    public void reset(int expectedSize) {
        this.mBuffer.reset(expectedSize);
        this.mRemoteComposeState.reset();
    }

    public Platform getPlatform() {
        return this.mPlatform;
    }

    public void setPlatform(Platform platform) {
        this.mPlatform = platform;
    }

    public WireBuffer getBuffer() {
        return this.mBuffer;
    }

    public void setBuffer(WireBuffer buffer) {
        this.mBuffer = buffer;
    }

    public void header(int width, int height, String contentDescription, long capabilities) {
        Header.COMPANION.apply(this.mBuffer, width, height, capabilities);
        if (contentDescription != null) {
            int contentDescriptionId = addText(contentDescription);
            RootContentDescription.COMPANION.apply(this.mBuffer, contentDescriptionId);
        }
    }

    public void header(int width, int height, String contentDescription) {
        header(width, height, contentDescription, 0L);
    }

    public void drawBitmap(
            Object image,
            int imageWidth,
            int imageHeight,
            int srcLeft,
            int srcTop,
            int srcRight,
            int srcBottom,
            int dstLeft,
            int dstTop,
            int dstRight,
            int dstBottom,
            String contentDescription) {
        int imageId = this.mRemoteComposeState.dataGetId(image);
        if (imageId == -1) {
            imageId = this.mRemoteComposeState.cache(image);
            byte[] data = this.mPlatform.imageToByteArray(image);
            BitmapData.COMPANION.apply(this.mBuffer, imageId, imageWidth, imageHeight, data);
        }
        int contentDescriptionId = 0;
        if (contentDescription != null) {
            contentDescriptionId = addText(contentDescription);
        }
        DrawBitmapInt.COMPANION.apply(
                this.mBuffer,
                imageId,
                srcLeft,
                srcTop,
                srcRight,
                srcBottom,
                dstLeft,
                dstTop,
                dstRight,
                dstBottom,
                contentDescriptionId);
    }

    public int addText(String text) {
        int id = this.mRemoteComposeState.dataGetId(text);
        if (id == -1) {
            int id2 = this.mRemoteComposeState.cache(text);
            TextData.COMPANION.apply(this.mBuffer, id2, text);
            return id2;
        }
        return id;
    }

    public void addClickArea(
            int id,
            String contentDescription,
            float left,
            float top,
            float right,
            float bottom,
            String metadata) {
        int metadataId;
        int contentDescriptionId = contentDescription != null ? addText(contentDescription) : 0;
        if (metadata == null) {
            metadataId = 0;
        } else {
            int metadataId2 = addText(metadata);
            metadataId = metadataId2;
        }
        ClickArea.COMPANION.apply(
                this.mBuffer, id, contentDescriptionId, left, top, right, bottom, metadataId);
    }

    public void setRootContentBehavior(int scroll, int alignment, int sizing, int mode) {
        RootContentBehavior.COMPANION.apply(this.mBuffer, scroll, alignment, sizing, mode);
    }

    public void addDrawArc(
            float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        DrawArc.COMPANION.apply(this.mBuffer, left, top, right, bottom, startAngle, sweepAngle);
    }

    public void addDrawBitmap(
            Object image,
            float left,
            float top,
            float right,
            float bottom,
            String contentDescription) {
        int imageId = this.mRemoteComposeState.dataGetId(image);
        if (imageId == -1) {
            imageId = this.mRemoteComposeState.cache(image);
            byte[] data = this.mPlatform.imageToByteArray(image);
            int imageWidth = this.mPlatform.getImageWidth(image);
            int imageHeight = this.mPlatform.getImageHeight(image);
            BitmapData.COMPANION.apply(this.mBuffer, imageId, imageWidth, imageHeight, data);
        }
        int contentDescriptionId = 0;
        if (contentDescription != null) {
            contentDescriptionId = addText(contentDescription);
        }
        DrawBitmap.COMPANION.apply(
                this.mBuffer, imageId, left, top, right, bottom, contentDescriptionId);
    }

    public void addDrawCircle(float centerX, float centerY, float radius) {
        DrawCircle.COMPANION.apply(this.mBuffer, centerX, centerY, radius);
    }

    public void addDrawLine(float x1, float y1, float x2, float y2) {
        DrawLine.COMPANION.apply(this.mBuffer, x1, y1, x2, y2);
    }

    public void addDrawOval(float left, float top, float right, float bottom) {
        DrawOval.COMPANION.apply(this.mBuffer, left, top, right, bottom);
    }

    public void addDrawPath(Object path) {
        int id = this.mRemoteComposeState.dataGetId(path);
        if (id == -1) {
            id = addPathData(path);
        }
        addDrawPath(id);
    }

    public void addDrawPath(int pathId) {
        DrawPath.COMPANION.apply(this.mBuffer, pathId);
    }

    public void addDrawRect(float left, float top, float right, float bottom) {
        DrawRect.COMPANION.apply(this.mBuffer, left, top, right, bottom);
    }

    public void addDrawRoundRect(
            float left, float top, float right, float bottom, float radiusX, float radiusY) {
        DrawRoundRect.COMPANION.apply(this.mBuffer, left, top, right, bottom, radiusX, radiusY);
    }

    public void addDrawTextOnPath(String text, Object path, float hOffset, float vOffset) {
        int pathId = this.mRemoteComposeState.dataGetId(path);
        if (pathId == -1) {
            pathId = addPathData(path);
        }
        int textId = addText(text);
        DrawTextOnPath.COMPANION.apply(this.mBuffer, textId, pathId, hOffset, vOffset);
    }

    public void addDrawTextRun(
            String text,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            float x,
            float y,
            boolean rtl) {
        int textId = addText(text);
        DrawText.COMPANION.apply(
                this.mBuffer, textId, start, end, contextStart, contextEnd, x, y, rtl);
    }

    public void addDrawTextRun(
            int textId,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            float x,
            float y,
            boolean rtl) {
        DrawText.COMPANION.apply(
                this.mBuffer, textId, start, end, contextStart, contextEnd, x, y, rtl);
    }

    public void drawTextAnchored(String text, float x, float y, float panX, float panY, int flags) {
        int textId = addText(text);
        DrawTextAnchored.COMPANION.apply(this.mBuffer, textId, x, y, panX, panY, flags);
    }

    public int createTextId(String text) {
        return addText(text);
    }

    public int textMerge(int id1, int id2) {
        int textId = addText(id1 + "+" + id2);
        TextMerge.COMPANION.apply(this.mBuffer, textId, id1, id2);
        return textId;
    }

    public int createTextFromFloat(float value, short digitsBefore, short digitsAfter, int flags) {
        String placeHolder =
                Utils.floatToString(value)
                        + NavigationBarInflaterView.KEY_CODE_START
                        + ((int) digitsBefore)
                        + ","
                        + ((int) digitsAfter)
                        + ","
                        + flags
                        + NavigationBarInflaterView.KEY_CODE_END;
        int id = this.mRemoteComposeState.dataGetId(placeHolder);
        if (id == -1) {
            id = this.mRemoteComposeState.cache(placeHolder);
        }
        TextFromFloat.COMPANION.apply(this.mBuffer, id, value, digitsBefore, digitsAfter, flags);
        return id;
    }

    public void drawTextAnchored(int textId, float x, float y, float panX, float panY, int flags) {
        DrawTextAnchored.COMPANION.apply(this.mBuffer, textId, x, y, panX, panY, flags);
    }

    public void addDrawTweenPath(Object path1, Object path2, float tween, float start, float stop) {
        int path2Id;
        int path1Id = this.mRemoteComposeState.dataGetId(path1);
        if (path1Id == -1) {
            path1Id = addPathData(path1);
        }
        int path2Id2 = this.mRemoteComposeState.dataGetId(path2);
        if (path2Id2 != -1) {
            path2Id = path2Id2;
        } else {
            path2Id = addPathData(path2);
        }
        addDrawTweenPath(path1Id, path2Id, tween, start, stop);
    }

    public void addDrawTweenPath(int path1Id, int path2Id, float tween, float start, float stop) {
        DrawTweenPath.COMPANION.apply(this.mBuffer, path1Id, path2Id, tween, start, stop);
    }

    public int addPathData(Object path) {
        float[] pathData = this.mPlatform.pathToFloatArray(path);
        int id = this.mRemoteComposeState.cache(path);
        PathData.COMPANION.apply(this.mBuffer, id, pathData);
        return id;
    }

    public void addPaint(PaintBundle paint) {
        PaintData.COMPANION.apply(this.mBuffer, paint);
    }

    public void inflateFromBuffer(ArrayList<Operation> operations) {
        this.mBuffer.setIndex(0);
        while (this.mBuffer.available()) {
            int opId = this.mBuffer.readByte();
            CompanionOperation operation = Operations.map.get(opId);
            if (operation == null) {
                throw new RuntimeException("Unknown operation encountered " + opId);
            }
            operation.read(this.mBuffer, operations);
        }
    }

    RemoteComposeBuffer copy() {
        ArrayList<Operation> operations = new ArrayList<>();
        inflateFromBuffer(operations);
        RemoteComposeBuffer buffer = new RemoteComposeBuffer(this.mRemoteComposeState);
        return copyFromOperations(operations, buffer);
    }

    public void setTheme(int theme) {
        Theme.COMPANION.apply(this.mBuffer, theme);
    }

    static String version() {
        return "v1.0";
    }

    public static RemoteComposeBuffer fromFile(String path, RemoteComposeState remoteComposeState)
            throws IOException {
        RemoteComposeBuffer buffer = new RemoteComposeBuffer(remoteComposeState);
        read(new File(path), buffer);
        return buffer;
    }

    public RemoteComposeBuffer fromFile(File file, RemoteComposeState remoteComposeState)
            throws IOException {
        RemoteComposeBuffer buffer = new RemoteComposeBuffer(remoteComposeState);
        read(file, buffer);
        return buffer;
    }

    public static RemoteComposeBuffer fromInputStream(
            InputStream inputStream, RemoteComposeState remoteComposeState) {
        RemoteComposeBuffer buffer = new RemoteComposeBuffer(remoteComposeState);
        read(inputStream, buffer);
        return buffer;
    }

    RemoteComposeBuffer copyFromOperations(
            ArrayList<Operation> operations, RemoteComposeBuffer buffer) {
        Iterator<Operation> it = operations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            operation.write(buffer.mBuffer);
        }
        return buffer;
    }

    public void write(RemoteComposeBuffer buffer, File file) {
        try {
            FileOutputStream fd = new FileOutputStream(file);
            fd.write(buffer.mBuffer.getBuffer(), 0, buffer.mBuffer.getSize());
            fd.flush();
            fd.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void read(File file, RemoteComposeBuffer buffer) throws IOException {
        FileInputStream fd = new FileInputStream(file);
        read(fd, buffer);
    }

    public static void read(InputStream fd, RemoteComposeBuffer buffer) {
        try {
            byte[] bytes = readAllBytes(fd);
            buffer.reset(bytes.length);
            System.arraycopy(bytes, 0, buffer.mBuffer.mBuffer, 0, bytes.length);
            buffer.mBuffer.mSize = bytes.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readAllBytes(InputStream is) throws IOException {
        byte[] buff = new byte[32768];
        int red = 0;
        while (true) {
            int ret = is.read(buff, red, buff.length - red);
            if (ret == -1) {
                is.close();
                return Arrays.copyOf(buff, red);
            }
            red += ret;
            if (red == buff.length) {
                buff = Arrays.copyOf(buff, buff.length * 2);
            }
        }
    }

    public void addMatrixSkew(float skewX, float skewY) {
        MatrixSkew.COMPANION.apply(this.mBuffer, skewX, skewY);
    }

    public void addMatrixRestore() {
        MatrixRestore.COMPANION.apply(this.mBuffer);
    }

    public void addMatrixSave() {
        MatrixSave.COMPANION.apply(this.mBuffer);
    }

    public void addMatrixRotate(float angle, float centerX, float centerY) {
        MatrixRotate.COMPANION.apply(this.mBuffer, angle, centerX, centerY);
    }

    public void addMatrixTranslate(float dx, float dy) {
        MatrixTranslate.COMPANION.apply(this.mBuffer, dx, dy);
    }

    public void addMatrixScale(float scaleX, float scaleY) {
        MatrixScale.COMPANION.apply(this.mBuffer, scaleX, scaleY, Float.NaN, Float.NaN);
    }

    public void addMatrixScale(float scaleX, float scaleY, float centerX, float centerY) {
        MatrixScale.COMPANION.apply(this.mBuffer, scaleX, scaleY, centerX, centerY);
    }

    public void addClipPath(int pathId) {
        ClipPath.COMPANION.apply(this.mBuffer, pathId);
    }

    public void addClipRect(float left, float top, float right, float bottom) {
        ClipRect.COMPANION.apply(this.mBuffer, left, top, right, bottom);
    }

    public float addFloat(float value) {
        int id = this.mRemoteComposeState.cacheFloat(value);
        FloatConstant.COMPANION.apply(this.mBuffer, id, value);
        return Utils.asNan(id);
    }

    public float addAnimatedFloat(float... value) {
        int id = this.mRemoteComposeState.cache(value);
        FloatExpression.COMPANION.apply(this.mBuffer, id, value, null);
        return Utils.asNan(id);
    }

    public float addAnimatedFloat(float[] value, float[] animation) {
        int id = this.mRemoteComposeState.cache(value);
        FloatExpression.COMPANION.apply(this.mBuffer, id, value, animation);
        return Utils.asNan(id);
    }

    public short addColorExpression(int color1, int color2, float tween) {
        ColorExpression c = new ColorExpression(0, 0, color1, color2, tween);
        short id = (short) this.mRemoteComposeState.cache(c);
        c.mId = id;
        c.write(this.mBuffer);
        return id;
    }

    public short addColorExpression(short color1, int color2, float tween) {
        ColorExpression c = new ColorExpression(0, 1, (int) color1, color2, tween);
        short id = (short) this.mRemoteComposeState.cache(c);
        c.mId = id;
        c.write(this.mBuffer);
        return id;
    }

    public short addColorExpression(int color1, short color2, float tween) {
        ColorExpression c = new ColorExpression(0, 2, color1, (int) color2, tween);
        short id = (short) this.mRemoteComposeState.cache(c);
        c.mId = id;
        c.write(this.mBuffer);
        return id;
    }

    public short addColorExpression(short color1, short color2, float tween) {
        ColorExpression c = new ColorExpression(0, 3, (int) color1, (int) color2, tween);
        short id = (short) this.mRemoteComposeState.cache(c);
        c.mId = id;
        c.write(this.mBuffer);
        return id;
    }

    public short addColorExpression(float hue, float sat, float value) {
        ColorExpression c = new ColorExpression(0, hue, sat, value);
        short id = (short) this.mRemoteComposeState.cache(c);
        c.mId = id;
        c.write(this.mBuffer);
        return id;
    }

    public short addColorExpression(int alpha, float hue, float sat, float value) {
        ColorExpression c = new ColorExpression(0, alpha, hue, sat, value);
        short id = (short) this.mRemoteComposeState.cache(c);
        c.mId = id;
        c.write(this.mBuffer);
        return id;
    }

    public static float[] packAnimation(
            float duration, int type, float[] spec, float initialValue, float wrap) {
        return FloatAnimation.packToFloatArray(duration, type, spec, initialValue, wrap);
    }
}
