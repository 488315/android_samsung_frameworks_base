package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.motion.widget.TouchResponse;
import androidx.constraintlayout.widget.R$styleable;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Carousel extends MotionHelper {
    public int backwardTransition;
    public float dampening;
    public int emptyViewBehavior;
    public int firstViewReference;
    public int forwardTransition;
    public boolean infiniteCarousel;
    public int mIndex;
    public final ArrayList mList;
    public MotionLayout mMotionLayout;
    public final RunnableC01221 mUpdateRunnable;
    public int nextState;
    public int previousState;
    public int touchUpMode;
    public float velocityThreshold;

    /* JADX WARN: Type inference failed for: r2v7, types: [androidx.constraintlayout.helper.widget.Carousel$1] */
    public Carousel(Context context) {
        super(context);
        this.mList = new ArrayList();
        this.mIndex = 0;
        this.firstViewReference = -1;
        this.infiniteCarousel = false;
        this.backwardTransition = -1;
        this.forwardTransition = -1;
        this.previousState = -1;
        this.nextState = -1;
        this.dampening = 0.9f;
        this.emptyViewBehavior = 4;
        this.touchUpMode = 1;
        this.velocityThreshold = 2.0f;
        this.mUpdateRunnable = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public final void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.getClass();
                Carousel.this.getClass();
                int i = Carousel.this.mIndex;
                throw null;
            }
        };
    }

    public final void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 2) {
                    this.firstViewReference = obtainStyledAttributes.getResourceId(index, this.firstViewReference);
                } else if (index == 0) {
                    this.backwardTransition = obtainStyledAttributes.getResourceId(index, this.backwardTransition);
                } else if (index == 3) {
                    this.forwardTransition = obtainStyledAttributes.getResourceId(index, this.forwardTransition);
                } else if (index == 1) {
                    this.emptyViewBehavior = obtainStyledAttributes.getInt(index, this.emptyViewBehavior);
                } else if (index == 6) {
                    this.previousState = obtainStyledAttributes.getResourceId(index, this.previousState);
                } else if (index == 5) {
                    this.nextState = obtainStyledAttributes.getResourceId(index, this.nextState);
                } else if (index == 8) {
                    this.dampening = obtainStyledAttributes.getFloat(index, this.dampening);
                } else if (index == 7) {
                    this.touchUpMode = obtainStyledAttributes.getInt(index, this.touchUpMode);
                } else if (index == 9) {
                    this.velocityThreshold = obtainStyledAttributes.getFloat(index, this.velocityThreshold);
                } else if (index == 4) {
                    this.infiniteCarousel = obtainStyledAttributes.getBoolean(index, this.infiniteCarousel);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public final void onAttachedToWindow() {
        TouchResponse touchResponse;
        TouchResponse touchResponse2;
        super.onAttachedToWindow();
        if (getParent() instanceof MotionLayout) {
            MotionLayout motionLayout = (MotionLayout) getParent();
            for (int i = 0; i < this.mCount; i++) {
                this.mList.add(motionLayout.getViewById(this.mIds[i]));
            }
            this.mMotionLayout = motionLayout;
            if (this.touchUpMode == 2) {
                MotionScene.Transition transition = motionLayout.getTransition(this.forwardTransition);
                if (transition != null && (touchResponse2 = transition.mTouchResponse) != null) {
                    touchResponse2.mOnTouchUp = 5;
                }
                MotionScene.Transition transition2 = this.mMotionLayout.getTransition(this.backwardTransition);
                if (transition2 == null || (touchResponse = transition2.mTouchResponse) == null) {
                    return;
                }
                touchResponse.mOnTouchUp = 5;
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public final void onTransitionCompleted(int i) {
        int i2 = this.mIndex;
        if (i == this.nextState) {
            this.mIndex = i2 + 1;
        } else if (i == this.previousState) {
            this.mIndex = i2 - 1;
        }
        if (!this.infiniteCarousel) {
            throw null;
        }
        throw null;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.constraintlayout.helper.widget.Carousel$1] */
    public Carousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mList = new ArrayList();
        this.mIndex = 0;
        this.firstViewReference = -1;
        this.infiniteCarousel = false;
        this.backwardTransition = -1;
        this.forwardTransition = -1;
        this.previousState = -1;
        this.nextState = -1;
        this.dampening = 0.9f;
        this.emptyViewBehavior = 4;
        this.touchUpMode = 1;
        this.velocityThreshold = 2.0f;
        this.mUpdateRunnable = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public final void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.getClass();
                Carousel.this.getClass();
                int i = Carousel.this.mIndex;
                throw null;
            }
        };
        init(context, attributeSet);
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public final void onTransitionChange() {
    }

    /* JADX WARN: Type inference failed for: r4v7, types: [androidx.constraintlayout.helper.widget.Carousel$1] */
    public Carousel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mList = new ArrayList();
        this.mIndex = 0;
        this.firstViewReference = -1;
        this.infiniteCarousel = false;
        this.backwardTransition = -1;
        this.forwardTransition = -1;
        this.previousState = -1;
        this.nextState = -1;
        this.dampening = 0.9f;
        this.emptyViewBehavior = 4;
        this.touchUpMode = 1;
        this.velocityThreshold = 2.0f;
        this.mUpdateRunnable = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public final void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.getClass();
                Carousel.this.getClass();
                int i2 = Carousel.this.mIndex;
                throw null;
            }
        };
        init(context, attributeSet);
    }
}
