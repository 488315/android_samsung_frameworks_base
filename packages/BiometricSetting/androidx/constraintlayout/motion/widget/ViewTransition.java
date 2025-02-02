package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R$styleable;
import com.samsung.android.biometrics.app.setting.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ViewTransition {
    ConstraintSet.Constraint mConstraintDelta;
    Context mContext;
    private int mId;
    KeyFrames mKeyFrames;
    private int mTargetId;
    private String mTargetString;
    int mViewTransitionMode;
    private int mOnStateTransition = -1;
    private boolean mDisabled = false;
    private int mPathMotionArc = 0;
    private int mDuration = -1;
    private int mUpDuration = -1;
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private int mDefaultInterpolatorID = -1;
    private int mSetsTag = -1;
    private int mClearsTag = -1;
    private int mIfTagSet = -1;
    private int mIfTagNotSet = -1;
    private int mSharedValueTarget = -1;
    private int mSharedValueID = -1;

    static class Animate {
        private final int mClearsTag;
        float mDpositionDt;
        boolean mHoldAt100;
        Interpolator mInterpolator;
        MotionController mMC;
        float mPosition;
        private final int mSetsTag;
        int mUpDuration;
        ViewTransitionController mVtController;
        KeyCache mCache = new KeyCache();
        boolean mReverse = false;
        Rect mTempRec = new Rect();
        long mLastRender = System.nanoTime();

        Animate(ViewTransitionController viewTransitionController, MotionController motionController, int i, int i2, int i3, Interpolator interpolator, int i4, int i5) {
            this.mHoldAt100 = false;
            this.mVtController = viewTransitionController;
            this.mMC = motionController;
            this.mUpDuration = i2;
            ViewTransitionController viewTransitionController2 = this.mVtController;
            if (viewTransitionController2.mAnimations == null) {
                viewTransitionController2.mAnimations = new ArrayList<>();
            }
            viewTransitionController2.mAnimations.add(this);
            this.mInterpolator = interpolator;
            this.mSetsTag = i4;
            this.mClearsTag = i5;
            if (i3 == 3) {
                this.mHoldAt100 = true;
            }
            this.mDpositionDt = i == 0 ? Float.MAX_VALUE : 1.0f / i;
            mutate();
        }

        final void mutate() {
            boolean z = this.mReverse;
            int i = this.mClearsTag;
            int i2 = this.mSetsTag;
            if (z) {
                long nanoTime = System.nanoTime();
                long j = nanoTime - this.mLastRender;
                this.mLastRender = nanoTime;
                float f = this.mPosition - (((float) (j * 1.0E-6d)) * this.mDpositionDt);
                this.mPosition = f;
                if (f < 0.0f) {
                    this.mPosition = 0.0f;
                }
                Interpolator interpolator = this.mInterpolator;
                float interpolation = interpolator == null ? this.mPosition : interpolator.getInterpolation(this.mPosition);
                MotionController motionController = this.mMC;
                boolean interpolate = motionController.interpolate(interpolation, nanoTime, motionController.mView, this.mCache);
                if (this.mPosition <= 0.0f) {
                    if (i2 != -1) {
                        this.mMC.mView.setTag(i2, Long.valueOf(System.nanoTime()));
                    }
                    if (i != -1) {
                        this.mMC.mView.setTag(i, null);
                    }
                    this.mVtController.mRemoveList.add(this);
                }
                if (this.mPosition > 0.0f || interpolate) {
                    this.mVtController.invalidate();
                    return;
                }
                return;
            }
            long nanoTime2 = System.nanoTime();
            long j2 = nanoTime2 - this.mLastRender;
            this.mLastRender = nanoTime2;
            float f2 = (((float) (j2 * 1.0E-6d)) * this.mDpositionDt) + this.mPosition;
            this.mPosition = f2;
            if (f2 >= 1.0f) {
                this.mPosition = 1.0f;
            }
            Interpolator interpolator2 = this.mInterpolator;
            float interpolation2 = interpolator2 == null ? this.mPosition : interpolator2.getInterpolation(this.mPosition);
            MotionController motionController2 = this.mMC;
            boolean interpolate2 = motionController2.interpolate(interpolation2, nanoTime2, motionController2.mView, this.mCache);
            if (this.mPosition >= 1.0f) {
                if (i2 != -1) {
                    this.mMC.mView.setTag(i2, Long.valueOf(System.nanoTime()));
                }
                if (i != -1) {
                    this.mMC.mView.setTag(i, null);
                }
                if (!this.mHoldAt100) {
                    this.mVtController.mRemoveList.add(this);
                }
            }
            if (this.mPosition < 1.0f || interpolate2) {
                this.mVtController.invalidate();
            }
        }

        final void reverse() {
            this.mReverse = true;
            int i = this.mUpDuration;
            if (i != -1) {
                this.mDpositionDt = i == 0 ? Float.MAX_VALUE : 1.0f / i;
            }
            this.mVtController.invalidate();
            this.mLastRender = System.nanoTime();
        }
    }

    /* renamed from: $r8$lambda$R5MnrVoAl7eqn8MpswIds-XSqNs, reason: not valid java name */
    public static /* synthetic */ void m16$r8$lambda$R5MnrVoAl7eqn8MpswIdsXSqNs(ViewTransition viewTransition, View[] viewArr) {
        if (viewTransition.mSetsTag != -1) {
            for (View view : viewArr) {
                view.setTag(viewTransition.mSetsTag, Long.valueOf(System.nanoTime()));
            }
        }
        if (viewTransition.mClearsTag != -1) {
            for (View view2 : viewArr) {
                view2.setTag(viewTransition.mClearsTag, null);
            }
        }
    }

    ViewTransition(Context context, XmlPullParser xmlPullParser) {
        char c;
        this.mContext = context;
        try {
            int eventType = xmlPullParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    String name = xmlPullParser.getName();
                    switch (name.hashCode()) {
                        case -1962203927:
                            if (name.equals("ConstraintOverride")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1239391468:
                            if (name.equals("KeyFrameSet")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 61998586:
                            if (name.equals("ViewTransition")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 366511058:
                            if (name.equals("CustomMethod")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1791837707:
                            if (name.equals("CustomAttribute")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    if (c == 0) {
                        parseViewTransitionTags(context, xmlPullParser);
                    } else if (c == 1) {
                        this.mKeyFrames = new KeyFrames(context, xmlPullParser);
                    } else if (c == 2) {
                        this.mConstraintDelta = ConstraintSet.buildDelta(context, xmlPullParser);
                    } else if (c == 3 || c == 4) {
                        ConstraintAttribute.parse(context, xmlPullParser, this.mConstraintDelta.mCustomConstraints);
                    } else {
                        Log.e("ViewTransition", Debug.getLoc() + " unknown tag " + name);
                        StringBuilder sb = new StringBuilder();
                        sb.append(".xml:");
                        sb.append(xmlPullParser.getLineNumber());
                        Log.e("ViewTransition", sb.toString());
                    }
                } else if (eventType == 3 && "ViewTransition".equals(xmlPullParser.getName())) {
                    return;
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e) {
            Log.e("ViewTransition", "Error parsing XML resource", e);
        } catch (XmlPullParserException e2) {
            Log.e("ViewTransition", "Error parsing XML resource", e2);
        }
    }

    private void parseViewTransitionTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.ViewTransition);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                this.mId = obtainStyledAttributes.getResourceId(index, this.mId);
            } else if (index == 8) {
                if (MotionLayout.IS_IN_EDIT_MODE) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                    this.mTargetId = resourceId;
                    if (resourceId == -1) {
                        this.mTargetString = obtainStyledAttributes.getString(index);
                    }
                } else if (obtainStyledAttributes.peekValue(index).type == 3) {
                    this.mTargetString = obtainStyledAttributes.getString(index);
                } else {
                    this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                }
            } else if (index == 9) {
                this.mOnStateTransition = obtainStyledAttributes.getInt(index, this.mOnStateTransition);
            } else if (index == 12) {
                this.mDisabled = obtainStyledAttributes.getBoolean(index, this.mDisabled);
            } else if (index == 10) {
                this.mPathMotionArc = obtainStyledAttributes.getInt(index, this.mPathMotionArc);
            } else if (index == 4) {
                this.mDuration = obtainStyledAttributes.getInt(index, this.mDuration);
            } else if (index == 13) {
                this.mUpDuration = obtainStyledAttributes.getInt(index, this.mUpDuration);
            } else if (index == 14) {
                this.mViewTransitionMode = obtainStyledAttributes.getInt(index, this.mViewTransitionMode);
            } else if (index == 7) {
                int i2 = obtainStyledAttributes.peekValue(index).type;
                if (i2 == 1) {
                    int resourceId2 = obtainStyledAttributes.getResourceId(index, -1);
                    this.mDefaultInterpolatorID = resourceId2;
                    if (resourceId2 != -1) {
                        this.mDefaultInterpolator = -2;
                    }
                } else if (i2 == 3) {
                    String string = obtainStyledAttributes.getString(index);
                    this.mDefaultInterpolatorString = string;
                    if (string == null || string.indexOf("/") <= 0) {
                        this.mDefaultInterpolator = -1;
                    } else {
                        this.mDefaultInterpolatorID = obtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolator = -2;
                    }
                } else {
                    this.mDefaultInterpolator = obtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                }
            } else if (index == 11) {
                this.mSetsTag = obtainStyledAttributes.getResourceId(index, this.mSetsTag);
            } else if (index == 3) {
                this.mClearsTag = obtainStyledAttributes.getResourceId(index, this.mClearsTag);
            } else if (index == 6) {
                this.mIfTagSet = obtainStyledAttributes.getResourceId(index, this.mIfTagSet);
            } else if (index == 5) {
                this.mIfTagNotSet = obtainStyledAttributes.getResourceId(index, this.mIfTagNotSet);
            } else if (index == 2) {
                this.mSharedValueID = obtainStyledAttributes.getResourceId(index, this.mSharedValueID);
            } else if (index == 1) {
                this.mSharedValueTarget = obtainStyledAttributes.getInteger(index, this.mSharedValueTarget);
            }
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.constraintlayout.motion.widget.ViewTransition$$ExternalSyntheticLambda0] */
    final void applyTransition(ViewTransitionController viewTransitionController, MotionLayout motionLayout, int i, ConstraintSet constraintSet, final View... viewArr) {
        Interpolator loadInterpolator;
        Interpolator interpolator;
        if (this.mDisabled) {
            return;
        }
        int i2 = this.mViewTransitionMode;
        if (i2 == 2) {
            View view = viewArr[0];
            MotionController motionController = new MotionController(view);
            motionController.setBothStates(view);
            this.mKeyFrames.addAllFrames(motionController);
            motionController.setup(motionLayout.getWidth(), motionLayout.getHeight(), System.nanoTime());
            int i3 = this.mDuration;
            int i4 = this.mUpDuration;
            int i5 = this.mOnStateTransition;
            Context context = motionLayout.getContext();
            int i6 = this.mDefaultInterpolator;
            if (i6 == -2) {
                loadInterpolator = AnimationUtils.loadInterpolator(context, this.mDefaultInterpolatorID);
            } else {
                if (i6 == -1) {
                    final Easing interpolator2 = Easing.getInterpolator(this.mDefaultInterpolatorString);
                    interpolator = new Interpolator() { // from class: androidx.constraintlayout.motion.widget.ViewTransition.1
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return (float) Easing.this.get(f);
                        }
                    };
                    new Animate(viewTransitionController, motionController, i3, i4, i5, interpolator, this.mSetsTag, this.mClearsTag);
                    return;
                }
                loadInterpolator = i6 != 0 ? i6 != 1 ? i6 != 2 ? i6 != 4 ? i6 != 5 ? i6 != 6 ? null : new AnticipateInterpolator() : new OvershootInterpolator() : new BounceInterpolator() : new DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();
            }
            interpolator = loadInterpolator;
            new Animate(viewTransitionController, motionController, i3, i4, i5, interpolator, this.mSetsTag, this.mClearsTag);
            return;
        }
        if (i2 == 1) {
            for (int i7 : motionLayout.getConstraintSetIds()) {
                if (i7 != i) {
                    ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i7);
                    for (View view2 : viewArr) {
                        ConstraintSet.Constraint constraint = constraintSet2.getConstraint(view2.getId());
                        ConstraintSet.Constraint constraint2 = this.mConstraintDelta;
                        if (constraint2 != null) {
                            constraint2.applyDelta(constraint);
                            constraint.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
                        }
                    }
                }
            }
        }
        ConstraintSet constraintSet3 = new ConstraintSet();
        constraintSet3.clone(constraintSet);
        for (View view3 : viewArr) {
            ConstraintSet.Constraint constraint3 = constraintSet3.getConstraint(view3.getId());
            ConstraintSet.Constraint constraint4 = this.mConstraintDelta;
            if (constraint4 != null) {
                constraint4.applyDelta(constraint3);
                constraint3.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
            }
        }
        motionLayout.updateState(i, constraintSet3);
        motionLayout.updateState(R.id.view_transition, constraintSet);
        motionLayout.setState(R.id.view_transition);
        MotionScene.Transition transition = new MotionScene.Transition(motionLayout.mScene, i);
        for (View view4 : viewArr) {
            int i8 = this.mDuration;
            if (i8 != -1) {
                transition.setDuration(i8);
            }
            transition.setPathMotionArc(this.mPathMotionArc);
            transition.setInterpolatorInfo(this.mDefaultInterpolator, this.mDefaultInterpolatorID, this.mDefaultInterpolatorString);
            int id = view4.getId();
            KeyFrames keyFrames = this.mKeyFrames;
            if (keyFrames != null) {
                ArrayList keyFramesForView = keyFrames.getKeyFramesForView();
                KeyFrames keyFrames2 = new KeyFrames();
                Iterator it = keyFramesForView.iterator();
                while (it.hasNext()) {
                    Key mo15clone = ((Key) it.next()).mo15clone();
                    mo15clone.mTargetId = id;
                    keyFrames2.addKey(mo15clone);
                }
                transition.addKeyFrame(keyFrames2);
            }
        }
        motionLayout.setTransition(transition);
        motionLayout.transitionToEnd(new Runnable() { // from class: androidx.constraintlayout.motion.widget.ViewTransition$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ViewTransition.m16$r8$lambda$R5MnrVoAl7eqn8MpswIdsXSqNs(ViewTransition.this, viewArr);
            }
        });
    }

    final boolean checkTags(View view) {
        int i = this.mIfTagSet;
        boolean z = i == -1 || view.getTag(i) != null;
        int i2 = this.mIfTagNotSet;
        return z && (i2 == -1 || view.getTag(i2) == null);
    }

    final int getId() {
        return this.mId;
    }

    public final int getSharedValueID() {
        return this.mSharedValueID;
    }

    public final int getStateTransition() {
        return this.mOnStateTransition;
    }

    final boolean matchesView(View view) {
        String str;
        if (view == null) {
            return false;
        }
        if ((this.mTargetId == -1 && this.mTargetString == null) || !checkTags(view)) {
            return false;
        }
        if (view.getId() == this.mTargetId) {
            return true;
        }
        return this.mTargetString != null && (view.getLayoutParams() instanceof ConstraintLayout.LayoutParams) && (str = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).constraintTag) != null && str.matches(this.mTargetString);
    }

    final boolean supports(int i) {
        int i2 = this.mOnStateTransition;
        return i2 == 1 ? i == 0 : i2 == 2 ? i == 1 : i2 == 3 && i == 0;
    }

    public final String toString() {
        return "ViewTransition(" + Debug.getName(this.mContext, this.mId) + ")";
    }
}
