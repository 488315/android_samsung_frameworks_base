package com.android.systemui.qs.bar;

import com.android.systemui.qs.bar.VideoCallMicModeBar;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class VideoCallMicModeBar$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        VideoCallMicModeBar.VideoCallMicModeBarBase videoCallMicModeBarBase = (VideoCallMicModeBar.VideoCallMicModeBarBase) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(videoCallMicModeBarBase.isEnabled());
            case 1:
                return Boolean.valueOf(videoCallMicModeBarBase.isEnabled());
            default:
                return videoCallMicModeBarBase.getButton();
        }
    }
}
