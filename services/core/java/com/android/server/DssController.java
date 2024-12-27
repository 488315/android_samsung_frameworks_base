package com.android.server;

import android.app.GameManagerInternal;
import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.util.MergedConfiguration;
import android.util.Slog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class DssController {
    static final String TAG = "DssController";
    private static DssController sDssController;
    private final int DEFAULT_GAME_SIOP_LEVEL = 0;
    private final int DEFAULT_CPU_LEVEL = 0;
    private final int DEFAULT_GPU_LEVEL = 0;
    private final float DEFAULT_SCALE = 1.0f;
    private final float FLOAT_EPSILON = 0.001f;
    private final HashMap mAllowList = new HashMap();
    private final HashMap mRunningPackages = new HashMap();
    private GameManagerInternal mGameManager = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DssAppDate {
        public float mScale = 1.0f;
        public int mGameSiopLevel = 0;
        public int mCpuLevel = 0;
        public int mGpuLevel = 0;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PairConfiguration {
        public final Rect mAppGlobal;
        public final Rect mAppOverride;
        public final Consumer mBound;

        public PairConfiguration(Rect rect, Rect rect2, Consumer consumer) {
            this.mAppGlobal = rect;
            this.mAppOverride = rect2;
            this.mBound = consumer;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Tools {
        public static void applyDssToConfiguration(Configuration configuration, float f) {
            int i = configuration.densityDpi;
            if (i != 0) {
                configuration.densityDpi = (int) ((i * f) + 0.5f);
            }
            WindowConfiguration windowConfiguration = configuration.windowConfiguration;
            Rect appBounds = windowConfiguration.getAppBounds();
            if (appBounds != null) {
                applyScaleToCompatFrame(appBounds, f);
            }
            Rect maxBounds = windowConfiguration.getMaxBounds();
            if (maxBounds != null) {
                applyScaleToCompatFrame(maxBounds, f);
            }
            Rect bounds = windowConfiguration.getBounds();
            if (bounds != null) {
                applyScaleToCompatFrame(bounds, f);
            }
        }

        public static void applyScaleToCompatFrame(Rect rect, float f) {
            int i = -rect.left;
            int i2 = -rect.top;
            rect.offset(i, i2);
            rect.scale(f);
            rect.offset(-((int) ((i * f) + 0.5f)), -((int) ((i2 * f) + 0.5f)));
        }
    }

    public static DssController getService() {
        if (sDssController == null) {
            sDssController = new DssController();
        }
        return sDssController;
    }

    private String pidToPkg(int i) {
        Iterator it = this.mRunningPackages.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(((Map.Entry) it.next()).getValue());
        throw null;
    }

    public synchronized void addPackageData(String str, float f) {
        DssAppDate dssAppDate = new DssAppDate();
        dssAppDate.mScale = f;
        dssAppDate.mGameSiopLevel = 0;
        dssAppDate.mCpuLevel = 0;
        dssAppDate.mGpuLevel = 0;
        this.mAllowList.put(str, dssAppDate);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mGameManager == null) {
                this.mGameManager =
                        (GameManagerInternal) LocalServices.getService(GameManagerInternal.class);
            }
            GameManagerInternal gameManagerInternal = this.mGameManager;
            if (gameManagerInternal != null) {
                gameManagerInternal.updateResolutionScalingFactorInternal(str, f);
                StringBuilder sb = new StringBuilder("Google Screen Compat ");
                sb.append(f != 1.0f ? "on with Factor " : "off");
                sb.append(" for package: ");
                sb.append(str);
                Slog.d(TAG, sb.toString());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public synchronized void addPackageData(String str, float f, int i) {
        DssAppDate dssAppDate = new DssAppDate();
        dssAppDate.mScale = f;
        dssAppDate.mGameSiopLevel = i;
        this.mAllowList.put(str, dssAppDate);
    }

    public synchronized void addPackageData(String str, float f, String str2) {
        DssAppDate dssAppDate = new DssAppDate();
        dssAppDate.mScale = f;
        String[] split = str2.split(",");
        dssAppDate.mCpuLevel = Integer.parseInt(split[0]);
        dssAppDate.mGpuLevel = Integer.parseInt(split[1]);
        this.mAllowList.put(str, dssAppDate);
    }

    public Configuration createScaledConfiguration(Configuration configuration, String str) {
        if (!isScaledApp(str)) {
            return configuration;
        }
        float scalingFactor = getScalingFactor(str);
        Configuration configuration2 = new Configuration(configuration);
        Tools.applyDssToConfiguration(configuration2, scalingFactor);
        return configuration2;
    }

    public synchronized StringBuilder getAllowList() {
        StringBuilder sb;
        try {
            sb = new StringBuilder();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Map.Entry entry : this.mAllowList.entrySet()) {
                if (Math.abs(((DssAppDate) entry.getValue()).mScale - 0.5f) < 0.001f) {
                    arrayList.add((String) entry.getKey());
                } else if (Math.abs(((DssAppDate) entry.getValue()).mScale - 0.75f) < 0.001f) {
                    arrayList2.add((String) entry.getKey());
                }
            }
            sb.append("DSS 0.5 Group\n");
            sb.append("=============\n");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                sb.append((String) it.next());
                sb.append("\n");
            }
            sb.append("\n");
            sb.append("DSS 0.75 Group\n");
            sb.append("=============\n");
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append("\n");
            }
            sb.append("\n");
        } catch (Throwable th) {
            throw th;
        }
        return sb;
    }

    public synchronized float getDssForPackage(String str) {
        HashMap hashMap;
        hashMap = this.mAllowList;
        return (hashMap == null || !hashMap.containsKey(str))
                ? 1.0f
                : ((DssAppDate) this.mAllowList.get(str)).mScale;
    }

    public synchronized int getGameSiopLevel(String str) {
        if (!this.mAllowList.containsKey(str)) {
            return 0;
        }
        return ((DssAppDate) this.mAllowList.get(str)).mGameSiopLevel;
    }

    public synchronized String getGameSiopLevelString(String str) {
        if (!this.mAllowList.containsKey(str)) {
            return "0,0";
        }
        DssAppDate dssAppDate = (DssAppDate) this.mAllowList.get(str);
        return dssAppDate.mCpuLevel + "," + dssAppDate.mGpuLevel;
    }

    public synchronized float getScalingFactor(int i) {
        return getScalingFactor(pidToPkg(i));
    }

    public synchronized float getScalingFactor(String str) {
        return 1.0f;
    }

    public synchronized boolean isScaledApp(int i) {
        return isScaledApp(pidToPkg(i));
    }

    public synchronized boolean isScaledApp(String str) {
        if (this.mRunningPackages.containsKey(str)) {
            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mRunningPackages.get(str));
            throw null;
        }
        return false;
    }

    public synchronized float onApplicationStarted(String str, int i, boolean z) {
        return 1.0f;
    }

    public synchronized void onApplicationStopped(String str, int i) {
        DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mRunningPackages.get(str));
    }

    public synchronized void removePackage(String str) {
        if (this.mAllowList.containsKey(str)) {
            this.mAllowList.remove(str);
        }
    }

    public void scaleExistingConfiguration(Configuration configuration, String str) {
        if (isScaledApp(str)) {
            Tools.applyDssToConfiguration(configuration, getScalingFactor(str));
        }
    }

    public void scaleExistingMergedConfiguration(
            MergedConfiguration mergedConfiguration, String str) {
        if (isScaledApp(str)) {
            final float scalingFactor = getScalingFactor(str);
            int i = mergedConfiguration.getGlobalConfiguration().densityDpi;
            int i2 = mergedConfiguration.getOverrideConfiguration().densityDpi;
            Configuration configuration =
                    new Configuration(mergedConfiguration.getOverrideConfiguration());
            if (i2 != 0) {
                configuration.densityDpi = (int) ((i2 * scalingFactor) + 0.5f);
            } else if (i != 0) {
                configuration.densityDpi = (int) ((i * scalingFactor) + 0.5f);
            }
            WindowConfiguration windowConfiguration =
                    mergedConfiguration.getGlobalConfiguration().windowConfiguration;
            WindowConfiguration windowConfiguration2 =
                    mergedConfiguration.getOverrideConfiguration().windowConfiguration;
            final WindowConfiguration windowConfiguration3 = configuration.windowConfiguration;
            final int i3 = 0;
            PairConfiguration pairConfiguration =
                    new PairConfiguration(
                            windowConfiguration.getAppBounds(),
                            windowConfiguration2.getAppBounds(),
                            new Consumer() { // from class:
                                             // com.android.server.DssController$Tools$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i4 = i3;
                                    WindowConfiguration windowConfiguration4 = windowConfiguration3;
                                    Rect rect = (Rect) obj;
                                    switch (i4) {
                                        case 0:
                                            windowConfiguration4.setAppBounds(rect);
                                            break;
                                        case 1:
                                            windowConfiguration4.setMaxBounds(rect);
                                            break;
                                        default:
                                            windowConfiguration4.setBounds(rect);
                                            break;
                                    }
                                }
                            });
            final int i4 = 1;
            PairConfiguration pairConfiguration2 =
                    new PairConfiguration(
                            windowConfiguration.getMaxBounds(),
                            windowConfiguration2.getMaxBounds(),
                            new Consumer() { // from class:
                                             // com.android.server.DssController$Tools$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i42 = i4;
                                    WindowConfiguration windowConfiguration4 = windowConfiguration3;
                                    Rect rect = (Rect) obj;
                                    switch (i42) {
                                        case 0:
                                            windowConfiguration4.setAppBounds(rect);
                                            break;
                                        case 1:
                                            windowConfiguration4.setMaxBounds(rect);
                                            break;
                                        default:
                                            windowConfiguration4.setBounds(rect);
                                            break;
                                    }
                                }
                            });
            final int i5 = 2;
            PairConfiguration pairConfiguration3 =
                    new PairConfiguration(
                            windowConfiguration.getBounds(),
                            windowConfiguration2.getBounds(),
                            new Consumer() { // from class:
                                             // com.android.server.DssController$Tools$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i42 = i5;
                                    WindowConfiguration windowConfiguration4 = windowConfiguration3;
                                    Rect rect = (Rect) obj;
                                    switch (i42) {
                                        case 0:
                                            windowConfiguration4.setAppBounds(rect);
                                            break;
                                        case 1:
                                            windowConfiguration4.setMaxBounds(rect);
                                            break;
                                        default:
                                            windowConfiguration4.setBounds(rect);
                                            break;
                                    }
                                }
                            });
            ArrayList arrayList = new ArrayList();
            arrayList.add(pairConfiguration);
            arrayList.add(pairConfiguration2);
            arrayList.add(pairConfiguration3);
            arrayList.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.DssController$Tools$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            float f = scalingFactor;
                            DssController.PairConfiguration pairConfiguration4 =
                                    (DssController.PairConfiguration) obj;
                            Rect rect = pairConfiguration4.mAppGlobal;
                            Rect rect2 = pairConfiguration4.mAppOverride;
                            Rect rect3 =
                                    rect2 != null
                                            ? new Rect(rect2)
                                            : rect != null ? new Rect(rect) : null;
                            if (rect3 != null) {
                                DssController.Tools.applyScaleToCompatFrame(rect3, f);
                                pairConfiguration4.mBound.accept(rect3);
                            }
                        }
                    });
            mergedConfiguration.setOverrideConfiguration(configuration);
        }
    }

    public synchronized void setDssForPackage(String str, float f) {
        try {
            if (Math.abs(f - 1.0f) < 0.001f) {
                removePackage(str);
            } else {
                addPackageData(str, f);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setUsesOwnResolution(int i, String str, boolean z) {
        String pidToPkg = pidToPkg(i);
        if (this.mRunningPackages.containsKey(pidToPkg)) {
            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                    this.mRunningPackages.get(pidToPkg));
            throw null;
        }
        Slog.wtf(
                TAG, "Trying to setUsesOwnResolution(" + z + ") for a package that isn't running!");
    }

    public synchronized void showAllDSSInfo() {
        for (Map.Entry entry : this.mAllowList.entrySet()) {
            Slog.i(
                    TAG,
                    "DSS PackageName : "
                            + ((String) entry.getKey())
                            + ", GameSiopLevel :"
                            + ((DssAppDate) entry.getValue()).mGameSiopLevel
                            + ", CpuLevel :"
                            + ((DssAppDate) entry.getValue()).mCpuLevel
                            + ", GpuLevel :"
                            + ((DssAppDate) entry.getValue()).mGpuLevel);
            if (this.mRunningPackages.containsKey(entry.getKey())) {
                DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        this.mRunningPackages.get((String) entry.getKey()));
                throw null;
            }
        }
    }

    public synchronized boolean usesOwnResolution(String str) {
        DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mRunningPackages.get(str));
        return false;
    }
}
