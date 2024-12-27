package com.android.server.wm;

import android.R;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.AppInstallerUtil;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public final class AppWarnings {
    public final ActivityTaskManagerService mAtm;
    public final AtomicFile mConfigFile;
    public SparseArray mDeprecatedAbiDialogs;
    public SparseArray mDeprecatedTargetSdkVersionDialogs;
    public SparseArray mPackageNightModeDialogs;
    public final UiHandler mUiHandler;
    public SparseArray mUnsupportedCompileSdkDialogs;
    public SparseArray mUnsupportedDisplaySizeDialogs;
    public UserManagerInternal mUserManagerInternal;
    public final ArrayMap mPackageFlags = new ArrayMap();
    public final ArraySet mAlwaysShowUnsupportedCompileSdkWarningActivities = new ArraySet();
    public final WriteConfigTask mWriteConfigTask = new WriteConfigTask();

    public abstract class BaseDialog {
        public AnonymousClass1 mCloseReceiver;
        public AlertDialog mDialog;
        public final AppWarnings mManager;
        public final String mPackageName;
        public final Context mUiContext;
        public final int mUserId;

        public BaseDialog(AppWarnings appWarnings, Context context, String str, int i) {
            this.mManager = appWarnings;
            this.mUiContext = context;
            this.mPackageName = str;
            this.mUserId = i;
        }

        public final void dismiss() {
            if (this.mDialog == null) {
                return;
            }
            AnonymousClass1 anonymousClass1 = this.mCloseReceiver;
            if (anonymousClass1 != null) {
                this.mUiContext.unregisterReceiver(anonymousClass1);
                this.mCloseReceiver = null;
            }
            this.mDialog.dismiss();
            this.mDialog = null;
        }

        public final void show() {
            if (this.mDialog == null) {
                return;
            }
            if (this.mCloseReceiver == null) {
                ?? r0 = new BroadcastReceiver() { // from class: com.android.server.wm.AppWarnings.BaseDialog.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                            BaseDialog baseDialog = BaseDialog.this;
                            baseDialog.mManager.mUiHandler.obtainMessage(4, baseDialog.mUserId, 0, baseDialog.mPackageName).sendToTarget();
                        }
                    }
                };
                this.mCloseReceiver = r0;
                this.mUiContext.registerReceiver(r0, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
            }
            StringBuilder sb = new StringBuilder("Showing ");
            sb.append(getClass().getSimpleName());
            sb.append(" for package ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, this.mPackageName, "AppWarnings");
            this.mDialog.show();
        }
    }

    public final class UiHandler extends Handler {
        public UiHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog;
            UnsupportedCompileSdkDialog unsupportedCompileSdkDialog;
            PackageNightModeDialog packageNightModeDialog;
            DeprecatedAbiDialog deprecatedAbiDialog;
            DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog;
            UnsupportedCompileSdkDialog unsupportedCompileSdkDialog2;
            UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog2;
            DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog2;
            DeprecatedAbiDialog deprecatedAbiDialog2;
            PackageNightModeDialog packageNightModeDialog2;
            int i = message.what;
            final AppWarnings appWarnings = AppWarnings.this;
            switch (i) {
                case 1:
                    ActivityRecord activityRecord = (ActivityRecord) message.obj;
                    appWarnings.getClass();
                    if (!CoreRune.SYSFW_APP_SPEG || !appWarnings.isSpeg(activityRecord)) {
                        int userIdForActivity = appWarnings.getUserIdForActivity(activityRecord);
                        SparseArray sparseArray = appWarnings.mUnsupportedDisplaySizeDialogs;
                        if (sparseArray != null && (unsupportedDisplaySizeDialog = (UnsupportedDisplaySizeDialog) sparseArray.get(userIdForActivity)) != null) {
                            unsupportedDisplaySizeDialog.dismiss();
                            appWarnings.mUnsupportedDisplaySizeDialogs.remove(userIdForActivity);
                        }
                        if ((appWarnings.getPackageFlags(userIdForActivity, activityRecord.packageName) & 1) != 1) {
                            Context uiContextForActivity = appWarnings.getUiContextForActivity(activityRecord);
                            ApplicationInfo applicationInfo = activityRecord.info.applicationInfo;
                            final UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog3 = new UnsupportedDisplaySizeDialog(appWarnings, uiContextForActivity, applicationInfo.packageName, userIdForActivity);
                            AlertDialog create = new AlertDialog.Builder(uiContextForActivity).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).setMessage(uiContextForActivity.getString(17043313, applicationInfo.loadSafeLabel(uiContextForActivity.getPackageManager(), 1000.0f, 5))).setView(17367515).create();
                            unsupportedDisplaySizeDialog3.mDialog = create;
                            create.create();
                            Window window = unsupportedDisplaySizeDialog3.mDialog.getWindow();
                            window.setType(2002);
                            window.getAttributes().setTitle("UnsupportedDisplaySizeDialog");
                            CheckBox checkBox = (CheckBox) unsupportedDisplaySizeDialog3.mDialog.findViewById(R.id.autofill_dataset_picker);
                            checkBox.setChecked(true);
                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.server.wm.UnsupportedDisplaySizeDialog$$ExternalSyntheticLambda0
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                                    UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog4 = UnsupportedDisplaySizeDialog.this;
                                    appWarnings.setPackageFlag(unsupportedDisplaySizeDialog4.mUserId, 1, unsupportedDisplaySizeDialog4.mPackageName, !z);
                                }
                            });
                            unsupportedDisplaySizeDialog3.show();
                            if (appWarnings.mUnsupportedDisplaySizeDialogs == null) {
                                appWarnings.mUnsupportedDisplaySizeDialogs = new SparseArray();
                            }
                            appWarnings.mUnsupportedDisplaySizeDialogs.put(userIdForActivity, unsupportedDisplaySizeDialog3);
                            break;
                        }
                    }
                    break;
                case 2:
                    if (appWarnings.mUnsupportedDisplaySizeDialogs != null) {
                        for (int i2 = 0; i2 < appWarnings.mUnsupportedDisplaySizeDialogs.size(); i2++) {
                            ((UnsupportedDisplaySizeDialog) appWarnings.mUnsupportedDisplaySizeDialogs.valueAt(i2)).dismiss();
                        }
                        appWarnings.mUnsupportedDisplaySizeDialogs.clear();
                        break;
                    }
                    break;
                case 3:
                    ActivityRecord activityRecord2 = (ActivityRecord) message.obj;
                    appWarnings.getClass();
                    if (!CoreRune.SYSFW_APP_SPEG || !appWarnings.isSpeg(activityRecord2)) {
                        int userIdForActivity2 = appWarnings.getUserIdForActivity(activityRecord2);
                        SparseArray sparseArray2 = appWarnings.mUnsupportedCompileSdkDialogs;
                        if (sparseArray2 != null && (unsupportedCompileSdkDialog = (UnsupportedCompileSdkDialog) sparseArray2.get(userIdForActivity2)) != null) {
                            unsupportedCompileSdkDialog.dismiss();
                            appWarnings.mUnsupportedCompileSdkDialogs.remove(userIdForActivity2);
                        }
                        if ((appWarnings.getPackageFlags(userIdForActivity2, activityRecord2.packageName) & 2) != 2) {
                            final Context uiContextForActivity2 = appWarnings.getUiContextForActivity(activityRecord2);
                            ApplicationInfo applicationInfo2 = activityRecord2.info.applicationInfo;
                            final UnsupportedCompileSdkDialog unsupportedCompileSdkDialog3 = new UnsupportedCompileSdkDialog(appWarnings, uiContextForActivity2, applicationInfo2.packageName, userIdForActivity2);
                            AlertDialog.Builder view = new AlertDialog.Builder(uiContextForActivity2).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).setMessage(uiContextForActivity2.getString(17043311, applicationInfo2.loadSafeLabel(uiContextForActivity2.getPackageManager(), 1000.0f, 5))).setView(17367514);
                            final Intent createIntent = AppInstallerUtil.createIntent(uiContextForActivity2, applicationInfo2.packageName);
                            if (createIntent != null) {
                                view.setNeutralButton(17043310, new DialogInterface.OnClickListener() { // from class: com.android.server.wm.UnsupportedCompileSdkDialog$$ExternalSyntheticLambda0
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i3) {
                                        uiContextForActivity2.startActivity(createIntent);
                                    }
                                });
                            }
                            AlertDialog create2 = view.create();
                            unsupportedCompileSdkDialog3.mDialog = create2;
                            create2.create();
                            Window window2 = unsupportedCompileSdkDialog3.mDialog.getWindow();
                            window2.setType(2002);
                            window2.getAttributes().setTitle("UnsupportedCompileSdkDialog");
                            CheckBox checkBox2 = (CheckBox) unsupportedCompileSdkDialog3.mDialog.findViewById(R.id.autofill_dataset_picker);
                            checkBox2.setChecked(true);
                            checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.server.wm.UnsupportedCompileSdkDialog$$ExternalSyntheticLambda1
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                                    UnsupportedCompileSdkDialog unsupportedCompileSdkDialog4 = UnsupportedCompileSdkDialog.this;
                                    appWarnings.setPackageFlag(unsupportedCompileSdkDialog4.mUserId, 2, unsupportedCompileSdkDialog4.mPackageName, !z);
                                }
                            });
                            unsupportedCompileSdkDialog3.show();
                            if (appWarnings.mUnsupportedCompileSdkDialogs == null) {
                                appWarnings.mUnsupportedCompileSdkDialogs = new SparseArray();
                            }
                            appWarnings.mUnsupportedCompileSdkDialogs.put(userIdForActivity2, unsupportedCompileSdkDialog3);
                            break;
                        }
                    }
                    break;
                case 4:
                    String str = (String) message.obj;
                    int i3 = message.arg1;
                    SparseArray sparseArray3 = appWarnings.mUnsupportedDisplaySizeDialogs;
                    if (sparseArray3 != null && (unsupportedDisplaySizeDialog2 = (UnsupportedDisplaySizeDialog) sparseArray3.get(i3)) != null && (str == null || str.equals(unsupportedDisplaySizeDialog2.mPackageName))) {
                        unsupportedDisplaySizeDialog2.dismiss();
                        appWarnings.mUnsupportedDisplaySizeDialogs.remove(i3);
                    }
                    SparseArray sparseArray4 = appWarnings.mUnsupportedCompileSdkDialogs;
                    if (sparseArray4 != null && (unsupportedCompileSdkDialog2 = (UnsupportedCompileSdkDialog) sparseArray4.get(i3)) != null && (str == null || str.equals(unsupportedCompileSdkDialog2.mPackageName))) {
                        unsupportedCompileSdkDialog2.dismiss();
                        appWarnings.mUnsupportedCompileSdkDialogs.remove(i3);
                    }
                    SparseArray sparseArray5 = appWarnings.mDeprecatedTargetSdkVersionDialogs;
                    if (sparseArray5 != null && (deprecatedTargetSdkVersionDialog = (DeprecatedTargetSdkVersionDialog) sparseArray5.get(i3)) != null && (str == null || str.equals(deprecatedTargetSdkVersionDialog.mPackageName))) {
                        deprecatedTargetSdkVersionDialog.dismiss();
                        appWarnings.mDeprecatedTargetSdkVersionDialogs.remove(i3);
                    }
                    SparseArray sparseArray6 = appWarnings.mDeprecatedAbiDialogs;
                    if (sparseArray6 != null && (deprecatedAbiDialog = (DeprecatedAbiDialog) sparseArray6.get(i3)) != null && (str == null || str.equals(deprecatedAbiDialog.mPackageName))) {
                        deprecatedAbiDialog.dismiss();
                        appWarnings.mDeprecatedAbiDialogs.remove(i3);
                    }
                    SparseArray sparseArray7 = appWarnings.mPackageNightModeDialogs;
                    if (sparseArray7 != null && (packageNightModeDialog = (PackageNightModeDialog) sparseArray7.get(i3)) != null) {
                        if (str == null || str.equals(packageNightModeDialog.mPackageName)) {
                            packageNightModeDialog.dismiss();
                            appWarnings.mPackageNightModeDialogs.remove(i3);
                            break;
                        }
                    }
                    break;
                case 5:
                    ActivityRecord activityRecord3 = (ActivityRecord) message.obj;
                    appWarnings.getClass();
                    if (!CoreRune.SYSFW_APP_SPEG || !appWarnings.isSpeg(activityRecord3)) {
                        int userIdForActivity3 = appWarnings.getUserIdForActivity(activityRecord3);
                        SparseArray sparseArray8 = appWarnings.mDeprecatedTargetSdkVersionDialogs;
                        if (sparseArray8 != null && (deprecatedTargetSdkVersionDialog2 = (DeprecatedTargetSdkVersionDialog) sparseArray8.get(userIdForActivity3)) != null) {
                            deprecatedTargetSdkVersionDialog2.dismiss();
                            appWarnings.mDeprecatedTargetSdkVersionDialogs.remove(userIdForActivity3);
                        }
                        if ((appWarnings.getPackageFlags(userIdForActivity3, activityRecord3.packageName) & 4) != 4) {
                            final Context uiContextForActivity3 = appWarnings.getUiContextForActivity(activityRecord3);
                            ApplicationInfo applicationInfo3 = activityRecord3.info.applicationInfo;
                            final DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog3 = new DeprecatedTargetSdkVersionDialog(appWarnings, uiContextForActivity3, applicationInfo3.packageName, userIdForActivity3);
                            final int i4 = 0;
                            AlertDialog.Builder title = new AlertDialog.Builder(uiContextForActivity3).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.server.wm.DeprecatedTargetSdkVersionDialog$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i5) {
                                    switch (i4) {
                                        case 0:
                                            DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog4 = (DeprecatedTargetSdkVersionDialog) deprecatedTargetSdkVersionDialog3;
                                            ((AppWarnings) appWarnings).setPackageFlag(deprecatedTargetSdkVersionDialog4.mUserId, 4, deprecatedTargetSdkVersionDialog4.mPackageName, true);
                                            break;
                                        default:
                                            ((Context) deprecatedTargetSdkVersionDialog3).startActivity((Intent) appWarnings);
                                            break;
                                    }
                                }
                            }).setMessage(uiContextForActivity3.getString(R.string.gpsVerifNo)).setTitle(applicationInfo3.loadSafeLabel(uiContextForActivity3.getPackageManager(), 1000.0f, 5));
                            final Intent createIntent2 = AppInstallerUtil.createIntent(uiContextForActivity3, applicationInfo3.packageName);
                            if (createIntent2 != null) {
                                final int i5 = 1;
                                title.setNeutralButton(R.string.gpsNotifTitle, new DialogInterface.OnClickListener() { // from class: com.android.server.wm.DeprecatedTargetSdkVersionDialog$$ExternalSyntheticLambda0
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i52) {
                                        switch (i5) {
                                            case 0:
                                                DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog4 = (DeprecatedTargetSdkVersionDialog) uiContextForActivity3;
                                                ((AppWarnings) createIntent2).setPackageFlag(deprecatedTargetSdkVersionDialog4.mUserId, 4, deprecatedTargetSdkVersionDialog4.mPackageName, true);
                                                break;
                                            default:
                                                ((Context) uiContextForActivity3).startActivity((Intent) createIntent2);
                                                break;
                                        }
                                    }
                                });
                            }
                            AlertDialog create3 = title.create();
                            deprecatedTargetSdkVersionDialog3.mDialog = create3;
                            create3.create();
                            Window window3 = deprecatedTargetSdkVersionDialog3.mDialog.getWindow();
                            window3.setType(2002);
                            window3.getAttributes().setTitle("DeprecatedTargetSdkVersionDialog");
                            deprecatedTargetSdkVersionDialog3.show();
                            if (appWarnings.mDeprecatedTargetSdkVersionDialogs == null) {
                                appWarnings.mDeprecatedTargetSdkVersionDialogs = new SparseArray();
                            }
                            appWarnings.mDeprecatedTargetSdkVersionDialogs.put(userIdForActivity3, deprecatedTargetSdkVersionDialog3);
                            break;
                        }
                    }
                    break;
                case 6:
                    ActivityRecord activityRecord4 = (ActivityRecord) message.obj;
                    appWarnings.getClass();
                    if (!CoreRune.SYSFW_APP_SPEG || !appWarnings.isSpeg(activityRecord4)) {
                        int userIdForActivity4 = appWarnings.getUserIdForActivity(activityRecord4);
                        SparseArray sparseArray9 = appWarnings.mDeprecatedAbiDialogs;
                        if (sparseArray9 != null && (deprecatedAbiDialog2 = (DeprecatedAbiDialog) sparseArray9.get(userIdForActivity4)) != null) {
                            deprecatedAbiDialog2.dismiss();
                            appWarnings.mDeprecatedAbiDialogs.remove(userIdForActivity4);
                        }
                        if ((appWarnings.getPackageFlags(userIdForActivity4, activityRecord4.packageName) & 8) != 8) {
                            Context uiContextForActivity4 = appWarnings.getUiContextForActivity(activityRecord4);
                            ApplicationInfo applicationInfo4 = activityRecord4.info.applicationInfo;
                            final DeprecatedAbiDialog deprecatedAbiDialog3 = new DeprecatedAbiDialog(appWarnings, uiContextForActivity4, applicationInfo4.packageName, userIdForActivity4);
                            AlertDialog create4 = new AlertDialog.Builder(uiContextForActivity4).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.server.wm.DeprecatedAbiDialog$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i6) {
                                    DeprecatedAbiDialog deprecatedAbiDialog4 = DeprecatedAbiDialog.this;
                                    appWarnings.setPackageFlag(deprecatedAbiDialog4.mUserId, 8, deprecatedAbiDialog4.mPackageName, true);
                                }
                            }).setMessage(uiContextForActivity4.getString(R.string.gpsNotifTicker)).setTitle(applicationInfo4.loadSafeLabel(uiContextForActivity4.getPackageManager(), 1000.0f, 5)).create();
                            deprecatedAbiDialog3.mDialog = create4;
                            create4.create();
                            Window window4 = deprecatedAbiDialog3.mDialog.getWindow();
                            window4.setType(2002);
                            window4.getAttributes().setTitle("DeprecatedAbiDialog");
                            deprecatedAbiDialog3.show();
                            if (appWarnings.mDeprecatedAbiDialogs == null) {
                                appWarnings.mDeprecatedAbiDialogs = new SparseArray();
                            }
                            appWarnings.mDeprecatedAbiDialogs.put(userIdForActivity4, deprecatedAbiDialog3);
                            break;
                        }
                    }
                    break;
                case 7:
                    ActivityRecord activityRecord5 = (ActivityRecord) message.obj;
                    int userIdForActivity5 = appWarnings.getUserIdForActivity(activityRecord5);
                    SparseArray sparseArray10 = appWarnings.mPackageNightModeDialogs;
                    if (sparseArray10 != null && (packageNightModeDialog2 = (PackageNightModeDialog) sparseArray10.get(userIdForActivity5)) != null) {
                        packageNightModeDialog2.dismiss();
                        appWarnings.mPackageNightModeDialogs.remove(userIdForActivity5);
                    }
                    if (activityRecord5 != null && activityRecord5.mShouldShowPackageNightModeDialog) {
                        final Context uiContextForActivity5 = appWarnings.getUiContextForActivity(activityRecord5);
                        ApplicationInfo applicationInfo5 = activityRecord5.info.applicationInfo;
                        PackageNightModeDialog packageNightModeDialog3 = new PackageNightModeDialog(appWarnings, uiContextForActivity5, applicationInfo5.packageName, userIdForActivity5);
                        AlertDialog create5 = new AlertDialog.Builder(uiContextForActivity5, uiContextForActivity5.getResources().getConfiguration().isNightModeActive() ? R.style.Theme.DeviceDefault.Dialog.Alert : R.style.Theme.DeviceDefault.Light.Dialog.Alert).setNegativeButton(R.string.sms_short_code_remember_choice, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.smv_application, new DialogInterface.OnClickListener() { // from class: com.android.server.wm.PackageNightModeDialog$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i6) {
                                Context context = uiContextForActivity5;
                                Intent intent = new Intent();
                                intent.setClassName(KnoxCustomManagerService.SETTING_PKG_NAME, "com.android.settings.Settings$DarkModeAppsSettingsActivity");
                                intent.addFlags(268468224);
                                context.startActivity(intent);
                            }
                        }).setMessage(String.format(uiContextForActivity5.getString(R.string.sms_short_code_remember_undo_instruction), applicationInfo5.loadSafeLabel(uiContextForActivity5.getPackageManager(), 1000.0f, 5))).setCancelable(false).create();
                        packageNightModeDialog3.mDialog = create5;
                        create5.setCanceledOnTouchOutside(false);
                        packageNightModeDialog3.mDialog.create();
                        Window window5 = packageNightModeDialog3.mDialog.getWindow();
                        window5.setType(2002);
                        window5.getAttributes().setTitle("PackageNightModeDialog");
                        packageNightModeDialog3.show();
                        activityRecord5.mShouldShowPackageNightModeDialog = false;
                        if (appWarnings.mPackageNightModeDialogs == null) {
                            appWarnings.mPackageNightModeDialogs = new SparseArray();
                        }
                        appWarnings.mPackageNightModeDialogs.put(userIdForActivity5, packageNightModeDialog3);
                        break;
                    }
                    break;
            }
        }
    }

    public final class WriteConfigTask implements Runnable {
        public final AtomicReference mPendingPackageFlags = new AtomicReference();

        public WriteConfigTask() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            FileOutputStream fileOutputStream = null;
            ArrayMap arrayMap = (ArrayMap) this.mPendingPackageFlags.getAndSet(null);
            if (arrayMap == null) {
                return;
            }
            AppWarnings appWarnings = AppWarnings.this;
            appWarnings.getClass();
            try {
                FileOutputStream startWrite = appWarnings.mConfigFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((String) null, "packages");
                    for (int i = 0; i < arrayMap.size(); i++) {
                        Pair pair = (Pair) arrayMap.keyAt(i);
                        int intValue = ((Integer) pair.first).intValue();
                        String str = (String) pair.second;
                        int intValue2 = ((Integer) arrayMap.valueAt(i)).intValue();
                        if (intValue2 != 0) {
                            resolveSerializer.startTag((String) null, "package");
                            resolveSerializer.attributeInt((String) null, "user", intValue);
                            resolveSerializer.attribute((String) null, "name", str);
                            resolveSerializer.attributeInt((String) null, "flags", intValue2);
                            resolveSerializer.endTag((String) null, "package");
                        }
                    }
                    resolveSerializer.endTag((String) null, "packages");
                    resolveSerializer.endDocument();
                    appWarnings.mConfigFile.finishWrite(startWrite);
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = startWrite;
                    Slog.w("AppWarnings", "Error writing package metadata", e);
                    if (fileOutputStream != null) {
                        appWarnings.mConfigFile.failWrite(fileOutputStream);
                    }
                }
            } catch (IOException e2) {
                e = e2;
            }
        }

        public final void schedule() {
            if (this.mPendingPackageFlags.getAndSet(new ArrayMap(AppWarnings.this.mPackageFlags)) == null) {
                IoThread.getHandler().postDelayed(this, 10000L);
            }
        }
    }

    public AppWarnings(Handler handler, ActivityTaskManagerService activityTaskManagerService, File file) {
        this.mAtm = activityTaskManagerService;
        this.mUiHandler = new UiHandler(handler.getLooper());
        this.mConfigFile = new AtomicFile(new File(file, "packages-warnings.xml"), "warnings-config");
    }

    public final int getPackageFlags(int i, String str) {
        int intValue;
        synchronized (this.mPackageFlags) {
            intValue = ((Integer) this.mPackageFlags.getOrDefault(Pair.create(Integer.valueOf(i), str), 0)).intValue();
        }
        return intValue;
    }

    public final Context getUiContextForActivity(ActivityRecord activityRecord) {
        if (UserManager.isVisibleBackgroundUsersEnabled()) {
            DisplayContent displayContent = activityRecord.mDisplayContent;
            return displayContent.mDisplayPolicy.mUiContext.createContextAsUser(new UserHandle(this.mUserManagerInternal.getUserAssignedToDisplay(displayContent.mDisplayId)), 0);
        }
        boolean isHeadlessSystemUserMode = UserManager.isHeadlessSystemUserMode();
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        return !isHeadlessSystemUserMode ? activityTaskManagerService.mUiContext : activityTaskManagerService.mUiContext.createContextAsUser(new UserHandle(activityTaskManagerService.mAmInternal.getCurrentUserId()), 0);
    }

    public final int getUserIdForActivity(ActivityRecord activityRecord) {
        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
            return 0;
        }
        int i = activityRecord.mUserId;
        if (i != 0) {
            return this.mUserManagerInternal.getProfileParentId(i);
        }
        return this.mUserManagerInternal.getUserAssignedToDisplay(activityRecord.mDisplayContent.mDisplayId);
    }

    public final boolean isSpeg(ActivityRecord activityRecord) {
        PackageManager packageManager;
        if (activityRecord == null || (packageManager = getUiContextForActivity(activityRecord).getPackageManager()) == null || !packageManager.isSpeg(activityRecord.packageName, activityRecord.mUserId)) {
            return false;
        }
        BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("Skipping warning dialog of "), activityRecord.packageName, "SPEG");
        return true;
    }

    public final void onStartActivity(ActivityRecord activityRecord) {
        ApplicationInfo applicationInfo = activityRecord.info.applicationInfo;
        int i = applicationInfo.compileSdkVersion;
        UiHandler uiHandler = this.mUiHandler;
        if (i != 0 && applicationInfo.compileSdkVersionCodename != null && this.mAlwaysShowUnsupportedCompileSdkWarningActivities.contains(activityRecord.mActivityComponent)) {
            ApplicationInfo applicationInfo2 = activityRecord.info.applicationInfo;
            int i2 = applicationInfo2.compileSdkVersion;
            int i3 = Build.VERSION.SDK_INT;
            boolean z = !"REL".equals(applicationInfo2.compileSdkVersionCodename);
            String str = Build.VERSION.CODENAME;
            boolean z2 = !"REL".equals(str);
            if ((z && i2 < i3) || ((z2 && i3 < i2) || (z && z2 && i3 == i2 && !str.equals(activityRecord.info.applicationInfo.compileSdkVersionCodename)))) {
                uiHandler.removeMessages(3);
                uiHandler.obtainMessage(3, activityRecord).sendToTarget();
            }
        }
        showUnsupportedDisplaySizeDialogIfNeeded(activityRecord);
        boolean z3 = SystemProperties.getBoolean("debug.wm.disable_deprecated_target_sdk_dialog", false);
        if (activityRecord.info.applicationInfo.targetSdkVersion < Build.VERSION.MIN_SUPPORTED_TARGET_SDK_INT && !z3) {
            uiHandler.removeMessages(5);
            uiHandler.obtainMessage(5, activityRecord).sendToTarget();
        }
        if ((activityRecord.info.applicationInfo.privateFlagsExt & 32) == 0 && !SystemProperties.getBoolean("debug.wm.disable_deprecated_abi_dialog", false)) {
            ApplicationInfo applicationInfo3 = activityRecord.info.applicationInfo;
            String str2 = applicationInfo3.primaryCpuAbi;
            boolean z4 = (str2 == null || applicationInfo3.secondaryCpuAbi != null || str2.contains("64")) ? false : true;
            if (ArrayUtils.find(Build.SUPPORTED_ABIS, new AppWarnings$$ExternalSyntheticLambda0()) != null && z4) {
                uiHandler.removeMessages(6);
                uiHandler.obtainMessage(6, activityRecord).sendToTarget();
            }
        }
        if (activityRecord.mShouldShowPackageNightModeDialog) {
            uiHandler.removeMessages(7);
            uiHandler.obtainMessage(7, activityRecord).sendToTarget();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0038, code lost:
    
        if (r5 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00e7, code lost:
    
        if (r5 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00fc, code lost:
    
        if (r4 != null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00fe, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0105, code lost:
    
        if (r4 == null) goto L69;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSystemReady() {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.AppWarnings.onSystemReady():void");
    }

    public final void removePackageAndHideDialogs(int i, String str) {
        int profileParentId = !UserManager.isVisibleBackgroundUsersEnabled() ? 0 : this.mUserManagerInternal.getProfileParentId(i);
        this.mUiHandler.obtainMessage(4, profileParentId, 0, str).sendToTarget();
        synchronized (this.mPackageFlags) {
            try {
                if (this.mPackageFlags.remove(Pair.create(Integer.valueOf(profileParentId), str)) != null) {
                    this.mWriteConfigTask.schedule();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setPackageFlag(int i, int i2, String str, boolean z) {
        synchronized (this.mPackageFlags) {
            try {
                int packageFlags = getPackageFlags(i, str);
                int i3 = z ? i2 | packageFlags : (~i2) & packageFlags;
                if (packageFlags != i3) {
                    Pair create = Pair.create(Integer.valueOf(i), str);
                    if (i3 != 0) {
                        this.mPackageFlags.put(create, Integer.valueOf(i3));
                    } else {
                        this.mPackageFlags.remove(create);
                    }
                    this.mWriteConfigTask.schedule();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void showUnsupportedDisplaySizeDialogIfNeeded(ActivityRecord activityRecord) {
        Configuration globalConfiguration = this.mAtm.getGlobalConfiguration();
        if (globalConfiguration.densityDpi == DisplayMetrics.DENSITY_DEVICE_STABLE || activityRecord.info.applicationInfo.requiresSmallestWidthDp <= globalConfiguration.smallestScreenWidthDp) {
            return;
        }
        UiHandler uiHandler = this.mUiHandler;
        uiHandler.removeMessages(1);
        uiHandler.obtainMessage(1, activityRecord).sendToTarget();
    }
}
