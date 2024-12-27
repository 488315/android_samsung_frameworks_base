package com.android.server.wm;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppTransitionController$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AppTransitionController$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WindowState windowState = (WindowState) obj;
                if (windowState.mAnimatingExit) {
                    boolean inTransition = windowState.inTransition();
                    boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled;
                    if (!inTransition) {
                        if (windowState.mDisplayContent.okToAnimate()) {
                            if (!windowState.isAnimationRunningSelfOrParent()) {
                                if (windowState.mDisplayContent.mWallpaperController
                                        .isWallpaperTarget(windowState)) {
                                    if (zArr[0]) {
                                        ProtoLogImpl_54989576.d(
                                                ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                                                -1760879391350377377L,
                                                0,
                                                null,
                                                String.valueOf(windowState));
                                        break;
                                    }
                                }
                            } else if (zArr[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                                        810267895099109466L,
                                        0,
                                        null,
                                        String.valueOf(windowState));
                                break;
                            }
                        }
                        if (zArr[3]) {
                            ProtoLogImpl_54989576.w(
                                    ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                                    272960397873328729L,
                                    0,
                                    null,
                                    String.valueOf(windowState));
                        }
                        windowState.onExitAnimationDone();
                        break;
                    } else if (zArr[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                                7624470121297688739L,
                                0,
                                null,
                                String.valueOf(windowState));
                        break;
                    }
                }
                break;
            default:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (!activityRecord.mIsInputDroppedForAnimation) {
                    activityRecord.mIsInputDroppedForAnimation = true;
                    activityRecord.updateUntrustedEmbeddingInputProtection();
                    break;
                }
                break;
        }
    }
}
