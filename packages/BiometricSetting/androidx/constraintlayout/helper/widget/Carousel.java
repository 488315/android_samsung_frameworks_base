package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.R$styleable;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Carousel extends MotionHelper {
    private int mBackwardTransition;
    private float mDampening;
    private int mEmptyViewBehavior;
    private int mFirstViewReference;
    private int mForwardTransition;
    private int mIndex;
    private boolean mInfiniteCarousel;
    private final ArrayList<View> mList;
    private MotionLayout mMotionLayout;
    private int mNextState;
    private int mPreviousState;
    private int mTouchUpMode;
    private float mVelocityThreshold;

    public interface Adapter {
    }

    public Carousel(Context context) {
        super(context);
        this.mList = new ArrayList<>();
        this.mIndex = 0;
        this.mFirstViewReference = -1;
        this.mInfiniteCarousel = false;
        this.mBackwardTransition = -1;
        this.mForwardTransition = -1;
        this.mPreviousState = -1;
        this.mNextState = -1;
        this.mDampening = 0.9f;
        this.mEmptyViewBehavior = 4;
        this.mTouchUpMode = 1;
        this.mVelocityThreshold = 2.0f;
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 2) {
                    this.mFirstViewReference = obtainStyledAttributes.getResourceId(index, this.mFirstViewReference);
                } else if (index == 0) {
                    this.mBackwardTransition = obtainStyledAttributes.getResourceId(index, this.mBackwardTransition);
                } else if (index == 3) {
                    this.mForwardTransition = obtainStyledAttributes.getResourceId(index, this.mForwardTransition);
                } else if (index == 1) {
                    this.mEmptyViewBehavior = obtainStyledAttributes.getInt(index, this.mEmptyViewBehavior);
                } else if (index == 6) {
                    this.mPreviousState = obtainStyledAttributes.getResourceId(index, this.mPreviousState);
                } else if (index == 5) {
                    this.mNextState = obtainStyledAttributes.getResourceId(index, this.mNextState);
                } else if (index == 8) {
                    this.mDampening = obtainStyledAttributes.getFloat(index, this.mDampening);
                } else if (index == 7) {
                    this.mTouchUpMode = obtainStyledAttributes.getInt(index, this.mTouchUpMode);
                } else if (index == 9) {
                    this.mVelocityThreshold = obtainStyledAttributes.getFloat(index, this.mVelocityThreshold);
                } else if (index == 4) {
                    this.mInfiniteCarousel = obtainStyledAttributes.getBoolean(index, this.mInfiniteCarousel);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public int getCount() {
        return 0;
    }

    public int getCurrentIndex() {
        return this.mIndex;
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof MotionLayout) {
            MotionLayout motionLayout = (MotionLayout) getParent();
            this.mList.clear();
            for (int i = 0; i < this.mCount; i++) {
                this.mList.add(motionLayout.getViewById(this.mIds[i]));
            }
            this.mMotionLayout = motionLayout;
            if (this.mTouchUpMode == 2) {
                MotionScene.Transition transition = motionLayout.getTransition(this.mForwardTransition);
                if (transition != null) {
                    transition.setOnTouchUp();
                }
                MotionScene.Transition transition2 = this.mMotionLayout.getTransition(this.mBackwardTransition);
                if (transition2 != null) {
                    transition2.setOnTouchUp();
                }
            }
        }
    }

    @Override // android.view.View
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mList.clear();
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public final void onTransitionCompleted(int i) {
        int i2 = this.mIndex;
        if (i == this.mNextState) {
            this.mIndex = i2 + 1;
        } else if (i == this.mPreviousState) {
            this.mIndex = i2 - 1;
        }
        if (!this.mInfiniteCarousel) {
            throw null;
        }
        throw null;
    }

    public Carousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mList = new ArrayList<>();
        this.mIndex = 0;
        this.mFirstViewReference = -1;
        this.mInfiniteCarousel = false;
        this.mBackwardTransition = -1;
        this.mForwardTransition = -1;
        this.mPreviousState = -1;
        this.mNextState = -1;
        this.mDampening = 0.9f;
        this.mEmptyViewBehavior = 4;
        this.mTouchUpMode = 1;
        this.mVelocityThreshold = 2.0f;
        init(context, attributeSet);
    }

    public Carousel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mList = new ArrayList<>();
        this.mIndex = 0;
        this.mFirstViewReference = -1;
        this.mInfiniteCarousel = false;
        this.mBackwardTransition = -1;
        this.mForwardTransition = -1;
        this.mPreviousState = -1;
        this.mNextState = -1;
        this.mDampening = 0.9f;
        this.mEmptyViewBehavior = 4;
        this.mTouchUpMode = 1;
        this.mVelocityThreshold = 2.0f;
        init(context, attributeSet);
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public final void onTransitionChange() {
    }

    public void setAdapter(Adapter adapter) {
    }
}
