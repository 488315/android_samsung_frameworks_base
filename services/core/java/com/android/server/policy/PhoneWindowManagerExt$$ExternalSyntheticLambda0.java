package com.android.server.policy;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.input.InputManager;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;

import com.samsung.android.view.SemWindowManager;

import java.util.ArrayList;
import java.util.Iterator;

public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhoneWindowManagerExt f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PhoneWindowManagerExt$$ExternalSyntheticLambda0(
            PhoneWindowManagerExt phoneWindowManagerExt, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = phoneWindowManagerExt;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InputManager inputManager;
        int i;
        switch (this.$r8$classId) {
            case 0:
                PhoneWindowManagerExt phoneWindowManagerExt = this.f$0;
                int i2 = this.f$1;
                if (!phoneWindowManagerExt.mIsPogoKeyboardConnected
                        || (inputManager = InputManager.getInstance()) == null) {
                    return;
                }
                if (i2 == 0) {
                    inputManager.semRegisterOnMultiFingerGestureListener(
                            phoneWindowManagerExt.mMultiFingerGestureListener, null);
                    return;
                } else {
                    inputManager.semUnregisterOnMultiFingerGestureListener(
                            phoneWindowManagerExt.mMultiFingerGestureListener);
                    return;
                }
            case 1:
                PhoneWindowManagerExt phoneWindowManagerExt2 = this.f$0;
                int i3 = this.f$1;
                KeyCustomizationManager keyCustomizationManager =
                        phoneWindowManagerExt2.mKeyCustomizationPolicy;
                KeyCustomizationInfoManager keyCustomizationInfoManager =
                        keyCustomizationManager.mKeyCustomizationInfoManager;
                if (i3 == keyCustomizationInfoManager.mUserId) {
                    return;
                }
                Slog.d(
                        "KeyCustomizationInfoManager",
                        "onUserSwitch oldId="
                                + keyCustomizationInfoManager.mUserId
                                + " newId="
                                + i3);
                keyCustomizationInfoManager.mUserId = i3;
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                        keyCustomizationInfoManager.get(1104, 4, 26, null);
                if (keyCustomizationInfo != null) {
                    keyCustomizationInfoManager.mGlobalSideKeyLongInfo =
                            new SemWindowManager.KeyCustomizationInfo(
                                    keyCustomizationInfo.press,
                                    keyCustomizationInfo.id,
                                    keyCustomizationInfo.keyCode,
                                    keyCustomizationInfo.action,
                                    keyCustomizationInfo.intent);
                } else {
                    keyCustomizationInfoManager.mGlobalSideKeyLongInfo = null;
                }
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 =
                        keyCustomizationInfoManager.get(1104, 8, 26, null);
                if (keyCustomizationInfo2 != null) {
                    keyCustomizationInfoManager.mGlobalSideKeyDoubleInfo =
                            new SemWindowManager.KeyCustomizationInfo(
                                    keyCustomizationInfo2.press,
                                    keyCustomizationInfo2.id,
                                    keyCustomizationInfo2.keyCode,
                                    keyCustomizationInfo2.action,
                                    keyCustomizationInfo2.intent);
                } else {
                    keyCustomizationInfoManager.mGlobalSideKeyDoubleInfo = null;
                }
                synchronized (keyCustomizationInfoManager.mLock) {
                    try {
                        for (int i4 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                            keyCustomizationInfoManager.getInfoMapLocked(i4).clear();
                            keyCustomizationInfoManager.getLastInfoLocked(i4).clear();
                        }
                        keyCustomizationInfoManager.mOwnerPackageList.clear();
                        keyCustomizationInfoManager.mHotKeyMap.clear();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                keyCustomizationInfoManager.init(i3, true);
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo3 =
                        keyCustomizationInfoManager.get(1104, 4, 26, null);
                SemWindowManager.KeyCustomizationInfo last =
                        keyCustomizationInfoManager.getLast(4, 26);
                if (keyCustomizationInfoManager.mGlobalSideKeyLongInfo != null) {
                    if ((keyCustomizationInfo3 == null && last == null)
                            || (keyCustomizationInfo3 != null
                                    && keyCustomizationInfo3.id == 1104)) {
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo4 =
                                keyCustomizationInfoManager.mGlobalSideKeyLongInfo;
                        keyCustomizationInfoManager.put(
                                new SemWindowManager.KeyCustomizationInfo(
                                        keyCustomizationInfo4.press,
                                        keyCustomizationInfo4.id,
                                        keyCustomizationInfo4.keyCode,
                                        keyCustomizationInfo4.action,
                                        keyCustomizationInfo4.intent),
                                true);
                    }
                } else if ((keyCustomizationInfo3 == null && last == null)
                        || (keyCustomizationInfo3 != null && keyCustomizationInfo3.id == 1104)) {
                    keyCustomizationInfoManager.put(
                            keyCustomizationInfoManager.getSideKeyLongInfoFromGlobalSetting(),
                            true);
                }
                keyCustomizationInfoManager.mGlobalSideKeyLongInfo = null;
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo5 =
                        keyCustomizationInfoManager.get(1104, 8, 26, null);
                SemWindowManager.KeyCustomizationInfo last2 =
                        keyCustomizationInfoManager.getLast(8, 26);
                if (keyCustomizationInfoManager.mGlobalSideKeyDoubleInfo != null) {
                    if ((keyCustomizationInfo5 == null && last2 == null)
                            || (keyCustomizationInfo5 != null
                                    && keyCustomizationInfo5.id == 1104)) {
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo6 =
                                keyCustomizationInfoManager.mGlobalSideKeyDoubleInfo;
                        keyCustomizationInfoManager.put(
                                new SemWindowManager.KeyCustomizationInfo(
                                        keyCustomizationInfo6.press,
                                        keyCustomizationInfo6.id,
                                        keyCustomizationInfo6.keyCode,
                                        keyCustomizationInfo6.action,
                                        keyCustomizationInfo6.intent),
                                true);
                    }
                } else if ((keyCustomizationInfo5 == null && last2 == null)
                        || (keyCustomizationInfo5 != null && keyCustomizationInfo5.id == 1104)) {
                    SemWindowManager.KeyCustomizationInfo sideKeyDoubleInfoFromGlobalSetting =
                            keyCustomizationInfoManager.getSideKeyDoubleInfoFromGlobalSetting();
                    if (sideKeyDoubleInfoFromGlobalSetting == null) {
                        keyCustomizationInfoManager.remove(1104, 8, null, 26, true);
                    } else {
                        keyCustomizationInfoManager.put(sideKeyDoubleInfoFromGlobalSetting, true);
                    }
                }
                keyCustomizationInfoManager.mGlobalSideKeyDoubleInfo = null;
                synchronized (keyCustomizationInfoManager.mLock) {
                    keyCustomizationInfoManager.saveSettingsLocked(i3);
                }
                for (int i5 : KeyCustomizationConstants.ALL_KEYCODE_TYPE) {
                    keyCustomizationManager.initPowerBehaviorAndSingleKeyGestureDetectorRule(i5);
                }
                return;
            default:
                PhoneWindowManagerExt phoneWindowManagerExt3 = this.f$0;
                int i6 = this.f$1;
                if (phoneWindowManagerExt3.mBootMsgDialogs.isEmpty()) {
                    for (Display display :
                            phoneWindowManagerExt3.mPolicy.mDisplayManager.getDisplays(
                                    "android.hardware.display.category.ALL_INCLUDING_DISABLED")) {
                        if (display.getType() == 1) {
                            Context createDisplayContext =
                                    display.getDisplayId() != 0
                                            ? phoneWindowManagerExt3.mContext.createDisplayContext(
                                                    display)
                                            : phoneWindowManagerExt3.mContext;
                            ArrayList arrayList = phoneWindowManagerExt3.mBootMsgDialogs;
                            PhoneWindowManagerExt.AnonymousClass15 anonymousClass15 =
                                    new PhoneWindowManagerExt.AnonymousClass15(
                                            createDisplayContext);
                            anonymousClass15.setProgressStyle(0);
                            anonymousClass15.setIndeterminate(false);
                            anonymousClass15.getWindow().setType(2021);
                            anonymousClass15.getWindow().addFlags(258);
                            anonymousClass15.getWindow().setDimAmount(1.0f);
                            WindowManager.LayoutParams attributes =
                                    anonymousClass15.getWindow().getAttributes();
                            attributes.setTitle(
                                    "BootProgressDialog_d" + createDisplayContext.getDisplayId());
                            if (createDisplayContext.getDisplayId() != 0) {
                                DisplayInfo displayInfo = new DisplayInfo();
                                if (createDisplayContext
                                        .getDisplayNoVerify()
                                        .getDisplayInfo(displayInfo)) {
                                    attributes.width = displayInfo.logicalWidth;
                                    attributes.height = displayInfo.logicalHeight;
                                }
                            }
                            attributes.screenOrientation = 5;
                            attributes.layoutInDisplayCutoutMode = 1;
                            anonymousClass15.getWindow().setAttributes(attributes);
                            anonymousClass15.setMax(100);
                            anonymousClass15.setCancelable(false);
                            anonymousClass15.show();
                            arrayList.add(anonymousClass15);
                        }
                    }
                }
                Iterator it = phoneWindowManagerExt3.mBootMsgDialogs.iterator();
                while (it.hasNext()) {
                    ProgressDialog progressDialog = (ProgressDialog) it.next();
                    progressDialog.setMessage(i6 + "%");
                    progressDialog.setProgress(i6);
                }
                return;
        }
    }
}
