package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.util.Pair;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.compatui.api.CompatUIInfo;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import java.util.HashSet;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CompatUIController f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda2(CompatUIController compatUIController, int i) {
        this.$r8$classId = i;
        this.f$0 = compatUIController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        CompatUIController compatUIController = this.f$0;
        switch (i) {
            case 0:
                compatUIController.mHasShownUserAspectRatioSettingsButton = ((Boolean) obj).booleanValue();
                break;
            case 1:
                compatUIController.onRestartButtonClicked((Pair) obj);
                break;
            case 2:
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = (CompatUIWindowManagerAbstract) obj;
                compatUIController.getClass();
                compatUIWindowManagerAbstract.updateVisibility(compatUIController.showOnDisplay(compatUIWindowManagerAbstract.mDisplayId));
                break;
            case 3:
                Pair pair = (Pair) obj;
                compatUIController.mTaskIdToRestartDialogWindowManagerMap.remove(((TaskInfo) pair.first).taskId);
                compatUIController.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(((TaskInfo) pair.first).taskId));
                break;
            case 4:
                Pair pair2 = (Pair) obj;
                ((HashSet) compatUIController.mSetOfTaskIdsShowingRestartDialog).remove(Integer.valueOf(((TaskInfo) pair2.first).taskId));
                compatUIController.onCompatInfoChanged(new CompatUIInfo((TaskInfo) pair2.first, (ShellTaskOrganizer.TaskListener) pair2.second));
                break;
            case 5:
                Pair pair3 = (Pair) obj;
                compatUIController.getClass();
                compatUIController.createOrUpdateReachabilityEduLayout((TaskInfo) pair3.first, (ShellTaskOrganizer.TaskListener) pair3.second);
                break;
            case 6:
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract2 = (CompatUIWindowManagerAbstract) obj;
                compatUIController.getClass();
                compatUIWindowManagerAbstract2.updateVisibility(compatUIController.showOnDisplay(compatUIWindowManagerAbstract2.mDisplayId));
                break;
            default:
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract3 = (CompatUIWindowManagerAbstract) obj;
                compatUIController.getClass();
                compatUIWindowManagerAbstract3.updateVisibility(compatUIController.showOnDisplay(compatUIWindowManagerAbstract3.mDisplayId));
                break;
        }
    }
}
