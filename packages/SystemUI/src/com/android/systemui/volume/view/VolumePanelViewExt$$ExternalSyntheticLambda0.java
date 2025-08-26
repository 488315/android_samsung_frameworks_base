package com.android.systemui.volume.view;

import android.view.View;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelViewExt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        View view = (View) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = VolumePanelViewExt.$r8$clinit;
                return (SubFullLayoutVolumeRowView) view;
            default:
                int i2 = VolumePanelViewExt.$r8$clinit;
                return (VolumeRowView) view;
        }
    }
}
