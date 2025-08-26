package com.airbnb.lottie.compose;

import com.airbnb.lottie.model.KeyPath;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class LottieDynamicProperty {
    public final Function1 callback;
    public final KeyPath keyPath;
    public final Object property;

    public LottieDynamicProperty(Object obj, KeyPath keyPath, Function1 function1) {
        this.property = obj;
        this.keyPath = keyPath;
        this.callback = function1;
    }

    public LottieDynamicProperty(Object obj, KeyPath keyPath, final Object obj2) {
        this(obj, keyPath, new Function1() { // from class: com.airbnb.lottie.compose.LottieDynamicProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj3) {
                return obj2;
            }
        });
    }
}
