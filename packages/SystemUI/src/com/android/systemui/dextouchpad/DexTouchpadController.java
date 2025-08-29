package com.android.systemui.dextouchpad;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.InputMonitor;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.activity.ButtonWindowController;
import com.android.systemui.dextouchpad.activity.RotationButtonWindow;
import com.android.systemui.dextouchpad.activity.TouchpadActivity;
import com.android.systemui.dextouchpad.activity.TouchpadFragment;
import com.android.systemui.dextouchpad.activity.TouchpadViewModel;
import com.android.systemui.dextouchpad.manager.NavBarIconManager;
import com.android.systemui.dextouchpad.manager.notification.DualModeReceiver;
import com.android.systemui.dextouchpad.manager.notification.NotificationType;
import com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager;
import com.android.systemui.dextouchpad.settings.Settings$Key;
import com.android.systemui.dextouchpad.settings.SettingsKeys;
import com.android.systemui.dextouchpad.settings.SettingsRepository;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.dextouchpad.util.Utils;
import com.android.systemui.dextouchpad.view.StartingGuideDialog;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class DexTouchpadController implements CoreStartable {
    public static final Handler mMainHandler = new Handler(Looper.getMainLooper());
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public SpenInputEventReceiver mDefaultInputEventReceiver;
    public InputMonitor mDefaultInputMonitor;
    public SpenInputEventReceiver mDexInputEventReceiver;
    public InputMonitor mDexInputMonitor;
    public final DisplayManager mDisplayManager;
    public final AnonymousClass3 mMultiuserReceiver;
    public NavBarIconManager mNavBarIconManager;
    public final SettingsObserver mSettingsObserver;
    public final SettingsRepository mSettingsRepo;
    public StartingGuideDialog mStartingGuideDialog;
    public final TouchpadNotificationManager mTouchpadNotificationManager;
    public int mNavBarMode = 0;
    public boolean mIsConnectedDexMode = false;
    public final Set mDexDisplayIds = new HashSet();
    public int mUserId = 0;
    public final AnonymousClass1 mCommandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.dextouchpad.DexTouchpadController.1
        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void notifyPenState(int i) {
            if (Features.IS_SUPPORT_SPEN) {
                DexTouchpadController dexTouchpadController = DexTouchpadController.this;
                if (dexTouchpadController.mTouchpadNotificationManager == null) {
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "notifyPenState, state=", "DexTouchpadController");
                TouchpadNotificationManager touchpadNotificationManager = dexTouchpadController.mTouchpadNotificationManager;
                touchpadNotificationManager.getClass();
                if (i == 1) {
                    Utils.mIsSpenDetached = false;
                    touchpadNotificationManager.remove(NotificationType.SPEN);
                } else if (i == 0) {
                    Utils.mIsSpenDetached = true;
                    if (Utils.mIsTouchpadEnabled) {
                        touchpadNotificationManager.show(NotificationType.SPEN);
                    }
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayAddSystemDecorations(int i) {
            String string;
            DexTouchpadController dexTouchpadController = DexTouchpadController.this;
            Handler handler = DexTouchpadController.mMainHandler;
            dexTouchpadController.setTouchpadActivityEnabled(true);
            if (DexTouchpadController.this.canLaunchDexTouchPad(i, "onDisplayAddSystemDecorations", true)) {
                ((HashSet) DexTouchpadController.this.mDexDisplayIds).add(Integer.valueOf(i));
                ContentResolver contentResolver = DexTouchpadController.this.mContext.getContentResolver();
                boolean z = Settings.Global.getInt(contentResolver, "autorun_touchpad", 0) == 1;
                int intForUser = Settings.Secure.getIntForUser(contentResolver, SettingsHelper.INDEX_NAVIGATION_MODE, 0, -2);
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, intForUser, "onDisplayAddSystemDecorations, displayId=", " navBarMode=", " autoRunTouchpad=");
                sbM.append(z);
                sbM.append(" dexDisplayIds=");
                sbM.append(DexTouchpadController.this.mDexDisplayIds);
                Log.d("DexTouchpadController", sbM.toString());
                DexTouchpadController dexTouchpadController2 = DexTouchpadController.this;
                dexTouchpadController2.mNavBarMode = intForUser;
                dexTouchpadController2.mIsConnectedDexMode = true;
                Utils.mDesktopDisplayId = i;
                SettingsObserver settingsObserver = dexTouchpadController2.mSettingsObserver;
                settingsObserver.mResolver.registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_NAVIGATION_MODE), false, settingsObserver, -1);
                settingsObserver.mResolver.registerContentObserver(Settings.Global.getUriFor("autorun_touchpad"), false, settingsObserver, -1);
                TouchpadNotificationManager touchpadNotificationManager = DexTouchpadController.this.mTouchpadNotificationManager;
                if (!touchpadNotificationManager.mDMReceiverRegistered) {
                    Context context = touchpadNotificationManager.mContext;
                    DualModeReceiver dualModeReceiver = touchpadNotificationManager.mDualModeReceiver;
                    dualModeReceiver.getClass();
                    IntentFilter intentFilter = new IntentFilter();
                    if (Features.IS_SUPPORT_SPEN) {
                        intentFilter.addAction("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED");
                        intentFilter.addAction("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED");
                    }
                    intentFilter.addAction("com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED");
                    context.registerReceiver(dualModeReceiver, intentFilter, 4);
                    touchpadNotificationManager.mDMReceiverRegistered = true;
                }
                DexTouchpadController.this.getClass();
                DexTouchpadController dexTouchpadController3 = DexTouchpadController.this;
                SettingsRepository settingsRepository = dexTouchpadController3.mSettingsRepo;
                Settings$Key settings$Key = SettingsKeys.TOUCHPAD_STARTING_GUIDE;
                synchronized (settingsRepository.mLock) {
                    string = settingsRepository.mPrefs.getString(settings$Key.mName, settings$Key.mDefValue);
                }
                if (!"confirmed".equals(string)) {
                    if (dexTouchpadController3.mStartingGuideDialog == null) {
                        dexTouchpadController3.mStartingGuideDialog = new StartingGuideDialog(dexTouchpadController3.mContext, dexTouchpadController3.mNavBarMode);
                    }
                    dexTouchpadController3.mStartingGuideDialog.show();
                }
                DexTouchpadController.this.updateIconOrNotification(intForUser);
                DexTouchpadController dexTouchpadController4 = DexTouchpadController.this;
                if (dexTouchpadController4.mDefaultInputMonitor == null) {
                    dexTouchpadController4.mDefaultInputMonitor = InputManager.getInstance().monitorGestureInput("touchpad-window", 0);
                }
                if (dexTouchpadController4.mDefaultInputEventReceiver == null) {
                    dexTouchpadController4.mDefaultInputEventReceiver = new SpenInputEventReceiver(dexTouchpadController4.mDefaultInputMonitor.getInputChannel(), Looper.myLooper(), dexTouchpadController4.mTouchpadNotificationManager);
                }
                DexTouchpadController dexTouchpadController5 = DexTouchpadController.this;
                if (dexTouchpadController5.mDexInputMonitor == null) {
                    dexTouchpadController5.mDexInputMonitor = InputManager.getInstance().monitorGestureInput("touchpad-window", Utils.mDesktopDisplayId);
                }
                if (dexTouchpadController5.mDexInputEventReceiver == null) {
                    dexTouchpadController5.mDexInputEventReceiver = new SpenInputEventReceiver(dexTouchpadController5.mDexInputMonitor.getInputChannel(), Looper.myLooper(), dexTouchpadController5.mTouchpadNotificationManager);
                }
                if (z) {
                    Context context2 = DexTouchpadController.this.mContext;
                    Log.d("DexTouchpadUtils", "startTouchpadActivity, callers=" + Debug.getCallers(3));
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setLaunchDisplayId(0);
                    context2.startActivityAsUser(Utils.getTouchActivityIntent(), activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT);
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayRemoveSystemDecorations(int i) {
            Handler handler = DexTouchpadController.mMainHandler;
            DexTouchpadController dexTouchpadController = DexTouchpadController.this;
            if (dexTouchpadController.canLaunchDexTouchPad(i, "onDisplayRemoveSystemDecorations", false)) {
                ((HashSet) dexTouchpadController.mDexDisplayIds).remove(Integer.valueOf(i));
                Log.d("DexTouchpadController", "onDisplayRemoveSystemDecorations displayId=" + i + " dexDisplayIds=" + dexTouchpadController.mDexDisplayIds);
                if (((HashSet) dexTouchpadController.mDexDisplayIds).isEmpty()) {
                    DexTouchpadController.m2566$$Nest$mremoveAll(dexTouchpadController);
                    Utils.mDesktopDisplayId = -1;
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayRemoved(int i) {
            Handler handler = DexTouchpadController.mMainHandler;
            DexTouchpadController dexTouchpadController = DexTouchpadController.this;
            if (dexTouchpadController.canLaunchDexTouchPad(i, "onDisplayRemoved", false)) {
                ((HashSet) dexTouchpadController.mDexDisplayIds).remove(Integer.valueOf(i));
                Log.d("DexTouchpadController", "onDisplayRemoved, displayId=" + i + " dexDisplayIds=" + dexTouchpadController.mDexDisplayIds);
                if (((HashSet) dexTouchpadController.mDexDisplayIds).isEmpty()) {
                    DexTouchpadController.m2566$$Nest$mremoveAll(dexTouchpadController);
                }
            }
        }
    };

    public class SettingsObserver extends ContentObserver {
        public final ContentResolver mResolver;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mResolver = DexTouchpadController.this.mContext.getContentResolver();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            DexTouchpadController dexTouchpadController = DexTouchpadController.this;
            String lastPathSegment = uri.getLastPathSegment();
            Handler handler = DexTouchpadController.mMainHandler;
            dexTouchpadController.getClass();
            if (!SettingsHelper.INDEX_NAVIGATION_MODE.equals(lastPathSegment)) {
                if ("autorun_touchpad".equals(lastPathSegment)) {
                    dexTouchpadController.mSettingsRepo.putInt(SettingsKeys.TOUCHPAD_AUTO_RUN_GUIDE_COUNT, 4);
                    return;
                }
                return;
            }
            int intForUser = Settings.Secure.getIntForUser(dexTouchpadController.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_MODE, 0, -2);
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged(), navBarMode old="), dexTouchpadController.mNavBarMode, " new=", intForUser, "DexTouchpadController");
            if (dexTouchpadController.mIsConnectedDexMode && dexTouchpadController.mNavBarMode != intForUser) {
                dexTouchpadController.mNavBarMode = intForUser;
                if (dexTouchpadController.mNavBarIconManager.mHasNavBarIcon) {
                    DexTouchpadController.mMainHandler.post(new DexTouchpadController$$ExternalSyntheticLambda1(dexTouchpadController, 2));
                }
                TouchpadNotificationManager touchpadNotificationManager = dexTouchpadController.mTouchpadNotificationManager;
                if (!((HashMap) touchpadNotificationManager.mActiveNotifications).isEmpty()) {
                    DexTouchpadController.mMainHandler.post(new DexTouchpadController$$ExternalSyntheticLambda1(touchpadNotificationManager, 3));
                }
                dexTouchpadController.updateIconOrNotification(dexTouchpadController.mNavBarMode);
            }
        }
    }

    /* renamed from: -$$Nest$mremoveAll, reason: not valid java name */
    public static void m2566$$Nest$mremoveAll(DexTouchpadController dexTouchpadController) {
        dexTouchpadController.mIsConnectedDexMode = false;
        SettingsObserver settingsObserver = dexTouchpadController.mSettingsObserver;
        settingsObserver.mResolver.unregisterContentObserver(settingsObserver);
        TouchpadNotificationManager touchpadNotificationManager = dexTouchpadController.mTouchpadNotificationManager;
        if (touchpadNotificationManager.mDMReceiverRegistered) {
            touchpadNotificationManager.mDMReceiverRegistered = false;
            Context context = touchpadNotificationManager.mContext;
            DualModeReceiver dualModeReceiver = touchpadNotificationManager.mDualModeReceiver;
            dualModeReceiver.getClass();
            context.unregisterReceiver(dualModeReceiver);
        }
        boolean z = dexTouchpadController.mNavBarIconManager.mHasNavBarIcon;
        Handler handler = mMainHandler;
        if (z) {
            handler.post(new DexTouchpadController$$ExternalSyntheticLambda1(dexTouchpadController, 2));
        }
        if (!((HashMap) touchpadNotificationManager.mActiveNotifications).isEmpty()) {
            handler.post(new DexTouchpadController$$ExternalSyntheticLambda1(touchpadNotificationManager, 3));
        }
        if (TouchpadActivity.getActivity() != null) {
            TouchpadActivity activity = TouchpadActivity.getActivity();
            activity.getClass();
            if (Features.DEBUG) {
                Log.d("DexTouchpadActivity", "finishActivity()");
            }
            activity.getApplicationContext().unregisterReceiver(activity.mFinishActivityReceiver);
            TouchpadActivity.mActivity = null;
            activity.finish();
        }
        StartingGuideDialog startingGuideDialog = dexTouchpadController.mStartingGuideDialog;
        if (startingGuideDialog != null && startingGuideDialog.isShowing()) {
            dexTouchpadController.mStartingGuideDialog.dismiss();
        }
        dexTouchpadController.setTouchpadActivityEnabled(false);
        InputMonitor inputMonitor = dexTouchpadController.mDefaultInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            dexTouchpadController.mDefaultInputMonitor = null;
        }
        SpenInputEventReceiver spenInputEventReceiver = dexTouchpadController.mDefaultInputEventReceiver;
        if (spenInputEventReceiver != null) {
            spenInputEventReceiver.dispose();
            dexTouchpadController.mDefaultInputEventReceiver = null;
        }
        InputMonitor inputMonitor2 = dexTouchpadController.mDexInputMonitor;
        if (inputMonitor2 != null) {
            inputMonitor2.dispose();
            dexTouchpadController.mDexInputMonitor = null;
        }
        SpenInputEventReceiver spenInputEventReceiver2 = dexTouchpadController.mDexInputEventReceiver;
        if (spenInputEventReceiver2 != null) {
            spenInputEventReceiver2.dispose();
            dexTouchpadController.mDexInputEventReceiver = null;
        }
        if (dexTouchpadController.mUserId == 0) {
            return;
        }
        Log.d("DexTouchpadController", "send broadcast finish touchpad activity, userId=" + dexTouchpadController.mUserId);
        Intent intent = new Intent("com.samsung.android.desktopmode.action.FINISH_DEX_TOUCHPAD_ACTIVITY");
        intent.setPackage("com.android.systemui");
        dexTouchpadController.mContext.sendBroadcastAsUser(intent, UserHandle.of(dexTouchpadController.mUserId));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.dextouchpad.DexTouchpadController$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.BroadcastReceiver, com.android.systemui.dextouchpad.DexTouchpadController$3] */
    public DexTouchpadController(Context context, CommandQueue commandQueue) {
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.dextouchpad.DexTouchpadController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    ListPopupWindow$$ExternalSyntheticOutline0.m(intExtra, "User changed userId=", "DexTouchpadController");
                    DexTouchpadController dexTouchpadController = DexTouchpadController.this;
                    if (dexTouchpadController.mUserId != intExtra) {
                        dexTouchpadController.mUserId = intExtra;
                    }
                }
            }
        };
        this.mMultiuserReceiver = r0;
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mSettingsRepo = SettingsRepository.getInstance(context);
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mTouchpadNotificationManager = TouchpadNotificationManager.getsInstance(context);
        this.mSettingsObserver = new SettingsObserver(mMainHandler);
        context.registerReceiver(r0, new IntentFilter("android.intent.action.USER_SWITCHED"));
    }

    public final boolean canLaunchDexTouchPad(int i, String str, boolean z) {
        Display display;
        if (Features.IS_FACTORY_BINARY) {
            Log.d("DexTouchpadController", str.concat(", factory binary"));
            return false;
        }
        if (!z) {
            return true;
        }
        if (i != 0 && i != -1 && (display = this.mDisplayManager.getDisplay(i)) != null) {
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            if ((displayInfo.flags & 131072) != 0) {
                Log.d("DexTouchpadController", "isDesktopWindowing is true, display id=" + display.getDisplayId());
                return true;
            }
        }
        Log.d("DexTouchpadController", str.concat(", not desktopWindowing mode"));
        return false;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        TouchpadViewModel touchpadViewModel;
        printWriter.println("DexTouchpadController");
        printWriter.print("    ");
        printWriter.print("isConnectedDexMode=");
        printWriter.print(this.mIsConnectedDexMode);
        printWriter.print(" navBarMode=");
        printWriter.println(this.mNavBarMode);
        printWriter.print("dexDisplayIds=");
        printWriter.println(this.mDexDisplayIds);
        printWriter.print("userId=");
        printWriter.println(this.mUserId);
        printWriter.println();
        if (TouchpadActivity.getActivity() != null) {
            Fragment fragmentFindFragmentById = TouchpadActivity.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if ((fragmentFindFragmentById instanceof TouchpadFragment) && (touchpadViewModel = ((TouchpadFragment) fragmentFindFragmentById).mViewModel) != null) {
                printWriter.println("TouchpadViewModel");
                printWriter.print("    ");
                printWriter.print("isTouchpadEnabled=");
                printWriter.print(touchpadViewModel.mIsTouchpadEnabled);
                printWriter.print(" isTouchpadAutoRunShown=");
                printWriter.print(touchpadViewModel.mIsTouchpadAutoRunShown);
                printWriter.println();
                ButtonWindowController buttonWindowController = touchpadViewModel.mButtonWindowController;
                if (buttonWindowController != null) {
                    printWriter.println("ButtonWindowController");
                    printWriter.print("    ");
                    printWriter.print("nightMode=");
                    printWriter.print(buttonWindowController.mNightMode);
                    printWriter.println();
                    RotationButtonWindow rotationButtonWindow = buttonWindowController.mRotationButtonWindow;
                    if (rotationButtonWindow != null) {
                        printWriter.println("RotationButtonWindow");
                        printWriter.print("    ");
                        printWriter.print("latestRotation=");
                        printWriter.print(rotationButtonWindow.mLatestRotation);
                        printWriter.println();
                    }
                }
            }
        }
        SettingsRepository settingsRepository = this.mSettingsRepo;
        synchronized (settingsRepository.mLock) {
            try {
                printWriter.println("SettingsRepository");
                for (Map.Entry<String, ?> entry : settingsRepository.mPrefs.getAll().entrySet()) {
                    printWriter.print("    ");
                    printWriter.print(entry.getKey());
                    printWriter.print("=");
                    printWriter.println(entry.getValue());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final boolean isDumpCritical() {
        return true;
    }

    public final void setTouchpadActivityEnabled(boolean z) {
        if (Features.DEBUG) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setTouchpadActivityEnabled: ", "DexTouchpadController", z);
        }
        this.mContext.getPackageManager().setComponentEnabledSetting(new ComponentName("com.android.systemui", "com.android.systemui.dextouchpad.activity.TouchpadActivity"), z ? 1 : 2, 1);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this.mCommandQueueCallbacks);
    }

    public final void updateIconOrNotification(int i) {
        Handler handler = mMainHandler;
        if (i == 0) {
            handler.post(new DexTouchpadController$$ExternalSyntheticLambda1(this, 0));
        } else if (QuickStepContract.isGesturalMode(i)) {
            handler.post(new DexTouchpadController$$ExternalSyntheticLambda1(this, 1));
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
    }
}
