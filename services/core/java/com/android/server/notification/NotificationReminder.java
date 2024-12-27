package com.android.server.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.IRingtonePlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.util.Log;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.sepunion.SemGoodCatchManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationReminder {
    public final AlarmManager mAlarmManager;
    public String mAppSettingList;
    public final AudioManager mAudioManager;
    public final Context mContext;
    public boolean mEnableReminder;
    public boolean mEnableVibrate;
    public final AnonymousClass2 mHandler;
    public int mInterval;
    public final boolean mNeedMigration;
    public final PreferencesHelper mPreferencesHelper;
    public PendingIntent mReminderIntent;
    public final AnonymousClass1 mReminderReceiver;
    public final VibratorHelper mVibratorHelper;
    public static final Intent notificationIntent = new Intent("com.samsung.action.Notification_Reminder_Alarm").setPackage("android");
    public static final VibrationEffect REMINDER_VIBRATION_EFFECT = VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(7), -1, VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
    public final ArrayList mActiveNotiList = new ArrayList();
    public int mReminderType = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationReminder$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ NotificationReminder this$0;
        public final /* synthetic */ NotificationRecord val$record;

        public /* synthetic */ AnonymousClass3(NotificationReminder notificationReminder, NotificationRecord notificationRecord, int i) {
            this.$r8$classId = i;
            this.this$0 = notificationReminder;
            this.val$record = notificationRecord;
        }

        @Override // java.lang.Runnable
        public final void run() {
            NotificationRecord notificationRecord;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mActiveNotiList) {
                        Slog.d("NotificationReminder", "addNotificationRecord record " + this.val$record.sbn.getPackageName());
                        this.this$0.mActiveNotiList.add(this.val$record);
                        this.this$0.sendMessage(1000);
                    }
                    return;
                default:
                    synchronized (this.this$0.mActiveNotiList) {
                        Slog.d("NotificationReminder", "removeFromNotificationList record " + this.val$record.sbn.getPackageName());
                        NotificationReminder notificationReminder = this.this$0;
                        ArrayList arrayList = notificationReminder.mActiveNotiList;
                        String key = this.val$record.sbn.getKey();
                        notificationReminder.getClass();
                        int size = arrayList.size();
                        int i = 0;
                        while (true) {
                            if (i >= size) {
                                notificationRecord = null;
                            } else if (key.equals(((NotificationRecord) arrayList.get(i)).sbn.getKey())) {
                                notificationRecord = (NotificationRecord) arrayList.get(i);
                            } else {
                                i++;
                            }
                        }
                        this.this$0.mActiveNotiList.remove(notificationRecord);
                        this.this$0.sendMessage(1001);
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationReminder$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ NotificationReminder this$0;

        public /* synthetic */ AnonymousClass5(NotificationReminder notificationReminder, int i) {
            this.$r8$classId = i;
            this.this$0 = notificationReminder;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.new UpdateEnablePackageTask().execute(new Void[0]);
                    break;
                default:
                    this.this$0.new UpdateEnablePackageTask().execute(new Void[0]);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationReminderObserver extends ContentObserver {
        public final Uri NOTIFICATION_REMINDER_APP_LIST;
        public final Uri NOTIFICATION_REMINDER_SELECTABLE;
        public final Uri NOTIFICATION_REMINDER_VIBRATE;
        public final Uri REMINDER_TYPE;
        public final Uri TIME_KEY;

        public NotificationReminderObserver(AnonymousClass2 anonymousClass2) {
            super(anonymousClass2);
            this.NOTIFICATION_REMINDER_SELECTABLE = Settings.System.getUriFor("notification_reminder_selectable");
            this.REMINDER_TYPE = Settings.System.getUriFor("notification_reminder_type");
            this.NOTIFICATION_REMINDER_APP_LIST = Settings.System.getUriFor("notification_reminder_app_list");
            this.NOTIFICATION_REMINDER_VIBRATE = Settings.System.getUriFor("notification_reminder_vibrate");
            this.TIME_KEY = Settings.System.getUriFor("time_key");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            update(uri);
        }

        public final void update(Uri uri) {
            Slog.d("NotificationReminder", "update uri: " + uri);
            ContentResolver contentResolver = NotificationReminder.this.mContext.getContentResolver();
            if (uri == null || this.NOTIFICATION_REMINDER_SELECTABLE.equals(uri)) {
                NotificationReminder.this.mEnableReminder = Settings.System.getIntForUser(contentResolver, "notification_reminder_selectable", 0, -2) != 0;
                AnyMotionDetector$$ExternalSyntheticOutline0.m("NotificationReminder", new StringBuilder("update mEnableReminder: "), NotificationReminder.this.mEnableReminder);
                NotificationReminder notificationReminder = NotificationReminder.this;
                if (!notificationReminder.mEnableReminder) {
                    NotificationReminder.m730$$Nest$mcancelAlarm(notificationReminder);
                } else if (NotificationReminder.m731$$Nest$misRemindNeeded(notificationReminder, notificationReminder.mActiveNotiList)) {
                    NotificationReminder.m733$$Nest$msetReminderAlarm(NotificationReminder.this);
                }
            }
            if (uri == null || this.NOTIFICATION_REMINDER_VIBRATE.equals(uri)) {
                NotificationReminder.this.mEnableVibrate = Settings.System.getIntForUser(contentResolver, "notification_reminder_vibrate", 0, -2) != 0;
                AnyMotionDetector$$ExternalSyntheticOutline0.m("NotificationReminder", new StringBuilder("update mEnableVibrate: "), NotificationReminder.this.mEnableVibrate);
            }
            if (uri == null || this.TIME_KEY.equals(uri)) {
                NotificationReminder.this.mInterval = Settings.System.getIntForUser(contentResolver, "time_key", 180, -2);
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("update mInterval: "), NotificationReminder.this.mInterval, "NotificationReminder");
                if (uri != null) {
                    post(new AnonymousClass1.RunnableC00191(1, this));
                }
            }
            if (uri == null || this.REMINDER_TYPE.equals(uri)) {
                NotificationReminder.this.mReminderType = Settings.System.getIntForUser(contentResolver, "notification_reminder_type", -1, -2);
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("update mReminderType: "), NotificationReminder.this.mReminderType, "NotificationReminder");
            }
            if (uri == null || this.NOTIFICATION_REMINDER_APP_LIST.equals(uri)) {
                NotificationReminder.this.mAppSettingList = Settings.System.getStringForUser(contentResolver, "notification_reminder_app_list", -2);
                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("update mAppSettingList: "), NotificationReminder.this.mAppSettingList, "NotificationReminder");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpdateEnablePackageTask extends AsyncTask {
        public UpdateEnablePackageTask() {
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String str;
            Log.d("NotificationReminder", "updateEnablePackageList start");
            NotificationReminder notificationReminder = NotificationReminder.this;
            int i = notificationReminder.mReminderType;
            if (i == 0) {
                Context context = notificationReminder.mContext;
                ArrayList arrayList = new ArrayList();
                arrayList.add("com.samsung.android.messaging");
                arrayList.add("com.samsung.android.dialer");
                String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(context);
                Log.d("NotificationReminder", "defaultSms : " + defaultSmsPackage);
                arrayList.add(defaultSmsPackage);
                try {
                    String defaultDialerPackage = ((TelecomManager) context.getSystemService(TelecomManager.class)).getDefaultDialerPackage();
                    Log.d("NotificationReminder", "defaultDialer : " + defaultDialerPackage);
                    arrayList.add(defaultDialerPackage);
                } catch (Exception e) {
                    Log.e("NotificationReminder", "Unexpected exception occurred when getting default dialer package.", e);
                }
                StringBuffer stringBuffer = new StringBuffer();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (stringBuffer.length() != 0) {
                        stringBuffer.append(";");
                    }
                    stringBuffer.append(str2);
                }
                str = stringBuffer.toString();
            } else {
                str = i == 1 ? notificationReminder.mAppSettingList : "";
            }
            PackageManager packageManager = notificationReminder.mContext.getPackageManager();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            boolean equalsIgnoreCase = "selectAll".equalsIgnoreCase(str);
            PreferencesHelper preferencesHelper = notificationReminder.mPreferencesHelper;
            if (equalsIgnoreCase) {
                Iterator<ResolveInfo> it2 = queryIntentActivities.iterator();
                while (it2.hasNext()) {
                    ActivityInfo activityInfo = it2.next().activityInfo;
                    String str3 = activityInfo.name;
                    String str4 = activityInfo.packageName;
                    DualAppManagerService$$ExternalSyntheticOutline0.m("updateEnablePackageList SELECT_All pkg = ", str4, "NotificationReminder");
                    if (str3 != null) {
                        try {
                            preferencesHelper.setReminderEnabled(packageManager.getPackageUidAsUser(str4, ActivityManager.getCurrentUser()), str4, true);
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                    }
                }
            } else if (str != null) {
                ArrayList arrayList2 = new ArrayList();
                Iterator<ResolveInfo> it3 = queryIntentActivities.iterator();
                while (it3.hasNext()) {
                    arrayList2.add(it3.next().activityInfo.packageName);
                }
                List<String> asList = Arrays.asList(str.split(";"));
                if (asList.size() > 0) {
                    preferencesHelper.removeAllReminderSetting(ActivityManager.getCurrentUser());
                    for (String str5 : asList) {
                        DualAppManagerService$$ExternalSyntheticOutline0.m("updateEnablePackageList : ", str5, "NotificationReminder");
                        try {
                            if (arrayList2.contains(str5)) {
                                Log.d("NotificationReminder", "installPackages : " + str5);
                                preferencesHelper.setReminderEnabled(notificationReminder.mContext.getPackageManager().getPackageUidAsUser(str5, ActivityManager.getCurrentUser()), str5, true);
                            }
                        } catch (PackageManager.NameNotFoundException unused2) {
                        }
                    }
                }
            }
            if (notificationReminder.mNeedMigration) {
                Settings.Secure.putIntForUser(notificationReminder.mContext.getContentResolver(), "reminder_migration_finished", 1, -2);
            }
            Log.d("NotificationReminder", "updateEnablePackageList finish");
            return null;
        }
    }

    /* renamed from: -$$Nest$mcancelAlarm, reason: not valid java name */
    public static void m730$$Nest$mcancelAlarm(NotificationReminder notificationReminder) {
        notificationReminder.getClass();
        Log.d("NotificationReminder", "cancelAlarm");
        if (notificationReminder.mReminderIntent != null) {
            Log.d("NotificationReminder", "cancelAlarm - cancel Alarm");
            notificationReminder.mAlarmManager.cancel(notificationReminder.mReminderIntent);
            notificationReminder.mReminderIntent = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$misRemindNeeded, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m731$$Nest$misRemindNeeded(com.android.server.notification.NotificationReminder r7, java.util.ArrayList r8) {
        /*
            r7.getClass()
            boolean r0 = r8.isEmpty()
            java.lang.String r1 = "NotificationReminder"
            r2 = 0
            if (r0 == 0) goto L13
            java.lang.String r7 = "The active notification list is empty."
            android.util.Log.d(r1, r7)
            goto L93
        L13:
            java.util.Iterator r8 = r8.iterator()
        L17:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L93
            java.lang.Object r0 = r8.next()
            com.android.server.notification.NotificationRecord r0 = (com.android.server.notification.NotificationRecord) r0
            android.service.notification.StatusBarNotification r3 = r0.sbn
            android.app.Notification r3 = r3.getNotification()
            int r4 = r3.flags
            r5 = r4 & 512(0x200, float:7.175E-43)
            if (r5 == 0) goto L30
            goto L17
        L30:
            r4 = r4 & 4096(0x1000, float:5.74E-42)
            if (r4 == 0) goto L48
            android.app.Notification$BubbleMetadata r4 = r3.getBubbleMetadata()
            if (r4 == 0) goto L43
            android.app.Notification$BubbleMetadata r3 = r3.getBubbleMetadata()
            boolean r3 = r3.isNotificationSuppressed()
            goto L49
        L43:
            java.lang.String r3 = "bubbleMetadata is null."
            android.util.Log.w(r1, r3)
        L48:
            r3 = r2
        L49:
            if (r3 != 0) goto L17
            android.service.notification.StatusBarNotification r3 = r0.sbn
            boolean r3 = r3.isClearable()
            if (r3 == 0) goto L17
            boolean r3 = r0.mIntercept
            if (r3 != 0) goto L17
            android.service.notification.StatusBarNotification r3 = r0.sbn
            java.lang.String r3 = r3.getPackageName()
            android.service.notification.StatusBarNotification r4 = r0.sbn
            int r4 = r4.getUid()
            com.android.server.notification.PreferencesHelper r5 = r7.mPreferencesHelper
            java.lang.Object r6 = r5.mLock
            monitor-enter(r6)
            com.android.server.notification.PreferencesHelper$PackagePreferences r3 = r5.getPackagePreferencesLocked(r4, r3)     // Catch: java.lang.Throwable -> L72
            if (r3 == 0) goto L74
            boolean r3 = r3.reminder     // Catch: java.lang.Throwable -> L72
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L72
            goto L76
        L72:
            r7 = move-exception
            goto L91
        L74:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L72
            r3 = r2
        L76:
            if (r3 == 0) goto L17
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Clearable checked item found: "
            r7.<init>(r8)
            android.service.notification.StatusBarNotification r8 = r0.sbn
            java.lang.String r8 = r8.getPackageName()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.d(r1, r7)
            r2 = 1
            goto L93
        L91:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L72
            throw r7
        L93:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationReminder.m731$$Nest$misRemindNeeded(com.android.server.notification.NotificationReminder, java.util.ArrayList):boolean");
    }

    /* renamed from: -$$Nest$mplaySoundVibration, reason: not valid java name */
    public static void m732$$Nest$mplaySoundVibration(NotificationReminder notificationReminder) {
        long clearCallingIdentity;
        int ringerModeInternal = notificationReminder.mAudioManager.getRingerModeInternal();
        VibratorHelper vibratorHelper = notificationReminder.mVibratorHelper;
        if (ringerModeInternal == 1) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d("NotificationReminder", "playVibration only");
                vibratorHelper.vibrate(REMINDER_VIBRATION_EFFECT, Notification.AUDIO_ATTRIBUTES_DEFAULT, "NotificationReminder(com.android.settings)");
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (notificationReminder.mAudioManager.getRingerModeInternal() == 2) {
            AudioManager audioManager = notificationReminder.mAudioManager;
            AudioAttributes audioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
            if (audioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(audioAttributes)) > 0) {
                Uri actualDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(notificationReminder.mContext, 2) != null ? RingtoneManager.getActualDefaultRingtoneUri(notificationReminder.mContext, 2) : RingtoneManager.getDefaultUri(2);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    IRingtonePlayer ringtonePlayer = notificationReminder.mAudioManager.getRingtonePlayer();
                    if (ringtonePlayer != null) {
                        Log.d("NotificationReminder", "playSound");
                        ringtonePlayer.playAsync(actualDefaultRingtoneUri, UserHandle.ALL, false, audioAttributes, 1.0f);
                        SemGoodCatchManager semGoodCatchManager = NotificationAttentionHelper.mSemAudioGoodCatchManager;
                        if (semGoodCatchManager != null) {
                            semGoodCatchManager.update("playback", KnoxCustomManagerService.SETTING_PKG_NAME, AudioAttributes.toLegacyStreamType(audioAttributes), "", "repeat_notification_alerts");
                        } else {
                            Log.w("NotificationReminder", "SemAudioGoodCatchManager has not created");
                        }
                    }
                    if (notificationReminder.mEnableVibrate) {
                        Log.d("NotificationReminder", "playVibration");
                        vibratorHelper.vibrate(REMINDER_VIBRATION_EFFECT, audioAttributes, "NotificationReminder(com.android.settings)");
                    }
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$msetReminderAlarm, reason: not valid java name */
    public static void m733$$Nest$msetReminderAlarm(NotificationReminder notificationReminder) {
        if (notificationReminder.mReminderIntent != null) {
            Slog.e("NotificationReminder", "Reminder alarm is exist before calling setReminder Alarm.");
            return;
        }
        Slog.i("NotificationReminder", "setReminderAlarm");
        notificationReminder.mReminderIntent = PendingIntent.getBroadcast(notificationReminder.mContext, 0, notificationIntent, 301989888);
        if (notificationReminder.mInterval < 180) {
            notificationReminder.mInterval = 180;
            Settings.System.putIntForUser(notificationReminder.mContext.getContentResolver(), "time_key", notificationReminder.mInterval, -2);
            Slog.w("NotificationReminder", "The interval is set to the wrong value, so it is reset to the default interval value.(180 sec).");
        }
        if (notificationReminder.mAlarmManager.canScheduleExactAlarms()) {
            notificationReminder.mAlarmManager.setExactAndAllowWhileIdle(2, (notificationReminder.mInterval * 1000) + SystemClock.elapsedRealtime(), notificationReminder.mReminderIntent);
        } else {
            notificationReminder.mAlarmManager.setAndAllowWhileIdle(2, (notificationReminder.mInterval * 1000) + SystemClock.elapsedRealtime(), notificationReminder.mReminderIntent);
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [android.os.Handler, com.android.server.notification.NotificationReminder$2] */
    public NotificationReminder(Context context, Looper looper, PreferencesHelper preferencesHelper, AlarmManager alarmManager) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.NotificationReminder.1

            /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
            /* renamed from: com.android.server.notification.NotificationReminder$1$1, reason: invalid class name and collision with other inner class name */
            public final class RunnableC00191 implements Runnable {
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ Object this$1;

                public /* synthetic */ RunnableC00191(int i, Object obj) {
                    this.$r8$classId = i;
                    this.this$1 = obj;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (this.$r8$classId) {
                        case 0:
                            synchronized (NotificationReminder.this.mActiveNotiList) {
                                try {
                                    NotificationReminder notificationReminder = NotificationReminder.this;
                                    if (NotificationReminder.m731$$Nest$misRemindNeeded(notificationReminder, notificationReminder.mActiveNotiList)) {
                                        NotificationReminder.m732$$Nest$mplaySoundVibration(NotificationReminder.this);
                                        NotificationReminder.m730$$Nest$mcancelAlarm(NotificationReminder.this);
                                        NotificationReminder.m733$$Nest$msetReminderAlarm(NotificationReminder.this);
                                    } else {
                                        NotificationReminder.m730$$Nest$mcancelAlarm(NotificationReminder.this);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            return;
                        default:
                            synchronized (NotificationReminder.this.mActiveNotiList) {
                                NotificationReminder.this.sendMessage(1004);
                            }
                            return;
                    }
                }
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Slog.d("NotificationReminder", "mReminderReceiver, action = " + action);
                if (action.equals("com.samsung.action.Notification_Reminder_Alarm")) {
                    post(new RunnableC00191(0, this));
                }
            }
        };
        this.mContext = context;
        ?? r2 = new Handler(looper) { // from class: com.android.server.notification.NotificationReminder.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                NotificationReminder notificationReminder = NotificationReminder.this;
                switch (i) {
                    case 1000:
                    case 1002:
                        if (notificationReminder.mEnableReminder && NotificationReminder.m731$$Nest$misRemindNeeded(notificationReminder, notificationReminder.mActiveNotiList)) {
                            NotificationReminder.m733$$Nest$msetReminderAlarm(notificationReminder);
                            break;
                        }
                        break;
                    case 1001:
                    case 1003:
                        if (notificationReminder.mEnableReminder && !NotificationReminder.m731$$Nest$misRemindNeeded(notificationReminder, notificationReminder.mActiveNotiList)) {
                            NotificationReminder.m730$$Nest$mcancelAlarm(notificationReminder);
                            break;
                        }
                        break;
                    case 1004:
                        if (notificationReminder.mEnableReminder) {
                            NotificationReminder.m730$$Nest$mcancelAlarm(notificationReminder);
                            NotificationReminder.m733$$Nest$msetReminderAlarm(notificationReminder);
                            break;
                        }
                        break;
                }
            }
        };
        this.mHandler = r2;
        this.mPreferencesHelper = preferencesHelper;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mVibratorHelper = new VibratorHelper(context);
        this.mAlarmManager = alarmManager;
        NotificationReminderObserver notificationReminderObserver = new NotificationReminderObserver(r2);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("notification_reminder_selectable"), false, notificationReminderObserver, -1);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("notification_reminder_type"), false, notificationReminderObserver, -1);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("notification_reminder_app_list"), false, notificationReminderObserver, -1);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("notification_reminder_vibrate"), false, notificationReminderObserver, -1);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_key"), false, notificationReminderObserver, -1);
        notificationReminderObserver.update(null);
        IntentFilter intentFilter = new IntentFilter("com.samsung.action.Notification_Reminder_Alarm");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        if (this.mReminderType != 1) {
            preferencesHelper.removeAllReminderSetting(ActivityManager.getCurrentUser());
        }
        boolean z = Settings.Secure.getIntForUser(context.getContentResolver(), "reminder_migration_finished", 0, -2) == 0;
        this.mNeedMigration = z;
        if (z) {
            r2.postDelayed(new AnonymousClass5(this, 1), 30000L);
        }
    }

    public final void addNotificationRecord(NotificationRecord notificationRecord) {
        post(new AnonymousClass3(this, notificationRecord, 0));
    }

    public final void dump(PrintWriter printWriter) {
        int size = this.mActiveNotiList.size();
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "\n  NotificationReminder :", "      EnableReminder = "), this.mEnableReminder, printWriter, "      Interval = "), this.mInterval, printWriter, "      EnableVibrate = "), this.mEnableVibrate, printWriter);
        if (this.mEnableReminder) {
            AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter, "      mActiveNotiList, size = ", size);
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    printWriter.println("      key : " + ((NotificationRecord) this.mActiveNotiList.get(i)).sbn.getKey());
                }
                printWriter.println("  ");
            }
        }
    }

    public final void sendMessage(int i) {
        AnonymousClass2 anonymousClass2 = this.mHandler;
        if (anonymousClass2.hasMessages(i)) {
            anonymousClass2.removeMessages(i);
        }
        anonymousClass2.sendMessage(anonymousClass2.obtainMessage(i));
    }
}
