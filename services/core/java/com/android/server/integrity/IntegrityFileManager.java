package com.android.server.integrity;

import android.content.integrity.AppInstallMetadata;
import android.os.Environment;
import android.util.Slog;
import com.android.server.integrity.model.RuleMetadata;
import com.android.server.integrity.parser.RandomAccessObject;
import com.android.server.integrity.parser.RuleBinaryParser;
import com.android.server.integrity.parser.RuleIndexingController;
import com.android.server.integrity.parser.RuleMetadataParser;
import com.android.server.integrity.parser.RuleParser;
import com.android.server.integrity.serializer.RuleBinarySerializer;
import com.android.server.integrity.serializer.RuleMetadataSerializer;
import com.android.server.integrity.serializer.RuleSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/* loaded from: classes2.dex */
public class IntegrityFileManager {
  public static final Object RULES_LOCK = new Object();
  public static IntegrityFileManager sInstance;
  public final File mDataDir;
  public RuleIndexingController mRuleIndexingController;
  public RuleMetadata mRuleMetadataCache;
  public final RuleParser mRuleParser;
  public final RuleSerializer mRuleSerializer;
  public final File mRulesDir;
  public final File mStagingDir;

  public static synchronized IntegrityFileManager getInstance() {
    IntegrityFileManager integrityFileManager;
    synchronized (IntegrityFileManager.class) {
      if (sInstance == null) {
        sInstance = new IntegrityFileManager();
      }
      integrityFileManager = sInstance;
    }
    return integrityFileManager;
  }

  public IntegrityFileManager() {
    this(new RuleBinaryParser(), new RuleBinarySerializer(), Environment.getDataSystemDirectory());
  }

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
          this.mRuleMetadataCache = RuleMetadataParser.parse(fileInputStream);
          fileInputStream.close();
        } finally {
        }
      } catch (Exception e) {
        Slog.e("IntegrityFileManager", "Error reading metadata file.", e);
      }
    }
    updateRuleIndexingController();
  }

  public boolean initialized() {
    return new File(this.mRulesDir, "rules").exists()
        && new File(this.mRulesDir, "metadata").exists()
        && new File(this.mRulesDir, "indexing").exists();
  }

  public void writeRules(String str, String str2, List list) {
    try {
      writeMetadata(this.mStagingDir, str2, str);
    } catch (IOException e) {
      Slog.e("IntegrityFileManager", "Error writing metadata.", e);
    }
    FileOutputStream fileOutputStream = new FileOutputStream(new File(this.mStagingDir, "rules"));
    try {
      FileOutputStream fileOutputStream2 =
          new FileOutputStream(new File(this.mStagingDir, "indexing"));
      try {
        this.mRuleSerializer.serialize(list, Optional.empty(), fileOutputStream, fileOutputStream2);
        fileOutputStream2.close();
        fileOutputStream.close();
        switchStagingRulesDir();
        updateRuleIndexingController();
      } finally {
      }
    } catch (Throwable th) {
      try {
        fileOutputStream.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }

  public List readRules(AppInstallMetadata appInstallMetadata) {
    List parse;
    synchronized (RULES_LOCK) {
      List emptyList = Collections.emptyList();
      if (appInstallMetadata != null) {
        try {
          emptyList = this.mRuleIndexingController.identifyRulesToEvaluate(appInstallMetadata);
        } catch (Exception e) {
          Slog.w(
              "IntegrityFileManager", "Error identifying the rule indexes. Trying unindexed.", e);
        }
      }
      parse =
          this.mRuleParser.parse(
              RandomAccessObject.ofFile(new File(this.mRulesDir, "rules")), emptyList);
    }
    return parse;
  }

  public RuleMetadata readMetadata() {
    return this.mRuleMetadataCache;
  }

  public final void switchStagingRulesDir() {
    synchronized (RULES_LOCK) {
      File file = new File(this.mDataDir, "temp");
      if (!this.mRulesDir.renameTo(file)
          || !this.mStagingDir.renameTo(this.mRulesDir)
          || !file.renameTo(this.mStagingDir)) {
        throw new IOException("Error switching staging/rules directory");
      }
      for (File file2 : this.mStagingDir.listFiles()) {
        file2.delete();
      }
    }
  }

  public final void updateRuleIndexingController() {
    File file = new File(this.mRulesDir, "indexing");
    if (file.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          this.mRuleIndexingController = new RuleIndexingController(fileInputStream);
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
      RuleMetadataSerializer.serialize(this.mRuleMetadataCache, fileOutputStream);
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
}
