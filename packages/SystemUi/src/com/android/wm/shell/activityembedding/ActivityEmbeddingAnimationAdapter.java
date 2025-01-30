package com.android.wm.shell.activityembedding;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.TransitionInfo;
import com.android.wm.shell.util.TransitionUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ActivityEmbeddingAnimationAdapter {
    public final Animation mAnimation;
    public final TransitionInfo.Change mChange;
    public final Rect mContentBounds;
    public final Point mContentRelOffset;
    public final SurfaceControl mLeash;
    public final float[] mMatrix;
    public int mOverrideLayer;
    public final Rect mRect;
    public final Transformation mTransformation;
    public final float[] mVecs;
    public final Rect mWholeAnimationBounds;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BoundsChangeAdapter extends ActivityEmbeddingAnimationAdapter {
        public BoundsChangeAdapter(Animation animation, TransitionInfo.Change change, TransitionInfo.Root root) {
            super(animation, change, root);
        }

        @Override // com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter
        public final void onAnimationUpdateInner(SurfaceControl.Transaction transaction) {
            Transformation transformation = this.mTransformation;
            Matrix matrix = transformation.getMatrix();
            Point point = this.mContentRelOffset;
            matrix.postTranslate(point.x, point.y);
            Matrix matrix2 = transformation.getMatrix();
            float[] fArr = this.mMatrix;
            SurfaceControl surfaceControl = this.mLeash;
            transaction.setMatrix(surfaceControl, matrix2, fArr);
            transaction.setAlpha(surfaceControl, transformation.getAlpha());
            float[] fArr2 = this.mVecs;
            fArr2[2] = 0.0f;
            fArr2[1] = 0.0f;
            fArr2[3] = 1.0f;
            fArr2[0] = 1.0f;
            transformation.getMatrix().mapVectors(fArr2);
            fArr2[0] = 1.0f / fArr2[0];
            fArr2[3] = 1.0f / fArr2[3];
            Rect clipRect = transformation.getClipRect();
            float f = clipRect.left;
            float f2 = fArr2[0];
            Rect rect = this.mRect;
            rect.left = (int) ((f * f2) + 0.5f);
            rect.right = (int) ((clipRect.right * f2) + 0.5f);
            float f3 = clipRect.top;
            float f4 = fArr2[3];
            rect.top = (int) ((f3 * f4) + 0.5f);
            rect.bottom = (int) ((clipRect.bottom * f4) + 0.5f);
            transaction.setCrop(surfaceControl, rect);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SnapshotAdapter extends ActivityEmbeddingAnimationAdapter {
        public SnapshotAdapter(Animation animation, TransitionInfo.Change change, SurfaceControl surfaceControl, TransitionInfo.Root root) {
            super(animation, change, surfaceControl, change.getEndAbsBounds(), root);
        }

        @Override // com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter
        public final void onAnimationEnd(SurfaceControl.Transaction transaction) {
            super.onAnimationEnd(transaction);
            SurfaceControl surfaceControl = this.mLeash;
            if (surfaceControl.isValid()) {
                transaction.remove(surfaceControl);
            }
        }

        @Override // com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter
        public final void onAnimationUpdateInner(SurfaceControl.Transaction transaction) {
            Transformation transformation = this.mTransformation;
            transformation.getMatrix().postTranslate(0.0f, 0.0f);
            Matrix matrix = transformation.getMatrix();
            float[] fArr = this.mMatrix;
            SurfaceControl surfaceControl = this.mLeash;
            transaction.setMatrix(surfaceControl, matrix, fArr);
            transaction.setAlpha(surfaceControl, transformation.getAlpha());
        }
    }

    public ActivityEmbeddingAnimationAdapter(Animation animation, TransitionInfo.Change change, TransitionInfo.Root root) {
        this(animation, change, change.getLeash(), change.getEndAbsBounds(), root);
    }

    public void onAnimationEnd(SurfaceControl.Transaction transaction) {
        Animation animation = this.mAnimation;
        animation.getTransformation(Math.min(animation.getDuration(), animation.getDuration()), this.mTransformation);
        onAnimationUpdateInner(transaction);
    }

    public void onAnimationUpdateInner(SurfaceControl.Transaction transaction) {
        Transformation transformation = this.mTransformation;
        Matrix matrix = transformation.getMatrix();
        Point point = this.mContentRelOffset;
        matrix.postTranslate(point.x, point.y);
        Matrix matrix2 = transformation.getMatrix();
        SurfaceControl surfaceControl = this.mLeash;
        float[] fArr = this.mMatrix;
        transaction.setMatrix(surfaceControl, matrix2, fArr);
        transaction.setAlpha(surfaceControl, transformation.getAlpha());
        int round = Math.round(fArr[2]);
        int round2 = Math.round(fArr[5]);
        Rect rect = this.mContentBounds;
        Rect rect2 = new Rect(rect);
        rect2.offset(round - point.x, round2 - point.y);
        int i = rect2.left;
        int i2 = rect2.top;
        if (!rect2.intersect(this.mWholeAnimationBounds)) {
            transaction.setAlpha(surfaceControl, 0.0f);
        } else if (this.mAnimation.hasExtension()) {
            rect2.union(rect);
        }
        rect2.offset(-i, -i2);
        transaction.setCrop(surfaceControl, rect2);
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
        this.mVecs = new float[4];
        this.mRect = new Rect();
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
