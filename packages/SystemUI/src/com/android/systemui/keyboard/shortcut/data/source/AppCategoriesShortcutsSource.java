package com.android.systemui.keyboard.shortcut.data.source;

import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.WindowManager;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class AppCategoriesShortcutsSource implements KeyboardShortcutGroupsSource {
    public final CoroutineDispatcher backgroundDispatcher;
    public final WindowManager windowManager;

    /* renamed from: com.android.systemui.keyboard.shortcut.data.source.AppCategoriesShortcutsSource$shortcutGroups$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $deviceId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$deviceId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AppCategoriesShortcutsSource.this.new AnonymousClass2(this.$deviceId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            KeyboardShortcutGroup applicationLaunchKeyboardShortcuts = AppCategoriesShortcutsSource.this.windowManager.getApplicationLaunchKeyboardShortcuts(this.$deviceId);
            if (applicationLaunchKeyboardShortcuts == null) {
                return EmptyList.INSTANCE;
            }
            List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(applicationLaunchKeyboardShortcuts.getItems(), new Comparator() { // from class: com.android.systemui.keyboard.shortcut.data.source.AppCategoriesShortcutsSource$shortcutGroups$2$invokeSuspend$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    CharSequence label = ((KeyboardShortcutInfo) obj2).getLabel();
                    label.getClass();
                    String string = label.toString();
                    Locale locale = Locale.ROOT;
                    String lowerCase = string.toLowerCase(locale);
                    CharSequence label2 = ((KeyboardShortcutInfo) obj3).getLabel();
                    label2.getClass();
                    return ComparisonsKt__ComparisonsKt.compareValues(lowerCase, label2.toString().toLowerCase(locale));
                }
            });
            CharSequence label = applicationLaunchKeyboardShortcuts.getLabel();
            boolean zIsSystemGroup = applicationLaunchKeyboardShortcuts.isSystemGroup();
            CharSequence packageName = applicationLaunchKeyboardShortcuts.getPackageName();
            KeyboardShortcutGroup keyboardShortcutGroup = new KeyboardShortcutGroup(label, listSortedWith, zIsSystemGroup);
            keyboardShortcutGroup.setPackageName(packageName);
            return Collections.singletonList(keyboardShortcutGroup);
        }
    }

    public AppCategoriesShortcutsSource(WindowManager windowManager, CoroutineDispatcher coroutineDispatcher) {
        this.windowManager = windowManager;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    public final Object shortcutGroups(int i, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(i, null), continuation);
    }
}
