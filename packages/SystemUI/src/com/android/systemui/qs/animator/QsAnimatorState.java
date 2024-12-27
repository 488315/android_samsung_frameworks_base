package com.android.systemui.qs.animator;

import com.android.systemui.Dependency;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class QsAnimatorState {
    public static boolean detailTriggeredExpand;
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
    public static final Lazy qsExpansionStateInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.animator.QsAnimatorState$qsExpansionStateInteractor$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
        }
    });

    private QsAnimatorState() {
    }
}
