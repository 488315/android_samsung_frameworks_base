package com.android.systemui.flags;

import com.android.systemui.Dependency;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RefactorFlag {
    public static final Companion Companion = new Companion(null);
    public final Object flagName;
    public final FeatureFlags injectedFlags;
    public final Lazy isEnabled$delegate;
    public final Function1 readFlagValue;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static RefactorFlag forView$default(Companion companion, final UnreleasedFlag unreleasedFlag) {
            companion.getClass();
            return new RefactorFlag(null, unreleasedFlag, new Function1() { // from class: com.android.systemui.flags.RefactorFlag$Companion$forView$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Boolean.FALSE;
                }
            }, 0 == true ? 1 : 0);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ RefactorFlag(FeatureFlags featureFlags, Object obj, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(featureFlags, obj, function1);
    }

    private RefactorFlag(FeatureFlags featureFlags, Object obj, Function1 function1) {
        this.injectedFlags = featureFlags;
        this.flagName = obj;
        this.readFlagValue = function1;
        this.isEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.flags.RefactorFlag$isEnabled$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FeatureFlags featureFlags2 = RefactorFlag.this.injectedFlags;
                if (featureFlags2 == null) {
                    featureFlags2 = (FeatureFlags) Dependency.sDependency.getDependencyInner(FeatureFlags.class);
                }
                Function1 function12 = RefactorFlag.this.readFlagValue;
                Intrinsics.checkNotNull(featureFlags2);
                return (Boolean) function12.invoke(featureFlags2);
            }
        });
    }

    public RefactorFlag(FeatureFlags featureFlags, final UnreleasedFlag unreleasedFlag) {
        this(featureFlags, unreleasedFlag, new Function1() { // from class: com.android.systemui.flags.RefactorFlag.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.FALSE;
            }
        });
    }

    public RefactorFlag(FeatureFlags featureFlags, final ReleasedFlag releasedFlag) {
        this(featureFlags, releasedFlag, new Function1() { // from class: com.android.systemui.flags.RefactorFlag.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((FeatureFlagsClassicRelease) ((FeatureFlags) obj)).isEnabled(ReleasedFlag.this));
            }
        });
    }
}
