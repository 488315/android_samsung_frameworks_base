package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class IconLabelVisibilityInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer logBuffer;
    public final QSPreferencesInteractor preferencesInteractor;
    public final ReadonlyStateFlow showLabels;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public IconLabelVisibilityInteractor(QSPreferencesInteractor qSPreferencesInteractor, LogBuffer logBuffer, CoroutineScope coroutineScope) {
        this.logBuffer = logBuffer;
        this.showLabels = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(qSPreferencesInteractor.showLabels, new IconLabelVisibilityInteractor$showLabels$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }
}
