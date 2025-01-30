package com.android.systemui.people.widget;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.people.IPeopleManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.people.PeopleBackupFollowUpJob;
import com.android.systemui.people.SharedPreferencesHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleBackupHelper extends SharedPreferencesBackupHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppWidgetManager mAppWidgetManager;
    public final Context mContext;
    public final IPeopleManager mIPeopleManager;
    public final PackageManager mPackageManager;
    public final UserHandle mUserHandle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.people.widget.PeopleBackupHelper$1 */
    public abstract /* synthetic */ class AbstractC19131 {

        /* renamed from: $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType */
        public static final /* synthetic */ int[] f327xd18c5f3a;

        static {
            int[] iArr = new int[SharedFileEntryType.values().length];
            f327xd18c5f3a = iArr;
            try {
                iArr[SharedFileEntryType.WIDGET_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f327xd18c5f3a[SharedFileEntryType.PEOPLE_TILE_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f327xd18c5f3a[SharedFileEntryType.CONTACT_URI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f327xd18c5f3a[SharedFileEntryType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum SharedFileEntryType {
        UNKNOWN,
        WIDGET_ID,
        PEOPLE_TILE_KEY,
        CONTACT_URI
    }

    public PeopleBackupHelper(Context context, UserHandle userHandle, String[] strArr) {
        super(context, strArr);
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mPackageManager = context.getPackageManager();
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
    }

    public static SharedFileEntryType getEntryType(Map.Entry entry) {
        String str = (String) entry.getKey();
        if (str == null) {
            return SharedFileEntryType.UNKNOWN;
        }
        try {
            try {
                Integer.parseInt(str);
                try {
                    return SharedFileEntryType.WIDGET_ID;
                } catch (Exception unused) {
                    Log.w("PeopleBackupHelper", "Malformed value, skipping:" + entry.getValue());
                    return SharedFileEntryType.UNKNOWN;
                }
            } catch (NumberFormatException unused2) {
                if (PeopleTileKey.fromString(str) != null) {
                    return SharedFileEntryType.PEOPLE_TILE_KEY;
                }
                try {
                    Uri.parse(str);
                    return SharedFileEntryType.CONTACT_URI;
                } catch (Exception unused3) {
                    return SharedFileEntryType.UNKNOWN;
                }
            }
        } catch (Exception unused4) {
            Log.w("PeopleBackupHelper", "Malformed value, skipping:" + entry.getValue());
            return SharedFileEntryType.UNKNOWN;
        }
    }

    public static void updateWidgets(Context context) {
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, (Class<?>) PeopleSpaceWidgetProvider.class));
        if (appWidgetIds == null || appWidgetIds.length == 0) {
            return;
        }
        Intent intent = new Intent(context, (Class<?>) PeopleSpaceWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("appWidgetIds", appWidgetIds);
        context.sendBroadcast(intent);
    }

    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        if (defaultSharedPreferences.getAll().isEmpty()) {
            return;
        }
        final SharedPreferences.Editor edit = this.mContext.getSharedPreferences("shared_backup", 0).edit();
        edit.clear();
        int identifier = this.mUserHandle.getIdentifier();
        final ArrayList arrayList = new ArrayList();
        for (int i : this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, (Class<?>) PeopleSpaceWidgetProvider.class))) {
            String valueOf = String.valueOf(i);
            if (this.mContext.getSharedPreferences(valueOf, 0).getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1) == identifier) {
                arrayList.add(valueOf);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        defaultSharedPreferences.getAll().entrySet().forEach(new Consumer() { // from class: com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PeopleBackupHelper peopleBackupHelper = PeopleBackupHelper.this;
                SharedPreferences.Editor editor = edit;
                final List list = arrayList;
                Map.Entry entry = (Map.Entry) obj;
                peopleBackupHelper.getClass();
                String str = (String) entry.getKey();
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                int i2 = PeopleBackupHelper.AbstractC19131.f327xd18c5f3a[PeopleBackupHelper.getEntryType(entry).ordinal()];
                if (i2 == 1) {
                    String valueOf2 = String.valueOf(entry.getValue());
                    if (list.contains(str)) {
                        Uri parse = Uri.parse(valueOf2);
                        if (ContentProvider.uriHasUserId(parse)) {
                            editor.putInt("add_user_id_to_uri_" + str, ContentProvider.getUserIdFromUri(parse));
                            parse = ContentProvider.getUriWithoutUserId(parse);
                        }
                        editor.putString(str, parse.toString());
                        return;
                    }
                    return;
                }
                if (i2 == 2) {
                    Set set = (Set) entry.getValue();
                    PeopleTileKey fromString = PeopleTileKey.fromString(str);
                    if (fromString.mUserId != peopleBackupHelper.mUserHandle.getIdentifier()) {
                        return;
                    }
                    Set<String> set2 = (Set) set.stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            return list.contains((String) obj2);
                        }
                    }).collect(Collectors.toSet());
                    if (set2.isEmpty()) {
                        return;
                    }
                    fromString.mUserId = -1;
                    editor.putStringSet(fromString.toString(), set2);
                    return;
                }
                if (i2 != 3) {
                    MotionLayout$$ExternalSyntheticOutline0.m23m("Key not identified, skipping: ", str, "PeopleBackupHelper");
                    return;
                }
                Set<String> set3 = (Set) entry.getValue();
                Uri parse2 = Uri.parse(String.valueOf(str));
                if (!ContentProvider.uriHasUserId(parse2)) {
                    if (peopleBackupHelper.mUserHandle.isSystem()) {
                        editor.putStringSet(parse2.toString(), set3);
                        return;
                    }
                    return;
                }
                int userIdFromUri = ContentProvider.getUserIdFromUri(parse2);
                if (userIdFromUri == peopleBackupHelper.mUserHandle.getIdentifier()) {
                    Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(parse2);
                    editor.putInt("add_user_id_to_uri_" + uriWithoutUserId.toString(), userIdFromUri);
                    editor.putStringSet(uriWithoutUserId.toString(), set3);
                }
            }
        });
        edit.apply();
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0121 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0031 A[SYNTHETIC] */
    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void restoreEntity(BackupDataInputStream backupDataInputStream) {
        boolean z;
        super.restoreEntity(backupDataInputStream);
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("shared_backup", 0);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
        SharedPreferences.Editor edit2 = this.mContext.getSharedPreferences("shared_follow_up", 0).edit();
        boolean z2 = false;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            SharedFileEntryType entryType = getEntryType(entry);
            int i = sharedPreferences.getInt("add_user_id_to_uri_" + key, -1);
            int i2 = AbstractC19131.f327xd18c5f3a[entryType.ordinal()];
            if (i2 == 1) {
                Uri parse = Uri.parse(String.valueOf(entry.getValue()));
                if (i != -1) {
                    parse = ContentProvider.createContentUriForUser(parse, UserHandle.of(i));
                }
                edit.putString(key, parse.toString());
            } else if (i2 == 2) {
                Set<String> set = (Set) entry.getValue();
                PeopleTileKey fromString = PeopleTileKey.fromString(key);
                if (fromString != null) {
                    String str = fromString.mPackageName;
                    fromString.mUserId = this.mUserHandle.getIdentifier();
                    if (PeopleTileKey.isValid(fromString)) {
                        IPeopleManager iPeopleManager = this.mIPeopleManager;
                        PackageManager packageManager = this.mPackageManager;
                        if (PeopleTileKey.isValid(fromString)) {
                            try {
                                packageManager.getPackageInfoAsUser(str, 0, fromString.mUserId);
                                z = iPeopleManager.isConversation(str, fromString.mUserId, fromString.mShortcutId);
                            } catch (PackageManager.NameNotFoundException | Exception unused) {
                                z = false;
                            }
                        } else {
                            z = true;
                        }
                        if (!z) {
                            edit2.putStringSet(fromString.toString(), set);
                        }
                        edit.putStringSet(fromString.toString(), set);
                        Context context = this.mContext;
                        Iterator<String> it = set.iterator();
                        while (it.hasNext()) {
                            SharedPreferencesHelper.setPeopleTileKey(context.getSharedPreferences(it.next(), 0), fromString);
                        }
                        if (z) {
                            z2 = true;
                        }
                    }
                }
            } else if (i2 != 3) {
                Log.e("PeopleBackupHelper", "Key not identified, skipping:" + key);
            } else {
                Set<String> set2 = (Set) entry.getValue();
                Uri parse2 = Uri.parse(key);
                if (i != -1) {
                    parse2 = ContentProvider.createContentUriForUser(parse2, UserHandle.of(i));
                }
                edit.putStringSet(parse2.toString(), set2);
            }
            z = true;
            if (z) {
            }
        }
        edit.apply();
        edit2.apply();
        SharedPreferences.Editor edit3 = sharedPreferences.edit();
        edit3.clear();
        edit3.apply();
        if (z2) {
            Context context2 = this.mContext;
            int i3 = PeopleBackupFollowUpJob.$r8$clinit;
            JobScheduler jobScheduler = (JobScheduler) context2.getSystemService(JobScheduler.class);
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putLong("start_date", System.currentTimeMillis());
            jobScheduler.schedule(new JobInfo.Builder(74823873, new ComponentName(context2, (Class<?>) PeopleBackupFollowUpJob.class)).setPeriodic(PeopleBackupFollowUpJob.JOB_PERIODIC_DURATION).setExtras(persistableBundle).build());
        }
        updateWidgets(this.mContext);
    }

    public PeopleBackupHelper(Context context, UserHandle userHandle, String[] strArr, PackageManager packageManager, IPeopleManager iPeopleManager) {
        super(context, strArr);
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mPackageManager = packageManager;
        this.mIPeopleManager = iPeopleManager;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
    }
}
