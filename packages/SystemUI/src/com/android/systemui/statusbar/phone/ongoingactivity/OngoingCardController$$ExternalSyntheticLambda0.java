package com.android.systemui.statusbar.phone.ongoingactivity;

import android.util.Log;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaPlayerData;
import java.util.Iterator;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCardController$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OngoingCardController f$0;

    public /* synthetic */ OngoingCardController$$ExternalSyntheticLambda0(OngoingCardController ongoingCardController, int i) {
        this.$r8$classId = i;
        this.f$0 = ongoingCardController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        OngoingCardController ongoingCardController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                OngoingActivityController$$ExternalSyntheticLambda2 ongoingActivityController$$ExternalSyntheticLambda2 = ongoingCardController.getMediaCardView;
                if (ongoingActivityController$$ExternalSyntheticLambda2 == null) {
                    return null;
                }
                Unit unit = Unit.INSTANCE;
                return ongoingActivityController$$ExternalSyntheticLambda2.f$0.mMediaCardView;
            case 1:
                String str = (String) obj;
                Log.i("{OngoingExpandedPipController}", "Swipe L/R dismiss. sbnId= " + str + "}");
                ongoingCardController.notifCollection.dismissOngoingActivityNotification(str);
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
                if (ongoingActivityDataByKey != null && ongoingActivityDataByKey.mIsMediaOngoingData) {
                    Log.i("{OngoingExpandedPipController}", "Swipe L/R delete media");
                    SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) ongoingCardController.mediaHost.mMediaPlayerData.get(MediaType.OA);
                    if (secMediaPlayerData != null) {
                        Iterator it = secMediaPlayerData.getMediaData().iterator();
                        while (it.hasNext()) {
                            ongoingCardController.mediaDataManager.dismissMediaData((String) ((Map.Entry) it.next()).getKey(), 0L, true);
                        }
                    }
                }
                return Unit.INSTANCE;
            case 2:
                OngoingActivityController$$ExternalSyntheticLambda2 ongoingActivityController$$ExternalSyntheticLambda22 = ongoingCardController.isMediaPlaying;
                return Boolean.valueOf(ongoingActivityController$$ExternalSyntheticLambda22 == null ? false : ((Boolean) ongoingActivityController$$ExternalSyntheticLambda22.mo779invoke(Unit.INSTANCE)).booleanValue());
            default:
                ongoingCardController.collapseAnimation();
                return Unit.INSTANCE;
        }
    }
}
