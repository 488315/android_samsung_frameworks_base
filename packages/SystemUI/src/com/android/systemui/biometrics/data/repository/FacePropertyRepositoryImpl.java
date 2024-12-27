package com.android.systemui.biometrics.data.repository;

import android.content.Context;
import android.graphics.Point;
import android.hardware.camera2.CameraManager;
import android.hardware.face.FaceManager;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.keyguard.shared.model.DevicePosture;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class FacePropertyRepositoryImpl implements FacePropertyRepository {
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow cameraInfo;
    public final List cameraInfoList;
    public final CameraManager cameraManager;
    public String currentPhysicalCameraId;
    public final ReadonlyStateFlow defaultSensorLocation;
    public final FaceManager faceManager;
    public final ReadonlyStateFlow sensorInfo;
    public final ReadonlyStateFlow sensorLocation;

    public FacePropertyRepositoryImpl(Context context, Executor executor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, FaceManager faceManager, CameraManager cameraManager, DisplayStateRepository displayStateRepository, ConfigurationRepository configurationRepository) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.faceManager = faceManager;
        this.cameraManager = cameraManager;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        FacePropertyRepositoryImpl$sensorInfo$1 facePropertyRepositoryImpl$sensorInfo$1 = new FacePropertyRepositoryImpl$sensorInfo$1(this, null);
        conflatedCallbackFlow.getClass();
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(facePropertyRepositoryImpl$sensorInfo$1), new FacePropertyRepositoryImpl$sensorInfo$2(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, null);
        this.sensorInfo = stateIn;
        ArrayList arrayList = new ArrayList();
        CameraInfo loadCameraInfo = loadCameraInfo(R.string.config_protectedCameraId, R.string.config_protectedPhysicalCameraId, R.array.config_face_auth_props);
        if (loadCameraInfo != null) {
            arrayList.add(loadCameraInfo);
        }
        CameraInfo loadCameraInfo2 = loadCameraInfo(R.string.config_protectedInnerCameraId, R.string.config_protectedInnerPhysicalCameraId, R.array.config_inner_face_auth_props);
        if (loadCameraInfo2 != null) {
            arrayList.add(loadCameraInfo2);
        }
        this.cameraInfoList = arrayList;
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new FacePropertyRepositoryImpl$cameraInfo$1(this, executor, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), arrayList.isEmpty() ^ true ? (CameraInfo) arrayList.get(0) : null);
        int integer = context.getResources().getInteger(R.integer.config_face_auth_supported_posture);
        if (integer == 0) {
            DevicePosture.Companion companion2 = DevicePosture.Companion;
        } else {
            DevicePosture.Companion.getClass();
            Collections.singletonList(DevicePosture.Companion.toPosture(integer));
        }
        this.defaultSensorLocation = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.data.repository.CameraInfo r5 = (com.android.systemui.biometrics.data.repository.CameraInfo) r5
                        if (r5 == 0) goto L39
                        android.graphics.Point r5 = r5.cameraLocation
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.sensorLocation = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new FacePropertyRepositoryImpl$special$$inlined$flatMapLatest$1(null, this, displayStateRepository, configurationRepository)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }

    public final CameraInfo loadCameraInfo(int i, int i2, int i3) {
        String string = this.applicationContext.getString(i);
        if (string.length() == 0) {
            return null;
        }
        String string2 = this.applicationContext.getString(i2);
        int[] intArray = this.applicationContext.getResources().getIntArray(i3);
        return new CameraInfo(string, string2, intArray.length >= 2 ? new Point(intArray[0], intArray[1]) : null);
    }
}
