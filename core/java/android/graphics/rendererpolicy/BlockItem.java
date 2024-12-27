package android.graphics.rendererpolicy;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import java.util.Iterator;
import java.util.List;

public class BlockItem {
    private static final String ALL_CHIPSET = "ALL_CHIPSET";
    private static final String ALL_MODEL = "ALL_MODEL";
    private static final String ALL_PACKAGE = "ALL_PACKAGE";
    private final List<String> chipsetNames;
    private final List<String> modelNames;
    private final List<Integer> osVersions;
    private final String packageName;

    public BlockItem(
            String packageName,
            List<String> modelNames,
            List<String> chipsetNames,
            List<Integer> osVersions) {
        this.packageName = packageName;
        this.modelNames = modelNames;
        this.chipsetNames = chipsetNames;
        this.osVersions = osVersions;
    }

    public boolean isPackageNameMatched(String queryPackageName) {
        if (this.packageName == null) {
            return false;
        }
        return queryPackageName.equals(this.packageName) || ALL_PACKAGE.equals(this.packageName);
    }

    public boolean isModelNameMatched(String queryModelName) {
        if (this.modelNames == null || this.modelNames.isEmpty()) {
            return false;
        }
        String lowerCaseQueryModel = queryModelName.toLowerCase();
        for (String configModel : this.modelNames) {
            if (lowerCaseQueryModel.contains(configModel.toLowerCase())
                    || ALL_MODEL.equals(configModel)) {
                return true;
            }
        }
        return false;
    }

    public boolean isChipsetNameMatched(String queryChipsetName) {
        if (this.chipsetNames == null || this.chipsetNames.isEmpty()) {
            return false;
        }
        String lowerCaseQueryChipset = queryChipsetName.toLowerCase();
        for (String configChipset : this.chipsetNames) {
            if (lowerCaseQueryChipset.contains(configChipset.toLowerCase())
                    || ALL_CHIPSET.equals(configChipset)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOsVersionMatched(int queryOsVersion) {
        if (this.osVersions == null || this.osVersions.isEmpty()) {
            return false;
        }
        Iterator<Integer> it = this.osVersions.iterator();
        while (it.hasNext()) {
            int osVersion = it.next().intValue();
            if (queryOsVersion == osVersion) {
                return true;
            }
        }
        return false;
    }

    public boolean isModelOrChipsetNameMatched(String queryModelName, String queryChipsetName) {
        return isModelNameMatched(queryModelName) || isChipsetNameMatched(queryChipsetName);
    }

    public boolean isBlockItemMatched(QueryInfo queryInfo) {
        return isPackageNameMatched(queryInfo.getPackageName())
                && isModelOrChipsetNameMatched(queryInfo.getModelName(), queryInfo.getChipsetName())
                && isOsVersionMatched(queryInfo.getOsVersion());
    }

    public String getPackageName() {
        return this.packageName;
    }

    public List<String> getModelNames() {
        return this.modelNames;
    }

    public List<String> getChipsetNames() {
        return this.chipsetNames;
    }

    public List<Integer> getOsVersions() {
        return this.osVersions;
    }

    public String toString() {
        return NavigationBarInflaterView.KEY_CODE_START
                + this.packageName
                + ", "
                + this.modelNames
                + ", "
                + this.chipsetNames
                + ", "
                + this.osVersions
                + NavigationBarInflaterView.KEY_CODE_END;
    }
}
