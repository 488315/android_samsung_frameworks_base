package com.android.server.selinux;

import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Slog;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SelinuxAuditLogBuilder {
    static final String CONFIG_SELINUX_AUDIT_DOMAIN = "selinux_audit_domain";
    public static final Matcher NO_OP_MATCHER = Pattern.compile("no-op^").matcher("");
    public final SelinuxAuditLog mAuditLog;
    final Matcher mPathMatcher;
    final Matcher mScontextMatcher;
    final Matcher mTcontextMatcher;
    public Iterator mTokens;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SelinuxAuditLog {
        public boolean mGranted;
        public String mPath;
        public String[] mPermissions;
        public boolean mPermissive;
        public int[] mSCategories;
        public String mSType;
        public int[] mTCategories;
        public String mTClass;
        public String mTType;
    }

    public SelinuxAuditLogBuilder() {
        Matcher matcher;
        Matcher matcher2;
        SelinuxAuditLog selinuxAuditLog = new SelinuxAuditLog();
        selinuxAuditLog.mGranted = false;
        selinuxAuditLog.mPermissions = null;
        selinuxAuditLog.mSType = null;
        selinuxAuditLog.mSCategories = null;
        selinuxAuditLog.mTType = null;
        selinuxAuditLog.mTCategories = null;
        selinuxAuditLog.mTClass = null;
        selinuxAuditLog.mPath = null;
        selinuxAuditLog.mPermissive = false;
        this.mAuditLog = selinuxAuditLog;
        Matcher matcher3 = NO_OP_MATCHER;
        try {
            matcher =
                    Pattern.compile(
                                    TextUtils.formatSimple(
                                            "u:r:(?<stype>%s):s0(:c)?(?<scategories>((,c)?\\d+)+)*",
                                            new Object[] {
                                                DeviceConfig.getString(
                                                        "adservices",
                                                        CONFIG_SELINUX_AUDIT_DOMAIN,
                                                        "no_match^")
                                            }))
                            .matcher("");
            try {
                matcher2 =
                        Pattern.compile(
                                        "u:object_r:(?<ttype>\\w+):s0(:c)?(?<tcategories>((,c)?\\d+)+)*")
                                .matcher("");
                try {
                    matcher3 = Pattern.compile("\"(?<path>/\\w+(/\\w+)?)(/\\w+)*\"").matcher("");
                } catch (PatternSyntaxException e) {
                    e = e;
                    Slog.e(
                            "SelinuxAuditLogs",
                            "Invalid pattern, setting every matcher to no-op.",
                            e);
                    this.mScontextMatcher = matcher;
                    this.mTcontextMatcher = matcher2;
                    this.mPathMatcher = matcher3;
                }
            } catch (PatternSyntaxException e2) {
                e = e2;
                matcher2 = matcher3;
            }
        } catch (PatternSyntaxException e3) {
            e = e3;
            matcher = matcher3;
            matcher2 = matcher;
        }
        this.mScontextMatcher = matcher;
        this.mTcontextMatcher = matcher2;
        this.mPathMatcher = matcher3;
    }

    public final boolean nextTokenMatches(Matcher matcher) {
        return this.mTokens.hasNext()
                && matcher.reset((CharSequence) this.mTokens.next()).matches();
    }
}
