package com.android.systemui.statusbar.featurepods.popups.shared.model;

import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface HoverBehavior {

    public final class Button implements HoverBehavior {
        public final Icon icon;
        public final Function0 onIconPressed;

        public Button(Icon icon, Function0 function0) {
            this.icon = icon;
            this.onIconPressed = function0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Button)) {
                return false;
            }
            Button button = (Button) obj;
            return Intrinsics.areEqual(this.icon, button.icon) && Intrinsics.areEqual(this.onIconPressed, button.onIconPressed);
        }

        public final int hashCode() {
            return this.onIconPressed.hashCode() + (this.icon.hashCode() * 31);
        }

        public final String toString() {
            return "Button(icon=" + this.icon + ", onIconPressed=" + this.onIconPressed + ")";
        }
    }

    public final class None implements HoverBehavior {
        public static final None INSTANCE = new None();

        private None() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof None);
        }

        public final int hashCode() {
            return 359124757;
        }

        public final String toString() {
            return "None";
        }
    }
}
