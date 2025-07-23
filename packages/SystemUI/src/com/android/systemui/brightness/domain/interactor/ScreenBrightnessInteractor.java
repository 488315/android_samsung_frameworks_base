package com.android.systemui.brightness.domain.interactor;

import com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository;
import com.android.systemui.brightness.data.repository.ScreenBrightnessRepository;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.shared.model.GammaBrightnessKt;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScreenBrightnessInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow brightnessOverriddenByWindow;
    public final ReadonlyStateFlow gammaBrightness;
    public final ScreenBrightnessRepository screenBrightnessRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ScreenBrightnessInteractor(ScreenBrightnessRepository screenBrightnessRepository, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        this.screenBrightnessRepository = screenBrightnessRepository;
        ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = (ScreenBrightnessDisplayManagerRepository) screenBrightnessRepository;
        this.gammaBrightness = FlowKt.stateIn(GammaBrightnessKt.m1066logDiffForTableGAU2kQA(FlowKt.combine(screenBrightnessDisplayManagerRepository.linearBrightness, screenBrightnessDisplayManagerRepository.minLinearBrightness, screenBrightnessDisplayManagerRepository.maxLinearBrightness, new ScreenBrightnessInteractor$gammaBrightness$1$1(this, null)), tableLogBuffer), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), GammaBrightness.m1064boximpl(0));
        this.brightnessOverriddenByWindow = screenBrightnessDisplayManagerRepository.isBrightnessOverriddenByWindow;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* renamed from: setBrightness-saDbZGg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1061setBrightnesssaDbZGg(int r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setBrightness$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setBrightness$1 r0 = (com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setBrightness$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setBrightness$1 r0 = new com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setBrightness$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.brightness.data.repository.ScreenBrightnessRepository r5 = (com.android.systemui.brightness.data.repository.ScreenBrightnessRepository) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = 0
            r2 = 65535(0xffff, float:9.1834E-41)
            int r6 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r6, r7, r2)
            com.android.systemui.brightness.data.repository.ScreenBrightnessRepository r7 = r5.screenBrightnessRepository
            r0.L$0 = r7
            r0.label = r3
            java.lang.Object r5 = r5.m1063toLinearBrightnesskRMD4pI(r6, r0)
            if (r5 != r1) goto L4b
            return r1
        L4b:
            r4 = r7
            r7 = r5
            r5 = r4
        L4e:
            com.android.systemui.brightness.shared.model.LinearBrightness r7 = (com.android.systemui.brightness.shared.model.LinearBrightness) r7
            float r6 = r7.floatValue
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r5 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository) r5
            kotlinx.coroutines.channels.BufferedChannel r5 = r5.apiQueue
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$SetBrightnessMethod$Permanent r6 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Permanent.m1059boximpl(r6)
            r5.mo3456trySendJP2dKIU(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor.m1061setBrightnesssaDbZGg(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* renamed from: setTemporaryBrightness-saDbZGg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1062setTemporaryBrightnesssaDbZGg(int r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setTemporaryBrightness$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setTemporaryBrightness$1 r0 = (com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setTemporaryBrightness$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setTemporaryBrightness$1 r0 = new com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$setTemporaryBrightness$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.brightness.data.repository.ScreenBrightnessRepository r5 = (com.android.systemui.brightness.data.repository.ScreenBrightnessRepository) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = 0
            r2 = 65535(0xffff, float:9.1834E-41)
            int r6 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r6, r7, r2)
            com.android.systemui.brightness.data.repository.ScreenBrightnessRepository r7 = r5.screenBrightnessRepository
            r0.L$0 = r7
            r0.label = r3
            java.lang.Object r5 = r5.m1063toLinearBrightnesskRMD4pI(r6, r0)
            if (r5 != r1) goto L4b
            return r1
        L4b:
            r4 = r7
            r7 = r5
            r5 = r4
        L4e:
            com.android.systemui.brightness.shared.model.LinearBrightness r7 = (com.android.systemui.brightness.shared.model.LinearBrightness) r7
            float r6 = r7.floatValue
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r5 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository) r5
            kotlinx.coroutines.channels.BufferedChannel r5 = r5.apiQueue
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$SetBrightnessMethod$Temporary r6 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Temporary.m1060boximpl(r6)
            r5.mo3456trySendJP2dKIU(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor.m1062setTemporaryBrightnesssaDbZGg(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* renamed from: toLinearBrightness-kRMD4pI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1063toLinearBrightnesskRMD4pI(int r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$toLinearBrightness$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$toLinearBrightness$1 r0 = (com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$toLinearBrightness$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$toLinearBrightness$1 r0 = new com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor$toLinearBrightness$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            int r5 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L29:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.I$0 = r5
            r0.label = r3
            com.android.systemui.brightness.data.repository.ScreenBrightnessRepository r4 = r4.screenBrightnessRepository
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r4 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository) r4
            java.lang.Object r6 = r4.getMinMaxLinearBrightness(r0)
            if (r6 != r1) goto L43
            return r1
        L43:
            kotlin.Pair r6 = (kotlin.Pair) r6
            java.lang.Object r4 = r6.getFirst()
            com.android.systemui.brightness.shared.model.LinearBrightness r4 = (com.android.systemui.brightness.shared.model.LinearBrightness) r4
            float r4 = r4.floatValue
            java.lang.Object r6 = r6.getSecond()
            com.android.systemui.brightness.shared.model.LinearBrightness r6 = (com.android.systemui.brightness.shared.model.LinearBrightness) r6
            float r6 = r6.floatValue
            float r4 = com.android.settingslib.display.BrightnessUtils.convertGammaToLinearFloat(r4, r6, r5)
            com.android.systemui.brightness.shared.model.LinearBrightness r4 = com.android.systemui.brightness.shared.model.LinearBrightness.m1067boximpl(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor.m1063toLinearBrightnesskRMD4pI(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
