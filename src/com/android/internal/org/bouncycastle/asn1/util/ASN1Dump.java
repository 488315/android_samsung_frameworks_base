package com.android.internal.org.bouncycastle.asn1.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.org.bouncycastle.asn1.ASN1BMPString;
import com.android.internal.org.bouncycastle.asn1.ASN1BitString;
import com.android.internal.org.bouncycastle.asn1.ASN1Boolean;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.internal.org.bouncycastle.asn1.ASN1External;
import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ASN1GraphicString;
import com.android.internal.org.bouncycastle.asn1.ASN1IA5String;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Null;
import com.android.internal.org.bouncycastle.asn1.ASN1NumericString;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectDescriptor;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1PrintableString;
import com.android.internal.org.bouncycastle.asn1.ASN1RelativeOID;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.ASN1T61String;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.internal.org.bouncycastle.asn1.ASN1UTF8String;
import com.android.internal.org.bouncycastle.asn1.ASN1Util;
import com.android.internal.org.bouncycastle.asn1.ASN1VideotexString;
import com.android.internal.org.bouncycastle.asn1.ASN1VisibleString;
import com.android.internal.org.bouncycastle.asn1.BEROctetString;
import com.android.internal.org.bouncycastle.asn1.BERSequence;
import com.android.internal.org.bouncycastle.asn1.BERSet;
import com.android.internal.org.bouncycastle.asn1.BERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERBitString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERSet;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.DLBitString;
import com.android.internal.org.bouncycastle.util.Strings;
import com.android.internal.org.bouncycastle.util.encoders.Hex;

/* loaded from: classes5.dex */
public class ASN1Dump {
    private static final int SAMPLE_SIZE = 32;
    private static final String TAB = "    ";

    static void _dumpAsString(String str, boolean z, ASN1Primitive aSN1Primitive, StringBuffer stringBuffer) {
        String lineSeparator = Strings.lineSeparator();
        if (aSN1Primitive instanceof ASN1Null) {
            stringBuffer.append(str);
            stringBuffer.append("NULL");
            stringBuffer.append(lineSeparator);
            return;
        }
        int i = 0;
        if (aSN1Primitive instanceof ASN1Sequence) {
            stringBuffer.append(str);
            if (aSN1Primitive instanceof BERSequence) {
                stringBuffer.append("BER Sequence");
            } else if (aSN1Primitive instanceof DERSequence) {
                stringBuffer.append("DER Sequence");
            } else {
                stringBuffer.append("Sequence");
            }
            stringBuffer.append(lineSeparator);
            ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1Primitive;
            String str2 = str + TAB;
            int size = aSN1Sequence.size();
            while (i < size) {
                _dumpAsString(str2, z, aSN1Sequence.getObjectAt(i).toASN1Primitive(), stringBuffer);
                i++;
            }
            return;
        }
        if (aSN1Primitive instanceof ASN1Set) {
            stringBuffer.append(str);
            if (aSN1Primitive instanceof BERSet) {
                stringBuffer.append("BER Set");
            } else if (aSN1Primitive instanceof DERSet) {
                stringBuffer.append("DER Set");
            } else {
                stringBuffer.append("Set");
            }
            stringBuffer.append(lineSeparator);
            ASN1Set aSN1Set = (ASN1Set) aSN1Primitive;
            String str3 = str + TAB;
            int size2 = aSN1Set.size();
            while (i < size2) {
                _dumpAsString(str3, z, aSN1Set.getObjectAt(i).toASN1Primitive(), stringBuffer);
                i++;
            }
            return;
        }
        if (aSN1Primitive instanceof ASN1TaggedObject) {
            stringBuffer.append(str);
            if (aSN1Primitive instanceof BERTaggedObject) {
                stringBuffer.append("BER Tagged ");
            } else if (aSN1Primitive instanceof DERTaggedObject) {
                stringBuffer.append("DER Tagged ");
            } else {
                stringBuffer.append("Tagged ");
            }
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
            stringBuffer.append(ASN1Util.getTagText(aSN1TaggedObject));
            if (!aSN1TaggedObject.isExplicit()) {
                stringBuffer.append(" IMPLICIT ");
            }
            stringBuffer.append(lineSeparator);
            _dumpAsString(str + TAB, z, aSN1TaggedObject.getBaseObject().toASN1Primitive(), stringBuffer);
            return;
        }
        if (aSN1Primitive instanceof ASN1OctetString) {
            ASN1OctetString aSN1OctetString = (ASN1OctetString) aSN1Primitive;
            if (aSN1Primitive instanceof BEROctetString) {
                stringBuffer.append(str + "BER Constructed Octet String[" + aSN1OctetString.getOctets().length + "] ");
            } else {
                stringBuffer.append(str + "DER Octet String[" + aSN1OctetString.getOctets().length + "] ");
            }
            if (z) {
                stringBuffer.append(dumpBinaryDataAsString(str, aSN1OctetString.getOctets()));
                return;
            } else {
                stringBuffer.append(lineSeparator);
                return;
            }
        }
        if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
            stringBuffer.append(str + "ObjectIdentifier(" + ((ASN1ObjectIdentifier) aSN1Primitive).getId() + NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1RelativeOID) {
            stringBuffer.append(str + "RelativeOID(" + ((ASN1RelativeOID) aSN1Primitive).getId() + NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1Boolean) {
            stringBuffer.append(str + "Boolean(" + ((ASN1Boolean) aSN1Primitive).isTrue() + NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1Integer) {
            stringBuffer.append(str + "Integer(" + ((ASN1Integer) aSN1Primitive).getValue() + NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1BitString) {
            ASN1BitString aSN1BitString = (ASN1BitString) aSN1Primitive;
            byte[] bytes = aSN1BitString.getBytes();
            int padBits = aSN1BitString.getPadBits();
            if (aSN1BitString instanceof DERBitString) {
                stringBuffer.append(str + "DER Bit String[" + bytes.length + ", " + padBits + "] ");
            } else if (aSN1BitString instanceof DLBitString) {
                stringBuffer.append(str + "DL Bit String[" + bytes.length + ", " + padBits + "] ");
            } else {
                stringBuffer.append(str + "BER Bit String[" + bytes.length + ", " + padBits + "] ");
            }
            if (z) {
                stringBuffer.append(dumpBinaryDataAsString(str, bytes));
                return;
            } else {
                stringBuffer.append(lineSeparator);
                return;
            }
        }
        if (aSN1Primitive instanceof ASN1IA5String) {
            stringBuffer.append(str + "IA5String(" + ((ASN1IA5String) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1UTF8String) {
            stringBuffer.append(str + "UTF8String(" + ((ASN1UTF8String) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1NumericString) {
            stringBuffer.append(str + "NumericString(" + ((ASN1NumericString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1PrintableString) {
            stringBuffer.append(str + "PrintableString(" + ((ASN1PrintableString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1VisibleString) {
            stringBuffer.append(str + "VisibleString(" + ((ASN1VisibleString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1BMPString) {
            stringBuffer.append(str + "BMPString(" + ((ASN1BMPString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1T61String) {
            stringBuffer.append(str + "T61String(" + ((ASN1T61String) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1GraphicString) {
            stringBuffer.append(str + "GraphicString(" + ((ASN1GraphicString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1VideotexString) {
            stringBuffer.append(str + "VideotexString(" + ((ASN1VideotexString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1UTCTime) {
            stringBuffer.append(str + "UTCTime(" + ((ASN1UTCTime) aSN1Primitive).getTime() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1GeneralizedTime) {
            stringBuffer.append(str + "GeneralizedTime(" + ((ASN1GeneralizedTime) aSN1Primitive).getTime() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1Enumerated) {
            stringBuffer.append(str + "DER Enumerated(" + ((ASN1Enumerated) aSN1Primitive).getValue() + NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1ObjectDescriptor) {
            stringBuffer.append(str + "ObjectDescriptor(" + ((ASN1ObjectDescriptor) aSN1Primitive).getBaseGraphicString().getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof ASN1External) {
            ASN1External aSN1External = (ASN1External) aSN1Primitive;
            stringBuffer.append(str + "External " + lineSeparator);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(TAB);
            String sb2 = sb.toString();
            if (aSN1External.getDirectReference() != null) {
                stringBuffer.append(sb2 + "Direct Reference: " + aSN1External.getDirectReference().getId() + lineSeparator);
            }
            if (aSN1External.getIndirectReference() != null) {
                stringBuffer.append(sb2 + "Indirect Reference: " + aSN1External.getIndirectReference().toString() + lineSeparator);
            }
            if (aSN1External.getDataValueDescriptor() != null) {
                _dumpAsString(sb2, z, aSN1External.getDataValueDescriptor(), stringBuffer);
            }
            stringBuffer.append(sb2 + "Encoding: " + aSN1External.getEncoding() + lineSeparator);
            _dumpAsString(sb2, z, aSN1External.getExternalContent(), stringBuffer);
            return;
        }
        stringBuffer.append(str + aSN1Primitive.toString() + lineSeparator);
    }

    public static String dumpAsString(Object obj) {
        return dumpAsString(obj, false);
    }

    public static String dumpAsString(Object obj, boolean z) {
        ASN1Primitive aSN1Primitive;
        if (obj instanceof ASN1Primitive) {
            aSN1Primitive = (ASN1Primitive) obj;
        } else if (obj instanceof ASN1Encodable) {
            aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
        } else {
            return "unknown object type " + obj.toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        _dumpAsString("", z, aSN1Primitive, stringBuffer);
        return stringBuffer.toString();
    }

    private static String dumpBinaryDataAsString(String str, byte[] bArr) {
        String lineSeparator = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        String str2 = str + TAB;
        stringBuffer.append(lineSeparator);
        for (int i = 0; i < bArr.length; i += 32) {
            if (bArr.length - i > 32) {
                stringBuffer.append(str2);
                stringBuffer.append(Strings.fromByteArray(Hex.encode(bArr, i, 32)));
                stringBuffer.append(TAB);
                stringBuffer.append(calculateAscString(bArr, i, 32));
                stringBuffer.append(lineSeparator);
            } else {
                stringBuffer.append(str2);
                stringBuffer.append(Strings.fromByteArray(Hex.encode(bArr, i, bArr.length - i)));
                for (int length = bArr.length - i; length != 32; length++) {
                    stringBuffer.append("  ");
                }
                stringBuffer.append(TAB);
                stringBuffer.append(calculateAscString(bArr, i, bArr.length - i));
                stringBuffer.append(lineSeparator);
            }
        }
        return stringBuffer.toString();
    }

    private static String calculateAscString(byte[] bArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = i; i3 != i + i2; i3++) {
            byte b = bArr[i3];
            if (b >= 32 && b <= 126) {
                stringBuffer.append((char) b);
            }
        }
        return stringBuffer.toString();
    }
}
