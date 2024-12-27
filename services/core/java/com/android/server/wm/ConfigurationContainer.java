package com.android.server.wm;

import android.app.WindowConfiguration;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ConfigurationContainer {
    static final int BOUNDS_CHANGE_NONE = 0;
    static final int BOUNDS_CHANGE_POSITION = 1;
    static final int BOUNDS_CHANGE_SIZE = 2;
    private boolean mHasOverrideConfiguration;
    private Rect mReturnBounds = new Rect();
    private Configuration mRequestedOverrideConfiguration = new Configuration();
    private Configuration mResolvedOverrideConfiguration = new Configuration();
    private Configuration mFullConfiguration = new Configuration();
    private Configuration mMergedOverrideConfiguration = new Configuration();
    private ArrayList mChangeListeners = new ArrayList();
    private final Configuration mRequestsTmpConfig = new Configuration();
    private final Configuration mResolvedTmpConfig = new Configuration();
    private final Rect mTmpRect = new Rect();

    public static void applySizeOverrideIfNeeded(
            DisplayContent displayContent,
            ApplicationInfo applicationInfo,
            Configuration configuration,
            Configuration configuration2,
            boolean z,
            boolean z2,
            boolean z3,
            Task task) {
        Task task2;
        int i;
        int i2;
        if (displayContent == null) {
            return;
        }
        boolean isChangeEnabled =
                displayContent.mWmService.mFlags.mInsetsDecoupledConfiguration
                        ? (applicationInfo.isChangeEnabled(151861875L)
                                        || applicationInfo.isChangeEnabled(327313645L))
                                ? false
                                : true
                        : applicationInfo.isChangeEnabled(327313645L);
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        boolean z4 =
                WindowConfiguration.isFloating(windowingMode)
                        && (configuration2.windowConfiguration.getWindowingMode() == 0
                                || WindowConfiguration.isFloating(
                                        configuration2.windowConfiguration.getWindowingMode()));
        int rotation = configuration.windowConfiguration.getRotation();
        if (rotation == -1 && !z2) {
            rotation = displayContent.mDisplayRotation.mRotation;
        }
        if ((z || !(!isChangeEnabled || z3 || z4 || rotation == -1)) && !z4) {
            if (z2) {
                configuration2.windowConfiguration.setAppBounds((Rect) null);
                configuration2.screenWidthDp = 0;
                configuration2.screenHeightDp = 0;
                configuration2.smallestScreenWidthDp = 0;
                configuration2.orientation = 0;
            }
            boolean z5 = rotation == 1 || rotation == 3;
            int i3 = z5 ? displayContent.mBaseDisplayHeight : displayContent.mBaseDisplayWidth;
            int i4 = z5 ? displayContent.mBaseDisplayWidth : displayContent.mBaseDisplayHeight;
            Rect appBounds = configuration2.windowConfiguration.getAppBounds();
            if (appBounds == null || appBounds.isEmpty()) {
                configuration2.windowConfiguration.setAppBounds(
                        configuration.windowConfiguration.getBounds());
                appBounds = configuration2.windowConfiguration.getAppBounds();
                if (task != null) {
                    task2 = task.getCreatedByOrganizerTask();
                    if (task2 != null
                            && ((i2 = task2.mOffsetYForInsets) != 0
                                    || task2.mOffsetXForInsets != 0)) {
                        appBounds.offset(task2.mOffsetXForInsets, i2);
                    }
                } else {
                    task2 = task;
                }
                appBounds.intersectUnchecked(
                        displayContent.mDisplayPolicy.getDecorInsetsInfo(rotation, i3, i4)
                                .mOverrideNonDecorFrame);
                if (task2 != null
                        && ((i = task2.mOffsetYForInsets) != 0 || task2.mOffsetXForInsets != 0)) {
                    appBounds.offset(-task2.mOffsetXForInsets, -i);
                }
            }
            float f = configuration2.densityDpi;
            if (f == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f = configuration.densityDpi;
            }
            float f2 = f * 0.00625f;
            if (configuration2.screenWidthDp == 0) {
                configuration2.screenWidthDp = (int) ((appBounds.width() / f2) + 0.5f);
            }
            if (configuration2.screenHeightDp == 0) {
                configuration2.screenHeightDp = (int) ((appBounds.height() / f2) + 0.5f);
            }
            if (configuration2.smallestScreenWidthDp == 0 && windowingMode == 1) {
                displayContent.computeSizeRanges(
                        new DisplayInfo(displayContent.mDisplayInfo),
                        z5,
                        i3,
                        i4,
                        displayContent.mDisplayMetrics.density,
                        configuration2,
                        true);
            }
            if (configuration2.orientation == 0) {
                configuration2.orientation =
                        configuration2.screenWidthDp > configuration2.screenHeightDp ? 2 : 1;
            }
        }
    }

    public static boolean equivalentBounds(Rect rect, Rect rect2) {
        return rect == rect2
                || (rect != null && (rect.equals(rect2) || (rect.isEmpty() && rect2 == null)))
                || (rect2 != null && rect2.isEmpty() && rect == null);
    }

    public static boolean isCompatibleActivityType(int i, int i2) {
        if (i == i2) {
            return true;
        }
        if (i == 4) {
            return false;
        }
        return i == 0 || i2 == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean applyAppSpecificConfig(
            java.lang.Integer r6, android.os.LocaleList r7, java.lang.Integer r8) {
        /*
            r5 = this;
            android.content.res.Configuration r0 = r5.mRequestsTmpConfig
            android.content.res.Configuration r1 = r5.getRequestedOverrideConfiguration()
            r0.setTo(r1)
            r0 = 0
            r1 = 1
            if (r6 == 0) goto L25
            android.content.res.Configuration r2 = r5.mRequestsTmpConfig
            int r6 = r6.intValue()
            android.content.res.Configuration r3 = r5.mRequestedOverrideConfiguration
            int r3 = r3.uiMode
            r4 = r3 & 48
            r6 = r6 & 48
            if (r4 != r6) goto L1e
            goto L25
        L1e:
            r3 = r3 & (-49)
            r6 = r6 | r3
            r2.uiMode = r6
            r6 = r1
            goto L26
        L25:
            r6 = r0
        L26:
            if (r7 == 0) goto L3e
            android.content.res.Configuration r2 = r5.mRequestsTmpConfig
            android.content.res.Configuration r3 = r5.mRequestedOverrideConfiguration
            android.os.LocaleList r3 = r3.getLocales()
            boolean r3 = r3.equals(r7)
            if (r3 == 0) goto L37
            goto L3e
        L37:
            r2.setLocales(r7)
            r2.userSetLocale = r1
            r7 = r1
            goto L3f
        L3e:
            r7 = r0
        L3f:
            android.content.res.Configuration r2 = r5.mRequestsTmpConfig
            if (r8 != 0) goto L45
            r8 = r0
            goto L49
        L45:
            int r8 = r8.intValue()
        L49:
            boolean r8 = r5.setOverrideGender(r2, r8)
            if (r6 != 0) goto L53
            if (r7 != 0) goto L53
            if (r8 == 0) goto L58
        L53:
            android.content.res.Configuration r2 = r5.mRequestsTmpConfig
            r5.onRequestedOverrideConfigurationChanged(r2)
        L58:
            if (r6 != 0) goto L5e
            if (r7 != 0) goto L5e
            if (r8 == 0) goto L5f
        L5e:
            r0 = r1
        L5f:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ConfigurationContainer.applyAppSpecificConfig(java.lang.Integer,"
                    + " android.os.LocaleList, java.lang.Integer):boolean");
    }

    public boolean containsListener(ConfigurationContainerListener configurationContainerListener) {
        return this.mChangeListeners.contains(configurationContainerListener);
    }

    public int diffRequestedOverrideBounds(Rect rect) {
        if (equivalentRequestedOverrideBounds(rect)) {
            return 0;
        }
        Rect requestedOverrideBounds = getRequestedOverrideBounds();
        int i =
                (rect != null
                                && requestedOverrideBounds.left == rect.left
                                && requestedOverrideBounds.top == rect.top)
                        ? 0
                        : 1;
        return (rect != null
                        && requestedOverrideBounds.width() == rect.width()
                        && requestedOverrideBounds.height() == rect.height())
                ? i
                : i | 2;
    }

    public int diffRequestedOverrideMaxBounds(Rect rect) {
        if (equivalentRequestedOverrideMaxBounds(rect)) {
            return 0;
        }
        Rect requestedOverrideMaxBounds = getRequestedOverrideMaxBounds();
        int i =
                (rect != null
                                && requestedOverrideMaxBounds.left == rect.left
                                && requestedOverrideMaxBounds.top == rect.top)
                        ? 0
                        : 1;
        return (rect != null
                        && requestedOverrideMaxBounds.width() == rect.width()
                        && requestedOverrideMaxBounds.height() == rect.height())
                ? i
                : i | 2;
    }

    public void dispatchConfigurationToChild(
            ConfigurationContainer configurationContainer, Configuration configuration) {
        configurationContainer.onConfigurationChanged(configuration);
    }

    public void dumpChildrenNames(PrintWriter printWriter, String str) {
        dumpChildrenNames(printWriter, str, true);
    }

    public void dumpChildrenNames(PrintWriter printWriter, String str, boolean z) {
        int windowingMode = getWindowingMode();
        String windowingModeToString = WindowConfiguration.windowingModeToString(windowingMode);
        if (windowingMode != 0 && windowingMode != 1) {
            windowingModeToString = windowingModeToString.toUpperCase();
        }
        int requestedOverrideWindowingMode = getRequestedOverrideWindowingMode();
        String windowingModeToString2 =
                WindowConfiguration.windowingModeToString(requestedOverrideWindowingMode);
        if (requestedOverrideWindowingMode != 0 && requestedOverrideWindowingMode != 1) {
            windowingModeToString2 = windowingModeToString2.toUpperCase();
        }
        String activityTypeToString = WindowConfiguration.activityTypeToString(getActivityType());
        if (getActivityType() != 0 && getActivityType() != 1) {
            activityTypeToString = activityTypeToString.toUpperCase();
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m.append(z ? "└─ " : "├─ ");
        printWriter.print(m.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append(" type=");
        sb.append(activityTypeToString);
        sb.append(" mode=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb,
                windowingModeToString,
                " override-mode=",
                windowingModeToString2,
                " requested-bounds=");
        sb.append(getRequestedOverrideBounds().toShortString());
        sb.append(" bounds=");
        sb.append(getBounds().toShortString());
        printWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(z ? "   " : "│  ");
        String sb3 = sb2.toString();
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            getChildAt(childCount).dumpChildrenNames(printWriter, sb3, childCount == 0);
            childCount--;
        }
    }

    public void dumpConfigurationLocked(PrintWriter printWriter, String str, int i) {
        printWriter.println(str + "-----------------------------------------------");
        printWriter.println(str + "#" + i + " " + this);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("   Full=");
        sb.append(this.mFullConfiguration);
        printWriter.println(sb.toString());
        printWriter.println(str + "   Merg=" + this.mMergedOverrideConfiguration);
        if (this.mHasOverrideConfiguration) {
            if (this.mRequestedOverrideConfiguration.equals(this.mResolvedOverrideConfiguration)) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "   Over=");
                m.append(this.mResolvedOverrideConfiguration);
                printWriter.println(m.toString());
            } else {
                StringBuilder m2 =
                        Preconditions$$ExternalSyntheticOutline0.m(str, "   Over(Request)=");
                m2.append(this.mRequestedOverrideConfiguration);
                printWriter.println(m2.toString());
                printWriter.println(
                        str + "   Over(Resolve)=" + this.mResolvedOverrideConfiguration);
            }
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount += -1) {
            getChildAt(childCount).dumpConfigurationLocked(printWriter, str + "  ", childCount);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(1146756268033L);
        if (i == 0 || this.mHasOverrideConfiguration) {
            this.mRequestedOverrideConfiguration.dumpDebug(
                    protoOutputStream, 1146756268033L, i == 2);
        }
        if (i == 0) {
            this.mFullConfiguration.dumpDebug(protoOutputStream, 1146756268034L, false);
            this.mMergedOverrideConfiguration.dumpDebug(protoOutputStream, 1146756268035L, false);
        }
        if (i == 1) {
            long start2 = protoOutputStream.start(1146756268034L);
            long start3 = protoOutputStream.start(1146756268051L);
            protoOutputStream.write(
                    1120986464258L, this.mFullConfiguration.windowConfiguration.getWindowingMode());
            protoOutputStream.end(start3);
            protoOutputStream.end(start2);
        }
        protoOutputStream.end(start);
    }

    public boolean equivalentRequestedOverrideBounds(Rect rect) {
        return equivalentBounds(getRequestedOverrideBounds(), rect);
    }

    public boolean equivalentRequestedOverrideMaxBounds(Rect rect) {
        return equivalentBounds(getRequestedOverrideMaxBounds(), rect);
    }

    public int getActivityType() {
        return this.mFullConfiguration.windowConfiguration.getActivityType();
    }

    public Rect getBounds() {
        this.mReturnBounds.set(getConfiguration().windowConfiguration.getBounds());
        return this.mReturnBounds;
    }

    public void getBounds(Rect rect) {
        rect.set(getBounds());
    }

    public abstract ConfigurationContainer getChildAt(int i);

    public abstract int getChildCount();

    public Configuration getConfiguration() {
        return this.mFullConfiguration;
    }

    public Rect getMaxBounds() {
        this.mReturnBounds.set(getConfiguration().windowConfiguration.getMaxBounds());
        return this.mReturnBounds;
    }

    public Configuration getMergedOverrideConfiguration() {
        return this.mMergedOverrideConfiguration;
    }

    public String getName() {
        return toString();
    }

    public abstract ConfigurationContainer getParent();

    public void getPosition(Point point) {
        Rect bounds = getBounds();
        point.set(bounds.left, bounds.top);
    }

    public Rect getRequestedOverrideBounds() {
        this.mReturnBounds.set(getRequestedOverrideConfiguration().windowConfiguration.getBounds());
        return this.mReturnBounds;
    }

    public void getRequestedOverrideBounds(Rect rect) {
        rect.set(getRequestedOverrideBounds());
    }

    public Configuration getRequestedOverrideConfiguration() {
        return this.mRequestedOverrideConfiguration;
    }

    public Rect getRequestedOverrideMaxBounds() {
        this.mReturnBounds.set(
                getRequestedOverrideConfiguration().windowConfiguration.getMaxBounds());
        return this.mReturnBounds;
    }

    public int getRequestedOverrideWindowingMode() {
        return this.mRequestedOverrideConfiguration.windowConfiguration.getWindowingMode();
    }

    public Rect getResolvedOverrideBounds() {
        this.mReturnBounds.set(getResolvedOverrideConfiguration().windowConfiguration.getBounds());
        return this.mReturnBounds;
    }

    public Configuration getResolvedOverrideConfiguration() {
        return this.mResolvedOverrideConfiguration;
    }

    public int getStagePosition() {
        return this.mFullConfiguration.windowConfiguration.getStagePosition();
    }

    public int getStageType() {
        return this.mFullConfiguration.windowConfiguration.getStageType();
    }

    public WindowConfiguration getWindowConfiguration() {
        return this.mFullConfiguration.windowConfiguration;
    }

    public int getWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode();
    }

    public boolean hasChild() {
        return getChildCount() > 0;
    }

    public boolean hasOverrideBounds() {
        return !getRequestedOverrideBounds().isEmpty();
    }

    public boolean hasRequestedOverrideConfiguration() {
        return this.mHasOverrideConfiguration;
    }

    public boolean hasStagePosition(int i) {
        return (this.mFullConfiguration.windowConfiguration.getStagePosition() & i) != 0;
    }

    public boolean inFreeformWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 5;
    }

    public boolean inFullscreenWindowingMode() {
        return getWindowingMode() == 1;
    }

    public boolean inMultiWindowMode() {
        return WindowConfiguration.inMultiWindowMode(
                this.mFullConfiguration.windowConfiguration.getWindowingMode());
    }

    public boolean inPinnedWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 2;
    }

    public boolean inSplitScreenWindowingMode() {
        return WindowConfiguration.isSplitScreenWindowingMode(
                this.mFullConfiguration.windowConfiguration);
    }

    public boolean isActivityTypeAssistant() {
        return getActivityType() == 4;
    }

    public boolean isActivityTypeDream() {
        return getActivityType() == 5;
    }

    public boolean isActivityTypeHome() {
        return getActivityType() == 2;
    }

    public final boolean isActivityTypeHomeOrRecents() {
        int activityType = getActivityType();
        return activityType == 2 || activityType == 3;
    }

    public boolean isActivityTypeRecents() {
        return getActivityType() == 3;
    }

    public boolean isActivityTypeStandard() {
        return getActivityType() == 1;
    }

    public boolean isActivityTypeStandardOrUndefined() {
        int activityType = getActivityType();
        return activityType == 1 || activityType == 0;
    }

    public boolean isAlwaysOnTop() {
        return this.mFullConfiguration.windowConfiguration.isAlwaysOnTop();
    }

    public boolean isAlwaysOnTopFreeform() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 5
                && this.mFullConfiguration.windowConfiguration.isAlwaysOnTop();
    }

    public boolean isCompatible(int i, int i2) {
        int activityType = getActivityType();
        int windowingMode = getWindowingMode();
        boolean z = activityType == i2;
        boolean z2 = windowingMode == i;
        if (z && z2) {
            return true;
        }
        return ((i2 == 0 || i2 == 1) && isActivityTypeStandardOrUndefined()) ? z2 : z;
    }

    public boolean isDesktopModeEnabled() {
        return this.mFullConfiguration.semDesktopModeEnabled == 1;
    }

    public boolean isDexMode() {
        return this.mFullConfiguration.isDexMode();
    }

    public boolean isNewDexMode() {
        return this.mFullConfiguration.isNewDexMode();
    }

    public boolean matchParentBounds() {
        return getResolvedOverrideBounds().isEmpty();
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.mResolvedTmpConfig.setTo(this.mResolvedOverrideConfiguration);
        resolveOverrideConfiguration(configuration);
        this.mFullConfiguration.setTo(configuration);
        this.mFullConfiguration.windowConfiguration.unsetAlwaysOnTop();
        this.mFullConfiguration.updateFrom(this.mResolvedOverrideConfiguration);
        onMergedOverrideConfigurationChanged();
        if (!this.mResolvedTmpConfig.equals(this.mResolvedOverrideConfiguration)) {
            for (int size = this.mChangeListeners.size() - 1; size >= 0; size--) {
                ((ConfigurationContainerListener) this.mChangeListeners.get(size))
                        .onRequestedOverrideConfigurationChanged(
                                this.mResolvedOverrideConfiguration);
            }
        }
        for (int size2 = this.mChangeListeners.size() - 1; size2 >= 0; size2--) {
            ((ConfigurationContainerListener) this.mChangeListeners.get(size2))
                    .onMergedOverrideConfigurationChanged(this.mMergedOverrideConfiguration);
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            dispatchConfigurationToChild(getChildAt(childCount), this.mFullConfiguration);
        }
    }

    public void onMergedOverrideConfigurationChanged() {
        ConfigurationContainer parent = getParent();
        if (parent == null) {
            this.mMergedOverrideConfiguration.setTo(this.mResolvedOverrideConfiguration);
            return;
        }
        this.mMergedOverrideConfiguration.setTo(parent.getMergedOverrideConfiguration());
        this.mMergedOverrideConfiguration.windowConfiguration.unsetAlwaysOnTop();
        this.mMergedOverrideConfiguration.updateFrom(this.mResolvedOverrideConfiguration);
    }

    public void onMergedOverrideConfigurationChanged(Configuration configuration) {
        onRequestedOverrideConfigurationChanged(configuration);
    }

    public void onParentChanged(
            ConfigurationContainer configurationContainer,
            ConfigurationContainer configurationContainer2) {
        if (configurationContainer != null) {
            onConfigurationChanged(configurationContainer.mFullConfiguration);
        }
    }

    public void onRequestedOverrideConfigurationChanged(Configuration configuration) {
        updateRequestedOverrideConfiguration(configuration);
        ConfigurationContainer parent = getParent();
        onConfigurationChanged(parent != null ? parent.getConfiguration() : Configuration.EMPTY);
    }

    public boolean providesMaxBounds() {
        return false;
    }

    public void registerConfigurationChangeListener(
            ConfigurationContainerListener configurationContainerListener) {
        registerConfigurationChangeListener(configurationContainerListener, true);
    }

    public void registerConfigurationChangeListener(
            ConfigurationContainerListener configurationContainerListener, boolean z) {
        if (this.mChangeListeners.contains(configurationContainerListener)) {
            return;
        }
        this.mChangeListeners.add(configurationContainerListener);
        if (z) {
            configurationContainerListener.onRequestedOverrideConfigurationChanged(
                    this.mResolvedOverrideConfiguration);
            configurationContainerListener.onMergedOverrideConfigurationChanged(
                    this.mMergedOverrideConfiguration);
        }
    }

    public void resolveOverrideConfiguration(Configuration configuration) {
        this.mResolvedOverrideConfiguration.setTo(this.mRequestedOverrideConfiguration);
    }

    public void setActivityType(int i) {
        int activityType = getActivityType();
        if (activityType == i) {
            return;
        }
        if (activityType == 0) {
            this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
            this.mRequestsTmpConfig.windowConfiguration.setActivityType(i);
            onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
            return;
        }
        Log.d(
                "WindowManager",
                "setActivityType's exception occurs, name="
                        + getName()
                        + ", caller="
                        + Debug.getCallers(7));
        throw new IllegalStateException(
                "Can't change activity type once set: "
                        + this
                        + " activityType="
                        + WindowConfiguration.activityTypeToString(i));
    }

    public void setAlwaysOnTop(boolean z) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setAlwaysOnTop(z);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public int setBounds(int i, int i2, int i3, int i4) {
        this.mTmpRect.set(i, i2, i3, i4);
        return setBounds(this.mTmpRect);
    }

    public int setBounds(Rect rect) {
        int diffRequestedOverrideBounds = diffRequestedOverrideBounds(rect);
        boolean z = providesMaxBounds() && diffRequestedOverrideMaxBounds(rect) != 0;
        if (diffRequestedOverrideBounds == 0 && !z) {
            return diffRequestedOverrideBounds;
        }
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setBounds(rect);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
        return diffRequestedOverrideBounds;
    }

    public void setDexTaskDocking(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setDexTaskDockingState(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public void setEmbedActivityMode(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setEmbedActivityMode(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public void setFreeformTaskPinning(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        if (CoreRune.MT_NEW_DEX_TASK_PINNING && isNewDexMode()) {
            this.mRequestsTmpConfig.windowConfiguration.setAlwaysOnTop(i == 2);
        } else {
            this.mRequestsTmpConfig.windowConfiguration.setFreeformTaskPinningState(i);
        }
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public boolean setOverrideGender(Configuration configuration, int i) {
        return false;
    }

    public void setStageType(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setStageType(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public void setWindowingMode(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setWindowingMode(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public boolean supportsSplitScreenWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.supportSplitScreenWindowingMode();
    }

    public void unregisterConfigurationChangeListener(
            ConfigurationContainerListener configurationContainerListener) {
        this.mChangeListeners.remove(configurationContainerListener);
    }

    public void updateRequestedOverrideConfiguration(Configuration configuration) {
        this.mHasOverrideConfiguration = !Configuration.EMPTY.equals(configuration);
        this.mRequestedOverrideConfiguration.setTo(configuration);
        Rect bounds = this.mRequestedOverrideConfiguration.windowConfiguration.getBounds();
        if (this.mHasOverrideConfiguration
                && providesMaxBounds()
                && diffRequestedOverrideMaxBounds(bounds) != 0) {
            this.mRequestedOverrideConfiguration.windowConfiguration.setMaxBounds(bounds);
        }
    }
}
