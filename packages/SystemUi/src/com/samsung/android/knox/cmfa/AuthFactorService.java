package com.samsung.android.knox.cmfa;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.cmfa.IAuthFactorService;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AuthFactorService extends Service {
    public static final String DETECT_DEATH_BINDER = "detectDeathBinder";
    public static final String TAG = "AuthFactorService";
    public final IAuthFactorService.Stub mBinder = new IAuthFactorService.Stub() { // from class: com.samsung.android.knox.cmfa.AuthFactorService.1
        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public final long getTrustScore() {
            return AuthFactorService.this.mScore;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public final AuthFactorType getType() {
            return AuthFactorType.TRUSTED_SERVICE;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public final int init(final IResultListener iResultListener) {
            new Handler(AuthFactorService.this.getMainLooper()).post(new Runnable() { // from class: com.samsung.android.knox.cmfa.AuthFactorService.1.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        AuthFactorService.this.onFactorInit();
                        iResultListener.onSuccess();
                    } catch (Exception e) {
                        Log.e(AuthFactorService.TAG, e.toString());
                    }
                }
            });
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public final boolean isStarted() {
            return AuthFactorService.this.mStarted;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public final int start(final Map map, IAuthFactorListener iAuthFactorListener) {
            Log.d(AuthFactorService.TAG, NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            AuthFactorService authFactorService = AuthFactorService.this;
            authFactorService.mListener = iAuthFactorListener;
            authFactorService.mScore = 0L;
            authFactorService.mStarted = true;
            new Handler(AuthFactorService.this.getMainLooper()).post(new Runnable() { // from class: com.samsung.android.knox.cmfa.AuthFactorService.1.2
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        AuthFactorService.this.onFactorStart(map);
                    } catch (Exception e) {
                        Log.e(AuthFactorService.TAG, e.toString());
                    }
                }
            });
            return 0;
        }

        @Override // com.samsung.android.knox.cmfa.IAuthFactorService
        public final int stop() {
            Log.d(AuthFactorService.TAG, "stop");
            AuthFactorService authFactorService = AuthFactorService.this;
            authFactorService.mListener = null;
            authFactorService.mScore = 0L;
            authFactorService.mStarted = false;
            new Handler(AuthFactorService.this.getMainLooper()).post(new Runnable() { // from class: com.samsung.android.knox.cmfa.AuthFactorService.1.3
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        AuthFactorService.this.onFactorStop();
                    } catch (Exception e) {
                        Log.e(AuthFactorService.TAG, e.toString());
                    }
                }
            });
            return 0;
        }
    };
    public BinderDeathReceiver mBinderDeathReceiver;
    public IAuthFactorListener mListener;
    public long mScore;
    public boolean mStarted;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BinderDeathReceiver implements IBinder.DeathRecipient {
        public IBinder mReceiver;

        public /* synthetic */ BinderDeathReceiver(AuthFactorService authFactorService, int i) {
            this();
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.e(AuthFactorService.TAG, "Binder Death Detected!");
            IBinder iBinder = this.mReceiver;
            if (iBinder != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }

        public final void setReceiver(IBinder iBinder) {
            this.mReceiver = iBinder;
        }

        private BinderDeathReceiver() {
        }

        public final void handleBinderDeath() {
        }
    }

    public final long getTrustScore() {
        return this.mScore;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        IBinder binder;
        Bundle extras = intent.getExtras();
        if (extras != null && (binder = extras.getBinder("detectDeathBinder")) != null) {
            try {
                BinderDeathReceiver binderDeathReceiver = this.mBinderDeathReceiver;
                binderDeathReceiver.mReceiver = binder;
                binder.linkToDeath(binderDeathReceiver, 0);
            } catch (RemoteException e) {
                Log.e(TAG, e.toString());
            }
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.mBinderDeathReceiver = new BinderDeathReceiver(this, 0);
        this.mListener = null;
        this.mScore = 0L;
        this.mStarted = false;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
    }

    public abstract void onFactorInit();

    public abstract void onFactorStart(Map map);

    public abstract void onFactorStop();

    public final boolean setTrustScore(long j) {
        try {
            if (!this.mStarted) {
                return false;
            }
            this.mScore = j;
            if (j > 100) {
                this.mScore = 100L;
            } else if (j < 0) {
                this.mScore = 0L;
            }
            IAuthFactorListener iAuthFactorListener = this.mListener;
            if (iAuthFactorListener == null) {
                return true;
            }
            iAuthFactorListener.onStateUpdate();
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return true;
        }
    }
}
