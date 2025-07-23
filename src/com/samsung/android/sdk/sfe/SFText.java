package com.samsung.android.sdk.sfe;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;
import com.samsung.android.sdk.sfe.font.FontManager;
import com.samsung.android.sdk.sfe.util.SFError;
import java.io.File;

/* loaded from: classes6.dex */
public class SFText {
    private static final String TAG = "SFText";
    private final Context mContext;
    private String mFlipFont;
    private String mFontFamily;
    private FontManager mFontManager;
    private TextView owner;
    private boolean isBoldStyle = false;
    private boolean isItalicStyle = false;
    private boolean isSetFontFromAsset = false;
    private boolean isSetFontFromFile = false;
    private int mLines = 0;
    private Paint mPaint = null;
    private int mHandle = -1;
    private boolean hasEffect = false;
    private boolean firstInitializedFlag = true;

    private native int SFText_AddInnerShadowTextEffect(float f, float f2, float f3, int i, float f4);

    private native int SFText_AddLinearGradientTextEffect(float f, float f2, int[] iArr, float[] fArr, float[] fArr2, float f3);

    private native int SFText_AddOuterGlowTextEffect(float f, int i, float f2);

    private native int SFText_AddOuterShadowTextEffect(float f, float f2, float f3, int i, float f4);

    private native int SFText_AddStrokeTextEffect(float f, int i, float f2, int i2, int i3);

    private native void SFText_ClearAllTextEffect();

    private native int[] SFText_GetDrawingBitmapSize();

    private native int SFText_GetEffectLeftOffset();

    private native int SFText_GetEffectTopOffset();

    private native int[] SFText_RenderTextEffect();

    private native boolean SFText_SetFont(String str);

    private native boolean SFText_SetFont2(String str, byte[] bArr);

    private native boolean SFText_SetFont3(AssetManager assetManager, String str);

    private native boolean SFText_SetFontFamilyName(String str);

    private native boolean SFText_SetLayout(Layout layout);

    private native boolean SFText_SetLine(int i);

    private native boolean SFText_SetPaint(Paint paint);

    private native boolean SFText_SetView(TextView textView);

    private native void SFText_finalize();

    public SFText(Context context) {
        this.mContext = context;
    }

    private void init() {
        if (this.firstInitializedFlag) {
            SFEffect.initialize();
            if (SFEffect.isInitialized()) {
                FontManager fontManager = SFEffect.getFontManager();
                this.mFontManager = fontManager;
                this.mFlipFont = fontManager.getFlipFontPath(this.mContext);
                this.firstInitializedFlag = false;
                if (this.mPaint == null) {
                    this.mPaint = new Paint();
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        if (SFEffect.isInitialized()) {
            SFText_finalize();
            this.mHandle = -1;
        }
    }

    public void setFontFamily(String str) {
        this.mFontFamily = str;
    }

    public void setLines(int i) {
        this.mLines = i;
    }

    public boolean render(Canvas canvas, int i, int i2) {
        if (!FontManager.isSetConfigFinished()) {
            return false;
        }
        Log.d(TAG, "render() - Start!");
        try {
            int width = this.owner.getWidth();
            int height = this.owner.getHeight();
            Layout layout = this.owner.getLayout();
            Typeface typeface = this.owner.getTypeface();
            if (typeface == null) {
                this.isBoldStyle = false;
                this.isItalicStyle = false;
            } else {
                this.isBoldStyle = typeface.isBold();
                this.isItalicStyle = typeface.isItalic();
            }
            getFontPath();
            if (layout == null) {
                Log.e(TAG, "Can not render text effect - layout is null");
                return false;
            }
            if (width > 0 && height > 0) {
                setSFTextPaint(this.owner.getPaint());
                setSFTextLine(this.mLines);
                setSFTextView(this.owner);
                setSFTextLayout(layout);
                int[] renderTextEffect = renderTextEffect();
                int[] drawingBitmapSize = getDrawingBitmapSize();
                int i3 = drawingBitmapSize[0];
                int i4 = drawingBitmapSize[1];
                Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(renderTextEffect, 0, i3, 0, 0, i3, i4);
                canvas.save();
                int effectLeftOffset = getEffectLeftOffset();
                int effectTopOffset = getEffectTopOffset();
                int i5 = 0;
                int i6 = 0;
                for (int i7 = 0; i7 < i3; i7++) {
                    int i8 = 0;
                    while (true) {
                        if (i8 >= i4) {
                            break;
                        }
                        i6 = Color.alpha(createBitmap.getPixel(i7, i8));
                        if (i6 > 0) {
                            i5 = i7;
                            break;
                        }
                        i8++;
                    }
                    if (i6 > 0) {
                        break;
                    }
                }
                int i9 = 0;
                for (int i10 = i3 - 1; i10 >= 0; i10--) {
                    int i11 = 0;
                    while (true) {
                        if (i11 >= i4) {
                            break;
                        }
                        i6 = Color.alpha(createBitmap.getPixel(i10, i11));
                        if (i6 > 0) {
                            i9 = i10;
                            break;
                        }
                        i11++;
                    }
                    if (i6 > 0) {
                        break;
                    }
                }
                canvas.translate(i - effectLeftOffset, i2 - effectTopOffset);
                canvas.drawBitmap(createBitmap, (i5 + (i3 - i9)) / 2, 0.0f, this.mPaint);
                canvas.restore();
                Log.d(TAG, "render() - End.");
                return true;
            }
            Log.e(TAG, "Can not render text effect - width and height must be > 0");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "render() - Fail.");
            return false;
        }
    }

    private void setSFTextPaint(Paint paint) {
        if (SFText_SetPaint(paint)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void setSFTextLine(int i) {
        if (SFText_SetLine(i)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void setSFTextLayout(Layout layout) {
        if (SFText_SetLayout(layout)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void setSFTextView(TextView textView) {
        if (SFText_SetView(textView)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void getFontPath() {
        if (this.isSetFontFromAsset || this.isSetFontFromFile) {
            return;
        }
        String str = this.mFontFamily;
        if (str == null || str.isEmpty()) {
            this.mFontFamily = Typeface.DEFAULT_FAMILY;
        }
        String systemFontName = this.mFontManager.getSystemFontName(this.mFontFamily, this.isBoldStyle, this.isItalicStyle);
        if (systemFontName == null) {
            Log.w(TAG, "System not support fontFamily = '" + this.mFontFamily + "' , change to default fontFamily");
            this.mFontFamily = Typeface.DEFAULT_FAMILY;
            systemFontName = this.mFontManager.getSystemFontName(Typeface.DEFAULT_FAMILY, this.isBoldStyle, this.isItalicStyle);
            if (systemFontName == null) {
                Log.e(TAG, "System not support default fontFamily = '" + this.mFontFamily);
                return;
            }
        }
        if (systemFontName.indexOf("/system/fonts/") != 0) {
            systemFontName = "/system/fonts/" + systemFontName;
        }
        String str2 = this.mFlipFont;
        if (str2 != null) {
            systemFontName = str2;
        }
        setSFFontFile(systemFontName);
        setSFFontFamilyName(this.mFontFamily);
    }

    public void setOwnerView(TextView textView) {
        Log.d(TAG, "setOwnerView");
        if (textView != null) {
            this.owner = textView;
        } else {
            Log.e(TAG, "setOwnerView - Textview is null");
        }
    }

    private int[] renderTextEffect() {
        return SFText_RenderTextEffect();
    }

    private int[] getDrawingBitmapSize() {
        return SFText_GetDrawingBitmapSize();
    }

    public int addOuterShadowTextEffect(float f, float f2, float f3, int i, float f4) {
        Log.d(TAG, "addOuterShadowTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddOuterShadowTextEffect(f, f2, f3, i, f4);
    }

    public int addInnerShadowTextEffect(float f, float f2, float f3, int i, float f4) {
        Log.d(TAG, "addInnerShadowTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddInnerShadowTextEffect(f, f2, f3, i, f4);
    }

    public int addStrokeTextEffect(float f, int i, float f2) {
        Log.d(TAG, "addStrokeTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddStrokeTextEffect(f, i, f2, 0, 1);
    }

    public int addOuterGlowTextEffect(float f, int i, float f2) {
        Log.d(TAG, "addOuterGlowTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddOuterGlowTextEffect(f, i, f2);
    }

    public int addLinearGradientTextEffect(float f, float f2, int[] iArr, float[] fArr, float[] fArr2, float f3) {
        Log.d(TAG, "addLinearGradientTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddLinearGradientTextEffect(f, f2, iArr, fArr, fArr2, f3);
    }

    public void clearAllTextEffect() {
        Log.d(TAG, "clearAllTextEffect");
        if (this.mHandle < 0) {
            return;
        }
        init();
        if (SFEffect.isInitialized()) {
            this.hasEffect = false;
            SFText_ClearAllTextEffect();
        }
    }

    public void setFontFromFile(String str) {
        Log.d(TAG, "setFontFromFile");
        init();
        if (SFEffect.isInitialized() && str != null && !str.isEmpty() && new File(str).exists()) {
            setSFFontFile(str);
            this.isSetFontFromAsset = false;
            this.isSetFontFromFile = true;
        }
    }

    public void setFontFromAsset(AssetManager assetManager, String str) {
        Log.d(TAG, "setFontFromAsset");
        init();
        if (!SFEffect.isInitialized() || assetManager == null || str == null || str.isEmpty()) {
            return;
        }
        setSFFontFile(assetManager, str);
        this.isSetFontFromAsset = true;
        this.isSetFontFromFile = false;
    }

    public boolean hasEffect() {
        if (SFEffect.isInitialized()) {
            return this.hasEffect;
        }
        return false;
    }

    private void setSFFontFile(String str) {
        if (SFText_SetFont(str)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void setSFFontFamilyName(String str) {
        if (SFText_SetFontFamilyName(str)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void setSFFontFile(AssetManager assetManager, String str) {
        if (SFText_SetFont3(assetManager, str)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private void throwUncheckedException(int i) {
        SFError.ThrowUncheckedException(i);
    }

    private int getEffectLeftOffset() {
        return SFText_GetEffectLeftOffset();
    }

    private int getEffectTopOffset() {
        return SFText_GetEffectTopOffset();
    }
}
