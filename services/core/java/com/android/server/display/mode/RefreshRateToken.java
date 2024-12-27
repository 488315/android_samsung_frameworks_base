package com.android.server.display.mode;

import android.os.IBinder;
import android.util.TimeUtils;

import com.samsung.android.hardware.display.IRefreshRateToken;

import java.util.function.Consumer;

public abstract class RefreshRateToken extends IRefreshRateToken.Stub
        implements IBinder.DeathRecipient {
    public RefreshRateTokenInfo mInfo;
    public Consumer mRemoveConsumer;

    public final class RefreshRateTokenInfo {
        public long mAcquireTime;
        public int mRefreshRate;
        public String mTag;
        public IBinder mToken;
    }

    public abstract void accept();

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        IBinder iBinder;
        Consumer consumer = this.mRemoveConsumer;
        if (consumer != null) {
            consumer.accept(this);
        }
        RefreshRateTokenInfo refreshRateTokenInfo = this.mInfo;
        if (refreshRateTokenInfo == null || (iBinder = refreshRateTokenInfo.mToken) == null) {
            return;
        }
        iBinder.unlinkToDeath(this, 0);
    }

    public final void release() {
        IBinder iBinder;
        Consumer consumer = this.mRemoveConsumer;
        if (consumer != null) {
            consumer.accept(this);
        }
        RefreshRateTokenInfo refreshRateTokenInfo = this.mInfo;
        if (refreshRateTokenInfo == null || (iBinder = refreshRateTokenInfo.mToken) == null) {
            return;
        }
        iBinder.unlinkToDeath(this, 0);
    }

    public final String toString() {
        return "{ RefreshRateToken[->"
                + this.mInfo.mTag
                + ", acquire at "
                + TimeUtils.formatUptime(this.mInfo.mAcquireTime)
                + "}";
    }
}
