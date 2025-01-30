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
public final class FromDreamingTransitionInteractor extends TransitionInteractor {
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
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(1167, durationUnit);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromDreamingTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromDreamingTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        FromDreamingTransitionInteractor$listenForDreamingToOccluded$1 fromDreamingTransitionInteractor$listenForDreamingToOccluded$1 = new FromDreamingTransitionInteractor$listenForDreamingToOccluded$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromDreamingTransitionInteractor$listenForDreamingToOccluded$1, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToGone$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToDozing$1(this, null), 3);
    }

    public final void startToLockscreenTransition() {
        BuildersKt.launch$default(this.scope, null, null, new FromDreamingTransitionInteractor$startToLockscreenTransition$1(this, null), 3);
    }
}
