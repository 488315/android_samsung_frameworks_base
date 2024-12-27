package com.android.server.vcn.routeselection;

import android.net.IpSecTransform;
import android.net.Network;
import android.os.Handler;
import android.util.CloseGuard;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.ExitPenaltyBoxRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class NetworkMetricMonitor implements AutoCloseable {
    public final NetworkMetricMonitorCallback mCallback;
    public final CloseGuard mCloseGuard = new CloseGuard();
    public boolean mIsSelectedUnderlyingNetwork;
    public boolean mIsStarted;
    public boolean mIsValidationFailed;
    public final Network mNetwork;
    public final VcnContext mVcnContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class IpSecTransformWrapper {
        public final IpSecTransform ipSecTransform;

        public IpSecTransformWrapper(IpSecTransform ipSecTransform) {
            this.ipSecTransform = ipSecTransform;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof IpSecTransformWrapper) {
                return Objects.equals(
                        this.ipSecTransform, ((IpSecTransformWrapper) obj).ipSecTransform);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.ipSecTransform);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NetworkMetricMonitorCallback {}

    public NetworkMetricMonitor(
            VcnContext vcnContext,
            Network network,
            NetworkMetricMonitorCallback networkMetricMonitorCallback) {
        if (!vcnContext.isFlagNetworkMetricMonitorEnabled()) {
            logWtf("networkMetricMonitor flag disabled");
            throw new IllegalAccessException("networkMetricMonitor flag disabled");
        }
        this.mVcnContext = vcnContext;
        Objects.requireNonNull(network, "Missing network");
        this.mNetwork = network;
        Objects.requireNonNull(networkMetricMonitorCallback, "Missing callback");
        this.mCallback = networkMetricMonitorCallback;
        this.mIsSelectedUnderlyingNetwork = false;
        this.mIsStarted = false;
        this.mIsValidationFailed = false;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        IpSecPacketLossDetector ipSecPacketLossDetector = (IpSecPacketLossDetector) this;
        ipSecPacketLossDetector.mIsValidationFailed = false;
        ipSecPacketLossDetector.mIsStarted = false;
        ipSecPacketLossDetector.mHandler.removeCallbacksAndEqualMessages(
                ipSecPacketLossDetector.mCancellationToken);
        ipSecPacketLossDetector.mLastIpSecTransformState = null;
    }

    public final void finalize() {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            close();
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
    }

    public final String getClassName() {
        return getClass().getSimpleName();
    }

    public final String getLogPrefix() {
        return " [Network " + this.mNetwork + "] ";
    }

    public final void logWtf(String str) {
        Slog.wtf(getClassName(), getLogPrefix() + str);
        VcnManagementService.LOCAL_LOG.log("[WTF ] " + getClassName() + getLogPrefix() + str);
    }

    public final void onValidationResultReceivedInternal(boolean z) {
        this.mIsValidationFailed = z;
        UnderlyingNetworkEvaluator underlyingNetworkEvaluator = UnderlyingNetworkEvaluator.this;
        underlyingNetworkEvaluator.mVcnContext.ensureRunningOnLooperThread();
        boolean z2 = underlyingNetworkEvaluator.mIsPenalized;
        underlyingNetworkEvaluator.mIsPenalized = false;
        Iterator it = ((ArrayList) underlyingNetworkEvaluator.mMetricMonitors).iterator();
        while (it.hasNext()) {
            underlyingNetworkEvaluator.mIsPenalized =
                    ((NetworkMetricMonitor) it.next()).mIsValidationFailed
                            | underlyingNetworkEvaluator.mIsPenalized;
        }
        if (z2 == underlyingNetworkEvaluator.mIsPenalized) {
            return;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "#handleValidationResult: wasPenalized ", " mIsPenalized ", z2);
        m.append(underlyingNetworkEvaluator.mIsPenalized);
        String sb = m.toString();
        Slog.i("UnderlyingNetworkEvaluator", underlyingNetworkEvaluator.getLogPrefix() + sb);
        VcnManagementService.LOCAL_LOG.log(
                "[INFO ] UnderlyingNetworkEvaluator"
                        + underlyingNetworkEvaluator.getLogPrefix()
                        + sb);
        boolean z3 = underlyingNetworkEvaluator.mIsPenalized;
        Object obj = underlyingNetworkEvaluator.mCancellationToken;
        Handler handler = underlyingNetworkEvaluator.mHandler;
        if (z3) {
            handler.postDelayed(
                    underlyingNetworkEvaluator.new ExitPenaltyBoxRunnable(),
                    obj,
                    underlyingNetworkEvaluator.mPenalizedTimeoutMs);
        } else {
            handler.removeCallbacksAndEqualMessages(obj);
        }
        ((UnderlyingNetworkController.NetworkEvaluatorCallbackImpl)
                        underlyingNetworkEvaluator.mEvaluatorCallback)
                .onEvaluationResultChanged();
    }

    public void setInboundTransformInternal(IpSecTransformWrapper ipSecTransformWrapper) {}
}
