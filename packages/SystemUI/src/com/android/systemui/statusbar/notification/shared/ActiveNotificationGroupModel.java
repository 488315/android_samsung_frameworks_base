package com.android.systemui.statusbar.notification.shared;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActiveNotificationGroupModel extends ActiveNotificationEntryModel {
    public final List children;
    public final String key;
    public final ActiveNotificationModel summary;

    public ActiveNotificationGroupModel(String str, ActiveNotificationModel activeNotificationModel, List<ActiveNotificationModel> list) {
        super(null);
        this.key = str;
        this.summary = activeNotificationModel;
        this.children = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationGroupModel)) {
            return false;
        }
        ActiveNotificationGroupModel activeNotificationGroupModel = (ActiveNotificationGroupModel) obj;
        return Intrinsics.areEqual(this.key, activeNotificationGroupModel.key) && Intrinsics.areEqual(this.summary, activeNotificationGroupModel.summary) && Intrinsics.areEqual(this.children, activeNotificationGroupModel.children);
    }

    public final int hashCode() {
        return this.children.hashCode() + ((this.summary.hashCode() + (this.key.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "ActiveNotificationGroupModel(key=" + this.key + ", summary=" + this.summary + ", children=" + this.children + ")";
    }
}
