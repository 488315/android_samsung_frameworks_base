package com.samsung.android.core.pm.mm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.R;

public class MaintenanceModeOutroActivity extends Activity {
    private static final long EXIT_PROGRESS_TIMEOUT = 120000;
    private static final String TAG = "MaintenanceMode";
    private String mCallingPackage;
    private Context mContext;
    private Button mExitButton;
    private View mProgressView;
    private Resources mResources;
    private View mRootView;
    private WindowManager.LayoutParams mViewWindowParams;
    private WindowManager mWm;
    private boolean mIsTablet = false;
    private boolean mIsFold = false;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        this.mResources = this.mContext.getResources();
        this.mCallingPackage = getCallingPackage();
        if (ActivityManager.getCurrentUser() != 77) {
            finish();
            return;
        }
        this.mIsTablet = MaintenanceModeUtils.isTablet();
        this.mIsFold = MaintenanceModeUtils.isFold();
        setContentView(getResources().getConfiguration());
        init();
    }

    private void setContentView(Configuration config) {
        ActionBar actionBar;
        MaintenanceModeUtils.configureLayout(
                this,
                this.mResources,
                config,
                this.mIsTablet,
                this.mIsFold,
                R.layout.activity_maintenance_mode_outro,
                R.layout.activity_maintenance_mode_outro_land,
                R.id.maintenance_mode_outro_body_container);
        if (this.mCallingPackage != null && (actionBar = getActionBar()) != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (this.mIsTablet) {
            ImageView imageView = (ImageView) findViewById(R.id.maintenance_mode_outro_imageview);
            imageView.setMaxWidth(
                    this.mResources.getDimensionPixelSize(
                            R.dimen.maintenance_mode_image_max_width_tablet));
            TextView outroTextView = (TextView) findViewById(R.id.maintenance_mode_outro_textview);
            outroTextView.setText(R.string.maintenance_mode_outro_textview_message_tablet);
        }
        this.mExitButton = (Button) findViewById(R.id.maintenance_mode_outro_exit_button);
        if (this.mIsTablet) {
            this.mExitButton.setWidth(
                    this.mResources.getDimensionPixelSize(
                            R.dimen.maintenance_mode_body_button_width_tablet));
        }
        this.mExitButton.setTextSize(
                0,
                MaintenanceModeUtils.getFontSize(
                        this.mContext, R.dimen.maintenance_mode_common_button_text_size));
        this.mExitButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MaintenanceModeOutroActivity.this.lambda$setContentView$0(view);
                    }
                });
    }

    public /* synthetic */ void lambda$setContentView$0(View v) {
        showDialog();
    }

    private void init() {
        this.mRootView = getWindow().getDecorView();
        this.mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        prepareProgressView();
    }

    private void prepareProgressView() {
        this.mViewWindowParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 131328, -3);
        this.mViewWindowParams.gravity = 17;
        this.mViewWindowParams.privateFlags |= 16;
        this.mViewWindowParams.screenOrientation = 1;
        this.mViewWindowParams.layoutInDisplayCutoutMode = 1;
        this.mViewWindowParams.setFitInsetsSides(0);
        this.mProgressView =
                LayoutInflater.from(this)
                        .inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(newConfig);
    }

    private void showDialog() {
        int i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (this.mIsTablet) {
            i = R.string.maintenance_mode_outro_dialog_message_tablet;
        } else {
            i = R.string.maintenance_mode_outro_dialog_message_phone;
        }
        AlertDialog.Builder builder2 =
                builder.setMessage(i)
                        .setPositiveButton(
                                R.string.maintenance_mode_dialog_button_text_restart,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda0
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        MaintenanceModeOutroActivity.this.lambda$showDialog$4(
                                                dialogInterface, i2);
                                    }
                                })
                        .setNegativeButton(
                                R.string.maintenance_mode_dialog_button_text_cancel,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        MaintenanceModeOutroActivity.lambda$showDialog$5(
                                                dialogInterface, i2);
                                    }
                                });
        AlertDialog dialog = builder2.create();
        dialog.getWindow().setGravity(80);
        dialog.show();
    }

    public /* synthetic */ void lambda$showDialog$4(DialogInterface dialog, int which) {
        Runnable runningJob =
                new Runnable() { // from class:
                                 // com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MaintenanceModeOutroActivity.this.lambda$showDialog$3();
                    }
                };
        MaintenanceModeUtils.confirmSecureLock(this, runningJob);
    }

    public /* synthetic */ void lambda$showDialog$3() {
        this.mExitButton.setClickable(false);
        this.mWm.addView(this.mProgressView, this.mViewWindowParams);
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                MaintenanceModeOutroActivity.this.lambda$showDialog$1();
                            }
                        })
                .start();
        this.mRootView.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        MaintenanceModeOutroActivity.this.lambda$showDialog$2();
                    }
                },
                120000L);
    }

    public /* synthetic */ void lambda$showDialog$2() {
        this.mWm.removeView(this.mProgressView);
        this.mExitButton.setClickable(true);
    }

    static /* synthetic */ void lambda$showDialog$5(DialogInterface dialog, int which) {}

    /* renamed from: exitMaintenanceMode, reason: merged with bridge method [inline-methods] */
    public void lambda$showDialog$1() {
        try {
            UserManager um = (UserManager) this.mContext.getSystemService("user");
            um.removeUser(77);
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Exception", e);
        }
    }
}
