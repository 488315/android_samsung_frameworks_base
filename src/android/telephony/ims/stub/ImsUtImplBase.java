package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ims.ImsUtListener;
import android.telephony.ims.stub.ImsUtImplBase;
import android.util.Log;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.IImsUtListener;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes4.dex */
public class ImsUtImplBase {
    public static final int CALL_BARRING_ALL = 7;
    public static final int CALL_BARRING_ALL_INCOMING = 1;
    public static final int CALL_BARRING_ALL_OUTGOING = 2;
    public static final int CALL_BARRING_ANONYMOUS_INCOMING = 6;
    public static final int CALL_BARRING_INCOMING_ALL_SERVICES = 9;
    public static final int CALL_BARRING_OUTGOING_ALL_SERVICES = 8;
    public static final int CALL_BARRING_OUTGOING_INTL = 3;
    public static final int CALL_BARRING_OUTGOING_INTL_EXCL_HOME = 4;
    public static final int CALL_BARRING_SPECIFIC_INCOMING_CALLS = 10;
    public static final int CALL_BLOCKING_INCOMING_WHEN_ROAMING = 5;
    public static final int INVALID_RESULT = -1;
    private static final String TAG = "ImsUtImplBase";
    private Executor mExecutor = new PendingIntent$$ExternalSyntheticLambda0();
    private final IImsUt.Stub mServiceImpl = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallBarringMode {
    }

    public void close() {
    }

    public int queryCallBarring(int i) {
        return -1;
    }

    public int queryCallBarringForServiceClass(int i, int i2) {
        return -1;
    }

    public int queryCallForward(int i, String str) {
        return -1;
    }

    public int queryCallWaiting() {
        return -1;
    }

    public int queryClip() {
        return -1;
    }

    public int queryClir() {
        return -1;
    }

    public int queryColp() {
        return -1;
    }

    public int queryColr() {
        return -1;
    }

    public void setListener(ImsUtListener imsUtListener) {
    }

    public int transact(Bundle bundle) {
        return -1;
    }

    public int updateCallBarring(int i, int i2, String[] strArr) {
        return -1;
    }

    public int updateCallBarringForServiceClass(int i, int i2, String[] strArr, int i3) {
        return -1;
    }

    public int updateCallBarringWithPassword(int i, int i2, String[] strArr, int i3, String str) {
        return -1;
    }

    public int updateCallForward(int i, int i2, String str, int i3, int i4) {
        return 0;
    }

    public int updateCallWaiting(boolean z, int i) {
        return -1;
    }

    public int updateClip(boolean z) {
        return -1;
    }

    public int updateClir(int i) {
        return -1;
    }

    public int updateColp(boolean z) {
        return -1;
    }

    public int updateColr(int i) {
        return -1;
    }

    /* renamed from: android.telephony.ims.stub.ImsUtImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends IImsUt.Stub {
        private final Object mLock = new Object();
        private ImsUtListener mUtListener;

        @Override // com.android.ims.internal.IImsUt
        public boolean isUssdEnabled() throws RemoteException {
            return false;
        }

        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            ImsUtImplBase.this.close();
        }

        @Override // com.android.ims.internal.IImsUt
        public void close() throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    ImsUtImplBase.AnonymousClass1.this.lambda$close$0();
                }
            }, "close");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCallBarring$1(int i) {
            return Integer.valueOf(ImsUtImplBase.this.queryCallBarring(i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallBarring(final int i) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCallBarring$1;
                    lambda$queryCallBarring$1 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCallBarring$1(i);
                    return lambda$queryCallBarring$1;
                }
            }, "queryCallBarring")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCallForward$2(int i, String str) {
            return Integer.valueOf(ImsUtImplBase.this.queryCallForward(i, str));
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallForward(final int i, final String str) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCallForward$2;
                    lambda$queryCallForward$2 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCallForward$2(i, str);
                    return lambda$queryCallForward$2;
                }
            }, "queryCallForward")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCallWaiting$3() {
            return Integer.valueOf(ImsUtImplBase.this.queryCallWaiting());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallWaiting() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCallWaiting$3;
                    lambda$queryCallWaiting$3 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCallWaiting$3();
                    return lambda$queryCallWaiting$3;
                }
            }, "queryCallWaiting")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCLIR$4() {
            return Integer.valueOf(ImsUtImplBase.this.queryCLIR());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCLIR() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCLIR$4;
                    lambda$queryCLIR$4 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCLIR$4();
                    return lambda$queryCLIR$4;
                }
            }, "queryCLIR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCLIP$5() {
            return Integer.valueOf(ImsUtImplBase.this.queryCLIP());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCLIP() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCLIP$5;
                    lambda$queryCLIP$5 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCLIP$5();
                    return lambda$queryCLIP$5;
                }
            }, "queryCLIP")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCOLR$6() {
            return Integer.valueOf(ImsUtImplBase.this.queryCOLR());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCOLR() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda10
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCOLR$6;
                    lambda$queryCOLR$6 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCOLR$6();
                    return lambda$queryCOLR$6;
                }
            }, "queryCOLR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCOLP$7() {
            return Integer.valueOf(ImsUtImplBase.this.queryCOLP());
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCOLP() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCOLP$7;
                    lambda$queryCOLP$7 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCOLP$7();
                    return lambda$queryCOLP$7;
                }
            }, "queryCOLP")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$transact$8(Bundle bundle) {
            return Integer.valueOf(ImsUtImplBase.this.transact(bundle));
        }

        @Override // com.android.ims.internal.IImsUt
        public int transact(final Bundle bundle) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda14
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$transact$8;
                    lambda$transact$8 = ImsUtImplBase.AnonymousClass1.this.lambda$transact$8(bundle);
                    return lambda$transact$8;
                }
            }, "transact")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCallBarring$9(int i, int i2, String[] strArr) {
            return Integer.valueOf(ImsUtImplBase.this.updateCallBarring(i, i2, strArr));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarring(final int i, final int i2, final String[] strArr) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCallBarring$9;
                    lambda$updateCallBarring$9 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCallBarring$9(i, i2, strArr);
                    return lambda$updateCallBarring$9;
                }
            }, "updateCallBarring")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCallForward$10(int i, int i2, String str, int i3, int i4) {
            return Integer.valueOf(ImsUtImplBase.this.updateCallForward(i, i2, str, i3, i4));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallForward(final int i, final int i2, final String str, final int i3, final int i4) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCallForward$10;
                    lambda$updateCallForward$10 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCallForward$10(i, i2, str, i3, i4);
                    return lambda$updateCallForward$10;
                }
            }, "updateCallForward")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCallWaiting$11(boolean z, int i) {
            return Integer.valueOf(ImsUtImplBase.this.updateCallWaiting(z, i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallWaiting(final boolean z, final int i) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCallWaiting$11;
                    lambda$updateCallWaiting$11 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCallWaiting$11(z, i);
                    return lambda$updateCallWaiting$11;
                }
            }, "updateCallWaiting")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCLIR$12(int i) {
            return Integer.valueOf(ImsUtImplBase.this.updateCLIR(i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCLIR(final int i) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCLIR$12;
                    lambda$updateCLIR$12 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCLIR$12(i);
                    return lambda$updateCLIR$12;
                }
            }, "updateCLIR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCLIP$13(boolean z) {
            return Integer.valueOf(ImsUtImplBase.this.updateCLIP(z));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCLIP(final boolean z) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda21
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCLIP$13;
                    lambda$updateCLIP$13 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCLIP$13(z);
                    return lambda$updateCLIP$13;
                }
            }, "updateCLIP")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCOLR$14(int i) {
            return Integer.valueOf(ImsUtImplBase.this.updateCOLR(i));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCOLR(final int i) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda11
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCOLR$14;
                    lambda$updateCOLR$14 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCOLR$14(i);
                    return lambda$updateCOLR$14;
                }
            }, "updateCOLR")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCOLP$15(boolean z) {
            return Integer.valueOf(ImsUtImplBase.this.updateCOLP(z));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCOLP(final boolean z) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCOLP$15;
                    lambda$updateCOLP$15 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCOLP$15(z);
                    return lambda$updateCOLP$15;
                }
            }, "updateCOLP")).intValue();
        }

        @Override // com.android.ims.internal.IImsUt
        public void setListener(final IImsUtListener iImsUtListener) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ImsUtImplBase.AnonymousClass1.this.lambda$setListener$16(iImsUtListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$16(IImsUtListener iImsUtListener) {
            ImsUtListener imsUtListener = this.mUtListener;
            if (imsUtListener != null && !imsUtListener.getListenerInterface().asBinder().isBinderAlive()) {
                Log.w(ImsUtImplBase.TAG, "setListener: discarding dead Binder");
                this.mUtListener = null;
            }
            ImsUtListener imsUtListener2 = this.mUtListener;
            if (imsUtListener2 == null || iImsUtListener == null || !Objects.equals(imsUtListener2.getListenerInterface().asBinder(), iImsUtListener.asBinder())) {
                if (iImsUtListener == null) {
                    this.mUtListener = null;
                } else if (iImsUtListener != null && this.mUtListener == null) {
                    this.mUtListener = new ImsUtListener(iImsUtListener);
                } else {
                    Log.w(ImsUtImplBase.TAG, "setListener is being called when there is already an active listener");
                    this.mUtListener = new ImsUtListener(iImsUtListener);
                }
                ImsUtImplBase.this.setListener(this.mUtListener);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCallBarringForServiceClass$17(int i, int i2) {
            return Integer.valueOf(ImsUtImplBase.this.queryCallBarringForServiceClass(i, i2));
        }

        @Override // com.android.ims.internal.IImsUt
        public int queryCallBarringForServiceClass(final int i, final int i2) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda20
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCallBarringForServiceClass$17;
                    lambda$queryCallBarringForServiceClass$17 = ImsUtImplBase.AnonymousClass1.this.lambda$queryCallBarringForServiceClass$17(i, i2);
                    return lambda$queryCallBarringForServiceClass$17;
                }
            }, "queryCallBarringForServiceClass")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCallBarringForServiceClass$18(int i, int i2, String[] strArr, int i3) {
            return Integer.valueOf(ImsUtImplBase.this.updateCallBarringForServiceClass(i, i2, strArr, i3));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarringForServiceClass(final int i, final int i2, final String[] strArr, final int i3) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda17
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCallBarringForServiceClass$18;
                    lambda$updateCallBarringForServiceClass$18 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCallBarringForServiceClass$18(i, i2, strArr, i3);
                    return lambda$updateCallBarringForServiceClass$18;
                }
            }, "updateCallBarringForServiceClass")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$updateCallBarringWithPassword$19(int i, int i2, String[] strArr, int i3, String str) {
            return Integer.valueOf(ImsUtImplBase.this.updateCallBarringWithPassword(i, i2, strArr, i3, str));
        }

        @Override // com.android.ims.internal.IImsUt
        public int updateCallBarringWithPassword(final int i, final int i2, final String[] strArr, final int i3, final String str) throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$updateCallBarringWithPassword$19;
                    lambda$updateCallBarringWithPassword$19 = ImsUtImplBase.AnonymousClass1.this.lambda$updateCallBarringWithPassword$19(i, i2, strArr, i3, str);
                    return lambda$updateCallBarringWithPassword$19;
                }
            }, "updateCallBarringWithPassword")).intValue();
        }

        private void executeMethodAsync(final Runnable runnable, String str) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, ImsUtImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsUtImplBase.TAG, "ImsUtImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) throws RemoteException {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.stub.ImsUtImplBase$1$$ExternalSyntheticLambda19
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = TelephonyUtils.runWithCleanCallingIdentity((Supplier<Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, ImsUtImplBase.this.mExecutor).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(ImsUtImplBase.TAG, "ImsUtImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public int queryCLIR() {
        return queryClir();
    }

    public int queryCLIP() {
        return queryClip();
    }

    public int queryCOLR() {
        return queryColr();
    }

    public int queryCOLP() {
        return queryColp();
    }

    public int updateCLIR(int i) {
        return updateClir(i);
    }

    public int updateCLIP(boolean z) {
        return updateClip(z);
    }

    public int updateCOLR(int i) {
        return updateColr(i);
    }

    public int updateCOLP(boolean z) {
        return updateColp(z);
    }

    public IImsUt getInterface() {
        return this.mServiceImpl;
    }

    public final void setDefaultExecutor(Executor executor) {
        this.mExecutor = executor;
    }
}
