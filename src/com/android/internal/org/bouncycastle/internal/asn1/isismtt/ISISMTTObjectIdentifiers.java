package com.android.internal.org.bouncycastle.internal.asn1.isismtt;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes5.dex */
public interface ISISMTTObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_isismtt;
    public static final ASN1ObjectIdentifier id_isismtt_at;
    public static final ASN1ObjectIdentifier id_isismtt_at_PKReference;
    public static final ASN1ObjectIdentifier id_isismtt_at_additionalInformation;
    public static final ASN1ObjectIdentifier id_isismtt_at_admission;
    public static final ASN1ObjectIdentifier id_isismtt_at_certHash;
    public static final ASN1ObjectIdentifier id_isismtt_at_certInDirSince;
    public static final ASN1ObjectIdentifier id_isismtt_at_dateOfCertGen;
    public static final ASN1ObjectIdentifier id_isismtt_at_declarationOfMajority;
    public static final ASN1ObjectIdentifier id_isismtt_at_iCCSN;
    public static final ASN1ObjectIdentifier id_isismtt_at_liabilityLimitationFlag;
    public static final ASN1ObjectIdentifier id_isismtt_at_monetaryLimit;
    public static final ASN1ObjectIdentifier id_isismtt_at_nameAtBirth;
    public static final ASN1ObjectIdentifier id_isismtt_at_namingAuthorities;
    public static final ASN1ObjectIdentifier id_isismtt_at_procuration;
    public static final ASN1ObjectIdentifier id_isismtt_at_requestedCertificate;
    public static final ASN1ObjectIdentifier id_isismtt_at_restriction;
    public static final ASN1ObjectIdentifier id_isismtt_at_retrieveIfAllowed;
    public static final ASN1ObjectIdentifier id_isismtt_cp;
    public static final ASN1ObjectIdentifier id_isismtt_cp_accredited;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.36.8");
        id_isismtt = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch = aSN1ObjectIdentifier.branch("1");
        id_isismtt_cp = aSN1ObjectIdentifierBranch;
        id_isismtt_cp_accredited = aSN1ObjectIdentifierBranch.branch("1");
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch2 = aSN1ObjectIdentifier.branch("3");
        id_isismtt_at = aSN1ObjectIdentifierBranch2;
        id_isismtt_at_dateOfCertGen = aSN1ObjectIdentifierBranch2.branch("1");
        id_isismtt_at_procuration = aSN1ObjectIdentifierBranch2.branch("2");
        id_isismtt_at_admission = aSN1ObjectIdentifierBranch2.branch("3");
        id_isismtt_at_monetaryLimit = aSN1ObjectIdentifierBranch2.branch("4");
        id_isismtt_at_declarationOfMajority = aSN1ObjectIdentifierBranch2.branch("5");
        id_isismtt_at_iCCSN = aSN1ObjectIdentifierBranch2.branch("6");
        id_isismtt_at_PKReference = aSN1ObjectIdentifierBranch2.branch("7");
        id_isismtt_at_restriction = aSN1ObjectIdentifierBranch2.branch("8");
        id_isismtt_at_retrieveIfAllowed = aSN1ObjectIdentifierBranch2.branch("9");
        id_isismtt_at_requestedCertificate = aSN1ObjectIdentifierBranch2.branch("10");
        id_isismtt_at_namingAuthorities = aSN1ObjectIdentifierBranch2.branch("11");
        id_isismtt_at_certInDirSince = aSN1ObjectIdentifierBranch2.branch(AudioParameter.VALUE_VM_CSD_500_WARNING);
        id_isismtt_at_certHash = aSN1ObjectIdentifierBranch2.branch("13");
        id_isismtt_at_nameAtBirth = aSN1ObjectIdentifierBranch2.branch("14");
        id_isismtt_at_additionalInformation = aSN1ObjectIdentifierBranch2.branch("15");
        id_isismtt_at_liabilityLimitationFlag = new ASN1ObjectIdentifier("0.2.262.1.10.12.0");
    }
}
