package com.android.server.integrity;

import android.content.integrity.AppInstallMetadata;
import android.os.Environment;
import android.util.Slog;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.integrity.model.BitInputStream;
import com.android.server.integrity.model.RuleMetadata;
import com.android.server.integrity.parser.RandomAccessInputStream;
import com.android.server.integrity.parser.RandomAccessObject$RandomAccessFileObject;
import com.android.server.integrity.parser.RuleBinaryParser;
import com.android.server.integrity.parser.RuleIndexingController;
import com.android.server.integrity.parser.RuleParseException;
import com.android.server.integrity.parser.RuleParser;
import com.android.server.integrity.serializer.RuleBinarySerializer;
import com.android.server.integrity.serializer.RuleSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IntegrityFileManager {
    public static final Object RULES_LOCK = new Object();
    public static IntegrityFileManager sInstance;
    public final File mDataDir;
    public RuleIndexingController mRuleIndexingController;
    public RuleMetadata mRuleMetadataCache;
    public final RuleParser mRuleParser;
    public final RuleSerializer mRuleSerializer;
    public final File mRulesDir;
    public final File mStagingDir;

    public IntegrityFileManager(RuleParser ruleParser, RuleSerializer ruleSerializer, File file) {
        this.mRuleParser = ruleParser;
        this.mRuleSerializer = ruleSerializer;
        this.mDataDir = file;
        File file2 = new File(file, "integrity_rules");
        this.mRulesDir = file2;
        File file3 = new File(file, "integrity_staging");
        this.mStagingDir = file3;
        if (!file3.mkdirs() || !file2.mkdirs()) {
            Slog.e("IntegrityFileManager", "Error creating staging and rules directory");
        }
        File file4 = new File(file2, "metadata");
        if (file4.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file4);
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    String str = "";
                    String str2 = "";
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            this.mRuleMetadataCache = new RuleMetadata(str, str2);
                            fileInputStream.close();
                            break;
                        } else if (next == 2) {
                            String name = resolvePullParser.getName();
                            name.getClass();
                            if (name.equals("P")) {
                                str = resolvePullParser.nextText();
                            } else {
                                if (!name.equals("V")) {
                                    throw new IllegalStateException(
                                            "Unknown tag in metadata: ".concat(name));
                                }
                                str2 = resolvePullParser.nextText();
                            }
                        }
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.e("IntegrityFileManager", "Error reading metadata file.", e);
            }
        }
        updateRuleIndexingController();
    }

    public static synchronized IntegrityFileManager getInstance() {
        IntegrityFileManager integrityFileManager;
        synchronized (IntegrityFileManager.class) {
            try {
                if (sInstance == null) {
                    sInstance =
                            new IntegrityFileManager(
                                    new RuleBinaryParser(),
                                    new RuleBinarySerializer(),
                                    Environment.getDataSystemDirectory());
                }
                integrityFileManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return integrityFileManager;
    }

    public final List readRules(AppInstallMetadata appInstallMetadata) {
        List parseRules;
        synchronized (RULES_LOCK) {
            List emptyList = Collections.emptyList();
            if (appInstallMetadata != null) {
                try {
                    this.mRuleIndexingController.getClass();
                    emptyList = RuleIndexingController.identifyRulesToEvaluate(appInstallMetadata);
                } catch (Exception e) {
                    Slog.w(
                            "IntegrityFileManager",
                            "Error identifying the rule indexes. Trying unindexed.",
                            e);
                }
            }
            File file = new File(this.mRulesDir, "rules");
            RuleParser ruleParser = this.mRuleParser;
            RandomAccessObject$RandomAccessFileObject randomAccessObject$RandomAccessFileObject =
                    new RandomAccessObject$RandomAccessFileObject(file);
            ((RuleBinaryParser) ruleParser).getClass();
            try {
                RandomAccessInputStream randomAccessInputStream =
                        new RandomAccessInputStream(randomAccessObject$RandomAccessFileObject);
                try {
                    parseRules = RuleBinaryParser.parseRules(randomAccessInputStream, emptyList);
                    randomAccessInputStream.close();
                } catch (Throwable th) {
                    try {
                        randomAccessInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Exception e2) {
                throw new RuleParseException(e2.getMessage(), e2);
            }
        }
        return parseRules;
    }

    public final void updateRuleIndexingController() {
        File file = new File(this.mRulesDir, "indexing");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    RuleIndexingController ruleIndexingController = new RuleIndexingController();
                    BitInputStream bitInputStream = new BitInputStream(fileInputStream);
                    RuleIndexingController.sPackageNameBasedIndexes =
                            RuleIndexingController.getNextIndexGroup(bitInputStream);
                    RuleIndexingController.sAppCertificateBasedIndexes =
                            RuleIndexingController.getNextIndexGroup(bitInputStream);
                    RuleIndexingController.sUnindexedRuleIndexes =
                            RuleIndexingController.getNextIndexGroup(bitInputStream);
                    this.mRuleIndexingController = ruleIndexingController;
                    fileInputStream.close();
                } finally {
                }
            } catch (Exception e) {
                Slog.e("IntegrityFileManager", "Error parsing the rule indexing file.", e);
            }
        }
    }

    public final void writeMetadata(File file, String str, String str2) {
        this.mRuleMetadataCache = new RuleMetadata(str, str2);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(file, "metadata"));
        try {
            RuleMetadata ruleMetadata = this.mRuleMetadataCache;
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
            String str3 = ruleMetadata.mRuleProvider;
            resolveSerializer.startTag((String) null, "P");
            resolveSerializer.text(str3);
            resolveSerializer.endTag((String) null, "P");
            String str4 = ruleMetadata.mVersion;
            resolveSerializer.startTag((String) null, "V");
            resolveSerializer.text(str4);
            resolveSerializer.endTag((String) null, "V");
            resolveSerializer.endDocument();
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void writeRules(String str, String str2, List list) {
        try {
            writeMetadata(this.mStagingDir, str2, str);
        } catch (IOException e) {
            Slog.e("IntegrityFileManager", "Error writing metadata.", e);
        }
        FileOutputStream fileOutputStream =
                new FileOutputStream(new File(this.mStagingDir, "rules"));
        try {
            FileOutputStream fileOutputStream2 =
                    new FileOutputStream(new File(this.mStagingDir, "indexing"));
            try {
                ((RuleBinarySerializer) this.mRuleSerializer)
                        .serialize(list, Optional.empty(), fileOutputStream, fileOutputStream2);
                fileOutputStream2.close();
                fileOutputStream.close();
                synchronized (RULES_LOCK) {
                    try {
                        File file = new File(this.mDataDir, "temp");
                        if (!this.mRulesDir.renameTo(file)
                                || !this.mStagingDir.renameTo(this.mRulesDir)
                                || !file.renameTo(this.mStagingDir)) {
                            throw new IOException("Error switching staging/rules directory");
                        }
                        for (File file2 : this.mStagingDir.listFiles()) {
                            file2.delete();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                updateRuleIndexingController();
            } finally {
            }
        } catch (Throwable th2) {
            try {
                fileOutputStream.close();
            } catch (Throwable th3) {
                th2.addSuppressed(th3);
            }
            throw th2;
        }
    }
}
