package com.android.server.wm;

import android.os.IBinder;
import android.service.voice.IVoiceInteractionSession;
import android.util.ArrayMap;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.am.AppTimeTracker;

import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                DisplayContent displayContent = (DisplayContent) obj;
                ((ArrayMap) obj2)
                        .put(
                                Integer.valueOf(displayContent.mDisplayId),
                                Integer.valueOf(displayContent.getImePolicy()));
                break;
            case 1:
                IBinder iBinder = (IBinder) obj2;
                Task task = (Task) obj;
                IVoiceInteractionSession iVoiceInteractionSession = task.voiceSession;
                if (iVoiceInteractionSession != null
                        && iVoiceInteractionSession.asBinder() == iBinder) {
                    task.forAllActivities(new Task$$ExternalSyntheticLambda17(1, task));
                    break;
                } else {
                    PooledPredicate obtainPredicate =
                            PooledLambda.obtainPredicate(
                                    new Task$$ExternalSyntheticLambda29(0),
                                    PooledLambda.__(ActivityRecord.class),
                                    iBinder);
                    task.forAllActivities((Predicate) obtainPredicate);
                    obtainPredicate.recycle();
                    break;
                }
                break;
            case 2:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.appTimeTracker != ((AppTimeTracker) obj2)) {
                    activityRecord.appTimeTracker = null;
                    break;
                }
                break;
            case 3:
                RootWindowContainer.AnonymousClass1 anonymousClass1 =
                        (RootWindowContainer.AnonymousClass1) obj2;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                anonymousClass1.getClass();
                if (!activityRecord2.finishing && activityRecord2.isDestroyable()) {
                    activityRecord2.destroyImmediately(
                            anonymousClass1.this$0.mDestroyAllActivitiesReason);
                    break;
                }
                break;
            default:
                RootWindowContainer.AnonymousClass2 anonymousClass2 =
                        (RootWindowContainer.AnonymousClass2) obj2;
                ActivityRecord activityRecord3 = (ActivityRecord) obj;
                anonymousClass2.getClass();
                if (!activityRecord3.finishing && activityRecord3.isDestroyable()) {
                    WindowProcessController windowProcessController =
                            (WindowProcessController) anonymousClass2.val$rootTask;
                    if (windowProcessController == null
                            || activityRecord3.app == windowProcessController) {
                        activityRecord3.destroyImmediately(
                                (String) anonymousClass2.val$enterPipThrowable);
                        break;
                    }
                }
                break;
        }
    }
}
