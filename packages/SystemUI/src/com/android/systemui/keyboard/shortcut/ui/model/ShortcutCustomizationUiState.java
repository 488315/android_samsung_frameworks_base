package com.android.systemui.keyboard.shortcut.ui.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ShortcutCustomizationUiState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeleteShortcutDialog implements ShortcutCustomizationUiState {
        public static final DeleteShortcutDialog INSTANCE = new DeleteShortcutDialog();

        private DeleteShortcutDialog() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DeleteShortcutDialog);
        }

        public final int hashCode() {
            return -1302184232;
        }

        public final String toString() {
            return "DeleteShortcutDialog";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Inactive implements ShortcutCustomizationUiState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Inactive);
        }

        public final int hashCode() {
            return -141128182;
        }

        public final String toString() {
            return "Inactive";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResetShortcutDialog implements ShortcutCustomizationUiState {
        public static final ResetShortcutDialog INSTANCE = new ResetShortcutDialog();

        private ResetShortcutDialog() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ResetShortcutDialog);
        }

        public final int hashCode() {
            return 1087295838;
        }

        public final String toString() {
            return "ResetShortcutDialog";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AddShortcutDialog implements ShortcutCustomizationUiState {
        public final ShortcutKey.Icon.ResIdIcon defaultCustomShortcutModifierKey;
        public final String errorMessage;
        public final List pressedKeys;
        public final String pressedKeysDescription;
        public final String shortcutLabel;

        public AddShortcutDialog(String str, String str2, ShortcutKey.Icon.ResIdIcon resIdIcon, List<? extends ShortcutKey> list, String str3) {
            this.shortcutLabel = str;
            this.errorMessage = str2;
            this.defaultCustomShortcutModifierKey = resIdIcon;
            this.pressedKeys = list;
            this.pressedKeysDescription = str3;
        }

        public static AddShortcutDialog copy$default(AddShortcutDialog addShortcutDialog, String str, List list, String str2, int i) {
            String str3 = addShortcutDialog.shortcutLabel;
            ShortcutKey.Icon.ResIdIcon resIdIcon = addShortcutDialog.defaultCustomShortcutModifierKey;
            if ((i & 8) != 0) {
                list = addShortcutDialog.pressedKeys;
            }
            List list2 = list;
            if ((i & 16) != 0) {
                str2 = addShortcutDialog.pressedKeysDescription;
            }
            addShortcutDialog.getClass();
            return new AddShortcutDialog(str3, str, resIdIcon, list2, str2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AddShortcutDialog)) {
                return false;
            }
            AddShortcutDialog addShortcutDialog = (AddShortcutDialog) obj;
            return Intrinsics.areEqual(this.shortcutLabel, addShortcutDialog.shortcutLabel) && Intrinsics.areEqual(this.errorMessage, addShortcutDialog.errorMessage) && Intrinsics.areEqual(this.defaultCustomShortcutModifierKey, addShortcutDialog.defaultCustomShortcutModifierKey) && Intrinsics.areEqual(this.pressedKeys, addShortcutDialog.pressedKeys) && Intrinsics.areEqual(this.pressedKeysDescription, addShortcutDialog.pressedKeysDescription);
        }

        public final int hashCode() {
            return this.pressedKeysDescription.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.pressedKeys, ReorderTile$$ExternalSyntheticOutline0.m(this.defaultCustomShortcutModifierKey.drawableResId, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.shortcutLabel.hashCode() * 31, 31, this.errorMessage), 31), 31);
        }

        public final String toString() {
            List list = this.pressedKeys;
            StringBuilder sb = new StringBuilder("AddShortcutDialog(shortcutLabel=");
            sb.append(this.shortcutLabel);
            sb.append(", errorMessage=");
            sb.append(this.errorMessage);
            sb.append(", defaultCustomShortcutModifierKey=");
            sb.append(this.defaultCustomShortcutModifierKey);
            sb.append(", pressedKeys=");
            sb.append(list);
            sb.append(", pressedKeysDescription=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.pressedKeysDescription, ")");
        }

        public AddShortcutDialog(String str, String str2, ShortcutKey.Icon.ResIdIcon resIdIcon, List list, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? "" : str2, resIdIcon, (i & 8) != 0 ? EmptyList.INSTANCE : list, (i & 16) != 0 ? "" : str3);
        }
    }
}
