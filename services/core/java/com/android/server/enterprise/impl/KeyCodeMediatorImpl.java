package com.android.server.enterprise.impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IWindowManagerAdapter;
import com.android.server.enterprise.common.KeyCodeMediator;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class KeyCodeMediatorImpl implements KeyCodeMediator, Handler.Callback {
  public final Handler mHandler;
  public KeyCodeMediatorInjector mInjector;
  public final Map mRestrictedKeyCodeBackup;
  public final Set mRestrictionCallbacks;

  public interface KeyCodeMediatorInjector {
    Handler getHandler(Handler.Callback callback);

    void knoxSdkFileLogD(String str, String str2);

    void knoxSdkFileLogW(String str, String str2);
  }

  public KeyCodeMediatorImpl() {
    this(
        new KeyCodeMediatorInjector() { // from class:
                                        // com.android.server.enterprise.impl.KeyCodeMediatorImpl.1
          public Handler mHandler;

          @Override // com.android.server.enterprise.impl.KeyCodeMediatorImpl.KeyCodeMediatorInjector
          public Handler getHandler(Handler.Callback callback) {
            if (this.mHandler == null) {
              this.mHandler = new Handler(Looper.myLooper(), callback);
            }
            return this.mHandler;
          }

          @Override // com.android.server.enterprise.impl.KeyCodeMediatorImpl.KeyCodeMediatorInjector
          public void knoxSdkFileLogD(String str, String str2) {
            KnoxsdkFileLog.w(str, str2);
          }

          @Override // com.android.server.enterprise.impl.KeyCodeMediatorImpl.KeyCodeMediatorInjector
          public void knoxSdkFileLogW(String str, String str2) {
            KnoxsdkFileLog.w(str, str2);
          }
        });
  }

  public KeyCodeMediatorImpl(KeyCodeMediatorInjector keyCodeMediatorInjector) {
    this.mRestrictedKeyCodeBackup = new HashMap();
    this.mRestrictionCallbacks = new HashSet();
    this.mInjector = keyCodeMediatorInjector;
    this.mHandler = keyCodeMediatorInjector.getHandler(this);
  }

  @Override // com.android.server.enterprise.common.KeyCodeMediator
  public void registerCallback(KeyCodeRestrictionCallback keyCodeRestrictionCallback) {
    this.mRestrictionCallbacks.add(keyCodeRestrictionCallback);
  }

  @Override // com.android.server.enterprise.common.KeyCodeMediator
  public boolean update(int i) {
    Handler handler = this.mInjector.getHandler(this);
    if (handler != null) {
      if (handler.hasMessages(i)) {
        handler.removeMessages(i);
      }
      if (handler.sendEmptyMessage(i)) {
        return true;
      }
    }
    this.mInjector.knoxSdkFileLogW("KeyCodeMediator", "Failed to update for keycode :" + i);
    return false;
  }

  @Override // android.os.Handler.Callback
  public boolean handleMessage(Message message) {
    return handleKeyCode(message.what);
  }

  public boolean handleKeyCode(int i) {
    if (hasPreviousConfigurations(i)) {
      clear(i);
    }
    if (!needToBlock(i)) {
      return true;
    }
    block(i);
    return true;
  }

  public final boolean hasPreviousConfigurations(int i) {
    return getWindowManagerAdapter().hasConfigurations(i);
  }

  public final void clear(int i) {
    getWindowManagerAdapter().clearAllConfiguration(i);
  }

  public final void block(int i) {
    getWindowManagerAdapter().blockKeyCode(i);
  }

  public final IWindowManagerAdapter getWindowManagerAdapter() {
    IWindowManagerAdapter iWindowManagerAdapter =
        (IWindowManagerAdapter) AdapterRegistry.getAdapter(IWindowManagerAdapter.class);
    Objects.requireNonNull(iWindowManagerAdapter);
    return iWindowManagerAdapter;
  }

  public final boolean needToBlock(int i) {
    for (KeyCodeRestrictionCallback keyCodeRestrictionCallback : this.mRestrictionCallbacks) {
      if (!keyCodeRestrictionCallback.isKeyCodeInputAllowed(i)) {
        this.mInjector.knoxSdkFileLogD(
            "KeyCodeMediator",
            "keyCode " + i + " must be blocked by " + keyCodeRestrictionCallback.getServiceName());
        return true;
      }
    }
    return false;
  }

  public void onSystemReady() {
    Iterator it = getAllRestrictedKeyCodes().iterator();
    while (it.hasNext()) {
      update(((Integer) it.next()).intValue());
    }
  }

  public void onPreAdminRemoval(int i) {
    this.mRestrictedKeyCodeBackup.put(Integer.valueOf(i), getAllRestrictedKeyCodes());
  }

  public final Set getAllRestrictedKeyCodes() {
    HashSet hashSet = new HashSet();
    for (KeyCodeRestrictionCallback keyCodeRestrictionCallback : this.mRestrictionCallbacks) {
      if (keyCodeRestrictionCallback.getRestrictedKeyCodes() != null) {
        hashSet.addAll(keyCodeRestrictionCallback.getRestrictedKeyCodes());
      }
    }
    return hashSet;
  }

  public void onAdminRemoved(int i) {
    Set set = (Set) this.mRestrictedKeyCodeBackup.get(Integer.valueOf(i));
    this.mRestrictedKeyCodeBackup.remove(Integer.valueOf(i));
    Iterator it = set.iterator();
    while (it.hasNext()) {
      update(((Integer) it.next()).intValue());
    }
  }
}
