package com.android.systemui.keyboard.shortcut.data.source;

import android.view.WindowManager;
import java.util.List;
import kotlin.Result;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes2.dex */
public final class CurrentAppShortcutsSource implements KeyboardShortcutGroupsSource {
    public final WindowManager windowManager;

    public CurrentAppShortcutsSource(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    public final Object shortcutGroups(int i, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        this.windowManager.requestAppKeyboardShortcuts(new WindowManager.KeyboardShortcutsReceiver() { // from class: com.android.systemui.keyboard.shortcut.data.source.CurrentAppShortcutsSource$shortcutGroups$2$shortcutsReceiver$1
            public final void onKeyboardShortcutsReceived(List list) {
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                int i2 = Result.$r8$clinit;
                if (list == null) {
                    list = EmptyList.INSTANCE;
                }
                cancellableContinuation.resumeWith(list);
            }
        }, i);
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }
}
