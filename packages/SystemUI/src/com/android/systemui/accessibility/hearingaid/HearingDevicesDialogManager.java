package com.android.systemui.accessibility.hearingaid;

import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class HearingDevicesDialogManager {
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

    public final void showDialog(final Expandable expandable, final int i) {
        if (this.mDialog != null) {
            Log.d("HearingDevicesDialogManager", "HearingDevicesDialog already showing. Destroy it first.");
            this.mDialog.dismiss();
            this.mDialog = null;
        }
        final CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
                final HearingDevicesDialogManager hearingDevicesDialogManager = this.f$0;
                hearingDevicesDialogManager.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        completer.set(Boolean.valueOf(hearingDevicesDialogManager.mDevicesChecker.isAnyPairedHearingDevice()));
                    }
                });
                return "isAnyPairedHearingDevice check";
            }
        });
        future.delegate.addListener(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DialogTransitionAnimator.Controller controllerDialogTransitionController;
                HearingDevicesDialogManager hearingDevicesDialogManager = this.f$0;
                CallbackToFutureAdapter.SafeFuture safeFuture = future;
                int i2 = i;
                Expandable expandable2 = expandable;
                hearingDevicesDialogManager.getClass();
                try {
                    hearingDevicesDialogManager.mDialog = hearingDevicesDialogManager.mDialogFactory.create(!((Boolean) safeFuture.delegate.get()).booleanValue(), i2).createDialog();
                    if (expandable2 == null || (controllerDialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "hearing_devices_tile"))) == null) {
                        hearingDevicesDialogManager.mDialog.show();
                    } else {
                        hearingDevicesDialogManager.mDialogTransitionAnimator.show(hearingDevicesDialogManager.mDialog, controllerDialogTransitionController, true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("HearingDevicesDialogManager", "Exception occurs while running pairedHearingDeviceCheckTask", e);
                }
            }
        }, this.mMainExecutor);
    }
}
