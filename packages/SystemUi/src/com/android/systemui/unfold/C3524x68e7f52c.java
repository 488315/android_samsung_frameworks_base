package com.android.systemui.unfold;

import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.unfold.UnfoldTransitionModule$provideStatusBarScopedTransitionProvider$1 */
/* loaded from: classes2.dex */
public final class C3524x68e7f52c implements Function {
    public static final C3524x68e7f52c INSTANCE = new C3524x68e7f52c();

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new ScopedUnfoldTransitionProgressProvider((NaturalRotationUnfoldProgressProvider) obj);
    }
}
