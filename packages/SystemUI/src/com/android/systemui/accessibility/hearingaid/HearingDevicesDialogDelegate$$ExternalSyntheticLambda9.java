package com.android.systemui.accessibility.hearingaid;

import com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController;

/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda9 implements HearingDevicesInputRoutingController.InputRoutingControlAvailableCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HearingDevicesDialogDelegate f$0;

    public /* synthetic */ HearingDevicesDialogDelegate$$ExternalSyntheticLambda9(HearingDevicesDialogDelegate hearingDevicesDialogDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = hearingDevicesDialogDelegate;
    }

    @Override // com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController.InputRoutingControlAvailableCallback
    public final void onResult(final boolean z) {
        switch (this.$r8$classId) {
            case 0:
                final HearingDevicesDialogDelegate hearingDevicesDialogDelegate = this.f$0;
                final int i = 1;
                hearingDevicesDialogDelegate.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                hearingDevicesDialogDelegate.mInputRoutingLayout.setVisibility(z ? 0 : 8);
                                break;
                            default:
                                hearingDevicesDialogDelegate.mInputRoutingLayout.setVisibility(z ? 0 : 8);
                                break;
                        }
                    }
                });
                break;
            default:
                final HearingDevicesDialogDelegate hearingDevicesDialogDelegate2 = this.f$0;
                final int i2 = 0;
                hearingDevicesDialogDelegate2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                hearingDevicesDialogDelegate2.mInputRoutingLayout.setVisibility(z ? 0 : 8);
                                break;
                            default:
                                hearingDevicesDialogDelegate2.mInputRoutingLayout.setVisibility(z ? 0 : 8);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
