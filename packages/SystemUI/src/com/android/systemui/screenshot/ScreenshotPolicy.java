package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.graphics.Rect;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ScreenshotPolicy {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DisplayContentInfo {
        public final Rect bounds;
        public final ComponentName component;
        public final int taskId;
        public final UserHandle user;

        public DisplayContentInfo(ComponentName componentName, Rect rect, UserHandle userHandle, int i) {
            this.component = componentName;
            this.bounds = rect;
            this.user = userHandle;
            this.taskId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayContentInfo)) {
                return false;
            }
            DisplayContentInfo displayContentInfo = (DisplayContentInfo) obj;
            return Intrinsics.areEqual(this.component, displayContentInfo.component) && Intrinsics.areEqual(this.bounds, displayContentInfo.bounds) && Intrinsics.areEqual(this.user, displayContentInfo.user) && this.taskId == displayContentInfo.taskId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.taskId) + ((this.user.hashCode() + ((this.bounds.hashCode() + (this.component.hashCode() * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "DisplayContentInfo(component=" + this.component + ", bounds=" + this.bounds + ", user=" + this.user + ", taskId=" + this.taskId + ")";
        }
    }
}
