package com.android.systemui.settings;

import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.semantics.SemanticsPropertiesKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Assert;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class UserTrackerImpl extends BroadcastReceiver implements UserTracker, Dumpable {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final CoroutineDispatcher backgroundContext;
    public final Handler backgroundHandler;
    public final Context context;
    public final DumpManager dumpManager;
    public final Provider featureFlagsProvider;
    public final IActivityManager iActivityManager;
    public boolean initialized;
    public final SynchronizedDelegate userContext$delegate;
    public final SynchronizedDelegate userHandle$delegate;
    public final SynchronizedDelegate userId$delegate;
    public final UserManager userManager;
    public final Object mutex = new Object();
    public final SynchronizedDelegate userProfiles$delegate = new SynchronizedDelegate(EmptyList.INSTANCE);
    public final List callbacks = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(UserTrackerImpl.class, "userId", "getUserId()I", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        MutablePropertyReference1Impl mutablePropertyReference1Impl2 = new MutablePropertyReference1Impl(UserTrackerImpl.class, "userHandle", "getUserHandle()Landroid/os/UserHandle;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl, mutablePropertyReference1Impl2, SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(UserTrackerImpl.class, "userContext", "getUserContext()Landroid/content/Context;", 0, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(UserTrackerImpl.class, "userProfiles", "getUserProfiles()Ljava/util/List;", 0, reflectionFactory)};
        new Companion(null);
    }

    public UserTrackerImpl(Context context, Provider provider, UserManager userManager, IActivityManager iActivityManager, DumpManager dumpManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        this.context = context;
        this.featureFlagsProvider = provider;
        this.userManager = userManager;
        this.iActivityManager = iActivityManager;
        this.dumpManager = dumpManager;
        this.backgroundHandler = handler;
        this.userId$delegate = new SynchronizedDelegate(Integer.valueOf(context.getUserId()));
        this.userHandle$delegate = new SynchronizedDelegate(context.getUser());
        this.userContext$delegate = new SynchronizedDelegate(context);
    }

    public final void addCallback(UserTracker.Callback callback, Executor executor) {
        synchronized (this.callbacks) {
            ((ArrayList) this.callbacks).add(new DataItem(new WeakReference(callback), executor));
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        List list;
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("Initialized: ", this.initialized, printWriter);
        if (this.initialized) {
            printWriter.println("userId: " + getUserId());
            List userProfiles = getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            Iterator it = userProfiles.iterator();
            while (it.hasNext()) {
                arrayList.add(((UserInfo) it.next()).toFullString());
            }
            printWriter.println("userProfiles: " + arrayList);
        }
        synchronized (this.callbacks) {
            list = CollectionsKt___CollectionsKt.toList(this.callbacks);
        }
        printWriter.println("Callbacks:");
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            UserTracker.Callback callback = (UserTracker.Callback) ((DataItem) it2.next()).callback.get();
            if (callback != null) {
                printWriter.println("  " + callback);
            }
        }
    }

    public final Context getUserContext() {
        return (Context) this.userContext$delegate.getValue(this, $$delegatedProperties[2]);
    }

    public final UserHandle getUserHandle() {
        return (UserHandle) this.userHandle$delegate.getValue(this, $$delegatedProperties[1]);
    }

    public final int getUserId() {
        return ((Number) this.userId$delegate.getValue(this, $$delegatedProperties[0])).intValue();
    }

    public final UserInfo getUserInfo() {
        int userId = getUserId();
        for (UserInfo userInfo : getUserProfiles()) {
            if (userInfo.id == userId) {
                return userInfo;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public final List getUserProfiles() {
        return (List) this.userProfiles$delegate.getValue(this, $$delegatedProperties[3]);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        List<DataItem> list;
        String action = intent.getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1823736795:
                    if (!action.equals("android.intent.action.PROFILE_REMOVED")) {
                        return;
                    }
                    break;
                case -1462075554:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_UNLOCKED")) {
                        return;
                    }
                    break;
                case -1238404651:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                        return;
                    }
                    break;
                case -1112607227:
                    if (!action.equals("android.intent.action.PROFILE_ADDED")) {
                        return;
                    }
                    break;
                case -1062385259:
                    if (!action.equals("android.intent.action.PROFILE_UNAVAILABLE")) {
                        return;
                    }
                    break;
                case -864107122:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                        return;
                    }
                    break;
                case -385593787:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_ADDED")) {
                        return;
                    }
                    break;
                case -201513518:
                    if (!action.equals("android.intent.action.USER_INFO_CHANGED")) {
                        return;
                    }
                    break;
                case -19011148:
                    if (!action.equals("android.intent.action.LOCALE_CHANGED")) {
                        return;
                    }
                    break;
                case 1051477093:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_REMOVED")) {
                        return;
                    }
                    break;
                case 2014285134:
                    if (!action.equals("android.intent.action.PROFILE_AVAILABLE")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            Assert.isNotMainThread();
            final List profiles = this.userManager.getProfiles(getUserId());
            synchronized (this.mutex) {
                try {
                    Intrinsics.checkNotNull(profiles);
                    List list2 = profiles;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new UserInfo((UserInfo) it.next()));
                    }
                    SynchronizedDelegate synchronizedDelegate = this.userProfiles$delegate;
                    KProperty kProperty = $$delegatedProperties[3];
                    synchronizedDelegate.setValue(this, arrayList);
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            synchronized (this.callbacks) {
                list = CollectionsKt___CollectionsKt.toList(this.callbacks);
            }
            final CountDownLatch countDownLatch = new CountDownLatch(list.size());
            for (DataItem dataItem : list) {
                final UserTracker.Callback callback = (UserTracker.Callback) dataItem.callback.get();
                if (callback != null) {
                    dataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserTracker.Callback callback2 = UserTracker.Callback.this;
                            final CountDownLatch countDownLatch2 = countDownLatch;
                            new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    countDownLatch2.countDown();
                                }
                            };
                            Intrinsics.checkNotNull(profiles);
                            callback2.onProfilesChanged(profiles);
                        }
                    });
                } else {
                    countDownLatch.countDown();
                }
            }
        }
    }

    public final void removeCallback(final UserTracker.Callback callback) {
        synchronized (this.callbacks) {
            ((ArrayList) this.callbacks).removeIf(new Predicate() { // from class: com.android.systemui.settings.UserTrackerImpl$removeCallback$1$1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    UserTracker.Callback callback2 = UserTracker.Callback.this;
                    UserTracker.Callback callback3 = (UserTracker.Callback) ((DataItem) obj).callback.get();
                    if (callback3 != null) {
                        return callback3.equals(callback2);
                    }
                    return true;
                }
            });
        }
    }

    public final void setUserIdInternal(int i) {
        List profiles = this.userManager.getProfiles(i);
        UserHandle userHandle = new UserHandle(i);
        Context createContextAsUser = this.context.createContextAsUser(userHandle, 0);
        synchronized (this.mutex) {
            try {
                SynchronizedDelegate synchronizedDelegate = this.userId$delegate;
                KProperty[] kPropertyArr = $$delegatedProperties;
                KProperty kProperty = kPropertyArr[0];
                synchronizedDelegate.setValue(this, Integer.valueOf(i));
                SynchronizedDelegate synchronizedDelegate2 = this.userHandle$delegate;
                KProperty kProperty2 = kPropertyArr[1];
                synchronizedDelegate2.setValue(this, userHandle);
                SynchronizedDelegate synchronizedDelegate3 = this.userContext$delegate;
                KProperty kProperty3 = kPropertyArr[2];
                synchronizedDelegate3.setValue(this, createContextAsUser);
                Intrinsics.checkNotNull(profiles);
                List list = profiles;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new UserInfo((UserInfo) it.next()));
                }
                SynchronizedDelegate synchronizedDelegate4 = this.userProfiles$delegate;
                KProperty kProperty4 = $$delegatedProperties[3];
                synchronizedDelegate4.setValue(this, arrayList);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        new Pair(createContextAsUser, profiles);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SynchronizedDelegate implements ReadWriteProperty {
        public Object value;

        public SynchronizedDelegate(Object obj) {
            this.value = obj;
        }

        @Override // kotlin.properties.ReadWriteProperty
        public final Object getValue(UserTrackerImpl userTrackerImpl, KProperty kProperty) {
            Object obj;
            if (!userTrackerImpl.initialized) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Must initialize before getting ", kProperty.getName()));
            }
            synchronized (userTrackerImpl.mutex) {
                obj = this.value;
            }
            return obj;
        }

        public final void setValue(UserTrackerImpl userTrackerImpl, Object obj) {
            synchronized (userTrackerImpl.mutex) {
                this.value = obj;
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // kotlin.properties.ReadWriteProperty
        public final /* bridge */ /* synthetic */ void setValue(Object obj, KProperty kProperty, Object obj2) {
            setValue((UserTrackerImpl) obj, obj2);
        }
    }
}
