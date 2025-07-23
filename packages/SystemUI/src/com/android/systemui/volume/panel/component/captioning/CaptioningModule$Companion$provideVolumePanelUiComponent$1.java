package com.android.systemui.volume.panel.component.captioning;

import com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class CaptioningModule$Companion$provideVolumePanelUiComponent$1 extends FunctionReferenceImpl implements Function1 {
    public CaptioningModule$Companion$provideVolumePanelUiComponent$1(Object obj) {
        super(1, obj, CaptioningViewModel.class, "setIsSystemAudioCaptioningEnabled", "setIsSystemAudioCaptioningEnabled(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((CaptioningViewModel) this.receiver).setIsSystemAudioCaptioningEnabled(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
