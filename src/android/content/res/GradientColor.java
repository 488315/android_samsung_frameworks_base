package android.content.res;

import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.ims.ImsConfig;
import com.android.internal.R;
import com.android.internal.util.GrowingArrayUtils;
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
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(resources, xmlResourceParser, attributeSetAsAttributeSet, theme);
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
        TypedArray typedArrayObtainAttributes = Resources.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientColor);
        updateRootElementState(typedArrayObtainAttributes);
        this.mChangingConfigurations |= typedArrayObtainAttributes.getChangingConfigurations();
        typedArrayObtainAttributes.recycle();
        validateXmlContent();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        onColorsChange();
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a1, code lost:
    
        if (r7 <= 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a3, code lost:
    
        if (r8 == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00a5, code lost:
    
        r1 = new int[r7][];
        r17.mItemsThemeAttrs = r1;
        java.lang.System.arraycopy(r3, 0, r1, 0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ad, code lost:
    
        r17.mItemsThemeAttrs = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b0, code lost:
    
        r1 = new int[r7];
        r17.mItemColors = r1;
        r17.mItemOffsets = new float[r7];
        java.lang.System.arraycopy(r5, 0, r1, 0, r7);
        java.lang.System.arraycopy(r4, 0, r17.mItemOffsets, 0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c0, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int i = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        float[] fArrAppend = new float[20];
        int[] iArrAppend = new int[20];
        int[][] iArr = new int[20][];
        int i2 = 0;
        boolean z = false;
        while (true) {
            int next = xmlPullParser.next();
            if (next == i || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                TypedArray typedArrayObtainAttributes = Resources.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientColorItem);
                boolean zHasValue = typedArrayObtainAttributes.hasValue(0);
                boolean zHasValue2 = typedArrayObtainAttributes.hasValue(i);
                if (!zHasValue || !zHasValue2) {
                    break;
                }
                int[] iArrExtractThemeAttrs = typedArrayObtainAttributes.extractThemeAttrs();
                int color = typedArrayObtainAttributes.getColor(0, 0);
                float f = typedArrayObtainAttributes.getFloat(i, 0.0f);
                this.mChangingConfigurations |= typedArrayObtainAttributes.getChangingConfigurations();
                typedArrayObtainAttributes.recycle();
                if (iArrExtractThemeAttrs != null) {
                    z = true;
                }
                iArrAppend = GrowingArrayUtils.append(iArrAppend, i2, color);
                fArrAppend = GrowingArrayUtils.append(fArrAppend, i2, f);
                iArr = (int[][]) GrowingArrayUtils.append(iArr, i2, iArrExtractThemeAttrs);
                i2++;
            }
            i = 1;
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
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
                TypedArray typedArrayResolveAttributes = theme.resolveAttributes(iArr2, R.styleable.GradientColorItem);
                int[] iArrExtractThemeAttrs = typedArrayResolveAttributes.extractThemeAttrs(iArr[i]);
                iArr[i] = iArrExtractThemeAttrs;
                if (iArrExtractThemeAttrs != null) {
                    z = true;
                }
                int[] iArr3 = this.mItemColors;
                iArr3[i] = typedArrayResolveAttributes.getColor(0, iArr3[i]);
                float[] fArr = this.mItemOffsets;
                fArr[i] = typedArrayResolveAttributes.getFloat(1, fArr[i]);
                this.mChangingConfigurations |= typedArrayResolveAttributes.getChangingConfigurations();
                typedArrayResolveAttributes.recycle();
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
        TypedArray typedArrayResolveAttributes = theme.resolveAttributes(this.mThemeAttrs, R.styleable.GradientColor);
        this.mThemeAttrs = typedArrayResolveAttributes.extractThemeAttrs(this.mThemeAttrs);
        updateRootElementState(typedArrayResolveAttributes);
        this.mChangingConfigurations |= typedArrayResolveAttributes.getChangingConfigurations();
        typedArrayResolveAttributes.recycle();
    }

    @Override // android.content.res.ComplexColor
    public boolean canApplyTheme() {
        return (this.mThemeAttrs == null && this.mItemsThemeAttrs == null) ? false : true;
    }
}
