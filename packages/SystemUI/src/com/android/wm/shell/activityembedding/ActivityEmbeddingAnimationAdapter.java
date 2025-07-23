package com.android.wm.shell.activityembedding;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ActivityEmbeddingAnimationAdapter {
    public final Animation mAnimation;
    public final TransitionInfo.Change mChange;
    public final Rect mContentBounds;
    public final Point mContentRelOffset;
    public final SurfaceControl mLeash;
    public final float[] mMatrix;
    public int mOverrideLayer;
    public final Transformation mTransformation;
    public final Rect mWholeAnimationBounds;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BoundsChangeAdapter extends ActivityEmbeddingAnimationAdapter {
        public BoundsChangeAdapter(Animation animation, TransitionInfo.Change change, TransitionInfo.Root root) {
            super(animation, change, root);
        }

        @Override // com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter
        public final void onAnimationUpdateInner(SurfaceControl.Transaction transaction) {
            Matrix matrix = this.mTransformation.getMatrix();
            Point point = this.mContentRelOffset;
            matrix.postTranslate(point.x, point.y);
            transaction.setMatrix(this.mLeash, this.mTransformation.getMatrix(), this.mMatrix);
            transaction.setAlpha(this.mLeash, this.mTransformation.getAlpha());
            transaction.setWindowCrop(this.mLeash, this.mWholeAnimationBounds.width(), this.mWholeAnimationBounds.height());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SnapshotAdapter extends ActivityEmbeddingAnimationAdapter {
        public SnapshotAdapter(Animation animation, TransitionInfo.Change change, SurfaceControl surfaceControl, TransitionInfo.Root root) {
            super(animation, change, surfaceControl, change.getEndAbsBounds(), root);
        }

        @Override // com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter
        public final void onAnimationEnd(SurfaceControl.Transaction transaction) {
            super.onAnimationEnd(transaction);
            if (this.mLeash.isValid()) {
                transaction.remove(this.mLeash);
            }
        }

        @Override // com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter
        public final void onAnimationUpdateInner(SurfaceControl.Transaction transaction) {
            this.mTransformation.getMatrix().postTranslate(0.0f, 0.0f);
            transaction.setMatrix(this.mLeash, this.mTransformation.getMatrix(), this.mMatrix);
            transaction.setAlpha(this.mLeash, this.mTransformation.getAlpha());
        }
    }

    public ActivityEmbeddingAnimationAdapter(Animation animation, TransitionInfo.Change change, TransitionInfo.Root root) {
        this(animation, change, change.getLeash(), change.getEndAbsBounds(), root);
    }

    public void onAnimationEnd(SurfaceControl.Transaction transaction) {
        long duration = this.mAnimation.getDuration();
        this.mTransformation.clear();
        Animation animation = this.mAnimation;
        animation.getTransformation(Math.min(duration, animation.getDuration()), this.mTransformation);
        onAnimationUpdateInner(transaction);
        if (this.mAnimation.getExtensionEdges() != 0) {
            transaction.setEdgeExtensionEffect(this.mLeash, 0);
        }
    }

    public void onAnimationUpdateInner(SurfaceControl.Transaction transaction) {
        if (this.mAnimation.getExtensionEdges() != 0 && (!this.mChange.hasFlags(4) || this.mChange.getActivityComponent() == null)) {
            transaction.setEdgeExtensionEffect(this.mLeash, this.mAnimation.getExtensionEdges());
        }
        Matrix matrix = this.mTransformation.getMatrix();
        Point point = this.mContentRelOffset;
        matrix.postTranslate(point.x, point.y);
        SurfaceControl surfaceControl = this.mLeash;
        Matrix matrix2 = this.mTransformation.getMatrix();
        float[] fArr = this.mMatrix;
        transaction.setMatrix(surfaceControl, matrix2, fArr);
        transaction.setAlpha(this.mLeash, this.mTransformation.getAlpha());
        int round = Math.round(fArr[2]);
        int round2 = Math.round(fArr[5]);
        Rect rect = new Rect(this.mContentBounds);
        Point point2 = this.mContentRelOffset;
        rect.offset(round - point2.x, round2 - point2.y);
        int i = rect.left;
        int i2 = rect.top;
        if (!rect.intersect(this.mWholeAnimationBounds)) {
            transaction.setAlpha(this.mLeash, 0.0f);
        } else if (this.mAnimation.getExtensionEdges() != 0) {
            rect.union(this.mContentBounds);
        }
        rect.offset(-i, -i2);
        transaction.setCrop(this.mLeash, rect);
    }

    public ActivityEmbeddingAnimationAdapter(Animation animation, TransitionInfo.Change change, SurfaceControl surfaceControl, Rect rect, TransitionInfo.Root root) {
        Rect rect2 = new Rect();
        this.mWholeAnimationBounds = rect2;
        Rect rect3 = new Rect();
        this.mContentBounds = rect3;
        Point point = new Point();
        this.mContentRelOffset = point;
        this.mTransformation = new Transformation();
        this.mMatrix = new float[9];
        new Rect();
        this.mOverrideLayer = -1;
        this.mAnimation = animation;
        this.mChange = change;
        this.mLeash = surfaceControl;
        rect2.set(rect);
        Rect startAbsBounds = change.getStartAbsBounds();
        Rect endAbsBounds = change.getEndAbsBounds();
        if (change.getParent() != null) {
            point.set(change.getEndRelOffset());
        } else {
            Point offset = root.getOffset();
            point.set(endAbsBounds.left - offset.x, endAbsBounds.top - offset.y);
        }
        if (!TransitionUtil.isClosingType(change.getMode())) {
            rect3.set(change.getEndAbsBounds());
        } else {
            rect3.set(startAbsBounds);
            point.offset(startAbsBounds.left - endAbsBounds.left, startAbsBounds.top - endAbsBounds.top);
        }
    }
}
