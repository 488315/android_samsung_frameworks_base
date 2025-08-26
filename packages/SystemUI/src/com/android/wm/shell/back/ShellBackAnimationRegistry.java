package com.android.wm.shell.back;

import android.util.SparseArray;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ShellBackAnimationRegistry {
    public final SparseArray mAnimationDefinition;
    public final ShellBackAnimation mCrossTaskAnimation;
    public final ShellBackAnimation mCustomizeActivityAnimation;
    public final ShellBackAnimation mDefaultCrossActivityAnimation;
    public final ArrayList mSupportedAnimators;
    public boolean mSupportedAnimatorsChanged;

    public ShellBackAnimationRegistry(ShellBackAnimation shellBackAnimation, ShellBackAnimation shellBackAnimation2, ShellBackAnimation shellBackAnimation3, ShellBackAnimation shellBackAnimation4, ShellBackAnimation shellBackAnimation5, ShellBackAnimation shellBackAnimation6) {
        SparseArray sparseArray = new SparseArray();
        this.mAnimationDefinition = sparseArray;
        this.mSupportedAnimatorsChanged = false;
        this.mSupportedAnimators = new ArrayList();
        if (CoreRune.FW_PREDICTIVE_BACK_ANIM && shellBackAnimation6 != null) {
            shellBackAnimation = shellBackAnimation6;
            shellBackAnimation2 = shellBackAnimation;
        }
        if (shellBackAnimation != null) {
            sparseArray.set(2, shellBackAnimation.getRunner());
        }
        if (shellBackAnimation2 != null) {
            sparseArray.set(3, shellBackAnimation2.getRunner());
        }
        if (shellBackAnimation3 != null) {
            sparseArray.set(0, shellBackAnimation3.getRunner());
        }
        if (shellBackAnimation5 != null) {
            sparseArray.set(1, shellBackAnimation5.getRunner());
        }
        this.mDefaultCrossActivityAnimation = shellBackAnimation;
        this.mCustomizeActivityAnimation = shellBackAnimation4;
        this.mCrossTaskAnimation = shellBackAnimation2;
        updateSupportedAnimators();
    }

    public final void updateSupportedAnimators() {
        this.mSupportedAnimators.clear();
        for (int size = this.mAnimationDefinition.size() - 1; size >= 0; size--) {
            this.mSupportedAnimators.add(Integer.valueOf(this.mAnimationDefinition.keyAt(size)));
        }
        this.mSupportedAnimatorsChanged = true;
    }
}
