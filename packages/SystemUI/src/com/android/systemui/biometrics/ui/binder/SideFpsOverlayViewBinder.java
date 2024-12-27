package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import com.android.systemui.CoreStartable;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

public final class SideFpsOverlayViewBinder implements CoreStartable {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SideFpsOverlayViewBinder(CoroutineScope coroutineScope, Context context, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
