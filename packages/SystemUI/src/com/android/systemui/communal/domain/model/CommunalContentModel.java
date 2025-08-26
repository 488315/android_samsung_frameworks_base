package com.android.systemui.communal.domain.model;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.UUID;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface CommunalContentModel {

    public final class CtaTileInViewMode implements CommunalContentModel {
        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return "cta_tile_in_view_mode";
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return CommunalContentSize.FixedSize.HALF;
        }
    }

    public final class KEY {
        public static final Companion Companion = new Companion(null);

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    public interface Ongoing extends CommunalContentModel {
        long getCreatedTimestampMillis();

        default CommunalContentSize getMinSize() {
            return CommunalContentSize.FixedSize.THIRD;
        }

        void setSize(CommunalContentSize communalContentSize);
    }

    public final class Spacer implements CommunalContentModel {
        public final String key;
        public final CommunalContentSize size;

        public Spacer(CommunalContentSize communalContentSize) {
            this.size = communalContentSize;
            KEY.Companion.getClass();
            this.key = "spacer_" + UUID.randomUUID();
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Spacer) && Intrinsics.areEqual(this.size, ((Spacer) obj).size);
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
            return this.size.hashCode();
        }

        public final String toString() {
            return "Spacer(size=" + this.size + ")";
        }
    }

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

    public final class WidgetPlaceholder implements CommunalContentModel {
        public final String key;

        public WidgetPlaceholder() {
            KEY.Companion.getClass();
            this.key = "widget_placeholder_" + UUID.randomUUID();
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return CommunalContentSize.FixedSize.HALF;
        }
    }

    String getKey();

    CommunalContentSize getSize();

    public final class Umo implements Ongoing {
        public final long createdTimestampMillis;
        public final String key;
        public final CommunalContentSize minSize;
        public CommunalContentSize size;

        public Umo(long j, CommunalContentSize communalContentSize, CommunalContentSize communalContentSize2) {
            this.createdTimestampMillis = j;
            this.size = communalContentSize;
            this.minSize = communalContentSize2;
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

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel.Ongoing
        public final CommunalContentSize getMinSize() {
            return this.minSize;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel
        public final CommunalContentSize getSize() {
            return this.size;
        }

        @Override // com.android.systemui.communal.domain.model.CommunalContentModel.Ongoing
        public final void setSize(CommunalContentSize communalContentSize) {
            this.size = communalContentSize;
        }

        public Umo(long j, CommunalContentSize communalContentSize, CommunalContentSize communalContentSize2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, (i & 2) != 0 ? CommunalContentSize.FixedSize.HALF : communalContentSize, (i & 4) != 0 ? CommunalContentSize.FixedSize.HALF : communalContentSize2);
        }
    }

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
            this.key = "smartspace_" + str;
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

        public Smartspace(String str, RemoteViews remoteViews, long j, CommunalContentSize communalContentSize, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, remoteViews, j, (i & 8) != 0 ? CommunalContentSize.FixedSize.HALF : communalContentSize);
        }
    }

    public interface WidgetContent extends CommunalContentModel {

        public final class DisabledWidget implements WidgetContent {
            public final int appWidgetId;
            public final ComponentName componentName;
            public final String key;
            public final AppWidgetProviderInfo providerInfo;
            public final int rank;
            public final CommunalContentSize size;

            public DisabledWidget(int i, int i2, AppWidgetProviderInfo appWidgetProviderInfo, CommunalContentSize communalContentSize) {
                this.appWidgetId = i;
                this.rank = i2;
                this.providerInfo = appWidgetProviderInfo;
                this.size = communalContentSize;
                KEY.Companion.getClass();
                this.key = "disabled_widget_" + i;
                this.componentName = appWidgetProviderInfo.provider;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof DisabledWidget)) {
                    return false;
                }
                DisabledWidget disabledWidget = (DisabledWidget) obj;
                return this.appWidgetId == disabledWidget.appWidgetId && this.rank == disabledWidget.rank && Intrinsics.areEqual(this.providerInfo, disabledWidget.providerInfo) && Intrinsics.areEqual(this.size, disabledWidget.size);
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getAppWidgetId() {
                return this.appWidgetId;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final ComponentName getComponentName() {
                return this.componentName;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getRank() {
                return this.rank;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final CommunalContentSize getSize() {
                return this.size;
            }

            public final int hashCode() {
                return this.size.hashCode() + ((this.providerInfo.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, Integer.hashCode(this.appWidgetId) * 31, 31)) * 31);
            }

            public final String toString() {
                return "DisabledWidget(appWidgetId=" + this.appWidgetId + ", rank=" + this.rank + ", providerInfo=" + this.providerInfo + ", size=" + this.size + ")";
            }
        }

        public final class Widget implements WidgetContent {
            public final int appWidgetId;
            public final ComponentName componentName;
            public final boolean inQuietMode;
            public final String key;
            public final AppWidgetProviderInfo providerInfo;
            public final int rank;
            public final CommunalContentSize size;

            public Widget(int i, int i2, AppWidgetProviderInfo appWidgetProviderInfo, boolean z, CommunalContentSize communalContentSize) {
                this.appWidgetId = i;
                this.rank = i2;
                this.providerInfo = appWidgetProviderInfo;
                this.inQuietMode = z;
                this.size = communalContentSize;
                KEY.Companion.getClass();
                this.key = "widget_" + i;
                this.componentName = appWidgetProviderInfo.provider;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Widget)) {
                    return false;
                }
                Widget widget = (Widget) obj;
                return this.appWidgetId == widget.appWidgetId && this.rank == widget.rank && Intrinsics.areEqual(this.providerInfo, widget.providerInfo) && this.inQuietMode == widget.inQuietMode && Intrinsics.areEqual(this.size, widget.size);
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getAppWidgetId() {
                return this.appWidgetId;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final ComponentName getComponentName() {
                return this.componentName;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getRank() {
                return this.rank;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final CommunalContentSize getSize() {
                return this.size;
            }

            public final int hashCode() {
                return this.size.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((this.providerInfo.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, Integer.hashCode(this.appWidgetId) * 31, 31)) * 31, 31, this.inQuietMode);
            }

            public final String toString() {
                return "Widget(appWidgetId=" + this.appWidgetId + ", rank=" + this.rank + ", providerInfo=" + this.providerInfo + ", inQuietMode=" + this.inQuietMode + ", size=" + this.size + ")";
            }
        }

        int getAppWidgetId();

        ComponentName getComponentName();

        int getRank();

        public final class PendingWidget implements WidgetContent {
            public final int appWidgetId;
            public final ComponentName componentName;
            public final Bitmap icon;
            public final String key;
            public final int rank;
            public final CommunalContentSize size;

            public PendingWidget(int i, int i2, ComponentName componentName, CommunalContentSize communalContentSize, Bitmap bitmap) {
                this.appWidgetId = i;
                this.rank = i2;
                this.componentName = componentName;
                this.size = communalContentSize;
                this.icon = bitmap;
                KEY.Companion.getClass();
                this.key = "pending_widget_" + i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof PendingWidget)) {
                    return false;
                }
                PendingWidget pendingWidget = (PendingWidget) obj;
                return this.appWidgetId == pendingWidget.appWidgetId && this.rank == pendingWidget.rank && Intrinsics.areEqual(this.componentName, pendingWidget.componentName) && Intrinsics.areEqual(this.size, pendingWidget.size) && Intrinsics.areEqual(this.icon, pendingWidget.icon);
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getAppWidgetId() {
                return this.appWidgetId;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final ComponentName getComponentName() {
                return this.componentName;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
            public final int getRank() {
                return this.rank;
            }

            @Override // com.android.systemui.communal.domain.model.CommunalContentModel
            public final CommunalContentSize getSize() {
                return this.size;
            }

            public final int hashCode() {
                int iHashCode = (this.size.hashCode() + ((this.componentName.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, Integer.hashCode(this.appWidgetId) * 31, 31)) * 31)) * 31;
                Bitmap bitmap = this.icon;
                return iHashCode + (bitmap == null ? 0 : bitmap.hashCode());
            }

            public final String toString() {
                return "PendingWidget(appWidgetId=" + this.appWidgetId + ", rank=" + this.rank + ", componentName=" + this.componentName + ", size=" + this.size + ", icon=" + this.icon + ")";
            }

            public /* synthetic */ PendingWidget(int i, int i2, ComponentName componentName, CommunalContentSize communalContentSize, Bitmap bitmap, int i3, DefaultConstructorMarker defaultConstructorMarker) {
                this(i, i2, componentName, communalContentSize, (i3 & 16) != 0 ? null : bitmap);
            }
        }
    }
}
