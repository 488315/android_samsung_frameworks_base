package com.android.systemui.education.domain.interactor;

import com.android.systemui.education.data.model.EduDeviceConnectionTime;
import com.android.systemui.education.data.model.GestureEduModel;
import java.time.Instant;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ContextualEducationInteractor$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContextualEducationInteractor f$0;

    public /* synthetic */ ContextualEducationInteractor$$ExternalSyntheticLambda1(ContextualEducationInteractor contextualEducationInteractor, int i) {
        this.$r8$classId = i;
        this.f$0 = contextualEducationInteractor;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ContextualEducationInteractor contextualEducationInteractor = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                EduDeviceConnectionTime eduDeviceConnectionTime = (EduDeviceConnectionTime) obj;
                Instant instant = contextualEducationInteractor.clock.instant();
                Instant instant2 = eduDeviceConnectionTime.keyboardFirstConnectionTime;
                eduDeviceConnectionTime.getClass();
                return new EduDeviceConnectionTime(instant2, instant);
            case 1:
                EduDeviceConnectionTime eduDeviceConnectionTime2 = (EduDeviceConnectionTime) obj;
                Instant instant3 = contextualEducationInteractor.clock.instant();
                Instant instant4 = eduDeviceConnectionTime2.touchpadFirstConnectionTime;
                eduDeviceConnectionTime2.getClass();
                return new EduDeviceConnectionTime(instant3, instant4);
            case 2:
                GestureEduModel gestureEduModel = (GestureEduModel) obj;
                return GestureEduModel.copy$default(gestureEduModel, 0, gestureEduModel.educationShownCount + 1, null, null, contextualEducationInteractor.clock.instant(), 73);
            case 3:
                return GestureEduModel.copy$default((GestureEduModel) obj, 0, 0, contextualEducationInteractor.clock.instant(), null, null, 119);
            case 4:
                return GestureEduModel.copy$default((GestureEduModel) obj, 1, 0, null, contextualEducationInteractor.clock.instant(), null, 109);
            default:
                GestureEduModel gestureEduModel2 = (GestureEduModel) obj;
                int i = ContextualEducationInteractor.$r8$clinit;
                int i2 = gestureEduModel2.signalCount;
                return GestureEduModel.copy$default(gestureEduModel2, i2 + 1, 0, null, i2 == 0 ? contextualEducationInteractor.clock.instant() : gestureEduModel2.usageSessionStartTime, null, 109);
        }
    }
}
