package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import androidx.lifecycle.Lifecycle;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.wifitrackerlib.WifiPickerTracker;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class WifiRepositoryImpl$wifiPickerTrackerInfo$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<WifiRepositoryImpl.WifiPickerTrackerInfo> $current;
    final /* synthetic */ FeatureFlags $featureFlags;
    final /* synthetic */ WifiRepositoryImpl $this_run;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiPickerTrackerInfo$1$1(WifiRepositoryImpl wifiRepositoryImpl, FeatureFlags featureFlags, Ref$ObjectRef<WifiRepositoryImpl.WifiPickerTrackerInfo> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.$this_run = wifiRepositoryImpl;
        this.$featureFlags = featureFlags;
        this.$current = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$wifiPickerTrackerInfo$1$1 wifiRepositoryImpl$wifiPickerTrackerInfo$1$1 = new WifiRepositoryImpl$wifiPickerTrackerInfo$1$1(this.$this_run, this.$featureFlags, this.$current, continuation);
        wifiRepositoryImpl$wifiPickerTrackerInfo$1$1.L$0 = obj;
        return wifiRepositoryImpl$wifiPickerTrackerInfo$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$wifiPickerTrackerInfo$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final WifiRepositoryImpl wifiRepositoryImpl = this.$this_run;
            final FeatureFlags featureFlags = this.$featureFlags;
            final Ref$ObjectRef<WifiRepositoryImpl.WifiPickerTrackerInfo> ref$ObjectRef = this.$current;
            WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback = new WifiPickerTracker.WifiPickerTrackerCallback() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$1$1$callback$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r6v2, types: [T, com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo, java.lang.Object] */
                public static void send$default(WifiRepositoryImpl$wifiPickerTrackerInfo$1$1$callback$1 wifiRepositoryImpl$wifiPickerTrackerInfo$1$1$callback$1, int i2, boolean z, WifiNetworkModel wifiNetworkModel, List list, int i3) {
                    if ((i3 & 1) != 0) {
                        i2 = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).state;
                    }
                    if ((i3 & 2) != 0) {
                        z = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).isDefault;
                    }
                    if ((i3 & 4) != 0) {
                        wifiNetworkModel = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).primaryNetwork;
                    }
                    if ((i3 & 8) != 0) {
                        list = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).secondaryNetworks;
                    }
                    wifiRepositoryImpl$wifiPickerTrackerInfo$1$1$callback$1.getClass();
                    ?? wifiPickerTrackerInfo = new WifiRepositoryImpl.WifiPickerTrackerInfo(i2, z, wifiNetworkModel, list);
                    ref$ObjectRef.element = wifiPickerTrackerInfo;
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(wifiPickerTrackerInfo);
                }

                /* JADX WARN: Code restructure failed: missing block: B:41:0x00c1, code lost:
                
                    if (r0 != null) goto L43;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v12, types: [java.util.ArrayList] */
                /* JADX WARN: Type inference failed for: r4v4, types: [kotlin.collections.EmptyList] */
                /* JADX WARN: Type inference failed for: r4v5 */
                @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onWifiEntriesChanged() {
                    /*
                        Method dump skipped, instructions count: 222
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$1$1$callback$1.onWifiEntriesChanged():void");
                }

                @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
                public final void onWifiStateChanged() {
                    Integer num;
                    WifiRepositoryImpl wifiRepositoryImpl2 = WifiRepositoryImpl.this;
                    WifiPickerTracker wifiPickerTracker = wifiRepositoryImpl2.wifiPickerTracker;
                    if (wifiPickerTracker != null) {
                        if (wifiPickerTracker.mWifiState == 4) {
                            wifiPickerTracker.mWifiState = wifiPickerTracker.mWifiManager.getWifiState();
                        }
                        num = Integer.valueOf(wifiPickerTracker.mWifiState);
                    } else {
                        num = null;
                    }
                    LogLevel logLevel = LogLevel.DEBUG;
                    WifiRepositoryImpl$logOnWifiStateChanged$2 wifiRepositoryImpl$logOnWifiStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$logOnWifiStateChanged$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            LogMessage logMessage = (LogMessage) obj2;
                            return "onWifiStateChanged. State=" + (logMessage.getInt1() == -1 ? null : Integer.valueOf(logMessage.getInt1()));
                        }
                    };
                    String str = wifiRepositoryImpl2.TAG$1;
                    LogBuffer logBuffer = wifiRepositoryImpl2.inputLogger;
                    LogMessage obtain = logBuffer.obtain(str, logLevel, wifiRepositoryImpl$logOnWifiStateChanged$2, null);
                    ((LogMessageImpl) obtain).int1 = num != null ? num.intValue() : -1;
                    logBuffer.commit(obtain);
                    send$default(this, num != null ? num.intValue() : 1, false, null, null, 14);
                }
            };
            WifiRepositoryImpl wifiRepositoryImpl2 = this.$this_run;
            WifiPickerTracker create = wifiRepositoryImpl2.wifiPickerTrackerFactory.create(wifiRepositoryImpl2.lifecycle, wifiPickerTrackerCallback, "WifiRepository");
            if (create != null) {
                create.mIsScanningDisabled = true;
                create.mInjector.mVerboseLoggingDisabledOverride = true;
            }
            wifiRepositoryImpl2.wifiPickerTracker = create;
            final WifiRepositoryImpl wifiRepositoryImpl3 = this.$this_run;
            wifiRepositoryImpl3.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$1$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    WifiRepositoryImpl.this.lifecycle.setCurrentState(Lifecycle.State.STARTED);
                }
            });
            final WifiRepositoryImpl wifiRepositoryImpl4 = this.$this_run;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$1$1.3
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final WifiRepositoryImpl wifiRepositoryImpl5 = WifiRepositoryImpl.this;
                    wifiRepositoryImpl5.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl.wifiPickerTrackerInfo.1.1.3.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WifiRepositoryImpl.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                        }
                    });
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
