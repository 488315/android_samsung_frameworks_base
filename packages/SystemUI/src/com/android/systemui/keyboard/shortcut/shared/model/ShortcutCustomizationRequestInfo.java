package com.android.systemui.keyboard.shortcut.shared.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface ShortcutCustomizationRequestInfo {

    public final class Reset implements ShortcutCustomizationRequestInfo {
        public static final Reset INSTANCE = new Reset();

        private Reset() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Reset);
        }

        public final int hashCode() {
            return 2128349791;
        }

        public final String toString() {
            return "Reset";
        }
    }

    public interface SingleShortcutCustomization extends ShortcutCustomizationRequestInfo {

        public final class Add implements SingleShortcutCustomization {
            public final ShortcutCategoryType categoryType;
            public final ShortcutCommand defaultShortcutCommand;
            public final String label;
            public final String subCategoryLabel;

            public Add() {
                this(null, null, null, null, 15, null);
            }

            public static Add copy$default(Add add, ShortcutCategoryType shortcutCategoryType, String str, int i) {
                String str2 = add.label;
                if ((i & 2) != 0) {
                    shortcutCategoryType = add.categoryType;
                }
                if ((i & 4) != 0) {
                    str = add.subCategoryLabel;
                }
                ShortcutCommand shortcutCommand = add.defaultShortcutCommand;
                add.getClass();
                return new Add(str2, shortcutCategoryType, str, shortcutCommand);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Add)) {
                    return false;
                }
                Add add = (Add) obj;
                return Intrinsics.areEqual(this.label, add.label) && Intrinsics.areEqual(this.categoryType, add.categoryType) && Intrinsics.areEqual(this.subCategoryLabel, add.subCategoryLabel) && Intrinsics.areEqual(this.defaultShortcutCommand, add.defaultShortcutCommand);
            }

            @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo.SingleShortcutCustomization
            public final ShortcutCategoryType getCategoryType() {
                return this.categoryType;
            }

            @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo.SingleShortcutCustomization
            public final ShortcutCommand getDefaultShortcutCommand() {
                return this.defaultShortcutCommand;
            }

            @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo.SingleShortcutCustomization
            public final String getLabel() {
                return this.label;
            }

            public final int hashCode() {
                int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.categoryType.hashCode() + (this.label.hashCode() * 31)) * 31, 31, this.subCategoryLabel);
                ShortcutCommand shortcutCommand = this.defaultShortcutCommand;
                return iM + (shortcutCommand == null ? 0 : shortcutCommand.hashCode());
            }

            public final String toString() {
                return "Add(label=" + this.label + ", categoryType=" + this.categoryType + ", subCategoryLabel=" + this.subCategoryLabel + ", defaultShortcutCommand=" + this.defaultShortcutCommand + ")";
            }

            public Add(String str, ShortcutCategoryType shortcutCategoryType, String str2, ShortcutCommand shortcutCommand) {
                this.label = str;
                this.categoryType = shortcutCategoryType;
                this.subCategoryLabel = str2;
                this.defaultShortcutCommand = shortcutCommand;
            }

            public /* synthetic */ Add(String str, ShortcutCategoryType shortcutCategoryType, String str2, ShortcutCommand shortcutCommand, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? ShortcutCategoryType.System.INSTANCE : shortcutCategoryType, (i & 4) != 0 ? "" : str2, (i & 8) != 0 ? null : shortcutCommand);
            }
        }

        public final class Delete implements SingleShortcutCustomization {
            public final ShortcutCategoryType categoryType;
            public final ShortcutCommand customShortcutCommand;
            public final ShortcutCommand defaultShortcutCommand;
            public final String label;
            public final String subCategoryLabel;

            public Delete() {
                this(null, null, null, null, null, 31, null);
            }

            public static Delete copy$default(Delete delete, ShortcutCategoryType shortcutCategoryType, String str, int i) {
                String str2 = delete.label;
                if ((i & 2) != 0) {
                    shortcutCategoryType = delete.categoryType;
                }
                ShortcutCategoryType shortcutCategoryType2 = shortcutCategoryType;
                if ((i & 4) != 0) {
                    str = delete.subCategoryLabel;
                }
                ShortcutCommand shortcutCommand = delete.defaultShortcutCommand;
                ShortcutCommand shortcutCommand2 = delete.customShortcutCommand;
                delete.getClass();
                return new Delete(str2, shortcutCategoryType2, str, shortcutCommand, shortcutCommand2);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Delete)) {
                    return false;
                }
                Delete delete = (Delete) obj;
                return Intrinsics.areEqual(this.label, delete.label) && Intrinsics.areEqual(this.categoryType, delete.categoryType) && Intrinsics.areEqual(this.subCategoryLabel, delete.subCategoryLabel) && Intrinsics.areEqual(this.defaultShortcutCommand, delete.defaultShortcutCommand) && Intrinsics.areEqual(this.customShortcutCommand, delete.customShortcutCommand);
            }

            @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo.SingleShortcutCustomization
            public final ShortcutCategoryType getCategoryType() {
                return this.categoryType;
            }

            @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo.SingleShortcutCustomization
            public final ShortcutCommand getDefaultShortcutCommand() {
                return this.defaultShortcutCommand;
            }

            @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo.SingleShortcutCustomization
            public final String getLabel() {
                return this.label;
            }

            public final int hashCode() {
                int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.categoryType.hashCode() + (this.label.hashCode() * 31)) * 31, 31, this.subCategoryLabel);
                ShortcutCommand shortcutCommand = this.defaultShortcutCommand;
                int iHashCode = (iM + (shortcutCommand == null ? 0 : shortcutCommand.hashCode())) * 31;
                ShortcutCommand shortcutCommand2 = this.customShortcutCommand;
                return iHashCode + (shortcutCommand2 != null ? shortcutCommand2.hashCode() : 0);
            }

            public final String toString() {
                return "Delete(label=" + this.label + ", categoryType=" + this.categoryType + ", subCategoryLabel=" + this.subCategoryLabel + ", defaultShortcutCommand=" + this.defaultShortcutCommand + ", customShortcutCommand=" + this.customShortcutCommand + ")";
            }

            public Delete(String str, ShortcutCategoryType shortcutCategoryType, String str2, ShortcutCommand shortcutCommand, ShortcutCommand shortcutCommand2) {
                this.label = str;
                this.categoryType = shortcutCategoryType;
                this.subCategoryLabel = str2;
                this.defaultShortcutCommand = shortcutCommand;
                this.customShortcutCommand = shortcutCommand2;
            }

            public /* synthetic */ Delete(String str, ShortcutCategoryType shortcutCategoryType, String str2, ShortcutCommand shortcutCommand, ShortcutCommand shortcutCommand2, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? ShortcutCategoryType.System.INSTANCE : shortcutCategoryType, (i & 4) != 0 ? "" : str2, (i & 8) != 0 ? null : shortcutCommand, (i & 16) != 0 ? null : shortcutCommand2);
            }
        }

        ShortcutCategoryType getCategoryType();

        ShortcutCommand getDefaultShortcutCommand();

        String getLabel();
    }
}
