package com.android.systemui.statusbar.phone.ongoingactivity;

import android.view.View;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingActivityController$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OngoingActivityController f$0;

    public /* synthetic */ OngoingActivityController$$ExternalSyntheticLambda2(OngoingActivityController ongoingActivityController, int i) {
        this.$r8$classId = i;
        this.f$0 = ongoingActivityController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                OngoingActivityController ongoingActivityController = this.f$0;
                AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isCallConnected ongoingCallController.hasOngoingCall():", "{OngoingActivityController}", ongoingActivityController.ongoingCallController.hasOngoingCall());
                return Boolean.valueOf(ongoingActivityController.ongoingCallController.hasOngoingCall());
            case 1:
                this.f$0.mMediaCardView = (View) obj;
                return Unit.INSTANCE;
            case 2:
                return this.f$0.mMediaCardView;
            default:
                return Boolean.valueOf(this.f$0.isMediaPlaying);
        }
    }
}
