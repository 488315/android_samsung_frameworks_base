package com.samsung.android.sdk.scs.ai.translation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum LanguageDirectionState {
    UNKNOWN(-1),
    AVAILABLE(0),
    AVAILABLE_BY_PIVOT(1),
    DOWNLOADABLE(2),
    /* JADX INFO: Fake field, exist only in values array */
    UNAUTHORIZED_RESOURCE(3);

    private final int value;

    LanguageDirectionState(int i) {
        this.value = i;
    }

    public static LanguageDirectionState from(int i) {
        for (LanguageDirectionState languageDirectionState : values()) {
            if (languageDirectionState.value == i) {
                return languageDirectionState;
            }
        }
        return UNKNOWN;
    }
}
