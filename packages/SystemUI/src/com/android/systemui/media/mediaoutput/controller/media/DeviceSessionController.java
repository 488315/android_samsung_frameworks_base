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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final String access$getRoomDetails(Companion companion, DeviceDomain deviceDomain) {
            String m;
            companion.getClass();
            String str = deviceDomain.roomName;
            if (StringsKt__StringsKt.isBlank(str)) {
                str = null;
            }
            String str2 = deviceDomain.deviceName;
            return (str == null || (m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, " - ", str)) == null) ? str2 : m;
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
        ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
        this.coroutineScope = CoroutineScope;
        this.id$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DeviceSessionController.this.deviceDomain.deviceId;
            }
        });
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(smartThingsMediaSdkManager.mediaSdkOperationManager.mediaContentOperationImpl.getMediaContent(getId()));
        this._titleFlow = MutableStateFlow;
        this.titleFlow = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(deviceDomain.deviceName);
        this._artistFlow = MutableStateFlow2;
        this.artistFlow = FlowKt.asStateFlow(MutableStateFlow2);
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons.Feature feature = Icons.Feature.INSTANCE;
        ImageVector imageVector = (ImageVector) IcTvKt.IcTv$delegate.getValue();
        companion.getClass();
        this.appIconFlow = StateFlowKt.MutableStateFlow(ImageVectorConverterPainter.Companion.toConverter(imageVector));
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Companion.access$getRoomDetails(Companion, deviceDomain));
        this._roomDetailsFlow = MutableStateFlow3;
        this.roomDetailsFlow = FlowKt.asStateFlow(MutableStateFlow3);
        Log.d("DeviceSessionController", "init()");
        BuildersKt.launch$default(CoroutineScope, null, null, new AnonymousClass1(null), 3);
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
            this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$execute$1
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    DeviceSessionController deviceSessionController = DeviceSessionController.this;
                    deviceSessionController.shadeController.animateCollapseShade(0);
                    deviceSessionController.mediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.launchRemoteControlPlugIn(deviceSessionController.getId());
                    return true;
                }
            }, null, true);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new DeviceSessionController$Companion$mediaChanged$1(smartThingsMediaSdkManager, id, null)), -1, 2);
                C02281 c02281 = new C02281(DeviceSessionController.this);
                this.label = 1;
                if (buffer$default.collect(c02281, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1, reason: invalid class name and collision with other inner class name */
        public final class C02281 implements FlowCollector {
            public final /* synthetic */ DeviceSessionController this$0;

            public C02281(DeviceSessionController deviceSessionController) {
                this.this$0 = deviceSessionController;
            }

            /* JADX WARN: Code restructure failed: missing block: B:18:0x00bc, code lost:
            
                if (kotlin.Unit.INSTANCE != r1) goto L32;
             */
            /* JADX WARN: Removed duplicated region for block: B:20:0x0048  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(kotlin.coroutines.Continuation r9) {
                /*
                    r8 = this;
                    boolean r0 = r9 instanceof com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1$emit$1
                    if (r0 == 0) goto L13
                    r0 = r9
                    com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1$emit$1 r0 = (com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1$emit$1 r0 = new com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$1$1$emit$1
                    r0.<init>(r8, r9)
                L18:
                    java.lang.Object r9 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 2
                    r4 = 1
                    r5 = 0
                    if (r2 == 0) goto L48
                    if (r2 == r4) goto L38
                    if (r2 != r3) goto L30
                    java.lang.Object r8 = r0.L$0
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain r8 = (com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain) r8
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto Lbf
                L30:
                    java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                    java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                    r8.<init>(r9)
                    throw r8
                L38:
                    java.lang.Object r8 = r0.L$2
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain r8 = (com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain) r8
                    java.lang.Object r2 = r0.L$1
                    com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController r2 = (com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController) r2
                    java.lang.Object r4 = r0.L$0
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain r4 = (com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain) r4
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto La7
                L48:
                    kotlin.ResultKt.throwOnFailure(r9)
                    com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController r2 = r8.this$0
                    com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r8 = r2.mediaSdkManager
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r8 = r8.mediaSdkOperationManager
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl r8 = r8.deviceStatusOperationImpl
                    java.util.List r8 = r8.getDevices()
                    java.lang.Iterable r8 = (java.lang.Iterable) r8
                    java.util.Iterator r8 = r8.iterator()
                L5d:
                    boolean r9 = r8.hasNext()
                    if (r9 == 0) goto L77
                    java.lang.Object r9 = r8.next()
                    r6 = r9
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain r6 = (com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain) r6
                    java.lang.String r6 = r6.deviceId
                    java.lang.String r7 = r2.getId()
                    boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
                    if (r6 == 0) goto L5d
                    goto L78
                L77:
                    r9 = r5
                L78:
                    r8 = r9
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain r8 = (com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain) r8
                    if (r8 == 0) goto Lbf
                    java.lang.String r9 = java.lang.String.valueOf(r8)
                    java.lang.String r6 = "DeviceSessionController"
                    android.util.Log.d(r6, r9)
                    java.lang.String r9 = r2.getId()
                    com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r6 = r2.mediaSdkManager
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r6 = r6.mediaSdkOperationManager
                    com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent.MediaContentOperationImpl r6 = r6.mediaContentOperationImpl
                    java.lang.String r9 = r6.getMediaContent(r9)
                    r0.L$0 = r8
                    r0.L$1 = r2
                    r0.L$2 = r8
                    r0.label = r4
                    kotlinx.coroutines.flow.StateFlowImpl r4 = r2._titleFlow
                    r4.setValue(r9)
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    if (r9 != r1) goto La6
                    goto Lbe
                La6:
                    r4 = r8
                La7:
                    kotlinx.coroutines.flow.StateFlowImpl r9 = r2._roomDetailsFlow
                    com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion r2 = com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController.Companion
                    java.lang.String r8 = com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController.Companion.access$getRoomDetails(r2, r8)
                    r0.L$0 = r4
                    r0.L$1 = r5
                    r0.L$2 = r5
                    r0.label = r3
                    r9.setValue(r8)
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    if (r8 != r1) goto Lbf
                Lbe:
                    return r1
                Lbf:
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController.AnonymousClass1.C02281.emit(kotlin.coroutines.Continuation):java.lang.Object");
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(continuation);
            }
        }
    }
}
