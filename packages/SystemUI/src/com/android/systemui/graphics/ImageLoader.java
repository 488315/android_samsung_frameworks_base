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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ImageLoader {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context defaultContext;

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
                float fMin = Math.min(i <= 0 ? 1.0f : i / size.getWidth(), i2 <= 0 ? 1.0f : i2 / size.getHeight());
                if (fMin < 1.0f) {
                    int width = (int) (size.getWidth() * fMin);
                    int height = (int) (size.getHeight() * fMin);
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
            ImageDecoder.Source sourceCreateSource;
            companion.getClass();
            if (source instanceof Res) {
                Res res = (Res) source;
                Context context2 = res.context;
                if (context2 != null) {
                    context = context2;
                }
                sourceCreateSource = ImageDecoder.createSource(context.getResources(), res.resId);
            } else if (source instanceof File) {
                sourceCreateSource = ImageDecoder.createSource(((File) source).file);
            } else if (source instanceof Uri) {
                sourceCreateSource = ImageDecoder.createSource(context.getContentResolver(), ((Uri) source).uri);
            } else {
                if (!(source instanceof InputStream)) {
                    throw new NoWhenBranchMatchedException();
                }
                InputStream inputStream = (InputStream) source;
                Context context3 = inputStream.context;
                if (context3 != null) {
                    context = context3;
                }
                sourceCreateSource = ImageDecoder.createSource(context.getResources(), inputStream.inputStream);
            }
            sourceCreateSource.getClass();
            return sourceCreateSource;
        }

        private Companion() {
        }
    }

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
            int iHashCode = this.inputStream.hashCode() * 31;
            Context context = this.context;
            return iHashCode + (context == null ? 0 : context.hashCode());
        }

        public final String toString() {
            return "InputStream(inputStream=" + this.inputStream + ", context=" + this.context + ")";
        }

        public InputStream(java.io.InputStream inputStream) {
            this(inputStream, null);
        }
    }

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
            int iHashCode = Integer.hashCode(this.resId) * 31;
            Context context = this.context;
            return iHashCode + (context == null ? 0 : context.hashCode());
        }

        public final String toString() {
            return "Res(resId=" + this.resId + ", context=" + this.context + ")";
        }

        public Res(int i) {
            this(i, null);
        }
    }

    public interface Source {
    }

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

    /* renamed from: com.android.systemui.graphics.ImageLoader$loadBitmap$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $allocator;
        final /* synthetic */ int $maxHeight;
        final /* synthetic */ int $maxWidth;
        final /* synthetic */ Source $source;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Source source, int i, int i2, int i3, Continuation continuation) {
            super(2, continuation);
            this.$source = source;
            this.$maxWidth = i;
            this.$maxHeight = i2;
            this.$allocator = i3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ImageLoader.this.new AnonymousClass2(this.$source, this.$maxWidth, this.$maxHeight, this.$allocator, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ImageLoader imageLoader = ImageLoader.this;
            Source source = this.$source;
            int i = this.$maxWidth;
            int i2 = this.$maxHeight;
            int i3 = this.$allocator;
            imageLoader.getClass();
            try {
                return ImageLoader.loadBitmapSync(Companion.access$toImageDecoderSource(ImageLoader.Companion, source, imageLoader.defaultContext), i, i2, i3);
            } catch (Resources.NotFoundException e) {
                Log.w("ImageLoader", "Couldn't load resource " + source, e);
                return null;
            }
        }
    }

    public ImageLoader(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.defaultContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public static Bitmap loadBitmapSync(ImageDecoder.Source source, final int i, final int i2, final int i3) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ImageLoader#loadBitmap");
        }
        try {
            try {
                Bitmap bitmapDecodeBitmap = ImageDecoder.decodeBitmap(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.graphics.ImageLoader$loadBitmapSync$1$1
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                        ImageLoader.Companion.access$configureDecoderForMaximumSize(ImageLoader.Companion, imageDecoder, imageInfo.getSize(), i, i2);
                        imageDecoder.setAllocator(i3);
                    }
                });
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return bitmapDecodeBitmap;
            } catch (Exception e) {
                Log.w("ImageLoader", "Failed to load source " + source, e);
                if (!zIsEnabled) {
                    return null;
                }
                TraceUtilsKt.endSlice();
                return null;
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
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
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ImageLoader#loadDrawable");
        }
        try {
            try {
                Drawable drawableDecodeDrawable = ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.graphics.ImageLoader$loadDrawableSync$2$1
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                        ImageLoader.Companion.access$configureDecoderForMaximumSize(ImageLoader.Companion, imageDecoder, imageInfo.getSize(), i, i2);
                        imageDecoder.setAllocator(i3);
                    }
                });
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return drawableDecodeDrawable;
            } catch (Exception e) {
                Log.w("ImageLoader", "Failed to load source " + source, e);
                if (!zIsEnabled) {
                    return null;
                }
                TraceUtilsKt.endSlice();
                return null;
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final Object loadBitmap(Uri uri, int i, int i2, ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(uri, i, i2, 1, null), continuationImpl);
    }

    public static Drawable loadDrawableSync(Icon icon, Context context, int i, int i2, int i3) {
        Drawable bitmapDrawable;
        Drawable drawableLoadDrawable;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ImageLoader#loadDrawable");
        }
        try {
            int type = icon.getType();
            Drawable drawable = null;
            Companion companion = Companion;
            switch (type) {
                case 1:
                    bitmapDrawable = new BitmapDrawable(context.getResources(), icon.getBitmap());
                    drawableLoadDrawable = bitmapDrawable;
                    break;
                case 2:
                    Resources resourcesAccess$resolveResourcesForIcon = Companion.access$resolveResourcesForIcon(companion, context, icon);
                    if (resourcesAccess$resolveResourcesForIcon != null && (bitmapDrawable = loadDrawableSync(ImageDecoder.createSource(resourcesAccess$resolveResourcesForIcon, icon.getResId()), i, i2, i3)) != null) {
                        drawableLoadDrawable = bitmapDrawable;
                        break;
                    } else {
                        drawableLoadDrawable = icon.loadDrawable(context);
                        if (drawableLoadDrawable == null) {
                            Log.w("ImageLoader", "Failed to load drawable for " + icon);
                            drawableLoadDrawable = null;
                            break;
                        } else {
                            break;
                        }
                    }
                    break;
                case 3:
                    drawableLoadDrawable = loadDrawableSync(ImageDecoder.createSource(icon.getDataBytes(), icon.getDataOffset(), icon.getDataLength()), i, i2, i3);
                    break;
                case 4:
                case 6:
                    drawableLoadDrawable = loadDrawableSync(ImageDecoder.createSource(context.getContentResolver(), icon.getUri()), i, i2, i3);
                    break;
                case 5:
                    bitmapDrawable = new AdaptiveIconDrawable(null, new BitmapDrawable(context.getResources(), icon.getBitmap()));
                    drawableLoadDrawable = bitmapDrawable;
                    break;
                default:
                    drawableLoadDrawable = icon.loadDrawable(context);
                    if (drawableLoadDrawable == null) {
                        Log.w("ImageLoader", "Failed to load drawable for " + icon);
                        drawableLoadDrawable = null;
                        break;
                    } else {
                        break;
                    }
            }
            if (drawableLoadDrawable != null) {
                companion.getClass();
                if (icon.hasTint()) {
                    drawableLoadDrawable.mutate();
                    drawableLoadDrawable.setTintList(icon.getTintList());
                    drawableLoadDrawable.setTintBlendMode(icon.getTintBlendMode());
                }
                drawable = drawableLoadDrawable;
            }
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return drawable;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
