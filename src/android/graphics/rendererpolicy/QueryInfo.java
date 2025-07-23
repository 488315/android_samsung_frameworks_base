package android.graphics.rendererpolicy;

/* loaded from: classes.dex */
public class QueryInfo {
    private final String chipsetName;
    private final String modelName;
    private final int osVersion;
    private final String packageName;

    public QueryInfo(String str, String str2, String str3, int i) {
        this.packageName = str;
        this.modelName = str2;
        this.chipsetName = str3;
        this.osVersion = i;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getChipsetName() {
        return this.chipsetName;
    }

    public int getOsVersion() {
        return this.osVersion;
    }
}
