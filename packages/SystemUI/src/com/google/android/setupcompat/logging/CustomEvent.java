package com.google.android.setupcompat.logging;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.android.setupcompat.internal.ClockProvider;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.Validations;
import com.google.android.setupcompat.util.Logger;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CustomEvent implements Parcelable {
    public static final Parcelable.Creator<CustomEvent> CREATOR = new Parcelable.Creator() { // from class: com.google.android.setupcompat.logging.CustomEvent.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new CustomEvent(parcel.readLong(), (MetricKey) parcel.readParcelable(MetricKey.class.getClassLoader()), parcel.readPersistableBundle(MetricKey.class.getClassLoader()), parcel.readPersistableBundle(MetricKey.class.getClassLoader()), 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CustomEvent[i];
        }
    };
    public static final int MAX_STR_LENGTH = 50;
    static final int MIN_BUNDLE_KEY_LENGTH = 3;
    public final MetricKey metricKey;
    public final PersistableBundle persistableBundle;
    public final PersistableBundle piiValues;
    public final long timestampMillis;

    public /* synthetic */ CustomEvent(long j, MetricKey metricKey, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, int i) {
        this(j, metricKey, persistableBundle, persistableBundle2);
    }

    public static CustomEvent create(MetricKey metricKey, PersistableBundle persistableBundle) {
        PersistableBundle persistableBundle2 = PersistableBundle.EMPTY;
        long millis = TimeUnit.NANOSECONDS.toMillis(ClockProvider.ticker.read());
        PersistableBundles.assertIsValid(persistableBundle);
        PersistableBundles.assertIsValid(persistableBundle2);
        return new CustomEvent(millis, metricKey, persistableBundle, persistableBundle2);
    }

    public static Bundle toBundle(CustomEvent customEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("CustomEvent_version", 1);
        bundle.putLong("CustomEvent_timestamp", customEvent.timestampMillis);
        bundle.putBundle("CustomEvent_metricKey", MetricKey.fromMetricKey(customEvent.metricKey));
        PersistableBundle persistableBundle = new PersistableBundle(customEvent.persistableBundle);
        Logger logger = PersistableBundles.LOG;
        Bundle bundle2 = new Bundle();
        bundle2.putAll(persistableBundle);
        bundle.putBundle("CustomEvent_bundleValues", bundle2);
        PersistableBundle persistableBundle2 = customEvent.piiValues;
        Bundle bundle3 = new Bundle();
        bundle3.putAll(persistableBundle2);
        bundle.putBundle("CustomEvent_pii_bundleValues", bundle3);
        return bundle;
    }

    public static String trimsStringOverMaxLength(String str) {
        return str.length() <= 50 ? str : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str.substring(0, 49), "…");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CustomEvent) {
            CustomEvent customEvent = (CustomEvent) obj;
            if (this.timestampMillis == customEvent.timestampMillis) {
                MetricKey metricKey = this.metricKey;
                MetricKey metricKey2 = customEvent.metricKey;
                if (metricKey != metricKey2 && (metricKey == null || !metricKey.equals(metricKey2))) {
                    return false;
                }
                PersistableBundle persistableBundle = this.persistableBundle;
                PersistableBundle persistableBundle2 = customEvent.persistableBundle;
                if (persistableBundle == persistableBundle2) {
                    Logger logger = PersistableBundles.LOG;
                } else if (!PersistableBundles.toMap(persistableBundle).equals(PersistableBundles.toMap(persistableBundle2))) {
                    return false;
                }
                PersistableBundle persistableBundle3 = this.piiValues;
                PersistableBundle persistableBundle4 = customEvent.piiValues;
                return persistableBundle3 == persistableBundle4 || PersistableBundles.toMap(persistableBundle3).equals(PersistableBundles.toMap(persistableBundle4));
            }
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.timestampMillis), this.metricKey, this.persistableBundle, this.piiValues});
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.timestampMillis);
        parcel.writeParcelable(this.metricKey, i);
        parcel.writePersistableBundle(this.persistableBundle);
        parcel.writePersistableBundle(this.piiValues);
    }

    private CustomEvent(long j, MetricKey metricKey, PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
        Preconditions.checkArgument("Timestamp cannot be negative.", j >= 0);
        Preconditions.checkNotNull(metricKey, "MetricKey cannot be null.");
        Preconditions.checkNotNull(persistableBundle, "Bundle cannot be null.");
        Preconditions.checkArgument("Bundle cannot be empty.", !persistableBundle.isEmpty());
        Preconditions.checkNotNull(persistableBundle2, "piiValues cannot be null.");
        for (String str : persistableBundle.keySet()) {
            Validations.assertLengthInRange(3, 50, str, "bundle key");
            Object obj = persistableBundle.get(str);
            if (obj instanceof String) {
                Preconditions.checkArgument("Maximum length of string value for key='" + str + "' cannot exceed 50.", ((String) obj).length() <= 50);
            }
        }
        this.timestampMillis = j;
        this.metricKey = metricKey;
        this.persistableBundle = new PersistableBundle(persistableBundle);
        this.piiValues = new PersistableBundle(persistableBundle2);
    }
}
