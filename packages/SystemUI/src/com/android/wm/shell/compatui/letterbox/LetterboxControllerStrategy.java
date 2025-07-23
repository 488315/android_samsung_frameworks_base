package com.android.wm.shell.compatui.letterbox;

import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxControllerStrategy {
    public volatile LetterboxMode currentMode = LetterboxMode.SINGLE_SURFACE;
    public final LetterboxConfiguration letterboxConfiguration;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LetterboxMode {
        public static final /* synthetic */ LetterboxMode[] $VALUES;
        public static final LetterboxMode MULTIPLE_SURFACES;
        public static final LetterboxMode SINGLE_SURFACE;

        static {
            LetterboxMode letterboxMode = new LetterboxMode("SINGLE_SURFACE", 0);
            SINGLE_SURFACE = letterboxMode;
            LetterboxMode letterboxMode2 = new LetterboxMode("MULTIPLE_SURFACES", 1);
            MULTIPLE_SURFACES = letterboxMode2;
            LetterboxMode[] letterboxModeArr = {letterboxMode, letterboxMode2};
            $VALUES = letterboxModeArr;
            EnumEntriesKt.enumEntries(letterboxModeArr);
        }

        private LetterboxMode(String str, int i) {
        }

        public static LetterboxMode valueOf(String str) {
            return (LetterboxMode) Enum.valueOf(LetterboxMode.class, str);
        }

        public static LetterboxMode[] values() {
            return (LetterboxMode[]) $VALUES.clone();
        }
    }

    public LetterboxControllerStrategy(LetterboxConfiguration letterboxConfiguration) {
        this.letterboxConfiguration = letterboxConfiguration;
    }
}
