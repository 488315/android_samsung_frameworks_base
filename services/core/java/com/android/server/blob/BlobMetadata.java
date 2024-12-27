package com.android.server.blob;

import android.app.blob.BlobHandle;
import android.app.blob.LeaseInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RevocableFileDescriptor;
import android.os.UserHandle;
import android.text.format.TimeMigrationUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;

import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BlobMetadata {
    public File mBlobFile;
    public final BlobHandle mBlobHandle;
    public final long mBlobId;
    public final Context mContext;
    public final Object mMetadataLock = new Object();
    public final ArraySet mCommitters = new ArraySet();
    public final ArraySet mLeasees = new ArraySet();
    public final ArrayMap mRevocableFds = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Accessor {
        public final String packageName;
        public final int uid;

        public Accessor(String str, int i) {
            this.packageName = str;
            this.uid = i;
        }

        public final boolean equals(int i, String str) {
            return this.uid == i && this.packageName.equals(str);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Accessor)) {
                return false;
            }
            Accessor accessor = (Accessor) obj;
            return this.uid == accessor.uid && this.packageName.equals(accessor.packageName);
        }

        public final int hashCode() {
            return Objects.hash(this.packageName, Integer.valueOf(this.uid));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            sb.append(this.packageName);
            sb.append(", ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, "]");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Committer extends Accessor {
        public final BlobAccessMode blobAccessMode;
        public final long commitTimeMs;

        public Committer(String str, int i, BlobAccessMode blobAccessMode, long j) {
            super(str, i);
            this.blobAccessMode = blobAccessMode;
            this.commitTimeMs = j;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            StringBuilder sb = new StringBuilder("commit time: ");
            long j = this.commitTimeMs;
            sb.append(j == 0 ? "<null>" : TimeMigrationUtils.formatMillisWithFixedFormat(j));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("accessMode:");
            indentingPrintWriter.increaseIndent();
            this.blobAccessMode.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Leasee extends Accessor {
        public final CharSequence description;
        public final String descriptionResEntryName;
        public final long expiryTimeMillis;

        public Leasee(
                Context context, String str, int i, int i2, CharSequence charSequence, long j) {
            super(str, i);
            Resources packageResources =
                    BlobStoreUtils.getPackageResources(context, str, UserHandle.getUserId(i));
            this.descriptionResEntryName =
                    (!ResourceId.isValid(i2) || packageResources == null)
                            ? null
                            : packageResources.getResourceEntryName(i2);
            this.expiryTimeMillis = j;
            this.description =
                    charSequence == null
                            ? (!ResourceId.isValid(i2) || packageResources == null)
                                    ? null
                                    : packageResources.getString(i2)
                            : charSequence;
        }

        public Leasee(String str, int i, String str2, CharSequence charSequence, long j) {
            super(str, i);
            this.descriptionResEntryName = str2;
            this.expiryTimeMillis = j;
            this.description = charSequence;
        }

        public final void dump(Context context, IndentingPrintWriter indentingPrintWriter) {
            String str;
            Resources packageResources;
            int identifier;
            int userId = UserHandle.getUserId(this.uid);
            String str2 = this.descriptionResEntryName;
            String str3 = null;
            if (str2 != null
                    && !str2.isEmpty()
                    && (packageResources =
                                    BlobStoreUtils.getPackageResources(
                                            context, (str = this.packageName), userId))
                            != null
                    && (identifier = packageResources.getIdentifier(str2, "string", str)) != 0) {
                str3 = packageResources.getString(identifier);
            }
            if (str3 == null) {
                str3 = this.description.toString();
            }
            if (str3 == null) {
                str3 = "<none>";
            }
            indentingPrintWriter.println("desc: ".concat(str3));
            indentingPrintWriter.println("expiryMs: " + this.expiryTimeMillis);
        }

        public final boolean isStillValid() {
            long j = this.expiryTimeMillis;
            return j == 0 || j >= System.currentTimeMillis();
        }

        public final void writeToXml(XmlSerializer xmlSerializer) {
            XmlUtils.writeStringAttribute(
                    xmlSerializer, KnoxAnalyticsDataConverter.PAYLOAD, this.packageName);
            XmlUtils.writeIntAttribute(xmlSerializer, "u", this.uid);
            XmlUtils.writeStringAttribute(xmlSerializer, "rn", this.descriptionResEntryName);
            XmlUtils.writeLongAttribute(xmlSerializer, "ex", this.expiryTimeMillis);
            XmlUtils.writeStringAttribute(xmlSerializer, "d", this.description);
        }
    }

    public BlobMetadata(Context context, long j, BlobHandle blobHandle) {
        this.mContext = context;
        this.mBlobId = j;
        this.mBlobHandle = blobHandle;
    }

    public static BlobMetadata createFromXml(XmlPullParser xmlPullParser, int i, Context context) {
        Committer committer;
        long readLongAttribute = XmlUtils.readLongAttribute(xmlPullParser, "id");
        if (i < 6) {
            XmlUtils.readIntAttribute(xmlPullParser, "us");
        }
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        int depth = xmlPullParser.getDepth();
        BlobHandle blobHandle = null;
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("bh".equals(xmlPullParser.getName())) {
                blobHandle = BlobHandle.createFromXml(xmlPullParser);
            } else if ("c".equals(xmlPullParser.getName())) {
                String readStringAttribute =
                        XmlUtils.readStringAttribute(
                                xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD);
                int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "u");
                long readLongAttribute2 =
                        i >= 4 ? XmlUtils.readLongAttribute(xmlPullParser, "cmt") : 0L;
                int depth2 = xmlPullParser.getDepth();
                BlobAccessMode blobAccessMode = null;
                while (XmlUtils.nextElementWithin(xmlPullParser, depth2)) {
                    if ("am".equals(xmlPullParser.getName())) {
                        blobAccessMode = BlobAccessMode.createFromXml(xmlPullParser);
                    }
                }
                if (blobAccessMode == null) {
                    Slog.wtf("BlobStore", "blobAccessMode should be available");
                    committer = null;
                } else {
                    committer =
                            new Committer(
                                    readStringAttribute,
                                    readIntAttribute,
                                    blobAccessMode,
                                    readLongAttribute2);
                }
                if (committer != null) {
                    arraySet.add(committer);
                }
            } else if ("l".equals(xmlPullParser.getName())) {
                arraySet2.add(
                        new Leasee(
                                XmlUtils.readStringAttribute(
                                        xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD),
                                XmlUtils.readIntAttribute(xmlPullParser, "u"),
                                i >= 3 ? XmlUtils.readStringAttribute(xmlPullParser, "rn") : null,
                                i >= 2 ? XmlUtils.readStringAttribute(xmlPullParser, "d") : null,
                                XmlUtils.readLongAttribute(xmlPullParser, "ex")));
            }
        }
        if (blobHandle == null) {
            Slog.wtf("BlobStore", "blobHandle should be available");
            return null;
        }
        BlobMetadata blobMetadata = new BlobMetadata(context, readLongAttribute, blobHandle);
        synchronized (blobMetadata.mMetadataLock) {
            blobMetadata.mCommitters.clear();
            blobMetadata.mCommitters.addAll(arraySet);
        }
        synchronized (blobMetadata.mMetadataLock) {
            blobMetadata.mLeasees.clear();
            blobMetadata.mLeasees.addAll(arraySet2);
        }
        return blobMetadata;
    }

    public static Accessor getAccessor(ArraySet arraySet, String str, int i, int i2) {
        int size = arraySet.size();
        for (int i3 = 0; i3 < size; i3++) {
            Accessor accessor = (Accessor) arraySet.valueAt(i3);
            if (str != null && i != -1 && accessor.equals(i, str)) {
                return accessor;
            }
            if (str != null
                    && accessor.packageName.equals(str)
                    && i2 == UserHandle.getUserId(accessor.uid)) {
                return accessor;
            }
            if (i != -1 && accessor.uid == i) {
                return accessor;
            }
        }
        return null;
    }

    public final void addOrReplaceCommitter(Committer committer) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.remove(committer);
            this.mCommitters.add(committer);
        }
    }

    public final ParcelFileDescriptor createRevocableFd(
            FileDescriptor fileDescriptor, String str, int i) {
        final Accessor accessor;
        final RevocableFileDescriptor revocableFileDescriptor =
                new RevocableFileDescriptor(
                        this.mContext, fileDescriptor, BlobStoreUtils.getRevocableFdHandler());
        synchronized (this.mRevocableFds) {
            try {
                accessor = new Accessor(str, i);
                ArraySet arraySet = (ArraySet) this.mRevocableFds.get(accessor);
                if (arraySet == null) {
                    arraySet = new ArraySet();
                    this.mRevocableFds.put(accessor, arraySet);
                }
                arraySet.add(revocableFileDescriptor);
            } catch (Throwable th) {
                throw th;
            }
        }
        revocableFileDescriptor.addOnCloseListener(
                new ParcelFileDescriptor
                        .OnCloseListener() { // from class:
                                             // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda8
                    @Override // android.os.ParcelFileDescriptor.OnCloseListener
                    public final void onClose(IOException iOException) {
                        BlobMetadata blobMetadata = BlobMetadata.this;
                        BlobMetadata.Accessor accessor2 = accessor;
                        RevocableFileDescriptor revocableFileDescriptor2 = revocableFileDescriptor;
                        synchronized (blobMetadata.mRevocableFds) {
                            try {
                                ArraySet arraySet2 =
                                        (ArraySet) blobMetadata.mRevocableFds.get(accessor2);
                                if (arraySet2 != null) {
                                    arraySet2.remove(revocableFileDescriptor2);
                                    if (arraySet2.isEmpty()) {
                                        blobMetadata.mRevocableFds.remove(accessor2);
                                    }
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                });
        return revocableFileDescriptor.getRevocableFileDescriptor();
    }

    public final File getBlobFile() {
        if (this.mBlobFile == null) {
            boolean z = BlobStoreConfig.LOGV;
            this.mBlobFile =
                    new File(
                            new File(
                                    new File(Environment.getDataSystemDirectory(), "blobstore"),
                                    "blobs"),
                            String.valueOf(this.mBlobId));
        }
        return this.mBlobFile;
    }

    public final LeaseInfo getLeaseInfo(int i, String str) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    Leasee leasee = (Leasee) this.mLeasees.valueAt(i3);
                    if (leasee.isStillValid()
                            && leasee.uid == i
                            && leasee.packageName.equals(str)) {
                        String str2 = leasee.descriptionResEntryName;
                        if (str2 != null) {
                            Context context = this.mContext;
                            String str3 = leasee.packageName;
                            Resources packageResources =
                                    BlobStoreUtils.getPackageResources(
                                            context, str3, UserHandle.getUserId(leasee.uid));
                            if (packageResources != null) {
                                i2 = packageResources.getIdentifier(str2, "string", str3);
                            }
                        }
                        return new LeaseInfo(str, leasee.expiryTimeMillis, i2, leasee.description);
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long getSize() {
        return getBlobFile().length();
    }

    public final boolean hasACommitterInUser(int i) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mCommitters.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (i == UserHandle.getUserId(((Committer) this.mCommitters.valueAt(i2)).uid)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasLeaseWaitTimeElapsedForAll() {
        int size = this.mCommitters.size();
        for (int i = 0; i < size; i++) {
            long j = ((Committer) this.mCommitters.valueAt(i)).commitTimeMs;
            boolean z = BlobStoreConfig.LOGV;
            if (j + BlobStoreConfig.DeviceConfigProperties.LEASE_ACQUISITION_WAIT_DURATION_MS
                    >= System.currentTimeMillis()) {
                return false;
            }
        }
        return true;
    }

    public final boolean hasOtherLeasees(int i, int i2, String str) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i3 = 0; i3 < size; i3++) {
                    Leasee leasee = (Leasee) this.mLeasees.valueAt(i3);
                    if (leasee.isStillValid()) {
                        if (str != null && i != -1 && !leasee.equals(i, str)) {
                            return true;
                        }
                        if (str != null
                                && (!leasee.packageName.equals(str)
                                        || i2 != UserHandle.getUserId(leasee.uid))) {
                            return true;
                        }
                        if (i != -1 && leasee.uid != i) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasValidLeases() {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i = 0; i < size; i++) {
                    if (((Leasee) this.mLeasees.valueAt(i)).isStillValid()) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isACommitter(int i, String str) {
        boolean z;
        synchronized (this.mMetadataLock) {
            z = getAccessor(this.mCommitters, str, i, UserHandle.getUserId(i)) != null;
        }
        return z;
    }

    public final boolean isALeasee(int i, String str) {
        boolean z;
        synchronized (this.mMetadataLock) {
            try {
                Leasee leasee =
                        (Leasee) getAccessor(this.mLeasees, str, i, UserHandle.getUserId(i));
                z = leasee != null && leasee.isStillValid();
            } finally {
            }
        }
        return z;
    }

    public final boolean isALeaseeInUser(int i, int i2, String str) {
        boolean z;
        synchronized (this.mMetadataLock) {
            try {
                Leasee leasee = (Leasee) getAccessor(this.mLeasees, str, i, i2);
                z = leasee != null && leasee.isStillValid();
            } finally {
            }
        }
        return z;
    }

    public final boolean isAccessAllowedForCaller(int i, String str) {
        if (this.mBlobHandle.isExpired()) {
            return false;
        }
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Leasee leasee = (Leasee) this.mLeasees.valueAt(i2);
                    if (leasee.isStillValid() && leasee.equals(i, str)) {
                        return true;
                    }
                }
                int userId = UserHandle.getUserId(i);
                int size2 = this.mCommitters.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Committer committer = (Committer) this.mCommitters.valueAt(i3);
                    if (userId == UserHandle.getUserId(committer.uid)) {
                        if (committer.equals(i, str)) {
                            return true;
                        }
                        if (committer.blobAccessMode.isAccessAllowedForCaller(
                                this.mContext, str, i, committer.uid)) {
                            return true;
                        }
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!(this.mContext.checkPermission(
                                    "android.permission.ACCESS_BLOBS_ACROSS_USERS", -1, i)
                            == 0)) {
                        return false;
                    }
                    int size3 = this.mCommitters.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        Committer committer2 = (Committer) this.mCommitters.valueAt(i4);
                        int userId2 = UserHandle.getUserId(committer2.uid);
                        if (userId != userId2) {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                this.mContext
                                        .getPackageManager()
                                        .getPackageInfoAsUser(str, 0, userId2);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                if (committer2.blobAccessMode.isAccessAllowedForCaller(
                                        this.mContext, str, i, committer2.uid)) {
                                    return true;
                                }
                            } catch (PackageManager.NameNotFoundException unused) {
                            } finally {
                            }
                        }
                    }
                    return false;
                } finally {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeCommitter(Committer committer) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.remove(committer);
        }
    }

    public final void removeDataForUser(final int i) {
        synchronized (this.mMetadataLock) {
            final int i2 = 0;
            this.mCommitters.removeIf(
                    new Predicate() { // from class:
                                      // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i3 = i2;
                            int i4 = i;
                            switch (i3) {
                                case 0:
                                    return i4
                                            == UserHandle.getUserId(
                                                    ((BlobMetadata.Committer) obj).uid);
                                case 1:
                                    return i4
                                            == UserHandle.getUserId(
                                                    ((BlobMetadata.Leasee) obj).uid);
                                default:
                                    Map.Entry entry = (Map.Entry) obj;
                                    BlobMetadata.Accessor accessor =
                                            (BlobMetadata.Accessor) entry.getKey();
                                    ArraySet arraySet = (ArraySet) entry.getValue();
                                    if (i4 != UserHandle.getUserId(accessor.uid)) {
                                        return false;
                                    }
                                    int size = arraySet.size();
                                    for (int i5 = 0; i5 < size; i5++) {
                                        ((RevocableFileDescriptor) arraySet.valueAt(i5)).revoke();
                                    }
                                    arraySet.clear();
                                    return true;
                            }
                        }
                    });
            final int i3 = 1;
            this.mLeasees.removeIf(
                    new Predicate() { // from class:
                                      // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i32 = i3;
                            int i4 = i;
                            switch (i32) {
                                case 0:
                                    return i4
                                            == UserHandle.getUserId(
                                                    ((BlobMetadata.Committer) obj).uid);
                                case 1:
                                    return i4
                                            == UserHandle.getUserId(
                                                    ((BlobMetadata.Leasee) obj).uid);
                                default:
                                    Map.Entry entry = (Map.Entry) obj;
                                    BlobMetadata.Accessor accessor =
                                            (BlobMetadata.Accessor) entry.getKey();
                                    ArraySet arraySet = (ArraySet) entry.getValue();
                                    if (i4 != UserHandle.getUserId(accessor.uid)) {
                                        return false;
                                    }
                                    int size = arraySet.size();
                                    for (int i5 = 0; i5 < size; i5++) {
                                        ((RevocableFileDescriptor) arraySet.valueAt(i5)).revoke();
                                    }
                                    arraySet.clear();
                                    return true;
                            }
                        }
                    });
            final int i4 = 2;
            this.mRevocableFds
                    .entrySet()
                    .removeIf(
                            new Predicate() { // from class:
                                              // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda5
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i32 = i4;
                                    int i42 = i;
                                    switch (i32) {
                                        case 0:
                                            return i42
                                                    == UserHandle.getUserId(
                                                            ((BlobMetadata.Committer) obj).uid);
                                        case 1:
                                            return i42
                                                    == UserHandle.getUserId(
                                                            ((BlobMetadata.Leasee) obj).uid);
                                        default:
                                            Map.Entry entry = (Map.Entry) obj;
                                            BlobMetadata.Accessor accessor =
                                                    (BlobMetadata.Accessor) entry.getKey();
                                            ArraySet arraySet = (ArraySet) entry.getValue();
                                            if (i42 != UserHandle.getUserId(accessor.uid)) {
                                                return false;
                                            }
                                            int size = arraySet.size();
                                            for (int i5 = 0; i5 < size; i5++) {
                                                ((RevocableFileDescriptor) arraySet.valueAt(i5))
                                                        .revoke();
                                            }
                                            arraySet.clear();
                                            return true;
                                    }
                                }
                            });
        }
    }

    public final void removeLeasee(int i, String str) {
        synchronized (this.mMetadataLock) {
            this.mLeasees.removeIf(new BlobMetadata$$ExternalSyntheticLambda3(i, str, 0));
        }
    }

    public final boolean shouldBeDeleted(boolean z) {
        if (this.mBlobHandle.isExpired()) {
            return true;
        }
        return (!z || hasLeaseWaitTimeElapsedForAll()) && !hasValidLeases();
    }

    public final void writeToXml(XmlSerializer xmlSerializer) {
        synchronized (this.mMetadataLock) {
            try {
                XmlUtils.writeLongAttribute(xmlSerializer, "id", this.mBlobId);
                FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
                fastXmlSerializer.startTag((String) null, "bh");
                this.mBlobHandle.writeToXml(xmlSerializer);
                fastXmlSerializer.endTag((String) null, "bh");
                int size = this.mCommitters.size();
                for (int i = 0; i < size; i++) {
                    fastXmlSerializer.startTag((String) null, "c");
                    Committer committer = (Committer) this.mCommitters.valueAt(i);
                    XmlUtils.writeStringAttribute(
                            xmlSerializer,
                            KnoxAnalyticsDataConverter.PAYLOAD,
                            committer.packageName);
                    XmlUtils.writeIntAttribute(xmlSerializer, "u", committer.uid);
                    XmlUtils.writeLongAttribute(xmlSerializer, "cmt", committer.commitTimeMs);
                    fastXmlSerializer.startTag((String) null, "am");
                    committer.blobAccessMode.writeToXml(xmlSerializer);
                    fastXmlSerializer.endTag((String) null, "am");
                    fastXmlSerializer.endTag((String) null, "c");
                }
                int size2 = this.mLeasees.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    fastXmlSerializer.startTag((String) null, "l");
                    ((Leasee) this.mLeasees.valueAt(i2)).writeToXml(xmlSerializer);
                    fastXmlSerializer.endTag((String) null, "l");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
