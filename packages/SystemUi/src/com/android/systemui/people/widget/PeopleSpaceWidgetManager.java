package com.android.systemui.people.widget;

import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Person;
import android.app.backup.BackupManager;
import android.app.job.JobScheduler;
import android.app.people.ConversationChannel;
import android.app.people.IPeopleManager;
import android.app.people.PeopleManager;
import android.app.people.PeopleSpaceTile;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.people.NotificationHelper;
import com.android.systemui.people.NotificationHelper$$ExternalSyntheticLambda0;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda0;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleSpaceWidgetManager {
    public static final Map mListeners = new HashMap();
    public static final Map mTiles = new HashMap();
    public final AppWidgetManager mAppWidgetManager;
    public final BackupManager mBackupManager;
    public final C19152 mBaseBroadcastReceiver;
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Optional mBubblesOptional;
    public final Context mContext;
    public final INotificationManager mINotificationManager;
    public final IPeopleManager mIPeopleManager;
    public final LauncherApps mLauncherApps;
    public final C19141 mListener;
    public final Object mLock;
    public final PeopleSpaceWidgetManager mManager;
    public final CommonNotifCollection mNotifCollection;
    public final Map mNotificationKeyToWidgetIdsMatchedByUri;
    public final NotificationManager mNotificationManager;
    public final PackageManager mPackageManager;
    public final PeopleManager mPeopleManager;
    public boolean mRegisteredReceivers;
    public final SharedPreferences mSharedPrefs;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$2 */
    public final class C19152 extends BroadcastReceiver {
        public C19152() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(2, this, intent));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$3 */
    public abstract /* synthetic */ class AbstractC19163 {

        /* renamed from: $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType */
        public static final /* synthetic */ int[] f328xd18c5f3a;

        static {
            int[] iArr = new int[PeopleBackupHelper.SharedFileEntryType.values().length];
            f328xd18c5f3a = iArr;
            try {
                iArr[PeopleBackupHelper.SharedFileEntryType.WIDGET_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f328xd18c5f3a[PeopleBackupHelper.SharedFileEntryType.PEOPLE_TILE_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f328xd18c5f3a[PeopleBackupHelper.SharedFileEntryType.CONTACT_URI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f328xd18c5f3a[PeopleBackupHelper.SharedFileEntryType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TileConversationListener implements PeopleManager.ConversationListener {
        public TileConversationListener() {
        }

        public final void onConversationUpdate(ConversationChannel conversationChannel) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(3, this, conversationChannel));
        }
    }

    public PeopleSpaceWidgetManager(Context context, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, NotificationManager notificationManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.mLock = new Object();
        this.mUiEventLogger = new UiEventLoggerImpl();
        this.mNotificationKeyToWidgetIdsMatchedByUri = new HashMap();
        this.mListener = new C19141();
        this.mBaseBroadcastReceiver = new C19152();
        this.mContext = context;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mLauncherApps = launcherApps;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPeopleManager = (PeopleManager) context.getSystemService(PeopleManager.class);
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mINotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        this.mBubblesOptional = optional;
        this.mUserManager = userManager;
        this.mBackupManager = new BackupManager(context);
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBgExecutor = executor;
    }

    public static Set getNewWidgets(Set set, final Map map) {
        return (Set) set.stream().map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) map.get((String) obj);
            }
        }).filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda2(0)).collect(Collectors.toSet());
    }

    public final void addNewWidget(final int i, PeopleTileKey peopleTileKey) {
        PeopleTileKey keyFromStorageByWidgetId;
        try {
            PeopleSpaceTile tileFromPersistentStorage = getTileFromPersistentStorage(peopleTileKey, i, false);
            if (tileFromPersistentStorage == null) {
                return;
            }
            final PeopleSpaceTile augmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(tileFromPersistentStorage, Optional.of(Integer.valueOf(i)));
            synchronized (this.mLock) {
                keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
            }
            if (PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
                deleteWidgets(new int[]{i});
            } else {
                this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_ADDED);
            }
            synchronized (this.mLock) {
                PeopleSpaceUtils.setSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, augmentTileFromNotificationEntryManager.getContactUri(), this.mBackupManager);
            }
            registerConversationListenerIfNeeded(i, peopleTileKey);
            try {
                this.mLauncherApps.cacheShortcuts(augmentTileFromNotificationEntryManager.getPackageName(), Collections.singletonList(augmentTileFromNotificationEntryManager.getId()), augmentTileFromNotificationEntryManager.getUserHandle(), 2);
            } catch (Exception e) {
                Log.w("PeopleSpaceWidgetMgr", "failed to cache shortcut for widget " + i, e);
            }
            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    PeopleSpaceWidgetManager.this.updateAppWidgetOptionsAndView(i, augmentTileFromNotificationEntryManager);
                }
            });
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("PeopleSpaceWidgetMgr", "Cannot add widget " + i + " since app was uninstalled");
        }
    }

    public final PeopleSpaceTile augmentTileFromNotificationEntryManager(PeopleSpaceTile peopleSpaceTile, Optional optional) {
        return augmentTileFromNotifications(peopleSpaceTile, new PeopleTileKey(peopleSpaceTile), peopleSpaceTile.getContactUri() != null ? peopleSpaceTile.getContactUri().toString() : null, groupConversationNotifications(((NotifPipeline) this.mNotifCollection).getAllNotifs()), optional);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v14, types: [java.util.List] */
    public final PeopleSpaceTile augmentTileFromNotifications(PeopleSpaceTile peopleSpaceTile, PeopleTileKey peopleTileKey, final String str, Map map, Optional optional) {
        Person senderPerson;
        List<Notification.MessagingStyle.Message> messagingStyleMessages;
        String packageName = peopleSpaceTile.getPackageName();
        final PackageManager packageManager = this.mPackageManager;
        boolean z = packageManager.checkPermission("android.permission.READ_CONTACTS", packageName) == 0;
        ArrayList arrayList = new ArrayList();
        if (z) {
            PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
            arrayList = TextUtils.isEmpty(str) ? new ArrayList() : (List) map.entrySet().stream().flatMap(new PeopleSpaceUtils$$ExternalSyntheticLambda0()).filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    PackageManager packageManager2 = packageManager;
                    String str2 = str;
                    NotificationEntry notificationEntry = (NotificationEntry) obj;
                    StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                    NotificationHelper.C19081 c19081 = NotificationHelper.notificationEntryComparator;
                    if (packageManager2.checkPermission("android.permission.READ_CONTACTS", statusBarNotification.getPackageName()) == 0) {
                        Notification notification2 = notificationEntry.mSbn.getNotification();
                        if ((notification2 == null ? false : NotificationHelper.isMissedCall(notification2)) && Objects.equals(str2, NotificationHelper.getContactUri(notificationEntry.mSbn))) {
                            return true;
                        }
                    }
                    return false;
                }
            }).collect(Collectors.toList());
            arrayList.isEmpty();
        }
        Set set = (Set) map.get(peopleTileKey);
        if (set == null) {
            set = new HashSet();
        }
        if (set.isEmpty() && arrayList.isEmpty()) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        set.addAll(arrayList);
        PeopleTileKey peopleTileKey3 = PeopleSpaceUtils.EMPTY_KEY;
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            Notification notification2 = ((NotificationEntry) it.next()).mSbn.getNotification();
            if (!NotificationHelper.isMissedCall(notification2) && (messagingStyleMessages = NotificationHelper.getMessagingStyleMessages(notification2)) != null) {
                i += messagingStyleMessages.size();
            }
        }
        NotificationHelper.C19081 c19081 = NotificationHelper.notificationEntryComparator;
        CharSequence charSequence = null;
        NotificationEntry notificationEntry = set.isEmpty() ? null : (NotificationEntry) set.stream().filter(new NotificationHelper$$ExternalSyntheticLambda0()).sorted(NotificationHelper.notificationEntryComparator).findFirst().orElse(null);
        if (notificationEntry == null || notificationEntry.mSbn.getNotification() == null) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Notification notification3 = statusBarNotification.getNotification();
        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
        String contactUri = NotificationHelper.getContactUri(statusBarNotification);
        boolean isPresent = optional.isPresent();
        Context context = this.mContext;
        if (isPresent && peopleSpaceTile.getContactUri() == null && !TextUtils.isEmpty(contactUri)) {
            Uri parse = Uri.parse(contactUri);
            PeopleSpaceUtils.setSharedPreferencesStorageForTile(context, new PeopleTileKey(peopleSpaceTile), ((Integer) optional.get()).intValue(), parse, this.mBackupManager);
            builder.setContactUri(parse);
        }
        boolean isMissedCall = NotificationHelper.isMissedCall(notification3);
        List<Notification.MessagingStyle.Message> messagingStyleMessages2 = NotificationHelper.getMessagingStyleMessages(notification3);
        if (!isMissedCall && ArrayUtils.isEmpty(messagingStyleMessages2)) {
            return PeopleSpaceUtils.removeNotificationFields(builder.build());
        }
        Notification.MessagingStyle.Message message = messagingStyleMessages2 != null ? messagingStyleMessages2.get(0) : null;
        CharSequence text = (!isMissedCall || ((message == null || TextUtils.isEmpty(message.getText())) ? false : true)) ? message.getText() : context.getString(R.string.missed_call);
        Uri dataUri = (message == null || !MessagingMessage.hasImage(message)) ? null : message.getDataUri();
        if (notification3.extras.getBoolean("android.isGroupConversation", false) && (senderPerson = message.getSenderPerson()) != null) {
            charSequence = senderPerson.getName();
        }
        return builder.setLastInteractionTimestamp(statusBarNotification.getPostTime()).setNotificationKey(statusBarNotification.getKey()).setNotificationCategory(notification3.category).setNotificationContent(text).setNotificationSender(charSequence).setNotificationDataUri(dataUri).setMessagesCount(i).build();
    }

    public final void deleteWidgets(int[] iArr) {
        PeopleTileKey peopleTileKey;
        HashSet hashSet;
        String string;
        for (int i : iArr) {
            this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_DELETED);
            synchronized (this.mLock) {
                SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
                peopleTileKey = new PeopleTileKey(sharedPreferences.getString("shortcut_id", null), sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), sharedPreferences.getString("package_name", null));
                if (!PeopleTileKey.isValid(peopleTileKey)) {
                    Log.e("PeopleSpaceWidgetMgr", "Invalid tile key trying to remove widget " + i);
                    return;
                }
                hashSet = new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
                string = this.mSharedPrefs.getString(String.valueOf(i), null);
            }
            synchronized (this.mLock) {
                PeopleSpaceUtils.removeSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, string);
            }
            if (hashSet.contains(String.valueOf(i)) && hashSet.size() == 1) {
                Map map = mListeners;
                synchronized (map) {
                    TileConversationListener tileConversationListener = (TileConversationListener) ((HashMap) map).get(peopleTileKey);
                    if (tileConversationListener != null) {
                        ((HashMap) map).remove(peopleTileKey);
                        this.mPeopleManager.unregisterConversationListener(tileConversationListener);
                    }
                }
                try {
                    this.mLauncherApps.uncacheShortcuts(peopleTileKey.mPackageName, Collections.singletonList(peopleTileKey.mShortcutId), UserHandle.of(peopleTileKey.mUserId), 2);
                } catch (Exception e) {
                    Log.d("PeopleSpaceWidgetMgr", "failed to uncache shortcut", e);
                }
            }
        }
    }

    public final PeopleTileKey getKeyFromStorageByWidgetId(int i) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), sharedPreferences.getString("package_name", ""));
    }

    public final Set getMatchingKeyWidgetIds(PeopleTileKey peopleTileKey) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            return new HashSet();
        }
        return new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0036, code lost:
    
        if (r1.isEmpty() != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Set getMatchingUriWidgetIds(StatusBarNotification statusBarNotification, PeopleSpaceUtils.NotificationAction notificationAction) {
        HashSet hashSet;
        String contactUri;
        boolean equals = notificationAction.equals(PeopleSpaceUtils.NotificationAction.POSTED);
        Map map = this.mNotificationKeyToWidgetIdsMatchedByUri;
        if (equals) {
            NotificationHelper.C19081 c19081 = NotificationHelper.notificationEntryComparator;
            Notification notification2 = statusBarNotification.getNotification();
            if ((notification2 == null ? false : NotificationHelper.isMissedCall(notification2)) && (contactUri = NotificationHelper.getContactUri(statusBarNotification)) != null) {
                hashSet = new HashSet(this.mSharedPrefs.getStringSet(contactUri, new HashSet()));
            }
            hashSet = null;
            if (hashSet != null && !hashSet.isEmpty()) {
                ((HashMap) map).put(statusBarNotification.getKey(), hashSet);
                return hashSet;
            }
        } else {
            Set set = (Set) ((HashMap) map).remove(statusBarNotification.getKey());
            if (set != null && !set.isEmpty()) {
                return set;
            }
        }
        return new HashSet();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getNotificationPolicyState() {
        int currentInterruptionFilter;
        int i;
        NotificationManager notificationManager = this.mNotificationManager;
        NotificationManager.Policy notificationPolicy = notificationManager.getNotificationPolicy();
        if (!NotificationManager.Policy.areAllVisualEffectsSuppressed(notificationPolicy.suppressedVisualEffects) || (currentInterruptionFilter = notificationManager.getCurrentInterruptionFilter()) == 1) {
            return 1;
        }
        if (currentInterruptionFilter == 2) {
            if (notificationPolicy.allowConversations()) {
                int i2 = notificationPolicy.priorityConversationSenders;
                if (i2 == 1) {
                    return 1;
                }
                if (i2 == 2) {
                    i = 4;
                    if (!notificationPolicy.allowMessages()) {
                        int allowMessagesFrom = notificationPolicy.allowMessagesFrom();
                        if (allowMessagesFrom == 1) {
                            return i | 16;
                        }
                        if (allowMessagesFrom != 2) {
                            return 1;
                        }
                        return i | 8;
                    }
                    if (i != 0) {
                        return i;
                    }
                }
            }
            i = 0;
            if (!notificationPolicy.allowMessages()) {
            }
        }
        return 2;
    }

    public final boolean getPackageSuspended(PeopleSpaceTile peopleSpaceTile) {
        boolean isEmpty = TextUtils.isEmpty(peopleSpaceTile.getPackageName());
        PackageManager packageManager = this.mPackageManager;
        boolean z = !isEmpty && packageManager.isPackageSuspended(peopleSpaceTile.getPackageName());
        String packageName = peopleSpaceTile.getPackageName();
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        packageManager.getApplicationInfoAsUser(packageName, 128, peopleSpaceTile.getUserHandle().getIdentifier());
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final RemoteViews getPreview(String str, UserHandle userHandle, String str2, Bundle bundle) {
        PeopleSpaceTile build;
        try {
            ConversationChannel conversation = this.mIPeopleManager.getConversation(str2, userHandle.getIdentifier(), str);
            LauncherApps launcherApps = this.mLauncherApps;
            PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
            if (conversation != null) {
                build = new PeopleSpaceTile.Builder(conversation, launcherApps).build();
                if (!((build == null || TextUtils.isEmpty(build.getUserName())) ? false : true)) {
                    Log.i("PeopleSpaceUtils", "PeopleSpaceTile is not valid");
                }
                if (build != null) {
                    return null;
                }
                PeopleSpaceTile augmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(build, Optional.empty());
                return PeopleTileViewHelper.createRemoteViews(this.mContext, augmentTileFromNotificationEntryManager, 0, bundle, new PeopleTileKey(augmentTileFromNotificationEntryManager));
            }
            Log.i("PeopleSpaceUtils", "ConversationChannel is null");
            build = null;
            if (build != null) {
            }
        } catch (Exception e) {
            Log.w("PeopleSpaceWidgetMgr", "failed to get conversation or tile", e);
            return null;
        }
    }

    public final PeopleSpaceTile getTileForExistingWidget(int i) {
        try {
            return getTileForExistingWidgetThrowing(i);
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "failed to retrieve tile for existing widget " + i, e);
            return null;
        }
    }

    public final PeopleSpaceTile getTileForExistingWidgetThrowing(int i) {
        PeopleSpaceTile peopleSpaceTile;
        Map map = mTiles;
        synchronized (map) {
            peopleSpaceTile = (PeopleSpaceTile) ((HashMap) map).get(Integer.valueOf(i));
        }
        if (peopleSpaceTile != null) {
            return peopleSpaceTile;
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return getTileFromPersistentStorage(new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), sharedPreferences.getString("package_name", "")), i, true);
    }

    public final PeopleSpaceTile getTileFromPersistentStorage(PeopleTileKey peopleTileKey, int i, boolean z) {
        LauncherApps launcherApps;
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Invalid tile key finding tile for existing widget ", i, "PeopleSpaceWidgetMgr");
            return null;
        }
        IPeopleManager iPeopleManager = this.mIPeopleManager;
        if (iPeopleManager == null || (launcherApps = this.mLauncherApps) == null) {
            Log.d("PeopleSpaceWidgetMgr", "System services are null");
            return null;
        }
        try {
            ConversationChannel conversation = iPeopleManager.getConversation(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId);
            if (conversation == null) {
                return null;
            }
            PeopleSpaceTile.Builder builder = new PeopleSpaceTile.Builder(conversation, launcherApps);
            String string = this.mSharedPrefs.getString(String.valueOf(i), null);
            if (z && string != null && builder.build().getContactUri() == null) {
                builder.setContactUri(Uri.parse(string));
            }
            return getTileWithCurrentState(builder.build(), "android.intent.action.BOOT_COMPLETED");
        } catch (RemoteException e) {
            Log.e("PeopleSpaceWidgetMgr", "getTileFromPersistentStorage failing for widget " + i, e);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final PeopleSpaceTile getTileWithCurrentState(PeopleSpaceTile peopleSpaceTile, String str) {
        char c;
        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
        boolean z = false;
        switch (str.hashCode()) {
            case -1238404651:
                if (str.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1001645458:
                if (str.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -864107122:
                if (str.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -19011148:
                if (str.equals("android.intent.action.LOCALE_CHANGED")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 798292259:
                if (str.equals("android.intent.action.BOOT_COMPLETED")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 833559602:
                if (str.equals("android.intent.action.USER_UNLOCKED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1290767157:
                if (str.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2106958107:
                if (str.equals("android.app.action.INTERRUPTION_FILTER_CHANGED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        UserManager userManager = this.mUserManager;
        switch (c) {
            case 0:
                builder.setNotificationPolicyState(getNotificationPolicyState());
                break;
            case 1:
            case 2:
                builder.setIsPackageSuspended(getPackageSuspended(peopleSpaceTile));
                break;
            case 3:
            case 4:
            case 5:
                if (peopleSpaceTile.getUserHandle() != null && userManager.isQuietModeEnabled(peopleSpaceTile.getUserHandle())) {
                    z = true;
                }
                builder.setIsUserQuieted(z);
                break;
            case 6:
                break;
            default:
                if (peopleSpaceTile.getUserHandle() != null && userManager.isQuietModeEnabled(peopleSpaceTile.getUserHandle())) {
                    z = true;
                }
                builder.setIsUserQuieted(z).setIsPackageSuspended(getPackageSuspended(peopleSpaceTile)).setNotificationPolicyState(getNotificationPolicyState());
                break;
        }
        return builder.build();
    }

    public final Map groupConversationNotifications(Collection collection) {
        return (Map) collection.stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
            @Override // java.util.function.Predicate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean test(Object obj) {
                boolean z;
                NotificationListenerService.Ranking ranking;
                PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                peopleSpaceWidgetManager.getClass();
                NotificationHelper.C19081 c19081 = NotificationHelper.notificationEntryComparator;
                if (((notificationEntry == null || (ranking = notificationEntry.mRanking) == null || ranking.getConversationShortcutInfo() == null || notificationEntry.mSbn.getNotification() == null) ? false : true) && NotificationHelper.isMissedCallOrHasContent(notificationEntry)) {
                    Optional optional = peopleSpaceWidgetManager.mBubblesOptional;
                    try {
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m58m("Exception checking if notification is suppressed: ", e, "PeopleNotifHelper");
                    }
                    if (optional.isPresent()) {
                        if (((BubbleController.BubblesImpl) ((Bubbles) optional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey())) {
                            z = true;
                            if (!z) {
                                return true;
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
                return false;
            }
        }).collect(Collectors.groupingBy(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(0), Collectors.mapping(Function.identity(), Collectors.toSet())));
    }

    public final void registerConversationListenerIfNeeded(int i, PeopleTileKey peopleTileKey) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            IconCompat$$ExternalSyntheticOutline0.m30m("Invalid tile key registering listener for widget ", i, "PeopleSpaceWidgetMgr");
            return;
        }
        TileConversationListener tileConversationListener = new TileConversationListener();
        Map map = mListeners;
        synchronized (map) {
            if (((HashMap) map).containsKey(peopleTileKey)) {
                return;
            }
            ((HashMap) map).put(peopleTileKey, tileConversationListener);
            this.mPeopleManager.registerConversationListener(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId, tileConversationListener, this.mContext.getMainExecutor());
        }
    }

    public final void updateAppWidgetOptionsAndView(int i, PeopleSpaceTile peopleSpaceTile) {
        if (peopleSpaceTile == null) {
            IconCompat$$ExternalSyntheticOutline0.m30m("Storing null tile for widget ", i, "PeopleSpaceWidgetMgr");
        }
        Map map = mTiles;
        synchronized (map) {
            ((HashMap) map).put(Integer.valueOf(i), peopleSpaceTile);
        }
        Bundle appWidgetOptions = this.mAppWidgetManager.getAppWidgetOptions(i);
        PeopleTileKey keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
        if (!PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Invalid tile key updating widget ", i, "PeopleSpaceWidgetMgr");
        } else {
            this.mAppWidgetManager.updateAppWidget(i, PeopleTileViewHelper.createRemoteViews(this.mContext, peopleSpaceTile, i, appWidgetOptions, keyFromStorageByWidgetId));
        }
    }

    public final void updateSingleConversationWidgets(final int[] iArr) {
        final HashMap hashMap = new HashMap();
        for (int i : iArr) {
            PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
            if (tileForExistingWidget == null) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("Matching conversation not found for widget ", i, "PeopleSpaceWidgetMgr");
            }
            updateAppWidgetOptionsAndView(i, tileForExistingWidget);
            hashMap.put(Integer.valueOf(i), tileForExistingWidget);
            if (tileForExistingWidget != null) {
                registerConversationListenerIfNeeded(i, new PeopleTileKey(tileForExistingWidget));
            }
        }
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        final Context context = this.mContext;
        final PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mManager;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PeopleSpaceUtils.getDataFromContacts(context, peopleSpaceWidgetManager, hashMap, iArr);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateStorageAndViewWithConversationData(ConversationChannel conversationChannel, int i) {
        Uri uri;
        CharSequence label;
        Icon convertDrawableToIcon;
        NotificationChannel notificationChannel;
        PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
        if (tileForExistingWidget == null) {
            return;
        }
        PeopleSpaceTile.Builder builder = tileForExistingWidget.toBuilder();
        ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
        if (shortcutInfo.getPersons() != null && shortcutInfo.getPersons().length > 0) {
            Person person = shortcutInfo.getPersons()[0];
            if (person.getUri() != null) {
                uri = Uri.parse(person.getUri());
                label = shortcutInfo.getLabel();
                if (label != null) {
                    builder.setUserName(label);
                }
                convertDrawableToIcon = PeopleSpaceTile.convertDrawableToIcon(this.mLauncherApps.getShortcutIconDrawable(shortcutInfo, 0));
                if (convertDrawableToIcon != null) {
                    builder.setUserIcon(convertDrawableToIcon);
                }
                notificationChannel = conversationChannel.getNotificationChannel();
                if (notificationChannel != null) {
                    builder.setIsImportantConversation(notificationChannel.isImportantConversation());
                }
                builder.setContactUri(uri).setStatuses(conversationChannel.getStatuses()).setLastInteractionTimestamp(conversationChannel.getLastEventTimestamp());
                updateAppWidgetOptionsAndView(i, builder.build());
            }
        }
        uri = null;
        label = shortcutInfo.getLabel();
        if (label != null) {
        }
        convertDrawableToIcon = PeopleSpaceTile.convertDrawableToIcon(this.mLauncherApps.getShortcutIconDrawable(shortcutInfo, 0));
        if (convertDrawableToIcon != null) {
        }
        notificationChannel = conversationChannel.getNotificationChannel();
        if (notificationChannel != null) {
        }
        builder.setContactUri(uri).setStatuses(conversationChannel.getStatuses()).setLastInteractionTimestamp(conversationChannel.getLastEventTimestamp());
        updateAppWidgetOptionsAndView(i, builder.build());
    }

    public final void updateWidgetIdsBasedOnNotifications(Set set, Collection collection) {
        if (((HashSet) set).isEmpty()) {
            return;
        }
        try {
            final Map groupConversationNotifications = groupConversationNotifications(collection);
            ((Map) set.stream().map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(4)).collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
                    Map map = groupConversationNotifications;
                    peopleSpaceWidgetManager.getClass();
                    int intValue = ((Integer) obj).intValue();
                    PeopleSpaceTile tileForExistingWidget = peopleSpaceWidgetManager.getTileForExistingWidget(intValue);
                    if (tileForExistingWidget != null) {
                        return Optional.ofNullable(peopleSpaceWidgetManager.augmentTileFromNotifications(tileForExistingWidget, new PeopleTileKey(tileForExistingWidget), peopleSpaceWidgetManager.mSharedPrefs.getString(String.valueOf(intValue), null), map, Optional.of(Integer.valueOf(intValue))));
                    }
                    Log.w("PeopleSpaceWidgetMgr", "Null tile for existing widget " + intValue + ", skipping update.");
                    return Optional.empty();
                }
            }))).forEach(new BiConsumer() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
                    Optional optional = (Optional) obj2;
                    peopleSpaceWidgetManager.getClass();
                    int intValue = ((Integer) obj).intValue();
                    if (optional.isPresent()) {
                        peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(intValue, (PeopleSpaceTile) optional.get());
                    }
                }
            });
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "updateWidgetIdsBasedOnNotifications failing", e);
        }
    }

    public void updateWidgetsFromBroadcastInBackground(String str) {
        int[] appWidgetIds = this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, (Class<?>) PeopleSpaceWidgetProvider.class));
        if (appWidgetIds == null) {
            return;
        }
        for (int i : appWidgetIds) {
            try {
                synchronized (this.mLock) {
                    PeopleSpaceTile tileForExistingWidgetThrowing = getTileForExistingWidgetThrowing(i);
                    if (tileForExistingWidgetThrowing == null) {
                        Log.e("PeopleSpaceWidgetMgr", "Matching conversation not found for widget " + i);
                    } else {
                        updateAppWidgetOptionsAndView(i, getTileWithCurrentState(tileForExistingWidgetThrowing, str));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PeopleSpaceWidgetMgr", "Package no longer found for widget " + i, e);
                JobScheduler jobScheduler = (JobScheduler) this.mContext.getSystemService(JobScheduler.class);
                if (jobScheduler == null || jobScheduler.getPendingJob(74823873) == null) {
                    synchronized (this.mLock) {
                        updateAppWidgetOptionsAndView(i, null);
                        deleteWidgets(new int[]{i});
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$1 */
    public final class C19141 implements NotificationListener.NotificationHandler {
        public C19141() {
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            if (notificationChannel.isConversation()) {
                PeopleSpaceWidgetManager.this.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(1, this, userHandle));
            }
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            PeopleSpaceUtils.NotificationAction notificationAction = PeopleSpaceUtils.NotificationAction.POSTED;
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda6(peopleSpaceWidgetManager, statusBarNotification, notificationAction, ((NotifPipeline) peopleSpaceWidgetManager.mNotifCollection).getAllNotifs()));
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            PeopleSpaceUtils.NotificationAction notificationAction = PeopleSpaceUtils.NotificationAction.REMOVED;
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda6(peopleSpaceWidgetManager, statusBarNotification, notificationAction, ((NotifPipeline) peopleSpaceWidgetManager.mNotifCollection).getAllNotifs()));
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationsInitialized() {
        }
    }

    public PeopleSpaceWidgetManager(Context context, AppWidgetManager appWidgetManager, IPeopleManager iPeopleManager, PeopleManager peopleManager, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, BackupManager backupManager, INotificationManager iNotificationManager, NotificationManager notificationManager, Executor executor) {
        this.mLock = new Object();
        this.mUiEventLogger = new UiEventLoggerImpl();
        this.mNotificationKeyToWidgetIdsMatchedByUri = new HashMap();
        this.mListener = new C19141();
        this.mBaseBroadcastReceiver = new C19152();
        this.mContext = context;
        this.mAppWidgetManager = appWidgetManager;
        this.mIPeopleManager = iPeopleManager;
        this.mPeopleManager = peopleManager;
        this.mLauncherApps = launcherApps;
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mBubblesOptional = optional;
        this.mUserManager = userManager;
        this.mBackupManager = backupManager;
        this.mINotificationManager = iNotificationManager;
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mBgExecutor = executor;
    }
}
