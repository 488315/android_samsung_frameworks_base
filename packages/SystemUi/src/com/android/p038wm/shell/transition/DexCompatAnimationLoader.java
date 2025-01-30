package com.android.p038wm.shell.transition;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DexCompatAnimationLoader extends AnimationLoader {
    public DexCompatAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        return false;
    }

    public final String toString() {
        return "DexCompatAnimationLoader";
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
    }
}
