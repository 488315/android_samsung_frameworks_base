package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationIconAreaController$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotificationIconAreaController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((NotificationEntry) obj).mIcons.mStatusBarIcon;
            case 1:
                return ((NotificationEntry) obj).mIcons.mAodIcon;
            default:
                return ((NotificationEntry) obj).mIcons.mShelfIcon;
        }
    }
}
