package com.android.systemui.keyboard.shortcut.ui.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface ShortcutsUiState {

    public final class Inactive implements ShortcutsUiState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Inactive);
        }

        public final int hashCode() {
            return 500168522;
        }

        public final String toString() {
            return "Inactive";
        }
    }

    public final class Active implements ShortcutsUiState {
        public final ShortcutCategoryType defaultSelectedCategory;
        public final boolean isCustomizationModeEnabled;
        public final boolean isShortcutCustomizerFlagEnabled;
        public final String searchQuery;
        public final List shortcutCategories;
        public final boolean shouldShowResetButton;

        public Active(String str, List<ShortcutCategoryUi> list, ShortcutCategoryType shortcutCategoryType, boolean z, boolean z2, boolean z3) {
            this.searchQuery = str;
            this.shortcutCategories = list;
            this.defaultSelectedCategory = shortcutCategoryType;
            this.isShortcutCustomizerFlagEnabled = z;
            this.shouldShowResetButton = z2;
            this.isCustomizationModeEnabled = z3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Active)) {
                return false;
            }
            Active active = (Active) obj;
            return Intrinsics.areEqual(this.searchQuery, active.searchQuery) && Intrinsics.areEqual(this.shortcutCategories, active.shortcutCategories) && Intrinsics.areEqual(this.defaultSelectedCategory, active.defaultSelectedCategory) && this.isShortcutCustomizerFlagEnabled == active.isShortcutCustomizerFlagEnabled && this.shouldShowResetButton == active.shouldShowResetButton && this.isCustomizationModeEnabled == active.isCustomizationModeEnabled;
        }

        public final int hashCode() {
            int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.shortcutCategories, this.searchQuery.hashCode() * 31, 31);
            ShortcutCategoryType shortcutCategoryType = this.defaultSelectedCategory;
            return Boolean.hashCode(this.isCustomizationModeEnabled) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((iM + (shortcutCategoryType == null ? 0 : shortcutCategoryType.hashCode())) * 31, 31, this.isShortcutCustomizerFlagEnabled), 31, this.shouldShowResetButton);
        }

        public final String toString() {
            List list = this.shortcutCategories;
            StringBuilder sb = new StringBuilder("Active(searchQuery=");
            sb.append(this.searchQuery);
            sb.append(", shortcutCategories=");
            sb.append(list);
            sb.append(", defaultSelectedCategory=");
            sb.append(this.defaultSelectedCategory);
            sb.append(", isShortcutCustomizerFlagEnabled=");
            sb.append(this.isShortcutCustomizerFlagEnabled);
            sb.append(", shouldShowResetButton=");
            sb.append(this.shouldShowResetButton);
            sb.append(", isCustomizationModeEnabled=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCustomizationModeEnabled, ")");
        }

        public /* synthetic */ Active(String str, List list, ShortcutCategoryType shortcutCategoryType, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, list, shortcutCategoryType, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3);
        }
    }
}
