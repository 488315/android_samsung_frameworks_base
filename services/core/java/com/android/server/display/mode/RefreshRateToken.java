package com.android.server.display.mode;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.TimeUtils;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public abstract class RefreshRateToken extends IRefreshRateToken.Stub
    implements IBinder.DeathRecipient {
  public RefreshRateTokenInfo mInfo;
  public Consumer mRemoveConsumer;

  public abstract void accept();

  public void init(RefreshRateTokenInfo refreshRateTokenInfo, Consumer consumer) {
    this.mInfo = refreshRateTokenInfo;
    this.mRemoveConsumer = consumer;
    IBinder iBinder = refreshRateTokenInfo.mToken;
    if (iBinder != null) {
      try {
        iBinder.linkToDeath(this, 0);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void release() {
    remove();
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {
    remove();
  }

  public final void remove() {
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

  public String toString() {
    return "{ RefreshRateToken[->"
        + this.mInfo.mTag
        + ", acquire at "
        + TimeUtils.formatUptime(this.mInfo.mAcquireTime)
        + "}";
  }

  public class RefreshRateTokenInfo {
    public long mAcquireTime;
    public int mRefreshRate;
    public String mTag;
    public IBinder mToken;

    public RefreshRateTokenInfo(Builder builder) {
      this.mToken = builder.mToken;
      this.mTag = builder.mTag;
      this.mAcquireTime = builder.mAcquireTime;
      this.mRefreshRate = builder.mRefreshRate;
    }

    public class Builder {
      public long mAcquireTime = SystemClock.uptimeMillis();
      public int mRefreshRate;
      public String mTag;
      public IBinder mToken;

      public RefreshRateTokenInfo build() {
        return new RefreshRateTokenInfo(this);
      }

      public Builder(IBinder iBinder, String str) {
        this.mToken = iBinder;
        this.mTag = str;
      }

      public Builder setRefreshRate(int i) {
        this.mRefreshRate = i;
        return this;
      }
    }
  }
}
