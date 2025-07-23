package com.android.internal.pm.pkg.component;

import android.content.pm.PackageManager;
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
    public static ParseResult<ParsedService> parseService(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) throws XmlPullParserException, IOException {
        XmlResourceParser xmlResourceParser2;
        ParsingPackage parsingPackage2;
        ParseResult<?> addMetaData;
        XmlResourceParser xmlResourceParser3 = xmlResourceParser;
        String packageName = parsingPackage.getPackageName();
        ParsedServiceImpl parsedServiceImpl = new ParsedServiceImpl();
        String name = xmlResourceParser3.getName();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser3, R.styleable.AndroidManifestService);
        ParsingPackage parsingPackage3 = parsingPackage;
        try {
            ParseResult<?> parseMainComponent = ParsedMainComponentUtils.parseMainComponent(parsedServiceImpl, name, strArr, parsingPackage3, obtainAttributes, i, z, str, parseInput, 12, 7, 13, 4, 1, 0, 8, 2, 6, 15, 17, 20, 23);
            if (parseMainComponent.isError()) {
                return parseInput.error(parseMainComponent);
            }
            boolean hasValue = obtainAttributes.hasValue(5);
            if (hasValue) {
                parsedServiceImpl.setExported(obtainAttributes.getBoolean(5, false));
            }
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString == null) {
                nonConfigurationString = parsingPackage3.getPermission();
            }
            parsedServiceImpl.setPermission(nonConfigurationString);
            parsedServiceImpl.setForegroundServiceType(obtainAttributes.getInt(19, 0)).setFlags(parsedServiceImpl.getFlags() | ComponentParseUtils.flag(1, 9, obtainAttributes) | ComponentParseUtils.flag(2, 10, obtainAttributes) | ComponentParseUtils.flag(4, 14, obtainAttributes) | ComponentParseUtils.flag(8, 18, obtainAttributes) | ComponentParseUtils.flag(16, 21, obtainAttributes) | ComponentParseUtils.flag(1073741824, 11, obtainAttributes));
            if (Flags.enableSystemUserOnlyForServicesAndProviders()) {
                parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | ComponentParseUtils.flag(536870912, 22, obtainAttributes));
            }
            boolean z2 = obtainAttributes.getBoolean(16, false);
            if (z2) {
                parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | 1048576);
                parsingPackage3.setVisibleToInstantApps(true);
            }
            obtainAttributes.recycle();
            if (parsingPackage3.isSaveStateDisallowed() && Objects.equals(parsedServiceImpl.getProcessName(), packageName)) {
                return parseInput.error("Heavy-weight applications can not have services in main process");
            }
            int depth = xmlResourceParser3.getDepth();
            while (true) {
                int next = xmlResourceParser3.next();
                if (next != 1 && (next != 3 || xmlResourceParser3.getDepth() > depth)) {
                    if (next == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage3, xmlResourceParser3)) {
                        String name2 = xmlResourceParser3.getName();
                        name2.hashCode();
                        switch (name2) {
                            case "meta-data":
                                xmlResourceParser2 = xmlResourceParser3;
                                parsingPackage2 = parsingPackage3;
                                addMetaData = ParsedComponentUtils.addMetaData(parsedServiceImpl, parsingPackage2, resources, xmlResourceParser2, parseInput);
                                break;
                            case "intent-filter":
                                ParsedServiceImpl parsedServiceImpl2 = parsedServiceImpl;
                                xmlResourceParser2 = xmlResourceParser3;
                                parsingPackage2 = parsingPackage;
                                addMetaData = ParsedMainComponentUtils.parseIntentFilter(parsedServiceImpl2, parsingPackage2, resources, xmlResourceParser2, z2, true, false, false, false, parseInput);
                                parsedServiceImpl = parsedServiceImpl2;
                                if (addMetaData.isSuccess()) {
                                    ParsedIntentInfoImpl parsedIntentInfoImpl = (ParsedIntentInfoImpl) addMetaData.getResult();
                                    parsedServiceImpl.setOrder(Math.max(parsedIntentInfoImpl.getIntentFilter().getOrder(), parsedServiceImpl.getOrder()));
                                    parsedServiceImpl.addIntent(parsedIntentInfoImpl);
                                    break;
                                }
                                break;
                            case "property":
                                ParseResult<PackageManager.Property> addProperty = ParsedComponentUtils.addProperty(parsedServiceImpl, parsingPackage3, resources, xmlResourceParser3, parseInput);
                                xmlResourceParser2 = xmlResourceParser3;
                                parsingPackage2 = parsingPackage3;
                                addMetaData = addProperty;
                                break;
                            default:
                                ParseResult<?> unknownTag = ParsingUtils.unknownTag(name, parsingPackage3, xmlResourceParser3, parseInput);
                                xmlResourceParser2 = xmlResourceParser3;
                                parsingPackage2 = parsingPackage3;
                                addMetaData = unknownTag;
                                break;
                        }
                        if (addMetaData.isError()) {
                            return parseInput.error(addMetaData);
                        }
                        parsingPackage3 = parsingPackage2;
                        xmlResourceParser3 = xmlResourceParser2;
                    }
                }
            }
            if (!hasValue) {
                boolean z3 = parsedServiceImpl.getIntents().size() > 0;
                if (z3) {
                    ParseResult<?> deferError = parseInput.deferError(parsedServiceImpl.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
                    if (deferError.isError()) {
                        return parseInput.error(deferError);
                    }
                }
                parsedServiceImpl.setExported(z3);
            }
            return parseInput.success(parsedServiceImpl);
        } finally {
            obtainAttributes.recycle();
        }
    }
}
