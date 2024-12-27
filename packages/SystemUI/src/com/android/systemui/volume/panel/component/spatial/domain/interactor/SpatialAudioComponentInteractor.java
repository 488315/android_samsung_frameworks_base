package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.settingslib.media.domain.interactor.SpatializerInteractor;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.systemui.volume.domain.interactor.AudioOutputInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SpatialAudioComponentInteractor {
    public final AudioRepository audioRepository;
    public final CoroutineContext backgroundCoroutineContext;
    public final SharedFlowImpl changes;
    public final ReadonlyStateFlow currentAudioDeviceAttributes;
    public final ReadonlyStateFlow isAvailable;
    public final ReadonlyStateFlow isEnabled;
    public final SpatializerInteractor spatializerInteractor;
    public static final Companion Companion = new Companion(null);
    public static final AudioDeviceAttributes builtinSpeaker = new AudioDeviceAttributes(2, 2, "");
    public static final Set audioProfiles = SetsKt__SetsKt.setOf(2, 22, 21);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SpatialAudioComponentInteractor(AudioOutputInteractor audioOutputInteractor, SpatializerInteractor spatializerInteractor, AudioRepository audioRepository, CoroutineContext coroutineContext, CoroutineScope coroutineScope) {
        this.spatializerInteractor = spatializerInteractor;
        this.audioRepository = audioRepository;
        this.backgroundCoroutineContext = coroutineContext;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.changes = MutableSharedFlow$default;
        final ReadonlyStateFlow readonlyStateFlow = audioOutputInteractor.currentAudioDevice;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SpatialAudioComponentInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, SpatialAudioComponentInteractor spatialAudioComponentInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = spatialAudioComponentInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:20:0x0063 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:21:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L64
                    L2a:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L32:
                        java.lang.Object r5 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L56
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.volume.domain.model.AudioOutputDevice r6 = (com.android.systemui.volume.domain.model.AudioOutputDevice) r6
                        boolean r7 = r6 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.Unknown
                        kotlinx.coroutines.flow.FlowCollector r2 = r5.$this_unsafeFlow
                        if (r7 == 0) goto L48
                        android.media.AudioDeviceAttributes r5 = com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.builtinSpeaker
                        goto L58
                    L48:
                        r0.L$0 = r2
                        r0.label = r4
                        com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r5 = r5.this$0
                        java.lang.Object r7 = com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.access$getAudioDeviceAttributes(r5, r6, r0)
                        if (r7 != r1) goto L55
                        return r1
                    L55:
                        r5 = r2
                    L56:
                        r2 = r5
                        r5 = r7
                    L58:
                        r6 = 0
                        r0.L$0 = r6
                        r0.label = r3
                        java.lang.Object r5 = r2.emit(r5, r0)
                        if (r5 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), builtinSpeaker);
        this.currentAudioDeviceAttributes = stateIn;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SpatialAudioComponentInteractor$isAvailable$1(null), MutableSharedFlow$default), new SpatialAudioComponentInteractor$isAvailable$2(this, null));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, SpatialAudioAvailabilityModel.Unavailable.INSTANCE);
        this.isAvailable = stateIn2;
        this.isEnabled = FlowKt.stateIn(FlowKt.combine(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SpatialAudioComponentInteractor$isEnabled$1(null), MutableSharedFlow$default), stateIn, stateIn2, new SpatialAudioComponentInteractor$isEnabled$2(this, null)), coroutineScope, startedEagerly, SpatialAudioEnabledModel.Unknown.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v7, types: [java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getAudioDeviceAttributes(com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r6, com.android.systemui.volume.domain.model.AudioOutputDevice r7, kotlin.coroutines.Continuation r8) {
        /*
            r6.getClass()
            boolean r0 = r8 instanceof com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributes$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributes$1 r0 = (com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributes$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributes$1 r0 = new com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributes$1
            r0.<init>(r6, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L73
            if (r2 == r3) goto L6f
            r6 = 2
            if (r2 != r6) goto L67
            java.lang.Object r7 = r0.L$2
            java.lang.Object r2 = r0.L$1
            java.util.Iterator r2 = (java.util.Iterator) r2
            java.lang.Object r3 = r0.L$0
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r3 = (com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor) r3
            kotlin.ResultKt.throwOnFailure(r8)
        L37:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L41
            r4 = r7
            goto L63
        L41:
            boolean r7 = r2.hasNext()
            if (r7 == 0) goto L63
            java.lang.Object r7 = r2.next()
            r8 = r7
            android.media.AudioDeviceAttributes r8 = (android.media.AudioDeviceAttributes) r8
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r5 = r3.spatializerInteractor
            r0.L$0 = r3
            r0.L$1 = r2
            r0.L$2 = r7
            r0.label = r6
            com.android.settingslib.media.data.repository.SpatializerRepository r5 = r5.repository
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r5 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r5
            java.lang.Object r8 = r5.isSpatialAudioAvailableForDevice(r8, r0)
            if (r8 != r1) goto L37
            goto L9c
        L63:
            android.media.AudioDeviceAttributes r4 = (android.media.AudioDeviceAttributes) r4
        L65:
            r1 = r4
            goto L9c
        L67:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L6f:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L9b
        L73:
            kotlin.ResultKt.throwOnFailure(r8)
            boolean r8 = r7 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.BuiltIn
            if (r8 == 0) goto L7e
            android.media.AudioDeviceAttributes r6 = com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.builtinSpeaker
            r1 = r6
            goto L9c
        L7e:
            boolean r8 = r7 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.Bluetooth
            if (r8 == 0) goto L65
            com.android.settingslib.flags.FeatureFlagsImpl r8 = com.android.settingslib.flags.Flags.FEATURE_FLAGS
            r8.getClass()
            com.android.systemui.volume.domain.model.AudioOutputDevice$Bluetooth r7 = (com.android.systemui.volume.domain.model.AudioOutputDevice.Bluetooth) r7
            com.android.settingslib.bluetooth.CachedBluetoothDevice r7 = r7.cachedBluetoothDevice
            r0.label = r3
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2 r8 = new com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2
            r8.<init>(r7, r6, r4)
            kotlin.coroutines.CoroutineContext r6 = r6.backgroundCoroutineContext
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
            if (r8 != r1) goto L9b
            goto L9c
        L9b:
            r1 = r8
        L9c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.access$getAudioDeviceAttributes(com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor, com.android.systemui.volume.domain.model.AudioOutputDevice, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00b8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object setEnabled(com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$setEnabled$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$setEnabled$1 r0 = (com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$setEnabled$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$setEnabled$1 r0 = new com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$setEnabled$1
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L52
            if (r2 == r5) goto L40
            if (r2 == r4) goto L37
            if (r2 != r3) goto L2f
            kotlin.ResultKt.throwOnFailure(r9)
            goto Lb9
        L2f:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L37:
            java.lang.Object r7 = r0.L$0
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r7 = (com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto Laa
        L40:
            java.lang.Object r7 = r0.L$2
            android.media.AudioDeviceAttributes r7 = (android.media.AudioDeviceAttributes) r7
            java.lang.Object r8 = r0.L$1
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel r8 = (com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel) r8
            java.lang.Object r2 = r0.L$0
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r2 = (com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor) r2
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r7
            r7 = r2
            goto L8e
        L52:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.flow.ReadonlyStateFlow r9 = r7.currentAudioDeviceAttributes
            kotlinx.coroutines.flow.StateFlow r9 = r9.$$delegate_0
            java.lang.Object r9 = r9.getValue()
            android.media.AudioDeviceAttributes r9 = (android.media.AudioDeviceAttributes) r9
            if (r9 != 0) goto L64
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L64:
            boolean r2 = r8 instanceof com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.SpatialAudioEnabled
            r0.L$0 = r7
            r0.L$1 = r8
            r0.L$2 = r9
            r0.label = r5
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r5 = r7.spatializerInteractor
            com.android.settingslib.media.data.repository.SpatializerRepository r5 = r5.repository
            if (r2 == 0) goto L80
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r5 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r5
            java.lang.Object r2 = r5.addSpatialAudioCompatibleDevice(r9, r0)
            if (r2 != r1) goto L7d
            goto L8b
        L7d:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            goto L8b
        L80:
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r5 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r5
            java.lang.Object r2 = r5.removeSpatialAudioCompatibleDevice(r9, r0)
            if (r2 != r1) goto L89
            goto L8b
        L89:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
        L8b:
            if (r2 != r1) goto L8e
            return r1
        L8e:
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r2 = r7.spatializerInteractor
            boolean r8 = r8 instanceof com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.HeadTrackingEnabled
            r0.L$0 = r7
            r0.L$1 = r6
            r0.L$2 = r6
            r0.label = r4
            com.android.settingslib.media.data.repository.SpatializerRepository r2 = r2.repository
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r2 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r2
            java.lang.Object r8 = r2.setHeadTrackingEnabled(r9, r8, r0)
            if (r8 != r1) goto La5
            goto La7
        La5:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
        La7:
            if (r8 != r1) goto Laa
            return r1
        Laa:
            kotlinx.coroutines.flow.SharedFlowImpl r7 = r7.changes
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r7 = r7.emit(r8, r0)
            if (r7 != r1) goto Lb9
            return r1
        Lb9:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.setEnabled(com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
