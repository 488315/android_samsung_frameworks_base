package com.android.server.display.mode;

import android.os.IBinder;
import android.util.TimeUtils;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class RefreshRateToken extends IRefreshRateToken.Stub implements IBinder.DeathRecipient {
    public RefreshRateTokenInfo mInfo;
    public Consumer mRemoveConsumer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
        return "{ RefreshRateToken[->" + this.mInfo.mTag + ", acquire at " + TimeUtils.formatUptime(this.mInfo.mAcquireTime) + "}";
    }
}
