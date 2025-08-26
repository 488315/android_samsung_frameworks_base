package android.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityTransitionCoordinator;
import android.app.SharedElementCallback;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
class EnterTransitionCoordinator extends ActivityTransitionCoordinator {
    private static final int MIN_ANIMATION_FRAMES = 2;
    private static final String TAG = "EnterTransitionCoordinator";
    private Activity mActivity;
    private boolean mAreViewsReady;
    private ObjectAnimator mBackgroundAnimator;
    private Transition mEnterViewsTransition;
    private boolean mHasStopped;
    private boolean mIsCanceled;
    private final boolean mIsCrossTask;
    private boolean mIsExitTransitionComplete;
    private boolean mIsReadyForTransition;
    private boolean mIsTaskRoot;
    private boolean mIsViewsTransitionStarted;
    private Runnable mOnTransitionComplete;
    private ArrayList<String> mPendingExitNames;
    private Drawable mReplacedBackground;
    private boolean mSharedElementTransitionStarted;
    private Bundle mSharedElementsBundle;
    private OneShotPreDrawListener mViewsReadyListener;
    private boolean mWasOpaque;

    EnterTransitionCoordinator(Activity activity, ResultReceiver resultReceiver, ArrayList<String> arrayList, boolean z, boolean z2) {
        super(activity.getWindow(), arrayList, getListener(activity, z && !z2), z);
        this.mActivity = activity;
        this.mIsCrossTask = z2;
        setResultReceiver(resultReceiver);
        prepareEnter();
        Bundle bundle = new Bundle();
        bundle.putParcelable("android:remoteReceiver", this);
        this.mResultReceiver.send(100, bundle);
        final ViewGroup decor = getDecor();
        if (decor != null) {
            final ViewTreeObserver viewTreeObserver = decor.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.app.EnterTransitionCoordinator.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    if (!EnterTransitionCoordinator.this.mIsReadyForTransition) {
                        return false;
                    }
                    if (viewTreeObserver.isAlive()) {
                        viewTreeObserver.removeOnPreDrawListener(this);
                        return false;
                    }
                    decor.getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        }
        Slog.d(TAG, "EnterTransitionCoordinator is created, activity=" + activity + ", mWindow=" + getWindow() + ", caller=" + Debug.getCallers(3));
    }

    boolean isCrossTask() {
        return this.mIsCrossTask;
    }

    public void viewInstancesReady(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<View> arrayList3) {
        for (int i = 0; i < arrayList3.size(); i++) {
            View view = arrayList3.get(i);
            if (!TextUtils.equals(view.getTransitionName(), arrayList2.get(i)) || !view.isAttachedToWindow()) {
                triggerViewsReady(mapNamedElements(arrayList, arrayList2));
                return;
            }
        }
        triggerViewsReady(mapSharedElements(arrayList, arrayList3));
    }

    public void namedViewsReady(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        triggerViewsReady(mapNamedElements(arrayList, arrayList2));
    }

    public Transition getEnterViewsTransition() {
        return this.mEnterViewsTransition;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void viewsReady(ArrayMap<String, View> arrayMap) {
        super.viewsReady(arrayMap);
        this.mIsReadyForTransition = true;
        hideViews(this.mSharedElements);
        Transition viewsTransition = getViewsTransition();
        if (viewsTransition != null && this.mTransitioningViews != null) {
            removeExcludedViews(viewsTransition, this.mTransitioningViews);
            stripOffscreenViews();
            hideViews(this.mTransitioningViews);
        }
        if (this.mIsReturning) {
            sendSharedElementDestination();
        } else {
            moveSharedElementsToOverlay();
        }
        if (this.mSharedElementsBundle != null) {
            onTakeSharedElements();
        }
    }

    private void triggerViewsReady(final ArrayMap<String, View> arrayMap) {
        if (this.mAreViewsReady) {
            return;
        }
        this.mAreViewsReady = true;
        ViewGroup decor = getDecor();
        if (decor == null || (decor.isAttachedToWindow() && (arrayMap.isEmpty() || !arrayMap.valueAt(0).isLayoutRequested()))) {
            viewsReady(arrayMap);
        } else {
            this.mViewsReadyListener = OneShotPreDrawListener.add(decor, new Runnable() { // from class: android.app.EnterTransitionCoordinator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$triggerViewsReady$0(arrayMap);
                }
            });
            decor.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerViewsReady$0(ArrayMap arrayMap) {
        this.mViewsReadyListener = null;
        viewsReady(arrayMap);
    }

    private ArrayMap<String, View> mapNamedElements(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        View view;
        ArrayMap<String, View> arrayMap = new ArrayMap<>();
        ViewGroup decor = getDecor();
        if (decor != null) {
            decor.findNamedViews(arrayMap);
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList2.size(); i++) {
                String str = arrayList2.get(i);
                String str2 = arrayList.get(i);
                if (str != null && !str.equals(str2) && (view = arrayMap.get(str)) != null) {
                    arrayMap.put(str2, view);
                }
            }
        }
        return arrayMap;
    }

    private void sendSharedElementDestination() {
        ViewGroup decor = getDecor();
        boolean z = false;
        if (!allowOverlappingTransitions() || getEnterViewsTransition() == null) {
            if (decor == null) {
                z = true;
            } else {
                boolean zIsLayoutRequested = decor.isLayoutRequested();
                boolean z2 = !zIsLayoutRequested;
                if (zIsLayoutRequested) {
                    z = z2;
                } else {
                    for (int i = 0; i < this.mSharedElements.size(); i++) {
                        if (this.mSharedElements.get(i).isLayoutRequested()) {
                            break;
                        }
                    }
                    z = z2;
                }
            }
        }
        if (z) {
            Bundle bundleCaptureSharedElementState = captureSharedElementState();
            moveSharedElementsToOverlay();
            if (this.mResultReceiver != null) {
                this.mResultReceiver.send(107, bundleCaptureSharedElementState);
            }
        } else if (decor != null) {
            OneShotPreDrawListener.add(decor, new Runnable() { // from class: android.app.EnterTransitionCoordinator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendSharedElementDestination$1();
                }
            });
        }
        if (allowOverlappingTransitions()) {
            startEnterTransitionOnly();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSharedElementDestination$1() {
        if (this.mResultReceiver != null) {
            Bundle bundleCaptureSharedElementState = captureSharedElementState();
            moveSharedElementsToOverlay();
            this.mResultReceiver.send(107, bundleCaptureSharedElementState);
        }
    }

    private static SharedElementCallback getListener(Activity activity, boolean z) {
        return z ? activity.mExitTransitionListener : activity.mEnterTransitionListener;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        if (i == 103) {
            if (this.mIsCanceled) {
                return;
            }
            this.mSharedElementsBundle = bundle;
            onTakeSharedElements();
            return;
        }
        if (i == 104) {
            if (this.mIsCanceled) {
                return;
            }
            this.mIsExitTransitionComplete = true;
            if (this.mSharedElementTransitionStarted) {
                onRemoteExitTransitionComplete();
                return;
            }
            return;
        }
        if (i == 106) {
            cancel();
        } else {
            if (i != 108 || this.mIsCanceled || this.mIsTaskRoot) {
                return;
            }
            this.mPendingExitNames = this.mAllSharedElementNames;
        }
    }

    public boolean isWaitingForRemoteExit() {
        return this.mIsReturning && this.mResultReceiver != null;
    }

    public ArrayList<String> getPendingExitSharedElementNames() {
        return this.mPendingExitNames;
    }

    public void forceViewsToAppear() {
        OneShotPreDrawListener oneShotPreDrawListener;
        if (this.mIsReturning) {
            if (!this.mIsReadyForTransition) {
                this.mIsReadyForTransition = true;
                if (getDecor() != null && (oneShotPreDrawListener = this.mViewsReadyListener) != null) {
                    oneShotPreDrawListener.removeListener();
                    this.mViewsReadyListener = null;
                }
                showViews(this.mTransitioningViews, true);
                setTransitioningViewsVisiblity(0, true);
                this.mSharedElements.clear();
                this.mAllSharedElementNames.clear();
                this.mTransitioningViews.clear();
                this.mIsReadyForTransition = true;
                viewsTransitionComplete();
                sharedElementTransitionComplete();
            } else {
                if (!this.mSharedElementTransitionStarted) {
                    moveSharedElementsFromOverlay();
                    this.mSharedElementTransitionStarted = true;
                    showViews(this.mSharedElements, true);
                    this.mSharedElements.clear();
                    sharedElementTransitionComplete();
                }
                if (!this.mIsViewsTransitionStarted) {
                    this.mIsViewsTransitionStarted = true;
                    showViews(this.mTransitioningViews, true);
                    setTransitioningViewsVisiblity(0, true);
                    this.mTransitioningViews.clear();
                    viewsTransitionComplete();
                }
                cancelPendingTransitions();
            }
            this.mAreViewsReady = true;
            if (this.mResultReceiver != null) {
                this.mResultReceiver.send(106, null);
                this.mResultReceiver = null;
            }
        }
    }

    private void cancel() {
        if (this.mIsCanceled) {
            return;
        }
        this.mIsCanceled = true;
        if (getViewsTransition() == null || this.mIsViewsTransitionStarted) {
            showViews(this.mSharedElements, true);
        } else if (this.mTransitioningViews != null) {
            this.mTransitioningViews.addAll(this.mSharedElements);
        }
        moveSharedElementsFromOverlay();
        this.mSharedElementNames.clear();
        this.mSharedElements.clear();
        this.mAllSharedElementNames.clear();
        startSharedElementTransition(null);
        onRemoteExitTransitionComplete();
    }

    public boolean isReturning() {
        return this.mIsReturning;
    }

    protected void prepareEnter() {
        Drawable drawableMutate;
        ViewGroup decor = getDecor();
        Activity activity = this.mActivity;
        if (activity == null || decor == null) {
            return;
        }
        this.mIsTaskRoot = activity.isTaskRoot();
        if (!isCrossTask()) {
            this.mActivity.overridePendingTransition(0, 0);
        }
        if (!this.mIsReturning) {
            this.mWasOpaque = this.mActivity.convertToTranslucent(null, null);
            Drawable background = decor.getBackground();
            if (background == null) {
                drawableMutate = new ColorDrawable(0);
                this.mReplacedBackground = drawableMutate;
            } else {
                getWindow().setBackgroundDrawable(null);
                drawableMutate = background.mutate();
                drawableMutate.setAlpha(0);
            }
            getWindow().setBackgroundDrawable(drawableMutate);
            return;
        }
        this.mActivity = null;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected Transition getViewsTransition() {
        Window window = getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getReenterTransition();
        }
        return window.getEnterTransition();
    }

    protected Transition getSharedElementTransition() {
        Window window = getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getSharedElementReenterTransition();
        }
        return window.getSharedElementEnterTransition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSharedElementTransition(Bundle bundle) {
        ViewGroup decor = getDecor();
        if (decor == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mAllSharedElementNames);
        arrayList.removeAll(this.mSharedElementNames);
        ArrayList<View> arrayListCreateSnapshots = createSnapshots(bundle, arrayList);
        if (this.mListener != null) {
            this.mListener.onRejectSharedElements(arrayListCreateSnapshots);
        }
        removeNullViews(arrayListCreateSnapshots);
        startRejectedAnimations(arrayListCreateSnapshots);
        ArrayList<View> arrayListCreateSnapshots2 = createSnapshots(bundle, this.mSharedElementNames);
        showViews(this.mSharedElements, true);
        scheduleSetSharedElementEnd(arrayListCreateSnapshots2);
        ArrayList<ActivityTransitionCoordinator.SharedElementOriginalState> sharedElementState = setSharedElementState(bundle, arrayListCreateSnapshots2);
        requestLayoutForSharedElements();
        boolean z = allowOverlappingTransitions() && !this.mIsReturning;
        lambda$scheduleGhostVisibilityChange$1(4);
        scheduleGhostVisibilityChange(4);
        pauseInput();
        Transition transitionBeginTransition = beginTransition(decor, z, true);
        scheduleGhostVisibilityChange(0);
        lambda$scheduleGhostVisibilityChange$1(0);
        if (z) {
            startEnterTransition(transitionBeginTransition);
        }
        setOriginalSharedElementState(this.mSharedElements, sharedElementState);
        if (this.mResultReceiver != null) {
            decor.postOnAnimation(new Runnable() { // from class: android.app.EnterTransitionCoordinator.2
                int mAnimations;

                @Override // java.lang.Runnable
                public void run() {
                    int i = this.mAnimations;
                    this.mAnimations = i + 1;
                    if (i < 2) {
                        ViewGroup decor2 = EnterTransitionCoordinator.this.getDecor();
                        if (decor2 != null) {
                            decor2.postOnAnimation(this);
                            return;
                        }
                        return;
                    }
                    if (EnterTransitionCoordinator.this.mResultReceiver != null) {
                        EnterTransitionCoordinator.this.mResultReceiver.send(101, null);
                        EnterTransitionCoordinator.this.mResultReceiver = null;
                    }
                }
            });
        }
    }

    private static void removeNullViews(ArrayList<View> arrayList) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size) == null) {
                    arrayList.remove(size);
                }
            }
        }
    }

    private void onTakeSharedElements() {
        Bundle bundle;
        if (!this.mIsReadyForTransition || (bundle = this.mSharedElementsBundle) == null) {
            return;
        }
        this.mSharedElementsBundle = null;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(bundle);
        if (this.mListener == null) {
            anonymousClass3.onSharedElementsReady();
        } else {
            this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, anonymousClass3);
        }
    }

    /* renamed from: android.app.EnterTransitionCoordinator$3, reason: invalid class name */
    class AnonymousClass3 implements SharedElementCallback.OnSharedElementsReadyListener {
        final /* synthetic */ Bundle val$sharedElementState;

        AnonymousClass3(Bundle bundle) {
            this.val$sharedElementState = bundle;
        }

        @Override // android.app.SharedElementCallback.OnSharedElementsReadyListener
        public void onSharedElementsReady() {
            ViewGroup decor = EnterTransitionCoordinator.this.getDecor();
            if (decor != null) {
                final Bundle bundle = this.val$sharedElementState;
                OneShotPreDrawListener.add(decor, false, new Runnable() { // from class: android.app.EnterTransitionCoordinator$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSharedElementsReady$1(bundle);
                    }
                });
                decor.invalidate();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedElementsReady$1(final Bundle bundle) {
            EnterTransitionCoordinator.this.startTransition(new Runnable() { // from class: android.app.EnterTransitionCoordinator$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSharedElementsReady$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedElementsReady$0(Bundle bundle) {
            EnterTransitionCoordinator.this.startSharedElementTransition(bundle);
        }
    }

    private void requestLayoutForSharedElements() {
        int size = this.mSharedElements.size();
        for (int i = 0; i < size; i++) {
            this.mSharedElements.get(i).requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Transition beginTransition(ViewGroup viewGroup, boolean z, boolean z2) {
        Transition transitionConfigureTransition;
        Transition transitionConfigureTransition2 = null;
        if (z2) {
            transitionConfigureTransition = !this.mSharedElementNames.isEmpty() ? configureTransition(getSharedElementTransition(), false) : null;
            if (transitionConfigureTransition == null) {
                sharedElementTransitionStarted();
                sharedElementTransitionComplete();
            } else {
                transitionConfigureTransition.addListener(new TransitionListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.4
                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionStart(Transition transition) {
                        EnterTransitionCoordinator.this.sharedElementTransitionStarted();
                    }

                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionEnd(Transition transition) {
                        transition.removeListener(this);
                        EnterTransitionCoordinator.this.sharedElementTransitionComplete();
                    }
                });
            }
        } else {
            transitionConfigureTransition = null;
        }
        if (z) {
            this.mIsViewsTransitionStarted = true;
            if (this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
                transitionConfigureTransition2 = configureTransition(getViewsTransition(), true);
            }
            if (transitionConfigureTransition2 == null) {
                viewsTransitionComplete();
            } else {
                final ArrayList<View> arrayList = this.mTransitioningViews;
                transitionConfigureTransition2.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener() { // from class: android.app.EnterTransitionCoordinator.5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionStart(Transition transition) {
                        EnterTransitionCoordinator.this.mEnterViewsTransition = transition;
                        ArrayList<View> arrayList2 = arrayList;
                        if (arrayList2 != null) {
                            EnterTransitionCoordinator.this.showViews(arrayList2, false);
                        }
                        super.onTransitionStart(transition);
                    }

                    @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionEnd(Transition transition) {
                        EnterTransitionCoordinator.this.mEnterViewsTransition = null;
                        transition.removeListener(this);
                        EnterTransitionCoordinator.this.viewsTransitionComplete();
                        super.onTransitionEnd(transition);
                    }
                });
            }
        }
        Transition transitionMergeTransitions = mergeTransitions(transitionConfigureTransition, transitionConfigureTransition2);
        if (transitionMergeTransitions != null) {
            transitionMergeTransitions.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener());
            if (z) {
                setTransitioningViewsVisiblity(4, false);
            }
            TransitionManager.beginDelayedTransition(viewGroup, transitionMergeTransitions);
            if (z) {
                setTransitioningViewsVisiblity(0, false);
            }
            viewGroup.invalidate();
            return transitionMergeTransitions;
        }
        transitionStarted();
        return transitionMergeTransitions;
    }

    public void runAfterTransitionsComplete(Runnable runnable) {
        if (!isTransitionRunning()) {
            onTransitionsComplete();
        } else {
            this.mOnTransitionComplete = runnable;
        }
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void onTransitionsComplete() {
        moveSharedElementsFromOverlay();
        ViewGroup decor = getDecor();
        if (decor != null) {
            decor.sendAccessibilityEvent(2048);
            Window window = getWindow();
            if (window != null && this.mReplacedBackground == decor.getBackground()) {
                window.setBackgroundDrawable(null);
            }
        }
        Runnable runnable = this.mOnTransitionComplete;
        if (runnable != null) {
            runnable.run();
            this.mOnTransitionComplete = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sharedElementTransitionStarted() {
        this.mSharedElementTransitionStarted = true;
        if (this.mIsExitTransitionComplete) {
            send(104, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEnterTransition(Transition transition) {
        ViewGroup decor = getDecor();
        if (!this.mIsReturning && decor != null) {
            Drawable background = decor.getBackground();
            if (background == null) {
                if (transition != null) {
                    transition.addListener(new TransitionListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.7
                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionEnd(Transition transition2) {
                            transition2.removeListener(this);
                            EnterTransitionCoordinator.this.makeOpaque();
                        }
                    });
                    backgroundAnimatorComplete();
                    return;
                } else {
                    makeOpaque();
                    backgroundAnimatorComplete();
                    return;
                }
            }
            Drawable drawableMutate = background.mutate();
            getWindow().setBackgroundDrawable(drawableMutate);
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(drawableMutate, "alpha", 255);
            this.mBackgroundAnimator = objectAnimatorOfInt;
            objectAnimatorOfInt.setDuration(getFadeDuration());
            this.mBackgroundAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    EnterTransitionCoordinator.this.makeOpaque();
                    EnterTransitionCoordinator.this.backgroundAnimatorComplete();
                }
            });
            this.mBackgroundAnimator.start();
            return;
        }
        backgroundAnimatorComplete();
    }

    public void stop() {
        ViewGroup decor;
        Drawable background;
        ObjectAnimator objectAnimator = this.mBackgroundAnimator;
        if (objectAnimator != null) {
            objectAnimator.end();
            this.mBackgroundAnimator = null;
        } else if (this.mWasOpaque && (decor = getDecor()) != null && (background = decor.getBackground()) != null) {
            background.setAlpha(255);
        }
        makeOpaque();
        this.mIsCanceled = true;
        this.mResultReceiver = null;
        this.mActivity = null;
        moveSharedElementsFromOverlay();
        if (this.mTransitioningViews != null) {
            showViews(this.mTransitioningViews, true);
            setTransitioningViewsVisiblity(0, true);
        }
        showViews(this.mSharedElements, true);
        clearState();
    }

    public boolean cancelEnter() {
        lambda$scheduleGhostVisibilityChange$1(4);
        this.mHasStopped = true;
        this.mIsCanceled = true;
        clearState();
        return super.cancelPendingTransitions();
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void clearState() {
        this.mSharedElementsBundle = null;
        this.mEnterViewsTransition = null;
        this.mResultReceiver = null;
        ObjectAnimator objectAnimator = this.mBackgroundAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        Runnable runnable = this.mOnTransitionComplete;
        if (runnable != null) {
            runnable.run();
            this.mOnTransitionComplete = null;
        }
        super.clearState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeOpaque() {
        Activity activity;
        if (this.mHasStopped || (activity = this.mActivity) == null) {
            return;
        }
        if (this.mWasOpaque) {
            activity.convertFromTranslucent();
        }
        this.mActivity = null;
    }

    private boolean allowOverlappingTransitions() {
        Window window = getWindow();
        if (window == null) {
            return false;
        }
        return this.mIsReturning ? window.getAllowReturnTransitionOverlap() : window.getAllowEnterTransitionOverlap();
    }

    private void startRejectedAnimations(final ArrayList<View> arrayList) {
        final ViewGroup decor;
        if (arrayList == null || arrayList.isEmpty() || (decor = getDecor()) == null) {
            return;
        }
        ViewGroupOverlay overlay = decor.getOverlay();
        int size = arrayList.size();
        ObjectAnimator objectAnimatorOfFloat = null;
        for (int i = 0; i < size; i++) {
            View view = arrayList.get(i);
            overlay.add(view);
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f);
            objectAnimatorOfFloat.start();
        }
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: android.app.EnterTransitionCoordinator.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ViewGroupOverlay overlay2 = decor.getOverlay();
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    overlay2.remove((View) arrayList.get(i2));
                }
            }
        });
    }

    protected void onRemoteExitTransitionComplete() {
        if (allowOverlappingTransitions()) {
            return;
        }
        startEnterTransitionOnly();
    }

    private void startEnterTransitionOnly() {
        startTransition(new Runnable() { // from class: android.app.EnterTransitionCoordinator.9
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup decor = EnterTransitionCoordinator.this.getDecor();
                if (decor != null) {
                    EnterTransitionCoordinator.this.startEnterTransition(EnterTransitionCoordinator.this.beginTransition(decor, true, false));
                }
            }
        });
    }
}
