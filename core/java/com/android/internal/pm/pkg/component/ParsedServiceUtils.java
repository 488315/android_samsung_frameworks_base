package com.android.internal.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.multiuser.Flags;
import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedServiceUtils {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    public static ParseResult<ParsedService> parseService(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) throws XmlPullParserException, IOException {
        TypedArray typedArray;
        ParsedServiceImpl parsedServiceImpl;
        ParsingPackage parsingPackage2;
        String str2;
        int i2;
        int i3;
        String str3;
        int i4;
        int i5;
        int i6;
        ParsingPackage parsingPackage3;
        ParseResult<?> parseResult;
        String str4;
        String packageName = parsingPackage.getPackageName();
        ParsedServiceImpl parsedServiceImpl2 = new ParsedServiceImpl();
        String name = xmlResourceParser.getName();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestService);
        String str5 = name;
        try {
            ParseResult<?> parseMainComponent = ParsedMainComponentUtils.parseMainComponent(parsedServiceImpl2, name, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 12, 7, 13, 4, 1, 0, 8, 2, 6, 15, 17, 20);
            if (parseMainComponent.isError()) {
                try {
                    ParseResult<ParsedService> error = parseInput.error(parseMainComponent);
                    obtainAttributes.recycle();
                    return error;
                } catch (Throwable th) {
                    th = th;
                    typedArray = obtainAttributes;
                }
            } else {
                typedArray = obtainAttributes;
                try {
                    boolean hasValue = typedArray.hasValue(5);
                    int i7 = 0;
                    if (hasValue) {
                        try {
                            ParsedServiceImpl parsedServiceImpl3 = parsedServiceImpl2;
                            try {
                                parsedServiceImpl3.setExported(typedArray.getBoolean(5, false));
                                parsedServiceImpl = parsedServiceImpl3;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } else {
                        parsedServiceImpl = parsedServiceImpl2;
                    }
                    int i8 = 3;
                    try {
                        String nonConfigurationString = typedArray.getNonConfigurationString(3, 0);
                        parsedServiceImpl.setPermission(nonConfigurationString != null ? nonConfigurationString : parsingPackage.getPermission());
                        int i9 = 1;
                        int i10 = 2;
                        parsedServiceImpl.setForegroundServiceType(typedArray.getInt(19, 0)).setFlags(parsedServiceImpl.getFlags() | ComponentParseUtils.flag(1, 9, typedArray) | ComponentParseUtils.flag(2, 10, typedArray) | ComponentParseUtils.flag(4, 14, typedArray) | ComponentParseUtils.flag(8, 18, typedArray) | ComponentParseUtils.flag(16, 21, typedArray) | ComponentParseUtils.flag(1073741824, 11, typedArray));
                        if (Flags.enableSystemUserOnlyForServicesAndProviders()) {
                            parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | ComponentParseUtils.flag(536870912, 22, typedArray));
                        }
                        boolean z2 = typedArray.getBoolean(16, false);
                        if (z2) {
                            try {
                                parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | 1048576);
                                parsingPackage2 = parsingPackage;
                                try {
                                    parsingPackage2.setVisibleToInstantApps(true);
                                } catch (Throwable th4) {
                                    th = th4;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                            }
                        } else {
                            parsingPackage2 = parsingPackage;
                        }
                        typedArray.recycle();
                        if (parsingPackage.isSaveStateDisallowed()) {
                            str2 = packageName;
                            if (Objects.equals(parsedServiceImpl.getProcessName(), str2)) {
                                return parseInput.error("Heavy-weight applications can not have services in main process");
                            }
                        } else {
                            str2 = packageName;
                        }
                        int depth = xmlResourceParser.getDepth();
                        while (true) {
                            int next = xmlResourceParser.next();
                            if (next == i9) {
                                i2 = i9;
                            } else if (next == i8 && xmlResourceParser.getDepth() <= depth) {
                                i2 = i9;
                            } else if (next == i10 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(xmlResourceParser)) {
                                String name2 = xmlResourceParser.getName();
                                switch (name2.hashCode()) {
                                    case -1115949454:
                                        if (name2.equals("meta-data")) {
                                            i3 = i9;
                                            break;
                                        }
                                        i3 = -1;
                                        break;
                                    case -1029793847:
                                        if (name2.equals("intent-filter")) {
                                            i3 = i7;
                                            break;
                                        }
                                        i3 = -1;
                                        break;
                                    case -993141291:
                                        if (name2.equals("property")) {
                                            i3 = i10;
                                            break;
                                        }
                                        i3 = -1;
                                        break;
                                    default:
                                        i3 = -1;
                                        break;
                                }
                                switch (i3) {
                                    case 0:
                                        str3 = str2;
                                        i4 = i10;
                                        i5 = i9;
                                        i6 = i8;
                                        ParseResult<ParsedIntentInfoImpl> parseIntentFilter = ParsedMainComponentUtils.parseIntentFilter(parsedServiceImpl, parsingPackage, resources, xmlResourceParser, z2, true, false, false, false, parseInput);
                                        if (parseIntentFilter.isSuccess()) {
                                            ParsedIntentInfoImpl result = parseIntentFilter.getResult();
                                            parsedServiceImpl.setOrder(Math.max(result.getIntentFilter().getOrder(), parsedServiceImpl.getOrder()));
                                            parsedServiceImpl.addIntent(result);
                                        }
                                        parsingPackage3 = parsingPackage;
                                        parseResult = parseIntentFilter;
                                        str4 = str5;
                                        break;
                                    case 1:
                                        parseResult = ParsedComponentUtils.addMetaData(parsedServiceImpl, parsingPackage2, resources, xmlResourceParser, parseInput);
                                        str3 = str2;
                                        parsingPackage3 = parsingPackage2;
                                        i4 = i10;
                                        i5 = i9;
                                        i6 = i8;
                                        str4 = str5;
                                        break;
                                    case 2:
                                        parseResult = ParsedComponentUtils.addProperty(parsedServiceImpl, parsingPackage2, resources, xmlResourceParser, parseInput);
                                        str3 = str2;
                                        parsingPackage3 = parsingPackage2;
                                        i4 = i10;
                                        i5 = i9;
                                        i6 = i8;
                                        str4 = str5;
                                        break;
                                    default:
                                        str3 = str2;
                                        i4 = i10;
                                        i5 = i9;
                                        i6 = i8;
                                        parsingPackage3 = parsingPackage;
                                        str4 = str5;
                                        parseResult = ParsingUtils.unknownTag(str4, parsingPackage3, xmlResourceParser, parseInput);
                                        break;
                                }
                                if (parseResult.isError()) {
                                    return parseInput.error(parseResult);
                                }
                                parsingPackage2 = parsingPackage3;
                                str5 = str4;
                                i10 = i4;
                                i9 = i5;
                                i8 = i6;
                                str2 = str3;
                                i7 = 0;
                            }
                        }
                        if (!hasValue) {
                            if (parsedServiceImpl.getIntents().size() <= 0) {
                                i2 = 0;
                            }
                            boolean z3 = i2;
                            if (z3 != 0) {
                                ParseResult<?> deferError = parseInput.deferError(parsedServiceImpl.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
                                if (deferError.isError()) {
                                    return parseInput.error(deferError);
                                }
                            }
                            parsedServiceImpl.setExported(z3);
                        }
                        return parseInput.success(parsedServiceImpl);
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            }
        } catch (Throwable th8) {
            th = th8;
            typedArray = obtainAttributes;
        }
        typedArray.recycle();
        throw th;
    }
}
