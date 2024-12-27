package com.android.server.tv.tunerresourcemanager;

import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UseCasePriorityHints {
    public static final boolean DEBUG = Log.isLoggable("UseCasePriorityHints", 3);
    public int mDefaultBackground;
    public int mDefaultForeground;
    public SparseArray mPriorityHints;
    public Set mVendorDefinedUseCase;

    public final void addNewUseCasePriority(int i, int i2, int i3) {
        this.mPriorityHints.append(i, new int[] {i2, i3});
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void parseInternal(InputStream inputStream) throws IOException, XmlPullParserException {
        char c;
        int i;
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        resolvePullParser.nextTag();
        resolvePullParser.require(2, (String) null, "config");
        while (resolvePullParser.next() != 3) {
            if (resolvePullParser.getEventType() == 2) {
                String name = resolvePullParser.getName();
                if (name.equals("useCaseDefault")) {
                    this.mDefaultForeground =
                            resolvePullParser.getAttributeInt((String) null, "fgPriority");
                    this.mDefaultBackground =
                            resolvePullParser.getAttributeInt((String) null, "bgPriority");
                    resolvePullParser.nextTag();
                    resolvePullParser.require(3, (String) null, name);
                } else if (name.equals("useCasePreDefined")) {
                    String attributeValue =
                            resolvePullParser.getAttributeValue((String) null, "type");
                    attributeValue.getClass();
                    switch (attributeValue.hashCode()) {
                        case -884787515:
                            if (attributeValue.equals("USE_CASE_BACKGROUND")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 377959794:
                            if (attributeValue.equals("USE_CASE_PLAYBACK")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1222007747:
                            if (attributeValue.equals("USE_CASE_LIVE")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1222209876:
                            if (attributeValue.equals("USE_CASE_SCAN")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1990900072:
                            if (attributeValue.equals("USE_CASE_RECORD")) {
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
                            i = 100;
                            break;
                        case 1:
                            i = 300;
                            break;
                        case 2:
                            i = 400;
                            break;
                        case 3:
                            i = 200;
                            break;
                        case 4:
                            i = 500;
                            break;
                        default:
                            i = -1;
                            break;
                    }
                    if (i == -1) {
                        Slog.e(
                                "UseCasePriorityHints",
                                "Wrong predefined use case name given in the vendor config.");
                    } else {
                        addNewUseCasePriority(
                                i,
                                resolvePullParser.getAttributeInt((String) null, "fgPriority"),
                                resolvePullParser.getAttributeInt((String) null, "bgPriority"));
                        resolvePullParser.nextTag();
                        resolvePullParser.require(3, (String) null, name);
                    }
                } else if (name.equals("useCaseVendor")) {
                    int attributeInt = resolvePullParser.getAttributeInt((String) null, "id");
                    addNewUseCasePriority(
                            attributeInt,
                            resolvePullParser.getAttributeInt((String) null, "fgPriority"),
                            resolvePullParser.getAttributeInt((String) null, "bgPriority"));
                    ((HashSet) this.mVendorDefinedUseCase).add(Integer.valueOf(attributeInt));
                    resolvePullParser.nextTag();
                    resolvePullParser.require(3, (String) null, name);
                } else {
                    if (resolvePullParser.getEventType() != 2) {
                        throw new IllegalStateException();
                    }
                    int i2 = 1;
                    while (i2 != 0) {
                        int next = resolvePullParser.next();
                        if (next == 2) {
                            i2++;
                        } else if (next == 3) {
                            i2--;
                        }
                    }
                }
            }
        }
        inputStream.close();
        for (int i3 = 0; i3 < this.mPriorityHints.size(); i3++) {
            int keyAt = this.mPriorityHints.keyAt(i3);
            int[] iArr = (int[]) this.mPriorityHints.get(keyAt);
            if (DEBUG) {
                Slog.d(
                        "UseCasePriorityHints",
                        "{defaultFg="
                                + this.mDefaultForeground
                                + ", defaultBg="
                                + this.mDefaultBackground
                                + "}");
                StringBuilder sb = new StringBuilder("{useCase=");
                sb.append(keyAt);
                sb.append(", fg=");
                sb.append(iArr[0]);
                sb.append(", bg=");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb, iArr[1], "}", "UseCasePriorityHints");
            }
        }
    }
}
