package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
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
public final class FromOccludedTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public final KeyguardInteractor keyguardInteractor;
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
        DEFAULT_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(933, durationUnit);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromOccludedTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromOccludedTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        FromOccludedTransitionInteractor$listenForOccludedToLockscreen$1 fromOccludedTransitionInteractor$listenForOccludedToLockscreen$1 = new FromOccludedTransitionInteractor$listenForOccludedToLockscreen$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromOccludedTransitionInteractor$listenForOccludedToLockscreen$1, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToDreaming$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1684x38c7852(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToGone$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1683xda86c284(this, null), 3);
    }
}
