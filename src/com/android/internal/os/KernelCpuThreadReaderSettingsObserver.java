package com.android.internal.os;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Range;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class KernelCpuThreadReaderSettingsObserver extends ContentObserver {
    private static final String COLLECTED_UIDS_DEFAULT = "0-0;1000-1000";
    private static final String COLLECTED_UIDS_SETTINGS_KEY = "collected_uids";
    private static final int MINIMUM_TOTAL_CPU_USAGE_MILLIS_DEFAULT = 10000;
    private static final String MINIMUM_TOTAL_CPU_USAGE_MILLIS_SETTINGS_KEY = "minimum_total_cpu_usage_millis";
    private static final int NUM_BUCKETS_DEFAULT = 8;
    private static final String NUM_BUCKETS_SETTINGS_KEY = "num_buckets";
    private static final String TAG = "KernelCpuThreadReaderSettingsObserver";
    private final Context mContext;
    private final KernelCpuThreadReader mKernelCpuThreadReader;
    private final KernelCpuThreadReaderDiff mKernelCpuThreadReaderDiff;

    public static KernelCpuThreadReaderDiff getSettingsModifiedReader(Context context) {
        KernelCpuThreadReaderSettingsObserver kernelCpuThreadReaderSettingsObserver = new KernelCpuThreadReaderSettingsObserver(context);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(Settings.Global.KERNEL_CPU_THREAD_READER), false, kernelCpuThreadReaderSettingsObserver, 0);
        return kernelCpuThreadReaderSettingsObserver.mKernelCpuThreadReaderDiff;
    }

    private KernelCpuThreadReaderSettingsObserver(Context context) {
        super(BackgroundThread.getHandler());
        this.mContext = context;
        KernelCpuThreadReader kernelCpuThreadReaderCreate = KernelCpuThreadReader.create(8, UidPredicate.fromString(COLLECTED_UIDS_DEFAULT));
        this.mKernelCpuThreadReader = kernelCpuThreadReaderCreate;
        this.mKernelCpuThreadReaderDiff = kernelCpuThreadReaderCreate == null ? null : new KernelCpuThreadReaderDiff(kernelCpuThreadReaderCreate, 10000);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
        updateReader();
    }

    private void updateReader() {
        if (this.mKernelCpuThreadReader == null) {
            return;
        }
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(Settings.Global.getString(this.mContext.getContentResolver(), Settings.Global.KERNEL_CPU_THREAD_READER));
            try {
                UidPredicate uidPredicateFromString = UidPredicate.fromString(keyValueListParser.getString(COLLECTED_UIDS_SETTINGS_KEY, COLLECTED_UIDS_DEFAULT));
                this.mKernelCpuThreadReader.setNumBuckets(keyValueListParser.getInt(NUM_BUCKETS_SETTINGS_KEY, 8));
                this.mKernelCpuThreadReader.setUidPredicate(uidPredicateFromString);
                this.mKernelCpuThreadReaderDiff.setMinimumTotalCpuUsageMillis(keyValueListParser.getInt(MINIMUM_TOTAL_CPU_USAGE_MILLIS_SETTINGS_KEY, 10000));
            } catch (NumberFormatException e) {
                Slog.w(TAG, "Failed to get UID predicate", e);
            }
        } catch (IllegalArgumentException e2) {
            Slog.e(TAG, "Bad settings", e2);
        }
    }

    public static class UidPredicate implements Predicate<Integer> {
        private static final Pattern UID_RANGE_PATTERN = Pattern.compile("([0-9]+)-([0-9]+)");
        private static final String UID_SPECIFIER_DELIMITER = ";";
        private final List<Range<Integer>> mAcceptedUidRanges;

        public static UidPredicate fromString(String str) throws NumberFormatException {
            ArrayList arrayList = new ArrayList();
            for (String str2 : str.split(";")) {
                Matcher matcher = UID_RANGE_PATTERN.matcher(str2);
                if (!matcher.matches()) {
                    throw new NumberFormatException("Failed to recognize as number range: " + str2);
                }
                arrayList.add(Range.create(Integer.valueOf(Integer.parseInt(matcher.group(1))), Integer.valueOf(Integer.parseInt(matcher.group(2)))));
            }
            return new UidPredicate(arrayList);
        }

        private UidPredicate(List<Range<Integer>> list) {
            this.mAcceptedUidRanges = list;
        }

        @Override // java.util.function.Predicate
        public boolean test(Integer num) {
            for (int i = 0; i < this.mAcceptedUidRanges.size(); i++) {
                if (this.mAcceptedUidRanges.get(i).contains((Range<Integer>) num)) {
                    return true;
                }
            }
            return false;
        }
    }
}
