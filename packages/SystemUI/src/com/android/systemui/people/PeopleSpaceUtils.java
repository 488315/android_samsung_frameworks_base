package com.android.systemui.people;

import android.app.backup.BackupManager;
import android.app.people.IPeopleManager;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.PreferenceManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleTileKey;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PeopleSpaceUtils {
    public static final PeopleTileKey EMPTY_KEY = new PeopleTileKey("", -1, "");

    public enum NotificationAction {
        POSTED,
        REMOVED
    }

    public enum PeopleSpaceWidgetEvent implements UiEventLogger.UiEventEnum {
        PEOPLE_SPACE_WIDGET_DELETED(666),
        PEOPLE_SPACE_WIDGET_ADDED(667),
        PEOPLE_SPACE_WIDGET_CLICKED(668);

        private final int mId;

        PeopleSpaceWidgetEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap createBitmap = (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x005b, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0058, code lost:
    
        if (r1 == null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> getContactLookupKeysWithBirthdaysToday(android.content.Context r10) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 1
            r0.<init>(r1)
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.lang.String r2 = "MM-dd"
            r1.<init>(r2)
            java.util.Date r2 = new java.util.Date
            r2.<init>()
            java.lang.String r1 = r1.format(r2)
            java.lang.String r2 = "data1"
            java.lang.String r3 = "lookup"
            java.lang.String[] r6 = new java.lang.String[]{r3, r2}
            java.lang.String r7 = "mimetype= ? AND data2=3 AND (substr(data1,6) = ? OR substr(data1,3) = ? )"
            java.lang.String r2 = "vnd.android.cursor.item/contact_event"
            java.lang.String[] r8 = new java.lang.String[]{r2, r1, r1}
            r1 = 0
            android.content.ContentResolver r4 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
            android.net.Uri r5 = android.provider.ContactsContract.Data.CONTENT_URI     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
            r9 = 0
            android.database.Cursor r1 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
        L33:
            if (r1 == 0) goto L4b
            boolean r10 = r1.moveToNext()     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
            if (r10 == 0) goto L4b
            int r10 = r1.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
            java.lang.String r10 = r1.getString(r10)     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
            r0.add(r10)     // Catch: java.lang.Throwable -> L47 android.database.SQLException -> L49
            goto L33
        L47:
            r10 = move-exception
            goto L5c
        L49:
            r10 = move-exception
            goto L51
        L4b:
            if (r1 == 0) goto L5b
        L4d:
            r1.close()
            goto L5b
        L51:
            java.lang.String r2 = "PeopleSpaceUtils"
            java.lang.String r3 = "Failed to query birthdays"
            android.util.Log.e(r2, r3, r10)     // Catch: java.lang.Throwable -> L47
            if (r1 == 0) goto L5b
            goto L4d
        L5b:
            return r0
        L5c:
            if (r1 == 0) goto L61
            r1.close()
        L61:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleSpaceUtils.getContactLookupKeysWithBirthdaysToday(android.content.Context):java.util.List");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void getDataFromContacts(android.content.Context r19, com.android.systemui.people.widget.PeopleSpaceWidgetManager r20, java.util.Map<java.lang.Integer, android.app.people.PeopleSpaceTile> r21, int[] r22) {
        /*
            Method dump skipped, instructions count: 211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleSpaceUtils.getDataFromContacts(android.content.Context, com.android.systemui.people.widget.PeopleSpaceWidgetManager, java.util.Map, int[]):void");
    }

    public static List getSortedTiles(final IPeopleManager iPeopleManager, final LauncherApps launcherApps, final UserManager userManager, Stream stream) {
        final int i = 0;
        final int i2 = 0;
        Stream map = stream.filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                switch (i) {
                    case 0:
                        return Objects.nonNull((ShortcutInfo) obj);
                    default:
                        PeopleSpaceTile peopleSpaceTile = (PeopleSpaceTile) obj;
                        return (peopleSpaceTile == null || TextUtils.isEmpty(peopleSpaceTile.getUserName())) ? false : true;
                }
            }
        }).filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !userManager.isQuietModeEnabled(((ShortcutInfo) obj).getUserHandle());
            }
        }).map(new Function() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Long l;
                int i3 = i2;
                Object obj2 = launcherApps;
                switch (i3) {
                    case 0:
                        return new PeopleSpaceTile.Builder((ShortcutInfo) obj, (LauncherApps) obj2).build();
                    default:
                        IPeopleManager iPeopleManager2 = (IPeopleManager) obj2;
                        PeopleSpaceTile peopleSpaceTile = (PeopleSpaceTile) obj;
                        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
                        try {
                            l = Long.valueOf(iPeopleManager2.getLastInteraction(peopleSpaceTile.getPackageName(), peopleSpaceTile.getUserHandle().getIdentifier(), peopleSpaceTile.getId()));
                        } catch (Exception e) {
                            Log.e("PeopleSpaceUtils", "Couldn't retrieve last interaction time", e);
                            l = 0L;
                        }
                        return builder.setLastInteractionTimestamp(l.longValue()).build();
                }
            }
        });
        final int i3 = 1;
        Stream filter = map.filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                switch (i3) {
                    case 0:
                        return Objects.nonNull((ShortcutInfo) obj);
                    default:
                        PeopleSpaceTile peopleSpaceTile = (PeopleSpaceTile) obj;
                        return (peopleSpaceTile == null || TextUtils.isEmpty(peopleSpaceTile.getUserName())) ? false : true;
                }
            }
        });
        final int i4 = 1;
        return (List) filter.map(new Function() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Long l;
                int i32 = i4;
                Object obj2 = iPeopleManager;
                switch (i32) {
                    case 0:
                        return new PeopleSpaceTile.Builder((ShortcutInfo) obj, (LauncherApps) obj2).build();
                    default:
                        IPeopleManager iPeopleManager2 = (IPeopleManager) obj2;
                        PeopleSpaceTile peopleSpaceTile = (PeopleSpaceTile) obj;
                        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
                        try {
                            l = Long.valueOf(iPeopleManager2.getLastInteraction(peopleSpaceTile.getPackageName(), peopleSpaceTile.getUserHandle().getIdentifier(), peopleSpaceTile.getId()));
                        } catch (Exception e) {
                            Log.e("PeopleSpaceUtils", "Couldn't retrieve last interaction time", e);
                            l = 0L;
                        }
                        return builder.setLastInteractionTimestamp(l.longValue()).build();
                }
            }
        }).sorted(new PeopleSpaceUtils$$ExternalSyntheticLambda8()).collect(Collectors.toList());
    }

    public static PeopleSpaceTile removeNotificationFields(PeopleSpaceTile peopleSpaceTile) {
        PeopleSpaceTile.Builder notificationCategory = peopleSpaceTile.toBuilder().setNotificationKey((String) null).setNotificationContent((CharSequence) null).setNotificationSender((CharSequence) null).setNotificationDataUri((Uri) null).setMessagesCount(0).setNotificationCategory((String) null);
        if (!TextUtils.isEmpty(peopleSpaceTile.getNotificationKey())) {
            notificationCategory.setLastInteractionTimestamp(System.currentTimeMillis());
        }
        return notificationCategory.build();
    }

    public static void removeSharedPreferencesStorageForTile(Context context, PeopleTileKey peopleTileKey, int i, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(String.valueOf(i));
        String peopleTileKey2 = peopleTileKey.toString();
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(peopleTileKey2, new HashSet()));
        hashSet.remove(String.valueOf(i));
        edit.putStringSet(peopleTileKey2, hashSet);
        HashSet hashSet2 = new HashSet(sharedPreferences.getStringSet(str, new HashSet()));
        hashSet2.remove(String.valueOf(i));
        edit.putStringSet(str, hashSet2);
        edit.apply();
        SharedPreferences.Editor edit2 = context.getSharedPreferences(String.valueOf(i), 0).edit();
        edit2.remove("package_name");
        edit2.remove(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
        edit2.remove("shortcut_id");
        edit2.apply();
    }

    public static void setSharedPreferencesStorageForTile(Context context, PeopleTileKey peopleTileKey, int i, Uri uri, BackupManager backupManager) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            Log.e("PeopleSpaceUtils", "Not storing for invalid key");
            return;
        }
        SharedPreferencesHelper.setPeopleTileKey(context.getSharedPreferences(String.valueOf(i), 0), peopleTileKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String uri2 = uri == null ? "" : uri.toString();
        edit.putString(String.valueOf(i), uri2);
        String peopleTileKey2 = peopleTileKey.toString();
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(peopleTileKey2, new HashSet()));
        hashSet.add(String.valueOf(i));
        edit.putStringSet(peopleTileKey2, hashSet);
        if (!TextUtils.isEmpty(uri2)) {
            HashSet hashSet2 = new HashSet(sharedPreferences.getStringSet(uri2, new HashSet()));
            hashSet2.add(String.valueOf(i));
            edit.putStringSet(uri2, hashSet2);
        }
        edit.apply();
        backupManager.dataChanged();
    }

    public static void updateTileContactFields(PeopleSpaceWidgetManager peopleSpaceWidgetManager, Context context, PeopleSpaceTile peopleSpaceTile, int i, float f, String str) {
        boolean z = false;
        boolean z2 = (peopleSpaceTile.getBirthdayText() != null && peopleSpaceTile.getBirthdayText().equals(context.getString(R.string.birthday_status))) && str == null;
        if ((peopleSpaceTile.getBirthdayText() == null || !peopleSpaceTile.getBirthdayText().equals(context.getString(R.string.birthday_status))) && str != null) {
            z = true;
        }
        if (peopleSpaceTile.getContactAffinity() != f || z2 || z) {
            peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(i, peopleSpaceTile.toBuilder().setBirthdayText(str).setContactAffinity(f).build());
        }
    }
}
