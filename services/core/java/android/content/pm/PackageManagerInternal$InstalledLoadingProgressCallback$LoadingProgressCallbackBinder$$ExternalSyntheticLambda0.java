package android.content.pm;

import com.android.server.pm.LauncherAppsService;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback) obj)
                .onLoadingProgressChanged(((Float) obj2).floatValue());
    }
}
