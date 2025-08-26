package com.android.internal.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArraySet;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedAttributionUtils {
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a5, code lost:
    
        if (r5 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a7, code lost:
    
        r5 = java.util.Collections.EMPTY_LIST;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00aa, code lost:
    
        ((java.util.ArrayList) r5).trimToSize();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00b9, code lost:
    
        return r11.success(new com.android.internal.pm.pkg.component.ParsedAttributionImpl(r3, r4, r5));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ParseResult<ParsedAttribution> parseAttribution(Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAttribution);
        if (typedArrayObtainAttributes == null) {
            return parseInput.error("<attribution> could not be parsed");
        }
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString == null) {
                return parseInput.error("<attribution> does not specify android:tag");
            }
            if (nonConfigurationString.length() > 50) {
                return parseInput.error("android:tag is too long. Max length is 50");
            }
            int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
            if (resourceId == 0) {
                return parseInput.error("<attribution> does not specify android:label");
            }
            typedArrayObtainAttributes.recycle();
            int depth = xmlResourceParser.getDepth();
            List arrayList = null;
            while (true) {
                int next = xmlResourceParser.next();
                if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4) {
                    String name = xmlResourceParser.getName();
                    if (!name.equals("inherit-from")) {
                        return parseInput.error("Bad element under <attribution>: " + name);
                    }
                    typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAttributionInheritFrom);
                    if (typedArrayObtainAttributes == null) {
                        return parseInput.error("<inherit-from> could not be parsed");
                    }
                    try {
                        String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(nonConfigurationString2);
                    } finally {
                    }
                }
            }
        } finally {
        }
    }

    public static boolean isCombinationValid(List<ParsedAttribution> list) {
        if (list == null) {
            return true;
        }
        ArraySet arraySet = new ArraySet(list.size());
        ArraySet arraySet2 = new ArraySet();
        int size = list.size();
        if (size > 400) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!arraySet.add(list.get(i).getTag())) {
                return false;
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            List<String> inheritFrom = list.get(i2).getInheritFrom();
            int size2 = inheritFrom.size();
            for (int i3 = 0; i3 < size2; i3++) {
                String str = inheritFrom.get(i3);
                if (arraySet.contains(str) || !arraySet2.add(str)) {
                    return false;
                }
            }
        }
        return true;
    }
}
