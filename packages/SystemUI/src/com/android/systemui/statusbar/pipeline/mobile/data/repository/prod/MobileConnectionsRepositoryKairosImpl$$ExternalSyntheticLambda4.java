package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileConnectionsRepositoryKairosImpl f$0;

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryKairosImpl.logger;
                mobileInputLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(24);
                LogBuffer logBuffer = mobileInputLogger.buffer;
                logBuffer.commit(logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null));
                break;
            case 1:
                if (!((Boolean) obj2).booleanValue()) {
                    break;
                } else {
                    int i = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                    break;
                }
            case 2:
                Map.Entry entry = (Map.Entry) obj2;
                int i2 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                final int intValue = ((Number) entry.getKey()).intValue();
                final MobileConnectionRepositoryKairosFactoryImpl mobileConnectionRepositoryKairosFactoryImpl = (MobileConnectionRepositoryKairosFactoryImpl) ((MobileConnectionsRepositoryKairosImpl.ConnectionRepoFactory) mobileConnectionsRepositoryKairosImpl.mobileRepoFactory.get());
                mobileConnectionRepositoryKairosFactoryImpl.getClass();
                break;
            case 3:
                int i3 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                mobileConnectionsRepositoryKairosImpl.dumpCache = new MobileConnectionsRepositoryKairosImpl.DumpCache((Map) obj2);
                break;
            default:
                MobileInputLogger mobileInputLogger2 = mobileConnectionsRepositoryKairosImpl.logger;
                mobileInputLogger2.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda02 = new MobileInputLogger$$ExternalSyntheticLambda0(4);
                LogBuffer logBuffer2 = mobileInputLogger2.buffer;
                LogMessage obtain = logBuffer2.obtain("MobileInputLog", logLevel2, mobileInputLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) obtain).str1 = ((SignalIcon$MobileIconGroup) obj2).name;
                logBuffer2.commit(obtain);
                break;
        }
        return Unit.INSTANCE;
    }
}
