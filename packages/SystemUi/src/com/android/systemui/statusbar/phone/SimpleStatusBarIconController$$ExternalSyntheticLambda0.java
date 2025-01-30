package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.SimpleStatusBarIconController;
import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SimpleStatusBarIconController$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        SimpleStatusBarIconController.TimeOrderKey timeOrderKey = (SimpleStatusBarIconController.TimeOrderKey) obj;
        SimpleStatusBarIconController.TimeOrderKey timeOrderKey2 = (SimpleStatusBarIconController.TimeOrderKey) obj2;
        boolean z = timeOrderKey.isCallChipNotif;
        long j = timeOrderKey.when;
        if (!z || j != 0) {
            boolean z2 = timeOrderKey2.isCallChipNotif;
            long j2 = timeOrderKey2.when;
            if ((z2 && j2 == 0) || j < j2) {
                return 1;
            }
            if (j <= j2) {
                return 0;
            }
        }
        return -1;
    }
}
