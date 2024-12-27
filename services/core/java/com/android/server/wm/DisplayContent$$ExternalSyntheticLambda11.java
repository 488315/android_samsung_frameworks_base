package com.android.server.wm;

import android.util.Slog;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda11(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Set set = (Set) this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                WindowState windowState = (WindowState) obj;
                if (set.contains(Integer.valueOf(windowState.mAttrs.type))) {
                    arrayList.add(windowState.mSurfaceControl);
                    break;
                }
                break;
            default:
                DisplayContent displayContent = (DisplayContent) this.f$0;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.f$1;
                WindowState windowState2 = (WindowState) obj;
                displayContent.getClass();
                WindowStateAnimator windowStateAnimator = windowState2.mWinAnimator;
                if (windowStateAnimator.mSurfaceController != null) {
                    if (!displayContent.mWmService.mSessions.contains(
                            windowStateAnimator.mSession)) {
                        Slog.w(
                                "WindowManager",
                                "LEAKED SURFACE (session doesn't exist): "
                                        + windowState2
                                        + " surface="
                                        + windowStateAnimator.mSurfaceController
                                        + " token="
                                        + windowState2.mToken
                                        + " pid="
                                        + windowState2.mSession.mPid
                                        + " uid="
                                        + windowState2.mSession.mUid);
                        windowStateAnimator.destroySurface(transaction);
                        displayContent.mWmService.mForceRemoves.add(windowState2);
                        displayContent.mTmpWindow = windowState2;
                        break;
                    } else {
                        ActivityRecord activityRecord = windowState2.mActivityRecord;
                        if (activityRecord != null && !activityRecord.mClientVisible) {
                            Slog.w(
                                    "WindowManager",
                                    "LEAKED SURFACE (app token hidden): "
                                            + windowState2
                                            + " surface="
                                            + windowStateAnimator.mSurfaceController
                                            + " token="
                                            + windowState2.mActivityRecord);
                            if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                                ProtoLogImpl_54989576.i(
                                        ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                                        -4130402450005935184L,
                                        0,
                                        null,
                                        String.valueOf(windowState2));
                            }
                            windowStateAnimator.destroySurface(transaction);
                            displayContent.mTmpWindow = windowState2;
                            break;
                        }
                    }
                }
                break;
        }
    }
}
