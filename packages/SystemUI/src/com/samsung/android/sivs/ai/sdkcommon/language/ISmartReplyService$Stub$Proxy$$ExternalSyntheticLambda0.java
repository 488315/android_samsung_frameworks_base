package com.samsung.android.sivs.ai.sdkcommon.language;

import android.os.Parcel;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Parcel f$0;

    public /* synthetic */ ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(Parcel parcel, int i) {
        this.$r8$classId = i;
        this.f$0 = parcel;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Parcel parcel = this.f$0;
        String str = (String) obj;
        String str2 = (String) obj2;
        switch (i) {
            case 0:
                parcel.writeString(str);
                parcel.writeString(str2);
                break;
            case 1:
                parcel.writeString(str);
                parcel.writeString(str2);
                break;
            case 2:
                parcel.writeString(str);
                parcel.writeString(str2);
                break;
            default:
                parcel.writeString(str);
                parcel.writeString(str2);
                break;
        }
    }
}
