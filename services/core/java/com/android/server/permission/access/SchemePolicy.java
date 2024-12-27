package com.android.server.permission.access;

import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.pm.pkg.PackageState;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SchemePolicy {
    public abstract String getObjectScheme();

    public abstract String getSubjectScheme();

    public void migrateSystemState(MutableAccessState mutableAccessState) {}

    public void migrateUserState(MutableAccessState mutableAccessState, int i) {}

    public void onAppIdRemoved(MutateStateScope mutateStateScope, int i) {}

    public void onPackageAdded(MutateStateScope mutateStateScope, PackageState packageState) {}

    public void onPackageInstalled(
            MutateStateScope mutateStateScope, PackageState packageState, int i) {}

    public void onPackageRemoved(MutateStateScope mutateStateScope, String str, int i) {}

    public void onPackageUninstalled(MutateStateScope mutateStateScope, String str, int i) {}

    public abstract void onStateMutated();

    public void onStorageVolumeMounted(MutateStateScope mutateStateScope, List list, boolean z) {}

    public void onSystemReady() {}

    public void onUserAdded(MutateStateScope mutateStateScope, int i) {}

    public void parseSystemState(
            BinaryXmlPullParser binaryXmlPullParser, MutableAccessState mutableAccessState) {}

    public abstract void parseUserState(
            BinaryXmlPullParser binaryXmlPullParser, MutableAccessState mutableAccessState, int i);

    public void serializeSystemState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState) {}

    public abstract void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i);

    public void upgradePackageState(
            MutateStateScope mutateStateScope, PackageState packageState, int i, int i2) {}
}
