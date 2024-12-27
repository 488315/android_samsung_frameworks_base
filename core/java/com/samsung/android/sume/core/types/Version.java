package com.samsung.android.sume.core.types;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda0;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class Version implements Serializable {
    private static final int MAXNUM_VERSION_UNITS = 3;
    private final int major;
    private final int minor;
    private final int patch;

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public Version(int major) {
        this(major, 0, 0);
    }

    public Version(int major, int minor) {
        this(major, minor, 0);
    }

    public Version(String version) {
        List<Integer> versions =
                (List)
                        Arrays.stream(version.replaceAll("^[^0-9]", "").split("\\."))
                                .map(new MediaBufferFileReader$$ExternalSyntheticLambda0())
                                .collect(Collectors.toList());
        Def.require(
                !versions.isEmpty() && versions.size() <= 3,
                "version should be given major.{minor}.{patch} format(ex: 1, 1.0, 1.0.0)",
                new Object[0]);
        Integer[] additional = new Integer[3 - versions.size()];
        Arrays.fill((Object[]) additional, (Object) 0);
        List<Integer> versions2 =
                (List)
                        Stream.concat(versions.stream(), Stream.of((Object[]) additional))
                                .collect(Collectors.toList());
        this.major = versions2.get(0).intValue();
        this.minor = versions2.get(1).intValue();
        this.patch = versions2.get(2).intValue();
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getPatch() {
        return this.patch;
    }

    public String toString() {
        return Def.fmtstr(
                "%d.%d.%d",
                Integer.valueOf(this.major),
                Integer.valueOf(this.minor),
                Integer.valueOf(this.patch));
    }
}
