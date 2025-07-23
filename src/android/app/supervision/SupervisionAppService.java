package android.app.supervision;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.supervision.ISupervisionAppService;
import android.content.Intent;
import android.os.IBinder;

@SystemApi
/* loaded from: classes.dex */
public class SupervisionAppService extends Service {
    private final ISupervisionAppService mBinder = new ISupervisionAppService.Stub() { // from class: android.app.supervision.SupervisionAppService.1
        @Override // android.app.supervision.ISupervisionAppService
        public void onEnabled() {
            SupervisionAppService.this.onEnabled();
        }

        @Override // android.app.supervision.ISupervisionAppService
        public void onDisabled() {
            SupervisionAppService.this.onDisabled();
        }
    };

    @SystemApi
    public void onDisabled() {
    }

    @SystemApi
    public void onEnabled() {
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder.asBinder();
    }
}
