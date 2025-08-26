package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompat;
import java.io.IOException;
import java.util.Objects;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ResourcesCompat {
    public static final ThreadLocal sTempTypedValue = new ThreadLocal();
    public static final WeakHashMap sColorStateCaches = new WeakHashMap(0);
    public static final Object sColorStateCacheLock = new Object();

    public class ColorStateListCacheEntry {
        public final Configuration mConfiguration;
        public final int mThemeHash;
        public final ColorStateList mValue;

        public ColorStateListCacheEntry(ColorStateList colorStateList, Configuration configuration, Resources.Theme theme) {
            this.mValue = colorStateList;
            this.mConfiguration = new Configuration(configuration);
            this.mThemeHash = theme == null ? 0 : theme.hashCode();
        }
    }

    public final class ColorStateListCacheKey {
        public final Resources mResources;
        public final Resources.Theme mTheme;

        public ColorStateListCacheKey(Resources resources, Resources.Theme theme) {
            this.mResources = resources;
            this.mTheme = theme;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && ColorStateListCacheKey.class == obj.getClass()) {
                ColorStateListCacheKey colorStateListCacheKey = (ColorStateListCacheKey) obj;
                if (this.mResources.equals(colorStateListCacheKey.mResources) && Objects.equals(this.mTheme, colorStateListCacheKey.mTheme)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.mResources, this.mTheme);
        }
    }

    public abstract class FontCallback {
        public final void callbackFailAsync(final int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onFontRetrievalFailed(i);
                }
            });
        }

        public abstract void onFontRetrievalFailed(int i);

        public abstract void onFontRetrieved(Typeface typeface);
    }

    private ResourcesCompat() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0043, code lost:
    
        if (r4.mThemeHash == r5.hashCode()) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ColorStateList getColorStateList(int i, Resources.Theme theme, Resources resources) throws Resources.NotFoundException {
        ColorStateList colorStateListCreateFromXml;
        ColorStateList colorStateList;
        ColorStateListCacheEntry colorStateListCacheEntry;
        ColorStateListCacheKey colorStateListCacheKey = new ColorStateListCacheKey(resources, theme);
        synchronized (sColorStateCacheLock) {
            try {
                SparseArray sparseArray = (SparseArray) sColorStateCaches.get(colorStateListCacheKey);
                colorStateListCreateFromXml = null;
                if (sparseArray == null || sparseArray.size() <= 0 || (colorStateListCacheEntry = (ColorStateListCacheEntry) sparseArray.get(i)) == null) {
                    colorStateList = null;
                } else {
                    if (colorStateListCacheEntry.mConfiguration.equals(colorStateListCacheKey.mResources.getConfiguration())) {
                        Resources.Theme theme2 = colorStateListCacheKey.mTheme;
                        if (theme2 != null || colorStateListCacheEntry.mThemeHash != 0) {
                            if (theme2 != null) {
                            }
                        }
                        colorStateList = colorStateListCacheEntry.mValue;
                    }
                    sparseArray.remove(i);
                    colorStateList = null;
                }
            } finally {
            }
        }
        if (colorStateList != null) {
            return colorStateList;
        }
        ThreadLocal threadLocal = sTempTypedValue;
        TypedValue typedValue = (TypedValue) threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.type;
        if (i2 < 28 || i2 > 31) {
            try {
                colorStateListCreateFromXml = ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i), theme);
            } catch (Exception e) {
                Log.w("ResourcesCompat", "Failed to inflate ColorStateList, leaving it to the framework", e);
            }
        }
        if (colorStateListCreateFromXml == null) {
            return resources.getColorStateList(i, theme);
        }
        synchronized (sColorStateCacheLock) {
            try {
                WeakHashMap weakHashMap = sColorStateCaches;
                SparseArray sparseArray2 = (SparseArray) weakHashMap.get(colorStateListCacheKey);
                if (sparseArray2 == null) {
                    sparseArray2 = new SparseArray();
                    weakHashMap.put(colorStateListCacheKey, sparseArray2);
                }
                sparseArray2.append(i, new ColorStateListCacheEntry(colorStateListCreateFromXml, colorStateListCacheKey.mResources.getConfiguration(), theme));
            } finally {
            }
        }
        return colorStateListCreateFromXml;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00bd A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Typeface loadFont(Context context, int i, TypedValue typedValue, int i2, FontCallback fontCallback, boolean z, boolean z2) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String string = charSequence.toString();
        Typeface typefaceCreateFromResourcesFamilyXml = null;
        if (string.startsWith("res/")) {
            Typeface typeface = (Typeface) TypefaceCompat.sTypefaceCache.get(TypefaceCompat.createResourceUid(resources, i, string, typedValue.assetCookie, i2));
            if (typeface != null) {
                if (fontCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(fontCallback, typeface));
                }
                typefaceCreateFromResourcesFamilyXml = typeface;
            } else if (!z2) {
                try {
                    if (string.toLowerCase().endsWith(".xml")) {
                        FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry = FontResourcesParserCompat.parse(resources.getXml(i), resources);
                        if (familyResourceEntry == null) {
                            Log.e("ResourcesCompat", "Failed to find font-family tag");
                            if (fontCallback != null) {
                                fontCallback.callbackFailAsync(-3);
                            }
                        } else {
                            typefaceCreateFromResourcesFamilyXml = TypefaceCompat.createFromResourcesFamilyXml(context, familyResourceEntry, resources, i, string, typedValue.assetCookie, i2, fontCallback, z);
                        }
                    } else {
                        Typeface typefaceCreateFromResourcesFontFile = TypefaceCompat.createFromResourcesFontFile(resources, i, string, typedValue.assetCookie, i2);
                        if (fontCallback != null) {
                            if (typefaceCreateFromResourcesFontFile != null) {
                                new Handler(Looper.getMainLooper()).post(new ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(fontCallback, typefaceCreateFromResourcesFontFile));
                            } else {
                                fontCallback.callbackFailAsync(-3);
                            }
                        }
                        typefaceCreateFromResourcesFamilyXml = typefaceCreateFromResourcesFontFile;
                    }
                } catch (IOException e) {
                    Log.e("ResourcesCompat", "Failed to read xml resource ".concat(string), e);
                    if (fontCallback != null) {
                        fontCallback.callbackFailAsync(-3);
                    }
                    if (typefaceCreateFromResourcesFamilyXml == null) {
                    }
                    return typefaceCreateFromResourcesFamilyXml;
                } catch (XmlPullParserException e2) {
                    Log.e("ResourcesCompat", "Failed to parse xml resource ".concat(string), e2);
                    if (fontCallback != null) {
                    }
                    if (typefaceCreateFromResourcesFamilyXml == null) {
                    }
                    return typefaceCreateFromResourcesFamilyXml;
                }
            }
        } else if (fontCallback != null) {
            fontCallback.callbackFailAsync(-3);
        }
        if (typefaceCreateFromResourcesFamilyXml == null || fontCallback != null || z2) {
            return typefaceCreateFromResourcesFamilyXml;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
    }
}
