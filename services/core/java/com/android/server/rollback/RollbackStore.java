package com.android.server.rollback;

import android.content.pm.VersionedPackage;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseIntArray;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import libcore.io.IoUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RollbackStore {
    public final File mRollbackDataDir;
    public final File mRollbackHistoryDir;

    public RollbackStore(File file, File file2) {
        this.mRollbackDataDir = file;
        this.mRollbackHistoryDir = file2;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void backupPackageCodePath(com.android.server.rollback.Rollback r6, java.lang.String r7, java.lang.String r8) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r8)
            java.io.File r8 = new java.io.File
            java.io.File r6 = r6.mBackupDir
            r8.<init>(r6, r7)
            r8.mkdirs()
            java.io.File r6 = new java.io.File
            java.lang.String r7 = r0.getName()
            r6.<init>(r8, r7)
            r7 = 1
            r1 = 0
            java.lang.String r2 = r0.getAbsolutePath()     // Catch: android.system.ErrnoException -> L34
            android.system.StructStat r2 = android.system.Os.stat(r2)     // Catch: android.system.ErrnoException -> L34
            long r2 = r2.st_dev     // Catch: android.system.ErrnoException -> L34
            java.lang.String r8 = r8.getAbsolutePath()     // Catch: android.system.ErrnoException -> L34
            android.system.StructStat r8 = android.system.Os.stat(r8)     // Catch: android.system.ErrnoException -> L34
            long r4 = r8.st_dev     // Catch: android.system.ErrnoException -> L34
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 != 0) goto L34
            r8 = r7
            goto L35
        L34:
            r8 = r1
        L35:
            r8 = r8 ^ r7
            if (r8 != 0) goto L55
            java.lang.String r2 = r0.getAbsolutePath()     // Catch: android.system.ErrnoException -> L44
            java.lang.String r3 = r6.getAbsolutePath()     // Catch: android.system.ErrnoException -> L44
            android.system.Os.link(r2, r3)     // Catch: android.system.ErrnoException -> L44
            goto L55
        L44:
            r8 = move-exception
            java.lang.String r2 = "persist.rollback.is_test"
            boolean r2 = android.os.SystemProperties.getBoolean(r2, r1)
            if (r2 != 0) goto L4f
            goto L56
        L4f:
            java.io.IOException r6 = new java.io.IOException
            r6.<init>(r8)
            throw r6
        L55:
            r7 = r8
        L56:
            if (r7 == 0) goto L65
            java.nio.file.Path r7 = r0.toPath()
            java.nio.file.Path r6 = r6.toPath()
            java.nio.file.CopyOption[] r8 = new java.nio.file.CopyOption[r1]
            java.nio.file.Files.copy(r7, r6, r8)
        L65:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.rollback.RollbackStore.backupPackageCodePath(com.android.server.rollback.Rollback, java.lang.String, java.lang.String):void");
    }

    public static JSONArray extensionVersionsToJson(SparseIntArray sparseIntArray) {
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < sparseIntArray.size(); i++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sdkVersion", sparseIntArray.keyAt(i));
            jSONObject.put("extensionVersion", sparseIntArray.valueAt(i));
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static List loadRollbacks(File file) {
        ArrayList arrayList = new ArrayList();
        file.mkdirs();
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                try {
                    try {
                        arrayList.add(rollbackFromJson(new JSONObject(IoUtils.readFileAsString(new File(file2, "rollback.json").getAbsolutePath())), file2));
                    } catch (ParseException | DateTimeParseException | JSONException e) {
                        throw new IOException(e);
                    }
                } catch (IOException e2) {
                    Slog.e("RollbackManager", "Unable to read rollback at " + file2, e2);
                    removeFile(file2);
                }
            }
        }
        return arrayList;
    }

    public static void removeFile(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                removeFile(file2);
            }
        }
        if (file.exists()) {
            file.delete();
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
        	at jadx.core.dex.visitors.MoveInlineVisitor.processMove(MoveInlineVisitor.java:52)
        	at jadx.core.dex.visitors.MoveInlineVisitor.moveInline(MoveInlineVisitor.java:41)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:43)
        */
    public static com.android.server.rollback.Rollback rollbackFromJson(
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r27v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */
    /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
        	at jadx.core.dex.visitors.MoveInlineVisitor.processMove(MoveInlineVisitor.java:52)
        	at jadx.core.dex.visitors.MoveInlineVisitor.moveInline(MoveInlineVisitor.java:41)
        */

    public static JSONObject rollbackInfoToJson(RollbackInfo rollbackInfo) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("rollbackId", rollbackInfo.getRollbackId());
        List<PackageRollbackInfo> packages = rollbackInfo.getPackages();
        JSONArray jSONArray = new JSONArray();
        for (PackageRollbackInfo packageRollbackInfo : packages) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("versionRolledBackFrom", toJson(packageRollbackInfo.getVersionRolledBackFrom()));
            jSONObject2.put("versionRolledBackTo", toJson(packageRollbackInfo.getVersionRolledBackTo()));
            List pendingBackups = packageRollbackInfo.getPendingBackups();
            ArrayList<PackageRollbackInfo.RestoreInfo> pendingRestores = packageRollbackInfo.getPendingRestores();
            List snapshottedUsers = packageRollbackInfo.getSnapshottedUsers();
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < pendingBackups.size(); i++) {
                jSONArray2.put(pendingBackups.get(i));
            }
            jSONObject2.put("pendingBackups", jSONArray2);
            JSONArray jSONArray3 = new JSONArray();
            for (PackageRollbackInfo.RestoreInfo restoreInfo : pendingRestores) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("userId", restoreInfo.userId);
                jSONObject3.put("appId", restoreInfo.appId);
                jSONObject3.put("seInfo", restoreInfo.seInfo);
                jSONArray3.put(jSONObject3);
            }
            jSONObject2.put("pendingRestores", jSONArray3);
            jSONObject2.put("isApex", packageRollbackInfo.isApex());
            jSONObject2.put("isApkInApex", packageRollbackInfo.isApkInApex());
            JSONArray jSONArray4 = new JSONArray();
            for (int i2 = 0; i2 < snapshottedUsers.size(); i2++) {
                jSONArray4.put(snapshottedUsers.get(i2));
            }
            jSONObject2.put("installedUsers", jSONArray4);
            jSONObject2.put("rollbackDataPolicy", packageRollbackInfo.getRollbackDataPolicy());
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("packages", jSONArray);
        jSONObject.put("isStaged", rollbackInfo.isStaged());
        List causePackages = rollbackInfo.getCausePackages();
        JSONArray jSONArray5 = new JSONArray();
        Iterator it = causePackages.iterator();
        while (it.hasNext()) {
            jSONArray5.put(toJson((VersionedPackage) it.next()));
        }
        jSONObject.put("causePackages", jSONArray5);
        jSONObject.put("committedSessionId", rollbackInfo.getCommittedSessionId());
        if (Flags.recoverabilityDetection()) {
            jSONObject.put("rollbackImpactLevel", rollbackInfo.getRollbackImpactLevel());
        }
        return jSONObject;
    }

    public static void saveRollback(Rollback rollback, File file) {
        AtomicFile atomicFile = new AtomicFile(new File(file, "rollback.json"));
        FileOutputStream fileOutputStream = null;
        try {
            file.mkdirs();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("info", rollbackInfoToJson(rollback.info));
            rollback.assertInWorkerThread();
            jSONObject.put("timestamp", rollback.mTimestamp.toString());
            if (Flags.rollbackLifetime()) {
                rollback.assertInWorkerThread();
                jSONObject.put("rollbackLifetimeMillis", rollback.mRollbackLifetimeMillis);
            }
            jSONObject.put("originalSessionId", rollback.mOriginalSessionId);
            jSONObject.put(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, rollback.getStateAsString());
            rollback.assertInWorkerThread();
            jSONObject.put("stateDescription", rollback.mStateDescription);
            rollback.assertInWorkerThread();
            jSONObject.put("restoreUserDataInProgress", rollback.mRestoreUserDataInProgress);
            jSONObject.put("userId", rollback.mUserId);
            jSONObject.putOpt("installerPackageName", rollback.mInstallerPackageName);
            jSONObject.putOpt("extensionVersions", extensionVersionsToJson(rollback.mExtensionVersions));
            fileOutputStream = atomicFile.startWrite();
            fileOutputStream.write(jSONObject.toString().getBytes());
            fileOutputStream.flush();
            atomicFile.finishWrite(fileOutputStream);
        } catch (IOException | JSONException e) {
            Slog.e("RollbackManager", "Unable to save rollback for: " + rollback.info.getRollbackId(), e);
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
        }
    }

    public static JSONObject toJson(VersionedPackage versionedPackage) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("packageName", versionedPackage.getPackageName());
        jSONObject.put("longVersionCode", versionedPackage.getLongVersionCode());
        return jSONObject;
    }
}
