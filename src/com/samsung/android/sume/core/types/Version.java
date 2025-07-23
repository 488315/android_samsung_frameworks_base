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

    public Version(int i, int i2, int i3) {
        this.major = i;
        this.minor = i2;
        this.patch = i3;
    }

    public Version(int i) {
        this(i, 0, 0);
    }

    public Version(int i, int i2) {
        this(i, i2, 0);
    }

    public Version(String str) {
        List list = (List) Arrays.stream(str.replaceAll("^[^0-9]", "").split("\\.")).map(new MediaBufferFileReader$$ExternalSyntheticLambda0()).collect(Collectors.toList());
        Def.require(!list.isEmpty() && list.size() <= 3, "version should be given major.{minor}.{patch} format(ex: 1, 1.0, 1.0.0)", new Object[0]);
        Integer[] numArr = new Integer[3 - list.size()];
        Arrays.fill((Object[]) numArr, (Object) 0);
        List list2 = (List) Stream.concat(list.stream(), Stream.of((Object[]) numArr)).collect(Collectors.toList());
        this.major = ((Integer) list2.get(0)).intValue();
        this.minor = ((Integer) list2.get(1)).intValue();
        this.patch = ((Integer) list2.get(2)).intValue();
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
        return Def.fmtstr("%d.%d.%d", Integer.valueOf(this.major), Integer.valueOf(this.minor), Integer.valueOf(this.patch));
    }
}
