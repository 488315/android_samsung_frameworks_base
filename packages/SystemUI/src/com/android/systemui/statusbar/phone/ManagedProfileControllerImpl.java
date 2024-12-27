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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class UserTrackerCallback implements UserTracker.Callback {
        public /* synthetic */ UserTrackerCallback(ManagedProfileControllerImpl managedProfileControllerImpl, int i) {
            this();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
            managedProfileControllerImpl.reloadManagedProfiles();
            Iterator it = new ArrayList(managedProfileControllerImpl.mCallbacks).iterator();
            while (it.hasNext()) {
                ((ManagedProfileController.Callback) it.next()).onManagedProfileChanged();
            }
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
            managedProfileControllerImpl.reloadManagedProfiles();
            Iterator it = new ArrayList(managedProfileControllerImpl.mCallbacks).iterator();
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
        ((ArrayList) this.mCallbacks).add(callback);
        if (((ArrayList) this.mCallbacks).size() == 1) {
            setListening$4(true);
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
            try {
                Iterator it = this.mProfiles.iterator();
                while (it.hasNext()) {
                    if (((UserInfo) it.next()).isQuietModeEnabled()) {
                        return false;
                    }
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reloadManagedProfiles() {
        synchronized (this.mProfiles) {
            try {
                boolean z = this.mProfiles.size() > 0;
                int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
                this.mProfiles.clear();
                for (UserInfo userInfo : this.mUserManager.getEnabledProfiles(userId)) {
                    if (userInfo.isManagedProfile()) {
                        ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).getClass();
                        if (!SemPersonaManager.isSecureFolderId(userInfo.id) && !userInfo.isDualAppProfile() && !userInfo.isUserTypeAppSeparation()) {
                            this.mProfiles.add(userInfo);
                        }
                    }
                }
                if (this.mProfiles.size() == 0 && z && userId == this.mCurrentUser) {
                    this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.ManagedProfileControllerImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
                            managedProfileControllerImpl.getClass();
                            Iterator it = new ArrayList(managedProfileControllerImpl.mCallbacks).iterator();
                            while (it.hasNext()) {
                                ((ManagedProfileController.Callback) it.next()).onManagedProfileRemoved();
                            }
                        }
                    });
                }
                this.mCurrentUser = userId;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        if (((ArrayList) this.mCallbacks).remove((ManagedProfileController.Callback) obj) && ((ArrayList) this.mCallbacks).size() == 0) {
            setListening$4(false);
        }
    }

    public final void setListening$4(boolean z) {
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
            try {
                Iterator it = this.mProfiles.iterator();
                while (it.hasNext()) {
                    if (!this.mUserManager.requestQuietModeEnabled(!z, UserHandle.of(((UserInfo) it.next()).id))) {
                        ((StatusBarManager) this.mContext.getSystemService("statusbar")).collapsePanels();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
