package android.animation;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class LayoutTransition {
    private static TimeInterpolator ACCEL_DECEL_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    public static final int APPEARING = 2;
    public static final int CHANGE_APPEARING = 0;
    public static final int CHANGE_DISAPPEARING = 1;
    public static final int CHANGING = 4;
    private static TimeInterpolator DECEL_INTERPOLATOR = null;
    private static long DEFAULT_DURATION = 300;
    public static final int DISAPPEARING = 3;
    private static final int FLAG_APPEARING = 1;
    private static final int FLAG_CHANGE_APPEARING = 4;
    private static final int FLAG_CHANGE_DISAPPEARING = 8;
    private static final int FLAG_CHANGING = 16;
    private static final int FLAG_DISAPPEARING = 2;
    private static ObjectAnimator defaultChange;
    private static ObjectAnimator defaultChangeIn;
    private static ObjectAnimator defaultChangeOut;
    private static ObjectAnimator defaultFadeIn;
    private static ObjectAnimator defaultFadeOut;
    private static TimeInterpolator sAppearingInterpolator;
    private static TimeInterpolator sChangingAppearingInterpolator;
    private static TimeInterpolator sChangingDisappearingInterpolator;
    private static TimeInterpolator sChangingInterpolator;
    private static TimeInterpolator sDisappearingInterpolator;
    private final LinkedHashMap<View, Animator> currentAppearingAnimations;
    private final LinkedHashMap<View, Animator> currentChangingAnimations;
    private final LinkedHashMap<View, Animator> currentDisappearingAnimations;
    private final HashMap<View, View.OnLayoutChangeListener> layoutChangeListenerMap;
    private boolean mAnimateParentHierarchy;
    private Animator mAppearingAnim;
    private long mAppearingDelay;
    private long mAppearingDuration;
    private TimeInterpolator mAppearingInterpolator;
    private Animator mChangingAnim;
    private Animator mChangingAppearingAnim;
    private long mChangingAppearingDelay;
    private long mChangingAppearingDuration;
    private TimeInterpolator mChangingAppearingInterpolator;
    private long mChangingAppearingStagger;
    private long mChangingDelay;
    private Animator mChangingDisappearingAnim;
    private long mChangingDisappearingDelay;
    private long mChangingDisappearingDuration;
    private TimeInterpolator mChangingDisappearingInterpolator;
    private long mChangingDisappearingStagger;
    private long mChangingDuration;
    private TimeInterpolator mChangingInterpolator;
    private long mChangingStagger;
    private Animator mDisappearingAnim;
    private long mDisappearingDelay;
    private long mDisappearingDuration;
    private TimeInterpolator mDisappearingInterpolator;
    private ArrayList<TransitionListener> mListeners;
    private int mTransitionTypes;
    private final HashMap<View, Animator> pendingAnimations;
    private long staggerDelay;

    public interface TransitionListener {
        void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i);

        void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i);
    }

    static {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        DECEL_INTERPOLATOR = decelerateInterpolator;
        TimeInterpolator timeInterpolator = ACCEL_DECEL_INTERPOLATOR;
        sAppearingInterpolator = timeInterpolator;
        sDisappearingInterpolator = timeInterpolator;
        sChangingAppearingInterpolator = decelerateInterpolator;
        sChangingDisappearingInterpolator = decelerateInterpolator;
        sChangingInterpolator = decelerateInterpolator;
    }

    public LayoutTransition() {
        this.mDisappearingAnim = null;
        this.mAppearingAnim = null;
        this.mChangingAppearingAnim = null;
        this.mChangingDisappearingAnim = null;
        this.mChangingAnim = null;
        long j = DEFAULT_DURATION;
        this.mChangingAppearingDuration = j;
        this.mChangingDisappearingDuration = j;
        this.mChangingDuration = j;
        this.mAppearingDuration = j;
        this.mDisappearingDuration = j;
        this.mAppearingDelay = j;
        this.mDisappearingDelay = 0L;
        this.mChangingAppearingDelay = 0L;
        this.mChangingDisappearingDelay = j;
        this.mChangingDelay = 0L;
        this.mChangingAppearingStagger = 0L;
        this.mChangingDisappearingStagger = 0L;
        this.mChangingStagger = 0L;
        this.mAppearingInterpolator = sAppearingInterpolator;
        this.mDisappearingInterpolator = sDisappearingInterpolator;
        this.mChangingAppearingInterpolator = sChangingAppearingInterpolator;
        this.mChangingDisappearingInterpolator = sChangingDisappearingInterpolator;
        this.mChangingInterpolator = sChangingInterpolator;
        this.pendingAnimations = new HashMap<>();
        this.currentChangingAnimations = new LinkedHashMap<>();
        this.currentAppearingAnimations = new LinkedHashMap<>();
        this.currentDisappearingAnimations = new LinkedHashMap<>();
        this.layoutChangeListenerMap = new HashMap<>();
        this.mTransitionTypes = 15;
        this.mAnimateParentHierarchy = true;
        if (defaultChangeIn == null) {
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(null, PropertyValuesHolder.ofInt("left", 0, 1), PropertyValuesHolder.ofInt(GenerateXML.TOP, 0, 1), PropertyValuesHolder.ofInt("right", 0, 1), PropertyValuesHolder.ofInt(GenerateXML.BOTTOM, 0, 1), PropertyValuesHolder.ofInt("scrollX", 0, 1), PropertyValuesHolder.ofInt("scrollY", 0, 1));
            defaultChangeIn = ofPropertyValuesHolder;
            ofPropertyValuesHolder.setDuration(DEFAULT_DURATION);
            defaultChangeIn.setStartDelay(this.mChangingAppearingDelay);
            defaultChangeIn.setInterpolator(this.mChangingAppearingInterpolator);
            ObjectAnimator mo76clone = defaultChangeIn.mo76clone();
            defaultChangeOut = mo76clone;
            mo76clone.setStartDelay(this.mChangingDisappearingDelay);
            defaultChangeOut.setInterpolator(this.mChangingDisappearingInterpolator);
            ObjectAnimator mo76clone2 = defaultChangeIn.mo76clone();
            defaultChange = mo76clone2;
            mo76clone2.setStartDelay(this.mChangingDelay);
            defaultChange.setInterpolator(this.mChangingInterpolator);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
            defaultFadeIn = ofFloat;
            ofFloat.setDuration(DEFAULT_DURATION);
            defaultFadeIn.setStartDelay(this.mAppearingDelay);
            defaultFadeIn.setInterpolator(this.mAppearingInterpolator);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
            defaultFadeOut = ofFloat2;
            ofFloat2.setDuration(DEFAULT_DURATION);
            defaultFadeOut.setStartDelay(this.mDisappearingDelay);
            defaultFadeOut.setInterpolator(this.mDisappearingInterpolator);
        }
        this.mChangingAppearingAnim = defaultChangeIn;
        this.mChangingDisappearingAnim = defaultChangeOut;
        this.mChangingAnim = defaultChange;
        this.mAppearingAnim = defaultFadeIn;
        this.mDisappearingAnim = defaultFadeOut;
    }

    public void setDuration(long j) {
        this.mChangingAppearingDuration = j;
        this.mChangingDisappearingDuration = j;
        this.mChangingDuration = j;
        this.mAppearingDuration = j;
        this.mDisappearingDuration = j;
    }

    public void enableTransitionType(int i) {
        if (i == 0) {
            this.mTransitionTypes |= 4;
            return;
        }
        if (i == 1) {
            this.mTransitionTypes |= 8;
            return;
        }
        if (i == 2) {
            this.mTransitionTypes |= 1;
        } else if (i == 3) {
            this.mTransitionTypes |= 2;
        } else {
            if (i != 4) {
                return;
            }
            this.mTransitionTypes |= 16;
        }
    }

    public void disableTransitionType(int i) {
        if (i == 0) {
            this.mTransitionTypes &= -5;
            return;
        }
        if (i == 1) {
            this.mTransitionTypes &= -9;
            return;
        }
        if (i == 2) {
            this.mTransitionTypes &= -2;
        } else if (i == 3) {
            this.mTransitionTypes &= -3;
        } else {
            if (i != 4) {
                return;
            }
            this.mTransitionTypes &= -17;
        }
    }

    public boolean isTransitionTypeEnabled(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i == 4 && (this.mTransitionTypes & 16) == 16 : (this.mTransitionTypes & 2) == 2 : (this.mTransitionTypes & 1) == 1 : (this.mTransitionTypes & 8) == 8 : (this.mTransitionTypes & 4) == 4;
    }

    public void setStartDelay(int i, long j) {
        if (i == 0) {
            this.mChangingAppearingDelay = j;
            return;
        }
        if (i == 1) {
            this.mChangingDisappearingDelay = j;
            return;
        }
        if (i == 2) {
            this.mAppearingDelay = j;
        } else if (i == 3) {
            this.mDisappearingDelay = j;
        } else {
            if (i != 4) {
                return;
            }
            this.mChangingDelay = j;
        }
    }

    public long getStartDelay(int i) {
        if (i == 0) {
            return this.mChangingAppearingDelay;
        }
        if (i == 1) {
            return this.mChangingDisappearingDelay;
        }
        if (i == 2) {
            return this.mAppearingDelay;
        }
        if (i == 3) {
            return this.mDisappearingDelay;
        }
        if (i != 4) {
            return 0L;
        }
        return this.mChangingDelay;
    }

    public void setDuration(int i, long j) {
        if (i == 0) {
            this.mChangingAppearingDuration = j;
            return;
        }
        if (i == 1) {
            this.mChangingDisappearingDuration = j;
            return;
        }
        if (i == 2) {
            this.mAppearingDuration = j;
        } else if (i == 3) {
            this.mDisappearingDuration = j;
        } else {
            if (i != 4) {
                return;
            }
            this.mChangingDuration = j;
        }
    }

    public long getDuration(int i) {
        if (i == 0) {
            return this.mChangingAppearingDuration;
        }
        if (i == 1) {
            return this.mChangingDisappearingDuration;
        }
        if (i == 2) {
            return this.mAppearingDuration;
        }
        if (i == 3) {
            return this.mDisappearingDuration;
        }
        if (i != 4) {
            return 0L;
        }
        return this.mChangingDuration;
    }

    public void setStagger(int i, long j) {
        if (i == 0) {
            this.mChangingAppearingStagger = j;
        } else if (i == 1) {
            this.mChangingDisappearingStagger = j;
        } else {
            if (i != 4) {
                return;
            }
            this.mChangingStagger = j;
        }
    }

    public long getStagger(int i) {
        if (i == 0) {
            return this.mChangingAppearingStagger;
        }
        if (i == 1) {
            return this.mChangingDisappearingStagger;
        }
        if (i != 4) {
            return 0L;
        }
        return this.mChangingStagger;
    }

    public void setInterpolator(int i, TimeInterpolator timeInterpolator) {
        if (i == 0) {
            this.mChangingAppearingInterpolator = timeInterpolator;
            return;
        }
        if (i == 1) {
            this.mChangingDisappearingInterpolator = timeInterpolator;
            return;
        }
        if (i == 2) {
            this.mAppearingInterpolator = timeInterpolator;
        } else if (i == 3) {
            this.mDisappearingInterpolator = timeInterpolator;
        } else {
            if (i != 4) {
                return;
            }
            this.mChangingInterpolator = timeInterpolator;
        }
    }

    public TimeInterpolator getInterpolator(int i) {
        if (i == 0) {
            return this.mChangingAppearingInterpolator;
        }
        if (i == 1) {
            return this.mChangingDisappearingInterpolator;
        }
        if (i == 2) {
            return this.mAppearingInterpolator;
        }
        if (i == 3) {
            return this.mDisappearingInterpolator;
        }
        if (i != 4) {
            return null;
        }
        return this.mChangingInterpolator;
    }

    public void setAnimator(int i, Animator animator) {
        if (i == 0) {
            this.mChangingAppearingAnim = animator;
            return;
        }
        if (i == 1) {
            this.mChangingDisappearingAnim = animator;
            return;
        }
        if (i == 2) {
            this.mAppearingAnim = animator;
        } else if (i == 3) {
            this.mDisappearingAnim = animator;
        } else {
            if (i != 4) {
                return;
            }
            this.mChangingAnim = animator;
        }
    }

    public Animator getAnimator(int i) {
        if (i == 0) {
            return this.mChangingAppearingAnim;
        }
        if (i == 1) {
            return this.mChangingDisappearingAnim;
        }
        if (i == 2) {
            return this.mAppearingAnim;
        }
        if (i == 3) {
            return this.mDisappearingAnim;
        }
        if (i != 4) {
            return null;
        }
        return this.mChangingAnim;
    }

    private void runChangeTransition(ViewGroup viewGroup, View view, int i) {
        Animator animator;
        long j;
        ObjectAnimator objectAnimator;
        ObjectAnimator objectAnimator2;
        Animator animator2;
        int i2 = i;
        if (i2 == 2) {
            animator = this.mChangingAppearingAnim;
            j = this.mChangingAppearingDuration;
            objectAnimator = defaultChangeIn;
        } else if (i2 == 3) {
            animator = this.mChangingDisappearingAnim;
            j = this.mChangingDisappearingDuration;
            objectAnimator = defaultChangeOut;
        } else if (i2 != 4) {
            j = 0;
            animator = null;
            objectAnimator = null;
        } else {
            animator = this.mChangingAnim;
            j = this.mChangingDuration;
            objectAnimator = defaultChange;
        }
        if (animator == null) {
            return;
        }
        this.staggerDelay = 0L;
        ViewTreeObserver viewTreeObserver = viewGroup.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            int childCount = viewGroup.getChildCount();
            int i3 = 0;
            while (i3 < childCount) {
                long j2 = j;
                View childAt = viewGroup.getChildAt(i3);
                if (childAt != view) {
                    animator2 = animator;
                    setupChangeAnimation(viewGroup, i2, animator2, j2, childAt);
                } else {
                    animator2 = animator;
                }
                i3++;
                i2 = i;
                animator = animator2;
                j = j2;
            }
            long j3 = j;
            if (this.mAnimateParentHierarchy) {
                ViewGroup viewGroup2 = viewGroup;
                while (viewGroup2 != null) {
                    ViewParent parent = viewGroup2.getParent();
                    if (parent instanceof ViewGroup) {
                        ViewGroup viewGroup3 = (ViewGroup) parent;
                        objectAnimator2 = objectAnimator;
                        setupChangeAnimation(viewGroup3, i, objectAnimator2, j3, viewGroup2);
                        viewGroup2 = viewGroup3;
                    } else {
                        objectAnimator2 = objectAnimator;
                        viewGroup2 = null;
                    }
                    objectAnimator = objectAnimator2;
                }
            }
            CleanupCallback cleanupCallback = new CleanupCallback(this.layoutChangeListenerMap, viewGroup);
            viewTreeObserver.addOnPreDrawListener(cleanupCallback);
            viewGroup.addOnAttachStateChangeListener(cleanupCallback);
        }
    }

    public void setAnimateParentHierarchy(boolean z) {
        this.mAnimateParentHierarchy = z;
    }

    private void setupChangeAnimation(final ViewGroup viewGroup, final int i, Animator animator, final long j, final View view) {
        if (this.layoutChangeListenerMap.get(view) != null) {
            return;
        }
        if (view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        final Animator mo76clone = animator.mo76clone();
        mo76clone.setTarget(view);
        mo76clone.setupStartValues();
        Animator animator2 = this.pendingAnimations.get(view);
        if (animator2 != null) {
            animator2.cancel();
            this.pendingAnimations.remove(view);
        }
        this.pendingAnimations.put(view, mo76clone);
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(100 + j);
        duration.addListener(new AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator3) {
                LayoutTransition.this.pendingAnimations.remove(view);
            }
        });
        duration.start();
        final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: android.animation.LayoutTransition.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                long j2;
                mo76clone.setupEndValues();
                Animator animator3 = mo76clone;
                if (animator3 instanceof ValueAnimator) {
                    boolean z = false;
                    for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator3).getValues()) {
                        if (propertyValuesHolder.mKeyframes instanceof KeyframeSet) {
                            KeyframeSet keyframeSet = (KeyframeSet) propertyValuesHolder.mKeyframes;
                            if (keyframeSet.mFirstKeyframe != null && keyframeSet.mLastKeyframe != null && keyframeSet.mFirstKeyframe.getValue().equals(keyframeSet.mLastKeyframe.getValue())) {
                            }
                            z = true;
                        } else {
                            if (propertyValuesHolder.mKeyframes.getValue(0.0f).equals(propertyValuesHolder.mKeyframes.getValue(1.0f))) {
                            }
                            z = true;
                        }
                    }
                    if (!z) {
                        return;
                    }
                }
                int i10 = i;
                if (i10 == 2) {
                    j2 = LayoutTransition.this.mChangingAppearingDelay + LayoutTransition.this.staggerDelay;
                    LayoutTransition.this.staggerDelay += LayoutTransition.this.mChangingAppearingStagger;
                    if (LayoutTransition.this.mChangingAppearingInterpolator != LayoutTransition.sChangingAppearingInterpolator) {
                        mo76clone.setInterpolator(LayoutTransition.this.mChangingAppearingInterpolator);
                    }
                } else if (i10 == 3) {
                    j2 = LayoutTransition.this.mChangingDisappearingDelay + LayoutTransition.this.staggerDelay;
                    LayoutTransition.this.staggerDelay += LayoutTransition.this.mChangingDisappearingStagger;
                    if (LayoutTransition.this.mChangingDisappearingInterpolator != LayoutTransition.sChangingDisappearingInterpolator) {
                        mo76clone.setInterpolator(LayoutTransition.this.mChangingDisappearingInterpolator);
                    }
                } else if (i10 != 4) {
                    j2 = 0;
                } else {
                    j2 = LayoutTransition.this.mChangingDelay + LayoutTransition.this.staggerDelay;
                    LayoutTransition.this.staggerDelay += LayoutTransition.this.mChangingStagger;
                    if (LayoutTransition.this.mChangingInterpolator != LayoutTransition.sChangingInterpolator) {
                        mo76clone.setInterpolator(LayoutTransition.this.mChangingInterpolator);
                    }
                }
                mo76clone.setStartDelay(j2);
                mo76clone.setDuration(j);
                Animator animator4 = (Animator) LayoutTransition.this.currentChangingAnimations.get(view);
                if (animator4 != null) {
                    animator4.cancel();
                }
                if (((Animator) LayoutTransition.this.pendingAnimations.get(view)) != null) {
                    LayoutTransition.this.pendingAnimations.remove(view);
                }
                LayoutTransition.this.currentChangingAnimations.put(view, mo76clone);
                viewGroup.requestTransitionStart(LayoutTransition.this);
                view.removeOnLayoutChangeListener(this);
                LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }
        };
        mo76clone.addListener(new AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator3) {
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        TransitionListener transitionListener = (TransitionListener) it.next();
                        LayoutTransition layoutTransition = LayoutTransition.this;
                        ViewGroup viewGroup2 = viewGroup;
                        View view2 = view;
                        int i2 = i;
                        transitionListener.startTransition(layoutTransition, viewGroup2, view2, i2 == 2 ? 0 : i2 == 3 ? 1 : 4);
                    }
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator3) {
                view.removeOnLayoutChangeListener(onLayoutChangeListener);
                LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator3) {
                LayoutTransition.this.currentChangingAnimations.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        TransitionListener transitionListener = (TransitionListener) it.next();
                        LayoutTransition layoutTransition = LayoutTransition.this;
                        ViewGroup viewGroup2 = viewGroup;
                        View view2 = view;
                        int i2 = i;
                        transitionListener.endTransition(layoutTransition, viewGroup2, view2, i2 == 2 ? 0 : i2 == 3 ? 1 : 4);
                    }
                }
            }
        });
        view.addOnLayoutChangeListener(onLayoutChangeListener);
        this.layoutChangeListenerMap.put(view, onLayoutChangeListener);
    }

    public void startChangingAnimations() {
        for (Animator animator : ((LinkedHashMap) this.currentChangingAnimations.clone()).values()) {
            if (animator instanceof ObjectAnimator) {
                ((ObjectAnimator) animator).setCurrentPlayTime(0L);
            }
            animator.start();
        }
    }

    public void endChangingAnimations() {
        for (Animator animator : ((LinkedHashMap) this.currentChangingAnimations.clone()).values()) {
            animator.start();
            animator.end();
        }
        this.currentChangingAnimations.clear();
    }

    public boolean isChangingLayout() {
        return this.currentChangingAnimations.size() > 0;
    }

    public boolean isRunning() {
        return this.currentChangingAnimations.size() > 0 || this.currentAppearingAnimations.size() > 0 || this.currentDisappearingAnimations.size() > 0;
    }

    public void cancel() {
        if (this.currentChangingAnimations.size() > 0) {
            Iterator it = ((LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
            while (it.hasNext()) {
                ((Animator) it.next()).cancel();
            }
            this.currentChangingAnimations.clear();
        }
        if (this.currentAppearingAnimations.size() > 0) {
            Iterator it2 = ((LinkedHashMap) this.currentAppearingAnimations.clone()).values().iterator();
            while (it2.hasNext()) {
                ((Animator) it2.next()).end();
            }
            this.currentAppearingAnimations.clear();
        }
        if (this.currentDisappearingAnimations.size() > 0) {
            Iterator it3 = ((LinkedHashMap) this.currentDisappearingAnimations.clone()).values().iterator();
            while (it3.hasNext()) {
                ((Animator) it3.next()).end();
            }
            this.currentDisappearingAnimations.clear();
        }
    }

    public void cancel(int i) {
        if (i != 0 && i != 1) {
            if (i == 2) {
                if (this.currentAppearingAnimations.size() > 0) {
                    Iterator it = ((LinkedHashMap) this.currentAppearingAnimations.clone()).values().iterator();
                    while (it.hasNext()) {
                        ((Animator) it.next()).end();
                    }
                    this.currentAppearingAnimations.clear();
                    return;
                }
                return;
            }
            if (i == 3) {
                if (this.currentDisappearingAnimations.size() > 0) {
                    Iterator it2 = ((LinkedHashMap) this.currentDisappearingAnimations.clone()).values().iterator();
                    while (it2.hasNext()) {
                        ((Animator) it2.next()).end();
                    }
                    this.currentDisappearingAnimations.clear();
                    return;
                }
                return;
            }
            if (i != 4) {
                return;
            }
        }
        if (this.currentChangingAnimations.size() > 0) {
            Iterator it3 = ((LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
            while (it3.hasNext()) {
                ((Animator) it3.next()).cancel();
            }
            this.currentChangingAnimations.clear();
        }
    }

    private void runAppearingTransition(final ViewGroup viewGroup, final View view) {
        Animator animator = this.currentDisappearingAnimations.get(view);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.mAppearingAnim;
        if (animator2 == null) {
            if (hasListeners()) {
                Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
                while (it.hasNext()) {
                    ((TransitionListener) it.next()).endTransition(this, viewGroup, view, 2);
                }
                return;
            }
            return;
        }
        Animator mo76clone = animator2.mo76clone();
        mo76clone.setTarget(view);
        mo76clone.setStartDelay(this.mAppearingDelay);
        mo76clone.setDuration(this.mAppearingDuration);
        TimeInterpolator timeInterpolator = this.mAppearingInterpolator;
        if (timeInterpolator != sAppearingInterpolator) {
            mo76clone.setInterpolator(timeInterpolator);
        }
        if (mo76clone instanceof ObjectAnimator) {
            ((ObjectAnimator) mo76clone).setCurrentPlayTime(0L);
        }
        mo76clone.addListener(new AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator3) {
                LayoutTransition.this.currentAppearingAnimations.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it2 = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it2.hasNext()) {
                        ((TransitionListener) it2.next()).endTransition(LayoutTransition.this, viewGroup, view, 2);
                    }
                }
            }
        });
        this.currentAppearingAnimations.put(view, mo76clone);
        mo76clone.start();
    }

    private void runDisappearingTransition(final ViewGroup viewGroup, final View view) {
        Animator animator = this.currentAppearingAnimations.get(view);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.mDisappearingAnim;
        if (animator2 == null) {
            if (hasListeners()) {
                Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
                while (it.hasNext()) {
                    ((TransitionListener) it.next()).endTransition(this, viewGroup, view, 3);
                }
                return;
            }
            return;
        }
        Animator mo76clone = animator2.mo76clone();
        mo76clone.setStartDelay(this.mDisappearingDelay);
        mo76clone.setDuration(this.mDisappearingDuration);
        TimeInterpolator timeInterpolator = this.mDisappearingInterpolator;
        if (timeInterpolator != sDisappearingInterpolator) {
            mo76clone.setInterpolator(timeInterpolator);
        }
        mo76clone.setTarget(view);
        final float alpha = view.getAlpha();
        mo76clone.addListener(new AnimatorListenerAdapter() { // from class: android.animation.LayoutTransition.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator3) {
                LayoutTransition.this.currentDisappearingAnimations.remove(view);
                view.setAlpha(alpha);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it2 = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it2.hasNext()) {
                        ((TransitionListener) it2.next()).endTransition(LayoutTransition.this, viewGroup, view, 3);
                    }
                }
            }
        });
        if (mo76clone instanceof ObjectAnimator) {
            ((ObjectAnimator) mo76clone).setCurrentPlayTime(0L);
        }
        this.currentDisappearingAnimations.put(view, mo76clone);
        mo76clone.start();
    }

    private void addChild(ViewGroup viewGroup, View view, boolean z) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 1) == 1) {
            cancel(3);
        }
        if (z && (this.mTransitionTypes & 4) == 4) {
            cancel(0);
            cancel(4);
        }
        if (hasListeners() && (this.mTransitionTypes & 1) == 1) {
            Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((TransitionListener) it.next()).startTransition(this, viewGroup, view, 2);
            }
        }
        if (z && (this.mTransitionTypes & 4) == 4) {
            runChangeTransition(viewGroup, view, 2);
        }
        if ((this.mTransitionTypes & 1) == 1) {
            runAppearingTransition(viewGroup, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasListeners() {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        return arrayList != null && arrayList.size() > 0;
    }

    public void layoutChange(ViewGroup viewGroup) {
        if (viewGroup.getWindowVisibility() == 0 && (this.mTransitionTypes & 16) == 16 && !isRunning()) {
            runChangeTransition(viewGroup, null, 4);
        }
    }

    public void addChild(ViewGroup viewGroup, View view) {
        addChild(viewGroup, view, true);
    }

    @Deprecated
    public void showChild(ViewGroup viewGroup, View view) {
        addChild(viewGroup, view, true);
    }

    public void showChild(ViewGroup viewGroup, View view, int i) {
        addChild(viewGroup, view, i == 8);
    }

    private void removeChild(ViewGroup viewGroup, View view, boolean z) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 2) == 2) {
            cancel(2);
        }
        if (z && (this.mTransitionTypes & 8) == 8) {
            cancel(1);
            cancel(4);
        }
        if (hasListeners() && (this.mTransitionTypes & 2) == 2) {
            Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((TransitionListener) it.next()).startTransition(this, viewGroup, view, 3);
            }
        }
        if (z && (this.mTransitionTypes & 8) == 8) {
            runChangeTransition(viewGroup, view, 3);
        }
        if ((this.mTransitionTypes & 2) == 2) {
            runDisappearingTransition(viewGroup, view);
        }
    }

    public void removeChild(ViewGroup viewGroup, View view) {
        removeChild(viewGroup, view, true);
    }

    @Deprecated
    public void hideChild(ViewGroup viewGroup, View view) {
        removeChild(viewGroup, view, true);
    }

    public void hideChild(ViewGroup viewGroup, View view, int i) {
        removeChild(viewGroup, view, i == 8);
    }

    public void addTransitionListener(TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(transitionListener);
    }

    public void removeTransitionListener(TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(transitionListener);
    }

    public List<TransitionListener> getTransitionListeners() {
        return this.mListeners;
    }

    private static final class CleanupCallback implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        final Map<View, View.OnLayoutChangeListener> layoutChangeListenerMap;
        final ViewGroup parent;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        CleanupCallback(Map<View, View.OnLayoutChangeListener> map, ViewGroup viewGroup) {
            this.layoutChangeListenerMap = map;
            this.parent = viewGroup;
        }

        private void cleanup() {
            this.parent.getViewTreeObserver().removeOnPreDrawListener(this);
            this.parent.removeOnAttachStateChangeListener(this);
            if (this.layoutChangeListenerMap.size() > 0) {
                for (View view : this.layoutChangeListenerMap.keySet()) {
                    view.removeOnLayoutChangeListener(this.layoutChangeListenerMap.get(view));
                }
                this.layoutChangeListenerMap.clear();
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            cleanup();
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            cleanup();
            return true;
        }
    }
}
