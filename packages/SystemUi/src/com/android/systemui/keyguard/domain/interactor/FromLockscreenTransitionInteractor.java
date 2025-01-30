package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FromLockscreenTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;
    public final ShadeRepository shadeRepository;

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
        TO_DREAMING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_OCCLUDED_DURATION = DurationKt.toDuration(450, durationUnit);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromLockscreenTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionRepository keyguardTransitionRepository) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromLockscreenTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeRepository = shadeRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
    }

    /* renamed from: getAnimator-LRDsOJo, reason: not valid java name */
    public final ValueAnimator m1578getAnimatorLRDsOJo(long j) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.m2863getInWholeMillisecondsimpl(j));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        FromLockscreenTransitionInteractor$listenForLockscreenToGone$1 fromLockscreenTransitionInteractor$listenForLockscreenToGone$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromLockscreenTransitionInteractor$listenForLockscreenToGone$1, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1680x21c39b77(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1674xe54481ba(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1681xc4a5954(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1677xcf1fe14b(this, SetsKt__SetsKt.setOf(KeyguardState.AOD, KeyguardState.DOZING), null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1682x1c964403(this, new Ref$ObjectRef(), null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new C1673x58e45a1c(this, null), 3);
    }
}
