package com.android.server.policy;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.INetd;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;

import com.samsung.android.rune.InputRune;
import com.samsung.android.view.SemWindowManager;

public final class KeyCustomizationManager {
    public long defaultLongPressTimeout;
    public final Context mContext;
    public final KeyCustomizationInfoManager mKeyCustomizationInfoManager;
    public final PhoneWindowManagerExt mPolicyExt;
    public boolean mIsKeyLongPressed = false;
    public boolean mIsKeyLongConsumed = false;
    public boolean mIsXCoverKeyOnLockScreen = false;
    public boolean mIsTopKeyOnLockScreen = false;
    public KeyguardManager mKeyguardManager = null;
    public boolean mIsCalledOpenDictationXCoverTop = false;

    public KeyCustomizationManager(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
        this.mContext = context;
        this.mPolicyExt = phoneWindowManagerExt;
        this.mKeyCustomizationInfoManager = new KeyCustomizationInfoManager(context);
    }

    public static String actionToString(int i) {
        return i != 1
                ? i != 2
                        ? i != 3
                                ? i != 4 ? Integer.toString(i) : "ACTION_BLOCK_KEY_EVENT"
                                : "ACTION_START_SERVICE"
                        : "ACTION_SEND_BROADCAST"
                : "ACTION_START_ACTIVITY";
    }

    public static String getEventId(int i, int i2) {
        if (i2 == 1015) {
            if ((i & 3) != 0) {
                return "HWB1101";
            }
            if ((i & 4) != 0) {
                return "HWB1106";
            }
            return null;
        }
        if (i2 != 1079) {
            return null;
        }
        if ((i & 3) != 0) {
            return "HWB1107";
        }
        if ((i & 4) != 0) {
            return "HWB1112";
        }
        return null;
    }

    public static String idToString(int i) {
        if (i == 10) {
            return "ID_KNOX_MDM";
        }
        if (i == 30) {
            return "ID_KNOX_V2";
        }
        if (i == 50) {
            return "ID_KNOX_LEGACY";
        }
        if (i == 951) {
            return "ID_SETTING_UI_B2B_DELTA";
        }
        if (i == 1000) {
            return "ID_DEFAULT";
        }
        if (i == 1100) {
            return "ID_SETTING_UI";
        }
        if (i == 1103) {
            return "ID_SETTING_UI_XCOVER_TOP";
        }
        if (i == 1104) {
            return "ID_SETTING_UI_SIDE_KEY";
        }
        if (i == 1106) {
            return "ID_SETTING_UI_ONE_HAND_MODE";
        }
        if (i == 1107) {
            return "ID_SETTING_UI_MOUSE_BUTTON";
        }
        switch (i) {
            case 2001:
                return "ID_APPLICATION_UI_CAMERA";
            case 2002:
                return "ID_APPLICATION_UI_TV_MODE";
            case 2003:
                return "ID_GENERAL_APPLICATION";
            default:
                return Integer.toString(i);
        }
    }

    public static boolean isKnoxId(int i) {
        return i == 10 || i == 30 || i == 50;
    }

    public static boolean isReportedKey(boolean z, Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(z ? "reportStateOnKeyedDown" : "reportStateOnKeyedUp");
    }

    public static boolean isXCoverOrTopKey(int i) {
        return i == 1015 || i == 1079;
    }

    public static String pressToString(int i) {
        return (i & 3) != 0
                ? "KEY_PRESS_SINGLE"
                : (i & 4) != 0
                        ? "KEY_PRESS_LONG"
                        : (i & 8) != 0
                                ? "KEY_PRESS_DOUBLE"
                                : (i & 16) != 0
                                        ? "KEY_PRESS_TRIPLE"
                                        : (i & 32) != 0
                                                ? "KEY_PRESS_QUADRUPLE"
                                                : (i & 64) != 0
                                                        ? "KEY_PRESS_QUINTUPLE"
                                                        : Integer.toString(i);
    }

    public final boolean canDispatchXCoverTopKeyEvent(int i) {
        if (this.mPolicyExt.mPolicy.mPowerManager.isInteractive()) {
            return true;
        }
        for (int i2 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_XCOVER_TOP) {
            int lastId = getLastId(i2, i);
            if (lastId != -1 && lastId < 1000) {
                Log.d(
                        "KeyCustomizationManager",
                        "Allow XCover or Top key event dispatching even though screen is turned off"
                            + " by knox policy.");
                return true;
            }
        }
        for (int i3 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_XCOVER_TOP) {
            if (hasXCoverTopId(i3, i)) {
                Log.d(
                        "KeyCustomizationManager",
                        "After screen is turned off, disallow XCover or Top key event dispatching,"
                            + " because single or long press has a ID_SETTING_UI_XCOVER_TOP.");
                return false;
            }
        }
        return true;
    }

    public final int dispatchKeyCustomizationKeyEvent(KeyEvent keyEvent, boolean z) {
        boolean z2;
        boolean z3;
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        boolean isLongPress = keyEvent.isLongPress();
        boolean z4 = keyEvent.getAction() == 0;
        int lastAction = getLastAction(3, keyCode);
        int lastAction2 = getLastAction(4, keyCode);
        boolean z5 = z ? lastAction2 != -1 : z;
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        boolean z6 = PhoneWindowManager.DEBUG_INPUT;
        if (z6) {
            StringBuilder sb = new StringBuilder("dispatchKeyEvent: keyCode=");
            sb.append(keyCode);
            sb.append(" down=");
            sb.append(z4);
            sb.append(" repeatCount=");
            sb.append(repeatCount);
            sb.append(" isLongPress=");
            sb.append(isLongPress);
            sb.append(" pressAction=");
            sb.append(actionToString(lastAction));
            sb.append(" longPressAction=");
            sb.append(actionToString(lastAction2));
            sb.append(" forceDispatching=");
            AnyMotionDetector$$ExternalSyntheticOutline0.m("KeyCustomizationManager", sb, z5);
        }
        int i = (lastAction2 == -1 || lastAction2 == 4) ? lastAction : lastAction2;
        if (i != 0 && i != 1 && i != 2 && i != 3) {
            if (z6) {
                Slog.d(
                        "KeyCustomizationManager",
                        "dispatchKeyEvent - default, there is no action, NEEDED_NEXT_STEP");
            }
            return 2;
        }
        if (z4) {
            if (repeatCount == 0) {
                this.mIsKeyLongPressed = false;
                this.mIsKeyLongConsumed = false;
                if (InputRune.PWM_B2B_DEDICATED_APP
                        && startMCPTTServiceIfNeeded(keyEvent, keyCode)) {
                    if (z6) {
                        Slog.d(
                                "KeyCustomizationManager",
                                "dispatchKeyEvent:down - launch MCPTT, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
                if (InputRune.PWM_XCOVER_AND_TOP_KEY) {
                    prepareVoiceToTextMessage(keyEvent, keyCode);
                }
                if (lastAction == 2 && sendBroadcast(null, keyEvent, 3, false) == -1) {
                    if (z6) {
                        Slog.d(
                                "KeyCustomizationManager",
                                "dispatchKeyEvent:down - sendBroadcast, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
            }
            if (isLongPress) {
                this.mIsKeyLongPressed = true;
                if (InputRune.PWM_ACTIVE_OR_XCOVER_KEY) {
                    if (!isXCoverOrTopKey(keyCode) ? false : hasXCoverTopId(4, keyCode)) {
                        if (!launchXCoverLongActionIfNeeded(keyEvent, false)) {
                            if (z6) {
                                Slog.d(
                                        "KeyCustomizationManager",
                                        "dispatchKeyEvent:longPress - XCoverTopKey Action,"
                                            + " NEEDED_NEXT_STEP");
                            }
                            return 2;
                        }
                        if (z6) {
                            Slog.d(
                                    "KeyCustomizationManager",
                                    "dispatchKeyEvent:longPress - XCoverTopKey Action,"
                                        + " NO_NEED_NEXT_STEP");
                        }
                        this.mIsKeyLongConsumed = true;
                        return 1;
                    }
                }
                if (launchLongPressAction(lastAction2, keyEvent, keyCode)) {
                    if (z6) {
                        Slog.d(
                                "KeyCustomizationManager",
                                "dispatchKeyEvent:longPress - launch Action, NO_NEED_NEXT_STEP");
                    }
                    this.mIsKeyLongConsumed = true;
                    return 1;
                }
                if (!z6) {
                    return 3;
                }
                Slog.d(
                        "KeyCustomizationManager",
                        "dispatchKeyEvent:longPress, NEEDED_ONLY_LONG_PRESS_STEP");
                return 3;
            }
            if (InputRune.PWM_ACTIVE_OR_XCOVER_KEY) {
                if (isXCoverOrTopKey(keyCode)) {
                    for (int i2 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_XCOVER_TOP) {
                        if (hasXCoverTopId(i2, keyCode)) {
                            z3 = true;
                            break;
                        }
                    }
                }
                z3 = false;
                if (z3) {
                    if (z6) {
                        Slog.d(
                                "KeyCustomizationManager",
                                "dispatchKeyEvent:down - XCoverTopKey Action, NEEDED_NEXT_STEP");
                    }
                    return 2;
                }
            }
        } else {
            if (InputRune.PWM_XCOVER_AND_TOP_KEY) {
                prepareVoiceToTextMessage(keyEvent, keyCode);
            }
            if (lastAction == 2 && sendBroadcast(null, keyEvent, 3, this.mIsKeyLongPressed) == -1) {
                if (z6) {
                    Slog.d(
                            "KeyCustomizationManager",
                            "dispatchKeyEvent:up - sendBroadcast, NO_NEED_NEXT_STEP");
                }
                return 1;
            }
            if (InputRune.PWM_ACTIVE_OR_XCOVER_KEY) {
                if (!this.mIsKeyLongPressed) {
                    if (!isXCoverOrTopKey(keyCode) ? false : hasXCoverTopId(3, keyCode)) {
                        if (launchXCoverPressActionIfNeeded(keyEvent, false)) {
                            if (z6) {
                                Slog.d(
                                        "KeyCustomizationManager",
                                        "dispatchKeyEvent:up - XCoverTopKey Action,"
                                            + " NO_NEED_NEXT_STEP");
                            }
                            return 1;
                        }
                        if (z6) {
                            Slog.d(
                                    "KeyCustomizationManager",
                                    "dispatchKeyEvent:up - XCoverTopKey Action, NEEDED_NEXT_STEP");
                        }
                        return 2;
                    }
                } else if (!this.mIsKeyLongConsumed) {
                    if (isXCoverOrTopKey(keyCode)) {
                        for (int i3 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_XCOVER_TOP) {
                            if (hasXCoverTopId(i3, keyCode)) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                        if (z6) {
                            Slog.d(
                                    "KeyCustomizationManager",
                                    "dispatchKeyEvent:up - XCoverTopKey Action, No longPress"
                                        + " consumed, NEEDED_NEXT_STEP");
                        }
                        return 2;
                    }
                }
            }
            if (!this.mIsKeyLongPressed) {
                launchPressAction(lastAction, keyEvent, keyCode, z5);
            } else {
                if (InputRune.PWM_B2B_DEDICATED_APP
                        && startMCPTTServiceIfNeeded(keyEvent, keyCode)) {
                    if (z6) {
                        Slog.d(
                                "KeyCustomizationManager",
                                "dispatchKeyEvent:up - launch MCPTT, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
                if (this.mIsKeyLongConsumed) {
                    if (z6) {
                        Slog.d(
                                "KeyCustomizationManager",
                                "dispatchKeyEvent:up - longPress, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
            }
        }
        if (lastAction == 1 || lastAction == 3 || z5) {
            if (z6) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("dispatchKeyEvent:"),
                        z4 ? INetd.IF_STATE_DOWN : INetd.IF_STATE_UP,
                        " - forceDispatching or press action, NO_NEED_NEXT_STEP",
                        "KeyCustomizationManager");
            }
            return 1;
        }
        if (z6) {
            Slog.d("KeyCustomizationManager", "dispatchKeyEvent - default, NEEDED_NEXT_STEP");
        }
        return 2;
    }

    public final int getLastAction(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo last =
                this.mKeyCustomizationInfoManager.getLast(i, i2);
        if (last == null) {
            return -1;
        }
        return last.action;
    }

    public final int getLastId(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo last =
                this.mKeyCustomizationInfoManager.getLast(i, i2);
        if (last == null) {
            return -1;
        }
        return last.id;
    }

    public final boolean hasHigherIdWithAllPress(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo last;
        int i3;
        int lastId = getLastId(i, i2);
        if (lastId >= 1000 || lastId == -1) {
            return false;
        }
        int i4 = 2003;
        for (int i5 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
            if (i5 != i
                    && (last = this.mKeyCustomizationInfoManager.getLast(i5, i2)) != null
                    && i4 > (i3 = last.id)) {
                i4 = i3;
            }
        }
        return i4 < lastId;
    }

    public final boolean hasHigherIdWithOppositePress(int i, int i2) {
        int lastId;
        if (this.mPolicyExt.mPolicy.mPowerManager.isInteractive()
                || (lastId = getLastId(i, i2)) == -1
                || lastId >= 1000) {
            return false;
        }
        GestureWakeup$$ExternalSyntheticOutline0.m(
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "hasHigherIdInOppositePress, press=", " keyCode=", " id="),
                lastId,
                "KeyCustomizationManager");
        return true;
    }

    public final boolean hasLastInfo(int i, int i2) {
        return this.mKeyCustomizationInfoManager.getLast(i, i2) != null;
    }

    public final boolean hasXCoverTopId(int i, int i2) {
        return getLastId(i, i2) == 1103;
    }

    public final void initPowerBehaviorAndSingleKeyGestureDetectorRule(int i) {
        SingleKeyGestureDetector.SingleKeyRule singleKeyRule;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        if (i != 26) {
            phoneWindowManagerExt.updateSingleKeyGestureRule(i);
        }
        long j = 0;
        for (int i2 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
            if (i == 26) {
                updatePowerBehavior(i2);
            }
            if ((i2 & 4) != 0) {
                updateLongPressTimeoutIfNeeded(i2, i, true);
            } else {
                SemWindowManager.KeyCustomizationInfo last =
                        this.mKeyCustomizationInfoManager.getLast(i2, i);
                long j2 = last == null ? 0L : last.multiPressTimeout;
                if (j < j2) {
                    j = j2;
                }
            }
        }
        if (j == 0
                || (singleKeyRule =
                                (SingleKeyGestureDetector.SingleKeyRule)
                                        phoneWindowManagerExt.mPolicy.mSingleKeyGestureDetector
                                                .mCustomRules.get(i))
                        == null) {
            return;
        }
        long j3 = singleKeyRule.extensionMultiPressTimeout;
        if (j3 == 0) {
            j3 = SingleKeyGestureDetector.sDefaultMultiPressTimeout;
        }
        if (j == j3) {
            return;
        }
        singleKeyRule.extensionMultiPressTimeout =
                j != SingleKeyGestureDetector.sDefaultMultiPressTimeout ? j : 0L;
    }

    public final boolean launchLongPressAction(int i, KeyEvent keyEvent, int i2) {
        if (hasHigherIdWithAllPress(4, i2)) {
            return false;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i2, "launchLongPressAction, keyCode=", " ");
        m.append(actionToString(i));
        Log.d("KeyCustomizationManager", m.toString());
        KeyCustomizationInfoManager keyCustomizationInfoManager = this.mKeyCustomizationInfoManager;
        if (i == 1) {
            SemWindowManager.KeyCustomizationInfo last = keyCustomizationInfoManager.getLast(4, i2);
            if (last != null) {
                startActivity(last);
            }
        } else if (i != 2) {
            if (i == 3) {
                SemWindowManager.KeyCustomizationInfo last2 =
                        keyCustomizationInfoManager.getLast(4, i2);
                if (last2 != null) {
                    startService(last2, 4, i2, keyEvent);
                }
            } else {
                if (i != 4) {
                    return false;
                }
                Log.d(
                        "KeyCustomizationManager",
                        "launchLongPressAction was blocked by KeyCustomizationPolicy.");
            }
        } else if (sendBroadcast(null, keyEvent, 4, false) == 0) {
            return false;
        }
        return true;
    }

    public final boolean launchLongPressAction(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        int lastAction = getLastAction(4, keyCode);
        if (lastAction == -1) {
            return false;
        }
        return launchLongPressAction(lastAction, keyEvent, keyCode);
    }

    public final void launchMultiPressAction(KeyEvent keyEvent, int i) {
        int keyCode;
        SemWindowManager.KeyCustomizationInfo last;
        int i2;
        if (keyEvent == null) {
            Log.e(
                    "KeyCustomizationManager",
                    "launchMultiPressAction, event is null, Callers=" + Debug.getCallers(5));
            return;
        }
        int i3 = i == 2 ? 8 : i == 3 ? 16 : i == 4 ? 32 : i == 5 ? 64 : 0;
        if (i3 == 0
                || (last =
                                this.mKeyCustomizationInfoManager.getLast(
                                        i3, (keyCode = keyEvent.getKeyCode())))
                        == null
                || (i2 = last.action) == 4
                || hasHigherIdWithAllPress(i3, keyCode)) {
            return;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                i,
                keyCode,
                "launchMultiPressAction, count=",
                " keyCode=",
                "KeyCustomizationManager");
        if (i2 == 1) {
            startActivity(last);
            return;
        }
        if (i2 == 2) {
            sendBroadcast(null, keyEvent, i3, false);
            return;
        }
        if (i2 == 3) {
            startService(last, i3, keyCode, keyEvent);
            return;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "launchMultiPressAction, count=", " ");
        m.append(actionToString(i2));
        m.append(" was wrong.");
        Log.e("KeyCustomizationManager", m.toString());
    }

    public final boolean launchPressAction(int i, KeyEvent keyEvent, int i2, boolean z) {
        if (hasHigherIdWithAllPress(3, i2)) {
            return false;
        }
        KeyCustomizationInfoManager keyCustomizationInfoManager = this.mKeyCustomizationInfoManager;
        if (i == 1) {
            SemWindowManager.KeyCustomizationInfo last = keyCustomizationInfoManager.getLast(3, i2);
            if (last != null) {
                startActivity(last);
            }
        } else if (i == 3) {
            SemWindowManager.KeyCustomizationInfo last2 =
                    keyCustomizationInfoManager.getLast(3, i2);
            if (last2 != null) {
                startService(last2, 3, i2, keyEvent);
            }
        } else {
            if (!z) {
                return false;
            }
            this.mPolicyExt.injectionKeyEvent(i2, 268435456, -1);
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(i2, "launchPressAction, keyCode=", " ");
        m.append(actionToString(i));
        m.append(" forceDispatching=");
        m.append(z);
        Log.d("KeyCustomizationManager", m.toString());
        return true;
    }

    public final void launchPressSendBroadcast(KeyEvent keyEvent, int i, boolean z) {
        SemWindowManager.KeyCustomizationInfo last =
                this.mKeyCustomizationInfoManager.getLast(3, i);
        if (last == null) {
            return;
        }
        Log.d(
                "KeyCustomizationManager",
                "launchPressSendBroadcast, keyCode=" + i + " isKeyLongPressed=" + z);
        sendBroadcast(last, keyEvent, 3, z);
    }

    public final boolean launchXCoverDefaultAction(
            String str, int i, KeyEvent keyEvent, boolean z) {
        if ("torch/torch".equals(str)) {
            boolean z2 =
                    keyEvent.getKeyCode() == 1079
                            ? this.mIsTopKeyOnLockScreen
                            : this.mIsXCoverKeyOnLockScreen;
            int keyCode = keyEvent.getKeyCode();
            PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
            if (!phoneWindowManagerExt.mPolicy.keyguardOn() || z2) {
                phoneWindowManagerExt.onFlashlightKeyPressed(keyCode);
                if (InputRune.PWM_KEY_SA_LOGGING) {
                    PhoneWindowManagerExt.sendCoreSaLoggingDimension(
                            getEventId(i, keyEvent.getKeyCode()), "Torch");
                }
                return true;
            }
        }
        if (!z) {
            return false;
        }
        if (!"com.sec.android.app.camera/com.sec.android.app.camera.Camera".equals(str)) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            SemWindowManager.KeyCustomizationInfo last =
                    this.mKeyCustomizationInfoManager.getLast(i, keyEvent.getKeyCode());
            if (last != null) {
                startActivity(last);
            }
            return true;
        }
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(
                        "com.sec.android.app.camera/com.sec.android.app.camera.Camera");
        Intent m =
                PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.MAIN", "android.intent.category.LAUNCHER");
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        }
        m.putExtra("isSecure", this.mKeyguardManager.isKeyguardSecure());
        m.putExtra("isQuickLaunchMode", true);
        m.setComponent(unflattenFromString);
        m.setFlags(268435456);
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        }
        if (this.mKeyguardManager.semIsKeyguardShowingAndNotOccluded()) {
            m.addFlags(603979776);
        } else {
            m.addFlags(2097152);
        }
        try {
            this.mContext.startActivity(m);
        } catch (ActivityNotFoundException e) {
            Slog.w("KeyCustomizationManager", "No activity to launch Camera, ", e);
        }
        return true;
    }

    public final boolean launchXCoverLongActionIfNeeded(KeyEvent keyEvent, boolean z) {
        Intent intent;
        if (FactoryTest.isAutomaticTestMode(this.mContext)) {
            Slog.d(
                    "KeyCustomizationManager",
                    "Block handling XCoverKey because of Automatic Test Mode");
            return false;
        }
        if (hasHigherIdWithOppositePress(3, keyEvent.getKeyCode())) {
            Log.d("KeyCustomizationManager", "Can not launch long press action by knox policy");
            return true;
        }
        SemWindowManager.KeyCustomizationInfo last =
                this.mKeyCustomizationInfoManager.getLast(4, keyEvent.getKeyCode());
        ComponentName component =
                (last == null || (intent = last.intent) == null) ? null : intent.getComponent();
        if (component == null) {
            return false;
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        phoneWindowManagerExt.getClass();
        if (PhoneWindowManagerExt.isCameraRunning() && !z) {
            Slog.d(
                    "KeyCustomizationManager",
                    "Can not launch dedicated long action. Camera is running.");
            return false;
        }
        String flattenToString = component.flattenToString();
        if (!"quickMessageSender/quickMessageSender".equals(flattenToString)) {
            if (launchXCoverTopDedicatedAction(flattenToString, 4, keyEvent)) {
                return true;
            }
            return launchXCoverDefaultAction(flattenToString, 4, keyEvent, z);
        }
        if (phoneWindowManagerExt.mIsSamsungKeyboard) {
            this.mIsCalledOpenDictationXCoverTop = true;
            phoneWindowManagerExt.callDictation(keyEvent.getKeyCode(), "open_dictation");
        } else {
            Context context = this.mContext;
            phoneWindowManagerExt.showToast(context, context.getResources().getString(17043443));
        }
        if (InputRune.PWM_KEY_SA_LOGGING) {
            PhoneWindowManagerExt.sendCoreSaLoggingDimension(
                    getEventId(4, keyEvent.getKeyCode()), "Voice to text message");
        }
        return true;
    }

    public final boolean launchXCoverPressActionIfNeeded(KeyEvent keyEvent, boolean z) {
        Intent intent;
        if (FactoryTest.isAutomaticTestMode(this.mContext)) {
            Slog.d(
                    "KeyCustomizationManager",
                    "Block handling XCoverKey because of Automatic Test Mode");
            return false;
        }
        if (hasHigherIdWithOppositePress(4, keyEvent.getKeyCode())) {
            Log.d("KeyCustomizationManager", "Can not launch press action by knox policy");
            return true;
        }
        SemWindowManager.KeyCustomizationInfo last =
                this.mKeyCustomizationInfoManager.getLast(3, keyEvent.getKeyCode());
        ComponentName component =
                (last == null || (intent = last.intent) == null) ? null : intent.getComponent();
        if (component == null) {
            return false;
        }
        String flattenToString = component.flattenToString();
        this.mPolicyExt.getClass();
        if (PhoneWindowManagerExt.isCameraRunning() && !z) {
            Slog.d(
                    "KeyCustomizationManager",
                    "Can not launch dedicated action. Camera is running.");
            return false;
        }
        if (launchXCoverTopDedicatedAction(flattenToString, 3, keyEvent)) {
            return true;
        }
        return launchXCoverDefaultAction(flattenToString, 3, keyEvent, z);
    }

    public final boolean launchXCoverTopDedicatedAction(String str, int i, KeyEvent keyEvent) {
        String str2;
        boolean equals = "home/home".equals(str);
        boolean z = true;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        if (equals) {
            phoneWindowManagerExt.mPolicy.postShortPressOnHome(keyEvent);
            str2 = "Home";
        } else if ("back/back".equals(str)) {
            phoneWindowManagerExt.injectionKeyEvent(4, 0, -1);
            str2 = "Back";
        } else {
            str2 = null;
            z = false;
        }
        if (InputRune.PWM_KEY_SA_LOGGING) {
            String eventId = getEventId(i, keyEvent.getKeyCode());
            phoneWindowManagerExt.getClass();
            PhoneWindowManagerExt.sendCoreSaLoggingDimension(eventId, str2);
        }
        return z;
    }

    public final void prepareVoiceToTextMessage(KeyEvent keyEvent, int i) {
        Intent intent;
        if (isXCoverOrTopKey(i)) {
            SemWindowManager.KeyCustomizationInfo last =
                    this.mKeyCustomizationInfoManager.getLast(4, i);
            ComponentName component =
                    (last == null || (intent = last.intent) == null) ? null : intent.getComponent();
            if (component == null
                    || !"quickMessageSender/quickMessageSender"
                            .equals(component.flattenToString())) {
                return;
            }
            if (keyEvent.getAction() == 0) {
                this.mIsCalledOpenDictationXCoverTop = false;
            } else if (this.mIsCalledOpenDictationXCoverTop) {
                this.mIsCalledOpenDictationXCoverTop = false;
                if (InputRune.PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD) {
                    this.mPolicyExt.callDictation(i, "close_dictation");
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void putKeyCustomizationInfo(
            com.samsung.android.view.SemWindowManager.KeyCustomizationInfo r19) {
        /*
            Method dump skipped, instructions count: 716
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.KeyCustomizationManager.putKeyCustomizationInfo(com.samsung.android.view.SemWindowManager$KeyCustomizationInfo):void");
    }

    public final void removeKeyCustomizationInfo(int i, int i2, int i3, String str) {
        long j;
        SingleKeyGestureDetector.SingleKeyRule singleKeyRule;
        String str2 = KeyCustomizationConstants.VOLD_DECRYPT;
        KeyCustomizationInfoManager keyCustomizationInfoManager = this.mKeyCustomizationInfoManager;
        synchronized (keyCustomizationInfoManager.mLock) {
            SparseArray infoMapLocked = keyCustomizationInfoManager.getInfoMapLocked(i2);
            if (infoMapLocked.get(i3) != null
                    && ((SparseArray) infoMapLocked.get(i3)).get(i) != null) {
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                        this.mKeyCustomizationInfoManager.get(i, i2, i3, null);
                long j2 =
                        keyCustomizationInfo == null ? 0L : keyCustomizationInfo.multiPressTimeout;
                if (this.mKeyCustomizationInfoManager.remove(i, i2, str, i3, false)) {
                    if (i3 == 26) {
                        updatePowerBehavior(i2);
                    } else {
                        this.mPolicyExt.updateSingleKeyGestureRule(i3);
                    }
                    if ((i2 & 4) != 0
                            && (singleKeyRule =
                                            (SingleKeyGestureDetector.SingleKeyRule)
                                                    this.mPolicyExt.mPolicy
                                                            .mSingleKeyGestureDetector.mCustomRules
                                                            .get(i3))
                                    != null) {
                        singleKeyRule.getLongPressTimeoutMs();
                    }
                    SingleKeyGestureDetector.SingleKeyRule singleKeyRule2 =
                            (SingleKeyGestureDetector.SingleKeyRule)
                                    this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.mCustomRules
                                            .get(i3);
                    if (singleKeyRule2 == null) {
                        j = 0;
                    } else {
                        j = singleKeyRule2.extensionMultiPressTimeout;
                        if (j == 0) {
                            j = SingleKeyGestureDetector.sDefaultMultiPressTimeout;
                        }
                    }
                    if (j2 <= 0 || j != j2) {
                        return;
                    }
                    long j3 = 0;
                    for (int i4 :
                            KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
                        if ((i4 & 4) == 0) {
                            SemWindowManager.KeyCustomizationInfo last =
                                    this.mKeyCustomizationInfoManager.getLast(i4, i3);
                            long j4 = last == null ? 0L : last.multiPressTimeout;
                            if (j3 < j4) {
                                j3 = j4;
                            }
                        }
                    }
                    updateMultiPressTimeoutIfNeeded(i2, i3, true, j3);
                    return;
                }
                return;
            }
            Slog.d(
                    "KeyCustomizationManager",
                    "Requested info has been removed. "
                            + idToString(i)
                            + " keyCode="
                            + i3
                            + " "
                            + pressToString(i2));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0199, code lost:

       r1 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0197, code lost:

       if (r18 == false) goto L129;
    */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0221  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int sendBroadcast(
            com.samsung.android.view.SemWindowManager.KeyCustomizationInfo r24,
            android.view.KeyEvent r25,
            int r26,
            boolean r27) {
        /*
            Method dump skipped, instructions count: 774
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.KeyCustomizationManager.sendBroadcast(com.samsung.android.view.SemWindowManager$KeyCustomizationInfo,"
                    + " android.view.KeyEvent, int, boolean):int");
    }

    public final void startActivity(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        Intent intent = keyCustomizationInfo.intent;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        phoneWindowManagerExt.getClass();
        Intent fillInIntent = PhoneWindowManagerExt.getFillInIntent();
        if (intent == null) {
            Log.e("KeyCustomizationManager", "Can not startActivity. intent is null.");
            return;
        }
        int i = keyCustomizationInfo.userId;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "startActivity. userId=", " Callers=");
        m.append(Debug.getCallers(3));
        Log.i("KeyCustomizationManager", m.toString());
        if (!phoneWindowManagerExt.mPolicy.isKeyguardShowing()) {
            this.mContext.startActivityAsUser(intent, new UserHandle(i));
        } else {
            phoneWindowManagerExt.setPendingIntentAfterUnlock(
                    PendingIntent.getActivityAsUser(
                            phoneWindowManagerExt.mContext,
                            0,
                            intent,
                            201326592,
                            null,
                            new UserHandle(i)),
                    fillInIntent);
        }
    }

    public final boolean startMCPTTServiceIfNeeded(KeyEvent keyEvent, int i) {
        SemWindowManager.KeyCustomizationInfo last;
        Intent intent;
        if (!isXCoverOrTopKey(i)
                || (last = this.mKeyCustomizationInfoManager.getLast(3, i)) == null
                || (intent = last.intent) == null
                || intent.getComponent() == null
                || !"com.att.firstnet.grey".equals(intent.getComponent().getPackageName())) {
            return false;
        }
        startService(last, 3, i, keyEvent);
        return true;
    }

    public final void startService(
            SemWindowManager.KeyCustomizationInfo keyCustomizationInfo,
            int i,
            int i2,
            KeyEvent keyEvent) {
        Intent intent = keyCustomizationInfo.intent;
        int i3 = keyCustomizationInfo.id;
        if (intent == null) {
            Log.e("KeyCustomizationManager", "Can not startService. intent is null.");
            return;
        }
        String str = null;
        String packageName =
                intent.getComponent() != null ? intent.getComponent().getPackageName() : null;
        if (InputRune.PWM_B2B_DEDICATED_APP
                && i3 == 951
                && "com.att.firstnet.grey".equals(packageName)
                && keyEvent != null) {
            intent.putExtra("android.intent.extra.KEY_EVENT", KeyEvent.obtain(keyEvent));
        } else {
            intent.putExtra("extraKeyCode", i2);
            if ((i & 3) != 0) {
                str = "press";
            } else if ((i & 4) != 0) {
                str = "long";
            } else if ((i & 8) != 0) {
                str = "double";
            } else if ((i & 16) != 0) {
                str = "triple";
            } else if ((i & 32) != 0) {
                str = "quadruple";
            } else if ((i & 64) != 0) {
                str = "quintuple";
            }
            intent.putExtra("extraKeyAction", str);
        }
        if (intent.getAction() == null) {
            intent.setAction("android.intent.action.MAIN");
            Log.i("KeyCustomizationManager", "startService add action main");
        }
        int i4 = keyCustomizationInfo.userId;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i4, "startService. userId=", " ownerPackage=");
        m.append(keyCustomizationInfo.ownerPackage);
        m.append(" Callers=");
        m.append(Debug.getCallers(3));
        Log.i("KeyCustomizationManager", m.toString());
        if (i3 == 2003) {
            this.mContext.startForegroundServiceAsUser(intent, new UserHandle(i4));
        } else {
            this.mContext.startServiceAsUser(intent, new UserHandle(i4));
        }
    }

    public final void updateLongPressTimeoutIfNeeded(int i, int i2, boolean z) {
        SingleKeyGestureDetector.SingleKeyRule singleKeyRule;
        if ((i & 4) == 0) {
            return;
        }
        SemWindowManager.KeyCustomizationInfo last =
                this.mKeyCustomizationInfoManager.getLast(i, i2);
        long j = last == null ? 0L : last.longPressTimeout;
        if ((z && j == 0)
                || (singleKeyRule =
                                (SingleKeyGestureDetector.SingleKeyRule)
                                        this.mPolicyExt.mPolicy.mSingleKeyGestureDetector
                                                .mCustomRules.get(i2))
                        == null) {
            return;
        }
        singleKeyRule.getLongPressTimeoutMs();
    }

    public final void updateMultiPressTimeoutIfNeeded(int i, int i2, boolean z, long j) {
        long j2;
        SingleKeyGestureDetector.SingleKeyRule singleKeyRule;
        if ((i & 3) == 0 && (i & 4) == 0) {
            if (j != 0 || z) {
                PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                SingleKeyGestureDetector.SingleKeyRule singleKeyRule2 =
                        (SingleKeyGestureDetector.SingleKeyRule)
                                phoneWindowManagerExt.mPolicy.mSingleKeyGestureDetector.mCustomRules
                                        .get(i2);
                if (singleKeyRule2 == null) {
                    j2 = 0;
                } else {
                    j2 = singleKeyRule2.extensionMultiPressTimeout;
                    if (j2 == 0) {
                        j2 = SingleKeyGestureDetector.sDefaultMultiPressTimeout;
                    }
                }
                if ((z || j2 == 0 || j >= j2)
                        && (singleKeyRule =
                                        (SingleKeyGestureDetector.SingleKeyRule)
                                                phoneWindowManagerExt.mPolicy
                                                        .mSingleKeyGestureDetector.mCustomRules.get(
                                                        i2))
                                != null) {
                    long j3 = singleKeyRule.extensionMultiPressTimeout;
                    if (j3 == 0) {
                        j3 = SingleKeyGestureDetector.sDefaultMultiPressTimeout;
                    }
                    if (j == j3) {
                        return;
                    }
                    if (j == SingleKeyGestureDetector.sDefaultMultiPressTimeout) {
                        j = 0;
                    }
                    singleKeyRule.extensionMultiPressTimeout = j;
                }
            }
        }
    }

    public final void updatePowerBehavior(int i) {
        int i2 = i & 4;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        if (i2 == 0) {
            if ((i & 8) != 0) {
                phoneWindowManagerExt.updateDoublePressPowerBehavior();
                return;
            }
            if ((i & 16) == 0) {
                if ((i & 32) != 0) {
                    int lastAction =
                            phoneWindowManagerExt.mKeyCustomizationPolicy.getLastAction(32, 26);
                    if (lastAction == 1 || lastAction == 3 || lastAction == 2) {
                        phoneWindowManagerExt.mQuadruplePressOnPowerBehavior = 106;
                        return;
                    }
                    return;
                }
                return;
            }
            phoneWindowManagerExt.getClass();
            boolean z = InputRune.PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL;
            PhoneWindowManager phoneWindowManager = phoneWindowManagerExt.mPolicy;
            if (z) {
                phoneWindowManager.mTriplePressOnPowerBehavior = 102;
                return;
            }
            int lastAction2 = phoneWindowManagerExt.mKeyCustomizationPolicy.getLastAction(16, 26);
            if (lastAction2 == 1 || lastAction2 == 3 || lastAction2 == 2) {
                phoneWindowManager.mTriplePressOnPowerBehavior = 106;
                return;
            }
            return;
        }
        KeyCustomizationManager keyCustomizationManager =
                phoneWindowManagerExt.mKeyCustomizationPolicy;
        SemWindowManager.KeyCustomizationInfo last =
                keyCustomizationManager.mKeyCustomizationInfoManager.getLast(4, 26);
        if (last == null) {
            if (keyCustomizationManager.getLastId(3, 26) == 951) {
                Slog.d(
                        "PhoneWindowManagerExt",
                        "updated long press power behavior by b2b dedicated app");
                Settings.Global.putInt(
                        phoneWindowManagerExt.mContext.getContentResolver(),
                        "power_button_long_press",
                        0);
                return;
            } else {
                Slog.d("PhoneWindowManagerExt", "Side key long press info was wrong.");
                Settings.Global.putInt(
                        phoneWindowManagerExt.mContext.getContentResolver(),
                        "power_button_long_press",
                        1);
                return;
            }
        }
        Intent intent = last.intent;
        if (intent == null) {
            Slog.d("PhoneWindowManagerExt", "Side key long press intent info was wrong.");
            Settings.Global.putInt(
                    phoneWindowManagerExt.mContext.getContentResolver(),
                    "power_button_long_press",
                    102);
            return;
        }
        ComponentName component = intent.getComponent();
        String flattenToString = component != null ? component.flattenToString() : null;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "updateLongPressPowerBehavior componentName:",
                flattenToString,
                "PhoneWindowManagerExt");
        if (InputRune.PWM_SIDE_KEY_DIGITAL_ASSISTANT
                && "aiAgentApp/aiAgentApp".equals(flattenToString)) {
            Slog.d(
                    "PhoneWindowManagerExt",
                    "updated long press power behavior as digital assistant");
            Settings.Global.putInt(
                    phoneWindowManagerExt.mContext.getContentResolver(),
                    "power_button_long_press",
                    103);
        } else if ("wakeBixby/wakeBixby".equals(flattenToString)) {
            Slog.d("PhoneWindowManagerExt", "updated long press power behavior as wake bixby");
            Settings.Global.putInt(
                    phoneWindowManagerExt.mContext.getContentResolver(),
                    "power_button_long_press",
                    101);
        } else if ("globalAction/globalAction".equals(flattenToString)) {
            Slog.d("PhoneWindowManagerExt", "updated long press power behavior as global action");
            Settings.Global.putInt(
                    phoneWindowManagerExt.mContext.getContentResolver(),
                    "power_button_long_press",
                    1);
        } else {
            Slog.d(
                    "PhoneWindowManagerExt",
                    "updated long press power behavior as keyCustomizationInfo");
            Settings.Global.putInt(
                    phoneWindowManagerExt.mContext.getContentResolver(),
                    "power_button_long_press",
                    102);
        }
    }
}
