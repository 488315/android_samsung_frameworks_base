package com.android.server.companion.association;

import android.companion.AssociationInfo;
import android.util.Xml;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlSerializer;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AssociationDiskStore$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ Associations f$0;

    public final void acceptOrThrow(Object obj) {
        Associations associations = this.f$0;
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer((FileOutputStream) obj);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        XmlUtils.writeIntAttribute(resolveSerializer, "persistence-version", 1);
        XmlSerializer startTag = resolveSerializer.startTag(null, "associations");
        for (AssociationInfo associationInfo : associations.mAssociations) {
            XmlSerializer startTag2 = startTag.startTag(null, "association");
            XmlUtils.writeIntAttribute(startTag2, "id", associationInfo.getId());
            XmlUtils.writeStringAttribute(startTag2, "profile", associationInfo.getDeviceProfile());
            XmlUtils.writeStringAttribute(startTag2, "package", associationInfo.getPackageName());
            XmlUtils.writeStringAttribute(startTag2, "tag", associationInfo.getTag());
            XmlUtils.writeStringAttribute(
                    startTag2, "mac_address", associationInfo.getDeviceMacAddressAsString());
            XmlUtils.writeStringAttribute(
                    startTag2, "display_name", associationInfo.getDisplayName());
            XmlUtils.writeBooleanAttribute(
                    startTag2, "self_managed", associationInfo.isSelfManaged());
            XmlUtils.writeBooleanAttribute(
                    startTag2, "notify_device_nearby", associationInfo.isNotifyOnDeviceNearby());
            XmlUtils.writeBooleanAttribute(startTag2, "revoked", associationInfo.isRevoked());
            XmlUtils.writeBooleanAttribute(startTag2, "pending", associationInfo.isPending());
            XmlUtils.writeLongAttribute(
                    startTag2, "time_approved", associationInfo.getTimeApprovedMs());
            XmlUtils.writeLongAttribute(
                    startTag2, "last_time_connected", associationInfo.getLastTimeConnectedMs());
            XmlUtils.writeIntAttribute(
                    startTag2, "system_data_sync_flags", associationInfo.getSystemDataSyncFlags());
            startTag2.endTag(null, "association");
        }
        XmlUtils.writeIntAttribute(startTag, "max-id", associations.mMaxId);
        startTag.endTag(null, "associations");
        resolveSerializer.endTag((String) null, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        resolveSerializer.endDocument();
    }
}
