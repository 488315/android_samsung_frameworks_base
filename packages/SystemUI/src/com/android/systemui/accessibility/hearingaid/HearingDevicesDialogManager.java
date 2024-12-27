package com.android.systemui.accessibility.hearingaid;

import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HearingDevicesDialogManager {
    public final Executor mBackgroundExecutor;
    public final HearingDevicesChecker mDevicesChecker;
    public SystemUIDialog mDialog;
    public final HearingDevicesDialogDelegate.Factory mDialogFactory;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final Executor mMainExecutor;

    public HearingDevicesDialogManager(DialogTransitionAnimator dialogTransitionAnimator, HearingDevicesDialogDelegate.Factory factory, HearingDevicesChecker hearingDevicesChecker, Executor executor, Executor executor2) {
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mDialogFactory = factory;
        this.mDevicesChecker = hearingDevicesChecker;
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = executor2;
    }

    public final void showDialog(final Expandable expandable) {
        if (this.mDialog != null) {
            Log.d("HearingDevicesDialogManager", "HearingDevicesDialog already showing. Destroy it first.");
            this.mDialog.dismiss();
            this.mDialog = null;
        }
        final CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
                final HearingDevicesDialogManager hearingDevicesDialogManager = HearingDevicesDialogManager.this;
                hearingDevicesDialogManager.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        completer.set(Boolean.valueOf(HearingDevicesDialogManager.this.mDevicesChecker.isAnyPairedHearingDevice()));
                    }
                });
                return "isAnyPairedHearingDevice check";
            }
        });
        future.delegate.addListener(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DialogTransitionAnimator.Controller dialogTransitionController;
                HearingDevicesDialogManager hearingDevicesDialogManager = HearingDevicesDialogManager.this;
                ListenableFuture listenableFuture = future;
                Expandable expandable2 = expandable;
                hearingDevicesDialogManager.getClass();
                try {
                    hearingDevicesDialogManager.mDialog = hearingDevicesDialogManager.mDialogFactory.create(!((Boolean) listenableFuture.get()).booleanValue()).createDialog();
                    if (expandable2 == null || (dialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "hearing_devices_tile"))) == null) {
                        hearingDevicesDialogManager.mDialog.show();
                    } else {
                        hearingDevicesDialogManager.mDialogTransitionAnimator.show(hearingDevicesDialogManager.mDialog, dialogTransitionController, true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("HearingDevicesDialogManager", "Exception occurs while running pairedHearingDeviceCheckTask", e);
                }
            }
        }, this.mMainExecutor);
    }
}
