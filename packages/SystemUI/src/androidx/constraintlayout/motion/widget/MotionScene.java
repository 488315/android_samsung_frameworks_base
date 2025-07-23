package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.SharedValues;
import androidx.constraintlayout.widget.StateSet;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MotionScene {
    public final ArrayList mAbstractTransitionList;
    public final HashMap mConstraintSetIdMap;
    public final SparseArray mConstraintSetMap;
    public Transition mCurrentTransition;
    public int mDefaultDuration;
    public final Transition mDefaultTransition;
    public final SparseIntArray mDeriveMap;
    public boolean mDisableAutoTransition;
    public boolean mIgnoreTouch;
    public MotionEvent mLastTouchDown;
    public float mLastTouchX;
    public float mLastTouchY;
    public int mLayoutDuringTransition;
    public final MotionLayout mMotionLayout;
    public boolean mMotionOutsideRegion;
    public boolean mRtl;
    public final StateSet mStateSet;
    public final ArrayList mTransitionList;
    public MotionLayout.MotionTracker mVelocityTracker;
    public final ViewTransitionController mViewTransitionController;

    public MotionScene(MotionLayout motionLayout) {
        this.mStateSet = null;
        this.mCurrentTransition = null;
        this.mDisableAutoTransition = false;
        this.mTransitionList = new ArrayList();
        this.mDefaultTransition = null;
        this.mAbstractTransitionList = new ArrayList();
        this.mConstraintSetMap = new SparseArray();
        this.mConstraintSetIdMap = new HashMap();
        this.mDeriveMap = new SparseIntArray();
        this.mDefaultDuration = 400;
        this.mLayoutDuringTransition = 0;
        this.mIgnoreTouch = false;
        this.mMotionOutsideRegion = false;
        this.mMotionLayout = motionLayout;
        this.mViewTransitionController = new ViewTransitionController(motionLayout);
    }

    public static int getId(Context context, String str) {
        int i;
        if (str.contains("/")) {
            i = context.getResources().getIdentifier(str.substring(str.indexOf(47) + 1), "id", context.getPackageName());
        } else {
            i = -1;
        }
        if (i == -1) {
            if (str.length() > 1) {
                return Integer.parseInt(str.substring(1));
            }
            Log.e("MotionScene", "error in parsing id");
        }
        return i;
    }

    public final boolean autoTransition(int i, MotionLayout motionLayout) {
        Transition transition;
        if (this.mVelocityTracker == null && !this.mDisableAutoTransition) {
            ArrayList arrayList = this.mTransitionList;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                Transition transition2 = (Transition) obj;
                int i3 = transition2.mAutoTransition;
                if (i3 != 0 && ((transition = this.mCurrentTransition) != transition2 || (transition.mTransitionFlags & 2) == 0)) {
                    if (i == transition2.mConstraintSetStart && (i3 == 4 || i3 == 2)) {
                        MotionLayout.TransitionState transitionState = MotionLayout.TransitionState.FINISHED;
                        motionLayout.setState(transitionState);
                        motionLayout.setTransition(transition2);
                        if (transition2.mAutoTransition == 4) {
                            motionLayout.transitionToEnd();
                            motionLayout.setState(MotionLayout.TransitionState.SETUP);
                            motionLayout.setState(MotionLayout.TransitionState.MOVING);
                            return true;
                        }
                        motionLayout.setProgress(1.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(transitionState);
                        motionLayout.onNewStateAttachHandlers();
                        return true;
                    }
                    if (i == transition2.mConstraintSetEnd && (i3 == 3 || i3 == 1)) {
                        MotionLayout.TransitionState transitionState2 = MotionLayout.TransitionState.FINISHED;
                        motionLayout.setState(transitionState2);
                        motionLayout.setTransition(transition2);
                        if (transition2.mAutoTransition == 3) {
                            motionLayout.transitionToStart();
                            motionLayout.setState(MotionLayout.TransitionState.SETUP);
                            motionLayout.setState(MotionLayout.TransitionState.MOVING);
                            return true;
                        }
                        motionLayout.setProgress(0.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(transitionState2);
                        motionLayout.onNewStateAttachHandlers();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final ConstraintSet getConstraintSet(int i) {
        int stateGetConstraintID;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (stateGetConstraintID = stateSet.stateGetConstraintID(i, -1, -1)) != -1) {
            i = stateGetConstraintID;
        }
        if (this.mConstraintSetMap.get(i) != null) {
            return (ConstraintSet) this.mConstraintSetMap.get(i);
        }
        Log.e("MotionScene", "Warning could not find ConstraintSet id/" + Debug.getName(i, this.mMotionLayout.getContext()) + " In MotionScene");
        SparseArray sparseArray = this.mConstraintSetMap;
        return (ConstraintSet) sparseArray.get(sparseArray.keyAt(0));
    }

    public final int getDuration() {
        Transition transition = this.mCurrentTransition;
        return transition != null ? transition.mDuration : this.mDefaultDuration;
    }

    public final Interpolator getInterpolator() {
        Transition transition = this.mCurrentTransition;
        int i = transition.mDefaultInterpolator;
        if (i == -2) {
            return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), this.mCurrentTransition.mDefaultInterpolatorID);
        }
        if (i == -1) {
            final Easing interpolator = Easing.getInterpolator(transition.mDefaultInterpolatorString);
            return new Interpolator(this) { // from class: androidx.constraintlayout.motion.widget.MotionScene.1
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    return (float) interpolator.get(f);
                }
            };
        }
        if (i == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (i == 1) {
            return new AccelerateInterpolator();
        }
        if (i == 2) {
            return new DecelerateInterpolator();
        }
        if (i == 4) {
            return new BounceInterpolator();
        }
        if (i == 5) {
            return new OvershootInterpolator();
        }
        if (i != 6) {
            return null;
        }
        return new AnticipateInterpolator();
    }

    public final void getKeyFrames(MotionController motionController) {
        Transition transition = this.mCurrentTransition;
        int i = 0;
        if (transition != null) {
            ArrayList arrayList = transition.mKeyFramesList;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((KeyFrames) obj).addFrames(motionController);
            }
            return;
        }
        Transition transition2 = this.mDefaultTransition;
        if (transition2 != null) {
            ArrayList arrayList2 = transition2.mKeyFramesList;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                ((KeyFrames) obj2).addFrames(motionController);
            }
        }
    }

    public final float getMaxAcceleration() {
        TouchResponse touchResponse;
        Transition transition = this.mCurrentTransition;
        if (transition == null || (touchResponse = transition.mTouchResponse) == null) {
            return 0.0f;
        }
        return touchResponse.mMaxAcceleration;
    }

    public final int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetStart;
    }

    public final List getTransitionsWithState(int i) {
        int stateGetConstraintID;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (stateGetConstraintID = stateSet.stateGetConstraintID(i, -1, -1)) != -1) {
            i = stateGetConstraintID;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = this.mTransitionList;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            Transition transition = (Transition) obj;
            if (transition.mConstraintSetStart == i || transition.mConstraintSetEnd == i) {
                arrayList.add(transition);
            }
        }
        return arrayList;
    }

    public final String lookUpConstraintName(int i) {
        for (Map.Entry entry : this.mConstraintSetIdMap.entrySet()) {
            Integer num = (Integer) entry.getValue();
            if (num != null && num.intValue() == i) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int parseConstraintSet(Context context, XmlPullParser xmlPullParser) {
        char c;
        char c2;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.setForceId(false);
        int attributeCount = xmlPullParser.getAttributeCount();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < attributeCount; i3++) {
            String attributeName = xmlPullParser.getAttributeName(i3);
            String attributeValue = xmlPullParser.getAttributeValue(i3);
            attributeName.getClass();
            switch (attributeName.hashCode()) {
                case -1496482599:
                    if (attributeName.equals("deriveConstraintsFrom")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1153153640:
                    if (attributeName.equals("constraintRotate")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3355:
                    if (attributeName.equals("id")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 973381616:
                    if (attributeName.equals("stateLabels")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    i2 = getId(context, attributeValue);
                    break;
                case 1:
                    try {
                        constraintSet.mRotate = Integer.parseInt(attributeValue);
                        break;
                    } catch (NumberFormatException unused) {
                        attributeValue.getClass();
                        switch (attributeValue.hashCode()) {
                            case -768416914:
                                if (attributeValue.equals("x_left")) {
                                    c2 = 0;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 3317767:
                                if (attributeValue.equals("left")) {
                                    c2 = 1;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 3387192:
                                if (attributeValue.equals(SignalSeverity.NONE)) {
                                    c2 = 2;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 108511772:
                                if (attributeValue.equals("right")) {
                                    c2 = 3;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1954540437:
                                if (attributeValue.equals("x_right")) {
                                    c2 = 4;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            default:
                                c2 = 65535;
                                break;
                        }
                        switch (c2) {
                            case 0:
                                constraintSet.mRotate = 4;
                                break;
                            case 1:
                                constraintSet.mRotate = 2;
                                break;
                            case 2:
                                constraintSet.mRotate = 0;
                                break;
                            case 3:
                                constraintSet.mRotate = 1;
                                break;
                            case 4:
                                constraintSet.mRotate = 3;
                                break;
                        }
                    }
                    break;
                case 2:
                    i = getId(context, attributeValue);
                    HashMap hashMap = this.mConstraintSetIdMap;
                    int indexOf = attributeValue.indexOf(47);
                    if (indexOf >= 0) {
                        attributeValue = attributeValue.substring(indexOf + 1);
                    }
                    hashMap.put(attributeValue, Integer.valueOf(i));
                    constraintSet.mIdString = Debug.getName(i, context);
                    break;
                case 3:
                    constraintSet.setStateLabels(attributeValue);
                    break;
            }
        }
        if (i != -1) {
            if (this.mMotionLayout.mDebugPath != 0) {
                constraintSet.setValidateOnParse(true);
            }
            constraintSet.load(context, xmlPullParser);
            if (i2 != -1) {
                this.mDeriveMap.put(i, i2);
            }
            this.mConstraintSetMap.put(i, constraintSet);
        }
        return i;
    }

    public final void parseInclude(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.include);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.include_constraintSet) {
                parseInclude(obtainStyledAttributes.getResourceId(index, -1), context);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final void parseMotionSceneTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.MotionScene);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.MotionScene_defaultDuration) {
                int i2 = obtainStyledAttributes.getInt(index, this.mDefaultDuration);
                this.mDefaultDuration = i2;
                if (i2 < 8) {
                    this.mDefaultDuration = 8;
                }
            } else if (index == R.styleable.MotionScene_layoutDuringTransition) {
                this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final void readConstraintChain(int i, MotionLayout motionLayout) {
        ConstraintSet constraintSet = (ConstraintSet) this.mConstraintSetMap.get(i);
        constraintSet.derivedState = constraintSet.mIdString;
        int i2 = this.mDeriveMap.get(i);
        if (i2 > 0) {
            readConstraintChain(i2, motionLayout);
            ConstraintSet constraintSet2 = (ConstraintSet) this.mConstraintSetMap.get(i2);
            if (constraintSet2 == null) {
                Log.e("MotionScene", "ERROR! invalid deriveConstraintsFrom: @id/" + Debug.getName(i2, this.mMotionLayout.getContext()));
                return;
            } else {
                constraintSet.derivedState += "/" + constraintSet2.derivedState;
                constraintSet.readFallback(constraintSet2);
            }
        } else {
            constraintSet.derivedState = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), constraintSet.derivedState, "  layout");
            constraintSet.readFallback(motionLayout);
        }
        constraintSet.applyDeltaFrom(constraintSet);
    }

    public final void readFallback(MotionLayout motionLayout) {
        for (int i = 0; i < this.mConstraintSetMap.size(); i++) {
            int keyAt = this.mConstraintSetMap.keyAt(i);
            int i2 = this.mDeriveMap.get(keyAt);
            int size = this.mDeriveMap.size();
            while (i2 > 0) {
                if (i2 != keyAt) {
                    int i3 = size - 1;
                    if (size >= 0) {
                        i2 = this.mDeriveMap.get(i2);
                        size = i3;
                    }
                }
                Log.e("MotionScene", "Cannot be derived from yourself");
                return;
            }
            readConstraintChain(keyAt, motionLayout);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
    
        if (r2 != (-1)) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setTransition(int r11, int r12) {
        /*
            r10 = this;
            androidx.constraintlayout.widget.StateSet r0 = r10.mStateSet
            r1 = -1
            if (r0 == 0) goto L18
            int r0 = r0.stateGetConstraintID(r11, r1, r1)
            if (r0 == r1) goto Lc
            goto Ld
        Lc:
            r0 = r11
        Ld:
            androidx.constraintlayout.widget.StateSet r2 = r10.mStateSet
            int r2 = r2.stateGetConstraintID(r12, r1, r1)
            if (r2 == r1) goto L16
            goto L1a
        L16:
            r2 = r12
            goto L1a
        L18:
            r0 = r11
            goto L16
        L1a:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r3 = r10.mCurrentTransition
            if (r3 == 0) goto L27
            int r4 = r3.mConstraintSetEnd
            if (r4 != r12) goto L27
            int r3 = r3.mConstraintSetStart
            if (r3 != r11) goto L27
            goto L52
        L27:
            java.util.ArrayList r3 = r10.mTransitionList
            int r4 = r3.size()
            r5 = 0
            r6 = r5
        L2f:
            if (r6 >= r4) goto L53
            java.lang.Object r7 = r3.get(r6)
            int r6 = r6 + 1
            androidx.constraintlayout.motion.widget.MotionScene$Transition r7 = (androidx.constraintlayout.motion.widget.MotionScene.Transition) r7
            int r8 = r7.mConstraintSetEnd
            if (r8 != r2) goto L41
            int r9 = r7.mConstraintSetStart
            if (r9 == r0) goto L47
        L41:
            if (r8 != r12) goto L2f
            int r8 = r7.mConstraintSetStart
            if (r8 != r11) goto L2f
        L47:
            r10.mCurrentTransition = r7
            androidx.constraintlayout.motion.widget.TouchResponse r11 = r7.mTouchResponse
            if (r11 == 0) goto L52
            boolean r10 = r10.mRtl
            r11.setRTL(r10)
        L52:
            return
        L53:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r11 = r10.mDefaultTransition
            java.util.ArrayList r3 = r10.mAbstractTransitionList
            int r4 = r3.size()
        L5b:
            if (r5 >= r4) goto L6b
            java.lang.Object r6 = r3.get(r5)
            int r5 = r5 + 1
            androidx.constraintlayout.motion.widget.MotionScene$Transition r6 = (androidx.constraintlayout.motion.widget.MotionScene.Transition) r6
            int r7 = r6.mConstraintSetEnd
            if (r7 != r12) goto L5b
            r11 = r6
            goto L5b
        L6b:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r12 = new androidx.constraintlayout.motion.widget.MotionScene$Transition
            r12.<init>(r10, r11)
            r12.mConstraintSetStart = r0
            r12.mConstraintSetEnd = r2
            if (r0 == r1) goto L7b
            java.util.ArrayList r11 = r10.mTransitionList
            r11.add(r12)
        L7b:
            r10.mCurrentTransition = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.setTransition(int, int):void");
    }

    public final boolean supportTouch() {
        ArrayList arrayList = this.mTransitionList;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((Transition) obj).mTouchResponse != null) {
                return true;
            }
        }
        Transition transition = this.mCurrentTransition;
        return (transition == null || transition.mTouchResponse == null) ? false : true;
    }

    public final int parseInclude(int i, Context context) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                String name = xml.getName();
                if (2 == eventType && "ConstraintSet".equals(name)) {
                    return parseConstraintSet(context, xml);
                }
            }
            return -1;
        } catch (IOException e) {
            Log.e("MotionScene", "Error parsing resource: " + i, e);
            return -1;
        } catch (XmlPullParserException e2) {
            Log.e("MotionScene", "Error parsing resource: " + i, e2);
            return -1;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Transition {
        public final int mAutoTransition;
        public int mConstraintSetEnd;
        public int mConstraintSetStart;
        public int mDefaultInterpolator;
        public int mDefaultInterpolatorID;
        public String mDefaultInterpolatorString;
        public boolean mDisable;
        public int mDuration;
        public final int mId;
        public final boolean mIsAbstract;
        public final ArrayList mKeyFramesList;
        public final int mLayoutDuringTransition;
        public final MotionScene mMotionScene;
        public final ArrayList mOnClicks;
        public int mPathMotionArc;
        public final float mStagger;
        public TouchResponse mTouchResponse;
        public final int mTransitionFlags;

        public Transition(MotionScene motionScene, Transition transition) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mMotionScene = motionScene;
            this.mDuration = motionScene.mDefaultDuration;
            if (transition != null) {
                this.mPathMotionArc = transition.mPathMotionArc;
                this.mDefaultInterpolator = transition.mDefaultInterpolator;
                this.mDefaultInterpolatorString = transition.mDefaultInterpolatorString;
                this.mDefaultInterpolatorID = transition.mDefaultInterpolatorID;
                this.mDuration = transition.mDuration;
                this.mKeyFramesList = transition.mKeyFramesList;
                this.mStagger = transition.mStagger;
                this.mLayoutDuringTransition = transition.mLayoutDuringTransition;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class TransitionOnClick implements View.OnClickListener {
            public final int mMode;
            public final int mTargetId;
            public final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser xmlPullParser) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.OnClick);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == R.styleable.OnClick_targetId) {
                        this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                    } else if (index == R.styleable.OnClick_clickAction) {
                        this.mMode = obtainStyledAttributes.getInt(index, this.mMode);
                    }
                }
                obtainStyledAttributes.recycle();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r8v4, types: [android.view.View] */
            public final void addOnClickListeners(MotionLayout motionLayout, int i, Transition transition) {
                int i2 = this.mTargetId;
                MotionLayout motionLayout2 = motionLayout;
                if (i2 != -1) {
                    motionLayout2 = motionLayout.findViewById(i2);
                }
                if (motionLayout2 == null) {
                    Log.e("MotionScene", "OnClick could not find id " + this.mTargetId);
                    return;
                }
                int i3 = transition.mConstraintSetStart;
                int i4 = transition.mConstraintSetEnd;
                if (i3 == -1) {
                    motionLayout2.setOnClickListener(this);
                    return;
                }
                int i5 = this.mMode;
                int i6 = i5 & 1;
                if (((i6 != 0 && i == i3) | (i6 != 0 && i == i3) | ((i5 & 256) != 0 && i == i3) | ((i5 & 16) != 0 && i == i4)) || ((i5 & 4096) != 0 && i == i4)) {
                    motionLayout2.setOnClickListener(this);
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onClick(android.view.View r9) {
                /*
                    Method dump skipped, instructions count: 207
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick.onClick(android.view.View):void");
            }

            public final void removeOnClickListeners(MotionLayout motionLayout) {
                int i = this.mTargetId;
                if (i == -1) {
                    return;
                }
                View findViewById = motionLayout.findViewById(i);
                if (findViewById != null) {
                    findViewById.setOnClickListener(null);
                    return;
                }
                Log.e("MotionScene", " (*)  could not find id " + this.mTargetId);
            }

            public TransitionOnClick(Transition transition, int i, int i2) {
                this.mTransition = transition;
                this.mTargetId = i;
                this.mMode = i2;
            }
        }

        public Transition(int i, MotionScene motionScene, int i2, int i3) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mId = i;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = i2;
            this.mConstraintSetEnd = i3;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
        }

        public Transition(MotionScene motionScene, Context context, XmlPullParser xmlPullParser) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Transition);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.Transition_constraintSetEnd) {
                    this.mConstraintSetEnd = obtainStyledAttributes.getResourceId(index, -1);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintSetEnd);
                    if ("layout".equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.load(context, this.mConstraintSetEnd);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetEnd, constraintSet);
                    } else if ("xml".equals(resourceTypeName)) {
                        this.mConstraintSetEnd = motionScene.parseInclude(this.mConstraintSetEnd, context);
                    }
                } else if (index == R.styleable.Transition_constraintSetStart) {
                    this.mConstraintSetStart = obtainStyledAttributes.getResourceId(index, this.mConstraintSetStart);
                    String resourceTypeName2 = context.getResources().getResourceTypeName(this.mConstraintSetStart);
                    if ("layout".equals(resourceTypeName2)) {
                        ConstraintSet constraintSet2 = new ConstraintSet();
                        constraintSet2.load(context, this.mConstraintSetStart);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetStart, constraintSet2);
                    } else if ("xml".equals(resourceTypeName2)) {
                        this.mConstraintSetStart = motionScene.parseInclude(this.mConstraintSetStart, context);
                    }
                } else if (index == R.styleable.Transition_motionInterpolator) {
                    int i2 = obtainStyledAttributes.peekValue(index).type;
                    if (i2 == 1) {
                        int resourceId = obtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolatorID = resourceId;
                        if (resourceId != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (i2 == 3) {
                        String string = obtainStyledAttributes.getString(index);
                        this.mDefaultInterpolatorString = string;
                        if (string != null) {
                            if (string.indexOf("/") > 0) {
                                this.mDefaultInterpolatorID = obtainStyledAttributes.getResourceId(index, -1);
                                this.mDefaultInterpolator = -2;
                            } else {
                                this.mDefaultInterpolator = -1;
                            }
                        }
                    } else {
                        this.mDefaultInterpolator = obtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                    }
                } else if (index == R.styleable.Transition_duration) {
                    int i3 = obtainStyledAttributes.getInt(index, this.mDuration);
                    this.mDuration = i3;
                    if (i3 < 8) {
                        this.mDuration = 8;
                    }
                } else if (index == R.styleable.Transition_staggered) {
                    this.mStagger = obtainStyledAttributes.getFloat(index, this.mStagger);
                } else if (index == R.styleable.Transition_autoTransition) {
                    this.mAutoTransition = obtainStyledAttributes.getInteger(index, this.mAutoTransition);
                } else if (index == R.styleable.Transition_android_id) {
                    this.mId = obtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == R.styleable.Transition_transitionDisable) {
                    this.mDisable = obtainStyledAttributes.getBoolean(index, this.mDisable);
                } else if (index == R.styleable.Transition_pathMotionArc) {
                    this.mPathMotionArc = obtainStyledAttributes.getInteger(index, -1);
                } else if (index == R.styleable.Transition_layoutDuringTransition) {
                    this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
                } else if (index == R.styleable.Transition_transitionFlags) {
                    this.mTransitionFlags = obtainStyledAttributes.getInteger(index, 0);
                }
            }
            if (this.mConstraintSetStart == -1) {
                this.mIsAbstract = true;
            }
            obtainStyledAttributes.recycle();
        }
    }

    public MotionScene(Context context, MotionLayout motionLayout, int i) {
        int eventType;
        Transition transition;
        this.mStateSet = null;
        this.mCurrentTransition = null;
        final boolean z = false;
        this.mDisableAutoTransition = false;
        this.mTransitionList = new ArrayList();
        this.mDefaultTransition = null;
        this.mAbstractTransitionList = new ArrayList();
        this.mConstraintSetMap = new SparseArray();
        this.mConstraintSetIdMap = new HashMap();
        this.mDeriveMap = new SparseIntArray();
        this.mDefaultDuration = 400;
        this.mLayoutDuringTransition = 0;
        this.mIgnoreTouch = false;
        this.mMotionOutsideRegion = false;
        this.mMotionLayout = motionLayout;
        this.mViewTransitionController = new ViewTransitionController(motionLayout);
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            eventType = xml.getEventType();
            transition = null;
        } catch (IOException e) {
            Log.e("MotionScene", "Error parsing resource: " + i, e);
        } catch (XmlPullParserException e2) {
            Log.e("MotionScene", "Error parsing resource: " + i, e2);
        }
        while (true) {
            final boolean z2 = true;
            if (eventType != 1) {
                char c = 2;
                if (eventType == 2) {
                    String name = xml.getName();
                    switch (name.hashCode()) {
                        case -1349929691:
                            if (name.equals("ConstraintSet")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1239391468:
                            if (name.equals("KeyFrameSet")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case -687739768:
                            if (name.equals("Include")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 61998586:
                            if (name.equals("ViewTransition")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        case 269306229:
                            if (name.equals("Transition")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 312750793:
                            if (name.equals("OnClick")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 327855227:
                            if (name.equals("OnSwipe")) {
                                break;
                            }
                            c = 65535;
                            break;
                        case 793277014:
                            if (name.equals("MotionScene")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1382829617:
                            if (name.equals("StateSet")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1942574248:
                            if (name.equals("include")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    MotionLayout motionLayout2 = this.mMotionLayout;
                    switch (c) {
                        case 0:
                            parseMotionSceneTags(context, xml);
                            break;
                        case 1:
                            ArrayList arrayList = this.mTransitionList;
                            transition = new Transition(this, context, xml);
                            arrayList.add(transition);
                            if (this.mCurrentTransition == null && !transition.mIsAbstract) {
                                this.mCurrentTransition = transition;
                                TouchResponse touchResponse = transition.mTouchResponse;
                                if (touchResponse != null) {
                                    touchResponse.setRTL(this.mRtl);
                                }
                            }
                            if (!transition.mIsAbstract) {
                                break;
                            } else {
                                if (transition.mConstraintSetEnd == -1) {
                                    this.mDefaultTransition = transition;
                                } else {
                                    this.mAbstractTransitionList.add(transition);
                                }
                                this.mTransitionList.remove(transition);
                                break;
                            }
                            break;
                        case 2:
                            if (transition == null) {
                                context.getResources().getResourceEntryName(i);
                                xml.getLineNumber();
                            }
                            if (transition == null) {
                                break;
                            } else {
                                transition.mTouchResponse = new TouchResponse(context, motionLayout2, xml);
                                break;
                            }
                        case 3:
                            if (transition != null && !motionLayout2.isInEditMode()) {
                                transition.mOnClicks.add(new Transition.TransitionOnClick(context, transition, xml));
                                break;
                            }
                            break;
                        case 4:
                            this.mStateSet = new StateSet(context, xml);
                            break;
                        case 5:
                            parseConstraintSet(context, xml);
                            break;
                        case 6:
                        case 7:
                            parseInclude(context, xml);
                            break;
                        case '\b':
                            KeyFrames keyFrames = new KeyFrames(context, xml);
                            if (transition == null) {
                                break;
                            } else {
                                transition.mKeyFramesList.add(keyFrames);
                                break;
                            }
                        case '\t':
                            final ViewTransition viewTransition = new ViewTransition(context, xml);
                            final ViewTransitionController viewTransitionController = this.mViewTransitionController;
                            viewTransitionController.mViewTransitions.add(viewTransition);
                            viewTransitionController.mRelatedViews = null;
                            int i2 = viewTransition.mOnStateTransition;
                            if (i2 != 4) {
                                if (i2 != 5) {
                                    break;
                                } else {
                                    final int i3 = viewTransition.mSharedValueID;
                                    final int i4 = viewTransition.mSharedValueTarget;
                                    ConstraintLayout.getSharedValues().addListener(viewTransition.mSharedValueID, new SharedValues.SharedValuesListener() { // from class: androidx.constraintlayout.motion.widget.ViewTransitionController.1
                                        public final /* synthetic */ boolean val$isSet;
                                        public final /* synthetic */ int val$listen_for_id;
                                        public final /* synthetic */ int val$listen_for_value;
                                        public final /* synthetic */ ViewTransition val$viewTransition;

                                        public AnonymousClass1(final ViewTransition viewTransition2, final int i32, final boolean z3, final int i42) {
                                            r2 = viewTransition2;
                                            r3 = i32;
                                            r4 = z3;
                                            r5 = i42;
                                        }

                                        @Override // androidx.constraintlayout.widget.SharedValues.SharedValuesListener
                                        public final void onNewValue(int i5, int i6, int i7) {
                                            ViewTransition viewTransition2 = r2;
                                            int i8 = viewTransition2.mSharedValueCurrent;
                                            viewTransition2.mSharedValueCurrent = i6;
                                            if (r3 != i5 || i8 == i6) {
                                                return;
                                            }
                                            boolean z3 = r4;
                                            int i9 = r5;
                                            ViewTransitionController viewTransitionController2 = ViewTransitionController.this;
                                            if (z3) {
                                                if (i9 == i6) {
                                                    int childCount = viewTransitionController2.mMotionLayout.getChildCount();
                                                    for (int i10 = 0; i10 < childCount; i10++) {
                                                        View childAt = viewTransitionController2.mMotionLayout.getChildAt(i10);
                                                        if (viewTransition2.matchesView(childAt)) {
                                                            int currentState = viewTransitionController2.mMotionLayout.getCurrentState();
                                                            ConstraintSet constraintSet = viewTransitionController2.mMotionLayout.getConstraintSet(currentState);
                                                            ViewTransitionController viewTransitionController3 = ViewTransitionController.this;
                                                            r2.applyTransition(viewTransitionController3, viewTransitionController3.mMotionLayout, currentState, constraintSet, childAt);
                                                        }
                                                    }
                                                    return;
                                                }
                                                return;
                                            }
                                            if (i9 != i6) {
                                                int childCount2 = viewTransitionController2.mMotionLayout.getChildCount();
                                                for (int i11 = 0; i11 < childCount2; i11++) {
                                                    View childAt2 = viewTransitionController2.mMotionLayout.getChildAt(i11);
                                                    if (viewTransition2.matchesView(childAt2)) {
                                                        int currentState2 = viewTransitionController2.mMotionLayout.getCurrentState();
                                                        ConstraintSet constraintSet2 = viewTransitionController2.mMotionLayout.getConstraintSet(currentState2);
                                                        ViewTransitionController viewTransitionController4 = ViewTransitionController.this;
                                                        r2.applyTransition(viewTransitionController4, viewTransitionController4.mMotionLayout, currentState2, constraintSet2, childAt2);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    break;
                                }
                            } else {
                                final int i5 = viewTransition2.mSharedValueID;
                                final int i6 = viewTransition2.mSharedValueTarget;
                                ConstraintLayout.getSharedValues().addListener(viewTransition2.mSharedValueID, new SharedValues.SharedValuesListener() { // from class: androidx.constraintlayout.motion.widget.ViewTransitionController.1
                                    public final /* synthetic */ boolean val$isSet;
                                    public final /* synthetic */ int val$listen_for_id;
                                    public final /* synthetic */ int val$listen_for_value;
                                    public final /* synthetic */ ViewTransition val$viewTransition;

                                    public AnonymousClass1(final ViewTransition viewTransition2, final int i52, final boolean z22, final int i62) {
                                        r2 = viewTransition2;
                                        r3 = i52;
                                        r4 = z22;
                                        r5 = i62;
                                    }

                                    @Override // androidx.constraintlayout.widget.SharedValues.SharedValuesListener
                                    public final void onNewValue(int i52, int i62, int i7) {
                                        ViewTransition viewTransition2 = r2;
                                        int i8 = viewTransition2.mSharedValueCurrent;
                                        viewTransition2.mSharedValueCurrent = i62;
                                        if (r3 != i52 || i8 == i62) {
                                            return;
                                        }
                                        boolean z3 = r4;
                                        int i9 = r5;
                                        ViewTransitionController viewTransitionController2 = ViewTransitionController.this;
                                        if (z3) {
                                            if (i9 == i62) {
                                                int childCount = viewTransitionController2.mMotionLayout.getChildCount();
                                                for (int i10 = 0; i10 < childCount; i10++) {
                                                    View childAt = viewTransitionController2.mMotionLayout.getChildAt(i10);
                                                    if (viewTransition2.matchesView(childAt)) {
                                                        int currentState = viewTransitionController2.mMotionLayout.getCurrentState();
                                                        ConstraintSet constraintSet = viewTransitionController2.mMotionLayout.getConstraintSet(currentState);
                                                        ViewTransitionController viewTransitionController3 = ViewTransitionController.this;
                                                        r2.applyTransition(viewTransitionController3, viewTransitionController3.mMotionLayout, currentState, constraintSet, childAt);
                                                    }
                                                }
                                                return;
                                            }
                                            return;
                                        }
                                        if (i9 != i62) {
                                            int childCount2 = viewTransitionController2.mMotionLayout.getChildCount();
                                            for (int i11 = 0; i11 < childCount2; i11++) {
                                                View childAt2 = viewTransitionController2.mMotionLayout.getChildAt(i11);
                                                if (viewTransition2.matchesView(childAt2)) {
                                                    int currentState2 = viewTransitionController2.mMotionLayout.getCurrentState();
                                                    ConstraintSet constraintSet2 = viewTransitionController2.mMotionLayout.getConstraintSet(currentState2);
                                                    ViewTransitionController viewTransitionController4 = ViewTransitionController.this;
                                                    r2.applyTransition(viewTransitionController4, viewTransitionController4.mMotionLayout, currentState2, constraintSet2, childAt2);
                                                }
                                            }
                                        }
                                    }
                                });
                                break;
                            }
                    }
                }
                eventType = xml.next();
            } else {
                SparseArray sparseArray = this.mConstraintSetMap;
                int i7 = R.id.motion_base;
                sparseArray.put(i7, new ConstraintSet());
                this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(i7));
                return;
            }
        }
    }
}
