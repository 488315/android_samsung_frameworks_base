package com.android.systemui.keyboard.shortcut.data.source;

import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AppCategoriesShortcutsSource$shortcutGroups$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $deviceId;
    int label;
    final /* synthetic */ AppCategoriesShortcutsSource this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCategoriesShortcutsSource$shortcutGroups$2(AppCategoriesShortcutsSource appCategoriesShortcutsSource, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appCategoriesShortcutsSource;
        this.$deviceId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppCategoriesShortcutsSource$shortcutGroups$2(this.this$0, this.$deviceId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppCategoriesShortcutsSource$shortcutGroups$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyboardShortcutGroup applicationLaunchKeyboardShortcuts = this.this$0.windowManager.getApplicationLaunchKeyboardShortcuts(this.$deviceId);
        if (applicationLaunchKeyboardShortcuts == null) {
            return EmptyList.INSTANCE;
        }
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(applicationLaunchKeyboardShortcuts.getItems(), new Comparator() { // from class: com.android.systemui.keyboard.shortcut.data.source.AppCategoriesShortcutsSource$shortcutGroups$2$invokeSuspend$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                CharSequence label = ((KeyboardShortcutInfo) obj2).getLabel();
                label.getClass();
                String obj4 = label.toString();
                Locale locale = Locale.ROOT;
                String lowerCase = obj4.toLowerCase(locale);
                CharSequence label2 = ((KeyboardShortcutInfo) obj3).getLabel();
                label2.getClass();
                return ComparisonsKt__ComparisonsKt.compareValues(lowerCase, label2.toString().toLowerCase(locale));
            }
        });
        CharSequence label = applicationLaunchKeyboardShortcuts.getLabel();
        boolean isSystemGroup = applicationLaunchKeyboardShortcuts.isSystemGroup();
        CharSequence packageName = applicationLaunchKeyboardShortcuts.getPackageName();
        KeyboardShortcutGroup keyboardShortcutGroup = new KeyboardShortcutGroup(label, sortedWith, isSystemGroup);
        keyboardShortcutGroup.setPackageName(packageName);
        return Collections.singletonList(keyboardShortcutGroup);
    }
}
