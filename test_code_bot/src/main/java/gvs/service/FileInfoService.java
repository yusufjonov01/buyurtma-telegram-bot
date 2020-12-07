package gvs.service;

import gvs.dto.CodeMessage;
import gvs.enums.CodeMessageType;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class FileInfoService extends TelegramLongPollingBot {

    public CodeMessage getFileInfo(Message message) throws TelegramApiException { //< GETTING MESSAGE (WHICH CONTAINS FILE)


        Long cId = message.getChatId();

        CodeMessage codeMessage = new CodeMessage();
        codeMessage.setType(CodeMessageType.MESSAGE);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(cId);


        if (message.getPhoto() != null) { // ADDING PHOTO
            String s = this.returnPhotoId(message.getPhoto());
            sendMessage.setText(s);
        } else if (message.getVideo() != null) { // ADDING VIDEO
            String s = this.show_video_detail(message.getVideo());
            sendMessage.setText(s);
        } else if (message.getDocument() != null) {
            sendMessage.setText(this.returnDocPath(message));
        } else {
            sendMessage.setText("NOT FOUND");
        }

        codeMessage.setSendMessage(sendMessage);
        return codeMessage;
    }

    private String returnDocPath(Message message) throws TelegramApiException {
        Document document = message.getDocument();
        String uploadedFileId = document.getFileId();
        GetFile uploadedFile = new GetFile();
        uploadedFile.setFileId(uploadedFileId);
        File file = execute(uploadedFile);
        return file.getFileUrl("1117431038:AAFHCZVhC47wFLRPbI9ca_paHygly_5h11M");
    }

    private String returnPhotoId(List<PhotoSize> photoSizeList) {
//        String s = "------------------------- PHOTO INFO -------------------------\n";
        String photoId = "";
        for (PhotoSize photoSize : photoSizeList) {
//            s += " Size = " + photoSize.getFileSize() + "  ,    ID  = " + photoSize.getFileId() + " \n";
            photoId = photoSize.getFileId();
        }
        return photoId;
    }

    private String show_video_detail(Video video) {
        String s = "------------------------- VIDEO INFO -------------------------\n";
        s += video.toString(); //" Size " + video.getFileSize() + "  ,  duration = " + video.getDuration() + "  ID = " + video.getMimeType();
        return video.getFileId();
    }


    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "t.me/buyurtma_test_bot";
    }

    @Override
    public String getBotToken() {
        return "1117431038:AAFHCZVhC47wFLRPbI9ca_paHygly_5h11M";
    }
}
