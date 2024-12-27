package android.window;

import android.app.ResourcesManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.InsetsState;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import android.view.WindowMetrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public final class WindowMetricsController {
    private final Context mContext;

    public WindowMetricsController(Context context) {
        this.mContext = context;
    }

    public WindowMetrics getCurrentWindowMetrics() {
        return getWindowMetricsInternal(false);
    }

    public WindowMetrics getMaximumWindowMetrics() {
        return getWindowMetricsInternal(true);
    }

    private WindowMetrics getWindowMetricsInternal(boolean isMaximum) {
        Rect bounds;
        float density;
        final boolean isScreenRound;
        final int activityType;
        synchronized (ResourcesManager.getInstance()) {
            Configuration config = this.mContext.getResources().getConfiguration();
            WindowConfiguration winConfig = config.windowConfiguration;
            bounds = isMaximum ? winConfig.getMaxBounds() : winConfig.getBounds();
            density = config.densityDpi * 0.00625f;
            isScreenRound = config.isScreenRound();
            activityType = winConfig.getActivityType();
        }
        final IBinder token = Context.getToken(this.mContext);
        final Rect rect = bounds;
        Supplier<WindowInsets> insetsSupplier =
                new Supplier() { // from class:
                                 // android.window.WindowMetricsController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        WindowInsets lambda$getWindowMetricsInternal$0;
                        lambda$getWindowMetricsInternal$0 =
                                WindowMetricsController.this.lambda$getWindowMetricsInternal$0(
                                        token, rect, isScreenRound, activityType);
                        return lambda$getWindowMetricsInternal$0;
                    }
                };
        return new WindowMetrics(new Rect(bounds), insetsSupplier, density);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ WindowInsets lambda$getWindowMetricsInternal$0(
            IBinder token, Rect bounds, boolean isScreenRound, int activityType) {
        return getWindowInsetsFromServerForDisplay(
                this.mContext.getDisplayId(), token, bounds, isScreenRound, activityType);
    }

    private static WindowInsets getWindowInsetsFromServerForDisplay(
            int displayId, IBinder token, Rect bounds, boolean isScreenRound, int activityType) {
        try {
            InsetsState insetsState = new InsetsState();
            try {
                WindowManagerGlobal.getWindowManagerService()
                        .getWindowInsets(displayId, token, insetsState);
                float overrideInvScale = CompatibilityInfo.getOverrideInvertedScale();
                if (overrideInvScale != 1.0f) {
                    insetsState.scale(overrideInvScale);
                }
                return insetsState.calculateInsets(
                        bounds, null, isScreenRound, 48, 0, 0, -1, activityType, null);
            } catch (RemoteException e) {
                e = e;
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException e2) {
            e = e2;
        }
    }

    public Set<WindowMetrics> getPossibleMaximumWindowMetrics(int displayId) {
        try {
            List<DisplayInfo> possibleDisplayInfos =
                    WindowManagerGlobal.getWindowManagerService().getPossibleDisplayInfo(displayId);
            Set<WindowMetrics> maxMetrics = new HashSet<>();
            for (int i = 0; i < possibleDisplayInfos.size(); i++) {
                DisplayInfo currentDisplayInfo = possibleDisplayInfos.get(i);
                Rect maxBounds =
                        new Rect(
                                0,
                                0,
                                currentDisplayInfo.getNaturalWidth(),
                                currentDisplayInfo.getNaturalHeight());
                boolean isScreenRound = (currentDisplayInfo.flags & 16) != 0;
                WindowInsets windowInsets =
                        getWindowInsetsFromServerForDisplay(
                                currentDisplayInfo.displayId,
                                null,
                                new Rect(
                                        0,
                                        0,
                                        currentDisplayInfo.getNaturalWidth(),
                                        currentDisplayInfo.getNaturalHeight()),
                                isScreenRound,
                                0);
                DisplayCutout cutout = currentDisplayInfo.displayCutout;
                if (cutout != null && currentDisplayInfo.rotation != 0) {
                    cutout =
                            cutout.getRotated(
                                    currentDisplayInfo.logicalWidth,
                                    currentDisplayInfo.logicalHeight,
                                    currentDisplayInfo.rotation,
                                    0);
                }
                float density = currentDisplayInfo.logicalDensityDpi * 0.00625f;
                maxMetrics.add(
                        new WindowMetrics(
                                maxBounds,
                                new WindowInsets.Builder(windowInsets)
                                        .setRoundedCorners(currentDisplayInfo.roundedCorners)
                                        .setDisplayCutout(cutout)
                                        .build(),
                                density));
            }
            return maxMetrics;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
