package com.android.systemui.keyboard.shortcut.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ShortcutHelperState {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Active implements ShortcutHelperState {
        public final Integer deviceId;

        public Active() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Active) && Intrinsics.areEqual(this.deviceId, ((Active) obj).deviceId);
        }

        public final int hashCode() {
            Integer num = this.deviceId;
            if (num == null) {
                return 0;
            }
            return num.hashCode();
        }

        public final String toString() {
            return "Active(deviceId=" + this.deviceId + ")";
        }

        public Active(Integer num) {
            this.deviceId = num;
        }

        public /* synthetic */ Active(Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : num);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Inactive implements ShortcutHelperState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Inactive);
        }

        public final int hashCode() {
            return 1595827114;
        }

        public final String toString() {
            return "Inactive";
        }
    }
}
