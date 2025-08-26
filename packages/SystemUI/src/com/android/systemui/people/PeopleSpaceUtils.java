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

/* loaded from: classes2.dex */
public class PeopleSpaceUtils {
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
        Bitmap bitmapCreateBitmap = (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static List<String> getContactLookupKeysWithBirthdaysToday(Context context) {
        ArrayList arrayList = new ArrayList(1);
        String str = new SimpleDateFormat("MM-dd").format(new Date());
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{"lookup", "data1"}, "mimetype= ? AND data2=3 AND (substr(data1,6) = ? OR substr(data1,3) = ? )", new String[]{"vnd.android.cursor.item/contact_event", str, str}, null);
                while (cursorQuery != null) {
                    if (!cursorQuery.moveToNext()) {
                        break;
                    }
                    arrayList.add(cursorQuery.getString(cursorQuery.getColumnIndex("lookup")));
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                    return arrayList;
                }
            } catch (SQLException e) {
                Log.e("PeopleSpaceUtils", "Failed to query birthdays", e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
            return arrayList;
        } finally {
        }
    }

    public static void getDataFromContacts(Context context, PeopleSpaceWidgetManager peopleSpaceWidgetManager, Map<Integer, PeopleSpaceTile> map, int[] iArr) throws Throwable {
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
                        Cursor cursorQuery = context.getContentResolver().query(peopleSpaceTile.getContactUri(), null, null, null, null);
                        while (cursorQuery != null) {
                            try {
                                if (!cursorQuery.moveToNext()) {
                                    break;
                                }
                                String string = cursorQuery.getString(cursorQuery.getColumnIndex("lookup"));
                                int columnIndex = cursorQuery.getColumnIndex("starred");
                                float fMax = 0.5f;
                                if (columnIndex >= 0 && cursorQuery.getInt(columnIndex) != 0) {
                                    fMax = Math.max(0.5f, 1.0f);
                                }
                                float f = fMax;
                                if (string.isEmpty() || !contactLookupKeysWithBirthdaysToday.contains(string)) {
                                    updateTileContactFields(peopleSpaceWidgetManager, context, peopleSpaceTile, i, f, null);
                                } else {
                                    updateTileContactFields(peopleSpaceWidgetManager, context, peopleSpaceTile, i, f, context.getString(R.string.birthday_status));
                                }
                            } catch (SQLException e) {
                                e = e;
                                cursor = cursorQuery;
                                Log.e("PeopleSpaceUtils", "Failed to query contact", e);
                                if (cursor != null) {
                                    cursor.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } catch (SQLException e2) {
                        e = e2;
                    }
                } catch (Throwable th2) {
                    th = th2;
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
                Long lValueOf;
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
                            lValueOf = Long.valueOf(iPeopleManager2.getLastInteraction(peopleSpaceTile.getPackageName(), peopleSpaceTile.getUserHandle().getIdentifier(), peopleSpaceTile.getId()));
                        } catch (Exception e) {
                            Log.e("PeopleSpaceUtils", "Couldn't retrieve last interaction time", e);
                            lValueOf = 0L;
                        }
                        return builder.setLastInteractionTimestamp(lValueOf.longValue()).build();
                }
            }
        });
        final int i3 = 1;
        Stream streamFilter = map.filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda3
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
        return (List) streamFilter.map(new Function() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Long lValueOf;
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
                            lValueOf = Long.valueOf(iPeopleManager2.getLastInteraction(peopleSpaceTile.getPackageName(), peopleSpaceTile.getUserHandle().getIdentifier(), peopleSpaceTile.getId()));
                        } catch (Exception e) {
                            Log.e("PeopleSpaceUtils", "Couldn't retrieve last interaction time", e);
                            lValueOf = 0L;
                        }
                        return builder.setLastInteractionTimestamp(lValueOf.longValue()).build();
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
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.remove(String.valueOf(i));
        String string = peopleTileKey.toString();
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(string, new HashSet()));
        hashSet.remove(String.valueOf(i));
        editorEdit.putStringSet(string, hashSet);
        HashSet hashSet2 = new HashSet(sharedPreferences.getStringSet(str, new HashSet()));
        hashSet2.remove(String.valueOf(i));
        editorEdit.putStringSet(str, hashSet2);
        editorEdit.apply();
        SharedPreferences.Editor editorEdit2 = context.getSharedPreferences(String.valueOf(i), 0).edit();
        editorEdit2.remove("package_name");
        editorEdit2.remove(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
        editorEdit2.remove("shortcut_id");
        editorEdit2.apply();
    }

    public static void setSharedPreferencesStorageForTile(Context context, PeopleTileKey peopleTileKey, int i, Uri uri, BackupManager backupManager) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            Log.e("PeopleSpaceUtils", "Not storing for invalid key");
            return;
        }
        SharedPreferencesHelper.setPeopleTileKey(context.getSharedPreferences(String.valueOf(i), 0), peopleTileKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), 0);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        String string = uri == null ? "" : uri.toString();
        editorEdit.putString(String.valueOf(i), string);
        String string2 = peopleTileKey.toString();
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(string2, new HashSet()));
        hashSet.add(String.valueOf(i));
        editorEdit.putStringSet(string2, hashSet);
        if (!TextUtils.isEmpty(string)) {
            HashSet hashSet2 = new HashSet(sharedPreferences.getStringSet(string, new HashSet()));
            hashSet2.add(String.valueOf(i));
            editorEdit.putStringSet(string, hashSet2);
        }
        editorEdit.apply();
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
