package com.android.systemui.biometrics.ui.viewmodel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class PromptSizeKt {
    public static final boolean isNotSmall(PromptSize promptSize) {
        return (promptSize == null || promptSize == PromptSize.SMALL) ? false : true;
    }
}
