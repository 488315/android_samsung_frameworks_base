package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MultiResolutionStreamConfigurationMap {
    private final Map<String, StreamConfiguration[]> mConfigurations;
    private final Map<Integer, List<MultiResolutionStreamInfo>> mMultiResolutionOutputConfigs = new HashMap();
    private final Map<Integer, List<MultiResolutionStreamInfo>> mMultiResolutionInputConfigs = new HashMap();

    public MultiResolutionStreamConfigurationMap(Map<String, StreamConfiguration[]> map) {
        Map<Integer, List<MultiResolutionStreamInfo>> map2;
        Preconditions.checkNotNull(map, "multi-resolution configurations must not be null");
        if (map.size() == 0) {
            throw new IllegalArgumentException("multi-resolution configurations must not be empty");
        }
        this.mConfigurations = map;
        for (Map.Entry<String, StreamConfiguration[]> entry : map.entrySet()) {
            String key = entry.getKey();
            for (StreamConfiguration streamConfiguration : entry.getValue()) {
                int format = streamConfiguration.getFormat();
                MultiResolutionStreamInfo multiResolutionStreamInfo = new MultiResolutionStreamInfo(streamConfiguration.getWidth(), streamConfiguration.getHeight(), key);
                if (streamConfiguration.isInput()) {
                    map2 = this.mMultiResolutionInputConfigs;
                } else {
                    map2 = this.mMultiResolutionOutputConfigs;
                }
                if (!map2.containsKey(Integer.valueOf(format))) {
                    map2.put(Integer.valueOf(format), new ArrayList());
                }
                map2.get(Integer.valueOf(format)).add(multiResolutionStreamInfo);
            }
        }
    }

    public static class SizeComparator implements Comparator<MultiResolutionStreamInfo> {
        @Override // java.util.Comparator
        public int compare(MultiResolutionStreamInfo multiResolutionStreamInfo, MultiResolutionStreamInfo multiResolutionStreamInfo2) {
            return StreamConfigurationMap.compareSizes(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight(), multiResolutionStreamInfo2.getWidth(), multiResolutionStreamInfo2.getHeight());
        }
    }

    public int[] getOutputFormats() {
        return getPublicImageFormats(true);
    }

    public int[] getInputFormats() {
        return getPublicImageFormats(false);
    }

    private int[] getPublicImageFormats(boolean z) {
        Map<Integer, List<MultiResolutionStreamInfo>> map = z ? this.mMultiResolutionOutputConfigs : this.mMultiResolutionInputConfigs;
        int[] iArr = new int[map.size()];
        Iterator<Integer> it = map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = StreamConfigurationMap.imageFormatToPublic(it.next().intValue());
            i++;
        }
        return iArr;
    }

    public Collection<MultiResolutionStreamInfo> getOutputInfo(int i) {
        return getInfo(i, true);
    }

    public Collection<MultiResolutionStreamInfo> getInputInfo(int i) {
        return getInfo(i, false);
    }

    private Collection<MultiResolutionStreamInfo> getInfo(int i, boolean z) {
        int iImageFormatToInternal = StreamConfigurationMap.imageFormatToInternal(i);
        Map<Integer, List<MultiResolutionStreamInfo>> map = z ? this.mMultiResolutionOutputConfigs : this.mMultiResolutionInputConfigs;
        if (map.containsKey(Integer.valueOf(iImageFormatToInternal))) {
            return Collections.unmodifiableCollection(map.get(Integer.valueOf(iImageFormatToInternal)));
        }
        return Collections.EMPTY_LIST;
    }

    private void appendConfigurationsString(StringBuilder sb, boolean z) {
        sb.append(z ? "Outputs(" : "Inputs(");
        int[] publicImageFormats = getPublicImageFormats(z);
        if (publicImageFormats != null) {
            for (int i : publicImageFormats) {
                Collection<MultiResolutionStreamInfo> info = getInfo(i, z);
                sb.append(NavigationBarInflaterView.SIZE_MOD_START + StreamConfigurationMap.formatToString(i) + ":");
                for (MultiResolutionStreamInfo multiResolutionStreamInfo : info) {
                    sb.append(String.format("[w:%d, h:%d, id:%s], ", Integer.valueOf(multiResolutionStreamInfo.getWidth()), Integer.valueOf(multiResolutionStreamInfo.getHeight()), multiResolutionStreamInfo.getPhysicalCameraId()));
                }
                if (sb.charAt(sb.length() - 1) == ' ') {
                    sb.delete(sb.length() - 2, sb.length());
                }
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultiResolutionStreamConfigurationMap)) {
            return false;
        }
        MultiResolutionStreamConfigurationMap multiResolutionStreamConfigurationMap = (MultiResolutionStreamConfigurationMap) obj;
        if (!this.mConfigurations.keySet().equals(multiResolutionStreamConfigurationMap.mConfigurations.keySet())) {
            return false;
        }
        for (String str : this.mConfigurations.keySet()) {
            if (!Arrays.equals(this.mConfigurations.get(str), multiResolutionStreamConfigurationMap.mConfigurations.get(str))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mConfigurations, this.mMultiResolutionOutputConfigs, this.mMultiResolutionInputConfigs);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MultiResolutionStreamConfigurationMap(");
        appendConfigurationsString(sb, true);
        sb.append(",");
        appendConfigurationsString(sb, false);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
