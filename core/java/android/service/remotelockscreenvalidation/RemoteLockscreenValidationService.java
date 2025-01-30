package android.service.remotelockscreenvalidation;

import android.annotation.SystemApi;
import android.app.RemoteLockscreenValidationResult;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

@SystemApi
/* loaded from: classes3.dex */
public abstract class RemoteLockscreenValidationService extends Service {
  public static final String SERVICE_INTERFACE =
      "android.service.remotelockscreenvalidation.RemoteLockscreenValidationService";
  private static final String TAG = RemoteLockscreenValidationService.class.getSimpleName();
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private final IRemoteLockscreenValidationService mInterface =
      new IRemoteLockscreenValidationService
          .Stub() { // from class:
                    // android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.1
        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
        public void validateLockscreenGuess(
            byte[] guess, final IRemoteLockscreenValidationCallback callback) {
          RemoteLockscreenValidationService.this.mHandler.sendMessage(
              PooledLambda.obtainMessage(
                  new TriConsumer() { // from class:
                                      // android.service.remotelockscreenvalidation.RemoteLockscreenValidationService$1$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                      ((RemoteLockscreenValidationService) obj)
                          .onValidateLockscreenGuess(
                              (byte[]) obj2,
                              (RemoteLockscreenValidationService.BinderC27711.AnonymousClass1)
                                  obj3);
                    }
                  },
                  RemoteLockscreenValidationService.this,
                  guess,
                  new OutcomeReceiver<
                      RemoteLockscreenValidationResult,
                      Exception>() { // from class:
                                     // android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.1.1
                    @Override // android.os.OutcomeReceiver
                    public void onResult(RemoteLockscreenValidationResult result) {
                      try {
                        callback.onSuccess(result);
                      } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                      }
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(Exception e) {
                      try {
                        callback.onFailure(e.getMessage());
                      } catch (RemoteException ex) {
                        ex.rethrowFromSystemServer();
                      }
                    }
                  }));
        }
      };

  public abstract void onValidateLockscreenGuess(
      byte[] bArr, OutcomeReceiver<RemoteLockscreenValidationResult, Exception> outcomeReceiver);

  @Override // android.app.Service
  public final IBinder onBind(Intent intent) {
    if (!SERVICE_INTERFACE.equals(intent.getAction())) {
      Log.m102w(TAG, "Wrong action");
      return null;
    }
    return this.mInterface.asBinder();
  }
}
