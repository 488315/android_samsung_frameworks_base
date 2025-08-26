package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.SecStatusBarWindowViewTouchedRepository;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SecStatusBarWindowViewTouchedInteractor {
    public final Lazy repository$delegate;

    public SecStatusBarWindowViewTouchedInteractor(final CoroutineScope coroutineScope) {
        this.repository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.domain.interactor.SecStatusBarWindowViewTouchedInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SecStatusBarWindowViewTouchedRepository(coroutineScope);
            }
        });
    }

    public final boolean isTouched() {
        return ((Boolean) ((SecStatusBarWindowViewTouchedRepository) this.repository$delegate.getValue()).isTouched.$$delegate_0.getValue()).booleanValue();
    }
}
