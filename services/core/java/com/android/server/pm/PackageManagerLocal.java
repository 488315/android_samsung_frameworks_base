package com.android.server.pm;

import android.annotation.SystemApi;
import android.content.pm.SigningDetails;
import android.os.UserHandle;

import com.android.server.pm.pkg.PackageState;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface PackageManagerLocal {
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public interface FilteredSnapshot extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();

        PackageState getPackageState(String str);

        Map getPackageStates();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @Retention(RetentionPolicy.SOURCE)
    public @interface StorageFlags {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public interface UnfilteredSnapshot extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();

        FilteredSnapshot filtered(int i, UserHandle userHandle);

        Map getDisabledSystemPackageStates();

        Map getPackageStates();

        Map getSharedUsers();
    }

    void addOverrideSigningDetails(SigningDetails signingDetails, SigningDetails signingDetails2);

    void clearOverrideSigningDetails();

    void reconcileSdkData(
            String str, String str2, List list, int i, int i2, int i3, String str3, int i4)
            throws IOException;

    void removeOverrideSigningDetails(SigningDetails signingDetails);

    FilteredSnapshot withFilteredSnapshot();

    FilteredSnapshot withFilteredSnapshot(int i, UserHandle userHandle);

    UnfilteredSnapshot withUnfilteredSnapshot();
}
