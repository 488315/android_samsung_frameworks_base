package com.samsung.android.server.corestate;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class CoreStateObserverController {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public static final String TAG = "CoreStateObserverController";
    public final Callback mCallback;
    public final Context mContext;
    public final CoreStatePreferenceObserver mPreferenceObserver;
    public final CoreStateSettingObserver mSettingObserver;
    public final CoreStateSystemFeatureObserver mSystemFeatureObserver;
    public final CoreStateVolatileObserver mVolatileObserver;
    public final SparseArray mStateForUser = new SparseArray();
    public final ArraySet mStartedUserIds = new ArraySet();

    public interface Callback {
        void onCoreStateChanged(Bundle bundle, int i, boolean z, Runnable runnable);
    }

    public CoreStateObserverController(Context context, Callback callback, Handler handler) {
        this.mContext = context;
        this.mCallback = callback;
        this.mSettingObserver = new CoreStateSettingObserver(context, handler, this);
        this.mSystemFeatureObserver = new CoreStateSystemFeatureObserver(context);
        this.mVolatileObserver = new CoreStateVolatileObserver(handler, this);
        this.mPreferenceObserver = new CoreStatePreferenceObserver(context);
        if (DEBUG) {
            Slog.d(TAG, "CoreStateController()");
        }
    }

    public void init() {
        this.mStartedUserIds.add(0);
        this.mSystemFeatureObserver.init();
        this.mSettingObserver.beginObserveCoreStateSettings();
        sendCoreState(true, 0);
    }

    public final void sendCoreState(boolean z, int i) {
        sendCoreState(z, i, null);
    }

    public final void sendCoreState(boolean z, int i, Runnable runnable) {
        if (DEBUG) {
            Slog.d(TAG, "sendCoreState(u" + i + ")");
        }
        int populateCoreState = populateCoreState(i);
        if (z) {
            this.mCallback.onCoreStateChanged(copyCoreStateLocked(i), i, false, runnable);
            return;
        }
        if ((populateCoreState & 1) == 0) {
            if ((populateCoreState & 2) != 0) {
                this.mCallback.onCoreStateChanged(copyCoreStateLocked(i), i, true, runnable);
            }
        } else {
            Iterator it = this.mStartedUserIds.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (intValue != i) {
                    populateCoreState(intValue);
                }
                this.mCallback.onCoreStateChanged(copyCoreStateLocked(intValue), intValue, true, runnable);
            }
        }
    }

    public final int populateCoreState(int i) {
        if (DEBUG) {
            Slog.d(TAG, "populateCoreState(u" + i + ")");
        }
        Bundle bundle = (Bundle) this.mStateForUser.get(i);
        if (bundle == null) {
            bundle = new Bundle();
            this.mStateForUser.put(i, bundle);
            CoreStateSystemFeatureObserver coreStateSystemFeatureObserver = this.mSystemFeatureObserver;
            if (coreStateSystemFeatureObserver != null) {
                coreStateSystemFeatureObserver.populateState(bundle, i);
            }
        }
        CoreStateSettingObserver coreStateSettingObserver = this.mSettingObserver;
        int populateState = coreStateSettingObserver != null ? 0 | coreStateSettingObserver.populateState(bundle, i) : 0;
        CoreStateVolatileObserver coreStateVolatileObserver = this.mVolatileObserver;
        if (coreStateVolatileObserver != null) {
            populateState |= coreStateVolatileObserver.populateState(bundle, i);
        }
        CoreStatePreferenceObserver coreStatePreferenceObserver = this.mPreferenceObserver;
        return coreStatePreferenceObserver != null ? populateState | coreStatePreferenceObserver.populateState(bundle, i) : populateState;
    }

    public void startUserLocked(int i, boolean z, boolean z2) {
        CoreStateSettingObserver coreStateSettingObserver;
        Slog.d(TAG, "startUserLocked: " + this.mStartedUserIds + ", u" + i);
        this.mStartedUserIds.add(Integer.valueOf(i));
        if (z2 && (coreStateSettingObserver = this.mSettingObserver) != null) {
            coreStateSettingObserver.beginObserveCoreStateSettingsForSingleUser(i);
        }
        if (z2 || z) {
            sendCoreState(true, i);
        }
    }

    public void stopUserLocked(int i, boolean z) {
        CoreStateVolatileObserver coreStateVolatileObserver;
        Slog.d(TAG, "stopUserLocked: " + this.mStartedUserIds + ", u" + i);
        this.mStartedUserIds.remove(Integer.valueOf(i));
        CoreStateSettingObserver coreStateSettingObserver = this.mSettingObserver;
        if (coreStateSettingObserver != null) {
            coreStateSettingObserver.endObserveCoreStateSettingsForSingleUser(this.mStartedUserIds, i);
        }
        this.mStateForUser.remove(i);
        if (!z || (coreStateVolatileObserver = this.mVolatileObserver) == null) {
            return;
        }
        coreStateVolatileObserver.removeStatesForUser(i);
    }

    public Bundle copyCoreStateLocked(int i) {
        Bundle bundle = (Bundle) this.mStateForUser.get(i);
        return bundle == null ? new Bundle() : (Bundle) bundle.clone();
    }

    public void onCoreStateChanged(int i) {
        onCoreStateChanged(i, null);
    }

    public void onCoreStateChanged(int i, Runnable runnable) {
        sendCoreState(false, i, runnable);
    }

    public void setVolatileState(String str, Object obj, int i, boolean z, boolean z2, Runnable runnable) {
        CoreStateVolatileObserver coreStateVolatileObserver = this.mVolatileObserver;
        if (coreStateVolatileObserver != null) {
            coreStateVolatileObserver.setState(str, obj, i, z, z2, runnable);
        }
    }
}
