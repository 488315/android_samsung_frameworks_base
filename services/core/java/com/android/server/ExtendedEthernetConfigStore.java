package com.android.server;

import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class ExtendedEthernetConfigStore {
    public final Object mSync = new Object();
    public final ArrayMap mIpConfigurations = new ArrayMap(0);

    public static boolean doesConfigFileExist(String str) {
        return new File(str).exists();
    }

    public void read() {
        synchronized (this.mSync) {
            if (doesConfigFileExist("/data/misc/apexdata/com.android.tethering/misc/ethernet/ipconfig.txt")) {
                loadConfigFileLocked("/data/misc/apexdata/com.android.tethering/misc/ethernet/ipconfig.txt");
            }
        }
    }

    public final ArrayMap readIpConfigurations(String str) {
        try {
            return readIpConfigurations(new BufferedInputStream(new FileInputStream(str)));
        } catch (FileNotFoundException e) {
            Log.e("ExtendedEthernetConfigStore", "Error opening configuration file: " + e);
            return new ArrayMap(0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x02be, code lost:
    
        if (r3 == null) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x02b2, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02b0, code lost:
    
        if (r3 == null) goto L136;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v14, types: [android.net.StaticIpConfiguration$Builder] */
    /* JADX WARN: Type inference failed for: r0v15, types: [android.net.StaticIpConfiguration$Builder] */
    /* JADX WARN: Type inference failed for: r13v1, types: [java.net.InetAddress] */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v17 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r13v9 */
    /* JADX WARN: Type inference failed for: r14v1, types: [android.net.LinkAddress] */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v17 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r14v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayMap readIpConfigurations(InputStream inputStream) {
        DataInputStream dataInputStream;
        DataInputStream dataInputStream2;
        String str;
        int i;
        char c;
        int i2;
        String readUTF;
        ArrayMap arrayMap = new ArrayMap();
        String str2 = null;
        try {
            dataInputStream2 = new DataInputStream(inputStream);
        } catch (EOFException unused) {
            dataInputStream = null;
        } catch (IOException e) {
            e = e;
            dataInputStream = null;
        } catch (Throwable th) {
            th = th;
            dataInputStream = null;
        }
        try {
            int readInt = dataInputStream2.readInt();
            int i3 = 3;
            int i4 = 1;
            if (readInt != 3 && readInt != 2 && readInt != 1) {
                Log.e("ExtendedEthernetConfigStore", "Bad version on IP configuration file, ignore read");
                try {
                    dataInputStream2.close();
                } catch (Exception unused2) {
                }
                return null;
            }
            while (true) {
                IpConfiguration.IpAssignment ipAssignment = IpConfiguration.IpAssignment.DHCP;
                IpConfiguration.ProxySettings proxySettings = IpConfiguration.ProxySettings.NONE;
                ArrayList arrayList = new ArrayList();
                String str3 = str2;
                String str4 = str3;
                String str5 = str4;
                String str6 = str5;
                String str7 = str6;
                int i5 = -1;
                IpConfiguration.ProxySettings proxySettings2 = proxySettings;
                IpConfiguration.IpAssignment ipAssignment2 = ipAssignment;
                ?? r13 = str3;
                ?? r14 = str4;
                while (true) {
                    String readUTF2 = dataInputStream2.readUTF();
                    try {
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        str = str6;
                        i = i3;
                    }
                    if (readUTF2.equals("id")) {
                        if (readInt < i3) {
                            readUTF = String.valueOf(dataInputStream2.readInt());
                        } else {
                            readUTF = dataInputStream2.readUTF();
                        }
                        str5 = readUTF;
                    } else if (readUTF2.equals("ipAssignment")) {
                        ipAssignment2 = IpConfiguration.IpAssignment.valueOf(dataInputStream2.readUTF());
                    } else {
                        if (readUTF2.equals("linkAddress")) {
                            try {
                                LinkAddress linkAddress = new LinkAddress(InetAddresses.parseNumericAddress(dataInputStream2.readUTF()), dataInputStream2.readInt());
                                if ((linkAddress.getAddress() instanceof Inet4Address) && r14 == 0) {
                                    r14 = linkAddress;
                                    r13 = r13;
                                } else {
                                    Log.e("ExtendedEthernetConfigStore", "Non-IPv4 or duplicate address: " + linkAddress);
                                    r13 = r13;
                                    r14 = r14;
                                }
                            } catch (IllegalArgumentException e3) {
                                e = e3;
                                str = str6;
                                i = 3;
                                Log.e("ExtendedEthernetConfigStore", "Ignore invalid address while reading" + e);
                                i3 = i;
                                i4 = 1;
                                str6 = str;
                                r13 = r13;
                                r14 = r14;
                            }
                        } else if (readUTF2.equals("gateway")) {
                            if (readInt == i4) {
                                InetAddress parseNumericAddress = InetAddresses.parseNumericAddress(dataInputStream2.readUTF());
                                if (r13 == 0) {
                                    r13 = parseNumericAddress;
                                    r14 = r14;
                                } else {
                                    Log.e("ExtendedEthernetConfigStore", "Duplicate gateway: " + parseNumericAddress.getHostAddress());
                                    r13 = r13;
                                    r14 = r14;
                                }
                            } else {
                                LinkAddress linkAddress2 = dataInputStream2.readInt() == i4 ? new LinkAddress(InetAddresses.parseNumericAddress(dataInputStream2.readUTF()), dataInputStream2.readInt()) : null;
                                InetAddress parseNumericAddress2 = dataInputStream2.readInt() == i4 ? InetAddresses.parseNumericAddress(dataInputStream2.readUTF()) : null;
                                if (linkAddress2 != null && (!(linkAddress2.getAddress() instanceof Inet4Address) || linkAddress2.getPrefixLength() != 0 || r13 != 0)) {
                                    Log.e("ExtendedEthernetConfigStore", "Non-IPv4 default or duplicate route: " + linkAddress2.getAddress());
                                    r13 = r13;
                                    r14 = r14;
                                }
                                r13 = parseNumericAddress2;
                                r14 = r14;
                            }
                        } else if (readUTF2.equals("dns")) {
                            arrayList.add(InetAddresses.parseNumericAddress(dataInputStream2.readUTF()));
                            r13 = r13;
                            r14 = r14;
                        } else if (readUTF2.equals("proxySettings")) {
                            proxySettings2 = IpConfiguration.ProxySettings.valueOf(dataInputStream2.readUTF());
                            r13 = r13;
                            r14 = r14;
                        } else if (readUTF2.equals("proxyHost")) {
                            str2 = dataInputStream2.readUTF();
                            r13 = r13;
                            r14 = r14;
                        } else if (readUTF2.equals("proxyPort")) {
                            i5 = dataInputStream2.readInt();
                            r13 = r13;
                            r14 = r14;
                        } else if (readUTF2.equals("proxyPac")) {
                            str7 = dataInputStream2.readUTF();
                            r13 = r13;
                            r14 = r14;
                        } else if (readUTF2.equals("exclusionList")) {
                            str6 = dataInputStream2.readUTF();
                            r13 = r13;
                            r14 = r14;
                        } else {
                            if (readUTF2.equals("eos")) {
                                break;
                            }
                            str = str6;
                            i = 3;
                            try {
                                Log.e("ExtendedEthernetConfigStore", "Ignore unknown key " + readUTF2 + "while reading");
                            } catch (IllegalArgumentException e4) {
                                e = e4;
                                Log.e("ExtendedEthernetConfigStore", "Ignore invalid address while reading" + e);
                                i3 = i;
                                i4 = 1;
                                str6 = str;
                                r13 = r13;
                                r14 = r14;
                            }
                            i3 = i;
                            i4 = 1;
                            str6 = str;
                            r13 = r13;
                            r14 = r14;
                        }
                        str = str6;
                        i = 3;
                        i3 = i;
                        i4 = 1;
                        str6 = str;
                        r13 = r13;
                        r14 = r14;
                    }
                    str = str6;
                    i = i3;
                    i3 = i;
                    i4 = 1;
                    str6 = str;
                    r13 = r13;
                    r14 = r14;
                }
                StaticIpConfiguration build = new StaticIpConfiguration.Builder().setIpAddress(r14).setGateway(r13).setDnsServers(arrayList).build();
                if (str5 != null) {
                    IpConfiguration ipConfiguration = new IpConfiguration();
                    arrayMap.put(str5, ipConfiguration);
                    int i6 = AbstractC02711.$SwitchMap$android$net$IpConfiguration$IpAssignment[ipAssignment2.ordinal()];
                    if (i6 == i4) {
                        ipConfiguration.setStaticIpConfiguration(build);
                        ipConfiguration.setIpAssignment(ipAssignment2);
                    } else if (i6 == 2) {
                        ipConfiguration.setIpAssignment(ipAssignment2);
                    } else if (i6 == 3) {
                        Log.e("ExtendedEthernetConfigStore", "BUG: Found UNASSIGNED IP on file, use DHCP");
                        ipConfiguration.setIpAssignment(IpConfiguration.IpAssignment.DHCP);
                    } else {
                        Log.e("ExtendedEthernetConfigStore", "Ignore invalid ip assignment while reading.");
                        ipConfiguration.setIpAssignment(IpConfiguration.IpAssignment.UNASSIGNED);
                    }
                    int i7 = AbstractC02711.$SwitchMap$android$net$IpConfiguration$ProxySettings[proxySettings2.ordinal()];
                    if (i7 != i4) {
                        c = 2;
                        if (i7 != 2) {
                            i2 = 3;
                            if (i7 == 3) {
                                ipConfiguration.setProxySettings(proxySettings2);
                            } else if (i7 == 4) {
                                Log.e("ExtendedEthernetConfigStore", "BUG: Found UNASSIGNED proxy on file, use NONE");
                                ipConfiguration.setProxySettings(IpConfiguration.ProxySettings.NONE);
                            } else {
                                Log.e("ExtendedEthernetConfigStore", "Ignore invalid proxy settings while reading");
                                ipConfiguration.setProxySettings(IpConfiguration.ProxySettings.UNASSIGNED);
                            }
                        } else {
                            i2 = 3;
                            ProxyInfo buildPacProxy = ProxyInfo.buildPacProxy(Uri.parse(str7));
                            ipConfiguration.setProxySettings(proxySettings2);
                            ipConfiguration.setHttpProxy(buildPacProxy);
                        }
                    } else {
                        c = 2;
                        i2 = 3;
                        ProxyInfo buildDirectProxy = ProxyInfo.buildDirectProxy(str2, i5, parseProxyExclusionListString(str6));
                        ipConfiguration.setProxySettings(proxySettings2);
                        ipConfiguration.setHttpProxy(buildDirectProxy);
                        i3 = i2;
                        str2 = null;
                    }
                } else {
                    c = 2;
                    i2 = 3;
                }
                i3 = i2;
                str2 = null;
            }
        } catch (EOFException unused3) {
            dataInputStream = dataInputStream2;
        } catch (IOException e5) {
            e = e5;
            dataInputStream = dataInputStream2;
            try {
                Log.e("ExtendedEthernetConfigStore", "Error parsing configuration: " + e);
            } catch (Throwable th2) {
                th = th2;
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            dataInputStream = dataInputStream2;
            if (dataInputStream != null) {
            }
            throw th;
        }
        return arrayMap;
    }

    /* renamed from: com.android.server.ExtendedEthernetConfigStore$1 */
    public abstract /* synthetic */ class AbstractC02711 {
        public static final /* synthetic */ int[] $SwitchMap$android$net$IpConfiguration$IpAssignment;
        public static final /* synthetic */ int[] $SwitchMap$android$net$IpConfiguration$ProxySettings;

        static {
            int[] iArr = new int[IpConfiguration.ProxySettings.values().length];
            $SwitchMap$android$net$IpConfiguration$ProxySettings = iArr;
            try {
                iArr[IpConfiguration.ProxySettings.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$ProxySettings[IpConfiguration.ProxySettings.PAC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$ProxySettings[IpConfiguration.ProxySettings.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$ProxySettings[IpConfiguration.ProxySettings.UNASSIGNED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[IpConfiguration.IpAssignment.values().length];
            $SwitchMap$android$net$IpConfiguration$IpAssignment = iArr2;
            try {
                iArr2[IpConfiguration.IpAssignment.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$IpAssignment[IpConfiguration.IpAssignment.DHCP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$IpAssignment[IpConfiguration.IpAssignment.UNASSIGNED.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public final List parseProxyExclusionListString(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(str.toLowerCase(Locale.ROOT).split(","));
    }

    public final void loadConfigFileLocked(String str) {
        this.mIpConfigurations.putAll(readIpConfigurations(str));
    }

    public ArrayMap getIpConfigurations() {
        ArrayMap arrayMap;
        synchronized (this.mSync) {
            arrayMap = new ArrayMap(this.mIpConfigurations);
        }
        return arrayMap;
    }
}
