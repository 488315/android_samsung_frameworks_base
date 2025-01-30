package com.android.wm.shell.dagger;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideIndependentShellComponentsToCreateFactory */
/* loaded from: classes2.dex */
public final class C3950x6b581e0e implements Provider {
    public final Provider activityEmbeddingOptionalProvider;
    public final Provider bubblesOptionalProvider;
    public final Provider displayControllerProvider;
    public final Provider displayImeControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider dragAndDropControllerOptionalProvider;
    public final Provider enterSplitGestureHandlerOptionalProvider;
    public final Provider freeformComponentsProvider;
    public final Provider fullscreenTaskListenerProvider;
    public final Provider hideDisplayCutoutControllerOptionalProvider;
    public final Provider oneHandedControllerOptionalProvider;
    public final Provider overriddenCreateTriggerProvider;
    public final Provider pipOptionalProvider;
    public final Provider pipTouchHandlerOptionalProvider;
    public final Provider protoLogControllerProvider;
    public final Provider recentTasksOptionalProvider;
    public final Provider recentsTransitionHandlerOptionalProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splitScreenOptionalProvider;
    public final Provider startingWindowProvider;
    public final Provider transitionsProvider;
    public final Provider unfoldAnimationControllerProvider;
    public final Provider unfoldTransitionHandlerProvider;

    public C3950x6b581e0e(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23) {
        this.displayControllerProvider = provider;
        this.displayImeControllerProvider = provider2;
        this.displayInsetsControllerProvider = provider3;
        this.dragAndDropControllerOptionalProvider = provider4;
        this.shellTaskOrganizerProvider = provider5;
        this.bubblesOptionalProvider = provider6;
        this.splitScreenOptionalProvider = provider7;
        this.pipOptionalProvider = provider8;
        this.pipTouchHandlerOptionalProvider = provider9;
        this.fullscreenTaskListenerProvider = provider10;
        this.unfoldAnimationControllerProvider = provider11;
        this.unfoldTransitionHandlerProvider = provider12;
        this.freeformComponentsProvider = provider13;
        this.recentTasksOptionalProvider = provider14;
        this.recentsTransitionHandlerOptionalProvider = provider15;
        this.oneHandedControllerOptionalProvider = provider16;
        this.hideDisplayCutoutControllerOptionalProvider = provider17;
        this.activityEmbeddingOptionalProvider = provider18;
        this.transitionsProvider = provider19;
        this.startingWindowProvider = provider20;
        this.enterSplitGestureHandlerOptionalProvider = provider21;
        this.protoLogControllerProvider = provider22;
        this.overriddenCreateTriggerProvider = provider23;
    }

    public static Object provideIndependentShellComponentsToCreate() {
        return new Object();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new Object();
    }
}
