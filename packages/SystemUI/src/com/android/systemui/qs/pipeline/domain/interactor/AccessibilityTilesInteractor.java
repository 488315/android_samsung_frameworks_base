package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AccessibilityTilesInteractor {
    public final AccessibilityQsShortcutsRepository a11yQsShortcutsRepository;
    public final CoroutineDispatcher backgroundDispatcher;
    public final AtomicBoolean initialized = new AtomicBoolean(false);
    public final CoroutineScope scope;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Data {
        public final List currentTileSpecs;
        public final Context userContext;

        public Data(List<? extends TileSpec> list, Context context) {
            this.currentTileSpecs = list;
            this.userContext = context;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Data)) {
                return false;
            }
            Data data = (Data) obj;
            return Intrinsics.areEqual(this.currentTileSpecs, data.currentTileSpecs) && Intrinsics.areEqual(this.userContext, data.userContext);
        }

        public final int hashCode() {
            return this.userContext.hashCode() + (this.currentTileSpecs.hashCode() * 31);
        }

        public final String toString() {
            return "Data(currentTileSpecs=" + this.currentTileSpecs + ", userContext=" + this.userContext + ")";
        }
    }

    public AccessibilityTilesInteractor(AccessibilityQsShortcutsRepository accessibilityQsShortcutsRepository, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.a11yQsShortcutsRepository = accessibilityQsShortcutsRepository;
        this.backgroundDispatcher = coroutineDispatcher;
        this.scope = coroutineScope;
    }

    public final void init(CurrentTilesInteractor currentTilesInteractor) {
        if (this.initialized.compareAndSet(false, true)) {
            CoroutineTracingKt.launchTraced$default(this.scope, this.backgroundDispatcher, null, new AccessibilityTilesInteractor$startObservingTiles$1(currentTilesInteractor, this, null), 5);
        }
    }
}
