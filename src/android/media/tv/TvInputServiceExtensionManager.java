package android.media.tv;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public final class TvInputServiceExtensionManager {
    private static final String ANALOG_PACKAGE = "android.media.tv.extension.analog.";
    private static final String CAM_PACKAGE = "android.media.tv.extension.cam.";
    private static final String CLIENT_TOKEN_PACKAGE = "android.media.tv.extension.clienttoken.";
    private static final String EVENT_PACKAGE = "android.media.tv.extension.event.";
    public static final String IANALOG_ATTRIBUTE_INTERFACE = "android.media.tv.extension.analog.IAnalogAttributeInterface";
    public static final String IANALOG_AUDIO_INFO = "android.media.tv.extension.signal.IAnalogAudioInfo";
    public static final String IAUDIO_SIGNAL_INFO = "android.media.tv.extension.signal.IAudioSignalInfo";
    public static final String IAUDIO_SIGNAL_INFO_LISTENER = "android.media.tv.extension.signal.IAudioSignalInfoListener";
    public static final String IBROADCAST_TIME = "android.media.tv.extension.time.IBroadcastTime";
    public static final String ICAM_APP_INFO_LISTENER = "android.media.tv.extension.cam.ICamAppInfoListener";
    public static final String ICAM_APP_INFO_SERVICE = "android.media.tv.extension.cam.ICamAppInfoService";
    public static final String ICAM_DRM_INFO_LISTENER = "android.media.tv.extension.cam.ICamDrmInfoListener";
    public static final String ICAM_HOST_CONTROL_ASK_RELEASE_REPLY_CALLBACK = "android.media.tv.extension.cam.ICamHostControlAskReleaseReplyCallback";
    public static final String ICAM_HOST_CONTROL_INFO_LISTENER = "android.media.tv.extension.cam.ICamHostControlInfoListener";
    public static final String ICAM_HOST_CONTROL_SERVICE = "android.media.tv.extension.cam.ICamHostControlService";
    public static final String ICAM_HOST_CONTROL_TUNE_QUIETLY_FLAG = "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag";
    public static final String ICAM_HOST_CONTROL_TUNE_QUIETLY_FLAG_LISTENER = "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener";
    public static final String ICAM_INFO_LISTENER = "android.media.tv.extension.cam.ICamInfoListener";
    public static final String ICAM_MONITORING_SERVICE = "android.media.tv.extension.cam.ICamMonitoringService";
    public static final String ICAM_PIN_CAPABILITY_LISTENER = "android.media.tv.extension.cam.ICamPinCapabilityListener";
    public static final String ICAM_PIN_SERVICE = "android.media.tv.extension.cam.ICamPinService";
    public static final String ICAM_PIN_STATUS_LISTENER = "android.media.tv.extension.cam.ICamPinStatusListener";
    public static final String ICAM_PROFILE_INTERFACE = "android.media.tv.extension.cam.ICamProfileInterface";
    public static final String ICHANNEL_LIST_TRANSFER = "android.media.tv.extension.servicedb.IChannelListTransfer";
    public static final String ICHANNEL_TUNED_INTERFACE = "android.media.tv.extension.tune.IChannelTunedInterface";
    public static final String ICHANNEL_TUNED_LISTENER = "android.media.tv.extension.tune.IChannelTunedListener";
    public static final String ICLIENT_TOKEN = "android.media.tv.extension.clienttoken.IClientToken";
    public static final String ICONTENT_CONTROL_SERVICE = "android.media.tv.extension.cam.IContentControlService";
    public static final String IDATA_SERVICE_SIGNAL_INFO = "android.media.tv.extension.teletext.IDataServiceSignalInfo";
    public static final String IDATA_SERVICE_SIGNAL_INFO_LISTENER = "android.media.tv.extension.teletext.IDataServiceSignalInfoListener";
    public static final String IDELETE_RECORDED_CONTENTS_CALLBACK = "android.media.tv.extension.pvr.IDeleteRecordedContentsCallback";
    public static final String IDOWNLOADABLE_RATING_TABLE_MONITOR = "android.media.tv.extension.rating.IDownloadableRatingTableMonitor";
    public static final String IENTER_MENU_ERROR_CALLBACK = "android.media.tv.extension.cam.IEnterMenuErrorCallback";
    public static final String IEVENT_DOWNLOAD = "android.media.tv.extension.event.IEventDownload";
    public static final String IEVENT_DOWNLOAD_LISTENER = "android.media.tv.extension.event.IEventDownloadListener";
    public static final String IEVENT_DOWNLOAD_SESSION = "android.media.tv.extension.event.IEventDownloadSession";
    public static final String IEVENT_MONITOR = "android.media.tv.extension.event.IEventMonitor";
    public static final String IEVENT_MONITOR_LISTENER = "android.media.tv.extension.event.IEventMonitorListener";
    public static final String IFAVORITE_NETWORK = "android.media.tv.extension.scan.IFavoriteNetwork";
    public static final String IFAVORITE_NETWORK_LISTENER = "android.media.tv.extension.scan.IFavoriteNetworkListener";
    public static final String IGET_INFO_RECORDED_CONTENTS_CALLBACK = "android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback";
    public static final String IHDMI_SIGNAL_INFO_LISTENER = "android.media.tv.extension.signal.IHdmiSignalInfoListener";
    public static final String IHDMI_SIGNAL_INTERFACE = "android.media.tv.extension.signal.IHdmiSignalInterface";
    public static final String IHDPLUS_INFO = "android.media.tv.extension.scan.IHDPlusInfo";
    public static final String ILCNV2_CHANNEL_LIST = "android.media.tv.extension.scan.ILcnV2ChannelList";
    public static final String ILCNV2_CHANNEL_LIST_LISTENER = "android.media.tv.extension.scan.ILcnV2ChannelListListener";
    public static final String ILCN_CONFLICT = "android.media.tv.extension.scan.ILcnConflict";
    public static final String ILCN_CONFLICT_LISTENER = "android.media.tv.extension.scan.ILcnConflictListener";
    public static final String IMMI_INTERFACE = "android.media.tv.extension.cam.IMmiInterface";
    public static final String IMMI_SESSION = "android.media.tv.extension.cam.IMmiSession";
    public static final String IMMI_STATUS_CALLBACK = "android.media.tv.extension.cam.IMmiStatusCallback";
    public static final String IMUX_TUNE = "android.media.tv.extension.tune.IMuxTune";
    public static final String IMUX_TUNE_SESSION = "android.media.tv.extension.tune.IMuxTuneSession";
    public static final String IOAD_UPDATE_INTERFACE = "android.media.tv.extension.oad.IOadUpdateInterface";
    public static final String IOPERATOR_DETECTION = "android.media.tv.extension.scan.IOperatorDetection";
    public static final String IOPERATOR_DETECTION_LISTENER = "android.media.tv.extension.scan.IOperatorDetectionListener";
    public static final String IPMT_RATING_INTERFACE = "android.media.tv.extension.rating.IPmtRatingInterface";
    public static final String IPMT_RATING_LISTENER = "android.media.tv.extension.rating.IPmtRatingListener";
    public static final String IRATING_INTERFACE = "android.media.tv.extension.rating.IRatingInterface";
    public static final String IRECORDED_CONTENTS = "android.media.tv.extension.pvr.IRecordedContents";
    public static final String IREGION_CHANNEL_LIST = "android.media.tv.extension.scan.IRegionChannelList";
    public static final String IREGION_CHANNEL_LIST_LISTENER = "android.media.tv.extension.scan.IRegionChannelListListener";
    public static final String ISCAN_BACKGROUND_SERVICE_UPDATE = "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate";
    public static final String ISCAN_BACKGROUND_SERVICE_UPDATE_LISTENER = "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener";
    public static final String ISCAN_INTERFACE = "android.media.tv.extension.scan.IScanInterface";
    public static final String ISCAN_LISTENER = "android.media.tv.extension.scan.IScanListener";
    public static final String ISCAN_SAT_SEARCH = "android.media.tv.extension.scan.IScanSatSearch";
    public static final String ISCAN_SESSION = "android.media.tv.extension.scan.IScanSession";
    public static final String ISCREEN_MODE_SETTINGS = "android.media.tv.extension.screenmode.IScreenModeSettings";
    public static final String ISERVICE_LIST = "android.media.tv.extension.servicedb.IServiceList";
    public static final String ISERVICE_LIST_EDIT = "android.media.tv.extension.servicedb.IServiceListEdit";
    public static final String ISERVICE_LIST_EDIT_LISTENER = "android.media.tv.extension.servicedb.IServiceListEditListener";
    public static final String ISERVICE_LIST_EXPORT_LISTENER = "android.media.tv.extension.servicedb.IServiceListExportListener";
    public static final String ISERVICE_LIST_EXPORT_SESSION = "android.media.tv.extension.servicedb.IServiceListExportSession";
    public static final String ISERVICE_LIST_IMPORT_LISTENER = "android.media.tv.extension.servicedb.IServiceListImportListener";
    public static final String ISERVICE_LIST_IMPORT_SESSION = "android.media.tv.extension.servicedb.IServiceListImportSession";
    public static final String ISERVICE_LIST_SET_CHANNEL_LIST_LISTENER = "android.media.tv.extension.servicedb.IServiceListSetChannelListListener";
    public static final String ISERVICE_LIST_SET_CHANNEL_LIST_SESSION = "android.media.tv.extension.servicedb.IServiceListSetChannelListSession";
    public static final String ISERVICE_LIST_TRANSFER_INTERFACE = "android.media.tv.extension.servicedb.IServiceListTransferInterface";
    public static final String ITARGET_REGION = "android.media.tv.extension.scan.ITargetRegion";
    public static final String ITARGET_REGION_LISTENER = "android.media.tv.extension.scan.ITargetRegionListener";
    public static final String ITELETEXT_PAGE_SUB_CODE = "android.media.tv.extension.teletext.ITeletextPageSubCode";
    public static final String ITKGS_INFO = "android.media.tv.extension.scan.ITkgsInfo";
    public static final String ITKGS_INFO_LISTENER = "android.media.tv.extension.scan.ITkgsInfoListener";
    public static final String ITUNER_FRONTEND_SIGNAL_INFO_INTERFACE = "android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface";
    public static final String ITUNER_FRONTEND_SIGNAL_INFO_LISTENER = "android.media.tv.extension.signal.ITunerFrontendSignalInfoListener";
    public static final String IVBI_RATING_INTERFACE = "android.media.tv.extension.rating.IVbiRatingInterface";
    public static final String IVBI_RATING_LISTENER = "android.media.tv.extension.rating.IVbiRatingListener";
    public static final String IVIDEO_SIGNAL_INFO = "android.media.tv.extension.signal.IVideoSignalInfo";
    public static final String IVIDEO_SIGNAL_INFO_LISTENER = "android.media.tv.extension.signal.IVideoSignalInfoListener";
    private static final String OAD_PACKAGE = "android.media.tv.extension.oad.";
    private static final String PVR_PACKAGE = "android.media.tv.extension.pvr.";
    private static final String RATING_PACKAGE = "android.media.tv.extension.rating.";
    private static final String SCAN_BSU_PACKAGE = "android.media.tv.extension.scanbsu.";
    private static final String SCAN_PACKAGE = "android.media.tv.extension.scan.";
    private static final String SCREEN_MODE_PACKAGE = "android.media.tv.extension.screenmode.";
    private static final String SERVICE_DATABASE_PACKAGE = "android.media.tv.extension.servicedb.";
    private static final String SIGNAL_PACKAGE = "android.media.tv.extension.signal.";
    private static final String TAG = "TvInputServiceExtensionManager";
    private static final String TELETEXT_PACKAGE = "android.media.tv.extension.teletext.";
    private static final String TIME_PACKAGE = "android.media.tv.extension.time.";
    private static final String TUNE_PACKAGE = "android.media.tv.extension.tune.";
    public static final String ICI_OPERATOR_INTERFACE = "android.media.tv.extension.cam.ICiOperatorInterface";
    public static final String ICI_OPERATOR_LISTENER = "android.media.tv.extension.cam.ICiOperatorListener";
    public static final String IPROGRAM_INFO = "android.media.tv.extension.rating.IProgramInfo";
    public static final String IPROGRAM_INFO_LISTENER = "android.media.tv.extension.rating.IProgramInfoListener";
    private static final Set<String> sTisExtensions = new HashSet(Set.of((Object[]) new String[]{"android.media.tv.extension.scan.IScanInterface", "android.media.tv.extension.scan.IScanSession", "android.media.tv.extension.scan.IScanListener", "android.media.tv.extension.scan.IHDPlusInfo", "android.media.tv.extension.scan.IOperatorDetection", "android.media.tv.extension.scan.IOperatorDetectionListener", "android.media.tv.extension.scan.IRegionChannelList", "android.media.tv.extension.scan.IRegionChannelListListener", "android.media.tv.extension.scan.ITargetRegion", "android.media.tv.extension.scan.ITargetRegionListener", "android.media.tv.extension.scan.ILcnConflict", "android.media.tv.extension.scan.ILcnConflictListener", "android.media.tv.extension.scan.ILcnV2ChannelList", "android.media.tv.extension.scan.ILcnV2ChannelListListener", "android.media.tv.extension.scan.IFavoriteNetwork", "android.media.tv.extension.scan.IFavoriteNetworkListener", "android.media.tv.extension.scan.ITkgsInfo", "android.media.tv.extension.scan.ITkgsInfoListener", "android.media.tv.extension.scan.IScanSatSearch", "android.media.tv.extension.oad.IOadUpdateInterface", "android.media.tv.extension.cam.ICamAppInfoService", "android.media.tv.extension.cam.ICamAppInfoListener", "android.media.tv.extension.cam.ICamMonitoringService", "android.media.tv.extension.cam.ICamInfoListener", ICI_OPERATOR_INTERFACE, ICI_OPERATOR_LISTENER, "android.media.tv.extension.cam.ICamProfileInterface", "android.media.tv.extension.cam.IContentControlService", "android.media.tv.extension.cam.ICamDrmInfoListener", "android.media.tv.extension.cam.ICamPinService", "android.media.tv.extension.cam.ICamPinCapabilityListener", "android.media.tv.extension.cam.ICamPinStatusListener", "android.media.tv.extension.cam.ICamHostControlService", "android.media.tv.extension.cam.ICamHostControlAskReleaseReplyCallback", "android.media.tv.extension.cam.ICamHostControlInfoListener", "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlag", "android.media.tv.extension.cam.ICamHostControlTuneQuietlyFlagListener", "android.media.tv.extension.cam.IMmiInterface", "android.media.tv.extension.cam.IMmiSession", "android.media.tv.extension.cam.IMmiStatusCallback", "android.media.tv.extension.cam.IEnterMenuErrorCallback", "android.media.tv.extension.rating.IDownloadableRatingTableMonitor", "android.media.tv.extension.rating.IRatingInterface", "android.media.tv.extension.rating.IPmtRatingInterface", "android.media.tv.extension.rating.IPmtRatingListener", "android.media.tv.extension.rating.IVbiRatingInterface", "android.media.tv.extension.rating.IVbiRatingListener", IPROGRAM_INFO, IPROGRAM_INFO_LISTENER, "android.media.tv.extension.time.IBroadcastTime", "android.media.tv.extension.teletext.IDataServiceSignalInfo", "android.media.tv.extension.teletext.IDataServiceSignalInfoListener", "android.media.tv.extension.teletext.ITeletextPageSubCode", "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdate", "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener", "android.media.tv.extension.clienttoken.IClientToken", "android.media.tv.extension.screenmode.IScreenModeSettings", "android.media.tv.extension.signal.IHdmiSignalInterface", "android.media.tv.extension.signal.IHdmiSignalInfoListener", "android.media.tv.extension.signal.IAudioSignalInfo", "android.media.tv.extension.signal.IAnalogAudioInfo", "android.media.tv.extension.signal.IAudioSignalInfoListener", "android.media.tv.extension.signal.IVideoSignalInfo", "android.media.tv.extension.signal.IVideoSignalInfoListener", "android.media.tv.extension.servicedb.IServiceListEdit", "android.media.tv.extension.servicedb.IServiceListEditListener", "android.media.tv.extension.servicedb.IServiceList", "android.media.tv.extension.servicedb.IServiceListTransferInterface", "android.media.tv.extension.servicedb.IServiceListExportSession", "android.media.tv.extension.servicedb.IServiceListExportListener", "android.media.tv.extension.servicedb.IServiceListImportSession", "android.media.tv.extension.servicedb.IServiceListImportListener", "android.media.tv.extension.servicedb.IServiceListSetChannelListSession", "android.media.tv.extension.servicedb.IServiceListSetChannelListListener", "android.media.tv.extension.servicedb.IChannelListTransfer", "android.media.tv.extension.pvr.IRecordedContents", "android.media.tv.extension.pvr.IDeleteRecordedContentsCallback", "android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback", "android.media.tv.extension.event.IEventMonitor", "android.media.tv.extension.event.IEventMonitorListener", "android.media.tv.extension.event.IEventDownload", "android.media.tv.extension.event.IEventDownloadListener", "android.media.tv.extension.event.IEventDownloadSession", "android.media.tv.extension.analog.IAnalogAttributeInterface", "android.media.tv.extension.tune.IChannelTunedInterface", "android.media.tv.extension.tune.IChannelTunedListener", "android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface", "android.media.tv.extension.signal.ITunerFrontendSignalInfoListener", "android.media.tv.extension.tune.IMuxTuneSession", "android.media.tv.extension.tune.IMuxTune"}));

    @Retention(RetentionPolicy.SOURCE)
    public @interface StandardizedExtensionName {
    }

    public static List<String> getStandardExtensionInterfaceNames() {
        return new ArrayList(sTisExtensions);
    }

    static boolean checkIsStandardizedInterfaces(String str) {
        return sTisExtensions.contains(str);
    }

    public static boolean checkIsStandardizedIBinder(String str, IBinder iBinder) {
        if (iBinder == null) {
            return false;
        }
        try {
            return iBinder.getInterfaceDescriptor().equals(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Fetching IBinder object failure due to " + e);
            return false;
        }
    }
}
