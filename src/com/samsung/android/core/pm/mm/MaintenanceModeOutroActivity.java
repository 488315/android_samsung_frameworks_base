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

/* loaded from: classes6.dex */
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

    static /* synthetic */ void lambda$showDialog$5(DialogInterface dialogInterface, int i) {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        this.mResources = applicationContext.getResources();
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

    private void setContentView(Configuration configuration) {
        ActionBar actionBar;
        MaintenanceModeUtils.configureLayout(this, this.mResources, configuration, this.mIsTablet, this.mIsFold, R.layout.activity_maintenance_mode_outro, R.layout.activity_maintenance_mode_outro_land, R.id.maintenance_mode_outro_body_container);
        if (this.mCallingPackage != null && (actionBar = getActionBar()) != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (this.mIsTablet) {
            ((ImageView) findViewById(R.id.maintenance_mode_outro_imageview)).setMaxWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_image_max_width_tablet));
            ((TextView) findViewById(R.id.maintenance_mode_outro_textview)).setText(R.string.maintenance_mode_outro_textview_message_tablet);
        }
        Button button = (Button) findViewById(R.id.maintenance_mode_outro_exit_button);
        this.mExitButton = button;
        if (this.mIsTablet) {
            button.setWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_body_button_width_tablet));
        }
        this.mExitButton.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_button_text_size));
        this.mExitButton.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeOutroActivity.this.lambda$setContentView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        showDialog();
    }

    private void init() {
        this.mRootView = getWindow().getDecorView();
        this.mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        prepareProgressView();
    }

    private void prepareProgressView() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 131328, -3);
        this.mViewWindowParams = layoutParams;
        layoutParams.gravity = 17;
        this.mViewWindowParams.privateFlags |= 16;
        this.mViewWindowParams.screenOrientation = 1;
        this.mViewWindowParams.layoutInDisplayCutoutMode = 1;
        this.mViewWindowParams.setFitInsetsSides(0);
        this.mProgressView = LayoutInflater.from(this).inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != null && menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setContentView(configuration);
    }

    private void showDialog() {
        AlertDialog create = new AlertDialog.Builder(this).setMessage(this.mIsTablet ? R.string.maintenance_mode_outro_dialog_message_tablet : R.string.maintenance_mode_outro_dialog_message_phone).setPositiveButton(R.string.maintenance_mode_dialog_button_text_restart, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeOutroActivity.this.lambda$showDialog$4(dialogInterface, i);
            }
        }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeOutroActivity.lambda$showDialog$5(dialogInterface, i);
            }
        }).create();
        create.getWindow().setGravity(80);
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialog$4(DialogInterface dialogInterface, int i) {
        MaintenanceModeUtils.confirmSecureLock(this, new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeOutroActivity.this.lambda$showDialog$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialog$3() {
        this.mExitButton.setClickable(false);
        this.mWm.addView(this.mProgressView, this.mViewWindowParams);
        new Thread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeOutroActivity.this.lambda$showDialog$1();
            }
        }).start();
        this.mRootView.postDelayed(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeOutroActivity.this.lambda$showDialog$2();
            }
        }, 120000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialog$2() {
        this.mWm.removeView(this.mProgressView);
        this.mExitButton.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: exitMaintenanceMode, reason: merged with bridge method [inline-methods] */
    public void lambda$showDialog$1() {
        try {
            ((UserManager) this.mContext.getSystemService("user")).removeUser(77);
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Exception", e);
        }
    }
}
