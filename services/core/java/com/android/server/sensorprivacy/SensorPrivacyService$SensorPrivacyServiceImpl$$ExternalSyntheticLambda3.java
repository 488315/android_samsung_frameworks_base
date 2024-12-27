package com.android.server.sensorprivacy;

import com.android.internal.util.FunctionalUtils;
import com.android.server.sensorprivacy.SensorPrivacyService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda3 implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SensorPrivacyService.SensorPrivacyServiceImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda3(SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl, int i, int i2, int i3, boolean z, int i4) {
        this.$r8$classId = i4;
        this.f$0 = sensorPrivacyServiceImpl;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = z;
    }

    public final void acceptOrThrow(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = this.f$3;
                boolean z = this.f$4;
                Integer num = (Integer) obj;
                if (i == sensorPrivacyServiceImpl.this$0.mUserManagerInternal.getProfileParentId(num.intValue())) {
                    sensorPrivacyServiceImpl.setToggleSensorPrivacy(num.intValue(), i2, i3, z);
                    break;
                }
                break;
            default:
                SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl2 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                boolean z2 = this.f$4;
                Integer num2 = (Integer) obj;
                if (i4 == sensorPrivacyServiceImpl2.this$0.mUserManagerInternal.getProfileParentId(num2.intValue())) {
                    sensorPrivacyServiceImpl2.setToggleSensorPrivacy(num2.intValue(), i5, i6, z2);
                    break;
                }
                break;
        }
    }
}
