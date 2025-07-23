package com.samsung.android.knox.net.firewall;

import android.text.TextUtils;
import android.util.Patterns;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String[] split = str.split("::");
        int i = 0;
        if (split.length != 1) {
            if (split.length != 2) {
                return null;
            }
            String[] split2 = split[0].split(":");
            String[] split3 = split[1].split(":");
            int length = (8 - split2.length) - split3.length;
            StringBuilder sb = new StringBuilder();
            while (i < split2.length) {
                sb.append(split2[i] + ":");
                i++;
            }
            for (int length2 = split2.length; length2 < split2.length + length; length2++) {
                sb.append("0:");
            }
            for (int length3 = split2.length + length; length3 < 8; length3++) {
                sb.append(split3[(length3 - split2.length) - length]);
                if (length3 != 7) {
                    sb.append(":");
                }
            }
            return sb.toString();
        }
        if (str.charAt(0) == ':') {
            String[] split4 = split[0].split(":");
            int length4 = 8 - split4.length;
            StringBuilder sb2 = new StringBuilder();
            while (i < length4) {
                sb2.append("0:");
                i++;
            }
            for (int i2 = length4; i2 < 8; i2++) {
                sb2.append(split4[i2 - length4]);
                if (i2 != 7) {
                    sb2.append(":");
                }
            }
            return sb2.toString();
        }
        String[] split5 = split[0].split(":");
        int length5 = 8 - split5.length;
        StringBuilder sb3 = new StringBuilder();
        while (i < length5) {
            sb3.append(split5[i] + ":");
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

    public static byte[] translateIpv4TextualAddress(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        byte[] bArr = new byte[4];
        String[] split = str.split("\\.", -1);
        try {
            int length = split.length;
            int i = 0;
            try {
                if (length == 1) {
                    long parseLong = Long.parseLong(split[0]);
                    if (parseLong >= 0 && parseLong <= 4294967295L) {
                        bArr[0] = (byte) ((parseLong >> 24) & 255);
                        bArr[1] = (byte) (((16777215 & parseLong) >> 16) & 255);
                        bArr[2] = (byte) (((parseLong & 65535) >> 8) & 255);
                        bArr[3] = (byte) (parseLong & 255);
                        return bArr;
                    }
                    return null;
                }
                if (length == 2) {
                    long parseInt = Integer.parseInt(split[0]);
                    if (parseInt >= 0 && parseInt <= 255) {
                        bArr[0] = (byte) (parseInt & 255);
                        long parseInt2 = Integer.parseInt(split[1]);
                        if (parseInt2 >= 0 && parseInt2 <= 16777215) {
                            bArr[1] = (byte) ((parseInt2 >> 16) & 255);
                            bArr[2] = (byte) (((parseInt2 & 65535) >> 8) & 255);
                            bArr[3] = (byte) (parseInt2 & 255);
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
                        long parseInt3 = Integer.parseInt(split[i]);
                        if (parseInt3 >= 0 && parseInt3 <= 255) {
                            bArr[i] = (byte) (parseInt3 & 255);
                            i++;
                        }
                        return null;
                    }
                    return bArr;
                }
                while (i < 2) {
                    long parseInt4 = Integer.parseInt(split[i]);
                    if (parseInt4 >= 0 && parseInt4 <= 255) {
                        bArr[i] = (byte) (parseInt4 & 255);
                        i++;
                    }
                    return null;
                }
                long parseInt5 = Integer.parseInt(split[2]);
                if (parseInt5 >= 0 && parseInt5 <= 65535) {
                    bArr[2] = (byte) ((parseInt5 >> 8) & 255);
                    bArr[3] = (byte) (parseInt5 & 255);
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

    public static boolean validadeIpv4Range(String str) {
        if (str != null && str.contains("-")) {
            String[] split = str.split("-");
            if (split.length == 2 && validateIpv4Address(split[0]) && validateIpv4Address(split[1])) {
                String[] split2 = split[0].split("\\.");
                String[] split3 = split[1].split("\\.");
                if (split2 != null && split2.length == 4 && split3 != null && split3.length == 4) {
                    for (int i = 0; i < 4; i++) {
                        try {
                            int parseInt = Integer.parseInt(split2[i]);
                            int parseInt2 = Integer.parseInt(split3[i]);
                            if (parseInt > parseInt2) {
                                return false;
                            }
                            if (parseInt < parseInt2) {
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

    public static boolean validadeIpv6Range(String str) {
        if (str != null && str.contains("-")) {
            String[] split = str.split("-");
            if (split.length == 2 && validateIpv6Address(split[0]) && validateIpv6Address(split[1])) {
                String[] split2 = str.split("-");
                if (split2[0].contains("::")) {
                    split2[0] = convertIpv6ToCompleteForm(split2[0]);
                }
                if (split2[1].contains("::")) {
                    split2[1] = convertIpv6ToCompleteForm(split2[1]);
                }
                String[] split3 = split2[0].split(":");
                String[] split4 = split2[1].split(":");
                if (split3 != null && split3.length == 8 && split4 != null && split4.length == 8) {
                    for (int i = 0; i < 8; i++) {
                        long parseLong = Long.parseLong(split3[i], 16);
                        long parseLong2 = Long.parseLong(split4[i], 16);
                        if (parseLong > parseLong2) {
                            return false;
                        }
                        if (parseLong < parseLong2) {
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
            String[] split = str.split("-");
            if (split.length == 2 && validatePortNumber(split[0]) && validatePortNumber(split[1])) {
                try {
                    return Integer.parseInt(split[0]) <= Integer.parseInt(split[1]);
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
        String[] split = str.split("\\.");
        for (int i = 0; i < split[0].length(); i++) {
            char charAt = split[0].charAt(i);
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                int i2 = 0;
                for (int i3 = 0; i3 < str.length(); i3++) {
                    if (str.charAt(i3) == '.') {
                        i2++;
                    }
                }
                if (i2 >= split.length) {
                    return false;
                }
                for (String str2 : split) {
                    if (str2.length() > 63) {
                        return false;
                    }
                }
                for (String str3 : split) {
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

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ab, code lost:
    
        if (r9 == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00af, code lost:
    
        if ((r10 + 2) <= 16) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b1, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00b2, code lost:
    
        r14 = r10 + 1;
        r2[r10] = (byte) ((r8 >> 8) & 255);
        r10 = r10 + 2;
        r2[r14] = (byte) (r8 & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c2, code lost:
    
        if (r11 == (-1)) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c4, code lost:
    
        r14 = r10 - r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c6, code lost:
    
        if (r10 != 16) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00c8, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00c9, code lost:
    
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00ca, code lost:
    
        if (r3 > r14) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00cc, code lost:
    
        r6 = (r11 + r14) - r3;
        r2[16 - r3] = r2[r6];
        r2[r6] = 0;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00da, code lost:
    
        r10 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00db, code lost:
    
        if (r10 == 16) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00dd, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00de, code lost:
    
        translateIpv4MappedAddress(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00e1, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean validateIpv6Address(java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.firewall.FirewallRuleValidator.validateIpv6Address(java.lang.String):boolean");
    }

    public static boolean validatePackageName(String str) {
        if (str == null) {
            return false;
        }
        if (!"*".equals(str) && !Firewall.FIREWALL_SYSTEM_UIDS.equals(str)) {
            String[] split = str.split("\\.");
            int i = 0;
            for (int i2 = 0; i2 < str.length(); i2++) {
                if (str.charAt(i2) == '.') {
                    i++;
                }
            }
            if (i >= split.length) {
                return false;
            }
            for (String str2 : split) {
                if (!str2.matches("^[A-Za-z0-9_]+$") || str2.charAt(0) == '_' || (str2.charAt(0) >= '0' && str2.charAt(0) <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validatePortNumber(String str) {
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

    /* JADX WARN: Removed duplicated region for block: B:49:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.knox.net.firewall.FirewallResponse validateRedirectRule(com.samsung.android.knox.net.firewall.FirewallRule r8) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.firewall.FirewallRuleValidator.validateRedirectRule(com.samsung.android.knox.net.firewall.FirewallRule):com.samsung.android.knox.net.firewall.FirewallResponse");
    }

    public static boolean validateUidRule(FirewallRule firewallRule) {
        return !(Firewall.Direction.INPUT.equals(firewallRule.getDirection()) || Firewall.Direction.FORWARD.equals(firewallRule.getDirection())) || "*".equals(firewallRule.getApplication().getPackageName());
    }
}
