package gvs.controller;

import gvs.dto.CodeMessage;
import gvs.dto.UserItem;
import gvs.repository.ProductRepository;
import gvs.repository.UserRepository;
import gvs.service.FileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;


public class MainController extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private GeneralController generalController;
    private FileInfoService fileInfoService;
    private SendOrderController sendOrderController;
    private OrderController orderController;
    private UniversalController universalController;
    private UserRepository userRepository;

    public MainController() {
        this.userRepository = new UserRepository();
        this.generalController = new GeneralController();
        this.fileInfoService = new FileInfoService();
        this.orderController = new OrderController();
        this.sendOrderController = new SendOrderController();
        this.universalController = new UniversalController();
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            if (update.hasCallbackQuery()) {

                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                User user = callbackQuery.getFrom();
                message = callbackQuery.getMessage();

                LOGGER.info("messageId: " + message.getMessageId() + "  User_Name  " + user.getFirstName() + " User_username  " + user.getUserName() + "  message: " + data);

                if (data.equals("menu") || data.equals("/adminPanel")) {
                    this.sendMsg(this.generalController.handle(data, message.getChatId(), message.getMessageId()));
                }
                if (data.startsWith("/universal")) {
                    this.sendMsg(this.universalController.handle(data, message.getChatId(), message.getMessageId(), message));
                }
                if (data.startsWith("/order")) {
                    this.sendMsg(this.orderController.handle(data, message.getChatId(), message.getMessageId()));
                }
            } else if (message != null) {
                String text = message.getText();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                Integer messageId = message.getMessageId();
                User user = message.getFrom();

                LOGGER.info("messageId: " + messageId + "  User_Name  " + user.getUserName() + " User_username  " + user.getUserName() + "  message: " + text);


                if (text != null) {
                    if (text.equals("/start") || (text.equals("Menu")) || text.equals("/help") || text.equals("/setting") || text.equals("/adminPanel") || text.equals("adminPanel")) {
                        if (text.equals("/start")) {
                            this.universalController.getUser(message);
                        }
                        if (text.equals("Menu")) {
                            text = "menu";
                        }
                        this.sendMsg(this.generalController.handle(text, message.getChatId(), messageId));
                    } else if (this.universalController.getProductItemMap().containsKey(message.getChatId()) || text.startsWith("/universal/")) {
                        this.sendMsg(this.universalController.handle(text, message.getChatId(), message.getMessageId(), message));
                    } else {
                        sendMessage.setText("Mavjud Emas");
                        this.sendMsg(sendMessage);
                    }
                } else if (update.getMessage().getPhoto() != null || update.getMessage().getVideo() != null) {
                    this.sendMsg(this.universalController.handle(this.fileInfoService.getFileInfo(message).getSendMessage().getText(), message.getChatId(), message.getMessageId(), message));
                } else if (update.getMessage().getDocument() != null) {
                    this.sendMsg(this.universalController.handle(this.fileInfoService.getFileInfo(message).getSendMessage().getText(), message.getChatId(), message.getMessageId(), message));
                } else if (update.getMessage().getContact() != null) {
                    this.sendMsg(this.universalController.handle(update.getMessage().getContact().getPhoneNumber(), message.getChatId(), message.getMessageId(), message));
                } else if (update.getMessage().getLocation() != null) {
                    String location = (update.getMessage().getLocation().getLongitude() + "/" + update.getMessage().getLocation().getLatitude());
                    this.sendMsg(this.universalController.handle(location, message.getChatId(), message.getMessageId(), message));
                } else {
                    this.sendMsg(this.fileInfoService.getFileInfo(message));
                }
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(CodeMessage codeMessage) {
        try {
            switch (codeMessage.getType()) {
                case MESSAGE:
                    execute(codeMessage.getSendMessage());
                    break;
                case EDIT:
                    execute(codeMessage.getEditMessageText());
                    break;
                case MESSAGE_VIDEO:
                    execute(codeMessage.getSendMessage());
                    execute(codeMessage.getSendVideo());
                    break;
                case MESSAGE_PHOTO:
                    execute(codeMessage.getSendPhoto());
                    execute(codeMessage.getSendMessage());
                    break;
                case PHOTO:
                    execute(codeMessage.getSendPhoto());
                    break;
                case MESSAGE_LOCATION:
                    execute(codeMessage.getSendMessage());
                    execute(codeMessage.getSendLocation());
                    break;
                case MESSAGE_FILE:
                    execute(codeMessage.getSendDocument());
                    execute(codeMessage.getSendMessage());
                    break;
                default:
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
