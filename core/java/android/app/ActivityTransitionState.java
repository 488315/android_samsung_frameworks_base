package android.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.android.internal.view.OneShotPreDrawListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

class ActivityTransitionState {
    private static final String EXITING_MAPPED_FROM = "android:exitingMappedFrom";
    private static final String EXITING_MAPPED_TO = "android:exitingMappedTo";
    private static final String PENDING_EXIT_SHARED_ELEMENTS = "android:pendingExitSharedElements";
    private ExitTransitionCoordinator mCalledExitCoordinator;
    private ActivityOptions.SceneTransitionInfo mEnterSceneTransitionInfo;
    private EnterTransitionCoordinator mEnterTransitionCoordinator;
    private SparseArray<WeakReference<ExitTransitionCoordinator>> mExitTransitionCoordinators;
    private int mExitTransitionCoordinatorsKey = 1;
    private ArrayList<String> mExitingFrom;
    private ArrayList<String> mExitingTo;
    private ArrayList<View> mExitingToView;
    private boolean mHasExited;
    private boolean mIsEnterPostponed;
    private boolean mIsEnterTriggered;
    private ArrayList<String> mPendingExitNames;
    private ExitTransitionCoordinator mReturnExitCoordinator;

    public int addExitTransitionCoordinator(ExitTransitionCoordinator exitTransitionCoordinator) {
        if (this.mExitTransitionCoordinators == null) {
            this.mExitTransitionCoordinators = new SparseArray<>();
        }
        WeakReference<ExitTransitionCoordinator> ref =
                new WeakReference<>(exitTransitionCoordinator);
        for (int i = this.mExitTransitionCoordinators.size() - 1; i >= 0; i--) {
            WeakReference<ExitTransitionCoordinator> oldRef =
                    this.mExitTransitionCoordinators.valueAt(i);
            if (oldRef.refersTo(null)) {
                this.mExitTransitionCoordinators.removeAt(i);
            }
        }
        int i2 = this.mExitTransitionCoordinatorsKey;
        this.mExitTransitionCoordinatorsKey = i2 + 1;
        this.mExitTransitionCoordinators.append(i2, ref);
        return i2;
    }

    public void readState(Bundle bundle) {
        if (bundle != null) {
            if (this.mEnterTransitionCoordinator == null
                    || this.mEnterTransitionCoordinator.isReturning()) {
                this.mPendingExitNames = bundle.getStringArrayList(PENDING_EXIT_SHARED_ELEMENTS);
            }
            if (this.mEnterTransitionCoordinator == null) {
                this.mExitingFrom = bundle.getStringArrayList(EXITING_MAPPED_FROM);
                this.mExitingTo = bundle.getStringArrayList(EXITING_MAPPED_TO);
            }
        }
    }

    public ArrayList<String> getPendingExitNames() {
        if (this.mPendingExitNames == null
                && this.mEnterTransitionCoordinator != null
                && !this.mEnterTransitionCoordinator.isReturning()) {
            this.mPendingExitNames =
                    this.mEnterTransitionCoordinator.getPendingExitSharedElementNames();
        }
        return this.mPendingExitNames;
    }

    public void saveState(Bundle bundle) {
        ArrayList<String> pendingExitNames = getPendingExitNames();
        if (pendingExitNames != null) {
            bundle.putStringArrayList(PENDING_EXIT_SHARED_ELEMENTS, pendingExitNames);
        }
        if (this.mExitingFrom != null) {
            bundle.putStringArrayList(EXITING_MAPPED_FROM, this.mExitingFrom);
            bundle.putStringArrayList(EXITING_MAPPED_TO, this.mExitingTo);
        }
    }

    public void setEnterSceneTransitionInfo(
            Activity activity, ActivityOptions.SceneTransitionInfo info) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView();
        if (window.hasFeature(13)
                && info != null
                && this.mEnterSceneTransitionInfo == null
                && this.mEnterTransitionCoordinator == null) {
            this.mEnterSceneTransitionInfo = info;
            this.mIsEnterTriggered = false;
            if (this.mEnterSceneTransitionInfo.isReturning()) {
                restoreExitedViews();
                int result = this.mEnterSceneTransitionInfo.getResultCode();
                if (result != 0) {
                    Intent intent = this.mEnterSceneTransitionInfo.getResultData();
                    if (intent != null) {
                        intent.setExtrasClassLoader(activity.getClassLoader());
                    }
                    activity.onActivityReenter(result, intent);
                }
            }
        }
    }

    public void enterReady(Activity activity) {
        if (this.mEnterSceneTransitionInfo == null || this.mIsEnterTriggered) {
            return;
        }
        this.mIsEnterTriggered = true;
        this.mHasExited = false;
        ArrayList<String> sharedElementNames =
                this.mEnterSceneTransitionInfo.getSharedElementNames();
        ResultReceiver resultReceiver = this.mEnterSceneTransitionInfo.getResultReceiver();
        boolean isReturning = this.mEnterSceneTransitionInfo.isReturning();
        if (isReturning) {
            restoreExitedViews();
            activity.getWindow().getDecorView().setVisibility(0);
        }
        getPendingExitNames();
        this.mEnterTransitionCoordinator =
                new EnterTransitionCoordinator(
                        activity,
                        resultReceiver,
                        sharedElementNames,
                        this.mEnterSceneTransitionInfo.isReturning(),
                        this.mEnterSceneTransitionInfo.isCrossTask());
        if (this.mEnterSceneTransitionInfo.isCrossTask() && sharedElementNames != null) {
            this.mExitingFrom = new ArrayList<>(sharedElementNames);
            this.mExitingTo = new ArrayList<>(sharedElementNames);
        }
        if (!this.mIsEnterPostponed) {
            startEnter();
        }
    }

    public void postponeEnterTransition() {
        this.mIsEnterPostponed = true;
    }

    public void startPostponedEnterTransition() {
        if (this.mIsEnterPostponed) {
            this.mIsEnterPostponed = false;
            if (this.mEnterTransitionCoordinator != null) {
                startEnter();
            }
        }
    }

    private void startEnter() {
        if (this.mEnterTransitionCoordinator.isReturning()) {
            if (this.mExitingToView != null) {
                this.mEnterTransitionCoordinator.viewInstancesReady(
                        this.mExitingFrom, this.mExitingTo, this.mExitingToView);
            } else {
                this.mEnterTransitionCoordinator.namedViewsReady(
                        this.mExitingFrom, this.mExitingTo);
            }
        } else {
            this.mEnterTransitionCoordinator.namedViewsReady(null, null);
            this.mPendingExitNames = null;
        }
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
        this.mEnterSceneTransitionInfo = null;
    }

    public void onStop(Activity activity) {
        restoreExitedViews();
        if (this.mEnterTransitionCoordinator != null) {
            getPendingExitNames();
            this.mEnterTransitionCoordinator.stop();
            this.mEnterTransitionCoordinator = null;
        }
        if (this.mReturnExitCoordinator != null) {
            this.mReturnExitCoordinator.stop(activity);
            this.mReturnExitCoordinator = null;
        }
    }

    public void onResume(Activity activity) {
        if (this.mEnterTransitionCoordinator == null || activity.isTopOfTask()) {
            restoreExitedViews();
            restoreReenteringViews();
        } else {
            activity.mHandler.postDelayed(new AnonymousClass1(), 1000L);
        }
    }

    /* renamed from: android.app.ActivityTransitionState$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {}

        @Override // java.lang.Runnable
        public void run() {
            if (ActivityTransitionState.this.mEnterTransitionCoordinator == null
                    || ActivityTransitionState.this.mEnterTransitionCoordinator
                            .isWaitingForRemoteExit()) {
                ActivityTransitionState.this.restoreExitedViews();
                ActivityTransitionState.this.restoreReenteringViews();
            } else if (ActivityTransitionState.this.mEnterTransitionCoordinator.isReturning()) {
                ActivityTransitionState.this.mEnterTransitionCoordinator
                        .runAfterTransitionsComplete(
                                new Runnable() { // from class:
                                    // android.app.ActivityTransitionState$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ActivityTransitionState.AnonymousClass1.this.lambda$run$0();
                                    }
                                });
            }
        }

        public /* synthetic */ void lambda$run$0() {
            ActivityTransitionState.this.getPendingExitNames();
            ActivityTransitionState.this.mEnterTransitionCoordinator = null;
        }
    }

    public void clear() {
        this.mPendingExitNames = null;
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
        this.mCalledExitCoordinator = null;
        this.mEnterTransitionCoordinator = null;
        this.mEnterSceneTransitionInfo = null;
        this.mExitTransitionCoordinators = null;
    }

    public void restoreExitedViews() {
        if (this.mCalledExitCoordinator != null) {
            this.mCalledExitCoordinator.resetViews();
            this.mCalledExitCoordinator = null;
        }
    }

    public void restoreReenteringViews() {
        if (this.mEnterTransitionCoordinator != null
                && this.mEnterTransitionCoordinator.isReturning()
                && !this.mEnterTransitionCoordinator.isCrossTask()) {
            this.mEnterTransitionCoordinator.forceViewsToAppear();
            this.mExitingFrom = null;
            this.mExitingTo = null;
            this.mExitingToView = null;
        }
    }

    public boolean startExitBackTransition(final Activity activity) {
        Transition enterViewsTransition;
        ViewGroup decor;
        boolean delayExitBack;
        ArrayList<String> pendingExitNames = getPendingExitNames();
        if (pendingExitNames == null || this.mCalledExitCoordinator != null) {
            return false;
        }
        if (!this.mHasExited) {
            this.mHasExited = true;
            if (this.mEnterTransitionCoordinator == null) {
                enterViewsTransition = null;
                decor = null;
                delayExitBack = false;
            } else {
                Transition enterViewsTransition2 =
                        this.mEnterTransitionCoordinator.getEnterViewsTransition();
                ViewGroup decor2 = this.mEnterTransitionCoordinator.getDecor();
                boolean delayExitBack2 = this.mEnterTransitionCoordinator.cancelEnter();
                this.mEnterTransitionCoordinator = null;
                if (enterViewsTransition2 != null && decor2 != null) {
                    enterViewsTransition2.pause(decor2);
                }
                enterViewsTransition = enterViewsTransition2;
                decor = decor2;
                delayExitBack = delayExitBack2;
            }
            this.mReturnExitCoordinator =
                    new ExitTransitionCoordinator(
                            new ExitTransitionCoordinator.ActivityExitTransitionCallbacks(activity),
                            activity.getWindow(),
                            activity.mEnterTransitionListener,
                            pendingExitNames,
                            null,
                            null,
                            true);
            if (enterViewsTransition != null && decor != null) {
                enterViewsTransition.resume(decor);
            }
            if (delayExitBack && decor != null) {
                OneShotPreDrawListener.add(
                        decor,
                        new Runnable() { // from class:
                            // android.app.ActivityTransitionState$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActivityTransitionState.this.lambda$startExitBackTransition$0(
                                        activity);
                            }
                        });
            } else {
                this.mReturnExitCoordinator.startExit(activity);
            }
        }
        return true;
    }

    public /* synthetic */ void lambda$startExitBackTransition$0(Activity activity) {
        if (this.mReturnExitCoordinator != null) {
            this.mReturnExitCoordinator.startExit(activity);
        }
    }

    public boolean isTransitionRunning() {
        if (this.mEnterTransitionCoordinator != null
                && this.mEnterTransitionCoordinator.isTransitionRunning()) {
            return true;
        }
        if (this.mCalledExitCoordinator == null
                || !this.mCalledExitCoordinator.isTransitionRunning()) {
            return this.mReturnExitCoordinator != null
                    && this.mReturnExitCoordinator.isTransitionRunning();
        }
        return true;
    }

    public void startExitOutTransition(Activity activity, Bundle options) {
        getPendingExitNames();
        this.mEnterTransitionCoordinator = null;
        if (!activity.getWindow().hasFeature(13) || this.mExitTransitionCoordinators == null) {
            return;
        }
        ActivityOptions activityOptions = new ActivityOptions(options);
        ActivityOptions.SceneTransitionInfo info = activityOptions.getSceneTransitionInfo();
        if (info != null) {
            int key = info.getExitCoordinatorKey();
            int index = this.mExitTransitionCoordinators.indexOfKey(key);
            if (index >= 0) {
                this.mCalledExitCoordinator = this.mExitTransitionCoordinators.valueAt(index).get();
                this.mExitTransitionCoordinators.removeAt(index);
                if (this.mCalledExitCoordinator != null) {
                    this.mExitingFrom = this.mCalledExitCoordinator.getAcceptedNames();
                    this.mExitingTo = this.mCalledExitCoordinator.getMappedNames();
                    this.mExitingToView = this.mCalledExitCoordinator.copyMappedViews();
                    this.mCalledExitCoordinator.startExit();
                }
            }
        }
    }
}
