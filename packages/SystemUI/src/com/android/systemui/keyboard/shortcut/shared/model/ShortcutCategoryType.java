package com.android.systemui.keyboard.shortcut.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ShortcutCategoryType {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Accessibility implements ShortcutCategoryType {
        public static final Accessibility INSTANCE = new Accessibility();
        public static final boolean includeInCustomization = true;

        private Accessibility() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Accessibility);
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean getIncludeInCustomization() {
            return includeInCustomization;
        }

        public final int hashCode() {
            return 1934979884;
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean isTrusted() {
            return false;
        }

        public final String toString() {
            return "Accessibility";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AppCategories implements ShortcutCategoryType {
        public static final AppCategories INSTANCE = new AppCategories();
        public static final boolean isTrusted = true;
        public static final boolean includeInCustomization = true;

        private AppCategories() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof AppCategories);
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean getIncludeInCustomization() {
            return includeInCustomization;
        }

        public final int hashCode() {
            return 630118651;
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean isTrusted() {
            return isTrusted;
        }

        public final String toString() {
            return "AppCategories";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CurrentApp implements ShortcutCategoryType {
        public final String packageName;

        public CurrentApp(String str) {
            this.packageName = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof CurrentApp) && Intrinsics.areEqual(this.packageName, ((CurrentApp) obj).packageName);
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean getIncludeInCustomization() {
            return false;
        }

        public final int hashCode() {
            return this.packageName.hashCode();
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean isTrusted() {
            return false;
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("CurrentApp(packageName="), this.packageName, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InputMethodEditor implements ShortcutCategoryType {
        public static final InputMethodEditor INSTANCE = new InputMethodEditor();

        private InputMethodEditor() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof InputMethodEditor);
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean getIncludeInCustomization() {
            return false;
        }

        public final int hashCode() {
            return -371863178;
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean isTrusted() {
            return false;
        }

        public final String toString() {
            return "InputMethodEditor";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MultiTasking implements ShortcutCategoryType {
        public static final MultiTasking INSTANCE = new MultiTasking();
        public static final boolean isTrusted = true;
        public static final boolean includeInCustomization = true;

        private MultiTasking() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MultiTasking);
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean getIncludeInCustomization() {
            return includeInCustomization;
        }

        public final int hashCode() {
            return 1497658150;
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean isTrusted() {
            return isTrusted;
        }

        public final String toString() {
            return "MultiTasking";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class System implements ShortcutCategoryType {
        public static final System INSTANCE = new System();
        public static final boolean isTrusted = true;
        public static final boolean includeInCustomization = true;

        private System() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof System);
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean getIncludeInCustomization() {
            return includeInCustomization;
        }

        public final int hashCode() {
            return 211418641;
        }

        @Override // com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType
        public final boolean isTrusted() {
            return isTrusted;
        }

        public final String toString() {
            return "System";
        }
    }

    boolean getIncludeInCustomization();

    boolean isTrusted();
}
