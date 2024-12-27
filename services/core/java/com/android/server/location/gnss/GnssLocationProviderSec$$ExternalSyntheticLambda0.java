package com.android.server.location.gnss;

import com.android.server.location.gnss.GnssLocationProviderSec;
import java.io.StringWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssLocationProviderSec$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GnssLocationProviderSec$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((GnssLocationProviderSec) this.f$0).gnssConfigurationUpdateSec((String) this.f$1);
                break;
            case 1:
                GnssLocationProviderSec gnssLocationProviderSec = (GnssLocationProviderSec) this.f$0;
                StringWriter stringWriter = (StringWriter) this.f$1;
                gnssLocationProviderSec.getClass();
                gnssLocationProviderSec.gnssConfigurationUpdateSec(stringWriter.toString());
                break;
            default:
                GnssLocationProviderSec.CtsRestrictModeFileObserver ctsRestrictModeFileObserver = (GnssLocationProviderSec.CtsRestrictModeFileObserver) this.f$0;
                String str = (String) this.f$1;
                ctsRestrictModeFileObserver.this$0.gnssConfigurationUpdateSec("CTS_RESTRICTMODE=" + str);
                break;
        }
    }
}
