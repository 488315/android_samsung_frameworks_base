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
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.RemoteViews;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.people.NotificationHelper;
import com.android.systemui.people.NotificationHelper$$ExternalSyntheticLambda0;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda0;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.people.widget.PeopleTileKey;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.wm.shell.bubbles.Bubbles;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.io.PrintWriter;
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

/* loaded from: classes2.dex */
public class PeopleSpaceWidgetManager implements Dumpable {
    public static final Map mListeners = new HashMap();
    public static final Map mTiles = new HashMap();
    public final Optional mAppWidgetManagerOptional;
    public final BackupManager mBackupManager;
    public final AnonymousClass3 mBaseBroadcastReceiver;
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Optional mBubblesOptional;
    public final Context mContext;
    public final INotificationManager mINotificationManager;
    public final IPeopleManager mIPeopleManager;
    public final LauncherApps mLauncherApps;
    public final PeopleSpaceWidgetManager mManager;
    public final CommonNotifCollection mNotifCollection;
    public final NotificationManager mNotificationManager;
    public final PackageManager mPackageManager;
    public final PeopleManager mPeopleManager;
    public boolean mRegisteredReceivers;
    public final SharedPreferences mSharedPrefs;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final Object mLock = new Object();
    public final UiEventLogger mUiEventLogger = new UiEventLoggerImpl();
    public final Map mNotificationKeyToWidgetIdsMatchedByUri = new HashMap();
    public final SparseBooleanArray mUpdatedPreviews = new SparseBooleanArray();
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            peopleSpaceWidgetManager.updateGeneratedPreviewForUser(((UserTrackerImpl) peopleSpaceWidgetManager.mUserTracker).getUserHandle());
        }
    };

    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$3, reason: invalid class name */
    public class AnonymousClass3 extends BroadcastReceiver {
        public AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5(2, this, intent));
        }
    }

    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$4, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType;

        static {
            int[] iArr = new int[PeopleBackupHelper.SharedFileEntryType.values().length];
            $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType = iArr;
            try {
                iArr[PeopleBackupHelper.SharedFileEntryType.WIDGET_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.SharedFileEntryType.PEOPLE_TILE_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.SharedFileEntryType.CONTACT_URI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.SharedFileEntryType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public class TileConversationListener implements PeopleManager.ConversationListener {
        public TileConversationListener() {
        }

        public final void onConversationUpdate(ConversationChannel conversationChannel) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5(3, this, conversationChannel));
        }
    }

    public PeopleSpaceWidgetManager(Context context, Optional<AppWidgetManager> optional, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional2, UserManager userManager, NotificationManager notificationManager, BroadcastDispatcher broadcastDispatcher, Executor executor, DumpManager dumpManager, UserTracker userTracker, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        new AnonymousClass2();
        this.mBaseBroadcastReceiver = new AnonymousClass3();
        this.mContext = context;
        this.mAppWidgetManagerOptional = optional;
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mLauncherApps = launcherApps;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPeopleManager = (PeopleManager) context.getSystemService(PeopleManager.class);
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mINotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        this.mBubblesOptional = optional2;
        this.mUserManager = userManager;
        this.mBackupManager = new BackupManager(context);
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBgExecutor = executor;
        dumpManager.registerNormalDumpable("PeopleSpaceWidgetMgr", this);
        this.mUserTracker = userTracker;
    }

    public static Set getNewWidgets(Set set, final Map map) {
        return (Set) set.stream().map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) map.get((String) obj);
            }
        }).filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(0)).collect(Collectors.toSet());
    }

    public final void addNewWidget(final int i, PeopleTileKey peopleTileKey) {
        PeopleTileKey keyFromStorageByWidgetId;
        try {
            PeopleSpaceTile tileFromPersistentStorage = getTileFromPersistentStorage(peopleTileKey, i, false);
            if (tileFromPersistentStorage == null) {
                return;
            }
            final PeopleSpaceTile peopleSpaceTileAugmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(tileFromPersistentStorage, Optional.of(Integer.valueOf(i)));
            synchronized (this.mLock) {
                keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
            }
            if (PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
                deleteWidgets(new int[]{i});
            } else {
                this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_ADDED);
            }
            synchronized (this.mLock) {
                PeopleSpaceUtils.setSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, peopleSpaceTileAugmentTileFromNotificationEntryManager.getContactUri(), this.mBackupManager);
            }
            registerConversationListenerIfNeeded(i, peopleTileKey);
            try {
                this.mLauncherApps.cacheShortcuts(peopleSpaceTileAugmentTileFromNotificationEntryManager.getPackageName(), Collections.singletonList(peopleSpaceTileAugmentTileFromNotificationEntryManager.getId()), peopleSpaceTileAugmentTileFromNotificationEntryManager.getUserHandle(), 2);
            } catch (Exception e) {
                Log.w("PeopleSpaceWidgetMgr", "failed to cache shortcut for widget " + i, e);
            }
            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
                    int i2 = i;
                    PeopleSpaceTile peopleSpaceTile = peopleSpaceTileAugmentTileFromNotificationEntryManager;
                    Map map = PeopleSpaceWidgetManager.mListeners;
                    peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(i2, peopleSpaceTile);
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
    /* JADX WARN: Type inference failed for: r10v16, types: [java.util.List] */
    public final PeopleSpaceTile augmentTileFromNotifications(PeopleSpaceTile peopleSpaceTile, PeopleTileKey peopleTileKey, final String str, Map map, Optional optional) {
        Person senderPerson;
        List<Notification.MessagingStyle.Message> messagingStyleMessages;
        boolean z = this.mPackageManager.checkPermission("android.permission.READ_CONTACTS", peopleSpaceTile.getPackageName()) == 0;
        ArrayList arrayList = new ArrayList();
        if (z) {
            final PackageManager packageManager = this.mPackageManager;
            PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
            arrayList = TextUtils.isEmpty(str) ? new ArrayList() : (List) map.entrySet().stream().flatMap(new PeopleSpaceUtils$$ExternalSyntheticLambda0()).filter(new Predicate() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    PackageManager packageManager2 = packageManager;
                    String str2 = str;
                    NotificationEntry notificationEntry = (NotificationEntry) obj;
                    PeopleTileKey peopleTileKey3 = PeopleSpaceUtils.EMPTY_KEY;
                    StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                    NotificationHelper.AnonymousClass1 anonymousClass1 = NotificationHelper.notificationEntryComparator;
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
        Set hashSet = (Set) map.get(peopleTileKey);
        if (hashSet == null) {
            hashSet = new HashSet();
        }
        if (hashSet.isEmpty() && arrayList.isEmpty()) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        hashSet.addAll(arrayList);
        PeopleTileKey peopleTileKey3 = PeopleSpaceUtils.EMPTY_KEY;
        Iterator it = hashSet.iterator();
        int size = 0;
        while (it.hasNext()) {
            Notification notification2 = ((NotificationEntry) it.next()).mSbn.getNotification();
            if (!NotificationHelper.isMissedCall(notification2) && (messagingStyleMessages = NotificationHelper.getMessagingStyleMessages(notification2)) != null) {
                size = messagingStyleMessages.size() + size;
            }
        }
        NotificationHelper.AnonymousClass1 anonymousClass1 = NotificationHelper.notificationEntryComparator;
        CharSequence name = null;
        NotificationEntry notificationEntry = hashSet.isEmpty() ? null : (NotificationEntry) hashSet.stream().filter(new NotificationHelper$$ExternalSyntheticLambda0()).sorted(NotificationHelper.notificationEntryComparator).findFirst().orElse(null);
        Context context = this.mContext;
        BackupManager backupManager = this.mBackupManager;
        if (notificationEntry == null || notificationEntry.mSbn.getNotification() == null) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Notification notification3 = statusBarNotification.getNotification();
        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
        String contactUri = NotificationHelper.getContactUri(statusBarNotification);
        if (optional.isPresent() && peopleSpaceTile.getContactUri() == null && !TextUtils.isEmpty(contactUri)) {
            Uri uri = Uri.parse(contactUri);
            PeopleSpaceUtils.setSharedPreferencesStorageForTile(context, new PeopleTileKey(peopleSpaceTile), ((Integer) optional.get()).intValue(), uri, backupManager);
            builder.setContactUri(uri);
        }
        boolean zIsMissedCall = NotificationHelper.isMissedCall(notification3);
        List<Notification.MessagingStyle.Message> messagingStyleMessages2 = NotificationHelper.getMessagingStyleMessages(notification3);
        if (!zIsMissedCall && ArrayUtils.isEmpty(messagingStyleMessages2)) {
            return PeopleSpaceUtils.removeNotificationFields(builder.build());
        }
        Notification.MessagingStyle.Message message = messagingStyleMessages2 != null ? messagingStyleMessages2.get(0) : null;
        CharSequence text = (!zIsMissedCall || ((message == null || TextUtils.isEmpty(message.getText())) ? false : true)) ? message.getText() : context.getString(R.string.missed_call);
        Uri dataUri = (message == null || !MessagingMessage.hasImage(message)) ? null : message.getDataUri();
        if (notification3.extras.getBoolean("android.isGroupConversation", false) && (senderPerson = message.getSenderPerson()) != null) {
            name = senderPerson.getName();
        }
        return builder.setLastInteractionTimestamp(statusBarNotification.getPostTime()).setNotificationKey(statusBarNotification.getKey()).setNotificationCategory(notification3.category).setNotificationContent(text).setNotificationSender(name).setNotificationDataUri(dataUri).setMessagesCount(size).build();
    }

    public final void deleteWidgets(int[] iArr) {
        PeopleTileKey peopleTileKey;
        HashSet hashSet;
        String string;
        for (int i : iArr) {
            this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_DELETED);
            synchronized (this.mLock) {
                try {
                    SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
                    peopleTileKey = new PeopleTileKey(sharedPreferences.getString("shortcut_id", null), sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), sharedPreferences.getString("package_name", null));
                    if (!PeopleTileKey.isValid(peopleTileKey)) {
                        Log.e("PeopleSpaceWidgetMgr", "Invalid tile key trying to remove widget " + i);
                        return;
                    }
                    hashSet = new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
                    string = this.mSharedPrefs.getString(String.valueOf(i), null);
                } finally {
                }
            }
            synchronized (this.mLock) {
                PeopleSpaceUtils.removeSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, string);
            }
            if (hashSet.contains(String.valueOf(i)) && hashSet.size() == 1) {
                Map map = mListeners;
                synchronized (map) {
                    try {
                        TileConversationListener tileConversationListener = (TileConversationListener) ((HashMap) map).get(peopleTileKey);
                        if (tileConversationListener != null) {
                            ((HashMap) map).remove(peopleTileKey);
                            this.mPeopleManager.unregisterConversationListener(tileConversationListener);
                        }
                    } finally {
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

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Trace.traceBegin(4096L, "PeopleSpaceWidgetMgr.dump");
        Map<String, ?> all = PreferenceManager.getDefaultSharedPreferences(this.mContext).getAll();
        printWriter.println("People widget list:");
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String key = entry.getKey();
            int i = AnonymousClass4.$SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.getEntryType(entry).ordinal()];
            if (i == 1) {
                SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(key, 0);
                printWriter.print("People widget (valid) [");
                printWriter.print(key);
                printWriter.print("] shortcut id: \"");
                printWriter.print(sharedPreferences.getString("shortcut_id", ""));
                printWriter.print("\", user id: ");
                printWriter.print(sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1));
                printWriter.print(", package: ");
                printWriter.println(sharedPreferences.getString("package_name", ""));
            } else if (i == 2 || i == 3) {
                printWriter.print("Extra data [");
                printWriter.print(key);
                printWriter.print(" : ");
                printWriter.print((Set) entry.getValue());
                printWriter.println("]");
            }
        }
        Trace.traceEnd(4096L);
    }

    public final PeopleTileKey getKeyFromStorageByWidgetId(int i) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), sharedPreferences.getString("package_name", ""));
    }

    public final Set getMatchingKeyWidgetIds(PeopleTileKey peopleTileKey) {
        return !PeopleTileKey.isValid(peopleTileKey) ? new HashSet() : new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
    }

    public final Set getMatchingUriWidgetIds(StatusBarNotification statusBarNotification, PeopleSpaceUtils.NotificationAction notificationAction) {
        String contactUri;
        if (notificationAction.equals(PeopleSpaceUtils.NotificationAction.POSTED)) {
            NotificationHelper.AnonymousClass1 anonymousClass1 = NotificationHelper.notificationEntryComparator;
            Notification notification2 = statusBarNotification.getNotification();
            HashSet hashSet = null;
            if ((notification2 == null ? false : NotificationHelper.isMissedCall(notification2)) && (contactUri = NotificationHelper.getContactUri(statusBarNotification)) != null) {
                HashSet hashSet2 = new HashSet(this.mSharedPrefs.getStringSet(contactUri, new HashSet()));
                if (!hashSet2.isEmpty()) {
                    hashSet = hashSet2;
                }
            }
            if (hashSet != null && !hashSet.isEmpty()) {
                ((HashMap) this.mNotificationKeyToWidgetIdsMatchedByUri).put(statusBarNotification.getKey(), hashSet);
                return hashSet;
            }
        } else {
            Set set = (Set) ((HashMap) this.mNotificationKeyToWidgetIdsMatchedByUri).remove(statusBarNotification.getKey());
            if (set != null && !set.isEmpty()) {
                return set;
            }
        }
        return new HashSet();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getNotificationPolicyState() {
        int currentInterruptionFilter;
        int i;
        NotificationManager.Policy notificationPolicy = this.mNotificationManager.getNotificationPolicy();
        if (!NotificationManager.Policy.areAllVisualEffectsSuppressed(notificationPolicy.suppressedVisualEffects) || (currentInterruptionFilter = this.mNotificationManager.getCurrentInterruptionFilter()) == 1) {
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
                }
                if (!notificationPolicy.allowMessages()) {
                }
            } else {
                i = 0;
                if (!notificationPolicy.allowMessages()) {
                    int iAllowMessagesFrom = notificationPolicy.allowMessagesFrom();
                    if (iAllowMessagesFrom == 1) {
                        return i | 16;
                    }
                    if (iAllowMessagesFrom != 2) {
                        return 1;
                    }
                    return i | 8;
                }
                if (i != 0) {
                    return i;
                }
            }
        }
        return 2;
    }

    public final boolean getPackageSuspended(PeopleSpaceTile peopleSpaceTile) {
        boolean z = !TextUtils.isEmpty(peopleSpaceTile.getPackageName()) && this.mPackageManager.isPackageSuspended(peopleSpaceTile.getPackageName());
        PackageManager packageManager = this.mPackageManager;
        String packageName = peopleSpaceTile.getPackageName();
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        packageManager.getApplicationInfoAsUser(packageName, 128, peopleSpaceTile.getUserHandle().getIdentifier());
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final RemoteViews getPreview(String str, UserHandle userHandle, String str2, Bundle bundle) {
        PeopleSpaceTile peopleSpaceTileBuild;
        try {
            ConversationChannel conversation = this.mIPeopleManager.getConversation(str2, userHandle.getIdentifier(), str);
            LauncherApps launcherApps = this.mLauncherApps;
            PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
            if (conversation != null) {
                peopleSpaceTileBuild = new PeopleSpaceTile.Builder(conversation, launcherApps).build();
                if (!((peopleSpaceTileBuild == null || TextUtils.isEmpty(peopleSpaceTileBuild.getUserName())) ? false : true)) {
                    Log.i("PeopleSpaceUtils", "PeopleSpaceTile is not valid");
                }
                if (peopleSpaceTileBuild != null) {
                    return null;
                }
                PeopleSpaceTile peopleSpaceTileAugmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(peopleSpaceTileBuild, Optional.empty());
                return PeopleTileViewHelper.createRemoteViews(this.mContext, peopleSpaceTileAugmentTileFromNotificationEntryManager, 0, bundle, new PeopleTileKey(peopleSpaceTileAugmentTileFromNotificationEntryManager));
            }
            Log.i("PeopleSpaceUtils", "ConversationChannel is null");
            peopleSpaceTileBuild = null;
            if (peopleSpaceTileBuild != null) {
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
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "Invalid tile key finding tile for existing widget ", "PeopleSpaceWidgetMgr");
            return null;
        }
        IPeopleManager iPeopleManager = this.mIPeopleManager;
        if (iPeopleManager == null || this.mLauncherApps == null) {
            Log.d("PeopleSpaceWidgetMgr", "System services are null");
            return null;
        }
        try {
            ConversationChannel conversation = iPeopleManager.getConversation(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId);
            if (conversation == null) {
                return null;
            }
            PeopleSpaceTile.Builder builder = new PeopleSpaceTile.Builder(conversation, this.mLauncherApps);
            String string = this.mSharedPrefs.getString(String.valueOf(i), null);
            if (z && string != null && builder.build().getContactUri() == null) {
                builder.setContactUri(Uri.parse(string));
            }
            return getTileWithCurrentState(builder.build(), PopupUIUtil.ACTION_BOOT_COMPLETED);
        } catch (RemoteException e) {
            Log.e("PeopleSpaceWidgetMgr", "getTileFromPersistentStorage failing for widget " + i, e);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PeopleSpaceTile getTileWithCurrentState(PeopleSpaceTile peopleSpaceTile, String str) {
        PeopleSpaceTile.Builder builder;
        builder = peopleSpaceTile.toBuilder();
        switch (str) {
            case "android.app.action.INTERRUPTION_FILTER_CHANGED":
                builder.setNotificationPolicyState(getNotificationPolicyState());
                break;
            case "android.intent.action.PACKAGES_SUSPENDED":
            case "android.intent.action.PACKAGES_UNSUSPENDED":
                builder.setIsPackageSuspended(getPackageSuspended(peopleSpaceTile));
                break;
            case "android.intent.action.MANAGED_PROFILE_AVAILABLE":
            case "android.intent.action.MANAGED_PROFILE_UNAVAILABLE":
            case "android.intent.action.USER_UNLOCKED":
                builder.setIsUserQuieted(peopleSpaceTile.getUserHandle() != null && this.mUserManager.isQuietModeEnabled(peopleSpaceTile.getUserHandle()));
                break;
            case "android.intent.action.LOCALE_CHANGED":
                break;
            case "android.intent.action.BOOT_COMPLETED":
            default:
                builder.setIsUserQuieted(peopleSpaceTile.getUserHandle() != null && this.mUserManager.isQuietModeEnabled(peopleSpaceTile.getUserHandle())).setIsPackageSuspended(getPackageSuspended(peopleSpaceTile)).setNotificationPolicyState(getNotificationPolicyState());
                break;
        }
        return builder.build();
    }

    public final Map groupConversationNotifications(Collection collection) {
        return (Map) collection.stream().filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(this, 0)).collect(Collectors.groupingBy(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(0), Collectors.mapping(Function.identity(), Collectors.toSet())));
    }

    public final void registerConversationListenerIfNeeded(int i, PeopleTileKey peopleTileKey) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Invalid tile key registering listener for widget ", "PeopleSpaceWidgetMgr");
            return;
        }
        TileConversationListener tileConversationListener = new TileConversationListener();
        Map map = mListeners;
        synchronized (map) {
            try {
                if (((HashMap) map).containsKey(peopleTileKey)) {
                    return;
                }
                ((HashMap) map).put(peopleTileKey, tileConversationListener);
                this.mPeopleManager.registerConversationListener(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId, tileConversationListener, this.mContext.getMainExecutor());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAppWidgetOptionsAndView(int i, PeopleSpaceTile peopleSpaceTile) {
        if (this.mAppWidgetManagerOptional.isEmpty()) {
            return;
        }
        if (peopleSpaceTile == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Storing null tile for widget ", "PeopleSpaceWidgetMgr");
        }
        Map map = mTiles;
        synchronized (map) {
            ((HashMap) map).put(Integer.valueOf(i), peopleSpaceTile);
        }
        Bundle appWidgetOptions = ((AppWidgetManager) this.mAppWidgetManagerOptional.get()).getAppWidgetOptions(i);
        if (this.mAppWidgetManagerOptional.isEmpty()) {
            return;
        }
        PeopleTileKey keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
        if (!PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "Invalid tile key updating widget ", "PeopleSpaceWidgetMgr");
        } else {
            ((AppWidgetManager) this.mAppWidgetManagerOptional.get()).updateAppWidget(i, PeopleTileViewHelper.createRemoteViews(this.mContext, peopleSpaceTile, i, appWidgetOptions, keyFromStorageByWidgetId));
        }
    }

    public void updateGeneratedPreviewForUser(UserHandle userHandle) {
        if (this.mUpdatedPreviews.get(userHandle.getIdentifier()) || !this.mUserManager.isUserUnlocked(userHandle) || this.mAppWidgetManagerOptional.isEmpty()) {
            return;
        }
        ComponentName componentName = new ComponentName(this.mContext, (Class<?>) PeopleSpaceWidgetProvider.class);
        if (((AppWidgetManager) this.mAppWidgetManagerOptional.get()).getInstalledProvidersForPackage(this.mContext.getPackageName(), userHandle).stream().noneMatch(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(componentName, 1))) {
            return;
        }
        this.mUpdatedPreviews.put(userHandle.getIdentifier(), ((AppWidgetManager) this.mAppWidgetManagerOptional.get()).setWidgetPreview(componentName, 3, new RemoteViews(this.mContext.getPackageName(), R.layout.people_space_placeholder_layout)));
    }

    public final void updateSingleConversationWidgets(final int[] iArr) {
        final HashMap map = new HashMap();
        for (int i : iArr) {
            PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
            if (tileForExistingWidget == null) {
                ClockEventController$$ExternalSyntheticOutline0.m(i, "Matching conversation not found for widget ", "PeopleSpaceWidgetMgr");
            }
            updateAppWidgetOptionsAndView(i, tileForExistingWidget);
            map.put(Integer.valueOf(i), tileForExistingWidget);
            if (tileForExistingWidget != null) {
                registerConversationListenerIfNeeded(i, new PeopleTileKey(tileForExistingWidget));
            }
        }
        final Context context = this.mContext;
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        final PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mManager;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                PeopleSpaceUtils.getDataFromContacts(context, peopleSpaceWidgetManager, map, iArr);
            }
        });
    }

    public final void updateStorageAndViewWithConversationData(ConversationChannel conversationChannel, int i) {
        PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
        if (tileForExistingWidget == null) {
            return;
        }
        PeopleSpaceTile.Builder builder = tileForExistingWidget.toBuilder();
        ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
        Uri uri = null;
        if (shortcutInfo.getPersons() != null && shortcutInfo.getPersons().length > 0) {
            Person person = shortcutInfo.getPersons()[0];
            if (person.getUri() != null) {
                uri = Uri.parse(person.getUri());
            }
        }
        CharSequence label = shortcutInfo.getLabel();
        if (label != null) {
            builder.setUserName(label);
        }
        Icon iconConvertDrawableToIcon = PeopleSpaceTile.convertDrawableToIcon(this.mLauncherApps.getShortcutIconDrawable(shortcutInfo, 0));
        if (iconConvertDrawableToIcon != null) {
            builder.setUserIcon(iconConvertDrawableToIcon);
        }
        NotificationChannel notificationChannel = conversationChannel.getNotificationChannel();
        if (notificationChannel != null) {
            builder.setIsImportantConversation(notificationChannel.isImportantConversation());
        }
        builder.setContactUri(uri).setStatuses(conversationChannel.getStatuses()).setLastInteractionTimestamp(conversationChannel.getLastEventTimestamp());
        updateAppWidgetOptionsAndView(i, builder.build());
    }

    public final void updateWidgetIdsBasedOnNotifications(Set set, Collection collection) {
        if (((HashSet) set).isEmpty()) {
            return;
        }
        try {
            final Map mapGroupConversationNotifications = groupConversationNotifications(collection);
            ((Map) set.stream().map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(3)).collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda14
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
                    Map map = mapGroupConversationNotifications;
                    Integer num = (Integer) obj;
                    Map map2 = PeopleSpaceWidgetManager.mListeners;
                    peopleSpaceWidgetManager.getClass();
                    int iIntValue = num.intValue();
                    PeopleSpaceTile tileForExistingWidget = peopleSpaceWidgetManager.getTileForExistingWidget(iIntValue);
                    if (tileForExistingWidget != null) {
                        return Optional.ofNullable(peopleSpaceWidgetManager.augmentTileFromNotifications(tileForExistingWidget, new PeopleTileKey(tileForExistingWidget), peopleSpaceWidgetManager.mSharedPrefs.getString(String.valueOf(iIntValue), null), map, Optional.of(num)));
                    }
                    Log.w("PeopleSpaceWidgetMgr", "Null tile for existing widget " + iIntValue + ", skipping update.");
                    return Optional.empty();
                }
            }))).forEach(new BiConsumer() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda15
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
                    Optional optional = (Optional) obj2;
                    Map map = PeopleSpaceWidgetManager.mListeners;
                    peopleSpaceWidgetManager.getClass();
                    int iIntValue = ((Integer) obj).intValue();
                    if (optional.isPresent()) {
                        peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(iIntValue, (PeopleSpaceTile) optional.get());
                    }
                }
            });
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "updateWidgetIdsBasedOnNotifications failing", e);
        }
    }

    public void updateWidgetsFromBroadcastInBackground(String str) {
        int[] appWidgetIds;
        if (this.mAppWidgetManagerOptional.isEmpty() || (appWidgetIds = ((AppWidgetManager) this.mAppWidgetManagerOptional.get()).getAppWidgetIds(new ComponentName(this.mContext, (Class<?>) PeopleSpaceWidgetProvider.class))) == null) {
            return;
        }
        for (int i : appWidgetIds) {
            try {
                synchronized (this.mLock) {
                    try {
                        PeopleSpaceTile tileForExistingWidgetThrowing = getTileForExistingWidgetThrowing(i);
                        if (tileForExistingWidgetThrowing == null) {
                            Log.e("PeopleSpaceWidgetMgr", "Matching conversation not found for widget " + i);
                        } else {
                            updateAppWidgetOptionsAndView(i, getTileWithCurrentState(tileForExistingWidgetThrowing, str));
                        }
                    } catch (Throwable th) {
                        throw th;
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

    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$2, reason: invalid class name */
    public class AnonymousClass2 implements NotificationListener.NotificationHandler {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            if (!peopleSpaceWidgetManager.mAppWidgetManagerOptional.isEmpty() && notificationChannel.isConversation()) {
                peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5(1, this, userHandle));
            }
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            PeopleSpaceUtils.NotificationAction notificationAction = PeopleSpaceUtils.NotificationAction.POSTED;
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda12(peopleSpaceWidgetManager, statusBarNotification, notificationAction, ((NotifPipeline) peopleSpaceWidgetManager.mNotifCollection).getAllNotifs()));
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            PeopleSpaceUtils.NotificationAction notificationAction = PeopleSpaceUtils.NotificationAction.REMOVED;
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda12(peopleSpaceWidgetManager, statusBarNotification, notificationAction, ((NotifPipeline) peopleSpaceWidgetManager.mNotifCollection).getAllNotifs()));
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public final void onNotificationsInitialized() {
        }
    }

    public PeopleSpaceWidgetManager(Context context, Optional<AppWidgetManager> optional, IPeopleManager iPeopleManager, PeopleManager peopleManager, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional2, UserManager userManager, BackupManager backupManager, INotificationManager iNotificationManager, NotificationManager notificationManager, Executor executor, UserTracker userTracker) {
        new AnonymousClass2();
        this.mBaseBroadcastReceiver = new AnonymousClass3();
        this.mContext = context;
        this.mAppWidgetManagerOptional = optional;
        this.mIPeopleManager = iPeopleManager;
        this.mPeopleManager = peopleManager;
        this.mLauncherApps = launcherApps;
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mBubblesOptional = optional2;
        this.mUserManager = userManager;
        this.mBackupManager = backupManager;
        this.mINotificationManager = iNotificationManager;
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mBgExecutor = executor;
        this.mUserTracker = userTracker;
    }
}
