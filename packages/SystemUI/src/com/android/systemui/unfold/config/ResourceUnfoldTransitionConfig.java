package com.android.systemui.unfold.config;

import android.content.res.Resources;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class ResourceUnfoldTransitionConfig implements UnfoldTransitionConfig {
    public final Lazy halfFoldedTimeoutMillis$delegate;
    public final Lazy isEnabled$delegate;
    public final Lazy isHapticsEnabled$delegate;
    public final Lazy isHingeAngleEnabled$delegate;

    public ResourceUnfoldTransitionConfig() {
        final int i = 0;
        this.isEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionEnabled", "bool", "android")));
                    case 1:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHingeAngle", "bool", "android")));
                    case 2:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHapticsEnabled", "bool", "android")));
                    default:
                        return Integer.valueOf(Resources.getSystem().getInteger(Resources.getSystem().getIdentifier("config_unfoldTransitionHalfFoldedTimeout", "integer", "android")));
                }
            }
        });
        final int i2 = 1;
        this.isHingeAngleEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionEnabled", "bool", "android")));
                    case 1:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHingeAngle", "bool", "android")));
                    case 2:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHapticsEnabled", "bool", "android")));
                    default:
                        return Integer.valueOf(Resources.getSystem().getInteger(Resources.getSystem().getIdentifier("config_unfoldTransitionHalfFoldedTimeout", "integer", "android")));
                }
            }
        });
        final int i3 = 2;
        this.isHapticsEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionEnabled", "bool", "android")));
                    case 1:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHingeAngle", "bool", "android")));
                    case 2:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHapticsEnabled", "bool", "android")));
                    default:
                        return Integer.valueOf(Resources.getSystem().getInteger(Resources.getSystem().getIdentifier("config_unfoldTransitionHalfFoldedTimeout", "integer", "android")));
                }
            }
        });
        final int i4 = 3;
        this.halfFoldedTimeoutMillis$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionEnabled", "bool", "android")));
                    case 1:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHingeAngle", "bool", "android")));
                    case 2:
                        return Boolean.valueOf(Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("config_unfoldTransitionHapticsEnabled", "bool", "android")));
                    default:
                        return Integer.valueOf(Resources.getSystem().getInteger(Resources.getSystem().getIdentifier("config_unfoldTransitionHalfFoldedTimeout", "integer", "android")));
                }
            }
        });
    }
}
