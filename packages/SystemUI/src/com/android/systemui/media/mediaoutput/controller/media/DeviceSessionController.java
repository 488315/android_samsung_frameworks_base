package com.android.systemui.media.mediaoutput.controller.media;

import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.feature.IcTvKt;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.ShadeController;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class DeviceSessionController implements DeviceSession {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _artistFlow;
    public final StateFlowImpl _roomDetailsFlow;
    public final StateFlowImpl _titleFlow;
    public final ActivityStarter activityStarter;
    public final StateFlowImpl appIconFlow;
    public final ReadonlyStateFlow artistFlow;
    public final ContextScope coroutineScope;
    public final DeviceDomain deviceDomain;
    public final Lazy id$delegate;
    public final SmartThingsMediaSdkManager mediaSdkManager;
    public final ReadonlyStateFlow roomDetailsFlow;
    public final ShadeController shadeController;
    public final ReadonlyStateFlow titleFlow;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final String access$getRoomDetails(Companion companion, DeviceDomain deviceDomain) {
            String strM;
            companion.getClass();
            String str = deviceDomain.roomName;
            if (StringsKt__StringsKt.isBlank(str)) {
                str = null;
            }
            String str2 = deviceDomain.deviceName;
            return (str == null || (strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, " - ", str)) == null) ? str2 : strM;
        }

        private Companion() {
        }
    }

    public DeviceSessionController(SmartThingsMediaSdkManager smartThingsMediaSdkManager, ActivityStarter activityStarter, ShadeController shadeController, DeviceDomain deviceDomain) {
        this.mediaSdkManager = smartThingsMediaSdkManager;
        this.activityStarter = activityStarter;
        this.shadeController = shadeController;
        this.deviceDomain = deviceDomain;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        ContextScope contextScopeCoroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
        this.coroutineScope = contextScopeCoroutineScope;
        this.id$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.deviceDomain.deviceId;
            }
        });
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(smartThingsMediaSdkManager.mediaSdkOperationManager.mediaContentOperationImpl.getMediaContent(getId()));
        this._titleFlow = stateFlowImplMutableStateFlow;
        this.titleFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(deviceDomain.deviceName);
        this._artistFlow = stateFlowImplMutableStateFlow2;
        this.artistFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons.Feature feature = Icons.Feature.INSTANCE;
        ImageVector imageVector = (ImageVector) IcTvKt.IcTv$delegate.getValue();
        companion.getClass();
        this.appIconFlow = StateFlowKt.MutableStateFlow(ImageVectorConverterPainter.Companion.toConverter(imageVector));
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(Companion.access$getRoomDetails(Companion, deviceDomain));
        this._roomDetailsFlow = stateFlowImplMutableStateFlow3;
        this.roomDetailsFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        Log.d("DeviceSessionController", "init()");
        BuildersKt.launch$default(contextScopeCoroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void close() {
        CoroutineScopeKt.cancel(this.coroutineScope, null);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void execute(long j, long j2) {
        Log.d("DeviceSessionController", "execute() - " + j);
        SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.mediaSdkManager;
        if (j == 2) {
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.setMute(getId());
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.Mute.INSTANCE);
            return;
        }
        if (j == 4) {
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.updateVolume(-1, getId());
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.VolumeDown.INSTANCE);
            return;
        }
        if (j == 8) {
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.updateVolume(1, getId());
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.VolumeUp.INSTANCE);
        } else if (j == 16) {
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.togglePlayback(getId());
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.PlayPause.INSTANCE);
        } else if (j == 32) {
            this.shadeController.animateCollapseShade(0);
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.launchRemoteControlPlugIn(getId());
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.RemoteControl.INSTANCE);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getAppIconFlow() {
        return this.appIconFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getArtistFlow() {
        return this.artistFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return Arrays.asList(new Pair("deviceName", this._roomDetailsFlow.getValue()), new Pair("contents", this._titleFlow.getValue()));
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getId() {
        return (String) this.id$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final ReadonlyStateFlow getTitleFlow() {
        return this.titleFlow;
    }

    public final String toString() {
        return toLogText();
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DeviceSessionController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion = DeviceSessionController.Companion;
                DeviceSessionController deviceSessionController = DeviceSessionController.this;
                SmartThingsMediaSdkManager smartThingsMediaSdkManager = deviceSessionController.mediaSdkManager;
                String id = deviceSessionController.getId();
                companion.getClass();
                Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new DeviceSessionController$Companion$mediaChanged$1(smartThingsMediaSdkManager, id, null)), -1, 2);
                C03511 c03511 = new C03511(DeviceSessionController.this);
                this.label = 1;
                if (flowBuffer$default.collect(c03511, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1, reason: invalid class name and collision with other inner class name */
        public final class C03511 implements FlowCollector {
            public final /* synthetic */ DeviceSessionController this$0;

            public C03511(DeviceSessionController deviceSessionController) {
                this.this$0 = deviceSessionController;
            }

            /* JADX WARN: Code restructure failed: missing block: B:30:0x00bc, code lost:
            
                if (kotlin.Unit.INSTANCE != r1) goto L32;
             */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(Continuation continuation) {
                DeviceSessionController$1$1$emit$1 deviceSessionController$1$1$emit$1;
                DeviceSessionController deviceSessionController;
                Object next;
                DeviceDomain deviceDomain;
                DeviceDomain deviceDomain2;
                if (continuation instanceof DeviceSessionController$1$1$emit$1) {
                    deviceSessionController$1$1$emit$1 = (DeviceSessionController$1$1$emit$1) continuation;
                    int i = deviceSessionController$1$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        deviceSessionController$1$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        deviceSessionController$1$1$emit$1 = new DeviceSessionController$1$1$emit$1(this, continuation);
                    }
                }
                Object obj = deviceSessionController$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = deviceSessionController$1$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    deviceSessionController = this.this$0;
                    Iterator it = deviceSessionController.mediaSdkManager.mediaSdkOperationManager.deviceStatusOperationImpl.getDevices().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (Intrinsics.areEqual(((DeviceDomain) next).deviceId, deviceSessionController.getId())) {
                            break;
                        }
                    }
                    deviceDomain = (DeviceDomain) next;
                    if (deviceDomain != null) {
                        Log.d("DeviceSessionController", String.valueOf(deviceDomain));
                        String mediaContent = deviceSessionController.mediaSdkManager.mediaSdkOperationManager.mediaContentOperationImpl.getMediaContent(deviceSessionController.getId());
                        deviceSessionController$1$1$emit$1.L$0 = deviceDomain;
                        deviceSessionController$1$1$emit$1.L$1 = deviceSessionController;
                        deviceSessionController$1$1$emit$1.L$2 = deviceDomain;
                        deviceSessionController$1$1$emit$1.label = 1;
                        deviceSessionController._titleFlow.setValue(mediaContent);
                        if (Unit.INSTANCE != coroutineSingletons) {
                            deviceDomain2 = deviceDomain;
                            StateFlowImpl stateFlowImpl = deviceSessionController._roomDetailsFlow;
                            String strAccess$getRoomDetails = Companion.access$getRoomDetails(DeviceSessionController.Companion, deviceDomain);
                            deviceSessionController$1$1$emit$1.L$0 = deviceDomain2;
                            deviceSessionController$1$1$emit$1.L$1 = null;
                            deviceSessionController$1$1$emit$1.L$2 = null;
                            deviceSessionController$1$1$emit$1.label = 2;
                            stateFlowImpl.setValue(strAccess$getRoomDetails);
                        }
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                deviceDomain = (DeviceDomain) deviceSessionController$1$1$emit$1.L$2;
                deviceSessionController = (DeviceSessionController) deviceSessionController$1$1$emit$1.L$1;
                deviceDomain2 = (DeviceDomain) deviceSessionController$1$1$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl2 = deviceSessionController._roomDetailsFlow;
                String strAccess$getRoomDetails2 = Companion.access$getRoomDetails(DeviceSessionController.Companion, deviceDomain);
                deviceSessionController$1$1$emit$1.L$0 = deviceDomain2;
                deviceSessionController$1$1$emit$1.L$1 = null;
                deviceSessionController$1$1$emit$1.L$2 = null;
                deviceSessionController$1$1$emit$1.label = 2;
                stateFlowImpl2.setValue(strAccess$getRoomDetails2);
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(continuation);
            }
        }
    }
}
