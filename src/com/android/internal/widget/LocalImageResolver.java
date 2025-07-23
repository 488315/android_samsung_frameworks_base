package com.android.internal.widget;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import java.io.IOException;

/* loaded from: classes6.dex */
public class LocalImageResolver {
    static final int DEFAULT_MAX_SAFE_ICON_SIZE_PX = 480;
    public static final int NO_MAX_SIZE = -1;
    private static final String TAG = "LocalImageResolver";

    public static Drawable resolveImage(Uri uri, Context context) throws IOException {
        try {
            return ImageDecoder.decodeDrawable(ImageDecoder.createSource(context.getContentResolver(), uri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.internal.widget.LocalImageResolver$$ExternalSyntheticLambda1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    LocalImageResolver.onHeaderDecoded(imageDecoder, imageInfo, 480, 480);
                }
            });
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static Drawable resolveImage(Icon icon, Context context) throws IOException {
        return resolveImage(icon, context, 480, 480);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0017, code lost:
    
        if (r2 != 6) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable resolveImage(android.graphics.drawable.Icon r4, android.content.Context r5, int r6, int r7) {
        /*
            java.lang.String r0 = "Couldn't load drawable for icon: "
            r1 = 0
            if (r4 != 0) goto L6
            return r1
        L6:
            int r2 = r4.getType()
            r3 = 1
            if (r2 == r3) goto L5e
            r3 = 2
            if (r2 == r3) goto L2b
            r3 = 4
            if (r2 == r3) goto L1a
            r3 = 5
            if (r2 == r3) goto L5e
            r3 = 6
            if (r2 == r3) goto L1a
            goto L45
        L1a:
            android.net.Uri r2 = getResolvableUri(r4)
            if (r2 == 0) goto L45
            android.graphics.drawable.Drawable r6 = resolveImage(r2, r5, r6, r7)
            if (r6 == 0) goto L45
            android.graphics.drawable.Drawable r4 = tintDrawable(r4, r6)
            return r4
        L2b:
            android.content.res.Resources r2 = resolveResourcesForIcon(r5, r4)
            if (r2 != 0) goto L36
            android.graphics.drawable.Drawable r4 = r4.loadDrawable(r5)
            return r4
        L36:
            int r3 = r4.getResId()
            android.graphics.drawable.Drawable r6 = resolveImage(r2, r3, r6, r7)
            if (r6 == 0) goto L45
            android.graphics.drawable.Drawable r4 = tintDrawable(r4, r6)
            return r4
        L45:
            android.graphics.drawable.Drawable r5 = r4.loadDrawable(r5)     // Catch: android.content.res.Resources.NotFoundException -> L5d
            if (r5 != 0) goto L5c
            java.lang.String r6 = "LocalImageResolver"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: android.content.res.Resources.NotFoundException -> L5d
            r7.<init>(r0)     // Catch: android.content.res.Resources.NotFoundException -> L5d
            r7.append(r4)     // Catch: android.content.res.Resources.NotFoundException -> L5d
            java.lang.String r4 = r7.toString()     // Catch: android.content.res.Resources.NotFoundException -> L5d
            android.util.Log.w(r6, r4)     // Catch: android.content.res.Resources.NotFoundException -> L5d
        L5c:
            return r5
        L5d:
            return r1
        L5e:
            android.graphics.drawable.Drawable r4 = resolveBitmapImage(r4, r5, r6, r7)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.LocalImageResolver.resolveImage(android.graphics.drawable.Icon, android.content.Context, int, int):android.graphics.drawable.Drawable");
    }

    public static Drawable resolveImage(Uri uri, Context context, int i, int i2) {
        return resolveImage(ImageDecoder.createSource(context.getContentResolver(), uri), i, i2);
    }

    public static Drawable resolveImage(int i, Context context, int i2, int i3) {
        return resolveImage(ImageDecoder.createSource(context.getResources(), i), i2, i3);
    }

    private static Drawable resolveImage(Resources resources, int i, int i2, int i3) {
        return resolveImage(ImageDecoder.createSource(resources, i), i2, i3);
    }

    private static Drawable resolveBitmapImage(Icon icon, Context context, int i, int i2) {
        if (i > 0 && i2 > 0) {
            Bitmap bitmap = icon.getBitmap();
            if (bitmap == null) {
                return null;
            }
            if (bitmap.getWidth() > i || bitmap.getHeight() > i2) {
                Icon createWithAdaptiveBitmap = icon.getType() == 5 ? Icon.createWithAdaptiveBitmap(bitmap) : Icon.createWithBitmap(bitmap);
                createWithAdaptiveBitmap.setTintList(icon.getTintList()).setTintBlendMode(icon.getTintBlendMode()).scaleDownIfNecessary(i, i2);
                return createWithAdaptiveBitmap.loadDrawable(context);
            }
        }
        return icon.loadDrawable(context);
    }

    private static Drawable tintDrawable(Icon icon, Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (icon.hasTint()) {
            drawable.mutate();
            drawable.setTintList(icon.getTintList());
            drawable.setTintBlendMode(icon.getTintBlendMode());
        }
        return drawable;
    }

    private static Drawable resolveImage(ImageDecoder.Source source, final int i, final int i2) {
        try {
            return ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.internal.widget.LocalImageResolver$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                    LocalImageResolver.lambda$resolveImage$1(i, i2, imageDecoder, imageInfo, source2);
                }
            });
        } catch (Resources.NotFoundException | IOException unused) {
            Log.d(TAG, "Couldn't use ImageDecoder for drawable, falling back to non-resized load.");
            return null;
        }
    }

    static /* synthetic */ void lambda$resolveImage$1(int i, int i2, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        Size size = imageInfo.getSize();
        if (size.getWidth() > i || size.getHeight() > i2) {
            if (size.getWidth() > size.getHeight()) {
                if (size.getWidth() > i) {
                    imageDecoder.setTargetSize(i, (size.getHeight() * i) / size.getWidth());
                }
            } else if (size.getHeight() > i2) {
                imageDecoder.setTargetSize((size.getWidth() * i2) / size.getHeight(), i2);
            }
        }
    }

    private static int getPowerOfTwoForSampleRatio(double d) {
        return Math.max(1, Integer.highestOneBit((int) Math.floor(d)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, int i, int i2) {
        Size size = imageInfo.getSize();
        imageDecoder.setTargetSampleSize(getPowerOfTwoForSampleRatio(Math.max(size.getHeight(), size.getWidth()) > Math.max(i, i2) ? (r2 * 1.0f) / r3 : 1.0d));
    }

    private static Uri getResolvableUri(Icon icon) {
        if (icon == null) {
            return null;
        }
        if (icon.getType() == 4 || icon.getType() == 6) {
            return icon.getUri();
        }
        return null;
    }

    public static Resources resolveResourcesForIcon(Context context, Icon icon) {
        if (icon.getType() != 2) {
            return null;
        }
        Resources resources = icon.getResources();
        if (resources != null) {
            return resources;
        }
        String resPackage = icon.getResPackage();
        if (TextUtils.isEmpty(resPackage) || context.getPackageName().equals(resPackage)) {
            return context.getResources();
        }
        if ("android".equals(resPackage)) {
            return Resources.getSystem();
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resPackage, 9216);
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo);
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, String.format("Unable to resolve package %s for icon %s", resPackage, icon));
            return null;
        }
    }
}
