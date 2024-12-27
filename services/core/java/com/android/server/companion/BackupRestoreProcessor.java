package com.android.server.companion;

import android.companion.AssociationInfo;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.os.UserHandle;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.hidden_from_bootclasspath.android.companion.Flags;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.companion.association.AssociationDiskStore;
import com.android.server.companion.association.AssociationRequestsProcessor;
import com.android.server.companion.association.AssociationRequestsProcessor$$ExternalSyntheticLambda1;
import com.android.server.companion.association.AssociationStore;
import com.android.server.companion.association.Associations;
import com.android.server.companion.datatransfer.SystemDataTransferRequestStore;
import com.android.server.companion.utils.DataStoreUtils;
import com.android.server.companion.utils.RolesUtils;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupRestoreProcessor {
    public final AssociationDiskStore mAssociationDiskStore;
    public final AssociationRequestsProcessor mAssociationRequestsProcessor;
    public final AssociationStore mAssociationStore;
    public final Context mContext;
    public final PackageManagerInternal mPackageManagerInternal;
    public final SystemDataTransferRequestStore mSystemDataTransferRequestStore;

    public BackupRestoreProcessor(Context context, PackageManagerInternal packageManagerInternal, AssociationStore associationStore, AssociationDiskStore associationDiskStore, SystemDataTransferRequestStore systemDataTransferRequestStore, AssociationRequestsProcessor associationRequestsProcessor) {
        this.mContext = context;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mAssociationStore = associationStore;
        this.mAssociationDiskStore = associationDiskStore;
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
        this.mAssociationRequestsProcessor = associationRequestsProcessor;
    }

    public final void applyRestoredPayload(byte[] bArr, int i) {
        Associations associations;
        ArrayList arrayList;
        int i2;
        int i3;
        ByteArrayInputStream byteArrayInputStream;
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "applyRestoredPayload() userId=[", "], payload size=["), bArr.length, "].", "CDM_BackupRestoreProcessor");
        if (bArr.length == 0) {
            Slog.i("CDM_BackupRestoreProcessor", "CDM backup payload was empty.");
            return;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (wrap.getInt() != 0) {
            Slog.e("CDM_BackupRestoreProcessor", "Unsupported backup payload version");
            return;
        }
        try {
            byte[] bArr2 = new byte[wrap.getInt()];
            wrap.get(bArr2);
            byte[] bArr3 = new byte[wrap.getInt()];
            wrap.get(bArr3);
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr2);
                try {
                    associations = AssociationDiskStore.readAssociationsFromInputStream(i, byteArrayInputStream, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    byteArrayInputStream.close();
                } finally {
                }
            } catch (IOException | XmlPullParserException e) {
                Slog.e("CDM_AssociationDiskStore", "Error while reading associations file", e);
                associations = new Associations();
            }
            Associations associations2 = associations;
            SystemDataTransferRequestStore systemDataTransferRequestStore = this.mSystemDataTransferRequestStore;
            systemDataTransferRequestStore.getClass();
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr3);
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(byteArrayInputStream);
                    XmlUtils.beginDocument(resolvePullParser, "requests");
                    arrayList = SystemDataTransferRequestStore.readRequestsFromXml(i, resolvePullParser);
                    byteArrayInputStream.close();
                } finally {
                }
            } catch (IOException | XmlPullParserException e2) {
                Slog.e("CDM_SystemDataTransferRequestStore", "Error while reading requests file", e2);
                arrayList = new ArrayList();
            }
            List installedApplications = this.mPackageManagerInternal.getInstalledApplications(i, UserHandle.getCallingUserId(), 0L);
            for (final AssociationInfo associationInfo : associations2.mAssociations) {
                if (!associationInfo.isRevoked()) {
                    final int i4 = 0;
                    List<SystemDataTransferRequest> filter = CollectionUtils.filter(arrayList, new Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i5 = i4;
                            AssociationInfo associationInfo2 = associationInfo;
                            switch (i5) {
                                case 0:
                                    return ((SystemDataTransferRequest) obj).getAssociationId() == associationInfo2.getId();
                                default:
                                    AssociationInfo associationInfo3 = (AssociationInfo) obj;
                                    return Objects.equals(associationInfo3.getDeviceMacAddress(), associationInfo2.getDeviceMacAddress()) && (!Flags.associationTag() || Objects.equals(associationInfo3.getTag(), associationInfo2.getTag()));
                            }
                        }
                    });
                    int userId = associationInfo.getUserId();
                    String packageName = associationInfo.getPackageName();
                    AssociationStore associationStore = this.mAssociationStore;
                    final int i5 = 1;
                    AssociationInfo associationInfo2 = (AssociationInfo) CollectionUtils.find(associationStore.getActiveAssociationsByPackage(userId, packageName), new Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i52 = i5;
                            AssociationInfo associationInfo22 = associationInfo;
                            switch (i52) {
                                case 0:
                                    return ((SystemDataTransferRequest) obj).getAssociationId() == associationInfo22.getId();
                                default:
                                    AssociationInfo associationInfo3 = (AssociationInfo) obj;
                                    return Objects.equals(associationInfo3.getDeviceMacAddress(), associationInfo22.getDeviceMacAddress()) && (!Flags.associationTag() || Objects.equals(associationInfo3.getTag(), associationInfo22.getTag()));
                            }
                        }
                    });
                    if (associationInfo2 == null) {
                        final String packageName2 = associationInfo.getPackageName();
                        synchronized (associationStore.mLock) {
                            synchronized (associationStore.mLock) {
                                i2 = associationStore.mMaxId;
                            }
                            i3 = i2 + 1;
                        }
                        AssociationInfo build = new AssociationInfo.Builder(i3, i, packageName2, associationInfo).build();
                        final int i6 = 0;
                        if (installedApplications.stream().anyMatch(new Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i7 = i6;
                                Object obj2 = packageName2;
                                switch (i7) {
                                    case 0:
                                        return ((String) obj2).equals(((ApplicationInfo) obj).packageName);
                                    default:
                                        return ((SystemDataTransferRequest) obj).getDataType() == ((SystemDataTransferRequest) obj2).getDataType();
                                }
                            }
                        })) {
                            AssociationRequestsProcessor associationRequestsProcessor = this.mAssociationRequestsProcessor;
                            RolesUtils.addRoleHolderForAssociation(associationRequestsProcessor.mContext, build, new AssociationRequestsProcessor$$ExternalSyntheticLambda1(associationRequestsProcessor, build, null, null));
                        } else {
                            associationStore.addAssociation(new AssociationInfo.Builder(build).setPending(true).build());
                        }
                        Iterator it = filter.iterator();
                        while (it.hasNext()) {
                            SystemDataTransferRequest copyWithNewId = ((SystemDataTransferRequest) it.next()).copyWithNewId(i3);
                            copyWithNewId.setUserId(i);
                            systemDataTransferRequestStore.writeRequest(i, copyWithNewId);
                        }
                    } else {
                        Slog.d("CDM_BackupRestoreProcessor", "Conflict detected with association id=" + associationInfo2.getId() + " while restoring CDM backup. Keeping local association.");
                        List readRequestsByAssociationId = systemDataTransferRequestStore.readRequestsByAssociationId(i, associationInfo2.getId());
                        for (final SystemDataTransferRequest systemDataTransferRequest : filter) {
                            final int i7 = 1;
                            if (!CollectionUtils.any(readRequestsByAssociationId, new Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda2
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i72 = i7;
                                    Object obj2 = systemDataTransferRequest;
                                    switch (i72) {
                                        case 0:
                                            return ((String) obj2).equals(((ApplicationInfo) obj).packageName);
                                        default:
                                            return ((SystemDataTransferRequest) obj).getDataType() == ((SystemDataTransferRequest) obj2).getDataType();
                                    }
                                }
                            })) {
                                Slog.d("CDM_BackupRestoreProcessor", "Restoring " + systemDataTransferRequest.getClass().getSimpleName() + " to an existing association id=[" + associationInfo2.getId() + "].");
                                SystemDataTransferRequest copyWithNewId2 = systemDataTransferRequest.copyWithNewId(associationInfo2.getId());
                                copyWithNewId2.setUserId(i);
                                systemDataTransferRequestStore.writeRequest(i, copyWithNewId2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e3) {
            Slog.e("CDM_BackupRestoreProcessor", "CDM backup payload was mal-formatted.", e3);
        }
    }

    public final byte[] getBackupPayload(int i) {
        byte[] fileToByteArray;
        byte[] fileToByteArray2;
        BootReceiver$$ExternalSyntheticOutline0.m(i, "getBackupPayload() userId=[", "].", "CDM_BackupRestoreProcessor");
        AssociationDiskStore associationDiskStore = this.mAssociationDiskStore;
        associationDiskStore.getClass();
        Slog.i("CDM_AssociationDiskStore", "Fetching stored state data for user " + i + " from disk");
        AtomicFile storageFileForUser = associationDiskStore.getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            fileToByteArray = DataStoreUtils.fileToByteArray(storageFileForUser);
        }
        int length = fileToByteArray.length;
        AtomicFile storageFileForUser2 = this.mSystemDataTransferRequestStore.getStorageFileForUser(i);
        synchronized (storageFileForUser2) {
            fileToByteArray2 = DataStoreUtils.fileToByteArray(storageFileForUser2);
        }
        int length2 = fileToByteArray2.length;
        return ByteBuffer.allocate(length + 12 + length2).putInt(0).putInt(length).put(fileToByteArray).putInt(length2).put(fileToByteArray2).array();
    }
}
