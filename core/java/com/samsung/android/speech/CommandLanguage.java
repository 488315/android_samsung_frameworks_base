package com.samsung.android.speech;

import com.samsung.android.core.AppJumpBlockTool;

public class CommandLanguage {
    public static final String[][] CALL = {
        new String[] {"수신", "거절"},
        new String[] {"Answer", "Reject"},
        new String[] {"接听", "拒绝"},
        new String[] {"Responder", "Rechazar"},
        new String[] {"Répondre", "Refuser"},
        new String[] {"Antworten", "Abweisen"},
        new String[] {"Rispondi", "Rifiuta"},
        new String[] {"応答", "拒否"},
        new String[] {"Ответить", "Отклонить"},
        new String[] {"Atender", "Recusar"},
        new String[] {"Answer", "Reject"},
        new String[] {"Responder", "Rechazar"},
        new String[] {"接通", "掛斷"},
        new String[] {"聽電話", "唔聽"},
        new String[] {"听电话", "唔听"}
    };
    public static final String[][] CALL_PDT = {
        new String[] {"수신", "거절"},
        new String[] {"Answer", "Decline"},
        new String[] {"接听", "拒绝"},
        new String[] {"Responder", "Rechazar"},
        new String[] {"Répondre", "Refuser"},
        new String[] {"Antworten", "Abweisen"},
        new String[] {"Rispondi", "Rifiuta"},
        new String[] {"応答", "拒否"},
        new String[] {"Ответить", "Отклонить"},
        new String[] {"Atender", "Recusar"},
        new String[] {"Answer", "Decline"},
        new String[] {"Responder", "Rechazar"},
        new String[] {"接通", "掛斷"},
        new String[] {"聽電話", "唔聽"},
        new String[] {"听电话", "唔听"}
    };
    public static final String[][] ALARM = {
        new String[] {"중지", "다시 알림"},
        new String[] {"Stop", "Snooze"},
        new String[] {"停止", "重响"},
        new String[] {"Parar", "Repetir"},
        new String[] {"Arrêter", "Répéter"},
        new String[] {"Stopp", "Erinnern"},
        new String[] {"Stop", "Dopo"},
        new String[] {"ストップ", "スヌーズ"},
        new String[] {"Остановить", "Отложить"},
        new String[] {"Soneca", "Próxima"},
        new String[] {"Stop", "Snooze"},
        new String[] {"Parar", "Repetir"},
        new String[] {"關閉", "貪睡"},
        new String[] {"關閉", "響多次"},
        new String[] {"关闭", "响多次"}
    };
    public static final String[][] MUSIC = {
        new String[] {"다음", "이전", "일시 정지", "재생", "소리 크게", "소리 작게"},
        new String[] {"Next", "Previous", "Pause", "Play", "Volume Up", "Volume Down"},
        new String[] {"下一首", "上一首", "暂停", "播放", "增大音量", "减小音量"},
        new String[] {
            "Siguiente", "Anterior", "Pausa", "Reproducir", "Subir Volumen", "Bajar Volumen"
        },
        new String[] {
            "Suivant", "Précédent", "Pause", "Lire", "Augmenter le Volume", "Baisser le Volume"
        },
        new String[] {"Weiter", "Vorherige", "Pause", "Wiedergabe", "Lauter", "Leiser"},
        new String[] {
            "Successivo", "Precedente", "Pausa", "Riproduci", "Aumenta Volume", "Abbassa Volume"
        },
        new String[] {"次", "前", "一時停止", "再生", "音量アップ", "音量ダウン"},
        new String[] {"Вперед", "Назад", "Пауза", "Играть", "Громче", "Тише"},
        new String[] {
            "Próxima", "Anterior", "Pausar", "Reproduzir", "Aumentar Volume", "Diminuir Volume"
        },
        new String[] {"Next", "Previous", "Pause", "Play", "Volume Up", "Volume Down"},
        new String[] {
            "Siguiente", "Anterior", "Pausa", "Reproducir", "Subir Volumen", "Bajar Volumen"
        },
        new String[] {"下首", "上首", "暫停", "開始", "放大", "減輕"},
        new String[] {"下一首", "上一首", "暫停", "播放", "大聲", "細聲"},
        new String[] {"下一首", "上一首", "暂停", "播放", "大声", "细声"}
    };
    public static final String[][] CAMERA = {
        new String[] {"스마일", "김치", "촬영", "찰칵", "동영상 촬영"},
        new String[] {"Smile", "Cheese", "Capture", "Shoot", "Record Video"},
        new String[] {"微笑", "茄子", "拍摄", "拍照", "录制视频"},
        new String[] {"Sonrisa", "Patata", "Capturar", "Disparar", "Grabar vídeo"},
        new String[] {"Sourire", "Cheese", "Capture", "Shooter", "Enregistrer une vidéo"},
        new String[] {"Lächeln", "Bitte Lächeln", "Klick", "Aufnahme", "Video aufnehmen"},
        new String[] {"Sorriso", "Cheese", "Cattura", "Scatta", "Registra"},
        new String[] {"スマイル", "チーズ", "撮影", "はいチーズ", "録画"},
        new String[] {"Сфотографировать", "Снять", "Улыбнитесь", "Снимаю", "Запись видео"},
        new String[] {"Sorria", "Diga xis", "Capturar", "Disparar", "Gravar Vídeo"},
        new String[] {"Smile", "Cheese", "Capture", "Shoot", "Record Video"},
        new String[] {"Sonrisa", "Patata", "Capturar", "Disparar", "Grabar Video"},
        new String[] {"笑一個", "Cheese", "攝影", "照相", "錄製影片"},
        new String[] {"笑一笑", "Cheese", "攝影", "影相", "錄影"},
        new String[] {"笑一笑", "Cheese", "摄影", "影相", "录影"}
    };
    public static final String[][] CANCEL = {
        new String[] {"취소"},
        new String[] {AppJumpBlockTool.BlockDialogReceiver.RESULT_CANCEL},
        new String[] {"取消"},
        new String[] {"Cancelar"},
        new String[] {"Annuler"},
        new String[] {"Abbrechen"},
        new String[] {"Annulla"},
        new String[] {"キャンセル"},
        new String[] {"Отмена"},
        new String[] {"Cancelar"},
        new String[] {AppJumpBlockTool.BlockDialogReceiver.RESULT_CANCEL},
        new String[] {"Cancelar"},
        new String[] {"退出"},
        new String[] {"取消"},
        new String[] {"取消"}
    };
}
