package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogSettingsButtonViewModel$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(((VolumeDialogSettingsButtonViewModel.PlaybackStates) obj).isCurrentActive);
    }
}
