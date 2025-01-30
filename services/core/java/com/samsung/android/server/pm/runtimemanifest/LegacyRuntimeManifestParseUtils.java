package com.samsung.android.server.pm.runtimemanifest;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.pkg.component.ParsedComponentImpl;
import com.android.server.pm.pkg.component.ParsedMainComponentImpl;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import com.android.server.pm.pkg.parsing.ParsingUtils;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class LegacyRuntimeManifestParseUtils {

    public class ApplicationReplacement {
        public static String COERCED_LABEL = "coerced_label";
        public static String ENABLED = "enabled";
        public static String ICON = "icon";
        public static String LABEL = "label";
        public int mLabel = 0;
        public CharSequence mCoercedLabel = null;
        public int mIcon = 0;
        public boolean mEnabled = false;
        public Set mCandidates = new HashSet();

        public void setLabelRes(int i) {
            this.mLabel = i;
            this.mCandidates.add(LABEL);
        }

        public void setCoercedLabel(CharSequence charSequence) {
            this.mCoercedLabel = charSequence;
            this.mCandidates.add(COERCED_LABEL);
        }

        public void setIconRes(int i) {
            this.mIcon = i;
            this.mCandidates.add(ICON);
        }

        public void setEnabled(boolean z) {
            this.mEnabled = z;
            this.mCandidates.add(ENABLED);
        }

        public int getLabelRes() {
            return this.mLabel;
        }

        public CharSequence getCoercedLabel() {
            return this.mCoercedLabel;
        }

        public int getIconRes() {
            return this.mIcon;
        }

        public boolean getEnabled() {
            return this.mEnabled;
        }

        public boolean hasLabel() {
            return this.mCandidates.contains(LABEL);
        }

        public boolean hasCoercedLabel() {
            return this.mCandidates.contains(COERCED_LABEL);
        }

        public boolean hasIcon() {
            return this.mCandidates.contains(ICON);
        }

        public boolean hasEnabled() {
            return this.mCandidates.contains(ENABLED);
        }
    }

    public static ApplicationReplacement getReplacementForApplicationSalescode(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        ApplicationReplacement applicationReplacement = new ApplicationReplacement();
        if (TextUtils.isEmpty(RuntimeManifestUtils.getSalesCode())) {
            Slog.d("LegacyRuntimeManifestParseUtils", "<application-salescode> No sales code, skip it");
            return null;
        }
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestApplication);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString == null) {
                Slog.d("LegacyRuntimeManifestParseUtils", "<application-salescode> does not specify android:name");
                return null;
            }
            if (!RuntimeManifestUtils.getSalesCode().equals(nonConfigurationString)) {
                return null;
            }
            TypedValue peekValue = obtainAttributes.peekValue(1);
            if (peekValue != null) {
                if (peekValue.resourceId == 0) {
                    applicationReplacement.setCoercedLabel(peekValue.coerceToString());
                    applicationReplacement.setLabelRes(0);
                } else {
                    applicationReplacement.setCoercedLabel(null);
                    applicationReplacement.setLabelRes(peekValue.resourceId);
                }
            }
            int resourceId = obtainAttributes.getResourceId(2, 0);
            if (resourceId != 0) {
                applicationReplacement.setIconRes(resourceId);
            }
            if (obtainAttributes.hasValueOrEmpty(9)) {
                applicationReplacement.setEnabled(obtainAttributes.getBoolean(9, true));
            }
            return applicationReplacement;
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static void modifyParsingPackageWithReplacement(ParsingPackage parsingPackage, ApplicationReplacement applicationReplacement) {
        if (applicationReplacement == null || parsingPackage == null) {
            return;
        }
        if (applicationReplacement.hasLabel()) {
            parsingPackage.setLabelResourceId(applicationReplacement.getLabelRes());
        }
        if (applicationReplacement.hasCoercedLabel()) {
            parsingPackage.setNonLocalizedLabel(applicationReplacement.getCoercedLabel());
        }
        if (applicationReplacement.hasIcon()) {
            parsingPackage.setIconResourceId(applicationReplacement.getIconRes());
        }
        if (applicationReplacement.hasEnabled()) {
            parsingPackage.setEnabled(applicationReplacement.getEnabled());
        }
    }

    public static void parseOverlayComponentAndModify(String str, List list, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput, String str2) {
        ParsedMainComponentImpl parsedMainComponentImpl;
        if (TextUtils.isEmpty(RuntimeManifestUtils.getSalesCode())) {
            Slog.d("LegacyRuntimeManifestParseUtils", str2 + " No sales code, skip it");
            return;
        }
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
        int i = 0;
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(2, 0);
            if (nonConfigurationString == null) {
                Slog.d("LegacyRuntimeManifestParseUtils", str2 + " does not specify android:name");
                return;
            }
            if (!RuntimeManifestUtils.getSalesCode().equals(nonConfigurationString)) {
                Slog.d("LegacyRuntimeManifestParseUtils", "Sales code mismatch");
                return;
            }
            String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(7, 1024);
            if (nonConfigurationString2 == null) {
                Slog.d("LegacyRuntimeManifestParseUtils", str2 + " does not specify android:targetActivity");
                return;
            }
            String buildClassName = ParsingUtils.buildClassName(str, nonConfigurationString2);
            if (buildClassName == null) {
                Slog.d("LegacyRuntimeManifestParseUtils", str2 + "Empty class name in package " + str);
                return;
            }
            int size = ArrayUtils.size(list);
            while (true) {
                if (i >= size) {
                    parsedMainComponentImpl = null;
                    break;
                }
                parsedMainComponentImpl = (ParsedMainComponentImpl) list.get(i);
                if (buildClassName.equals(parsedMainComponentImpl.getName())) {
                    break;
                } else {
                    i++;
                }
            }
            ParsedMainComponentImpl parsedMainComponentImpl2 = parsedMainComponentImpl;
            if (parsedMainComponentImpl2 == null) {
                Slog.d("LegacyRuntimeManifestParseUtils", str2 + " target " + buildClassName + " not found in manifest");
                return;
            }
            if (!parseMainOverlayComponentAndModify(parsedMainComponentImpl2, str2, obtainAttributes, parseInput, 2, 1, 0).isError()) {
                parsedMainComponentImpl2.setEnabled(obtainAttributes.getBoolean(4, parsedMainComponentImpl2.isEnabled()));
                return;
            }
            Slog.d("LegacyRuntimeManifestParseUtils", str2 + " got error while parsing overlay components");
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static ParseResult parseMainOverlayComponentAndModify(ParsedComponentImpl parsedComponentImpl, String str, TypedArray typedArray, ParseInput parseInput, int i, int i2, int i3) {
        if (TextUtils.isEmpty(typedArray.getNonConfigurationString(i, 0))) {
            return parseInput.error(str + " does not specify android:name");
        }
        int resourceId = typedArray.getResourceId(i2, 0);
        if (resourceId != 0) {
            parsedComponentImpl.setIcon(resourceId);
        }
        TypedValue peekValue = typedArray.peekValue(i3);
        if (peekValue != null) {
            parsedComponentImpl.setLabelRes(peekValue.resourceId);
            if (peekValue.resourceId == 0) {
                parsedComponentImpl.setNonLocalizedLabel(peekValue.coerceToString());
            }
        }
        return parseInput.success(parsedComponentImpl);
    }
}
