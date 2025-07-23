package android.drm;

import java.util.ArrayList;
import java.util.Iterator;

@Deprecated
/* loaded from: classes.dex */
public class DrmSupportInfo {
    private final ArrayList<String> mFileSuffixList = new ArrayList<>();
    private final ArrayList<String> mMimeTypeList = new ArrayList<>();
    private String mDescription = "";

    public void addMimeType(String str) {
        if (str == null) {
            throw new IllegalArgumentException("mimeType is null");
        }
        if (str == "") {
            throw new IllegalArgumentException("mimeType is an empty string");
        }
        this.mMimeTypeList.add(str);
    }

    public void addFileSuffix(String str) {
        if (str == "") {
            throw new IllegalArgumentException("fileSuffix is an empty string");
        }
        this.mFileSuffixList.add(str);
    }

    public Iterator<String> getMimeTypeIterator() {
        return this.mMimeTypeList.iterator();
    }

    public Iterator<String> getFileSuffixIterator() {
        return this.mFileSuffixList.iterator();
    }

    public void setDescription(String str) {
        if (str == null) {
            throw new IllegalArgumentException("description is null");
        }
        if (str == "") {
            throw new IllegalArgumentException("description is an empty string");
        }
        this.mDescription = str;
    }

    public String getDescriprition() {
        return this.mDescription;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int hashCode() {
        return this.mFileSuffixList.hashCode() + this.mMimeTypeList.hashCode() + this.mDescription.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof DrmSupportInfo) {
            DrmSupportInfo drmSupportInfo = (DrmSupportInfo) obj;
            if (this.mFileSuffixList.equals(drmSupportInfo.mFileSuffixList) && this.mMimeTypeList.equals(drmSupportInfo.mMimeTypeList) && this.mDescription.equals(drmSupportInfo.mDescription)) {
                return true;
            }
        }
        return false;
    }

    boolean isSupportedMimeType(String str) {
        if (str != null && !str.equals("")) {
            for (int i = 0; i < this.mMimeTypeList.size(); i++) {
                if (this.mMimeTypeList.get(i).startsWith(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isSupportedFileSuffix(String str) {
        return this.mFileSuffixList.contains(str);
    }
}
