package com.android.server.autofill;

import com.android.server.autofill.SaveEventLogger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SaveEventLogger$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ long f$0;

    public /* synthetic */ SaveEventLogger$$ExternalSyntheticLambda0(long j, int i) {
        this.$r8$classId = i;
        this.f$0 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SaveEventLogger.SaveEventInternal) obj).mLatencySaveFinishMillis = this.f$0;
                break;
            case 1:
                ((SaveEventLogger.SaveEventInternal) obj).mLatencySaveRequestMillis = this.f$0;
                break;
            default:
                ((SaveEventLogger.SaveEventInternal) obj).mLatencySaveUiDisplayMillis = this.f$0;
                break;
        }
    }
}
