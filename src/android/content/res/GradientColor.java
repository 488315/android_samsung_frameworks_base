package android.content.res;

import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class GradientColor extends ComplexColor {
    private static final boolean DBG_GRADIENT = false;
    private static final String TAG = "GradientColor";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private int mCenterColor;
    private float mCenterX;
    private float mCenterY;
    private int mChangingConfigurations;
    private int mDefaultColor;
    private int mEndColor;
    private float mEndX;
    private float mEndY;
    private GradientColorFactory mFactory;
    private float mGradientRadius;
    private int mGradientType;
    private boolean mHasCenterColor;
    private int[] mItemColors;
    private float[] mItemOffsets;
    private int[][] mItemsThemeAttrs;
    private Shader mShader;
    private int mStartColor;
    private float mStartX;
    private float mStartY;
    private int[] mThemeAttrs;
    private int mTileMode;

    @Retention(RetentionPolicy.SOURCE)
    private @interface GradientTileMode {
    }

    private GradientColor() {
        this.mShader = null;
        this.mGradientType = 0;
        this.mCenterX = 0.0f;
        this.mCenterY = 0.0f;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        this.mStartColor = 0;
        this.mCenterColor = 0;
        this.mEndColor = 0;
        this.mHasCenterColor = false;
        this.mTileMode = 0;
        this.mGradientRadius = 0.0f;
    }

    private GradientColor(GradientColor gradientColor) {
        this.mShader = null;
        this.mGradientType = 0;
        this.mCenterX = 0.0f;
        this.mCenterY = 0.0f;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        this.mStartColor = 0;
        this.mCenterColor = 0;
        this.mEndColor = 0;
        this.mHasCenterColor = false;
        this.mTileMode = 0;
        this.mGradientRadius = 0.0f;
        if (gradientColor != null) {
            this.mChangingConfigurations = gradientColor.mChangingConfigurations;
            this.mDefaultColor = gradientColor.mDefaultColor;
            this.mShader = gradientColor.mShader;
            this.mGradientType = gradientColor.mGradientType;
            this.mCenterX = gradientColor.mCenterX;
            this.mCenterY = gradientColor.mCenterY;
            this.mStartX = gradientColor.mStartX;
            this.mStartY = gradientColor.mStartY;
            this.mEndX = gradientColor.mEndX;
            this.mEndY = gradientColor.mEndY;
            this.mStartColor = gradientColor.mStartColor;
            this.mCenterColor = gradientColor.mCenterColor;
            this.mEndColor = gradientColor.mEndColor;
            this.mHasCenterColor = gradientColor.mHasCenterColor;
            this.mGradientRadius = gradientColor.mGradientRadius;
            this.mTileMode = gradientColor.mTileMode;
            int[] iArr = gradientColor.mItemColors;
            if (iArr != null) {
                this.mItemColors = (int[]) iArr.clone();
            }
            float[] fArr = gradientColor.mItemOffsets;
            if (fArr != null) {
                this.mItemOffsets = (float[]) fArr.clone();
            }
            int[] iArr2 = gradientColor.mThemeAttrs;
            if (iArr2 != null) {
                this.mThemeAttrs = (int[]) iArr2.clone();
            }
            int[][] iArr3 = gradientColor.mItemsThemeAttrs;
            if (iArr3 != null) {
                this.mItemsThemeAttrs = (int[][]) iArr3.clone();
            }
        }
    }

    private static Shader.TileMode parseTileMode(int i) {
        if (i == 0) {
            return Shader.TileMode.CLAMP;
        }
        if (i == 1) {
            return Shader.TileMode.REPEAT;
        }
        if (i == 2) {
            return Shader.TileMode.MIRROR;
        }
        return Shader.TileMode.CLAMP;
    }

    private void updateRootElementState(TypedArray typedArray) {
        this.mThemeAttrs = typedArray.extractThemeAttrs();
        this.mStartX = typedArray.getFloat(8, this.mStartX);
        this.mStartY = typedArray.getFloat(9, this.mStartY);
        this.mEndX = typedArray.getFloat(10, this.mEndX);
        this.mEndY = typedArray.getFloat(11, this.mEndY);
        this.mCenterX = typedArray.getFloat(3, this.mCenterX);
        this.mCenterY = typedArray.getFloat(4, this.mCenterY);
        this.mGradientType = typedArray.getInt(2, this.mGradientType);
        this.mStartColor = typedArray.getColor(0, this.mStartColor);
        this.mHasCenterColor |= typedArray.hasValue(7);
        this.mCenterColor = typedArray.getColor(7, this.mCenterColor);
        this.mEndColor = typedArray.getColor(1, this.mEndColor);
        this.mTileMode = typedArray.getInt(6, this.mTileMode);
        this.mGradientRadius = typedArray.getFloat(5, this.mGradientRadius);
    }

    private void validateXmlContent() throws XmlPullParserException {
        if (this.mGradientRadius <= 0.0f && this.mGradientType == 1) {
            throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }
    }

    public Shader getShader() {
        return this.mShader;
    }

    public static GradientColor createFromXml(Resources resources, XmlResourceParser xmlResourceParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(resources, xmlResourceParser, asAttributeSet, theme);
    }

    static GradientColor createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = xmlPullParser.getName();
        if (!name.equals("gradient")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid gradient color tag " + name);
        }
        GradientColor gradientColor = new GradientColor();
        gradientColor.inflate(resources, xmlPullParser, attributeSet, theme);
        return gradientColor;
    }

    private void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = Resources.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientColor);
        updateRootElementState(obtainAttributes);
        this.mChangingConfigurations |= obtainAttributes.getChangingConfigurations();
        obtainAttributes.recycle();
        validateXmlContent();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        onColorsChange();
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0097, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException(r19.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void inflateChildElements(android.content.res.Resources r18, org.xmlpull.v1.XmlPullParser r19, android.util.AttributeSet r20, android.content.res.Resources.Theme r21) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            int r1 = r19.getDepth()
            r2 = 1
            int r1 = r1 + r2
            r3 = 20
            float[] r4 = new float[r3]
            int[] r5 = new int[r3]
            int[][] r3 = new int[r3][]
            r6 = 0
            r7 = r6
            r8 = r7
        L13:
            int r9 = r19.next()
            if (r9 == r2) goto La1
            int r10 = r19.getDepth()
            if (r10 >= r1) goto L22
            r11 = 3
            if (r9 == r11) goto La1
        L22:
            r11 = 2
            if (r9 == r11) goto L27
            goto L98
        L27:
            if (r10 > r1) goto L98
            java.lang.String r9 = r19.getName()
            java.lang.String r10 = "item"
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L36
            goto L98
        L36:
            int[] r9 = com.android.internal.R.styleable.GradientColorItem
            r10 = r18
            r11 = r20
            r12 = r21
            android.content.res.TypedArray r9 = android.content.res.Resources.obtainAttributes(r10, r12, r11, r9)
            boolean r13 = r9.hasValue(r6)
            boolean r14 = r9.hasValue(r2)
            if (r13 == 0) goto L7d
            if (r14 == 0) goto L7d
            int[] r13 = r9.extractThemeAttrs()
            int r14 = r9.getColor(r6, r6)
            r15 = 0
            float r15 = r9.getFloat(r2, r15)
            int r2 = r0.mChangingConfigurations
            int r16 = r9.getChangingConfigurations()
            r2 = r2 | r16
            r0.mChangingConfigurations = r2
            r9.recycle()
            if (r13 == 0) goto L6b
            r8 = 1
        L6b:
            int[] r5 = com.android.internal.util.GrowingArrayUtils.append(r5, r7, r14)
            float[] r4 = com.android.internal.util.GrowingArrayUtils.append(r4, r7, r15)
            java.lang.Object[] r2 = com.android.internal.util.GrowingArrayUtils.append(r3, r7, r13)
            r3 = r2
            int[][] r3 = (int[][]) r3
            int r7 = r7 + 1
            goto L9e
        L7d:
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r19.getPositionDescription()
            r1.append(r2)
            java.lang.String r2 = ": <item> tag requires a 'color' attribute and a 'offset' attribute!"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L98:
            r10 = r18
            r11 = r20
            r12 = r21
        L9e:
            r2 = 1
            goto L13
        La1:
            if (r7 <= 0) goto Lc0
            if (r8 == 0) goto Lad
            int[][] r1 = new int[r7][]
            r0.mItemsThemeAttrs = r1
            java.lang.System.arraycopy(r3, r6, r1, r6, r7)
            goto Lb0
        Lad:
            r1 = 0
            r0.mItemsThemeAttrs = r1
        Lb0:
            int[] r1 = new int[r7]
            r0.mItemColors = r1
            float[] r2 = new float[r7]
            r0.mItemOffsets = r2
            java.lang.System.arraycopy(r5, r6, r1, r6, r7)
            float[] r0 = r0.mItemOffsets
            java.lang.System.arraycopy(r4, r6, r0, r6, r7)
        Lc0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.GradientColor.inflateChildElements(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    private void applyItemsAttrsTheme(Resources.Theme theme) {
        int[][] iArr = this.mItemsThemeAttrs;
        if (iArr == null) {
            return;
        }
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            int[] iArr2 = iArr[i];
            if (iArr2 != null) {
                TypedArray resolveAttributes = theme.resolveAttributes(iArr2, R.styleable.GradientColorItem);
                int[] extractThemeAttrs = resolveAttributes.extractThemeAttrs(iArr[i]);
                iArr[i] = extractThemeAttrs;
                if (extractThemeAttrs != null) {
                    z = true;
                }
                int[] iArr3 = this.mItemColors;
                iArr3[i] = resolveAttributes.getColor(0, iArr3[i]);
                float[] fArr = this.mItemOffsets;
                fArr[i] = resolveAttributes.getFloat(1, fArr[i]);
                this.mChangingConfigurations |= resolveAttributes.getChangingConfigurations();
                resolveAttributes.recycle();
            }
        }
        if (z) {
            return;
        }
        this.mItemsThemeAttrs = null;
    }

    private void onColorsChange() {
        int[] iArr;
        float[] fArr;
        int[] iArr2;
        int[] iArr3 = this.mItemColors;
        if (iArr3 != null) {
            int length = iArr3.length;
            iArr = new int[length];
            fArr = new float[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = this.mItemColors[i];
                fArr[i] = this.mItemOffsets[i];
            }
        } else if (this.mHasCenterColor) {
            iArr = new int[]{this.mStartColor, this.mCenterColor, this.mEndColor};
            fArr = new float[]{0.0f, 0.5f, 1.0f};
        } else {
            iArr = new int[]{this.mStartColor, this.mEndColor};
            fArr = null;
        }
        int[] iArr4 = iArr;
        float[] fArr2 = fArr;
        if (iArr4.length < 2) {
            Log.w(TAG, "<gradient> tag requires 2 color values specified!" + iArr4.length + " " + Arrays.toString(iArr4));
        }
        int i2 = this.mGradientType;
        if (i2 == 0) {
            iArr2 = iArr4;
            this.mShader = new LinearGradient(this.mStartX, this.mStartY, this.mEndX, this.mEndY, iArr2, fArr2, parseTileMode(this.mTileMode));
        } else {
            iArr2 = iArr4;
            if (i2 == 1) {
                RadialGradient radialGradient = new RadialGradient(this.mCenterX, this.mCenterY, this.mGradientRadius, iArr2, fArr2, parseTileMode(this.mTileMode));
                iArr2 = iArr2;
                this.mShader = radialGradient;
            } else {
                this.mShader = new SweepGradient(this.mCenterX, this.mCenterY, iArr2, fArr2);
            }
        }
        this.mDefaultColor = iArr2[0];
    }

    @Override // android.content.res.ComplexColor
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    @Override // android.content.res.ComplexColor
    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new GradientColorFactory(this);
        }
        return this.mFactory;
    }

    private static class GradientColorFactory extends ConstantState<ComplexColor> {
        private final GradientColor mSrc;

        public GradientColorFactory(GradientColor gradientColor) {
            this.mSrc = gradientColor;
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public ComplexColor newInstance2() {
            return this.mSrc;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public ComplexColor newInstance2(Resources resources, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    @Override // android.content.res.ComplexColor
    public GradientColor obtainForTheme(Resources.Theme theme) {
        if (theme == null || !canApplyTheme()) {
            return this;
        }
        GradientColor gradientColor = new GradientColor(this);
        gradientColor.applyTheme(theme);
        return gradientColor;
    }

    @Override // android.content.res.ComplexColor
    public int getChangingConfigurations() {
        return this.mChangingConfigurations | super.getChangingConfigurations();
    }

    private void applyTheme(Resources.Theme theme) {
        if (this.mThemeAttrs != null) {
            applyRootAttrsTheme(theme);
        }
        if (this.mItemsThemeAttrs != null) {
            applyItemsAttrsTheme(theme);
        }
        onColorsChange();
    }

    private void applyRootAttrsTheme(Resources.Theme theme) {
        TypedArray resolveAttributes = theme.resolveAttributes(this.mThemeAttrs, R.styleable.GradientColor);
        this.mThemeAttrs = resolveAttributes.extractThemeAttrs(this.mThemeAttrs);
        updateRootElementState(resolveAttributes);
        this.mChangingConfigurations |= resolveAttributes.getChangingConfigurations();
        resolveAttributes.recycle();
    }

    @Override // android.content.res.ComplexColor
    public boolean canApplyTheme() {
        return (this.mThemeAttrs == null && this.mItemsThemeAttrs == null) ? false : true;
    }
}
