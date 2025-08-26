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
import androidx.constraintlayout.widget.R;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ViewTransition {
    public final ConstraintSet.Constraint mConstraintDelta;
    public final Context mContext;
    public int mId;
    public final KeyFrames mKeyFrames;
    public int mTargetId;
    public String mTargetString;
    public int mViewTransitionMode;
    public int mOnStateTransition = -1;
    public boolean mDisabled = false;
    public int mPathMotionArc = 0;
    public int mDuration = -1;
    public int mUpDuration = -1;
    public int mDefaultInterpolator = 0;
    public String mDefaultInterpolatorString = null;
    public int mDefaultInterpolatorID = -1;
    public int mSetsTag = -1;
    public int mClearsTag = -1;
    public int mIfTagSet = -1;
    public int mIfTagNotSet = -1;
    public int mSharedValueTarget = -1;
    public int mSharedValueID = -1;
    public int mSharedValueCurrent = -1;

    public class Animate {
        public final int mClearsTag;
        public float mDpositionDt;
        public final boolean mHoldAt100;
        public final Interpolator mInterpolator;
        public final MotionController mMC;
        public float mPosition;
        public final int mSetsTag;
        public final int mUpDuration;
        public final ViewTransitionController mVtController;
        public final KeyCache mCache = new KeyCache();
        public boolean mReverse = false;
        public final Rect mTempRec = new Rect();
        public long mLastRender = System.nanoTime();

        public Animate(ViewTransitionController viewTransitionController, MotionController motionController, int i, int i2, int i3, Interpolator interpolator, int i4, int i5) {
            this.mHoldAt100 = false;
            this.mVtController = viewTransitionController;
            this.mMC = motionController;
            this.mUpDuration = i2;
            if (viewTransitionController.mAnimations == null) {
                viewTransitionController.mAnimations = new ArrayList();
            }
            viewTransitionController.mAnimations.add(this);
            this.mInterpolator = interpolator;
            this.mSetsTag = i4;
            this.mClearsTag = i5;
            if (i3 == 3) {
                this.mHoldAt100 = true;
            }
            this.mDpositionDt = i == 0 ? Float.MAX_VALUE : 1.0f / i;
            mutate();
        }

        public final void mutate() {
            boolean z = this.mReverse;
            ViewTransitionController viewTransitionController = this.mVtController;
            MotionController motionController = this.mMC;
            int i = this.mClearsTag;
            int i2 = this.mSetsTag;
            if (z) {
                long jNanoTime = System.nanoTime();
                long j = jNanoTime - this.mLastRender;
                this.mLastRender = jNanoTime;
                float f = this.mPosition - (((float) (j * 1.0E-6d)) * this.mDpositionDt);
                this.mPosition = f;
                if (f < 0.0f) {
                    this.mPosition = 0.0f;
                }
                Interpolator interpolator = this.mInterpolator;
                boolean zInterpolate = motionController.interpolate(interpolator == null ? this.mPosition : interpolator.getInterpolation(this.mPosition), jNanoTime, motionController.mView, this.mCache);
                if (this.mPosition <= 0.0f) {
                    if (i2 != -1) {
                        motionController.mView.setTag(i2, Long.valueOf(System.nanoTime()));
                    }
                    if (i != -1) {
                        motionController.mView.setTag(i, null);
                    }
                    viewTransitionController.mRemoveList.add(this);
                }
                if (this.mPosition > 0.0f || zInterpolate) {
                    viewTransitionController.mMotionLayout.invalidate();
                    return;
                }
                return;
            }
            long jNanoTime2 = System.nanoTime();
            long j2 = jNanoTime2 - this.mLastRender;
            this.mLastRender = jNanoTime2;
            float f2 = (((float) (j2 * 1.0E-6d)) * this.mDpositionDt) + this.mPosition;
            this.mPosition = f2;
            if (f2 >= 1.0f) {
                this.mPosition = 1.0f;
            }
            Interpolator interpolator2 = this.mInterpolator;
            boolean zInterpolate2 = motionController.interpolate(interpolator2 == null ? this.mPosition : interpolator2.getInterpolation(this.mPosition), jNanoTime2, motionController.mView, this.mCache);
            if (this.mPosition >= 1.0f) {
                if (i2 != -1) {
                    motionController.mView.setTag(i2, Long.valueOf(System.nanoTime()));
                }
                if (i != -1) {
                    motionController.mView.setTag(i, null);
                }
                if (!this.mHoldAt100) {
                    viewTransitionController.mRemoveList.add(this);
                }
            }
            if (this.mPosition < 1.0f || zInterpolate2) {
                viewTransitionController.mMotionLayout.invalidate();
            }
        }

        public final void reverse() {
            this.mReverse = true;
            int i = this.mUpDuration;
            if (i != -1) {
                this.mDpositionDt = i == 0 ? Float.MAX_VALUE : 1.0f / i;
            }
            this.mVtController.mMotionLayout.invalidate();
            this.mLastRender = System.nanoTime();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ViewTransition(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        char c;
        this.mContext = context;
        try {
            int eventType = xmlPullParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    String name = xmlPullParser.getName();
                    switch (name.hashCode()) {
                        case -1962203927:
                            if (!name.equals("ConstraintOverride")) {
                                c = 65535;
                                break;
                            } else {
                                c = 2;
                                break;
                            }
                        case -1239391468:
                            if (name.equals("KeyFrameSet")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 61998586:
                            if (name.equals("ViewTransition")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 366511058:
                            if (name.equals("CustomMethod")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 1791837707:
                            if (name.equals("CustomAttribute")) {
                                c = 3;
                                break;
                            }
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

    public final void applyTransition(ViewTransitionController viewTransitionController, MotionLayout motionLayout, int i, ConstraintSet constraintSet, final View... viewArr) {
        Interpolator interpolatorLoadInterpolator;
        Interpolator interpolator;
        if (this.mDisabled) {
            return;
        }
        int i2 = this.mViewTransitionMode;
        KeyFrames keyFrames = this.mKeyFrames;
        if (i2 == 2) {
            View view = viewArr[0];
            MotionController motionController = new MotionController(view);
            MotionPaths motionPaths = motionController.mStartMotionPath;
            motionPaths.mTime = 0.0f;
            motionPaths.mPosition = 0.0f;
            motionController.mNoMovement = true;
            motionPaths.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
            motionController.mEndMotionPath.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
            MotionConstrainedPoint motionConstrainedPoint = motionController.mStartPoint;
            motionConstrainedPoint.getClass();
            view.getX();
            view.getY();
            view.getWidth();
            view.getHeight();
            motionConstrainedPoint.applyParameters(view);
            MotionConstrainedPoint motionConstrainedPoint2 = motionController.mEndPoint;
            motionConstrainedPoint2.getClass();
            view.getX();
            view.getY();
            view.getWidth();
            view.getHeight();
            motionConstrainedPoint2.applyParameters(view);
            ArrayList arrayList = (ArrayList) keyFrames.mFramesMap.get(-1);
            if (arrayList != null) {
                motionController.mKeyList.addAll(arrayList);
            }
            motionController.setup(motionLayout.getWidth(), motionLayout.getHeight(), System.nanoTime());
            int i3 = this.mDuration;
            int i4 = this.mUpDuration;
            int i5 = this.mOnStateTransition;
            Context context = motionLayout.getContext();
            int i6 = this.mDefaultInterpolator;
            if (i6 == -2) {
                interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(context, this.mDefaultInterpolatorID);
            } else {
                if (i6 == -1) {
                    final Easing interpolator2 = Easing.getInterpolator(this.mDefaultInterpolatorString);
                    interpolator = new Interpolator(this) { // from class: androidx.constraintlayout.motion.widget.ViewTransition.1
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return (float) interpolator2.get(f);
                        }
                    };
                    new Animate(viewTransitionController, motionController, i3, i4, i5, interpolator, this.mSetsTag, this.mClearsTag);
                    return;
                }
                interpolatorLoadInterpolator = i6 != 0 ? i6 != 1 ? i6 != 2 ? i6 != 4 ? i6 != 5 ? i6 != 6 ? null : new AnticipateInterpolator() : new OvershootInterpolator() : new BounceInterpolator() : new DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();
            }
            interpolator = interpolatorLoadInterpolator;
            new Animate(viewTransitionController, motionController, i3, i4, i5, interpolator, this.mSetsTag, this.mClearsTag);
            return;
        }
        ConstraintSet.Constraint constraint = this.mConstraintDelta;
        if (i2 == 1) {
            for (int i7 : motionLayout.getConstraintSetIds()) {
                if (i7 != i) {
                    ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i7);
                    for (View view2 : viewArr) {
                        ConstraintSet.Constraint constraint2 = constraintSet2.getConstraint(view2.getId());
                        if (constraint != null) {
                            constraint.applyDelta(constraint2);
                            constraint2.mCustomConstraints.putAll(constraint.mCustomConstraints);
                        }
                    }
                }
            }
        }
        ConstraintSet constraintSet3 = new ConstraintSet();
        constraintSet3.clone(constraintSet);
        for (View view3 : viewArr) {
            ConstraintSet.Constraint constraint3 = constraintSet3.getConstraint(view3.getId());
            if (constraint != null) {
                constraint.applyDelta(constraint3);
                constraint3.mCustomConstraints.putAll(constraint.mCustomConstraints);
            }
        }
        motionLayout.updateState(i, constraintSet3);
        int i8 = R.id.view_transition;
        motionLayout.updateState(i8, constraintSet);
        motionLayout.setState(i8, -1, -1);
        MotionScene.Transition transition = new MotionScene.Transition(-1, motionLayout.mScene, i8, i);
        for (View view4 : viewArr) {
            int i9 = this.mDuration;
            if (i9 != -1) {
                transition.mDuration = Math.max(i9, 8);
            }
            transition.mPathMotionArc = this.mPathMotionArc;
            int i10 = this.mDefaultInterpolator;
            String str = this.mDefaultInterpolatorString;
            int i11 = this.mDefaultInterpolatorID;
            transition.mDefaultInterpolator = i10;
            transition.mDefaultInterpolatorString = str;
            transition.mDefaultInterpolatorID = i11;
            int id = view4.getId();
            if (keyFrames != null) {
                ArrayList arrayList2 = (ArrayList) keyFrames.mFramesMap.get(-1);
                KeyFrames keyFrames2 = new KeyFrames();
                int size = arrayList2.size();
                int i12 = 0;
                while (i12 < size) {
                    Object obj = arrayList2.get(i12);
                    i12++;
                    Key keyMo888clone = ((Key) obj).mo888clone();
                    keyMo888clone.mTargetId = id;
                    keyFrames2.addKey(keyMo888clone);
                }
                transition.mKeyFramesList.add(keyFrames2);
            }
        }
        motionLayout.setTransition(transition);
        motionLayout.transitionToEnd(new Runnable() { // from class: androidx.constraintlayout.motion.widget.ViewTransition$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ViewTransition viewTransition = this.f$0;
                View[] viewArr2 = viewArr;
                if (viewTransition.mSetsTag != -1) {
                    for (View view5 : viewArr2) {
                        view5.setTag(viewTransition.mSetsTag, Long.valueOf(System.nanoTime()));
                    }
                }
                if (viewTransition.mClearsTag != -1) {
                    for (View view6 : viewArr2) {
                        view6.setTag(viewTransition.mClearsTag, null);
                    }
                }
            }
        });
    }

    public final boolean checkTags(View view) {
        int i = this.mIfTagSet;
        boolean z = i == -1 || view.getTag(i) != null;
        int i2 = this.mIfTagNotSet;
        return z && (i2 == -1 || view.getTag(i2) == null);
    }

    public final boolean matchesView(View view) {
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

    public final void parseViewTransitionTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.ViewTransition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R.styleable.ViewTransition_android_id) {
                this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
            } else if (index == R.styleable.ViewTransition_motionTarget) {
                if (MotionLayout.IS_IN_EDIT_MODE) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                    this.mTargetId = resourceId;
                    if (resourceId == -1) {
                        this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                    }
                } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                    this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                } else {
                    this.mTargetId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                }
            } else if (index == R.styleable.ViewTransition_onStateTransition) {
                this.mOnStateTransition = typedArrayObtainStyledAttributes.getInt(index, this.mOnStateTransition);
            } else if (index == R.styleable.ViewTransition_transitionDisable) {
                this.mDisabled = typedArrayObtainStyledAttributes.getBoolean(index, this.mDisabled);
            } else if (index == R.styleable.ViewTransition_pathMotionArc) {
                this.mPathMotionArc = typedArrayObtainStyledAttributes.getInt(index, this.mPathMotionArc);
            } else if (index == R.styleable.ViewTransition_duration) {
                this.mDuration = typedArrayObtainStyledAttributes.getInt(index, this.mDuration);
            } else if (index == R.styleable.ViewTransition_upDuration) {
                this.mUpDuration = typedArrayObtainStyledAttributes.getInt(index, this.mUpDuration);
            } else if (index == R.styleable.ViewTransition_viewTransitionMode) {
                this.mViewTransitionMode = typedArrayObtainStyledAttributes.getInt(index, this.mViewTransitionMode);
            } else if (index == R.styleable.ViewTransition_motionInterpolator) {
                int i2 = typedArrayObtainStyledAttributes.peekValue(index).type;
                if (i2 == 1) {
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    this.mDefaultInterpolatorID = resourceId2;
                    if (resourceId2 != -1) {
                        this.mDefaultInterpolator = -2;
                    }
                } else if (i2 == 3) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mDefaultInterpolatorString = string;
                    if (string == null || string.indexOf("/") <= 0) {
                        this.mDefaultInterpolator = -1;
                    } else {
                        this.mDefaultInterpolatorID = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolator = -2;
                    }
                } else {
                    this.mDefaultInterpolator = typedArrayObtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                }
            } else if (index == R.styleable.ViewTransition_setsTag) {
                this.mSetsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mSetsTag);
            } else if (index == R.styleable.ViewTransition_clearsTag) {
                this.mClearsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mClearsTag);
            } else if (index == R.styleable.ViewTransition_ifTagSet) {
                this.mIfTagSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagSet);
            } else if (index == R.styleable.ViewTransition_ifTagNotSet) {
                this.mIfTagNotSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagNotSet);
            } else if (index == R.styleable.ViewTransition_SharedValueId) {
                this.mSharedValueID = typedArrayObtainStyledAttributes.getResourceId(index, this.mSharedValueID);
            } else if (index == R.styleable.ViewTransition_SharedValue) {
                this.mSharedValueTarget = typedArrayObtainStyledAttributes.getInteger(index, this.mSharedValueTarget);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final String toString() {
        return "ViewTransition(" + Debug.getName(this.mId, this.mContext) + ")";
    }
}
