package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
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
        BlendMode fromValue = mode != null ? BlendMode.fromValue(mode.nativeInt) : null;
        if (fromValue == null) {
            fromValue = DEFAULT_BLEND_MODE;
        }
        setTintBlendMode(fromValue);
        this.mSetTintModeInvoked = false;
    }

    public void setTintBlendMode(BlendMode blendMode) {
        if (this.mSetBlendModeInvoked) {
            return;
        }
        this.mSetBlendModeInvoked = true;
        PorterDuff.Mode blendModeToPorterDuffMode = BlendMode.blendModeToPorterDuffMode(blendMode);
        if (blendModeToPorterDuffMode == null) {
            blendModeToPorterDuffMode = DEFAULT_TINT_MODE;
        }
        setTintMode(blendModeToPorterDuffMode);
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

    /* JADX WARN: Code restructure failed: missing block: B:46:0x001b, code lost:
    
        if (r12.endsWith(".spr") == false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable createFromResourceStream(android.content.res.Resources r9, android.util.TypedValue r10, java.io.InputStream r11, java.lang.String r12, android.graphics.BitmapFactory.Options r13) {
        /*
            r1 = 0
            if (r11 != 0) goto L4
            return r1
        L4:
            r2 = 0
            if (r12 == 0) goto L1d
            boolean r0 = r12.isEmpty()     // Catch: java.lang.Exception -> L77
            if (r0 != 0) goto L1d
            java.lang.String r0 = ".bmp"
            boolean r0 = r12.endsWith(r0)     // Catch: java.lang.Exception -> L77
            if (r0 != 0) goto L1d
            java.lang.String r0 = ".spr"
            boolean r0 = r12.endsWith(r0)     // Catch: java.lang.Exception -> L77
            if (r0 == 0) goto L7b
        L1d:
            boolean r0 = r11.markSupported()     // Catch: java.lang.Exception -> L77
            if (r0 == 0) goto L7b
            r0 = 3
            byte[] r3 = new byte[r0]     // Catch: java.lang.Exception -> L77
            r11.read(r3, r2, r0)     // Catch: java.lang.Exception -> L77
            r11.reset()     // Catch: java.lang.Exception -> L77
            r4 = r3[r2]     // Catch: java.lang.Exception -> L77
            r5 = 83
            if (r4 != r5) goto L7b
            r4 = 1
            r5 = r3[r4]     // Catch: java.lang.Exception -> L77
            r6 = 80
            if (r5 != r6) goto L7b
            r5 = 2
            r3 = r3[r5]     // Catch: java.lang.Exception -> L77
            r6 = 82
            if (r3 != r6) goto L7b
            java.lang.Class<?> r3 = android.graphics.drawable.Drawable.SprClass     // Catch: java.lang.Exception -> L77
            if (r3 != 0) goto L4c
            java.lang.String r3 = "com.samsung.android.graphics.spr.SemPathRenderingDrawable"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch: java.lang.Exception -> L77
            android.graphics.drawable.Drawable.SprClass = r3     // Catch: java.lang.Exception -> L77
        L4c:
            java.lang.reflect.Method r3 = android.graphics.drawable.Drawable.SprCreateFromStream     // Catch: java.lang.Exception -> L77
            if (r3 != 0) goto L68
            java.lang.Class<?> r3 = android.graphics.drawable.Drawable.SprClass     // Catch: java.lang.Exception -> L77
            java.lang.String r6 = "createFromStream"
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch: java.lang.Exception -> L77
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r0[r2] = r7     // Catch: java.lang.Exception -> L77
            java.lang.Class<java.io.InputStream> r7 = java.io.InputStream.class
            r0[r4] = r7     // Catch: java.lang.Exception -> L77
            java.lang.Class<android.content.res.Resources> r4 = android.content.res.Resources.class
            r0[r5] = r4     // Catch: java.lang.Exception -> L77
            java.lang.reflect.Method r0 = r3.getMethod(r6, r0)     // Catch: java.lang.Exception -> L77
            android.graphics.drawable.Drawable.SprCreateFromStream = r0     // Catch: java.lang.Exception -> L77
        L68:
            java.lang.reflect.Method r0 = android.graphics.drawable.Drawable.SprCreateFromStream     // Catch: java.lang.Exception -> L77
            java.lang.Class<?> r3 = android.graphics.drawable.Drawable.SprClass     // Catch: java.lang.Exception -> L77
            java.lang.Object[] r4 = new java.lang.Object[]{r12, r11, r9}     // Catch: java.lang.Exception -> L77
            java.lang.Object r0 = r0.invoke(r3, r4)     // Catch: java.lang.Exception -> L77
            android.graphics.drawable.Drawable r0 = (android.graphics.drawable.Drawable) r0     // Catch: java.lang.Exception -> L77
            return r0
        L77:
            r0 = move-exception
            r0.printStackTrace()
        L7b:
            if (r13 != 0) goto L82
            android.graphics.drawable.Drawable r9 = getBitmapDrawable(r9, r10, r11)
            return r9
        L82:
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            int r2 = resolveDensity(r9, r2)
            r13.inScreenDensity = r2
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeResourceStream(r9, r10, r11, r0, r13)
            if (r4 == 0) goto Lb4
            byte[] r10 = r4.getNinePatchChunk()
            if (r10 == 0) goto La3
            boolean r11 = android.graphics.NinePatch.isNinePatchChunk(r10)
            if (r11 != 0) goto La0
            goto La3
        La0:
            r5 = r10
            r6 = r0
            goto La5
        La3:
            r5 = r1
            r6 = r5
        La5:
            android.graphics.Rect r7 = new android.graphics.Rect
            r7.<init>()
            r4.getOpticalInsets(r7)
            r3 = r9
            r8 = r12
            android.graphics.drawable.Drawable r9 = drawableFromBitmap(r3, r4, r5, r6, r7, r8)
            return r9
        Lb4:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.Drawable.createFromResourceStream(android.content.res.Resources, android.util.TypedValue, java.io.InputStream, java.lang.String, android.graphics.BitmapFactory$Options):android.graphics.drawable.Drawable");
    }

    private static Drawable getBitmapDrawable(Resources resources, TypedValue typedValue, InputStream inputStream) {
        ImageDecoder.Source createSource;
        int i;
        try {
            if (typedValue != null) {
                if (typedValue.density == 0) {
                    i = 160;
                } else {
                    i = typedValue.density != 65535 ? typedValue.density : 0;
                }
                createSource = ImageDecoder.createSource(resources, inputStream, i);
            } else {
                createSource = ImageDecoder.createSource(resources, inputStream);
            }
            return ImageDecoder.decodeDrawable(createSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda0
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
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        Drawable createFromXmlInnerForDensity = createFromXmlInnerForDensity(resources, xmlPullParser, asAttributeSet, i, theme);
        if (createFromXmlInnerForDensity != null) {
            return createFromXmlInnerForDensity;
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
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.Drawable);
        this.mVisible = obtainAttributes.getBoolean(0, this.mVisible);
        obtainAttributes.recycle();
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
        int round = Math.round(f);
        return round != 0 ? round : i > 0 ? 1 : -1;
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
