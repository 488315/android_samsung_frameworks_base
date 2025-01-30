package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FromPrimaryBouncerTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_GONE_SHORT_DURATION;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        DEFAULT_DURATION = DurationKt.toDuration(300, durationUnit);
        TO_GONE_DURATION = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        TO_GONE_SHORT_DURATION = DurationKt.toDuration(200, durationUnit);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromPrimaryBouncerTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardSecurityModel keyguardSecurityModel) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromPrimaryBouncerTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.keyguardSecurityModel = keyguardSecurityModel;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        C1694x592043cd c1694x592043cd = new C1694x592043cd(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, c1694x592043cd, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1693x832cb018(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1695x3fc45d93(this, null), 3);
    }
}
