package com.android.server.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import com.android.internal.R;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import com.android.server.pm.pkg.parsing.ParsingUtils;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class ParsedServiceUtils {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Multi-variable type inference failed */
    public static ParseResult parseService(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) {
        TypedArray typedArray;
        ParsedServiceImpl parsedServiceImpl;
        int i2;
        int i3;
        int i4;
        String str2;
        ParseResult addMetaData;
        XmlResourceParser xmlResourceParser2;
        String str3;
        String packageName = parsingPackage.getPackageName();
        ParsedServiceImpl parsedServiceImpl2 = new ParsedServiceImpl();
        String name = xmlResourceParser.getName();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestService);
        String str4 = name;
        try {
            ParseResult parseMainComponent = ParsedMainComponentUtils.parseMainComponent(parsedServiceImpl2, name, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 12, 7, 13, 4, 1, 0, 8, 2, 6, 15, 17, 20);
            if (parseMainComponent.isError()) {
                ParseResult error = parseInput.error(parseMainComponent);
                obtainAttributes.recycle();
                return error;
            }
            typedArray = obtainAttributes;
            try {
                boolean hasValue = typedArray.hasValue(5);
                int i5 = 0;
                if (hasValue) {
                    ParsedServiceImpl parsedServiceImpl3 = parsedServiceImpl2;
                    parsedServiceImpl3.setExported(typedArray.getBoolean(5, false));
                    parsedServiceImpl = parsedServiceImpl3;
                } else {
                    parsedServiceImpl = parsedServiceImpl2;
                }
                int i6 = 3;
                String nonConfigurationString = typedArray.getNonConfigurationString(3, 0);
                if (nonConfigurationString == null) {
                    nonConfigurationString = parsingPackage.getPermission();
                }
                parsedServiceImpl.setPermission(nonConfigurationString);
                int i7 = 1;
                int i8 = 2;
                parsedServiceImpl.setForegroundServiceType(typedArray.getInt(19, 0)).setFlags(parsedServiceImpl.getFlags() | ComponentParseUtils.flag(1, 9, typedArray) | ComponentParseUtils.flag(2, 10, typedArray) | ComponentParseUtils.flag(4, 14, typedArray) | ComponentParseUtils.flag(8, 18, typedArray) | ComponentParseUtils.flag(16, 21, typedArray) | ComponentParseUtils.flag(1073741824, 11, typedArray));
                boolean z2 = typedArray.getBoolean(16, false);
                if (z2) {
                    parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | 1048576);
                    parsingPackage.setVisibleToInstantApps(true);
                }
                typedArray.recycle();
                if (parsingPackage.isSaveStateDisallowed() && Objects.equals(parsedServiceImpl.getProcessName(), packageName)) {
                    return parseInput.error("Heavy-weight applications can not have services in main process");
                }
                int depth = xmlResourceParser.getDepth();
                while (true) {
                    int next = xmlResourceParser.next();
                    if (next != i7 && (next != i6 || xmlResourceParser.getDepth() > depth)) {
                        if (next == i8) {
                            String name2 = xmlResourceParser.getName();
                            name2.hashCode();
                            int i9 = -1;
                            switch (name2.hashCode()) {
                                case -1115949454:
                                    if (name2.equals("meta-data")) {
                                        i9 = i5;
                                        break;
                                    }
                                    break;
                                case -1029793847:
                                    if (name2.equals("intent-filter")) {
                                        i9 = i7;
                                        break;
                                    }
                                    break;
                                case -993141291:
                                    if (name2.equals("property")) {
                                        i9 = i8;
                                        break;
                                    }
                                    break;
                            }
                            switch (i9) {
                                case 0:
                                    i2 = depth;
                                    i3 = i8;
                                    i4 = i7;
                                    str2 = str4;
                                    addMetaData = ParsedComponentUtils.addMetaData(parsedServiceImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                                    break;
                                case 1:
                                    str2 = str4;
                                    i2 = depth;
                                    i3 = i8;
                                    i4 = i7;
                                    addMetaData = ParsedMainComponentUtils.parseIntentFilter(parsedServiceImpl, parsingPackage, resources, xmlResourceParser, z2, true, false, false, false, parseInput);
                                    if (addMetaData.isSuccess()) {
                                        ParsedIntentInfoImpl parsedIntentInfoImpl = (ParsedIntentInfoImpl) addMetaData.getResult();
                                        parsedServiceImpl.setOrder(Math.max(parsedIntentInfoImpl.getIntentFilter().getOrder(), parsedServiceImpl.getOrder()));
                                        parsedServiceImpl.addIntent(parsedIntentInfoImpl);
                                        break;
                                    }
                                    break;
                                case 2:
                                    xmlResourceParser2 = xmlResourceParser;
                                    str3 = str4;
                                    addMetaData = ParsedComponentUtils.addProperty(parsedServiceImpl, parsingPackage, resources, xmlResourceParser2, parseInput);
                                    str2 = str3;
                                    i2 = depth;
                                    i3 = i8;
                                    i4 = i7;
                                    break;
                                default:
                                    xmlResourceParser2 = xmlResourceParser;
                                    str3 = str4;
                                    addMetaData = ParsingUtils.unknownTag(str3, parsingPackage, xmlResourceParser2, parseInput);
                                    str2 = str3;
                                    i2 = depth;
                                    i3 = i8;
                                    i4 = i7;
                                    break;
                            }
                            if (addMetaData.isError()) {
                                return parseInput.error(addMetaData);
                            }
                            depth = i2;
                            i8 = i3;
                            i7 = i4;
                            str4 = str2;
                            i5 = 0;
                            i6 = 3;
                        }
                    }
                }
                int i10 = i7;
                if (!hasValue) {
                    boolean z3 = parsedServiceImpl.getIntents().size() > 0 ? i10 : 0;
                    if (z3 != 0) {
                        ParseResult deferError = parseInput.deferError(parsedServiceImpl.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", 150232615L);
                        if (deferError.isError()) {
                            return parseInput.error(deferError);
                        }
                    }
                    parsedServiceImpl.setExported(z3);
                }
                return parseInput.success(parsedServiceImpl);
            } catch (Throwable th) {
                th = th;
                typedArray.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            typedArray = obtainAttributes;
        }
    }
}
