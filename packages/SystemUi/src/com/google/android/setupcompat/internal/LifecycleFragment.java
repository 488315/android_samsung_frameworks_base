package com.google.android.setupcompat.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LifecycleFragment extends Fragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public long durationInNanos = 0;
    public MetricKey metricKey;
    public long startInNanos;

    public LifecycleFragment() {
        setRetainInstance(true);
    }

    @Override // android.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.metricKey = MetricKey.get("ScreenDuration", getActivity());
    }

    @Override // android.app.Fragment
    public final void onDetach() {
        super.onDetach();
        Activity activity = getActivity();
        MetricKey metricKey = this.metricKey;
        long millis = TimeUnit.NANOSECONDS.toMillis(this.durationInNanos);
        int i = SetupMetricsLogger.$r8$clinit;
        if (activity == null) {
            throw new NullPointerException("Context cannot be null.");
        }
        if (metricKey == null) {
            throw new NullPointerException("Timer name cannot be null.");
        }
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
        this.durationInNanos = (ClockProvider.ticker.read() - this.startInNanos) + this.durationInNanos;
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.startInNanos = ClockProvider.ticker.read();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putLong("onScreenResume", System.nanoTime());
        SetupMetricsLogger.logCustomEvent(getActivity(), CustomEvent.create(MetricKey.get("ScreenActivity", getActivity()), persistableBundle));
    }
}
