package com.android.server.hdmi;

import java.util.function.Predicate;

public final /* synthetic */ class HdmiCecLocalDevice$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ HdmiCecLocalDevice$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                if (((SetAudioVolumeLevelDiscoveryAction) obj).mTargetAddress == i2) {}
                break;
            default:
                if (((HdmiCecLocalDevice) obj).getDeviceInfo().getLogicalAddress() == i2) {}
                break;
        }
        return false;
    }
}
