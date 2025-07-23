package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.core.provider.FontsContractCompat$FontInfo;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.io.IOException;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    public static Font findBaseFont(FontFamily fontFamily, int i) {
        FontStyle fontStyle = new FontStyle((i & 1) != 0 ? KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED : 400, (i & 2) != 0 ? 1 : 0);
        Font font = fontFamily.getFont(0);
        int matchScore = getMatchScore(fontStyle, font.getStyle());
        for (int i2 = 1; i2 < fontFamily.getSize(); i2++) {
            Font font2 = fontFamily.getFont(i2);
            int matchScore2 = getMatchScore(fontStyle, font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
        }
        return font;
    }

    public static FontFamily getFontFamily(FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr, ContentResolver contentResolver) {
        int i;
        ParcelFileDescriptor openFileDescriptor;
        int length = fontsContractCompat$FontInfoArr.length;
        FontFamily.Builder builder = null;
        while (i < length) {
            FontsContractCompat$FontInfo fontsContractCompat$FontInfo = fontsContractCompat$FontInfoArr[i];
            try {
                openFileDescriptor = contentResolver.openFileDescriptor(fontsContractCompat$FontInfo.mUri, "r", null);
            } catch (IOException e) {
                Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            }
            if (openFileDescriptor == null) {
                i = openFileDescriptor == null ? i + 1 : 0;
            } else {
                try {
                    Font build = new Font.Builder(openFileDescriptor).setWeight(fontsContractCompat$FontInfo.mWeight).setSlant(fontsContractCompat$FontInfo.mItalic ? 1 : 0).setTtcIndex(fontsContractCompat$FontInfo.mTtcIndex).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(build);
                    } else {
                        builder.addFont(build);
                    }
                } catch (Throwable th) {
                    try {
                        openFileDescriptor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            openFileDescriptor.close();
        }
        if (builder == null) {
            return null;
        }
        return builder.build();
    }

    public static int getMatchScore(FontStyle fontStyle, FontStyle fontStyle2) {
        return (Math.abs(fontStyle.getWeight() - fontStyle2.getWeight()) / 100) + (fontStyle.getSlant() == fontStyle2.getSlant() ? 0 : 2);
    }

    public final Typeface createFromFontInfoWithFallback(Context context, int i, List list) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            FontFamily fontFamily = getFontFamily((FontsContractCompat$FontInfo[]) list.get(0), contentResolver);
            if (fontFamily == null) {
                return null;
            }
            Typeface.CustomFallbackBuilder customFallbackBuilder = new Typeface.CustomFallbackBuilder(fontFamily);
            for (int i2 = 1; i2 < list.size(); i2++) {
                FontFamily fontFamily2 = getFontFamily((FontsContractCompat$FontInfo[]) list.get(i2), contentResolver);
                if (fontFamily2 != null) {
                    customFallbackBuilder.addCustomFallback(fontFamily2);
                }
            }
            return customFallbackBuilder.setStyle(findBaseFont(fontFamily, i).getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            return null;
        }
    }
}
