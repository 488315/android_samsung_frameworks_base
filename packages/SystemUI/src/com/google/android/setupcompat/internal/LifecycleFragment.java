package com.google.android.setupcompat.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda1;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LifecycleFragment extends Fragment {
    public static final Logger LOG = new Logger("LifecycleFragment");
    public long durationInNanos = 0;
    public PartnerCustomizationLayout$$ExternalSyntheticLambda1 lifecycleChangeListener;
    public MetricKey metricKey;
    public long startInNanos;

    public LifecycleFragment() {
        setRetainInstance(true);
    }

    @Override // android.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        LOG.atDebug("onAttach host=".concat(getActivity().getClass().getSimpleName()));
        this.metricKey = MetricKey.get("ScreenDuration", getActivity());
    }

    @Override // android.app.Fragment
    public final void onDetach() {
        super.onDetach();
        LOG.atDebug("onDetach host=".concat(getActivity().getClass().getSimpleName()));
        Activity activity = getActivity();
        MetricKey metricKey = this.metricKey;
        long millis = TimeUnit.NANOSECONDS.toMillis(this.durationInNanos);
        int i = SetupMetricsLogger.$r8$clinit;
        Preconditions.checkNotNull(activity, "Context cannot be null.");
        Preconditions.checkNotNull(metricKey, "Timer name cannot be null.");
        Preconditions.checkArgument("Duration cannot be negative.", millis >= 0);
        SetupCompatServiceInvoker setupCompatServiceInvoker = SetupCompatServiceInvoker.get(activity);
        Bundle bundle = new Bundle();
        bundle.putParcelable("MetricKey_bundle", MetricKey.fromMetricKey(metricKey));
        bundle.putLong("timeMillis", millis);
        setupCompatServiceInvoker.logMetricEvent(2, bundle);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        LOG.atDebug("onPause host=".concat(getActivity().getClass().getSimpleName()));
        this.durationInNanos = (ClockProvider.ticker.read() - this.startInNanos) + this.durationInNanos;
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.startInNanos = ClockProvider.ticker.read();
        LOG.atDebug("onResume host=" + getActivity().getClass().getSimpleName() + ", startInNanos=" + this.startInNanos);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putLong("onScreenResume", System.nanoTime());
        SetupMetricsLogger.logCustomEvent(getActivity(), CustomEvent.create(MetricKey.get("ScreenActivity", getActivity()), persistableBundle));
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        long nanoTime = System.nanoTime();
        LOG.atDebug("onStop host=" + getActivity().getClass().getSimpleName() + ", onStopTimestamp=" + nanoTime);
        if (this.lifecycleChangeListener != null) {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putLong("onScreenStop", nanoTime);
            PartnerCustomizationLayout partnerCustomizationLayout = this.lifecycleChangeListener.f$0;
            Activity activity = partnerCustomizationLayout.activity;
            if (activity != null && WizardManagerHelper.isAnySetupWizard(activity.getIntent()) && PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(partnerCustomizationLayout.getContext())) {
                FooterBarMixin footerBarMixin = (FooterBarMixin) partnerCustomizationLayout.getMixin(FooterBarMixin.class);
                if (footerBarMixin == null || (footerBarMixin.primaryButton == null && footerBarMixin.secondaryButton == null)) {
                    PartnerCustomizationLayout.LOG.atDebug("Skip footer button logging because no footer buttons.");
                    return;
                }
                FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
                boolean isPrimaryButtonVisible = footerBarMixin.isPrimaryButtonVisible();
                boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
                footerBarMixinMetrics.primaryButtonVisibility = FooterBarMixinMetrics.updateButtonVisibilityState(footerBarMixinMetrics.primaryButtonVisibility, isPrimaryButtonVisible);
                footerBarMixinMetrics.secondaryButtonVisibility = FooterBarMixinMetrics.updateButtonVisibilityState(footerBarMixinMetrics.secondaryButtonVisibility, isSecondaryButtonVisible);
                FooterButton footerButton = footerBarMixin.primaryButton;
                FooterButton footerButton2 = footerBarMixin.secondaryButton;
                SetupMetricsLogger.logCustomEvent(partnerCustomizationLayout.getContext(), CustomEvent.create(MetricKey.get("FooterButtonMetrics", partnerCustomizationLayout.activity), PersistableBundles.mergeBundles(footerBarMixin.getLoggingMetrics(), footerButton != null ? footerButton.getMetrics("PrimaryFooterButton") : PersistableBundle.EMPTY, footerButton2 != null ? footerButton2.getMetrics("SecondaryFooterButton") : PersistableBundle.EMPTY, persistableBundle)));
            }
        }
    }
}
