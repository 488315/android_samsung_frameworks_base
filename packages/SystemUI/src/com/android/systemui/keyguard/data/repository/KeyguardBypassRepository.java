package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.DevicePosture;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlin.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardBypassRepository extends FlowDumperImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy configFaceAuthSupportedPosture$delegate;
    public final Flow isBypassAvailable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DevicePosture.values().length];
            try {
                iArr[DevicePosture.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KeyguardBypassRepository(final android.content.res.Resources r5, com.android.systemui.keyguard.data.repository.BiometricSettingsRepository r6, com.android.systemui.keyguard.data.repository.DevicePostureRepository r7, com.android.systemui.dump.DumpManager r8, com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository r9, kotlinx.coroutines.CoroutineDispatcher r10) {
        /*
            r4 = this;
            r0 = 0
            r1 = 2
            r4.<init>(r8, r0, r1, r0)
            com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$$ExternalSyntheticLambda0 r8 = new com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$$ExternalSyntheticLambda0
            r2 = 0
            r8.<init>()
            kotlin.Lazy r8 = kotlin.LazyKt__LazyJVMKt.lazy(r8)
            com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$$ExternalSyntheticLambda0 r2 = new com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$$ExternalSyntheticLambda0
            r3 = 1
            r2.<init>()
            kotlin.Lazy r2 = kotlin.LazyKt__LazyJVMKt.lazy(r2)
            r4.configFaceAuthSupportedPosture$delegate = r2
            r3 = 17891761(0x11101b1, float:2.6633507E-38)
            boolean r5 = r5.getBoolean(r3)
            java.lang.String r3 = "face_unlock_dismisses_keyguard"
            kotlinx.coroutines.flow.Flow r5 = r9.boolSetting(r3, r5)
            kotlinx.coroutines.flow.Flow r5 = kotlinx.coroutines.flow.FlowKt.flowOn(r5, r10)
            java.lang.String r9 = "bypassEnabledSetting"
            kotlinx.coroutines.flow.Flow r5 = r4.dumpWhileCollecting(r5, r9)
            java.lang.Object r8 = r8.getValue()
            java.lang.Number r8 = (java.lang.Number) r8
            int r8 = r8.intValue()
            r9 = 1
            if (r8 == r9) goto L4b
            if (r8 == r1) goto L42
            goto L53
        L42:
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r8 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r8.<init>(r5)
        L49:
            r5 = r8
            goto L53
        L4b:
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r8 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r8.<init>(r5)
            goto L49
        L53:
            java.lang.Object r8 = r2.getValue()
            com.android.systemui.keyguard.shared.model.DevicePosture r8 = (com.android.systemui.keyguard.shared.model.DevicePosture) r8
            int[] r10 = com.android.systemui.keyguard.data.repository.KeyguardBypassRepository.WhenMappings.$EnumSwitchMapping$0
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 != r9) goto L6b
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r8 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r8.<init>(r7)
            goto L7a
        L6b:
            com.android.systemui.keyguard.data.repository.DevicePostureRepositoryImpl r7 = (com.android.systemui.keyguard.data.repository.DevicePostureRepositoryImpl) r7
            kotlinx.coroutines.flow.Flow r7 = r7.getCurrentDevicePosture()
            com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$special$$inlined$map$1 r8 = new com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$special$$inlined$map$1
            r8.<init>()
            kotlinx.coroutines.flow.Flow r8 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r8)
        L7a:
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl r6 = (com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl) r6
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.isFaceAuthEnrolledAndEnabled
            com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$isBypassAvailable$1 r7 = new com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$isBypassAvailable$1
            r7.<init>(r0)
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 r5 = kotlinx.coroutines.flow.FlowKt.combine(r5, r6, r8, r7)
            kotlinx.coroutines.flow.Flow r5 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r5)
            java.lang.String r6 = "isBypassAvailable"
            kotlinx.coroutines.flow.Flow r5 = r4.dumpWhileCollecting(r5, r6)
            r4.isBypassAvailable = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.KeyguardBypassRepository.<init>(android.content.res.Resources, com.android.systemui.keyguard.data.repository.BiometricSettingsRepository, com.android.systemui.keyguard.data.repository.DevicePostureRepository, com.android.systemui.dump.DumpManager, com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository, kotlinx.coroutines.CoroutineDispatcher):void");
    }
}
