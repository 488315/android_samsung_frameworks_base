package com.android.systemui.biometrics.ui.viewmodel;

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
