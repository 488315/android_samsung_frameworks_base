package com.android.server.vcn.routeselection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.IpSecTransformState;
import android.net.Network;
import android.net.vcn.Flags;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.OutcomeReceiver;
import android.os.PowerManager;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.util.PersistableBundleUtils;

import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class IpSecPacketLossDetector extends NetworkMetricMonitor {
    static final int IPSEC_PACKET_LOSS_PERCENT_THRESHOLD_DISABLE_DETECTOR = -1;
    static final int MIN_VALID_EXPECTED_RX_PACKET_NUM = 10;
    public final Object mCancellationToken;
    public final ConnectivityManager mConnectivityManager;
    public final Handler mHandler;
    public NetworkMetricMonitor.IpSecTransformWrapper mInboundTransform;
    public IpSecTransformState mLastIpSecTransformState;
    public int mMaxSeqNumIncreasePerSecond;
    public final PacketLossCalculator mPacketLossCalculator;
    public int mPacketLossRatePercentThreshold;
    public long mPollIpSecStateIntervalMs;
    public final PowerManager mPowerManager;

    public class Dependencies {}

    public final class IpSecTransformStateReceiver implements OutcomeReceiver {
        public IpSecTransformStateReceiver() {}

        @Override // android.os.OutcomeReceiver
        public final void onError(Throwable th) {
            IpSecPacketLossDetector.this.mVcnContext.ensureRunningOnLooperThread();
            IpSecPacketLossDetector ipSecPacketLossDetector = IpSecPacketLossDetector.this;
            String str = "TransformStateReceiver#onError " + ((RuntimeException) th).toString();
            Slog.w(
                    ipSecPacketLossDetector.getClassName(),
                    ipSecPacketLossDetector.getLogPrefix() + str);
            VcnManagementService.LOCAL_LOG.log(
                    "[WARN ] "
                            + ipSecPacketLossDetector.getClassName()
                            + ipSecPacketLossDetector.getLogPrefix()
                            + str);
        }

        @Override // android.os.OutcomeReceiver
        public final void onResult(Object obj) {
            int i;
            PacketLossCalculationResult packetLossCalculationResult;
            boolean z;
            long cardinality;
            IpSecTransformState ipSecTransformState = (IpSecTransformState) obj;
            IpSecPacketLossDetector.this.mVcnContext.ensureRunningOnLooperThread();
            IpSecPacketLossDetector ipSecPacketLossDetector = IpSecPacketLossDetector.this;
            if (ipSecPacketLossDetector.mIsStarted) {
                IpSecTransformState ipSecTransformState2 =
                        ipSecPacketLossDetector.mLastIpSecTransformState;
                if (ipSecTransformState2 == null) {
                    ipSecPacketLossDetector.mLastIpSecTransformState = ipSecTransformState;
                    return;
                }
                PacketLossCalculator packetLossCalculator =
                        ipSecPacketLossDetector.mPacketLossCalculator;
                int i2 = ipSecPacketLossDetector.mMaxSeqNumIncreasePerSecond;
                ipSecPacketLossDetector.getLogPrefix();
                packetLossCalculator.getClass();
                ipSecTransformState2.getRxHighestSequenceNumber();
                ipSecTransformState2.getPacketCount();
                BitSet.valueOf(ipSecTransformState2.getReplayBitmap()).cardinality();
                ipSecTransformState.getRxHighestSequenceNumber();
                ipSecTransformState.getPacketCount();
                BitSet.valueOf(ipSecTransformState.getReplayBitmap()).cardinality();
                int length = ipSecTransformState2.getReplayBitmap().length * 8;
                long rxHighestSequenceNumber = ipSecTransformState2.getRxHighestSequenceNumber();
                long j = length;
                long max = Math.max(0L, (rxHighestSequenceNumber - j) + 1);
                long rxHighestSequenceNumber2 = ipSecTransformState.getRxHighestSequenceNumber();
                long max2 = Math.max(0L, (rxHighestSequenceNumber2 - j) + 1);
                if (rxHighestSequenceNumber == rxHighestSequenceNumber2
                        || rxHighestSequenceNumber2 < j) {
                    i = 1;
                    packetLossCalculationResult = new PacketLossCalculationResult(1, -1);
                } else {
                    if (Flags.handleSeqNumLeap() && i2 != -1) {
                        long timestampMillis =
                                ((ipSecTransformState.getTimestampMillis()
                                                        - ipSecTransformState2.getTimestampMillis())
                                                * i2)
                                        / 1000;
                        if (timestampMillis >= 0
                                && rxHighestSequenceNumber2 - rxHighestSequenceNumber
                                        >= timestampMillis) {
                            z = true;
                            cardinality =
                                    (BitSet.valueOf(ipSecTransformState.getReplayBitmap())
                                                            .cardinality()
                                                    + max2)
                                            - (BitSet.valueOf(
                                                                    ipSecTransformState2
                                                                            .getReplayBitmap())
                                                            .cardinality()
                                                    + max);
                            long packetCount =
                                    ipSecTransformState.getPacketCount()
                                            - ipSecTransformState2.getPacketCount();
                            if (!Flags.handleSeqNumLeap() && cardinality < 10) {
                                packetLossCalculationResult =
                                        new PacketLossCalculationResult(1, -1);
                            } else if (cardinality >= 0
                                    || cardinality == 0
                                    || packetCount < 0
                                    || packetCount > cardinality) {
                                Slog.wtf(
                                        "IpSecPacketLossDetector",
                                        "Impossible values for expectedPktCntDiff or"
                                            + " actualPktCntDiff");
                                VcnManagementService.LOCAL_LOG.log(
                                        "[WTF ] IpSecPacketLossDetectorImpossible values for"
                                            + " expectedPktCntDiff or actualPktCntDiff");
                                i = 1;
                                packetLossCalculationResult =
                                        new PacketLossCalculationResult(1, -1);
                            } else {
                                int i3 = 100 - ((int) ((packetCount * 100) / cardinality));
                                packetLossCalculationResult =
                                        z
                                                ? new PacketLossCalculationResult(2, i3)
                                                : new PacketLossCalculationResult(0, i3);
                            }
                            i = 1;
                        }
                    }
                    z = false;
                    cardinality =
                            (BitSet.valueOf(ipSecTransformState.getReplayBitmap()).cardinality()
                                            + max2)
                                    - (BitSet.valueOf(ipSecTransformState2.getReplayBitmap())
                                                    .cardinality()
                                            + max);
                    long packetCount2 =
                            ipSecTransformState.getPacketCount()
                                    - ipSecTransformState2.getPacketCount();
                    if (!Flags.handleSeqNumLeap()) {}
                    if (cardinality >= 0) {}
                    Slog.wtf(
                            "IpSecPacketLossDetector",
                            "Impossible values for expectedPktCntDiff or actualPktCntDiff");
                    VcnManagementService.LOCAL_LOG.log(
                            "[WTF ] IpSecPacketLossDetectorImpossible values for expectedPktCntDiff"
                                + " or actualPktCntDiff");
                    i = 1;
                    packetLossCalculationResult = new PacketLossCalculationResult(1, -1);
                }
                int i4 = packetLossCalculationResult.mResultType;
                if (i4 == i) {
                    return;
                }
                String str =
                        "calculateResult: "
                                + packetLossCalculationResult
                                + "% in the past "
                                + (ipSecTransformState.getTimestampMillis()
                                        - ipSecPacketLossDetector.mLastIpSecTransformState
                                                .getTimestampMillis())
                                + "ms";
                ipSecPacketLossDetector.mLastIpSecTransformState = ipSecTransformState;
                if (packetLossCalculationResult.mPacketLossRatePercent
                        < ipSecPacketLossDetector.mPacketLossRatePercentThreshold) {
                    ipSecPacketLossDetector.onValidationResultReceivedInternal(false);
                    return;
                }
                Slog.i(
                        ipSecPacketLossDetector.getClassName(),
                        ipSecPacketLossDetector.getLogPrefix() + str);
                VcnManagementService.LOCAL_LOG.log(
                        "[INFO ] "
                                + ipSecPacketLossDetector.getClassName()
                                + ipSecPacketLossDetector.getLogPrefix()
                                + str);
                if (i4 == 0) {
                    ipSecPacketLossDetector.onValidationResultReceivedInternal(true);
                }
                if (Flags.validateNetworkOnIpsecLoss()) {
                    ipSecPacketLossDetector.mConnectivityManager.reportNetworkConnectivity(
                            ipSecPacketLossDetector.mNetwork, false);
                }
            }
        }
    }

    public class PacketLossCalculationResult {
        public final int mPacketLossRatePercent;
        public final int mResultType;

        public PacketLossCalculationResult(int i, int i2) {
            this.mResultType = i;
            this.mPacketLossRatePercent = i2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof PacketLossCalculationResult)) {
                return false;
            }
            PacketLossCalculationResult packetLossCalculationResult =
                    (PacketLossCalculationResult) obj;
            return this.mResultType == packetLossCalculationResult.mResultType
                    && this.mPacketLossRatePercent
                            == packetLossCalculationResult.mPacketLossRatePercent;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mResultType),
                    Integer.valueOf(this.mPacketLossRatePercent));
        }

        public final String toString() {
            return "mResultType: "
                    + this.mResultType
                    + " | mPacketLossRatePercent: "
                    + this.mPacketLossRatePercent;
        }
    }

    public class PacketLossCalculator {}

    public final class PollIpSecStateRunnable implements Runnable {
        public PollIpSecStateRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            IpSecPacketLossDetector ipSecPacketLossDetector = IpSecPacketLossDetector.this;
            if (!ipSecPacketLossDetector.mIsStarted) {
                ipSecPacketLossDetector.logWtf(
                        "Monitor stopped but PollIpSecStateRunnable not removed from Handler");
                return;
            }
            NetworkMetricMonitor.IpSecTransformWrapper inboundTransformInternal =
                    ipSecPacketLossDetector.getInboundTransformInternal();
            inboundTransformInternal.ipSecTransform.requestIpSecTransformState(
                    new HandlerExecutor(IpSecPacketLossDetector.this.mHandler),
                    IpSecPacketLossDetector.this.new IpSecTransformStateReceiver());
            IpSecPacketLossDetector ipSecPacketLossDetector2 = IpSecPacketLossDetector.this;
            ipSecPacketLossDetector2.mHandler.postDelayed(
                    ipSecPacketLossDetector2.new PollIpSecStateRunnable(),
                    ipSecPacketLossDetector2.mCancellationToken,
                    ipSecPacketLossDetector2.mPollIpSecStateIntervalMs);
        }
    }

    public IpSecPacketLossDetector(
            VcnContext vcnContext,
            Network network,
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper,
            NetworkMetricMonitor.NetworkMetricMonitorCallback networkMetricMonitorCallback,
            Dependencies dependencies)
            throws IllegalAccessException {
        super(vcnContext, network, networkMetricMonitorCallback);
        this.mCancellationToken = new Object();
        Objects.requireNonNull(dependencies, "Missing deps");
        if (!VcnContext.isFlagIpSecTransformStateEnabled()) {
            logWtf("ipsecTransformState flag disabled");
            throw new IllegalAccessException("ipsecTransformState flag disabled");
        }
        Handler handler = new Handler(this.mVcnContext.mLooper);
        this.mHandler = handler;
        this.mPowerManager =
                (PowerManager) this.mVcnContext.mContext.getSystemService(PowerManager.class);
        this.mConnectivityManager =
                (ConnectivityManager)
                        this.mVcnContext.mContext.getSystemService(ConnectivityManager.class);
        this.mPacketLossCalculator = new PacketLossCalculator();
        this.mPollIpSecStateIntervalMs =
                TimeUnit.SECONDS.toMillis(
                        persistableBundleWrapper != null
                                ? persistableBundleWrapper.mBundle.getInt(
                                        "vcn_network_selection_poll_ipsec_state_interval_seconds",
                                        20)
                                : 20);
        this.mPacketLossRatePercentThreshold =
                persistableBundleWrapper != null
                        ? persistableBundleWrapper.mBundle.getInt(
                                "vcn_network_selection_ipsec_packet_loss_percent_threshold", 12)
                        : 12;
        this.mMaxSeqNumIncreasePerSecond = getMaxSeqNumIncreasePerSecond(persistableBundleWrapper);
        this.mVcnContext.mContext.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.vcn.routeselection.IpSecPacketLossDetector.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.os.action.DEVICE_IDLE_MODE_CHANGED".equals(intent.getAction())
                                && IpSecPacketLossDetector.this.mPowerManager.isDeviceIdleMode()) {
                            IpSecPacketLossDetector.this.mLastIpSecTransformState = null;
                        }
                    }
                },
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.os.action.DEVICE_IDLE_MODE_CHANGED"),
                null,
                handler);
    }

    public static int getMaxSeqNumIncreasePerSecond(
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        int i =
                (!Flags.handleSeqNumLeap() || persistableBundleWrapper == null)
                        ? -1
                        : persistableBundleWrapper.mBundle.getInt(
                                "vcn_network_selection_max_seq_num_increase_per_second", -1);
        if (i >= -1) {
            return i;
        }
        String str = "Invalid value of MAX_SEQ_NUM_INCREASE_PER_SECOND_KEY " + i;
        Slog.w("IpSecPacketLossDetector", str);
        VcnManagementService.LOCAL_LOG.log("[ERROR ] IpSecPacketLossDetector" + str);
        return -1;
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor, java.lang.AutoCloseable
    public final void close() {
        super.close();
        NetworkMetricMonitor.IpSecTransformWrapper ipSecTransformWrapper = this.mInboundTransform;
        if (ipSecTransformWrapper != null) {
            ipSecTransformWrapper.ipSecTransform.close();
        }
    }

    public NetworkMetricMonitor.IpSecTransformWrapper getInboundTransformInternal() {
        return this.mInboundTransform;
    }

    public IpSecTransformState getLastTransformState() {
        return this.mLastIpSecTransformState;
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor
    public final void setInboundTransformInternal(
            NetworkMetricMonitor.IpSecTransformWrapper ipSecTransformWrapper) {
        Objects.requireNonNull(ipSecTransformWrapper, "inboundTransform is null");
        if (ipSecTransformWrapper.equals(this.mInboundTransform)) {
            return;
        }
        if (!this.mIsSelectedUnderlyingNetwork) {
            logWtf("setInboundTransform called but network not selected");
            return;
        }
        this.mInboundTransform = ipSecTransformWrapper;
        if (Flags.allowDisableIpsecLossDetector()
                && (this.mInboundTransform == null || this.mPacketLossRatePercentThreshold == -1)) {
            return;
        }
        this.mIsStarted = true;
        this.mHandler.removeCallbacksAndEqualMessages(this.mCancellationToken);
        this.mLastIpSecTransformState = null;
        this.mHandler.postDelayed(new PollIpSecStateRunnable(), this.mCancellationToken, 0L);
    }
}
