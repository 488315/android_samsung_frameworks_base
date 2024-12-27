package com.samsung.android.sdk.sfe;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.SystemFonts;
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
        if (!this.firstInitializedFlag) {
            return;
        }
        SFEffect.initialize();
        if (!SFEffect.isInitialized()) {
            return;
        }
        this.mFontManager = SFEffect.getFontManager();
        this.mFlipFont = this.mFontManager.getFlipFontPath(this.mContext);
        this.firstInitializedFlag = false;
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
    }

    protected void finalize() throws Throwable {
        if (!SFEffect.isInitialized()) {
            return;
        }
        SFText_finalize();
        this.mHandle = -1;
    }

    public void setFontFamily(String family) {
        this.mFontFamily = family;
    }

    public void setLines(int lines) {
        this.mLines = lines;
    }

    public boolean render(Canvas canvas, int hoffset, int voffset) {
        if (!FontManager.isSetConfigFinished()) {
            return false;
        }
        Log.d(TAG, "render() - Start!");
        try {
            int mWidth = this.owner.getWidth();
            int mHeight = this.owner.getHeight();
            Layout mLayout = this.owner.getLayout();
            Typeface typeface = this.owner.getTypeface();
            if (typeface != null) {
                this.isBoldStyle = typeface.isBold();
                this.isItalicStyle = typeface.isItalic();
            } else {
                this.isBoldStyle = false;
                this.isItalicStyle = false;
            }
            getFontPath();
            if (mLayout == null) {
                Log.e(TAG, "Can not render text effect - layout is null");
                return false;
            }
            if (mWidth > 0 && mHeight > 0) {
                setSFTextPaint(this.owner.getPaint());
                setSFTextLine(this.mLines);
                setSFTextView(this.owner);
                setSFTextLayout(mLayout);
                int[] buffer = renderTextEffect();
                int[] nativeSize = getDrawingBitmapSize();
                int renderWidth = nativeSize[0];
                int renderHeight = nativeSize[1];
                Bitmap bitmap = Bitmap.createBitmap(renderWidth, renderHeight, Bitmap.Config.ARGB_8888);
                bitmap.setPixels(buffer, 0, renderWidth, 0, 0, renderWidth, renderHeight);
                canvas.save();
                int offsetx = getEffectLeftOffset();
                int offsety = getEffectTopOffset();
                canvas.translate(hoffset - offsetx, voffset - offsety);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mPaint);
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
        if (!SFText_SetPaint(paint)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void setSFTextLine(int line) {
        if (!SFText_SetLine(line)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void setSFTextLayout(Layout layout) {
        if (!SFText_SetLayout(layout)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void setSFTextView(TextView v) {
        if (!SFText_SetView(v)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void getFontPath() {
        if (this.isSetFontFromAsset || this.isSetFontFromFile) {
            return;
        }
        if (this.mFontFamily == null || this.mFontFamily.isEmpty()) {
            this.mFontFamily = Typeface.DEFAULT_FAMILY;
        }
        String fontName = this.mFontManager.getSystemFontName(this.mFontFamily, this.isBoldStyle, this.isItalicStyle);
        if (fontName == null) {
            Log.w(TAG, "System not support fontFamily = '" + this.mFontFamily + "' , change to default fontFamily");
            this.mFontFamily = Typeface.DEFAULT_FAMILY;
            fontName = this.mFontManager.getSystemFontName(this.mFontFamily, this.isBoldStyle, this.isItalicStyle);
            if (fontName == null) {
                Log.e(TAG, "System not support default fontFamily = '" + this.mFontFamily);
                return;
            }
        }
        String mFontPath = fontName;
        if (mFontPath.indexOf(SystemFonts.SYSTEM_FONT_DIR) != 0) {
            mFontPath = SystemFonts.SYSTEM_FONT_DIR + fontName;
        }
        if (this.mFlipFont != null) {
            mFontPath = this.mFlipFont;
        }
        setSFFontFile(mFontPath);
        setSFFontFamilyName(this.mFontFamily);
    }

    public void setOwnerView(TextView v) {
        Log.d(TAG, "setOwnerView");
        if (v != null) {
            this.owner = v;
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

    public int addOuterShadowTextEffect(float angle, float offset, float softness, int color, float blendingOpacity) {
        Log.d(TAG, "addOuterShadowTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddOuterShadowTextEffect(angle, offset, softness, color, blendingOpacity);
    }

    public int addInnerShadowTextEffect(float angle, float offset, float softness, int color, float blendingOpacity) {
        Log.d(TAG, "addInnerShadowTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddInnerShadowTextEffect(angle, offset, softness, color, blendingOpacity);
    }

    public int addStrokeTextEffect(float size, int color, float blendingOpacity) {
        Log.d(TAG, "addStrokeTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddStrokeTextEffect(size, color, blendingOpacity, 0, 1);
    }

    public int addOuterGlowTextEffect(float size, int color, float blendingOpacity) {
        Log.d(TAG, "addOuterGlowTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddOuterGlowTextEffect(size, color, blendingOpacity);
    }

    public int addLinearGradientTextEffect(float angle, float scale, int[] colors, float[] alphas, float[] positions, float blendingOpacity) {
        Log.d(TAG, "addLinearGradientTextEffect");
        init();
        if (!SFEffect.isInitialized()) {
            return -1;
        }
        this.hasEffect = true;
        return SFText_AddLinearGradientTextEffect(angle, scale, colors, alphas, positions, blendingOpacity);
    }

    public void clearAllTextEffect() {
        Log.d(TAG, "clearAllTextEffect");
        if (this.mHandle < 0) {
            return;
        }
        init();
        if (!SFEffect.isInitialized()) {
            return;
        }
        this.hasEffect = false;
        SFText_ClearAllTextEffect();
    }

    public void setFontFromFile(String path) {
        Log.d(TAG, "setFontFromFile");
        init();
        if (SFEffect.isInitialized() && path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                setSFFontFile(path);
                this.isSetFontFromAsset = false;
                this.isSetFontFromFile = true;
            }
        }
    }

    public void setFontFromAsset(AssetManager mgr, String path) {
        Log.d(TAG, "setFontFromAsset");
        init();
        if (SFEffect.isInitialized() && mgr != null && path != null && !path.isEmpty()) {
            setSFFontFile(mgr, path);
            this.isSetFontFromAsset = true;
            this.isSetFontFromFile = false;
        }
    }

    public boolean hasEffect() {
        if (!SFEffect.isInitialized()) {
            return false;
        }
        return this.hasEffect;
    }

    private void setSFFontFile(String filePath) {
        if (!SFText_SetFont(filePath)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void setSFFontFamilyName(String familyName) {
        if (!SFText_SetFontFamilyName(familyName)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void setSFFontFile(AssetManager assetManager, String filePath) {
        if (!SFText_SetFont3(assetManager, filePath)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private void throwUncheckedException(int errno) {
        SFError.ThrowUncheckedException(errno);
    }

    private int getEffectLeftOffset() {
        return SFText_GetEffectLeftOffset();
    }

    private int getEffectTopOffset() {
        return SFText_GetEffectTopOffset();
    }
}
