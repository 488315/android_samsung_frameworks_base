package com.samsung.android.sume.solution.service;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.controller.SumeClient;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MediaMuxerDescriptor;
import com.samsung.android.sume.core.descriptor.MediaParserDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.functional.PlaceHolder;
import com.samsung.android.sume.core.graph.MFDescriptorGraph;
import com.samsung.android.sume.core.service.ServiceProxySupplier;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.HwUnit;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.nn.Model;
import com.samsung.android.sume.core.types.nn.NNFW;
import com.samsung.android.sume.solution.Option;

/* loaded from: classes6.dex */
public class NNDirector {
    private static final String TAG = Def.tagOf((Class<?>) NNDirector.class);
    private final MFDescriptorGraph.Builder graphBuilder;
    private final ServiceProxySupplier serviceProxySupplier;
    private final String defaultServicePackage = "com.samsung.android.sume.nn.service";
    private final String defaultServiceClass = "com.samsung.android.sume.nn.service.RemoteNNService";
    private final String AlphaChannelPluginName = "com.samsung.android.sume.ext.plugin.AlphaChannelPlugin";
    private final String OldPhotoPluginName = "com.samsung.android.sume.nn.plugin.OldPhotoPlugin";
    private final String VSWUpscalerPluginName = "com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin";
    private final String MasteringNetPluginName = "com.samsung.android.masteringnet.MasteringNetPlugin";

    /* JADX WARN: Multi-variable type inference failed */
    public NNDirector(ServiceProxySupplier serviceProxySupplier) {
        if (serviceProxySupplier instanceof PlaceHolder) {
            this.serviceProxySupplier = (ServiceProxySupplier) ((PlaceHolder) serviceProxySupplier).setParameters("com.samsung.android.sume.nn.service", "com.samsung.android.sume.nn.service.RemoteNNService").reset();
        } else {
            this.serviceProxySupplier = serviceProxySupplier;
        }
        this.graphBuilder = new MFDescriptorGraph.Builder();
    }

    public SumeClient newVideoUpscaler() {
        Option option = new Option();
        option.setAudioBitrate(256000);
        return newVideoUpscaler(option);
    }

    public SumeClient newVideoUpscaler(Option option) {
        CodecDescriptor codecDescriptor = new CodecDescriptor(MediaType.COMPRESSED_AUDIO);
        CodecDescriptor codecDescriptor2 = new CodecDescriptor(MediaType.RAW_AUDIO);
        codecDescriptor2.setBitrate(option.getAudioBitrate());
        CodecDescriptor codecDescriptor3 = new CodecDescriptor(MediaType.COMPRESSED_VIDEO);
        CodecDescriptor codecDescriptor4 = new CodecDescriptor(MediaType.RAW_VIDEO);
        codecDescriptor4.setScale(4.0f);
        codecDescriptor4.setBitrate(option.getVideoBitrate());
        codecDescriptor.setRunInstant(true);
        codecDescriptor2.setRunInstant(true);
        codecDescriptor3.setRunInstant(true);
        codecDescriptor4.setRunInstant(true);
        MediaParserDescriptor mediaParserDescriptor = new MediaParserDescriptor(new MediaType[0]);
        MediaMuxerDescriptor mediaMuxerDescriptor = new MediaMuxerDescriptor(0);
        mediaMuxerDescriptor.setMediaTypeToNotifyEvent(MediaType.COMPRESSED_VIDEO);
        NNDescriptor nNDescriptor = new NNDescriptor(Model.VIDEO_UPSCALER_X4, NNFW.TFLITE, HwUnit.GPU, 1);
        nNDescriptor.setBatchIO(true);
        nNDescriptor.setKeepFilterDatatype(true);
        option.runOneByOne();
        option.packedIOBuffers();
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.addNode(mediaParserDescriptor, codecDescriptor, Evaluator.eq(MediaType.COMPRESSED_AUDIO), 1).addNode(codecDescriptor, codecDescriptor2).addNode(codecDescriptor2, mediaMuxerDescriptor, Evaluator.eq(MediaType.RAW_AUDIO)).addNode(mediaParserDescriptor, codecDescriptor3, Evaluator.eq(MediaType.COMPRESSED_VIDEO), 1).addNode(codecDescriptor3, nNDescriptor, 2).addNode(nNDescriptor, codecDescriptor4, 3).addNode(codecDescriptor4, mediaMuxerDescriptor, Evaluator.eq(MediaType.RAW_VIDEO)).build(option));
    }

    @Deprecated
    public SumeClient newAiUpscaler() {
        return newImageUpscaler();
    }

    @Deprecated
    public SumeClient newAiUpscaler(Option option) {
        return newImageUpscaler(option);
    }

    public SumeClient newImageUpscaler() {
        return newImageUpscaler(new Option());
    }

    public SumeClient newMotionPhotoUpscaler() {
        return newMotionPhotoUpscaler(new Option());
    }

    public SumeClient newImageVSWUpscaler() {
        return newImageVSWUpscaler(new Option());
    }

    public SumeClient newMotionPhotoUpscaler(Option option) {
        NNDescriptor nNDescriptor = new NNDescriptor(Model.IMAGE_UPSCALER_X4, NNFW.SNAP, HwUnit.GPU, 1);
        nNDescriptor.setTargetFormat(MediaFormat.mutableImageOf(new Object[0]).setDataType(DataType.U8C3));
        this.graphBuilder.addNode(nNDescriptor);
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.build(option));
    }

    public SumeClient newMotionPhotoVSWUpscaler(Option option) {
        ImgpDescriptor imgpDescriptor;
        int upscaler = option.getUpscaler();
        if (upscaler == 2) {
            imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X2_UPSCALER");
        } else if (upscaler == 3) {
            imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X3_UPSCALER");
        } else {
            imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X4_UPSCALER");
        }
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.addNode(imgpDescriptor).build(option));
    }

    public SumeClient newMasteringNet(Option option) {
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.addNode(new ImgpDescriptor("com.samsung.android.masteringnet.MasteringNetPlugin", "MasteringNet")).build(option));
    }

    public SumeClient newAvailableMasteringNet(Option option) {
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.addNode(new ImgpDescriptor("com.samsung.android.masteringnet.MasteringNetPlugin", "AvailableMasteringNet")).build(option));
    }

    public SumeClient newImageUpscaler(Option option) {
        NNDescriptor nNDescriptor = new NNDescriptor(Model.MIRACLE_ESTIMATOR, NNFW.TFLITE, HwUnit.GPU, 1);
        nNDescriptor.setKeepFilterDatatype(true);
        nNDescriptor.setInputWithEvaluationValue(true);
        NNDescriptor nNDescriptor2 = new NNDescriptor(Model.MIRACLE_FILTER, NNFW.TFLITE, HwUnit.GPU, 1);
        NNDescriptor nNDescriptor3 = new NNDescriptor(Model.IMAGE_UPSCALER_X4, NNFW.SNAP, HwUnit.GPU, 1);
        nNDescriptor3.setTargetFormat(MediaFormat.mutableImageOf(new Object[0]).setDataType(DataType.U8C3));
        float floatValue = option.getFilterThreshold().floatValue();
        if (floatValue == 0.0f) {
            floatValue = 86.0f;
        }
        this.graphBuilder.addNode(nNDescriptor, nNDescriptor3, Evaluator.ge(Float.valueOf(floatValue))).addNode(nNDescriptor, nNDescriptor2, Evaluator.lt(Float.valueOf(floatValue))).addNode(nNDescriptor2, nNDescriptor3);
        if (option.isSupportAlphaChannel()) {
            ImgpDescriptor imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "EXTRACT_ALPHA");
            this.graphBuilder.addNode(imgpDescriptor, nNDescriptor).addNode(nNDescriptor3, new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "MERGE_ALPHA"));
        }
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.build(option));
    }

    public SumeClient newImageVSWUpscaler(Option option) {
        ImgpDescriptor imgpDescriptor;
        NNDescriptor nNDescriptor = new NNDescriptor(Model.MIRACLE_ESTIMATOR, NNFW.TFLITE, HwUnit.GPU, 1);
        nNDescriptor.setKeepFilterDatatype(true);
        nNDescriptor.setInputWithEvaluationValue(true);
        NNDescriptor nNDescriptor2 = new NNDescriptor(Model.MIRACLE_FILTER, NNFW.TFLITE, HwUnit.GPU, 1);
        int upscaler = option.getUpscaler();
        if (upscaler == 2) {
            imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X2_UPSCALER");
        } else if (upscaler == 3) {
            imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X3_UPSCALER");
        } else {
            imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X4_UPSCALER");
        }
        float floatValue = option.getFilterThreshold().floatValue();
        if (floatValue == 0.0f) {
            floatValue = 86.0f;
        }
        this.graphBuilder.addNode(nNDescriptor, imgpDescriptor, Evaluator.ge(Float.valueOf(floatValue))).addNode(nNDescriptor, nNDescriptor2, Evaluator.lt(Float.valueOf(floatValue))).addNode(nNDescriptor2, imgpDescriptor);
        if (option.isSupportAlphaChannel()) {
            ImgpDescriptor imgpDescriptor2 = new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "EXTRACT_ALPHA");
            this.graphBuilder.addNode(imgpDescriptor2, nNDescriptor).addNode(imgpDescriptor, new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "MERGE_ALPHA"));
        }
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.build(option));
    }

    public SumeClient newOldPhotoDetector() {
        return newOldPhotoDetector(new Option());
    }

    public SumeClient newOldPhotoDetector(Option option) {
        NNDescriptor nNDescriptor = new NNDescriptor(Model.OLD_PHOTO_ESTIMATOR, NNFW.TFLITE, HwUnit.CPU, 1);
        nNDescriptor.setKeepFilterDatatype(true);
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.addNode(nNDescriptor).build(option));
    }

    public SumeClient newOldPhotoEnhancer() {
        return newOldPhotoEnhancer(new Option());
    }

    public SumeClient newOldPhotoEnhancer(Option option) {
        ImgpDescriptor imgpDescriptor = new ImgpDescriptor("com.samsung.android.sume.nn.plugin.OldPhotoPlugin", "SEPARATE_BG_FACES");
        ImgpDescriptor imgpDescriptor2 = new ImgpDescriptor("com.samsung.android.sume.nn.plugin.OldPhotoPlugin", "COMPOSE_BG_FACES");
        imgpDescriptor2.setWaitToReceiveAll(true);
        NNDescriptor nNDescriptor = new NNDescriptor(Model.OLD_PHOTO_ENHANCER, NNFW.SNAP, HwUnit.GPU, 1);
        nNDescriptor.setFilterIgnorable(true);
        NNDescriptor nNDescriptor2 = new NNDescriptor(Model.OLD_PHOTO_FACE_ENHANCER, NNFW.SNAP, HwUnit.GPU, 1);
        return new SumeClient(this.serviceProxySupplier.get(), this.graphBuilder.addNode(imgpDescriptor, nNDescriptor).addNode(nNDescriptor, imgpDescriptor2).addNode(imgpDescriptor, nNDescriptor2).addNode(nNDescriptor2, imgpDescriptor2).build(option));
    }
}
