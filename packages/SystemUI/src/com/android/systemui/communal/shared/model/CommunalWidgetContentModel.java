package com.android.systemui.communal.shared.model;

import android.appwidget.AppWidgetProviderInfo;
import android.graphics.Bitmap;
import android.os.UserHandle;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface CommunalWidgetContentModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Available implements CommunalWidgetContentModel {
        public final int appWidgetId;
        public final int priority;
        public final AppWidgetProviderInfo providerInfo;

        public Available(int i, AppWidgetProviderInfo appWidgetProviderInfo, int i2) {
            this.appWidgetId = i;
            this.providerInfo = appWidgetProviderInfo;
            this.priority = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Available)) {
                return false;
            }
            Available available = (Available) obj;
            return this.appWidgetId == available.appWidgetId && Intrinsics.areEqual(this.providerInfo, available.providerInfo) && this.priority == available.priority;
        }

        @Override // com.android.systemui.communal.shared.model.CommunalWidgetContentModel
        public final int getAppWidgetId() {
            return this.appWidgetId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.priority) + ((this.providerInfo.hashCode() + (Integer.hashCode(this.appWidgetId) * 31)) * 31);
        }

        public final String toString() {
            AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
            StringBuilder sb = new StringBuilder("Available(appWidgetId=");
            sb.append(this.appWidgetId);
            sb.append(", providerInfo=");
            sb.append(appWidgetProviderInfo);
            sb.append(", priority=");
            return Anchor$$ExternalSyntheticOutline0.m(this.priority, ")", sb);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Pending implements CommunalWidgetContentModel {
        public final int appWidgetId;
        public final Bitmap icon;
        public final String packageName;
        public final int priority;
        public final UserHandle user;

        public Pending(int i, int i2, String str, Bitmap bitmap, UserHandle userHandle) {
            this.appWidgetId = i;
            this.priority = i2;
            this.packageName = str;
            this.icon = bitmap;
            this.user = userHandle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Pending)) {
                return false;
            }
            Pending pending = (Pending) obj;
            return this.appWidgetId == pending.appWidgetId && this.priority == pending.priority && Intrinsics.areEqual(this.packageName, pending.packageName) && Intrinsics.areEqual(this.icon, pending.icon) && Intrinsics.areEqual(this.user, pending.user);
        }

        @Override // com.android.systemui.communal.shared.model.CommunalWidgetContentModel
        public final int getAppWidgetId() {
            return this.appWidgetId;
        }

        public final int hashCode() {
            int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.priority, Integer.hashCode(this.appWidgetId) * 31, 31), 31, this.packageName);
            Bitmap bitmap = this.icon;
            return this.user.hashCode() + ((m + (bitmap == null ? 0 : bitmap.hashCode())) * 31);
        }

        public final String toString() {
            return "Pending(appWidgetId=" + this.appWidgetId + ", priority=" + this.priority + ", packageName=" + this.packageName + ", icon=" + this.icon + ", user=" + this.user + ")";
        }
    }

    int getAppWidgetId();
}
