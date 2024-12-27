package com.android.server.inputmethod;

import android.content.pm.PackageManagerInternal;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AccessibilityManagerInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ClientController {
    public final PackageManagerInternal mPackageManagerInternal;
    public final ArrayMap mClients = new ArrayMap();
    public final List mCallbacks = new ArrayList();

    public ClientController(PackageManagerInternal packageManagerInternal) {
        this.mPackageManagerInternal = packageManagerInternal;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.os.IBinder$DeathRecipient, com.android.server.inputmethod.ClientController$$ExternalSyntheticLambda0] */
    public final void addClient(final IInputMethodClientInvoker iInputMethodClientInvoker, IRemoteInputConnection iRemoteInputConnection, int i, int i2, int i3) {
        ?? r6 = new IBinder.DeathRecipient() { // from class: com.android.server.inputmethod.ClientController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                ClientController clientController = ClientController.this;
                IInputMethodClientInvoker iInputMethodClientInvoker2 = iInputMethodClientInvoker;
                clientController.getClass();
                synchronized (ImfLock.class) {
                    clientController.removeClientAsBinder(iInputMethodClientInvoker2.mTarget.asBinder());
                }
            }
        };
        int size = this.mClients.size();
        for (int i4 = 0; i4 < size; i4++) {
            ClientState clientState = (ClientState) this.mClients.valueAt(i4);
            if (clientState.mUid == i2 && clientState.mPid == i3 && clientState.mSelfReportedDisplayId == i) {
                throw new SecurityException(AmFmBandRange$$ExternalSyntheticOutline0.m(i, ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, "uid=", "/pid=", "/displayId="), " is already registered"));
            }
        }
        try {
            iInputMethodClientInvoker.mTarget.asBinder().linkToDeath(r6, 0);
            this.mClients.put(iInputMethodClientInvoker.mTarget.asBinder(), new ClientState(iInputMethodClientInvoker, iRemoteInputConnection, i2, i3, i, r6));
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void forAllClients(Consumer consumer) {
        for (int i = 0; i < this.mClients.size(); i++) {
            consumer.accept((ClientState) this.mClients.valueAt(i));
        }
    }

    public final ClientState getClient(IBinder iBinder) {
        return (ClientState) this.mClients.get(iBinder);
    }

    public boolean removeClient(IInputMethodClient iInputMethodClient) {
        return removeClientAsBinder(iInputMethodClient.asBinder());
    }

    public final boolean removeClientAsBinder(IBinder iBinder) {
        ClientState clientState = (ClientState) this.mClients.remove(iBinder);
        if (clientState == null) {
            return false;
        }
        iBinder.unlinkToDeath(clientState.mClientDeathRecipient, 0);
        for (int i = 0; i < ((ArrayList) this.mCallbacks).size(); i++) {
            InputMethodManagerService inputMethodManagerService = ((InputMethodManagerService$$ExternalSyntheticLambda2) ((ArrayList) this.mCallbacks).get(i)).f$0;
            inputMethodManagerService.getClass();
            Slog.d("InputMethodManagerService", "onClientRemoved");
            inputMethodManagerService.finishSessionLocked(clientState.mCurSession);
            clientState.mCurSession = null;
            clientState.mSessionRequested = false;
            InputMethodManagerService.clearClientSessionForAccessibilityLocked(clientState);
            if (inputMethodManagerService.mCurClient == clientState) {
                inputMethodManagerService.hideCurrentInputLocked(22, inputMethodManagerService.mImeBindingState.mFocusedWindow);
                if (inputMethodManagerService.mBoundToMethod) {
                    inputMethodManagerService.mBoundToMethod = false;
                    IInputMethodInvoker curMethodLocked = inputMethodManagerService.getCurMethodLocked();
                    if (curMethodLocked != null) {
                        try {
                            curMethodLocked.mTarget.unbindInput();
                        } catch (RemoteException e) {
                            IInputMethodInvoker.logRemoteException(e);
                        }
                        AccessibilityManagerInternal.get().unbindInput();
                    }
                }
                inputMethodManagerService.mBoundToAccessibility = false;
                inputMethodManagerService.mCurClient = null;
                if (inputMethodManagerService.mImeBindingState.mFocusedWindowClient == clientState) {
                    inputMethodManagerService.mImeBindingState = new ImeBindingState(null, 0, null, null);
                }
            }
        }
        return true;
    }

    public final boolean verifyClientAndPackageMatch(IInputMethodClient iInputMethodClient, String str) {
        ClientState clientState = (ClientState) this.mClients.get(iInputMethodClient.asBinder());
        if (clientState != null) {
            int i = clientState.mUid;
            return this.mPackageManagerInternal.isSameApp(i, UserHandle.getUserId(i), 0L, str);
        }
        throw new IllegalArgumentException("unknown client " + iInputMethodClient.asBinder());
    }
}
