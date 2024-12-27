package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteCallbackList;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class AccessibilityUserState {
    public boolean mBindInstantServiceAllowed;
    public final Context mContext;
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
    public boolean mIsFilterKeyEventsEnabled;
    public boolean mIsGestureNaviBar;
    public boolean mIsMagnificationSingleFingerTripleTapEnabled;
    public boolean mIsPerformGesturesEnabled;
    public boolean mIsSlowKeysEnabled;
    public boolean mIsStickyKeysEnabled;
    public boolean mIsTapDurationEnabled;
    public boolean mIsTextHighContrastEnabled;
    public boolean mIsTouchBlockingEnabled;
    public boolean mIsTouchExplorationEnabled;
    public boolean mMagnificationTwoFingerTripleTapEnabled;
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
    public final LinkedHashSet mAccessibilityQsTargets = new LinkedHashSet();
    public final LinkedHashSet mAccessibilityShortcutKeyTargets = new LinkedHashSet();
    public final LinkedHashSet mAccessibilityButtonTargets = new LinkedHashSet();
    public final LinkedHashSet mAccessibilityDirectAccessTargets = new LinkedHashSet();
    public final ArraySet mA11yTilesInQsPanel = new ArraySet();
    public final SparseArray mServiceDetectsGestures = new SparseArray(0);
    public int mNonInteractiveUiTimeout = 0;
    public int mInteractiveUiTimeout = 0;
    public int mLastSentClientState = -1;
    public final SparseIntArray mMagnificationModes = new SparseIntArray();
    public int mMagnificationCapabilities = 1;
    public boolean mMagnificationFollowTypingEnabled = true;
    public boolean mAlwaysOnMagnificationEnabled = false;
    public final Map mA11yServiceToTileService = new ArrayMap();
    public final Map mA11yActivityToTileService = new ArrayMap();
    public int mSoftKeyboardShowMode = 0;

    public interface ServiceInfoChangeListener {}

    public AccessibilityUserState(
            int i, Context context, ServiceInfoChangeListener serviceInfoChangeListener) {
        this.mUserId = i;
        this.mContext = context;
        this.mServiceInfoChangeListener = serviceInfoChangeListener;
        int dimensionPixelSize =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
        this.mFocusStrokeWidthDefaultValue = dimensionPixelSize;
        int color =
                context.getResources()
                        .getColor(
                                R.color
                                        .accessibility_magnification_thumbnail_container_background_color);
        this.mFocusColorDefaultValue = color;
        this.mFocusStrokeWidth = dimensionPixelSize;
        this.mFocusColor = color;
        this.mSupportWindowMagnification = true;
    }

    public static boolean doesShortcutTargetsStringContain(String str, Collection collection) {
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

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append("User state[");
        printWriter.println();
        printWriter
                .append("     attributes:{id=")
                .append((CharSequence) String.valueOf(this.mUserId));
        AccessibilityUserState$$ExternalSyntheticOutline0.m(
                        this.mIsAutoclickEnabled,
                        AccessibilityUserState$$ExternalSyntheticOutline0.m(
                                this.mIsMagnificationSingleFingerTripleTapEnabled,
                                AccessibilityUserState$$ExternalSyntheticOutline0.m(
                                        this.mSendMotionEventsEnabled,
                                        AccessibilityUserState$$ExternalSyntheticOutline0.m(
                                                this.mRequestTwoFingerPassthrough,
                                                AccessibilityUserState$$ExternalSyntheticOutline0.m(
                                                        this.mRequestMultiFingerGestures,
                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                .m(
                                                                        this
                                                                                .mServiceHandlesDoubleTap,
                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        this
                                                                                                .mIsTouchExplorationEnabled,
                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        this
                                                                                                                .mIsAMEnabled,
                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                .m(
                                                                                                                        this
                                                                                                                                .mIsCornerActionEnabled,
                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                .m(
                                                                                                                                        this
                                                                                                                                                .mIsAutoActionEnabled,
                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                .m(
                                                                                                                                                        this
                                                                                                                                                                .mIsAMEnabled,
                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                .m(
                                                                                                                                                                        this
                                                                                                                                                                                .mIsCornerActionEnabled,
                                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                                .m(
                                                                                                                                                                                        this
                                                                                                                                                                                                .mIsAutoActionEnabled,
                                                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                                                .m(
                                                                                                                                                                                                        this
                                                                                                                                                                                                                .mIsSlowKeysEnabled,
                                                                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                .mIsBounceKeysEnabled,
                                                                                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                .mIsStickyKeysEnabled,
                                                                                                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                .mIsTouchBlockingEnabled,
                                                                                                                                                                                                                                                        AccessibilityUserState$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                .m(
                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                .mIsTapDurationEnabled,
                                                                                                                                                                                                                                                                        printWriter
                                                                                                                                                                                                                                                                                .append(
                                                                                                                                                                                                                                                                                        ", mIsTapDurationEnabled="),
                                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                                        ", mIsTouchBlockingEnabled="),
                                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                                        ", mIsStickyKeysEnabled="),
                                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                                        ", mIsBounceKeysEnabled="),
                                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                                        ", mIsSlowKeysEnabled="),
                                                                                                                                                                                                        printWriter,
                                                                                                                                                                                                        ", mIsAutoActionEnabled="),
                                                                                                                                                                                        printWriter,
                                                                                                                                                                                        ", mIsCornerActionEnabled="),
                                                                                                                                                                        printWriter,
                                                                                                                                                                        ", mIsAMEnabled="),
                                                                                                                                                        printWriter,
                                                                                                                                                        ", autoActionEnabled="),
                                                                                                                                        printWriter,
                                                                                                                                        ", cornerActionEnabled="),
                                                                                                                        printWriter,
                                                                                                                        ", AmEnabled="),
                                                                                                        printWriter,
                                                                                                        ", touchExplorationEnabled="),
                                                                                        printWriter,
                                                                                        ", serviceHandlesDoubleTap="),
                                                                        printWriter,
                                                                        ", requestMultiFingerGestures="),
                                                        printWriter,
                                                        ", requestTwoFingerPassthrough="),
                                                printWriter,
                                                ", sendMotionEventsEnabled"),
                                        printWriter,
                                        ", displayMagnificationEnabled="),
                                printWriter,
                                ", autoclickEnabled="),
                        printWriter,
                        ", nonInteractiveUiTimeout=")
                .append((CharSequence) String.valueOf(this.mNonInteractiveUiTimeout));
        printWriter
                .append(", interactiveUiTimeout=")
                .append((CharSequence) String.valueOf(this.mInteractiveUiTimeout));
        printWriter
                .append(", installedServiceCount=")
                .append(
                        (CharSequence)
                                String.valueOf(((ArrayList) this.mInstalledServices).size()));
        printWriter
                .append(", magnificationModes=")
                .append((CharSequence) String.valueOf(this.mMagnificationModes));
        printWriter
                .append(", magnificationCapabilities=")
                .append((CharSequence) String.valueOf(this.mMagnificationCapabilities));
        AccessibilityUserState$$ExternalSyntheticOutline0.m(
                        this.mMagnificationFollowTypingEnabled,
                        AccessibilityUserState$$ExternalSyntheticOutline0.m(
                                this.mIsAudioDescriptionByDefaultRequested,
                                printWriter.append(", audioDescriptionByDefaultEnabled="),
                                printWriter,
                                ", magnificationFollowTypingEnabled="),
                        printWriter,
                        ", alwaysOnMagnificationEnabled=")
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
        printWriter
                .append("     qs shortcut targets:")
                .append((CharSequence) this.mAccessibilityQsTargets.toString());
        printWriter.println();
        printWriter
                .append("     a11y tiles in QS panel:")
                .append((CharSequence) this.mA11yTilesInQsPanel.toString());
        printWriter.println();
        printWriter.println();
        printWriter.append("     installed services: {");
        printWriter.println();
        int size = ((ArrayList) this.mInstalledServices).size();
        for (int i = 0; i < size; i++) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "      ", " : ");
            m.append(
                    ((AccessibilityServiceInfo) ((ArrayList) this.mInstalledServices).get(i))
                            .getId());
            m.append(
                    ((AccessibilityServiceInfo) ((ArrayList) this.mInstalledServices).get(i))
                                    .isAccessibilityTool()
                            ? "  (A11yTool)"
                            : "");
            printWriter.append((CharSequence) m.toString());
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
        Iterator it = ((HashSet) this.mEnabledServices).iterator();
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
        Iterator it2 = ((HashSet) this.mBindingServices).iterator();
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
        Iterator it3 = ((HashSet) this.mCrashedServices).iterator();
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
                                                    this.mUserClients.getRegisteredCallbackCookie(
                                                            i3))
                                            .mPackageNames));
        }
        printWriter.println("}]");
    }

    public final Map getA11yFeatureToTileService() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.putAll(this.mA11yServiceToTileService);
        arrayMap.putAll(this.mA11yActivityToTileService);
        return arrayMap;
    }

    public final AccessibilityServiceInfo getInstalledServiceInfoLocked(
            ComponentName componentName) {
        for (int i = 0; i < ((ArrayList) this.mInstalledServices).size(); i++) {
            AccessibilityServiceInfo accessibilityServiceInfo =
                    (AccessibilityServiceInfo) ((ArrayList) this.mInstalledServices).get(i);
            if (accessibilityServiceInfo.getComponentName().equals(componentName)) {
                return accessibilityServiceInfo;
            }
        }
        return null;
    }

    public final int getMagnificationModeLocked(int i) {
        int i2 = this.mMagnificationModes.get(i, 0);
        if (i2 != 0) {
            return i2;
        }
        this.mMagnificationModes.put(i, 1);
        return 1;
    }

    public final int getSecureIntForUser(int i, String str) {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), str, 0, i);
    }

    public final LinkedHashSet getShortcutTargetsLocked(int i) {
        if (i == 2) {
            return this.mAccessibilityShortcutKeyTargets;
        }
        if (i == 1) {
            return this.mAccessibilityButtonTargets;
        }
        if (i == 16) {
            return new LinkedHashSet(this.mAccessibilityQsTargets);
        }
        if ((i != 4 || !this.mIsMagnificationSingleFingerTripleTapEnabled)
                && (i != 8 || !this.mMagnificationTwoFingerTripleTapEnabled)) {
            return i == 512 ? this.mAccessibilityDirectAccessTargets : new LinkedHashSet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add("com.android.server.accessibility.MagnificationController");
        return linkedHashSet;
    }

    public final Map getTileServiceToA11yServiceInfoMapLocked() {
        ArrayMap arrayMap = new ArrayMap();
        Map map =
                (Map)
                        this.mInstalledServices.stream()
                                .collect(
                                        Collectors.toMap(
                                                new AccessibilityUserState$$ExternalSyntheticLambda3(),
                                                Function.identity()));
        for (Map.Entry entry : ((ArrayMap) this.mA11yServiceToTileService).entrySet()) {
            if (map.containsKey(entry.getKey())) {
                arrayMap.put(
                        (ComponentName) entry.getValue(),
                        (AccessibilityServiceInfo) map.get(entry.getKey()));
            }
        }
        return arrayMap;
    }

    public final boolean isHandlingAccessibilityEventsLocked() {
        return (this.mBoundServices.isEmpty() && ((HashSet) this.mBindingServices).isEmpty())
                ? false
                : true;
    }

    public final boolean isShortcutMagnificationEnabledLocked() {
        return this.mAccessibilityShortcutKeyTargets.contains(
                        "com.android.server.accessibility.MagnificationController")
                || this.mAccessibilityButtonTargets.contains(
                        "com.android.server.accessibility.MagnificationController")
                || this.mAccessibilityDirectAccessTargets.contains(
                        "com.android.server.accessibility.MagnificationController");
    }

    public final boolean isShortcutTargetInstalledLocked(String str) {
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
        for (int i = 0; i < ((ArrayList) this.mInstalledShortcuts).size(); i++) {
            if (((AccessibilityShortcutInfo) ((ArrayList) this.mInstalledShortcuts).get(i))
                    .getComponentName()
                    .equals(unflattenFromString)) {
                return true;
            }
        }
        return false;
    }

    public final void onSwitchToAnotherUserLocked() {
        ArrayList arrayList = this.mBoundServices;
        for (int size = arrayList.size(); size > 0; size--) {
            ((AccessibilityServiceConnection) arrayList.get(0)).unbindLocked();
        }
        this.mBoundServices.clear();
        ((HashSet) this.mBindingServices).clear();
        ((HashSet) this.mCrashedServices).clear();
        this.mLastSentClientState = -1;
        this.mNonInteractiveUiTimeout = 0;
        this.mInteractiveUiTimeout = 0;
        ((HashSet) this.mEnabledServices).clear();
        ((HashSet) this.mTouchExplorationGrantedServices).clear();
        this.mAccessibilityShortcutKeyTargets.clear();
        this.mAccessibilityButtonTargets.clear();
        this.mTargetAssignedToAccessibilityButton = null;
        this.mIsTouchExplorationEnabled = false;
        this.mServiceHandlesDoubleTap = false;
        this.mRequestMultiFingerGestures = false;
        this.mRequestTwoFingerPassthrough = false;
        this.mSendMotionEventsEnabled = false;
        this.mIsMagnificationSingleFingerTripleTapEnabled = false;
        this.mMagnificationTwoFingerTripleTapEnabled = false;
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

    public final void putSecureIntForUser(int i, int i2, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), str, i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reconcileSoftKeyboardModeWithSettingsLocked() {
        int i = this.mUserId;
        boolean z = getSecureIntForUser(i, "show_ime_with_hard_keyboard") != 0;
        if (this.mSoftKeyboardShowMode == 2 && !z) {
            setSoftKeyboardModeLocked(0, null);
            putSecureIntForUser(
                    getSecureIntForUser(i, "accessibility_soft_keyboard_mode") | 1073741824,
                    i,
                    "accessibility_soft_keyboard_mode");
        }
        if ((getSecureIntForUser(i, "accessibility_soft_keyboard_mode") & 3)
                != this.mSoftKeyboardShowMode) {
            Slog.e(
                    "AccessibilityUserState",
                    "Show IME setting inconsistent with internal state. Overwriting");
            setSoftKeyboardModeLocked(0, null);
            putSecureIntForUser(0, i, "accessibility_soft_keyboard_mode");
        }
    }

    public final void removeDisabledServicesFromTemporaryStatesLocked() {
        int size = ((ArrayList) this.mInstalledServices).size();
        for (int i = 0; i < size; i++) {
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(
                            ((AccessibilityServiceInfo)
                                            ((ArrayList) this.mInstalledServices).get(i))
                                    .getId());
            if (!((HashSet) this.mEnabledServices).contains(unflattenFromString)) {
                ((HashSet) this.mCrashedServices).remove(unflattenFromString);
                ((HashSet) this.mBindingServices).remove(unflattenFromString);
            }
        }
    }

    public final void removeServiceLocked(
            AccessibilityServiceConnection accessibilityServiceConnection) {
        this.mBoundServices.remove(accessibilityServiceConnection);
        accessibilityServiceConnection.onRemoved();
        ComponentName componentName = this.mServiceChangingSoftKeyboardMode;
        if (componentName != null
                && componentName.equals(
                        accessibilityServiceConnection.mAccessibilityServiceInfo
                                .getComponentName())) {
            setSoftKeyboardModeLocked(0, null);
        }
        ((HashMap) this.mComponentNameToServiceMap).clear();
        for (int i = 0; i < this.mBoundServices.size(); i++) {
            AccessibilityServiceConnection accessibilityServiceConnection2 =
                    (AccessibilityServiceConnection) this.mBoundServices.get(i);
            ((HashMap) this.mComponentNameToServiceMap)
                    .put(
                            accessibilityServiceConnection2.mComponentName,
                            accessibilityServiceConnection2);
        }
        ((AccessibilityManagerService) this.mServiceInfoChangeListener)
                .onServiceInfoChangedLocked(this);
    }

    public final boolean removeShortcutTargetLocked(int i, final ComponentName componentName) {
        if (i == 4 || i == 8) {
            throw new UnsupportedOperationException(
                    "removeShortcutTargetLocked only support shortcut type: software and hardware"
                        + " and quick settings for now");
        }
        LinkedHashSet shortcutTargetsLocked = getShortcutTargetsLocked(i);
        boolean removeIf =
                shortcutTargetsLocked.removeIf(
                        new Predicate() { // from class:
                                          // com.android.server.accessibility.AccessibilityUserState$$ExternalSyntheticLambda4
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                ComponentName unflattenFromString;
                                ComponentName componentName2 = componentName;
                                String str = (String) obj;
                                if (str == null
                                        || (unflattenFromString =
                                                        ComponentName.unflattenFromString(str))
                                                == null) {
                                    return false;
                                }
                                return unflattenFromString.equals(componentName2);
                            }
                        });
        if (i == 16) {
            updateA11yQsTargetLocked(shortcutTargetsLocked);
        }
        return removeIf;
    }

    public final boolean setSoftKeyboardModeLocked(int i, ComponentName componentName) {
        if (i != 0 && i != 1 && i != 2) {
            Slog.w("AccessibilityUserState", "Invalid soft keyboard mode");
            return false;
        }
        int i2 = this.mSoftKeyboardShowMode;
        if (i2 == i) {
            return true;
        }
        int i3 = this.mUserId;
        if (i == 2) {
            if ((getSecureIntForUser(i3, "accessibility_soft_keyboard_mode") & 1073741824) != 0) {
                return false;
            }
            if ((getSecureIntForUser(i3, "accessibility_soft_keyboard_mode") & 3) != 2) {
                putSecureIntForUser(
                        (getSecureIntForUser(i3, "accessibility_soft_keyboard_mode") & (-536870913))
                                | (getSecureIntForUser(i3, "show_ime_with_hard_keyboard") != 0
                                        ? 536870912
                                        : 0),
                        i3,
                        "accessibility_soft_keyboard_mode");
            }
            putSecureIntForUser(1, i3, "show_ime_with_hard_keyboard");
        } else if (i2 == 2) {
            putSecureIntForUser(
                    (getSecureIntForUser(i3, "accessibility_soft_keyboard_mode") & 536870912) != 0
                            ? 1
                            : 0,
                    i3,
                    "show_ime_with_hard_keyboard");
        }
        putSecureIntForUser(
                (getSecureIntForUser(i3, "accessibility_soft_keyboard_mode") & (-4)) | i,
                i3,
                "accessibility_soft_keyboard_mode");
        this.mSoftKeyboardShowMode = i;
        this.mServiceChangingSoftKeyboardMode = componentName;
        for (int size = this.mBoundServices.size() - 1; size >= 0; size--) {
            AccessibilityServiceConnection accessibilityServiceConnection =
                    (AccessibilityServiceConnection) this.mBoundServices.get(size);
            int i4 = this.mSoftKeyboardShowMode;
            AbstractAccessibilityServiceConnection.InvocationHandler invocationHandler =
                    accessibilityServiceConnection.mInvocationHandler;
            if (invocationHandler.mIsSoftKeyboardCallbackEnabled) {
                invocationHandler.obtainMessage(6, i4, 0).sendToTarget();
            }
        }
        return true;
    }

    public final void updateA11yQsTargetLocked(Set set) {
        this.mAccessibilityQsTargets.clear();
        this.mAccessibilityQsTargets.addAll(set);
    }
}
