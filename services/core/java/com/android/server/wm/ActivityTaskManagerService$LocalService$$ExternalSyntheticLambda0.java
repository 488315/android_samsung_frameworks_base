package com.android.server.wm;

import android.R;
import android.content.Context;
import android.provider.Settings;

import com.android.server.am.BaseErrorDialog;

import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityTaskManagerService.LocalService f$0;

    public /* synthetic */ ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0(
            ActivityTaskManagerService.LocalService localService, int i) {
        this.$r8$classId = i;
        this.f$0 = localService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ActivityTaskManagerService.LocalService localService = this.f$0;
        switch (i) {
            case 0:
                ActivityTaskManagerService activityTaskManagerService =
                        ActivityTaskManagerService.this.mMultiTaskingController.mAtm;
                activityTaskManagerService.mDexController.mGlobalFontScaleForRestore =
                        Settings.System.getFloatForUser(
                                activityTaskManagerService.mContext.getContentResolver(),
                                "font_scale",
                                1.0f,
                                activityTaskManagerService.mAmInternal.getCurrentUserId());
                break;
            case 1:
                ActivityTaskManagerService activityTaskManagerService2 =
                        ActivityTaskManagerService.this;
                if (activityTaskManagerService2.mShowDialogs) {
                    BaseErrorDialog baseErrorDialog =
                            new BaseErrorDialog(activityTaskManagerService2.mUiContext);
                    baseErrorDialog.getWindow().setType(2010);
                    baseErrorDialog.setCancelable(false);
                    Context context = activityTaskManagerService2.mUiContext;
                    boolean z = CoreRune.IS_TABLET_DEVICE;
                    baseErrorDialog.setTitle(context.getText(z ? 17042562 : 17042561));
                    baseErrorDialog.setMessage(
                            activityTaskManagerService2.mUiContext.getText(
                                    z ? 17042560 : 17042559));
                    baseErrorDialog.setButton(
                            -1,
                            activityTaskManagerService2.mUiContext.getText(R.string.ok),
                            activityTaskManagerService2.mUiHandler.obtainMessage(
                                    1, baseErrorDialog));
                    baseErrorDialog.show();
                    break;
                }
                break;
            case 2:
                ActivityTaskManagerService activityTaskManagerService3 =
                        ActivityTaskManagerService.this;
                if (activityTaskManagerService3.mShowDialogs) {
                    BaseErrorDialog baseErrorDialog2 =
                            new BaseErrorDialog(activityTaskManagerService3.mUiContext);
                    baseErrorDialog2.getWindow().setType(2010);
                    baseErrorDialog2.setCancelable(false);
                    baseErrorDialog2.setTitle(
                            activityTaskManagerService3.mUiContext.getText(
                                    R.string.capability_title_canPerformGestures));
                    baseErrorDialog2.setMessage(
                            activityTaskManagerService3.mUiContext.getText(17043200));
                    baseErrorDialog2.setButton(
                            -1,
                            activityTaskManagerService3.mUiContext.getText(R.string.ok),
                            activityTaskManagerService3.mUiHandler.obtainMessage(
                                    1, baseErrorDialog2));
                    baseErrorDialog2.show();
                    break;
                }
                break;
            default:
                ActivityTaskManagerService activityTaskManagerService4 =
                        ActivityTaskManagerService.this;
                if (activityTaskManagerService4.mShowDialogs) {
                    BaseErrorDialog baseErrorDialog3 =
                            new BaseErrorDialog(activityTaskManagerService4.mUiContext);
                    baseErrorDialog3.getWindow().setType(2010);
                    baseErrorDialog3.setCancelable(false);
                    baseErrorDialog3.setTitle(
                            activityTaskManagerService4.mUiContext.getText(
                                    R.string.capability_title_canPerformGestures));
                    baseErrorDialog3.setMessage(
                            activityTaskManagerService4.mUiContext.getText(17043199));
                    baseErrorDialog3.setButton(
                            -1,
                            activityTaskManagerService4.mUiContext.getText(R.string.ok),
                            activityTaskManagerService4.mUiHandler.obtainMessage(
                                    1, baseErrorDialog3));
                    baseErrorDialog3.show();
                    break;
                }
                break;
        }
    }
}
