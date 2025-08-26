package android.graphics;

import android.app.compat.CompatChanges;
import android.graphics.fonts.FontVariationAxis;
import android.os.LocaleList;
import android.text.GraphicsOperations;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.util.Log;
import com.android.graphics.hwui.flags.Flags;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Paint {
    public static final int ANTI_ALIAS_FLAG = 1;
    public static final int AUTO_HINTING_TEXT_FLAG = 2048;
    public static final int BIDI_DEFAULT_LTR = 2;
    public static final int BIDI_DEFAULT_RTL = 3;
    private static final int BIDI_FLAG_MASK = 7;
    public static final int BIDI_FORCE_LTR = 4;
    public static final int BIDI_FORCE_RTL = 5;
    public static final int BIDI_LTR = 0;
    private static final int BIDI_MAX_FLAG_VALUE = 5;
    public static final int BIDI_RTL = 1;
    public static final int CURSOR_AFTER = 0;
    public static final int CURSOR_AT = 4;
    public static final int CURSOR_AT_OR_AFTER = 1;
    public static final int CURSOR_AT_OR_BEFORE = 3;
    public static final int CURSOR_BEFORE = 2;
    private static final int CURSOR_OPT_MAX_VALUE = 4;
    public static final long DEPRECATE_UI_FONT = 279646685;
    public static final long DEPRECATE_UI_FONT_ENFORCE = 349519475;
    public static final int DEV_KERN_TEXT_FLAG = 256;
    public static final int DIRECTION_LTR = 0;
    public static final int DIRECTION_RTL = 1;
    public static final int DITHER_FLAG = 4;
    private static final int ELEGANT_TEXT_HEIGHT_DISABLED = 1;
    private static final int ELEGANT_TEXT_HEIGHT_ENABLED = 0;
    private static final int ELEGANT_TEXT_HEIGHT_UNSET = -1;
    public static final int EMBEDDED_BITMAP_TEXT_FLAG = 1024;
    public static final int END_HYPHEN_EDIT_INSERT_ARMENIAN_HYPHEN = 3;
    public static final int END_HYPHEN_EDIT_INSERT_HYPHEN = 2;
    public static final int END_HYPHEN_EDIT_INSERT_MAQAF = 4;
    public static final int END_HYPHEN_EDIT_INSERT_UCAS_HYPHEN = 5;
    public static final int END_HYPHEN_EDIT_INSERT_ZWJ_AND_HYPHEN = 6;
    public static final int END_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int END_HYPHEN_EDIT_REPLACE_WITH_HYPHEN = 1;
    public static final int FAKE_BOLD_TEXT_FLAG = 32;
    public static final int FILTER_BITMAP_FLAG = 2;
    static final int HIDDEN_DEFAULT_PAINT_FLAGS = 1282;
    public static final int HINTING_OFF = 0;
    public static final int HINTING_ON = 1;
    public static final int LCD_RENDER_TEXT_FLAG = 512;
    public static final int LINEAR_TEXT_FLAG = 64;
    public static final int START_HYPHEN_EDIT_INSERT_HYPHEN = 1;
    public static final int START_HYPHEN_EDIT_INSERT_ZWJ = 2;
    public static final int START_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int STRIKE_THRU_TEXT_FLAG = 16;
    public static final int SUBPIXEL_TEXT_FLAG = 128;
    private static final String TAG = "Paint";
    public static final int TEXT_RUN_FLAG_LEFT_EDGE = 8192;
    public static final int TEXT_RUN_FLAG_RIGHT_EDGE = 16384;
    public static final int UNDERLINE_TEXT_FLAG = 8;
    public static final int VERTICAL_TEXT_FLAG = 4096;
    public int mBidiFlags;
    private long mColor;
    private ColorFilter mColorFilter;
    private float mCompatScaling;
    private String mFontFeatureSettings;
    private String mFontVariationOverride;
    private String mFontVariationSettings;
    private boolean mHasCompatScaling;
    private float mInvCompatScaling;
    private LocaleList mLocales;
    private MaskFilter mMaskFilter;
    private MyanmarEncoding mMyanmarEncoding;
    private long mNativeColorFilter;
    private long mNativePaint;
    private long mNativeShader;
    private long mNativeXfermode;
    private PathEffect mPathEffect;
    private Shader mShader;
    private long mShadowLayerColor;
    private float mShadowLayerDx;
    private float mShadowLayerDy;
    private float mShadowLayerRadius;
    private Typeface mTypeface;
    private boolean mUseCustomMyanmarEncoding;
    private Xfermode mXfermode;
    private static final Object sCacheLock = new Object();
    private static final HashMap<String, Integer> sMinikinLocaleListIdCache = new HashMap<>();
    static final Style[] sStyleArray = {Style.FILL, Style.STROKE, Style.FILL_AND_STROKE};
    static final Cap[] sCapArray = {Cap.BUTT, Cap.ROUND, Cap.SQUARE};
    static final Join[] sJoinArray = {Join.MITER, Join.ROUND, Join.BEVEL};
    static final Align[] sAlignArray = {Align.LEFT, Align.CENTER, Align.RIGHT};

    @Retention(RetentionPolicy.SOURCE)
    public @interface CursorOption {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EndHyphenEdit {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PaintFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartHyphenEdit {
    }

    @CriticalNative
    private static native void nAddFontVariationToBuilder(long j, int i, float f);

    @CriticalNative
    private static native float nAscent(long j);

    private static native int nBreakText(long j, String str, boolean z, float f, int i, float[] fArr);

    private static native int nBreakText(long j, char[] cArr, int i, int i2, float f, int i3, float[] fArr);

    @CriticalNative
    private static native long nCreateFontVariationBuilder(int i);

    @CriticalNative
    private static native float nDescent(long j);

    @CriticalNative
    private static native boolean nEqualsForTextMeasurement(long j, long j2);

    private static native void nGetCharArrayBounds(long j, char[] cArr, int i, int i2, int i3, Rect rect);

    @CriticalNative
    private static native int nGetElegantTextHeight(long j);

    @CriticalNative
    private static native int nGetEndHyphenEdit(long j);

    @CriticalNative
    private static native boolean nGetFillPath(long j, long j2, long j3);

    @CriticalNative
    private static native int nGetFlags(long j);

    @FastNative
    private static native float nGetFontMetrics(long j, FontMetrics fontMetrics, boolean z);

    @FastNative
    private static native int nGetFontMetricsInt(long j, FontMetricsInt fontMetricsInt, boolean z);

    private static native void nGetFontMetricsIntForText(long j, String str, int i, int i2, int i3, int i4, boolean z, FontMetricsInt fontMetricsInt);

    private static native void nGetFontMetricsIntForText(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, FontMetricsInt fontMetricsInt);

    @CriticalNative
    private static native int nGetHinting(long j);

    @CriticalNative
    private static native float nGetLetterSpacing(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    private static native int nGetOffsetForAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, float f);

    private static native float nGetRunAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5);

    private static native float nGetRunCharacterAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF);

    private static native float nGetRunCharacterAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF, RunInfo runInfo);

    @CriticalNative
    private static native int nGetStartHyphenEdit(long j);

    @CriticalNative
    private static native float nGetStrikeThruPosition(long j);

    @CriticalNative
    private static native float nGetStrikeThruThickness(long j);

    private static native void nGetStringBounds(long j, String str, int i, int i2, int i3, Rect rect);

    @CriticalNative
    private static native int nGetStrokeCap(long j);

    @CriticalNative
    private static native int nGetStrokeJoin(long j);

    @CriticalNative
    private static native float nGetStrokeMiter(long j);

    @CriticalNative
    private static native float nGetStrokeWidth(long j);

    @CriticalNative
    private static native int nGetStyle(long j);

    private static native float nGetTextAdvances(long j, String str, int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    private static native float nGetTextAdvances(long j, char[] cArr, int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    @CriticalNative
    private static native int nGetTextAlign(long j);

    private static native void nGetTextPath(long j, int i, String str, int i2, int i3, float f, float f2, long j2);

    private static native void nGetTextPath(long j, int i, char[] cArr, int i2, int i3, float f, float f2, long j2);

    private native int nGetTextRunCursor(long j, String str, int i, int i2, int i3, int i4, int i5);

    private native int nGetTextRunCursor(long j, char[] cArr, int i, int i2, int i3, int i4, int i5);

    @CriticalNative
    private static native float nGetTextScaleX(long j);

    @CriticalNative
    private static native float nGetTextSize(long j);

    @CriticalNative
    private static native float nGetTextSkewX(long j);

    @CriticalNative
    private static native float nGetUnderlinePosition(long j);

    @CriticalNative
    private static native float nGetUnderlineThickness(long j);

    @CriticalNative
    private static native float nGetWordSpacing(long j);

    private static native boolean nHasGlyph(long j, int i, String str);

    @CriticalNative
    private static native boolean nHasShadowLayer(long j);

    private static native long nInit();

    private static native long nInitWithPaint(long j);

    @CriticalNative
    private static native void nReset(long j);

    @CriticalNative
    private static native void nSet(long j, long j2);

    @CriticalNative
    private static native void nSetAlpha(long j, int i);

    @CriticalNative
    private static native void nSetAntiAlias(long j, boolean z);

    @CriticalNative
    private static native void nSetColor(long j, int i);

    @CriticalNative
    private static native void nSetColor(long j, long j2, long j3);

    @CriticalNative
    private static native long nSetColorFilter(long j, long j2);

    @CriticalNative
    private static native void nSetDither(long j, boolean z);

    @CriticalNative
    private static native void nSetElegantTextHeight(long j, int i);

    @CriticalNative
    private static native void nSetEndHyphenEdit(long j, int i);

    @CriticalNative
    private static native void nSetFakeBoldText(long j, boolean z);

    @CriticalNative
    private static native void nSetFilterBitmap(long j, boolean z);

    @CriticalNative
    private static native void nSetFlags(long j, int i);

    @FastNative
    private static native void nSetFontFeatureSettings(long j, String str);

    @CriticalNative
    private static native void nSetFontVariationOverride(long j, long j2);

    @CriticalNative
    private static native void nSetHinting(long j, int i);

    @CriticalNative
    private static native void nSetLetterSpacing(long j, float f);

    @CriticalNative
    private static native void nSetLinearText(long j, boolean z);

    @CriticalNative
    private static native long nSetMaskFilter(long j, long j2);

    private static native void nSetMyanmarEncoding(long j, int i);

    @CriticalNative
    private static native long nSetPathEffect(long j, long j2);

    @CriticalNative
    private static native long nSetShader(long j, long j2);

    @CriticalNative
    private static native void nSetShadowLayer(long j, float f, float f2, float f3, long j2, long j3);

    @CriticalNative
    private static native void nSetStartHyphenEdit(long j, int i);

    @CriticalNative
    private static native void nSetStrikeThruText(long j, boolean z);

    @CriticalNative
    private static native void nSetStrokeCap(long j, int i);

    @CriticalNative
    private static native void nSetStrokeJoin(long j, int i);

    @CriticalNative
    private static native void nSetStrokeMiter(long j, float f);

    @CriticalNative
    private static native void nSetStrokeWidth(long j, float f);

    @CriticalNative
    private static native void nSetStyle(long j, int i);

    @CriticalNative
    private static native void nSetSubpixelText(long j, boolean z);

    @CriticalNative
    private static native void nSetTextAlign(long j, int i);

    @FastNative
    private static native int nSetTextLocales(long j, String str);

    @CriticalNative
    private static native void nSetTextLocalesByMinikinLocaleListId(long j, int i);

    @CriticalNative
    private static native void nSetTextScaleX(long j, float f);

    @CriticalNative
    private static native void nSetTextSize(long j, float f);

    @CriticalNative
    private static native void nSetTextSkewX(long j, float f);

    @CriticalNative
    private static native void nSetTypeface(long j, long j2);

    @CriticalNative
    private static native void nSetUnderlineText(long j, boolean z);

    @CriticalNative
    private static native void nSetWordSpacing(long j, float f);

    @CriticalNative
    private static native void nSetXfermode(long j, int i);

    @CriticalNative
    private static native void nSetXfermode(long j, long j2);

    @Deprecated
    public Rasterizer getRasterizer() {
        return null;
    }

    @Deprecated
    public Rasterizer setRasterizer(Rasterizer rasterizer) {
        return rasterizer;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Paint.class.getClassLoader(), Paint.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public enum Style {
        FILL(0),
        STROKE(1),
        FILL_AND_STROKE(2);

        final int nativeInt;

        Style(int i) {
            this.nativeInt = i;
        }
    }

    public enum Cap {
        BUTT(0),
        ROUND(1),
        SQUARE(2);

        final int nativeInt;

        Cap(int i) {
            this.nativeInt = i;
        }
    }

    public enum Join {
        MITER(0),
        ROUND(1),
        BEVEL(2);

        final int nativeInt;

        Join(int i) {
            this.nativeInt = i;
        }
    }

    public enum Align {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        final int nativeInt;

        Align(int i) {
            this.nativeInt = i;
        }
    }

    public Paint() {
        this(1);
    }

    public Paint(int i) {
        this.mUseCustomMyanmarEncoding = false;
        this.mBidiFlags = 2;
        this.mNativePaint = nInit();
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePaint);
        setFlags(i | 1282);
        this.mInvCompatScaling = 1.0f;
        this.mCompatScaling = 1.0f;
        setTextLocales(LocaleList.getAdjustedDefault());
        this.mColor = Color.pack(-16777216);
        resetElegantTextHeight();
    }

    public Paint(Paint paint) {
        this.mUseCustomMyanmarEncoding = false;
        this.mBidiFlags = 2;
        this.mNativePaint = nInitWithPaint(paint.getNativeInstance());
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePaint);
        setClassVariablesFrom(paint);
    }

    public void reset() {
        nReset(this.mNativePaint);
        setFlags(1283);
        this.mColor = Color.pack(-16777216);
        this.mColorFilter = null;
        this.mMaskFilter = null;
        this.mPathEffect = null;
        this.mShader = null;
        this.mNativeShader = 0L;
        this.mNativeXfermode = 0L;
        this.mTypeface = null;
        this.mXfermode = null;
        this.mHasCompatScaling = false;
        this.mCompatScaling = 1.0f;
        this.mInvCompatScaling = 1.0f;
        this.mBidiFlags = 2;
        setTextLocales(LocaleList.getAdjustedDefault());
        resetElegantTextHeight();
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
        this.mShadowLayerRadius = 0.0f;
        this.mShadowLayerDx = 0.0f;
        this.mShadowLayerDy = 0.0f;
        this.mShadowLayerColor = Color.pack(0);
        this.mMyanmarEncoding = MyanmarEncoding.ME_UNICODE;
    }

    public void set(Paint paint) {
        if (this != paint) {
            nSet(this.mNativePaint, paint.mNativePaint);
            setClassVariablesFrom(paint);
        }
        nSetTypeface(this.mNativePaint, getTypefaceNativeInstance(this.mTypeface));
    }

    private void setClassVariablesFrom(Paint paint) {
        this.mColor = paint.mColor;
        this.mColorFilter = paint.mColorFilter;
        this.mMaskFilter = paint.mMaskFilter;
        this.mPathEffect = paint.mPathEffect;
        this.mShader = paint.mShader;
        this.mNativeShader = paint.mNativeShader;
        this.mTypeface = paint.mTypeface;
        this.mXfermode = paint.mXfermode;
        this.mNativeXfermode = paint.mNativeXfermode;
        this.mHasCompatScaling = paint.mHasCompatScaling;
        this.mCompatScaling = paint.mCompatScaling;
        this.mInvCompatScaling = paint.mInvCompatScaling;
        this.mBidiFlags = paint.mBidiFlags;
        this.mLocales = paint.mLocales;
        this.mFontFeatureSettings = paint.mFontFeatureSettings;
        this.mFontVariationSettings = paint.mFontVariationSettings;
        this.mShadowLayerRadius = paint.mShadowLayerRadius;
        this.mShadowLayerDx = paint.mShadowLayerDx;
        this.mShadowLayerDy = paint.mShadowLayerDy;
        this.mShadowLayerColor = paint.mShadowLayerColor;
        this.mMyanmarEncoding = paint.mMyanmarEncoding;
        this.mUseCustomMyanmarEncoding = paint.mUseCustomMyanmarEncoding;
    }

    public void setCompatibilityScaling(float f) {
        if (f == 1.0d) {
            this.mHasCompatScaling = false;
            this.mInvCompatScaling = 1.0f;
            this.mCompatScaling = 1.0f;
        } else {
            this.mHasCompatScaling = true;
            this.mCompatScaling = f;
            this.mInvCompatScaling = 1.0f / f;
        }
    }

    public synchronized long getNativeInstance() {
        boolean zIsFilterBitmap = isFilterBitmap();
        Shader shader = this.mShader;
        long nativeInstance = 0;
        long nativeInstance2 = shader == null ? 0L : shader.getNativeInstance(zIsFilterBitmap);
        if (nativeInstance2 != this.mNativeShader) {
            this.mNativeShader = nativeInstance2;
            nSetShader(this.mNativePaint, nativeInstance2);
        }
        ColorFilter colorFilter = this.mColorFilter;
        if (colorFilter != null) {
            nativeInstance = colorFilter.getNativeInstance();
        }
        if (nativeInstance != this.mNativeColorFilter) {
            this.mNativeColorFilter = nativeInstance;
            nSetColorFilter(this.mNativePaint, nativeInstance);
        }
        if (Flags.runtimeColorFiltersBlenders()) {
            Xfermode xfermode = this.mXfermode;
            if (xfermode instanceof RuntimeXfermode) {
                long jCreateNativeInstance = ((RuntimeXfermode) xfermode).createNativeInstance();
                if (jCreateNativeInstance != this.mNativeXfermode) {
                    this.mNativeXfermode = jCreateNativeInstance;
                    nSetXfermode(this.mNativePaint, jCreateNativeInstance);
                }
            }
        }
        return this.mNativePaint;
    }

    public int getBidiFlags() {
        return this.mBidiFlags;
    }

    public void setBidiFlags(int i) {
        int i2 = i & 7;
        if (i2 > 5) {
            throw new IllegalArgumentException("unknown bidi flag: " + i2);
        }
        this.mBidiFlags = i2;
    }

    public int getFlags() {
        return nGetFlags(this.mNativePaint);
    }

    public void setFlags(int i) {
        nSetFlags(this.mNativePaint, i);
    }

    public int getHinting() {
        return nGetHinting(this.mNativePaint);
    }

    public void setHinting(int i) {
        nSetHinting(this.mNativePaint, i);
    }

    public final boolean isAntiAlias() {
        return (getFlags() & 1) != 0;
    }

    public void setAntiAlias(boolean z) {
        nSetAntiAlias(this.mNativePaint, z);
    }

    public final boolean isDither() {
        return (getFlags() & 4) != 0;
    }

    public void setDither(boolean z) {
        nSetDither(this.mNativePaint, z);
    }

    public final boolean isLinearText() {
        return (getFlags() & 64) != 0;
    }

    public void setLinearText(boolean z) {
        nSetLinearText(this.mNativePaint, z);
    }

    public final boolean isSubpixelText() {
        return (getFlags() & 128) != 0;
    }

    public void setSubpixelText(boolean z) {
        nSetSubpixelText(this.mNativePaint, z);
    }

    public final boolean isUnderlineText() {
        return (getFlags() & 8) != 0;
    }

    public float getUnderlinePosition() {
        return nGetUnderlinePosition(this.mNativePaint);
    }

    public float getUnderlineThickness() {
        return nGetUnderlineThickness(this.mNativePaint);
    }

    public void setUnderlineText(boolean z) {
        nSetUnderlineText(this.mNativePaint, z);
    }

    public final boolean isStrikeThruText() {
        return (getFlags() & 16) != 0;
    }

    public float getStrikeThruPosition() {
        return nGetStrikeThruPosition(this.mNativePaint);
    }

    public float getStrikeThruThickness() {
        return nGetStrikeThruThickness(this.mNativePaint);
    }

    public void setStrikeThruText(boolean z) {
        nSetStrikeThruText(this.mNativePaint, z);
    }

    public final boolean isFakeBoldText() {
        return (getFlags() & 32) != 0;
    }

    public void setFakeBoldText(boolean z) {
        nSetFakeBoldText(this.mNativePaint, z);
    }

    public final boolean isFilterBitmap() {
        return (getFlags() & 2) != 0;
    }

    public void setFilterBitmap(boolean z) {
        nSetFilterBitmap(this.mNativePaint, z);
    }

    public Style getStyle() {
        return sStyleArray[nGetStyle(this.mNativePaint)];
    }

    public void setStyle(Style style) {
        nSetStyle(this.mNativePaint, style.nativeInt);
    }

    public int getColor() {
        return Color.toArgb(this.mColor);
    }

    public long getColorLong() {
        return this.mColor;
    }

    public void setColor(int i) {
        nSetColor(this.mNativePaint, i);
        this.mColor = Color.pack(i);
    }

    public void setColor(long j) {
        nSetColor(this.mNativePaint, Color.colorSpace(j).getNativeInstance(), j);
        this.mColor = j;
    }

    public int getAlpha() {
        return Math.round(Color.alpha(this.mColor) * 255.0f);
    }

    public void setAlpha(int i) {
        this.mColor = Color.pack(Color.red(this.mColor), Color.green(this.mColor), Color.blue(this.mColor), i * 0.003921569f, Color.colorSpace(this.mColor));
        nSetAlpha(this.mNativePaint, i);
    }

    public void setARGB(int i, int i2, int i3, int i4) {
        setColor((i << 24) | (i2 << 16) | (i3 << 8) | i4);
    }

    public float getStrokeWidth() {
        return nGetStrokeWidth(this.mNativePaint);
    }

    public void setStrokeWidth(float f) {
        nSetStrokeWidth(this.mNativePaint, f);
    }

    public float getStrokeMiter() {
        return nGetStrokeMiter(this.mNativePaint);
    }

    public void setStrokeMiter(float f) {
        nSetStrokeMiter(this.mNativePaint, f);
    }

    public Cap getStrokeCap() {
        return sCapArray[nGetStrokeCap(this.mNativePaint)];
    }

    public void setStrokeCap(Cap cap) {
        nSetStrokeCap(this.mNativePaint, cap.nativeInt);
    }

    public Join getStrokeJoin() {
        return sJoinArray[nGetStrokeJoin(this.mNativePaint)];
    }

    public void setStrokeJoin(Join join) {
        nSetStrokeJoin(this.mNativePaint, join.nativeInt);
    }

    public boolean getFillPath(Path path, Path path2) {
        return nGetFillPath(this.mNativePaint, path.readOnlyNI(), path2.mutateNI());
    }

    public Shader getShader() {
        return this.mShader;
    }

    public Shader setShader(Shader shader) {
        if (this.mShader != shader) {
            this.mNativeShader = -1L;
            nSetShader(this.mNativePaint, 0L);
        }
        this.mShader = shader;
        return shader;
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public ColorFilter setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mNativeColorFilter = -1L;
        }
        this.mColorFilter = colorFilter;
        return colorFilter;
    }

    public Xfermode getXfermode() {
        return this.mXfermode;
    }

    public BlendMode getBlendMode() {
        Xfermode xfermode = this.mXfermode;
        if (xfermode == null || !(xfermode instanceof PorterDuffXfermode)) {
            return null;
        }
        return BlendMode.fromValue(((PorterDuffXfermode) xfermode).porterDuffMode);
    }

    public Xfermode setXfermode(Xfermode xfermode) {
        return installXfermode(xfermode);
    }

    private Xfermode installXfermode(Xfermode xfermode) {
        if (Flags.runtimeColorFiltersBlenders() && (xfermode instanceof RuntimeXfermode)) {
            this.mXfermode = xfermode;
            nSetXfermode(this.mNativePaint, ((RuntimeXfermode) xfermode).createNativeInstance());
            return xfermode;
        }
        int i = xfermode instanceof PorterDuffXfermode ? ((PorterDuffXfermode) xfermode).porterDuffMode : PorterDuffXfermode.DEFAULT;
        Xfermode xfermode2 = this.mXfermode;
        if (i != (xfermode2 instanceof PorterDuffXfermode ? ((PorterDuffXfermode) xfermode2).porterDuffMode : PorterDuffXfermode.DEFAULT)) {
            nSetXfermode(this.mNativePaint, i);
        }
        this.mXfermode = xfermode;
        return xfermode;
    }

    public void setBlendMode(BlendMode blendMode) {
        installXfermode(blendMode != null ? blendMode.getXfermode() : null);
    }

    public PathEffect getPathEffect() {
        return this.mPathEffect;
    }

    public PathEffect setPathEffect(PathEffect pathEffect) {
        nSetPathEffect(this.mNativePaint, pathEffect != null ? pathEffect.native_instance : 0L);
        this.mPathEffect = pathEffect;
        return pathEffect;
    }

    public MaskFilter getMaskFilter() {
        return this.mMaskFilter;
    }

    public MaskFilter setMaskFilter(MaskFilter maskFilter) {
        nSetMaskFilter(this.mNativePaint, maskFilter != null ? maskFilter.native_instance : 0L);
        this.mMaskFilter = maskFilter;
        return maskFilter;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public Typeface setTypeface(Typeface typeface) {
        if (com.android.text.flags.Flags.typefaceRedesignReadonly() && typeface != null && typeface.isVariationInstance()) {
            Log.w(TAG, "Attempting to set a Typeface on a Paint object that was previously configured with setFontVariationSettings(). This is no longer supported as of Target SDK 36. To apply font variations, call setFontVariationSettings() directly on the Paint object instead.");
        }
        return setTypefaceWithoutWarning(typeface);
    }

    private Typeface setTypefaceWithoutWarning(Typeface typeface) {
        if (typeface != null) {
            long j = typeface.native_instance;
        }
        nSetTypeface(this.mNativePaint, getTypefaceNativeInstance(typeface));
        this.mTypeface = typeface;
        return typeface;
    }

    private long getTypefaceNativeInstance(Typeface typeface) {
        long j;
        if (typeface == null) {
            j = 0;
        } else if (Typeface.isFlipFontUsed && typeface.isLikeDefault) {
            j = Typeface.DEFAULT.native_instance;
        } else {
            j = typeface.native_instance;
        }
        return (j == 0 && Typeface.isFlipFontUsed) ? Typeface.DEFAULT.native_instance : j;
    }

    public void setShadowLayer(float f, float f2, float f3, int i) {
        setShadowLayer(f, f2, f3, Color.pack(i));
    }

    public void setShadowLayer(float f, float f2, float f3, long j) {
        nSetShadowLayer(this.mNativePaint, f, f2, f3, Color.colorSpace(j).getNativeInstance(), j);
        this.mShadowLayerRadius = f;
        this.mShadowLayerDx = f2;
        this.mShadowLayerDy = f3;
        this.mShadowLayerColor = j;
    }

    public void clearShadowLayer() {
        setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }

    public boolean hasShadowLayer() {
        return nHasShadowLayer(this.mNativePaint);
    }

    public float getShadowLayerRadius() {
        return this.mShadowLayerRadius;
    }

    public float getShadowLayerDx() {
        return this.mShadowLayerDx;
    }

    public float getShadowLayerDy() {
        return this.mShadowLayerDy;
    }

    public int getShadowLayerColor() {
        return Color.toArgb(this.mShadowLayerColor);
    }

    public long getShadowLayerColorLong() {
        return this.mShadowLayerColor;
    }

    public Align getTextAlign() {
        return sAlignArray[nGetTextAlign(this.mNativePaint)];
    }

    public void setTextAlign(Align align) {
        nSetTextAlign(this.mNativePaint, align.nativeInt);
    }

    public Locale getTextLocale() {
        return this.mLocales.get(0);
    }

    public LocaleList getTextLocales() {
        return this.mLocales;
    }

    public void setTextLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("locale cannot be null");
        }
        if (!this.mUseCustomMyanmarEncoding) {
            if ("ZG".equals(locale.getCountry())) {
                if (this.mMyanmarEncoding != MyanmarEncoding.ME_ZAWGYI) {
                    this.mMyanmarEncoding = MyanmarEncoding.ME_ZAWGYI;
                    nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_ZAWGYI.nativeInt);
                }
            } else if (this.mMyanmarEncoding != MyanmarEncoding.ME_UNICODE) {
                this.mMyanmarEncoding = MyanmarEncoding.ME_UNICODE;
                nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_UNICODE.nativeInt);
            }
        }
        LocaleList localeList = this.mLocales;
        if (localeList != null && localeList.size() == 1 && locale.equals(this.mLocales.get(0))) {
            return;
        }
        this.mLocales = new LocaleList(locale);
        syncTextLocalesWithMinikin();
    }

    public void setTextLocales(LocaleList localeList) {
        if (localeList == null || localeList.isEmpty()) {
            throw new IllegalArgumentException("locales cannot be null or empty");
        }
        if (localeList.equals(this.mLocales)) {
            return;
        }
        if (!this.mUseCustomMyanmarEncoding) {
            String country = localeList.get(0).getCountry();
            if (localeList.size() > 1 && (country == null || "".equals(country))) {
                country = localeList.get(1).getCountry();
            }
            if ("ZG".equals(country)) {
                if (this.mMyanmarEncoding != MyanmarEncoding.ME_ZAWGYI) {
                    this.mMyanmarEncoding = MyanmarEncoding.ME_ZAWGYI;
                    nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_ZAWGYI.nativeInt);
                }
            } else if (this.mMyanmarEncoding != MyanmarEncoding.ME_UNICODE) {
                this.mMyanmarEncoding = MyanmarEncoding.ME_UNICODE;
                nSetMyanmarEncoding(this.mNativePaint, MyanmarEncoding.ME_UNICODE.nativeInt);
            }
        }
        this.mLocales = localeList;
        syncTextLocalesWithMinikin();
    }

    private void syncTextLocalesWithMinikin() {
        String languageTags = this.mLocales.toLanguageTags();
        synchronized (sCacheLock) {
            HashMap<String, Integer> map = sMinikinLocaleListIdCache;
            Integer num = map.get(languageTags);
            if (num == null) {
                map.put(languageTags, Integer.valueOf(nSetTextLocales(this.mNativePaint, languageTags)));
            } else {
                nSetTextLocalesByMinikinLocaleListId(this.mNativePaint, num.intValue());
            }
        }
    }

    @Deprecated
    public boolean isElegantTextHeight() {
        return nGetElegantTextHeight(this.mNativePaint) != 1;
    }

    @Deprecated
    public void setElegantTextHeight(boolean z) {
        if (!com.android.text.flags.Flags.deprecateElegantTextHeightApi() || z || !CompatChanges.isChangeEnabled(DEPRECATE_UI_FONT_ENFORCE)) {
            nSetElegantTextHeight(this.mNativePaint, !z ? 1 : 0);
        } else {
            if (z) {
                return;
            }
            Log.w(TAG, "The elegant text height cannot be turned off.");
        }
    }

    private void resetElegantTextHeight() {
        if (CompatChanges.isChangeEnabled(DEPRECATE_UI_FONT)) {
            nSetElegantTextHeight(this.mNativePaint, -1);
        } else {
            nSetElegantTextHeight(this.mNativePaint, 1);
        }
    }

    public float getTextSize() {
        return nGetTextSize(this.mNativePaint);
    }

    public void setTextSize(float f) {
        nSetTextSize(this.mNativePaint, f);
    }

    public float getTextScaleX() {
        return nGetTextScaleX(this.mNativePaint);
    }

    public void setTextScaleX(float f) {
        nSetTextScaleX(this.mNativePaint, f);
    }

    public float getTextSkewX() {
        return nGetTextSkewX(this.mNativePaint);
    }

    public void setTextSkewX(float f) {
        nSetTextSkewX(this.mNativePaint, f);
    }

    public float getLetterSpacing() {
        return nGetLetterSpacing(this.mNativePaint);
    }

    public void setLetterSpacing(float f) {
        nSetLetterSpacing(this.mNativePaint, f);
    }

    public float getWordSpacing() {
        return nGetWordSpacing(this.mNativePaint);
    }

    public void setWordSpacing(float f) {
        nSetWordSpacing(this.mNativePaint, f);
    }

    public String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public void setFontFeatureSettings(String str) {
        if (str != null && str.equals("")) {
            str = null;
        }
        if (str == null && this.mFontFeatureSettings == null) {
            return;
        }
        if (str == null || !str.equals(this.mFontFeatureSettings)) {
            this.mFontFeatureSettings = str;
            nSetFontFeatureSettings(this.mNativePaint, str);
        }
    }

    public String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public boolean setFontVariationSettings(String str) {
        String strNullIfEmpty = TextUtils.nullIfEmpty(str);
        String str2 = this.mFontVariationSettings;
        if (strNullIfEmpty != str2 && (strNullIfEmpty == null || !strNullIfEmpty.equals(str2))) {
            if (strNullIfEmpty == null || strNullIfEmpty.length() == 0) {
                this.mFontVariationSettings = null;
                setTypefaceWithoutWarning(Typeface.createFromTypefaceWithVariation(this.mTypeface, Collections.EMPTY_LIST));
            } else {
                Typeface typeface = this.mTypeface;
                if (typeface == null) {
                    typeface = Typeface.DEFAULT;
                }
                FontVariationAxis[] fontVariationAxisArrFromFontVariationSettings = FontVariationAxis.fromFontVariationSettings(strNullIfEmpty);
                ArrayList arrayList = new ArrayList();
                for (FontVariationAxis fontVariationAxis : fontVariationAxisArrFromFontVariationSettings) {
                    if (typeface.isSupportedAxes(fontVariationAxis.getOpenTypeTagValue())) {
                        arrayList.add(fontVariationAxis);
                    }
                }
                if (arrayList.isEmpty()) {
                    return false;
                }
                this.mFontVariationSettings = strNullIfEmpty;
                setTypefaceWithoutWarning(Typeface.createFromTypefaceWithVariation(typeface, arrayList));
                return true;
            }
        }
        return true;
    }

    public void setFontVariationOverride(String str) {
        if (Objects.equals(str, this.mFontVariationOverride)) {
            return;
        }
        List<FontVariationAxis> listFromFontVariationSettingsForList = FontVariationAxis.fromFontVariationSettingsForList(str);
        long jNCreateFontVariationBuilder = nCreateFontVariationBuilder(listFromFontVariationSettingsForList.size());
        for (int i = 0; i < listFromFontVariationSettingsForList.size(); i++) {
            FontVariationAxis fontVariationAxis = listFromFontVariationSettingsForList.get(i);
            nAddFontVariationToBuilder(jNCreateFontVariationBuilder, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
        }
        nSetFontVariationOverride(this.mNativePaint, jNCreateFontVariationBuilder);
        this.mFontVariationOverride = str;
    }

    public String getFontVariationOverride() {
        return this.mFontVariationOverride;
    }

    public int getStartHyphenEdit() {
        return nGetStartHyphenEdit(this.mNativePaint);
    }

    public int getEndHyphenEdit() {
        return nGetEndHyphenEdit(this.mNativePaint);
    }

    public void setStartHyphenEdit(int i) {
        nSetStartHyphenEdit(this.mNativePaint, i);
    }

    public void setEndHyphenEdit(int i) {
        nSetEndHyphenEdit(this.mNativePaint, i);
    }

    public float ascent() {
        return nAscent(this.mNativePaint);
    }

    public float descent() {
        return nDescent(this.mNativePaint);
    }

    public static class FontMetrics {
        public float ascent;
        public float bottom;
        public float descent;
        public float leading;
        public float top;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof FontMetrics)) {
                FontMetrics fontMetrics = (FontMetrics) obj;
                if (fontMetrics.top == this.top && fontMetrics.ascent == this.ascent && fontMetrics.descent == this.descent && fontMetrics.bottom == this.bottom && fontMetrics.leading == this.leading) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.top), Float.valueOf(this.ascent), Float.valueOf(this.descent), Float.valueOf(this.bottom), Float.valueOf(this.leading));
        }

        public String toString() {
            return "FontMetrics{top=" + this.top + ", ascent=" + this.ascent + ", descent=" + this.descent + ", bottom=" + this.bottom + ", leading=" + this.leading + '}';
        }
    }

    public float getFontMetrics(FontMetrics fontMetrics) {
        return nGetFontMetrics(this.mNativePaint, fontMetrics, false);
    }

    public FontMetrics getFontMetrics() {
        FontMetrics fontMetrics = new FontMetrics();
        getFontMetrics(fontMetrics);
        return fontMetrics;
    }

    public void getFontMetricsForLocale(FontMetrics fontMetrics) {
        nGetFontMetrics(this.mNativePaint, fontMetrics, true);
    }

    public void getFontMetricsInt(CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, FontMetricsInt fontMetricsInt) {
        int i5;
        if (charSequence == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (i < 0 || i >= charSequence.length()) {
            throw new IllegalArgumentException("start argument is out of bounds.");
        }
        if (i2 < 0 || i + i2 > charSequence.length()) {
            throw new IllegalArgumentException("count argument is out of bounds.");
        }
        if (i3 < 0 || i3 >= charSequence.length()) {
            throw new IllegalArgumentException("ctxStart argument is out of bounds.");
        }
        if (i4 < 0 || (i5 = i3 + i4) > charSequence.length()) {
            throw new IllegalArgumentException("ctxCount argument is out of bounds.");
        }
        if (fontMetricsInt == null) {
            throw new IllegalArgumentException("outMetrics must not be null.");
        }
        if (i2 == 0) {
            getFontMetricsInt(fontMetricsInt);
            return;
        }
        if (charSequence instanceof String) {
            nGetFontMetricsIntForText(this.mNativePaint, (String) charSequence, i, i2, i3, i4, z, fontMetricsInt);
            return;
        }
        char[] cArrObtain = TemporaryBuffer.obtain(i4);
        try {
            TextUtils.getChars(charSequence, i3, i5, cArrObtain, 0);
            nGetFontMetricsIntForText(this.mNativePaint, cArrObtain, i - i3, i2, 0, i4, z, fontMetricsInt);
        } finally {
            TemporaryBuffer.recycle(cArrObtain);
        }
    }

    public void getFontMetricsInt(char[] cArr, int i, int i2, int i3, int i4, boolean z, FontMetricsInt fontMetricsInt) {
        if (cArr == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (i < 0 || i >= cArr.length) {
            throw new IllegalArgumentException("start argument is out of bounds.");
        }
        if (i2 < 0 || i + i2 > cArr.length) {
            throw new IllegalArgumentException("count argument is out of bounds.");
        }
        if (i3 < 0 || i3 >= cArr.length) {
            throw new IllegalArgumentException("ctxStart argument is out of bounds.");
        }
        if (i4 < 0 || i3 + i4 > cArr.length) {
            throw new IllegalArgumentException("ctxCount argument is out of bounds.");
        }
        if (fontMetricsInt == null) {
            throw new IllegalArgumentException("outMetrics must not be null.");
        }
        if (i2 == 0) {
            getFontMetricsInt(fontMetricsInt);
        } else {
            nGetFontMetricsIntForText(this.mNativePaint, cArr, i, i2, i3, i4, z, fontMetricsInt);
        }
    }

    public static class FontMetricsInt {
        public int ascent;
        public int bottom;
        public int descent;
        public int leading;
        public int top;

        public void set(FontMetricsInt fontMetricsInt) {
            this.top = fontMetricsInt.top;
            this.ascent = fontMetricsInt.ascent;
            this.descent = fontMetricsInt.descent;
            this.bottom = fontMetricsInt.bottom;
            this.leading = fontMetricsInt.leading;
        }

        public void set(FontMetrics fontMetrics) {
            this.top = (int) Math.floor(fontMetrics.top);
            this.ascent = Math.round(fontMetrics.ascent);
            this.descent = Math.round(fontMetrics.descent);
            this.bottom = (int) Math.ceil(fontMetrics.bottom);
            this.leading = Math.round(fontMetrics.leading);
        }

        public String toString() {
            return "FontMetricsInt: top=" + this.top + " ascent=" + this.ascent + " descent=" + this.descent + " bottom=" + this.bottom + " leading=" + this.leading;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FontMetricsInt)) {
                return false;
            }
            FontMetricsInt fontMetricsInt = (FontMetricsInt) obj;
            return this.top == fontMetricsInt.top && this.ascent == fontMetricsInt.ascent && this.descent == fontMetricsInt.descent && this.bottom == fontMetricsInt.bottom && this.leading == fontMetricsInt.leading;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.top), Integer.valueOf(this.ascent), Integer.valueOf(this.descent), Integer.valueOf(this.bottom), Integer.valueOf(this.leading));
        }
    }

    public int getFontMetricsInt(FontMetricsInt fontMetricsInt) {
        return nGetFontMetricsInt(this.mNativePaint, fontMetricsInt, false);
    }

    public FontMetricsInt getFontMetricsInt() {
        FontMetricsInt fontMetricsInt = new FontMetricsInt();
        getFontMetricsInt(fontMetricsInt);
        return fontMetricsInt;
    }

    public void getFontMetricsIntForLocale(FontMetricsInt fontMetricsInt) {
        nGetFontMetricsInt(this.mNativePaint, fontMetricsInt, true);
    }

    public static final class RunInfo {
        private int mClusterCount = 0;

        public int getClusterCount() {
            return this.mClusterCount;
        }

        public void setClusterCount(int i) {
            this.mClusterCount = i;
        }
    }

    public float getFontSpacing() {
        return getFontMetrics(null);
    }

    public float measureText(char[] cArr, int i, int i2) {
        double dCeil;
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((i | i2) < 0 || i + i2 > cArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0.0f;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                dCeil = Math.ceil(nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0));
            } else {
                float textSize = getTextSize();
                setTextSize(this.mCompatScaling * textSize);
                float fNGetTextAdvances = nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0);
                setTextSize(textSize);
                dCeil = Math.ceil(fNGetTextAdvances * this.mInvCompatScaling);
            }
            return (float) dCeil;
        } finally {
            setFlags(flags);
        }
    }

    public float measureText(String str, int i, int i2) {
        double dCeil;
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((i | i2 | (i2 - i) | (str.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (str.length() == 0 || i == i2) {
            return 0.0f;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                dCeil = Math.ceil(nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0));
            } else {
                float textSize = getTextSize();
                setTextSize(this.mCompatScaling * textSize);
                float fNGetTextAdvances = nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0);
                setTextSize(textSize);
                dCeil = Math.ceil(fNGetTextAdvances * this.mInvCompatScaling);
            }
            return (float) dCeil;
        } finally {
            setFlags(flags);
        }
    }

    public float measureText(String str) {
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        return measureText(str, 0, str.length());
    }

    public float measureText(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (charSequence.length() == 0 || i == i2) {
            return 0.0f;
        }
        if (charSequence instanceof String) {
            return measureText((String) charSequence, i, i2);
        }
        if ((charSequence instanceof SpannedString) || (charSequence instanceof SpannableString)) {
            return measureText(charSequence.toString(), i, i2);
        }
        if (charSequence instanceof GraphicsOperations) {
            return ((GraphicsOperations) charSequence).measureText(i, i2, this);
        }
        char[] cArrObtain = TemporaryBuffer.obtain(i3);
        TextUtils.getChars(charSequence, i, i2, cArrObtain, 0);
        float fMeasureText = measureText(cArrObtain, 0, i3);
        TemporaryBuffer.recycle(cArrObtain);
        return fMeasureText;
    }

    public int breakText(char[] cArr, int i, int i2, float f, float[] fArr) {
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (i < 0 || cArr.length - i < Math.abs(i2)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0;
        }
        if (!this.mHasCompatScaling) {
            return nBreakText(this.mNativePaint, cArr, i, i2, f, this.mBidiFlags, fArr);
        }
        float textSize = getTextSize();
        setTextSize(this.mCompatScaling * textSize);
        int iNBreakText = nBreakText(this.mNativePaint, cArr, i, i2, f * this.mCompatScaling, this.mBidiFlags, fArr);
        setTextSize(textSize);
        if (fArr != null) {
            fArr[0] = fArr[0] * this.mInvCompatScaling;
        }
        return iNBreakText;
    }

    public int breakText(CharSequence charSequence, int i, int i2, boolean z, float f, float[] fArr) {
        int iBreakText;
        if (charSequence == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i3 = i | i2;
        int i4 = i2 - i;
        if ((i3 | i4 | (charSequence.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (charSequence.length() == 0 || i == i2) {
            return 0;
        }
        if (i == 0 && (charSequence instanceof String) && i2 == charSequence.length()) {
            return breakText((String) charSequence, z, f, fArr);
        }
        char[] cArrObtain = TemporaryBuffer.obtain(i4);
        TextUtils.getChars(charSequence, i, i2, cArrObtain, 0);
        if (z) {
            iBreakText = breakText(cArrObtain, 0, i4, f, fArr);
        } else {
            iBreakText = breakText(cArrObtain, 0, -i4, f, fArr);
            cArrObtain = cArrObtain;
        }
        TemporaryBuffer.recycle(cArrObtain);
        return iBreakText;
    }

    public int breakText(String str, boolean z, float f, float[] fArr) {
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (str.length() == 0) {
            return 0;
        }
        if (!this.mHasCompatScaling) {
            return nBreakText(this.mNativePaint, str, z, f, this.mBidiFlags, fArr);
        }
        float textSize = getTextSize();
        setTextSize(this.mCompatScaling * textSize);
        int iNBreakText = nBreakText(this.mNativePaint, str, z, f * this.mCompatScaling, this.mBidiFlags, fArr);
        setTextSize(textSize);
        if (fArr != null) {
            fArr[0] = fArr[0] * this.mInvCompatScaling;
        }
        return iNBreakText;
    }

    public int getTextWidths(char[] cArr, int i, int i2, float[] fArr) {
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((i | i2) < 0 || i + i2 > cArr.length || i2 > fArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, fArr, 0);
                return i2;
            }
            float textSize = getTextSize();
            setTextSize(this.mCompatScaling * textSize);
            nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, fArr, 0);
            setTextSize(textSize);
            for (int i3 = 0; i3 < i2; i3++) {
                fArr[i3] = fArr[i3] * this.mInvCompatScaling;
            }
            return i2;
        } finally {
            setFlags(flags);
        }
    }

    public int getTextWidths(CharSequence charSequence, int i, int i2, float[] fArr) {
        if (charSequence == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 > fArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (charSequence.length() == 0 || i == i2) {
            return 0;
        }
        if (charSequence instanceof String) {
            return getTextWidths((String) charSequence, i, i2, fArr);
        }
        if ((charSequence instanceof SpannedString) || (charSequence instanceof SpannableString)) {
            return getTextWidths(charSequence.toString(), i, i2, fArr);
        }
        if (charSequence instanceof GraphicsOperations) {
            return ((GraphicsOperations) charSequence).getTextWidths(i, i2, fArr, this);
        }
        char[] cArrObtain = TemporaryBuffer.obtain(i3);
        TextUtils.getChars(charSequence, i, i2, cArrObtain, 0);
        int textWidths = getTextWidths(cArrObtain, 0, i3, fArr);
        TemporaryBuffer.recycle(cArrObtain);
        return textWidths;
    }

    public int getTextWidths(String str, int i, int i2, float[] fArr) {
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (str.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 > fArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (str.length() == 0 || i == i2) {
            return 0;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, fArr, 0);
                return i3;
            }
            float textSize = getTextSize();
            setTextSize(this.mCompatScaling * textSize);
            nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, fArr, 0);
            setTextSize(textSize);
            for (int i4 = 0; i4 < i3; i4++) {
                fArr[i4] = fArr[i4] * this.mInvCompatScaling;
            }
            return i3;
        } finally {
            setFlags(flags);
        }
    }

    public int getTextWidths(String str, float[] fArr) {
        return getTextWidths(str, 0, str.length(), fArr);
    }

    public float getTextRunAdvances(char[] cArr, int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        float[] fArr2;
        int i11;
        char[] cArr2;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        char[] cArr3;
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i18 = i3 + i4;
        if ((i | i2 | i3 | i4 | i5 | (i - i3) | (i4 - i2) | (i18 - (i + i2)) | (cArr.length - i18) | (fArr == null ? 0 : fArr.length - (i5 + i2))) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0.0f;
        }
        if (!this.mHasCompatScaling) {
            long j = this.mNativePaint;
            if (z) {
                i12 = 5;
                cArr3 = cArr;
                i14 = i2;
                i15 = i3;
                i16 = i4;
                i17 = i5;
                i13 = i;
            } else {
                i12 = 4;
                i13 = i;
                i14 = i2;
                i15 = i3;
                i16 = i4;
                i17 = i5;
                cArr3 = cArr;
            }
            return nGetTextAdvances(j, cArr3, i13, i14, i15, i16, i12, fArr, i17);
        }
        float textSize = getTextSize();
        setTextSize(this.mCompatScaling * textSize);
        long j2 = this.mNativePaint;
        if (z) {
            i6 = 5;
            cArr2 = cArr;
            i8 = i2;
            i9 = i3;
            i10 = i4;
            fArr2 = fArr;
            i11 = i5;
            i7 = i;
        } else {
            i6 = 4;
            i7 = i;
            i8 = i2;
            i9 = i3;
            i10 = i4;
            fArr2 = fArr;
            i11 = i5;
            cArr2 = cArr;
        }
        float fNGetTextAdvances = nGetTextAdvances(j2, cArr2, i7, i8, i9, i10, i6, fArr2, i11);
        setTextSize(textSize);
        if (fArr != null) {
            int i19 = i5 + i2;
            for (int i20 = i5; i20 < i19; i20++) {
                fArr[i20] = fArr[i20] * this.mInvCompatScaling;
            }
        }
        return fNGetTextAdvances * this.mInvCompatScaling;
    }

    public int getTextRunCursor(char[] cArr, int i, int i2, boolean z, int i3, int i4) {
        int i5 = i + i2;
        if ((i | i5 | i3 | (i5 - i) | (i3 - i) | (i5 - i3) | (cArr.length - i5) | i4) < 0 || i4 > 4) {
            throw new IndexOutOfBoundsException();
        }
        return nGetTextRunCursor(this.mNativePaint, cArr, i, i2, z ? 1 : 0, i3, i4);
    }

    public int getTextRunCursor(CharSequence charSequence, int i, int i2, boolean z, int i3, int i4) {
        if ((charSequence instanceof String) || (charSequence instanceof SpannedString) || (charSequence instanceof SpannableString)) {
            return getTextRunCursor(charSequence.toString(), i, i2, z, i3, i4);
        }
        if (charSequence instanceof GraphicsOperations) {
            return ((GraphicsOperations) charSequence).getTextRunCursor(i, i2, z, i3, i4, this);
        }
        int i5 = i2 - i;
        char[] cArrObtain = TemporaryBuffer.obtain(i5);
        TextUtils.getChars(charSequence, i, i2, cArrObtain, 0);
        int textRunCursor = getTextRunCursor(cArrObtain, 0, i5, z, i3 - i, i4);
        TemporaryBuffer.recycle(cArrObtain);
        if (textRunCursor == -1) {
            return -1;
        }
        return textRunCursor + i;
    }

    public int getTextRunCursor(String str, int i, int i2, boolean z, int i3, int i4) {
        if ((i | i2 | i3 | (i2 - i) | (i3 - i) | (i2 - i3) | (str.length() - i2) | i4) < 0 || i4 > 4) {
            throw new IndexOutOfBoundsException();
        }
        return nGetTextRunCursor(this.mNativePaint, str, i, i2, z ? 1 : 0, i3, i4);
    }

    public void getTextPath(char[] cArr, int i, int i2, float f, float f2, Path path) {
        if ((i | i2) < 0 || i + i2 > cArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        nGetTextPath(this.mNativePaint, this.mBidiFlags, cArr, i, i2, f, f2, path.mutateNI());
    }

    public void getTextPath(String str, int i, int i2, float f, float f2, Path path) {
        if ((i | i2 | (i2 - i) | (str.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        nGetTextPath(this.mNativePaint, this.mBidiFlags, str, i, i2, f, f2, path.mutateNI());
    }

    public void getTextBounds(String str, int i, int i2, Rect rect) {
        if ((i | i2 | (i2 - i) | (str.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (rect == null) {
            throw new NullPointerException("need bounds Rect");
        }
        nGetStringBounds(this.mNativePaint, str, i, i2, this.mBidiFlags, rect);
    }

    public void getTextBounds(CharSequence charSequence, int i, int i2, Rect rect) {
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (rect == null) {
            throw new NullPointerException("need bounds Rect");
        }
        char[] cArrObtain = TemporaryBuffer.obtain(i3);
        TextUtils.getChars(charSequence, i, i2, cArrObtain, 0);
        getTextBounds(cArrObtain, 0, i3, rect);
        TemporaryBuffer.recycle(cArrObtain);
    }

    public void getTextBounds(char[] cArr, int i, int i2, Rect rect) {
        if ((i | i2) < 0 || i + i2 > cArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (rect == null) {
            throw new NullPointerException("need bounds Rect");
        }
        nGetCharArrayBounds(this.mNativePaint, cArr, i, i2, this.mBidiFlags, rect);
    }

    public boolean hasGlyph(String str) {
        return nHasGlyph(this.mNativePaint, this.mBidiFlags, str);
    }

    public float getRunAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5) {
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((i3 | i | i5 | i2 | i4 | (i - i3) | (i5 - i) | (i2 - i5) | (i4 - i2) | (cArr.length - i4)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == i) {
            return 0.0f;
        }
        return nGetRunAdvance(this.mNativePaint, cArr, i, i2, i3, i4, z, i5);
    }

    public float getRunAdvance(CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, int i5) {
        if (charSequence == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i6 = i3 | i | i5 | i2 | i4;
        int i7 = i - i3;
        if ((i6 | i7 | (i5 - i) | (i2 - i5) | (i4 - i2) | (charSequence.length() - i4)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == i) {
            return 0.0f;
        }
        int i8 = i4 - i3;
        char[] cArrObtain = TemporaryBuffer.obtain(i8);
        TextUtils.getChars(charSequence, i3, i4, cArrObtain, 0);
        float runAdvance = getRunAdvance(cArrObtain, i7, i2 - i3, 0, i8, z, i5 - i3);
        TemporaryBuffer.recycle(cArrObtain);
        return runAdvance;
    }

    public float getRunCharacterAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6) {
        return getRunCharacterAdvance(cArr, i, i2, i3, i4, z, i5, fArr, i6, (RectF) null, (RunInfo) null);
    }

    public float getRunCharacterAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF, RunInfo runInfo) {
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (i3 < 0 || i4 > cArr.length) {
            throw new IndexOutOfBoundsException("Invalid Context Range: " + i3 + ", " + i4 + " must be in 0, " + cArr.length);
        }
        if (i < i3 || i4 < i2) {
            throw new IndexOutOfBoundsException("Invalid start/end range: " + i + ", " + i2 + " must be in " + i3 + ", " + i4);
        }
        if (i5 < i || i2 < i5) {
            throw new IndexOutOfBoundsException("Invalid offset position: " + i5 + " must be in " + i + ", " + i2);
        }
        if (fArr == null || fArr.length >= (i6 - i) + i2) {
            if (i2 != i) {
                return nGetRunCharacterAdvance(this.mNativePaint, cArr, i, i2, i3, i4, z, i5, fArr, i6, rectF, runInfo);
            }
            if (runInfo == null) {
                return 0.0f;
            }
            runInfo.setClusterCount(0);
            return 0.0f;
        }
        throw new IndexOutOfBoundsException("Given array doesn't have enough space to receive the result, advances.length: " + fArr.length + " advanceIndex: " + i6 + " needed space: " + (i5 - i));
    }

    public float getRunCharacterAdvance(CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6) {
        return getRunCharacterAdvance(charSequence, i, i2, i3, i4, z, i5, fArr, i6, (RectF) null, (RunInfo) null);
    }

    public float getRunCharacterAdvance(CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, RectF rectF, RunInfo runInfo) {
        if (charSequence == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if (i3 < 0 || i4 > charSequence.length()) {
            throw new IndexOutOfBoundsException("Invalid Context Range: " + i3 + ", " + i4 + " must be in 0, " + charSequence.length());
        }
        if (i < i3 || i4 < i2) {
            throw new IndexOutOfBoundsException("Invalid start/end range: " + i + ", " + i2 + " must be in " + i3 + ", " + i4);
        }
        if (i5 < i || i2 < i5) {
            throw new IndexOutOfBoundsException("Invalid offset position: " + i5 + " must be in " + i + ", " + i2);
        }
        if (fArr != null && fArr.length < (i6 - i) + i2) {
            throw new IndexOutOfBoundsException("Given array doesn't have enough space to receive the result, advances.length: " + fArr.length + " advanceIndex: " + i6 + " needed space: " + (i5 - i));
        }
        if (i2 == i) {
            return 0.0f;
        }
        int i7 = i4 - i3;
        char[] cArrObtain = TemporaryBuffer.obtain(i7);
        TextUtils.getChars(charSequence, i3, i4, cArrObtain, 0);
        float runCharacterAdvance = getRunCharacterAdvance(cArrObtain, i - i3, i2 - i3, 0, i7, z, i5 - i3, fArr, i6, rectF, runInfo);
        TemporaryBuffer.recycle(cArrObtain);
        return runCharacterAdvance;
    }

    public int getOffsetForAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, float f) {
        if (cArr == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        if ((i3 | i | i2 | i4 | (i - i3) | (i2 - i) | (i4 - i2) | (cArr.length - i4)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        return nGetOffsetForAdvance(this.mNativePaint, cArr, i, i2, i3, i4, z, f);
    }

    public int getOffsetForAdvance(CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, float f) {
        if (charSequence == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        int i5 = i - i3;
        if (((i2 - i) | i3 | i | i2 | i4 | i5 | (i4 - i2) | (charSequence.length() - i4)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i6 = i4 - i3;
        char[] cArrObtain = TemporaryBuffer.obtain(i6);
        TextUtils.getChars(charSequence, i3, i4, cArrObtain, 0);
        int offsetForAdvance = getOffsetForAdvance(cArrObtain, i5, i2 - i3, 0, i6, z, f) + i3;
        TemporaryBuffer.recycle(cArrObtain);
        return offsetForAdvance;
    }

    public boolean equalsForTextMeasurement(Paint paint) {
        return nEqualsForTextMeasurement(this.mNativePaint, paint.mNativePaint);
    }

    public enum MyanmarEncoding {
        ME_UNICODE(0),
        ME_ZAWGYI(1),
        ME_AUTO(2);

        final int nativeInt;

        MyanmarEncoding(int i) {
            this.nativeInt = i;
        }
    }

    public MyanmarEncoding getMyanmarEncoding() {
        return this.mMyanmarEncoding;
    }

    public void setMyanmarEncoding(MyanmarEncoding myanmarEncoding) {
        this.mUseCustomMyanmarEncoding = true;
        if (this.mMyanmarEncoding != myanmarEncoding) {
            this.mMyanmarEncoding = myanmarEncoding;
            nSetMyanmarEncoding(this.mNativePaint, myanmarEncoding.nativeInt);
        }
    }

    public void setMyanmarEncoding(Locale locale) {
        Locale locale2;
        if (this.mUseCustomMyanmarEncoding || locale == null) {
            return;
        }
        MyanmarEncoding myanmarEncoding = MyanmarEncoding.ME_UNICODE;
        String country = locale.getCountry();
        if (LocaleList.getDefault().size() > 1 && ((country == null || "".equals(country)) && (locale2 = LocaleList.getDefault().get(1)) != null)) {
            country = locale2.getCountry();
        }
        if ("ZG".equals(country)) {
            myanmarEncoding = MyanmarEncoding.ME_ZAWGYI;
        }
        if (this.mMyanmarEncoding != myanmarEncoding) {
            this.mMyanmarEncoding = myanmarEncoding;
            nSetMyanmarEncoding(this.mNativePaint, myanmarEncoding.nativeInt);
        }
    }

    public float getHCTStrokeWidth() {
        return (getTextSize() <= 15.0f ? 1 : 2) + (getTextSize() * 0.04f);
    }
}
