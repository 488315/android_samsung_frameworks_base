package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemUIDialogFactory {
    public final Context applicationContext;
    public final BroadcastDispatcher broadcastDispatcher;
    public final SystemUIDialogManager dialogManager;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final SysUiState sysUiState;

    public SystemUIDialogFactory(Context context, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator) {
        this.applicationContext = context;
        this.dialogManager = systemUIDialogManager;
        this.sysUiState = sysUiState;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
    }
}
