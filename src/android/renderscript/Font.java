package android.renderscript;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Environment;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Deprecated
/* loaded from: classes3.dex */
public class Font extends BaseObj {
    private static Map<String, FontFamily> sFontFamilyMap;
    private static final String[] sSansNames = {Typeface.DEFAULT_FAMILY, "arial", "helvetica", "tahoma", "verdana"};
    private static final String[] sSerifNames = {"serif", "times", "times new roman", "palatino", "georgia", "baskerville", "goudy", "fantasy", "cursive", "ITC Stone Serif"};
    private static final String[] sMonoNames = {"monospace", "courier", "courier new", "monaco"};

    public enum Style {
        NORMAL,
        BOLD,
        ITALIC,
        BOLD_ITALIC
    }

    static {
        initFontFamilyMap();
    }

    private static class FontFamily {
        String mBoldFileName;
        String mBoldItalicFileName;
        String mItalicFileName;
        String[] mNames;
        String mNormalFileName;

        private FontFamily() {
        }
    }

    private static void addFamilyToMap(FontFamily fontFamily) {
        for (int i = 0; i < fontFamily.mNames.length; i++) {
            sFontFamilyMap.put(fontFamily.mNames[i], fontFamily);
        }
    }

    private static void initFontFamilyMap() {
        sFontFamilyMap = new HashMap();
        FontFamily fontFamily = new FontFamily();
        fontFamily.mNames = sSansNames;
        fontFamily.mNormalFileName = "Roboto-Regular.ttf";
        fontFamily.mBoldFileName = "Roboto-Bold.ttf";
        fontFamily.mItalicFileName = "Roboto-Italic.ttf";
        fontFamily.mBoldItalicFileName = "Roboto-BoldItalic.ttf";
        addFamilyToMap(fontFamily);
        FontFamily fontFamily2 = new FontFamily();
        fontFamily2.mNames = sSerifNames;
        fontFamily2.mNormalFileName = "NotoSerif-Regular.ttf";
        fontFamily2.mBoldFileName = "NotoSerif-Bold.ttf";
        fontFamily2.mItalicFileName = "NotoSerif-Italic.ttf";
        fontFamily2.mBoldItalicFileName = "NotoSerif-BoldItalic.ttf";
        addFamilyToMap(fontFamily2);
        FontFamily fontFamily3 = new FontFamily();
        fontFamily3.mNames = sMonoNames;
        fontFamily3.mNormalFileName = "DroidSansMono.ttf";
        fontFamily3.mBoldFileName = "DroidSansMono.ttf";
        fontFamily3.mItalicFileName = "DroidSansMono.ttf";
        fontFamily3.mBoldItalicFileName = "DroidSansMono.ttf";
        addFamilyToMap(fontFamily3);
    }

    static String getFontFileName(String str, Style style) {
        FontFamily fontFamily = sFontFamilyMap.get(str);
        if (fontFamily != null) {
            int iOrdinal = style.ordinal();
            if (iOrdinal == 0) {
                return fontFamily.mNormalFileName;
            }
            if (iOrdinal == 1) {
                return fontFamily.mBoldFileName;
            }
            if (iOrdinal == 2) {
                return fontFamily.mItalicFileName;
            }
            if (iOrdinal == 3) {
                return fontFamily.mBoldItalicFileName;
            }
            return "DroidSans.ttf";
        }
        return "DroidSans.ttf";
    }

    Font(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public static Font createFromFile(RenderScript renderScript, Resources resources, String str, float f) throws Throwable {
        renderScript.validate();
        long jNFontCreateFromFile = renderScript.nFontCreateFromFile(str, f, resources.getDisplayMetrics().densityDpi);
        if (jNFontCreateFromFile == 0) {
            throw new RSRuntimeException("Unable to create font from file " + str);
        }
        return new Font(jNFontCreateFromFile, renderScript);
    }

    public static Font createFromFile(RenderScript renderScript, Resources resources, File file, float f) {
        return createFromFile(renderScript, resources, file.getAbsolutePath(), f);
    }

    public static Font createFromAsset(RenderScript renderScript, Resources resources, String str, float f) throws Throwable {
        renderScript.validate();
        long jNFontCreateFromAsset = renderScript.nFontCreateFromAsset(resources.getAssets(), str, f, resources.getDisplayMetrics().densityDpi);
        if (jNFontCreateFromAsset == 0) {
            throw new RSRuntimeException("Unable to create font from asset " + str);
        }
        return new Font(jNFontCreateFromAsset, renderScript);
    }

    public static Font createFromResource(RenderScript renderScript, Resources resources, int i, float f) throws Throwable {
        String str = "R." + Integer.toString(i);
        renderScript.validate();
        try {
            InputStream inputStreamOpenRawResource = resources.openRawResource(i);
            int i2 = resources.getDisplayMetrics().densityDpi;
            if (inputStreamOpenRawResource instanceof AssetManager.AssetInputStream) {
                long jNFontCreateFromAssetStream = renderScript.nFontCreateFromAssetStream(str, f, i2, ((AssetManager.AssetInputStream) inputStreamOpenRawResource).getNativeAsset());
                if (jNFontCreateFromAssetStream == 0) {
                    throw new RSRuntimeException("Unable to create font from resource " + i);
                }
                return new Font(jNFontCreateFromAssetStream, renderScript);
            }
            throw new RSRuntimeException("Unsupported asset stream created");
        } catch (Exception unused) {
            throw new RSRuntimeException("Unable to open resource " + i);
        }
    }

    public static Font create(RenderScript renderScript, Resources resources, String str, Style style, float f) {
        String fontFileName = getFontFileName(str, style);
        return createFromFile(renderScript, resources, Environment.getRootDirectory().getAbsolutePath() + "/fonts/" + fontFileName, f);
    }
}
