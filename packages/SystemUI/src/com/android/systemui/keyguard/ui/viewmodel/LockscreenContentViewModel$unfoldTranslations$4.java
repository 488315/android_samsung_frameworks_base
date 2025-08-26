package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class LockscreenContentViewModel$unfoldTranslations$4 extends AdaptedFunctionReference implements Function3 {
    public static final LockscreenContentViewModel$unfoldTranslations$4 INSTANCE = new LockscreenContentViewModel$unfoldTranslations$4();

    public LockscreenContentViewModel$unfoldTranslations$4() {
        super(3, LockscreenContentViewModel.UnfoldTranslations.class, "<init>", "<init>(FF)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new LockscreenContentViewModel.UnfoldTranslations(((Number) obj).floatValue(), ((Number) obj2).floatValue());
    }
}
