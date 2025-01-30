package com.android.systemui.statusbar.phone;

import android.app.StatusBarManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.systemui.Dependency;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ManagedProfileControllerImpl implements ManagedProfileController {
    public final Context mContext;
    public int mCurrentUser;
    public boolean mListening;
    public final Executor mMainExecutor;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final List mCallbacks = new ArrayList();
    public final UserTrackerCallback mUserTrackerCallback = new UserTrackerCallback(this, 0);
    public final LinkedList mProfiles = new LinkedList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserTrackerCallback implements UserTracker.Callback {
        public /* synthetic */ UserTrackerCallback(ManagedProfileControllerImpl managedProfileControllerImpl, int i) {
            this();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
            managedProfileControllerImpl.reloadManagedProfiles();
            Iterator it = ((ArrayList) managedProfileControllerImpl.mCallbacks).iterator();
            while (it.hasNext()) {
                ((ManagedProfileController.Callback) it.next()).onManagedProfileChanged();
            }
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
            managedProfileControllerImpl.reloadManagedProfiles();
            Iterator it = ((ArrayList) managedProfileControllerImpl.mCallbacks).iterator();
            while (it.hasNext()) {
                ((ManagedProfileController.Callback) it.next()).onManagedProfileChanged();
            }
        }

        private UserTrackerCallback() {
        }
    }

    public ManagedProfileControllerImpl(Context context, Executor executor, UserTracker userTracker, UserManager userManager) {
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ManagedProfileController.Callback callback = (ManagedProfileController.Callback) obj;
        ArrayList arrayList = (ArrayList) this.mCallbacks;
        arrayList.add(callback);
        if (arrayList.size() == 1) {
            setListening(true);
        }
        callback.onManagedProfileChanged();
    }

    public final boolean hasActiveProfile() {
        boolean z;
        if (!this.mListening || ((UserTrackerImpl) this.mUserTracker).getUserId() != this.mCurrentUser) {
            reloadManagedProfiles();
        }
        synchronized (this.mProfiles) {
            z = this.mProfiles.size() > 0;
        }
        return z;
    }

    public final boolean isWorkModeEnabled() {
        if (!this.mListening) {
            reloadManagedProfiles();
        }
        synchronized (this.mProfiles) {
            Iterator it = this.mProfiles.iterator();
            while (it.hasNext()) {
                if (((UserInfo) it.next()).isQuietModeEnabled()) {
                    return false;
                }
            }
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0060 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0027 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reloadManagedProfiles() {
        boolean z;
        synchronized (this.mProfiles) {
            boolean z2 = this.mProfiles.size() > 0;
            int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
            this.mProfiles.clear();
            for (UserInfo userInfo : this.mUserManager.getEnabledProfiles(userId)) {
                if (userInfo.isManagedProfile()) {
                    ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).getClass();
                    if (!SemPersonaManager.isSecureFolderId(userInfo.id) && !userInfo.isDualAppProfile() && !userInfo.isUserTypeAppSeparation()) {
                        z = false;
                        if (z) {
                            this.mProfiles.add(userInfo);
                        }
                    }
                    z = true;
                    if (z) {
                    }
                }
            }
            if (this.mProfiles.size() == 0 && z2 && userId == this.mCurrentUser) {
                this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.ManagedProfileControllerImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it = ((ArrayList) ManagedProfileControllerImpl.this.mCallbacks).iterator();
                        while (it.hasNext()) {
                            ((ManagedProfileController.Callback) it.next()).onManagedProfileRemoved();
                        }
                    }
                });
            }
            this.mCurrentUser = userId;
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ArrayList arrayList = (ArrayList) this.mCallbacks;
        if (arrayList.remove((ManagedProfileController.Callback) obj) && arrayList.size() == 0) {
            setListening(false);
        }
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        UserTracker userTracker = this.mUserTracker;
        if (!z) {
            ((UserTrackerImpl) userTracker).removeCallback(this.mUserTrackerCallback);
        } else {
            reloadManagedProfiles();
            ((UserTrackerImpl) userTracker).addCallback(this.mUserTrackerCallback, this.mMainExecutor);
        }
    }

    public final void setWorkModeEnabled(boolean z) {
        synchronized (this.mProfiles) {
            Iterator it = this.mProfiles.iterator();
            while (it.hasNext()) {
                if (!this.mUserManager.requestQuietModeEnabled(!z, UserHandle.of(((UserInfo) it.next()).id))) {
                    ((StatusBarManager) this.mContext.getSystemService("statusbar")).collapsePanels();
                }
            }
        }
    }
}
