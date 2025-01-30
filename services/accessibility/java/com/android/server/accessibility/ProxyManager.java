package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityTrace;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.util.IntArray;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IntPair;
import com.android.server.LocalServices;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wm.WindowManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ProxyManager {
  public AccessibilityInputFilter mA11yInputFilter;
  public final AccessibilityWindowManager mA11yWindowManager;
  public final Context mContext;
  public final Object mLock;
  public final Handler mMainHandler;
  public final SystemSupport mSystemSupport;
  public final UiAutomationManager mUiAutomationManager;
  public final SparseIntArray mLastStates = new SparseIntArray();
  public final SparseArray mProxyA11yServiceConnections = new SparseArray();
  public VirtualDeviceManagerInternal mLocalVdm =
      (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);

  public interface SystemSupport {
    RemoteCallbackList getCurrentUserClientsLocked();

    RemoteCallbackList getGlobalClientsLocked();

    void notifyClearAccessibilityCacheLocked();

    void removeDeviceIdLocked(int i);

    void updateWindowsForAccessibilityCallbackLocked();
  }

  public ProxyManager(
      Object obj,
      AccessibilityWindowManager accessibilityWindowManager,
      Context context,
      Handler handler,
      UiAutomationManager uiAutomationManager,
      SystemSupport systemSupport) {
    this.mLock = obj;
    this.mA11yWindowManager = accessibilityWindowManager;
    this.mContext = context;
    this.mMainHandler = handler;
    this.mUiAutomationManager = uiAutomationManager;
    this.mSystemSupport = systemSupport;
  }

  public void registerProxy(
      final IAccessibilityServiceClient iAccessibilityServiceClient,
      final int i,
      int i2,
      AccessibilitySecurityPolicy accessibilitySecurityPolicy,
      AbstractAccessibilityServiceConnection.SystemSupport systemSupport,
      AccessibilityTrace accessibilityTrace,
      WindowManagerInternal windowManagerInternal) {
    VirtualDeviceManager virtualDeviceManager =
        (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
    if (virtualDeviceManager == null) {
      return;
    }
    int deviceIdForDisplayId = virtualDeviceManager.getDeviceIdForDisplayId(i);
    AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
    accessibilityServiceInfo.setCapabilities(3);
    accessibilityServiceInfo.setComponentName(new ComponentName("ProxyPackage", "ProxyClass" + i));
    ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
        new ProxyAccessibilityServiceConnection(
            this.mContext,
            accessibilityServiceInfo.getComponentName(),
            accessibilityServiceInfo,
            i2,
            this.mMainHandler,
            this.mLock,
            accessibilitySecurityPolicy,
            systemSupport,
            accessibilityTrace,
            windowManagerInternal,
            this.mA11yWindowManager,
            i,
            deviceIdForDisplayId);
    synchronized (this.mLock) {
      this.mProxyA11yServiceConnections.put(i, proxyAccessibilityServiceConnection);
    }
    iAccessibilityServiceClient
        .asBinder()
        .linkToDeath(
            new IBinder
                .DeathRecipient() { // from class: com.android.server.accessibility.ProxyManager.1
              @Override // android.os.IBinder.DeathRecipient
              public void binderDied() {
                iAccessibilityServiceClient.asBinder().unlinkToDeath(this, 0);
                ProxyManager.this.clearConnectionAndUpdateState(i);
              }
            },
            0);
    this.mMainHandler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            ProxyManager.this.lambda$registerProxy$0(i);
          }
        });
    proxyAccessibilityServiceConnection.initializeServiceInterface(iAccessibilityServiceClient);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerProxy$0(int i) {
    AccessibilityInputFilter accessibilityInputFilter = this.mA11yInputFilter;
    if (accessibilityInputFilter != null) {
      accessibilityInputFilter.disableFeaturesForDisplayIfInstalled(i);
    }
  }

  public boolean unregisterProxy(int i) {
    return clearConnectionAndUpdateState(i);
  }

  public void clearConnections(int i) {
    int i2;
    IntArray intArray = new IntArray();
    synchronized (this.mLock) {
      for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
        ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
            (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i3);
        if (proxyAccessibilityServiceConnection != null
            && proxyAccessibilityServiceConnection.getDeviceId() == i) {
          intArray.add(proxyAccessibilityServiceConnection.getDisplayId());
        }
      }
    }
    for (i2 = 0; i2 < intArray.size(); i2++) {
      clearConnectionAndUpdateState(intArray.get(i2));
    }
  }

  public final boolean clearConnectionAndUpdateState(int i) {
    boolean z;
    int i2;
    synchronized (this.mLock) {
      if (this.mProxyA11yServiceConnections.contains(i)) {
        i2 =
            ((ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.get(i))
                .getDeviceId();
        this.mProxyA11yServiceConnections.remove(i);
        z = true;
      } else {
        z = false;
        i2 = -1;
      }
    }
    if (z) {
      updateStateForRemovedDisplay(i, i2);
    }
    return z;
  }

  public final void updateStateForRemovedDisplay(final int i, int i2) {
    this.mA11yWindowManager.stopTrackingDisplayProxy(i);
    this.mMainHandler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            ProxyManager.this.lambda$updateStateForRemovedDisplay$1(i);
          }
        });
    if (!isProxyedDeviceId(i2)) {
      synchronized (this.mLock) {
        this.mSystemSupport.removeDeviceIdLocked(i2);
        this.mLastStates.delete(i2);
      }
      return;
    }
    onProxyChanged(i2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateStateForRemovedDisplay$1(int i) {
    Display display;
    if (this.mA11yInputFilter == null
        || (display = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(i))
            == null) {
      return;
    }
    this.mA11yInputFilter.enableFeaturesForDisplayIfInstalled(display);
  }

  public boolean isProxyedDisplay(int i) {
    boolean contains;
    synchronized (this.mLock) {
      contains = this.mProxyA11yServiceConnections.contains(i);
    }
    return contains;
  }

  public boolean isProxyedDeviceId(int i) {
    boolean z;
    if (i == 0 && i == -1) {
      return false;
    }
    synchronized (this.mLock) {
      z = getFirstProxyForDeviceIdLocked(i) != null;
    }
    return z;
  }

  public boolean displayBelongsToCaller(int i, int i2) {
    VirtualDeviceManager virtualDeviceManager =
        (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
    VirtualDeviceManagerInternal localVdm = getLocalVdm();
    if (virtualDeviceManager != null && localVdm != null) {
      for (VirtualDevice virtualDevice : virtualDeviceManager.getVirtualDevices()) {
        if (localVdm
                .getDisplayIdsForDevice(virtualDevice.getDeviceId())
                .contains(Integer.valueOf(i2))
            && i == localVdm.getDeviceOwnerUid(virtualDevice.getDeviceId())) {
          return true;
        }
      }
    }
    return false;
  }

  public void sendAccessibilityEventLocked(AccessibilityEvent accessibilityEvent) {
    ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
        (ProxyAccessibilityServiceConnection)
            this.mProxyA11yServiceConnections.get(accessibilityEvent.getDisplayId());
    if (proxyAccessibilityServiceConnection != null) {
      proxyAccessibilityServiceConnection.notifyAccessibilityEvent(accessibilityEvent);
    }
  }

  public boolean canRetrieveInteractiveWindowsLocked() {
    for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
      if (((ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i))
          .mRetrieveInteractiveWindows) {
        return true;
      }
    }
    return false;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public int getStateLocked(int i) {
    int i2 = 0;
    int i3 = this.mUiAutomationManager.isUiAutomationRunningLocked();
    while (i2 < this.mProxyA11yServiceConnections.size()) {
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
          (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i2);
      if (proxyAccessibilityServiceConnection != null
          && proxyAccessibilityServiceConnection.getDeviceId() == i) {
        i3 = (i3 == true ? 1 : 0) | getStateForDisplayIdLocked(proxyAccessibilityServiceConnection);
      }
      i2++;
      i3 = i3;
    }
    return i3 == true ? 1 : 0;
  }

  public final int getStateForDisplayIdLocked(
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection) {
    if (proxyAccessibilityServiceConnection != null) {
      return proxyAccessibilityServiceConnection.mRequestTouchExplorationMode ? 3 : 1;
    }
    return 0;
  }

  public final int getLastSentStateLocked(int i) {
    return this.mLastStates.get(i, 0);
  }

  public final void setLastStateLocked(int i, int i2) {
    this.mLastStates.put(i, i2);
  }

  public final void updateRelevantEventTypesLocked(final int i) {
    if (isProxyedDeviceId(i)) {
      this.mMainHandler.post(
          new Runnable() { // from class:
            // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              ProxyManager.this.lambda$updateRelevantEventTypesLocked$3(i);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateRelevantEventTypesLocked$3(final int i) {
    synchronized (this.mLock) {
      broadcastToClientsLocked(
          FunctionalUtils.ignoreRemoteException(
              new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda8
                public final void acceptOrThrow(Object obj) {
                  ProxyManager.this.lambda$updateRelevantEventTypesLocked$2(
                      i, (AccessibilityManagerService.Client) obj);
                }
              }));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateRelevantEventTypesLocked$2(
      int i, AccessibilityManagerService.Client client) {
    int computeRelevantEventTypesLocked;
    if (client.mDeviceId != i
        || client.mLastSentRelevantEventTypes
            == (computeRelevantEventTypesLocked = computeRelevantEventTypesLocked(client))) {
      return;
    }
    client.mLastSentRelevantEventTypes = computeRelevantEventTypesLocked;
    client.mCallback.setRelevantEventTypes(computeRelevantEventTypesLocked);
  }

  public int computeRelevantEventTypesLocked(AccessibilityManagerService.Client client) {
    int i = 0;
    for (int i2 = 0; i2 < this.mProxyA11yServiceConnections.size(); i2++) {
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
          (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i2);
      if (proxyAccessibilityServiceConnection != null
          && proxyAccessibilityServiceConnection.getDeviceId() == client.mDeviceId) {
        i =
            i
                | proxyAccessibilityServiceConnection.getRelevantEventTypes()
                | (AccessibilityManagerService.isClientInPackageAllowlist(
                        this.mUiAutomationManager.getServiceInfo(), client)
                    ? this.mUiAutomationManager.getRelevantEventTypes()
                    : 0);
      }
    }
    return i;
  }

  public void addServiceInterfacesLocked(List list, int i) {
    for (int i2 = 0; i2 < this.mProxyA11yServiceConnections.size(); i2++) {
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
          (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i2);
      if (proxyAccessibilityServiceConnection != null
          && proxyAccessibilityServiceConnection.getDeviceId() == i) {
        IBinder iBinder = proxyAccessibilityServiceConnection.mService;
        IAccessibilityServiceClient iAccessibilityServiceClient =
            proxyAccessibilityServiceConnection.mServiceInterface;
        if (iBinder != null && iAccessibilityServiceClient != null) {
          list.add(iAccessibilityServiceClient);
        }
      }
    }
  }

  public List getInstalledAndEnabledServiceInfosLocked(int i, int i2) {
    ArrayList arrayList = new ArrayList();
    for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
          (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i3);
      if (proxyAccessibilityServiceConnection != null
          && proxyAccessibilityServiceConnection.getDeviceId() == i2) {
        if (i == -1) {
          arrayList.addAll(proxyAccessibilityServiceConnection.getInstalledAndEnabledServices());
        } else if ((proxyAccessibilityServiceConnection.mFeedbackType & i) != 0) {
          for (AccessibilityServiceInfo accessibilityServiceInfo :
              proxyAccessibilityServiceConnection.getInstalledAndEnabledServices()) {
            if ((accessibilityServiceInfo.feedbackType & i) != 0) {
              arrayList.add(accessibilityServiceInfo);
            }
          }
        }
      }
    }
    return arrayList;
  }

  public void onProxyChanged(int i) {
    synchronized (this.mLock) {
      updateDeviceIdsIfNeededLocked(i);
      this.mSystemSupport.updateWindowsForAccessibilityCallbackLocked();
      updateRelevantEventTypesLocked(i);
      scheduleUpdateProxyClientsIfNeededLocked(i);
      scheduleNotifyProxyClientsOfServicesStateChangeLocked(i);
      updateFocusAppearanceLocked(i);
      this.mSystemSupport.notifyClearAccessibilityCacheLocked();
    }
  }

  public final void scheduleUpdateProxyClientsIfNeededLocked(final int i) {
    final int stateLocked = getStateLocked(i);
    if (getLastSentStateLocked(i) != stateLocked) {
      setLastStateLocked(i, stateLocked);
      this.mMainHandler.post(
          new Runnable() { // from class:
            // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              ProxyManager.this.lambda$scheduleUpdateProxyClientsIfNeededLocked$5(i, stateLocked);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleUpdateProxyClientsIfNeededLocked$5(
      final int i, final int i2) {
    synchronized (this.mLock) {
      broadcastToClientsLocked(
          FunctionalUtils.ignoreRemoteException(
              new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda9
                public final void acceptOrThrow(Object obj) {
                  ProxyManager.lambda$scheduleUpdateProxyClientsIfNeededLocked$4(
                      i, i2, (AccessibilityManagerService.Client) obj);
                }
              }));
    }
  }

  public static /* synthetic */ void lambda$scheduleUpdateProxyClientsIfNeededLocked$4(
      int i, int i2, AccessibilityManagerService.Client client) {
    if (client.mDeviceId == i) {
      client.mCallback.setState(i2);
    }
  }

  public final void scheduleNotifyProxyClientsOfServicesStateChangeLocked(final int i) {
    this.mMainHandler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            ProxyManager.this.lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$7(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$7(
      final int i) {
    broadcastToClientsLocked(
        FunctionalUtils.ignoreRemoteException(
            new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
              // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda7
              public final void acceptOrThrow(Object obj) {
                ProxyManager.this.lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$6(
                    i, (AccessibilityManagerService.Client) obj);
              }
            }));
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleNotifyProxyClientsOfServicesStateChangeLocked$6(
      int i, AccessibilityManagerService.Client client) {
    if (client.mDeviceId == i) {
      synchronized (this.mLock) {
        client.mCallback.notifyServicesStateChanged(getRecommendedTimeoutMillisLocked(i));
      }
    }
  }

  public final void updateFocusAppearanceLocked(int i) {
    final ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked =
        getFirstProxyForDeviceIdLocked(i);
    if (firstProxyForDeviceIdLocked != null) {
      this.mMainHandler.post(
          new Runnable() { // from class:
            // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              ProxyManager.this.lambda$updateFocusAppearanceLocked$9(firstProxyForDeviceIdLocked);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateFocusAppearanceLocked$9(
      final ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection) {
    broadcastToClientsLocked(
        FunctionalUtils.ignoreRemoteException(
            new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
              // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda6
              public final void acceptOrThrow(Object obj) {
                ProxyManager.lambda$updateFocusAppearanceLocked$8(
                    ProxyAccessibilityServiceConnection.this,
                    (AccessibilityManagerService.Client) obj);
              }
            }));
  }

  public static /* synthetic */ void lambda$updateFocusAppearanceLocked$8(
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection,
      AccessibilityManagerService.Client client) {
    if (client.mDeviceId == proxyAccessibilityServiceConnection.getDeviceId()) {
      client.mCallback.setFocusAppearance(
          proxyAccessibilityServiceConnection.getFocusStrokeWidthLocked(),
          proxyAccessibilityServiceConnection.getFocusColorLocked());
    }
  }

  public final ProxyAccessibilityServiceConnection getFirstProxyForDeviceIdLocked(int i) {
    for (int i2 = 0; i2 < this.mProxyA11yServiceConnections.size(); i2++) {
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
          (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i2);
      if (proxyAccessibilityServiceConnection != null
          && proxyAccessibilityServiceConnection.getDeviceId() == i) {
        return proxyAccessibilityServiceConnection;
      }
    }
    return null;
  }

  public final void broadcastToClientsLocked(Consumer consumer) {
    RemoteCallbackList currentUserClientsLocked = this.mSystemSupport.getCurrentUserClientsLocked();
    RemoteCallbackList globalClientsLocked = this.mSystemSupport.getGlobalClientsLocked();
    currentUserClientsLocked.broadcastForEachCookie(consumer);
    globalClientsLocked.broadcastForEachCookie(consumer);
  }

  public void updateTimeoutsIfNeeded(int i, int i2) {
    synchronized (this.mLock) {
      for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
        ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
            (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i3);
        if (proxyAccessibilityServiceConnection != null
            && proxyAccessibilityServiceConnection.updateTimeouts(i, i2)) {
          scheduleNotifyProxyClientsOfServicesStateChangeLocked(
              proxyAccessibilityServiceConnection.getDeviceId());
        }
      }
    }
  }

  public long getRecommendedTimeoutMillisLocked(int i) {
    int i2 = 0;
    int i3 = 0;
    for (int i4 = 0; i4 < this.mProxyA11yServiceConnections.size(); i4++) {
      ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
          (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i4);
      if (proxyAccessibilityServiceConnection != null
          && proxyAccessibilityServiceConnection.getDeviceId() == i) {
        int interactiveTimeout = proxyAccessibilityServiceConnection.getInteractiveTimeout();
        int nonInteractiveTimeout = proxyAccessibilityServiceConnection.getNonInteractiveTimeout();
        i2 = Math.max(interactiveTimeout, i2);
        i3 = Math.max(nonInteractiveTimeout, i3);
      }
    }
    return IntPair.of(i2, i3);
  }

  public int getFocusStrokeWidthLocked(int i) {
    ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked =
        getFirstProxyForDeviceIdLocked(i);
    if (firstProxyForDeviceIdLocked != null) {
      return firstProxyForDeviceIdLocked.getFocusStrokeWidthLocked();
    }
    return 0;
  }

  public int getFocusColorLocked(int i) {
    ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked =
        getFirstProxyForDeviceIdLocked(i);
    if (firstProxyForDeviceIdLocked != null) {
      return firstProxyForDeviceIdLocked.getFocusColorLocked();
    }
    return 0;
  }

  public int getFirstDeviceIdForUidLocked(int i) {
    VirtualDeviceManagerInternal localVdm = getLocalVdm();
    if (localVdm == null) {
      return -1;
    }
    for (Integer num : localVdm.getDeviceIdsForUid(i)) {
      if (num.intValue() != 0 && num.intValue() != -1) {
        return num.intValue();
      }
    }
    return -1;
  }

  public final void updateDeviceIdsIfNeededLocked(int i) {
    RemoteCallbackList currentUserClientsLocked = this.mSystemSupport.getCurrentUserClientsLocked();
    RemoteCallbackList globalClientsLocked = this.mSystemSupport.getGlobalClientsLocked();
    updateDeviceIdsIfNeededLocked(i, currentUserClientsLocked);
    updateDeviceIdsIfNeededLocked(i, globalClientsLocked);
  }

  public final void updateDeviceIdsIfNeededLocked(int i, RemoteCallbackList remoteCallbackList) {
    VirtualDeviceManagerInternal localVdm = getLocalVdm();
    if (localVdm == null) {
      return;
    }
    for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
      AccessibilityManagerService.Client client =
          (AccessibilityManagerService.Client) remoteCallbackList.getRegisteredCallbackCookie(i2);
      if (i != 0
          && i != -1
          && localVdm.getDeviceIdsForUid(client.mUid).contains(Integer.valueOf(i))) {
        client.mDeviceId = i;
      }
    }
  }

  public void clearCacheLocked() {
    for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
      ((ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i))
          .notifyClearAccessibilityNodeInfoCache();
    }
  }

  public void setAccessibilityInputFilter(AccessibilityInputFilter accessibilityInputFilter) {
    this.mA11yInputFilter = accessibilityInputFilter;
  }

  public final VirtualDeviceManagerInternal getLocalVdm() {
    if (this.mLocalVdm == null) {
      this.mLocalVdm =
          (VirtualDeviceManagerInternal)
              LocalServices.getService(VirtualDeviceManagerInternal.class);
    }
    return this.mLocalVdm;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    synchronized (this.mLock) {
      printWriter.println();
      printWriter.println("Proxy manager state:");
      printWriter.println(
          "    Number of proxy connections: " + this.mProxyA11yServiceConnections.size());
      printWriter.println("    Registered proxy connections:");
      RemoteCallbackList currentUserClientsLocked =
          this.mSystemSupport.getCurrentUserClientsLocked();
      RemoteCallbackList globalClientsLocked = this.mSystemSupport.getGlobalClientsLocked();
      for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
        ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
            (ProxyAccessibilityServiceConnection) this.mProxyA11yServiceConnections.valueAt(i);
        if (proxyAccessibilityServiceConnection != null) {
          proxyAccessibilityServiceConnection.dump(fileDescriptor, printWriter, strArr);
        }
        printWriter.println();
        printWriter.println("        User clients for proxy's virtual device id");
        printClientsForDeviceId(
            printWriter,
            currentUserClientsLocked,
            proxyAccessibilityServiceConnection.getDeviceId());
        printWriter.println();
        printWriter.println("        Global clients for proxy's virtual device id");
        printClientsForDeviceId(
            printWriter, globalClientsLocked, proxyAccessibilityServiceConnection.getDeviceId());
      }
    }
  }

  public final void printClientsForDeviceId(
      PrintWriter printWriter, RemoteCallbackList remoteCallbackList, int i) {
    if (remoteCallbackList != null) {
      for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
        AccessibilityManagerService.Client client =
            (AccessibilityManagerService.Client) remoteCallbackList.getRegisteredCallbackCookie(i2);
        if (client.mDeviceId == i) {
          printWriter.println(
              "            "
                  + Arrays.toString(client.mPackageNames)
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
    }
  }
}
