package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.autofill.Dataset;
import android.service.autofill.FillEventHistory;
import android.service.autofill.augmented.IAugmentedAutofillService;
import android.service.autofill.augmented.PresentationParams;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAugmentedAutofillManagerClient;
import android.view.autofill.IAutofillWindowPresenter;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.util.function.DecConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class AugmentedAutofillService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.autofill.augmented.AugmentedAutofillService";
    private static final String TAG = "AugmentedAutofillService";
    static boolean sDebug = !Build.IS_USER;
    static boolean sVerbose = false;
    private SparseArray<AutofillProxy> mAutofillProxies;
    private AutofillProxy mAutofillProxyForLastRequest;
    private Handler mHandler;
    private ComponentName mServiceComponentName;

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public void onFillRequest(FillRequest fillRequest, CancellationSignal cancellationSignal, FillController fillController, FillCallback fillCallback) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class AugmentedAutofillServiceImpl extends IAugmentedAutofillService.Stub {
        private AugmentedAutofillServiceImpl() {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onConnected(boolean z, boolean z2) {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AugmentedAutofillService) obj).handleOnConnected(((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue());
                }
            }, AugmentedAutofillService.this, Boolean.valueOf(z), Boolean.valueOf(z2)));
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDisconnected() {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AugmentedAutofillService) obj).handleOnDisconnected();
                }
            }, AugmentedAutofillService.this));
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onFillRequest(int i, IBinder iBinder, int i2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long j, InlineSuggestionsRequest inlineSuggestionsRequest, IFillCallback iFillCallback) {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new DecConsumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.DecConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
                    ((AugmentedAutofillService) obj).handleOnFillRequest(((Integer) obj2).intValue(), (IBinder) obj3, ((Integer) obj4).intValue(), (ComponentName) obj5, (AutofillId) obj6, (AutofillValue) obj7, ((Long) obj8).longValue(), (InlineSuggestionsRequest) obj9, (IFillCallback) obj10);
                }
            }, AugmentedAutofillService.this, Integer.valueOf(i), iBinder, Integer.valueOf(i2), componentName, autofillId, autofillValue, Long.valueOf(j), inlineSuggestionsRequest, iFillCallback));
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDestroyAllFillWindowsRequest() {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AugmentedAutofillService) obj).handleOnDestroyAllFillWindowsRequest();
                }
            }, AugmentedAutofillService.this));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
        BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        this.mServiceComponentName = intent.getComponent();
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new AugmentedAutofillServiceImpl();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.autofill.augmented.AugmentedAutofillService: " + intent);
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AugmentedAutofillService) obj).handleOnUnbind();
            }
        }, this));
        return false;
    }

    public final boolean requestAutofill(ComponentName componentName, AutofillId autofillId) {
        AutofillProxy autofillProxy = this.mAutofillProxyForLastRequest;
        if (autofillProxy != null && autofillProxy.mComponentName.equals(componentName) && autofillProxy.mFocusedId.equals(autofillId)) {
            try {
                return autofillProxy.requestAutofill();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(boolean z, boolean z2) {
        if (sDebug || z) {
            Log.d(TAG, "handleOnConnected(): debug=" + z + ", verbose=" + z2);
        }
        sDebug = z;
        sVerbose = z2;
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnFillRequest(int i, IBinder iBinder, int i2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long j, InlineSuggestionsRequest inlineSuggestionsRequest, IFillCallback iFillCallback) {
        IFillCallback iFillCallback2;
        if (this.mAutofillProxies == null) {
            this.mAutofillProxies = new SparseArray<>();
        }
        ICancellationSignal iCancellationSignalCreateTransport = CancellationSignal.createTransport();
        CancellationSignal cancellationSignalFromTransport = CancellationSignal.fromTransport(iCancellationSignalCreateTransport);
        AutofillProxy autofillProxy = this.mAutofillProxies.get(i);
        if (autofillProxy == null) {
            iFillCallback2 = iFillCallback;
            autofillProxy = new AutofillProxy(i, iBinder, i2, this.mServiceComponentName, componentName, autofillId, autofillValue, j, iFillCallback2, cancellationSignalFromTransport);
            this.mAutofillProxies.put(i, autofillProxy);
        } else {
            iFillCallback2 = iFillCallback;
            if (sDebug) {
                Log.d(TAG, "Reusing proxy for session " + i);
            }
            autofillProxy.update(autofillId, autofillValue, iFillCallback2, cancellationSignalFromTransport);
        }
        AutofillProxy autofillProxy2 = autofillProxy;
        try {
            iFillCallback2.onCancellable(iCancellationSignalCreateTransport);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        this.mAutofillProxyForLastRequest = autofillProxy2;
        onFillRequest(new FillRequest(autofillProxy2, inlineSuggestionsRequest), cancellationSignalFromTransport, new FillController(autofillProxy2), new FillCallback(autofillProxy2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDestroyAllFillWindowsRequest() {
        SparseArray<AutofillProxy> sparseArray = this.mAutofillProxies;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int iKeyAt = this.mAutofillProxies.keyAt(i);
                AutofillProxy autofillProxyValueAt = this.mAutofillProxies.valueAt(i);
                if (autofillProxyValueAt == null) {
                    Log.w(TAG, "No proxy for session " + iKeyAt);
                    return;
                }
                if (autofillProxyValueAt.mCallback != null) {
                    try {
                        if (!autofillProxyValueAt.mCallback.isCompleted()) {
                            autofillProxyValueAt.mCallback.cancel();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "failed to check current pending request status", e);
                    }
                }
                autofillProxyValueAt.destroy();
            }
            this.mAutofillProxies.clear();
            this.mAutofillProxyForLastRequest = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnUnbind() {
        SparseArray<AutofillProxy> sparseArray = this.mAutofillProxies;
        if (sparseArray == null) {
            if (sDebug) {
                Log.d(TAG, "onUnbind(): no proxy to destroy");
                return;
            }
            return;
        }
        int size = sparseArray.size();
        if (sDebug) {
            Log.d(TAG, "onUnbind(): destroying " + size + " proxies");
        }
        for (int i = 0; i < size; i++) {
            AutofillProxy autofillProxyValueAt = this.mAutofillProxies.valueAt(i);
            try {
                autofillProxyValueAt.destroy();
            } catch (Exception unused) {
                Log.w(TAG, "error destroying " + autofillProxyValueAt);
            }
        }
        this.mAutofillProxies = null;
        this.mAutofillProxyForLastRequest = null;
    }

    @Override // android.app.Service
    protected final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("Service component: ");
        printWriter.println(ComponentName.flattenToShortString(this.mServiceComponentName));
        SparseArray<AutofillProxy> sparseArray = this.mAutofillProxies;
        if (sparseArray != null) {
            int size = sparseArray.size();
            printWriter.print("Number proxies: ");
            printWriter.println(size);
            for (int i = 0; i < size; i++) {
                int iKeyAt = this.mAutofillProxies.keyAt(i);
                AutofillProxy autofillProxyValueAt = this.mAutofillProxies.valueAt(i);
                printWriter.print(i);
                printWriter.print(") SessionId=");
                printWriter.print(iKeyAt);
                printWriter.println(":");
                autofillProxyValueAt.dump("  ", printWriter);
            }
        }
        dump(printWriter, strArr);
    }

    protected void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print(getClass().getName());
        printWriter.println(": nothing to dump");
    }

    public final FillEventHistory getFillEventHistory() {
        AutofillManager autofillManager = (AutofillManager) getSystemService(AutofillManager.class);
        if (autofillManager == null) {
            return null;
        }
        return autofillManager.getFillEventHistory();
    }

    static final class AutofillProxy {
        static final int REPORT_EVENT_INLINE_RESPONSE = 4;
        static final int REPORT_EVENT_NO_RESPONSE = 1;
        static final int REPORT_EVENT_UI_DESTROYED = 3;
        static final int REPORT_EVENT_UI_SHOWN = 2;
        private IFillCallback mCallback;
        private CancellationSignal mCancellationSignal;
        private final IAugmentedAutofillManagerClient mClient;
        public final ComponentName mComponentName;
        private FillWindow mFillWindow;
        private long mFirstOnSuccessTime;
        private final long mFirstRequestTime;
        private AutofillId mFocusedId;
        private AutofillValue mFocusedValue;
        private AssistStructure.ViewNode mFocusedViewNode;
        private AutofillId mLastShownId;
        private final Object mLock;
        private String mServicePackageName;
        private final int mSessionId;
        private PresentationParams.SystemPopupPresentationParams mSmartSuggestion;
        public final int mTaskId;
        private long mUiFirstDestroyedTime;
        private long mUiFirstShownTime;

        @Retention(RetentionPolicy.SOURCE)
        @interface ReportEvent {
        }

        private AutofillProxy(int i, IBinder iBinder, int i2, ComponentName componentName, ComponentName componentName2, AutofillId autofillId, AutofillValue autofillValue, long j, IFillCallback iFillCallback, CancellationSignal cancellationSignal) {
            this.mLock = new Object();
            this.mSessionId = i;
            this.mClient = IAugmentedAutofillManagerClient.Stub.asInterface(iBinder);
            this.mCallback = iFillCallback;
            this.mTaskId = i2;
            this.mComponentName = componentName2;
            this.mServicePackageName = componentName.getPackageName();
            this.mFocusedId = autofillId;
            this.mFocusedValue = autofillValue;
            this.mFirstRequestTime = j;
            this.mCancellationSignal = cancellationSignal;
        }

        public PresentationParams.SystemPopupPresentationParams getSmartSuggestionParams() {
            synchronized (this.mLock) {
                if (this.mSmartSuggestion != null && this.mFocusedId.equals(this.mLastShownId)) {
                    return this.mSmartSuggestion;
                }
                try {
                    Rect viewCoordinates = this.mClient.getViewCoordinates(this.mFocusedId);
                    if (viewCoordinates == null) {
                        if (AugmentedAutofillService.sDebug) {
                            Log.d(AugmentedAutofillService.TAG, "getViewCoordinates(" + this.mFocusedId + ") returned null");
                        }
                        return null;
                    }
                    PresentationParams.SystemPopupPresentationParams systemPopupPresentationParams = new PresentationParams.SystemPopupPresentationParams(this, viewCoordinates);
                    this.mSmartSuggestion = systemPopupPresentationParams;
                    this.mLastShownId = this.mFocusedId;
                    return systemPopupPresentationParams;
                } catch (RemoteException unused) {
                    Log.w(AugmentedAutofillService.TAG, "Could not get coordinates for " + this.mFocusedId);
                    return null;
                }
            }
        }

        public void autofill(List<Pair<AutofillId, AutofillValue>> list) throws RemoteException {
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            ArrayList arrayList2 = new ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                Pair<AutofillId, AutofillValue> pair = list.get(i);
                arrayList.add(pair.first);
                arrayList2.add(pair.second);
            }
            if (size == 1 && ((AutofillId) arrayList.get(0)).equals(this.mFocusedId)) {
                z = true;
            }
            this.mClient.autofill(this.mSessionId, arrayList, arrayList2, z);
        }

        public void setFillWindow(FillWindow fillWindow) {
            synchronized (this.mLock) {
                this.mFillWindow = fillWindow;
            }
        }

        public FillWindow getFillWindow() {
            FillWindow fillWindow;
            synchronized (this.mLock) {
                fillWindow = this.mFillWindow;
            }
            return fillWindow;
        }

        public void requestShowFillUi(int i, int i2, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
            if (this.mCancellationSignal.isCanceled()) {
                if (AugmentedAutofillService.sVerbose) {
                    Log.v(AugmentedAutofillService.TAG, "requestShowFillUi() not showing because request is cancelled");
                    return;
                }
                return;
            }
            this.mClient.requestShowFillUi(this.mSessionId, this.mFocusedId, i, i2, rect, iAutofillWindowPresenter);
        }

        public void requestHideFillUi() throws RemoteException {
            this.mClient.requestHideFillUi(this.mSessionId, this.mFocusedId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean requestAutofill() throws RemoteException {
            return this.mClient.requestAutofill(this.mSessionId, this.mFocusedId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update(AutofillId autofillId, AutofillValue autofillValue, IFillCallback iFillCallback, CancellationSignal cancellationSignal) {
            synchronized (this.mLock) {
                this.mFocusedId = autofillId;
                this.mFocusedValue = autofillValue;
                this.mFocusedViewNode = null;
                IFillCallback iFillCallback2 = this.mCallback;
                if (iFillCallback2 != null) {
                    try {
                        if (!iFillCallback2.isCompleted()) {
                            this.mCallback.cancel();
                        }
                    } catch (RemoteException e) {
                        Log.e(AugmentedAutofillService.TAG, "failed to check current pending request status", e);
                    }
                    Log.d(AugmentedAutofillService.TAG, "mCallback is updated.");
                    this.mCallback = iFillCallback;
                    this.mCancellationSignal = cancellationSignal;
                } else {
                    this.mCallback = iFillCallback;
                    this.mCancellationSignal = cancellationSignal;
                }
            }
        }

        public AutofillId getFocusedId() {
            AutofillId autofillId;
            synchronized (this.mLock) {
                autofillId = this.mFocusedId;
            }
            return autofillId;
        }

        public AutofillValue getFocusedValue() {
            AutofillValue autofillValue;
            synchronized (this.mLock) {
                autofillValue = this.mFocusedValue;
            }
            return autofillValue;
        }

        void reportResult(List<Dataset> list, Bundle bundle, boolean z) {
            try {
                this.mCallback.onSuccess(list, bundle, z);
            } catch (RemoteException e) {
                Log.e(AugmentedAutofillService.TAG, "Error calling back with the inline suggestions data: " + e);
            }
        }

        public AssistStructure.ViewNode getFocusedViewNode() {
            AssistStructure.ViewNode viewNode;
            synchronized (this.mLock) {
                if (this.mFocusedViewNode == null) {
                    try {
                        AssistStructure.ViewNodeParcelable viewNodeParcelable = this.mClient.getViewNodeParcelable(this.mFocusedId);
                        if (viewNodeParcelable != null) {
                            this.mFocusedViewNode = viewNodeParcelable.getViewNode();
                        }
                    } catch (RemoteException e) {
                        Log.e(AugmentedAutofillService.TAG, "Error getting the ViewNode of the focused view: " + e);
                        return null;
                    }
                }
                viewNode = this.mFocusedViewNode;
            }
            return viewNode;
        }

        void logEvent(int i) {
            int i2;
            if (AugmentedAutofillService.sVerbose) {
                Log.v(AugmentedAutofillService.TAG, "returnAndLogResult(): " + i);
            }
            int i3 = 1;
            long j = -1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    if (i != 3) {
                        i2 = 0;
                        if (i != 4) {
                            Log.w(AugmentedAutofillService.TAG, "invalid event reported: " + i);
                        } else if (this.mFirstOnSuccessTime == 0) {
                            long jElapsedRealtime = SystemClock.elapsedRealtime();
                            this.mFirstOnSuccessTime = jElapsedRealtime;
                            j = jElapsedRealtime - this.mFirstRequestTime;
                            if (AugmentedAutofillService.sDebug) {
                                Log.d(AugmentedAutofillService.TAG, "Inline response in " + TimeUtils.formatDuration(j));
                            }
                        }
                    } else if (this.mUiFirstDestroyedTime == 0) {
                        long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                        this.mUiFirstDestroyedTime = jElapsedRealtime2;
                        j = jElapsedRealtime2 - this.mFirstRequestTime;
                        if (AugmentedAutofillService.sDebug) {
                            Log.d(AugmentedAutofillService.TAG, "UI destroyed in " + TimeUtils.formatDuration(j));
                        }
                    }
                    Helper.logResponse(i2, this.mServicePackageName, this.mComponentName, this.mSessionId, j);
                }
                if (this.mUiFirstShownTime == 0) {
                    long jElapsedRealtime3 = SystemClock.elapsedRealtime();
                    this.mUiFirstShownTime = jElapsedRealtime3;
                    j = jElapsedRealtime3 - this.mFirstRequestTime;
                    if (AugmentedAutofillService.sDebug) {
                        Log.d(AugmentedAutofillService.TAG, "UI shown in " + TimeUtils.formatDuration(j));
                    }
                }
            } else {
                i3 = 10;
                if (this.mFirstOnSuccessTime == 0) {
                    long jElapsedRealtime4 = SystemClock.elapsedRealtime();
                    this.mFirstOnSuccessTime = jElapsedRealtime4;
                    j = jElapsedRealtime4 - this.mFirstRequestTime;
                    if (AugmentedAutofillService.sDebug) {
                        Log.d(AugmentedAutofillService.TAG, "Service responded nothing in " + TimeUtils.formatDuration(j));
                    }
                }
            }
            i2 = i3;
            Helper.logResponse(i2, this.mServicePackageName, this.mComponentName, this.mSessionId, j);
        }

        public void dump(String str, PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("sessionId: ");
            printWriter.println(this.mSessionId);
            printWriter.print(str);
            printWriter.print("taskId: ");
            printWriter.println(this.mTaskId);
            printWriter.print(str);
            printWriter.print("component: ");
            printWriter.println(this.mComponentName.flattenToShortString());
            printWriter.print(str);
            printWriter.print("focusedId: ");
            printWriter.println(this.mFocusedId);
            if (this.mFocusedValue != null) {
                printWriter.print(str);
                printWriter.print("focusedValue: ");
                printWriter.println(this.mFocusedValue);
            }
            if (this.mLastShownId != null) {
                printWriter.print(str);
                printWriter.print("lastShownId: ");
                printWriter.println(this.mLastShownId);
            }
            printWriter.print(str);
            printWriter.print("client: ");
            printWriter.println(this.mClient);
            String str2 = str + "  ";
            if (this.mFillWindow != null) {
                printWriter.print(str);
                printWriter.println("window:");
                this.mFillWindow.dump(str2, printWriter);
            }
            if (this.mSmartSuggestion != null) {
                printWriter.print(str);
                printWriter.println("smartSuggestion:");
                this.mSmartSuggestion.dump(str2, printWriter);
            }
            long j = this.mFirstOnSuccessTime;
            if (j > 0) {
                long j2 = j - this.mFirstRequestTime;
                printWriter.print(str);
                printWriter.print("response time: ");
                TimeUtils.formatDuration(j2, printWriter);
                printWriter.println();
            }
            long j3 = this.mUiFirstShownTime;
            if (j3 > 0) {
                long j4 = j3 - this.mFirstRequestTime;
                printWriter.print(str);
                printWriter.print("UI rendering time: ");
                TimeUtils.formatDuration(j4, printWriter);
                printWriter.println();
            }
            long j5 = this.mUiFirstDestroyedTime;
            if (j5 > 0) {
                long j6 = j5 - this.mFirstRequestTime;
                printWriter.print(str);
                printWriter.print("UI life time: ");
                TimeUtils.formatDuration(j6, printWriter);
                printWriter.println();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void destroy() {
            synchronized (this.mLock) {
                if (this.mFillWindow != null) {
                    if (AugmentedAutofillService.sDebug) {
                        Log.d(AugmentedAutofillService.TAG, "destroying window");
                    }
                    this.mFillWindow.destroy();
                    this.mFillWindow = null;
                }
            }
        }
    }
}
