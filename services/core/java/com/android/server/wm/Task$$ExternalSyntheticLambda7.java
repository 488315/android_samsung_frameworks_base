package com.android.server.wm;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.IBinder;

import com.android.internal.util.function.TriPredicate;
import com.android.modules.utils.TypedXmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class Task$$ExternalSyntheticLambda7 implements TriPredicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Task$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    public final boolean test(Object obj, Object obj2, Object obj3) {
        ActivityManager.TaskDescription taskDescription;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                ActivityRecord activityRecord2 = (ActivityRecord) obj2;
                ActivityManager.TaskDescription taskDescription2 =
                        (ActivityManager.TaskDescription) obj3;
                if (!activityRecord.mTaskOverlay
                        && (taskDescription = activityRecord.taskDescription) != null) {
                    if (taskDescription2.getLabel() == null) {
                        taskDescription2.setLabel(taskDescription.getLabel());
                    }
                    if (taskDescription2.getRawIcon() == null) {
                        taskDescription2.setIcon(taskDescription.getRawIcon());
                    }
                    if (taskDescription2.getIconFilename() == null) {
                        taskDescription2.setIconFilename(taskDescription.getIconFilename());
                    }
                    if (!taskDescription2.getColorsAreDetermined()) {
                        if (taskDescription2.getPrimaryColor() == 0) {
                            taskDescription2.setPrimaryColor(taskDescription.getPrimaryColor());
                        }
                        if (taskDescription2.getBackgroundColor() == 0) {
                            taskDescription2.setBackgroundColor(
                                    taskDescription.getBackgroundColor());
                        }
                        if (taskDescription2.getStatusBarColor() == 0) {
                            taskDescription2.setStatusBarColor(taskDescription.getStatusBarColor());
                            taskDescription2.setEnsureStatusBarContrastWhenTransparent(
                                    taskDescription.getEnsureStatusBarContrastWhenTransparent());
                        }
                        if (taskDescription2.getSystemBarsAppearance() == 0) {
                            taskDescription2.setSystemBarsAppearance(
                                    taskDescription.getSystemBarsAppearance());
                        }
                        if (taskDescription2.getTopOpaqueSystemBarsAppearance() == 0
                                && activityRecord.occludesParent(true)) {
                            taskDescription2.setTopOpaqueSystemBarsAppearance(
                                    taskDescription.getSystemBarsAppearance());
                        }
                        if (taskDescription2.getNavigationBarColor() == 0) {
                            taskDescription2.setNavigationBarColor(
                                    taskDescription.getNavigationBarColor());
                            taskDescription2.setEnsureNavigationBarContrastWhenTransparent(
                                    taskDescription
                                            .getEnsureNavigationBarContrastWhenTransparent());
                        }
                        if (taskDescription2.getBackgroundColorFloating() == 0) {
                            taskDescription2.setBackgroundColorFloating(
                                    taskDescription.getBackgroundColorFloating());
                        }
                        if (activityRecord.occludesParent(false)) {
                            taskDescription2.setColorsAreDetermined();
                        }
                    }
                }
                if (activityRecord == activityRecord2) {
                    break;
                }
                break;
            case 1:
                IBinder iBinder = (IBinder) obj3;
                if (activityRecord.task.mTaskId == ((Integer) obj2).intValue()
                        || activityRecord.token == iBinder
                        || !activityRecord.canBeTopRunning()) {}
                break;
            case 2:
                ActivityRecord activityRecord3 = (ActivityRecord) obj2;
                TypedXmlSerializer typedXmlSerializer = (TypedXmlSerializer) obj3;
                if (activityRecord.info.persistableMode != 0 && activityRecord.isPersistable()) {
                    if (((activityRecord.intent.getFlags() & 524288) | 8192) != 524288
                            || activityRecord == activityRecord3) {
                        try {
                            typedXmlSerializer.startTag((String) null, "activity");
                            activityRecord.saveToXml(typedXmlSerializer);
                            typedXmlSerializer.endTag((String) null, "activity");
                            break;
                        } catch (Exception e) {
                            Task.sTmpException = e;
                            return true;
                        }
                    }
                }
                break;
            default:
                ComponentName componentName = (ComponentName) obj2;
                int intValue = ((Integer) obj3).intValue();
                if (activityRecord.finishing
                        || !activityRecord.mActivityComponent.equals(componentName)
                        || activityRecord.mUserId != intValue) {}
                break;
        }
        return true;
    }
}
