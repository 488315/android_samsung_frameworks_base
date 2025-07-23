package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.Trace;
import android.util.Log;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.FontsContractCompat$FontInfo;
import androidx.core.provider.FontsContractCompat$FontRequestCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TypefaceCompat {
    public static final LruCache sTypefaceCache;
    public static final TypefaceCompatApi29Impl sTypefaceCompatImpl;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ResourcesCallbackAdapter extends FontsContractCompat$FontRequestCallback {
        public final ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }

        @Override // androidx.core.provider.FontsContractCompat$FontRequestCallback
        public final void onTypefaceRequestFailed(int i) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.onFontRetrievalFailed(i);
            }
        }

        @Override // androidx.core.provider.FontsContractCompat$FontRequestCallback
        public final void onTypefaceRetrieved(Typeface typeface) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.onFontRetrieved(typeface);
            }
        }
    }

    static {
        Trace.beginSection("TypefaceCompat static init");
        sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
        sTypefaceCache = new LruCache(16);
        Trace.endSection();
    }

    private TypefaceCompat() {
    }

    public static Typeface createFromFontInfo(Context context, FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr, int i) {
        Trace.beginSection("TypefaceCompat.createFromFontInfo");
        try {
            sTypefaceCompatImpl.getClass();
            Typeface typeface = null;
            try {
                FontFamily fontFamily = TypefaceCompatApi29Impl.getFontFamily(fontsContractCompat$FontInfoArr, context.getContentResolver());
                if (fontFamily != null) {
                    typeface = new Typeface.CustomFallbackBuilder(fontFamily).setStyle(TypefaceCompatApi29Impl.findBaseFont(fontFamily, i).getStyle()).build();
                }
            } catch (Exception e) {
                Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            }
            return typeface;
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        if (r3.equals(r7) == false) goto L15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Typeface createFromResourcesFamilyXml(final android.content.Context r12, androidx.core.content.res.FontResourcesParserCompat.FamilyResourceEntry r13, android.content.res.Resources r14, int r15, java.lang.String r16, int r17, final int r18, androidx.core.content.res.ResourcesCompat.FontCallback r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(android.content.Context, androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry, android.content.res.Resources, int, java.lang.String, int, int, androidx.core.content.res.ResourcesCompat$FontCallback, boolean):android.graphics.Typeface");
    }

    public static Typeface createFromResourcesFontFile(Resources resources, int i, String str, int i2, int i3) {
        Typeface typeface;
        sTypefaceCompatImpl.getClass();
        try {
            Font build = new Font.Builder(resources, i).build();
            typeface = new Typeface.CustomFallbackBuilder(new FontFamily.Builder(build).build()).setStyle(build.getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            typeface = null;
        }
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typeface);
        }
        return typeface;
    }

    public static String createResourceUid(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + '-' + str + '-' + i2 + '-' + i + '-' + i3;
    }
}
