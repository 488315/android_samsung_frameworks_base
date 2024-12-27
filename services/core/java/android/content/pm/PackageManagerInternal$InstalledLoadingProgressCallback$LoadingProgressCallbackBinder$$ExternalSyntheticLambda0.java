package android.content.pm;

import com.android.server.pm.LauncherAppsService;

import java.util.function.BiConsumer;

public final /* synthetic */
class PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback) obj)
                .onLoadingProgressChanged(((Float) obj2).floatValue());
    }
}
