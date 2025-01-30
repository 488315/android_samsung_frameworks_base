package com.google.android.setupcompat.logging;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.google.android.setupcompat.internal.ClockProvider;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.Validations;
import com.google.android.setupcompat.util.Logger;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    static final int MAX_STR_LENGTH = 50;
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

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomEvent)) {
            return false;
        }
        CustomEvent customEvent = (CustomEvent) obj;
        if (this.timestampMillis == customEvent.timestampMillis) {
            MetricKey metricKey = this.metricKey;
            MetricKey metricKey2 = customEvent.metricKey;
            if (metricKey == metricKey2 || (metricKey != null && metricKey.equals(metricKey2))) {
                PersistableBundle persistableBundle = this.persistableBundle;
                PersistableBundle persistableBundle2 = customEvent.persistableBundle;
                if (persistableBundle == persistableBundle2) {
                    Logger logger = PersistableBundles.LOG;
                } else if (!PersistableBundles.toMap(persistableBundle).equals(PersistableBundles.toMap(persistableBundle2))) {
                    z = false;
                    if (z) {
                        PersistableBundle persistableBundle3 = this.piiValues;
                        PersistableBundle persistableBundle4 = customEvent.piiValues;
                        if (persistableBundle3 == persistableBundle4 || PersistableBundles.toMap(persistableBundle3).equals(PersistableBundles.toMap(persistableBundle4))) {
                            return true;
                        }
                    }
                }
                z = true;
                if (z) {
                }
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
        if (metricKey == null) {
            throw new NullPointerException("MetricKey cannot be null.");
        }
        if (persistableBundle == null) {
            throw new NullPointerException("Bundle cannot be null.");
        }
        Preconditions.checkArgument("Bundle cannot be empty.", !persistableBundle.isEmpty());
        if (persistableBundle2 == null) {
            throw new NullPointerException("piiValues cannot be null.");
        }
        for (String str : persistableBundle.keySet()) {
            Validations.assertLengthInRange(3, 50, str, "bundle key");
            Object obj = persistableBundle.get(str);
            if (obj instanceof String) {
                Preconditions.checkArgument(String.format("Maximum length of string value for key='%s' cannot exceed %s.", str, 50), ((String) obj).length() <= 50);
            }
        }
        this.timestampMillis = j;
        this.metricKey = metricKey;
        this.persistableBundle = new PersistableBundle(persistableBundle);
        this.piiValues = new PersistableBundle(persistableBundle2);
    }
}
