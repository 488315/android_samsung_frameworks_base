package com.android.server.grammaticalinflection;

import android.app.ActivityTaskManager;
import android.app.Flags;
import android.app.GrammaticalInflectionManager;
import android.app.IGrammaticalInflectionManager;
import android.app.backup.BackupManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Environment;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.Trace;
import android.permission.PermissionManager;
import android.util.AtomicFile;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.PackageConfigurationUpdaterImpl;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public final class GrammaticalInflectionService extends SystemService {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public final GrammaticalInflectionBackupHelper mBackupHelper;
    public final GrammaticalInflectionBinderService mBinderService;
    public final Context mContext;
    public final SparseIntArray mGrammaticalGenderCache;
    public final Object mLock;
    public final PackageManagerInternal mPackageManagerInternal;
    public final PermissionManager mPermissionManager;

    public final class GrammaticalInflectionBinderService
            extends IGrammaticalInflectionManager.Stub {
        public GrammaticalInflectionBinderService() {}

        public final int getSystemGrammaticalGender(AttributionSource attributionSource, int i) {
            if (GrammaticalInflectionUtils.checkSystemGrammaticalGenderPermission(
                    GrammaticalInflectionService.this.mPermissionManager, attributionSource)) {
                if (GrammaticalInflectionService.checkSystemTermsOfAddressIsEnabled()) {
                    return GrammaticalInflectionService.this.getSystemGrammaticalGender(i);
                }
                return 0;
            }
            throw new SecurityException(
                    "AttributionSource: "
                            + attributionSource
                            + " does not have READ_SYSTEM_GRAMMATICAL_GENDER permission.");
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            GrammaticalInflectionService grammaticalInflectionService =
                    GrammaticalInflectionService.this;
            new GrammaticalInflectionShellCommand(
                            grammaticalInflectionService.mBinderService,
                            grammaticalInflectionService.mContext.getAttributionSource())
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final int peekSystemGrammaticalGenderByUserId(
                AttributionSource attributionSource, int i) {
            GrammaticalInflectionService grammaticalInflectionService =
                    GrammaticalInflectionService.this;
            grammaticalInflectionService.getClass();
            if (GrammaticalInflectionService.checkSystemTermsOfAddressIsEnabled()
                    && GrammaticalInflectionUtils.checkSystemGrammaticalGenderPermission(
                            grammaticalInflectionService.mPermissionManager, attributionSource)) {
                return GrammaticalInflectionService.this.getSystemGrammaticalGender(i);
            }
            return 0;
        }

        public final void setRequestedApplicationGrammaticalGender(String str, int i, int i2) {
            GrammaticalInflectionService.this.setRequestedApplicationGrammaticalGender(str, i, i2);
        }

        public final void setSystemWideGrammaticalGender(int i, int i2) {
            GrammaticalInflectionService.m569$$Nest$menforceCallerPermissions(
                    GrammaticalInflectionService.this);
            GrammaticalInflectionService.this.setSystemWideGrammaticalGender(i, i2);
        }
    }

    public final class GrammaticalInflectionManagerInternalImpl {
        public GrammaticalInflectionManagerInternalImpl() {}
    }

    /* renamed from: -$$Nest$menforceCallerPermissions, reason: not valid java name */
    public static void m569$$Nest$menforceCallerPermissions(
            GrammaticalInflectionService grammaticalInflectionService) {
        grammaticalInflectionService.getClass();
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || callingUid == 2000 || callingUid == 0) {
            return;
        }
        grammaticalInflectionService.mContext.enforceCallingOrSelfPermission(
                "android.permission.CHANGE_CONFIGURATION",
                "Caller must be system, shell, root or hold CHANGE_CONFIGURATION permission.");
    }

    public GrammaticalInflectionService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mGrammaticalGenderCache = new SparseIntArray();
        this.mContext = context;
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        context.getAttributionSource();
        this.mBackupHelper =
                new GrammaticalInflectionBackupHelper(this, context.getPackageManager());
        this.mBinderService = new GrammaticalInflectionBinderService();
        this.mPermissionManager =
                (PermissionManager) context.getSystemService(PermissionManager.class);
    }

    public static boolean checkSystemTermsOfAddressIsEnabled() {
        if (Flags.systemTermsOfAddressEnabled()) {
            return true;
        }
        Log.d("GrammaticalInflection", "The flag must be enabled to allow calling the API.");
        return false;
    }

    public static byte[] toXmlByteArray(int i, FileOutputStream fileOutputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
        resolveSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, "grammatical_inflection");
        resolveSerializer.attributeInt((String) null, "grammatical_gender", i);
        resolveSerializer.endTag((String) null, "grammatical_inflection");
        resolveSerializer.endDocument();
        return byteArrayOutputStream.toByteArray();
    }

    public static void updateConfiguration(int i, int i2) {
        try {
            Configuration configuration = new Configuration();
            int grammaticalGender = configuration.getGrammaticalGender();
            configuration.setGrammaticalGender(i);
            ActivityTaskManager.getService().updateConfiguration(configuration);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.SYSTEM_GRAMMATICAL_INFLECTION_CHANGED,
                    1,
                    i2,
                    i != 0,
                    grammaticalGender != 0);
            Duration duration = GrammaticalInflectionBackupHelper.STAGE_DATA_RETENTION_PERIOD;
            BackupManager.dataChanged("android");
        } catch (RemoteException e) {
            Log.w("GrammaticalInflection", "Can not update configuration", e);
        }
    }

    public int getSystemGrammaticalGender(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mGrammaticalGenderCache.get(i);
            if (i2 < 0) {
                i2 = 0;
            }
        }
        return i2;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("grammatical_inflection", this.mBinderService);
        LocalServices.addService(
                GrammaticalInflectionManagerInternalImpl.class,
                new GrammaticalInflectionManagerInternalImpl());
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(final SystemService.TargetUser targetUser) {
        if (checkSystemTermsOfAddressIsEnabled()) {
            IoThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                // com.android.server.grammaticalinflection.GrammaticalInflectionService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i;
                                    GrammaticalInflectionService grammaticalInflectionService =
                                            GrammaticalInflectionService.this;
                                    SystemService.TargetUser targetUser2 = targetUser;
                                    grammaticalInflectionService.getClass();
                                    int userIdentifier = targetUser2.getUserIdentifier();
                                    File file =
                                            new File(
                                                    new File(
                                                            Environment.getDataSystemCeDirectory(
                                                                    userIdentifier),
                                                            "grammatical_inflection"),
                                                    "user_settings.xml");
                                    synchronized (grammaticalInflectionService.mLock) {
                                        try {
                                            if (!file.exists()) {
                                                Log.d(
                                                        "GrammaticalInflection",
                                                        "User "
                                                                + userIdentifier
                                                                + " doesn't have the grammatical"
                                                                + " gender file.");
                                                return;
                                            }
                                            if (grammaticalInflectionService.mGrammaticalGenderCache
                                                            .indexOfKey(userIdentifier)
                                                    >= 0) {
                                                return;
                                            }
                                            try {
                                                FileInputStream fileInputStream =
                                                        new FileInputStream(file);
                                                try {
                                                    TypedXmlPullParser resolvePullParser =
                                                            Xml.resolvePullParser(fileInputStream);
                                                    XmlUtils.nextElement(resolvePullParser);
                                                    while (true) {
                                                        if (resolvePullParser.getEventType() == 1) {
                                                            i = 0;
                                                            break;
                                                        } else {
                                                            if ("grammatical_inflection"
                                                                    .equals(
                                                                            resolvePullParser
                                                                                    .getName())) {
                                                                i =
                                                                        resolvePullParser
                                                                                .getAttributeInt(
                                                                                        (String)
                                                                                                null,
                                                                                        "grammatical_gender");
                                                                break;
                                                            }
                                                            XmlUtils.nextElement(resolvePullParser);
                                                        }
                                                    }
                                                    grammaticalInflectionService
                                                            .mGrammaticalGenderCache.put(
                                                            userIdentifier, i);
                                                    fileInputStream.close();
                                                    GrammaticalInflectionService
                                                            .updateConfiguration(i, userIdentifier);
                                                } catch (Throwable th) {
                                                    try {
                                                        fileInputStream.close();
                                                    } catch (Throwable th2) {
                                                        th.addSuppressed(th2);
                                                    }
                                                    throw th;
                                                }
                                            } catch (IOException | XmlPullParserException e) {
                                                Log.e(
                                                        "GrammaticalInflection",
                                                        "Failed to parse XML configuration from "
                                                                + file,
                                                        e);
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            });
        }
    }

    public final void setRequestedApplicationGrammaticalGender(String str, int i, int i2) {
        Integer num;
        ActivityTaskManagerInternal.PackageConfig findPackageConfiguration =
                ActivityTaskManagerService.this.mPackageConfigPersister.findPackageConfiguration(
                        i, str);
        int intValue =
                (findPackageConfiguration == null
                                || (num = findPackageConfiguration.mGrammaticalGender) == null)
                        ? 0
                        : num.intValue();
        PackageConfigurationUpdaterImpl packageConfigurationUpdaterImpl =
                new PackageConfigurationUpdaterImpl(i, ActivityTaskManagerService.this, str);
        if (SystemProperties.getBoolean("i18n.grammatical_Inflection.enabled", true)) {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.APPLICATION_GRAMMATICAL_INFLECTION_CHANGED,
                    2,
                    this.mPackageManagerInternal.getPackageUid(str, 0L, i),
                    i2 != 0,
                    intValue != 0);
            synchronized (packageConfigurationUpdaterImpl) {
                packageConfigurationUpdaterImpl.mGrammaticalGender = i2;
            }
            packageConfigurationUpdaterImpl.commit();
            return;
        }
        if (intValue != 0) {
            Log.d("GrammaticalInflection", "Clearing the user's grammatical gender setting");
            synchronized (packageConfigurationUpdaterImpl) {
                packageConfigurationUpdaterImpl.mGrammaticalGender = 0;
            }
            packageConfigurationUpdaterImpl.commit();
        }
    }

    public final void setSystemWideGrammaticalGender(int i, int i2) {
        FileOutputStream fileOutputStream;
        try {
            if (checkSystemTermsOfAddressIsEnabled()) {
                Trace.beginSection("GrammaticalInflectionService.setSystemWideGrammaticalGender");
                if (!GrammaticalInflectionManager.VALID_GRAMMATICAL_GENDER_VALUES.contains(
                        Integer.valueOf(i))) {
                    throw new IllegalArgumentException("Unknown grammatical gender");
                }
                File file =
                        new File(
                                new File(
                                        Environment.getDataSystemCeDirectory(i2),
                                        "grammatical_inflection"),
                                "user_settings.xml");
                synchronized (this.mLock) {
                    AtomicFile atomicFile = new AtomicFile(file);
                    try {
                        fileOutputStream = atomicFile.startWrite();
                        try {
                            fileOutputStream.write(toXmlByteArray(i, fileOutputStream));
                            atomicFile.finishWrite(fileOutputStream);
                            this.mGrammaticalGenderCache.put(i2, i);
                        } catch (IOException e) {
                            e = e;
                            Log.e("GrammaticalInflection", "Failed to write file " + atomicFile, e);
                            if (fileOutputStream != null) {
                                atomicFile.failWrite(fileOutputStream);
                            }
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        fileOutputStream = null;
                    }
                }
                updateConfiguration(i, i2);
            }
        } finally {
            Trace.endSection();
        }
    }
}
