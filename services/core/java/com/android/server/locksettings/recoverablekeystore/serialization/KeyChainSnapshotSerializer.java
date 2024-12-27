package com.android.server.locksettings.recoverablekeystore.serialization;

import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.KeyDerivationParams;
import android.security.keystore.recovery.WrappedApplicationKey;
import android.util.Base64;
import android.util.Xml;

import com.android.modules.utils.TypedXmlSerializer;

import java.io.OutputStream;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class KeyChainSnapshotSerializer {
    public static void serialize(KeyChainSnapshot keyChainSnapshot, OutputStream outputStream) {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((String) null, (Boolean) null);
        resolveSerializer.startTag((String) null, "keyChainSnapshot");
        writePropertyTag(
                resolveSerializer, "snapshotVersion", keyChainSnapshot.getSnapshotVersion());
        writePropertyTag(resolveSerializer, "maxAttempts", keyChainSnapshot.getMaxAttempts());
        writePropertyTag(resolveSerializer, "counterId", keyChainSnapshot.getCounterId());
        writePropertyTag(
                keyChainSnapshot.getEncryptedRecoveryKeyBlob(),
                "recoveryKeyMaterial",
                resolveSerializer);
        writePropertyTag(keyChainSnapshot.getServerParams(), "serverParams", resolveSerializer);
        writePropertyTag(
                keyChainSnapshot.getTrustedHardwareCertPath().getEncoded("PkiPath"),
                "thmCertPath",
                resolveSerializer);
        List<KeyChainProtectionParams> keyChainProtectionParams =
                keyChainSnapshot.getKeyChainProtectionParams();
        resolveSerializer.startTag((String) null, "keyChainProtectionParamsList");
        for (KeyChainProtectionParams keyChainProtectionParams2 : keyChainProtectionParams) {
            resolveSerializer.startTag((String) null, "keyChainProtectionParams");
            writePropertyTag(
                    resolveSerializer,
                    "userSecretType",
                    keyChainProtectionParams2.getUserSecretType());
            writePropertyTag(
                    resolveSerializer,
                    "lockScreenUiType",
                    keyChainProtectionParams2.getLockScreenUiFormat());
            KeyDerivationParams keyDerivationParams =
                    keyChainProtectionParams2.getKeyDerivationParams();
            resolveSerializer.startTag((String) null, "keyDerivationParams");
            writePropertyTag(resolveSerializer, "algorithm", keyDerivationParams.getAlgorithm());
            writePropertyTag(keyDerivationParams.getSalt(), "salt", resolveSerializer);
            writePropertyTag(
                    resolveSerializer,
                    "memoryDifficulty",
                    keyDerivationParams.getMemoryDifficulty());
            resolveSerializer.endTag((String) null, "keyDerivationParams");
            resolveSerializer.endTag((String) null, "keyChainProtectionParams");
        }
        resolveSerializer.endTag((String) null, "keyChainProtectionParamsList");
        List<WrappedApplicationKey> wrappedApplicationKeys =
                keyChainSnapshot.getWrappedApplicationKeys();
        resolveSerializer.startTag((String) null, "applicationKeysList");
        for (WrappedApplicationKey wrappedApplicationKey : wrappedApplicationKeys) {
            resolveSerializer.startTag((String) null, "applicationKey");
            String alias = wrappedApplicationKey.getAlias();
            resolveSerializer.startTag((String) null, "alias");
            resolveSerializer.text(alias);
            resolveSerializer.endTag((String) null, "alias");
            writePropertyTag(
                    wrappedApplicationKey.getEncryptedKeyMaterial(),
                    "keyMaterial",
                    resolveSerializer);
            writePropertyTag(wrappedApplicationKey.getMetadata(), "keyMetadata", resolveSerializer);
            resolveSerializer.endTag((String) null, "applicationKey");
        }
        resolveSerializer.endTag((String) null, "applicationKeysList");
        resolveSerializer.endTag((String) null, "keyChainSnapshot");
        resolveSerializer.endDocument();
    }

    public static void writePropertyTag(TypedXmlSerializer typedXmlSerializer, String str, long j) {
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.text(Long.toString(j));
        typedXmlSerializer.endTag((String) null, str);
    }

    public static void writePropertyTag(
            byte[] bArr, String str, TypedXmlSerializer typedXmlSerializer) {
        if (bArr == null) {
            return;
        }
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.text(Base64.encodeToString(bArr, 0));
        typedXmlSerializer.endTag((String) null, str);
    }
}
