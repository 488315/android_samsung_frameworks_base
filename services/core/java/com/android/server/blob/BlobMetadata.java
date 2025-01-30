package com.android.server.blob;

import android.app.blob.BlobHandle;
import android.app.blob.LeaseInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.RevocableFileDescriptor;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.format.Formatter;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.StatsEvent;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class BlobMetadata {
  public File mBlobFile;
  public final BlobHandle mBlobHandle;
  public final long mBlobId;
  public final Context mContext;
  public final Object mMetadataLock = new Object();
  public final ArraySet mCommitters = new ArraySet();
  public final ArraySet mLeasees = new ArraySet();
  public final ArrayMap mRevocableFds = new ArrayMap();

  public BlobMetadata(Context context, long j, BlobHandle blobHandle) {
    this.mContext = context;
    this.mBlobId = j;
    this.mBlobHandle = blobHandle;
  }

  public long getBlobId() {
    return this.mBlobId;
  }

  public BlobHandle getBlobHandle() {
    return this.mBlobHandle;
  }

  public void addOrReplaceCommitter(Committer committer) {
    synchronized (this.mMetadataLock) {
      this.mCommitters.remove(committer);
      this.mCommitters.add(committer);
    }
  }

  public void setCommitters(ArraySet arraySet) {
    synchronized (this.mMetadataLock) {
      this.mCommitters.clear();
      this.mCommitters.addAll(arraySet);
    }
  }

  public void removeCommitter(final String str, final int i) {
    synchronized (this.mMetadataLock) {
      this.mCommitters.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeCommitter$0;
              lambda$removeCommitter$0 =
                  BlobMetadata.lambda$removeCommitter$0(i, str, (BlobMetadata.Committer) obj);
              return lambda$removeCommitter$0;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$removeCommitter$0(
      int i, String str, Committer committer) {
    return committer.uid == i && committer.packageName.equals(str);
  }

  public void removeCommitter(Committer committer) {
    synchronized (this.mMetadataLock) {
      this.mCommitters.remove(committer);
    }
  }

  public void removeCommittersFromUnknownPkgs(final SparseArray sparseArray) {
    synchronized (this.mMetadataLock) {
      this.mCommitters.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeCommittersFromUnknownPkgs$1;
              lambda$removeCommittersFromUnknownPkgs$1 =
                  BlobMetadata.lambda$removeCommittersFromUnknownPkgs$1(
                      sparseArray, (BlobMetadata.Committer) obj);
              return lambda$removeCommittersFromUnknownPkgs$1;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$removeCommittersFromUnknownPkgs$1(
      SparseArray sparseArray, Committer committer) {
    if (((SparseArray) sparseArray.get(UserHandle.getUserId(committer.uid))) == null) {
      return true;
    }
    return !committer.packageName.equals(r2.get(committer.uid));
  }

  public void addCommittersAndLeasees(BlobMetadata blobMetadata) {
    this.mCommitters.addAll(blobMetadata.mCommitters);
    this.mLeasees.addAll(blobMetadata.mLeasees);
  }

  public Committer getExistingCommitter(String str, int i) {
    synchronized (this.mCommitters) {
      int size = this.mCommitters.size();
      for (int i2 = 0; i2 < size; i2++) {
        Committer committer = (Committer) this.mCommitters.valueAt(i2);
        if (committer.uid == i && committer.packageName.equals(str)) {
          return committer;
        }
      }
      return null;
    }
  }

  public void addOrReplaceLeasee(String str, int i, int i2, CharSequence charSequence, long j) {
    synchronized (this.mMetadataLock) {
      Leasee leasee = new Leasee(this.mContext, str, i, i2, charSequence, j);
      this.mLeasees.remove(leasee);
      this.mLeasees.add(leasee);
    }
  }

  public void setLeasees(ArraySet arraySet) {
    synchronized (this.mMetadataLock) {
      this.mLeasees.clear();
      this.mLeasees.addAll(arraySet);
    }
  }

  public void removeLeasee(final String str, final int i) {
    synchronized (this.mMetadataLock) {
      this.mLeasees.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeLeasee$2;
              lambda$removeLeasee$2 =
                  BlobMetadata.lambda$removeLeasee$2(i, str, (BlobMetadata.Leasee) obj);
              return lambda$removeLeasee$2;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$removeLeasee$2(int i, String str, Leasee leasee) {
    return leasee.uid == i && leasee.packageName.equals(str);
  }

  public void removeLeaseesFromUnknownPkgs(final SparseArray sparseArray) {
    synchronized (this.mMetadataLock) {
      this.mLeasees.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeLeaseesFromUnknownPkgs$3;
              lambda$removeLeaseesFromUnknownPkgs$3 =
                  BlobMetadata.lambda$removeLeaseesFromUnknownPkgs$3(
                      sparseArray, (BlobMetadata.Leasee) obj);
              return lambda$removeLeaseesFromUnknownPkgs$3;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$removeLeaseesFromUnknownPkgs$3(
      SparseArray sparseArray, Leasee leasee) {
    if (((SparseArray) sparseArray.get(UserHandle.getUserId(leasee.uid))) == null) {
      return true;
    }
    return !leasee.packageName.equals(r2.get(leasee.uid));
  }

  public void removeExpiredLeases() {
    synchronized (this.mMetadataLock) {
      this.mLeasees.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeExpiredLeases$4;
              lambda$removeExpiredLeases$4 =
                  BlobMetadata.lambda$removeExpiredLeases$4((BlobMetadata.Leasee) obj);
              return lambda$removeExpiredLeases$4;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$removeExpiredLeases$4(Leasee leasee) {
    return !leasee.isStillValid();
  }

  public void removeDataForUser(final int i) {
    synchronized (this.mMetadataLock) {
      this.mCommitters.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeDataForUser$5;
              lambda$removeDataForUser$5 =
                  BlobMetadata.lambda$removeDataForUser$5(i, (BlobMetadata.Committer) obj);
              return lambda$removeDataForUser$5;
            }
          });
      this.mLeasees.removeIf(
          new Predicate() { // from class:
                            // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeDataForUser$6;
              lambda$removeDataForUser$6 =
                  BlobMetadata.lambda$removeDataForUser$6(i, (BlobMetadata.Leasee) obj);
              return lambda$removeDataForUser$6;
            }
          });
      this.mRevocableFds
          .entrySet()
          .removeIf(
              new Predicate() { // from class:
                                // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$removeDataForUser$7;
                  lambda$removeDataForUser$7 =
                      BlobMetadata.lambda$removeDataForUser$7(i, (Map.Entry) obj);
                  return lambda$removeDataForUser$7;
                }
              });
    }
  }

  public static /* synthetic */ boolean lambda$removeDataForUser$5(int i, Committer committer) {
    return i == UserHandle.getUserId(committer.uid);
  }

  public static /* synthetic */ boolean lambda$removeDataForUser$6(int i, Leasee leasee) {
    return i == UserHandle.getUserId(leasee.uid);
  }

  public static /* synthetic */ boolean lambda$removeDataForUser$7(int i, Map.Entry entry) {
    Accessor accessor = (Accessor) entry.getKey();
    ArraySet arraySet = (ArraySet) entry.getValue();
    if (i != UserHandle.getUserId(accessor.uid)) {
      return false;
    }
    int size = arraySet.size();
    for (int i2 = 0; i2 < size; i2++) {
      ((RevocableFileDescriptor) arraySet.valueAt(i2)).revoke();
    }
    arraySet.clear();
    return true;
  }

  public boolean hasValidLeases() {
    synchronized (this.mMetadataLock) {
      int size = this.mLeasees.size();
      for (int i = 0; i < size; i++) {
        if (((Leasee) this.mLeasees.valueAt(i)).isStillValid()) {
          return true;
        }
      }
      return false;
    }
  }

  public long getSize() {
    return getBlobFile().length();
  }

  public boolean isAccessAllowedForCaller(String str, int i) {
    if (getBlobHandle().isExpired()) {
      return false;
    }
    synchronized (this.mMetadataLock) {
      int size = this.mLeasees.size();
      for (int i2 = 0; i2 < size; i2++) {
        Leasee leasee = (Leasee) this.mLeasees.valueAt(i2);
        if (leasee.isStillValid() && leasee.equals(str, i)) {
          return true;
        }
      }
      int userId = UserHandle.getUserId(i);
      int size2 = this.mCommitters.size();
      for (int i3 = 0; i3 < size2; i3++) {
        Committer committer = (Committer) this.mCommitters.valueAt(i3);
        if (userId == UserHandle.getUserId(committer.uid)) {
          if (committer.equals(str, i)) {
            return true;
          }
          if (committer.blobAccessMode.isAccessAllowedForCaller(
              this.mContext, str, i, committer.uid)) {
            return true;
          }
        }
      }
      if (!checkCallerCanAccessBlobsAcrossUsers(str, userId)) {
        return false;
      }
      int size3 = this.mCommitters.size();
      for (int i4 = 0; i4 < size3; i4++) {
        Committer committer2 = (Committer) this.mCommitters.valueAt(i4);
        int userId2 = UserHandle.getUserId(committer2.uid);
        if (userId != userId2
            && isPackageInstalledOnUser(str, userId2)
            && committer2.blobAccessMode.isAccessAllowedForCaller(
                this.mContext, str, i, committer2.uid)) {
          return true;
        }
      }
      return false;
    }
  }

  public static boolean checkCallerCanAccessBlobsAcrossUsers(String str, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return PermissionManager.checkPackageNamePermission(
              "android.permission.ACCESS_BLOBS_ACROSS_USERS", str, i)
          == 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean isPackageInstalledOnUser(String str, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return true;
    } catch (PackageManager.NameNotFoundException unused) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public boolean hasACommitterOrLeaseeInUser(int i) {
    return hasACommitterInUser(i) || hasALeaseeInUser(i);
  }

  public boolean hasACommitterInUser(int i) {
    synchronized (this.mMetadataLock) {
      int size = this.mCommitters.size();
      for (int i2 = 0; i2 < size; i2++) {
        if (i == UserHandle.getUserId(((Committer) this.mCommitters.valueAt(i2)).uid)) {
          return true;
        }
      }
      return false;
    }
  }

  public final boolean hasALeaseeInUser(int i) {
    synchronized (this.mMetadataLock) {
      int size = this.mLeasees.size();
      for (int i2 = 0; i2 < size; i2++) {
        if (i == UserHandle.getUserId(((Leasee) this.mLeasees.valueAt(i2)).uid)) {
          return true;
        }
      }
      return false;
    }
  }

  public boolean isACommitter(String str, int i) {
    boolean isAnAccessor;
    synchronized (this.mMetadataLock) {
      isAnAccessor = isAnAccessor(this.mCommitters, str, i, UserHandle.getUserId(i));
    }
    return isAnAccessor;
  }

  public boolean isALeasee(String str, int i) {
    boolean z;
    synchronized (this.mMetadataLock) {
      Leasee leasee = (Leasee) getAccessor(this.mLeasees, str, i, UserHandle.getUserId(i));
      z = leasee != null && leasee.isStillValid();
    }
    return z;
  }

  public final boolean isALeaseeInUser(String str, int i, int i2) {
    boolean z;
    synchronized (this.mMetadataLock) {
      Leasee leasee = (Leasee) getAccessor(this.mLeasees, str, i, i2);
      z = leasee != null && leasee.isStillValid();
    }
    return z;
  }

  public static boolean isAnAccessor(ArraySet arraySet, String str, int i, int i2) {
    return getAccessor(arraySet, str, i, i2) != null;
  }

  public static Accessor getAccessor(ArraySet arraySet, String str, int i, int i2) {
    int size = arraySet.size();
    for (int i3 = 0; i3 < size; i3++) {
      Accessor accessor = (Accessor) arraySet.valueAt(i3);
      if (str != null && i != -1 && accessor.equals(str, i)) {
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

  public boolean shouldAttributeToUser(int i) {
    synchronized (this.mMetadataLock) {
      int size = this.mLeasees.size();
      for (int i2 = 0; i2 < size; i2++) {
        if (i != UserHandle.getUserId(((Leasee) this.mLeasees.valueAt(i2)).uid)) {
          return false;
        }
      }
      return true;
    }
  }

  public boolean shouldAttributeToLeasee(String str, int i, boolean z) {
    if (isALeaseeInUser(str, -1, i)) {
      return (z && hasOtherLeasees(str, -1, i)) ? false : true;
    }
    return false;
  }

  public boolean shouldAttributeToLeasee(int i, boolean z) {
    int userId = UserHandle.getUserId(i);
    if (isALeaseeInUser(null, i, userId)) {
      return (z && hasOtherLeasees(null, i, userId)) ? false : true;
    }
    return false;
  }

  public final boolean hasOtherLeasees(String str, int i, int i2) {
    synchronized (this.mMetadataLock) {
      int size = this.mLeasees.size();
      for (int i3 = 0; i3 < size; i3++) {
        Leasee leasee = (Leasee) this.mLeasees.valueAt(i3);
        if (leasee.isStillValid()) {
          if (str != null && i != -1 && !leasee.equals(str, i)) {
            return true;
          }
          if (str != null
              && (!leasee.packageName.equals(str) || i2 != UserHandle.getUserId(leasee.uid))) {
            return true;
          }
          if (i != -1 && leasee.uid != i) {
            return true;
          }
        }
      }
      return false;
    }
  }

  public LeaseInfo getLeaseInfo(String str, int i) {
    synchronized (this.mMetadataLock) {
      int size = this.mLeasees.size();
      for (int i2 = 0; i2 < size; i2++) {
        Leasee leasee = (Leasee) this.mLeasees.valueAt(i2);
        if (leasee.isStillValid() && leasee.uid == i && leasee.packageName.equals(str)) {
          String str2 = leasee.descriptionResEntryName;
          return new LeaseInfo(
              str,
              leasee.expiryTimeMillis,
              str2 != null
                  ? BlobStoreUtils.getDescriptionResourceId(
                      this.mContext, str2, leasee.packageName, UserHandle.getUserId(leasee.uid))
                  : 0,
              leasee.description);
        }
      }
      return null;
    }
  }

  public void forEachLeasee(Consumer consumer) {
    synchronized (this.mMetadataLock) {
      this.mLeasees.forEach(consumer);
    }
  }

  public File getBlobFile() {
    if (this.mBlobFile == null) {
      this.mBlobFile = BlobStoreConfig.getBlobFile(this.mBlobId);
    }
    return this.mBlobFile;
  }

  public ParcelFileDescriptor openForRead(String str, int i) {
    try {
      FileDescriptor open = Os.open(getBlobFile().getPath(), OsConstants.O_RDONLY, 0);
      try {
        if (BlobStoreConfig.shouldUseRevocableFdForReads()) {
          return createRevocableFd(open, str, i);
        }
        return new ParcelFileDescriptor(open);
      } catch (IOException e) {
        IoUtils.closeQuietly(open);
        throw e;
      }
    } catch (ErrnoException e2) {
      throw e2.rethrowAsIOException();
    }
  }

  public final ParcelFileDescriptor createRevocableFd(
      FileDescriptor fileDescriptor, String str, int i) {
    final Accessor accessor;
    final RevocableFileDescriptor revocableFileDescriptor =
        new RevocableFileDescriptor(this.mContext, fileDescriptor);
    synchronized (this.mRevocableFds) {
      accessor = new Accessor(str, i);
      ArraySet arraySet = (ArraySet) this.mRevocableFds.get(accessor);
      if (arraySet == null) {
        arraySet = new ArraySet();
        this.mRevocableFds.put(accessor, arraySet);
      }
      arraySet.add(revocableFileDescriptor);
    }
    revocableFileDescriptor.addOnCloseListener(
        new ParcelFileDescriptor
            .OnCloseListener() { // from class:
                                 // com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda6
          @Override // android.os.ParcelFileDescriptor.OnCloseListener
          public final void onClose(IOException iOException) {
            BlobMetadata.this.lambda$createRevocableFd$8(
                accessor, revocableFileDescriptor, iOException);
          }
        });
    return revocableFileDescriptor.getRevocableFileDescriptor();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createRevocableFd$8(
      Accessor accessor, RevocableFileDescriptor revocableFileDescriptor, IOException iOException) {
    synchronized (this.mRevocableFds) {
      ArraySet arraySet = (ArraySet) this.mRevocableFds.get(accessor);
      if (arraySet != null) {
        arraySet.remove(revocableFileDescriptor);
        if (arraySet.isEmpty()) {
          this.mRevocableFds.remove(accessor);
        }
      }
    }
  }

  public void destroy() {
    revokeAndClearAllFds();
    getBlobFile().delete();
  }

  public final void revokeAndClearAllFds() {
    synchronized (this.mRevocableFds) {
      int size = this.mRevocableFds.size();
      for (int i = 0; i < size; i++) {
        ArraySet arraySet = (ArraySet) this.mRevocableFds.valueAt(i);
        if (arraySet != null) {
          int size2 = arraySet.size();
          for (int i2 = 0; i2 < size2; i2++) {
            ((RevocableFileDescriptor) arraySet.valueAt(i2)).revoke();
          }
        }
      }
      this.mRevocableFds.clear();
    }
  }

  public boolean shouldBeDeleted(boolean z) {
    if (getBlobHandle().isExpired()) {
      return true;
    }
    return (!z || hasLeaseWaitTimeElapsedForAll()) && !hasValidLeases();
  }

  public boolean hasLeaseWaitTimeElapsedForAll() {
    int size = this.mCommitters.size();
    for (int i = 0; i < size; i++) {
      if (!BlobStoreConfig.hasLeaseWaitTimeElapsed(
          ((Committer) this.mCommitters.valueAt(i)).getCommitTimeMs())) {
        return false;
      }
    }
    return true;
  }

  public StatsEvent dumpAsStatsEvent(int i) {
    long j;
    StatsEvent buildStatsEvent;
    synchronized (this.mMetadataLock) {
      ProtoOutputStream protoOutputStream = new ProtoOutputStream();
      int size = this.mCommitters.size();
      int i2 = 0;
      int i3 = 0;
      while (true) {
        j = 1120986464257L;
        if (i3 >= size) {
          break;
        }
        Committer committer = (Committer) this.mCommitters.valueAt(i3);
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1120986464257L, committer.uid);
        protoOutputStream.write(1112396529666L, committer.commitTimeMs);
        protoOutputStream.write(1120986464259L, committer.blobAccessMode.getAccessType());
        protoOutputStream.write(1120986464260L, committer.blobAccessMode.getAllowedPackagesCount());
        protoOutputStream.end(start);
        i3++;
      }
      byte[] bytes = protoOutputStream.getBytes();
      ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
      int size2 = this.mLeasees.size();
      while (i2 < size2) {
        Leasee leasee = (Leasee) this.mLeasees.valueAt(i2);
        long start2 = protoOutputStream2.start(2246267895809L);
        protoOutputStream2.write(j, leasee.uid);
        protoOutputStream2.write(1112396529666L, leasee.expiryTimeMillis);
        protoOutputStream2.end(start2);
        i2++;
        j = 1120986464257L;
      }
      buildStatsEvent =
          FrameworkStatsLog.buildStatsEvent(
              i,
              this.mBlobId,
              getSize(),
              this.mBlobHandle.getExpiryTimeMillis(),
              bytes,
              protoOutputStream2.getBytes());
    }
    return buildStatsEvent;
  }

  public void dump(
      IndentingPrintWriter indentingPrintWriter, BlobStoreManagerService.DumpArgs dumpArgs) {
    synchronized (this.mMetadataLock) {
      indentingPrintWriter.println("blobHandle:");
      indentingPrintWriter.increaseIndent();
      this.mBlobHandle.dump(indentingPrintWriter, dumpArgs.shouldDumpFull());
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println(
          "size: " + Formatter.formatFileSize(this.mContext, getSize(), 8));
      indentingPrintWriter.println("Committers:");
      indentingPrintWriter.increaseIndent();
      if (this.mCommitters.isEmpty()) {
        indentingPrintWriter.println("<empty>");
      } else {
        int size = this.mCommitters.size();
        for (int i = 0; i < size; i++) {
          Committer committer = (Committer) this.mCommitters.valueAt(i);
          indentingPrintWriter.println("committer " + committer.toString());
          indentingPrintWriter.increaseIndent();
          committer.dump(indentingPrintWriter);
          indentingPrintWriter.decreaseIndent();
        }
      }
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println("Leasees:");
      indentingPrintWriter.increaseIndent();
      if (this.mLeasees.isEmpty()) {
        indentingPrintWriter.println("<empty>");
      } else {
        int size2 = this.mLeasees.size();
        for (int i2 = 0; i2 < size2; i2++) {
          Leasee leasee = (Leasee) this.mLeasees.valueAt(i2);
          indentingPrintWriter.println("leasee " + leasee.toString());
          indentingPrintWriter.increaseIndent();
          leasee.dump(this.mContext, indentingPrintWriter);
          indentingPrintWriter.decreaseIndent();
        }
      }
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println("Open fds:");
      indentingPrintWriter.increaseIndent();
      if (this.mRevocableFds.isEmpty()) {
        indentingPrintWriter.println("<empty>");
      } else {
        int size3 = this.mRevocableFds.size();
        for (int i3 = 0; i3 < size3; i3++) {
          indentingPrintWriter.println(
              ((Accessor) this.mRevocableFds.keyAt(i3))
                  + ": #"
                  + ((ArraySet) this.mRevocableFds.valueAt(i3)).size());
        }
      }
      indentingPrintWriter.decreaseIndent();
    }
  }

  public void writeToXml(XmlSerializer xmlSerializer) {
    synchronized (this.mMetadataLock) {
      XmlUtils.writeLongAttribute(xmlSerializer, "id", this.mBlobId);
      xmlSerializer.startTag(null, "bh");
      this.mBlobHandle.writeToXml(xmlSerializer);
      xmlSerializer.endTag(null, "bh");
      int size = this.mCommitters.size();
      for (int i = 0; i < size; i++) {
        xmlSerializer.startTag(null, "c");
        ((Committer) this.mCommitters.valueAt(i)).writeToXml(xmlSerializer);
        xmlSerializer.endTag(null, "c");
      }
      int size2 = this.mLeasees.size();
      for (int i2 = 0; i2 < size2; i2++) {
        xmlSerializer.startTag(null, "l");
        ((Leasee) this.mLeasees.valueAt(i2)).writeToXml(xmlSerializer);
        xmlSerializer.endTag(null, "l");
      }
    }
  }

  public static BlobMetadata createFromXml(XmlPullParser xmlPullParser, int i, Context context) {
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
        Committer createFromXml = Committer.createFromXml(xmlPullParser, i);
        if (createFromXml != null) {
          arraySet.add(createFromXml);
        }
      } else if ("l".equals(xmlPullParser.getName())) {
        arraySet2.add(Leasee.createFromXml(xmlPullParser, i));
      }
    }
    if (blobHandle == null) {
      Slog.wtf("BlobStore", "blobHandle should be available");
      return null;
    }
    BlobMetadata blobMetadata = new BlobMetadata(context, readLongAttribute, blobHandle);
    blobMetadata.setCommitters(arraySet);
    blobMetadata.setLeasees(arraySet2);
    return blobMetadata;
  }

  public final class Committer extends Accessor {
    public final BlobAccessMode blobAccessMode;
    public final long commitTimeMs;

    public Committer(String str, int i, BlobAccessMode blobAccessMode, long j) {
      super(str, i);
      this.blobAccessMode = blobAccessMode;
      this.commitTimeMs = j;
    }

    public long getCommitTimeMs() {
      return this.commitTimeMs;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
      StringBuilder sb = new StringBuilder();
      sb.append("commit time: ");
      long j = this.commitTimeMs;
      sb.append(j == 0 ? "<null>" : BlobStoreUtils.formatTime(j));
      indentingPrintWriter.println(sb.toString());
      indentingPrintWriter.println("accessMode:");
      indentingPrintWriter.increaseIndent();
      this.blobAccessMode.dump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
    }

    public void writeToXml(XmlSerializer xmlSerializer) {
      XmlUtils.writeStringAttribute(
          xmlSerializer, KnoxAnalyticsDataConverter.PAYLOAD, this.packageName);
      XmlUtils.writeIntAttribute(xmlSerializer, "u", this.uid);
      XmlUtils.writeLongAttribute(xmlSerializer, "cmt", this.commitTimeMs);
      xmlSerializer.startTag(null, "am");
      this.blobAccessMode.writeToXml(xmlSerializer);
      xmlSerializer.endTag(null, "am");
    }

    public static Committer createFromXml(XmlPullParser xmlPullParser, int i) {
      String readStringAttribute =
          XmlUtils.readStringAttribute(xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD);
      int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "u");
      long readLongAttribute = i >= 4 ? XmlUtils.readLongAttribute(xmlPullParser, "cmt") : 0L;
      int depth = xmlPullParser.getDepth();
      BlobAccessMode blobAccessMode = null;
      while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
        if ("am".equals(xmlPullParser.getName())) {
          blobAccessMode = BlobAccessMode.createFromXml(xmlPullParser);
        }
      }
      if (blobAccessMode == null) {
        Slog.wtf("BlobStore", "blobAccessMode should be available");
        return null;
      }
      return new Committer(
          readStringAttribute, readIntAttribute, blobAccessMode, readLongAttribute);
    }
  }

  public final class Leasee extends Accessor {
    public final CharSequence description;
    public final String descriptionResEntryName;
    public final long expiryTimeMillis;

    public Leasee(Context context, String str, int i, int i2, CharSequence charSequence, long j) {
      super(str, i);
      Resources packageResources =
          BlobStoreUtils.getPackageResources(context, str, UserHandle.getUserId(i));
      this.descriptionResEntryName = getResourceEntryName(packageResources, i2);
      this.expiryTimeMillis = j;
      this.description = charSequence == null ? getDescription(packageResources, i2) : charSequence;
    }

    public Leasee(String str, int i, String str2, CharSequence charSequence, long j) {
      super(str, i);
      this.descriptionResEntryName = str2;
      this.expiryTimeMillis = j;
      this.description = charSequence;
    }

    public static String getResourceEntryName(Resources resources, int i) {
      if (!ResourceId.isValid(i) || resources == null) {
        return null;
      }
      return resources.getResourceEntryName(i);
    }

    public static String getDescription(Context context, String str, String str2, int i) {
      Resources packageResources;
      int descriptionResourceId;
      if (str == null
          || str.isEmpty()
          || (packageResources = BlobStoreUtils.getPackageResources(context, str2, i)) == null
          || (descriptionResourceId =
                  BlobStoreUtils.getDescriptionResourceId(packageResources, str, str2))
              == 0) {
        return null;
      }
      return packageResources.getString(descriptionResourceId);
    }

    public static String getDescription(Resources resources, int i) {
      if (!ResourceId.isValid(i) || resources == null) {
        return null;
      }
      return resources.getString(i);
    }

    public boolean isStillValid() {
      long j = this.expiryTimeMillis;
      return j == 0 || j >= System.currentTimeMillis();
    }

    public void dump(Context context, IndentingPrintWriter indentingPrintWriter) {
      indentingPrintWriter.println("desc: " + getDescriptionToDump(context));
      indentingPrintWriter.println("expiryMs: " + this.expiryTimeMillis);
    }

    public final String getDescriptionToDump(Context context) {
      String description =
          getDescription(
              context,
              this.descriptionResEntryName,
              this.packageName,
              UserHandle.getUserId(this.uid));
      if (description == null) {
        description = this.description.toString();
      }
      return description == null ? "<none>" : description;
    }

    public void writeToXml(XmlSerializer xmlSerializer) {
      XmlUtils.writeStringAttribute(
          xmlSerializer, KnoxAnalyticsDataConverter.PAYLOAD, this.packageName);
      XmlUtils.writeIntAttribute(xmlSerializer, "u", this.uid);
      XmlUtils.writeStringAttribute(xmlSerializer, "rn", this.descriptionResEntryName);
      XmlUtils.writeLongAttribute(xmlSerializer, "ex", this.expiryTimeMillis);
      XmlUtils.writeStringAttribute(xmlSerializer, "d", this.description);
    }

    public static Leasee createFromXml(XmlPullParser xmlPullParser, int i) {
      return new Leasee(
          XmlUtils.readStringAttribute(xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD),
          XmlUtils.readIntAttribute(xmlPullParser, "u"),
          i >= 3 ? XmlUtils.readStringAttribute(xmlPullParser, "rn") : null,
          i >= 2 ? XmlUtils.readStringAttribute(xmlPullParser, "d") : null,
          XmlUtils.readLongAttribute(xmlPullParser, "ex"));
    }
  }

  public class Accessor {
    public final String packageName;
    public final int uid;

    public Accessor(String str, int i) {
      this.packageName = str;
      this.uid = i;
    }

    public boolean equals(String str, int i) {
      return this.uid == i && this.packageName.equals(str);
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !(obj instanceof Accessor)) {
        return false;
      }
      Accessor accessor = (Accessor) obj;
      return this.uid == accessor.uid && this.packageName.equals(accessor.packageName);
    }

    public int hashCode() {
      return Objects.hash(this.packageName, Integer.valueOf(this.uid));
    }

    public String toString() {
      return "[" + this.packageName + ", " + this.uid + "]";
    }
  }
}
