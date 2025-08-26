package com.android.internal.pm.pkg;

import com.android.server.pm.pkg.AndroidPackageSplit;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public class AndroidPackageSplitImpl implements AndroidPackageSplit {
    private final String mClassLoaderName;
    private List<AndroidPackageSplit> mDependencies = Collections.EMPTY_LIST;
    private final int mFlags;
    private final String mName;
    private final String mPath;
    private final int mRevisionCode;

    public AndroidPackageSplitImpl(String str, String str2, int i, int i2, String str3) {
        this.mName = str;
        this.mPath = str2;
        this.mRevisionCode = i;
        this.mFlags = i2;
        this.mClassLoaderName = str3;
    }

    public void fillDependencies(List<AndroidPackageSplit> list) {
        if (!this.mDependencies.isEmpty()) {
            throw new IllegalStateException("Cannot fill split dependencies more than once");
        }
        this.mDependencies = list;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public String getName() {
        return this.mName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public String getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public int getRevisionCode() {
        return this.mRevisionCode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public boolean isHasCode() {
        return (this.mFlags & 4) != 0;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public String getClassLoaderName() {
        return this.mClassLoaderName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public List<AndroidPackageSplit> getDependencies() {
        return this.mDependencies;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AndroidPackageSplitImpl)) {
            return false;
        }
        AndroidPackageSplitImpl androidPackageSplitImpl = (AndroidPackageSplitImpl) obj;
        if (this.mRevisionCode != androidPackageSplitImpl.mRevisionCode || this.mFlags != androidPackageSplitImpl.mFlags || !Objects.equals(this.mName, androidPackageSplitImpl.mName) || !Objects.equals(this.mPath, androidPackageSplitImpl.mPath) || !Objects.equals(this.mClassLoaderName, androidPackageSplitImpl.mClassLoaderName) || this.mDependencies.size() != androidPackageSplitImpl.mDependencies.size()) {
            return false;
        }
        for (int i = 0; i < this.mDependencies.size(); i++) {
            if (!Objects.equals(this.mDependencies.get(i).getName(), androidPackageSplitImpl.mDependencies.get(i).getName())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int iHash = Objects.hash(this.mName, this.mPath, Integer.valueOf(this.mRevisionCode), Integer.valueOf(this.mFlags), this.mClassLoaderName);
        for (int i = 0; i < this.mDependencies.size(); i++) {
            String name = this.mDependencies.get(i).getName();
            iHash = (iHash * 31) + (name == null ? 0 : name.hashCode());
        }
        return iHash;
    }
}
