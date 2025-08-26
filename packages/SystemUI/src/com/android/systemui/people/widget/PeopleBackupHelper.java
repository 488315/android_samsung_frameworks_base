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
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class PeopleBackupHelper extends SharedPreferencesBackupHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppWidgetManager mAppWidgetManager;
    public final Context mContext;
    public final IPeopleManager mIPeopleManager;
    public final PackageManager mPackageManager;
    public final UserHandle mUserHandle;

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

    public static boolean isReadyForRestore(IPeopleManager iPeopleManager, PackageManager packageManager, PeopleTileKey peopleTileKey) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            return true;
        }
        try {
            packageManager.getPackageInfoAsUser(peopleTileKey.mPackageName, 0, peopleTileKey.mUserId);
            return iPeopleManager.isConversation(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId);
        } catch (PackageManager.NameNotFoundException | Exception unused) {
            return false;
        }
    }

    public static void updateWidgets(Context context) {
        int[] appWidgetIds;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (appWidgetManager == null || (appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, (Class<?>) PeopleSpaceWidgetProvider.class))) == null || appWidgetIds.length == 0) {
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
        final SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences("shared_backup", 0).edit();
        editorEdit.clear();
        int identifier = this.mUserHandle.getIdentifier();
        final ArrayList arrayList = new ArrayList();
        AppWidgetManager appWidgetManager = this.mAppWidgetManager;
        if (appWidgetManager != null) {
            for (int i : appWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, (Class<?>) PeopleSpaceWidgetProvider.class))) {
                String strValueOf = String.valueOf(i);
                if (this.mContext.getSharedPreferences(strValueOf, 0).getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1) == identifier) {
                    arrayList.add(strValueOf);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        defaultSharedPreferences.getAll().entrySet().forEach(new Consumer() { // from class: com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws NumberFormatException {
                PeopleBackupHelper peopleBackupHelper = this.f$0;
                SharedPreferences.Editor editor = editorEdit;
                final List list = arrayList;
                Map.Entry entry = (Map.Entry) obj;
                int i2 = PeopleBackupHelper.$r8$clinit;
                String str = (String) entry.getKey();
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                int iOrdinal = PeopleBackupHelper.getEntryType(entry).ordinal();
                if (iOrdinal == 1) {
                    String strValueOf2 = String.valueOf(entry.getValue());
                    if (((ArrayList) list).contains(str)) {
                        Uri uriWithoutUserId = Uri.parse(strValueOf2);
                        if (ContentProvider.uriHasUserId(uriWithoutUserId)) {
                            editor.putInt("add_user_id_to_uri_" + str, ContentProvider.getUserIdFromUri(uriWithoutUserId));
                            uriWithoutUserId = ContentProvider.getUriWithoutUserId(uriWithoutUserId);
                        }
                        editor.putString(str, uriWithoutUserId.toString());
                        return;
                    }
                    return;
                }
                if (iOrdinal == 2) {
                    Set set = (Set) entry.getValue();
                    PeopleTileKey peopleTileKeyFromString = PeopleTileKey.fromString(str);
                    if (peopleTileKeyFromString.mUserId != peopleBackupHelper.mUserHandle.getIdentifier()) {
                        return;
                    }
                    Set<String> set2 = (Set) set.stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            int i3 = PeopleBackupHelper.$r8$clinit;
                            return list.contains((String) obj2);
                        }
                    }).collect(Collectors.toSet());
                    if (set2.isEmpty()) {
                        return;
                    }
                    peopleTileKeyFromString.mUserId = -1;
                    editor.putStringSet(peopleTileKeyFromString.toString(), set2);
                    return;
                }
                if (iOrdinal != 3) {
                    MotionLayout$$ExternalSyntheticOutline0.m("Key not identified, skipping: ", str, "PeopleBackupHelper");
                    return;
                }
                Set<String> set3 = (Set) entry.getValue();
                Uri uri = Uri.parse(String.valueOf(str));
                if (!ContentProvider.uriHasUserId(uri)) {
                    if (peopleBackupHelper.mUserHandle.isSystem()) {
                        editor.putStringSet(uri.toString(), set3);
                        return;
                    }
                    return;
                }
                int userIdFromUri = ContentProvider.getUserIdFromUri(uri);
                if (userIdFromUri == peopleBackupHelper.mUserHandle.getIdentifier()) {
                    Uri uriWithoutUserId2 = ContentProvider.getUriWithoutUserId(uri);
                    editor.putInt("add_user_id_to_uri_" + uriWithoutUserId2.toString(), userIdFromUri);
                    editor.putStringSet(uriWithoutUserId2.toString(), set3);
                }
            }
        });
        editorEdit.apply();
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00fc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0031 A[SYNTHETIC] */
    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void restoreEntity(BackupDataInputStream backupDataInputStream) throws NumberFormatException {
        boolean zIsReadyForRestore;
        super.restoreEntity(backupDataInputStream);
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("shared_backup", 0);
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
        SharedPreferences.Editor editorEdit2 = this.mContext.getSharedPreferences("shared_follow_up", 0).edit();
        boolean z = false;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            SharedFileEntryType entryType = getEntryType(entry);
            int i = sharedPreferences.getInt("add_user_id_to_uri_" + key, -1);
            int iOrdinal = entryType.ordinal();
            if (iOrdinal == 1) {
                Uri uriCreateContentUriForUser = Uri.parse(String.valueOf(entry.getValue()));
                if (i != -1) {
                    uriCreateContentUriForUser = ContentProvider.createContentUriForUser(uriCreateContentUriForUser, UserHandle.of(i));
                }
                editorEdit.putString(key, uriCreateContentUriForUser.toString());
            } else if (iOrdinal == 2) {
                Set<String> set = (Set) entry.getValue();
                PeopleTileKey peopleTileKeyFromString = PeopleTileKey.fromString(key);
                if (peopleTileKeyFromString != null) {
                    peopleTileKeyFromString.mUserId = this.mUserHandle.getIdentifier();
                    if (PeopleTileKey.isValid(peopleTileKeyFromString)) {
                        zIsReadyForRestore = isReadyForRestore(this.mIPeopleManager, this.mPackageManager, peopleTileKeyFromString);
                        if (!zIsReadyForRestore) {
                            editorEdit2.putStringSet(peopleTileKeyFromString.toString(), set);
                        }
                        editorEdit.putStringSet(peopleTileKeyFromString.toString(), set);
                        Context context = this.mContext;
                        Iterator<String> it = set.iterator();
                        while (it.hasNext()) {
                            SharedPreferencesHelper.setPeopleTileKey(context.getSharedPreferences(it.next(), 0), peopleTileKeyFromString);
                        }
                    }
                }
                if (zIsReadyForRestore) {
                    z = true;
                }
            } else if (iOrdinal != 3) {
                Log.e("PeopleBackupHelper", "Key not identified, skipping:" + key);
            } else {
                Set<String> set2 = (Set) entry.getValue();
                Uri uriCreateContentUriForUser2 = Uri.parse(key);
                if (i != -1) {
                    uriCreateContentUriForUser2 = ContentProvider.createContentUriForUser(uriCreateContentUriForUser2, UserHandle.of(i));
                }
                editorEdit.putStringSet(uriCreateContentUriForUser2.toString(), set2);
            }
            zIsReadyForRestore = true;
            if (zIsReadyForRestore) {
            }
        }
        editorEdit.apply();
        editorEdit2.apply();
        SharedPreferences.Editor editorEdit3 = sharedPreferences.edit();
        editorEdit3.clear();
        editorEdit3.apply();
        if (z) {
            Context context2 = this.mContext;
            int i2 = PeopleBackupFollowUpJob.$r8$clinit;
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
