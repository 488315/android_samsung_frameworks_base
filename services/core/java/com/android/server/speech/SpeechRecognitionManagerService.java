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
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

/* loaded from: classes3.dex */
public final class SpeechRecognitionManagerService extends AbstractMasterSystemService {
    public static final String TAG = "SpeechRecognitionManagerService";

    @Override // com.android.server.infra.AbstractMasterSystemService
    public int getMaximumTemporaryServiceDurationMs() {
        return 60000;
    }

    public SpeechRecognitionManagerService(Context context) {
        super(context, new FrameworkResourcesServiceNameResolver(context, R.string.date_picker_prev_month_button), null);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("speech_recognition", new SpeechRecognitionManagerServiceStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_SPEECH_RECOGNITION", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public SpeechRecognitionManagerServiceImpl newServiceLocked(int i, boolean z) {
        return new SpeechRecognitionManagerServiceImpl(this, this.mLock, i);
    }

    public final class SpeechRecognitionManagerServiceStub extends IRecognitionServiceManager.Stub {
        public SpeechRecognitionManagerServiceStub() {
        }

        public void createSession(ComponentName componentName, IBinder iBinder, boolean z, IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) {
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (SpeechRecognitionManagerService.this.mLock) {
                ((SpeechRecognitionManagerServiceImpl) SpeechRecognitionManagerService.this.getServiceForUserLocked(callingUserId)).createSessionLocked(componentName, iBinder, z, iRecognitionServiceManagerCallback);
            }
        }

        public void setTemporaryComponent(ComponentName componentName) {
            int callingUserId = UserHandle.getCallingUserId();
            if (componentName == null) {
                SpeechRecognitionManagerService.this.resetTemporaryService(callingUserId);
                Slog.i(SpeechRecognitionManagerService.TAG, "Reset temporary service for user " + callingUserId);
                return;
            }
            SpeechRecognitionManagerService.this.setTemporaryService(callingUserId, componentName.flattenToString(), 60000);
            Slog.i(SpeechRecognitionManagerService.TAG, "SpeechRecognition temporarily set to " + componentName + " for 60000ms");
        }
    }
}
