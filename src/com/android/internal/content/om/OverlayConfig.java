package com.android.internal.content.om;

import android.content.pm.PackagePartitions;
import android.content.res.AssetManager;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.apex.ApexInfo;
import com.android.apex.XmlParser;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.content.om.OverlayConfigParser;
import com.android.internal.content.om.OverlayScanner;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: classes5.dex */
public class OverlayConfig {
    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
    public static final String PARTITION_ORDER_FILE_PATH = "/product/overlay/partition_order.xml";
    static final String TAG = "OverlayConfig";
    private static OverlayConfig sInstance;
    private static final Comparator<OverlayConfigParser.ParsedConfiguration> sStaticOverlayComparator = new Comparator() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda4
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return OverlayConfig.lambda$static$0((OverlayConfigParser.ParsedConfiguration) obj, (OverlayConfigParser.ParsedConfiguration) obj2);
        }
    };
    private final ArrayMap<String, Configuration> mConfigurations = new ArrayMap<>();
    private final boolean mIsDefaultPartitionOrder;
    private final String mPartitionOrder;

    public interface PackageProvider {

        public interface Package {
            String getBaseApkPath();

            int getOverlayPriority();

            String getOverlayTarget();

            String getPackageName();

            int getTargetSdkVersion();

            boolean isOverlayIsStatic();
        }

        void forEachPackage(TriConsumer<Package, Boolean, File> triConsumer);
    }

    private static native String[] createIdmap(String str, String[] strArr, String[] strArr2, boolean z);

    public static final class Configuration {
        public final int configIndex;
        public final OverlayConfigParser.ParsedConfiguration parsedConfig;

        public Configuration(OverlayConfigParser.ParsedConfiguration parsedConfiguration, int i) {
            this.parsedConfig = parsedConfiguration;
            this.configIndex = i;
        }
    }

    static /* synthetic */ int lambda$static$0(OverlayConfigParser.ParsedConfiguration parsedConfiguration, OverlayConfigParser.ParsedConfiguration parsedConfiguration2) {
        OverlayScanner.ParsedOverlayInfo parsedOverlayInfo = parsedConfiguration.parsedInfo;
        OverlayScanner.ParsedOverlayInfo parsedOverlayInfo2 = parsedConfiguration2.parsedInfo;
        Preconditions.checkArgument(parsedOverlayInfo.isStatic && parsedOverlayInfo2.isStatic, "attempted to sort non-static overlay");
        if (!parsedOverlayInfo.targetPackageName.equals(parsedOverlayInfo2.targetPackageName)) {
            return parsedOverlayInfo.targetPackageName.compareTo(parsedOverlayInfo2.targetPackageName);
        }
        int i = parsedOverlayInfo.priority - parsedOverlayInfo2.priority;
        return i == 0 ? parsedOverlayInfo.path.compareTo(parsedOverlayInfo2.path) : i;
    }

    public OverlayConfig(final File file, Supplier<OverlayScanner> supplier, PackageProvider packageProvider) throws IOException {
        ArrayList arrayList;
        ArrayList arrayList2;
        int i = 1;
        Preconditions.checkArgument((supplier == null) != (packageProvider == null), "scannerFactory and packageProvider cannot be both null or both non-null");
        if (file == null) {
            arrayList = new ArrayList(PackagePartitions.getOrderedPartitions(new Function() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new OverlayConfigParser.OverlayPartition((PackagePartitions.SystemPartition) obj);
                }
            }));
        } else {
            arrayList = new ArrayList(PackagePartitions.getOrderedPartitions(new Function() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return OverlayConfig.lambda$new$1(file, (PackagePartitions.SystemPartition) obj);
                }
            }));
        }
        this.mIsDefaultPartitionOrder = !sortPartitions(PARTITION_ORDER_FILE_PATH, arrayList);
        this.mPartitionOrder = generatePartitionOrderString(arrayList);
        ArrayMap<Integer, List<String>> activeApexes = getActiveApexes(arrayList);
        Map<String, OverlayScanner.ParsedOverlayInfo> overlayPackageInfos = packageProvider == null ? null : getOverlayPackageInfos(packageProvider);
        ArrayList arrayList3 = new ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            OverlayConfigParser.OverlayPartition overlayPartition = (OverlayConfigParser.OverlayPartition) arrayList.get(i2);
            OverlayScanner overlayScanner = supplier == null ? null : supplier.get();
            ArrayList<OverlayConfigParser.ParsedConfiguration> configurations = OverlayConfigParser.getConfigurations(overlayPartition, overlayScanner, overlayPackageInfos, activeApexes.getOrDefault(Integer.valueOf(overlayPartition.type), Collections.EMPTY_LIST));
            if (configurations != null) {
                arrayList3.addAll(configurations);
            } else {
                if (supplier != null) {
                    arrayList2 = new ArrayList(overlayScanner.getAllParsedInfos());
                } else {
                    arrayList2 = new ArrayList(overlayPackageInfos.values());
                    for (int size2 = arrayList2.size() - i; size2 >= 0; size2--) {
                        if (!overlayPartition.containsFile(((OverlayScanner.ParsedOverlayInfo) arrayList2.get(size2)).getOriginalPartitionPath())) {
                            arrayList2.remove(size2);
                        }
                    }
                }
                ArrayList arrayList4 = new ArrayList();
                int size3 = arrayList2.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    OverlayScanner.ParsedOverlayInfo parsedOverlayInfo = (OverlayScanner.ParsedOverlayInfo) arrayList2.get(i3);
                    if (parsedOverlayInfo.isStatic) {
                        arrayList4.add(new OverlayConfigParser.ParsedConfiguration(parsedOverlayInfo.packageName, true, false, overlayPartition.policy, parsedOverlayInfo, null));
                    }
                }
                arrayList4.sort(sStaticOverlayComparator);
                arrayList3.addAll(arrayList4);
            }
            i2++;
            i = 1;
        }
        int size4 = arrayList3.size();
        for (int i4 = 0; i4 < size4; i4++) {
            OverlayConfigParser.ParsedConfiguration parsedConfiguration = (OverlayConfigParser.ParsedConfiguration) arrayList3.get(i4);
            this.mConfigurations.put(parsedConfiguration.packageName, new Configuration(parsedConfiguration, i4));
        }
    }

    static /* synthetic */ OverlayConfigParser.OverlayPartition lambda$new$1(File file, PackagePartitions.SystemPartition systemPartition) {
        return new OverlayConfigParser.OverlayPartition(new File(file, systemPartition.getNonConicalFolder().getPath()), systemPartition);
    }

    private static String generatePartitionOrderString(List<OverlayConfigParser.OverlayPartition> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0).getName());
        for (int i = 1; i < list.size(); i++) {
            sb.append(", ");
            sb.append(list.get(i).getName());
        }
        return sb.toString();
    }

    private static boolean parseAndValidatePartitionsOrderXml(String str, Map<String, Integer> map, List<OverlayConfigParser.OverlayPartition> list) throws SAXException, IOException {
        try {
            File file = new File(str);
            if (!file.exists()) {
                Log.w(TAG, "partition_order.xml does not exist.");
                return false;
            }
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            document.getDocumentElement().normalize();
            if (!document.getDocumentElement().getNodeName().equals("partition-order")) {
                Log.w(TAG, "Invalid partition_order.xml, xml root element is not partition-order");
                return false;
            }
            NodeList elementsByTagName = document.getElementsByTagName("partition");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Node nodeItem = elementsByTagName.item(i);
                if (nodeItem.getNodeType() == 1) {
                    String attribute = ((Element) nodeItem).getAttribute("name");
                    if (map.containsKey(attribute)) {
                        Log.w(TAG, "Invalid partition_order.xml, it has duplicate partition: " + attribute);
                        return false;
                    }
                    map.put(attribute, Integer.valueOf(i));
                }
            }
            if (map.keySet().size() != list.size()) {
                Log.w(TAG, "Invalid partition_order.xml, partition_order.xml has " + map.keySet().size() + " partitions, which is different from SYSTEM_PARTITIONS");
                return false;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (!map.keySet().contains(list.get(i2).getName())) {
                    Log.w(TAG, "Invalid Parsing partition_order.xml, partition_order.xml does not have partition: " + list.get(i2).getName());
                    return false;
                }
            }
            Log.i(TAG, "Sorting partitions in the specified order from partitions_order.xml");
            return true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            Log.w(TAG, "Parsing or validating partition_order.xml failed, exception thrown: " + e.getMessage());
            return false;
        }
    }

    public static boolean sortPartitions(String str, List<OverlayConfigParser.OverlayPartition> list) {
        final HashMap map = new HashMap();
        if (!parseAndValidatePartitionsOrderXml(str, map, list)) {
            return false;
        }
        Collections.sort(list, Comparator.comparingInt(new ToIntFunction() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) map.get(((OverlayConfigParser.OverlayPartition) obj).getName())).intValue();
            }
        }));
        return true;
    }

    public static OverlayConfig getZygoteInstance() {
        Trace.traceBegin(67108864L, "OverlayConfig#getZygoteInstance");
        try {
            return new OverlayConfig(null, new Supplier() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new OverlayScanner();
                }
            }, null);
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    public static OverlayConfig initializeSystemInstance(PackageProvider packageProvider) {
        Trace.traceBegin(67108864L, "OverlayConfig#initializeSystemInstance");
        try {
            sInstance = new OverlayConfig(null, null, packageProvider);
            Trace.traceEnd(67108864L);
            return sInstance;
        } catch (Throwable th) {
            Trace.traceEnd(67108864L);
            throw th;
        }
    }

    public static OverlayConfig getSystemInstance() {
        OverlayConfig overlayConfig = sInstance;
        if (overlayConfig != null) {
            return overlayConfig;
        }
        throw new IllegalStateException("System instance not initialized");
    }

    public Configuration getConfiguration(String str) {
        return this.mConfigurations.get(str);
    }

    public boolean isEnabled(String str) {
        Configuration configuration = this.mConfigurations.get(str);
        if (configuration == null) {
            return false;
        }
        return configuration.parsedConfig.enabled;
    }

    public boolean isMutable(String str) {
        Configuration configuration = this.mConfigurations.get(str);
        if (configuration == null) {
            return true;
        }
        return configuration.parsedConfig.mutable;
    }

    public int getPriority(String str) {
        Configuration configuration = this.mConfigurations.get(str);
        if (configuration == null) {
            return Integer.MAX_VALUE;
        }
        return configuration.configIndex;
    }

    private ArrayList<Configuration> getSortedOverlays() {
        ArrayList<Configuration> arrayList = new ArrayList<>();
        int size = this.mConfigurations.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.mConfigurations.valueAt(i));
        }
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda5
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((OverlayConfig.Configuration) obj).configIndex;
            }
        }));
        return arrayList;
    }

    private static Map<String, OverlayScanner.ParsedOverlayInfo> getOverlayPackageInfos(PackageProvider packageProvider) {
        final HashMap map = new HashMap();
        packageProvider.forEachPackage(new TriConsumer() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda6
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) {
                OverlayConfig.lambda$getOverlayPackageInfos$4(map, (OverlayConfig.PackageProvider.Package) obj, (Boolean) obj2, (File) obj3);
            }
        });
        return map;
    }

    static /* synthetic */ void lambda$getOverlayPackageInfos$4(HashMap map, PackageProvider.Package r9, Boolean bool, File file) {
        if (r9.getOverlayTarget() == null || !bool.booleanValue()) {
            return;
        }
        map.put(r9.getPackageName(), new OverlayScanner.ParsedOverlayInfo(r9.getPackageName(), r9.getOverlayTarget(), r9.getTargetSdkVersion(), r9.isOverlayIsStatic(), r9.getOverlayPriority(), new File(r9.getBaseApkPath()), file));
    }

    private static ArrayMap<Integer, List<String>> getActiveApexes(List<OverlayConfigParser.OverlayPartition> list) throws IOException {
        ArrayMap<Integer, List<String>> arrayMap = new ArrayMap<>();
        Iterator<OverlayConfigParser.OverlayPartition> it = list.iterator();
        while (it.hasNext()) {
            arrayMap.put(Integer.valueOf(it.next().type), new ArrayList());
        }
        File file = new File("/apex/apex-info-list.xml");
        if (file.exists() && file.canRead()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    for (ApexInfo apexInfo : XmlParser.readApexInfoList(fileInputStream).getApexInfo()) {
                        if (apexInfo.getIsActive()) {
                            Iterator<OverlayConfigParser.OverlayPartition> it2 = list.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    OverlayConfigParser.OverlayPartition next = it2.next();
                                    if (next.containsPath(apexInfo.getPreinstalledModulePath())) {
                                        arrayMap.get(Integer.valueOf(next.type)).add(apexInfo.getModuleName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    fileInputStream.close();
                    return arrayMap;
                } finally {
                }
            } catch (Exception e) {
                Log.w(TAG, "Error reading apex-info-list: " + e);
            }
        }
        return arrayMap;
    }

    public static class IdmapInvocation {
        public final boolean enforceOverlayable;
        public final ArrayList<String> overlayPaths = new ArrayList<>();
        public final String policy;

        IdmapInvocation(boolean z, String str) {
            this.enforceOverlayable = z;
            this.policy = str;
        }

        public String toString() {
            return getClass().getSimpleName() + String.format("{enforceOverlayable=%s, policy=%s, overlayPaths=[%s]}", Boolean.valueOf(this.enforceOverlayable), this.policy, String.join(", ", this.overlayPaths));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArrayList<IdmapInvocation> getImmutableFrameworkOverlayIdmapInvocations() {
        ArrayList<IdmapInvocation> arrayList = new ArrayList<>();
        ArrayList<Configuration> sortedOverlays = getSortedOverlays();
        int size = sortedOverlays.size();
        for (int i = 0; i < size; i++) {
            Configuration configuration = sortedOverlays.get(i);
            if (!configuration.parsedConfig.mutable && configuration.parsedConfig.enabled && "android".equals(configuration.parsedConfig.parsedInfo.targetPackageName)) {
                boolean z = configuration.parsedConfig.parsedInfo.targetSdkVersion >= 29;
                if (!arrayList.isEmpty()) {
                    IdmapInvocation idmapInvocation = arrayList.get(arrayList.size() - 1);
                    if (idmapInvocation.enforceOverlayable != z || !idmapInvocation.policy.equals(configuration.parsedConfig.policy)) {
                        idmapInvocation = null;
                    }
                    if (idmapInvocation == null) {
                        idmapInvocation = new IdmapInvocation(z, configuration.parsedConfig.policy);
                        arrayList.add(idmapInvocation);
                    }
                    idmapInvocation.overlayPaths.add(configuration.parsedConfig.parsedInfo.path.getAbsolutePath());
                }
            }
        }
        return arrayList;
    }

    public String[] createImmutableFrameworkIdmapsInZygote() {
        String str = AssetManager.FRAMEWORK_APK_PATH;
        ArrayList arrayList = new ArrayList();
        ArrayList<IdmapInvocation> immutableFrameworkOverlayIdmapInvocations = getImmutableFrameworkOverlayIdmapInvocations();
        int size = immutableFrameworkOverlayIdmapInvocations.size();
        for (int i = 0; i < size; i++) {
            IdmapInvocation idmapInvocation = immutableFrameworkOverlayIdmapInvocations.get(i);
            String[] strArrCreateIdmap = createIdmap(str, (String[]) idmapInvocation.overlayPaths.toArray(new String[0]), new String[]{"public", idmapInvocation.policy}, idmapInvocation.enforceOverlayable);
            if (strArrCreateIdmap == null) {
                Log.w(TAG, "'idmap2 create-multiple' failed: no mutable=\"false\" overlays targeting \"android\" will be loaded");
                return new String[0];
            }
            arrayList.addAll(Arrays.asList(strArrCreateIdmap));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("Overlay configurations:");
        indentingPrintWriter.increaseIndent();
        ArrayList arrayList = new ArrayList(this.mConfigurations.values());
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((OverlayConfig.Configuration) obj).configIndex;
            }
        }));
        for (int i = 0; i < arrayList.size(); i++) {
            Configuration configuration = (Configuration) arrayList.get(i);
            indentingPrintWriter.print(configuration.configIndex);
            indentingPrintWriter.print(", ");
            indentingPrintWriter.print(configuration.parsedConfig);
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public boolean isDefaultPartitionOrder() {
        return this.mIsDefaultPartitionOrder;
    }

    public String getPartitionOrder() {
        return this.mPartitionOrder;
    }
}
