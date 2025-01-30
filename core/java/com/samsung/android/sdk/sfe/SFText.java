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

/* loaded from: classes5.dex */
public class SFText {
  private static final String TAG = "SFText";
  private boolean firstInitializedFlag;
  private CharSequence mCharSequense;
  private Context mContext;
  private String mFlipFont;
  private String mFontFamily;
  private FontManager mFontManager;
  private int mHeight;
  private Layout mLayout;
  private String mText;
  private int mWidth;
  private TextView owner;
  private String mFontPath = null;
  private boolean isBoldStyle = false;
  private boolean isItalicStyle = false;
  private boolean isSetFontFromAsset = false;
  private boolean isSetFontFromFile = false;
  private int mLines = 0;
  private Paint mPaint = null;
  private int mHandle = -1;
  private boolean hasEffect = false;

  private native int SFText_AddInnerShadowTextEffect(float f, float f2, float f3, int i, float f4);

  private native int SFText_AddLinearGradientTextEffect(
      float f, float f2, int[] iArr, float[] fArr, float[] fArr2, float f3);

  private native int SFText_AddOuterGlowTextEffect(float f, int i, float f2);

  private native int SFText_AddOuterShadowTextEffect(float f, float f2, float f3, int i, float f4);

  private native int SFText_AddStrokeTextEffect(float f, int i, float f2, int i2, int i3);

  private native void SFText_ClearAllTextEffect();

  private native int[] SFText_GetDrawingBitmapSize();

  private native int SFText_GetEffectBottomOffset();

  private native int SFText_GetEffectLeftOffset();

  private native int SFText_GetEffectRightOffset();

  private native int SFText_GetEffectTopOffset();

  private native int[] SFText_RenderTextEffect();

  private native boolean SFText_SetFont(String str);

  private native boolean SFText_SetFont2(String str, byte[] bArr);

  private native boolean SFText_SetFont3(AssetManager assetManager, String str);

  private native boolean SFText_SetFontFamilyName(String str);

  private native boolean SFText_SetFontWeight(int i);

  private native boolean SFText_SetLayout(Layout layout);

  private native boolean SFText_SetLine(int i);

  private native boolean SFText_SetPaint(Paint paint);

  private native boolean SFText_SetView(TextView textView);

  private native void SFText_finalize();

  public SFText(Context context) {
    this.firstInitializedFlag = true;
    this.mContext = context;
    this.firstInitializedFlag = true;
  }

  private void init() {
    if (!this.firstInitializedFlag) {
      return;
    }
    SFEffect.initialize();
    if (!SFEffect.isInitialized()) {
      return;
    }
    FontManager fontManager = SFEffect.getFontManager();
    this.mFontManager = fontManager;
    this.mFlipFont = fontManager.getFlipFontPath(this.mContext);
    this.firstInitializedFlag = false;
    if (this.mPaint == null) {
      this.mPaint = new Paint();
    }
  }

  public void finalize() throws Throwable {
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
    Log.m94d(TAG, "render() - Start!");
    try {
      this.mWidth = this.owner.getWidth();
      this.mHeight = this.owner.getHeight();
      Layout layout = this.owner.getLayout();
      this.mLayout = layout;
      CharSequence text = layout.getText();
      this.mCharSequense = text;
      this.mText = text.toString();
      Typeface typeface = this.owner.getTypeface();
      if (typeface != null) {
        this.isBoldStyle = typeface.isBold();
        this.isItalicStyle = typeface.isItalic();
      } else {
        this.isBoldStyle = false;
        this.isItalicStyle = false;
      }
      getFontPath();
      if (this.mLayout == null) {
        Log.m96e(TAG, "Can not render text effect - layout is null");
        return false;
      }
      if (this.mWidth > 0 && this.mHeight > 0) {
        setSFTextPaint(this.owner.getPaint());
        setSFTextLine(this.mLines);
        setSFTextView(this.owner);
        setSFTextLayout(this.mLayout);
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
        Log.m94d(TAG, "render() - End.");
        return true;
      }
      Log.m96e(TAG, "Can not render text effect - width and height must be > 0");
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      Log.m102w(TAG, "render() - Fail.");
      return false;
    }
  }

  private void setSFTextPaint(Paint paint) {
    boolean rnt = SFText_SetPaint(paint);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFTextLine(int line) {
    boolean rnt = SFText_SetLine(line);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFTextLayout(Layout layout) {
    boolean rnt = SFText_SetLayout(layout);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFTextView(TextView v) {
    boolean rnt = SFText_SetView(v);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void getFontPath() {
    if (this.isSetFontFromAsset || this.isSetFontFromFile) {
      return;
    }
    String str = this.mFontFamily;
    if (str == null || str.isEmpty()) {
      this.mFontFamily = Typeface.DEFAULT_FAMILY;
    }
    String fontName =
        this.mFontManager.getSystemFontName(this.mFontFamily, this.isBoldStyle, this.isItalicStyle);
    if (fontName == null) {
      Log.m102w(
          TAG,
          "System not support fontFamily = '"
              + this.mFontFamily
              + "' , change to default fontFamily");
      this.mFontFamily = Typeface.DEFAULT_FAMILY;
      fontName =
          this.mFontManager.getSystemFontName(
              Typeface.DEFAULT_FAMILY, this.isBoldStyle, this.isItalicStyle);
      if (fontName == null) {
        Log.m96e(TAG, "System not support default fontFamily = '" + this.mFontFamily);
        return;
      }
    }
    this.mFontPath = fontName;
    if (fontName.indexOf(SystemFonts.SYSTEM_FONT_DIR) != 0) {
      this.mFontPath = SystemFonts.SYSTEM_FONT_DIR + fontName;
    }
    String str2 = this.mFlipFont;
    if (str2 != null) {
      this.mFontPath = str2;
    }
    setSFFontFile(this.mFontPath);
    setSFFontFamilyName(this.mFontFamily);
    setSFFontWeight(600);
  }

  public void setOwnerView(TextView v) {
    Log.m94d(TAG, "setOwnerView");
    if (v != null) {
      this.owner = v;
    } else {
      Log.m96e(TAG, "setOwnerView - Textview is null");
    }
  }

  private int[] renderTextEffect() {
    return SFText_RenderTextEffect();
  }

  private int[] getDrawingBitmapSize() {
    return SFText_GetDrawingBitmapSize();
  }

  public int addOuterShadowTextEffect(
      float angle, float offset, float softness, int color, float blendingOpacity) {
    Log.m94d(TAG, "addOuterShadowTextEffect");
    init();
    if (!SFEffect.isInitialized()) {
      return -1;
    }
    this.hasEffect = true;
    return SFText_AddOuterShadowTextEffect(angle, offset, softness, color, blendingOpacity);
  }

  public int addInnerShadowTextEffect(
      float angle, float offset, float softness, int color, float blendingOpacity) {
    Log.m94d(TAG, "addInnerShadowTextEffect");
    init();
    if (!SFEffect.isInitialized()) {
      return -1;
    }
    this.hasEffect = true;
    return SFText_AddInnerShadowTextEffect(angle, offset, softness, color, blendingOpacity);
  }

  public int addStrokeTextEffect(float size, int color, float blendingOpacity) {
    Log.m94d(TAG, "addStrokeTextEffect");
    init();
    if (!SFEffect.isInitialized()) {
      return -1;
    }
    this.hasEffect = true;
    return SFText_AddStrokeTextEffect(size, color, blendingOpacity, 0, 1);
  }

  public int addOuterGlowTextEffect(float size, int color, float blendingOpacity) {
    Log.m94d(TAG, "addOuterGlowTextEffect");
    init();
    if (!SFEffect.isInitialized()) {
      return -1;
    }
    this.hasEffect = true;
    return SFText_AddOuterGlowTextEffect(size, color, blendingOpacity);
  }

  public int addLinearGradientTextEffect(
      float angle,
      float scale,
      int[] colors,
      float[] alphas,
      float[] positions,
      float blendingOpacity) {
    Log.m94d(TAG, "addLinearGradientTextEffect");
    init();
    if (!SFEffect.isInitialized()) {
      return -1;
    }
    this.hasEffect = true;
    return SFText_AddLinearGradientTextEffect(
        angle, scale, colors, alphas, positions, blendingOpacity);
  }

  public void clearAllTextEffect() {
    Log.m94d(TAG, "clearAllTextEffect");
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
    Log.m94d(TAG, "setFontFromFile");
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
    Log.m94d(TAG, "setFontFromAsset");
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
    boolean rnt = SFText_SetFont(filePath);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFFontFamilyName(String familyName) {
    boolean rnt = SFText_SetFontFamilyName(familyName);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFFontWeight(int weight) {
    boolean rnt = SFText_SetFontWeight(weight);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFFontFile(String filePath, byte[] buf) {
    boolean rnt = SFText_SetFont2(filePath, buf);
    if (!rnt) {
      throwUncheckedException(SFError.getError());
    }
  }

  private void setSFFontFile(AssetManager assetManager, String filePath) {
    boolean rnt = SFText_SetFont3(assetManager, filePath);
    if (!rnt) {
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

  private int getEffectRightOffset() {
    return SFText_GetEffectRightOffset();
  }

  private int getEffectBottomOffset() {
    return SFText_GetEffectBottomOffset();
  }
}
