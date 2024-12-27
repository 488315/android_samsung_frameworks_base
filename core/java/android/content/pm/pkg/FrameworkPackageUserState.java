package android.content.pm.pkg;

import android.content.pm.overlay.OverlayPaths;

import java.util.Map;
import java.util.Set;

@Deprecated
public interface FrameworkPackageUserState {
    public static final FrameworkPackageUserState DEFAULT = new FrameworkPackageUserStateDefault();

    OverlayPaths getAllOverlayPaths();

    long getCeDataInode();

    Set<String> getDisabledComponents();

    int getDistractionFlags();

    Set<String> getEnabledComponents();

    int getEnabledState();

    String getHarmfulAppWarning();

    int getInstallReason();

    String getLastDisableAppCaller();

    OverlayPaths getOverlayPaths();

    Map<String, OverlayPaths> getSharedLibraryOverlayPaths();

    String getSplashScreenTheme();

    int getUninstallReason();

    boolean isComponentDisabled(String str);

    boolean isComponentEnabled(String str);

    boolean isHidden();

    boolean isInstalled();

    boolean isInstantApp();

    boolean isNotLaunched();

    boolean isStopped();

    boolean isSuspended();

    boolean isVirtualPreload();
}
