package com.android.systemui.statusbar.pipeline.shared.ui.model;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.plugins.qs.QSTile;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface InternetTileModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Active implements InternetTileModel {
        public final ContentDescription contentDescription;
        public final QSTile.Icon icon;
        public final Integer iconId;
        public final Text secondaryLabel;
        public final CharSequence secondaryTitle;
        public final ContentDescription stateDescription;

        public Active() {
            this(null, null, null, null, null, null, 63, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Active)) {
                return false;
            }
            Active active = (Active) obj;
            return Intrinsics.areEqual(this.secondaryTitle, active.secondaryTitle) && Intrinsics.areEqual(this.secondaryLabel, active.secondaryLabel) && Intrinsics.areEqual(this.iconId, active.iconId) && Intrinsics.areEqual(this.icon, active.icon) && Intrinsics.areEqual(this.stateDescription, active.stateDescription) && Intrinsics.areEqual(this.contentDescription, active.contentDescription);
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final QSTile.Icon getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final Integer getIconId() {
            return this.iconId;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final Text getSecondaryLabel() {
            return this.secondaryLabel;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final CharSequence getSecondaryTitle() {
            return this.secondaryTitle;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final ContentDescription getStateDescription() {
            return this.stateDescription;
        }

        public final int hashCode() {
            CharSequence charSequence = this.secondaryTitle;
            int hashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
            Text text = this.secondaryLabel;
            int hashCode2 = (hashCode + (text == null ? 0 : text.hashCode())) * 31;
            Integer num = this.iconId;
            int hashCode3 = (hashCode2 + (num == null ? 0 : num.hashCode())) * 31;
            QSTile.Icon icon = this.icon;
            int hashCode4 = (hashCode3 + (icon == null ? 0 : icon.hashCode())) * 31;
            ContentDescription contentDescription = this.stateDescription;
            int hashCode5 = (hashCode4 + (contentDescription == null ? 0 : contentDescription.hashCode())) * 31;
            ContentDescription contentDescription2 = this.contentDescription;
            return hashCode5 + (contentDescription2 != null ? contentDescription2.hashCode() : 0);
        }

        public final String toString() {
            return "Active(secondaryTitle=" + ((Object) this.secondaryTitle) + ", secondaryLabel=" + this.secondaryLabel + ", iconId=" + this.iconId + ", icon=" + this.icon + ", stateDescription=" + this.stateDescription + ", contentDescription=" + this.contentDescription + ")";
        }

        public Active(CharSequence charSequence, Text text, Integer num, QSTile.Icon icon, ContentDescription contentDescription, ContentDescription contentDescription2) {
            this.secondaryTitle = charSequence;
            this.secondaryLabel = text;
            this.iconId = num;
            this.icon = icon;
            this.stateDescription = contentDescription;
            this.contentDescription = contentDescription2;
        }

        public /* synthetic */ Active(CharSequence charSequence, Text text, Integer num, QSTile.Icon icon, ContentDescription contentDescription, ContentDescription contentDescription2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : charSequence, (i & 2) != 0 ? null : text, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : icon, (i & 16) != 0 ? null : contentDescription, (i & 32) != 0 ? null : contentDescription2);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Inactive implements InternetTileModel {
        public final ContentDescription contentDescription;
        public final QSTile.Icon icon;
        public final Integer iconId;
        public final Text secondaryLabel;
        public final CharSequence secondaryTitle;
        public final ContentDescription stateDescription;

        public Inactive() {
            this(null, null, null, null, null, null, 63, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Inactive)) {
                return false;
            }
            Inactive inactive = (Inactive) obj;
            return Intrinsics.areEqual(this.secondaryTitle, inactive.secondaryTitle) && Intrinsics.areEqual(this.secondaryLabel, inactive.secondaryLabel) && Intrinsics.areEqual(this.iconId, inactive.iconId) && Intrinsics.areEqual(this.icon, inactive.icon) && Intrinsics.areEqual(this.stateDescription, inactive.stateDescription) && Intrinsics.areEqual(this.contentDescription, inactive.contentDescription);
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final QSTile.Icon getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final Integer getIconId() {
            return this.iconId;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final Text getSecondaryLabel() {
            return this.secondaryLabel;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final CharSequence getSecondaryTitle() {
            return this.secondaryTitle;
        }

        @Override // com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel
        public final ContentDescription getStateDescription() {
            return this.stateDescription;
        }

        public final int hashCode() {
            CharSequence charSequence = this.secondaryTitle;
            int hashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
            Text text = this.secondaryLabel;
            int hashCode2 = (hashCode + (text == null ? 0 : text.hashCode())) * 31;
            Integer num = this.iconId;
            int hashCode3 = (hashCode2 + (num == null ? 0 : num.hashCode())) * 31;
            QSTile.Icon icon = this.icon;
            int hashCode4 = (hashCode3 + (icon == null ? 0 : icon.hashCode())) * 31;
            ContentDescription contentDescription = this.stateDescription;
            int hashCode5 = (hashCode4 + (contentDescription == null ? 0 : contentDescription.hashCode())) * 31;
            ContentDescription contentDescription2 = this.contentDescription;
            return hashCode5 + (contentDescription2 != null ? contentDescription2.hashCode() : 0);
        }

        public final String toString() {
            return "Inactive(secondaryTitle=" + ((Object) this.secondaryTitle) + ", secondaryLabel=" + this.secondaryLabel + ", iconId=" + this.iconId + ", icon=" + this.icon + ", stateDescription=" + this.stateDescription + ", contentDescription=" + this.contentDescription + ")";
        }

        public Inactive(CharSequence charSequence, Text text, Integer num, QSTile.Icon icon, ContentDescription contentDescription, ContentDescription contentDescription2) {
            this.secondaryTitle = charSequence;
            this.secondaryLabel = text;
            this.iconId = num;
            this.icon = icon;
            this.stateDescription = contentDescription;
            this.contentDescription = contentDescription2;
        }

        public /* synthetic */ Inactive(CharSequence charSequence, Text text, Integer num, QSTile.Icon icon, ContentDescription contentDescription, ContentDescription contentDescription2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : charSequence, (i & 2) != 0 ? null : text, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : icon, (i & 16) != 0 ? null : contentDescription, (i & 32) != 0 ? null : contentDescription2);
        }
    }

    ContentDescription getContentDescription();

    QSTile.Icon getIcon();

    Integer getIconId();

    Text getSecondaryLabel();

    CharSequence getSecondaryTitle();

    ContentDescription getStateDescription();
}
