package com.android.systemui.biometrics.ui.viewmodel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PromptSizeKt {
    public static final boolean isMedium(PromptSize promptSize) {
        return promptSize != null && promptSize == PromptSize.MEDIUM;
    }

    public static final boolean isNotSmall(PromptSize promptSize) {
        return (promptSize == null || promptSize == PromptSize.SMALL) ? false : true;
    }

    public static final boolean isSmall(PromptSize promptSize) {
        return promptSize != null && promptSize == PromptSize.SMALL;
    }
}
