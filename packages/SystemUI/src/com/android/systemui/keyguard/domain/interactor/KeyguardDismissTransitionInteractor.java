package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardDismissTransitionInteractor {
    public static final String TAG;
    public final FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor;
    public final FromAodTransitionInteractor fromAodTransitionInteractor;
    public final FromDozingTransitionInteractor fromDozingTransitionInteractor;
    public final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor;
    public final FromOccludedTransitionInteractor fromOccludedTransitionInteractor;
    public final FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor;
    public final KeyguardTransitionRepository repository;
    public final CoroutineScope scope;

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
        TAG = Reflection.getOrCreateKotlinClass(KeyguardDismissTransitionInteractor.class).getSimpleName();
    }

    public KeyguardDismissTransitionInteractor(CoroutineScope coroutineScope, KeyguardTransitionRepository keyguardTransitionRepository, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, FromAodTransitionInteractor fromAodTransitionInteractor, FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor, FromDozingTransitionInteractor fromDozingTransitionInteractor, FromOccludedTransitionInteractor fromOccludedTransitionInteractor) {
        this.scope = coroutineScope;
        this.repository = keyguardTransitionRepository;
        this.fromLockscreenTransitionInteractor = fromLockscreenTransitionInteractor;
        this.fromPrimaryBouncerTransitionInteractor = fromPrimaryBouncerTransitionInteractor;
        this.fromAodTransitionInteractor = fromAodTransitionInteractor;
        this.fromAlternateBouncerTransitionInteractor = fromAlternateBouncerTransitionInteractor;
        this.fromDozingTransitionInteractor = fromDozingTransitionInteractor;
        this.fromOccludedTransitionInteractor = fromOccludedTransitionInteractor;
    }

    public static void startDismissKeyguardTransition$default(KeyguardDismissTransitionInteractor keyguardDismissTransitionInteractor, String str) {
        keyguardDismissTransitionInteractor.getClass();
        Log.d(TAG, "#startDismissKeyguardTransition(reason=" + str + ")");
        BuildersKt.launch$default(keyguardDismissTransitionInteractor.scope, null, null, new KeyguardDismissTransitionInteractor$startDismissKeyguardTransition$1(keyguardDismissTransitionInteractor, str, null, null), 3);
    }
}
