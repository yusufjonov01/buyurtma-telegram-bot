package gvs.dto;

import gvs.enums.CodeMessageType;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class CodeMessage {
    public CodeMessageType type;

    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private SendVideo sendVideo;
    private SendPhoto sendPhoto;
    private SendLocation sendLocation;
    private SendDocument sendDocument;

    public SendDocument getSendDocument() {
        return sendDocument;
    }

    public void setSendDocument(SendDocument sendDocument) {
        this.sendDocument = sendDocument;
    }

    public SendLocation getSendLocation() {
        return sendLocation;
    }

    public void setSendLocation(SendLocation sendLocation) {
        this.sendLocation = sendLocation;
    }

    public SendPhoto getSendPhoto() {
        return sendPhoto;
    }

    public void setSendPhoto(SendPhoto sendPhoto) {
        this.sendPhoto = sendPhoto;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public EditMessageText getEditMessageText() {
        return editMessageText;
    }

    public void setEditMessageText(EditMessageText editMessageText) {
        this.editMessageText = editMessageText;
    }

    public CodeMessageType getType() {
        return type;
    }

    public void setType(CodeMessageType type) {
        this.type = type;
    }

    public SendVideo getSendVideo() {
        return sendVideo;
    }

    public void setSendVideo(SendVideo sendVideo) {
        this.sendVideo = sendVideo;
    }
}
