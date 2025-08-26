package com.samsung.android.knox.net.firewall;

import android.text.TextUtils;
import android.util.Patterns;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class FirewallRuleValidator {
    public static final String ADDRESS = "address";
    public static final String APP_IDENTITY = "app identity";
    public static final String DIRECTION = "direction";
    public static final Pattern INTERFACE_REGEX = Pattern.compile("[a-z_]{2,}([0-9]*|\\+?)$");
    public static final String NETWORK_INTERFACE = "network interface";
    public static final String PARAMETERS = "Parameter(s): ";
    public static final String PORT_LOCATION = "port location";
    public static final String PORT_NUMBER = "port number";
    public static final String PROTOCOL = "protocol";
    public static final int SIZE_IPV4_ADDRESS = 4;
    public static final int SIZE_IPV6_ADDRESS = 16;
    public static final int SIZE_SHORT_INT = 2;
    public static final String SOURCE_ADDRESS = "source address";
    public static final String SOURCE_PORT_NUMBER = "source port number";
    public static final String TARGET_IP = "target IP";
    public static final String TARGET_PORT_NUMBER = "target port number";

    /* renamed from: com.samsung.android.knox.net.firewall.FirewallRuleValidator$1, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static long convertFromHexToInt(String str) {
        return Long.parseLong(str, 16);
    }

    public static String convertIpv6ToCompleteForm(String str) {
        if (str == null || !str.contains("::")) {
            return str;
        }
        String[] strArrSplit = str.split("::");
        int i = 0;
        if (strArrSplit.length != 1) {
            if (strArrSplit.length != 2) {
                return null;
            }
            String[] strArrSplit2 = strArrSplit[0].split(":");
            String[] strArrSplit3 = strArrSplit[1].split(":");
            int length = (8 - strArrSplit2.length) - strArrSplit3.length;
            StringBuilder sb = new StringBuilder();
            while (i < strArrSplit2.length) {
                sb.append(strArrSplit2[i] + ":");
                i++;
            }
            for (int length2 = strArrSplit2.length; length2 < strArrSplit2.length + length; length2++) {
                sb.append("0:");
            }
            for (int length3 = strArrSplit2.length + length; length3 < 8; length3++) {
                sb.append(strArrSplit3[(length3 - strArrSplit2.length) - length]);
                if (length3 != 7) {
                    sb.append(":");
                }
            }
            return sb.toString();
        }
        if (str.charAt(0) == ':') {
            String[] strArrSplit4 = strArrSplit[0].split(":");
            int length4 = 8 - strArrSplit4.length;
            StringBuilder sb2 = new StringBuilder();
            while (i < length4) {
                sb2.append("0:");
                i++;
            }
            for (int i2 = length4; i2 < 8; i2++) {
                sb2.append(strArrSplit4[i2 - length4]);
                if (i2 != 7) {
                    sb2.append(":");
                }
            }
            return sb2.toString();
        }
        String[] strArrSplit5 = strArrSplit[0].split(":");
        int length5 = 8 - strArrSplit5.length;
        StringBuilder sb3 = new StringBuilder();
        while (i < length5) {
            sb3.append(strArrSplit5[i] + ":");
            i++;
        }
        while (length5 < 8) {
            sb3.append("0");
            if (length5 != 7) {
                sb3.append(":");
            }
            length5++;
        }
        return sb3.toString();
    }

    public static boolean isIpv4MappedAddress(byte[] bArr) {
        if (bArr != null && bArr.length >= 16) {
            for (int i = 0; i < 10; i++) {
                if (bArr[i] != 0) {
                    return false;
                }
            }
            if (bArr[10] == -1 && bArr[11] == -1) {
                return true;
            }
        }
        return false;
    }

    public static byte[] translateIpv4MappedAddress(byte[] bArr) {
        if (!isIpv4MappedAddress(bArr)) {
            return null;
        }
        byte[] bArr2 = new byte[4];
        System.arraycopy(bArr, 12, bArr2, 0, 4);
        return bArr2;
    }

    public static byte[] translateIpv4TextualAddress(String str) throws NumberFormatException {
        if (str == null || str.length() == 0) {
            return null;
        }
        byte[] bArr = new byte[4];
        String[] strArrSplit = str.split("\\.", -1);
        try {
            int length = strArrSplit.length;
            int i = 0;
            try {
                if (length == 1) {
                    long j = Long.parseLong(strArrSplit[0]);
                    if (j >= 0 && j <= 4294967295L) {
                        bArr[0] = (byte) ((j >> 24) & 255);
                        bArr[1] = (byte) (((16777215 & j) >> 16) & 255);
                        bArr[2] = (byte) (((j & 65535) >> 8) & 255);
                        bArr[3] = (byte) (j & 255);
                        return bArr;
                    }
                    return null;
                }
                if (length == 2) {
                    long j2 = Integer.parseInt(strArrSplit[0]);
                    if (j2 >= 0 && j2 <= 255) {
                        bArr[0] = (byte) (j2 & 255);
                        long j3 = Integer.parseInt(strArrSplit[1]);
                        if (j3 >= 0 && j3 <= 16777215) {
                            bArr[1] = (byte) ((j3 >> 16) & 255);
                            bArr[2] = (byte) (((j3 & 65535) >> 8) & 255);
                            bArr[3] = (byte) (j3 & 255);
                            return bArr;
                        }
                    }
                    return null;
                }
                if (length != 3) {
                    if (length != 4) {
                        return null;
                    }
                    while (i < 4) {
                        long j4 = Integer.parseInt(strArrSplit[i]);
                        if (j4 >= 0 && j4 <= 255) {
                            bArr[i] = (byte) (j4 & 255);
                            i++;
                        }
                        return null;
                    }
                    return bArr;
                }
                while (i < 2) {
                    long j5 = Integer.parseInt(strArrSplit[i]);
                    if (j5 >= 0 && j5 <= 255) {
                        bArr[i] = (byte) (j5 & 255);
                        i++;
                    }
                    return null;
                }
                long j6 = Integer.parseInt(strArrSplit[2]);
                if (j6 >= 0 && j6 <= 65535) {
                    bArr[2] = (byte) ((j6 >> 8) & 255);
                    bArr[3] = (byte) (j6 & 255);
                    return bArr;
                }
                return null;
            } catch (NumberFormatException unused) {
                return null;
            }
        } catch (NumberFormatException unused2) {
            return null;
        }
    }

    public static boolean validadeIpv4Range(String str) throws NumberFormatException {
        if (str != null && str.contains("-")) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length == 2 && validateIpv4Address(strArrSplit[0]) && validateIpv4Address(strArrSplit[1])) {
                String[] strArrSplit2 = strArrSplit[0].split("\\.");
                String[] strArrSplit3 = strArrSplit[1].split("\\.");
                if (strArrSplit2 != null && strArrSplit2.length == 4 && strArrSplit3 != null && strArrSplit3.length == 4) {
                    for (int i = 0; i < 4; i++) {
                        try {
                            int i2 = Integer.parseInt(strArrSplit2[i]);
                            int i3 = Integer.parseInt(strArrSplit3[i]);
                            if (i2 > i3) {
                                return false;
                            }
                            if (i2 < i3) {
                                return true;
                            }
                        } catch (NumberFormatException unused) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validadeIpv6Range(String str) throws NumberFormatException {
        if (str != null && str.contains("-")) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length == 2 && validateIpv6Address(strArrSplit[0]) && validateIpv6Address(strArrSplit[1])) {
                String[] strArrSplit2 = str.split("-");
                if (strArrSplit2[0].contains("::")) {
                    strArrSplit2[0] = convertIpv6ToCompleteForm(strArrSplit2[0]);
                }
                if (strArrSplit2[1].contains("::")) {
                    strArrSplit2[1] = convertIpv6ToCompleteForm(strArrSplit2[1]);
                }
                String[] strArrSplit3 = strArrSplit2[0].split(":");
                String[] strArrSplit4 = strArrSplit2[1].split(":");
                if (strArrSplit3 != null && strArrSplit3.length == 8 && strArrSplit4 != null && strArrSplit4.length == 8) {
                    for (int i = 0; i < 8; i++) {
                        long j = Long.parseLong(strArrSplit3[i], 16);
                        long j2 = Long.parseLong(strArrSplit4[i], 16);
                        if (j > j2) {
                            return false;
                        }
                        if (j < j2) {
                            return true;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validadePortNumberRange(String str) {
        if (str != null && str.contains("-")) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length == 2 && validatePortNumber(strArrSplit[0]) && validatePortNumber(strArrSplit[1])) {
                try {
                    return Integer.parseInt(strArrSplit[0]) <= Integer.parseInt(strArrSplit[1]);
                } catch (NumberFormatException unused) {
                }
            }
        }
        return false;
    }

    public static FirewallResponse validateAllowRule(FirewallRule firewallRule) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        if (firewallRule == null) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, FirewallResponseMessages.RULE_IS_NULL);
        }
        if (!validateUidRule(firewallRule)) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, sb.toString());
        }
        if (!validateForwardConstraints(firewallRule)) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, sb.toString());
        }
        Firewall.AddressType addressType = firewallRule.getAddressType();
        String ipAddress = firewallRule.getIpAddress();
        boolean z2 = false;
        if (addressType.equals(Firewall.AddressType.IPV4)) {
            if (!validadeIpv4Range(ipAddress) && !validateIpv4Address(ipAddress) && !"*".equals(ipAddress)) {
                sb.append("Parameter(s): address");
                z = false;
            }
            z = true;
        } else {
            if (!validadeIpv6Range(ipAddress) && !validateIpv6Address(ipAddress) && !"*".equals(ipAddress)) {
                sb.append("Parameter(s): address");
                z = false;
            }
            z = true;
        }
        if (!validatePortNumber(firewallRule.getPortNumber()) && !validadePortNumberRange(firewallRule.getPortNumber()) && !"*".equals(firewallRule.getPortNumber())) {
            if (z) {
                sb.append("Parameter(s): port number");
            } else {
                sb.append(", port number");
            }
            z = false;
        }
        if (firewallRule.getPortLocation() == null) {
            if (z) {
                sb.append("Parameter(s): port location");
            } else {
                sb.append(", port location");
            }
            z = false;
        }
        if (firewallRule.getApplication() == null || firewallRule.getApplication().getPackageName() == null || (!TextUtils.isEmpty(firewallRule.getApplication().getPackageName()) && !validatePackageName(firewallRule.getApplication().getPackageName()))) {
            if (z) {
                sb.append("Parameter(s): app identity");
            } else {
                sb.append(", app identity");
            }
            z = false;
        }
        if ((firewallRule.getStrNetworkInterface() != null && !validateInterfaceName(firewallRule)) || firewallRule.getNetworkInterface() == null) {
            if (z) {
                sb.append("Parameter(s): network interface");
            } else {
                sb.append(", network interface");
            }
            z = false;
        }
        if (firewallRule.getProtocol() == null) {
            if (z) {
                sb.append("Parameter(s): protocol");
            } else {
                sb.append(", protocol");
            }
            z = false;
        }
        if (firewallRule.getDirection() != null) {
            z2 = z;
        } else if (z) {
            sb.append("Parameter(s): direction");
        } else {
            sb.append(", direction");
        }
        if (z2) {
            return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, FirewallResponseMessages.VALIDATION_SUCCESS);
        }
        sb.append(FirewallResponseMessages.IS_ARE_INVALID);
        return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, sb.toString());
    }

    public static FirewallResponse validateDenyRule(FirewallRule firewallRule) {
        return validateAllowRule(firewallRule);
    }

    public static FirewallResponse validateFirewallRule(FirewallRule firewallRule) {
        if (firewallRule == null) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, FirewallResponseMessages.RULE_IS_NULL);
        }
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[firewallRule.getRuleType().ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, FirewallResponseMessages.VALIDATION_FAILED) : validateRedirectExceptionRule(firewallRule) : validateRedirectRule(firewallRule) : validateDenyRule(firewallRule) : validateAllowRule(firewallRule);
    }

    public static boolean validateForwardConstraints(FirewallRule firewallRule) {
        if (Firewall.Direction.FORWARD.equals(firewallRule.getDirection())) {
            return firewallRule.getPortLocation() != null && Firewall.PortLocation.ALL.equals(firewallRule.getPortLocation()) && firewallRule.getNetworkInterface() != null && Firewall.NetworkInterface.ALL_NETWORKS.equals(firewallRule.getNetworkInterface()) && TextUtils.isEmpty(firewallRule.getStrNetworkInterface());
        }
        return true;
    }

    public static boolean validateHostName(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("*")) {
            return true;
        }
        if (str.length() > 255) {
            return false;
        }
        String[] strArrSplit = str.split("\\.");
        for (int i = 0; i < strArrSplit[0].length(); i++) {
            char cCharAt = strArrSplit[0].charAt(i);
            if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                int i2 = 0;
                for (int i3 = 0; i3 < str.length(); i3++) {
                    if (str.charAt(i3) == '.') {
                        i2++;
                    }
                }
                if (i2 >= strArrSplit.length) {
                    return false;
                }
                for (String str2 : strArrSplit) {
                    if (str2.length() > 63) {
                        return false;
                    }
                }
                for (String str3 : strArrSplit) {
                    if (!str3.matches("^[A-Za-z0-9-]+$") || str3.charAt(0) == '-' || str3.charAt(str3.length() - 1) == '-') {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean validateInterfaceName(FirewallRule firewallRule) {
        if (firewallRule.getStrNetworkInterface() == null) {
            return false;
        }
        return INTERFACE_REGEX.matcher(firewallRule.getStrNetworkInterface()).matches();
    }

    public static boolean validateIpv4Address(String str) {
        if (translateIpv4TextualAddress(str) != null) {
            return Patterns.IP_ADDRESS.matcher(str).matches();
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ab, code lost:
    
        if (r9 == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00af, code lost:
    
        if ((r10 + 2) <= 16) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b1, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b2, code lost:
    
        r14 = r10 + 1;
        r2[r10] = (byte) ((r8 >> 8) & 255);
        r10 = r10 + 2;
        r2[r14] = (byte) (r8 & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c2, code lost:
    
        if (r11 == (-1)) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c4, code lost:
    
        r14 = r10 - r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c6, code lost:
    
        if (r10 != 16) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c8, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c9, code lost:
    
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00ca, code lost:
    
        if (r3 > r14) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00cc, code lost:
    
        r6 = (r11 + r14) - r3;
        r2[16 - r3] = r2[r6];
        r2[r6] = 0;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00da, code lost:
    
        r10 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00db, code lost:
    
        if (r10 == 16) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00dd, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00de, code lost:
    
        translateIpv4MappedAddress(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00e1, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean validateIpv6Address(String str) {
        int i;
        byte[] bArrTranslateIpv4TextualAddress;
        if (str != null && str.length() >= 2) {
            byte[] bArr = new byte[16];
            if (str.charAt(0) != ':') {
                i = 0;
            } else {
                if (str.charAt(1) != ':') {
                    return false;
                }
                i = 1;
            }
            int i2 = 0;
            boolean z = false;
            int i3 = 0;
            int i4 = i;
            int i5 = -1;
            while (true) {
                if (i >= str.length()) {
                    break;
                }
                int i6 = i + 1;
                char cCharAt = str.charAt(i);
                int iDigit = Character.digit(cCharAt, 16);
                if (iDigit != -1) {
                    i2 = (i2 << 4) | iDigit;
                    if (i2 > 65535) {
                        return false;
                    }
                    z = true;
                    i = i6;
                } else if (cCharAt == ':') {
                    if (z) {
                        if (i6 == str.length() || i3 + 2 > 16) {
                            return false;
                        }
                        int i7 = i3 + 1;
                        bArr[i3] = (byte) ((i2 >> 8) & 255);
                        i3 += 2;
                        bArr[i7] = (byte) (i2 & 255);
                        i2 = 0;
                        z = false;
                    } else {
                        if (i5 != -1) {
                            return false;
                        }
                        i5 = i3;
                    }
                    i = i6;
                    i4 = i;
                } else {
                    if (cCharAt != '.' || i3 + 4 > 16) {
                        return false;
                    }
                    String strSubstring = str.substring(i4, str.length());
                    int i8 = 0;
                    int i9 = 0;
                    while (true) {
                        int iIndexOf = strSubstring.indexOf(46, i8);
                        if (iIndexOf == -1) {
                            break;
                        }
                        i9++;
                        i8 = iIndexOf + 1;
                    }
                    if (i9 != 3 || (bArrTranslateIpv4TextualAddress = translateIpv4TextualAddress(strSubstring)) == null) {
                        return false;
                    }
                    int i10 = 0;
                    while (i10 < 4) {
                        bArr[i3] = bArrTranslateIpv4TextualAddress[i10];
                        i10++;
                        i3++;
                    }
                    z = false;
                }
            }
        } else {
            return false;
        }
    }

    public static boolean validatePackageName(String str) {
        if (str == null) {
            return false;
        }
        if (!"*".equals(str) && !Firewall.FIREWALL_SYSTEM_UIDS.equals(str)) {
            String[] strArrSplit = str.split("\\.");
            int i = 0;
            for (int i2 = 0; i2 < str.length(); i2++) {
                if (str.charAt(i2) == '.') {
                    i++;
                }
            }
            if (i >= strArrSplit.length) {
                return false;
            }
            for (String str2 : strArrSplit) {
                if (!str2.matches("^[A-Za-z0-9_]+$") || str2.charAt(0) == '_' || (str2.charAt(0) >= '0' && str2.charAt(0) <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validatePortNumber(String str) throws NumberFormatException {
        int i;
        if (str == null) {
            return false;
        }
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            i = -1;
        }
        return i >= 0 && i <= 65535;
    }

    public static FirewallResponse validateRedirectExceptionRule(FirewallRule firewallRule) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        if (firewallRule == null) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, FirewallResponseMessages.RULE_IS_NULL);
        }
        Firewall.AddressType addressType = firewallRule.getAddressType();
        String ipAddress = firewallRule.getIpAddress();
        boolean z2 = false;
        if (addressType.equals(Firewall.AddressType.IPV4)) {
            if (!validadeIpv4Range(ipAddress) && !validateIpv4Address(ipAddress) && !"*".equals(ipAddress)) {
                sb.append("Parameter(s): address");
                z = false;
            }
            z = true;
        } else {
            if (!validadeIpv6Range(ipAddress) && !validateIpv6Address(ipAddress) && !"*".equals(ipAddress)) {
                sb.append("Parameter(s): address");
                z = false;
            }
            z = true;
        }
        if (!validatePortNumber(firewallRule.getPortNumber()) && !validadePortNumberRange(firewallRule.getPortNumber()) && !"*".equals(firewallRule.getPortNumber())) {
            if (z) {
                sb.append("Parameter(s): port number");
            } else {
                sb.append(", port number");
            }
            z = false;
        }
        if (firewallRule.getApplication() == null || firewallRule.getApplication().getPackageName() == null || (!TextUtils.isEmpty(firewallRule.getApplication().getPackageName()) && !validatePackageName(firewallRule.getApplication().getPackageName()))) {
            if (z) {
                sb.append("Parameter(s): app identity");
            } else {
                sb.append(", app identity");
            }
            z = false;
        }
        if (firewallRule.getProtocol() == null) {
            if (z) {
                sb.append("Parameter(s): protocol");
            } else {
                sb.append(", protocol");
            }
            z = false;
        }
        if ((firewallRule.getStrNetworkInterface() == null || validateInterfaceName(firewallRule)) && firewallRule.getNetworkInterface() != null) {
            z2 = z;
        } else if (z) {
            sb.append("Parameter(s): network interface");
        } else {
            sb.append(", network interface");
        }
        if (z2) {
            return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, FirewallResponseMessages.VALIDATION_SUCCESS);
        }
        sb.append(FirewallResponseMessages.IS_ARE_INVALID);
        return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, sb.toString());
    }

    public static FirewallResponse validateRedirectRule(FirewallRule firewallRule) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        if (firewallRule == null) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, FirewallResponseMessages.RULE_IS_NULL);
        }
        Firewall.AddressType addressType = firewallRule.getAddressType();
        String ipAddress = firewallRule.getIpAddress();
        Firewall.AddressType addressType2 = Firewall.AddressType.IPV4;
        boolean z2 = false;
        if (addressType.equals(addressType2)) {
            if (!validadeIpv4Range(ipAddress) && !validateIpv4Address(ipAddress) && !"*".equals(ipAddress)) {
                sb.append("Parameter(s): source address");
                z = false;
            }
            z = true;
        } else {
            if (!validadeIpv6Range(ipAddress) && !validateIpv6Address(ipAddress) && !"*".equals(ipAddress)) {
                sb.append("Parameter(s): source address");
                z = false;
            }
            z = true;
        }
        if (!validatePortNumber(firewallRule.getPortNumber()) && !validadePortNumberRange(firewallRule.getPortNumber()) && !"*".equals(firewallRule.getPortNumber())) {
            if (z) {
                sb.append("Parameter(s): source port number");
            } else {
                sb.append(", source port number");
            }
            z = false;
        }
        String targetIpAddress = firewallRule.getTargetIpAddress();
        if (addressType.equals(addressType2)) {
            if (!validateIpv4Address(targetIpAddress)) {
                sb.append("Parameter(s): target IP");
                z = false;
            }
        } else if (!validateIpv6Address(targetIpAddress)) {
            sb.append("Parameter(s): target IP");
            z = false;
        }
        if (!validatePortNumber(firewallRule.getTargetPortNumber()) || "*".equals(firewallRule.getTargetPortNumber())) {
            if (z) {
                sb.append("Parameter(s): target port number");
            } else {
                sb.append(", target port number");
            }
            z = false;
        }
        if (firewallRule.getApplication() == null || firewallRule.getApplication().getPackageName() == null || (!TextUtils.isEmpty(firewallRule.getApplication().getPackageName()) && !validatePackageName(firewallRule.getApplication().getPackageName()))) {
            if (z) {
                sb.append("Parameter(s): app identity");
            } else {
                sb.append(", app identity");
            }
            z = false;
        }
        if ((firewallRule.getStrNetworkInterface() != null && !validateInterfaceName(firewallRule)) || firewallRule.getNetworkInterface() == null) {
            if (z) {
                sb.append("Parameter(s): network interface");
            } else {
                sb.append(", network interface");
            }
            z = false;
        }
        if (firewallRule.getProtocol() != null) {
            z2 = z;
        } else if (z) {
            sb.append("Parameter(s): protocol");
        } else {
            sb.append(", protocol");
        }
        if (z2) {
            return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, FirewallResponseMessages.VALIDATION_SUCCESS);
        }
        sb.append(FirewallResponseMessages.IS_ARE_INVALID);
        return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, sb.toString());
    }

    public static boolean validateUidRule(FirewallRule firewallRule) {
        return !(Firewall.Direction.INPUT.equals(firewallRule.getDirection()) || Firewall.Direction.FORWARD.equals(firewallRule.getDirection())) || "*".equals(firewallRule.getApplication().getPackageName());
    }
}
