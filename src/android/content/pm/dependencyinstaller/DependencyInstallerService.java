package android.content.pm.dependencyinstaller;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.dependencyinstaller.IDependencyInstallerService;
import android.os.IBinder;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public abstract class DependencyInstallerService extends Service {
    private IDependencyInstallerService mBinder;

    public abstract void onDependenciesRequired(List<SharedLibraryInfo> list, DependencyInstallerCallback dependencyInstallerCallback);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mBinder == null) {
            this.mBinder = new IDependencyInstallerService.Stub() { // from class: android.content.pm.dependencyinstaller.DependencyInstallerService.1
                @Override // android.content.pm.dependencyinstaller.IDependencyInstallerService
                public void onDependenciesRequired(List<SharedLibraryInfo> list, DependencyInstallerCallback dependencyInstallerCallback) {
                    DependencyInstallerService.this.onDependenciesRequired(list, dependencyInstallerCallback);
                }
            };
        }
        return this.mBinder.asBinder();
    }
}
