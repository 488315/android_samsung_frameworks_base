package com.android.internal.pm.pkg;

import com.android.server.pm.pkg.AndroidPackageSplit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public class AndroidPackageSplitImpl implements AndroidPackageSplit {
    private final String mClassLoaderName;
    private List<AndroidPackageSplit> mDependencies = Collections.emptyList();
    private final int mFlags;
    private final String mName;
    private final String mPath;
    private final int mRevisionCode;

    public AndroidPackageSplitImpl(
            String name, String path, int revisionCode, int flags, String classLoaderName) {
        this.mName = name;
        this.mPath = path;
        this.mRevisionCode = revisionCode;
        this.mFlags = flags;
        this.mClassLoaderName = classLoaderName;
    }

    public void fillDependencies(List<AndroidPackageSplit> splits) {
        if (!this.mDependencies.isEmpty()) {
            throw new IllegalStateException("Cannot fill split dependencies more than once");
        }
        this.mDependencies = splits;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AndroidPackageSplitImpl)) {
            return false;
        }
        AndroidPackageSplitImpl that = (AndroidPackageSplitImpl) o;
        boolean fieldsEqual =
                this.mRevisionCode == that.mRevisionCode
                        && this.mFlags == that.mFlags
                        && Objects.equals(this.mName, that.mName)
                        && Objects.equals(this.mPath, that.mPath)
                        && Objects.equals(this.mClassLoaderName, that.mClassLoaderName);
        if (!fieldsEqual || this.mDependencies.size() != that.mDependencies.size()) {
            return false;
        }
        for (int index = 0; index < this.mDependencies.size(); index++) {
            if (!Objects.equals(
                    this.mDependencies.get(index).getName(),
                    that.mDependencies.get(index).getName())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int dependenciesHash =
                Objects.hash(
                        this.mName,
                        this.mPath,
                        Integer.valueOf(this.mRevisionCode),
                        Integer.valueOf(this.mFlags),
                        this.mClassLoaderName);
        for (int index = 0; index < this.mDependencies.size(); index++) {
            String name = this.mDependencies.get(index).getName();
            dependenciesHash = (dependenciesHash * 31) + (name == null ? 0 : name.hashCode());
        }
        return dependenciesHash;
    }
}
