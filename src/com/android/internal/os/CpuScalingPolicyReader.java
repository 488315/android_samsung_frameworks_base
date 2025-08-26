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

    public CpuScalingPolicies read() throws NumberFormatException {
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        File[] fileArrListFiles = new File(this.mCpuFreqDir).listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                Matcher matcher = POLICY_PATTERN.matcher(file.getName());
                if (matcher.matches()) {
                    int[] intsFromFile = readIntsFromFile(new File(file, FILE_NAME_RELATED_CPUS));
                    if (intsFromFile.length != 0) {
                        int[] intsFromFile2 = readIntsFromFile(new File(file, FILE_NAME_SCALING_AVAILABLE_FREQUENCIES));
                        int[] intsFromFile3 = readIntsFromFile(new File(file, FILE_NAME_SCALING_BOOST_FREQUENCIES));
                        if (intsFromFile3.length != 0) {
                            int[] iArrCopyOf = Arrays.copyOf(intsFromFile2, intsFromFile2.length + intsFromFile3.length);
                            System.arraycopy(intsFromFile3, 0, iArrCopyOf, intsFromFile2.length, intsFromFile3.length);
                            intsFromFile2 = iArrCopyOf;
                        }
                        if (intsFromFile2.length == 0) {
                            intsFromFile2 = readIntsFromFile(new File(file, FILE_NAME_CPUINFO_CUR_FREQ));
                            if (intsFromFile2.length == 0) {
                                intsFromFile2 = new int[]{0};
                            }
                        }
                        int i = Integer.parseInt(matcher.group(1));
                        sparseArray.put(i, intsFromFile);
                        sparseArray2.put(i, intsFromFile2);
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
            String strTrim = FileUtils.readTextFile(file, 0, null).trim();
            Slog.i(TAG, "FILE CONTENTS: " + strTrim);
            String[] strArrSplit = strTrim.split(" ");
            intArray.clear();
            for (String str : strArrSplit) {
                if (!str.isBlank()) {
                    try {
                        intArray.add(Integer.parseInt(str));
                    } catch (NumberFormatException e) {
                        Slog.e(TAG, "Unexpected file format " + file + ": " + strTrim, e);
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
