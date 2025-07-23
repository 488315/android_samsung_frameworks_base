package android.graphics.rendererpolicy;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class BlockItem {
    private static final String ALL_CHIPSET = "ALL_CHIPSET";
    private static final String ALL_MODEL = "ALL_MODEL";
    private static final String ALL_PACKAGE = "ALL_PACKAGE";
    private final List<String> chipsetNames;
    private final List<String> modelNames;
    private final List<Integer> osVersions;
    private final String packageName;

    public BlockItem(String str, List<String> list, List<String> list2, List<Integer> list3) {
        this.packageName = str;
        this.modelNames = list;
        this.chipsetNames = list2;
        this.osVersions = list3;
    }

    public boolean isPackageNameMatched(String str) {
        String str2 = this.packageName;
        if (str2 == null) {
            return false;
        }
        return str.equals(str2) || ALL_PACKAGE.equals(this.packageName);
    }

    public boolean isModelNameMatched(String str) {
        List<String> list = this.modelNames;
        if (list != null && !list.isEmpty()) {
            String lowerCase = str.toLowerCase();
            for (String str2 : this.modelNames) {
                if (lowerCase.contains(str2.toLowerCase()) || ALL_MODEL.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isChipsetNameMatched(String str) {
        List<String> list = this.chipsetNames;
        if (list != null && !list.isEmpty()) {
            String lowerCase = str.toLowerCase();
            for (String str2 : this.chipsetNames) {
                if (lowerCase.contains(str2.toLowerCase()) || ALL_CHIPSET.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOsVersionMatched(int i) {
        List<Integer> list = this.osVersions;
        if (list != null && !list.isEmpty()) {
            Iterator<Integer> it = this.osVersions.iterator();
            while (it.hasNext()) {
                if (i == it.next().intValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isModelOrChipsetNameMatched(String str, String str2) {
        return isModelNameMatched(str) || isChipsetNameMatched(str2);
    }

    public boolean isBlockItemMatched(QueryInfo queryInfo) {
        return isPackageNameMatched(queryInfo.getPackageName()) && isModelOrChipsetNameMatched(queryInfo.getModelName(), queryInfo.getChipsetName()) && isOsVersionMatched(queryInfo.getOsVersion());
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
        return NavigationBarInflaterView.KEY_CODE_START + this.packageName + ", " + this.modelNames + ", " + this.chipsetNames + ", " + this.osVersions + NavigationBarInflaterView.KEY_CODE_END;
    }
}
