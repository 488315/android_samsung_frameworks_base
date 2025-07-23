package com.android.systemui.keyguard.domain.interactor;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.KeyguardServiceShowLockscreenRepository;
import com.android.systemui.keyguard.data.repository.ShowLockscreenCallback;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardServiceShowLockscreenInteractor implements CoreStartable {
    public final CoroutineScope backgroundScope;
    public final KeyguardEnabledInteractor keyguardEnabledInteractor;
    public final KeyguardServiceShowLockscreenRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final SharedFlowImpl showNowEvents = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
    public final UserTracker userTracker;
    public final Lazy wmLockscreenVisibilityInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public KeyguardServiceShowLockscreenInteractor(CoroutineScope coroutineScope, SelectedUserInteractor selectedUserInteractor, KeyguardServiceShowLockscreenRepository keyguardServiceShowLockscreenRepository, UserTracker userTracker, Lazy lazy, KeyguardEnabledInteractor keyguardEnabledInteractor) {
        this.backgroundScope = coroutineScope;
        this.selectedUserInteractor = selectedUserInteractor;
        this.repository = keyguardServiceShowLockscreenRepository;
        this.userTracker = userTracker;
        this.wmLockscreenVisibilityInteractor = lazy;
        this.keyguardEnabledInteractor = keyguardEnabledInteractor;
    }

    public static final void access$notifyShowLockscreenCallbacks(KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor) {
        ArrayList arrayList;
        synchronized (keyguardServiceShowLockscreenInteractor.repository.showLockscreenCallbacks) {
            arrayList = new ArrayList(keyguardServiceShowLockscreenInteractor.repository.showLockscreenCallbacks);
            keyguardServiceShowLockscreenInteractor.repository.showLockscreenCallbacks.clear();
            Unit unit = Unit.INSTANCE;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ShowLockscreenCallback showLockscreenCallback = (ShowLockscreenCallback) obj;
            if (showLockscreenCallback.userId != keyguardServiceShowLockscreenInteractor.selectedUserInteractor.getSelectedUserId()) {
                Log.i("ShowLockscreenInteractor", "Not notifying lockNowCallback due to user mismatch");
                return;
            }
            Log.i("ShowLockscreenInteractor", "Notifying lockNowCallback");
            try {
                showLockscreenCallback.remoteCallback.sendResult((Bundle) null);
            } catch (RemoteException e) {
                Log.e("ShowLockscreenInteractor", "Could not issue LockNowCallback sendResult", e);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.backgroundScope, null, null, new KeyguardServiceShowLockscreenInteractor$start$1(this, null), 3);
    }
}
