package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class DesktopUserRepositories implements UserChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DesktopUserRepositories$desktopRepoByUserId$1 desktopRepoByUserId;
    public final CoroutineScope mainCoroutineScope;
    public final DesktopPersistentRepository persistentRepository;
    public final DesktopRepositoryInitializer repositoryInitializer;
    public final ShellController shellController;
    public int userId;
    public final Map userIdToProfileIdsMap;
    public final UserManager userManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DesktopUserRepositories(ShellInit shellInit, ShellController shellController, DesktopPersistentRepository desktopPersistentRepository, DesktopRepositoryInitializer desktopRepositoryInitializer, CoroutineScope coroutineScope, UserManager userManager, DesktopState desktopState, DesktopConfig desktopConfig) {
        this.shellController = shellController;
        this.persistentRepository = desktopPersistentRepository;
        this.repositoryInitializer = desktopRepositoryInitializer;
        this.mainCoroutineScope = coroutineScope;
        this.userManager = userManager;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.userIdToProfileIdsMap = linkedHashMap;
        this.desktopRepoByUserId = new DesktopUserRepositories$desktopRepoByUserId$1(this, desktopConfig);
        this.userId = ActivityManager.getCurrentUser();
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopUserRepositories.1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopUserRepositories desktopUserRepositories = DesktopUserRepositories.this;
                    ((DesktopRepositoryInitializerImpl) desktopUserRepositories.repositoryInitializer).initialize(desktopUserRepositories);
                    desktopUserRepositories.shellController.addUserChangeListener(desktopUserRepositories);
                    if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue() && DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        int currentUser = ActivityManager.getCurrentUser();
                        desktopUserRepositories.userId = currentUser;
                        Map map = desktopUserRepositories.userIdToProfileIdsMap;
                        Integer numValueOf = Integer.valueOf(currentUser);
                        List profiles = desktopUserRepositories.userManager.getProfiles(desktopUserRepositories.userId);
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(profiles, 10));
                        Iterator it = profiles.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                        }
                        map.put(numValueOf, arrayList);
                    }
                }
            }, this);
        }
        if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue() || DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            return;
        }
        Integer numValueOf = Integer.valueOf(this.userId);
        List profiles = userManager.getProfiles(this.userId);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(profiles, 10));
        Iterator it = profiles.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
        }
        linkedHashMap.put(numValueOf, arrayList);
    }

    public static void logD$3(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopUserRepositories", objArr);
        ProtoLog.d(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final DesktopRepository getCurrent() {
        return this.desktopRepoByUserId.getOrCreate(this.userId);
    }

    public final DesktopRepository getProfile(int i) {
        boolean zIsTrue = DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue();
        DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = this.desktopRepoByUserId;
        if (zIsTrue) {
            for (Map.Entry entry : ((LinkedHashMap) this.userIdToProfileIdsMap).entrySet()) {
                int iIntValue = ((Number) entry.getKey()).intValue();
                if (((List) entry.getValue()).contains(Integer.valueOf(i))) {
                    return desktopUserRepositories$desktopRepoByUserId$1.getOrCreate(iIntValue);
                }
            }
        }
        return desktopUserRepositories$desktopRepoByUserId$1.getOrCreate(i);
    }

    public final Set getRepositoriesWithDeskId(int i) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = this.desktopRepoByUserId;
        int size = desktopUserRepositories$desktopRepoByUserId$1.size();
        for (int i2 = 0; i2 < size; i2++) {
            desktopUserRepositories$desktopRepoByUserId$1.keyAt(i2);
            DesktopRepository desktopRepository = (DesktopRepository) desktopUserRepositories$desktopRepoByUserId$1.valueAt(i2);
            if (desktopRepository.getAllDeskIds().contains(Integer.valueOf(i))) {
                linkedHashSet.add(desktopRepository);
            }
        }
        return linkedHashSet;
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged(int i, Context context) {
        logD$3("onUserChanged previousUserId=%d, newUserId=%d", Integer.valueOf(this.userId), Integer.valueOf(i));
        this.userId = i;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue()) {
            List aliveUsers = this.userManager.getAliveUsers();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(aliveUsers, 10));
            Iterator it = aliveUsers.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            Set setKeySet = ((LinkedHashMap) this.userIdToProfileIdsMap).keySet();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : setKeySet) {
                if (!arrayList.contains(Integer.valueOf(((Number) obj).intValue()))) {
                    arrayList2.add(obj);
                }
            }
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                int iIntValue = ((Number) obj2).intValue();
                this.userIdToProfileIdsMap.remove(Integer.valueOf(iIntValue));
                this.desktopRepoByUserId.remove(iIntValue);
            }
            BuildersKt.launch$default(this.mainCoroutineScope, null, null, new DesktopUserRepositories$sanitizeUsers$2(this, arrayList2, null), 3);
        }
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserProfilesChanged(List list) {
        logD$3("onUserProfilesChanged profiles=%s", list.toString());
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue()) {
            Map map = this.userIdToProfileIdsMap;
            Integer numValueOf = Integer.valueOf(this.userId);
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            map.put(numValueOf, arrayList);
        }
    }
}
