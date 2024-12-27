package com.android.server.speech;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.UserHandle;
import android.speech.IRecognitionServiceManager;
import android.speech.IRecognitionServiceManagerCallback;
import android.util.Slog;

import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SpeechRecognitionManagerService extends AbstractMasterSystemService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SpeechRecognitionManagerServiceStub extends IRecognitionServiceManager.Stub {
        public SpeechRecognitionManagerServiceStub() {}

        public final void createSession(
                ComponentName componentName,
                IBinder iBinder,
                boolean z,
                IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) {
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (SpeechRecognitionManagerService.this.mLock) {
                ((SpeechRecognitionManagerServiceImpl)
                                SpeechRecognitionManagerService.this.getServiceForUserLocked(
                                        callingUserId))
                        .createSessionLocked(
                                componentName, iBinder, z, iRecognitionServiceManagerCallback);
            }
        }

        public final void setTemporaryComponent(ComponentName componentName) {
            int callingUserId = UserHandle.getCallingUserId();
            if (componentName == null) {
                SpeechRecognitionManagerService.this.resetTemporaryService(callingUserId);
                Slog.i(
                        "SpeechRecognitionManagerService",
                        "Reset temporary service for user " + callingUserId);
                return;
            }
            SpeechRecognitionManagerService.this.setTemporaryService(
                    callingUserId, componentName.flattenToString(), 60000);
            Slog.i(
                    "SpeechRecognitionManagerService",
                    "SpeechRecognition temporarily set to " + componentName + " for 60000ms");
        }
    }

    public SpeechRecognitionManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(
                        context, R.string.default_sms_application),
                null);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_SPEECH_RECOGNITION",
                        "SpeechRecognitionManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 60000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new SpeechRecognitionManagerServiceImpl(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("speech_recognition", new SpeechRecognitionManagerServiceStub());
    }
}
