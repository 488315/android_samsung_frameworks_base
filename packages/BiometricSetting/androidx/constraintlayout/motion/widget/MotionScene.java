package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.VelocityTracker;
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
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.StateSet;
import com.samsung.android.biometrics.app.setting.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class MotionScene {
    Transition mCurrentTransition;
    private Transition mDefaultTransition;
    private MotionEvent mLastTouchDown;
    float mLastTouchX;
    float mLastTouchY;
    private final MotionLayout mMotionLayout;
    private boolean mRtl;
    StateSet mStateSet;
    private MotionLayout.MotionTracker mVelocityTracker;
    final ViewTransitionController mViewTransitionController;
    private ArrayList<Transition> mTransitionList = new ArrayList<>();
    private ArrayList<Transition> mAbstractTransitionList = new ArrayList<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private HashMap<String, Integer> mConstraintSetIdMap = new HashMap<>();
    private SparseIntArray mDeriveMap = new SparseIntArray();
    private int mDefaultDuration = 400;
    private int mLayoutDuringTransition = 0;
    private boolean mIgnoreTouch = false;
    private boolean mMotionOutsideRegion = false;

    MotionScene(Context context, MotionLayout motionLayout, int i) {
        int eventType;
        Transition transition = null;
        this.mStateSet = null;
        this.mCurrentTransition = null;
        this.mDefaultTransition = null;
        this.mMotionLayout = motionLayout;
        this.mViewTransitionController = new ViewTransitionController(motionLayout);
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            eventType = xml.getEventType();
        } catch (IOException e) {
            Log.e("MotionScene", "Error parsing resource: " + i, e);
        } catch (XmlPullParserException e2) {
            Log.e("MotionScene", "Error parsing resource: " + i, e2);
        }
        while (true) {
            char c = 1;
            if (eventType == 1) {
                this.mConstraintSetMap.put(R.id.motion_base, new ConstraintSet());
                this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(R.id.motion_base));
                return;
            }
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
                            c = 2;
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
                        ArrayList<Transition> arrayList = this.mTransitionList;
                        Transition transition2 = new Transition(this, context, xml);
                        arrayList.add(transition2);
                        if (this.mCurrentTransition == null && !transition2.mIsAbstract) {
                            this.mCurrentTransition = transition2;
                            if (transition2.mTouchResponse != null) {
                                this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
                            }
                        }
                        if (transition2.mIsAbstract) {
                            if (transition2.mConstraintSetEnd == -1) {
                                this.mDefaultTransition = transition2;
                            } else {
                                this.mAbstractTransitionList.add(transition2);
                            }
                            this.mTransitionList.remove(transition2);
                        }
                        transition = transition2;
                        break;
                    case 2:
                        if (transition == null) {
                            Log.v("MotionScene", " OnSwipe (" + context.getResources().getResourceEntryName(i) + ".xml:" + xml.getLineNumber() + ")");
                        }
                        if (transition == null) {
                            break;
                        } else {
                            transition.mTouchResponse = new TouchResponse(context, motionLayout2, xml);
                            break;
                        }
                    case 3:
                        if (transition != null && !motionLayout2.isInEditMode()) {
                            transition.addOnClick(context, xml);
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
                        this.mViewTransitionController.add(new ViewTransition(context, xml));
                        break;
                }
            }
            eventType = xml.next();
        }
    }

    private static int getId(Context context, String str) {
        int i;
        if (str.contains("/")) {
            i = context.getResources().getIdentifier(str.substring(str.indexOf(47) + 1), "id", context.getPackageName());
        } else {
            i = -1;
        }
        if (i != -1) {
            return i;
        }
        if (str.length() > 1) {
            return Integer.parseInt(str.substring(1));
        }
        Log.e("MotionScene", "error in parsing id");
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int parseConstraintSet(Context context, XmlPullParser xmlPullParser) {
        boolean z;
        boolean z2;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.setForceId();
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
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case -1153153640:
                    if (attributeName.equals("constraintRotate")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 3355:
                    if (attributeName.equals("id")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case 973381616:
                    if (attributeName.equals("stateLabels")) {
                        z = 3;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i2 = getId(context, attributeValue);
                    break;
                case true:
                    try {
                        constraintSet.mRotate = Integer.parseInt(attributeValue);
                        break;
                    } catch (NumberFormatException unused) {
                        attributeValue.getClass();
                        switch (attributeValue.hashCode()) {
                            case -768416914:
                                if (attributeValue.equals("x_left")) {
                                    z2 = false;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case 3317767:
                                if (attributeValue.equals("left")) {
                                    z2 = true;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case 3387192:
                                if (attributeValue.equals("none")) {
                                    z2 = 2;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case 108511772:
                                if (attributeValue.equals("right")) {
                                    z2 = 3;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case 1954540437:
                                if (attributeValue.equals("x_right")) {
                                    z2 = 4;
                                    break;
                                }
                                z2 = -1;
                                break;
                            default:
                                z2 = -1;
                                break;
                        }
                        switch (z2) {
                            case false:
                                constraintSet.mRotate = 4;
                                break;
                            case true:
                                constraintSet.mRotate = 2;
                                break;
                            case true:
                                constraintSet.mRotate = 0;
                                break;
                            case true:
                                constraintSet.mRotate = 1;
                                break;
                            case true:
                                constraintSet.mRotate = 3;
                                break;
                        }
                    }
                    break;
                case true:
                    i = getId(context, attributeValue);
                    HashMap<String, Integer> hashMap = this.mConstraintSetIdMap;
                    int indexOf = attributeValue.indexOf(47);
                    if (indexOf >= 0) {
                        attributeValue = attributeValue.substring(indexOf + 1);
                    }
                    hashMap.put(attributeValue, Integer.valueOf(i));
                    constraintSet.mIdString = Debug.getName(context, i);
                    break;
                case true:
                    constraintSet.setStateLabels(attributeValue);
                    break;
            }
        }
        if (i != -1) {
            int i4 = this.mMotionLayout.mDebugPath;
            constraintSet.load(context, xmlPullParser);
            if (i2 != -1) {
                this.mDeriveMap.put(i, i2);
            }
            this.mConstraintSetMap.put(i, constraintSet);
        }
        return i;
    }

    private void parseInclude(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.include);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                parseInclude(context, obtainStyledAttributes.getResourceId(index, -1));
            }
        }
        obtainStyledAttributes.recycle();
    }

    private void parseMotionSceneTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.MotionScene);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                int i2 = obtainStyledAttributes.getInt(index, this.mDefaultDuration);
                this.mDefaultDuration = i2;
                if (i2 < 8) {
                    this.mDefaultDuration = 8;
                }
            } else if (index == 1) {
                this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    private void readConstraintChain(int i, MotionLayout motionLayout) {
        ConstraintSet constraintSet = this.mConstraintSetMap.get(i);
        constraintSet.derivedState = constraintSet.mIdString;
        int i2 = this.mDeriveMap.get(i);
        if (i2 > 0) {
            readConstraintChain(i2, motionLayout);
            ConstraintSet constraintSet2 = this.mConstraintSetMap.get(i2);
            if (constraintSet2 == null) {
                Log.e("MotionScene", "ERROR! invalid deriveConstraintsFrom: @id/" + Debug.getName(this.mMotionLayout.getContext(), i2));
                return;
            } else {
                constraintSet.derivedState += "/" + constraintSet2.derivedState;
                constraintSet.readFallback(constraintSet2);
            }
        } else {
            constraintSet.derivedState += "  layout";
            constraintSet.readFallback(motionLayout);
        }
        constraintSet.applyDeltaFrom(constraintSet);
    }

    public final void addOnClickListeners(int i, MotionLayout motionLayout) {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (next.mOnClicks.size() > 0) {
                Iterator it2 = next.mOnClicks.iterator();
                while (it2.hasNext()) {
                    ((Transition.TransitionOnClick) it2.next()).removeOnClickListeners(motionLayout);
                }
            }
        }
        Iterator<Transition> it3 = this.mAbstractTransitionList.iterator();
        while (it3.hasNext()) {
            Transition next2 = it3.next();
            if (next2.mOnClicks.size() > 0) {
                Iterator it4 = next2.mOnClicks.iterator();
                while (it4.hasNext()) {
                    ((Transition.TransitionOnClick) it4.next()).removeOnClickListeners(motionLayout);
                }
            }
        }
        Iterator<Transition> it5 = this.mTransitionList.iterator();
        while (it5.hasNext()) {
            Transition next3 = it5.next();
            if (next3.mOnClicks.size() > 0) {
                Iterator it6 = next3.mOnClicks.iterator();
                while (it6.hasNext()) {
                    ((Transition.TransitionOnClick) it6.next()).addOnClickListeners(motionLayout, i, next3);
                }
            }
        }
        Iterator<Transition> it7 = this.mAbstractTransitionList.iterator();
        while (it7.hasNext()) {
            Transition next4 = it7.next();
            if (next4.mOnClicks.size() > 0) {
                Iterator it8 = next4.mOnClicks.iterator();
                while (it8.hasNext()) {
                    ((Transition.TransitionOnClick) it8.next()).addOnClickListeners(motionLayout, i, next4);
                }
            }
        }
    }

    final boolean autoTransition(int i, MotionLayout motionLayout) {
        Transition transition;
        if (this.mVelocityTracker != null) {
            return false;
        }
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (next.mAutoTransition != 0 && ((transition = this.mCurrentTransition) != next || !transition.isTransitionFlag(2))) {
                int i2 = next.mConstraintSetStart;
                MotionLayout.TransitionState transitionState = MotionLayout.TransitionState.FINISHED;
                MotionLayout.TransitionState transitionState2 = MotionLayout.TransitionState.MOVING;
                MotionLayout.TransitionState transitionState3 = MotionLayout.TransitionState.SETUP;
                if (i == i2 && (next.mAutoTransition == 4 || next.mAutoTransition == 2)) {
                    motionLayout.setState(transitionState);
                    motionLayout.setTransition(next);
                    if (next.mAutoTransition == 4) {
                        motionLayout.transitionToEnd();
                        motionLayout.setState(transitionState3);
                        motionLayout.setState(transitionState2);
                    } else {
                        motionLayout.setProgress(1.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(transitionState3);
                        motionLayout.setState(transitionState2);
                        motionLayout.setState(transitionState);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
                if (i == next.mConstraintSetEnd && (next.mAutoTransition == 3 || next.mAutoTransition == 1)) {
                    motionLayout.setState(transitionState);
                    motionLayout.setTransition(next);
                    if (next.mAutoTransition == 3) {
                        motionLayout.animateTo(0.0f);
                        motionLayout.setState(transitionState3);
                        motionLayout.setState(transitionState2);
                    } else {
                        motionLayout.setProgress(0.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(transitionState3);
                        motionLayout.setState(transitionState2);
                        motionLayout.setState(transitionState);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    final ConstraintSet getConstraintSet(int i) {
        int stateGetConstraintID;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (stateGetConstraintID = stateSet.stateGetConstraintID(i)) != -1) {
            i = stateGetConstraintID;
        }
        if (this.mConstraintSetMap.get(i) != null) {
            return this.mConstraintSetMap.get(i);
        }
        Log.e("MotionScene", "Warning could not find ConstraintSet id/" + Debug.getName(this.mMotionLayout.getContext(), i) + " In MotionScene");
        SparseArray<ConstraintSet> sparseArray = this.mConstraintSetMap;
        return sparseArray.get(sparseArray.keyAt(0));
    }

    public final int[] getConstraintSetIds() {
        int size = this.mConstraintSetMap.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.mConstraintSetMap.keyAt(i);
        }
        return iArr;
    }

    public final ArrayList<Transition> getDefinedTransitions() {
        return this.mTransitionList;
    }

    public final int getDuration() {
        Transition transition = this.mCurrentTransition;
        return transition != null ? transition.mDuration : this.mDefaultDuration;
    }

    public final Interpolator getInterpolator() {
        int i = this.mCurrentTransition.mDefaultInterpolator;
        if (i == -2) {
            return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), this.mCurrentTransition.mDefaultInterpolatorID);
        }
        if (i == -1) {
            final Easing interpolator = Easing.getInterpolator(this.mCurrentTransition.mDefaultInterpolatorString);
            return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionScene.1
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    return (float) Easing.this.get(f);
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
        if (transition != null) {
            Iterator it = transition.mKeyFramesList.iterator();
            while (it.hasNext()) {
                ((KeyFrames) it.next()).addFrames(motionController);
            }
        } else {
            Transition transition2 = this.mDefaultTransition;
            if (transition2 != null) {
                Iterator it2 = transition2.mKeyFramesList.iterator();
                while (it2.hasNext()) {
                    ((KeyFrames) it2.next()).addFrames(motionController);
                }
            }
        }
    }

    final float getMaxAcceleration() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getMaxAcceleration();
    }

    final int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetStart;
    }

    public final Transition getTransitionById(int i) {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (next.mId == i) {
                return next;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d8, code lost:
    
        if (r7.contains(r12.getX(), r12.getY()) == false) goto L59;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void processTouchEvent(MotionEvent motionEvent, int i, MotionLayout motionLayout) {
        MotionLayout.MotionTracker motionTracker;
        MotionLayout.MotionTracker motionTracker2;
        MotionEvent motionEvent2;
        RectF rectF;
        Transition transition;
        int i2;
        RectF rectF2;
        RectF rectF3;
        Iterator it;
        float f;
        float f2;
        MotionEvent motionEvent3;
        RectF rectF4 = new RectF();
        MotionLayout.MotionTracker motionTracker3 = this.mVelocityTracker;
        MotionLayout motionLayout2 = this.mMotionLayout;
        if (motionTracker3 == null) {
            motionLayout2.getClass();
            this.mVelocityTracker = MotionLayout.MyTracker.obtain();
        }
        VelocityTracker velocityTracker = ((MotionLayout.MyTracker) this.mVelocityTracker).mTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        if (i != -1) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                this.mLastTouchDown = motionEvent;
                this.mIgnoreTouch = false;
                if (this.mCurrentTransition.mTouchResponse != null) {
                    RectF limitBoundsTo = this.mCurrentTransition.mTouchResponse.getLimitBoundsTo(motionLayout2, rectF4);
                    if (limitBoundsTo != null && !limitBoundsTo.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                        this.mLastTouchDown = null;
                        this.mIgnoreTouch = true;
                        return;
                    }
                    RectF touchRegion = this.mCurrentTransition.mTouchResponse.getTouchRegion(motionLayout2, rectF4);
                    if (touchRegion == null || touchRegion.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                        this.mMotionOutsideRegion = false;
                    } else {
                        this.mMotionOutsideRegion = true;
                    }
                    this.mCurrentTransition.mTouchResponse.setDown(this.mLastTouchX, this.mLastTouchY);
                    return;
                }
                return;
            }
            if (action == 2 && !this.mIgnoreTouch) {
                float rawY = motionEvent.getRawY() - this.mLastTouchY;
                float rawX = motionEvent.getRawX() - this.mLastTouchX;
                if ((rawX == 0.0d && rawY == 0.0d) || (motionEvent2 = this.mLastTouchDown) == null) {
                    return;
                }
                if (i != -1) {
                    StateSet stateSet = this.mStateSet;
                    if (stateSet == null || (i2 = stateSet.stateGetConstraintID(i)) == -1) {
                        i2 = i;
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator<Transition> it2 = this.mTransitionList.iterator();
                    while (it2.hasNext()) {
                        Transition next = it2.next();
                        if (next.mConstraintSetStart == i2 || next.mConstraintSetEnd == i2) {
                            arrayList.add(next);
                        }
                    }
                    RectF rectF5 = new RectF();
                    Iterator it3 = arrayList.iterator();
                    float f3 = 0.0f;
                    transition = null;
                    while (it3.hasNext()) {
                        Transition transition2 = (Transition) it3.next();
                        if (transition2.mDisable || transition2.mTouchResponse == null) {
                            rectF2 = rectF4;
                            rectF3 = rectF5;
                            it = it3;
                        } else {
                            transition2.mTouchResponse.setRTL(this.mRtl);
                            RectF touchRegion2 = transition2.mTouchResponse.getTouchRegion(motionLayout2, rectF5);
                            if (touchRegion2 != null) {
                                it = it3;
                            } else {
                                it = it3;
                            }
                            RectF limitBoundsTo2 = transition2.mTouchResponse.getLimitBoundsTo(motionLayout2, rectF5);
                            if (limitBoundsTo2 == null || limitBoundsTo2.contains(motionEvent2.getX(), motionEvent2.getY())) {
                                float dot = transition2.mTouchResponse.dot(rawX, rawY);
                                if (transition2.mTouchResponse.mIsRotateMode) {
                                    float x = motionEvent2.getX();
                                    transition2.mTouchResponse.getClass();
                                    float y = motionEvent2.getY();
                                    transition2.mTouchResponse.getClass();
                                    rectF3 = rectF5;
                                    f2 = rawX;
                                    motionEvent3 = motionEvent2;
                                    rectF2 = rectF4;
                                    f = rawY;
                                    dot = ((float) (Math.atan2(rawY + r10, rawX + r7) - Math.atan2(x - 0.5f, y - 0.5f))) * 10.0f;
                                } else {
                                    rectF2 = rectF4;
                                    rectF3 = rectF5;
                                    f = rawY;
                                    f2 = rawX;
                                    motionEvent3 = motionEvent2;
                                }
                                float f4 = (transition2.mConstraintSetEnd == i ? -1.0f : 1.1f) * dot;
                                if (f4 > f3) {
                                    f3 = f4;
                                    transition = transition2;
                                }
                                it3 = it;
                                rectF5 = rectF3;
                                rawX = f2;
                                motionEvent2 = motionEvent3;
                                rectF4 = rectF2;
                                rawY = f;
                            }
                            rectF2 = rectF4;
                            rectF3 = rectF5;
                        }
                        f = rawY;
                        f2 = rawX;
                        motionEvent3 = motionEvent2;
                        it3 = it;
                        rectF5 = rectF3;
                        rawX = f2;
                        motionEvent2 = motionEvent3;
                        rectF4 = rectF2;
                        rawY = f;
                    }
                    rectF = rectF4;
                } else {
                    rectF = rectF4;
                    transition = this.mCurrentTransition;
                }
                if (transition != null) {
                    motionLayout.setTransition(transition);
                    RectF touchRegion3 = this.mCurrentTransition.mTouchResponse.getTouchRegion(motionLayout2, rectF);
                    this.mMotionOutsideRegion = (touchRegion3 == null || touchRegion3.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) ? false : true;
                    this.mCurrentTransition.mTouchResponse.setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
                }
            }
        }
        if (this.mIgnoreTouch) {
            return;
        }
        Transition transition3 = this.mCurrentTransition;
        if (transition3 != null && transition3.mTouchResponse != null && !this.mMotionOutsideRegion) {
            this.mCurrentTransition.mTouchResponse.processTouchEvent(motionEvent, this.mVelocityTracker);
        }
        this.mLastTouchX = motionEvent.getRawX();
        this.mLastTouchY = motionEvent.getRawY();
        if (motionEvent.getAction() != 1 || (motionTracker = this.mVelocityTracker) == null) {
            return;
        }
        MotionLayout.MyTracker myTracker = (MotionLayout.MyTracker) motionTracker;
        VelocityTracker velocityTracker2 = myTracker.mTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            motionTracker2 = null;
            myTracker.mTracker = null;
        } else {
            motionTracker2 = null;
        }
        this.mVelocityTracker = motionTracker2;
        int i3 = motionLayout.mCurrentState;
        if (i3 != -1) {
            autoTransition(i3, motionLayout);
        }
    }

    final void readFallback(MotionLayout motionLayout) {
        boolean z;
        for (int i = 0; i < this.mConstraintSetMap.size(); i++) {
            int keyAt = this.mConstraintSetMap.keyAt(i);
            int i2 = this.mDeriveMap.get(keyAt);
            int size = this.mDeriveMap.size();
            while (true) {
                if (i2 <= 0) {
                    z = false;
                    break;
                }
                z = true;
                if (i2 == keyAt) {
                    break;
                }
                int i3 = size - 1;
                if (size < 0) {
                    break;
                }
                i2 = this.mDeriveMap.get(i2);
                size = i3;
            }
            if (z) {
                Log.e("MotionScene", "Cannot be derived from yourself");
                return;
            }
            readConstraintChain(keyAt, motionLayout);
        }
    }

    public final void setConstraintSet(int i, ConstraintSet constraintSet) {
        this.mConstraintSetMap.put(i, constraintSet);
    }

    public final void setDuration(int i) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            transition.setDuration(i);
        } else {
            this.mDefaultDuration = i;
        }
    }

    public final void setRtl(boolean z) {
        this.mRtl = z;
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
    
        if (r2 != (-1)) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void setTransition(int i, int i2) {
        int i3;
        int i4;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null) {
            i3 = stateSet.stateGetConstraintID(i);
            if (i3 == -1) {
                i3 = i;
            }
            i4 = this.mStateSet.stateGetConstraintID(i2);
        } else {
            i3 = i;
        }
        i4 = i2;
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mConstraintSetEnd == i2 && this.mCurrentTransition.mConstraintSetStart == i) {
            return;
        }
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if ((next.mConstraintSetEnd == i4 && next.mConstraintSetStart == i3) || (next.mConstraintSetEnd == i2 && next.mConstraintSetStart == i)) {
                this.mCurrentTransition = next;
                if (next.mTouchResponse != null) {
                    this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
                    return;
                }
                return;
            }
        }
        Transition transition2 = this.mDefaultTransition;
        Iterator<Transition> it2 = this.mAbstractTransitionList.iterator();
        while (it2.hasNext()) {
            Transition next2 = it2.next();
            if (next2.mConstraintSetEnd == i2) {
                transition2 = next2;
            }
        }
        Transition transition3 = new Transition(this, transition2);
        transition3.mConstraintSetStart = i3;
        transition3.mConstraintSetEnd = i4;
        if (i3 != -1) {
            this.mTransitionList.add(transition3);
        }
        this.mCurrentTransition = transition3;
    }

    final boolean supportTouch() {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (it.next().mTouchResponse != null) {
                return true;
            }
        }
        Transition transition = this.mCurrentTransition;
        return (transition == null || transition.mTouchResponse == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int parseInclude(Context context, int i) {
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

    public final void setTransition(Transition transition) {
        this.mCurrentTransition = transition;
        if (transition == null || transition.mTouchResponse == null) {
            return;
        }
        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }

    public static class Transition {
        private int mAutoTransition;
        private int mConstraintSetEnd;
        private int mConstraintSetStart;
        private int mDefaultInterpolator;
        private int mDefaultInterpolatorID;
        private String mDefaultInterpolatorString;
        private boolean mDisable;
        private int mDuration;
        private int mId;
        private boolean mIsAbstract;
        private ArrayList<KeyFrames> mKeyFramesList;
        private int mLayoutDuringTransition;
        private final MotionScene mMotionScene;
        private ArrayList<TransitionOnClick> mOnClicks;
        private int mPathMotionArc;
        private float mStagger;
        private TouchResponse mTouchResponse;
        private int mTransitionFlags;

        public static class TransitionOnClick implements View.OnClickListener {
            int mMode;
            int mTargetId;
            private final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser xmlPullParser) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.OnClick);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == 1) {
                        this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                    } else if (index == 0) {
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

            /* JADX WARN: Removed duplicated region for block: B:43:0x00bd  */
            /* JADX WARN: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onClick(View view) {
                MotionLayout motionLayout = this.mTransition.mMotionScene.mMotionLayout;
                if (!motionLayout.isInteractionEnabled()) {
                    return;
                }
                if (this.mTransition.mConstraintSetStart == -1) {
                    int currentState = motionLayout.getCurrentState();
                    if (currentState == -1) {
                        motionLayout.transitionToState(this.mTransition.mConstraintSetEnd);
                        return;
                    }
                    Transition transition = new Transition(this.mTransition.mMotionScene, this.mTransition);
                    transition.mConstraintSetStart = currentState;
                    transition.mConstraintSetEnd = this.mTransition.mConstraintSetEnd;
                    motionLayout.setTransition(transition);
                    motionLayout.transitionToEnd();
                    return;
                }
                Transition transition2 = this.mTransition.mMotionScene.mCurrentTransition;
                int i = this.mMode;
                boolean z = false;
                boolean z2 = ((i & 1) == 0 && (i & 256) == 0) ? false : true;
                boolean z3 = ((i & 16) == 0 && (i & 4096) == 0) ? false : true;
                if (z2 && z3) {
                    Transition transition3 = this.mTransition.mMotionScene.mCurrentTransition;
                    Transition transition4 = this.mTransition;
                    if (transition3 != transition4) {
                        motionLayout.setTransition(transition4);
                    }
                    if (motionLayout.getCurrentState() == motionLayout.getEndState() || motionLayout.getProgress() > 0.5f) {
                        z2 = false;
                    } else {
                        z3 = false;
                    }
                }
                Transition transition5 = this.mTransition;
                if (transition5 != transition2) {
                    int i2 = transition5.mConstraintSetEnd;
                    int i3 = this.mTransition.mConstraintSetStart;
                    if (i3 != -1) {
                    }
                    if (z) {
                        return;
                    }
                    if (z2 && (this.mMode & 1) != 0) {
                        motionLayout.setTransition(this.mTransition);
                        motionLayout.transitionToEnd();
                        return;
                    }
                    if (z3 && (this.mMode & 16) != 0) {
                        motionLayout.setTransition(this.mTransition);
                        motionLayout.animateTo(0.0f);
                        return;
                    } else if (z2 && (this.mMode & 256) != 0) {
                        motionLayout.setTransition(this.mTransition);
                        motionLayout.setProgress(1.0f);
                        return;
                    } else {
                        if (!z3 || (this.mMode & 4096) == 0) {
                            return;
                        }
                        motionLayout.setTransition(this.mTransition);
                        motionLayout.setProgress(0.0f);
                        return;
                    }
                }
                z = true;
                if (z) {
                }
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
        }

        Transition(MotionScene motionScene, Transition transition) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList<>();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList<>();
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

        public final void addKeyFrame(KeyFrames keyFrames) {
            this.mKeyFramesList.add(keyFrames);
        }

        public final void addOnClick(Context context, XmlPullParser xmlPullParser) {
            this.mOnClicks.add(new TransitionOnClick(context, this, xmlPullParser));
        }

        public final int getAutoTransition() {
            return this.mAutoTransition;
        }

        public final int getEndConstraintSetId() {
            return this.mConstraintSetEnd;
        }

        public final int getLayoutDuringTransition() {
            return this.mLayoutDuringTransition;
        }

        public final int getStartConstraintSetId() {
            return this.mConstraintSetStart;
        }

        public final TouchResponse getTouchResponse() {
            return this.mTouchResponse;
        }

        public final boolean isEnabled() {
            return !this.mDisable;
        }

        public final boolean isTransitionFlag(int i) {
            return (this.mTransitionFlags & i) != 0;
        }

        public final void setDuration(int i) {
            this.mDuration = Math.max(i, 8);
        }

        public final void setInterpolatorInfo(int i, int i2, String str) {
            this.mDefaultInterpolator = i;
            this.mDefaultInterpolatorString = str;
            this.mDefaultInterpolatorID = i2;
        }

        public final void setOnTouchUp() {
            TouchResponse touchResponse = this.mTouchResponse;
            if (touchResponse != null) {
                touchResponse.setTouchUpMode();
            }
        }

        public final void setPathMotionArc(int i) {
            this.mPathMotionArc = i;
        }

        public Transition(MotionScene motionScene, int i) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList<>();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList<>();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mId = -1;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = R.id.view_transition;
            this.mConstraintSetEnd = i;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
        }

        Transition(MotionScene motionScene, Context context, XmlPullParser xmlPullParser) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList<>();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList<>();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.Transition);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 2) {
                    this.mConstraintSetEnd = obtainStyledAttributes.getResourceId(index, -1);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintSetEnd);
                    if ("layout".equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.load(context, this.mConstraintSetEnd);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetEnd, constraintSet);
                    } else if ("xml".equals(resourceTypeName)) {
                        this.mConstraintSetEnd = motionScene.parseInclude(context, this.mConstraintSetEnd);
                    }
                } else if (index == 3) {
                    this.mConstraintSetStart = obtainStyledAttributes.getResourceId(index, this.mConstraintSetStart);
                    String resourceTypeName2 = context.getResources().getResourceTypeName(this.mConstraintSetStart);
                    if ("layout".equals(resourceTypeName2)) {
                        ConstraintSet constraintSet2 = new ConstraintSet();
                        constraintSet2.load(context, this.mConstraintSetStart);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetStart, constraintSet2);
                    } else if ("xml".equals(resourceTypeName2)) {
                        this.mConstraintSetStart = motionScene.parseInclude(context, this.mConstraintSetStart);
                    }
                } else if (index == 6) {
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
                } else if (index == 4) {
                    int i3 = obtainStyledAttributes.getInt(index, this.mDuration);
                    this.mDuration = i3;
                    if (i3 < 8) {
                        this.mDuration = 8;
                    }
                } else if (index == 8) {
                    this.mStagger = obtainStyledAttributes.getFloat(index, this.mStagger);
                } else if (index == 1) {
                    this.mAutoTransition = obtainStyledAttributes.getInteger(index, this.mAutoTransition);
                } else if (index == 0) {
                    this.mId = obtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == 9) {
                    this.mDisable = obtainStyledAttributes.getBoolean(index, this.mDisable);
                } else if (index == 7) {
                    this.mPathMotionArc = obtainStyledAttributes.getInteger(index, -1);
                } else if (index == 5) {
                    this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
                } else if (index == 10) {
                    this.mTransitionFlags = obtainStyledAttributes.getInteger(index, 0);
                }
            }
            if (this.mConstraintSetStart == -1) {
                this.mIsAbstract = true;
            }
            obtainStyledAttributes.recycle();
        }
    }
}
