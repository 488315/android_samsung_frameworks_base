package android.graphics.rendererpolicy;

public class QueryInfo {
    private final String chipsetName;
    private final String modelName;
    private final int osVersion;
    private final String packageName;

    public QueryInfo(String packageName, String modelName, String chipsetName, int osVersion) {
        this.packageName = packageName;
        this.modelName = modelName;
        this.chipsetName = chipsetName;
        this.osVersion = osVersion;
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
