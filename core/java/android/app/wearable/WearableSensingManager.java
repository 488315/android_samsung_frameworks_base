package android.app.wearable;

import android.annotation.SystemApi;
import android.content.Context;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.ParcelFileDescriptor;
import android.p009os.PersistableBundle;
import android.p009os.RemoteCallback;
import android.p009os.RemoteException;
import android.p009os.SharedMemory;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes.dex */
public class WearableSensingManager {
  public static final int STATUS_ACCESS_DENIED = 5;
  public static final String STATUS_RESPONSE_BUNDLE_KEY =
      "android.app.wearable.WearableSensingStatusBundleKey";
  public static final int STATUS_SERVICE_UNAVAILABLE = 3;
  public static final int STATUS_SUCCESS = 1;
  public static final int STATUS_UNKNOWN = 0;
  public static final int STATUS_UNSUPPORTED = 2;
  public static final int STATUS_WEARABLE_UNAVAILABLE = 4;
  private final Context mContext;
  private final IWearableSensingManager mService;

  public @interface StatusCode {}

  public WearableSensingManager(Context context, IWearableSensingManager service) {
    this.mContext = context;
    this.mService = service;
  }

  public void provideDataStream(
      ParcelFileDescriptor parcelFileDescriptor,
      final Executor executor,
      final Consumer<Integer> statusConsumer) {
    try {
      RemoteCallback callback =
          new RemoteCallback(
              new RemoteCallback
                  .OnResultListener() { // from class:
                                        // android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda0
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                  WearableSensingManager.lambda$provideDataStream$1(
                      executor, statusConsumer, bundle);
                }
              });
      this.mService.provideDataStream(parcelFileDescriptor, callback);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static /* synthetic */ void lambda$provideDataStream$1(
      Executor executor, final Consumer statusConsumer, Bundle result) {
    final int status = result.getInt("android.app.wearable.WearableSensingStatusBundleKey");
    long identity = Binder.clearCallingIdentity();
    try {
      executor.execute(
          new Runnable() { // from class:
                           // android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              statusConsumer.accept(Integer.valueOf(status));
            }
          });
    } finally {
      Binder.restoreCallingIdentity(identity);
    }
  }

  public void provideData(
      PersistableBundle data,
      SharedMemory sharedMemory,
      final Executor executor,
      final Consumer<Integer> statusConsumer) {
    try {
      RemoteCallback callback =
          new RemoteCallback(
              new RemoteCallback
                  .OnResultListener() { // from class:
                                        // android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda2
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                  WearableSensingManager.lambda$provideData$3(executor, statusConsumer, bundle);
                }
              });
      this.mService.provideData(data, sharedMemory, callback);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static /* synthetic */ void lambda$provideData$3(
      Executor executor, final Consumer statusConsumer, Bundle result) {
    final int status = result.getInt("android.app.wearable.WearableSensingStatusBundleKey");
    long identity = Binder.clearCallingIdentity();
    try {
      executor.execute(
          new Runnable() { // from class:
                           // android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              statusConsumer.accept(Integer.valueOf(status));
            }
          });
    } finally {
      Binder.restoreCallingIdentity(identity);
    }
  }
}
