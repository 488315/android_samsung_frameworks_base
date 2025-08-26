package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.IPacProxyInstalledListener;
import android.os.Binder;
import android.os.RemoteException;
import com.android.internal.util.FunctionalUtils;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class PacProxyManager {
    private final Context mContext;
    private final HashMap<PacProxyInstalledListener, PacProxyInstalledListenerProxy> mListenerMap = new HashMap<>();
    private final IPacProxyManager mService;

    public interface PacProxyInstalledListener {
        void onPacProxyInstalled(Network network, ProxyInfo proxyInfo);
    }

    public PacProxyManager(Context context, IPacProxyManager iPacProxyManager) {
        Objects.requireNonNull(iPacProxyManager, "missing IPacProxyManager");
        this.mContext = context;
        this.mService = iPacProxyManager;
    }

    public void addPacProxyInstalledListener(Executor executor, PacProxyInstalledListener pacProxyInstalledListener) {
        try {
            synchronized (this.mListenerMap) {
                PacProxyInstalledListenerProxy pacProxyInstalledListenerProxy = new PacProxyInstalledListenerProxy(this, executor, pacProxyInstalledListener);
                if (this.mListenerMap.putIfAbsent(pacProxyInstalledListener, pacProxyInstalledListenerProxy) != null) {
                    throw new IllegalStateException("Listener is already added.");
                }
                this.mService.addListener(pacProxyInstalledListenerProxy);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePacProxyInstalledListener(PacProxyInstalledListener pacProxyInstalledListener) {
        try {
            synchronized (this.mListenerMap) {
                PacProxyInstalledListenerProxy pacProxyInstalledListenerProxyRemove = this.mListenerMap.remove(pacProxyInstalledListener);
                if (pacProxyInstalledListenerProxyRemove == null) {
                    return;
                }
                this.mService.removeListener(pacProxyInstalledListenerProxyRemove);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCurrentProxyScriptUrl(ProxyInfo proxyInfo) {
        try {
            this.mService.setCurrentProxyScriptUrl(proxyInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public class PacProxyInstalledListenerProxy extends IPacProxyInstalledListener.Stub {
        private final Executor mExecutor;
        private final PacProxyInstalledListener mListener;

        PacProxyInstalledListenerProxy(PacProxyManager pacProxyManager, Executor executor, PacProxyInstalledListener pacProxyInstalledListener) {
            this.mExecutor = executor;
            this.mListener = pacProxyInstalledListener;
        }

        @Override // android.net.IPacProxyInstalledListener
        public void onPacProxyInstalled(final Network network, final ProxyInfo proxyInfo) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.net.PacProxyManager$PacProxyInstalledListenerProxy$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onPacProxyInstalled$1(network, proxyInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPacProxyInstalled$1(final Network network, final ProxyInfo proxyInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.net.PacProxyManager$PacProxyInstalledListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPacProxyInstalled$0(network, proxyInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPacProxyInstalled$0(Network network, ProxyInfo proxyInfo) {
            this.mListener.onPacProxyInstalled(network, proxyInfo);
        }
    }
}
