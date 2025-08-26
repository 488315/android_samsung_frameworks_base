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

    /* JADX WARN: Removed duplicated region for block: B:16:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Drawable resolveImage(Icon icon, Context context, int i, int i2) throws IOException {
        Drawable drawableResolveImage;
        if (icon == null) {
            return null;
        }
        int type = icon.getType();
        if (type != 1) {
            if (type == 2) {
                Resources resourcesResolveResourcesForIcon = resolveResourcesForIcon(context, icon);
                if (resourcesResolveResourcesForIcon == null) {
                    return icon.loadDrawable(context);
                }
                Drawable drawableResolveImage2 = resolveImage(resourcesResolveResourcesForIcon, icon.getResId(), i, i2);
                if (drawableResolveImage2 != null) {
                    return tintDrawable(icon, drawableResolveImage2);
                }
            } else if (type == 4) {
                Uri resolvableUri = getResolvableUri(icon);
                if (resolvableUri != null && (drawableResolveImage = resolveImage(resolvableUri, context, i, i2)) != null) {
                    return tintDrawable(icon, drawableResolveImage);
                }
            } else if (type != 5) {
                if (type == 6) {
                }
            }
            try {
                Drawable drawableLoadDrawable = icon.loadDrawable(context);
                if (drawableLoadDrawable == null) {
                    Log.w(TAG, "Couldn't load drawable for icon: " + icon);
                }
                return drawableLoadDrawable;
            } catch (Resources.NotFoundException unused) {
                return null;
            }
        }
        return resolveBitmapImage(icon, context, i, i2);
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
                Icon iconCreateWithAdaptiveBitmap = icon.getType() == 5 ? Icon.createWithAdaptiveBitmap(bitmap) : Icon.createWithBitmap(bitmap);
                iconCreateWithAdaptiveBitmap.setTintList(icon.getTintList()).setTintBlendMode(icon.getTintBlendMode()).scaleDownIfNecessary(i, i2);
                return iconCreateWithAdaptiveBitmap.loadDrawable(context);
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
