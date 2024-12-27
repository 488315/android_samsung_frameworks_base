package com.android.systemui.popup;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SamsungScreenPinningRequest implements DialogInterface.OnClickListener, NavigationModeController.ModeChangedListener {
    private static final int PIN_WINDOWS_DEX = 0;
    private static final int PIN_WINDOWS_EXCLUDED = 1;
    private static final int PIN_WINDOWS_NORMAL = 2;
    private static final String TAG = "SamsungScreenPinningRequest";
    private String mAppName;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final Context mContext;
    private AlertDialog mDialog;
    private boolean mIsExcluded;
    private LogWrapper mLogWrapper;
    private int mTaskId;
    private boolean mTouchExplorationEnabled;
    public final BroadcastReceiver mPinWindowsReceiver = new BroadcastReceiver() { // from class: com.android.systemui.popup.SamsungScreenPinningRequest.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (SamsungScreenPinningRequest.this.mDialog == null || !SamsungScreenPinningRequest.this.mDialog.isShowing()) {
                return;
            }
            SamsungScreenPinningRequest.this.clearPrompt();
        }
    };
    private ActivityManagerWrapper mActivityManagerWrapper = ActivityManagerWrapper.sInstance;
    private PackageManagerWrapper mPackageManagerWrapper = PackageManagerWrapper.sInstance;
    private int mNavBarMode = ((NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class)).addListener(this);

    public SamsungScreenPinningRequest(Context context, LogWrapper logWrapper, BroadcastDispatcher broadcastDispatcher) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    private void createDialog(int i, LinearLayout linearLayout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
        if (i == 0) {
            builder.setTitle(R.string.lock_to_app_dex_title);
            builder.setMessage(R.string.lock_to_app_dex_desc);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.popup.SamsungScreenPinningRequest$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    SamsungScreenPinningRequest.this.lambda$createDialog$0(dialogInterface, i2);
                }
            });
        } else if (i == 1) {
            builder.setTitle(this.mContext.getString(R.string.lock_to_app_unable_to_pin_title, this.mAppName));
            builder.setMessage(R.string.lock_to_app_unable_to_pin_desc);
            builder.setNegativeButton(android.R.string.ok, this);
        } else if (i == 2) {
            builder.setTitle(this.mContext.getString(R.string.lock_to_app_normal_title));
            builder.setView(linearLayout);
            builder.setPositiveButton(R.string.lock_to_app_positive, this);
        }
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.getWindow().getAttributes().setTitle(TAG);
        this.mDialog.getWindow().setType(2008);
        this.mDialog.getWindow().getAttributes().semAddPrivateFlags(16);
        this.mDialog.show();
    }

    private void extractRecentTaskInfo(int i) {
        List recentTasks = this.mActivityManagerWrapper.mAtm.getRecentTasks(ActivityTaskManager.getMaxRecentTasksStatic(), 2, UserHandle.myUserId());
        int size = recentTasks.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) recentTasks.get(i2);
            ComponentName componentName = recentTaskInfo.origActivity;
            if (componentName == null) {
                componentName = recentTaskInfo.realActivity;
            }
            PackageManagerWrapper packageManagerWrapper = this.mPackageManagerWrapper;
            int i3 = recentTaskInfo.userId;
            packageManagerWrapper.getClass();
            ActivityInfo activityInfo = PackageManagerWrapper.getActivityInfo(componentName, i3);
            if (activityInfo == null) {
                return;
            }
            if (recentTaskInfo.persistentId == i) {
                ActivityManagerWrapper activityManagerWrapper = this.mActivityManagerWrapper;
                Context context = this.mContext;
                int i4 = recentTaskInfo.id;
                activityManagerWrapper.getClass();
                PackageManager packageManager = context.getPackageManager();
                String charSequence = activityInfo.loadLabel(packageManager).toString();
                if (i4 != UserHandle.myUserId()) {
                    charSequence = packageManager.getUserBadgedLabel(charSequence, new UserHandle(i4)).toString();
                }
                this.mAppName = charSequence;
                boolean z = (recentTaskInfo.baseIntent.getFlags() & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) != 0;
                this.mIsExcluded = z;
                if (z) {
                    this.mLogWrapper.d(TAG, "flag:" + String.valueOf(recentTaskInfo.baseIntent.getFlags()) + " / intent:" + String.valueOf(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED));
                }
            } else {
                i2++;
            }
        }
        this.mTaskId = i;
        this.mLogWrapper.d(TAG, "New taskId: " + String.valueOf(this.mTaskId));
    }

    private LinearLayout getContentsView() {
        boolean isGesturalMode = BasicRune.NAVBAR_GESTURE ? com.android.systemui.shared.system.QuickStepContract.isGesturalMode(this.mNavBarMode) : false;
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_Dialog_Alert)).inflate(hasNavigationBar(this.mContext.getDisplayId()) ? isGesturalMode ? R.layout.screen_pinning_content_view_gesture : R.layout.screen_pinning_content_view_swkey : R.layout.screen_pinning_content_view_hwkey, (ViewGroup) null);
        setPinWindowsOptionalText(isGesturalMode, linearLayout);
        if (!isGesturalMode) {
            int i = Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_KEY_ORDER, 0);
            setPinWindowsKeyImage(linearLayout, i);
            setPinWindowsGestureImage(linearLayout, i);
        }
        return linearLayout;
    }

    private boolean hasNavigationBar(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().hasNavigationBar(i);
        } catch (RemoteException unused) {
            this.mLogWrapper.e(TAG, "hasNavigationBar failed");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDialog$0(DialogInterface dialogInterface, int i) {
        clearPrompt();
    }

    private void setPinWindowsGestureImage(LinearLayout linearLayout, int i) {
        this.mTouchExplorationEnabled = onTouchExplorationEnabled();
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.right_gesture);
        ImageView imageView2 = (ImageView) linearLayout.findViewById(R.id.left_gesture);
        if (!this.mTouchExplorationEnabled) {
            imageView.setVisibility(0);
            imageView2.setVisibility(0);
        } else if (i == 0) {
            imageView2.setVisibility(0);
            imageView.setVisibility(4);
        } else {
            imageView2.setVisibility(4);
            imageView.setVisibility(0);
        }
    }

    private void setPinWindowsKeyImage(LinearLayout linearLayout, int i) {
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.left_key);
        ImageView imageView2 = (ImageView) linearLayout.findViewById(R.id.right_key);
        int i2 = R.drawable.pin_windows_ic_recent;
        imageView.setImageResource(i == 0 ? R.drawable.pin_windows_ic_recent : R.drawable.pin_windows_ic_back);
        if (i == 0) {
            i2 = R.drawable.pin_windows_ic_back;
        }
        imageView2.setImageResource(i2);
        int color = this.mContext.getResources().getColor(hasNavigationBar(this.mContext.getDisplayId()) ? R.color.screen_pinning_dialog_button : R.color.screen_pinning_dialog_button_hw, null);
        imageView.setImageTintList(ColorStateList.valueOf(color));
        imageView2.setImageTintList(ColorStateList.valueOf(color));
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1;
        if (hasNavigationBar(this.mContext.getDisplayId()) && z) {
            if (i == 0) {
                imageView2.setScaleX(-1.0f);
            } else {
                imageView.setScaleX(-1.0f);
            }
        }
    }

    private void setPinWindowsOptionalText(boolean z, LinearLayout linearLayout) {
        TextView textView = (TextView) linearLayout.findViewById(R.id.pinning_desc_optional);
        this.mTouchExplorationEnabled = onTouchExplorationEnabled();
        if (hasNavigationBar(this.mContext.getDisplayId())) {
            textView.setText(this.mContext.getString(z ? R.string.screen_pinning_msg_in_gesture : this.mTouchExplorationEnabled ? R.string.lock_to_app_recent_and_back_softkey_accessibility : R.string.lock_to_app_recent_and_back_softkey));
        } else {
            textView.setText(this.mContext.getString(this.mTouchExplorationEnabled ? R.string.lock_to_app_recent_and_back_accessibility : R.string.lock_to_app_recent_and_back));
        }
    }

    public boolean checkUnableToPin() {
        return this.mIsExcluded || this.mActivityManagerWrapper.getRunningTask() == null;
    }

    public void clearPrompt() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mDialog = null;
            unregisterReceivers();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (-1 == i) {
            if (checkUnableToPin()) {
                Context context = this.mContext;
                Toast.makeText(context, context.getString(R.string.lock_to_app_unable_to_pin_toast, this.mAppName), 0).show();
            } else {
                try {
                    ActivityTaskManager.getService().startSystemLockTaskMode(this.mTaskId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        clearPrompt();
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    public boolean onTouchExplorationEnabled() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        return accessibilityManager != null && accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }

    public void registerReceivers() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mPinWindowsReceiver);
    }

    public void showPrompt(int i, boolean z, String str) {
        this.mLogWrapper.d(TAG, "Old taskId: " + String.valueOf(this.mTaskId));
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            if (this.mTaskId == i) {
                return;
            } else {
                clearPrompt();
            }
        }
        extractRecentTaskInfo(i);
        this.mAppName = str;
        this.mIsExcluded = z;
        this.mTaskId = i;
        registerReceivers();
        if (this.mIsExcluded) {
            createDialog(1, null);
        } else {
            createDialog(2, getContentsView());
        }
        this.mLogWrapper.d(TAG, "New taskId: " + String.valueOf(this.mTaskId) + "mIsExcluded: " + this.mIsExcluded);
    }

    public void unregisterReceivers() {
        try {
            this.mBroadcastDispatcher.unregisterReceiver(this.mPinWindowsReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
