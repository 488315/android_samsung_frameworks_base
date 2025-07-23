package com.android.systemui.flags;

import com.android.systemui.Dependency;
import com.android.systemui.flags.RefactorFlag;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RefactorFlag {
    public static final Companion Companion = new Companion(null);
    public final Object flagName;
    public final FeatureFlags injectedFlags;
    public final Lazy isEnabled$delegate;
    public final Function1 readFlagValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ RefactorFlag(FeatureFlags featureFlags, Object obj, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(featureFlags, obj, function1);
    }

    private RefactorFlag(FeatureFlags featureFlags, Object obj, Function1 function1) {
        this.injectedFlags = featureFlags;
        this.flagName = obj;
        this.readFlagValue = function1;
        this.isEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.flags.RefactorFlag$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlag refactorFlag = RefactorFlag.this;
                FeatureFlags featureFlags2 = refactorFlag.injectedFlags;
                if (featureFlags2 == null) {
                    featureFlags2 = (FeatureFlags) Dependency.sDependency.getDependencyInner(FeatureFlags.class);
                }
                featureFlags2.getClass();
                Boolean bool = (Boolean) refactorFlag.readFlagValue.mo779invoke(featureFlags2);
                bool.booleanValue();
                return bool;
            }
        });
    }

    public RefactorFlag(FeatureFlags featureFlags, UnreleasedFlag unreleasedFlag) {
        this(featureFlags, unreleasedFlag, new RefactorFlag$$ExternalSyntheticLambda0(0));
    }

    public RefactorFlag(FeatureFlags featureFlags, final ReleasedFlag releasedFlag) {
        this(featureFlags, releasedFlag, new Function1() { // from class: com.android.systemui.flags.RefactorFlag$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                RefactorFlag.Companion companion = RefactorFlag.Companion;
                return Boolean.valueOf(((FeatureFlagsClassicRelease) ((FeatureFlags) obj)).isEnabled(ReleasedFlag.this));
            }
        });
    }
}
