package com.android.systemui.communal.domain.model;

import android.appwidget.AppWidgetProviderInfo;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import java.util.UUID;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface CommunalContentModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CtaTileInViewMode implements CommunalContentModel {
        public final String key = "cta_tile_in_view_mode";
        public final CommunalContentSize size = CommunalContentSize.HALF;

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return this.size;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class KEY {
        public static final Companion Companion = new Companion(null);

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Ongoing extends CommunalContentModel {
        long getCreatedTimestampMillis();

        void setSize(CommunalContentSize communalContentSize);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Tutorial implements CommunalContentModel {
        public final String key;
        public final CommunalContentSize size;

        public Tutorial(int i, CommunalContentSize communalContentSize) {
            this.size = communalContentSize;
            KEY.Companion.getClass();
            this.key = "tutorial_" + i;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return this.size;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class WidgetPlaceholder implements CommunalContentModel {
        public final String key;
        public final CommunalContentSize size;

        public WidgetPlaceholder() {
            KEY.Companion.getClass();
            this.key = "widget_placeholder_" + UUID.randomUUID();
            this.size = CommunalContentSize.HALF;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return this.size;
        }
    }

    String getKey();

    CommunalContentSize getSize();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Umo implements Ongoing {
        public final long createdTimestampMillis;
        public final String key;
        public CommunalContentSize size;

        public Umo(long j, CommunalContentSize communalContentSize) {
            this.createdTimestampMillis = j;
            this.size = communalContentSize;
            KEY.Companion.getClass();
            this.key = "umo";
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel.Ongoing
        public final long getCreatedTimestampMillis() {
            return this.createdTimestampMillis;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return this.size;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel.Ongoing
        public final void setSize(CommunalContentSize communalContentSize) {
            this.size = communalContentSize;
        }

        public /* synthetic */ Umo(long j, CommunalContentSize communalContentSize, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, (i & 2) != 0 ? CommunalContentSize.HALF : communalContentSize);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Smartspace implements Ongoing {
        public final long createdTimestampMillis;
        public final String key;
        public final RemoteViews remoteViews;
        public CommunalContentSize size;

        public Smartspace(String str, RemoteViews remoteViews, long j, CommunalContentSize communalContentSize) {
            this.remoteViews = remoteViews;
            this.createdTimestampMillis = j;
            this.size = communalContentSize;
            KEY.Companion.getClass();
            this.key = "smartspace_".concat(str);
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel.Ongoing
        public final long getCreatedTimestampMillis() {
            return this.createdTimestampMillis;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return this.size;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel.Ongoing
        public final void setSize(CommunalContentSize communalContentSize) {
            this.size = communalContentSize;
        }

        public /* synthetic */ Smartspace(String str, RemoteViews remoteViews, long j, CommunalContentSize communalContentSize, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, remoteViews, j, (i & 8) != 0 ? CommunalContentSize.HALF : communalContentSize);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface WidgetContent extends CommunalContentModel {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class DisabledWidget implements WidgetContent {
            public final int appWidgetId;
            public final String key;
            public final AppWidgetProviderInfo providerInfo;
            public final CommunalContentSize size;

            public DisabledWidget(int i, AppWidgetProviderInfo appWidgetProviderInfo) {
                this.appWidgetId = i;
                this.providerInfo = appWidgetProviderInfo;
                KEY.Companion.getClass();
                this.key = "disabled_widget_" + i;
                this.size = CommunalContentSize.HALF;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof DisabledWidget)) {
                    return false;
                }
                DisabledWidget disabledWidget = (DisabledWidget) obj;
                return this.appWidgetId == disabledWidget.appWidgetId && Intrinsics.areEqual(this.providerInfo, disabledWidget.providerInfo);
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getAppWidgetId() {
                return this.appWidgetId;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final CommunalContentSize getSize() {
                return this.size;
            }

            public final int hashCode() {
                return this.providerInfo.hashCode() + (Integer.hashCode(this.appWidgetId) * 31);
            }

            public final String toString() {
                return "DisabledWidget(appWidgetId=" + this.appWidgetId + ", providerInfo=" + this.providerInfo + ")";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Widget implements WidgetContent {
            public final CommunalAppWidgetHost appWidgetHost;
            public final int appWidgetId;
            public final boolean inQuietMode;
            public final String key;
            public final AppWidgetProviderInfo providerInfo;
            public final CommunalContentSize size;

            public Widget(int i, AppWidgetProviderInfo appWidgetProviderInfo, CommunalAppWidgetHost communalAppWidgetHost, boolean z) {
                this.appWidgetId = i;
                this.providerInfo = appWidgetProviderInfo;
                this.appWidgetHost = communalAppWidgetHost;
                this.inQuietMode = z;
                KEY.Companion.getClass();
                this.key = "widget_" + i;
                this.size = CommunalContentSize.HALF;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Widget)) {
                    return false;
                }
                Widget widget = (Widget) obj;
                return this.appWidgetId == widget.appWidgetId && Intrinsics.areEqual(this.providerInfo, widget.providerInfo) && Intrinsics.areEqual(this.appWidgetHost, widget.appWidgetHost) && this.inQuietMode == widget.inQuietMode;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getAppWidgetId() {
                return this.appWidgetId;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final CommunalContentSize getSize() {
                return this.size;
            }

            public final int hashCode() {
                return Boolean.hashCode(this.inQuietMode) + ((this.appWidgetHost.hashCode() + ((this.providerInfo.hashCode() + (Integer.hashCode(this.appWidgetId) * 31)) * 31)) * 31);
            }

            public final String toString() {
                return "Widget(appWidgetId=" + this.appWidgetId + ", providerInfo=" + this.providerInfo + ", appWidgetHost=" + this.appWidgetHost + ", inQuietMode=" + this.inQuietMode + ")";
            }
        }

        int getAppWidgetId();

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class PendingWidget implements WidgetContent {
            public final int appWidgetId;
            public final Bitmap icon;
            public final String key;
            public final String packageName;
            public final CommunalContentSize size;

            public PendingWidget(int i, String str, Bitmap bitmap) {
                this.appWidgetId = i;
                this.packageName = str;
                this.icon = bitmap;
                KEY.Companion.getClass();
                this.key = "pending_widget_" + i;
                this.size = CommunalContentSize.HALF;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof PendingWidget)) {
                    return false;
                }
                PendingWidget pendingWidget = (PendingWidget) obj;
                return this.appWidgetId == pendingWidget.appWidgetId && Intrinsics.areEqual(this.packageName, pendingWidget.packageName) && Intrinsics.areEqual(this.icon, pendingWidget.icon);
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getAppWidgetId() {
                return this.appWidgetId;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final CommunalContentSize getSize() {
                return this.size;
            }

            public final int hashCode() {
                int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.appWidgetId) * 31, 31, this.packageName);
                Bitmap bitmap = this.icon;
                return m + (bitmap == null ? 0 : bitmap.hashCode());
            }

            public final String toString() {
                return "PendingWidget(appWidgetId=" + this.appWidgetId + ", packageName=" + this.packageName + ", icon=" + this.icon + ")";
            }

            public /* synthetic */ PendingWidget(int i, String str, Bitmap bitmap, int i2, DefaultConstructorMarker defaultConstructorMarker) {
                this(i, str, (i2 & 4) != 0 ? null : bitmap);
            }
        }
    }
}
