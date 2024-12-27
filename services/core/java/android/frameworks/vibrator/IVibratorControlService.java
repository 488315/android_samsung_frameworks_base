package android.frameworks.vibrator;

import android.frameworks.vibrator.IVibratorController;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.SystemClock;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.modules.expresslog.Counter;
import com.android.server.vibrator.VibratorControlService;
import com.android.server.vibrator.VibratorFrameworkStatsLogger;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IVibratorControlService extends IInterface {
    public static final String DESCRIPTOR = "android$frameworks$vibrator$IVibratorControlService".replace('$', '.');

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IVibratorControlService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        public final int getMaxTransactionId() {
            return 16777214;
        }

        public final String getTransactionName(int i) {
            if (i == 1) {
                return "registerVibratorController";
            }
            if (i == 2) {
                return "unregisterVibratorController";
            }
            if (i == 3) {
                return "setVibrationParams";
            }
            if (i == 4) {
                return "clearVibrationParams";
            }
            if (i == 5) {
                return "onRequestVibrationParamsComplete";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IVibratorControlService.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString("eb095ed3034973273898ca9e37bbc72566392b8a");
                return true;
            }
            if (i == 1) {
                IVibratorController asInterface = IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                VibratorControlService vibratorControlService = (VibratorControlService) this;
                Objects.requireNonNull(asInterface);
                synchronized (vibratorControlService.mLock) {
                    vibratorControlService.mVibratorControllerHolder.setVibratorController(asInterface);
                }
            } else if (i == 2) {
                IVibratorController asInterface2 = IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                VibratorControlService vibratorControlService2 = (VibratorControlService) this;
                Objects.requireNonNull(asInterface2);
                synchronized (vibratorControlService2.mLock) {
                    try {
                        IVibratorController iVibratorController = vibratorControlService2.mVibratorControllerHolder.mVibratorController;
                        if (iVibratorController == null) {
                            Slog.w("VibratorControlService", "Received request to unregister IVibratorController = " + asInterface2 + ", but no controller was previously registered. Request Ignored.");
                        } else if (Objects.equals(((IVibratorController.Stub.Proxy) iVibratorController).mRemote, ((IVibratorController.Stub.Proxy) asInterface2).mRemote)) {
                            vibratorControlService2.mVibrationScaler.mAdaptiveHapticsScales.clear();
                            vibratorControlService2.mVibratorControllerHolder.setVibratorController(null);
                            vibratorControlService2.endOngoingRequestVibrationParamsLocked(true);
                        } else {
                            Slog.wtf("VibratorControlService", "Failed to unregister IVibratorController. The provided controller doesn't match the registered one. " + vibratorControlService2);
                        }
                    } finally {
                    }
                }
            } else if (i == 3) {
                VibrationParam[] vibrationParamArr = (VibrationParam[]) parcel.createTypedArray(VibrationParam.CREATOR);
                IVibratorController asInterface3 = IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                VibratorControlService vibratorControlService3 = (VibratorControlService) this;
                Objects.requireNonNull(asInterface3);
                DateTimeFormatter dateTimeFormatter = VibratorControlService.DEBUG_DATE_TIME_FORMATTER;
                if (ArrayUtils.contains(vibrationParamArr, (Object) null)) {
                    throw new IllegalArgumentException("Invalid vibration params received: null values are not permitted.");
                }
                synchronized (vibratorControlService3.mLock) {
                    try {
                        IVibratorController iVibratorController2 = vibratorControlService3.mVibratorControllerHolder.mVibratorController;
                        if (iVibratorController2 == null) {
                            Slog.w("VibratorControlService", "Received request to set VibrationParams for IVibratorController = " + asInterface3 + ", but no controller was previously registered. Request Ignored.");
                        } else if (!Objects.equals(((IVibratorController.Stub.Proxy) iVibratorController2).mRemote, ((IVibratorController.Stub.Proxy) asInterface3).mRemote)) {
                            Slog.wtf("VibratorControlService", "Failed to set new VibrationParams. The provided controller doesn't match the registered one. " + vibratorControlService3);
                        } else if (vibrationParamArr == null) {
                            Slog.d("VibratorControlService", "New vibration params received but are null. New vibration params ignored.");
                        } else {
                            vibratorControlService3.updateAdaptiveHapticsScales(vibrationParamArr);
                            vibratorControlService3.recordUpdateVibrationParams(vibrationParamArr, false);
                        }
                    } finally {
                    }
                }
            } else if (i == 4) {
                int readInt = parcel.readInt();
                IVibratorController asInterface4 = IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ((VibratorControlService) this).clearVibrationParams(readInt, asInterface4);
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IBinder readStrongBinder = parcel.readStrongBinder();
                VibrationParam[] vibrationParamArr2 = (VibrationParam[]) parcel.createTypedArray(VibrationParam.CREATOR);
                parcel.enforceNoDataAvail();
                VibratorControlService vibratorControlService4 = (VibratorControlService) this;
                Objects.requireNonNull(readStrongBinder);
                DateTimeFormatter dateTimeFormatter2 = VibratorControlService.DEBUG_DATE_TIME_FORMATTER;
                if (ArrayUtils.contains(vibrationParamArr2, (Object) null)) {
                    throw new IllegalArgumentException("Invalid vibration params received: null values are not permitted.");
                }
                synchronized (vibratorControlService4.mLock) {
                    try {
                        VibratorControlService.VibrationParamRequest vibrationParamRequest = vibratorControlService4.mVibrationParamRequest;
                        if (vibrationParamRequest == null) {
                            Slog.wtf("VibratorControlService", "New vibration params received but no token was cached in the service. New vibration params ignored.");
                            vibratorControlService4.mStatsLogger.getClass();
                            Counter.logIncrement("vibrator.value_vibration_param_response_ignored");
                        } else if (readStrongBinder.equals(vibrationParamRequest.token)) {
                            long uptimeMillis = SystemClock.uptimeMillis();
                            VibratorControlService.VibrationParamRequest vibrationParamRequest2 = vibratorControlService4.mVibrationParamRequest;
                            long j = uptimeMillis - vibrationParamRequest2.uptimeMs;
                            VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = vibratorControlService4.mStatsLogger;
                            int i3 = vibrationParamRequest2.uid;
                            vibratorFrameworkStatsLogger.getClass();
                            VibratorFrameworkStatsLogger.sVibrationParamRequestLatencyHistogram.logSampleWithUid(i3, j);
                            if (vibrationParamArr2 == null) {
                                Slog.d("VibratorControlService", "New vibration params received but are null. New vibration params ignored.");
                            } else {
                                vibratorControlService4.updateAdaptiveHapticsScales(vibrationParamArr2);
                                vibratorControlService4.endOngoingRequestVibrationParamsLocked(false);
                                vibratorControlService4.recordUpdateVibrationParams(vibrationParamArr2, true);
                            }
                        } else {
                            Slog.w("VibratorControlService", "New vibration params received but the provided token does not match the cached one. New vibration params ignored.");
                            vibratorControlService4.mStatsLogger.getClass();
                            Counter.logIncrement("vibrator.value_vibration_param_response_ignored");
                        }
                    } finally {
                    }
                }
            }
            return true;
        }
    }
}
