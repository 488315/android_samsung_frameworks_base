package com.android.wm.shell.compatui;

import android.util.Pair;

/* loaded from: classes3.dex */
public final /* synthetic */ class LetterboxEduWindowManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LetterboxEduWindowManager f$0;

    public /* synthetic */ LetterboxEduWindowManager$$ExternalSyntheticLambda0(LetterboxEduWindowManager letterboxEduWindowManager, int i) {
        this.$r8$classId = i;
        this.f$0 = letterboxEduWindowManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        int i2 = this.$r8$classId;
        LetterboxEduWindowManager letterboxEduWindowManager = this.f$0;
        switch (i2) {
            case 0:
                LetterboxEduDialogLayout letterboxEduDialogLayout = letterboxEduWindowManager.mLayout;
                if (letterboxEduDialogLayout != null) {
                    letterboxEduWindowManager.mAnimationController.startEnterAnimation(letterboxEduDialogLayout, new LetterboxEduWindowManager$$ExternalSyntheticLambda0(letterboxEduWindowManager, i));
                    break;
                }
                break;
            case 1:
                LetterboxEduDialogLayout letterboxEduDialogLayout2 = letterboxEduWindowManager.mLayout;
                if (letterboxEduDialogLayout2 != null) {
                    letterboxEduDialogLayout2.setDismissOnClickListener(new LetterboxEduWindowManager$$ExternalSyntheticLambda0(letterboxEduWindowManager, 2));
                    letterboxEduWindowManager.mLayout.mDialogTitle.sendAccessibilityEvent(8);
                    break;
                }
                break;
            case 2:
                if (letterboxEduWindowManager.mLayout != null) {
                    letterboxEduWindowManager.mCompatUIConfiguration.mLetterboxEduSharedPreferences.edit().putBoolean(String.valueOf(letterboxEduWindowManager.mUserId), true).apply();
                    letterboxEduWindowManager.mLayout.setDismissOnClickListener(null);
                    letterboxEduWindowManager.mAnimationController.startExitAnimation(letterboxEduWindowManager.mLayout, new LetterboxEduWindowManager$$ExternalSyntheticLambda0(letterboxEduWindowManager, 3));
                    break;
                }
                break;
            default:
                int i3 = LetterboxEduWindowManager.$r8$clinit;
                letterboxEduWindowManager.release();
                letterboxEduWindowManager.mOnDismissCallback.accept(Pair.create(letterboxEduWindowManager.mTaskInfo, letterboxEduWindowManager.mTaskListener));
                break;
        }
    }
}
