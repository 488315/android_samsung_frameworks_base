package com.android.internal.os;

import android.os.FileUtils;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;

/* loaded from: classes5.dex */
public class CpuScalingPolicyReader {
    private static final String CPUFREQ_DIR = "/sys/devices/system/cpu/cpufreq";
    private static final String FILE_NAME_CPUINFO_CUR_FREQ = "cpuinfo_cur_freq";
    private static final String FILE_NAME_RELATED_CPUS = "related_cpus";
    private static final String FILE_NAME_SCALING_AVAILABLE_FREQUENCIES = "scaling_available_frequencies";
    private static final String FILE_NAME_SCALING_BOOST_FREQUENCIES = "scaling_boost_frequencies";
    private static final Pattern POLICY_PATTERN = Pattern.compile("policy(\\d+)");
    private static final String TAG = "CpuScalingPolicyReader";
    private final String mCpuFreqDir;

    public CpuScalingPolicyReader() {
        this(CPUFREQ_DIR);
    }

    public CpuScalingPolicyReader(String str) {
        this.mCpuFreqDir = str;
    }

    public CpuScalingPolicies read() {
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        File[] listFiles = new File(this.mCpuFreqDir).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                Matcher matcher = POLICY_PATTERN.matcher(file.getName());
                if (matcher.matches()) {
                    int[] readIntsFromFile = readIntsFromFile(new File(file, FILE_NAME_RELATED_CPUS));
                    if (readIntsFromFile.length != 0) {
                        int[] readIntsFromFile2 = readIntsFromFile(new File(file, FILE_NAME_SCALING_AVAILABLE_FREQUENCIES));
                        int[] readIntsFromFile3 = readIntsFromFile(new File(file, FILE_NAME_SCALING_BOOST_FREQUENCIES));
                        if (readIntsFromFile3.length != 0) {
                            int[] copyOf = Arrays.copyOf(readIntsFromFile2, readIntsFromFile2.length + readIntsFromFile3.length);
                            System.arraycopy(readIntsFromFile3, 0, copyOf, readIntsFromFile2.length, readIntsFromFile3.length);
                            readIntsFromFile2 = copyOf;
                        }
                        if (readIntsFromFile2.length == 0) {
                            readIntsFromFile2 = readIntsFromFile(new File(file, FILE_NAME_CPUINFO_CUR_FREQ));
                            if (readIntsFromFile2.length == 0) {
                                readIntsFromFile2 = new int[]{0};
                            }
                        }
                        int parseInt = Integer.parseInt(matcher.group(1));
                        sparseArray.put(parseInt, readIntsFromFile);
                        sparseArray2.put(parseInt, readIntsFromFile2);
                    }
                }
            }
        }
        if (sparseArray.size() == 0) {
            sparseArray.put(0, new int[]{0});
            sparseArray2.put(0, new int[]{0});
        }
        CpuScalingPolicies cpuScalingPolicies = new CpuScalingPolicies(sparseArray, sparseArray2);
        Slog.i(TAG, "CpuScalingPolicies: " + cpuScalingPolicies);
        Slog.i(TAG, "CpuScalingPolicies.getScalingStepCount(): " + cpuScalingPolicies.getScalingStepCount());
        return cpuScalingPolicies;
    }

    private static int[] readIntsFromFile(File file) {
        Slog.i(TAG, "READING FROM " + file);
        if (!file.exists()) {
            Slog.w(TAG, "FILE DOES NOT EXIST");
            return EmptyArray.INT;
        }
        IntArray intArray = new IntArray(16);
        try {
            String trim = FileUtils.readTextFile(file, 0, null).trim();
            Slog.i(TAG, "FILE CONTENTS: " + trim);
            String[] split = trim.split(" ");
            intArray.clear();
            for (String str : split) {
                if (!str.isBlank()) {
                    try {
                        intArray.add(Integer.parseInt(str));
                    } catch (NumberFormatException e) {
                        Slog.e(TAG, "Unexpected file format " + file + ": " + trim, e);
                    }
                }
            }
            int[] array = intArray.toArray();
            Slog.i(TAG, "PARSED INTS: " + Arrays.toString(array));
            return array;
        } catch (IOException e2) {
            Slog.e(TAG, "Cannot read " + file, e2);
            return EmptyArray.INT;
        }
    }
}
