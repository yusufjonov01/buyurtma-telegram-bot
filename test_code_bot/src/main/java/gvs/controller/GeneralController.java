package gvs.controller;

import gvs.dto.CodeMessage;
import gvs.enums.CodeMessageType;
import gvs.repository.OrderRepository;
import gvs.repository.ProductRepository;
import gvs.repository.UserRepository;
import gvs.util.InlineButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


public class GeneralController {
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public CodeMessage handle(String text, Long chatId, Integer messageId) {

        CodeMessage codeMessage = new CodeMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        codeMessage.setSendMessage(sendMessage);

        if (text.equals("/start")) {

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);

            // new list
            List<KeyboardRow> keyboard = new ArrayList<>();

            // first keyboard line
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText("Menu");
            keyboardFirstRow.add(keyboardButton);

            // add array to list
            keyboard.add(keyboardFirstRow);

            // add list to our keyboard
            replyKeyboardMarkup.setKeyboard(keyboard);

            sendMessage.setText("Assalomu alaykum *Buyurtma test* botiga xush kelibsiz.");
            sendMessage.setParseMode("Markdown");

//            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
//                    InlineButtonUtil.collection(
//                            InlineButtonUtil.row(InlineButtonUtil.button("\uD83D\uDD39 Menyu \uD83D\uDD39", "menu"))
//                    )));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            codeMessage.setType(CodeMessageType.MESSAGE);

        }
        else if (text.equals("/help")) {

            String msg = "*TodoList* Yordam oynasi.\n Siz bu bo'tda qilish kerak bo'lgna islariz jadvalini tuzishingiz mumkin.\n" +
                    "malumot uchun videoni [inline URL](https://www.youtube.com/channel/UCFoy0KOV9sihL61PJSh7x1g)  ko'ring.\n"
                    + "Yokiy manabu videoni ko'ring!";
            sendMessage.setText(msg);
            sendMessage.setParseMode("Markdown");
            sendMessage = sendMessage.disableWebPagePreview();

            SendVideo sendVideo = new SendVideo();
            sendVideo.setVideo("BAACAgIAAxkBAAICnF6Fj1geeSI9o8t3sPsyds-9yBUbAAJvBgACVpUwSAcyTHmoZHr3GAQ");
            sendVideo.setChatId(chatId);
            sendVideo.setCaption("Bu video siz uhun juda muxim");
            sendVideo.setParseMode("HTML");

            codeMessage.setSendVideo(sendVideo);

            codeMessage.setSendMessage(sendMessage);
            codeMessage.setType(CodeMessageType.MESSAGE_VIDEO);

        }
        else if (text.equals("/setting")) {
            sendMessage.setText("Settinglar xali mavjut emas");
            sendMessage.setParseMode("Markdown");
            codeMessage.setType(CodeMessageType.MESSAGE);
        }
        else if (text.equals("/adminPanel") || text.equals("adminPanel")) {
            return this.getAdminMenu(messageId, chatId);
        }
        else if (text.equals("menu")) {
            sendMessage.setText("*Menyu*");
            sendMessage.setChatId(chatId);
            sendMessage.setParseMode("Markdown");
            sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                    InlineButtonUtil.collection(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Pechenyelar", "/universal/user/sweet", ":cake:"),
                                    InlineButtonUtil.button("Ichimliklar", "/universal/user/drink", ":cocktail:")

                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Tagliklar", "/universal/user/diaper", ":baby:"),
                                    InlineButtonUtil.button("Savat", "/universal/basket", ":pouch:")
                            ),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Biz haqimizda", "/universal/about")
                            )
                    )));

            codeMessage.setType(CodeMessageType.MESSAGE);
            codeMessage.setSendMessage(sendMessage);
        }
        return codeMessage;


    }

    public CodeMessage getAdminMenu(Integer messageId, Long chatId) {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("*Admin Menyu*");
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode("Markdown");

        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                InlineButtonUtil.collection(
                        InlineButtonUtil.row(
                                InlineButtonUtil.button("Mahsulotlar", "/universal/product", ":cake:"),
                                InlineButtonUtil.button("Hisobotlar", "/universal/report", ":cocktail:")
                        ),
                        InlineButtonUtil.row(
                                InlineButtonUtil.button("\uD83D\uDCCA Statistika \uD83D\uDCCA", "/universal/stat"),
                                InlineButtonUtil.button("\uD83D\uDCE4 Xabar yuborish \uD83D\uDCE4", "/universal/sendMes")
                        ),
                        InlineButtonUtil.row(
                                InlineButtonUtil.button(" Biz haqimizda ", "/universal/about/admin"))
                )));

        codeMessage.setType(CodeMessageType.MESSAGE);
        codeMessage.setSendMessage(sendMessage);
        return codeMessage;
    }

    public CodeMessage getMenu(Long chatId) {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("*Savatga qo'shildi!*\n" +
                "Davom etamizmi? \t\uD83D\uDE09 ");
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode("Markdown");

        sendMessage.setReplyMarkup(InlineButtonUtil.keyboard(
                InlineButtonUtil.collection(
                        InlineButtonUtil.row(
                                InlineButtonUtil.button("Pechenyelar", "/universal/user/sweet", ":cake:"),
                                InlineButtonUtil.button("Ichimliklar", "/universal/user/drink", ":cocktail:")

                        ),
                        InlineButtonUtil.row(
                                InlineButtonUtil.button("Tagliklar", "/universal/user/diaper", ":baby:"),
                                InlineButtonUtil.button("Savat", "/universal/basket", ":pouch:")
                        )
                )));


        codeMessage.setType(CodeMessageType.MESSAGE);
        codeMessage.setSendMessage(sendMessage);
        return codeMessage;
    }

}
