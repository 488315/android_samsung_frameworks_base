package com.android.dream.lowlight;

import android.app.DreamManager;
import android.content.ComponentName;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;

/* loaded from: classes.dex */
public final class LowLightDreamManager {
    public static final boolean DEBUG;
    public final CoroutineScope coroutineScope;
    public final DreamManager dreamManager;
    public final ComponentName lowLightDreamComponent;
    public final LowLightTransitionCoordinator lowLightTransitionCoordinator;
    public int mAmbientLightMode;
    public final long mLowLightTransitionTimeout;
    public StandaloneCoroutine mTransitionJob;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.dream.lowlight.LowLightDreamManager$setAmbientLightMode$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $shouldEnterLowLight;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$shouldEnterLowLight = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LowLightDreamManager.this.new AnonymousClass1(this.$shouldEnterLowLight, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LowLightDreamManager lowLightDreamManager = LowLightDreamManager.this;
                    LowLightTransitionCoordinator lowLightTransitionCoordinator = lowLightDreamManager.lowLightTransitionCoordinator;
                    long j = lowLightDreamManager.mLowLightTransitionTimeout;
                    boolean z = this.$shouldEnterLowLight;
                    this.label = 1;
                    lowLightTransitionCoordinator.getClass();
                    Object objM3470withTimeoutKLykuaI = TimeoutKt.m3470withTimeoutKLykuaI(j, new LowLightTransitionCoordinator$waitForLowLightTransitionAnimationKLykuaI$$inlined$suspendCoroutineWithTimeoutKLykuaI$1(null, z, lowLightTransitionCoordinator), this);
                    if (objM3470withTimeoutKLykuaI != obj2) {
                        objM3470withTimeoutKLykuaI = Unit.INSTANCE;
                    }
                    if (objM3470withTimeoutKLykuaI == obj2) {
                        return obj2;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (TimeoutCancellationException e) {
                Log.e("LowLightDreamManager", "timed out while waiting for low light animation", e);
            } catch (CancellationException unused) {
                Log.w("LowLightDreamManager", "low light transition animation cancelled");
            }
            LowLightDreamManager lowLightDreamManager2 = LowLightDreamManager.this;
            lowLightDreamManager2.dreamManager.setSystemDreamComponent(this.$shouldEnterLowLight ? lowLightDreamManager2.lowLightDreamComponent : null);
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
        DEBUG = Log.isLoggable("LowLightDreamManager", 3);
    }

    public LowLightDreamManager(CoroutineScope coroutineScope, DreamManager dreamManager, LowLightTransitionCoordinator lowLightTransitionCoordinator, ComponentName componentName, long j) {
        this.coroutineScope = coroutineScope;
        this.dreamManager = dreamManager;
        this.lowLightTransitionCoordinator = lowLightTransitionCoordinator;
        this.lowLightDreamComponent = componentName;
        this.mLowLightTransitionTimeout = DurationKt.toDuration(j, DurationUnit.MILLISECONDS);
    }

    public final void setAmbientLightMode(int i) {
        ComponentName componentName = this.lowLightDreamComponent;
        boolean z = DEBUG;
        if (componentName == null) {
            if (z) {
                Log.d("LowLightDreamManager", "ignore ambient light mode change because low light dream component is empty");
                return;
            }
            return;
        }
        int i2 = this.mAmbientLightMode;
        if (i2 == i) {
            return;
        }
        if (z) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i, "ambient light mode changed from ", " to ", "LowLightDreamManager");
        }
        this.mAmbientLightMode = i;
        boolean z2 = i == 2;
        StandaloneCoroutine standaloneCoroutine = this.mTransitionJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.mTransitionJob = BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(z2, null), 3);
    }
}
