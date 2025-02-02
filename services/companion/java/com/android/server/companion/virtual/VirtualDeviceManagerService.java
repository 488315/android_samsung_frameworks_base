package com.android.server.companion.virtual;

import android.R;
import android.app.ActivityOptions;
import android.companion.AssociationInfo;
import android.companion.CompanionDeviceManager;
import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.IVirtualDeviceActivityListener;
import android.companion.virtual.IVirtualDeviceManager;
import android.companion.virtual.IVirtualDeviceSoundEffectListener;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceParams;
import android.companion.virtual.sensor.VirtualSensor;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplayConfig;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.DumpUtils;
import com.android.server.SystemService;
import com.android.server.wm.ActivityInterceptorCallback;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class VirtualDeviceManagerService extends SystemService {
  public static AtomicInteger sNextUniqueIndex = new AtomicInteger(1);
  public final ActivityInterceptorCallback mActivityInterceptorCallback;
  public final SparseArray mAppsOnVirtualDevices;
  public final CompanionDeviceManager.OnAssociationsChangedListener mCdmAssociationListener;
  public final Handler mHandler;
  public final VirtualDeviceManagerImpl mImpl;
  public final VirtualDeviceManagerInternal mLocalService;
  public final PendingTrampolineMap mPendingTrampolines;
  public final Object mVirtualDeviceManagerLock;
  public final SparseArray mVirtualDevices;

  public VirtualDeviceManagerService(Context context) {
    super(context);
    this.mVirtualDeviceManagerLock = new Object();
    Handler handler = new Handler(Looper.getMainLooper());
    this.mHandler = handler;
    this.mPendingTrampolines = new PendingTrampolineMap(handler);
    this.mCdmAssociationListener =
        new CompanionDeviceManager.OnAssociationsChangedListener() { // from class:
          // com.android.server.companion.virtual.VirtualDeviceManagerService.1
          public void onAssociationsChanged(List list) {
            VirtualDeviceManagerService.this.syncVirtualDevicesToCdmAssociations(list);
          }
        };
    this.mVirtualDevices = new SparseArray();
    this.mAppsOnVirtualDevices = new SparseArray();
    this.mActivityInterceptorCallback =
        new ActivityInterceptorCallback() { // from class:
          // com.android.server.companion.virtual.VirtualDeviceManagerService.2
          @Override // com.android.server.wm.ActivityInterceptorCallback
          public ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch(
              ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
            VirtualDeviceImpl.PendingTrampoline remove;
            if (activityInterceptorInfo.getCallingPackage() == null
                || (remove =
                        VirtualDeviceManagerService.this.mPendingTrampolines.remove(
                            activityInterceptorInfo.getCallingPackage()))
                    == null) {
              return null;
            }
            remove.mResultReceiver.send(0, null);
            ActivityOptions checkedOptions = activityInterceptorInfo.getCheckedOptions();
            if (checkedOptions == null) {
              checkedOptions = ActivityOptions.makeBasic();
            }
            return new ActivityInterceptorCallback.ActivityInterceptResult(
                activityInterceptorInfo.getIntent(),
                checkedOptions.setLaunchDisplayId(remove.mDisplayId));
          }
        };
    this.mImpl = new VirtualDeviceManagerImpl();
    this.mLocalService = new LocalService();
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("virtualdevice", this.mImpl);
    publishLocalService(VirtualDeviceManagerInternal.class, this.mLocalService);
    ((ActivityTaskManagerInternal) getLocalService(ActivityTaskManagerInternal.class))
        .registerActivityStartInterceptor(3, this.mActivityInterceptorCallback);
  }

  public void onCameraAccessBlocked(int i) {
    ArrayList virtualDevicesSnapshot = getVirtualDevicesSnapshot();
    for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
      VirtualDeviceImpl virtualDeviceImpl = (VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
      virtualDeviceImpl.showToastWhereUidIsRunning(
          i,
          getContext().getString(17043217, virtualDeviceImpl.getDisplayName()),
          1,
          Looper.myLooper());
    }
  }

  public CameraAccessController getCameraAccessController(UserHandle userHandle) {
    int identifier = userHandle.getIdentifier();
    synchronized (this.mVirtualDeviceManagerLock) {
      for (int i = 0; i < this.mVirtualDevices.size(); i++) {
        CameraAccessController cameraAccessController =
            ((VirtualDeviceImpl) this.mVirtualDevices.valueAt(i)).getCameraAccessController();
        if (cameraAccessController.getUserId() == identifier) {
          return cameraAccessController;
        }
      }
      return new CameraAccessController(
          getContext().createContextAsUser(userHandle, 0),
          this.mLocalService,
          new CameraAccessController.CameraAccessBlockedCallback() { // from class:
            // com.android.server.companion.virtual.VirtualDeviceManagerService$$ExternalSyntheticLambda0
            @Override // com.android.server.companion.virtual.CameraAccessController.CameraAccessBlockedCallback
            public final void onCameraAccessBlocked(int i2) {
              VirtualDeviceManagerService.this.onCameraAccessBlocked(i2);
            }
          });
    }
  }

  public VirtualDeviceManagerInternal getLocalServiceInstance() {
    return this.mLocalService;
  }

  public void notifyRunningAppsChanged(int i, ArraySet arraySet) {
    synchronized (this.mVirtualDeviceManagerLock) {
      if (!this.mVirtualDevices.contains(i)) {
        Slog.e(
            "VirtualDeviceManagerService",
            "notifyRunningAppsChanged called for unknown deviceId:"
                + i
                + " (maybe it was recently closed?)");
        return;
      }
      this.mAppsOnVirtualDevices.put(i, arraySet);
      this.mLocalService.onAppsOnVirtualDeviceChanged();
    }
  }

  public void addVirtualDevice(VirtualDeviceImpl virtualDeviceImpl) {
    synchronized (this.mVirtualDeviceManagerLock) {
      this.mVirtualDevices.put(virtualDeviceImpl.getDeviceId(), virtualDeviceImpl);
    }
  }

  public boolean removeVirtualDevice(int i) {
    synchronized (this.mVirtualDeviceManagerLock) {
      if (!this.mVirtualDevices.contains(i)) {
        return false;
      }
      this.mAppsOnVirtualDevices.remove(i);
      this.mVirtualDevices.remove(i);
      Intent intent = new Intent("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED");
      intent.putExtra("android.companion.virtual.extra.VIRTUAL_DEVICE_ID", i);
      intent.setFlags(1073741824);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        getContext().sendBroadcastAsUser(intent, UserHandle.ALL);
        synchronized (this.mVirtualDeviceManagerLock) {
          if (this.mVirtualDevices.size() == 0) {
            unregisterCdmAssociationListener();
          }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
  }

  public final void syncVirtualDevicesToCdmAssociations(List list) {
    HashSet hashSet = new HashSet();
    synchronized (this.mVirtualDeviceManagerLock) {
      if (this.mVirtualDevices.size() == 0) {
        return;
      }
      HashSet hashSet2 = new HashSet(list.size());
      Iterator it = list.iterator();
      while (it.hasNext()) {
        hashSet2.add(Integer.valueOf(((AssociationInfo) it.next()).getId()));
      }
      for (int i = 0; i < this.mVirtualDevices.size(); i++) {
        VirtualDeviceImpl virtualDeviceImpl = (VirtualDeviceImpl) this.mVirtualDevices.valueAt(i);
        if (!hashSet2.contains(Integer.valueOf(virtualDeviceImpl.getAssociationId()))) {
          hashSet.add(virtualDeviceImpl);
        }
      }
      Iterator it2 = hashSet.iterator();
      while (it2.hasNext()) {
        ((VirtualDeviceImpl) it2.next()).close();
      }
    }
  }

  public final void registerCdmAssociationListener() {
    ((CompanionDeviceManager) getContext().getSystemService(CompanionDeviceManager.class))
        .addOnAssociationsChangedListener(
            getContext().getMainExecutor(), this.mCdmAssociationListener);
  }

  public final void unregisterCdmAssociationListener() {
    ((CompanionDeviceManager) getContext().getSystemService(CompanionDeviceManager.class))
        .removeOnAssociationsChangedListener(this.mCdmAssociationListener);
  }

  public final ArrayList getVirtualDevicesSnapshot() {
    ArrayList arrayList;
    synchronized (this.mVirtualDeviceManagerLock) {
      arrayList = new ArrayList(this.mVirtualDevices.size());
      for (int i = 0; i < this.mVirtualDevices.size(); i++) {
        arrayList.add((VirtualDeviceImpl) this.mVirtualDevices.valueAt(i));
      }
    }
    return arrayList;
  }

  public class VirtualDeviceManagerImpl extends IVirtualDeviceManager.Stub {
    public final VirtualDeviceImpl.PendingTrampolineCallback mPendingTrampolineCallback =
        new VirtualDeviceImpl.PendingTrampolineCallback() { // from class:
          // com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl.1
          @Override // com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback
          public void startWaitingForPendingTrampoline(
              VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
            VirtualDeviceImpl.PendingTrampoline put =
                VirtualDeviceManagerService.this.mPendingTrampolines.put(
                    pendingTrampoline.mPendingIntent.getCreatorPackage(), pendingTrampoline);
            if (put != null) {
              put.mResultReceiver.send(2, null);
            }
          }

          @Override // com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback
          public void stopWaitingForPendingTrampoline(
              VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
            VirtualDeviceManagerService.this.mPendingTrampolines.remove(
                pendingTrampoline.mPendingIntent.getCreatorPackage());
          }
        };

    public VirtualDeviceManagerImpl() {}

    public IVirtualDevice createVirtualDevice(
        IBinder iBinder,
        String str,
        int i,
        VirtualDeviceParams virtualDeviceParams,
        IVirtualDeviceActivityListener iVirtualDeviceActivityListener,
        IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) {
      VirtualDeviceManagerService.this
          .getContext()
          .enforceCallingOrSelfPermission(
              "android.permission.CREATE_VIRTUAL_DEVICE", "createVirtualDevice");
      int callingUid = IVirtualDeviceManager.Stub.getCallingUid();
      if (!PermissionUtils.validateCallingPackageName(
          VirtualDeviceManagerService.this.getContext(), str)) {
        throw new SecurityException(
            "Package name " + str + " does not belong to calling uid " + callingUid);
      }
      AssociationInfo associationInfo = getAssociationInfo(str, i);
      if (associationInfo == null) {
        throw new IllegalArgumentException("No association with ID " + i);
      }
      Objects.requireNonNull(virtualDeviceParams);
      Objects.requireNonNull(iVirtualDeviceActivityListener);
      Objects.requireNonNull(iVirtualDeviceSoundEffectListener);
      CameraAccessController cameraAccessController =
          VirtualDeviceManagerService.this.getCameraAccessController(
              IVirtualDeviceManager.Stub.getCallingUserHandle());
      final int andIncrement = VirtualDeviceManagerService.sNextUniqueIndex.getAndIncrement();
      VirtualDeviceImpl virtualDeviceImpl =
          new VirtualDeviceImpl(
              VirtualDeviceManagerService.this.getContext(),
              associationInfo,
              VirtualDeviceManagerService.this,
              iBinder,
              callingUid,
              andIncrement,
              cameraAccessController,
              this.mPendingTrampolineCallback,
              iVirtualDeviceActivityListener,
              iVirtualDeviceSoundEffectListener,
              new Consumer() { // from class:
                // com.android.server.companion.virtual.VirtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  VirtualDeviceManagerService.VirtualDeviceManagerImpl.this
                      .lambda$createVirtualDevice$0(andIncrement, (ArraySet) obj);
                }
              },
              virtualDeviceParams);
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        if (VirtualDeviceManagerService.this.mVirtualDevices.size() == 0) {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            VirtualDeviceManagerService.this.registerCdmAssociationListener();
            Binder.restoreCallingIdentity(clearCallingIdentity);
          } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
          }
        }
        VirtualDeviceManagerService.this.mVirtualDevices.put(andIncrement, virtualDeviceImpl);
      }
      return virtualDeviceImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVirtualDevice$0(int i, ArraySet arraySet) {
      VirtualDeviceManagerService.this.notifyRunningAppsChanged(i, arraySet);
    }

    public int createVirtualDisplay(
        VirtualDisplayConfig virtualDisplayConfig,
        IVirtualDisplayCallback iVirtualDisplayCallback,
        IVirtualDevice iVirtualDevice,
        String str) {
      VirtualDeviceImpl virtualDeviceImpl;
      Objects.requireNonNull(virtualDisplayConfig);
      int callingUid = IVirtualDeviceManager.Stub.getCallingUid();
      if (!PermissionUtils.validateCallingPackageName(
          VirtualDeviceManagerService.this.getContext(), str)) {
        throw new SecurityException(
            "Package name " + str + " does not belong to calling uid " + callingUid);
      }
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDeviceImpl =
            (VirtualDeviceImpl)
                VirtualDeviceManagerService.this.mVirtualDevices.get(iVirtualDevice.getDeviceId());
        if (virtualDeviceImpl == null) {
          throw new SecurityException("Invalid VirtualDevice");
        }
      }
      if (virtualDeviceImpl.getOwnerUid() != callingUid) {
        throw new SecurityException(
            "uid " + callingUid + " is not the owner of the supplied VirtualDevice");
      }
      int createVirtualDisplay =
          virtualDeviceImpl.createVirtualDisplay(
              virtualDisplayConfig, iVirtualDisplayCallback, str);
      VirtualDeviceManagerService.this.mLocalService.onVirtualDisplayCreated(createVirtualDisplay);
      return createVirtualDisplay;
    }

    public List getVirtualDevices() {
      ArrayList arrayList = new ArrayList();
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        for (int i = 0; i < VirtualDeviceManagerService.this.mVirtualDevices.size(); i++) {
          VirtualDeviceImpl virtualDeviceImpl =
              (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.valueAt(i);
          arrayList.add(
              new VirtualDevice(
                  virtualDeviceImpl.getDeviceId(), virtualDeviceImpl.getDeviceName()));
        }
      }
      return arrayList;
    }

    public int getDevicePolicy(int i, int i2) {
      int devicePolicy;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        VirtualDeviceImpl virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
        devicePolicy = virtualDeviceImpl != null ? virtualDeviceImpl.getDevicePolicy(i2) : 0;
      }
      return devicePolicy;
    }

    public int getDeviceIdForDisplayId(int i) {
      if (i != -1 && i != 0) {
        ArrayList virtualDevicesSnapshot =
            VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
        for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
          VirtualDeviceImpl virtualDeviceImpl = (VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
          if (virtualDeviceImpl.isDisplayOwnedByVirtualDevice(i)) {
            return virtualDeviceImpl.getDeviceId();
          }
        }
      }
      return 0;
    }

    public boolean isValidVirtualDeviceId(int i) {
      boolean contains;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        contains = VirtualDeviceManagerService.this.mVirtualDevices.contains(i);
      }
      return contains;
    }

    public int getAudioPlaybackSessionId(int i) {
      int audioPlaybackSessionId;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        VirtualDeviceImpl virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
        audioPlaybackSessionId =
            virtualDeviceImpl != null ? virtualDeviceImpl.getAudioPlaybackSessionId() : 0;
      }
      return audioPlaybackSessionId;
    }

    public int getAudioRecordingSessionId(int i) {
      int audioRecordingSessionId;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        VirtualDeviceImpl virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
        audioRecordingSessionId =
            virtualDeviceImpl != null ? virtualDeviceImpl.getAudioRecordingSessionId() : 0;
      }
      return audioRecordingSessionId;
    }

    public void playSoundEffect(int i, int i2) {
      VirtualDeviceImpl virtualDeviceImpl;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
      }
      if (virtualDeviceImpl != null) {
        virtualDeviceImpl.playSoundEffect(i2);
      }
    }

    public final AssociationInfo getAssociationInfo(String str, int i) {
      UserHandle callingUserHandle = IVirtualDeviceManager.Stub.getCallingUserHandle();
      CompanionDeviceManager companionDeviceManager =
          (CompanionDeviceManager)
              VirtualDeviceManagerService.this
                  .getContext()
                  .createContextAsUser(callingUserHandle, 0)
                  .getSystemService(CompanionDeviceManager.class);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        List allAssociations = companionDeviceManager.getAllAssociations();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        int identifier = callingUserHandle.getIdentifier();
        if (allAssociations != null) {
          int size = allAssociations.size();
          for (int i2 = 0; i2 < size; i2++) {
            AssociationInfo associationInfo = (AssociationInfo) allAssociations.get(i2);
            if (associationInfo.belongsToPackage(identifier, str) && i == associationInfo.getId()) {
              return associationInfo;
            }
          }
          return null;
        }
        Slog.w("VirtualDeviceManagerService", "No associations for user " + identifier);
        return null;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      try {
        return super.onTransact(i, parcel, parcel2, i2);
      } catch (Throwable th) {
        Slog.e("VirtualDeviceManagerService", "Error during IPC", th);
        throw ExceptionUtils.propagate(th, RemoteException.class);
      }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpAndUsageStatsPermission(
          VirtualDeviceManagerService.this.getContext(),
          "VirtualDeviceManagerService",
          printWriter)) {
        printWriter.println("Created virtual devices: ");
        ArrayList virtualDevicesSnapshot =
            VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
        for (int i = 0; i < virtualDevicesSnapshot.size(); i++) {
          ((VirtualDeviceImpl) virtualDevicesSnapshot.get(i))
              .dump(fileDescriptor, printWriter, strArr);
        }
      }
    }
  }

  public final class LocalService extends VirtualDeviceManagerInternal {
    public final ArraySet mAllUidsOnVirtualDevice;
    public final ArrayList mAppsOnVirtualDeviceListeners;
    public final ArrayList mVirtualDisplayListeners;

    public LocalService() {
      this.mVirtualDisplayListeners = new ArrayList();
      this.mAppsOnVirtualDeviceListeners = new ArrayList();
      this.mAllUidsOnVirtualDevice = new ArraySet();
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public int getDeviceOwnerUid(int i) {
      VirtualDeviceImpl virtualDeviceImpl;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
      }
      if (virtualDeviceImpl != null) {
        return virtualDeviceImpl.getOwnerUid();
      }
      return -1;
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public VirtualSensor getVirtualSensor(int i, int i2) {
      VirtualDeviceImpl virtualDeviceImpl;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
      }
      if (virtualDeviceImpl != null) {
        return virtualDeviceImpl.getVirtualSensorByHandle(i2);
      }
      return null;
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public ArraySet getDeviceIdsForUid(int i) {
      ArrayList virtualDevicesSnapshot =
          VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
      ArraySet arraySet = new ArraySet();
      for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
        VirtualDeviceImpl virtualDeviceImpl = (VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
        if (virtualDeviceImpl.isAppRunningOnVirtualDevice(i)) {
          arraySet.add(Integer.valueOf(virtualDeviceImpl.getDeviceId()));
        }
      }
      return arraySet;
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public void onVirtualDisplayCreated(final int i) {
      final VirtualDeviceManagerInternal.VirtualDisplayListener[] virtualDisplayListenerArr;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDisplayListenerArr =
            (VirtualDeviceManagerInternal.VirtualDisplayListener[])
                this.mVirtualDisplayListeners.toArray(
                    new VirtualDeviceManagerInternal.VirtualDisplayListener[0]);
      }
      VirtualDeviceManagerService.this.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.companion.virtual.VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              VirtualDeviceManagerService.LocalService.lambda$onVirtualDisplayCreated$0(
                  virtualDisplayListenerArr, i);
            }
          });
    }

    public static /* synthetic */ void lambda$onVirtualDisplayCreated$0(
        VirtualDeviceManagerInternal.VirtualDisplayListener[] virtualDisplayListenerArr, int i) {
      for (VirtualDeviceManagerInternal.VirtualDisplayListener virtualDisplayListener :
          virtualDisplayListenerArr) {
        virtualDisplayListener.onVirtualDisplayCreated(i);
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public void onVirtualDisplayRemoved(IVirtualDevice iVirtualDevice, final int i) {
      final VirtualDeviceManagerInternal.VirtualDisplayListener[] virtualDisplayListenerArr;
      VirtualDeviceImpl virtualDeviceImpl;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDisplayListenerArr =
            (VirtualDeviceManagerInternal.VirtualDisplayListener[])
                this.mVirtualDisplayListeners.toArray(
                    new VirtualDeviceManagerInternal.VirtualDisplayListener[0]);
        virtualDeviceImpl =
            (VirtualDeviceImpl)
                VirtualDeviceManagerService.this.mVirtualDevices.get(
                    ((VirtualDeviceImpl) iVirtualDevice).getDeviceId());
      }
      if (virtualDeviceImpl != null) {
        virtualDeviceImpl.onVirtualDisplayRemoved(i);
      }
      VirtualDeviceManagerService.this.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.companion.virtual.VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              VirtualDeviceManagerService.LocalService.lambda$onVirtualDisplayRemoved$1(
                  virtualDisplayListenerArr, i);
            }
          });
    }

    public static /* synthetic */ void lambda$onVirtualDisplayRemoved$1(
        VirtualDeviceManagerInternal.VirtualDisplayListener[] virtualDisplayListenerArr, int i) {
      for (VirtualDeviceManagerInternal.VirtualDisplayListener virtualDisplayListener :
          virtualDisplayListenerArr) {
        virtualDisplayListener.onVirtualDisplayRemoved(i);
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public void onAppsOnVirtualDeviceChanged() {
      final VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[]
          appsOnVirtualDeviceListenerArr;
      final ArraySet arraySet = new ArraySet();
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        int size = VirtualDeviceManagerService.this.mAppsOnVirtualDevices.size();
        for (int i = 0; i < size; i++) {
          arraySet.addAll(
              (ArraySet) VirtualDeviceManagerService.this.mAppsOnVirtualDevices.valueAt(i));
        }
        if (this.mAllUidsOnVirtualDevice.equals(arraySet)) {
          appsOnVirtualDeviceListenerArr = null;
        } else {
          this.mAllUidsOnVirtualDevice.clear();
          this.mAllUidsOnVirtualDevice.addAll(arraySet);
          appsOnVirtualDeviceListenerArr =
              (VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[])
                  this.mAppsOnVirtualDeviceListeners.toArray(
                      new VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[0]);
        }
      }
      if (appsOnVirtualDeviceListenerArr != null) {
        VirtualDeviceManagerService.this.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.companion.virtual.VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                VirtualDeviceManagerService.LocalService.lambda$onAppsOnVirtualDeviceChanged$2(
                    appsOnVirtualDeviceListenerArr, arraySet);
              }
            });
      }
    }

    public static /* synthetic */ void lambda$onAppsOnVirtualDeviceChanged$2(
        VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[] appsOnVirtualDeviceListenerArr,
        ArraySet arraySet) {
      for (VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener :
          appsOnVirtualDeviceListenerArr) {
        appsOnVirtualDeviceListener.onAppsOnAnyVirtualDeviceChanged(arraySet);
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public void onAuthenticationPrompt(int i) {
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        for (int i2 = 0; i2 < VirtualDeviceManagerService.this.mVirtualDevices.size(); i2++) {
          ((VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.valueAt(i2))
              .showToastWhereUidIsRunning(
                  i, R.string.color_inversion_feature_name, 1, Looper.getMainLooper());
        }
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public int getBaseVirtualDisplayFlags(IVirtualDevice iVirtualDevice) {
      return ((VirtualDeviceImpl) iVirtualDevice).getBaseVirtualDisplayFlags();
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public LocaleList getPreferredLocaleListForUid(int i) {
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        for (int i2 = 0; i2 < VirtualDeviceManagerService.this.mAppsOnVirtualDevices.size(); i2++) {
          if (((ArraySet) VirtualDeviceManagerService.this.mAppsOnVirtualDevices.valueAt(i2))
              .contains(Integer.valueOf(i))) {
            return ((VirtualDeviceImpl)
                    VirtualDeviceManagerService.this.mVirtualDevices.get(
                        VirtualDeviceManagerService.this.mAppsOnVirtualDevices.keyAt(i2)))
                .getDeviceLocaleList();
          }
        }
        return null;
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public boolean isAppRunningOnAnyVirtualDevice(int i) {
      ArrayList virtualDevicesSnapshot =
          VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
      for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
        if (((VirtualDeviceImpl) virtualDevicesSnapshot.get(i2)).isAppRunningOnVirtualDevice(i)) {
          return true;
        }
      }
      return false;
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public boolean isDisplayOwnedByAnyVirtualDevice(int i) {
      ArrayList virtualDevicesSnapshot =
          VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
      for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
        if (((VirtualDeviceImpl) virtualDevicesSnapshot.get(i2)).isDisplayOwnedByVirtualDevice(i)) {
          return true;
        }
      }
      return false;
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public ArraySet getDisplayIdsForDevice(int i) {
      VirtualDeviceImpl virtualDeviceImpl;
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        virtualDeviceImpl =
            (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
      }
      return virtualDeviceImpl == null ? new ArraySet() : virtualDeviceImpl.getDisplayIds();
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public void registerVirtualDisplayListener(
        VirtualDeviceManagerInternal.VirtualDisplayListener virtualDisplayListener) {
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        this.mVirtualDisplayListeners.add(virtualDisplayListener);
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
    public void registerAppsOnVirtualDeviceListener(
        VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener) {
      synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
        this.mAppsOnVirtualDeviceListeners.add(appsOnVirtualDeviceListener);
      }
    }
  }

  public final class PendingTrampolineMap {
    public final Handler mHandler;
    public final ConcurrentHashMap mMap = new ConcurrentHashMap();

    public PendingTrampolineMap(Handler handler) {
      this.mHandler = handler;
    }

    public VirtualDeviceImpl.PendingTrampoline put(
        String str, final VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
      VirtualDeviceImpl.PendingTrampoline pendingTrampoline2 =
          (VirtualDeviceImpl.PendingTrampoline) this.mMap.put(str, pendingTrampoline);
      this.mHandler.removeCallbacksAndMessages(pendingTrampoline2);
      this.mHandler.postDelayed(
          new Runnable() { // from class:
            // com.android.server.companion.virtual.VirtualDeviceManagerService$PendingTrampolineMap$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              VirtualDeviceManagerService.PendingTrampolineMap.this.lambda$put$0(pendingTrampoline);
            }
          },
          pendingTrampoline,
          5000L);
      return pendingTrampoline2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$put$0(
        VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
      String creatorPackage = pendingTrampoline.mPendingIntent.getCreatorPackage();
      if (creatorPackage != null) {
        remove(creatorPackage);
      }
    }

    public VirtualDeviceImpl.PendingTrampoline remove(String str) {
      VirtualDeviceImpl.PendingTrampoline pendingTrampoline =
          (VirtualDeviceImpl.PendingTrampoline) this.mMap.remove(str);
      this.mHandler.removeCallbacksAndMessages(pendingTrampoline);
      return pendingTrampoline;
    }
  }
}
