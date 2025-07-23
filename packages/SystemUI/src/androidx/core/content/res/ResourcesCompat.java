package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import androidx.core.content.res.ResourcesCompat;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ResourcesCompat {
    public static final ThreadLocal sTempTypedValue = new ThreadLocal();
    public static final WeakHashMap sColorStateCaches = new WeakHashMap(0);
    public static final Object sColorStateCacheLock = new Object();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class FontCallback {
        public final void callbackFailAsync(final int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ResourcesCompat.FontCallback.this.onFontRetrievalFailed(i);
                }
            });
        }

        public abstract void onFontRetrievalFailed(int i);

        public abstract void onFontRetrieved(Typeface typeface);
    }

    private ResourcesCompat() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0043, code lost:
    
        if (r4.mThemeHash == r5.hashCode()) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.res.ColorStateList getColorStateList(int r7, android.content.res.Resources.Theme r8, android.content.res.Resources r9) {
        /*
            androidx.core.content.res.ResourcesCompat$ColorStateListCacheKey r0 = new androidx.core.content.res.ResourcesCompat$ColorStateListCacheKey
            r0.<init>(r9, r8)
            java.lang.Object r1 = androidx.core.content.res.ResourcesCompat.sColorStateCacheLock
            monitor-enter(r1)
            java.util.WeakHashMap r2 = androidx.core.content.res.ResourcesCompat.sColorStateCaches     // Catch: java.lang.Throwable -> L38
            java.lang.Object r2 = r2.get(r0)     // Catch: java.lang.Throwable -> L38
            android.util.SparseArray r2 = (android.util.SparseArray) r2     // Catch: java.lang.Throwable -> L38
            r3 = 0
            if (r2 == 0) goto L4c
            int r4 = r2.size()     // Catch: java.lang.Throwable -> L38
            if (r4 <= 0) goto L4c
            java.lang.Object r4 = r2.get(r7)     // Catch: java.lang.Throwable -> L38
            androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry r4 = (androidx.core.content.res.ResourcesCompat.ColorStateListCacheEntry) r4     // Catch: java.lang.Throwable -> L38
            if (r4 == 0) goto L4c
            android.content.res.Configuration r5 = r4.mConfiguration     // Catch: java.lang.Throwable -> L38
            android.content.res.Resources r6 = r0.mResources     // Catch: java.lang.Throwable -> L38
            android.content.res.Configuration r6 = r6.getConfiguration()     // Catch: java.lang.Throwable -> L38
            boolean r5 = r5.equals(r6)     // Catch: java.lang.Throwable -> L38
            if (r5 == 0) goto L49
            android.content.res.Resources$Theme r5 = r0.mTheme     // Catch: java.lang.Throwable -> L38
            if (r5 != 0) goto L3b
            int r6 = r4.mThemeHash     // Catch: java.lang.Throwable -> L38
            if (r6 == 0) goto L45
            goto L3b
        L38:
            r7 = move-exception
            goto Lb4
        L3b:
            if (r5 == 0) goto L49
            int r6 = r4.mThemeHash     // Catch: java.lang.Throwable -> L38
            int r5 = r5.hashCode()     // Catch: java.lang.Throwable -> L38
            if (r6 != r5) goto L49
        L45:
            android.content.res.ColorStateList r2 = r4.mValue     // Catch: java.lang.Throwable -> L38
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L38
            goto L4e
        L49:
            r2.remove(r7)     // Catch: java.lang.Throwable -> L38
        L4c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L38
            r2 = r3
        L4e:
            if (r2 == 0) goto L51
            return r2
        L51:
            java.lang.ThreadLocal r1 = androidx.core.content.res.ResourcesCompat.sTempTypedValue
            java.lang.Object r2 = r1.get()
            android.util.TypedValue r2 = (android.util.TypedValue) r2
            if (r2 != 0) goto L63
            android.util.TypedValue r2 = new android.util.TypedValue
            r2.<init>()
            r1.set(r2)
        L63:
            r1 = 1
            r9.getValue(r7, r2, r1)
            int r1 = r2.type
            r2 = 28
            if (r1 < r2) goto L72
            r2 = 31
            if (r1 > r2) goto L72
            goto L83
        L72:
            android.content.res.XmlResourceParser r1 = r9.getXml(r7)
            android.content.res.ColorStateList r3 = androidx.core.content.res.ColorStateListInflaterCompat.createFromXml(r9, r1, r8)     // Catch: java.lang.Exception -> L7b
            goto L83
        L7b:
            r1 = move-exception
            java.lang.String r2 = "ResourcesCompat"
            java.lang.String r4 = "Failed to inflate ColorStateList, leaving it to the framework"
            android.util.Log.w(r2, r4, r1)
        L83:
            if (r3 == 0) goto Laf
            java.lang.Object r1 = androidx.core.content.res.ResourcesCompat.sColorStateCacheLock
            monitor-enter(r1)
            java.util.WeakHashMap r9 = androidx.core.content.res.ResourcesCompat.sColorStateCaches     // Catch: java.lang.Throwable -> L9b
            java.lang.Object r2 = r9.get(r0)     // Catch: java.lang.Throwable -> L9b
            android.util.SparseArray r2 = (android.util.SparseArray) r2     // Catch: java.lang.Throwable -> L9b
            if (r2 != 0) goto L9d
            android.util.SparseArray r2 = new android.util.SparseArray     // Catch: java.lang.Throwable -> L9b
            r2.<init>()     // Catch: java.lang.Throwable -> L9b
            r9.put(r0, r2)     // Catch: java.lang.Throwable -> L9b
            goto L9d
        L9b:
            r7 = move-exception
            goto Lad
        L9d:
            androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry r9 = new androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry     // Catch: java.lang.Throwable -> L9b
            android.content.res.Resources r0 = r0.mResources     // Catch: java.lang.Throwable -> L9b
            android.content.res.Configuration r0 = r0.getConfiguration()     // Catch: java.lang.Throwable -> L9b
            r9.<init>(r3, r0, r8)     // Catch: java.lang.Throwable -> L9b
            r2.append(r7, r9)     // Catch: java.lang.Throwable -> L9b
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L9b
            return r3
        Lad:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L9b
            throw r7
        Laf:
            android.content.res.ColorStateList r7 = r9.getColorStateList(r7, r8)
            return r7
        Lb4:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L38
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.getColorStateList(int, android.content.res.Resources$Theme, android.content.res.Resources):android.content.res.ColorStateList");
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00bd A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Typeface loadFont(android.content.Context r12, int r13, android.util.TypedValue r14, int r15, androidx.core.content.res.ResourcesCompat.FontCallback r16, boolean r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.loadFont(android.content.Context, int, android.util.TypedValue, int, androidx.core.content.res.ResourcesCompat$FontCallback, boolean, boolean):android.graphics.Typeface");
    }
}
