package com.android.systemui.multishade.domain.interactor;

import com.android.systemui.multishade.shared.model.ProxiedInputModel;
import com.android.systemui.multishade.shared.model.ShadeConfig;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class MultiShadeInteractor$processedProxiedInput$2 extends AdaptedFunctionReference implements Function3 {
    public static final MultiShadeInteractor$processedProxiedInput$2 INSTANCE = new MultiShadeInteractor$processedProxiedInput$2();

    public MultiShadeInteractor$processedProxiedInput$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((ShadeConfig) obj, (ProxiedInputModel) obj2);
    }
}
