package com.samsung.android.core.pm.mm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes6.dex */
public class MaintenanceModeIntroActivity extends Activity {
    private static final long DUMP_CHECK_DELAY = 1000;
    private static final long DUMP_CHECK_INITIAL_DELAY = 10000;
    private static final long DUMP_CHECK_TIMEOUT = 300000;
    private static final String TAG = "MaintenanceMode";
    private TimerTask mCloudBackupTimerTask;
    private View mColudBackupMenu;
    private View mColudBackupMenuDivider;
    private TextView mColudBackupMenuSubTextView;
    private Context mContext;
    private View mDialogView;
    private long mDumpEndTime;
    private View mDumpView;
    private View mExternalStorageBackupMenu;
    private Resources mResources;
    private View mRootView;
    private Button mTurnOnButton;
    private WindowManager.LayoutParams mViewWindowParams;
    private View mWaitingView;
    private WindowManager mWm;
    private boolean mIsTablet = false;
    private boolean mIsFold = false;
    private boolean mIsCloudBackupSupported = false;
    private int mCloudBackupRetentionPeriod = 30;
    private String mCloudBackupIntroDescription = null;
    private String mCloudBackupStatus = KeyProperties.DIGEST_NONE;
    private final ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService mLoggingExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService mButtonExecutor = Executors.newSingleThreadExecutor();
    private final BroadcastReceiver mCloudBackupReceiver = new CloudBackupReceiver();
    private final Timer mTimer = new Timer();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        this.mResources = this.mContext.getResources();
        if (MaintenanceModeUtils.checkRequiredConditions(this.mContext, true) != 0) {
            finish();
            return;
        }
        this.mIsTablet = MaintenanceModeUtils.isTablet();
        this.mIsFold = MaintenanceModeUtils.isFold();
        setContentView(getResources().getConfiguration());
        init();
    }

    private void setContentView(Configuration config) {
        MaintenanceModeUtils.configureLayout(this, this.mResources, config, this.mIsTablet, this.mIsFold, R.layout.activity_maintenance_mode_intro, R.layout.activity_maintenance_mode_intro_land, R.id.maintenance_mode_intro_body_container);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (this.mIsTablet) {
            ImageView imageView = (ImageView) findViewById(R.id.maintenance_mode_intro_imageview);
            imageView.setMaxWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_image_max_width_tablet));
            TextView summaryTextView = (TextView) findViewById(R.id.maintenance_mode_intro_summary_textview);
            String summaryTextViewMessage = this.mResources.getString(R.string.maintenance_mode_intro_summary_textview_message_tablet);
            summaryTextView.lambda$setTextAsync$0(summaryTextViewMessage);
            TextView descriptionNeedToUnlockTextView = (TextView) findViewById(R.id.maintenance_mode_intro_description_need_to_unlock_textview);
            String descriptionNeedToUnlockTextViewMessage = this.mResources.getString(R.string.maintenance_mode_intro_description_need_to_unlock_tablet);
            descriptionNeedToUnlockTextView.lambda$setTextAsync$0(descriptionNeedToUnlockTextViewMessage);
            TextView recommendationTextView = (TextView) findViewById(R.id.maintenance_mode_intro_recommendation_textview);
            String recommendationTextViewMessage = this.mResources.getString(R.string.maintenance_mode_intro_recommendation_textview_message_tablet);
            recommendationTextView.lambda$setTextAsync$0(recommendationTextViewMessage);
        }
        this.mColudBackupMenu = findViewById(R.id.maintenance_mode_intro_backup_menu_cloud);
        this.mColudBackupMenu.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_top);
        this.mColudBackupMenu.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeIntroActivity.this.lambda$setContentView$2(view);
            }
        });
        TextView cloudBackupMenuMainTextView = (TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_cloud_main_textview);
        cloudBackupMenuMainTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_main_text_size));
        this.mColudBackupMenuSubTextView = (TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_cloud_sub_textview);
        this.mColudBackupMenuSubTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_sub_text_size));
        this.mColudBackupMenuDivider = findViewById(R.id.maintenance_mode_intro_backup_menu_divider_cloud);
        this.mExternalStorageBackupMenu = findViewById(R.id.maintenance_mode_intro_backup_menu_external_storage);
        this.mExternalStorageBackupMenu.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_alone);
        this.mExternalStorageBackupMenu.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeIntroActivity.this.lambda$setContentView$4(view);
            }
        });
        TextView externalStorageBackupMenuMainTextView = (TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_external_storage_main_textview);
        externalStorageBackupMenuMainTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_main_text_size));
        TextView externalStorageBackupMenuSubTextView = (TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_external_storage_sub_textview);
        externalStorageBackupMenuSubTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_sub_text_size));
        updateCloudBackupMenuSubText();
        updateCloudBackupMenuVisibility();
        this.mTurnOnButton = (Button) findViewById(R.id.maintenance_mode_intro_turnon_button);
        if (this.mIsTablet) {
            this.mTurnOnButton.setWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_body_button_width_tablet));
        }
        this.mTurnOnButton.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_button_text_size));
        this.mTurnOnButton.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeIntroActivity.this.lambda$setContentView$8(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$2(View v) {
        this.mButtonExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$setContentView$0();
            }
        });
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$setContentView$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0() {
        MaintenanceModeUtils.startCloudActivity(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7083", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$4(View v) {
        MaintenanceModeUtils.startSmartSwitchActivity(this.mContext);
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$setContentView$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$3() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7074", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$8(View v) {
        if (!MaintenanceModeUtils.isSecureLockSet(this.mContext)) {
            showDialogToInformSecureLockIsNeeded();
        } else {
            if (MaintenanceModeUtils.isLowOnStorage(this.mContext)) {
                showDialogToNotifyLowOnStorage();
                return;
            }
            this.mTurnOnButton.setClickable(false);
            this.mButtonExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$setContentView$6();
                }
            });
            this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$setContentView$7();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$6() {
        final String backupStatus = MaintenanceModeUtils.getStatusOfBackupInProgress(this.mContext);
        runOnUiThread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$setContentView$5(backupStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$5(String backupStatus) {
        if ("NOT_IN_PROGRESS".equals(backupStatus)) {
            showDialogToConfirmRestart();
        } else {
            showDialogToReconfirmCancelingBackup(backupStatus);
        }
        this.mTurnOnButton.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$7() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7066", null);
    }

    private void init() {
        this.mRootView = getWindow().getDecorView();
        this.mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        registerCloudBackupReceiver();
        checkAndUpdateCloudBackupMenu();
        prepareWaitingView();
    }

    private void prepareWaitingView() {
        int i;
        int i2;
        this.mViewWindowParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 131328, -3);
        this.mViewWindowParams.gravity = 17;
        this.mViewWindowParams.privateFlags |= 16;
        this.mViewWindowParams.screenOrientation = 1;
        this.mViewWindowParams.layoutInDisplayCutoutMode = 1;
        this.mViewWindowParams.setFitInsetsSides(0);
        this.mWaitingView = LayoutInflater.from(this).inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
        this.mWaitingView.findViewById(R.id.maintenance_mode_view_dump_progressbar_container).setVisibility(8);
        TextView waitingTextView = (TextView) this.mWaitingView.findViewById(R.id.maintenance_mode_view_dump_textview);
        Resources resources = this.mResources;
        if (this.mIsTablet) {
            i = R.string.maintenance_mode_view_waiting_textview_message_tablet;
        } else {
            i = R.string.maintenance_mode_view_waiting_textview_message_phone;
        }
        String waitingTextViewMessage = resources.getString(i);
        waitingTextView.lambda$setTextAsync$0(waitingTextViewMessage);
        waitingTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_text_size));
        this.mDumpView = LayoutInflater.from(this).inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
        TextView dumpTextView = (TextView) this.mDumpView.findViewById(R.id.maintenance_mode_view_dump_textview);
        StringBuilder append = new StringBuilder().append(this.mResources.getString(R.string.maintenance_mode_view_dump_textview_message_creating)).append("\n\n");
        Resources resources2 = this.mResources;
        if (this.mIsTablet) {
            i2 = R.string.maintenance_mode_view_dump_textview_message_tablet;
        } else {
            i2 = R.string.maintenance_mode_view_dump_textview_message_phone;
        }
        String dumpTextViewMessage = append.append(resources2.getString(i2)).toString();
        dumpTextView.lambda$setTextAsync$0(dumpTextViewMessage);
        dumpTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_text_size));
        if (this.mIsTablet) {
            int padding = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_body_padding_left_right_tablet);
            View waitingViewContainer = this.mWaitingView.findViewById(R.id.maintenance_mode_view_dump_container);
            waitingViewContainer.setPadding(padding, 0, padding, 0);
            View dumpViewContainer = this.mDumpView.findViewById(R.id.maintenance_mode_view_dump_container);
            dumpViewContainer.setPadding(padding, 0, padding, 0);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        boolean isFinished = false;
        if (MaintenanceModeUtils.checkRequiredConditions(this.mContext, false) != 0) {
            finish();
            isFinished = true;
        }
        super.onResume();
        if (isFinished) {
            return;
        }
        checkAndUpdateCloudBackupMenu();
        this.mCloudBackupTimerTask = new AnonymousClass1();
        this.mTimer.schedule(this.mCloudBackupTimerTask, 30000L, 30000L);
    }

    /* renamed from: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$1, reason: invalid class name */
    class AnonymousClass1 extends TimerTask {
        AnonymousClass1() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (MaintenanceModeIntroActivity.this.mIsCloudBackupSupported) {
                try {
                    MaintenanceModeIntroActivity.this.mSingleThreadExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MaintenanceModeIntroActivity.AnonymousClass1.this.lambda$run$0();
                        }
                    });
                } catch (Exception e) {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            MaintenanceModeIntroActivity.this.updateCloudBackupStatusFromProvider();
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        if (this.mCloudBackupTimerTask != null) {
            this.mCloudBackupTimerTask.cancel();
        }
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            unregisterReceiver(this.mCloudBackupReceiver);
        } catch (Exception e) {
        }
        this.mTimer.cancel();
        this.mSingleThreadExecutor.shutdownNow();
        this.mButtonExecutor.shutdownNow();
        this.mLoggingExecutor.shutdown();
        super.onDestroy();
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
        adjustDialogLayout(newConfig);
    }

    private void checkAndUpdateCloudBackupMenu() {
        this.mSingleThreadExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$checkAndUpdateCloudBackupMenu$10();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAndUpdateCloudBackupMenu$10() {
        updateCloudBackupStatusFromProvider();
        MaintenanceModeUtils.CloudInfo cloudInfo = MaintenanceModeUtils.checkCloudBackupSupport(this.mContext);
        this.mIsCloudBackupSupported = cloudInfo.isSupported;
        this.mCloudBackupRetentionPeriod = cloudInfo.retentionPeriod;
        this.mCloudBackupIntroDescription = cloudInfo.introDescription;
        runOnUiThread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$checkAndUpdateCloudBackupMenu$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAndUpdateCloudBackupMenu$9() {
        updateCloudBackupMenuSubText();
        updateCloudBackupMenuVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCloudBackupStatusFromProvider() {
        this.mCloudBackupStatus = MaintenanceModeUtils.getCloudBackupStatus(this.mContext);
        runOnUiThread(new MaintenanceModeIntroActivity$$ExternalSyntheticLambda23(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCloudBackupStatusFromReceiver(String action) {
        this.mCloudBackupStatus = convertActionToStatusForCloudBackup(action);
        runOnUiThread(new MaintenanceModeIntroActivity$$ExternalSyntheticLambda23(this));
    }

    private void updateCloudBackupMenuVisibility() {
        if (this.mColudBackupMenu == null || this.mColudBackupMenuDivider == null || this.mExternalStorageBackupMenu == null) {
            return;
        }
        if (this.mIsCloudBackupSupported) {
            this.mColudBackupMenu.setVisibility(0);
            this.mColudBackupMenuDivider.setVisibility(0);
            this.mExternalStorageBackupMenu.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_bottom);
        } else {
            this.mColudBackupMenu.setVisibility(8);
            this.mColudBackupMenuDivider.setVisibility(8);
            this.mExternalStorageBackupMenu.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_alone);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateCloudBackupMenuSubText() {
        char c;
        if (this.mColudBackupMenuSubTextView == null) {
        }
        String str = this.mCloudBackupStatus;
        switch (str.hashCode()) {
            case -739684063:
                if (str.equals("BACKUP_NON_FINISHED")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -226439134:
                if (str.equals("BACKUP_RUNNING")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 82681838:
                if (str.equals("BACKUP_COMPLETED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_backup_cloud_menu_sub_textview_message_backing_up));
                break;
            case 1:
                this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_backup_cloud_menu_sub_textview_message_backed_up_succeeded));
                break;
            case 2:
                this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_backup_cloud_menu_sub_textview_message_backed_up_failed));
                break;
            default:
                if (!TextUtils.isEmpty(this.mCloudBackupIntroDescription)) {
                    this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mCloudBackupIntroDescription);
                    break;
                } else {
                    this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getQuantityString(R.plurals.maintenance_mode_backup_cloud_menu_sub_textview_message_default, this.mCloudBackupRetentionPeriod, Integer.valueOf(this.mCloudBackupRetentionPeriod)));
                    break;
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CloudBackupReceiver extends BroadcastReceiver {
        private CloudBackupReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null) {
                try {
                    MaintenanceModeIntroActivity.this.mSingleThreadExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$CloudBackupReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MaintenanceModeIntroActivity.CloudBackupReceiver.this.lambda$onReceive$0(action);
                        }
                    });
                } catch (Exception e) {
                }
            }
            Log.i("MaintenanceMode", "onReceive: " + action);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(String action) {
            MaintenanceModeIntroActivity.this.updateCloudBackupStatusFromReceiver(action);
        }
    }

    private void registerCloudBackupReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_STARTED");
        intentFilter.addAction("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_COMPLETED");
        intentFilter.addAction("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_NOT_FINISHED");
        intentFilter.addAction("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_CANCELED");
        registerReceiver(this.mCloudBackupReceiver, intentFilter, "com.samsung.android.permission.ACCESS_MAINTENANCE_MODE", null, 2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String convertActionToStatusForCloudBackup(String action) {
        char c;
        switch (action.hashCode()) {
            case 365973607:
                if (action.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_CANCELED")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 750993107:
                if (action.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_STARTED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 875734045:
                if (action.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_COMPLETED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1579405644:
                if (action.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_NOT_FINISHED")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "BACKUP_RUNNING";
            case 1:
                return "BACKUP_COMPLETED";
            case 2:
                return "BACKUP_NON_FINISHED";
            default:
                return KeyProperties.DIGEST_NONE;
        }
    }

    private void showDialogToInformSecureLockIsNeeded() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(R.string.maintenance_mode_intro_dialog_message_set_screen_lock_first).setPositiveButton(R.string.maintenance_mode_intro_dialog_button_text_set_screen_lock, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToInformSecureLockIsNeeded$11(dialogInterface, i);
            }
        }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.lambda$showDialogToInformSecureLockIsNeeded$12(dialogInterface, i);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(80);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToInformSecureLockIsNeeded$11(DialogInterface dialog, int which) {
        MaintenanceModeUtils.startActivityToSetSecureLock(this);
    }

    static /* synthetic */ void lambda$showDialogToInformSecureLockIsNeeded$12(DialogInterface dialog, int which) {
    }

    private void showDialogToNotifyLowOnStorage() {
        int i;
        Log.i("MaintenanceMode", "Low on storage");
        AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle(R.string.maintenance_mode_intro_low_storage_dialog_title);
        if (this.mIsTablet) {
            i = R.string.maintenance_mode_intro_low_storage_dialog_message_tablet;
        } else {
            i = R.string.maintenance_mode_intro_low_storage_dialog_message_phone;
        }
        AlertDialog.Builder builder = title.setMessage(i).setPositiveButton(R.string.maintenance_mode_intro_low_storage_dialog_button_text, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToNotifyLowOnStorage$13(dialogInterface, i2);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(80);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToNotifyLowOnStorage$13(DialogInterface dialog, int which) {
        MaintenanceModeUtils.startMyFilesActivity(this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void showDialogToReconfirmCancelingBackup(String backupStatus) {
        char c;
        int messageResId = R.string.maintenance_mode_stop_backup_dialog_message;
        boolean isCloudBackupInProgress = false;
        if (backupStatus != null) {
            switch (backupStatus.hashCode()) {
                case -1070966578:
                    if (backupStatus.equals("RESTORE_RUNNING")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -739684063:
                    if (backupStatus.equals("BACKUP_NON_FINISHED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -226439134:
                    if (backupStatus.equals("BACKUP_RUNNING")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    messageResId = R.string.maintenance_mode_stop_backup_dialog_message_backing_up;
                    isCloudBackupInProgress = true;
                    break;
                case 1:
                    messageResId = R.string.maintenance_mode_stop_backup_dialog_message_backed_up_failed;
                    isCloudBackupInProgress = true;
                    break;
                case 2:
                    messageResId = R.string.maintenance_mode_stop_backup_dialog_message_restoring;
                    isCloudBackupInProgress = true;
                    break;
            }
        }
        final boolean needToLaunchCloudApp = isCloudBackupInProgress;
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(messageResId).setPositiveButton(R.string.maintenance_mode_dialog_button_text_ok, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$16(needToLaunchCloudApp, dialogInterface, i);
            }
        }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda19
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$18(dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$20(dialogInterface);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(80);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$16(boolean needToLaunchCloudApp, DialogInterface dialog, int which) {
        if (needToLaunchCloudApp) {
            this.mButtonExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$14();
                }
            });
        } else {
            showDialogToConfirmRestart();
        }
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$15();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$14() {
        MaintenanceModeUtils.startCloudActivity(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$15() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7068", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$17() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7069", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$18(DialogInterface dialog, int which) {
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$17();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$19() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7069", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$20(DialogInterface dialog) {
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$19();
            }
        });
    }

    private void showDialogToConfirmRestart() {
        int i;
        int i2;
        this.mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_maintenance_mode_intro, (ViewGroup) null);
        adjustDialogLayout(getResources().getConfiguration());
        final CheckedTextView checkedTextView = (CheckedTextView) this.mDialogView.findViewById(R.id.maintenance_mode_intro_dialog_checked_textview);
        checkedTextView.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CheckedTextView.this.toggle();
            }
        });
        checkedTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_checkbox_text_size, 1.1f));
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(this.mDialogView).setPositiveButton(R.string.maintenance_mode_dialog_button_text_restart, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$27(checkedTextView, dialogInterface, i3);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(80);
        TextView dialogTextView = (TextView) this.mDialogView.findViewById(R.id.maintenance_mode_intro_dialog_textview);
        StringBuilder sb = new StringBuilder();
        Resources resources = this.mResources;
        if (this.mIsTablet) {
            i = R.string.maintenance_mode_intro_dialog_message_tablet;
        } else {
            i = R.string.maintenance_mode_intro_dialog_message_phone;
        }
        StringBuilder append = sb.append(resources.getString(i)).append("\n\n");
        Resources resources2 = this.mResources;
        if (this.mIsTablet) {
            i2 = R.string.maintenance_mode_intro_dialog_textview_message_tablet;
        } else {
            i2 = R.string.maintenance_mode_intro_dialog_textview_message_phone;
        }
        String textViewMessage = append.append(resources2.getString(i2)).toString();
        dialogTextView.lambda$setTextAsync$0(textViewMessage);
        dialogTextView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_text_size));
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$27(CheckedTextView checkedTextView, DialogInterface dialog, int which) {
        if (!MaintenanceModeUtils.isSecureLockSet(this.mContext)) {
            showDialogToInformSecureLockIsNeeded();
            return;
        }
        final boolean skipDump = !checkedTextView.isChecked();
        Runnable runningJob = new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$26(skipDump);
            }
        };
        MaintenanceModeUtils.confirmSecureLock(this, runningJob);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$26(final boolean skipDump) {
        this.mTurnOnButton.setClickable(false);
        if (this.mIsTablet) {
            setWaitingViewRotation();
        }
        MaintenanceModeUtils.setUserConsentAboutCreatingLog(!skipDump);
        if (skipDump) {
            this.mWm.addView(this.mWaitingView, this.mViewWindowParams);
            new Thread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$23();
                }
            }).start();
        } else {
            this.mWm.addView(this.mDumpView, this.mViewWindowParams);
            triggerDump();
        }
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$24(skipDump);
            }
        });
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$25();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$23() {
        UserInfo userInfo = enterMaintenanceMode();
        if (userInfo == null) {
            this.mWm.removeView(this.mWaitingView);
            runOnUiThread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$22();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$22() {
        if (MaintenanceModeUtils.isLowOnStorage(this.mContext)) {
            showDialogToNotifyLowOnStorage();
        }
        this.mTurnOnButton.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$24(boolean skipDump) {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7070", skipDump ? "1" : "0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$25() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7071", null);
    }

    private void setWaitingViewRotation() {
        Display display = getDisplay();
        if (display != null) {
            int rotation = display.getRotation();
            switch (rotation) {
                case 0:
                    this.mViewWindowParams.screenOrientation = 1;
                    break;
                case 1:
                    this.mViewWindowParams.screenOrientation = 0;
                    break;
                case 2:
                    this.mViewWindowParams.screenOrientation = 9;
                    break;
                case 3:
                    this.mViewWindowParams.screenOrientation = 8;
                    break;
            }
        }
    }

    private void adjustDialogLayout(Configuration config) {
        if (this.mIsTablet) {
            return;
        }
        if ((!this.mIsFold || config.semDisplayDeviceType == 5) && this.mDialogView != null) {
            ScrollView dialogScrollView = (ScrollView) this.mDialogView.findViewById(R.id.maintenance_mode_intro_dialog_scrollview);
            ViewGroup.MarginLayoutParams dialogScrollViewLayoutParams = (ViewGroup.MarginLayoutParams) dialogScrollView.getLayoutParams();
            View checkBoxLayout = this.mDialogView.findViewById(R.id.maintenance_mode_intro_dialog_checkbox_layout);
            ViewGroup.MarginLayoutParams checkBoxLayoutParams = (ViewGroup.MarginLayoutParams) checkBoxLayout.getLayoutParams();
            if (config.orientation == 2) {
                dialogScrollViewLayoutParams.topMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_margin_top_land);
                int checkBoxPadding = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_padding_top_bottom_land);
                checkBoxLayout.setPadding(0, checkBoxPadding, 0, checkBoxPadding);
                checkBoxLayoutParams.bottomMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_margin_bottom_land);
            } else {
                dialogScrollViewLayoutParams.topMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_margin_top);
                int checkBoxPadding2 = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_padding_top_bottom);
                checkBoxLayout.setPadding(0, checkBoxPadding2, 0, checkBoxPadding2);
                checkBoxLayoutParams.bottomMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_margin_bottom);
            }
            dialogScrollView.setLayoutParams(dialogScrollViewLayoutParams);
            checkBoxLayout.setLayoutParams(checkBoxLayoutParams);
        }
    }

    private void triggerDump() {
        SystemProperties.set("bugreport.mode", "light_mode");
        SystemProperties.set("ctl.start", "bugreportm");
        this.mDumpEndTime = SystemClock.elapsedRealtime() + 300000;
        this.mRootView.postDelayed(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$triggerDump$28();
            }
        }, 10000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: checkPendingDump, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$triggerDump$28() {
        if (isDumpRunning() && !isDumpTimeout()) {
            this.mRootView.postDelayed(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$checkPendingDump$29();
                }
            }, 1000L);
        } else {
            new Thread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$checkPendingDump$31();
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPendingDump$31() {
        UserInfo userInfo = enterMaintenanceMode();
        if (userInfo == null) {
            this.mWm.removeView(this.mDumpView);
            runOnUiThread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$checkPendingDump$30();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPendingDump$30() {
        if (MaintenanceModeUtils.isLowOnStorage(this.mContext)) {
            showDialogToNotifyLowOnStorage();
        }
        this.mTurnOnButton.setClickable(true);
    }

    private boolean isDumpRunning() {
        return SystemProperties.getInt("dumpstate.is_running", 0) != 0;
    }

    private boolean isDumpTimeout() {
        long remainingTime = this.mDumpEndTime - SystemClock.elapsedRealtime();
        if (remainingTime <= 0) {
            Log.i("MaintenanceMode", "Dumpstate wait timed out");
            return true;
        }
        return false;
    }

    private UserInfo enterMaintenanceMode() {
        if (!MaintenanceModeUtils.isSecureLockSet(this.mContext) || MaintenanceModeUtils.checkRequiredConditions(this.mContext, true) != 0) {
            return null;
        }
        try {
            UserManager um = (UserManager) this.mContext.getSystemService("user");
            return um.createUser(this.mResources.getString(R.string.maintenance_mode_name), MaintenanceModeUtils.USER_TYPE_FULL_MAINTENANCE_MODE, 1024);
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Exception", e);
            return null;
        }
    }
}
