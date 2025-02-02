package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.helper.widget.LogJson$JsonWriter$$ExternalSyntheticOutline0;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class LottieDrawable extends Drawable implements Drawable.Callback, Animatable {
    private int alpha;
    private final LottieValueAnimator animator;
    private LottieComposition composition;
    private CompositionLayer compositionLayer;
    private boolean enableMergePaths;
    private FontAssetManager fontAssetManager;
    private ImageAssetManager imageAssetManager;
    private String imageAssetsFolder;
    private boolean isApplyingOpacityToLayersEnabled;
    private boolean isDirty;
    private boolean isExtraScaleEnabled;
    private final ArrayList<LazyCompositionTask> lazyCompositionTasks;
    private final Matrix matrix = new Matrix();
    private boolean outlineMasksAndMattes;
    private boolean performanceTrackingEnabled;
    private boolean safeMode;
    private float scale;
    private ImageView.ScaleType scaleType;
    private boolean systemAnimationsEnabled;

    private interface LazyCompositionTask {
        void run();
    }

    public LottieDrawable() {
        LottieValueAnimator lottieValueAnimator = new LottieValueAnimator();
        this.animator = lottieValueAnimator;
        this.scale = 1.0f;
        this.systemAnimationsEnabled = true;
        this.safeMode = false;
        new HashSet();
        this.lazyCompositionTasks = new ArrayList<>();
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.airbnb.lottie.LottieDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (LottieDrawable.this.compositionLayer != null) {
                    LottieDrawable.this.compositionLayer.setProgress(LottieDrawable.this.animator.getAnimatedValueAbsolute());
                }
            }
        };
        this.alpha = 255;
        this.isExtraScaleEnabled = true;
        this.isDirty = false;
        lottieValueAnimator.addUpdateListener(animatorUpdateListener);
    }

    private void buildCompositionLayer() {
        LottieComposition lottieComposition = this.composition;
        int i = LayerParser.$r8$clinit;
        Rect bounds = lottieComposition.getBounds();
        CompositionLayer compositionLayer = new CompositionLayer(this, new Layer(Collections.emptyList(), lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false), this.composition.getLayers(), this.composition);
        this.compositionLayer = compositionLayer;
        if (this.outlineMasksAndMattes) {
            compositionLayer.setOutlineMasksAndMattes(true);
        }
    }

    private void drawInternal(Canvas canvas) {
        float f;
        float f2;
        int i = -1;
        if (ImageView.ScaleType.FIT_XY != this.scaleType) {
            if (this.compositionLayer == null) {
                return;
            }
            float f3 = this.scale;
            float min = Math.min(canvas.getWidth() / this.composition.getBounds().width(), canvas.getHeight() / this.composition.getBounds().height());
            if (f3 > min) {
                f = this.scale / min;
            } else {
                min = f3;
                f = 1.0f;
            }
            if (f > 1.0f) {
                i = canvas.save();
                float width = this.composition.getBounds().width() / 2.0f;
                float height = this.composition.getBounds().height() / 2.0f;
                float f4 = width * min;
                float f5 = height * min;
                float f6 = this.scale;
                canvas.translate((width * f6) - f4, (f6 * height) - f5);
                canvas.scale(f, f, f4, f5);
            }
            this.matrix.reset();
            this.matrix.preScale(min, min);
            this.compositionLayer.draw(canvas, this.matrix, this.alpha);
            if (i > 0) {
                canvas.restoreToCount(i);
                return;
            }
            return;
        }
        if (this.compositionLayer == null) {
            return;
        }
        Rect bounds = getBounds();
        float width2 = bounds.width() / this.composition.getBounds().width();
        float height2 = bounds.height() / this.composition.getBounds().height();
        if (this.isExtraScaleEnabled) {
            float min2 = Math.min(width2, height2);
            if (min2 < 1.0f) {
                f2 = 1.0f / min2;
                width2 /= f2;
                height2 /= f2;
            } else {
                f2 = 1.0f;
            }
            if (f2 > 1.0f) {
                i = canvas.save();
                float width3 = bounds.width() / 2.0f;
                float height3 = bounds.height() / 2.0f;
                float f7 = width3 * min2;
                float f8 = min2 * height3;
                canvas.translate(width3 - f7, height3 - f8);
                canvas.scale(f2, f2, f7, f8);
            }
        }
        this.matrix.reset();
        this.matrix.preScale(width2, height2);
        this.compositionLayer.draw(canvas, this.matrix, this.alpha);
        if (i > 0) {
            canvas.restoreToCount(i);
        }
    }

    private void updateBounds() {
        if (this.composition == null) {
            return;
        }
        float f = this.scale;
        setBounds(0, 0, (int) (r0.getBounds().width() * f), (int) (this.composition.getBounds().height() * f));
    }

    public final void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animator.addListener(animatorListener);
    }

    public final <T> void addValueCallback(final KeyPath keyPath, final T t, final LottieValueCallback<T> lottieValueCallback) {
        List list;
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.16
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.addValueCallback(keyPath, t, lottieValueCallback);
                }
            });
            return;
        }
        boolean z = true;
        if (keyPath == KeyPath.COMPOSITION) {
            compositionLayer.addValueCallback(lottieValueCallback, t);
        } else if (keyPath.getResolvedElement() != null) {
            keyPath.getResolvedElement().addValueCallback(lottieValueCallback, t);
        } else {
            if (this.compositionLayer == null) {
                Logger.warning("Cannot resolve KeyPath. Composition is not set yet.");
                list = Collections.emptyList();
            } else {
                ArrayList arrayList = new ArrayList();
                this.compositionLayer.resolveKeyPath(keyPath, 0, arrayList, new KeyPath(new String[0]));
                list = arrayList;
            }
            for (int i = 0; i < list.size(); i++) {
                ((KeyPath) list.get(i)).getResolvedElement().addValueCallback(lottieValueCallback, t);
            }
            z = true ^ list.isEmpty();
        }
        if (z) {
            invalidateSelf();
            if (t == LottieProperty.TIME_REMAP) {
                setProgress(getProgress());
            }
        }
    }

    public final void cancelAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.cancel();
    }

    public final void clearComposition() {
        if (this.animator.isRunning()) {
            this.animator.cancel();
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        this.animator.clearComposition();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.isDirty = false;
        if (this.safeMode) {
            try {
                drawInternal(canvas);
            } catch (Throwable unused) {
                Logger.error();
            }
        } else {
            drawInternal(canvas);
        }
        C0179L.endSection();
    }

    public final boolean enableMergePathsForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.alpha;
    }

    public final LottieComposition getComposition() {
        return this.composition;
    }

    public final int getFrame() {
        return (int) this.animator.getFrame();
    }

    public final Bitmap getImageAsset(String str) {
        ImageAssetManager imageAssetManager;
        if (getCallback() == null) {
            imageAssetManager = null;
        } else {
            ImageAssetManager imageAssetManager2 = this.imageAssetManager;
            if (imageAssetManager2 != null) {
                Drawable.Callback callback = getCallback();
                if (!imageAssetManager2.hasSameContext((callback != null && (callback instanceof View)) ? ((View) callback).getContext() : null)) {
                    this.imageAssetManager = null;
                }
            }
            if (this.imageAssetManager == null) {
                this.imageAssetManager = new ImageAssetManager(getCallback(), this.imageAssetsFolder, this.composition.getImages());
            }
            imageAssetManager = this.imageAssetManager;
        }
        if (imageAssetManager != null) {
            return imageAssetManager.bitmapForId(str);
        }
        return null;
    }

    public final String getImageAssetsFolder() {
        return this.imageAssetsFolder;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        if (this.composition == null) {
            return -1;
        }
        return (int) (r0.getBounds().height() * this.scale);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        if (this.composition == null) {
            return -1;
        }
        return (int) (r0.getBounds().width() * this.scale);
    }

    public final float getMaxFrame() {
        return this.animator.getMaxFrame();
    }

    public final float getMinFrame() {
        return this.animator.getMinFrame();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    public final PerformanceTracker getPerformanceTracker() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return lottieComposition.getPerformanceTracker();
        }
        return null;
    }

    public final float getProgress() {
        return this.animator.getAnimatedValueAbsolute();
    }

    public final int getRepeatCount() {
        return this.animator.getRepeatCount();
    }

    public final int getRepeatMode() {
        return this.animator.getRepeatMode();
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getSpeed() {
        return this.animator.getSpeed();
    }

    public final Typeface getTypeface(String str, String str2) {
        FontAssetManager fontAssetManager;
        if (getCallback() == null) {
            fontAssetManager = null;
        } else {
            if (this.fontAssetManager == null) {
                this.fontAssetManager = new FontAssetManager(getCallback());
            }
            fontAssetManager = this.fontAssetManager;
        }
        if (fontAssetManager != null) {
            return fontAssetManager.getTypeface(str, str2);
        }
        return null;
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

    public final boolean isAnimating() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.isRunning();
    }

    public final boolean isApplyingOpacityToLayersEnabled() {
        return this.isApplyingOpacityToLayersEnabled;
    }

    @Override // android.graphics.drawable.Animatable
    public final boolean isRunning() {
        return isAnimating();
    }

    public final void pauseAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.pauseAnimation();
    }

    public final void playAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.2
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.playAnimation();
                }
            });
            return;
        }
        if (this.systemAnimationsEnabled || getRepeatCount() == 0) {
            this.animator.playAnimation();
        }
        if (this.systemAnimationsEnabled) {
            return;
        }
        setFrame((int) (getSpeed() < 0.0f ? getMinFrame() : getMaxFrame()));
        this.animator.endAnimation();
    }

    public final void resumeAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.3
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.resumeAnimation();
                }
            });
            return;
        }
        if (this.systemAnimationsEnabled || getRepeatCount() == 0) {
            this.animator.resumeAnimation();
        }
        if (this.systemAnimationsEnabled) {
            return;
        }
        setFrame((int) (getSpeed() < 0.0f ? getMinFrame() : getMaxFrame()));
        this.animator.endAnimation();
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

    public final void setApplyingOpacityToLayersEnabled(boolean z) {
        this.isApplyingOpacityToLayersEnabled = z;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        Logger.warning("Use addColorFilter instead.");
    }

    public final boolean setComposition(LottieComposition lottieComposition) {
        if (this.composition == lottieComposition) {
            return false;
        }
        this.isDirty = false;
        clearComposition();
        this.composition = lottieComposition;
        buildCompositionLayer();
        this.animator.setComposition(lottieComposition);
        setProgress(this.animator.getAnimatedFraction());
        setScale(this.scale);
        updateBounds();
        Iterator it = new ArrayList(this.lazyCompositionTasks).iterator();
        while (it.hasNext()) {
            ((LazyCompositionTask) it.next()).run();
            it.remove();
        }
        this.lazyCompositionTasks.clear();
        lottieComposition.setPerformanceTrackingEnabled(this.performanceTrackingEnabled);
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof ImageView)) {
            return true;
        }
        ImageView imageView = (ImageView) callback;
        imageView.setImageDrawable(null);
        imageView.setImageDrawable(this);
        return true;
    }

    public final void setFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.14
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setFrame(i);
                }
            });
        } else {
            this.animator.setFrame(i);
        }
    }

    public final void setImagesAssetsFolder(String str) {
        this.imageAssetsFolder = str;
    }

    public final void setMaxFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.6
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMaxFrame(i);
                }
            });
        } else {
            this.animator.setMaxFrame(i + 0.99f);
        }
    }

    public final void setMaxProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.7
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMaxProgress(f);
                }
            });
            return;
        }
        float startFrame = lottieComposition.getStartFrame();
        float endFrame = this.composition.getEndFrame();
        int i = MiscUtils.$r8$clinit;
        setMaxFrame((int) RunGroup$$ExternalSyntheticOutline0.m3m(endFrame, startFrame, f, startFrame));
    }

    public final void setMinAndMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.10
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMinAndMaxFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker == null) {
            throw new IllegalArgumentException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Cannot find marker with name ", str, "."));
        }
        int i = (int) marker.startFrame;
        setMinAndMaxFrame(i, ((int) marker.durationFrames) + i);
    }

    public final void setMinFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.4
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMinFrame(i);
                }
            });
        } else {
            this.animator.setMinFrame(i);
        }
    }

    public final void setMinProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.5
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMinProgress(f);
                }
            });
            return;
        }
        float startFrame = lottieComposition.getStartFrame();
        float endFrame = this.composition.getEndFrame();
        int i = MiscUtils.$r8$clinit;
        setMinFrame((int) RunGroup$$ExternalSyntheticOutline0.m3m(endFrame, startFrame, f, startFrame));
    }

    public final void setOutlineMasksAndMattes(boolean z) {
        if (this.outlineMasksAndMattes == z) {
            return;
        }
        this.outlineMasksAndMattes = z;
        CompositionLayer compositionLayer = this.compositionLayer;
        if (compositionLayer != null) {
            compositionLayer.setOutlineMasksAndMattes(z);
        }
    }

    public final void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTrackingEnabled = z;
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            lottieComposition.setPerformanceTrackingEnabled(z);
        }
    }

    public final void setProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.15
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setProgress(f);
                }
            });
            return;
        }
        LottieValueAnimator lottieValueAnimator = this.animator;
        float startFrame = lottieComposition.getStartFrame();
        float endFrame = this.composition.getEndFrame();
        int i = MiscUtils.$r8$clinit;
        lottieValueAnimator.setFrame(((endFrame - startFrame) * f) + startFrame);
        C0179L.endSection();
    }

    public final void setRepeatCount(int i) {
        this.animator.setRepeatCount(i);
    }

    public final void setRepeatMode(int i) {
        this.animator.setRepeatMode(i);
    }

    public final void setSafeMode(boolean z) {
        this.safeMode = z;
    }

    public final void setScale(float f) {
        this.scale = f;
        updateBounds();
    }

    final void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public final void setSpeed(float f) {
        this.animator.setSpeed(f);
    }

    final void setSystemAnimationsAreEnabled(Boolean bool) {
        this.systemAnimationsEnabled = bool.booleanValue();
    }

    @Override // android.graphics.drawable.Animatable
    public final void start() {
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof View) || ((View) callback).isInEditMode()) {
            return;
        }
        playAnimation();
    }

    @Override // android.graphics.drawable.Animatable
    public final void stop() {
        this.lazyCompositionTasks.clear();
        this.animator.endAnimation();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        callback.unscheduleDrawable(this, runnable);
    }

    public final boolean useTextGlyphs() {
        return this.composition.getCharacters().size > 0;
    }

    public final void enableMergePathsForKitKatAndAbove(boolean z) {
        if (this.enableMergePaths == z) {
            return;
        }
        this.enableMergePaths = z;
        if (this.composition != null) {
            buildCompositionLayer();
        }
    }

    public final void setMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.9
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMaxFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMaxFrame((int) (marker.startFrame + marker.durationFrames));
            return;
        }
        throw new IllegalArgumentException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Cannot find marker with name ", str, "."));
    }

    public final void setMinFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.8
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMinFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMinFrame((int) marker.startFrame);
            return;
        }
        throw new IllegalArgumentException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Cannot find marker with name ", str, "."));
    }

    public final void setMinAndMaxFrame(final int i, final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() { // from class: com.airbnb.lottie.LottieDrawable.12
                @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
                public final void run() {
                    LottieDrawable.this.setMinAndMaxFrame(i, i2);
                }
            });
        } else {
            this.animator.setMinAndMaxFrames(i, i2 + 0.99f);
        }
    }
}
