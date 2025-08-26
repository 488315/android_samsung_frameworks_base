package com.android.systemui.qs.tiles.impl.internet.domain.model;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileIconModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface InternetTileModel {

    public final class Active implements InternetTileModel {
        public final ContentDescription contentDescription;
        public final InternetTileIconModel icon;
        public final Text secondaryLabel;
        public final CharSequence secondaryTitle;
        public final ContentDescription stateDescription;

        public Active() {
            this(null, null, null, null, null, 31, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Active)) {
                return false;
            }
            Active active = (Active) obj;
            return Intrinsics.areEqual(this.secondaryTitle, active.secondaryTitle) && Intrinsics.areEqual(this.secondaryLabel, active.secondaryLabel) && Intrinsics.areEqual(this.icon, active.icon) && Intrinsics.areEqual(this.stateDescription, active.stateDescription) && Intrinsics.areEqual(this.contentDescription, active.contentDescription);
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final InternetTileIconModel getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final Text getSecondaryLabel() {
            return this.secondaryLabel;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final CharSequence getSecondaryTitle() {
            return this.secondaryTitle;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final ContentDescription getStateDescription() {
            return this.stateDescription;
        }

        public final int hashCode() {
            CharSequence charSequence = this.secondaryTitle;
            int iHashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
            Text text = this.secondaryLabel;
            int iHashCode2 = (this.icon.hashCode() + ((iHashCode + (text == null ? 0 : text.hashCode())) * 31)) * 31;
            ContentDescription contentDescription = this.stateDescription;
            int iHashCode3 = (iHashCode2 + (contentDescription == null ? 0 : contentDescription.hashCode())) * 31;
            ContentDescription contentDescription2 = this.contentDescription;
            return iHashCode3 + (contentDescription2 != null ? contentDescription2.hashCode() : 0);
        }

        public final String toString() {
            return "Active(secondaryTitle=" + ((Object) this.secondaryTitle) + ", secondaryLabel=" + this.secondaryLabel + ", icon=" + this.icon + ", stateDescription=" + this.stateDescription + ", contentDescription=" + this.contentDescription + ")";
        }

        public Active(CharSequence charSequence, Text text, InternetTileIconModel internetTileIconModel, ContentDescription contentDescription, ContentDescription contentDescription2) {
            this.secondaryTitle = charSequence;
            this.secondaryLabel = text;
            this.icon = internetTileIconModel;
            this.stateDescription = contentDescription;
            this.contentDescription = contentDescription2;
        }

        public /* synthetic */ Active(CharSequence charSequence, Text text, InternetTileIconModel internetTileIconModel, ContentDescription contentDescription, ContentDescription contentDescription2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : charSequence, (i & 2) != 0 ? null : text, (i & 4) != 0 ? new InternetTileIconModel.Cellular(1) : internetTileIconModel, (i & 8) != 0 ? null : contentDescription, (i & 16) != 0 ? null : contentDescription2);
        }
    }

    public final class Inactive implements InternetTileModel {
        public final ContentDescription contentDescription;
        public final InternetTileIconModel icon;
        public final Text secondaryLabel;
        public final CharSequence secondaryTitle;
        public final ContentDescription stateDescription;

        public Inactive() {
            this(null, null, null, null, null, 31, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Inactive)) {
                return false;
            }
            Inactive inactive = (Inactive) obj;
            return Intrinsics.areEqual(this.secondaryTitle, inactive.secondaryTitle) && Intrinsics.areEqual(this.secondaryLabel, inactive.secondaryLabel) && Intrinsics.areEqual(this.icon, inactive.icon) && Intrinsics.areEqual(this.stateDescription, inactive.stateDescription) && Intrinsics.areEqual(this.contentDescription, inactive.contentDescription);
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final InternetTileIconModel getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final Text getSecondaryLabel() {
            return this.secondaryLabel;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final CharSequence getSecondaryTitle() {
            return this.secondaryTitle;
        }

        @Override // com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel
        public final ContentDescription getStateDescription() {
            return this.stateDescription;
        }

        public final int hashCode() {
            CharSequence charSequence = this.secondaryTitle;
            int iHashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
            Text text = this.secondaryLabel;
            int iHashCode2 = (this.icon.hashCode() + ((iHashCode + (text == null ? 0 : text.hashCode())) * 31)) * 31;
            ContentDescription contentDescription = this.stateDescription;
            int iHashCode3 = (iHashCode2 + (contentDescription == null ? 0 : contentDescription.hashCode())) * 31;
            ContentDescription contentDescription2 = this.contentDescription;
            return iHashCode3 + (contentDescription2 != null ? contentDescription2.hashCode() : 0);
        }

        public final String toString() {
            return "Inactive(secondaryTitle=" + ((Object) this.secondaryTitle) + ", secondaryLabel=" + this.secondaryLabel + ", icon=" + this.icon + ", stateDescription=" + this.stateDescription + ", contentDescription=" + this.contentDescription + ")";
        }

        public Inactive(CharSequence charSequence, Text text, InternetTileIconModel internetTileIconModel, ContentDescription contentDescription, ContentDescription contentDescription2) {
            this.secondaryTitle = charSequence;
            this.secondaryLabel = text;
            this.icon = internetTileIconModel;
            this.stateDescription = contentDescription;
            this.contentDescription = contentDescription2;
        }

        public /* synthetic */ Inactive(CharSequence charSequence, Text text, InternetTileIconModel internetTileIconModel, ContentDescription contentDescription, ContentDescription contentDescription2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : charSequence, (i & 2) != 0 ? null : text, (i & 4) != 0 ? new InternetTileIconModel.Cellular(1) : internetTileIconModel, (i & 8) != 0 ? null : contentDescription, (i & 16) != 0 ? null : contentDescription2);
        }
    }

    ContentDescription getContentDescription();

    InternetTileIconModel getIcon();

    Text getSecondaryLabel();

    CharSequence getSecondaryTitle();

    ContentDescription getStateDescription();
}
