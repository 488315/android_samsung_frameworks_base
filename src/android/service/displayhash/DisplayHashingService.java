package android.service.displayhash;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallback;
import android.service.displayhash.DisplayHashingService;
import android.service.displayhash.IDisplayHashingService;
import android.view.displayhash.DisplayHash;
import android.view.displayhash.DisplayHashResultCallback;
import android.view.displayhash.VerifiedDisplayHash;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Map;
import java.util.function.BiConsumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class DisplayHashingService extends Service {
    public static final String EXTRA_INTERVAL_BETWEEN_REQUESTS = "android.service.displayhash.extra.INTERVAL_BETWEEN_REQUESTS";
    public static final String EXTRA_VERIFIED_DISPLAY_HASH = "android.service.displayhash.extra.VERIFIED_DISPLAY_HASH";

    @SystemApi
    public static final String SERVICE_INTERFACE = "android.service.displayhash.DisplayHashingService";
    private Handler mHandler;
    private DisplayHashingServiceWrapper mWrapper;

    public abstract void onGenerateDisplayHash(byte[] bArr, HardwareBuffer hardwareBuffer, Rect rect, String str, DisplayHashResultCallback displayHashResultCallback);

    public abstract Map<String, DisplayHashParams> onGetDisplayHashAlgorithms();

    public abstract int onGetIntervalBetweenRequestsMillis();

    public abstract VerifiedDisplayHash onVerifyDisplayHash(byte[] bArr, DisplayHash displayHash);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new DisplayHashingServiceWrapper();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyDisplayHash(byte[] bArr, DisplayHash displayHash, RemoteCallback remoteCallback) {
        VerifiedDisplayHash onVerifyDisplayHash = onVerifyDisplayHash(bArr, displayHash);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_VERIFIED_DISPLAY_HASH, onVerifyDisplayHash);
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDisplayHashAlgorithms(RemoteCallback remoteCallback) {
        Map<String, DisplayHashParams> onGetDisplayHashAlgorithms = onGetDisplayHashAlgorithms();
        Bundle bundle = new Bundle();
        for (Map.Entry<String, DisplayHashParams> entry : onGetDisplayHashAlgorithms.entrySet()) {
            bundle.putParcelable(entry.getKey(), entry.getValue());
        }
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDurationBetweenRequestsMillis(RemoteCallback remoteCallback) {
        int onGetIntervalBetweenRequestsMillis = onGetIntervalBetweenRequestsMillis();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_INTERVAL_BETWEEN_REQUESTS, onGetIntervalBetweenRequestsMillis);
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DisplayHashingServiceWrapper extends IDisplayHashingService.Stub {
        private DisplayHashingServiceWrapper() {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void generateDisplayHash(byte[] bArr, HardwareBuffer hardwareBuffer, Rect rect, String str, final RemoteCallback remoteCallback) {
            DisplayHashingService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                    ((DisplayHashingService) obj).onGenerateDisplayHash((byte[]) obj2, (HardwareBuffer) obj3, (Rect) obj4, (String) obj5, (DisplayHashingService.DisplayHashingServiceWrapper.AnonymousClass1) obj6);
                }
            }, DisplayHashingService.this, bArr, hardwareBuffer, rect, str, new DisplayHashResultCallback(this) { // from class: android.service.displayhash.DisplayHashingService.DisplayHashingServiceWrapper.1
                @Override // android.view.displayhash.DisplayHashResultCallback
                public void onDisplayHashResult(DisplayHash displayHash) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DisplayHashResultCallback.EXTRA_DISPLAY_HASH, displayHash);
                    remoteCallback.sendResult(bundle);
                }

                @Override // android.view.displayhash.DisplayHashResultCallback
                public void onDisplayHashError(int i) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(DisplayHashResultCallback.EXTRA_DISPLAY_HASH_ERROR_CODE, i);
                    remoteCallback.sendResult(bundle);
                }
            }));
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void verifyDisplayHash(byte[] bArr, DisplayHash displayHash, RemoteCallback remoteCallback) {
            DisplayHashingService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((DisplayHashingService) obj).verifyDisplayHash((byte[]) obj2, (DisplayHash) obj3, (RemoteCallback) obj4);
                }
            }, DisplayHashingService.this, bArr, displayHash, remoteCallback));
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getDisplayHashAlgorithms(RemoteCallback remoteCallback) {
            DisplayHashingService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((DisplayHashingService) obj).getDisplayHashAlgorithms((RemoteCallback) obj2);
                }
            }, DisplayHashingService.this, remoteCallback));
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getIntervalBetweenRequestsMillis(RemoteCallback remoteCallback) {
            DisplayHashingService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((DisplayHashingService) obj).getDurationBetweenRequestsMillis((RemoteCallback) obj2);
                }
            }, DisplayHashingService.this, remoteCallback));
        }
    }
}
