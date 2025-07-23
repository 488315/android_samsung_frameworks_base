package com.android.wm.shell.shared;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TypefaceUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FontFamily {
        public static final /* synthetic */ FontFamily[] $VALUES;
        public static final FontFamily GSF_BODY_MEDIUM = null;
        public static final FontFamily GSF_BODY_MEDIUM_EMPHASIZED = null;
        public static final FontFamily GSF_LABEL_LARGE = null;
        public static final FontFamily GSF_TITLE_MEDIUM = null;
        private final String value;

        static {
            FontFamily[] fontFamilyArr = {new FontFamily("GSF_DISPLAY_LARGE", 0, "variable-display-large"), new FontFamily("GSF_DISPLAY_MEDIUM", 1, "variable-display-medium"), new FontFamily("GSF_DISPLAY_SMALL", 2, "variable-display-small"), new FontFamily("GSF_HEADLINE_LARGE", 3, "variable-headline-large"), new FontFamily("GSF_HEADLINE_MEDIUM", 4, "variable-headline-medium"), new FontFamily("GSF_HEADLINE_SMALL", 5, "variable-headline-small"), new FontFamily("GSF_TITLE_LARGE", 6, "variable-title-large"), new FontFamily("GSF_TITLE_MEDIUM", 7, "variable-title-medium"), new FontFamily("GSF_TITLE_SMALL", 8, "variable-title-small"), new FontFamily("GSF_LABEL_LARGE", 9, "variable-label-large"), new FontFamily("GSF_LABEL_MEDIUM", 10, "variable-label-medium"), new FontFamily("GSF_LABEL_SMALL", 11, "variable-label-small"), new FontFamily("GSF_BODY_LARGE", 12, "variable-body-large"), new FontFamily("GSF_BODY_MEDIUM", 13, "variable-body-medium"), new FontFamily("GSF_BODY_SMALL", 14, "variable-body-small"), new FontFamily("GSF_DISPLAY_LARGE_EMPHASIZED", 15, "variable-display-large-emphasized"), new FontFamily("GSF_DISPLAY_MEDIUM_EMPHASIZED", 16, "variable-display-medium-emphasized"), new FontFamily("GSF_DISPLAY_SMALL_EMPHASIZED", 17, "variable-display-small-emphasized"), new FontFamily("GSF_HEADLINE_LARGE_EMPHASIZED", 18, "variable-headline-large-emphasized"), new FontFamily("GSF_HEADLINE_MEDIUM_EMPHASIZED", 19, "variable-headline-medium-emphasized"), new FontFamily("GSF_HEADLINE_SMALL_EMPHASIZED", 20, "variable-headline-small-emphasized"), new FontFamily("GSF_TITLE_LARGE_EMPHASIZED", 21, "variable-title-large-emphasized"), new FontFamily("GSF_TITLE_MEDIUM_EMPHASIZED", 22, "variable-title-medium-emphasized"), new FontFamily("GSF_TITLE_SMALL_EMPHASIZED", 23, "variable-title-small-emphasized"), new FontFamily("GSF_LABEL_LARGE_EMPHASIZED", 24, "variable-label-large-emphasized"), new FontFamily("GSF_LABEL_MEDIUM_EMPHASIZED", 25, "variable-label-medium-emphasized"), new FontFamily("GSF_LABEL_SMALL_EMPHASIZED", 26, "variable-label-small-emphasized"), new FontFamily("GSF_BODY_LARGE_EMPHASIZED", 27, "variable-body-large-emphasized"), new FontFamily("GSF_BODY_MEDIUM_EMPHASIZED", 28, "variable-body-medium-emphasized"), new FontFamily("GSF_BODY_SMALL_EMPHASIZED", 29, "variable-body-small-emphasized")};
            $VALUES = fontFamilyArr;
            EnumEntriesKt.enumEntries(fontFamilyArr);
        }

        private FontFamily(String str, int i, String str2) {
            this.value = str2;
        }

        public static FontFamily valueOf(String str) {
            return (FontFamily) Enum.valueOf(FontFamily.class, str);
        }

        public static FontFamily[] values() {
            return (FontFamily[]) $VALUES.clone();
        }
    }

    public static final void setTypeface() {
        Companion.getClass();
    }
}
