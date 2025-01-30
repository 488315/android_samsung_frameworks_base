package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes3.dex */
public final class DreamyNfcLedCoverController extends BaseNfcLedCoverController {
  public static final String TAG = "DreamyNfcLedCoverController";
  public ArrayList mCoverAuthCallbacks;
  public ArrayDeque mEnquedFactoryCommands;
  public Queue mFotaInProgressCallbacks;
  public final NfcLedCoverControllerHandler mHandler;
  public ArrayList mListeners;
  public PowerManager.WakeLock mSendLedDataWakeLock;
  public PowerManager.WakeLock mTouchResponseWakeLock;

  public final void handleRequestCoverAuthentication(long j) {}

  public DreamyNfcLedCoverController(Looper looper, Context context) {
    super(looper, context);
    this.mListeners = new ArrayList();
    this.mCoverAuthCallbacks = new ArrayList();
    this.mEnquedFactoryCommands = new ArrayDeque();
    this.mFotaInProgressCallbacks = new LinkedList();
    this.mHandler = new NfcLedCoverControllerHandler(looper);
    PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "send leddata");
    this.mSendLedDataWakeLock = newWakeLock;
    newWakeLock.setReferenceCounted(false);
    PowerManager.WakeLock newWakeLock2 =
        this.mPowerManager.newWakeLock(1, "touchResponse ledcover");
    this.mTouchResponseWakeLock = newWakeLock2;
    newWakeLock2.setReferenceCounted(false);
  }

  public final class NfcLedCoverControllerHandler extends Handler {
    public NfcLedCoverControllerHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 0:
          DreamyNfcLedCoverController.this.handleSendDataToNfcLedCover(
              message.arg1, (byte[]) message.obj);
          break;
        case 3:
          DreamyNfcLedCoverController.this.handleEventResponse(message.arg1, message.arg2);
          break;
        case 4:
          DreamyNfcLedCoverController.this.handleSendPowerKeyToCover();
          break;
        case 5:
          if (!(message.arg1 == 1)) {
            DreamyNfcLedCoverController.this.handleCoverDetachedLocked();
            break;
          }
          break;
        case 6:
          DreamyNfcLedCoverController.this.handleAddLedNotification((Bundle) message.obj);
          break;
        case 7:
          DreamyNfcLedCoverController.this.handleRemoveLedNotification((Bundle) message.obj);
          break;
        case 9:
          DreamyNfcLedCoverController.this.handleLcdOffDisabledByCover((Bundle) message.obj);
          break;
        case 10:
          DreamyNfcLedCoverController.this.handleRequestCoverAuthentication(
              ((Long) message.obj).longValue());
          break;
        case 11:
          DreamyNfcLedCoverController.this.handleNotifyAuthResponse();
          break;
        case 12:
          DreamyNfcLedCoverController.this.handleSetFotaInProgress(message.arg1 == 1);
          break;
        case 13:
          DreamyNfcLedCoverController.this.handleNotifyFotaInProgress(message.arg1 == 1);
          break;
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void updateNfcLedCoverAttachStateLocked(boolean z, int i) {
    boolean z2 = z && (i == 7 || i == 14);
    if (this.mIsLedCoverAttached != z2) {
      this.mIsLedCoverAttached = z2;
      if (z2) {
        return;
      }
      Log.d(TAG, "NfcLedCover detached, start clearing all flags, messages, wakelocks");
      Message obtainMessage = this.mHandler.obtainMessage(5);
      obtainMessage.arg1 = z ? 1 : 0;
      this.mHandler.sendMessageAtFrontOfQueue(obtainMessage);
    }
  }

  public final void handleCoverDetachedLocked() {
    Log.d(TAG, "handleCoverDetached()");
    this.mHandler.removeCallbacksAndMessages(null);
    releaseWakeLockWithPermission(this.mSendLedDataWakeLock);
    releaseWakeLockWithPermission(this.mTouchResponseWakeLock);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendDataToNfcLedCover(int i, byte[] bArr) {
    if (!this.mIsLedCoverAttached && !FactoryTest.isFactoryBinary()) {
      Log.d(TAG, "sendDataToLedCover : Not attached LED Cover");
      return;
    }
    if (this.mNfcAdapter == null && getNfcAdapter() == null) {
      Log.d(TAG, "sendDataToLedCover : Nfc Service not available");
      return;
    }
    if (i == -1 || i == -2) {
      Log.d(TAG, "sendDataToLedCover : command: -1 should not be sent, ignore");
      return;
    }
    acquireWakeLockWithPermission(this.mSendLedDataWakeLock);
    Message obtain = Message.obtain();
    obtain.arg1 = i;
    obtain.obj = bArr;
    obtain.what = 0;
    this.mHandler.sendMessage(obtain);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void registerNfcTouchListenerCallback(
      int i, IBinder iBinder, ComponentName componentName) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo != null && iBinder.equals(nfcLedTouchListenerInfo.token)) {
          Log.e(TAG, "sendDataToNfcLedCover : duplicated listener handle");
          return;
        }
      }
      NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
          new NfcLedTouchListenerInfo(
              iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), i);
      iBinder.linkToDeath(nfcLedTouchListenerInfo2, 0);
      this.mListeners.add(nfcLedTouchListenerInfo2);
      if (i == 4) {
        Iterator it2 = this.mEnquedFactoryCommands.iterator();
        while (it2.hasNext()) {
          nfcLedTouchListenerInfo2.onSystemCoverEvent(5, (Bundle) it2.next());
        }
        this.mEnquedFactoryCommands.clear();
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public boolean unRegisterNfcTouchListenerCallback(IBinder iBinder) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo != null && iBinder.equals(nfcLedTouchListenerInfo.token)) {
          Log.e(TAG, "remove listener: " + nfcLedTouchListenerInfo.pid);
          this.mListeners.remove(nfcLedTouchListenerInfo);
          iBinder.unlinkToDeath(nfcLedTouchListenerInfo, 0);
          return true;
        }
      }
      Log.e(TAG, "UnregisterNfcTouchListener: listener does not exist");
      return false;
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void addLedNotification(Bundle bundle) {
    Log.d(TAG, "addLedNotification");
    Message obtainMessage = this.mHandler.obtainMessage(6);
    obtainMessage.obj = bundle;
    this.mHandler.sendMessage(obtainMessage);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void removeLedNotification(Bundle bundle) {
    Log.d(TAG, "removeLedNotification");
    Message obtainMessage = this.mHandler.obtainMessage(7);
    obtainMessage.obj = bundle;
    this.mHandler.sendMessage(obtainMessage);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendSystemEvent(Bundle bundle) {
    int i = bundle.getInt("event_type");
    if (i == 0) {
      acquireWakeLockWithPermission(this.mTouchResponseWakeLock);
      int i2 = bundle.getInt("lcd_touch_listener_type", -1);
      int i3 = bundle.getInt("lcd_touch_listener_respone", -1);
      Message obtainMessage = this.mHandler.obtainMessage(3);
      obtainMessage.arg1 = i2;
      obtainMessage.arg2 = i3;
      obtainMessage.sendToTarget();
      return;
    }
    if (i == 1) {
      Message obtainMessage2 = this.mHandler.obtainMessage(9);
      obtainMessage2.obj = bundle;
      obtainMessage2.sendToTarget();
    } else {
      if (i != 8) {
        return;
      }
      boolean z = bundle.getBoolean("fota_in_progress", false);
      Message obtainMessage3 = this.mHandler.obtainMessage(13);
      obtainMessage3.arg1 = z ? 1 : 0;
      obtainMessage3.sendToTarget();
    }
  }

  public final void handleAddLedNotification(Bundle bundle) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(2, bundle);
        }
      }
    }
  }

  public final void handleRemoveLedNotification(Bundle bundle) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(3, bundle);
        }
      }
    }
  }

  public final void handleLcdOffDisabledByCover(Bundle bundle) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(4, bundle);
        }
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendPowerKeyToCover() {
    this.mHandler.sendEmptyMessage(4);
  }

  public final void handleSendPowerKeyToCover() {
    notifyPowerButtonPressListeners();
  }

  public final void handleSendDataToNfcLedCover(int i, byte[] bArr) {
    boolean z;
    Bundle bundle = new Bundle();
    bundle.putInt("send_command_id", i);
    bundle.putByteArray("send_command_content", bArr);
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      z = false;
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(5, bundle);
          z = true;
        }
      }
    }
    if (!z) {
      Log.w(TAG, "LedCoverService did not finished initalizing, enqueue command " + i);
      if (this.mEnquedFactoryCommands.size() >= 3) {
        this.mEnquedFactoryCommands.poll();
      }
      this.mEnquedFactoryCommands.add(bundle);
    }
    if (this.mHandler.hasMessages(0)) {
      return;
    }
    releaseWakeLockWithPermission(this.mSendLedDataWakeLock);
  }

  public final void handleEventResponse(int i, int i2) {
    String str = TAG;
    Log.d(str, "HandleEventResponse: type: " + i + " action: " + i2);
    if (i2 == 1) {
      Log.d(str, "Event touch: accept");
      synchronized (this.mListeners) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
          if (i == nfcLedTouchListenerInfo.type) {
            nfcLedTouchListenerInfo.onCoverTouchAccept();
          }
        }
      }
    } else if (i2 == 2) {
      Log.d(str, "Event touch: reject");
      synchronized (this.mListeners) {
        Iterator it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 = (NfcLedTouchListenerInfo) it2.next();
          if (i == nfcLedTouchListenerInfo2.type) {
            nfcLedTouchListenerInfo2.onCoverTouchReject();
          }
        }
      }
    } else if (i2 == 3) {
      Log.d(str, "Event touch: tap left");
      synchronized (this.mListeners) {
        Iterator it3 = this.mListeners.iterator();
        while (it3.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo3 = (NfcLedTouchListenerInfo) it3.next();
          if (i == nfcLedTouchListenerInfo3.type) {
            nfcLedTouchListenerInfo3.onCoverTapLeft();
          }
        }
      }
    } else if (i2 == 4) {
      Log.d(str, "Event touch: tap middle");
      synchronized (this.mListeners) {
        Iterator it4 = this.mListeners.iterator();
        while (it4.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo4 = (NfcLedTouchListenerInfo) it4.next();
          if (i == nfcLedTouchListenerInfo4.type) {
            nfcLedTouchListenerInfo4.onCoverTapMid();
          }
        }
      }
    } else if (i2 == 5) {
      Log.d(str, "Event touch: tap right");
      synchronized (this.mListeners) {
        Iterator it5 = this.mListeners.iterator();
        while (it5.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo5 = (NfcLedTouchListenerInfo) it5.next();
          if (i == nfcLedTouchListenerInfo5.type) {
            nfcLedTouchListenerInfo5.onCoverTapRight();
          }
        }
      }
    } else {
      Log.d(str, "Unknown event action: " + i2);
    }
    if (this.mHandler.hasMessages(3)) {
      return;
    }
    releaseWakeLockWithPermission(this.mTouchResponseWakeLock);
  }

  public final void notifyPowerButtonPressListeners() {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        int i = nfcLedTouchListenerInfo.type;
        if (i == 4 || i == 10) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(1, null);
        }
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public boolean requestCoverAuthentication(long j, IBinder iBinder, ComponentName componentName) {
    synchronized (this.mCoverAuthCallbacks) {
      Iterator it = this.mCoverAuthCallbacks.iterator();
      while (it.hasNext()) {
        AuthCallbackInfo authCallbackInfo = (AuthCallbackInfo) it.next();
        if (authCallbackInfo != null && iBinder.equals(authCallbackInfo.token)) {
          Log.e(TAG, "requestCoverAuthentication : duplicated listener handle");
          return false;
        }
      }
      AuthCallbackInfo authCallbackInfo2 =
          new AuthCallbackInfo(
              iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), 4);
      iBinder.linkToDeath(authCallbackInfo2, 0);
      this.mCoverAuthCallbacks.add(authCallbackInfo2);
      Message obtainMessage = this.mHandler.obtainMessage(10);
      obtainMessage.obj = Long.valueOf(j);
      obtainMessage.sendToTarget();
      return true;
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void notifyAuthenticationResponse() {
    this.mHandler.sendEmptyMessage(11);
  }

  public final void handleNotifyAuthResponse() {
    synchronized (this.mCoverAuthCallbacks) {
      Iterator it = this.mCoverAuthCallbacks.iterator();
      while (it.hasNext()) {
        AuthCallbackInfo authCallbackInfo = (AuthCallbackInfo) it.next();
        authCallbackInfo.onSystemCoverEvent(6, null);
        authCallbackInfo.token.unlinkToDeath(authCallbackInfo, 0);
        it.remove();
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
    synchronized (this.mFotaInProgressCallbacks) {
      for (FotaInProgressCallbackInfo fotaInProgressCallbackInfo : this.mFotaInProgressCallbacks) {
        if (fotaInProgressCallbackInfo != null
            && iBinder.equals(fotaInProgressCallbackInfo.token)) {
          Log.e(TAG, "setFotaInProgress : duplicated listener handle");
          return false;
        }
      }
      FotaInProgressCallbackInfo fotaInProgressCallbackInfo2 =
          new FotaInProgressCallbackInfo(
              iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), 4);
      iBinder.linkToDeath(fotaInProgressCallbackInfo2, 0);
      this.mFotaInProgressCallbacks.offer(fotaInProgressCallbackInfo2);
      Message obtainMessage = this.mHandler.obtainMessage(12);
      obtainMessage.arg1 = z ? 1 : 0;
      obtainMessage.sendToTarget();
      return true;
    }
  }

  public final void handleSetFotaInProgress(boolean z) {
    Bundle bundle = new Bundle();
    bundle.putBoolean("fota_in_progress", z);
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(7, bundle);
        }
      }
    }
  }

  public final void handleNotifyFotaInProgress(boolean z) {
    Bundle bundle = new Bundle();
    bundle.putBoolean("fota_in_progress", z);
    synchronized (this.mFotaInProgressCallbacks) {
      FotaInProgressCallbackInfo fotaInProgressCallbackInfo =
          (FotaInProgressCallbackInfo) this.mFotaInProgressCallbacks.poll();
      if (fotaInProgressCallbackInfo != null) {
        fotaInProgressCallbackInfo.onSystemCoverEvent(8, bundle);
        fotaInProgressCallbackInfo.token.unlinkToDeath(fotaInProgressCallbackInfo, 0);
      }
    }
  }

  public class NfcLedTouchListenerInfo implements IBinder.DeathRecipient {
    public final ComponentName component;
    public final int pid;
    public final IBinder token;
    public final int type;
    public final int uid;

    public NfcLedTouchListenerInfo(
        IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
      this.token = iBinder;
      this.component = componentName;
      this.pid = i;
      this.uid = i2;
      this.type = i3;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Log.v(DreamyNfcLedCoverController.TAG, "binderDied : binder = " + this.token);
      synchronized (DreamyNfcLedCoverController.this.mListeners) {
        DreamyNfcLedCoverController.this.mListeners.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
    }

    public void onCoverTouchAccept() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(DreamyNfcLedCoverController.TAG, "null listener received TouchAccept!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTouchAccept();
        }
      } catch (RemoteException e) {
        Log.e(DreamyNfcLedCoverController.TAG, "Failed onCoverTouchAccept callback", e);
      }
    }

    public void onCoverTouchReject() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(DreamyNfcLedCoverController.TAG, "null listener received TouchReject!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTouchReject();
        }
      } catch (RemoteException e) {
        Log.e(DreamyNfcLedCoverController.TAG, "Failed onCoverTouchReject callback", e);
      }
    }

    public void onSystemCoverEvent(int i, Bundle bundle) {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(DreamyNfcLedCoverController.TAG, "null listener received onSystemCoverEvent!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onSystemCoverEvent(i, bundle);
        }
      } catch (RemoteException e) {
        Log.e(DreamyNfcLedCoverController.TAG, "Failed onSystemCoverEvent callback", e);
      }
    }

    public void onCoverTapLeft() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(DreamyNfcLedCoverController.TAG, "null listener received onCoverTapLeft!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTapLeft();
        }
      } catch (RemoteException e) {
        Log.e(DreamyNfcLedCoverController.TAG, "Failed onCoverTapLeft callback", e);
      }
    }

    public void onCoverTapMid() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(DreamyNfcLedCoverController.TAG, "null listener received onCoverTapMid!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTapMid();
        }
      } catch (RemoteException e) {
        Log.e(DreamyNfcLedCoverController.TAG, "Failed onCoverTapMid callback", e);
      }
    }

    public void onCoverTapRight() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(DreamyNfcLedCoverController.TAG, "null listener received onCoverTapRight!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTapRight();
        }
      } catch (RemoteException e) {
        Log.e(DreamyNfcLedCoverController.TAG, "Failed onCoverTapRight callback", e);
      }
    }
  }

  public class AuthCallbackInfo extends NfcLedTouchListenerInfo {
    public AuthCallbackInfo(IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
      super(iBinder, componentName, i, i2, i3);
    }

    @Override // com.android.server.sepunion.cover.DreamyNfcLedCoverController.NfcLedTouchListenerInfo, android.os.IBinder.DeathRecipient
    public void binderDied() {
      Log.v(DreamyNfcLedCoverController.TAG, "binderDied : binder = " + this.token);
      synchronized (DreamyNfcLedCoverController.this.mCoverAuthCallbacks) {
        DreamyNfcLedCoverController.this.mCoverAuthCallbacks.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
    }
  }

  public class FotaInProgressCallbackInfo extends NfcLedTouchListenerInfo {
    public FotaInProgressCallbackInfo(
        IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
      super(iBinder, componentName, i, i2, i3);
    }

    @Override // com.android.server.sepunion.cover.DreamyNfcLedCoverController.NfcLedTouchListenerInfo, android.os.IBinder.DeathRecipient
    public void binderDied() {
      Log.v(DreamyNfcLedCoverController.TAG, "binderDied : binder = " + this.token);
      synchronized (DreamyNfcLedCoverController.this.mFotaInProgressCallbacks) {
        DreamyNfcLedCoverController.this.mFotaInProgressCallbacks.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current NfcLedCoverController state:");
    printWriter.print("  mIsLEDCoverAttached=");
    printWriter.println(this.mIsLedCoverAttached);
    printWriter.println("  ");
    printWriter.println(" Current NFC Callback state:");
    synchronized (this.mListeners) {
      printWriter.println("  Live callbacks (" + this.mListeners.size() + "):");
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo != null) {
          printWriter.println(
              "    "
                  + nfcLedTouchListenerInfo.component
                  + " (pid="
                  + nfcLedTouchListenerInfo.pid
                  + " uid="
                  + nfcLedTouchListenerInfo.uid
                  + " type="
                  + nfcLedTouchListenerInfo.type
                  + ")");
        }
      }
      printWriter.println("  ");
    }
  }
}
