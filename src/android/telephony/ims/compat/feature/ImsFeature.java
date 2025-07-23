package android.telephony.ims.compat.feature;

import android.content.Context;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.internal.IImsFeatureStatusCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public abstract class ImsFeature {
    public static final int EMERGENCY_MMTEL = 0;
    public static final int INVALID = -1;
    private static final String LOG_TAG = "ImsFeature";
    public static final int MAX = 3;
    public static final int MMTEL = 1;
    public static final int RCS = 2;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_NOT_AVAILABLE = 0;
    public static final int STATE_READY = 2;
    protected Context mContext;
    private final Set<IImsFeatureStatusCallback> mStatusCallbacks = Collections.newSetFromMap(new WeakHashMap());
    private int mState = 0;
    private int mSlotId = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsState {
    }

    public abstract IInterface getBinder();

    public abstract void onFeatureReady();

    public abstract void onFeatureRemoved();

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setSlotId(int i) {
        this.mSlotId = i;
    }

    public int getFeatureState() {
        return this.mState;
    }

    protected final void setFeatureState(int i) {
        if (this.mState != i) {
            this.mState = i;
            notifyFeatureState(i);
        }
    }

    public void addImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        if (iImsFeatureStatusCallback == null) {
            return;
        }
        try {
            iImsFeatureStatusCallback.notifyImsFeatureStatus(this.mState);
            synchronized (this.mStatusCallbacks) {
                this.mStatusCallbacks.add(iImsFeatureStatusCallback);
            }
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Couldn't notify feature state: " + e.getMessage());
        }
    }

    public void removeImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        if (iImsFeatureStatusCallback == null) {
            return;
        }
        synchronized (this.mStatusCallbacks) {
            this.mStatusCallbacks.remove(iImsFeatureStatusCallback);
        }
    }

    private void notifyFeatureState(int i) {
        synchronized (this.mStatusCallbacks) {
            Iterator<IImsFeatureStatusCallback> it = this.mStatusCallbacks.iterator();
            while (it.hasNext()) {
                IImsFeatureStatusCallback next = it.next();
                try {
                    Log.i(LOG_TAG, "notifying ImsFeatureState=" + i);
                    next.notifyImsFeatureStatus(i);
                } catch (RemoteException e) {
                    it.remove();
                    Log.w(LOG_TAG, "Couldn't notify feature state: " + e.getMessage());
                }
            }
        }
    }
}
