package com.android.systemui.biometrics.data.repository;

import android.content.Context;
import android.content.res.Resources;
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
import kotlin.ResultKt;
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

/* loaded from: classes.dex */
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

    public FacePropertyRepositoryImpl(Context context, Executor executor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, FaceManager faceManager, CameraManager cameraManager, DisplayStateRepository displayStateRepository, ConfigurationRepository configurationRepository) throws Resources.NotFoundException {
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
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, null);
        this.sensorInfo = readonlyStateFlowStateIn;
        ArrayList arrayList = new ArrayList();
        CameraInfo cameraInfoLoadCameraInfo = loadCameraInfo(R.string.config_protectedCameraId, R.string.config_protectedPhysicalCameraId, R.array.config_face_auth_props);
        if (cameraInfoLoadCameraInfo != null) {
            arrayList.add(cameraInfoLoadCameraInfo);
        }
        CameraInfo cameraInfoLoadCameraInfo2 = loadCameraInfo(R.string.config_protectedInnerCameraId, R.string.config_protectedInnerPhysicalCameraId, R.array.config_inner_face_auth_props);
        if (cameraInfoLoadCameraInfo2 != null) {
            arrayList.add(cameraInfoLoadCameraInfo2);
        }
        this.cameraInfoList = arrayList;
        final ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new FacePropertyRepositoryImpl$cameraInfo$1(this, executor, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), !arrayList.isEmpty() ? (CameraInfo) arrayList.get(0) : null);
        this.cameraInfo = readonlyStateFlowStateIn2;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        CameraInfo cameraInfo = (CameraInfo) obj;
                        Point point = cameraInfo != null ? cameraInfo.cameraLocation : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(point, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlowStateIn2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.sensorLocation = FlowKt.stateIn(FlowKt.transformLatest(readonlyStateFlowStateIn, new FacePropertyRepositoryImpl$special$$inlined$flatMapLatest$1(null, this, displayStateRepository, configurationRepository)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }

    public final CameraInfo loadCameraInfo(int i, int i2, int i3) throws Resources.NotFoundException {
        String string = this.applicationContext.getString(i);
        if (string.length() == 0) {
            return null;
        }
        String string2 = this.applicationContext.getString(i2);
        int[] intArray = this.applicationContext.getResources().getIntArray(i3);
        return new CameraInfo(string, string2, intArray.length >= 2 ? new Point(intArray[0], intArray[1]) : null);
    }
}
