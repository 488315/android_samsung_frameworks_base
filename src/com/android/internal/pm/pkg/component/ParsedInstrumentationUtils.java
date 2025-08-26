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
    public static ParseResult<ParsedInstrumentation> parseInstrumentation(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParsedInstrumentationImpl parsedInstrumentationImpl = new ParsedInstrumentationImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestInstrumentation);
        try {
            ParseResult<?> component = ParsedComponentUtils.parseComponent(parsedInstrumentationImpl, str, parsingPackage, typedArrayObtainAttributes, z, parseInput, 7, -1, 1, 0, 6, 2, 8);
            if (component.isError()) {
                return parseInput.error(component);
            }
            parsedInstrumentationImpl.setTargetPackage(typedArrayObtainAttributes.getNonResourceString(3)).setTargetProcesses(typedArrayObtainAttributes.getNonResourceString(9)).setHandleProfiling(typedArrayObtainAttributes.getBoolean(4, false)).setFunctionalTest(typedArrayObtainAttributes.getBoolean(5, false));
            typedArrayObtainAttributes.recycle();
            ParseResult<?> allMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedInstrumentationImpl, parseInput);
            if (allMetaData.isError()) {
                return parseInput.error(allMetaData);
            }
            return parseInput.success((ParsedInstrumentation) allMetaData.getResult());
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }
}
