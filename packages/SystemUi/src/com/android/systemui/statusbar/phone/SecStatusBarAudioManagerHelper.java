package com.android.systemui.statusbar.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecStatusBarAudioManagerHelper {
    public static SecStatusBarAudioManagerHelper sInstance;
    public int mCachedRingerMode = -1;
    public final Context mContext;
    public AudioManager mManager;
    public final SecStatusBarAudioManagerHelperBroadcastReceiver mReceiver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SecStatusBarAudioManagerHelperBroadcastReceiver extends BroadcastReceiver {
        public boolean mIsRegistered;

        public /* synthetic */ SecStatusBarAudioManagerHelperBroadcastReceiver(SecStatusBarAudioManagerHelper secStatusBarAudioManagerHelper, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SecStatusBarAudioManagerHelper secStatusBarAudioManagerHelper = SecStatusBarAudioManagerHelper.this;
            secStatusBarAudioManagerHelper.mCachedRingerMode = secStatusBarAudioManagerHelper.getRingerMode(true);
        }

        private SecStatusBarAudioManagerHelperBroadcastReceiver() {
            this.mIsRegistered = false;
        }
    }

    public SecStatusBarAudioManagerHelper(Context context) {
        this.mManager = null;
        SecStatusBarAudioManagerHelperBroadcastReceiver secStatusBarAudioManagerHelperBroadcastReceiver = new SecStatusBarAudioManagerHelperBroadcastReceiver(this, 0);
        this.mReceiver = secStatusBarAudioManagerHelperBroadcastReceiver;
        this.mContext = context;
        this.mManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (secStatusBarAudioManagerHelperBroadcastReceiver.mIsRegistered) {
            return;
        }
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"), SecStatusBarAudioManagerHelper.this.mReceiver);
        secStatusBarAudioManagerHelperBroadcastReceiver.mIsRegistered = true;
    }

    public final AudioManager getManager() {
        if (this.mManager == null) {
            this.mManager = (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        }
        return this.mManager;
    }

    public final int getRingerMode(boolean z) {
        if (this.mContext == null) {
            return 0;
        }
        if ((z || this.mCachedRingerMode == -1) && getManager() != null) {
            this.mCachedRingerMode = getManager().getRingerModeInternal();
        }
        int i = this.mCachedRingerMode;
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public final void setRingerModeInternal(int i) {
        if (this.mContext == null) {
            Log.e("StatusBarAudioManagerHelper", "setRingerModeInternal(" + i + ") mContext==null");
            return;
        }
        if (getManager() != null) {
            getManager().setRingerModeInternal(i);
            return;
        }
        Log.e("StatusBarAudioManagerHelper", "setRingerModeInternal(" + i + ") AudioManager==null");
    }
}
