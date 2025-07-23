package androidx.navigation;

import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NavController$executePopOperations$1 extends Lambda implements Function1 {
    final /* synthetic */ Ref$BooleanRef $popped;
    final /* synthetic */ Ref$BooleanRef $receivedPop;
    final /* synthetic */ boolean $saveState;
    final /* synthetic */ ArrayDeque $savedState;
    final /* synthetic */ NavController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavController$executePopOperations$1(Ref$BooleanRef ref$BooleanRef, Ref$BooleanRef ref$BooleanRef2, NavController navController, boolean z, ArrayDeque arrayDeque) {
        super(1);
        this.$receivedPop = ref$BooleanRef;
        this.$popped = ref$BooleanRef2;
        this.this$0 = navController;
        this.$saveState = z;
        this.$savedState = arrayDeque;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        this.$receivedPop.element = true;
        this.$popped.element = true;
        NavController navController = this.this$0;
        boolean z = this.$saveState;
        ArrayDeque arrayDeque = this.$savedState;
        boolean z2 = NavController.deepLinkSaveState;
        navController.popEntryFromBackStack((NavBackStackEntry) obj, z, arrayDeque);
        return Unit.INSTANCE;
    }
}
