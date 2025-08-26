package com.android.systemui.qs.animator;

import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* loaded from: classes2.dex */
public final class QsAnimatorState {
    public static boolean expandedByNotiOverScroll;
    public static boolean isCustomizerShowing;
    public static boolean isDetailClosing;
    public static boolean isDetailOpening;
    public static boolean isDetailPopupShowing;
    public static boolean isDetailShowing;
    public static boolean isNotificationImmersiceScrolling;
    public static boolean isSliding;
    public static boolean panelExpanded;
    public static boolean qsExpanded;
    public static int state;
    public static final QsAnimatorState INSTANCE = new QsAnimatorState();
    public static final HashMap animViewStateMap = new HashMap();
    public static final Lazy qsExpansionStateInteractor$delegate = LazyKt__LazyJVMKt.lazy(new QsAnimatorState$$ExternalSyntheticLambda0());
    public static boolean isDetailPopupClosing = true;

    private QsAnimatorState() {
    }

    public static final void setDetailShowing(boolean z) {
        INSTANCE.getClass();
        ((SecQSExpansionStateInteractor) qsExpansionStateInteractor$delegate.getValue()).getRepository()._isDetailShowing.updateState(null, Boolean.valueOf(z));
        isDetailShowing = z;
    }
}
