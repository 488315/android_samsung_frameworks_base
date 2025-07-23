package com.android.dream.lowlight;

import android.app.DreamManager;
import android.content.ComponentName;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        this.mTransitionJob = BuildersKt.launch$default(this.coroutineScope, null, null, new LowLightDreamManager$setAmbientLightMode$1(this, z2, null), 3);
    }
}
