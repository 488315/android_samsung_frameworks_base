package com.android.server.companion.devicepresence;

import android.companion.AssociationInfo;
import android.companion.DevicePresenceEvent;
import android.companion.ICompanionDeviceService;

import com.android.internal.infra.ServiceConnector;
import com.android.server.ServiceThread;

public final /* synthetic */ class CompanionServiceConnector$$ExternalSyntheticLambda2
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CompanionServiceConnector$$ExternalSyntheticLambda2(
            int i, AssociationInfo associationInfo) {
        this.$r8$classId = i;
        this.f$0 = associationInfo;
    }

    public /* synthetic */ CompanionServiceConnector$$ExternalSyntheticLambda2(
            DevicePresenceEvent devicePresenceEvent) {
        this.$r8$classId = 2;
        this.f$0 = devicePresenceEvent;
    }

    public final void runNoResult(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ServiceThread serviceThread = CompanionServiceConnector.sServiceThread;
                ((ICompanionDeviceService) obj).onDeviceAppeared((AssociationInfo) obj2);
                break;
            case 1:
                ServiceThread serviceThread2 = CompanionServiceConnector.sServiceThread;
                ((ICompanionDeviceService) obj).onDeviceDisappeared((AssociationInfo) obj2);
                break;
            default:
                ServiceThread serviceThread3 = CompanionServiceConnector.sServiceThread;
                ((ICompanionDeviceService) obj).onDevicePresenceEvent((DevicePresenceEvent) obj2);
                break;
        }
    }
}
