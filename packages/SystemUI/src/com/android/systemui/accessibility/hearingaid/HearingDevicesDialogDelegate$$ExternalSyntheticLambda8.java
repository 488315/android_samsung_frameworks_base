package com.android.systemui.accessibility.hearingaid;

import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HearingDevicesDialogDelegate f$0;

    public /* synthetic */ HearingDevicesDialogDelegate$$ExternalSyntheticLambda8(HearingDevicesDialogDelegate hearingDevicesDialogDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = hearingDevicesDialogDelegate;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HearingDevicesDialogDelegate hearingDevicesDialogDelegate = this.f$0;
        switch (i) {
            case 0:
                HearingDevicesListAdapter hearingDevicesListAdapter = hearingDevicesDialogDelegate.mDeviceListAdapter;
                List list = hearingDevicesDialogDelegate.mHearingDeviceItemList;
                hearingDevicesListAdapter.mItemList.clear();
                hearingDevicesListAdapter.mItemList.addAll(list);
                hearingDevicesListAdapter.notifyDataSetChanged();
                break;
            default:
                HearingDevicesListAdapter hearingDevicesListAdapter2 = hearingDevicesDialogDelegate.mDeviceListAdapter;
                List list2 = hearingDevicesDialogDelegate.mHearingDeviceItemList;
                hearingDevicesListAdapter2.mItemList.clear();
                hearingDevicesListAdapter2.mItemList.addAll(list2);
                hearingDevicesListAdapter2.notifyDataSetChanged();
                break;
        }
    }
}
