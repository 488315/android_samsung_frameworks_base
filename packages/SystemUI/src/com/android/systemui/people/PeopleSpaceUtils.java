package com.android.systemui.people;

import android.app.backup.BackupManager;
import android.app.people.IPeopleManager;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.PreferenceManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleTileKey;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PeopleSpaceUtils {
    public static final PeopleTileKey EMPTY_KEY = new PeopleTileKey("", -1, "");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum NotificationAction {
        POSTED,
        REMOVED
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public static List<String> getContactLookupKeysWithBirthdaysToday(Context context) {
        ArrayList arrayList = new ArrayList(1);
        String format = new SimpleDateFormat("MM-dd").format(new Date());
        Cursor cursor = null;
        try {
            try {
                cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{"lookup", "data1"}, "mimetype= ? AND data2=3 AND (substr(data1,6) = ? OR substr(data1,3) = ? )", new String[]{"vnd.android.cursor.item/contact_event", format, format}, null);
                while (cursor != null) {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                    arrayList.add(cursor.getString(cursor.getColumnIndex("lookup")));
                }
                if (cursor != null) {
                    cursor.close();
                    return arrayList;
                }
            } catch (SQLException e) {
                Log.e("PeopleSpaceUtils", "Failed to query birthdays", e);
                if (cursor != null) {
                    cursor.close();
                }
            }
            return arrayList;
        } finally {
        }
    }

    public static void getDataFromContacts(Context context, PeopleSpaceWidgetManager peopleSpaceWidgetManager, Map<Integer, PeopleSpaceTile> map, int[] iArr) {
        if (iArr.length == 0) {
            return;
        }
        List<String> contactLookupKeysWithBirthdaysToday = getContactLookupKeysWithBirthdaysToday(context);
        for (int i : iArr) {
            PeopleSpaceTile peopleSpaceTile = map.get(Integer.valueOf(i));
            if (peopleSpaceTile == null || peopleSpaceTile.getContactUri() == null) {
                updateTileContactFields(peopleSpaceWidgetManager, context, peopleSpaceTile, i, 0.0f, null);
            } else {
                Cursor cursor = null;
                try {
                    try {
                        Cursor query = context.getContentResolver().query(peopleSpaceTile.getContactUri(), null, null, null, null);
                        while (query != null) {
                            try {
                                if (!query.moveToNext()) {
                                    break;
                                }
                                String string = query.getString(query.getColumnIndex("lookup"));
                                int columnIndex = query.getColumnIndex("starred");
                                float f = 0.5f;
                                if (columnIndex >= 0 && query.getInt(columnIndex) != 0) {
                                    f = Math.max(0.5f, 1.0f);
                                }
                                float f2 = f;
                                if (string.isEmpty() || !contactLookupKeysWithBirthdaysToday.contains(string)) {
                                    updateTileContactFields(peopleSpaceWidgetManager, context, peopleSpaceTile, i, f2, null);
                                } else {
                                    updateTileContactFields(peopleSpaceWidgetManager, context, peopleSpaceTile, i, f2, context.getString(R.string.birthday_status));
                                }
                            } catch (SQLException e) {
                                e = e;
                                cursor = query;
                                Log.e("PeopleSpaceUtils", "Failed to query contact", e);
                                if (cursor != null) {
                                    cursor.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = query;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (SQLException e2) {
                    e = e2;
                }
            }
        }
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
                        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
                        return (peopleSpaceTile == null || TextUtils.isEmpty(peopleSpaceTile.getUserName())) ? false : true;
                }
            }
        }).filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                UserManager userManager2 = userManager;
                PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
                return !userManager2.isQuietModeEnabled(((ShortcutInfo) obj).getUserHandle());
            }
        }).map(new Function() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Long l;
                int i3 = i2;
                Object obj2 = launcherApps;
                switch (i3) {
                    case 0:
                        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
                        return new PeopleSpaceTile.Builder(shortcutInfo, (LauncherApps) obj2).build();
                    default:
                        IPeopleManager iPeopleManager2 = (IPeopleManager) obj2;
                        PeopleSpaceTile peopleSpaceTile = (PeopleSpaceTile) obj;
                        PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
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
                        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
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
                        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
                        return new PeopleSpaceTile.Builder(shortcutInfo, (LauncherApps) obj2).build();
                    default:
                        IPeopleManager iPeopleManager2 = (IPeopleManager) obj2;
                        PeopleSpaceTile peopleSpaceTile = (PeopleSpaceTile) obj;
                        PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
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
