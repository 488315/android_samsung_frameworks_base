package com.android.server.accessibility;

import android.R;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.internal.util.FunctionalUtils;
import com.android.server.accessibility.AccessibilityManagerService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccessibilityManagerService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda8(AccessibilityManagerService accessibilityManagerService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = accessibilityManagerService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final AccessibilityManagerService accessibilityManagerService = this.f$0;
                final AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.f$1;
                accessibilityManagerService.getClass();
                final int i = 1;
                Consumer ignoreRemoteException = FunctionalUtils.ignoreRemoteException(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda59
                    public final void acceptOrThrow(Object obj) {
                        switch (i) {
                            case 0:
                                AccessibilityManagerService accessibilityManagerService2 = accessibilityManagerService;
                                AccessibilityUserState accessibilityUserState2 = accessibilityUserState;
                                AccessibilityManagerService.Client client = (AccessibilityManagerService.Client) obj;
                                synchronized (accessibilityManagerService2.mLock) {
                                    try {
                                        int computeRelevantEventTypesLocked = accessibilityManagerService2.computeRelevantEventTypesLocked(accessibilityUserState2, client);
                                        if (!accessibilityManagerService2.mProxyManager.isProxyedDeviceId(client.mDeviceId) && client.mLastSentRelevantEventTypes != computeRelevantEventTypesLocked) {
                                            client.mLastSentRelevantEventTypes = computeRelevantEventTypesLocked;
                                            client.mCallback.setRelevantEventTypes(computeRelevantEventTypesLocked);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                AccessibilityManagerService accessibilityManagerService3 = accessibilityManagerService;
                                AccessibilityUserState accessibilityUserState3 = accessibilityUserState;
                                AccessibilityManagerService.Client client2 = (AccessibilityManagerService.Client) obj;
                                if (accessibilityManagerService3.mProxyManager.isProxyedDeviceId(client2.mDeviceId)) {
                                    return;
                                }
                                client2.mCallback.setFocusAppearance(accessibilityUserState3.mFocusStrokeWidth, accessibilityUserState3.mFocusColor);
                                return;
                        }
                    }
                });
                accessibilityManagerService.mGlobalClients.broadcastForEachCookie(ignoreRemoteException);
                accessibilityUserState.mUserClients.broadcastForEachCookie(ignoreRemoteException);
                break;
            case 1:
                final AccessibilityManagerService accessibilityManagerService2 = this.f$0;
                final AccessibilityUserState accessibilityUserState2 = (AccessibilityUserState) this.f$1;
                accessibilityManagerService2.getClass();
                final int i2 = 0;
                Consumer ignoreRemoteException2 = FunctionalUtils.ignoreRemoteException(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda59
                    public final void acceptOrThrow(Object obj) {
                        switch (i2) {
                            case 0:
                                AccessibilityManagerService accessibilityManagerService22 = accessibilityManagerService2;
                                AccessibilityUserState accessibilityUserState22 = accessibilityUserState2;
                                AccessibilityManagerService.Client client = (AccessibilityManagerService.Client) obj;
                                synchronized (accessibilityManagerService22.mLock) {
                                    try {
                                        int computeRelevantEventTypesLocked = accessibilityManagerService22.computeRelevantEventTypesLocked(accessibilityUserState22, client);
                                        if (!accessibilityManagerService22.mProxyManager.isProxyedDeviceId(client.mDeviceId) && client.mLastSentRelevantEventTypes != computeRelevantEventTypesLocked) {
                                            client.mLastSentRelevantEventTypes = computeRelevantEventTypesLocked;
                                            client.mCallback.setRelevantEventTypes(computeRelevantEventTypesLocked);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                AccessibilityManagerService accessibilityManagerService3 = accessibilityManagerService2;
                                AccessibilityUserState accessibilityUserState3 = accessibilityUserState2;
                                AccessibilityManagerService.Client client2 = (AccessibilityManagerService.Client) obj;
                                if (accessibilityManagerService3.mProxyManager.isProxyedDeviceId(client2.mDeviceId)) {
                                    return;
                                }
                                client2.mCallback.setFocusAppearance(accessibilityUserState3.mFocusStrokeWidth, accessibilityUserState3.mFocusColor);
                                return;
                        }
                    }
                });
                accessibilityManagerService2.mGlobalClients.broadcastForEachCookie(ignoreRemoteException2);
                accessibilityUserState2.mUserClients.broadcastForEachCookie(ignoreRemoteException2);
                break;
            default:
                AccessibilityManagerService accessibilityManagerService3 = this.f$0;
                String str = (String) this.f$1;
                accessibilityManagerService3.getClass();
                Toast.makeText(new ContextThemeWrapper(accessibilityManagerService3.mContext, R.style.Theme.DeviceDefault.Light), str, 0).show();
                break;
        }
    }
}
