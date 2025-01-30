package com.android.p038wm.shell.compatui;

import android.app.TaskInfo;
import android.content.SharedPreferences;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import com.android.systemui.R;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class RestartDialogWindowManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RestartDialogWindowManager f$0;

    public /* synthetic */ RestartDialogWindowManager$$ExternalSyntheticLambda0(RestartDialogWindowManager restartDialogWindowManager, int i) {
        this.$r8$classId = i;
        this.f$0 = restartDialogWindowManager;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.wm.shell.compatui.RestartDialogWindowManager$$ExternalSyntheticLambda1] */
    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RestartDialogWindowManager restartDialogWindowManager = this.f$0;
                RestartDialogLayout restartDialogLayout = restartDialogWindowManager.mLayout;
                if (restartDialogLayout != null) {
                    restartDialogWindowManager.mAnimationController.startEnterAnimation(restartDialogLayout, new RestartDialogWindowManager$$ExternalSyntheticLambda0(restartDialogWindowManager, 1));
                    break;
                }
                break;
            case 1:
                final RestartDialogWindowManager restartDialogWindowManager2 = this.f$0;
                RestartDialogLayout restartDialogLayout2 = restartDialogWindowManager2.mLayout;
                if (restartDialogLayout2 != null) {
                    final TaskInfo taskInfo = restartDialogWindowManager2.mTaskInfo;
                    restartDialogLayout2.setDismissOnClickListener(new RestartDialogWindowManager$$ExternalSyntheticLambda0(restartDialogWindowManager2, 2));
                    RestartDialogLayout restartDialogLayout3 = restartDialogWindowManager2.mLayout;
                    final ?? r2 = new Consumer() { // from class: com.android.wm.shell.compatui.RestartDialogWindowManager$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RestartDialogWindowManager restartDialogWindowManager3 = RestartDialogWindowManager.this;
                            TaskInfo taskInfo2 = taskInfo;
                            Boolean bool = (Boolean) obj;
                            RestartDialogLayout restartDialogLayout4 = restartDialogWindowManager3.mLayout;
                            if (restartDialogLayout4 != null) {
                                restartDialogLayout4.setDismissOnClickListener(null);
                                restartDialogWindowManager3.mAnimationController.startExitAnimation(restartDialogWindowManager3.mLayout, new RestartDialogWindowManager$$ExternalSyntheticLambda0(restartDialogWindowManager3, 3));
                            }
                            if (bool.booleanValue()) {
                                SharedPreferences.Editor edit = restartDialogWindowManager3.mCompatUIConfiguration.mCompatUISharedPreferences.edit();
                                int i = taskInfo2.userId;
                                edit.putBoolean(taskInfo2.topActivity.getPackageName() + "@" + i, true).apply();
                            }
                            restartDialogWindowManager3.mOnRestartCallback.accept(Pair.create(taskInfo2, restartDialogWindowManager3.mTaskListener));
                        }
                    };
                    final CheckBox checkBox = (CheckBox) restartDialogLayout3.findViewById(R.id.letterbox_restart_dialog_checkbox);
                    restartDialogLayout3.findViewById(R.id.letterbox_restart_dialog_restart_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.RestartDialogLayout$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            Consumer consumer = r2;
                            CheckBox checkBox2 = checkBox;
                            int i = RestartDialogLayout.$r8$clinit;
                            consumer.accept(Boolean.valueOf(checkBox2.isChecked()));
                        }
                    });
                    restartDialogWindowManager2.mLayout.mDialogTitle.sendAccessibilityEvent(8);
                    break;
                }
                break;
            case 2:
                RestartDialogWindowManager restartDialogWindowManager3 = this.f$0;
                RestartDialogLayout restartDialogLayout4 = restartDialogWindowManager3.mLayout;
                if (restartDialogLayout4 != null) {
                    restartDialogLayout4.setDismissOnClickListener(null);
                    restartDialogWindowManager3.mAnimationController.startExitAnimation(restartDialogWindowManager3.mLayout, new RestartDialogWindowManager$$ExternalSyntheticLambda0(restartDialogWindowManager3, 4));
                    break;
                }
                break;
            case 3:
                this.f$0.release();
                break;
            default:
                RestartDialogWindowManager restartDialogWindowManager4 = this.f$0;
                restartDialogWindowManager4.release();
                restartDialogWindowManager4.mOnDismissCallback.accept(Pair.create(restartDialogWindowManager4.mTaskInfo, restartDialogWindowManager4.mTaskListener));
                break;
        }
    }
}
