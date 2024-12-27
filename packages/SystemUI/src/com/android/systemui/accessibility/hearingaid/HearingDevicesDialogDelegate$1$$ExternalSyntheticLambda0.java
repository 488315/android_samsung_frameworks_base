package com.android.systemui.accessibility.hearingaid;

import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import java.util.List;

public final /* synthetic */ class HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HearingDevicesDialogDelegate.AnonymousClass1 f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda0(HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass1, List list, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass1;
        this.f$1 = list;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass1 = this.f$0;
                List list = this.f$1;
                int i = this.f$2;
                anonymousClass1.getClass();
                String str = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = anonymousClass1.this$0;
                hearingDevicesDialogDelegate.refreshPresetInfoAdapter(i, list);
                Toast.makeText(hearingDevicesDialogDelegate.mApplicationContext, R.string.hearing_devices_presets_error, 0).show();
                break;
            default:
                HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass12 = this.f$0;
                List list2 = this.f$1;
                int i2 = this.f$2;
                anonymousClass12.getClass();
                String str2 = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                anonymousClass12.this$0.refreshPresetInfoAdapter(i2, list2);
                break;
        }
    }
}
