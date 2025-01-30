package com.samsung.android.desktopsystemui.sharedlib.system;

import android.annotation.NonNull;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.util.AnnotationValidations;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.recents.model.ThumbnailData;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RemoteTransitionCompat implements Parcelable {
    public static final Parcelable.Creator<RemoteTransitionCompat> CREATOR = new Parcelable.Creator<RemoteTransitionCompat>() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteTransitionCompat.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransitionCompat createFromParcel(Parcel parcel) {
            return new RemoteTransitionCompat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransitionCompat[] newArray(int i) {
            return new RemoteTransitionCompat[i];
        }
    };
    private static final String TAG = "RemoteTransitionCompat";
    TransitionFilter mFilter;
    final RemoteTransition mTransition;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.desktopsystemui.sharedlib.system.RemoteTransitionCompat$1 */
    public class C45761 extends IRemoteTransition.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ RemoteTransitionRunner val$runner;

        public C45761(Executor executor, RemoteTransitionRunner remoteTransitionRunner) {
            this.val$executor = executor;
            this.val$runner = remoteTransitionRunner;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mergeAnimation$2(IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            try {
                iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
            } catch (RemoteException e) {
                Log.e(RemoteTransitionCompat.TAG, "Failed to call transition finished callback", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$startAnimation$0(IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            try {
                iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
            } catch (RemoteException e) {
                Log.e(RemoteTransitionCompat.TAG, "Failed to call transition finished callback", e);
            }
        }

        public void mergeAnimation(final IBinder iBinder, final TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            final RemoteTransitionCompat$1$$ExternalSyntheticLambda0 remoteTransitionCompat$1$$ExternalSyntheticLambda0 = new RemoteTransitionCompat$1$$ExternalSyntheticLambda0(iRemoteTransitionFinishedCallback, 1);
            Executor executor = this.val$executor;
            final RemoteTransitionRunner remoteTransitionRunner = this.val$runner;
            executor.execute(new Runnable() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteTransitionCompat$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteTransitionRunner.this.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, remoteTransitionCompat$1$$ExternalSyntheticLambda0);
                }
            });
        }

        public void startAnimation(final IBinder iBinder, final TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            final RemoteTransitionCompat$1$$ExternalSyntheticLambda0 remoteTransitionCompat$1$$ExternalSyntheticLambda0 = new RemoteTransitionCompat$1$$ExternalSyntheticLambda0(iRemoteTransitionFinishedCallback, 0);
            Executor executor = this.val$executor;
            final RemoteTransitionRunner remoteTransitionRunner = this.val$runner;
            executor.execute(new Runnable() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteTransitionCompat$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteTransitionRunner.this.startAnimation(iBinder, transitionInfo, transaction, remoteTransitionCompat$1$$ExternalSyntheticLambda0);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        private long mBuilderFieldsSet = 0;
        private TransitionFilter mFilter;
        private RemoteTransition mTransition;

        public Builder(RemoteTransition remoteTransition) {
            this.mTransition = remoteTransition;
            AnnotationValidations.validate(NonNull.class, (NonNull) null, remoteTransition);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }

        public RemoteTransitionCompat build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 4;
            this.mBuilderFieldsSet = j;
            if ((j & 2) == 0) {
                this.mFilter = null;
            }
            return new RemoteTransitionCompat(this.mTransition, this.mFilter);
        }

        public Builder setFilter(TransitionFilter transitionFilter) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mFilter = transitionFilter;
            return this;
        }

        public Builder setTransition(RemoteTransition remoteTransition) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mTransition = remoteTransition;
            return this;
        }
    }

    public RemoteTransitionCompat(RemoteTransition remoteTransition) {
        this.mFilter = null;
        this.mTransition = remoteTransition;
    }

    public void addHomeOpenCheck(ComponentName componentName) {
        if (this.mFilter == null) {
            this.mFilter = new TransitionFilter();
        }
        TransitionFilter transitionFilter = this.mFilter;
        transitionFilter.mNotFlags = 256;
        transitionFilter.mRequirements = new TransitionFilter.Requirement[]{new TransitionFilter.Requirement(), new TransitionFilter.Requirement()};
        TransitionFilter.Requirement[] requirementArr = this.mFilter.mRequirements;
        TransitionFilter.Requirement requirement = requirementArr[0];
        requirement.mActivityType = 2;
        requirement.mTopActivity = componentName;
        requirement.mModes = new int[]{1, 3};
        requirement.mOrder = 1;
        TransitionFilter.Requirement requirement2 = requirementArr[1];
        requirement2.mActivityType = 1;
        requirement2.mModes = new int[]{2, 4};
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TransitionFilter getFilter() {
        return this.mFilter;
    }

    public RemoteTransition getTransition() {
        return this.mTransition;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mFilter != null ? (byte) 2 : (byte) 0);
        parcel.writeTypedObject(this.mTransition, i);
        TransitionFilter transitionFilter = this.mFilter;
        if (transitionFilter != null) {
            parcel.writeTypedObject(transitionFilter, i);
        }
    }

    public RemoteTransitionCompat(RemoteTransitionRunner remoteTransitionRunner, Executor executor, IApplicationThread iApplicationThread) {
        this.mFilter = null;
        this.mTransition = new RemoteTransition(new C45761(executor, remoteTransitionRunner));
    }

    public RemoteTransitionCompat(final RecentsAnimationListener recentsAnimationListener, final RecentsAnimationControllerCompat recentsAnimationControllerCompat, IApplicationThread iApplicationThread) {
        this.mFilter = null;
        this.mTransition = new RemoteTransition(new IRemoteTransition.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteTransitionCompat.2
            final RecentsControllerWrap mRecentsSession = new RecentsControllerWrap();
            IBinder mToken = null;

            public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
                if (iBinder2.equals(this.mToken) && this.mRecentsSession.merge(transitionInfo, transaction, recentsAnimationListener)) {
                    try {
                        iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
                    } catch (RemoteException e) {
                        Log.e(RemoteTransitionCompat.TAG, "Error merging transition.", e);
                    }
                }
            }

            public void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
                ArrayMap<SurfaceControl, SurfaceControl> arrayMap = new ArrayMap<>();
                RemoteAnimationTargetCompat[] wrap = RemoteAnimationTargetCompat.wrap(transitionInfo, false, transaction, arrayMap);
                RemoteAnimationTargetCompat[] wrap2 = RemoteAnimationTargetCompat.wrap(transitionInfo, true, transaction, arrayMap);
                this.mToken = iBinder;
                ArrayList<WindowContainerToken> arrayList = new ArrayList<>();
                WindowContainerToken windowContainerToken = null;
                for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                    if (change.getMode() == 2 || change.getMode() == 4) {
                        transaction.setLayer(arrayMap.get(change.getLeash()), (transitionInfo.getChanges().size() * 3) - m136m);
                        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                        if (taskInfo != null) {
                            arrayList.add(0, taskInfo.token);
                            PictureInPictureParams pictureInPictureParams = taskInfo.pictureInPictureParams;
                            if (pictureInPictureParams != null && pictureInPictureParams.isAutoEnterEnabled()) {
                                windowContainerToken = taskInfo.token;
                            }
                        }
                    } else if (change.getTaskInfo() != null && change.getTaskInfo().topActivityType == 3) {
                        transaction.setLayer(arrayMap.get(change.getLeash()), (transitionInfo.getChanges().size() * 3) - m136m);
                    }
                }
                for (int length = wrap2.length - 1; length >= 0; length--) {
                    transaction.setAlpha(wrap2[length].leash, 1.0f);
                }
                transaction.apply();
                this.mRecentsSession.setup(recentsAnimationControllerCompat, transitionInfo, iRemoteTransitionFinishedCallback, arrayList, windowContainerToken, arrayMap, this.mToken);
                recentsAnimationListener.onAnimationStart(this.mRecentsSession, wrap, wrap2, new Rect(0, 0, 0, 0), new Rect());
            }
        });
    }

    public RemoteTransitionCompat(RemoteTransition remoteTransition, TransitionFilter transitionFilter) {
        this.mFilter = null;
        this.mTransition = remoteTransition;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, remoteTransition);
        this.mFilter = transitionFilter;
    }

    public RemoteTransitionCompat(Parcel parcel) {
        this.mFilter = null;
        byte readByte = parcel.readByte();
        RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
        TransitionFilter transitionFilter = (readByte & 2) == 0 ? null : (TransitionFilter) parcel.readTypedObject(TransitionFilter.CREATOR);
        this.mTransition = remoteTransition;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, remoteTransition);
        this.mFilter = transitionFilter;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class RecentsControllerWrap extends RecentsAnimationControllerCompat {
        private RecentsAnimationControllerCompat mWrapped = null;
        private IRemoteTransitionFinishedCallback mFinishCB = null;
        private ArrayList<WindowContainerToken> mPausingTasks = null;
        private WindowContainerToken mPipTask = null;
        private TransitionInfo mInfo = null;
        private ArrayList<SurfaceControl> mOpeningLeashes = null;
        private ArrayMap<SurfaceControl, SurfaceControl> mLeashMap = null;
        private PictureInPictureSurfaceTransaction mPipTransaction = null;
        private IBinder mTransition = null;

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void cleanupScreenshot() {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.cleanupScreenshot();
            }
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void detachNavigationBarFromApp(boolean z) {
            try {
                ActivityTaskManager.getService().detachNavigationBarFromApp(this.mTransition);
            } catch (RemoteException e) {
                Log.e(RemoteTransitionCompat.TAG, "Failed to detach the navigation bar from app", e);
            }
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void finish(boolean z, boolean z2) {
            WindowContainerTransaction windowContainerTransaction;
            if (this.mFinishCB == null) {
                Log.e(RemoteTransitionCompat.TAG, "Duplicate call to finish", new RuntimeException());
                return;
            }
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.finish(z, z2);
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            if (z || this.mPausingTasks == null || this.mOpeningLeashes != null) {
                WindowContainerToken windowContainerToken = this.mPipTask;
                if (windowContainerToken != null && this.mPipTransaction != null) {
                    transaction.show(this.mInfo.getChange(windowContainerToken).getLeash());
                    PictureInPictureSurfaceTransaction.apply(this.mPipTransaction, this.mInfo.getChange(this.mPipTask).getLeash(), transaction);
                    this.mPipTask = null;
                    this.mPipTransaction = null;
                }
                windowContainerTransaction = null;
            } else {
                windowContainerTransaction = new WindowContainerTransaction();
                for (int size = this.mPausingTasks.size() - 1; size >= 0; size--) {
                    windowContainerTransaction.reorder(this.mPausingTasks.get(size), true);
                    transaction.show(this.mInfo.getChange(this.mPausingTasks.get(size)).getLeash());
                }
            }
            for (int i = 0; i < this.mLeashMap.size(); i++) {
                if (this.mLeashMap.keyAt(i) != this.mLeashMap.valueAt(i)) {
                    transaction.remove(this.mLeashMap.valueAt(i));
                }
            }
            try {
                this.mFinishCB.onTransitionFinished(windowContainerTransaction, transaction);
            } catch (RemoteException e) {
                Log.e(RemoteTransitionCompat.TAG, "Failed to call animation finish callback", e);
                transaction.apply();
            }
            for (int i2 = 0; i2 < this.mInfo.getChanges().size(); i2++) {
                ((TransitionInfo.Change) this.mInfo.getChanges().get(i2)).getLeash().release();
            }
            this.mWrapped = null;
            this.mFinishCB = null;
            this.mPausingTasks = null;
            this.mInfo = null;
            this.mOpeningLeashes = null;
            this.mLeashMap = null;
            this.mTransition = null;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void hideCurrentInputMethod() {
            this.mWrapped.hideCurrentInputMethod();
        }

        public boolean merge(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, RecentsAnimationListener recentsAnimationListener) {
            ArrayList arrayList = null;
            for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                if ((change.getMode() == 1 || change.getMode() == 3) && change.getTaskInfo() != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(change);
                }
            }
            if (arrayList == null) {
                return false;
            }
            int i = 0;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (this.mPausingTasks.contains(((TransitionInfo.Change) arrayList.get(i2)).getContainer())) {
                    i++;
                }
            }
            if (i > 0) {
                if (i == this.mPausingTasks.size()) {
                    return true;
                }
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("\"Concelling\" a recents transitions by unpausing ", i, " apps after pausing ");
                m1m.append(this.mPausingTasks.size());
                m1m.append(" apps.");
                throw new IllegalStateException(m1m.toString());
            }
            int size = this.mInfo.getChanges().size() * 3;
            this.mOpeningLeashes = new ArrayList<>();
            RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr = new RemoteAnimationTargetCompat[arrayList.size()];
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                this.mOpeningLeashes.add(((TransitionInfo.Change) arrayList.get(i3)).getLeash());
                RemoteAnimationTargetCompat remoteAnimationTargetCompat = new RemoteAnimationTargetCompat((TransitionInfo.Change) arrayList.get(i3), size, transitionInfo, transaction);
                this.mLeashMap.put(this.mOpeningLeashes.get(i3), remoteAnimationTargetCompat.leash);
                transaction.reparent(remoteAnimationTargetCompat.leash, this.mInfo.getRootLeash());
                transaction.setLayer(remoteAnimationTargetCompat.leash, size);
                remoteAnimationTargetCompatArr[i3] = remoteAnimationTargetCompat;
            }
            transaction.apply();
            recentsAnimationListener.onTasksAppeared(remoteAnimationTargetCompatArr);
            return true;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public boolean removeTask(int i) {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                return recentsAnimationControllerCompat.removeTask(i);
            }
            return false;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public ThumbnailData screenshotTask(int i) {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                return recentsAnimationControllerCompat.screenshotTask(i);
            }
            return null;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void setAnimationTargetsBehindSystemBars(boolean z) {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.setAnimationTargetsBehindSystemBars(z);
            }
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void setDeferCancelUntilNextTransition(boolean z, boolean z2) {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.setDeferCancelUntilNextTransition(z, z2);
            }
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void setFinishTaskTransaction(int i, PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, SurfaceControl surfaceControl) {
            this.mPipTransaction = pictureInPictureSurfaceTransaction;
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.setFinishTaskTransaction(i, pictureInPictureSurfaceTransaction, surfaceControl);
            }
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void setInputConsumerEnabled(boolean z) {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.setInputConsumerEnabled(z);
            }
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void setWillFinishToHome(boolean z) {
            RecentsAnimationControllerCompat recentsAnimationControllerCompat = this.mWrapped;
            if (recentsAnimationControllerCompat != null) {
                recentsAnimationControllerCompat.setWillFinishToHome(z);
            }
        }

        public void setup(RecentsAnimationControllerCompat recentsAnimationControllerCompat, TransitionInfo transitionInfo, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, ArrayList<WindowContainerToken> arrayList, WindowContainerToken windowContainerToken, ArrayMap<SurfaceControl, SurfaceControl> arrayMap, IBinder iBinder) {
            if (this.mInfo != null) {
                throw new IllegalStateException("Trying to run a new recents animation while recents is already active.");
            }
            this.mWrapped = recentsAnimationControllerCompat;
            this.mInfo = transitionInfo;
            this.mFinishCB = iRemoteTransitionFinishedCallback;
            this.mPausingTasks = arrayList;
            this.mPipTask = windowContainerToken;
            this.mLeashMap = arrayMap;
            this.mTransition = iBinder;
        }

        @Override // com.samsung.android.desktopsystemui.sharedlib.system.RecentsAnimationControllerCompat
        public void animateNavigationBarToApp(long j) {
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
