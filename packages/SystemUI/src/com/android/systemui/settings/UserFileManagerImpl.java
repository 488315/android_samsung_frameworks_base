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
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
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

/* loaded from: classes3.dex */
public final class UserFileManagerImpl implements UserFileManager, CoreStartable {
    public static final Companion Companion = new Companion(null);
    public final DelayableExecutor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final UserFileManagerImpl$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.settings.UserFileManagerImpl$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.USER_REMOVED")) {
                this.this$0.clearDeletedUserData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }
        }
    };
    public final Context context;
    public final UserManager userManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static File createFile(int i, String str) {
            return new UserHandle(i).isSystem() ? new File(str) : new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "__USER_", "_"), str));
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.settings.UserFileManagerImpl$broadcastReceiver$1] */
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
        File[] fileArrListFiles = file.listFiles(new FilenameFilter() { // from class: com.android.systemui.settings.UserFileManagerImpl$deleteFiles$filesToDelete$1
            @Override // java.io.FilenameFilter
            public final boolean accept(File file2, String str) {
                str.getClass();
                if (!str.startsWith("__USER_")) {
                    return false;
                }
                List list = arrayList;
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : list) {
                    if (str.startsWith((String) obj)) {
                        arrayList2.add(obj);
                    }
                }
                return arrayList2.isEmpty();
            }
        });
        if (fileArrListFiles == null) {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Empty directory: ", file.getPath(), "UserFileManagerImpl");
            return;
        }
        for (File file2 : fileArrListFiles) {
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
        boolean zAreEqual = Intrinsics.areEqual(file.getName(), "UserFileManager");
        file.delete();
        if (zAreEqual) {
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
                UserFileManagerImpl userFileManagerImpl = this.this$0;
                UserFileManagerImpl.access$deleteFiles(userFileManagerImpl, userFileManagerImpl.context.getFilesDir());
                UserFileManagerImpl.access$deleteFiles(this.this$0, new File(this.this$0.context.getDataDir(), "shared_prefs"));
            }
        });
    }

    public final File getFile(int i, String str) {
        File filesDir = this.context.getFilesDir();
        Companion.getClass();
        File file = new File(filesDir, Companion.createFile(i, str).getPath());
        File fileBuildPath = new UserHandle(i).isSystem() ? null : Environment.buildPath(this.context.getFilesDir(), new String[]{"UserFileManager", String.valueOf(i), "files", str});
        if (fileBuildPath != null) {
            migrate(file, fileBuildPath);
        }
        return file;
    }

    public final SharedPreferences getSharedPreferences$1(int i, String str) {
        Companion.getClass();
        File fileCreateFile = Companion.createFile(i, str);
        File fileBuildPath = new UserHandle(i).isSystem() ? null : Environment.buildPath(this.context.getFilesDir(), new String[]{"UserFileManager", String.valueOf(i), "shared_prefs", str.concat(".xml")});
        if (fileBuildPath != null) {
            File fileBuildPath2 = Environment.buildPath(this.context.getDataDir(), new String[]{"shared_prefs", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(fileCreateFile.getPath(), ".xml")});
            fileBuildPath2.getClass();
            migrate(fileBuildPath2, fileBuildPath);
        }
        return this.context.getSharedPreferences(fileCreateFile.getPath(), 0);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        clearDeletedUserData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.broadcastReceiver, intentFilter, this.backgroundExecutor, null, 0, null, 56);
    }
}
