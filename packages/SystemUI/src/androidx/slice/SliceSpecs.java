package androidx.slice;

/* loaded from: classes.dex */
public class SliceSpecs {
    public static final SliceSpec BASIC = new SliceSpec("androidx.slice.BASIC", 1);
    public static final SliceSpec LIST = new SliceSpec("androidx.slice.LIST", 1);
    public static final SliceSpec LIST_V2 = new SliceSpec("androidx.slice.LIST", 2);

    static {
        new SliceSpec("androidx.slice.MESSAGING", 1);
    }

    private SliceSpecs() {
    }
}
