package com.android.server.power.shutdown;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.util.Slog;
import android.view.View;
import android.widget.ImageView;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class PngPlayer extends AnimationPlayer implements PlayerInterface.ViewSizeListener {
    public Handler drawHandler;
    public Pair mainAnimationWidthHeight;
    public Bitmap mainBitmap;
    public Bitmap subBitmap;

    /* renamed from: com.android.server.power.shutdown.PngPlayer$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnLayoutChangeListener {
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(
                View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {}
    }

    public static Bitmap getBitmap(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static void setImageToView(ImageView imageView, Bitmap bitmap) {
        Locale locale = Locale.ENGLISH;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                bitmap.getByteCount(), "setImageToView bitmapSize[", "]", "Shutdown-PngPlayer");
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(bitmap);
    }

    public final synchronized Handler getDrawHandler() {
        try {
            if (this.drawHandler == null) {
                this.drawHandler = new Handler(Looper.getMainLooper());
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.drawHandler;
    }

    @Override // com.android.server.power.shutdown.AnimationPlayer
    public final Pair getMainAnimationWidthHeight() {
        if (this.mainAnimationWidthHeight == null) {
            if (this.mainBitmap == null) {
                prepare();
            }
            Bitmap bitmap = this.mainBitmap;
            if (bitmap != null) {
                this.mainAnimationWidthHeight =
                        Pair.create(
                                Integer.valueOf(bitmap.getWidth()),
                                Integer.valueOf(this.mainBitmap.getHeight()));
            }
            Slog.i(
                    "Shutdown-PngPlayer",
                    String.format(
                            Locale.ENGLISH,
                            "getMainAnimationWidthHeight unexpected flow %s",
                            this.mainAnimationWidthHeight));
        }
        return this.mainAnimationWidthHeight;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final boolean isPlaying() {
        return false;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface.ViewSizeListener
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        final Bitmap bitmap;
        Locale locale = Locale.ENGLISH;
        Slog.d(
                "Shutdown-PngPlayer",
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        i3,
                        i4,
                        ", oldHeight=",
                        ",",
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i, i2, "onSizeChanged width=", ", height=", ", oldWidth=")));
        if (i == 0 || i2 == 0 || this.subBitmap == null) {
            Slog.d(
                    "Shutdown-PngPlayer",
                    "getMatchedAnimationLoader viewWidth or viewHeight is 0 or subAnimationLoaders"
                        + " is null "
                            + this.mainBitmap);
            bitmap = this.mainBitmap;
        } else {
            Bitmap bitmap2 = this.mainBitmap;
            int width = bitmap2 != null ? bitmap2.getWidth() : Integer.MAX_VALUE;
            Bitmap bitmap3 = this.mainBitmap;
            int height = bitmap3 != null ? bitmap3.getHeight() : Integer.MAX_VALUE;
            if (i == width && i2 == height) {
                ASKSManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        i2,
                        "getMatchedAnimationLoader main matched exactly width=",
                        ", height=",
                        "Shutdown-PngPlayer");
                bitmap = this.mainBitmap;
            } else {
                int width2 = this.subBitmap.getWidth();
                int height2 = this.subBitmap.getHeight();
                if (i == width2 && i2 == height2) {
                    ASKSManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "getMatchedAnimationLoader sub matched exactly width=",
                            ", height=",
                            "Shutdown-PngPlayer");
                    bitmap = this.subBitmap;
                } else {
                    int i5 = i;
                    int i6 = i2;
                    while (i6 != 0) {
                        int i7 = i5 % i6;
                        i5 = i6;
                        i6 = i7;
                    }
                    int i8 = i5 > 0 ? i / i5 : i;
                    int i9 = i5 > 0 ? i2 / i5 : i2;
                    int i10 = width;
                    int i11 = height;
                    while (i11 != 0) {
                        int i12 = i10 % i11;
                        i10 = i11;
                        i11 = i12;
                    }
                    if (i10 > 0) {
                        width /= i10;
                    }
                    if (i10 > 0) {
                        height /= i10;
                    }
                    if (i8 == width && i9 == height) {
                        ASKSManagerService$$ExternalSyntheticOutline0.m(
                                i8,
                                i9,
                                "getMatchedAnimationLoader main matched ratio width=",
                                ", height=",
                                "Shutdown-PngPlayer");
                        bitmap = this.mainBitmap;
                    } else {
                        int i13 = width2;
                        int i14 = height2;
                        while (i14 != 0) {
                            int i15 = i13 % i14;
                            i13 = i14;
                            i14 = i15;
                        }
                        if (i13 > 0) {
                            width2 /= i13;
                        }
                        if (i13 > 0) {
                            height2 /= i13;
                        }
                        if (i8 == width2 && i9 == height2) {
                            ASKSManagerService$$ExternalSyntheticOutline0.m(
                                    i8,
                                    i9,
                                    "getMatchedAnimationLoader sub matched ratio width=",
                                    ", height=",
                                    "Shutdown-PngPlayer");
                            bitmap = this.subBitmap;
                        } else {
                            ASKSManagerService$$ExternalSyntheticOutline0.m(
                                    i,
                                    i2,
                                    "getMatchedAnimationLoader not found matched resouce"
                                        + " viewWidth=",
                                    ", viewHeight=",
                                    "Shutdown-PngPlayer");
                            bitmap = this.mainBitmap;
                        }
                    }
                }
            }
        }
        getDrawHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.power.shutdown.PngPlayer$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                PngPlayer pngPlayer = PngPlayer.this;
                                PngPlayer.setImageToView(pngPlayer.mainImageView, bitmap);
                            }
                        });
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void prepare() {
        File file;
        File file2;
        Slog.d("Shutdown-PngPlayer", "prepare");
        getDrawHandler();
        ResourceManager resourceManager = this.resourceManager;
        List list = resourceManager.mainImages;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            if (!arrayList.isEmpty()) {
                this.mainBitmap = getBitmap((File) arrayList.get(0));
            }
        }
        if (this.mainBitmap == null && (file2 = resourceManager.mainLoopImage) != null) {
            this.mainBitmap = getBitmap(file2);
        }
        List list2 = resourceManager.subImages;
        if (list2 != null) {
            ArrayList arrayList2 = (ArrayList) list2;
            if (!arrayList2.isEmpty()) {
                this.subBitmap = getBitmap((File) arrayList2.get(0));
            }
        }
        if (this.subBitmap != null || (file = resourceManager.subLoopImage) == null) {
            return;
        }
        this.subBitmap = getBitmap(file);
    }

    @Override // com.android.server.power.shutdown.AnimationPlayer
    public final void setView(ShutdownAnimatedImageView shutdownAnimatedImageView) {
        this.mainImageView = shutdownAnimatedImageView;
        shutdownAnimatedImageView.addOnLayoutChangeListener(new AnonymousClass1());
        shutdownAnimatedImageView.listener = this;
        this.subImageView = null;
        this.subDialog = null;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void start() {
        Locale locale = Locale.ENGLISH;
        Slog.d("Shutdown-PngPlayer", "start");
        setImageToView(this.mainImageView, this.mainBitmap);
    }
}
