package android.app;

import android.app.ActivityOptions;
import android.app.ActivityTransitionState;
import android.app.ExitTransitionCoordinator;
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

/* loaded from: classes.dex */
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
        WeakReference<ExitTransitionCoordinator> weakReference = new WeakReference<>(exitTransitionCoordinator);
        for (int size = this.mExitTransitionCoordinators.size() - 1; size >= 0; size--) {
            if (this.mExitTransitionCoordinators.valueAt(size).refersTo(null)) {
                this.mExitTransitionCoordinators.removeAt(size);
            }
        }
        int i = this.mExitTransitionCoordinatorsKey;
        this.mExitTransitionCoordinatorsKey = i + 1;
        this.mExitTransitionCoordinators.append(i, weakReference);
        return i;
    }

    public void readState(Bundle bundle) {
        if (bundle != null) {
            EnterTransitionCoordinator enterTransitionCoordinator = this.mEnterTransitionCoordinator;
            if (enterTransitionCoordinator == null || enterTransitionCoordinator.isReturning()) {
                this.mPendingExitNames = bundle.getStringArrayList(PENDING_EXIT_SHARED_ELEMENTS);
            }
            if (this.mEnterTransitionCoordinator == null) {
                this.mExitingFrom = bundle.getStringArrayList(EXITING_MAPPED_FROM);
                this.mExitingTo = bundle.getStringArrayList(EXITING_MAPPED_TO);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<String> getPendingExitNames() {
        EnterTransitionCoordinator enterTransitionCoordinator;
        if (this.mPendingExitNames == null && (enterTransitionCoordinator = this.mEnterTransitionCoordinator) != null && !enterTransitionCoordinator.isReturning()) {
            this.mPendingExitNames = this.mEnterTransitionCoordinator.getPendingExitSharedElementNames();
        }
        return this.mPendingExitNames;
    }

    public void saveState(Bundle bundle) {
        ArrayList<String> pendingExitNames = getPendingExitNames();
        if (pendingExitNames != null) {
            bundle.putStringArrayList(PENDING_EXIT_SHARED_ELEMENTS, pendingExitNames);
        }
        ArrayList<String> arrayList = this.mExitingFrom;
        if (arrayList != null) {
            bundle.putStringArrayList(EXITING_MAPPED_FROM, arrayList);
            bundle.putStringArrayList(EXITING_MAPPED_TO, this.mExitingTo);
        }
    }

    public void setEnterSceneTransitionInfo(Activity activity, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView();
        if (window.hasFeature(13) && sceneTransitionInfo != null && this.mEnterSceneTransitionInfo == null && this.mEnterTransitionCoordinator == null) {
            this.mEnterSceneTransitionInfo = sceneTransitionInfo;
            this.mIsEnterTriggered = false;
            if (sceneTransitionInfo.isReturning()) {
                restoreExitedViews();
                int resultCode = this.mEnterSceneTransitionInfo.getResultCode();
                if (resultCode != 0) {
                    Intent resultData = this.mEnterSceneTransitionInfo.getResultData();
                    if (resultData != null) {
                        resultData.setExtrasClassLoader(activity.getClassLoader());
                    }
                    activity.onActivityReenter(resultCode, resultData);
                }
            }
        }
    }

    public void enterReady(Activity activity) {
        ActivityOptions.SceneTransitionInfo sceneTransitionInfo = this.mEnterSceneTransitionInfo;
        if (sceneTransitionInfo == null || this.mIsEnterTriggered) {
            return;
        }
        this.mIsEnterTriggered = true;
        this.mHasExited = false;
        ArrayList<String> sharedElementNames = sceneTransitionInfo.getSharedElementNames();
        ResultReceiver resultReceiver = this.mEnterSceneTransitionInfo.getResultReceiver();
        if (this.mEnterSceneTransitionInfo.isReturning()) {
            restoreExitedViews();
            activity.getWindow().getDecorView().setVisibility(0);
        }
        getPendingExitNames();
        this.mEnterTransitionCoordinator = new EnterTransitionCoordinator(activity, resultReceiver, sharedElementNames, this.mEnterSceneTransitionInfo.isReturning(), this.mEnterSceneTransitionInfo.isCrossTask());
        if (this.mEnterSceneTransitionInfo.isCrossTask() && sharedElementNames != null) {
            this.mExitingFrom = new ArrayList<>(sharedElementNames);
            this.mExitingTo = new ArrayList<>(sharedElementNames);
        }
        if (this.mIsEnterPostponed) {
            return;
        }
        startEnter();
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
            ArrayList<View> arrayList = this.mExitingToView;
            if (arrayList != null) {
                this.mEnterTransitionCoordinator.viewInstancesReady(this.mExitingFrom, this.mExitingTo, arrayList);
            } else {
                this.mEnterTransitionCoordinator.namedViewsReady(this.mExitingFrom, this.mExitingTo);
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
        ExitTransitionCoordinator exitTransitionCoordinator = this.mReturnExitCoordinator;
        if (exitTransitionCoordinator != null) {
            exitTransitionCoordinator.stop(activity);
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
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ActivityTransitionState.this.mEnterTransitionCoordinator == null || ActivityTransitionState.this.mEnterTransitionCoordinator.isWaitingForRemoteExit()) {
                ActivityTransitionState.this.restoreExitedViews();
                ActivityTransitionState.this.restoreReenteringViews();
            } else if (ActivityTransitionState.this.mEnterTransitionCoordinator.isReturning()) {
                ActivityTransitionState.this.mEnterTransitionCoordinator.runAfterTransitionsComplete(new Runnable() { // from class: android.app.ActivityTransitionState$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityTransitionState.AnonymousClass1.this.lambda$run$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
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

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreExitedViews() {
        ExitTransitionCoordinator exitTransitionCoordinator = this.mCalledExitCoordinator;
        if (exitTransitionCoordinator != null) {
            exitTransitionCoordinator.resetViews();
            this.mCalledExitCoordinator = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreReenteringViews() {
        EnterTransitionCoordinator enterTransitionCoordinator = this.mEnterTransitionCoordinator;
        if (enterTransitionCoordinator == null || !enterTransitionCoordinator.isReturning() || this.mEnterTransitionCoordinator.isCrossTask()) {
            return;
        }
        this.mEnterTransitionCoordinator.forceViewsToAppear();
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
    }

    public boolean startExitBackTransition(final Activity activity) {
        boolean z;
        Transition transition;
        ViewGroup viewGroup;
        ArrayList<String> pendingExitNames = getPendingExitNames();
        if (pendingExitNames == null || this.mCalledExitCoordinator != null) {
            return false;
        }
        if (!this.mHasExited) {
            this.mHasExited = true;
            EnterTransitionCoordinator enterTransitionCoordinator = this.mEnterTransitionCoordinator;
            if (enterTransitionCoordinator != null) {
                Transition enterViewsTransition = enterTransitionCoordinator.getEnterViewsTransition();
                ViewGroup decor = this.mEnterTransitionCoordinator.getDecor();
                boolean cancelEnter = this.mEnterTransitionCoordinator.cancelEnter();
                this.mEnterTransitionCoordinator = null;
                if (enterViewsTransition != null && decor != null) {
                    enterViewsTransition.pause(decor);
                }
                transition = enterViewsTransition;
                viewGroup = decor;
                z = cancelEnter;
            } else {
                z = false;
                transition = null;
                viewGroup = null;
            }
            this.mReturnExitCoordinator = new ExitTransitionCoordinator(new ExitTransitionCoordinator.ActivityExitTransitionCallbacks(activity), activity.getWindow(), activity.mEnterTransitionListener, pendingExitNames, null, null, true);
            if (transition != null && viewGroup != null) {
                transition.resume(viewGroup);
            }
            if (z && viewGroup != null) {
                OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: android.app.ActivityTransitionState$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityTransitionState.this.lambda$startExitBackTransition$0(activity);
                    }
                });
            } else {
                this.mReturnExitCoordinator.startExit(activity);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startExitBackTransition$0(Activity activity) {
        ExitTransitionCoordinator exitTransitionCoordinator = this.mReturnExitCoordinator;
        if (exitTransitionCoordinator != null) {
            exitTransitionCoordinator.startExit(activity);
        }
    }

    public boolean isTransitionRunning() {
        EnterTransitionCoordinator enterTransitionCoordinator = this.mEnterTransitionCoordinator;
        if (enterTransitionCoordinator != null && enterTransitionCoordinator.isTransitionRunning()) {
            return true;
        }
        ExitTransitionCoordinator exitTransitionCoordinator = this.mCalledExitCoordinator;
        if (exitTransitionCoordinator != null && exitTransitionCoordinator.isTransitionRunning()) {
            return true;
        }
        ExitTransitionCoordinator exitTransitionCoordinator2 = this.mReturnExitCoordinator;
        return exitTransitionCoordinator2 != null && exitTransitionCoordinator2.isTransitionRunning();
    }

    public void startExitOutTransition(Activity activity, Bundle bundle) {
        ActivityOptions.SceneTransitionInfo sceneTransitionInfo;
        getPendingExitNames();
        this.mEnterTransitionCoordinator = null;
        if (!activity.getWindow().hasFeature(13) || this.mExitTransitionCoordinators == null || (sceneTransitionInfo = new ActivityOptions(bundle).getSceneTransitionInfo()) == null) {
            return;
        }
        int indexOfKey = this.mExitTransitionCoordinators.indexOfKey(sceneTransitionInfo.getExitCoordinatorKey());
        if (indexOfKey >= 0) {
            this.mCalledExitCoordinator = this.mExitTransitionCoordinators.valueAt(indexOfKey).get();
            this.mExitTransitionCoordinators.removeAt(indexOfKey);
            ExitTransitionCoordinator exitTransitionCoordinator = this.mCalledExitCoordinator;
            if (exitTransitionCoordinator != null) {
                this.mExitingFrom = exitTransitionCoordinator.getAcceptedNames();
                this.mExitingTo = this.mCalledExitCoordinator.getMappedNames();
                this.mExitingToView = this.mCalledExitCoordinator.copyMappedViews();
                this.mCalledExitCoordinator.startExit();
            }
        }
    }
}
