package com.android.systemui.brightness.domain.interactor;

import com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository;
import com.android.systemui.brightness.data.repository.ScreenBrightnessRepository;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.shared.model.GammaBrightnessKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ScreenBrightnessInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow gammaBrightness;
    public final int maxGammaBrightness = CustomDeviceManager.QUICK_PANEL_ALL;
    public final ScreenBrightnessRepository screenBrightnessRepository;

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

    public ScreenBrightnessInteractor(ScreenBrightnessRepository screenBrightnessRepository, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = (ScreenBrightnessDisplayManagerRepository) screenBrightnessRepository;
        this.gammaBrightness = FlowKt.stateIn(GammaBrightnessKt.m934logDiffForTableGAU2kQA(FlowKt.combine(screenBrightnessDisplayManagerRepository.linearBrightness, screenBrightnessDisplayManagerRepository.minLinearBrightness, screenBrightnessDisplayManagerRepository.maxLinearBrightness, new ScreenBrightnessInteractor$gammaBrightness$1$1(this, null)), tableLogBuffer), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), GammaBrightness.m933boximpl(0));
    }
}
