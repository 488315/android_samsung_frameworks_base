package com.android.wm.shell.dagger;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideIndependentShellComponentsToCreateFactory implements Provider {
    public final Provider activityEmbeddingOptionalProvider;
    public final Provider appZoomOutControllerOptionalProvider;
    public final Provider bubblesOptionalProvider;
    public final Provider displayControllerProvider;
    public final Provider displayImeControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider enterSplitGestureHandlerOptionalProvider;
    public final Provider freeformComponentsProvider;
    public final Provider fullscreenTaskListenerProvider;
    public final Provider hideDisplayCutoutControllerOptionalProvider;
    public final Provider mixedTransitionHandlerProvider;
    public final Provider oneHandedControllerOptionalProvider;
    public final Provider overriddenCreateTriggerProvider;
    public final Provider protoLogControllerProvider;
    public final Provider recentTasksOptionalProvider;
    public final Provider recentsTransitionHandlerOptionalProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splitScreenOptionalProvider;
    public final Provider startingWindowProvider;
    public final Provider transitionsProvider;
    public final Provider unfoldAnimationControllerProvider;
    public final Provider unfoldTransitionHandlerProvider;

    public WMShellBaseModule_ProvideIndependentShellComponentsToCreateFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22) {
        this.displayControllerProvider = provider;
        this.displayImeControllerProvider = provider2;
        this.displayInsetsControllerProvider = provider3;
        this.shellTaskOrganizerProvider = provider4;
        this.bubblesOptionalProvider = provider5;
        this.splitScreenOptionalProvider = provider6;
        this.fullscreenTaskListenerProvider = provider7;
        this.unfoldAnimationControllerProvider = provider8;
        this.unfoldTransitionHandlerProvider = provider9;
        this.freeformComponentsProvider = provider10;
        this.recentTasksOptionalProvider = provider11;
        this.recentsTransitionHandlerOptionalProvider = provider12;
        this.oneHandedControllerOptionalProvider = provider13;
        this.appZoomOutControllerOptionalProvider = provider14;
        this.hideDisplayCutoutControllerOptionalProvider = provider15;
        this.activityEmbeddingOptionalProvider = provider16;
        this.mixedTransitionHandlerProvider = provider17;
        this.transitionsProvider = provider18;
        this.startingWindowProvider = provider19;
        this.enterSplitGestureHandlerOptionalProvider = provider20;
        this.protoLogControllerProvider = provider21;
        this.overriddenCreateTriggerProvider = provider22;
    }

    public static Object provideIndependentShellComponentsToCreate() {
        return new Object();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new Object();
    }
}
