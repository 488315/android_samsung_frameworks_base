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
    public static ParseResult<ParsedInstrumentation> parseInstrumentation(ParsingPackage pkg, Resources res, XmlResourceParser parser, boolean useRoundIcon, ParseInput input) throws IOException, XmlPullParserException {
        TypedArray sa;
        ParsedInstrumentationImpl instrumentation = new ParsedInstrumentationImpl();
        String tag = "<" + parser.getName() + ">";
        TypedArray sa2 = res.obtainAttributes(parser, R.styleable.AndroidManifestInstrumentation);
        try {
            ParseResult<ParsedInstrumentationImpl> result = ParsedComponentUtils.parseComponent(instrumentation, tag, pkg, sa2, useRoundIcon, input, 7, -1, 1, 0, 6, 2, 8);
            if (result.isError()) {
                try {
                    ParseResult<ParsedInstrumentation> error = input.error(result);
                    sa2.recycle();
                    return error;
                } catch (Throwable th) {
                    th = th;
                    sa = sa2;
                }
            } else {
                sa = sa2;
                try {
                    instrumentation.setTargetPackage(sa.getNonResourceString(3)).setTargetProcesses(sa.getNonResourceString(9)).setHandleProfiling(sa.getBoolean(4, false)).setFunctionalTest(sa.getBoolean(5, false));
                    sa.recycle();
                    ParseResult<ParsedInstrumentationImpl> result2 = ComponentParseUtils.parseAllMetaData(pkg, res, parser, tag, instrumentation, input);
                    return result2.isError() ? input.error(result2) : input.success(result2.getResult());
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            sa = sa2;
        }
        sa.recycle();
        throw th;
    }
}
