package com.android.server.vcn.routeselection;

import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Slog;
import com.android.server.VcnManagementService;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.routeselection.IpSecPacketLossDetector;
import com.android.server.vcn.routeselection.NetworkMetricMonitor;
import com.android.server.vcn.routeselection.UnderlyingNetworkController;
import com.android.server.vcn.routeselection.UnderlyingNetworkRecord;
import com.android.server.vcn.util.PersistableBundleUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UnderlyingNetworkEvaluator {
    public static final int[] PENALTY_TIMEOUT_MINUTES_DEFAULT = {5};
    public final Object mCancellationToken = new Object();
    public final NetworkEvaluatorCallback mEvaluatorCallback;
    public final Handler mHandler;
    public boolean mIsPenalized;
    public boolean mIsSelected;
    public final List mMetricMonitors;
    public final UnderlyingNetworkRecord.Builder mNetworkRecordBuilder;
    public long mPenalizedTimeoutMs;
    public int mPriorityClass;
    public final VcnContext mVcnContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Dependencies {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExitPenaltyBoxRunnable implements Runnable {
        public ExitPenaltyBoxRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnderlyingNetworkEvaluator underlyingNetworkEvaluator = UnderlyingNetworkEvaluator.this;
            if (!underlyingNetworkEvaluator.mIsPenalized) {
                underlyingNetworkEvaluator.logWtf("Evaluator not being penalized but ExitPenaltyBoxRunnable was scheduled");
            } else {
                underlyingNetworkEvaluator.mIsPenalized = false;
                ((UnderlyingNetworkController.NetworkEvaluatorCallbackImpl) underlyingNetworkEvaluator.mEvaluatorCallback).onEvaluationResultChanged();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MetricMonitorCallbackImpl implements NetworkMetricMonitor.NetworkMetricMonitorCallback {
        public MetricMonitorCallbackImpl() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NetworkEvaluatorCallback {
    }

    public UnderlyingNetworkEvaluator(VcnContext vcnContext, Network network, List list, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, NetworkEvaluatorCallback networkEvaluatorCallback, Dependencies dependencies) {
        int[] intArray;
        ArrayList arrayList = new ArrayList();
        this.mMetricMonitors = arrayList;
        this.mPriorityClass = -1;
        Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mVcnContext = vcnContext;
        this.mHandler = new Handler(vcnContext.mLooper);
        Objects.requireNonNull(dependencies, "Missing dependencies");
        Objects.requireNonNull(networkEvaluatorCallback, "Missing deps");
        this.mEvaluatorCallback = networkEvaluatorCallback;
        Objects.requireNonNull(list, "Missing underlyingNetworkTemplates");
        Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing lastSnapshot");
        Objects.requireNonNull(network, "Missing network");
        this.mNetworkRecordBuilder = new UnderlyingNetworkRecord.Builder(network);
        this.mIsSelected = false;
        this.mIsPenalized = false;
        int[] iArr = PENALTY_TIMEOUT_MINUTES_DEFAULT;
        if (persistableBundleWrapper != null && (intArray = persistableBundleWrapper.mBundle.getIntArray("vcn_network_selection_penalty_timeout_minutes_list")) != null) {
            iArr = intArray;
        }
        this.mPenalizedTimeoutMs = TimeUnit.MINUTES.toMillis(iArr[0]);
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
        if (VcnContext.isFlagIpSecTransformStateEnabled() && vcnContext.isFlagNetworkMetricMonitorEnabled()) {
            try {
                arrayList.add(new IpSecPacketLossDetector(vcnContext, network, persistableBundleWrapper, new MetricMonitorCallbackImpl(), new IpSecPacketLossDetector.Dependencies()));
            } catch (IllegalAccessException unused) {
            }
        }
    }

    public final void close() {
        this.mHandler.removeCallbacksAndEqualMessages(this.mCancellationToken);
        Iterator it = ((ArrayList) this.mMetricMonitors).iterator();
        while (it.hasNext()) {
            ((NetworkMetricMonitor) it.next()).close();
        }
    }

    public final String getLogPrefix() {
        return "[Network " + this.mNetworkRecordBuilder.mNetwork + "] ";
    }

    public final void logWtf(String str) {
        Slog.wtf("UnderlyingNetworkEvaluator", getLogPrefix() + str);
        VcnManagementService.LOCAL_LOG.log("[WTF ] UnderlyingNetworkEvaluator" + getLogPrefix() + str);
    }

    public final void updatePriorityClass(List list, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        UnderlyingNetworkRecord.Builder builder = this.mNetworkRecordBuilder;
        int i = -1;
        if (!builder.isValid()) {
            this.mPriorityClass = -1;
            return;
        }
        UnderlyingNetworkRecord build = builder.build();
        boolean z = this.mIsSelected;
        if (build.isBlocked) {
            NetworkPriorityClassifier.logWtf("Network blocked for System Server: " + build.network);
        } else if (telephonySubscriptionSnapshot == null) {
            NetworkPriorityClassifier.logWtf("Got null snapshot");
        } else {
            Iterator it = list.iterator();
            int i2 = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                VcnContext vcnContext = this.mVcnContext;
                if (!hasNext) {
                    NetworkCapabilities networkCapabilities = build.networkCapabilities;
                    if (networkCapabilities.hasCapability(12) || (vcnContext.mIsInTestMode && networkCapabilities.hasTransport(7))) {
                        i = Integer.MAX_VALUE;
                    }
                } else {
                    if (NetworkPriorityClassifier.checkMatchesPriorityRule(vcnContext, (VcnUnderlyingNetworkTemplate) it.next(), build, parcelUuid, telephonySubscriptionSnapshot, z, persistableBundleWrapper)) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
            }
        }
        this.mPriorityClass = i;
    }
}
