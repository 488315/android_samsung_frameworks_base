package com.android.systemui.bluetooth;

import android.content.Intent;
import com.android.systemui.R;
import com.android.systemui.bluetooth.BroadcastDialogDelegate;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastDialogDelegate$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BroadcastDialogDelegate.AnonymousClass1 f$0;

    public /* synthetic */ BroadcastDialogDelegate$1$$ExternalSyntheticLambda0(BroadcastDialogDelegate.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BroadcastDialogDelegate.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                BroadcastDialogDelegate broadcastDialogDelegate = anonymousClass1.this$0;
                if (broadcastDialogDelegate.mShouldLaunchLeBroadcastDialog) {
                    Intent putExtra = new Intent().setPackage(broadcastDialogDelegate.mContext.getPackageName()).setAction("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG").putExtra("package_name", broadcastDialogDelegate.mOutputPackageName);
                    BroadcastSender broadcastSender = broadcastDialogDelegate.mBroadcastSender;
                    broadcastSender.getClass();
                    broadcastSender.sendInBackground(String.valueOf(putExtra), new BroadcastSender$$ExternalSyntheticLambda0(broadcastSender, putExtra, 1));
                    broadcastDialogDelegate.mDialogs.forEach(new BroadcastDialogDelegate$$ExternalSyntheticLambda3());
                    broadcastDialogDelegate.mShouldLaunchLeBroadcastDialog = false;
                    break;
                }
                break;
            case 1:
                anonymousClass1.this$0.handleLeBroadcastStopped();
                break;
            case 2:
                BroadcastDialogDelegate broadcastDialogDelegate2 = anonymousClass1.this$0;
                boolean z = BroadcastDialogDelegate.DEBUG;
                broadcastDialogDelegate2.mSwitchBroadcast.setText(R.string.media_output_broadcast_start_failed);
                broadcastDialogDelegate2.mSwitchBroadcast.setEnabled(false);
                broadcastDialogDelegate2.refreshSwitchBroadcastButton();
                break;
            case 3:
                anonymousClass1.this$0.mShouldLaunchLeBroadcastDialog = true;
                break;
            default:
                BroadcastDialogDelegate broadcastDialogDelegate3 = anonymousClass1.this$0;
                boolean z2 = BroadcastDialogDelegate.DEBUG;
                broadcastDialogDelegate3.mSwitchBroadcast.setText(R.string.media_output_broadcast_start_failed);
                broadcastDialogDelegate3.mSwitchBroadcast.setEnabled(false);
                broadcastDialogDelegate3.refreshSwitchBroadcastButton();
                break;
        }
    }
}
