package com.samsung.android.sume.solution.service;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.controller.SumeClient;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MediaMuxerDescriptor;
import com.samsung.android.sume.core.descriptor.MediaParserDescriptor;
import com.samsung.android.sume.core.descriptor.p044nn.NNDescriptor;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.functional.PlaceHolder;
import com.samsung.android.sume.core.graph.MFDescriptorGraph;
import com.samsung.android.sume.core.service.ServiceProxySupplier;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.HwUnit;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.p045nn.Model;
import com.samsung.android.sume.core.types.p045nn.NNFW;
import com.samsung.android.sume.solution.Option;

/* loaded from: classes4.dex */
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
        CodecDescriptor audioDecoder = new CodecDescriptor(MediaType.COMPRESSED_AUDIO);
        CodecDescriptor audioEncoder = new CodecDescriptor(MediaType.RAW_AUDIO);
        audioEncoder.setBitrate(option.getAudioBitrate());
        CodecDescriptor videoDecoder = new CodecDescriptor(MediaType.COMPRESSED_VIDEO);
        CodecDescriptor videoEncoder = new CodecDescriptor(MediaType.RAW_VIDEO);
        videoEncoder.setScale(4.0f);
        videoEncoder.setBitrate(option.getVideoBitrate());
        audioDecoder.setRunInstant(true);
        audioEncoder.setRunInstant(true);
        videoDecoder.setRunInstant(true);
        videoEncoder.setRunInstant(true);
        MediaParserDescriptor parser = new MediaParserDescriptor(new MediaType[0]);
        MediaMuxerDescriptor muxer = new MediaMuxerDescriptor(0);
        muxer.setMediaTypeToNotifyEvent(MediaType.COMPRESSED_VIDEO);
        NNDescriptor upscaler = new NNDescriptor(Model.VIDEO_UPSCALER_X4, NNFW.TFLITE, HwUnit.GPU, 1);
        upscaler.setBatchIO(true);
        upscaler.setKeepFilterDatatype(true);
        option.runOneByOne();
        option.packedIOBuffers();
        MFDescriptorGraph graph = this.graphBuilder.addNode(parser, audioDecoder, Evaluator.m362eq(MediaType.COMPRESSED_AUDIO), 1).addNode(audioDecoder, audioEncoder).addNode(audioEncoder, muxer, Evaluator.m362eq(MediaType.RAW_AUDIO)).addNode(parser, videoDecoder, Evaluator.m362eq(MediaType.COMPRESSED_VIDEO), 1).addNode(videoDecoder, upscaler, 2).addNode(upscaler, videoEncoder, 3).addNode(videoEncoder, muxer, Evaluator.m362eq(MediaType.RAW_VIDEO)).build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
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
        NNDescriptor upscaler = new NNDescriptor(Model.IMAGE_UPSCALER_X4, NNFW.SNAP, HwUnit.GPU, 1);
        upscaler.setTargetFormat(MediaFormat.mutableImageOf(new Object[0]).setDataType(DataType.U8C3));
        this.graphBuilder.addNode(upscaler);
        MFDescriptorGraph graph = this.graphBuilder.build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newMotionPhotoVSWUpscaler(Option option) {
        ImgpDescriptor vswUpscaler;
        switch (option.getUpscaler()) {
            case 2:
                vswUpscaler = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X2_UPSCALER");
                break;
            case 3:
                vswUpscaler = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X3_UPSCALER");
                break;
            default:
                vswUpscaler = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X4_UPSCALER");
                break;
        }
        MFDescriptorGraph graph = this.graphBuilder.addNode(vswUpscaler).build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newMasteringNet(Option option) {
        ImgpDescriptor imgpDescriptor = new ImgpDescriptor("com.samsung.android.masteringnet.MasteringNetPlugin", "MasteringNet");
        MFDescriptorGraph graph = this.graphBuilder.addNode(imgpDescriptor).build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newAvailableMasteringNet(Option option) {
        ImgpDescriptor imgpDescriptor = new ImgpDescriptor("com.samsung.android.masteringnet.MasteringNetPlugin", "AvailableMasteringNet");
        MFDescriptorGraph graph = this.graphBuilder.addNode(imgpDescriptor).build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newImageUpscaler(Option option) {
        NNDescriptor miracleEstimator = new NNDescriptor(Model.MIRACLE_ESTIMATOR, NNFW.TFLITE, HwUnit.GPU, 1);
        miracleEstimator.setKeepFilterDatatype(true);
        miracleEstimator.setInputWithEvaluationValue(true);
        NNDescriptor miracleFilter = new NNDescriptor(Model.MIRACLE_FILTER, NNFW.TFLITE, HwUnit.GPU, 1);
        NNDescriptor upscaler = new NNDescriptor(Model.IMAGE_UPSCALER_X4, NNFW.SNAP, HwUnit.GPU, 1);
        upscaler.setTargetFormat(MediaFormat.mutableImageOf(new Object[0]).setDataType(DataType.U8C3));
        float miracleFilterThreshold = option.getFilterThreshold().floatValue();
        if (miracleFilterThreshold == 0.0f) {
            miracleFilterThreshold = 86.0f;
        }
        this.graphBuilder.addNode(miracleEstimator, upscaler, Evaluator.m363ge(Float.valueOf(miracleFilterThreshold))).addNode(miracleEstimator, miracleFilter, Evaluator.m366lt(Float.valueOf(miracleFilterThreshold))).addNode(miracleFilter, upscaler);
        if (option.isSupportAlphaChannel()) {
            ImgpDescriptor extractAlpha = new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "EXTRACT_ALPHA");
            ImgpDescriptor mergeAlpha = new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "MERGE_ALPHA");
            this.graphBuilder.addNode(extractAlpha, miracleEstimator).addNode(upscaler, mergeAlpha);
        }
        MFDescriptorGraph graph = this.graphBuilder.build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newImageVSWUpscaler(Option option) {
        ImgpDescriptor vswUpscaler;
        NNDescriptor miracleEstimator = new NNDescriptor(Model.MIRACLE_ESTIMATOR, NNFW.TFLITE, HwUnit.GPU, 1);
        miracleEstimator.setKeepFilterDatatype(true);
        miracleEstimator.setInputWithEvaluationValue(true);
        NNDescriptor miracleFilter = new NNDescriptor(Model.MIRACLE_FILTER, NNFW.TFLITE, HwUnit.GPU, 1);
        switch (option.getUpscaler()) {
            case 2:
                vswUpscaler = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X2_UPSCALER");
                break;
            case 3:
                vswUpscaler = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X3_UPSCALER");
                break;
            default:
                vswUpscaler = new ImgpDescriptor("com.samsung.android.sume.midas.upscaler.WrapVSWEnginePlugin", "X4_UPSCALER");
                break;
        }
        float miracleFilterThreshold = option.getFilterThreshold().floatValue();
        if (miracleFilterThreshold == 0.0f) {
            miracleFilterThreshold = 86.0f;
        }
        this.graphBuilder.addNode(miracleEstimator, vswUpscaler, Evaluator.m363ge(Float.valueOf(miracleFilterThreshold))).addNode(miracleEstimator, miracleFilter, Evaluator.m366lt(Float.valueOf(miracleFilterThreshold))).addNode(miracleFilter, vswUpscaler);
        if (option.isSupportAlphaChannel()) {
            ImgpDescriptor extractAlpha = new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "EXTRACT_ALPHA");
            ImgpDescriptor mergeAlpha = new ImgpDescriptor("com.samsung.android.sume.ext.plugin.AlphaChannelPlugin", "MERGE_ALPHA");
            this.graphBuilder.addNode(extractAlpha, miracleEstimator).addNode(vswUpscaler, mergeAlpha);
        }
        MFDescriptorGraph graph = this.graphBuilder.build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newOldPhotoDetector() {
        return newOldPhotoDetector(new Option());
    }

    public SumeClient newOldPhotoDetector(Option option) {
        NNDescriptor oldPhotoDetector = new NNDescriptor(Model.OLD_PHOTO_ESTIMATOR, NNFW.TFLITE, HwUnit.CPU, 1);
        oldPhotoDetector.setKeepFilterDatatype(true);
        MFDescriptorGraph graph = this.graphBuilder.addNode(oldPhotoDetector).build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }

    public SumeClient newOldPhotoEnhancer() {
        return newOldPhotoEnhancer(new Option());
    }

    public SumeClient newOldPhotoEnhancer(Option option) {
        ImgpDescriptor extractBgNFaces = new ImgpDescriptor("com.samsung.android.sume.nn.plugin.OldPhotoPlugin", "SEPARATE_BG_FACES");
        ImgpDescriptor composeBgNFaces = new ImgpDescriptor("com.samsung.android.sume.nn.plugin.OldPhotoPlugin", "COMPOSE_BG_FACES");
        composeBgNFaces.setWaitToReceiveAll(true);
        NNDescriptor oldPhotoEnhancer = new NNDescriptor(Model.OLD_PHOTO_ENHANCER, NNFW.SNAP, HwUnit.GPU, 1);
        oldPhotoEnhancer.setFilterIgnorable(true);
        NNDescriptor oldPhotoFaceEnhancer = new NNDescriptor(Model.OLD_PHOTO_FACE_ENHANCER, NNFW.SNAP, HwUnit.GPU, 1);
        MFDescriptorGraph graph = this.graphBuilder.addNode(extractBgNFaces, oldPhotoEnhancer).addNode(oldPhotoEnhancer, composeBgNFaces).addNode(extractBgNFaces, oldPhotoFaceEnhancer).addNode(oldPhotoFaceEnhancer, composeBgNFaces).build(option);
        return new SumeClient(this.serviceProxySupplier.get(), graph);
    }
}
