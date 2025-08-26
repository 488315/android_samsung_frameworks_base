package android.app;

import android.app.ActivityOptions;
import android.app.IHwuiCallback;
import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.app.servertransaction.ClientTransaction;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ProviderInfoList;
import android.content.pm.ServiceInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.net.ProxyInfoWrapper;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.os.instrumentation.IOffsetCallback;
import android.os.instrumentation.MethodDescriptor;
import android.view.autofill.AutofillId;
import android.view.translation.TranslationSpec;
import android.view.translation.UiTranslationSpec;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentTransaction;
import com.android.internal.app.IVoiceInteractor;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface IApplicationThread extends IInterface {

    public static class Default implements IApplicationThread {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(String str) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(String str) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void bindApplication(String str, ApplicationInfo applicationInfo, String str2, String str3, boolean z, ProviderInfoList providerInfoList, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str4, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] jArr, long[] jArr2, SharedMemory sharedMemory, FileDescriptor fileDescriptor, long j, long j2, boolean z6) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void clearIdsTrainingData(boolean z) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean z, boolean z2, boolean z3, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void getCurrentResourceCacheMax(IHwuiCallback iHwuiCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void getCurrentResourceCacheUsage(IHwuiCallback iHwuiCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void getExecutableMethodFileOffsets(MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void getProfileLength(String str) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void getResourceCacheLimit(IHwuiCallback iHwuiCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(ComponentName componentName, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, ApplicationInfo applicationInfo) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] bArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(ContentProviderHolder contentProviderHolder, String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void relaunchActivityIfWebViewAttached(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtrasFromCapture(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void runIsolatedEntryPoint(String str, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i, long j) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(String str, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleExit() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void schedulePing(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, String str2) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleReceiverList(List<ReceiverInfo> list) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleStopService(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleSuicide() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer iTaskFragmentOrganizer, TaskFragmentTransaction taskFragmentTransaction) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutService(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutServiceForType(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setFlingerFlag(String str) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setHttpProxyInfo(ProxyInfoWrapper proxyInfoWrapper) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long j) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setViewVisibleFlag(int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimePrefs(int i) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) throws RemoteException {
        }
    }

    void attachAgent(String str) throws RemoteException;

    void attachStartupAgents(String str) throws RemoteException;

    void bindApplication(String str, ApplicationInfo applicationInfo, String str2, String str3, boolean z, ProviderInfoList providerInfoList, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str4, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] jArr, long[] jArr2, SharedMemory sharedMemory, FileDescriptor fileDescriptor, long j, long j2, boolean z6) throws RemoteException;

    void clearDnsCache() throws RemoteException;

    void clearIdsTrainingData(boolean z) throws RemoteException;

    void dispatchPackageBroadcast(int i, String[] strArr) throws RemoteException;

    void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) throws RemoteException;

    void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpHeap(boolean z, boolean z2, boolean z3, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String[] strArr) throws RemoteException;

    void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) throws RemoteException;

    void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException;

    void dumpResources(ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException;

    void getCurrentResourceCacheMax(IHwuiCallback iHwuiCallback) throws RemoteException;

    void getCurrentResourceCacheUsage(IHwuiCallback iHwuiCallback) throws RemoteException;

    void getExecutableMethodFileOffsets(MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException;

    void getProfileLength(String str) throws RemoteException;

    void getResourceCacheLimit(IHwuiCallback iHwuiCallback) throws RemoteException;

    void handleTrustStorageUpdate() throws RemoteException;

    void instrumentWithoutRestart(ComponentName componentName, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, ApplicationInfo applicationInfo) throws RemoteException;

    void notifyCleartextNetwork(byte[] bArr) throws RemoteException;

    void notifyContentProviderPublishStatus(ContentProviderHolder contentProviderHolder, String str, int i, boolean z) throws RemoteException;

    void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void processInBackground() throws RemoteException;

    void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) throws RemoteException;

    void relaunchActivityIfWebViewAttached(IBinder iBinder) throws RemoteException;

    void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) throws RemoteException;

    void requestAssistContextExtrasFromCapture(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, boolean z) throws RemoteException;

    void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void runIsolatedEntryPoint(String str, String[] strArr) throws RemoteException;

    void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException;

    void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i, long j) throws RemoteException;

    void scheduleCrash(String str, int i, Bundle bundle) throws RemoteException;

    void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, int i, int i2, int i3) throws RemoteException;

    void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException;

    void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, int i) throws RemoteException;

    void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException;

    void scheduleExit() throws RemoteException;

    void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException;

    void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException;

    void scheduleLowMemory() throws RemoteException;

    void scheduleOnNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws RemoteException;

    void schedulePing(RemoteCallback remoteCallback) throws RemoteException;

    void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, String str2) throws RemoteException;

    void scheduleReceiverList(List<ReceiverInfo> list) throws RemoteException;

    void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2) throws RemoteException;

    void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException;

    void scheduleStopService(IBinder iBinder) throws RemoteException;

    void scheduleSuicide() throws RemoteException;

    void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer iTaskFragmentOrganizer, TaskFragmentTransaction taskFragmentTransaction) throws RemoteException;

    void scheduleTimeoutService(IBinder iBinder, int i) throws RemoteException;

    void scheduleTimeoutServiceForType(IBinder iBinder, int i, int i2) throws RemoteException;

    void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException;

    void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) throws RemoteException;

    void scheduleTrimMemory(int i) throws RemoteException;

    void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException;

    void setCoreSettings(Bundle bundle) throws RemoteException;

    void setFlingerFlag(String str) throws RemoteException;

    void setHttpProxyInfo(ProxyInfoWrapper proxyInfoWrapper) throws RemoteException;

    void setNetworkBlockSeq(long j) throws RemoteException;

    void setProcessState(int i) throws RemoteException;

    void setResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException;

    void setSchedulingGroup(int i) throws RemoteException;

    void setViewVisibleFlag(int i) throws RemoteException;

    void startBinderTracking() throws RemoteException;

    void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) throws RemoteException;

    void unstableProviderDied(IBinder iBinder) throws RemoteException;

    void updateHttpProxy() throws RemoteException;

    void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) throws RemoteException;

    void updateTimePrefs(int i) throws RemoteException;

    void updateTimeZone() throws RemoteException;

    void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) throws RemoteException;

    public static class Delegator extends Stub {
        IApplicationThread mImpl;

        public Delegator(IApplicationThread iApplicationThread) {
            this.mImpl = iApplicationThread;
        }

        @Override // android.app.IApplicationThread
        public void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, String str2) throws RemoteException {
            this.mImpl.scheduleReceiver(intent, activityInfo, compatibilityInfo, i, str, bundle, z, z2, i2, i3, i4, str2);
        }

        @Override // android.app.IApplicationThread
        public void scheduleReceiverList(List<ReceiverInfo> list) throws RemoteException {
            this.mImpl.scheduleReceiverList(list);
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException {
            this.mImpl.scheduleCreateService(iBinder, serviceInfo, compatibilityInfo, i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleStopService(IBinder iBinder) throws RemoteException {
            this.mImpl.scheduleStopService(iBinder);
        }

        @Override // android.app.IApplicationThread
        public void bindApplication(String str, ApplicationInfo applicationInfo, String str2, String str3, boolean z, ProviderInfoList providerInfoList, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str4, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] jArr, long[] jArr2, SharedMemory sharedMemory, FileDescriptor fileDescriptor, long j, long j2, boolean z6) throws RemoteException {
            this.mImpl.bindApplication(str, applicationInfo, str2, str3, z, providerInfoList, componentName, profilerInfo, bundle, iInstrumentationWatcher, iUiAutomationConnection, i, z2, z3, z4, z5, configuration, compatibilityInfo, map, bundle2, str4, autofillOptions, contentCaptureOptions, jArr, jArr2, sharedMemory, fileDescriptor, j, j2, z6);
        }

        @Override // android.app.IApplicationThread
        public void runIsolatedEntryPoint(String str, String[] strArr) throws RemoteException {
            this.mImpl.runIsolatedEntryPoint(str, strArr);
        }

        @Override // android.app.IApplicationThread
        public void scheduleExit() throws RemoteException {
            this.mImpl.scheduleExit();
        }

        @Override // android.app.IApplicationThread
        public void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException {
            this.mImpl.scheduleServiceArgs(iBinder, parceledListSlice);
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() throws RemoteException {
            this.mImpl.updateTimeZone();
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() throws RemoteException {
            this.mImpl.processInBackground();
        }

        @Override // android.app.IApplicationThread
        public void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i, long j) throws RemoteException {
            this.mImpl.scheduleBindService(iBinder, intent, z, i, j);
        }

        @Override // android.app.IApplicationThread
        public void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException {
            this.mImpl.scheduleUnbindService(iBinder, intent);
        }

        @Override // android.app.IApplicationThread
        public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException {
            this.mImpl.dumpService(parcelFileDescriptor, iBinder, strArr);
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2) throws RemoteException {
            this.mImpl.scheduleRegisteredReceiver(iIntentReceiver, intent, i, str, bundle, z, z2, z3, i2, i3, i4, str2);
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() throws RemoteException {
            this.mImpl.scheduleLowMemory();
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) throws RemoteException {
            this.mImpl.profilerControl(z, profilerInfo, i);
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int i) throws RemoteException {
            this.mImpl.setSchedulingGroup(i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, int i, int i2, int i3) throws RemoteException {
            this.mImpl.scheduleCreateBackupAgent(applicationInfo, i, i2, i3);
        }

        @Override // android.app.IApplicationThread
        public void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, int i) throws RemoteException {
            this.mImpl.scheduleDestroyBackupAgent(applicationInfo, i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws RemoteException {
            this.mImpl.scheduleOnNewSceneTransitionInfo(iBinder, sceneTransitionInfo);
        }

        @Override // android.app.IApplicationThread
        public void scheduleSuicide() throws RemoteException {
            this.mImpl.scheduleSuicide();
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int i, String[] strArr) throws RemoteException {
            this.mImpl.dispatchPackageBroadcast(i, strArr);
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(String str, int i, Bundle bundle) throws RemoteException {
            this.mImpl.scheduleCrash(str, i, bundle);
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean z, boolean z2, boolean z3, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
            this.mImpl.dumpHeap(z, z2, z3, str, str2, parcelFileDescriptor, remoteCallback);
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) throws RemoteException {
            this.mImpl.dumpActivity(parcelFileDescriptor, iBinder, str, strArr);
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
            this.mImpl.dumpResources(parcelFileDescriptor, remoteCallback);
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() throws RemoteException {
            this.mImpl.clearDnsCache();
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() throws RemoteException {
            this.mImpl.updateHttpProxy();
        }

        @Override // android.app.IApplicationThread
        public void setHttpProxyInfo(ProxyInfoWrapper proxyInfoWrapper) throws RemoteException {
            this.mImpl.setHttpProxyInfo(proxyInfoWrapper);
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(Bundle bundle) throws RemoteException {
            this.mImpl.setCoreSettings(bundle);
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) throws RemoteException {
            this.mImpl.updatePackageCompatibilityInfo(str, compatibilityInfo);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int i) throws RemoteException {
            this.mImpl.scheduleTrimMemory(i);
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String[] strArr) throws RemoteException {
            this.mImpl.dumpMemInfo(parcelFileDescriptor, memoryInfo, z, z2, z3, z4, z5, z6, strArr);
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) throws RemoteException {
            this.mImpl.dumpMemInfoProto(parcelFileDescriptor, memoryInfo, z, z2, z3, z4, strArr);
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
            this.mImpl.dumpGfxInfo(parcelFileDescriptor, strArr);
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
            this.mImpl.dumpCacheInfo(parcelFileDescriptor, strArr);
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException {
            this.mImpl.dumpProvider(parcelFileDescriptor, iBinder, strArr);
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
            this.mImpl.dumpDbInfo(parcelFileDescriptor, strArr);
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(IBinder iBinder) throws RemoteException {
            this.mImpl.unstableProviderDied(iBinder);
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) throws RemoteException {
            this.mImpl.requestAssistContextExtras(iBinder, iBinder2, i, i2, i3);
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtrasFromCapture(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, boolean z) throws RemoteException {
            this.mImpl.requestAssistContextExtrasFromCapture(iBinder, iBinder2, i, i2, i3, z);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) throws RemoteException {
            this.mImpl.scheduleTranslucentConversionComplete(iBinder, z);
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int i) throws RemoteException {
            this.mImpl.setProcessState(i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException {
            this.mImpl.scheduleInstallProvider(providerInfo);
        }

        @Override // android.app.IApplicationThread
        public void updateTimePrefs(int i) throws RemoteException {
            this.mImpl.updateTimePrefs(i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException {
            this.mImpl.scheduleEnterAnimationComplete(iBinder);
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] bArr) throws RemoteException {
            this.mImpl.notifyCleartextNetwork(bArr);
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() throws RemoteException {
            this.mImpl.startBinderTracking();
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            this.mImpl.stopBinderTrackingAndDump(parcelFileDescriptor);
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) throws RemoteException {
            this.mImpl.stopBinderTrackingAndDumpSystemServer(parcelFileDescriptor, str, str2, i, i2);
        }

        @Override // android.app.IApplicationThread
        public void setResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
            this.mImpl.setResourceCacheLimit(i, iHwuiCallback);
        }

        @Override // android.app.IApplicationThread
        public void getResourceCacheLimit(IHwuiCallback iHwuiCallback) throws RemoteException {
            this.mImpl.getResourceCacheLimit(iHwuiCallback);
        }

        @Override // android.app.IApplicationThread
        public void getCurrentResourceCacheUsage(IHwuiCallback iHwuiCallback) throws RemoteException {
            this.mImpl.getCurrentResourceCacheUsage(iHwuiCallback);
        }

        @Override // android.app.IApplicationThread
        public void getCurrentResourceCacheMax(IHwuiCallback iHwuiCallback) throws RemoteException {
            this.mImpl.getCurrentResourceCacheMax(iHwuiCallback);
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
            this.mImpl.scheduleLocalVoiceInteractionStarted(iBinder, iVoiceInteractor);
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() throws RemoteException {
            this.mImpl.handleTrustStorageUpdate();
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(String str) throws RemoteException {
            this.mImpl.attachAgent(str);
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(String str) throws RemoteException {
            this.mImpl.attachStartupAgents(str);
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException {
            this.mImpl.scheduleApplicationInfoChanged(applicationInfo);
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long j) throws RemoteException {
            this.mImpl.setNetworkBlockSeq(j);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
            this.mImpl.scheduleTransaction(clientTransaction);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer iTaskFragmentOrganizer, TaskFragmentTransaction taskFragmentTransaction) throws RemoteException {
            this.mImpl.scheduleTaskFragmentTransaction(iTaskFragmentOrganizer, taskFragmentTransaction);
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
            this.mImpl.requestDirectActions(iBinder, iVoiceInteractor, remoteCallback, remoteCallback2);
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
            this.mImpl.performDirectAction(iBinder, str, bundle, remoteCallback, remoteCallback2);
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(ContentProviderHolder contentProviderHolder, String str, int i, boolean z) throws RemoteException {
            this.mImpl.notifyContentProviderPublishStatus(contentProviderHolder, str, i, z);
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(ComponentName componentName, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, ApplicationInfo applicationInfo) throws RemoteException {
            this.mImpl.instrumentWithoutRestart(componentName, bundle, iInstrumentationWatcher, iUiAutomationConnection, applicationInfo);
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) throws RemoteException {
            this.mImpl.updateUiTranslationState(iBinder, i, translationSpec, translationSpec2, list, uiTranslationSpec);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutService(IBinder iBinder, int i) throws RemoteException {
            this.mImpl.scheduleTimeoutService(iBinder, i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutServiceForType(IBinder iBinder, int i, int i2) throws RemoteException {
            this.mImpl.scheduleTimeoutServiceForType(iBinder, i, i2);
        }

        @Override // android.app.IApplicationThread
        public void schedulePing(RemoteCallback remoteCallback) throws RemoteException {
            this.mImpl.schedulePing(remoteCallback);
        }

        @Override // android.app.IApplicationThread
        public void getExecutableMethodFileOffsets(MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException {
            this.mImpl.getExecutableMethodFileOffsets(methodDescriptor, iOffsetCallback);
        }

        @Override // android.app.IApplicationThread
        public void getProfileLength(String str) throws RemoteException {
            this.mImpl.getProfileLength(str);
        }

        @Override // android.app.IApplicationThread
        public void setFlingerFlag(String str) throws RemoteException {
            this.mImpl.setFlingerFlag(str);
        }

        @Override // android.app.IApplicationThread
        public void setViewVisibleFlag(int i) throws RemoteException {
            this.mImpl.setViewVisibleFlag(i);
        }

        @Override // android.app.IApplicationThread
        public void clearIdsTrainingData(boolean z) throws RemoteException {
            this.mImpl.clearIdsTrainingData(z);
        }

        @Override // android.app.IApplicationThread
        public void relaunchActivityIfWebViewAttached(IBinder iBinder) throws RemoteException {
            this.mImpl.relaunchActivityIfWebViewAttached(iBinder);
        }
    }

    public static abstract class Stub extends Binder implements IApplicationThread {
        public static final String DESCRIPTOR = "android.app.IApplicationThread";
        static final int TRANSACTION_attachAgent = 57;
        static final int TRANSACTION_attachStartupAgents = 58;
        static final int TRANSACTION_bindApplication = 5;
        static final int TRANSACTION_clearDnsCache = 27;
        static final int TRANSACTION_clearIdsTrainingData = 75;
        static final int TRANSACTION_dispatchPackageBroadcast = 22;
        static final int TRANSACTION_dumpActivity = 25;
        static final int TRANSACTION_dumpCacheInfo = 36;
        static final int TRANSACTION_dumpDbInfo = 38;
        static final int TRANSACTION_dumpGfxInfo = 35;
        static final int TRANSACTION_dumpHeap = 24;
        static final int TRANSACTION_dumpMemInfo = 33;
        static final int TRANSACTION_dumpMemInfoProto = 34;
        static final int TRANSACTION_dumpProvider = 37;
        static final int TRANSACTION_dumpResources = 26;
        static final int TRANSACTION_dumpService = 13;
        static final int TRANSACTION_getCurrentResourceCacheMax = 54;
        static final int TRANSACTION_getCurrentResourceCacheUsage = 53;
        static final int TRANSACTION_getExecutableMethodFileOffsets = 71;
        static final int TRANSACTION_getProfileLength = 72;
        static final int TRANSACTION_getResourceCacheLimit = 52;
        static final int TRANSACTION_handleTrustStorageUpdate = 56;
        static final int TRANSACTION_instrumentWithoutRestart = 66;
        static final int TRANSACTION_notifyCleartextNetwork = 47;
        static final int TRANSACTION_notifyContentProviderPublishStatus = 65;
        static final int TRANSACTION_performDirectAction = 64;
        static final int TRANSACTION_processInBackground = 10;
        static final int TRANSACTION_profilerControl = 16;
        static final int TRANSACTION_relaunchActivityIfWebViewAttached = 76;
        static final int TRANSACTION_requestAssistContextExtras = 40;
        static final int TRANSACTION_requestAssistContextExtrasFromCapture = 41;
        static final int TRANSACTION_requestDirectActions = 63;
        static final int TRANSACTION_runIsolatedEntryPoint = 6;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 59;
        static final int TRANSACTION_scheduleBindService = 11;
        static final int TRANSACTION_scheduleCrash = 23;
        static final int TRANSACTION_scheduleCreateBackupAgent = 18;
        static final int TRANSACTION_scheduleCreateService = 3;
        static final int TRANSACTION_scheduleDestroyBackupAgent = 19;
        static final int TRANSACTION_scheduleEnterAnimationComplete = 46;
        static final int TRANSACTION_scheduleExit = 7;
        static final int TRANSACTION_scheduleInstallProvider = 44;
        static final int TRANSACTION_scheduleLocalVoiceInteractionStarted = 55;
        static final int TRANSACTION_scheduleLowMemory = 15;
        static final int TRANSACTION_scheduleOnNewSceneTransitionInfo = 20;
        static final int TRANSACTION_schedulePing = 70;
        static final int TRANSACTION_scheduleReceiver = 1;
        static final int TRANSACTION_scheduleReceiverList = 2;
        static final int TRANSACTION_scheduleRegisteredReceiver = 14;
        static final int TRANSACTION_scheduleServiceArgs = 8;
        static final int TRANSACTION_scheduleStopService = 4;
        static final int TRANSACTION_scheduleSuicide = 21;
        static final int TRANSACTION_scheduleTaskFragmentTransaction = 62;
        static final int TRANSACTION_scheduleTimeoutService = 68;
        static final int TRANSACTION_scheduleTimeoutServiceForType = 69;
        static final int TRANSACTION_scheduleTransaction = 61;
        static final int TRANSACTION_scheduleTranslucentConversionComplete = 42;
        static final int TRANSACTION_scheduleTrimMemory = 32;
        static final int TRANSACTION_scheduleUnbindService = 12;
        static final int TRANSACTION_setCoreSettings = 30;
        static final int TRANSACTION_setFlingerFlag = 73;
        static final int TRANSACTION_setHttpProxyInfo = 29;
        static final int TRANSACTION_setNetworkBlockSeq = 60;
        static final int TRANSACTION_setProcessState = 43;
        static final int TRANSACTION_setResourceCacheLimit = 51;
        static final int TRANSACTION_setSchedulingGroup = 17;
        static final int TRANSACTION_setViewVisibleFlag = 74;
        static final int TRANSACTION_startBinderTracking = 48;
        static final int TRANSACTION_stopBinderTrackingAndDump = 49;
        static final int TRANSACTION_stopBinderTrackingAndDumpSystemServer = 50;
        static final int TRANSACTION_unstableProviderDied = 39;
        static final int TRANSACTION_updateHttpProxy = 28;
        static final int TRANSACTION_updatePackageCompatibilityInfo = 31;
        static final int TRANSACTION_updateTimePrefs = 45;
        static final int TRANSACTION_updateTimeZone = 9;
        static final int TRANSACTION_updateUiTranslationState = 67;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 75;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IApplicationThread asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IApplicationThread)) {
                return (IApplicationThread) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "scheduleReceiver";
                case 2:
                    return "scheduleReceiverList";
                case 3:
                    return "scheduleCreateService";
                case 4:
                    return "scheduleStopService";
                case 5:
                    return "bindApplication";
                case 6:
                    return "runIsolatedEntryPoint";
                case 7:
                    return "scheduleExit";
                case 8:
                    return "scheduleServiceArgs";
                case 9:
                    return "updateTimeZone";
                case 10:
                    return "processInBackground";
                case 11:
                    return "scheduleBindService";
                case 12:
                    return "scheduleUnbindService";
                case 13:
                    return "dumpService";
                case 14:
                    return "scheduleRegisteredReceiver";
                case 15:
                    return "scheduleLowMemory";
                case 16:
                    return "profilerControl";
                case 17:
                    return "setSchedulingGroup";
                case 18:
                    return "scheduleCreateBackupAgent";
                case 19:
                    return "scheduleDestroyBackupAgent";
                case 20:
                    return "scheduleOnNewSceneTransitionInfo";
                case 21:
                    return "scheduleSuicide";
                case 22:
                    return "dispatchPackageBroadcast";
                case 23:
                    return "scheduleCrash";
                case 24:
                    return "dumpHeap";
                case 25:
                    return "dumpActivity";
                case 26:
                    return "dumpResources";
                case 27:
                    return "clearDnsCache";
                case 28:
                    return "updateHttpProxy";
                case 29:
                    return "setHttpProxyInfo";
                case 30:
                    return "setCoreSettings";
                case 31:
                    return "updatePackageCompatibilityInfo";
                case 32:
                    return "scheduleTrimMemory";
                case 33:
                    return "dumpMemInfo";
                case 34:
                    return "dumpMemInfoProto";
                case 35:
                    return "dumpGfxInfo";
                case 36:
                    return "dumpCacheInfo";
                case 37:
                    return "dumpProvider";
                case 38:
                    return "dumpDbInfo";
                case 39:
                    return "unstableProviderDied";
                case 40:
                    return "requestAssistContextExtras";
                case 41:
                    return "requestAssistContextExtrasFromCapture";
                case 42:
                    return "scheduleTranslucentConversionComplete";
                case 43:
                    return "setProcessState";
                case 44:
                    return "scheduleInstallProvider";
                case 45:
                    return "updateTimePrefs";
                case 46:
                    return "scheduleEnterAnimationComplete";
                case 47:
                    return "notifyCleartextNetwork";
                case 48:
                    return "startBinderTracking";
                case 49:
                    return "stopBinderTrackingAndDump";
                case 50:
                    return "stopBinderTrackingAndDumpSystemServer";
                case 51:
                    return "setResourceCacheLimit";
                case 52:
                    return "getResourceCacheLimit";
                case 53:
                    return "getCurrentResourceCacheUsage";
                case 54:
                    return "getCurrentResourceCacheMax";
                case 55:
                    return "scheduleLocalVoiceInteractionStarted";
                case 56:
                    return "handleTrustStorageUpdate";
                case 57:
                    return "attachAgent";
                case 58:
                    return "attachStartupAgents";
                case 59:
                    return "scheduleApplicationInfoChanged";
                case 60:
                    return "setNetworkBlockSeq";
                case 61:
                    return "scheduleTransaction";
                case 62:
                    return "scheduleTaskFragmentTransaction";
                case 63:
                    return "requestDirectActions";
                case 64:
                    return "performDirectAction";
                case 65:
                    return "notifyContentProviderPublishStatus";
                case 66:
                    return "instrumentWithoutRestart";
                case 67:
                    return "updateUiTranslationState";
                case 68:
                    return "scheduleTimeoutService";
                case 69:
                    return "scheduleTimeoutServiceForType";
                case 70:
                    return "schedulePing";
                case 71:
                    return "getExecutableMethodFileOffsets";
                case 72:
                    return "getProfileLength";
                case 73:
                    return "setFlingerFlag";
                case 74:
                    return "setViewVisibleFlag";
                case 75:
                    return "clearIdsTrainingData";
                case 76:
                    return "relaunchActivityIfWebViewAttached";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                    CompatibilityInfo compatibilityInfo = (CompatibilityInfo) parcel.readTypedObject(CompatibilityInfo.CREATOR);
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    scheduleReceiver(intent, activityInfo, compatibilityInfo, i3, string, bundle, z, z2, i4, i5, i6, string2);
                    return true;
                case 2:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ReceiverInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleReceiverList(arrayListCreateTypedArrayList);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    ServiceInfo serviceInfo = (ServiceInfo) parcel.readTypedObject(ServiceInfo.CREATOR);
                    CompatibilityInfo compatibilityInfo2 = (CompatibilityInfo) parcel.readTypedObject(CompatibilityInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleCreateService(strongBinder, serviceInfo, compatibilityInfo2, i7);
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    scheduleStopService(strongBinder2);
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    ProviderInfoList providerInfoList = (ProviderInfoList) parcel.readTypedObject(ProviderInfoList.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IInstrumentationWatcher iInstrumentationWatcherAsInterface = IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    IUiAutomationConnection iUiAutomationConnectionAsInterface = IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i8 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    boolean z6 = parcel.readBoolean();
                    boolean z7 = parcel.readBoolean();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    CompatibilityInfo compatibilityInfo3 = (CompatibilityInfo) parcel.readTypedObject(CompatibilityInfo.CREATOR);
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string6 = parcel.readString();
                    AutofillOptions autofillOptions = (AutofillOptions) parcel.readTypedObject(AutofillOptions.CREATOR);
                    ContentCaptureOptions contentCaptureOptions = (ContentCaptureOptions) parcel.readTypedObject(ContentCaptureOptions.CREATOR);
                    long[] jArrCreateLongArray = parcel.createLongArray();
                    long[] jArrCreateLongArray2 = parcel.createLongArray();
                    SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bindApplication(string3, applicationInfo, string4, string5, z3, providerInfoList, componentName, profilerInfo, bundle2, iInstrumentationWatcherAsInterface, iUiAutomationConnectionAsInterface, i8, z4, z5, z6, z7, configuration, compatibilityInfo3, hashMap, bundle3, string6, autofillOptions, contentCaptureOptions, jArrCreateLongArray, jArrCreateLongArray2, sharedMemory, rawFileDescriptor, j, j2, z8);
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    runIsolatedEntryPoint(string7, strArrCreateStringArray);
                    break;
                case 7:
                    scheduleExit();
                    break;
                case 8:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleServiceArgs(strongBinder3, parceledListSlice);
                    break;
                case 9:
                    updateTimeZone();
                    break;
                case 10:
                    processInBackground();
                    break;
                case 11:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    scheduleBindService(strongBinder4, intent2, z9, i9, j3);
                    break;
                case 12:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleUnbindService(strongBinder5, intent3);
                    break;
                case 13:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpService(parcelFileDescriptor, strongBinder6, strArrCreateStringArray2);
                    break;
                case 14:
                    IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i10 = parcel.readInt();
                    String string8 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    scheduleRegisteredReceiver(iIntentReceiverAsInterface, intent4, i10, string8, bundle4, z10, z11, z12, i11, i12, i13, string9);
                    break;
                case 15:
                    scheduleLowMemory();
                    break;
                case 16:
                    boolean z13 = parcel.readBoolean();
                    ProfilerInfo profilerInfo2 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    profilerControl(z13, profilerInfo2, i14);
                    break;
                case 17:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSchedulingGroup(i15);
                    break;
                case 18:
                    ApplicationInfo applicationInfo2 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleCreateBackupAgent(applicationInfo2, i16, i17, i18);
                    break;
                case 19:
                    ApplicationInfo applicationInfo3 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleDestroyBackupAgent(applicationInfo3, i19);
                    break;
                case 20:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    ActivityOptions.SceneTransitionInfo sceneTransitionInfo = (ActivityOptions.SceneTransitionInfo) parcel.readTypedObject(ActivityOptions.SceneTransitionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleOnNewSceneTransitionInfo(strongBinder7, sceneTransitionInfo);
                    break;
                case 21:
                    scheduleSuicide();
                    break;
                case 22:
                    int i20 = parcel.readInt();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dispatchPackageBroadcast(i20, strArrCreateStringArray3);
                    break;
                case 23:
                    String string10 = parcel.readString();
                    int i21 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleCrash(string10, i21, bundle5);
                    break;
                case 24:
                    boolean z14 = parcel.readBoolean();
                    boolean z15 = parcel.readBoolean();
                    boolean z16 = parcel.readBoolean();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpHeap(z14, z15, z16, string11, string12, parcelFileDescriptor2, remoteCallback);
                    break;
                case 25:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    String string13 = parcel.readString();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpActivity(parcelFileDescriptor3, strongBinder8, string13, strArrCreateStringArray4);
                    break;
                case 26:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpResources(parcelFileDescriptor4, remoteCallback2);
                    break;
                case 27:
                    clearDnsCache();
                    break;
                case 28:
                    updateHttpProxy();
                    break;
                case 29:
                    ProxyInfoWrapper proxyInfoWrapper = (ProxyInfoWrapper) parcel.readTypedObject(ProxyInfoWrapper.CREATOR);
                    parcel.enforceNoDataAvail();
                    setHttpProxyInfo(proxyInfoWrapper);
                    break;
                case 30:
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCoreSettings(bundle6);
                    break;
                case 31:
                    String string14 = parcel.readString();
                    CompatibilityInfo compatibilityInfo4 = (CompatibilityInfo) parcel.readTypedObject(CompatibilityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePackageCompatibilityInfo(string14, compatibilityInfo4);
                    break;
                case 32:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleTrimMemory(i22);
                    break;
                case 33:
                    ParcelFileDescriptor parcelFileDescriptor5 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Debug.MemoryInfo memoryInfo = (Debug.MemoryInfo) parcel.readTypedObject(Debug.MemoryInfo.CREATOR);
                    boolean z17 = parcel.readBoolean();
                    boolean z18 = parcel.readBoolean();
                    boolean z19 = parcel.readBoolean();
                    boolean z20 = parcel.readBoolean();
                    boolean z21 = parcel.readBoolean();
                    boolean z22 = parcel.readBoolean();
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpMemInfo(parcelFileDescriptor5, memoryInfo, z17, z18, z19, z20, z21, z22, strArrCreateStringArray5);
                    break;
                case 34:
                    ParcelFileDescriptor parcelFileDescriptor6 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Debug.MemoryInfo memoryInfo2 = (Debug.MemoryInfo) parcel.readTypedObject(Debug.MemoryInfo.CREATOR);
                    boolean z23 = parcel.readBoolean();
                    boolean z24 = parcel.readBoolean();
                    boolean z25 = parcel.readBoolean();
                    boolean z26 = parcel.readBoolean();
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpMemInfoProto(parcelFileDescriptor6, memoryInfo2, z23, z24, z25, z26, strArrCreateStringArray6);
                    break;
                case 35:
                    ParcelFileDescriptor parcelFileDescriptor7 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpGfxInfo(parcelFileDescriptor7, strArrCreateStringArray7);
                    break;
                case 36:
                    ParcelFileDescriptor parcelFileDescriptor8 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] strArrCreateStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpCacheInfo(parcelFileDescriptor8, strArrCreateStringArray8);
                    break;
                case 37:
                    ParcelFileDescriptor parcelFileDescriptor9 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    String[] strArrCreateStringArray9 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpProvider(parcelFileDescriptor9, strongBinder9, strArrCreateStringArray9);
                    break;
                case 38:
                    ParcelFileDescriptor parcelFileDescriptor10 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] strArrCreateStringArray10 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    dumpDbInfo(parcelFileDescriptor10, strArrCreateStringArray10);
                    break;
                case 39:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unstableProviderDied(strongBinder10);
                    break;
                case 40:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAssistContextExtras(strongBinder11, strongBinder12, i23, i24, i25);
                    break;
                case 41:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestAssistContextExtrasFromCapture(strongBinder13, strongBinder14, i26, i27, i28, z27);
                    break;
                case 42:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    scheduleTranslucentConversionComplete(strongBinder15, z28);
                    break;
                case 43:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProcessState(i29);
                    break;
                case 44:
                    ProviderInfo providerInfo = (ProviderInfo) parcel.readTypedObject(ProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleInstallProvider(providerInfo);
                    break;
                case 45:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTimePrefs(i30);
                    break;
                case 46:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    scheduleEnterAnimationComplete(strongBinder16);
                    break;
                case 47:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCleartextNetwork(bArrCreateByteArray);
                    break;
                case 48:
                    startBinderTracking();
                    break;
                case 49:
                    ParcelFileDescriptor parcelFileDescriptor11 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopBinderTrackingAndDump(parcelFileDescriptor11);
                    break;
                case 50:
                    ParcelFileDescriptor parcelFileDescriptor12 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopBinderTrackingAndDumpSystemServer(parcelFileDescriptor12, string15, string16, i31, i32);
                    break;
                case 51:
                    int i33 = parcel.readInt();
                    IHwuiCallback iHwuiCallbackAsInterface = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResourceCacheLimit(i33, iHwuiCallbackAsInterface);
                    break;
                case 52:
                    IHwuiCallback iHwuiCallbackAsInterface2 = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getResourceCacheLimit(iHwuiCallbackAsInterface2);
                    break;
                case 53:
                    IHwuiCallback iHwuiCallbackAsInterface3 = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getCurrentResourceCacheUsage(iHwuiCallbackAsInterface3);
                    break;
                case 54:
                    IHwuiCallback iHwuiCallbackAsInterface4 = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getCurrentResourceCacheMax(iHwuiCallbackAsInterface4);
                    break;
                case 55:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    IVoiceInteractor iVoiceInteractorAsInterface = IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    scheduleLocalVoiceInteractionStarted(strongBinder17, iVoiceInteractorAsInterface);
                    break;
                case 56:
                    handleTrustStorageUpdate();
                    break;
                case 57:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    attachAgent(string17);
                    break;
                case 58:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    attachStartupAgents(string18);
                    break;
                case 59:
                    ApplicationInfo applicationInfo4 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(applicationInfo4);
                    break;
                case 60:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setNetworkBlockSeq(j4);
                    break;
                case 61:
                    ClientTransaction clientTransaction = (ClientTransaction) parcel.readTypedObject(ClientTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleTransaction(clientTransaction);
                    break;
                case 62:
                    ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    TaskFragmentTransaction taskFragmentTransaction = (TaskFragmentTransaction) parcel.readTypedObject(TaskFragmentTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    scheduleTaskFragmentTransaction(iTaskFragmentOrganizerAsInterface, taskFragmentTransaction);
                    break;
                case 63:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    IVoiceInteractor iVoiceInteractorAsInterface2 = IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDirectActions(strongBinder18, iVoiceInteractorAsInterface2, remoteCallback3, remoteCallback4);
                    break;
                case 64:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    String string19 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    RemoteCallback remoteCallback5 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback6 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    performDirectAction(strongBinder19, string19, bundle7, remoteCallback5, remoteCallback6);
                    break;
                case 65:
                    ContentProviderHolder contentProviderHolder = (ContentProviderHolder) parcel.readTypedObject(ContentProviderHolder.CREATOR);
                    String string20 = parcel.readString();
                    int i34 = parcel.readInt();
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyContentProviderPublishStatus(contentProviderHolder, string20, i34, z29);
                    break;
                case 66:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IInstrumentationWatcher iInstrumentationWatcherAsInterface2 = IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    IUiAutomationConnection iUiAutomationConnectionAsInterface2 = IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
                    ApplicationInfo applicationInfo5 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    instrumentWithoutRestart(componentName2, bundle8, iInstrumentationWatcherAsInterface2, iUiAutomationConnectionAsInterface2, applicationInfo5);
                    break;
                case 67:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    int i35 = parcel.readInt();
                    TranslationSpec translationSpec = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
                    TranslationSpec translationSpec2 = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AutofillId.CREATOR);
                    UiTranslationSpec uiTranslationSpec = (UiTranslationSpec) parcel.readTypedObject(UiTranslationSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateUiTranslationState(strongBinder20, i35, translationSpec, translationSpec2, arrayListCreateTypedArrayList2, uiTranslationSpec);
                    break;
                case 68:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleTimeoutService(strongBinder21, i36);
                    break;
                case 69:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleTimeoutServiceForType(strongBinder22, i37, i38);
                    break;
                case 70:
                    RemoteCallback remoteCallback7 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    schedulePing(remoteCallback7);
                    break;
                case 71:
                    MethodDescriptor methodDescriptor = (MethodDescriptor) parcel.readTypedObject(MethodDescriptor.CREATOR);
                    IOffsetCallback iOffsetCallbackAsInterface = IOffsetCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getExecutableMethodFileOffsets(methodDescriptor, iOffsetCallbackAsInterface);
                    break;
                case 72:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getProfileLength(string21);
                    break;
                case 73:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setFlingerFlag(string22);
                    break;
                case 74:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setViewVisibleFlag(i39);
                    break;
                case 75:
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearIdsTrainingData(z30);
                    break;
                case 76:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    relaunchActivityIfWebViewAttached(strongBinder23);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IApplicationThread {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IApplicationThread
            public void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(activityInfo, 0);
                    parcelObtain.writeTypedObject(compatibilityInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleReceiverList(List<ReceiverInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(serviceInfo, 0);
                    parcelObtain.writeTypedObject(compatibilityInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleStopService(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void bindApplication(String str, ApplicationInfo applicationInfo, String str2, String str3, boolean z, ProviderInfoList providerInfoList, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str4, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] jArr, long[] jArr2, SharedMemory sharedMemory, FileDescriptor fileDescriptor, long j, long j2, boolean z6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(providerInfoList, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iInstrumentationWatcher);
                    parcelObtain.writeStrongInterface(iUiAutomationConnection);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeTypedObject(configuration, 0);
                    parcelObtain.writeTypedObject(compatibilityInfo, 0);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeTypedObject(bundle2, 0);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(autofillOptions, 0);
                    parcelObtain.writeTypedObject(contentCaptureOptions, 0);
                    parcelObtain.writeLongArray(jArr);
                    parcelObtain.writeLongArray(jArr2);
                    parcelObtain.writeTypedObject(sharedMemory, 0);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeBoolean(z6);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void runIsolatedEntryPoint(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleExit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimeZone() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void processInBackground() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLowMemory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setSchedulingGroup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleOnNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(sceneTransitionInfo, 0);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleSuicide() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dispatchPackageBroadcast(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCrash(String str, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpHeap(boolean z, boolean z2, boolean z3, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpResources(ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void clearDnsCache() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateHttpProxy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setHttpProxyInfo(ProxyInfoWrapper proxyInfoWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(proxyInfoWrapper, 0);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setCoreSettings(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(compatibilityInfo, 0);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTrimMemory(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(memoryInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeBoolean(z6);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(memoryInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void unstableProviderDied(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(40, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestAssistContextExtrasFromCapture(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setProcessState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(providerInfo, 0);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimePrefs(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(46, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyCleartextNetwork(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(47, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void startBinderTracking() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(49, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(51, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void getResourceCacheLimit(IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(52, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void getCurrentResourceCacheUsage(IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(53, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void getCurrentResourceCacheMax(IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(54, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iVoiceInteractor);
                    this.mRemote.transact(55, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void handleTrustStorageUpdate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachAgent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachStartupAgents(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(58, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(59, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setNetworkBlockSeq(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(60, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientTransaction, 0);
                    this.mRemote.transact(61, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer iTaskFragmentOrganizer, TaskFragmentTransaction taskFragmentTransaction) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFragmentOrganizer);
                    parcelObtain.writeTypedObject(taskFragmentTransaction, 0);
                    this.mRemote.transact(62, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iVoiceInteractor);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(63, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(64, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyContentProviderPublishStatus(ContentProviderHolder contentProviderHolder, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contentProviderHolder, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void instrumentWithoutRestart(ComponentName componentName, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iInstrumentationWatcher);
                    parcelObtain.writeStrongInterface(iUiAutomationConnection);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(66, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(translationSpec, 0);
                    parcelObtain.writeTypedObject(translationSpec2, 0);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(uiTranslationSpec, 0);
                    this.mRemote.transact(67, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTimeoutService(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTimeoutServiceForType(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(69, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void schedulePing(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(70, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void getExecutableMethodFileOffsets(MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(methodDescriptor, 0);
                    parcelObtain.writeStrongInterface(iOffsetCallback);
                    this.mRemote.transact(71, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void getProfileLength(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setFlingerFlag(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(73, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setViewVisibleFlag(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void clearIdsTrainingData(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(75, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void relaunchActivityIfWebViewAttached(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(76, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
