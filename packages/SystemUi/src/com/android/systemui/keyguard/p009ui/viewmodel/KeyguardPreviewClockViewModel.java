package com.android.systemui.keyguard.p009ui.viewmodel;

import android.content.Context;
import com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPreviewClockViewModel {
    public final KeyguardPreviewClockViewModel$special$$inlined$map$1 isLargeClockVisible;
    public final KeyguardPreviewClockViewModel$special$$inlined$map$2 isSmallClockVisible;

    public KeyguardPreviewClockViewModel(Context context, KeyguardClockInteractor keyguardClockInteractor) {
        KeyguardClockRepository$special$$inlined$map$1 keyguardClockRepository$special$$inlined$map$1 = keyguardClockInteractor.selectedClockSize;
        this.isLargeClockVisible = new KeyguardPreviewClockViewModel$special$$inlined$map$1(keyguardClockRepository$special$$inlined$map$1);
        this.isSmallClockVisible = new KeyguardPreviewClockViewModel$special$$inlined$map$2(keyguardClockRepository$special$$inlined$map$1);
    }
}
