package android.nfc;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public final class NfcActivityManager extends IAppCallback.Stub
    implements Application.ActivityLifecycleCallbacks {
  static final Boolean DBG = false;
  private static final String NFC_PERM = "android.permission.NFC";
  static final String TAG = "NFC";
  final NfcAdapter mAdapter;
  final List<NfcActivityState> mActivities = new LinkedList();
  final List<NfcApplicationState> mApps = new ArrayList(1);

  class NfcApplicationState {
    final Application app;
    int refCount = 0;

    public NfcApplicationState(Application app) {
      this.app = app;
    }

    public void register() {
      int i = this.refCount + 1;
      this.refCount = i;
      if (i == 1) {
        this.app.registerActivityLifecycleCallbacks(NfcActivityManager.this);
      }
    }

    public void unregister() {
      int i = this.refCount - 1;
      this.refCount = i;
      if (i == 0) {
        this.app.unregisterActivityLifecycleCallbacks(NfcActivityManager.this);
      } else if (i < 0) {
        Log.m96e(NfcActivityManager.TAG, "-ve refcount for " + this.app);
      }
    }
  }

  NfcApplicationState findAppState(Application app) {
    for (NfcApplicationState appState : this.mApps) {
      if (appState.app == app) {
        return appState;
      }
    }
    return null;
  }

  void registerApplication(Application app) {
    NfcApplicationState appState = findAppState(app);
    if (appState == null) {
      appState = new NfcApplicationState(app);
      this.mApps.add(appState);
    }
    appState.register();
  }

  void unregisterApplication(Application app) {
    NfcApplicationState appState = findAppState(app);
    if (appState == null) {
      Log.m96e(TAG, "app was not registered " + app);
    } else {
      appState.unregister();
    }
  }

  class NfcActivityState {
    Activity activity;
    boolean resumed;
    Binder token;
    NfcAdapter.ReaderCallback readerCallback = null;
    int readerModeFlags = 0;
    Bundle readerModeExtras = null;
    int pollTech = -1;
    int listenTech = -1;
    String proto = null;
    String tech = null;
    int userId = -1;
    List<ComponentName> services = null;

    public NfcActivityState(Activity activity) {
      this.resumed = false;
      if (activity.isDestroyed()) {
        throw new IllegalStateException("activity is already destroyed");
      }
      activity.enforceCallingOrSelfPermission("android.permission.NFC", "NFC permission required.");
      this.resumed = activity.isResumed();
      this.activity = activity;
      this.token = new Binder();
      NfcActivityManager.this.registerApplication(activity.getApplication());
    }

    public void destroy() {
      NfcActivityManager.this.unregisterApplication(this.activity.getApplication());
      this.resumed = false;
      this.activity = null;
      this.readerCallback = null;
      this.readerModeFlags = 0;
      this.readerModeExtras = null;
      this.token = null;
      this.pollTech = -1;
      this.listenTech = -1;
      this.tech = null;
      this.proto = null;
      this.services = null;
      this.userId = -1;
    }

    public String toString() {
      return NavigationBarInflaterView.SIZE_MOD_START
          + this.readerCallback
          + NavigationBarInflaterView.SIZE_MOD_END;
    }
  }

  synchronized NfcActivityState findActivityState(Activity activity) {
    for (NfcActivityState state : this.mActivities) {
      if (state.activity == activity) {
        return state;
      }
    }
    return null;
  }

  synchronized NfcActivityState getActivityState(Activity activity) {
    NfcActivityState state;
    state = findActivityState(activity);
    if (state == null) {
      state = new NfcActivityState(activity);
      this.mActivities.add(state);
    }
    return state;
  }

  synchronized NfcActivityState findResumedActivityState() {
    for (NfcActivityState state : this.mActivities) {
      if (state.resumed) {
        return state;
      }
    }
    return null;
  }

  synchronized void destroyActivityState(Activity activity) {
    NfcActivityState activityState = findActivityState(activity);
    if (activityState != null) {
      activityState.destroy();
      this.mActivities.remove(activityState);
    }
  }

  public NfcActivityManager(NfcAdapter adapter) {
    this.mAdapter = adapter;
  }

  public void enableReaderMode(
      Activity activity, NfcAdapter.ReaderCallback callback, int flags, Bundle extras) {
    Binder token;
    boolean isResumed;
    synchronized (this) {
      NfcActivityState state = getActivityState(activity);
      state.readerCallback = callback;
      state.readerModeFlags = flags;
      state.readerModeExtras = extras;
      token = state.token;
      isResumed = state.resumed;
    }
    if (isResumed) {
      setReaderMode(token, flags, extras);
    }
  }

  public void disableReaderMode(Activity activity) {
    Binder token;
    boolean isResumed;
    synchronized (this) {
      NfcActivityState state = getActivityState(activity);
      state.readerCallback = null;
      state.readerModeFlags = 0;
      state.readerModeExtras = null;
      token = state.token;
      isResumed = state.resumed;
    }
    if (isResumed) {
      setReaderMode(token, 0, null);
    }
  }

  public void setReaderMode(Binder token, int flags, Bundle extras) {
    if (DBG.booleanValue()) {
      Log.m94d(TAG, "Setting reader mode");
    }
    try {
      NfcAdapter.sService.setReaderMode(token, this, flags, extras);
    } catch (RemoteException e) {
      this.mAdapter.attemptDeadServiceRecovery(e);
    }
  }

  public void setDiscoveryTech(Activity activity, int pollTech, int listenTech) {
    Binder token;
    boolean isResumed;
    synchronized (this) {
      NfcActivityState state = getActivityState(activity);
      state.listenTech = listenTech;
      state.pollTech = pollTech;
      token = state.token;
      isResumed = state.resumed;
    }
    if (isResumed) {
      changeDiscoveryTech(token, pollTech, listenTech);
    }
  }

  public void unsetDiscoveryTech(Activity activity) {
    Binder token;
    boolean isResumed;
    synchronized (this) {
      NfcActivityState state = getActivityState(activity);
      state.listenTech = -1;
      state.pollTech = -1;
      token = state.token;
      isResumed = state.resumed;
    }
    if (isResumed) {
      changeDiscoveryTech(token, 255, 255);
    }
  }

  public void changeDiscoveryTech(Binder token, int pollTech, int listenTech) {
    try {
      NfcAdapter.sService.changeDiscoveryTech(token, pollTech, listenTech);
    } catch (RemoteException e) {
      this.mAdapter.attemptDeadServiceRecovery(e);
    }
  }

  public void changeRouting(
      int userHandle, Activity activity, String proto, String tech, List<ComponentName> services) {
    Binder token;
    boolean isResumed;
    synchronized (this) {
      NfcActivityState state = getActivityState(activity);
      token = state.token;
      state.userId = userHandle;
      state.proto = proto;
      state.tech = tech;
      state.services = services;
      isResumed = state.resumed;
    }
    if (isResumed) {
      changeRoutingTable(token, userHandle, proto, tech, services);
    } else {
      Log.m94d(TAG, "Activity must be resumed.");
    }
  }

  public void changeRoutingTable(
      Binder token, int userHandle, String proto, String tech, List<ComponentName> services) {
    try {
      NfcAdapter.sService.changeRoutingTable(token, userHandle, proto, tech, services);
    } catch (RemoteException e) {
      this.mAdapter.attemptDeadServiceRecovery(e);
    }
  }

  void requestNfcServiceCallback() {
    try {
      NfcAdapter.sService.setAppCallback(this);
    } catch (RemoteException e) {
      this.mAdapter.attemptDeadServiceRecovery(e);
    }
  }

  void verifyNfcPermission() {
    try {
      NfcAdapter.sService.verifyNfcPermission();
    } catch (RemoteException e) {
      this.mAdapter.attemptDeadServiceRecovery(e);
    }
  }

  @Override // android.nfc.IAppCallback
  public void onTagDiscovered(Tag tag) throws RemoteException {
    synchronized (this) {
      NfcActivityState state = findResumedActivityState();
      if (state == null) {
        return;
      }
      NfcAdapter.ReaderCallback callback = state.readerCallback;
      if (callback != null) {
        callback.onTagDiscovered(tag);
      }
    }
  }

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivityStarted(Activity activity) {}

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivityResumed(Activity activity) {
    synchronized (this) {
      try {
        NfcActivityState state = findActivityState(activity);
        if (DBG.booleanValue()) {
          try {
            Log.m94d(TAG, "onResume() for " + activity + " " + state);
          } catch (Throwable th) {
            th = th;
            throw th;
          }
        }
        if (state == null) {
          return;
        }
        state.resumed = true;
        Binder token = state.token;
        int readerModeFlags = state.readerModeFlags;
        try {
          Bundle readerModeExtras = state.readerModeExtras;
          int pollTech = state.pollTech;
          try {
            int listenTech = state.listenTech;
            try {
              String proto = state.proto;
              try {
                String tech = state.tech;
                try {
                  List<ComponentName> services = state.services;
                  int userId = state.userId;
                  try {
                    if (readerModeFlags != 0) {
                      setReaderMode(token, readerModeFlags, readerModeExtras);
                    }
                    if (listenTech != -1 || pollTech != -1) {
                      changeDiscoveryTech(token, pollTech, listenTech);
                    }
                    if (proto != null || tech != null || services != null) {
                      changeRoutingTable(token, userId, proto, tech, services);
                    }
                    requestNfcServiceCallback();
                  } catch (Throwable th2) {
                    th = th2;
                    throw th;
                  }
                } catch (Throwable th3) {
                  th = th3;
                }
              } catch (Throwable th4) {
                th = th4;
              }
            } catch (Throwable th5) {
              th = th5;
            }
          } catch (Throwable th6) {
            th = th6;
          }
        } catch (Throwable th7) {
          th = th7;
        }
      } catch (Throwable th8) {
        th = th8;
      }
    }
  }

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivityPaused(Activity activity) {
    synchronized (this) {
      NfcActivityState state = findActivityState(activity);
      if (DBG.booleanValue()) {
        Log.m94d(TAG, "onPause() for " + activity + " " + state);
      }
      if (state == null) {
        return;
      }
      state.resumed = false;
      Binder token = state.token;
      boolean z = true;
      boolean readerModeFlagsSet = state.readerModeFlags != 0;
      boolean pollTechSet = state.pollTech != -1;
      boolean listenTechSet = state.listenTech != -1;
      if (state.proto == null && state.tech == null && state.services == null) {
        z = false;
      }
      boolean changeRoutingFlagsSet = z;
      int userId = state.userId;
      if (readerModeFlagsSet) {
        setReaderMode(token, 0, null);
      }
      if (pollTechSet || listenTechSet) {
        changeDiscoveryTech(token, 255, 255);
      }
      if (changeRoutingFlagsSet) {
        changeRoutingTable(token, userId, null, null, null);
      }
    }
  }

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivityStopped(Activity activity) {}

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

  @Override // android.app.Application.ActivityLifecycleCallbacks
  public void onActivityDestroyed(Activity activity) {
    synchronized (this) {
      NfcActivityState state = findActivityState(activity);
      if (DBG.booleanValue()) {
        Log.m94d(TAG, "onDestroy() for " + activity + " " + state);
      }
      if (state != null) {
        destroyActivityState(activity);
      }
    }
  }
}
