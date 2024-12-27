package com.android.server.power.shutdown;

import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.view.SemWindowManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WebpPlayer extends AnimationPlayer
        implements PlayerInterface.ViewSizeListener, SemWindowManager.FoldStateListener {
    public AnimationLoader currentAnimationLoader;
    public Handler drawHandler;
    public List mainAnimationLoaders;
    public Pair mainAnimationWidthHeight;
    public ExecutorService singleExecutorService;
    public List subAnimationLoaders;

    public static void channingAnimationLoaders(List list, List list2) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int i = 0;
        while (i < list.size()) {
            AnimationLoader animationLoader = (AnimationLoader) list.get(i);
            int i2 = i + 1;
            animationLoader.nextAnimation =
                    list.size() <= i2 ? null : (AnimationLoader) list.get(i2);
            if (list2 != null && !list2.isEmpty()) {
                animationLoader.pairAnimation =
                        list2.size() > i ? (AnimationLoader) list2.get(i) : null;
            }
            i = i2;
        }
    }

    public final Handler getHandler() {
        if (this.drawHandler == null) {
            this.drawHandler = new Handler(Looper.myLooper());
        }
        return this.drawHandler;
    }

    @Override // com.android.server.power.shutdown.AnimationPlayer
    public final Pair getMainAnimationWidthHeight() {
        if (this.mainAnimationWidthHeight == null) {
            File file = (File) ((ArrayList) this.resourceManager.mainImages).get(0);
            AnimatedImageDrawable animatedImageDrawable =
                    file != null
                            ? (AnimatedImageDrawable)
                                    AnimatedImageDrawable.createFromPath(file.getAbsolutePath())
                            : null;
            if (animatedImageDrawable != null) {
                this.mainAnimationWidthHeight =
                        Pair.create(
                                Integer.valueOf(animatedImageDrawable.getIntrinsicWidth()),
                                Integer.valueOf(animatedImageDrawable.getIntrinsicHeight()));
            } else {
                Display display = this.context.getDisplay();
                this.mainAnimationWidthHeight =
                        Pair.create(
                                Integer.valueOf(display.getWidth()),
                                Integer.valueOf(display.getHeight()));
            }
            Slog.i(
                    "Shutdown-WebpPlayer",
                    String.format(
                            Locale.ENGLISH,
                            "getMainAnimationWidthHeight unexpected flow %s",
                            this.mainAnimationWidthHeight));
        }
        return this.mainAnimationWidthHeight;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final boolean isPlaying() {
        List list = this.mainAnimationLoaders;
        AnimationStatus animationStatus = AnimationStatus.START;
        AnimationType animationType = AnimationType.SUB_LOOP;
        AnimationType animationType2 = AnimationType.MAIN_LOOP;
        if (list != null && !((ArrayList) list).isEmpty()) {
            Iterator it = ((ArrayList) this.mainAnimationLoaders).iterator();
            while (it.hasNext()) {
                AnimationLoader animationLoader = (AnimationLoader) it.next();
                AnimationType animationType3 = animationLoader.animationType;
                animationType3.getClass();
                if (animationType3 != animationType2 && animationType3 != animationType) {
                    AnimationStatus animationStatus2 = animationLoader.status;
                    animationStatus2.getClass();
                    if (animationStatus2 == animationStatus) {
                        return true;
                    }
                }
            }
        }
        List list2 = this.subAnimationLoaders;
        if (list2 == null || ((ArrayList) list2).isEmpty()) {
            return false;
        }
        Iterator it2 = ((ArrayList) this.subAnimationLoaders).iterator();
        while (it2.hasNext()) {
            AnimationLoader animationLoader2 = (AnimationLoader) it2.next();
            AnimationType animationType4 = animationLoader2.animationType;
            animationType4.getClass();
            if (animationType4 != animationType2 && animationType4 != animationType) {
                AnimationStatus animationStatus3 = animationLoader2.status;
                animationStatus3.getClass();
                if (animationStatus3 == animationStatus) {
                    return true;
                }
            }
        }
        return false;
    }

    public final List makeAnimationLoaders(List list, File file, AnimationType animationType) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size() + (file != null ? 1 : 0);
        Locale locale = Locale.ENGLISH;
        Slog.i("Shutdown-WebpPlayer", "makeAnimationLoaders, file count[" + size + "]");
        ArrayList arrayList2 = new ArrayList(size);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(new AnimationLoader((File) it.next(), 0, 5000L, animationType, this));
        }
        if (file != null) {
            arrayList2.add(
                    new AnimationLoader(
                            file,
                            -1,
                            0L,
                            animationType == AnimationType.MAIN
                                    ? AnimationType.MAIN_LOOP
                                    : AnimationType.SUB_LOOP,
                            this));
        }
        return arrayList2;
    }

    public final void onFoldStateChanged(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "onFoldStateChanged folded = ", "Shutdown-WebpPlayer", z);
    }

    @Override // com.android.server.power.shutdown.PlayerInterface.ViewSizeListener
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        Locale locale = Locale.ENGLISH;
        Slog.d(
                "Shutdown-WebpPlayer",
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        i3,
                        i4,
                        ", oldHeight=",
                        ",",
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i, i2, "onSizeChanged width=", ", height=", ", oldWidth=")));
    }

    public final void onTableModeChanged(boolean z) {
        Slog.v("Shutdown-WebpPlayer", "onTableModeChanged half_opened = " + z);
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void prepare() {
        Slog.d("Shutdown-WebpPlayer", "prepare");
        ResourceManager resourceManager = this.resourceManager;
        this.mainAnimationLoaders =
                makeAnimationLoaders(
                        resourceManager.mainImages,
                        resourceManager.mainLoopImage,
                        AnimationType.MAIN);
        this.subAnimationLoaders =
                makeAnimationLoaders(
                        resourceManager.subImages, resourceManager.subLoopImage, AnimationType.SUB);
        this.hasSubResources = !((ArrayList) r0).isEmpty();
        channingAnimationLoaders(this.subAnimationLoaders, this.mainAnimationLoaders);
        channingAnimationLoaders(this.mainAnimationLoaders, this.subAnimationLoaders);
        List list = this.mainAnimationLoaders;
        if (list != null && !((ArrayList) list).isEmpty()) {
            AnimationLoader animationLoader =
                    (AnimationLoader) ((ArrayList) this.mainAnimationLoaders).get(0);
            animationLoader.imageResolutionExtractor =
                    new Consumer() { // from class:
                                     // com.android.server.power.shutdown.WebpPlayer$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WebpPlayer webpPlayer = WebpPlayer.this;
                            AnimatedImageDrawable animatedImageDrawable =
                                    (AnimatedImageDrawable) obj;
                            webpPlayer.getClass();
                            webpPlayer.mainAnimationWidthHeight =
                                    Pair.create(
                                            Integer.valueOf(
                                                    animatedImageDrawable.getIntrinsicWidth()),
                                            Integer.valueOf(
                                                    animatedImageDrawable.getIntrinsicHeight()));
                        }
                    };
            animationLoader.prepare();
        }
        List list2 = this.subAnimationLoaders;
        if (list2 == null || ((ArrayList) list2).isEmpty()) {
            return;
        }
        ((AnimationLoader) ((ArrayList) this.subAnimationLoaders).get(0)).prepare();
    }

    @Override // com.android.server.power.shutdown.AnimationPlayer
    public final void setView(ShutdownAnimatedImageView shutdownAnimatedImageView) {
        this.mainImageView = shutdownAnimatedImageView;
        if (shutdownAnimatedImageView instanceof ShutdownAnimatedImageView) {
            shutdownAnimatedImageView.listener = this;
        }
        this.subImageView = null;
        this.subDialog = null;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void start() {
        List list = this.mainAnimationLoaders;
        if (list != null && !((ArrayList) list).isEmpty()) {
            ((AnimationLoader) ((ArrayList) this.mainAnimationLoaders).get(0)).start();
        }
        List list2 = this.subAnimationLoaders;
        if (list2 == null || ((ArrayList) list2).isEmpty()) {
            return;
        }
        ((AnimationLoader) ((ArrayList) this.subAnimationLoaders).get(0)).start();
    }
}
