package com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel;

import com.android.systemui.common.shared.model.Color;
import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DeviceIconViewModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class IsNotPlaying implements DeviceIconViewModel {
        public final Color backgroundColor;
        public final Icon icon;
        public final Color iconColor;

        public IsNotPlaying(Icon icon, Color color, Color color2) {
            this.icon = icon;
            this.iconColor = color;
            this.backgroundColor = color2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IsNotPlaying)) {
                return false;
            }
            IsNotPlaying isNotPlaying = (IsNotPlaying) obj;
            return Intrinsics.areEqual(this.icon, isNotPlaying.icon) && Intrinsics.areEqual(this.iconColor, isNotPlaying.iconColor) && Intrinsics.areEqual(this.backgroundColor, isNotPlaying.backgroundColor);
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel
        public final Color getBackgroundColor() {
            return this.backgroundColor;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel
        public final Icon getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel
        public final Color getIconColor() {
            return this.iconColor;
        }

        public final int hashCode() {
            return this.backgroundColor.hashCode() + ((this.iconColor.hashCode() + (this.icon.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "IsNotPlaying(icon=" + this.icon + ", iconColor=" + this.iconColor + ", backgroundColor=" + this.backgroundColor + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class IsPlaying implements DeviceIconViewModel {
        public final Color backgroundColor;
        public final Icon icon;
        public final Color iconColor;

        public IsPlaying(Icon icon, Color color, Color color2) {
            this.icon = icon;
            this.iconColor = color;
            this.backgroundColor = color2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IsPlaying)) {
                return false;
            }
            IsPlaying isPlaying = (IsPlaying) obj;
            return Intrinsics.areEqual(this.icon, isPlaying.icon) && Intrinsics.areEqual(this.iconColor, isPlaying.iconColor) && Intrinsics.areEqual(this.backgroundColor, isPlaying.backgroundColor);
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel
        public final Color getBackgroundColor() {
            return this.backgroundColor;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel
        public final Icon getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel
        public final Color getIconColor() {
            return this.iconColor;
        }

        public final int hashCode() {
            return this.backgroundColor.hashCode() + ((this.iconColor.hashCode() + (this.icon.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "IsPlaying(icon=" + this.icon + ", iconColor=" + this.iconColor + ", backgroundColor=" + this.backgroundColor + ")";
        }
    }

    Color getBackgroundColor();

    Icon getIcon();

    Color getIconColor();
}
