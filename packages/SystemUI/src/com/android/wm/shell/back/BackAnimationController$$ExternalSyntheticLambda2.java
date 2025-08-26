package com.android.wm.shell.back;

import android.window.BackAnimationAdapter;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final /* synthetic */ class BackAnimationController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BackAnimationController f$0;

    public /* synthetic */ BackAnimationController$$ExternalSyntheticLambda2(BackAnimationController backAnimationController, int i) {
        this.$r8$classId = i;
        this.f$0 = backAnimationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final BackAnimationController backAnimationController = this.f$0;
        switch (i) {
            case 0:
                backAnimationController.mBackAnimationAdapter = new BackAnimationAdapter(new BackAnimationController.AnonymousClass3(backAnimationController));
                Supplier supplier = new Supplier() { // from class: com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        BackAnimationController backAnimationController2 = backAnimationController;
                        backAnimationController2.getClass();
                        return new BackAnimationController.IBackAnimationImpl(backAnimationController2, backAnimationController2);
                    }
                };
                ShellController shellController = backAnimationController.mShellController;
                shellController.addExternalInterface("com.android.wm.shell.back.IBackAnimation", supplier, backAnimationController);
                backAnimationController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        BackAnimationController backAnimationController2 = backAnimationController;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        printWriter.println(str + "BackAnimationController state:");
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append("  mBackGestureStarted=");
                        StringBuilder sbM = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb, backAnimationController2.mBackGestureStarted, printWriter, str, "  mPostCommitAnimationInProgress="), backAnimationController2.mPostCommitAnimationInProgress, printWriter, str, "  mShouldStartOnNextMoveEvent="), backAnimationController2.mShouldStartOnNextMoveEvent, printWriter, str, "  mPointerPilfered="), backAnimationController2.mThresholdCrossed, printWriter, str, "  mRequirePointerPilfer=");
                        sbM.append(backAnimationController2.mRequirePointerPilfer);
                        printWriter.println(sbM.toString());
                        printWriter.println(str + "  mCurrentTracker state:");
                        backAnimationController2.mCurrentTracker.dump(printWriter, str + "    ");
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        CarrierTextController$$ExternalSyntheticOutline0.m(sb2, "  mQueuedTracker state:", printWriter);
                        backAnimationController2.mQueuedTracker.dump(printWriter, str + "    ");
                    }
                }, backAnimationController);
                shellController.addConfigurationChangeListener(backAnimationController);
                break;
            case 1:
                backAnimationController.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                    ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -7068333096785398281L, 1, Long.valueOf(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY));
                }
                backAnimationController.finishBackAnimation();
                break;
            case 2:
                backAnimationController.getClass();
                backAnimationController.mShellExecutor.execute(new BackAnimationController$$ExternalSyntheticLambda2(backAnimationController, 3));
                break;
            default:
                backAnimationController.onBackAnimationFinished();
                break;
        }
    }
}
