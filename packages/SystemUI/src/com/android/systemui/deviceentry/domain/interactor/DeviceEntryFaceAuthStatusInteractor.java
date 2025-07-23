package com.android.systemui.deviceentry.domain.interactor;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.biometrics.FaceHelpMessageDebouncer;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryFaceAuthStatusInteractor {
    public final ReadonlyStateFlow authenticationStatus;
    public final Set faceAcquiredInfoIgnoreList;
    public final FaceHelpMessageDebouncer faceHelpMessageDebouncer = new FaceHelpMessageDebouncer(0, 0, 0, 0.0f, 15, null);

    public DeviceEntryFaceAuthStatusInteractor(DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, Resources resources, CoroutineScope coroutineScope) {
        this.faceAcquiredInfoIgnoreList = (Set) Arrays.stream(resources.getIntArray(R.array.config_face_acquire_device_entry_ignorelist)).boxed().collect(Collectors.toSet());
        this.authenticationStatus = FlowKt.stateIn(new SafeFlow(new DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(((DeviceEntryFaceAuthRepositoryImpl) deviceEntryFaceAuthRepository)._authenticationStatus), null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null);
    }
}
