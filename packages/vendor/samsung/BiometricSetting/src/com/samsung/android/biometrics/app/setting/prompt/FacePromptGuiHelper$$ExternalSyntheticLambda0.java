package com.samsung.android.biometrics.app.setting.prompt;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final /* synthetic */ class FacePromptGuiHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FacePromptGuiHelper f$0;

    public /* synthetic */ FacePromptGuiHelper$$ExternalSyntheticLambda0(
            FacePromptGuiHelper facePromptGuiHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = facePromptGuiHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FacePromptGuiHelper facePromptGuiHelper = this.f$0;
        switch (i) {
            case 0:
                facePromptGuiHelper.mScrollView.postDelayed(
                        new FacePromptGuiHelper$$ExternalSyntheticLambda0(facePromptGuiHelper, 4),
                        100L);
                break;
            case 1:
                facePromptGuiHelper.mScrollView.fullScroll(130);
                break;
            case 2:
                facePromptGuiHelper.mScrollView.fullScroll(130);
                break;
            case 3:
                facePromptGuiHelper.mScrollView.postDelayed(
                        new FacePromptGuiHelper$$ExternalSyntheticLambda0(facePromptGuiHelper, 5),
                        100L);
                break;
            case 4:
                facePromptGuiHelper.updateScrollViewHeight();
                facePromptGuiHelper.mScrollView.post(
                        new FacePromptGuiHelper$$ExternalSyntheticLambda0(facePromptGuiHelper, 8));
                break;
            case 5:
                facePromptGuiHelper.updateScrollViewHeight();
                facePromptGuiHelper.mScrollView.post(
                        new FacePromptGuiHelper$$ExternalSyntheticLambda0(facePromptGuiHelper, 1));
                break;
            case 6:
                facePromptGuiHelper.mScrollView.postDelayed(
                        new FacePromptGuiHelper$$ExternalSyntheticLambda0(facePromptGuiHelper, 7),
                        100L);
                break;
            case 7:
                facePromptGuiHelper.updateScrollViewHeight();
                facePromptGuiHelper.mScrollView.post(
                        new FacePromptGuiHelper$$ExternalSyntheticLambda0(facePromptGuiHelper, 2));
                break;
            default:
                facePromptGuiHelper.mScrollView.fullScroll(130);
                break;
        }
    }
}
