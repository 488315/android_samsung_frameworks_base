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

/* loaded from: classes5.dex */
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

    private WindowMetrics getWindowMetricsInternal(boolean z) {
        final Rect maxBounds;
        float f;
        final boolean isScreenRound;
        final int activityType;
        synchronized (ResourcesManager.getInstance()) {
            Configuration configuration = this.mContext.getResources().getConfiguration();
            WindowConfiguration windowConfiguration = configuration.windowConfiguration;
            maxBounds = z ? windowConfiguration.getMaxBounds() : windowConfiguration.getBounds();
            f = configuration.densityDpi * 0.00625f;
            isScreenRound = configuration.isScreenRound();
            activityType = windowConfiguration.getActivityType();
        }
        final IBinder token = Context.getToken(this.mContext);
        return new WindowMetrics(new Rect(maxBounds), (Supplier<WindowInsets>) new Supplier() { // from class: android.window.WindowMetricsController$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                WindowInsets lambda$getWindowMetricsInternal$0;
                lambda$getWindowMetricsInternal$0 = WindowMetricsController.this.lambda$getWindowMetricsInternal$0(token, maxBounds, isScreenRound, activityType);
                return lambda$getWindowMetricsInternal$0;
            }
        }, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ WindowInsets lambda$getWindowMetricsInternal$0(IBinder iBinder, Rect rect, boolean z, int i) {
        return getWindowInsetsFromServerForDisplay(this.mContext.getDisplayId(), iBinder, rect, z, i);
    }

    private static WindowInsets getWindowInsetsFromServerForDisplay(int i, IBinder iBinder, Rect rect, boolean z, int i2) {
        try {
            InsetsState insetsState = new InsetsState();
            WindowManagerGlobal.getWindowManagerService().getWindowInsets(i, iBinder, insetsState);
            float overrideInvertedScale = CompatibilityInfo.getOverrideInvertedScale();
            if (overrideInvertedScale != 1.0f) {
                insetsState.scale(overrideInvertedScale);
            }
            return insetsState.calculateInsets(rect, null, z, 48, 0, 0, -1, i2, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<WindowMetrics> getPossibleMaximumWindowMetrics(int i) {
        try {
            List<DisplayInfo> possibleDisplayInfo = WindowManagerGlobal.getWindowManagerService().getPossibleDisplayInfo(i);
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < possibleDisplayInfo.size(); i2++) {
                DisplayInfo displayInfo = possibleDisplayInfo.get(i2);
                Rect rect = new Rect(0, 0, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
                WindowInsets windowInsetsFromServerForDisplay = getWindowInsetsFromServerForDisplay(displayInfo.displayId, null, new Rect(0, 0, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight()), (displayInfo.flags & 16) != 0, 0);
                DisplayCutout displayCutout = displayInfo.displayCutout;
                if (displayCutout != null && displayInfo.rotation != 0) {
                    displayCutout = displayCutout.getRotated(displayInfo.logicalWidth, displayInfo.logicalHeight, displayInfo.rotation, 0);
                }
                hashSet.add(new WindowMetrics(rect, new WindowInsets.Builder(windowInsetsFromServerForDisplay).setRoundedCorners(displayInfo.roundedCorners).setDisplayCutout(displayCutout).build(), displayInfo.logicalDensityDpi * 0.00625f));
            }
            return hashSet;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
