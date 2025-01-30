package com.android.server.wm;

import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.LocaleList;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
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

  public static boolean isCompatibleActivityType(int i, int i2) {
    if (i == i2) {
      return true;
    }
    if (i == 4) {
      return false;
    }
    return i == 0 || i2 == 0;
  }

  public abstract ConfigurationContainer getChildAt(int i);

  public abstract int getChildCount();

  public abstract ConfigurationContainer getParent();

  public boolean providesMaxBounds() {
    return false;
  }

  public Configuration getConfiguration() {
    return this.mFullConfiguration;
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
            .onRequestedOverrideConfigurationChanged(this.mResolvedOverrideConfiguration);
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

  public void dispatchConfigurationToChild(
      ConfigurationContainer configurationContainer, Configuration configuration) {
    configurationContainer.onConfigurationChanged(configuration);
  }

  public void resolveOverrideConfiguration(Configuration configuration) {
    this.mResolvedOverrideConfiguration.setTo(this.mRequestedOverrideConfiguration);
  }

  public boolean hasRequestedOverrideConfiguration() {
    return this.mHasOverrideConfiguration;
  }

  public Configuration getRequestedOverrideConfiguration() {
    return this.mRequestedOverrideConfiguration;
  }

  public Configuration getResolvedOverrideConfiguration() {
    return this.mResolvedOverrideConfiguration;
  }

  public void onRequestedOverrideConfigurationChanged(Configuration configuration) {
    updateRequestedOverrideConfiguration(configuration);
    ConfigurationContainer parent = getParent();
    onConfigurationChanged(parent != null ? parent.getConfiguration() : Configuration.EMPTY);
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

  public Configuration getMergedOverrideConfiguration() {
    return this.mMergedOverrideConfiguration;
  }

  public void onMergedOverrideConfigurationChanged() {
    ConfigurationContainer parent = getParent();
    if (parent != null) {
      this.mMergedOverrideConfiguration.setTo(parent.getMergedOverrideConfiguration());
      this.mMergedOverrideConfiguration.windowConfiguration.unsetAlwaysOnTop();
      this.mMergedOverrideConfiguration.updateFrom(this.mResolvedOverrideConfiguration);
    } else {
      this.mMergedOverrideConfiguration.setTo(this.mResolvedOverrideConfiguration);
    }
    for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
      getChildAt(childCount).onMergedOverrideConfigurationChanged();
    }
  }

  public boolean matchParentBounds() {
    return getResolvedOverrideBounds().isEmpty();
  }

  public boolean equivalentRequestedOverrideBounds(Rect rect) {
    return equivalentBounds(getRequestedOverrideBounds(), rect);
  }

  public boolean equivalentRequestedOverrideMaxBounds(Rect rect) {
    return equivalentBounds(getRequestedOverrideMaxBounds(), rect);
  }

  public static boolean equivalentBounds(Rect rect, Rect rect2) {
    return rect == rect2
        || (rect != null && (rect.equals(rect2) || (rect.isEmpty() && rect2 == null)))
        || (rect2 != null && rect2.isEmpty() && rect == null);
  }

  public Rect getBounds() {
    this.mReturnBounds.set(getConfiguration().windowConfiguration.getBounds());
    return this.mReturnBounds;
  }

  public void getBounds(Rect rect) {
    rect.set(getBounds());
  }

  public Rect getMaxBounds() {
    this.mReturnBounds.set(getConfiguration().windowConfiguration.getMaxBounds());
    return this.mReturnBounds;
  }

  public void getPosition(Point point) {
    Rect bounds = getBounds();
    point.set(bounds.left, bounds.top);
  }

  public Rect getResolvedOverrideBounds() {
    this.mReturnBounds.set(getResolvedOverrideConfiguration().windowConfiguration.getBounds());
    return this.mReturnBounds;
  }

  public Rect getRequestedOverrideBounds() {
    this.mReturnBounds.set(getRequestedOverrideConfiguration().windowConfiguration.getBounds());
    return this.mReturnBounds;
  }

  public Rect getRequestedOverrideMaxBounds() {
    this.mReturnBounds.set(getRequestedOverrideConfiguration().windowConfiguration.getMaxBounds());
    return this.mReturnBounds;
  }

  public boolean hasOverrideBounds() {
    return !getRequestedOverrideBounds().isEmpty();
  }

  public void getRequestedOverrideBounds(Rect rect) {
    rect.set(getRequestedOverrideBounds());
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

  public int setBounds(int i, int i2, int i3, int i4) {
    this.mTmpRect.set(i, i2, i3, i4);
    return setBounds(this.mTmpRect);
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

  public WindowConfiguration getWindowConfiguration() {
    return this.mFullConfiguration.windowConfiguration;
  }

  public int getWindowingMode() {
    return this.mFullConfiguration.windowConfiguration.getWindowingMode();
  }

  public int getRequestedOverrideWindowingMode() {
    return this.mRequestedOverrideConfiguration.windowConfiguration.getWindowingMode();
  }

  public void setWindowingMode(int i) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setWindowingMode(i);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }

  public void setAlwaysOnTop(boolean z) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setAlwaysOnTop(z);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }

  public void setDisplayWindowingMode(int i) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setDisplayWindowingMode(i);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }

  public void setStageType(int i) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setStageType(i);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }

  public int getStageType() {
    return this.mFullConfiguration.windowConfiguration.getStageType();
  }

  public int getStagePosition() {
    return this.mFullConfiguration.windowConfiguration.getStagePosition();
  }

  public boolean hasStagePosition(int i) {
    return (this.mFullConfiguration.windowConfiguration.getStagePosition() & i) != 0;
  }

  public boolean inSplitScreenWindowingMode() {
    return WindowConfiguration.isSplitScreenWindowingMode(
        this.mFullConfiguration.windowConfiguration);
  }

  public boolean inMultiWindowMode() {
    return WindowConfiguration.inMultiWindowMode(
        this.mFullConfiguration.windowConfiguration.getWindowingMode());
  }

  public boolean inPinnedWindowingMode() {
    return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 2;
  }

  public boolean inFreeformWindowingMode() {
    return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 5;
  }

  public boolean supportsSplitScreenWindowingMode() {
    return this.mFullConfiguration.windowConfiguration.supportSplitScreenWindowingMode();
  }

  public boolean inFullscreenWindowingMode() {
    return getWindowingMode() == 1;
  }

  public int getActivityType() {
    return this.mFullConfiguration.windowConfiguration.getActivityType();
  }

  public void setActivityType(int i) {
    int activityType = getActivityType();
    if (activityType == i) {
      return;
    }
    if (activityType != 0) {
      Log.d(
          StartingSurfaceController.TAG,
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
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setActivityType(i);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }

  public boolean isActivityTypeHome() {
    return getActivityType() == 2;
  }

  public boolean isActivityTypeRecents() {
    return getActivityType() == 3;
  }

  public final boolean isActivityTypeHomeOrRecents() {
    int activityType = getActivityType();
    return activityType == 2 || activityType == 3;
  }

  public boolean isActivityTypeAssistant() {
    return getActivityType() == 4;
  }

  public boolean applyAppSpecificConfig(Integer num, LocaleList localeList, Integer num2) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    boolean z = num != null && setOverrideNightMode(this.mRequestsTmpConfig, num.intValue());
    boolean z2 = localeList != null && setOverrideLocales(this.mRequestsTmpConfig, localeList);
    boolean z3 = num2 != null && setOverrideGender(this.mRequestsTmpConfig, num2.intValue());
    if (z || z2 || z3) {
      onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }
    return z || z2 || z3;
  }

  public final boolean setOverrideNightMode(Configuration configuration, int i) {
    int i2 = this.mRequestedOverrideConfiguration.uiMode;
    int i3 = i & 48;
    if ((i2 & 48) == i3) {
      return false;
    }
    configuration.uiMode = (i2 & (-49)) | i3;
    return true;
  }

  public final boolean setOverrideLocales(Configuration configuration, LocaleList localeList) {
    if (this.mRequestedOverrideConfiguration.getLocales().equals(localeList)) {
      return false;
    }
    configuration.setLocales(localeList);
    configuration.userSetLocale = true;
    return true;
  }

  public final boolean setOverrideGender(Configuration configuration, int i) {
    if (this.mRequestedOverrideConfiguration.getGrammaticalGender() == i) {
      return false;
    }
    configuration.setGrammaticalGender(i);
    return true;
  }

  public boolean isActivityTypeDream() {
    return getActivityType() == 5;
  }

  public boolean isActivityTypeStandard() {
    return getActivityType() == 1;
  }

  public boolean isActivityTypeStandardOrUndefined() {
    int activityType = getActivityType();
    return activityType == 1 || activityType == 0;
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

  public void unregisterConfigurationChangeListener(
      ConfigurationContainerListener configurationContainerListener) {
    this.mChangeListeners.remove(configurationContainerListener);
  }

  public boolean containsListener(ConfigurationContainerListener configurationContainerListener) {
    return this.mChangeListeners.contains(configurationContainerListener);
  }

  public void onParentChanged(
      ConfigurationContainer configurationContainer,
      ConfigurationContainer configurationContainer2) {
    if (configurationContainer != null) {
      onConfigurationChanged(configurationContainer.mFullConfiguration);
      onMergedOverrideConfigurationChanged();
    }
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
    long start = protoOutputStream.start(j);
    if (i == 0 || this.mHasOverrideConfiguration) {
      this.mRequestedOverrideConfiguration.dumpDebug(protoOutputStream, 1146756268033L, i == 2);
    }
    if (i == 0) {
      this.mFullConfiguration.dumpDebug(protoOutputStream, 1146756268034L, false);
      this.mMergedOverrideConfiguration.dumpDebug(protoOutputStream, 1146756268035L, false);
    }
    if (i == 1) {
      dumpDebugWindowingMode(protoOutputStream);
    }
    protoOutputStream.end(start);
  }

  public final void dumpDebugWindowingMode(ProtoOutputStream protoOutputStream) {
    long start = protoOutputStream.start(1146756268034L);
    long start2 = protoOutputStream.start(1146756268051L);
    protoOutputStream.write(
        1120986464258L, this.mFullConfiguration.windowConfiguration.getWindowingMode());
    protoOutputStream.end(start2);
    protoOutputStream.end(start);
  }

  public void dumpChildrenNames(PrintWriter printWriter, String str) {
    String str2 = str + " ";
    printWriter.println(
        getName()
            + " type="
            + WindowConfiguration.activityTypeToString(getActivityType())
            + " mode="
            + WindowConfiguration.windowingModeToString(getWindowingMode())
            + " override-mode="
            + WindowConfiguration.windowingModeToString(getRequestedOverrideWindowingMode())
            + " requested-bounds="
            + getRequestedOverrideBounds().toShortString()
            + " bounds="
            + getBounds().toShortString());
    for (int childCount = getChildCount() + (-1); childCount >= 0; childCount += -1) {
      ConfigurationContainer childAt = getChildAt(childCount);
      printWriter.print(str2 + "#" + childCount + " ");
      childAt.dumpChildrenNames(printWriter, str2);
    }
  }

  String getName() {
    return toString();
  }

  public boolean isAlwaysOnTop() {
    return this.mFullConfiguration.windowConfiguration.isAlwaysOnTop();
  }

  public boolean hasChild() {
    return getChildCount() > 0;
  }

  public void getAppBounds(Rect rect) {
    rect.set(getConfiguration().windowConfiguration.getAppBounds());
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
        printWriter.println(str + "   Over=" + this.mResolvedOverrideConfiguration);
      } else {
        printWriter.println(str + "   Over(Request)=" + this.mRequestedOverrideConfiguration);
        printWriter.println(str + "   Over(Resolve)=" + this.mResolvedOverrideConfiguration);
      }
    }
    for (int childCount = getChildCount() - 1; childCount >= 0; childCount += -1) {
      getChildAt(childCount).dumpConfigurationLocked(printWriter, str + "  ", childCount);
    }
  }

  public boolean isDexMode() {
    return this.mFullConfiguration.isDexMode();
  }

  public boolean isNewDexMode() {
    return this.mFullConfiguration.isNewDexMode();
  }

  public boolean isDesktopModeEnabled() {
    return this.mFullConfiguration.semDesktopModeEnabled == 1;
  }

  public boolean isAlwaysOnTopFreeform() {
    return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 5
        && this.mFullConfiguration.windowConfiguration.isAlwaysOnTop();
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

  public void setEmbedActivityMode(int i) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setEmbedActivityMode(i);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }

  public void setDexTaskDocking(int i) {
    this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
    this.mRequestsTmpConfig.windowConfiguration.setDexTaskDockingState(i);
    onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
  }
}
