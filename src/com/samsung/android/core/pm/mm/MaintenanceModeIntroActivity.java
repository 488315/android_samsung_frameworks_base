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

    static /* synthetic */ void lambda$showDialogToInformSecureLockIsNeeded$12(DialogInterface dialogInterface, int i) {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        this.mResources = applicationContext.getResources();
        if (MaintenanceModeUtils.checkRequiredConditions(this.mContext, true) != 0) {
            finish();
            return;
        }
        this.mIsTablet = MaintenanceModeUtils.isTablet();
        this.mIsFold = MaintenanceModeUtils.isFold();
        setContentView(getResources().getConfiguration());
        init();
    }

    private void setContentView(Configuration configuration) {
        MaintenanceModeUtils.configureLayout(this, this.mResources, configuration, this.mIsTablet, this.mIsFold, R.layout.activity_maintenance_mode_intro, R.layout.activity_maintenance_mode_intro_land, R.id.maintenance_mode_intro_body_container);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (this.mIsTablet) {
            ((ImageView) findViewById(R.id.maintenance_mode_intro_imageview)).setMaxWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_image_max_width_tablet));
            ((TextView) findViewById(R.id.maintenance_mode_intro_summary_textview)).lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_intro_summary_textview_message_tablet));
            ((TextView) findViewById(R.id.maintenance_mode_intro_description_need_to_unlock_textview)).lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_intro_description_need_to_unlock_tablet));
            ((TextView) findViewById(R.id.maintenance_mode_intro_recommendation_textview)).lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_intro_recommendation_textview_message_tablet));
        }
        View findViewById = findViewById(R.id.maintenance_mode_intro_backup_menu_cloud);
        this.mColudBackupMenu = findViewById;
        findViewById.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_top);
        this.mColudBackupMenu.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeIntroActivity.this.lambda$setContentView$2(view);
            }
        });
        ((TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_cloud_main_textview)).setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_main_text_size));
        TextView textView = (TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_cloud_sub_textview);
        this.mColudBackupMenuSubTextView = textView;
        textView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_sub_text_size));
        this.mColudBackupMenuDivider = findViewById(R.id.maintenance_mode_intro_backup_menu_divider_cloud);
        View findViewById2 = findViewById(R.id.maintenance_mode_intro_backup_menu_external_storage);
        this.mExternalStorageBackupMenu = findViewById2;
        findViewById2.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_alone);
        this.mExternalStorageBackupMenu.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeIntroActivity.this.lambda$setContentView$4(view);
            }
        });
        ((TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_external_storage_main_textview)).setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_main_text_size));
        ((TextView) findViewById(R.id.maintenance_mode_intro_backup_menu_external_storage_sub_textview)).setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_focus_block_sub_text_size));
        updateCloudBackupMenuSubText();
        updateCloudBackupMenuVisibility();
        Button button = (Button) findViewById(R.id.maintenance_mode_intro_turnon_button);
        this.mTurnOnButton = button;
        if (this.mIsTablet) {
            button.setWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_body_button_width_tablet));
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
    public /* synthetic */ void lambda$setContentView$2(View view) {
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
    public /* synthetic */ void lambda$setContentView$4(View view) {
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
    public /* synthetic */ void lambda$setContentView$8(View view) {
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
        final String statusOfBackupInProgress = MaintenanceModeUtils.getStatusOfBackupInProgress(this.mContext);
        runOnUiThread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$setContentView$5(statusOfBackupInProgress);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$5(String str) {
        if ("NOT_IN_PROGRESS".equals(str)) {
            showDialogToConfirmRestart();
        } else {
            showDialogToReconfirmCancelingBackup(str);
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
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 131328, -3);
        this.mViewWindowParams = layoutParams;
        layoutParams.gravity = 17;
        this.mViewWindowParams.privateFlags |= 16;
        this.mViewWindowParams.screenOrientation = 1;
        this.mViewWindowParams.layoutInDisplayCutoutMode = 1;
        this.mViewWindowParams.setFitInsetsSides(0);
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
        this.mWaitingView = inflate;
        inflate.findViewById(R.id.maintenance_mode_view_dump_progressbar_container).setVisibility(8);
        TextView textView = (TextView) this.mWaitingView.findViewById(R.id.maintenance_mode_view_dump_textview);
        textView.lambda$setTextAsync$0(this.mResources.getString(this.mIsTablet ? R.string.maintenance_mode_view_waiting_textview_message_tablet : R.string.maintenance_mode_view_waiting_textview_message_phone));
        textView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_text_size));
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
        this.mDumpView = inflate2;
        TextView textView2 = (TextView) inflate2.findViewById(R.id.maintenance_mode_view_dump_textview);
        StringBuilder sb = new StringBuilder();
        sb.append(this.mResources.getString(R.string.maintenance_mode_view_dump_textview_message_creating));
        sb.append("\n\n");
        sb.append(this.mResources.getString(this.mIsTablet ? R.string.maintenance_mode_view_dump_textview_message_tablet : R.string.maintenance_mode_view_dump_textview_message_phone));
        textView2.lambda$setTextAsync$0(sb.toString());
        textView2.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_text_size));
        if (this.mIsTablet) {
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_body_padding_left_right_tablet);
            this.mWaitingView.findViewById(R.id.maintenance_mode_view_dump_container).setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
            this.mDumpView.findViewById(R.id.maintenance_mode_view_dump_container).setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        boolean z = false;
        if (MaintenanceModeUtils.checkRequiredConditions(this.mContext, false) != 0) {
            finish();
            z = true;
        }
        super.onResume();
        if (z) {
            return;
        }
        checkAndUpdateCloudBackupMenu();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mCloudBackupTimerTask = anonymousClass1;
        this.mTimer.schedule(anonymousClass1, 30000L, 30000L);
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
                } catch (Exception unused) {
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
        TimerTask timerTask = this.mCloudBackupTimerTask;
        if (timerTask != null) {
            timerTask.cancel();
        }
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            unregisterReceiver(this.mCloudBackupReceiver);
        } catch (Exception unused) {
        }
        this.mTimer.cancel();
        this.mSingleThreadExecutor.shutdownNow();
        this.mButtonExecutor.shutdownNow();
        this.mLoggingExecutor.shutdown();
        super.onDestroy();
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
        adjustDialogLayout(configuration);
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
        MaintenanceModeUtils.CloudInfo checkCloudBackupSupport = MaintenanceModeUtils.checkCloudBackupSupport(this.mContext);
        this.mIsCloudBackupSupported = checkCloudBackupSupport.isSupported;
        this.mCloudBackupRetentionPeriod = checkCloudBackupSupport.retentionPeriod;
        this.mCloudBackupIntroDescription = checkCloudBackupSupport.introDescription;
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
    public void updateCloudBackupStatusFromReceiver(String str) {
        this.mCloudBackupStatus = convertActionToStatusForCloudBackup(str);
        runOnUiThread(new MaintenanceModeIntroActivity$$ExternalSyntheticLambda23(this));
    }

    private void updateCloudBackupMenuVisibility() {
        View view = this.mColudBackupMenu;
        if (view == null || this.mColudBackupMenuDivider == null || this.mExternalStorageBackupMenu == null) {
            return;
        }
        if (this.mIsCloudBackupSupported) {
            view.setVisibility(0);
            this.mColudBackupMenuDivider.setVisibility(0);
            this.mExternalStorageBackupMenu.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_bottom);
        } else {
            view.setVisibility(8);
            this.mColudBackupMenuDivider.setVisibility(8);
            this.mExternalStorageBackupMenu.setBackgroundResource(R.drawable.shape_maintenance_mode_focus_block_alone);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCloudBackupMenuSubText() {
        if (this.mColudBackupMenuSubTextView == null) {
        }
        String str = this.mCloudBackupStatus;
        str.hashCode();
        switch (str) {
            case "BACKUP_NON_FINISHED":
                this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_backup_cloud_menu_sub_textview_message_backed_up_failed));
                break;
            case "BACKUP_RUNNING":
                this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_backup_cloud_menu_sub_textview_message_backing_up));
                break;
            case "BACKUP_COMPLETED":
                this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mResources.getString(R.string.maintenance_mode_backup_cloud_menu_sub_textview_message_backed_up_succeeded));
                break;
            default:
                if (!TextUtils.isEmpty(this.mCloudBackupIntroDescription)) {
                    this.mColudBackupMenuSubTextView.lambda$setTextAsync$0(this.mCloudBackupIntroDescription);
                    break;
                } else {
                    TextView textView = this.mColudBackupMenuSubTextView;
                    Resources resources = this.mResources;
                    int i = this.mCloudBackupRetentionPeriod;
                    textView.lambda$setTextAsync$0(resources.getQuantityString(R.plurals.maintenance_mode_backup_cloud_menu_sub_textview_message_default, i, Integer.valueOf(i)));
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
                } catch (Exception unused) {
                }
            }
            Log.i("MaintenanceMode", "onReceive: " + action);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(String str) {
            MaintenanceModeIntroActivity.this.updateCloudBackupStatusFromReceiver(str);
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
    private String convertActionToStatusForCloudBackup(String str) {
        char c;
        switch (str.hashCode()) {
            case 365973607:
                if (str.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_CANCELED")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 750993107:
                if (str.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_STARTED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 875734045:
                if (str.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_COMPLETED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1579405644:
                if (str.equals("com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_NOT_FINISHED")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return "BACKUP_RUNNING";
        }
        if (c == 1) {
            return "BACKUP_COMPLETED";
        }
        if (c == 2) {
            return "BACKUP_NON_FINISHED";
        }
        return KeyProperties.DIGEST_NONE;
    }

    private void showDialogToInformSecureLockIsNeeded() {
        AlertDialog create = new AlertDialog.Builder(this).setMessage(R.string.maintenance_mode_intro_dialog_message_set_screen_lock_first).setPositiveButton(R.string.maintenance_mode_intro_dialog_button_text_set_screen_lock, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToInformSecureLockIsNeeded$11(dialogInterface, i);
            }
        }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.lambda$showDialogToInformSecureLockIsNeeded$12(dialogInterface, i);
            }
        }).create();
        create.getWindow().setGravity(80);
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToInformSecureLockIsNeeded$11(DialogInterface dialogInterface, int i) {
        MaintenanceModeUtils.startActivityToSetSecureLock(this);
    }

    private void showDialogToNotifyLowOnStorage() {
        Log.i("MaintenanceMode", "Low on storage");
        AlertDialog create = new AlertDialog.Builder(this).setTitle(R.string.maintenance_mode_intro_low_storage_dialog_title).setMessage(this.mIsTablet ? R.string.maintenance_mode_intro_low_storage_dialog_message_tablet : R.string.maintenance_mode_intro_low_storage_dialog_message_phone).setPositiveButton(R.string.maintenance_mode_intro_low_storage_dialog_button_text, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToNotifyLowOnStorage$13(dialogInterface, i);
            }
        }).create();
        create.getWindow().setGravity(80);
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToNotifyLowOnStorage$13(DialogInterface dialogInterface, int i) {
        MaintenanceModeUtils.startMyFilesActivity(this);
    }

    private void showDialogToReconfirmCancelingBackup(String str) {
        int i;
        final boolean z = false;
        if (str != null) {
            str.hashCode();
            switch (str) {
                case "RESTORE_RUNNING":
                    i = R.string.maintenance_mode_stop_backup_dialog_message_restoring;
                    z = true;
                    break;
                case "BACKUP_NON_FINISHED":
                    i = R.string.maintenance_mode_stop_backup_dialog_message_backed_up_failed;
                    z = true;
                    break;
                case "BACKUP_RUNNING":
                    i = R.string.maintenance_mode_stop_backup_dialog_message_backing_up;
                    z = true;
                    break;
            }
            AlertDialog create = new AlertDialog.Builder(this).setMessage(i).setPositiveButton(R.string.maintenance_mode_dialog_button_text_ok, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda18
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$16(z, dialogInterface, i2);
                }
            }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda19
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$18(dialogInterface, i2);
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda20
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$20(dialogInterface);
                }
            }).create();
            create.getWindow().setGravity(80);
            create.show();
        }
        i = R.string.maintenance_mode_stop_backup_dialog_message;
        AlertDialog create2 = new AlertDialog.Builder(this).setMessage(i).setPositiveButton(R.string.maintenance_mode_dialog_button_text_ok, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$16(z, dialogInterface, i2);
            }
        }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda19
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$18(dialogInterface, i2);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$20(dialogInterface);
            }
        }).create();
        create2.getWindow().setGravity(80);
        create2.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$16(boolean z, DialogInterface dialogInterface, int i) {
        if (z) {
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
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$18(DialogInterface dialogInterface, int i) {
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
    public /* synthetic */ void lambda$showDialogToReconfirmCancelingBackup$20(DialogInterface dialogInterface) {
        this.mLoggingExecutor.submit(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeIntroActivity.this.lambda$showDialogToReconfirmCancelingBackup$19();
            }
        });
    }

    private void showDialogToConfirmRestart() {
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
        AlertDialog create = new AlertDialog.Builder(this).setView(this.mDialogView).setPositiveButton(R.string.maintenance_mode_dialog_button_text_restart, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$27(checkedTextView, dialogInterface, i);
            }
        }).create();
        create.getWindow().setGravity(80);
        TextView textView = (TextView) this.mDialogView.findViewById(R.id.maintenance_mode_intro_dialog_textview);
        StringBuilder sb = new StringBuilder();
        sb.append(this.mResources.getString(this.mIsTablet ? R.string.maintenance_mode_intro_dialog_message_tablet : R.string.maintenance_mode_intro_dialog_message_phone));
        sb.append("\n\n");
        sb.append(this.mResources.getString(this.mIsTablet ? R.string.maintenance_mode_intro_dialog_textview_message_tablet : R.string.maintenance_mode_intro_dialog_textview_message_phone));
        textView.lambda$setTextAsync$0(sb.toString());
        textView.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_text_size));
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$27(CheckedTextView checkedTextView, DialogInterface dialogInterface, int i) {
        if (!MaintenanceModeUtils.isSecureLockSet(this.mContext)) {
            showDialogToInformSecureLockIsNeeded();
        } else {
            final boolean z = !checkedTextView.isChecked();
            MaintenanceModeUtils.confirmSecureLock(this, new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeIntroActivity$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$26(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$26(final boolean z) {
        this.mTurnOnButton.setClickable(false);
        if (this.mIsTablet) {
            setWaitingViewRotation();
        }
        MaintenanceModeUtils.setUserConsentAboutCreatingLog(!z);
        if (z) {
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
                MaintenanceModeIntroActivity.this.lambda$showDialogToConfirmRestart$24(z);
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
        if (enterMaintenanceMode() == null) {
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
    public /* synthetic */ void lambda$showDialogToConfirmRestart$24(boolean z) {
        String str;
        Context context = this.mContext;
        if (z) {
            str = "1";
        } else {
            str = "0";
        }
        MaintenanceModeUtils.sendLoggingDataToSA(context, "7070", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialogToConfirmRestart$25() {
        MaintenanceModeUtils.sendLoggingDataToSA(this.mContext, "7071", null);
    }

    private void setWaitingViewRotation() {
        Display display = getDisplay();
        if (display != null) {
            int rotation = display.getRotation();
            if (rotation == 0) {
                this.mViewWindowParams.screenOrientation = 1;
                return;
            }
            if (rotation == 1) {
                this.mViewWindowParams.screenOrientation = 0;
            } else if (rotation == 2) {
                this.mViewWindowParams.screenOrientation = 9;
            } else {
                if (rotation != 3) {
                    return;
                }
                this.mViewWindowParams.screenOrientation = 8;
            }
        }
    }

    private void adjustDialogLayout(Configuration configuration) {
        View view;
        if (this.mIsTablet) {
            return;
        }
        if ((!this.mIsFold || configuration.semDisplayDeviceType == 5) && (view = this.mDialogView) != null) {
            ScrollView scrollView = (ScrollView) view.findViewById(R.id.maintenance_mode_intro_dialog_scrollview);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) scrollView.getLayoutParams();
            View findViewById = this.mDialogView.findViewById(R.id.maintenance_mode_intro_dialog_checkbox_layout);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) findViewById.getLayoutParams();
            if (configuration.orientation == 2) {
                marginLayoutParams.topMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_margin_top_land);
                int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_padding_top_bottom_land);
                findViewById.setPadding(0, dimensionPixelSize, 0, dimensionPixelSize);
                marginLayoutParams2.bottomMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_margin_bottom_land);
            } else {
                marginLayoutParams.topMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_margin_top);
                int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_padding_top_bottom);
                findViewById.setPadding(0, dimensionPixelSize2, 0, dimensionPixelSize2);
                marginLayoutParams2.bottomMargin = this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_dialog_checkbox_margin_bottom);
            }
            scrollView.setLayoutParams(marginLayoutParams);
            findViewById.setLayoutParams(marginLayoutParams2);
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
        if (enterMaintenanceMode() == null) {
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
        if (this.mDumpEndTime - SystemClock.elapsedRealtime() > 0) {
            return false;
        }
        Log.i("MaintenanceMode", "Dumpstate wait timed out");
        return true;
    }

    private UserInfo enterMaintenanceMode() {
        if (MaintenanceModeUtils.isSecureLockSet(this.mContext) && MaintenanceModeUtils.checkRequiredConditions(this.mContext, true) == 0) {
            try {
                return ((UserManager) this.mContext.getSystemService("user")).createUser(this.mResources.getString(R.string.maintenance_mode_name), MaintenanceModeUtils.USER_TYPE_FULL_MAINTENANCE_MODE, 1024);
            } catch (Exception e) {
                Log.i("MaintenanceMode", "Exception", e);
            }
        }
        return null;
    }
}
