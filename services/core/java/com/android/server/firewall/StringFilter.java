package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.PatternMatcher;

import java.util.regex.Pattern;

public abstract class StringFilter implements Filter {
    public final AnonymousClass1 mValueProvider;
    public static final AnonymousClass1 COMPONENT = new AnonymousClass1("component", 0);
    public static final AnonymousClass1 COMPONENT_NAME = new AnonymousClass1("component-name", 2);
    public static final AnonymousClass1 COMPONENT_PACKAGE =
            new AnonymousClass1("component-package", 3);
    public static final AnonymousClass1 ACTION = new AnonymousClass1("action", 4);
    public static final AnonymousClass1 DATA = new AnonymousClass1("data", 5);
    public static final AnonymousClass1 MIME_TYPE = new AnonymousClass1("mime-type", 6);
    public static final AnonymousClass1 SCHEME = new AnonymousClass1("scheme", 7);
    public static final AnonymousClass1 SSP = new AnonymousClass1("scheme-specific-part", 8);
    public static final AnonymousClass1 HOST = new AnonymousClass1("host", 9);
    public static final AnonymousClass1 PATH = new AnonymousClass1("path", 1);

    /* renamed from: com.android.server.firewall.StringFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass1(String str, int i) {
            super(str);
            this.$r8$classId = i;
        }

        @Override // com.android.server.firewall.FilterFactory
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser r8) {
            /*
                r7 = this;
                com.android.server.firewall.StringFilter$1 r0 = com.android.server.firewall.StringFilter.COMPONENT
                r0 = 0
                r1 = 0
                r3 = r0
                r2 = r1
            L6:
                int r4 = r8.getAttributeCount()
                if (r2 >= r4) goto Lb8
                java.lang.String r4 = r8.getAttributeName(r2)
                char r5 = r4.charAt(r1)
                r6 = 99
                if (r5 == r6) goto L93
                r6 = 101(0x65, float:1.42E-43)
                if (r5 == r6) goto L7f
                r6 = 105(0x69, float:1.47E-43)
                if (r5 == r6) goto L6b
                r6 = 112(0x70, float:1.57E-43)
                if (r5 == r6) goto L57
                r6 = 114(0x72, float:1.6E-43)
                if (r5 == r6) goto L43
                r6 = 115(0x73, float:1.61E-43)
                if (r5 == r6) goto L2f
            L2c:
                r4 = r0
                goto La6
            L2f:
                java.lang.String r5 = "startsWith"
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L39
                goto L2c
            L39:
                com.android.server.firewall.StringFilter$StartsWithFilter r4 = new com.android.server.firewall.StringFilter$StartsWithFilter
                java.lang.String r5 = r8.getAttributeValue(r2)
                r4.<init>(r7, r5)
                goto La6
            L43:
                java.lang.String r5 = "regex"
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L4d
                goto L2c
            L4d:
                com.android.server.firewall.StringFilter$RegexFilter r4 = new com.android.server.firewall.StringFilter$RegexFilter
                java.lang.String r5 = r8.getAttributeValue(r2)
                r4.<init>(r7, r5)
                goto La6
            L57:
                java.lang.String r5 = "pattern"
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L61
                goto L2c
            L61:
                com.android.server.firewall.StringFilter$PatternStringFilter r4 = new com.android.server.firewall.StringFilter$PatternStringFilter
                java.lang.String r5 = r8.getAttributeValue(r2)
                r4.<init>(r7, r5)
                goto La6
            L6b:
                java.lang.String r5 = "isNull"
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L75
                goto L2c
            L75:
                com.android.server.firewall.StringFilter$IsNullFilter r4 = new com.android.server.firewall.StringFilter$IsNullFilter
                java.lang.String r5 = r8.getAttributeValue(r2)
                r4.<init>(r7, r5)
                goto La6
            L7f:
                java.lang.String r5 = "equals"
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L89
                goto L2c
            L89:
                com.android.server.firewall.StringFilter$EqualsFilter r4 = new com.android.server.firewall.StringFilter$EqualsFilter
                java.lang.String r5 = r8.getAttributeValue(r2)
                r4.<init>(r7, r5)
                goto La6
            L93:
                java.lang.String r5 = "contains"
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L9d
                goto L2c
            L9d:
                com.android.server.firewall.StringFilter$ContainsFilter r4 = new com.android.server.firewall.StringFilter$ContainsFilter
                java.lang.String r5 = r8.getAttributeValue(r2)
                r4.<init>(r7, r5)
            La6:
                if (r4 == 0) goto Lb4
                if (r3 != 0) goto Lac
                r3 = r4
                goto Lb4
            Lac:
                org.xmlpull.v1.XmlPullParserException r7 = new org.xmlpull.v1.XmlPullParserException
                java.lang.String r8 = "Multiple string filter attributes found"
                r7.<init>(r8)
                throw r7
            Lb4:
                int r2 = r2 + 1
                goto L6
            Lb8:
                if (r3 != 0) goto Lbf
                com.android.server.firewall.StringFilter$IsNullFilter r3 = new com.android.server.firewall.StringFilter$IsNullFilter
                r3.<init>(r7)
            Lbf:
                return r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.firewall.StringFilter.AnonymousClass1.newFilter(org.xmlpull.v1.XmlPullParser):com.android.server.firewall.Filter");
        }
    }

    public final class ContainsFilter extends StringFilter {
        public final String mFilterValue;

        public ContainsFilter(AnonymousClass1 anonymousClass1, String str) {
            super(anonymousClass1);
            this.mFilterValue = str;
        }

        @Override // com.android.server.firewall.StringFilter
        public final boolean matchesValue(String str) {
            return str != null && str.contains(this.mFilterValue);
        }
    }

    public final class EqualsFilter extends StringFilter {
        public final String mFilterValue;

        public EqualsFilter(AnonymousClass1 anonymousClass1, String str) {
            super(anonymousClass1);
            this.mFilterValue = str;
        }

        @Override // com.android.server.firewall.StringFilter
        public final boolean matchesValue(String str) {
            return str != null && str.equals(this.mFilterValue);
        }
    }

    public final class IsNullFilter extends StringFilter {
        public final boolean mIsNull;

        public IsNullFilter(AnonymousClass1 anonymousClass1) {
            super(anonymousClass1);
            this.mIsNull = false;
        }

        public IsNullFilter(AnonymousClass1 anonymousClass1, String str) {
            super(anonymousClass1);
            this.mIsNull = Boolean.parseBoolean(str);
        }

        @Override // com.android.server.firewall.StringFilter
        public final boolean matchesValue(String str) {
            return (str == null) == this.mIsNull;
        }
    }

    public final class PatternStringFilter extends StringFilter {
        public final PatternMatcher mPattern;

        public PatternStringFilter(AnonymousClass1 anonymousClass1, String str) {
            super(anonymousClass1);
            this.mPattern = new PatternMatcher(str, 2);
        }

        @Override // com.android.server.firewall.StringFilter
        public final boolean matchesValue(String str) {
            return str != null && this.mPattern.match(str);
        }
    }

    public final class RegexFilter extends StringFilter {
        public final Pattern mPattern;

        public RegexFilter(AnonymousClass1 anonymousClass1, String str) {
            super(anonymousClass1);
            this.mPattern = Pattern.compile(str);
        }

        @Override // com.android.server.firewall.StringFilter
        public final boolean matchesValue(String str) {
            return str != null && this.mPattern.matcher(str).matches();
        }
    }

    public final class StartsWithFilter extends StringFilter {
        public final String mFilterValue;

        public StartsWithFilter(AnonymousClass1 anonymousClass1, String str) {
            super(anonymousClass1);
            this.mFilterValue = str;
        }

        @Override // com.android.server.firewall.StringFilter
        public final boolean matchesValue(String str) {
            return str != null && str.startsWith(this.mFilterValue);
        }
    }

    public StringFilter(AnonymousClass1 anonymousClass1) {
        this.mValueProvider = anonymousClass1;
    }

    @Override // com.android.server.firewall.Filter
    public final boolean matches(
            IntentFirewall intentFirewall,
            ComponentName componentName,
            Intent intent,
            int i,
            int i2,
            String str,
            int i3) {
        String flattenToString;
        switch (this.mValueProvider.$r8$classId) {
            case 0:
                if (componentName != null) {
                    flattenToString = componentName.flattenToString();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            case 1:
                Uri data = intent.getData();
                if (data != null) {
                    flattenToString = data.getPath();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            case 2:
                if (componentName != null) {
                    flattenToString = componentName.getClassName();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            case 3:
                if (componentName != null) {
                    flattenToString = componentName.getPackageName();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            case 4:
                str = intent.getAction();
                break;
            case 5:
                Uri data2 = intent.getData();
                if (data2 != null) {
                    flattenToString = data2.toString();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            case 6:
                break;
            case 7:
                Uri data3 = intent.getData();
                if (data3 != null) {
                    flattenToString = data3.getScheme();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            case 8:
                Uri data4 = intent.getData();
                if (data4 != null) {
                    flattenToString = data4.getSchemeSpecificPart();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
            default:
                Uri data5 = intent.getData();
                if (data5 != null) {
                    flattenToString = data5.getHost();
                    str = flattenToString;
                    break;
                }
                flattenToString = null;
                str = flattenToString;
        }
        return matchesValue(str);
    }

    public abstract boolean matchesValue(String str);
}
