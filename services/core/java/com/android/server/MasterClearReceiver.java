package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.Supplier;

public class MasterClearReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mWipeEsims;
    public boolean mWipeExternalStorage;
    public boolean mShowWipeProgress = true;
    public String requestedTimeArg = null;
    public String extraCmd = null;

    public final class WipeDataTask extends AsyncTask {
        public final Thread mChainedTask;
        public final Context mContext;
        public final ProgressDialog mProgressDialog;

        public WipeDataTask(Context context, AnonymousClass1 anonymousClass1) {
            this.mContext = context;
            this.mChainedTask = anonymousClass1;
            Slog.d("MasterClear", "!@[MasterClearReceiver] WipeDataTask: mProgressDialog object will create");
            ProgressDialog progressDialog = MasterClearReceiver.this.mShowWipeProgress ? new ProgressDialog(context, R.style.ThemeOverlay.DeviceDefault.Accent) : null;
            this.mProgressDialog = progressDialog;
            if (progressDialog != null) {
                Slog.d("MasterClear", "!@[MasterClearReceiver] WipeDataTask: mProgressDialog object created");
            }
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Slog.w("MasterClear", "Wiping adoptable disks");
            if (!MasterClearReceiver.this.mWipeExternalStorage) {
                return null;
            }
            ((StorageManager) this.mContext.getSystemService("storage")).wipeAdoptableDisks();
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ProgressDialog progressDialog = this.mProgressDialog;
            if (progressDialog != null && progressDialog.isShowing()) {
                Slog.d("MasterClear", "!@[MasterClearReceiver] WipeDataTask onPostExecute: mProgressDialog object will dismiss");
                this.mProgressDialog.dismiss();
                Slog.d("MasterClear", "!@[MasterClearReceiver] WipeDataTask onPostExecute: mProgressDialog object dismissed");
            }
            this.mChainedTask.start();
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            ProgressDialog progressDialog = this.mProgressDialog;
            if (progressDialog != null) {
                progressDialog.setIndeterminate(true);
                this.mProgressDialog.getWindow().setType(2003);
                this.mProgressDialog.setMessage(this.mContext.getText(17042507));
                Slog.d("MasterClear", "!@[MasterClearReceiver] WipeDataTask onPreExecute: mProgressDialog will show");
                this.mProgressDialog.show();
                Slog.d("MasterClear", "!@[MasterClearReceiver] WipeDataTask onPreExecute: mProgressDialog showed");
            }
        }
    }

    /* renamed from: -$$Nest$mremoveFirstUseDate, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m68$$Nest$mremoveFirstUseDate(com.android.server.MasterClearReceiver r9) {
        /*
            r9.getClass()
            java.lang.String r9 = "MasterClear"
            java.lang.String r0 = "!@[MasterClearReceiver]removeFirstUseDate)"
            android.util.Slog.d(r9, r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r1 = "/sys/class/power_supply/sec_auth/fai_expired"
            r0.add(r1)
            java.lang.String r1 = "/sys/class/power_supply/sec_auth_2nd/fai_expired"
            r0.add(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.String r2 = "/sys/class/power_supply/sec_auth/first_use_date"
            r1.add(r2)
            java.lang.String r2 = "/sys/class/power_supply/sec_auth_2nd/first_use_date"
            r1.add(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r3 = "/efs/FactoryApp/batt_beginning_date"
            r2.add(r3)
            java.lang.String r3 = "/efs/FactoryApp/batt_beginning_date_2nd"
            r2.add(r3)
            r3 = 0
            r4 = r3
        L39:
            r5 = 2
            if (r4 >= r5) goto Ld0
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L7a
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Exception -> L7a
            java.lang.Object r7 = r0.get(r4)     // Catch: java.lang.Exception -> L7a
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Exception -> L7a
            r6.<init>(r7)     // Catch: java.lang.Exception -> L7a
            r5.<init>(r6)     // Catch: java.lang.Exception -> L7a
            java.lang.String r6 = r5.readLine()     // Catch: java.lang.Throwable -> L70
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L70
            r7.<init>()     // Catch: java.lang.Throwable -> L70
            java.lang.String r8 = "!@[MasterClearReceiver]removeFirstUseDate)faiExpiredStr:"
            r7.append(r8)     // Catch: java.lang.Throwable -> L70
            r7.append(r6)     // Catch: java.lang.Throwable -> L70
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L70
            android.util.Slog.i(r9, r7)     // Catch: java.lang.Throwable -> L70
            java.lang.String r7 = "0"
            boolean r6 = r7.equals(r6)     // Catch: java.lang.Throwable -> L70
            r5.close()     // Catch: java.lang.Exception -> L6e
            goto L84
        L6e:
            r5 = move-exception
            goto L7c
        L70:
            r6 = move-exception
            r5.close()     // Catch: java.lang.Throwable -> L75
            goto L79
        L75:
            r5 = move-exception
            r6.addSuppressed(r5)     // Catch: java.lang.Exception -> L7a
        L79:
            throw r6     // Catch: java.lang.Exception -> L7a
        L7a:
            r5 = move-exception
            r6 = r3
        L7c:
            java.lang.String r7 = "!@[MasterClearReceiver]removeFirstUseDate)read or parse Exception"
            android.util.Slog.w(r9, r7)
            r5.printStackTrace()
        L84:
            if (r6 != 0) goto L8c
            java.lang.String r5 = "!@[MasterClearReceiver]removeFirstUseDate)skip remove FirstUseDate"
            android.util.Slog.d(r9, r5)
            goto Lcc
        L8c:
            java.io.FileWriter r5 = new java.io.FileWriter     // Catch: java.lang.Exception -> Lb9
            java.lang.Object r6 = r1.get(r4)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Exception -> Lb9
            r5.<init>(r6)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r6 = "FF"
            r5.write(r6)     // Catch: java.lang.Throwable -> Lb3
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> Lb3
            java.lang.Object r7 = r2.get(r4)     // Catch: java.lang.Throwable -> Lb3
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Throwable -> Lb3
            r6.<init>(r7)     // Catch: java.lang.Throwable -> Lb3
            boolean r6 = r6.delete()     // Catch: java.lang.Throwable -> Lb3
            if (r6 == 0) goto Lb5
            java.lang.String r6 = "!@[MasterClearReceiver]removeFirstUseDate)erase FirstUseDate Success"
            android.util.Slog.i(r9, r6)     // Catch: java.lang.Throwable -> Lb3
            goto Lb5
        Lb3:
            r6 = move-exception
            goto Lbb
        Lb5:
            r5.close()     // Catch: java.lang.Exception -> Lb9
            goto Lcc
        Lb9:
            r5 = move-exception
            goto Lc4
        Lbb:
            r5.close()     // Catch: java.lang.Throwable -> Lbf
            goto Lc3
        Lbf:
            r5 = move-exception
            r6.addSuppressed(r5)     // Catch: java.lang.Exception -> Lb9
        Lc3:
            throw r6     // Catch: java.lang.Exception -> Lb9
        Lc4:
            java.lang.String r6 = "!@[MasterClearReceiver]removeFirstUseDate)write Exception"
            android.util.Slog.w(r9, r6)
            r5.printStackTrace()
        Lcc:
            int r4 = r4 + 1
            goto L39
        Ld0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.MasterClearReceiver.m68$$Nest$mremoveFirstUseDate(com.android.server.MasterClearReceiver):void");
    }

    /* renamed from: -$$Nest$mremoveVZWResetDate, reason: not valid java name */
    public static void m69$$Nest$mremoveVZWResetDate(MasterClearReceiver masterClearReceiver) {
        masterClearReceiver.getClass();
        try {
            File file = new File("/efs/sec_efs/LastResetDate.txt");
            if (file.exists()) {
                Slog.d("MasterClear", "!@[MasterClearReceiver] removeVZWResetDate: Try to delete VZW Reset Date file");
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: -$$Nest$smdeleteOmrDir, reason: not valid java name */
    public static void m70$$Nest$smdeleteOmrDir(String str) {
        File file = new File(str);
        if (file.exists()) {
            Slog.d("MasterClear", "!@[MasterClearReceiver] Delete OMR path !!! : ".concat(str));
            if (deleteDir(file)) {
                return;
            }
            Slog.d("MasterClear", "!@[MasterClearReceiver] Fail to delete OMR path !!! : ".concat(str));
        }
    }

    public static boolean deleteDir(File file) {
        Slog.d("MasterClear", "!@[MasterClearReceiver] Remove File : " + file);
        if (file.isDirectory() && file.listFiles() != null) {
            for (File file2 : file.listFiles()) {
                deleteDir(file2);
            }
        }
        return file.delete();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        Slog.w("MasterClear", "!@[MasterClearReceiver] onReceive: intent:" + intent);
        if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE") && !"google.com".equals(intent.getStringExtra("from"))) {
            Slog.w("MasterClear", "Ignoring master clear request -- not from trusted server.");
            return;
        }
        if ("android.intent.action.MASTER_CLEAR".equals(intent.getAction())) {
            Slog.w("MasterClear", "The request uses the deprecated Intent#ACTION_MASTER_CLEAR, Intent#ACTION_FACTORY_RESET should be used instead.");
        }
        if (intent.hasExtra("android.intent.extra.FORCE_MASTER_CLEAR")) {
            Slog.w("MasterClear", "The request uses the deprecated Intent#EXTRA_FORCE_MASTER_CLEAR, Intent#EXTRA_FORCE_FACTORY_RESET should be used instead.");
        }
        String string = context.getString(R.string.duration_hours_relative);
        if ("android.intent.action.FACTORY_RESET".equals(intent.getAction()) && !TextUtils.isEmpty(string)) {
            Slog.i("MasterClear", "Re-directing intent to " + string);
            intent.setPackage(string).setComponent(null);
            context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
            return;
        }
        final String stringExtra = intent.getStringExtra("android.intent.extra.REASON");
        this.mShowWipeProgress = intent.getBooleanExtra("android.intent.extra.SHOW_WIPE_PROGRESS", true);
        final boolean booleanExtra = intent.getBooleanExtra("shutdown", false);
        this.mWipeExternalStorage = intent.getBooleanExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", false);
        this.mWipeEsims = intent.getBooleanExtra("com.android.internal.intent.extra.WIPE_ESIMS", false);
        final boolean z = intent.getBooleanExtra("android.intent.extra.FORCE_MASTER_CLEAR", false) || intent.getBooleanExtra("android.intent.extra.FORCE_FACTORY_RESET", false);
        final boolean booleanExtra2 = intent.getBooleanExtra("keep_memtag_mode", false);
        if (stringExtra == null) {
            Slog.w("MasterClear", "!@[MasterClearReceiver] onReceive: Ignoring FACTORY_RESET request -- reason cannot be null.");
            return;
        }
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: mShowWipeProgress: " + this.mShowWipeProgress);
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: shutdown: " + booleanExtra);
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: reason: ".concat(stringExtra));
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: mWipeExternalStorage: " + this.mWipeExternalStorage);
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: mWipeEsims: " + this.mWipeEsims);
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: forceWipe: " + z);
        Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: keepMemtagMode: " + booleanExtra2);
        this.extraCmd = intent.getStringExtra("EXTRA_COMMAND");
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("!@[MasterClearReceiver] onReceive: extraCmd is ["), this.extraCmd, "]", "MasterClear");
        if (this.extraCmd == null) {
            Slog.d("MasterClear", "!@[MasterClearReceiver] onReceive: extraCmd is null");
        }
        this.requestedTimeArg = intent.getStringExtra("com.android.internal.intent.extra.FDR_REQUEST_TIME");
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("!@[MasterClearReceiver] onReceive: requestedTimeArg is ["), this.requestedTimeArg, "]", "MasterClear");
        if (this.requestedTimeArg == null) {
            long currentTimeMillis = System.currentTimeMillis();
            this.requestedTimeArg = "requested_time=" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date(currentTimeMillis));
        }
        String str = this.requestedTimeArg;
        if (str != null) {
            if (this.extraCmd != null) {
                this.extraCmd += "\n--" + this.requestedTimeArg;
            } else {
                this.extraCmd = str;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("!@[MasterClearReceiver] onReceive: requested time is added to extraCmd, extraCmd is ["), this.extraCmd, "]", "MasterClear");
        }
        final int sendingUserId = getSendingUserId();
        if (sendingUserId != 0 && !UserManager.isHeadlessSystemUserMode()) {
            Slogf.w("MasterClear", "ACTION_FACTORY_RESET received on a non-system user %d, WIPING THE USER!!", Integer.valueOf(sendingUserId));
            if (((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.MasterClearReceiver$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                    int i;
                    MasterClearReceiver masterClearReceiver = MasterClearReceiver.this;
                    final Context context2 = context;
                    int i2 = sendingUserId;
                    String str2 = stringExtra;
                    int i3 = MasterClearReceiver.$r8$clinit;
                    masterClearReceiver.getClass();
                    UserManager userManager = (UserManager) context2.getSystemService(UserManager.class);
                    boolean z2 = false;
                    if (UserManager.isRemoveResultSuccessful(userManager.removeUserWhenPossible(UserHandle.of(i2), false))) {
                        try {
                            i = ActivityManager.getCurrentUser();
                        } catch (Exception e) {
                            Slogf.e("MasterClear", "Can't get current user", e);
                            i = -10000;
                        }
                        if (i == i2) {
                            try {
                                if (!ActivityManager.getService().switchUser(0)) {
                                    Slogf.w("MasterClear", "Can't switch from current user %d, user will get removed when it is stopped.", Integer.valueOf(i2));
                                }
                            } catch (RemoteException unused) {
                                Slogf.w("MasterClear", "Can't switch from current user %d, user will get removed when it is stopped.", Integer.valueOf(i2));
                            }
                        }
                        if (userManager.isManagedProfile(i2)) {
                            ((NotificationManager) context2.getSystemService(NotificationManager.class)).notify(1001, new Notification.Builder(context2, SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(R.drawable.stat_sys_warning).setContentTitle(((DevicePolicyManager) context2.getSystemService(DevicePolicyManager.class)).getResources().getString("Core.WORK_PROFILE_DELETED_TITLE", new Supplier() { // from class: com.android.server.MasterClearReceiver$$ExternalSyntheticLambda1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    Context context3 = context2;
                                    int i4 = MasterClearReceiver.$r8$clinit;
                                    return context3.getString(17043633);
                                }
                            })).setContentText(str2).setColor(context2.getColor(R.color.system_notification_accent_color)).setStyle(new Notification.BigTextStyle().bigText(str2)).build());
                        }
                        z2 = true;
                    } else {
                        Slogf.e("MasterClear", "Can't remove user %d", Integer.valueOf(i2));
                    }
                    return Boolean.valueOf(z2);
                }
            })).booleanValue()) {
                return;
            }
            Slogf.e("MasterClear", "Failed to wipe user %d", Integer.valueOf(sendingUserId));
            return;
        }
        Slog.w("MasterClear", "!@[MasterClearReceiver] onReceive: !!! FACTORY RESET !!!");
        ?? r0 = new Thread() { // from class: com.android.server.MasterClearReceiver.1
            {
                super("Reboot");
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    if (intent.hasExtra("FactoryResetByCommand")) {
                        Slog.w("MasterClear", "!@[MasterClearReceiver] thr.run: FactoryResetByATCommand");
                        MasterClearReceiver.m69$$Nest$mremoveVZWResetDate(MasterClearReceiver.this);
                    }
                    MasterClearReceiver.m70$$Nest$smdeleteOmrDir("/omr/carrier");
                    MasterClearReceiver.m70$$Nest$smdeleteOmrDir("/omr/update");
                    MasterClearReceiver.m70$$Nest$smdeleteOmrDir("/omr/res");
                    MasterClearReceiver.m70$$Nest$smdeleteOmrDir("/omr/temp");
                    MasterClearReceiver.m68$$Nest$mremoveFirstUseDate(MasterClearReceiver.this);
                    if (intent.hasExtra("CustomWipe")) {
                        Slog.w("MasterClear", "!@[MasterClearReceiver] thr.run: !!!call wipe customer!!!");
                        String str2 = new String("");
                        if (intent.hasExtra("args")) {
                            str2 = str2 + intent.getExtras().get("args").toString();
                        }
                        RecoverySystem.rebootWipeCustomerPartition(context, str2 + "--locale=" + Locale.getDefault().toString(), stringExtra);
                    } else if (intent.hasExtra("WipeCustomerPartiotion")) {
                        Slog.w("MasterClear", "!@[MasterClearReceiver] thr.run: !!!WipeData and WipeCustomerPartiotion!!!");
                        RecoverySystem.rebootWipeCustomerPartition(context, "--wipe_data\n--wipe_carrier\n--locale=" + Locale.getDefault().toString(), stringExtra);
                    } else if (intent.hasExtra("WipeCache")) {
                        Slog.w("MasterClear", "!@[MasterClearReceiver] thr.run: !!!Just Exit (For Bypass Factory Reset)!!!");
                        RecoverySystem.rebootWipeCustomerPartition(context, "--just_exit\n--locale=" + Locale.ROOT.toString(), stringExtra);
                    } else if (intent.hasExtra("Download")) {
                        Slog.w("MasterClear", "!@[MasterClearReceiver] thr.run: !!!Enter the Download Mode for Factory Routine!!!");
                        RecoverySystem.rebootWipeCustomerPartition(context, "", stringExtra);
                    } else {
                        Slog.w("MasterClear", "!@[MasterClearReceiver] thr.run: !!!No hasExtra, just call rebootWipeUserData!!!");
                        RecoverySystem.rebootWipeUserData(context, booleanExtra, stringExtra, z, false, booleanExtra2, MasterClearReceiver.this.extraCmd);
                    }
                    Slog.wtf("MasterClear", "!@[MasterClearReceiver] thr.run: Still running after master clear?!");
                } catch (FileNotFoundException e) {
                    Slog.e("MasterClear", "!@[MasterClearReceiver] thr.run: Can't perform master clear/factory reset", e);
                } catch (IOException e2) {
                    Slog.e("MasterClear", "!@[MasterClearReceiver] thr.run: Can't perform master clear/factory reset", e2);
                } catch (SecurityException e3) {
                    Slog.e("MasterClear", "!@[MasterClearReceiver] thr.run: Can't perform master clear/factory reset", e3);
                }
            }
        };
        if (this.mWipeExternalStorage) {
            Slog.i("MasterClear", "Wiping external storage on async task");
            new WipeDataTask(context, r0).execute(new Void[0]);
        } else {
            Slog.i("MasterClear", "NOT wiping external storage; starting thread " + r0.getName());
            r0.start();
        }
    }
}
