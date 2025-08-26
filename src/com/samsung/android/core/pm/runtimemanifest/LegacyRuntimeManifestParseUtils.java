package com.samsung.android.core.pm.runtimemanifest;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.internal.pm.pkg.component.ParsedComponentImpl;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedMainComponentImpl;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class LegacyRuntimeManifestParseUtils {
    private static final String TAG = "LegacyRuntimeManifestParseUtils";

    public static class ApplicationReplacement {
        private static String COERCED_LABEL = "coerced_label";
        private static String ENABLED = "enabled";
        private static String ICON = "icon";
        private static String LABEL = "label";
        private int mLabel = 0;
        private CharSequence mCoercedLabel = null;
        private int mIcon = 0;
        private boolean mEnabled = false;
        private Set<String> mCandidates = new HashSet();

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

    public static ApplicationReplacement getReplacementForApplicationSalescode(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ApplicationReplacement applicationReplacement = new ApplicationReplacement();
        if (TextUtils.isEmpty(RuntimeManifestUtils.getSalesCode())) {
            Slog.d(TAG, "<application-salescode> No sales code, skip it");
            return null;
        }
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestApplication);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString == null) {
                Slog.d(TAG, "<application-salescode> does not specify android:name");
                return null;
            }
            if (!RuntimeManifestUtils.getSalesCode().equals(nonConfigurationString)) {
                return null;
            }
            TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(1);
            if (typedValuePeekValue != null) {
                if (typedValuePeekValue.resourceId == 0) {
                    applicationReplacement.setCoercedLabel(typedValuePeekValue.coerceToString());
                    applicationReplacement.setLabelRes(0);
                } else {
                    applicationReplacement.setCoercedLabel(null);
                    applicationReplacement.setLabelRes(typedValuePeekValue.resourceId);
                }
            }
            int resourceId = typedArrayObtainAttributes.getResourceId(2, 0);
            if (resourceId != 0) {
                applicationReplacement.setIconRes(resourceId);
            }
            if (typedArrayObtainAttributes.hasValueOrEmpty(9)) {
                applicationReplacement.setEnabled(typedArrayObtainAttributes.getBoolean(9, true));
            }
            return applicationReplacement;
        } finally {
            typedArrayObtainAttributes.recycle();
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

    public static <Component extends ParsedMainComponent> void parseOverlayComponentAndModify(String str, List<Component> list, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput, String str2) {
        ParsedMainComponentImpl parsedMainComponentImpl;
        if (TextUtils.isEmpty(RuntimeManifestUtils.getSalesCode())) {
            Slog.d(TAG, str2 + " No sales code, skip it");
            return;
        }
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
        int i = 0;
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(2, 0);
            if (nonConfigurationString == null) {
                Slog.d(TAG, str2 + " does not specify android:name");
                return;
            }
            if (!RuntimeManifestUtils.getSalesCode().equals(nonConfigurationString)) {
                Slog.d(TAG, "Sales code mismatch");
                return;
            }
            String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(7, 1024);
            if (nonConfigurationString2 == null) {
                Slog.d(TAG, str2 + " does not specify android:targetActivity");
                return;
            }
            String strBuildClassName = ParsingUtils.buildClassName(str, nonConfigurationString2);
            if (strBuildClassName == null) {
                Slog.d(TAG, str2 + "Empty class name in package " + str);
                return;
            }
            int size = ArrayUtils.size(list);
            while (true) {
                if (i >= size) {
                    parsedMainComponentImpl = null;
                    break;
                }
                parsedMainComponentImpl = (ParsedMainComponentImpl) list.get(i);
                if (strBuildClassName.equals(parsedMainComponentImpl.getName())) {
                    break;
                } else {
                    i++;
                }
            }
            ParsedMainComponentImpl parsedMainComponentImpl2 = parsedMainComponentImpl;
            if (parsedMainComponentImpl2 == null) {
                Slog.d(TAG, str2 + " target " + strBuildClassName + " not found in manifest");
                return;
            }
            if (!parseMainOverlayComponentAndModify(parsedMainComponentImpl2, str2, typedArrayObtainAttributes, parseInput, 2, 1, 0).isError()) {
                parsedMainComponentImpl2.setEnabled(typedArrayObtainAttributes.getBoolean(4, parsedMainComponentImpl2.isEnabled()));
                return;
            }
            Slog.d(TAG, str2 + " got error while parsing overlay components");
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    static <Component extends ParsedComponentImpl> ParseResult<Component> parseMainOverlayComponentAndModify(Component component, String str, TypedArray typedArray, ParseInput parseInput, int i, int i2, int i3) {
        if (TextUtils.isEmpty(typedArray.getNonConfigurationString(i, 0))) {
            return parseInput.error(str + " does not specify android:name");
        }
        int resourceId = typedArray.getResourceId(i2, 0);
        if (resourceId != 0) {
            component.setIcon(resourceId);
        }
        TypedValue typedValuePeekValue = typedArray.peekValue(i3);
        if (typedValuePeekValue != null) {
            component.setLabelRes(typedValuePeekValue.resourceId);
            if (typedValuePeekValue.resourceId == 0) {
                component.setNonLocalizedLabel(typedValuePeekValue.coerceToString());
            }
        }
        return parseInput.success(component);
    }
}
