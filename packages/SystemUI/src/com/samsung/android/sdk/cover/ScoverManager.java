package com.samsung.android.sdk.cover;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.edgelighting.device.EdgeLightingCoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.cover.ICoverStateListenerCallback;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.sdk.SsdkUnsupportedException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public class ScoverManager {
    public static boolean sIsClearCoverSystemFeatureEnabled = false;
    public static boolean sIsClearSideViewCoverSystemFeatureEnabled = false;
    public static boolean sIsFilpCoverSystemFeatureEnabled = false;
    public static boolean sIsLEDBackCoverSystemFeatureEnabled = false;
    public static boolean sIsMiniSviewWalletCoverSysltemFeatureEnabled = false;
    public static boolean sIsNeonCoverSystemFeatureEnabled = false;
    public static boolean sIsNfcLedCoverSystemFeatureEnabled = false;
    public static boolean sIsSViewCoverSystemFeatureEnabled = false;
    public static boolean sIsSystemFeatureQueried = false;
    public static int sServiceVersion = 16777216;
    public final Context mContext;
    public final CopyOnWriteArrayList mCoverStateListenerDelegates;
    public ICoverManager mService;

    public ScoverManager(Context context) {
        int iIntValue;
        new CopyOnWriteArrayList();
        this.mCoverStateListenerDelegates = new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new Binder();
        this.mContext = context;
        if (sIsSystemFeatureQueried) {
            return;
        }
        sIsFilpCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.flip");
        sIsSViewCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.sview");
        sIsNfcLedCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.nfcledcover");
        sIsClearCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.clearcover");
        sIsNeonCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.neoncover");
        sIsClearSideViewCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.clearsideviewcover");
        sIsLEDBackCoverSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.ledbackcover");
        sIsMiniSviewWalletCoverSysltemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.cover.minisviewwalletcover");
        sIsSystemFeatureQueried = true;
        if (isSupportCover()) {
            try {
                Class[] clsArr = new Class[0];
                iIntValue = ((Integer) ICoverManager.class.getMethod("getVersion", null).invoke(getService(), null)).intValue();
            } catch (Exception e) {
                Log.w("ScoverManager", "getVersion failed : " + e);
            }
        } else {
            iIntValue = 16777216;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(iIntValue, "serviceVersion : ", "ScoverManager");
        sServiceVersion = iIntValue;
    }

    public static boolean isSupportCover() {
        return sIsFilpCoverSystemFeatureEnabled || sIsSViewCoverSystemFeatureEnabled || sIsClearCoverSystemFeatureEnabled || sIsNeonCoverSystemFeatureEnabled || sIsClearSideViewCoverSystemFeatureEnabled || sIsNfcLedCoverSystemFeatureEnabled || sIsLEDBackCoverSystemFeatureEnabled || sIsMiniSviewWalletCoverSysltemFeatureEnabled;
    }

    public static boolean isSupportableVersion(int i) {
        int i2 = (i >> 16) & 255;
        int i3 = sServiceVersion;
        return ((i3 >> 24) & 255) >= 1 && ((i3 >> 16) & 255) >= i2 && (i3 & CustomDeviceManager.QUICK_PANEL_ALL) >= 0;
    }

    public final ScoverState getCoverState() {
        if (!isSupportCover()) {
            Log.w("ScoverManager", "getCoverState : This device is not supported cover");
            return null;
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                CoverState coverState = service.getCoverState();
                if (coverState == null) {
                    Log.e("ScoverManager", "getCoverState : coverState is null");
                    return null;
                }
                if (coverState.type != 255 || coverState.switchState) {
                    return isSupportableVersion(R.animator.fade_in) ? new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel, coverState.attached, coverState.model, coverState.fakeCover, coverState.fotaMode) : isSupportableVersion(R.array.emailAddressTypes) ? new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel, coverState.attached, coverState.model, coverState.fakeCover) : isSupportableVersion(R.id.background) ? new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel, coverState.attached, coverState.model) : isSupportableVersion(R.attr.theme) ? new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel, coverState.attached) : new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel);
                }
                Log.e("ScoverManager", "getCoverState : type of cover is nfc smart cover and cover is closed");
                return null;
            }
        } catch (RemoteException e) {
            Log.e("ScoverManager", "RemoteException in getCoverState: ", e);
        }
        return null;
    }

    public final synchronized ICoverManager getService() {
        try {
            if (this.mService == null) {
                ICoverManager iCoverManagerAsInterface = ICoverManager.Stub.asInterface(ServiceManager.getService("cover"));
                this.mService = iCoverManagerAsInterface;
                if (iCoverManagerAsInterface == null) {
                    Slog.w("ScoverManager", "warning: no COVER_MANAGER_SERVICE");
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mService;
    }

    public final void registerListener(EdgeLightingCoverManager.AnonymousClass1 anonymousClass1) throws SsdkUnsupportedException {
        boolean z;
        ICoverStateListenerCallback.Stub coverStateListenerDelegate;
        Log.d("ScoverManager", "registerListener");
        if (!isSupportCover()) {
            Log.w("ScoverManager", "registerListener : This device is not supported cover");
            return;
        }
        ScoverState coverState = getCoverState();
        if (coverState != null && coverState.type == 255) {
            Log.w("ScoverManager", "registerListener : If cover is smart cover, it does not need to register listener of intenal App");
            return;
        }
        if (!isSupportableVersion(R.attr.theme)) {
            throw new SsdkUnsupportedException("This device is not supported this function. Device is must higher then v1.1.0", 2);
        }
        Iterator it = this.mCoverStateListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                coverStateListenerDelegate = null;
                break;
            } else {
                CoverStateListenerDelegate coverStateListenerDelegate2 = (CoverStateListenerDelegate) it.next();
                if (coverStateListenerDelegate2.mListener.equals(anonymousClass1)) {
                    z = true;
                    coverStateListenerDelegate = coverStateListenerDelegate2;
                    break;
                }
            }
        }
        if (coverStateListenerDelegate == null) {
            coverStateListenerDelegate = new CoverStateListenerDelegate(anonymousClass1, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                service.registerListenerCallback(coverStateListenerDelegate, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()), 2);
                if (z) {
                    return;
                }
                this.mCoverStateListenerDelegates.add(coverStateListenerDelegate);
            }
        } catch (RemoteException e) {
            Log.e("ScoverManager", "RemoteException in registerListener: ", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.os.IBinder, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.samsung.android.sdk.cover.CoverStateListenerDelegate] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.util.concurrent.CopyOnWriteArrayList] */
    public final void unregisterListener(EdgeLightingCoverManager.AnonymousClass1 anonymousClass1) throws SsdkUnsupportedException {
        ?? r2;
        Log.d("ScoverManager", "unregisterListener");
        if (!isSupportCover()) {
            Log.w("ScoverManager", "unregisterListener : This device is not supported cover");
            return;
        }
        ScoverState coverState = getCoverState();
        if (coverState != null && coverState.type == 255) {
            Log.w("ScoverManager", "unregisterListener : If cover is smart cover, it does not need to unregister listener of intenal App");
            return;
        }
        if (!isSupportableVersion(R.attr.theme)) {
            throw new SsdkUnsupportedException("This device is not supported this function. Device is must higher then v1.1.0", 2);
        }
        if (anonymousClass1 == null) {
            Log.w("ScoverManager", "unregisterListener : listener is null");
            return;
        }
        Iterator it = this.mCoverStateListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                r2 = 0;
                break;
            } else {
                r2 = (CoverStateListenerDelegate) it.next();
                if (r2.mListener.equals(anonymousClass1)) {
                    break;
                }
            }
        }
        if (r2 == 0) {
            return;
        }
        try {
            ICoverManager service = getService();
            if (service == null || !service.unregisterCallback((IBinder) r2)) {
                return;
            }
            this.mCoverStateListenerDelegates.remove(r2);
        } catch (RemoteException e) {
            Log.e("ScoverManager", "RemoteException in unregisterListener: ", e);
        }
    }

    public class CoverStateListener {
        public void onCoverAttachStateChanged(boolean z) {
        }

        public void onCoverSwitchStateChanged(boolean z) {
        }
    }
}
