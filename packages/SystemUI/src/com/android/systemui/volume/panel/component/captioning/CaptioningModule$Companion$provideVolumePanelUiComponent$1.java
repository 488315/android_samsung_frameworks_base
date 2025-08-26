package com.android.systemui.volume.panel.component.captioning;

import com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class CaptioningModule$Companion$provideVolumePanelUiComponent$1 extends FunctionReferenceImpl implements Function1 {
    public CaptioningModule$Companion$provideVolumePanelUiComponent$1(Object obj) {
        super(1, obj, CaptioningViewModel.class, "setIsSystemAudioCaptioningEnabled", "setIsSystemAudioCaptioningEnabled(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((CaptioningViewModel) this.receiver).setIsSystemAudioCaptioningEnabled(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
