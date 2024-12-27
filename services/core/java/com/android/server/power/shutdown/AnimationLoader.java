package com.android.server.power.shutdown;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import java.io.File;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AnimationLoader extends Animatable2.AnimationCallback implements Callable, Drawable.Callback {
    public AnimatedImageDrawable animatedImageDrawable;
    public final File animationFile;
    public final AnimationType animationType;
    public Bitmap bitmap;
    public Canvas canvas;
    public Consumer imageResolutionExtractor;
    public AnimationLoader nextAnimation;
    public AnimationLoader pairAnimation;
    public final long playTimeout;
    public final WebpPlayer player;
    public final int repeatCount;
    public AnimationStatus status = AnimationStatus.IDLE;
    public AnimationLoader$$ExternalSyntheticLambda0 stopRunnable;

    public AnimationLoader(File file, int i, long j, AnimationType animationType, WebpPlayer webpPlayer) {
        this.animationFile = file;
        this.repeatCount = i;
        this.playTimeout = j;
        this.animationType = animationType;
        this.player = webpPlayer;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return loadDrawable();
    }

    public final void changeStatus(AnimationStatus animationStatus) {
        AnimationLoader animationLoader;
        AnimationStatus animationStatus2 = AnimationStatus.START;
        if (animationStatus == animationStatus2) {
            SystemClock.uptimeMillis();
        } else if (animationStatus == AnimationStatus.STOP) {
            SystemClock.uptimeMillis();
        }
        WebpPlayer webpPlayer = this.player;
        if (webpPlayer != null) {
            synchronized (webpPlayer) {
                animationLoader = webpPlayer.currentAnimationLoader;
            }
            Locale locale = Locale.ENGLISH;
            Slog.d("Shutdown-WebpPlayer", "onAnimationChanged status[" + animationStatus + "] equals[" + (this == animationLoader) + "] animation[" + this + "]");
            if (animationStatus == animationStatus2) {
                synchronized (webpPlayer) {
                    webpPlayer.currentAnimationLoader = this;
                }
            }
        }
        this.status = animationStatus;
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x003d, code lost:
    
        if (r0.hasSubResources != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x005b, code lost:
    
        r9 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0059, code lost:
    
        if (r0.hasSubResources != false) goto L32;
     */
    @Override // android.graphics.drawable.Drawable.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void invalidateDrawable(android.graphics.drawable.Drawable r13) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.shutdown.AnimationLoader.invalidateDrawable(android.graphics.drawable.Drawable):void");
    }

    public final synchronized AnimatedImageDrawable loadDrawable() {
        try {
            if (this.animatedImageDrawable == null) {
                this.animatedImageDrawable = (AnimatedImageDrawable) AnimatedImageDrawable.createFromPath(this.animationFile.getAbsolutePath());
                changeStatus(AnimationStatus.LOAD);
                Consumer consumer = this.imageResolutionExtractor;
                if (consumer != null) {
                    consumer.accept(this.animatedImageDrawable);
                }
                Locale locale = Locale.ENGLISH;
                Slog.i("Shutdown-AnimationLoader", "loadDrawable " + this);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.animatedImageDrawable;
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationEnd(Drawable drawable) {
        Locale locale = Locale.ENGLISH;
        Slog.d("Shutdown-AnimationLoader", "onAnimationEnd " + this);
        ((AnimatedImageDrawable) drawable).clearAnimationCallbacks();
        if (this.status != AnimationStatus.STOP) {
            AnimationLoader animationLoader = this.nextAnimation;
            if (animationLoader != null) {
                animationLoader.start();
            }
            changeStatus(AnimationStatus.FINISH);
            synchronized (this) {
                this.animatedImageDrawable = null;
            }
        }
        if (this.stopRunnable != null) {
            this.player.getHandler().removeCallbacks(this.stopRunnable);
        }
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.bitmap = null;
        }
        if (this.canvas != null) {
            this.canvas = null;
        }
        super.onAnimationEnd(drawable);
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationStart(Drawable drawable) {
        Locale locale = Locale.ENGLISH;
        Slog.d("Shutdown-AnimationLoader", "onAnimationStart " + this);
        changeStatus(AnimationStatus.START);
        super.onAnimationStart(drawable);
        AnimationLoader animationLoader = this.nextAnimation;
        if (animationLoader != null) {
            animationLoader.prepare();
        }
    }

    public final void prepare() {
        WebpPlayer webpPlayer = this.player;
        if (webpPlayer.singleExecutorService == null) {
            webpPlayer.singleExecutorService = Executors.newSingleThreadExecutor();
        }
        boolean z = webpPlayer.singleExecutorService.submit(this) != null;
        Locale locale = Locale.ENGLISH;
        Slog.d("Shutdown-AnimationLoader", "prepare " + z + " " + this);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Slog.d("Shutdown-AnimationLoader", String.format(Locale.ENGLISH, "scheduleDrawable schedule time[%d][%d] drawable[%s]", Long.valueOf(j), Long.valueOf(j - SystemClock.uptimeMillis()), drawable));
        this.player.getHandler().postAtTime(runnable, j);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.power.shutdown.AnimationLoader$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public final void start() {
        Handler handler = this.player.getHandler();
        final int i = 0;
        handler.post(new Runnable(this) { // from class: com.android.server.power.shutdown.AnimationLoader$$ExternalSyntheticLambda0
            public final /* synthetic */ AnimationLoader f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                AnimationLoader animationLoader = this.f$0;
                switch (i2) {
                    case 0:
                        AnimatedImageDrawable loadDrawable = animationLoader.loadDrawable();
                        Locale locale = Locale.ENGLISH;
                        Slog.d("Shutdown-AnimationLoader", "startAnimation " + animationLoader);
                        if (loadDrawable != null) {
                            animationLoader.changeStatus(AnimationStatus.START);
                            loadDrawable.setRepeatCount(animationLoader.repeatCount);
                            loadDrawable.setCallback(animationLoader);
                            loadDrawable.registerAnimationCallback(animationLoader);
                            loadDrawable.start();
                            break;
                        }
                        break;
                    default:
                        animationLoader.getClass();
                        Locale locale2 = Locale.ENGLISH;
                        Slog.i("Shutdown-AnimationLoader", "stopAnimation reason[" + ("timeout[" + animationLoader.playTimeout + "]") + "] " + animationLoader);
                        if (animationLoader.stopRunnable != null) {
                            animationLoader.player.getHandler().removeCallbacks(animationLoader.stopRunnable);
                        }
                        AnimatedImageDrawable loadDrawable2 = animationLoader.loadDrawable();
                        if (loadDrawable2 != null) {
                            animationLoader.changeStatus(AnimationStatus.STOP);
                            loadDrawable2.stop();
                            break;
                        } else {
                            Slog.w("Shutdown-AnimationLoader", "stopAnimation can't load drawable " + animationLoader);
                            break;
                        }
                }
            }
        });
        long j = this.playTimeout;
        if (j > 0) {
            final int i2 = 1;
            ?? r3 = new Runnable(this) { // from class: com.android.server.power.shutdown.AnimationLoader$$ExternalSyntheticLambda0
                public final /* synthetic */ AnimationLoader f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i2;
                    AnimationLoader animationLoader = this.f$0;
                    switch (i22) {
                        case 0:
                            AnimatedImageDrawable loadDrawable = animationLoader.loadDrawable();
                            Locale locale = Locale.ENGLISH;
                            Slog.d("Shutdown-AnimationLoader", "startAnimation " + animationLoader);
                            if (loadDrawable != null) {
                                animationLoader.changeStatus(AnimationStatus.START);
                                loadDrawable.setRepeatCount(animationLoader.repeatCount);
                                loadDrawable.setCallback(animationLoader);
                                loadDrawable.registerAnimationCallback(animationLoader);
                                loadDrawable.start();
                                break;
                            }
                            break;
                        default:
                            animationLoader.getClass();
                            Locale locale2 = Locale.ENGLISH;
                            Slog.i("Shutdown-AnimationLoader", "stopAnimation reason[" + ("timeout[" + animationLoader.playTimeout + "]") + "] " + animationLoader);
                            if (animationLoader.stopRunnable != null) {
                                animationLoader.player.getHandler().removeCallbacks(animationLoader.stopRunnable);
                            }
                            AnimatedImageDrawable loadDrawable2 = animationLoader.loadDrawable();
                            if (loadDrawable2 != null) {
                                animationLoader.changeStatus(AnimationStatus.STOP);
                                loadDrawable2.stop();
                                break;
                            } else {
                                Slog.w("Shutdown-AnimationLoader", "stopAnimation can't load drawable " + animationLoader);
                                break;
                            }
                    }
                }
            };
            this.stopRunnable = r3;
            handler.postDelayed(r3, j);
        }
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("AnimationLoader{animationFile=");
        sb.append(this.animationFile);
        sb.append(", repeatCount=");
        sb.append(this.repeatCount);
        sb.append(", playTimeout=");
        sb.append(this.playTimeout);
        sb.append(", animationType=");
        sb.append(this.animationType);
        sb.append(", status=");
        sb.append(this.status);
        sb.append(", drawable=");
        sb.append(this.animatedImageDrawable);
        String str2 = "";
        if (this.nextAnimation == null) {
            str = "";
        } else {
            str = ", next=" + this.nextAnimation.animationFile;
        }
        sb.append(str);
        if (this.pairAnimation != null) {
            str2 = ", pair=" + this.pairAnimation.animationFile;
        }
        sb.append(str2);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Slog.d("Shutdown-AnimationLoader", String.format(Locale.ENGLISH, "unscheduleDrawable drawable[%s]", drawable));
        this.player.getHandler().removeCallbacks(runnable);
    }
}
