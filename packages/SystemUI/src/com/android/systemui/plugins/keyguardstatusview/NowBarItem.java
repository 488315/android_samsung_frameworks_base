package com.android.systemui.plugins.keyguardstatusview;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NowBarItem {
    public static final int COLOR_INVALID = 1;
    public static final String EXTRA_NOW_BAR_KEY = "android.nowBarKey";
    public static final String EXTRA_NOW_BAR_PACKAGE = "android.nowBarPackage";
    public static final String EXTRA_NOW_BAR_SHOULD_REMOVE = "android.nowBarShouldRemove";
    public static final String EXTRA_NOW_BAR_VIEW_STYLE = "android.nowBarViewStyle";
    public static final String EXTRA_ONGOING_ACTIVITY_CARD_ICON = "android.ongoingActivityCardIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_CARD_ICON_BG = "android.ongoingActivityCardIconBg";
    public static final String EXTRA_ONGOING_ACTIVITY_EXPANDED_NOW_BAR_VIEW = "android.ongoingActivityExpandedNowBarView";
    public static final String EXTRA_ONGOING_ACTIVITY_PENDING_INTENT = "android.ongoingActivityPendingIntent";
    public static final String EXTRA_ONGOING_ACTIVITY_PRIMARY_INFO = "android.ongoingActivityPrimaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_SECONDARY_INFO = "android.ongoingActivitySecondaryInfo";
    public static final int INDEX_NOT_FOUND = -1;
    public static final int NOW_BAR_VIEW_STYLE_INVALID = -1;
    private ArrayList<Integer> actionBgColors;
    private ArrayList<Notification.Action> actions;
    private int cardBackgroundColor;
    private Icon cardIcon;
    private int cardIconBg;
    private int chipBackground;
    private Icon chipIcon;
    private String chronometerTag;
    private RemoteViews chronometerView;
    private View contentViewForExpandCard;
    private View contentViewForNormalCard;
    private RemoteViews customExpandedCardView;
    private CharSequence expandedChipText;
    private RemoteViews expandedChipView;
    private Bundle extraData;
    private PluginFaceWidgetMediaData faceWidgetMediaData;
    private String moreInfo;
    private String notiID;
    private NowBarItemObject nowBarItemObject;
    private String nowBarKey;
    private String nowBarPackage;
    private int nowBarViewStyle;
    private Icon nowbarIcon;
    private String nowbarPrimaryInfo;
    private String nowbarSecondaryInfo;
    private RemoteViews ongoingExpandView;
    private RemoteViews ongoingNowbarView;
    private PendingIntent pendingIntent;
    private int primaryActionNum;
    private String primaryInfo;
    private int primaryInfoColor;
    private String secondaryInfo;
    private int secondaryInfoColor;
    private Boolean shouldRemoveCard;
    private int userId;

    public NowBarItem() {
        this.notiID = "";
        this.pendingIntent = null;
        this.chipIcon = null;
        this.expandedChipView = null;
        this.chipBackground = 1;
        this.actions = null;
        this.actionBgColors = null;
        this.primaryActionNum = 0;
        this.cardIcon = null;
        this.cardIconBg = 1;
        this.primaryInfo = "";
        this.primaryInfoColor = 1;
        this.secondaryInfo = "";
        this.secondaryInfoColor = 0;
        this.moreInfo = "";
        this.customExpandedCardView = null;
        this.cardBackgroundColor = 1;
        this.nowBarKey = "";
        this.nowBarPackage = "";
        this.nowBarViewStyle = -1;
        this.faceWidgetMediaData = null;
        this.expandedChipText = "";
        this.shouldRemoveCard = Boolean.FALSE;
        this.contentViewForNormalCard = null;
        this.nowbarIcon = null;
        this.nowbarPrimaryInfo = "";
        this.nowbarSecondaryInfo = "";
        this.userId = -10000;
        this.extraData = new Bundle();
        this.nowBarItemObject = null;
    }

    public ArrayList<Integer> getActionBgColors() {
        return this.actionBgColors;
    }

    public ArrayList<Notification.Action> getActions() {
        return this.actions;
    }

    public int getCardBackgroundColor() {
        return this.cardBackgroundColor;
    }

    public Icon getCardIcon() {
        return this.cardIcon;
    }

    public int getCardIconBg() {
        return this.cardIconBg;
    }

    public int getChipBackground() {
        return this.chipBackground;
    }

    public Icon getChipIcon() {
        return this.chipIcon;
    }

    public String getChronometerTag() {
        return this.chronometerTag;
    }

    public RemoteViews getChronometerView() {
        return this.chronometerView;
    }

    public View getContentViewForExpandCard() {
        return this.contentViewForExpandCard;
    }

    public View getContentViewForNormalCard() {
        return this.contentViewForNormalCard;
    }

    public RemoteViews getCustomExpandedCardView() {
        return this.customExpandedCardView;
    }

    public CharSequence getExpandedChipText() {
        return this.expandedChipText;
    }

    public RemoteViews getExpandedChipView() {
        return this.expandedChipView;
    }

    public Bundle getExtraData() {
        return this.extraData;
    }

    public PluginFaceWidgetMediaData getFaceWidgetMediaData() {
        return this.faceWidgetMediaData;
    }

    public String getMoreInfo() {
        return this.moreInfo;
    }

    public String getNotiID() {
        return this.notiID;
    }

    public String getNowBarKey() {
        return this.nowBarKey;
    }

    public String getNowBarPackage() {
        return this.nowBarPackage;
    }

    public int getNowBarViewStyle() {
        return this.nowBarViewStyle;
    }

    public Icon getNowbarIcon() {
        return this.nowbarIcon;
    }

    public String getNowbarPrimaryInfo() {
        return this.nowbarPrimaryInfo;
    }

    public String getNowbarSecondaryInfo() {
        return this.nowbarSecondaryInfo;
    }

    public NowBarItemObject getObject() {
        return this.nowBarItemObject;
    }

    public RemoteViews getOngoingExpandView() {
        return this.ongoingExpandView;
    }

    public RemoteViews getOngoingNowbarView() {
        return this.ongoingNowbarView;
    }

    public PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public int getPrimaryActionNum() {
        return this.primaryActionNum;
    }

    public String getPrimaryInfo() {
        return this.primaryInfo;
    }

    public int getPrimaryInfoColor() {
        return this.primaryInfoColor;
    }

    public String getSecondaryInfo() {
        return this.secondaryInfo;
    }

    public int getSecondaryInfoColor() {
        return this.secondaryInfoColor;
    }

    public Boolean getShouldRemoveCard() {
        return this.shouldRemoveCard;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setActionBgColors(ArrayList<Integer> arrayList) {
        this.actionBgColors = arrayList;
    }

    public void setActions(ArrayList<Notification.Action> arrayList) {
        this.actions = arrayList;
    }

    public void setCardBackgroundColor(int i) {
        this.cardBackgroundColor = i;
    }

    public void setCardIcon(Icon icon) {
        this.cardIcon = icon;
    }

    public void setCardIconBg(int i) {
        this.cardIconBg = i;
    }

    public void setChipBackground(int i) {
        this.chipBackground = i;
    }

    public void setChipIcon(Icon icon) {
        this.chipIcon = icon;
    }

    public void setChronometerTag(String str) {
        this.chronometerTag = str;
    }

    public void setChronometerView(RemoteViews remoteViews) {
        this.chronometerView = remoteViews;
    }

    public void setContentViewForExpandCard(View view) {
        this.contentViewForExpandCard = view;
    }

    public void setContentViewForNormalCard(View view) {
        this.contentViewForNormalCard = view;
    }

    public void setCustomExpandedCardView(RemoteViews remoteViews) {
        this.customExpandedCardView = remoteViews;
    }

    public void setExpandedChipText(CharSequence charSequence) {
        this.expandedChipText = charSequence;
    }

    public void setExpandedChipView(RemoteViews remoteViews) {
        this.expandedChipView = remoteViews;
    }

    public void setExtraData(Bundle bundle) {
        this.extraData = bundle;
    }

    public void setFaceWidgetMediaData(PluginFaceWidgetMediaData pluginFaceWidgetMediaData) {
        this.faceWidgetMediaData = pluginFaceWidgetMediaData;
    }

    public void setMoreInfo(String str) {
        this.moreInfo = str;
    }

    public void setNotiID(String str) {
        this.notiID = str;
    }

    public void setNowBarKey(String str) {
        this.nowBarKey = str;
    }

    public void setNowBarPackage(String str) {
        this.nowBarPackage = str;
    }

    public void setNowBarViewStyle(int i) {
        this.nowBarViewStyle = i;
    }

    public void setNowbarIcon(Icon icon) {
        this.nowbarIcon = icon;
    }

    public void setNowbarPrimaryInfo(String str) {
        this.nowbarPrimaryInfo = str;
    }

    public void setNowbarSecondaryInfo(String str) {
        this.nowbarSecondaryInfo = str;
    }

    public void setObject(NowBarItemObject nowBarItemObject) {
        this.nowBarItemObject = nowBarItemObject;
    }

    public void setOngoingExpandView(RemoteViews remoteViews) {
        this.ongoingExpandView = remoteViews;
    }

    public void setOngoingNowbarView(RemoteViews remoteViews) {
        this.ongoingNowbarView = remoteViews;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public void setPrimaryActionNum(int i) {
        this.primaryActionNum = i;
    }

    public void setPrimaryInfo(String str) {
        this.primaryInfo = str;
    }

    public void setPrimaryInfoColor(int i) {
        this.primaryInfoColor = i;
    }

    public void setSecondaryInfo(String str) {
        this.secondaryInfo = str;
    }

    public void setSecondaryInfoColor(int i) {
        this.secondaryInfoColor = i;
    }

    public void setShouldRemoveCard(boolean z) {
        this.shouldRemoveCard = Boolean.valueOf(z);
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public String toString() {
        return "NowBarItem{notiID=" + this.notiID + ", nowBarKey='" + this.nowBarKey + ", nowBarPackage='" + this.nowBarPackage + ", nowBarViewStyle=" + this.nowBarViewStyle + ", userId=" + this.userId + ", pendingIntent=" + this.pendingIntent + ", chipIcon=" + this.chipIcon + ", expandedChipView=" + this.expandedChipView + ", chipBackground=" + this.chipBackground + ", actions=" + this.actions + ", actionBgColors=" + this.actionBgColors + ", primaryActionNum=" + this.primaryActionNum + ", cardIcon=" + this.cardIcon + ", cardIconBg=" + this.cardIconBg + ", primaryInfo='" + this.primaryInfo + ", primaryInfoColor=" + this.primaryInfoColor + ", secondaryInfo='" + this.secondaryInfo + ", secondaryInfoColor=" + this.secondaryInfoColor + ", moreInfo='" + this.moreInfo + ", customExpandedCardView=" + this.customExpandedCardView + ", cardBackground=" + this.cardBackgroundColor + ", faceWidgetMediaData=" + this.faceWidgetMediaData + ", expandedChipText=" + ((Object) this.expandedChipText) + ", chronometerView=" + this.chronometerView + ", chronometerTag=" + this.chronometerTag + ", shouldRemoveCard=" + this.shouldRemoveCard + ", contentViewForNormalCard=" + this.contentViewForNormalCard + ", contentViewForExpandCard=" + this.contentViewForExpandCard + '}';
    }

    public NowBarItem(Intent intent) {
        try {
            setPendingIntent((PendingIntent) intent.getParcelableExtra(EXTRA_ONGOING_ACTIVITY_PENDING_INTENT, PendingIntent.class));
            setNowBarKey(String.valueOf(intent.getCharSequenceExtra(EXTRA_NOW_BAR_KEY)));
            setNowBarPackage(String.valueOf(intent.getCharSequenceExtra(EXTRA_NOW_BAR_PACKAGE)));
            setNowBarViewStyle(intent.getIntExtra(EXTRA_NOW_BAR_VIEW_STYLE, -1));
            setCardIcon((Icon) intent.getParcelableExtra(EXTRA_ONGOING_ACTIVITY_CARD_ICON, Icon.class));
            setCardIconBg(intent.getIntExtra(EXTRA_ONGOING_ACTIVITY_CARD_ICON_BG, 1));
            setPrimaryInfo(String.valueOf(intent.getCharSequenceExtra(EXTRA_ONGOING_ACTIVITY_PRIMARY_INFO)));
            setSecondaryInfo(String.valueOf(intent.getCharSequenceExtra(EXTRA_ONGOING_ACTIVITY_SECONDARY_INFO)));
            setOngoingExpandView((RemoteViews) intent.getParcelableExtra(EXTRA_ONGOING_ACTIVITY_EXPANDED_NOW_BAR_VIEW, RemoteViews.class));
            setShouldRemoveCard(intent.getBooleanExtra(EXTRA_NOW_BAR_SHOULD_REMOVE, false));
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("receivedIntent exception = ", e, "NowBarItem");
        }
    }
}
