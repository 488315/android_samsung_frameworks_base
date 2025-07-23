package com.android.systemui.screenshot.policy;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Process;
import com.android.systemui.SystemUIService;
import com.android.systemui.screenshot.ImageCapture;
import com.android.systemui.screenshot.data.repository.DisplayContentRepository;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import dagger.internal.Provider;
import java.util.List;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotPolicyModule_BindScreenshotRequestProcessorFactory implements Provider {
    public final Provider backgroundProvider;
    public final Provider contextProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider displayContentRepoProvider;
    public final Provider imageCaptureProvider;
    public final Provider policyListProvider;
    public final Provider semCaptureProvider;
    public final Provider standardPolicyProvider;

    public ScreenshotPolicyModule_BindScreenshotRequestProcessorFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.backgroundProvider = provider2;
        this.imageCaptureProvider = provider3;
        this.displayContentRepoProvider = provider4;
        this.devicePolicyManagerProvider = provider5;
        this.semCaptureProvider = provider6;
        this.policyListProvider = provider7;
        this.standardPolicyProvider = provider8;
    }

    public static PolicyRequestProcessor bindScreenshotRequestProcessor(Context context, CoroutineDispatcher coroutineDispatcher, ImageCapture imageCapture, DisplayContentRepository displayContentRepository, DevicePolicyManager devicePolicyManager, SemImageCaptureImpl semImageCaptureImpl, javax.inject.Provider provider, ScreenshotPolicy screenshotPolicy) {
        ScreenshotPolicyModule.Companion.getClass();
        return new PolicyRequestProcessor(coroutineDispatcher, imageCapture, displayContentRepository, (List) provider.get(), screenshotPolicy, Process.myUserHandle(), new ComponentName(context.getPackageName(), SystemUIService.class.toString()), semImageCaptureImpl, devicePolicyManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return bindScreenshotRequestProcessor((Context) this.contextProvider.get(), (CoroutineDispatcher) this.backgroundProvider.get(), (ImageCapture) this.imageCaptureProvider.get(), (DisplayContentRepository) this.displayContentRepoProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (SemImageCaptureImpl) this.semCaptureProvider.get(), this.policyListProvider, (ScreenshotPolicy) this.standardPolicyProvider.get());
    }
}
