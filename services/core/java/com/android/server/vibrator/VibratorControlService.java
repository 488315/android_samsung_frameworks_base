package com.android.server.vibrator;

import android.R;
import android.content.Context;
import android.frameworks.vibrator.IVibratorControlService;
import android.frameworks.vibrator.IVibratorController;
import android.frameworks.vibrator.ScaleParam;
import android.frameworks.vibrator.VibrationParam;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.VibrationAttributes;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public final class VibratorControlService extends IVibratorControlService.Stub {
    public static final DateTimeFormatter DEBUG_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    public final Object mLock;
    public final int[] mRequestVibrationParamsForUsages;
    public final VibratorFrameworkStatsLogger mStatsLogger;
    public VibrationParamRequest mVibrationParamRequest;
    public final VibrationParamsRecords mVibrationParamsRecords;
    public final VibrationScaler mVibrationScaler;
    public final VibratorControllerHolder mVibratorControllerHolder;

    public final class VibrationParamRequest {
        public final int uid;
        public final CompletableFuture future = new CompletableFuture();
        public final IBinder token = new Binder();
        public final long uptimeMs = SystemClock.uptimeMillis();

        public VibrationParamRequest(int i) {
            this.uid = i;
        }
    }

    public final class VibrationParamsRecords extends GroupedAggregatedLogRecords {

        final class Operation {
            public static final /* synthetic */ Operation[] $VALUES;
            public static final Operation CLEAR;
            public static final Operation PULL;
            public static final Operation PUSH;

            static {
                Operation operation = new Operation("PULL", 0);
                PULL = operation;
                Operation operation2 = new Operation("PUSH", 1);
                PUSH = operation2;
                Operation operation3 = new Operation("CLEAR", 2);
                CLEAR = operation3;
                $VALUES = new Operation[] {operation, operation2, operation3};
            }

            public static Operation valueOf(String str) {
                return (Operation) Enum.valueOf(Operation.class, str);
            }

            public static Operation[] values() {
                return (Operation[]) $VALUES.clone();
            }
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        public final synchronized void dumpGroupHeader(
                IndentingPrintWriter indentingPrintWriter, int i) {
            try {
                if (i == 0) {
                    indentingPrintWriter.println("SCALE:");
                } else {
                    indentingPrintWriter.println("UNKNOWN:");
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        public final synchronized long findGroupKeyProtoFieldId(int i) {
            return 2246267895836L;
        }
    }

    public final class VibrationScaleParamRecord
            implements GroupedAggregatedLogRecords.SingleLogRecord {
        public final long mCreateTime;
        public final VibrationParamsRecords.Operation mOperation;
        public final float mScale;
        public final int mTypesMask;

        public VibrationScaleParamRecord(
                VibrationParamsRecords.Operation operation, long j, int i, float f) {
            this.mOperation = operation;
            this.mCreateTime = j;
            this.mTypesMask = i;
            this.mScale = f;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            Locale locale = Locale.ROOT;
            String format =
                    VibratorControlService.DEBUG_DATE_TIME_FORMATTER
                            .withZone(ZoneId.systemDefault())
                            .format(Instant.ofEpochMilli(this.mCreateTime));
            String lowerCase = this.mOperation.name().toLowerCase(locale);
            float f = this.mScale;
            String format2 = f == -1.0f ? "" : String.format(locale, "%.2f", Float.valueOf(f));
            int i = this.mTypesMask;
            String binaryString = Long.toBinaryString(i);
            StringBuilder sb = new StringBuilder();
            int[] mapFromAdaptiveVibrationTypeToVibrationUsages =
                    VibratorControlService.mapFromAdaptiveVibrationTypeToVibrationUsages(i);
            for (int i2 = 0; i2 < mapFromAdaptiveVibrationTypeToVibrationUsages.length; i2++) {
                if (i2 > 0) {
                    sb.append(", ");
                }
                sb.append(
                        VibrationAttributes.usageToString(
                                mapFromAdaptiveVibrationTypeToVibrationUsages[i2]));
            }
            indentingPrintWriter.println(
                    String.format(
                            locale,
                            "%s | %6s | scale: %5s | typesMask: %6s | usages: %s",
                            format,
                            lowerCase,
                            format2,
                            binaryString,
                            sb.toString()));
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final void dump(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529666L, this.mCreateTime);
            protoOutputStream.write(
                    1133871366147L, this.mOperation == VibrationParamsRecords.Operation.PULL);
            long start2 = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1120986464257L, this.mTypesMask);
            protoOutputStream.write(1108101562370L, this.mScale);
            protoOutputStream.end(start2);
            protoOutputStream.end(start);
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final long getCreateUptimeMs() {
            return this.mCreateTime;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final int getGroupKey() {
            return 0;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public final boolean mayAggregate(
                GroupedAggregatedLogRecords.SingleLogRecord singleLogRecord) {
            if (!(singleLogRecord instanceof VibrationScaleParamRecord)) {
                return false;
            }
            VibrationScaleParamRecord vibrationScaleParamRecord =
                    (VibrationScaleParamRecord) singleLogRecord;
            return this.mTypesMask == vibrationScaleParamRecord.mTypesMask
                    && this.mOperation == vibrationScaleParamRecord.mOperation;
        }
    }

    public VibratorControlService(
            Context context,
            VibratorControllerHolder vibratorControllerHolder,
            VibrationScaler vibrationScaler,
            VibrationSettings vibrationSettings,
            VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger,
            Object obj) {
        markVintfStability();
        attachInterface(this, IVibratorControlService.DESCRIPTOR);
        this.mVibrationParamRequest = null;
        this.mVibratorControllerHolder = vibratorControllerHolder;
        this.mVibrationScaler = vibrationScaler;
        this.mStatsLogger = vibratorFrameworkStatsLogger;
        this.mLock = obj;
        this.mRequestVibrationParamsForUsages =
                vibrationSettings.mVibrationConfig.getRequestVibrationParamsForUsages();
        this.mVibrationParamsRecords =
                new VibrationParamsRecords(
                        context.getResources()
                                .getInteger(R.integer.config_shortPressOnSleepBehavior),
                        context.getResources()
                                .getInteger(R.integer.config_shortPressOnSettingsBehavior));
    }

    public static int[] mapFromAdaptiveVibrationTypeToVibrationUsages(int i) {
        IntArray intArray = new IntArray(15);
        if ((i & 1) != 0) {
            intArray.add(17);
        }
        if ((i & 2) != 0) {
            intArray.add(49);
            intArray.add(65);
        }
        if ((i & 4) != 0) {
            intArray.add(33);
        }
        if ((i & 16) != 0) {
            intArray.add(19);
            intArray.add(0);
        }
        if ((i & 8) != 0) {
            intArray.add(18);
            intArray.add(50);
        }
        return intArray.toArray();
    }

    public final void clearVibrationParams(int i, IVibratorController iVibratorController) {
        Objects.requireNonNull(iVibratorController);
        synchronized (this.mLock) {
            try {
                IVibratorController iVibratorController2 =
                        this.mVibratorControllerHolder.mVibratorController;
                if (iVibratorController2 == null) {
                    Slog.w(
                            "VibratorControlService",
                            "Received request to clear VibrationParams for IVibratorController = "
                                    + iVibratorController
                                    + ", but no controller was previously registered. Request"
                                    + " Ignored.");
                    return;
                }
                if (Objects.equals(
                        ((IVibratorController.Stub.Proxy) iVibratorController2).mRemote,
                        ((IVibratorController.Stub.Proxy) iVibratorController).mRemote)) {
                    updateAdaptiveHapticsScales(-1.0f, i);
                    this.mVibrationParamsRecords.add(
                            new VibrationScaleParamRecord(
                                    VibrationParamsRecords.Operation.CLEAR,
                                    SystemClock.uptimeMillis(),
                                    i,
                                    -1.0f));
                } else {
                    Slog.wtf(
                            "VibratorControlService",
                            "Failed to clear VibrationParams. The provided controller doesn't match"
                                + " the registered one. "
                                    + this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void endOngoingRequestVibrationParamsLocked(boolean z) {
        VibrationParamRequest vibrationParamRequest = this.mVibrationParamRequest;
        if (vibrationParamRequest != null) {
            if (z) {
                vibrationParamRequest.future.cancel(true);
            } else {
                vibrationParamRequest.future.complete(null);
            }
        }
        this.mVibrationParamRequest = null;
    }

    public IBinder getRequestVibrationParamsToken() {
        IBinder iBinder;
        synchronized (this.mLock) {
            VibrationParamRequest vibrationParamRequest = this.mVibrationParamRequest;
            iBinder = vibrationParamRequest == null ? null : vibrationParamRequest.token;
        }
        return iBinder;
    }

    public final void recordUpdateVibrationParams(VibrationParam[] vibrationParamArr, boolean z) {
        VibrationParamsRecords.Operation operation =
                z ? VibrationParamsRecords.Operation.PULL : VibrationParamsRecords.Operation.PUSH;
        long uptimeMillis = SystemClock.uptimeMillis();
        for (VibrationParam vibrationParam : vibrationParamArr) {
            if (vibrationParam._tag != 0) {
                Slog.w(
                        "VibratorControlService",
                        "Unsupported vibration param ignored from dumpsys records: "
                                + vibrationParam);
            } else {
                ScaleParam scale = vibrationParam.getScale();
                this.mVibrationParamsRecords.add(
                        new VibrationScaleParamRecord(
                                operation, uptimeMillis, scale.typesMask, scale.scale));
            }
        }
    }

    public final void updateAdaptiveHapticsScales(float f, int i) {
        this.mStatsLogger.getClass();
        VibratorFrameworkStatsLogger.sVibrationParamScaleHistogram.logSample(f);
        for (int i2 : mapFromAdaptiveVibrationTypeToVibrationUsages(i)) {
            if (f == -1.0f) {
                this.mVibrationScaler.mAdaptiveHapticsScales.remove(i2);
            } else {
                this.mVibrationScaler.mAdaptiveHapticsScales.put(i2, Float.valueOf(f));
            }
        }
    }

    public final void updateAdaptiveHapticsScales(VibrationParam[] vibrationParamArr) {
        for (VibrationParam vibrationParam : vibrationParamArr) {
            if (vibrationParam._tag != 0) {
                Slog.e("VibratorControlService", "Unsupported vibration param: " + vibrationParam);
            } else {
                ScaleParam scale = vibrationParam.getScale();
                updateAdaptiveHapticsScales(scale.scale, scale.typesMask);
            }
        }
    }
}
