package com.android.server.pm.pkg.parsing;

import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.ext.SdkExtensions;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.R;
import com.android.internal.os.ClassLoaderFactory;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.SharedUidMigration;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.permission.CompatibilityPermissionInfo;
import com.android.server.pm.pkg.component.ComponentMutateUtils;
import com.android.server.pm.pkg.component.ComponentParseUtils;
import com.android.server.pm.pkg.component.InstallConstraintsTagParser;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedActivityImpl;
import com.android.server.pm.pkg.component.ParsedActivityUtils;
import com.android.server.pm.pkg.component.ParsedApexSystemService;
import com.android.server.pm.pkg.component.ParsedApexSystemServiceUtils;
import com.android.server.pm.pkg.component.ParsedAttribution;
import com.android.server.pm.pkg.component.ParsedAttributionUtils;
import com.android.server.pm.pkg.component.ParsedComponent;
import com.android.server.pm.pkg.component.ParsedInstrumentation;
import com.android.server.pm.pkg.component.ParsedInstrumentationUtils;
import com.android.server.pm.pkg.component.ParsedIntentInfo;
import com.android.server.pm.pkg.component.ParsedIntentInfoImpl;
import com.android.server.pm.pkg.component.ParsedIntentInfoUtils;
import com.android.server.pm.pkg.component.ParsedPermission;
import com.android.server.pm.pkg.component.ParsedPermissionGroup;
import com.android.server.pm.pkg.component.ParsedPermissionUtils;
import com.android.server.pm.pkg.component.ParsedProcessUtils;
import com.android.server.pm.pkg.component.ParsedProvider;
import com.android.server.pm.pkg.component.ParsedProviderImpl;
import com.android.server.pm.pkg.component.ParsedProviderUtils;
import com.android.server.pm.pkg.component.ParsedService;
import com.android.server.pm.pkg.component.ParsedServiceUtils;
import com.android.server.pm.pkg.component.ParsedUsesPermission;
import com.android.server.pm.pkg.component.ParsedUsesPermissionImpl;
import com.android.server.pm.split.DefaultSplitAssetLoader;
import com.android.server.pm.split.SplitAssetDependencyLoader;
import com.android.server.pm.split.SplitAssetLoader;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.runtimemanifest.LegacyRuntimeManifestParseUtils;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import libcore.util.HexEncoding;

/* loaded from: classes3.dex */
public class ParsingPackageUtils {
  public Callback mCallback;
  public DisplayMetrics mDisplayMetrics;
  public String[] mSeparateProcesses;
  public List mSplitPermissionInfos;
  public static final int SDK_VERSION = Build.VERSION.SDK_INT;
  public static final String[] SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;
  public static boolean sCompatibilityModeEnabled = true;
  public static boolean sUseRoundIcon = false;

  public interface Callback {
    boolean hasFeature(String str);

    ParsingPackage startParsingPackage(
        String str, String str2, String str3, TypedArray typedArray, boolean z);
  }

  public ParsingPackageUtils(
      String[] strArr, DisplayMetrics displayMetrics, List list, Callback callback) {
    this.mSeparateProcesses = strArr;
    this.mDisplayMetrics = displayMetrics;
    this.mSplitPermissionInfos = list;
    this.mCallback = callback;
  }

  public ParseResult parsePackage(ParseInput parseInput, File file, int i) {
    if (file.isDirectory()) {
      return parseClusterPackage(parseInput, file, i);
    }
    return parseMonolithicPackage(parseInput, file, i);
  }

  public final ParseResult parseClusterPackage(ParseInput parseInput, File file, int i) {
    SplitAssetLoader defaultSplitAssetLoader;
    SparseArray sparseArray;
    ParseResult parseClusterPackageLite =
        ApkLiteParseUtils.parseClusterPackageLite(parseInput, file, (i & 512) != 0 ? 512 : 0);
    if (parseClusterPackageLite.isError()) {
      return parseInput.error(parseClusterPackageLite);
    }
    PackageLite packageLite = (PackageLite) parseClusterPackageLite.getResult();
    if (!packageLite.isIsolatedSplits() || ArrayUtils.isEmpty(packageLite.getSplitNames())) {
      defaultSplitAssetLoader = new DefaultSplitAssetLoader(packageLite, i);
      sparseArray = null;
    } else {
      try {
        sparseArray = SplitDependencyLoader.createDependenciesFromPackage(packageLite);
        defaultSplitAssetLoader = new SplitAssetDependencyLoader(packageLite, sparseArray, i);
      } catch (SplitDependencyLoader.IllegalDependencyException e) {
        return parseInput.error(
            KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID, e.getMessage());
      }
    }
    SparseArray sparseArray2 = sparseArray;
    SplitAssetLoader splitAssetLoader = defaultSplitAssetLoader;
    try {
      ParseResult parseBaseApk =
          parseBaseApk(
              parseInput,
              new File(packageLite.getBaseApkPath()),
              packageLite.getPath(),
              splitAssetLoader,
              i);
      if (parseBaseApk.isError()) {
        return parseInput.error(parseBaseApk);
      }
      ParsingPackage parsingPackage = (ParsingPackage) parseBaseApk.getResult();
      if (!ArrayUtils.isEmpty(packageLite.getSplitNames())) {
        parsingPackage.asSplit(
            packageLite.getSplitNames(),
            packageLite.getSplitApkPaths(),
            packageLite.getSplitRevisionCodes(),
            sparseArray2);
        int length = packageLite.getSplitNames().length;
        for (int i2 = 0; i2 < length; i2++) {
          ParseResult parseSplitApk =
              parseSplitApk(
                  parseInput, parsingPackage, i2, splitAssetLoader.getSplitAssetManager(i2), i);
          if (parseSplitApk.isError()) {
            return parseInput.error(parseSplitApk);
          }
        }
      }
      parsingPackage.set32BitAbiPreferred(packageLite.isUse32bitAbi());
      return parseInput.success(parsingPackage);
    } catch (IllegalArgumentException e2) {
      return parseInput.error(
          e2.getCause() instanceof IOException ? -2 : -100, e2.getMessage(), e2);
    } finally {
      IoUtils.closeQuietly(splitAssetLoader);
    }
  }

  public final ParseResult parseMonolithicPackage(ParseInput parseInput, File file, int i) {
    ParseResult parseMonolithicPackageLite =
        ApkLiteParseUtils.parseMonolithicPackageLite(parseInput, file, i);
    if (parseMonolithicPackageLite.isError()) {
      return parseInput.error(parseMonolithicPackageLite);
    }
    PackageLite packageLite = (PackageLite) parseMonolithicPackageLite.getResult();
    DefaultSplitAssetLoader defaultSplitAssetLoader = new DefaultSplitAssetLoader(packageLite, i);
    try {
      ParseResult parseBaseApk =
          parseBaseApk(parseInput, file, file.getCanonicalPath(), defaultSplitAssetLoader, i);
      if (parseBaseApk.isError()) {
        return parseInput.error(parseBaseApk);
      }
      return parseInput.success(
          ((ParsingPackage) parseBaseApk.getResult())
              .set32BitAbiPreferred(packageLite.isUse32bitAbi()));
    } catch (IOException e) {
      return parseInput.error(-102, "Failed to get path: " + file, e);
    } finally {
      IoUtils.closeQuietly(defaultSplitAssetLoader);
    }
  }

  public final ParseResult parseBaseApk(
      ParseInput parseInput, File file, String str, SplitAssetLoader splitAssetLoader, int i) {
    boolean z;
    String absolutePath = file.getAbsolutePath();
    String substring =
        absolutePath.startsWith("/mnt/expand/")
            ? absolutePath.substring(12, absolutePath.indexOf(47, 12))
            : null;
    try {
      AssetManager baseAssetManager = splitAssetLoader.getBaseAssetManager();
      int findCookieForPath = baseAssetManager.findCookieForPath(absolutePath);
      if (findCookieForPath == 0) {
        return parseInput.error(
            KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID,
            "Failed adding asset path: " + absolutePath);
      }
      try {
        XmlResourceParser openXmlResourceParser =
            baseAssetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
        try {
          ParseResult parseBaseApk =
              parseBaseApk(
                  parseInput,
                  absolutePath,
                  str,
                  new Resources(baseAssetManager, this.mDisplayMetrics, null),
                  openXmlResourceParser,
                  i);
          if (parseBaseApk.isError()) {
            ParseResult error =
                parseInput.error(
                    parseBaseApk.getErrorCode(),
                    absolutePath
                        + " (at "
                        + openXmlResourceParser.getPositionDescription()
                        + "): "
                        + parseBaseApk.getErrorMessage());
            openXmlResourceParser.close();
            return error;
          }
          ParsingPackage parsingPackage = (ParsingPackage) parseBaseApk.getResult();
          if (baseAssetManager.containsAllocatedTable()) {
            ParseResult deferError =
                parseInput.deferError(
                    "Targeting R+ (version 30 and above) requires the resources.arsc of installed"
                        + " APKs to be stored uncompressed and aligned on a 4-byte boundary",
                    132742131L);
            if (deferError.isError()) {
              ParseResult error2 = parseInput.error(-124, deferError.getErrorMessage());
              if (openXmlResourceParser != null) {
                openXmlResourceParser.close();
              }
              return error2;
            }
          }
          try {
            z = splitAssetLoader.getBaseApkAssets().definesOverlayable();
          } catch (IOException unused) {
            z = false;
          }
          if (z) {
            SparseArray assignedPackageIdentifiers =
                baseAssetManager.getAssignedPackageIdentifiers();
            int size = assignedPackageIdentifiers.size();
            for (int i2 = 0; i2 < size; i2++) {
              Map overlayableMap =
                  baseAssetManager.getOverlayableMap(
                      (String) assignedPackageIdentifiers.valueAt(i2));
              if (overlayableMap != null && !overlayableMap.isEmpty()) {
                for (String str2 : overlayableMap.keySet()) {
                  parsingPackage.addOverlayable(str2, (String) overlayableMap.get(str2));
                }
              }
            }
          }
          parsingPackage.setVolumeUuid(substring);
          if ((i & 32) != 0) {
            ParseResult signingDetails = getSigningDetails(parseInput, parsingPackage, false);
            if (signingDetails.isError()) {
              ParseResult error3 = parseInput.error(signingDetails);
              if (openXmlResourceParser != null) {
                openXmlResourceParser.close();
              }
              return error3;
            }
            parsingPackage.setSigningDetails((SigningDetails) signingDetails.getResult());
          } else {
            parsingPackage.setSigningDetails(SigningDetails.UNKNOWN);
          }
          ParseResult success = parseInput.success(parsingPackage);
          if (openXmlResourceParser != null) {
            openXmlResourceParser.close();
          }
          return success;
        } finally {
        }
      } catch (Exception e) {
        return parseInput.error(-102, "Failed to read manifest from " + absolutePath, e);
      }
    } catch (IllegalArgumentException e2) {
      return parseInput.error(
          e2.getCause() instanceof IOException ? -2 : -100, e2.getMessage(), e2);
    }
  }

  public final ParseResult parseSplitApk(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      int i,
      AssetManager assetManager,
      int i2) {
    String str = parsingPackage.getSplitCodePaths()[i];
    int findCookieForPath = assetManager.findCookieForPath(str);
    if (findCookieForPath == 0) {
      return parseInput.error(
          KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID, "Failed adding asset path: " + str);
    }
    try {
      XmlResourceParser openXmlResourceParser =
          assetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
      try {
        ParseResult parseSplitApk =
            parseSplitApk(
                parseInput,
                parsingPackage,
                new Resources(assetManager, this.mDisplayMetrics, null),
                openXmlResourceParser,
                i2,
                i);
        if (!parseSplitApk.isError()) {
          if (openXmlResourceParser != null) {
            openXmlResourceParser.close();
          }
          return parseSplitApk;
        }
        ParseResult error =
            parseInput.error(
                parseSplitApk.getErrorCode(),
                str
                    + " (at "
                    + openXmlResourceParser.getPositionDescription()
                    + "): "
                    + parseSplitApk.getErrorMessage());
        openXmlResourceParser.close();
        return error;
      } finally {
      }
    } catch (Exception e) {
      return parseInput.error(-102, "Failed to read manifest from " + str, e);
    }
  }

  public final ParseResult parseBaseApk(
      ParseInput parseInput,
      String str,
      String str2,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i) {
    ParseResult parsePackageSplitNames =
        ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
    if (parsePackageSplitNames.isError()) {
      return parseInput.error(parsePackageSplitNames);
    }
    Pair pair = (Pair) parsePackageSplitNames.getResult();
    String str3 = (String) pair.first;
    String str4 = (String) pair.second;
    if (!TextUtils.isEmpty(str4)) {
      return parseInput.error(-106, "Expected base APK, but found split " + str4);
    }
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifest);
    try {
      ParsingPackage startParsingPackage =
          this.mCallback.startParsingPackage(
              str3,
              str,
              str2,
              obtainAttributes,
              xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false));
      ParseResult parseBaseApkTags =
          parseBaseApkTags(
              parseInput, startParsingPackage, obtainAttributes, resources, xmlResourceParser, i);
      return parseBaseApkTags.isError()
          ? parseBaseApkTags
          : parseInput.success(startParsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public final ParseResult parseSplitApk(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i,
      int i2) {
    ParseResult unknownTag;
    ParseResult parsePackageSplitNames =
        ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
    if (parsePackageSplitNames.isError()) {
      return parseInput.error(parsePackageSplitNames);
    }
    int depth = xmlResourceParser.getDepth();
    boolean z = false;
    while (true) {
      int next = xmlResourceParser.next();
      if (next != 1) {
        if (depth + 1 >= xmlResourceParser.getDepth() && next == 2) {
          if (!"application".equals(xmlResourceParser.getName())) {
            unknownTag =
                ParsingUtils.unknownTag(
                    "<manifest>", parsingPackage, xmlResourceParser, parseInput);
          } else if (z) {
            Slog.w("PackageParsing", "<manifest> has more than one <application>");
            unknownTag = parseInput.success((Object) null);
          } else {
            unknownTag =
                parseSplitApplication(
                    parseInput, parsingPackage, resources, xmlResourceParser, i, i2);
            z = true;
          }
          if (unknownTag.isError()) {
            return parseInput.error(unknownTag);
          }
        }
      } else {
        if (!z) {
          ParseResult deferError =
              parseInput.deferError("<manifest> does not contain an <application>", 150776642L);
          if (deferError.isError()) {
            return parseInput.error(deferError);
          }
        }
        return parseInput.success(parsingPackage);
      }
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x0172, code lost:

     return r18.success(r19);
  */
  /* JADX WARN: Code restructure failed: missing block: B:64:0x008a, code lost:

     if (r4.equals("activity-alias") == false) goto L24;
  */
  /* JADX WARN: Removed duplicated region for block: B:52:0x0147  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final ParseResult parseSplitApplication(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i,
      int i2) {
    boolean z;
    ParseResult parseProvider;
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestApplication);
    try {
      parsingPackage.setSplitHasCode(i2, obtainAttributes.getBoolean(7, true));
      String string = obtainAttributes.getString(46);
      if (string != null && !ClassLoaderFactory.isValidClassLoaderName(string)) {
        return parseInput.error("Invalid class loader name: " + string);
      }
      parsingPackage.setSplitClassLoaderName(i2, string);
      obtainAttributes.recycle();
      String str = parsingPackage.getSplitNames()[i2];
      int depth = xmlResourceParser.getDepth();
      while (true) {
        int next = xmlResourceParser.next();
        if (next != 1) {
          char c = 3;
          if (next != 3 || xmlResourceParser.getDepth() > depth) {
            if (next == 2) {
              String name = xmlResourceParser.getName();
              name.hashCode();
              switch (name.hashCode()) {
                case -1655966961:
                  if (name.equals("activity")) {
                    c = 0;
                    break;
                  }
                  c = 65535;
                  break;
                case -987494927:
                  if (name.equals("provider")) {
                    c = 1;
                    break;
                  }
                  c = 65535;
                  break;
                case -808719889:
                  if (name.equals("receiver")) {
                    c = 2;
                    break;
                  }
                  c = 65535;
                  break;
                case 790287890:
                  break;
                case 1984153269:
                  if (name.equals("service")) {
                    c = 4;
                    break;
                  }
                  c = 65535;
                  break;
                default:
                  c = 65535;
                  break;
              }
              switch (c) {
                case 0:
                  z = true;
                  parseProvider =
                      ParsedActivityUtils.parseActivityOrReceiver(
                          this.mSeparateProcesses,
                          parsingPackage,
                          resources,
                          xmlResourceParser,
                          i,
                          sUseRoundIcon,
                          str,
                          parseInput);
                  if (parseProvider.isSuccess()) {
                    ParsedActivity parsedActivity = (ParsedActivity) parseProvider.getResult();
                    if (z) {
                      parsingPackage.addActivity(parsedActivity);
                      break;
                    } else {
                      parsingPackage.addReceiver(parsedActivity);
                      break;
                    }
                  }
                  break;
                case 1:
                  parseProvider =
                      ParsedProviderUtils.parseProvider(
                          this.mSeparateProcesses,
                          parsingPackage,
                          resources,
                          xmlResourceParser,
                          i,
                          sUseRoundIcon,
                          str,
                          parseInput);
                  if (parseProvider.isSuccess()) {
                    parsingPackage.addProvider((ParsedProvider) parseProvider.getResult());
                    break;
                  }
                  break;
                case 2:
                  z = false;
                  parseProvider =
                      ParsedActivityUtils.parseActivityOrReceiver(
                          this.mSeparateProcesses,
                          parsingPackage,
                          resources,
                          xmlResourceParser,
                          i,
                          sUseRoundIcon,
                          str,
                          parseInput);
                  if (parseProvider.isSuccess()) {}
                  break;
                case 3:
                  parseProvider =
                      ParsedActivityUtils.parseActivityAlias(
                          parsingPackage,
                          resources,
                          xmlResourceParser,
                          sUseRoundIcon,
                          str,
                          parseInput);
                  if (parseProvider.isSuccess()) {
                    parsingPackage.addActivity((ParsedActivity) parseProvider.getResult());
                    break;
                  }
                  break;
                case 4:
                  parseProvider =
                      ParsedServiceUtils.parseService(
                          this.mSeparateProcesses,
                          parsingPackage,
                          resources,
                          xmlResourceParser,
                          i,
                          sUseRoundIcon,
                          str,
                          parseInput);
                  if (parseProvider.isSuccess()) {
                    parsingPackage.addService((ParsedService) parseProvider.getResult());
                    break;
                  }
                  break;
                default:
                  parseProvider =
                      parseSplitBaseAppChildTags(
                          parseInput, name, parsingPackage, resources, xmlResourceParser);
                  break;
              }
              if (parseProvider.isError()) {
                return parseInput.error(parseProvider);
              }
              if (hasTooManyComponents(parsingPackage)) {
                return parseInput.error(
                    "Total number of components has exceeded the maximum number: 30000");
              }
            }
          }
        }
      }
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static boolean hasTooManyComponents(ParsingPackage parsingPackage) {
    return ((parsingPackage.getActivities().size() + parsingPackage.getServices().size())
                + parsingPackage.getProviders().size())
            + parsingPackage.getReceivers().size()
        > 30000;
  }

  public final ParseResult parseSplitBaseAppChildTags(
      ParseInput parseInput,
      String str,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    str.hashCode();
    switch (str) {
      case "uses-native-library":
        return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-sdk-library":
        return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-library":
        return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "meta-data":
        ParseResult parseMetaData =
            parseMetaData(
                parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
        if (parseMetaData.isSuccess() && parseMetaData.getResult() != null) {
          parsingPackage.setMetaData(
              ((PackageManager.Property) parseMetaData.getResult())
                  .toBundle(parsingPackage.getMetaData()));
        }
        return parseMetaData;
      case "property":
        ParseResult parseMetaData2 =
            parseMetaData(
                parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
        if (parseMetaData2.isSuccess()) {
          parsingPackage.addProperty((PackageManager.Property) parseMetaData2.getResult());
        }
        return parseMetaData2;
      case "uses-static-library":
        return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-package":
        return parseInput.success((Object) null);
      default:
        return ParsingUtils.unknownTag(
            "<application>", parsingPackage, xmlResourceParser, parseInput);
    }
  }

  public final ParseResult parseBaseApkTags(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      TypedArray typedArray,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i) {
    ParseResult parseBaseApkTag;
    ParseResult parseSharedUser = parseSharedUser(parseInput, parsingPackage, typedArray);
    if (parseSharedUser.isError()) {
      return parseSharedUser;
    }
    boolean z = true;
    parsingPackage
        .setInstallLocation(anInteger(-1, 4, typedArray))
        .setTargetSandboxVersion(anInteger(1, 7, typedArray))
        .setExternalStorage((i & 8) != 0);
    int depth = xmlResourceParser.getDepth();
    boolean z2 = false;
    LegacyRuntimeManifestParseUtils.ApplicationReplacement applicationReplacement = null;
    while (true) {
      int next = xmlResourceParser.next();
      if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
        break;
      }
      if (next == 2) {
        String name = xmlResourceParser.getName();
        if ("application".equals(name)) {
          if (z2) {
            Slog.w("PackageParsing", "<manifest> has more than one <application>");
            parseBaseApkTag = parseInput.success((Object) null);
          } else {
            parseBaseApkTag =
                parseBaseApplication(parseInput, parsingPackage, resources, xmlResourceParser, i);
            z2 = true;
          }
        } else if ("application-salescode".equals(name)) {
          LegacyRuntimeManifestParseUtils.ApplicationReplacement
              replacementForApplicationSalescode =
                  LegacyRuntimeManifestParseUtils.getReplacementForApplicationSalescode(
                      parseInput, parsingPackage, resources, xmlResourceParser);
          if (replacementForApplicationSalescode != null) {
            applicationReplacement = replacementForApplicationSalescode;
          }
          parseBaseApkTag = parseInput.success(parsingPackage);
        } else {
          parseBaseApkTag =
              parseBaseApkTag(name, parseInput, parsingPackage, resources, xmlResourceParser, i);
        }
        if (parseBaseApkTag.isError()) {
          return parseInput.error(parseBaseApkTag);
        }
      }
    }
    if (applicationReplacement != null
        && RuntimeManifestUtils.useLegacyRuntimeManifest(parsingPackage.getMetaData())) {
      LegacyRuntimeManifestParseUtils.modifyParsingPackageWithReplacement(
          parsingPackage, applicationReplacement);
    }
    if (!z2 && ArrayUtils.size(parsingPackage.getInstrumentations()) == 0) {
      ParseResult deferError =
          parseInput.deferError(
              "<manifest> does not contain an <application> or <instrumentation>", 150776642L);
      if (deferError.isError()) {
        return parseInput.error(deferError);
      }
    }
    if (!ParsedAttributionUtils.isCombinationValid(parsingPackage.getAttributions())) {
      return parseInput.error(
          KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID,
          "Combination <attribution> tags are not valid");
    }
    if (ParsedPermissionUtils.declareDuplicatePermission(parsingPackage)) {
      return parseInput.error(-108, "Found duplicate permission with a different attribute value.");
    }
    convertCompatPermissions(parsingPackage);
    convertSplitPermissions(parsingPackage);
    if (PMRune.PM_NAL_GET_APP_LIST) {
      String packageName = parsingPackage.getPackageName();
      if (!"android.permission.cts.appthataccesseslocation".equals(packageName)
          && !"android.permission.cts.appthatrequestcustompermission".equals(packageName)
          && !"com.google.android.sdksandbox".equals(packageName)
          && !"android.appenumeration.queries.nothing.hasprovider".equals(packageName)
          && !"android.appenumeration.queries.nothing.haspermission".equals(packageName)) {
        z = false;
      }
      if (!z && ArrayUtils.size(parsingPackage.getInstrumentations()) == 0) {
        parsingPackage.addImplicitPermission("com.samsung.android.permission.GET_APP_LIST");
      }
    }
    if (parsingPackage.getTargetSdkVersion() < 4
        || (!parsingPackage.isSmallScreensSupported()
            && !parsingPackage.isNormalScreensSupported()
            && !parsingPackage.isLargeScreensSupported()
            && !parsingPackage.isExtraLargeScreensSupported()
            && !parsingPackage.isResizeable()
            && !parsingPackage.isAnyDensity())) {
      adjustPackageToBeUnresizeableAndUnpipable(parsingPackage);
    }
    return parseInput.success(parsingPackage);
  }

  public final ParseResult parseBaseApkTag(
      String str,
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i) {
    str.hashCode();
    switch (str) {
      case "uses-configuration":
        return parseUsesConfiguration(parseInput, parsingPackage, resources, xmlResourceParser);
      case "permission-tree":
        return parsePermissionTree(parseInput, parsingPackage, resources, xmlResourceParser);
      case "original-package":
        return parseOriginalPackage(parseInput, parsingPackage, resources, xmlResourceParser);
      case "overlay":
        return parseOverlay(parseInput, parsingPackage, resources, xmlResourceParser);
      case "restrict-update":
        return parseRestrictUpdateHash(i, parseInput, parsingPackage, resources, xmlResourceParser);
      case "feature":
      case "attribution":
        return parseAttribution(parseInput, parsingPackage, resources, xmlResourceParser);
      case "permission":
        return parsePermission(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-sdk":
        return parseUsesSdk(parseInput, parsingPackage, resources, xmlResourceParser, i);
      case "permission-group":
        return parsePermissionGroup(parseInput, parsingPackage, resources, xmlResourceParser);
      case "eat-comment":
      case "uses-gl-texture":
      case "supports-input":
      case "compatible-screens":
        XmlUtils.skipCurrentTag(xmlResourceParser);
        return parseInput.success(parsingPackage);
      case "key-sets":
        return parseKeySets(parseInput, parsingPackage, resources, xmlResourceParser);
      case "instrumentation":
        return parseInstrumentation(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-permission":
      case "uses-permission-sdk-23":
      case "uses-permission-sdk-m":
        return parseUsesPermission(parseInput, parsingPackage, resources, xmlResourceParser);
      case "adopt-permissions":
        return parseAdoptPermissions(parseInput, parsingPackage, resources, xmlResourceParser);
      case "queries":
        return parseQueries(parseInput, parsingPackage, resources, xmlResourceParser);
      case "install-constraints":
        return parseInstallConstraints(parseInput, parsingPackage, resources, xmlResourceParser);
      case "supports-screens":
        return parseSupportScreens(parseInput, parsingPackage, resources, xmlResourceParser);
      case "protected-broadcast":
        return parseProtectedBroadcast(parseInput, parsingPackage, resources, xmlResourceParser);
      case "feature-group":
        return parseFeatureGroup(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-feature":
        return parseUsesFeature(parseInput, parsingPackage, resources, xmlResourceParser);
      default:
        return ParsingUtils.unknownTag("<manifest>", parsingPackage, xmlResourceParser, parseInput);
    }
  }

  public static ParseResult parseSharedUser(
      ParseInput parseInput, ParsingPackage parsingPackage, TypedArray typedArray) {
    int anInteger;
    boolean z = false;
    String nonConfigString = nonConfigString(0, 0, typedArray);
    if (TextUtils.isEmpty(nonConfigString)) {
      return parseInput.success(parsingPackage);
    }
    if (!"android".equals(parsingPackage.getPackageName())) {
      ParseResult validateName =
          FrameworkParsingPackageUtils.validateName(parseInput, nonConfigString, true, true);
      if (validateName.isError()) {
        return parseInput.error(
            -107,
            "<manifest> specifies bad sharedUserId name \""
                + nonConfigString
                + "\": "
                + validateName.getErrorMessage());
      }
    }
    if (!SharedUidMigration.isDisabled()
        && (anInteger = anInteger(0, 13, typedArray)) != 0
        && anInteger < Build.VERSION.RESOURCES_SDK_INT) {
      z = true;
    }
    return parseInput.success(
        parsingPackage
            .setLeavingSharedUser(z)
            .setSharedUserId(nonConfigString.intern())
            .setSharedUserLabelResourceId(resId(3, typedArray)));
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:10:0x01b4, code lost:

     r2 = r17.getPackageName();
  */
  /* JADX WARN: Code restructure failed: missing block: B:11:0x01c6, code lost:

     if (r5.keySet().removeAll(r7.keySet()) == false) goto L84;
  */
  /* JADX WARN: Code restructure failed: missing block: B:13:0x01e0, code lost:

     return r16.error("Package" + r2 + " AndroidManifest.xml 'key-set' and 'public-key' names must be distinct.");
  */
  /* JADX WARN: Code restructure failed: missing block: B:15:0x01e1, code lost:

     r3 = r7.entrySet().iterator();
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x01ed, code lost:

     if (r3.hasNext() == false) goto L129;
  */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x01ef, code lost:

     r7 = (java.util.Map.Entry) r3.next();
     r9 = (java.lang.String) r7.getKey();
  */
  /* JADX WARN: Code restructure failed: missing block: B:19:0x0207, code lost:

     if (((android.util.ArraySet) r7.getValue()).size() != 0) goto L128;
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x022b, code lost:

     if (r8.contains(r9) == false) goto L131;
  */
  /* JADX WARN: Code restructure failed: missing block: B:24:0x024b, code lost:

     r7 = ((android.util.ArraySet) r7.getValue()).iterator();
  */
  /* JADX WARN: Code restructure failed: missing block: B:26:0x0259, code lost:

     if (r7.hasNext() == false) goto L134;
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x025b, code lost:

     r17.addKeySet(r9, (java.security.PublicKey) r5.get((java.lang.String) r7.next()));
  */
  /* JADX WARN: Code restructure failed: missing block: B:31:0x022d, code lost:

     android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
  */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x0209, code lost:

     android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
  */
  /* JADX WARN: Code restructure failed: missing block: B:38:0x0277, code lost:

     if (r17.getKeySetMapping().keySet().containsAll(r6) == false) goto L101;
  */
  /* JADX WARN: Code restructure failed: missing block: B:39:0x0279, code lost:

     r17.setUpgradeKeySets(r6);
  */
  /* JADX WARN: Code restructure failed: missing block: B:40:0x0280, code lost:

     return r16.success(r17);
  */
  /* JADX WARN: Code restructure failed: missing block: B:42:0x0299, code lost:

     return r16.error("Package" + r2 + " AndroidManifest.xml does not define all 'upgrade-key-set's .");
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseKeySets(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    char c;
    int depth = xmlResourceParser.getDepth();
    ArrayMap arrayMap = new ArrayMap();
    ArraySet arraySet = new ArraySet();
    ArrayMap arrayMap2 = new ArrayMap();
    ArraySet arraySet2 = new ArraySet();
    while (true) {
      int i = -1;
      String str = null;
      while (true) {
        int next = xmlResourceParser.next();
        if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
          if (next != 3) {
            String name = xmlResourceParser.getName();
            name.hashCode();
            switch (name.hashCode()) {
              case -1369233085:
                if (name.equals("upgrade-key-set")) {
                  c = 0;
                  break;
                }
                c = 65535;
                break;
              case -816609292:
                if (name.equals("key-set")) {
                  c = 1;
                  break;
                }
                c = 65535;
                break;
              case 1903323387:
                if (name.equals("public-key")) {
                  c = 2;
                  break;
                }
                c = 65535;
                break;
              default:
                c = 65535;
                break;
            }
            switch (c) {
              case 0:
                try {
                  arraySet.add(
                      resources
                          .obtainAttributes(
                              xmlResourceParser, R.styleable.AndroidManifestUpgradeKeySet)
                          .getNonResourceString(0));
                  XmlUtils.skipCurrentTag(xmlResourceParser);
                  break;
                } finally {
                }
              case 1:
                if (str != null) {
                  return parseInput.error(
                      "Improperly nested 'key-set' tag at "
                          + xmlResourceParser.getPositionDescription());
                }
                try {
                  str =
                      resources
                          .obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestKeySet)
                          .getNonResourceString(0);
                  arrayMap2.put(str, new ArraySet());
                  i = xmlResourceParser.getDepth();
                  break;
                } finally {
                }
              case 2:
                if (str == null) {
                  return parseInput.error(
                      "Improperly nested 'key-set' tag at "
                          + xmlResourceParser.getPositionDescription());
                }
                TypedArray obtainAttributes =
                    resources.obtainAttributes(
                        xmlResourceParser, R.styleable.AndroidManifestPublicKey);
                try {
                  String nonResString = nonResString(0, obtainAttributes);
                  String nonResString2 = nonResString(1, obtainAttributes);
                  if (nonResString2 == null && arrayMap.get(nonResString) == null) {
                    return parseInput.error(
                        "'public-key' "
                            + nonResString
                            + " must define a public-key value on first use at "
                            + xmlResourceParser.getPositionDescription());
                  }
                  if (nonResString2 != null) {
                    PublicKey parsePublicKey =
                        FrameworkParsingPackageUtils.parsePublicKey(nonResString2);
                    if (parsePublicKey == null) {
                      Slog.w(
                          "PackageParsing",
                          "No recognized valid key in 'public-key' tag at "
                              + xmlResourceParser.getPositionDescription()
                              + " key-set "
                              + str
                              + " will not be added to the package's defined key-sets.");
                      arraySet2.add(str);
                      XmlUtils.skipCurrentTag(xmlResourceParser);
                      break;
                    } else {
                      if (arrayMap.get(nonResString) != null
                          && !((PublicKey) arrayMap.get(nonResString)).equals(parsePublicKey)) {
                        return parseInput.error(
                            "Value of 'public-key' "
                                + nonResString
                                + " conflicts with previously defined value at "
                                + xmlResourceParser.getPositionDescription());
                      }
                      arrayMap.put(nonResString, parsePublicKey);
                    }
                  }
                  ((ArraySet) arrayMap2.get(str)).add(nonResString);
                  XmlUtils.skipCurrentTag(xmlResourceParser);
                } finally {
                }
                break;
              default:
                ParseResult unknownTag =
                    ParsingUtils.unknownTag(
                        "<key-sets>", parsingPackage, xmlResourceParser, parseInput);
                if (!unknownTag.isError()) {
                  break;
                } else {
                  return parseInput.error(unknownTag);
                }
            }
          } else if (xmlResourceParser.getDepth() == i) {
            break;
          }
        }
      }
    }
  }

  public static ParseResult parseAttribution(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    ParseResult parseAttribution =
        ParsedAttributionUtils.parseAttribution(resources, xmlResourceParser, parseInput);
    if (parseAttribution.isError()) {
      return parseInput.error(parseAttribution);
    }
    return parseInput.success(
        parsingPackage.addAttribution((ParsedAttribution) parseAttribution.getResult()));
  }

  public static ParseResult parsePermissionGroup(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    ParseResult parsePermissionGroup =
        ParsedPermissionUtils.parsePermissionGroup(
            parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
    if (parsePermissionGroup.isError()) {
      return parseInput.error(parsePermissionGroup);
    }
    return parseInput.success(
        parsingPackage.addPermissionGroup(
            (ParsedPermissionGroup) parsePermissionGroup.getResult()));
  }

  public static ParseResult parsePermission(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    ParseResult parsePermission =
        ParsedPermissionUtils.parsePermission(
            parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
    if (parsePermission.isError()) {
      return parseInput.error(parsePermission);
    }
    ParsedPermission parsedPermission = (ParsedPermission) parsePermission.getResult();
    if (parsedPermission != null) {
      parsingPackage.addPermission(parsedPermission);
    }
    return parseInput.success(parsingPackage);
  }

  public static ParseResult parsePermissionTree(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    ParseResult parsePermissionTree =
        ParsedPermissionUtils.parsePermissionTree(
            parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
    if (parsePermissionTree.isError()) {
      return parseInput.error(parsePermissionTree);
    }
    return parseInput.success(
        parsingPackage.addPermission((ParsedPermission) parsePermissionTree.getResult()));
  }

  public final int parseMinOrMaxSdkVersion(TypedArray typedArray, int i, int i2) {
    int i3;
    TypedValue peekValue = typedArray.peekValue(i);
    return (peekValue == null || (i3 = peekValue.type) < 16 || i3 > 31) ? i2 : peekValue.data;
  }

  /* JADX WARN: Removed duplicated region for block: B:103:0x00be A[Catch: all -> 0x01cb, TryCatch #0 {all -> 0x01cb, blocks: (B:3:0x0011, B:5:0x001d, B:10:0x002c, B:12:0x0044, B:13:0x0047, B:15:0x0053, B:16:0x0056, B:17:0x005f, B:20:0x0067, B:82:0x0074, B:91:0x00a3, B:92:0x00d1, B:97:0x00d7, B:100:0x00aa, B:102:0x00b4, B:103:0x00be, B:105:0x00c8, B:106:0x0087, B:109:0x0092, B:23:0x00e6, B:27:0x00f0, B:31:0x00f8, B:33:0x00fc, B:35:0x0104, B:42:0x0119, B:44:0x0121, B:51:0x0137, B:53:0x0142, B:57:0x0152, B:60:0x015c, B:63:0x0189, B:65:0x01bb, B:55:0x01b5), top: B:2:0x0011 }] */
  /* JADX WARN: Removed duplicated region for block: B:89:0x00a0  */
  /* JADX WARN: Removed duplicated region for block: B:94:0x00df  */
  /* JADX WARN: Removed duplicated region for block: B:96:0x00d7 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final ParseResult parseUsesPermission(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    boolean z;
    boolean z2;
    char c;
    ParseResult parseRequiredFeature;
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesPermission);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      if (TextUtils.length(nonResourceString) > 512) {
        return parseInput.error(-108, "The name in the <uses-permission> is greater than 512");
      }
      int i = 1;
      int parseMinOrMaxSdkVersion = parseMinOrMaxSdkVersion(obtainAttributes, 1, Integer.MIN_VALUE);
      int parseMinOrMaxSdkVersion2 =
          parseMinOrMaxSdkVersion(obtainAttributes, 2, Integer.MAX_VALUE);
      ArraySet arraySet = new ArraySet();
      int i2 = 3;
      String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
      if (nonConfigurationString != null) {
        arraySet.add(nonConfigurationString);
      }
      ArraySet arraySet2 = new ArraySet();
      String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(4, 0);
      if (nonConfigurationString2 != null) {
        arraySet2.add(nonConfigurationString2);
      }
      int i3 = obtainAttributes.getInt(5, 0);
      int depth = xmlResourceParser.getDepth();
      while (true) {
        int next = xmlResourceParser.next();
        if (next == i || (next == i2 && xmlResourceParser.getDepth() <= depth)) {
          break;
        }
        if (next != i2 && next != 4) {
          String name = xmlResourceParser.getName();
          int hashCode = name.hashCode();
          if (hashCode != 874138830) {
            if (hashCode == 1693350600 && name.equals("required-feature")) {
              c = 0;
              if (c != 0) {
                parseRequiredFeature =
                    parseRequiredFeature(parseInput, resources, xmlResourceParser);
                if (parseRequiredFeature.isSuccess()) {
                  arraySet.add((String) parseRequiredFeature.getResult());
                }
              } else if (c != 1) {
                parseRequiredFeature =
                    ParsingUtils.unknownTag(
                        "<uses-permission>", parsingPackage, xmlResourceParser, parseInput);
              } else {
                parseRequiredFeature =
                    parseRequiredNotFeature(parseInput, resources, xmlResourceParser);
                if (parseRequiredFeature.isSuccess()) {
                  arraySet2.add((String) parseRequiredFeature.getResult());
                }
              }
              if (!parseRequiredFeature.isError()) {
                return parseInput.error(parseRequiredFeature);
              }
              i = 1;
              i2 = 3;
            }
            c = 65535;
            if (c != 0) {}
            if (!parseRequiredFeature.isError()) {}
          } else {
            if (name.equals("required-not-feature")) {
              c = 1;
              if (c != 0) {}
              if (!parseRequiredFeature.isError()) {}
            }
            c = 65535;
            if (c != 0) {}
            if (!parseRequiredFeature.isError()) {}
          }
        }
        i = 1;
      }
      ParseResult success = parseInput.success(parsingPackage);
      if (nonResourceString == null) {
        return success;
      }
      int i4 = Build.VERSION.RESOURCES_SDK_INT;
      if (i4 >= parseMinOrMaxSdkVersion && i4 <= parseMinOrMaxSdkVersion2) {
        if (this.mCallback != null) {
          for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (!this.mCallback.hasFeature((String) arraySet.valueAt(size))) {
              return success;
            }
          }
          z = true;
          for (int size2 = arraySet2.size() - 1; size2 >= 0; size2--) {
            if (this.mCallback.hasFeature((String) arraySet2.valueAt(size2))) {
              return success;
            }
          }
        } else {
          z = true;
        }
        List usesPermissions = parsingPackage.getUsesPermissions();
        int size3 = usesPermissions.size();
        int i5 = 0;
        while (true) {
          if (i5 >= size3) {
            z2 = false;
            break;
          }
          ParsedUsesPermission parsedUsesPermission =
              (ParsedUsesPermission) usesPermissions.get(i5);
          if (!Objects.equals(parsedUsesPermission.getName(), nonResourceString)) {
            i5++;
          } else {
            if (parsedUsesPermission.getUsesPermissionFlags() != i3) {
              return parseInput.error(
                  "Conflicting uses-permissions flags: "
                      + nonResourceString
                      + " in package: "
                      + parsingPackage.getPackageName()
                      + " at: "
                      + xmlResourceParser.getPositionDescription());
            }
            Slog.w(
                "PackageParsing",
                "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: "
                    + nonResourceString
                    + " in package: "
                    + parsingPackage.getPackageName()
                    + " at: "
                    + xmlResourceParser.getPositionDescription());
            z2 = z;
          }
        }
        if (!z2) {
          parsingPackage.addUsesPermission(new ParsedUsesPermissionImpl(nonResourceString, i3));
        }
        return success;
      }
      return success;
    } finally {
      obtainAttributes.recycle();
    }
  }

  public final ParseResult parseRequiredFeature(
      ParseInput parseInput, Resources resources, AttributeSet attributeSet) {
    ParseResult success;
    TypedArray obtainAttributes =
        resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestRequiredFeature);
    try {
      String string = obtainAttributes.getString(0);
      if (TextUtils.isEmpty(string)) {
        success = parseInput.error("Feature name is missing from <required-feature> tag.");
      } else {
        success = parseInput.success(string);
      }
      return success;
    } finally {
      obtainAttributes.recycle();
    }
  }

  public final ParseResult parseRequiredNotFeature(
      ParseInput parseInput, Resources resources, AttributeSet attributeSet) {
    ParseResult success;
    TypedArray obtainAttributes =
        resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestRequiredNotFeature);
    try {
      String string = obtainAttributes.getString(0);
      if (TextUtils.isEmpty(string)) {
        success = parseInput.error("Feature name is missing from <required-not-feature> tag.");
      } else {
        success = parseInput.success(string);
      }
      return success;
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseUsesConfiguration(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    ConfigurationInfo configurationInfo = new ConfigurationInfo();
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesConfiguration);
    try {
      configurationInfo.reqTouchScreen = obtainAttributes.getInt(0, 0);
      configurationInfo.reqKeyboardType = obtainAttributes.getInt(1, 0);
      if (obtainAttributes.getBoolean(2, false)) {
        configurationInfo.reqInputFeatures = 1 | configurationInfo.reqInputFeatures;
      }
      configurationInfo.reqNavigation = obtainAttributes.getInt(3, 0);
      if (obtainAttributes.getBoolean(4, false)) {
        configurationInfo.reqInputFeatures |= 2;
      }
      parsingPackage.addConfigPreference(configurationInfo);
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseUsesFeature(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    FeatureInfo parseFeatureInfo = parseFeatureInfo(resources, xmlResourceParser);
    parsingPackage.addReqFeature(parseFeatureInfo);
    if (parseFeatureInfo.name == null) {
      ConfigurationInfo configurationInfo = new ConfigurationInfo();
      configurationInfo.reqGlEsVersion = parseFeatureInfo.reqGlEsVersion;
      parsingPackage.addConfigPreference(configurationInfo);
    }
    return parseInput.success(parsingPackage);
  }

  public static FeatureInfo parseFeatureInfo(Resources resources, AttributeSet attributeSet) {
    FeatureInfo featureInfo = new FeatureInfo();
    TypedArray obtainAttributes =
        resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestUsesFeature);
    try {
      featureInfo.name = obtainAttributes.getNonResourceString(0);
      featureInfo.version = obtainAttributes.getInt(3, 0);
      if (featureInfo.name == null) {
        featureInfo.reqGlEsVersion = obtainAttributes.getInt(1, 0);
      }
      if (obtainAttributes.getBoolean(2, true)) {
        featureInfo.flags |= 1;
      }
      return featureInfo;
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseFeatureGroup(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
    int depth = xmlResourceParser.getDepth();
    ArrayList arrayList = null;
    while (true) {
      int next = xmlResourceParser.next();
      if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
        break;
      }
      if (next == 2) {
        String name = xmlResourceParser.getName();
        if (name.equals("uses-feature")) {
          FeatureInfo parseFeatureInfo = parseFeatureInfo(resources, xmlResourceParser);
          parseFeatureInfo.flags = 1 | parseFeatureInfo.flags;
          arrayList = ArrayUtils.add(arrayList, parseFeatureInfo);
        } else {
          Slog.w(
              "PackageParsing",
              "Unknown element under <feature-group>: "
                  + name
                  + " at "
                  + parsingPackage.getBaseApkPath()
                  + " "
                  + xmlResourceParser.getPositionDescription());
        }
      }
    }
    if (arrayList != null) {
      FeatureInfo[] featureInfoArr = new FeatureInfo[arrayList.size()];
      featureGroupInfo.features = featureInfoArr;
      featureGroupInfo.features = (FeatureInfo[]) arrayList.toArray(featureInfoArr);
    }
    parsingPackage.addFeatureGroup(featureGroupInfo);
    return parseInput.success(parsingPackage);
  }

  /* JADX WARN: Removed duplicated region for block: B:17:0x0047 A[Catch: all -> 0x013d, TryCatch #0 {all -> 0x013d, blocks: (B:8:0x001d, B:10:0x0025, B:12:0x0029, B:14:0x002d, B:15:0x0041, B:17:0x0047, B:19:0x004b, B:21:0x004f, B:25:0x0066, B:27:0x006d, B:28:0x0073, B:30:0x007f, B:34:0x0087, B:36:0x009f, B:39:0x00a7, B:41:0x00b1, B:44:0x00b9, B:46:0x00cc, B:48:0x00d6, B:51:0x00de, B:52:0x00eb, B:53:0x00ef, B:56:0x00f7, B:66:0x0103, B:69:0x0111, B:70:0x0116, B:71:0x0124, B:74:0x012a, B:79:0x011e, B:59:0x0132, B:87:0x005d, B:89:0x003a), top: B:7:0x001d }] */
  /* JADX WARN: Removed duplicated region for block: B:30:0x007f A[Catch: all -> 0x013d, TRY_LEAVE, TryCatch #0 {all -> 0x013d, blocks: (B:8:0x001d, B:10:0x0025, B:12:0x0029, B:14:0x002d, B:15:0x0041, B:17:0x0047, B:19:0x004b, B:21:0x004f, B:25:0x0066, B:27:0x006d, B:28:0x0073, B:30:0x007f, B:34:0x0087, B:36:0x009f, B:39:0x00a7, B:41:0x00b1, B:44:0x00b9, B:46:0x00cc, B:48:0x00d6, B:51:0x00de, B:52:0x00eb, B:53:0x00ef, B:56:0x00f7, B:66:0x0103, B:69:0x0111, B:70:0x0116, B:71:0x0124, B:74:0x012a, B:79:0x011e, B:59:0x0132, B:87:0x005d, B:89:0x003a), top: B:7:0x001d }] */
  /* JADX WARN: Removed duplicated region for block: B:34:0x0087 A[Catch: all -> 0x013d, TRY_ENTER, TryCatch #0 {all -> 0x013d, blocks: (B:8:0x001d, B:10:0x0025, B:12:0x0029, B:14:0x002d, B:15:0x0041, B:17:0x0047, B:19:0x004b, B:21:0x004f, B:25:0x0066, B:27:0x006d, B:28:0x0073, B:30:0x007f, B:34:0x0087, B:36:0x009f, B:39:0x00a7, B:41:0x00b1, B:44:0x00b9, B:46:0x00cc, B:48:0x00d6, B:51:0x00de, B:52:0x00eb, B:53:0x00ef, B:56:0x00f7, B:66:0x0103, B:69:0x0111, B:70:0x0116, B:71:0x0124, B:74:0x012a, B:79:0x011e, B:59:0x0132, B:87:0x005d, B:89:0x003a), top: B:7:0x001d }] */
  /* JADX WARN: Removed duplicated region for block: B:88:0x0062  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseUsesSdk(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i) {
    boolean z;
    int i2;
    String str;
    TypedValue peekValue;
    String str2;
    ParseResult computeTargetSdkVersion;
    ParseResult unknownTag;
    TypedValue peekValue2;
    CharSequence charSequence;
    CharSequence charSequence2;
    int i3 = SDK_VERSION;
    if (i3 > 0) {
      int i4 = 0;
      boolean z2 = (i & 512) != 0;
      TypedArray obtainAttributes =
          resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesSdk);
      try {
        TypedValue peekValue3 = obtainAttributes.peekValue(0);
        SparseIntArray sparseIntArray = null;
        if (peekValue3 == null) {
          z = false;
          i2 = 1;
        } else if (peekValue3.type != 3 || (charSequence2 = peekValue3.string) == null) {
          i2 = peekValue3.data;
          z = true;
        } else {
          String charSequence3 = charSequence2.toString();
          z = !TextUtils.isEmpty(charSequence3);
          str = charSequence3;
          i2 = 1;
          peekValue = obtainAttributes.peekValue(1);
          if (peekValue != null) {
            i4 = i2;
            str2 = str;
          } else if (peekValue.type != 3 || (charSequence = peekValue.string) == null) {
            i4 = peekValue.data;
            str2 = str;
            str = null;
          } else {
            str2 = charSequence.toString();
            if (z) {
              str2 = str;
              str = str2;
            } else {
              str = str2;
            }
          }
          int i5 =
              (z2 || (peekValue2 = obtainAttributes.peekValue(2)) == null)
                  ? Integer.MAX_VALUE
                  : peekValue2.data;
          String[] strArr = SDK_CODENAMES;
          computeTargetSdkVersion =
              FrameworkParsingPackageUtils.computeTargetSdkVersion(i4, str, strArr, parseInput, z2);
          if (!computeTargetSdkVersion.isError()) {
            return parseInput.error(computeTargetSdkVersion);
          }
          int intValue = ((Integer) computeTargetSdkVersion.getResult()).intValue();
          ParseResult enableDeferredError =
              parseInput.enableDeferredError(parsingPackage.getPackageName(), intValue);
          if (enableDeferredError.isError()) {
            return parseInput.error(enableDeferredError);
          }
          ParseResult computeMinSdkVersion =
              FrameworkParsingPackageUtils.computeMinSdkVersion(i2, str2, i3, strArr, parseInput);
          if (computeMinSdkVersion.isError()) {
            return parseInput.error(computeMinSdkVersion);
          }
          parsingPackage
              .setMinSdkVersion(((Integer) computeMinSdkVersion.getResult()).intValue())
              .setTargetSdkVersion(intValue);
          if (z2) {
            ParseResult computeMaxSdkVersion =
                FrameworkParsingPackageUtils.computeMaxSdkVersion(i5, i3, parseInput);
            if (computeMaxSdkVersion.isError()) {
              return parseInput.error(computeMaxSdkVersion);
            }
            parsingPackage.setMaxSdkVersion(
                ((Integer) computeMaxSdkVersion.getResult()).intValue());
          }
          int depth = xmlResourceParser.getDepth();
          while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
              break;
            }
            if (next != 3 && next != 4) {
              if (xmlResourceParser.getName().equals("extension-sdk")) {
                if (sparseIntArray == null) {
                  sparseIntArray = new SparseIntArray();
                }
                unknownTag =
                    parseExtensionSdk(parseInput, resources, xmlResourceParser, sparseIntArray);
                XmlUtils.skipCurrentTag(xmlResourceParser);
              } else {
                unknownTag =
                    ParsingUtils.unknownTag(
                        "<uses-sdk>", parsingPackage, xmlResourceParser, parseInput);
              }
              if (unknownTag.isError()) {
                return parseInput.error(unknownTag);
              }
            }
          }
          parsingPackage.setMinExtensionVersions(exactSizedCopyOfSparseArray(sparseIntArray));
        }
        str = null;
        peekValue = obtainAttributes.peekValue(1);
        if (peekValue != null) {}
        if (z2) {}
        String[] strArr2 = SDK_CODENAMES;
        computeTargetSdkVersion =
            FrameworkParsingPackageUtils.computeTargetSdkVersion(i4, str, strArr2, parseInput, z2);
        if (!computeTargetSdkVersion.isError()) {}
      } finally {
        obtainAttributes.recycle();
      }
    }
    return parseInput.success(parsingPackage);
  }

  public static SparseIntArray exactSizedCopyOfSparseArray(SparseIntArray sparseIntArray) {
    if (sparseIntArray == null) {
      return null;
    }
    SparseIntArray sparseIntArray2 = new SparseIntArray(sparseIntArray.size());
    for (int i = 0; i < sparseIntArray.size(); i++) {
      sparseIntArray2.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
    }
    return sparseIntArray2;
  }

  public static ParseResult parseExtensionSdk(
      ParseInput parseInput,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      SparseIntArray sparseIntArray) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestExtensionSdk);
    try {
      int i = obtainAttributes.getInt(0, -1);
      int i2 = obtainAttributes.getInt(1, -1);
      obtainAttributes.recycle();
      if (i < 0) {
        return parseInput.error(-108, "<extension-sdk> must specify an sdkVersion >= 0");
      }
      if (i2 < 0) {
        return parseInput.error(-108, "<extension-sdk> must specify minExtensionVersion >= 0");
      }
      try {
        int extensionVersion = SdkExtensions.getExtensionVersion(i);
        if (extensionVersion < i2) {
          return parseInput.error(
              -12,
              "Package requires "
                  + i
                  + " extension version "
                  + i2
                  + " which exceeds device version "
                  + extensionVersion);
        }
        sparseIntArray.put(i, i2);
        return parseInput.success(sparseIntArray);
      } catch (RuntimeException unused) {
        return parseInput.error(-108, "Specified sdkVersion " + i + " is not valid");
      }
    } catch (Throwable th) {
      obtainAttributes.recycle();
      throw th;
    }
  }

  public static ParseResult parseRestrictUpdateHash(
      int i,
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    if ((i & 16) != 0) {
      TypedArray obtainAttributes =
          resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestRestrictUpdate);
      try {
        String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
        if (nonConfigurationString != null) {
          int length = nonConfigurationString.length();
          byte[] bArr = new byte[length / 2];
          for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] =
                (byte)
                    ((Character.digit(nonConfigurationString.charAt(i2), 16) << 4)
                        + Character.digit(nonConfigurationString.charAt(i2 + 1), 16));
          }
          parsingPackage.setRestrictUpdateHash(bArr);
        } else {
          parsingPackage.setRestrictUpdateHash(null);
        }
      } finally {
        obtainAttributes.recycle();
      }
    }
    return parseInput.success(parsingPackage);
  }

  public static ParseResult parseInstallConstraints(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    return InstallConstraintsTagParser.parseInstallConstraints(
        parseInput, parsingPackage, resources, xmlResourceParser);
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x018a, code lost:

     return r12.success(r13);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseQueries(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    int depth = xmlResourceParser.getDepth();
    while (true) {
      int next = xmlResourceParser.next();
      if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
        break;
      }
      if (next == 2) {
        if (xmlResourceParser.getName().equals(KnoxCustomManagerService.INTENT)) {
          ParseResult parseIntentInfo =
              ParsedIntentInfoUtils.parseIntentInfo(
                  null, parsingPackage, resources, xmlResourceParser, true, true, parseInput);
          if (parseIntentInfo.isError()) {
            return parseInput.error(parseIntentInfo);
          }
          IntentFilter intentFilter =
              ((ParsedIntentInfoImpl) parseIntentInfo.getResult()).getIntentFilter();
          int countActions = intentFilter.countActions();
          int countDataSchemes = intentFilter.countDataSchemes();
          int countDataTypes = intentFilter.countDataTypes();
          int length = intentFilter.getHosts().length;
          if (countDataSchemes == 0 && countDataTypes == 0 && countActions == 0) {
            return parseInput.error("intent tags must contain either an action or data.");
          }
          if (countActions > 1) {
            return parseInput.error("intent tag may have at most one action.");
          }
          if (countDataTypes > 1) {
            return parseInput.error("intent tag may have at most one data type.");
          }
          if (countDataSchemes > 1) {
            return parseInput.error("intent tag may have at most one data scheme.");
          }
          if (length > 1) {
            return parseInput.error("intent tag may have at most one data host.");
          }
          Intent intent = new Intent();
          int countCategories = intentFilter.countCategories();
          for (int i = 0; i < countCategories; i++) {
            intent.addCategory(intentFilter.getCategory(i));
          }
          String str = null;
          Uri build =
              countDataSchemes == 1
                  ? new Uri.Builder()
                      .scheme(intentFilter.getDataScheme(0))
                      .authority(length == 1 ? intentFilter.getHosts()[0] : null)
                      .path("/*")
                      .build()
                  : null;
          if (countDataTypes == 1) {
            String dataType = intentFilter.getDataType(0);
            if (!dataType.contains("/")) {
              dataType = dataType + "/*";
            }
            str = dataType;
            if (build == null) {
              build = new Uri.Builder().scheme("content").authority("*").path("/*").build();
            }
          }
          intent.setDataAndType(build, str);
          if (countActions == 1) {
            intent.setAction(intentFilter.getAction(0));
          }
          parsingPackage.addQueriesIntent(intent);
        } else if (xmlResourceParser.getName().equals("package")) {
          String nonConfigurationString =
              resources
                  .obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestQueriesPackage)
                  .getNonConfigurationString(0, 0);
          if (TextUtils.isEmpty(nonConfigurationString)) {
            return parseInput.error("Package name is missing from package tag.");
          }
          parsingPackage.addQueriesPackage(nonConfigurationString.intern());
        } else if (xmlResourceParser.getName().equals("provider")) {
          TypedArray obtainAttributes =
              resources.obtainAttributes(
                  xmlResourceParser, R.styleable.AndroidManifestQueriesProvider);
          try {
            String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(0, 0);
            if (TextUtils.isEmpty(nonConfigurationString2)) {
              return parseInput.error(-108, "Authority missing from provider tag.");
            }
            StringTokenizer stringTokenizer =
                new StringTokenizer(nonConfigurationString2, KnoxVpnFirewallHelper.DELIMITER);
            while (stringTokenizer.hasMoreElements()) {
              parsingPackage.addQueriesProvider(stringTokenizer.nextToken());
            }
          } finally {
            obtainAttributes.recycle();
          }
        } else {
          continue;
        }
      }
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:137:0x0487, code lost:

     if (com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.useLegacyRuntimeManifest(r25.getMetaData()) != false) goto L237;
  */
  /* JADX WARN: Code restructure failed: missing block: B:138:0x0489, code lost:

     com.samsung.android.server.p025pm.runtimemanifest.RuntimeManifestOverlayUtils.applyRuntimeManifestIfNeeded(r25, r26);
  */
  /* JADX WARN: Code restructure failed: missing block: B:140:0x0494, code lost:

     if (android.text.TextUtils.isEmpty(r25.getStaticSharedLibraryName()) == false) goto L246;
  */
  /* JADX WARN: Code restructure failed: missing block: B:142:0x049e, code lost:

     if (android.text.TextUtils.isEmpty(r25.getSdkLibraryName()) == false) goto L246;
  */
  /* JADX WARN: Code restructure failed: missing block: B:143:0x04a0, code lost:

     r1 = generateAppDetailsHiddenActivity(r24, r25);
  */
  /* JADX WARN: Code restructure failed: missing block: B:144:0x04a8, code lost:

     if (r1.isError() == false) goto L245;
  */
  /* JADX WARN: Code restructure failed: missing block: B:146:0x04ae, code lost:

     return r24.error(r1);
  */
  /* JADX WARN: Code restructure failed: missing block: B:147:0x04af, code lost:

     r25.addActivity((com.android.server.pm.pkg.component.ParsedActivity) r1.getResult());
  */
  /* JADX WARN: Code restructure failed: missing block: B:148:0x04b8, code lost:

     if (r18 == false) goto L248;
  */
  /* JADX WARN: Code restructure failed: missing block: B:149:0x04ba, code lost:

     r25.sortActivities();
  */
  /* JADX WARN: Code restructure failed: missing block: B:150:0x04bd, code lost:

     if (r19 == false) goto L250;
  */
  /* JADX WARN: Code restructure failed: missing block: B:151:0x04bf, code lost:

     r25.sortReceivers();
  */
  /* JADX WARN: Code restructure failed: missing block: B:152:0x04c2, code lost:

     if (r20 == false) goto L252;
  */
  /* JADX WARN: Code restructure failed: missing block: B:153:0x04c4, code lost:

     r25.sortServices();
  */
  /* JADX WARN: Code restructure failed: missing block: B:154:0x04c7, code lost:

     setMaxAspectRatio(r25);
     setMinAspectRatio(r25);
     setSupportsSizeChanges(r25);
     r25.setHasDomainUrls(hasDomainURLs(r25));
  */
  /* JADX WARN: Code restructure failed: missing block: B:155:0x04db, code lost:

     return r24.success(r25);
  */
  /* JADX WARN: Code restructure failed: missing block: B:238:0x02ec, code lost:

     if (r3.equals("provider") == false) goto L146;
  */
  /* JADX WARN: Removed duplicated region for block: B:203:0x043a  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final ParseResult parseBaseApplication(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i) {
    int i2;
    boolean z;
    ParseResult parseApexSystemService;
    boolean z2;
    String packageName = parsingPackage.getPackageName();
    int targetSdkVersion = parsingPackage.getTargetSdkVersion();
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestApplication);
    try {
      if (obtainAttributes == null) {
        return parseInput.error("<application> does not contain any attributes");
      }
      String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
      if (nonConfigurationString != null) {
        String packageName2 = parsingPackage.getPackageName();
        String buildClassName = ParsingUtils.buildClassName(packageName2, nonConfigurationString);
        if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
          return parseInput.error("<application> invalid android:name");
        }
        if (buildClassName == null) {
          return parseInput.error("Empty class name in package " + packageName2);
        }
        parsingPackage.setApplicationClassName(buildClassName);
      }
      TypedValue peekValue = obtainAttributes.peekValue(1);
      if (peekValue != null) {
        parsingPackage.setLabelResourceId(peekValue.resourceId);
        if (peekValue.resourceId == 0) {
          parsingPackage.setNonLocalizedLabel(peekValue.coerceToString());
        }
      }
      parseBaseAppBasicFlags(parsingPackage, obtainAttributes);
      String nonConfigString = nonConfigString(1024, 4, obtainAttributes);
      if (nonConfigString != null) {
        String buildClassName2 = ParsingUtils.buildClassName(packageName, nonConfigString);
        if (buildClassName2 == null) {
          return parseInput.error("Empty class name in package " + packageName);
        }
        parsingPackage.setManageSpaceActivityName(buildClassName2);
      }
      if (parsingPackage.isBackupAllowed()) {
        String nonConfigString2 = nonConfigString(1024, 16, obtainAttributes);
        if (nonConfigString2 != null) {
          String buildClassName3 = ParsingUtils.buildClassName(packageName, nonConfigString2);
          if (buildClassName3 == null) {
            return parseInput.error("Empty class name in package " + packageName);
          }
          parsingPackage
              .setBackupAgentName(buildClassName3)
              .setKillAfterRestoreAllowed(bool(true, 18, obtainAttributes))
              .setRestoreAnyVersion(bool(false, 21, obtainAttributes))
              .setFullBackupOnly(bool(false, 32, obtainAttributes))
              .setBackupInForeground(bool(false, 40, obtainAttributes));
        }
        TypedValue peekValue2 = obtainAttributes.peekValue(35);
        if (peekValue2 != null) {
          int i3 = peekValue2.resourceId;
          if (i3 == 0) {
            i3 = peekValue2.data == 0 ? -1 : 0;
          }
          parsingPackage.setFullBackupContentResourceId(i3);
        }
      }
      if (obtainAttributes.getBoolean(8, false)) {
        String nonResourceString = obtainAttributes.getNonResourceString(45);
        if (nonResourceString != null && !this.mCallback.hasFeature(nonResourceString)) {
          z2 = false;
          parsingPackage.setPersistent(z2);
        }
        z2 = true;
        parsingPackage.setPersistent(z2);
      }
      if (obtainAttributes.hasValueOrEmpty(37)) {
        parsingPackage.setResizeableActivity(
            Boolean.valueOf(obtainAttributes.getBoolean(37, true)));
      } else {
        parsingPackage.setResizeableActivityViaSdkVersion(targetSdkVersion >= 24);
      }
      ParseResult buildTaskAffinityName =
          ComponentParseUtils.buildTaskAffinityName(
              packageName,
              packageName,
              targetSdkVersion >= 8
                  ? obtainAttributes.getNonConfigurationString(12, 1024)
                  : obtainAttributes.getNonResourceString(12),
              parseInput);
      if (buildTaskAffinityName.isError()) {
        return parseInput.error(buildTaskAffinityName);
      }
      parsingPackage.setTaskAffinity((String) buildTaskAffinityName.getResult());
      String nonResourceString2 = obtainAttributes.getNonResourceString(48);
      if (nonResourceString2 != null) {
        String buildClassName4 = ParsingUtils.buildClassName(packageName, nonResourceString2);
        if (buildClassName4 == null) {
          return parseInput.error("Empty class name in package " + packageName);
        }
        parsingPackage.setAppComponentFactory(buildClassName4);
      }
      ParseResult buildProcessName =
          ComponentParseUtils.buildProcessName(
              packageName,
              null,
              targetSdkVersion >= 8
                  ? obtainAttributes.getNonConfigurationString(11, 1024)
                  : obtainAttributes.getNonResourceString(11),
              i,
              this.mSeparateProcesses,
              parseInput);
      if (buildProcessName.isError()) {
        return parseInput.error(buildProcessName);
      }
      String str = (String) buildProcessName.getResult();
      parsingPackage.setProcessName(str);
      if (parsingPackage.isSaveStateDisallowed() && str != null && !str.equals(packageName)) {
        return parseInput.error("cantSaveState applications can not use custom processes");
      }
      String classLoaderName = parsingPackage.getClassLoaderName();
      if (classLoaderName != null && !ClassLoaderFactory.isValidClassLoaderName(classLoaderName)) {
        return parseInput.error("Invalid class loader name: " + classLoaderName);
      }
      parsingPackage.setGwpAsanMode(obtainAttributes.getInt(62, -1));
      parsingPackage.setMemtagMode(obtainAttributes.getInt(64, -1));
      if (obtainAttributes.hasValue(65)) {
        parsingPackage.setNativeHeapZeroInitialized(obtainAttributes.getBoolean(65, false) ? 1 : 0);
      }
      if (obtainAttributes.hasValue(67)) {
        parsingPackage.setRequestRawExternalStorageAccess(
            Boolean.valueOf(obtainAttributes.getBoolean(67, false)));
      }
      if (obtainAttributes.hasValue(68)) {
        parsingPackage.setRequestForegroundServiceExemption(obtainAttributes.getBoolean(68, false));
      }
      ParseResult parseKnownActivityEmbeddingCerts =
          ParsingUtils.parseKnownActivityEmbeddingCerts(
              obtainAttributes, resources, 72, parseInput);
      if (parseKnownActivityEmbeddingCerts.isError()) {
        return parseInput.error(parseKnownActivityEmbeddingCerts);
      }
      Set set = (Set) parseKnownActivityEmbeddingCerts.getResult();
      if (set != null) {
        parsingPackage.setKnownActivityEmbeddingCerts(set);
      }
      obtainAttributes.recycle();
      int depth = xmlResourceParser.getDepth();
      boolean z3 = false;
      boolean z4 = false;
      boolean z5 = false;
      while (true) {
        int next = xmlResourceParser.next();
        if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
          char c = 2;
          if (next == 2) {
            String name = xmlResourceParser.getName();
            name.hashCode();
            switch (name.hashCode()) {
              case -1655966961:
                if (name.equals("activity")) {
                  c = 0;
                  break;
                }
                c = 65535;
                break;
              case -1572095710:
                if (name.equals("apex-system-service")) {
                  c = 1;
                  break;
                }
                c = 65535;
                break;
              case -987494927:
                break;
              case -808719889:
                if (name.equals("receiver")) {
                  c = 3;
                  break;
                }
                c = 65535;
                break;
              case 790287890:
                if (name.equals("activity-alias")) {
                  c = 4;
                  break;
                }
                c = 65535;
                break;
              case 920944669:
                if (name.equals("provider-salescode")) {
                  c = 5;
                  break;
                }
                c = 65535;
                break;
              case 1984153269:
                if (name.equals("service")) {
                  c = 6;
                  break;
                }
                c = 65535;
                break;
              case 1989484475:
                if (name.equals("activity-salescode")) {
                  c = 7;
                  break;
                }
                c = 65535;
                break;
              default:
                c = 65535;
                break;
            }
            switch (c) {
              case 0:
                i2 = depth;
                z = true;
                parseApexSystemService =
                    ParsedActivityUtils.parseActivityOrReceiver(
                        this.mSeparateProcesses,
                        parsingPackage,
                        resources,
                        xmlResourceParser,
                        i,
                        sUseRoundIcon,
                        null,
                        parseInput);
                if (parseApexSystemService.isSuccess()) {
                  ParsedActivity parsedActivity =
                      (ParsedActivity) parseApexSystemService.getResult();
                  if (!z) {
                    boolean z6 = parsedActivity.getOrder() != 0;
                    parsingPackage.addReceiver(parsedActivity);
                    z4 |= z6;
                    break;
                  } else {
                    boolean z7 = parsedActivity.getOrder() != 0;
                    parsingPackage.addActivity(parsedActivity);
                    z3 |= z7;
                    break;
                  }
                }
                break;
              case 1:
                i2 = depth;
                parseApexSystemService =
                    ParsedApexSystemServiceUtils.parseApexSystemService(
                        resources, xmlResourceParser, parseInput);
                if (parseApexSystemService.isSuccess()) {
                  parsingPackage.addApexSystemService(
                      (ParsedApexSystemService) parseApexSystemService.getResult());
                  break;
                }
                break;
              case 2:
                i2 = depth;
                parseApexSystemService =
                    ParsedProviderUtils.parseProvider(
                        this.mSeparateProcesses,
                        parsingPackage,
                        resources,
                        xmlResourceParser,
                        i,
                        sUseRoundIcon,
                        null,
                        parseInput);
                if (parseApexSystemService.isSuccess()) {
                  parsingPackage.addProvider((ParsedProvider) parseApexSystemService.getResult());
                  break;
                }
                break;
              case 3:
                i2 = depth;
                z = false;
                parseApexSystemService =
                    ParsedActivityUtils.parseActivityOrReceiver(
                        this.mSeparateProcesses,
                        parsingPackage,
                        resources,
                        xmlResourceParser,
                        i,
                        sUseRoundIcon,
                        null,
                        parseInput);
                if (parseApexSystemService.isSuccess()) {}
                break;
              case 4:
                i2 = depth;
                parseApexSystemService =
                    ParsedActivityUtils.parseActivityAlias(
                        parsingPackage,
                        resources,
                        xmlResourceParser,
                        sUseRoundIcon,
                        null,
                        parseInput);
                if (parseApexSystemService.isSuccess()) {
                  ParsedActivity parsedActivity2 =
                      (ParsedActivity) parseApexSystemService.getResult();
                  z3 |= parsedActivity2.getOrder() != 0;
                  parsingPackage.addActivity(parsedActivity2);
                  break;
                }
                break;
              case 5:
                i2 = depth;
                if (RuntimeManifestUtils.useLegacyRuntimeManifest(parsingPackage.getMetaData())) {
                  LegacyRuntimeManifestParseUtils.parseOverlayComponentAndModify(
                      parsingPackage.getPackageName(),
                      parsingPackage.getProviders(),
                      resources,
                      xmlResourceParser,
                      parseInput,
                      "<provider-salescode>");
                }
                parseApexSystemService = parseInput.success(new ParsedProviderImpl());
                break;
              case 6:
                i2 = depth;
                parseApexSystemService =
                    ParsedServiceUtils.parseService(
                        this.mSeparateProcesses,
                        parsingPackage,
                        resources,
                        xmlResourceParser,
                        i,
                        sUseRoundIcon,
                        null,
                        parseInput);
                if (parseApexSystemService.isSuccess()) {
                  ParsedService parsedService = (ParsedService) parseApexSystemService.getResult();
                  z5 |= parsedService.getOrder() != 0;
                  parsingPackage.addService(parsedService);
                  break;
                }
                break;
              case 7:
                if (RuntimeManifestUtils.useLegacyRuntimeManifest(parsingPackage.getMetaData())) {
                  LegacyRuntimeManifestParseUtils.parseOverlayComponentAndModify(
                      parsingPackage.getPackageName(),
                      parsingPackage.getActivities(),
                      resources,
                      xmlResourceParser,
                      parseInput,
                      "<activity-salescode>");
                }
                parseApexSystemService = parseInput.success(new ParsedActivityImpl());
                i2 = depth;
                break;
              default:
                parseApexSystemService =
                    parseBaseAppChildTag(
                        parseInput, name, parsingPackage, resources, xmlResourceParser, i);
                i2 = depth;
                break;
            }
            if (parseApexSystemService.isError()) {
              return parseInput.error(parseApexSystemService);
            }
            if (hasTooManyComponents(parsingPackage)) {
              return parseInput.error(
                  "Total number of components has exceeded the maximum number: 30000");
            }
            depth = i2;
          }
        }
      }
    } finally {
      obtainAttributes.recycle();
    }
  }

  public final void parseBaseAppBasicFlags(ParsingPackage parsingPackage, TypedArray typedArray) {
    int targetSdkVersion = parsingPackage.getTargetSdkVersion();
    parsingPackage
        .setBackupAllowed(bool(true, 17, typedArray))
        .setClearUserDataAllowed(bool(true, 5, typedArray))
        .setClearUserDataOnFailedRestoreAllowed(bool(true, 54, typedArray))
        .setAllowNativeHeapPointerTagging(bool(true, 59, typedArray))
        .setEnabled(bool(true, 9, typedArray))
        .setExtractNativeLibrariesRequested(bool(true, 34, typedArray))
        .setDeclaredHavingCode(bool(true, 7, typedArray))
        .setTaskReparentingAllowed(bool(false, 14, typedArray))
        .setSaveStateDisallowed(bool(false, 47, typedArray))
        .setCrossProfile(bool(false, 58, typedArray))
        .setDebuggable(bool(false, 10, typedArray))
        .setDefaultToDeviceProtectedStorage(bool(false, 38, typedArray))
        .setDirectBootAware(bool(false, 39, typedArray))
        .setForceQueryable(bool(false, 57, typedArray))
        .setGame(bool(false, 31, typedArray))
        .setUserDataFragile(bool(false, 50, typedArray))
        .setLargeHeap(bool(false, 24, typedArray))
        .setMultiArch(bool(false, 33, typedArray))
        .setPreserveLegacyExternalStorage(bool(false, 61, typedArray))
        .setRequiredForAllUsers(bool(false, 27, typedArray))
        .setRtlSupported(bool(false, 26, typedArray))
        .setTestOnly(bool(false, 15, typedArray))
        .setUseEmbeddedDex(bool(false, 53, typedArray))
        .setNonSdkApiRequested(bool(false, 49, typedArray))
        .setVmSafeMode(bool(false, 20, typedArray))
        .setAutoRevokePermissions(anInt(60, typedArray))
        .setAttributionsAreUserVisible(bool(false, 69, typedArray))
        .setResetEnabledSettingsOnAppDataCleared(bool(false, 70, typedArray))
        .setOnBackInvokedCallbackEnabled(bool(false, 73, typedArray))
        .setAllowAudioPlaybackCapture(bool(targetSdkVersion >= 29, 55, typedArray))
        .setHardwareAccelerated(bool(targetSdkVersion >= 14, 23, typedArray))
        .setRequestLegacyExternalStorage(bool(targetSdkVersion < 29, 56, typedArray))
        .setCleartextTrafficAllowed(bool(targetSdkVersion < 28, 36, typedArray))
        .setUiOptions(anInt(25, typedArray))
        .setCategory(anInt(-1, 43, typedArray))
        .setMaxAspectRatio(aFloat(44, typedArray))
        .setMinAspectRatio(aFloat(51, typedArray))
        .setBannerResourceId(resId(30, typedArray))
        .setDescriptionResourceId(resId(13, typedArray))
        .setIconResourceId(resId(2, typedArray))
        .setLogoResourceId(resId(22, typedArray))
        .setNetworkSecurityConfigResourceId(resId(41, typedArray))
        .setRoundIconResourceId(resId(42, typedArray))
        .setThemeResourceId(resId(0, typedArray))
        .setDataExtractionRulesResourceId(resId(66, typedArray))
        .setLocaleConfigResourceId(resId(71, typedArray))
        .setClassLoaderName(string(46, typedArray))
        .setRequiredAccountType(string(29, typedArray))
        .setRestrictedAccountType(string(28, typedArray))
        .setZygotePreloadName(string(52, typedArray))
        .setPermission(nonConfigString(0, 6, typedArray));
  }

  public final ParseResult parseBaseAppChildTag(
      ParseInput parseInput,
      String str,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i) {
    str.hashCode();
    switch (str) {
      case "sdk-library":
        return parseSdkLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
      case "uses-native-library":
        return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-sdk-library":
        return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-library":
        return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "meta-data":
        ParseResult parseMetaData =
            parseMetaData(
                parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
        if (parseMetaData.isSuccess() && parseMetaData.getResult() != null) {
          parsingPackage.setMetaData(
              ((PackageManager.Property) parseMetaData.getResult())
                  .toBundle(parsingPackage.getMetaData()));
        }
        return parseMetaData;
      case "processes":
        return parseProcesses(
            parseInput, parsingPackage, resources, xmlResourceParser, this.mSeparateProcesses, i);
      case "static-library":
        return parseStaticLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
      case "property":
        ParseResult parseMetaData2 =
            parseMetaData(
                parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
        if (parseMetaData2.isSuccess()) {
          parsingPackage.addProperty((PackageManager.Property) parseMetaData2.getResult());
        }
        return parseMetaData2;
      case "uses-static-library":
        return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
      case "library":
        return parseLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
      case "profileable":
        return parseProfileable(parseInput, parsingPackage, resources, xmlResourceParser);
      case "uses-package":
        return parseInput.success((Object) null);
      default:
        return ParsingUtils.unknownTag(
            "<application>", parsingPackage, xmlResourceParser, parseInput);
    }
  }

  public static ParseResult parseSdkLibrary(
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      ParseInput parseInput) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestSdkLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      int i = obtainAttributes.getInt(1, -1);
      if (nonResourceString != null && i >= 0) {
        if (parsingPackage.getSharedUserId() != null) {
          return parseInput.error(-107, "sharedUserId not allowed in SDK library");
        }
        if (parsingPackage.getSdkLibraryName() == null) {
          return parseInput.success(
              parsingPackage
                  .setSdkLibraryName(nonResourceString.intern())
                  .setSdkLibVersionMajor(i)
                  .setSdkLibrary(true));
        }
        return parseInput.error("Multiple SDKs for package " + parsingPackage.getPackageName());
      }
      return parseInput.error(
          "Bad sdk-library declaration name: " + nonResourceString + " version: " + i);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseStaticLibrary(
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      ParseInput parseInput) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestStaticLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      int i = obtainAttributes.getInt(1, -1);
      int i2 = obtainAttributes.getInt(2, 0);
      if (nonResourceString != null && i >= 0) {
        if (parsingPackage.getSharedUserId() != null) {
          return parseInput.error(-107, "sharedUserId not allowed in static shared library");
        }
        if (parsingPackage.getStaticSharedLibraryName() == null) {
          return parseInput.success(
              parsingPackage
                  .setStaticSharedLibraryName(nonResourceString.intern())
                  .setStaticSharedLibraryVersion(PackageInfo.composeLongVersionCode(i2, i))
                  .setStaticSharedLibrary(true));
        }
        return parseInput.error(
            "Multiple static-shared libs for package " + parsingPackage.getPackageName());
      }
      return parseInput.error(
          "Bad static-library declaration name: " + nonResourceString + " version: " + i);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseLibrary(
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      ParseInput parseInput) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      if (nonResourceString != null) {
        String intern = nonResourceString.intern();
        if (!ArrayUtils.contains(parsingPackage.getLibraryNames(), intern)) {
          parsingPackage.addLibraryName(intern);
        }
      }
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseUsesSdkLibrary(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    String str = "";
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesSdkLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      int i = obtainAttributes.getInt(2, -1);
      String nonResourceString2 = obtainAttributes.getNonResourceString(1);
      if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
        if (parsingPackage.getUsesSdkLibraries().contains(nonResourceString)) {
          return parseInput.error(
              "Depending on multiple versions of SDK library " + nonResourceString);
        }
        String intern = nonResourceString.intern();
        String lowerCase =
            nonResourceString2
                .replace(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR, "")
                .toLowerCase();
        if ("".equals(lowerCase)) {
          lowerCase = SystemProperties.get("debug.pm.uses_sdk_library_default_cert_digest", "");
          try {
            HexEncoding.decode(lowerCase, false);
          } catch (IllegalArgumentException unused) {
          }
        }
        str = lowerCase;
        ParseResult parseAdditionalCertificates =
            parseAdditionalCertificates(parseInput, resources, xmlResourceParser);
        if (parseAdditionalCertificates.isError()) {
          return parseInput.error(parseAdditionalCertificates);
        }
        String[] strArr = (String[]) parseAdditionalCertificates.getResult();
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = str;
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        return parseInput.success(parsingPackage.addUsesSdkLibrary(intern, i, strArr2));
      }
      return parseInput.error(
          "Bad uses-sdk-library declaration name: "
              + nonResourceString
              + " version: "
              + i
              + " certDigest"
              + nonResourceString2);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseUsesStaticLibrary(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesStaticLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      int i = obtainAttributes.getInt(1, -1);
      String nonResourceString2 = obtainAttributes.getNonResourceString(2);
      if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
        if (parsingPackage.getUsesStaticLibraries().contains(nonResourceString)) {
          return parseInput.error(
              "Depending on multiple versions of static library " + nonResourceString);
        }
        String intern = nonResourceString.intern();
        String lowerCase =
            nonResourceString2
                .replace(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR, "")
                .toLowerCase();
        String[] strArr = EmptyArray.STRING;
        if (parsingPackage.getTargetSdkVersion() >= 27) {
          ParseResult parseAdditionalCertificates =
              parseAdditionalCertificates(parseInput, resources, xmlResourceParser);
          if (parseAdditionalCertificates.isError()) {
            return parseInput.error(parseAdditionalCertificates);
          }
          strArr = (String[]) parseAdditionalCertificates.getResult();
        }
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = lowerCase;
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        return parseInput.success(parsingPackage.addUsesStaticLibrary(intern, i, strArr2));
      }
      return parseInput.error(
          "Bad uses-static-library declaration name: "
              + nonResourceString
              + " version: "
              + i
              + " certDigest"
              + nonResourceString2);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseUsesLibrary(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      boolean z = obtainAttributes.getBoolean(1, true);
      if (nonResourceString != null) {
        String intern = nonResourceString.intern();
        if (z) {
          parsingPackage.addUsesLibrary(intern).removeUsesOptionalLibrary(intern);
        } else if (!ArrayUtils.contains(parsingPackage.getUsesLibraries(), intern)) {
          parsingPackage.addUsesOptionalLibrary(intern);
        }
      }
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseUsesNativeLibrary(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesNativeLibrary);
    try {
      String nonResourceString = obtainAttributes.getNonResourceString(0);
      boolean z = obtainAttributes.getBoolean(1, true);
      if (nonResourceString != null) {
        if (z) {
          parsingPackage
              .addUsesNativeLibrary(nonResourceString)
              .removeUsesOptionalNativeLibrary(nonResourceString);
        } else if (!ArrayUtils.contains(
            parsingPackage.getUsesNativeLibraries(), nonResourceString)) {
          parsingPackage.addUsesOptionalNativeLibrary(nonResourceString);
        }
      }
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseProcesses(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      String[] strArr,
      int i) {
    ParseResult parseProcesses =
        ParsedProcessUtils.parseProcesses(
            strArr, parsingPackage, resources, xmlResourceParser, i, parseInput);
    if (parseProcesses.isError()) {
      return parseInput.error(parseProcesses);
    }
    return parseInput.success(parsingPackage.setProcesses((Map) parseProcesses.getResult()));
  }

  public static ParseResult parseProfileable(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    boolean z;
    ParsingPackage profileableByShell;
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProfileable);
    try {
      boolean z2 = false;
      if (!parsingPackage.isProfileableByShell() && !bool(false, 1, obtainAttributes)) {
        z = false;
        profileableByShell = parsingPackage.setProfileableByShell(z);
        if (profileableByShell.isProfileable() && bool(true, 0, obtainAttributes)) {
          z2 = true;
        }
        return parseInput.success(profileableByShell.setProfileable(z2));
      }
      z = true;
      profileableByShell = parsingPackage.setProfileableByShell(z);
      if (profileableByShell.isProfileable()) {
        z2 = true;
      }
      return parseInput.success(profileableByShell.setProfileable(z2));
    } finally {
      obtainAttributes.recycle();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x0071, code lost:

     return r6.success(r0);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseAdditionalCertificates(
      ParseInput parseInput, Resources resources, XmlResourceParser xmlResourceParser) {
    String[] strArr = EmptyArray.STRING;
    int depth = xmlResourceParser.getDepth();
    while (true) {
      int next = xmlResourceParser.next();
      if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
        break;
      }
      if (next == 2 && xmlResourceParser.getName().equals("additional-certificate")) {
        TypedArray obtainAttributes =
            resources.obtainAttributes(
                xmlResourceParser, R.styleable.AndroidManifestAdditionalCertificate);
        try {
          String nonResourceString = obtainAttributes.getNonResourceString(0);
          if (TextUtils.isEmpty(nonResourceString)) {
            return parseInput.error(
                "Bad additional-certificate declaration with empty certDigest:"
                    + nonResourceString);
          }
          strArr =
              (String[])
                  ArrayUtils.appendElement(
                      String.class,
                      strArr,
                      nonResourceString
                          .replace(
                              com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR, "")
                          .toLowerCase());
        } finally {
          obtainAttributes.recycle();
        }
      }
    }
  }

  public static ParseResult generateAppDetailsHiddenActivity(
      ParseInput parseInput, ParsingPackage parsingPackage) {
    String packageName = parsingPackage.getPackageName();
    ParseResult buildTaskAffinityName =
        ComponentParseUtils.buildTaskAffinityName(
            packageName, packageName, ":app_details", parseInput);
    if (buildTaskAffinityName.isError()) {
      return parseInput.error(buildTaskAffinityName);
    }
    return parseInput.success(
        ParsedActivity.makeAppDetailsActivity(
            packageName,
            parsingPackage.getProcessName(),
            parsingPackage.getUiOptions(),
            (String) buildTaskAffinityName.getResult(),
            parsingPackage.isHardwareAccelerated()));
  }

  public static boolean hasDomainURLs(ParsingPackage parsingPackage) {
    List activities = parsingPackage.getActivities();
    int size = activities.size();
    for (int i = 0; i < size; i++) {
      List intents = ((ParsedActivity) activities.get(i)).getIntents();
      int size2 = intents.size();
      for (int i2 = 0; i2 < size2; i2++) {
        IntentFilter intentFilter = ((ParsedIntentInfo) intents.get(i2)).getIntentFilter();
        if (intentFilter.hasAction("android.intent.action.VIEW")
            && intentFilter.hasAction("android.intent.action.VIEW")
            && (intentFilter.hasDataScheme("http") || intentFilter.hasDataScheme("https"))) {
          return true;
        }
      }
    }
    return false;
  }

  public static void setMaxAspectRatio(ParsingPackage parsingPackage) {
    float f = parsingPackage.getTargetSdkVersion() < 26 ? 1.86f : 0.0f;
    float maxAspectRatio = parsingPackage.getMaxAspectRatio();
    if (maxAspectRatio != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f = maxAspectRatio;
    } else {
      Bundle metaData = parsingPackage.getMetaData();
      if (metaData != null && metaData.containsKey("android.max_aspect")) {
        f = metaData.getFloat("android.max_aspect", f);
      }
    }
    List activities = parsingPackage.getActivities();
    int size = activities.size();
    for (int i = 0; i < size; i++) {
      ParsedActivity parsedActivity = (ParsedActivity) activities.get(i);
      if (parsedActivity.getMaxAspectRatio() == -1.0f) {
        ComponentMutateUtils.setMaxAspectRatio(
            parsedActivity,
            parsedActivity.getResizeMode(),
            parsedActivity.getMetaData().getFloat("android.max_aspect", f));
      }
    }
  }

  public final void setMinAspectRatio(ParsingPackage parsingPackage) {
    float minAspectRatio = parsingPackage.getMinAspectRatio();
    List activities = parsingPackage.getActivities();
    int size = activities.size();
    for (int i = 0; i < size; i++) {
      ParsedActivity parsedActivity = (ParsedActivity) activities.get(i);
      if (parsedActivity.getMinAspectRatio() == -1.0f) {
        ComponentMutateUtils.setMinAspectRatio(
            parsedActivity, parsedActivity.getResizeMode(), minAspectRatio);
      }
    }
  }

  public final void setSupportsSizeChanges(ParsingPackage parsingPackage) {
    Bundle metaData = parsingPackage.getMetaData();
    boolean z = metaData != null && metaData.getBoolean("android.supports_size_changes", false);
    List activities = parsingPackage.getActivities();
    int size = activities.size();
    for (int i = 0; i < size; i++) {
      ParsedActivity parsedActivity = (ParsedActivity) activities.get(i);
      if (z || parsedActivity.getMetaData().getBoolean("android.supports_size_changes", false)) {
        ComponentMutateUtils.setSupportsSizeChanges(parsedActivity, true);
      }
    }
  }

  public static ParseResult parseOverlay(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestResourceOverlay);
    try {
      String string = obtainAttributes.getString(1);
      int anInt = anInt(0, 0, obtainAttributes);
      if (string == null) {
        return parseInput.error("<overlay> does not specify a target package");
      }
      if (anInt < 0 || anInt > 9999) {
        return parseInput.error("<overlay> priority must be between 0 and 9999");
      }
      String string2 = obtainAttributes.getString(5);
      String string3 = obtainAttributes.getString(6);
      if (FrameworkParsingPackageUtils.checkRequiredSystemProperties(string2, string3)) {
        return parseInput.success(
            parsingPackage
                .setResourceOverlay(true)
                .setOverlayTarget(string)
                .setOverlayPriority(anInt)
                .setOverlayTargetOverlayableName(obtainAttributes.getString(3))
                .setOverlayCategory(obtainAttributes.getString(2))
                .setOverlayIsStatic(bool(false, 4, obtainAttributes)));
      }
      String str =
          "Skipping target and overlay pair "
              + string
              + " and "
              + parsingPackage.getBaseApkPath()
              + ": overlay ignored due to required system property: "
              + string2
              + " with value: "
              + string3;
      Slog.i("PackageParsing", str);
      return parseInput.skip(str);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseProtectedBroadcast(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(
            xmlResourceParser, R.styleable.AndroidManifestProtectedBroadcast);
    try {
      String nonResString = nonResString(0, obtainAttributes);
      if (nonResString != null) {
        parsingPackage.addProtectedBroadcast(nonResString);
      }
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseSupportScreens(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestSupportsScreens);
    try {
      int anInt = anInt(0, 6, obtainAttributes);
      int anInt2 = anInt(0, 7, obtainAttributes);
      return parseInput.success(
          parsingPackage
              .setSmallScreensSupported(anInt(1, 1, obtainAttributes))
              .setNormalScreensSupported(anInt(1, 2, obtainAttributes))
              .setLargeScreensSupported(anInt(1, 3, obtainAttributes))
              .setExtraLargeScreensSupported(anInt(1, 5, obtainAttributes))
              .setResizeable(anInt(1, 4, obtainAttributes))
              .setAnyDensity(anInt(1, 0, obtainAttributes))
              .setRequiresSmallestWidthDp(anInt)
              .setCompatibleWidthLimitDp(anInt2)
              .setLargestWidthLimitDp(anInt(0, 8, obtainAttributes)));
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseInstrumentation(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    ParseResult parseInstrumentation =
        ParsedInstrumentationUtils.parseInstrumentation(
            parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
    if (parseInstrumentation.isError()) {
      return parseInput.error(parseInstrumentation);
    }
    return parseInput.success(
        parsingPackage.addInstrumentation(
            (ParsedInstrumentation) parseInstrumentation.getResult()));
  }

  public static ParseResult parseOriginalPackage(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestOriginalPackage);
    try {
      String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
      if (!parsingPackage.getPackageName().equals(nonConfigurationString)) {
        parsingPackage.addOriginalPackage(nonConfigurationString);
      }
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult parseAdoptPermissions(
      ParseInput parseInput,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestOriginalPackage);
    try {
      String nonConfigString = nonConfigString(0, 0, obtainAttributes);
      if (nonConfigString != null) {
        parsingPackage.addAdoptPermission(nonConfigString);
      }
      return parseInput.success(parsingPackage);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static void convertCompatPermissions(ParsingPackage parsingPackage) {
    int length = CompatibilityPermissionInfo.COMPAT_PERMS.length;
    for (int i = 0; i < length; i++) {
      CompatibilityPermissionInfo compatibilityPermissionInfo =
          CompatibilityPermissionInfo.COMPAT_PERMS[i];
      if (parsingPackage.getTargetSdkVersion() >= compatibilityPermissionInfo.getSdkVersion()) {
        return;
      }
      if (!parsingPackage
          .getRequestedPermissions()
          .contains(compatibilityPermissionInfo.getName())) {
        parsingPackage.addImplicitPermission(compatibilityPermissionInfo.getName());
      }
    }
  }

  public final void convertSplitPermissions(ParsingPackage parsingPackage) {
    int size = this.mSplitPermissionInfos.size();
    for (int i = 0; i < size; i++) {
      PermissionManager.SplitPermissionInfo splitPermissionInfo =
          (PermissionManager.SplitPermissionInfo) this.mSplitPermissionInfos.get(i);
      List requestedPermissions = parsingPackage.getRequestedPermissions();
      if (parsingPackage.getTargetSdkVersion() < splitPermissionInfo.getTargetSdk()
          && requestedPermissions.contains(splitPermissionInfo.getSplitPermission())) {
        List newPermissions = splitPermissionInfo.getNewPermissions();
        for (int i2 = 0; i2 < newPermissions.size(); i2++) {
          String str = (String) newPermissions.get(i2);
          if (!requestedPermissions.contains(str)) {
            parsingPackage.addImplicitPermission(str);
          }
        }
      }
    }
  }

  public static void adjustPackageToBeUnresizeableAndUnpipable(ParsingPackage parsingPackage) {
    List activities = parsingPackage.getActivities();
    int size = activities.size();
    for (int i = 0; i < size; i++) {
      ParsedActivity parsedActivity = (ParsedActivity) activities.get(i);
      ComponentMutateUtils.setResizeMode(parsedActivity, 0);
      ComponentMutateUtils.setExactFlags(parsedActivity, parsedActivity.getFlags() & (-4194305));
    }
  }

  public static ParseResult parseMetaData(
      ParsingPackage parsingPackage,
      ParsedComponent parsedComponent,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      String str,
      ParseInput parseInput) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestMetaData);
    try {
      String safeIntern = TextUtils.safeIntern(nonConfigString(0, 0, obtainAttributes));
      if (safeIntern == null) {
        return parseInput.error(str + " requires an android:name attribute");
      }
      String packageName = parsingPackage.getPackageName();
      PackageManager.Property property = null;
      String name = parsedComponent != null ? parsedComponent.getName() : null;
      TypedValue peekValue = obtainAttributes.peekValue(2);
      if (peekValue != null && peekValue.resourceId != 0) {
        property =
            new PackageManager.Property(safeIntern, peekValue.resourceId, true, packageName, name);
      } else {
        TypedValue peekValue2 = obtainAttributes.peekValue(1);
        if (peekValue2 != null) {
          int i = peekValue2.type;
          if (i == 3) {
            CharSequence coerceToString = peekValue2.coerceToString();
            property =
                new PackageManager.Property(
                    safeIntern,
                    coerceToString != null ? coerceToString.toString() : null,
                    packageName,
                    name);
          } else if (i == 18) {
            property =
                new PackageManager.Property(safeIntern, peekValue2.data != 0, packageName, name);
          } else if (i >= 16 && i <= 31) {
            property =
                new PackageManager.Property(safeIntern, peekValue2.data, false, packageName, name);
          } else if (i == 4) {
            property =
                new PackageManager.Property(safeIntern, peekValue2.getFloat(), packageName, name);
          } else {
            Slog.w(
                "PackageParsing",
                str
                    + " only supports string, integer, float, color, boolean, and resource"
                    + " reference types: "
                    + xmlResourceParser.getName()
                    + " at "
                    + parsingPackage.getBaseApkPath()
                    + " "
                    + xmlResourceParser.getPositionDescription());
          }
        } else {
          return parseInput.error(str + " requires an android:value or android:resource attribute");
        }
      }
      return parseInput.success(property);
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult getSigningDetails(
      ParseInput parseInput, ParsedPackage parsedPackage, boolean z) {
    return getSigningDetails(
        parseInput,
        parsedPackage.getBaseApkPath(),
        parsedPackage.isStaticSharedLibrary(),
        parsedPackage.getTargetSdkVersion(),
        parsedPackage.getSplitCodePaths(),
        z);
  }

  public static ParseResult getSigningDetails(
      ParseInput parseInput, ParsingPackage parsingPackage, boolean z) {
    return getSigningDetails(
        parseInput,
        parsingPackage.getBaseApkPath(),
        parsingPackage.isStaticSharedLibrary(),
        parsingPackage.getTargetSdkVersion(),
        parsingPackage.getSplitCodePaths(),
        z);
  }

  public static ParseResult getSigningDetails(
      ParseInput parseInput, String str, boolean z, int i, String[] strArr, boolean z2) {
    SigningDetails signingDetails = SigningDetails.UNKNOWN;
    Trace.traceBegin(262144L, "collectCertificates");
    try {
      ParseResult signingDetails2 = getSigningDetails(parseInput, str, z2, z, signingDetails, i);
      if (signingDetails2.isError()) {
        return parseInput.error(signingDetails2);
      }
      SigningDetails signingDetails3 = (SigningDetails) signingDetails2.getResult();
      boolean equals =
          new File(Environment.getRootDirectory(), "framework/framework-res.apk")
              .getAbsolutePath()
              .equals(str);
      if (!ArrayUtils.isEmpty(strArr) && !equals) {
        for (String str2 : strArr) {
          signingDetails2 = getSigningDetails(parseInput, str2, z2, z, signingDetails3, i);
          if (signingDetails2.isError()) {
            return parseInput.error(signingDetails2);
          }
        }
      }
      return signingDetails2;
    } finally {
      Trace.traceEnd(262144L);
    }
  }

  public static ParseResult getSigningDetails(
      ParseInput parseInput,
      String str,
      boolean z,
      boolean z2,
      SigningDetails signingDetails,
      int i) {
    ParseResult verify;
    int minimumSignatureSchemeVersionForTargetSdk =
        ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(i);
    if (z2) {
      minimumSignatureSchemeVersionForTargetSdk = 2;
    }
    if (z) {
      verify =
          ApkSignatureVerifier.unsafeGetCertsWithoutVerification(
              parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
    } else {
      verify =
          ApkSignatureVerifier.verify(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
    }
    if (verify.isError()) {
      return parseInput.error(verify);
    }
    if (signingDetails == SigningDetails.UNKNOWN) {
      return verify;
    }
    if (!Signature.areExactMatch(
        signingDetails.getSignatures(), ((SigningDetails) verify.getResult()).getSignatures())) {
      return parseInput.error(-104, str + " has mismatched certificates");
    }
    return parseInput.success(signingDetails);
  }

  public static void setCompatibilityModeEnabled(boolean z) {
    sCompatibilityModeEnabled = z;
  }

  public static void readConfigUseRoundIcon(Resources resources) {
    if (resources != null) {
      sUseRoundIcon = resources.getBoolean(android.R.bool.config_supportTelephonyTimeZoneFallback);
      return;
    }
    try {
      ApplicationInfo applicationInfo =
          ActivityThread.getPackageManager()
              .getApplicationInfo("android", 0L, UserHandle.myUserId());
      Resources system = Resources.getSystem();
      sUseRoundIcon =
          ResourcesManager.getInstance()
              .getResources(
                  (IBinder) null,
                  (String) null,
                  (String[]) null,
                  applicationInfo.resourceDirs,
                  applicationInfo.overlayPaths,
                  applicationInfo.sharedLibraryFiles,
                  (Integer) null,
                  (Configuration) null,
                  system.getCompatibilityInfo(),
                  system.getClassLoader(),
                  (List) null)
              .getBoolean(android.R.bool.config_supportTelephonyTimeZoneFallback);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public static boolean bool(boolean z, int i, TypedArray typedArray) {
    return typedArray.getBoolean(i, z);
  }

  public static float aFloat(int i, TypedArray typedArray) {
    return typedArray.getFloat(i, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public static int anInt(int i, int i2, TypedArray typedArray) {
    return typedArray.getInt(i2, i);
  }

  public static int anInteger(int i, int i2, TypedArray typedArray) {
    return typedArray.getInteger(i2, i);
  }

  public static int anInt(int i, TypedArray typedArray) {
    return typedArray.getInt(i, 0);
  }

  public static int resId(int i, TypedArray typedArray) {
    return typedArray.getResourceId(i, 0);
  }

  public static String string(int i, TypedArray typedArray) {
    return typedArray.getString(i);
  }

  public static String nonConfigString(int i, int i2, TypedArray typedArray) {
    return typedArray.getNonConfigurationString(i2, i);
  }

  public static String nonResString(int i, TypedArray typedArray) {
    return typedArray.getNonResourceString(i);
  }

  public static void writeKeySetMapping(Parcel parcel, Map map) {
    if (map == null) {
      parcel.writeInt(-1);
      return;
    }
    parcel.writeInt(map.size());
    for (String str : map.keySet()) {
      parcel.writeString(str);
      ArraySet arraySet = (ArraySet) map.get(str);
      if (arraySet == null) {
        parcel.writeInt(-1);
      } else {
        int size = arraySet.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
          parcel.writeSerializable((Serializable) arraySet.valueAt(i));
        }
      }
    }
  }

  public static ArrayMap readKeySetMapping(Parcel parcel) {
    int readInt = parcel.readInt();
    if (readInt == -1) {
      return null;
    }
    ArrayMap arrayMap = new ArrayMap();
    for (int i = 0; i < readInt; i++) {
      String readString = parcel.readString();
      int readInt2 = parcel.readInt();
      if (readInt2 == -1) {
        arrayMap.put(readString, null);
      } else {
        ArraySet arraySet = new ArraySet(readInt2);
        for (int i2 = 0; i2 < readInt2; i2++) {
          arraySet.add(
              (PublicKey)
                  parcel.readSerializable(PublicKey.class.getClassLoader(), PublicKey.class));
        }
        arrayMap.put(readString, arraySet);
      }
    }
    return arrayMap;
  }
}
