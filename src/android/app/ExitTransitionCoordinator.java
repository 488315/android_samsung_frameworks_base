package android.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityTransitionCoordinator;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ExitTransitionCoordinator extends ActivityTransitionCoordinator {
    private static final String TAG = "ExitTransitionCoordinator";
    static long sMaxWaitMillis = 1000;
    private ObjectAnimator mBackgroundAnimator;
    private ExitTransitionCallbacks mExitCallbacks;
    private boolean mExitNotified;
    private Bundle mExitSharedElementBundle;
    private Handler mHandler;
    private boolean mIsBackgroundReady;
    private boolean mIsCanceled;
    private boolean mIsExitStarted;
    private boolean mIsHidden;
    private Bundle mSharedElementBundle;
    private boolean mSharedElementNotified;
    private boolean mSharedElementsHidden;

    public interface ExitTransitionCallbacks {
        default void hideSharedElements() {
        }

        boolean isReturnTransitionAllowed();

        void onFinish();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ ArrayList copyMappedViews() {
        return super.copyMappedViews();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ ArrayList getAcceptedNames() {
        return super.getAcceptedNames();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ ViewGroup getDecor() {
        return super.getDecor();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ ArrayList getMappedNames() {
        return super.getMappedNames();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ boolean isTransitionRunning() {
        return super.isTransitionRunning();
    }

    public ExitTransitionCoordinator(ExitTransitionCallbacks exitTransitionCallbacks, Window window, SharedElementCallback sharedElementCallback, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<View> arrayList3, boolean z) {
        super(window, arrayList, sharedElementCallback, z);
        viewsReady(mapSharedElements(arrayList2, arrayList3));
        stripOffscreenViews();
        this.mIsBackgroundReady = !z;
        this.mExitCallbacks = exitTransitionCallbacks;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        if (i == 100) {
            stopCancel();
            this.mResultReceiver = (ResultReceiver) bundle.getParcelable("android:remoteReceiver", ResultReceiver.class);
            if (this.mIsCanceled) {
                this.mResultReceiver.send(106, null);
                this.mResultReceiver = null;
                return;
            } else {
                notifyComplete();
                return;
            }
        }
        if (i == 101) {
            stopCancel();
            if (this.mIsCanceled) {
                return;
            }
            hideSharedElements();
            return;
        }
        switch (i) {
            case 105:
                this.mHandler.removeMessages(106);
                startExit();
                break;
            case 106:
                this.mIsCanceled = true;
                finish();
                break;
            case 107:
                this.mExitSharedElementBundle = bundle;
                sharedElementExitBack();
                break;
        }
    }

    private void stopCancel() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(106);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayCancel() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(106, sMaxWaitMillis);
        }
    }

    public void resetViews() {
        ViewGroup decor = getDecor();
        if (decor != null) {
            TransitionManager.endTransitions(decor);
        }
        if (this.mTransitioningViews != null) {
            showViews(this.mTransitioningViews, true);
            setTransitioningViewsVisiblity(0, true);
        }
        showViews(this.mSharedElements, true);
        this.mIsHidden = true;
        if (!this.mIsReturning && decor != null) {
            decor.suppressLayout(false);
        }
        moveSharedElementsFromOverlay();
        clearState();
    }

    private void sharedElementExitBack() {
        Bundle bundle;
        final ViewGroup decor = getDecor();
        if (decor != null) {
            decor.suppressLayout(true);
        }
        if (decor != null && (bundle = this.mExitSharedElementBundle) != null && !bundle.isEmpty() && !this.mSharedElements.isEmpty() && getSharedElementTransition() != null) {
            startTransition(new Runnable() { // from class: android.app.ExitTransitionCoordinator.1
                @Override // java.lang.Runnable
                public void run() {
                    ExitTransitionCoordinator.this.startSharedElementExit(decor);
                }
            });
        } else {
            sharedElementTransitionComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSharedElementExit(ViewGroup viewGroup) {
        Transition sharedElementExitTransition = getSharedElementExitTransition();
        sharedElementExitTransition.addListener(new TransitionListenerAdapter() { // from class: android.app.ExitTransitionCoordinator.2
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                if (ExitTransitionCoordinator.this.isViewsTransitionComplete()) {
                    ExitTransitionCoordinator.this.delayCancel();
                }
            }
        });
        final ArrayList<View> arrayListCreateSnapshots = createSnapshots(this.mExitSharedElementBundle, this.mSharedElementNames);
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: android.app.ExitTransitionCoordinator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startSharedElementExit$0(arrayListCreateSnapshots);
            }
        });
        lambda$scheduleGhostVisibilityChange$1(4);
        scheduleGhostVisibilityChange(4);
        if (this.mListener != null) {
            this.mListener.onSharedElementEnd(this.mSharedElementNames, this.mSharedElements, arrayListCreateSnapshots);
        }
        TransitionManager.beginDelayedTransition(viewGroup, sharedElementExitTransition);
        scheduleGhostVisibilityChange(0);
        lambda$scheduleGhostVisibilityChange$1(0);
        viewGroup.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSharedElementExit$0(ArrayList arrayList) {
        setSharedElementState(this.mExitSharedElementBundle, arrayList);
    }

    private void hideSharedElements() {
        moveSharedElementsFromOverlay();
        ExitTransitionCallbacks exitTransitionCallbacks = this.mExitCallbacks;
        if (exitTransitionCallbacks != null) {
            exitTransitionCallbacks.hideSharedElements();
        }
        if (!this.mIsHidden) {
            hideViews(this.mSharedElements);
        }
        this.mSharedElementsHidden = true;
        finishIfNecessary();
    }

    public void startExit() {
        if (this.mIsExitStarted) {
            return;
        }
        backgroundAnimatorComplete();
        this.mIsExitStarted = true;
        pauseInput();
        ViewGroup decor = getDecor();
        if (decor != null) {
            decor.suppressLayout(true);
        }
        moveSharedElementsToOverlay();
        startTransition(new Runnable() { // from class: android.app.ExitTransitionCoordinator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.beginTransitions();
            }
        });
    }

    public void startExit(Activity activity) {
        ArrayList<String> arrayList;
        int i = activity.mResultCode;
        Intent intent = activity.mResultData;
        if (this.mIsExitStarted) {
            return;
        }
        this.mIsExitStarted = true;
        pauseInput();
        ViewGroup decor = getDecor();
        if (decor != null) {
            decor.suppressLayout(true);
        }
        this.mHandler = new Handler() { // from class: android.app.ExitTransitionCoordinator.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                ExitTransitionCoordinator.this.mIsCanceled = true;
                ExitTransitionCoordinator.this.finish();
            }
        };
        delayCancel();
        moveSharedElementsToOverlay();
        if (decor != null && decor.getBackground() == null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        if (decor == null || decor.getContext().getApplicationInfo().targetSdkVersion >= 23) {
            arrayList = this.mSharedElementNames;
        } else {
            arrayList = this.mAllSharedElementNames;
        }
        activity.convertToTranslucent(new Activity.TranslucentConversionListener() { // from class: android.app.ExitTransitionCoordinator.4
            @Override // android.app.Activity.TranslucentConversionListener
            public void onTranslucentConversionComplete(boolean z) {
                if (ExitTransitionCoordinator.this.mIsCanceled) {
                    return;
                }
                ExitTransitionCoordinator.this.fadeOutBackground();
            }
        }, ActivityOptions.makeSceneTransitionAnimation(activity, this, arrayList, i, intent));
        startTransition(new Runnable() { // from class: android.app.ExitTransitionCoordinator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startExitTransition();
            }
        });
    }

    public void stop(Activity activity) {
        if (!this.mIsReturning || this.mExitCallbacks == null) {
            return;
        }
        activity.convertToTranslucent(null, null);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startExitTransition() {
        Transition exitTransition = getExitTransition();
        ViewGroup decor = getDecor();
        if (exitTransition != null && decor != null && this.mTransitioningViews != null) {
            setTransitioningViewsVisiblity(0, false);
            TransitionManager.beginDelayedTransition(decor, exitTransition);
            setTransitioningViewsVisiblity(4, false);
            decor.invalidate();
            return;
        }
        transitionStarted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeOutBackground() {
        Drawable background;
        if (this.mBackgroundAnimator == null) {
            ViewGroup decor = getDecor();
            if (decor != null && (background = decor.getBackground()) != null) {
                Drawable drawableMutate = background.mutate();
                getWindow().setBackgroundDrawable(drawableMutate);
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(drawableMutate, "alpha", 0);
                this.mBackgroundAnimator = objectAnimatorOfInt;
                objectAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: android.app.ExitTransitionCoordinator.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        ExitTransitionCoordinator.this.mBackgroundAnimator = null;
                        if (!ExitTransitionCoordinator.this.mIsCanceled) {
                            ExitTransitionCoordinator.this.mIsBackgroundReady = true;
                            ExitTransitionCoordinator.this.notifyComplete();
                        }
                        ExitTransitionCoordinator.this.backgroundAnimatorComplete();
                    }
                });
                this.mBackgroundAnimator.setDuration(getFadeDuration());
                this.mBackgroundAnimator.start();
                return;
            }
            backgroundAnimatorComplete();
            this.mIsBackgroundReady = true;
        }
    }

    private Transition getExitTransition() {
        Transition transition = null;
        if (this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
            Transition transitionConfigureTransition = configureTransition(getViewsTransition(), true);
            removeExcludedViews(transitionConfigureTransition, this.mTransitioningViews);
            if (!this.mTransitioningViews.isEmpty()) {
                transition = transitionConfigureTransition;
            }
        }
        if (transition == null) {
            viewsTransitionComplete();
            return transition;
        }
        final ArrayList<View> arrayList = this.mTransitioningViews;
        transition.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener() { // from class: android.app.ExitTransitionCoordinator.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition2) {
                ArrayList<View> arrayList2;
                ExitTransitionCoordinator.this.viewsTransitionComplete();
                if (ExitTransitionCoordinator.this.mIsHidden && (arrayList2 = arrayList) != null) {
                    ExitTransitionCoordinator.this.showViews(arrayList2, true);
                    ExitTransitionCoordinator.this.setTransitioningViewsVisiblity(0, true);
                }
                if (ExitTransitionCoordinator.this.mSharedElementBundle != null) {
                    ExitTransitionCoordinator.this.delayCancel();
                }
                super.onTransitionEnd(transition2);
            }
        });
        return transition;
    }

    private Transition getSharedElementExitTransition() {
        Transition transitionConfigureTransition = !this.mSharedElements.isEmpty() ? configureTransition(getSharedElementTransition(), false) : null;
        if (transitionConfigureTransition == null) {
            sharedElementTransitionComplete();
            return transitionConfigureTransition;
        }
        transitionConfigureTransition.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener() { // from class: android.app.ExitTransitionCoordinator.7
            @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition) {
                ExitTransitionCoordinator.this.sharedElementTransitionComplete();
                if (ExitTransitionCoordinator.this.mIsHidden) {
                    ExitTransitionCoordinator exitTransitionCoordinator = ExitTransitionCoordinator.this;
                    exitTransitionCoordinator.showViews(exitTransitionCoordinator.mSharedElements, true);
                }
                super.onTransitionEnd(transition);
            }
        });
        this.mSharedElements.get(0).invalidate();
        return transitionConfigureTransition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginTransitions() {
        Transition sharedElementExitTransition = getSharedElementExitTransition();
        Transition exitTransition = getExitTransition();
        Transition transitionMergeTransitions = mergeTransitions(sharedElementExitTransition, exitTransition);
        ViewGroup decor = getDecor();
        if (transitionMergeTransitions != null && decor != null) {
            lambda$scheduleGhostVisibilityChange$1(4);
            scheduleGhostVisibilityChange(4);
            if (exitTransition != null) {
                setTransitioningViewsVisiblity(0, false);
            }
            TransitionManager.beginDelayedTransition(decor, transitionMergeTransitions);
            scheduleGhostVisibilityChange(0);
            lambda$scheduleGhostVisibilityChange$1(0);
            if (exitTransition != null) {
                setTransitioningViewsVisiblity(4, false);
            }
            decor.invalidate();
            return;
        }
        transitionStarted();
    }

    protected boolean isReadyToNotify() {
        return (this.mSharedElementBundle == null || this.mResultReceiver == null || !this.mIsBackgroundReady) ? false : true;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void sharedElementTransitionComplete() {
        this.mSharedElementBundle = this.mExitSharedElementBundle == null ? captureSharedElementState() : captureExitSharedElementsState();
        super.sharedElementTransitionComplete();
    }

    private Bundle captureExitSharedElementsState() {
        ExitTransitionCoordinator exitTransitionCoordinator;
        Bundle bundle = new Bundle();
        RectF rectF = new RectF();
        Matrix matrix = new Matrix();
        int i = 0;
        while (i < this.mSharedElements.size()) {
            String str = this.mSharedElementNames.get(i);
            Bundle bundle2 = this.mExitSharedElementBundle.getBundle(str);
            if (bundle2 != null) {
                bundle.putBundle(str, bundle2);
                exitTransitionCoordinator = this;
            } else {
                exitTransitionCoordinator = this;
                exitTransitionCoordinator.captureSharedElementState(this.mSharedElements.get(i), str, bundle, matrix, rectF);
            }
            i++;
            this = exitTransitionCoordinator;
        }
        return bundle;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void onTransitionsComplete() {
        notifyComplete();
    }

    protected void notifyComplete() {
        if (isReadyToNotify()) {
            if (!this.mSharedElementNotified) {
                this.mSharedElementNotified = true;
                delayCancel();
                ExitTransitionCallbacks exitTransitionCallbacks = this.mExitCallbacks;
                if (exitTransitionCallbacks != null && exitTransitionCallbacks.isReturnTransitionAllowed()) {
                    this.mResultReceiver.send(108, null);
                }
                if (this.mListener == null) {
                    this.mResultReceiver.send(103, this.mSharedElementBundle);
                    notifyExitComplete();
                    return;
                } else {
                    final ResultReceiver resultReceiver = this.mResultReceiver;
                    final Bundle bundle = this.mSharedElementBundle;
                    this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, new SharedElementCallback.OnSharedElementsReadyListener() { // from class: android.app.ExitTransitionCoordinator.8
                        @Override // android.app.SharedElementCallback.OnSharedElementsReadyListener
                        public void onSharedElementsReady() {
                            resultReceiver.send(103, bundle);
                            ExitTransitionCoordinator.this.notifyExitComplete();
                        }
                    });
                    return;
                }
            }
            notifyExitComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyExitComplete() {
        if (this.mExitNotified || !isViewsTransitionComplete()) {
            return;
        }
        this.mExitNotified = true;
        this.mResultReceiver.send(104, null);
        this.mResultReceiver = null;
        ViewGroup decor = getDecor();
        if (!this.mIsReturning && decor != null) {
            decor.suppressLayout(false);
        }
        finishIfNecessary();
    }

    private void finishIfNecessary() {
        if (this.mIsReturning && this.mExitNotified && this.mExitCallbacks != null) {
            if (this.mSharedElements.isEmpty() || this.mSharedElementsHidden) {
                finish();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        stopCancel();
        ExitTransitionCallbacks exitTransitionCallbacks = this.mExitCallbacks;
        if (exitTransitionCallbacks != null) {
            exitTransitionCallbacks.onFinish();
            this.mExitCallbacks = null;
        }
        clearState();
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void clearState() {
        this.mHandler = null;
        this.mSharedElementBundle = null;
        ObjectAnimator objectAnimator = this.mBackgroundAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        this.mExitSharedElementBundle = null;
        super.clearState();
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected boolean moveSharedElementWithParent() {
        return !this.mIsReturning;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected Transition getViewsTransition() {
        if (this.mIsReturning) {
            return getWindow().getReturnTransition();
        }
        return getWindow().getExitTransition();
    }

    protected Transition getSharedElementTransition() {
        if (this.mIsReturning) {
            return getWindow().getSharedElementReturnTransition();
        }
        return getWindow().getSharedElementExitTransition();
    }

    public static class ActivityExitTransitionCallbacks implements ExitTransitionCallbacks {
        final Activity mActivity;

        @Override // android.app.ExitTransitionCoordinator.ExitTransitionCallbacks
        public boolean isReturnTransitionAllowed() {
            return true;
        }

        ActivityExitTransitionCallbacks(Activity activity) {
            this.mActivity = activity;
        }

        @Override // android.app.ExitTransitionCoordinator.ExitTransitionCallbacks
        public void onFinish() {
            this.mActivity.mActivityTransitionState.clear();
            this.mActivity.finish();
            this.mActivity.overridePendingTransition(0, 0);
        }
    }
}
