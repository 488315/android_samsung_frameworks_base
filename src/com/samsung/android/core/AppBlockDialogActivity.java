package com.samsung.android.core;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.samsung.android.core.AppJumpBlockTool;
import com.sec.android.iaft.SmLib_IafdConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class AppBlockDialogActivity extends AlertActivity implements AutoCloseable {
    public static final String TAG = "AppJumpBlockTool";
    private List<String> alwaysAllowPackageNameList = new ArrayList();
    private List<AppJumpBlockTool.AppInfo> blockedAppList;
    private boolean isClickAllow;
    private int mCallingPid;
    private int mCallingUid;
    private int mUserId;
    private Bundle options;
    private int requestCode;
    private AppJumpBlockTool.AppInfo sourceAppInfo;
    private Intent[] targetIntents;

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
    }

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        super.onCreate(bundle);
        Log.i("AppJumpBlockTool", "AdInterceptDialogActivity:onCreate");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e("AppJumpBlockTool", "data can't be null");
            this.isClickAllow = true;
            sendAllowResult();
            finish();
            return;
        }
        this.mUserId = extras.getInt(SmLib_IafdConstant.KEY_USER_ID);
        this.mCallingPid = extras.getInt("callingPid");
        this.mCallingUid = extras.getInt("callingUid");
        this.targetIntents = (Intent[]) extras.getParcelableArray("targetIntents", Intent.class);
        this.requestCode = extras.getInt("requestCode", -1);
        this.options = (Bundle) extras.getParcelable("options");
        this.sourceAppInfo = (AppJumpBlockTool.AppInfo) extras.getParcelable("sourceAppInfo");
        List<AppJumpBlockTool.AppInfo> asList = Arrays.asList((AppJumpBlockTool.AppInfo[]) extras.getParcelableArray("blockedAppList", AppJumpBlockTool.AppInfo.class));
        this.blockedAppList = asList;
        if (asList.isEmpty()) {
            Log.i("AppJumpBlockTool", "blockedAppList:isEmpty");
            this.isClickAllow = true;
            sendAllowResult();
            finish();
            return;
        }
        showBlockDialog();
    }

    private final void showBlockDialog() {
        final CheckBox checkBox = new CheckBox(this);
        checkBox.setText(R.string.always_allow);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.blockedAppList.size(); i++) {
            sb.append(this.blockedAppList.get(i).appName);
            if (i < this.blockedAppList.size() - 1) {
                sb.append("、");
            }
        }
        checkBox.setTextColor(((TextView) new AlertDialog.Builder(this).setPositiveButton(R.string.app_block_button_open, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.AppBlockDialogActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                Log.i("AppJumpBlockTool", "onClickAllow,requestCode=" + AppBlockDialogActivity.this.requestCode);
                AppBlockDialogActivity.this.isClickAllow = true;
                if (checkBox.isChecked()) {
                    Iterator it = AppBlockDialogActivity.this.blockedAppList.iterator();
                    while (it.hasNext()) {
                        AppBlockDialogActivity.this.alwaysAllowPackageNameList.add(((AppJumpBlockTool.AppInfo) it.next()).packageName);
                    }
                    AppBlockDialogActivity appBlockDialogActivity = AppBlockDialogActivity.this;
                    AppJumpBlockTool.addAlwaysAllowList(appBlockDialogActivity, appBlockDialogActivity.sourceAppInfo.packageName, AppBlockDialogActivity.this.alwaysAllowPackageNameList);
                }
                try {
                    if (AppBlockDialogActivity.this.targetIntents.length > 1) {
                        Log.e("AppJumpBlockTool", "launch from Block Dialog for multiple intents!");
                        AppBlockDialogActivity appBlockDialogActivity2 = AppBlockDialogActivity.this;
                        appBlockDialogActivity2.startActivities(appBlockDialogActivity2.targetIntents, AppBlockDialogActivity.this.options);
                    } else {
                        Log.e("AppJumpBlockTool", "launch from Block Dialog for startActivity!, callingUserId=" + UserHandle.getUserId(AppBlockDialogActivity.this.mCallingUid) + ",mUserId=" + AppBlockDialogActivity.this.mUserId);
                        TargetInfo.prepareIntentForCrossProfileLaunch(AppBlockDialogActivity.this.targetIntents[0], AppBlockDialogActivity.this.mUserId);
                        Intent intent = AppBlockDialogActivity.this.targetIntents[0];
                        intent.addFlags(50331648);
                        if ((intent.getExtendedFlags() & 2) != 0) {
                            intent.removeExtendedFlags(2);
                            Log.e("AppJumpBlockTool", "removeExtendedFlags EXTENDED_FLAG_MISSING_CREATOR_OR_INVALID_TOKEN");
                        }
                        TargetInfo.refreshIntentCreatorToken(intent);
                        Log.e("AppJumpBlockTool", "refreshIntentCreatorToken");
                        try {
                            AppBlockDialogActivity appBlockDialogActivity3 = AppBlockDialogActivity.this;
                            appBlockDialogActivity3.startActivityAsCaller(intent, appBlockDialogActivity3.options, false, AppBlockDialogActivity.this.mUserId);
                        } catch (ActivityNotFoundException e) {
                            Log.e("AppJumpBlockTool", "launch fail! try launch by UserHandle.USER_OWNER", e);
                            AppBlockDialogActivity appBlockDialogActivity4 = AppBlockDialogActivity.this;
                            appBlockDialogActivity4.startActivityAsCaller(intent, appBlockDialogActivity4.options, false, 0);
                        }
                    }
                    AppBlockDialogActivity.this.sendAllowResult();
                } catch (Throwable th) {
                    Log.e("AppJumpBlockTool", "App jump block dialog launch fail!", th);
                    Toast.makeText(AppBlockDialogActivity.this, "Launch fail:" + th.getMessage(), 0).show();
                    AppBlockDialogActivity.this.sendResult(AppJumpBlockTool.RESULT_FAIL);
                }
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.core.AppBlockDialogActivity.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                Log.i("AppJumpBlockTool", "onDismiss");
                if (AppBlockDialogActivity.this.isClickAllow) {
                    return;
                }
                AppBlockDialogActivity.this.finish();
            }
        }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).setMessage(getString(R.string.app_block_content, this.sourceAppInfo.appName, sb.toString())).setView(checkBox).show().getWindow().findViewById(16908299)).getCurrentTextColor());
        int applyDimension = (int) TypedValue.applyDimension(1, 17.0f, getResources().getDisplayMetrics());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) checkBox.getLayoutParams();
        layoutParams.setMargins(applyDimension, 0, 0, 0);
        checkBox.setLayoutParams(layoutParams);
    }

    private final void sendCancelResult() {
        sendResult(AppJumpBlockTool.RESULT_CANCEL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendAllowResult() {
        sendResult(AppJumpBlockTool.RESULT_ALLOW);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResult(String str) {
        Intent intent = new Intent(AppJumpBlockTool.BROADCAST_ACTION);
        intent.putExtra("reason", str);
        intent.setPackage(this.sourceAppInfo.packageName);
        intent.putExtras(getIntent().getExtras());
        Log.i("AppJumpBlockTool", "send Broadcast,reason:" + str);
        sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(this.mCallingUid));
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.i("AppJumpBlockTool", "onConfigurationChanged");
        finish();
    }

    private void delayFinish() {
        getWindow().getDecorView().postDelayed(new Runnable() { // from class: com.samsung.android.core.AppBlockDialogActivity.3
            @Override // java.lang.Runnable
            public void run() {
                AppBlockDialogActivity.this.finish();
            }
        }, 200L);
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        Log.i("AppJumpBlockTool", "AppBlockDialogActivity=>onPause");
        if (!this.isClickAllow) {
            Log.i("AppJumpBlockTool", "onDismiss");
            sendCancelResult();
        }
        finish();
    }
}
