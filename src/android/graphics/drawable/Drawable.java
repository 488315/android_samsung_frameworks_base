package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.NinePatch;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class Drawable {
    private int mLayoutDirection;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    static final BlendMode DEFAULT_BLEND_MODE = BlendMode.SRC_IN;
    private static Class<?> SprClass = null;
    private static Method SprCreateFromStream = null;
    private int[] mStateSet = StateSet.WILD_CARD;
    private int mLevel = 0;
    private int mChangingConfigurations = 0;
    private Rect mBounds = ZERO_BOUNDS_RECT;
    private WeakReference<Callback> mCallback = null;
    private boolean mVisible = true;
    protected int mSrcDensityOverride = 0;
    private boolean mSetBlendModeInvoked = false;
    private boolean mSetTintModeInvoked = false;

    public interface Callback {
        void invalidateDrawable(Drawable drawable);

        void scheduleDrawable(Drawable drawable, Runnable runnable, long j);

        void unscheduleDrawable(Drawable drawable, Runnable runnable);
    }

    public static int resolveOpacity(int i, int i2) {
        if (i == i2) {
            return i;
        }
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int i3 = -3;
        if (i != -3 && i2 != -3) {
            i3 = -2;
            if (i != -2 && i2 != -2) {
                return -1;
            }
        }
        return i3;
    }

    static float scaleFromDensity(float f, int i, int i2) {
        return (f * i2) / i;
    }

    public void applyTheme(Resources.Theme theme) {
    }

    public boolean canApplyTheme() {
        return false;
    }

    public void clearMutated() {
    }

    public abstract void draw(Canvas canvas);

    public int getAlpha() {
        return 255;
    }

    public ColorFilter getColorFilter() {
        return null;
    }

    public ConstantState getConstantState() {
        return null;
    }

    public Drawable getCurrent() {
        return this;
    }

    public int getIntrinsicHeight() {
        return -1;
    }

    public int getIntrinsicWidth() {
        return -1;
    }

    @Deprecated
    public abstract int getOpacity();

    public Region getTransparentRegion() {
        return null;
    }

    public boolean hasFocusStateSpecified() {
        return false;
    }

    public boolean isAutoMirrored() {
        return false;
    }

    public boolean isFilterBitmap() {
        return false;
    }

    public boolean isProjected() {
        return false;
    }

    public boolean isStateful() {
        return false;
    }

    public void jumpToCurrentState() {
    }

    public Drawable mutate() {
        return this;
    }

    protected void onBoundsChange(Rect rect) {
    }

    public boolean onLayoutDirectionChanged(int i) {
        return false;
    }

    protected boolean onLevelChange(int i) {
        return false;
    }

    protected boolean onStateChange(int[] iArr) {
        return false;
    }

    public abstract void setAlpha(int i);

    public void setAutoMirrored(boolean z) {
    }

    public abstract void setColorFilter(ColorFilter colorFilter);

    @Deprecated
    public void setDither(boolean z) {
    }

    public void setFilterBitmap(boolean z) {
    }

    public void setHotspot(float f, float f2) {
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
    }

    public void setTintList(ColorStateList colorStateList) {
    }

    public void setXfermode(Xfermode xfermode) {
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        Rect rect = this.mBounds;
        if (rect == ZERO_BOUNDS_RECT) {
            rect = new Rect();
            this.mBounds = rect;
        }
        if (rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4) {
            return;
        }
        if (!rect.isEmpty()) {
            invalidateSelf();
        }
        this.mBounds.set(i, i2, i3, i4);
        onBoundsChange(this.mBounds);
    }

    public void setBounds(Rect rect) {
        setBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public final void copyBounds(Rect rect) {
        rect.set(this.mBounds);
    }

    public final Rect copyBounds() {
        return new Rect(this.mBounds);
    }

    public final Rect getBounds() {
        if (this.mBounds == ZERO_BOUNDS_RECT) {
            this.mBounds = new Rect();
        }
        return this.mBounds;
    }

    public Rect getDirtyBounds() {
        return getBounds();
    }

    public void setChangingConfigurations(int i) {
        this.mChangingConfigurations = i;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public final void setCallback(Callback callback) {
        this.mCallback = callback != null ? new WeakReference<>(callback) : null;
    }

    public Callback getCallback() {
        WeakReference<Callback> weakReference = this.mCallback;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public void invalidateSelf() {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleSelf(Runnable runnable, long j) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public void unscheduleSelf(Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public int getLayoutDirection() {
        return this.mLayoutDirection;
    }

    public final boolean setLayoutDirection(int i) {
        if (this.mLayoutDirection == i) {
            return false;
        }
        this.mLayoutDirection = i;
        return onLayoutDirectionChanged(i);
    }

    @Deprecated
    public void setColorFilter(int i, PorterDuff.Mode mode) {
        if (getColorFilter() instanceof PorterDuffColorFilter) {
            PorterDuffColorFilter porterDuffColorFilter = (PorterDuffColorFilter) getColorFilter();
            if (porterDuffColorFilter.getColor() == i && porterDuffColorFilter.getMode() == mode) {
                return;
            }
        }
        setColorFilter(new PorterDuffColorFilter(i, mode));
    }

    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public void setTintMode(PorterDuff.Mode mode) {
        if (this.mSetTintModeInvoked) {
            return;
        }
        this.mSetTintModeInvoked = true;
        BlendMode blendModeFromValue = mode != null ? BlendMode.fromValue(mode.nativeInt) : null;
        if (blendModeFromValue == null) {
            blendModeFromValue = DEFAULT_BLEND_MODE;
        }
        setTintBlendMode(blendModeFromValue);
        this.mSetTintModeInvoked = false;
    }

    public void setTintBlendMode(BlendMode blendMode) {
        if (this.mSetBlendModeInvoked) {
            return;
        }
        this.mSetBlendModeInvoked = true;
        PorterDuff.Mode modeBlendModeToPorterDuffMode = BlendMode.blendModeToPorterDuffMode(blendMode);
        if (modeBlendModeToPorterDuffMode == null) {
            modeBlendModeToPorterDuffMode = DEFAULT_TINT_MODE;
        }
        setTintMode(modeBlendModeToPorterDuffMode);
        this.mSetBlendModeInvoked = false;
    }

    public void clearColorFilter() {
        setColorFilter(null);
    }

    public void getHotspotBounds(Rect rect) {
        rect.set(getBounds());
    }

    public boolean setState(int[] iArr) {
        if (Arrays.equals(this.mStateSet, iArr)) {
            return false;
        }
        this.mStateSet = iArr;
        return onStateChange(iArr);
    }

    public int[] getState() {
        return this.mStateSet;
    }

    public final boolean setLevel(int i) {
        if (this.mLevel == i) {
            return false;
        }
        this.mLevel = i;
        return onLevelChange(i);
    }

    public final int getLevel() {
        return this.mLevel;
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean z3 = this.mVisible != z;
        if (z3) {
            this.mVisible = z;
            invalidateSelf();
        }
        return z3;
    }

    public final boolean isVisible() {
        return this.mVisible;
    }

    public int getMinimumWidth() {
        int intrinsicWidth = getIntrinsicWidth();
        if (intrinsicWidth > 0) {
            return intrinsicWidth;
        }
        return 0;
    }

    public int getMinimumHeight() {
        int intrinsicHeight = getIntrinsicHeight();
        if (intrinsicHeight > 0) {
            return intrinsicHeight;
        }
        return 0;
    }

    public boolean getPadding(Rect rect) {
        rect.set(0, 0, 0, 0);
        return false;
    }

    public Insets getOpticalInsets() {
        return Insets.NONE;
    }

    public void getOutline(Outline outline) {
        outline.setRect(getBounds());
        outline.setAlpha(0.0f);
    }

    public static Drawable createFromStream(InputStream inputStream, String str) {
        Trace.traceBegin(8192L, str != null ? str : "Unknown drawable");
        try {
            return createFromResourceStream(null, null, inputStream, str);
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable createFromResourceStream(Resources resources, TypedValue typedValue, InputStream inputStream, String str) {
        Trace.traceBegin(8192L, str != null ? str : "Unknown drawable");
        try {
            return createFromResourceStream(resources, typedValue, inputStream, str, null);
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001d A[Catch: Exception -> 0x0077, TryCatch #0 {Exception -> 0x0077, blocks: (B:7:0x0007, B:9:0x000d, B:11:0x0015, B:13:0x001d, B:15:0x0023, B:17:0x0032, B:19:0x0039, B:21:0x0040, B:23:0x0044, B:24:0x004c, B:26:0x0050, B:27:0x0068), top: B:46:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Drawable createFromResourceStream(Resources resources, TypedValue typedValue, InputStream inputStream, String str, BitmapFactory.Options options) throws IOException {
        byte[] bArr;
        Rect rect;
        if (inputStream == null) {
            return null;
        }
        if (str != null) {
            try {
                if (str.isEmpty() || str.endsWith(".bmp") || str.endsWith(".spr")) {
                    if (inputStream.markSupported()) {
                        byte[] bArr2 = new byte[3];
                        inputStream.read(bArr2, 0, 3);
                        inputStream.reset();
                        if (bArr2[0] == 83 && bArr2[1] == 80 && bArr2[2] == 82) {
                            if (SprClass == null) {
                                SprClass = Class.forName("com.samsung.android.graphics.spr.SemPathRenderingDrawable");
                            }
                            if (SprCreateFromStream == null) {
                                SprCreateFromStream = SprClass.getMethod("createFromStream", String.class, InputStream.class, Resources.class);
                            }
                            return (Drawable) SprCreateFromStream.invoke(SprClass, str, inputStream, resources);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (options == null) {
            return getBitmapDrawable(resources, typedValue, inputStream);
        }
        Rect rect2 = new Rect();
        options.inScreenDensity = resolveDensity(resources, 0);
        Bitmap bitmapDecodeResourceStream = BitmapFactory.decodeResourceStream(resources, typedValue, inputStream, rect2, options);
        if (bitmapDecodeResourceStream == null) {
            return null;
        }
        byte[] ninePatchChunk = bitmapDecodeResourceStream.getNinePatchChunk();
        if (ninePatchChunk == null || !NinePatch.isNinePatchChunk(ninePatchChunk)) {
            bArr = null;
            rect = null;
        } else {
            bArr = ninePatchChunk;
            rect = rect2;
        }
        Rect rect3 = new Rect();
        bitmapDecodeResourceStream.getOpticalInsets(rect3);
        return drawableFromBitmap(resources, bitmapDecodeResourceStream, bArr, rect, rect3, str);
    }

    private static Drawable getBitmapDrawable(Resources resources, TypedValue typedValue, InputStream inputStream) {
        ImageDecoder.Source sourceCreateSource;
        int i;
        try {
            if (typedValue != null) {
                if (typedValue.density == 0) {
                    i = 160;
                } else {
                    i = typedValue.density != 65535 ? typedValue.density : 0;
                }
                sourceCreateSource = ImageDecoder.createSource(resources, inputStream, i);
            } else {
                sourceCreateSource = ImageDecoder.createSource(resources, inputStream);
            }
            return ImageDecoder.decodeDrawable(sourceCreateSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    Drawable.lambda$getBitmapDrawable$1(imageDecoder, imageInfo, source);
                }
            });
        } catch (IOException e) {
            Log.e("Drawable", "Unable to decode stream: " + e);
            return null;
        }
    }

    static /* synthetic */ void lambda$getBitmapDrawable$1(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
        imageDecoder.setOnPartialImageListener(new ImageDecoder.OnPartialImageListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda1
            @Override // android.graphics.ImageDecoder.OnPartialImageListener
            public final boolean onPartialImage(ImageDecoder.DecodeException decodeException) {
                return Drawable.lambda$getBitmapDrawable$0(decodeException);
            }
        });
    }

    static /* synthetic */ boolean lambda$getBitmapDrawable$0(ImageDecoder.DecodeException decodeException) {
        return decodeException.getError() == 2;
    }

    public static Drawable createFromXml(Resources resources, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return createFromXml(resources, xmlPullParser, null);
    }

    public static Drawable createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        return createFromXmlForDensity(resources, xmlPullParser, 0, theme);
    }

    public static Drawable createFromXmlForDensity(Resources resources, XmlPullParser xmlPullParser, int i, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        Drawable drawableCreateFromXmlInnerForDensity = createFromXmlInnerForDensity(resources, xmlPullParser, attributeSetAsAttributeSet, i, theme);
        if (drawableCreateFromXmlInnerForDensity != null) {
            return drawableCreateFromXmlInnerForDensity;
        }
        throw new RuntimeException("Unknown initial tag: " + xmlPullParser.getName());
    }

    public static Drawable createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        return createFromXmlInner(resources, xmlPullParser, attributeSet, null);
    }

    public static Drawable createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        return createFromXmlInnerForDensity(resources, xmlPullParser, attributeSet, 0, theme);
    }

    static Drawable createFromXmlInnerForDensity(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, int i, Resources.Theme theme) throws XmlPullParserException, IOException {
        return resources.getDrawableInflater().inflateFromXmlForDensity(xmlPullParser.getName(), xmlPullParser, attributeSet, i, theme);
    }

    public static Drawable createFromPath(String str) {
        if (str == null) {
            return null;
        }
        Trace.traceBegin(8192L, str);
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                Drawable bitmapDrawable = getBitmapDrawable(null, null, fileInputStream);
                fileInputStream.close();
                return bitmapDrawable;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException unused) {
            return null;
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.Drawable);
        this.mVisible = typedArrayObtainAttributes.getBoolean(0, this.mVisible);
        typedArrayObtainAttributes.recycle();
    }

    void inflateWithAttributes(Resources resources, XmlPullParser xmlPullParser, TypedArray typedArray, int i) throws XmlPullParserException, IOException {
        this.mVisible = typedArray.getBoolean(i, this.mVisible);
    }

    final void setSrcDensityOverride(int i) {
        this.mSrcDensityOverride = i;
    }

    public static abstract class ConstantState {
        public boolean canApplyTheme() {
            return false;
        }

        public abstract int getChangingConfigurations();

        public abstract Drawable newDrawable();

        public Drawable newDrawable(Resources resources) {
            return newDrawable();
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return newDrawable(resources);
        }
    }

    private static Drawable drawableFromBitmap(Resources resources, Bitmap bitmap, byte[] bArr, Rect rect, Rect rect2, String str) {
        if (bArr != null) {
            return new NinePatchDrawable(resources, bitmap, bArr, rect, rect2, str);
        }
        return new BitmapDrawable(resources, bitmap);
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        return (porterDuffColorFilter != null && porterDuffColorFilter.getColor() == colorForState && porterDuffColorFilter.getMode() == mode) ? porterDuffColorFilter : new PorterDuffColorFilter(colorForState, mode);
    }

    BlendModeColorFilter updateBlendModeFilter(BlendModeColorFilter blendModeColorFilter, ColorStateList colorStateList, BlendMode blendMode) {
        if (colorStateList == null || blendMode == null) {
            return null;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        return (blendModeColorFilter != null && blendModeColorFilter.getColor() == colorForState && blendModeColorFilter.getMode() == blendMode) ? blendModeColorFilter : new BlendModeColorFilter(colorForState, blendMode);
    }

    protected static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, iArr);
        }
        return theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    static int scaleFromDensity(int i, int i2, int i3, boolean z) {
        if (i == 0 || i2 == i3) {
            return i;
        }
        float f = (i3 * i) / i2;
        if (!z) {
            return (int) f;
        }
        int iRound = Math.round(f);
        return iRound != 0 ? iRound : i > 0 ? 1 : -1;
    }

    static int resolveDensity(Resources resources, int i) {
        if (resources != null) {
            i = resources.getDisplayMetrics().densityDpi;
        }
        if (i == 0) {
            return 160;
        }
        return i;
    }

    static void rethrowAsRuntimeException(Exception exc) throws RuntimeException {
        RuntimeException runtimeException = new RuntimeException(exc);
        runtimeException.setStackTrace(new StackTraceElement[0]);
        throw runtimeException;
    }

    public static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    public static BlendMode parseBlendMode(int i, BlendMode blendMode) {
        if (i == 3) {
            return BlendMode.SRC_OVER;
        }
        if (i == 5) {
            return BlendMode.SRC_IN;
        }
        if (i == 9) {
            return BlendMode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return BlendMode.MODULATE;
            case 15:
                return BlendMode.SCREEN;
            case 16:
                return BlendMode.PLUS;
            default:
                return blendMode;
        }
    }
}
