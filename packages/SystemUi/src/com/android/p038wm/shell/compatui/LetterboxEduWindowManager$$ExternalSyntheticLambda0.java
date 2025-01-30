package com.android.p038wm.shell.compatui;

import android.util.Pair;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        switch (this.$r8$classId) {
            case 0:
                LetterboxEduWindowManager letterboxEduWindowManager = this.f$0;
                LetterboxEduDialogLayout letterboxEduDialogLayout = letterboxEduWindowManager.mLayout;
                if (letterboxEduDialogLayout != null) {
                    letterboxEduWindowManager.mAnimationController.startEnterAnimation(letterboxEduDialogLayout, new LetterboxEduWindowManager$$ExternalSyntheticLambda0(letterboxEduWindowManager, i));
                    break;
                }
                break;
            case 1:
                LetterboxEduWindowManager letterboxEduWindowManager2 = this.f$0;
                LetterboxEduDialogLayout letterboxEduDialogLayout2 = letterboxEduWindowManager2.mLayout;
                if (letterboxEduDialogLayout2 != null) {
                    letterboxEduDialogLayout2.setDismissOnClickListener(new LetterboxEduWindowManager$$ExternalSyntheticLambda0(letterboxEduWindowManager2, 2));
                    letterboxEduWindowManager2.mLayout.mDialogTitle.sendAccessibilityEvent(8);
                    break;
                }
                break;
            case 2:
                LetterboxEduWindowManager letterboxEduWindowManager3 = this.f$0;
                if (letterboxEduWindowManager3.mLayout != null) {
                    letterboxEduWindowManager3.mCompatUIConfiguration.mLetterboxEduSharedPreferences.edit().putBoolean(String.valueOf(letterboxEduWindowManager3.mUserId), true).apply();
                    letterboxEduWindowManager3.mLayout.setDismissOnClickListener(null);
                    letterboxEduWindowManager3.mAnimationController.startExitAnimation(letterboxEduWindowManager3.mLayout, new LetterboxEduWindowManager$$ExternalSyntheticLambda0(letterboxEduWindowManager3, 3));
                    break;
                }
                break;
            default:
                LetterboxEduWindowManager letterboxEduWindowManager4 = this.f$0;
                letterboxEduWindowManager4.release();
                letterboxEduWindowManager4.mOnDismissCallback.accept(Pair.create(letterboxEduWindowManager4.mTaskInfo, letterboxEduWindowManager4.mTaskListener));
                break;
        }
    }
}
