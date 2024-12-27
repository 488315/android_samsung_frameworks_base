package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.RampSegment;
import android.os.vibrator.StepSegment;
import android.util.IndentingPrintWriter;
import android.util.SparseBooleanArray;
import android.util.proto.ProtoOutputStream;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Vibration {
    public final CallerInfo callerInfo;
    public final IBinder callerToken;
    public final long id;
    public final VibrationStats stats;
    public static final DateTimeFormatter DEBUG_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    public static final DateTimeFormatter DEBUG_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    public static final AtomicInteger sNextVibrationId = new AtomicInteger(1);

    public final class CallerInfo {
        public final VibrationAttributes attrs;
        public final int deviceId;
        public final String opPkg;
        public final String reason;
        public final int uid;

        public CallerInfo(
                VibrationAttributes vibrationAttributes, int i, int i2, String str, String str2) {
            Objects.requireNonNull(vibrationAttributes);
            this.attrs = vibrationAttributes;
            this.uid = i;
            this.deviceId = i2;
            this.opPkg = str;
            this.reason = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CallerInfo)) {
                return false;
            }
            CallerInfo callerInfo = (CallerInfo) obj;
            return Objects.equals(this.attrs, callerInfo.attrs)
                    && this.uid == callerInfo.uid
                    && this.deviceId == callerInfo.deviceId
                    && Objects.equals(this.opPkg, callerInfo.opPkg)
                    && Objects.equals(this.reason, callerInfo.reason);
        }

        public final int hashCode() {
            return Objects.hash(
                    this.attrs,
                    Integer.valueOf(this.uid),
                    Integer.valueOf(this.deviceId),
                    this.opPkg,
                    this.reason);
        }

        public final String toString() {
            return "CallerInfo{ uid="
                    + this.uid
                    + ", opPkg="
                    + this.opPkg
                    + ", deviceId="
                    + this.deviceId
                    + ", attrs="
                    + this.attrs
                    + ", reason="
                    + this.reason
                    + '}';
        }
    }

    public final class DebugInfo {
        public final float mAdaptiveScale;
        public final CallerInfo mCallerInfo;
        public final long mCreateTime;
        public final long mDurationMs;
        public final long mEndTime;
        public final CombinedVibration mOriginalEffect;
        public final CombinedVibration mPlayedEffect;
        public final int mScaleLevel;
        public final long mStartTime;
        public final Status mStatus;

        public DebugInfo(
                Status status,
                VibrationStats vibrationStats,
                CombinedVibration combinedVibration,
                CombinedVibration combinedVibration2,
                int i,
                float f,
                CallerInfo callerInfo) {
            Objects.requireNonNull(callerInfo);
            this.mCreateTime = vibrationStats.mCreateTimeDebug;
            this.mStartTime = vibrationStats.mStartTimeDebug;
            this.mEndTime = vibrationStats.mEndTimeDebug;
            long j = vibrationStats.mEndUptimeMillis;
            this.mDurationMs = j > 0 ? j - vibrationStats.mCreateUptimeMillis : -1L;
            this.mPlayedEffect = combinedVibration;
            this.mOriginalEffect = combinedVibration2;
            this.mScaleLevel = i;
            this.mAdaptiveScale = f;
            this.mCallerInfo = callerInfo;
            this.mStatus = status;
        }

        public static void dumpEffect(
                ProtoOutputStream protoOutputStream, long j, CombinedVibration combinedVibration) {
            CombinedVibration.Sequential combine =
                    CombinedVibration.startSequential().addNext(combinedVibration).combine();
            long start = protoOutputStream.start(j);
            for (int i = 0; i < combine.getEffects().size(); i++) {
                CombinedVibration.Mono mono = (CombinedVibration) combine.getEffects().get(i);
                if (mono instanceof CombinedVibration.Mono) {
                    long start2 = protoOutputStream.start(2246267895809L);
                    dumpEffect(protoOutputStream, mono.getEffect());
                    protoOutputStream.end(start2);
                } else if (mono instanceof CombinedVibration.Stereo) {
                    CombinedVibration.Stereo stereo = (CombinedVibration.Stereo) mono;
                    long start3 = protoOutputStream.start(2246267895809L);
                    for (int i2 = 0; i2 < stereo.getEffects().size(); i2++) {
                        protoOutputStream.write(2220498092034L, stereo.getEffects().keyAt(i2));
                        dumpEffect(
                                protoOutputStream,
                                (VibrationEffect) stereo.getEffects().valueAt(i2));
                    }
                    protoOutputStream.end(start3);
                }
                protoOutputStream.write(
                        2220498092034L, ((Integer) combine.getDelays().get(i)).intValue());
            }
            protoOutputStream.end(start);
        }

        public static void dumpEffect(
                ProtoOutputStream protoOutputStream, VibrationEffect vibrationEffect) {
            long start = protoOutputStream.start(2246267895809L);
            VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
            for (StepSegment stepSegment : composed.getSegments()) {
                long start2 = protoOutputStream.start(1146756268033L);
                if (stepSegment instanceof StepSegment) {
                    StepSegment stepSegment2 = stepSegment;
                    long start3 = protoOutputStream.start(1146756268035L);
                    protoOutputStream.write(1120986464257L, stepSegment2.getDuration());
                    protoOutputStream.write(1108101562370L, stepSegment2.getAmplitude());
                    protoOutputStream.write(1108101562371L, stepSegment2.getFrequencyHz());
                    protoOutputStream.end(start3);
                } else if (stepSegment instanceof RampSegment) {
                    RampSegment rampSegment = (RampSegment) stepSegment;
                    long start4 = protoOutputStream.start(1146756268036L);
                    protoOutputStream.write(1120986464257L, rampSegment.getDuration());
                    protoOutputStream.write(1108101562370L, rampSegment.getStartAmplitude());
                    protoOutputStream.write(1108101562371L, rampSegment.getEndAmplitude());
                    protoOutputStream.write(1108101562372L, rampSegment.getStartFrequencyHz());
                    protoOutputStream.write(1108101562373L, rampSegment.getEndFrequencyHz());
                    protoOutputStream.end(start4);
                } else if (stepSegment instanceof PrebakedSegment) {
                    PrebakedSegment prebakedSegment = (PrebakedSegment) stepSegment;
                    long start5 = protoOutputStream.start(1146756268033L);
                    protoOutputStream.write(1120986464257L, prebakedSegment.getEffectId());
                    protoOutputStream.write(1120986464258L, prebakedSegment.getEffectStrength());
                    protoOutputStream.write(1120986464259L, prebakedSegment.shouldFallback());
                    protoOutputStream.end(start5);
                } else if (stepSegment instanceof PrimitiveSegment) {
                    PrimitiveSegment primitiveSegment = (PrimitiveSegment) stepSegment;
                    long start6 = protoOutputStream.start(1146756268034L);
                    protoOutputStream.write(1120986464257L, primitiveSegment.getPrimitiveId());
                    protoOutputStream.write(1108101562370L, primitiveSegment.getScale());
                    protoOutputStream.write(1120986464259L, primitiveSegment.getDelay());
                    protoOutputStream.end(start6);
                }
                protoOutputStream.end(start2);
            }
            protoOutputStream.write(1120986464258L, composed.getRepeatIndex());
            protoOutputStream.end(start);
        }

        public static String formatTime(long j, boolean z) {
            return (z ? Vibration.DEBUG_DATE_TIME_FORMATTER : Vibration.DEBUG_TIME_FORMATTER)
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochMilli(j));
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Vibration:");
            indentingPrintWriter.increaseIndent();
            StringBuilder sb = new StringBuilder("status = ");
            String name = this.mStatus.name();
            Locale locale = Locale.ROOT;
            sb.append(name.toLowerCase(locale));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("durationMs = " + this.mDurationMs);
            indentingPrintWriter.println("createTime = " + formatTime(this.mCreateTime, true));
            indentingPrintWriter.println("startTime = " + formatTime(this.mStartTime, true));
            StringBuilder sb2 = new StringBuilder("endTime = ");
            long j = this.mEndTime;
            sb2.append(j == 0 ? null : formatTime(j, true));
            indentingPrintWriter.println(sb2.toString());
            indentingPrintWriter.println("playedEffect = " + this.mPlayedEffect);
            indentingPrintWriter.println("originalEffect = " + this.mOriginalEffect);
            indentingPrintWriter.println(
                    "scale = " + VibrationScaler.scaleLevelToString(this.mScaleLevel));
            indentingPrintWriter.println(
                    "adaptiveScale = "
                            .concat(
                                    String.format(
                                            locale, "%.2f", Float.valueOf(this.mAdaptiveScale))));
            indentingPrintWriter.println("callerInfo = " + this.mCallerInfo);
            indentingPrintWriter.decreaseIndent();
        }

        public final void dump(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, this.mStartTime);
            protoOutputStream.write(1112396529666L, this.mEndTime);
            protoOutputStream.write(1112396529671L, this.mDurationMs);
            protoOutputStream.write(1159641169928L, this.mStatus.ordinal());
            long start2 = protoOutputStream.start(1146756268037L);
            VibrationAttributes vibrationAttributes = this.mCallerInfo.attrs;
            protoOutputStream.write(1120986464257L, vibrationAttributes.getUsage());
            protoOutputStream.write(1120986464258L, vibrationAttributes.getAudioUsage());
            protoOutputStream.write(1120986464260L, vibrationAttributes.getCategory());
            protoOutputStream.write(1120986464259L, vibrationAttributes.getFlags());
            protoOutputStream.end(start2);
            CombinedVibration combinedVibration = this.mPlayedEffect;
            if (combinedVibration != null) {
                dumpEffect(protoOutputStream, 1146756268035L, combinedVibration);
            }
            CombinedVibration combinedVibration2 = this.mOriginalEffect;
            if (combinedVibration2 != null) {
                dumpEffect(protoOutputStream, 1146756268036L, combinedVibration2);
            }
            protoOutputStream.end(start);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("createTime: ");
            sb.append(formatTime(this.mCreateTime, true));
            sb.append(", startTime: ");
            sb.append(formatTime(this.mStartTime, true));
            sb.append(", endTime: ");
            long j = this.mEndTime;
            sb.append(j == 0 ? null : formatTime(j, true));
            sb.append(", durationMs: ");
            sb.append(this.mDurationMs);
            sb.append(", status: ");
            String name = this.mStatus.name();
            Locale locale = Locale.ROOT;
            sb.append(name.toLowerCase(locale));
            sb.append(", playedEffect: ");
            sb.append(this.mPlayedEffect);
            sb.append(", originalEffect: ");
            sb.append(this.mOriginalEffect);
            sb.append(", scaleLevel: ");
            sb.append(VibrationScaler.scaleLevelToString(this.mScaleLevel));
            sb.append(", adaptiveScale: ");
            sb.append(String.format(locale, "%.2f", Float.valueOf(this.mAdaptiveScale)));
            sb.append(", callerInfo: ");
            sb.append(this.mCallerInfo);
            return sb.toString();
        }
    }

    public final class EndInfo {
        public final CallerInfo endedBy;
        public final Status status;

        public EndInfo(Status status, CallerInfo callerInfo) {
            this.status = status;
            this.endedBy = callerInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EndInfo)) {
                return false;
            }
            EndInfo endInfo = (EndInfo) obj;
            return Objects.equals(this.endedBy, endInfo.endedBy) && this.status == endInfo.status;
        }

        public final int hashCode() {
            return Objects.hash(this.status, this.endedBy);
        }

        public final String toString() {
            return "EndInfo{status=" + this.status + ", endedBy=" + this.endedBy + '}';
        }
    }

    enum Status {
        /* JADX INFO: Fake field, exist only in values array */
        EF0("UNKNOWN"),
        RUNNING("RUNNING"),
        FINISHED("FINISHED"),
        FINISHED_UNEXPECTED("FINISHED_UNEXPECTED"),
        FORWARDED_TO_INPUT_DEVICES("FORWARDED_TO_INPUT_DEVICES"),
        CANCELLED_BINDER_DIED("CANCELLED_BINDER_DIED"),
        CANCELLED_BY_SCREEN_OFF("CANCELLED_BY_SCREEN_OFF"),
        CANCELLED_BY_SETTINGS_UPDATE("CANCELLED_BY_SETTINGS_UPDATE"),
        CANCELLED_BY_USER("CANCELLED_BY_USER"),
        CANCELLED_BY_UNKNOWN_REASON("CANCELLED_BY_UNKNOWN_REASON"),
        CANCELLED_SUPERSEDED("CANCELLED_SUPERSEDED"),
        IGNORED_ERROR_APP_OPS("IGNORED_ERROR_APP_OPS"),
        IGNORED_ERROR_CANCELLING("IGNORED_ERROR_CANCELLING"),
        IGNORED_ERROR_SCHEDULING("IGNORED_ERROR_SCHEDULING"),
        IGNORED_ERROR_TOKEN("IGNORED_ERROR_TOKEN"),
        IGNORED_APP_OPS("IGNORED_APP_OPS"),
        IGNORED_BACKGROUND("IGNORED_BACKGROUND"),
        IGNORED_MISSING_PERMISSION("IGNORED_MISSING_PERMISSION"),
        IGNORED_UNSUPPORTED("IGNORED_UNSUPPORTED"),
        IGNORED_FOR_EXTERNAL("IGNORED_FOR_EXTERNAL"),
        IGNORED_FOR_HIGHER_IMPORTANCE("IGNORED_FOR_HIGHER_IMPORTANCE"),
        IGNORED_FOR_ONGOING("IGNORED_FOR_ONGOING"),
        IGNORED_FOR_POWER("IGNORED_FOR_POWER"),
        IGNORED_FOR_RINGER_MODE("IGNORED_FOR_RINGER_MODE"),
        IGNORED_FOR_SETTINGS("IGNORED_FOR_SETTINGS"),
        IGNORED_SUPERSEDED("IGNORED_SUPERSEDED"),
        IGNORED_FROM_VIRTUAL_DEVICE("IGNORED_FROM_VIRTUAL_DEVICE"),
        IGNORED_ON_WIRELESS_CHARGER("IGNORED_ON_WIRELESS_CHARGER"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("CANCELLED_ACH_REPEATED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("CANCELLED_ACH_NON_REPEATED"),
        CANCELLED_SERVICE_RECOVERED("CANCELLED_SERVICE_RECOVERED"),
        CANCELLED_BY_DISABLED_NOTIFICATION("CANCELLED_BY_DISABLED_NOTIFICATION");

        private final int mProtoEnumValue;

        Status(String str) {
            this.mProtoEnumValue = r2;
        }

        public final int getProtoEnumValue() {
            return this.mProtoEnumValue;
        }
    }

    public Vibration(IBinder iBinder, CallerInfo callerInfo) {
        VibrationStats vibrationStats = new VibrationStats();
        vibrationStats.mVibratorEffectsUsed = new SparseBooleanArray();
        vibrationStats.mVibratorPrimitivesUsed = new SparseBooleanArray();
        vibrationStats.mCreateUptimeMillis = SystemClock.uptimeMillis();
        vibrationStats.mCreateTimeDebug = System.currentTimeMillis();
        vibrationStats.mEndedByUid = -1;
        vibrationStats.mEndedByUsage = -1;
        vibrationStats.mInterruptedUsage = -1;
        this.stats = vibrationStats;
        Objects.requireNonNull(iBinder);
        this.id = sNextVibrationId.getAndIncrement();
        this.callerToken = iBinder;
        this.callerInfo = callerInfo;
    }

    public abstract boolean isRepeating();
}
