package com.android.internal.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedInstrumentationUtils {
    public static ParseResult<ParsedInstrumentation> parseInstrumentation(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws IOException, XmlPullParserException {
        ParsedInstrumentationImpl parsedInstrumentationImpl = new ParsedInstrumentationImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestInstrumentation);
        try {
            ParseResult<?> parseComponent = ParsedComponentUtils.parseComponent(parsedInstrumentationImpl, str, parsingPackage, obtainAttributes, z, parseInput, 7, -1, 1, 0, 6, 2, 8);
            if (parseComponent.isError()) {
                return parseInput.error(parseComponent);
            }
            parsedInstrumentationImpl.setTargetPackage(obtainAttributes.getNonResourceString(3)).setTargetProcesses(obtainAttributes.getNonResourceString(9)).setHandleProfiling(obtainAttributes.getBoolean(4, false)).setFunctionalTest(obtainAttributes.getBoolean(5, false));
            obtainAttributes.recycle();
            ParseResult<?> parseAllMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedInstrumentationImpl, parseInput);
            if (parseAllMetaData.isError()) {
                return parseInput.error(parseAllMetaData);
            }
            return parseInput.success((ParsedInstrumentation) parseAllMetaData.getResult());
        } finally {
            obtainAttributes.recycle();
        }
    }
}
