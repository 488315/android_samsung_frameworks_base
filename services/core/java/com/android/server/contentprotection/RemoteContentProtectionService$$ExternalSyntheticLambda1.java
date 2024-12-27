package com.android.server.contentprotection;

import android.content.pm.ParceledListSlice;
import android.service.contentcapture.IContentProtectionAllowlistCallback;
import android.service.contentcapture.IContentProtectionService;

import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RemoteContentProtectionService$$ExternalSyntheticLambda1
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RemoteContentProtectionService$$ExternalSyntheticLambda1(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runNoResult(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int i2 = RemoteContentProtectionService.$r8$clinit;
                ((IContentProtectionService) obj)
                        .onUpdateAllowlistRequest(
                                ((IContentProtectionAllowlistCallback) obj2).asBinder());
                break;
            default:
                int i3 = RemoteContentProtectionService.$r8$clinit;
                ((IContentProtectionService) obj).onLoginDetected((ParceledListSlice) obj2);
                break;
        }
    }
}
