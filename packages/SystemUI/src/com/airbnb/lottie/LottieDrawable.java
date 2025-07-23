package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.airbnb.lottie.RenderMode;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieThreadFactory;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LottieDrawable extends Drawable implements Drawable.Callback, Animatable {
    public static final Executor setProgressExecutor = new ThreadPoolExecutor(0, 2, 35, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new LottieThreadFactory());
    public int alpha;
    public final LottieValueAnimator animator;
    public AsyncUpdates asyncUpdates;
    public Rect canvasClipBounds;
    public RectF canvasClipBoundsRectF;
    public boolean clipToCompositionBounds;
    public LottieComposition composition;
    public CompositionLayer compositionLayer;
    public String defaultFontFileExtension;
    public boolean enableMergePaths;
    public FontAssetDelegate fontAssetDelegate;
    public FontAssetManager fontAssetManager;
    public Map fontMap;
    public boolean ignoreSystemAnimationsDisabled;
    public ImageAssetManager imageAssetManager;
    public String imageAssetsFolder;
    public boolean isApplyingOpacityToLayersEnabled;
    public boolean isDirty;
    public float lastDrawnProgress;
    public final ArrayList lazyCompositionTasks;
    public boolean maintainOriginalImageBounds;
    public OnVisibleAction onVisibleAction;
    public boolean outlineMasksAndMattes;
    public boolean performanceTrackingEnabled;
    public final LottieDrawable$$ExternalSyntheticLambda5 progressUpdateListener;
    public RenderMode renderMode;
    public final Matrix renderingMatrix;
    public boolean safeMode;
    public final Semaphore setProgressDrawLock;
    public Bitmap softwareRenderingBitmap;
    public Canvas softwareRenderingCanvas;
    public Rect softwareRenderingDstBoundsRect;
    public RectF softwareRenderingDstBoundsRectF;
    public Matrix softwareRenderingOriginalCanvasMatrix;
    public Matrix softwareRenderingOriginalCanvasMatrixInverse;
    public LPaint softwareRenderingPaint;
    public Rect softwareRenderingSrcBoundsRect;
    public RectF softwareRenderingTransformedBounds;
    public boolean systemAnimationsEnabled;
    public TextDelegate textDelegate;
    public final LottieDrawable$$ExternalSyntheticLambda6 updateProgressRunnable;
    public boolean useSoftwareRendering;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface LazyCompositionTask {
        void run();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum OnVisibleAction {
        NONE,
        PLAY,
        RESUME
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r3v6, types: [android.animation.ValueAnimator$AnimatorUpdateListener, com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda5] */
    public LottieDrawable() {
        LottieValueAnimator lottieValueAnimator = new LottieValueAnimator();
        this.animator = lottieValueAnimator;
        this.systemAnimationsEnabled = true;
        this.ignoreSystemAnimationsDisabled = false;
        this.safeMode = false;
        this.onVisibleAction = OnVisibleAction.NONE;
        this.lazyCompositionTasks = new ArrayList();
        this.maintainOriginalImageBounds = false;
        this.clipToCompositionBounds = true;
        this.alpha = 255;
        this.renderMode = RenderMode.AUTOMATIC;
        this.useSoftwareRendering = false;
        this.renderingMatrix = new Matrix();
        this.asyncUpdates = AsyncUpdates.AUTOMATIC;
        ?? r3 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LottieDrawable lottieDrawable = LottieDrawable.this;
                Executor executor = LottieDrawable.setProgressExecutor;
                if (lottieDrawable.asyncUpdates == AsyncUpdates.ENABLED) {
                    lottieDrawable.invalidateSelf();
                    return;
                }
                CompositionLayer compositionLayer = lottieDrawable.compositionLayer;
                if (compositionLayer != null) {
                    compositionLayer.setProgress(lottieDrawable.animator.getAnimatedValueAbsolute());
                }
            }
        };
        this.progressUpdateListener = r3;
        this.setProgressDrawLock = new Semaphore(1);
        this.updateProgressRunnable = new Runnable() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                LottieDrawable lottieDrawable = LottieDrawable.this;
                CompositionLayer compositionLayer = lottieDrawable.compositionLayer;
                if (compositionLayer == null) {
                    return;
                }
                try {
                    lottieDrawable.setProgressDrawLock.acquire();
                    compositionLayer.setProgress(lottieDrawable.animator.getAnimatedValueAbsolute());
                } catch (InterruptedException unused) {
                } finally {
                    lottieDrawable.setProgressDrawLock.release();
                }
            }
        };
        this.lastDrawnProgress = -3.4028235E38f;
        this.isDirty = false;
        lottieValueAnimator.addUpdateListener(r3);
    }

    public static void convertRect(Rect rect, RectF rectF) {
        rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
    }

    public final void addValueCallback(final KeyPath keyPath, final Object obj, final LottieValueCallback lottieValueCallback) {
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda15
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    Executor executor = LottieDrawable.setProgressExecutor;
                    LottieDrawable.this.addValueCallback(keyPath, obj, lottieValueCallback);
                }
            });
            return;
        }
        boolean z = true;
        if (keyPath == KeyPath.COMPOSITION) {
            compositionLayer.addValueCallback(lottieValueCallback, obj);
        } else {
            KeyPathElement keyPathElement = keyPath.resolvedElement;
            if (keyPathElement != null) {
                keyPathElement.addValueCallback(lottieValueCallback, obj);
            } else {
                List resolveKeyPath = resolveKeyPath(keyPath);
                for (int i = 0; i < resolveKeyPath.size(); i++) {
                    ((KeyPath) resolveKeyPath.get(i)).resolvedElement.addValueCallback(lottieValueCallback, obj);
                }
                z = true ^ resolveKeyPath.isEmpty();
            }
        }
        if (z) {
            invalidateSelf();
            if (obj == LottieProperty.TIME_REMAP) {
                setProgress(this.animator.getAnimatedValueAbsolute());
            }
        }
    }

    public final boolean animationsEnabled() {
        return this.systemAnimationsEnabled || this.ignoreSystemAnimationsDisabled;
    }

    public final void buildCompositionLayer() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return;
        }
        JsonReader.Options options = LayerParser.NAMES;
        Rect rect = lottieComposition.bounds;
        List list = Collections.EMPTY_LIST;
        CompositionLayer compositionLayer = new CompositionLayer(this, new Layer(list, lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, list, new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, rect.width(), rect.height(), null, null, list, Layer.MatteType.NONE, null, false, null, null), lottieComposition.layers, lottieComposition);
        this.compositionLayer = compositionLayer;
        if (this.outlineMasksAndMattes) {
            compositionLayer.setOutlineMasksAndMattes(true);
        }
        this.compositionLayer.clipToCompositionBounds = this.clipToCompositionBounds;
    }

    public final void clearComposition() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        if (lottieValueAnimator.running) {
            lottieValueAnimator.cancel();
            if (!isVisible()) {
                this.onVisibleAction = OnVisibleAction.NONE;
            }
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        this.lastDrawnProgress = -3.4028235E38f;
        LottieValueAnimator lottieValueAnimator2 = this.animator;
        lottieValueAnimator2.composition = null;
        lottieValueAnimator2.minFrame = -2.1474836E9f;
        lottieValueAnimator2.maxFrame = 2.1474836E9f;
        invalidateSelf();
    }

    public final void computeRenderMode() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return;
        }
        RenderMode renderMode = this.renderMode;
        int i = lottieComposition.maskAndMatteCount;
        renderMode.getClass();
        int i2 = RenderMode.AnonymousClass1.$SwitchMap$com$airbnb$lottie$RenderMode[renderMode.ordinal()];
        boolean z = false;
        if (i2 != 1 && (i2 == 2 || i > 4)) {
            z = true;
        }
        this.useSoftwareRendering = z;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer == null) {
            return;
        }
        boolean z = this.asyncUpdates == AsyncUpdates.ENABLED;
        if (z) {
            try {
                this.setProgressDrawLock.acquire();
            } catch (InterruptedException unused) {
                if (!z) {
                    return;
                }
                this.setProgressDrawLock.release();
                if (compositionLayer.progress == this.animator.getAnimatedValueAbsolute()) {
                    return;
                }
            } catch (Throwable th) {
                if (z) {
                    this.setProgressDrawLock.release();
                    if (compositionLayer.progress != this.animator.getAnimatedValueAbsolute()) {
                        ((ThreadPoolExecutor) setProgressExecutor).execute(this.updateProgressRunnable);
                    }
                }
                throw th;
            }
        }
        if (z && shouldSetProgressBeforeDrawing()) {
            setProgress(this.animator.getAnimatedValueAbsolute());
        }
        if (this.safeMode) {
            try {
                if (this.useSoftwareRendering) {
                    renderAndDrawAsBitmap(canvas, compositionLayer);
                } else {
                    drawDirectlyToCanvas(canvas);
                }
            } catch (Throwable unused2) {
                Logger.INSTANCE.getClass();
            }
        } else if (this.useSoftwareRendering) {
            renderAndDrawAsBitmap(canvas, compositionLayer);
        } else {
            drawDirectlyToCanvas(canvas);
        }
        this.isDirty = false;
        if (z) {
            this.setProgressDrawLock.release();
            if (compositionLayer.progress == this.animator.getAnimatedValueAbsolute()) {
                return;
            }
            ((ThreadPoolExecutor) setProgressExecutor).execute(this.updateProgressRunnable);
        }
    }

    public final void drawDirectlyToCanvas(Canvas canvas) {
        CompositionLayer compositionLayer = this.compositionLayer;
        LottieComposition lottieComposition = this.composition;
        if (compositionLayer == null || lottieComposition == null) {
            return;
        }
        this.renderingMatrix.reset();
        if (!getBounds().isEmpty()) {
            this.renderingMatrix.preScale(r2.width() / lottieComposition.bounds.width(), r2.height() / lottieComposition.bounds.height());
            this.renderingMatrix.preTranslate(r2.left, r2.top);
        }
        compositionLayer.draw(canvas, this.renderingMatrix, this.alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.alpha;
    }

    public final FontAssetManager getFontAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        if (this.fontAssetManager == null) {
            FontAssetManager fontAssetManager = new FontAssetManager(getCallback(), this.fontAssetDelegate);
            this.fontAssetManager = fontAssetManager;
            String str = this.defaultFontFileExtension;
            if (str != null) {
                fontAssetManager.defaultFontFileExtension = str;
            }
        }
        return this.fontAssetManager;
    }

    public final ImageAssetManager getImageAssetManager() {
        ImageAssetManager imageAssetManager = this.imageAssetManager;
        if (imageAssetManager != null) {
            Drawable.Callback callback = getCallback();
            Context context = (callback != null && (callback instanceof View)) ? ((View) callback).getContext() : null;
            if (imageAssetManager.context instanceof Application) {
                context = context.getApplicationContext();
            }
            if (context != imageAssetManager.context) {
                this.imageAssetManager = null;
            }
        }
        if (this.imageAssetManager == null) {
            this.imageAssetManager = new ImageAssetManager(getCallback(), this.imageAssetsFolder, null, this.composition.images);
        }
        return this.imageAssetManager;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        return lottieComposition.bounds.height();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        return lottieComposition.bounds.width();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        if (this.isDirty) {
            return;
        }
        this.isDirty = true;
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public final boolean isRunning() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.running;
    }

    public final void pauseAnimation() {
        this.lazyCompositionTasks.clear();
        LottieValueAnimator lottieValueAnimator = this.animator;
        lottieValueAnimator.removeFrameCallback(true);
        Iterator it = ((CopyOnWriteArraySet) lottieValueAnimator.pauseListeners).iterator();
        while (it.hasNext()) {
            ((Animator.AnimatorPauseListener) it.next()).onAnimationPause(lottieValueAnimator);
        }
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    public final void playAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda1(this, 1));
            return;
        }
        computeRenderMode();
        if (animationsEnabled() || this.animator.getRepeatCount() == 0) {
            if (isVisible()) {
                LottieValueAnimator lottieValueAnimator = this.animator;
                lottieValueAnimator.running = true;
                boolean isReversed = lottieValueAnimator.isReversed();
                Iterator it = ((CopyOnWriteArraySet) lottieValueAnimator.listeners).iterator();
                while (it.hasNext()) {
                    ((Animator.AnimatorListener) it.next()).onAnimationStart(lottieValueAnimator, isReversed);
                }
                lottieValueAnimator.setFrame((int) (lottieValueAnimator.isReversed() ? lottieValueAnimator.getMaxFrame() : lottieValueAnimator.getMinFrame()));
                lottieValueAnimator.lastFrameTimeNs = 0L;
                lottieValueAnimator.repeatCount = 0;
                if (lottieValueAnimator.running) {
                    lottieValueAnimator.removeFrameCallback(false);
                    Choreographer.getInstance().postFrameCallback(lottieValueAnimator);
                }
                this.onVisibleAction = OnVisibleAction.NONE;
            } else {
                this.onVisibleAction = OnVisibleAction.PLAY;
            }
        }
        if (animationsEnabled()) {
            return;
        }
        LottieValueAnimator lottieValueAnimator2 = this.animator;
        setFrame((int) (lottieValueAnimator2.speed < 0.0f ? lottieValueAnimator2.getMinFrame() : lottieValueAnimator2.getMaxFrame()));
        LottieValueAnimator lottieValueAnimator3 = this.animator;
        lottieValueAnimator3.removeFrameCallback(true);
        lottieValueAnimator3.notifyEnd(lottieValueAnimator3.isReversed());
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void renderAndDrawAsBitmap(android.graphics.Canvas r10, com.airbnb.lottie.model.layer.CompositionLayer r11) {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieDrawable.renderAndDrawAsBitmap(android.graphics.Canvas, com.airbnb.lottie.model.layer.CompositionLayer):void");
    }

    public final List resolveKeyPath(KeyPath keyPath) {
        if (this.compositionLayer == null) {
            Logger.warning("Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        this.compositionLayer.resolveKeyPath(keyPath, 0, arrayList, new KeyPath(new String[0]));
        return arrayList;
    }

    public final void resumeAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda1(this, 0));
            return;
        }
        computeRenderMode();
        if (animationsEnabled() || this.animator.getRepeatCount() == 0) {
            if (isVisible()) {
                LottieValueAnimator lottieValueAnimator = this.animator;
                lottieValueAnimator.running = true;
                lottieValueAnimator.removeFrameCallback(false);
                Choreographer.getInstance().postFrameCallback(lottieValueAnimator);
                lottieValueAnimator.lastFrameTimeNs = 0L;
                if (lottieValueAnimator.isReversed() && lottieValueAnimator.frame == lottieValueAnimator.getMinFrame()) {
                    lottieValueAnimator.setFrame(lottieValueAnimator.getMaxFrame());
                } else if (!lottieValueAnimator.isReversed() && lottieValueAnimator.frame == lottieValueAnimator.getMaxFrame()) {
                    lottieValueAnimator.setFrame(lottieValueAnimator.getMinFrame());
                }
                Iterator it = ((CopyOnWriteArraySet) lottieValueAnimator.pauseListeners).iterator();
                while (it.hasNext()) {
                    ((Animator.AnimatorPauseListener) it.next()).onAnimationResume(lottieValueAnimator);
                }
                this.onVisibleAction = OnVisibleAction.NONE;
            } else {
                this.onVisibleAction = OnVisibleAction.RESUME;
            }
        }
        if (animationsEnabled()) {
            return;
        }
        LottieValueAnimator lottieValueAnimator2 = this.animator;
        setFrame((int) (lottieValueAnimator2.speed < 0.0f ? lottieValueAnimator2.getMinFrame() : lottieValueAnimator2.getMaxFrame()));
        LottieValueAnimator lottieValueAnimator3 = this.animator;
        lottieValueAnimator3.removeFrameCallback(true);
        lottieValueAnimator3.notifyEnd(lottieValueAnimator3.isReversed());
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.scheduleDrawable(this, runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.alpha = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        Logger.warning("Use addColorFilter instead.");
    }

    public final boolean setComposition(LottieComposition lottieComposition) {
        if (this.composition == lottieComposition) {
            return false;
        }
        this.isDirty = true;
        clearComposition();
        this.composition = lottieComposition;
        buildCompositionLayer();
        LottieValueAnimator lottieValueAnimator = this.animator;
        boolean z = lottieValueAnimator.composition == null;
        lottieValueAnimator.composition = lottieComposition;
        if (z) {
            lottieValueAnimator.setMinAndMaxFrames(Math.max(lottieValueAnimator.minFrame, lottieComposition.startFrame), Math.min(lottieValueAnimator.maxFrame, lottieComposition.endFrame));
        } else {
            lottieValueAnimator.setMinAndMaxFrames((int) lottieComposition.startFrame, (int) lottieComposition.endFrame);
        }
        float f = lottieValueAnimator.frame;
        lottieValueAnimator.frame = 0.0f;
        lottieValueAnimator.frameRaw = 0.0f;
        lottieValueAnimator.setFrame((int) f);
        lottieValueAnimator.notifyUpdate();
        setProgress(this.animator.getAnimatedFraction());
        Iterator it = new ArrayList(this.lazyCompositionTasks).iterator();
        while (it.hasNext()) {
            LazyCompositionTask lazyCompositionTask = (LazyCompositionTask) it.next();
            if (lazyCompositionTask != null) {
                lazyCompositionTask.run();
            }
            it.remove();
        }
        this.lazyCompositionTasks.clear();
        lottieComposition.performanceTracker.enabled = this.performanceTrackingEnabled;
        computeRenderMode();
        Drawable.Callback callback = getCallback();
        if (callback instanceof ImageView) {
            ImageView imageView = (ImageView) callback;
            imageView.setImageDrawable(null);
            imageView.setImageDrawable(this);
        }
        return true;
    }

    public final void setFrame(int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda10(this, i, 0));
        } else {
            this.animator.setFrame(i);
        }
    }

    public final void setMaxFrame(int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda10(this, i, 1));
            return;
        }
        LottieValueAnimator lottieValueAnimator = this.animator;
        lottieValueAnimator.setMinAndMaxFrames(lottieValueAnimator.minFrame, i + 0.99f);
    }

    public final void setMinAndMaxFrame(String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda0(this, str, 0));
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker == null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot find marker with name ", str, "."));
        }
        int i = (int) marker.startFrame;
        setMinAndMaxFrame(i, ((int) marker.durationFrames) + i);
    }

    public final void setMinAndMaxProgress(final float f, final float f2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda11
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    Executor executor = LottieDrawable.setProgressExecutor;
                    LottieDrawable.this.setMinAndMaxProgress(f, f2);
                }
            });
            return;
        }
        int lerp = (int) MiscUtils.lerp(lottieComposition.startFrame, lottieComposition.endFrame, f);
        LottieComposition lottieComposition2 = this.composition;
        setMinAndMaxFrame(lerp, (int) MiscUtils.lerp(lottieComposition2.startFrame, lottieComposition2.endFrame, f2));
    }

    public final void setMinFrame(int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda10(this, i, 2));
        } else {
            this.animator.setMinAndMaxFrames(i, (int) r3.maxFrame);
        }
    }

    public final void setProgress(float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda8(this, f, 2));
        } else {
            this.animator.setFrame(MiscUtils.lerp(lottieComposition.startFrame, lottieComposition.endFrame, f));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean isVisible = isVisible();
        boolean visible = super.setVisible(z, z2);
        if (z) {
            OnVisibleAction onVisibleAction = this.onVisibleAction;
            if (onVisibleAction == OnVisibleAction.PLAY) {
                playAnimation();
                return visible;
            }
            if (onVisibleAction == OnVisibleAction.RESUME) {
                resumeAnimation();
                return visible;
            }
        } else {
            if (this.animator.running) {
                pauseAnimation();
                this.onVisibleAction = OnVisibleAction.RESUME;
                return visible;
            }
            if (isVisible) {
                this.onVisibleAction = OnVisibleAction.NONE;
            }
        }
        return visible;
    }

    public final boolean shouldSetProgressBeforeDrawing() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return false;
        }
        float f = this.lastDrawnProgress;
        float animatedValueAbsolute = this.animator.getAnimatedValueAbsolute();
        this.lastDrawnProgress = animatedValueAbsolute;
        return Math.abs(animatedValueAbsolute - f) * lottieComposition.getDuration() >= 50.0f;
    }

    @Override // android.graphics.drawable.Animatable
    public final void start() {
        Drawable.Callback callback = getCallback();
        if ((callback instanceof View) && ((View) callback).isInEditMode()) {
            return;
        }
        playAnimation();
    }

    @Override // android.graphics.drawable.Animatable
    public final void stop() {
        this.lazyCompositionTasks.clear();
        LottieValueAnimator lottieValueAnimator = this.animator;
        lottieValueAnimator.removeFrameCallback(true);
        lottieValueAnimator.notifyEnd(lottieValueAnimator.isReversed());
        if (isVisible()) {
            return;
        }
        this.onVisibleAction = OnVisibleAction.NONE;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.unscheduleDrawable(this, runnable);
    }

    public final void setMaxFrame(String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda0(this, str, 1));
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMaxFrame((int) (marker.startFrame + marker.durationFrames));
            return;
        }
        throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot find marker with name ", str, "."));
    }

    public final void setMinFrame(String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda0(this, str, 2));
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMinFrame((int) marker.startFrame);
            return;
        }
        throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot find marker with name ", str, "."));
    }

    public final void setMinAndMaxFrame(final String str, final String str2, final boolean z) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda2
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    Executor executor = LottieDrawable.setProgressExecutor;
                    LottieDrawable.this.setMinAndMaxFrame(str, str2, z);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            int i = (int) marker.startFrame;
            Marker marker2 = this.composition.getMarker(str2);
            if (marker2 != null) {
                setMinAndMaxFrame(i, (int) (marker2.startFrame + (z ? 1.0f : 0.0f)));
                return;
            }
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot find marker with name ", str2, "."));
        }
        throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot find marker with name ", str, "."));
    }

    public final void setMinAndMaxFrame(final int i, final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda12
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    Executor executor = LottieDrawable.setProgressExecutor;
                    LottieDrawable.this.setMinAndMaxFrame(i, i2);
                }
            });
        } else {
            this.animator.setMinAndMaxFrames(i, i2 + 0.99f);
        }
    }
}
