package com.android.systemui.statusbar.phone;

import android.app.StatusBarManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.keyguard.KeyguardUpdateMonitor;
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
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ManagedProfileControllerImpl implements ManagedProfileController {
    public final Context mContext;
    public int mCurrentUser;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public final Executor mMainExecutor;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final List mCallbacks = new ArrayList();
    public final UserTrackerCallback mUserTrackerCallback = new UserTrackerCallback(this, 0);
    public final LinkedList mProfiles = new LinkedList();

    final class UserTrackerCallback implements UserTracker.Callback {
        public /* synthetic */ UserTrackerCallback(ManagedProfileControllerImpl managedProfileControllerImpl, int i) {
            this();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
            managedProfileControllerImpl.reloadManagedProfiles();
            managedProfileControllerImpl.notifyCallbacks$1(new ManagedProfileControllerImpl$$ExternalSyntheticLambda1(1));
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ManagedProfileControllerImpl managedProfileControllerImpl = ManagedProfileControllerImpl.this;
            managedProfileControllerImpl.reloadManagedProfiles();
            managedProfileControllerImpl.notifyCallbacks$1(new ManagedProfileControllerImpl$$ExternalSyntheticLambda1(1));
        }

        private UserTrackerCallback() {
        }
    }

    public ManagedProfileControllerImpl(Context context, Executor executor, UserTracker userTracker, UserManager userManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ManagedProfileController.Callback callback = (ManagedProfileController.Callback) obj;
        synchronized (this.mCallbacks) {
            try {
                ((ArrayList) this.mCallbacks).add(callback);
                if (((ArrayList) this.mCallbacks).size() == 1) {
                    setListening$4(true);
                }
                callback.onManagedProfileChanged();
            } catch (Throwable th) {
                throw th;
            }
        }
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

    public final void notifyCallbacks$1(Consumer consumer) {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = new ArrayList(this.mCallbacks);
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            consumer.accept((ManagedProfileController.Callback) obj);
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
                            ManagedProfileControllerImpl managedProfileControllerImpl = this.f$0;
                            managedProfileControllerImpl.getClass();
                            managedProfileControllerImpl.notifyCallbacks$1(new ManagedProfileControllerImpl$$ExternalSyntheticLambda1(0));
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
        ManagedProfileController.Callback callback = (ManagedProfileController.Callback) obj;
        synchronized (this.mCallbacks) {
            try {
                if (((ArrayList) this.mCallbacks).remove(callback) && ((ArrayList) this.mCallbacks).size() == 0) {
                    setListening$4(false);
                }
            } catch (Throwable th) {
                throw th;
            }
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
                        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
                        if (keyguardUpdateMonitor.mIsDreaming) {
                            try {
                                keyguardUpdateMonitor.mDreamManager.awaken();
                            } catch (RemoteException e) {
                                keyguardUpdateMonitor.mLogger.logException("Unable to awaken from dream", e);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
