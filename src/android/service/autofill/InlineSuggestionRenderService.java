package android.service.autofill;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.autofill.IInlineSuggestionRenderService;
import android.service.autofill.IInlineSuggestionUi;
import android.util.Log;
import android.util.LruCache;
import android.util.Size;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.window.InputTransferToken;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.function.BiConsumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class InlineSuggestionRenderService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.autofill.InlineSuggestionRenderService";
    private static final String TAG = "InlineSuggestionRenderService";
    private IInlineSuggestionUiCallback mCallback;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper(), null, true);
    private final LruCache<InlineSuggestionUiImpl, Boolean> mActiveInlineSuggestions = new LruCache<InlineSuggestionUiImpl, Boolean>(this, 30) { // from class: android.service.autofill.InlineSuggestionRenderService.1
        @Override // android.util.LruCache
        public void entryRemoved(boolean z, InlineSuggestionUiImpl inlineSuggestionUiImpl, Boolean bool, Boolean bool2) {
            if (z) {
                Log.w(InlineSuggestionRenderService.TAG, "Hit max=30 entries in the cache. Releasing oldest one to make space.");
                inlineSuggestionUiImpl.releaseSurfaceControlViewHost();
            }
        }
    };

    private Size measuredSize(View view, int i, int i2, Size size, Size size2) {
        int iMakeMeasureSpec;
        int iMakeMeasureSpec2;
        if (i != -2 && i2 != -2) {
            return new Size(i, i2);
        }
        if (i == -2) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2.getWidth(), Integer.MIN_VALUE);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        }
        if (i2 == -2) {
            iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2.getHeight(), Integer.MIN_VALUE);
        } else {
            iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        }
        view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        return new Size(Math.max(view.getMeasuredWidth(), size.getWidth()), Math.max(view.getMeasuredHeight(), size.getHeight()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRenderSuggestion(final IInlineSuggestionUiCallback iInlineSuggestionUiCallback, InlinePresentation inlinePresentation, int i, int i2, IBinder iBinder, int i3, int i4, int i5) {
        if (iBinder == null) {
            try {
                iInlineSuggestionUiCallback.onError();
                return;
            } catch (RemoteException unused) {
                Log.w(TAG, "RemoteException calling onError()");
                return;
            }
        }
        updateDisplay(i3);
        try {
            View viewOnRenderSuggestion = onRenderSuggestion(inlinePresentation, i, i2);
            if (viewOnRenderSuggestion == null) {
                Log.w(TAG, "ExtServices failed to render the inline suggestion view.");
                try {
                    iInlineSuggestionUiCallback.onError();
                } catch (RemoteException unused2) {
                    Log.w(TAG, "Null suggestion view returned by renderer");
                }
                return;
            }
            this.mCallback = iInlineSuggestionUiCallback;
            final Size sizeMeasuredSize = measuredSize(viewOnRenderSuggestion, i, i2, inlinePresentation.getInlinePresentationSpec().getMinSize(), inlinePresentation.getInlinePresentationSpec().getMaxSize());
            Log.v(TAG, "width=" + i + ", height=" + i2 + ", measuredSize=" + sizeMeasuredSize);
            InlineSuggestionRoot inlineSuggestionRoot = new InlineSuggestionRoot(this, iInlineSuggestionUiCallback);
            inlineSuggestionRoot.addView(viewOnRenderSuggestion);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(sizeMeasuredSize.getWidth(), sizeMeasuredSize.getHeight(), 2, 0, -2);
            final SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(this, getDisplay(), new InputTransferToken(iBinder), TAG);
            surfaceControlViewHost.setView(inlineSuggestionRoot, layoutParams);
            viewOnRenderSuggestion.setFocusable(false);
            viewOnRenderSuggestion.setOnClickListener(new View.OnClickListener() { // from class: android.service.autofill.InlineSuggestionRenderService$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InlineSuggestionRenderService.lambda$handleRenderSuggestion$0(iInlineSuggestionUiCallback, view);
                }
            });
            final View.OnLongClickListener onLongClickListener = viewOnRenderSuggestion.getOnLongClickListener();
            viewOnRenderSuggestion.setOnLongClickListener(new View.OnLongClickListener() { // from class: android.service.autofill.InlineSuggestionRenderService$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return InlineSuggestionRenderService.lambda$handleRenderSuggestion$1(onLongClickListener, iInlineSuggestionUiCallback, view);
                }
            });
            final InlineSuggestionUiImpl inlineSuggestionUiImpl = new InlineSuggestionUiImpl(surfaceControlViewHost, this.mMainHandler, i4, i5);
            this.mActiveInlineSuggestions.put(inlineSuggestionUiImpl, true);
            this.mMainHandler.post(new Runnable() { // from class: android.service.autofill.InlineSuggestionRenderService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    InlineSuggestionRenderService.lambda$handleRenderSuggestion$2(iInlineSuggestionUiCallback, inlineSuggestionUiImpl, surfaceControlViewHost, sizeMeasuredSize);
                }
            });
        } finally {
            updateDisplay(0);
        }
    }

    static /* synthetic */ void lambda$handleRenderSuggestion$0(IInlineSuggestionUiCallback iInlineSuggestionUiCallback, View view) {
        try {
            iInlineSuggestionUiCallback.onClick();
        } catch (RemoteException unused) {
            Log.w(TAG, "RemoteException calling onClick()");
        }
    }

    static /* synthetic */ boolean lambda$handleRenderSuggestion$1(View.OnLongClickListener onLongClickListener, IInlineSuggestionUiCallback iInlineSuggestionUiCallback, View view) {
        if (onLongClickListener != null) {
            onLongClickListener.onLongClick(view);
        }
        try {
            iInlineSuggestionUiCallback.onLongClick();
            return true;
        } catch (RemoteException unused) {
            Log.w(TAG, "RemoteException calling onLongClick()");
            return true;
        }
    }

    static /* synthetic */ void lambda$handleRenderSuggestion$2(IInlineSuggestionUiCallback iInlineSuggestionUiCallback, InlineSuggestionUiImpl inlineSuggestionUiImpl, SurfaceControlViewHost surfaceControlViewHost, Size size) {
        try {
            iInlineSuggestionUiCallback.onContent(new InlineSuggestionUiWrapper(inlineSuggestionUiImpl), surfaceControlViewHost.getSurfacePackage(), size.getWidth(), size.getHeight());
        } catch (RemoteException unused) {
            Log.w(TAG, "RemoteException calling onContent()");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGetInlineSuggestionsRendererInfo(RemoteCallback remoteCallback) {
        remoteCallback.sendResult(onGetInlineSuggestionsRendererInfo());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroySuggestionViews(int i, int i2) {
        Log.v(TAG, "handleDestroySuggestionViews called for " + i + ":" + i2);
        for (InlineSuggestionUiImpl inlineSuggestionUiImpl : this.mActiveInlineSuggestions.snapshot().keySet()) {
            if (inlineSuggestionUiImpl.mUserId == i && inlineSuggestionUiImpl.mSessionId == i2) {
                Log.v(TAG, "Destroy " + inlineSuggestionUiImpl);
                inlineSuggestionUiImpl.releaseSurfaceControlViewHost();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InlineSuggestionUiWrapper extends IInlineSuggestionUi.Stub {
        private final WeakReference<InlineSuggestionUiImpl> mUiImpl;

        InlineSuggestionUiWrapper(InlineSuggestionUiImpl inlineSuggestionUiImpl) {
            this.mUiImpl = new WeakReference<>(inlineSuggestionUiImpl);
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void releaseSurfaceControlViewHost() {
            InlineSuggestionUiImpl inlineSuggestionUiImpl = this.mUiImpl.get();
            if (inlineSuggestionUiImpl != null) {
                inlineSuggestionUiImpl.releaseSurfaceControlViewHost();
            }
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void getSurfacePackage(ISurfacePackageResultCallback iSurfacePackageResultCallback) {
            InlineSuggestionUiImpl inlineSuggestionUiImpl = this.mUiImpl.get();
            if (inlineSuggestionUiImpl != null) {
                inlineSuggestionUiImpl.getSurfacePackage(iSurfacePackageResultCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class InlineSuggestionUiImpl {
        private final Handler mHandler;
        private final int mSessionId;
        private final int mUserId;
        private SurfaceControlViewHost mViewHost;

        InlineSuggestionUiImpl(SurfaceControlViewHost surfaceControlViewHost, Handler handler, int i, int i2) {
            this.mViewHost = surfaceControlViewHost;
            this.mHandler = handler;
            this.mUserId = i;
            this.mSessionId = i2;
        }

        public void releaseSurfaceControlViewHost() {
            this.mHandler.post(new Runnable() { // from class: android.service.autofill.InlineSuggestionRenderService$InlineSuggestionUiImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$releaseSurfaceControlViewHost$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$releaseSurfaceControlViewHost$0() {
            if (this.mViewHost == null) {
                return;
            }
            Log.v(InlineSuggestionRenderService.TAG, "Releasing inline suggestion view host");
            this.mViewHost.release();
            this.mViewHost = null;
            InlineSuggestionRenderService.this.mActiveInlineSuggestions.remove(this);
            Log.v(InlineSuggestionRenderService.TAG, "Removed the inline suggestion from the cache, current size=" + InlineSuggestionRenderService.this.mActiveInlineSuggestions.size());
        }

        public void getSurfacePackage(final ISurfacePackageResultCallback iSurfacePackageResultCallback) {
            Log.d(InlineSuggestionRenderService.TAG, "getSurfacePackage");
            this.mHandler.post(new Runnable() { // from class: android.service.autofill.InlineSuggestionRenderService$InlineSuggestionUiImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getSurfacePackage$1(iSurfacePackageResultCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSurfacePackage$1(ISurfacePackageResultCallback iSurfacePackageResultCallback) {
            try {
                SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
                iSurfacePackageResultCallback.onResult(surfaceControlViewHost == null ? null : surfaceControlViewHost.getSurfacePackage());
            } catch (RemoteException unused) {
                Log.w(InlineSuggestionRenderService.TAG, "RemoteException calling onSurfacePackage");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("mActiveInlineSuggestions: " + this.mActiveInlineSuggestions.size());
        for (InlineSuggestionUiImpl inlineSuggestionUiImpl : this.mActiveInlineSuggestions.snapshot().keySet()) {
            printWriter.printf("ui: [%s] - [%d]  [%d]\n", inlineSuggestionUiImpl, Integer.valueOf(inlineSuggestionUiImpl.mUserId), Integer.valueOf(inlineSuggestionUiImpl.mSessionId));
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        BaseBundle.setShouldDefuse(true);
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new AnonymousClass2().asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.autofill.InlineSuggestionRenderService: " + intent);
        return null;
    }

    /* renamed from: android.service.autofill.InlineSuggestionRenderService$2, reason: invalid class name */
    class AnonymousClass2 extends IInlineSuggestionRenderService.Stub {
        AnonymousClass2() {
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void renderSuggestion(IInlineSuggestionUiCallback iInlineSuggestionUiCallback, InlinePresentation inlinePresentation, int i, int i2, IBinder iBinder, int i3, int i4, int i5) {
            InlineSuggestionRenderService.this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new NonaConsumer() { // from class: android.service.autofill.InlineSuggestionRenderService$2$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.NonaConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9) {
                    ((InlineSuggestionRenderService) obj).handleRenderSuggestion((IInlineSuggestionUiCallback) obj2, (InlinePresentation) obj3, ((Integer) obj4).intValue(), ((Integer) obj5).intValue(), (IBinder) obj6, ((Integer) obj7).intValue(), ((Integer) obj8).intValue(), ((Integer) obj9).intValue());
                }
            }, InlineSuggestionRenderService.this, iInlineSuggestionUiCallback, inlinePresentation, Integer.valueOf(i), Integer.valueOf(i2), iBinder, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)));
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void getInlineSuggestionsRendererInfo(RemoteCallback remoteCallback) {
            InlineSuggestionRenderService.this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.autofill.InlineSuggestionRenderService$2$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((InlineSuggestionRenderService) obj).handleGetInlineSuggestionsRendererInfo((RemoteCallback) obj2);
                }
            }, InlineSuggestionRenderService.this, remoteCallback));
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void destroySuggestionViews(int i, int i2) {
            InlineSuggestionRenderService.this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.autofill.InlineSuggestionRenderService$2$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((InlineSuggestionRenderService) obj).handleDestroySuggestionViews(((Integer) obj2).intValue(), ((Integer) obj3).intValue());
                }
            }, InlineSuggestionRenderService.this, Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public final void startIntentSender(IntentSender intentSender) {
        IInlineSuggestionUiCallback iInlineSuggestionUiCallback = this.mCallback;
        if (iInlineSuggestionUiCallback == null) {
            return;
        }
        try {
            iInlineSuggestionUiCallback.onStartIntentSender(intentSender);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public Bundle onGetInlineSuggestionsRendererInfo() {
        return Bundle.EMPTY;
    }

    public View onRenderSuggestion(InlinePresentation inlinePresentation, int i, int i2) {
        Log.e(TAG, "service implementation (" + getClass() + " does not implement onRenderSuggestion()");
        return null;
    }
}
