package com.android.server.chimera;

import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.genie.GenieConfigurations;
import com.android.server.chimera.genie.GenieLRUList;
import java.io.PrintWriter;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GenieUnloadPolicyHandler extends PolicyHandler {
    @Override // com.android.server.chimera.PolicyHandler
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.android.server.chimera.PolicyHandler
    public final int executePolicy(ChimeraCommonUtil.TriggerSource triggerSource, int i) {
        SystemRepository systemRepository = this.mSystemRepository;
        long availableMemoryKb = ChimeraCommonUtil.getAvailableMemoryKb(systemRepository);
        if (GenieConfigurations.GENAI_UNLOAD_MEMORY_PSI_LEVEL != i || ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_LMKD_GENIE != triggerSource || availableMemoryKb > GenieConfigurations.GENAI_UNLOAD_THRESHOLD) {
            return 0;
        }
        SystemRepository.log("GenieUnloadPolicyHandler", "Memory pressure occured");
        System.currentTimeMillis();
        Map.Entry firstEntry = GenieLRUList.getInstance().firstEntry();
        if (firstEntry == null) {
            SystemRepository.log("GenieUnloadPolicyHandler", "LRU list is empty");
            return 0;
        }
        String str = (String) firstEntry.getKey();
        SystemRepository.log("GenieUnloadPolicyHandler", "least used AIpackage: " + str);
        if (systemRepository.killGenieProcess(GenieConfigurations.GENAI_UNLOAD_OOMADJ_THRESHOLD, str)) {
            SystemRepository.log("GenieUnloadPolicyHandler", "process killed, remove from LRU list");
            GenieLRUList.getInstance().remove(str);
            return 0;
        }
        SystemRepository.log("GenieUnloadPolicyHandler", str + " not killed");
        return 0;
    }
}
