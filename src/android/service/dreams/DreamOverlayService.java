package android.service.dreams;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.dreams.IDreamOverlay;
import android.service.dreams.IDreamOverlayClient;
import android.util.Log;
import android.view.WindowManager;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class DreamOverlayService extends Service {
    private static final boolean DEBUG = false;
    private static final String TAG = "DreamOverlayService";
    private OverlayClient mCurrentClient;
    private Boolean mCurrentRedirectToWake;
    private final IDreamOverlay mDreamOverlay = new DreamOverlay(this);
    private Executor mExecutor;

    public void onComeToFront() {
    }

    public void onEndDream() {
    }

    public abstract void onStartDream(WindowManager.LayoutParams layoutParams);

    public void onWakeRequested() {
    }

    public void onWakeUp() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OverlayClient extends IDreamOverlayClient.Stub {
        private ComponentName mDreamComponent;
        IDreamOverlayCallback mDreamOverlayCallback;
        private boolean mIsPreview;
        private final WeakReference<DreamOverlayService> mService;
        private boolean mShowComplications;

        OverlayClient(WeakReference<DreamOverlayService> weakReference) {
            this.mService = weakReference;
        }

        private void applyToDream(Consumer<DreamOverlayService> consumer) {
            DreamOverlayService dreamOverlayService = this.mService.get();
            if (dreamOverlayService != null) {
                consumer.accept(dreamOverlayService);
            }
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void startDream(final WindowManager.LayoutParams layoutParams, IDreamOverlayCallback iDreamOverlayCallback, String str, boolean z, boolean z2) throws RemoteException {
            this.mDreamComponent = ComponentName.unflattenFromString(str);
            this.mShowComplications = z2;
            this.mIsPreview = z;
            this.mDreamOverlayCallback = iDreamOverlayCallback;
            applyToDream(new Consumer() { // from class: android.service.dreams.DreamOverlayService$OverlayClient$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$startDream$0(layoutParams, (DreamOverlayService) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startDream$0(WindowManager.LayoutParams layoutParams, DreamOverlayService dreamOverlayService) {
            dreamOverlayService.startDream(this, layoutParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wakeUp$1(DreamOverlayService dreamOverlayService) {
            dreamOverlayService.wakeUp(this);
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void wakeUp() {
            applyToDream(new Consumer() { // from class: android.service.dreams.DreamOverlayService$OverlayClient$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$wakeUp$1((DreamOverlayService) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$endDream$2(DreamOverlayService dreamOverlayService) {
            dreamOverlayService.endDream(this);
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void endDream() {
            applyToDream(new Consumer() { // from class: android.service.dreams.DreamOverlayService$OverlayClient$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$endDream$2((DreamOverlayService) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$comeToFront$3(DreamOverlayService dreamOverlayService) {
            dreamOverlayService.comeToFront(this);
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void comeToFront() {
            applyToDream(new Consumer() { // from class: android.service.dreams.DreamOverlayService$OverlayClient$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$comeToFront$3((DreamOverlayService) obj);
                }
            });
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void onWakeRequested() {
            if (Flags.dreamWakeRedirect()) {
                applyToDream(new Consumer() { // from class: android.service.dreams.DreamOverlayService$OverlayClient$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DreamOverlayService) obj).onWakeRequested();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestExit() {
            try {
                this.mDreamOverlayCallback.onExitRequested();
            } catch (RemoteException e) {
                Log.e(DreamOverlayService.TAG, "Could not request exit:" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void redirectWake(boolean z) {
            try {
                this.mDreamOverlayCallback.onRedirectWake(z);
            } catch (RemoteException e) {
                Log.e(DreamOverlayService.TAG, "could not request redirect wake", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldShowComplications() {
            return this.mShowComplications;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isDreamInPreviewMode() {
            return this.mIsPreview;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ComponentName getComponent() {
            return this.mDreamComponent;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDream(final OverlayClient overlayClient, final WindowManager.LayoutParams layoutParams) {
        this.mExecutor.execute(new Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startDream$0(overlayClient, layoutParams);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDream$0(OverlayClient overlayClient, WindowManager.LayoutParams layoutParams) {
        Boolean bool;
        lambda$endDream$1(this.mCurrentClient);
        this.mCurrentClient = overlayClient;
        if (Flags.dreamWakeRedirect() && (bool = this.mCurrentRedirectToWake) != null) {
            this.mCurrentClient.redirectWake(bool.booleanValue());
        }
        onStartDream(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endDream(final OverlayClient overlayClient) {
        this.mExecutor.execute(new Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$endDream$1(overlayClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: endDreamInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$endDream$1(OverlayClient overlayClient) {
        if (overlayClient == null || overlayClient != this.mCurrentClient) {
            return;
        }
        onEndDream();
        this.mCurrentClient = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeUp(final OverlayClient overlayClient) {
        this.mExecutor.execute(new Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$wakeUp$2(overlayClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wakeUp$2(OverlayClient overlayClient) {
        if (this.mCurrentClient != overlayClient) {
            return;
        }
        onWakeUp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void comeToFront(final OverlayClient overlayClient) {
        this.mExecutor.execute(new Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$comeToFront$3(overlayClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$comeToFront$3(OverlayClient overlayClient) {
        if (this.mCurrentClient != overlayClient) {
            return;
        }
        onComeToFront();
    }

    private static class DreamOverlay extends IDreamOverlay.Stub {
        private final WeakReference<DreamOverlayService> mService;

        DreamOverlay(DreamOverlayService dreamOverlayService) {
            this.mService = new WeakReference<>(dreamOverlayService);
        }

        @Override // android.service.dreams.IDreamOverlay
        public void getClient(IDreamOverlayClientCallback iDreamOverlayClientCallback) {
            try {
                iDreamOverlayClientCallback.onDreamOverlayClient(new OverlayClient(this.mService));
            } catch (RemoteException e) {
                Log.e(DreamOverlayService.TAG, "could not send client to callback", e);
            }
        }
    }

    public DreamOverlayService() {
    }

    public DreamOverlayService(Executor executor) {
        this.mExecutor = executor;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (this.mExecutor == null) {
            this.mExecutor = getMainExecutor();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mCurrentClient = null;
        super.onDestroy();
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mDreamOverlay.asBinder();
    }

    public final void requestExit() {
        OverlayClient overlayClient = this.mCurrentClient;
        if (overlayClient == null) {
            throw new IllegalStateException("requested exit with no dream present");
        }
        overlayClient.requestExit();
    }

    public final void redirectWake(boolean z) {
        if (Flags.dreamWakeRedirect()) {
            this.mCurrentRedirectToWake = Boolean.valueOf(z);
            OverlayClient overlayClient = this.mCurrentClient;
            if (overlayClient == null) {
                return;
            }
            overlayClient.redirectWake(z);
        }
    }

    public final boolean shouldShowComplications() {
        OverlayClient overlayClient = this.mCurrentClient;
        if (overlayClient == null) {
            throw new IllegalStateException("requested if should show complication when no dream active");
        }
        return overlayClient.shouldShowComplications();
    }

    public final boolean isDreamInPreviewMode() {
        OverlayClient overlayClient = this.mCurrentClient;
        if (overlayClient == null) {
            throw new IllegalStateException("requested if preview when no dream active");
        }
        return overlayClient.isDreamInPreviewMode();
    }

    public final ComponentName getDreamComponent() {
        OverlayClient overlayClient = this.mCurrentClient;
        if (overlayClient == null) {
            throw new IllegalStateException("requested dream component when no dream active");
        }
        return overlayClient.getComponent();
    }
}
