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
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import java.io.IOException;
import java.util.Objects;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ResourcesCompat {
    public static final ThreadLocal sTempTypedValue = new ThreadLocal();
    public static final WeakHashMap sColorStateCaches = new WeakHashMap(0);
    public static final Object sColorStateCacheLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ColorStateListCacheEntry {
        public final Configuration mConfiguration;
        public final int mThemeHash;
        public final ColorStateList mValue;

        public ColorStateListCacheEntry(ColorStateList colorStateList, Configuration configuration, Resources.Theme theme) {
            this.mValue = colorStateList;
            this.mConfiguration = new Configuration(configuration);
            this.mThemeHash = theme == null ? 0 : theme.hashCode();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (obj == null || ColorStateListCacheKey.class != obj.getClass()) {
                return false;
            }
            ColorStateListCacheKey colorStateListCacheKey = (ColorStateListCacheKey) obj;
            return this.mResources.equals(colorStateListCacheKey.mResources) && Objects.equals(this.mTheme, colorStateListCacheKey.mTheme);
        }

        public final int hashCode() {
            return Objects.hash(this.mResources, this.mTheme);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class FontCallback {
        public final void callbackFailAsync(final int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ResourcesCompat.FontCallback.this.onFontRetrievalFailed(i);
                }
            });
        }

        public final void callbackSuccessAsync(final Typeface typeface) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ResourcesCompat.FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }

        public abstract void onFontRetrievalFailed(int i);

        public abstract void onFontRetrieved(Typeface typeface);
    }

    private ResourcesCompat() {
    }

    public static ColorStateList getColorStateList(Resources resources, int i, Resources.Theme theme) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateListCacheEntry colorStateListCacheEntry;
        Resources.Theme theme2;
        ColorStateListCacheKey colorStateListCacheKey = new ColorStateListCacheKey(resources, theme);
        synchronized (sColorStateCacheLock) {
            SparseArray sparseArray = (SparseArray) sColorStateCaches.get(colorStateListCacheKey);
            colorStateList = null;
            if (sparseArray != null && sparseArray.size() > 0 && (colorStateListCacheEntry = (ColorStateListCacheEntry) sparseArray.get(i)) != null) {
                if (!colorStateListCacheEntry.mConfiguration.equals(colorStateListCacheKey.mResources.getConfiguration()) || (!((theme2 = colorStateListCacheKey.mTheme) == null && colorStateListCacheEntry.mThemeHash == 0) && (theme2 == null || colorStateListCacheEntry.mThemeHash != theme2.hashCode()))) {
                    sparseArray.remove(i);
                } else {
                    colorStateList2 = colorStateListCacheEntry.mValue;
                }
            }
            colorStateList2 = null;
        }
        if (colorStateList2 != null) {
            return colorStateList2;
        }
        ThreadLocal threadLocal = sTempTypedValue;
        TypedValue typedValue = (TypedValue) threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.type;
        if (!(i2 >= 28 && i2 <= 31)) {
            try {
                colorStateList = ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i), theme);
            } catch (Exception e) {
                Log.w("ResourcesCompat", "Failed to inflate ColorStateList, leaving it to the framework", e);
            }
        }
        if (colorStateList == null) {
            return resources.getColorStateList(i, theme);
        }
        synchronized (sColorStateCacheLock) {
            WeakHashMap weakHashMap = sColorStateCaches;
            SparseArray sparseArray2 = (SparseArray) weakHashMap.get(colorStateListCacheKey);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                weakHashMap.put(colorStateListCacheKey, sparseArray2);
            }
            sparseArray2.append(i, new ColorStateListCacheEntry(colorStateList, colorStateListCacheKey.mResources.getConfiguration(), theme));
        }
        return colorStateList;
    }

    public static Typeface getFont(int i, Context context) {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, new TypedValue(), 0, null, false, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00a8 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Typeface loadFont(Context context, int i, TypedValue typedValue, int i2, FontCallback fontCallback, boolean z, boolean z2) {
        Typeface typeface;
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String charSequence2 = charSequence.toString();
        if (charSequence2.startsWith("res/")) {
            typeface = (Typeface) TypefaceCompat.sTypefaceCache.get(TypefaceCompat.createResourceUid(resources, i, charSequence2, typedValue.assetCookie, i2));
            if (typeface != null) {
                if (fontCallback != null) {
                    fontCallback.callbackSuccessAsync(typeface);
                }
            } else if (!z2) {
                try {
                    if (charSequence2.toLowerCase().endsWith(".xml")) {
                        FontResourcesParserCompat.FamilyResourceEntry parse = FontResourcesParserCompat.parse(resources.getXml(i), resources);
                        if (parse == null) {
                            Log.e("ResourcesCompat", "Failed to find font-family tag");
                            if (fontCallback != null) {
                                fontCallback.callbackFailAsync(-3);
                            }
                        } else {
                            typeface = TypefaceCompat.createFromResourcesFamilyXml(context, parse, resources, i, charSequence2, typedValue.assetCookie, i2, fontCallback, z);
                        }
                    } else {
                        typeface = TypefaceCompat.createFromResourcesFontFile(resources, i, charSequence2, typedValue.assetCookie, i2);
                        if (fontCallback != null) {
                            if (typeface != null) {
                                fontCallback.callbackSuccessAsync(typeface);
                            } else {
                                fontCallback.callbackFailAsync(-3);
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.e("ResourcesCompat", "Failed to read xml resource ".concat(charSequence2), e);
                    if (fontCallback != null) {
                        fontCallback.callbackFailAsync(-3);
                    }
                    typeface = null;
                    if (typeface == null) {
                    }
                    return typeface;
                } catch (XmlPullParserException e2) {
                    Log.e("ResourcesCompat", "Failed to parse xml resource ".concat(charSequence2), e2);
                    if (fontCallback != null) {
                    }
                    typeface = null;
                    if (typeface == null) {
                    }
                    return typeface;
                }
            }
            if (typeface == null || fontCallback != null || z2) {
                return typeface;
            }
            throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
        }
        if (fontCallback != null) {
            fontCallback.callbackFailAsync(-3);
        }
        typeface = null;
        if (typeface == null) {
        }
        return typeface;
    }
}
