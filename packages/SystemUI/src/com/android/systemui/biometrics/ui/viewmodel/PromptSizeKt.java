package com.android.systemui.biometrics.ui.viewmodel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
