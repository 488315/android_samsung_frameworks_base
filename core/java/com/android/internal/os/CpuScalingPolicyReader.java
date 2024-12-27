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

    public CpuScalingPolicyReader(String cpuFreqDir) {
        this.mCpuFreqDir = cpuFreqDir;
    }

    public CpuScalingPolicies read() {
        int[] freqs;
        SparseArray<int[]> cpusByPolicy = new SparseArray<>();
        SparseArray<int[]> freqsByPolicy = new SparseArray<>();
        File cpuFreqDir = new File(this.mCpuFreqDir);
        File[] policyDirs = cpuFreqDir.listFiles();
        if (policyDirs != null) {
            for (File policyDir : policyDirs) {
                Matcher matcher = POLICY_PATTERN.matcher(policyDir.getName());
                if (matcher.matches()) {
                    int[] relatedCpus = readIntsFromFile(new File(policyDir, FILE_NAME_RELATED_CPUS));
                    if (relatedCpus.length != 0) {
                        int[] availableFreqs = readIntsFromFile(new File(policyDir, FILE_NAME_SCALING_AVAILABLE_FREQUENCIES));
                        int[] boostFreqs = readIntsFromFile(new File(policyDir, FILE_NAME_SCALING_BOOST_FREQUENCIES));
                        if (boostFreqs.length == 0) {
                            freqs = availableFreqs;
                        } else {
                            freqs = Arrays.copyOf(availableFreqs, availableFreqs.length + boostFreqs.length);
                            System.arraycopy(boostFreqs, 0, freqs, availableFreqs.length, boostFreqs.length);
                        }
                        if (freqs.length == 0) {
                            freqs = readIntsFromFile(new File(policyDir, FILE_NAME_CPUINFO_CUR_FREQ));
                            if (freqs.length == 0) {
                                freqs = new int[]{0};
                            }
                        }
                        int policy = Integer.parseInt(matcher.group(1));
                        cpusByPolicy.put(policy, relatedCpus);
                        freqsByPolicy.put(policy, freqs);
                    }
                }
            }
        }
        if (cpusByPolicy.size() == 0) {
            cpusByPolicy.put(0, new int[]{0});
            freqsByPolicy.put(0, new int[]{0});
        }
        return new CpuScalingPolicies(cpusByPolicy, freqsByPolicy);
    }

    private static int[] readIntsFromFile(File file) {
        if (!file.exists()) {
            return EmptyArray.INT;
        }
        IntArray intArray = new IntArray(16);
        try {
            String contents = FileUtils.readTextFile(file, 0, null).trim();
            String[] strings = contents.split(" ");
            intArray.clear();
            for (String s : strings) {
                if (!s.isBlank()) {
                    try {
                        intArray.add(Integer.parseInt(s));
                    } catch (NumberFormatException e) {
                        Slog.e(TAG, "Unexpected file format " + file + ": " + contents, e);
                    }
                }
            }
            return intArray.toArray();
        } catch (IOException e2) {
            Slog.e(TAG, "Cannot read " + file, e2);
            return EmptyArray.INT;
        }
    }
}
