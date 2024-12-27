package com.android.systemui.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import android.os.Environment;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class UserFileManagerImpl implements UserFileManager, CoreStartable {
    public static final Companion Companion = new Companion(null);
    public final DelayableExecutor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final UserFileManagerImpl$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.settings.UserFileManagerImpl$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.USER_REMOVED")) {
                UserFileManagerImpl.this.clearDeletedUserData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }
        }
    };
    public final Context context;
    public final UserManager userManager;

    public final class Companion {
        private Companion() {
        }

        public static File createFile(int i, String str) {
            return new UserHandle(i).isSystem() ? new File(str) : new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "__USER_", "_"), str));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public UserFileManagerImpl(Context context, UserManager userManager, BroadcastDispatcher broadcastDispatcher, DelayableExecutor delayableExecutor) {
        this.context = context;
        this.userManager = userManager;
        this.broadcastDispatcher = broadcastDispatcher;
        this.backgroundExecutor = delayableExecutor;
    }

    public static final void access$deleteFiles(UserFileManagerImpl userFileManagerImpl, File file) {
        List aliveUsers = userFileManagerImpl.userManager.getAliveUsers();
        final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(aliveUsers, 10));
        Iterator it = aliveUsers.iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            Companion.getClass();
            arrayList.add("__USER_" + i + "_");
        }
        File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.android.systemui.settings.UserFileManagerImpl$deleteFiles$filesToDelete$1
            @Override // java.io.FilenameFilter
            public final boolean accept(File file2, String str) {
                Intrinsics.checkNotNull(str);
                if (str.startsWith("__USER_")) {
                    List list = arrayList;
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj : list) {
                        if (str.startsWith((String) obj)) {
                            arrayList2.add(obj);
                        }
                    }
                    if (arrayList2.isEmpty()) {
                        return true;
                    }
                }
                return false;
            }
        });
        if (listFiles == null) {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Empty directory: ", file.getPath(), "UserFileManagerImpl");
            return;
        }
        for (File file2 : listFiles) {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Deleting file: ", file2.getPath(), "UserFileManagerImpl");
            try {
                file2.delete();
            } catch (Exception e) {
                Log.e("UserFileManagerImpl", "Deletion failed.", e);
            }
        }
    }

    public static void deleteParentDirsIfEmpty(File file) {
        if (file == null || file.listFiles().length != 0) {
            return;
        }
        File parentFile = file.getParentFile();
        boolean areEqual = Intrinsics.areEqual(file.getName(), "UserFileManager");
        file.delete();
        if (areEqual) {
            return;
        }
        deleteParentDirsIfEmpty(parentFile);
    }

    public static void migrate(File file, File file2) {
        if (file2.exists()) {
            try {
                File parentFile = file2.getParentFile();
                file2.renameTo(file);
                deleteParentDirsIfEmpty(parentFile);
            } catch (Exception e) {
                Log.e("UserFileManagerImpl", "Failed to rename and delete " + file2.getPath(), e);
            }
        }
    }

    public final void clearDeletedUserData$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.settings.UserFileManagerImpl$clearDeletedUserData$1
            @Override // java.lang.Runnable
            public final void run() {
                UserFileManagerImpl userFileManagerImpl = UserFileManagerImpl.this;
                UserFileManagerImpl.access$deleteFiles(userFileManagerImpl, userFileManagerImpl.context.getFilesDir());
                UserFileManagerImpl.access$deleteFiles(UserFileManagerImpl.this, new File(UserFileManagerImpl.this.context.getDataDir(), "shared_prefs"));
            }
        });
    }

    public final File getFile(int i, String str) {
        File filesDir = this.context.getFilesDir();
        Companion.getClass();
        File file = new File(filesDir, Companion.createFile(i, str).getPath());
        File buildPath = new UserHandle(i).isSystem() ? null : Environment.buildPath(this.context.getFilesDir(), new String[]{"UserFileManager", String.valueOf(i), "files", str});
        if (buildPath != null) {
            migrate(file, buildPath);
        }
        return file;
    }

    public final SharedPreferences getSharedPreferences$1(int i, String str) {
        Companion.getClass();
        File createFile = Companion.createFile(i, str);
        File buildPath = new UserHandle(i).isSystem() ? null : Environment.buildPath(this.context.getFilesDir(), new String[]{"UserFileManager", String.valueOf(i), "shared_prefs", str.concat(".xml")});
        if (buildPath != null) {
            File buildPath2 = Environment.buildPath(this.context.getDataDir(), new String[]{"shared_prefs", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(createFile.getPath(), ".xml")});
            Intrinsics.checkNotNull(buildPath2);
            migrate(buildPath2, buildPath);
        }
        return this.context.getSharedPreferences(createFile.getPath(), 0);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        clearDeletedUserData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.broadcastReceiver, intentFilter, this.backgroundExecutor, null, 0, null, 56);
    }
}
