package com.android.wm.shell.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.SparseArray;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserProfileContexts {
    public final Context baseContext;
    public final SparseArray currentProfilesContext = new SparseArray();
    public final ShellController shellController;
    public final int shellUserId;

    public UserProfileContexts(Context context, ShellController shellController, ShellInit shellInit) {
        this.baseContext = context;
        this.shellController = shellController;
        this.shellUserId = context.getUserId();
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.UserProfileContexts.1
            @Override // java.lang.Runnable
            public final void run() {
                final UserProfileContexts userProfileContexts = UserProfileContexts.this;
                userProfileContexts.getClass();
                userProfileContexts.shellController.addUserChangeListener(new UserChangeListener() { // from class: com.android.wm.shell.common.UserProfileContexts$onInit$1
                    @Override // com.android.wm.shell.sysui.UserChangeListener
                    public final void onUserChanged(int i, Context context2) {
                        UserProfileContexts userProfileContexts2 = UserProfileContexts.this;
                        userProfileContexts2.currentProfilesContext.clear();
                        userProfileContexts2.getClass();
                        userProfileContexts2.currentProfilesContext.put(i, context2);
                        int i2 = userProfileContexts2.shellUserId;
                        if (i != i2) {
                            userProfileContexts2.currentProfilesContext.put(i2, userProfileContexts2.baseContext);
                        }
                    }

                    @Override // com.android.wm.shell.sysui.UserChangeListener
                    public final void onUserProfilesChanged(List list) {
                        UserProfileContexts.this.updateProfilesContexts(list);
                    }
                });
                int currentUser = ActivityManager.getCurrentUser();
                UserManager userManager = (UserManager) userProfileContexts.baseContext.getSystemService(UserManager.class);
                userProfileContexts.baseContext.createContextAsUser(UserHandle.of(currentUser), 0);
                userProfileContexts.updateProfilesContexts(userManager.getProfiles(currentUser));
            }
        }, this);
    }

    public final void updateProfilesContexts(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if (!this.currentProfilesContext.contains(userInfo.id)) {
                this.currentProfilesContext.put(userInfo.id, this.baseContext.createContextAsUser(userInfo.getUserHandle(), 0));
            }
        }
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        int size = this.currentProfilesContext.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.currentProfilesContext.keyAt(i);
            if (keyAt != this.shellUserId) {
                List list2 = list;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    Iterator it2 = list2.iterator();
                    while (it2.hasNext()) {
                        if (((UserInfo) it2.next()).id == keyAt) {
                            break;
                        }
                    }
                }
                createListBuilder.add(Integer.valueOf(keyAt));
            }
        }
        ListIterator listIterator = createListBuilder.build().listIterator(0);
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
            if (!itr.hasNext()) {
                return;
            }
            this.currentProfilesContext.remove(((Number) itr.next()).intValue());
        }
    }
}
