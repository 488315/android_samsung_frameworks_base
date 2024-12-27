package com.android.systemui.unfold.config;

import android.content.res.Resources;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ResourceUnfoldTransitionConfig implements UnfoldTransitionConfig {
    public final Lazy isEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$isEnabled$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionEnabled", "bool", "android")));
        }
    });
    public final Lazy isHingeAngleEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$isHingeAngleEnabled$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHingeAngle", "bool", "android")));
        }
    });
    public final Lazy isHapticsEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$isHapticsEnabled$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHapticsEnabled", "bool", "android")));
        }
    });
    public final Lazy halfFoldedTimeoutMillis$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$halfFoldedTimeoutMillis$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(Resources.getSystem().getInteger(Resources.getSystem().getIdentifier("config_unfoldTransitionHalfFoldedTimeout", "integer", "android")));
        }
    });
}
