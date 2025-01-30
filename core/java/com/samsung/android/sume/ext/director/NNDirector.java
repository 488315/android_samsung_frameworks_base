package com.samsung.android.sume.ext.director;

import com.samsung.android.sume.core.controller.SumeClient;
import com.samsung.android.sume.core.service.ServiceProxySupplier;

@Deprecated
/* loaded from: classes4.dex */
public class NNDirector extends com.samsung.android.sume.solution.service.NNDirector {
    public NNDirector(ServiceProxySupplier serviceProxy) {
        super(serviceProxy);
    }

    public SumeClient newVideoUpscaler(Option option) {
        return super.newVideoUpscaler((com.samsung.android.sume.solution.Option) option);
    }

    public SumeClient newAiUpscaler(Option option) {
        return super.newAiUpscaler((com.samsung.android.sume.solution.Option) option);
    }

    public SumeClient newImageUpscaler(Option option) {
        return super.newImageUpscaler((com.samsung.android.sume.solution.Option) option);
    }

    public SumeClient newOldPhotoDetector(Option option) {
        return super.newOldPhotoDetector((com.samsung.android.sume.solution.Option) option);
    }

    public SumeClient newOldPhotoEnhancer(Option option) {
        return super.newOldPhotoEnhancer((com.samsung.android.sume.solution.Option) option);
    }
}
