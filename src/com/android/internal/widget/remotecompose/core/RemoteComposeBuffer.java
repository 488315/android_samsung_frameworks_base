package com.android.internal.widget.remotecompose.core;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.BitmapFontData;
import com.android.internal.widget.remotecompose.core.operations.ClickArea;
import com.android.internal.widget.remotecompose.core.operations.ClipPath;
import com.android.internal.widget.remotecompose.core.operations.ClipRect;
import com.android.internal.widget.remotecompose.core.operations.ColorAttribute;
import com.android.internal.widget.remotecompose.core.operations.ColorConstant;
import com.android.internal.widget.remotecompose.core.operations.ColorExpression;
import com.android.internal.widget.remotecompose.core.operations.ComponentValue;
import com.android.internal.widget.remotecompose.core.operations.ConditionalOperations;
import com.android.internal.widget.remotecompose.core.operations.DataListFloat;
import com.android.internal.widget.remotecompose.core.operations.DataListIds;
import com.android.internal.widget.remotecompose.core.operations.DataMapIds;
import com.android.internal.widget.remotecompose.core.operations.DataMapLookup;
import com.android.internal.widget.remotecompose.core.operations.DebugMessage;
import com.android.internal.widget.remotecompose.core.operations.DrawArc;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmap;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapFontText;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapScaled;
import com.android.internal.widget.remotecompose.core.operations.DrawCircle;
import com.android.internal.widget.remotecompose.core.operations.DrawContent;
import com.android.internal.widget.remotecompose.core.operations.DrawLine;
import com.android.internal.widget.remotecompose.core.operations.DrawOval;
import com.android.internal.widget.remotecompose.core.operations.DrawPath;
import com.android.internal.widget.remotecompose.core.operations.DrawRect;
import com.android.internal.widget.remotecompose.core.operations.DrawRoundRect;
import com.android.internal.widget.remotecompose.core.operations.DrawSector;
import com.android.internal.widget.remotecompose.core.operations.DrawText;
import com.android.internal.widget.remotecompose.core.operations.DrawTextAnchored;
import com.android.internal.widget.remotecompose.core.operations.DrawTextOnPath;
import com.android.internal.widget.remotecompose.core.operations.DrawTweenPath;
import com.android.internal.widget.remotecompose.core.operations.FloatConstant;
import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.FloatFunctionCall;
import com.android.internal.widget.remotecompose.core.operations.FloatFunctionDefine;
import com.android.internal.widget.remotecompose.core.operations.HapticFeedback;
import com.android.internal.widget.remotecompose.core.operations.Header;
import com.android.internal.widget.remotecompose.core.operations.ImageAttribute;
import com.android.internal.widget.remotecompose.core.operations.IntegerExpression;
import com.android.internal.widget.remotecompose.core.operations.MatrixRestore;
import com.android.internal.widget.remotecompose.core.operations.MatrixRotate;
import com.android.internal.widget.remotecompose.core.operations.MatrixSave;
import com.android.internal.widget.remotecompose.core.operations.MatrixScale;
import com.android.internal.widget.remotecompose.core.operations.MatrixSkew;
import com.android.internal.widget.remotecompose.core.operations.MatrixTranslate;
import com.android.internal.widget.remotecompose.core.operations.NamedVariable;
import com.android.internal.widget.remotecompose.core.operations.PaintData;
import com.android.internal.widget.remotecompose.core.operations.ParticlesCreate;
import com.android.internal.widget.remotecompose.core.operations.ParticlesLoop;
import com.android.internal.widget.remotecompose.core.operations.PathAppend;
import com.android.internal.widget.remotecompose.core.operations.PathCombine;
import com.android.internal.widget.remotecompose.core.operations.PathCreate;
import com.android.internal.widget.remotecompose.core.operations.PathData;
import com.android.internal.widget.remotecompose.core.operations.PathTween;
import com.android.internal.widget.remotecompose.core.operations.RootContentBehavior;
import com.android.internal.widget.remotecompose.core.operations.RootContentDescription;
import com.android.internal.widget.remotecompose.core.operations.TextAttribute;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.TextFromFloat;
import com.android.internal.widget.remotecompose.core.operations.TextLength;
import com.android.internal.widget.remotecompose.core.operations.TextLookup;
import com.android.internal.widget.remotecompose.core.operations.TextLookupInt;
import com.android.internal.widget.remotecompose.core.operations.TextMeasure;
import com.android.internal.widget.remotecompose.core.operations.TextMerge;
import com.android.internal.widget.remotecompose.core.operations.Theme;
import com.android.internal.widget.remotecompose.core.operations.TimeAttribute;
import com.android.internal.widget.remotecompose.core.operations.TouchExpression;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.CanvasContent;
import com.android.internal.widget.remotecompose.core.operations.layout.CanvasOperations;
import com.android.internal.widget.remotecompose.core.operations.layout.ComponentStart;
import com.android.internal.widget.remotecompose.core.operations.layout.ContainerEnd;
import com.android.internal.widget.remotecompose.core.operations.layout.ImpulseOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.ImpulseProcess;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponentContent;
import com.android.internal.widget.remotecompose.core.operations.layout.LoopOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.RootLayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.CanvasLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.CollapsibleColumnLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.CollapsibleRowLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.FitBoxLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.ImageLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.StateLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.TextLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.BackgroundModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.BorderModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ClipRectModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.GraphicsLayerModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.MarqueeModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.OffsetModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.PaddingModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RippleModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RoundedClipRectModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RunActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ScrollModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ZIndexModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import com.android.internal.widget.remotecompose.core.types.BooleanConstant;
import com.android.internal.widget.remotecompose.core.types.IntegerConstant;
import com.android.internal.widget.remotecompose.core.types.LongConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes6.dex */
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
    private final RemoteComposeState mRemoteComposeState;
    private WireBuffer mBuffer = new WireBuffer();
    private Platform mPlatform = null;
    private int mLastComponentId = 0;
    private int mGeneratedComponentId = -1;

    public RemoteComposeBuffer(RemoteComposeState remoteComposeState) {
        this.mRemoteComposeState = remoteComposeState;
    }

    public void reset(int i) {
        this.mBuffer.reset(i);
        this.mRemoteComposeState.reset();
        this.mLastComponentId = 0;
        this.mGeneratedComponentId = -1;
    }

    public int getLastComponentId() {
        return this.mLastComponentId;
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

    public void setBuffer(WireBuffer wireBuffer) {
        this.mBuffer = wireBuffer;
    }

    public void addHeader(short[] sArr, Object[] objArr) {
        Header.apply(this.mBuffer, sArr, objArr);
    }

    public void header(int i, int i2, String str, float f, long j) {
        Header.apply(this.mBuffer, i, i2, f, j);
        if (str != null) {
            RootContentDescription.apply(this.mBuffer, addText(str));
        }
    }

    public void addHeader(int i, int i2, String str, float f, long j) {
        Header.apply(this.mBuffer, i, i2, f, j);
        if (str != null) {
            RootContentDescription.apply(this.mBuffer, addText(str));
        }
    }

    public void header(int i, int i2, String str) {
        header(i, i2, str, 1.0f, 0L);
    }

    public void drawBitmap(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, String str) {
        DrawBitmapInt.apply(this.mBuffer, storeBitmap(obj), i3, i4, i5, i6, i7, i8, i9, i10, str != null ? addText(str) : 0);
    }

    public int mapLookup(int i, int i2) {
        int i3 = (i2 * 33) + i;
        int dataGetId = this.mRemoteComposeState.dataGetId(Integer.valueOf(i3));
        if (dataGetId != -1) {
            return dataGetId;
        }
        int cacheData = this.mRemoteComposeState.cacheData(Integer.valueOf(i3));
        DataMapLookup.apply(this.mBuffer, cacheData, i, i2);
        return cacheData;
    }

    public int addText(String str) {
        int dataGetId = this.mRemoteComposeState.dataGetId(str);
        if (dataGetId != -1) {
            return dataGetId;
        }
        int cacheData = this.mRemoteComposeState.cacheData(str);
        TextData.apply(this.mBuffer, cacheData, str);
        return cacheData;
    }

    public void addClickArea(int i, String str, float f, float f2, float f3, float f4, String str2) {
        ClickArea.apply(this.mBuffer, i, str != null ? addText(str) : 0, f, f2, f3, f4, str2 != null ? addText(str2) : 0);
    }

    public void setRootContentBehavior(int i, int i2, int i3, int i4) {
        RootContentBehavior.apply(this.mBuffer, i, i2, i3, i4);
    }

    public void addDrawArc(float f, float f2, float f3, float f4, float f5, float f6) {
        DrawArc.apply(this.mBuffer, f, f2, f3, f4, f5, f6);
    }

    public void addDrawSector(float f, float f2, float f3, float f4, float f5, float f6) {
        DrawSector.apply(this.mBuffer, f, f2, f3, f4, f5, f6);
    }

    public void addDrawBitmap(Object obj, float f, float f2, float f3, float f4, String str) {
        addDrawBitmap(storeBitmap(obj), f, f2, f3, f4, str);
    }

    public void addDrawBitmap(int i, float f, float f2, float f3, float f4, String str) {
        DrawBitmap.apply(this.mBuffer, i, f, f2, f3, f4, str != null ? addText(str) : 0);
    }

    public void addDrawBitmap(int i, float f, float f2, String str) {
        DrawBitmap.apply(this.mBuffer, i, f, f2, this.mPlatform.getImageWidth(Integer.valueOf(i)), this.mPlatform.getImageHeight(Integer.valueOf(i)), str != null ? addText(str) : 0);
    }

    public void drawScaledBitmap(Object obj, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, float f9, String str) {
        DrawBitmapScaled.apply(this.mBuffer, storeBitmap(obj), f, f2, f3, f4, f5, f6, f7, f8, i, f9, str != null ? addText(str) : 0);
    }

    public int addBitmap(Object obj) {
        return storeBitmap(obj);
    }

    public int addBitmap(Object obj, String str) {
        return storeBitmap(obj);
    }

    public int addBitmapFont(BitmapFontData.Glyph[] glyphArr) {
        int nextId = this.mRemoteComposeState.nextId();
        BitmapFontData.apply(this.mBuffer, nextId, glyphArr);
        return nextId;
    }

    public void setBitmapName(int i, String str) {
        NamedVariable.apply(this.mBuffer, i, 3, str);
    }

    public void drawScaledBitmap(int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i2, float f9, String str) {
        DrawBitmapScaled.apply(this.mBuffer, i, f, f2, f3, f4, f5, f6, f7, f8, i2, f9, str != null ? addText(str) : 0);
    }

    public void addDrawCircle(float f, float f2, float f3) {
        DrawCircle.apply(this.mBuffer, f, f2, f3);
    }

    public void addDrawLine(float f, float f2, float f3, float f4) {
        DrawLine.apply(this.mBuffer, f, f2, f3, f4);
    }

    public void addDrawOval(float f, float f2, float f3, float f4) {
        DrawOval.apply(this.mBuffer, f, f2, f3, f4);
    }

    public void addDrawPath(Object obj) {
        int dataGetId = this.mRemoteComposeState.dataGetId(obj);
        if (dataGetId == -1) {
            dataGetId = addPathData(obj);
        }
        addDrawPath(dataGetId);
    }

    public int pathTween(int i, int i2, float f) {
        int nextId = this.mRemoteComposeState.nextId();
        PathTween.apply(this.mBuffer, nextId, i, i2, f);
        return nextId;
    }

    public int pathCreate(float f, float f2) {
        int nextId = this.mRemoteComposeState.nextId();
        PathCreate.apply(this.mBuffer, nextId, f, f2);
        return nextId;
    }

    public void pathAppend(int i, float... fArr) {
        PathAppend.apply(this.mBuffer, i, fArr);
    }

    public void addDrawPath(int i) {
        DrawPath.apply(this.mBuffer, i);
    }

    public void addDrawRect(float f, float f2, float f3, float f4) {
        DrawRect.apply(this.mBuffer, f, f2, f3, f4);
    }

    public void addDrawRoundRect(float f, float f2, float f3, float f4, float f5, float f6) {
        DrawRoundRect.apply(this.mBuffer, f, f2, f3, f4, f5, f6);
    }

    public void addDrawTextOnPath(String str, Object obj, float f, float f2) {
        int dataGetId = this.mRemoteComposeState.dataGetId(obj);
        if (dataGetId == -1) {
            dataGetId = addPathData(obj);
        }
        DrawTextOnPath.apply(this.mBuffer, addText(str), dataGetId, f, f2);
    }

    public void addDrawTextOnPath(int i, Object obj, float f, float f2) {
        int dataGetId = this.mRemoteComposeState.dataGetId(obj);
        if (dataGetId == -1) {
            dataGetId = addPathData(obj);
        }
        DrawTextOnPath.apply(this.mBuffer, i, dataGetId, f, f2);
    }

    public void addDrawTextRun(String str, int i, int i2, int i3, int i4, float f, float f2, boolean z) {
        DrawText.apply(this.mBuffer, addText(str), i, i2, i3, i4, f, f2, z);
    }

    public void addDrawTextRun(int i, int i2, int i3, int i4, int i5, float f, float f2, boolean z) {
        DrawText.apply(this.mBuffer, i, i2, i3, i4, i5, f, f2, z);
    }

    public void addDrawBitmapFontTextRun(int i, int i2, int i3, int i4, float f, float f2) {
        DrawBitmapFontText.apply(this.mBuffer, i, i2, i3, i4, f, f2);
    }

    public void drawTextAnchored(String str, float f, float f2, float f3, float f4, int i) {
        DrawTextAnchored.apply(this.mBuffer, addText(str), f, f2, f3, f4, i);
    }

    public int createTextId(String str) {
        return addText(str);
    }

    public int textMerge(int i, int i2) {
        int nextId = nextId();
        TextMerge.apply(this.mBuffer, nextId, i, i2);
        return nextId;
    }

    public int createTextFromFloat(float f, short s, short s2, int i) {
        String str = Utils.floatToString(f) + NavigationBarInflaterView.KEY_CODE_START + ((int) s) + "," + ((int) s2) + "," + i + NavigationBarInflaterView.KEY_CODE_END;
        int dataGetId = this.mRemoteComposeState.dataGetId(str);
        if (dataGetId == -1) {
            dataGetId = this.mRemoteComposeState.cacheData(str);
        }
        int i2 = dataGetId;
        TextFromFloat.apply(this.mBuffer, i2, f, s, s2, i);
        return i2;
    }

    public void drawTextAnchored(int i, float f, float f2, float f3, float f4, int i2) {
        DrawTextAnchored.apply(this.mBuffer, i, f, f2, f3, f4, i2);
    }

    public void addDrawTweenPath(Object obj, Object obj2, float f, float f2, float f3) {
        int dataGetId = this.mRemoteComposeState.dataGetId(obj);
        if (dataGetId == -1) {
            dataGetId = addPathData(obj);
        }
        int i = dataGetId;
        int dataGetId2 = this.mRemoteComposeState.dataGetId(obj2);
        if (dataGetId2 == -1) {
            dataGetId2 = addPathData(obj2);
        }
        addDrawTweenPath(i, dataGetId2, f, f2, f3);
    }

    public void addDrawTweenPath(int i, int i2, float f, float f2, float f3) {
        DrawTweenPath.apply(this.mBuffer, i, i2, f, f2, f3);
    }

    public int addPathData(Object obj) {
        float[] pathToFloatArray = this.mPlatform.pathToFloatArray(obj);
        int cacheData = this.mRemoteComposeState.cacheData(obj);
        PathData.apply(this.mBuffer, cacheData, pathToFloatArray);
        return cacheData;
    }

    public void addPaint(PaintBundle paintBundle) {
        PaintData.apply(this.mBuffer, paintBundle);
    }

    public void inflateFromBuffer(ArrayList<Operation> arrayList) {
        this.mBuffer.setIndex(0);
        while (this.mBuffer.available()) {
            int readByte = this.mBuffer.readByte();
            CompanionOperation companionOperation = Operations.map.get(readByte);
            if (companionOperation == null) {
                throw new RuntimeException("Unknown operation encountered " + readByte);
            }
            companionOperation.read(this.mBuffer, arrayList);
        }
    }

    public static void readNextOperation(WireBuffer wireBuffer, ArrayList<Operation> arrayList) {
        int readByte = wireBuffer.readByte();
        CompanionOperation companionOperation = Operations.map.get(readByte);
        if (companionOperation == null) {
            throw new RuntimeException("Unknown operation encountered " + readByte);
        }
        companionOperation.read(wireBuffer, arrayList);
    }

    RemoteComposeBuffer copy() {
        ArrayList<Operation> arrayList = new ArrayList<>();
        inflateFromBuffer(arrayList);
        return copyFromOperations(arrayList, new RemoteComposeBuffer(this.mRemoteComposeState));
    }

    public void setTheme(int i) {
        Theme.apply(this.mBuffer, i);
    }

    static String version() {
        return "v1.0";
    }

    public static RemoteComposeBuffer fromFile(String str, RemoteComposeState remoteComposeState) throws IOException {
        RemoteComposeBuffer remoteComposeBuffer = new RemoteComposeBuffer(remoteComposeState);
        read(new File(str), remoteComposeBuffer);
        return remoteComposeBuffer;
    }

    public RemoteComposeBuffer fromFile(File file, RemoteComposeState remoteComposeState) throws IOException {
        RemoteComposeBuffer remoteComposeBuffer = new RemoteComposeBuffer(remoteComposeState);
        read(file, remoteComposeBuffer);
        return remoteComposeBuffer;
    }

    public static RemoteComposeBuffer fromInputStream(InputStream inputStream, RemoteComposeState remoteComposeState) {
        RemoteComposeBuffer remoteComposeBuffer = new RemoteComposeBuffer(remoteComposeState);
        read(inputStream, remoteComposeBuffer);
        return remoteComposeBuffer;
    }

    RemoteComposeBuffer copyFromOperations(ArrayList<Operation> arrayList, RemoteComposeBuffer remoteComposeBuffer) {
        Iterator<Operation> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().write(remoteComposeBuffer.mBuffer);
        }
        return remoteComposeBuffer;
    }

    public void write(RemoteComposeBuffer remoteComposeBuffer, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(remoteComposeBuffer.mBuffer.getBuffer(), 0, remoteComposeBuffer.mBuffer.getSize());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void read(File file, RemoteComposeBuffer remoteComposeBuffer) throws IOException {
        read(new FileInputStream(file), remoteComposeBuffer);
    }

    public static void read(InputStream inputStream, RemoteComposeBuffer remoteComposeBuffer) {
        try {
            byte[] readAllBytes = readAllBytes(inputStream);
            remoteComposeBuffer.reset(readAllBytes.length);
            System.arraycopy(readAllBytes, 0, remoteComposeBuffer.mBuffer.mBuffer, 0, readAllBytes.length);
            remoteComposeBuffer.mBuffer.mSize = readAllBytes.length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[32768];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (read == -1) {
                inputStream.close();
                return Arrays.copyOf(bArr, i);
            }
            i += read;
            if (i == bArr.length) {
                bArr = Arrays.copyOf(bArr, bArr.length * 2);
            }
        }
    }

    public void addMatrixSkew(float f, float f2) {
        MatrixSkew.apply(this.mBuffer, f, f2);
    }

    public void addMatrixRestore() {
        MatrixRestore.apply(this.mBuffer);
    }

    public void addMatrixSave() {
        MatrixSave.apply(this.mBuffer);
    }

    public void addMatrixRotate(float f, float f2, float f3) {
        MatrixRotate.apply(this.mBuffer, f, f2, f3);
    }

    public void addMatrixTranslate(float f, float f2) {
        MatrixTranslate.apply(this.mBuffer, f, f2);
    }

    public void addMatrixScale(float f, float f2) {
        MatrixScale.apply(this.mBuffer, f, f2, Float.NaN, Float.NaN);
    }

    public void addMatrixScale(float f, float f2, float f3, float f4) {
        MatrixScale.apply(this.mBuffer, f, f2, f3, f4);
    }

    public void addClipPath(int i) {
        ClipPath.apply(this.mBuffer, i);
    }

    public void addClipRect(float f, float f2, float f3, float f4) {
        ClipRect.apply(this.mBuffer, f, f2, f3, f4);
    }

    public float addFloat(float f) {
        int cacheFloat = this.mRemoteComposeState.cacheFloat(f);
        FloatConstant.apply(this.mBuffer, cacheFloat, f);
        return Utils.asNan(cacheFloat);
    }

    public float reserveFloatVariable() {
        return Utils.asNan(this.mRemoteComposeState.nextId());
    }

    public int addInteger(int i) {
        int cacheInteger = this.mRemoteComposeState.cacheInteger(i);
        IntegerConstant.apply(this.mBuffer, cacheInteger, i);
        return cacheInteger;
    }

    public int addLong(long j) {
        int nextId = this.mRemoteComposeState.nextId();
        LongConstant.apply(this.mBuffer, nextId, j);
        return nextId;
    }

    public int addBoolean(boolean z) {
        int nextId = this.mRemoteComposeState.nextId();
        BooleanConstant.apply(this.mBuffer, nextId, z);
        return nextId;
    }

    public float asFloatId(int i) {
        return Utils.asNan(i);
    }

    public float addAnimatedFloat(float... fArr) {
        int cacheData = this.mRemoteComposeState.cacheData(fArr);
        FloatExpression.apply(this.mBuffer, cacheData, fArr, null);
        return Utils.asNan(cacheData);
    }

    public void addTouchExpression(float f, float f2, float f3, float f4, float f5, int i, float[] fArr, int i2, float[] fArr2, float[] fArr3) {
        TouchExpression.apply(this.mBuffer, Utils.idFromNan(f), f2, f3, f4, f5, i, fArr, i2, fArr2, fArr3);
    }

    public float addTouchExpression(float f, float f2, float f3, float f4, int i, float[] fArr, int i2, float[] fArr2, float[] fArr3) {
        float asNan = Utils.asNan(this.mRemoteComposeState.nextId());
        addTouchExpression(asNan, f, f2, f3, f4, i, fArr, i2, fArr2, fArr3);
        return asNan;
    }

    public float addAnimatedFloat(float[] fArr, float[] fArr2) {
        int cacheData = this.mRemoteComposeState.cacheData(fArr);
        FloatExpression.apply(this.mBuffer, cacheData, fArr, fArr2);
        return Utils.asNan(cacheData);
    }

    public float textMeasure(int i, int i2) {
        int cacheData = this.mRemoteComposeState.cacheData(Integer.valueOf((i2 * 31) + i));
        TextMeasure.apply(this.mBuffer, cacheData, i, i2);
        return Utils.asNan(cacheData);
    }

    public float textLength(int i) {
        int cacheData = this.mRemoteComposeState.cacheData(Integer.valueOf((TextLength.id() << 16) + i));
        TextLength.apply(this.mBuffer, cacheData, i);
        return Utils.asNan(cacheData);
    }

    public float addFloatArray(float[] fArr) {
        int cacheData = this.mRemoteComposeState.cacheData(fArr, 2);
        DataListFloat.apply(this.mBuffer, cacheData, fArr);
        return Utils.asNan(cacheData);
    }

    public float addFloatList(float[] fArr) {
        int length = fArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            int cacheFloat = this.mRemoteComposeState.cacheFloat(fArr[i]);
            iArr[i] = cacheFloat;
            FloatConstant.apply(this.mBuffer, cacheFloat, fArr[i]);
        }
        return addList(iArr);
    }

    public float addList(int[] iArr) {
        int cacheData = this.mRemoteComposeState.cacheData(iArr, 2);
        DataListIds.apply(this.mBuffer, cacheData, iArr);
        return Utils.asNan(cacheData);
    }

    public float addFloatMap(String[] strArr, float[] fArr) {
        int length = fArr.length;
        int[] iArr = new int[length];
        byte[] bArr = new byte[fArr.length];
        for (int i = 0; i < length; i++) {
            int cacheFloat = this.mRemoteComposeState.cacheFloat(fArr[i]);
            iArr[i] = cacheFloat;
            FloatConstant.apply(this.mBuffer, cacheFloat, fArr[i]);
            bArr[i] = 2;
        }
        return addMap(strArr, bArr, iArr);
    }

    public int addMap(String[] strArr, byte[] bArr, int[] iArr) {
        int cacheData = this.mRemoteComposeState.cacheData(iArr, 2);
        DataMapIds.apply(this.mBuffer, cacheData, strArr, bArr, iArr);
        return cacheData;
    }

    public int textLookup(float f, float f2) {
        int cacheData = this.mRemoteComposeState.cacheData(Long.valueOf((Float.floatToRawIntBits(f) << 32) + Float.floatToRawIntBits(f2)));
        TextLookup.apply(this.mBuffer, cacheData, Utils.idFromNan(f), f2);
        return cacheData;
    }

    public int textLookup(float f, int i) {
        int cacheData = this.mRemoteComposeState.cacheData(Long.valueOf((Float.floatToRawIntBits(f) << 32) + Float.floatToRawIntBits(i)));
        TextLookupInt.apply(this.mBuffer, cacheData, Utils.idFromNan(f), i);
        return cacheData;
    }

    public int addIntegerExpression(int i, int[] iArr) {
        int cacheData = this.mRemoteComposeState.cacheData(iArr);
        IntegerExpression.apply(this.mBuffer, cacheData, i, iArr);
        return cacheData;
    }

    public int addColor(int i) {
        ColorConstant colorConstant = new ColorConstant(0, i);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorConstant);
        colorConstant.mColorId = cacheData;
        colorConstant.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(int i, int i2, float f) {
        ColorExpression colorExpression = new ColorExpression(0, 0, i, i2, f);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(short s, int i, float f) {
        ColorExpression colorExpression = new ColorExpression(0, 1, s, i, f);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(int i, short s, float f) {
        ColorExpression colorExpression = new ColorExpression(0, 2, i, s, f);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(short s, short s2, float f) {
        ColorExpression colorExpression = new ColorExpression(0, 3, s, s2, f);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(float f, float f2, float f3) {
        ColorExpression colorExpression = new ColorExpression(0, f, f2, f3);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(int i, float f, float f2, float f3) {
        ColorExpression colorExpression = new ColorExpression(0, (byte) 4, i, f, f2, f3);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public short addColorExpression(float f, float f2, float f3, float f4) {
        ColorExpression colorExpression = new ColorExpression(0, (byte) 5, f, f2, f3, f4);
        short cacheData = (short) this.mRemoteComposeState.cacheData(colorExpression);
        colorExpression.mId = cacheData;
        colorExpression.write(this.mBuffer);
        return cacheData;
    }

    public static float[] packAnimation(float f, int i, float[] fArr, float f2, float f3) {
        return FloatAnimation.packToFloatArray(f, i, fArr, f2, f3);
    }

    public void setNamedVariable(int i, String str, int i2) {
        NamedVariable.apply(this.mBuffer, i, i2, str);
    }

    private int getComponentId(int i) {
        if (i != -1) {
            return i;
        }
        int i2 = this.mGeneratedComponentId - 1;
        this.mGeneratedComponentId = i2;
        return i2;
    }

    public void addComponentStart(int i, int i2) {
        int componentId = getComponentId(i2);
        this.mLastComponentId = componentId;
        ComponentStart.apply(this.mBuffer, i, componentId, 0.0f, 0.0f);
    }

    public void addComponentStart(int i) {
        addComponentStart(i, -1);
    }

    public void addContainerEnd() {
        ContainerEnd.apply(this.mBuffer);
    }

    public void addModifierScroll(int i, float f, int i2) {
        float reserveFloatVariable = reserveFloatVariable();
        float reserveFloatVariable2 = reserveFloatVariable();
        float f2 = i != 0 ? RemoteContext.FLOAT_TOUCH_POS_X : RemoteContext.FLOAT_TOUCH_POS_Y;
        ScrollModifierOperation.apply(this.mBuffer, i, f, reserveFloatVariable, reserveFloatVariable2);
        addTouchExpression(f, 0.0f, 0.0f, reserveFloatVariable, 0.0f, 3, new float[]{f2, -1.0f, AnimatedFloatExpression.MUL}, 3, new float[]{i2, reserveFloatVariable2}, null);
        ContainerEnd.apply(this.mBuffer);
    }

    public void addModifierScroll(int i, float f) {
        float reserveFloatVariable = reserveFloatVariable();
        float reserveFloatVariable2 = reserveFloatVariable();
        float f2 = i != 0 ? RemoteContext.FLOAT_TOUCH_POS_X : RemoteContext.FLOAT_TOUCH_POS_Y;
        ScrollModifierOperation.apply(this.mBuffer, i, f, reserveFloatVariable, reserveFloatVariable2);
        addTouchExpression(f, 0.0f, 0.0f, reserveFloatVariable, 0.0f, 3, new float[]{f2, -1.0f, AnimatedFloatExpression.MUL}, 0, null, null);
        ContainerEnd.apply(this.mBuffer);
    }

    public void addModifierScroll(int i) {
        ScrollModifierOperation.apply(this.mBuffer, i, 0.0f, reserveFloatVariable(), 0.0f);
        ContainerEnd.apply(this.mBuffer);
    }

    public void addModifierBackground(int i, int i2) {
        BackgroundModifierOperation.apply(this.mBuffer, 0.0f, 0.0f, 0.0f, 0.0f, ((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f, i2);
    }

    public void addModifierBorder(float f, float f2, int i, int i2) {
        BorderModifierOperation.apply(this.mBuffer, 0.0f, 0.0f, 0.0f, 0.0f, f, f2, ((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f, i2);
    }

    public void addModifierPadding(float f, float f2, float f3, float f4) {
        PaddingModifierOperation.apply(this.mBuffer, f, f2, f3, f4);
    }

    public void addModifierOffset(float f, float f2) {
        OffsetModifierOperation.apply(this.mBuffer, f, f2);
    }

    public void addModifierZIndex(float f) {
        ZIndexModifierOperation.apply(this.mBuffer, f);
    }

    public void addModifierRipple() {
        RippleModifierOperation.apply(this.mBuffer);
    }

    public void addModifierMarquee(int i, int i2, float f, float f2, float f3, float f4) {
        MarqueeModifierOperation.apply(this.mBuffer, i, i2, f, f2, f3, f4);
    }

    public void addModifierGraphicsLayer(HashMap<Integer, Object> hashMap) {
        GraphicsLayerModifierOperation.apply(this.mBuffer, hashMap);
    }

    public void addRoundClipRectModifier(float f, float f2, float f3, float f4) {
        RoundedClipRectModifierOperation.apply(this.mBuffer, f, f2, f3, f4);
    }

    public void addClipRectModifier() {
        ClipRectModifierOperation.apply(this.mBuffer);
    }

    public void addLoopStart(int i, float f, float f2, float f3) {
        LoopOperation.apply(this.mBuffer, i, f, f2, f3);
    }

    public void addLoopEnd() {
        ContainerEnd.apply(this.mBuffer);
    }

    public void addStateLayout(int i, int i2, int i3, int i4, int i5) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        StateLayout.apply(this.mBuffer, componentId, i2, i3, i4, i5);
    }

    public void addBoxStart(int i, int i2, int i3, int i4) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        BoxLayout.apply(this.mBuffer, componentId, i2, i3, i4);
    }

    public void addFitBoxStart(int i, int i2, int i3, int i4) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        FitBoxLayout.apply(this.mBuffer, componentId, i2, i3, i4);
    }

    public void addImage(int i, int i2, int i3, int i4, float f) {
        this.mLastComponentId = getComponentId(i);
        ImageLayout.apply(this.mBuffer, i, i2, i3, i4, f);
    }

    public void addRowStart(int i, int i2, int i3, int i4, float f) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        RowLayout.apply(this.mBuffer, componentId, i2, i3, i4, f);
    }

    public void addCollapsibleRowStart(int i, int i2, int i3, int i4, float f) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        CollapsibleRowLayout.apply(this.mBuffer, componentId, i2, i3, i4, f);
    }

    public void addColumnStart(int i, int i2, int i3, int i4, float f) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        ColumnLayout.apply(this.mBuffer, componentId, i2, i3, i4, f);
    }

    public void addCollapsibleColumnStart(int i, int i2, int i3, int i4, float f) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        CollapsibleColumnLayout.apply(this.mBuffer, componentId, i2, i3, i4, f);
    }

    public void addCanvasStart(int i, int i2) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        CanvasLayout.apply(this.mBuffer, componentId, i2);
    }

    public void addCanvasContentStart(int i) {
        int componentId = getComponentId(i);
        this.mLastComponentId = componentId;
        CanvasContent.apply(this.mBuffer, componentId);
    }

    public void addRootStart() {
        int componentId = getComponentId(-1);
        this.mLastComponentId = componentId;
        RootLayoutComponent.apply(this.mBuffer, componentId);
    }

    public void addContentStart() {
        int componentId = getComponentId(-1);
        this.mLastComponentId = componentId;
        LayoutComponentContent.apply(this.mBuffer, componentId);
    }

    public void addCanvasOperationsStart() {
        CanvasOperations.apply(this.mBuffer);
    }

    public void addRunActionsStart() {
        RunActionOperation.apply(this.mBuffer);
    }

    public void addComponentWidthValue(int i) {
        ComponentValue.apply(this.mBuffer, 0, this.mLastComponentId, i);
    }

    public void addComponentHeightValue(int i) {
        ComponentValue.apply(this.mBuffer, 1, this.mLastComponentId, i);
    }

    public void addTextComponentStart(int i, int i2, int i3, int i4, float f, int i5, float f2, String str, int i6, int i7, int i8) {
        this.mLastComponentId = getComponentId(i);
        TextLayout.apply(this.mBuffer, this.mLastComponentId, i2, i3, i4, f, i5, f2, str != null ? addText(str) : -1, i6, i7, i8);
    }

    public int createID(int i) {
        return this.mRemoteComposeState.nextId(i);
    }

    public int nextId() {
        return this.mRemoteComposeState.nextId();
    }

    public void addImpulse(float f, float f2) {
        ImpulseOperation.apply(this.mBuffer, f, f2);
    }

    public void addImpulseProcess() {
        ImpulseProcess.apply(this.mBuffer);
    }

    public void addImpulseEnd() {
        ContainerEnd.apply(this.mBuffer);
    }

    public void addParticles(int i, int[] iArr, float[][] fArr, int i2) {
        ParticlesCreate.apply(this.mBuffer, i, iArr, fArr, i2);
    }

    public void addParticlesLoop(int i, float[] fArr, float[][] fArr2) {
        ParticlesLoop.apply(this.mBuffer, i, fArr, fArr2);
    }

    public void addParticleLoopEnd() {
        ContainerEnd.apply(this.mBuffer);
    }

    public void defineFloatFunction(int i, int[] iArr) {
        FloatFunctionDefine.apply(this.mBuffer, i, iArr);
    }

    public void addEndFloatFunctionDef() {
        ContainerEnd.apply(this.mBuffer);
    }

    public void callFloatFunction(int i, float[] fArr) {
        FloatFunctionCall.apply(this.mBuffer, i, fArr);
    }

    public float bitmapAttribute(int i, short s) {
        int nextId = this.mRemoteComposeState.nextId();
        ImageAttribute.apply(this.mBuffer, nextId, i, s, null);
        return Utils.asNan(nextId);
    }

    public float textAttribute(int i, short s) {
        int nextId = this.mRemoteComposeState.nextId();
        TextAttribute.apply(this.mBuffer, nextId, i, s);
        return Utils.asNan(nextId);
    }

    public float timeAttribute(int i, short s, int... iArr) {
        int nextId = this.mRemoteComposeState.nextId();
        TimeAttribute.apply(this.mBuffer, nextId, i, s, iArr);
        return Utils.asNan(nextId);
    }

    public void drawComponentContent() {
        DrawContent.apply(this.mBuffer);
    }

    private int storeBitmap(Object obj) {
        int dataGetId = this.mRemoteComposeState.dataGetId(obj);
        if (dataGetId != -1) {
            return dataGetId;
        }
        int cacheData = this.mRemoteComposeState.cacheData(obj);
        byte[] imageToByteArray = this.mPlatform.imageToByteArray(obj);
        short imageWidth = (short) this.mPlatform.getImageWidth(obj);
        short imageHeight = (short) this.mPlatform.getImageHeight(obj);
        if (this.mPlatform.isAlpha8Image(obj)) {
            BitmapData.apply(this.mBuffer, cacheData, (short) 4, imageWidth, (short) 0, imageHeight, imageToByteArray);
            return cacheData;
        }
        BitmapData.apply(this.mBuffer, cacheData, imageWidth, imageHeight, imageToByteArray);
        return cacheData;
    }

    public void pathCombine(int i, int i2, int i3, byte b) {
        PathCombine.apply(this.mBuffer, i, i2, i3, b);
    }

    public void performHaptic(int i) {
        HapticFeedback.apply(this.mBuffer, i);
    }

    public void addConditionalOperations(byte b, float f, float f2) {
        ConditionalOperations.apply(this.mBuffer, b, f, f2);
    }

    public void addDebugMessage(int i, float f, int i2) {
        DebugMessage.apply(this.mBuffer, i, f, i2);
    }

    public float getColorAttribute(int i, short s) {
        int nextId = this.mRemoteComposeState.nextId();
        ColorAttribute.apply(this.mBuffer, nextId, i, s);
        return Utils.asNan(nextId);
    }
}
