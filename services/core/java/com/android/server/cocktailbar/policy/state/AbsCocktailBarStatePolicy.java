package com.android.server.cocktailbar.policy.state;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.SparseArray;
import com.samsung.android.cocktailbar.CocktailBarStateInfo;
import com.samsung.android.util.SemLog;

/* loaded from: classes.dex */
public abstract class AbsCocktailBarStatePolicy implements CocktailBarStatePolicy {
  public static final String TAG = "AbsCocktailBarStatePolicy";
  public Handler mCocktailBarStateHandler;
  public HandlerThread mCocktailBarStateThread;
  public CocktailBarStatePolicy.OnCocktailBarStateListener mListener;
  public final CocktailBarStateInfo mStateInfo = new CocktailBarStateInfo(2);
  public int mWindowType = 1;
  public final SparseArray mLockMap = new SparseArray();
  public final Object mLock = new Object();

  public abstract void handleNotifyCurrentStateToBinder(IBinder iBinder);

  public abstract void handleUpdateActivate(boolean z);

  public abstract void handleUpdateCocktailBarWindowType(int i, String str);

  public abstract void handleUpdatePosition(int i);

  public abstract void handleUpdateVisibility(int i);

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public void initialize() {}

  public AbsCocktailBarStatePolicy(
      Context context,
      CocktailBarStatePolicy.OnCocktailBarStateListener onCocktailBarStateListener) {
    this.mListener = onCocktailBarStateListener;
  }

  public final void createHandlerThreadLocked() {
    if (this.mCocktailBarStateThread == null) {
      HandlerThread handlerThread = new HandlerThread("CocktailBarVisibility");
      this.mCocktailBarStateThread = handlerThread;
      handlerThread.start();
      synchronized (this.mLock) {
        CocktailBarStateHandler cocktailBarStateHandler =
            new CocktailBarStateHandler(this.mCocktailBarStateThread.getLooper());
        this.mCocktailBarStateHandler = cocktailBarStateHandler;
        cocktailBarStateHandler.post(
            new Runnable() { // from class:
                             // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy.1
              @Override // java.lang.Runnable
              public void run() {
                Process.setThreadPriority(-4);
                Process.setCanSelfBackground(false);
              }
            });
      }
    }
  }

  public final void quitHandlerThread() {
    synchronized (this.mLock) {
      HandlerThread handlerThread = this.mCocktailBarStateThread;
      if (handlerThread != null) {
        handlerThread.quitSafely();
        this.mCocktailBarStateThread = null;
        this.mCocktailBarStateHandler = null;
      }
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public void updateVisibility(int i) {
    synchronized (this.mLock) {
      Message obtain = Message.obtain();
      obtain.what = 1;
      obtain.arg1 = i;
      enqueueMessageLocked(obtain, 0L, true);
    }
  }

  public final void enqueueMessageLocked(Message message, long j, boolean z) {
    createHandlerThreadLocked();
    if (z) {
      removeQueuedMessageLocked(message.what);
    }
    Handler handler = this.mCocktailBarStateHandler;
    if (handler != null) {
      handler.sendMessageDelayed(message, j);
      updateThreadExpireTimeLocked(j);
    }
  }

  public final void updateThreadExpireTimeLocked(long j) {
    this.mCocktailBarStateHandler.removeMessages(101);
    this.mCocktailBarStateHandler.sendEmptyMessageDelayed(101, 5000L);
  }

  public final void removeQueuedMessageLocked(int i) {
    Handler handler = this.mCocktailBarStateHandler;
    if (handler != null) {
      handler.removeMessages(i);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public void updatePosition(int i) {
    synchronized (this.mLock) {
      Message obtain = Message.obtain();
      obtain.what = 4;
      obtain.arg1 = i;
      enqueueMessageLocked(obtain, 0L, true);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public void notifyStateToBinder(IBinder iBinder) {
    synchronized (this.mLock) {
      Message obtain = Message.obtain();
      obtain.what = 51;
      obtain.obj = iBinder;
      enqueueMessageLocked(obtain, 0L, false);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public int getWindowType() {
    return this.mWindowType;
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public CocktailBarStateInfo getCocktailBarStateInfo() {
    return this.mStateInfo;
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public void updateActivate(boolean z) {
    synchronized (this.mLock) {
      Message obtain = Message.obtain();
      obtain.what = 49;
      obtain.arg1 = z ? 1 : 0;
      enqueueMessageLocked(obtain, 0L, true);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public void updateCocktailBarWindowType(int i, String str) {
    synchronized (this.mLock) {
      Message obtain = Message.obtain();
      obtain.what = 6;
      obtain.arg1 = i;
      obtain.obj = str;
      enqueueMessageLocked(obtain, 0L, true);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public String dump() {
    return ((((("[LockState : ") + this.mLockMap.toString()) + " : " + this.mStateInfo.lockState)
                + " Visibility : "
                + this.mStateInfo.visibility)
            + " CocktailBarWindowType : "
            + this.mStateInfo.windowType)
        + " WindowType : "
        + this.mWindowType;
  }

  public final class CocktailBarStateHandler extends Handler {
    public CocktailBarStateHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      SemLog.i(AbsCocktailBarStatePolicy.TAG, "handleMessage: entry what = " + message.what);
      int i = message.what;
      if (i == 1) {
        AbsCocktailBarStatePolicy.this.handleUpdateVisibility(message.arg1);
        return;
      }
      if (i == 4) {
        AbsCocktailBarStatePolicy.this.handleUpdatePosition(message.arg1);
        AbsCocktailBarStatePolicy.this.mStateInfo.position = message.arg1;
        return;
      }
      if (i == 6) {
        AbsCocktailBarStatePolicy absCocktailBarStatePolicy = AbsCocktailBarStatePolicy.this;
        int i2 = message.arg1;
        Object obj = message.obj;
        absCocktailBarStatePolicy.handleUpdateCocktailBarWindowType(
            i2, obj != null ? (String) obj : null);
        AbsCocktailBarStatePolicy.this.mStateInfo.windowType = message.arg1;
        return;
      }
      if (i == 49) {
        boolean z = message.arg1 == 1;
        AbsCocktailBarStatePolicy.this.handleUpdateActivate(z);
        AbsCocktailBarStatePolicy.this.mStateInfo.activate = z;
      } else if (i == 51) {
        AbsCocktailBarStatePolicy.this.handleNotifyCurrentStateToBinder((IBinder) message.obj);
      } else {
        if (i != 101) {
          return;
        }
        AbsCocktailBarStatePolicy.this.quitHandlerThread();
      }
    }
  }
}
