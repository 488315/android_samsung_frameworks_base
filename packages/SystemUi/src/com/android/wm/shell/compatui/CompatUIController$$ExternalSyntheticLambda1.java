package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.SharedPreferences;
import android.util.Pair;
import com.android.wm.shell.ShellTaskOrganizer;
import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CompatUIController f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda1(CompatUIController compatUIController, int i) {
        this.$r8$classId = i;
        this.f$0 = compatUIController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                CompatUIController compatUIController = this.f$0;
                Pair pair = (Pair) obj;
                compatUIController.mActiveLetterboxEduLayout = null;
                compatUIController.createOrUpdateReachabilityEduLayout((TaskInfo) pair.first, (ShellTaskOrganizer.TaskListener) pair.second);
                break;
            case 1:
                CompatUIController compatUIController2 = this.f$0;
                Pair pair2 = (Pair) obj;
                CompatUIConfiguration compatUIConfiguration = compatUIController2.mCompatUIConfiguration;
                if (compatUIConfiguration.mIsRestartDialogOverrideEnabled || (compatUIConfiguration.mIsRestartDialogEnabled && compatUIConfiguration.mIsLetterboxRestartDialogAllowed)) {
                    TaskInfo taskInfo = (TaskInfo) pair2.first;
                    SharedPreferences sharedPreferences = compatUIConfiguration.mCompatUISharedPreferences;
                    int i = taskInfo.userId;
                    String packageName = taskInfo.topActivity.getPackageName();
                    if (!sharedPreferences.getBoolean(packageName + "@" + i, false)) {
                        ((HashSet) compatUIController2.mSetOfTaskIdsShowingRestartDialog).add(Integer.valueOf(((TaskInfo) pair2.first).taskId));
                        compatUIController2.onCompatInfoChanged((TaskInfo) pair2.first, (ShellTaskOrganizer.TaskListener) pair2.second);
                        break;
                    }
                }
                ((ShellTaskOrganizer) compatUIController2.mCallback).onSizeCompatRestartButtonClicked(((TaskInfo) pair2.first).taskId);
                break;
            case 2:
                CompatUIController compatUIController3 = this.f$0;
                Pair pair3 = (Pair) obj;
                compatUIController3.mTaskIdToRestartDialogWindowManagerMap.remove(((TaskInfo) pair3.first).taskId);
                ((ShellTaskOrganizer) compatUIController3.mCallback).onSizeCompatRestartButtonClicked(((TaskInfo) pair3.first).taskId);
                break;
            case 3:
                CompatUIController compatUIController4 = this.f$0;
                Pair pair4 = (Pair) obj;
                ((HashSet) compatUIController4.mSetOfTaskIdsShowingRestartDialog).remove(Integer.valueOf(((TaskInfo) pair4.first).taskId));
                compatUIController4.onCompatInfoChanged((TaskInfo) pair4.first, (ShellTaskOrganizer.TaskListener) pair4.second);
                break;
            case 4:
                CompatUIController compatUIController5 = this.f$0;
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = (CompatUIWindowManagerAbstract) obj;
                compatUIController5.getClass();
                compatUIWindowManagerAbstract.updateVisibility(compatUIController5.showOnDisplay(compatUIWindowManagerAbstract.mDisplayId));
                break;
            case 5:
                CompatUIController compatUIController6 = this.f$0;
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract2 = (CompatUIWindowManagerAbstract) obj;
                compatUIController6.getClass();
                compatUIWindowManagerAbstract2.updateVisibility(compatUIController6.showOnDisplay(compatUIWindowManagerAbstract2.mDisplayId));
                break;
            default:
                CompatUIController compatUIController7 = this.f$0;
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract3 = (CompatUIWindowManagerAbstract) obj;
                compatUIController7.getClass();
                compatUIWindowManagerAbstract3.updateVisibility(compatUIController7.showOnDisplay(compatUIWindowManagerAbstract3.mDisplayId));
                break;
        }
    }
}
