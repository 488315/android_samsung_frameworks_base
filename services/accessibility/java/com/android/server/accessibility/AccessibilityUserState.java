package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteCallbackList;
import android.os.IInstalld;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.accessibility.AccessibilityShortcutController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class AccessibilityUserState {
  public static final String LOG_TAG = "AccessibilityUserState";
  public boolean mAccessibilityFocusOnlyInActiveWindow;
  public boolean mBindInstantServiceAllowed;
  public Context mContext;
  public int mFocusColor;
  public final int mFocusColorDefaultValue;
  public int mFocusStrokeWidth;
  public final int mFocusStrokeWidthDefaultValue;
  public boolean mIsAMEnabled;
  public boolean mIsAudioDescriptionByDefaultRequested;
  public boolean mIsAutoActionEnabled;
  public boolean mIsAutoclickEnabled;
  public boolean mIsBounceKeysEnabled;
  public boolean mIsCornerActionEnabled;
  public boolean mIsDisplayMagnificationEnabled;
  public boolean mIsFilterKeyEventsEnabled;
  public boolean mIsGestureNaviBar;
  public boolean mIsPerformGesturesEnabled;
  public boolean mIsSlowKeysEnabled;
  public boolean mIsStickyKeysEnabled;
  public boolean mIsTapDurationEnabled;
  public boolean mIsTextHighContrastEnabled;
  public boolean mIsTouchBlockingEnabled;
  public boolean mIsTouchExplorationEnabled;
  public boolean mRequestMultiFingerGestures;
  public boolean mRequestTwoFingerPassthrough;
  public boolean mSendMotionEventsEnabled;
  public ComponentName mServiceChangingSoftKeyboardMode;
  public boolean mServiceHandlesDoubleTap;
  public final ServiceInfoChangeListener mServiceInfoChangeListener;
  public final boolean mSupportWindowMagnification;
  public String mTargetAssignedToAccessibilityButton;
  public final int mUserId;
  public int mUserInteractiveUiTimeout;
  public int mUserNonInteractiveUiTimeout;
  public final RemoteCallbackList mUserClients = new RemoteCallbackList();
  public final ArrayList mBoundServices = new ArrayList();
  public final Map mComponentNameToServiceMap = new HashMap();
  public final List mInstalledServices = new ArrayList();
  public final List mInstalledShortcuts = new ArrayList();
  public final Set mBindingServices = new HashSet();
  public final Set mCrashedServices = new HashSet();
  public final Set mEnabledServices = new HashSet();
  public final Set mTouchExplorationGrantedServices = new HashSet();
  public final LinkedHashSet mAccessibilityShortcutKeyTargets = new LinkedHashSet();
  public final LinkedHashSet mAccessibilityButtonTargets = new LinkedHashSet();
  public final LinkedHashSet mAccessibilityDirectAccessTargets = new LinkedHashSet();
  public SparseArray mServiceDetectsGestures = new SparseArray(0);
  public int mNonInteractiveUiTimeout = 0;
  public int mInteractiveUiTimeout = 0;
  public int mLastSentClientState = -1;
  public final SparseIntArray mMagnificationModes = new SparseIntArray();
  public int mMagnificationCapabilities = 1;
  public boolean mMagnificationFollowTypingEnabled = true;
  public boolean mAlwaysOnMagnificationEnabled = false;
  public int mSoftKeyboardShowMode = 0;

  public interface ServiceInfoChangeListener {
    void onServiceInfoChangedLocked(AccessibilityUserState accessibilityUserState);
  }

  public boolean isValidMagnificationModeLocked(int i) {
    int magnificationModeLocked = getMagnificationModeLocked(i);
    return (this.mSupportWindowMagnification || magnificationModeLocked != 2)
        && (this.mMagnificationCapabilities & magnificationModeLocked) != 0;
  }

  public AccessibilityUserState(
      int i, Context context, ServiceInfoChangeListener serviceInfoChangeListener) {
    this.mUserId = i;
    this.mContext = context;
    this.mServiceInfoChangeListener = serviceInfoChangeListener;
    int dimensionPixelSize =
        context
            .getResources()
            .getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
    this.mFocusStrokeWidthDefaultValue = dimensionPixelSize;
    int color =
        this.mContext.getResources().getColor(R.color.accent_primary_variant_dark_device_default);
    this.mFocusColorDefaultValue = color;
    this.mFocusStrokeWidth = dimensionPixelSize;
    this.mFocusColor = color;
    this.mSupportWindowMagnification = true;
  }

  public final boolean isA11yHelperIsBound() {
    ArrayList arrayList = this.mBoundServices;
    int size = arrayList.size();
    for (int i = 0; i < size; i++) {
      if (((AccessibilityServiceConnection) arrayList.get(i))
          .mComponentName
          .toString()
          .contains("com.samsung.android.app.a11yhelper")) {
        return true;
      }
    }
    return false;
  }

  public boolean isHandlingAccessibilityEventsLocked() {
    return (this.mBoundServices.isEmpty() && this.mBindingServices.isEmpty()) ? false : true;
  }

  public void onSwitchToAnotherUserLocked() {
    unbindAllServicesLocked();
    this.mBoundServices.clear();
    this.mBindingServices.clear();
    this.mCrashedServices.clear();
    this.mLastSentClientState = -1;
    this.mNonInteractiveUiTimeout = 0;
    this.mInteractiveUiTimeout = 0;
    this.mEnabledServices.clear();
    this.mTouchExplorationGrantedServices.clear();
    this.mAccessibilityShortcutKeyTargets.clear();
    this.mAccessibilityButtonTargets.clear();
    this.mTargetAssignedToAccessibilityButton = null;
    this.mIsTouchExplorationEnabled = false;
    this.mServiceHandlesDoubleTap = false;
    this.mRequestMultiFingerGestures = false;
    this.mRequestTwoFingerPassthrough = false;
    this.mSendMotionEventsEnabled = false;
    this.mIsDisplayMagnificationEnabled = false;
    this.mIsAutoclickEnabled = false;
    this.mUserNonInteractiveUiTimeout = 0;
    this.mUserInteractiveUiTimeout = 0;
    this.mMagnificationModes.clear();
    this.mFocusStrokeWidth = this.mFocusStrokeWidthDefaultValue;
    this.mFocusColor = this.mFocusColorDefaultValue;
    this.mMagnificationFollowTypingEnabled = true;
    this.mAlwaysOnMagnificationEnabled = false;
    this.mIsAutoActionEnabled = false;
    this.mIsCornerActionEnabled = false;
    this.mIsTapDurationEnabled = false;
    this.mIsTouchBlockingEnabled = false;
    this.mIsAMEnabled = false;
    this.mIsStickyKeysEnabled = false;
    this.mIsBounceKeysEnabled = false;
    this.mIsSlowKeysEnabled = false;
    this.mIsGestureNaviBar = false;
    this.mAccessibilityDirectAccessTargets.clear();
  }

  public void addServiceLocked(AccessibilityServiceConnection accessibilityServiceConnection) {
    if (this.mBoundServices.contains(accessibilityServiceConnection)) {
      return;
    }
    accessibilityServiceConnection.onAdded();
    this.mBoundServices.add(accessibilityServiceConnection);
    this.mComponentNameToServiceMap.put(
        accessibilityServiceConnection.getComponentName(), accessibilityServiceConnection);
    this.mServiceInfoChangeListener.onServiceInfoChangedLocked(this);
  }

  public void removeServiceLocked(AccessibilityServiceConnection accessibilityServiceConnection) {
    this.mBoundServices.remove(accessibilityServiceConnection);
    accessibilityServiceConnection.onRemoved();
    ComponentName componentName = this.mServiceChangingSoftKeyboardMode;
    if (componentName != null
        && componentName.equals(
            accessibilityServiceConnection.getServiceInfo().getComponentName())) {
      setSoftKeyboardModeLocked(0, null);
    }
    this.mComponentNameToServiceMap.clear();
    for (int i = 0; i < this.mBoundServices.size(); i++) {
      AccessibilityServiceConnection accessibilityServiceConnection2 =
          (AccessibilityServiceConnection) this.mBoundServices.get(i);
      this.mComponentNameToServiceMap.put(
          accessibilityServiceConnection2.getComponentName(), accessibilityServiceConnection2);
    }
    this.mServiceInfoChangeListener.onServiceInfoChangedLocked(this);
  }

  public void serviceDisconnectedLocked(
      AccessibilityServiceConnection accessibilityServiceConnection) {
    removeServiceLocked(accessibilityServiceConnection);
    this.mCrashedServices.add(accessibilityServiceConnection.getComponentName());
  }

  public boolean setSoftKeyboardModeLocked(int i, ComponentName componentName) {
    if (i != 0 && i != 1 && i != 2) {
      Slog.w(LOG_TAG, "Invalid soft keyboard mode");
      return false;
    }
    int i2 = this.mSoftKeyboardShowMode;
    if (i2 == i) {
      return true;
    }
    if (i == 2) {
      if (hasUserOverriddenHardKeyboardSetting()) {
        return false;
      }
      if (getSoftKeyboardValueFromSettings() != 2) {
        setOriginalHardKeyboardValue(
            getSecureIntForUser("show_ime_with_hard_keyboard", 0, this.mUserId) != 0);
      }
      putSecureIntForUser("show_ime_with_hard_keyboard", 1, this.mUserId);
    } else if (i2 == 2) {
      putSecureIntForUser(
          "show_ime_with_hard_keyboard", getOriginalHardKeyboardValue() ? 1 : 0, this.mUserId);
    }
    saveSoftKeyboardValueToSettings(i);
    this.mSoftKeyboardShowMode = i;
    this.mServiceChangingSoftKeyboardMode = componentName;
    for (int size = this.mBoundServices.size() - 1; size >= 0; size--) {
      ((AccessibilityServiceConnection) this.mBoundServices.get(size))
          .notifySoftKeyboardShowModeChangedLocked(this.mSoftKeyboardShowMode);
    }
    return true;
  }

  public int getSoftKeyboardShowModeLocked() {
    return this.mSoftKeyboardShowMode;
  }

  public void reconcileSoftKeyboardModeWithSettingsLocked() {
    boolean z = getSecureIntForUser("show_ime_with_hard_keyboard", 0, this.mUserId) != 0;
    if (this.mSoftKeyboardShowMode == 2 && !z) {
      setSoftKeyboardModeLocked(0, null);
      setUserOverridesHardKeyboardSetting();
    }
    if (getSoftKeyboardValueFromSettings() != this.mSoftKeyboardShowMode) {
      Slog.e(LOG_TAG, "Show IME setting inconsistent with internal state. Overwriting");
      setSoftKeyboardModeLocked(0, null);
      putSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId);
    }
  }

  public boolean getBindInstantServiceAllowedLocked() {
    return this.mBindInstantServiceAllowed;
  }

  public void setBindInstantServiceAllowedLocked(boolean z) {
    this.mBindInstantServiceAllowed = z;
  }

  public Set getBindingServicesLocked() {
    return this.mBindingServices;
  }

  public Set getCrashedServicesLocked() {
    return this.mCrashedServices;
  }

  public Set getEnabledServicesLocked() {
    return this.mEnabledServices;
  }

  public void removeDisabledServicesFromTemporaryStatesLocked() {
    int size = this.mInstalledServices.size();
    for (int i = 0; i < size; i++) {
      ComponentName unflattenFromString =
          ComponentName.unflattenFromString(
              ((AccessibilityServiceInfo) this.mInstalledServices.get(i)).getId());
      if (!this.mEnabledServices.contains(unflattenFromString)) {
        this.mCrashedServices.remove(unflattenFromString);
        this.mBindingServices.remove(unflattenFromString);
      }
    }
  }

  public int getClientStateLocked(boolean z, int i) {
    int i2 = (z || isHandlingAccessibilityEventsLocked()) ? 1 : 0;
    if ((i2 != 0 && this.mIsTouchExplorationEnabled) || isA11yHelperIsBound()) {
      i2 = i2 | 2 | 8 | 16;
    }
    if (this.mIsTextHighContrastEnabled) {
      i2 |= 4;
    }
    if (this.mIsAudioDescriptionByDefaultRequested) {
      i2 |= IInstalld.FLAG_USE_QUOTA;
    }
    return i2 | i;
  }

  public final void setUserOverridesHardKeyboardSetting() {
    putSecureIntForUser(
        "accessibility_soft_keyboard_mode",
        getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) | 1073741824,
        this.mUserId);
  }

  public final boolean hasUserOverriddenHardKeyboardSetting() {
    return (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & 1073741824)
        != 0;
  }

  public final void setOriginalHardKeyboardValue(boolean z) {
    putSecureIntForUser(
        "accessibility_soft_keyboard_mode",
        (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & (-536870913))
            | (z ? 536870912 : 0),
        this.mUserId);
  }

  public final void saveSoftKeyboardValueToSettings(int i) {
    putSecureIntForUser(
        "accessibility_soft_keyboard_mode",
        i | (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & (-4)),
        this.mUserId);
  }

  public final int getSoftKeyboardValueFromSettings() {
    return getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & 3;
  }

  public final boolean getOriginalHardKeyboardValue() {
    return (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & 536870912)
        != 0;
  }

  public final void unbindAllServicesLocked() {
    ArrayList arrayList = this.mBoundServices;
    for (int size = arrayList.size(); size > 0; size--) {
      ((AccessibilityServiceConnection) arrayList.get(0)).unbindLocked();
    }
  }

  public final int getSecureIntForUser(String str, int i, int i2) {
    return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), str, i, i2);
  }

  public final void putSecureIntForUser(String str, int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Settings.Secure.putIntForUser(this.mContext.getContentResolver(), str, i, i2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.append("User state[");
    printWriter.println();
    printWriter.append("     attributes:{id=").append((CharSequence) String.valueOf(this.mUserId));
    printWriter
        .append(", mIsTapDurationEnabled=")
        .append((CharSequence) String.valueOf(this.mIsTapDurationEnabled));
    printWriter
        .append(", mIsTouchBlockingEnabled=")
        .append((CharSequence) String.valueOf(this.mIsTouchBlockingEnabled));
    printWriter
        .append(", mIsStickyKeysEnabled=")
        .append((CharSequence) String.valueOf(this.mIsStickyKeysEnabled));
    printWriter
        .append(", mIsBounceKeysEnabled=")
        .append((CharSequence) String.valueOf(this.mIsBounceKeysEnabled));
    printWriter
        .append(", mIsSlowKeysEnabled=")
        .append((CharSequence) String.valueOf(this.mIsSlowKeysEnabled));
    printWriter
        .append(", mIsAutoActionEnabled=")
        .append((CharSequence) String.valueOf(this.mIsAutoActionEnabled));
    printWriter
        .append(", mIsCornerActionEnabled=")
        .append((CharSequence) String.valueOf(this.mIsCornerActionEnabled));
    printWriter.append(", mIsAMEnabled=").append((CharSequence) String.valueOf(this.mIsAMEnabled));
    printWriter
        .append(", autoActionEnabled=")
        .append((CharSequence) String.valueOf(this.mIsAutoActionEnabled));
    printWriter
        .append(", cornerActionEnabled=")
        .append((CharSequence) String.valueOf(this.mIsCornerActionEnabled));
    printWriter.append(", AmEnabled=").append((CharSequence) String.valueOf(this.mIsAMEnabled));
    printWriter
        .append(", touchExplorationEnabled=")
        .append((CharSequence) String.valueOf(this.mIsTouchExplorationEnabled));
    printWriter
        .append(", serviceHandlesDoubleTap=")
        .append((CharSequence) String.valueOf(this.mServiceHandlesDoubleTap));
    printWriter
        .append(", requestMultiFingerGestures=")
        .append((CharSequence) String.valueOf(this.mRequestMultiFingerGestures));
    printWriter
        .append(", requestTwoFingerPassthrough=")
        .append((CharSequence) String.valueOf(this.mRequestTwoFingerPassthrough));
    printWriter
        .append(", sendMotionEventsEnabled")
        .append((CharSequence) String.valueOf(this.mSendMotionEventsEnabled));
    printWriter
        .append(", displayMagnificationEnabled=")
        .append((CharSequence) String.valueOf(this.mIsDisplayMagnificationEnabled));
    printWriter
        .append(", autoclickEnabled=")
        .append((CharSequence) String.valueOf(this.mIsAutoclickEnabled));
    printWriter
        .append(", nonInteractiveUiTimeout=")
        .append((CharSequence) String.valueOf(this.mNonInteractiveUiTimeout));
    printWriter
        .append(", interactiveUiTimeout=")
        .append((CharSequence) String.valueOf(this.mInteractiveUiTimeout));
    printWriter
        .append(", installedServiceCount=")
        .append((CharSequence) String.valueOf(this.mInstalledServices.size()));
    printWriter
        .append(", magnificationModes=")
        .append((CharSequence) String.valueOf(this.mMagnificationModes));
    printWriter
        .append(", magnificationCapabilities=")
        .append((CharSequence) String.valueOf(this.mMagnificationCapabilities));
    printWriter
        .append(", audioDescriptionByDefaultEnabled=")
        .append((CharSequence) String.valueOf(this.mIsAudioDescriptionByDefaultRequested));
    printWriter
        .append(", magnificationFollowTypingEnabled=")
        .append((CharSequence) String.valueOf(this.mMagnificationFollowTypingEnabled));
    printWriter
        .append(", alwaysOnMagnificationEnabled=")
        .append((CharSequence) String.valueOf(this.mAlwaysOnMagnificationEnabled));
    printWriter.append("}");
    printWriter.println();
    printWriter.append("     shortcut key:{");
    printWriter.append((CharSequence) this.mAccessibilityShortcutKeyTargets.toString());
    printWriter.println("}");
    printWriter.append("     button:{");
    printWriter.append((CharSequence) this.mAccessibilityButtonTargets.toString());
    printWriter.println("}");
    printWriter.append("     direct access:{");
    printWriter.append((CharSequence) this.mAccessibilityDirectAccessTargets.toString());
    printWriter.println("}");
    printWriter
        .append("     button target:{")
        .append((CharSequence) this.mTargetAssignedToAccessibilityButton);
    printWriter.println("}");
    printWriter.println();
    printWriter.append("     installed services: {");
    printWriter.println();
    int size = this.mInstalledServices.size();
    for (int i = 0; i < size; i++) {
      StringBuilder sb = new StringBuilder();
      sb.append("      ");
      sb.append(i);
      sb.append(" : ");
      sb.append(((AccessibilityServiceInfo) this.mInstalledServices.get(i)).getId());
      sb.append(
          ((AccessibilityServiceInfo) this.mInstalledServices.get(i)).isAccessibilityTool()
              ? "  (A11yTool)"
              : "");
      printWriter.append((CharSequence) sb.toString());
      printWriter.println();
    }
    printWriter.append("     }");
    printWriter.println();
    printWriter.append("     Bound services:{");
    int size2 = this.mBoundServices.size();
    for (int i2 = 0; i2 < size2; i2++) {
      if (i2 > 0) {
        printWriter.append(", ");
        printWriter.println();
        printWriter.append("                     ");
      }
      ((AccessibilityServiceConnection) this.mBoundServices.get(i2))
          .dump(fileDescriptor, printWriter, strArr);
    }
    printWriter.println("}");
    printWriter.append("     Enabled services:{");
    Iterator it = this.mEnabledServices.iterator();
    if (it.hasNext()) {
      printWriter.append((CharSequence) ((ComponentName) it.next()).toShortString());
      while (it.hasNext()) {
        ComponentName componentName = (ComponentName) it.next();
        printWriter.append(", ");
        printWriter.append((CharSequence) componentName.toShortString());
      }
    }
    printWriter.println("}");
    printWriter.append("     Binding services:{");
    Iterator it2 = this.mBindingServices.iterator();
    if (it2.hasNext()) {
      printWriter.append((CharSequence) ((ComponentName) it2.next()).toShortString());
      while (it2.hasNext()) {
        ComponentName componentName2 = (ComponentName) it2.next();
        printWriter.append(", ");
        printWriter.append((CharSequence) componentName2.toShortString());
      }
    }
    printWriter.println("}");
    printWriter.append("     Crashed services:{");
    Iterator it3 = this.mCrashedServices.iterator();
    if (it3.hasNext()) {
      printWriter.append((CharSequence) ((ComponentName) it3.next()).toShortString());
      while (it3.hasNext()) {
        ComponentName componentName3 = (ComponentName) it3.next();
        printWriter.append(", ");
        printWriter.append((CharSequence) componentName3.toShortString());
      }
    }
    printWriter.println("}");
    printWriter.println("     Client list info:{");
    this.mUserClients.dump(printWriter, "          Client list ");
    printWriter.println("          Registered clients:{");
    for (int i3 = 0; i3 < this.mUserClients.getRegisteredCallbackCount(); i3++) {
      printWriter.append(
          (CharSequence)
              Arrays.toString(
                  ((AccessibilityManagerService.Client)
                          this.mUserClients.getRegisteredCallbackCookie(i3))
                      .mPackageNames));
    }
    printWriter.println("}]");
  }

  public boolean isAutoclickEnabledLocked() {
    return this.mIsAutoclickEnabled;
  }

  public void setAutoclickEnabledLocked(boolean z) {
    this.mIsAutoclickEnabled = z;
  }

  public boolean isAutoActionEnabledLocked() {
    return this.mIsAutoActionEnabled;
  }

  public void setAutoActionEnabledLocked(boolean z) {
    this.mIsAutoActionEnabled = z;
  }

  public boolean isCornerActionEnabledLocked() {
    return this.mIsCornerActionEnabled;
  }

  public void setCornerActionEnabledLocked(boolean z) {
    this.mIsCornerActionEnabled = z;
  }

  public boolean isAMEnabledLocked() {
    return this.mIsAMEnabled;
  }

  public void setAMEnabledLocked(boolean z) {
    this.mIsAMEnabled = z;
  }

  public boolean isDisplayMagnificationEnabledLocked() {
    return this.mIsDisplayMagnificationEnabled;
  }

  public void setDisplayMagnificationEnabledLocked(boolean z) {
    this.mIsDisplayMagnificationEnabled = z;
  }

  public boolean isFilterKeyEventsEnabledLocked() {
    return this.mIsFilterKeyEventsEnabled;
  }

  public void setFilterKeyEventsEnabledLocked(boolean z) {
    this.mIsFilterKeyEventsEnabled = z;
  }

  public int getInteractiveUiTimeoutLocked() {
    return this.mInteractiveUiTimeout;
  }

  public void setInteractiveUiTimeoutLocked(int i) {
    this.mInteractiveUiTimeout = i;
  }

  public int getLastSentClientStateLocked() {
    return this.mLastSentClientState;
  }

  public void setLastSentClientStateLocked(int i) {
    this.mLastSentClientState = i;
  }

  public boolean isShortcutMagnificationEnabledLocked() {
    return this.mAccessibilityShortcutKeyTargets.contains(
            "com.android.server.accessibility.MagnificationController")
        || this.mAccessibilityButtonTargets.contains(
            "com.android.server.accessibility.MagnificationController")
        || this.mAccessibilityDirectAccessTargets.contains(
            "com.android.server.accessibility.MagnificationController");
  }

  public int getMagnificationModeLocked(int i) {
    int i2 = this.mMagnificationModes.get(i, 0);
    if (i2 != 0) {
      return i2;
    }
    setMagnificationModeLocked(i, 2);
    return 2;
  }

  public int getMagnificationCapabilitiesLocked() {
    return this.mMagnificationCapabilities;
  }

  public void setMagnificationCapabilitiesLocked(int i) {
    this.mMagnificationCapabilities = i;
  }

  public void setMagnificationFollowTypingEnabled(boolean z) {
    this.mMagnificationFollowTypingEnabled = z;
  }

  public boolean isMagnificationFollowTypingEnabled() {
    return this.mMagnificationFollowTypingEnabled;
  }

  public void setAlwaysOnMagnificationEnabled(boolean z) {
    this.mAlwaysOnMagnificationEnabled = z;
  }

  public boolean isAlwaysOnMagnificationEnabled() {
    return this.mAlwaysOnMagnificationEnabled;
  }

  public void setMagnificationModeLocked(int i, int i2) {
    this.mMagnificationModes.put(i, i2);
  }

  public LinkedHashSet getShortcutTargetsLocked(int i) {
    if (i == 1) {
      return this.mAccessibilityShortcutKeyTargets;
    }
    if (i == 0) {
      return this.mAccessibilityButtonTargets;
    }
    if (i == 2) {
      return this.mAccessibilityDirectAccessTargets;
    }
    return null;
  }

  public boolean isShortcutTargetInstalledLocked(String str) {
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if ("com.android.server.accessibility.MagnificationController".equals(str)) {
      return true;
    }
    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
    if (unflattenFromString == null) {
      return false;
    }
    if (AccessibilityShortcutController.getFrameworkShortcutFeaturesMap()
            .containsKey(unflattenFromString)
        || getInstalledServiceInfoLocked(unflattenFromString) != null) {
      return true;
    }
    for (int i = 0; i < this.mInstalledShortcuts.size(); i++) {
      if (((AccessibilityShortcutInfo) this.mInstalledShortcuts.get(i))
          .getComponentName()
          .equals(unflattenFromString)) {
        return true;
      }
    }
    return false;
  }

  public boolean removeShortcutTargetLocked(int i, final ComponentName componentName) {
    return getShortcutTargetsLocked(i)
        .removeIf(
            new Predicate() { // from class:
              // com.android.server.accessibility.AccessibilityUserState$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$removeShortcutTargetLocked$0;
                lambda$removeShortcutTargetLocked$0 =
                    AccessibilityUserState.lambda$removeShortcutTargetLocked$0(
                        componentName, (String) obj);
                return lambda$removeShortcutTargetLocked$0;
              }
            });
  }

  public static /* synthetic */ boolean lambda$removeShortcutTargetLocked$0(
      ComponentName componentName, String str) {
    ComponentName unflattenFromString;
    if (str == null || (unflattenFromString = ComponentName.unflattenFromString(str)) == null) {
      return false;
    }
    return unflattenFromString.equals(componentName);
  }

  public AccessibilityServiceInfo getInstalledServiceInfoLocked(ComponentName componentName) {
    for (int i = 0; i < this.mInstalledServices.size(); i++) {
      AccessibilityServiceInfo accessibilityServiceInfo =
          (AccessibilityServiceInfo) this.mInstalledServices.get(i);
      if (accessibilityServiceInfo.getComponentName().equals(componentName)) {
        return accessibilityServiceInfo;
      }
    }
    return null;
  }

  public AccessibilityServiceConnection getServiceConnectionLocked(ComponentName componentName) {
    return (AccessibilityServiceConnection) this.mComponentNameToServiceMap.get(componentName);
  }

  public int getNonInteractiveUiTimeoutLocked() {
    return this.mNonInteractiveUiTimeout;
  }

  public void setNonInteractiveUiTimeoutLocked(int i) {
    this.mNonInteractiveUiTimeout = i;
  }

  public boolean isPerformGesturesEnabledLocked() {
    return this.mIsPerformGesturesEnabled;
  }

  public void setPerformGesturesEnabledLocked(boolean z) {
    this.mIsPerformGesturesEnabled = z;
  }

  public void setAccessibilityFocusOnlyInActiveWindow(boolean z) {
    this.mAccessibilityFocusOnlyInActiveWindow = z;
  }

  public boolean isTextHighContrastEnabledLocked() {
    return this.mIsTextHighContrastEnabled;
  }

  public void setTextHighContrastEnabledLocked(boolean z) {
    this.mIsTextHighContrastEnabled = z;
  }

  public boolean isAudioDescriptionByDefaultEnabledLocked() {
    return this.mIsAudioDescriptionByDefaultRequested;
  }

  public void setAudioDescriptionByDefaultEnabledLocked(boolean z) {
    this.mIsAudioDescriptionByDefaultRequested = z;
  }

  public boolean isTouchExplorationEnabledLocked() {
    return this.mIsTouchExplorationEnabled;
  }

  public void setTouchExplorationEnabledLocked(boolean z) {
    this.mIsTouchExplorationEnabled = z;
  }

  public boolean isServiceHandlesDoubleTapEnabledLocked() {
    return this.mServiceHandlesDoubleTap;
  }

  public void setServiceHandlesDoubleTapLocked(boolean z) {
    this.mServiceHandlesDoubleTap = z;
  }

  public boolean isMultiFingerGesturesEnabledLocked() {
    return this.mRequestMultiFingerGestures;
  }

  public void setMultiFingerGesturesLocked(boolean z) {
    this.mRequestMultiFingerGestures = z;
  }

  public boolean isTwoFingerPassthroughEnabledLocked() {
    return this.mRequestTwoFingerPassthrough;
  }

  public void setTwoFingerPassthroughLocked(boolean z) {
    this.mRequestTwoFingerPassthrough = z;
  }

  public boolean isSendMotionEventsEnabled() {
    return this.mSendMotionEventsEnabled;
  }

  public void setSendMotionEventsEnabled(boolean z) {
    this.mSendMotionEventsEnabled = z;
  }

  public int getUserInteractiveUiTimeoutLocked() {
    return this.mUserInteractiveUiTimeout;
  }

  public void setUserInteractiveUiTimeoutLocked(int i) {
    this.mUserInteractiveUiTimeout = i;
  }

  public int getUserNonInteractiveUiTimeoutLocked() {
    return this.mUserNonInteractiveUiTimeout;
  }

  public void setUserNonInteractiveUiTimeoutLocked(int i) {
    this.mUserNonInteractiveUiTimeout = i;
  }

  public String getTargetAssignedToAccessibilityButton() {
    return this.mTargetAssignedToAccessibilityButton;
  }

  public void setTargetAssignedToAccessibilityButton(String str) {
    this.mTargetAssignedToAccessibilityButton = str;
  }

  public static boolean doesShortcutTargetsStringContain(Collection collection, String str) {
    if (collection != null && str != null) {
      if (collection.contains(str)) {
        return true;
      }
      ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
      if (unflattenFromString == null) {
        return false;
      }
      Iterator it = collection.iterator();
      while (it.hasNext()) {
        String str2 = (String) it.next();
        if (!TextUtils.isEmpty(str2)
            && unflattenFromString.equals(ComponentName.unflattenFromString(str2))) {
          return true;
        }
      }
    }
    return false;
  }

  public int getFocusStrokeWidthLocked() {
    return this.mFocusStrokeWidth;
  }

  public int getFocusColorLocked() {
    return this.mFocusColor;
  }

  public void setFocusAppearanceLocked(int i, int i2) {
    this.mFocusStrokeWidth = i;
    this.mFocusColor = i2;
  }

  public void setServiceDetectsGesturesEnabled(int i, boolean z) {
    this.mServiceDetectsGestures.put(i, Boolean.valueOf(z));
  }

  public void resetServiceDetectsGestures() {
    this.mServiceDetectsGestures.clear();
  }

  public boolean isServiceDetectsGesturesEnabled(int i) {
    if (this.mServiceDetectsGestures.contains(i)) {
      return ((Boolean) this.mServiceDetectsGestures.get(i)).booleanValue();
    }
    return false;
  }
}
