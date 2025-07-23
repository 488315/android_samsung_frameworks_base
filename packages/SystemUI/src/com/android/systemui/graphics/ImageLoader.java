package com.android.systemui.graphics;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Trace;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.graphics.ImageLoader;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ImageLoader {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context defaultContext;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$configureDecoderForMaximumSize(Companion companion, ImageDecoder imageDecoder, Size size, int i, int i2) {
            companion.getClass();
            if (i == 0 && i2 == 0) {
                return;
            }
            if (size.getWidth() > i || size.getHeight() > i2) {
                float min = Math.min(i <= 0 ? 1.0f : i / size.getWidth(), i2 <= 0 ? 1.0f : i2 / size.getHeight());
                if (min < 1.0f) {
                    int width = (int) (size.getWidth() * min);
                    int height = (int) (size.getHeight() * min);
                    if (Log.isLoggable("ImageLoader", 3)) {
                        SuggestionsAdapter$$ExternalSyntheticOutline0.m(width, height, "Configured image size to ", " x ", "ImageLoader");
                    }
                    imageDecoder.setTargetSize(width, height);
                }
            }
        }

        public static final Resources access$resolveResourcesForIcon(Companion companion, Context context, Icon icon) {
            companion.getClass();
            if (icon.getType() != 2) {
                return null;
            }
            Resources resources = icon.getResources();
            if (resources != null) {
                return resources;
            }
            String resPackage = icon.getResPackage();
            if (resPackage.length() == 0 || context.getPackageName().equals(resPackage)) {
                return context.getResources();
            }
            if ("android".equals(resPackage)) {
                return Resources.getSystem();
            }
            PackageManager packageManager = context.getPackageManager();
            try {
                return packageManager.getResourcesForApplication(packageManager.getApplicationInfo(resPackage, 9216));
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("ImageLoader", "Failed to resolve resource package", e);
                return null;
            }
        }

        public static final ImageDecoder.Source access$toImageDecoderSource(Companion companion, Source source, Context context) {
            ImageDecoder.Source createSource;
            companion.getClass();
            if (source instanceof Res) {
                Res res = (Res) source;
                Context context2 = res.context;
                if (context2 != null) {
                    context = context2;
                }
                createSource = ImageDecoder.createSource(context.getResources(), res.resId);
            } else if (source instanceof File) {
                createSource = ImageDecoder.createSource(((File) source).file);
            } else if (source instanceof Uri) {
                createSource = ImageDecoder.createSource(context.getContentResolver(), ((Uri) source).uri);
            } else {
                if (!(source instanceof InputStream)) {
                    throw new NoWhenBranchMatchedException();
                }
                InputStream inputStream = (InputStream) source;
                Context context3 = inputStream.context;
                if (context3 != null) {
                    context = context3;
                }
                createSource = ImageDecoder.createSource(context.getResources(), inputStream.inputStream);
            }
            createSource.getClass();
            return createSource;
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class File implements Source {
        public final java.io.File file;

        public File(java.io.File file) {
            this.file = file;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof File) && Intrinsics.areEqual(this.file, ((File) obj).file);
        }

        public final int hashCode() {
            return this.file.hashCode();
        }

        public final String toString() {
            return "File(file=" + this.file + ")";
        }

        public File(String str) {
            this(new java.io.File(str));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InputStream implements Source {
        public final Context context;
        public final java.io.InputStream inputStream;

        public InputStream(java.io.InputStream inputStream, Context context) {
            this.inputStream = inputStream;
            this.context = context;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InputStream)) {
                return false;
            }
            InputStream inputStream = (InputStream) obj;
            return Intrinsics.areEqual(this.inputStream, inputStream.inputStream) && Intrinsics.areEqual(this.context, inputStream.context);
        }

        public final int hashCode() {
            int hashCode = this.inputStream.hashCode() * 31;
            Context context = this.context;
            return hashCode + (context == null ? 0 : context.hashCode());
        }

        public final String toString() {
            return "InputStream(inputStream=" + this.inputStream + ", context=" + this.context + ")";
        }

        public InputStream(java.io.InputStream inputStream) {
            this(inputStream, null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Res implements Source {
        public final Context context;
        public final int resId;

        public Res(int i, Context context) {
            this.resId = i;
            this.context = context;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Res)) {
                return false;
            }
            Res res = (Res) obj;
            return this.resId == res.resId && Intrinsics.areEqual(this.context, res.context);
        }

        public final int hashCode() {
            int hashCode = Integer.hashCode(this.resId) * 31;
            Context context = this.context;
            return hashCode + (context == null ? 0 : context.hashCode());
        }

        public final String toString() {
            return "Res(resId=" + this.resId + ", context=" + this.context + ")";
        }

        public Res(int i) {
            this(i, null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Source {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Uri implements Source {
        public final android.net.Uri uri;

        public Uri(android.net.Uri uri) {
            this.uri = uri;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Uri) && Intrinsics.areEqual(this.uri, ((Uri) obj).uri);
        }

        public final int hashCode() {
            return this.uri.hashCode();
        }

        public final String toString() {
            return "Uri(uri=" + this.uri + ")";
        }

        public Uri(String str) {
            this(android.net.Uri.parse(str));
        }
    }

    public ImageLoader(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.defaultContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public static Bitmap loadBitmapSync(ImageDecoder.Source source, final int i, final int i2, final int i3) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("ImageLoader#loadBitmap");
        }
        try {
            try {
                Bitmap decodeBitmap = ImageDecoder.decodeBitmap(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.graphics.ImageLoader$loadBitmapSync$1$1
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                        ImageLoader.Companion.access$configureDecoderForMaximumSize(ImageLoader.Companion, imageDecoder, imageInfo.getSize(), i, i2);
                        imageDecoder.setAllocator(i3);
                    }
                });
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return decodeBitmap;
            } catch (Exception e) {
                Log.w("ImageLoader", "Failed to load source " + source, e);
                if (!isEnabled) {
                    return null;
                }
                TraceUtilsKt.endSlice();
                return null;
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public static Object loadDrawable$default(ImageLoader imageLoader, Icon icon, Continuation continuation) {
        Context context = imageLoader.defaultContext;
        imageLoader.getClass();
        return BuildersKt.withContext(imageLoader.backgroundDispatcher, new ImageLoader$loadDrawable$4(imageLoader, icon, context, 200, 200, 0, null), continuation);
    }

    public static Drawable loadDrawableSync(ImageDecoder.Source source, final int i, final int i2, final int i3) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("ImageLoader#loadDrawable");
        }
        try {
            try {
                Drawable decodeDrawable = ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.graphics.ImageLoader$loadDrawableSync$2$1
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                        ImageLoader.Companion.access$configureDecoderForMaximumSize(ImageLoader.Companion, imageDecoder, imageInfo.getSize(), i, i2);
                        imageDecoder.setAllocator(i3);
                    }
                });
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return decodeDrawable;
            } catch (Exception e) {
                Log.w("ImageLoader", "Failed to load source " + source, e);
                if (!isEnabled) {
                    return null;
                }
                TraceUtilsKt.endSlice();
                return null;
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final Object loadBitmap(Uri uri, int i, int i2, ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new ImageLoader$loadBitmap$2(this, uri, i, i2, 1, null), continuationImpl);
    }

    public static Drawable loadDrawableSync(Icon icon, Context context, int i, int i2, int i3) {
        Drawable bitmapDrawable;
        Drawable loadDrawable;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("ImageLoader#loadDrawable");
        }
        try {
            int type = icon.getType();
            Drawable drawable = null;
            Companion companion = Companion;
            switch (type) {
                case 1:
                    bitmapDrawable = new BitmapDrawable(context.getResources(), icon.getBitmap());
                    loadDrawable = bitmapDrawable;
                    break;
                case 2:
                    Resources access$resolveResourcesForIcon = Companion.access$resolveResourcesForIcon(companion, context, icon);
                    if (access$resolveResourcesForIcon == null || (bitmapDrawable = loadDrawableSync(ImageDecoder.createSource(access$resolveResourcesForIcon, icon.getResId()), i, i2, i3)) == null) {
                        loadDrawable = icon.loadDrawable(context);
                        if (loadDrawable == null) {
                            Log.w("ImageLoader", "Failed to load drawable for " + icon);
                            loadDrawable = null;
                            break;
                        } else {
                            break;
                        }
                    }
                    loadDrawable = bitmapDrawable;
                    break;
                case 3:
                    loadDrawable = loadDrawableSync(ImageDecoder.createSource(icon.getDataBytes(), icon.getDataOffset(), icon.getDataLength()), i, i2, i3);
                    break;
                case 4:
                case 6:
                    loadDrawable = loadDrawableSync(ImageDecoder.createSource(context.getContentResolver(), icon.getUri()), i, i2, i3);
                    break;
                case 5:
                    bitmapDrawable = new AdaptiveIconDrawable(null, new BitmapDrawable(context.getResources(), icon.getBitmap()));
                    loadDrawable = bitmapDrawable;
                    break;
                default:
                    loadDrawable = icon.loadDrawable(context);
                    if (loadDrawable == null) {
                        Log.w("ImageLoader", "Failed to load drawable for " + icon);
                        loadDrawable = null;
                        break;
                    } else {
                        break;
                    }
            }
            if (loadDrawable != null) {
                companion.getClass();
                if (icon.hasTint()) {
                    loadDrawable.mutate();
                    loadDrawable.setTintList(icon.getTintList());
                    loadDrawable.setTintBlendMode(icon.getTintBlendMode());
                }
                drawable = loadDrawable;
            }
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            return drawable;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
