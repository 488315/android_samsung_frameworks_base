package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardPreviewSmartspaceViewModel {
    public final ReadonlyStateFlow selectedClockSize;
    public final KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1 shouldHideSmartspace;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockSizeSetting.values().length];
            try {
                iArr[ClockSizeSetting.DYNAMIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockSizeSetting.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KeyguardPreviewSmartspaceViewModel(KeyguardClockInteractor keyguardClockInteractor, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardClockViewModel keyguardClockViewModel) {
        ReadonlyStateFlow readonlyStateFlow = keyguardClockInteractor.selectedClockSize;
        this.selectedClockSize = readonlyStateFlow;
        this.shouldHideSmartspace = new KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, keyguardClockInteractor.currentClockId, KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$2.INSTANCE));
    }

    public static int getSmallClockTopPadding(Context context, boolean z) {
        Resources resources = context.getResources();
        if (z) {
            return resources.getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin);
        }
        return resources.getDimensionPixelSize(R.dimen.keyguard_smartspace_top_offset) + resources.getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard) + resources.getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
    }
}
