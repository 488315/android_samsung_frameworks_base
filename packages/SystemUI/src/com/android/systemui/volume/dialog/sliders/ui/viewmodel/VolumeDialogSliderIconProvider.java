package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.content.Context;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import kotlin.coroutines.CoroutineContext;

/* loaded from: classes3.dex */
public final class VolumeDialogSliderIconProvider {
    public final AudioVolumeInteractor audioVolumeInteractor;
    public final Context context;
    public final CoroutineContext uiBackgroundContext;
    public final ZenModeInteractor zenModeInteractor;

    public VolumeDialogSliderIconProvider(Context context, CoroutineContext coroutineContext, ZenModeInteractor zenModeInteractor, AudioVolumeInteractor audioVolumeInteractor) {
        this.context = context;
        this.uiBackgroundContext = coroutineContext;
        this.zenModeInteractor = zenModeInteractor;
        this.audioVolumeInteractor = audioVolumeInteractor;
    }
}
