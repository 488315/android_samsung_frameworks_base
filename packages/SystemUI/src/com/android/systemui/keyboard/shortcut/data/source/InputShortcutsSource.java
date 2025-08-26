package com.android.systemui.keyboard.shortcut.data.source;

import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.hardware.input.KeyGlyphMap;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.model.KeyboardShortcutInfoBuilder;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes2.dex */
public final class InputShortcutsSource implements KeyboardShortcutGroupsSource {
    public final InputManager inputManager;
    public final Resources resources;
    public final WindowManager windowManager;

    /* renamed from: com.android.systemui.keyboard.shortcut.data.source.InputShortcutsSource$shortcutGroups$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return InputShortcutsSource.this.shortcutGroups(0, this);
        }
    }

    public InputShortcutsSource(Resources resources, WindowManager windowManager, InputManager inputManager) {
        this.resources = resources;
        this.windowManager = windowManager;
        this.inputManager = inputManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object shortcutGroups(int i, Continuation continuation) throws Resources.NotFoundException {
        AnonymousClass1 anonymousClass1;
        Iterable iterableSingletonList;
        Collection collection;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            String string = this.resources.getString(R.string.shortcut_helper_category_input);
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.input_switch_input_language_next));
            keyboardShortcutInfoBuilder.modifiers = 4096;
            keyboardShortcutInfoBuilder.keyCode = 62;
            Unit unit = Unit.INSTANCE;
            int i4 = keyboardShortcutInfoBuilder.keyCode;
            int i5 = keyboardShortcutInfoBuilder.modifiers;
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder2 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.input_switch_input_language_previous));
            keyboardShortcutInfoBuilder2.modifiers = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND;
            keyboardShortcutInfoBuilder2.keyCode = 62;
            List listAsList = Arrays.asList(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder.label, i4, i5), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder2.label, keyboardShortcutInfoBuilder2.keyCode, keyboardShortcutInfoBuilder2.modifiers));
            KeyGlyphMap keyGlyphMap = this.inputManager.getKeyGlyphMap(i);
            if (keyGlyphMap == null || !ArraysKt___ArraysKt.contains(317, keyGlyphMap.getFunctionRowKeys())) {
                iterableSingletonList = EmptyList.INSTANCE;
            } else {
                KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder3 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.input_access_emoji));
                keyboardShortcutInfoBuilder3.modifiers = 0;
                keyboardShortcutInfoBuilder3.keyCode = 317;
                iterableSingletonList = Collections.singletonList(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder3.label, keyboardShortcutInfoBuilder3.keyCode, keyboardShortcutInfoBuilder3.modifiers));
            }
            List listSingletonList = Collections.singletonList(new KeyboardShortcutGroup(string, CollectionsKt___CollectionsKt.plus(iterableSingletonList, listAsList)));
            anonymousClass1.L$0 = listSingletonList;
            anonymousClass1.label = 1;
            final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass1), 1);
            cancellableContinuationImpl.initCancellability();
            this.windowManager.requestImeKeyboardShortcuts(new WindowManager.KeyboardShortcutsReceiver() { // from class: com.android.systemui.keyboard.shortcut.data.source.InputShortcutsSource$getImeShortcutGroup$2$shortcutsReceiver$1
                public final void onKeyboardShortcutsReceived(List list) {
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i6 = Result.$r8$clinit;
                    if (list == null) {
                        list = EmptyList.INSTANCE;
                    }
                    cancellableContinuation.resumeWith(list);
                }
            }, i);
            Object result = cancellableContinuationImpl.getResult();
            if (result == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = result;
            collection = listSingletonList;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            collection = (Collection) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) obj, collection);
    }
}
