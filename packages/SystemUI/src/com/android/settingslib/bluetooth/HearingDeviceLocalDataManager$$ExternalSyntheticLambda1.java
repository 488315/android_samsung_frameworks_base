package com.android.settingslib.bluetooth;

import com.android.settingslib.bluetooth.HearingDeviceLocalDataManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class HearingDeviceLocalDataManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HearingDeviceLocalDataManager f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ HearingDeviceLocalDataManager.Data f$2;

    public /* synthetic */ HearingDeviceLocalDataManager$$ExternalSyntheticLambda1(HearingDeviceLocalDataManager hearingDeviceLocalDataManager, String str, HearingDeviceLocalDataManager.Data data, int i) {
        this.$r8$classId = i;
        this.f$0 = hearingDeviceLocalDataManager;
        this.f$1 = str;
        this.f$2 = data;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                HearingDeviceLocalDataManager hearingDeviceLocalDataManager = this.f$0;
                hearingDeviceLocalDataManager.mListener.onDeviceLocalDataChange(this.f$1, this.f$2);
                break;
            default:
                HearingDeviceLocalDataManager hearingDeviceLocalDataManager2 = this.f$0;
                hearingDeviceLocalDataManager2.mListener.onDeviceLocalDataChange(this.f$1, this.f$2);
                break;
        }
    }
}
