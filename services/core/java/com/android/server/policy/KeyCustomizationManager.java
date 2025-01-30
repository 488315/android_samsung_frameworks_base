package com.android.server.policy;

import android.R;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.INetd;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes3.dex */
public class KeyCustomizationManager {
    public long defaultLongPressTimeout;
    public final Context mContext;
    public final KeyCustomizationInfoManager mKeyCustomizationInfoManager;
    public final PhoneWindowManagerExt mPolicyExt;
    public Handler mHandler = new Handler();
    public boolean mIsKeyLongPressed = false;
    public boolean mIsKeyLongConsumed = false;
    public boolean mIsXCoverKeyOnLockScreen = false;
    public boolean mIsTopKeyOnLockScreen = false;
    public KeyguardManager mKeyguardManager = null;
    public boolean mIsCalledOpenDictationXCoverTop = false;

    public final String getEventId(int i, int i2) {
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

    public final String getKeyAction(int i) {
        if ((i & 3) != 0) {
            return "press";
        }
        if ((i & 4) != 0) {
            return "long";
        }
        if ((i & 8) != 0) {
            return "double";
        }
        if ((i & 16) != 0) {
            return "triple";
        }
        if ((i & 32) != 0) {
            return "quadruple";
        }
        if ((i & 64) != 0) {
            return "quintuple";
        }
        return null;
    }

    public final boolean isAllowHandleDispatching(int i) {
        return i == 4 || i == 79 || i == 1015 || i == 1079 || i == 24 || i == 25;
    }

    public boolean isHigherIdThanDefault(int i) {
        return i != -1 && i < 1000;
    }

    public final boolean isKnoxId(int i) {
        return i == 10 || i == 30 || i == 50;
    }

    public final boolean isXCoverOrTopKey(int i) {
        return i == 1015 || i == 1079;
    }

    public final boolean shouldLaunchLongOrShortPressAction(int i, int i2) {
        if (i == -1 || i == 4) {
            i = i2;
        }
        return i == 0 || i == 1 || i == 2 || i == 3;
    }

    public KeyCustomizationManager(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
        this.mContext = context;
        this.mPolicyExt = phoneWindowManagerExt;
        this.mKeyCustomizationInfoManager = new KeyCustomizationInfoManager(context);
    }

    public void init() {
        this.mKeyCustomizationInfoManager.init(0, false);
        for (int i : KeyCustomizationConstants.ALL_KEYCODE_TYPE) {
            initPowerBehaviorAndSingleKeyGestureDetectorRule(i);
        }
        this.defaultLongPressTimeout = this.mContext.getResources().getInteger(R.integer.config_letterboxDefaultPositionForVerticalReachability);
    }

    public final void initPowerBehaviorAndSingleKeyGestureDetectorRule(int i) {
        if (i != 26) {
            this.mPolicyExt.updateSingleKeyGestureRule(i);
        }
        long j = 0;
        for (int i2 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
            if (i == 26) {
                updatePowerBehavior(i2);
            }
            if ((i2 & 4) != 0) {
                updateLongPressTimeoutIfNeeded(i2, i, true);
            } else {
                long lastMultiPressTimeout = getLastMultiPressTimeout(i2, i);
                if (j < lastMultiPressTimeout) {
                    j = lastMultiPressTimeout;
                }
            }
        }
        if (j != 0) {
            this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.setMultiPressTimeout(i, j);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0102  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkValidInputKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        int i;
        int i2;
        if (keyCustomizationInfo == null) {
            throw new IllegalArgumentException("KeyCustomizationInfo is null.");
        }
        int i3 = keyCustomizationInfo.press;
        int i4 = keyCustomizationInfo.keyCode;
        int i5 = keyCustomizationInfo.action;
        int i6 = keyCustomizationInfo.id;
        if (i4 != 3) {
            if (i4 != 4) {
                if (i4 != 79) {
                    if (i4 != 187) {
                        if (i4 != 1015 && i4 != 1079) {
                            switch (i4) {
                                case 24:
                                case 25:
                                case 26:
                                    break;
                                default:
                                    switch (i4) {
                                        case 1093:
                                        case 1094:
                                        case 1095:
                                        case 1096:
                                            if ((i3 & 3) == 0 || (i3 & 4) != 0 || (i3 & 8) != 0) {
                                                throw new IllegalArgumentException(pressToString(i3) + " is not supported in the requested keyCode(" + i4 + ").");
                                            }
                                            break;
                                        default:
                                            if (KeyEvent.keyCodeFromString(Integer.toString(i4)) == 0 || (i3 & 3) == 0 || i5 != 4 || i6 != 10) {
                                                throw new IllegalArgumentException("requested keyCode(" + i4 + ") was wrong. The keyCode is not supported.");
                                            }
                                            break;
                                    }
                            }
                        }
                    }
                }
                if (i5 != 0) {
                    if (i6 < 1100 || i6 > 1107) {
                        throw new IllegalArgumentException("requested " + actionToString(i5) + " and " + idToString(i6) + " was wrong.");
                    }
                } else if (i5 != 1 && i5 != 2 && i5 != 3 && i5 != 4) {
                    throw new IllegalArgumentException("requested " + actionToString(i5) + " was wrong. The action is not supported.");
                }
                i = keyCustomizationInfo.dispatching;
                if (i == 0 && i != -1) {
                    throw new IllegalArgumentException("requested dispatching(" + i + ") was wrong.");
                }
                if (i6 == 10 && i6 != 30 && i6 != 50 && i6 != 951 && ((i6 < 1100 || i6 > 1107) && (i6 < 2000 || i6 > 2003))) {
                    throw new IllegalArgumentException("requested " + idToString(i6) + " was wrong. The id is not supported.");
                }
                String str = keyCustomizationInfo.ownerPackage;
                if (i6 != 2003 && TextUtils.isEmpty(str)) {
                    throw new IllegalArgumentException("requested ownerPackage was empty.");
                }
                i2 = i3 & 3;
                if (i2 != 0 && (i3 & 4) == 0 && (i3 & 8) == 0 && (i3 & 16) == 0 && (i3 & 32) == 0 && (i3 & 64) == 0) {
                    throw new IllegalArgumentException("requested " + pressToString(i3) + " was wrong. The press is not supported.");
                }
                if (!CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL && i4 == 26 && (i3 & 16) != 0) {
                    throw new IllegalArgumentException("Triple press of Power key is not supported.");
                }
                if ((!CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS || CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL) && i4 == 26 && (i3 & 64) != 0) {
                    throw new IllegalArgumentException("Quintuple press of Power key is not supported.");
                }
                int i7 = i3 & 4;
                if ((i7 != 0 || (i3 & 8) != 0) && i6 == 1107) {
                    throw new IllegalArgumentException("The long or double press is not supported with mouse button id.");
                }
                long j = keyCustomizationInfo.longPressTimeout;
                if (j != 0 && j < this.defaultLongPressTimeout) {
                    throw new IllegalArgumentException("The longPressTimeout was wrong.");
                }
                if (j > 0 && i7 == 0) {
                    throw new IllegalArgumentException("The longPressTimeout only supported with long press");
                }
                if (keyCustomizationInfo.multiPressTimeout != 0) {
                    if (i2 == 0 && i7 == 0) {
                        return;
                    }
                    throw new IllegalArgumentException("The multiPressTimeout is not supported with " + pressToString(i3));
                }
                return;
            }
        } else if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && (i3 & 4) != 0 && !isKnoxId(i6)) {
            throw new IllegalArgumentException(pressToString(i3) + " and " + idToString(i6) + " are not supported in the requested keyCode(" + i4 + ").");
        }
        if ((i3 & 3) != 0 && (i5 == 1 || i5 == 3)) {
            throw new IllegalArgumentException(pressToString(i3) + " and " + actionToString(i5) + " are not supported in the requested keyCode(" + i4 + ").");
        }
        if (i5 != 0) {
        }
        i = keyCustomizationInfo.dispatching;
        if (i == 0) {
        }
        if (i6 == 10) {
        }
        String str2 = keyCustomizationInfo.ownerPackage;
        if (i6 != 2003) {
        }
        i2 = i3 & 3;
        if (i2 != 0) {
        }
        if (!CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL) {
        }
        if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
        }
        throw new IllegalArgumentException("Quintuple press of Power key is not supported.");
    }

    public void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        int i = keyCustomizationInfo.press;
        int i2 = keyCustomizationInfo.keyCode;
        int i3 = keyCustomizationInfo.action;
        if (i >= 8 && i3 == 4 && !this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.hasRule(i2)) {
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Slog.d("KeyCustomizationManager", "No need to block double press behavior. Because there is no added Rule.");
                return;
            }
            return;
        }
        checkValidInputKeyCustomizationInfo(keyCustomizationInfo);
        String str = keyCustomizationInfo.ownerPackage;
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("KeyCustomizationManager", "putKeyCustomizationInfo keyCode=" + i2 + " id=" + idToString(keyCustomizationInfo.id) + " ownerPackage=" + str + " Caller=" + Debug.getCallers(5));
        }
        this.mKeyCustomizationInfoManager.put(keyCustomizationInfo);
        if (i2 == 26) {
            updatePowerBehavior(i);
        } else if (i3 == 4 && i >= 8) {
            this.mPolicyExt.updateSingleKeyGestureRule(i2);
        } else {
            if ((i & 3) != 0) {
                return;
            }
            if ((i & 4) != 0 && keyCustomizationInfo.longPressTimeout == 0) {
                return;
            } else {
                this.mPolicyExt.addSingleKeyGestureRule(i2);
            }
        }
        updateLongPressTimeoutIfNeeded(i, i2);
        updateMultiPressTimeoutIfNeeded(i, i2, keyCustomizationInfo.multiPressTimeout, false);
    }

    public void removeKeyCustomizationInfoByPackage(String str, int i, int i2) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("KeyCustomizationManager", "removeKeyCustomizationInfoByPackage ownerPackage=" + str + " keyCode=" + i2 + " " + pressToString(i));
        }
        removeKeyCustomizationInfo(2003, str, i, i2);
    }

    public void removeKeyCustomizationInfo(int i, int i2, int i3) {
        removeKeyCustomizationInfo(i, null, i2, i3);
    }

    public void removeKeyCustomizationInfo(int i, String str, int i2, int i3) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("KeyCustomizationManager", "removeKeyCustomizationInfo " + idToString(i) + " keyCode=" + i3 + " " + pressToString(i2) + " Callers=" + Debug.getCallers(5));
        }
        if (this.mKeyCustomizationInfoManager.isEmptyKeyCustomizationInfo(i, i2, i3)) {
            Slog.d("KeyCustomizationManager", "Requested info has been removed. " + idToString(i) + " keyCode=" + i3 + " " + pressToString(i2));
            return;
        }
        long multiPressTimeout = getMultiPressTimeout(i, i2, i3);
        if (this.mKeyCustomizationInfoManager.remove(i, str, i2, i3)) {
            if (i3 == 26) {
                updatePowerBehavior(i2);
            } else {
                this.mPolicyExt.updateSingleKeyGestureRule(i3);
            }
            if ((i2 & 4) != 0) {
                this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.initLongPressTimeout(i3);
            }
            long multiPressTimeout2 = this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.getMultiPressTimeout(i3);
            if (multiPressTimeout <= 0 || multiPressTimeout2 != multiPressTimeout) {
                return;
            }
            updateMultiPressTimeoutIfNeeded(i2, i3, getMaximumMultiPressTimeout(i3), true);
        }
    }

    public boolean hasOwnerPackage(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.mKeyCustomizationInfoManager.hasOwnerPackage(str);
    }

    public void clearKeyCustomizationInfoByPackage(String str) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByPackage packageName=" + str);
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!this.mKeyCustomizationInfoManager.clearByPackage(str)) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByPackage, Requested info is empty. packageName=" + str);
            return;
        }
        for (int i : KeyCustomizationConstants.ALL_KEYCODE_TYPE) {
            if (i == 26) {
                for (int i2 : KeyCustomizationConstants.EXTERNAL_SUPPORTED_BEHAVIOR_PRESS_TYPE) {
                    updatePowerBehavior(i2);
                }
            } else {
                this.mPolicyExt.updateSingleKeyGestureRule(i);
            }
        }
    }

    public void clearKeyCustomizationInfoByKeyCode(int i, int i2) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByKeyCode, " + idToString(i) + " keyCode=" + i2);
        }
        if (!this.mKeyCustomizationInfoManager.clearByKeyCode(i, i2)) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByKeyCode, Requested info is empty. " + idToString(i) + " keyCode=" + i2);
            return;
        }
        if (i2 == 26) {
            for (int i3 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
                if ((!CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL || (i3 & 16) == 0) && (i3 & 64) == 0) {
                    updatePowerBehavior(i3);
                }
            }
        } else {
            this.mPolicyExt.updateSingleKeyGestureRule(i2);
        }
        this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.initLongPressTimeout(i2);
        this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.initMultiPressTimeout(i2);
    }

    public void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByAction, " + idToString(i) + " keyCode=" + i2 + " " + actionToString(i3));
        }
        if (!this.mKeyCustomizationInfoManager.clearByAction(i, i2, i3)) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByAction, Requested info is empty. " + idToString(i) + " keyCode=" + i2 + " " + actionToString(i3));
            return;
        }
        initPowerBehaviorAndSingleKeyGestureDetectorRule(i2);
    }

    public final void updatePowerBehavior(int i) {
        if ((i & 4) != 0) {
            this.mPolicyExt.updateLongPressPowerBehavior();
            return;
        }
        if ((i & 8) != 0) {
            this.mPolicyExt.updateDoublePressPowerBehavior();
        } else if ((i & 16) != 0) {
            this.mPolicyExt.updateTriplePressPowerBehavior();
        } else if ((i & 32) != 0) {
            this.mPolicyExt.updateQuadruplePressPowerBehavior();
        }
    }

    public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) {
        return this.mKeyCustomizationInfoManager.get(i, i2, i3);
    }

    public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) {
        return this.mKeyCustomizationInfoManager.get(2003, str, i, i2);
    }

    public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) {
        return this.mKeyCustomizationInfoManager.getLast(i, i2);
    }

    public long getMultiPressTimeout(int i, int i2, int i3) {
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = this.mKeyCustomizationInfoManager.get(i, i2, i3);
        if (keyCustomizationInfo == null) {
            return 0L;
        }
        return keyCustomizationInfo.multiPressTimeout;
    }

    public int getLastAction(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null) {
            return -1;
        }
        return lastKeyCustomizationInfo.action;
    }

    public int getLastId(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null) {
            return -1;
        }
        return lastKeyCustomizationInfo.id;
    }

    public ComponentName getLastComponentName(int i, int i2) {
        Intent intent;
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null || (intent = lastKeyCustomizationInfo.intent) == null) {
            return null;
        }
        return intent.getComponent();
    }

    public Intent getLastIntentClone(int i, int i2) {
        Intent intent;
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null || (intent = lastKeyCustomizationInfo.intent) == null) {
            return null;
        }
        return (Intent) intent.clone();
    }

    public long getLastLongPressTimeout(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null) {
            return 0L;
        }
        return lastKeyCustomizationInfo.longPressTimeout;
    }

    public long getLastMultiPressTimeout(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null) {
            return 0L;
        }
        return lastKeyCustomizationInfo.multiPressTimeout;
    }

    public boolean hasLastInfo(int i, int i2) {
        return getLastKeyCustomizationInfo(i, i2) != null;
    }

    public final void updateLongPressTimeoutIfNeeded(int i, int i2) {
        updateLongPressTimeoutIfNeeded(i, i2, false);
    }

    public final void updateLongPressTimeoutIfNeeded(int i, int i2, boolean z) {
        if ((i & 4) == 0) {
            return;
        }
        long lastLongPressTimeout = getLastLongPressTimeout(i, i2);
        if (z && lastLongPressTimeout == 0) {
            return;
        }
        this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.setLongPressTimeout(i2, lastLongPressTimeout);
    }

    public final void updateMultiPressTimeoutIfNeeded(int i, int i2, long j, boolean z) {
        if ((i & 3) == 0 && (i & 4) == 0) {
            if (j != 0 || z) {
                long multiPressTimeout = this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.getMultiPressTimeout(i2);
                if (z || multiPressTimeout == 0 || j >= multiPressTimeout) {
                    this.mPolicyExt.mPolicy.mSingleKeyGestureDetector.setMultiPressTimeout(i2, j);
                }
            }
        }
    }

    public final long getMaximumMultiPressTimeout(int i) {
        long j = 0;
        for (int i2 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
            if ((i2 & 4) == 0) {
                long lastMultiPressTimeout = getLastMultiPressTimeout(i2, i);
                if (j < lastMultiPressTimeout) {
                    j = lastMultiPressTimeout;
                }
            }
        }
        return j;
    }

    public int sendBroadcast(KeyEvent keyEvent, int i) {
        return sendBroadcast(null, keyEvent, i, false);
    }

    public int sendBroadcast(KeyEvent keyEvent, int i, boolean z) {
        return sendBroadcast(null, keyEvent, i, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int sendBroadcast(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo, KeyEvent keyEvent, int i, boolean z) {
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2;
        boolean z2;
        boolean z3;
        boolean migrationForKnoxPolicy;
        Intent intent;
        if (i == 0) {
            return 0;
        }
        int keyCode = keyEvent.getKeyCode();
        if (hasHigherIdWithAllPress(i, keyCode)) {
            return 0;
        }
        if (keyCustomizationInfo == null) {
            SemWindowManager.KeyCustomizationInfo last = this.mKeyCustomizationInfoManager.getLast(i, keyCode);
            if (last == null) {
                return 0;
            }
            keyCustomizationInfo2 = last;
        } else {
            keyCustomizationInfo2 = keyCustomizationInfo;
        }
        int i2 = keyCustomizationInfo2.id;
        int i3 = keyCustomizationInfo2.userId;
        boolean z4 = keyEvent.getAction() == 0;
        Intent intent2 = keyCustomizationInfo2.intent;
        if ((i & 4) != 0) {
            z2 = true;
            z3 = false;
        } else if ((i & 8) != 0) {
            z3 = true;
            z2 = false;
        } else {
            z2 = false;
            z3 = false;
        }
        if (intent2 == null) {
            intent2 = getDefaultIntentForKnoxId(i2, keyEvent, z2, z3, z);
        } else if (CoreRune.FW_B2B_DEDICATED_APP && i2 == 951) {
            if (!this.mPolicyExt.mPolicy.isUserSetupComplete()) {
                Log.d("KeyCustomizationManager", "Can not sendBroadcast. The user setup is not complete");
                return 0;
            }
            updateKeyInfoExtra(intent2, keyEvent);
        } else if (isKnoxId(i2)) {
            migrationForKnoxPolicy = migrationForKnoxPolicy(intent2, keyEvent, z2, z3);
            if (!z4 && z) {
                intent2.putExtra("com.samsung.android.knox.intent.extra.EXTRA_REPORT_TYPE_NEW_LONG_UP", true);
            }
            intent = intent2;
            if (intent != null) {
                Log.e("KeyCustomizationManager", "Can not sendBroadcast. intent is null. keyCode=" + keyCode + " " + pressToString(i));
                return 0;
            }
            if (!migrationForKnoxPolicy) {
                sendBroadcastAsUser(intent, i3);
            }
            if (isBlockedDispatchingByKnoxPolicy(i2, z4, keyEvent.getRepeatCount(), intent, keyCode) || isForceBlockDispatchingLongByKnoxPolicy(i2, i, keyCode)) {
                return -1;
            }
            int i4 = keyCustomizationInfo2.dispatching;
            if (i4 == -1 && isAllowHandleDispatching(keyCode)) {
                return i4;
            }
            return 0;
        }
        intent = intent2;
        migrationForKnoxPolicy = false;
        if (intent != null) {
        }
    }

    public final void sendBroadcastAsUser(Intent intent, int i) {
        Log.i("KeyCustomizationManager", "sendBroadcastAsUser intent=" + intent + " userId=" + i);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
    }

    public void startActivity(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null) {
            return;
        }
        startActivity(lastKeyCustomizationInfo);
    }

    public void startActivity(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        Intent intent = keyCustomizationInfo.intent;
        Intent fillInIntent = this.mPolicyExt.getFillInIntent();
        if (intent == null) {
            Log.e("KeyCustomizationManager", "Can not startActivity. intent is null.");
            return;
        }
        int i = keyCustomizationInfo.userId;
        Log.i("KeyCustomizationManager", "startActivity. userId=" + i + " Callers=" + Debug.getCallers(3));
        if (this.mPolicyExt.mPolicy.isKeyguardShowing()) {
            this.mPolicyExt.setPendingIntentAfterUnlock(this.mPolicyExt.getPendingIntentActivityAsUser(intent, new UserHandle(i)), fillInIntent);
        } else if (this.mPolicyExt.showCoverToast(fillInIntent, intent)) {
            this.mPolicyExt.setPendingIntentAfterUnlock(this.mPolicyExt.getPendingIntentActivityAsUser(intent, new UserHandle(i)), fillInIntent);
        } else {
            this.mContext.startActivityAsUser(intent, new UserHandle(i));
        }
    }

    public void startService(int i, int i2, KeyEvent keyEvent) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i, i2);
        if (lastKeyCustomizationInfo == null) {
            return;
        }
        startService(lastKeyCustomizationInfo, i, i2, keyEvent);
    }

    public void startService(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo, int i, int i2, KeyEvent keyEvent) {
        Intent intent = keyCustomizationInfo.intent;
        int i3 = keyCustomizationInfo.id;
        if (intent == null) {
            Log.e("KeyCustomizationManager", "Can not startService. intent is null.");
            return;
        }
        String packageName = intent.getComponent() != null ? intent.getComponent().getPackageName() : null;
        if (CoreRune.FW_B2B_DEDICATED_APP && i3 == 951 && "com.att.firstnet.grey".equals(packageName) && keyEvent != null) {
            intent.putExtra("android.intent.extra.KEY_EVENT", KeyEvent.obtain(keyEvent));
        } else {
            intent.putExtra("extraKeyCode", i2);
            intent.putExtra("extraKeyAction", getKeyAction(i));
        }
        if (intent.getAction() == null) {
            intent.setAction("android.intent.action.MAIN");
            Log.i("KeyCustomizationManager", "startService add action main");
        }
        int i4 = keyCustomizationInfo.userId;
        Log.i("KeyCustomizationManager", "startService. userId=" + i4 + " ownerPackage=" + keyCustomizationInfo.ownerPackage + " Callers=" + Debug.getCallers(3));
        if (i3 == 2003) {
            this.mContext.startForegroundServiceAsUser(intent, new UserHandle(i4));
        } else {
            this.mContext.startServiceAsUser(intent, new UserHandle(i4));
        }
    }

    public void launchPressSendBroadcast(KeyEvent keyEvent, int i, boolean z) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(3, i);
        if (lastKeyCustomizationInfo == null) {
            return;
        }
        Log.d("KeyCustomizationManager", "launchPressSendBroadcast, keyCode=" + i + " isKeyLongPressed=" + z);
        sendBroadcast(lastKeyCustomizationInfo, keyEvent, 3, z);
    }

    public boolean launchPressAction(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        int lastAction = getLastAction(3, keyCode);
        if (lastAction == -1) {
            return false;
        }
        return launchPressAction(lastAction, keyEvent, keyCode, false);
    }

    public final boolean launchPressAction(int i, KeyEvent keyEvent, int i2, boolean z) {
        if (hasHigherIdWithAllPress(3, i2)) {
            return false;
        }
        if (i == 1) {
            startActivity(3, i2);
        } else if (i == 3) {
            startService(3, i2, keyEvent);
        } else {
            if (!z) {
                return false;
            }
            this.mPolicyExt.injectionKeyEvent(i2, 268435456, -1);
        }
        Log.d("KeyCustomizationManager", "launchPressAction, keyCode=" + i2 + " " + actionToString(i) + " forceDispatching=" + z);
        return true;
    }

    public boolean launchLongPressAction(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        int lastAction = getLastAction(4, keyCode);
        if (lastAction == -1) {
            return false;
        }
        return launchLongPressAction(lastAction, keyEvent, keyCode);
    }

    public boolean launchLongPressAction(int i, KeyEvent keyEvent, int i2) {
        if (hasHigherIdWithAllPress(4, i2)) {
            return false;
        }
        Log.d("KeyCustomizationManager", "launchLongPressAction, keyCode=" + i2 + " " + actionToString(i));
        if (i == 1) {
            startActivity(4, i2);
        } else if (i != 2) {
            if (i == 3) {
                startService(4, i2, keyEvent);
            } else if (i == 4) {
                Log.d("KeyCustomizationManager", "launchLongPressAction was blocked by KeyCustomizationPolicy.");
            } else {
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d("KeyCustomizationManager", "launchLongPressAction. " + actionToString(i) + " was wrong.");
                }
                return false;
            }
        } else if (sendBroadcast(keyEvent, 4) == 0) {
            return false;
        }
        return true;
    }

    public void launchMultiPressAction(KeyEvent keyEvent, int i) {
        int keyCode;
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo;
        int i2;
        if (keyEvent == null) {
            Log.e("KeyCustomizationManager", "launchMultiPressAction, event is null, Callers=" + Debug.getCallers(5));
            return;
        }
        int i3 = i == 2 ? 8 : i == 3 ? 16 : i == 4 ? 32 : i == 5 ? 64 : 0;
        if (i3 == 0 || (lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i3, (keyCode = keyEvent.getKeyCode()))) == null || (i2 = lastKeyCustomizationInfo.action) == 4 || hasHigherIdWithAllPress(i3, keyCode)) {
            return;
        }
        Log.d("KeyCustomizationManager", "launchMultiPressAction, count=" + i + " keyCode=" + keyCode);
        if (i2 == 1) {
            startActivity(lastKeyCustomizationInfo);
            return;
        }
        if (i2 == 2) {
            sendBroadcast(keyEvent, i3);
            return;
        }
        if (i2 == 3) {
            startService(lastKeyCustomizationInfo, i3, keyCode, keyEvent);
            return;
        }
        Log.e("KeyCustomizationManager", "launchMultiPressAction, count=" + i + " " + actionToString(i2) + " was wrong.");
    }

    public int dispatchKeyCustomizationKeyEvent(KeyEvent keyEvent) {
        return dispatchKeyCustomizationKeyEvent(keyEvent, false);
    }

    public int dispatchKeyCustomizationKeyEvent(KeyEvent keyEvent, boolean z) {
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        boolean isLongPress = keyEvent.isLongPress();
        boolean z2 = keyEvent.getAction() == 0;
        int lastAction = getLastAction(3, keyCode);
        int lastAction2 = getLastAction(4, keyCode);
        boolean z3 = z ? lastAction2 != -1 : z;
        if (KeyCustomizationConstants.isDebugInput()) {
            Slog.d("KeyCustomizationManager", "dispatchKeyEvent: keyCode=" + keyCode + " down=" + z2 + " repeatCount=" + repeatCount + " isLongPress=" + isLongPress + " pressAction=" + actionToString(lastAction) + " longPressAction=" + actionToString(lastAction2) + " forceDispatching=" + z3);
        }
        if (!shouldLaunchLongOrShortPressAction(lastAction2, lastAction)) {
            if (KeyCustomizationConstants.isDebugInput()) {
                Slog.d("KeyCustomizationManager", "dispatchKeyEvent - default, there is no action, NEEDED_NEXT_STEP");
            }
            return 2;
        }
        if (z2) {
            if (repeatCount == 0) {
                this.mIsKeyLongPressed = false;
                this.mIsKeyLongConsumed = false;
                if (CoreRune.FW_B2B_DEDICATED_APP && startMCPTTServiceIfNeeded(keyCode, keyEvent)) {
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:down - launch MCPTT, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
                if (CoreRune.FW_XCOVER_AND_TOP_KEY) {
                    prepareVoiceToTextMessage(keyCode, keyEvent);
                }
                if (lastAction == 2 && sendBroadcast(keyEvent, 3) == -1) {
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:down - sendBroadcast, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
            }
            if (isLongPress) {
                this.mIsKeyLongPressed = true;
                if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY && hasXCoverTopKeyAndId(4, keyCode)) {
                    if (launchXCoverLongActionIfNeeded(keyCode, false)) {
                        if (KeyCustomizationConstants.isDebugInput()) {
                            Slog.d("KeyCustomizationManager", "dispatchKeyEvent:longPress - XCoverTopKey Action, NO_NEED_NEXT_STEP");
                        }
                        this.mIsKeyLongConsumed = true;
                        return 1;
                    }
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:longPress - XCoverTopKey Action, NEEDED_NEXT_STEP");
                    }
                    return 2;
                }
                if (launchLongPressAction(lastAction2, keyEvent, keyCode)) {
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:longPress - launch Action, NO_NEED_NEXT_STEP");
                    }
                    this.mIsKeyLongConsumed = true;
                    return 1;
                }
                if (KeyCustomizationConstants.isDebugInput()) {
                    Slog.d("KeyCustomizationManager", "dispatchKeyEvent:longPress, NEEDED_ONLY_LONG_PRESS_STEP");
                }
                return 3;
            }
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY && hasXCoverTopKeyAndId(keyCode)) {
                if (KeyCustomizationConstants.isDebugInput()) {
                    Slog.d("KeyCustomizationManager", "dispatchKeyEvent:down - XCoverTopKey Action, NEEDED_NEXT_STEP");
                }
                return 2;
            }
        } else {
            if (CoreRune.FW_XCOVER_AND_TOP_KEY) {
                prepareVoiceToTextMessage(keyCode, keyEvent);
            }
            if (lastAction == 2 && sendBroadcast(keyEvent, 3, this.mIsKeyLongPressed) == -1) {
                if (KeyCustomizationConstants.isDebugInput()) {
                    Slog.d("KeyCustomizationManager", "dispatchKeyEvent:up - sendBroadcast, NO_NEED_NEXT_STEP");
                }
                return 1;
            }
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY) {
                if (!this.mIsKeyLongPressed) {
                    if (hasXCoverTopKeyAndId(3, keyCode)) {
                        if (launchXCoverPressActionIfNeeded(keyCode, false)) {
                            if (KeyCustomizationConstants.isDebugInput()) {
                                Slog.d("KeyCustomizationManager", "dispatchKeyEvent:up - XCoverTopKey Action, NO_NEED_NEXT_STEP");
                            }
                            return 1;
                        }
                        if (KeyCustomizationConstants.isDebugInput()) {
                            Slog.d("KeyCustomizationManager", "dispatchKeyEvent:up - XCoverTopKey Action, NEEDED_NEXT_STEP");
                        }
                        return 2;
                    }
                } else if (!this.mIsKeyLongConsumed && hasXCoverTopKeyAndId(keyCode)) {
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:up - XCoverTopKey Action, No longPress consumed, NEEDED_NEXT_STEP");
                    }
                    return 2;
                }
            }
            if (!this.mIsKeyLongPressed) {
                launchPressAction(lastAction, keyEvent, keyCode, z3);
            } else {
                if (CoreRune.FW_B2B_DEDICATED_APP && startMCPTTServiceIfNeeded(keyCode, keyEvent)) {
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:up - launch MCPTT, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
                if (this.mIsKeyLongConsumed) {
                    if (KeyCustomizationConstants.isDebugInput()) {
                        Slog.d("KeyCustomizationManager", "dispatchKeyEvent:up - longPress, NO_NEED_NEXT_STEP");
                    }
                    return 1;
                }
            }
        }
        if (lastAction == 1 || lastAction == 3 || z3) {
            if (KeyCustomizationConstants.isDebugInput()) {
                StringBuilder sb = new StringBuilder();
                sb.append("dispatchKeyEvent:");
                sb.append(z2 ? INetd.IF_STATE_DOWN : INetd.IF_STATE_UP);
                sb.append(" - forceDispatching or press action, NO_NEED_NEXT_STEP");
                Slog.d("KeyCustomizationManager", sb.toString());
            }
            return 1;
        }
        if (KeyCustomizationConstants.isDebugInput()) {
            Slog.d("KeyCustomizationManager", "dispatchKeyEvent - default, NEEDED_NEXT_STEP");
        }
        return 2;
    }

    public final boolean hasXCoverTopKeyAndId(int i) {
        if (isXCoverOrTopKey(i)) {
            return hasXCoverTopIdWithXCoverTopPress(i);
        }
        return false;
    }

    public final boolean hasXCoverTopKeyAndId(int i, int i2) {
        if (isXCoverOrTopKey(i2)) {
            return hasXCoverTopId(i, i2);
        }
        return false;
    }

    public boolean canDispatchXCoverTopKeyEvent(int i) {
        if (this.mPolicyExt.mPolicy.mPowerManager.isInteractive()) {
            return true;
        }
        if (hasHigherIdThanDefaultWithXCoverTopPress(i)) {
            Log.d("KeyCustomizationManager", "Allow XCover or Top key event dispatching even though screen is turned off by knox policy.");
            return true;
        }
        if (!hasXCoverTopIdWithXCoverTopPress(i)) {
            return true;
        }
        Log.d("KeyCustomizationManager", "After screen is turned off, disallow XCover or Top key event dispatching, because single or long press has a ID_SETTING_UI_XCOVER_TOP.");
        return false;
    }

    public boolean hasHigherIdThanDefaultWithXCoverTopPress(int i) {
        for (int i2 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_XCOVER_TOP) {
            if (isHigherIdThanDefault(getLastId(i2, i))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasXCoverTopIdWithXCoverTopPress(int i) {
        for (int i2 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_XCOVER_TOP) {
            if (hasXCoverTopId(i2, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasXCoverTopId(int i, int i2) {
        return getLastId(i, i2) == 1103;
    }

    public final boolean shouldDispatchEventForCameraShutter(boolean z) {
        return this.mPolicyExt.isCameraRunning() && !z;
    }

    public final boolean isOnLockScreen(int i) {
        return i == 1079 ? this.mIsTopKeyOnLockScreen : this.mIsXCoverKeyOnLockScreen;
    }

    public boolean launchXCoverLongActionIfNeeded(int i, boolean z) {
        if (FactoryTest.isAutomaticTestMode(this.mContext)) {
            Slog.d("KeyCustomizationManager", "Block handling XCoverKey because of Automatic Test Mode");
            return false;
        }
        if (hasHigherIdWithOppositePress(3, i)) {
            Log.d("KeyCustomizationManager", "Can not launch long press action by knox policy");
            return true;
        }
        ComponentName lastComponentName = getLastComponentName(4, i);
        if (lastComponentName == null) {
            return false;
        }
        if (shouldDispatchEventForCameraShutter(z)) {
            Slog.d("KeyCustomizationManager", "Can not launch dedicated long action. Camera is running.");
            return false;
        }
        String flattenToString = lastComponentName.flattenToString();
        if (launchXCoverTopDedicatedLongAction(flattenToString, i) || launchXCoverTopDedicatedAction(flattenToString, 4, i)) {
            return true;
        }
        return launchXCoverDefaultAction(flattenToString, 4, i, z);
    }

    public boolean launchXCoverPressActionIfNeeded(int i, boolean z) {
        if (FactoryTest.isAutomaticTestMode(this.mContext)) {
            Slog.d("KeyCustomizationManager", "Block handling XCoverKey because of Automatic Test Mode");
            return false;
        }
        if (hasHigherIdWithOppositePress(4, i)) {
            Log.d("KeyCustomizationManager", "Can not launch press action by knox policy");
            return true;
        }
        ComponentName lastComponentName = getLastComponentName(3, i);
        if (lastComponentName == null) {
            return false;
        }
        return launchXCoverActionIfNeeded(3, i, lastComponentName.flattenToString(), z);
    }

    public final boolean launchXCoverActionIfNeeded(int i, int i2, String str, boolean z) {
        if (shouldDispatchEventForCameraShutter(z)) {
            Slog.d("KeyCustomizationManager", "Can not launch dedicated action. Camera is running.");
            return false;
        }
        if (launchXCoverTopDedicatedAction(str, i, i2)) {
            return true;
        }
        return launchXCoverDefaultAction(str, i, i2, z);
    }

    public final boolean launchXCoverDefaultAction(String str, int i, int i2, boolean z) {
        if ("torch/torch".equals(str) && this.mPolicyExt.handleTorchForXCoverKey(isOnLockScreen(i2), i2)) {
            String eventId = getEventId(i, i2);
            if (CoreRune.FW_SA_LOGGING && !TextUtils.isEmpty(eventId)) {
                this.mPolicyExt.sendCoreSaLoggingDimension(eventId, "Torch");
            }
            return true;
        }
        if (!z) {
            return false;
        }
        if ("com.sec.android.app.camera/com.sec.android.app.camera.Camera".equals(str)) {
            launchCamera();
            return true;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        startActivity(i, i2);
        return true;
    }

    public final void launchCamera() {
        ComponentName unflattenFromString = ComponentName.unflattenFromString("com.sec.android.app.camera/com.sec.android.app.camera.Camera");
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.putExtra("isSecure", getKeyguardManager().isKeyguardSecure());
        intent.putExtra("isQuickLaunchMode", true);
        intent.setComponent(unflattenFromString);
        intent.setFlags(268435456);
        if (getKeyguardManager().semIsKeyguardShowingAndNotOccluded()) {
            intent.addFlags(603979776);
            try {
                ActivityTaskManager.getService().requestWaitingForOccluding(0);
            } catch (RemoteException e) {
                Log.d("KeyCustomizationManager", "failed requestWaitingForOccluding, " + e);
            }
        } else {
            intent.addFlags(2097152);
        }
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e2) {
            Slog.w("KeyCustomizationManager", "No activity to launch Camera, ", e2);
        }
    }

    public final KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        }
        return this.mKeyguardManager;
    }

    public void prepareVoiceToTextMessage(int i, KeyEvent keyEvent) {
        ComponentName lastComponentName;
        if (isXCoverOrTopKey(i) && (lastComponentName = getLastComponentName(4, i)) != null && "quickMessageSender/quickMessageSender".equals(lastComponentName.flattenToString())) {
            if (keyEvent.getAction() == 0) {
                this.mIsCalledOpenDictationXCoverTop = false;
            } else if (this.mIsCalledOpenDictationXCoverTop) {
                this.mIsCalledOpenDictationXCoverTop = false;
                this.mPolicyExt.closeDictation(i);
            }
        }
    }

    public final boolean launchXCoverTopDedicatedAction(String str, int i, int i2) {
        String str2;
        boolean z = true;
        if ("home/home".equals(str)) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.policy.KeyCustomizationManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyCustomizationManager.this.lambda$launchXCoverTopDedicatedAction$0();
                }
            });
            str2 = "Home";
        } else if ("back/back".equals(str)) {
            this.mPolicyExt.injectionKeyEvent(4, 0, -1);
            str2 = "Back";
        } else {
            str2 = null;
            z = false;
        }
        String eventId = getEventId(i, i2);
        if (CoreRune.FW_SA_LOGGING && !TextUtils.isEmpty(eventId) && !TextUtils.isEmpty(str2)) {
            this.mPolicyExt.sendCoreSaLoggingDimension(eventId, str2);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$launchXCoverTopDedicatedAction$0() {
        this.mPolicyExt.mPolicy.handleShortPressOnHome(0);
    }

    public final boolean launchXCoverTopDedicatedLongAction(String str, int i) {
        if (!"quickMessageSender/quickMessageSender".equals(str)) {
            return false;
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        if (phoneWindowManagerExt.mIsSamsungKeyboard) {
            this.mIsCalledOpenDictationXCoverTop = true;
            phoneWindowManagerExt.openDictation(i);
        } else {
            Context context = this.mContext;
            phoneWindowManagerExt.showToast(context, context.getResources().getString(17043223));
        }
        String eventId = getEventId(4, i);
        if (CoreRune.FW_SA_LOGGING && !TextUtils.isEmpty(eventId)) {
            this.mPolicyExt.sendCoreSaLoggingDimension(eventId, "Voice to text message");
        }
        return true;
    }

    public final void updateKeyInfoExtra(Intent intent, KeyEvent keyEvent) {
        if (CoreRune.FW_KODIAK_DEDICATED_PTT_APP && isKodiakDedicatedPttApp(intent.getPackage())) {
            intent.putExtra("android.intent.extra.KEY_EVENT", KeyEvent.obtain(keyEvent));
            return;
        }
        boolean z = keyEvent.getAction() == 0;
        intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", keyEvent.getKeyCode());
        intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE", z ? 1 : 2);
    }

    public final boolean startMCPTTServiceIfNeeded(int i, KeyEvent keyEvent) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo;
        Intent intent;
        if (!isXCoverOrTopKey(i) || (lastKeyCustomizationInfo = getLastKeyCustomizationInfo(3, i)) == null || (intent = lastKeyCustomizationInfo.intent) == null || intent.getComponent() == null || !"com.att.firstnet.grey".equals(intent.getComponent().getPackageName())) {
            return false;
        }
        startService(lastKeyCustomizationInfo, 3, i, keyEvent);
        return true;
    }

    public final boolean isKodiakDedicatedPttApp(String str) {
        str.hashCode();
        switch (str) {
            case "com.bell.ptt":
            case "com.sprint.sdcplus":
            case "com.att.eptt":
            case "com.verizon.pushtotalkplus":
                return true;
            default:
                return false;
        }
    }

    public boolean hasB2BDedicatedPower() {
        return getLastId(3, 26) == 951;
    }

    public boolean hasSideKeyDoublePressId() {
        return getLastId(8, 26) == 1104;
    }

    public boolean hasDoubleCameraId(int i) {
        return getLastId(8, i) == 2001;
    }

    public boolean hasDoublePowerTvModeId() {
        return getLastId(8, 26) == 2002;
    }

    public void putHotKey(int i, ComponentName componentName) {
        if (componentName == null) {
            Log.d("KeyCustomizationManager", "componentName is empty. Can not set hot key info.");
        } else {
            this.mKeyCustomizationInfoManager.putHotKey(i, componentName);
        }
    }

    public void removeHotKey(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mKeyCustomizationInfoManager.removeHotKey(str);
    }

    public ComponentName getHotKeyComponentName(int i) {
        return this.mKeyCustomizationInfoManager.getHotKeyComponentName(i);
    }

    public void onUserSwitch(int i) {
        if (i == this.mKeyCustomizationInfoManager.getUserId()) {
            return;
        }
        this.mKeyCustomizationInfoManager.onUserSwitch(i);
        for (int i2 : KeyCustomizationConstants.ALL_KEYCODE_TYPE) {
            initPowerBehaviorAndSingleKeyGestureDetectorRule(i2);
        }
    }

    public void onUserRemove(int i) {
        this.mKeyCustomizationInfoManager.onUserRemove(i);
    }

    public List getBackupKeyCustomizationInfoList() {
        return this.mKeyCustomizationInfoManager.getBackupKeyCustomizationInfoList();
    }

    public void restoreKeyCustomizationInfo(List list) {
        Slog.d("KeyCustomizationManager", "restoreKeyCustomizationInfo, size=" + list.size());
        this.mKeyCustomizationInfoManager.restoreKeyCustomizationInfo(list);
    }

    public boolean hasHigherIdWithAllPress(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo last;
        int i3;
        int lastId = getLastId(i, i2);
        if (lastId >= 1000 || lastId == -1) {
            return false;
        }
        int i4 = 2003;
        for (int i5 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
            if (i5 != i && (last = this.mKeyCustomizationInfoManager.getLast(i5, i2)) != null && i4 > (i3 = last.id)) {
                i4 = i3;
            }
        }
        return i4 < lastId;
    }

    public final boolean hasHigherIdWithOppositePress(int i, int i2) {
        if (this.mPolicyExt.mPolicy.mPowerManager.isInteractive()) {
            return false;
        }
        int lastId = getLastId(i, i2);
        if (!isHigherIdThanDefault(lastId)) {
            return false;
        }
        Log.d("KeyCustomizationManager", "hasHigherIdInOppositePress, press=" + i + " keyCode=" + i2 + " id=" + lastId);
        return true;
    }

    public final Intent getDefaultIntentForKnoxId(int i, KeyEvent keyEvent, boolean z, boolean z2, boolean z3) {
        if (!isKnoxId(i)) {
            return null;
        }
        int keyCode = keyEvent.getKeyCode();
        boolean z4 = false;
        int i2 = 1;
        boolean z5 = keyEvent.getAction() == 0;
        int repeatCount = keyEvent.getRepeatCount();
        if (z5 && repeatCount == 0) {
            z4 = true;
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.HARD_KEY_REPORT");
        intent.setFlags(16777216);
        intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", keyCode);
        if (z) {
            i2 = 4;
        } else if (z2) {
            i2 = 8;
        } else if (!z4) {
            if (z5) {
                i2 = -1;
            } else {
                if (z3) {
                    intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_REPORT_TYPE_NEW_LONG_UP", true);
                }
                i2 = 2;
            }
        }
        intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE_NEW", i2);
        return intent;
    }

    public final boolean migrationForKnoxPolicy(Intent intent, KeyEvent keyEvent, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        int keyCode = keyEvent.getKeyCode();
        boolean z5 = keyEvent.getAction() == 0;
        boolean z6 = z5 && keyEvent.getRepeatCount() == 0;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            z3 = extras.getBoolean("getHardKeyReportState");
            z4 = extras.getBoolean("getHardKeyIntentState");
        } else {
            z3 = false;
            z4 = false;
        }
        if (shouldSkipActionForKnoxPolicy(keyCode, z5, z6, extras, z3, z4)) {
            return true;
        }
        intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", keyCode);
        if (z3) {
            updateHardKeyReport(intent, extras, z5, z6);
        } else if (z4) {
            updateHardKeyIntent(intent, keyCode, z5, z6, z);
        } else {
            updateIntentForKnoxPolicy(intent, z5, z6, z, z2);
        }
        return false;
    }

    public final boolean shouldSkipActionForKnoxPolicy(int i, boolean z, boolean z2, Bundle bundle, boolean z3, boolean z4) {
        if (!z3) {
            return (!z4 || i == 26 || z) ? false : true;
        }
        boolean isReportedKey = isReportedKey(false, bundle);
        boolean isReportedKey2 = isReportedKey(true, bundle);
        if (isReportedKey && isReportedKey2) {
            return !z2 && z;
        }
        if (isReportedKey && z2) {
            return true;
        }
        return isReportedKey2 && !z;
    }

    public final void updateHardKeyReport(Intent intent, Bundle bundle, boolean z, boolean z2) {
        boolean isReportedKey = isReportedKey(false, bundle);
        int i = 1;
        boolean isReportedKey2 = isReportedKey(true, bundle);
        if (isReportedKey && isReportedKey2) {
            if (!z2) {
                if (z) {
                    return;
                } else {
                    i = 2;
                }
            }
            intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE", i);
            return;
        }
        if (isReportedKey) {
            if (z) {
                return;
            }
            intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE", 2);
        } else if (isReportedKey2 && z2) {
            intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE", 1);
        }
    }

    public final void updateHardKeyIntent(Intent intent, int i, boolean z, boolean z2, boolean z3) {
        if (i != 26) {
            return;
        }
        if (z3) {
            intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE", 1);
        } else if (!z) {
            intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE", 2);
        } else if (z2) {
            intent.removeExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE");
        }
    }

    public final void updateIntentForKnoxPolicy(Intent intent, boolean z, boolean z2, boolean z3, boolean z4) {
        int i;
        if (z3) {
            i = 4;
        } else if (z4) {
            i = 8;
        } else if (z2) {
            i = 1;
        } else if (z) {
            return;
        } else {
            i = 2;
        }
        intent.putExtra("com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE_NEW", i);
    }

    public final boolean isReportedKey(boolean z, Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(z ? "reportStateOnKeyedDown" : "reportStateOnKeyedUp");
    }

    public final boolean isBlockedKey(boolean z, Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(z ? "blockedStateOnKeyedDown" : "blockedStateOnKeyedUp");
    }

    public final boolean isBlockedDispatchingByKnoxPolicy(int i, boolean z, int i2, Intent intent, int i3) {
        if (!isKnoxId(i)) {
            return false;
        }
        if (1015 != i3 && 1079 != i3 && 79 != i3) {
            return false;
        }
        if (!z && isReportedKey(false, intent.getExtras()) && isBlockedKey(false, intent.getExtras())) {
            return true;
        }
        return z && i2 == 0 && isReportedKey(true, intent.getExtras()) && isBlockedKey(true, intent.getExtras());
    }

    public final boolean isForceBlockDispatchingLongByKnoxPolicy(int i, int i2, int i3) {
        if (!isKnoxId(i) || (i2 & 4) == 0) {
            return false;
        }
        if (i3 != 187 && i3 != 3) {
            return false;
        }
        Log.i("KeyCustomizationManager", "Long press of key(" + i3 + ") action should be blocked by knox policy");
        return true;
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println();
        printWriter.print(str);
        printWriter.print("KeyCustomization info state: ");
        this.mKeyCustomizationInfoManager.dump(str, printWriter);
        printWriter.println();
    }

    public static String pressToString(int i) {
        return (i & 3) != 0 ? "KEY_PRESS_SINGLE" : (i & 4) != 0 ? "KEY_PRESS_LONG" : (i & 8) != 0 ? "KEY_PRESS_DOUBLE" : (i & 16) != 0 ? "KEY_PRESS_TRIPLE" : (i & 32) != 0 ? "KEY_PRESS_QUADRUPLE" : (i & 64) != 0 ? "KEY_PRESS_QUINTUPLE" : Integer.toString(i);
    }

    public static String actionToString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? Integer.toString(i) : "ACTION_BLOCK_KEY_EVENT" : "ACTION_START_SERVICE" : "ACTION_SEND_BROADCAST" : "ACTION_START_ACTIVITY";
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
}
