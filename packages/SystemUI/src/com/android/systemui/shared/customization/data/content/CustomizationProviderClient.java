package com.android.systemui.shared.customization.data.content;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface CustomizationProviderClient {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Selection {
        public final String affordanceId;
        public final String affordanceName;
        public final String slotId;

        public Selection(String str, String str2, String str3) {
            this.slotId = str;
            this.affordanceId = str2;
            this.affordanceName = str3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Selection)) {
                return false;
            }
            Selection selection = (Selection) obj;
            return Intrinsics.areEqual(this.slotId, selection.slotId) && Intrinsics.areEqual(this.affordanceId, selection.affordanceId) && Intrinsics.areEqual(this.affordanceName, selection.affordanceName);
        }

        public final int hashCode() {
            return this.affordanceName.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.slotId.hashCode() * 31, 31, this.affordanceId);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Selection(slotId=");
            sb.append(this.slotId);
            sb.append(", affordanceId=");
            sb.append(this.affordanceId);
            sb.append(", affordanceName=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.affordanceName, ")");
        }
    }
}
