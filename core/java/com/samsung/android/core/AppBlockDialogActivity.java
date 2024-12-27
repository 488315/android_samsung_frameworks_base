package com.samsung.android.core;

import android.app.AlertDialog;
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

import com.android.internal.R;
import com.android.internal.app.AlertActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppBlockDialogActivity extends AlertActivity implements AutoCloseable {
    public static final String TAG = "AppJumpBlockTool";
    private List<String> alwaysAllowPackageNameList = new ArrayList();
    private List<AppJumpBlockTool.AppInfo> blockedAppList;
    private boolean isClickAllow;
    private int mCallingPid;
    private int mCallingUid;
    private Bundle options;
    private int requestCode;
    private AppJumpBlockTool.AppInfo sourceAppInfo;
    private List<Intent> targetIntents;

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        super.onCreate(savedInstanceState);
        Log.i("AppJumpBlockTool", "AdInterceptDialogActivity:onCreate");
        Bundle data = getIntent().getExtras();
        if (data == null) {
            Log.e("AppJumpBlockTool", "data can't be null");
            this.isClickAllow = true;
            sendAllowResult();
            finish();
            return;
        }
        this.mCallingPid = data.getInt("callingPid");
        this.mCallingUid = data.getInt("callingUid");
        this.targetIntents =
                Arrays.asList((Intent[]) data.getParcelableArray("targetIntents", Intent.class));
        this.requestCode = data.getInt("requestCode", -1);
        this.options = (Bundle) data.getParcelable("options");
        this.sourceAppInfo = (AppJumpBlockTool.AppInfo) data.getParcelable("sourceAppInfo");
        this.blockedAppList =
                Arrays.asList(
                        (AppJumpBlockTool.AppInfo[])
                                data.getParcelableArray(
                                        "blockedAppList", AppJumpBlockTool.AppInfo.class));
        if (this.blockedAppList.isEmpty()) {
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
        StringBuilder targetAppNames = new StringBuilder();
        for (int i = 0; i < this.blockedAppList.size(); i++) {
            AppJumpBlockTool.AppInfo appInfo = this.blockedAppList.get(i);
            targetAppNames.append(appInfo.appName);
            if (i < this.blockedAppList.size() - 1) {
                targetAppNames.append("、");
            }
        }
        String message =
                getString(
                        R.string.app_block_content,
                        this.sourceAppInfo.appName,
                        targetAppNames.toString());
        AlertDialog alertDialog =
                new AlertDialog.Builder(this)
                        .setPositiveButton(
                                R.string.app_block_button_open,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.core.AppBlockDialogActivity.2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("AppJumpBlockTool", "onClickAllow");
                                        AppBlockDialogActivity.this.isClickAllow = true;
                                        if (checkBox.isChecked()) {
                                            for (AppJumpBlockTool.AppInfo appInfo2 :
                                                    AppBlockDialogActivity.this.blockedAppList) {
                                                AppBlockDialogActivity.this
                                                        .alwaysAllowPackageNameList.add(
                                                        appInfo2.packageName);
                                            }
                                        }
                                        AppBlockDialogActivity.this.sendAllowResult();
                                    }
                                })
                        .setOnDismissListener(
                                new DialogInterface
                                        .OnDismissListener() { // from class:
                                                               // com.samsung.android.core.AppBlockDialogActivity.1
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public void onDismiss(DialogInterface dialog) {
                                        Log.i("AppJumpBlockTool", "onDismiss");
                                        if (!AppBlockDialogActivity.this.isClickAllow) {
                                            AppBlockDialogActivity.this.finish();
                                        }
                                    }
                                })
                        .setNegativeButton(17039360, (DialogInterface.OnClickListener) null)
                        .setMessage(message)
                        .setView(checkBox)
                        .show();
        TextView messageView = (TextView) alertDialog.getWindow().findViewById(16908299);
        checkBox.setTextColor(messageView.getCurrentTextColor());
        int paddingLeft =
                (int) TypedValue.applyDimension(1, 17.0f, getResources().getDisplayMetrics());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) checkBox.getLayoutParams();
        params.setMargins(paddingLeft, 0, 0, 0);
        checkBox.setLayoutParams(params);
    }

    private final void sendCancelResult() {
        sendResult(AppJumpBlockTool.BlockDialogReceiver.RESULT_CANCEL);
    }

    public final void sendAllowResult() {
        AppJumpBlockTool.addAlwaysAllowList(
                this, this.sourceAppInfo.packageName, this.alwaysAllowPackageNameList);
        sendResult(AppJumpBlockTool.BlockDialogReceiver.RESULT_ALLOW);
    }

    private void sendResult(String reason) {
        Intent intent = new Intent(AppJumpBlockTool.BlockDialogReceiver.BROADCAST_ACTION);
        intent.putExtra("reason", reason);
        intent.setPackage(this.sourceAppInfo.packageName);
        Bundle data = getIntent().getExtras();
        intent.putExtras(data);
        Log.i("AppJumpBlockTool", "send Broadcast,reason:" + reason);
        UserHandle userHandle = UserHandle.getUserHandleForUid(this.mCallingUid);
        sendBroadcastAsUser(intent, userHandle);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("AppJumpBlockTool", "onConfigurationChanged");
        finish();
    }

    private void delayFinish() {
        getWindow()
                .getDecorView()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.core.AppBlockDialogActivity.3
                            @Override // java.lang.Runnable
                            public void run() {
                                AppBlockDialogActivity.this.finish();
                            }
                        },
                        200L);
    }

    @Override // android.app.Activity
    public void onPause() {
        Log.i("AppJumpBlockTool", "AppBlockDialogActivity=>onPause");
        if (!this.isClickAllow) {
            Log.i("AppJumpBlockTool", "onDismiss");
            sendCancelResult();
            finish();
        } else {
            delayFinish();
        }
        super.onPause();
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {}
}
