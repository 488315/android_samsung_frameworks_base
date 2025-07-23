package com.samsung.android.knox.zt.config;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class TrustFactorService extends Service {
    public static final String DETECT_DEATH_BINDER = "detectDeathBinder";
    public static final String TAG = "TrustFactorService";
    public final ITrustFactorService mBinder = new ITrustFactorService() { // from class: com.samsung.android.knox.zt.config.TrustFactorService.1
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public long getTrustScore() {
            return TrustFactorService.this.mScore;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public TrustFactorType getType() {
            return TrustFactorType.TRUSTED_SERVICE;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public int init(final IResultListener iResultListener) {
            new Handler(TrustFactorService.this.getMainLooper()).post(new Runnable() { // from class: com.samsung.android.knox.zt.config.TrustFactorService.1.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        TrustFactorService.this.onFactorInit();
                        iResultListener.onSuccess();
                    } catch (Exception e) {
                        Log.e(TrustFactorService.TAG, e.toString());
                    }
                }
            });
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public boolean isStarted() {
            return TrustFactorService.this.mStarted;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public int start(final Map map, ITrustFactorListener iTrustFactorListener) {
            Log.d(TrustFactorService.TAG, NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            TrustFactorService trustFactorService = TrustFactorService.this;
            trustFactorService.mListener = iTrustFactorListener;
            trustFactorService.mScore = 0L;
            trustFactorService.mStarted = true;
            new Handler(TrustFactorService.this.getMainLooper()).post(new Runnable() { // from class: com.samsung.android.knox.zt.config.TrustFactorService.1.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        TrustFactorService.this.onFactorStart(map);
                    } catch (Exception e) {
                        Log.e(TrustFactorService.TAG, e.toString());
                    }
                }
            });
            return 0;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorService
        public int stop() {
            Log.d(TrustFactorService.TAG, "stop");
            TrustFactorService trustFactorService = TrustFactorService.this;
            trustFactorService.mListener = null;
            trustFactorService.mScore = 0L;
            trustFactorService.mStarted = false;
            new Handler(TrustFactorService.this.getMainLooper()).post(new Runnable() { // from class: com.samsung.android.knox.zt.config.TrustFactorService.1.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        TrustFactorService.this.onFactorStop();
                    } catch (Exception e) {
                        Log.e(TrustFactorService.TAG, e.toString());
                    }
                }
            });
            return 0;
        }
    };
    public BinderDeathReceiver mBinderDeathReceiver;
    public ITrustFactorListener mListener;
    public long mScore;
    public boolean mStarted;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class BinderDeathReceiver implements IBinder.DeathRecipient {
        public IBinder mReceiver;

        public /* synthetic */ BinderDeathReceiver(TrustFactorService trustFactorService, int i) {
            this();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e(TrustFactorService.TAG, "Binder Death Detected!");
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
    public IBinder onBind(Intent intent) {
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
        return (IBinder) this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mBinderDeathReceiver = new BinderDeathReceiver(this, 0);
        this.mListener = null;
        this.mScore = 0L;
        this.mStarted = false;
    }

    @Override // android.app.Service
    public void onDestroy() {
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
            ITrustFactorListener iTrustFactorListener = this.mListener;
            if (iTrustFactorListener == null) {
                return true;
            }
            iTrustFactorListener.onStateUpdate();
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return true;
        }
    }
}
