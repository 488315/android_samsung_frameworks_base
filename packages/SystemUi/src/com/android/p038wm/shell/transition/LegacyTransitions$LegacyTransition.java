package com.android.p038wm.shell.transition;

import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IWindowContainerTransactionCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LegacyTransitions$LegacyTransition {
    public final RemoteAnimationAdapter mAdapter;
    public RemoteAnimationTarget[] mApps;
    public final LegacyTransitions$ILegacyTransition mLegacyTransition;
    public RemoteAnimationTarget[] mNonApps;
    public final SyncCallback mSyncCallback;
    public SurfaceControl.Transaction mTransaction;
    public int mTransit;
    public RemoteAnimationTarget[] mWallpapers;
    public int mSyncId = -1;
    public IRemoteAnimationFinishedCallback mFinishCallback = null;
    public boolean mCancelled = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RemoteAnimationWrapper extends IRemoteAnimationRunner.Stub {
        public /* synthetic */ RemoteAnimationWrapper(LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition, int i) {
            this();
        }

        public final void onAnimationCancelled() {
            LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = LegacyTransitions$LegacyTransition.this;
            legacyTransitions$LegacyTransition.mCancelled = true;
            legacyTransitions$LegacyTransition.mNonApps = null;
            legacyTransitions$LegacyTransition.mWallpapers = null;
            legacyTransitions$LegacyTransition.mApps = null;
            LegacyTransitions$LegacyTransition.m2759$$Nest$mcheckApply(legacyTransitions$LegacyTransition);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = LegacyTransitions$LegacyTransition.this;
            legacyTransitions$LegacyTransition.mTransit = i;
            legacyTransitions$LegacyTransition.mApps = remoteAnimationTargetArr;
            legacyTransitions$LegacyTransition.mWallpapers = remoteAnimationTargetArr2;
            legacyTransitions$LegacyTransition.mNonApps = remoteAnimationTargetArr3;
            legacyTransitions$LegacyTransition.mFinishCallback = iRemoteAnimationFinishedCallback;
            LegacyTransitions$LegacyTransition.m2759$$Nest$mcheckApply(legacyTransitions$LegacyTransition);
        }

        private RemoteAnimationWrapper() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SyncCallback extends IWindowContainerTransactionCallback.Stub {
        public /* synthetic */ SyncCallback(LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition, int i) {
            this();
        }

        public final void onTransactionReady(int i, SurfaceControl.Transaction transaction) {
            LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = LegacyTransitions$LegacyTransition.this;
            legacyTransitions$LegacyTransition.mSyncId = i;
            legacyTransitions$LegacyTransition.mTransaction = transaction;
            LegacyTransitions$LegacyTransition.m2759$$Nest$mcheckApply(legacyTransitions$LegacyTransition);
        }

        private SyncCallback() {
        }
    }

    /* renamed from: -$$Nest$mcheckApply, reason: not valid java name */
    public static void m2759$$Nest$mcheckApply(LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition) {
        if (legacyTransitions$LegacyTransition.mSyncId >= 0) {
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = legacyTransitions$LegacyTransition.mFinishCallback;
            if (iRemoteAnimationFinishedCallback != null || legacyTransitions$LegacyTransition.mCancelled) {
                legacyTransitions$LegacyTransition.mLegacyTransition.onAnimationStart(legacyTransitions$LegacyTransition.mTransit, legacyTransitions$LegacyTransition.mApps, legacyTransitions$LegacyTransition.mWallpapers, legacyTransitions$LegacyTransition.mNonApps, iRemoteAnimationFinishedCallback, legacyTransitions$LegacyTransition.mTransaction);
            }
        }
    }

    public LegacyTransitions$LegacyTransition(int i, LegacyTransitions$ILegacyTransition legacyTransitions$ILegacyTransition) {
        int i2 = 0;
        this.mSyncCallback = new SyncCallback(this, i2);
        this.mAdapter = new RemoteAnimationAdapter(new RemoteAnimationWrapper(this, i2), 0L, 0L);
        this.mLegacyTransition = legacyTransitions$ILegacyTransition;
        this.mTransit = i;
    }
}
