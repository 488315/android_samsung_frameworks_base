package com.android.systemui.blur.domain.interactor;

import com.android.systemui.blur.data.repository.SecCapturedBlurRepository;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecCapturedBlurInteractor {
    public final ReadonlyStateFlow fullScreenBlurShowing;
    public final ReadonlyStateFlow mirrorShowing;
    public final SharedFlowImpl requestCaptureBlur;
    public final SecCapturedBlurRepository secCapturedBlurRepository;

    public SecCapturedBlurInteractor(SecCapturedBlurRepository secCapturedBlurRepository) {
        this.secCapturedBlurRepository = secCapturedBlurRepository;
        SecCapturedBlurRepositoryImpl secCapturedBlurRepositoryImpl = (SecCapturedBlurRepositoryImpl) secCapturedBlurRepository;
        this.fullScreenBlurShowing = secCapturedBlurRepositoryImpl.fullScreenBlurShowing;
        this.mirrorShowing = secCapturedBlurRepositoryImpl.mirrorShowing;
        this.requestCaptureBlur = secCapturedBlurRepositoryImpl.requestCaptureBlur;
    }
}
