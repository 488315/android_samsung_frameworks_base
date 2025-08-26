package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.IntentFilter;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ MobileConnectionsRepositoryKairosImpl f$1;

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda2(BroadcastDispatcher broadcastDispatcher, MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl) {
        this.f$0 = broadcastDispatcher;
        this.f$1 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$0;
        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                int i = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryKairosImpl$defaultDataSubId$1$2(mobileConnectionsRepositoryKairosImpl, null), BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"), null, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(6), 14));
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl.getClass();
                StateInit stateInitHoldState = buildScopeImpl.stateScope.holdState(BuildScope.DefaultImpls.toEvents(buildScopeImpl, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1), null);
                DiffableKt.logIntDiffsForTable(buildScopeImpl, stateInitHoldState, mobileConnectionsRepositoryKairosImpl.tableLogger, "Repo", "defaultSubId");
                return stateInitHoldState;
            default:
                MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryKairosImpl.logger;
                mobileInputLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(25);
                LogBuffer logBuffer = mobileInputLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = ((MobileMappings.Config) obj2).toString();
                logBuffer.commit(logMessageObtain);
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda2(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, MobileMappings.Config config) {
        this.f$1 = mobileConnectionsRepositoryKairosImpl;
        this.f$0 = config;
    }
}
