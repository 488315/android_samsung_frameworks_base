package com.android.server.pm.pkg;

import android.content.pm.SuspendDialogInfo;
import android.os.BaseBundle;
import android.os.PersistableBundle;
import android.util.Slog;

import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SuspendParams {
    public final PersistableBundle mAppExtras;
    public final SuspendDialogInfo mDialogInfo;
    public final PersistableBundle mLauncherExtras;
    public final boolean mQuarantined;

    public SuspendParams(
            SuspendDialogInfo suspendDialogInfo,
            PersistableBundle persistableBundle,
            PersistableBundle persistableBundle2,
            boolean z) {
        this.mDialogInfo = suspendDialogInfo;
        this.mAppExtras = persistableBundle;
        this.mLauncherExtras = persistableBundle2;
        this.mQuarantined = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuspendParams)) {
            return false;
        }
        SuspendParams suspendParams = (SuspendParams) obj;
        return Objects.equals(this.mDialogInfo, suspendParams.mDialogInfo)
                && BaseBundle.kindofEquals(this.mAppExtras, suspendParams.mAppExtras)
                && BaseBundle.kindofEquals(this.mLauncherExtras, suspendParams.mLauncherExtras)
                && this.mQuarantined == suspendParams.mQuarantined;
    }

    public final int hashCode() {
        int hashCode = Objects.hashCode(this.mDialogInfo) * 31;
        PersistableBundle persistableBundle = this.mAppExtras;
        int size = (hashCode + (persistableBundle != null ? persistableBundle.size() : 0)) * 31;
        PersistableBundle persistableBundle2 = this.mLauncherExtras;
        return Boolean.hashCode(this.mQuarantined)
                + ((size + (persistableBundle2 != null ? persistableBundle2.size() : 0)) * 31);
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.attributeBoolean((String) null, "quarantined", this.mQuarantined);
        if (this.mDialogInfo != null) {
            typedXmlSerializer.startTag((String) null, "dialog-info");
            this.mDialogInfo.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "dialog-info");
        }
        if (this.mAppExtras != null) {
            typedXmlSerializer.startTag((String) null, "app-extras");
            try {
                this.mAppExtras.saveToXml(typedXmlSerializer);
            } catch (XmlPullParserException e) {
                Slog.e(
                        "FrameworkPackageUserState",
                        "Exception while trying to write appExtras. Will be lost on reboot",
                        e);
            }
            typedXmlSerializer.endTag((String) null, "app-extras");
        }
        if (this.mLauncherExtras != null) {
            typedXmlSerializer.startTag((String) null, "launcher-extras");
            try {
                this.mLauncherExtras.saveToXml(typedXmlSerializer);
            } catch (XmlPullParserException e2) {
                Slog.e(
                        "FrameworkPackageUserState",
                        "Exception while trying to write launcherExtras. Will be lost on reboot",
                        e2);
            }
            typedXmlSerializer.endTag((String) null, "launcher-extras");
        }
    }
}
