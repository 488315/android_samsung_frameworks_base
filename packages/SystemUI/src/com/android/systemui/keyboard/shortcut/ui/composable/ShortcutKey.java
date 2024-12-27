package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.vector.ImageVector;
import kotlin.jvm.internal.Intrinsics;

public interface ShortcutKey {

    public final class Icon implements ShortcutKey {
        public final ImageVector value;

        public Icon(ImageVector imageVector) {
            this.value = imageVector;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Icon) && Intrinsics.areEqual(this.value, ((Icon) obj).value);
        }

        public final int hashCode() {
            return this.value.hashCode();
        }

        public final String toString() {
            return "Icon(value=" + this.value + ")";
        }
    }

    public final class Text implements ShortcutKey {
        public final String value;

        public Text(String str) {
            this.value = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Text) && Intrinsics.areEqual(this.value, ((Text) obj).value);
        }

        public final int hashCode() {
            return this.value.hashCode();
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Text(value="), this.value, ")");
        }
    }
}
