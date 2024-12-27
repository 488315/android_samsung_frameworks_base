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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LegacyRuntimeManifestParseUtils {
    private static final String TAG = "LegacyRuntimeManifestParseUtils";

    public static class ApplicationReplacement {
        private static String LABEL = "label";
        private static String COERCED_LABEL = "coerced_label";
        private static String ICON = "icon";
        private static String ENABLED = "enabled";
        private int mLabel = 0;
        private CharSequence mCoercedLabel = null;
        private int mIcon = 0;
        private boolean mEnabled = false;
        private Set<String> mCandidates = new HashSet();

        public void setLabelRes(int label) {
            this.mLabel = label;
            this.mCandidates.add(LABEL);
        }

        public void setCoercedLabel(CharSequence coercedLabel) {
            this.mCoercedLabel = coercedLabel;
            this.mCandidates.add(COERCED_LABEL);
        }

        public void setIconRes(int icon) {
            this.mIcon = icon;
            this.mCandidates.add(ICON);
        }

        public void setEnabled(boolean enabled) {
            this.mEnabled = enabled;
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

    public static ApplicationReplacement getReplacementForApplicationSalescode(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        ApplicationReplacement replacement = new ApplicationReplacement();
        if (TextUtils.isEmpty(RuntimeManifestUtils.getSalesCode())) {
            Slog.d(TAG, "<application-salescode> No sales code, skip it");
            return null;
        }
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestApplication);
        try {
            String name = sa.getNonConfigurationString(3, 0);
            if (name == null) {
                Slog.d(TAG, "<application-salescode> does not specify android:name");
                return null;
            }
            if (!RuntimeManifestUtils.getSalesCode().equals(name)) {
                return null;
            }
            TypedValue labelValue = sa.peekValue(1);
            if (labelValue != null) {
                if (labelValue.resourceId == 0) {
                    replacement.setCoercedLabel(labelValue.coerceToString());
                    replacement.setLabelRes(0);
                } else {
                    replacement.setCoercedLabel(null);
                    replacement.setLabelRes(labelValue.resourceId);
                }
            }
            int iconValue = sa.getResourceId(2, 0);
            if (iconValue != 0) {
                replacement.setIconRes(iconValue);
            }
            if (sa.hasValueOrEmpty(9)) {
                boolean enabled = sa.getBoolean(9, true);
                replacement.setEnabled(enabled);
            }
            return replacement;
        } finally {
            sa.recycle();
        }
    }

    public static void modifyParsingPackageWithReplacement(
            ParsingPackage pkg, ApplicationReplacement replacement) {
        if (replacement == null || pkg == null) {
            return;
        }
        if (replacement.hasLabel()) {
            pkg.setLabelResourceId(replacement.getLabelRes());
        }
        if (replacement.hasCoercedLabel()) {
            pkg.setNonLocalizedLabel(replacement.getCoercedLabel());
        }
        if (replacement.hasIcon()) {
            pkg.setIconResourceId(replacement.getIconRes());
        }
        if (replacement.hasEnabled()) {
            pkg.setEnabled(replacement.getEnabled());
        }
    }

    public static <Component extends ParsedMainComponent> void parseOverlayComponentAndModify(
            String packageName,
            List<Component> components,
            Resources res,
            XmlResourceParser parser,
            ParseInput input,
            String tag) {
        ParsedMainComponentImpl target;
        if (TextUtils.isEmpty(RuntimeManifestUtils.getSalesCode())) {
            Slog.d(TAG, tag + " No sales code, skip it");
            return;
        }
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestActivityAlias);
        try {
            String name = sa.getNonConfigurationString(2, 0);
            if (name == null) {
                Slog.d(TAG, tag + " does not specify android:name");
                return;
            }
            if (!RuntimeManifestUtils.getSalesCode().equals(name)) {
                Slog.d(TAG, "Sales code mismatch");
                return;
            }
            String targetName = sa.getNonConfigurationString(7, 1024);
            if (targetName == null) {
                Slog.d(TAG, tag + " does not specify android:targetActivity");
                return;
            }
            String targetName2 = ParsingUtils.buildClassName(packageName, targetName);
            if (targetName2 == null) {
                Slog.d(TAG, tag + "Empty class name in package " + packageName);
                return;
            }
            int size = ArrayUtils.size(components);
            int i = 0;
            while (true) {
                if (i >= size) {
                    target = null;
                    break;
                }
                ParsedMainComponentImpl t = (ParsedMainComponentImpl) components.get(i);
                if (targetName2.equals(t.getName())) {
                    target = t;
                    break;
                }
                i++;
            }
            if (target == null) {
                Slog.d(TAG, tag + " target " + targetName2 + " not found in manifest");
                return;
            }
            ParsedMainComponentImpl target2 = target;
            ParseResult<ParsedMainComponentImpl> result =
                    parseMainOverlayComponentAndModify(target, tag, sa, input, 2, 1, 0);
            if (result.isError()) {
                Slog.d(TAG, tag + " got error while parsing overlay components");
            } else {
                target2.setEnabled(sa.getBoolean(4, target2.isEnabled()));
            }
        } finally {
            sa.recycle();
        }
    }

    static <Component extends ParsedComponentImpl>
            ParseResult<Component> parseMainOverlayComponentAndModify(
                    Component component,
                    String tag,
                    TypedArray array,
                    ParseInput input,
                    int nameAttr,
                    int iconAttr,
                    int labelAttr) {
        String name = array.getNonConfigurationString(nameAttr, 0);
        if (TextUtils.isEmpty(name)) {
            return input.error(tag + " does not specify android:name");
        }
        int iconResId = array.getResourceId(iconAttr, 0);
        if (iconResId != 0) {
            component.setIcon(iconResId);
        }
        TypedValue v = array.peekValue(labelAttr);
        if (v != null) {
            component.setLabelRes(v.resourceId);
            if (v.resourceId == 0) {
                component.setNonLocalizedLabel(v.coerceToString());
            }
        }
        return input.success(component);
    }
}
