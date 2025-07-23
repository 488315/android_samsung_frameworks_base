package com.android.systemui.keyboard.shortcut.data.repository;

import android.view.InputDevice;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomShortcutCategoriesRepository$categories$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CustomShortcutCategoriesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomShortcutCategoriesRepository$categories$1(CustomShortcutCategoriesRepository customShortcutCategoriesRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = customShortcutCategoriesRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CustomShortcutCategoriesRepository$categories$1 customShortcutCategoriesRepository$categories$1 = new CustomShortcutCategoriesRepository$categories$1(this.this$0, (Continuation) obj3);
        customShortcutCategoriesRepository$categories$1.L$0 = (InputDevice) obj;
        customShortcutCategoriesRepository$categories$1.L$1 = (List) obj2;
        return customShortcutCategoriesRepository$categories$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01cc  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r25) {
        /*
            Method dump skipped, instructions count: 650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$categories$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
