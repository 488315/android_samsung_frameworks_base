package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.KeyGlyphMap;
import android.view.InputDevice;
import com.android.systemui.keyboard.shortcut.shared.model.KeyCombination;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomShortcutCategoriesRepository$pressedKeys$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CustomShortcutCategoriesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomShortcutCategoriesRepository$pressedKeys$1(CustomShortcutCategoriesRepository customShortcutCategoriesRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = customShortcutCategoriesRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CustomShortcutCategoriesRepository$pressedKeys$1 customShortcutCategoriesRepository$pressedKeys$1 = new CustomShortcutCategoriesRepository$pressedKeys$1(this.this$0, (Continuation) obj3);
        customShortcutCategoriesRepository$pressedKeys$1.L$0 = (KeyCombination) obj;
        customShortcutCategoriesRepository$pressedKeys$1.L$1 = (InputDevice) obj2;
        return customShortcutCategoriesRepository$pressedKeys$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ShortcutKey shortcutKey;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyCombination keyCombination = (KeyCombination) this.L$0;
        InputDevice inputDevice = (InputDevice) this.L$1;
        if (inputDevice == null || keyCombination == null) {
            return EmptyList.INSTANCE;
        }
        CustomShortcutCategoriesRepository customShortcutCategoriesRepository = this.this$0;
        KeyGlyphMap keyGlyphMap = customShortcutCategoriesRepository.inputManager.getKeyGlyphMap(inputDevice.getId());
        List shortcutModifierKeys = this.this$0.shortcutCategoriesUtils.toShortcutModifierKeys(keyCombination.modifiers, keyGlyphMap);
        Integer num = keyCombination.keyCode;
        if (num != null) {
            CustomShortcutCategoriesRepository customShortcutCategoriesRepository2 = this.this$0;
            shortcutKey = customShortcutCategoriesRepository2.shortcutCategoriesUtils.toShortcutKey(keyGlyphMap, inputDevice.getKeyCharacterMap(), num.intValue(), (char) 0);
        } else {
            shortcutKey = null;
        }
        ArrayList arrayList = new ArrayList();
        if (shortcutModifierKeys != null) {
            CollectionsKt__MutableCollectionsKt.addAll(shortcutModifierKeys, arrayList);
        }
        if (shortcutKey != null) {
            arrayList.add(shortcutKey);
        }
        return arrayList;
    }
}
